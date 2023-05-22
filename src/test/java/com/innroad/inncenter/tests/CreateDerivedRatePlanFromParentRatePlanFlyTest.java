package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
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

public class CreateDerivedRatePlanFromParentRatePlanFlyTest extends TestCore {
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
	public void createDerivedRatePlanFromParentRatePlanFlyTest() throws InterruptedException, IOException {
		
		String clientName = "AutoRates";
		String delim = "|";
		String parentRatePlanType = "Interval rate plan"; 
		String parentRatePlanName = "ParentIntervalRatePlan"; 
		String parentRateFolioName = "Parent Folio Name";
		String parentRateDescription = "Interval Base Rate Plan";
		String parentRoomClass = "Double Bed Room|Policy Suite";
		String parentRoomClassSize = "2"; 
		String parentBaseAmount = "100|150";
		String addtionalAdult = "0";
		String additionalChild = "0";
		String intervalRatePlanIntervalValue = "2";
		String isDefaultProrateChecked = "TRUE";
		String packageRatePlanRateType = "Nightly rates";
		String packageRatePlanProductName = "Wine";
		String packageratePlanProductFirstCalculationMethod = "adult";
		String packageratePlanProductsecondCalculationMethod = "interval";
		String packageRatePlanProductAmount = "20"; 
		String channels = "innCenter"; 
		String isRatePlanRistrictionReq = "TRUE";
		String ristrictionType = "Length of stay|Booking window|Promo code"; 
		String isMinStay = "TRUE";
		String minNights = "2"; 
		String isMaxStay = "TRUE";
		String maxNights = "2";
		String isMoreThanDaysReq = "TRUE"; 
		String moreThanDaysCount = "2"; 
		String isWithInDaysReq = "TRUE"; 
		String withInDaysCount = "2";
		String promoCode = "abc123";
		String isPolicesReq = "FALSE";
		String policiesType = "Cancellation|Deposit";
		String policiesName = "CancellationPolicy0Z5rCuavdI|DepositPolicy8kkUb04xb5";
		String seasonDelim = ",";
		String seasonName = "Season,Season2"; 
		String seasonStartDate = "12/10/2020,14/10/2020"; 
		String seasonEndDate = "13/10/2020,15/10/2020";
		String isMonDay = "yes,yes"; 
		String isTueDay = "yes,yes";
		String isWednesDay = "yes,yes";
		String isThursDay = "yes,yes"; 
		String isFriday = "yes,yes"; 
		String isSaturDay = "yes,no"; 
		String isSunDay = "yes,yes";
		String isAdditionalChargesForChildrenAdults = "No,Yes"; 
		String ratePerNight = "100.25|200,50|20";
		String maxAdults = "2|3,3|2"; 
		String maxPersons = "3|4,4|3";
		String additionalAdultsPerNight = "2|3,3|2"; 
		String additionalChildPerNight = "1|2,2|1"; 
		String isAddRoomClassInSeason = "No,Yes";
		String extraRoomClassesInSeason = "#3 - Twin Beds,N Room1";
		String extraRoomClassRatePerNight = "300,200";
		String extraRoomClassMaxAdults = "3,2";
		String extraRoomClassMaxPersons = "4,3";
		String extraRoomClassAdditionalAdultsPerNight = "2,1";
		String extraRoomClassAdditionalChildPerNight = "1,2";
		String isSeasonLevelRules = "YES,YES"; 
		String isAssignRulesByRoomClass = "NO,NO";
		String seasonRuleSpecificRoomClasses = "Double Bed Room,Double Bed Room";
		String seasonRuleType = "Min nights|No check-in,Min nights|No check-in|No check-out"; 
		String seasonRuleMinStayValue = "3,3";
		String isSeasonRuleOnMonday = "yes,yes|yes";
		String isSeasonRuleOnTuesday = "yes,yes|yes";
		String isSeasonRuleOnWednesday = "no,yes|yes";
		String isSeasonRuleOnThursday = "no,yes|yes";
		String isSeasonRuleOnFriday = "no,yes|yes"; 
		String isSeasonRuleOnSaturday = "yes,yes|no";
		String isSeasonRuleOnSunday = "yes,yes|no"; 
		String isSeasonPolicies = "FALSE,FALSE";
		String seasonPolicyType = "Cancellation|Deposit,Deposit"; 
		String seasonPolicyValues = "CancellationPolicy0Z5rCuavdI|DepositPolicy8kkUb04xb5,DepositPolicy8kkUb04xb5";
		String isAssignPoliciesByRoomClass = "yes,no"; 
		String seasonPolicySpecificRoomClasses = "Double Bed Room,Double Bed Room"; 
		String isProRateStayInSeason = "FALSE,TRUE";
		String isProRateInRoomClass = "TRUE,TRUE"; 
		String isCustomPerNight = "FALSE,TRUE";
		String customRoomClasses = "Double Bed Room,Double Bed Room"; 
		String customRatePerNight = "30,30";
		String isCustomRatePerNightAdultandChild = "True,FALSE"; 
		String customRateChildPerNight = "10,20";
		String customRateAdultdPerNight = "5,10";
		String roomClassesWithProRateUnchecked = "no,no";

		String derivedRatePlanName = "DerivedRate";
		String derivedRatePlanFolioDisplayName = "FolioDerivedrate"; 
		String derivedRatePlanDescription = "Derived Rate Plan Desc";
		String istakenRulesFromParentRateplan = "TRUE"; 
		String dateRange = "Custom date range";
		String customStartDate = "12/10/2020|14/10/2020";
		String customEndDate = "13/10/2020|15/11/2020";
		String selectComparator = "greater than";
		String derivedRateType = "percent";				
		String derivedRateValue = "10"; 
		String derivedRateRoomClasses = "Select All";
		String derivedRateChannels = "innCenter"; 
		String derivedRateIsRatePlanRistrictionReq = "TRUE";
		String derivedRateRistrictionType = "Length of stay|Booking window|Promo code";
		String derivedRateIsMinStay = "FALSE";
		String derivedRateMinNights = "2"; 
		String derivedRateIsMaxStay = "TRUE";
		String derivedRateMaxNights = "2";
		String derivedRateIsMoreThanDaysReq = "TRUE";
		String derivedRateMoreThanDaysCount = "2";
		String derivedRateIsWithInDaysReq = "TRUE";
		String derivedRateWithInDaysCount = "2"; 
		String derivedRatePromoCode = "abc123";
		String derivedRateIsPolicesReq = "FALSE";
		String derivedRatePoliciesType = "Cancellation|Deposit"; 
		String derivedRatePoliciesName = "CancellationPolicy0Z5rCuavdI|DepositPolicy8kkUb04xb5";
		String deleteRatePlan = "TRUE";
		
		
		Utility.DELIM = "\\" + delim;

		Utility.SEASONDELIM = seasonDelim;

		app_logs.info("season Delim" + seasonDelim);

		app_logs.info("Utility.SeasonDelim" + Utility.SEASONDELIM);
		app_logs.info("Room Classes " + parentRoomClass.split(Utility.DELIM));

		app_logs.info("Room Classes " + parentRoomClass.split(Utility.DELIM)[0]);

		app_logs.info(" Delim" + delim);

		app_logs.info("Utility.Delim" + Utility.DELIM);

		app_logs.info("Season StartDAte" + seasonStartDate);

		test_name = "CreateDerivedRatePlanFromParentRatePlanFlyTest";
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

		parentRatePlanName = parentRatePlanName + randomString;
		parentRateFolioName = parentRateFolioName + randomString;
		parentRateDescription = parentRateDescription + randomString;
		String selectedDate = null;
		String seasonDuration = "2";
		String customDateRange = "Custom date range";
		String calendarTodayDay = "today";
		String seasonDateFormat = "dd/M/yyyy";
		String calendarSelectedDay = "selected";
		String todayDate = null;
		String currencySign = "";
		String currencyName = "";
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
			selectedDate = ratesGrid.getCalendarDate(driver, "selected", seasonDateFormat, getTestSteps);
			if (Utility.compareDates(selectedDate, todayDate, seasonDateFormat) > 0) {
				todayDate = selectedDate;
			}
			app_logs.info("Today date : " + todayDate);
			app_logs.info("Excel Sheet start Date : " + seasonStartDate);
			app_logs.info("Excel Sheet end Date : " + seasonEndDate);
			testSteps.add("Excel Sheet start Date : " + seasonStartDate);
			testSteps.add("Excel Sheet end Date : " + seasonEndDate);
			if (dateRange.equals(customDateRange)) {
				app_logs.info("Excel Sheet custom start Date : " + customStartDate);
				app_logs.info("Excel Sheet custom end Date : " + customEndDate);

				testSteps.add("Excel Sheet custom start Date : " + customStartDate);
				testSteps.add("Excel Sheet custom end Date : " + customEndDate);
			}

			if (Utility.compareDates(seasonStartDate.split(Utility.SEASONDELIM)[0], todayDate, seasonDateFormat) >= 0) {

			} else {
				seasonStartDate = todayDate;
				seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), seasonDateFormat, seasonStartDate,
						seasonDateFormat, timeZone);

