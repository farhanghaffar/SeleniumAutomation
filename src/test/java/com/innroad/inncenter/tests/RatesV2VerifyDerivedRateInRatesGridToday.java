
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

public class RatesV2VerifyDerivedRateInRatesGridToday extends TestCore {
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

	@Test
	public void ratesV2RatesV2VerifyDerivedRateInRatesGrid()
			throws InterruptedException, IOException, ParseException {
		String clientName = "AutoRates";
		String parentRatePlanType = "Nightly rate plan";
		String parentRatePlanName = "ParentRatePlan";
		String parentRateFolioName = "Parent Folio Name";
		String parentRateDescription = "ParentRatePlanDescription";
		String intervalRatePlanIntervalValue = "2";
		String isDefaultProRateChecked = "TRUE";
		String packageRateType = "Nightly rates";		
		String packageratePlanProduct = "Wine";
		String packageRatePlanPerRateIncrementType1 = "adult";	
		String packageRatePlanPerRateIncrementType2 = "interval";
		String packageRatePlanProductAmount = "20";
		String channels = "innCenter";
		String roomClassNames = "";
		String roomClassSize = "3";
		String isRatePlanRistrictionReq = "TRUE";
		String ristrictionType = "Length of stay,Booking window,Promo code";	
		String isMinStay = "TRUE";
		String minNights = "2";
		String isMaxStay = "TRUE";
		String maxNights = "3";
		String isMoreThanDaysReq = "TRUE";		
		String moreThanDaysCount = "4";
		String isWithInDaysReq = "FALSE";
		String withInDaysCount = "5";
		String promoCode = "abs";
		String isPolicesReq = "FALSE";
		String policyTypes = "Cancellation,Deposit";	
		String policyNames = "CancellationPolicy0Z5rCuavdI,Test";
		String parentSeasonName = "ParentSeason";
		String isSundayCheck = "yes";
		String isMondayCheck = "yes";		
		String isTuesdayCheck = "yes";
		String isWednesdayCheck = "yes";
		String isThursdayCheck = "yes";
		String isFridayCheck = "yes";
		String isSaturdayCheck = "yes";
		String isAdditionalChargesForChildrenAdults = "No";
		String parentMaxAdults = "2";
		String parentMaxPersons = "3";
		String parentAdditionalAdultsPerNight = "2";
		String parentAdditionalChildPerNight = "1";
		String parentIsAddRoomClassInSeason = "No";
		String roomClassesBaseAmount = "100,90,80";
		String additionalAdult = "0";
		String additionalChild = "0";
		String seasonDuration = "1";
		String seasonRules = "Min Nights|No check-in|No check-out";
		String seasonRuleMinStayValue = "2";
		String derivedRatePlan = "DerivedRate";
		String derivedRatePlanFolioDisplayName = "FolioDerivedrate";
		String derivedRatePlanDescription = "Derived Rate Plan Desc";
		String percentValue = "10.06";
		String currencyValue = "10.98";
		String istakenRulesFromParentRateplan = "TRUE";
		String seasonType = "Custom date range";	
		String startDate = null;
		String endDate = null;
		String overrideDerivedRateValue = "200";
		String overrideBaseRateValue = "250";
		String bulkUpdateDerivedRateValue = "300";
		String bulkUpdateBaseRateValue = "350";
		String bulkUpdateDerivedRateMinimumStayValue = "3";
		String overrideDerivedRateMinimumStayValue = "4";
		String bulkUpdateBaseRateMinimumStayValue = "5";
		String overrideBaseRateMinimumStayValue = "6";
		String isTotalOccupancyOn = "No";
		String totalOccupancyType = "Less";
		String totalOccupancyValue = "10";
		String isMinimumStayOn = "yes";
		String isCheckInToggle = "yes";
		String isBulkNoCheckIn = "yes";
		String isCheckOutToggle = "yes";
		String isBulkNoCheckOut = "no";
			
		Utility.DELIM = ",";

		test_name = "RatesV2VerifyDerivedRateInRatesGrid";
		testDescription = "Rates V2 - Verify Derived Rate(base rate as Nightly Rate) in Rates grid<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1805' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1805</a>";
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
		Login login = new Login();
		String existingRateName = "";
		ArrayList<String> ratePlanNames = new ArrayList<String>();
		String randomString = Utility.generateRandomString();
		//randomString = "bNyONttqri";
		String derivedRatePlanHavingCurrencyGreaterThanParent = derivedRatePlan + "_GreaterCurrency" + randomString;
		String derivedRatePlanHavingCurrencyLesserThanParent = derivedRatePlan + "_LesserCurrency" + randomString;
		String derivedRatePlanHavingPercentGreaterThanParent = derivedRatePlan + "_GreaterPercent" + randomString;
		String derivedRatePlanHavingPercentLesserThanParent = derivedRatePlan + "_LesserPercent" + randomString;
		String derivedRatePlanHavingDifferentBaseRate = derivedRatePlan + "_DifferentBaseRate" + randomString;

		parentRatePlanName = parentRatePlanName + randomString;
		parentRateFolioName = parentRateFolioName + randomString;
		parentRateDescription = parentRateDescription + randomString;
		existingRateName = parentRatePlanName;
		String summaryParentRatePlanOffset = null;
		String summaryChannels = null;
		String summaryRoomClasses = null;
		ArrayList<String> roomClassNamesesRates = new ArrayList<String>();
		String restrictionsSummary = null;
		String timeZone = "America/New_York";
		String currencyName = null;
		String percent = "percent";
		String purpleColor = "168, 87, 166";
		String lesserThan = "lesser than";
		String greaterThan = "greater than";

		String todayDate = null;
		String parentSeasonColor = null;
		boolean isProrateCheckbox = false;
		String parentSeasonDateFormat = "dd/M/yyyy";
		String calendarTodayDay = "today";
		String dateFormat = "MM/dd/yyyy";
		String calendarSelectedDay = "selected";
		String baseMinStayRuleValue = null;
		String baseNoCheckInRuleValue = null;
		String baseNoCheckoutRuleValue = null;
		String derivedMinStayRuleValue = null;
		String derivedNoCheckInRuleValue = null; // enabled/has-noValue
		String derivedNoCheckoutRuleValue = null;

		String selectedDate = null;
		String currencySign = "";
		currencySign = "$";
		String purpleColorRGB = "rgb(" + purpleColor + ")";
		int numberOfDerivedratePlans = 0;
		String greaterThanComparator = " on top of ";
		String lesserThanComparator = " off ";
		String[] rates = roomClassesBaseAmount.split(Utility.DELIM);
		String lowestCalculatedRate = null;
		String[] roomClasses = null;
		int roomClassIndex = 0;
		int bulkUpdateRoomClassIndex = 0;

		String overrideMinStayValue = null;
		String overrideNoCheckInValue = null;
		String overrideNoCheckOutValue = null;

		HashMap<String, Boolean> dayMap = new HashMap<>();
		if (isMondayCheck.equalsIgnoreCase("Yes")) {
			dayMap.put("Mon", true);
		} else {
			dayMap.put("Mon", false);
		}
		if (isTuesdayCheck.equalsIgnoreCase("Yes")) {
			dayMap.put("Tue", true);
		} else {
			dayMap.put("Tue", false);
		}

		if (isWednesdayCheck.equalsIgnoreCase("Yes")) {
			dayMap.put("Wed", true);
		} else {
			dayMap.put("Wed", false);
		}
		if (isThursdayCheck.equalsIgnoreCase("Yes")) {
			dayMap.put("Thu", true);
		} else {
			dayMap.put("Thu", false);
		}
		if (isFridayCheck.equalsIgnoreCase("Yes")) {
			dayMap.put("Fri", true);
		} else {
			dayMap.put("Fri", false);
		}
		if (isSaturdayCheck.equalsIgnoreCase("Yes")) {
			dayMap.put("Sat", true);
		} else {
			dayMap.put("Sat", false);
		}
		if (isSundayCheck.equalsIgnoreCase("Yes")) {
			dayMap.put("Sun", true);
		} else {
			dayMap.put("Sun", false);
		}

		if (seasonRules.contains("Min Nights")) {
			baseMinStayRuleValue = seasonRuleMinStayValue;
			if (Boolean.parseBoolean(istakenRulesFromParentRateplan)) {
				derivedMinStayRuleValue = seasonRuleMinStayValue;
			} else {
				derivedMinStayRuleValue = null;
			}
		} else {
			baseMinStayRuleValue = null;
			derivedMinStayRuleValue = null;
		}

		if (seasonRules.contains("No check-in")) {
			baseNoCheckInRuleValue = "enabled";
			if (Boolean.parseBoolean(istakenRulesFromParentRateplan)) {
				derivedNoCheckInRuleValue = "enabled";
			} else {
				derivedNoCheckInRuleValue = "has-noValue";
			}
		} else {
			baseNoCheckInRuleValue = "has-noValue";
			derivedNoCheckInRuleValue = "has-noValue";
		}
		if (seasonRules.contains("No check-out")) {
			baseNoCheckoutRuleValue = "enabled";
			if (Boolean.parseBoolean(istakenRulesFromParentRateplan)) {
				derivedNoCheckoutRuleValue = "enabled";
			} else {
				derivedNoCheckoutRuleValue = "has-noValue";
			}
		} else {
			baseNoCheckoutRuleValue = "has-noValue";
			derivedNoCheckoutRuleValue = "has-noValue";
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
				//loginWPI(driver);
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				//loginWPI(driver);
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
			}
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			app_logs.info("rates : " + rates);
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
			app_logs.info("Currency Name: '" + currencyName + "'");
			currencySign = currencyNameAndSign
					.substring(currencyNameAndSign.indexOf("(") + 1, currencyNameAndSign.indexOf(")") - 1).trim();
			app_logs.info("Currency Sign: '" + currencySign + "'");
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

		ArrayList<String> allCancelationPolicies = new ArrayList<String>();
		ArrayList<String> allDepositPolicies = new ArrayList<String>();
		ArrayList<String> allCheckInPolicies = new ArrayList<String>();
		ArrayList<String> allNoShowPolicies = new ArrayList<String>();
		try {
			testSteps.add("=================== GET ALL POLICIES ======================");
			app_logs.info("=================== GET ALL POLICIES ======================");

			
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
			testSteps.add("Policy Types from Excel Sheet : " + policyTypes);
			testSteps.add("Policy Names from Excel Sheet : " + policyNames);

			app_logs.info("Policy Types from Excel Sheet : " + policyTypes);
			app_logs.info("Policy Names from Excel Sheet : " + policyNames);
			String tempPolicyTypes = null;
			String tempPolicyNames = null;
			if (allCancelationPolicies.size() != 0) {
				tempPolicyTypes = "Cancellation";
				tempPolicyNames = allCancelationPolicies.get(0);

			}
			if (allDepositPolicies.size() != 0) {
				if (tempPolicyTypes == null) {
					tempPolicyTypes = "Deposit";
				} else {
					tempPolicyTypes = tempPolicyTypes + ",Deposit";
				}
				if (tempPolicyNames == null) {
					tempPolicyNames = allDepositPolicies.get(0);
				} else {
					tempPolicyNames = tempPolicyNames + "," + allDepositPolicies.get(0);
				}
			}

			if (allCheckInPolicies.size() != 0) {
				if (tempPolicyTypes == null) {
					tempPolicyTypes = "Check-in";
				} else {
					tempPolicyTypes = tempPolicyTypes + ",Check-in";
				}
				if (tempPolicyNames == null) {
					tempPolicyNames = allCheckInPolicies.get(0);
				} else {
					tempPolicyNames = tempPolicyNames + "," + allCheckInPolicies.get(0);
				}

			}
			if (allNoShowPolicies.size() != 0) {
				if (tempPolicyTypes == null) {
					tempPolicyTypes = "No Show";
				} else {
					tempPolicyTypes = tempPolicyTypes + ",No Show";
				}
				if (tempPolicyNames == null) {
					tempPolicyNames = allNoShowPolicies.get(0);
				} else {
					tempPolicyNames = tempPolicyNames + "," + allNoShowPolicies.get(0);
				}
			}
			policyTypes = tempPolicyTypes;
			policyNames = tempPolicyNames;
			testSteps.add("Final Policy Types : " + policyTypes);
			testSteps.add("Final Policy Names : " + policyNames);

			app_logs.info("Final Policy Types : " + policyTypes);
			app_logs.info("Final Policy Names : " + policyNames);

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
			testSteps.addAll(getTestSteps);
			app_logs.info("Today date : " + todayDate);
			app_logs.info("Excel Sheet Date : " + startDate);

				startDate = todayDate;
				endDate = Utility.addDate(Integer.parseInt(seasonDuration), dateFormat, startDate,
						dateFormat,
						timeZone);
			
			int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
			seasonDuration = "" + days + "";
			app_logs.info("Season Duration : " + seasonDuration);
			app_logs.info("Selected Season Start Date: " + startDate);
			app_logs.info("Selected Season End Date: " + endDate);

			getTestSteps.clear();
			selectedDate = ratesGrid.getCalendarDate(driver, calendarSelectedDay, dateFormat, getTestSteps);
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
			testSteps.add("=================== GET EXISTING RATE PLANS ======================");
			app_logs.info("=================== GET EXISTING RATE PLANS ======================");
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratePlanNames = ratesGrid.getActiveRatePlans(driver, parentRatePlanType.split(" ")[0], 1);
			existingRateName = ratePlanNames.get(0);
			if (existingRateName.equals(parentRatePlanName)) {
				existingRateName = ratePlanNames.get(1);
			}
			testSteps.add("Existing " + parentRatePlanType + " are " + ratePlanNames);
			app_logs.info("Existing " + parentRatePlanType + " are " + ratePlanNames);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all rate plan", testName, "GetAllRatePLan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all rate plan", testName, "GetAllRatePLan", driver);
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

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName);
			testSteps.addAll(getTestSteps);

			if (parentRatePlanType.equalsIgnoreCase("Package rate plan")) {

				testSteps.add("=================== SELECT RATE TYPE ======================");
				app_logs.info("=================== SELECT RATE TYPE ======================");

				getTestSteps.clear();
				getTestSteps = ratePackage.selectParentRatePlan(driver, packageRateType);
				testSteps.addAll(getTestSteps);

				if (packageRateType.equalsIgnoreCase("Interval rates")) {

					getTestSteps.clear();
					getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
					testSteps.addAll(getTestSteps);

					isProrateCheckbox = Boolean.parseBoolean(isDefaultProRateChecked);
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
				getTestSteps = ratePackage.addProductsInPackageRatePlan(driver, packageRatePlanProductAmount,
						packageratePlanProduct, packageRatePlanPerRateIncrementType1,
						packageRatePlanPerRateIncrementType2);
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
				isProrateCheckbox = Boolean.parseBoolean(isDefaultProRateChecked);
				getTestSteps.clear();
				getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, isProrateCheckbox);
				testSteps.addAll(getTestSteps);
				nightlyRate.clickNextButton(driver, testSteps);
			}

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, channels, true, testSteps);
			summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");

