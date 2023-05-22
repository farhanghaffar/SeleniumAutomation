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

public class UpdateDerivedRatePlan extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	public String todayDate = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getUpdateData", priority = 1)
	public void updateDerivedRatePlan(String delim,String derivedRatePlanName, String isDefaultRatePlan, String isActive,
			String updateRatePlanName, String updateFolioDisplayName, String updateRatePlanDescription,
			String updatedIstakenRulesFromParentRateplan, String updatedDateRange, String updatedCustomStartDate,
			String updatedCustomEndDate, String updatedSelectComparator, String updatedDerivedRateType,
			String updatedDerivedRateValue, String updateRoomClass, String updatedSelectedRoomClasses, String updatedNotSelectedRoomClasses,String updateChannel,String updatedDerivedRateChannels,
			String updatedDerivedRateIsRatePlanRistrictionReq, String updatedDerivedRateRistrictionType,
			String updatedDerivedRateIsMinStay, String updatedDerivedRateMinNights, String updatedDerivedRateIsMaxStay,
			String updatedDerivedRateMaxNights, String updatedDerivedRateIsMoreThanDaysReq,
			String updatedDerivedRateMoreThanDaysCount, String updatedDerivedRateIsWithInDaysReq,
			String updatedDerivedRateWithInDaysCount, String updatedDerivedRatePromoCode,
			String updatedDerivedRateIsPolicesReq, String updatedDerivedRatePoliciesType,
			String updatedDerivedRatePoliciesName, String deleteRatePlan) throws InterruptedException, IOException {

		Utility.DELIM = "\\" + delim;

		test_name = "UpdateDerivedRatePlan";
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
		String seasonDuration = "2";
		String customDateRange = "Custom date range";
		String seasonDateFormat = "dd/M/yyyy";

		RatePackage ratePackage = new RatePackage();
		Policies policies = new Policies();
		String existingRateName = "";
		ArrayList<String> ratePlanNames = new ArrayList<String>();
		String randomString = Utility.generateRandomString();

		String restrictionType = "Length of stay,Booking window,Promo code";
		String selectedDate = null;
		String percent = "percent";
		String valueOptions = "greater than,lesser than";
		String calendarTodayDay = "today";
		String calendarSelectedDay = "selected";
		String currencySign = "";
		String currencyName = "";

		String summaryChannels = null;
		String summaryRoomClasses = null;
		String derivedRateValues = "";
		String restrictionsSummary = null;
		boolean isProrateCheckbox = false;
		boolean isIntervalRateplan = false;
		boolean ratePlanExist = false;

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
			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver);
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");
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
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, seasonDateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			selectedDate = ratesGrid.getCalendarDate(driver, "selected", seasonDateFormat, getTestSteps);
			if (Utility.compareDates(selectedDate, todayDate, seasonDateFormat) > 0) {
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

			testSteps.add("=================== SEARCH CREATED DERIVED RATE PLAN ======================");
			app_logs.info("=================== SEARCH CREATED DERIVED RATE PLAN ======================");

			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			if (ratesGrid.searchAndVerifyRatePlanExist(driver, derivedRatePlanName, true, testSteps)) {
				ratePlanExist = true;
				testSteps.add("Successfully verified Derived Rate Plan(" + derivedRatePlanName + ") exist");
				app_logs.info("Successfully verified Derived Rate Plan(" + derivedRatePlanName + ") exist");
			} else {
				testSteps.add("Failed  Derived Rate Plan(" + derivedRatePlanName + ") not exist");
				app_logs.info("Failed  Derived Rate Plan(" + derivedRatePlanName + ") not exist");
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

		
	if(ratePlanExist) {
		try {

			testSteps.add("=================== " + "EDIT DERIVED RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "EDIT DERIVED RATE PLAN".toUpperCase() + " ======================");
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			try{
				derivedRate.derivedratePlanExist(driver, derivedRatePlanName, true, testSteps);
			}catch(Exception e) {
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.derivedratePlanExist(driver, derivedRatePlanName, true, testSteps);
			}
			derivedRate.clickEditIconOfDerivedRatePlan(driver,derivedRatePlanName , testSteps);

			testSteps.add("=================== VERIFY EDIT RATE PLAN INITIAL STATE AND UPDATE ======================");
			app_logs.info("=================== VERIFY EDIT RATE PLAN INITIAL STATE AND UPDATE ======================");

			nightlyRate.verifyRatePlanNameEditPage(driver, derivedRatePlanName, testSteps);
			nightlyRate.verifySaveRatePlanButton(driver, false, true, testSteps);
			nightlyRate.verifyRatePlanTypeEditPage(driver, "Derived rate plan", testSteps);

			testSteps.add("=================== UPDATE RATE PLAN DEFAULT STATE ===================");
			app_logs.info("=================== UPDATE RATE PLAN DEFAULT STATE ===================");
			nightlyRate.selectAsDefaultRatePlan(driver, testSteps, isDefaultRatePlan);
			if (isDefaultRatePlan.equalsIgnoreCase("Yes")) {
				nightlyRate.verifyIsDefaultRatePlan(driver, testSteps, true);
			} else {
				nightlyRate.verifyIsDefaultRatePlan(driver, testSteps, false);
			}
			testSteps.add("=================== UPDATE RATE PLAN STATUS ===================");
			app_logs.info("=================== UPDATE RATE PLAN STATUS ===================");
			boolean alreadySelectedStatus = nightlyRate.getSelectedRatePlanStatus(driver);
			if(Boolean.parseBoolean(isActive) && alreadySelectedStatus) {
				testSteps.add("Staus is already Selected");
				app_logs.info("Staus is already selected");
			}else {
			nightlyRate.ratePlanStatusChange(driver, Boolean.parseBoolean(isActive), testSteps);
			nightlyRate.verifySelectedRatePlanStatus(driver, Boolean.parseBoolean(isActive), testSteps);
			}

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

			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");

			derivedRate.selectDropDownOptions(driver, updatedDerivedRateType, testSteps);

			testSteps.add("===== ENTER VALUE =====");
			app_logs.info("===== ENTER VALUE =====");
			derivedRate.enterRateValue(driver, updatedDerivedRateValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			if (updatedDerivedRateType.equals("percent")) {
				Assert.assertEquals(value, updatedDerivedRateValue + "%", "Failed : Value missmatched");
			} else {
				Assert.assertEquals(value, updatedDerivedRateValue, "Failed : Value missmatched");
			}
			derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand Value Comparison DropDown");
			app_logs.info("Expand Value Comparison DropDown");
			derivedRate.selectDropDownOptions(driver, updatedSelectComparator, testSteps);

			testSteps.add("=================== UPDATE 'TAKE RULE FROM PARENT RATE PLAN' ======================");
			app_logs.info("=================== UPDATE 'TAKE RULE FROM PARENT RATE PLAN' ======================");

			derivedRate.takeRuleFromParentRatePlanCheckBox(driver,
					!Boolean.parseBoolean(updatedIstakenRulesFromParentRateplan), testSteps);

			derivedRate.takeRuleFromParentRatePlanCheckBox(driver,
					Boolean.parseBoolean(updatedIstakenRulesFromParentRateplan), testSteps);

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
			if(Boolean.parseBoolean(updateChannel)) {

			testSteps.add("=================== DISTRIBUTION CHANNEL UPDATE ======================");
			app_logs.info("===================  DISTRIBUTION CHANNEL UPDATE ======================");
			nightlyRate.selectChannelsEditPage(driver, updatedDerivedRateChannels, true, testSteps);
			nightlyRate.verifySelectedChannelsEditPage(driver, updatedDerivedRateChannels, true, testSteps);
			}
			if(Boolean.parseBoolean(updateRoomClass)) {

				testSteps.add("=================== ROOM CLASS UPDATE ======================");
				app_logs.info("===================  ROOM CLASS UPDATE ======================");
			if(!updatedSelectedRoomClasses.equals("")){
				nightlyRate.selectRoomClassesEditPage(driver, updatedSelectedRoomClasses, true, testSteps);
				nightlyRate.verifySelectedRoomClassesEditPage(driver, updatedSelectedRoomClasses, true, testSteps);
			}
			if(!updatedNotSelectedRoomClasses.equals("")){
				nightlyRate.selectRoomClassesEditPage(driver, updatedNotSelectedRoomClasses, false, testSteps);
				nightlyRate.verifySelectedRoomClassesEditPage(driver, updatedNotSelectedRoomClasses, false, testSteps);
			}
			}
			nightlyRate.switchRestrictionAndPoliciesTab(driver, testSteps);

			testSteps.add("=================== RESTRICTION UPDATE ======================");
			app_logs.info("===================  RESTRICTION UPDATE ======================");

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(updatedDerivedRateIsRatePlanRistrictionReq),
					updatedDerivedRateRistrictionType, Boolean.parseBoolean(updatedDerivedRateIsMinStay),
					updatedDerivedRateMinNights, Boolean.parseBoolean(updatedDerivedRateIsMaxStay),
					updatedDerivedRateMaxNights, Boolean.parseBoolean(updatedDerivedRateIsMoreThanDaysReq),
					updatedDerivedRateMoreThanDaysCount, Boolean.parseBoolean(updatedDerivedRateIsWithInDaysReq),
					updatedDerivedRateWithInDaysCount, updatedDerivedRatePromoCode, testSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRestriction(driver, updatedDerivedRateRistrictionType,
					Boolean.parseBoolean(updatedDerivedRateIsRatePlanRistrictionReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			if (Boolean.parseBoolean(updatedDerivedRateIsRatePlanRistrictionReq)) {
				String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(updatedDerivedRateRistrictionType,
						Boolean.parseBoolean(updatedDerivedRateIsMinStay), updatedDerivedRateMinNights,
						Boolean.parseBoolean(updatedDerivedRateIsMaxStay), updatedDerivedRateMaxNights,
						Boolean.parseBoolean(updatedDerivedRateIsMoreThanDaysReq), updatedDerivedRateMoreThanDaysCount,
						Boolean.parseBoolean(updatedDerivedRateIsWithInDaysReq), updatedDerivedRateWithInDaysCount,
						updatedDerivedRatePromoCode);

				getTestSteps.clear();
				nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, getTestSteps);
				testSteps.addAll(getTestSteps);
			}

			nightlyRate.selectPolicy(driver, updatedDerivedRatePoliciesType, updatedDerivedRatePoliciesName,
					Boolean.parseBoolean(updatedDerivedRateIsPolicesReq), testSteps);
			getTestSteps.clear();
			nightlyRate.verifySelectedPolicy(driver, updatedDerivedRatePoliciesType,
					Boolean.parseBoolean(updatedDerivedRateIsPolicesReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver,
					updatedDerivedRatePoliciesName, Boolean.parseBoolean(updatedDerivedRateIsPolicesReq), testSteps);
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
			derivedRate.selectDates(driver, "Always available", testSteps);
			derivedRate.selectDates(driver, "Custom date range", testSteps);
			derivedRate.selectDates(driver, updatedDateRange, testSteps);
			
			if (updatedDateRange.equals(customDateRange)) {
				if (Utility.compareDates(updatedCustomStartDate.split(Utility.DELIM)[0], todayDate,
						seasonDateFormat) >= 0) {

				} else {
					updatedCustomStartDate = todayDate;
					updatedCustomEndDate = Utility.addDate(Integer.parseInt(seasonDuration), seasonDateFormat,
							updatedCustomStartDate, seasonDateFormat, timeZone);
				}
				derivedRate.customDateRangeAppear(driver, true, testSteps);
				getTestSteps.clear();
				getTestSteps = derivedRate.selectCustomStartAndEndDates(driver, updatedCustomStartDate,
						updatedCustomEndDate, seasonDateFormat);
				testSteps.addAll(getTestSteps);
			}

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
			testSteps.add("=================== SAVE UPDATED DERIVED RATE PLAN ======================");
			app_logs.info("=================== SAVE UPDATED DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("Updated rate successfully");
			app_logs.info("Updated rate successfully");
			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
			try {
				derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", getTestSteps);
				ratesGrid.clickOnSaveratePlan(driver);
				derivedRate.closeOpenedRatePlanTab(driver, getTestSteps);
			} catch (Exception f) {

			}
			try {
				navigation.Rates_Grid(driver);
			} catch (Exception e) {
				derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", getTestSteps);
				ratesGrid.clickOnSaveratePlan(driver);
				derivedRate.closeOpenedRatePlanTab(driver, getTestSteps);
			}
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
			if (Boolean.parseBoolean(deleteRatePlan)) {
				ratesGrid.clickRatePlanArrow(driver, testSteps);

				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, updateRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				try{
					derivedRate.derivedratePlanExist(driver, derivedRatePlanName, true, testSteps);
				}catch(Exception e) {
					derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
					derivedRate.derivedratePlanExist(driver, derivedRatePlanName, true, testSteps);
				}
				testSteps.add(
						"=================== " + "DELETE Derived RATE PLAN".toUpperCase() + " ======================");
				app_logs.info(
						"=================== " + "DELETE DERIVED RATE PLAN".toUpperCase() + " ======================");
				getTestSteps.clear();
				getTestSteps = nightlyRate.deleteDeriveRatePlan(driver, updateRatePlanName, "Delete", getTestSteps);
				testSteps.addAll(getTestSteps);

				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);

				derivedRate.derivedratePlanExist(driver, updateRatePlanName, false, testSteps);
				testSteps.add("Successfully Delete derived rate plan '" + updateRatePlanName + "'");
				app_logs.info("Successfully Delete derived rate plan '" + updateRatePlanName + "'");
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
	}

		// Generate Report
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName,
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
	public Object[][] getUpdateData() {
		// return test data from the sheetname provided
		return Utility.getData("UpdateDerivedRatePlan", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
