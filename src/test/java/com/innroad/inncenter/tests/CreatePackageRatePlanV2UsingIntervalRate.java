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
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreatePackageRatePlanV2UsingIntervalRate extends TestCore {
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

	@Test(dataProvider = "getData")
	public void createPackageRatePlanV2UsingIntervalRate(String delim,String scenario, String packageRatePlanName,
			String folioDisplayName, String description, String channels, String roomClasses,
			String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights,
			String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
			String isWithInDaysReq, String withInDaysCount, String promoCode, String isPolicesReq, String policiesType,
			String policiesName, String productName, String productAmount, String productFirstCalculationMethod,
			String productSecondCalculationMethod, String rateType, String intervalRatePlanIntervalValue,
			String isDefaultProRateChecked, String isProRateStayInSeason, String seasonDelim, String seasonName,
			String seasonStartDate, String seasonEndDate,	String seasonDuration , String isMonDay, String isTueDay, String isWednesDay,
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
			String seasonPolicyType, String seasonPolicyValues,
			String isCreateProducts, String productsName, String productsCategory,
			String productsCost,
			String calculationMethodOne, String calculationMethodTwo, String isSellOnBookingEngine,
			String bookingEngineAvailabilityOption, String productsRoomClass, String productsDescription,  String productsPolicy)
			throws InterruptedException, IOException, ParseException {

		Utility.DELIM = delim;
		Utility.SEASONDELIM = "\\"+seasonDelim;
		app_logs.info("isRatePlanRistrictionReq : " + isRatePlanRistrictionReq);
		app_logs.info("isRatePlanRistrictionReq : " + Boolean.parseBoolean(isRatePlanRistrictionReq));
		

		test_name = "CreatePackageRatePlanV2UsingIntervalRate";
		testDescription = "Rates V2 - Create/Update/Delete Package Rate Plan of Interval Rate<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1807' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1807</a>";
		testCategory = "CreatePackageRatePlanV2UsingIntervalRate";
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
		packageRatePlanName = packageRatePlanName + randomString;
		folioDisplayName = folioDisplayName + randomString;
		String timeZone = "America/New_York";
		String dateFormat = "MM/dd/yyyy";
		String calendarTodayDay = "today";
	
		String rateTypeSummary = null;
		String parentSeasonDateFormat = "dd/M/yyyy";
		String productStartDate = seasonStartDate.split(Utility.SEASONDELIM)[0];
		String productEndDate = seasonEndDate.split(Utility.SEASONDELIM)[seasonEndDate.split(Utility.SEASONDELIM).length-1];

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

		// Verify Start Date

		try {
			navigation.Inventory(driver, testSteps);
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			String todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, parentSeasonDateFormat,
					getTestSteps);
			testSteps.addAll(getTestSteps);

			app_logs.info("Today date : " + todayDate);
			app_logs.info("Excel Sheet  Season Start Date: " + seasonStartDate);
			app_logs.info("Excel Sheet  Season End Date: " + seasonEndDate);

			testSteps.add("Excel Sheet  Season Start Date: " + seasonStartDate);
			testSteps.add("Excel Sheet  Season End Date: " + seasonEndDate);

			app_logs.info("Initially Product Start Date: " + productStartDate);
			app_logs.info("Initially Product End Date: " +  productEndDate);
			testSteps.add("Initially Product Start Date: " +  productStartDate);
			testSteps.add("Initially Product End Date: " +  productEndDate);

			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", parentSeasonDateFormat, getTestSteps);
			if (Utility.compareDates(selectedDate, todayDate, parentSeasonDateFormat) > 0) {
				todayDate = selectedDate;
			} 
			
			if (Utility.compareDates(seasonStartDate.split(Utility.DELIM)[0], todayDate, parentSeasonDateFormat) >= 0) {

			} else {
				seasonStartDate = todayDate;
				seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), parentSeasonDateFormat,
						seasonStartDate, parentSeasonDateFormat, timeZone);
				productStartDate = seasonStartDate;
				productEndDate = seasonEndDate;
			}
			int days = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate);
			seasonDuration = "" + days + "";
			app_logs.info("Season Duration : " + seasonDuration);
			app_logs.info("Selected Season Start Date: " + seasonStartDate);
			app_logs.info("Selected Season End Date: " + seasonEndDate);
			testSteps.add("Selected Season Start Date: " + seasonStartDate);
			testSteps.add("Selected Season End Date: " + seasonEndDate);
			app_logs.info("Selected Product Start Date: " + productStartDate);
			app_logs.info("Selected Product End Date: " +  productEndDate);
			testSteps.add("Selected Product Start Date: " +  productStartDate);
			testSteps.add("Selected Product End Date: " +  productEndDate);

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
			if(Boolean.parseBoolean(isCreateProducts)) {
				String productsStartDate = null;
				String productsEndDate = null;
				for(int i = 0 ; i < productsName.split(Utility.DELIM).length ; i++) {
					if(i==0) {
						productsStartDate = productStartDate;
						productsEndDate = productEndDate;
					}else {
						productsStartDate = productsStartDate + Utility.DELIM + productStartDate;
						productsEndDate = productsEndDate + Utility.DELIM+ productEndDate;
					}
				}
				testSteps.add("=================== CREATE PRODUCTS ======================");
				app_logs.info("=================== CREATE PRODUCTS ======================");
								
				getTestSteps.clear();
				getTestSteps = ratePackage.createProducts(driver, productsName, isSellOnBookingEngine,
						bookingEngineAvailabilityOption, productsRoomClass ,productsDescription,
						productsCost, productsPolicy, productsCategory, calculationMethodOne, 
						calculationMethodTwo, productsStartDate, productsEndDate);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("Created product successfully");
				app_logs.info("Created product successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create product ", testName, "CreateProducts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create product ", testName, "CreateProducts", driver);
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
			// nightlyRate.generateTitleSummaryValueForChannels(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");

			nightlyRate.selectRoomClasses(driver, roomClasses, true, testSteps);
			String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
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
			nightlyRate.clickCreateSeason(driver, testSteps);
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
					customRateChildPerNight, isCustomRatePerNightAdultandChild, seasonPolicySpecificRoomClasses, false,
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
			Utility.AddTest_IntoReport(testName+"_Scenario"+ scenario, testDescription, testCategory, testSteps);
			
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

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateIntervalPackageRateProd", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