			ArrayList<String> roomClassesList = new ArrayList<String>();
			roomClassesList = nightlyRate.getAllRoomClassesNames(driver);
			app_logs.info("Room Classes : " + roomClasses);
			for (int i = 0; i < Integer.parseInt(roomClassSize); i++) {
				if (i < roomClassesList.size() && i < 6) {
					if (i != 0) {
						roomClassNames = roomClassNames + Utility.DELIM;
						
					}
					roomClassNames = roomClassNames + roomClassesList.get(i);
					
				}
			}
			
			app_logs.info("Parent Room Clas : " + roomClassNames);
			app_logs.info("Parent Room Classes Rates : " + roomClassNamesesRates);
			nightlyRate.selectRoomClasses(driver, roomClassNames, true, testSteps);

			if (roomClassNames.equals("All")) {
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
						Utility.parseDate(startDate, dateFormat, parentSeasonDateFormat),
						Utility.parseDate(endDate, dateFormat, parentSeasonDateFormat));
			} catch (Exception f) {
				nightlyRate.clickCreateSeason(driver, testSteps);
				nightlyRate.selectSeasonDates(driver, testSteps,
						Utility.parseDate(startDate, dateFormat, parentSeasonDateFormat),
						Utility.parseDate(endDate, dateFormat, parentSeasonDateFormat));
			}
			nightlyRate.enterSeasonName(driver, testSteps, parentSeasonName);
			nightlyRate.selectSeasonDays(driver, getTestSteps, isMondayCheck, isTuesdayCheck, isWednesdayCheck,
					isThursdayCheck, isFridayCheck, isSaturdayCheck, isSundayCheck);
			testSteps.add("Select All Days");
			app_logs.info("Select All Days");
			nightlyRate.clickCreateSeason(driver, testSteps);
			parentSeasonColor = nightlyRate.selectSeasonColor(driver, testSteps);
			app_logs.info("Parent season color  is : " + parentSeasonColor);
			testSteps.add("Parent season color  is : " + parentSeasonColor);
			app_logs.info("Parent season color  is : " + parentSeasonColor);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, testSteps,
					isAdditionalChargesForChildrenAdults);
			nightlyRate.enterDifferentRateForRoomClasses(driver, getTestSteps, roomClassNames, roomClassesBaseAmount);
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

			testSteps.add("=================== CREATE DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE DERIVED RATE PLAN ======================");

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

			nightlyRate.enterRatePlanName(driver, derivedRatePlanHavingCurrencyGreaterThanParent, testSteps);
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
			testSteps.add("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");

			derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand Value Comparison DropDown");
			app_logs.info("Expand Value Comparison DropDown");
			derivedRate.selectDropDownOptions(driver, greaterThan, testSteps);
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");

			derivedRate.selectDropDownOptions(driver, currencyName, testSteps);

			testSteps.add("===== ENTER VALUE =====");
			app_logs.info("===== ENTER VALUE =====");
			derivedRate.enterRateValue(driver, currencyValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, currencyValue, "Failed : Value missmatched");

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
				Utility.updateReport(e, "Failed to select parent rate plan off set values", testName,
						"SelectParentRatePlanOffSetValues", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select parent rate plan off set values", testName,
						"SelectParentRatePlanOffSetValues", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			summaryParentRatePlanOffset = "Derived rates will be " + currencyValue + " " + currencySign + " "
					+ greaterThan + " rates in " + parentRatePlanName;

			testSteps.add("=================== SELECT DATES ======================");
			app_logs.info("=================== SELECT DATES ======================");
			
			if (seasonType.equalsIgnoreCase("Always available")) {
				derivedRate.selectDates(driver, seasonType, testSteps);
			} else {
				derivedRate.selectDates(driver, seasonType, testSteps);
				derivedRate.customDateRangeAppear(driver, true, testSteps);
				getTestSteps.clear();
				getTestSteps = derivedRate.selectCustomStartAndEndDates(driver, startDate, endDate,
						Integer.parseInt(seasonDuration), timeZone);
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select Dates", testName, "SelectDates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Select dates", testName, "SelectDates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.selectChannels(driver, channels, true, testSteps);
			nightlyRate.verifySelectedChannels(driver, channels, true, testSteps);
			summaryChannels = channels;

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

			getTestSteps.clear();
			nightlyRate.selectRoomClasses(driver, roomClassNames, true, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedRoomClasses(driver, roomClassNames, true, getTestSteps);
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
			
			testSteps.add("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");
			app_logs.info("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");

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
			
			testSteps.add("=================== SELECT POLICIES ======================");
			app_logs.info("=================== SELECT POLICIES ======================");

			getTestSteps.clear();
			nightlyRate.selectPolicy(driver, policyTypes, policyNames, Boolean.parseBoolean(isPolicesReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifySelectedPolicy(driver, policyTypes, Boolean.parseBoolean(isPolicesReq), getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policyNames, Boolean.parseBoolean(isPolicesReq),
					getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name",
					derivedRatePlanHavingCurrencyGreaterThanParent);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", seasonType);
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
			getTestSteps = nightlyRate.verifyPolicyTitleSummaryValue(driver, policyNames, allPolicyDesc, Boolean.parseBoolean(isPolicesReq),
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
			Utility.rateplanName = derivedRatePlanHavingCurrencyGreaterThanParent;
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

			testSteps.add("=================== CREATE DERIVED RATE PLAN '"
					+ derivedRatePlanHavingCurrencyLesserThanParent + "' ======================");
			app_logs.info("=================== CREATE DERIVED RATE PLAN '"
					+ derivedRatePlanHavingCurrencyLesserThanParent + "' ======================");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.createDerivedRatePlan(driver, "Derived rate plan",
					derivedRatePlanHavingCurrencyLesserThanParent, derivedRatePlanFolioDisplayName,
					derivedRatePlanDescription, parentRatePlanName, lesserThan, currencyName, currencyValue,
					Boolean.parseBoolean(istakenRulesFromParentRateplan), seasonType, startDate, endDate, dateFormat,
					channels, roomClassNames, seasonDuration, timeZone);
			testSteps.addAll(getTestSteps);
			numberOfDerivedratePlans++;
			
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

			testSteps.add("=================== CREATE DERIVED RATE PLAN '"
					+ derivedRatePlanHavingPercentGreaterThanParent + "' ======================");
			app_logs.info("=================== CREATE DERIVED RATE PLAN '"
					+ derivedRatePlanHavingPercentGreaterThanParent + "' ======================");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.createDerivedRatePlan(driver, "Derived rate plan",
					derivedRatePlanHavingPercentGreaterThanParent, derivedRatePlanFolioDisplayName,
					derivedRatePlanDescription, parentRatePlanName, greaterThan, percent, percentValue,
					Boolean.parseBoolean(istakenRulesFromParentRateplan), seasonType, startDate, endDate, dateFormat,
					channels, roomClassNames, seasonDuration, timeZone);
			testSteps.addAll(getTestSteps);
			numberOfDerivedratePlans++;
			
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
			
			testSteps.add("=================== CREATE DERIVED RATE PLAN '"
					+ derivedRatePlanHavingPercentLesserThanParent + "' ======================");
			app_logs.info("=================== CREATE DERIVED RATE PLAN '"
					+ derivedRatePlanHavingPercentLesserThanParent + "' ======================");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.createDerivedRatePlan(driver, "Derived rate plan",
					derivedRatePlanHavingPercentLesserThanParent, derivedRatePlanFolioDisplayName,
					derivedRatePlanDescription, parentRatePlanName, lesserThan, percent, percentValue,
					Boolean.parseBoolean(istakenRulesFromParentRateplan), seasonType, startDate, endDate, dateFormat,
					channels, roomClassNames, seasonDuration, timeZone);
			testSteps.addAll(getTestSteps);
			numberOfDerivedratePlans++;
			
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
			
			testSteps.add("=================== CREATE DERIVED RATE PLAN '" + derivedRatePlanHavingDifferentBaseRate
					+ "' ======================");
			app_logs.info("=================== CREATE DERIVED RATE PLAN '" + derivedRatePlanHavingDifferentBaseRate
					+ "' ======================");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.createDerivedRatePlan(driver, "Derived rate plan",
					derivedRatePlanHavingDifferentBaseRate, derivedRatePlanFolioDisplayName, derivedRatePlanDescription,
					existingRateName, lesserThan, percent, currencyValue, Boolean.parseBoolean(istakenRulesFromParentRateplan),
					seasonType, startDate, endDate, dateFormat, channels, "All", seasonDuration, timeZone);
			testSteps.addAll(getTestSteps);
			
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
				ratesGrid.selectDateFromDatePicker(driver, startDate, dateFormat, testSteps);
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, startDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			}
			testSteps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			numberOfDerivedratePlans++;

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

		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify Derived Rate Plan should be shown in Purple color in the Rates grid Rate Plan drop down"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify Derived Rate Plan should be shown in Purple color in the Rates grid Rate Plan drop down"
							.toUpperCase()
					+ " =========");

			getTestSteps.clear();
			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			String ratePlanColor = ratesGrid.getRatePlanColorInRatePlanDropDownList(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent);
			testSteps.add("Found Derived rate plan Color : " + ratePlanColor);
			app_logs.info("Found Derived rate plan Color : " + ratePlanColor);
			testSteps.add("Expected Derived rate plan Color : " + purpleColorRGB);
			app_logs.info("Expected Derived rate plan Color : " + purpleColorRGB);
			Assert.assertEquals(ratePlanColor, purpleColorRGB, "Failed Derived Rate plan Color missmatched");
			testSteps.add(
					"Successfully Verified Derived Rate Plan should be shown in Purple color in the Rates grid Rate Plan drop down");
			app_logs.info(
					"Successfully Verified Derived Rate Plan should be shown in Purple color in the Rates grid Rate Plan drop down");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Derived Rate Plan should be shown in Purple color in the Rates grid Rate Plan drop down",
						testName, "VerifyDerivedRatePlanColor", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Derived Rate Plan should be shown in Purple color in the Rates grid Rate Plan drop down",
						testName, "VerifyDerivedratePlanColor", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add(
					"==" + "Verify Upon selection of the Derived Rate Plan Base rate should be shown in the Rates grid Rate Plan"
							+ " drop down"
							.toUpperCase() + " ==");
			app_logs.info("====== "
					+ "Verify Upon selection of the Derived Rate Plan Base rate should be shown in the Rates grid Rate Plan drop down"
							.toUpperCase()
					+ " =========");
			
			getTestSteps.clear();
			ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
					derivedRatePlanHavingCurrencyGreaterThanParent);
			testSteps.addAll(getTestSteps);
			
			String selectedRatePlan = ratesGrid.selectedRatePlan(driver);
			testSteps.add("Found Selected rate plan  : " + selectedRatePlan);
			app_logs.info("Found Selected rate plan : " + selectedRatePlan);
			testSteps.add("Expected Selected rate plan : " + parentRatePlanName);
			app_logs.info("Expected Selected rate plan : " + parentRatePlanName);
			Assert.assertEquals(selectedRatePlan, parentRatePlanName, "Failed Selected Rate plan Name missmatched");
			
			testSteps.add(
					"Successfully Verified Derived Rate Plan Base rate is showing in the Rates grid Rate Plan drop down");
			app_logs.info(
					"Successfully Verified Derived Rate Plan Base rate is showing in the Rates grid Rate Plan drop down ");
			
			testSteps.add("====== " + "Verify Derived Rate should be shown in the bottom in Purple color".toUpperCase()
					+ " =========");
			app_logs.info("====== " + "Verify Derived Rate should be shown in the bottom in Purple color".toUpperCase()
					+ " =========");
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDerivedRateLabel(driver, purpleColor, getTestSteps);
			testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify Upon selection of the Derived Rate Plan Base rate should be shown in the Rates grid Rate Plan drop down and Derived Rate should be shown in the bottom in Purple color",
						testName, "VerifyBaseRateAndDerivedratePlanColor", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify Upon selection of the Derived Rate Plan Base rate should be shown in the Rates grid Rate Plan drop down and Derived Rate should be shown in the bottom in Purple color",
						testName, "VerifyBaseRateAndDerivedratePlanColor", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that - Total number of Derived Rates count should be shown next to the Derived Rate text in brackets"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that - Total number of Derived Rates count should be shown next to the Derived Rate text in brackets"
							.toUpperCase()
					+ " =========");
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyNumberOfDerivedRatePlanInLabel(driver,
					Integer.toString(numberOfDerivedratePlans), getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - Total number of Derived Rates count should be shown next to the Derived Rate text in brackets",
						testName, "VerifyTotalNumberOfDerivedrates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - Total number of Derived Rates count should be shown next to the Derived Rate text in brackets",
						testName, "VerifyTotalNumberOfDerivedrates", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify In the Derived Rate grid all Derived Rate Plans should be displayed (which have same base rate)"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify In the Derived Rate grid all Derived Rate Plans should be displayed (which have same base rate)"
							.toUpperCase()
					+ " =========");
			
			
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			testSteps.add("====== " + "Verify Derived Rate Plans having same base rate".toUpperCase() + " =========");
			app_logs.info("====== " + "Verify Derived Rate Plans having same base rate".toUpperCase() + " =========");
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingCurrencyGreaterThanParent, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingCurrencyLesserThanParent, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingPercentGreaterThanParent, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingPercentLesserThanParent, true, testSteps);
			
			testSteps.add(
					"====== " + "Verify Derived Rate Plans having different base rate".toUpperCase() + " =========");
			app_logs.info(
					"====== " + "Verify Derived Rate Plans having different base rate".toUpperCase() + " =========");
			
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingDifferentBaseRate, false, testSteps);

			testSteps.add(
					"Successfully Verified In the Derived Rate grid all Derived Rate Plans are displaying(which have same base rate)");
			app_logs.info(
					"Successfully Verified In the Derived Rate grid all Derived Rate Plans are displaying(which have same base rate)");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify In the Derived Rate grid all Derived Rate Plans should be displayed (which have same base rate)",
						testName, "VerifyAllDerivedRatePlanDisplayed", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify In the Derived Rate grid all Derived Rate Plans should be displayed (which have same base rate)",
						testName, "VerifyAllDerivedRatePlanDisplayed", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that - Best Available Rates for each Derived Rate should be shown when Derived Rate is collapsed"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that - Best Available Rates for each Derived Rate should be shown when Derived Rate is collapsed"
							.toUpperCase()
					+ " =========");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.bestAvailableRatePlanDisplayed(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.bestAvailableRatePlanDisplayed(driver,
					derivedRatePlanHavingCurrencyLesserThanParent);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.bestAvailableRatePlanDisplayed(driver,
					derivedRatePlanHavingPercentGreaterThanParent);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.bestAvailableRatePlanDisplayed(driver,
					derivedRatePlanHavingPercentLesserThanParent);
			testSteps.addAll(getTestSteps);
			
			testSteps.add(
					"Successfully Verified that - Best Available Rates for each Derived Rate is shown when Derived Rate is collapsed");
			app_logs.info(
					"Successfully Verified that - Best Available Rates for each Derived Rate should be shown when Derived Rate is collapsed");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - Best Available Rates for each Derived Rate should be shown when Derived Rate is collapsed",
						testName, "BestAvailableRateDisplayed", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - Best Available Rates for each Derived Rate should be shown when Derived Rate is collapsed",
						testName, "BestAvailableRateDisplayed", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add(
					"==== " + "Verify that - Best Available Rates for each of the Derived Rate is lowest calculated rate across all the Room classes"
							.toUpperCase() + " ====");
			app_logs.info("====== "
					+ "Verify that - Best Available Rates for each of the Derived Rate is lowest calculated rate across all the Room classes"
							.toUpperCase()
					+ " =========");
			
			lowestCalculatedRate = rates[0];
			for (int i = 1; i < rates.length; i++) {
				if (Float.parseFloat(lowestCalculatedRate) > Float.parseFloat(rates[i])) {
					lowestCalculatedRate = rates[i];
				}
			}
			
			testSteps.add("Lowest Base Rate value is '" + lowestCalculatedRate + "'");
			app_logs.info("Lowest Base Rate value is '" + lowestCalculatedRate + "'");
			String bestAvailabelRateValue = String.format("%.2f",
					Float.parseFloat(lowestCalculatedRate) + Float.parseFloat(currencyValue));

			derivedRate.verifyBestAvailableDerivedRRateValue(driver, derivedRatePlanHavingCurrencyGreaterThanParent,
					bestAvailabelRateValue, startDate, endDate, dateFormat, timeZone, dayMap);

			testSteps.add("Successfully Verified for Derived Rate '" + derivedRatePlanHavingCurrencyGreaterThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");
			app_logs.info("Successfully Verified for Derived Rate '" + derivedRatePlanHavingCurrencyGreaterThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");

			bestAvailabelRateValue = String.format("%.2f",
					Float.parseFloat(lowestCalculatedRate) - Float.parseFloat(currencyValue));

			derivedRate.verifyBestAvailableDerivedRRateValue(driver, derivedRatePlanHavingCurrencyLesserThanParent,
					bestAvailabelRateValue, startDate, endDate, dateFormat, timeZone, dayMap);

			testSteps.add("Successfully Verified for Derived Rate '" + derivedRatePlanHavingCurrencyLesserThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");
			app_logs.info("Successfully Verified for Derived Rate '" + derivedRatePlanHavingCurrencyLesserThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");

			bestAvailabelRateValue = String.format("%.2f", Float.parseFloat(lowestCalculatedRate)
					+ ((Float.parseFloat(lowestCalculatedRate) * Float.parseFloat(percentValue)) / 100));

			derivedRate.verifyBestAvailableDerivedRRateValue(driver, derivedRatePlanHavingPercentGreaterThanParent,
					bestAvailabelRateValue, startDate, endDate, dateFormat, timeZone, dayMap);

			testSteps.add("Successfully Verified for Derived Rate '" + derivedRatePlanHavingPercentGreaterThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");
			app_logs.info("Successfully Verified for Derived Rate '" + derivedRatePlanHavingPercentGreaterThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");

			bestAvailabelRateValue = String.format("%.2f", Float.parseFloat(lowestCalculatedRate)
					- ((Float.parseFloat(lowestCalculatedRate) * Float.parseFloat(percentValue)) / 100));

			derivedRate.verifyBestAvailableDerivedRRateValue(driver, derivedRatePlanHavingPercentLesserThanParent,
					bestAvailabelRateValue, startDate, endDate, dateFormat, timeZone, dayMap);

			testSteps.add("Successfully Verified for Derived Rate '" + derivedRatePlanHavingPercentLesserThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");
			app_logs.info("Successfully Verified for Derived Rate '" + derivedRatePlanHavingPercentLesserThanParent
					+ "' best available rate is the  Lowest Calculated Rate '" + bestAvailabelRateValue + "'");

			testSteps.add(
					"Successfully Verified that - Best Available Rates for each of the Derived Rate is lowest calculated rate across all the Room classes");
			app_logs.info(
					"Successfully Verified that - Best Available Rates for each of the Derived Rate is lowest calculated rate across all the Room classes");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - Best Available Rates for each of the Derived Rate is lowest calculated rate across all the Room classes",
						testName, "BestAvailableRateValu", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - Best Available Rates for each of the Derived Rate is lowest calculated rate across all the Room classes",
						testName, "BestAvailableRateValu", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		try {
			
			testSteps.add("====== "
					+ "Verify that based on the base Rate and increase/decrease based on the currency or % value should be shown for the Derived Rate in Rates grid"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that based on the base Rate and increase/decrease based on the currency or % value should be shown for the Derived Rate in Rates grid"
							.toUpperCase()
					+ " =========");
			
			testSteps.add("====== "
					+ "Verify that - When there is greater than $ Y then 'Derived Rate Plan: $ Y on top of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that - When there is greater than $ Y then 'Derived Rate Plan: $ Y on top of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");

			testSteps.add("====== VERIFY DETAILS OF DERIVED RATE PLAN '"
					+ derivedRatePlanHavingCurrencyGreaterThanParent + "' IN DERIVED RATES SECTION =========");
			app_logs.info("========= VERIFY DETAILS OF DERIVED RATE PLAN '"
					+ derivedRatePlanHavingCurrencyGreaterThanParent + "' IN DERIVED RATES SECTION ==========");
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingCurrencyGreaterThanParent, true, testSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);

			String derivedRatePlanDetails = "Derived Rate Plan: " + currencySign + currencyValue + greaterThanComparator
					+ parentRatePlanName;

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanDetails(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, derivedRatePlanDetails, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("======== VERIFY RESTRICTIONS ========");
			app_logs.info("======== VERIFY RESTRICTIONS ========");

			app_logs.info("Restrictions : " + ristrictionType);
			testSteps.add("Restrictions : " + ristrictionType);
			if(Boolean.parseBoolean(isRatePlanRistrictionReq)){
			if (ristrictionType.contains("Length of Stay")) {
				app_logs.info("Length of Stay");
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
						derivedRatePlanHavingCurrencyGreaterThanParent,
						restriction);
				testSteps.addAll(getTestSteps);
			}
			
			if (ristrictionType.contains("Booking window")) {

				app_logs.info("Booking Window restriction");
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
						derivedRatePlanHavingCurrencyGreaterThanParent, restriction);
				testSteps.addAll(getTestSteps);
			}
			
			if (ristrictionType.contains("Promo code")) {
				getTestSteps.clear();
				getTestSteps = derivedRate.verifyDerivedRatePlanRestrictions(driver,
						derivedRatePlanHavingCurrencyGreaterThanParent, "Guest must enter promo code " + promoCode);
				testSteps.addAll(getTestSteps);
				}
			}

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRoomClassRateValues(driver, roomClassNames, startDate,
					endDate, dateFormat, timeZone, dayMap, derivedRatePlanHavingCurrencyGreaterThanParent, rates,
					currencyValue, greaterThan, currencyName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully verified details of Created Derived Rate Plan");
			app_logs.info("Successfully verified details of Created Derived Rate Plan");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - When there is greater than $ Y then 'Derived Rate Plan: $ Y on top of <<Rate Plan name>>' should be displayed below the Derived Rate Plan name",
						testName, "VerifyParentRatePlanOffsetDetails", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - When there is greater than $ Y then 'Derived Rate Plan: $ Y on top of <<Rate Plan name>>' should be displayed below the Derived Rate Plan name",
						testName, "VerifyParentRatePlanOffsetDetails", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that - When there is lesser than $ Y then 'Derived Rate Plan: $ Y on off of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that - When there is lesser than $ Y then 'Derived Rate Plan: $ Y on off of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");

			testSteps.add("====== VERIFY DETAILS OF DERIVED RATE PLAN '" + derivedRatePlanHavingCurrencyLesserThanParent
					+ "' IN DERIVED RATES SECTION =========");
			app_logs.info("========= VERIFY DETAILS OF DERIVED RATE PLAN '"
					+ derivedRatePlanHavingCurrencyLesserThanParent + "' IN DERIVED RATES SECTION ==========");
			
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingCurrencyLesserThanParent, true, testSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);

			String derivedRatePlanDetails = "Derived Rate Plan: " + currencySign + currencyValue + lesserThanComparator
					+ parentRatePlanName;

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanDetails(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, derivedRatePlanDetails, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully verified Derived Rate Plan Details");
			app_logs.info("Successfully verified Derived Rate Plan Details");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRoomClassRateValues(driver, roomClassNames, startDate,
					endDate, dateFormat, timeZone, dayMap, derivedRatePlanHavingCurrencyLesserThanParent, rates,
					currencyValue, lesserThan, currencyName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully verified details of Created Derived Rate Plan");
			app_logs.info("Successfully verified details of Created Derived Rate Plan");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - When there is lesser than $ Y then 'Derived Rate Plan: $ Y on off of <<Rate Plan name>>' should be displayed below the Derived Rate Plan name",
						testName, "ParentOffsetCurrencyLessthan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - When there is lesser than $ Y then 'Derived Rate Plan: $ Y on off of <<Rate Plan name>>' should be displayed below the Derived Rate Plan name",
						testName, "ParentOffsetCurrencyLessthan", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {

			testSteps.add("====== "
					+ "Verify that - When there is greater than X % then 'Derived Rate Plan: X% on top of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that - When there is greater than X % then 'Derived Rate Plan: X% on top of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");

			testSteps.add("====== VERIFY DETAILS OF DERIVED RATE PLAN '" + derivedRatePlanHavingPercentGreaterThanParent
					+ "' IN DERIVED RATES SECTION =========");
			app_logs.info("========= VERIFY DETAILS OF DERIVED RATE PLAN '"
					+ derivedRatePlanHavingPercentGreaterThanParent + "' IN DERIVED RATES SECTION ==========");
			
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingPercentGreaterThanParent, true, testSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);

			String derivedRatePlanDetails = "Derived Rate Plan: " + percentValue + "%" + greaterThanComparator
					+ parentRatePlanName;

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanDetails(driver,
					derivedRatePlanHavingPercentGreaterThanParent, derivedRatePlanDetails, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully verified Derived Rate Plan details");
			app_logs.info("Successfully verified Derived Rate Plan detais");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRoomClassRateValues(driver, roomClassNames, startDate,
					endDate, dateFormat, timeZone, dayMap, derivedRatePlanHavingPercentGreaterThanParent, rates,
					percentValue, greaterThan, percent);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully verified details of Created Derived Rate Plan");
			app_logs.info("Successfully verified details of Created Derived Rate Plan");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that based on the base Rate and increase/decrease based on the currency or % value should be shown for the Derived Rate in Rates grid",
						testName, "VerifyParentRatePlanOffsetDetails", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that based on the base Rate and increase/decrease based on the currency or % value should be shown for the Derived Rate in Rates grid",
						testName, "VerifyParentRatePlanOffsetDetails", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that - When there is lesser than X % then 'Derived Rate Plan: X% on off of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that - When there is lesser than X % then 'Derived Rate Plan: X% on off of rate plan should be displayed below the Derived Rate Plan name"
							.toUpperCase()
					+ " =========");

			testSteps.add("====== VERIFY DETAILS OF DERIVED RATE PLAN '" + derivedRatePlanHavingPercentLesserThanParent
					+ "' IN DERIVED RATES SECTION =========");
			app_logs.info("========= VERIFY DETAILS OF DERIVED RATE PLAN '"
					+ derivedRatePlanHavingPercentLesserThanParent + "' IN DERIVED RATES SECTION ==========");
			
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingPercentLesserThanParent, true, testSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);

			String derivedRatePlanDetails = "Derived Rate Plan: " + percentValue + "%" + lesserThanComparator
					+ parentRatePlanName;

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanDetails(driver,
					derivedRatePlanHavingPercentLesserThanParent, derivedRatePlanDetails, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully verified Derived Rate Plan details");
			app_logs.info("Successfully verified Derived Rate Plan details");

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRoomClassRateValues(driver, roomClassNames, startDate,
					endDate, dateFormat, timeZone, dayMap, derivedRatePlanHavingPercentLesserThanParent, rates,
					percentValue, lesserThan, percent);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully verified details of Created Derived Rate Plan");
			app_logs.info("Successfully verified details of Created Derived Rate Plan");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - When there is lesser than X % then 'Derived Rate Plan: X% on off of <<Rate Plan name>>' should be displayed below the Derived Rate Plan name",
						testName, "ParentOffsetPercentLessthan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that - When there is lesser than X % then 'Derived Rate Plan: X% on off of <<Rate Plan name>>' should be displayed below the Derived Rate Plan name",
						testName, "ParentOffsetPercentLessthan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			try {

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				
				ratesGrid.selectDateFromDatePicker(driver, startDate, dateFormat, testSteps);
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, startDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				driver.navigate().refresh();
				
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");

			testSteps.add("====== Override Derived Rate Value Using Bulk Update =========");
			app_logs.info("====== Override Derived Rate Value Using Bulk Update =========");
			
			roomClasses = roomClassNames.split(Utility.DELIM);
			if (roomClasses.length > 1) {
				bulkUpdateRoomClassIndex = 1;
			}
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.bulkUpdate(driver, "Rates", startDate, endDate, dateFormat, isSundayCheck,
					isMondayCheck, isTuesdayCheck, isWednesdayCheck, isThursdayCheck, isFridayCheck, isSaturdayCheck,
					isTotalOccupancyOn, greaterThan, "", derivedRatePlanHavingCurrencyGreaterThanParent,
					roomClasses[bulkUpdateRoomClassIndex], channels, "EnterNewRate", "FALSE",
					bulkUpdateDerivedRateValue, additionalAdult, additionalChild, currencyValue, currencyName, null,
					null, null, null, isMinimumStayOn, bulkUpdateDerivedRateMinimumStayValue, isCheckInToggle,
					isBulkNoCheckIn, isCheckOutToggle, isBulkNoCheckOut);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex],
					bulkUpdateDerivedRateValue, startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Verified Derived Rate Value has been override.");
			app_logs.info("Verified Derived Rate Value has been override.");

			ratesGrid.expandParentRateGrid(driver, "plus");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyBaseRatePlanRateValue(driver, parentRatePlanName,
					roomClasses[bulkUpdateRoomClassIndex], rates[bulkUpdateRoomClassIndex], startDate, endDate,
					dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified Base Rate Value has no effect.");
			app_logs.info("Verified Base Rate Value has no effect.");

			String rateValue = String.format("%.2f",
					Float.parseFloat(rates[bulkUpdateRoomClassIndex]) - Float.parseFloat(currencyValue));

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], rateValue,
					startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			rateValue = String.format("%.2f", Float.parseFloat(rates[bulkUpdateRoomClassIndex])
					+ ((Float.parseFloat(rates[bulkUpdateRoomClassIndex]) * Float.parseFloat(percentValue)) / 100));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], rateValue,
					startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			rateValue = String.format("%.2f", Float.parseFloat(rates[bulkUpdateRoomClassIndex])
					- ((Float.parseFloat(rates[bulkUpdateRoomClassIndex]) * Float.parseFloat(percentValue)) / 100));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], rateValue,
					startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Verified Other Derived Rate Values has no effect.");
			app_logs.info("Successfully Verified Other Derived Rate Values has no effect");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate) ",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that base rate can be overriden and this results in change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that base rate can be overriden and this results in change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup"
							.toUpperCase()
					+ " =========");
			
			testSteps.add("====== Override Base Rate Value from Bulk Update =========");
			app_logs.info("====== Override Base Rate Value from Bulk Update =========");
			
			roomClasses = roomClassNames.split(Utility.DELIM);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.bulkUpdate(driver, "Rates", startDate, endDate, dateFormat, isSundayCheck,
					isMondayCheck, isTuesdayCheck, isWednesdayCheck, isThursdayCheck, isFridayCheck, isSaturdayCheck,
					isTotalOccupancyOn, greaterThan, "", parentRatePlanName, roomClasses[bulkUpdateRoomClassIndex],
					channels, "EnterNewRate", "FALSE", bulkUpdateBaseRateValue, additionalAdult, additionalChild,
					currencyValue, currencyName, null, null, null, null, isMinimumStayOn,
					bulkUpdateBaseRateMinimumStayValue, isCheckInToggle, isBulkNoCheckIn, isCheckOutToggle,
					isBulkNoCheckOut);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyBaseRatePlanRateValue(driver, parentRatePlanName,
					roomClasses[bulkUpdateRoomClassIndex], bulkUpdateBaseRateValue, startDate, endDate, dateFormat,
					timeZone, dayMap);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfuly Override Base Rate Value");
			app_logs.info("Successfuly Override Base Rate Value");
			String rateValue = String.format("%.2f",
					Float.parseFloat(bulkUpdateBaseRateValue) - Float.parseFloat(currencyValue));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], rateValue,
					startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			rateValue = String.format("%.2f", Float.parseFloat(bulkUpdateBaseRateValue)
					+ ((Float.parseFloat(bulkUpdateBaseRateValue) * Float.parseFloat(percentValue)) / 100));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], rateValue,
					startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			rateValue = String.format("%.2f", Float.parseFloat(bulkUpdateBaseRateValue)
					- ((Float.parseFloat(bulkUpdateBaseRateValue) * Float.parseFloat(percentValue)) / 100));
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], rateValue,
					startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully Verified Derived Rate Values has updated");
			app_logs.info("Successfully Verified Derived Rate Values has updated");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that base rate can be overriden and this results in change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup",
						testName, "OverrideBaseRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that base rate can be overriden and this results in change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup",
						testName, "OverrideBaseRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that overriden derived rate has no change even though there is a change/override on base rate"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that overriden derived rate has no change even though there is a change/override on base rate"
							.toUpperCase()
					+ " =========");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex],
					bulkUpdateDerivedRateValue, startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully Verified Override Derived Rate Value has no effect.");
			app_logs.info("Successfully Verified Override Derived Rate Value has no effect");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that overriden derived rate has no change even though there is a change/override on base rate",
						testName, "VerifyNoChangeONOverrideRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that overriden derived rate has no change even though there is a change/override on base rate",
						testName, "", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");

			testSteps.add("====== Override Derived Rate Value from Rates Grid =========");
			app_logs.info("====== Override Derived Rate Value from Rates Grid =========");
			roomClasses = roomClassNames.split(Utility.DELIM);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.changeDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex],
					overrideDerivedRateValue, startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex],
					overrideDerivedRateValue, startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified Derived Rate Value has been override.");
			app_logs.info("Verified Derived Rate Value has been override.");

			getTestSteps.clear();
			getTestSteps = derivedRate.verifyBaseRatePlanRateValue(driver, parentRatePlanName,
					roomClasses[roomClassIndex], rates[roomClassIndex], startDate, endDate, dateFormat, timeZone,
					dayMap);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified Base Rate Value has no effect.");
			app_logs.info("Verified Base Rate Value has no effect.");

			String rateValue = String.format("%.2f",
					Float.parseFloat(rates[roomClassIndex]) - Float.parseFloat(currencyValue));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[roomClassIndex], rateValue, startDate,
					endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			rateValue = String.format("%.2f", Float.parseFloat(rates[roomClassIndex])
					+ ((Float.parseFloat(rates[roomClassIndex]) * Float.parseFloat(percentValue)) / 100));
			
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[roomClassIndex], rateValue, startDate,
					endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			rateValue = String.format("%.2f", Float.parseFloat(rates[roomClassIndex])
					- ((Float.parseFloat(rates[roomClassIndex]) * Float.parseFloat(percentValue)) / 100));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[roomClassIndex], rateValue, startDate,
					endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate) ",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that base rate can be overriden and this results will change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that base rate can be overriden and this results will change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup"
							.toUpperCase()
					+ " =========");
			
			testSteps.add("====== Override Base Rate Value from Rates Grid =========");
			app_logs.info("====== Override Base Rate Value from Rates Grid =========");
			
			roomClasses = roomClassNames.split(Utility.DELIM);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.changeBaseRatePlanRateValue(driver, parentRatePlanName,
					roomClasses[roomClassIndex], overrideBaseRateValue, startDate, endDate, dateFormat, timeZone,
					dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyBaseRatePlanRateValue(driver, parentRatePlanName,
					roomClasses[roomClassIndex], overrideBaseRateValue, startDate, endDate, dateFormat, timeZone,
					dayMap);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfuly Override Base Rate Value");
			app_logs.info("Successfuly Override Base Rate Value");

			String rateValue = String.format("%.2f",
					Float.parseFloat(overrideBaseRateValue) - Float.parseFloat(currencyValue));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[roomClassIndex], rateValue, startDate,
					endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			rateValue = String.format("%.2f", Float.parseFloat(overrideBaseRateValue)
					+ ((Float.parseFloat(overrideBaseRateValue) * Float.parseFloat(percentValue)) / 100));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[roomClassIndex], rateValue, startDate,
					endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			rateValue = String.format("%.2f", Float.parseFloat(overrideBaseRateValue)
					- ((Float.parseFloat(overrideBaseRateValue) * Float.parseFloat(percentValue)) / 100));
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[roomClassIndex], rateValue, startDate,
					endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully Verified Derived Rate Values has updated");
			app_logs.info("Successfully Verified Derived Rate Values has updated");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that base rate can be overriden and this results in change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup",
						testName, "OverrideBaseRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that base rate can be overriden and this results in change in all the Derived rates (of the same base rate) based on the %/$ increase/decrease as per each Derived rate setup",
						testName, "OverrideBaseRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		// Verify
		try {
			
			testSteps.add("====== "
					+ "Verify that overriden derived rate has no change even though there is a change/override on base rate"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that overriden derived rate has no change even though there is a change/override on base rate"
							.toUpperCase()
					+ " =========");
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.verifyDerivedRatePlanRateValue(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex],
					overrideDerivedRateValue, startDate, endDate, dateFormat, timeZone, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Verified Override Derived Rate Value has no effect.");
			app_logs.info("Successfully Verified Override Derived Rate Value has no effect");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that overriden derived rate has no change even though there is a change/override on base rate",
						testName, "VerifyNoChangeONOverrideRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that overriden derived rate has no change even though there is a change/override on base rate",
						testName, "", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Rules Verification

		try {
			try {
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				
				ratesGrid.selectDateFromDatePicker(driver, startDate, dateFormat, testSteps);
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, startDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			testSteps.add("====== "
					+ "Verify that Rule in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that Rule in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			
			testSteps.add("====== Override Derived Rule Value Using Bulk Update =========");
			app_logs.info("====== Override Derived Rule Value Using Bulk Update =========");
			
			roomClasses = roomClassNames.split(Utility.DELIM);
			if (roomClasses.length > 1) {
				bulkUpdateRoomClassIndex = 1;
			}
			getTestSteps.clear();
			getTestSteps = ratesGrid.bulkUpdate(driver, "Rules", startDate, endDate, dateFormat, isSundayCheck,
					isMondayCheck, isTuesdayCheck, isWednesdayCheck, isThursdayCheck, isFridayCheck, isSaturdayCheck,
					isTotalOccupancyOn, greaterThan, "", derivedRatePlanHavingCurrencyGreaterThanParent,
					roomClasses[bulkUpdateRoomClassIndex], channels, "EnterNewRate", "FALSE",
					bulkUpdateDerivedRateValue, additionalAdult, additionalChild, currencyValue, currencyName, null,
					null, null, null, isMinimumStayOn, bulkUpdateDerivedRateMinimumStayValue, isCheckInToggle,
					isBulkNoCheckIn, isCheckOutToggle, isBulkNoCheckOut);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);

			if (isMinimumStayOn.equalsIgnoreCase("yes")) {
				overrideMinStayValue = bulkUpdateDerivedRateMinimumStayValue;
			} else {
				overrideMinStayValue = derivedMinStayRuleValue;
			}
			if (isCheckInToggle.equalsIgnoreCase("yes")) {
				if (isBulkNoCheckIn.equalsIgnoreCase("yes")) {
					overrideNoCheckInValue = "enabled";
				} else {
					overrideNoCheckInValue = "has-noValue";
				}
			} else {
				overrideNoCheckInValue = derivedNoCheckInRuleValue;
			}
			if (isCheckOutToggle.equalsIgnoreCase("yes")) {
				if (isBulkNoCheckOut.equalsIgnoreCase("yes")) {
					overrideNoCheckOutValue = "enabled";
				} else {
					overrideNoCheckOutValue = "has-noValue";
				}
			} else {
				overrideNoCheckOutValue = derivedNoCheckoutRuleValue;
			}
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, overrideMinStayValue, overrideNoCheckInValue, overrideNoCheckOutValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Verified Derived Rate Plan Rule Value has been override.");
			app_logs.info("Verified Derived Rate Plan Rule Value has been override.");

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Base", channels, startDate, endDate, dateFormat,
					timeZone, baseMinStayRuleValue, baseNoCheckInRuleValue, baseNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Verified Base Rule Value has no effect.");
			app_logs.info("Verified Base Rule Value has no effect.");

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Verified Other Derived Rate Plan Rule Values has no effect.");
			app_logs.info("Successfully Verified Other Derived Rate Plan Rule Values has no effect");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate) ",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		try {
			try {

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				
				ratesGrid.selectDateFromDatePicker(driver, startDate, dateFormat, testSteps);
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, startDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			testSteps.add("====== "
					+ "Verify that Rule in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that Rule in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			
			testSteps.add("====== Override Derived Rule Value Using Rates Grid =========");
			app_logs.info("====== Override Derived Rule Value Using Rates Grid =========");
			
			roomClasses = roomClassNames.split(Utility.DELIM);

			app_logs.info(roomClasses[roomClassIndex] + channels + derivedRatePlanHavingCurrencyGreaterThanParent
					+ bulkUpdateRoomClassIndex);

			ratesGrid.expandParentRateGrid(driver, "minus");

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			boolean noCheckIn = false;
			boolean noCheckOut = false;

			if (isBulkNoCheckIn.equalsIgnoreCase("yes")) {
				noCheckIn = true;
			}
			if (isBulkNoCheckOut.equalsIgnoreCase("yes")) {
				noCheckOut = true;
			}

			getTestSteps.clear();
			getTestSteps = ratesGrid.overrideMinStayRules(driver, startDate, endDate, dateFormat,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex], timeZone, channels,
					overrideDerivedRateMinimumStayValue, noCheckIn, noCheckOut, dayMap);
			testSteps.addAll(getTestSteps);

			if (isMinimumStayOn.equalsIgnoreCase("yes")) {
				overrideMinStayValue = overrideDerivedRateMinimumStayValue;
			} else {
				overrideMinStayValue = bulkUpdateDerivedRateMinimumStayValue;
			}
			if (isBulkNoCheckIn.equalsIgnoreCase("yes")) {
				overrideNoCheckInValue = "enabled";
			} else {
				overrideNoCheckInValue = "has-noValue";
			}

			if (isBulkNoCheckOut.equalsIgnoreCase("yes")) {
				overrideNoCheckOutValue = "enabled";
			} else {
				overrideNoCheckOutValue = "has-noValue";
			}

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, overrideMinStayValue, overrideNoCheckInValue, overrideNoCheckOutValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Verified Derived Rate Plan Rule Value has been override.");
			app_logs.info("Verified Derived Rate Plan Rule Value has been override.");

			ratesGrid.expandParentRateGrid(driver, "plus");

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Base", channels, startDate, endDate, dateFormat,
					timeZone, baseMinStayRuleValue, baseNoCheckInRuleValue, baseNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Verified Base Rule Value has no effect.");
			app_logs.info("Verified Base Rule Value has no effect.");

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Verified Other Derived Rate Plan Rule Values has no effect.");
			app_logs.info("Successfully Verified Other Derived Rate Plan Rule Values has no effect");
			// end

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate) ",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Rules Verification

		try {
			try {

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				
				ratesGrid.selectDateFromDatePicker(driver, startDate, dateFormat, testSteps);
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, startDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			testSteps.add("====== "
					+ "Verify that Rule in Parent Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that Rule in Parent Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");

			testSteps.add("====== Override Parent Rule Value Using Bulk Update =========");
			app_logs.info("====== Override Parent Rule Value Using Bulk Update =========");
			
			roomClasses = roomClassNames.split(Utility.DELIM);
			
			if (roomClasses.length > 1) {
				bulkUpdateRoomClassIndex = 1;
			}
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.bulkUpdate(driver, "Rules", startDate, endDate, dateFormat, isSundayCheck,
					isMondayCheck, isTuesdayCheck, isWednesdayCheck, isThursdayCheck, isFridayCheck, isSaturdayCheck,
					isTotalOccupancyOn, greaterThan, "", parentRatePlanName, roomClasses[bulkUpdateRoomClassIndex],
					channels, "EnterNewRate", "FALSE", bulkUpdateDerivedRateValue, additionalAdult, additionalChild,
					currencyValue, currencyName, null, null, null, null, isMinimumStayOn,
					bulkUpdateBaseRateMinimumStayValue, isCheckInToggle, isBulkNoCheckIn, isCheckOutToggle,
					isBulkNoCheckOut);
			testSteps.addAll(getTestSteps);

			if (isMinimumStayOn.equalsIgnoreCase("yes")) {
				overrideMinStayValue = bulkUpdateBaseRateMinimumStayValue;
			} else {
				overrideMinStayValue = baseMinStayRuleValue;
			}
			if (isCheckInToggle.equalsIgnoreCase("yes")) {
				if (isBulkNoCheckIn.equalsIgnoreCase("yes")) {
					overrideNoCheckInValue = "enabled";
				} else {
					overrideNoCheckInValue = "has-noValue";
				}
			}
			if (isCheckOutToggle.equalsIgnoreCase("yes")) {
				if (isBulkNoCheckOut.equalsIgnoreCase("yes")) {
					overrideNoCheckOutValue = "enabled";
				} else {
					overrideNoCheckOutValue = "has-noValue";
				}
			}
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Base", channels, startDate, endDate, dateFormat,
					timeZone, overrideMinStayValue, overrideNoCheckInValue, overrideNoCheckOutValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Verified Base Rule Value has been overriden.");
			app_logs.info("Verified Base Rule Value has been overriden.");

			// Verify derived rates
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, bulkUpdateDerivedRateMinimumStayValue, overrideNoCheckInValue, overrideNoCheckOutValue,
					dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Verified Other Derived Rate Plan Rule Values has no effect.");
			app_logs.info("Successfully Verified Other Derived Rate Plan Rule Values has no effect");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate) ",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rate in Derived Rate can be overriden and this results in no change in base rate nor in other Derived rates (of the same base rate)",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		try {
			try {

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				
				ratesGrid.selectDateFromDatePicker(driver, startDate, dateFormat, testSteps);
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, startDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				ratesGrid.selectAndReturnSpecificRatePlan(driver, getTestSteps,
						derivedRatePlanHavingCurrencyGreaterThanParent);
				testSteps.addAll(getTestSteps);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  select calendar Date", testName, "selectCalendarDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			testSteps.add("====== "
					+ "Verify that Rule in Parent Rate can be overriden and this results in no change in rules in all Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			app_logs.info("====== "
					+ "Verify that Rule in Parent Rate can be overriden and this results in change in rules in all Derived rates (of the same base rate)"
							.toUpperCase()
					+ " =========");
			
			testSteps.add("====== Override Parent Rule Value Using Rates Grid =========");
			app_logs.info("====== Override Parent Rule Value Using Rates Grid =========");
			
			roomClasses = roomClassNames.split(Utility.DELIM);
			boolean noCheckIn = false;
			boolean noCheckOut = false;

			if (isBulkNoCheckIn.equalsIgnoreCase("yes")) {
				noCheckIn = true;
			}
			if (isBulkNoCheckOut.equalsIgnoreCase("yes")) {
				noCheckOut = true;
			}

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.overrideMinStayRules(driver, startDate, endDate, dateFormat, parentRatePlanName,
					roomClasses[roomClassIndex], timeZone, channels, overrideBaseRateMinimumStayValue, noCheckIn,
					noCheckOut, dayMap);
			testSteps.addAll(getTestSteps);

			overrideMinStayValue = overrideBaseRateMinimumStayValue;

			if (isBulkNoCheckIn.equalsIgnoreCase("yes")) {
				overrideNoCheckInValue = "enabled";
			} else {
				overrideNoCheckInValue = "has-noValue";
			}

			if (isBulkNoCheckOut.equalsIgnoreCase("yes")) {
				overrideNoCheckOutValue = "enabled";
			} else {
				overrideNoCheckOutValue = "has-noValue";
			}

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Base", channels, startDate, endDate, dateFormat,
					timeZone, overrideMinStayValue, overrideNoCheckInValue, overrideNoCheckOutValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceBaseRateRoomClass(driver, parentRatePlanName,
					roomClasses[bulkUpdateRoomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Verified Base Rule Value has been overriden.");
			app_logs.info("Verified Base Rule Value has been overriden.");

			ratesGrid.expandParentRateGrid(driver, "minus");

			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, overrideDerivedRateMinimumStayValue, overrideNoCheckInValue, overrideNoCheckOutValue,
					dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingCurrencyLesserThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentGreaterThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver,
					derivedRatePlanHavingPercentGreaterThanParent, "minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"plus", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[roomClassIndex], "Expand");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesValue(driver, "Derived", channels, startDate, endDate, dateFormat,
					timeZone, derivedMinStayRuleValue, derivedNoCheckInRuleValue, derivedNoCheckoutRuleValue, dayMap);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.expandReduceDerivedRateRoomClass(driver,
					derivedRatePlanHavingPercentLesserThanParent, roomClasses[roomClassIndex], "Reduce");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanHavingPercentLesserThanParent,
					"minus", getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified ALL Derived Rate Plans Rule Value has no effect.");
			app_logs.info("Verified All Derived Rate Plans Rule Value has no effect.");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rule in Parent Rate can be overriden and this results in change in other Derived rates (of the same base rate) ",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to Verify that Rule in Parent Rate can be overriden and this results in change in other Derived rates (of the same base rate) ",
						testName, "OverrideDerivedRate", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Delete Derived rate Plan
		try {

			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
			
			testSteps.add("=================== " + "DELETE PARENT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "DELETE parent RATE PLAN".toUpperCase() + " ======================");
			
			getTestSteps.clear();
			getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, parentRatePlanName, "Delete", getTestSteps);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Successfully Delete Parent rate plan '" + parentRatePlanName + "'");
			app_logs.info("Successfully Delete Parent rate plan '" + parentRatePlanName + "'");
			
			ratesGrid.clickRatePlanArrow(driver, testSteps);

			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, derivedRatePlanHavingDifferentBaseRate);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);

			testSteps
					.add("=================== " + "DELETE Derived RATE PLAN".toUpperCase() + " ======================");
			app_logs.info(
					"=================== " + "DELETE DERIVED RATE PLAN".toUpperCase() + " ======================");
			getTestSteps.clear();
			getTestSteps = nightlyRate.deleteDeriveRatePlan(driver, derivedRatePlanHavingDifferentBaseRate, "Delete",
					getTestSteps);
			testSteps.addAll(getTestSteps);

			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);

			derivedRate.derivedratePlanExist(driver, derivedRatePlanHavingDifferentBaseRate, false, testSteps);
			testSteps.add("Successfully Delete derived rate plan '" + derivedRatePlanHavingDifferentBaseRate + "'");
			app_logs.info("Successfully Delete derived rate plan '" + derivedRatePlanHavingDifferentBaseRate + "'");

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

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
}
