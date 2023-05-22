package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.By;
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
import com.innroad.inncenter.properties.OR_Reports;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2RoomForecastReportValidation extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	ReportsV2 report = new ReportsV2();
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
	NewRoomClassesV2 rc2 = new NewRoomClassesV2();
	RoomClass rc = new RoomClass();
	RoomMaintenance rm = new RoomMaintenance();
	RatesGrid ratesGrid = new RatesGrid();	
	

	String reservationNumber = null, guestFirstName = null, guestLastName, phoneNumber = "1234567890",  
	email = "innRoadTestEmail@innroad.com",	address1 = "10th Building", address2 = "Block C", address3 = "Street 10",
	city = "NewYork", country = "United States", state = "Alaska", postalCode = "12345", referral = "Walk In",
	paymentMethod = "MC", cardNumber = "5454545454545454", currency = null, clientTimeZone, dFormat, sourceOfRes, propertyName, 
	accountFirstName, accountLastName, reportsTab, applicationTab, currentDay, accountName = "AccountEQSVVD", roomNumber,
	cardExpDate, updateStartDate, updateEndDate, newRoomClassName, roomCharge , guestFullName;
	
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();

	ArrayList<String> reservationDates = new ArrayList<>();
	HashMap<String, Double> expChanges = new HashMap<>();
	HashMap<String, String> summaryViewReportBeforeAction = new HashMap<>();
	HashMap<String, String> summaryViewReportAfterAction = new HashMap<>();
	HashMap<String, String> summaryViewReportAfterAction2 = new HashMap<>();
	
	HashMap<String, HashMap<String, String>> detailedViewTotalReportBeforeActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewTotalReportAfterActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewTotalReportAfterActionForAllTheDates2 = new HashMap<>();

	HashMap<String, HashMap<String, String>> detailedViewIndividualReportBeforeActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewIndividualReportAfterActionForAllTheDates = new HashMap<>();
	HashMap<String, HashMap<String, String>> detailedViewIndividualReportAfterActionForAllTheDates2 = new HashMap<>();

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
		loginReportsV2(driver);		
		nav.admin(driver);
		nav.navigateToClientinfo(driver);		
		admin.clickClientName(driver);
		admin.clickClientOption(driver);		
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		app_logs.info("Currency: "+currency);
		if (currency.equalsIgnoreCase("USD ( $ )") || currency.equalsIgnoreCase("USD ( $ ) ")) {
			TestCore.propertyCurrency = "$";
			propertyCurrency = "$";
		}else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
			TestCore.propertyCurrency = "£";
		}			
		app_logs.info("Currency: "+TestCore.propertyCurrency);
		app_logs.info("Currency: "+propertyCurrency);
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
		
		nav.Reservation_Backward(driver);
		cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateLedgerBalancesReport(String Scenario, String DateRange, String CheckInDate, String CheckOutDate,		
			String breakOutBy, String accountType, String associateAccount, String resStatus, String ResType, String numberOfRooms,
			String Rateplan, String RoomClass, String marketSegment, String adults, String children, String roomsAction) throws Throwable {


		test_name = Scenario;
		test_description = "Validate LedgerBalances Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Ledger Balances Report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Utility.closeTabsExcept(driver, 1);
		loginReportsV2(driver);	
		String BlockName = "Block" + Utility.generateRandomStringWithoutNumbers();		
		int noOfRooms = 1;
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
		
		if (Scenario.contains("Super User") || Scenario.contains("super user")) {
			admin.logout(driver);
			loginSuperUser(driver);
			prop.selectPropertyWithSuperUser(driver, propertyName);
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

		try {

			nav.ReportsV2(driver);
			report.navigateToRoomForecastReport(driver);
			report.selectDateRange(driver, CheckInDate, CheckOutDate, DateRange, test_steps);
			report.selectBreakOutByOptionRoomForecast(driver, breakOutBy, test_steps);
			report.selectIncludePerformanceMetrics(driver, "Yes", test_steps);
			report.selectIncludeGroupReservations(driver, "Yes", test_steps);
			report.clickOnRunReport(driver);
		
			summaryViewReportBeforeAction = report.getSummaryViewData(driver);
			reservationDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, CheckOutDate);
			for (String day : reservationDates) {
				detailedViewTotalReportBeforeActionForAllTheDates.put(day, 
						report.getDetailedViewTotalData(driver, day));
			}
			if (breakOutBy.equalsIgnoreCase("Room Class")) {
				for (String day : reservationDates) {
					detailedViewIndividualReportBeforeActionForAllTheDates.put(day, 
							report.getDetailedViewRoomClassOrMarketSegmantData(driver, RoomClasses.get(0), day));
				}
			} else if (breakOutBy.equalsIgnoreCase("Market Segment")) {
				for (String day : reservationDates) {
					detailedViewIndividualReportBeforeActionForAllTheDates.put(day, 
							report.getDetailedViewRoomClassOrMarketSegmantData(driver, marketSegment, day));
				}
			}
			
			app_logs.info("Before Summary view: "+summaryViewReportBeforeAction);
			app_logs.info("Before Detailed view: "+detailedViewTotalReportBeforeActionForAllTheDates);
			app_logs.info("Before Detailed view: "+detailedViewIndividualReportBeforeActionForAllTheDates);
			
			allTabs = new ArrayList<>(driver.getWindowHandles());
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);
			
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
		
		//Action
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
		if (accountType.equalsIgnoreCase("Group") && ResType.contains("Block")) {
			test_steps.add("========== Creating Group account ==========");
			app_logs.info("========== Creating Group account ==========");
			nav.Reservation_Backward_3(driver);
			nav.groups(driver);
			// group.click_NewAccount(driver, test, test_steps);
			
			if (!group.checkForGrouptExistsAndOpen(driver, test_steps, accountName, true)) {
				group.type_GroupName(driver, test, accountName, test_steps);
				group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
				group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber,
						address1, city, state, country, postalCode, test_steps);
				group.Billinginfo(driver);
				group.Save(driver, test_steps);
			}
			if (ResType.equalsIgnoreCase("GroupBlock") || ResType.equalsIgnoreCase("GroupPickRoomingList") || ResType.equalsIgnoreCase("GroupPickBlueIcon") || ResType.equalsIgnoreCase("GroupBlockQuote")) {
				
				app_logs.info("Romm Classes: "+RoomClass);
				app_logs.info("Romm Classes list: "+RoomClasses);
				
				BlockName = BlockName + Utility.GenerateRandomString15Digit();
				group.navigateRoomBlock(driver, test);
				if (ResType.equalsIgnoreCase("GroupBlock") || ResType.equalsIgnoreCase("GroupPickRoomingList") ||ResType.equalsIgnoreCase("GroupPickBlueIcon")) {
					test_steps.add("========== Creating Group block ==========");
					app_logs.info("========== Creating Group block ==========");
					group.createNewBlockWithMultiRoomClasses(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRooms, RoomClass, test_steps);
					//test_steps.addAll(group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRooms, RoomClass));
					group.navigateRoomBlock(driver, test);										
					
					for (int i = 0; i < RoomClasses.size(); i++) {
						totalRate = totalRate + (Double.parseDouble(group.getTotalRateValue(driver, RoomClasses.get(i))) * 
								Double.parseDouble(group.getBlocked(driver, RoomClasses.get(i))));
					}
					//totalRate = group.getTotalRateValue(driver, RoomClass);
					app_logs.info("Total rate: "+totalRate);
					
					expChanges.put("Group Blocks", (double)numberOfNights*noOfRooms);
					expChanges.put("Group Revenue", totalRate);
					expChanges.put("Performance Revenue", totalRate);
					expChanges.put("Rooms Sold", (double)numberOfNights*noOfRooms);
					expChanges.put("Rooms Availability", -(double)numberOfNights*noOfRooms);
					expChanges.put("Guests Count", (double)numberOfNights*noOfRooms);
					expChanges.put("Guests Arrival", (double)numberOfNights*noOfRooms);
				}else if (ResType.equalsIgnoreCase("GroupBlockQuote")) {
					test_steps.add("========== Creating Group block Quote ==========");
					app_logs.info("========== Creating Group block Quote ==========");
					group.createNewBlockQuote(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRooms, RoomClass, test_steps);
				}
			}
			
			if (ResType.equalsIgnoreCase("GroupPickRoomingList")) {
				test_steps.addAll(group.roomingListClick(driver));
				advgrp.enter_RoomPickupdetails(driver, test_steps);
				reservationNumber = group.pickUp_getResNo(driver);
				app_logs.info("Group pick Reservation number: "+reservationNumber);			
				group.pickUpCloseClick(driver);
				
				expChanges.put("Group Pick Ups", (double)noOfRooms);
			}else if (ResType.equalsIgnoreCase("GroupPickBlueIcon")) {
				advgrp.clickBlueBookIcon(driver);
				
				reservationPage.clickNext(driver, test_steps);
				reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, "Mr", guestFirstName, guestLastName, config.getProperty("flagOff"));	
				reservationPage.clickBookNow(driver, test_steps);
				reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);		
				reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
				reservationPage.click_Folio(driver, test_steps);
				
				expChanges.put("Group Pick Ups", (double)1);
			}
			
			if (ResType.equalsIgnoreCase("GroupReservation")) {
				group.click_GroupNewReservation(driver, test_steps);
				report.singleRoomReservation(driver, CheckInDate, CheckOutDate, adults, children, Rateplan, RoomClass, "Mr.", guestFirstName, guestLastName, phoneNumber, phoneNumber, email, "", "", address1, city, country, state, postalCode, "No", "", "Cash", "", "test", "10/25", "", "No", marketSegment, referral, resStatus, test_steps);
				String roomCharge = reservationPage.getRoomCharge_TripSummary(driver);
				//expChanges.put("Group Revenue", totalRate);
				expChanges.put("Performance Revenue", Double.parseDouble(roomCharge));
				expChanges.put("Rooms Sold", (double)numberOfNights*noOfRooms);
				expChanges.put("Rooms Availability", -(double)numberOfNights*noOfRooms);
				expChanges.put("Guests Count", Double.parseDouble(adults)+Double.parseDouble(children));
				expChanges.put("Guests Arrival", Double.parseDouble(adults)+Double.parseDouble(children));
			}
						
		}	
		

		try {
			Utility.switchTab(driver, applicationTab);
			
			if (Utility.validateString(accountType)) {
				if (associateAccount.equalsIgnoreCase("Associate Account")) {
					sourceOfRes = "Associate Account";
				} else if (associateAccount.equalsIgnoreCase("Account Reservation")) {
					sourceOfRes = "Account Reservation";
				}
			}else {
				sourceOfRes = "From Reservations page";
			}
			
			if (ResType.equalsIgnoreCase("TapeChart")) {
				sourceOfRes = "TapeChart";
			}
			if (accountType.equalsIgnoreCase("Group")) {
				sourceOfRes = "Group";
			}
			 
			
			if (ResType.equalsIgnoreCase("Single") || ResType.equalsIgnoreCase("TapeChart")) {				
				reservationPage.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, 
						adults, children, Rateplan, null, RoomClass, "Mr.", guestFirstName, guestLastName, phoneNumber, 
						phoneNumber, email, address1, address2, address3, city, country, state, postalCode, 
						"No", marketSegment, referral, paymentMethod, cardNumber, guestFullName, 
						cardExpDate, additionalGuests, roomNumber, isQuote, 
						accountName, accountType, accountFirstName, accountLastName);	
				roomCharge = reservationPage.getRoomCharge_TripSummary(driver);

			} else if (ResType.equalsIgnoreCase("MRB")) {
				reservationPage.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, 
						adults, children, Rateplan, null, RoomClass, "Mr.", guestFirstName+"|"+guestFirstName, 
						guestLastName+"|"+guestLastName, phoneNumber, phoneNumber, email, address1, address2, 
						address3, city, country, state, postalCode, "No", marketSegment, referral, paymentMethod, cardNumber, 
						guestFullName, cardExpDate, additionalGuests, 
						roomNumber, isQuote, accountName, accountType, "Test", "Account");
				
				roomCharge = reservationPage.getRoomCharge_TripSummary(driver);
				
			} else if (ResType.equalsIgnoreCase("Copy")) {				
				reservationPage.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, 
						adults, children, Rateplan, null, RoomClass, "Mr.", guestFirstName, guestLastName, phoneNumber, 
						phoneNumber, email, address1, address2, address3, city, country, state, postalCode, 
						"No", null, referral, paymentMethod, cardNumber, guestFullName, 
						cardExpDate, additionalGuests, roomNumber, isQuote, 
						accountName, accountType, accountFirstName, accountLastName);
				
				reservationPage.copyReservation(driver, RoomClasses, guestFullName, "Copy", guestLastName);
								
			}
			
			if (Scenario.contains("Quote Book") || Scenario.contains("QuoteBook") || Scenario.contains("book")) {
				reservationPage.bookReservationFromQuote(driver, test_steps);
			}
			int noOfDays = numberOfNights + 1;
			if (!Utility.validateString(roomsAction)) {
				if (!isQuote || Scenario.contains("Quote Book") || Scenario.contains("book")) {
					if (ResType.equalsIgnoreCase("Copy")) {
						if (DateRange.equalsIgnoreCase("Today")) {
							expChanges.put("Performance Revenue", 2*(Double.parseDouble(roomCharge)/numberOfNights));
							expChanges.put("Rooms Sold", 2*((double)noOfRooms));
							expChanges.put("Rooms Availability", -2*((double)noOfRooms));
							double guestCount = 0.0;
							if (adults.split("\\|").length > 1) {
								for (int i = 0; i < adults.split("\\|").length; i++) {
									guestCount = guestCount + Double.parseDouble(adults.split("\\|")[i]) + Double.parseDouble(children.split("\\|")[i]);
								}
							}else {
								guestCount = Double.parseDouble(adults) + Double.parseDouble(children);
							}
							expChanges.put("Guests Count", 2*guestCount);
							expChanges.put("Guests Arrival", 2*((double)noOfRooms));
							if (accountType.equalsIgnoreCase("Group")) {
								expChanges.put("Group Revenue", 2*(Double.parseDouble(roomCharge)/numberOfNights));
							}
						}else {
							expChanges.put("Performance Revenue", 2*(Double.parseDouble(roomCharge)));
							expChanges.put("Rooms Sold", 2*((double)noOfRooms*numberOfNights));
							expChanges.put("Rooms Availability", -2*((double)noOfRooms*numberOfNights));
							double guestCount = 0.0;
							if (adults.split("\\|").length > 1) {
								for (int i = 0; i < adults.split("\\|").length; i++) {
									guestCount = guestCount + Double.parseDouble(adults.split("\\|")[i]) + Double.parseDouble(children.split("\\|")[i]);
								}
							}else {
								guestCount = Double.parseDouble(adults) + Double.parseDouble(children);
							}
							expChanges.put("Guests Count", 2*(guestCount*numberOfNights));
							expChanges.put("Guests Arrival", 2*((double)noOfRooms));
							expChanges.put("Guests Depature", 2*((double)noOfRooms));
							expChanges.put("Guests Stay Over", 2*((double)noOfRooms*(numberOfNights-1)));
							if (accountType.equalsIgnoreCase("Group")) {
								expChanges.put("Group Revenue", 2*(Double.parseDouble(roomCharge)));
							}
						}

					}else if (!ResType.contains("Group")) {
						if (DateRange.equalsIgnoreCase("Today")) {
							expChanges.put("Performance Revenue", Double.parseDouble(roomCharge)/numberOfNights);
							expChanges.put("Rooms Sold", (double)noOfRooms);
							expChanges.put("Rooms Availability", -(double)noOfRooms);
							double guestCount = 0.0;
							if (adults.split("\\|").length > 1) {
								for (int i = 0; i < adults.split("\\|").length; i++) {
									guestCount = guestCount + Double.parseDouble(adults.split("\\|")[i]) + Double.parseDouble(children.split("\\|")[i]);
								}
							}else {
								guestCount = Double.parseDouble(adults) + Double.parseDouble(children);
							}
							expChanges.put("Guests Count", guestCount);
							expChanges.put("Guests Arrival", (double)noOfRooms);
							if (accountType.equalsIgnoreCase("Group")) {
								expChanges.put("Group Revenue", Double.parseDouble(roomCharge)/numberOfNights);
							}
						}else {
							expChanges.put("Performance Revenue", Double.parseDouble(roomCharge));
							expChanges.put("Rooms Sold", (double)noOfRooms*numberOfNights);
							expChanges.put("Rooms Availability", -(double)noOfRooms*numberOfNights);
							double guestCount = 0.0;
							if (adults.split("\\|").length > 1) {
								for (int i = 0; i < adults.split("\\|").length; i++) {
									guestCount = guestCount + Double.parseDouble(adults.split("\\|")[i]) + Double.parseDouble(children.split("\\|")[i]);
								}
							}else {
								guestCount = Double.parseDouble(adults) + Double.parseDouble(children);
							}
							expChanges.put("Guests Count", guestCount*numberOfNights);
							expChanges.put("Guests Arrival", (double)noOfRooms);
							expChanges.put("Guests Depature", (double)noOfRooms);
							expChanges.put("Guests Stay Over", (double)noOfRooms*(numberOfNights-1));
							if (accountType.equalsIgnoreCase("Group")) {
								expChanges.put("Group Revenue", Double.parseDouble(roomCharge));
							}
						}

					}
				}
			}
							

			
			 if (resStatus.equalsIgnoreCase("CheckIn")) {
				reservationPage.inHouseReservation(driver);
				//reservationPage.checkInGuestFromGuestProfileWithOrWithoutPayment(driver, test_steps, guestFullName);
			 }else if (resStatus.equalsIgnoreCase("CheckOut")) {
				 reservationPage.departedReservation(driver);
				 //reservationPage.checkOutGuestFromGuestProfileWithOrWithoutPayment(driver, test_steps, guestFullName);
				 if (numberOfNights > 1 && !DateRange.equalsIgnoreCase("Today")) {
						expChanges.put("Rooms Sold", (double)noOfRooms*(numberOfNights-1));
						expChanges.put("Rooms Availability", -(double)noOfRooms*(numberOfNights-1));
				}

					
			 }else if (resStatus.equalsIgnoreCase("Cancelled")) {
				 reservationPage.cancelReservationFromGuestDetails(driver, test_steps, guestFullName, "None");
				 expChanges.put("Performance Revenue", 0.0);
				 expChanges.put("Rooms Sold", 0.0);
				 expChanges.put("Rooms Availability", 0.0);
				 expChanges.put("Guests Count", 0.0);
				 expChanges.put("Guests Arrival", 0.0);
				 if (accountType.equalsIgnoreCase("Group")) {
					 expChanges.put("Group Revenue", 0.0);
				 }

			 }else if (resStatus.equalsIgnoreCase("NoShow")) {
				 reservationPage.noShowReservationFromGuestDetails(driver, test_steps, guestFullName);
				 expChanges.put("Performance Revenue", 0.0);
				 expChanges.put("Rooms Sold", 0.0);
				 expChanges.put("Rooms Availability", 0.0);
				 expChanges.put("Guests Count", 0.0);
				 expChanges.put("Guests Arrival", 0.0);
				 if (accountType.equalsIgnoreCase("Group")) {
					 expChanges.put("Group Revenue", 0.0);
				 }
			 } else if ( (Scenario.contains("extend")) || (Scenario.contains("reduce")) ||
					 (Scenario.contains("update") && Scenario.contains("dates")) ) {
				 if (ResType.equalsIgnoreCase("Single")) {
					 reservationPage.updateReservationFromStayInfo(driver, test_steps, guestFullName, Scenario, updateDates, null);					
				} else if (ResType.equalsIgnoreCase("MRB")) {
					 reservationPage.updateReservationFromStayInfoForMRB(driver, test_steps, guestNames, Scenario, updateDates, null, null, 0);										
				}
			 } else if (resStatus.equalsIgnoreCase("MRBPrimaryChekIn")) {
				reservationPage.mrbPrimaryCheckin(driver);
			 } else if (resStatus.equalsIgnoreCase("MRBPrimaryCheckOut")) {
				reservationPage.mrbPrimaryCheckin(driver);
				reservationPage.mrbPrimaryCheckOut(driver);
			 } else if (resStatus.equalsIgnoreCase("MRBSecondaryChekIn")) {
				reservationPage.mrbSecondaryCheckin(driver);
			 } else if (resStatus.equalsIgnoreCase("MRBSecondaryCheckOut")) {
				reservationPage.mrbSecondaryCheckin(driver);
				Wait.wait5Second();
				reservationPage.mrbSecondaryCheckOut(driver);
			 } else if (resStatus.equalsIgnoreCase("MRBSecondaryCancel")) {
				reservationPage.mrbSecondaryCancel(driver);				
				expChanges.put("Rooms Sold", ((double)noOfRooms - 1.0 ));
				expChanges.put("Rooms Availability", -((double)noOfRooms - 1.0 ));
			 } else if (resStatus.equalsIgnoreCase("Confirmed")) {
				reservationPage.confirmReservation(driver);
			 } else if (resStatus.equalsIgnoreCase("Guaranteed")) {
				reservationPage.guaranteeReservation(driver);
			 } else if (resStatus.equalsIgnoreCase("OnHold")) {
				reservationPage.onHoldReservation(driver);
			 }
			 
			 if (Scenario.contains("rollback")) {
				reservationPage.rollBackReservation(driver, test_steps, guestFullName, "RESERVED");
				if (resStatus.equalsIgnoreCase("Cancelled") || resStatus.equalsIgnoreCase("NoShow") || resStatus.equalsIgnoreCase("CheckOut")) {
					if (DateRange.equalsIgnoreCase("Today")) {
						expChanges.put("Performance Revenue", Double.parseDouble(roomCharge)/numberOfNights);
						expChanges.put("Rooms Sold", (double)noOfRooms);
						expChanges.put("Rooms Availability", -(double)noOfRooms);
						double guestCount = 0.0;
						if (adults.split("\\|").length > 1) {
							for (int i = 0; i < adults.split("\\|").length; i++) {
								guestCount = guestCount + Double.parseDouble(adults.split("\\|")[i]) + Double.parseDouble(children.split("\\|")[i]);
							}
						}else {
							guestCount = Double.parseDouble(adults) + Double.parseDouble(children);
						}
						expChanges.put("Guests Count", guestCount);
						expChanges.put("Guests Arrival", (double)noOfRooms);
						if (accountType.equalsIgnoreCase("Group")) {
							expChanges.put("Group Revenue", Double.parseDouble(roomCharge)/numberOfNights);
						}
					}else {
						expChanges.put("Performance Revenue", Double.parseDouble(roomCharge));
						expChanges.put("Rooms Sold", (double)noOfRooms*numberOfNights);
						expChanges.put("Rooms Availability", -(double)noOfRooms*numberOfNights);
						double guestCount = 0.0;
						if (adults.split("\\|").length > 1) {
							for (int i = 0; i < adults.split("\\|").length; i++) {
								guestCount = guestCount + Double.parseDouble(adults.split("\\|")[i]) + Double.parseDouble(children.split("\\|")[i]);
							}
						}else {
							guestCount = Double.parseDouble(adults) + Double.parseDouble(children);
						}
						expChanges.put("Guests Count", guestCount*numberOfNights);
						expChanges.put("Guests Arrival", (double)noOfRooms);
						expChanges.put("Guests Depature", (double)noOfRooms);
						expChanges.put("Guests Stay Over", (double)noOfRooms*(numberOfNights-1));
						if (accountType.equalsIgnoreCase("Group")) {
							expChanges.put("Group Revenue", Double.parseDouble(roomCharge));
						}
					}
				}
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
		
		
		
		String subjectOOO = Utility.fourDigitgenerateRandomString();
		newRoomClassName = "Test"+Utility.generateRandomNumber();
		roomQuantity = Utility.getRandomNumber(3, 4); 
		if (Utility.validateString(roomsAction)) {
			app_logs.info("Rooms quantity: "+roomQuantity);
			if (roomsAction.equalsIgnoreCase("NewRoomClass")) {
				nav.setup(driver);
				nav.roomClass(driver);
				rc2.createRoomClassV2(driver, test_steps, newRoomClassName, "TRC", "3", "5", ""+roomQuantity+"");
				expChanges.put("Rooms Total", (double)roomQuantity);
				expChanges.put("Rooms Bookable", (double)roomQuantity);
				expChanges.put("Rooms Availability", (double)roomQuantity);
				
			}else if (roomsAction.equalsIgnoreCase("OutOfOrder") || roomsAction.equalsIgnoreCase("OutOfOrderIncrease") || roomsAction.equalsIgnoreCase("OutOfOrderDecrease") || roomsAction.equalsIgnoreCase("OutOfOrderDelete")) {
				nav.Reservation_Backward_3(driver);
				nav.clickOnGuestServices(driver, test_steps);
				nav.RoomMaintenance(driver);
				//rm.CreateNewRoomOut(driver, "", subjectOOO, RoomClass);
				//rm.CreateNewRoomOut(driver, "1", subjectOOO);
				rm.createNewRoomOutOfOrder(driver, "1", subjectOOO, roomQuantity);
				expChanges.put("Rooms Out of Order", (double)roomQuantity);
				expChanges.put("Rooms Bookable", -(double)roomQuantity);
				expChanges.put("Rooms Availability", -(double)roomQuantity);
			}else if (roomsAction.equalsIgnoreCase("BlackOut")) {
				nav.inventory_Backward_1(driver);
				nav.Rates_Grid(driver);
				ratesGrid.clickOnAvailability(driver);
				ratesGrid.clickForRateGridCalender(driver,test_steps);
				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

				ratesGrid.clickExpendRooClass(driver, test_steps, RoomClass);
				ratesGrid.blockoutRoomClassOrAvilable(driver, test_steps, RoomClass, 1, "innCenter","Blockout");
				//ratesGrid.blockoutRoomClassOrAvilable(driver, test_steps, RoomClass, 1, "Incenter", "BlockOut");
			}else if (roomsAction.equalsIgnoreCase("BulkUpdate")) {
				nav.inventory_Backward_1(driver);
				nav.Rates_Grid(driver);
				ratesGrid.clickOnAvailability(driver);
				ratesGrid.clickForRateGridCalender(driver,test_steps);
				ratesGrid.clickOnBulkUpdate(driver);
				ratesGrid.selectBulkUpdateOption(driver, "Availability");
				ratesGrid.startDate(driver, CheckInDate);
				ratesGrid.endDate(driver, CheckOutDate);
				//ratesGrid.clickTotalOccupancy(driver, "Yes");
				
//				String[] roomClassesArray = RoomClass.split(",");
//				for (String roomClasName : roomClassesArray) {
//					roomClasName = roomClasName.trim();
//					app_logs.info(roomClasName.trim());
//					ratesGrid.selectRoomClass(driver, roomClasName);
//				}
				ratesGrid.selectRoomClass(driver, RoomClass);
				ratesGrid.selectUpdateAvailability(driver, "innCenter", "Blackout");
				ratesGrid.clickUpdateButton(driver);
				ratesGrid.clickYesUpdateButton(driver);
				
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
				
		app_logs.info("Expected changes: "+expChanges);
		test_steps.add("========== Action completed at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		
		Wait.wait60Second();
		Wait.wait60Second();
		Wait.wait60Second();
		test_steps.add("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		
		try {
			Utility.switchTab(driver, reportsTab);
			report.clickOnRunReport(driver);
			summaryViewReportAfterAction = report.getSummaryViewData(driver);
			reservationDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, CheckOutDate);
			for (String day : reservationDates) {
				detailedViewTotalReportAfterActionForAllTheDates.put(day, 
						report.getDetailedViewTotalData(driver, day));
			}
			if (breakOutBy.equalsIgnoreCase("Room Class")) {
				for (String day : reservationDates) {
					detailedViewIndividualReportAfterActionForAllTheDates.put(day, 
							report.getDetailedViewRoomClassOrMarketSegmantData(driver, RoomClasses.get(0), day));
				}
			} else if (breakOutBy.equalsIgnoreCase("Market Segment")) {
				for (String day : reservationDates) {
					detailedViewIndividualReportAfterActionForAllTheDates.put(day, 
							report.getDetailedViewRoomClassOrMarketSegmantData(driver, marketSegment, day));
				}
			}
			
			app_logs.info("Before Summary view: "+summaryViewReportBeforeAction);
			app_logs.info("Before Detailed view: "+detailedViewTotalReportBeforeActionForAllTheDates);
			app_logs.info("Before Detailed view: "+detailedViewIndividualReportBeforeActionForAllTheDates);
			app_logs.info("After Summary view: "+summaryViewReportAfterAction);
			app_logs.info("After Detailed view: "+detailedViewTotalReportAfterActionForAllTheDates);
			app_logs.info("After Detailed view: "+detailedViewIndividualReportAfterActionForAllTheDates);
			
			HashMap<String, Double> calculations = report.calculationsRoomForecast(summaryViewReportBeforeAction, summaryViewReportAfterAction);
			expChanges.put("Rooms Occupancy", calculations.get("Rooms Occupancy"));
			expChanges.put("Group Pick Ups Percentage", calculations.get("Group Pick Ups Percentage"));
			expChanges.put("Performance ADR", calculations.get("Performance ADR"));
			expChanges.put("Performance RevPAR", calculations.get("Performance RevPAR"));
			
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
//			if (Scenario.contains("single")) {
//				expChanges.replace("Rooms Sold", 1.0);
//				expChanges.replace("Rooms Availability", -1.0);
//				expChanges.replace("Guests Count", Double.parseDouble(adults)+Double.parseDouble(children));
//				if (CheckInDate.equalsIgnoreCase(currentDay)) {
//					expChanges.replace("Guests Arrival", 1.0);	
//				}
//			}
			test_steps.add("*******************<b> Validating summary view report</b> ****************************");
			report.compareRoomForeCastReport(test_steps, summaryViewReportBeforeAction, summaryViewReportAfterAction, expChanges);
						
			if (DateRange.equalsIgnoreCase("Today")) {
				test_steps.add("*******************<b> Validating detailed view report </b> ****************************");
				for (String day : reservationDates) {
					try {
						//test_steps.add("*******************<b> Validating detailed view report for the day "+day+"</b> ****************************");
						report.compareRoomForeCastReport(test_steps, detailedViewTotalReportBeforeActionForAllTheDates.get(day), 
								detailedViewTotalReportAfterActionForAllTheDates.get(day), expChanges);
					}catch(Exception e) {
						app_logs.info("There is no data available for given day "+day);
					}
				}
			}
			if (breakOutBy.equalsIgnoreCase("Room Class") || breakOutBy.equalsIgnoreCase("Market Segment")) {
				for (String day : reservationDates) {
					report.compareRoomForeCastReport(test_steps, detailedViewIndividualReportBeforeActionForAllTheDates.get(day), 
							detailedViewIndividualReportAfterActionForAllTheDates.get(day), expChanges);
				}			
			}
			
//			RetryFailedTestCases.count = Utility.reset_count;
//			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

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
	
	
		
		
		
		

		
		
		
		
	

		


		
		
		
		//Second time verifying - after modifications		
		if (Scenario.contains("Increase") || Scenario.contains("Decrease") || Scenario.contains("Delete") || ResType.equalsIgnoreCase("GroupBlockQuoteComplete") || 
				ResType.equalsIgnoreCase("OutOfOrderUpdate") || ResType.equalsIgnoreCase("OutOfOrderDelete")) {
			Utility.switchTab(driver, applicationTab);
			if (Scenario.contains("Increase group block")) {
				app_logs.info("=== Updating group block - Increasing rooms count ===");
				test_steps.add("=== Updating group block - Increasing rooms count ===");
				//nav.Reservation_Backward_3(driver);
				//nav.groups(driver);
				//group.searchGroupAccount(driver, accountName, true, test_steps);
				//group.navigateRoomBlock(driver, test);
				//group.clickSecondBlock(driver, BlockName);
				group.clickEditIcon(driver, test_steps);
				group.increaseRoomBlockedCount(driver, RoomClass, 1);
				group.editDialogSave(driver);
				group.editBlockRoomNightDialog(driver);
				group.editDialogDone(driver);
				group.Save(driver, test_steps);
				expChanges.put("Group Blocks", 1.0);
				expChanges.put("Group Revenue", (totalRate/noOfRooms));
				expChanges.put("Performance Revenue", (totalRate/noOfRooms));
			}
			if (Scenario.contains("Decrease group block")) {
				app_logs.info("=== Updating group block - Decreasing rooms count ===");
				test_steps.add("=== Updating group block - Decreasing rooms count ===");
				group.clickEditIcon(driver, test_steps);
				group.decreaseRoomBlockedCount(driver, RoomClass, 1);
				group.editDialogSave(driver);
				group.editBlockRoomNightDialog(driver);
				group.editDialogDone(driver);
				group.Save(driver, test_steps);
				expChanges.put("Group Blocks", -1.0);
				expChanges.put("Group Revenue", -(totalRate/noOfRooms));
				expChanges.put("Performance Revenue", -(totalRate/noOfRooms));
			}
			
			if (Scenario.contains("Delete group block")) {
				app_logs.info("=== Delete group block ===");
				test_steps.add("=== Delete group block ===");
//				nav.Reservation_Backward_3(driver);
//				nav.groups(driver);
//				group.searchGroupAccount(driver, accountName, true, test_steps);
//				group.navigateRoomBlock(driver, test);
//				group.clickSecondBlock(driver, BlockName);
				totalRate = 0.0;
				for (int i = 0; i < RoomClasses.size(); i++) {
					totalRate = totalRate + (Double.parseDouble(group.getTotalRateValue(driver, RoomClasses.get(i))) * 
							Double.parseDouble(group.getBlocked(driver, RoomClasses.get(i))));
				}
				group.deleteBlock(driver, test_steps);
				expChanges.put("Group Blocks", -(double)noOfRooms);
				expChanges.put("Group Revenue", -totalRate);
				expChanges.put("Performance Revenue", -totalRate);
				
				expChanges.put("Rooms Sold", -(double)noOfRooms);
				expChanges.put("Rooms Availability", (double)noOfRooms);
				expChanges.put("Guests Count", -(double)noOfRooms);
				expChanges.put("Guests Arrival", -(double)noOfRooms);
				
			}
			
			if (ResType.equalsIgnoreCase("GroupBlockQuoteComplete")) {
				app_logs.info("=== Completing group block - Quote to Confirmed ===");
				test_steps.add("=== Competing group block - Quote to Confirmed ===");
				group.clickEditIcon(driver, test_steps);
				group.changeStatusOfRoomBlock(driver, "Reserved");
				group.editDialogSave(driver);
				group.editBlockRoomNightDialog(driver);
				group.editDialogDone(driver);
				group.Save(driver, test_steps);
				group.navigateRoomBlock(driver, test);
				for (int i = 0; i < RoomClasses.size(); i++) {
					totalRate = totalRate + (Double.parseDouble(group.getTotalRateValue(driver, RoomClasses.get(i))) * 
							Double.parseDouble(group.getBlocked(driver, RoomClasses.get(i))));
				}
				//totalRate = group.getTotalRateValue(driver, RoomClass);
				app_logs.info("Total rate: "+totalRate);
				
			}
			int noOfRoomsUpdate = Utility.getRandomNumber(1, 2);
			if (roomsAction.equalsIgnoreCase("OutOfOrderIncrease")) {
				rm.updateOutOfOrderRooms(driver, subjectOOO, "Increase", noOfRoomsUpdate);
				expChanges.put("Rooms Out of Order", 1.0);
				expChanges.put("Rooms Bookable", -1.0);
				expChanges.put("Rooms Availability", -1.0);
			}else if (roomsAction.equalsIgnoreCase("OutOfOrderDecrease")) {
				rm.updateOutOfOrderRooms(driver, subjectOOO, "Decrease", noOfRoomsUpdate);
				expChanges.put("Rooms Out of Order", -1.0);
				expChanges.put("Rooms Bookable", 1.0);
				expChanges.put("Rooms Availability", 1.0);
			}else if (roomsAction.equalsIgnoreCase("OutOfOrderDelete")) {
				rm.DeleteRoomOutOfOrder(driver, subjectOOO);
				expChanges.put("Rooms Out of Order", -(double)roomQuantity);
				expChanges.put("Rooms Bookable", (double)roomQuantity);
				expChanges.put("Rooms Availability", (double)roomQuantity);
			}else if (roomsAction.equalsIgnoreCase("InactiveRoomClass")) {
				nav.setup(driver);
				nav.roomClass(driver);
				rc2.openRoomClass(driver, test_steps, newRoomClassName);
				rc2.selectStatus(driver, test_steps, "Inactive");
				rc2.clickPublishV2(driver, test_steps);
				expChanges.put("Rooms Total", -(double)roomQuantity);
				expChanges.put("Rooms Bookable", -(double)roomQuantity);
				expChanges.put("Rooms Availability", -(double)roomQuantity);
				
			}else if (roomsAction.equalsIgnoreCase("ObsoleteRoomClass")) {
				rc2.openRoomClass(driver, test_steps, newRoomClassName);
				rc2.selectStatus(driver, test_steps, "Obsolete");
				rc2.clickPublishV2(driver, test_steps);
				expChanges.put("Rooms Total", -(double)roomQuantity);
				expChanges.put("Rooms Bookable", -(double)roomQuantity);
				expChanges.put("Rooms Availability", -(double)roomQuantity);
				
			}else if (roomsAction.equalsIgnoreCase("DeleteRoomClass")) {
				rc2.deleteRoomClassV2(driver, newRoomClassName);
				expChanges.put("Rooms Total", -(double)roomQuantity);
				expChanges.put("Rooms Bookable", -(double)roomQuantity);
				expChanges.put("Rooms Availability", -(double)roomQuantity);				
			}else if (roomsAction.equalsIgnoreCase("UpdateRoomClass")) {
				rc2.updateRoomClassRoomCount(driver, 1, test_steps);
				expChanges.put("Rooms Total", 1.0);
				expChanges.put("Rooms Bookable", 1.0);
				expChanges.put("Rooms Availability", 1.0);
			}
			
			app_logs.info("Expected Changes: "+expChanges);
			
			app_logs.info("Groups: "+expChanges);
			test_steps.add("========== Action modify completed at "+java.time.LocalTime.now()+"==========");
			app_logs.info("========== Action modify completed at "+java.time.LocalTime.now()+"==========");		
			Wait.wait60Second();
			Wait.wait60Second();
			test_steps.add("========== Validating Reports after modification at "+java.time.LocalTime.now()+"==========");
			app_logs.info("========== Validating Reports after modification at "+java.time.LocalTime.now()+"==========");
			
			try {
				Utility.switchTab(driver, reportsTab);
				report.editRoomForeCastFiltersClick(driver);
//				report.selectDateRange(driver, CheckInDate, CheckOutDate, DateRange, test_steps);			
//				report.selectBreakOutByOptionRoomForecast(driver, breakOutBy, test_steps);
//				report.selectIncludePerformanceMetrics(driver, "Yes", test_steps);
//				report.selectIncludeGroupReservations(driver, "Yes", test_steps);
				report.clickOnRunReport(driver);
				Wait.waitForElementToBeVisibile(By.xpath(OR_Reports.SummaryViewHeader), driver, 30);
				summaryViewReportAfterAction2 = report.getSummaryViewData(driver);
				reservationDates.clear();
				reservationDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, CheckOutDate);
				for (String day : reservationDates) {
					detailedViewTotalReportAfterActionForAllTheDates2.put(day, 
							report.getDetailedViewTotalData(driver, day));
				}
				if (breakOutBy.equalsIgnoreCase("Room Class")) {
					for (String day : reservationDates) {
						detailedViewIndividualReportAfterActionForAllTheDates2.put(day, 
								report.getDetailedViewRoomClassOrMarketSegmantData(driver, RoomClass, day));
					}
				} else if (breakOutBy.equalsIgnoreCase("Market Segment")) {
					for (String day : reservationDates) {
						detailedViewIndividualReportAfterActionForAllTheDates2.put(day, 
								report.getDetailedViewRoomClassOrMarketSegmantData(driver, marketSegment, day));
					}
				}
				
				app_logs.info("After Summary view: "+summaryViewReportAfterAction);
				app_logs.info("After Detailed view: "+detailedViewTotalReportAfterActionForAllTheDates);
				app_logs.info("After Detailed view: "+detailedViewIndividualReportAfterActionForAllTheDates);
				app_logs.info("After Modify Summary view: "+summaryViewReportAfterAction2);
				app_logs.info("After Modify Detailed view: "+detailedViewTotalReportAfterActionForAllTheDates2);
				app_logs.info("After Modify Detailed view: "+detailedViewIndividualReportAfterActionForAllTheDates2);
				
				
				HashMap<String, Double> calculations = report.calculationsRoomForecast(summaryViewReportAfterAction, summaryViewReportAfterAction2);
				expChanges.put("Rooms Occupancy", calculations.get("Rooms Occupancy"));
				expChanges.put("Group Pick Ups Percentage", calculations.get("Group Pick Ups Percentage"));
				expChanges.put("Performance ADR", calculations.get("Performance ADR"));
				expChanges.put("Performance RevPAR", calculations.get("Performance RevPAR"));

				app_logs.info("========== Validating Summary View ==========");	
				test_steps.add("========== Validating Summary View ==========");
	            report.compareRoomForeCastReport(test_steps, summaryViewReportAfterAction, summaryViewReportAfterAction2, expChanges);
	           
				app_logs.info("========== Validating Detailed View ==========");	
				test_steps.add("========== Validating Detailed View ==========");
	            for (String day : reservationDates) {
	                report.compareRoomForeCastReport(test_steps, detailedViewTotalReportAfterActionForAllTheDates.get(day),
	                        detailedViewTotalReportAfterActionForAllTheDates2.get(day), expChanges);
	            }
	            if (breakOutBy.equalsIgnoreCase("Room Class") || breakOutBy.equalsIgnoreCase("Market Segment")) {
	                for (String day : reservationDates) {
	                    report.compareRoomForeCastReport(test_steps, detailedViewIndividualReportAfterActionForAllTheDates.get(day),
	                            detailedViewIndividualReportAfterActionForAllTheDates2.get(day), expChanges);
	                }           
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
		// return test data from the sheetname provided
		return Utility.getData("RoomForecast", envLoginExcel);
		// CP_CreateReservation
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}

}
