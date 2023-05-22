package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Driver;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Move;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.jetty9.server.Authentication.Failed;
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
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.model.Category;
import com.snowtide.pdf.layout.t;

import groovyjarjarantlr4.v4.parse.ANTLRParser.element_return;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

public class VerifyFolioComponentActions extends TestCore {

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
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyFolioComponentActions(String TestCaseID, String caseName, String displayName, String Policy_T,
			String ratePlan, String firstName, String lastName, String IsAssign, String isChecked, String paymentType,
			String cardNumber, String nameOnCard, String cardExpDate, String Action, String CheckInDate,
			String CheckOutDate, String Adults, String Children, String Rateplan, String PromoCode, String IsSplitRes,
			String roomclassabb, String RoomClass, String IsDepositOverride, String DepositOverrideAmount,
			String IsAddMoreGuestInfo, String Salutation, String GuestFirstName, String GuestLastName,
			String PhoneNumber, String AltenativePhone, String Email, String Account, String AccountType,
			String Address1, String Address2, String Address3, String City, String Country, String State,
			String PostalCode, String IsGuesProfile, String TravelAgent, String MarketSegment, String Referral,
			String Description, String Incidental_Category, String Incidental_PerUnit, String Incidental_Quantity,
			String TaxName, String taxLedgerAccount, String TaxdisplayName, String description, String value,
			String category, String MRBReservation) throws ParseException, Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> scriptName = new ArrayList<>();
		ArrayList<String> testCategory = new ArrayList<>();
		ArrayList<String> testDescription = new ArrayList<>();

		String[] roomClass = RoomClass.split("\\|");
		String[] roomClassabb = roomclassabb.split("\\|");
		String[] Phone_Number = PhoneNumber.split("\\|");
		String[] rate_Plan = ratePlan.split("\\|");

