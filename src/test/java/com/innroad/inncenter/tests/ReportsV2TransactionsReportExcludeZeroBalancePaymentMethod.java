package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class ReportsV2TransactionsReportExcludeZeroBalancePaymentMethod extends TestCore  {

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
	HashMap<String, String> folioPaymentItemValues= new HashMap<>();
	
	
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
	HashMap<String, String> itemDescription = new HashMap<>();

	HashMap<String, String> reservationData = new HashMap<>();
	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	
	
	HashMap<String, String> summaryViewReportBeforeAction = new HashMap<>();
	HashMap<String, String> summaryViewReportAfterAction = new HashMap<>();



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
		comments.add("Test got failed");
		if(!Utility.validateString(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}			
		}

		Utility.closeTabsExcept(driver, 1);
		loginAutoReportsV2(driver, test_steps);
		

		
		String includePayment="Yes";
		String amount="0.00";
		try {
			
			nav.ReportsV2(driver, test_steps);
			report.navigateToTransactionsReport(driver, test_steps);
			report.selectDateRange(driver, checkInDate, checkOutDate, dateRange, test_steps);
			report.excludeZeroBalancePaymentMethod(driver, test_steps, false);
			//report.excludeZeroBalancePaymentMethodsForTransactionReport(driver, test_steps, true);
			//report.includedPaymentMethodsForTransactionReport(driver, test_steps, includePayment);
			report.selectBreakOutDailyTotalForTransactionReport(driver, test_steps, false);			
			report.clickOnRunReport(driver, test_steps);
			report.getPaymentMethodSummaryViewTransactionReport(driver, test_steps);
		
			
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
			   //comments = "";
				//Test data 
				String[] testcase = TestCaseID.split(Utility.DELIM);
				for (int i = 0; i < testcase.length; i++) {
					statusCode.add(i, "1");
					comments.add("Test got pass");
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
		return Utility.getData("ExcludeZeroBalancePaymentMethod", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
;
	}







}
