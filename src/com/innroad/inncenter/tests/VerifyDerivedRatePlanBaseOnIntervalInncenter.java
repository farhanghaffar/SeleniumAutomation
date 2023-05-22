package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.store.MMapDirectory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.RatesGridChannelRatesRules;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import bsh.ParseException;

public class VerifyDerivedRatePlanBaseOnIntervalInncenter extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	CPReservationPage reservationPage = new CPReservationPage();
	boolean restricrionsBookingWindowChange = false;
	boolean restricrionsLengthOfStayChange = false;
	boolean isBookinWindowChange = false;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private void getData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			testSteps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "getData")
	public void verifyDerivedRatePlanBaseOnIntervalInncenter(String delim, String parentRatePlanName, String FolioDisplayName,
			String Description, String Channels, String RoomClasses, String isRatePlanRistrictionReq,
			String RistrictionType, String isMinStay, String MinNights, String isMaxStay, String MaxNights,
			String isMoreThanDaysReq, String MoreThanDaysCount, String isWithInDaysReq, String WithInDaysCount,
			String PromoCode, String isPolicesReq, String PoliciesType, String PoliciesName, String SeasonName,
			String seasonStartDate, String seasonEndDate, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,

			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,

			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight,

			String isSerasonLevelRules, String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClasses,
			String SeasonRuleType, String SeasonRuleMinStayValue, String isSeasonRuleOnMonday,
			String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday,
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String isSeasonPolicies, String SeasonPolicyType, String SeasonPolicyValues,

			String interval, String IsProRateStayInRate, String isProRateStayInSeason, String isProRateInRoomClass,
			String ProRateRoomClassName, String IsCustomPerNight, String CustomeRoomClass, String CustomRatePerNight,
			String isCustomRatePerNightAdultandChild

			, String CustomRateAdultPerNight, String CustomRateChildPerNight, String isAssignPolicyByRoomClass,
			String RoomClassInPolicy,
			
			 String derivedRatePlanName, String derivedRatePlanFolioDisplayName, String derivedRatePlanDescription,
				String istakenRulesFromParentRateplan, String dateRange, String customStartDate, String customEndDate,
				String selectComparator, String derivedRateType, String derivedRateValue, String derivedRateRoomClasses,
				String derivedRateChannels, String derivedRateIsRatePlanRistrictionReq, String derivedRateRistrictionType,
				String derivedRateIsMinStay, String derivedRateMinNights, String derivedRateIsMaxStay,
				String derivedRateMaxNights, String derivedRateIsMoreThanDaysReq, String derivedRateMoreThanDaysCount,
				String derivedRateIsWithInDaysReq, String derivedRateWithInDaysCount, String derivedRatePromoCode,
				String derivedRateIsPolicesReq, String derivedRatePoliciesType, String derivedRatePoliciesName,

			String ReservationType, String CheckInDate, String CheckOutDate, String ReservationRoomClasses,
			String RulesUpdateType, String RulesUpdateStartDate, String RulesUpdateEndDate, String isSun_RulesUpdate,
			String isMon_RulesUpdate, String isTue_RulesUpdate, String isWed_RulesUpdate, String isThu_RulesUpdate,
			String isFri_RulesUpdate, String isSat_RulesUpdate, String Type_RulesUpdate,
			String RuleMinStayValue_RulesUpdate,

			String adult, String children, String isAccountPolicyApplicable,String ActionOnReservation,
			String ReservationChangeClass,String ChangeResStartDate,String ChangeResEndDate)
			throws Exception {

		//
		Utility.DELIM = "\\"+delim;

		test_name = "VerifyDerivedRatePlanBaseOnIntervalInncenter";
		testDescription = "Create interval rate plan and verify in reservation";
		testCategory = "Interval Rateplal";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		CPReservationPage reservationPage = new CPReservationPage();
		DerivedRate derivedRate = new DerivedRate();
		Groups group = new Groups();
		AdvGroups advgroup = new AdvGroups();
		Account CreateTA = new Account();
		NewRoomClassesV2 rc = new NewRoomClassesV2();
		Policies policy = new Policies();

		String channelName = "innCenter";
		String reervationNoShowPolicy = "No Policy";
		String reervationDepositPolicy = "No Policy";
		String reervationCheckInPolicy = "No Policy";

		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> Rooms = new ArrayList<String>();
		boolean isBaseRateplanExist = false;
		boolean isDerivedRateplanExist = false;
		boolean isSeasonExist = true;
		boolean isRoomClassAvailable = true;
		String PolicyTypes = "";
		String PolicyNames = "";
		String TypeOfFees = "";
		String GuestsWillIncurAFee = "";
		String ChargesType = "";
		String NoOfDays = "";
		String CancelWithInType = "";
		boolean isPromocode = false;
		boolean isMinStayRule = false;
		boolean isMinStayRuleBrokenPopComeOrNot = false;
		ArrayList<String> minStayRule = null;
		ArrayList<String> minrule = null;
		ArrayList<String> noCheckInRule = null;
		ArrayList<String> noCheckOutRule = null;
		String checkInColor = null;
		String checkOutColor = null;
		boolean verifyLenthOfStayChecked = false;
		boolean verifyMinStayCondidtion = false;
		boolean verifyMaxStayCondition = false;
		boolean restricrionsBookingWindow = false;
		boolean restricrionsLengthOfStay = false;
		int minStayRuleValue = 0;
		boolean isBookinWindow = false;
		String folioName = null;
		String reservationRoomClassBaseRate = null;
		String reservationRoomClassDerivedRate = null;

		String customDateRange = "Custom date range";
		String calendarTodayDay = "today";
		String seasonDateFormat = "dd/M/yyyy";
		String calendarSelectedDay = "selected";
		String todayDate = null;
		String timeZone = "America/New_York";
		String seasonDuration = "2";

		String rateIs = "";
		String[] RoomClass = RoomClasses.split(Utility.DELIM);

		String[] rate = RatePerNight.split(Utility.DELIM);
		String reservation = "";
		String status = "";
		boolean ReservationChangeRoomClass = false;
		int roomClassIndex = 0;
		 String[] roomClasses = ReservationRoomClasses.split(Utility.DELIM);
			for (int i = 0; i < roomClasses.length; i++) {
			app_logs.info("Room Class : " + roomClasses[i]);
			}
		boolean isRoomClassAvailableChange = true;
		// String ReservationRoomClasses = "";
		HashMap<String, String> capacityAdult = new HashMap<String, String>(),
				capacityChild = new HashMap<String, String>(), maxAdult = new HashMap<String, String>(),
				maxChild = new HashMap<String, String>();
		HashMap<String, String> getRates = new HashMap<String, String>();
		HashMap<String, String> getExAdult = new HashMap<String, String>();
		HashMap<String, String> getExChild = new HashMap<String, String>();
		HashMap<String, String> getRatesPerNightChannels = new HashMap<String, String>();
		List<Date> dates = new ArrayList<Date>();
		List dates1 = new ArrayList();
		HashMap<String, String> getRateLevelPolicy = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicy = new HashMap<String, String>();

		boolean isVerifyPolicies = true;

		ArrayList<String> minStayRuleMRB = null;
		ArrayList<String> minruleMRB = null;
		ArrayList<Integer> minStayRuleValueMRB = new ArrayList<Integer>();
		ArrayList<Boolean> isMinStayRuleMRB = new ArrayList<Boolean>();
		ArrayList<Boolean> isMinStayRuleBrokenPopComeOrNotMRB = new ArrayList<Boolean>();
		ArrayList noCheckInRuleMRB = new ArrayList<String>();
		ArrayList noCheckOutRuleMRB = new ArrayList<String>();
		ArrayList<String> checkInColorMRB = new ArrayList<String>();
		ArrayList<String> checkOutColorMRB = new ArrayList<String>();
		ArrayList<Boolean> restricrionsLengthOfStayMRB = new ArrayList<Boolean>();
		ArrayList<Boolean> restricrionsBookingWindowMRB = new ArrayList<Boolean>();
		ArrayList<String> reservationRoomClassesList = new ArrayList<String>();
		ArrayList<HashMap<String, String>> ratesList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> exAdultList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> exChildList = new ArrayList<HashMap<String, String>>();
		HashMap<String, ArrayList<String>> getRoomClassWithRates = new HashMap<>();
		ArrayList<String> seasonDepositPolicy = new ArrayList<String>();
		ArrayList<String> seasonCheckInPolicy = new ArrayList<String>();
		ArrayList<String> getRatesWithAdultsAndChild = new ArrayList<>();
		ArrayList<ArrayList<String>> rateListInReservation = new ArrayList<>();
		ArrayList<String> foundBestPolicyAmount = null;
		ArrayList<String> seasonDepositPolicyChange = new ArrayList<String>();
		ArrayList<String> seasonCheckInPolicyChange = new ArrayList<String>();
		HashMap<String, String> getRateLevelPolicyChange = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicyChange = new HashMap<String, String>();

		HashMap<String, String> names = null;
		HashMap<String, String> policyClauses = null;
		HashMap<String, String> guestsWillIncurAFee = null;
		HashMap<String, String> chargesType = new HashMap<String, String>();
		HashMap<String, String> cancelWithInType = null;
		HashMap<String, String> noOfDays = null;
		
		// stay details change
		
		boolean isSeasonExistChange = true;					
		boolean isPromocodeChnage = false;
		boolean isMinStayRuleChnage = false;
		boolean isMinStayRuleBrokenPopComeOrNotChange = false;
		ArrayList<String> minStayRuleChnage = null;
		ArrayList<String> minruleChange = null;
		ArrayList<String> noCheckInRuleChnage = null;
		ArrayList<String> noCheckOutRuleChnage	 = null;
		String checkInColorChnage = null;
		int minStayRuleValueChange = 0;
		String checkOutColorChange = null;
		boolean verifyLenthOfStayCheckedChange = false;
		boolean verifyMinStayCondidtionChange = false;
		boolean verifyMaxStayConditionChange = false;
		HashMap<String, String> addStayofLengthChange = new HashMap<>();
		boolean restricrionsBookingWindowChange = false;
		boolean restricrionsLengthOfStayChange = false;
		boolean isBookinWindowChange = false;
		String PromoCodeChange="";
		boolean isVerifyPoliciesChange = true;
		HashMap<String, String> capacityAdultChange = new HashMap<String, String>(),
				capacityChildChange = new HashMap<String, String>(), maxAdultChange = new HashMap<String, String>(),
				maxChildChange = new HashMap<String, String>();
		HashMap<String, String> getRatesChange = new HashMap<String, String>();
		HashMap<String, String> getExAdultChange = new HashMap<String, String>();
		HashMap<String, String> getExChildChange = new HashMap<String, String>();
		HashMap<String, String> getRatesPerNightChannelsChange = new HashMap<String, String>();
		List<Date> datesChnage = new ArrayList<Date>();
		ArrayList<String> parentRatePlanOffSetList = new ArrayList<String>();
		
		ArrayList<String> baseRate = new ArrayList<>();
		ArrayList<String> adultCapacity = new ArrayList<>();
		ArrayList<String> personCapacity = new ArrayList<>();
		ArrayList<Boolean> isAdditonalAdultChild = new ArrayList<>();
		ArrayList<String> maxAdult1 = new ArrayList<>();
		ArrayList<String> maxPerson = new ArrayList<>();
		ArrayList<String> adultRate = new ArrayList<>();
		ArrayList<String> childRate1 = new ArrayList<>();
		ArrayList<Boolean> isProStayRate = new ArrayList<>();
		ArrayList<String> customRatePerNight = new ArrayList<>();
		ArrayList<String> adultRatePerNight = new ArrayList<>();
		ArrayList<String> childRatePerNight = new ArrayList<>();
		
        // end here
		int days = 0;
		if (!ReservationType.equalsIgnoreCase("MRB")) {
			days = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		}

		if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")||ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
			daysChnage=Utility.getNumberofDays(ChangeResStartDate, ChangeResEndDate);
		}
		
		// if (Utility.rowCount ==0) {
		Utility.rowCount = Utility.rowCount + 1;

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
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
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

