package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyPageLoadtime  extends TestCore {
	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	Navigation nav = new Navigation();
	ReservationHomePage resHomePage = new ReservationHomePage();
	String testName = null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test( groups = "Reservation")
	public void cPVerifyPageLoadtime() throws ParseException {
		String testCaseID="848771";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifyPageLoadtime";		
		test_description = "CPVerifyPageLoadtime<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848771' target='_blank'>"
				+ "Click here to open TestRail: 848771</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848771", Utility.testId, Utility.statusCode,
				Utility.comments, "");
		long start,finish,totalTime;
		try {
			
			
			testSteps.add("<b>========Navigating Modules========<b>");
			app_logs.info("<b>========Navigating Modules========<b>");

			driver = getDriver();
			login_CP(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			
			 start = System.currentTimeMillis();
			////////////////// Accounts////////////////
			nav.Accounts(driver);
			testSteps.add("Navigating to Accounts");
			app_logs.info("Navigating to Accounts");
			

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			String time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			String time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Accounts" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Accounts" + "Load time is " + time1 + " Second");
			
			
			start = System.currentTimeMillis();
			nav.Reservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");
			
			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + "Second");
			start = System.currentTimeMillis();
			
			
			////////////////// GuestServices////////////////
			nav.Guestservices_3(driver);
			testSteps.add("Navigating to GuestServices");
			app_logs.info("Navigating to GuestServices");


			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("GuestServices" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("GuestServices" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation_Backward_2(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			////////////////// Inventory////////////////
			nav.inventory(driver);
			testSteps.add("Navigating to inventory");
			app_logs.info("Navigating to inventory");


			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("inventory" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("inventory" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");
			
			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();

			////////////////// Setup////////////////
			nav.Setup(driver);
			testSteps.add("Navigating to Setup");
			app_logs.info("Navigating to Setup");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Setup" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Setup" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");


			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			
			////////////////// Admin////////////////
			nav.Admin(driver);
			testSteps.add("Navigating to Admin");
			app_logs.info("Navigating to Admin");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Admin" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Admin" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			
			nav.Reservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			////////////////// NightAudit////////////////
			nav.Reports(driver);
			nav.NightAudit(driver);
			testSteps.add("Navigating to NightAudit");
			app_logs.info("Navigating to NightAudit");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("NightAudit" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("NightAudit" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");


			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			////////////////// Reports////////////////
			nav.Reports(driver);
			testSteps.add("Navigating to Reports");
			app_logs.info("Navigating to Reports");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reports" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reports" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			//nav.Reservation(driver);
			nav.Reservation_Backward_3(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			testSteps.add("<b>========Navigating Sub Modules========<b>");
			app_logs.info("<b>========Navigating Sub Modules========<b>");

			////////////////// Accounts////////////////
			nav.Accounts(driver);
			testSteps.add("Navigating to Accounts");
			app_logs.info("Navigating to Accounts");
			
			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Accounts" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Accounts" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Statements(driver);
			testSteps.add("Navigating to Statements");
			app_logs.info("Navigating to Statements");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Statements" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Statements" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.UnitownerAccount(driver);
			testSteps.add("Navigating to Unit owner Item");
			app_logs.info("Navigating to Unit owner Item");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Unit owner Item" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Unit owner Item" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			
			nav.TravelAgent(driver);
			testSteps.add("Navigating to TravelAgent");
			app_logs.info("Navigating to TravelAgent");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("TravelAgent" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("TravelAgent" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.ManagementTransfers(driver);
			testSteps.add("Navigating to ManagementTransfers");
			app_logs.info("Navigating to ManagementTransfers");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("ManagementTransfers" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("ManagementTransfers" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.AccountDistribution(driver);
			testSteps.add("Navigating to AccountDistribution");
			app_logs.info("Navigating to AccountDistribution");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("AccountDistribution" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("AccountDistribution" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			
			////////////////// Inventory////////////////
			nav.inventory(driver);
			testSteps.add("Navigating to inventory");
			app_logs.info("Navigating to inventory");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("inventory" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("inventory" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.RatesGrid(driver);
			testSteps.add("Navigating to RatesGrid");
			app_logs.info("Navigating to RatesGrid");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("RatesGrid" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("RatesGrid" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
	
			nav.Distribution(driver);
			testSteps.add("Navigating to Distribution");
			app_logs.info("Navigating to Distribution");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Distribution" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Distribution" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.policies(driver);
			testSteps.add("Navigating to policies");
			app_logs.info("Navigating to policies");
			
			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("policies" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("policies" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			//nav.backToReservationfromPolicy(driver);
			nav.navigateToReservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			////////////////// Setup////////////////
			nav.Setup(driver);
			testSteps.add("Navigating to Setup");
			app_logs.info("Navigating to Setup");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Setup" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Setup" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Properties(driver);
			testSteps.add("Navigating to Properties");
			app_logs.info("Navigating to Properties");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Properties" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Properties" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			
			nav.RoomClass(driver);
			testSteps.add("Navigating to RoomClass");
			app_logs.info("Navigating to RoomClass");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("RoomClass" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("RoomClass" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.clickTaxesAndFeesAfterRoomClass(driver);
			testSteps.add("Navigating to Taxes");
			app_logs.info("Navigating to Taxes");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Taxes" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Taxes" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.LedgerAccounts(driver);
			testSteps.add("Navigating to LedgerAccounts");
			app_logs.info("Navigating to LedgerAccounts");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("LedgerAccounts" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("LedgerAccounts" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Merchantservices(driver);
			testSteps.add("Navigating to Merchantservices");
			app_logs.info("Navigating to Merchantservices");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Merchantservices" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Merchantservices" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.DocumentTemplate(driver);
			testSteps.add("Navigating to DocumentTemplate");
			app_logs.info("Navigating to DocumentTemplate");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("DocumentTemplate" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("DocumentTemplate" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.ListManagemnet(driver);
			testSteps.add("Navigating to ListManagemnet");
			app_logs.info("Navigating to ListManagemnet");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("ListManagemnet" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("ListManagemnet" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.clickAirbnbSetup(driver);
			testSteps.add("Navigating to AirbnbSetup");
			app_logs.info("Navigating to AirbnbSetup");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("AirbnbSetup" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("AirbnbSetup" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.TaskManagement(driver);
			testSteps.add("Navigating to TaskManagement");
			app_logs.info("Navigating to TaskManagement");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("TaskManagement" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("TaskManagement" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation_Backward_2(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			////////////////// Admin////////////////
			nav.Admin(driver);
			testSteps.add("Navigating to Admin");
			app_logs.info("Navigating to Admin");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Admin" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Admin" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Clientinfo(driver);
			testSteps.add("Navigating to Clientinfo");
			app_logs.info("Navigating to Clientinfo");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Clientinfo" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Clientinfo" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Users(driver);
			testSteps.add("Navigating to Users");
			app_logs.info("Navigating to Users");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Users" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Users" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Roles(driver);
			testSteps.add("Navigating to Roles");
			app_logs.info("Navigating to Roles");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Roles" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Roles" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.GDPR(driver);
			testSteps.add("Navigating to GDPR");
			app_logs.info("Navigating to GDPR");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("GDPR" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("GDPR" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation_Backward_3(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			////////////////// NightAudit////////////////
			nav.Reports(driver);
			nav.NightAudit(driver);
			testSteps.add("Navigating to NightAudit");
			app_logs.info("Navigating to NightAudit");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("NightAudit" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("NightAudit" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			////////////////// Reports////////////////
			nav.Reports(driver);
			testSteps.add("Navigating to Reports");
			app_logs.info("Navigating to Reports");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reports" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reports" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.AccountBalances(driver);
			testSteps.add("Navigating to AccountBalances");
			app_logs.info("Navigating to AccountBalances");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("AccountBalances" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("AccountBalances" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.LedgerBalances(driver);
			testSteps.add("Navigating to LedgerBalances");
			app_logs.info("Navigating to LedgerBalances");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("LedgerBalances" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("LedgerBalances" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.MerchantTrans(driver);
			testSteps.add("Navigating to MerchantTrans");
			app_logs.info("Navigating to MerchantTrans");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("MerchantTrans" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("MerchantTrans" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.DailyFalsh(driver);
			testSteps.add("Navigating to DailyFalsh");
			app_logs.info("Navigating to DailyFalsh");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("DailyFalsh" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("DailyFalsh" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.RoomForecast(driver);
			testSteps.add("Navigating to RoomForecast");
			app_logs.info("Navigating to RoomForecast");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("RoomForecast" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("RoomForecast" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.NetSales(driver);
			testSteps.add("Navigating to NetSales");
			app_logs.info("Navigating to NetSales");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("NetSales" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("NetSales" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.AdvanceDeposite(driver);
			testSteps.add("Navigating to AdvanceDeposite");
			app_logs.info("Navigating to AdvanceDeposite");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("AdvanceDeposite" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("AdvanceDeposite" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			nav.Reservation_Backward(driver);
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");

			finish = System.currentTimeMillis();
			totalTime = finish - start; 
			time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(totalTime));
			time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(totalTime));
			testSteps.add("Reservation" + "Load time is " + time + " </b> MilliSecond");
			app_logs.info("Reservation" + "Load time is " + time1 + " Second");
			start = System.currentTimeMillis();
			
			/*testSteps.add("Verify the loading time of the reservation module by navigating to reservation dashboard from different modules"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848771' target='_blank'>"
					+ "Click here to open TestRail: 848771</a><br>");	
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the loading time of the reservation module by navigating to reservation dashboard from different modules");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		*/
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Pages load time");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Delete Room Class", "GS", "GS", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Room Class", "GS", "GS", testName, test_description, test_catagory,
					testSteps);
		}
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
