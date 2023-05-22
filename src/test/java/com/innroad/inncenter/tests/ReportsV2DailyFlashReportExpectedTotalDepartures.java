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

import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2DailyFlashReportExpectedTotalDepartures extends TestCore  {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	Account accountPage = new Account();
//	CPReservationPage reservationPage = new CPReservationPage();
	ReservationV2 reservationV2Page = new ReservationV2();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	FolioNew folioNew = new FolioNew();
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
	ArrayList<String> test_steps = new ArrayList<>();

	String reservationNumber = null, guestFirstName = null, guestLastName, phoneNumber = "1234567890", guestSalutation = "Mr.",
	email = "innRoadTestEmail@innroad.com",	address1 = "10th Building", address2 = "Block C", address3 = "Street 10",
	city = "NewYork", country = "United States", state = "Alaska", postalCode = "12345", referral = "Walk In", marketSegment = "GDS",
	paymentMethod = "Masterrr", cardNumber = "5454545454545454", currency = null, clientTimeZone, dFormat, sourceOfRes, propertyName, 
	accountFirstName, accountLastName, reportsTab, applicationTab, currentDay, accountName = "NaveenTest", roomNumber,
	cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver,test_steps), updateStartDate, updateEndDate, newRoomClassName, roomCharge , guestFullName, promoCode, roomClassAbb,
	noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee, taskStatus;
	HashMap<String, String> ledgerAccounts = new HashMap<>();
	HashMap<String, Double> ledgerAmounts = new HashMap<>();
	HashMap<String, String> folioItemValues = new HashMap<>();
	HashMap<String, Double> folioBalances = new HashMap<>();
	
	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();

	ArrayList<String> reservationDates = new ArrayList<>();
	HashMap<String, Double> expChanges = new HashMap<>();
	
	HashMap<String, ArrayList<String>> beforeRevenueDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterRevenueDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeRoomsRevenue = new HashMap<>();
	HashMap<String, ArrayList<String>> afterRoomsRevenue = new HashMap<>();
	HashMap<String, ArrayList<String>> beforePaymentDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterPaymentDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeNetChangesDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterNetChangesDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeGuestCountDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterGuestCountDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforePropertyStatisticsDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterPropertyStatisticsDetails = new HashMap<>();

	HashMap<String, String> reservationData = new HashMap<>();
	
    ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

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
		loginAutoReportsV2(driver, test_steps);
		nav.admin(driver);
		nav.navigateToClientinfo(driver);		
		admin.clickClientName(driver);
		admin.clickClientOption(driver);		
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		if (currency.equalsIgnoreCase("USD ( $ )") || currency.equalsIgnoreCase("USD ( $ ) ")) {
			propertyCurrency = "$";
		}else if (currency.equalsIgnoreCase("GBP ( £ )") || currency.equalsIgnoreCase("GBP ( £ ) ")) {
			propertyCurrency = "£";
		}			
	
		propertyFirstDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
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
		app_logs.info("Client Info: "+clientTimeZone);
		nav.ReservationV2_Backward(driver);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateLedgerBalancesReport(String TestCaseID, String Scenario, String dateRange, String dateEffective, 
			String ledgerAccount, String ledgerValue, String accountType, String associateAccount, String resStatus, 
			String resType, String numberOfRooms, String checkInDate, String checkOutDate,			
			String ratePlan, String roomClass, String isPayment, String paymentMethod, String cardNumber,String isTaxExempt, 
			String taxExemptID, String adults, String children, String changeStayOption, String roomsAction, String guestStatus) throws Throwable {


		test_name = Scenario;
		test_description = "Validate LedgerBalances Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Ledger Balances Report";
		String testName = test_name;
		
		int totalRooms = 0;
		int oooRooms = 0;
		int soldRooms = 0;
		
		HashMap<String, Integer> guestCounts = new HashMap<>();
		HashMap<String, Integer> adultCounts = new HashMap<>();
		HashMap<String, Integer> childCounts = new HashMap<>();
		HashMap<String, Integer> roomCounts = new HashMap<>();
		String[] guestStat = {"Current In-House", "Expected Total Arrivals", "Pending Arrivals (To Be Checked In)", 
				"Expected Total Departures", "Pending Departures (To Be Checked Out)", "Current Staying OverNight"};
		for (int i = 0; i < guestStat.length; i++) {
			guestCounts.put(guestStat[i], 0);
			adultCounts.put(guestStat[i], 0);
			childCounts.put(guestStat[i], 0);
			roomCounts.put(guestStat[i], 0);
		}

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}			
		}

		Utility.closeTabsExcept(driver, 1);
		loginAutoReportsV2(driver, test_steps);
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
			if (roomClass.split("\\|").length > 1) {
				noOfRooms = roomClass.split("\\|").length;
			}else {
				noOfRooms = 1;
				numberOfRooms = "1";
			}
			
		}
		int roomCount = 0, guestCount = 0, adultsCount = 0, childCount = 0;
		
		if (Utility.validateString(accountType)) {
			accountName = "NaveenTest";
		}else {
			accountName = "";
		}
		
		ledgerAccounts = report.getLedgerInputsAndValues(driver, ledgerAccount, ledgerValue);

		String Incidentals = "", RoomCharges = "", Taxes = "", Fees = "";
		
		if (ledgerAccount.split("\\|").length == 1) {
			switch (ledgerAccount) {
			case "Incidentals":
			case "Incidental":
				Incidentals = ledgerAccounts.get(ledgerAccount);
				break;

			case "Room Charges":
			case "Room Charge":
				RoomCharges = ledgerAccounts.get(ledgerAccount);
				break;

			case "Tax":
			case "Taxes":
				Taxes = ledgerAccounts.get(ledgerAccount);
				break;

			case "Fee":
			case "Fees":
				Fees = ledgerAccounts.get(ledgerAccount);
				break;

			}
		} else {
			String[] ledgers = ledgerAccount.split("\\|");

			for (int i = 0; i < ledgers.length; i++) {
				switch (ledgers[i]) {
				case "Incidentals":
				case "Incidental":
					Incidentals = ledgerAccounts.get(ledgers[i]);
					break;

				case "Room Charges":
				case "Room Charge":
					RoomCharges = ledgerAccounts.get(ledgers[i]);
					break;

				case "Tax":
				case "Taxes":
					Taxes = ledgerAccounts.get(ledgers[i]);
					break;

				case "Fee":
				case "Fees":
					Fees = ledgerAccounts.get(ledgers[i]);
					break;

				}
			}
		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			if (!(Utility.validateString(checkInDate))) {
				if (!resType.equalsIgnoreCase("MRB")) {
	            	checkInDate = Utility.parseDate(Utility.getDatePast_FutureDate(-1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
	            	if (Scenario.contains("multi")) {
	            		checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					} else if (Scenario.contains("Single")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					} else if (Scenario.contains("extend")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
						updateEndDate = "";
						updateEndDate = Utility.addDays(checkOutDate, 2);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, updateEndDate);
					} else if (Scenario.contains("reduce")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
						updateEndDate = "";
						updateEndDate = Utility.addDays(checkOutDate, -1);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, updateEndDate);
					} else if (Scenario.contains("update") && Scenario.contains("dates")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
						updateStartDate = "";
						updateStartDate = Utility.addDays(checkOutDate, 3);
						updateEndDate = "";
						updateEndDate = Utility.addDays(checkOutDate, 3);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(updateStartDate, updateEndDate);
					}else if (Scenario.contains("Expected Total Departures")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
						checkInDate = Utility.parseDate(Utility.getDatePast_FutureDate(-1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					}else {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					}

				} else {
					checkInDates.clear();
					checkOutDates.clear();
					if ( !(Utility.validateDate(checkInDate)) ) {
						for (int i = 0; i < noOfRooms; i++) {
							checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
							if (Scenario.contains("multi")) {
								checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
							}else {
								checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
							}
							
						}
						checkInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
						checkOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);
					} else {
						checkInDates = Utility.splitInputData(checkInDate);
						checkOutDates = Utility.splitInputData(checkOutDate);
					}

				}
    		}
			
			if (!Utility.validateString(adults)) {
				adults = "2";
			}
			if (!Utility.validateString(children)) {
				children = "0";
			}
			
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);


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
		guestFirstName = "Auto";
		guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = guestFirstName+" "+guestLastName;
		accountFirstName = Utility.generateRandomStringWithoutNumbers();
		accountLastName = Utility.generateRandomStringWithoutNumbers();
		expChanges = report.setDefaultDataForRoomForeCastReport();
		currentDay = Utility.getCurrentDate("dd/MM/yyyy", TestCore.propertyTimeZone);
		additionalGuests = 0;
		roomNumber=null;
		isQuote = false;
		Double outBoundAmount = 0.00;
		
		if (Scenario.contains("Super User") || Scenario.contains("super user")) {
			admin.logout(driver);
			loginSuperUser(driver);
			prop.selectPropertyWithSuperUser(driver, propertyName);
		}
		
		ArrayList<String> RoomClasses = new ArrayList<>();
		String[] rc = roomClass.split("\\|");
		if (rc.length>1) {
			for (int i = 0; i < rc.length; i++) {
				RoomClasses.add(rc[i]);
			}
		}else {
			RoomClasses.add(roomClass);
		}

		try {

			nav.ReportsV2(driver);
			report.navigateToDailyFlashReport(driver);
			if (dateRange.isEmpty()) {
				report.selectDateDailyFlash(driver, dateEffective, test_steps);
			}else {
				report.selectDateRange(driver, dateRange, test_steps);
			}
			report.expandGivenAdvancedOptions(driver, "Break Out Tax-Exempt Revenue", test_steps);
			report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			report.clickOnRunReport(driver);
		
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 10; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				
			}
			
			beforeRevenueDetails = report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps);
			beforePaymentDetails = report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps);
			beforeNetChangesDetails = report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps);
			beforeGuestCountDetails = report.getGuestCountStatisticsTableData(driver, test_steps);
			beforePropertyStatisticsDetails = report.getPropertyPerformanceDataWithTable(driver, "Property Statistics", test_steps);
			
			app_logs.info("Revenue Details Before: "+beforeRevenueDetails);
			app_logs.info("Payment Details Before: "+beforePaymentDetails);
			app_logs.info("Net Changes Details Before: "+beforeNetChangesDetails);
			app_logs.info("Guest Count Details Before: "+beforeGuestCountDetails);
			app_logs.info("Property Statistics Details Before: "+beforePropertyStatisticsDetails);

			
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
		if (checkInDate.split("\\|").length > 1) {
			numberOfNights = Utility.getNumberofDays(checkInDate.split("\\|")[0], checkOutDate.split("\\|")[0]);
		}else {
			numberOfNights = Utility.getNumberofDays(checkInDate, checkOutDate);
		}
		
		app_logs.info("Number of nights: "+numberOfNights);
		Utility.switchTab(driver, applicationTab);
		nav.ReservationV2_Backward(driver);
		if (accountType.equalsIgnoreCase("Group") && resType.contains("Block")) {
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
			if (resType.equalsIgnoreCase("GroupBlock") || resType.equalsIgnoreCase("GroupPickRoomingList") || resType.equalsIgnoreCase("GroupPickBlueIcon") || resType.equalsIgnoreCase("GroupBlockQuote")) {
				
				app_logs.info("Romm Classes: "+roomClass);
				app_logs.info("Romm Classes list: "+RoomClasses);
				
				BlockName = BlockName + Utility.GenerateRandomString15Digit();
				group.navigateRoomBlock(driver, test);
				if (resType.equalsIgnoreCase("GroupBlock") || resType.equalsIgnoreCase("GroupPickRoomingList") ||resType.equalsIgnoreCase("GroupPickBlueIcon")) {
					test_steps.add("========== Creating Group block ==========");
					app_logs.info("========== Creating Group block ==========");
					group.createNewBlockWithMultiRoomClasses(driver, BlockName, checkInDate, checkOutDate, ratePlan, numberOfRooms, roomClass, test_steps);
					//test_steps.addAll(group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, ratePlan, numberOfRooms, RoomClass));
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
				}else if (resType.equalsIgnoreCase("GroupBlockQuote")) {
					test_steps.add("========== Creating Group block Quote ==========");
					app_logs.info("========== Creating Group block Quote ==========");
					group.createNewBlockQuote(driver, BlockName, checkInDate, checkOutDate, ratePlan, numberOfRooms, roomClass, test_steps);
				}
			}
			
			if (resType.equalsIgnoreCase("GroupPickRoomingList")) {
				test_steps.addAll(group.roomingListClick(driver));
				advgrp.enter_RoomPickupdetails(driver, test_steps);
				reservationNumber = group.pickUp_getResNo(driver);
				app_logs.info("Group pick Reservation number: "+reservationNumber);			
				group.pickUpCloseClick(driver);
				
				expChanges.put("Group Pick Ups", (double)noOfRooms);
			}else if (resType.equalsIgnoreCase("GroupPickBlueIcon")) {
				advgrp.clickBlueBookIcon(driver);
				reservationV2Page.clickNext(driver, test_steps);
				reservationV2Page.enter_GuestName(driver, test_steps, "Mr.", guestFirstName, guestLastName);
				reservationV2Page.clickBookNow(driver, test_steps);
				reservationV2Page.clickCloseReservationSavePopup(driver, test_steps);
				reservationData.put("Reservation Number", reservationV2Page.get_ReservationConfirmationNumber(driver, test_steps));
				reservationV2Page.click_Folio(driver, test_steps);
				expChanges.put("Group Pick Ups", (double)1);
			}
			
			if (resType.equalsIgnoreCase("GroupReservation")) {
				group.click_GroupNewReservation(driver, test_steps);
				report.singleRoomReservation(driver, checkInDate, checkOutDate, adults, children, ratePlan, roomClass, "Mr.", guestFirstName, guestLastName, phoneNumber, phoneNumber, email, "", "", address1, city, country, state, postalCode, "No", "", "Cash", "", "test", "10/25", "", "No", marketSegment, referral, resStatus, test_steps);
				String roomCharge = reservationV2Page.getRoomChargesFromGuestDetailsTab(driver, checkInDate, checkOutDate);
				//expChanges.put("Group Revenue", totalRate);
				expChanges.put("Performance Revenue", Double.parseDouble(roomCharge));
				expChanges.put("Rooms Sold", (double)numberOfNights*noOfRooms);
				expChanges.put("Rooms Availability", -(double)numberOfNights*noOfRooms);
				expChanges.put("Guests Count", Double.parseDouble(adults)+Double.parseDouble(children));
				expChanges.put("Guests Arrival", Double.parseDouble(adults)+Double.parseDouble(children));
			}
						
		}	
		
		if (resType.equalsIgnoreCase("TapeChart")) {
			nav.setup(driver);
			nav.roomClass(driver);
			roomClassAbb = rc2.getAbbreviationText(driver, test_steps, roomClass);
			app_logs.info("Room Abbreviation: "+roomClassAbb);
			nav.ReservationV2_Backward(driver);
		}
		
		
		if (!resType.isEmpty()) {
			
//			try {
				Utility.switchTab(driver, applicationTab);
				
				if (Utility.validateString(associateAccount)) {
					if (associateAccount.equalsIgnoreCase("Associate Account")) {
						sourceOfRes = "Associate Account";
					} else if (associateAccount.equalsIgnoreCase("Account Reservation")) {
						sourceOfRes = "Account Reservation";
					}
				}else {
					sourceOfRes = "From Reservations page";
				}
				
				if (resType.equalsIgnoreCase("TapeChart")) {
					sourceOfRes = "TapeChart";
				}
				if (accountType.equalsIgnoreCase("Group")) {
					sourceOfRes = "Group";
				}

				if (resType.equalsIgnoreCase("Single") || resType.equalsIgnoreCase("TapeChart")) {	
					reservationData = reservationV2Page.createReservation_RV2(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
							children, ratePlan, null, roomClass, roomClassAbb, "Mr.", guestFirstName, guestLastName, true,phoneNumber,
							phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
							referral, paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, 
							noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
							taskAssignee, taskStatus, accountName, accountType, accountFirstName, accountLastName, false, "1235");
					roomCharge = reservationData.get("Room Charges");
				} else if (resType.equalsIgnoreCase("MRB")) {
					for (int i = 2; i <= roomClass.split("\\|").length; i++) {
						guestFirstName = guestFirstName+"|"+Utility.generateRandomString();
						guestLastName = guestLastName+"|"+Utility.generateRandomString();
						//guestSalutation = guestSalutation+"|Mr.";
						//email = email+"|innroadautomation@innroad.com";
						//phoneNumber = phoneNumber+"|9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
						if (!(adults.split("\\|").length > 1)) {
							adults = adults + "|2";
							children = children + "|1";
						}
					}
					reservationData = reservationV2Page.createReservation_RV2(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
							children, ratePlan, null, roomClass, roomClassAbb, guestSalutation, guestFirstName, 
							guestLastName, true,phoneNumber, phoneNumber, email, address1, address2, address3, 
							city, country, state, postalCode, false, marketSegment, referral, paymentMethod, cardNumber, 
							guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, noteType, noteSubject, 
							noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
							taskAssignee, taskStatus, accountName, accountType, accountFirstName, accountLastName, false, "1235");				
					roomCharge = reservationData.get("Room Charges1");
					
				} else if (resType.equalsIgnoreCase("Copy")) {
					reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
							children, ratePlan, null, roomClass, roomClassAbb, "Mr.", guestFirstName, guestLastName, phoneNumber,
							phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
							referral, paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, 
							noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
							taskAssignee, taskStatus, accountName, accountType, accountFirstName, accountLastName, false, "1235");
					roomCharge = reservationData.get("Room Charges");
					
					reservationV2Page.copyReservation(driver, test_steps, guestFirstName, guestLastName);
									
				}
				
				if (Scenario.contains("Quote Book") || Scenario.contains("QuoteBook") || Scenario.contains("book")) {
					reservationV2Page.bookReservationFromQuote(driver, test_steps);
				}
				
				if (!Incidentals.isEmpty()) {
					reservationV2Page.switchFolioTab(driver, test_steps);
					folioNew.addFolioLineItem_RV2(driver, test_steps, Incidentals, "10");
					//reservationV2Page.addIncidental(driver, CheckInDate, Incidentals, "55", "1");
					reservationV2Page.switchDetailTab(driver, test_steps);
				}
				
				
				if (isPayment.equalsIgnoreCase("Yes")) {
					app_logs.info("Making Payment");
					reservationV2Page.takePayment(driver, test_steps, false, null, "Test",true);
				}
				
				int noOfDays = numberOfNights + 1;
				if (!Utility.validateString(roomsAction)) {
					if (!isQuote || Scenario.contains("Quote Book") || Scenario.contains("book") || !resType.contains("Group")) {
						if (resType.equalsIgnoreCase("Copy")) {							
							 if (adults.split("\\|").length > 1) {
									for (int i = 0; i < adults.split("\\|").length; i++) {
										adultsCount = adultsCount + Integer.parseInt(adults.split("\\|")[i]);
										childCount = childCount + Integer.parseInt(children.split("\\|")[i]);
									}
									guestCount = adultsCount + childCount;
							}else {
								adultsCount = Integer.parseInt(adults);
								childCount = Integer.parseInt(children);;
								guestCount = adultsCount + childCount;

							}
							soldRooms = noOfRooms;
//							if (resStatus.equalsIgnoreCase("Cancelled") && resStatus.equalsIgnoreCase("NoShow")) {
//								TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
//								ledgerAmounts.put("Room Charges", Double.parseDouble(tripSummary.getTS_ROOM_CHARGE().replaceAll("[$£ ]", "")));
//								ledgerAmounts.put("Incidentals", Double.parseDouble(tripSummary.getTS_INCIDENTALS().replaceAll("[$£ ]", "")));
//								ledgerAmounts.put("Taxes", Double.parseDouble(tripSummary.getTS_TAXES().replaceAll("[$£ ]", "")));
//								ledgerAmounts.put("Fees", Double.parseDouble(tripSummary.getTS_FEES().replaceAll("[$£ ]", "")));
//								ledgerAmounts.put("Total Charges", Double.parseDouble(tripSummary.getTS_TRIP_TOTAL().replaceAll("[$£ ]", "")));
//								ledgerAmounts.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
//								
//								folioBalances.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
//								folioBalances.put("Balance", Double.parseDouble(tripSummary.getTS_BALANCE().replaceAll("[$£ ]", "")));
//								//reservationV2Page.switchFolioTab(driver, test_steps);
//							}
//							
//							roomQuantity = noOfRooms;
//							app_logs.info("Rooms quantity: "+roomQuantity);
//							app_logs.info("Trip Summary: "+ledgerAmounts);
//							app_logs.info("Folio Balances: "+folioBalances);
						}else {
							 if (adults.split("\\|").length > 1) {
									for (int i = 0; i < adults.split("\\|").length; i++) {
										adultsCount = adultsCount + Integer.parseInt(adults.split("\\|")[i]);
										childCount = childCount + Integer.parseInt(children.split("\\|")[i]);
									}
									guestCount = adultsCount + childCount;
							}else {
								adultsCount = Integer.parseInt(adults);
								childCount = Integer.parseInt(children);;
								guestCount = adultsCount + childCount;

							}
							soldRooms = noOfRooms;
//							TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
//							ledgerAmounts.put("Room Charges", Double.parseDouble(tripSummary.getTS_ROOM_CHARGE().replaceAll("[$£ ]", "")));
//							ledgerAmounts.put("Incidentals", Double.parseDouble(tripSummary.getTS_INCIDENTALS().replaceAll("[$£ ]", "")));
//							ledgerAmounts.put("Taxes", Double.parseDouble(tripSummary.getTS_TAXES().replaceAll("[$£ ]", "")));
//							ledgerAmounts.put("Fees", Double.parseDouble(tripSummary.getTS_FEES().replaceAll("[$£ ]", "")));
//							ledgerAmounts.put("Total Charges", Double.parseDouble(tripSummary.getTS_TRIP_TOTAL().replaceAll("[$£ ]", "")));
//							ledgerAmounts.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
//							ledgerAmounts.put("Balance", Double.parseDouble(tripSummary.getTS_BALANCE().replaceAll("[$£ ]", "")));
//							
//							folioBalances.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
//							folioBalances.put("Balance", Double.parseDouble(tripSummary.getTS_BALANCE().replaceAll("[$£ ]", "")));
//							
//							
//							roomQuantity = noOfRooms;
//							app_logs.info("Rooms quantity: "+roomQuantity);
//							app_logs.info("Trip Summary: "+ledgerAmounts);
//							app_logs.info("Folio Balances: "+folioBalances);
//							app_logs.info("Rooms Sold: "+soldRooms);
						}
					}
				}
								
				
				 if (resStatus.equals("In-House")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "CheckIn", false);
					 app_logs.info("Number of room: "+noOfRooms);
					 //roomCount = noOfRooms;					 
					 roomCounts.put("Expected Total Arrivals", noOfRooms);
					 guestCounts.put("Expected Total Arrivals", guestCount);
					 adultCounts.put("Expected Total Arrivals", adultsCount);
					 childCounts.put("Expected Total Arrivals", childCount);
					 
					 roomCounts.put("Current In-House", noOfRooms);
					 guestCounts.put("Current In-House", guestCount);
					 adultCounts.put("Current In-House", adultsCount);
					 childCounts.put("Current In-House", childCount);
					 
					 if (checkInDate.equalsIgnoreCase(Utility.getDatePast_FutureDate(-1, TestCore.propertyTimeZone, "dd/MM/yyyy")) && 
							 checkOutDate.equalsIgnoreCase(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone, "dd/MM/yyyy"))) {
						 roomCounts.put("Current Staying OverNight", noOfRooms);
						 guestCounts.put("Current Staying OverNight", guestCount);
						 adultCounts.put("Current Staying OverNight", adultsCount);
						 childCounts.put("Current Staying OverNight", childCount);
					}

				 }else if (resStatus.equalsIgnoreCase("CheckOut")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "CheckOut", false);
					 if (numberOfNights > 1 && dateRange.equalsIgnoreCase("Tomarrow")) {
						 soldRooms = 0;
					}

						
				 }else if (resStatus.equalsIgnoreCase("Cancelled") || resStatus.equalsIgnoreCase("Cancel")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "Cancel", true);
					 soldRooms = 0;

				 }else if (resStatus.equalsIgnoreCase("NoShow") || resStatus.equalsIgnoreCase("No Show")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "NoShow", true);
					 soldRooms = 0;
				 } else if (resStatus.equalsIgnoreCase("MRBPrimaryChekIn")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "PrimaryCheckIn", false);
				 } else if (resStatus.equalsIgnoreCase("MRBPrimaryCheckOut")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "PrimaryCheckOut", false);
					 if (numberOfNights > 1 && dateRange.equalsIgnoreCase("Tomarrow")) {
						 soldRooms = noOfRooms-1;
					}
				 } else if (resStatus.equalsIgnoreCase("MRBSecondaryChekIn")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "SecondaryCheckIn", false);
				 } else if (resStatus.equalsIgnoreCase("MRBSecondaryCheckOut")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "SecondaryCheckOut", false);
					 Wait.wait5Second();
					 if (numberOfNights > 1 && dateRange.equalsIgnoreCase("Tomarrow")) {
						 soldRooms = noOfRooms-1;
					}