//		RoomAbbri.add("NR");
	//	RoomAbbri.add("NR2");
		if (ReservationType.equalsIgnoreCase("MRB") || ReservationType.equalsIgnoreCase("tapechart")
				|| ActionOnReservation.equalsIgnoreCase("Cancellation")) {

			testSteps.add("=================== Getting the Room Class Abbrivation ======================");
			app_logs.info("=================== Getting the Room Class Abbrivation ======================");
			try {
				navigation.Setup(driver);
				navigation.RoomClass(driver);
				RoomAbbri = rc.getAbbrivation(driver, delim, ReservationRoomClasses, testSteps);
				Utility.app_logs.info(RoomAbbri);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		try {
			navigation.Inventory(driver, testSteps);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.RatesGrid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");
		/*	getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, seasonDateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", seasonDateFormat, getTestSteps);
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
			app_logs.info("season Parsed Date : " + seasonStartDate.split(Utility.DELIM)[0]);
			app_logs.info("season Parsed Date : " + seasonStartDate.split(Utility.DELIM)[0]);
			if (Utility.compareDates(seasonStartDate.split(Utility.DELIM)[0], todayDate, seasonDateFormat) >= 0) {

			} else {
				seasonStartDate = todayDate;
				seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), seasonDateFormat, seasonStartDate,
						seasonDateFormat, timeZone);

				customStartDate = seasonStartDate;
				customEndDate = seasonEndDate;
				CheckInDate = seasonStartDate;
				CheckOutDate = seasonEndDate;
				
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
			testSteps.addAll(getTestSteps); */

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

		testSteps.add("=================== Verify Base Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Base Rate plan Exist or Not ======================");
		try {
			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			if (ratesGrid.searchAndVerifyRatePlanExist(driver, parentRatePlanName, true, testSteps)) {
				isBaseRateplanExist = true;
				testSteps.add("Successfully verified Base Rate Plan(" + parentRatePlanName + ") exist");
				app_logs.info("Successfully verified Base Rate Plan(" + parentRatePlanName + ") exist");
			} else {
				testSteps.add("Successfully verified Base Rate Plan(" + parentRatePlanName + ") not exist");
				app_logs.info("Successfully verified Base Rate Plan(" + parentRatePlanName + ") not exist");
			}
//			isBaseRateplanExist = ratesGrid.isRatePlanExist(driver, baseIntervalRatePlanName, testSteps);
//			System.out.println("israteplanExist : " + isBaseRateplanExist);
//			app_logs.info("israteplanExist : " + isBaseRateplanExist);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (!isBaseRateplanExist) {
			try {

				testSteps.add("=================== CREATE NEW INTERVAL RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW INTERVAL RATE PLAN ======================");

				ratesGrid.clickRateGridAddRatePlan(driver);
				ratesGrid.clickRateGridAddRatePlanOption(driver, "Interval rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Interval rate plan", testSteps);

				testSteps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				parentRatePlanName = parentRatePlanName + Utility.generateRandomString();
				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, parentRatePlanName, testSteps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
				nightlyRate.enterRatePlanDescription(driver, Description, testSteps);
				nightlyRate.clickNextButton(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.enterInterval(driver, interval);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, Boolean.parseBoolean(IsProRateStayInRate));
				testSteps.addAll(getTestSteps);

				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, testSteps);

				testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, testSteps);
				// String summaryChannels =
				// nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, testSteps);

				// nightlyRate.verifyTitleSummaryValue(driver, "Channel",
				// summaryChannels, testSteps);

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

			//	nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						testSteps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), testSteps);
				nightlyRate.clickNextButton(driver, testSteps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), testSteps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to enter rate plan level value", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to enter rate plan level value", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, testSteps);

				nightlyRate.createSeasonForIntervalRatePlan(driver, testSteps, seasonStartDate, seasonEndDate,
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

				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.clickSaveAsActive(driver, testSteps);
				// navigation.cpReservation_Backward(driver);
				testSteps.add("=================== BASE INTERVAL RATE PLAN CREATED ======================");
				app_logs.info("=================== BASE INTERVAL RATE PLAN CREATED ======================");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create interval rate plan", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create interval rate plan", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}
		
		
		//DerivedratePlan Verification and creation
		
		testSteps.add("=================== Verify Derived Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Derived Rate plan Exist or Not ======================");
		try {
			
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			try{
				isDerivedRateplanExist = derivedRate.isDerivedratePlanExist(driver, derivedRatePlanName, testSteps);
			}catch(Exception e){
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				isDerivedRateplanExist = derivedRate.isDerivedratePlanExist(driver, derivedRatePlanName, testSteps);
			}
			System.out.println("isDrivedRateplanExist : " + isDerivedRateplanExist);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		if(!isDerivedRateplanExist) {
			try {

				testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

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
					Utility.updateReport(e, "Failed to select derived rate plan option", testName,
							"DerivedRatePlanCreation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to select derived rate plan option", testName,
							"DerivedRatePlanCreation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);
				nightlyRate.enterRateFolioDisplayName(driver, derivedRatePlanFolioDisplayName, testSteps);
				nightlyRate.enterRatePlanDescription(driver, derivedRatePlanDescription, testSteps);
				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add("=================== " + "SELECT PARENT " + parentRatePlanName.toUpperCase()
						+ " ======================");
				app_logs.info("=================== " + "SELECT PARENT " + parentRatePlanName.toUpperCase()
						+ " ======================");

				derivedRate.selectRatePlan(driver, parentRatePlanName, true, testSteps);

				testSteps.add("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
						+ " ======================");
				app_logs.info("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
						+ " ======================");

				derivedRate.expandCurrencyValueDropdown(driver, 1);
				testSteps.add("Expand Value Comparison DropDown");
				app_logs.info("Expand Value Comparison DropDown");
				derivedRate.selectDropDownOptions(driver, selectComparator, testSteps);
				derivedRate.expandCurrencyValueDropdown(driver, 0);
				testSteps.add("Expand Currency DropDown");
				app_logs.info("Expand Currency DropDown");

				derivedRate.selectDropDownOptions(driver, derivedRateType, testSteps);

				testSteps.add("===== ENTER VALUE =====");
				app_logs.info("===== ENTER VALUE =====");
				derivedRate.enterRateValue(driver, derivedRateValue, testSteps);
				String value = derivedRate.getRateValue(driver, testSteps);
				if (derivedRateType.equals("percent")) {
					Assert.assertEquals(value, derivedRateValue + "%", "Failed : Value missmatched");
				} else {
					Assert.assertEquals(value, derivedRateValue, "Failed : Value missmatched");
				}

				derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(istakenRulesFromParentRateplan),
						testSteps);
				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);
				testSteps.add("=================== SELECT DATES ======================");
				app_logs.info("=================== SELECT DATES ======================");
				derivedRate.selectDates(driver, dateRange, testSteps);
				if (dateRange.equals(customDateRange)) {
					derivedRate.customDateRangeAppear(driver, true, testSteps);
					getTestSteps.clear();
					getTestSteps = derivedRate.selectCustomStartAndEndDates(driver, customStartDate, customEndDate,
							seasonDateFormat);
					testSteps.addAll(getTestSteps);
				}
				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);
				testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
				nightlyRate.selectChannels(driver, derivedRateChannels, true, testSteps);
				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");
				nightlyRate.selectRoomClasses(driver, derivedRateRoomClasses, true, testSteps);

				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq),
						derivedRateRistrictionType, Boolean.parseBoolean(derivedRateIsMinStay), derivedRateMinNights,
						Boolean.parseBoolean(derivedRateIsMaxStay), derivedRateMaxNights,
						Boolean.parseBoolean(derivedRateIsMoreThanDaysReq), derivedRateMoreThanDaysCount,
						Boolean.parseBoolean(derivedRateIsWithInDaysReq), derivedRateWithInDaysCount, derivedRatePromoCode,
						testSteps);

				getTestSteps.clear();
				nightlyRate.verifySelectedRestriction(driver, derivedRateRistrictionType,
						Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq), getTestSteps);
				testSteps.addAll(getTestSteps);

				if (Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq)) {
					String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(derivedRateRistrictionType,
							Boolean.parseBoolean(derivedRateIsMinStay), derivedRateMinNights,
							Boolean.parseBoolean(derivedRateIsMaxStay), derivedRateMaxNights,
							Boolean.parseBoolean(derivedRateIsMoreThanDaysReq), derivedRateMoreThanDaysCount,
							Boolean.parseBoolean(derivedRateIsWithInDaysReq), derivedRateWithInDaysCount,
							derivedRatePromoCode);

					getTestSteps.clear();
					nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, getTestSteps);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				nightlyRate.getRestrictionsToQualifyRate(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);

				nightlyRate.selectPolicy(driver, derivedRatePoliciesType, derivedRatePoliciesName,
						Boolean.parseBoolean(derivedRateIsPolicesReq), testSteps);
				getTestSteps.clear();
				nightlyRate.verifySelectedPolicy(driver, derivedRatePoliciesType,
						Boolean.parseBoolean(derivedRateIsPolicesReq), getTestSteps);
				testSteps.addAll(getTestSteps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver,
						derivedRatePoliciesName, Boolean.parseBoolean(derivedRateIsPolicesReq), testSteps);

				getTestSteps.clear();
				getTestSteps = nightlyRate.clickNextButton(driver);
				testSteps.addAll(getTestSteps);

				nightlyRate.clickSaveAsActive(driver, testSteps);
				Utility.rateplanName = derivedRatePlanName;
				testSteps.add("=================== DERIVED RATE PLAN CREATED ======================");
				app_logs.info("=================== DERIVED RATE PLAN CREATED ======================");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

			try {
				navigation.RatesGrid(driver);
				testSteps.add("Navigated to RatesGrid");
				testSteps.add("======== "
						+ "Verify the Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
								.toUpperCase()
						+ " =========");
				app_logs.info("=================== "
						+ "Verify the Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
								.toUpperCase()
						+ " ======================");
				driver.navigate().refresh();
				Wait.wait3Second();
				driver.navigate().refresh();
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, derivedRatePlanName);
				testSteps.add(
						"Successfully verified that Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
				app_logs.info(
						"Successfully verified that Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
							testName, "DerivedRatePlanVerification", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
							testName, "DerivedRatePlanVerification", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				testSteps.add("====="
						+ "Verify the Derived Rate Plan in the Rates Grid's Rate Plan drop down".toUpperCase()
						+ " ======");
				app_logs.info("=================== "
						+ "Verify the Derived Rate Plan in the Rates Grid's Rate Plan drop down".toUpperCase()
						+ " ======================");

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyDerivedRateDisplay(driver, derivedRatePlanName);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"Successfully verified that Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
				app_logs.info(
						"Successfully verified that Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
							testName, "DerivedRatePlanVerification", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
							testName, "DerivedRatePlanVerification", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			ArrayList<String> selectedChannels = null;
			ArrayList<String> selectedRoomClasses = null;
			HashMap<String, String> selectedLengthofStayRestrictions = new HashMap<>();
			String selectedPromoCode = null;
			String selectedSeasonType = "";
			HashMap<Integer, String> customDatesStart = new HashMap<>();
			HashMap<Integer, String> customDatesEnd = new HashMap<>();
			String startSeasonDate = "";
			String endSeasonDate = "";
			ArrayList<String> allDatesBW = null;
			HashMap<String, String> selectedBookingWindowRestrictions = new HashMap<>();
			
			try {	ArrayList<String> allSeasonsDates = null;
				testSteps.add(
						"=================== GETTING DERIVED RATE PLAN CHANNELS AND ROOM CLASESS ======================");
				app_logs.info(
						"=================== GETTING DERIVED RATE PLAN CHANNELS AND ROOM CLASESS ======================");

				if (isDerivedRateplanExist) {
					ratesGrid.clickRatePlanArrow(driver, testSteps);
					ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
					derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
					derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, testSteps);
					folioName = ratesGrid.getFolioNameOfRatePlan(driver, testSteps);
					System.out.println("folioName : " + folioName);
					Utility.app_logs.info("Selected Folio Name : " + folioName);
					testSteps.add("Selected Folio Name : " + folioName);
	
					parentRatePlanOffSetList = derivedRate.getParentPlanOffset(driver);
					Utility.app_logs.info("Selected OffSet Value  : " + parentRatePlanOffSetList);
					testSteps.add("Selected OffSet Value  : " + parentRatePlanOffSetList);
					selectedChannels = nightlyRate.getSelectedChannels(driver);
					selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
					Utility.app_logs.info("Selected Channels : " + selectedChannels);
					testSteps.add("Selected Channels : " + selectedChannels);
					Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
					testSteps.add("Selected Room Classes : " + selectedRoomClasses);
					
				
					ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, testSteps);

					Utility.app_logs.info("Selected Policies: " + getRateLevelPolicy);
					testSteps.add("Selected Policies : " + getRateLevelPolicy);
					testSteps.add("=================== GETTING DERIVED RATE PLAN SEASON TYPE ======================");
					app_logs.info("=================== GETTING DERIVED RATE PLAN SEASON TYPE ======================");
					nightlyRate.switchCalendarTab(driver, testSteps);
					Wait.waitUntilPageLoadNotCompleted(driver, 40);
					selectedSeasonType = derivedRate.getSelectedSeasonType(driver);
					Utility.app_logs.info(selectedSeasonType);
					Utility.app_logs.info("Selected Season Type : " + selectedSeasonType);
					testSteps.add("Selected Season Type : " + selectedSeasonType);
					
					if(selectedSeasonType.equalsIgnoreCase("Always available")) {
						navigation.ratesGrid(driver);
		
						ratesGrid.clickRatePlanArrow(driver, testSteps);
						ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
						Wait.waitUntilPageLoadNotCompleted(driver, 40);
						derivedRate.expandReduceDerivedratePlans(driver, false, testSteps);
						ratesGrid.clickEditIcon(driver, testSteps);
						nightlyRate.switchCalendarTab(driver, testSteps);
		
						allSeasonsDates = nightlyRate.getAllSeasonsDates(driver);
						Utility.app_logs.info("All Season Dates : " + allSeasonsDates);
						testSteps.add("All Season Dates : " + allSeasonsDates);
		
						nightlyRate.clickSaveRatePlanButton(driver, testSteps);
						
						startSeasonDate = allSeasonsDates.get(0);
						endSeasonDate = allSeasonsDates.get(allSeasonsDates.size() - 1);
						
						allDatesBW = Utility.getAllDatesBetweenCheckInOutDates(startSeasonDate,endSeasonDate);
					}else {
						
						customDatesStart = derivedRate.getCustomDates(driver, "Custom date range", "Start Date");
						testSteps.add("Custom start date ranges :  " + customDatesStart);
						app_logs.info("Custom start date ranges :  " + customDatesStart);
						System.out.println(customDatesStart.size());
						customDatesEnd = derivedRate.getCustomDates(driver, "Custom date range", "End Date");
						testSteps.add("Custom end date ranges :  " + customDatesEnd);
						app_logs.info("Custom end date ranges :  " + customDatesEnd);
						System.out.println(customDatesEnd.size());
						
						startSeasonDate = Utility.mapToToken(delim, customDatesStart);
						endSeasonDate = Utility.mapToToken(delim, customDatesEnd);
						allDatesBW = new ArrayList<String>();
						for(int i = 0; i < customDatesStart.size(); i++) {
							allDatesBW.addAll(Utility.getAllDatesBetweenCheckInOutDates(Utility.parseDate(customDatesStart.get(i), "MM/dd/yyyy", "dd/MM/yyyy"),Utility.parseDate(customDatesEnd.get(i), "MM/dd/yyyy", "dd/MM/yyyy")));
						}
						
						app_logs.info(allDatesBW);
					}
					try{
						navigation.ratesGrid(driver);
					}catch(Exception f) {
						derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
						navigation.ratesGrid(driver);
					}
				} else {
					selectedSeasonType = dateRange;
				}

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


		
		try {
			testSteps.add(
					"=================== Getting the Availability of room classes in between check in and checkout dates ======================");
			app_logs.info(
					"=================== Getting the Availability of room classes in between check in and checkout dates ======================");
			ratesGrid.clickOnAvailability(driver);
			ArrayList MRBAvail = new ArrayList();
			ArrayList MRBBlockOut = new ArrayList();
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				ratesGrid.clickForRateGridCalender(driver, testSteps);
				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
				ratesGrid.expandRoomClass1(driver, testSteps, ReservationRoomClasses);
				app_logs.info("==========Getting the availability count of the room class " + ReservationRoomClasses
						+ " in Availability tab==========");
				testSteps.add("==========Getting the availability count of the room class " + ReservationRoomClasses
						+ " in Availability tab==========");
				ArrayList<String> avail = ratesGrid.getAvailability(driver, testSteps, ReservationRoomClasses, days,
						CheckInDate);

				app_logs.info("==========Getting the room class " + ReservationRoomClasses
						+ "os blocked out or not in Availability tab==========");
				testSteps.add("==========Getting the room class " + ReservationRoomClasses
						+ "os blocked out or not in Availability tab==========");
				ArrayList<Boolean> blockout = ratesGrid.getBlockOutRoomClass(driver, testSteps, ReservationRoomClasses,
						days, channelName, CheckInDate);

				for (int i = 0; i < avail.size(); i++) {
					int available = Integer.parseInt(avail.get(i));
					boolean falg = blockout.get(i);
					if (available <= 0 || !falg) {
						isRoomClassAvailable = false;
						break;
					}
				}
				// here change stay detail
		         if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")||
		        		 ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
							
							rateGrid.clickForRateGridCalender(driver, test_steps);
							Utility.selectDateFromDatePicker(driver, ChangeResStartDate, "dd/MM/yyyy");
							
							rateGrid.expandRoomClass1(driver, test_steps, ReservationChangeClass);
							app_logs.info("==========Getting the availability count of the room class " + ReservationChangeClass+ " in Availability tab==========");
							test_steps.add("==========Getting the availability count of the room class " + ReservationChangeClass+ " in Availability tab==========");
							ArrayList<String> availChnage = rateGrid.getAvailability(driver, test_steps, ReservationChangeClass, 
									daysChnage,ChangeResStartDate);

							app_logs.info("==========Getting the room class " + ReservationChangeClass+ " as blocked out or not in Availability tab==========");
							test_steps.add("==========Getting the room class " + ReservationChangeClass+ " as blocked out or not in Availability tab==========");
							ArrayList<Boolean> blockoutChange = rateGrid.getBlockOutRoomClass(driver, test_steps, ReservationChangeClass,
									daysChnage, channelName, ChangeResStartDate);

							for (int i = 0; i < availChnage.size(); i++) {
								int available = Integer.parseInt(availChnage.get(i));
								boolean falg = blockoutChange.get(i);
								if (available <= 0 || !falg) {
									isRoomClassAvailableChange = false;
									break;
								}
							}
						}
			} else {
				String[] rm = ReservationRoomClasses.split(Utility.DELIM);
				String[] chkin = CheckInDate.split(Utility.DELIM);
				String[] chkout = CheckOutDate.split(Utility.DELIM);
				int MRBdays = 0;
				for (int i = 0; i < rm.length; i++) {
					MRBdays = Utility.getNumberofDays(chkin[i], chkout[i]);
					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, chkin[i], "dd/MM/yyyy");
					rateGrid.clickExpendRooClass(driver, test_steps, rm[i]);

					app_logs.info("==========Getting the availability count of the room class " + rm[i]
							+ " in Availability tab==========");
					test_steps.add("==========Getting the availability count of the room class " + rm[i]
							+ " in Availability tab==========");
					MRBAvail.add(rateGrid.getAvailability(driver, test_steps, rm[i], MRBdays, chkin[i]));

					app_logs.info("==========Getting the  the room class " + rm[i]
							+ "  is blocked are not in Availability tab==========");
					test_steps.add("==========Getting the  the room class " + rm[i]
							+ "  is blocked are not in Availability tab==========");
					MRBBlockOut.add(
							rateGrid.getBlockOutRoomClass(driver, test_steps, rm[i], MRBdays, channelName, chkin[i]));
				}

				if (ActionOnReservation.equalsIgnoreCase("Recalculate")
						|| ActionOnReservation.equalsIgnoreCase("No Change")
						|| ActionOnReservation.equalsIgnoreCase("Changed Dates")) {

					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
					rateGrid.clickExpendRooClass(driver, test_steps, ReservationChangeRoomClass);
					app_logs.info("==========Getting the availability count of the room class "
							+ ReservationChangeRoomClass + " in Availability tab==========");
					test_steps.add("==========Getting the availability count of the room class "
							+ ReservationChangeRoomClass + " in Availability tab==========");
					ArrayList<String> availChnage = rateGrid.getAvailability(driver, test_steps,
							ReservationChangeRoomClass, daysChnage, ChangeResStartDate);

					app_logs.info("==========Getting the room class " + ReservationChangeRoomClass
							+ " as blocked out or not in Availability tab==========");
					test_steps.add("==========Getting the room class " + ReservationChangeRoomClass
							+ " as blocked out or not in Availability tab==========");
					ArrayList<Boolean> blockoutChange = rateGrid.getBlockOutRoomClass(driver, test_steps,
							ReservationChangeRoomClass, daysChnage, channelName, ChangeResStartDate);

					for (int i = 0; i < availChnage.size(); i++) {
						int available = Integer.parseInt(availChnage.get(i));
						boolean falg = blockoutChange.get(i);
						if (available <= 0 || !falg) {
							isRoomClassAvailableChange = false;
							break;
						}
					}
				}

				outer: for (int i = 0; i < MRBAvail.size(); i++) {
					ArrayList al = (ArrayList) MRBAvail.get(i);
					for (int k = 0; k < al.size(); k++) {
						int available = Integer.parseInt(al.get(k).toString());
						if (available <= 0) {
							isRoomClassAvailable = false;
							break outer;
						}
					}

					for (int k = 0; k < MRBBlockOut.size(); k++) {
						ArrayList al1 = (ArrayList) MRBBlockOut.get(i);
						for (int m = 0; m < al1.size(); m++) {
							boolean falg = (boolean) al1.get(m);
							if (!falg) {
								isRoomClassAvailable = false;
								break outer;
							}
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify availability", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify availability", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			ratesGrid.clickRatesTab(driver, testSteps);
			reservationRoomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on rate tab ", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on rate tab", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
// from here code needs to clear
		try {
			testSteps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			ratesGrid.selectRatePlan(driver, derivedRatePlanName, testSteps);
			app_logs.info("select rate plan: " + derivedRatePlanName);

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				ratesGrid.clickForRateGridCalender(driver, testSteps);
				app_logs.info("clickForRateGridCalender");

				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
				app_logs.info("selectDateFromDatePicker");

				ratesGrid.expandRoomClass1(driver, testSteps, reservationRoomClassesList.get(0));
				app_logs.info("expandRoomClass");

				ratesGrid.expandChannel(driver, testSteps, reservationRoomClassesList.get(0), channelName);
				app_logs.info("expandChannel");

			}
			if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectBulkUpdateOption(driver, "Rules");
				testSteps.addAll(getTestSteps);

				testSteps.add("==========SELECT START DATE==========");
				app_logs.info("==========SELECT START DATE==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectDate(driver, RulesUpdateStartDate, true);
				testSteps.addAll(getTestSteps);

				testSteps.add("==========SELECT END DATE==========");
				app_logs.info("==========SELECT END DATE==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectDate(driver, RulesUpdateEndDate, false);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========CHECKING/UNCHECKING DAYS==========");
				testSteps.add("==========CHECKING/UNCHECKING DAYS==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.checkDays(driver, "Sun", isSun_RulesUpdate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.checkDays(driver, "Mon", isMon_RulesUpdate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.checkDays(driver, "Tue", isTue_RulesUpdate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.checkDays(driver, "Wed", isWed_RulesUpdate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.checkDays(driver, "Thu", isThu_RulesUpdate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.checkDays(driver, "Fri", isFri_RulesUpdate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.checkDays(driver, "Sat", isSat_RulesUpdate);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========SELECTING RATE PLAN==========");
				testSteps.add("==========SELECTING RATE PLAN==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", derivedRatePlanName);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========SELECTING ROOM CLASS==========");
				testSteps.add("==========SELECTING ROOM CLASS==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", ReservationRoomClasses);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", channelName);
				testSteps.addAll(getTestSteps);

				ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);

				for (String type : type_rulesUpdateList) {

					if (type.equalsIgnoreCase("min stay")) {
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickMinimumStay(driver, "Yes");
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = ratesGrid.enterMinimumStayValue(driver, RuleMinStayValue_RulesUpdate);
						testSteps.addAll(getTestSteps);

					} else if (type.equalsIgnoreCase("No Check In")) {

						getTestSteps.clear();
						getTestSteps = ratesGrid.clickCheckin(driver, "Yes");
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = ratesGrid.clickNoCheckInCheckbox(driver, "Yes");
						testSteps.addAll(getTestSteps);

					} else if (type.equalsIgnoreCase("No Check out")) {
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickCheckOut(driver, "Yes");
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = ratesGrid.clickNoCheckOutCheckbox(driver, "Yes");
						testSteps.addAll(getTestSteps);

					}

				}
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickUpdateButton(driver);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
				testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

				int numberOfDays = ESTTimeZone.numberOfDaysBetweenDates(RulesUpdateStartDate, RulesUpdateEndDate) + 1;
				String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
				testSteps.add("Expected total days : " + expectedDays);
				app_logs.info("Expected total days : " + expectedDays);
				String totalDays = ratesGrid.getTotalDaysText(driver, "Rules");
				testSteps.add("Found : " + totalDays);
				app_logs.info("Found : " + totalDays);
				Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
				testSteps.add("Verified total number of days");
				app_logs.info("Verified total number of days");

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnYesUpdateButton(driver);
				testSteps.addAll(getTestSteps);
			} else if (RulesUpdateType.equalsIgnoreCase("Override")) {
				ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
				ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);
				ArrayList<String> getDates = Utility.checkDayAndReturnDates(RulesUpdateStartDate, RulesUpdateEndDate,
						"dd/MM/yyyy", isMon_RulesUpdate, isTue_RulesUpdate, isWed_RulesUpdate, isThu_RulesUpdate,
						isFri_RulesUpdate, isSat_RulesUpdate, isSun_RulesUpdate);
				int numberOfDays = Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate);
				String newDateDate = RulesUpdateStartDate;

				for (String roomClassName : roomClassesList) {
					for (int i = 0; i < numberOfDays; i++) {
						newDateDate = Utility.getCustomDate(RulesUpdateStartDate, "dd/MM/yyyy", "dd/MM/yyyy", i);
						ratesGrid.clickForRateGridCalender(driver, testSteps);
						Utility.selectDateFromDatePicker(driver, newDateDate, "dd/MM/yyyy");
						ratesGrid.overrideMinStayValue(driver, testSteps, roomClassName, channelName, "0");
						ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, channelName,
								ratesConfig.getProperty("checkinRule"), false);
						ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, channelName,
								ratesConfig.getProperty("checkoutRule"), false);

					}
				}
				for (String roomClassName : roomClassesList) {
					for (int i = 0; i < getDates.size(); i++) {
						newDateDate = Utility.getCustomDate(RulesUpdateStartDate, "dd/MM/yyyy", "dd/MM/yyyy", i);
						ratesGrid.clickForRateGridCalender(driver, testSteps);
						Utility.selectDateFromDatePicker(driver, getDates.get(i), "dd/MM/yyyy");

						for (String type : type_rulesUpdateList) {

							if (type.equalsIgnoreCase("min stay")) {
								ratesGrid.overrideMinStayValue(driver, testSteps, roomClassName, channelName,
										RuleMinStayValue_RulesUpdate);
							} else if (type.equalsIgnoreCase("No Check In")) {
								ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, channelName,
										ratesConfig.getProperty("checkinRule"), true);

							} else if (type.equalsIgnoreCase("No Check out")) {
								ratesGrid.overrideRuleForNoCheckInAndOut(driver, testSteps, roomClassName, channelName,
										ratesConfig.getProperty("checkoutRule"), true);
							}
						}
					}
				}

			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to over ride rule", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to over ride rule", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
