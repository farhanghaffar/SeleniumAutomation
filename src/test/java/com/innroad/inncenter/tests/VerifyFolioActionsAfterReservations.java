package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Move;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.model.Category;

import groovyjarjarantlr4.v4.parse.ANTLRParser.element_return;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

public class VerifyFolioActionsAfterReservations extends TestCore {

	private WebDriver driver = null;

	public static String test_description = "";
	public static String test_catagory = "";

	static ExtentTest test;
	static ExtentReports report;
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;

	HashMap<String, String> ItemLineStatusBeforeRollBack = new HashMap<String, String>();
	HashMap<String, String> itemLineStatusAfterRollBack = new HashMap<String, String>();

	// @BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyFolioActions(String TestCaseID, String caseName, String displayName, String Policy_T,
			String ratePlan, String firstName, String lastName, String IsAssign, String isChecked, String paymentType,
			String cardNumber, String nameOnCard, String cardExpDate, String Action, String CheckInDate,
			String CheckOutDate, String Adults, String Children, String Rateplan, String PromoCode, String IsSplitRes,
			String roomclassabb, String RoomClass, String IsDepositOverride, String DepositOverrideAmount,
			String IsAddMoreGuestInfo, String Salutation, String GuestFirstName, String GuestLastName,
			String PhoneNumber, String AltenativePhone, String Email, String Account, String AccountType,
			String Address1, String Address2, String Address3, String City, String Country, String State,
			String PostalCode, String IsGuesProfile, String TravelAgent, String MarketSegment, String Referral,
			String Description, String Incidental_Category, String Incidental_PerUnit, String Incidental_Quantity,
			String ResType) throws ParseException, Exception {

		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> scriptName = new ArrayList<>();
		ArrayList<String> testCategory = new ArrayList<>();
		ArrayList<String> testDescription = new ArrayList<>();

		String[] roomClass = RoomClass.replace("|", " ").split(" ");
		String[] roomClassabb = roomclassabb.replace("|", " ").split(" ");
		String[] Phone_Number = PhoneNumber.replace("|", " ").split(" ");
		String[] rate_Plan = ratePlan.replace("|", " ").split(" ");

		String testName = caseName;
		String testDescriptionForLink = "";
		if (!Utility.validateString(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("4");
			testDescriptionForLink = caseName + "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/"
					+ TestCaseID + "' target='_blank'>" + "Click here to open TestRail: C" + TestCaseID + "</a>";
		} else {
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
				testDescriptionForLink = testDescriptionForLink + "<br>"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/" + testcase[i]
						+ "' target='_blank'>" + "Click here to open TestRail: C" + testcase[i] + "</a>";

			}

		}
		test_description = testDescriptionForLink;
		test_catagory = "Folio";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		// MBR Reservation
		Double depositAmount = 0.0;
		Double paidDeposit = 0.0;
		String reservation = null;
		String status = null;
		ArrayList<String> Rooms = new ArrayList<String>();
		ArrayList<String> roomCost = new ArrayList<String>();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> getTest_steps = new ArrayList<>();

		CPReservationPage cpRes = new CPReservationPage();
		Navigation navigation = new Navigation();
		Account account = new Account();
		Admin admin = new Admin();
		Policies policies = new Policies();
		ReservationHomePage reservationHomePage = new  ReservationHomePage();
		CPReservationPage reservationPage = new CPReservationPage();
		Folio folio = new Folio();
		Properties properties = new Properties();
		System.out.print("RoomClass: " + roomClass[0]);
		String tempraryRoomClassName = roomClass[0];
		String randomNumber = Utility.GenerateRandomNumber();
		roomClass[0] = roomClass[0] + randomNumber;
		displayName = roomClass[0];
		lastName = lastName + randomNumber;
		firstName = firstName + randomNumber;
		Policy_T = Policy_T + randomNumber;
		String adult = "2";
		String children = "1";
		String reservationNumber1 = "";
		String reservationNumber2 = "";
		String GuestName1 = "";
		String GuestName2 = "";
		String ResTotalBalance2 = "";
		String AccountName = "Account_Name";
		String AccountNumber = "";
		String getTimeZone = "";
		String expectedDate = "";

		// Login

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			loginClinent3281(driver);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		/////////////////////////
		// 825330
		////////////////////////

		if (TestCaseID.equalsIgnoreCase("848647")) {
			
		
		try {

				// Get CheckIN and Checkout Date
				if (!(Utility.validateInput(CheckInDate))) {
					for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}
			CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);

			// Single Reservation

			getTestSteps.clear();
			Thread.sleep(4000);
			app_logs.info("Clicking on New Reservation");
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Check In Date");
			app_logs.info("Entering Check In Date");
			getTestSteps = reservationPage.checkInDate(driver, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Check Out Date");
			app_logs.info("Entering Check Out Date");
			getTestSteps = reservationPage.checkOutDate(driver, +1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Adult Value");
			app_logs.info("Entering Adult Value");
			getTestSteps = reservationPage.enterAdult(driver, adult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Children Value");
			app_logs.info("Entering Children Value");
			getTestSteps = reservationPage.enterChildren(driver, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Rateplan");
			app_logs.info("Entering Rateplan");
			getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			app_logs.info("Clicking On Find Room");
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Filling All Fullfil Values");
			app_logs.info("Filling All Fullfil Values");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Address(driver, getTest_steps, Address1, Address2, Address3);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.enter_City(driver, getTest_steps, City);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.enter_PostalCode(driver, getTest_steps, "12345");;
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.select_Country(driver, getTest_steps, Country);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.select_State(driver, getTest_steps, State);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", firstName, lastName);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, "5454545454545454", "Test Useer","12/23");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, "GDS");
			testSteps.addAll(getTestSteps);
			

			Thread.sleep(1000);
			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.add("Successfully click Book Now");

			Wait.wait1Second();
			getTestSteps.clear();
			reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			testSteps.add("Reservation confirmation number: " + reservationNumber1);
			app_logs.info("Reservation confirmation number: " + reservationNumber1);

			Wait.wait1Second();
			getTestSteps.clear();
			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			testSteps.add("Sucessfully close save quote popup");
			
			Wait.wait1Second();
			getTestSteps.clear();
			reservationPage.click_Folio(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			
			Wait.wait1Second();
			reservationHomePage.folioPayButton(driver);
			testSteps.add("Click On Pay Button");
			
			Thread.sleep(1000);
			getTest_steps.clear();
			reservationHomePage.AuthorizeButtonClickInTakePayment(driver, getTest_steps, "40", false, true);
			testSteps.addAll(getTest_steps);
			cpRes.clickSaveButtonUnderFolioTab(driver);


			//navigation.reservation(driver);
			// MRB
			
			reservationPage.closeAllOpenedReservations(driver);
			

			Thread.sleep(2000);
			cpRes.click_NewReservation(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			cpRes.select_Dates(driver, getTest_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,
					IsSplitRes);
			testSteps.addAll(getTest_steps);

			if (IsSplitRes.equalsIgnoreCase("Yes")) {
				getTest_steps.clear();
				cpRes.enter_Adults(driver, getTest_steps, Adults);
				testSteps.addAll(getTest_steps);

				getTest_steps.clear();
				cpRes.enter_Children(driver, getTest_steps, Children);
				testSteps.addAll(getTest_steps);

				getTest_steps.clear();
				cpRes.select_Rateplan(driver, getTest_steps, Rateplan, PromoCode);
				testSteps.addAll(getTest_steps);
			}
			getTest_steps.clear();
			cpRes.clickOnFindRooms(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			roomCost = cpRes.select_MRBRooms(driver, getTest_steps, RoomClass, IsAssign, Account);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			depositAmount = cpRes.deposit(driver, getTest_steps, IsDepositOverride, DepositOverrideAmount);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			cpRes.clickNext(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			cpRes.enter_MRB_MailingAddress(driver, getTest_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City,
					Country, State, PostalCode, IsGuesProfile, IsAddMoreGuestInfo, "No", Rooms);
			testSteps.addAll(getTest_steps);

			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				getTest_steps.clear();
				cpRes.enter_PaymentDetails(driver, getTest_steps, paymentType, "5454545454545454", nameOnCard, "12/23");
				testSteps.addAll(getTest_steps);
			}
			System.out.println(Rooms);

			getTest_steps.clear();
			cpRes.enter_MarketSegmentDetails(driver, getTest_steps, TravelAgent, MarketSegment, Referral);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			cpRes.clickBookNow(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			reservation = cpRes.get_ReservationConfirmationNumber(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			status = cpRes.get_ReservationStatus(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			cpRes.clickCloseReservationSavePopup(driver, getTest_steps);
			testSteps.addAll(getTest_steps);
			
			Wait.wait1Second();
			getTestSteps.clear();
			reservationPage.click_Folio(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			
			Wait.wait1Second();
			reservationHomePage.folioPayButton(driver);
			testSteps.add("Click On Pay Button");
			
			Thread.sleep(1000);
			getTest_steps.clear();
			reservationHomePage.AuthorizeButtonClickInTakePayment(driver, getTest_steps, "40", false, true);
			testSteps.addAll(getTest_steps);
			
			Thread.sleep(1000);
			getTest_steps.clear();
			String PrimaryFolioPaidAmount = folio.getAmount(driver, "MC");
			testSteps.add("Primary Amount: "+PrimaryFolioPaidAmount);
			testSteps.add("Payment is Verified");
			
			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.mrbChangeFolio(driver, 2, getTest_steps);
			testSteps.addAll(getTest_steps);
			
			Wait.wait1Second();
			reservationHomePage.folioPayButton(driver);
			testSteps.add("Click On Pay Button");
			
			Thread.sleep(1000);
			getTest_steps.clear();
			reservationHomePage.AuthorizeButtonClickInTakePayment(driver, getTest_steps, "40", false, true);
			testSteps.addAll(getTest_steps);
			
			Thread.sleep(1000);
			getTest_steps.clear();
			String SecondaryFolioPaidAmount = folio.getAmount(driver, "MC");
			testSteps.add("Secondary Amount: "+SecondaryFolioPaidAmount);
			testSteps.add("Secondary Paid Payment is Verified");
			
			
			statusCode.add(0,"1");

		} catch (Exception e) {
			testSteps.add(e.toString());
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		}
		if(TestCaseID.contains("848668")) {
			try {
				// Single Reservation

				getTestSteps.clear();
				Thread.sleep(4000);
				app_logs.info("Clicking on New Reservation");
				getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Check In Date");
				app_logs.info("Entering Check In Date");
				getTestSteps = reservationPage.checkInDate(driver, 0);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Check Out Date");
				app_logs.info("Entering Check Out Date");
				getTestSteps = reservationPage.checkOutDate(driver, +1);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Adult Value");
				app_logs.info("Entering Adult Value");
				getTestSteps = reservationPage.enterAdult(driver, adult);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Children Value");
				app_logs.info("Entering Children Value");
				getTestSteps = reservationPage.enterChildren(driver, children);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Rateplan");
				app_logs.info("Entering Rateplan");
				getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				app_logs.info("Clicking On Find Room");
				getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				testSteps.add("Filling All Fullfil Values");
				app_logs.info("Filling All Fullfil Values");
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.clickNext(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.enter_Address(driver, getTest_steps, Address1, Address2, Address3);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				reservationPage.enter_City(driver, getTest_steps, City);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				reservationPage.enter_PostalCode(driver, getTest_steps, "12345");;
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				reservationPage.select_Country(driver, getTest_steps, Country);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				reservationPage.select_State(driver, getTest_steps, State);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", firstName, lastName);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, Boolean.parseBoolean(isChecked));
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, "5454545454545454", "NameOnCard","12/23");
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.selectReferral(driver, "GDS");
				testSteps.addAll(getTestSteps);
				

				Wait.wait1Second();
				getTestSteps.clear();
				getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
				testSteps.add("Successfully click Book Now");

				Wait.wait1Second();
				getTestSteps.clear();
				reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Reservation confirmation number: " + reservationNumber1);
				app_logs.info("Reservation confirmation number: " + reservationNumber1);

				Wait.wait1Second();
				getTestSteps.clear();
				reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Sucessfully close save quote popup");
				
				
				Wait.wait1Second();
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				
				Wait.wait1Second();
				reservationHomePage.folioPayButton(driver);
				testSteps.add("Click On Pay Button");
				
				Wait.wait1Second();
				String Name = reservationHomePage.getNameONCard(driver);
				testSteps.add("Name On Card: "+Name);
				app_logs.info("Name On Card: "+Name);
				
				
				Wait.wait1Second();
				String Amount = reservationHomePage.getAmountTakePayment(driver);
				testSteps.add("Amount: "+Amount);
				app_logs.info("Amount: "+Amount);
				
				
				
				Wait.wait1Second();
				getTest_steps.clear();
				reservationHomePage.CaptureButtonClickInTakePayment(driver, getTest_steps, String.valueOf(Double.valueOf(Amount)+60), false, true);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				getTest_steps = folio.ClickSaveFolioButton(driver);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				String TotalBalance = folio.getTotalBalance(driver);
				testSteps.add("Total Balance: "+TotalBalance);
				app_logs.info("Total Balance: "+TotalBalance);
				
				Wait.wait1Second();
				reservationHomePage.folioPayButton(driver);
				testSteps.add("Click On Pay Button");
				
				getTest_steps.clear();
				reservationHomePage.RefundButton(driver, getTest_steps, true);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				String Balance = folio.getTotalBalance(driver);
				testSteps.add("Verified: Total Balance After Refund: 0.00");
				app_logs.info("Total Balance After Refund: "+Balance);
				statusCode.add(1,"1");
				
			} catch (Exception e) {
				testSteps.add(e.toString());
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		comments = "Verified Folio Action";
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("folioactionsReservation", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,
		 comments,TestCore.TestRail_AssignToID);
	}
}
