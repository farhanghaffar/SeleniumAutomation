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

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class ReportsV2TransactionsReportValidationForReservationV2 extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	ReservationV2 reservationV2Page = new ReservationV2();
	Admin admin = new Admin();
	NewRoomClassesV2 rc2 = new NewRoomClassesV2();
	GuestFolio guestFolio = new GuestFolio();

	String guestFirstName, guestLastName, phoneNumber = "1234567890", email = "innRoadTestEmail@innroad.com",
			address1 = "10th Building", address2 = "Block C", address3 = "Street 10", city = "NewYork", 
			country = "United States", state = "Alaska", postalCode = "12345", referral = "Walk In", 
			clientTimeZone, dFormat, propertyName="Groups Property", accountFirstName, accountLastName, 
			reportsTab, applicationTab, accountName = null, cardExpDate, guestFullName, promoCode,
			noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
			taskAssignee, taskStatus, randomString, adults = "2", children = "0", currency, roomClassAbb, includePayment,
			reservationNumber, cardHolderName, transactionType, amount;
	
	ArrayList<String> test_steps = new ArrayList<>();

	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> RoomClasses = new ArrayList<>();

	HashMap<String, Double> expChanges = new HashMap<>();
	HashMap<String, String> summaryViewReportBeforeAction = new HashMap<>();
	HashMap<String, String> summaryViewReportAfterAction = new HashMap<>();

	HashMap<String, String> reservationData = new HashMap<>();
	HashMap<String, String> paymentData = new HashMap<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@BeforeClass()
	public void getTimeZone() throws Exception{
		driver = getDriver();
		loginReportsV2ReservationV2(driver, test_steps);	
		nav.admin(driver);
		nav.navigateToClientinfo(driver);		
		admin.clickClientName(driver);
		admin.clickClientOption(driver);		
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		if (currency.contains("$") || currency.contains("USD")) {
			propertyCurrency = "$";
		}else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
			propertyCurrency = "£";
		}			
	
		propertyFirstDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
		clientTimeZone = admin.getClientTimeZone(driver, test_steps);
		dFormat = admin.getClientDateFormat(driver);
		
		switch (clientTimeZone) {
		case "(GMT-05:00) Eastern Time (US and Canada)":
			propertyTimeZone = "US/Eastern";
			break;			
		case "(GMT-06:00) Central Time (US and Canada)":
			propertyTimeZone = "US/Central";
			break;		
		case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
			propertyTimeZone = "Europe/London";
			break;
		default:
			break;
		}
		
		if (dFormat.equalsIgnoreCase("USA")) {
			propertyDateFormat = "MMM dd, YYYY";
		}else if (dFormat.equalsIgnoreCase("International")) {
			propertyDateFormat = "dd MMM, YYYY";
		}
		nav.ReservationV2_Backward(driver);
		cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateTransactionsReport(String Scenario, String DateRange, String CheckInDate, String CheckOutDate,		
			String sourceOfRes, String numberOfRooms, String Rateplan, String RoomClass, String marketSegment, 
			String paymentType, String cardNumber, String accountType) throws Throwable {

		test_name = Scenario;
		if (Scenario.contains("capture")) {
			includePayment = "Capture";
		} else if (Scenario.contains("authorize")) {
			includePayment = "Authorization";
		} else if (Scenario.contains("void") || Scenario.contains("cancel")) {
			includePayment = "Cancel (Void)";
		} else if (Scenario.contains("refund")) {
			includePayment = "Refund";
		} else if (Scenario.contains("cash")) {
			includePayment = "Cash";
		} else if (Scenario.contains("check")) {
			includePayment = "Check";
		} else if (Scenario.contains("gift certificate")) {
			includePayment = "Gift Certificate";
		} 
		
		test_description = "Validate Transactions Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Transactions Report";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");

		Utility.closeTabsExcept(driver, 1);
		if (!Utility.validateString(numberOfRooms)) {
			numberOfRooms = "1";
		}		
		
		if (sourceOfRes.equalsIgnoreCase("Associate Account") || sourceOfRes.equalsIgnoreCase("Account Reservation")) {
			accountName = "NaveenTest";
		}

		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}

			if ( !(Utility.validateDate(CheckInDate)) ) {
            	CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
				CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
    		}

			guestFirstName = ""; guestFirstName = "Auto";
			guestLastName = ""; guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
			guestFullName = ""; guestFullName = guestFirstName+" "+guestLastName;			
			cardHolderName = guestFullName+"01";
			expChanges = report.setDefaultDataForTransactionReport();
			if (sourceOfRes.equalsIgnoreCase("TapeChart")) {
				test_name = test_name+" from TapeChart";
				roomClassAbb = rc2.openRoomClassAndGetDataFromRoomTab(driver, test_steps, RoomClass).get("Abbreviation");
				nav.Reservation_Backward_3(driver);
			}

			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", test_name,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", test_name,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		String[] rc = RoomClass.split("\\|");
		if (rc.length>1) {
			for (int i = 0; i < rc.length; i++) {
				RoomClasses.add(rc[i]);
			}
		}else {
			RoomClasses.add(RoomClass);
		}

		try {

			nav.ReportsV2(driver, test_steps);
			report.navigateToTransactionsReport(driver, test_steps);
			report.selectDateRange(driver, CheckInDate, CheckOutDate, DateRange, test_steps);
			report.excludeZeroBalancePaymentMethodsForTransactionReport(driver, test_steps, true);
			report.includedPaymentMethodsForTransactionReport(driver, test_steps, includePayment);
			report.selectBreakOutDailyTotalForTransactionReport(driver, test_steps, false);			
			report.clickOnRunReport(driver, test_steps);
		
			summaryViewReportBeforeAction = report.getSummaryViewDataForTransactionReport(driver, test_steps);			
			app_logs.info("Before Summary view: "+summaryViewReportBeforeAction);
			test_steps.add("Before Summary view: "+summaryViewReportBeforeAction);
			
			allTabs = new ArrayList<>(driver.getWindowHandles());
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);
			
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		app_logs.info("Data: "+expChanges);

		try {
			Utility.switchTab(driver, applicationTab);
			
			if (!Utility.validateString(sourceOfRes)) {
				sourceOfRes = "From Reservations page";				
			}
			nav.Reservation_Backward_3(driver);
			
			if (Integer.parseInt(numberOfRooms)>1) {
				reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, adults, 
						children, Rateplan, null, RoomClass, roomClassAbb, "Mr.", guestFirstName+"|"+guestFirstName, 
						guestLastName+"|"+guestLastName, phoneNumber, phoneNumber, email, address1, address2, address3, 
						city, country, state, postalCode, false, marketSegment, referral, paymentType, cardNumber, 
						cardHolderName, cardExpDate, 0, null, false, noteType, noteSubject, 
						noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
						taskAssignee, taskStatus, accountName, accountType, accountFirstName, accountLastName,false,null);				
				
			} else {
				reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, adults, 
						children, Rateplan, null, RoomClass, roomClassAbb, "Mr.", guestFirstName, guestLastName, phoneNumber,
						phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
						referral, paymentType, cardNumber, cardHolderName, cardExpDate, 0, null, false, 
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
						taskAssignee, taskStatus, accountName, accountType, accountFirstName, accountLastName,false,null);
	
			}
			reservationNumber = reservationData.get("Reservation Number");
			
			if (Scenario.contains("capture") || Scenario.contains("authorize") ||
					Scenario.contains("cash") || Scenario.contains("check") ||
					Scenario.contains("refund") ) {
				test_steps.add("**************<b> TAKING PAYMENT FROM GUEST FOLIO </b>**************");
				guestFolio.clickOnFolioTab(driver, test_steps);
				paymentData = guestFolio.takePaymentFromGuestFolio(driver, test_steps, includePayment);
				amount = paymentData.get("Amount");
				transactionType = paymentData.get("Transaction Type");
			} else if (Scenario.contains("void") || Scenario.contains("cancel")) {
				test_steps.add("**************<b> CANCELLING RESERVATIONS FROM GUEST FOLIO </b>**************");
				 reservationV2Page.cancelReservation(driver, test_steps, true, "Cancel");
			} 
			
			if (Scenario.contains("refund") ) {
				test_steps.add("**************<b> REFUND PAYMENT FROM GUEST FOLIO </b>**************");
				reservationV2Page.switchDetailTab(driver, test_steps);
				guestFolio.refundPaymentFromGuestDetailsTab(driver, test_steps, reservationData.get("Room Charges"));
			}
			
			if (paymentData.get("Payment Type").equalsIgnoreCase("MC")) {
				expChanges.put("Credit Card", Double.parseDouble(amount));				
			} else if (paymentData.get("Payment Type").equalsIgnoreCase("Cash")) {
				expChanges.put("Cash", Double.parseDouble(amount));								
			} else if (paymentData.get("Payment Type").equalsIgnoreCase("Check")) {
				expChanges.put("Check", Double.parseDouble(amount));								
			}
			expChanges.put("Total", Double.parseDouble(amount));
			test_steps.add("Changes expected in report after reservation creation is : <b>"+expChanges+"</b>");

			Thread.sleep(240000); 
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			Utility.switchTab(driver, reportsTab);
			report.clickOnRunReport(driver);
		
			summaryViewReportAfterAction = report.getSummaryViewDataForTransactionReport(driver, test_steps);			
			app_logs.info("After Summary view: "+summaryViewReportBeforeAction);
			test_steps.add("After Summary view: "+summaryViewReportBeforeAction);
			
			test_steps.add("**************<b> COMPARING SUMMARY VIEW REPORT BEFORE AND AFTER </b>**************");
			report.compareTransactionReportSummaryView(test_steps, summaryViewReportBeforeAction, summaryViewReportAfterAction, expChanges);

			test_steps.add("**************<b> VERIFYING RESERVATION RECORD EXISTS IN DETAILED VIEW </b>**************");
			report.validateReservationAddedToDetailedViewForTransactionReport(driver, test_steps, CheckInDate, guestFullName, reservationNumber, 
					paymentType, transactionType, cardNumber, cardHolderName, amount);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
			
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("TransactionReport", excelReservationV2);
		// CP_CreateReservation
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