/*
		int MRBDays = 0;
		try {
			testSteps.add("=================== Getting Rate Plan Restrictiona and Rules ======================");
			app_logs.info("=================== Getting Rate Plan Restrictiona and Rules ======================");

			if (ReservationType.equalsIgnoreCase("MRB")) {
				String[] MRBCheckIn = CheckInDate.split(Utility.DELIM);
				String[] MRBCheckOut = CheckOutDate.split(Utility.DELIM);
				String[] roomClass = ReservationRoomClasses.split(Utility.DELIM);

				for (int k = 0; k < roomClass.length; k++) {
					ratesGrid.clickForRateGridCalender(driver, testSteps);
					Utility.selectDateFromDatePicker(driver, MRBCheckIn[k], "dd/MM/yyyy");
					ratesGrid.expandRoomClass(driver, testSteps, roomClass[k]);
					ratesGrid.expandChannel(driver, testSteps, roomClass[k], channelName);

					MRBDays = Utility.getNumberofDays(MRBCheckIn[k], MRBCheckOut[k]);
					System.out.println("MRBCheckIn[k] : " + MRBCheckIn[k]);
					System.out.println("MRBCheckOut[k] : " + MRBCheckOut[k]);
					System.out.println("MRBDays : " + MRBDays);

					minStayRuleMRB = ratesGrid.getRuleDataValuesForMinStay(driver, testSteps, roomClass[k], channelName,
							MRBDays);

					minruleMRB = minStayRuleMRB;

					Collections.sort(minruleMRB);
					System.out.println("minrule : " + minruleMRB);
					int min = Integer.parseInt((String) minruleMRB.get(minruleMRB.size() - 1));
					System.out.println(min);
					minStayRuleValueMRB.add(min);
					

					if (minStayRuleValueMRB.get(k) > 0) {
						isMinStayRuleMRB.add(true);
						isMinStayRuleBrokenPopComeOrNotMRB.add(reservationPage.verifyMinStayPopupComeOrNot(driver,
								testSteps, minStayRuleValueMRB.get(k), MRBDays));
					} else {
						isMinStayRuleMRB.add(false);
						isMinStayRuleBrokenPopComeOrNotMRB.add(reservationPage.verifyMinStayPopupComeOrNot(driver,
								testSteps, minStayRuleValueMRB.get(k), MRBDays));
					}

					ArrayList<String> noCheckInRule1 = null;
					noCheckInRule1 = ratesGrid.getRuleDataValuesForNoCheckIn(driver, testSteps, roomClass[k],
							channelName, MRBDays);

					noCheckInRuleMRB.add(noCheckInRule1);

					System.out.println("noCheckInRule1 : " + noCheckInRule1);

					checkInColorMRB.add(reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule1,
							MRBCheckIn[k], MRBCheckOut[k]));

					System.out.println("checkInColor : " + checkInColorMRB.get(k));

					ArrayList<String> noCheckOutRule1 = null;

					noCheckOutRule1 = ratesGrid.getRuleDataValuesForNoCheckOut(driver, testSteps, roomClass[k],
							channelName, MRBDays);
					noCheckOutRuleMRB.add(noCheckOutRule1);

					System.out.println("noCheckOutRule : " + noCheckOutRule1);

					checkOutColorMRB.add(reservationPage.verifyNoCheckOutPopupComeOrNot(driver, testSteps,
							noCheckOutRule1, MRBCheckIn[k], MRBCheckOut[k]));
					System.out.println("checkOutColor : " + checkOutColorMRB.get(k));
					ratesGrid.collapseRoomClass(driver, testSteps, roomClass[k]);

					app_logs.info("Min Stay Rule for room classes : " + minStayRuleValueMRB);
				}


				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, testSteps);

				interval = ratesGrid.getInterval(driver);
				testSteps.add("Expected interval : " + interval);
				app_logs.info("Expected interval : " + interval);
				testSteps.add("Found : " + interval);
				app_logs.info("Found : " + interval);

				ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
				verifyLenthOfStayChecked = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Length of stay");
				app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayChecked);
				if (verifyLenthOfStayChecked) {

					verifyMinStayCondidtion = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Min");
					verifyMaxStayCondition = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Max");
					if (verifyMinStayCondidtion) {
						String getMin = ratesGrid.getMinAndMaxValue(driver, "Min");
						selectedLengthofStayRestrictions.put("Min", getMin);
					}
					if (verifyMaxStayCondition) {
						String getMax = ratesGrid.getMinAndMaxValue(driver, "Max");
						selectedLengthofStayRestrictions.put("Max", getMax);
					}

				}

				isBookinWindow = ratesGrid.isBookingWindowChecked(driver, testSteps);

				HashMap<String, String> bookingWindowRestrictions = new HashMap<>();

				testSteps.add("========== Getting booking window default restrictions for rate plan " + "<b>"
						+ derivedRatePlanName + "</b> ==========");
				bookingWindowRestrictions = ratesGrid.getBookingWindowRestrictions(driver, testSteps, derivedRatePlanName);

				System.out.println(bookingWindowRestrictions);

				// this wrong one

				for (int i = 1; i < roomClass.length; i++) {
					MRBDays = Utility.getNumberofDays(MRBCheckIn[i], MRBCheckOut[i]);
					restricrionsLengthOfStayMRB.add(
							reservationPage.verifylenthOfStayRestrictions(driver, testSteps, verifyLenthOfStayChecked,
									verifyMinStayCondidtion, verifyMaxStayCondition, selectedLengthofStayRestrictions, MRBDays));

					System.out.println(selectedLengthofStayRestrictions);
					System.out.println(restricrionsLengthOfStay);

					restricrionsBookingWindowMRB.add(reservationPage.verifyBookingWindowRestrictions(driver, testSteps,
							isBookinWindow, MRBCheckIn[i], MRBCheckOut[i], bookingWindowRestrictions));

					System.out.println("isBookinWindow : " + isBookinWindow);
					System.out.println("restricrionsBookingWindow : " + restricrionsBookingWindow);
				}

				isPromocode = ratesGrid.isPromoCodeChecked(driver, testSteps);

				if (isPromocode) {
					PromoCode = ratesGrid.getPromoCode(driver, testSteps);
				} else {
					PromoCode = "";
				}
				System.out.println("promoCode : " + PromoCode);

				System.out.println("isMinStayRuleBrokenPopComeOrNotMRB : " + isMinStayRuleBrokenPopComeOrNotMRB);
				app_logs.info("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				app_logs.info("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				app_logs.info("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);
				testSteps.add("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				testSteps.add("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				testSteps.add("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);

				System.out.println("minStayRuleValueMRB : " + minStayRuleValueMRB);
				System.out.println("checkInColorMRB : " + checkInColorMRB);
				System.out.println("checkOutColorMRB : " + checkOutColorMRB);

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, testSteps);
				}

				String[] chkIn = CheckInDate.split(Utility.DELIM);
				String[] splitRoomClass = ReservationRoomClasses.split( Utility.DELIM);

				for (int i = 0; i < chkIn.length; i++) {
					nightlyRate.switchCalendarTab(driver, testSteps);
					nightlyRate.selectSeasonDates(driver, testSteps, chkIn[i]);
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);

					getRoomClassWithRates.put(splitRoomClass[i], ratesGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, splitRoomClass[i], testSteps));

					nightlyRate.closeSeason(driver, testSteps);

				}

				app_logs.info(getRoomClassWithRates.get(splitRoomClass[0]));
				app_logs.info(getRoomClassWithRates.get(splitRoomClass[1]));
				ArrayList<String> gettest = new ArrayList<>();
				for (int i = 0; i < getRoomClassWithRates.size(); i++) {
					app_logs.info(getRoomClassWithRates.get(splitRoomClass[i]));

					gettest = getRoomClassWithRates.get(splitRoomClass[i]);
					app_logs.info("arraylistSize" + gettest.size());
					for (int j = 0; j < gettest.size(); j++) {
						app_logs.info(gettest.get(j));
					}

				}
				app_logs.info("after get room class details");
				ratesGrid.clickOnSaveratePlan(driver);
				app_logs.info("after click on save rate plan");

				Wait.wait5Second();
				ratesGrid.closeOpendTabInMainMenu(driver);
				app_logs.info("after click on close tab");


				if (isVerifyPolicies) {

					driver.navigate().refresh();

					ratesGrid.selectRatePlan(driver, derivedRatePlanName, testSteps);
					ratesGrid.clickOnEditRatePlan(driver);
					nightlyRate.switchCalendarTab(driver, testSteps);
					
					// need to update
					
					nightlyRate.selectSeasonDates(driver, testSteps, MRBCheckIn[0]);
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);

					nightlyRate.clickSeasonPolicies(driver, testSteps);

					
					getSessionLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, testSteps);
					app_logs.info("getSessionLevelPolicy: "+getSessionLevelPolicy.toString());
				
					testSteps.add("==================Get Policy from  Season==================");
					ArrayList<String> deposit = new ArrayList<String>();
					ArrayList<String> checkin = new ArrayList<String>();
					
					if (CheckInDate.split(Utility.DELIM).length > 1) {
						for (int i = 0; i < RoomClass.length; i++) {
							deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("depositPolicyText"), RoomClass[i], testSteps));
							checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("checkInPolicyText"), RoomClass[i], testSteps));
						}
					} else {
						deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
								ratesConfig.getProperty("depositPolicyText"), RoomClass[0], testSteps));
						checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
								ratesConfig.getProperty("checkInPolicyText"), RoomClass[0], testSteps));
					}

					for (String str : deposit) {
						if (str != null) {
							seasonDepositPolicy.add(str);
						}
					}
					for (String str : checkin) {
						if (str != null) {
							seasonCheckInPolicy.add(str);
						}
					}
					app_logs.info(seasonDepositPolicy);
					app_logs.info(seasonCheckInPolicy);

					nightlyRate.closeSeason(driver, testSteps);

					ratesGrid.clickOnSaveratePlan(driver);
					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);

				}
				
			}
			
			// here start get rates for single room reservation
			else {
							}//end here single reservation
	
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify season in single room reservation", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify season in single room reservation", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		*/
		

		
		


		int MRBDays = 0;
		try {	
			ArrayList<String> allSeasonsDates = null;
			test_name = "Verify Derived Rate Plan In " + ReservationType + " Reservations";
			test_description = "Verify Derived Rate Plan In " + ReservationType + " Reservations";
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			testSteps.add("=================== GETTING RATE PLAN RESTRICTION AND RULES ======================");
			app_logs.info("=================== GETTING RATE PLAN RESTRICTION AND RULES ======================");
			if (ReservationType.equalsIgnoreCase("MRB")) {
				String[] MRBCheckIn = CheckInDate.split(Utility.DELIM);
				String[] MRBCheckOut = CheckOutDate.split(Utility.DELIM);
				String[] roomClass = ReservationRoomClasses.split(Utility.DELIM);

				for (int k = 0; k < roomClass.length; k++) {

					rateGrid.clickForRateGridCalender(driver, test_steps);
					rateGrid.selectDateFromDatePicker(driver, MRBCheckIn[k],
							ratesConfig.getProperty("defaultDateFormat"), test_steps);
					Wait.waitUntilPageLoadNotCompleted(driver, 50);
					rateGrid.clickRatePlanArrow(driver, test_steps);
					rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
					Wait.waitUntilPageLoadNotCompleted(driver, 40);
					derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
					rateGrid.expandParentRateGrid(driver, "minus");
					getTest_Steps.clear();
					getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
							getTest_Steps);
					test_steps.addAll(getTest_Steps);

					rateGrid.expandRoomClass(driver, test_steps, roomClass[k]);
					rateGrid.expandChannel(driver, test_steps, roomClass[k], channelName);

					MRBDays = Utility.getNumberofDays(MRBCheckIn[k], MRBCheckOut[k]);
					app_logs.info("MRBCheckIn[k] : " + MRBCheckIn[k]);
					app_logs.info("MRBCheckOut[k] : " + MRBCheckOut[k]);
					app_logs.info("MRBDays : " + MRBDays);

					minStayRuleMRB = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, roomClass[k], channelName,
							MRBDays);

					minruleMRB = minStayRuleMRB;

					Collections.sort(minruleMRB);
					app_logs.info("minrule : " + minruleMRB);
					int min = Integer.parseInt((String) minruleMRB.get(minruleMRB.size() - 1));
					app_logs.info(min);
					minStayRuleValueMRB.add(min);

					if (minStayRuleValueMRB.get(k) > 0) {
						isMinStayRuleMRB.add(true);
						isMinStayRuleBrokenPopComeOrNotMRB.add(reservationPage.verifyMinStayPopupComeOrNot(driver,
								test_steps, minStayRuleValueMRB.get(k), MRBDays));
					} else {
						isMinStayRuleMRB.add(false);
						isMinStayRuleBrokenPopComeOrNotMRB.add(reservationPage.verifyMinStayPopupComeOrNot(driver,
								test_steps, minStayRuleValueMRB.get(k), MRBDays));
					}

					ArrayList<String> noCheckInRule1 = null;
					noCheckInRule1 = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, roomClass[k],
							channelName, MRBDays);

					noCheckInRuleMRB.addAll(noCheckInRule1);

					app_logs.info("noCheckInRule1 : " + noCheckInRule1);

					checkInColorMRB.add(reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule1,
							MRBCheckIn[k], MRBCheckOut[k]));

					app_logs.info("checkInColor : " + checkInColorMRB.get(k));

					ArrayList<String> noCheckOutRule1 = null;

					noCheckOutRule1 = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, roomClass[k],
							channelName, MRBDays);
					noCheckOutRuleMRB.addAll(noCheckOutRule1);

					app_logs.info("noCheckOutRule : " + noCheckOutRule1);

					checkOutColorMRB.add(reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
							noCheckOutRule1, MRBCheckIn[k], MRBCheckOut[k]));
					app_logs.info("checkOutColor : " + checkOutColorMRB.get(k));
					rateGrid.collapseRoomClass(driver, test_steps, roomClass[k]);

					app_logs.info("Min Stay Rule for room classes : " + minStayRuleValueMRB);
				}

				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, test_steps);

				folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
				app_logs.info("folioName : " + folioName);

				parentRatePlanOffSetList = derivedRate.getParentPlanOffset(driver);

				rateGrid.clickOnRestrcitionSAndPoliciesTab(driver);

				verifyLenthOfStayChecked = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Length of stay");
				app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayChecked);
				if (verifyLenthOfStayChecked) {

					verifyMinStayCondidtion = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Min");
					verifyMaxStayCondition = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Max");
					if (verifyMinStayCondidtion) {
						String getMin = rateGrid.getMinAndMaxValue(driver, "Min");
						addStayofLength.put("Min", getMin);
					}
					if (verifyMaxStayCondition) {
						String getMax = rateGrid.getMinAndMaxValue(driver, "Max");
						addStayofLength.put("Max", getMax);
					}

				}

				isBookinWindow = rateGrid.isBookingWindowChecked(driver, test_steps);

				HashMap<String, String> bookingWindowRestrictions = new HashMap<>();

				test_steps.add("========== Getting booking window default restrictions for rate plan " + "<b>"
						+ derivedRatePlanName + "</b> ==========");
				bookingWindowRestrictions = rateGrid.getBookingWindowRestrictions(driver, test_steps,
						derivedRatePlanName);

				app_logs.info(bookingWindowRestrictions);

				for (int i = 1; i < roomClass.length; i++) {
					MRBDays = Utility.getNumberofDays(MRBCheckIn[i], MRBCheckOut[i]);
					restricrionsLengthOfStayMRB.add(
							reservationPage.verifylenthOfStayRestrictions(driver, test_steps, verifyLenthOfStayChecked,
									verifyMinStayCondidtion, verifyMaxStayCondition, addStayofLength, MRBDays));

					app_logs.info(addStayofLength);
					app_logs.info(restricrionsLengthOfStay);

					restricrionsBookingWindowMRB.add(reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
							isBookinWindow, MRBCheckIn[i], MRBCheckOut[i], bookingWindowRestrictions));

					app_logs.info("isBookinWindow : " + isBookinWindow);
					app_logs.info("restricrionsBookingWindow : " + restricrionsBookingWindow);
				}

				isPromocode = rateGrid.isPromoCodeChecked(driver, test_steps);

				if (isPromocode) {
					derivedPromoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					derivedPromoCode = "";
				}

				app_logs.info("promoCode : " + parentPromoCode);

				app_logs.info("isMinStayRuleBrokenPopComeOrNotMRB : " + isMinStayRuleBrokenPopComeOrNotMRB);
				app_logs.info("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				app_logs.info("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				app_logs.info("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);
				test_steps.add("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				test_steps.add("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				test_steps.add("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);

				app_logs.info("minStayRuleValueMRB : " + minStayRuleValueMRB);
				app_logs.info("checkInColorMRB : " + checkInColorMRB);
				app_logs.info("checkOutColorMRB : " + checkOutColorMRB);

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				}

				String[] chkIn = CheckInDate.split(Utility.DELIM);
				nightlyRate.switchCalendarTab(driver, test_steps);

				HashMap<Integer, String> map = new HashMap<>();
				map = derivedRate.getCustomDates(driver, "Custom date range", "Start Date");
				test_steps.add("Custom start date ranges :  " + map);
				app_logs.info("Custom start date ranges :  " + map);
				map = derivedRate.getCustomDates(driver, "Custom date range", "End Date");
				test_steps.add("Custom end date ranges :  " + map);
				app_logs.info("Custom end date ranges :  " + map);

				navigation.ratesGrid(driver);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

				rateGrid.clickOnEditRatePlan(driver);
				nightlyRate.switchCalendarTab(driver, test_steps);
				nightlyRate.selectSeasonDates(driver, test_steps, chkIn[0]);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				for (String str : roomClass) {
					capacityAdult1.add(nightlyRate.getAdultCapacity(driver, str));
					capacityChild1.add(nightlyRate.getChildCapacity(driver, str));
					ratePlanAdults.add(nightlyRate.getMaxAdult(driver, str));
					ratePlanChilds.add(nightlyRate.getMaxPersons(driver, str));
				}

				nightlyRate.closeSeason(driver, test_steps);

				rateGrid.closeOpendTabInMainMenu(driver);

				String[] rm = ReservationRoomClasses.split(Utility.DELIM);
				driver.navigate().refresh();

				
				for (int k = 0; k < rm.length; k++) {
					
//					rateGrid.clickForRateGridCalender(driver, test_steps);
//					Utility.selectDateFromDatePicker(driver, MRBCheckIn[k], "dd/MM/yyyy");
//					rateGrid.clickRatePlanArrow(driver, test_steps);
//					rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
//					Wait.waitUntilPageLoadNotCompleted(driver, 40);
//					rateGrid.expandParentRateGrid(driver, "minus");
//					derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
//					getTest_Steps.clear();
//					getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
//							getTest_Steps);

					HashMap<String, String> getRates1 = new HashMap<String, String>();
					HashMap<String, String> getExAdult1 = new HashMap<String, String>();
					HashMap<String, String> getExChild1 = new HashMap<String, String>();
//
//					List<Date> datesD = new ArrayList<Date>();
//
//					rateGrid.expandRoomClass(driver, test_steps, rm[k]);
//					test_steps.add("===Get Data From Rate Plan===");
//					app_logs.info("===Get Data From Rate Plan===");
//					getRatesPerNightChannels = rateGrid.getRoomRatesExAdExChOfChannel(driver, MRBCheckIn[k],
//							MRBCheckOut[k], rm[k], channelName);
//					app_logs.info(getRatesPerNightChannels);
//					// rateGrid.collapseRoomClass(driver, test_steps, rm[k]);
//					getRates1.clear();
//					getExAdult1.clear();
//					getExChild1.clear();
//					getData(getRatesPerNightChannels);
//
//					Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
//							MRBCheckIn[k]);
//					app_logs.info("Start Date: " + fromDate);
//					Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
//							MRBCheckOut[k]);
//					app_logs.info("End Date: " + toDate);
//					datesD = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
//					app_logs.info("Dates Are: " + dates);
//					dates1.add(datesD);
//					// dates1.add(dates);
//					Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
//
//					app_logs.info("dates : " + datesD.size());
					
					allDatesBW = Utility.getAllDatesBetweenTwoDates(CheckInDate, CheckOutDate);
					
					for (String date : allDatesBW) {
						app_logs.info(date);
						app_logs.info("Found Room Rates : " + roomClassWiseSourceDerivedRatesRule.get(rm[k]).get(channelName).get(date));
						test_steps.add("Found Room Rates : " + roomClassWiseSourceDerivedRatesRule.get(rm[k]).get(channelName).get(date));
						app_logs.info(roomClassWiseSourceDerivedRatesRule.get(selectedRoomClasses.get(0))
								.get(selectedChannels.get(0)).get(date));
						RatesGridChannelRatesRules obj = roomClassWiseSourceDerivedRatesRule.get(rm[k]).get(channelName).get(date);
					
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
				
				

			} else {
				
			ratesGrid.clickForRateGridCalender(driver, testSteps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			ratesGrid.expandParentRateGrid(driver, "minus");
			getTestSteps.clear();
			getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
					getTestSteps);
			testSteps.addAll(getTestSteps);
			ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassesList.get(0));
			ratesGrid.expandChannel(driver, testSteps, reservationRoomClassesList.get(0), channelName);

			minStayRule = ratesGrid.getRuleDataValuesForMinStay(driver, testSteps, ReservationRoomClasses,
					channelName, days);
			minrule = minStayRule;

			Collections.sort(minrule);
			app_logs.info("minrule : " + minrule);

			minStayRuleValue = Integer.parseInt((String) minrule.get(minrule.size() - 1));

			if (minStayRuleValue > 0) {
				isMinStayRule = true;
				isMinStayRuleBrokenPopComeOrNot = reservationPage.verifyMinStayPopupComeOrNot(driver, minrule,
						minStayRuleValue, days);
			}

			noCheckInRule = ratesGrid.getRuleDataValuesForNoCheckIn(driver, testSteps, ReservationRoomClasses,
					channelName, days);

			app_logs.info("noCheckInRule : " + noCheckInRule);

			checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule,
					CheckInDate, CheckOutDate);

			app_logs.info("checkInColor : " + checkInColor);

			noCheckOutRule = ratesGrid.getRuleDataValuesForNoCheckOut(driver, testSteps, ReservationRoomClasses,
					channelName, days);

			app_logs.info("noCheckOutRule : " + noCheckOutRule);

			checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, noCheckInRule, noCheckOutRule,
					CheckInDate, CheckOutDate);
			app_logs.info("checkOutColor : " + checkOutColor);
			}

			// here get stay detail change info
			if (ActionOnReservation.equalsIgnoreCase("Recalculate") || ActionOnReservation.equalsIgnoreCase("No Change")
					|| ActionOnReservation.equalsIgnoreCase("Changed Dates")) {

				app_logs.info("Single: stay details change in rate grid");
				ratesGrid.clickForRateGridCalender(driver, testSteps);
				Utility.selectDateFromDatePicker(driver, ChangeResStartDate, "dd/MM/yyyy");

				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				ratesGrid.expandParentRateGrid(driver, "minus");
				getTestSteps.clear();
				getTestSteps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTestSteps);
				testSteps.addAll(getTestSteps);
				ratesGrid.expandRoomClass(driver, testSteps, ReservationChangeClass);
				ratesGrid.expandChannel(driver, testSteps, ReservationChangeClass, channelName);
				minStayRuleChnage = ratesGrid.getRuleDataValuesForMinStay(driver, testSteps, ReservationRoomClasses,
						channelName, daysChnage);
				minruleChange = minStayRuleChnage;

				Collections.sort(minruleChange);
				app_logs.info("minruleChange : " + minruleChange);
				minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));
				if (minStayRuleValueChange > 0) {
					isMinStayRuleChnage = true;
					isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
							minruleChange, minStayRuleValueChange, daysChnage);
				}

				noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
						channelName, daysChnage);

				Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

				checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps,
						noCheckInRuleChnage, ChangeResStartDate, ChangeResEndDate);

				Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

				noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
						ReservationChangeClass, channelName, daysChnage);

				Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

				checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
						noCheckOutRuleChnage, ChangeResStartDate, ChangeResEndDate);
				Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);
			}
			
			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, testSteps);
			ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
				
				
				testSteps.add("=================== Getting Rate Plan Restrictiona and Rules ======================");
				app_logs.info("=================== Getting Rate Plan Restrictiona and Rules ======================");

				ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
				verifyLenthOfStayChecked = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Length of stay");
				app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayChecked);
				if (verifyLenthOfStayChecked) {

					verifyMinStayCondidtion = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Min");
					verifyMaxStayCondition = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Max");
					if (verifyMinStayCondidtion) {
						String getMin = ratesGrid.getMinAndMaxValue(driver, "Min");
						selectedLengthofStayRestrictions.put("Min", getMin);
					}
					if (verifyMaxStayCondition) {
						String getMax = ratesGrid.getMinAndMaxValue(driver, "Max");
						selectedLengthofStayRestrictions.put("Max", getMax);
					}

				}
				
				Utility.app_logs.info("Selected Length of Stay : " + selectedLengthofStayRestrictions);
				testSteps.add("Selected Length of Stay : " + selectedLengthofStayRestrictions);
				
				testSteps.add("========== Getting booking window default restrictions for rate plan " + "<b>"
						+ derivedRatePlanName + "</b> ==========");
				selectedBookingWindowRestrictions = ratesGrid.getBookingWindowRestrictions(driver, testSteps, derivedRatePlanName);

				Utility.app_logs.info("Selected Booking Window : " + selectedBookingWindowRestrictions);
				testSteps.add("Selected Booking Window : " + selectedBookingWindowRestrictions);
				Utility.app_logs.info(selectedBookingWindowRestrictions);

				isPromocode = ratesGrid.isPromoCodeChecked(driver, testSteps);

				if (isPromocode) {
					derivedPromoCode = ratesGrid.getPromoCode(driver, testSteps);
				} else {
					derivedPromoCode = "";
				}
				

				Utility.app_logs.info("Selected Promo Code : " + derivedPromoCode);
				testSteps.add("Selected Promo Code : " + derivedPromoCode);
				getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, testSteps);

				Utility.app_logs.info("Selected Policies: " + getRateLevelPolicy);
				testSteps.add("Selected Policies : " + getRateLevelPolicy);
				
				try{
					navigation.ratesGrid(driver);
				}catch(Exception f) {
					derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
					navigation.ratesGrid(driver);
				}				
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


		// Code to get data from ParentRate Plan Like interval and base Rate Value

			try {
				testSteps.add("=================== GET INTERVAL RATE PLAN DATA ======================");
				app_logs.info("=================== GET INTERVAL RATE PLAN DATA ======================");
				ratesGrid.clickRatePlanArrow(driver, getTestSteps);
				ratesGrid.searchAndVerifyRatePlanExist(driver, parentRatePlanName, true, testSteps);
				ratesGrid.clickOnEditRatePlan(driver);

				interval = ratesGrid.getInterval(driver);
				testSteps.add("Expected interval : " + interval);
				app_logs.info("Expected interval : " + interval);

				nightlyRate.switchCalendarTab(driver, testSteps);
				if (!ReservationType.equalsIgnoreCase("MRB")) {
					isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, testSteps,
							CheckInDate, CheckOutDate);
				} else {
					ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);

					ArrayList<String> checkOutList = Utility.convertTokenToArrayList(CheckOutDate, delim);

					ArrayList<Boolean> isSeason = new ArrayList<Boolean>();
					int index = 0;
					for (String checkIn : checkInList) {
						isSeason.add(nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, testSteps,
								checkIn, checkOutList.get(index++)));
					}

					for (Boolean season : isSeason) {
						if (!season) {
							isSeasonExist = false;
							break;
						}
					}
				}
				if (isSeasonExist) {
					if (!ReservationType.equalsIgnoreCase("MRB")) {
						nightlyRate.selectSeasonDates(driver, testSteps, CheckInDate);
						nightlyRate.clickEditThisSeasonButton(driver, testSteps);
						
						String[] roomClass = ReservationRoomClasses.split(Utility.DELIM);
						for (int i = 0; i < roomClass.length; i++) {
						getRoomClassWithRates.put(roomClass[i],
								ratesGrid.getRoomClassRateWithAdditionalAdultAndChild(driver, roomClass[i],
										testSteps));

						app_logs.info(getRoomClassWithRates.get(roomClass[i]));
						}

						nightlyRate.closeSeason(driver, testSteps);
						ArrayList<String> gettest = new ArrayList<>();
						for (int i = 0; i < getRoomClassWithRates.size(); i++) {
							app_logs.info("Room Class : " + roomClass[i]);
							testSteps.add("Room Class : " + roomClass[i]);
							app_logs.info(getRoomClassWithRates.get(roomClass[i]));
							gettest = getRoomClassWithRates.get(roomClass[i]);
							reservationRoomClassBaseRate = gettest.get(0);
							testSteps.add("Adults capacity: " + gettest.get(1));
							testSteps.add("Persons capacity: " + gettest.get(2));
							testSteps.add("Is additional adults/child on? " + gettest.get(3));
							testSteps.add("Max adults: " + gettest.get(4));
							testSteps.add("Max persons: " + gettest.get(5));
							testSteps.add("Adults rate: " + gettest.get(6));
							testSteps.add("Childs rate: " + gettest.get(7));
							testSteps.add("Is pro stay rate on? " + gettest.get(8));
							testSteps.add("Rate per night? " + gettest.get(9));
							testSteps.add("Per night adults rate " + gettest.get(10));
							testSteps.add("Per night childs rate " + gettest.get(11));
							String adultCapicity = gettest.get(1);
							String  personCapicity = gettest.get(2);
							boolean isAdditionalAdultChild= false;
							if(gettest.get(3).equalsIgnoreCase("yes")) {
								isAdditionalAdultChild= true;
							}
							String  maxAdults =gettest.get(4);		
							String maxperson  = gettest.get(5);	
							String  adultsRate = gettest.get(6);
							String childRate = gettest.get(7);
							boolean isProRate = false;
							if(gettest.get(8).equalsIgnoreCase("yes")) {
								isProRate= true;
							}
							String ratePerNight = gettest.get(9);
							String adultPerNight =  gettest.get(10);
							String childPerNight =  gettest.get(11) ;
							String adultRateWithOffset = null;
							String childRateWithOffSet =null;
							// Calculating Derived Rate Value
							if(parentRatePlanOffSetList.get(1).equalsIgnoreCase("percent")){

								app_logs.info("Off Set Value " + parentRatePlanOffSetList.get(0));
								testSteps.add("Off Set Value " + parentRatePlanOffSetList.get(0));
								String offSetValue  = parentRatePlanOffSetList.get(0).replaceAll("%", "");

								app_logs.info("Off Set Value " + offSetValue);
								testSteps.add("Off Set Value " + offSetValue);
								if(parentRatePlanOffSetList.get(2).equalsIgnoreCase("greater than")){
									derivedRateValue = String.format("%.2f", Float.parseFloat(reservationRoomClassBaseRate)
											+ ((Float.parseFloat(reservationRoomClassBaseRate) * Float.parseFloat(offSetValue)) / 100));
									adultRateWithOffset = String.format("%.2f", Float.parseFloat(adultsRate)
											+ ((Float.parseFloat(adultsRate) * Float.parseFloat(offSetValue)) / 100));
									childRateWithOffSet = String.format("%.2f", Float.parseFloat(childRate)
											+ ((Float.parseFloat(childRate) * Float.parseFloat(offSetValue)) / 100));
								}else if(parentRatePlanOffSetList.get(2).equalsIgnoreCase("Lesser than")){
									derivedRateValue = String.format("%.2f", Float.parseFloat(reservationRoomClassBaseRate)
											- ((Float.parseFloat(reservationRoomClassBaseRate) * Float.parseFloat(offSetValue)) / 100));
									adultRateWithOffset = String.format("%.2f", Float.parseFloat(adultsRate)
											- ((Float.parseFloat(adultsRate) * Float.parseFloat(offSetValue)) / 100));
									childRateWithOffSet = String.format("%.2f", Float.parseFloat(childRate)
											- ((Float.parseFloat(childRate) * Float.parseFloat(offSetValue)) / 100));
											
								}
								adultsRate=  adultRateWithOffset;
								childRate = childRateWithOffSet ;
							}else if(parentRatePlanOffSetList.get(2).equalsIgnoreCase("greater than")){
								 derivedRateValue = String.format("%.2f",
										Float.parseFloat(reservationRoomClassBaseRate) + Float.parseFloat(parentRatePlanOffSetList.get(0)));
								}else if(parentRatePlanOffSetList.get(2).equalsIgnoreCase("lesser than")){
									 derivedRateValue = String.format("%.2f",
											Float.parseFloat(reservationRoomClassBaseRate) - Float.parseFloat(parentRatePlanOffSetList.get(0)));
								}
						app_logs.info("Derived Rate Value : " + derivedRateValue);
						testSteps.add("Derived Rate Value : " + derivedRateValue);
						baseRate.add(derivedRateValue);
						adultCapacity.add(adultCapicity);
						personCapacity.add(personCapicity);
						isAdditonalAdultChild.add(isAdditionalAdultChild);
						maxAdult1.add(maxAdults);
						maxPerson.add(maxperson);
						adultRate.add(adultsRate); 
						childRate1.add(childRate);  
						isProStayRate.add(isProRate);
						customRatePerNight.add(ratePerNight);
						adultRatePerNight.add(adultPerNight);
						childRatePerNight.add(childPerNight);
					}
				}
					

					if (isVerifyPolicies) {
						
						nightlyRate.selectSeasonDates(driver, testSteps, CheckInDate);
						nightlyRate.clickEditThisSeasonButton(driver, testSteps);

						nightlyRate.clickSeasonPolicies(driver, testSteps);
						getSessionLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, testSteps);
						
						app_logs.info("getSessionLevelPolicy: "+getSessionLevelPolicy);
						testSteps.add("==================Get Policy from  Season==================");
						app_logs.info("==================Get Policy from  Season==================");
						
						ArrayList<String> deposit = new ArrayList<String>();
						ArrayList<String> checkin = new ArrayList<String>();
						
						if (CheckInDate.split(Utility.DELIM).length > 1) {
							for (int i = 0; i < RoomClass.length; i++) {
								deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
										ratesConfig.getProperty("depositPolicyText"), RoomClass[i], testSteps));
								checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
										ratesConfig.getProperty("checkInPolicyText"), RoomClass[i], testSteps));
							}
						} else {
							deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("depositPolicyText"), RoomClass[0], testSteps));
							checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("checkInPolicyText"), RoomClass[0], testSteps));
						}

						for (String str : deposit) {
							if (str != null) {
								seasonDepositPolicy.add(str);
							}
						}
						for (String str : checkin) {
							if (str != null) {
								seasonCheckInPolicy.add(str);
							}
						}
						app_logs.info("123seasonDepositPolicy: "+seasonDepositPolicy);
						app_logs.info("1234seasonCheckInPolicy"+seasonCheckInPolicy);

						nightlyRate.closeSeason(driver, testSteps);
						app_logs.info("Close Season");
						
					}
					
			
				} else {
					app_logs.info("No Season For Desired Date");
//					nightlyRate.clickSaveRatePlan(driver, testSteps);
//					Wait.wait5Second();
//					ratesGrid.closeOpendTabInMainMenu(driver);
				}
				
			mk	

			// here get stay detail change info
			if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")
					||ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
						nightlyRate.switchCalendarTab(driver, test_steps);	
		
		if (isSeasonExistChange) {
				// here get interval info
				nightlyRate.selectSeasonDates(driver, test_steps, ChangeResStartDate);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);
				
				getRoomClassWithRates.put(ReservationRoomClasses,
						rateGrid.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass,
								test_steps));
				nightlyRate.closeSeason(driver, test_steps);

				app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
				ArrayList<String> gettest1 = new ArrayList<>();
				for (int i = 0; i < getRoomClassWithRates.size(); i++) {
					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

					gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
					app_logs.info("arraylistSize" + gettest.size());
					for (int j = 0; j < gettest1.size(); j++) {
						app_logs.info(gettest1.get(j));
					}

				}
			
			
			

			if (isVerifyPolicies) {
				
				nightlyRate.selectSeasonDates(driver, test_steps, ChangeResStartDate);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				nightlyRate.clickSeasonPolicies(driver, test_steps);

				getSessionLevelPolicyChange = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				
				test_steps.add("==================Get Policy from  Season  for change stay details==================");
				app_logs.info("==================Get Policy from  Season  for change stay details==================");
				ArrayList<String> deposit= new ArrayList<String>();
				ArrayList<String> checkin= new ArrayList<String>();
				if (ChangeResStartDate.split("\\|").length>1) {
						for(int i=0;i<RoomClass.length;i++) {
						deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, 
								ratesConfig.getProperty("depositPolicyText"),RoomClass[i],test_steps));
						checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),RoomClass[i],test_steps));											
					}
				}else {
					deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, 
							ratesConfig.getProperty("depositPolicyText"),ReservationChangeClass,test_steps));
					checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, 
							ratesConfig.getProperty("checkInPolicyText"),ReservationChangeClass,test_steps));					
				}
				
				for (String str : deposit) {
					if (str != null) {
						seasonDepositPolicyChange.add(str);
					}
				}
				for (String str : checkin) {
					if (str != null) {
						seasonCheckInPolicyChange.add(str);
					}
				}
				app_logs.info(seasonDepositPolicyChange);
				app_logs.info(seasonCheckInPolicyChange);		

				nightlyRate.closeSeason(driver, test_steps);
				
			}
			
		} else {
			app_logs.info("No Season For no stay details change Date");
		}	
	
		}
			ratesGrid.clickOnSaveratePlan(driver);
			Wait.wait5Second();
			ratesGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Get Base rate Values", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Get Base rate Values", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		

		
		
		
		
		try {
			navigation.cpReservation_Backward(driver);
		} catch (Exception e) {
			Actions actions = new Actions(driver);

			actions.sendKeys(Keys.ENTER);
		}

		String Salutation = "Mr.";
		String GuestFirstName = "Test Res";
		String GuestLastName = Utility.GenerateRandomString();
		String PhoneNumber = "8790321567";
		String AltenativePhone = "8790321577";
		String Email = "innroadautomation@innroad.com";
		String Account = "";
		String Address1 = "test1";
		String Address2 = "test2";
		String Address3 = "test3";
		String City = "test";
		String Country = "United States";
		String PostalCode = "12345";
		String IsGuesProfile = "No";
		String PaymentType = "Cash";
		String CardNumber = "5454545454545454";
		String NameOnCard = "Test card";
		String CardExpDate = Utility.getFutureMonthAndYearForMasterCard();
		String IsChangeInPayAmount = "No";
		String ChangedAmountValue = "";
		String TravelAgent = "";
		String MarketSegment = "GDS";
		String State = "New York";
		String IsSplitRes = "No";
		String Referral = "Other";
		String AccountType = "Corporate/Member Accounts";
		String AccountName = "AccountName_";
		String MargetSegment = "GDS";
		String BlockName = "Test Block";
		String RoomPerNight = "1";
		String noOfNightsGroupBlock = "2";
		String ExpectedAbgPerNight = "";
		 
		if (ReservationType.equalsIgnoreCase("MRB")) {
			ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);
			Salutation = "";
			PhoneNumber = "";
			GuestFirstName = "";
			GuestLastName = "";
			for (int i = 0; i < checkInList.size(); i++) {

				if (i == 0) {
					Salutation = "Mr.";
					PhoneNumber = "2314567890";
					GuestFirstName = "MRB Res";
					GuestLastName = "last Name";
				} else {
					Salutation = "Mr." + "|" + Salutation;
					PhoneNumber = PhoneNumber + "|" + "2314567890";
					GuestFirstName = GuestFirstName + "|" + "MRB Res";
					GuestLastName = GuestLastName + "|" + "last Name";
				}
			}
		}

		boolean isReservationCreated = false;
		if (ReservationType.equalsIgnoreCase("Single")) {
			try {
				Double depositAmount = 0.00;

				reservationPage.click_NewReservation(driver, testSteps);
				testSteps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.select_CheckInDate(driver, testSteps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, testSteps, CheckOutDate);
				reservationPage.enter_Adults(driver, testSteps, adult);
				reservationPage.enter_Children(driver, testSteps, children);
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, testSteps, derivedRatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, testSteps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);

				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);
					// Find this

					String minStayColor = "";
					String noCheckinColor = "";
					String noCheckoutColor = "";
