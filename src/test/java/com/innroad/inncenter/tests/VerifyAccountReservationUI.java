package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyAccountReservationUI extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	Navigation nav = new Navigation();
	Account accountPage = new Account();
	CPReservationPage reservationPage = new CPReservationPage();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	Groups group = new Groups();
	AdvGroups advgrp = new AdvGroups();
	LedgerAccount la = new LedgerAccount();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop = new Properties();
	Tapechart tc = new Tapechart();
	Admin admin = new Admin();
	RoomClass rc = new RoomClass();
	RoomMaintenance rm = new RoomMaintenance();
	RatesGrid ratesGrid = new RatesGrid();	
	

	String reservationNumber = null, guestFirstName = null, guestLastName, phoneNumber = "1234567890",  
	email = "innRoadTestEmail@innroad.com",	address1 = "10th Building", address2 = "Block C", address3 = "Street 10",
	city = "NewYork", country = "United States", state = "Alaska", postalCode = "12345", referral = "Walk In",
	paymentMethod = "MC", cardNumber = "5454545454545454", currency = null, clientTimeZone, dFormat, sourceOfRes, propertyName, 
	accountFirstName="code", accountLastName="auto", reportsTab, applicationTab, currentDay, accountName = "AccountEQSVVD", roomNumber,
	cardExpDate, updateStartDate, updateEndDate, newRoomClassName, roomCharge , guestFullName,
	marketSegment = "Internet", adults = "1", children = "0";
	
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();

	ArrayList<String> reservationDates = new ArrayList<>();
	HashMap<String, Double> expChanges = new HashMap<>();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	@BeforeClass()
	public void getTimeZone() throws Exception{
		driver = getDriver();
		login_CP(driver);
		nav.admin(driver);
		nav.navigateToClientinfo(driver);		
		admin.clickClientName(driver);
		admin.clickClientOption(driver);		
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		if (currency.equalsIgnoreCase("USD ( $ ) ")) {
			TestCore.propertyCurrency = "$";
		}else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
			TestCore.propertyCurrency = "£";
		}			
	
		TestCore.propertyFirstDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
		clientTimeZone = admin.getClientTimeZone(driver, test_steps);
		dFormat = admin.getClientDateFormat(driver);
		
		switch (clientTimeZone) {
		case "(GMT-05:00) Eastern Time (US and Canada)":
			TestCore.propertyTimeZone = "US/Eastern";
			break;			
		case "(GMT-06:00) Central Time (US and Canada)":
			TestCore.propertyTimeZone = "US/Central";
			break;		
		case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
			TestCore.propertyTimeZone = "Europe/London";
			break;
		default:
			break;
		}
		
		if (dFormat.equalsIgnoreCase("USA")) {
			TestCore.propertyDateFormat = "MMM dd, YYYY";
		}else if (dFormat.equalsIgnoreCase("International")) {
			TestCore.propertyDateFormat = "dd MMM, YYYY";
		}
		try {
		nav.ReservationV2_Backward(driver);
		}catch (Exception e) {
			e.printStackTrace();
		}
		cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
	}

	@Test(dataProvider = "getData")
	public void verifyUI(String Scenario, String CheckInDate, String CheckOutDate,		
			String accountType, String associateAccount,String ResType,String Adult,String Child,
			String Rateplan, String RoomClass) throws Throwable {
		children=Child;
		
		test_name = "Verify UI validation from account reservation";
		String testdescription = "Verify that the information present in the accounts are pre-populated in the reservation as per the selected respective accounts.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824786&group_by=cases:section_id&group_order=asc&display_deleted_cases=0&group_id=58295' target='_blank'>"
				+ "Click here to open TestRail: C58295</a><br>";
		test_category = "Verify UI validation from account reservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Utility.closeTabsExcept(driver, 1);
		login_CP(driver);	
		int noOfRooms = 1;
		
		if (Utility.validateString(accountType)) {
			accountName = "AccountEQSVVDDDDDD";
		}else {
			accountName = "";
		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			if (!ResType.equalsIgnoreCase("MRB")) {
				if ( !(Utility.validateDate(CheckInDate)) ) {
	            	CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
	            	if (Scenario.contains("multi")) {
	            		CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					} else if (Scenario.contains("single")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					} else if (Scenario.contains("extend")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
						updateEndDate = "";
						updateEndDate = Utility.addDays(CheckOutDate, 2);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, updateEndDate);
					} else if (Scenario.contains("reduce")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
						updateEndDate = "";
						updateEndDate = Utility.addDays(CheckOutDate, -1);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, updateEndDate);
					} else if (Scenario.contains("update") && Scenario.contains("dates")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
						updateStartDate = "";
						updateStartDate = Utility.addDays(CheckOutDate, 3);
						updateEndDate = "";
						updateEndDate = Utility.addDays(CheckOutDate, 3);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(updateStartDate, updateEndDate);
					}else {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					}
	    		}
			} else {
				checkInDates.clear();
				checkOutDates.clear();
				if ( !(Utility.validateDate(CheckInDate)) ) {
					for (int i = 0; i < noOfRooms; i++) {
						checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
						if (Scenario.contains("multi")) {
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
						}else {
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
						
					}
					CheckInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
					CheckOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}

			}
			if (!Utility.validateString(adults)) {
				adults = "2";
			}
			if (!Utility.validateString(children)) {
				children = "0";
			}
			guestFirstName = "code";
			guestLastName = "auto"; 
			roomNumber=null;			
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		ArrayList<String> RoomClasses = new ArrayList<>();
		String[] rc = RoomClass.split("\\|");
		if (rc.length>1) {
			for (int i = 0; i < rc.length; i++) {
				RoomClasses.add(rc[i]);
			}
		}else {
			RoomClasses.add(RoomClass);
		}
		
		if (CheckInDate.split("\\|").length > 1) {
			numberOfNights = Utility.getNumberofDays(CheckInDate.split("\\|")[0], CheckOutDate.split("\\|")[0]);
		}else {
			numberOfNights = Utility.getNumberofDays(CheckInDate, CheckOutDate);
	
		}
		
		app_logs.info("Number of nights: "+numberOfNights);
		Utility.switchTab(driver, applicationTab);
	      try {
			accountPage.clickNewReservationFromAccount(driver, test_steps, associateAccount, accountName, marketSegment,
					referral, accountFirstName, accountLastName, phoneNumber, phoneNumber, address1, address2,
					address3, email, city, state, postalCode, null);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adults);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, Rateplan, null);
			reservationPage.click_FindRooms(driver, test_steps);
			if (!Utility.validateString(roomNumber)) {
				ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClass);
				reservationPage.selectRoomToReserve(driver, test_steps, RoomClass, rooms.get(0));
