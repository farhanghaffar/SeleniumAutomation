package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

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

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyPoliciesInGroupBasedOnRates extends TestCore {
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
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyPoliciesInGroupBasedOnRates(String delim, String secondaryDelim, String PolicyTypes,
			String PolicyNames, String TypeOfFees, String GuestsWillIncurAFee, String ChargesType, String NoOfDays,
			String CancelWithInType, String isCustomText, String CustomText, String RatePlanName,
			String FolioDisplayName, String Description, String Channels, String RoomClasses,
			String isRatePlanRistrictionReq, String RistrictionType, String isMinStay, String MinNights,
			String isMaxStay, String MaxNights, String isMoreThanDaysReq, String MoreThanDaysCount,
			String isWithInDaysReq, String WithInDaysCount, String PromoCode, String isPolicesReq, String PolicesTypes,
			String SeasonName, String isMonDay, String isTueDay, String isWednesDay, String isThursDay, String isFriday,
			String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults, String RatePerNight,
			String MaxAdults, String MaxPersons, String AdditionalAdultsPerNight, String AdditionalChildPerNight,
			String isAddRoomClassInSeason, String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight,
			String ExtraRoomClassMaxAdults, String ExtraRoomClassMaxPersons,
			String ExtraRoomClassAdditionalAdultsPerNight, String ExtraRoomClassAdditionalChildPerNight,String isSerasonLevelRules,
			String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClasses, String SeasonRuleType,
			String SeasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday,
			String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday,
			String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday, String isSeasonPolicies,
			String isDefaultRatePlan, String isRatePlanActive, String AccountName, String MargetSegment,
			String Referral, String AccountFirstName, String AccountLastName, String Phonenumber, String Address1,
			String city, String Country, String State, String Postalcode, String RoomClassName, String ResFirstName,
			String ResLastName, String adult, String children, String email, String BlockName, String RoomPerNight,
			String noOfNightsGroupBlock)

			throws InterruptedException, IOException {

		test_name = "verifyPoliciesInGroupBasedOnRates";
		test_description = "verifyPoliciesInGroupBasedOnRates <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "verifyPoliciesInGroupBasedOnRates";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Policies policies = new Policies();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		Groups group = new Groups();
		CPReservationPage reservationPage = new CPReservationPage();
		AdvGroups advgroup = new AdvGroups();

		Utility.DELIM = delim;
		String SeasonStartDate = Utility.getCurrentDate("dd/MM/yyyy");
		String SeasonEndDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 10);

		HashMap<String, String> names = policies.getPolicyNames(secondaryDelim, PolicyTypes, PolicyNames);
		HashMap<String, String> policyClauses = policies.getPolicyClauses(delim, PolicyTypes, TypeOfFees);
		HashMap<String, String> guestsWillIncurAFee = policies.getPolicyClauses(delim, PolicyTypes,
				GuestsWillIncurAFee);
		HashMap<String, String> chargesType = policies.getPolicyClauses(delim, PolicyTypes, ChargesType);
		HashMap<String, String> cancelWithInType = policies.getPolicyClauses(delim, PolicyTypes, CancelWithInType);
		String CancellationPolicyName = "";
		String DepositPolicyName = "";
		String CheckInPolicyName = "";
		String NoShowPolicyName = "";

		if (names.get("Cancellation") != null) {
			CancellationPolicyName = names.get("Cancellation") + Utility.generateRandomStringWithoutNumbers();
		}
		if (names.get("Deposit") != null) {
			DepositPolicyName = names.get("Deposit") + Utility.generateRandomStringWithoutNumbers();
		}
		if (names.get("Check-In") != null) {
			CheckInPolicyName = names.get("Check-In") + Utility.generateRandomStringWithoutNumbers();
		}
		if (names.get("No Show") != null) {
			NoShowPolicyName = names.get("No Show") + Utility.generateRandomStringWithoutNumbers();
		}
		PolicyNames = policies.getPolicyNames(delim, PolicyTypes, CancellationPolicyName, DepositPolicyName,
				CheckInPolicyName, NoShowPolicyName);
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

			policies.verifyPioilcyUsesLinkValue(driver, delim, PolicyNames, "0", test_steps);
			policies.verifyNoPolicyUsesAllType(driver, delim, PolicyTypes, CancellationPolicyName, DepositPolicyName,
					CheckInPolicyName, NoShowPolicyName, test_steps);

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

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
					Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					WithInDaysCount, PromoCode, test_steps);

			String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, test_steps);
			nightlyRate.clickNextButton(driver, test_steps);

			nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, test_steps);