//					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									testSteps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									testSteps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							}
						}

						
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								testSteps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								testSteps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								testSteps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}

						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								testSteps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								testSteps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								testSteps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}
					
						reservationPage.select_RoomWithRatePlanRulesValidation(driver, testSteps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);
				
						try {
							
							System.out.println("in try");
							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);
							
							app_logs.info("getPayAbleAmount from room class: " + getPayAbleAmount);
							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);
							
							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);
							
							rateIs = getRoomChargesFromTripSummary;
							app_logs.info(rateIs);

							testSteps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							testSteps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");

								testSteps.add("Verified room charges and room class charges are same");
								
								
							} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								testSteps.add("bug id: " + bugId);

							}
														
							rateListInReservation = reservationPage.intervalRateVerification(driver,
									ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
									children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
									
									personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), 
									adultRate.get(0),
									childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0), adultRatePerNight.get(0), 
									childRatePerNight.get(0),
									folioName,testSteps);
							
							
							for (int i = 0; i < rateListInReservation.size(); i++) {
								app_logs.info(rateListInReservation.get(i));
							}
							
							if (rateListInReservation.get(rateListInReservation.size()-1).get(0).equalsIgnoreCase("no")) {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								testSteps.add("bug id: " + bugId);

							}
						} catch (Exception e) {
							
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							testSteps.add("bug id: " + bugId);

						}
						
						
						
						

						reservationPage.clickNext(driver, testSteps);

						reservationPage.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName,
								GuestLastName, PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3,
								City, Country, State, PostalCode, IsGuesProfile);
						if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
							reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber,
									NameOnCard, CardExpDate);
						}

						reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", MarketSegment, Referral);

						reservationPage.clickBookNow(driver, testSteps);
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
						app_logs.info("Reservation number" + reservation);
						status = reservationPage.get_ReservationStatus(driver, testSteps);
						isReservationCreated = true;
						reservationPage.clickCloseReservationSavePopup(driver, testSteps);
						reservationPage.get_RoomNumber(driver, testSteps, "Yes");
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								testSteps);

						String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

						String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
						foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
						String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
						foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);

						reservationPage.click_Folio(driver, testSteps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, testSteps);

						// Verification of rateList in Folio
						reservationPage.includeTaxesinLineItems(driver, getTestSteps, false);
						//reservationPage.intervalRateFolioVerification(driver, rateListInReservation, "Anil RatezaZVhKWrUb");

						
						testSteps.add("======INTERVAL RATE PLAN VERIFICATION IN FOLIO LINE ITEM=====");
						Folio folio = new Folio();
						String category = "Room Charge";
						//String folioDescritption = "Anil RatezaZVhKWrUb";
						
						ArrayList<String> intervalDates = rateListInReservation.get(1);	
						ArrayList<String> folioRate = rateListInReservation.get(0);
						app_logs.info("folioRate: "+folioRate);
						app_logs.info("intervalDates: "+intervalDates);
						for (int i = 0; i < intervalDates.size(); i++) {
							
							
							String folioDates = Utility.parseDate(intervalDates.get(i), 
									ratesConfig.getProperty("defaultDateFormat")
									, "EE MMM dd, yyyy");

							String date = folio.itemDate(driver, category, i+1);
							assertEquals(date, folioDates,"Failed: Interval date is mismatching in folio line item!");
							
							String description = folio.itemDetailsDescroption(driver, category,i+1);
							assertEquals(description, folioName,"Failed: Description is mismatching in folio line item!");

							String amount = folio.itemAmount(driver, category,i+1);
							amount = folio.splitStringBySign(amount, "$");
							//String expectedAmount = String.format("%.2f", folioRate.get(i));
							assertEquals(amount, ""+amount,"Failed: Amount is mismatching in folio line item!");
							
						}
						testSteps.add("Verified interval rate along with dates and description");

						
						testSteps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, testSteps, "Anil RatezaZVhKWrUb");
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							testSteps.add("Rate plan folio name is matched : " + derivedRatePlanName);
							app_logs.info("Rate plan folio name is matched : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, testSteps);

						testSteps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, testSteps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							testSteps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						Double folioRCharges = Double.parseDouble(folioRoomCharges);
						Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							testSteps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							testSteps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, testSteps);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}
					}
