package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreateIntervalRatePlanV2 extends TestCore {

	// Automation-1738
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void createIntervalRatePlanV2(String delim, String RatePlanName, String FolioDisplayName, String Description,
			String Channels, String RoomClasses, String isRatePlanRistrictionReq, String RistrictionType,
			String isMinStay, String MinNights, String isMaxStay, String MaxNights, String isMoreThanDaysReq,
			String MoreThanDaysCount, String isWithInDaysReq, String WithInDaysCount, String PromoCode,
			String isPolicesReq, String PoliciesType, String PoliciesName, String SeasonName, String SeasonStartDate,
			String SeasonEndDate, String isMonDay, String isTueDay, String isWednesDay, String isThursDay,
			String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults,
			String RatePerNight, String MaxAdults, String MaxPersons, String AdditionalAdultsPerNight,
			String AdditionalChildPerNight, String isAddRoomClassInSeason, String ExtraRoomClassesInSeason,
			String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults, String ExtraRoomClassMaxPersons,
			String ExtraRoomClassAdditionalAdultsPerNight, String ExtraRoomClassAdditionalChildPerNight,
			String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClasses, String SeasonRuleType,
			String SeasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday,
			String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday,
			String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday, String isSeasonPolicies,
			String SeasonPolicyType, String SeasonPolicyValues, String rateType, String interval,
			String isDefaultProrateChecked, String isProrateCheckboxCheccked, String isAdditionCharge, String adultRate,
			String childRate) throws InterruptedException, IOException, ParseException {

		Utility.DELIM = delim;

		test_name = "CreateIntervalRatePlanV2";
		test_description = "Create interval rate plan <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "RatePlan";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();

		
		String timeZone = "America/New_York";
		String getCurrentDate = Utility.getNextDate(0, "dd/M/yyyy", timeZone);
		if (ESTTimeZone.CompareDates(SeasonStartDate, "dd/M/yyyy", timeZone)) {
			SeasonStartDate = getCurrentDate;
		}
		if (ESTTimeZone.CompareDates(SeasonEndDate, "dd/M/yyyy", timeZone)) {
			getCurrentDate = Utility.getNextDate(10, "dd/M/yyyy", timeZone);
			SeasonEndDate = getCurrentDate;
		}
		app_logs.info("startDate: " + SeasonStartDate);
		app_logs.info("endDate: " + SeasonEndDate);

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();

			try {
				loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				loginWPI(driver);
			}
			testSteps.add("Logged into the application");
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to naivigate to rate grid", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to naivigate to rate grid", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SELECT INTERVAL RATE PLAN ======================");
			app_logs.info("=================== SELECT INTERVAL RATE PLAN ======================");

			ratesGrid.clickRateGridAddRatePlan(driver);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, rateType);
			testSteps.addAll(getTestSteps);

			 nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type",
			 "Interval rate plan", testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select rate plan type as " + rateType, testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select rate plan type as " + rateType, testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			RatePlanName = RatePlanName + Utility.GenerateRandomNumber();
			FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, RatePlanName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, Description, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name, folio and description", testName, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name, folio and description", testName, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== ENTER INTERVAL RATE PLAN LENGHT ======================");
			app_logs.info("=================== ENTER INTERVAL RATE PLAN LENGHT ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, interval);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, Boolean.parseBoolean(isDefaultProrateChecked));
			testSteps.addAll(getTestSteps);

			nightlyRate.clickNextButton(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e,
						"Failed to enter rate interval rate plan value and verify pro rate season checkk box for each night",
						testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e,
						"Failed to enter rate interval rate plan value and verify pro rate season checkk box for each night",
						testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);

			nightlyRate.selectChannels(driver, Channels, true, testSteps);
			// String summaryChannels =
			// nightlyRate.generateTitleSummaryValueForChannels(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			// nightlyRate.verifyTitleSummaryValue(driver, "Channel",
			// summaryChannels, testSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);
			String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			// nightlyRate.verifyTitleSummaryValue(driver, "Room class",
			// summaryRoomClasses, testSteps);

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
					Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					WithInDaysCount, PromoCode, testSteps);

			String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);

			// nightlyRate.verifyTitleSummaryValue(driver, "Restrictions",
			// restrictionsSummary, testSteps);

		//	nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq), testSteps);

			//HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
				//	Boolean.parseBoolean(isPolicesReq), testSteps);

			nightlyRate.clickNextButton(driver, testSteps);

			// nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName,
			// allPolicyDesc,
			// Boolean.parseBoolean(isPolicesReq), testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select channel", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select channel", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, SeasonStartDate, SeasonEndDate);
			nightlyRate.enterSeasonName(driver, testSteps, SeasonName);
			nightlyRate.selectSeasonDays(driver, testSteps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
					isSaturDay, isSunDay);
			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonColor(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// here to enter rate for each room class
		try {

			testSteps.add("=================== ENTER RATE VALUE FOR EACH ROOM CLASS ======================");
			app_logs.info("=================== ENTER RATE VALUE FOR EACH ROOM CLASS ======================");

			ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
					Boolean.parseBoolean(isAdditionCharge));
			ratesGrid.verifyProrateCheckbox(driver, Boolean.parseBoolean(isProrateCheckboxCheccked));


			String[] splitRoomClassName = RoomClasses.split(",");
			String[] splitRatePerNight = RatePerNight.split(",");
			String[] splitadultRate = adultRate.split(",");
			String[] splitchildRate = childRate.split(",");

			HashMap<String, String> mapRoomClassWithRate = new HashMap<>();
			HashMap<String, String> mapRoomClassWithadultRate = new HashMap<>();
			HashMap<String, String> mapRoomClassWithChildRate = new HashMap<>();
			boolean isAdditionCharges = Boolean.parseBoolean(isAdditionCharge);

			for (int i = 0; i < splitRoomClassName.length; i++) {
				String getRoomClass = splitRoomClassName[i].trim();
				String getRate = splitRatePerNight[i].trim();

				mapRoomClassWithRate.put(getRoomClass, getRate);

				app_logs.info("RoomClass: " + getRoomClass);
				app_logs.info("Pro rate: " + getRate);
				getTestSteps.clear();

				if (isAdditionCharges) {
					String getadultRate = splitadultRate[i].trim();
					String getchildRate = splitchildRate[i].trim();
					mapRoomClassWithadultRate.put(getRoomClass, getadultRate);
					mapRoomClassWithChildRate.put(getRoomClass, getchildRate);

					getTestSteps = ratesGrid.enterRoomClassRateWithAdditionalAdultsAndChild(driver, getRoomClass,
							getRate, getadultRate, getchildRate);
					testSteps.addAll(getTestSteps);
				} else {
					ratesGrid.enterRoomClassRate(driver, getRoomClass, getRate);
				}

				getTestSteps.clear();
				getTestSteps = ratesGrid.getPerNightProrate(driver, getRoomClass, isAdditionCharges);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to enter interval rate value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to enter interval rate value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickCompleteChanges(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			
			testSteps.add("=================== RATE PLAN CREATED ======================");
			app_logs.info("=================== RATE PLAN CREATED ======================");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to save rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to save rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateIntervalRatePlanV2", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
