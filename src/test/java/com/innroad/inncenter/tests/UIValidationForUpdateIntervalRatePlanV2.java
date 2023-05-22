package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

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

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class UIValidationForUpdateIntervalRatePlanV2 extends TestCore {

	// Automation-1791 B part verify UI validation
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
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	//Test interval rate plan description
	
	@Test(dataProvider = "getData")
	public void uIValidationForUpdateIntervalRatePlanV2(String delim, String RatePlanName, String FolioDisplayName,
			String Description, String Channels, String RoomClasses, String isRatePlanRistrictionReq,
			String RistrictionType, String isMinStay, String MinNights, String isMaxStay, String MaxNights,
			String isMoreThanDaysReq, String MoreThanDaysCount, String isWithInDaysReq, String WithInDaysCount,
			String PromoCode, String isPolicesReq, String PoliciesType, String PoliciesName, String SeasonName,
			String SeasonStartDate, String SeasonEndDate, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String isAssignRulesByRoomClass,
			String SeasonRuleSpecificRoomClasses, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String isSeasonPolicies, String SeasonPolicyType, String SeasonPolicyValues,
			String rateType, String interval, String isDefaultProrateChecked, String isProrateCheckboxCheccked,
			String isAdditionCharge, String adultRate, String childRate, String isUpdateDefaultProrateChecked,
			String updatedChannels, String updatedRoomClasses, String updatedInterval, String updatedSeasonName,
			String updatedRatePaln, String clientName,String UpdatedInterval,String maximumRate,String expectedMaximumRatePerInterval,
			String additionalRoomClass,String updateRateValue,String expectedTextAfterCheckedProRateCheckbox ,String MoreThen255Character)
			throws InterruptedException, IOException, ParseException {

		Utility.DELIM = delim;

		test_name = "UIValidationForUpdateIntervalRatePlanV2";
		test_description = "Update Created interval rate plan and perform UI validation<br>"
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
		String currency = "";

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

			testSteps.add(
					"=================== NAVIGATE TO CLIENT INFO FOR CURRENCY VERIFICATION ======================");
			app_logs.info(
					"=================== NAVIGATE TO CLIENT INFO FOR CURRENCY VERIFICATION ======================");
			navigation.Admin(driver);
			testSteps.add("Click on admin");
			app_logs.info("Click on admin");

			navigation.clickonClientinfo(driver);
			testSteps.add("Click on client info");
			app_logs.info("Click on client info");


			Admin admin = new Admin();
			getTestSteps.clear();
			getTestSteps = admin.clickOnClient(driver, clientName);
			testSteps.addAll(getTestSteps);

			admin.clickOnClientOptions(driver);
			testSteps.add("Click on options");

			currency = admin.getSelectedCurrency(driver);
			testSteps.add("Selecetd currency: "+currency);
			app_logs.info("Currency: " + currency);
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
				Utility.updateReport(e, "Failed to navigate to rate grid", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rate grid", testName, "RatesV2", driver);
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
		
		//search created season
		try {

			testSteps.add("=================== SEARCH CREATED SEASON ======================");
			app_logs.info("=================== SEARCH CREATED SEASON ======================");

			//RatePlanName = "TestInterval1774";
			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			ratesGrid.searchRatePlan(driver, RatePlanName);
			String getRatPlanName = ratesGrid.selectedRatePlan(driver);
			app_logs.info("getRatPlanName: "+getRatPlanName);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to search created season", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to search created season", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			testSteps.add("=================== UPDATING INTERVAL RATE PLAN ======================");
			app_logs.info("=================== UPDATING INTERVAL RATE PLAN ======================");

			ratesGrid.clickOnEditRatePlan(driver);
			testSteps.add("Click on edit rate plan");
			app_logs.info("Click on edit rate plan");
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.ratePlanOverView(driver);
			testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click on edit rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click on edit rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== UPDATE INTERVAL RATE PLAN NAME, FOLIO NAME AND DESCRIPTION ======================");
			app_logs.info("=================== UPDATE INTERVAL RATE PLAN NAME, FOLIO NAME AND DESCRIPTION ======================");

			updatedRatePaln = updatedRatePaln + Utility.GenerateRandomNumber();
			FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

			nightlyRate.enterRatePlanName(driver, updatedRatePaln, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, updatedRatePaln, testSteps);
			nightlyRate.enterRatePlanDescription(driver, Description, testSteps);

			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to update rate plan name, folio name and description", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to update rate plan name, folio name and description", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("=================== UPDATING INTERVAL RATE PLAN LENGHT ======================");
			app_logs.info("=================== UPDATING INTERVAL RATE PLAN LENGHT ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, updatedInterval);
			testSteps.addAll(getTestSteps);

			app_logs.info("isUpdateDefaultProrateChecked: "+isUpdateDefaultProrateChecked);
			getTestSteps.clear();
			getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, Boolean.parseBoolean(isUpdateDefaultProrateChecked));
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e,
						"Failed to update rate interval rate plan value and verify pro rate season checkk box for each night",
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
						"Failed to update rate interval rate plan value and verify pro rate season checkk box for each night",
						testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== UPDATE DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== UPDATE DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, updatedChannels, true, testSteps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to upadet channel", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to upadet channel", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

						
			testSteps.add("=================== UPDATING ROOM CLASSES ======================");
			app_logs.info("=================== UPDATING ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, updatedRoomClasses, true, testSteps);
			
			} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating room classes", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating room classes", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			
			testSteps.add("=================== UPDATING RESTRICATIONS  ======================");
			app_logs.info("=================== UPDATING RESTRICATIONS  ======================");
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
			testSteps.addAll(getTestSteps);
			
			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
					Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					WithInDaysCount, PromoCode, testSteps);

			} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating restrications", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating restrications", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			updatedSeasonName = updatedSeasonName+Utility.GenerateRandomNumber();
			ratesGrid.clickOnSeasonTab(driver);
			
			SeasonStartDate = Utility.getNextDate(11, "dd/M/yyyy", timeZone);
			SeasonEndDate = Utility.getNextDate(20, "dd/M/yyyy", timeZone);

			nightlyRate.selectSeasonDates(driver, testSteps, SeasonStartDate, SeasonEndDate);
			nightlyRate.enterSeasonName(driver, testSteps, updatedSeasonName);
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
					getTestSteps.clear();
					getTestSteps = ratesGrid.enterRoomClassRate(driver, getRoomClass, getRate);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = ratesGrid.getPerNightProrate(driver, getRoomClass, isAdditionCharges);
				testSteps.addAll(getTestSteps);
			}
			
			// provide info for additional room  class
			if (isAdditionCharges) {
				mapRoomClassWithadultRate.put(updatedRoomClasses, "1");
				mapRoomClassWithChildRate.put(updatedRoomClasses, "1");

				getTestSteps = ratesGrid.enterRoomClassRateWithAdditionalAdultsAndChild(driver, updatedRoomClasses,
						"100", "1", "1");
				testSteps.addAll(getTestSteps);
			} else {
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterRoomClassRate(driver, updatedRoomClasses, "100");
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			getTestSteps = ratesGrid.getPerNightProrate(driver, updatedRoomClasses, isAdditionCharges);
			testSteps.addAll(getTestSteps);


		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to update  retes for each room class", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to update  retes for each room class", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			testSteps.add("=================== SAVE UPDATED INTERVAL RATE PLAN SEASON ======================");
			app_logs.info("=================== SAVE UPDATED INTERVAL RATE PLAN SEASON ======================");

			nightlyRate.clickSaveSason(driver, testSteps);
			
			getTestSteps.clear();
			getTestSteps =  ratesGrid.clickOnSaveratePlan(driver);
			testSteps.addAll(getTestSteps);
			
			
			
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


		

		// search created rate plan
		try {

			testSteps.add("=================== SEARCH CREATED INTERVAL RATE PLAN ======================");
			app_logs.info("=================== SEARCH CREATED INTERVAL RATE PLAN ======================");

			//RatePlanName = "TestInterval1774";
			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			ratesGrid.searchRatePlan(driver, RatePlanName);
			String getRatPlanName = ratesGrid.selectedRatePlan(driver);
			app_logs.info("getRatPlanName: " + getRatPlanName);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to search created rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to search created rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CLICK ON EDIT INTERVAL RATE PLAN ======================");
			app_logs.info("=================== CLICK ON EDIT INTERVAL RATE PLAN ======================");

			ratesGrid.clickOnEditRatePlan(driver);
			testSteps.add("Click on edit rate plan");
			app_logs.info("Click on edit rate plan");

			getTestSteps.clear();
			getTestSteps = ratesGrid.ratePlanOverView(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click on edit rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click on edit rate plan", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"=================== VERIFY RATE PLAN NAME, FOLIO NAME AND DESCRIPTIONS ======================");
			app_logs.info(
					"=================== VERIFY RATE PLAN NAME, FOLIO NAME AND DESCRIPTIONS ======================");

			String getRatePlanName = nightlyRate.getRatePlanName(driver);

			testSteps.add("Expected rate plan name: " + RatePlanName);
			testSteps.add("Found: " + getRatePlanName);
			assertEquals(getRatePlanName, RatePlanName, "Failed: rate plan name is mismatching!");
			testSteps.add("Verified rate plan name");

			String getFolioName = nightlyRate.getRateFolioDisplayName(driver);
			testSteps.add("Expected folio name: " + RatePlanName);
			testSteps.add("Found: " + getFolioName);
			assertEquals(getFolioName, RatePlanName, "Failed: folio name name is mismatching!");
			testSteps.add("Verified folio name");

			String getRatePlanDescription = nightlyRate.getRatePlanDescription(driver);
			testSteps.add("Expected rate plan description: " + Description);
			testSteps.add("Found: " + getRatePlanDescription);
			assertEquals(getRatePlanDescription, Description, "Failed: description is mismatching!");
			testSteps.add("Verified rate plan description");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan name, folio name and description", testName,
						"RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan name, folio name and description", testName,
						"RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// here to perform UI validation

		try {

			testSteps.add(
					"=================== ERROR VALIDATION FOR RATE PLAN NAME, FOLIO NAME AND DESCRIPTTION INPUT FIELD ======================");
			app_logs.info(
					"=================== ERROR VALIDATION FOR RATE PLAN NAME, FOLIO NAME AND DESCRIPTTION INPUT FIELD ======================");

			RatePlanName = updatedRatePaln + Utility.GenerateRandomNumber();
			FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clearRatePlanName(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "Rate Plan Name cannot be empty", true, testSteps);

			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			//nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, RatePlanName, true, getTestSteps);
			//testSteps.add(
				//	"Verified folio name has been updated as rate plan name after entered rate plan name in input field");

			nightlyRate.enterRateFolioDisplayName(driver, RatePlanName, testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clearRateFolioDisplayName(driver);
			testSteps.addAll(getTestSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, true, getTestSteps);

			nightlyRate.enterRatePlanDescription(driver, MoreThen255Character, testSteps);
			testSteps.add("Enter more then 255 character in description");
			int lenthg = nightlyRate.getRatePlanDescriptionInputFeiledLength(driver, testSteps);
			testSteps.add("Expecetd description field character length: 255");
			testSteps.add("Found: " + lenthg);
			assertEquals(lenthg, 255, "Failed: Descritption field in showing more then 255 character");
			testSteps.add("Verified character lenght in description input field");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify enter rate plan , folio and description fields verification",
						testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify enter rate plan , folio and description fields verification",
						testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"=================== UPDATING RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== UPDATING RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			RatePlanName = RatePlanName + Utility.GenerateRandomNumber();
			FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, RatePlanName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, Description, testSteps);
			// nightlyRate.clickNextButton(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to update rate plan name, folio and description", testName, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to update rate plan name, folio and description", testName, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		boolean isDefaultProrateCheckbox = false;
		String getDefultValue = "";
		String DefaultIntervalLength = "4";
		try {

			testSteps.add("=================== VERIFY INTERVAL RATE PLAN LENGHT ======================");
			app_logs.info("=================== VERIFY INTERVAL RATE PLAN LENGHT ======================");

			getDefultValue = ratesGrid.getDefultValueOfInterval(driver);
			testSteps.add("Expected defult interval lenght: " + DefaultIntervalLength);
			testSteps.add("Found: " + getDefultValue);
			assertEquals(getDefultValue, DefaultIntervalLength, "Failed: Default interval lenght is mismatching");
			testSteps.add("Verified default interval lenght");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify default interval length", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "updateReport", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		boolean expecetdProrateEachSeasonCheckbox = Boolean.parseBoolean(isDefaultProrateChecked);
		try {

			testSteps.add("=================== VERIFY PRO-RATE EACH SEASON CHECKED/UNCHECKED  ======================");
			app_logs.info("=================== VERIFY PRO-RATE EACH SEASON CHECKED/UNCHECKED  ======================");

			isDefaultProrateCheckbox = ratesGrid.verifyDefaultProrateCheckbox(driver);
			testSteps.add("Expecetd pro-rate each season checkbox should be checked? " + expecetdProrateEachSeasonCheckbox);
			assertEquals(isDefaultProrateCheckbox, expecetdProrateEachSeasonCheckbox);
			testSteps.add("Verified pro-rate each season checkbox");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify pro rate each season checkbox", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify pro rate each season checkbox", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("=================== ENTER DIFFERENT INTERVAL RATE PLAN LENGHT ======================");
			app_logs.info("=================== ENTER INTERVAL RATE PLAN LENGHT ======================");

			String[] getInvervalLength = UpdatedInterval.split(",");
			for (int i = 0; i < getInvervalLength.length; i++) {
				String getInterval = getInvervalLength[i].trim();
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterInterval(driver, getInterval);
				testSteps.addAll(getTestSteps);
				ratesGrid.clickonbyDefaultProrateCheckbox(driver);

				if (getInterval.equals("1")) {
					testSteps.add("Expected interval rate: " + DefaultIntervalLength);
					testSteps.add("Found: " + getDefultValue);
					assertEquals(getDefultValue, DefaultIntervalLength,
							"Failed interval length is mismatching after enter " + getInterval);

				} else {
					getDefultValue = ratesGrid.getDefultValueOfInterval(driver);
					double expectedValue = Double.parseDouble(getInterval.split("\\.")[1]);
					app_logs.info("expectedValue: " + expectedValue);
					String expectedInterval = "";
					if (expectedValue >= 5) {
						int convertIntoInt = Integer.parseInt(getInterval.split("\\.")[0]);
						convertIntoInt = convertIntoInt + 1;
						expectedInterval = String.valueOf(convertIntoInt);
					} else {
						int convertIntoInt = Integer.parseInt(getInterval.split("\\.")[0]);
						expectedInterval = String.valueOf(convertIntoInt);
					}

					testSteps.add("Expected interval rate: " + expectedInterval);
					testSteps.add("Found: " + getDefultValue);
					assertEquals(getDefultValue, expectedInterval,
							"Failed interval length is mismatching after enter " + getInterval);

				}
			}

			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, updatedInterval);
			testSteps.addAll(getTestSteps);
			
			getDefultValue= updatedInterval;
			
			isDefaultProrateCheckbox = ratesGrid.verifyDefaultProrateCheckbox(driver);
			if (expecetdProrateEachSeasonCheckbox) {
				if (!isDefaultProrateCheckbox) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver,
							Boolean.parseBoolean(isDefaultProrateChecked));
					testSteps.addAll(getTestSteps);
					String getTextAfterCheckedProrate = ratesGrid.getTextAfterCheckedProRateCheckbox(driver);
					assertEquals(getTextAfterCheckedProrate, expectedTextAfterCheckedProRateCheckbox,
							"Failed: pro rate each season message is not displaying after checked checkbox ");
				}
			} else {
				if (!isDefaultProrateCheckbox) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, true);
					testSteps.addAll(getTestSteps);
					
					String getTextAfterCheckedProrate = ratesGrid.getTextAfterCheckedProRateCheckbox(driver);
					assertEquals(getTextAfterCheckedProrate, expectedTextAfterCheckedProRateCheckbox,
							"Failed: pro rate each season message is not displaying after checked checkbox ");

				}
				testSteps.add("Pro-rate each season by default checkbox unchecked");
			}
			app_logs.info("after click default checbox");


		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e,
						"Failed to verify rate interval rate plan values and verify pro rate season checkk box for each night",
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

			testSteps.add("=================== UPDATE DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== UPDATE DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, updatedChannels, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to upadet channel", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to upadet channel", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== UPDATING ROOM CLASSES ======================");
			app_logs.info("=================== UPDATING ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, updatedRoomClasses, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating room classes", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating room classes", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== UPDATING RESTRICATIONS  ======================");
			app_logs.info("=================== UPDATING RESTRICATIONS  ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
			testSteps.addAll(getTestSteps);

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
					Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					WithInDaysCount, PromoCode, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating restrications", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to updating restrications", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("=================== UPDATE SEASON ======================");
			app_logs.info("=================== UPDATE SEASON ======================");

			updatedSeasonName = updatedSeasonName + Utility.GenerateRandomNumber();
			ratesGrid.clickOnSeasonTab(driver);
			nightlyRate.selectSeasonDates(driver, testSteps, "28/8/2020", "30/8/2020");
			nightlyRate.enterSeasonName(driver, testSteps, updatedSeasonName);
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
		
		// here start pro rate
		ArrayList<String> listOfRoomClasses = new ArrayList<>();
		try {

			testSteps.add(
					"=================== VERIFICATION OF CHARGES FOR ADDITIONAL ADULT/CHILD ======================");
			app_logs.info(
					"=================== VERIFICATION OF CHARGES FOR ADDITIONAL ADULT/CHILD ======================");

			boolean isChargesbuttonOn = ratesGrid.verifyToggleBtnAdditionalChargForAdultsAndChildernisOn(driver);

			assertEquals(isChargesbuttonOn, false, "Failed: Charge for additional adult/child is on");
			testSteps.add("Verified charge for additional adult/child button is off");

			testSteps.add(
					"=================== CHANGE STATE OF CHARGES FOR ADDITIONAL ADULT/CHILD ======================");
			app_logs.info(
					"=================== CHANGE STATE OF CHARGES FOR ADDITIONAL ADULT/CHILD ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver, true);
			testSteps.addAll(getTestSteps);
			
			RoomClasses = RoomClasses+","+updatedRoomClasses;
			String[] splitRoomClassName = RoomClasses.split(",");
			
			for (int i = 0; i < splitRoomClassName.length; i++) {
				String getRoomclass = splitRoomClassName[i].trim();
				listOfRoomClasses.add(getRoomclass);
				ratesGrid.VerifyAdditonalAdultandChildFiled(driver, getRoomclass, true);

			}

			testSteps.add(
					"Verified additional adult and child input fields are displaying after click on additional charge for adult/child");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified additional adult and child", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified additional adult and child", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// default pro rate check box checked or not
		try {

			testSteps.add(
					"=================== VERIFICATION OF PRO RATE STAY CHECKBOX CHECKED/UNCHECKED BYDEFAULT ======================");
			app_logs.info(
					"=================== VERIFICATION OF PRO RATE STAY CHECKBOX CHECKED/UNCHECKED BYDEFAULT ======================");

			for (int i = 0; i < listOfRoomClasses.size(); i++) {
				if (expecetdProrateEachSeasonCheckbox) {
					boolean getState = ratesGrid.proRateCheckboxInSeason(driver, listOfRoomClasses.get(i), false, true,
							false);
					assertEquals(getState, true,
							"Failed: pro rate checkbox bydefault checbox is unchecked for room class: "
									+ listOfRoomClasses.get(i));
					testSteps.add("Verified pro rate stay bydefault state is checked for room class: "
							+ listOfRoomClasses.get(i));

				} else {
					boolean getState = ratesGrid.proRateCheckboxInSeason(driver, listOfRoomClasses.get(i), false, true,
							false);
					assertEquals(getState, false,
							"Failed: pro rate checkbox bydefault checbox is unchecked for room class: "
									+ listOfRoomClasses.get(i));
					testSteps.add("Verified pro rate stay bydefault state is unchecked for room class: "
							+ listOfRoomClasses.get(i));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified pro rate stay checkbox is checked or unchecked", testName,
						"RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified pro rate stay checkbox is checked or unchecked", testName,
						"RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== VERIFICATION OF INTERVAL NIGHT ======================");
			app_logs.info("=================== VERIFICATION OF INTERVAL NIGHT ======================");

			boolean isAdditionCharges = Boolean.parseBoolean(isAdditionCharge);

			for (int i = 0; i < listOfRoomClasses.size(); i++) {
				String getRoomClass = listOfRoomClasses.get(i).trim();
				getTestSteps.clear();
				getTestSteps = ratesGrid.getIntervalNight(driver, getRoomClass, isAdditionCharges);
				for (int j = 0; j < getTestSteps.size(); j++) {
					app_logs.info("night: " + getTestSteps.get(j));
					String[] splitNightText = getTestSteps.get(j).split("\\(");
					String getIntervalPerNight = splitNightText[1];
					getIntervalPerNight = getIntervalPerNight.replace(")", "");
					app_logs.info("getIntervalPerNight: " + getIntervalPerNight);
					assertEquals(getIntervalPerNight, getDefultValue + "n*",
							"Failed: Interval per night is mismatching!");
				}

			}
			testSteps.add("Verified interval for each room class");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified interval rate night", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified interval rate night", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//
		try {

			testSteps.add("=================== VERIFICATION OF CURRENCY ======================");
			app_logs.info("=================== VERIFICATION OF CURRENCY ======================");

			boolean isAdditionCharges = Boolean.parseBoolean(isAdditionCharge);

			for (int i = 0; i < listOfRoomClasses.size(); i++) {
				String getRoomClass = listOfRoomClasses.get(i).trim();
				getTestSteps.clear();
				getTestSteps = ratesGrid.getCurrency(driver, getRoomClass, isAdditionCharges);
				for (int j = 0; j < getTestSteps.size(); j++) {
					app_logs.info("getIntervalPerNight: " + getTestSteps.get(j));
					assertEquals(getTestSteps.get(j), currency, "Failed: Currency is mismatching!");
				}

			}
			testSteps.add("Verified currency symbol for each room class");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified interval rate night", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verified interval rate night", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		HashMap<String, String> mapRoomClassWithRate = new HashMap<>();
		HashMap<String, String> mapRoomClassWithadultRate = new HashMap<>();
		HashMap<String, String> mapRoomClassWithChildRate = new HashMap<>();
		boolean isAdditionCharges = Boolean.parseBoolean(isAdditionCharge);

		try {

			testSteps.add(
					"=================== ENTER RATE PER NIGHT AND VERIFY PRO RATE STAY FOR EACH ROOM CLASS ======================");
			app_logs.info(
					"=================== ENTER RATE PER NIGHT AND VERIFY PRO RATE STAY FOR EACH ROOM CLASS ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
					Boolean.parseBoolean(isAdditionCharge));
			testSteps.addAll(getTestSteps);

			RatePerNight = RatePerNight+","+updateRateValue;
			String[] splitRatePerNight = RatePerNight.split(",");
			String[] splitadultRate = adultRate.split(",");
			String[] splitchildRate = childRate.split(",");

			for (int i = 0; i < listOfRoomClasses.size(); i++) {
				String getRoomClass = listOfRoomClasses.get(i);
				String getRate = splitRatePerNight[i].trim();
				app_logs.info("getRate: " + getRate);
				app_logs.info("DefaultIntervalLength: " + DefaultIntervalLength);
				
				DefaultIntervalLength = "2";
				
				String expectedPerNightRate = ratesGrid.calculateAmountPerNight(getDefultValue, getRate);
				app_logs.info("expectedPerNightRate: " + expectedPerNightRate);

				getTestSteps.clear();

				if (isAdditionCharges) {
					String getadultRate = splitadultRate[i].trim();
					String getchildRate = splitchildRate[i].trim();
					mapRoomClassWithadultRate.put(getRoomClass, getadultRate);
					mapRoomClassWithChildRate.put(getRoomClass, getchildRate);

					getTestSteps = ratesGrid.enterRoomClassRateWithAdditionalAdultsAndChild(driver, getRoomClass,
							getRate, getadultRate, getchildRate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.getRatePerNight(driver, getRoomClass, isAdditionCharges);
					testSteps.addAll(getTestSteps);

					// expected rate for per night
					testSteps.add(
							"Expected rate per night: " + expectedPerNightRate + " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(0));
					assertEquals(getTestSteps.get(0), expectedPerNightRate, "Failed: rate per night is mismatching");

					// expected adult per night
					String expectedPerNightRateForAdult = ratesGrid.calculateAmountPerNight(getDefultValue,
							getadultRate);

					app_logs.info("expectedPerNightRate: " + expectedPerNightRateForAdult);
					testSteps.add("Expected additional adults rate per night: " + expectedPerNightRateForAdult
							+ " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(1));
					assertEquals(getTestSteps.get(1), expectedPerNightRateForAdult,
							"Failed: rate per night is mismatchingfor additional adults");

					// expected child per night
					String expectedPerNightRateForChilds = ratesGrid.calculateAmountPerNight(getDefultValue,
							getchildRate);
					app_logs.info("expectedPerNightRateForAdultForChilds: " + expectedPerNightRateForChilds);
					testSteps.add("Expected additional child per night: " + expectedPerNightRateForChilds
							+ " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(2));
					assertEquals(getTestSteps.get(2), expectedPerNightRateForChilds,
							"Failed: rate per night is mismatchingfor additional child");

				} else {
					ratesGrid.enterRoomClassRate(driver, getRoomClass, getRate);
					testSteps.addAll(getTestSteps);

					getTestSteps = ratesGrid.getRatePerNight(driver, getRoomClass, isAdditionCharges);
					testSteps.add(
							"Expected rate per night: " + getTestSteps.get(0) + " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(0));
					assertEquals(getTestSteps.get(0), expectedPerNightRate, "Failed: rate per night is mismatching");

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify per night value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify per night value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== ENTER RATE VALUE " + maximumRate
					+ " RATE PER INTERVAL, ADULT AND CHILD INPUT FILED AND VERIFY RATE PER INTERVAL ======================");
			app_logs.info("=================== ENTER RATE VALUE " + maximumRate
					+ " RATE PER INTERVAL, ADULT AND CHILD INPUT FILED AND VERIFY RATE PER INTERVAL ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
					Boolean.parseBoolean(isAdditionCharge));
			testSteps.addAll(getTestSteps);

			for (int i = 0; i < 1; i++) {
				String getRoomClass = listOfRoomClasses.get(i);

				getTestSteps.clear();
				if (isAdditionCharges) {

					getTestSteps = ratesGrid.enterRoomClassRateWithAdditionalAdultsAndChild(driver, getRoomClass,
							maximumRate, maximumRate, maximumRate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.getRoomClassRateWithAdditionalAdultsAndChild(driver, getRoomClass,
							isAdditionCharges);
					testSteps.addAll(getTestSteps);
					testSteps.add("Expected maximum value in rate per interval: " + expectedMaximumRatePerInterval);
					testSteps.add("Found: " + getTestSteps.get(0));
					assertEquals(getTestSteps.get(0), expectedMaximumRatePerInterval,
							"Failed: maximum value is mismatching in rate per interval");
					testSteps.add("Verified maximum rate per interval");

					testSteps.add("Expected maximum value for adult: " + expectedMaximumRatePerInterval);
					testSteps.add("Found: " + getTestSteps.get(1));
					assertEquals(getTestSteps.get(1), expectedMaximumRatePerInterval,
							"Failed: maximum value is mismatching in adult");
					testSteps.add("Verified maximum rate in adult");

					testSteps.add("Expected maximum value for child: " + expectedMaximumRatePerInterval);
					testSteps.add("Found: " + getTestSteps.get(2));
					assertEquals(getTestSteps.get(2), expectedMaximumRatePerInterval,
							"Failed: maximum value is mismatching in child");
					testSteps.add("Verified maximum rate in child");

				} else {
					
					getTestSteps.clear();
					getTestSteps = ratesGrid.enterRoomClassRate(driver, getRoomClass, maximumRate);
					testSteps.addAll(getTestSteps);

					ratesGrid.clickOnCapcity(driver);
					
					getTestSteps.clear();
					String getPerNightValue = ratesGrid.getPerNightRate(driver, getRoomClass);
					
					testSteps.add("Expected maximum rate per night: " +expectedMaximumRatePerInterval + " for room class: "
							+ getRoomClass);
					testSteps.add("Found: " + getPerNightValue);
					assertEquals(getPerNightValue, expectedMaximumRatePerInterval,
							"Failed: maximum rate per night is mismatching");

				}

			}
			testSteps.add("Verified maximum rate value in interval per nigth, adult and child input fields");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify maximum erate per night value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify maximum rate per night value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// here add add one more room class
		try {

			testSteps.add("=================== ADD ONE MORE ROOM CLASS ======================");
			app_logs.info("=================== ADD ONE MORE ROOM CLASS ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickAddMoreRoomClass(driver);
			testSteps.addAll(getTestSteps);

			String[] spltAdditionalRC = additionalRoomClass.split(",");
			for (int i = 0; i < spltAdditionalRC.length; i++) {
				String getRoomClass = spltAdditionalRC[i].trim();
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectRoomClasses(driver, getRoomClass);
				testSteps.addAll(getTestSteps);

				// here verification of room class field
				if (isAdditionCharges) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.VerifyAdditonalAdultandChildFiled(driver, getRoomClass, true);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyRatePerInterval(driver, getRoomClass);
				testSteps.addAll(getTestSteps);

				boolean isChecked = ratesGrid.proRateCheckboxInSeason(driver, getRoomClass, false,
						Boolean.parseBoolean(isDefaultProrateChecked), false);
				assertEquals(isChecked, Boolean.parseBoolean(isDefaultProrateChecked),
						"Failed: default pro rate stay checkbox is mismatching expected " + isDefaultProrateChecked);
				testSteps.add("Is pro rate stay checkbox is showing? " + isChecked);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to add one more room class", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to add one more room class", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add(
					"=================== ENTER RATE VALUE FOR EACH ROOM CLASS AND VERIFY RATE PER INTERVAL ======================");
			app_logs.info(
					"=================== ENTER RATE VALUE FOR EACH ROOM CLASS AND VERIFY RATE PER INTERVAL ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
					Boolean.parseBoolean(isAdditionCharge));
			testSteps.addAll(getTestSteps);

			String[] splitRatePerNight = RatePerNight.split(",");
			String[] splitadultRate = adultRate.split(",");
			String[] splitchildRate = childRate.split(",");
			String[] spltAdditionalRC = additionalRoomClass.split(",");

			for (int i = 0; i < spltAdditionalRC.length; i++) {
				String getRoomClass = spltAdditionalRC[i].trim();
				app_logs.info("getRoomClass: " + getRoomClass);
				String getRate = splitRatePerNight[i].trim();
				app_logs.info("getRate: " + getRate);
				app_logs.info("DefaultIntervalLength: " + DefaultIntervalLength);
				DefaultIntervalLength = "2";
				String expectedPerNightRate = ratesGrid.calculateAmountPerNight(getDefultValue, getRate);
				app_logs.info("expectedPerNightRate: " + expectedPerNightRate);

				getTestSteps.clear();

				if (isAdditionCharges) {
					String getadultRate = splitadultRate[i].trim();
					String getchildRate = splitchildRate[i].trim();
					mapRoomClassWithadultRate.put(getRoomClass, getadultRate);
					mapRoomClassWithChildRate.put(getRoomClass, getchildRate);

					getTestSteps = ratesGrid.enterRoomClassRateWithAdditionalAdultsAndChild(driver, getRoomClass,
							getRate, getadultRate, getchildRate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.getRatePerNight(driver, getRoomClass, isAdditionCharges);
					testSteps.addAll(getTestSteps);

					// expected rate for per night
					testSteps.add(
							"Expected rate per night: " + expectedPerNightRate + " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(0));
					assertEquals(getTestSteps.get(0), expectedPerNightRate, "Failed: rate per night is mismatching");

					// expected adult per night
					String expectedPerNightRateForAdult = ratesGrid.calculateAmountPerNight(getDefultValue,
							getadultRate);

					app_logs.info("expectedPerNightRate: " + expectedPerNightRateForAdult);
					testSteps.add("Expected additional adults rate per night: " + expectedPerNightRateForAdult
							+ " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(1));
					assertEquals(getTestSteps.get(1), expectedPerNightRateForAdult,
							"Failed: rate per night is mismatchingfor additional adults");

					// expected child per night
					String expectedPerNightRateForChilds = ratesGrid.calculateAmountPerNight(getDefultValue,
							getchildRate);
					app_logs.info("expectedPerNightRateForAdultForChilds: " + expectedPerNightRateForChilds);
					testSteps.add("Expected additional child per night: " + expectedPerNightRateForChilds
							+ " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(2));
					assertEquals(getTestSteps.get(2), expectedPerNightRateForChilds,
							"Failed: rate per night is mismatchingfor additional child");

				} else {
					ratesGrid.enterRoomClassRate(driver, getRoomClass, getRate);
					testSteps.addAll(getTestSteps);

					getTestSteps = ratesGrid.getRatePerNight(driver, getRoomClass, isAdditionCharges);
					testSteps.add(
							"Expected rate per night: " + getTestSteps.get(0) + " for room class: " + getRoomClass);
					testSteps.add("Found: " + getTestSteps.get(0));
					assertEquals(getTestSteps.get(0), expectedPerNightRate, "Failed: rate per night is mismatching");

				}

			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify per night value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify per night value", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			ratesGrid.clickOnCLoseRoomClassesExpandList(driver);
			testSteps.add("Click on close room classes expand list");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click on close room classes expand list", testName, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click on close room classes expand list", testName, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("UIUpdateIntervalRatePlan", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