//				data.put("Room Number", rooms.get(0));
			} else {
				reservationPage.selectRoomToReserve(driver, test_steps, RoomClass, roomNumber);
//				data.put("Room Number", roomNumber);
			}
			reservationPage.click_Next(driver, test_steps);
			reservationPage.clickYesOrNoOnPolicyChangePopup(driver, test_steps,"Yes");
			test_steps.addAll(reservationPage.clickonPaymentMetod(driver));
			test_steps.addAll(reservationPage.selectPaymentMethod(driver,"Account (Corp/Member)"));
			reservationPage.verifyCreateNewReservationFieldUI(driver, test_steps, associateAccount, accountName, marketSegment,
					referral, accountFirstName, accountLastName, phoneNumber, phoneNumber, address1, address2,
					address3, email, city, state, postalCode);
			try {
			reservationPage.enter_GuestName(driver, test_steps, "Mr.", guestFirstName, guestLastName);
			}
			catch (Exception e) {
				// TODO: handle exception
			}

			reservationPage.click_BookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

			test_steps.clear();
			test_name = "Verify reservation status dropdown";
			testdescription = "Verify reservation status dropdown will include the following statuses.<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824783' target='_blank'>"
					+ "Click here to open TestRail: C824783</a><br>";
			testName = test_name;
			test_description=testdescription;
			test_steps.clear();
			test_steps.add("========== Guaranteed Reservation ==========");
			app_logs.info("========== Guaranteed Reservation ==========");
			String[] DropDownListAfterGuranteed= {"Reserved","Confirmed","On Hold","Cancel","No Show"};
			test_steps.addAll(reservationPage.guaranteedReservation(driver));
			test_steps.add("========== Verify Reservation Dropdown After Guaranteed Reservation ==========");
			app_logs.info("========== Verify Reservation Dropdown After Guaranteed Reservation ==========");
			test_steps.addAll(reservationPage.verifyReservationDropDown(driver,DropDownListAfterGuranteed));
		  	RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);	

	      }
	      catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		}	
	}
	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyAccountReservationUI", envLoginExcel);
	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
