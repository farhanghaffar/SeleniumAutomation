package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ValidatePackageRatePlanCreationFromIntervalRatesV2 extends TestCore {

	// AUTOMATION-1745
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
	public void validatePackageRatePlanCreationFromIntervalRatesV2(String delim, String packageRatePlanName,
			String folioDisplayName, String description, String channels, String roomClasses,
			String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights,
			String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
			String isWithInDaysReq, String withInDaysCount, String promoCode, String isPolicesReq, String policiesType,
			String policiesName, String seasonDelim, String seasonName, String seasonStartDate, String seasonEndDate, String isMonDay,
			String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason,
			String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults,
			String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight,
			String extraRoomClassAdditionalChildPerNight, String isSeasonLevelRules, String isAssignRulesByRoomClass,
			String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String isSeasonPolicies,String isAssignPoliciesByRoomClass,String seasonPolicySpecificRoomClasses,
			String seasonPolicyType, String seasonPolicyValues,
			String isProRateStayInSeason, String isProRateInRoomClass, String roomClassesWithProRateUnchecked,
			String isCustomPerNight, String customRoomClasses, String customRatePerNight,
			String isCustomRatePerNightAdultandChild,String customRateChildPerNight, String customRateAdultdPerNight, String productAmount,
			String calculationMethodOne, String calculationMethodTwo, String rateType,
			String intervalRatePlanIntervalValue, String isDefaultProRateChecked, String updatedChannels,
			String updatedRoomClasses, String updatedClassesRate, String updatedSeasonName, String isCreateProducts,
			String productsName, String isSellOnBookingEngine, String bookingEngineAvailabilityOption,
			String productDescription, String productCategory, String productPolicy)
			throws InterruptedException, IOException, ParseException {

		Utility.DELIM =  delim;
		Utility.SEASONDELIM =  "\\"+seasonDelim;
		String productDetailsDELIM = Utility.DELIM + Utility.DELIM;

		test_name = "ValidatePackageRatePlanCreationFromIntervalRatesV2";
		testDescription = "Rates V2 - Create/Update/Delete Package Rate Plan of Interval Rate<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1807' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1807</a>";
		testCategory = "CreatePackageRatePlanFromNightlyRateV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RatePackage ratePackage = new RatePackage();
		DerivedRate derivedRate = new DerivedRate();
		Login login = new Login();
		Policies policies = new Policies();
		String randomString = Utility.generateRandomString();
		// randomString = "WKSUMNkykh";
		packageRatePlanName = packageRatePlanName + randomString;
		folioDisplayName = folioDisplayName + randomString;
		seasonName = seasonName + randomString;
		updatedSeasonName = updatedSeasonName + randomString;
		boolean isCreateProduct = Boolean.parseBoolean(isCreateProducts);

		String timeZone = "America/New_York";

		String calendarTodayDay = "today";
		String parentSeasonDateFormat = "dd/M/yyyy";
		String seasonDuration = "2";
		String intervalMinimumValue = "2";
		String negativeIntervalValue = "-2";
		String fourDigitIntervalValue = "1999";
		String rateTypeSummary = null;

		HashMap<String, String> producstPriceMap = new HashMap<>();
		HashMap<String, String> producstCalculationMethodMap = new HashMap<>();
		HashMap<String, String> producstImage = new HashMap<>();
		HashMap<String, String> producstCategories = new HashMap<>();
		String productWithDetails = null; // image + Utility.DELIM + productName + Utility.DELIM + category +
											// Utility.DELIM
											// + calculationMethod + Utility.DELIM + productPrice
		String productName = null;
		String productFirstCalculationMethod = null;
		String productSecondCalculationMethod = null;
		productsName = productsName + randomString;
		productDescription = productDescription + randomString;

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			// Login login = new Login();

			try {
				loginRateV2(driver);

				// login.login(driver, envURL, "autorates", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginRateV2(driver);
//				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
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

			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", parentSeasonDateFormat, getTestSteps);
			if (Utility.compareDates(selectedDate, todayDate, parentSeasonDateFormat) > 0) {
				todayDate = selectedDate;
			} 
			
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

			if (isCreateProduct) {
				testSteps.add("=================== CREATE PRODUCT ======================");
				app_logs.info("=================== CREATE PRODUCT ======================");

				Boolean isSellOnEngine = Boolean.parseBoolean(isSellOnBookingEngine);
				getTestSteps.clear();
				getTestSteps = ratePackage.createProduct(driver, productsName, isSellOnEngine,
						bookingEngineAvailabilityOption, roomClasses.split(Utility.DELIM)[0], productDescription,
						productAmount.split(Utility.DELIM)[0], productPolicy, productCategory,
						calculationMethodOne.split(Utility.DELIM)[0], calculationMethodTwo.split(Utility.DELIM)[0],
						seasonStartDate, seasonEndDate, seasonDuration, timeZone);
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

			testSteps.add(
					"=================== GET ALL PRODUCTS DEFAULT PRICE AND CALCULATION METHOD ======================");
			app_logs.info(
					"=================== GET ALL PRODUCTS DEFAULT PRICE AND CALCULATION METHOD ======================");
			producstPriceMap = ratePackage.getAllProductsPrice(driver);
			testSteps.add("Products with default Price : " + producstPriceMap.toString());
			app_logs.info("Products with default Price : " + producstPriceMap.toString());

			producstCalculationMethodMap = ratePackage.getAllProductsCalculationMethod(driver);
			testSteps.add("Products with default calculation method : " + producstCalculationMethodMap.toString());
			app_logs.info("Products with default calculation method : " + producstCalculationMethodMap.toString());

			producstImage = ratePackage.getAllProductsImage(driver);
			// testSteps.add("Products with Image source : " + producstImage.toString());
			app_logs.info("Products with Image source : " + producstImage.toString());

			producstCategories = ratePackage.getAllProductsCategories(driver);
			testSteps.add("Products with Category : " + producstCategories.toString());
			app_logs.info("Products with Category : " + producstCategories.toString());

			productWithDetails = ratePackage.getProductDetails(driver, null, productDetailsDELIM);
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[0] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[1] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[2] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[3] + "'");
			app_logs.info("'" + productWithDetails.split(productDetailsDELIM)[4] + "'");
			productName = productWithDetails.split(productDetailsDELIM)[1];
			String calculationMethod = productWithDetails.split(productDetailsDELIM)[3];
			String[] calculationArray = calculationMethod.split("/");
			app_logs.info("'" + calculationArray[0] + "'");
			app_logs.info("'" + calculationArray[1] + "'");
			productFirstCalculationMethod = calculationArray[0];
			app_logs.info(" " + productFirstCalculationMethod);
			productFirstCalculationMethod = productFirstCalculationMethod.replace("Per ", "");
			productFirstCalculationMethod = productFirstCalculationMethod.trim();
			app_logs.info("'" + productFirstCalculationMethod + "'");
			if (productFirstCalculationMethod.equals(calculationMethodOne.split(Utility.DELIM)[0])) {
				productFirstCalculationMethod = calculationMethodOne.split(Utility.DELIM)[1];
			} else {
				productFirstCalculationMethod = calculationMethodOne.split(Utility.DELIM)[0];
			}
			app_logs.info("'" + productFirstCalculationMethod + "'");
			productSecondCalculationMethod = calculationArray[1];
			app_logs.info(" " + productSecondCalculationMethod);
			productSecondCalculationMethod = productSecondCalculationMethod.replace("per ", "");
			productSecondCalculationMethod = productSecondCalculationMethod.trim();
			app_logs.info("'" + productSecondCalculationMethod + "'");
			if (productSecondCalculationMethod.equals(calculationMethodTwo.split(Utility.DELIM)[0])) {
				productSecondCalculationMethod = calculationMethodTwo.split(Utility.DELIM)[1];
			} else {
				productSecondCalculationMethod = calculationMethodTwo.split(Utility.DELIM)[0];
			}
			app_logs.info("'" + productSecondCalculationMethod + "'");
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
			testSteps.add(
					"=================== VERIFY PRO_RATE EACH SEASON BY DEFAULT CHECKBOX VALUE ======================");
			app_logs.info(
					"=================== VERIFY PRO_RATE EACH SEASON BY DEFAULT CHECKBOX VALUE ======================");
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifybyDefaultProrateCheckbox(driver,
					false);
			testSteps.addAll(getTestSteps);
			testSteps.add(
					"=================== VERIFY IF PRO_RATE EACH SEASON BY DEFAULT CHECKBOX IS CHECKED MESSAGE DISPLAYED ======================");
			app_logs.info(
					"=================== VERIFY PRO_RATE EACH SEASON BY DEFAULT IS CHECKED MESSAGE DISPLAYED ======================");
			getTestSteps.clear();
			getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver,true);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifybyDefaultProrateCheckboxMessageDisplayed(driver);
			testSteps.addAll(getTestSteps);
			testSteps.add(
					"=================== VALIDATE INTERVAL RATE INTERVAL VALUE ======================");
			app_logs.info(
					"=================== VALIDATE INTERVAL RATE INTERVAL VALUE ======================");
			testSteps.add(
					"=================== VERIFY INTERVAL RATE INTERVAL DEFAULT VALUE ======================");
			app_logs.info(
					"=================== VERIFY INTERVAL RATE INTERVAL DEFAULT VALUE ======================");
			ratesGrid.getOrVerifyIntervalValue(driver, intervalMinimumValue, true, testSteps);
			testSteps.add("Successfully verified that by default interval value is 2");
			app_logs.info("Successfully verified that by default interval value is 2");
			testSteps.add(
					"=================== VALIDATE THAT THE INTERVAL VALUE MUST BE A POSITIVE NUMBER ======================");
			app_logs.info(
					"=================== VALIDATE THAT THE INTERVAL VALUE MUST BE A POSITIVE NUMBER ======================");
			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, negativeIntervalValue);
			testSteps.addAll(getTestSteps);
			ratesGrid.byDefaultProrateCheckbox(driver,true);
			ratesGrid.getOrVerifyIntervalValue(driver, negativeIntervalValue.replaceAll("-", ""), true, testSteps);
			testSteps.add(
					"=================== VALIDATE THAT THE INTERVAL VALUE MUST BE GREATER THAN OR EQUAL 2 ======================");
			app_logs.info(
					"=================== VALIDATE THAT THE INTERVAL VALUE MUST BE GREATER THAN OR EQUAL 2 ======================");
			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, "0");
			testSteps.addAll(getTestSteps);
			ratesGrid.byDefaultProrateCheckbox(driver,false);
			ratesGrid.getOrVerifyIntervalValue(driver, intervalMinimumValue, true, testSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, "1");
			testSteps.addAll(getTestSteps);
			ratesGrid.byDefaultProrateCheckbox(driver,true);
			ratesGrid.getOrVerifyIntervalValue(driver, intervalMinimumValue, true, testSteps);
			testSteps.add("Successfully verified that user can't enter interval value less than 2");
			app_logs.info("Successfully verified that user can't enter interval value less than 2");
			
			testSteps.add(
					"=================== VALIDATE THAT THE INTERVAL VALUE MUST BE 3 DIGIT NUMBER ======================");
			app_logs.info(
					"=================== VALIDATE THAT THE INTERVAL VALUE MUST BE 3 DIGIT NUMBER ======================");
			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, fourDigitIntervalValue);
			testSteps.addAll(getTestSteps);
			ratesGrid.byDefaultProrateCheckbox(driver,false);
			ratesGrid.getOrVerifyIntervalValue(driver, fourDigitIntervalValue.substring(0, 3), true, testSteps);
			testSteps.add("Successfully verified that user can't enter interval value less than 2");
			app_logs.info("Successfully verified that user can't enter interval value less than 2");

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

			testSteps.add(
					"=================== VERIFY PRODUCTS PAGE IS DISPLAYING IN PACKAGE RATE PLAN ======================");
			app_logs.info(
					"=================== VERIFY PRODUCTS PAGE IS DISPLAYING IN PACKAGE RATE PLAN ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate type", rateTypeSummary, testSteps);

			testSteps.add("Successfully verified rate type");
			app_logs.info("Successfully verified rate type");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifyProductPage(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== VALIDATE PRODUCT SEARCH FIELD ======================");
			app_logs.info("=================== VALIDATE PRODUCT SEARCH FIELD ======================");

			testSteps.add("=================== SEARCH INVALID PRODUCT ======================");
			app_logs.info("=================== SEARCH INVALID PRODUCT ======================");

			String invalidProductName = Utility.generateRandomString();
			app_logs.info(invalidProductName);
			getTestSteps.clear();
			getTestSteps = ratePackage.searchProduct(driver, invalidProductName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.verifySearchedProductExist(driver, invalidProductName, false);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== SEARCH VALID PRODUCT ======================");
			app_logs.info("=================== SEARCH VALID PRODUCT ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.searchProduct(driver, productName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.verifySearchedProductExist(driver, productName, true);
			testSteps.addAll(getTestSteps);

			testSteps.add("===== "
					+ "Verify that user can choose one or more Products during Package rate plan creation".toUpperCase()
					+ " =====");
			app_logs.info("==== "
					+ "Verify that user can choose one or more Products during Package rate plan creation".toUpperCase()
					+ " =====");

			getTestSteps.clear();
			getTestSteps = ratePackage.selectProducts(driver, producstPriceMap);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully verified that user can choose one or more Products during Package rate plan creation");
			app_logs.info(
					"Successfully verified that user can choose one or more Products during Package rate plan creation");

			testSteps.add("===== "
					+ "Verify that Image shown when we add product to the Package is same as the one in Product"
							.toUpperCase()
					+ " ====");
			app_logs.info("===== "
					+ "Verify that Image shown when we add product to the Package is same as the one in Product"
							.toUpperCase()
					+ " ============");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifyProductsImages(driver, producstImage);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully Verified that Image shown when we add product to the Package is same as the one in Product");
			app_logs.info(
					"Successfully Verified that Image shown when we add product to the Package is same as the one in Product");

			testSteps.add("==== "
					+ "Verify that default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ "====");
			app_logs.info("==="
					+ "Verify that default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ " ====");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifySelectedProductPrice(driver, producstPriceMap);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully verified that  default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle");
			app_logs.info(
					"Successfully verified that  default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle");

			testSteps.add("==== "
					+ "Verify that default Calculation method per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ " =====");
			app_logs.info("===== "
					+ "Verify that default Calculation method per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ "=====");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifySelectedProductsCalculationMethods(driver, producstCalculationMethodMap);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully Verified that default Calculation method per Product shown when we add it to the Package is the Calculation method stated in the Product/Bundle");
			app_logs.info(
					"Successfully Verified that default Calculation method per Product shown when we add it to the Package is the Calculation method stated in the Product/Bundle");

			testSteps.add("===="
					+ "Verify that user can delete one or more Products during Package rate plan creation".toUpperCase()
					+ " =====");
			app_logs.info("=================== "
					+ "Verify that user can delete one or more Products during Package rate plan creation".toUpperCase()
					+ " ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.removeProducts(driver, producstPriceMap);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified that user can delete one or more Products during Package rate plan creation");
			app_logs.info("Verified that user can delete one or more Products during Package rate plan creation");

			testSteps.add("="
					+ "Verify that user can update price and calculation method for one or more Products during Package rate plan creation"
							.toUpperCase()
					+ " =");
			app_logs.info("=================== "
					+ "Verify that user can update price and calculation method for one or more Products during Package rate plan creation"
							.toUpperCase()
					+ " ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.searchProduct(driver, productName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.clickProduct(driver, productName, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.addProductAmount(driver, productAmount, productName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.selectCalculationMethod(driver, productName, productFirstCalculationMethod,
					productSecondCalculationMethod);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Verified that user can update price and calculation method for one or more Products during Package rate plan creation");
			app_logs.info(
					"Verified that user can update price and calculation method for one or more Products during Package rate plan creation");

			nightlyRate.clickNextButton(driver, testSteps);

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

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, channels, true, testSteps);
			String summaryChannels = channels;
			// nightlyRate.generateTitleSummaryValueForChannels(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Rate type", rateTypeSummary, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Products", "1 products", testSteps);

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
				Utility.updateReport(e, "Failed to select polccies", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select polccies", testName, "RatesV2", driver);
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

			nightlyRate.clickCompleteChanges(driver, testSteps);

			try {
				nightlyRate.clickSaveAsActive(driver, testSteps);
			} catch (Exception f) {
				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.clickSaveAsActive(driver, testSteps);
			}
			Utility.rateplanName = packageRatePlanName;

			testSteps.add("Successfully Create Rate Plan");
			app_logs.info("Successfully Create Rate Plan");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to save package rate", testName, "SaveRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to save package rate", testName, "SaveRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// search created season
		try {

			testSteps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
			app_logs.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			ratesGrid.searchRatePlan(driver, packageRatePlanName);

			String getRatPlanName = ratesGrid.selectedRatePlan(driver);

			app_logs.info("getRatPlanName: " + getRatPlanName);

			testSteps.add("Successfully verified Created Rate Plan");
			app_logs.info("Successfully verified Created Rate Plan");

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

			testSteps.add("======= "
					+ "Verify that Products tab is displayed in between 'Restrictions & Policies' and 'Calendar'"
							.toUpperCase()
					+ " =====");
			app_logs.info("===== "
					+ "Verify that Products tab is displayed in between 'Restrictions & Policies' and 'Calendar'"
							.toUpperCase()
					+ " =====");

			ratePackage.verifyProductsTabExistBetweenRestrictionsPoliciesAndCalendar(driver);

			testSteps.add(
					"Successfully verified that Products tab is displayed in between 'Restrictions & Policies' and 'Calendar'");
			app_logs.info(
					"Successfully verified that Products tab is displayed in between 'Restrictions & Policies' and 'Calendar'");

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
		String updatedRatePlan = "update_" + packageRatePlanName;

		try {

			testSteps.add(
					"=================== UPDATE PACKAGE RATE PLAN NAME, FOLIO NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== UPDATE PACKAGE RATE PLAN NAME, FOLIO NAME AND DESCRIPTION ======================");

			nightlyRate.enterRatePlanName(driver, updatedRatePlan, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, "update_" + folioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, "update_" + description, testSteps);

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

			testSteps.add("==="
					+ "Verify that user cannot change package rate plan type (if it is Nightly rates then it can't be changed to Interval rates)"
							.toUpperCase()
					+ "====");
			app_logs.info("=================== "
					+ "Verify that user cannot change package rate plan type (if it is Nightly rates then it can't be changed to Interval rates)"
							.toUpperCase()
					+ " ======================");

			if (rateType.equalsIgnoreCase("Nightly rates")) {
				ratePackage.verifyRatePlanTypeDisability(driver, "Interval rates");
			} else {
				ratePackage.verifyRatePlanTypeDisability(driver, "Nightly rates");

			}

			testSteps.add(
					"Successfully Verified that user cannot change package rate plan type (if it is Nightly rates then it can't be changed to Interval rates)");
			app_logs.info(
					"Successfully Verify that user cannot change package rate plan type (if it is Nightly rates then it can't be changed to Interval rates)");

			testSteps.add("=================== UPDATE PRODUCTS IN PACKAGE RATE PLAN ======================");
			app_logs.info("=================== UPDATE PRODUCTS IN PACKAGE RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.clickPackageTab(driver, "Products");
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== VERIFY DETAILS OF ADDED PRODUCT ======================");
			app_logs.info("=================== VERIFY DETAILS OF ADDED PRODUCT======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifySelectedProductDetails(driver, productName, productAmount,
					productWithDetails.split(productDetailsDELIM)[0], productFirstCalculationMethod,
					productSecondCalculationMethod);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.clickProduct(driver, productName, false);
			testSteps.addAll(getTestSteps);

			testSteps.add("===== "
					+ "Verify that user can choose one or more Products during Package rate plan updation".toUpperCase()
					+ " =====");
			app_logs.info("==== "
					+ "Verify that user can choose one or more Products during Package rate plan updation".toUpperCase()
					+ " =====");

			getTestSteps.clear();
			getTestSteps = ratePackage.selectProducts(driver, producstPriceMap);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully verified that user can choose one or more Products during Package rate plan updation");
			app_logs.info(
					"Successfully verified that user can choose one or more Products during Package rate plan updation");

			testSteps.add("==== "
					+ "Verify that default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ "====");
			app_logs.info("==="
					+ "Verify that default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ " ====");

			testSteps.add("===== "
					+ "Verify that Image shown when we add product to the Package is same as the one in Product"
							.toUpperCase()
					+ " ====");
			app_logs.info("===== "
					+ "Verify that Image shown when we add product to the Package is same as the one in Product"
							.toUpperCase()
					+ " ============");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifyProductsImages(driver, producstImage);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully Verified that Image shown when we add product to the Package is same as the one in Product");
			app_logs.info(
					"Successfully Verified that Image shown when we add product to the Package is same as the one in Product");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifySelectedProductPrice(driver, producstPriceMap);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully verified that  default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle");
			app_logs.info(
					"Successfully verified that  default price per Product shown when we add it to the Package is the Price stated in the Product/Bundle");

			testSteps.add("==== "
					+ "Verify that default Calculation method per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ " =====");
			app_logs.info("===== "
					+ "Verify that default Calculation method per Product shown when we add it to the Package is the Price stated in the Product/Bundle"
							.toUpperCase()
					+ "=====");

			getTestSteps.clear();
			getTestSteps = ratePackage.verifySelectedProductsCalculationMethods(driver, producstCalculationMethodMap);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Successfully Verified that default Calculation method per Product shown when we add it to the Package is the Calculation method stated in the Product/Bundle");
			app_logs.info(
					"Successfully Verified that default Calculation method per Product shown when we add it to the Package is the Calculation method stated in the Product/Bundle");

			testSteps.add("====="
					+ "Verify that user can delete one or more Products during Package rate plan updation".toUpperCase()
					+ " ====");
			app_logs.info("=================== "
					+ "Verify that user can delete one or more Products during Package rate plan updation".toUpperCase()
					+ " ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.removeProducts(driver, producstPriceMap);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified that user can delete one or more Products during Package rate plan updation");
			app_logs.info("Verified that user can delete one or more Products during Package rate plan updation");

			testSteps.add("== "
					+ "Verify that user can update price and calculation method for one or more Products during Package rate plan updation"
							.toUpperCase()
					+ "==");
			app_logs.info("=================== "
					+ "Verify that user can update price and calculation method for one or more Products during Package rate plan updation"
							.toUpperCase()
					+ " ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.searchProduct(driver, productName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.clickProduct(driver, productName, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.addProductAmount(driver, productAmount, productName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratePackage.selectCalculationMethod(driver, productName, productFirstCalculationMethod,
					productSecondCalculationMethod);
			testSteps.addAll(getTestSteps);

			testSteps.add(
					"Verified that user can update price and calculation method for one or more Products during Package rate plan updation");
			app_logs.info(
					"Verified that user can update price and calculation method for one or more Products during Package rate plan updation");

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

			testSteps.add("=================== UPDATE DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== UPDATE DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, updatedChannels, false, testSteps);
			nightlyRate.selectChannels(driver, updatedChannels, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to upadet channel", testName, "UpdateChannel", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to upadet channel", testName, "UpdateChannel", driver);
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
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to updating room classes", testName, "UpdateRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to updating room classes", testName, "UpdateRoomClass", driver);
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

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
					Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					withInDaysCount, promoCode, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to updating restrications", testName, "UpdateRestrictions", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to updating restrications", testName, "UpdateRestrictions", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			ratesGrid.clickOnSeasonTab(driver);
			String[] endDateArray = seasonEndDate.split(Utility.DELIM);
			app_logs.info(endDateArray[endDateArray.length - 1]);
			String endDate = endDateArray[endDateArray.length - 1];
			nightlyRate.selectSeasonDates(driver, testSteps, seasonStartDate.split(Utility.DELIM)[0], endDate);
			nightlyRate.enterSeasonName(driver, testSteps, updatedSeasonName);
			nightlyRate.selectSeasonDays(driver, testSteps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
					isSaturDay, isSunDay);
			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonColor(driver, testSteps);

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
		// here to enter rate for each room class
		try {

			testSteps.add("=================== UPDATING RATE VALUE FOR EACH ROOM CLASS ======================");
			app_logs.info("=================== UPDATING RATE VALUE FOR EACH ROOM CLASS ======================");

			String[] splitRoomClassName = roomClasses.split(Utility.DELIM);
			String[] splitRatePerNight = ratePerNight.split(Utility.DELIM);

			HashMap<String, String> mapRoomClassWithRate = new HashMap<>();
			for (int i = 0; i < splitRoomClassName.length; i++) {
				String getRoomClass = splitRoomClassName[i].trim();
				String getRate = splitRatePerNight[i].trim();

				mapRoomClassWithRate.put(getRoomClass, getRate);

				app_logs.info("RoomClass: " + getRoomClass);
				app_logs.info("Pro rate: " + getRate);
				getTestSteps.clear();
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterRoomClassRate(driver, getRoomClass, getRate);
				testSteps.addAll(getTestSteps);

			}
			String[] splitRoomClass = updatedRoomClasses.split(Utility.DELIM);
			String[] splitUpdatedRate = updatedClassesRate.split(Utility.DELIM);
			for (int i = 0; i < splitRoomClass.length; i++) {
				String getRoomClass = splitRoomClass[i].trim();
				String getRate = splitUpdatedRate[i].trim();

				app_logs.info("RoomClass: " + getRoomClass);
				app_logs.info("Pro rate: " + getRate);
				getTestSteps.clear();
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterRoomClassRate(driver, getRoomClass, getRate);
				testSteps.addAll(getTestSteps);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update rates for each room class", testName,
						"UpdateRateForRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update rates for each room class", testName,
						"UpdateRateForRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SAVE UPDATED PAKCAGE RATE PLAN SEASON ======================");
			app_logs.info("=================== SAVE UPDATED PAKCAGE RATE PLAN SEASON ======================");

			nightlyRate.clickSaveSason(driver, testSteps);

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
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, updatedRatePlan);
			testSteps.add("=================== " + "DELETE PARENT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "DELETE parent RATE PLAN".toUpperCase() + " ======================");
			getTestSteps.clear();
			getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, updatedRatePlan, "Delete", getTestSteps);
			testSteps.addAll(getTestSteps);
			testSteps.add("Successfully Delete Parent rate plan '" + updatedRatePlan + "'");
			app_logs.info("Successfully Delete Parent rate plan '" + updatedRatePlan + "'");

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
		return Utility.getData("ValidatePackageRatePlanInterval", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
