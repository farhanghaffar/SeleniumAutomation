package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.CancellationPolicy;
import com.innroad.inncenter.model.RatesGridChannelRatesRules;
import com.innroad.inncenter.model.ReservationRatesDetail;
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
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyDerivedNightlyRatePlanReservationV2 extends TestCore {
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
			String parentRatePlanName,String derivedRatePlanName, String change_checkInDate,
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
					"CreateNightlyRatePlanV2");
		} catch (Exception e) {
			System.out.println("Exception came");
			ratePlanData = Utility.getExcelData(".\\test-data\\CentralparkSanityTestData.xlsx",
					"CreateNightlyRatePlanV2");
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
		
		driver = getDriver();
		loginReportsV2ReservationV2(driver);

		Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		test_name = "Create Rate Plan " + parentRatePlanName;
		test_description = "Create Rate Plan " + parentRatePlanName;
		test_steps.clear();
		String ReservationRoomClassesAbbr="";
		NewRoomClassesV2 rc2 = new NewRoomClassesV2();
		DerivedRate derivedRate = new DerivedRate();
		if (ReservationType.equalsIgnoreCase("TapeChart")) {
			test_name = test_name+" from TapeChart";
			 ReservationRoomClassesAbbr = rc2.openRoomClassAndGetDataFromRoomTab(driver, test_steps, RoomClasses).get("Abbreviation");
			navigation.Reservation_Backward_3(driver);
		}
		String currencyName = null;
		String currencySign = "$";
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
				getTest_Steps = admin.clickOnClient(driver, Utility.loginReportsV2ReservationV2.get(1));
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
			Utility.app_logs.info("RatePlanName : " + parentRatePlanName);
			israteplanExist = rateGrid.isRatePlanExist(driver, parentRatePlanName, test_steps);
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

				test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", test_steps);

				test_steps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, parentRatePlanName, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, Description, test_steps);

				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, test_steps);

				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, test_steps);
				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, test_steps);

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

				String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, test_steps);

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

				nightlyRate.createSeason(driver, test_steps, SeasonStartDate, SeasonEndDate, SeasonName, isMonDay,
						isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
						ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
						ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues,
						isSeasonPolicies);

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
		boolean isDerivedRateplanExist = false;
		test_steps.add("=================== VERIFY Derived RATE PLAN EXIST OR NOT ======================");
		app_logs.info("=================== VERIFY Derived RATE PLAN EXIST OR NOT ======================");
		try {
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
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
				derivedRate.selectRatePlan(driver, parentRatePlanName, true, test_steps);

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
		HashMap<String, String> capacityAdult1 = new HashMap<String, String>();
		HashMap<String, String> capacityChild1 = new HashMap<String, String>();
		HashMap<String, String> ratePlanAdults = new HashMap<String, String>();
		HashMap<String, String> ratePlanChilds = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> ratesList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> exAdultList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> exChildList = new ArrayList<HashMap<String, String>>();
		ArrayList<String> allDatesBW = new ArrayList<String>();
		
		
		HashMap<String, String> changeCapacityAdult1 = new HashMap<String, String>();
		HashMap<String, String> changeCapacityChild1 = new HashMap<String, String>();
		HashMap<String, String> changeRatePlanAdults = new HashMap<String, String>();
		HashMap<String, String> changeRatePlanChilds = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> changeRatesList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> changeExAdultList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> changeExChildList = new ArrayList<HashMap<String, String>>();
		ArrayList<String> changeAllDatesBW = new ArrayList<String>();
		
		try {
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_name = "Getting Rate Plan Rates and Capacity : " + parentRatePlanName;
			test_description = "Getting Rate Plan Rates and Capacity : " + parentRatePlanName;
			test_steps.clear();
			ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);
			ArrayList<String> checkOutList = Utility.convertTokenToArrayList(CheckOutDate, delim);
			ArrayList<String> roomClassList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);

			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			rateGrid.expandParentRateGrid(driver, "minus");
			derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus", test_steps);
			derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);
			
			rateGrid.clickOnEditRatePlan(driver);

			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInList.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);

			for (String str : roomClassList) {
				capacityAdult1.put(str, nightlyRate.getAdultCapacity(driver, str));
				capacityChild1.put(str, nightlyRate.getChildCapacity(driver, str));
				ratePlanAdults.put(str, nightlyRate.getMaxAdult(driver, str));
				ratePlanChilds.put(str, nightlyRate.getMaxPersons(driver, str));
			}

			nightlyRate.closeSeason(driver, test_steps);

			rateGrid.closeOpendTabInMainMenu(driver);

			String[] rm = ReservationRoomClasses.split("\\|");
			driver.navigate().refresh();

			HashMap<String, // RoomClass
					HashMap<String, // Source
							HashMap<String, // date
									RatesGridChannelRatesRules>>> roomClassWiseSourceDerivedRatesRule = rateGrid
											.getSourceWiseRatesRules(driver, test_steps, parentRatePlanName, derivedRatePlanName,
													true, delim, ReservationRoomClasses, channelName, CheckInDate,
													CheckOutDate);

			for (int k = 0; k < rm.length; k++) {

				HashMap<String, String> getRates1 = new HashMap<String, String>();
				HashMap<String, String> getExAdult1 = new HashMap<String, String>();
				HashMap<String, String> getExChild1 = new HashMap<String, String>();

				allDatesBW = Utility.getAllDatesBetweenTwoDates(CheckInDate, CheckOutDate);

				for (String date : allDatesBW) {
					app_logs.info(date);
					app_logs.info("Found Room Rates : "
							+ roomClassWiseSourceDerivedRatesRule.get(rm[k]).get(channelName).get(date));
					test_steps.add("Found Room Rates : "
							+ roomClassWiseSourceDerivedRatesRule.get(rm[k]).get(channelName).get(date));

					RatesGridChannelRatesRules obj = roomClassWiseSourceDerivedRatesRule.get(rm[k]).get(channelName)
							.get(date);

					getRates1.put(date, obj.getRooomRate());
					getExAdult1.put(date, obj.getExtraAdultRate());
					getExChild1.put(date, obj.getExtraChildRate());

					app_logs.info("getRates1 : " + getRates1);
				}
				ratesList.add(getRates1);
				exAdultList.add(getExAdult1);
				exChildList.add(getExChild1);
				app_logs.info(ratesList);
				app_logs.info(getExAdult1);
				app_logs.info(getExChild1);
			}
			
			
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_name = "Getting Rate Plan Rates and Capacity For Change Stay Details : " + derivedRatePlanName;
			test_description = "Getting Rate Plan Rates and Capacity For Change Stay Details : " + derivedRatePlanName;
			test_steps.clear();

			
		
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);

			rateGrid.clickOnEditRatePlan(driver);

			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, change_checkInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);

			
				changeCapacityAdult1.put(changeRoomClass, nightlyRate.getAdultCapacity(driver, changeRoomClass));
				changeCapacityChild1.put(changeRoomClass, nightlyRate.getChildCapacity(driver, changeRoomClass));
				changeRatePlanAdults.put(changeRoomClass, nightlyRate.getMaxAdult(driver, changeRoomClass));
				changeRatePlanChilds.put(changeRoomClass, nightlyRate.getMaxPersons(driver, changeRoomClass));
			

			nightlyRate.closeSeason(driver, test_steps);

			rateGrid.closeOpendTabInMainMenu(driver);

			
			driver.navigate().refresh();

			HashMap<String, // RoomClass
					HashMap<String, // Source
							HashMap<String, // date
									RatesGridChannelRatesRules>>> chagneRoomClassWiseSourceDerivedRatesRule = rateGrid
											.getSourceWiseRatesRules(driver, test_steps, parentRatePlanName, derivedRatePlanName,
													true, delim, changeRoomClass, channelName, change_checkInDate,
													change_checkOutDate);

			

				HashMap<String, String> getRates1 = new HashMap<String, String>();
				HashMap<String, String> getExAdult1 = new HashMap<String, String>();
				HashMap<String, String> getExChild1 = new HashMap<String, String>();

				changeAllDatesBW = Utility.getAllDatesBetweenTwoDates(change_checkInDate, change_checkOutDate);

				for (String date : changeAllDatesBW) {
					app_logs.info(date);
					app_logs.info("Found Room Rates : "
							+ chagneRoomClassWiseSourceDerivedRatesRule.get(changeRoomClass).get(channelName).get(date));
					test_steps.add("Found Room Rates : "
							+ chagneRoomClassWiseSourceDerivedRatesRule.get(changeRoomClass).get(channelName).get(date));

					RatesGridChannelRatesRules obj = chagneRoomClassWiseSourceDerivedRatesRule.get(changeRoomClass).get(channelName)
							.get(date);

					getRates1.put(date, obj.getRooomRate());
					getExAdult1.put(date, obj.getExtraAdultRate());
					getExChild1.put(date, obj.getExtraChildRate());

					app_logs.info("getRates1 : " + getRates1);
				}
				changeRatesList.add(getRates1);
				changeExAdultList.add(getExAdult1);
				changeExChildList.add(getExChild1);
				app_logs.info(changeRatesList);
				app_logs.info(getExAdult1);
				app_logs.info(getExChild1);
		
			
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
			navigation.ReservationV2_Backward(driver);
