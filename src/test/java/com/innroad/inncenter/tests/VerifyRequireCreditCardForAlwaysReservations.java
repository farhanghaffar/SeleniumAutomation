package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.OverView;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyRequireCreditCardForAlwaysReservations extends TestCore{

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	RoomClass roomClass = new RoomClass();
	public static String roomClassName;
	public static String roomClassAbbrivation;
	
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	List<LocalDate> datesList = new ArrayList<LocalDate>();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	List<String> date = new ArrayList<String>();
	List<String> day = new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	ArrayList<String> checkInDates = new ArrayList<>();


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void verifyRequireCreditCardForAlwaysReservations(String testCaseID,String ratePlan, String roomClassName) throws ParseException {
		test_name = "VerifyRequireCreditCardForAlwaysReservations";
		test_description = "VerifyRequireCreditCardForAlwaysReservations"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848577' target='_blank'>"
				+ "Click here to open TestRail: C848577</a><br>";
		Navigation navigation = new Navigation();
		CPReservationPage reservationPage = new CPReservationPage();
		Properties prop = new Properties();
		ReservationHomePage homePage = new ReservationHomePage();
		Login login = new Login();
		String testName = null;
		
		String randomString = Utility.GenerateRandomString();
		String salutation = "Mr."; 
		String guestFirstName = "VerifyRes" + randomString; 
		String guestLastName = "Realization" + randomString;
		String paymentType = "MC"; 
		String marketSegment = "GDS"; 
		String referral = "Other";
		String guaranteedStatus = "Guaranteed";
		

		if(!Utility.validateString(testCaseID)) {
			caseId.add("848577");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		test_catagory = "Verification";
		testName = test_name;
		
		
		String timeZone = "US/Eastren";
		String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		String checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
			
		String maxAdult = "1";
		String maxPerson = "0";
		String property = "Automation OTA";
		checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "MM/dd/yyyy"));
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			driver = getDriver();
			login_Autoota(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
	
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String reservationNumber = "";
		
		String cardNumber = "5454545454545454"; 
		String nameOnCard = guestFirstName; 
		String cardExpDate = "12/23";
		
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848577' target='_blank'>"
					+ "<b>Require credit card for Always Reservations.</a> =====");

			navigation.Inventory(driver, testSteps);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			
			navigation.Setup(driver);
			testSteps.add("Navigate to Setup");
			
			navigation.properties(driver);
			
			navigation.open_Property(driver, testSteps, property);			
			navigation.clickPropertyOptions(driver, testSteps);
			
			prop.clickRequireCreditCardForGuaranteeCheckbox(driver, testSteps, true);
			prop.clickAlwaysRadioButton(driver, testSteps, true);
			prop.SaveButton(driver);
			prop.PublishButton(driver);

			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");
			Wait.wait10Second();
			login.logout(driver);
			testSteps.add("Logged out");
			app_logs.info("Logged out");
			

			login_Autoota(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");		
			
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
		try {
			
			reservationPage.click_NewReservation(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			

			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);									

			homePage.changeReservationStatusInCreationPage(driver, testSteps, guaranteedStatus);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			reservationPage.SelectReferral(driver, referral);
			
			reservationPage.clickBookNow(driver, testSteps);
			
			testSteps.add("Verify Toaster message with text 'Please select only credit card as a payment method.' is showing");
			boolean isDisplay = homePage.isCreditCradRequiredToasterShowing(driver);
			assertTrue(isDisplay, "Failed : Toaster message with text 'Please select only credit card as a payment method.' is not showing");
			testSteps.add("Verified Toaster message with text 'Please select only credit card as a payment method.' is showing");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");
			
			statusCode.add(0, "1");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Verify Toaster message with text 'Please select only credit card as a payment method.' is showing", "VerifyToasterMessage", test_description, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Verify Toaster message with text 'Please select only credit card as a payment method.' is showing", "VerifyToasterMessage", test_description, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {	
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848576' target='_blank'>"
			+ "<b>Require credit card for guarantee For Guaranteed Reservations.</a> =====");

			navigation.Inventory(driver, testSteps);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			
			navigation.Setup(driver);
			testSteps.add("Navigate to Setup");
			
			navigation.properties(driver);
			
			navigation.open_Property(driver, testSteps, property);			
			navigation.clickPropertyOptions(driver, testSteps);
			
			prop.clickRequireCreditCardForGuaranteeCheckbox(driver, testSteps, true);
			prop.clickForGuaranteedReservationRadioButton(driver, testSteps, true);
			prop.SaveButton(driver);
			
			prop.PublishButton(driver);

			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");
		
			Wait.wait10Second();
			login.logout(driver);
			testSteps.add("Logged out");
			app_logs.info("Logged out");
			

			login_Autoota(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");		
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		try {
		
			reservationPage.click_NewReservation(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			

			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);									

			homePage.changeReservationStatusInCreationPage(driver, testSteps, guaranteedStatus);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			reservationPage.SelectReferral(driver, referral);
			
			reservationPage.clickBookNow(driver, testSteps);

			testSteps.add("Verify Toaster message with text 'Please select only credit card as a payment method.' is showing");
			boolean isDisplay = homePage.isCreditCradRequiredToasterShowing(driver);
			assertTrue(isDisplay, "Failed : Toaster message with text 'Please select only credit card as a payment method.' is not showing");
			testSteps.add("Verified Toaster message with text 'Please select only credit card as a payment method.' is showing");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");
			
			statusCode.add(1, "1");
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


	try {	
		testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848578' target='_blank'>"
		+ "<b>Deposit is required to save Guaranteed reservation.</a> =====");
	
		navigation.Inventory(driver, testSteps);
		app_logs.info("Navigate Inventory");
		testSteps.add("Navigate Inventory");
		
		navigation.Setup(driver);
		testSteps.add("Navigate to Setup");
		
		navigation.properties(driver);
		
		navigation.open_Property(driver, testSteps, property);			
		navigation.clickPropertyOptions(driver, testSteps);
		
		prop.clickRequireCreditCardForGuaranteeCheckbox(driver, testSteps, true);
		prop.clickForGuaranteedReservationRadioButton(driver, testSteps, true);
		prop.clickRequireDepositForGuaranteeCheckbox(driver, testSteps, true);
		
		prop.SaveButton(driver);
		
		prop.PublishButton(driver);

		navigation.navigateToReservations(driver);
		testSteps.add("Navigate Reservations");
		app_logs.info("Navigate Reservations");
	
		Wait.wait10Second();
		login.logout(driver);
		testSteps.add("Logged out");
		app_logs.info("Logged out");
		

		login_Autoota(driver);
		testSteps.add("Logged into the application");
		app_logs.info("Logged into the application");		
		
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	
	try {
		reservationPage.click_NewReservation(driver, testSteps);									

		getTestSteps.clear();
		getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
		testSteps.addAll(getTestSteps);


		reservationPage.enter_Adults(driver, testSteps, maxAdult);
		reservationPage.enter_Children(driver, testSteps, maxPerson);
		reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
		reservationPage.clickOnFindRooms(driver, testSteps);
		String isAssign = "Yes";
		

		reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
		reservationPage.clickNext(driver, testSteps);									

		homePage.changeReservationStatusInCreationPage(driver, testSteps, guaranteedStatus);
		
		getTestSteps.clear();
		getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
				guestLastName);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
		testSteps.addAll(getTestSteps);

		reservationPage.SelectReferral(driver, referral);
		
		reservationPage.clickBookNow(driver, testSteps);

		testSteps.add("Verify Error message with text 'Please select only credit card as a payment method.' is showing");
		boolean isDisplay = homePage.isCreditCradRequiredToasterShowing(driver);
		assertTrue(isDisplay, "Failed : Error message with text 'Please select only credit card as a payment method.' is not showing");
		testSteps.add("Verified Error message with text 'Please select only credit card as a payment method.' is showing");

		reservationPage.closeReservationTab(driver);
		testSteps.add("Closed reservation tab");
		app_logs.info("Closed reservation tab");
		
		statusCode.add(2, "1");
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to create res", "NONGS_Login", "create res", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to create res", "NONGS_Login", "create res", driver);
		} else {
			Assert.assertTrue(false);
		}
	}


	try {	
		navigation.Inventory(driver, testSteps);
		app_logs.info("Navigate Inventory");
		testSteps.add("Navigate Inventory");
		
		navigation.Setup(driver);
		testSteps.add("Navigate to Setup");
		
		navigation.properties(driver);
		
		navigation.open_Property(driver, testSteps, property);			
		navigation.clickPropertyOptions(driver, testSteps);
		
		prop.clickRequireCreditCardForGuaranteeCheckbox(driver, testSteps, false);
		prop.clickRequireDepositForGuaranteeCheckbox(driver, testSteps, false);
		
		prop.SaveButton(driver);
		
		prop.PublishButton(driver);

		navigation.navigateToReservations(driver);
		testSteps.add("Navigate Reservations");
		app_logs.info("Navigate Reservations");
	
		Wait.wait10Second();
		login.logout(driver);
		testSteps.add("Logged out");
		app_logs.info("Logged out");
		statusCode.add(3, "1");
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	String DepositPolicyName="deposit"+Utility.GenerateRandomNumber(300, 3000);
	String GuestMustPayPercentage="50";
	String DepositCustomText="New Deposit policy creation";
	Policies policies = new Policies();
	String Chargestype="Total Charges";
	/*//need to add id in tesdata
	try {
		testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/825202' target='_blank'>"
				+ "<b>Verify Payment pop-up displaying after changing status from Reserved/Confirmed to Guaranteed.</a> =====");

		login_CP(driver);
		testSteps.add("Logged into the application");
		app_logs.info("Logged into the application");		
		
		// After login
		testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
		app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
		navigation.Inventory(driver, testSteps);
		navigation.policies(driver);
		testSteps.add("Navigated to policies");
	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	}


	try {
		app_logs.info("************** Creatin a deposit policy *******************");
		testSteps.add("************** Creatin a deposit policy *******************");
		policies.deleteAllPolicies(driver, testSteps,"Deposit","deposit");
		policies.ClickNewPolicybutton(driver);
		policies.Enter_Policy_Name(driver, DepositPolicyName, testSteps);
		policies.Deposit_Policy_Attributes(driver, Chargestype, GuestMustPayPercentage, testSteps);
		policies.Enter_Policy_Desc(driver, DepositCustomText, DepositCustomText);
		policies.Associate_Sources(driver);
		policies.Associate_Seasons(driver);
		policies.Associate_RoomClasses(driver, roomClassName);
		policies.Associate_RatePlan(driver, ratePlan);
		policies.Save_Policy(driver);
		navigation.cpReservationBackward(driver);
	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	

	try {
	
		reservationPage.click_NewReservation(driver, testSteps);									

		getTestSteps.clear();
		getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
		testSteps.addAll(getTestSteps);


		reservationPage.enter_Adults(driver, testSteps, maxAdult);
		reservationPage.enter_Children(driver, testSteps, maxPerson);
		reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
		reservationPage.clickOnFindRooms(driver, testSteps);
		String isAssign = "Yes";
		

		reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
		reservationPage.clickNext(driver, testSteps);									

		getTestSteps.clear();
		getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
				guestLastName);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
		testSteps.addAll(getTestSteps);
		
		reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);

		reservationPage.SelectReferral(driver, referral);
		
		reservationPage.clickBookNow(driver, testSteps);
		reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
		app_logs.info("reservationNumber : " + reservationNumber);
		String reservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);		
		reservationPage.clickCloseReservationSavePopup(driver, testSteps);
		testSteps.add("Verify reservation status 'Reserved' is reserved");
		Utility.verifyEquals(reservationStatus.toLowerCase(), "Reserved".toLowerCase(), testSteps);
		
		reservationPage.closeReservationTab(driver);
		testSteps.add("Closed reservation tab");
		app_logs.info("Closed reservation tab");
		
		statusCode.add(3, "1");


	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
*/

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("requireCardForGuaratedRes", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