//				}
//				// here end room class showing part
//				
//				else {
//					testSteps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
//					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
//				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
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
		} else if (ReservationType.equalsIgnoreCase("MRB")) {
			try {
				String Rateplan = derivedRatePlanName + "|" + derivedRatePlanName;
				reservationPage.click_NewReservation(driver, test_steps);
				if (derivedPromoCode.isEmpty()) {
					reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, adult, children,
							Rateplan, derivedPromoCode, IsSplitRes);
				} else {
					String rateplan = "Promo Code|Promo Code";
					derivedPromoCode = derivedPromoCode + "|" + derivedPromoCode;
					reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, adult, children,
							Rateplan, derivedPromoCode, IsSplitRes);
				}

				if (IsSplitRes.equalsIgnoreCase("Yes")) {
					reservationPage.enter_Adults(driver, test_steps, adult);
					reservationPage.enter_Children(driver, test_steps, children);
					reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, derivedPromoCode);
				}

				reservationPage.clickOnFindRooms(driver, test_steps);
				String minStayColor = "";

				String[] roomClass = ReservationRoomClasses.split(Utility.DELIM);

				ArrayList<String> minStayColorMRB = new ArrayList<String>();
				ArrayList<String> nocheckinColorMRB = new ArrayList<String>();
				ArrayList<String> nocheckoutColorMRB = new ArrayList<String>();

				boolean mrbFlag = true;

				for (int i = 0; i < roomClass.length; i++) {

					if (driver.findElements(By.xpath("(//span[text()='" + roomClass[i] + "'])[2]")).size() > 0
							|| isRoomClassAvailable) {

						boolean restriction = true;

						for (int m = 0; m < restricrionsLengthOfStayMRB.size(); m++) {
							if (!restricrionsLengthOfStayMRB.get(m) || !restricrionsBookingWindowMRB.get(m)) {
								restriction = false;
								break;
							}
						}

						if (restriction && isSeasonExist) {

						} else {
							try {
								reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
								mrbFlag = false;
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					} else {
						test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
						app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
						mrbFlag = false;
						break;
					}
				}

				if (mrbFlag) {
					ArrayList roomCost = reservationPage.select_MRBRoomsRatePlanValidation1(driver, test_steps,
							ReservationRoomClasses, "Yes", Account, adult, isMinStayRuleMRB,
							isMinStayRuleBrokenPopComeOrNotMRB, minStayRuleValueMRB, checkInColorMRB, checkOutColorMRB);
					Utility.app_logs.info("roomCost : " + roomCost);
					Double depositAmount = reservationPage.deposit(driver, test_steps, "No", "");
					reservationPage.clickNext(driver, test_steps);
					reservationPage.enter_MRB_MailingAddress(driver, test_steps, Salutation, GuestFirstName,
							GuestLastName, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1,
							Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, "No", IsSplitRes,
							Rooms);
					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					}
					Utility.app_logs.info(Rooms);
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment,
							Referral);
					reservationPage.clickBookNow(driver, test_steps);
					reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					status = reservationPage.get_ReservationStatus(driver, test_steps);
					isReservationCreated = true;
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);

					String[] roomClass1 = ReservationRoomClasses.split(Utility.DELIM);
					String[] adu = adult.split(Utility.DELIM);
					String[] child = children.split(Utility.DELIM);

					double str = 0.00;

					for (int i = 0; i < roomClass1.length; i++) {
						str = str + reservationPage.calculateRatesAsPerAdultsAndChildCapacityMRB(driver,
								ratesList.get(i), ratePlanAdults.get(i), ratePlanChilds.get(i), exAdultList.get(i),
								exChildList.get(i), capacityChild1.get(i), capacityAdult1.get(i), adu[i], child[i],
								test_steps, ratesConfig.getProperty("noCombination"), roomClass1[i],
								(List<Date>) dates1.get(i), ratesConfig.getProperty("defaultDateFormat"));
						app_logs.info(str);
					}

					test_steps.add("=================== Verify Rate Plan In Reservation ======================");
					app_logs.info("=================== Verify Rate Plan In Reservation ======================");
					ArrayList<String> ratePlanName = reservationPage.verifyRatePlanForMRB(driver, test_steps, Rateplan);

					String[] ratePlan = Rateplan.split(Utility.DELIM);
					for (int i = 0; i < ratePlanName.size(); i++) {
						try {
							assertTrue(ratePlan[i].trim().equalsIgnoreCase(ratePlanName.get(i)),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + ratePlanName.get(i));
							app_logs.info("Rate plan is matched for room class for : " + ratePlanName.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
							test_steps);

					reservationPage.click_Folio(driver, test_steps);
					ArrayList<String> roomChnrges = reservationPage.getMRBFolioBalance(driver, test_steps, RoomAbbri,
							"Yes", Rooms);

					test_steps.add("=================== Verify Folio Name In Reservation ======================");
					app_logs.info("=================== Verify Folio Name In Reservation ======================");
					boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
					try {
						assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
						test_steps.add("Rate plan folio name is matched : " + derivedRatePlanName);
						app_logs.info("Rate plan folio name is matched : " + derivedRatePlanName);
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}

					double expectedRoomCharges = str;
					double folioRoomCharges = 0.00;
					for (int i = 0; i < roomChnrges.size(); i++) {
						folioRoomCharges = folioRoomCharges + Double.parseDouble(roomChnrges.get(i).toString());
					}
					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
					TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
					Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
					try {
						assertTrue(Double.compare(expectedRoomCharges, folioRoomCharges) == 0,
								"Expeced room charges and folio room charges are not matched");
						test_steps
								.add("Expected room charges and folio room charges are same : " + expectedRoomCharges);
						app_logs.info("Expected room charges and folio room charges are same : " + expectedRoomCharges);
					} catch (Error e) {
						test_steps.add(e.toString());
					}

					try {
						assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
								"Expeced room charges and trip summary room charges are not matched");
						test_steps.add("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
						app_logs.info("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
					} catch (Error e) {
						test_steps.add(e.toString());
					}
					reservationPage.click_DeatilsTab(driver, test_steps);
					reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
					reervationDepositPolicy = reservationPage.getReservationDepositPolicy(driver);

					test_steps.add("Getting reservation no show policy : " + reervationNoShowPolicy);
					app_logs.info("Getting reservation no show policy : " + reervationNoShowPolicy);

					test_steps.add("Getting reservation deposit policy : " + reervationDepositPolicy);
					app_logs.info("Getting reservation deposit policy : " + reervationDepositPolicy);

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (ReservationType.equalsIgnoreCase("Group")) {

			// Create New Groups
			String AccountNo = "0";
			try {

				AccountName = AccountName + Utility.GenerateRandomString15Digit();

				navigation.Groups(driver);
				getTestSteps.clear();
				group.type_GroupName(driver, test, AccountName, getTestSteps);
				testSteps.addAll(getTestSteps);

				AccountNo = Utility.GenerateRandomString15Digit();
				getTestSteps.clear();
				getTestSteps = group.enterAccountNo(driver, AccountNo);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				group.type_MailingAddrtess(driver, test, GuestFirstName, GuestLastName, PhoneNumber, Address1, City,
						State, Country, PostalCode, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				group.Billinginfo(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				group.Save(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				getTestSteps.clear();
				getTestSteps = group.newReservation(driver);
				testSteps.addAll(getTestSteps);
				testSteps.add("=================== CREATE Reservation ======================");
				app_logs.info("=================== CREATE Reservation ======================");
				reservationPage.select_CheckInDate(driver, testSteps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, testSteps, CheckOutDate);
				reservationPage.enter_Adults(driver, testSteps, "2");
				reservationPage.enter_Children(driver, testSteps, "0");
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, testSteps, derivedRatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, testSteps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);

				String minStayColor = "";
				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
							|| isRoomClassAvailable) {

						

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									testSteps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									testSteps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								testSteps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								testSteps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								testSteps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								testSteps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								testSteps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								testSteps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, testSteps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);

						try {

							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);
							app_logs.info("getPayAbleAmount: " + getPayAbleAmount);
							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);
							app_logs.info("getRoomChargesFromRates: " + getRoomChargesFromTripSummary);

							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);
							rateIs = getRoomChargesFromTripSummary;

							testSteps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							testSteps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");

								testSteps.add("Verified room charges and room class charges are same");

								rateListInReservation = reservationPage.intervalRateVerification(driver,
										ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
										children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
										
										personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), 
										adultRate.get(0),
										childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0), adultRatePerNight.get(0), 
										childRatePerNight.get(0),
										folioName,testSteps);
								//rateIs = rateListInReservation.get(1);
							} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								testSteps.add("bug id: " + bugId);

							}
							System.out.println(rateIs);
							app_logs.info("rateIs: " + rateIs);

						} catch (Exception e) {
						}
						getTestSteps.clear();
						getTestSteps = reservationPage.clickNext(driver, getTestSteps);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", GuestFirstName,
								GuestLastName);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.enterEmail(driver, getTestSteps, Email);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.enter_PaymentDetails(driver, getTestSteps, "Cash", null, null,
								null);
						testSteps.addAll(getTestSteps);

						reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", MarketSegment, Referral);

						getTestSteps.clear();
						getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
						testSteps.addAll(getTestSteps);
						app_logs.info("Reservation number" + reservation);

						getTestSteps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTestSteps);
						testSteps.addAll(getTestSteps);
						isReservationCreated = true;
						getTestSteps.clear();
						getTestSteps = reservationPage.clickCloseReservation(driver, getTestSteps);
						testSteps.addAll(getTestSteps);

						reservationPage.verifyStatusAfterReservation(driver, foundStatus);

						getTestSteps.clear();
						getTestSteps = reservationPage.verifyAccountName(driver, AccountName, false);
						testSteps.addAll(getTestSteps);

						reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, testSteps);

						reservationPage.closeReservationTab(driver);
						testSteps.add("Close Reservation");
						app_logs.info("Close Reservation");

						reservationPage.Search_ResNumber_And_Click(driver, reservation);

						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								testSteps);
						reservationPage.click_Folio(driver, testSteps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, testSteps);

						testSteps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, testSteps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							testSteps.add("Rate plan folio name is matched : " + derivedRatePlanName);
							app_logs.info("Rate plan folio name is matched : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, testSteps);

						String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

						String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
						foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
						String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
						foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
						Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

						testSteps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, testSteps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							testSteps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						Double folioRCharges = Double.parseDouble(folioRoomCharges);
						Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							testSteps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							testSteps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, testSteps);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}
					}
				} else {
					testSteps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (ReservationType.equalsIgnoreCase("Group block")) {
			// Create New Groups
			String AccountNo = "0";
			try {
				isReservationCreated = false;
				navigation.Groups(driver);
				AccountName = AccountName + Utility.GenerateRandomString15Digit();
				getTestSteps.clear();
				group.type_GroupName(driver, test, AccountName, getTestSteps);
				testSteps.addAll(getTestSteps);

				AccountNo = Utility.GenerateRandomString15Digit();
				getTestSteps.clear();
				getTestSteps = group.enterAccountNo(driver, AccountNo);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				group.type_MailingAddrtess(driver, test, GuestFirstName, GuestLastName, PhoneNumber, Address1, City,
						State, Country, PostalCode, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				group.Billinginfo(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				group.Save(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			// Create RoomBlock

			try {

				group.navigateRoomBlock(driver, test);
				app_logs.info("Navigate to Room Block Tab");
				testSteps.add("Navigate to Room Block Tab");

				app_logs.info("==========CREATE NEW BLOCK==========");
				testSteps.add("==========CREATE NEW BLOCK==========");

				getTestSteps.clear();
				getTestSteps = group.navigateRoomBlock(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickNewBlock(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.EnterBlockName(driver, BlockName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickOkay_CreateNewBlock(driver);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========SEARCH ROOMS==========");
				testSteps.add("==========SEARCH ROOMS==========");

				getTestSteps.clear();
				getTestSteps = group.searchRoomsForBlock(driver, CheckInDate, CheckOutDate, RoomPerNight);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectRatePlan(driver, derivedRatePlanName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectAdults(driver, adult);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectChilds(driver, children);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickSearchGroup(driver);
				testSteps.addAll(getTestSteps);

				
				// Find this
				advgroup.updatedAutomaticallyAssignedRooms(driver, "0");
				advgroup.BlockRoomForSelectedRoomclass(driver, RoomPerNight, ReservationRoomClasses);

				// Find this

				restricrionsLengthOfStay = true;
				restricrionsBookingWindow = true;
				isSeasonExist = true;

				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					// ab : add method here

					String getAverageRatePerNight = advgroup.intervalRateVerificationInGroupBlock(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),personCapacity.get(0),
							isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), adultRate.get(0), childRate1.get(0), isProStayRate.get(0),
							customRatePerNight.get(0),  adultRatePerNight.get(0), childRatePerNight.get(0), testSteps);
					
				} else {

					testSteps.add("No combination found");
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Click on block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Click on block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Create Reservation
			try {
				// here create a reservation if need
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create reservation from group block", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create reservation from group block", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (ReservationType.equalsIgnoreCase("Account")) {

			// navigate to accounts
			try {

				navigation.Accounts(driver);
				testSteps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
				} else {

					Assert.assertTrue(false);
				}
			}
			String AccountNumber = null;
			// New account
			try {
				testSteps.add("****************** Creating account *********************");
				app_logs.info("****************** Creating account *********************");
				AccountName = AccountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, AccountType);
				CreateTA.AccountDetails(driver, AccountType, AccountName);
				AccountNumber = Utility.GenerateRandomString15Digit();
				CreateTA.ChangeAccountNumber(driver, AccountNumber);
				getTestSteps.clear();
				getTestSteps = CreateTA.AccountAttributes(driver, test, MarketSegment, Referral, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.Mailinginfo(driver, test, GuestFirstName, GuestLastName, PhoneNumber,
						PhoneNumber, Address1, Address2, Address3, Email, City, State, PostalCode, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.AccountSave(driver, test, AccountName, getTestSteps);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Clicking on New Reservation
			try {
				CreateTA.NewReservationButton(driver, test);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			String reservationNumber = null;

			try {

				testSteps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.select_CheckInDate(driver, testSteps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, testSteps, CheckOutDate);
				reservationPage.enter_Adults(driver, testSteps, "2");
				reservationPage.enter_Children(driver, testSteps, "0");
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, testSteps, derivedRatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, testSteps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									testSteps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									testSteps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								testSteps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								testSteps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								testSteps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								testSteps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								testSteps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								testSteps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, testSteps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);

						try {

							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);
							app_logs.info("getPayAbleAmount: " + getPayAbleAmount);
							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);
							app_logs.info("getRoomChargesFromRates: " + getRoomChargesFromTripSummary);

							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);
							rateIs = getRoomChargesFromTripSummary;

							testSteps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							testSteps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");

								testSteps.add("Verified room charges and room class charges are same");

								} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								testSteps.add("bug id: " + bugId);

							}
							rateListInReservation = reservationPage.intervalRateVerification(driver,
									ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
									children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
				
									personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), 
									adultRate.get(0),
									childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0), adultRatePerNight.get(0), 
									childRatePerNight.get(0),
									folioName,testSteps);

						//	rateIs = rateListInReservation.get(1);

							System.out.println(rateIs);
							app_logs.info("rateIs: " + rateIs);

						} catch (Exception e) {
						}

						getTestSteps.clear();
						getTestSteps = reservationPage.clickNext(driver, getTestSteps);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", GuestFirstName,
								GuestLastName);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.enterEmail(driver, getTestSteps, Email);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.enter_PaymentDetails(driver, getTestSteps, "Cash", null, null,
								null);
						testSteps.addAll(getTestSteps);

						reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", MarketSegment, Referral);

						getTestSteps.clear();
						getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
						testSteps.addAll(getTestSteps);
						app_logs.info("Reservation number" + reservation);

						getTestSteps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTestSteps);
						testSteps.addAll(getTestSteps);

						isReservationCreated = true;

						getTestSteps.clear();
						getTestSteps = reservationPage.clickCloseReservation(driver, getTestSteps);
						testSteps.addAll(getTestSteps);

						reservationPage.verifyStatusAfterReservation(driver, foundStatus);

						getTestSteps.clear();
						getTestSteps = reservationPage.verifyAccountName(driver, AccountName, false);
						testSteps.addAll(getTestSteps);

						reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, testSteps);

						reservationPage.closeReservationTab(driver);
						testSteps.add("Close Reservation");
						app_logs.info("Close Reservation");

						reservationPage.Search_ResNumber_And_Click(driver, reservation);

						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								testSteps);
						reservationPage.click_Folio(driver, testSteps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, testSteps);

						testSteps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, testSteps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							testSteps.add("Rate plan folio name is matched : " + derivedRatePlanName);
							app_logs.info("Rate plan folio name is matched : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, testSteps);

						String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

						String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
						foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
						String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
						foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
						Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

						testSteps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, testSteps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							testSteps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						Double folioRCharges = Double.parseDouble(folioRoomCharges);
						Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							testSteps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							testSteps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, testSteps);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}
					}
				} else {
					testSteps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (ReservationType.equalsIgnoreCase("Quote")) {
			try {
				isReservationCreated = false;
				testSteps.add("=================== CREATE QUOTE ======================");

				app_logs.info("=================== CREATE QUOTE ======================");
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_CheckInDate(driver, testSteps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, testSteps, CheckOutDate);
				reservationPage.enter_Adults(driver, testSteps, "2");
				reservationPage.enter_Children(driver, testSteps, "0");
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, testSteps, derivedRatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, testSteps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {


					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									testSteps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, testSteps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									testSteps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									testSteps.add(e.toString());
								} catch (Exception e) {
									testSteps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								testSteps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								testSteps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								testSteps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								testSteps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								testSteps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, testSteps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								testSteps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								testSteps.add(e.toString());
							} catch (Exception e) {
								testSteps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, testSteps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);
						

						try {

							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);
							app_logs.info("getPayAbleAmount: " + getPayAbleAmount);
							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);
							app_logs.info("getRoomChargesFromRates: " + getRoomChargesFromTripSummary);

							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);
							rateIs = getRoomChargesFromTripSummary;

							
							testSteps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							testSteps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");
								testSteps.add("Verified room charges and room class charges are same");
							} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								testSteps.add("bug id: " + bugId);

							}
							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");

								testSteps.add("Verified room charges and room class charges are same");

								
							}
							rateListInReservation = reservationPage.intervalRateVerification(driver,
									ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
									children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
									
									personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), 
									adultRate.get(0),
									childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0), adultRatePerNight.get(0), 
									childRatePerNight.get(0), folioName,testSteps);

						//	rateIs = rateListInReservation.get(1);
							System.out.println(rateIs);
							app_logs.info("rateIs: " + rateIs);

						} catch (Exception e) {
						}

						reservationPage.clickNext(driver, testSteps);

						reservationPage.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName,
								GuestLastName, PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3,
								City, Country, State, PostalCode, IsGuesProfile);
						if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
							reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber,
									NameOnCard, CardExpDate);
						}
						reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", MarketSegment, Referral);

						reservationPage.clickSaveAsQuoteButton(driver);
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
						app_logs.info("Reservation number" + reservation);

						status = reservationPage.get_ReservationStatus(driver, testSteps);
						reservationPage.clickCloseReservationSavePopup(driver, testSteps);
						reservationPage.get_RoomNumber(driver, testSteps, "Yes");
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								testSteps);

						String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

						String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
						foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
						String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
						foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
						Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

						reservationPage.click_Folio(driver, testSteps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, testSteps);

						testSteps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, testSteps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							testSteps.add("Rate plan folio name is matched : " + derivedRatePlanName);
							app_logs.info("Rate plan folio name is matched : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, testSteps);

						testSteps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, testSteps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							testSteps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						double folioRCharges = Double.parseDouble(folioRoomCharges);
						double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							testSteps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							testSteps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

						reservationPage.clickBookQuote(driver, testSteps);

						Wait.wait10Second();
						TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								testSteps);
						reservationPage.click_Folio(driver, testSteps);
						folioRoomCharges = reservationPage.get_RoomCharge(driver, testSteps);

						reservationPage.click_DeatilsTab(driver, testSteps);
						days = Utility.getNumberofDays(CheckInDate, CheckOutDate);

						expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						folioRCharges = Double.parseDouble(folioRoomCharges);
						tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							testSteps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							testSteps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							testSteps.add(e.toString());
						}
					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, testSteps);
						} catch (Error e) {
							testSteps.add(e.toString());
						} catch (Exception e) {
							testSteps.add(e.toString());
						}
					}
				} else {
					testSteps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (ReservationType.equalsIgnoreCase("tapechart")) {
			try {

				Tapechart tapechart = new Tapechart();
				testSteps.add("=================== Create Reservation from Tape Chart ======================");
				app_logs.info("=================== Create Reservation from Tape Chart ======================");
				navigation.navigateTapeChart(driver, test);
				testSteps.add("Navigate TapeChart");
				app_logs.info("Navigate TapeChart");
				app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
				testSteps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");

				if (PromoCode.isEmpty()) {
					tapechart.searchInTapechart(driver, CheckInDate, CheckOutDate, adult, children, derivedRatePlanName,
							PromoCode);
				} else {
					tapechart.searchInTapechart(driver, CheckInDate, CheckOutDate, adult, children, derivedRatePlanName,
							PromoCode);
				}

				String minStayColor = "";
				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					app_logs.info("==========SELECT ROOM==========");
					testSteps.add("==========SELECT ROOM==========");

					tapechart.clickAvailableSlotWithRatePalnValidation(driver, RoomAbbri.get(0), isMinStayRule,
							isMinStayRuleBrokenPopComeOrNot, minStayRuleValue, checkInColor, checkOutColor);
					testSteps.add("Click available room of Room Class '" + RoomAbbri.get(0) + "'");
					app_logs.info("Click on available room");

					testSteps.add("New Reservation page is opened");
					app_logs.info("New Reservation Page is Opened");

					// here verify interval rate plan
					// Find this

					reservationPage.clickOnEditReservation(driver);
					testSteps.add("Click on edit reservation in trip summary");

					try {

						String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
								ReservationRoomClasses);
						app_logs.info("getPayAbleAmount: " + getPayAbleAmount);
						String getRoomChargesFromTripSummary = reservationPage.getRoomChargesFromTripSummary(driver);
						app_logs.info("getRoomChargesFromRates: " + getRoomChargesFromTripSummary);
						app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);
						rateIs = getRoomChargesFromTripSummary;

						testSteps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
						testSteps.add("Room charges in room class section: " + getPayAbleAmount);

						if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
							app_logs.info("in if");

							testSteps.add("Verified room charges and room class charges are same");

						} else {
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							testSteps.add("bug id: " + bugId);

						}

						rateListInReservation = reservationPage.intervalRateVerification(driver,
								ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
								children,ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
								personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), 
								adultRate.get(0),
								childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0), adultRatePerNight.get(0), 
								childRatePerNight.get(0),folioName,testSteps);

						//rateIs = rateListInReservation.get(1);
						System.out.println(rateIs);
						app_logs.info("rateIs: " + rateIs);

					} catch (Exception e) {

					}

					reservationPage.clickNext(driver, testSteps);

					reservationPage.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName,
							PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3, City, Country, State,
							PostalCode, IsGuesProfile);
					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					}
					reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", MarketSegment, Referral);
					reservationPage.enter_MarketSegmentDetails(driver, testSteps, TravelAgent, MarketSegment,
							Referral);

					reservationPage.clickBookNow(driver, testSteps);
					reservation = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("Reservation number" + reservation);

					status = reservationPage.get_ReservationStatus(driver, testSteps);
					isReservationCreated = true;
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
					reservationPage.get_RoomNumber(driver, testSteps, "Yes");
					String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
							testSteps);

					String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

					String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
					foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
					String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
					foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
					Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

					testSteps.add("=================== Verify Rate Plan In Reservation ======================");
					app_logs.info("=================== Verify Rate Plan In Reservation ======================");
					String ratePlan = reservationPage.verifyRatePlan(driver, testSteps, derivedRatePlanName);

					try {
						assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
								"Rate plan is not matched for room class");
						testSteps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
						app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
					} catch (Error e) {
						testSteps.add(e.toString());
					} catch (Exception e) {
						testSteps.add(e.toString());
					}

					reservationPage.click_Folio(driver, testSteps);
					String folioRoomCharges = reservationPage.get_RoomCharge(driver, testSteps);

					// Verification of rateList in Folio
				//	reservationPage.includeTaxesinLineItems(driver, rateListInReservation, false);
				//	reservationPage.intervalRateFolioVerification(driver, rateListInReservation, RatePlanName);

					testSteps.add("=================== Verify Folio Name In Reservation ======================");
					app_logs.info("=================== Verify Folio Name In Reservation ======================");

					boolean folioFlag = reservationPage.verifyFolioName(driver, testSteps, folioName);
					try {
						assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
						testSteps.add("Rate plan folio name is matched : " + derivedRatePlanName);
						app_logs.info("Rate plan folio name is matched : " + derivedRatePlanName);
					} catch (Error e) {
						testSteps.add(e.toString());
					} catch (Exception e) {
						testSteps.add(e.toString());
					}

					reservationPage.click_DeatilsTab(driver, testSteps);

					double expectedRoomCharges = Double.parseDouble(rateIs);

					folioRoomCharges = folioRoomCharges.trim();

					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
					TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

					Double folioRCharges = Double.parseDouble(folioRoomCharges);
					Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
					try {
						assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
								"Expeced room charges and folio room charges are not matched");
						testSteps
								.add("Expected room charges and folio room charges are same : " + expectedRoomCharges);
						app_logs.info("Expected room charges and folio room charges are same : " + expectedRoomCharges);
					} catch (Error e) {
						testSteps.add(e.toString());
					}

					try {
						assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
								"Expeced room charges and trip summary room charges are not matched");
						testSteps.add("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
						app_logs.info("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
					} catch (Error e) {
						testSteps.add(e.toString());
					}

				} else {
					tapechart.verifyNoResultsmatchedInTapechart(driver, testSteps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed", testName, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed", testName, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		
		//boolean isReservationCreated = true;
		// reservation = "17821123";
		// Rooms.add("8");
		 //Rooms.add("1");
		 seasonDepositPolicy.add("DepositPolicy8kkUb04xb5");
		 seasonDepositPolicy.add("DepositPolicy8kkUb04xb5");

		 //RatePlanName = "Interval";


		if (isReservationCreated) {
			try {
				
				HashMap<String, String> getdepositAmount = new HashMap<String, String>();
				HashMap<String, Double> getcheckInAmount = new HashMap<String, Double>();
				ArrayList<HashMap<String, String>> getdepositPoliciesData = new ArrayList<HashMap<String, String>>();
				String depositAmount = null;
				String depositPolicyApplied = "";
				reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
				reervationDepositPolicy = reservationPage.getReservationDepositPolicy(driver);
				reervationCheckInPolicy = reservationPage.getReservationCheckInPolicy(driver);

				String rateDepositPlanPolicy = getRateLevelPolicy.get("Deposit");
				
				String seasonDepositpolicy = getSessionLevelPolicy.get("Deposit");

				app_logs.info("seasonDepositpolicy : " + seasonDepositpolicy);
				app_logs.info("reervationDepositPolicy : " + reervationDepositPolicy);
				if (seasonDepositpolicy.equalsIgnoreCase("NA")) {
					seasonDepositpolicy = "No Policy";
				}
				testSteps.add("=================== Verify the deposit policy in reservation ======================");
				app_logs.info("=================== Verify the deposit policy in reservation ======================");
				assertTrue(seasonDepositpolicy.equalsIgnoreCase(reervationDepositPolicy),
						"Reservation deposit policy is not matched");
				testSteps.add("Verified Reservation deposit policy is reservation policies : " + seasonDepositpolicy);
				app_logs.info("Verified Reservation deposit policy is reservation policies : " + seasonDepositpolicy);

				reservationPage.closeReservationTab(driver);

				if (!reervationDepositPolicy.equalsIgnoreCase("No Policy")) {
					navigation.inventory(driver);

					navigation.policies(driver, testSteps);

					if (CheckInDate.split(Utility.DELIM).length > 1) {
						for (String str : seasonDepositPolicy) {
							getdepositPoliciesData.add(policy.getpoliciesData(driver, testSteps,
									ratesConfig.getProperty("depositPolicyText"), str));
						}
					} else {
						getdepositPoliciesData.add(policy.getpoliciesData(driver, testSteps,
								ratesConfig.getProperty("depositPolicyText"), seasonDepositpolicy));
					}

					app_logs.info("getdepositPoliciesData: "+getdepositPoliciesData);
					// getdepositPoliciesData.add(policy.getpoliciesData(driver,
					 //testSteps,
					 //ratesConfig.getProperty("depositPolicyText"),reervationDepositPolicy ));

					reservationPage.navigateToReservationPage(driver);
				}
				driver.navigate().refresh();
				reservationPage.Search_ResNumber_And_Click(driver, reservation);
				reservationPage.clickFolio(driver, testSteps);
				HashMap<String, String> roomChargesAre = new HashMap<String, String>();
				ArrayList<String> roomCharges = new ArrayList<>();

				String[] chkin = CheckInDate.split(Utility.DELIM);
				String[] chkout = CheckOutDate.split(Utility.DELIM);
				String[] reservationSplit = ReservationRoomClasses.split(Utility.DELIM);
				
				testSteps.add("==================Verify Deposit Policy On Reservation==================");

				// need to update
								
				roomChargesAre = reservationPage.getRoomChargesFromFolioBasedOnDates1(driver, testSteps, chkin[0],
						chkout[0], true,Integer.parseInt(interval),isProStayRate.get(0));
				for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
					roomCharges.add(entry.getValue());
				}
				if (GuestFirstName.split(Utility.DELIM).length > 1) {
					reservationPage.click_FolioDetail_DropDownBox(driver, testSteps);
					reservationPage.clickFolioDetailOptionValue(driver, testSteps, RoomAbbri.get(1), Rooms.get(1));
					roomChargesAre = reservationPage.getRoomChargesFromFolioBasedOnDates1(driver, testSteps, chkin[1],
							chkout[1], true,Integer.parseInt(interval),isProStayRate.get(1));
					for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
						roomCharges.add(entry.getValue());
					}
					reservationPage.click_FolioDetail_DropDownBox(driver, testSteps);
					reservationPage.clickFolioDetailOptionValue(driver, testSteps, RoomAbbri.get(0), Rooms.get(0));
				}
				app_logs.info(roomCharges);

				if (isAccountPolicyApplicable.equals("Yes")) {
					// /*if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					// depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
					// policyAttr1Is[0], policyAttrValueIs[0]);}
					// else if(isAccountPolicyCreate.equalsIgnoreCase("No") &&
					// !(selectedpolicyNames.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText"))))
					// {
					// depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
					// getAccountPoliciesData.get(0).get("Type"),
					// getAccountPoliciesData.get(0).get("AttrValue"));
					// }
					
				} else if (!seasonDepositpolicy.equalsIgnoreCase("No Policy")) {
					if (CheckInDate.split(Utility.DELIM).length > 1) {
						for (int i = 0; i < seasonDepositPolicy.size(); i++) {
							String size = String.valueOf(ReservationRoomClasses.split(Utility.DELIM).length);
							getdepositAmount.put(seasonDepositPolicy.get(i),
									reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharges,
											getdepositPoliciesData.get(i).get("Type"),
											getdepositPoliciesData.get(i).get("AttrValue"), size));
						}
						app_logs.info("getdepositAmount: "+getdepositAmount.toString());
						ArrayList<Double> dbr = new ArrayList<Double>();
						for (Map.Entry<String, String> entry : getdepositAmount.entrySet()) {
							app_logs.info("entry: "+entry.getValue());
							dbr.add(Double.valueOf(entry.getValue()));
						}
						app_logs.info("dbr: "+dbr);
						DecimalFormat df = new DecimalFormat("0.00");
						df.setMaximumFractionDigits(2);
						depositAmount = df.format(Collections.max(dbr));
						app_logs.info(depositAmount);
						depositPolicyApplied = Utility.getKeyOfValue(getdepositAmount, depositAmount);

					} else {
						depositAmount = reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
								getdepositPoliciesData.get(0).get("Type"),
								getdepositPoliciesData.get(0).get("AttrValue"));
						// depositPolicyApplied=seasonDepositPolicy.get(0);
					}

				}
				app_logs.info(depositPolicyApplied);
				app_logs.info(depositAmount);
				String fourDigitCardNo = Utility.getCardNumberHidden(CardNumber);
				String paymentTypeIs = "";
				if (PaymentType.equals("MC")) {
					paymentTypeIs = "" + PaymentType + " " + fourDigitCardNo + " (" + CardExpDate + ")";
				} else if (PaymentType.equals("Cash")) {
					paymentTypeIs = PaymentType;
				}
				app_logs.info("paymentTypeIs: "+paymentTypeIs);
				if (isAccountPolicyApplicable.equals("Yes")) {
					// /* if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					// verificationDepositWithPolicy(PaymentType,policyNames.get(0),depositAmount,paymentTypeIs);
					// }
					// else if(isAccountPolicyCreate.equalsIgnoreCase("No") &&
					// !(selectedpolicyNames.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText"))))
					// {
					// verificationDepositWithPolicy(paymentType,selectedpolicyNames.get(policyTypes.get(0)),depositAmount,paymentTypeIs);
					// }else {
					// verificationDepoistWithoutPolicy(paymentType,checkInDates.get(0),ratesConfig.getProperty("noPolicyText"));
					// }
				} else {
					if (Utility.isEmptyStringArrayList(seasonDepositPolicy)) {
						//verificationDepositWithPolicy(PaymentType, reervationDepositPolicy, depositAmount,
							//	paymentTypeIs);
					} else {
						verificationDepoistWithoutPolicy(PaymentType,
								Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")),
								ratesConfig.getProperty("noPolicyText"));
					}
				}
				reservationPage.click_DeatilsTab(driver, testSteps);
				
					
		
		// end here is created try		
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to perform action", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			}catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to perform action", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
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
		}
		

	}

	private void verificationDepositWithPolicy(String paymentType, String policyName, String amount,
			String historyPaymentType) throws InterruptedException, ParseException, java.text.ParseException {
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, testSteps);
		reservationPage.verify_FolioPayment(driver, testSteps, amount);
		getTestSteps = reservationPage.clickOnDetails(driver);
		testSteps.addAll(getTestSteps);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, policyName, "");
		reservationPage.verifyTripSummaryPaidAmount(driver, testSteps, amount);
		reservationPage.click_History(driver, testSteps);
		reservationPage.verifyDepositAtHistoryTab(driver, testSteps, amount, historyPaymentType);
	}

	private void verificationDepoistWithoutPolicy(String paymentType, String date, String policyName)
			throws InterruptedException, ParseException, java.text.ParseException {
		reservationPage.verifyNoPaymentAtFolioLineItem(driver, testSteps, date, paymentType);
		getTestSteps = reservationPage.clickOnDetails(driver);
		testSteps.addAll(getTestSteps);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, policyName, "");
	}

	private void verificationCheckinWithPolicy(String paymentType, String policyName, String amount, String payment,
			String cardFormat) throws InterruptedException, ParseException {
		reservationPage.verifyCheckInPolicy(driver, testSteps, policyName, "");
		reservationPage.clickFolio(driver, testSteps);
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, testSteps);
		reservationPage.verify_FolioPayment(driver, testSteps, payment);
		reservationPage.click_History(driver, testSteps);
		reservationPage.verifyChekInReservationOnHistoryTab(driver, testSteps);
		reservationPage.verifyHistoryWithCapturedPayment(driver, testSteps, amount, cardFormat, paymentType);
	}

	private void verificationCheckinWithoutPolicy(String policyName) throws ParseException, InterruptedException {
		reservationPage.verifyCheckInPolicy(driver, testSteps, policyName, "");
		reservationPage.click_History(driver, testSteps);
		reservationPage.verifyChekInReservationOnHistoryTab(driver, testSteps);
	}

	private void completeCheckInIfPolicyExist(double balanceAmt, String amountToBePaid) throws Exception {
		if (balanceAmt > 0.00) {
			reservationPage.clickOnProceedToCheckInPaymentButton(driver, testSteps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			reservationPage.verifyAmountOnPaymentScreen(driver, amountToBePaid, testSteps);
			reservationPage.clickLogORPayAuthorizedButton(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			reservationPage.clickCloseButtonOfSuccessModelPopup(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
		} else {
			reservationPage.clickOnConfirmCheckInButton(driver, testSteps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}

	private void completeCheckInIfPolicyDoesntExist(double balanceAmt) throws InterruptedException {
		if (balanceAmt > 0.00 || balanceAmt == 0.00) {
			reservationPage.clickOnConfirmCheckInButton(driver, testSteps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyDerivedRateBasedInterval", excel);
	}

	// @AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
