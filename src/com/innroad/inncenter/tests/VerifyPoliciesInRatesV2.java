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

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyPoliciesInRatesV2 extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	// ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyPoliciesInRatesV2(String delim, String secondaryDelim, String PolicyTypes,
			String CancellationPolicyName, String DepositPolicyName, String CheckInPolicyName, String NoShowPolicyName,
			String TypeOfFees, String GuestsWillIncurAFee, String ChargesType, String NoOfDays, String CancelWithInType,
			String isCustomText, String CustomText, String EditPolicyTypes, String UpdateCancellationPolicyName,
			String UpdateDepositPolicyName, String UpdateCheckInPolicyName, String UpdateNoShowPolicyName,
			String DeleteClauses, String EditClause, String UpdateTypeOfFees, String UpdateGuestsWillIncurAFee,
			String UpdateChargesType, String UpdateNoOfDays, String UpdateCancelWithInType, String UpdateIsCustomText,
			String UpdateCustomText,
			String RatePlanName, String FolioDisplayName, String Description,
			String Channels, String RoomClasses, String isRatePlanRistrictionReq, String RistrictionType,
			String isMinStay, String MinNights, String isMaxStay, String MaxNights, String isMoreThanDaysReq,
			String MoreThanDaysCount, String isWithInDaysReq, String WithInDaysCount, String PromoCode,
			String isPolicesReq, String SeasonName, String SeasonStartDate,
			String SeasonEndDate, String isMonDay, String isTueDay, String isWednesDay, String isThursDay,
			String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults,
			String RatePerNight, String MaxAdults, String MaxPersons, String AdditionalAdultsPerNight,
			String AdditionalChildPerNight, String isAddRoomClassInSeason, String ExtraRoomClassesInSeason,
			String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults, String ExtraRoomClassMaxPersons,
			String ExtraRoomClassAdditionalAdultsPerNight, String ExtraRoomClassAdditionalChildPerNight,
			String isSerasonLevelRules,String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClasses, String SeasonRuleType,
			String SeasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday,
			String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday,
			String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday, String isSeasonPolicies,
			 String isDefaultRatePlan,String isRatePlanActive,
			String UpdateRatePlanStatus
			) throws InterruptedException, IOException {

		Utility.DELIM = delim;

		test_name = "VerifyPoliciesInRatesV2";
		test_description = "VerifyPoliciesInRatesV2 <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "VerifyPoliciesInRatesV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Policies policies = new Policies();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();

		CancellationPolicyName = CancellationPolicyName + Utility.generateRandomStringWithoutNumbers();
		DepositPolicyName = DepositPolicyName + Utility.generateRandomStringWithoutNumbers();
		CheckInPolicyName = CheckInPolicyName + Utility.generateRandomStringWithoutNumbers();
		NoShowPolicyName = NoShowPolicyName + Utility.generateRandomStringWithoutNumbers();
		
		if(!UpdateCancellationPolicyName.equalsIgnoreCase("NA")) {
			UpdateCancellationPolicyName = UpdateCancellationPolicyName + Utility.generateRandomStringWithoutNumbers();
		}
		if(!UpdateDepositPolicyName.equalsIgnoreCase("NA")) {
			UpdateDepositPolicyName = UpdateDepositPolicyName + Utility.generateRandomStringWithoutNumbers();
		}
		if(!UpdateCheckInPolicyName.equalsIgnoreCase("NA")) {
			UpdateCheckInPolicyName = UpdateCheckInPolicyName + Utility.generateRandomStringWithoutNumbers();
		}
		if(!UpdateNoShowPolicyName.equalsIgnoreCase("NA")) {
			UpdateNoShowPolicyName = UpdateNoShowPolicyName + Utility.generateRandomStringWithoutNumbers();
		}
		String policiesName = policies.getPolicyNames(delim, PolicyTypes, CancellationPolicyName, DepositPolicyName, CheckInPolicyName, NoShowPolicyName);
		// Login to application
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
				// login.login(driver, envURL, "wpi", "autouser", "Auto@123");
				loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				// login.login(driver, envURL, "wpi", "autouser", "Auto@123");
				loginWPI(driver);
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

		try {
			test_steps.add("=================== NAVIGATE TO POLICIES ======================");
			app_logs.info("=================== NAVIGATE TO POLICIES ======================");
			navigation.Inventory(driver, test_steps);
			navigation.policies(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			HashMap<String, ArrayList<String>> foundCheckInPrintText = policies.createPolicies(driver, test_steps,
					delim, secondaryDelim, PolicyTypes, CancellationPolicyName, DepositPolicyName, CheckInPolicyName,
					NoShowPolicyName, TypeOfFees, GuestsWillIncurAFee, ChargesType, NoOfDays, CancelWithInType,
					isCustomText, CustomText);

			policies.verifyPolicies(driver, test_steps, delim, secondaryDelim, PolicyTypes, CancellationPolicyName,
					DepositPolicyName, CheckInPolicyName, NoShowPolicyName, TypeOfFees, GuestsWillIncurAFee,
					ChargesType, NoOfDays, CancelWithInType, isCustomText, foundCheckInPrintText);

			policies.verifyPioilcyUsesLinkValue(driver, delim,policiesName, "0", test_steps);
			policies.verifyNoPolicyUsesAllType(driver, delim, PolicyTypes, CancellationPolicyName, DepositPolicyName, CheckInPolicyName, NoShowPolicyName, test_steps);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		
		ArrayList<String> allCancelationPolicies = new ArrayList<String>();
		ArrayList<String> allDepositPolicies = new ArrayList<String>();
		ArrayList<String> allCheckInPolicies = new ArrayList<String>();
		ArrayList<String> allNoShowPolicies = new ArrayList<String>();
		try {
			
				test_steps.add("=================== GET ALL POLICIES ======================");
				app_logs.info("=================== GET ALL POLICIES ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver, test_steps);
				allCancelationPolicies = policies.getCancelationPolicies(driver);
				test_steps.add("Found Cancelation Policies : " + allCancelationPolicies.size());
				app_logs.info("Found Cancelation Policies : " + allCancelationPolicies.size());
				allDepositPolicies = policies.getDepositPolicies(driver);
				test_steps.add("Found Deposit Policies : " + allDepositPolicies.size());
				app_logs.info("Found Deposit Policies : " + allDepositPolicies.size());
				allCheckInPolicies = policies.getCheckInPolicies(driver);
				test_steps.add("Found CheckIn Policies : " + allCheckInPolicies.size());
				app_logs.info("Found CheckIn Policies : " + allCheckInPolicies.size());
				allNoShowPolicies = policies.getNoShowPolicies(driver);
				test_steps.add("Found NoShow Policies : " + allNoShowPolicies.size());
				app_logs.info("Found NoShow Policies : " + allNoShowPolicies.size());
			
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

			test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", test_steps);

			test_steps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			RatePlanName = RatePlanName + Utility.generateRandomString();
			FolioDisplayName = FolioDisplayName + Utility.generateRandomString();
             
		   
			nightlyRate.enterRatePlanName(driver, RatePlanName, test_steps);
			nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
			nightlyRate.enterRatePlanDescription(driver, Description, test_steps);

			nightlyRate.clickNextButton(driver, test_steps);

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, test_steps);

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

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType, Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights, Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode, test_steps);


			String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, test_steps);
			nightlyRate.clickNextButton(driver, test_steps);

			nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, test_steps);
			
			nightlyRate.verifyAllPolicies(driver, "Cancellation", allCancelationPolicies, test_steps);
			nightlyRate.verifyAllPolicies(driver, "Deposit", allDepositPolicies, test_steps);
			nightlyRate.verifyAllPolicies(driver, "Check-in", allCheckInPolicies, test_steps);
			nightlyRate.verifyAllPolicies(driver, "No Show", allNoShowPolicies, test_steps);
			nightlyRate.selectPolicy(driver, PolicyTypes, policiesName, Boolean.parseBoolean(isPolicesReq), test_steps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
					Boolean.parseBoolean(isPolicesReq), test_steps);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
					Boolean.parseBoolean(isPolicesReq), test_steps);
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		try {

			test_steps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");
			test_steps.add("Navigated to RatesGrid");
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, SeasonStartDate, SeasonEndDate);
			nightlyRate.enterSeasonName(driver, test_steps, SeasonName);
			nightlyRate.selectSeasonDays(driver, test_steps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonColor(driver, test_steps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps, isAdditionalChargesForChildrenAdults);
			nightlyRate.enterRate(driver, test_steps, RatePerNight, isAdditionalChargesForChildrenAdults, MaxAdults, MaxPersons, AdditionalAdultsPerNight, AdditionalChildPerNight);
			nightlyRate.addExtraRoomClassInSeason(driver, test_steps, isAddRoomClassInSeason, ExtraRoomClassesInSeason, isAdditionalChargesForChildrenAdults, RatePerNight, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight, ExtraRoomClassAdditionalChildPerNight);
			nightlyRate.clickRulesRestrictionOnSeason(driver, test_steps);
			nightlyRate.clickAssignRulesByRoomClass(driver, test_steps, isAssignRulesByRoomClass);
			nightlyRate.enterSeasonLevelRules(driver, test_steps, isSerasonLevelRules,isAssignRulesByRoomClass, SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday);
			nightlyRate.clickSeasonPolicies(driver, test_steps);

			nightlyRate.selectPolicy(driver, PolicyTypes, policiesName, Boolean.parseBoolean(isSeasonPolicies), test_steps);
			
			nightlyRate.verifyAllPolicies(driver, "Cancellation", allCancelationPolicies, test_steps);
			nightlyRate.verifyAllPolicies(driver, "Deposit", allDepositPolicies, test_steps);
			nightlyRate.verifyAllPolicies(driver, "Check-in", allCheckInPolicies, test_steps);
			nightlyRate.verifyAllPolicies(driver, "No Show", allNoShowPolicies, test_steps);
			
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickCompleteChanges(driver, test_steps);
			nightlyRate.clickSaveAsActive(driver, test_steps);
			 Utility.rateplanName=RatePlanName;

			test_steps.add("=================== RATE PLAN CREATED ======================");
			app_logs.info("=================== RATE PLAN CREATED ======================");
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			navigation.policies(driver, test_steps);
//			policies.verifyPioilcyUsesLinkValue(driver, delim,policiesName, "1", test_steps);
//			policies.verifyPolicyUsesAllType(driver, delim, policiesName, RatePlanName, true, test_steps);
//			
			HashMap<String, ArrayList<String>> foundCheckInPrintTextUpdate = policies.editPolicies(driver, test_steps, 
					delim, secondaryDelim, EditPolicyTypes, CancellationPolicyName, DepositPolicyName, CheckInPolicyName, 
					NoShowPolicyName, UpdateCancellationPolicyName, UpdateDepositPolicyName, UpdateCheckInPolicyName, UpdateNoShowPolicyName, DeleteClauses, EditClause, 
					UpdateTypeOfFees, UpdateGuestsWillIncurAFee, UpdateChargesType, UpdateNoOfDays, UpdateCancelWithInType, UpdateIsCustomText, UpdateCustomText);
			
			policies.verifyPolicies(driver, test_steps, delim, secondaryDelim, EditPolicyTypes, UpdateCancellationPolicyName,
					UpdateDepositPolicyName, UpdateCheckInPolicyName, UpdateNoShowPolicyName, UpdateTypeOfFees,
					UpdateGuestsWillIncurAFee, UpdateChargesType, UpdateNoOfDays, UpdateCancelWithInType, UpdateIsCustomText, foundCheckInPrintTextUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		try {
//			=================== DELETE POLICIES ======================
//			=================== DELETE POLICIES ======================
			ArrayList<String> nameList = new ArrayList<String>();
			if(UpdateCancellationPolicyName.equalsIgnoreCase("NA")) {
				nameList.add(CancellationPolicyName);
			}else {
				nameList.add(UpdateCancellationPolicyName);
			}
			
			if(UpdateDepositPolicyName.equalsIgnoreCase("NA")) {
				nameList.add(DepositPolicyName);
			}else {
				nameList.add(UpdateDepositPolicyName);
			}
			if(UpdateCheckInPolicyName.equalsIgnoreCase("NA")) {
				nameList.add(CheckInPolicyName);
			}else {
				nameList.add(UpdateCheckInPolicyName);
			}
			
			if(UpdateNoShowPolicyName.equalsIgnoreCase("NA")) {
				nameList.add(NoShowPolicyName);
			}else {
				nameList.add(UpdateNoShowPolicyName);
			}
			
			policies.deleteAllPolicies(driver, test_steps, nameList);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyPoliciesInRatesV2", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
