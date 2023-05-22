package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

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

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyRatePlanForMRBInInncenter extends TestCore{
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
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyRateplanInReservation(String delim, String RatePlanName, String FolioDisplayName, String Description,
			String Channels, String RoomClasses, String isRatePlanRistrictionReq, String RistrictionType,
			String isMinStay, String MinNights, String isMaxStay, String MaxNights, String isMoreThanDaysReq,
			String MoreThanDaysCount, String isWithInDaysReq, String WithInDaysCount, String PromoCode,
			String isPolicesReq, String PoliciesType, String PoliciesName, String SeasonName, String SeasonStartDate,
			String SeasonEndDate, String isMonDay, String isTueDay, String isWednesDay, String isThursDay,
			String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults,
			String RatePerNight, String MaxAdults, String MaxPersons, String AdditionalAdultsPerNight,
			String AdditionalChildPerNight,String isAddRoomClassInSeason, String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight,
			String ExtraRoomClassMaxAdults, String ExtraRoomClassMaxPersons,
			String ExtraRoomClassAdditionalAdultsPerNight, String ExtraRoomClassAdditionalChildPerNight,
			String isSerasonLevelRules,String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClasses, String SeasonRuleType,
			String SeasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday,
			String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday,
			String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday, String isSeasonPolicies,
			String SeasonPolicyType, String SeasonPolicyValues) throws InterruptedException, IOException {

		Utility.DELIM = delim;

		test_name = "CreateNightlyRatePlanV2VerificationInReservation";
		test_description = "Create_CorpAccAnd_VerifyPayments";
		test_catagory = "CreateNightlyRatePlanV2VerificationInReservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		CPReservationPage res = new CPReservationPage();

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

			test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

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

			nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq), test_steps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
					Boolean.parseBoolean(isPolicesReq), test_steps);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
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

			nightlyRate.clickCreateSeason(driver, test_steps);
			SeasonStartDate=Utility.getDateFromCurrentDate(0);
			SeasonEndDate=Utility.getDateFromCurrentDate(30);
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
			nightlyRate.selectPolicy(driver, SeasonPolicyType, SeasonPolicyValues, Boolean.parseBoolean(isSeasonPolicies), test_steps);
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickCompleteChanges(driver, test_steps);
			nightlyRate.clickSaveAsActive(driver, test_steps);
			Wait.wait30Second();
			navigation.cpReservation_Backward(driver);
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
			//RatePlanName="TestRatedejg7WWb5K";
			String Salutation="Mr.|Mr.";
			String GuestFirstName="Primary Res|Secondary Res";
			String GuestLastName=Utility.generateRandomString()+"|"+Utility.generateRandomString();
			String PhoneNumber="8790321567|8790321533";
			String AltenativePhone="8790321577";
			String Email="nagireddy.akkala@innroad.com|nagireddy.akkala@innroad.com";
			String Account="";
			String AccountType="";
			String Address1="test1";
			String Address2="test2";
			String Address3="test3";
			String City="test";
			String Country="United States";
			String State="Alaska";
			String PostalCode="12345";
			String IsGuesProfile="No";
			String PaymentType="Cash";
			String CardNumber="";
			String NameOnCard="";
			String CardExpDate="";
			String IsChangeInPayAmount="No";
			String ChangedAmountValue="";
			String TravelAgent="";
			String MarketSegment="GDS";
			String Referral="Other";
			String IsSplitRes="No";			
			
			String Adults="2|2";
			String Children="0|0";
			
			ArrayList<String> Rooms = new ArrayList<String>();
			
			test_steps.add("=================== CREATE RESERVATION ======================");
			app_logs.info("=================== CREATE RESERVATION ======================");
			ArrayList<String> RoomAbbri = new ArrayList<String>();
			
			RoomAbbri.add("NR");
			RoomAbbri.add("NR2");
			
			String CheckOutDate=Utility.getDateFromCurrentDate(4);
			String CheckOutDate1=Utility.getDateFromCurrentDate(6);
			res.click_NewReservation(driver, test_steps);
			res.select_DatesForRatePlan(driver, test_steps, CheckOutDate, CheckOutDate1, Adults, Children, RatePlanName, PromoCode,IsSplitRes);
			if(IsSplitRes.equalsIgnoreCase("Yes")) {
				res.enter_Adults(driver, test_steps, Adults);
				res.enter_Children(driver, test_steps, Children);
				res.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			}
			
			String[] RoomClasses1=RoomClasses.split("\\|");
			String[] rate=RatePerNight.split("\\|");
			String RoomClass=RoomClasses1[0]+"|"+RoomClasses1[1];
			res.clickOnFindRooms(driver, test_steps);
			ArrayList roomCost=res.select_MRBRoomsRatePlan(driver, test_steps, RoomClass, "Yes",Account,Adults);
			System.out.println("roomCost : "+roomCost);
			Double depositAmount=res.deposit(driver, test_steps, "No", "");
			res.clickNext(driver, test_steps);
			res.enter_MRB_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, "No", IsSplitRes,Rooms);
			if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			System.out.println(Rooms);
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
			res.clickBookNow(driver, test_steps);
			String reservation=res.get_ReservationConfirmationNumber(driver, test_steps);
			String status=res.get_ReservationStatus(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			String TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			
			res.click_Folio(driver, test_steps);

			ArrayList roomChnrges=res.getMRBFolioBalance(driver, roomCost, RoomAbbri, "Yes", Rooms);
			
			String folioRoomCharges=(String) roomChnrges.get(0);
			String folioRoomCharges1=(String) roomChnrges.get(1);

			res.click_DeatilsTab(driver, test_steps);
			int days=Utility.getNumberofDays(CheckOutDate, CheckOutDate1);

			double expectedRoomCharges=days*Double.parseDouble(rate[1])+days*Double.parseDouble(rate[0]);

			folioRoomCharges=folioRoomCharges.trim();

			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
			TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();

			Double folioRCharges=Double.parseDouble(folioRoomCharges);
			Double folioRCharges1=Double.parseDouble(folioRoomCharges1);
			
			Double roomCharge=folioRCharges+folioRCharges1;
			
			Double tripRoom=Double.parseDouble(TripSummaryRoomCharges);
			try {
				assertTrue(Double.compare(expectedRoomCharges, roomCharge)==0, "Expeced room charges and folio room charges are not matched");
				test_steps.add("Expected room charges and folio room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and folio room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}

			try {
				assertTrue(Double.compare(expectedRoomCharges, tripRoom)==0, "Expeced room charges and trip summary room charges are not matched");
				test_steps.add("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}

			test_steps.add("=================== Cancel reservation ======================");
			app_logs.info("=================== Cancel reservation ======================");

			res.cancelReservation(driver, test_steps,"Test Cancel");
			Wait.wait25Second();
			TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
			TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();

			tripRoom=Double.parseDouble(TripSummaryRoomCharges);

			try {
				assertTrue(Double.compare(0.00, tripRoom)==0, "Expeced room charges and folio room charges are not matched");
				test_steps.add("Expected room charges and folio room charges are same : "+tripRoom);
				app_logs.info("Expected room charges and folio room charges are same : "+tripRoom);
			}catch(Error e) {
				test_steps.add(e.toString());
			}
			
			test_steps.add("=================== Roll back reservation ======================");
			app_logs.info("=================== Roll back reservation ======================");
			res.rollBackAll(driver, roomChnrges);
			Wait.wait25Second();
			TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			
			res.click_Folio(driver, test_steps);
			
			
			roomChnrges=res.getMRBFolioBalance(driver, roomCost, RoomAbbri, "Yes", Rooms);
			
			folioRoomCharges=(String) roomChnrges.get(0);
			folioRoomCharges1=(String) roomChnrges.get(1);

			res.click_DeatilsTab(driver, test_steps);
			days=Utility.getNumberofDays(CheckOutDate, CheckOutDate1);

			expectedRoomCharges=days*Double.parseDouble(rate[1])+days*Double.parseDouble(rate[0]);

			folioRoomCharges=folioRoomCharges.trim();

			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
			TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();

			folioRCharges=Double.parseDouble(folioRoomCharges);
			folioRCharges1=Double.parseDouble(folioRoomCharges1);
			
			roomCharge=folioRCharges+folioRCharges1;
			
			tripRoom=Double.parseDouble(TripSummaryRoomCharges);
			try {
				assertTrue(Double.compare(expectedRoomCharges, roomCharge)==0, "Expeced room charges and folio room charges are not matched");
				test_steps.add("Expected room charges and folio room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and folio room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}

			try {
				assertTrue(Double.compare(expectedRoomCharges, tripRoom)==0, "Expeced room charges and trip summary room charges are not matched");
				test_steps.add("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}
			
			
			res.close_FirstOpenedReservation(driver, test_steps);
			
			
			res.click_NewReservation(driver, test_steps);
			res.select_DatesForRatePlan(driver, test_steps, CheckOutDate, CheckOutDate1, Adults, Children, RatePlanName, PromoCode,IsSplitRes);
			if(IsSplitRes.equalsIgnoreCase("Yes")) {
				res.enter_Adults(driver, test_steps, Adults);
				res.enter_Children(driver, test_steps, Children);
				res.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			}
			
			
			roomCost.clear();
			Rooms.clear();
			RoomClasses1=RoomClasses.split("\\|");
			rate=RatePerNight.split("\\|");
			RoomClass=RoomClasses1[0]+"|"+RoomClasses1[1];
			res.clickOnFindRooms(driver, test_steps);
			roomCost=res.select_MRBRoomsRatePlan(driver, test_steps, RoomClass, "Yes",Account,Adults);
			System.out.println("roomCost : "+roomCost);
			depositAmount=res.deposit(driver, test_steps, "No", "");
			res.clickNext(driver, test_steps);
			res.enter_MRB_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, "No", IsSplitRes,Rooms);
			if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			System.out.println(Rooms);
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
			res.clickBookNow(driver, test_steps);
			reservation=res.get_ReservationConfirmationNumber(driver, test_steps);
			status=res.get_ReservationStatus(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			
			res.click_Folio(driver, test_steps);
			
			
			roomChnrges=res.getMRBFolioBalance(driver, roomCost, RoomAbbri, "Yes", Rooms);
			
			folioRoomCharges=(String) roomChnrges.get(0);
			folioRoomCharges1=(String) roomChnrges.get(1);

			res.click_DeatilsTab(driver, test_steps);
			days=Utility.getNumberofDays(CheckOutDate, CheckOutDate1);

			expectedRoomCharges=days*Double.parseDouble(rate[1])+days*Double.parseDouble(rate[0]);

			folioRoomCharges=folioRoomCharges.trim();

			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
			TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();

			folioRCharges=Double.parseDouble(folioRoomCharges);
			folioRCharges1=Double.parseDouble(folioRoomCharges1);
			
			roomCharge=folioRCharges+folioRCharges1;
			
			tripRoom=Double.parseDouble(TripSummaryRoomCharges);
			try {
				assertTrue(Double.compare(expectedRoomCharges, roomCharge)==0, "Expeced room charges and folio room charges are not matched");
				test_steps.add("Expected room charges and folio room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and folio room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}

			try {
				assertTrue(Double.compare(expectedRoomCharges, tripRoom)==0, "Expeced room charges and trip summary room charges are not matched");
				test_steps.add("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}

			test_steps.add("=================== No Show reservation ======================");
			app_logs.info("=================== No Show reservation ======================");

			res.noShowReservation(driver, test_steps);
			Wait.wait25Second();
			TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
			TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();

			tripRoom=Double.parseDouble(TripSummaryRoomCharges);

			try {
				assertTrue(Double.compare(0.00, tripRoom)==0, "Expeced room charges and folio room charges are not matched");
				test_steps.add("Expected room charges and folio room charges are same : "+tripRoom);
				app_logs.info("Expected room charges and folio room charges are same : "+tripRoom);
			}catch(Error e) {
				test_steps.add(e.toString());
			}
			
			test_steps.add("=================== Roll back reservation ======================");
			app_logs.info("=================== Roll back reservation ======================");
			res.rollBackAll(driver, roomChnrges);
			Wait.wait25Second();
			TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			
			res.click_Folio(driver, test_steps);
			
			
			roomChnrges=res.getMRBFolioBalance(driver, roomCost, RoomAbbri, "Yes", Rooms);
			
			folioRoomCharges=(String) roomChnrges.get(0);
			folioRoomCharges1=(String) roomChnrges.get(1);

			res.click_DeatilsTab(driver, test_steps);
			days=Utility.getNumberofDays(CheckOutDate, CheckOutDate1);

			expectedRoomCharges=days*Double.parseDouble(rate[1])+days*Double.parseDouble(rate[0]);

			folioRoomCharges=folioRoomCharges.trim();

			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
			TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
			TripSummaryRoomCharges=TripSummaryRoomCharges.trim();

			folioRCharges=Double.parseDouble(folioRoomCharges);
			folioRCharges1=Double.parseDouble(folioRoomCharges1);
			
			roomCharge=folioRCharges+folioRCharges1;
			
			tripRoom=Double.parseDouble(TripSummaryRoomCharges);
			try {
				assertTrue(Double.compare(expectedRoomCharges, roomCharge)==0, "Expeced room charges and folio room charges are not matched");
				test_steps.add("Expected room charges and folio room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and folio room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}

			try {
				assertTrue(Double.compare(expectedRoomCharges, tripRoom)==0, "Expeced room charges and trip summary room charges are not matched");
				test_steps.add("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
				app_logs.info("Expected room charges and trip summary room charges are same : "+expectedRoomCharges);
			}catch(Error e) {
				test_steps.add(e.toString());
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyRateplanMRBInInncenter", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