//			nightlyRate.verifyAllPolicies(driver, "Cancellation", allCancelationPolicies, test_steps);
//			nightlyRate.verifyAllPolicies(driver, "Deposit", allDepositPolicies, test_steps);
//			nightlyRate.verifyAllPolicies(driver, "Check-in", allCheckInPolicies, test_steps);
//			nightlyRate.verifyAllPolicies(driver, "No Show", allNoShowPolicies, test_steps);

			nightlyRate.selectPolicy(driver, PolicyTypes, PolicyNames, Boolean.parseBoolean(isPolicesReq), test_steps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PolicyNames,
					Boolean.parseBoolean(isPolicesReq), test_steps);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.verifyPolicyTitleSummaryValue(driver, PolicyNames, allPolicyDesc,
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
			nightlyRate.selectSeasonDays(driver, test_steps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
					isSaturDay, isSunDay);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonColor(driver, test_steps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps,
					isAdditionalChargesForChildrenAdults);
			nightlyRate.enterRate(driver, test_steps, RatePerNight, isAdditionalChargesForChildrenAdults, MaxAdults,
					MaxPersons, AdditionalAdultsPerNight, AdditionalChildPerNight);
			nightlyRate.addExtraRoomClassInSeason(driver, test_steps, isAddRoomClassInSeason, ExtraRoomClassesInSeason,
					isAdditionalChargesForChildrenAdults, RatePerNight, ExtraRoomClassRatePerNight,
					ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
					ExtraRoomClassAdditionalChildPerNight);
			nightlyRate.clickRulesRestrictionOnSeason(driver, test_steps);
			nightlyRate.clickAssignRulesByRoomClass(driver, test_steps, isAssignRulesByRoomClass);
			nightlyRate.enterSeasonLevelRules(driver, test_steps, isSerasonLevelRules, isAssignRulesByRoomClass, SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday);
			nightlyRate.clickSeasonPolicies(driver, test_steps);

			nightlyRate.selectPolicy(driver, PolicyTypes, PolicyNames, Boolean.parseBoolean(isSeasonPolicies),
					test_steps);

//			nightlyRate.verifyAllPolicies(driver, "Cancellation", allCancelationPolicies, test_steps);
//			nightlyRate.verifyAllPolicies(driver, "Deposit", allDepositPolicies, test_steps);
//			nightlyRate.verifyAllPolicies(driver, "Check-in", allCheckInPolicies, test_steps);
//			nightlyRate.verifyAllPolicies(driver, "No Show", allNoShowPolicies, test_steps);

			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickCompleteChanges(driver, test_steps);
			nightlyRate.clickSaveAsActive(driver, test_steps);
			Utility.rateplanName = RatePlanName;

			test_steps.add("=================== RATE PLAN CREATED ======================");
			app_logs.info("=================== RATE PLAN CREATED ======================");
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

		// Navigate to Groups
		try {
		 navigation.inventoryToReservation(driver);
			navigation.Groups(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Groups
		String AccountNo = "0";
		try {
			AccountName = AccountName + Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			group.type_GroupName(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			AccountNo = Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			getTest_Steps = group.enterAccountNo(driver, AccountNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.type_MailingAddrtess(driver, test, AccountFirstName, AccountLastName, Phonenumber, Address1, city,
					State, Country, Postalcode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			group.clickGroupFolioTab(driver);
			group.clickGroupFolioOption(driver);

			// group.selectCancellationPolicy(driver, CancellationPolicyName);
			String name = group.selectCancellationPolicesAllClauses(driver,
					Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), secondaryDelim),
					CancellationPolicyName, test_steps);
			group.verifyCancellationPolicyTitle(driver, name, test_steps);

			getTest_Steps.clear();
			getTest_Steps = group.done(driver);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		String reservationNumber = null;

		String CheckInDate = null;
		String CheckoutDate = null;
		try {
			navigation.Reservation_Backward(driver);
			app_logs.info("==========CREATE NEW RESERVATION <b>ASSOCIATE GROUP ACCOUNT</b>==========");
			test_steps.add("==========CREATE NEW RESERVATION <b>ASSOCIATE GROUP ACCOUNT</b>==========");
			reservationPage.click_NewReservation(driver, test_steps);

			CheckInDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 0);
			CheckoutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 3);

			app_logs.info("CheckOut Date : " + CheckoutDate);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckoutDate);
			
			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterAdult(driver, adult);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterChildren(driver, children);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRateplan(driver, RatePlanName, "", 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickOnFindRooms(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRoom(driver, getTest_Steps, RoomClassName, "Yes", "");
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", ResFirstName, ResLastName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, email);
			test_steps.addAll(getTest_Steps);

			reservationPage.selectGroupAccount(driver, test_steps, AccountName, true, true);
			
			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, email);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null, null);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickCloseReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyStatusAfterReservation(driver, foundStatus);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

			HashMap<String, String> cancellationClauseValues = reservationPage.getCancellationClauseValue(
					secondaryDelim, policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"));

			reservationPage.verifyAssociatedCancellationPolicy(driver, delim, PolicyTypes, PolicyNames,
					Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), secondaryDelim),
					cancellationClauseValues, test_steps);

			String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckoutDate);

			String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
			foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
			String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
			foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
			Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

//			ArrayList<String> foundBestPolicyAmount = policies.findBestCancellationPolicy(secondaryDelim,
//					policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
//					chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"), calRoomCharge + "",
//					foundTotalCharge);

			reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
			reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, CancellationPolicyName,
					"Cancellation");
			reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
			//reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", foundBestPolicyAmount.get(1), "Capture");
			reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);

