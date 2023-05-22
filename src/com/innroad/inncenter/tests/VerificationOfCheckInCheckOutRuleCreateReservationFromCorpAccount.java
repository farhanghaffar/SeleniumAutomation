package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerificationOfCheckInCheckOutRuleCreateReservationFromCorpAccount extends TestCore {
	private WebDriver driver = null;
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private ArrayList<String> getDataOfHash(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		ArrayList<String> values = new ArrayList<String>();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getValue().toString().equalsIgnoreCase("YES")) {
				testSteps.add("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
				app_logs.info("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
				values.add(mentry.getKey().toString());

			}
		}
		return values;
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verificationOfCheckInCheckOutRuleCreateReservationFromCorpAccount(String guestFirstName,
			String guestLastName, String phoneNumber, String alternativePhone, String email, String account,
			String accountType, String address1, String address2, String address3, String city, String country,
			String state, String postalCode, String isGuesProfile, String paymentType, String cardNumber,
			String nameOnCard, String cardExpDate, String isChangeInPayAmount, String changedAmountValue,
			String travelAgent, String marketSegment, String referral, String accountName, String accounttype,
			String rateName, String baseAmount, String addtionalAdult, String additionalChild, String displayName,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String ratePlan,
			String rateType, String rateAttributes, String source, String adults, String child, String seasonName,
			String daysOfWeekCheckIn, String daysOfWeekCheckOut) throws InterruptedException, IOException {

		String testName = "VerificationOfCheckInCheckOutRuleCreateReservationFromCorpAccount";
		testDescription = "Create_CorpAcc <br>";
		testCategory = "Create_CorpAcc";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RoomClass roomClass = new RoomClass();
		Account CreateTA = new Account();
		ReservationSearch reservationSearch = new ReservationSearch();
		Login login = new Login();

		Rules rules = new Rules();

		String calendarTodayDate = null;
		String randomNumber = Utility.GenerateRandomNumber();
		String reservationNumber = null;
		String status = null;
		guestFirstName = guestFirstName + randomNumber;
		guestLastName = guestLastName + randomNumber;
		String timeZone = "America/New_York";
		String seasonStartDate = "";
		String seasonEndDate = "";
		String dateFormat = "MM/dd/yyyy";
		String checkInDate = Utility.getCurrentDate(dateFormat);
		String checkOutDate = Utility.GetNextDate(3, dateFormat);

		roomClassName = roomClassName + randomNumber;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		rateName = rateName + randomNumber;
		displayName = rateName;

		HashMap<String, String> checkInRules = new HashMap<String, String>();
		HashMap<String, String> checkoutRules = new HashMap<String, String>();
		ArrayList<Boolean> daysOfWeekCheckInList = new ArrayList<Boolean>();
		ArrayList<Boolean> daysOfWeekCheckOutList = new ArrayList<Boolean>();

		for (int i = 0; i < daysOfWeekCheckIn.split("\\|").length; i++) {

			daysOfWeekCheckInList.add(Boolean.parseBoolean(daysOfWeekCheckIn.split("\\|")[i]));
			daysOfWeekCheckOutList.add(Boolean.parseBoolean(daysOfWeekCheckOut.split("\\|")[i]));

		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login.login(driver, "https://www.app.qainnroad.com", "autocp", "autouser", "Auto@123");
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========CREATE A NEW ROOM CLASS==========");
			testSteps.add("==========CREATE A NEW ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.RoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

			navigation.clickOnNewRoomClass(driver);
			getTestSteps.clear();
			getTestSteps = roomClass.createRoomClass(driver, roomClassName, roomClassAbbreviation, bedsCount, maxAdults,
					maxPersons, roomQuantity, test, getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Sccessfully Created New RoomClass " + roomClassName + " Abb : " + roomClassAbbreviation);
			app_logs.info("Sccessfully Created New RoomClass" + roomClassName + " Abb : " + roomClassAbbreviation);

			roomClass.closeRoomClassTab(driver);
			testSteps.add("Close created room class tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create New Rate and Attach RoomClass
		ArrayList<String> datesCheckInRuleApplied = new ArrayList<String>();
		ArrayList<String> datesCheckOutRuleApplied = new ArrayList<String>();

		try {
			testSteps.add("==========CREATE A NEW RATE==========");
			app_logs.info("==========CREATE A NEW RATE==========");
			navigation.cpReservationBackward(driver);
			navigation.Inventory_Backward(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");

			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlan);

			nightlyRate.enterRatePlanName(driver, rateName, getTestSteps);
			nightlyRate.enterRateFolioDisplayName(driver, displayName, getTestSteps);
			nightlyRate.enterRatePlanDescription(driver, rateDescription, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.selectChannels(driver, source, true, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.selectRoomClasses(driver, roomClassName, true, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickCreateSeason(driver, getTestSteps);
			seasonStartDate = Utility.getDateFromCurrentDate(0);
			seasonEndDate = Utility.getDateFromCurrentDate(20);
			nightlyRate.selectSeasonDates(driver, getTestSteps, seasonStartDate, seasonEndDate);
			nightlyRate.enterSeasonName(driver, getTestSteps, seasonName);
			nightlyRate.clickCreateSeason(driver, getTestSteps);
			nightlyRate.selectSeasonColor(driver, getTestSteps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, getTestSteps, "No");
			nightlyRate.enterRate(driver, getTestSteps, baseAmount, addtionalAdult, maxAdults, maxPersons,
					addtionalAdult, additionalChild);

			nightlyRate.clickRulesRestrictionOnSeason(driver, getTestSteps);

			getTestSteps.clear();
			getTestSteps = rules.checkInRuleCheckBox(driver, true);
			testSteps.addAll(getTestSteps);
			rules.checkInDaysOfWeek(driver, daysOfWeekCheckInList);

			getTestSteps.clear();
			getTestSteps = rules.checkOutRuleCheckBox(driver, true);
			testSteps.addAll(getTestSteps);
			rules.checkOutDaysOfWeek(driver, daysOfWeekCheckOutList);

			nightlyRate.clickSaveSason(driver, getTestSteps);
			nightlyRate.clickCompleteChanges(driver, getTestSteps);
			nightlyRate.clickSaveAsActive(driver, getTestSteps);

			testSteps.add("New Rate '" + rateName + "' Created & Verified ");
			app_logs.info("New Rate '" + rateName + "' Created & Verified");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, getTestSteps);
			ratesGrid.expandRoomClassWithoutMinus(driver, getTestSteps, roomClassName);
			ratesGrid.expandChannelWithoutMinus(driver, getTestSteps, roomClassName, source);
			checkInRules = ratesGrid.getCheckInCheckOutRulesOfChannel(driver,
					ESTTimeZone.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					ESTTimeZone.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"), roomClassName, source,
					ratesConfig.getProperty("checkinRule"));
			checkoutRules = ratesGrid.getCheckInCheckOutRulesOfChannel(driver,
					ESTTimeZone.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					ESTTimeZone.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"), roomClassName, source,
					ratesConfig.getProperty("checkoutRule"));
			datesCheckInRuleApplied = getDataOfHash(checkInRules);
			datesCheckOutRuleApplied = getDataOfHash(checkoutRules);

			System.out.println("CheckInRule:" + checkInRules);
			System.out.println("checkoutRules:" + checkoutRules);

			System.out.println("Hash1:" + datesCheckInRuleApplied);
			System.out.println("Has2:" + datesCheckOutRuleApplied);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Reservation
		try {
			driver.navigate().refresh();
			navigation.Reservation_Backward_4(driver);
			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", testName, "NavigateAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", testName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		String accountNumber = null;
		// New account
		try {
			testSteps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");
			accountName = accountName + Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, accounttype);
			CreateTA.AccountDetails(driver, accounttype, accountName);
			accountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, accountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber,
					alternativePhone, address1, address2, address3, email, city, state, postalCode, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
			testSteps.addAll(getTestSteps);
			CreateTA.accountSave(driver);
			testSteps.add("Save Account");
			app_logs.info("Save Account");

		} catch (Exception e) {

			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Clicking on New Reservation
		try {
			CreateTA.NewReservationButton(driver, test);
			testSteps.add("New Reservation page has been opened successfully.");
			app_logs.info("New Reservation page has been opened successfully.");

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", testName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", testName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate to Inventory->Rates Grid-> Availability and verify roomclass
		// availability

		try {

			testSteps.add(
					"=================== CREATE Reservation & VERIFY CHECKIN/CHECKOUT RULES ======================");
			app_logs.info(
					"=================== CREATE Reservation & VERIFY CHECKIN/CHECKOUT RULES ======================");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInCheckOutRuleStatusVerfication(driver,
					ESTTimeZone.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					ESTTimeZone.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"), adults, child, rateName,
					roomClassName, datesCheckInRuleApplied, datesCheckOutRuleApplied);
			testSteps.addAll(getTestSteps);

			testSteps.add("CheckIn CheckOut Rule Verified ");
			app_logs.info("CheckIn CheckOut Rule Verified");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try

		{
			driver.navigate().refresh();

			testSteps.add("=================== DELETE RATEPLAN ======================");
			app_logs.info("===================  DELETE RATEPLAN  ======================");

			navigation.Inventory_Backward(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");

			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			ratesGrid.searchRatePlan(driver, rateName);
			nightlyRate.deleteNightlyRatePlan(driver, rateName, "Delete", getTestSteps);
			testSteps.add("=================== DELETE ROOMCLASS ======================");
			app_logs.info("===================  DELETE ROOMCLASS  ======================");
			driver.navigate().refresh();
			navigation.Reservation_Backward_4(driver);
			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.RoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");
			roomClass.searchButtonClick(driver);
			boolean isRoomClassShowing = roomClass.searchClass(driver, roomClassName);
			app_logs.info("Search");
			if (isRoomClassShowing) {

				roomClass.deleteRoomClass(driver, roomClassName);
				testSteps.add("Delete room class successfully");
				app_logs.info("Delete room class successfully");
			} else {
				testSteps.add("No room class exist");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CCRuleVerifyCreateResCorpAcc", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
