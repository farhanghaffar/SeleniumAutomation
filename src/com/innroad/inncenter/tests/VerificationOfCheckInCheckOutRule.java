package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
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

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerificationOfCheckInCheckOutRule extends TestCore {

	// Automation-1709
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();

	public static String test_description = "";
	public static String test_catagory = "";

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

	@Test(dataProvider = "getData", groups = "Rules")
	public void verificationOfCheckInCheckOutRule(String rateName, String baseAmount, String addtionalAdult,
			String additionalChild, String displayName, String ratePolicy, String rateDescription,
			String roomClassAbbreviation, String roomClassName, String bedsCount, String maxAdults, String maxPersons,
			String roomQuantity, String ratePlan, String rateType, String rateAttributes, String source, String adults,
			String child, String seasonName, String daysOfWeekCheckIn, String daysOfWeekCheckOut)
			throws InterruptedException, IOException, ParseException {

		String testName = "VerificationOfCheckInCheckOutRule";
		test_description = "Verification of CheckIn CheckOut Rule<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682468' target='_blank'>"
				+ "Click here to open TestRail: C682468</a>";
		test_catagory = "CheckInCheckOutRule";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Rate rate = new Rate();
		Navigation navigation = new Navigation();
		CPReservationPage reservation = new CPReservationPage();
		RoomClass roomClass = new RoomClass();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		Login login = new Login();
		Rules rules = new Rules();

		String randomNumber =  Utility.GenerateRandomNumber();
		roomClassName = roomClassName + randomNumber;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		rateName = rateName + randomNumber;
		displayName = rateName;

		String seasonStartDate = "";
		String seasonEndDate = "";
		String dateFormat = "MM/dd/yyyy";
		boolean applyCheckInRule = false;
		boolean applyCheckOutRule = false;

		 String checkInDate = Utility.getCurrentDate(dateFormat);
		String checkOutDate = Utility.GetNextDate(1, dateFormat);

		HashMap<String, String> checkInRules = new HashMap<String, String>();
		HashMap<String, String> checkoutRules = new HashMap<String, String>();
		ArrayList<Boolean> daysOfWeekCheckInList = new ArrayList<Boolean>();
		ArrayList<Boolean> daysOfWeekCheckOutList = new ArrayList<Boolean>();

		for (int i = 0; i < daysOfWeekCheckIn.split("\\|").length; i++) {

			if (Boolean.parseBoolean((daysOfWeekCheckIn.split("\\|")[i])))
				applyCheckInRule = true;

			if (Boolean.parseBoolean((daysOfWeekCheckOut.split("\\|")[i])))
				applyCheckOutRule = true;

			daysOfWeekCheckInList.add(Boolean.parseBoolean(daysOfWeekCheckIn.split("\\|")[i]));
			daysOfWeekCheckOutList.add(Boolean.parseBoolean(daysOfWeekCheckOut.split("\\|")[i]));

			System.out.println("Checkin RUle:" + applyCheckInRule);
			System.out.println("applyCheckOutRule:" + applyCheckOutRule);

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
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
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
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
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
			if (applyCheckInRule) {

				getTestSteps.clear();
				getTestSteps = rules.checkInRuleCheckBox(driver, applyCheckInRule);
				testSteps.addAll(getTestSteps);
				rules.checkInDaysOfWeek(driver, daysOfWeekCheckInList);
			}
			if (applyCheckOutRule) {

				getTestSteps.clear();
				getTestSteps = rules.checkOutRuleCheckBox(driver, applyCheckOutRule);
				testSteps.addAll(getTestSteps);
				rules.checkOutDaysOfWeek(driver, daysOfWeekCheckOutList);
			}

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
			if (datesCheckInRuleApplied.size() > 0) {
				applyCheckInRule = true;
				app_logs.info(" Checkin Rule Apply On Date:" + datesCheckInRuleApplied);
				testSteps.add(" Checkin Rule Apply On Date:" + datesCheckInRuleApplied);
			} else {
				app_logs.info(" Checkin Date don't fall under Rules");
				testSteps.add(" Checkin Date don't fall under Rules");
			}
			if (datesCheckOutRuleApplied.size() > 0) {
				applyCheckOutRule = true;
				app_logs.info(" Checkout Rule Apply On Date:" + datesCheckOutRuleApplied);
				testSteps.add(" Checkout Rule Apply On Date:" + datesCheckOutRuleApplied);
			} else {
				app_logs.info(" Checkout Date don't fall under Rules");
				testSteps.add(" Checkout Date don't fall under Rules");
			}

		} catch (

		Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate to Inventory->Rates Grid-> Availability and verify roomclass
		// availability

		try {
			driver.navigate().refresh();
			navigation.Reservation_Backward_4(driver);
			reservation.click_NewReservation(driver, testSteps);

			testSteps.add(
					"=================== CREATE Reservation & VERIFY CHECKIN/CHECKOUT RULES ======================");
			app_logs.info(
					"=================== CREATE Reservation & VERIFY CHECKIN/CHECKOUT RULES ======================");

			getTestSteps.clear();
			getTestSteps = reservation.checkInCheckOutRuleStatusVerfication(driver,
					ESTTimeZone.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					ESTTimeZone.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"), adults, child, rateName,
					roomClassName, datesCheckInRuleApplied, datesCheckOutRuleApplied);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
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
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();

			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyCCRule", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
//		driver.quit();
	}
}
