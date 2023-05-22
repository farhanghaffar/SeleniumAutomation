package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class UpdatePackageRatePlanUsingIntervalRateV2 extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	String testName = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	String todayDate = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", priority = 0)
	public void verifyPackageRatePlanExist(String delim, String packageRatePlanName, String folioDisplayName,
			String description, String channels, String roomClasses, String isRatePlanRistrictionReq,
			String ristrictionType, String isMinStay, String minNights, String isMaxStay, String maxNights,
			String isMoreThanDaysReq, String moreThanDaysCount, String isWithInDaysReq, String withInDaysCount,
			String promoCode, String isPolicesReq, String policiesType, String policiesName, String productName,
			String productAmount, String productFirstCalculationMethod, String productSecondCalculationMethod,
			String rateType, String intervalRatePlanIntervalValue, String isDefaultProRateChecked,
			String isProRateStayInSeason,

			String seasonDelim, String seasonName, String seasonStartDate, String seasonEndDate, String isMonDay,
			String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason,
			String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults,
			String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight,
			String extraRoomClassAdditionalChildPerNight, String isProRateInRoomClass, String isCustomPerNight,
			String customRoomClasses, String customRatePerNight, String isCustomRatePerNightAdultandChild,
			String customRateChildPerNight, String customRateAdultdPerNight, String roomClassesWithProRateUnchecked,
			String isSeasonLevelRules, String isAssignRulesByRoomClass, String seasonRuleSpecificRoomClasses,
			String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday,
			String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday,
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String isSeasonPolicies, String isAssignPoliciesByRoomClass, String seasonPolicySpecificRoomClasses,
			String seasonPolicyType, String seasonPolicyValues)
			throws InterruptedException, IOException, ParseException {

		Utility.DELIM =  delim;
		Utility.SEASONDELIM =  "\\"+seasonDelim;

		test_name = "VerifyPackageRatePlanExist";
		testDescription = "Rates V2 - Create/Update/Delete Package Rate Plan of Interval Rate<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1807' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1807</a>";
		testCategory = "VerifyPackageRatePlanExist";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		RatePackage ratePackage = new RatePackage();

		String randomString = Utility.generateRandomString();
		String timeZone = "America/New_York";
		String calendarTodayDay = "today";
		String seasonDuration = "2";
		String parentSeasonDateFormat = "dd/M/yyyy";
		boolean ratePlanExist = false;
		String[] productArray = productName.split(Utility.DELIM);
		for (String s : productArray) {
			app_logs.info(" product : " + s);

		}
//		app_logs.info(" product : " + productArray.length + productArray[0]);

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			Login login = new Login();

			try {
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
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
			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, testSteps);
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Start Date

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, parentSeasonDateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", parentSeasonDateFormat, getTestSteps);
			if (Utility.compareDates(selectedDate, todayDate, parentSeasonDateFormat) > 0) {
				todayDate = selectedDate;
			} 
			
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

		try {

			testSteps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
			app_logs.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			if (ratesGrid.searchAndVerifyRatePlanExist(driver, packageRatePlanName, true, testSteps)) {
				ratePlanExist = true;
				testSteps.add("Successfully verified Package Rate Plan(" + packageRatePlanName + ") exist");
				app_logs.info("Successfully verified Package Rate Plan(" + packageRatePlanName + ") exist");
			} else {
				testSteps.add("Successfully verified Package Rate Plan(" + packageRatePlanName + ") not exist");
				app_logs.info("Successfully verified Package Rate Plan(" + packageRatePlanName + ") not exist");
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search created plan", testName, "SearchPackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search created plan", testName, "SearchPackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			if (!ratePlanExist) {
				app_logs.info("Today date : " + todayDate);
				app_logs.info("Excel Sheet  Season Start Date: " + seasonStartDate);
				app_logs.info("Excel Sheet  Season End Date: " + seasonEndDate);

				testSteps.add("Excel Sheet  Season Start Date: " + seasonStartDate);
				testSteps.add("Excel Sheet  Season End Date: " + seasonEndDate);

				if (Utility.compareDates(seasonStartDate.split(Utility.DELIM)[0], todayDate,
						parentSeasonDateFormat) >= 0) {

				} else {
					seasonStartDate = todayDate;
					seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), parentSeasonDateFormat,
							seasonStartDate, parentSeasonDateFormat, timeZone);
				}
				int days = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate);
				seasonDuration = "" + days + "";
				app_logs.info("Season Duration : " + seasonDuration);
				app_logs.info("Selected Season Start Date: " + seasonStartDate);
				app_logs.info("Selected Season End Date: " + seasonEndDate);
				testSteps.add("Selected Season Start Date: " + seasonStartDate);
				testSteps.add("Selected Season End Date: " + seasonEndDate);
				testSteps
						.add("=================== CREATE PACKAGE RATE PLAN USING SINGLE METHOD ======================");
				app_logs.info(
						"=================== CREATE PACKAGE RATE PLAN USING SINGLE METHOD ======================");

				getTestSteps.clear();
				getTestSteps = ratePackage.createPackage(driver, packageRatePlanName, folioDisplayName, description,
						rateType, intervalRatePlanIntervalValue, isDefaultProRateChecked, productName, productAmount,
						productFirstCalculationMethod, productSecondCalculationMethod, channels, roomClasses,
						isRatePlanRistrictionReq, ristrictionType, isMinStay, minNights, isMaxStay, maxNights,
						isMoreThanDaysReq, moreThanDaysCount, isWithInDaysReq, withInDaysCount, promoCode, policiesType,
						policiesName, isPolicesReq, seasonStartDate, seasonEndDate, seasonName, isMonDay, isTueDay,
						isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults,
						ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight, additionalChildPerNight,
						isAddRoomClassInSeason, extraRoomClassesInSeason, extraRoomClassRatePerNight,
						extraRoomClassMaxAdults, extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
						extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSeasonLevelRules,
						seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, seasonPolicyType, seasonPolicyValues,
						isSeasonPolicies, isProRateStayInSeason, isProRateInRoomClass, roomClassesWithProRateUnchecked,
						isCustomPerNight, customRoomClasses, customRatePerNight, isAssignPoliciesByRoomClass,
						customRateAdultdPerNight, customRateChildPerNight, isCustomRatePerNightAdultandChild,
						seasonPolicySpecificRoomClasses);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create package rate plan using single method", testName,
						"CreatePackage", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create package rate plan using single method", testName,
						"CreatePackage", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
			app_logs.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

			ratesGrid.clickRatePlanArrow(driver, getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.searchRatePlan(driver, packageRatePlanName);
			testSteps.addAll(getTestSteps);

			String getRatPlanName = ratesGrid.selectedRatePlan(driver);

			app_logs.info("getRatPlanName: " + getRatPlanName);

			testSteps.add("Successfully verified Created Rate Plan");
			app_logs.info("Successfully verified Created Rate Plan");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search and verify created plan", testName, "SearchPackagePlan",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search and verify created plan", testName, "SearchPackagePlan",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	} 

	@Test(dataProvider = "getUpdateData", priority = 1)
	public void updatePackageRatePlanUsingIntervalRatesV2(String delim, String updatedRatePlanName,
			String updatedFolioDisplayName, String updatedDescription, String isExisingProductRemove,
			String productName, String isAddMoreProduct, String newProductName, String newProductAmount,
			String calculationMethodOne, String calculationMethodTwo,String roomClasses,String intervalRatePlanIntervalValue,
			String isDefaultProRateChecked,
			String isProRateStayInSeason, String seasonDuration,String seasonDelim, String seasonName,
			String seasonStartDate, String seasonEndDate, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason,
			String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults,
			String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight,
			String extraRoomClassAdditionalChildPerNight, String isProRateInRoomClass, String isCustomPerNight,
			String customRoomClasses, String customRatePerNight, String isCustomRatePerNightAdultandChild,
			String customRateChildPerNight, String customRateAdultdPerNight, String roomClassesWithProRateUnchecked,
			String isSeasonLevelRules, String isAssignRulesByRoomClass, String seasonRuleSpecificRoomClasses,
			String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday,
			String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday,
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String isSeasonPolicies, String isAssignPoliciesByRoomClass, String seasonPolicySpecificRoomClasses,
			String seasonPolicyType, String seasonPolicyValues
			) throws InterruptedException, IOException, ParseException {

		Utility.DELIM = delim;
		Utility.SEASONDELIM =  "\\"+seasonDelim;

		test_name = "UpdatePackageRatePlanUsingIntervalRatesV2";
		testDescription = "Rates V2 - Create/Update/Delete Package Rate Plan of Interval Rate<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1807' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1807</a>";
		testCategory = "UpdatePackageRatePlanUsingIntervalRatesV2";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RatePackage ratePackage = new RatePackage();
		DerivedRate derivedRate = new DerivedRate();

		String randomString = Utility.generateRandomString();
		updatedRatePlanName = updatedRatePlanName + randomString;
		updatedFolioDisplayName = updatedFolioDisplayName + randomString;
		String timeZone = "America/New_York";
		String parentSeasonDateFormat = "dd/M/yyyy";

		try {

			testSteps.add("=================== UPDATING PACKAGE RATE PLAN ======================");
			app_logs.info("=================== UPDATING PACKAGE RATE PLAN ======================");

			ratesGrid.clickOnEditRatePlan(driver);
			testSteps.add("Click on edit rate plan");
			app_logs.info("Click on edit rate plan");
			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.ratePlanOverView(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception f) {
				derivedRate.closeOpenedRatePlanTab(driver, testSteps);
				ratesGrid.clickOnEditRatePlan(driver);
				app_logs.info("Click on edit rate plan");
				getTestSteps.clear();
				getTestSteps = ratesGrid.ratePlanOverView(driver);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on edit rate plan", testName, "UpdatePackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on edit rate plan", testName, "UpdatePackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"=================== UPDATE PACKAGE RATE PLAN NAME, FOLIO NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== UPDATE PACKAGE RATE PLAN NAME, FOLIO NAME AND DESCRIPTION ======================");

			nightlyRate.enterRatePlanName(driver, updatedRatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, updatedFolioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, updatedDescription, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update rate plan name, folio name and description", testName,
						"UpdatePackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update rate plan name, folio name and description", testName,
						"UpdatePackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== UPDATE PRODUCTS IN PACKAGE RATE PLAN ======================");
			app_logs.info("=================== UPDATE PRODUCTS IN PACKAGE RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.clickPackageTab(driver, "Products");
			testSteps.addAll(getTestSteps);

			if (Boolean.parseBoolean(isExisingProductRemove)) {

				String[] existingProductArray = productName.split(Utility.DELIM);

				for (String product : existingProductArray) {
					getTestSteps.clear();
					getTestSteps = ratePackage.clickProduct(driver, product, false);
					testSteps.addAll(getTestSteps);

				}

			}

			if (Boolean.parseBoolean(isAddMoreProduct)) {

				getTestSteps.clear();
				getTestSteps = ratePackage.addProducts(driver, newProductName, newProductAmount, calculationMethodOne,
						calculationMethodTwo);
				testSteps.addAll(getTestSteps);

			}

			getTestSteps.clear();
			getTestSteps = ratePackage.clickPackageTab(driver, "Rate plan overview");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify products in package rate plan", testName, "VerifyProducts",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify products in package rate plan", testName, "VerifyProducts",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== UPDATE SEASON ======================");
			app_logs.info("=================== UPDATE SEASON ======================");
			if (Utility.compareDates(seasonStartDate.split(Utility.DELIM)[0], todayDate, parentSeasonDateFormat) >= 0) {

			} else {
				seasonStartDate = todayDate;
				seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), parentSeasonDateFormat,
						seasonStartDate, parentSeasonDateFormat, timeZone);
			}
			int days = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate);
			seasonDuration = "" + days + "";
			app_logs.info("Season Duration : " + seasonDuration);
			app_logs.info("Selected Season Start Date: " + seasonStartDate);
			app_logs.info("Selected Season End Date: " + seasonEndDate);
			ratesGrid.clickOnSeasonTab(driver);
			//nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.createUpdateSeasonForPackageIntervalRatePlan(driver, testSteps, seasonStartDate, seasonEndDate,
					seasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
					roomClasses, isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
					additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason,
					extraRoomClassRatePerNight, extraRoomClassMaxAdults, extraRoomClassMaxPersons,
					extraRoomClassAdditionalAdultsPerNight, extraRoomClassAdditionalChildPerNight,
					isAssignRulesByRoomClass, isSeasonLevelRules, seasonRuleSpecificRoomClasses, seasonRuleType,
					seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday,
					isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday,
					isSeasonPolicies, seasonPolicyType, seasonPolicyValues, isDefaultProRateChecked,
					isProRateStayInSeason, isProRateInRoomClass, roomClassesWithProRateUnchecked, isCustomPerNight,
					customRoomClasses, customRatePerNight, isAssignPoliciesByRoomClass, customRateAdultdPerNight,
					customRateChildPerNight, isCustomRatePerNightAdultandChild, seasonPolicySpecificRoomClasses, true,
					intervalRatePlanIntervalValue);

			

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season in updated package rate", testName,
						"SeasonUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season in updated package rate", testName,
						"SeasonUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SAVE UPDATED PAKCAGE RATE PLAN ======================");
			app_logs.info("=================== SAVE UPDATED PAKCAGE RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickSaveratePlan(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("Updated rate successfully");
			app_logs.info("Updated rate successfully");
			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
			
			try {
				navigation.Rates_Grid(driver);
			} catch (Exception e) {
				derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", getTestSteps);
				Wait.wait5Second();
				ratesGrid.clickSaveratePlan(driver);
				derivedRate.closeOpenedRatePlanTab(driver, getTestSteps);
				navigation.Rates_Grid(driver);
			}
			try {
				navigation.Rates_Grid(driver);
			} catch (Exception e) {
				derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", getTestSteps);
				Wait.wait5Second();
				ratesGrid.clickSaveratePlan(driver);
				derivedRate.closeOpenedRatePlanTab(driver, getTestSteps);
				navigation.Rates_Grid(driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to save updated package rate plan", testName, "SaveUpdatedPackageRate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to save updated package rate plan", testName, "SaveUpdatedPackageRate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Derived rate Plan
		try {

			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, updatedRatePlanName);
			testSteps.add("=================== " + "DELETE PARENT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "DELETE parent RATE PLAN".toUpperCase() + " ======================");
			getTestSteps.clear();
			getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, updatedRatePlanName, "Delete", getTestSteps);
			testSteps.addAll(getTestSteps);
			testSteps.add("Successfully Delete Parent rate plan '" + updatedRatePlanName + "'");
			app_logs.info("Successfully Delete Parent rate plan '" + updatedRatePlanName + "'");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete package rate plan", testName, "PackageRatePlanDeletion",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete package rate plan", testName, "PackageRatePlanDeletion",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreatePackageRatePlanV2Interval", envLoginExcel);
	}

	@DataProvider
	public Object[][] getUpdateData() {
		return Utility.getData("UpdatePackageRatePlanIntervalV2", envLoginExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
