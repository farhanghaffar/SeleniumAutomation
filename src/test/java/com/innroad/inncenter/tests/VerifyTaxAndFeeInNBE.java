package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyTaxAndFeeInNBE extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String testName = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyTaxAndFeeInNBE(String delim, String testCaseName,
			String taxOrFee, String ledgerAccountName, String subTypeOfLedgerAccount, String taxName,
			String displayName, String Status, String description, String flateOrPercentage,
			String feeCalculationMethod, String value, String isExclude, String isVat, String applyOnLedgerAccounts,
			String isApplyOnAccountFolio, String restricationForRoomClassSourceAndRatePlan, String checkInDate,
			String checkOutDate, String RatePlanName, String roomClassName, String source, String productName,
			String productQuentity,String cases) throws Exception {
				
		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"CreateNightlyRatePlanV2");
		String FolioDisplayName = ratePlanData.get("FolioDisplayName");
		String Description = ratePlanData.get("Description");
		String Channels = ratePlanData.get("Channels");
		String RoomClasses = ratePlanData.get("RoomClasses");
		String isRatePlanRistrictionReq = ratePlanData.get("isRatePlanRistrictionReq");
		String RistrictionType = ratePlanData.get("RistrictionType");
		String isMinStay = ratePlanData.get("isMinStay");
		String MinNights = ratePlanData.get("MinNights");
		String isMaxStay = ratePlanData.get("isMaxStay");
		String MaxNights = ratePlanData.get("MaxNights");
		String isMoreThanDaysReq = ratePlanData.get("isMoreThanDaysReq");
		String MoreThanDaysCount = ratePlanData.get("MoreThanDaysCount");
		String isWithInDaysReq = ratePlanData.get("isWithInDaysReq");
		String WithInDaysCount = ratePlanData.get("WithInDaysCount");
		String PromoCode = ratePlanData.get("PromoCode");
		String isPolicesReq = ratePlanData.get("isPolicesReq");
		String PoliciesType = ratePlanData.get("PoliciesType");
		String PoliciesName = ratePlanData.get("PoliciesName");
		String SeasonName = ratePlanData.get("SeasonName");
		String SeasonStartDate = ratePlanData.get("SeasonStartDate");
		String SeasonEndDate = ratePlanData.get("SeasonEndDate");
		String isMonDay = ratePlanData.get("isMonDay");
		String isTueDay = ratePlanData.get("isTueDay");
		String isWednesDay = ratePlanData.get("isWednesDay");
		String isThursDay = ratePlanData.get("isThursDay");
		String isFriday = ratePlanData.get("isFriday");
		String isSaturDay = ratePlanData.get("isSaturDay");
		String isSunDay = ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String RatePerNight = ratePlanData.get("RatePerNight");
		String MaxAdults = ratePlanData.get("MaxAdults");
		String AdditionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSerasonLevelRules = ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass = ratePlanData.get("isAssignRulesByRoomClass");
		String SeasonRuleSpecificRoomClasses = ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String SeasonRuleType = ratePlanData.get("SeasonRuleType");
		String SeasonRuleMinStayValue = ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday = ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday = ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday = ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday = ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday = ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday = ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday = ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies = ratePlanData.get("isSeasonPolicies");
		String SeasonPolicyType = ratePlanData.get("SeasonPolicyType");
		String SeasonPolicyValues = ratePlanData.get("SeasonPolicyValues");
		String MaxPersons = ratePlanData.get("MaxPersons");

		ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx", "InteralRPForNBE");

		String intervalRatePlanName = ratePlanData.get("RatePlanName");
		String intervalFolioName = ratePlanData.get("FolioName");
		String intervalDescription = ratePlanData.get("Description");
		String interval = ratePlanData.get("Inteval");
		String IsProRateStayInRate = ratePlanData.get("IsProRateStayInRate");
		String isProRateStayInSeason = ratePlanData.get("ProRateStayInSeason");
		String isProRateInRoomClass = ratePlanData.get("isProRateInRoomClass");
		String ProRateRoomClassName = ratePlanData.get("ProRateRoomClassName");
		String IsCustomPerNight = ratePlanData.get("IsCustomPerNight");
		String CustomeRoomClass = ratePlanData.get("CustomeRoomClass");
		String CustomRatePerNight = ratePlanData.get("CustomRatePerNight");
		String isCustomRatePerNightAdultandChild = ratePlanData.get("IsCustomRatePerAdditionalAdultandChild");
		String CustomRateAdultPerNight = ratePlanData.get("CustomRateAdultPerNight");
		String CustomRateChildPerNight = ratePlanData.get("CustomRateChildPerNight");
		String isAssignPolicyByRoomClass = ratePlanData.get("isAssignPolicyByRoomClass");
		String RoomClassInPolicy = ratePlanData.get("RoomClassInPolicy");

		String timeZone = "";
		String channelName = source;
		String ratePlanType = "";
		String promoCode = "";
		String ratePlanPromoCode = "";
		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		//Utility.DELIM = "\\" + delim;
		testName = testCaseName;
		String feeName = "testFee";
		
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> scriptName = new ArrayList<>();
		ArrayList<String> testCategory = new ArrayList<>();
		ArrayList<String> testDescription = new ArrayList<>();

		String test_description = "";
		String test_catagory = "";

		test_description = testCaseName;
		test_catagory = "TaxAndFees";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid rateGrid = new RatesGrid();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		BookingEngine bookingEngine = new BookingEngine();
		Properties properties = new Properties();
		TaxesAndFee taxFees=new TaxesAndFee();
		Login login = new Login();
		Tax tax = new Tax();
		String dateFormat = "dd/MM/yyyy";
		boolean israteplanExist = false;
		String propertyName="";
		int numberOfProperties = 0;
		
		boolean ratePlanAvailableInBE = true;
	    ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;
		boolean selectedRatePlanDefaultStatus = false;
		String selectedRatePlanFolioName = null;
		String dateWithoutSeason = "";
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		/*if (Utility.rateplanName.equals("")) {
			Utility.rateplanName = RatePlanName;
		}
		else {
			RatePlanName = Utility.rateplanName;

		}*/
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			try {
				caseId.clear();
				statusCode.clear();
				String[] casesList = cases.split(",");
				for (int i = 0; i < casesList.length; i++) {
					caseId.add(casesList[i]);
					statusCode.add("4");
				}
				driver = getDriver();
				login_Group(driver);
				
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				//loginRateV2(driver);
				login_Group(driver);
			}
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================GET THE PROPERTY TIMEZONE ======================");
			app_logs.info("================= GET THE PROPERTY TIMEZONE ======================");
			navigation.Setup(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");
			navigation.Properties(driver);
			app_logs.info("Navigate Properties");
			testSteps.add("Navigate Properties");
			propertyName = properties.getPropertyName(driver, 1);
			testSteps.add("Property Name : " + propertyName);
			app_logs.info("Property Name : " + propertyName);
			numberOfProperties = properties.getNumberOfProperties(driver);
			testSteps.add("Number of Properties : " + numberOfProperties);
			app_logs.info("Number of Properties : " + numberOfProperties);
			try {
				navigation.openProperty(driver, testSteps, propertyName);
			} catch (Exception e) {
				navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
			}
			testSteps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			navigation.clickPropertyOptions(driver, testSteps);
			timeZone = navigation.get_Property_TimeZone(driver);
			testSteps.add("Property TimeZone: " + timeZone);
			navigation.Reservation_Backward(driver);
			String getCurrentDate = Utility.getNextDate(0, "dd/MM/yyyy", timeZone);
			checkInDate = getCurrentDate;
			checkOutDate = Utility.getNextDate(days, "dd/MM/yyyy", timeZone);
			app_logs.info("Check-in Date : " + checkInDate);
			app_logs.info("Check-out Date : " + checkOutDate);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get time zone from property", "Property", "Property", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed get time zone from property", "Property", "Property", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== Verify Rate plan Exist or Not ======================");
			app_logs.info("=================== Verify Rate plan Exist or Not ======================");
			navigation.Inventory(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			getTestSteps.clear();
			getTestSteps = navigation.ratesGrid(driver);
			testSteps.addAll(getTestSteps);

			try {
				getTestSteps.clear();
				israteplanExist = rateGrid.searchForRatePlanExistingOrNot(driver,getTestSteps, RatePlanName);
			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				israteplanExist = rateGrid.searchForRatePlanExistingOrNot(driver,getTestSteps, RatePlanName);
			}
			testSteps.addAll(getTestSteps);
			app_logs.info("israteplanExist : " + israteplanExist);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (!israteplanExist) {
			testSteps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan ======================");
			try {

				testSteps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, ratePlanType);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", ratePlanType, testSteps);
				Utility.rateplanName = RatePlanName+Utility.generateRandomNumber();
				nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
				nightlyRate.enterRatePlanDescription(driver, Description, testSteps);
				nightlyRate.clickNextButton(driver, testSteps);

				if (ratePlanType.equalsIgnoreCase("Interval rate plan")) {
					ratesGrid.enterInterval(driver, interval);
					ratesGrid.byDefaultProrateCheckbox(driver, true);
					nightlyRate.clickNextButton(driver, getTestSteps);
				}

				testSteps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				RatePlanName = RatePlanName + Utility.generateRandomString();
				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
				nightlyRate.enterRatePlanDescription(driver, Description, testSteps);

				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);

				testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, testSteps);
				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

				testSteps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");
				nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);
				String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
						Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode, testSteps);

				String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						testSteps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), testSteps);
				nightlyRate.clickNextButton(driver, testSteps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), testSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, testSteps);

				if (!ratePlanType.contains("interval")) {
					nightlyRate.createSeason(driver, testSteps, SeasonStartDate, SeasonEndDate, SeasonName, isMonDay,
							isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
							isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
							AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
							ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
							ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
							ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
							SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
							isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday,
							isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType,
							SeasonPolicyValues, isSeasonPolicies);

				} else {
					nightlyRate.createSeasonForIntervalRatePlan(driver, testSteps, SeasonStartDate, SeasonEndDate,
							SeasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
							RoomClasses, isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
							AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
							ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
							ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
							ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
							SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
							isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday,
							isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType,
							SeasonPolicyValues, isSeasonPolicies,

							IsProRateStayInRate, isProRateStayInSeason, isProRateInRoomClass, ProRateRoomClassName,
							IsCustomPerNight, CustomeRoomClass, CustomRatePerNight, isAssignPolicyByRoomClass,
							CustomRateAdultPerNight, CustomRateChildPerNight, isCustomRatePerNightAdultandChild,
							RoomClassInPolicy);

				}

				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.clickSaveAsActive(driver, testSteps);
				Wait.wait30Second();
				testSteps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		boolean isSeasonExist = false;
		try {
			testSteps.add("Navigate Rates");
			app_logs.info("Navigate Rates");
			testSteps.add("=================== GETTING RATE PLAN INFORMATION ======================");
			app_logs.info("=================== GETTING RATE PLAN INFORMATION ======================");
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, RatePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			rateGrid.clickEditIcon(driver, testSteps);
			selectedRatePlanFolioName = rateGrid.getFolioNameOfRatePlan(driver, testSteps);
			Utility.app_logs.info("Rate Plan Folio Name : " + selectedRatePlanFolioName);
			testSteps.add("Rate Plan Folio Name : " + selectedRatePlanFolioName);
			selectedChannels = nightlyRate.getSelectedChannels(driver);
			selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);

			Utility.app_logs.info("Selected Channels : " + selectedChannels);
			testSteps.add("Selected Channels : " + selectedChannels);
			Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
			testSteps.add("Selected Room Classes : " + selectedRoomClasses);

			// verify restrictions

			testSteps.add("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");
			app_logs.info("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");

			selectedRatePlanDefaultStatus = ratesGrid.verifyRatePlanSetToDefault(driver, testSteps, RatePlanName);
			nightlyRate.switchCalendarTab(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);

			//dateWithoutSeason = nightlyRate.getDateWhereSeasonNotExist(driver);
			isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
					Utility.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					Utility.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"));
			if (isSeasonExist) {
				app_logs.info("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
						+ checkOutDate + ")");
				testSteps.add("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
						+ checkOutDate + ")");
				//nightlyRate.clickSaveSason(driver, testSteps);
				//app_logs.info("Close Season");
				ratesGrid.clickOnSaveratePlan(driver);
				Wait.wait5Second();
				ratesGrid.closeOpendTabInMainMenu(driver);

			} else {
				app_logs.info("No Season For Desired Date");
				testSteps.add("No Season For Desired Date");
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		HashMap<String, ArrayList<String>> getRatesAlongWithChildAndAdult = new HashMap<String, ArrayList<String>>();
		ArrayList<String> getRates = new ArrayList<String>();
		try {
			testSteps.add("========== Getting old rates value before bulk rate update ==========");
			getTestSteps.clear();
			navigation.ratesGridInventory(driver);
			getTestSteps = ratesGrid.clickOnCalendarIcon(driver);
			testSteps.addAll(getTestSteps);
			Utility.selectDateFromDatePicker(driver, checkInDate, "dd/MM/yyyy");
			Wait.waitUntilPageLoadNotCompleted(driver, 50);

			for (int j = 0; j < 1; j++) {
				//ratesGrid.selectRatePlan(driver, RatePlanName, testSteps);
				rateGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, RatePlanName);
				app_logs.info("select rate plan: " + RatePlanName);

				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				for (int k = 0; k < 1; k++) {
					testSteps.add("Select room class: " + roomClassName);
					ratesGrid.expandRoomClass1(driver, testSteps, roomClassName);

					for (int l = 0; l < 1; l++) {
						ratesGrid.expandChannel(driver, testSteps, roomClassName, channelName);
						getRatesAlongWithChildAndAdult = ratesGrid.getRateExAdExChForChannel(driver, roomClassName,
								channelName, days);
						ratesGrid.clickMinusChannel(driver, roomClassName, channelName);
						getRates.addAll(getRatesAlongWithChildAndAdult.get("rates"));

					}
					ratesGrid.collapseRoomClass(driver, testSteps, roomClassName);
					app_logs.info("collapse roomClass");

				}
			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates from rates grid", "ratesgrid", "ratesgrid", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates from rates grid", "ratesgrid", "ratesgrid", driver);

			}
		}

		// create new tax or fee
		ArrayList<String> listOfTax = new ArrayList<String>();
		ArrayList<String> listOfFee = new ArrayList<String>();
		HashMap<String, String> percentValue = new HashMap<String, String>();
		HashMap<String, String> percentValueForFee = new HashMap<String, String>();
		String[] strFeeCalculationMethod = null;
		ArrayList<String> listOfCHargeFee = new ArrayList<String>();
		String[] arrTaxorFee = taxOrFee.split(Utility.DELIM);
		String[] strValueSplit = value.split(Utility.DELIM);
		String[] flatOrPercent=flateOrPercentage.split(Utility.DELIM);
		String[] strSubTypeledgerAccount = subTypeOfLedgerAccount.split(Utility.DELIM);
		ArrayList<String> LedgerAccountForFee = new ArrayList<String>();

		try {
			navigation.navigateToSetupfromRateGrid(driver);
			testSteps.add("Navigate to setup");

			tax.clickOnTaxButton(driver);
			testSteps.add("Navigate to taxes page");

			ArrayList<String> taxesList = tax.getExistingAvailableTaxesName(driver);
			app_logs.info("Already available Tax :" + taxesList);
			testSteps.add("Already available Tax :" + taxesList);

			/*for (String taxNames : taxesList) {
				getTestSteps.clear();
				getTestSteps = tax.delete_AlreadyExistingTax(driver, taxNames);
				testSteps.addAll(getTestSteps);
			}*/
			taxFees.deleteAllTaxesAndFee(driver, testSteps);

			boolean isApplyRestrictionOrNot = false;
			if (restricationForRoomClassSourceAndRatePlan.equalsIgnoreCase("yes")) {
				isApplyRestrictionOrNot = true;
			}

			
			String taxFee = "";
			String[] strFlatOrPercentage = flateOrPercentage.split(Utility.DELIM);
			strFeeCalculationMethod = feeCalculationMethod.split(Utility.DELIM);
			String[] strledgerAccountName = ledgerAccountName.split(Utility.DELIM);
			

			for (int i = 0; i < arrTaxorFee.length; i++) {
				String USDOrPercentage = "USD";
				boolean isFlatOrPercentage = false;
				if (strFlatOrPercentage[i].equalsIgnoreCase("percentage")) {
					isFlatOrPercentage = true;
					USDOrPercentage = "Percentage";
				}

				taxFee = arrTaxorFee[i];
				if (taxFee.equalsIgnoreCase("tax")) {
					getTestSteps.clear();
					getTestSteps = tax.clickOnCreateTaxButton(driver);
					testSteps.addAll(getTestSteps);
					String getNumber = Utility.generateRandomNumber();
					taxName = taxName.trim();
					taxName = taxName + getNumber;
					displayName = displayName + getNumber;
					description = description + getNumber;
					listOfTax.add(taxName);
					if (!Boolean.parseBoolean(isExclude) && !Boolean.parseBoolean(isVat)) {
						/*tax.createNewTax(driver, taxName.trim(), taxName, taxName, strValueSplit[i], strledgerAccountName[i],
								subTypeOfLedgerAccount, false, isFlatOrPercentage, false, isApplyRestrictionOrNot,
								roomClassName, RatePlanName, channelName);*/
						
						tax.createNewTax(driver,taxName.trim(), taxName, taxName, strValueSplit[i], strledgerAccountName[i],
								strSubTypeledgerAccount[i], false, isFlatOrPercentage,false,isApplyRestrictionOrNot,
								roomClassName.trim(), RatePlanName.trim(), channelName.trim());
					}
					if (Boolean.parseBoolean(isExclude) && !Boolean.parseBoolean(isVat)) {
						tax.createNewTax(driver, taxName.trim(), taxName, taxName, strValueSplit[i], strledgerAccountName[i],
								strSubTypeledgerAccount[i], true, isFlatOrPercentage, false, isApplyRestrictionOrNot,
								roomClassName.trim(), RatePlanName.trim(), channelName.trim());
					}
					if (!Boolean.parseBoolean(isExclude) && Boolean.parseBoolean(isVat)) {
						/*tax.createNewTax(driver, taxName.trim(), taxName, taxName, strValueSplit[i],
								strledgerAccountName[i], subTypeOfLedgerAccount.trim(), false, isFlatOrPercentage,
								true, isApplyRestrictionOrNot, roomClassName, RatePlanName,
								channelName.trim());*/
						tax.createNewTax(driver,taxName.trim(), taxName, taxName, strValueSplit[i], strledgerAccountName[i],
								strSubTypeledgerAccount[i], false, isFlatOrPercentage,true,isApplyRestrictionOrNot,
								roomClassName.trim(), RatePlanName.trim(), channelName.trim());
					
					}
					if (Boolean.parseBoolean(isExclude) && Boolean.parseBoolean(isVat)) {
						tax.createNewTax(driver, taxName.trim(), taxName, taxName, strValueSplit[i], ledgerAccountName,
								strledgerAccountName[i], true, isFlatOrPercentage, true, isApplyRestrictionOrNot,
								roomClassName, RatePlanName, "");
					}

					percentValue.put(taxName, strValueSplit[i]);
					testSteps.add("Craete new tax item");

				}
				Wait.wait5Second();
				if (taxFee.contains("fee") || taxFee.contains("Fee")) {
					getTestSteps.clear();
					getTestSteps = tax.clickOnCreateFeeButton(driver);
					testSteps.addAll(getTestSteps);

					String getNumber = Utility.generateRandomNumber();
					feeName = feeName.trim();
					feeName = feeName + getNumber;
					displayName = displayName + getNumber;
					description = description + getNumber;
					listOfFee.add(feeName);

					String getCalculationMethod = "";
					if (strFeeCalculationMethod.length > 1) {
						getCalculationMethod = strFeeCalculationMethod[i];
					} else {
						getCalculationMethod = strFeeCalculationMethod[0];
					}
					getTestSteps.clear();
					getTestSteps = tax.createNewFee(driver, feeName.trim(), feeName.trim(), feeName.trim(),
							strValueSplit[i], strledgerAccountName[i], isFlatOrPercentage, getCalculationMethod);
					LedgerAccountForFee.add(strledgerAccountName[i]);
					testSteps.addAll(getTestSteps);
					testSteps.add("Created Fee with " + USDOrPercentage + " and amount: " + "<b>" + strValueSplit[i]
							+ "</b>");
					percentValueForFee.put(feeName, strValueSplit[i]);

				}
			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new tax or fee", "Tax", "Tax", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new tax or fee", "Tax", "Tax", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== VERIFY RATE PLAN IS SELECTED IN BOOKING ENGINE ======================");
			app_logs.info("=================== VERIFY RATE PLAN IS SELECTED BOOKING ENGINE ======================");

			//navigation.navSetup(driver);
			//testSteps.add("Navigate Setup");
			//app_logs.info("Navigate Setup");
			navigation.Properties(driver);
			testSteps.add("Navigate Properties");
			app_logs.info("Navigate Properties");

			navigation.openProperty(driver, testSteps, propertyName);
			testSteps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			navigation.clickPropertyOptions(driver, testSteps);

			bookingEngine.clickOnBookingEngineConfigLink(driver);
			testSteps.add("Opened the Booking Engine Configuration");
			app_logs.info("Opened the Booking Engine Configuration");
			testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
			app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(0));
			driver.close();
			driver.switchTo().window(tabs2.get(1));

			getTestSteps.clear();
			boolean ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, RatePlanName, getTestSteps);

			if (ratePlanIsSelectedOrNot) {
				testSteps.add(
						"Successfully Verified that '" + RatePlanName + "' is already selected in Booking Engine.");
				app_logs.info(
						"Successfully Verified that '" + RatePlanName + "' is already selected in Booking Engine.");
			} else {
				testSteps.add("Successfully Verified that '" + RatePlanName + "' is not selected in Booking Engine.");
				app_logs.info("Successfully Verified that '" + RatePlanName + "' is not selected in Booking Engine.");
			}
			bookingEngine.clickWelcomePageLink(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String reservationNumber = "";
		double totalAmount = 0.00;
		double totalTax = 0.00;
		double rate = Double.parseDouble(getRates.get(0));
		double totalFee = 0.00;

		if (isSeasonExist) {
		try {

      		String firstName = "NBE Res";
			String lastName = "Res123";
			String emailAddress = "innroadautomation@innroad.com";
			String phone = "1234567891";
			String address = "New York";
			String postalCode = "New York";
			String city = "New York";
			String state = "New York";
			String cardNumber = "5454545454545454";
			String cardName = "MC";
			String cardExpDate = "12/29";
			String cvv = "123";
			String adults = "2";
			String child = "0";
			getTestSteps.clear();
			//getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
			bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate, false);
			testSteps.addAll(getTestSteps);

			testSteps.add("Select Check-In Date : " + checkInDate);
			app_logs.info("Select Check-In Date : " + checkInDate);

			testSteps.add("Select Check-Out Date : " + checkOutDate);
			app_logs.info("Select Check-Out Date : " + checkOutDate);
			getTestSteps.clear();
			getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.enterChildrenBE(driver, child);
			testSteps.addAll(getTestSteps);

			if (promoCode.contains("yes")) {
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterPromoCode(driver, ratePlanPromoCode);
				testSteps.addAll(getTestSteps);
			}

			bookingEngine.clickSearchOfRooms(driver);
			testSteps.add("Click on search for rooms button");

			getTestSteps.clear();
			getTestSteps = bookingEngine.selectRoomClass(driver, roomClassName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyRatePlanExist(driver, RatePlanName, ratePlanAvailableInBE);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickBookRoom(driver, RatePlanName);
			testSteps.addAll(getTestSteps);

			int numberOfProduct = 0;
			numberOfProduct = productName.split(Utility.DELIM).length;
			for (int i = 0; i < arrTaxorFee.length; i++) {
				if (arrTaxorFee[i].contains("Tax") || arrTaxorFee[i].contains("tax")) {
					String getAppliedTax = percentValue.get(listOfTax.get(i));

					if (flatOrPercent[i].contains("Percentage") || flatOrPercent[i].contains("percenatage")) {

						if (isVat.equalsIgnoreCase("true")) {
							double getPercentage = (Double.parseDouble(strValueSplit[i])/ 100);
							
							double amount = rate / Double.parseDouble("1"+getPercentage);
							amount = Double.parseDouble(String.format("%.2f", amount));
							totalTax = totalTax + amount;

						} else {
							double amount = ((Double.parseDouble(strValueSplit[i])) /100 )* rate;
							amount = Double.parseDouble(String.format("%.2f", amount));
							totalTax = totalTax + amount;
						}
					}

					else {
						totalTax = totalTax + (Double.parseDouble(strValueSplit[i]));
					}
				}

				if (arrTaxorFee[i].equalsIgnoreCase("Fee") || arrTaxorFee[i].equalsIgnoreCase("fee")) {
					
					String getAppliedFee = percentValueForFee.get(listOfFee.get(0));
					String fee = "";
					if (flatOrPercent[i].contains("Percentage") || flatOrPercent[i].contains("percenatage")) {
						double amount = ((Double.parseDouble(strValueSplit[i])) / 100) * rate;
						amount = Double.parseDouble(String.format("%.2f", amount));
						totalFee = totalFee + amount;
						fee = String.format("%.2f", totalFee);
						listOfCHargeFee.add(String.valueOf(amount));

					} else {
						totalFee = totalFee + (Double.parseDouble(strValueSplit[i]));
						listOfCHargeFee.add(strValueSplit[i]);

					}
				}
				
			if (arrTaxorFee[i].equalsIgnoreCase("Fee")) {
	
				if (feeCalculationMethod.equalsIgnoreCase("per night")) {
					totalFee = totalFee * days;
				}
				if (numberOfProduct > 0) {
					totalFee = totalFee * numberOfProduct;
				}		
				
			}
		}
			for (int i = 0; i < arrTaxorFee.length; i++) {
			if (arrTaxorFee[i].equalsIgnoreCase("Fee")) {
				String expectedFee = String.format("%.2f", totalFee);
				String getFee = bookingEngine.getFee(driver);
				testSteps.add("Expected fee : " + expectedFee);
				testSteps.add("Found : " + getFee);
				assertEquals(getFee, expectedFee, "Failed: Fee amount is mismatching!");
				testSteps.add("Verified fees amount");
				}
			

			if (arrTaxorFee[i].contains("Tax") || arrTaxorFee[i].contains("tax")) {

				if (isVat.equalsIgnoreCase("true")) {
					String getSubTotal = bookingEngine.geSubTotal(driver);
					assertEquals(getSubTotal, df.format(rate), "Failed: Sub total is mismaching in!");
					testSteps.add("Verified vat including amount");
				}

				if (isVat.equalsIgnoreCase("true") && isExclude.equalsIgnoreCase("true")) {
					String getSubTotal = bookingEngine.geSubTotal(driver);
					assertEquals(getSubTotal, df.format((rate + totalTax)), "Failed: Sub total is mismaching in!");
					testSteps.add("Verified vat including amount");
				}
			if(!Boolean.parseBoolean(isVat)) {
				if (numberOfProduct > 0) {

					String getTax = bookingEngine.getTax(driver);
					totalTax = totalTax * numberOfProduct;
					String expectedTax = String.format("%.2f", totalTax);
					testSteps.add("Expected tax : " + expectedTax);
					testSteps.add("Found : " + getTax);
					assertEquals(getTax, expectedTax, "Failed:  Tax amount is mismatching!");
					testSteps.add("Verified tax amount");
				} else {

					String getTax = bookingEngine.getTax(driver);
					String expectedTax = String.format("%.2f", totalTax);
					testSteps.add("Expected tax : " + expectedTax);
					testSteps.add("Found : " + getTax);
					assertEquals(getTax, expectedTax, "Failed:  Tax amount is mismatching!");
					testSteps.add("Verified tax amount");

				}
			}

			}
			}

			
			testSteps.add("===== ENTER GUEST INFORMATION =====");
			app_logs.info("===== ENTER GUEST INFORMATION =====");

			getTestSteps.clear();
			getTestSteps = bookingEngine.enterGuestInfo(driver, firstName, lastName, emailAddress, phone, address,
					address, postalCode, city, state);
			testSteps.addAll(getTestSteps);
			testSteps.add("===== ENTER CARD DETAILS =====");
			app_logs.info("===== ENTER CARD DETAILS =====");
			getTestSteps.clear();
			getTestSteps = bookingEngine.enterMcCardInfo(driver, cardNumber, cardName, cardExpDate, cvv);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickBookStay(driver);
			testSteps.addAll(getTestSteps);
			reservationNumber = bookingEngine.getReservationNumber(driver);
			testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
			app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);

			totalAmount = totalFee + totalTax + rate;

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify tax and fee in NBE", "NBE", "NBE", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify tax and fee in NBE", "NBE", "NBE", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();
		Folio folio = new Folio();

		try {
			//loginRateV2(driver);
			login.login(driver,envURL, "autonbe", "autouser", "Auto@123");
			reservationSearch.multipleSearchReservationNumber(driver, reservationNumber);
			testSteps.add("Successfully Search with Multiple Reservation Numbers");

			Wait.wait10Second();
			app_logs.info("reservationNumber: "+reservationNumber);
			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber);
			testSteps.addAll(getTestSteps);

			folio.folioTab(driver);
			subTypeOfLedgerAccount = subTypeOfLedgerAccount.trim();
			for (int i = 1; i<days;i++) {
				
			if (taxOrFee.contains("Tax") || taxOrFee.contains("tax")) {

				getTestSteps.clear();
				getTestSteps = folio.LineItemCategory(driver, strSubTypeledgerAccount[i], 1);
				testSteps.addAll(getTestSteps);

				if (Boolean.parseBoolean(isVat)) {
					String getTax = folio.getLineTaxAmount(driver, strSubTypeledgerAccount[i], 1);
					testSteps.add("Expected tax in folio: " + getTax);
					testSteps.add("Found: " + getTax);
					assertEquals(getTax, getTax, "Failed: tax mismatching in folio!");
					testSteps.add("Verified tax in folio");
				}
				else {
					String expectedTotalTax = String.format("%.2f", totalTax);
					String getTax = folio.getLineTaxAmount(driver, strSubTypeledgerAccount[i], 1);
					testSteps.add("Expected tax in folio: " + expectedTotalTax);
					testSteps.add("Found: " + getTax);
					assertEquals(getTax, expectedTotalTax, "Failed: tax mismatching in folio!");
					testSteps.add("Verified tax in folio");
				}
			}
			}
			for (int i = 1; i <= listOfFee.size(); i++) {
				String getCalculationMethod = LedgerAccountForFee.get(i-1);
				for (int j = 0; j < days; j++) {
					
					getTestSteps.clear();
					getTestSteps = folio.LineItemCategory(driver, getCalculationMethod, 1);
					testSteps.addAll(getTestSteps);
					String getAmount = folio.getAmount(driver, getCalculationMethod, i);
					testSteps.add("Expected fee: " + listOfCHargeFee.get(i-1));
					String feesAmount=Utility.RemoveDollarandSpaces(driver, getAmount);
					testSteps.add("Found : " + getAmount);		
					String chargeFees=String.format("%.2f", Double.parseDouble(listOfCHargeFee.get(i-1)));
					assertEquals(feesAmount,chargeFees , "Failed: fee is mismatching!");
					testSteps.add("Verified fee in folio");
				}
				}			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify tax verification in folio", "Reservations", "Reservations",
						driver);

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify tax verification in folio", "Reservations", "Reservations",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}else {
			testSteps.add("No season available for the given dates");
		}
		try {
			String[] testcase = cases.split(",");
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			Utility.updateTestCase(driver, caseId, statusCode,testName,TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify tax verification in folio", "Reservations", "Reservations",
						driver);
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify tax verification in folio", "Reservations", "Reservations",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("CreateTax", BEExcel);
	}

	/*@AfterMethod(alwaysRun = true)
	public void updateTestRailLink() throws MalformedURLException, IOException, APIException {
		System.out.println("In after method");
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,testName,TestCore.TestRail_AssignToID);
		 driver.close();

	}*/
	
	@AfterSuite(alwaysRun = true)
	public void closeDriver()  throws MalformedURLException, IOException, APIException {
		//driver.close();	
	}

}
