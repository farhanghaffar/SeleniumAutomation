package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.RatesGridChannelRatesRules;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyDeriviedIntervalRatePlanReservationV2  extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, envLoginExcel))
			throw new SkipException("Skipping the test - " + test_name);
	}
	DerivedRate derivedRate = new DerivedRate();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 res = new ReservationV2();

	private void getMapData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}

	@Test(dataProvider = "getData")
	public void verifyNightlyRatePlanReservationV2(String delim, String ReservationType, String CheckInDate,
			String CheckOutDate, String adult, String children, String ReservationRoomClasses,
			String RatePlanName,String derivedRatePlanName, String change_checkInDate,
			String change_checkOutDate, String changeOption, String changeRoomClass) throws Exception {
//change_checkInDate		

		Utility.DELIM = delim;

		test_name = "VerifyRateplanReservationV2_" + ReservationType + "_" + CheckInDate + "_" + CheckOutDate;
		test_description = "VerifyRateplanInInncenter";
		test_catagory = "VerifyRateplanInInncenter";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");

		String channelName = "innCenter";
		System.out.println("channelName : " + channelName);
		System.out.println(System.getProperty("user.dir"));
		HashMap<String, String> ratePlanData;
		try {
			ratePlanData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
					"CreateIntervalRatePlan");
		} catch (Exception e) {
			System.out.println("Exception came");
			ratePlanData = Utility.getExcelData(".\\test-data\\CentralparkSanityTestData.xlsx",
					"CreateIntervalRatePlan");
		}

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
		String MaxPerson = ratePlanData.get("MaxPerson");
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

		String interval = ratePlanData.get("Interval");
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
		
		HashMap<String, String> derivedRatePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"CreateDerivedNightlyRateV2");

		String derivedRatePlanFolioDisplayName = derivedRatePlanData.get("derivedRatePlanfolioDisplayName");
		String derivedRatePlanDescription = derivedRatePlanData.get("derivedratePlandescription");
		String DrivedRatePlanValue = derivedRatePlanData.get("DrivedRatePlanValue");
		String PercentORCurrency = derivedRatePlanData.get("PercentORCurrency");
		String GreaterOrLessThan = derivedRatePlanData.get("GreaterOrLessThan");
		String takeRulesFromRateplan = derivedRatePlanData.get("takeRulesFromRateplan");
		String seasonType = derivedRatePlanData.get("seasonType");
		String derivedCustomStartDate = derivedRatePlanData.get("seasonStartDate");
		String derivedCustomEndDate = derivedRatePlanData.get("seasonEndDate");
		String derivedChannels = derivedRatePlanData.get("derivedChannels");
		String derivedRoomClasses = derivedRatePlanData.get("derivedRoomClasses");
		String derivedisRatePlanRistrictionReq = derivedRatePlanData.get("derivedisRatePlanRistrictionReq");
		String derivedRistrictionType = derivedRatePlanData.get("derivedRistrictionType");
		String derivedisMinStay = derivedRatePlanData.get("derivedisMinStay");
		String derivedMinNights = derivedRatePlanData.get("derivedMinNights");
		String derivedisMaxStay = derivedRatePlanData.get("derivedisMaxStay");
		String derivedMaxNights = derivedRatePlanData.get("derivedMaxNights");
		String derivedisMoreThanDaysReq = derivedRatePlanData.get("derivedisMoreThanDaysReq");
		String derivedMoreThanDaysCount = derivedRatePlanData.get("derivedMoreThanDaysCount");
		String derivedisWithInDaysReq = derivedRatePlanData.get("derivedisWithInDaysReq");
		String derivedWithInDaysCount = derivedRatePlanData.get("derivedWithInDaysCount");
		String derivedPromoCode = derivedRatePlanData.get("derivedPromoCode");
		String derivedisPolicesReq = derivedRatePlanData.get("derivedisPolicesReq");
		String derivedPoliciesType = derivedRatePlanData.get("derivedPoliciesType");
		String derivedPoliciesName = derivedRatePlanData.get("derivedPoliciesName");
		
		String currencyName = null;
		String currencySign = "$";
		driver = getDriver();
		loginReportsV2ReservationV2(driver);

		Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		test_name = "Create Rate Plan " + RatePlanName;
		test_description = "Create Rate Plan " + RatePlanName;
		test_steps.clear();
		String ReservationRoomClassesAbbr="";
		NewRoomClassesV2 rc2 = new NewRoomClassesV2();
		if (ReservationType.equalsIgnoreCase("TapeChart")) {
			test_name = test_name+" from TapeChart";
			 ReservationRoomClassesAbbr = rc2.openRoomClassAndGetDataFromRoomTab(driver, test_steps, RoomClasses).get("Abbreviation");
			navigation.Reservation_Backward_3(driver);
		}
		if (!Utility.insertTestName.containsKey(test_name)) {
			Utility.insertTestName.put(test_name, test_name);
			Utility.reTry.put(test_name, 0);
		} else {
			Utility.reTry.replace(test_name, 1);
		}
		
		if (PercentORCurrency.equalsIgnoreCase("currency")) {
			try {

				test_steps.add(
						"=================== NAVIGATE TO CLIENT INFO FOR CURRENCY VERIFICATION ======================");
				app_logs.info(
						"=================== NAVIGATE TO CLIENT INFO FOR CURRENCY VERIFICATION ======================");

				navigation.Admin(driver);
				test_steps.add("Click on admin");
				app_logs.info("Click on admin");

				navigation.clickonClientinfo(driver);
				test_steps.add("Click on client info");
				app_logs.info("Click on client info");

				Admin admin = new Admin();
				getTest_Steps.clear();
				getTest_Steps = admin.clickOnClient(driver, Utility.loginRateV2.get(1));
				test_steps.addAll(getTest_Steps);

				admin.clickOnClientOptions(driver);
				test_steps.add("Click on options");

				String currencyNameAndSign = admin.getSelectedCurrencyNameAndSign(driver);
				test_steps.add("Selecetd currency: " + currencyNameAndSign);
				app_logs.info("Currency: " + currencyNameAndSign);
				currencyName = currencyNameAndSign.split(" ")[0].trim();
				app_logs.info("Currency Name: '" + currencyName + "'");
				currencySign = currencyNameAndSign
						.substring(currencyNameAndSign.indexOf("(") + 1, currencyNameAndSign.indexOf(")") - 1).trim();
				app_logs.info("Currency Sign: '" + currencySign + "'");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get Selected Currency from client info", test_name,
							"GetSelectedCurrency", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get Selected Currency from client info", test_name,
							"GetSelectedCurrency", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		test_steps.add("=================== Verify Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Rate plan Exist or Not ======================");
		boolean israteplanExist = false;
		try {
			Utility.app_logs.info("RatePlanName : " + RatePlanName);
			israteplanExist = rateGrid.isRatePlanExist(driver, RatePlanName, test_steps);
			Utility.app_logs.info("israteplanExist : " + israteplanExist);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (!israteplanExist) {

			test_steps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan ======================");
			// RatePlanName = RatePlanName + Utility.generateRandomStringWithGivenLength(6);
			try {

				test_steps.add("=================== CREATE NEW INTERVAL RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Interval rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Interval rate plan", test_steps);

				test_steps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, RatePlanName, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, Description, test_steps);

				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, test_steps);
				
				test_steps.add("=================== ENTER INTERVAL AND PRO RATE ======================");
				app_logs.info("=================== ENTER INTERVAL AND PRO RATE ======================");
				
				getTest_Steps.clear();
				getTest_Steps = rateGrid.enterInterval(driver, interval);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.byDefaultProrateCheckbox(driver, Boolean.parseBoolean(IsProRateStayInRate));
				test_steps.addAll(getTest_Steps);
				
				nightlyRate.clickNextButton(driver, test_steps);
				
				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, test_steps);
//				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);

//				nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannelss, test_steps);

				test_steps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");
				nightlyRate.selectRoomClasses(driver, RoomClasses, true, test_steps);
				String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, test_steps);

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
						Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode, test_steps);