				customStartDate = seasonStartDate;
				customEndDate = seasonEndDate;
			}
			if (dateRange.equals(customDateRange)) {
				if (Utility.compareDates(customStartDate.split(Utility.DELIM)[0], todayDate, seasonDateFormat) >= 0) {

				} else {
					customStartDate = seasonStartDate;
					customEndDate = seasonEndDate;
				}

			}

			app_logs.info("Selected start Date : " + seasonStartDate);
			app_logs.info("Selected end Date : " + seasonEndDate);
			testSteps.add("Selected start Date : " + seasonStartDate);
			testSteps.add("Selected end Date : " + seasonEndDate);
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

		// Create Derived rate Using Single Method
		try {
			getTestSteps.clear();
			getTestSteps = derivedRate.createDerivedRatePlanFromParentRatePlan(driver, delim, parentRatePlanType, parentRatePlanName,
					parentRateFolioName, parentRateDescription, parentRoomClass, parentRoomClassSize, parentBaseAmount,
					addtionalAdult, additionalChild, intervalRatePlanIntervalValue, isDefaultProrateChecked,
					packageRatePlanRateType, packageRatePlanProductName, packageratePlanProductFirstCalculationMethod,
					packageratePlanProductsecondCalculationMethod, packageRatePlanProductAmount, channels,
					isRatePlanRistrictionReq, ristrictionType, isMinStay, minNights, isMaxStay, maxNights,
					isMoreThanDaysReq, moreThanDaysCount, isWithInDaysReq, withInDaysCount, promoCode, isPolicesReq,
					policiesType, policiesName, seasonDelim, seasonName, seasonStartDate, seasonEndDate, isMonDay,
					isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
					isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight,
					additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason,
					extraRoomClassRatePerNight, extraRoomClassMaxAdults, extraRoomClassMaxPersons,
					extraRoomClassAdditionalAdultsPerNight, extraRoomClassAdditionalChildPerNight, isSeasonLevelRules,
					isAssignRulesByRoomClass, seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue,
					isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday,
					isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday, isSeasonPolicies,
					seasonPolicyType, seasonPolicyValues, isAssignPoliciesByRoomClass, seasonPolicySpecificRoomClasses,
					isProRateStayInSeason, isProRateInRoomClass, isCustomPerNight, customRoomClasses,
					customRatePerNight, isCustomRatePerNightAdultandChild, customRateChildPerNight,
					customRateAdultdPerNight, roomClassesWithProRateUnchecked, derivedRatePlanName,
					derivedRatePlanFolioDisplayName, derivedRatePlanDescription, istakenRulesFromParentRateplan,
					dateRange, customStartDate, customEndDate, selectComparator, derivedRateType, derivedRateValue,
					derivedRateRoomClasses, derivedRateChannels, derivedRateIsRatePlanRistrictionReq,
					derivedRateRistrictionType, derivedRateIsMinStay, derivedRateMinNights, derivedRateIsMaxStay,
					derivedRateMaxNights, derivedRateIsMoreThanDaysReq, derivedRateMoreThanDaysCount,
					derivedRateIsWithInDaysReq, derivedRateWithInDaysCount, derivedRatePromoCode,
					derivedRateIsPolicesReq, derivedRatePoliciesType, derivedRatePoliciesName);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to  create derived rate Plan Using Single Method", testName,
						"CreateDerivedratePlanUsingSingleMethod", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate Plan Using Single Method", testName,
						"CreateDerivedratePlanUsingSingleMethod", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Derived rate Plan
		try {
			if (Boolean.parseBoolean(deleteRatePlan)) {
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, derivedRatePlanName);
				testSteps.add("=================== " + "DELETE PARENT " + parentRatePlanType.toUpperCase()
						+ " ======================");
				app_logs.info("=================== " + "DELETE PARENT " + parentRatePlanType.toUpperCase()
						+ " ======================");
				getTestSteps.clear();
				getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, parentRatePlanName, "Delete", getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add(
						"Successfully Delete  " + parentRatePlanType.toUpperCase() + " '" + parentRatePlanName + "'");
				app_logs.info(
						"Successfully Delete " + parentRatePlanType.toUpperCase() + " '" + parentRatePlanName + "'");
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
							+ parentRatePlanType.replaceAll(" ", ""),

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
		// driver.quit();
	}
}
