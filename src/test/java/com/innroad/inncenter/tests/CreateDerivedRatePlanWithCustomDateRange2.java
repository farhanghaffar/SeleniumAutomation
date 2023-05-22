package com.innroad.inncenter.tests;

import java.io.IOException;
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
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CreateDerivedRatePlanWithCustomDateRange2 extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void createDerivedRatePlanWithCustomDateRange(String clientName, String delim,String partOftestName, String parentRatePlanType,
			String parentRatePlanName, String parentRateFolioName, String parentRateDescription, String parentRoomClass,
			String parentRoomClassSize, String parentBaseAmount, String addtionalAdult, String additionalChild,
			String intervalRatePlanIntervalValue, String isDefaultProrateChecked, String packageRatePlanRateType,
			String packageRatePlanProductName, String packageratePlanProductFirstCalculationMethod,
			String packageratePlanProductsecondCalculationMethod, String packageRatePlanProductAmount, String channels,
			String roomClassesName, String isRatePlanRistrictionReq, String ristrictionType, String isMinStay,
			String minNights, String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
			String isWithInDaysReq, String withInDaysCount, String promoCode, String isPolicesReq, String policiesType,
			String policiesName, String seasonName, String seasonStartDate, String seasonEndDate, String isMonDay,
			String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason,
			String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults,
			String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight,
			String extraRoomClassAdditionalChildPerNight, String isSeasonLevelRules, String isAssignRulesByRoomClass,
			String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String isSeasonPolicies, String seasonPolicyType, String seasonPolicyValues,
			String derivedRatePlanName, String derivedRatePlanFolioDisplayName, String derivedRatePlanDescription,
			String istakenRulesFromParentRateplan, String customStartDate, String customEndDate,
			String selectComparator, String derivedRateType, String derivedRateValue)
			throws InterruptedException, IOException {

		Utility.DELIM = "\\" + delim;

		test_name = "CreateDerivedRatePlanWithCustomDateRange";
		testDescription = "Rates V2 - Create Derived Rate Plan (with always available) and update<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1759' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1759</a>";
		testCategory = "RateV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		DerivedRate derivedRate = new DerivedRate();
		RatePackage ratePackage = new RatePackage();
		Policies policies = new Policies();
		String existingRateName = "";
		String timeZone = "America/New_York";
		ArrayList<String> ratePlanNames = new ArrayList<String>();
		String randomString = Utility.generateRandomString();
		derivedRatePlanName = derivedRatePlanName + randomString;
		derivedRatePlanFolioDisplayName = derivedRatePlanFolioDisplayName + randomString;
		derivedRatePlanDescription = derivedRatePlanDescription + randomString;

		parentRatePlanName = parentRatePlanName + randomString;
		parentRateFolioName = parentRateFolioName + randomString;
		parentRateDescription = parentRateDescription + randomString;
		String restrictionType = "Length of stay,Booking window,Promo code";
		String selectedDate = null;
		String percent = "percent";
		String seasonDuration = "2";
		String currencyOptions = null;
		String valueOptions = "greater than,lesser than";
		String alwaysAvailable = "Always available";
		String customDateRange = "Custom date range";
		String customDateErrorMessage = "Date ranges cannot have overlapping dates";
		String calendarTodayDay = "today";
		String seasonDateFormat = "dd/M/yyyy";
		String calendarSelectedDay = "selected";
		String currentMonthsPlace = "3";
		String todayDate = null;
		String parentSeasonColor = null;
		String currencySign = "";
		String currencyName = "";

		String summaryParentRatePlanOffset = null;
		String summaryChannels = null;
		String summaryRoomClasses = null;
		String parentRoomClasses = null;
		String derivedRateValues = "";
		String restrictionsSummary = null;
		boolean isProrateCheckbox = false;
		app_logs.info("season Level Rules" + isSeasonLevelRules);

		app_logs.info("season Level Rules" + isSeasonLevelRules.equalsIgnoreCase("yes"));

		ArrayList<String> getAllRoomClasses = new ArrayList<>();
		for (String s : roomClassesName.split(",")) {
			getAllRoomClasses.add(s);
		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();

			try {
				Login login = new Login();
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
				// loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				Login login = new Login();
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
				// loginWPI(driver);
			}
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
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

			String currencyNameAndSign = admin.getSelectedCurrencyNameAndSign(driver);
			testSteps.add("Selecetd currency: " + currencyNameAndSign);
			app_logs.info("Currency: " + currencyNameAndSign);
			currencyName = currencyNameAndSign.split(" ")[0].trim();
			app_logs.info("Currency Name: " + currencyName);
			currencySign = currencyNameAndSign
					.substring(currencyNameAndSign.indexOf("(") + 1, currencyNameAndSign.indexOf(")") - 1).trim();
			app_logs.info("Currency Sign: " + currencySign);
			getTestSteps.clear();
			getTestSteps = navigation.inventoryBackwardAdmin(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Selected Currency from client info", testName,
						"GetSelectedCurrency", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Selected Currency from client info", testName,
						"GetSelectedCurrency", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Rate

		try {
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, seasonDateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			app_logs.info("Today date : " + todayDate);
			app_logs.info("Excel Sheet start Date : " + seasonStartDate);
			app_logs.info("Excel Sheet end Date : " + seasonEndDate);
			app_logs.info("Excel Sheet custom start Date : " + customStartDate);
			app_logs.info("Excel Sheet custom end Date : " + customEndDate);

			testSteps.add("Excel Sheet start Date : " + seasonStartDate);
			testSteps.add("Excel Sheet end Date : " + seasonEndDate);
			testSteps.add("Excel Sheet custom start Date : " + customStartDate);
			testSteps.add("Excel Sheet custom end Date : " + customEndDate);

			if (Utility.compareDates(seasonStartDate.split(Utility.DELIM)[0], todayDate, seasonDateFormat) >= 0) {

			} else {
				seasonStartDate = todayDate;
				seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), seasonDateFormat, seasonStartDate,
						seasonDateFormat, timeZone);
				customStartDate = seasonStartDate;
				customEndDate = seasonEndDate;
			}
			app_logs.info("Selected start Date : " + seasonStartDate);
			app_logs.info("Selected end Date : " + seasonEndDate);
			app_logs.info("Selected custom start Date : " + customStartDate);
			app_logs.info("Selected custom end Date : " + customEndDate);
			testSteps.add("Selected start Date : " + seasonStartDate);
			testSteps.add("Selected end Date : " + seasonEndDate);
			testSteps.add("Selected custom start Date : " + customStartDate);
			testSteps.add("Selected custom end Date : " + customEndDate);
			getTestSteps.clear();
			selectedDate = ratesGrid.getCalendarDate(driver, calendarSelectedDay, seasonDateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);

			app_logs.info("Current Selected date : " + selectedDate);

			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", testName,
						"NavigateToRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", testName,
						"NavigateToRateGrid", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// Create New Rate
		try {

			testSteps.add("=================== CREATE PARENT(" + parentRatePlanType.toUpperCase()
					+ ") RATE PLAN ======================");
			app_logs.info("=================== CREATE PARENT(" + parentRatePlanType.toUpperCase()
					+ ") RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, parentRatePlanType);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			nightlyRate.enterRatePlanName(driver, parentRatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, parentRateFolioName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, parentRateDescription, testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			if (parentRatePlanType.equalsIgnoreCase("Package rate plan")) {

				testSteps.add("=================== SELECT RATE TYPE ======================");
				app_logs.info("=================== SELECT RATE TYPE ======================");

				getTestSteps.clear();
				getTestSteps = ratePackage.selectParentRatePlan(driver, packageRatePlanRateType);
				testSteps.addAll(getTestSteps);
				if (packageRatePlanRateType.equalsIgnoreCase("Interval rates")) {

					getTestSteps.clear();
					getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
					testSteps.addAll(getTestSteps);

					isProrateCheckbox = Boolean.parseBoolean(isDefaultProrateChecked);
					getTestSteps.clear();
					getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, isProrateCheckbox);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add("=================== ADDING PRODUCT IN PACKAGE RATE PLAN ======================");
				app_logs.info("=================== ADDING PRODUCT IN PACKAGE RATE PLAN ======================");

				for (String prod : packageRatePlanProductName.split(",")) {
					getTestSteps.clear();
					getTestSteps = ratePackage.addProductInPackageRatePlan(driver, packageRatePlanProductAmount, prod,
							packageratePlanProductFirstCalculationMethod,
							packageratePlanProductsecondCalculationMethod);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);
			}

			if (parentRatePlanType.contains("Interval")) {

				getTestSteps.clear();
				getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName);
				testSteps.addAll(getTestSteps);

				testSteps.add("=================== ENTER INTERVAL RATE PLAN LENGTH ======================");
				app_logs.info("=================== ENTER INTERVAL RATE PLAN LENGTH ======================");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
				testSteps.addAll(getTestSteps);
				isProrateCheckbox = Boolean.parseBoolean(isDefaultProrateChecked);
				getTestSteps.clear();
				getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, isProrateCheckbox);
				testSteps.addAll(getTestSteps);
				nightlyRate.clickNextButton(driver, testSteps);
			}

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, testSteps);
			if (parentRatePlanType.contains("Interval")) {
				String IsProrateCheckboxChecked = intervalRatePlanIntervalValue + " nights;";
				if (isProrateCheckbox) {
					IsProrateCheckboxChecked = IsProrateCheckboxChecked + " " + "prorate cost for partial stay";
				}
				nightlyRate.verifyTitleSummaryValue(driver, "Interval Length", IsProrateCheckboxChecked, testSteps);
			}
			nightlyRate.selectChannels(driver, channels, true, testSteps);
			if (parentRatePlanType.equalsIgnoreCase("Interval rate plan")) {
				summaryChannels = channels;
			} else {
				summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
			}
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			ArrayList<String> roomClasses = new ArrayList<String>();
			roomClasses = nightlyRate.getAllRoomClassesNames(driver);
			String[] parentBaseAmountList = parentBaseAmount.split(Utility.DELIM);
			app_logs.info("Room Classes : " + roomClasses);
			parentRoomClass = "";

			String derivedValue = null;
			for (int i = 0; i < Integer.parseInt(parentRoomClassSize); i++) {
				if (i < roomClasses.size() && i < 6) {
					if (i != 0) {
						parentRoomClass = parentRoomClass + delim;
					}
					parentRoomClass = parentRoomClass + roomClasses.get(i);

					if (derivedRateType.equals("percent")) {
						if (selectComparator.equals("greater than")) {
							derivedValue = Integer.toString(Integer.parseInt(parentBaseAmountList[i])
									+ (Integer.parseInt(parentBaseAmountList[i]) * Integer.parseInt(derivedRateValue)));
						} else if (selectComparator.equals("lesser than")) {
							derivedValue = Integer.toString(Integer.parseInt(parentBaseAmountList[i])
									- (Integer.parseInt(parentBaseAmountList[i]) * Integer.parseInt(derivedRateValue)));
						}
					} else {
						if (selectComparator.equals("greater than")) {
							derivedValue = Integer.toString(
									Integer.parseInt(parentBaseAmountList[i]) + Integer.parseInt(derivedRateValue));
						} else if (selectComparator.equals("lesser than")) {
							derivedValue = Integer.toString(
									Integer.parseInt(parentBaseAmountList[i]) - Integer.parseInt(derivedRateValue));
						}
					}
				}
				if (i == 0) {
					derivedRateValues = derivedValue;
				} else {

					derivedRateValues = derivedRateValues + Utility.DELIM + derivedValue;
				}
			}
			app_logs.info("Parent Room Clas : " + parentRoomClass);
			nightlyRate.selectRoomClasses(driver, parentRoomClass, true, testSteps);

			if (parentRoomClass.equals("All")) {
				summaryRoomClasses = "All room classes selected";
			} else {
				summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			}

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses);
			testSteps.addAll(getTestSteps);
			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
					Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					withInDaysCount, promoCode, testSteps);

			restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

			nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
					Boolean.parseBoolean(isPolicesReq), testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
					Boolean.parseBoolean(isPolicesReq), testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  create parent rateplan", testName, "CreateNewParentRatePlan",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  create parent rateplan", testName, "CreateNewParentRatePlan",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.createSeasons(driver, testSteps, seasonStartDate, seasonEndDate, seasonName, isMonDay, isTueDay,
					isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults,
					ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight, additionalChildPerNight,
					isAddRoomClassInSeason, extraRoomClassesInSeason, extraRoomClassRatePerNight,
					extraRoomClassMaxAdults, extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
					extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSeasonLevelRules,
					seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
					isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
					isSeasonRuleOnSaturday, isSeasonRuleOnSunday, seasonPolicyType, seasonPolicyValues,
					isSeasonPolicies);
			nightlyRate.clickCompleteChanges(driver, testSteps);
			try {
				nightlyRate.clickSaveAsActive(driver, testSteps);
			} catch (Exception f) {
				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.clickSaveAsActive(driver, testSteps);
			}

			testSteps.add("PARENT " + parentRatePlanType.toUpperCase() + " CREATED");
			app_logs.info("PARENT " + parentRatePlanType.toUpperCase() + " CREATED");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			testSteps.add("=== " + "Verify the newly created Parent(" + parentRatePlanType.toUpperCase()
					+ ") Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ===");
			app_logs.info("=== " + "Verify the newly created Parent(" + parentRatePlanType.toUpperCase()
					+ ") Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ===");
			try {
				driver.navigate().refresh();
				Wait.wait3Second();
				driver.navigate().refresh();
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
			} catch (Exception e) {
				driver.navigate().refresh();
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
			}
			testSteps.add(
					"Successfully verified that newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info(
					"Successfully verified that newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "VerifyCreatedRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "VerifyCreatedRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
//navigation.Inventory(driver);
//			navigation.RatesGrid(driver);
//			testSteps.add("Navigated to RatesGrid");
			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select derived rate plan option", testName,
						"DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select derived rate plan option", testName,
						"DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, derivedRatePlanFolioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, derivedRatePlanDescription, testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// parentRatePlanName = "FarhanDummyRatePlan";

		try {

			testSteps.add("=================== " + "SELECT PARENT " + parentRatePlanType.toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "SELECT PARENT " + parentRatePlanType.toUpperCase()
					+ " ======================");

			derivedRate.selectRatePlan(driver, parentRatePlanName, true, testSteps);

			testSteps.add("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");

			derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand Value Comparison DropDown");
			app_logs.info("Expand Value Comparison DropDown");
			derivedRate.selectDropDownOptions(driver, selectComparator, testSteps);
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");

			derivedRate.selectDropDownOptions(driver, derivedRateType, testSteps);

			testSteps.add("===== ENTER VALUE =====");
			app_logs.info("===== ENTER VALUE =====");
			derivedRate.enterRateValue(driver, derivedRateValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			if (derivedRateType.equals("percent")) {
				Assert.assertEquals(value, derivedRateValue + "%", "Failed : Value missmatched");
			} else {
				Assert.assertEquals(value, derivedRateValue, "Failed : Value missmatched");
			}

			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(istakenRulesFromParentRateplan),
					testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
			testSteps.add("=================== SELECT DATES ======================");
			app_logs.info("=================== SELECT DATES ======================");
			derivedRate.selectDates(driver, customDateRange, testSteps);
			derivedRate.customDateRangeAppear(driver, true, testSteps);
			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomStartAndEndDates(driver, customStartDate, customEndDate,
					seasonDateFormat);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.selectChannels(driver, channels, true, testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, roomClassesName, true, testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
					Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					withInDaysCount, promoCode, testSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRestriction(driver, ristrictionType,
					Boolean.parseBoolean(isRatePlanRistrictionReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			if (Boolean.parseBoolean(isRatePlanRistrictionReq)) {
				String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(ristrictionType,
						Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode);

				getTestSteps.clear();
				nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, getTestSteps);
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);
			getTestSteps.clear();
			nightlyRate.verifySelectedPolicy(driver, policiesType, Boolean.parseBoolean(isPolicesReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
					Boolean.parseBoolean(isPolicesReq), testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			nightlyRate.clickSaveAsActive(driver, testSteps);
			Utility.rateplanName = derivedRatePlanName;
			testSteps.add("=================== DERIVED RATE PLAN CREATED ======================");
			app_logs.info("=================== DERIVED RATE PLAN CREATED ======================");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			testSteps.add("=================== "
					+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			driver.navigate().refresh();
			Wait.wait3Second();
			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, derivedRatePlanName);
			testSteps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			testSteps.add("=================== "
					+ "Verify the newly created Derived Rate Plan in the Rates Grid's Rate Plan drop down".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify the newly created Derived Rate Plan in the Rates Grid's Rate Plan drop down".toUpperCase()
					+ " ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDerivedRateDisplay(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("=================== " + "EDIT DERIVED RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "EDIT DERIVED RATE PLAN".toUpperCase() + " ======================");
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanName, true, testSteps);
			derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, testSteps);

			testSteps.add("=================== VERIFY EDIT RATE PLAN INITIAL STATE AND UPDATE ======================");
			app_logs.info("=================== VERIFY EDIT RATE PLAN INITIAL STATE AND UPDATE ======================");

			nightlyRate.verifyRatePlanNameEditPage(driver, derivedRatePlanName, testSteps);
			nightlyRate.verifySaveRatePlanButton(driver, false, true, testSteps);
			nightlyRate.verifyRatePlanTypeEditPage(driver, "Derived rate plan", testSteps);

			testSteps.add("=================== UPDATE RATE PLAN DEFAULT STATE ===================");
			app_logs.info("=================== UPDATE RATE PLAN DEFAULT STATE ===================");
			nightlyRate.selectAsDefaultRatePlan(driver, testSteps, "Yes");
			nightlyRate.verifyIsDefaultRatePlan(driver, testSteps, true);

			testSteps.add("=================== UPDATE RATE PLAN STATUS ===================");
			app_logs.info("=================== UPDATE RATE PLAN STATUS ===================");
			nightlyRate.ratePlanStatusChange(driver, false, testSteps);
			nightlyRate.verifySelectedRatePlanStatus(driver, false, testSteps);

			String updateRatePlanName = "Updated" + derivedRatePlanName;
			String updateFolioDisplayName = "Updated" + derivedRatePlanFolioDisplayName;
			String updateRatePlanDescription = "Updated" + derivedRatePlanDescription;
			testSteps.add("=================== UPDATE RATE PLAN NAME ===================");
			app_logs.info("=================== UPDATE RATE PLAN NAME ===================");
			nightlyRate.enterRatePlanName(driver, updateRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, updateRatePlanName, true, testSteps);

			testSteps.add("=================== UPDATE RATE PLAN FOLIO DISPLAY NAME ===================");
			app_logs.info("=================== UPDATE RATE PLAN FOLIO DISPLAY NAME ===================");
			nightlyRate.enterRateFolioDisplayName(driver, updateFolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, updateFolioDisplayName, true, testSteps);

			testSteps.add("=================== UPDATE RATE PLAN DESCRIPTION ======================");
			app_logs.info("=================== UPDATE RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, updateRatePlanDescription, testSteps);

			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, updateRatePlanDescription, true, testSteps);

			testSteps.add("=================== UPDATE PARENT RATE PLAN OFFSET ======================");
			app_logs.info("=================== UPDATE PARENT RATE PLAN OFFSET ======================");
			String getValue = derivedRate.getDropdownValue(driver, 1, testSteps);

			if (getValue.equalsIgnoreCase(percent)) {
				derivedRate.expandCurrencyValueDropdown(driver, 0);
				testSteps.add("Expand Currency DropDown");
				app_logs.info("Expand Currency DropDown");

				derivedRate.selectDropDownOptions(driver, currencyName, testSteps);

				derivedRate.enterRateValue(driver, derivedRateValue, testSteps);
				String value = derivedRate.getRateValue(driver, testSteps);
				Assert.assertEquals(value, derivedRateValue, "Failed : Value missmatched");

			} else {
				derivedRate.expandCurrencyValueDropdown(driver, 0);
				testSteps.add("Expand Currency DropDown");
				app_logs.info("Expand Currency DropDown");

				derivedRate.selectDropDownOptions(driver, percent, testSteps);

				derivedRate.enterRateValue(driver, derivedRateValue, testSteps);
				String value = derivedRate.getRateValue(driver, testSteps);
				Assert.assertEquals(value, derivedRateValue + "%", "Failed : Value missmatched");
			}
			getValue = derivedRate.getDropdownValue(driver, 2, testSteps);

			app_logs.info(valueOptions.split(",")[0]);
			app_logs.info(valueOptions.split(",")[1]);
			if (getValue.equalsIgnoreCase(valueOptions.split(",")[0])) {

				derivedRate.expandCurrencyValueDropdown(driver, 1);
				testSteps.add("Expand Value Comparison DropDown");
				app_logs.info("Expand Value Comparison DropDown");
				derivedRate.selectDropDownOptions(driver, valueOptions.split(",")[1], testSteps);
			} else {
				derivedRate.expandCurrencyValueDropdown(driver, 1);
				testSteps.add("Expand Value Comparison DropDown");
				app_logs.info("Expand Value Comparison DropDown");
				derivedRate.selectDropDownOptions(driver, valueOptions.split(",")[0], testSteps);

			}

			testSteps.add("=================== UPDATE 'TAKE RULE FROM PARENT RATE PLAN' ======================");
			app_logs.info("=================== UPDATE 'TAKE RULE FROM PARENT RATE PLAN' ======================");

			derivedRate.takeRuleFromParentRatePlanCheckBox(driver,
					!Boolean.parseBoolean(istakenRulesFromParentRateplan), testSteps);

			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(istakenRulesFromParentRateplan),
					testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update derived rate plan", testName, "DerivedRatePlanUpdation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update derived rate plan", testName, "DerivedRatePlanUpdation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== DISTRIBUTION CHANNEL UPDATE ======================");
			app_logs.info("===================  DISTRIBUTION CHANNEL UPDATE ======================");
			nightlyRate.selectChannelsEditPage(driver, channels, true, testSteps);
			nightlyRate.verifySelectedChannelsEditPage(driver, channels, true, testSteps);

			testSteps.add("=================== ROOM CLASS UPDATE ======================");
			app_logs.info("===================  ROOM CLASS UPDATE ======================");

			nightlyRate.selectRoomClassesEditPage(driver, roomClassesName, false, testSteps);
			nightlyRate.verifySelectedRoomClassesEditPage(driver, roomClassesName, false, testSteps);

			nightlyRate.switchRestrictionAndPoliciesTab(driver, testSteps);

			testSteps.add("=================== RESTRICTION UPDATE ======================");
			app_logs.info("===================  RESTRICTION UPDATE ======================");

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
					Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					withInDaysCount, promoCode, testSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRestriction(driver, ristrictionType,
					Boolean.parseBoolean(isRatePlanRistrictionReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			if (Boolean.parseBoolean(isRatePlanRistrictionReq)) {
				String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(ristrictionType,
						Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode);

				getTestSteps.clear();
				nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, getTestSteps);
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);
			getTestSteps.clear();
			nightlyRate.verifySelectedPolicy(driver, policiesType, Boolean.parseBoolean(isPolicesReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
					Boolean.parseBoolean(isPolicesReq), testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update policies in derived rate plan", testName,
						"DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update policies in derived rate plan", testName,
						"DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== CALENDAR UPDATE ======================");
			app_logs.info("===================  CALENDAR UPDATE ======================");
			nightlyRate.clickCalender(driver, testSteps);
			nightlyRate.switchCalendarTab(driver, testSteps);

			derivedRate.selectDates(driver, alwaysAvailable, testSteps);
			derivedRate.customDateRangeAppear(driver, false, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update calender in derived rate plan", testName,
						"DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update calender in derived rate plan", testName,
						"DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=="
					+ "Verify that during modification of rate plan, in Calendar 'View parent rate plan' link is displayed"
							.toUpperCase()
					+ " ==");
			app_logs.info("=================== "
					+ "Verify that during modification of rate plan, in Calendar 'View parent rate plan' link is displayed"
							.toUpperCase()
					+ " ======================");
			derivedRate.verifyViewParentRatePlanLinkExist(driver, testSteps);

			testSteps.add(
					"==" + "Verify that on click on 'View parent rate plan' link Parent rate plan popup is displayed"
							.toUpperCase() + " ==");
			app_logs.info("=================== "
					+ "Verify that on click on 'View parent rate plan' link Parent rate plan popup is displayed"
							.toUpperCase()
					+ " ======================");
			derivedRate.clickviewParentRatePlanLink(driver, testSteps);
			derivedRate.closeParentRatePlanPopup(driver, testSteps);

			testSteps.add("=="
					+ "Verify that 'Load more dates' link should be displayed in the calendar tab's bottom after all the month's dates are displayed"
							.toUpperCase()
					+ "==");
			app_logs.info("=================== "
					+ "Verify that 'Load more dates' link should be displayed in the calendar tab's bottom after all the month's dates are displayed"
							.toUpperCase()
					+ " ======================");
			derivedRate.verifyLoadMoreDatesExist(driver, testSteps);

			testSteps.add("=="
					+ "Verify that during modification, upon click of 'Load more dates' link next 2 months calendar dates are displayed"
							.toUpperCase()
					+ "==");
			app_logs.info("=================== "
					+ "Verify that during modification, upon click of 'Load more dates' link next 2 months calendar dates are displayed"
							.toUpperCase()
					+ " ======================");
			derivedRate.clickLoadMoreDates(driver, testSteps);

			testSteps.add("=="
					+ "Verify that during modification, upon toggling of Always available and custom date range Calendar data is displayed based on selection."
							.toUpperCase()
					+ "==");
			app_logs.info("=================== "
					+ "Verify that during modification, upon toggling of Always available and custom date range Calendar data is displayed based on selection."
							.toUpperCase()
					+ " ======================");
			derivedRate.selectDates(driver, "Always available", testSteps);
			derivedRate.customDateRangeAppear(driver, false, testSteps);
			derivedRate.selectDates(driver, "Custom date range", testSteps);
			derivedRate.customDateRangeAppear(driver, true, testSteps);
			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that upon toggling of Always available and custom date range Calendar data is displayed based on selection",
						testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that upon toggling of Always available and custom date range Calendar data is displayed based on selection",
						testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Derived rate Plan
		try {

			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, derivedRatePlanName);
			testSteps.add("=================== " + "DELETE PARENT " + parentRatePlanType.toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "DELETE PARENT " + parentRatePlanType.toUpperCase()
					+ " ======================");
			getTestSteps.clear();
			getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, parentRatePlanName, "Delete", getTestSteps);
			testSteps.addAll(getTestSteps);
			testSteps.add("Successfully Delete  " + parentRatePlanType.toUpperCase() + " '" + parentRatePlanName + "'");
			app_logs.info("Successfully Delete " + parentRatePlanType.toUpperCase() + " '" + parentRatePlanName + "'");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete derived rate plan", testName, "DerivedRatePlanDeletion",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete derived rate plan", testName, "DerivedRatePlanDeletion",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Generate Report
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName + "_DerivedFrom" + partOftestName,
					testDescription, testCategory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateDerivedRateCustomDate", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