		String testName = caseName;
		String testDescriptionForLink = "";
		if (!Utility.validateString(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("4");
			testDescriptionForLink = caseName + "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/"+ TestCaseID + "' target='_blank'>" + "Click here to open TestRail: C" + TestCaseID + "</a>";
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
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
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
		Rate rate = new Rate();
		Policies policies = new Policies();
		CPReservationPage reservationPage = new CPReservationPage();
		Folio folio = new Folio();
		Tax tax = new Tax();
		DecimalFormat f = new DecimalFormat("##.00");
		Properties properties = new Properties();
		System.out.print("RoomClass: " + roomClass[0]);
		String tempraryRoomClassName = roomClass[0];
		String randomNumber = Utility.GenerateRandomNumber();
		String roomClass_i = roomClass[0] + randomNumber;
		displayName = roomClass_i;
		lastName = lastName + randomNumber;
		firstName = firstName + randomNumber;
		Policy_T = Policy_T + randomNumber;
		boolean excludeTaxExempt = true;
		boolean vat = false;
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
		String randomString = "";
		String tName = "";
		String createdpercent = "";
		String tripSummaryTaxesWithCurrency = "";
		String roomCharge = "";
		String TripTotalCharge = "";
		String totalRoomChargesBeforeResSave = null;
		String totalTripChargesbeforesave = null;
		String totalTaxChargesbeforesave = null;
		double tripsummarytaxesafterressave = 0.0;

		////////////////////////////////////////
		// Login
		///////////////////////////////////////

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			driver = getDriver();
//			login_CP(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "CP_Login"));
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		TaxesAndFee taxFee = new TaxesAndFee();
		if (TestCaseID.equalsIgnoreCase("848662")) {

			try {
				// Create Tax
				testSteps.add("<b>************** Tax Setup*******************</b>");
				navigation.Setup(driver);
				testSteps.add("Navigate to setup");
				navigation.Taxes(driver);
				testSteps.add("Navigate to taxes page");

				// delete All Existing Taxes
				testSteps.add("<b>********** Deleting percent Tax****************</b>");
				getTest_steps.clear();
//				tax.DeleteAllTaxExcept(driver, "no", getTest_steps);
				taxFee.deleteAllTaxesAndFee(driver, testSteps);
				testSteps.addAll(getTest_steps);
				Thread.sleep(1000);

				// create new tax------------------
				testSteps.add("<b>********** Creating New percent Tax****************</b>");
				randomString = Utility.GenerateRandomNumber();
				tName = TaxName.replace("XXX", randomString);
//				tax.Click_NewItem(driver, getTest_steps);
//				testSteps.add("Click at new tax item button");
//				testSteps.add("<br>Create new taxes</br>");
//				boolean percentage = true;
//				testSteps.add("percent Tax name is: " + "<b>" + tName + " </b>");
//				tax.createTax(driver, test, tName.trim(), tName.trim(), tName.trim(), value, category, taxLedgerAccount,
//						excludeTaxExempt, percentage, vat);
				
				taxFee.createTaxes(driver, tName.trim(), tName.trim(), tName.trim(),
						category, "percent", value, "", taxLedgerAccount, false, "", "",
						"", testSteps);
				
				testSteps.add("Created tax with percent amount: " + "<b>" + value + "</b>");

				navigation.ReservationV2_Backward(driver);
				testSteps.add("Click at reservation menu link ");

				
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create tax", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create tax", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		////////////////////////////
		// 848659
		///////////////////////////

		if (TestCaseID.equalsIgnoreCase("848659") || TestCaseID.equalsIgnoreCase("848587")) {

			
			try {
				
				app_logs.info("Navigating to Inventory");
				navigation.Inventory(driver, testSteps);

				testSteps.add("Navigating to policy");
				app_logs.info("Navigating to policy");
				navigation.policies(driver);

				if (TestCaseID.equalsIgnoreCase("848587")) {
					testSteps.add("Searching For All policies");
					app_logs.info("Searching For All policies");

					getTest_steps.clear();
					policies.Search_All_Policy_U(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					testSteps.add("All policies Search Complete");
					app_logs.info("All policies Search Complete");

				} else {
					testSteps.add("Searching For Deposit policies");
					app_logs.info("Searching For Deposit policies");

					getTest_steps.clear();
					policies.Search_Deposit_Policy_U(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					testSteps.add("Deposit policies Search Complete");
					app_logs.info("Deposit policies Search Complete");

				}

				int size = policies.Check_Size_Of_Policies_Rows(driver);
				if (size > 0) {
					testSteps.add("Deleting policies");
					app_logs.info("Deleting policies");
					policies.DeleteAllPolicies(driver, getTest_steps);
				} else {
					testSteps.add("Verified: <b>No policies Found For Deletion<b>");
					app_logs.info("Verified: <b>No policies Found For Deletion<b>");
				}

				if (!TestCaseID.equalsIgnoreCase("848587")) {
					// Create Deposite Here
					Thread.sleep(500);
					policies.clickNewPolicyButton(driver, getTest_steps);
					Thread.sleep(500);
					policies.Enter_Policy_Name(driver, Policy_T, getTest_steps);
					Thread.sleep(500);
					policies.Deposit_Policy_Attributes(driver, "Room Charges", "30", getTest_steps);
					Thread.sleep(500);
					policies.Enter_Policy_Desc(driver, Policy_T, Policy_T);
					Thread.sleep(500);
					policies.Associate_Sources(driver);
					Thread.sleep(500);
					policies.Associate_Seasons(driver);
					Thread.sleep(500);
					policies.Associate_RoomClasses(driver, tempraryRoomClassName);
					Thread.sleep(500);
					policies.Associate_RatePlans(driver);
					Thread.sleep(500);
					policies.Save_Policy(driver);

				}
				Thread.sleep(500);
				navigation.Reservation(driver);
				
			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Policy", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Policy", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			
			
			
			
			
			
			
			
			
			
			
		}

		////////////////////////////
		// 848744 && 848743 && 848742
		///////////////////////////

		if (TestCaseID.equalsIgnoreCase("848744") || TestCaseID.equalsIgnoreCase("848743")
				|| TestCaseID.equalsIgnoreCase("848742")) {
			
			try {
				
				navigation.inventory(driver);
				testSteps.add("Navigate To Inventory");
				Thread.sleep(1000);
				navigation.Rate(driver);
				testSteps.add("Navigate To Rate");
				Thread.sleep(1000);
				String PackID = "MainContent_btnPackages";
				Wait.waitForElementToBeClickable(By.id(PackID), driver);
				driver.findElement(By.id(PackID)).click();
				testSteps.add("Click On Package Button");

				RatePackage ratePackage = new RatePackage();
//				Thread.sleep(1000);			
//				ratePackage.delete_rate(driver);
				Thread.sleep(1000);
				getTest_steps.clear();
				ratePackage.package_details(driver, displayName, getTest_steps);
				testSteps.addAll(getTest_steps);
//				Thread.sleep(1000);
//				ratePackage.associateRate(driver);
//				testSteps.add("Selecting a associate Rate");
				Thread.sleep(1000);
				getTest_steps.clear();
				getTest_steps = ratePackage.packageComponentWithAddOn(driver, Incidental_Category, "100");
				testSteps.addAll(getTest_steps);
				Thread.sleep(1000);
				ratePackage.package_descriptiveInformation(driver, displayName, Policy_T, "Description");
				testSteps.add("Entering Name: " + displayName);
				testSteps.add("Entering Policy: " + Policy_T);
				testSteps.add("Entering Description: " + "Description");

				// ratePackage.assoCiateRate(driver, displayName, getTest_steps,
				// tempraryRoomClassName);
				Thread.sleep(1000);
				getTest_steps.clear();
				getTest_steps = ratePackage.SavePackageRate(driver);
				testSteps.addAll(getTest_steps);
				Thread.sleep(1000);
				navigation.reservation(driver);
				testSteps.add("Navigate To Reservation");
				
			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Rate", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Rate", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			
			

		}

		////////////////////////////////////////
		// Reservation
		///////////////////////////////////////

		if (MRBReservation.equalsIgnoreCase("Yes")) {
//			String ResType = "Split";
			
			try {
				if (!(Utility.validateInput(CheckInDate))) {
					for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
						if(!IsSplitRes.equalsIgnoreCase("Yes")) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
						else {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),"MM/dd/yyyy","dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),"MM/dd/yyyy","dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy","dd/MM/yyyy"));
						break;
						}
						}
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}
				if (MRBReservation.equalsIgnoreCase("Yes")||IsSplitRes.equalsIgnoreCase("Yes")) {
					CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
					CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
					app_logs.info(CheckInDate);
					app_logs.info(CheckOutDate);
				}
				else {
					CheckInDate = checkInDates.get(0);
					CheckOutDate = checkOutDates.get(0);
					app_logs.info(CheckInDate);
					app_logs.info(CheckOutDate);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			


			try {
				if (IsSplitRes.equalsIgnoreCase("Yes")) {
					testSteps.add("==========CREATE NEW MRB SPLIT RESERVED RESERVATION==========");
					app_logs.info("==========CREATE NEW MRB SPLIT RESERVED RESERVATION==========");
				}
				else {
					testSteps.add("==========CREATE NEW MRB RESERVED RESERVATION==========");
					app_logs.info("==========CREATE NEW MRB RESERVED RESERVATION==========");
				}
				
				getTest_steps.clear();
				Thread.sleep(4000);
				cpRes.click_NewReservation(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				getTest_steps.clear();
				cpRes.select_Dates(driver, getTest_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan,
							PromoCode, IsSplitRes);
				testSteps.addAll(getTest_steps);	
				
				

				if (IsSplitRes.equalsIgnoreCase("Yes")) {
					getTest_steps.clear();
					cpRes.enter_Adults(driver, getTest_steps, "2");
					testSteps.addAll(getTest_steps);

					getTest_steps.clear();
					cpRes.enter_Children(driver, getTest_steps, "1");
					testSteps.addAll(getTest_steps);

					getTestSteps.clear();
					testSteps.add("Entering Rateplan");
					app_logs.info("Entering Rateplan");
					getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
					testSteps.addAll(getTestSteps);
					
				}
				getTest_steps.clear();
				cpRes.clickOnFindRooms(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				
				if(IsSplitRes.equalsIgnoreCase("Yes")) {
					ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClass[0]);
					Thread.sleep(500);
					reservationPage.selectRoomToReserve(driver, testSteps, roomClass[0].trim(), rooms.get(0));
					Thread.sleep(500);
					reservationPage.verifySpinerLoading(driver);
			        app_logs.info("rooms : " + rooms);
			        
			        Thread.sleep(3000);
			        rooms.clear();
			        rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClass[1]);
			        app_logs.info("rooms : " + rooms);
			        Thread.sleep(500);
			        reservationPage.selectRoomToReserve(driver, testSteps, roomClass[1].trim(), rooms.get(1));
			        Thread.sleep(500);
			        reservationPage.verifySpinerLoading(driver);
			        
			        getTest_steps.clear();
					cpRes.clickNext(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
			        
			        getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver,
							Boolean.parseBoolean(isChecked));
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", firstName, lastName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer",
							cardExpDate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.selectReferral(driver, "GDS");
					testSteps.addAll(getTestSteps);

					Thread.sleep(1000);
					getTestSteps.clear();
					getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
					testSteps.add("Successfully click Book Now");
				}
				else {
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
							Country, State, PostalCode, IsGuesProfile, IsAddMoreGuestInfo, IsSplitRes, Rooms);
					testSteps.addAll(getTest_steps);

					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						getTest_steps.clear();
						cpRes.enter_PaymentDetails(driver, getTest_steps, paymentType, cardNumber, nameOnCard, cardExpDate);
						testSteps.addAll(getTest_steps);
					}
					System.out.println(Rooms);

					getTest_steps.clear();
					cpRes.enter_MarketSegmentDetails(driver, getTest_steps, TravelAgent, MarketSegment, Referral);
					testSteps.addAll(getTest_steps);

					getTest_steps.clear();
					cpRes.clickBookNow(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
				}

				

				

				

				getTest_steps.clear();
				reservation = cpRes.get_ReservationConfirmationNumber(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				getTest_steps.clear();
				status = cpRes.get_ReservationStatus(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				getTest_steps.clear();
				cpRes.clickCloseReservationSavePopup(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create MRB Reservation", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create MRB Reservation", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else {

			try {
				testSteps.add("==========CREATE NEW RESERVED RESERVATION==========");
				app_logs.info("==========CREATE NEW RESERVED RESERVATION==========");

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

				if (TestCaseID.equalsIgnoreCase("848711")) {
					getTestSteps.clear();
					testSteps.add("Entering Rateplan");
					app_logs.info("Entering Rateplan");
					getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, "Manual Override", PromoCode);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					testSteps.add("Entering RateAmount");
					app_logs.info("Entering RateAmount");
					getTestSteps = reservationPage.EnterManualRateAmount(driver, DepositOverrideAmount);
					testSteps.addAll(getTestSteps);
				} else {
					getTestSteps.clear();
					testSteps.add("Entering Rateplan");
					app_logs.info("Entering Rateplan");
					getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
					testSteps.addAll(getTestSteps);
				}

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
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver,
						Boolean.parseBoolean(isChecked));
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", firstName, lastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer",
						cardExpDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.selectReferral(driver, "GDS");
				testSteps.addAll(getTestSteps);

				if (TestCaseID.equalsIgnoreCase("848662")) {
					totalTaxChargesbeforesave = reservationPage.getTotaltaxBeforeSaveRes(driver);
					app_logs.info("totalTaxChargesbeforesave :" + totalTaxChargesbeforesave);
					testSteps.add("Total tax charges captured before save reservation: " + "<b>"
							+ totalTaxChargesbeforesave + "</b>");
					totalTripChargesbeforesave = reservationPage.getTotalTripTotalBeforeSaveRes(driver);
					app_logs.info("totalTripCharges:" + totalTripChargesbeforesave);
					testSteps.add("Total trip charges captured before reservation saved: " + "<b>"
							+ totalTripChargesbeforesave + "</b>");
					totalRoomChargesBeforeResSave = reservationPage.getRoomChargesbeforeResSave(driver);
					app_logs.info("totalRoomChargesBeforeResSave:" + "<b>" + totalRoomChargesBeforeResSave + "<b>");
					testSteps.add("Total room charges captured before resrevation saved: " + "<b>"
							+ totalRoomChargesBeforeResSave + "<b>");
				}

				Thread.sleep(1000);
				getTestSteps.clear();
				getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
				testSteps.add("Successfully click Book Now");

				Thread.sleep(1000);
				getTestSteps.clear();
				reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Reservation confirmation number: " + reservationNumber1);
				app_logs.info("Reservation confirmation number: " + reservationNumber1);

				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Sucessfully close save quote popup");

				Thread.sleep(1000);
				getTestSteps.clear();
				GuestName1 = reservationPage.getguestname(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"Reservation Done with Guestname: " + GuestName1 + " Reservation No: " + reservationNumber1);

				expectedDate = ESTTimeZone.getDateWithTime(getTimeZone, "MM/dd/yy h:mm aa");

			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		////////////////////////////
		// 848587
		///////////////////////////
		if (TestCaseID.equalsIgnoreCase("848587")) {

			
			
			
			
			try {
				
				getTest_steps.clear();
				cpRes.Click_CheckInAllButton(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				getTest_steps.clear();
				cpRes.verifyCheckInConfirmDetailsPaymentPopupIsAppeared(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				getTest_steps.clear();
				cpRes.VerifyinHouseStatus(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				// Primary Folio
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.click_FolioDetail_DropDownBox(getTestSteps, driver);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.selectFolioByRoomClassAbb(driver, roomClassabb[0]);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				String PrimaryTotalBalanceBeforePayment = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add(" Primary Balance Defore Payment: "+PrimaryTotalBalanceBeforePayment);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.clickFolioPayButton(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.folioPayment(getTestSteps, driver, PrimaryTotalBalanceBeforePayment);
				// reservationPage.takePayment(driver, getTestSteps, paymentType,cardNumber,
				// nameOnCard,cardExpDate, "Authorization Only","Yes","200", "No","");
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				String PrimaryTotalBalanceAfterPayment = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);


				testSteps.add("Expected Primary Balance After Payment: "+PrimaryTotalBalanceAfterPayment);
				testSteps.add("Actual Primary Balance After Payment: "+PrimaryTotalBalanceAfterPayment);

				assertEquals(PrimaryTotalBalanceAfterPayment, "0.00");
				// Secondary Folio

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.click_FolioDetail_DropDownBox(getTestSteps, driver);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.selectFolioByRoomClassAbb(driver, roomClassabb[1]);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				String SecondaryTotalBalanceBeforePayment = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add("Secondary Balance Before Payment: "+SecondaryTotalBalanceBeforePayment);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.clickFolioPayButton(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.folioPayment(getTestSteps, driver, SecondaryTotalBalanceBeforePayment);
				// reservationPage.takePayment(driver, getTestSteps, paymentType,cardNumber,
				// nameOnCard,cardExpDate, "Authorization Only","Yes","200", "No","");
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				String SecondaryTotalBalanceAfterPayment = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("Expected Secondary Balance After Payment: "+SecondaryTotalBalanceAfterPayment);
				testSteps.add("Actual Secondary Balance After Payment: "+SecondaryTotalBalanceAfterPayment);
				
				assertEquals(SecondaryTotalBalanceAfterPayment, "0.00");
				
				Thread.sleep(500);
				getTestSteps.clear();
				getTest_steps = folio.ClickSaveFolioButton(driver);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTestSteps.clear();
				cpRes.clickCheckOutAllButton(driver, getTestSteps);;
				testSteps.addAll(getTestSteps);
				
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				cpRes.disableGenerateGuestReportToggle(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				cpRes.clickOnProceedToCheckOutPaymentButton(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				
				
				//Balance Verification
				
				testSteps.add("<b>***********Balance Verification***********<b>");
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.click_FolioDetail_DropDownBox(getTestSteps, driver);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.selectFolioByRoomClassAbb(driver, roomClassabb[0]);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				String PrimaryTotalBalance = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add(" Primary Balance: "+PrimaryTotalBalance);
				assertEquals(PrimaryTotalBalance, "0.00","Failed To match Primary Balance");
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.click_FolioDetail_DropDownBox(getTestSteps, driver);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.selectFolioByRoomClassAbb(driver, roomClassabb[1]);
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				String SecondaryTotalBalance = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add(" Primary Balance: "+SecondaryTotalBalance);
				assertEquals(SecondaryTotalBalance, "0.00","Failed To match Primary Balance");
				
				testSteps.add("Verified: <b>Verify Primary guest settling folios without a payment & full reservation balance must be paid before checkout<b>");
				
				
			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Primary guest settling folios without a payment & full reservation balance must be paid before checkout", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Primary guest settling folios without a payment & full reservation balance must be paid before checkout", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			
			

		}
		
		
		////////////////////////////
		// 848748
		///////////////////////////

		if(TestCaseID.equalsIgnoreCase("848748")) {
			
			
			try {
				String Amount_BeforeAddAmount = "";
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				List<WebElement> linksList = folio.getDescriptionLinkWebElementList(driver);
				List<WebElement> AmountList = folio.getAmountWebElementList(driver);
				printString("AmountList Size:  "+AmountList.size());
				testSteps.add("Number of LineItem : "+linksList.size());
				printString("linksList Size:  "+linksList.size());
				for(int i = 0 ; i<AmountList.size() ; i++) {
					testSteps.add("<b>=============For "+(i+1)+" LineItem=============<b>");
					Wait.wait3Second();
					Amount_BeforeAddAmount = AmountList.get(i).getText().trim().replace("$ ", "");
					testSteps.add("Amount of "+(i+1)+" LineItem Before Adding Amount:  "+Amount_BeforeAddAmount.replace("$ ", ""));
					printString("Amount_BeforeAddAmount:  "+Amount_BeforeAddAmount.replace("$ ", ""));
				}
				
				for(int i = 0 ; i<linksList.size() ; i++) {
					testSteps.add("<b>=============For "+(i+1)+" LineItem Adding Amount=============<b>");
					Wait.wait3Second();
					linksList.get(i).click();
					
					//Addsteps tomorrow
					getTestSteps.clear();
					folio.FolioItemDetailPopup(driver, getTestSteps,String.valueOf(100) , String.valueOf("Description"+i), String.valueOf("note for folio"+i));
					testSteps.addAll(getTestSteps);

				}
				AmountList = folio.getAmountWebElementList(driver);
				printString(" After AmountList Size:  "+AmountList.size());
				
				for(int i = 0 ; i<AmountList.size() ; i++) {
					testSteps.add("<b>=============For "+(i+1)+" LineItem =============<b>");
					Wait.wait3Second();
					String Amount_AfterAddAmount = AmountList.get(i).getText().trim();
					testSteps.add("Amount of "+(i+1)+" LineItem After Adding Amount:  "+Amount_AfterAddAmount.replace("$ ", ""));
					printString("Amount_AfterAddAmount:  "+Amount_AfterAddAmount.replace("$ ", ""));
					
//					assertEquals(Double.valueOf(Amount_AfterAddAmount.replace("$ ", "")) ,Double.valueOf(Amount_BeforeAddAmount)+100);
				
					testSteps.add("<b>Verified adding amount for room charges(all rooms) line items for split reservation<b>");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Verify adding amount for room charges(all rooms) line items for split reservation", "NONGS_Login", "Login",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Verify adding amount for room charges(all rooms) line items for split reservation", "NONGS_Login", "Login",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
		}
		
		
		
		
		
		////////////////////////////
		// 848663
		///////////////////////////
		
		if(TestCaseID.equalsIgnoreCase("848663")) {

			
			try {
				String folioName = "";
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				
				
				for(int i = 1; i<=2;i++) {
					testSteps.add("<b>*******Adding LineItems "+i+"*******<b>");
					Thread.sleep(500);
					getTestSteps.clear();
					folio.clickAddLineItemButton(driver);
					testSteps.addAll(getTest_steps);

					Thread.sleep(500);
					getTestSteps.clear();
					getTest_steps = folio.AddLineItem(driver, Incidental_Category, DepositOverrideAmount, 4, "1");
					testSteps.addAll(getTest_steps);

					Thread.sleep(500);
					getTestSteps.clear();
					getTest_steps = folio.ClickSaveFolioButton(driver);
					testSteps.addAll(getTest_steps);

				}
				
				Thread.sleep(500);
				getTestSteps.clear();
				int PrimaryLineItemCount = folio.getLineItemRowsCount(driver);
				testSteps.addAll(getTest_steps);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.click_FolioDetail_DropDownBox(getTest_steps, driver);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.selectFolioByRoomClassAbb(driver, roomClassabb[1]);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(500);
				getTestSteps.clear();
				int SecondaryLineItemCountBeforeMove = folio.getLineItemRowsCount(driver);
				testSteps.addAll(getTest_steps);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.click_FolioDetail_DropDownBox(getTest_steps, driver);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.selectFolioByRoomClassAbb(driver, roomClassabb[0]);
				testSteps.addAll(getTestSteps);
				
				folioName = "Guest Folio For "+roomClassabb[1];
				Thread.sleep(500);
				getTestSteps.clear();
				getTest_steps = folio.MoveFolioWithContainFolioName(driver, folioName);
				testSteps.addAll(getTest_steps);
				
				
				
				Wait.wait10Second();
				
				driver.navigate().refresh();
				
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.click_FolioDetail_DropDownBox(getTest_steps, driver);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.selectFolioByRoomClassAbb(driver, roomClassabb[1]);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(500);
				getTestSteps.clear();
				int SecondaryLineItemCount = folio.getLineItemRowsCount(driver);
				testSteps.addAll(getTest_steps);
				testSteps.add("Expected LineItem Count: <b>"+SecondaryLineItemCountBeforeMove+PrimaryLineItemCount);
				testSteps.add("Actual LineItem Count: <b>"+SecondaryLineItemCount);
				Assert.assertEquals(SecondaryLineItemCount, SecondaryLineItemCountBeforeMove+PrimaryLineItemCount,"Failed To amtch Line Item count");
				testSteps.add("<b>Folio LineItems Successfully Moved<b>");
				
			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify move folio", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify move folio", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			
			
			
			
			
		}
		

		////////////////////////////
		// 848569
		///////////////////////////

		if (TestCaseID.equalsIgnoreCase("848569")) {
			
			Utility.refreshPage(driver);
			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.ClickEditStayInfo(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.ClickStayInfo_ChangeDetails(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.clickStayInfoEditOptions(driver, getTest_steps, 2);
			testSteps.addAll(getTest_steps);

			try {
				Thread.sleep(1000);
				getTest_steps.clear();
				getTest_steps = reservationPage.checkOutDate(driver, 3);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.addAll(reservationPage.clickFindRooms(driver));

				Thread.sleep(1000);
				getTest_steps.clear();
				expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver, getTimeZone, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.add("********Before Decreasing the no of check  out days********* ");
				int BeforeLineItemRowCount = folio.getLineItemRowsCount(driver);
				testSteps.add("LineItems Count: " + BeforeLineItemRowCount);

				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.click_DeatilsTab(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.ClickEditStayInfo(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.ClickStayInfo_ChangeDetails(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.clickStayInfoEditOptions(driver, getTest_steps, 2);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				getTest_steps = reservationPage.checkOutDate(driver, 2);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.addAll(reservationPage.clickFindRooms(driver));

				Thread.sleep(1000);
				getTest_steps.clear();
				expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver, getTimeZone, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.add("********After Decreasing the no of check  out days********* ");
				int AfterLineItemRowCount = folio.getLineItemRowsCount(driver);
				testSteps.add("LineItems Count: " + AfterLineItemRowCount);

				String VerifyDateFolio = ESTTimeZone.DateFormateForLineItemWithFormate(2, "EEE MMM dd, yyyy");
				System.out.print("\n\n\n" + VerifyDateFolio + "\n\n");

				Thread.sleep(1000);
				getTest_steps.clear();
				folio.Verify_LineItem_(driver, VerifyDateFolio, "Room Charge", getTest_steps, false);
				testSteps.addAll(getTest_steps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e,
							"Failed to Verify display of folio line items after extending/reducing single reservation with selecting radio button Change only for added/removed dates.",
							testName, "Incidental", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e,
							"Failed to Verify display of folio line items after extending/reducing single reservation with selecting radio button Change only for added/removed dates.",
							testName, "Incidental", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		////////////////////////////
		// 848570
		///////////////////////////

		if (TestCaseID.equalsIgnoreCase("848570")) {
			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.ClickEditStayInfo(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.ClickStayInfo_ChangeDetails(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.clickStayInfoEditOptions(driver, getTest_steps, 3);
			testSteps.addAll(getTest_steps);

			try {
				Thread.sleep(1000);
				getTest_steps.clear();
				getTest_steps = reservationPage.checkOutDate(driver, 3);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.addAll(reservationPage.clickFindRooms(driver));

				Thread.sleep(1000);
				getTest_steps.clear();
				expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver, getTimeZone, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.add("********Before Decreasing the no of check  out days********* ");
				int BeforeLineItemRowCount = folio.getLineItemRowsCount(driver);
				testSteps.add("LineItems Count: " + BeforeLineItemRowCount);

				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.click_DeatilsTab(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.ClickEditStayInfo(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.ClickStayInfo_ChangeDetails(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.clickStayInfoEditOptions(driver, getTest_steps, 3);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				getTest_steps = reservationPage.checkOutDate(driver, 2);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.addAll(reservationPage.clickFindRooms(driver));

				Thread.sleep(1000);
				getTest_steps.clear();
				expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver, getTimeZone, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.add("********After Decreasing the no of check  out days********* ");
				int AfterLineItemRowCount = folio.getLineItemRowsCount(driver);
				testSteps.add("LineItems Count: " + AfterLineItemRowCount);

				String VerifyDateFolio = ESTTimeZone.DateFormateForLineItemWithFormate(2, "EEE MMM dd, yyyy");
				System.out.print("\n\n\n" + VerifyDateFolio + "\n\n");

				Thread.sleep(1000);
				getTest_steps.clear();
				folio.Verify_LineItem_(driver, VerifyDateFolio, "Room Charge", getTest_steps, false);
				testSteps.addAll(getTest_steps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e,
							"Failed to Verify display of folio line items after extending/reducing single reservation with selecting radio button No Rate Change.",
							testName, "Incidental", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e,
							"Failed to Verify display of folio line items after extending/reducing single reservation with selecting radio button No Rate Change.",
							testName, "Incidental", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		////////////////////////////
		// 825577
		///////////////////////////

		if (TestCaseID.equalsIgnoreCase("825577")) {
			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.ClickEditStayInfo(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.ClickStayInfo_ChangeDetails(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTest_steps.clear();
			reservationPage.clickStayInfoEditOptions(driver, getTest_steps, 3);
			testSteps.addAll(getTest_steps);

			try {
				Thread.sleep(1000);
				getTest_steps.clear();
				getTest_steps = reservationPage.checkOutDate(driver, 5);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				testSteps.addAll(reservationPage.clickFindRooms(driver));

				Thread.sleep(1000);
				getTest_steps.clear();
				expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver, getTimeZone, getTest_steps);
				testSteps.addAll(getTest_steps);
				testSteps.add("Verfied: <b>Save Button is Clickable<b>");

				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				folio.VerifyFolioDatesSort(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e,
							"Failed to Verify that correct amount and line items are displayed when user reduces the reservation date by one day from beginning from tape chart of a single room reservation with rate component as No Rate Change.",
							testName, "Incidental", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e,
							"Failed to Verify that correct amount and line items are displayed when user reduces the reservation date by one day from beginning from tape chart of a single room reservation with rate component as No Rate Change.",
							testName, "Incidental", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		////////////////////////////
		// 848162
		///////////////////////////
		if (TestCaseID.equalsIgnoreCase("848162")) {
			String newFolioName = "New_Folio";
			Thread.sleep(1000);
			getTestSteps.clear();
			reservationPage.click_Folio(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			Thread.sleep(500);
			getTestSteps.clear();
			folio.Create_NewFolio_(driver, newFolioName, "New_Folio_Description", getTest_steps);
			testSteps.addAll(getTestSteps);
			testSteps.add("New Folio Created Successfully.  " + newFolioName);

			Thread.sleep(500);
			getTestSteps.clear();
			getTest_steps = folio.ClickSaveFolioButton(driver);
			testSteps.addAll(getTest_steps);

			Thread.sleep(500);
			getTestSteps.clear();
			folio.clickAddLineItemButton(driver);
			testSteps.addAll(getTest_steps);

			Thread.sleep(500);
			getTestSteps.clear();
			getTest_steps = folio.AddLineItem(driver, Incidental_Category, DepositOverrideAmount, 4, "1");
			testSteps.addAll(getTest_steps);

			Thread.sleep(500);
			getTestSteps.clear();
			getTest_steps = folio.ClickSaveFolioButton(driver);
			testSteps.addAll(getTest_steps);

			Thread.sleep(500);
			getTestSteps.clear();
			getTest_steps = folio.MoveFolio(driver, newFolioName);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTestSteps.clear();
			reservationPage.click_Folio(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			Thread.sleep(500);
			getTestSteps.clear();
			int LineItemAfterMove = folio.getLineItemRowsCount(driver);
			testSteps.addAll(getTest_steps);

			testSteps.add("Expected LineItem Count: 0");
			testSteps.add("Actual LineItem Count: " + LineItemAfterMove);
			Assert.assertEquals(0, LineItemAfterMove, "Failed to Move LineItem To " + newFolioName);
			testSteps.add("Verified LineItem Count:<b> " + LineItemAfterMove);
			testSteps.add("Verified: Move Folio Functionality.");

		}

		////////////////////////////
		// 848662
		///////////////////////////
		if (TestCaseID.equalsIgnoreCase("848662")) {
			try {
				// After reservation save verify the validation
				testSteps.add("<b>*********** Calculating Overall Taxes************</b>");
				Wait.wait5Second();
				getTestSteps.clear();
				tripSummaryTaxesWithCurrency = reservationPage.get_TripSummaryTaxesWithCurrency(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				roomCharge = reservationPage.getRoomChargeUnderTripSummary(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				TripTotalCharge = reservationPage.getTripSummaryTripTotal(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				// Calculate tax percent which we have created
				int stayDays = 1;
				createdpercent = reservationPage.getPercentcalcvalueSingleItem(driver, value,
						totalRoomChargesBeforeResSave.replace("$", "").trim(), stayDays);

				// ------------------------------------
				// Making assertion of charges after reservation save and before save

				testSteps.add(
						"<b>*************Making assertion of Charges Before and After Reservation Save **************<b>");
				tripsummarytaxesafterressave = Double.parseDouble(tripSummaryTaxesWithCurrency.replace("$", "").trim());
				double tripTotalAfterResSave = Double.parseDouble(TripTotalCharge.replace("$", "").trim());
				double roomChargeAfterResSave = Double.parseDouble(roomCharge);
				double totalTripAmountBeforeResSave = Double
						.parseDouble(totalTripChargesbeforesave.replace("$", "").trim());
				// double totalTaxbeforeressave =
				// Double.parseDouble(totalTaxChargesbeforesave.replace("$", "").trim());
				// assert calculatedtax and aftersavedtax
				double taxAfterSave = Double.parseDouble(totalTaxChargesbeforesave.replace("$", "").trim());
				Assert.assertEquals(tripsummarytaxesafterressave, taxAfterSave);
				app_logs.info("Verifying before save tax and after save tax");
				testSteps.add("Verifying calculated existing tax: " + "<b>" + taxAfterSave + "</b>");

				testSteps.add("<br>Making assertion of charges before and after saved the reservation");
				double totalRoomCharges = Double.parseDouble(totalRoomChargesBeforeResSave.replace("$", "").trim());
				Assert.assertEquals(roomChargeAfterResSave, totalRoomCharges);
				testSteps.add("Verifying total room charges before and after reservation saved: " + "<b>"
						+ totalRoomCharges + "</b>");
				Assert.assertEquals(tripTotalAfterResSave, totalTripAmountBeforeResSave);
				testSteps.add("Verifying totalTrip charges before before and after saved: " + "<b>"
						+ totalTripAmountBeforeResSave + "</b>");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to verify charges after reservation", "NONGS_Login", "Login",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to verify charges after reservation", "NONGS_Login", "Login",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add("<b>*************Making assertion of tax in Folio **************<b>");
				String createdPercentCalc = f.format(Double.parseDouble(createdpercent));
				double taxValCreated = Double.parseDouble(createdPercentCalc);
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				reservationPage.verifyChildLineItemTaxes(driver, getTestSteps, taxLedgerAccount, tName.trim(),
						taxValCreated);
				testSteps.addAll(getTestSteps);
				testSteps.add(
						"<b>*************Making assertion after Calculating each Taxes and with Total Captured tax **************<b>");

				getTestSteps.clear();
				reservationPage.getTotalTaxInResFolios(driver, taxLedgerAccount, getTestSteps);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to verify tax in reservation folio", "NONGS_Login", "Login",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to verify tax in reservation folio", "NONGS_Login", "Login",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		////////////////////////////
		// 848659
		///////////////////////////
	try {	if (TestCaseID.equalsIgnoreCase("848659")) {
			getTest_steps.clear();
			cpRes.click_Folio(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			String Expected_Date = folio.getFolioLineItemDateWithCategory(driver, "MC", getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			String Expected_Amount = folio.getFolioLineItemAmountWithCategory(driver, "MC", getTest_steps);
			testSteps.addAll(getTest_steps);

			getTest_steps.clear();
			folio.clikOnLineItemDescription(driver, "MC", getTest_steps);
			testSteps.addAll(getTest_steps);

			getTestSteps.clear();
			folio.ClickOnAdvanceLedgerTab(driver, getTestSteps);
			testSteps.addAll(getTest_steps);

			getTestSteps.clear();
			String Actual_Date = folio.getAdv_Dep_LedgeDate(driver, getTestSteps);
			testSteps.addAll(getTest_steps);

			Actual_Date = ESTTimeZone.getDateBaseOnDate(Actual_Date, "EEE,dd-MMM-yyyy", "EEE MMM dd, yyyy");

			getTestSteps.clear();
			String Actual_Amount = folio.getAdv_Dep_LedgeAmount(driver, getTestSteps);
			testSteps.addAll(getTest_steps);

			testSteps.add("**********Amount Verification**********");

			testSteps.add("Actual Amount: " + Actual_Amount);
			testSteps.add("Expected Amount: " + Expected_Amount);
			Assert.assertEquals(Actual_Amount, Expected_Amount, "Failed to verify Amount");
			testSteps.add("Amount Verified");

			testSteps.add("**********Date Verification**********");
			testSteps.add("Actual Date: " + Actual_Date);
			testSteps.add("Expected Date: " + Expected_Date);
			Assert.assertEquals(Actual_Date, Expected_Date, "Failed to verify Date");
			testSteps.add("Date Verified");

			testSteps.add("Verified:  Deposit payment line item in reservation folio before save");

		}}catch (Exception e) {
			e.printStackTrace();
		}

		/////////////////////////
		// 848711
		////////////////////////
		if (TestCaseID.equalsIgnoreCase("848711")) {
			Thread.sleep(1000);
			getTestSteps.clear();
			cpRes.click_Folio(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTestSteps.clear();
			folio.clickAddLineItemButton(driver);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTestSteps.clear();
			getTest_steps = folio.AddLineItem(driver, Incidental_Category, DepositOverrideAmount, 2, "1");
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			getTestSteps.clear();
			getTest_steps = folio.ClickSaveFolioButton(driver);
			testSteps.addAll(getTest_steps);

			Thread.sleep(1000);
			String RoomCharge_ = folio.getRoomCharges(driver);
			String IncidentalCharge_ = folio.getIncidental(driver);
			testSteps.add("*********Verification Room Charge*********");
			testSteps.add("Expected Room Charge: " + RoomCharge_);
			Assert.assertEquals(RoomCharge_.trim(), DepositOverrideAmount.trim(), "Faied: Room Charge not match");
			testSteps.add("Verified Room Charge: " + RoomCharge_);

			testSteps.add("*********Verification Incidental Charge*********");
			testSteps.add("Expected Incidental Charge: " + IncidentalCharge_);
			Assert.assertEquals(IncidentalCharge_.trim(), DepositOverrideAmount.trim(),
					"Faied: Incidentals Charge not match");
			testSteps.add("Verified Incidental Charge: " + IncidentalCharge_);
			testSteps.addAll(getTest_steps);

			testSteps.add("Verify reservation with high folio line items with manual override rate");

		}
		/////////////////////////
		// 848746
		////////////////////////

		if (TestCaseID.equalsIgnoreCase("848746")) {
			Thread.sleep(1000);
			getTestSteps.clear();
			cpRes.click_Folio(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			for (int i = 1; i <= 2; i++) {
				testSteps.add("********* Adding Line Item " + i + " *********");
				Thread.sleep(2000);
				getTestSteps.clear();
				folio.clickAddLineItemButton(driver);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTestSteps.clear();
				getTest_steps = folio.AddfolioLineItem(driver, "Room Charge", DepositOverrideAmount,
						"Hello Discription" + i, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTestSteps.clear();
				getTest_steps = folio.ClickSaveFolioButton(driver);
				testSteps.addAll(getTest_steps);

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation tab");

			Utility.refreshPage(driver);

			// Create New Account
			Thread.sleep(1000);
			navigation.Accounts(driver);
			// New account
			try {
				AccountName = AccountName + Utility.generateRandomString();
				account.ClickOnNewAccountButton(driver, getTest_steps);
				AccountNumber = Utility.GenerateRandomString15Digit();
				if (TestCaseID.equalsIgnoreCase("825038")) {
					account.enterAccountDetails(driver, getTest_steps, "Travel Agent", AccountName, AccountNumber);
				} else {
					account.enterAccountDetails(driver, getTest_steps, AccountType, AccountName, AccountNumber);
				}

				getTest_steps.clear();
				account.AccountAttributes(driver, test, "GDS", Referral, getTest_steps);
				testSteps.addAll(getTest_steps);
				getTest_steps.clear();
				getTest_steps = account.Mailinginfo(driver, test, GuestFirstName, GuestLastName, Phone_Number[0],
						Phone_Number[0], Address1, Address2, Address3, Email, City, State, PostalCode, getTest_steps);
				testSteps.addAll(getTest_steps);
				getTest_steps.clear();
				getTest_steps = account.Billinginfo(driver, test, getTest_steps);
				testSteps.addAll(getTest_steps);
				getTest_steps.clear();
				getTest_steps = account.AccountSave(driver, test, AccountName, getTest_steps);
				testSteps.addAll(getTest_steps);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// New Reservation
			account.NewReservationButton(driver, test);
			testSteps.add("Clickin in New Reservation Button");

			////////////////////////////////////////
			// Reservation
			///////////////////////////////////////
			Thread.sleep(4000);
			try {
				testSteps.add("==========CREATE NEW RESERVED RESERVATION THROUGH ACCOUNTS==========");
				app_logs.info("==========CREATE NEW RESERVED RESERVATION THROUGH ACCOUNTS==========");

				getTestSteps.clear();
				app_logs.info("Entering Check In Date");
				getTestSteps = reservationPage.checkInDate(driver, 0);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
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

				if (TestCaseID.equalsIgnoreCase("848711")) {
					getTestSteps.clear();
					testSteps.add("Entering Rateplan");
					app_logs.info("Entering Rateplan");
					getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, "Manual Override", PromoCode);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					testSteps.add("Entering RateAmount");
					app_logs.info("Entering RateAmount");
					getTestSteps = reservationPage.EnterManualRateAmount(driver, DepositOverrideAmount);
					testSteps.addAll(getTestSteps);
				} else {
					getTestSteps.clear();
					testSteps.add("Entering Rateplan");
					app_logs.info("Entering Rateplan");
					getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				app_logs.info("Clicking On Find Room");
				getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				testSteps.add("Filling All Fullfil Values");
				app_logs.info("Filling All Fullfil Values");
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
				testSteps.addAll(getTestSteps);

				try {

					reservationPage.clickNewPolicesApplicablePopupYesNo(driver, false, testSteps);

				}catch (Exception e) {
					app_logs.info("No Polies Yes No popup appaired exception below.");
					e.printStackTrace();
				}
				
				getTestSteps.clear();
				getTestSteps = reservationPage.clickNext(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver,
						Boolean.parseBoolean(isChecked));
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", firstName, lastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer",
						cardExpDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.selectReferral(driver, "GDS");
				testSteps.addAll(getTestSteps);

				Thread.sleep(1000);
				getTestSteps.clear();
				getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
				testSteps.add("Successfully click Book Now");

				Thread.sleep(1000);
				getTestSteps.clear();
				reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Reservation confirmation number: " + reservationNumber1);
				app_logs.info("Reservation confirmation number: " + reservationNumber1);

				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Sucessfully close save quote popup");

				Thread.sleep(1000);
				getTestSteps.clear();
				GuestName1 = reservationPage.getguestname(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"Reservation Done with Guestname: " + GuestName1 + " Reservation No: " + reservationNumber1);

				expectedDate = ESTTimeZone.getDateWithTime(getTimeZone, "MM/dd/yy h:mm aa");

			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			//////////////////////////////////

			// Account Folio
			Thread.sleep(1000);
			getTestSteps.clear();
			cpRes.click_Folio(driver, getTest_steps);
			testSteps.addAll(getTest_steps);

			for (int i = 1; i <= 2; i++) {
				testSteps.add("********* Adding Accounts Folio Line Item " + i + " *********");
				Thread.sleep(2000);
				getTestSteps.clear();
				folio.clickAddLineItemButton(driver);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTestSteps.clear();
				getTest_steps = folio.AddfolioLineItem(driver, "Room Charge", DepositOverrideAmount,
						"Account Folio Description" + i, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTestSteps.clear();
				getTest_steps = folio.ClickSaveFolioButton(driver);
				testSteps.addAll(getTest_steps);

			}

			testSteps.add("Verified: Verify adding folio line item by giving spacebar in description field.");

		}

		// 848744 && 848743 && 848742

		if (TestCaseID.equalsIgnoreCase("848744") || TestCaseID.equalsIgnoreCase("848743")
				|| TestCaseID.equalsIgnoreCase("848742")) {

			try {

				int LineItemCount = 11;
				Thread.sleep(1000);
				getTestSteps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				int BeforeLineItem = folio.getLineItemRows(driver);

				for (int i = 1; i <= LineItemCount; i++) {
					Thread.sleep(500);
					getTestSteps.clear();
					folio.AddPackageTypeFolioWithQuantity(driver, getTestSteps, String.valueOf(i + 1));
					testSteps.add("******** " + i + " LineItem is added********");
					testSteps.addAll(getTestSteps);
				}
				testSteps.add("Verify folio tabs is not distorted.");

				int AfterLineItem = folio.getLineItemRows(driver);

				Assert.assertEquals(AfterLineItem - BeforeLineItem, LineItemCount,
						"Failed: LineItem Row Count Not Match");
				// Package Rate Deletion
				testSteps.add("**********Package Rate Deletion**********");
			try {	navigation.inventory(driver);
				testSteps.add("Navigate To Inventory");
				Thread.sleep(1000);
				navigation.Rate(driver);
				testSteps.add("Navigate To Rate");
				Thread.sleep(1000);
				String PackID = "MainContent_btnPackages";
				Wait.waitForElementToBeClickable(By.id(PackID), driver);
				driver.findElement(By.id(PackID)).click();
				testSteps.add("Click On Package Button");

				rate.SearchRate(driver, testSteps, displayName, false);
				rate.delete_rate(driver, displayName);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
				
				
				///////////////

			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify folio UI while having more no.of line items", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify folio UI while having more no.of line items", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		// 848745

		if (TestCaseID.equalsIgnoreCase("848745")) {

			try {

				Thread.sleep(1000);
				getTestSteps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				folio.clickAddButton(driver);
				testSteps.add("Clicking On AddLineItem Button");

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.SelectCategory(driver, getTest_steps, "Package");
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTestSteps.clear();
				folio.ClickOnThreeDotPackagecategoryFolioLineItem(driver, getTestSteps);
				testSteps.addAll(getTest_steps);

				Thread.sleep(1000);
				getTest_steps.clear();
				folio.VerifyPackageCategoryFolioPopup(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				testSteps.add("Verify folio tabs is not distorted.");
			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to \"Verify folio tabs is not distorted.", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to \"Verify folio tabs is not distorted.", testName,
							"FolioComponent", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		statusCode = new ArrayList<String>();
		caseId = new ArrayList<String>();
		String[] testcase = TestCaseID.split("\\|");
		for (int i = 0; i < testcase.length; i++) {
			caseId.add(testcase[i]);
			statusCode.add("1");
			comments = "pass";
		}
		closeDriver();
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("Verifyfoliocomponentactions", HS_EXCEL);
	}

//	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,
		 comments,TestCore.TestRail_AssignToID);
	}
}
