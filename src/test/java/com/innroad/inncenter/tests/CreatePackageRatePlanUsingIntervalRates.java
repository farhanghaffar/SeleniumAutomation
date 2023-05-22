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

import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreatePackageRatePlanUsingIntervalRates extends TestCore {

	// AUTOMATION-1745
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	String testName = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test
	public void createPackageRatePlanUsingIntervalRates() throws InterruptedException, IOException, ParseException {

		String delim = ",";
		String packageRatePlanName = "PackageRatePlanName";
		String folioDisplayName = "Folio Package";
		String description = "Description";
		String channels = "innCenter";
		String roomClassSize = "1";
		String roomClassesName = null;
		String rateType = "Interval rates";
		String intervalRatePlanIntervalValue = "3";
		String isDefaultProRateChecked = "TRUE";
		String isProRateStayInSeason = "TRUE";
		String isRatePlanRistrictionReq = "false";
		String ristrictionType = "Length of stay,Booking window,Promo code";
		String isMinStay = "true";
		String minNights = "2";
		String isMaxStay = "true";
		String maxNights = "2";
		String isMoreThanDaysReq = "true";
		String moreThanDaysCount = "2";
		String isWithInDaysReq = "true";
		String withInDaysCount = "2";
		String promoCode = "abc123";
		String isPolicesReq = "true";
		String policiesType = null;
		String policiesName = null;
		String seasonName = "Package Season";
		String seasonStartDate = null;
		String seasonEndDate = null;
		String isMonDay = "yes";
		String isTueDay = "yes";
		String isWednesDay = "yes";
		String isThursDay = "yes";
		String isFriday = "yes";
		String isSaturDay = "yes";
		String isSunDay = "yes";
		String isAdditionalChargesForChildrenAdults = "No";
		String ratePerNight = "100.25";
		String maxAdults = "2,3";
		String maxPersons = "3,2";
		String additionalAdultsPerNight = "3,4";
		String additionalChildPerNight = "3,2";
		String isAddRoomClassInSeason = "No";
		String extraRoomClassesInSeason = null;
		String extraRoomClassRatePerNight = "300";
		String extraRoomClassMaxAdults = "3";
		String extraRoomClassMaxPersons = "4";
		String extraRoomClassAdditionalAdultsPerNight = "2";
		String extraRoomClassAdditionalChildPerNight = "1";
		String isSeasonLevelRules = "yes";
		String isAssignRulesByRoomClass = "no";
		String seasonRuleSpecificRoomClasses = "";
		String seasonRuleType = "Min nights,No check-in";
		String seasonRuleMinStayValue = "3";
		String isSeasonRuleOnMonday = "true";
		String isSeasonRuleOnTuesday = "true";
		String isSeasonRuleOnWednesday = "true";
		String isSeasonRuleOnThursday = "true";
		String isSeasonRuleOnFriday = "true";
		String isSeasonRuleOnSaturday = "true";
		String isSeasonRuleOnSunday = "true";
		String seasonPolicyType = null;
		String seasonPolicyValues = null;
		String isSeasonPolicies = "true";
		String productName = null;
		String productAmount = "50";
		String productFirstCalculationMethod = "child";
		String productSecondCalculationMethod = "stay";
		String seasonDelim = "|";

		String isProRateInRoomClass = "FALSE";
		String isCustomPerNight = "TRUE";
		String customRoomClasses = "";
		String customRatePerNight = "30";
		String isCustomRatePerNightAdultandChild = "TRUE";
		String customRateChildPerNight = "10";
		String customRateAdultdPerNight = "20";
		String roomClassesWithProRateUnchecked = "";
		String isAssignPoliciesByRoomClass = "no";
		String seasonPolicySpecificRoomClasses = "";

		Utility.DELIM = delim;
		Utility.SEASONDELIM =  "\\"+seasonDelim;
		String productDetailsDELIM = Utility.DELIM + Utility.DELIM;

		test_name = "CreatePackageRatePlanUsingIntervalRates";
		testDescription = "Rates V2 - Create/Update/Delete Package Rate Plan of Interval Rate<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1807' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1807</a>";
		testCategory = "CreatePackageRatePlanUsingIntervalRates";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RatePackage ratePackage = new RatePackage();
		Policies policies = new Policies();

		DerivedRate derivedRate = new DerivedRate();

		String randomString = Utility.generateRandomString();
		packageRatePlanName = packageRatePlanName + randomString;
		folioDisplayName = folioDisplayName + randomString;
		String timeZone = "America/New_York";
		String dateFormat = "MM/dd/yyyy";
		String calendarTodayDay = "today";
		String seasonDuration = "2";
		String rateTypeSummary = null;
		String parentSeasonDateFormat = "dd/M/yyyy";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();

			try {
				// login.login(driver, envURL, "autorates", "autouser", "Auto@123");
				loginRateV2(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				// login.login(driver, envURL, "autorates", "autouser", "Auto@123");
				loginRateV2(driver);

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

		ArrayList<String> allCancelationPolicies = new ArrayList<String>();
		ArrayList<String> allDepositPolicies = new ArrayList<String>();
		ArrayList<String> allCheckInPolicies = new ArrayList<String>();
		ArrayList<String> allNoShowPolicies = new ArrayList<String>();
		try {
			testSteps.add("=================== GET ALL POLICIES ======================");
			app_logs.info("=================== GET ALL POLICIES ======================");

			navigation.Inventory(driver, testSteps);
			navigation.policies(driver, testSteps);
			allCancelationPolicies = policies.getCancelationPolicies(driver);
			testSteps.add("Found Cancelation Policies : " + allCancelationPolicies.size());
			app_logs.info("Found Cancelation Policies : " + allCancelationPolicies.size());
			allDepositPolicies = policies.getDepositPolicies(driver);
			testSteps.add("Found Deposit Policies : " + allDepositPolicies.size());
			app_logs.info("Found Deposit Policies : " + allDepositPolicies.size());
			allCheckInPolicies = policies.getCheckInPolicies(driver);
			testSteps.add("Found CheckIn Policies : " + allCheckInPolicies.size());
			app_logs.info("Found CheckIn Policies : " + allCheckInPolicies.size());
			allNoShowPolicies = policies.getNoShowPolicies(driver);
			testSteps.add("Found NoShow Policies : " + allNoShowPolicies.size());
			app_logs.info("Found NoShow Policies : " + allNoShowPolicies.size());
			if (allCancelationPolicies.size() != 0) {
				policiesType = "Cancellation";
				policiesName = allCancelationPolicies.get(0);

			}
			if (allDepositPolicies.size() != 0) {
				policiesType = policiesType + ",Deposit";
				policiesName = policiesName + "," + allDepositPolicies.get(0);
			}

			if (allCheckInPolicies.size() != 0) {
				policiesType = policiesType + ",Check-in";
				policiesName = policiesName + "," + allCheckInPolicies.get(0);
			}
			if (allNoShowPolicies.size() != 0) {
				policiesType = policiesType + ",No Show";
				policiesName = policiesName + "," + allNoShowPolicies.get(0);
			}
			seasonPolicyType = policiesType;
			seasonPolicyValues = policiesName;
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all policies", testName, "GetPolicies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all policies", testName, "GetPolicies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Start Date

		try {

			navigation.Rates_Grid(driver);
			testSteps.add("Navigated to RatesGrid");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			String todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, parentSeasonDateFormat,
					getTestSteps);
			testSteps.addAll(getTestSteps);

			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", parentSeasonDateFormat, getTestSteps);
			if (Utility.compareDates(selectedDate, todayDate, parentSeasonDateFormat) > 0) {
				seasonStartDate = selectedDate;
			} else {
				seasonStartDate = todayDate;
			}

			seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), parentSeasonDateFormat, seasonStartDate,
					parentSeasonDateFormat, timeZone);

			int days = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate);
			seasonDuration = "" + days + "";
			app_logs.info("Season Duration : " + seasonDuration);
			app_logs.info("Selected Season Start Date: " + seasonStartDate);
			app_logs.info("Selected Season End Date: " + seasonEndDate);
			testSteps.add("Selected Season Start Date: " + seasonStartDate);
			testSteps.add("Selected Season End Date: " + seasonEndDate);

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
			testSteps.add("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
			app_logs.info("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");

			derivedRate.clickTab(driver, "Products & Bundles", testSteps);
			testSteps.add("Navigated to products and bundles");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  navigate to products and bundles", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"=================== GET FIRST PRODUCT DEFAULT PRICE AND CALCULATION METHOD ======================");
			app_logs.info(
					"=================== GET FIRST PRODUCT DEFAULT PRICE AND CALCULATION METHOD ======================");

			String productWithDetails = ratePackage.getProductDetails(driver, null, productDetailsDELIM);
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[0] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[1] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[2] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[3] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[4] + "'");
			productName = productWithDetails.split(productDetailsDELIM)[1];
			testSteps.add("Successfully Get Product :  " + productWithDetails.split(productDetailsDELIM)[1]);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get default product price and calculation method", testName,
						"GetProducts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get default product price and calculation method", testName,
						"GetProducts", driver);
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

		try {

			testSteps.add("=================== CREATE NEW PACKAGE RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW PACKAGE RATE PLAN ======================");

			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Package rate plan");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Package rate plan", testSteps);

			testSteps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			nightlyRate.enterRatePlanName(driver, packageRatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, description, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);
			getTestSteps.clear();
			getTestSteps = ratePackage.selectParentRatePlan(driver, rateType);
			testSteps.addAll(getTestSteps);
			if (rateType.equalsIgnoreCase("Interval rates")) {

				getTestSteps.clear();
				getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver,
						Boolean.parseBoolean(isDefaultProRateChecked));
				testSteps.addAll(getTestSteps);
				rateTypeSummary = rateType + "; Interval Length - " + intervalRatePlanIntervalValue + " nights;";
			} else {
				rateTypeSummary = rateType;
			}
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", packageRatePlanName, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter package rate plan name", testName, "CreatePackageRate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter package rate plan name", testName, "CreatePackageRate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratePackage.addProducts(driver, productName, productAmount, productFirstCalculationMethod,
					productSecondCalculationMethod);
			testSteps.addAll(getTestSteps);
			nightlyRate.clickNextButton(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select products in package rate plan", testName, "SelectProducts",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select products in package rate plan", testName, "SelectProducts",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, channels, true, testSteps);
			String summaryChannels = channels;
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			ArrayList<String> roomClasses = new ArrayList<String>();
			roomClasses = nightlyRate.getAllRoomClassesNames(driver);
			app_logs.info("Room Classes : " + roomClasses);
			roomClassesName = "";
			for (int i = 0; i < Integer.parseInt(roomClassSize); i++) {
				if (i < roomClasses.size() && i < 6) {
					if (i != 0) {
						roomClassesName = roomClassesName + delim;
					}
					roomClassesName = roomClassesName + roomClasses.get(i);
				}
			}
			if (roomClasses.size() > Integer.parseInt(roomClassSize) + 1) {
				extraRoomClassesInSeason = roomClasses.get(Integer.parseInt(roomClassSize) + 1);
				isAddRoomClassInSeason = "Yes";
			} else {
				extraRoomClassesInSeason = "";
				isAddRoomClassInSeason = "No";
			}
			customRoomClasses = roomClassesName;
			app_logs.info(" Room Clas : " + roomClassesName);
			app_logs.info("Extra Room Clas : " + extraRoomClassesInSeason);
			nightlyRate.selectRoomClasses(driver, roomClassesName, true, testSteps);
			String summaryRoomClasses = null;
			if (roomClassesName.equals("All")) {
				summaryRoomClasses = "All room classes selected";
			} else {
				summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			}
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
					Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					withInDaysCount, promoCode, testSteps);

			String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);

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
				Utility.updateReport(e, "Failed to select policies during package rate plan creation", testName,
						"SelectPolicies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select policies during package rate plan creation", testName,
						"SelectPolicies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.createUpdateSeasonForPackageIntervalRatePlan(driver, testSteps, seasonStartDate, seasonEndDate,
					seasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
					roomClassesName, isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
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
			nightlyRate.clickCompleteChanges(driver, testSteps);

			try {
				nightlyRate.clickSaveAsActive(driver, testSteps);
			} catch (Exception f) {
				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.clickSaveAsActive(driver, testSteps);
			}
			Utility.rateplanName = packageRatePlanName;

			testSteps.add("=================== RATE PLAN CREATED ======================");
			app_logs.info("=================== RATE PLAN CREATED ======================");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create package rate plan", testName, "CreatePackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create package rate plan", testName, "CreatePackagePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
			app_logs.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			ratesGrid.searchRatePlan(driver, Utility.rateplanName);

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

		// Delete Derived rate Plan
		try {

			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, packageRatePlanName);
			testSteps
					.add("=================== " + "DELETE PACKAGE RATE PLAN".toUpperCase() + " ======================");
			app_logs.info(
					"=================== " + "DELETE PACKAGE RATE PLAN".toUpperCase() + " ======================");
			getTestSteps.clear();
			getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, packageRatePlanName, "Delete", getTestSteps);
			testSteps.addAll(getTestSteps);
			testSteps.add("Successfully deleted package rate plan '" + packageRatePlanName + "'");
			app_logs.info("Successfully deleted package rate plan '" + packageRatePlanName + "'");

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
				Utility.updateReport(e, "Failed to add steps into report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps into report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
