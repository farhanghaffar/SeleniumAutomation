package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class SeasonVerification extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}
	private void getData(HashMap<String, String> data)
	{
		Set set = data.entrySet();
		  Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         test_steps.add("Date : "+ mentry.getKey() + " & Value : " +mentry.getValue().toString());
		      }
	}
	
	@Test(dataProvider = "getData")
	public void verifyRateplanInInncenter(String delim, String RatePlanName, String FolioDisplayName,
			String Description, String Channels, String RoomClasses, String isRatePlanRistrictionReq,
			String RistrictionType, String isMinStay, String MinNights, String isMaxStay, String MaxNights,
			String isMoreThanDaysReq, String MoreThanDaysCount, String isWithInDaysReq, String WithInDaysCount,
			String PromoCode, String isPolicesReq, String PoliciesType, String PoliciesName, String SeasonName,
			String SeasonStartDate, String SeasonEndDate, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String isSerasonLevelRules, String isAssignRulesByRoomClass,
			String SeasonRuleSpecificRoomClasses, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String isSeasonPolicies, String SeasonPolicyType, String SeasonPolicyValues,
			String ReservationType, String CheckInDate, String CheckOutDate, String ReservationRoomClasses, String AccountType,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String ResFirstName, String ResLastName, String adult, String children, String email, String BlockName,
			String RoomPerNight, String noOfNightsGroupBlock) throws Exception {

		Utility.DELIM = delim;

		test_name = "VerifyRateplanInInncenter";
		test_description = "VerifyRateplanInInncenter";
		test_catagory = "VerifyRateplanInInncenter";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		CPReservationPage reservationPage = new CPReservationPage();
		Groups group = new Groups();
		AdvGroups advgroup = new AdvGroups();
		Account CreateTA = new Account();
		RoomClass rc = new RoomClass();
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			Login login = new Login();
			try {
				login.login(driver, envURL, "wpi", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				login.login(driver, envURL, "wpi", "autouser", "Auto@123");
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
		
		ArrayList<String> RoomAbbri = new ArrayList<String>();
	
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, test_steps);
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
		
		test_steps.
		add("=================== CREATE NEW NIGHTLY RATE PLAN ======================"
		); app_logs.
		info("=================== CREATE NEW NIGHTLY RATE PLAN ======================"
		);
		
		rateGrid.clickRateGridAddRatePlan(driver);
		rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
		
		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type",
		"Nightly rate plan", test_steps);
		
		test_steps.add(
		"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================"
		); app_logs.info(
		"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================"
		);
		
		RatePlanName = RatePlanName + Utility.generateRandomString();
		FolioDisplayName = FolioDisplayName + Utility.generateRandomString();
		
		nightlyRate.enterRatePlanName(driver, RatePlanName, test_steps);
		nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
		nightlyRate.enterRatePlanDescription(driver, Description, test_steps);
		
		nightlyRate.clickNextButton(driver, test_steps);
		
		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName,
		test_steps);
		
		test_steps.
		add("=================== SELECT DISTRIBUTED CHANNELS ======================"
		); app_logs.
		info("=================== SELECT DISTRIBUTED CHANNELS ======================"
		);
		
		nightlyRate.selectChannels(driver, Channels, true, test_steps); String
		summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
		nightlyRate.clickNextButton(driver, test_steps);
		
		nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels,
		test_steps);
		
		test_steps.
		add("=================== SELECT ROOM CLASSES ======================");
		app_logs.
		info("=================== SELECT ROOM CLASSES ======================");
		nightlyRate.selectRoomClasses(driver, RoomClasses, true, test_steps); String
		summaryRoomClasses =
		nightlyRate.generateTitleSummaryValueForRoomClass(driver);
		nightlyRate.clickNextButton(driver, test_steps);
		
		nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses,
		test_steps);
		
		nightlyRate.selectRestrictions(driver,
		Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
		Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay),
		MaxNights, Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount,
		Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode,
		test_steps);
		
		String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver,
		test_steps); nightlyRate.clickNextButton(driver, test_steps);
		
		nightlyRate.verifyTitleSummaryValue(driver, "Restrictions",
		restrictionsSummary, test_steps);
		
		nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName,
		Boolean.parseBoolean(isPolicesReq), test_steps);
		
		HashMap<String, String> allPolicyDesc =
		nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
		Boolean.parseBoolean(isPolicesReq), test_steps);
		nightlyRate.clickNextButton(driver, test_steps);
		nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName,
		allPolicyDesc, Boolean.parseBoolean(isPolicesReq), test_steps); } catch
		(Exception e) { e.printStackTrace(); if (Utility.reTry.get(testName) ==
		Utility.count) { RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory,
		test_steps); Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else { Assert.assertTrue(false); } } catch (Error e) { e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory,
		test_steps); Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else { Assert.assertTrue(false); } }
		
		try {
		test_steps.add("=================== CREATE SEASON ======================");
		app_logs.info("=================== CREATE SEASON ======================");
		
		nightlyRate.clickCreateSeason(driver, test_steps);
		nightlyRate.createSeason(driver, RoomAbbri, SeasonStartDate, SeasonEndDate, SeasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons, AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason, ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight, ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules, SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues, isSeasonPolicies);
			
		boolean isSeasonExist=nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, RoomAbbri, CheckInDate, CheckOutDate);
		
		
		System.out.println("isSeasonExist : "+isSeasonExist);
		
//		nightlyRate.clickCompleteChanges(driver, test_steps);
//		nightlyRate.clickSaveAsActive(driver, test_steps);
//		Wait.wait30Second();
//		navigation.cpReservation_Backward(driver);
		test_steps.add("=================== RATE PLAN CREATED ======================"
		);
		app_logs.info("=================== RATE PLAN CREATED ======================"
		); } catch (Exception e) { e.printStackTrace(); if
		(Utility.reTry.get(testName) == Utility.count) { RetryFailedTestCases.count =
		Utility.reset_count; Utility.AddTest_IntoReport(testName, test_description,
		test_catagory, test_steps); Utility.updateReport(e, "Failed", testName,
		"RatesV2", driver); } else { Assert.assertTrue(false); } } catch (Error e) {
		e.printStackTrace(); if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory,
		test_steps); Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else { Assert.assertTrue(false); } }
		
		 

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("Season", excel);
	}

	// @AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