//			reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", foundBestPolicyAmount.get(1),
//					"Capture", "Processed");
			reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);

			reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

			reservationPage.closeReservationTab(driver);
			test_steps.add("Close Reservation");
			app_logs.info("Close Reservation");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		// String AccountNo = "881253673035824";
		// Create Reservation
		

		try {

			 navigation.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.newReservation(driver);
			test_steps.addAll(getTest_Steps);
			app_logs.info("==========CREATE NEW RESERVATION FROM <b>NEW RESERVATION</b> BUTTON WITH ASSOSIATED GROUP POLICIES==========");
			test_steps.add("==========CREATE NEW RESERVATION FROM <b>NEW RESERVATION</b> BUTTON WITH ASSOSIATED GROUP POLICIES==========");
			// reservationPage.click_NewReservation(driver, test_steps);

			

			app_logs.info("CheckOut Date : " + CheckoutDate);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckoutDate);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterAdult(driver, adult);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterChildren(driver, children);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRateplan(driver, RatePlanName, "", 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickOnFindRooms(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRoom(driver, getTest_Steps, RoomClassName, "Yes", "");
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyNewPolicesApplicablePopup(driver, true, test_steps);
			reservationPage.clickNewPolicesApplicablePopupYesNo(driver, false, test_steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", ResFirstName, ResLastName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, email);
			test_steps.addAll(getTest_Steps);

//					getTest_Steps.clear();
//					getTest_Steps = reservationPage.selectReferral(driver, referral);
//					test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null, null);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickCloseReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyStatusAfterReservation(driver, foundStatus);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

			HashMap<String, String> cancellationClauseValues = reservationPage.getCancellationClauseValue(
					secondaryDelim, policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"));

			reservationPage.verifyAssociatedCancellationPolicy(driver, delim, PolicyTypes, PolicyNames,
					Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), secondaryDelim),
					cancellationClauseValues, test_steps);

			String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckoutDate);

			String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
			foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
			String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
			foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
			Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

//			ArrayList<String> foundBestPolicyAmount = policies.findBestCancellationPolicy(secondaryDelim,
//					policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
//					chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"), calRoomCharge + "",
//					foundTotalCharge);

			reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
			reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, CancellationPolicyName,
					"Cancellation");
			reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
			//reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", foundBestPolicyAmount.get(1), "Capture");
			reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);

