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
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
 
public class ReportsV2FolioBalanceReportValidation extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	ReservationV2 reservationV2Page = new ReservationV2();
	FolioNew folioNew = new FolioNew();
	Groups group = new Groups();
	AdvGroups advgrp = new AdvGroups();
	Admin admin = new Admin();

	ArrayList<String> reservationNumbers = new ArrayList<>();
	String reservationNumber = null, guestFirstName = null, guestLastName, phoneNumber = "1234567890",  
	email = "innRoadTestEmail@innroad.com",	address1 = "10th Building", address2 = "Block C", address3 = "Street 10",
	city = "NewYork", country = "United States", state = "Alaska", postalCode = "12345", referral = "Walk In",
	paymentMethod = "MC", cardNumber = "5454545454545454", currency = null, clientTimeZone, dFormat, sourceOfRes, propertyName, 
	accountFirstName, accountLastName, reportsTab, applicationTab, currentDay, accountName = "AccountEQSVVD", roomNumber,
	cardExpDate, updateStartDate, updateEndDate, newRoomClassName, roomCharge , guestFullName,
	marketSegment = "Internet", adults = "1", children = "0",noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee, taskStatus ;
	ArrayList<String> roomCharges=new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();
	ArrayList<String> RoomNos=new ArrayList<>();
	

	ArrayList<String> reservationDates = new ArrayList<>();
	HashMap<String, Double> expChanges = new HashMap<>();

	HashMap<String, String> summaryViewReportBeforeReservation = new HashMap<>();
	HashMap<String, String> summaryViewReportAfterReservation = new HashMap<>();
	HashMap<String, String> summaryViewReportAfterActionReservation = new HashMap<>();
	HashMap<String, String> summaryViewReportBeforeAction = new HashMap<>();
	HashMap<String, String> summaryViewReportAfterAction = new HashMap<>();
	
	
	HashMap<String, HashMap<String, String>> detailedViewTotalReportBeforeActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewTotalReportAfterActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewTotalReportAfterActionForAllTheDates2 = new HashMap<>();

	HashMap<String, HashMap<String, String>> detailedViewIndividualReportBeforeActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewIndividualReportAfterActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewIndividualReportAfterActionForAllTheDates2 = new HashMap<>();
	HashMap<String, String> reservationData = new HashMap<>();
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
		loginReportsV2ReservationV2(driver);
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

	@Test(dataProvider = "getData",groups = "ReportsV2")
	public void validateFolioBalancesReport(String Scenario, String DateRange, String CheckInDate, String CheckOutDate,		
			String accountType, String associateAccount, String resStatus,String ResType,String Adult,String Child, String numberOfRooms,
			String Rateplan, String RoomClass, String isTaxExempt, String taxExemptID) throws Throwable {
		adults=Adult;
		children=Child;
		String roomClassAbb="KS";
		String guestSecondName="";
		
		if(ResType.equalsIgnoreCase("MRB")) {
			
			String date1="";
			String date2="";
		String[] rn1 = CheckInDate.split("\\|");			
			date1=rn1[0];
			date2=rn1[1];
		date1=ESTTimeZone.reformatDate(date1, "MM/dd/yyyy","dd/MM/yyyy");
		date2=ESTTimeZone.reformatDate(date2,  "MM/dd/yyyy","dd/MM/yyyy");
		
		CheckInDate=date1+"|"+date2;
		String[] rn2 = CheckOutDate.split("\\|");			
			date1=rn2[0];
			date2=rn2[1];
		date1=ESTTimeZone.reformatDate(date1, "MM/dd/yyyy","dd/MM/yyyy");
		date2=ESTTimeZone.reformatDate(date2,  "MM/dd/yyyy","dd/MM/yyyy");
		CheckOutDate=date1+"|"+date2;
		}
		
		test_name = Scenario;
		test_description = "Validate Folio Balances Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Folio Balances Report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String BlockName = "Block" + Utility.generateRandomStringWithoutNumbers();		
		int noOfRooms = 1;
		if(resStatus.equalsIgnoreCase("CheckIn")) {
			resStatus="In-House";
			
		}
		else if(resStatus.equalsIgnoreCase("CheckOut")) {
			resStatus="Departed";
		}
		if (Utility.validateString(numberOfRooms)) {
			if (!(numberOfRooms.split("\\|").length > 1)) {
				noOfRooms = Integer.parseInt(numberOfRooms);
			}else {
				String[] rn = numberOfRooms.split("\\|");			
				noOfRooms = 0;
				for (int i = 0; i < rn.length; i++) {
					noOfRooms = noOfRooms + Integer.parseInt(rn[i]);
				}
			}
		}else {
			numberOfRooms = "1";
		}
		
		if (Utility.validateString(accountType)) {
			accountName = "AccountEQSVVD";
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
			Utility.closeTabsExcept(driver, 1);
			loginReportsV2ReservationV2(driver);
			
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
			guestFirstName = ""; guestFirstName = "Auto";
			guestLastName = ""; guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
			guestFullName = ""; guestFullName = guestFirstName+" "+guestLastName;
			expChanges = report.setDefaultDataForRoomForeCastReport();
			currentDay = Utility.getCurrentDate("dd/MM/yyyy", TestCore.propertyTimeZone);
			additionalGuests = 0;
			roomNumber=null;
			isQuote = false;
			
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

		HashMap<String, Boolean> reservationTypesMap = new HashMap<>();
		try {
			nav.ReportsV2(driver, test_steps);
			report.navigateToFolioBalanceReport(driver, test_steps);
			report.selectEffectiveDate(driver, CheckInDate, DateRange, test_steps);
			report.clickClearAllButton(driver, test_steps);
			report.clickReservedCheckBox(driver, test_steps,"Reserved");
			reservationTypesMap=report.includeReservationTypeCheckBoxesStatus(driver);		
			report.clickOnRunReport(driver);			
			summaryViewReportBeforeReservation = folioNew.getFolioBalances(driver, reservationTypesMap);
			app_logs.info("Before Summary view: "+summaryViewReportBeforeReservation);
			ArrayList<String> beforeReservation= Utility.splitInputData(summaryViewReportBeforeReservation.get("Reserved")); 
			test_steps.add("Get Receivable Balance : "+beforeReservation.get(0));
			test_steps.add("Get Payable Balance : "+beforeReservation.get(1));
			test_steps.add("Get net Before : "+summaryViewReportBeforeReservation.get("Net Balance"));
			report.clickeditButton(driver,test_steps);
			report.clickReservedCheckBox(driver, test_steps,"Reserved");
			report.clickReservedCheckBox(driver, test_steps,resStatus);
			reservationTypesMap=report.includeReservationTypeCheckBoxesStatus(driver);
			report.clickOnRunReport(driver);	
			summaryViewReportBeforeAction = folioNew.getFolioBalances(driver, reservationTypesMap);
			ArrayList<String> beforAction= Utility.splitInputData(summaryViewReportBeforeAction.get(resStatus)); 
			
			test_steps.add("Get Receivable Balance Before "+resStatus+": "+beforAction.get(0));
			test_steps.add("Get Payable Balance : "+beforAction.get(1));
			test_steps.add("Get net Before : "+summaryViewReportBeforeAction.get("Net Balance"));
			allTabs = new ArrayList<>(driver.getWindowHandles());
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		isQuote = false;
		if (resStatus.equalsIgnoreCase("Quote")) {
			isQuote = true;
		}
		roomNumber = "";
		if (Scenario.contains("unassigned") || Scenario.contains("Unassigned")) {
			roomNumber = "Unassigned";
		}
		additionalGuests = 0;
		if (Scenario.contains("multiple guests") || Scenario.contains("multipleGuests") || Scenario.contains("additional guest")) {
			additionalGuests = 1;
		}
		
		
		Double totalRate = 0.0;
		app_logs.info("Data: "+expChanges);
		if (CheckInDate.split("\\|").length > 1) {
			numberOfNights = Utility.getNumberofDays(CheckInDate.split("\\|")[0], CheckOutDate.split("\\|")[0]);
		}else {
			numberOfNights = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		}
		
		app_logs.info("Number of nights: "+numberOfNights);
		Utility.switchTab(driver, applicationTab);
		if (ResType.equalsIgnoreCase("GroupPickRoomingList") || ResType.equalsIgnoreCase("GroupPickBlueIcon")) {
			test_steps.add("========== Creating Group account ==========");
			app_logs.info("========== Creating Group account ==========");
			nav.Reservation_Backward_3(driver);
			nav.groups(driver);
			
			if (!group.checkForGrouptExistsAndOpen(driver, test_steps, accountName, true)) {
				group.type_GroupName(driver, test, accountName, test_steps);
				group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
				group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber,
						address1, city, state, country, postalCode, test_steps);
				group.Billinginfo(driver);
				group.Save(driver, test_steps);				
			}
			app_logs.info("Romm Classes: "+RoomClass);
			app_logs.info("Romm Classes list: "+RoomClasses);
			
			BlockName = BlockName + Utility.GenerateRandomString15Digit();
			group.navigateRoomBlock(driver, test);			
			test_steps.add("========== Creating Group block ==========");
			app_logs.info("========== Creating Group block ==========");
			group.createNewBlockWithMultiRoomClasses(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, "1", RoomClass, test_steps);
			//test_steps.addAll(group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRooms, RoomClass));
			group.navigateRoomBlock(driver, test);													
			for (int i = 0; i < RoomClasses.size(); i++) {
				totalRate = totalRate + (Double.parseDouble(group.getTotalRateValue(driver, RoomClasses.get(i))) * 
						Double.parseDouble(group.getBlocked(driver, RoomClasses.get(i))));
			}
			//totalRate = group.getTotalRateValue(driver, RoomClass);
			app_logs.info("Total rate: "+totalRate);
			
			if (ResType.equalsIgnoreCase("GroupPickRoomingList")) {
				test_steps.addAll(group.roomingListClick(driver));
				advgrp.enter_RoomPickupdetails(driver, test_steps);
				reservationNumber = group.pickUp_getResNo(driver);
				app_logs.info("Group pick Reservation number: "+reservationNumber);			
				group.pickUpCloseClick(driver);
				
			}else if (ResType.equalsIgnoreCase("GroupPickBlueIcon")) {
				advgrp.clickBlueBookIcon(driver);
				reservationV2Page.clickNext(driver, test_steps);
				reservationV2Page.enter_GuestName(driver, test_steps, "Mr.", guestFirstName, guestLastName);
				
				reservationV2Page.clickBookNow(driver, test_steps);
				reservationV2Page.clickCloseReservationSavePopup(driver, test_steps);
				reservationNumbers.add(reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim());
				
			}	
			HashMap<String, String> data = new HashMap<>();
			roomCharge = reservationV2Page.getRoomCharge_TripSummary(driver);
            folioNew.clickOnPendingStatus(driver,resStatus,ResType);
			app_logs.info("Expected changes: "+expChanges);
			test_steps.add("========== Action completed at "+java.time.LocalTime.now()+"==========");
			app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		
			Wait.waitForGivenSeconds(240000);
			test_steps.add("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
			app_logs.info("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
			try {
				Utility.switchTab(driver, reportsTab);
				report.clickOnRunReport(driver);	
				summaryViewReportAfterAction=folioNew.getFolioBalances(driver, reservationTypesMap);
				report.clickeditButton(driver,test_steps);
				report.clickReservedCheckBox(driver, test_steps,resStatus);
				report.clickReservedCheckBox(driver, test_steps,"Reserved");
				
				reservationTypesMap=report.includeReservationTypeCheckBoxesStatus(driver);
				report.clickOnRunReport(driver);
				summaryViewReportAfterReservation = folioNew.getFolioBalances(driver, reservationTypesMap);
			app_logs.info("Before Summary view: "+summaryViewReportBeforeReservation);
				app_logs.info("After Summary view: "+summaryViewReportAfterReservation);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
				} else {
					Assert.assertTrue(false);
				}

			}
			
			try {
				test_steps.add("*******************<b> Validating summary view report</b> ****************************");
				report.compareRoomFolioReport(driver,test_steps, summaryViewReportBeforeReservation, summaryViewReportAfterReservation,summaryViewReportBeforeAction, summaryViewReportAfterAction, Double.parseDouble(roomCharge),"",reservationTypesMap,ResType);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}	
		
		try {
			Utility.switchTab(driver, applicationTab);
			
			
				if (ResType.equalsIgnoreCase("Associate Account")) {
					sourceOfRes = "Associate Account";
				} else if (ResType.equalsIgnoreCase("Account Reservation")) {
					sourceOfRes = "Account Reservation";
				
			}
				else {
				sourceOfRes = "From Reservations page";
			}
			
			if (ResType.equalsIgnoreCase("TapeChart")) {
				sourceOfRes = "TapeChart";
			}
			if (accountType.equalsIgnoreCase("Group")) {
				sourceOfRes = "Group";
			}
			boolean taxExempt = false;
			if (isTaxExempt.equalsIgnoreCase("Yes")) {
				taxExempt = true;
			}else {
				isTaxExempt = "No";
			}
			
		
			if (ResType.equalsIgnoreCase("Single") || ResType.equalsIgnoreCase("TapeChart")) {
				reservationV2Page.navigateToReservationPage(driver);
				reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, adults, 
						children, Rateplan, null, RoomClass, roomClassAbb, "Mr.", guestFirstName, guestLastName, phoneNumber,
						phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
						referral, paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, 
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
						taskAssignee, taskStatus, accountName, accountType, guestFirstName, guestLastName, taxExempt, taxExemptID);
				roomCharges=reservationV2Page.getRoomBalances(driver);
				reservationNumber = reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim();
				roomCharge = reservationV2Page.getRoomCharge_TripSummary(driver);
				RoomNos.add(reservationData.get("Room Number").toString());
				folioNew.clickOnPendingStatus(driver,resStatus,ResType);
				app_logs.info("Expected changes: "+expChanges);
				test_steps.add("========== Action completed at "+java.time.LocalTime.now()+"==========");
				app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		
				Wait.waitForGivenSeconds(240000);
				test_steps.add("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
				app_logs.info("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
				try {
					Utility.switchTab(driver, reportsTab);
					report.clickOnRunReport(driver);	
					summaryViewReportAfterAction=folioNew.getFolioBalances(driver, reservationTypesMap);
					
		
					report.clickeditButton(driver,test_steps);
					report.clickReservedCheckBox(driver, test_steps,resStatus);
					report.clickReservedCheckBox(driver, test_steps,"Reserved");	
					reservationTypesMap=report.includeReservationTypeCheckBoxesStatus(driver);
					report.clickOnRunReport(driver);
					test_steps.add("<b> Verify Detail View After Reservation<b>");
					folioNew.verifyReservation(driver, guestFullName,guestSecondName, reservationNumber, RoomClasses,RoomNos, CheckInDate, CheckOutDate, roomCharges, test_steps,ResType);
					summaryViewReportAfterReservation = folioNew.getFolioBalances(driver, reservationTypesMap);
					app_logs.info("Before Summary view: "+summaryViewReportBeforeReservation);
					app_logs.info("After Summary view: "+summaryViewReportAfterReservation);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
					} else {
						Assert.assertTrue(false);
					}

				}
				
				try {
					test_steps.add("*******************<b> Validating summary view report</b> ****************************");
					report.compareRoomFolioReport(driver,test_steps, summaryViewReportBeforeReservation, summaryViewReportAfterReservation,summaryViewReportBeforeAction, summaryViewReportAfterAction, Double.parseDouble(roomCharge),"",reservationTypesMap,ResType);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			} else if (ResType.equalsIgnoreCase("MRB")) {
				for (int i = 2; i <= RoomClass.split("\\|").length; i++) {
					guestFirstName = guestFirstName+"|"+Utility.generateRandomString();
					guestLastName = guestLastName+"|"+Utility.generateRandomString();
					if (!(adults.split("\\|").length > 1)) {
						adults = adults + "|2";
						children = children + "|1";
					}
				}		
				reservationV2Page.navigateToReservationPage(driver);
				reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, adults, 
						children, Rateplan, null, RoomClass, roomClassAbb, "Mr.",  guestFirstName, 
						guestLastName, phoneNumber, phoneNumber, email, address1, address2, address3, 
						city, country, state, postalCode, false, marketSegment, referral, paymentMethod, cardNumber, 
						guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, noteType, noteSubject, 
						noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
						taskAssignee, taskStatus, accountName, accountType, guestFirstName, guestLastName, taxExempt, taxExemptID);				
				driver.navigate().refresh();
				Wait.waitforPageLoad(2000, driver);
				roomCharge = reservationData.get("Room Charges");				
				reservationNumber = reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim();
				
				roomCharge = reservationV2Page.getRoomCharge_TripSummary(driver);
				driver.navigate().refresh();
				Wait.waitforPageLoad(2000, driver);
				guestSecondName=reservationV2Page.getGuestName(driver);
				guestSecondName=guestSecondName.replace("Mr. ", "");
				roomCharges=reservationV2Page.getRoomBalances(driver);

				RoomNos.add(reservationData.get("Room Number1").toString());
				RoomNos.add(reservationData.get("Room Number2").toString());
				
				folioNew.clickOnPendingStatus(driver,resStatus,ResType);
				app_logs.info("Expected changes: "+expChanges);
				test_steps.add("========== Action completed at "+java.time.LocalTime.now()+"==========");
				app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		
				Wait.waitForGivenSeconds(240000);
				test_steps.add("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
				app_logs.info("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
				try {
					Utility.switchTab(driver, reportsTab);
					report.clickOnRunReport(driver);	
					summaryViewReportAfterAction=folioNew.getFolioBalances(driver, reservationTypesMap);
					report.clickeditButton(driver,test_steps);
					report.clickReservedCheckBox(driver, test_steps,resStatus);
					report.clickReservedCheckBox(driver, test_steps,"Reserved");
					
					reservationTypesMap=report.includeReservationTypeCheckBoxesStatus(driver);
					report.clickOnRunReport(driver);
					
					test_steps.add("<b> Verify Reservation After Reservation<b>");
					folioNew.verifyReservation(driver, guestFullName,guestSecondName, reservationNumber, RoomClasses,RoomNos, CheckInDate, CheckOutDate, roomCharges, test_steps,ResType);
					
					summaryViewReportAfterReservation = folioNew.getFolioBalances(driver, reservationTypesMap);
					app_logs.info("Before Summary view: "+summaryViewReportBeforeReservation);
					app_logs.info("After Summary view: "+summaryViewReportAfterReservation);
					
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
					} else {
						Assert.assertTrue(false);
					}
				}				
				try {
					test_steps.add("*******************<b> Validating summary view report</b> ****************************");
					report.compareRoomFolioReport(driver,test_steps, summaryViewReportBeforeReservation, summaryViewReportAfterReservation,summaryViewReportBeforeAction, summaryViewReportAfterAction, Double.parseDouble(roomCharge),"",reservationTypesMap,ResType);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}	
			Utility.switchTab(driver, applicationTab);
			 if (resStatus.equalsIgnoreCase("CheckIn")||resStatus.equalsIgnoreCase("In-House")) {
				 test_steps.add("========== CheckIn Reservation ==========");
				 app_logs.info("========== CheckIn Reservation ==========");
				 reservationV2Page.changeReservationStatus(driver,test_steps,resStatus,true);
			 }else if (resStatus.equalsIgnoreCase("CheckOut")||resStatus.equalsIgnoreCase("Departed")) {
				 test_steps.add("========== CheckOut Reservation ==========");
				 app_logs.info("========== CheckOut Reservation ==========");
				 reservationV2Page.changeReservationStatus(driver,test_steps,resStatus,true);
				 if (numberOfNights > 1 && !DateRange.equalsIgnoreCase("Today")) {
						expChanges.put("Rooms Sold", (double)noOfRooms*(numberOfNights-1));
						expChanges.put("Rooms Availability", -(double)noOfRooms*(numberOfNights-1));
				}
					
			 }else if (resStatus.equalsIgnoreCase("Cancelled")) {
				 test_steps.add("========== Cancelled Reservation ==========");
					app_logs.info("========== Cancelled Reservation  ==========");
					
				 test_steps.addAll(reservationV2Page.changingReservationStatus(driver,resStatus));
				 if (accountType.equalsIgnoreCase("Group")) {
					 expChanges.put("Group Revenue", 0.0);
				 }

			 }else if (resStatus.equalsIgnoreCase("No Show")) {
				 test_steps.add("========== No Show Reservation ==========");
				app_logs.info("========== No Show Reservation ==========");
					
				 test_steps.addAll(reservationV2Page.changingReservationStatus(driver,resStatus));
				 if (accountType.equalsIgnoreCase("Group")) {
					 expChanges.put("Group Revenue", 0.0);
				 }
			 }
			 else if (resStatus.equalsIgnoreCase("Confirmed")) {
				 test_steps.add("========== Confirmed Reservation ==========");
					app_logs.info("========== Confirmed Reservation ==========");
				 test_steps.addAll(reservationV2Page.changingReservationStatus(driver,resStatus));
			 } else if (resStatus.equalsIgnoreCase("Guaranteed")) {
				 test_steps.add("========== Guaranteed Reservation ==========");
					app_logs.info("========== Guaranteed Reservation ==========");
				 test_steps.addAll(reservationV2Page.changingReservationStatus(driver,resStatus));
			 } else if (resStatus.equalsIgnoreCase("On Hold")) {
				 test_steps.add("========== OnHold Reservation ==========");
				 app_logs.info("========== OnHold Reservation ==========");
				 test_steps.addAll(reservationV2Page.changingReservationStatus(driver,resStatus));
			 }
			 
			 
		} catch (Exception e) {
			
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		if(resStatus !="") {
		
		app_logs.info("Expected changes: "+expChanges);
		test_steps.add("========== Action completed at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		

		Wait.waitForGivenSeconds(240000);
		test_steps.add("========== Validating Reports "+resStatus+" after 2 minutes at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Validating Reports "+resStatus+" after 2 minutes at "+java.time.LocalTime.now()+"==========");
		try {
			if(!ResType.equalsIgnoreCase("MRB")) 
			{
				folioNew.clickOnPendingStatus(driver,resStatus,ResType);
			}
			Wait.wait3Second();
			Utility.switchTab(driver, reportsTab);
			
			report.clickOnRunReport(driver);	
			Wait.wait3Second();
			summaryViewReportAfterActionReservation = folioNew.getFolioBalances(driver, reservationTypesMap);
			report.clickeditButton(driver,test_steps);
			report.clickReservedCheckBox(driver, test_steps,"Reserved");
			report.clickReservedCheckBox(driver, test_steps,resStatus);
			reservationTypesMap=report.includeReservationTypeCheckBoxesStatus(driver);
			
			report.clickOnRunReport(driver);
			test_steps.add("<b> Verify Detail View After <b>"+ "<b>"+resStatus+"<b>");
			folioNew.verifyReservation(driver, guestFullName,guestSecondName, reservationNumber, RoomClasses,RoomNos, CheckInDate, CheckOutDate, roomCharges, test_steps,ResType);
			
			summaryViewReportAfterAction = folioNew.getFolioBalances(driver, reservationTypesMap);
			ArrayList<String> beforAction= Utility.splitInputData(summaryViewReportAfterAction.get(resStatus)); 
			
			test_steps.add("Get Receivable Balance Before "+resStatus+": "+beforAction.get(0));
			test_steps.add("Get Payable Balance : "+beforAction.get(1));
			test_steps.add("Get net Before : "+summaryViewReportBeforeAction.get("Net Balance"));
			
			app_logs.info("After Reservation Summary view: "+summaryViewReportAfterReservation);
			app_logs.info("After "+resStatus+" Summary view: "+summaryViewReportAfterActionReservation);
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		
		try {
			test_steps.add("*******************<b> Validating summary after "+resStatus+"</b> ****************************");
			report.compareRoomFolioReport(driver,test_steps, summaryViewReportAfterReservation, summaryViewReportAfterActionReservation,summaryViewReportBeforeAction, summaryViewReportAfterAction,  Double.parseDouble(roomCharge),resStatus,reservationTypesMap,ResType);				

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		
		try {
			if (Scenario.contains("Super User") || Scenario.contains("super user")) {
				Utility.switchTab(driver, applicationTab);
				admin.logout(driver);
				loginReportsV2(driver);
			}
		}catch (Exception e) {
			app_logs.info(e.toString());
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		
		}


	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("FolioReportV2", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
	//	driver.quit();
	}

}