//					reservationPage.mrbSecondaryCheckOut(driver);
				 } else if (resStatus.equalsIgnoreCase("MRBSecondaryCancel")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "SecondaryCancel", true);
					 soldRooms = noOfRooms-1;
				 } else if (resStatus.equalsIgnoreCase("Guarenteed")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "Guarenteed", false);
				 } else if (resStatus.equalsIgnoreCase("On Hold")) {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "On Hold", false);
				 }else if (resStatus.equalsIgnoreCase("CheckIn") && checkOutDate.equalsIgnoreCase(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone, "dd/MM/yyyy")))  {
					 reservationV2Page.changeReservationStatus(driver, test_steps, "CheckIn", false);
					 app_logs.info("Number of room: "+noOfRooms);
					 roomCounts.put("Expected Total Departures", noOfRooms);
					 guestCounts.put("Expected Total Departures", guestCount);
					 adultCounts.put("Expected Total Departures", adultsCount);
					 childCounts.put("Expected Total Departures", childCount);
				
				 } else {
					 roomCounts.put("Expected Total Arrivals", noOfRooms);
					 guestCounts.put("Expected Total Arrivals", guestCount);
					 adultCounts.put("Expected Total Arrivals", adultsCount);
					 childCounts.put("Expected Total Arrivals", childCount);
					 
					 roomCounts.put("Pending Arrivals (To Be Checked In)", noOfRooms);
					 guestCounts.put("Pending Arrivals (To Be Checked In)", guestCount);
					 adultCounts.put("Pending Arrivals (To Be Checked In)", adultsCount);
					 childCounts.put("Pending Arrivals (To Be Checked In)", childCount); 
				 }
				 
				 if (Scenario.contains("rollback")) {
//					reservationPage.rollBackReservation(driver, test_steps, guestFullName, "RESERVED");
					reservationV2Page.rollBackReservationStatus(driver, test_steps);
					if (resStatus.equalsIgnoreCase("Cancelled") || resStatus.equalsIgnoreCase("NoShow") || resStatus.equalsIgnoreCase("CheckOut")) {
						if (adults.split("\\|").length > 1) {
							for (int i = 0; i < adults.split("\\|").length; i++) {
								guestCount = guestCount + Integer.parseInt(adults.split("\\|")[i]) + Integer.parseInt(children.split("\\|")[i]);
							}
						}else {
							guestCount = Integer.parseInt(adults) + Integer.parseInt(children);
						}
						soldRooms = noOfRooms;
					}
				 }
				 
				 if ( (Scenario.contains("extend")) || (Scenario.contains("reduce")) ||
						 (Scenario.contains("update") && Scenario.contains("dates")) ) {
						 if ((Scenario.contains("guest1"))) {
							 reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
									 updateDates.get(updateDates.size()-1), changeStayOption, 1,roomClass);					
						} else if ((Scenario.contains("guest2"))) {
							reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
									 updateDates.get(updateDates.size()-1), changeStayOption, 2,roomClass);
						} else if ((Scenario.contains("all guests"))) {
							reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
									 updateDates.get(updateDates.size()-1), changeStayOption, 1,roomClass);
							reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
									 updateDates.get(updateDates.size()-1), changeStayOption, 2,roomClass);
						} else {
							 reservationV2Page.modifyReservationDates(driver, updateDates.get(0), updateDates.get(updateDates.size()-1), 
									 changeStayOption,roomClass, test_steps);					
						}
					 } 
				 
					if (!Utility.validateString(roomsAction)) {
						if (!isQuote || Scenario.contains("Quote Book") || Scenario.contains("book") || !resType.contains("Group")) {
							if (resType.equalsIgnoreCase("Copy")) {							
								soldRooms = noOfRooms;
								if (!resStatus.equalsIgnoreCase("Cancelled") && !resStatus.equalsIgnoreCase("NoShow")) {
									TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
									ledgerAmounts.put("Room Charges", Double.parseDouble(tripSummary.getTS_ROOM_CHARGE().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Incidentals", Double.parseDouble(tripSummary.getTS_INCIDENTALS().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Taxes", Double.parseDouble(tripSummary.getTS_TAXES().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Fees", Double.parseDouble(tripSummary.getTS_FEES().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Total Charges", Double.parseDouble(tripSummary.getTS_TRIP_TOTAL().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
									
									folioBalances.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
									folioBalances.put("Balance", Double.parseDouble(tripSummary.getTS_BALANCE().replaceAll("[$£ ]", "")));
									//reservationV2Page.switchFolioTab(driver, test_steps);
								}else {
									ledgerAmounts.put("Room Charges", 0.0);
									ledgerAmounts.put("Incidentals", 0.0);
									ledgerAmounts.put("Taxes", 0.0);
									ledgerAmounts.put("Fees", 0.0);
									ledgerAmounts.put("Total Charges", 0.0);
									ledgerAmounts.put("Payments", 0.0);
									
									folioBalances.put("Payments", 0.0);
									folioBalances.put("Balance", 0.0);	
								}
								
								roomQuantity = noOfRooms;
								app_logs.info("Rooms quantity: "+roomQuantity);
								app_logs.info("Trip Summary: "+ledgerAmounts);
								app_logs.info("Folio Balances: "+folioBalances);
							}else {
								soldRooms = noOfRooms;
								if (!resStatus.equalsIgnoreCase("Cancelled") && !resStatus.equalsIgnoreCase("NoShow")) {
									TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
									ledgerAmounts.put("Room Charges", Double.parseDouble(tripSummary.getTS_ROOM_CHARGE().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Incidentals", Double.parseDouble(tripSummary.getTS_INCIDENTALS().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Taxes", Double.parseDouble(tripSummary.getTS_TAXES().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Fees", Double.parseDouble(tripSummary.getTS_FEES().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Total Charges", Double.parseDouble(tripSummary.getTS_TRIP_TOTAL().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
									ledgerAmounts.put("Balance", Double.parseDouble(tripSummary.getTS_BALANCE().replaceAll("[$£ ]", "")));
									
									if (!resStatus.equalsIgnoreCase("CheckOut")) {
										folioBalances.put("Payments", Double.parseDouble(tripSummary.getTS_PAID().replaceAll("[$£ ]", "")));
									}else {
										folioBalances.put("Payments", 0.0);
									}
									
									folioBalances.put("Balance", Double.parseDouble(tripSummary.getTS_BALANCE().replaceAll("[$£ ]", "")));
									
								}else {
									ledgerAmounts.put("Room Charges", 0.0);
									ledgerAmounts.put("Incidentals", 0.0);
									ledgerAmounts.put("Taxes", 0.0);
									ledgerAmounts.put("Fees", 0.0);
									ledgerAmounts.put("Total Charges", 0.0);
									ledgerAmounts.put("Payments", 0.0);
									
									folioBalances.put("Payments", 0.0);
									folioBalances.put("Balance", 0.0);	
								}
								roomQuantity = noOfRooms;
								app_logs.info("Rooms quantity: "+roomQuantity);
								app_logs.info("Trip Summary: "+ledgerAmounts);
								app_logs.info("Folio Balances: "+folioBalances);
								app_logs.info("Rooms Sold: "+soldRooms);
							}
						}
					}
				 
				 
//			} catch (Exception e) {
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
//					Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			} catch (Error e) {
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
//					Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			}
			
		}else if (!accountType.isEmpty()) {
			if (!accountType.equalsIgnoreCase("Group")) {
				nav.Accounts(driver);
				accountName = "Acc"+Utility.generateRandomString();
				accountPage.createAccount(driver, test_steps, null, accountType, accountName, accountFirstName, accountLastName, 
						phoneNumber, phoneNumber, email, marketSegment, referral, address1, address2, address3, city, state, postalCode);

			}
			


			if (accountType.equals("Group")) {

				try {
					test_steps.add("========== Creating Group account ==========");
					app_logs.info("========== Creating Group account ==========");
					accountName = "Acc"+Utility.generateRandomString();
					nav.groups(driver);
					group.type_GroupName(driver, test, accountName, test_steps);
					group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
					group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber, address1,
							city, state, country, postalCode, test_steps);
					group.Billinginfo(driver);
					group.Save(driver, test_steps);
					group.clickGroupFolioTab(driver);
					if (!Incidentals.isEmpty()) {
						group.addLineItems(driver, Incidentals, Utility.GenerateRandomNumber(5, 50));
						group.commit(driver, null);
						group.Save(driver, test_steps);
						group.clickGroupFolioTab(driver);
					}
					

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			}
		}
		

		
		if (Utility.validateString(roomsAction)) {
			String subjectOOO = Utility.fourDigitgenerateRandomString();
			newRoomClassName = "Test"+Utility.generateRandomNumber();
			roomQuantity = Utility.getRandomNumber(3, 4); 
			app_logs.info("Rooms quantity: "+roomQuantity);
			if (roomsAction.equalsIgnoreCase("NewRoomClass")) {
				nav.setup(driver);
				nav.roomClass(driver);
				rc2.createRoomClassV2(driver, test_steps, newRoomClassName, "TRC", "3", "5", ""+roomQuantity+"");
				totalRooms = roomQuantity;
			}else if (roomsAction.equalsIgnoreCase("OutOfOrder") || roomsAction.equalsIgnoreCase("OutOfOrderIncrease") || roomsAction.equalsIgnoreCase("OutOfOrderDecrease") || roomsAction.equalsIgnoreCase("OutOfOrderDelete")) {
				nav.Reservation_Backward_3(driver);
				nav.clickOnGuestServices(driver, test_steps);
				nav.RoomMaintenance(driver);
				//rm.CreateNewRoomOut(driver, "", subjectOOO, RoomClass);
				//rm.CreateNewRoomOut(driver, "1", subjectOOO);
				rm.createNewRoomOutOfOrder(driver, "1", subjectOOO, roomQuantity);
				oooRooms = roomQuantity;
			}else if (roomsAction.equalsIgnoreCase("BlackOut")) {
				nav.inventory_Backward_1(driver);
				nav.Rates_Grid(driver);
				ratesGrid.clickOnAvailability(driver);
				ratesGrid.clickForRateGridCalender(driver,test_steps);
				Utility.selectDateFromDatePicker(driver, checkInDate, "dd/MM/yyyy");

				ratesGrid.clickExpendRooClass(driver, test_steps, roomClass);
				ratesGrid.blockoutRoomClassOrAvilable(driver, test_steps, roomClass, 1, "innCenter","Blockout");
				//ratesGrid.blockoutRoomClassOrAvilable(driver, test_steps, RoomClass, 1, "Incenter", "BlockOut");
			}else if (roomsAction.equalsIgnoreCase("BulkUpdate")) {
				nav.inventory_Backward_1(driver);
				nav.Rates_Grid(driver);
				ratesGrid.clickOnAvailability(driver);
				ratesGrid.clickForRateGridCalender(driver,test_steps);
				ratesGrid.clickOnBulkUpdate(driver);
				ratesGrid.selectBulkUpdateOption(driver, "Availability");
				ratesGrid.startDate(driver, checkInDate);
				ratesGrid.endDate(driver, checkOutDate);
				ratesGrid.selectRoomClass(driver, roomClass);
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
		
		test_steps.add("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		
		try {
			Utility.switchTab(driver, reportsTab);
			report.clickOnRunReport(driver);
			report.clickOnRunReport(driver);
			
			afterRevenueDetails = report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps);
			afterPaymentDetails = report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps);
			afterNetChangesDetails = report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps);
			afterGuestCountDetails = report.getGuestCountStatisticsTableData(driver, test_steps);
			afterPropertyStatisticsDetails = report.getPropertyPerformanceDataWithTable(driver, "Property Statistics", test_steps);
			
			app_logs.info("Revenue Details After: "+afterRevenueDetails);
			app_logs.info("Payment Details After: "+afterPaymentDetails);
			app_logs.info("Net Changes Details After: "+afterNetChangesDetails);
			app_logs.info("Guest Count Details After: "+afterGuestCountDetails);
			app_logs.info("Property Statistics Details After: "+afterPropertyStatisticsDetails);
			
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
			
			test_steps.add("========= Validating Guest Count Statistics table =========");
			if(TestCaseID.contains("849334"))
			{
			report.validateDailyFlashReportGuestCount_RV2(driver, beforeGuestCountDetails, afterGuestCountDetails, roomCounts.get("Current In-House"), guestCounts.get("Current In-House"), adultCounts.get("Current In-House"), childCounts.get("Current In-House"), "Current In-House", test_steps);
			}
			else if (TestCaseID.contains("849337"))
				{
				report.validateDailyFlashReportGuestCount_RV2(driver, beforeGuestCountDetails, afterGuestCountDetails, roomCounts.get("Expected Total Departures"), guestCounts.get("Expected Total Departures"), adultCounts.get("Expected Total Departures"), childCounts.get("Expected Total Departures"), "Expected Total Departures", test_steps);
				}
		
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Daily Flash Report - Guest Count Statistics table", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Daily Flash Report - Guest Count Statistics table", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		try {
			//comments = "";
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("DailyFlashExpectedTotalDepartur", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