//				String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);

//				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, test_steps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						test_steps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

//			String seasonStartDate = Utility.getDateFromCurrentDate(0);
//			String seasonEndDate = Utility.getDateFromCurrentDate(5);

			try {
				test_steps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, test_steps);

				nightlyRate.createSeasonForIntervalRatePlan(driver, test_steps, SeasonStartDate, SeasonEndDate,
						SeasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						RoomClasses, isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
						ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
						ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues,
						isSeasonPolicies,

						IsProRateStayInRate, isProRateStayInSeason, isProRateInRoomClass, ProRateRoomClassName,
						IsCustomPerNight, CustomeRoomClass, CustomRatePerNight, isAssignPolicyByRoomClass,
						CustomRateAdultPerNight, CustomRateChildPerNight, isCustomRatePerNightAdultandChild,
						RoomClassInPolicy);

				nightlyRate.clickCompleteChanges(driver, test_steps);
				nightlyRate.selectAsDefaultRatePlan(driver, test_steps, "Yes");
				nightlyRate.clickSaveAsActive(driver, test_steps);
				Wait.wait30Second();
				test_steps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		test_steps.add("=================== VERIFY Derived RATE PLAN EXIST OR NOT ======================");
		app_logs.info("=================== VERIFY Derived RATE PLAN EXIST OR NOT ======================");
		boolean isDerivedRateplanExist = false;
		try {
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);
			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			isDerivedRateplanExist = derivedRate.isDerivedratePlanExist(driver, derivedRatePlanName, test_steps);
			app_logs.info("isDrivedRateplanExist : " + isDerivedRateplanExist);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		if (!isDerivedRateplanExist) {


			try {

				test_steps.add("=================== CREATE DERIVED RATE PLAN ======================");
				app_logs.info("=================== CREATE DERIVED RATE PLAN ======================");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickRateGridAddRatePlan(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
				test_steps.addAll(getTest_Steps);

				nightlyRate.enterRatePlanName(driver, derivedRatePlanName, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, derivedRatePlanFolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, derivedRatePlanDescription, test_steps);

				getTest_Steps.clear();
				getTest_Steps = nightlyRate.clickNextButton(driver);
				test_steps.addAll(getTest_Steps);

				test_steps.add("======== SELECT PARENT RATE PLAN ========");
				app_logs.info("======== SELECT PARENT RATE PLAN ========");
				derivedRate.selectRatePlan(driver, RatePlanName, true, test_steps);

				derivedRate.expandCurrencyValueDropdown(driver, 1);
				test_steps.add("Expand Value Comparison DropDown");
				app_logs.info("Expand Value Comparison DropDown");
				derivedRate.selectDropDownOptions(driver, GreaterOrLessThan, test_steps);
				derivedRate.expandCurrencyValueDropdown(driver, 0);
				test_steps.add("Expand Currency DropDown");
				app_logs.info("Expand Currency DropDown");
				if (PercentORCurrency.equalsIgnoreCase("currency")) {
					derivedRate.selectDropDownOptions(driver, currencyName, test_steps);
				} else if (PercentORCurrency.equalsIgnoreCase("percent")) {
					derivedRate.selectDropDownOptions(driver, PercentORCurrency, test_steps);
				}
				test_steps.add("===== ENTER VALUE =====");
				app_logs.info("===== ENTER VALUE =====");
				derivedRate.enterRateValue(driver, DrivedRatePlanValue, test_steps);

				derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(takeRulesFromRateplan),
						test_steps);
				test_steps.add("CheckBox 'Take rules from parent rate plan' status is '"
						+ Boolean.parseBoolean(takeRulesFromRateplan) + "'");
				app_logs.info("CheckBox 'Take rules from parent rate plan' status is '"
						+ Boolean.parseBoolean(takeRulesFromRateplan) + "'");
				getTest_Steps.clear();
				getTest_Steps = nightlyRate.clickNextButton(driver);
				test_steps.addAll(getTest_Steps);

				test_steps.add("=================== SELECT DATES ======================");
				app_logs.info("=================== SELECT DATES ======================");
				if (seasonType.equalsIgnoreCase("Always available")) {
					derivedRate.selectDates(driver, seasonType, test_steps);
				} else {
					derivedRate.selectDates(driver, seasonType, test_steps);
					derivedRate.customDateRangeAppear(driver, true, test_steps);
					getTest_Steps.clear();
					getTest_Steps = derivedRate.selectCustomStartAndEndDates(driver, derivedCustomStartDate,
							derivedCustomEndDate);
					test_steps.addAll(getTest_Steps);
				}

				getTest_Steps.clear();
				getTest_Steps = nightlyRate.clickNextButton(driver);
				test_steps.addAll(getTest_Steps);

				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
				nightlyRate.selectChannels(driver, derivedChannels, true, test_steps);

				getTest_Steps.clear();
				getTest_Steps = nightlyRate.clickNextButton(driver);
				test_steps.addAll(getTest_Steps);

				test_steps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");
				nightlyRate.selectRoomClasses(driver, derivedRoomClasses, true, test_steps);

				getTest_Steps.clear();
				getTest_Steps = nightlyRate.clickNextButton(driver);
				test_steps.addAll(getTest_Steps);

				test_steps.add("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");
				app_logs.info("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(derivedisRatePlanRistrictionReq),
						derivedRistrictionType, Boolean.parseBoolean(derivedisMinStay), derivedMinNights,
						Boolean.parseBoolean(derivedisMaxStay), derivedMaxNights,
						Boolean.parseBoolean(derivedisMoreThanDaysReq), derivedMoreThanDaysCount,
						Boolean.parseBoolean(derivedisWithInDaysReq), derivedWithInDaysCount, derivedPromoCode,
						test_steps);

				getTest_Steps.clear();
				getTest_Steps = nightlyRate.clickNextButton(driver);
				test_steps.addAll(getTest_Steps);

				test_steps.add("=================== SELECT POLICIES ======================");
				app_logs.info("=================== SELECT POLICIES ======================");

				getTest_Steps.clear();
				nightlyRate.selectPolicy(driver, derivedPoliciesType, derivedPoliciesName,
						Boolean.parseBoolean(derivedisPolicesReq), getTest_Steps);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = nightlyRate.clickNextButton(driver);
				test_steps.addAll(getTest_Steps);

				nightlyRate.clickSaveAsActive(driver, test_steps);

				test_steps.add("=================== DERIVED RATE PLAN CREATED ======================");
				app_logs.info("=================== DERIVED RATE PLAN CREATED ======================");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click save as active", test_name, "ClickSaveAsActive", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click save as active", test_name, "ClickSaveAsActive", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		
		}
		
		int MRBDays = 0;
		HashMap<String, String> roomClassCapacityAdult_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> roomclassCapacityChild_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> ratePlanMaxAdults_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> ratePlanMaxChilds_SeasonLevel = new HashMap<String, String>();
		
		ArrayList<String> allDatesBW = new ArrayList<String>();
		
		
		HashMap<String, String> changeRoomClassCapacityAdult_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> changeRoomclassCapacityChild_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> changeRatePlanMaxAdults_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> changeRatePlanMaxChilds_SeasonLevel = new HashMap<String, String>();
		
		ArrayList<String> changeAllDatesBW = new ArrayList<String>();
		
		boolean isOverAllProRateToggle_SeasonLevel = false;
		boolean isAdditionalAdultChild = false;
		HashMap<String, Boolean> isRoomClassProRateToggle_SeasonLevel = new HashMap<String, Boolean>();
		HashMap<String, String> proRatePerNight_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> proRateExAdult_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> proRateExChild_SeasonLevel = new HashMap<String, String>();
		
		HashMap<String, String> baseRatePerNight_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> baseRateExAdult_SeasonLevel = new HashMap<String, String>();
		HashMap<String, String> baseRateExChild_SeasonLevel = new HashMap<String, String>();
		ArrayList<String> parentRatePlanOffSetList = new ArrayList<String>();
		try {
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_name = "Getting Rate Plan Rates and Capacity : " + RatePlanName;
			test_description = "Getting Rate Plan Rates and Capacity : " + RatePlanName;
			test_steps.clear();
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}
			ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);
			ArrayList<String> checkOutList = Utility.convertTokenToArrayList(CheckOutDate, delim);
			ArrayList<String> roomClassList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);

			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);
			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, test_steps);
			
			parentRatePlanOffSetList = derivedRate.getParentPlanOffset(driver);
			Utility.app_logs.info("Selected OffSet Value  : " + parentRatePlanOffSetList);
			test_steps.add("Selected OffSet Value  : " + parentRatePlanOffSetList);
			
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);

			rateGrid.clickOnEditRatePlan(driver);

			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInList.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);

			isOverAllProRateToggle_SeasonLevel = nightlyRate.getOverAllProRate(driver, roomClassList.get(0));
			isAdditionalAdultChild = nightlyRate.getAdditionalAdultChildtoggle(driver);
			for (String roomClass : roomClassList) {
				roomClassCapacityAdult_SeasonLevel.put(roomClass, nightlyRate.getAdultCapacity(driver, roomClass));
				roomclassCapacityChild_SeasonLevel.put(roomClass, nightlyRate.getChildCapacity(driver, roomClass));
				ratePlanMaxAdults_SeasonLevel.put(roomClass, nightlyRate.getMaxAdult(driver, roomClass));
				ratePlanMaxChilds_SeasonLevel.put(roomClass, nightlyRate.getMaxPersons(driver, roomClass));
				
				isRoomClassProRateToggle_SeasonLevel.put(roomClass, nightlyRate.getProRateStayForRoomClass(driver, roomClass));
				proRatePerNight_SeasonLevel.put(roomClass, nightlyRate.getProRatePerNight(driver, roomClass));
				proRateExAdult_SeasonLevel.put(roomClass, nightlyRate.getProRateExAdult(driver, roomClass));
				proRateExChild_SeasonLevel.put(roomClass, nightlyRate.getProRateExChild(driver, roomClass));
				
				ArrayList<String> tmpList = rateGrid.getRoomClassRateWithAdditionalAdultAndChild(driver, roomClass, test_steps);
				
				baseRatePerNight_SeasonLevel.put(roomClass, derivedRate.calculateOffSet(
						tmpList.get(0), parentRatePlanOffSetList.get(0),
						parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
				baseRateExAdult_SeasonLevel.put(roomClass, derivedRate.calculateOffSet(
						tmpList.get(6), parentRatePlanOffSetList.get(0),
						parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
				baseRateExChild_SeasonLevel.put(roomClass, derivedRate.calculateOffSet(
						tmpList.get(7), parentRatePlanOffSetList.get(0),
						parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
			}

			nightlyRate.closeSeason(driver, test_steps);

			rateGrid.closeOpendTabInMainMenu(driver);

			String[] rm = ReservationRoomClasses.split("\\|");
			driver.navigate().refresh();

			
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_name = "Getting Rate Plan Rates and Capacity For Change Stay Details : " + RatePlanName;
			test_description = "Getting Rate Plan Rates and Capacity For Change Stay Details : " + RatePlanName;
			test_steps.clear();
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}

		
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);

			rateGrid.clickOnEditRatePlan(driver);

			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, change_checkInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				changeRoomClassCapacityAdult_SeasonLevel.put(changeRoomClass, nightlyRate.getAdultCapacity(driver, changeRoomClass));
				changeRoomclassCapacityChild_SeasonLevel.put(changeRoomClass, nightlyRate.getChildCapacity(driver, changeRoomClass));
				changeRatePlanMaxAdults_SeasonLevel.put(changeRoomClass, nightlyRate.getMaxAdult(driver, changeRoomClass));
				changeRatePlanMaxChilds_SeasonLevel.put(changeRoomClass, nightlyRate.getMaxPersons(driver, changeRoomClass));
			
				isRoomClassProRateToggle_SeasonLevel.put(changeRoomClass, nightlyRate.getProRateStayForRoomClass(driver, changeRoomClass));
				proRatePerNight_SeasonLevel.put(changeRoomClass, nightlyRate.getProRatePerNight(driver, changeRoomClass));
				proRateExAdult_SeasonLevel.put(changeRoomClass, nightlyRate.getProRateExAdult(driver, changeRoomClass));
				proRateExChild_SeasonLevel.put(changeRoomClass, nightlyRate.getProRateExChild(driver, changeRoomClass));
				
				ArrayList<String> tmpList = rateGrid.getRoomClassRateWithAdditionalAdultAndChild(driver, changeRoomClass, test_steps);
				
				baseRatePerNight_SeasonLevel.put(changeRoomClass, derivedRate.calculateOffSet(
						tmpList.get(0), parentRatePlanOffSetList.get(0),
						parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
				baseRateExAdult_SeasonLevel.put(changeRoomClass, derivedRate.calculateOffSet(
						tmpList.get(6), parentRatePlanOffSetList.get(0),
						parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
				baseRateExChild_SeasonLevel.put(changeRoomClass, derivedRate.calculateOffSet(
						tmpList.get(7), parentRatePlanOffSetList.get(0),
						parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));

			nightlyRate.closeSeason(driver, test_steps);

			rateGrid.closeOpendTabInMainMenu(driver);

			
			driver.navigate().refresh();

		
				changeAllDatesBW = Utility.getAllDatesBetweenTwoDates(change_checkInDate, change_checkOutDate);

		
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String salutation = "Mr.";
		String guestFirstName = "John Wick";
		String guestLastName = Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		String phoneNumber = "1234567899";
		String altenativePhone = "9876543211";
		String email = "innroadautomation@innroad.com";
		String account = "";
		String accountType = "Corporate/Member Accounts";
		String address1 = "Lane1";
		String address2 = "Lane2";
		String address3 = "Lane3";
		String city = "New York";
		String country = "United States";
		String state = "New York";
		String postalCode = "5432";
		boolean isGuesProfile = false;
		String paymentMethod = "Cash";
		String cardNumber = "";
		String nameOnCard = "";
		String cardExpMonthAndYear = "";
		String referral = "Walk In";
		String marketSegment = "GDS";
		int additionalGuests = 0;
		String roomNumber = "";
		boolean quote = false;
		String noteType = "";
		String noteSubject = "";
		String noteDescription = "";
		String taskCategory = "";
		String taskType = "";
		String taskDetails = "";
		String taskRemarks = "";
		String taskDueOn = "";
		String taskAssignee = "";
		String taskStatus = "";
		String accountName = "";
		String accountFirstName = "";
		String accountLastName = "";
		boolean isTaxExempt = false;
		String taxExemptID = "";

		
		try {
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_name = "Create Reservation";
			test_description = "Create Reservation";
			test_steps.clear();
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}
			
			navigation.ReservationV2_Backward(driver);

			String ReservationRate = derivedRatePlanName;
			ArrayList<String> roomClassList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
			if (ReservationType.contains("single")) {
				
				for (String roomclass : roomClassList) {

					res.click_NewReservation(driver, test_steps);
					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, ReservationRate, PromoCode);	
					res.clickOnFindRooms(driver, test_steps);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							CheckInDate, CheckOutDate, adult,
							children, roomclass, interval,
							baseRatePerNight_SeasonLevel.get(roomclass), 
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							isAdditionalAdultChild,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass),
							
							baseRateExAdult_SeasonLevel.get(roomclass),
							baseRateExChild_SeasonLevel.get(roomclass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
							proRatePerNight_SeasonLevel.get(roomclass),
							proRateExAdult_SeasonLevel.get(roomclass),
							proRateExChild_SeasonLevel.get(roomclass),
							FolioDisplayName
							, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}
					res.closeAllOpenedReservations(driver);
				}

				res.createReservation(driver, test_steps, "From Reservations page", CheckInDate, CheckOutDate, adult, children,
						ReservationRate, "", ReservationRoomClasses, ReservationRoomClassesAbbr, salutation,
						guestFirstName, guestLastName, phoneNumber, altenativePhone, email, address1, address2,
						address3, city, country, state, postalCode, isGuesProfile, marketSegment, referral,
						paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, additionalGuests, roomNumber, quote,
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks,
						taskDueOn, taskAssignee, taskStatus, accountName, accountType, accountFirstName,
						accountLastName, isTaxExempt, taxExemptID);

				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_name = "Change Stay Detail";
				test_description = "Change Stay Detail";
				test_steps.clear();
				
				if (!Utility.insertTestName.containsKey(test_name)) {
					Utility.insertTestName.put(test_name, test_name);
					Utility.reTry.put(test_name, 0);
				} else {
					Utility.reTry.replace(test_name, 1);
				}
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				
				app_logs.info(changeRatePlanMaxAdults_SeasonLevel);
				app_logs.info(changeRatePlanMaxChilds_SeasonLevel);
				app_logs.info(changeRoomclassCapacityChild_SeasonLevel);
				app_logs.info(changeRoomClassCapacityAdult_SeasonLevel);
				app_logs.info(adult);
				app_logs.info(children);
				
				app_logs.info(changeCheckInList);
				app_logs.info(changeCheckOutList);
				
				for (int i = 0; i < changeCheckInList.size(); i++) {

					res.clickEditStayInfo(driver);
					res.clickChangeStayDetails(driver);
					res.waitForSweetLoading(driver);
					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
					res.selectStayInfoOption(driver, changeOption);
					res.clickFindRoomsStayInfo(driver);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
							children, changeRoomClass, interval,
							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							isAdditionalAdultChild,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							
							baseRateExAdult_SeasonLevel.get(changeRoomClass),
							baseRateExChild_SeasonLevel.get(changeRoomClass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
							proRatePerNight_SeasonLevel.get(changeRoomClass),
							proRateExAdult_SeasonLevel.get(changeRoomClass),
							proRateExChild_SeasonLevel.get(changeRoomClass),
							FolioDisplayName
							, test_steps);

					for (int j = 0; j < rateListInReservation.size(); j++) {
						app_logs.info(rateListInReservation.get(j));
					}
					res.clickCloseStayInfo(driver);
				}
				
//				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
//				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay(driver, test_steps,
//						changeRoomClass, change_checkInDate,
//						change_checkOutDate, changeOption);
//				obj1.put(changeRoomClass, ratesDetailChangeStay);
//				app_logs.info(obj1);
//				
//				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
//						changeRoomClass, change_checkInDate, test_steps);
//				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("mrb")) {
				ReservationRate = RatePlanName + "|" + RatePlanName;
				guestFirstName = "John Wick|Son Wick";
				guestLastName = Utility.generateRandomNumberWithGivenNumberOfDigits(4) + "|"
						+ Utility.generateRandomNumberWithGivenNumberOfDigits(4);

				String[] adu = adult.split("\\|");
				String[] child = children.split("\\|");
				ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);
				ArrayList<String> checkOutList = Utility.convertTokenToArrayList(CheckOutDate, delim);
				HashMap<String, String> rateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				for (int i = 0; i < roomClassList.size(); i++) {
//					rateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(perNightRatesList_ratesGrid.get(i),
//							ratePlanMaxAdults_SeasonLevel.get(roomClassList.get(i)), ratePlanMaxChilds_SeasonLevel.get(roomClassList.get(i)),
//							exAdultRatesList_ratesGrid.get(i), exChildRatesList_ratesGrid.get(i), roomclassCapacityChild_SeasonLevel.get(roomClassList.get(i)),
//							roomClassCapacityAdult_SeasonLevel.get(roomClassList.get(i)), adu[i], child[i],
//							Utility.getDateRangeBetweenfromAndToDate(
//									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
//											checkInList.get(i)),
//									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
//											checkOutList.get(i))),
//							ratesConfig.getProperty("defaultDateFormat")));
//					roomClassWiseCalculatedRates.put(roomClassList.get(i), rateIs);
					

					String roomclass = roomClassList.get(i);
					res.click_NewReservation(driver, test_steps);
					res.searchDataForFindRooms(driver, test_steps, checkInList.get(i), checkOutList.get(i), adu[i], child[i], RatePlanName, PromoCode);	
					res.clickOnFindRooms(driver, test_steps);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							checkInList.get(i), checkOutList.get(i), adu[i], child[i], roomclass, interval,
							baseRatePerNight_SeasonLevel.get(roomclass), 
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							isAdditionalAdultChild,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass),
							
							baseRateExAdult_SeasonLevel.get(roomclass),
							baseRateExChild_SeasonLevel.get(roomclass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
							proRatePerNight_SeasonLevel.get(roomclass),
							proRateExAdult_SeasonLevel.get(roomclass),
							proRateExChild_SeasonLevel.get(roomclass),
							FolioDisplayName
							, test_steps);

					for (int j = 0; j < rateListInReservation.size(); j++) {
						app_logs.info(rateListInReservation.get(j));
					}
					res.closeAllOpenedReservations(driver);
				
				}
//				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
//						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
//				app_logs.info(obj);
//				Utility.app_logs.info(rateIs);
//
//				Utility.app_logs.info(ratesConfig.getProperty("defaultDateFormat"));
//				
//				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
//						ReservationRoomClasses, CheckInDate, test_steps);
				
				res.createReservation(driver, test_steps, "From Reservations page", CheckInDate, CheckOutDate, adult, children,
						ReservationRate, "", ReservationRoomClasses, ReservationRoomClassesAbbr, salutation,
						guestFirstName, guestLastName, phoneNumber, altenativePhone, email, address1, address2,
						address3, city, country, state, postalCode, isGuesProfile, marketSegment, referral,
						paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, additionalGuests, roomNumber, quote,
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks,
						taskDueOn, taskAssignee, taskStatus, accountName, accountType, accountFirstName,
						accountLastName, isTaxExempt, taxExemptID);

				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_name = "Change Stay Detail";
				test_description = "Change Stay Detail";
				test_steps.clear();
				if (!Utility.insertTestName.containsKey(test_name)) {
					Utility.insertTestName.put(test_name, test_name);
					Utility.reTry.put(test_name, 0);
				} else {
					Utility.reTry.replace(test_name, 1);
				}
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
			
				app_logs.info(changeRatePlanMaxAdults_SeasonLevel);
				app_logs.info(changeRatePlanMaxChilds_SeasonLevel);
				app_logs.info(changeRoomclassCapacityChild_SeasonLevel);
				app_logs.info(changeRoomClassCapacityAdult_SeasonLevel);
				app_logs.info(adu);
				app_logs.info(child);
				
				app_logs.info(changeCheckInList);
				app_logs.info(changeCheckOutList);
				
				for (int i = 0; i < changeCheckInList.size(); i++) {
				
					res.clickChangeStayDetails(driver, 2);
					res.waitForSweetLoading(driver);
					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
					res.selectStayInfoOption(driver, changeOption);
					res.clickFindRoomsStayInfo(driver);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
							children, changeRoomClass, interval,
							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							isAdditionalAdultChild,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							
							baseRateExAdult_SeasonLevel.get(changeRoomClass),
							baseRateExChild_SeasonLevel.get(changeRoomClass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
							proRatePerNight_SeasonLevel.get(changeRoomClass),
							proRateExAdult_SeasonLevel.get(changeRoomClass),
							proRateExChild_SeasonLevel.get(changeRoomClass),
							FolioDisplayName
							, test_steps);

					for (int j = 0; j < rateListInReservation.size(); j++) {
						app_logs.info(rateListInReservation.get(j));
					}
					res.clickCloseStayInfo(driver);
				
				}
				
				
				res.modifyReservationDatesForMRB(driver, test_steps, change_checkInDate, change_checkOutDate,
						changeOption, 2,changeRoomClass);

			} else if (ReservationType.contains("Quote")) {
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {

					res.click_NewReservation(driver, test_steps);
					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
					res.clickOnFindRooms(driver, test_steps);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							CheckInDate, CheckOutDate, adult,
							children, roomclass, interval,
							baseRatePerNight_SeasonLevel.get(roomclass), 
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							isAdditionalAdultChild,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass),
							
							baseRateExAdult_SeasonLevel.get(roomclass),
							baseRateExChild_SeasonLevel.get(roomclass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
							proRatePerNight_SeasonLevel.get(roomclass),
							proRateExAdult_SeasonLevel.get(roomclass),
							proRateExChild_SeasonLevel.get(roomclass),
							FolioDisplayName
							, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}
					res.closeAllOpenedReservations(driver);
				}
				
				quote = true;
				res.createReservation(driver, test_steps, ReservationType, CheckInDate, CheckOutDate, adult, children,
						ReservationRate, "", ReservationRoomClasses, ReservationRoomClassesAbbr, salutation,
						guestFirstName, guestLastName, phoneNumber, altenativePhone, email, address1, address2,
						address3, city, country, state, postalCode, isGuesProfile, marketSegment, referral,
						paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, additionalGuests, roomNumber, quote,
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks,
						taskDueOn, taskAssignee, taskStatus, accountName, accountType, accountFirstName,
						accountLastName, isTaxExempt, taxExemptID);

				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_name = "Change Stay Detail";
				test_description = "Change Stay Detail";
				test_steps.clear();
				if (!Utility.insertTestName.containsKey(test_name)) {
					Utility.insertTestName.put(test_name, test_name);
					Utility.reTry.put(test_name, 0);
				} else {
					Utility.reTry.replace(test_name, 1);
				}
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {

					res.clickEditStayInfo(driver);
					res.clickChangeStayDetails(driver);
					res.waitForSweetLoading(driver);
					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
					res.selectStayInfoOption(driver, changeOption);
					res.clickFindRoomsStayInfo(driver);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
							children, changeRoomClass, interval,
							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							isAdditionalAdultChild,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							
							baseRateExAdult_SeasonLevel.get(changeRoomClass),
							baseRateExChild_SeasonLevel.get(changeRoomClass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
							proRatePerNight_SeasonLevel.get(changeRoomClass),
							proRateExAdult_SeasonLevel.get(changeRoomClass),
							proRateExChild_SeasonLevel.get(changeRoomClass),
							FolioDisplayName
							, test_steps);

					for (int j = 0; j < rateListInReservation.size(); j++) {
						app_logs.info(rateListInReservation.get(j));
					}
					res.clickCloseStayInfo(driver);
				}
				
			
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("TapeChart")) {
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {

					res.click_NewReservation(driver, test_steps);
					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
					res.clickOnFindRooms(driver, test_steps);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							CheckInDate, CheckOutDate, adult,
							children, roomclass, interval,
							baseRatePerNight_SeasonLevel.get(roomclass), 
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							isAdditionalAdultChild,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass),
							
							baseRateExAdult_SeasonLevel.get(roomclass),
							baseRateExChild_SeasonLevel.get(roomclass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
							proRatePerNight_SeasonLevel.get(roomclass),
							proRateExAdult_SeasonLevel.get(roomclass),
							proRateExChild_SeasonLevel.get(roomclass),
							FolioDisplayName
							, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}
					res.closeAllOpenedReservations(driver);
				}
				
				res.createReservation(driver, test_steps, ReservationType, CheckInDate, CheckOutDate, adult, children,
						ReservationRate, "", ReservationRoomClasses, ReservationRoomClassesAbbr, salutation,
						guestFirstName, guestLastName, phoneNumber, altenativePhone, email, address1, address2,
						address3, city, country, state, postalCode, isGuesProfile, marketSegment, referral,
						paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, additionalGuests, roomNumber, quote,
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks,
						taskDueOn, taskAssignee, taskStatus, accountName, accountType, accountFirstName,
						accountLastName, isTaxExempt, taxExemptID);
				
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_name = "Change Stay Detail";
				test_description = "Change Stay Detail";
				test_steps.clear();
				if (!Utility.insertTestName.containsKey(test_name)) {
					Utility.insertTestName.put(test_name, test_name);
					Utility.reTry.put(test_name, 0);
				} else {
					Utility.reTry.replace(test_name, 1);
				}
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {

					res.clickEditStayInfo(driver);
					res.clickChangeStayDetails(driver);
					res.waitForSweetLoading(driver);
					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
					res.selectStayInfoOption(driver, changeOption);
					res.clickFindRoomsStayInfo(driver);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
							children, changeRoomClass, interval,
							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							isAdditionalAdultChild,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							
							baseRateExAdult_SeasonLevel.get(changeRoomClass),
							baseRateExChild_SeasonLevel.get(changeRoomClass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
							proRatePerNight_SeasonLevel.get(changeRoomClass),
							proRateExAdult_SeasonLevel.get(changeRoomClass),
							proRateExChild_SeasonLevel.get(changeRoomClass),
							FolioDisplayName
							, test_steps);

					for (int j = 0; j < rateListInReservation.size(); j++) {
						app_logs.info(rateListInReservation.get(j));
					}
					res.clickCloseStayInfo(driver);
				}
				
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("Group")) {
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {

					res.click_NewReservation(driver, test_steps);
					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
					res.clickOnFindRooms(driver, test_steps);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							CheckInDate, CheckOutDate, adult,
							children, roomclass, interval,
							baseRatePerNight_SeasonLevel.get(roomclass), 
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							isAdditionalAdultChild,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass),
							
							baseRateExAdult_SeasonLevel.get(roomclass),
							baseRateExChild_SeasonLevel.get(roomclass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
							proRatePerNight_SeasonLevel.get(roomclass),
							proRateExAdult_SeasonLevel.get(roomclass),
							proRateExChild_SeasonLevel.get(roomclass),
							FolioDisplayName
							, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}
					res.closeAllOpenedReservations(driver);
				}
				
				res.createReservation(driver, test_steps, ReservationType, CheckInDate, CheckOutDate, adult, children,
						ReservationRate, "", ReservationRoomClasses, ReservationRoomClassesAbbr, salutation,
						guestFirstName, guestLastName, phoneNumber, altenativePhone, email, address1, address2,
						address3, city, country, state, postalCode, isGuesProfile, marketSegment, referral,
						paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, additionalGuests, roomNumber, quote,
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks,
						taskDueOn, taskAssignee, taskStatus, accountName, accountType, accountFirstName,
						accountLastName, isTaxExempt, taxExemptID);
				
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_name = "Change Stay Detail";
				test_description = "Change Stay Detail";
				test_steps.clear();
				if (!Utility.insertTestName.containsKey(test_name)) {
					Utility.insertTestName.put(test_name, test_name);
					Utility.reTry.put(test_name, 0);
				} else {
					Utility.reTry.replace(test_name, 1);
				}
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {

					res.clickEditStayInfo(driver);
					res.clickChangeStayDetails(driver);
					res.waitForSweetLoading(driver);
					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
					res.selectStayInfoOption(driver, changeOption);
					res.clickFindRoomsStayInfo(driver);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
							children, changeRoomClass, interval,
							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							isAdditionalAdultChild,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							
							baseRateExAdult_SeasonLevel.get(changeRoomClass),
							baseRateExChild_SeasonLevel.get(changeRoomClass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
							proRatePerNight_SeasonLevel.get(changeRoomClass),
							proRateExAdult_SeasonLevel.get(changeRoomClass),
							proRateExChild_SeasonLevel.get(changeRoomClass),
							FolioDisplayName
							, test_steps);

					for (int j = 0; j < rateListInReservation.size(); j++) {
						app_logs.info(rateListInReservation.get(j));
					}
					res.clickCloseStayInfo(driver);
				}
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("Account Reservation")) {
				
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {

					res.click_NewReservation(driver, test_steps);
					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
					res.clickOnFindRooms(driver, test_steps);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							CheckInDate, CheckOutDate, adult,
							children, roomclass, interval,
							baseRatePerNight_SeasonLevel.get(roomclass), 
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							isAdditionalAdultChild,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass),
							
							baseRateExAdult_SeasonLevel.get(roomclass),
							baseRateExChild_SeasonLevel.get(roomclass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
							proRatePerNight_SeasonLevel.get(roomclass),
							proRateExAdult_SeasonLevel.get(roomclass),
							proRateExChild_SeasonLevel.get(roomclass),
							FolioDisplayName
							, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}
					res.closeAllOpenedReservations(driver);
				}
				
				accountName = "copAccount7613";
				accountFirstName = "Wick";
				accountLastName = "Account" + Utility.generateRandomNumberWithGivenNumberOfDigits(4);

				res.createReservation(driver, test_steps, ReservationType, CheckInDate, CheckOutDate, adult, children,
						ReservationRate, "", ReservationRoomClasses, ReservationRoomClassesAbbr, salutation,
						guestFirstName, guestLastName, phoneNumber, altenativePhone, email, address1, address2,
						address3, city, country, state, postalCode, isGuesProfile, marketSegment, referral,
						paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, additionalGuests, roomNumber, quote,
						noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks,
						taskDueOn, taskAssignee, taskStatus, accountName, accountType, accountFirstName,
						accountLastName, isTaxExempt, taxExemptID);
				
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_name = "Change Stay Detail";
				test_description = "Change Stay Detail";
				test_steps.clear();
				if (!Utility.insertTestName.containsKey(test_name)) {
					Utility.insertTestName.put(test_name, test_name);
					Utility.reTry.put(test_name, 0);
				} else {
					Utility.reTry.replace(test_name, 1);
				}
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {

					res.clickEditStayInfo(driver);
					res.clickChangeStayDetails(driver);
					res.waitForSweetLoading(driver);
					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
					res.selectStayInfoOption(driver, changeOption);
					res.clickFindRoomsStayInfo(driver);
					
					
					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
							ratesConfig.getProperty("defaultDateFormat"),
							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
							children, changeRoomClass, interval,
							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							isAdditionalAdultChild,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							
							baseRateExAdult_SeasonLevel.get(changeRoomClass),
							baseRateExChild_SeasonLevel.get(changeRoomClass), 
							
							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
							proRatePerNight_SeasonLevel.get(changeRoomClass),
							proRateExAdult_SeasonLevel.get(changeRoomClass),
							proRateExChild_SeasonLevel.get(changeRoomClass),
							FolioDisplayName
							, test_steps);

					for (int j = 0; j < rateListInReservation.size(); j++) {
						app_logs.info(rateListInReservation.get(j));
					}
					res.clickCloseStayInfo(driver);
				}
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyInterDerivedRatePlanResV2", envLoginExcel);
	}

}
