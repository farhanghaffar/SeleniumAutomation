package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

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
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ValidateDerivedRatePlanWithCustomDateRangeV2 extends TestCore {
	
	//AUTOMATION-1759
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
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void validateDerivedRatePlanWithCustomDateRangeV2(String clientName, String delim,
			String derivedRatePlanName, String derivedRatePlanFolioDisplayName, String derivedRatePlanDescription,
			String channels, String roomClassesName,  String isRatePlanRistrictionReq, String ristrictionType,
			String isMinStay, String minNights, String isMaxStay, String maxNights, String isMoreThanDaysReq,
			String moreThanDaysCount, String isWithInDaysReq, String withInDaysCount, String promoCode,String isPolicesReq, String policiesType,
			String policiesName, String specialCharactersRatePlanName, String moreThanMaximumLengthRatePlanName,
			String specialCharactersFolioDisplayName, String moreThanMaximumLengthFolioDisplayName,
			String specialCharactersRatePlanDescription, String moreThanMaximumLengthRatePlanDescription,
			String descriptionInSeperatelines, String percentValue, String invalidPercentValue, String currencyValue,
			String invalidcurrencyValue, String parentSeasonName, String isAdditionalChargesForChildrenAdults,
			String parentMaxAdults, String parentMaxPersons, String parentAdditionalAdultsPerNight,
			String parentAdditionalChildPerNight, String parentIsAddRoomClassInSeason, String parentRatePlanName,
			String parentBaseAmount, String addtionalAdult, String additionalChild, String parentRateFolioName,
			String parentRateDescription, String parentRoomClass, String parentRoomClassSize, String seasonRules,
			String seasonRuleMinStayValue, String istakenRulesFromParentRateplan, String seasonStartDate, String seasonEndDate,
			String parentRatePlanType, String intervalRatePlanIntervalValue, String isDefaultProrateChecked,
			String packageRatePlanRateType, String packageRatePlanProductName,
			String packageratePlanProductFirstCalculationMethod, String packageratePlanProductsecondCalculationMethod,
			String packageRatePlanProductAmount) throws InterruptedException, IOException {

		Utility.DELIM = delim;

		test_name = "ValidateDerivedRatePlanWithCustomDateRangeV2";
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
		ArrayList<String> ratePlanNames = new ArrayList<String>();
		String randomString = Utility.generateRandomString();
		derivedRatePlanName = derivedRatePlanName + randomString;
		String ratePlanNameBackup = derivedRatePlanName;
		moreThanMaximumLengthRatePlanName = moreThanMaximumLengthRatePlanName + randomString;

		parentRatePlanName = parentRatePlanName + randomString;
		parentRateFolioName = parentRateFolioName + randomString;
		parentRateDescription = parentRateDescription + randomString;
		// parentRatePlanName = "ParentRatePlan7AUE6s9C01";
		// RatePlanName = "DerivedRateY3O4Gj6GM8";
		existingRateName = parentRatePlanName;
		List<String>[] ratePlansList = new List[3];
		String summaryParentRatePlanOffset = null;
		String summaryChannels = null;
		String summaryRoomClasses = null;
		String parentRoomClasses = null;
		
		  ArrayList<String> nightlyRatePlan = new ArrayList<String>();
		 ArrayList<String> intervalRatePlan = new ArrayList<String>();
		 ArrayList<String> packageRatePlan = new ArrayList<String>();
		 ArrayList<String> parentRoomClassesNames = new ArrayList<String>();
		ArrayList<String> parentRoomClassesRates = new ArrayList<String>();
		ArrayList<String> roomClassesDerivedRates = new ArrayList<String>();
		String rules = "No Rules";
		ArrayList<String> parentRatePlanPopupRoomClassesWithRates = new ArrayList<String>();
		ArrayList<String> derivedRatePlanPopupRoomClassesWithRates = new ArrayList<String>();
		String restrictionsSummary = null;
		String derivedRateValue = null;
		String timeZone = "America/New_York";

		
		String todayDate = null;
		String todayDateIs = null;
		String parentSeasonColor = null;
		boolean isProrateCheckbox = false;

		String selectedDate = null;
		String missmatchedPopupMessage = "The Parent Rate Plan does not have rates for part or all of this date range. Would you still like to add it?";
		String newDate = null;
		String currencySign = "";
		String currencyName = null;
		String percent = "percent";
		String seasonDuration = "2";
		String currencyOptions = null;
		String valueOptions = "greater than,lesser than";
		String alwaysAvailable = "Always available";
		String customDateRange = "Custom date range";
		String customDateErrorMessage = "Date ranges cannot have overlapping dates";
		String dateFormat = "MM/dd/yyyy";
		String calendarTodayDay = "today";
		String parentSeasonDateFormat = "dd/M/yyyy";
		String calendarSelectedDay = "selected";
		String selectComparator = "greater than";
		String currentMonthsPlace = "";
		
		

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
				login.login(driver, envURL, "autorates", "autouser","Auto@123");
				//loginWPI(driver);
			} catch (Exception e) {
				try {
					driver.navigate().refresh();
				} catch (Exception refresh) {

				}
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				Login login = new Login();
				login.login(driver, envURL, "autorates", "autouser","Auto@123");
				//loginWPI(driver);
			}
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			app_logs.info(Utility.addDate(1, dateFormat, "10/02/2020", dateFormat, timeZone));

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
			currencyOptions = currencyName + "," + percent;
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
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, getTestSteps);
			todayDateIs = todayDate;
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			selectedDate = ratesGrid.getCalendarDate(driver, calendarSelectedDay, dateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);

			app_logs.info("Current Selected date : " + selectedDate);

			if (Utility.compareDates(selectedDate, todayDate, dateFormat) > 0) {
				todayDate = selectedDate;
			}
			
			app_logs.info("Today date : " + todayDate);
			app_logs.info("Excel Sheet start Date : " + seasonStartDate);
			app_logs.info("Excel Sheet end Date : " + seasonEndDate);

			if (Utility.compareDates(seasonStartDate, todayDate, dateFormat) > 0) {

			} else {
				seasonStartDate = todayDate;
				seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), dateFormat, seasonStartDate,
						dateFormat, timeZone);
			}
			app_logs.info("Selected start Date : " + seasonStartDate);
			app_logs.info("Selected end Date : " + seasonEndDate);
			
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

			testSteps.add("=================== CREATE PARENT("+parentRatePlanType.toUpperCase()+") RATE PLAN ======================");
			app_logs.info("=================== CREATE PARENT("+parentRatePlanType.toUpperCase()+") RATE PLAN ======================");

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

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName);
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

				getTestSteps.clear();
				getTestSteps = ratePackage.addProducts(driver, packageRatePlanProductName, packageRatePlanProductAmount, packageratePlanProductFirstCalculationMethod, packageratePlanProductsecondCalculationMethod);
				testSteps.addAll(getTestSteps);

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
			app_logs.info("Room Classes : " + roomClasses);
			parentRoomClass = "";
			for (int i = 0; i < Integer.parseInt(parentRoomClassSize); i++) {
				if (i < roomClasses.size() && i < 6) {
					if (i != 0) {
						parentRoomClass = parentRoomClass + delim;
					}
					parentRoomClass = parentRoomClass + roomClasses.get(i);
					parentRoomClassesNames.add(roomClasses.get(i));
					parentRoomClassesRates.add(parentBaseAmount);

					if (selectComparator.equals("greater than")) {
						derivedRateValue = Integer
								.toString(Integer.parseInt(parentBaseAmount) + Integer.parseInt(currencyValue));
					} else if (selectComparator.equals("lesser than")) {
						derivedRateValue = Integer
								.toString(Integer.parseInt(parentBaseAmount) - Integer.parseInt(currencyValue));
					}
					roomClassesDerivedRates.add(derivedRateValue);
					parentRatePlanPopupRoomClassesWithRates
							.add(roomClasses.get(i) + " " + currencySign + parentBaseAmount);
					derivedRatePlanPopupRoomClassesWithRates.add(roomClasses.get(i) + " " + currencySign
							+ parentBaseAmount + " " + currencySign + derivedRateValue);
				}
			}
			app_logs.info("Parent Room Clas : " + parentRoomClass);
			app_logs.info("Parent Room Classes : " + parentRoomClassesNames);
			app_logs.info("Parent Room Classes Rates : " + parentRoomClassesRates);
			app_logs.info("Parent Rate Plan Popup : " + parentRatePlanPopupRoomClassesWithRates);
			app_logs.info("Derive dRate Plan Popup : " + derivedRatePlanPopupRoomClassesWithRates);
			app_logs.info("Derived Rates : " + roomClassesDerivedRates);
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
			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq),
					ristrictionType, Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay),
					maxNights, Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode, testSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRestriction(driver, ristrictionType, Boolean.parseBoolean(isRatePlanRistrictionReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			if(Boolean.parseBoolean(isRatePlanRistrictionReq)){
			String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(ristrictionType, Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay),
					maxNights, Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode);

			nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, testSteps);
			}
			restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
			nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
					Boolean.parseBoolean(isPolicesReq), testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
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
			try {
				nightlyRate.selectSeasonDates(driver, testSteps,
						Utility.parseDate(seasonStartDate, dateFormat, parentSeasonDateFormat),
						Utility.parseDate(seasonEndDate, dateFormat, parentSeasonDateFormat));
			} catch (Exception f) {
				nightlyRate.clickCreateSeason(driver, testSteps);
				nightlyRate.selectSeasonDates(driver, testSteps,
						Utility.parseDate(seasonStartDate, dateFormat, parentSeasonDateFormat),
						Utility.parseDate(seasonEndDate, dateFormat, parentSeasonDateFormat));
			}
			nightlyRate.enterSeasonName(driver, testSteps, parentSeasonName);
			nightlyRate.selectSeasonDays(driver, getTestSteps, "Yes", "Yes", "Yes", "Yes", "Yes", "Yes", "Yes");
			testSteps.add("Select All Days");
			app_logs.info("Select All Days");
			nightlyRate.clickCreateSeason(driver, testSteps);
			parentSeasonColor = nightlyRate.selectSeasonColor(driver, testSteps);
			app_logs.info("Parent season color  is : " + parentSeasonColor);
			// parentSeasonColor = "rgb(" + parentSeasonColor + ")";
			testSteps.add("Parent season color  is : " + parentSeasonColor);
			app_logs.info("Parent season color  is : " + parentSeasonColor);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, testSteps,
					isAdditionalChargesForChildrenAdults);
			nightlyRate.enterRate(driver, testSteps, parentBaseAmount, isAdditionalChargesForChildrenAdults,
					parentMaxAdults, parentMaxPersons, parentAdditionalAdultsPerNight, parentAdditionalChildPerNight);
			nightlyRate.enterRateForAllRoomClasses(driver, 1, getTestSteps, parentBaseAmount);
			nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);
			nightlyRate.enterSeasonLevelRules(driver, testSteps, "No", "", seasonRules, seasonRuleMinStayValue, "yes",
					"yes", "yes", "yes", "yes", "yes", "yes");
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickCompleteChanges(driver, testSteps);
			try {
				nightlyRate.clickSaveAsActive(driver, testSteps);
			} catch (Exception f) {
				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.clickSaveAsActive(driver, testSteps);
			}

			testSteps.add("PARENT RATE PLAN CREATED");
			app_logs.info("PARENT RATE PLAN CREATED");
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
			testSteps.add(
					"=== " + "Verify the newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase() + " ===");
			app_logs.info(
					"=== " + "Verify the newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase() + " ===");
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

//			testSteps.add("=================== GET ALL RATE PLANS ======================");
//			app_logs.info("=================== GET ALL RATE PLANS ======================");
//			ratesGrid.clickRatePlanArrow(driver, testSteps);
//			ArrayList<String> ratePlanTypes = new ArrayList<String>();
//			ratePlanTypes.add("Nightly");
//			ratePlanTypes.add("Interval");
//			ratePlanTypes.add("Package");
//			ratePlansList = ratesGrid.getActiveRatePlans(driver, ratePlanTypes, 0);
//			nightlyRatePlan = (ArrayList<String>) ratePlansList[0];
//			intervalRatePlan = (ArrayList<String>) ratePlansList[1];
//			packageRatePlan = (ArrayList<String>) ratePlansList[2];
//
//			existingRateName = parentRatePlanName;
//			testSteps.add("Successfully Get all(" + nightlyRatePlan.size() + ") Nightly rate plan ");
//			app_logs.info("Successfully Get all(" + nightlyRatePlan.size() + ") Nightly and Interval rate plan "
//					+ nightlyRatePlan);
//			testSteps.add("Successfully Get all(" + intervalRatePlan.size() + ") Interval rate plan ");
//			app_logs.info(
//					"Successfully Get all(" + intervalRatePlan.size() + ") Interval rate plan " + intervalRatePlan);
//			testSteps.add("Successfully Get all(" + packageRatePlan.size() + ") Package rate plan ");
//			app_logs.info("Successfully Get all(" + packageRatePlan.size() + ") Package rate plan " + packageRatePlan);
 
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all rate plans from rate plan dropdown", testName,
						"GetAllRatePlans", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all rate plans from rate plan dropdown", testName,
						"GetAllRatePlans", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			/*
			 * //This is implementation of create derived rate plan in single
			 * method. testSteps.
			 * add("=================== CREATE DERIVED RATE PLANS ======================"
			 * ); app_logs.
			 * info("=================== CREATE DERIVED RATE PLANS ======================"
			 * ); getTestSteps.clear(); getTestSteps =
			 * derivedRate.createDerivedRatePlan(driver, "Derived rate plan",
			 * RatePlanName, FolioDisplayName, ratePlanDescription,
			 * parentRatePlanName, valueOptions.split(",")[1], currencyName,
			 * currencyValue, false, customDateRange, todayDate, nextDate,
			 * dateFormat, parentChannels, parentRoomClass);
			 * testSteps.addAll(getTestSteps);
			 */

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan by using single method", testName,
						"DerivedRateCreationWithSingleMethod", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan by using single method", testName,
						"DerivedRateCreationWithSingleMethod", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("=================== VALIDATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== VALIDATE NEW DERIVED RATE PLAN ======================");

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
				Utility.updateReport(e, "Failed to navigate to derived rate plan", testName,
						"NavigateToDerivedRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to derived rate plan", testName,
						"NavigateToDerivedRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== RATE PLAN INITIAL STATE ======================");
			app_logs.info("=================== RATE PLAN INITIAL STATE ======================");

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyNextButton(driver, false, true, testSteps);
			nightlyRate.verifyRatePlanNameVisibility(driver, true, true, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameVisibility(driver, true, true, testSteps);
			nightlyRate.verifyRatePlanDescriptionVisibility(driver, true, true, testSteps);
			nightlyRate.verifyCharCountRatePlanDescription(driver, "0", testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan initial state", testName,
						"VerifyInitialStateRatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan initial state", testName,
						"VerifyInitialStateRatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== RATE PLAN NAME VALIDATIONS ======================");
			app_logs.info("=================== RATE PLAN NAME VALIDATIONS ======================");
			testSteps.add("=================== VERIFY RATE PLAN NAME MANADATORY FIELD ======================");
			app_logs.info("=================== VERIFY RATE PLAN NAME MANADATORY FIELD ======================");

			nightlyRate.enterAlreadyExistOrClearRatePlanName(driver, derivedRatePlanName, false, testSteps);
			testSteps.add("=================== ENTER EXISTING RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER EXISTING RATE PLAN NAME ======================");

			nightlyRate.enterAlreadyExistOrClearRatePlanName(driver, existingRateName, true, testSteps);

			testSteps.add("=================== ENTER EMPTY RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER EMPTY RATE PLAN NAME ======================");
			nightlyRate.enterRatePlanName(driver, "", testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, "", true, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "Rate Plan Name cannot be empty", true, testSteps);

			testSteps.add("=================== ENTER VALID RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER VALID RATE PLAN NAME ======================");
			nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, derivedRatePlanName, true, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "", false, testSteps);

			testSteps.add("=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN NAME ======================");
			nightlyRate.enterRatePlanName(driver, specialCharactersRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, specialCharactersRatePlanName, true, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "", false, testSteps);
			testSteps.add(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN NAME ======================");
			app_logs.info(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN NAME ======================");

			nightlyRate.enterRatePlanName(driver, moreThanMaximumLengthRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, moreThanMaximumLengthRatePlanName, false, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "", false, testSteps);
			int foundFeildLenght = nightlyRate.getRatePlanInputFeiledLength(driver, testSteps);
			assertNotEquals(foundFeildLenght, moreThanMaximumLengthRatePlanName.length(),
					"Failed To Verify Rate Plan Feild Length");
			testSteps.add("Successfully Verified Rate Plan Name can not be entered more than maximum length");
			app_logs.info("Successfully Verified Rate Plan Name can not be entered more than maximum length");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate rate plan name", testName, "ValidateRatePLanName", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate rate plan name", testName, "ValidateRatePLanName", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== FOLIO DISPLAY NAME VALIDATIONS ======================");
			app_logs.info("=================== FOLIO DISPLAY NAME VALIDATIONS ======================");
			testSteps.add("=================== VERIFY FOLIO DISPLAY NAME MANADATORY FIELD ======================");
			app_logs.info("=================== VERIFY FOLIO DISPLAY NAME MANADATORY FIELD ======================");
			nightlyRate.verifyRatePlanFolioDisplayNameRequiredFeild(driver, derivedRatePlanFolioDisplayName, testSteps);

			testSteps.add("=================== ENTER EMPTY FOLIO DISPLAY NAME ======================");
			app_logs.info("=================== ENTER EMPTY FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, "", testSteps);
			try {
				nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, "", true, testSteps);
			} catch (Exception e) {
				nightlyRate.enterRateFolioDisplayName(driver, "", testSteps);
				nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, "", true, testSteps);
			}
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, true, testSteps);

			testSteps.add("=================== ENTER VALID FOLIO DISPLAY NAME ======================");
			app_logs.info("=================== ENTER VALID FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, derivedRatePlanFolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, derivedRatePlanFolioDisplayName, true,
					testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, false, testSteps);

			testSteps.add("=================== ENTER SEPECIAL CHARACTERS AS FOLIO DISPLAY NAME ======================");
			app_logs.info("=================== ENTER SEPECIAL CHARACTERS AS FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, specialCharactersFolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, specialCharactersFolioDisplayName, true,
					testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, false, testSteps);
			testSteps.add(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS FOLIO DISPLAY NAME ======================");
			app_logs.info(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, moreThanMaximumLengthFolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, moreThanMaximumLengthFolioDisplayName, false,
					testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, false, testSteps);
			int foundFolioDisplayFeildLenght = nightlyRate.getRatePlanFolioDisplayNameInputFeiledLength(driver,
					testSteps);
			assertNotEquals(foundFolioDisplayFeildLenght, moreThanMaximumLengthFolioDisplayName.length(),
					"Failed To Verify Rate Plan Feild Length");

			testSteps.add("Successfully Verified Folio Display Name can not be entered more than maximum length");
			app_logs.info("Successfully Verified Folio Display Name can not be entered more than maximum length");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate folio display name", testName, "ValidateFolioDisplayName",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate folio display name", testName, "ValidateFolioDisplayName",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== RATE PLAN DESCRIPTION VALIDATIONS ======================");
			app_logs.info("=================== RATE PLAN DESCRIPTION VALIDATIONS ======================");

			testSteps.add("=================== ENTER VALID RATE PLAN DESCRIPTION ======================");
			app_logs.info("=================== ENTER VALID RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, derivedRatePlanDescription, testSteps);
			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, derivedRatePlanDescription, true, testSteps);

			testSteps.add(
					"=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, specialCharactersRatePlanDescription, testSteps);
			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, specialCharactersRatePlanDescription, true,
					testSteps);
			testSteps.add(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, moreThanMaximumLengthRatePlanDescription, testSteps);
			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, moreThanMaximumLengthRatePlanDescription, false,
					testSteps);

			int foundDescLength = nightlyRate.getRatePlanDescriptionInputFeiledLength(driver, testSteps);
			assertNotEquals(foundDescLength, moreThanMaximumLengthRatePlanDescription.length(),
					"Failed To Verify Rate Plan Feild Length");

			testSteps.add("Successfully Verified RATE PLAN DESCRIPTION can not be entered more than maximum length");
			app_logs.info("Successfully Verified RATE PLAN DESCRIPTION can not be entered more than maximum length");

			nightlyRate.verifyCharCountRatePlanDescription(driver, "" + foundDescLength + "", testSteps);

			testSteps.add(
					"== " + " # of characters/255 within Description is displayed in blue color at bottom right of the Description field "
							.toUpperCase() + " ==");
			app_logs.info(
					"== " + " # of characters/255 within Description is displayed in blue color at bottom right of the Description field"
							.toUpperCase() + " ==");

			nightlyRate.enterRatePlanDescription(driver, derivedRatePlanDescription, testSteps);
			nightlyRate.verifyCharCountRatePlanDescription(driver, "" + derivedRatePlanDescription.length() + "",
					testSteps);

			String descriptionCharCountColor = nightlyRate.getCharCountColorRatePlanDescription(driver);
			testSteps.add("Description Char count Color : " + descriptionCharCountColor);
			app_logs.info("Description Char count Color : " + descriptionCharCountColor);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate rate plan description", testName,
						"ValidateRatePlanDesciption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate rate plan description", testName,
						"ValidateRatePlanDesciption", driver);
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan details", testName, "EnterRatePlanDetails", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan details", testName, "EnterRatePlanDetails", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			
//			testSteps.add(
//					"==" + "Verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property (which 						includes Rate Plans created in V2 (Nightly Package and Interval Rates)"
//							.toUpperCase() + " ==");
//			app_logs.info("=================== "
//					+ "Verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property (which 							includes Rate Plans created in V2 (Nightly Package and Interval Rates)"
//							.toUpperCase()
//					+ " ======================");
//			testSteps.add("========== VERIFY ALL ACTIVE NIGHTLY RATE PLAN =========");
//			app_logs.info("========== VERIFY ALL ACTIVE NIGHTLY RATE PLAN =========");
//			derivedRate.verifyRatePlansDisplayed(driver, nightlyRatePlan, getTestSteps);
//			testSteps.add("Successfully verified all active nightly rate Plan exist.");
//			app_logs.info("Successfully verified all active nightly rate Plan exist.");
//			testSteps.add("========== VERIFY ALL ACTIVE INTERVAL RATE PLAN =========");
//			app_logs.info("========== VERIFY ALL ACTIVE INTERVAL RATE PLAN =========");
//			derivedRate.verifyRatePlansDisplayed(driver, intervalRatePlan, getTestSteps);
//			testSteps.add("Successfully verified all active interval rate Plan exist.");
//			app_logs.info("Successfully verified all active interval rate Plan exist.");
//
//			testSteps.add("========== VERIFY ALL ACTIVE PACKAGE RATE PLAN =========");
//			app_logs.info("========== VERIFY ALL ACTIVE PACKAGE RATE PLAN =========");
//			derivedRate.verifyRatePlansDisplayed(driver, packageRatePlan, getTestSteps);
//			testSteps.add("Successfully verified all active package rate Plan exist.");
//			app_logs.info("Successfully verified all active package rate Plan exist.");
			  
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property",
						testName, "VerifyAllRatePlans", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property",
						testName, "VerifyAllRatePlans", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("======== SELECT PARENT RATE PLAN ========");
			app_logs.info("======== SELECT PARENT RATE PLAN ========");
			derivedRate.selectRatePlan(driver, parentRatePlanName, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select parent rate plan", testName, "SelectParentRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select parent rate plan", testName, "SelectParentRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== "
					+ "Verify that when deriving Rates user can choose percent/currency (USD (based on the currency setting in Admin-Client Info)) greater/lesser than the derived rate from"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that when deriving Rates user can choose percent/currency (USD (based on the currency setting in Admin-Client Info)) greater/lesser than the derived rate from"
							.toUpperCase()
					+ " ======================");

			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");
			derivedRate.verifyDropDownOptionsExist(driver, 1, currencyOptions, testSteps);

			derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand Value Comparison DropDown");
			app_logs.info("Expand Value Comparison DropDown");
			derivedRate.verifyDropDownOptionsExist(driver, 2, valueOptions, testSteps);

			derivedRate.selectDropDownOptions(driver, selectComparator, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that when deriving Rates user can choose percent/currency (USD (based on the currency setting in Admin-Client Info)) greater/lesser than the derived rate from",
						testName, "ValidatePercentCurrencyOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that when deriving Rates user can choose percent/currency (USD (based on the currency setting in Admin-Client Info)) greater/lesser than the derived rate from",
						testName, "ValidatePercentCurrencyOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== "
					+ "Verify that upon choosing percent the rate value is changed to % and % is shown next to the Rate value"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that upon choosing percent the rate value is changed to % and % is shown next to the Rate value"
							.toUpperCase()
					+ " ======================");
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");
			derivedRate.selectDropDownOptions(driver, percent, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertTrue(value.contains("%"), "Failed : '%' symbol is not displayed  with value");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that upon choosing percent the rate value is changed to % and % is shown next to the Rate value",
						testName, "ValidatePercentageSymbol", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that upon choosing percent the rate value is changed to % and % is shown next to the Rate value",
						testName, "ValidatePercentageSymbol", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== "
					+ "Verify that only values from 0 to 99 can be provided in the % rate value".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that only values from 0 to 99 can be provided in the % rate value".toUpperCase()
					+ " ======================");
			testSteps.add("===== ENTER VALUE GREATER THAN 99 =====");
			app_logs.info("===== ENTER VALUE GREATER THAN 99 =====");
			derivedRate.enterRateValue(driver, invalidPercentValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, "99%", "Failed : Value missmatched");
			testSteps.add("===== ENTER NEGATIVE INVALID PERCENT VALUE =====");
			app_logs.info("===== ENTER NEGATIVE INVALID PERCENT VALUE =====");
			getTestSteps.clear();
			derivedRate.enterRateValue(driver, "-" + invalidPercentValue, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			value = derivedRate.getRateValue(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			Assert.assertEquals(value, "99" + "%", "Failed : Value missmatched");
			testSteps
					.add("Verified that only values greater than or equal to zero can be provided in the % rate value");

			testSteps.add("===== ENTER VALUE LESS THAN 99 =====");
			app_logs.info("===== ENTER VALUE LESS THAN 99 =====");
			derivedRate.enterRateValue(driver, percentValue, testSteps);
			value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, percentValue + "%", "Failed : Value missmatched");
			testSteps.add("Successfully verified that only values from 0 to 99 can be provided in the % rate value");
			app_logs.info("Successfully verified that only values from 0 to 99 can be provided in the % rate value");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate percentage rate value field", testName,
						"ValidatePercentageRateValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate percentage rate value field", testName,
						"ValidatePercentageRateValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== " + "Verify that upon choosing USD the rate value is prefixed with "
					+ currencySign + " (based on the Admin-Client Info setting)".toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "Verify that upon choosing USD the rate value is prefixed with "
					+ currencySign + " (based on the Admin-Client Info setting)".toUpperCase()
					+ " ======================");
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");
			derivedRate.selectDropDownOptions(driver, currencyName, testSteps);
			derivedRate.verifyCurrencySignDisplayed(driver, currencySign, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that upon choosing USD the rate value is prefixed with currency symbol",
						testName, "ValidateCurrencySymbol", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that upon choosing USD the rate value is prefixed with currency symbol",
						testName, "ValidateCurrencySymbol", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== "
					+ "Verify that when deriving Rates user can enter up to 4 digits of rate value".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that when deriving Rates user can enter up to 4 digits of rate value".toUpperCase()
					+ " ======================");

			testSteps.add("===== ENTER FIVE DIGIT VALUE =====");
			app_logs.info("===== ENTER FIVE DIGIT VALUE =====");
			derivedRate.enterRateValue(driver, invalidcurrencyValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, "9999", "Failed : Value missmatched");
			testSteps.add("===== ENTER LESS THAN FIVE DIGIT VALUE =====");
			app_logs.info("===== ENTER LESS THAN FIVE DIGIT VALUE =====");
			derivedRate.enterRateValue(driver, currencyValue, testSteps);
			value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, currencyValue, "Failed : Value missmatched");
			testSteps.add("Successfully verified that when deriving Rates user can enter up to 4 digits of rate value");
			app_logs.info("Successfully verified that when deriving Rates user can enter up to 4 digits of rate value");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that when deriving Rates user can enter up to 4 digits of rate value",
						testName, "ValidateDigitsInRateValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that when deriving Rates user can enter up to 4 digits of rate value",
						testName, "ValidateDigitsInRateValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== "
					+ "Verify that user sees and can check 'Take rules from parent rate plan'".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that user sees and can check 'Take rules from parent rate plan'".toUpperCase()
					+ " ======================");
			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(istakenRulesFromParentRateplan),
					testSteps);
			testSteps.add("CheckBox 'Take rules from parent rate plan' status is '"
					+ Boolean.parseBoolean(istakenRulesFromParentRateplan) + "'");
			app_logs.info("CheckBox 'Take rules from parent rate plan' status is '"
					+ Boolean.parseBoolean(istakenRulesFromParentRateplan) + "'");

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that user sees and can check 'Take rules from parent rate plan'", testName,
						"ValidateTakeRuleFromParentCheckBox", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that user sees and can check 'Take rules from parent rate plan'", testName,
						"ValidateTakeRuleFromParentCheckBox", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			summaryParentRatePlanOffset = "Derived rates will be " + currencyValue + " " + currencySign + " "
					+ selectComparator + " rates in " + parentRatePlanName;
			testSteps.add("=================== DERIVED PLAN DATES INITIAL STATE ======================");
			app_logs.info("===================  DERIVED PLAN DATESL INITIAL STATE ======================");

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate derived rate plan initial dates", testName,
						"ValidateInitialDates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate derived rate plan initial dates", testName,
						"ValidateInitialDates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"=================== VERIFY <b> ALWAYS AVAILABLE</b> AND <b>CUSTOM DATE RANGE</b> OPTIONS ARE SHOWN IN THE DATES ======================");
			app_logs.info(
					"=================== VERIFY <b> ALWAYS AVAILABLE</b> AND <b>CUSTOM DATE RANGE</b> OPTIONS ARE SHOWN IN THE DATES ======================");
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDatesOptionDisplayed(driver, alwaysAvailable, true);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDatesOptionDisplayed(driver, customDateRange, true);
			testSteps.addAll(getTestSteps);
			derivedRate.selectDates(driver, alwaysAvailable, testSteps);
			derivedRate.customDateRangeAppear(driver, false, testSteps);
			derivedRate.selectDates(driver, customDateRange, testSteps);
			derivedRate.customDateRangeAppear(driver, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify always available and custom date range option are showing",
						testName, "VerifyDatesOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify always available and custom date range option are showing",
						testName, "VerifyDatesOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==VERIFY THAT TODAY'S DATE IS SHOWN IN BOTH START DATE AND END DATE IN THE CUSTOM DATE RANGE (AS SOON AS USER SELECTS CUSTOM DATE RANGE RADIO BUTTON) ==");
			app_logs.info(
					"== VERIFY THAT TODAY'S DATE IS SHOWN IN BOTH START DATE AND END DATE IN THE CUSTOM DATE RANGE (AS SOON AS USER SELECTS CUSTOM DATE RANGE RADIO BUTTON) ==");

			derivedRate.verifyCustomDate(driver, 0, selectedDate, dateFormat);
			derivedRate.verifyCustomDate(driver, 1, selectedDate, dateFormat);
			testSteps.add("Successfully Verified today's Date is shown in both start and end dates");
			app_logs.info("Successfully Verified today's Date is shown in both start and end dates");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that today's Date is shown in both start and end dates",
						testName, "VerifyTodayDateInCustomDateRange", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that today's Date is shown in both start and end dates",
						testName, "VerifyTodayDateInCustomDateRange", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"=================== VERIFY THAT MORE THAN ONE DATE RANGE CAN BE CREATED AS PART OF CUSTOM DATE RANGE ======================");
			app_logs.info(
					"=================== VERIFY THAT MORE THAN ONE DATE RANGE CAN BE CREATED AS PART OF CUSTOM DATE RANGE ======================");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickAddCustomDate(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully verified user can add more than one date range");
			app_logs.info("Successfully verified user can add more than one date range");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that user can add more than one date range", testName,
						"VerifyCustomDateRangeNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that user can add more than one date range", testName,
						"VerifyCustomDateRangeNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"=================== VERIFY THAT USER CAN DELETE NEWLY ADDED CUSTOM DATE RANGES ======================");
			app_logs.info(
					"=================== VERIFY THAT USER CAN DELETE NEWLY ADDED CUSTOM DATE RANGES ======================");
			getTestSteps.clear();
			getTestSteps = derivedRate.removeCustomDate(driver, 1);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully verified user can delete newly added custom date range");
			app_logs.info("Successfully verified user can delete newly added custom date range");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that user can delete newly added custom date range", testName,
						"VerifyCustomDateRangeDeletion", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that user can delete newly added custom date range", testName,
						"VerifyCustomDateRangeDeletion", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"======== VERIFY THAT NEXT BUTTON IS ENABLED ONLY WHEN THERE ARE NO OVERLAPPING DATE RANGES =====");
			app_logs.info(
					"====== VERIFY THAT NEXT BUTTON IS ENABLED ONLY WHEN THERE ARE NO OVERLAPPING DATE RANGES ====");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickAddCustomDate(driver);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyNextButton(driver, false, true, testSteps);

			
			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 2,
					Utility.addDate(1, dateFormat, seasonStartDate, dateFormat, timeZone), dateFormat);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyNextButton(driver, true, true, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that next button is enabled only when there are no overllaping date ranges",
						testName, "VerifyNextButtonEnabled", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that next button is enabled only when there are no overllaping date ranges",
						testName, "VerifyNextButtonEnabled", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==== VERIFY THAT ON OVERLAP 'Date ranges cannot have overlapping dates' IN RED COLOR SHOULD BE DISPLAYED ====");
			app_logs.info(
					"=== VERIFY THAT ON OVERLAP 'Date ranges cannot have overlapping dates' IN RED COLOR SHOULD BE DISPLAYED ========");
			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 0, seasonStartDate, dateFormat);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 2, seasonStartDate, dateFormat);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.customDateRangeErrorMessageDisplayed(driver, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifycustomDateRangeErrorMessage(driver, customDateErrorMessage);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.removeCustomDate(driver, 1);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that on overlap 'Date ranges cannot have overlapping dates' in red colour is displaying",
						testName, "OverlapMessageVisibility", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that on overlap 'Date ranges cannot have overlapping dates' in red colour is displaying",
						testName, "OverlapMessageVisibility", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"=== VERIFY IF CUSTOM DATE RANGE IS PROVIDED BEYOND PARENT'S SEASON END DATE THEN 'Date Range Mismatch' MESSAGE WILL APPEAR ===");
			app_logs.info(
					"=== VERIFY IF CUSTOM DATE RANGE IS PROVIDED BEYOND PARENT'S SEASON END DATE THEN 'Date Range Mismatch' MESSAGE WILL APPEAR ===");

			int beyondParentDate = Integer.parseInt(seasonDuration) + 1;
			testSteps.add("Before Changing Custom  start Date : " + seasonStartDate);
			app_logs.info("Before Changing Custom start Date : " + seasonStartDate);
			derivedRate.verifyCustomDate(driver, 0, seasonStartDate, dateFormat);

			testSteps.add("Before Changing Custom end Date : " + seasonStartDate);
			app_logs.info("Before Changing Custom end Date : " + seasonStartDate);
			derivedRate.verifyCustomDate(driver, 1, seasonStartDate, dateFormat);
			newDate = Utility.addDate(beyondParentDate, dateFormat, seasonStartDate, dateFormat, timeZone);

			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 0, newDate, dateFormat);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDateRangeMissmatchPopup(driver, missmatchedPopupMessage);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify date range mismatch popup if custom date range is beyond parent season",
						testName, "VerifyDateRangeMismatchPopup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify date range mismatch popup if custom date range is beyond parent season",
						testName, "VerifyDateRangeMismatchPopup", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==== UPON CLICK ON <b>NO</b> FOR 'DATE RANGE MISSMATCHED' MESSAGE the date entered IN CUSTOM DATE RANGE SHOULD BE RESET ====");
			app_logs.info(
					"==== UPON CLICK ON <b>NO</b> FOR 'DATE RANGE MISSMATCHED' MESSAGE THE DATE ENTERED IN CUSTOM DATE RANGE SHOULD BE RESET ====");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickDateRangeMissmatchedPopupButton(driver, "No");
			testSteps.addAll(getTestSteps);

			testSteps
					.add("After Clicking No  of 'Date Range Missmatched' Popup Custom start Date : " + seasonStartDate);
			app_logs.info("After Clicking No of 'Date Range Missmatched' Popup Custom start Date : " + seasonStartDate);
			derivedRate.verifyCustomDate(driver, 0, seasonStartDate, dateFormat);

			testSteps.add("After Clicking No  of 'Date Range Missmatched' Popup Custom end Date : " + seasonStartDate);
			app_logs.info("After Clicking No of 'Date Range Missmatched' Popup Custom end Date : " + seasonStartDate);
			derivedRate.verifyCustomDate(driver, 1, seasonStartDate, dateFormat);

			testSteps.add("Verified date range has been reset");
			app_logs.info("Verified date range has been reset");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify on clicking no button for date range mismatch popup date range is reset",
						testName, "VerifyDateRangeReset", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify on clicking no button for date range mismatch popup date range is reset",
						testName, "VerifyDateRangeReset", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==== UPON CLICK ON <b>YES</b> FOR 'DATE RANGE MISSMATCHED' MESSAGE USER SHOULD BE ALLOWED TO PROCEED ON ====");
			app_logs.info(
					"==== UPON CLICK ON <b>YES</b> FOR 'DATE RANGE MISSMATCHED' MESSAGE USER SHOULD BE ALLOWED TO PROCEED ON ====");

			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 0, newDate, dateFormat);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDateRangeMissmatchPopup(driver, missmatchedPopupMessage);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.clickDateRangeMissmatchedPopupButton(driver, "Yes");
			testSteps.addAll(getTestSteps);

			testSteps.add("After Clicking No  of 'Date Range Missmatched' Popup Custom start Date : " + newDate);
			app_logs.info("After Clicking No of 'Date Range Missmatched' Popup Custom start Date : " + newDate);
			derivedRate.verifyCustomDate(driver, 0, newDate, dateFormat);

			testSteps.add("After Clicking No  of 'Date Range Missmatched' Popup Custom end Date : " + newDate);
			app_logs.info("After Clicking No of 'Date Range Missmatched' Popup Custom end Date : " + newDate);
			derivedRate.verifyCustomDate(driver, 1, newDate, dateFormat);
			testSteps.add("Verified date has been changed");
			app_logs.info("Verified date has been changed");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify on clicking yes button for date range mismatch popup user should be allow to proceed on",
						testName, "VerifyDateRangeProceedOnClickingYes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify on clicking yes button for date range mismatch popup user should be allow to proceed on",
						testName, "VerifyDateRangeProceedOnClickingYes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"===  VERIFY THAT UPON CLICK OF 'VIEW SEASON CALENDAR' 2 TABS ARE DISPLAYED (1st TAB IS FOR DERIVED FROM(PARENT) RATE NAME AND @ND TAB IS FOR CURRENT DERIVED RATE PLAN NAME  ===");
			app_logs.info(
					"=== VERIFY THAT UPON CLICK OF 'VIEW SEASON CALENDAR' 2 TABS ARE DISPLAYED (1st TAB IS FOR DERIVED FROM(PARENT) RATE NAME AND 2ND TAB IS FOR CURRENT DERIVED RATE PLAN NAME  ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickViewSeasonCalendar(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifySeasonCalendarTabExist(driver, parentRatePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifySeasonCalendarTabExist(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify upon clicking view season calendar two tabs displaying",
						testName, "TabsINViewSeasonCalendar", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify upon clicking view season calendar two tabs displaying",
						testName, "TabsINViewSeasonCalendar", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==== VERIFY IN 2ND TAB THE PROVIDED OUTSIDE THE PARENT DATE RANGE DATES SHOULD BE SHOWN IN SHADED MANNER ===");
			app_logs.info(
					"=== VERIFY IN 2ND TAB THE PROVIDED OUTSIDE THE PARENT DATE RANGE DATES SHOULD BE SHOWN IN SHADED MANNER ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyShadedBackground(driver, newDate, dateFormat, 2);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify shaded date range in second tab", testName,
						"VerifyShadedDateRange", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify shaded date range in second tab", testName,
						"VerifyShadedDateRange", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==== " + "Verify that in the view season calendar current date is shown with underline in first tab"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that in the view season calendar current date is shown with underline in first tab"
							.toUpperCase() + " ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, parentRatePlanName);
			testSteps.addAll(getTestSteps);

			String underline = "underline";
			testSteps.add("Expected : " + underline);
			app_logs.info("Expected : " + underline);

			String expectedProperty = derivedRate.getCurrentDateUnderlineProperty(driver, todayDateIs, dateFormat, 1);
			testSteps.add("Found : " + expectedProperty);
			app_logs.info("Found : " + expectedProperty);
			assertEquals(underline, expectedProperty, "Failed : Current date is not underlined");
			app_logs.info(
					"Verified that in the view season calendar current date is shown with underline in first tab");
			testSteps
					.add("Verified that in the view season calendar current date is shown with underline in first tab");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the view season calendar current date is shown with underline in first tab",
						testName, "VerifyUnderlineCurrentDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the view season calendar current date is shown with underline in first tab",
						testName, "VerifyUnderlineCurrentDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==== " + "Verify that in the view season calendar current date is shown with underline in second tab"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that in the view season calendar current date is shown with underline in second tab"
							.toUpperCase() + " ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			String underline = "underline";
			testSteps.add("Expected : " + underline);
			app_logs.info("Expected : " + underline);

			String expectedProperty = derivedRate.getCurrentDateUnderlineProperty(driver, todayDateIs, dateFormat, 2);
			testSteps.add("Found : " + expectedProperty);
			app_logs.info("Found : " + expectedProperty);

			assertEquals(underline, expectedProperty, "Failed : Current date is not underlined");
			app_logs.info(
					"Verified that in the view season calendar current date is shown with underline in second tab");
			testSteps.add(
					"Verified that in the view season calendar current date is shown with underline in second tab");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the view season calendar current date is shown with underline in second tab",
						testName, "VerifyUnderlineCurrentDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the view season calendar current date is shown with underline in second tab",
						testName, "VerifyUnderlineCurrentDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"==== " + "Verify that in the View season calendar's 1st tab shows all the Seasons of the derived from rate in the same color as it was shown for the from rate plan"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that in the View season calendar's 1st tab shows all the Seasons of the derived from rate in the same color as it was shown for the from rate plan"
							.toUpperCase() + " ===");

			testSteps.add("==== " + "Verifying Parent Season date range background colour".toUpperCase() + " ===");
			app_logs.info("==== " + "Verifying Parent Season date range background colour".toUpperCase() + " ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, parentRatePlanName);
			testSteps.addAll(getTestSteps);

			testSteps.add("Expected : " + parentSeasonColor);
			app_logs.info("Expected : " + parentSeasonColor);

			for (int index = 0; index < +Integer.parseInt(seasonDuration); index++) {
				String foundColor = derivedRate.getDateBackgroundColour(driver,
						Utility.addDate(index, dateFormat, seasonStartDate, dateFormat, timeZone), dateFormat, 1);
				testSteps.add("Found : " + foundColor);
				app_logs.info("Found : " + foundColor);
				Assert.assertTrue(foundColor.contains(parentSeasonColor),
						"Failed : parent Season date range background colour didn't match");
			}
			app_logs.info(
					"Verified that Parent Season Date range background color is same as that is selected in Parent Rate Plan");
			testSteps.add(
					"Verified that Parent Season Date range background color is same as that is selected in Parent Rate Plan");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that Parent Season Date range background color is same as that is selected in Parent Rate Plan",
						testName, "VerifyDateRangeInSecondTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that Parent Season Date range background color is same as that is selected in Parent Rate Plan",
						testName, "VerifyDateRangeInSecondTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"==== " + "Verify that in the View season calendar's upon hovering mouse on the seasons within the 1st tab it is showing the Seasons Rate details along with Room class details"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that in the View season calendar's upon hovering mouse on the seasons within the 1st tab it is showing the Seasons Rate details along with Room class details"
							.toUpperCase() + " ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, parentRatePlanName);
			testSteps.addAll(getTestSteps);

			String columns = "Room Class Rate";
			if (Boolean.parseBoolean(istakenRulesFromParentRateplan)) {
				String[] ruleType = seasonRules.split("\\|");
				if (ruleType.length > 0 && ruleType != null) {
					rules = "Rules";
					for (int i = 0; i < ruleType.length; i++) {
						if (ruleType[i].equalsIgnoreCase("Min Nights")) {
							rules = rules + "," + seasonRuleMinStayValue + " nights min";
						} else if (ruleType[i].equalsIgnoreCase("No check-in")) {
							rules = rules + "," + "No check in";
						} else if (ruleType[i].equalsIgnoreCase("No check-out")) {
							rules = rules + "," + "No check out";
						}
					}
				}
			}
			app_logs.info("Rulee to verify  " + rules);
			getTestSteps.clear();
			getTestSteps = derivedRate.verifySeasonRatesDetailPopup(driver, 1, seasonStartDate, dateFormat,
					parentSeasonName, parentSeasonColor, columns, currencySign, parentRatePlanPopupRoomClassesWithRates,
					rules);
			
			testSteps.addAll(getTestSteps);

			app_logs.info(
					"Verified that in the View season calendar's upon hovering mouse on the seasons within the 1st tab it is showing the Seasons Rate details along with Room class details");
			testSteps.add(
					"Verified that in the View season calendar's upon hovering mouse on the seasons within the 1st tab it is showing the Seasons Rate details along with Room class details");
			getTestSteps.clear();
			getTestSteps = derivedRate.closeViewSeasonCalendar(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the View season calendar's upon hovering mouse on the seasons within the 1st tab it is showing the Seasons Rate details along with Room class details and rules",
						testName, "VerifySeasonPopupInFirstTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the View season calendar's upon hovering mouse on the seasons within the 1st tab it is showing the Seasons Rate details along with Room class details and rules",
						testName, "VerifySeasonPopupInFirstTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add(
					"==== " + "Verify that in the 2nd tab in view season calendar upon hovering mouse both Parent and Derived rate values are displayed"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that in the 2nd tab in view season calendar upon hovering mouse both Parent and Derived rate values are displayed"
							.toUpperCase() + " ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 0, seasonStartDate, dateFormat);
			testSteps.addAll(getTestSteps);

			try {
				getTestSteps.clear();
				getTestSteps = derivedRate.clickDateRangeMissmatchedPopupButton(driver, "Yes");
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

			}

			app_logs.info(newDate);
			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 1, newDate, dateFormat);
			testSteps.addAll(getTestSteps);
			try {
				getTestSteps.clear();
				getTestSteps = derivedRate.clickDateRangeMissmatchedPopupButton(driver, "Yes");
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

			}

			getTestSteps.clear();
			getTestSteps = derivedRate.clickViewSeasonCalendar(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			String columns = "Room Class ParentDerived";

			getTestSteps.clear();
			getTestSteps = derivedRate.verifySeasonRatesDetailPopup(driver, 2, seasonStartDate, dateFormat,
					parentSeasonName, parentSeasonColor, columns, currencySign,
					derivedRatePlanPopupRoomClassesWithRates, rules);
			testSteps.addAll(getTestSteps);

			app_logs.info(
					"Verified that in the 2nd tab in view season calendar upon hovering mouse both Parent and Derived rate values are displayed");
			testSteps.add(
					"Verified that in the 2nd tab in view season calendar upon hovering mouse both Parent and Derived rate values are displayed");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the 2nd tab in view season calendar upon hovering mouse both Parent and Derived rate values are displayed and are displayed in the same color of season",
						testName, "VerifySeasonPopupInSecondTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the 2nd tab in view season calendar upon hovering mouse both Parent and Derived rate values are displayed and are displayed in the same color of season",
						testName, "VerifySeasonPopupInSecondTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"==== " + "Verify that in the 2nd tab in view season calendar the date range is showing based on the custom date range start and end dates"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that in the 2nd tab in view season calendar the date range is showing based on the custom date range start and end dates"
							.toUpperCase() + " ===");

			testSteps.add(
					"==== " + "Verifying date range background colour which are same as Parent rate Plan ".toUpperCase()
							+ " ===");
			app_logs.info(
					"==== " + "Verifying date range background colour which are same as Parent rate Plan ".toUpperCase()
							+ " ===");

			for (int index = 0; index < +Integer.parseInt(seasonDuration); index++) {
				String foundColor = derivedRate.getDateBackgroundColour(driver,
						Utility.addDate(index, dateFormat, seasonStartDate, dateFormat, timeZone), dateFormat, 1);
				testSteps.add("Found : " + foundColor);
				app_logs.info("Found : " + foundColor);
				Assert.assertTrue(foundColor.contains(parentSeasonColor),
						"Failed : parent Season date range background colour didn't match");
			}
			app_logs.info("Verified that Date range background color is same as that is selected in Parent Rate Plan");
			testSteps.add("Verified that Date range background color is same as that is selected in Parent Rate Plan");

			testSteps.add("==== "
					+ "Verifying date range in Shaded backgrounnd which are beyond Parent rate Plan ".toUpperCase()
					+ " ===");
			app_logs.info("==== "
					+ "Verifying date range in Shaded backgrounnd which are beyond Parent rate Plan ".toUpperCase()
					+ " ===");

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyShadedBackground(driver, newDate, dateFormat, 2);
			testSteps.addAll(getTestSteps);

			app_logs.info(
					"Verified that in the 2nd tab in view season calendar the date range is showing based on the custom date range start and end dates");
			testSteps.add(
					"Verified that in the 2nd tab in view season calendar the date range is showing based on the custom date range start and end dates");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the 2nd tab in view season calendar the date range is showing based on the custom date range start and end dates",
						testName, "VerifyDateRangeInSecondTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in the 2nd tab in view season calendar the date range is showing based on the custom date range start and end dates",
						testName, "VerifyDateRangeInSecondTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"==== " + "Verify that 'Load more dates' link is displaying in the view season calendar both in 1st an 2nd tabs"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that 'Load more dates' link is displaying in the view season calendar both in 1st an 2nd tabs"
							.toUpperCase() + " ===");

			testSteps.add("Verifying 'Load more dates' link is displaying in first tab");
			app_logs.info("Verifying 'Load more dates' link is displaying in first tab");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, parentRatePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyLoadMoreDateLinkDisplay(driver, 1, false);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verifying 'Load more dates' link is displaying in second tab");
			app_logs.info("Verifying 'Load more dates' link is displaying in second tab");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyLoadMoreDateLinkDisplay(driver, 2, false);
			testSteps.addAll(getTestSteps);

			app_logs.info(
					"Verify that 'Load more dates' link is displaying in the view season calendar both in 1st an 2nd tabs");
			testSteps.add(
					"Verify that 'Load more dates' link is displaying in the view season calendar both in 1st an 2nd tabs");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that 'Load more dates' link is displaying", testName,
						"VerifyLoadMoreLink", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that 'Load more dates' link is displaying", testName,
						"VerifyLoadMoreLink", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"==== " + "Verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) in both 1st an 2nd tabs"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) in both 1st an 2nd tabs"
							.toUpperCase() + " ===");

			testSteps.add(
					"Verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) in first tab");
			app_logs.info(
					"Verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) in first tab");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, parentRatePlanName);
			testSteps.addAll(getTestSteps);

			String monthsNumber = Utility.parseDate(todayDate, dateFormat, "M");
			int monthPlace = Integer.parseInt(monthsNumber) % 3;
			currentMonthsPlace = Integer.toString(monthPlace);
			app_logs.info("month Place : " + monthPlace);
			int month = 0;
			for (int i = Integer.parseInt(currentMonthsPlace); i <= 12; i++) {
				getTestSteps.clear();
				getTestSteps = derivedRate.VerifyCurrentMonthExist(driver, 1, i,
						Utility.addMonth(month, dateFormat, todayDate, "MMMM yyyy", timeZone));
				testSteps.addAll(getTestSteps);
				month++;
			}
			testSteps.add(
					"Verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) in second tab");
			app_logs.info(
					"Verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) in second tab");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);
			month = 0;
			for (int i = Integer.parseInt(currentMonthsPlace); i <= 12; i++) {
				getTestSteps.clear();
				getTestSteps = derivedRate.VerifyCurrentMonthExist(driver, 2, i,
						Utility.addMonth(month, dateFormat, todayDate, "MMMM yyyy", timeZone));
				testSteps.addAll(getTestSteps);
				month++;
			}
			app_logs.info(
					"Verified in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) both in 1st an 2nd tabs");
			testSteps.add(
					"Successfully Verified in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) both in 1st an 2nd tabs");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) both in 1st an 2nd tabs",
						testName, "VerifyCalendarDates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that in view season calendar 1 year's months calendar dates are displayed (starting from the current date's month) both in 1st an 2nd tabs",
						testName, "VerifyCalendarDates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"==== " + "Verify that upon click of 'Load more dates' link next 3 months calendar dates are displayed in both 1st an 2nd tabs"
							.toUpperCase() + " ===");
			app_logs.info(
					"==== " + "Verify that upon click of 'Load more dates' link next 3 months calendar dates are displayed in both 1st an 2nd tabs"
							.toUpperCase() + " ===");

			testSteps.add(
					"Verify that upon click of 'Load more dates' link next 3 months calendar dates are displayed in first tab");
			app_logs.info(
					"Verify that upon click of 'Load more dates' link next 3 months calendar dates are displayed in first tab");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, parentRatePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.clickLoadMoreDates(driver, 1, 3);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Verify that upon click of 'Load more dates' link next 3 months calendar dates are displayed in second tab");
			app_logs.info(
					"Verify that upon click of 'Load more dates' link next 3 months calendar dates are displayed in second tab");

			getTestSteps.clear();
			getTestSteps = derivedRate.clickSeasonCalendarTab(driver, derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.clickLoadMoreDates(driver, 2, 3);
			testSteps.addAll(getTestSteps);

			app_logs.info(
					"Verify upon click of 'Load more dates' link next 3 months  are displaying in the view season calendar both in 1st an 2nd tabs");
			testSteps.add(
					"Verify that upon click of 'Load more dates' link next 3 months  are  displaying in the view season calendar both in 1st an 2nd tabs");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify upon click of 'Load more dates' link next 3 months calendar dates are displayed",
						testName, "VerifyLoadMoreLink", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify upon click of 'Load more dates' link next 3 months calendar dates are displayed",
						testName, "VerifyLoadMoreLink", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== CLOSE VIEW SEASON CALENDAR ======================");
			app_logs.info("=================== CLOSE VIEW SEASON CALENDAR ======================");

			getTestSteps.clear();
			getTestSteps = derivedRate.closeViewSeasonCalendar(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to close view season calendar", testName, "CloseSeasonCalendarr",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to close view season calendar", testName, "CloseSeasonCalendarr",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== DISTRIBUTION CHANNEL INITIAL STATE ======================");
			app_logs.info("===================  DISTRIBUTION CHANNEL INITIAL STATE ======================");

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", customDateRange);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifySelectedChannels(driver, "innCenter", true, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate distribution channel initial state", testName,
						"ValidateDistributionChannel", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate distribution channel initial state", testName,
						"ValidateDistributionChannel", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.selectChannels(driver, channels, true, testSteps);
			nightlyRate.verifySelectedChannels(driver, channels, true, testSteps);
			summaryChannels = channels;// nightlyRate.generateTitleSummaryValueForChannels(driver);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select distribution channel", testName, "SelectDistributionChannel",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select distribution channel", testName, "SelectDistributionChannel",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== ROOM CLASS PAGE INITIAL STATE ======================");
			app_logs.info("===================  ROOM CLASS PAGE INITIAL STATE ======================");

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", customDateRange);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class page initial state", testName,
						"ValidateRoomClassPage", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class page initial state", testName,
						"ValidateRoomClassPage", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");

			getTestSteps.clear();
			nightlyRate.selectRoomClasses(driver, roomClassesName, true, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRoomClasses(driver, roomClassesName, true, getTestSteps);
			testSteps.addAll(getTestSteps);

			summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", testName, "SelectRoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", testName, "SelectRoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== RESTRICTION PAGE INITIAL STATE ======================");
			app_logs.info("===================  RESTRICTION PAGE INITIAL STATE ======================");

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", customDateRange);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRestriction(driver,
					"Length of stay" + delim + "Booking window" + delim + "Promo code", false, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifyRestrictionsTypesCheckBoxes(driver,
					"Length of stay" + delim + "Booking window" + delim + "Promo code", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifyToolTipBookingWindow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifyToolTipPromoCode(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify restriction page initial state", testName,
						"ValidateRestrictionPage", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify restriction page initial state", testName,
						"ValidateRestrictionPage", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq),
					ristrictionType, Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay),
					maxNights, Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode, testSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRestriction(driver, ristrictionType, Boolean.parseBoolean(isRatePlanRistrictionReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			if(Boolean.parseBoolean(isRatePlanRistrictionReq)){
			String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(ristrictionType, Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay),
					maxNights, Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode);

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
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select restrictions to qualify rate", testName, "SelectRestrictions",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select restrictions to qualify rate", testName, "SelectRestrictions",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== POLICIES PAGE INITIAL STATE ======================");
			app_logs.info("===================  POLICIES PAGE INITIAL STATE ======================");

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", customDateRange);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate policies initial state", testName, "ValidatePoliciesPage",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to validate policies initial state", testName, "ValidatePoliciesPage",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);
			getTestSteps.clear();
			nightlyRate.verifySelectedPolicy(driver, policiesType,  Boolean.parseBoolean(isPolicesReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
					Boolean.parseBoolean(isPolicesReq), testSteps);
			
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", derivedRatePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", customDateRange);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc, Boolean.parseBoolean(isPolicesReq),
					getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select policies", testName, "SelectPolicies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select policies", testName, "SelectPolicies", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			nightlyRate.clickSaveAsActive(driver, testSteps);
			Utility.rateplanName = derivedRatePlanName;
			testSteps.add("=================== DERIVED RATE PLAN CREATED ======================");
			app_logs.info("=================== DERIVED RATE PLAN CREATED ======================");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click save as active", testName, "ClickSaveAsActive", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click save as active", testName, "ClickSaveAsActive", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			// navigation.Inventory(driver, testSteps); parentRoomClass = "#1 -
			// Double Bed,AngelHills,N Room1"; derivedRateValue = "110";
			// currencySign = "$";// will be deleted
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			testSteps.add(
					"===== " + "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase() + " ======");
			app_logs.info("=================== "
					+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			try {
				driver.navigate().refresh();
				Wait.wait3Second();
				driver.navigate().refresh();
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				ratesGrid.selectDateFromDatePicker(driver, seasonStartDate, dateFormat, testSteps);
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, seasonStartDate, dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps, derivedRatePlanName);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps, derivedRatePlanName);
				testSteps.addAll(getTestSteps);

			}
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
						"Failed to verify that that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "VerifyRateplanInRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						testName, "VerifyRateplanInRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("====== VERIFY DETAILS OF CREATED DERIVED RATE PLAN IN DERIVED RATES SECTION =========");
			app_logs.info("========= VERIFY DETAILS OF CREATED DERIVED RATE PLAN IN DERIVED RATES SECTION ==========");
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanName, true, testSteps);
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			String comparator = "";
			if (selectComparator.equals("greater than")) {
				comparator = " on top of ";
			} else if (selectComparator.equals("lesser than")) {
				comparator = " off ";
			}
			String derivedRatePlanDetails = "Derived Rate Plan: " + currencySign + currencyValue + comparator
					+ parentRatePlanName;

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanDetails(driver, derivedRatePlanName, derivedRatePlanDetails,
					getTestSteps);
			testSteps.addAll(getTestSteps);
			testSteps.add("======== VERIFY RESTRICTIONS ========");
			app_logs.info("======== VERIFY RESTRICTIONS ========");
			 
			if(Boolean.parseBoolean(isRatePlanRistrictionReq)){
			if (ristrictionType.contains("Length of Stay")) {
				String restriction = null;
				if(Boolean.parseBoolean(isMinStay) && Boolean.parseBoolean(isMaxStay)){
					if(maxNights.equals(minNights)){
						restriction = "Guest must stay " + minNights +" nights";
					}else{
						restriction = "Guest must stay between " + minNights + " to " + maxNights + " nights";
					}
				}else if(Boolean.parseBoolean(isMinStay)){
					restriction = "Guest must stay at least " + minNights +  " nights";
				}else if(Boolean.parseBoolean(isMaxStay)){
					restriction = "Guest can only stay a maximum of " + maxNights +  " nights";
				}
				getTestSteps.clear();
				getTestSteps = derivedRate.verifyDerivedRatePlanRestrictions(driver,
						derivedRatePlanName,
						restriction);
				testSteps.addAll(getTestSteps);
			}
			
			if (ristrictionType.contains("Booking window")) {
				String restriction = null;
				if(Boolean.parseBoolean(isMoreThanDaysReq) && Boolean.parseBoolean(isWithInDaysReq)){
					if(moreThanDaysCount.equals(withInDaysCount)){
						restriction = "Guest must purchase " + withInDaysCount + " days in advance";
					}else{
						restriction = "Guest must purchase " + moreThanDaysCount
								+ " to " + withInDaysCount + " days in advance";
					}
				}else if(Boolean.parseBoolean(isMoreThanDaysReq)){
					restriction = "Guest must purchase at least " + moreThanDaysCount + " days in advance";
				}else if(Boolean.parseBoolean(isWithInDaysReq)){
					restriction = "Guest must purchase not more than " + withInDaysCount + " days in advance";
				}
				getTestSteps.clear();
				getTestSteps = derivedRate.verifyDerivedRatePlanRestrictions(driver,
						derivedRatePlanName, restriction);
				testSteps.addAll(getTestSteps);
			}
			
			if (ristrictionType.contains("Promo code")) {
				getTestSteps.clear();
				getTestSteps = derivedRate.verifyDerivedRatePlanRestrictions(driver,
						derivedRatePlanName, "Guest must enter promo code " + promoCode);
				testSteps.addAll(getTestSteps);
				}
			}
			testSteps.add("Successfully verified Derived Rate Plan restrictions");
			app_logs.info("Successfully verified Derived Rate Plan restrictions");
			StringTokenizer token = new StringTokenizer(parentRoomClass, Utility.DELIM);
			while (token.hasMoreTokens()) {
				String roomClass = token.nextToken();
				testSteps.add("====== VERIFY Room Class '" + roomClass + "' DERIVED RATE VALUES =========");
				app_logs.info("======  VERIFY Room Class '" + roomClass + "' DERIVED RATE VALUES =========");
				for (int i = 1; i <= Integer.parseInt(seasonDuration); i++) {
					getTestSteps.clear();
					getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver, derivedRatePlanName, roomClass,
							derivedRateValue, i);
					testSteps.addAll(getTestSteps);
				}
				testSteps.add("Successfully verified Room Class '" + roomClass + "' Derived Rate values");
				app_logs.info("Successfully verified Room Class '" + roomClass + "' Derived Rate values");
			}

			testSteps.add("Successfully verified details of Created Derived Rate Plan");
			app_logs.info("Successfully verified details of Created Derived Rate Plan");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify details of New Derived Rate Plan", testName,
						"VerifyRateplanInRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify details of New Derived Rate Plan", testName,
						"VerifyRateplanInRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== "
					+ "Verifying on clicking ratesgrid, policies or product & bundles during rate creation a unsaved data popup appears"
							.toUpperCase()
					+ " ===================");
			app_logs.info("=================== "
					+ "Verifying on clicking ratesgrid, policies or product & bundles during rate creation a unsaved data popup appears"
							.toUpperCase()
					+ " ===================");

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);

			nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);

			derivedRate.clickTab(driver, "Rates Grid", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			derivedRate.newRateplanTabExist(driver, true, testSteps);
			derivedRate.clickTab(driver, "Distribution", testSteps);
			try {
				// driver.switchTo().alert().accept();
				driver.switchTo().alert().dismiss();
				testSteps.add("Alert!!!!!!  appear");
				app_logs.info("Alert!!!!!!  appear");

				testSteps.add("Dismiss Alert! ");
				app_logs.info("Dismiss Alert! ");
			} catch (Exception alert) {
				testSteps.add("Alert!!!!!! not appear");
				app_logs.info("Alert!!!!!! not appear");
			}
			derivedRate.newRateplanTabExist(driver, true, testSteps);
			derivedRate.clickTab(driver, "Policies", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			derivedRate.newRateplanTabExist(driver, true, testSteps);
			derivedRate.clickTab(driver, "Products & Bundles", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			derivedRate.newRateplanTabExist(driver, true, testSteps);

			getTestSteps.clear();
			derivedRate.closeOpenedRatePlanTab(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", getTestSteps);
			testSteps.addAll(getTestSteps);

			derivedRate.newRateplanTabExist(driver, true, testSteps);

			testSteps.add("=================== "
					+ "Verifying on clicking yes button in unsaved data popup rateplan creation tab is closed"
							.toUpperCase()
					+ " ===================");
			app_logs.info("=================== "
					+ "Verifying on clicking yes button in unsaved data popup rateplan creation tab is closed"
							.toUpperCase()
					+ " ===================");

			derivedRate.clickTab(driver, "Rates Grid", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);

			derivedRate.clickTab(driver, "Distribution", testSteps);
			try {
				driver.switchTo().alert().accept();
				testSteps.add("Alert!!!!!!  appear");
				app_logs.info("Alert!!!!!!  appear");

				testSteps.add("Accept Alert! ");
				app_logs.info("Accept Alert! ");
			} catch (Exception alert) {
				testSteps.add("Alert!!!!!! not appear");
				app_logs.info("Alert!!!!!! not appear");
			}
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");
			navigation.Inventory(driver, testSteps);
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);

			derivedRate.clickTab(driver, "Policies", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);

			derivedRate.clickTab(driver, "Products & Bundles", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);

			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that on clicking ratesgrid, policies or product & bundles during rate creation a unsaved data popup appears",
						testName, "VerifyClosePopup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that on clicking ratesgrid, policies or product & bundles during rate creation a unsaved data popup appears",
						testName, "VerifyClosePopup", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			// new Step
			testSteps.add("=================== "
					+ "Verify that user can edit the provided Rate plan name, date ranges, Channel, Room classes during the process of creation of the rate."
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that user can edit the provided Rate plan name, date ranges, Channel, Room classes during the process of creation of the rate."
							.toUpperCase()
					+ " ======================");

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
				Utility.updateReport(e,
						"Failed to verify that user can edit the provided Rate plan name, date ranges, Channel, Room classes during the process of creation of the rate",
						testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify that user can edit the provided Rate plan name, date ranges, Channel, Room classes during the process of creation of the rate",
						testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			derivedRatePlanName = derivedRatePlanName + Utility.generateRandomString();
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

		try {
			testSteps.add("=================== " + "SELECT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "SELECT RATE PLAN".toUpperCase() + " ======================");
			derivedRate.selectRatePlan(driver, parentRatePlanName, true, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to slect parent rate plan", testName, "SelectParentRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to slect parent rate plan", testName, "SelectParentRatePlan", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");

			derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand Value Comparison DropDown");
			app_logs.info("Expand Value Comparison DropDown");
			derivedRate.selectDropDownOptions(driver, valueOptions.split(",")[1], testSteps);
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");

			derivedRate.selectDropDownOptions(driver, currencyName, testSteps);

			testSteps.add("===== ENTER VALUE =====");
			app_logs.info("===== ENTER VALUE =====");
			derivedRate.enterRateValue(driver, currencyValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, currencyValue, "Failed : Value missmatched");
			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(istakenRulesFromParentRateplan), testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select parent rate plan offset", testName, "SelectRatePlanOffset",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select parent rate plan offset", testName, "SelectRatePlanOffset",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== SELECT DATES ======================");
			app_logs.info("=================== SELECT DATES ======================");
			derivedRate.selectDates(driver, alwaysAvailable, testSteps);
			derivedRate.customDateRangeAppear(driver, false, testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select dates", testName, "SelectDates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select dates", testName, "SelectDates", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.selectChannels(driver, channels, true, testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select distribution channel", testName, "SelectDistributionChannel",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select distribution channel", testName, "SelectDistributionChannel",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, roomClassesName, true, testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select  room classes", testName, "SelectRoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select  room classes", testName, "SelectRoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"=================== " + "EDIT DERIVED RATE PLAN FIELDS".toUpperCase() + " ======================");
			app_logs.info("=================== " + "EDIT DERIVED RATE PLAN FIELDS.".toUpperCase()
					+ " ======================");
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Room class", testSteps);
			testSteps.add("=================== EDIT ROOM CLASS ======================");
			app_logs.info("===================  EDIT ROOM CLASS ======================");

			nightlyRate.selectRoomClasses(driver, roomClassesName, false, testSteps);
			nightlyRate.verifySelectedRoomClasses(driver, roomClassesName, false, testSteps);
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Channel", testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan room classes", testName, "EditRoomClasses",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan room classes", testName, "EditRoomClasses",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== EDIT CHANNELS ======================");
			app_logs.info("===================  EDIT CHANNELS ======================");
			nightlyRate.selectChannels(driver, channels, true, testSteps);
			nightlyRate.verifySelectedChannels(driver, channels, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan channels", testName, "EditChannels", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan channels", testName, "EditChannels", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== EDIT DATE ======================");
			app_logs.info("=================== EDIT DATE ======================");
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Derived plan dates", testSteps);
			derivedRate.selectDates(driver, "Custom date range", testSteps);
			derivedRate.customDateRangeAppear(driver, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan custom date range", testName,
						"EditCustomDateRange", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan custom date range", testName,
						"EditCustomDateRange", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== EDIT RATE PLAN NAME ===================");
			app_logs.info("=================== EDIT RATE PLAN NAME ===================");
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Rate plan name", testSteps);
			String updateRatePlanName = "Updated" + derivedRatePlanName;
			nightlyRate.enterRatePlanName(driver, updateRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, updateRatePlanName, true, testSteps);
			derivedRate.clickTab(driver, "Rates Grid", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan name", testName, "EditRateplanName", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to edit derived rate plan name", testName, "EditRateplanName", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Derived rate Plan
		try {

			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
			testSteps.add("=================== " + "DELETE PARENT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info(
					"=================== " + "DELETE DERIVED RATE PLAN".toUpperCase() + " ======================");
			getTestSteps.clear();
			getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, parentRatePlanName, "Delete", getTestSteps);
			testSteps.addAll(getTestSteps);
			// derivedRate.expandReduceDerivedratePlans(driver, true,
			// testSteps);
			// derivedRate.expandReduceDerivedratePlans(driver, true,
			// testSteps);
			// derivedRate.expandReduceDerivedratePlans(driver, true,
			// testSteps);
			//
			// derivedRate.derivedratePlanExist(driver, RatePlanName, true,
			// testSteps);
			// derivedRate.deleteDerivedRatePlan(driver, RatePlanName, "Cancel",
			// testSteps);
			// derivedRate.deleteDerivedRatePlan(driver, RatePlanName, "Delete",
			// testSteps);
			//
			// derivedRate.derivedratePlanExist(driver, RatePlanName, false,
			// testSteps);
			testSteps.add("Successfully Delete Parent rate plan '" + parentRatePlanName + "'");
			app_logs.info("Successfully Delete Parent rate plan '" + parentRatePlanName + "'");

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
			Utility.AddTest_IntoReport(testName + "_DerivedFrom" + parentRatePlanType.replaceAll(" ", ""),
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
		return Utility.getData("ValidateDerivedRateCustomDate", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
