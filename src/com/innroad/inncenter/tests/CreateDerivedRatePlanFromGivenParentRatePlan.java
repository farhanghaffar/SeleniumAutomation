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
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CreateDerivedRatePlanFromGivenParentRatePlan extends TestCore {
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
	public void createDerivedRatePlanFromGivenParentRatePlan(String clientName, String delim, String partOftestName,
			String parentRatePlanName, String derivedRatePlanName, String derivedRatePlanFolioDisplayName, String derivedRatePlanDescription,
			String istakenRulesFromParentRateplan, String dateRange, String customStartDate, String customEndDate,
			String selectComparator, String derivedRateType, String derivedRateValue, String derivedRateRoomClasses,
			String derivedRateChannels, String derivedRateIsRatePlanRistrictionReq, String derivedRateRistrictionType,
			String derivedRateIsMinStay, String derivedRateMinNights, String derivedRateIsMaxStay,
			String derivedRateMaxNights, String derivedRateIsMoreThanDaysReq, String derivedRateMoreThanDaysCount,
			String derivedRateIsWithInDaysReq, String derivedRateWithInDaysCount, String derivedRatePromoCode,
			String derivedRateIsPolicesReq, String derivedRatePoliciesType, String derivedRatePoliciesName,
			String deleteRatePlan) throws InterruptedException, IOException {

		Utility.DELIM = "\\" + delim;


		app_logs.info(" Delim" + delim);

		app_logs.info("Utility.Delim" + Utility.DELIM);


		test_name = "CreateDerivedRatePlanFromGivenParentRatePlan";
		testDescription = "Rates V2 - Create Derived Rate Plan From <br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1801' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1801</a>";
		testCategory = "RateV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		DerivedRate derivedRate = new DerivedRate();
		String timeZone = "America/New_York";
		String randomString = Utility.generateRandomString();
		derivedRatePlanName = derivedRatePlanName + randomString;
		derivedRatePlanFolioDisplayName = derivedRatePlanFolioDisplayName + randomString;
		derivedRatePlanDescription = derivedRatePlanDescription + randomString;

		String selectedDate = null;
		String seasonDuration = "2";
		String customDateRange = "Custom date range";
		String calendarTodayDay = "today";
		String seasonDateFormat = "dd/M/yyyy";
		String calendarSelectedDay = "selected";
		String todayDate = null;
		String currencySign = "";
		String currencyName = "";

		String restrictionsSummary = null;
		

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
				login.login(driver, envURL, "autorate", "autouser", "Auto@123");
				// loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				Login login = new Login();
				login.login(driver, envURL, "autorate", "autouser", "Auto@123");
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
			selectedDate = ratesGrid.getCalendarDate(driver, "selected", seasonDateFormat, getTestSteps);
			if (Utility.compareDates(selectedDate, todayDate, seasonDateFormat) > 0) {
				todayDate = selectedDate;
			}
			app_logs.info("Today date : " + todayDate);
			if (dateRange.equals(customDateRange)) {
				app_logs.info("Excel Sheet custom start Date : " + customStartDate);
				app_logs.info("Excel Sheet custom end Date : " + customEndDate);

				testSteps.add("Excel Sheet custom start Date : " + customStartDate);
				testSteps.add("Excel Sheet custom end Date : " + customEndDate);
			}

		
			if (dateRange.equals(customDateRange)) {
				if (Utility.compareDates(customStartDate.split(Utility.DELIM)[0], todayDate, seasonDateFormat) >= 0) {

				} else {
					customStartDate = todayDate;
					customEndDate = Utility.addDate(Integer.parseInt(seasonDuration), seasonDateFormat, customStartDate,
							seasonDateFormat, timeZone);;
				}

			}

			if (dateRange.equals(customDateRange)) {
				app_logs.info("Selected custom start Date : " + customStartDate);
				app_logs.info("Selected custom end Date : " + customEndDate);
				testSteps.add("Selected custom start Date : " + customStartDate);
				testSteps.add("Selected custom end Date : " + customEndDate);
			}
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
		try {

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

		try {

			testSteps.add("=================== " + "SELECT PARENT " + parentRatePlanName.toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "SELECT PARENT " + parentRatePlanName.toUpperCase()
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
			derivedRate.selectDates(driver, dateRange, testSteps);
			if (dateRange.equals(customDateRange)) {
				derivedRate.customDateRangeAppear(driver, true, testSteps);
				getTestSteps.clear();
				getTestSteps = derivedRate.selectCustomStartAndEndDates(driver, customStartDate, customEndDate,
						seasonDateFormat);
				testSteps.addAll(getTestSteps);
			}
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.selectChannels(driver, derivedRateChannels, true, testSteps);
			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, derivedRateRoomClasses, true, testSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq),
					derivedRateRistrictionType, Boolean.parseBoolean(derivedRateIsMinStay), derivedRateMinNights,
					Boolean.parseBoolean(derivedRateIsMaxStay), derivedRateMaxNights,
					Boolean.parseBoolean(derivedRateIsMoreThanDaysReq), derivedRateMoreThanDaysCount,
					Boolean.parseBoolean(derivedRateIsWithInDaysReq), derivedRateWithInDaysCount, derivedRatePromoCode,
					testSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRestriction(driver, derivedRateRistrictionType,
					Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			if (Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq)) {
				String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(derivedRateRistrictionType,
						Boolean.parseBoolean(derivedRateIsMinStay), derivedRateMinNights,
						Boolean.parseBoolean(derivedRateIsMaxStay), derivedRateMaxNights,
						Boolean.parseBoolean(derivedRateIsMoreThanDaysReq), derivedRateMoreThanDaysCount,
						Boolean.parseBoolean(derivedRateIsWithInDaysReq), derivedRateWithInDaysCount,
						derivedRatePromoCode);

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

			nightlyRate.selectPolicy(driver, derivedRatePoliciesType, derivedRatePoliciesName,
					Boolean.parseBoolean(derivedRateIsPolicesReq), testSteps);
			getTestSteps.clear();
			nightlyRate.verifySelectedPolicy(driver, derivedRatePoliciesType,
					Boolean.parseBoolean(derivedRateIsPolicesReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver,
					derivedRatePoliciesName, Boolean.parseBoolean(derivedRateIsPolicesReq), testSteps);

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

		// Delete Derived rate Plan
		try {
			if (Boolean.parseBoolean(deleteRatePlan)) {
				ratesGrid.clickRatePlanArrow(driver, testSteps);

				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, derivedRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);

				testSteps.add(
						"=================== " + "DELETE Derived RATE PLAN".toUpperCase() + " ======================");
				app_logs.info(
						"=================== " + "DELETE DERIVED RATE PLAN".toUpperCase() + " ======================");
				getTestSteps.clear();
				getTestSteps = nightlyRate.deleteDeriveRatePlan(driver, derivedRatePlanName, "Delete", getTestSteps);
				testSteps.addAll(getTestSteps);

				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);

				derivedRate.derivedratePlanExist(driver, derivedRatePlanName, false, testSteps);
				testSteps.add("Successfully Delete derived rate plan '" + derivedRatePlanName + "'");
				app_logs.info("Successfully Delete derived rate plan '" + derivedRatePlanName + "'");
			}
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
			Utility.AddTest_IntoReport(
					"CreateDerivedRatePlanWith_" + dateRange.replaceAll(" ", "") + "_DerivedFrom"
							+ parentRatePlanName.replaceAll(" ", "") + partOftestName,

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
		return Utility.getData("CreateDerivedRateFromGivenRate", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