//			reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", foundBestPolicyAmount.get(1),
//					"Capture", "Processed");
			reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);

			reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

			reservationPage.closeReservationTab(driver);
			test_steps.add("Close Reservation");
			app_logs.info("Close Reservation");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			navigation.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.newReservation(driver);
			test_steps.addAll(getTest_Steps);
			app_logs.info("==========CREATE NEW RESERVATION FROM <b>NEW RESERVATION</b> BUTTON WITHOUT ASSOSIATED GROUP POLICIES==========");
			test_steps.add("==========CREATE NEW RESERVATION FROM <b>NEW RESERVATION</b> BUTTON WITHOUT ASSOSIATED GROUP POLICIES==========");
			// reservationPage.click_NewReservation(driver, test_steps);

//			CheckInDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 0);
//			CheckoutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 3);

			app_logs.info("CheckOut Date : " + CheckoutDate);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckoutDate);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterAdult(driver, adult);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterChildren(driver, children);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRateplan(driver, RatePlanName, "", 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickOnFindRooms(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRoom(driver, getTest_Steps, RoomClassName, "Yes", "");
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyNewPolicesApplicablePopup(driver, true, test_steps);
			reservationPage.clickNewPolicesApplicablePopupYesNo(driver, true, test_steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", ResFirstName, ResLastName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, email);
			test_steps.addAll(getTest_Steps);

//					getTest_Steps.clear();
//					getTest_Steps = reservationPage.selectReferral(driver, referral);
//					test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null, null);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickCloseReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyStatusAfterReservation(driver, foundStatus);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

			HashMap<String, String> cancellationClauseValues = reservationPage.getCancellationClauseValue(
					secondaryDelim, policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"));

			reservationPage.verifyNoCancellationPolicyAssociated(driver, test_steps);

			reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
			reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
			

			reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

			reservationPage.closeReservationTab(driver);
			test_steps.add("Close Reservation");
			app_logs.info("Close Reservation");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Groups", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Groups", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create RoomBlock

		try {
			
			navigation.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			app_logs.info("==========CREATE NEW BLOCK==========");
			test_steps.add("==========CREATE NEW BLOCK==========");

			getTest_Steps.clear();
			getTest_Steps = group.navigateRoomBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.ClickNewBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.EnterBlockName(driver, BlockName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.ClickOkay_CreateNewBlock(driver);
			test_steps.addAll(getTest_Steps);

			app_logs.info("==========SEARCH ROOMS==========");
			test_steps.add("==========SEARCH ROOMS==========");

			getTest_Steps.clear();
			getTest_Steps = group.fillReqBlockFeilds(driver, true,
					Integer.parseInt(Utility.differenceBetweenDates(CheckInDate, CheckoutDate)), RoomPerNight);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.SelectRatePlan(driver, RatePlanName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.ClickSearchGroup(driver);
			test_steps.addAll(getTest_Steps);

			advgroup.updatedAutomaticallyAssignedRooms(driver, "0");
			advgroup.BlockRoomForSelectedRoomclass(driver, RoomPerNight, RoomClassName);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			test_steps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, RoomPerNight, "Failed Room Blocked Not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		try {
			String expectedRevenueDetail = group.getExpectedRevenue_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			test_steps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

			String expectedRevenueInfo = group.getExpectedRevenue_GroupInfo(driver);
			Utility.app_logs.info("Before Group Info Expected Revenue  : " + expectedRevenueInfo);
			test_steps.add("Before Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		String getRoomNumber = null;
		try {

			test_steps.add("==========CREATE A NEW RESERVATION FORM ROOMING LIST==========");
			app_logs.info("==========CREATE A NEW RESERVATION FORM ROOMING LIST==========");

			getTest_Steps.clear();
			getTest_Steps = group.clickOnRoomingListButton(driver);
			test_steps.addAll(getTest_Steps);

			test_steps.add("Expected BlockName : " + BlockName);
			app_logs.info("Expected BlockName : " + BlockName);
			String actualBlockName = group.getBlockName(driver);
			test_steps.add("Found : " + actualBlockName);
			app_logs.info("Found : " + actualBlockName);
			assertEquals(actualBlockName, BlockName, "BlockName doesn't match");

			test_steps.add("Expected arrive date : " + CheckInDate);
			app_logs.info("Expected arrive date : " + CheckInDate);
			String actualArriveDate = group.getArriveDate(driver);
			test_steps.add("Found : " + actualArriveDate);
			app_logs.info("Found : " + actualArriveDate);
			assertEquals(actualArriveDate, Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"),
					"arrive date doesn't match");

			test_steps.add("Expected depart date : " + CheckoutDate);
			app_logs.info("Expected depart date : " + CheckoutDate);
			String actualDepartDate = group.getDepartDate(driver);
			test_steps.add("Found : " + actualDepartDate);
			app_logs.info("Found : " + actualDepartDate);
			assertEquals(actualDepartDate, Utility.parseDate(CheckoutDate, "dd/MM/yyyy", "MMM dd, yyyy"),
					"depart date doesn't match");

			test_steps.add("Expected ReservationStatus : " + "Reserved");
			app_logs.info("Expected ReservationStatus : " + "Reserved");
			String actualReservationStatus = group.getReservationStatus(driver);
			test_steps.add("Found : " + actualReservationStatus);
			app_logs.info("Found : " + actualReservationStatus);
			assertEquals("Reserved", actualReservationStatus, "reservation status  doesn't match");

			test_steps.add("Expected picked up revenue : " + "$0.00");
			app_logs.info("Expected picked up revenue : " + "$0.00");
			String actualPickup = group.getPickupRevenue(driver);
			test_steps.add("Found : " + actualPickup);
			app_logs.info("Found : " + actualPickup);
			assertEquals(actualPickup, "$0.00", "pickup revenue doesn't match");

			test_steps.add("Expected pickup percent : " + "0%");
			app_logs.info("Expected pickup percent : " + "0%");
			String actualPickupPercent = group.getPickupPercent(driver);
			test_steps.add("Found : " + actualPickupPercent);
			app_logs.info("Found : " + actualPickupPercent);
			assertEquals(actualPickupPercent, "0%", "pickup percent doesn't match");

			getTest_Steps.clear();
			getTest_Steps = group.enterReservationContentIntoRoomListingPopup(driver, ResFirstName, ResLastName,
					RatePerNight, RoomClassName);
			test_steps.addAll(getTest_Steps);

			getRoomNumber = group.getSelecteRoomNumber(driver);

			getTest_Steps.clear();
			getTest_Steps = group.clickOnBillingInfoIcon(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.enterBillingInfoInRoomListing(driver, "Mr.", "Cash", "", "");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickOnPickupButton(driver);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a reservation form roomming list", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a reservation form roomming list", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String guestName = "";
		String expectedBaseAmont = "";
		try {

			app_logs.info("==========VERIFY ROOMING LIST - DETAILS SUMMARY==========");
			test_steps.add("==========VERIFY ROOMING LIST - DETAILS SUMMARY==========");

			String getBlockName = group.getBlockNameInRoomingListSummary(driver);
			test_steps.add("Expected block name: " + BlockName);
			test_steps.add("Found: " + getBlockName);
			assertEquals(getBlockName, BlockName, "Failed: Block name is mismatching in summary");
			test_steps.add("Successfully Verified block name");
			app_logs.info("Successfully Verified block name");

			String getStatus = group.getStatusInRoomingListSummary(driver);
			test_steps.add("Expected status: " + BlockName);
			test_steps.add("Found: " + getStatus);
			assertEquals(getStatus, "Reserved", "Failed:Status is mismatching in summary");
			test_steps.add("Successfully Verified status");
			app_logs.info("Successfully Verified status");

			String getArriveDate = group.getArriveDateInRoomingListSummary(driver);
			test_steps.add("Expected arrive date: " + Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
			test_steps.add("Found: " + getStatus);
			assertEquals(getArriveDate, Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"),
					"Failed: Arrive date is mismatching in summary");
			test_steps.add("Successfully Verified arrive date");
			app_logs.info("Successfully Verified arrive date");

			String getDepartDate = group.getDepartDateInRoomingListSummary(driver);
			test_steps.add("Expected depart date: " + Utility.parseDate(CheckoutDate, "dd/MM/yyyy", "MMM dd, yyyy"));
			test_steps.add("Found: " + getStatus);
			assertEquals(getDepartDate, Utility.parseDate(CheckoutDate, "dd/MM/yyyy", "MMM dd, yyyy"),
					"Failed: Depart date is mismatching in summary");
			test_steps.add("Successfully Verified depart dates");
			app_logs.info("Successfully Verified depart dates");

			guestName = ResFirstName + " " + ResLastName;
			reservationNumber = group.getReservationNumberfromRoomingListSummary(driver, guestName);
			test_steps.add("Reservation #: " + reservationNumber);
			app_logs.info("Reservation #: " + reservationNumber);

			String getGuestName = group.getGuestNamefromRoomingListSummary(driver, reservationNumber, false);
			test_steps.add("Expected guest name: " + guestName);
			test_steps.add("Found: " + getGuestName);
			assertEquals(getGuestName, guestName, "Failed: guest name is mismatching in reservation details");
			test_steps.add("Successfully Verified guest name");
			app_logs.info("Successfully Verified guest name");

			String getArrivalDate = group.getReservationDetailsfromRoomingListSummary(driver, reservationNumber, 2);
			test_steps.add("Expected arrival date: " + Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
			test_steps.add("Found: " + getArrivalDate);
			assertEquals(getArrivalDate, Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"),
					"Failed: arriva date is mismatching in reservation details");
			test_steps.add("Successfully Verified arrival date");
			app_logs.info("Successfully Verified arrival date");

			String getDepartedDate = group.getReservationDetailsfromRoomingListSummary(driver, reservationNumber, 3);
			test_steps.add("Expected depart date: " + Utility.parseDate(CheckoutDate, "dd/MM/yyyy", "MMM dd, yyyy"));
			test_steps.add("Found: " + getDepartedDate);
			assertEquals(getDepartedDate, Utility.parseDate(CheckoutDate, "dd/MM/yyyy", "MMM dd, yyyy"),
					"Failed: depart date is mismatching in reservation detailss");
			test_steps.add("Successfully Verified arrival date");
			app_logs.info("Successfully Verified arrival date");

			// getRoomNumber = "1";
			String expecteRoomClassandNumber = RoomClassName + " : " + getRoomNumber;
			String getRoom = group.getReservationDetailsfromRoomingListSummary(driver, reservationNumber, 4);
			test_steps.add("Expected room class and room number: " + expecteRoomClassandNumber);
			test_steps.add("Found: " + getRoom);
			assertEquals(getRoom, expecteRoomClassandNumber, "Failed: Room is mismatching in reservation detailss");
			test_steps.add("Successfully Verified room");
			app_logs.info("Successfully Verified room");

			String getPaymentMethod = group.getReservationDetailsfromRoomingListSummary(driver, reservationNumber, 5);
			test_steps.add("Expected payment method: " + "Cash");
			test_steps.add("Found: " + getPaymentMethod);
			assertEquals(getPaymentMethod, "Cash", "Failed: payment method is mismatching in reservation detailss");
			test_steps.add("Successfully Verified payment method");
			app_logs.info("Successfully Verified payment method");

			group.clickonClosePickedupSummary(driver);
			test_steps.add("Click on close pickedup details summary popup");
			app_logs.info("Click on close pickedup details summary popup");
			driver.switchTo().defaultContent();

//			group.clickonCloseRoomingListPopup(driver);
//			test_steps.add("Click on close rooming list popup");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify reservatin details in rooming list -  pick up summary",
						testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify reservatin details in rooming list -  pick up summary",
						testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// here search reservation
		try {
			navigation.Reservation_Backward(driver);
			ReservationSearch reservationSearch = new ReservationSearch();
			reservationSearch.multipleSearchReservationNumber(driver, reservationNumber);
			test_steps.add("Search reservation using reservation number");

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to search reservation", testName, "Reservations", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to search reservation", testName, "Reservations", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			reservationPage.verifyStatusAfterReservation(driver, "RESERVED");

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

			HashMap<String, String> cancellationClauseValues = reservationPage.getCancellationClauseValue(
					secondaryDelim, policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"));

			reservationPage.verifyAssociatedCancellationPolicy(driver, delim, PolicyTypes, PolicyNames,
					Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), secondaryDelim),
					cancellationClauseValues, test_steps);

			String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckoutDate);

			String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
			foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
			String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
			foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
			Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

//			ArrayList<String> foundBestPolicyAmount = policies.findBestCancellationPolicy(secondaryDelim,
//					policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
//					chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"), calRoomCharge + "",
//					foundTotalCharge);

			reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
			reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, CancellationPolicyName,
					"Cancellation");
			reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
//					reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", foundBestPolicyAmount.get(1), "Capture");
//					reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
//					
//					reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", foundBestPolicyAmount.get(1), "Capture", "Processed");
//					reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);
//					
			reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

			reservationPage.closeReservationTab(driver);
			test_steps.add("Close Reservation");
			app_logs.info("Close Reservation");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			navigation.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Groups", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Groups", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String beforePickupValue = null;
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========CREATE NEW RESERVATION FROM <b>GROUP BLOCK BLUE BOOK ICON</b>==========");
			test_steps.add("==========CREATE NEW RESERVATION FROM <b>GROUP BLOCK BLUE BOOK ICON</b>==========");
			// reservationPage.click_NewReservation(driver, test_steps);

			String roomnumber = reservationPage.getRoomNumber(driver, getTest_Steps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			test_steps.add("Verified Room Number is Unassigned");

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAssociatedAccount_ReservationHeader(driver, AccountName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickOnFindRooms(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRoom(driver, getTest_Steps, RoomClassName, "Yes", "");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyNewPolicesApplicablePopup(driver, true, test_steps);
			reservationPage.clickNewPolicesApplicablePopupYesNo(driver, false, test_steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", ResFirstName, ResLastName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, email);
			test_steps.addAll(getTest_Steps);

//				getTest_Steps.clear();
//				getTest_Steps = reservationPage.selectReferral(driver, referral);
//				test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null, null);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			reservationPage.get_ReservationStatus(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickCloseReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

			HashMap<String, String> cancellationClauseValues = reservationPage.getCancellationClauseValue(
					secondaryDelim, policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"));

			reservationPage.verifyAssociatedCancellationPolicy(driver, delim, PolicyTypes, PolicyNames,
					Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), secondaryDelim),
					cancellationClauseValues, test_steps);
//				 CheckInDate = SeasonStartDate;
//				 CheckoutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", Integer.parseInt(RoomPerNight));

			String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckoutDate);

			String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
			foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
			System.out.println("found room Charge : " + foundRoomCharge);
			String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
			foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
			System.out.println("found total Charge : " + foundTotalCharge);
			Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));
			System.out.println("calculate room Charge : " + calRoomCharge);
//			ArrayList<String> foundBestPolicyAmount = policies.findBestCancellationPolicy(secondaryDelim,
//					policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
//					chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"), calRoomCharge + "",
//					foundTotalCharge);
		//	System.out.println("found best policy : " + foundBestPolicyAmount);
			reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
			reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, CancellationPolicyName,
					"Cancellation");
			reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
//			reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", foundBestPolicyAmount.get(1), "Capture");
			reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);

//			reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", foundBestPolicyAmount.get(1),
//					"Capture", "Processed");
			reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);

			reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

			reservationPage.closeReservationTab(driver);
			test_steps.add("Close Reservation");
			app_logs.info("Close Reservation");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// searching and navigate to Room Block
		try {

			navigation.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String afterPickupValue = null;
		String afterBookIconClass = null;
		try {
			afterPickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);
			assertEquals(Integer.parseInt(afterPickupValue), Integer.parseInt(beforePickupValue),
					"Failed PickUp Value Not increased");

			afterBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("After BookIcon Class : " + afterBookIconClass);
			test_steps.add("After BookIcon Class : " + afterBookIconClass);
			assertEquals(afterBookIconClass, "bookyellow", "Book Icon Color Not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========CREATE NEW RESERVATION FROM <b>GROUP BLOCK YELLOW BOOK ICON</b>==========");
			test_steps.add("==========CREATE NEW RESERVATION FROM <b>GROUP BLOCK YELLOW BOOK ICON</b>==========");
			// reservationPage.click_NewReservation(driver, test_steps);

			String roomnumber = reservationPage.getRoomNumber(driver, getTest_Steps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			test_steps.add("Verified Room Number is Unassigned");

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAssociatedAccount_ReservationHeader(driver, AccountName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickOnFindRooms(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectRoom(driver, getTest_Steps, RoomClassName, "Yes", "");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyNewPolicesApplicablePopup(driver, true, test_steps);
			reservationPage.clickNewPolicesApplicablePopupYesNo(driver, false, test_steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", ResFirstName, ResLastName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, email);
			test_steps.addAll(getTest_Steps);

//					getTest_Steps.clear();
//					getTest_Steps = reservationPage.selectReferral(driver, referral);
//					test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null, null);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			reservationPage.get_ReservationStatus(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.clickCloseReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
			test_steps.addAll(getTest_Steps);

			reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

			HashMap<String, String> cancellationClauseValues = reservationPage.getCancellationClauseValue(
					secondaryDelim, policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"));

			reservationPage.verifyAssociatedCancellationPolicy(driver, delim, PolicyTypes, PolicyNames,
					Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), secondaryDelim),
					cancellationClauseValues, test_steps);
//					 CheckInDate = SeasonStartDate;
//					 CheckoutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", Integer.parseInt(RoomPerNight));

			String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckoutDate);

			String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
			foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
			System.out.println("found room Charge : " + foundRoomCharge);
			String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
			foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
			System.out.println("found total Charge : " + foundTotalCharge);
			Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));
			System.out.println("calculate room Charge : " + calRoomCharge);
//			ArrayList<String> foundBestPolicyAmount = policies.findBestCancellationPolicy(secondaryDelim,
//					policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
//					chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"), calRoomCharge + "",
//					foundTotalCharge);
//			System.out.println("found best policy : " + foundBestPolicyAmount);
			reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
			reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, CancellationPolicyName,
					"Cancellation");
			reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
//			reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", foundBestPolicyAmount.get(1), "Capture");
			reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);

//			reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", foundBestPolicyAmount.get(1),
//					"Capture", "Processed");
			reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);

			reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

			reservationPage.closeReservationTab(driver);
			test_steps.add("Close Reservation");
			app_logs.info("Close Reservation");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			navigation.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		}catch (Exception e) {
			System.err.println("Tried to InActive Group");
		}
		
		try{
			navigation.Reservation_Backward(driver);
			navigation.Inventory(driver, test_steps);
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			ratesGrid.clickRatePlanArrow(driver, test_steps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);
			ratesGrid.clickEditIcon(driver, test_steps);
			nightlyRate.ratePlanStatusChange(driver, false, test_steps);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to delete Rates");
		}
		
		try {
			navigation.policies(driver, test_steps);
			policies.deleteAllPolicies(driver, test_steps, Utility.convertTokenToArrayList(PolicyNames, delim));
		}catch (Exception e) {
			System.err.println("Tried to delete Policy");
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
		return Utility.getData("VerifyPoliciesInGroupBasedOnRat", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