//			res.click_NewReservation(driver, test_steps);

			String ReservationRate = derivedRatePlanName;
			ArrayList<String> roomClassList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
			if (ReservationType.contains("single")) {
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(ratesList.get(0),
							ratePlanAdults.get(roomclass), ratePlanChilds.get(roomclass), exAdultList.get(0),
							exChildList.get(0), capacityChild1.get(roomclass), capacityAdult1.get(roomclass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckInDate),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckOutDate)),
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);
					roomClassWiseCalculatedRates.put(roomclass, rateIs);
				}
				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
				app_logs.info(obj);
				
				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						ReservationRoomClasses, CheckInDate, test_steps);

//				app_logs.info("test : " + rateIs.get("15/04/2021"));
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
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				app_logs.info(changeRatesList);
				app_logs.info(changeRatePlanAdults);
				app_logs.info(changeRatePlanChilds);
				app_logs.info(changeExAdultList);
				app_logs.info(changeExChildList);
				app_logs.info(changeCapacityChild1);
				app_logs.info(changeCapacityAdult1);
				app_logs.info(adult);
				app_logs.info(children);
				
				app_logs.info(changeCheckInList);
				app_logs.info(changeCheckOutList);
				
				for (int i = 0; i < changeCheckInList.size(); i++) {
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(changeRatesList.get(i),
							changeRatePlanAdults.get(changeRoomClass), changeRatePlanChilds.get(changeRoomClass),
							changeExAdultList.get(i), changeExChildList.get(i), changeCapacityChild1.get(changeRoomClass),
							changeCapacityAdult1.get(changeRoomClass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckInList.get(i)),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckOutList.get(i))),
							ratesConfig.getProperty("defaultDateFormat")));
					changeRoomClassWiseCalculatedRates.put(changeRoomClass, changeRateIs);
				}
				
				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay(driver, test_steps,
						changeRoomClass, change_checkInDate,
						change_checkOutDate, changeOption);
				obj1.put(changeRoomClass, ratesDetailChangeStay);
				app_logs.info(obj1);
				
				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						changeRoomClass, change_checkInDate, test_steps);
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("mrb")) {
				ReservationRate = derivedRatePlanName + "|" + derivedRatePlanName;
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
					rateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(ratesList.get(i),
							ratePlanAdults.get(roomClassList.get(i)), ratePlanChilds.get(roomClassList.get(i)),
							exAdultList.get(i), exChildList.get(i), capacityChild1.get(roomClassList.get(i)),
							capacityAdult1.get(roomClassList.get(i)), adu[i], child[i],
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											checkInList.get(i)),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											checkOutList.get(i))),
							ratesConfig.getProperty("defaultDateFormat")));
					roomClassWiseCalculatedRates.put(roomClassList.get(i), rateIs);
				}
				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
				app_logs.info(obj);
				Utility.app_logs.info(rateIs);

				Utility.app_logs.info(ratesConfig.getProperty("defaultDateFormat"));
				
				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						ReservationRoomClasses, CheckInDate, test_steps);
				
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
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				app_logs.info(changeRatesList);
				app_logs.info(changeRatePlanAdults);
				app_logs.info(changeRatePlanChilds);
				app_logs.info(changeExAdultList);
				app_logs.info(changeExChildList);
				app_logs.info(changeCapacityChild1);
				app_logs.info(changeCapacityAdult1);
				app_logs.info(adu);
				app_logs.info(child);
				
				app_logs.info(changeCheckInList);
				app_logs.info(changeCheckOutList);
				
				for (int i = 0; i < changeCheckInList.size(); i++) {
				
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(changeRatesList.get(i),
							changeRatePlanAdults.get(changeRoomClass), changeRatePlanChilds.get(changeRoomClass),
							changeExAdultList.get(i), changeExChildList.get(i), changeCapacityChild1.get(changeRoomClass),
							changeCapacityAdult1.get(changeRoomClass), adu[i], child[i],
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckInList.get(i)),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckOutList.get(i))),
							ratesConfig.getProperty("defaultDateFormat")));
					changeRoomClassWiseCalculatedRates.put(changeRoomClass, changeRateIs);
				}
				
				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay_MRB(driver, test_steps,
						changeRoomClass, change_checkInDate,
						change_checkOutDate, changeOption, 2);
				obj1.put(changeRoomClass, ratesDetailChangeStay);
				app_logs.info(obj1);
				
				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						changeRoomClass, change_checkInDate, test_steps);
				
				res.modifyReservationDatesForMRB(driver, test_steps, change_checkInDate, change_checkOutDate,
						changeOption, 2,changeRoomClass);

			} else if (ReservationType.contains("Quote")) {
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(ratesList.get(0),
							ratePlanAdults.get(roomclass), ratePlanChilds.get(roomclass), exAdultList.get(0),
							exChildList.get(0), capacityChild1.get(roomclass), capacityAdult1.get(roomclass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckInDate),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckOutDate)),
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);
					roomClassWiseCalculatedRates.put(roomclass, rateIs);
				}
				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
				app_logs.info(obj);
				
				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						ReservationRoomClasses, CheckInDate, test_steps);

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
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(changeRatesList.get(i),
							changeRatePlanAdults.get(changeRoomClass), changeRatePlanChilds.get(changeRoomClass),
							changeExAdultList.get(i), changeExChildList.get(i), changeCapacityChild1.get(changeRoomClass),
							changeCapacityAdult1.get(changeRoomClass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckInList.get(i)),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckOutList.get(i))),
							ratesConfig.getProperty("defaultDateFormat")));
					changeRoomClassWiseCalculatedRates.put(changeRoomClass, changeRateIs);
				}
				
				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay(driver, test_steps,
						changeRoomClass, change_checkInDate,
						change_checkOutDate, changeOption);
				obj1.put(changeRoomClass, ratesDetailChangeStay);
				app_logs.info(obj1);
				
				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						changeRoomClass, change_checkInDate, test_steps);
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("TapeChart")) {
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(ratesList.get(0),
							ratePlanAdults.get(roomclass), ratePlanChilds.get(roomclass), exAdultList.get(0),
							exChildList.get(0), capacityChild1.get(roomclass), capacityAdult1.get(roomclass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckInDate),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckOutDate)),
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);
					roomClassWiseCalculatedRates.put(roomclass, rateIs);
				}
				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
				app_logs.info(obj);
				
				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						ReservationRoomClasses, CheckInDate, test_steps);

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
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(changeRatesList.get(i),
							changeRatePlanAdults.get(changeRoomClass), changeRatePlanChilds.get(changeRoomClass),
							changeExAdultList.get(i), changeExChildList.get(i), changeCapacityChild1.get(changeRoomClass),
							changeCapacityAdult1.get(changeRoomClass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckInList.get(i)),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckOutList.get(i))),
							ratesConfig.getProperty("defaultDateFormat")));
					changeRoomClassWiseCalculatedRates.put(changeRoomClass, changeRateIs);
				}
				
				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay(driver, test_steps,
						changeRoomClass, change_checkInDate,
						change_checkOutDate, changeOption);
				obj1.put(changeRoomClass, ratesDetailChangeStay);
				app_logs.info(obj1);
				
				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						changeRoomClass, change_checkInDate, test_steps);
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("Group")) {
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(ratesList.get(0),
							ratePlanAdults.get(roomclass), ratePlanChilds.get(roomclass), exAdultList.get(0),
							exChildList.get(0), capacityChild1.get(roomclass), capacityAdult1.get(roomclass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckInDate),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckOutDate)),
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);
					roomClassWiseCalculatedRates.put(roomclass, rateIs);
				}
				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
				app_logs.info(obj);
				
				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						ReservationRoomClasses, CheckInDate, test_steps);

				
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
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(changeRatesList.get(i),
							changeRatePlanAdults.get(changeRoomClass), changeRatePlanChilds.get(changeRoomClass),
							changeExAdultList.get(i), changeExChildList.get(i), changeCapacityChild1.get(changeRoomClass),
							changeCapacityAdult1.get(changeRoomClass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckInList.get(i)),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckOutList.get(i))),
							ratesConfig.getProperty("defaultDateFormat")));
					changeRoomClassWiseCalculatedRates.put(changeRoomClass, changeRateIs);
				}
				
				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay(driver, test_steps,
						changeRoomClass, change_checkInDate,
						change_checkOutDate, changeOption);
				obj1.put(changeRoomClass, ratesDetailChangeStay);
				app_logs.info(obj1);
				
				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						changeRoomClass, change_checkInDate, test_steps);
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("Account Reservation")) {
				
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(ratesList.get(0),
							ratePlanAdults.get(roomclass), ratePlanChilds.get(roomclass), exAdultList.get(0),
							exChildList.get(0), capacityChild1.get(roomclass), capacityAdult1.get(roomclass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckInDate),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											CheckOutDate)),
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);
					roomClassWiseCalculatedRates.put(roomclass, rateIs);
				}
				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
				app_logs.info(obj);
				
				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						ReservationRoomClasses, CheckInDate, test_steps);

				
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
				
				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
				HashMap<String, String> changeRateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
				
				for (int i = 0; i < changeCheckInList.size(); i++) {
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(changeRatesList.get(i),
							changeRatePlanAdults.get(changeRoomClass), changeRatePlanChilds.get(changeRoomClass),
							changeExAdultList.get(i), changeExChildList.get(i), changeCapacityChild1.get(changeRoomClass),
							changeCapacityAdult1.get(changeRoomClass), adult,
							children,
							Utility.getDateRangeBetweenfromAndToDate(
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckInList.get(i)),
									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
											changeCheckOutList.get(i))),
							ratesConfig.getProperty("defaultDateFormat")));
					changeRoomClassWiseCalculatedRates.put(changeRoomClass, changeRateIs);
				}
				
				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay(driver, test_steps,
						changeRoomClass, change_checkInDate,
						change_checkOutDate, changeOption);
				obj1.put(changeRoomClass, ratesDetailChangeStay);
				app_logs.info(obj1);
				
				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
						changeRoomClass, change_checkInDate, test_steps);
				
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
		return Utility.getData("VerifyDerivedNightlyRateResV2", envLoginExcel);
	}

}
