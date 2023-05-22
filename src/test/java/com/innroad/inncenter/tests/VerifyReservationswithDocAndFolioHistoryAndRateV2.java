//package com.innroad.inncenter.tests;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.sql.Driver;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.apache.tools.ant.taskdefs.Move;
//import org.apache.xmlbeans.impl.xb.xsdschema.TotalDigitsDocument.TotalDigits;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.seleniumhq.jetty9.server.Authentication.Failed;
//import org.testng.Assert;
//import org.testng.SkipException;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import com.innroad.inncenter.pageobjects.Account;
//import com.innroad.inncenter.pageobjects.Admin;
//import com.innroad.inncenter.pageobjects.CPReservationPage;
//import com.innroad.inncenter.pageobjects.Folio;
//import com.innroad.inncenter.pageobjects.Groups;
//import com.innroad.inncenter.pageobjects.Navigation;
//import com.innroad.inncenter.pageobjects.Policies;
//import com.innroad.inncenter.pageobjects.Properties;
//import com.innroad.inncenter.pageobjects.Rate;
//import com.innroad.inncenter.pageobjects.RatePackage;
//import com.innroad.inncenter.pageobjects.RatesGrid;
//import com.innroad.inncenter.pageobjects.ReservationSearch;
//import com.innroad.inncenter.pageobjects.Tax;
//import com.innroad.inncenter.testcore.TestCore;
//import com.innroad.inncenter.utils.APIException;
//import com.innroad.inncenter.utils.ESTTimeZone;
//import com.innroad.inncenter.utils.RetryFailedTestCases;
//import com.innroad.inncenter.utils.Utility;
//import com.innroad.inncenter.waits.Wait;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.model.Category;
//import com.snowtide.pdf.layout.t;
//
//import groovyjarjarantlr4.v4.parse.ANTLRParser.element_return;
//import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;
//
//public class VerifyReservationswithDocAndFolioHistoryAndRateV2 extends TestCore {
//
//	private WebDriver driver = null;
//
//	public static String test_description = "";
//	public static String test_catagory = "";
//
//	static ExtentTest test;
//	static ExtentReports report;
//	ArrayList<String> caseId = new ArrayList<String>();
//	ArrayList<String> statusCode = new ArrayList<String>();
//	String comments;
//
//	HashMap<String, String> ItemLineStatusBeforeRollBack = new HashMap<String, String>();
//	HashMap<String, String> itemLineStatusAfterRollBack = new HashMap<String, String>();
//
//	// @BeforeTest(alwaysRun = true)
//	public void checkRunMode() {
//
//		String testName = this.getClass().getSimpleName().trim();
//		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
//		if (!Utility.isExecutable(testName, excel))
//			throw new SkipException("Skipping the test - " + testName);
//	}
//
//	@Test(dataProvider = "getData", groups = "Inventory")
//	public void verifyFolioActions(String TestCaseID, String caseName, String displayName, String Policy_T,
//			String ratePlan, String firstName, String lastName, String IsAssign, String isChecked, String paymentType,
//			String cardNumber, String nameOnCard, String cardExpDate, String Action, String CheckInDate,
//			String CheckOutDate, String Adults, String Children, String Rateplan, String PromoCode, String IsSplitRes,
//			String roomclassabb, String RoomClass, String IsDepositOverride, String DepositOverrideAmount,
//			String IsAddMoreGuestInfo, String Salutation, String GuestFirstName, String GuestLastName,
//			String PhoneNumber, String AltenativePhone, String Email, String Account, String AccountType,
//			String Address1, String Address2, String Address3, String City, String Country, String State,
//			String PostalCode, String IsGuesProfile, String TravelAgent, String MarketSegment, String Referral,
//			String Description, String Incidental_Category, String Incidental_PerUnit, String Incidental_Quantity,
//			String TaxName, String taxLedgerAccount, String TaxdisplayName, String description, String value,
//			String category, String MRBReservation,String Version,String OverRideRate) throws ParseException, Exception {
//
//		ArrayList<String> testSteps = new ArrayList<>();
//		ArrayList<String> getTestSteps = new ArrayList<>();
//		ArrayList<String> scriptName = new ArrayList<>();
//		ArrayList<String> testCategory = new ArrayList<>();
//		ArrayList<String> testDescription = new ArrayList<>();
//
//		String[] roomClass = RoomClass.replace("|", " ").split(" ");
//		String[] roomClassabb = roomclassabb.replace("|", " ").split(" ");
//		String[] Phone_Number = PhoneNumber.replace("|", " ").split(" ");
//		String[] rate_Plan = ratePlan.replace("|", " ").split(" ");
//
//		String testName = caseName;
//		String testDescriptionForLink = "";
//		if (!Utility.validateString(TestCaseID)) {
//			caseId.add(TestCaseID);
//			statusCode.add("5");
//			testDescriptionForLink = caseName + "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/"
//					+ TestCaseID + "' target='_blank'>" + "Click here to open TestRail: C" + TestCaseID + "</a>";
//		} else {
//			String[] testcase = TestCaseID.split("\\|");
//			for (int i = 0; i < testcase.length; i++) {
//				caseId.add(testcase[i]);
//				statusCode.add("5");
//				testDescriptionForLink = testDescriptionForLink + "<br>"
//						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/" + testcase[i]
//						+ "' target='_blank'>" + "Click here to open TestRail: C" + testcase[i] + "</a>";
//
//			}
//
//		}
//		test_description = testDescriptionForLink;
//		test_catagory = "Folio";
//		scriptName.add(testName);
//		testDescription.add(test_description);
//		testCategory.add(test_catagory);
//
//		app_logs.info("##################################################################################");
//		app_logs.info("EXECUTING: " + testName + " TEST.");
//		app_logs.info("##################################################################################");
//
//		// MBR Reservation
//		Double depositAmount = 0.0;
//		Double paidDeposit = 0.0;
//		String reservation = null;
//		String status = null;
//		ArrayList<String> Rooms = new ArrayList<String>();
//		ArrayList<String> roomCost = new ArrayList<String>();
//		ArrayList<String> checkInDates = new ArrayList<>();
//		ArrayList<String> checkOutDates = new ArrayList<>();
//		ArrayList<String> getTest_steps = new ArrayList<>();
//
//		CPReservationPage cpRes = new CPReservationPage();
//		Navigation navigation = new Navigation();
//		Account account = new Account();
//		Admin admin = new Admin();
//		Rate rate = new Rate();
//		RatesGrid ratesGrid = new RatesGrid();
//		Policies policies = new Policies();
//		CPReservationPage reservationPage = new CPReservationPage();
//		Folio folio = new Folio();
//		Tax tax = new Tax();
//		DecimalFormat f = new DecimalFormat("##.00");
//		Properties properties = new Properties();
//		System.out.print("RoomClass: " + roomClass[0]);
//		String tempraryRoomClassName = roomClass[0];
//		String randomNumber = Utility.GenerateRandomNumber();
//		String roomClass_i = roomClass[0] + randomNumber;
//		displayName = roomClass_i;
//		lastName = lastName + randomNumber;
//		firstName = firstName + randomNumber;
//		Policy_T = Policy_T + randomNumber;
//		boolean excludeTaxExempt = true;
//		boolean vat = false;
//		String adult = "2";
//		String children = "1";
//		String reservationNumber1 = "";
//		String reservationNumber2 = "";
//		String GuestName1 = "";
//		String GuestName2 = "";
//		String ResTotalBalance2 = "";
//		String AccountName = "Account_Name";
//		String AccountNumber = "";
//		String getTimeZone = "";
//		String expectedDate = "";
//		String randomString = "";
//		String tName = "";
//		String createdpercent = "";
//		String tripSummaryTaxesWithCurrency = "";
//		String roomCharge = "";
//		String TripTotalCharge = "";
//		String getGuestName = "";
//		String totalRoomChargesBeforeResSave = null;
//		String totalTripChargesbeforesave = null;
//		String totalTaxChargesbeforesave = null;
//		double tripsummarytaxesafterressave = 0.0;
//		String RateplanForV2 = "";
//		String override_Amount = OverRideRate;
//		
//
//		////////////////////////////////////////
//		// Login
//		///////////////////////////////////////
//		
//		
//		
//		
//		if(Version.equalsIgnoreCase("2")) {
//			try {
//				if (!Utility.insertTestName.containsKey(testName)) {
//					Utility.insertTestName.put(testName, testName);
//					Utility.reTry.put(testName, 0);
//				} else {
//					Utility.reTry.replace(testName, 1);
//				}
//				driver = getDriver();
//				
//				try {
//					loginRateV2(driver);
//				} catch (Exception e) {
//					driver.switchTo().alert().accept();
//					Actions actions = new Actions(driver);
//
//					actions.sendKeys(Keys.ENTER);
//					loginRateV2(driver);
//					
//				}
//				testSteps.add("Logged into the application");
//				app_logs.info("Logged into the application");
//			} catch (Exception e) {
//				e.printStackTrace();
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
//					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			} catch (Error e) {
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			}
//		}
//		else {
//			try {
//				if (!Utility.insertTestName.containsKey(testName)) {
//					Utility.insertTestName.put(testName, testName);
//					Utility.reTry.put(testName, 0);
//				} else {
//					Utility.reTry.replace(testName, 1);
//				}
//				driver = getDriver();
//				login_CP(driver);
//				testSteps.add("Logged into the application ");
//				app_logs.info("Logged into the application");
//			} catch (Exception e) {
//				e.printStackTrace();
//
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
//					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//
//			} catch (Error e) {
//				e.printStackTrace();
//
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
//					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			}
//			
//		}
//
//
//		//////////////////////////
//		//RateGrid
//		//////////////////////////
//		if(TestCaseID.equalsIgnoreCase("825619"))
//		{
//			try {
//				navigation.inventory(driver);
//				testSteps.add("Navigating To Inventory");
//				
//				getTestSteps.clear();
//				getTestSteps = navigation.RatesGrid(driver);
//				testSteps.addAll(getTestSteps);
//				
//				RateplanForV2 = ratesGrid.getRateplanFromRateGridDropDown(driver);
//				testSteps.add("Rate Plan: <b>"+RateplanForV2);
//				
//				getTestSteps.clear();
//				
//				
//				override_Amount = ratesGrid.OverRideRatePlanWithRoomClass(driver, getTestSteps, roomClass[0], OverRideRate);
//				testSteps.addAll(getTestSteps);
//				
//			} catch (Exception e) {
//
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//					Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//
//			} catch (Error e) {
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//					Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			}
//			
//			
//		}
//		
//		
//		
//		
//
//		///////////////////////////////
//		// get user name from admin
//		//////////////////////////////
//		if (TestCaseID.equalsIgnoreCase("825623")) {
//		
//		
//		try {
//			testSteps.add("Get user name from admin");
//			  navigation.admin(driver);
//			  testSteps.add("Click on admin");
//			  admin.clickOnUserTab(driver);
//			  testSteps.add("Click on users");
//			  admin.clickOnSearchButton(driver);
//			  testSteps.add("Click on search button");
//			  getGuestName = admin.getUserName(driver, Utility.login_CP.get(2));
//			  testSteps.add("User name: "+getGuestName);
//
//			  navigation.navigateToReservations(driver);
//			  
//		} catch (Exception e) {
//
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//				Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//				Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//		
//		}
//		
//		
//		
//		
//		
//		
//		////////////////////////////////////////
//		// Navigating To Reservation
//		///////////////////////////////////////
//
//		try {
//			Thread.sleep(1000);
//			navigation.Reservation_Backward_3(driver);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//				Utility.updateReport(e, "Failed to Navigating To Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//				Utility.updateReport(e, "Failed to Navigating To Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//		////////////////////////////////////////
//		// Reservation
//		///////////////////////////////////////
//
//		try {
//			Utility.refreshPage(driver);
//			
//			testSteps.add("==========CREATE NEW RESERVED RESERVATION==========");
//			app_logs.info("==========CREATE NEW RESERVED RESERVATION==========");
//
//			getTestSteps.clear();
//			Thread.sleep(4000);
//			app_logs.info("Clicking on New Reservation");
//			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			testSteps.add("Entering Check In Date");
//			app_logs.info("Entering Check In Date");
//			getTestSteps = reservationPage.checkInDate(driver, 0);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			testSteps.add("Entering Check Out Date");
//			app_logs.info("Entering Check Out Date");
//			getTestSteps = reservationPage.checkOutDate(driver, +1);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			testSteps.add("Entering Adult Value");
//			app_logs.info("Entering Adult Value");
//			getTestSteps = reservationPage.enterAdult(driver, adult);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			testSteps.add("Entering Children Value");
//			app_logs.info("Entering Children Value");
//			getTestSteps = reservationPage.enterChildren(driver, children);
//			testSteps.addAll(getTestSteps);
//
//			
//			if(TestCaseID.equalsIgnoreCase("825619"))
//			{
//				getTestSteps.clear();
//				testSteps.add("Entering Rateplan");
//				app_logs.info("Entering Rateplan");
//				getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, RateplanForV2, PromoCode);
//				testSteps.addAll(getTestSteps);
//			}
//			else {
//				getTestSteps.clear();
//				testSteps.add("Entering Rateplan");
//				app_logs.info("Entering Rateplan");
//				getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
//				testSteps.addAll(getTestSteps);
//			}
//			
//			
//		
//
//			getTestSteps.clear();
//			app_logs.info("Clicking On Find Room");
//			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
//			testSteps.addAll(getTestSteps);
//
//			testSteps.add("Filling All Fullfil Values");
//			app_logs.info("Filling All Fullfil Values");
//			getTestSteps.clear();
//			getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver,
//					Boolean.parseBoolean(isChecked));
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", firstName, lastName);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer",
//					cardExpDate);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = reservationPage.selectReferral(driver, "GDS");
//			testSteps.addAll(getTestSteps);
//
//
//			Thread.sleep(1000);
//			getTestSteps.clear();
//			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
//			testSteps.add("Successfully click Book Now");
//
//			Thread.sleep(1000);
//			getTestSteps.clear();
//			reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
//			testSteps.addAll(getTestSteps);
//			testSteps.add("Reservation confirmation number: " + reservationNumber1);
//			app_logs.info("Reservation confirmation number: " + reservationNumber1);
//
//			Thread.sleep(1000);
//			getTestSteps.clear();
//			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
//			testSteps.addAll(getTestSteps);
//			testSteps.add("Sucessfully close save quote popup");
//
//			Thread.sleep(1000);
//			getTestSteps.clear();
//			GuestName1 = reservationPage.getguestname(driver, getTestSteps);
//			testSteps.addAll(getTestSteps);
//
//			testSteps.add(
//					"Reservation Done with Guestname: " + GuestName1 + " Reservation No: " + reservationNumber1);
//
//			expectedDate = ESTTimeZone.getDateWithTime(getTimeZone, "MM/dd/yy h:mm aa");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
//				Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			e.printStackTrace();
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
//				Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//		
//		
//		////////////////////////////
//		// 825619
//		///////////////////////////
//		
//		if(TestCaseID.equalsIgnoreCase("825619")) {
//			try {
//				getTest_steps.clear();
//				folio.click_Folio(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				
//				String TotalBalnace = folio.getToatalBalance(driver);
//				testSteps.add("Total Balance: <b>"+TotalBalnace);
//				
//				assertEquals(TotalBalnace, override_Amount,"Failed To Verify Amount");
//				
//				testSteps.add("<b>Validate Rate Override (without description) on Folio for Single Reservation<b>");
//				
//				
//				
//			} catch (Exception e) {
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//					Utility.updateReport(e, "Failed to Validate Rate Override (without description) on Folio for Single Reservation", testName, "Incidental", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//
//			} catch (Error e) {
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//					Utility.updateReport(e, "Failed to Validate Rate Override (without description) on Folio for Single Reservation", testName, "Incidental", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			}
//
//		}
//		
//		
//		
//		
//		
//		////////////////////////////
//		// 825623
//		///////////////////////////
//
//		if (TestCaseID.equalsIgnoreCase("825623")) {
//
//			try {
//				getTest_steps.clear();
//				folio.click_Folio(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				getTest_steps = folio.clickAddLineItemButton(driver);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				getTest_steps = folio.AddFolioLineItem(driver, Incidental_Category, DepositOverrideAmount);
//				testSteps.addAll(getTest_steps);
//				
//				
//				getTest_steps.clear();
//				getTest_steps = folio.ClickSaveFolioItemsButton(driver);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				folio.Click_History(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				
//				getTest_steps.clear();
//				folio.get_HistoryDescription(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				folio.click_Folio(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				
//				getTest_steps.clear();
//				folio.clikOnLineItemDescription(driver, Incidental_Category, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				folio.FolioItemDetailPopupWithCategory(driver, getTest_steps, DepositOverrideAmount, "Folio Description", "Folio Note",Incidental_Category);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				getTest_steps = folio.ClickSaveFolioItemsButton(driver);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				String BalanceAfterAddingAmount = folio.getTotalBalance(driver);
//				testSteps.add("Total Balnace: <b>"+BalanceAfterAddingAmount);
//				
//				getTest_steps.clear();
//				folio.Click_History(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				
//				getTest_steps.clear();
//				folio.get_HistoryDescription(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				folio.click_Folio(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				
//				getTest_steps.clear();
//				getTest_steps = folio.voidLineItem(driver, Incidental_Category, "Void Note");
//				testSteps.addAll(getTest_steps);
//				
//				
//				getTest_steps.clear();
//				getTest_steps = folio.ClickSaveFolioItemsButton(driver);
//				testSteps.addAll(getTest_steps);
//				
//				getTest_steps.clear();
//				folio.Click_History(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//				
//				getTest_steps.clear();
//				folio.get_HistoryDescription(driver, getTest_steps);
//				testSteps.addAll(getTest_steps);
//				
//			
//				// INCIDENTAL IN HISTORY
//				try {
//					testSteps.add("==========Verify user name and time and date in history tab after updated reservation==========");
//
//					getTest_steps.clear();
//					getTest_steps = reservationPage.ClickOnHistory(driver);
//					testSteps.addAll(getTest_steps);
//				
//					testSteps.add("Expected guest name: "+getGuestName);
//					String getGuestNameFromHistoryTab = folio.get_HistoryUserName(driver, getTestSteps);
//					testSteps.add("Found: "+getGuestNameFromHistoryTab);
//					if (getGuestNameFromHistoryTab.equalsIgnoreCase(getGuestName)) {
//						testSteps.add("Verified guest name is mathching in histroy tab after updated reservation");
//					}
//					else {
//						testSteps.add("Failed: Guest name is mismatching  in history tab");
//					}
//
//				} catch (Exception e) {
//					if (Utility.reTry.get(testName) == Utility.count) {
//						RetryFailedTestCases.count = Utility.reset_count;
//						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//						Utility.updateReport(e, "Failed to verify incidental in hostory", testName, "Incidental", driver);
//					} else {
//						Assert.assertTrue(false);
//					}
//
//				} catch (Error e) {
//					if (Utility.reTry.get(testName) == Utility.count) {
//						RetryFailedTestCases.count = Utility.reset_count;
//						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//						Utility.updateReport(e, "Failed to verify incidental in hostory", testName, "Incidental", driver);
//					} else {
//						Assert.assertTrue(false);
//					}
//				}
//				
//				
//				
//				
//				
//				
//				
//				
//				
//				
//				
//				
//				
//				
//
//				testSteps.add(
//						"<b>Verify history log is getting created when user adds/modifies any line item in folio tab for the reservation.<b>");
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//					Utility.updateReport(e,
//							"Failed to Verify history log is getting created when user adds/modifies any line item in folio tab for the reservation.",
//							"NONGS_Login", "Login", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			} catch (Error e) {
//				e.printStackTrace();
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//					Utility.updateReport(e,
//							"Failed to Verify history log is getting created when user adds/modifies any line item in folio tab for the reservation.",
//							"NONGS_Login", "Login", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//			}
//
//		}
//
//		comments = "Verified Folio Component Actions";
//		RetryFailedTestCases.count = Utility.reset_count;
//		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//	}
//
//	@DataProvider
//	public Object[][] getData() {
//		// return test data from the sheet name provided
//		return Utility.getData("VerifyReservatioswithDocAndRate", excel);
//	}
//
////	@AfterClass(alwaysRun = true)
////	public void closeDriver() throws MalformedURLException, IOException, APIException {
////		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
////	}
//}
