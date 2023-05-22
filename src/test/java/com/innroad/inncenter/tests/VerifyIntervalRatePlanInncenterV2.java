package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.generic.NEW;
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
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import bsh.ParseException;

public class VerifyIntervalRatePlanInncenterV2 extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";

	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	CPReservationPage reservationPage = new CPReservationPage();
	boolean restricrionsBookingWindowChange = false;
	boolean restricrionsLengthOfStayChange = false;
	boolean isBookinWindowChange = false;
	ArrayList<ArrayList<String>> rateListInReservation = new ArrayList<>();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();

	boolean isSeasonExistChange = true;
	boolean isPromocodeChnage = false;
	boolean isMinStayRuleChnage = false;
	boolean isMinStayRuleBrokenPopComeOrNotChange = false;
	ArrayList<String> minStayRuleChnage = null;
	ArrayList<String> minruleChange = null;
	ArrayList<String> noCheckInRuleChnage = null;
	ArrayList<String> noCheckOutRuleChnage = null;
	String checkInColorChnage = null;
	int minStayRuleValueChange = 0;
	String checkOutColorChange = null;
	boolean verifyLenthOfStayCheckedChange = false;
	boolean verifyMinStayCondidtionChange = false;
	boolean verifyMaxStayConditionChange = false;
	HashMap<String, String> addStayofLengthChange = new HashMap<>();
	String PromoCodeChange = "";
	boolean isVerifyPoliciesChange = true;
	HashMap<String, String> capacityAdultChange = new HashMap<String, String>(),
			capacityChildChange = new HashMap<String, String>(), maxAdultChange = new HashMap<String, String>(),
			maxChildChange = new HashMap<String, String>();

	HashMap<String, String> getRatesChange = new HashMap<String, String>();
	HashMap<String, String> getExAdultChange = new HashMap<String, String>();
	HashMap<String, String> getExChildChange = new HashMap<String, String>();
	HashMap<String, String> getRatesPerNightChannelsChange = new HashMap<String, String>();
	List<Date> datesChnage = new ArrayList<Date>();

	String channelName = "innCenter";
	String reervationNoShowPolicy = "No Policy";
	String reervationDepositPolicy = "No Policy";
	String reervationCheckInPolicy = "No Policy";

	ArrayList<String> RoomAbbri = new ArrayList<String>();
	ArrayList<String> Rooms = new ArrayList<String>();
	boolean israteplanExist = false;
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
	HashMap<String, String> addStayofLength = new HashMap<>();
	boolean restricrionsBookingWindow = false;
	boolean restricrionsLengthOfStay = false;
	int minStayRuleValue = 0;
	boolean isBookinWindow = false;
	int daysChange = 0;
	String folioName = "";
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
	Folio folio = new Folio();

	ArrayList<String> oldbaseRate = new ArrayList<>();
	ArrayList<String> oldadultCapacity = new ArrayList<>();
	ArrayList<String> oldpersonCapacity = new ArrayList<>();
	ArrayList<Boolean> oldisAdditonalAdultChild = new ArrayList<>();
	ArrayList<String> oldmaxAdult1 = new ArrayList<>();
	ArrayList<String> oldmaxPerson = new ArrayList<>();
	ArrayList<String> oldadultRate = new ArrayList<>();
	ArrayList<String> oldchildRate1 = new ArrayList<>();
	ArrayList<Boolean> oldisProStayRate = new ArrayList<>();
	ArrayList<String> oldcustomRatePerNight = new ArrayList<>();
	ArrayList<String> oldadultRatePerNight = new ArrayList<>();
	ArrayList<String> oldchildRatePerNight = new ArrayList<>();

	ArrayList<HashMap<String, String>> ratesList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> exAdultList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> exChildList = new ArrayList<HashMap<String, String>>();
	HashMap<String, ArrayList<String>> getRoomClassWithRates = new HashMap<>();
	String reservation = "";

	boolean ReservationChangeRoomClass = false;
	boolean isRoomClassAvailableChange = true;
	HashMap<String, String> capacityAdult = new HashMap<String, String>(),
			capacityChild = new HashMap<String, String>(), maxAdult = new HashMap<String, String>(),
			maxChild = new HashMap<String, String>();
	HashMap<String, String> getRates = new HashMap<String, String>();
	HashMap<String, String> getExAdult = new HashMap<String, String>();
	HashMap<String, String> getExChild = new HashMap<String, String>();
	HashMap<String, String> getRatesPerNightChannels = new HashMap<String, String>();
	List<Date> dates = new ArrayList<Date>();
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
	ArrayList<String> seasonDepositPolicy = new ArrayList<String>();
	ArrayList<String> seasonCheckInPolicy = new ArrayList<String>();
	ArrayList<String> getRatesWithAdultsAndChild = new ArrayList<>();
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
	String testName = "";
	ArrayList<String> intervalDatesSingle = new ArrayList<>();
	ArrayList<String> folioRateSingle = new ArrayList<>();


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "getData")
	public void verifyIntervalRatePlanInncenterV2(String delim, String ReservationType, String AccountType,
			String CheckInDate, String CheckOutDate, String ReservationRoomClasses, String RulesUpdateType,
			String RulesUpdateStartDate, String RulesUpdateEndDate, String RulesUpdateDays, String Type_RulesUpdate,
			String RuleMinStayValue_RulesUpdate, String adult, String children, String isAccountPolicyApplicable,
			String ActionOnReservation, String ReservationChangeClass, String isAccountPolicyCreate,
			String FeesGuestMustPay, String PercentOfFee, String FeeChargesType, 
			String isProRateAtRateLevel,String isProRateAtSeaonLevel,String isAdditionalAdultChilds,String Verification) throws Exception {

		Utility.DELIM = delim;
		test_name = ReservationType + " Reservation, " + Verification;
		test_description = ReservationType + ": " + Verification;
		test_catagory = "IntervalRateplan";
		testName = test_name;

		app_logs.info("CheckInDate: "+CheckInDate);
		app_logs.info("CheckOutDate: "+CheckOutDate);

		String timeZone = "America/New_York";
		int days = ESTTimeZone.numberOfDaysBetweenDates(CheckInDate, CheckOutDate);
		app_logs.info("days: " + days);
		String getCurrentDate = Utility.getNextDate(0, "dd/MM/yyyy", timeZone);
		if (ESTTimeZone.CompareDates(CheckInDate, "dd/MM/yyyy", timeZone)) {
			CheckInDate = getCurrentDate;
			CheckOutDate = Utility.getNextDate(days, "dd/MM/yyyy", timeZone);

		}
		
		app_logs.info("CheckInDate: "+CheckInDate);
		app_logs.info("CheckOutDate: "+CheckOutDate);
		app_logs.info("RulesUpdateStartDate: "+RulesUpdateStartDate);
		app_logs.info("RulesUpdateEndDate: "+RulesUpdateEndDate);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"CreateIntervalRatePlanV2");
		app_logs.info("RulesUpdateDays: " + RulesUpdateDays);
		String[] arrayRuleUpdateDays = RulesUpdateDays.split("\\,");
		String isMon_RulesUpdate = arrayRuleUpdateDays[0];
		String isTue_RulesUpdate = arrayRuleUpdateDays[1];
		String isWed_RulesUpdate = arrayRuleUpdateDays[2];
		String isThu_RulesUpdate = arrayRuleUpdateDays[3];
		String isFri_RulesUpdate = arrayRuleUpdateDays[4];
		String isSat_RulesUpdate = arrayRuleUpdateDays[5];
		String isSun_RulesUpdate = arrayRuleUpdateDays[6];

		String RatePlanName = ratePlanData.get("RatePlanName");
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
		String MaxPersons = ratePlanData.get("MaxPersons");
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

		String rateIs = "";
		String[] RoomClass = RoomClasses.split("\\|");
		String policyName = "";
		String policyType = "";
		HashMap<String, ArrayList<String>> descWhileCreatePolicy = new HashMap<>();
		ArrayList<HashMap<String, String>> getAccountPoliciesData = new ArrayList<HashMap<String, String>>();
		String[] policyTypeIs;
		String[] policyNameIs;
		String[] policyAttr1Is = null;
		String[] policyAttrValueIs = null;
		String[] policyAttr2Is;
		ArrayList<String> policyNames = new ArrayList<String>();
		ArrayList<String> policyTypes = new ArrayList<String>();
		HashMap<String, String> selectedpolicyNames = new HashMap<String, String>();

		if (Utility.rateplanName.equals("")) {
			RatePlanName = RatePlanName + Utility.generateRandomString();
			app_logs.info("New rate created");

		}
		else {
			RatePlanName = Utility.rateplanName;
			app_logs.info("Rate already exist");


		}
		// end here
		days = 0;
		if (!ReservationType.equalsIgnoreCase("MRB")) {
			days = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			Login login = new Login();
			//envURL = "https://app.innroad.com/";
			try {
				//login.login(driver, envURL, "stone1", "robin", "Innroad@123");
				//login.login(driver, "https://app.qa3innroad.com/", "autoota", "autouser", "Letmein18@");
				login.login(driver, "https://app.qainnroad.com/", "autorates", "autouser", "Auto@123");


			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				login.login(driver, "", "autorates", "autouser", "Auto@123");
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
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

		boolean isComeFromRoomClass = false;
		if (ReservationType.equalsIgnoreCase("MRB") || ReservationType.equalsIgnoreCase("tapechart")
				|| ActionOnReservation.equalsIgnoreCase("Cancellation")) {

			test_steps.add("=================== Getting the Room Class Abbrivation ======================");
			app_logs.info("=================== Getting the Room Class Abbrivation ======================");
			try {
				navigation.Setup(driver);
				navigation.RoomClass(driver);
				RoomAbbri = rc.getAbbrivation(driver, delim, ReservationRoomClasses, test_steps);
				Utility.app_logs.info(RoomAbbri);
				isComeFromRoomClass = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		// create polices here
		try {
			if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
				if (isComeFromRoomClass) {
					navigation.inventoryFromRoomClass(driver, test_steps);
				} else {
					navigation.Inventory(driver, test_steps);

				}
				navigation.policies(driver, test_steps);

				policyName = "policyName" + Utility.generateRandomString();
				if (ActionOnReservation.equalsIgnoreCase("no Show")) {
					policyType = "No Show";
					test_steps.add("========== Creating new policy for account ==========");
					Wait.waitUntilPageLoadNotCompleted(driver, 5);
					descWhileCreatePolicy = policy.createPolicies(driver, test_steps, "", "", policyType, "", "", "",
							policyName, FeesGuestMustPay, PercentOfFee, FeeChargesType, "", "", "No", "");
					reservationPage.navigateToReservationPage(driver);
				} else if (ActionOnReservation.equalsIgnoreCase("Check in")) {
					policyType = "Check In";
					Wait.waitUntilPageLoadNotCompleted(driver, 5);
					policyTypeIs = policyType.split("\\|");
					policyNameIs = policyName.split("\\|");
					policyAttr1Is = FeesGuestMustPay.split("\\|");
					policyAttrValueIs = PercentOfFee.split("\\|");
					policyAttr2Is = FeeChargesType.split("\\|");
					if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
						test_steps.add("========== Creating new policy for account ==========");
						navigation.policies(driver, test_steps);
						Wait.waitUntilPageLoadNotCompleted(driver, 5);
						for (int i = 0; i < policyTypeIs.length; i++) {
							// String
							// policyNameis=policyNameIs[i]+Utility.generateRandomNumber();

							app_logs.info(policyName);
							policyNames.add(policyName);
							policyTypes.add(policyTypeIs[i]);
							policy.createPolicies(driver, test_steps, "", "", policyTypeIs[i], "", policyName,
									policyName, "", policyAttr1Is[i], policyAttrValueIs[i], policyAttr2Is[i], "", "",
									"No", "");
						}
					} else {
						for (int i = 0; i < policyTypeIs.length; i++) {
							policyTypes.add(policyTypeIs[i]);
						}
						app_logs.info(policyNames);
						app_logs.info(policyTypes);
					}
					reservationPage.navigateToReservationPage(driver);
				} else if (ActionOnReservation.equalsIgnoreCase("Cancellation")
						|| ActionOnReservation.equalsIgnoreCase("Cancel")) {
					policyType = "Cancellation";
				}
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create new policy for account", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create new policy for account", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			if (isComeFromRoomClass) {
				navigation.inventoryFromRoomClass(driver, test_steps);
			} else {
				navigation.Inventory(driver, test_steps);

			}

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

		} catch (Exception e) {
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

		test_steps.add("=================== Verify Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Rate plan Exist or Not ======================");
		try {
			if (!Utility.rateplanName.equals("")) {
				israteplanExist = rateGrid.isRatePlanExist(driver, RatePlanName, test_steps);

			}
			System.out.println("israteplanExist : " + israteplanExist);
			app_logs.info("israteplanExist : " + israteplanExist);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (!israteplanExist) {
			try {

				test_steps.add("=================== CREATE NEW INTERVAL RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW INTERVAL RATE PLAN ======================");

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

				RatesGrid ratesGrid = new RatesGrid();
				getTest_Steps.clear();
				getTest_Steps = ratesGrid.enterInterval(driver, interval);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = ratesGrid.byDefaultProrateCheckbox(driver, Boolean.parseBoolean(IsProRateStayInRate));
				test_steps.addAll(getTest_Steps);

				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, test_steps);

				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, test_steps);
				// String summaryChannels =
				// nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);

				// nightlyRate.verifyTitleSummaryValue(driver, "Channel",
				// summaryChannels, test_steps);

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

				// nightlyRate.verifyTitleSummaryValue(driver, "Restrictions",
				// restrictionsSummary, test_steps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						test_steps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				// nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName,
				// allPolicyDesc,
				// Boolean.parseBoolean(isPolicesReq), test_steps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to enter rate plan level value", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to enter rate plan level value", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

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
				nightlyRate.clickSaveAsActive(driver, test_steps);
				Wait.wait30Second();
				// navigation.cpReservation_Backward(driver);
				test_steps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");
				Utility.rateplanName = RatePlanName;
				app_logs.info("Utility.rateplanName: "+Utility.rateplanName);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to create interval rate plan", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to create interval rate plan", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}
		// add season in existing rate plan 
		try {
			RatesGrid ratesGrid = new RatesGrid();
			rateGrid.clickOnEditRatePlan(driver);
			boolean isProstayeachseasonCheckboxchecked =  ratesGrid.isProstayeachseasonCheckboxchecked(driver);
			if(ActionOnReservation.equals("Add Season with pro rate yes")) {
			if(!isProstayeachseasonCheckboxchecked)
				rateGrid.clickProstayeachseasonCheckboxchecked(driver, true);

			}
			if(ActionOnReservation.equals("Add Season with pro rate no")) {
				if(isProstayeachseasonCheckboxchecked)
					rateGrid.clickProstayeachseasonCheckboxchecked(driver, false);

				}

			nightlyRate.switchCalendarTab(driver, test_steps);
			ratesGrid.addSeasonForIntervalRatePlan(driver, test_steps, SeasonStartDate, SeasonEndDate,
					SeasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
					RoomClasses, isAdditionalAdultChilds, RatePerNight, MaxAdults, MaxPersons,
					AdditionalAdultsPerNight, AdditionalChildPerNight,
					isProRateAtRateLevel,IsProRateStayInRate, isProRateAtSeaonLevel, isProRateInRoomClass, ProRateRoomClassName,
					IsCustomPerNight, CustomeRoomClass, CustomRatePerNight, isAssignPolicyByRoomClass,
					CustomRateAdultPerNight, CustomRateChildPerNight, isCustomRatePerNightAdultandChild,
					RoomClassInPolicy);
			
			rateGrid.clickOnSaveratePlan(driver);
			rateGrid.verifyLoadingGone(driver);
			test_steps.add("Save rate plan after added new season into existing rate plan");
			rateGrid.closeOpendTabInMainMenu(driver);
			//Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Assert.assertTrue(false);
			

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, "Failed to add season into existing rate plan", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, "Failed to add season into existing rate plan", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
		try {
			test_steps.add(
					"=================== Getting the Availability of room classes in between check in and checkout dates ======================");
			app_logs.info(
					"=================== Getting the Availability of room classes in between check in and checkout dates ======================");
			rateGrid.clickOnAvailability(driver);
			ArrayList MRBAvail = new ArrayList();
			ArrayList MRBBlockOut = new ArrayList();
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				rateGrid.clickForRateGridCalender(driver, test_steps);
				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
				rateGrid.expandRoomClass1(driver, test_steps, ReservationRoomClasses);
				app_logs.info("==========Getting the availability count of the room class " + ReservationRoomClasses
						+ " in Availability tab==========");
				test_steps.add("==========Getting the availability count of the room class " + ReservationRoomClasses
						+ " in Availability tab==========");
				ArrayList<String> avail = rateGrid.getAvailability(driver, test_steps, ReservationRoomClasses, days,
						CheckInDate);

				app_logs.info("==========Getting the room class " + ReservationRoomClasses
						+ "os blocked out or not in Availability tab==========");
				test_steps.add("==========Getting the room class " + ReservationRoomClasses
						+ "os blocked out or not in Availability tab==========");
				ArrayList<Boolean> blockout = rateGrid.getBlockOutRoomClass(driver, test_steps, ReservationRoomClasses,
						days, channelName, CheckInDate);

				for (int i = 0; i < avail.size(); i++) {
					int available = Integer.parseInt(avail.get(i));
					boolean falg = blockout.get(i);
					if (available <= 0 || !falg) {
						isRoomClassAvailable = false;
						break;
					}
				}

			} else {
				String[] rm = ReservationRoomClasses.split("\\|");
				String[] chkin = CheckInDate.split("\\|");
				String[] chkout = CheckOutDate.split("\\|");
				int MRBdays = 0;
				for (int i = 0; i < rm.length; i++) {
					MRBdays = Utility.getNumberofDays(chkin[i], chkout[i]);
					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, chkin[i], "dd/MM/yyyy");
					rateGrid.expandRoomClass1(driver, test_steps, rm[i]);

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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify availability", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify availability", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			rateGrid.clickRatesTab(driver, test_steps);
			reservationRoomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click on rate tab ", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click on rate tab", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				rateGrid.clickForRateGridCalender(driver, test_steps);
				app_logs.info("clickForRateGridCalender");

				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
				app_logs.info("selectDateFromDatePicker");

				rateGrid.expandRoomClass1(driver, test_steps, reservationRoomClassesList.get(0));
				app_logs.info("expandRoomClass");

				try {
					rateGrid.expandChannel(driver, test_steps, reservationRoomClassesList.get(0), channelName);
					app_logs.info("expandChannel");

				} catch (Exception e) {
					test_steps.add("Failed: No channel is showing after expanded room class in rates grid");
				}

			}
			if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {
				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickOnBulkUpdate(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectBulkUpdateOption(driver, "Rules");
				test_steps.addAll(getTest_Steps);

				test_steps.add("==========SELECT START DATE==========");
				app_logs.info("==========SELECT START DATE==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectDate(driver, RulesUpdateStartDate, true);
				test_steps.addAll(getTest_Steps);

				test_steps.add("==========SELECT END DATE==========");
				app_logs.info("==========SELECT END DATE==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectDate(driver, RulesUpdateEndDate, false);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========CHECKING/UNCHECKING DAYS==========");
				test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Sun", isSun_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Mon", isMon_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Tue", isTue_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Wed", isWed_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Thu", isThu_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Fri", isFri_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Sat", isSat_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========SELECTING RATE PLAN==========");
				test_steps.add("==========SELECTING RATE PLAN==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========SELECTING ROOM CLASS==========");
				test_steps.add("==========SELECTING ROOM CLASS==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectItemsFromDropDowns(driver, "Room class", ReservationRoomClasses);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectItemsFromDropDowns(driver, "Source", channelName);
				test_steps.addAll(getTest_Steps);

				ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);

				for (String type : type_rulesUpdateList) {

					if (type.equalsIgnoreCase("min stay")) {
						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickMinimumStay(driver, "Yes");
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.enterMinimumStayValue(driver, RuleMinStayValue_RulesUpdate);
						test_steps.addAll(getTest_Steps);

					} else if (type.equalsIgnoreCase("No Check In")) {

						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickCheckin(driver, "Yes");
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickNoCheckInCheckbox(driver, "Yes");
						test_steps.addAll(getTest_Steps);

					} else if (type.equalsIgnoreCase("No Check out")) {
						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickCheckOut(driver, "Yes");
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickNoCheckOutCheckbox(driver, "Yes");
						test_steps.addAll(getTest_Steps);

					}

				}
				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickUpdateButton(driver);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
				test_steps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

				int numberOfDays = ESTTimeZone.numberOfDaysBetweenDates(RulesUpdateStartDate, RulesUpdateEndDate) + 1;
				String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
				test_steps.add("Expected total days : " + expectedDays);
				app_logs.info("Expected total days : " + expectedDays);
				String totalDays = rateGrid.getTotalDaysText(driver, "Rules");
				test_steps.add("Found : " + totalDays);
				app_logs.info("Found : " + totalDays);
				Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
				test_steps.add("Verified total number of days");
				app_logs.info("Verified total number of days");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickOnYesUpdateButton(driver);
				test_steps.addAll(getTest_Steps);
				Wait.wait10Second();
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
						rateGrid.clickForRateGridCalender(driver, test_steps);
						Utility.selectDateFromDatePicker(driver, newDateDate, "dd/MM/yyyy");
						rateGrid.overrideMinStayValue(driver, test_steps, roomClassName, channelName, "0");
						rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
								ratesConfig.getProperty("checkinRule"), false);
						rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
								ratesConfig.getProperty("checkoutRule"), false);

					}
				}
				for (String roomClassName : roomClassesList) {
					for (int i = 0; i < getDates.size(); i++) {
						newDateDate = Utility.getCustomDate(RulesUpdateStartDate, "dd/MM/yyyy", "dd/MM/yyyy", i);
						rateGrid.clickForRateGridCalender(driver, test_steps);
						Utility.selectDateFromDatePicker(driver, getDates.get(i), "dd/MM/yyyy");

						for (String type : type_rulesUpdateList) {

							if (type.equalsIgnoreCase("min stay")) {
								rateGrid.overrideMinStayValue(driver, test_steps, roomClassName, channelName,
										RuleMinStayValue_RulesUpdate);
							} else if (type.equalsIgnoreCase("No Check In")) {
								rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										ratesConfig.getProperty("checkinRule"), true);

							} else if (type.equalsIgnoreCase("No Check out")) {
								rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										ratesConfig.getProperty("checkoutRule"), true);
							}
						}
					}
				}

			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to over ride rule", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to over ride rule", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		int MRBDays = 0;
		try {
			test_steps.add("=================== Getting Rate Plan Restrictiona and Rules ======================");
			app_logs.info("=================== Getting Rate Plan Restrictiona and Rules ======================");

			if (ReservationType.equalsIgnoreCase("MRB")) {
				String[] MRBCheckIn = CheckInDate.split("\\" + Utility.DELIM);
				String[] MRBCheckOut = CheckOutDate.split("\\" + Utility.DELIM);
				String[] roomClass = ReservationRoomClasses.split("\\" + Utility.DELIM);

				for (int k = 0; k < roomClass.length; k++) {
					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, MRBCheckIn[k], "dd/MM/yyyy");
					rateGrid.expandRoomClass(driver, test_steps, roomClass[k]);
					try {
						rateGrid.expandChannel(driver, test_steps, roomClass[k], channelName);
						app_logs.info("expandChannel");

					} catch (Exception e) {
						test_steps.add("No channel is showing after expanded room class in rates grid");
					}


					MRBDays = Utility.getNumberofDays(MRBCheckIn[k], MRBCheckOut[k]);
					System.out.println("MRBCheckIn[k] : " + MRBCheckIn[k]);
					System.out.println("MRBCheckOut[k] : " + MRBCheckOut[k]);
					System.out.println("MRBDays : " + MRBDays);

					minStayRuleMRB = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, roomClass[k], channelName,
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
								test_steps, minStayRuleValueMRB.get(k), MRBDays));
					} else {
						isMinStayRuleMRB.add(false);
						isMinStayRuleBrokenPopComeOrNotMRB.add(reservationPage.verifyMinStayPopupComeOrNot(driver,
								test_steps, minStayRuleValueMRB.get(k), MRBDays));
					}

					ArrayList<String> noCheckInRule1 = null;
					noCheckInRule1 = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, roomClass[k],
							channelName, MRBDays);

					noCheckInRuleMRB.add(noCheckInRule1);

					System.out.println("noCheckInRule1 : " + noCheckInRule1);

					checkInColorMRB.add(reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule1,
							MRBCheckIn[k], MRBCheckOut[k]));
					ArrayList<String> noCheckOutRule1 = null;

					noCheckOutRule1 = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, roomClass[k],
							channelName, MRBDays);
					noCheckOutRuleMRB.add(noCheckOutRule1);
					checkOutColorMRB.add(reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
							noCheckOutRule1, MRBCheckIn[k], MRBCheckOut[k]));
					rateGrid.collapseRoomClass(driver, test_steps, roomClass[k]);
					app_logs.info("Min Stay Rule for room classes : " + minStayRuleValueMRB);
				}

				rateGrid.clickOnEditRatePlan(driver);
				folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
				interval = rateGrid.getInterval(driver);
				test_steps.add("Expected interval : " + interval);
				app_logs.info("Expected interval : " + interval);
				test_steps.add("Found : " + interval);
				app_logs.info("Found : " + interval);

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
						+ RatePlanName + "</b> ==========");
				bookingWindowRestrictions = rateGrid.getBookingWindowRestrictions(driver, test_steps, RatePlanName);
				// this wrong one

				for (int i = 1; i < roomClass.length; i++) {
					MRBDays = Utility.getNumberofDays(MRBCheckIn[i], MRBCheckOut[i]);
					restricrionsLengthOfStayMRB.add(
							reservationPage.verifylenthOfStayRestrictions(driver, test_steps, verifyLenthOfStayChecked,
									verifyMinStayCondidtion, verifyMaxStayCondition, addStayofLength, MRBDays));
					restricrionsBookingWindowMRB.add(reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
							isBookinWindow, MRBCheckIn[i], MRBCheckOut[i], bookingWindowRestrictions));
				}

				isPromocode = rateGrid.isPromoCodeChecked(driver, test_steps);

				if (isPromocode) {
					PromoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					PromoCode = "";
				}
				app_logs.info("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				app_logs.info("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				app_logs.info("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);
				test_steps.add("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				test_steps.add("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				test_steps.add("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				}

				String[] chkIn = CheckInDate.split("\\|");
				String[] splitRoomClass = ReservationRoomClasses.split("\\" + Utility.DELIM);

				for (int i = 0; i < chkIn.length; i++) {
					nightlyRate.switchCalendarTab(driver, test_steps);
					nightlyRate.selectSeasonDates(driver, test_steps, chkIn[i]);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);
					getRoomClassWithRates.put(splitRoomClass[i], rateGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, splitRoomClass[i], test_steps));
					nightlyRate.closeSeason(driver, test_steps);

				}

				app_logs.info(getRoomClassWithRates.get(splitRoomClass[0]));
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

				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

				if (isVerifyPolicies) {

					driver.navigate().refresh();
					rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
					rateGrid.clickOnEditRatePlan(driver);
					nightlyRate.switchCalendarTab(driver, test_steps);

					// need to update
					nightlyRate.selectSeasonDates(driver, test_steps, MRBCheckIn[0]);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);
					nightlyRate.clickSeasonPolicies(driver, test_steps);
					getSessionLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
					app_logs.info("getSessionLevelPolicy: " + getSessionLevelPolicy.toString());

					test_steps.add("==================Get Policy from  Season==================");
					ArrayList<String> deposit = new ArrayList<String>();
					ArrayList<String> checkin = new ArrayList<String>();

					if (CheckInDate.split("\\|").length > 1) {
						for (int i = 0; i < RoomClass.length; i++) {
							deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("depositPolicyText"), RoomClass[i], test_steps));
							checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("checkInPolicyText"), RoomClass[i], test_steps));
						}
					} else {
						deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
								ratesConfig.getProperty("depositPolicyText"), RoomClass[0], test_steps));
						checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
								ratesConfig.getProperty("checkInPolicyText"), RoomClass[0], test_steps));
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

					nightlyRate.closeSeason(driver, test_steps);

					nightlyRate.clickSaveRatePlan(driver, test_steps);
					rateGrid.verifyLoadingGone(driver);
					rateGrid.closeOpendTabInMainMenu(driver);
				}
			}

			// here start get rates for single room reservation
			else {
				app_logs.info("in else");

				app_logs.info("ReservationRoomClasses: " + ReservationRoomClasses);
				app_logs.info("channelName: " + channelName);
				app_logs.info("days: " + days);

				minStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationRoomClasses,
						channelName, days);
				minrule = minStayRule;

				Collections.sort(minrule);
				app_logs.info("get min stay rule in : " + minrule);
				app_logs.info("get min stay rule size : " + minrule.size());
				app_logs.info("get min stay rule highest value : " + minrule.get(minrule.size() - 1));
				minStayRuleValue = Integer.parseInt((String) minrule.get(minrule.size() - 1));

				if (minStayRuleValue > 0) {
					isMinStayRule = true;
					isMinStayRuleBrokenPopComeOrNot = reservationPage.verifyMinStayPopupComeOrNot(driver, minrule,
							minStayRuleValue, days);
				}

				noCheckInRule = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationRoomClasses,
						channelName, days);
				checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule,
						CheckInDate, CheckOutDate);
				noCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationRoomClasses,
						channelName, days);
				checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, noCheckInRule, noCheckOutRule,
						CheckInDate, CheckOutDate);

				// here start single reservation edit part
				Wait.wait5Second();
				rateGrid.clickOnEditRatePlan(driver);
				folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
				interval = rateGrid.getInterval(driver);
				test_steps.add("Get interval: " + interval);
				app_logs.info("Get interval: " + interval);
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
				app_logs.info("after get lenth of stay");

				isBookinWindow = rateGrid.isBookingWindowChecked(driver, test_steps);
				HashMap<String, String> bookingWindowRestrictions = new HashMap<>();
				test_steps.add("========== Getting booking window default restrictions for rate plan " + "<b>"
						+ RatePlanName + "</b> ==========");
				bookingWindowRestrictions = rateGrid.getBookingWindowRestrictions(driver, test_steps, RatePlanName);
				restricrionsLengthOfStay = reservationPage.verifylenthOfStayRestrictions(driver, test_steps,
						verifyLenthOfStayChecked, verifyMinStayCondidtion, verifyMaxStayCondition, addStayofLength,
						days);

				restricrionsBookingWindow = reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
						isBookinWindow, CheckInDate, CheckOutDate, bookingWindowRestrictions);
				isPromocode = rateGrid.isPromoCodeChecked(driver, test_steps);
				app_logs.info("after get booking window");


				if (isPromocode) {
					PromoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					PromoCode = "";
				}

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				}
				app_logs.info("after get rate level policies");

				nightlyRate.switchCalendarTab(driver, test_steps);

				if (!ReservationType.equalsIgnoreCase("MRB")) {
					isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
							CheckInDate, CheckOutDate);
				} else {
					ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);

					ArrayList<String> checkOutList = Utility.convertTokenToArrayList(CheckOutDate, delim);

					ArrayList<Boolean> isSeason = new ArrayList<Boolean>();
					int index = 0;
					for (String checkIn : checkInList) {
						isSeason.add(nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
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
						nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
						nightlyRate.clickEditThisSeasonButton(driver, test_steps);

						getRoomClassWithRates.put(ReservationRoomClasses,
								rateGrid.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationRoomClasses,
										test_steps));
						nightlyRate.closeSeason(driver, test_steps);

						app_logs.info(getRoomClassWithRates.get(ReservationRoomClasses));
						ArrayList<String> gettest = new ArrayList<>();
						for (int i = 0; i < getRoomClassWithRates.size(); i++) {
							app_logs.info(getRoomClassWithRates.get(ReservationRoomClasses));

							gettest = getRoomClassWithRates.get(ReservationRoomClasses);
							app_logs.info("arraylistSize" + gettest.size());
							for (int j = 0; j < gettest.size(); j++) {
								app_logs.info(gettest.get(j));
							}

						}
					}

					rateGrid.closeOpendTabInMainMenu(driver);
					if (isVerifyPolicies) {

						driver.navigate().refresh();
						rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
						rateGrid.clickOnEditRatePlan(driver);
						nightlyRate.switchCalendarTab(driver, test_steps);
						nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
						nightlyRate.clickEditThisSeasonButton(driver, test_steps);

						nightlyRate.clickSeasonPolicies(driver, test_steps);
						getSessionLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);

						app_logs.info("getSessionLevelPolicy: " + getSessionLevelPolicy);
						test_steps.add("==================Get Policy from  Season==================");
						app_logs.info("==================Get Policy from  Season==================");

						ArrayList<String> deposit = new ArrayList<String>();
						ArrayList<String> checkin = new ArrayList<String>();

						if (CheckInDate.split("\\|").length > 1) {
							for (int i = 0; i < RoomClass.length; i++) {
								deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
										ratesConfig.getProperty("depositPolicyText"), RoomClass[i], test_steps));
								checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
										ratesConfig.getProperty("checkInPolicyText"), RoomClass[i], test_steps));
							}
						} else {
							deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("depositPolicyText"), RoomClass[0], test_steps));
							checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("checkInPolicyText"), RoomClass[0], test_steps));
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
						app_logs.info("seasonDepositPolicy: " + seasonDepositPolicy);
						app_logs.info("seasonCheckInPolicy" + seasonCheckInPolicy);
						nightlyRate.closeSeason(driver, test_steps);
					}

				} else {
					app_logs.info("No Season For Desired Date");
				}
				rateGrid.clickOnSaveratePlan(driver);
				// here put wait for checking element gone
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} // end here single reservation

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify season in single room reservation", testName, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify season in single room reservation", testName, "RatesV2",
						driver);
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

		app_logs.info("after getting room classes");
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
		String AccountName = "AccountName_";
		String MargetSegment = "GDS";
		String BlockName = "Test Block";
		String RoomPerNight = "1";
		String noOfNightsGroupBlock = "2";
		String ExpectedAbgPerNight = "";

		app_logs.info("room is showing");
		String baserate = "";
		String adultCapicity = "";
		String personCapicity = "";
		boolean isAdditionalAdultChild1 = false;
		String maxAdults = "";
		String maxperson = "";
		String adultsRate = "";
		String childRate = "";
		boolean isProRate = false;
		String ratePerNight = "";
		String adultPerNight = "";
		String childPerNight = "";
		String[] roomClass = ReservationRoomClasses.split("\\" + Utility.DELIM);
		boolean isAdditionalAdultChild = true;
		
//		ArrayList<String> getRates = new ArrayList<String>();
//		getRates.add("200");
//		getRates.add("2");
//		getRates.add("4");
//		getRates.add("yes");
//		getRates.add("3");
//		getRates.add("6");
//		getRates.add("100");
//		getRates.add("100");
//		getRates.add("yes");
//		getRates.add("66.67");
//		getRates.add("33.33");
//		getRates.add("33.33");
//		//RatePlanName = "Anil RateHMyC1S9v6t";
//		//folioName = "Anil RateHMyC1S9v6t";
//		//interval = "3";
//
//		getRoomClassWithRates.put(roomClass[0], getRates);
//		getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);
//		app_logs.info("getRatesWithAdultsAndChild:" + getRatesWithAdultsAndChild);

		for (int j = 0; j < roomClass.length; j++) {
			
//			baserate = getRates.get(0);
//			baseRate.add(baserate);
//			oldbaseRate.add(baserate);
//
//			adultCapacity.add(getRates.get(1));
//			oldadultCapacity.add(getRates.get(1));
//
//			personCapacity.add(getRates.get(2));
//			oldpersonCapacity.add(getRates.get(2));
//
//			isAdditionalAdultChild = false;
//			if (getRates.get(3).equalsIgnoreCase("yes")) {
//
//				isAdditionalAdultChild = true;
//			}
//			app_logs.info("additional: " + isAdditionalAdultChild);
//			isAdditonalAdultChild.add(isAdditionalAdultChild);
//			oldisAdditonalAdultChild.add(isAdditionalAdultChild);
//
//			maxAdult1.add(getRates.get(4));
//			oldmaxAdult1.add(getRates.get(4));
//
//			//maxperson = getRatesWithAdultsAndChild.get(5);
//			//app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
//			maxPerson.add(getRates.get(5));
//			oldmaxPerson.add(getRates.get(5));
//
//			adultRate.add(getRates.get(6));
//			oldadultRate.add(getRates.get(6));
//
//			childRate1.add(getRates.get(7));
//			oldchildRate1.add(getRates.get(7));
//
//			isProRate = false;
//			if (getRates.get(8).equalsIgnoreCase("yes")) {
//
//				isProRate = true;
//			}
//			isProStayRate.add(isProRate);
//			oldisProStayRate.add(isProRate);
//
//			customRatePerNight.add(getRates.get(9));
//			oldcustomRatePerNight.add(getRates.get(9));
//
//			adultRatePerNight.add(getRates.get(10));
//			oldadultRatePerNight.add(getRates.get(10));
//
//			childRatePerNight.add(getRates.get(11));
//			oldchildRatePerNight.add(getRates.get(11));

		
			

			getRatesWithAdultsAndChild = getRoomClassWithRates.get(roomClass[j]);
			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("Capacity adult: " + baserate);
			baseRate.add(baserate);
			oldbaseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);
			oldadultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);
			oldpersonCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);
			oldisAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);
			oldmaxAdult1.add(maxAdults);

			maxperson = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(maxperson);
			oldmaxPerson.add(maxperson);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);
			oldadultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);
			oldchildRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);
			oldisProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);
			oldcustomRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);
			oldadultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);
			oldchildRatePerNight.add(childPerNight);

		}

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

				reservationPage.click_NewReservation(driver, test_steps);
				test_steps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				PromoCode = "";
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);

				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);
					// Find this

					String minStayColor = "";
					restricrionsLengthOfStay = true;
					restricrionsBookingWindow = true;
					isSeasonExist = true;
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
						isMinStayRule = false;
						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Successfully veried the red color lable for min stay rule");
									test_steps.add("Successfully verified the red color label for minimum stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);
						//Wait.wait10Second();

						try {
							
							
							rateListInReservation = reservationPage.intervalRateVerification(driver,
									ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
									children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),

									personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0),
									maxPerson.get(0), adultRate.get(0), childRate1.get(0), isProStayRate.get(0),
									customRatePerNight.get(0), adultRatePerNight.get(0), childRatePerNight.get(0),
									folioName, test_steps);

							for (int i = 0; i < rateListInReservation.size(); i++) {
								app_logs.info(rateListInReservation.get(i));
							}

							if (rateListInReservation.get(rateListInReservation.size() - 1).get(0)
									.equalsIgnoreCase("no")) {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}

							System.out.println("in try");
							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);
							getPayAbleAmount = folio.splitStringBySign(getPayAbleAmount, "$");

							app_logs.info("getPayAbleAmount from room class: " + getPayAbleAmount);
							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);

							getRoomChargesFromTripSummary = folio.splitStringBySign(getRoomChargesFromTripSummary, "$");
							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);

							rateIs = getRoomChargesFromTripSummary;
							app_logs.info(rateIs);

							test_steps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							test_steps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");

								test_steps.add("Verified room charges in select room room class sectiona and in trip summary are matching");

							} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}

						} catch (Exception e) {
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							test_steps.add("bug id: " + bugId);

						}

						reservationPage.clickNext(driver, test_steps);
						reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName,
								GuestLastName, PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3,
								City, Country, State, PostalCode, IsGuesProfile);
						if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
							reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber,
									NameOnCard, CardExpDate);
						}

						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						reservationPage.clickBookNow(driver, test_steps);
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
						app_logs.info("Reservation number" + reservation);
						isReservationCreated = true;
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						reservationPage.get_RoomNumber(driver, test_steps, "Yes");
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);

						String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

						String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
						foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
						String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
						foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);

						// Verification of rateList in Folio

						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);
						reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
						test_steps.add("======INTERVAL RATE PLAN VERIFICATION IN FOLIO LINE ITEM=====");
						String category = "Room Charge";
						
						folioRateSingle = rateListInReservation.get(0);
						intervalDatesSingle = rateListInReservation.get(1);
						app_logs.info("folioRate: " + folioRateSingle);
						app_logs.info("intervalDates: " + intervalDatesSingle);
						
						for (int i = 0; i < intervalDatesSingle.size(); i++) {

							String folioDates = Utility.parseDate(intervalDatesSingle.get(i),
									ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

							String date = folio.itemDate(driver, category, i + 1);
							app_logs.info("expecetd: " + folioDates);
							app_logs.info("found: " + date);

							// assertEquals(date, folioDates, "Failed: Interval date is
							// mismatching in folio line item!");

							String description = folio.itemDetailsDescroption(driver, category, i + 1);
							app_logs.info("expected description name: " + folioName);
							app_logs.info("found: " + description);

							// assertEquals(description, folioName, "Failed: Description
							// is mismatching in folio line item!");

							String getamount = folio.itemAmount(driver, category, i + 1);
							getamount = folio.splitStringBySign(getamount, "$");
							double dbAmount = Double.parseDouble(folioRateSingle.get(i));
							String strAmount = String.format("%.2f", dbAmount);
							app_logs.info("expected amount: " + strAmount);
							app_logs.info("found: " + getamount);

							// assertEquals(getamount, strAmount, "Failed: Amount is
							// mismatching in folio line item!");
							test_steps.add(folioDates + "         " + folioName + "           " + strAmount);
							test_steps.add(date + "          " + description + "            " + getamount);


							if (!date.equalsIgnoreCase(folioDates)) {
								test_steps.add("Failed: Folio date is mismatching!");
							}

							if (!description.equalsIgnoreCase(folioName)) {
								test_steps.add("Failed: Folio description is mismatching!");

							}

							if (!getamount.equalsIgnoreCase(strAmount)) {
								test_steps.add("Failed: Folio amount is mismatching!");

							}
							

						}
						test_steps.add("Verified interval rate along with dates and description");
						
						reservationPage.click_DeatilsTab(driver, test_steps);

					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							//test_steps.add(e.toString());
						} catch (Exception e) {
							//test_steps.add(e.toString());
						}
					}
				
						}
				// end if room class is showing or not
				else {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}
			} catch (Exception e) {
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
		} else if (ReservationType.equalsIgnoreCase("MRB")) {

			try {
				String Rateplan = RatePlanName + "|" + RatePlanName;
				reservationPage.click_NewReservation(driver, test_steps);
				if (PromoCode.isEmpty()) {
					reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, adult, children,
							Rateplan, PromoCode, IsSplitRes);
				} else {
					String rateplan = "Promo Code|Promo Code";
					PromoCode = PromoCode + "|" + PromoCode;
					reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, adult, children,
							Rateplan, PromoCode, IsSplitRes);
				}

				if (IsSplitRes.equalsIgnoreCase("Yes")) {
					reservationPage.enter_Adults(driver, test_steps, adult);
					reservationPage.enter_Children(driver, test_steps, children);
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				}

				reservationPage.clickOnFindRooms(driver, test_steps);
				String minStayColor = "";

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
				// mrbFlag = true;
				if (mrbFlag) {
					// here I will add mine method

					HashMap<String, ArrayList<ArrayList<String>>> rates = new HashMap<>();
					ArrayList<String> getRatesList = new ArrayList<>();
					app_logs.info("isMinStayRuleMRB: " + isMinStayRuleMRB.size());
					app_logs.info("isMinStayRuleMRB: " + isMinStayRuleMRB.toString());
					
					app_logs.info("isMinStayRuleBrokenPopComeOrNotMRB: " + isMinStayRuleBrokenPopComeOrNotMRB.size());
					app_logs.info("isMinStayRuleBrokenPopComeOrNotMRB: " + isMinStayRuleBrokenPopComeOrNotMRB.toString());

					app_logs.info("checkInColorMRB: " + checkInColorMRB.size());
					app_logs.info("checkInColorMRB: " + checkInColorMRB.toString());

					app_logs.info("checkOutColorMRB: " + checkOutColorMRB.size());
					app_logs.info("checkOutColorMRB: " + checkOutColorMRB.toString());


					rates = reservationPage.verifyMRBForIntervalRatePlan(driver,
 							test_steps,
 							ReservationRoomClasses, "Yes", Account, adult, isMinStayRuleMRB, 
 							isMinStayRuleBrokenPopComeOrNotMRB,
 							minStayRuleValueMRB,checkInColorMRB,checkOutColorMRB,
 							children,
 							CheckInDate,CheckOutDate,interval,baseRate,adultCapacity,
 							personCapacity,isAdditonalAdultChild,maxAdult1,maxPerson,adultRate,
 							childRate1,isProStayRate,customRatePerNight,adultRatePerNight,childRatePerNight,
 							"dd/MM/yyyy",folioName);
 					
					app_logs.info("rates: " + rates.toString());
					app_logs.info("getRatesList: " + getRatesList.toString());
					reservationPage.clickNext(driver, test_steps);
					
					reservationPage.enter_MRB_MailingAddress(driver, test_steps, Salutation, GuestFirstName,
							GuestLastName, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1,
							Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, "No", IsSplitRes,
							Rooms);
					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					}
					System.out.println(Rooms);
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment,
							Referral);
					reservationPage.clickBookNow(driver, test_steps);
					reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					app_logs.info("Reservation number" + reservation);
					isReservationCreated = true;
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					
					
					reservationPage.click_Folio(driver, test_steps);
					String category = "Room Charge";
					app_logs.info("rates.get(RoomClass[i]: "+rates.get(RoomClass[0]));
					app_logs.info("rates.get(RoomClass[i]: "+rates.get(RoomClass[1]));

					for (int i = 0; i < RoomClass.length; i++) {
						ArrayList<String> folioRate = new ArrayList<String>();
						ArrayList<String> intervalDates = new ArrayList<String>();
						rateListInReservation.clear();
						rateListInReservation = rates.get(RoomClass[i]);
						String guestfolioName = "Guest Folio For " + RoomAbbri.get(i) + " : ";
						
						folio.changeFolioOption(driver, guestfolioName);
						test_steps.add("Select folio: "+guestfolioName);
						
						folioRate = rateListInReservation.get(0);
						app_logs.info("folioRate: "+folioRate);

						intervalDates = rateListInReservation.get(1);
						app_logs.info("intervalDates: "+intervalDates);
						
						reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);


						for (int j = 0; j < intervalDates.size(); j++) {
							
						
						String folioDates = Utility.parseDate(intervalDates.get(j),
								ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

						String date = folio.itemDate(driver, category, j + 1);
						app_logs.info("expecetd: " + folioDates);
						app_logs.info("found: " + date);

						// assertEquals(date, folioDates, "Failed: Interval date is
						// mismatching in folio line item!");

						String description = folio.itemDetailsDescroption(driver, category, j + 1);
						app_logs.info("expected description name: " + folioName);
						app_logs.info("found: " + description);

						// assertEquals(description, folioName, "Failed: Description
						// is mismatching in folio line item!");

						String getamount = folio.itemAmount(driver, category, j + 1);
						getamount = folio.splitStringBySign(getamount, "$");
						double dbAmount = Double.parseDouble(folioRate.get(j));
						String strAmount = String.format("%.2f", dbAmount);
						app_logs.info("expected amount: " + strAmount);
						app_logs.info("found: " + getamount);

						// assertEquals(getamount, strAmount, "Failed: Amount is
						// mismatching in folio line item!");
						test_steps.add(folioDates + "         " + folioName + "           " + strAmount);
						test_steps.add(date + "          " + description + "            " + getamount);


						if (!date.equalsIgnoreCase(folioDates)) {
							test_steps.add("Failed: Folio date is mismatching!");
						}

						if (!description.equalsIgnoreCase(folioName)) {
							test_steps.add("Failed: Folio description is mismatching!");

						}

						if (!getamount.equalsIgnoreCase(strAmount)) {
							test_steps.add("Failed: Folio amount is mismatching!");

						}
						
						}// inner loop
					
						
						}
					test_steps.add("Verified folio line item for MRB reservation");
					reservationPage.ClickOnDetails(driver);

				}
				else {
					test_steps.add("No rate combination found");
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to verify MRB Reservation", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to verify MRB Reservation", testName, "RatesV2", driver);
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
				group.type_MailingAddrtess(driver, test, GuestFirstName, GuestLastName, PhoneNumber, Address1, City,
						State, Country, PostalCode, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Billinginfo(driver, test, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Save(driver, test, getTest_Steps);
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

			try {

				getTest_Steps.clear();
				getTest_Steps = group.newReservation(driver);
				test_steps.addAll(getTest_Steps);
				test_steps.add("=================== CREATE Reservation ======================");
				app_logs.info("=================== CREATE Reservation ======================");
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);

				String minStayColor = "";
				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
							|| isRoomClassAvailable) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationRoomClasses, "Yes", "Account", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);
						Wait.wait5Second();

						try {
							// update group

							rateListInReservation = reservationPage.intervalRateVerification(driver,
									ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
									children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),

									personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0),
									maxPerson.get(0), adultRate.get(0), childRate1.get(0), isProStayRate.get(0),
									customRatePerNight.get(0), adultRatePerNight.get(0), childRatePerNight.get(0),
									folioName, test_steps);

							for (int i = 0; i < rateListInReservation.size(); i++) {
								app_logs.info(rateListInReservation.get(i));
							}

							if (rateListInReservation.get(rateListInReservation.size() - 1).get(0)
									.equalsIgnoreCase("no")) {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}

							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);
							getPayAbleAmount = folio.splitStringBySign(getPayAbleAmount, "$");
							app_logs.info("getPayAbleAmount: " + getPayAbleAmount);

							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);
							getRoomChargesFromTripSummary = folio.splitStringBySign(getRoomChargesFromTripSummary, "$");
							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);

							rateIs = getRoomChargesFromTripSummary;
							app_logs.info(rateIs);

							test_steps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							test_steps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");

								test_steps.add("Verified room charges in select room room class sectiona and in trip summary are matching");

							} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}

						} catch (Exception e) {
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							test_steps.add("bug id: " + bugId);

						}

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", GuestFirstName,
								GuestLastName);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, Email);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null,
								null);
						test_steps.addAll(getTest_Steps);

						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);
						app_logs.info("Reservation number" + reservation);

						getTest_Steps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);
						isReservationCreated = true;
						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickCloseReservation(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						reservationPage.verifyStatusAfterReservation(driver, foundStatus);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
						test_steps.addAll(getTest_Steps);

						reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

						reservationPage.closeReservationTab(driver);
						test_steps.add("Close Reservation");
						app_logs.info("Close Reservation");

						reservationPage.Search_ResNumber_And_Click(driver, reservation);

						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);
						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);
						reservationPage.includeTaxesinLineItems(driver, test_steps, false);
						String category = "Room Charge";
						ArrayList<String> intervalDates = rateListInReservation.get(1);
						ArrayList<String> folioRate = rateListInReservation.get(0);
						app_logs.info("folioRate: " + folioRate);
						app_logs.info("intervalDates: " + intervalDates);

						for (int i = 0; i < intervalDates.size(); i++) {

							String folioDates = Utility.parseDate(intervalDates.get(i),
									ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

							String date = folio.itemDate(driver, category, i + 1);
							app_logs.info("expecetd: " + folioDates);
							app_logs.info("found: " + date);

							// assertEquals(date, folioDates, "Failed: Interval date is
							// mismatching in folio line item!");

							String description = folio.itemDetailsDescroption(driver, category, i + 1);
							app_logs.info("expected description name: " + folioName);
							app_logs.info("found: " + description);

							// assertEquals(description, folioName, "Failed: Description
							// is mismatching in folio line item!");

							String getamount = folio.itemAmount(driver, category, i + 1);
							getamount = folio.splitStringBySign(getamount, "$");
							double dbAmount = Double.parseDouble(folioRate.get(i));
							String strAmount = String.format("%.2f", dbAmount);
							app_logs.info("expected amount: " + strAmount);
							app_logs.info("found: " + getamount);

							// assertEquals(getamount, strAmount, "Failed: Amount is
							// mismatching in folio line item!");
							test_steps.add(folioDates + "         " + folioName + "           " + strAmount);
							test_steps.add(date + "         " + description + "           " + getamount);

							if (!date.equalsIgnoreCase(folioDates)) {
								test_steps.add("Failed: Folio date is mismatching!");
								//break;
							}

							if (!description.equalsIgnoreCase(folioName)) {
								test_steps.add("Failed: Folio description is mismatching!");
								//break;

							}

							if (!getamount.equalsIgnoreCase(strAmount)) {
								test_steps.add("Failed: Folio amount is mismatching!");
								//break;

							}

							test_steps.add(folioDates + "     " + folioName + "        " + strAmount);
						}
						test_steps.add("Verified interval rate along with dates and description");
						reservationPage.click_DeatilsTab(driver, test_steps);


					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				} else {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
				group.type_MailingAddrtess(driver, test, GuestFirstName, GuestLastName, PhoneNumber, Address1, City,
						State, Country, PostalCode, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Billinginfo(driver, test, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Save(driver, test, getTest_Steps);
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
			// Create RoomBlock

			try {

				group.navigateRoomBlock(driver, test);
				app_logs.info("Navigate to Room Block Tab");
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
				getTest_Steps = group.searchRoomsForBlock(driver, CheckInDate, CheckOutDate, RoomPerNight);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.SelectRatePlan(driver, RatePlanName);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.SelectAdults(driver, adult);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.SelectChilds(driver, children);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.ClickSearchGroup(driver);
				test_steps.addAll(getTest_Steps);

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
							ReservationRoomClasses, interval, baserate, adultCapicity, personCapicity,
							isAdditionalAdultChild, maxAdults, MaxPersons, adultsRate, childRate, isProRate,
							ratePerNight, adultPerNight, childPerNight, test_steps);

				} else {

					test_steps.add("No combination found");
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Click on block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to create reservation from group block", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
				test_steps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
				} else {

					Assert.assertTrue(false);
				}
			}
			String AccountNumber = null;
			// New account
			try {
				test_steps.add("****************** Creating account *********************");
				app_logs.info("****************** Creating account *********************");
				AccountName = AccountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, AccountType);
				CreateTA.AccountDetails(driver, AccountType, AccountName);
				AccountNumber = Utility.GenerateRandomString15Digit();
				CreateTA.ChangeAccountNumber(driver, AccountNumber);
				getTest_Steps.clear();
				getTest_Steps = CreateTA.AccountAttributes(driver, test, MarketSegment, Referral, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				getTest_Steps = CreateTA.Mailinginfo(driver, test, GuestFirstName, GuestLastName, PhoneNumber,
						PhoneNumber, Address1, Address2, Address3, Email, City, State, PostalCode, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				getTest_Steps = CreateTA.Billinginfo(driver, test, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();

				if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					CreateTA.ClickFolio(driver);
					CreateTA.navigateFolioOptions(driver);
					if (policyType.equalsIgnoreCase("No Show")) {
						CreateTA.selectNoShowPolicyForAccount(driver, test_steps, PoliciesName);
					} else if (policyType.equalsIgnoreCase("Check In")) {
						CreateTA.selectPolicyForAccount(driver, policyTypes, policyName, policyType,
								selectedpolicyNames, isAccountPolicyCreate);
					}
				}
				getTest_Steps = CreateTA.AccountSave(driver, test, AccountName, getTest_Steps);
				test_steps.addAll(getTest_Steps);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			String reservationNumber = null;

			try {

				test_steps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationRoomClasses, "Yes", "Account", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);
						Wait.wait5Second();

						try {
							rateListInReservation = reservationPage.intervalRateVerification(driver,
									ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
									children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
									personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0),
									maxPerson.get(0), adultRate.get(0), childRate1.get(0), isProStayRate.get(0),
									customRatePerNight.get(0), adultRatePerNight.get(0), childRatePerNight.get(0),
									folioName, test_steps);

							app_logs.info("rateListInReservation: " + rateListInReservation);

							for (int i = 0; i < rateListInReservation.size(); i++) {
								app_logs.info(rateListInReservation.get(i));
							}

							if (rateListInReservation.get(rateListInReservation.size() - 1).get(0)
									.equalsIgnoreCase("no")) {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}

							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);
							getPayAbleAmount = folio.splitStringBySign(getPayAbleAmount, "$");
							app_logs.info("getPayAbleAmount from room class: " + getPayAbleAmount);
							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);

							getRoomChargesFromTripSummary = folio.splitStringBySign(getRoomChargesFromTripSummary, "$");
							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);

							rateIs = getRoomChargesFromTripSummary;
							app_logs.info(rateIs);

							test_steps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							test_steps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");

								test_steps.add("Verified room charges in select room room class sectiona and in trip summary are matching");
							} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}
						} catch (Exception e) {
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							test_steps.add("bug id: " + bugId);

						}

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", GuestFirstName,
								GuestLastName);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, Email);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null,
								null);
						test_steps.addAll(getTest_Steps);

						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);
						app_logs.info("Reservation number" + reservation);

						getTest_Steps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						isReservationCreated = true;

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickCloseReservation(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						reservationPage.verifyStatusAfterReservation(driver, foundStatus);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.verifyAccountName(driver, AccountName, false);
						test_steps.addAll(getTest_Steps);

						reservationPage.verifyGuestInfoAssociatedAccount(driver, AccountName, true, test_steps);

						reservationPage.closeReservationTab(driver);
						test_steps.add("Close Reservation");
						app_logs.info("Close Reservation");

						reservationPage.Search_ResNumber_And_Click(driver, reservation);

						reservationPage.click_Folio(driver, test_steps);
						reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
						test_steps.add("======INTERVAL RATE PLAN VERIFICATION IN FOLIO LINE ITEM=====");
						String category = "Room Charge";
						ArrayList<String> intervalDates = rateListInReservation.get(1);
						ArrayList<String> folioRate = rateListInReservation.get(0);
						app_logs.info("folioRate: " + folioRate);
						app_logs.info("intervalDates: " + intervalDates);
						for (int i = 0; i < intervalDates.size(); i++) {

							String folioDates = Utility.parseDate(intervalDates.get(i),
									ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

							String date = folio.itemDate(driver, category, i + 1);
							app_logs.info("expecetd: " + folioDates);
							app_logs.info("found: " + date);

							// assertEquals(date, folioDates, "Failed: Interval date is
							// mismatching in folio line item!");

							String description = folio.itemDetailsDescroption(driver, category, i + 1);
							app_logs.info("expected description name: " + folioName);
							app_logs.info("found: " + description);

							// assertEquals(description, folioName, "Failed: Description
							// is mismatching in folio line item!");

							String getamount = folio.itemAmount(driver, category, i + 1);
							getamount = folio.splitStringBySign(getamount, "$");
							double dbAmount = Double.parseDouble(folioRate.get(i));
							String strAmount = String.format("%.2f", dbAmount);
							app_logs.info("expected amount: " + strAmount);
							app_logs.info("found: " + getamount);

							// assertEquals(getamount, strAmount, "Failed: Amount is
							// mismatching in folio line item!");
							test_steps.add(folioDates + "         " + folioName + "           " + strAmount);
							test_steps.add(date + "         " + description + "           " + getamount);

							if (!date.equalsIgnoreCase(folioDates)) {
								test_steps.add("Failed: Folio date is mismatching!");
								//break;
							}

							if (!description.equalsIgnoreCase(folioName)) {
								test_steps.add("Failed: Folio description is mismatching!");
								//break;

							}

							if (!getamount.equalsIgnoreCase(strAmount)) {
								test_steps.add("Failed: Folio amount is mismatching!");
								//break;

							}

							test_steps.add(folioDates + "     " + folioName + "        " + strAmount);
						}
						test_steps.add("Verified interval rate along with dates and description");
						reservationPage.ClickOnDetails(driver);
					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				} else {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (ReservationType.equalsIgnoreCase("Quote")) {
			try {
				isReservationCreated = false;
				test_steps.add("=================== CREATE QUOTE ======================");

				app_logs.info("=================== CREATE QUOTE ======================");
				reservationPage.click_NewReservation(driver, test_steps);
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								System.out.println("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								System.out.println("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);

						try {
							String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
									ReservationRoomClasses);

							getPayAbleAmount = folio.splitStringBySign(getPayAbleAmount, "$");

							app_logs.info("getPayAbleAmount from room class: " + getPayAbleAmount);
							String getRoomChargesFromTripSummary = reservationPage
									.getRoomChargesFromTripSummary(driver);

							getRoomChargesFromTripSummary = folio.splitStringBySign(getRoomChargesFromTripSummary, "$");
							app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);

							rateIs = getRoomChargesFromTripSummary;
							app_logs.info(rateIs);

							test_steps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
							test_steps.add("Room charges in room class section: " + getPayAbleAmount);

							if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
								app_logs.info("in if");
								test_steps.add("Verified room charges in select room room class sectiona and in trip summary are matching");

							} else {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}
							rateListInReservation = reservationPage.intervalRateVerification(driver,
									ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
									children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),

									personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0),
									maxPerson.get(0), adultRate.get(0), childRate1.get(0), isProStayRate.get(0),
									customRatePerNight.get(0), adultRatePerNight.get(0), childRatePerNight.get(0),
									folioName, test_steps);

							for (int i = 0; i < rateListInReservation.size(); i++) {
								app_logs.info(rateListInReservation.get(i));
							}

							if (rateListInReservation.get(rateListInReservation.size() - 1).get(0)
									.equalsIgnoreCase("no")) {
								app_logs.info("in else part of interval");
								String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
										+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
								test_steps.add("bug id: " + bugId);

							}
						} catch (Exception e) {
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							test_steps.add("bug id: " + bugId);

						}
						reservationPage.clickNext(driver, test_steps);

						reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName,
								GuestLastName, PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3,
								City, Country, State, PostalCode, IsGuesProfile);
						if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
							reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber,
									NameOnCard, CardExpDate);
						}
						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						reservationPage.clickSaveAsQuoteButton(driver);
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
						app_logs.info("Reservation number" + reservation);

						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						reservationPage.get_RoomNumber(driver, test_steps, "Yes");
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);

						String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

						String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
						foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
						String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
						foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
						Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);
						reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
						test_steps.add("======INTERVAL RATE PLAN VERIFICATION IN FOLIO LINE ITEM=====");
						String category = "Room Charge";
						ArrayList<String> intervalDates = rateListInReservation.get(1);
						ArrayList<String> folioRate = rateListInReservation.get(0);
						app_logs.info("folioRate: " + folioRate);
						app_logs.info("intervalDates: " + intervalDates);

						for (int i = 0; i < intervalDates.size(); i++) {

							String folioDates = Utility.parseDate(intervalDates.get(i),
									ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

							String date = folio.itemDate(driver, category, i + 1);
							app_logs.info("expecetd: " + folioDates);
							app_logs.info("found: " + date);

							// assertEquals(date, folioDates, "Failed: Interval date is
							// mismatching in folio line item!");

							String description = folio.itemDetailsDescroption(driver, category, i + 1);
							app_logs.info("expected description name: " + folioName);
							app_logs.info("found: " + description);

							// assertEquals(description, folioName, "Failed: Description
							// is mismatching in folio line item!");

							String getamount = folio.itemAmount(driver, category, i + 1);
							getamount = folio.splitStringBySign(getamount, "$");
							double dbAmount = Double.parseDouble(folioRate.get(i));
							String strAmount = String.format("%.2f", dbAmount);
							app_logs.info("expected amount: " + strAmount);
							app_logs.info("found: " + getamount);

							// assertEquals(getamount, strAmount, "Failed: Amount is
							// mismatching in folio line item!");
							test_steps.add(folioDates + "         " + folioName + "           " + strAmount);
							test_steps.add(date + "         " + description + "           " + getamount);


							if (!date.equalsIgnoreCase(folioDates)) {
								test_steps.add("Failed: Folio date is mismatching!");
								//break;
							}

							if (!description.equalsIgnoreCase(folioName)) {
								test_steps.add("Failed: Folio description is mismatching!");
								//break;

							}

							if (!getamount.equalsIgnoreCase(strAmount)) {
								test_steps.add("Failed: Folio amount is mismatching!");
							//	break;

							}

							test_steps.add(folioDates + "     " + folioName + "        " + strAmount);
						}
						test_steps.add("Verified interval rate along with dates and description");

						test_steps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							test_steps.add("Rate plan folio name is matched : " + RatePlanName);
							app_logs.info("Rate plan folio name is matched : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, test_steps);
						reservationPage.clickBookQuote(driver, test_steps);
						Wait.wait10Second();
					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				} else {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}
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
		} else if (ReservationType.equalsIgnoreCase("tapechart")) {
			try {

				Tapechart tapechart = new Tapechart();
				test_steps.add("=================== Create Reservation from Tape Chart ======================");
				app_logs.info("=================== Create Reservation from Tape Chart ======================");
				navigation.navigateTapeChart(driver, test);
				test_steps.add("Navigate TapeChart");
				app_logs.info("Navigate TapeChart");
				app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
				test_steps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");

				if (PromoCode.isEmpty()) {
					tapechart.searchInTapechart(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName,
							PromoCode);
				} else {
					tapechart.searchInTapechart(driver,test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName,
							PromoCode);
				}

				String minStayColor = "";
				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					app_logs.info("==========SELECT ROOM==========");
					test_steps.add("==========SELECT ROOM==========");

					tapechart.clickAvailableSlotWithRatePalnValidation(driver, RoomAbbri.get(0), isMinStayRule,
							isMinStayRuleBrokenPopComeOrNot, minStayRuleValue, checkInColor, checkOutColor);
					test_steps.add("Click available room of Room Class '" + RoomAbbri.get(0) + "'");
					app_logs.info("Click on available room");

					test_steps.add("New Reservation page is opened");
					app_logs.info("New Reservation Page is Opened");

					reservationPage.clickOnEditReservation(driver);
					test_steps.add("Click on edit reservation in trip summary");

					try {

						rateListInReservation = reservationPage.intervalRateVerification(driver,
								ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult,
								children, ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),

								personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
								adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
								adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

						for (int i = 0; i < rateListInReservation.size(); i++) {
							app_logs.info(rateListInReservation.get(i));
						}

						if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							test_steps.add("bug id: " + bugId);

						}

						String getPayAbleAmount = reservationPage.getRoomChargesFromRoomClass(driver,
								ReservationRoomClasses);
						getPayAbleAmount = folio.splitStringBySign(getPayAbleAmount, "$");
						app_logs.info("getPayAbleAmount: " + getPayAbleAmount);
						
						String getRoomChargesFromTripSummary = reservationPage.getRoomChargesFromTripSummary(driver);
						getRoomChargesFromTripSummary = folio.splitStringBySign(getRoomChargesFromTripSummary, "$");

						app_logs.info("getRoomChargesFromRates: " + getRoomChargesFromTripSummary);
						app_logs.info("getRoomChargesFromTripSummary: " + getRoomChargesFromTripSummary);
						rateIs = getRoomChargesFromTripSummary;

						test_steps.add("Room charges in trip summary: " + getRoomChargesFromTripSummary);
						test_steps.add("Room charges in room class section: " + getPayAbleAmount);

						if (getPayAbleAmount.equals(getRoomChargesFromTripSummary)) {
							app_logs.info("in if");

							test_steps.add("Verified room charges in select room room class sectiona and in trip summary are matching");

						} else {
							app_logs.info("in else part of interval");
							String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
									+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
							test_steps.add("bug id: " + bugId);

						}

					} catch (Exception e) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.clickNext(driver, test_steps);

					reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
							PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3, City, Country, State,
							PostalCode, IsGuesProfile);
					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					}
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment,
							Referral);

					reservationPage.clickBookNow(driver, test_steps);
					reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					app_logs.info("Reservation number" + reservation);

					isReservationCreated = true;
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					reservationPage.get_RoomNumber(driver, test_steps, "Yes");
					String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
							test_steps);

					String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

					String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
					foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
					String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
					foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
					Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

					test_steps.add("=================== Verify Rate Plan In Reservation ======================");
					app_logs.info("=================== Verify Rate Plan In Reservation ======================");
					String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, RatePlanName);

					try {
						assertTrue(ratePlan.trim().equalsIgnoreCase(RatePlanName),
								"Rate plan is not matched for room class");
						test_steps.add("Rate plan is matched for room class for : " + RatePlanName);
						app_logs.info("Rate plan is matched for room class for : " + RatePlanName);
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}

					reservationPage.click_Folio(driver, test_steps);
					String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);
					reservationPage.includeTaxesinLineItems(driver, test_steps, false);

					String category = "Room Charge";
					ArrayList<String> intervalDates = rateListInReservation.get(1);
					ArrayList<String> folioRate = rateListInReservation.get(0);
					app_logs.info("folioRate: " + folioRate);
					app_logs.info("intervalDates: " + intervalDates);

					for (int i = 0; i < intervalDates.size(); i++) {

						String folioDates = Utility.parseDate(intervalDates.get(i),
								ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

						String date = folio.itemDate(driver, category, i + 1);
						app_logs.info("expecetd: " + folioDates);
						app_logs.info("found: " + date);

						// assertEquals(date, folioDates, "Failed: Interval date is
						// mismatching in folio line item!");

						String description = folio.itemDetailsDescroption(driver, category, i + 1);
						app_logs.info("expected description name: " + folioName);
						app_logs.info("found: " + description);

						// assertEquals(description, folioName, "Failed: Description
						// is mismatching in folio line item!");

						String getamount = folio.itemAmount(driver, category, i + 1);
						getamount = folio.splitStringBySign(getamount, "$");
						double dbAmount = Double.parseDouble(folioRate.get(i));
						String strAmount = String.format("%.2f", dbAmount);
						app_logs.info("expected amount: " + strAmount);
						app_logs.info("found: " + getamount);

						// assertEquals(getamount, strAmount, "Failed: Amount is
						// mismatching in folio line item!");
						test_steps.add(folioDates + "         " + folioName + "           " + strAmount);
						test_steps.add(date + "         " + description + "           " + getamount);

						if (!date.equalsIgnoreCase(folioDates)) {
							test_steps.add("Failed: Folio date is mismatching!");
							//break;
						}

						if (!description.equalsIgnoreCase(folioName)) {
							test_steps.add("Failed: Folio description is mismatching!");
							//break;

						}

						if (!getamount.equalsIgnoreCase(strAmount)) {
							test_steps.add("Failed: Folio amount is mismatching!");
							//break;

						}

						test_steps.add(folioDates + "     " + folioName + "        " + strAmount);
					}
					test_steps.add("Verified interval rate along with dates and description");
					
					reservationPage.click_DeatilsTab(driver, test_steps);
				} else {
					tapechart.verifyNoResultsmatchedInTapechart(driver, test_steps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (isReservationCreated && !ActionOnReservation.equals("No")) {
//			getSessionLevelPolicy.put("Deposit", "deposit tc");
//			getSessionLevelPolicy.put("No Show", "Nagi Policy");
//			getSessionLevelPolicy.put("Check-in", "last check-in");
//			getSessionLevelPolicy.put("Cancellation", "manual cancel policy");
//			
			

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
				getSessionLevelPolicy.put("Deposit", "NA");
				String seasonDepositpolicy = getSessionLevelPolicy.get("Deposit");

				app_logs.info("seasonDepositpolicy : " + seasonDepositpolicy);
				app_logs.info("reervationDepositPolicy : " + reervationDepositPolicy);
				String fourDigitCardNo = Utility.getCardNumberHidden(CardNumber);
				if (seasonDepositpolicy.equalsIgnoreCase("NA")) {
					seasonDepositpolicy = "No Policy";
				}
				test_steps.add("=================== Verify the deposit policy in reservation ======================");
				app_logs.info("=================== Verify the deposit policy in reservation ======================");
				assertTrue(seasonDepositpolicy.equalsIgnoreCase(reervationDepositPolicy),
						"Reservation deposit policy is not matched");
				test_steps.add("Verified Reservation deposit policy is reservation policies : " + seasonDepositpolicy);
				app_logs.info("Verified Reservation deposit policy is reservation policies : " + seasonDepositpolicy);

				reservationPage.closeReservationTab(driver);
				if (!reervationDepositPolicy.equalsIgnoreCase("No Policy")) {
					navigation.inventory(driver);

					navigation.policies(driver, test_steps);

					if (CheckInDate.split("\\|").length > 1) {
						for (String str : seasonDepositPolicy) {
							getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps,
									ratesConfig.getProperty("depositPolicyText"), str));
						}
					} else {
						getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps,
								ratesConfig.getProperty("depositPolicyText"), seasonDepositpolicy));
					}

					app_logs.info("getdepositPoliciesData: " + getdepositPoliciesData);
					getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps,
							ratesConfig.getProperty("depositPolicyText"), reervationDepositPolicy));

					reservationPage.navigateToReservationPage(driver);
				}
				driver.navigate().refresh();
				app_logs.info("reservation number: "+reservation);
				reservationPage.Search_ResNumber_And_Click(driver, reservation);
				reservationPage.clickFolio(driver, test_steps);
				HashMap<String, String> roomChargesAre = new HashMap<String, String>();
				ArrayList<String> roomCharges = new ArrayList<>();

				String[] chkin = CheckInDate.split("\\|");
				String[] chkout = CheckOutDate.split("\\|");
				String[] reservationSplit = ReservationRoomClasses.split("\\" + Utility.DELIM);

				test_steps.add("==================Verify Deposit Policy On Reservation==================");

				roomChargesAre = reservationPage.getRoomChargesFromFolioBasedOnDates1(driver, test_steps, chkin[0],
						chkout[0], true, Integer.parseInt(interval), isProStayRate.get(0));
				for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
					roomCharges.add(entry.getValue());
				}
				if (GuestFirstName.split("\\|").length > 1) {
					reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
					reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(1), Rooms.get(1));
					roomChargesAre = reservationPage.getRoomChargesFromFolioBasedOnDates1(driver, test_steps, chkin[1],
							chkout[1], true, Integer.parseInt(interval), isProStayRate.get(1));
					for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
						roomCharges.add(entry.getValue());
					}
					reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
					reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(0), Rooms.get(0));
				}
				app_logs.info(roomCharges);

				if (isAccountPolicyApplicable.equals("Yes")) {
					if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
						depositAmount = reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
								policyAttr1Is[0], policyAttrValueIs[0]);
					} else if (isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames
							.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText")))) {
						depositAmount = reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
								getAccountPoliciesData.get(0).get("Type"),
								getAccountPoliciesData.get(0).get("AttrValue"));
					}

				} else if (!seasonDepositpolicy.equalsIgnoreCase("No Policy")) {
					if (CheckInDate.split("\\|").length > 1) {
						for (int i = 0; i < seasonDepositPolicy.size(); i++) {
							String size = String.valueOf(ReservationRoomClasses.split("\\|").length);
							getdepositAmount.put(seasonDepositPolicy.get(i),
									reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharges,
											getdepositPoliciesData.get(i).get("Type"),
											getdepositPoliciesData.get(i).get("AttrValue"), size));
						}
						app_logs.info("getdepositAmount: " + getdepositAmount.toString());
						ArrayList<Double> dbr = new ArrayList<Double>();
						for (Map.Entry<String, String> entry : getdepositAmount.entrySet()) {
							app_logs.info("entry: " + entry.getValue());
							dbr.add(Double.valueOf(entry.getValue()));
						}
						app_logs.info("dbr: " + dbr);
						DecimalFormat df = new DecimalFormat("0.00");
						df.setMaximumFractionDigits(2);
						depositAmount = df.format(Collections.max(dbr));
						app_logs.info(depositAmount);
						depositPolicyApplied = Utility.getKeyfromHashmap(getdepositAmount, depositAmount);

					} else {
						depositAmount = reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
								getdepositPoliciesData.get(0).get("Type"),
								getdepositPoliciesData.get(0).get("AttrValue"));
						// depositPolicyApplied=seasonDepositPolicy.get(0);
					}

				}
				app_logs.info(depositPolicyApplied);
				app_logs.info(depositAmount);
				String paymentTypeIs = "";
				if (PaymentType.equals("MC")) {
					paymentTypeIs = "" + PaymentType + " " + fourDigitCardNo + " (" + CardExpDate + ")";
				} else if (PaymentType.equals("Cash")) {
					paymentTypeIs = PaymentType;
				}
				app_logs.info("paymentTypeIs: " + paymentTypeIs);
				if (isAccountPolicyApplicable.equals("Yes")) {
					if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
						verificationDepositWithPolicy(PaymentType, policyNames.get(0), depositAmount, paymentTypeIs);
					} else if (isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames
							.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText")))) {
						verificationDepositWithPolicy(PaymentType, selectedpolicyNames.get(policyTypes.get(0)),
								depositAmount, paymentTypeIs);
					} else {
						verificationDepoistWithoutPolicy(PaymentType, CheckInDate,
								ratesConfig.getProperty("noPolicyText"));
					}
				} else {

					app_logs.info("in else deposit: " + Utility.isEmptyStringArrayList(seasonDepositPolicy));
					if (Utility.isEmptyStringArrayList(seasonDepositPolicy)) {
						verificationDepositWithPolicy(PaymentType, reervationDepositPolicy, depositAmount,
								paymentTypeIs);
					} else {
						// remove
						// verificationDepoistWithoutPolicy(PaymentType,
						// Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")),
						// ratesConfig.getProperty("noPolicyText"));
					}
				}

				if (ActionOnReservation.equalsIgnoreCase("No Show")) {
					String rateNoShowPlanPolicy = getRateLevelPolicy.get("No Show");
					String seasonNoShowpolicy = getSessionLevelPolicy.get("No Show");

					if (seasonNoShowpolicy.equalsIgnoreCase("NA")) {
						seasonNoShowpolicy = "No Policy";
					}
					String policyToValidate = "No Policy", policyAttrDisplayed, policyAttrValueDisplayed,
							policyDesc = null, paymentsFromFolio = null;

					HashMap<String, String> highestAmountOfPolicyDetails = new HashMap<>();
					HashMap<String, ArrayList<String>> allPoliciesDetails = new HashMap<>();
					HashMap<String, String> allChargesFromFolio = new HashMap<>();
					ArrayList<String> noShowAmounts = new ArrayList<>();
					String noShowAmount = "";

					if (!seasonNoShowpolicy.equalsIgnoreCase("No Policy")) {
						reservationPage.close_FirstOpenedReservation(driver, test_steps);
						navigation.Inventory(driver);
						navigation.clickPoliciesAfterRateGridTab(driver, test_steps);

						ArrayList<String> checkInDates = new ArrayList<>();
						ArrayList<String> checkOutDates = new ArrayList<>();

						try {
							test_steps
									.add("========== Capturing all policies attribute and attribute values ==========");
							allPoliciesDetails = policy.getAllPoliciesDetails(driver, test_steps, "No Show",
									seasonNoShowpolicy);
						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
								Utility.updateReport(e, "Failed to capture no show policy attributes and values",
										testName, test_catagory, driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.updateReport(e, "Failed to capture no show policy attributes and values",
										testName, test_catagory, driver);
							} else {
								Assert.assertTrue(false);
							}
						}
						reservationPage.navigateToReservationPage(driver);
						reservationPage.Search_ResNumber_And_Click(driver, reservation);
						int resCount = GuestFirstName.split("\\|").length;

						if (!(Utility.validateInput(CheckInDate))) {
							for (int i = 0; i < resCount; i++) {
								checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
								checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy",
										"dd/MM/yyyy"));
							}
						} else {
							checkInDates = Utility.splitInputData(CheckInDate);
							checkOutDates = Utility.splitInputData(CheckOutDate);
						}
						try {
							test_steps
									.add("========== Evaluating which policy to be applied on reservation ==========");
							if (isAccountPolicyApplicable.equalsIgnoreCase("Yes")) {
								policyToValidate = policyName;
								policyAttrDisplayed = FeesGuestMustPay;
								policyAttrValueDisplayed = PercentOfFee;
								policyDesc = descWhileCreatePolicy.get(policyName).get(0);
								// test_steps.add("Season level policy should be
								// applied for check-in date
								// as<b>"+CheckInDate+"</b>");

							} else if (!(allPoliciesDetails.get("Names").isEmpty())) {

								reservationPage.click_Folio(driver, test_steps);

								highestAmountOfPolicyDetails = reservationPage.getHighestAmountOfPolicyForInterval(
										driver, test_steps, allPoliciesDetails, checkInDates, checkOutDates, true,
										Integer.parseInt(interval), isProStayRate);
								policyToValidate = highestAmountOfPolicyDetails.get("Name");
								Utility.app_logs.info("policyToValidate : " + policyToValidate);
								policyAttrDisplayed = highestAmountOfPolicyDetails.get("Attribute");
								policyAttrValueDisplayed = highestAmountOfPolicyDetails.get("AttributeValue");
								policyDesc = highestAmountOfPolicyDetails.get("Description");
								paymentsFromFolio = highestAmountOfPolicyDetails.get("Payments");
								test_steps.add(
										"========== Calculating no show amounts for different guests based on rate plan ==========");
								if (!ReservationType.equalsIgnoreCase("Account")) {
									noShowAmounts = reservationPage.getNoShowPaymentsForAllGuestsForInterval(driver,
											test_steps, checkInDates, checkOutDates, policyAttrDisplayed,
											policyAttrValueDisplayed, true, Integer.parseInt(interval), isProStayRate);
									noShowAmount = reservationPage.getTotalOfNoShowAmounts(noShowAmounts);

								}

							}
						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
								Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation",
										testName, test_catagory, driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation",
										testName, test_catagory, driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}

					test_steps
							.add("=================== Verify the no show policy in reservation ======================");
					app_logs.info(
							"=================== Verify the no show policy in reservation ======================");
					if (!ReservationType.equalsIgnoreCase("Account")) {

						Utility.app_logs.info(policyToValidate);
						Utility.app_logs.info(reervationNoShowPolicy);
						assertTrue(policyToValidate.equalsIgnoreCase(reervationNoShowPolicy),
								"No Show policy is not matched");
						test_steps.add("Verified no show policy is reservation policies : " + policyToValidate);
						app_logs.info("Verified no show policy is reservation policies : " + policyToValidate);

						test_steps.add("=================== No Show reservation ======================");
						app_logs.info("=================== No Show reservation ======================");

						reservationPage.makeReservationNoShowWithPaymentProcess(driver, test_steps, "No Show",
								paymentsFromFolio, noShowAmount);

						try {
							if (Utility.validateString(policyToValidate)) {
								test_steps.add(
										"===================== Verifying associated No Show policy details at Policies "
												+ "And Disclaimers tab =====================");
								reservationPage.verifyNoShowPolicy(driver, test_steps, policyToValidate, policyDesc);
							} else {
								test_steps.add(
										"===================== Verifying associated No policy appllied for No Show at Policies "
												+ "And Disclaimers tab =====================");
								reservationPage.verifyNoShowPolicy(driver, test_steps, "No Policy", "");
							}
							test_steps.add(
									"===================== Verifying No show amount at folio =====================");
							reservationPage.click_Folio(driver, test_steps);
							if (Utility.validateString(policyToValidate)) {
								reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, noShowAmounts);
							} else {
								noShowAmounts.clear();
								reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, noShowAmounts);
							}

						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
								Utility.updateReport(e, "Failed to make reservation as no show", testName,
										test_catagory, driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.updateReport(e, "Failed to make reservation as no show", testName,
										test_catagory, driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}
				} else if (ActionOnReservation.equalsIgnoreCase("Check In")) {
					Double paymentAmt = 0.00;
					String checkInAmount = "";
					String paymentAmount = "";
					String checkinPolicyApplied = "";
					Double balance = 0.00;
					HashMap<String, String> checkinPoliciesData = new HashMap<String, String>();
					ArrayList<HashMap<String, String>> getcheckinPoliciesData = new ArrayList<HashMap<String, String>>();
					String ratePlanCheckInPolicy = getRateLevelPolicy.get("Check-in");
					String seasonCheckInpolicy = getSessionLevelPolicy.get("Check-in");
					if (seasonCheckInpolicy.equalsIgnoreCase("NA")) {
						seasonCheckInpolicy = "No Policy";
					}
					app_logs.info(seasonCheckInpolicy);
					app_logs.info(reervationCheckInPolicy);
					test_steps.add(
							"=================== Verify the check in policy in reservation ======================");
					app_logs.info(
							"=================== Verify the check in policy in reservation ======================");
					assertTrue(seasonCheckInpolicy.equalsIgnoreCase(reervationCheckInPolicy),
							"Reservation check in policy is not matched");
					test_steps.add("Verified Reservation check in policy is reservation policies : "
							+ reervationCheckInPolicy);
					app_logs.info("Verified Reservation check in policy is reservation policies : "
							+ reervationCheckInPolicy);

					if (!seasonCheckInpolicy.equalsIgnoreCase("No Policy")) {
						navigation.inventory(driver);
						navigation.policies(driver, test_steps);

						test_steps.add(
								"=========Get  Data from Check-In Policy if policy exist on Season level=========");
						app_logs.info(
								"=========Get  Data from Check-In Policy if policy exist on Season level=========");
						navigation.clickPoliciesAfterRateGridTab(driver, test_steps);
						Wait.waitUntilPageLoadNotCompleted(driver, 5);
						HashMap<String, String> valueHashmap = new HashMap<String, String>();
						if (CheckInDate.split("\\|").length > 1) {
							for (int i = 0; i < seasonCheckInPolicy.size(); i++) {
								getcheckinPoliciesData.add(policy.getpoliciesData(driver, test_steps,
										ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(i)));
								valueHashmap.put(seasonCheckInPolicy.get(i),
										getcheckinPoliciesData.get(i).get("AttrValue"));
							}
							String value = null;
							value = Utility.getMaxValuefromHashmap(valueHashmap);
							checkinPolicyApplied = Utility.getKeyfromHashmap(valueHashmap, value);
							checkinPoliciesData.put("AttrValue", value);
							app_logs.info(checkinPoliciesData);
						} else {
							checkinPoliciesData = policy.getpoliciesData(driver, test_steps,
									ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(0));
							checkinPolicyApplied = seasonCheckInPolicy.get(0);

						}
						//need to discuss with haider
						app_logs.info(getcheckinPoliciesData);
						reservationPage.navigateToReservationPage(driver);
						reservationPage.Search_ResNumber_And_Click(driver, reservation);

						reservationPage.clickFolio(driver, test_steps);
						balance = Double.parseDouble(reservationPage.get_Balance(driver, test_steps));
						if (CheckInDate.split("\\|").length > 1) {
							reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
							reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(1),
									Rooms.get(1));
							balance = balance + Double.parseDouble(reservationPage.get_Balance(driver, test_steps));
							reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
							reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(0),
									Rooms.get(0));
						}
						app_logs.info(balance);
						if (isAccountPolicyApplicable.equals("Yes")) {

							paymentAmt = Double
									.parseDouble(reservationPage.get_PaymentsForAccountReservation(driver, test_steps));
							app_logs.info(paymentAmt);
							if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
								checkInAmount = reservationPage.calculationOfCheckInAmountToBePaidForRateV2(
										String.valueOf(balance), policyAttrValueIs[1]);
							} else if (isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames
									.get(policyTypes.get(1)).equals(ratesConfig.getProperty("noPolicyText")))) {
								checkInAmount = reservationPage.calculationOfCheckInAmountToBePaidForRateV2(
										String.valueOf(balance), getAccountPoliciesData.get(1).get("AttrValue"));
							}

						} else if (isAccountPolicyApplicable.equals("No")
								&& Utility.isEmptyStringArrayList(seasonCheckInPolicy)) {
							paymentAmt = Double
									.parseDouble(reservationPage.get_PaymentsForAccountReservation(driver, test_steps));
							app_logs.info(paymentAmt);
							checkInAmount = reservationPage.calculationOfCheckInAmountToBePaidForRateV2(
									String.valueOf(balance), checkinPoliciesData.get("AttrValue"));
						} else if (Utility.isEmptyStringArrayList(seasonCheckInPolicy)) {
							paymentAmt = Double.parseDouble(reservationPage.get_Payments(driver, test_steps));
							app_logs.info(paymentAmt);
							checkInAmount = reservationPage.calculationOfCheckInAmountToBePaidForRateV2(
									String.valueOf(balance), checkinPoliciesData.get("AttrValue"));

						}
						if (Utility.validateString(checkInAmount)) {
							app_logs.info(checkInAmount);
							DecimalFormat df = new DecimalFormat("0.00");
							df.setMaximumFractionDigits(2);
							paymentAmt = paymentAmt + Double.valueOf(checkInAmount);
							app_logs.info(df.format(paymentAmt));
							paymentAmount = df.format(paymentAmt);
							app_logs.info(paymentAmount);
						}
					}
					String checkInCardFormat = "";
					if (PaymentType.equals("MC")) {
						checkInCardFormat = "" + PaymentType + "-" + fourDigitCardNo + " " + CardExpDate + "";
					} else if (PaymentType.equals("Cash")) {
						checkInCardFormat = PaymentType;
					}
					reservationPage.ClickOnDetails(driver);
					reservationPage.clickCheckInButton(driver, test_steps);
					Wait.waitUntilPageLoadNotCompleted(driver, 10);
					reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));

					if (isAccountPolicyApplicable.equals("Yes")) {

						if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
							completeCheckInIfPolicyExist(balance, checkInAmount);
							test_steps.add("==================Verify Check-In Policy On Reservation==================");
							verificationCheckinWithPolicy(PaymentType, policyNames.get(1), checkInAmount, paymentAmount,
									checkInCardFormat);
						} else if (isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames
								.get(policyTypes.get(1)).equals(ratesConfig.getProperty("noPolicyText")))) {
							completeCheckInIfPolicyExist(balance, checkInAmount);
							verificationCheckinWithPolicy(PaymentType, selectedpolicyNames.get(policyTypes.get(1)),
									checkInAmount, paymentAmount, checkInCardFormat);
						} else {
							completeCheckInIfPolicyDoesntExist(balance);
							test_steps.add("==================Verify Check-In Policy On Reservation==================");
							verificationCheckinWithoutPolicy(ratesConfig.getProperty("noPolicyText"));
						}

					} else {
						if (Utility.isEmptyStringArrayList(seasonCheckInPolicy)) {
							completeCheckInIfPolicyExist(balance, checkInAmount);
							test_steps.add("==================Verify Check-In Policy On Reservation==================");
							verificationCheckinWithPolicy(PaymentType, checkinPolicyApplied, checkInAmount,
									paymentAmount, checkInCardFormat);
						} else {
							completeCheckInIfPolicyDoesntExist(balance);
							test_steps.add("==================Verify Check-In Policy On Reservation==================");
							verificationCheckinWithoutPolicy(ratesConfig.getProperty("noPolicyText"));
						}
					}
				}

				else if (ActionOnReservation.equalsIgnoreCase("Cancellation")) {
					
					String cancellationPolicy = "";
					String cancellationDelim = ",";
					if (isVerifyPolicies) {

						boolean delimFlag = false;

						cancellationPolicy = getSessionLevelPolicy.get("Cancellation");

						if (isAccountPolicyApplicable.equals("Yes")) {
							// to-do
						}

						if (!cancellationPolicy.equalsIgnoreCase("NA")) {

							test_steps.add(
									"==================VERIFY CANCELLATION POLICY ON RESERVATION==================");

							reservationPage.close_FirstOpenedReservation(driver, test_steps);
							navigation.inventory(driver);
							navigation.policies(driver, test_steps);

							PolicyTypes += "Cancellation";
							PolicyNames += cancellationPolicy;
							policy.clickEditIcon(driver, "Cancellation", cancellationPolicy, test_steps);
							int noOfClauses = policy.getNoOfClauses(driver);
							TypeOfFees += policy.getSelectedTypeOfFees(driver, cancellationDelim, noOfClauses,
									test_steps);
							GuestsWillIncurAFee += policy.getGuestWillIncurAFee(driver, cancellationDelim, noOfClauses,
									test_steps);
							ChargesType += policy.getSelectedChargeType(driver, cancellationDelim, TypeOfFees,
									test_steps);
							NoOfDays += policy.getNoOfDays(driver, cancellationDelim, noOfClauses, test_steps);
							CancelWithInType += policy.getSelectedCancelType(driver, cancellationDelim, noOfClauses,
									test_steps);
							policy.closePolicyPopup(driver, test_steps);
							app_logs.info("Policies Types : " + PolicyTypes);
							app_logs.info("Type Of Fees : " + TypeOfFees);
							app_logs.info("Guests Will Incur A fee : " + GuestsWillIncurAFee);
							app_logs.info("Charge Type : " + ChargesType);
							app_logs.info("No Of Days : " + NoOfDays);
							app_logs.info("Cancel With In Type : " + CancelWithInType);
							// converting to hashmap
							names = policy.getPolicyNames(delim, PolicyTypes, PolicyNames);
							policyClauses = policy.getPolicyClauses(delim, PolicyTypes, TypeOfFees);
							guestsWillIncurAFee = policy.getPolicyClauses(delim, PolicyTypes, GuestsWillIncurAFee);
							if (TypeOfFees.toLowerCase().contains("percent of stay")) {
								chargesType = policy.getPolicyClauses(delim, PolicyTypes, ChargesType);
							} else {
								chargesType.put("Cancellation", "");
							}
							cancelWithInType = policy.getPolicyClauses(delim, PolicyTypes, CancelWithInType);
							noOfDays = policy.getPolicyClauses(delim, PolicyTypes, NoOfDays);
							delimFlag = true;
						} else {
							delimFlag = false;
						}

						app_logs.info("Type Of Fees : " + policyClauses);
						app_logs.info("Guests Will Incur A fee : " + guestsWillIncurAFee);
						app_logs.info("Charge Type : " + chargesType);
						app_logs.info("No Of Days : " + noOfDays);
						app_logs.info("Cancel With In Type : " + cancelWithInType);
					}

					try {
						navigation.cpReservation_Backward(driver);
					} catch (Exception e) {
						Actions actions = new Actions(driver);

						actions.sendKeys(Keys.ENTER);
					}
					reservationPage.Search_ResNumber_And_Click(driver, reservation);

					String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);
					String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
					foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
					String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
					foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
					Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

					if (isVerifyPolicies) {
						if (!cancellationPolicy.equalsIgnoreCase("NA")) {
							foundBestPolicyAmount = policy.findBestCancellationPolicy(",",
									policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
									chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"),
									calRoomCharge + "", foundTotalCharge);

							app_logs.info("Best Clause : " + foundBestPolicyAmount);
							app_logs.info("Clauses : " + policyClauses.get("Cancellation"));
							app_logs.info("Guest will incur a fee : " + guestsWillIncurAFee.get("Cancellation"));
							HashMap<String, String> cancellationClauseValues = reservationPage
									.getCancellationClauseValue(",", policyClauses.get("Cancellation"),
											guestsWillIncurAFee.get("Cancellation"));
							app_logs.info(cancellationClauseValues);
							reservationPage.verifyAssociatedCancellationPolicy(driver, ",", PolicyTypes, PolicyNames,
									Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), ","),
									cancellationClauseValues, test_steps);
						}
					}

					reservationPage.click_Folio(driver, test_steps);
					reservationPage.includeTaxesinLineItems(driver, test_steps, false);

					HashMap<String, ArrayList<String>> roomClassWiseDates = reservationPage
							.getRoomClassFolioWiseRoomChargeDates(driver, test_steps, reservationRoomClassesList,
									RoomAbbri, "Yes", Rooms, "dd/MM/yyyy");
					HashMap<String, HashMap<String, String>> roomClassWiseRoomCharge = reservationPage
							.getRoomClassWiseFolioRoomChargeBasedOnDates(driver, reservationRoomClassesList,
									roomClassWiseDates, "dd/MM/yyyy", RoomAbbri, "Yes", Rooms);

					app_logs.info(roomClassWiseDates);
					app_logs.info(roomClassWiseRoomCharge);

					reservationPage.click_DeatilsTab(driver, test_steps);

					String tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver,
							test_steps);
					String paidAmount = reservationPage.get_TripSummaryPaidAmount(driver, test_steps);
					app_logs.info("paidAmount: " + paidAmount);

					reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
					reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, names.get("Cancellation"),
							"Cancellation");
					reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");

					String[] calCancelPolicy;

					if (foundBestPolicyAmount.get(0).equalsIgnoreCase("number of nights")) {

						float foundRoomCharges = 0;
						for (String foundRoomClass : reservationRoomClassesList) {
							String firstDate = roomClassWiseDates.get(foundRoomClass).get(0);
							foundRoomCharges += Float
									.parseFloat(roomClassWiseRoomCharge.get(foundRoomClass).get(firstDate));
						}
						calCancelPolicy = reservationPage.calculateCancellationFee(paidAmount, foundRoomCharges + "");

					} else {
						calCancelPolicy = reservationPage.calculateCancellationFee(paidAmount,
								foundBestPolicyAmount.get(1));
					}

					app_logs.info("calCancelPolicy[1]: " + calCancelPolicy[1]);
					app_logs.info("calCancelPolicy[0]: " + calCancelPolicy[0]);

					reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", calCancelPolicy[1],
							calCancelPolicy[0]);
					reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);

					reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", calCancelPolicy[1],
							calCancelPolicy[0], "Processed");
					reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);

					reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

					tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
					assertEquals(Float.parseFloat(tripTotal), Float.parseFloat(foundBestPolicyAmount.get(1)),
							"Failed to Verify Trip Total After Reservation");
					test_steps.add("Successfully Verified Trip Total After Reservation : " + tripTotal);

					paidAmount = reservationPage.get_TripSummaryPaidAmount(driver, test_steps);
					assertEquals(paidAmount, tripTotal, "Failed to Verify Paid Amount After Reservation");
					test_steps.add("Successfully Verified Paid Amount After Reservation : " + tripTotal);

				}

				else if (ActionOnReservation.equalsIgnoreCase("Change Stay Details: recalculate and no rate change")) {
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

					changeStaydetails(RatePlanName, CheckInDate, CheckOutDate, ReservationChangeClass, channelName,
							ReservationRoomClasses, ReservationType, adult, children, PromoCode, interval);
									
				}
				else if (ActionOnReservation.equalsIgnoreCase("Change Stay Details: Add and Remove Dates")) {
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						ChangeStayDetailsForAddAndRemoveDates(RatePlanName, CheckInDate, CheckOutDate, ReservationChangeClass, channelName,
								ReservationRoomClasses, ReservationType, adult, children, PromoCode, interval);				
				}


				// here end reservation
				else {

					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}

				// end here is created try
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to perform action", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to perform action", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
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
		}

	}

	public void changeStaydetails(String RatePlanName, String CheckInDate, String CheckOutDate,
			String ReservationChangeClass, String channelName, String ReservationRoomClasses, String ReservationType,
			String adult, String children, String PromoCode, String interval) throws Exception {

		
		// edit base rate
		try {
			
			test_steps.clear();
			testName = "Change stay details-Recalculate, rate update at season level";
			test_description = "Change stay details-Recalculate, rate update at season level and verify in recalculate without change"
					+ "checkin, checkout, adults and children";

			ArrayList<String> folioRate = rateListInReservation.get(0);
			ArrayList<String> intervalDates = rateListInReservation.get(1);
			ArrayList<String> oldRates1 = new ArrayList<>();
			app_logs.info("folioRate: " + folioRate);
			app_logs.info("intervalDates: " + intervalDates);
			String category = "Room Charge";
			int numberOfDys = 0;
			// reservationPage.closeReservationTab(driver);
			app_logs.info("close reservation tabe");

			String baserate = "";
			String adultCapicity = "";
			String personCapicity = "";
			boolean isAdditionalAdultChild = false;
			String maxAdults = "";
			String max_person = "";
			String adultsRate = "";
			String childRate = "";
			boolean isProRate = false;
			String ratePerNight = "";
			String adultPerNight = "";
			String childPerNight = "";
			String path = "";

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			// need to update taking too much time
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			try {
				rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			} catch (Exception e) {
				test_steps.add("Failed: No channel is found");
			}

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;
			app_logs.info("get min stay rule in : " + minruleChange);
			app_logs.info("get min stay rule size : " + minruleChange.size());
			app_logs.info("get min stay rule highest value : " + minruleChange.get(minruleChange.size() - 1));

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {
				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);

			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckInDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			boolean isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckInDate);
			// change rate value
			if (isSeasonExistChange) {
				nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				// here get interval info and update rate value
				double newRate = Double.parseDouble(oldbaseRate.get(0));
				newRate = newRate + 50;
				double newChildRate = Double.parseDouble(oldchildRate1.get(0));
				double newAdultRate = Double.parseDouble(oldadultRate.get(0));
				rateGrid.enterRoomClassRates(driver, ReservationChangeClass, "" + newRate,
						oldisAdditonalAdultChild.get(0), "" + newAdultRate, "" + newChildRate, test_steps);

				double customRatePN = Double.parseDouble(oldcustomRatePerNight.get(0));
				double childPN = Double.parseDouble(oldchildRatePerNight.get(0));
				double adultPN = Double.parseDouble(oldadultRatePerNight.get(0));

				rateGrid.enterRoomClassRates(driver, ReservationChangeClass, "" + newRate, false, "", "", test_steps);
				nightlyRate.clickSaveSason(driver, test_steps);

				nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
						.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
				nightlyRate.closeSeason(driver, test_steps);

				app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
				ArrayList<String> gettest1 = new ArrayList<>();
				for (int i = 0; i < getRoomClassWithRates.size(); i++) {
					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

					gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
					app_logs.info("arraylistSize" + gettest1.size());
					for (int j = 0; j < gettest1.size(); j++) {
						app_logs.info(gettest1.get(j));
					}

				}

			} else {
				app_logs.info("No Season For no stay details change Date");
			}
			rateGrid.clickOnSaveratePlan(driver);
			rateGrid.verifyLoadingGone(driver);
			rateGrid.closeOpendTabInMainMenu(driver);

			navigation.cpReservation_Backward(driver);
			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			baseRate.clear();
			adultCapacity.clear();
			personCapacity.clear();
			isAdditonalAdultChild.clear();
			maxAdult1.clear();
			maxPerson.clear();
			adultRate.clear();
			childRate1.clear();
			isProStayRate.clear();
			customRatePerNight.clear();
			adultRatePerNight.clear();
			childRatePerNight.clear();

			ArrayList<String> getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);

			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
			baseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);

			max_person = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(max_person);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);

			// first verify edit rate with same date
			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			String[] checkin = CheckInDate.split("\\|");
			String[] checkout_recalculate = CheckOutDate.split("\\|");
			HashMap<String, String> roomChargesFolio = null;

			// here click on details tab
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				// if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange &&
				// isSeasonExist) {
				// if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange &&
				// isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
						// test_steps.add("Rates: "+rateListInReservation)
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);
				}
				app_logs.info("before click on save button");
				Wait.wait5Second();
				boolean isButtonEnabled = reservationPage.clickSaveButton(driver);
				if (isButtonEnabled) {
					reservationPage.verifyPopupChangeInCost1(driver, test_steps);
					reservationPage.click_Folio(driver, test_steps);
					if (ReservationType.equalsIgnoreCase("MRB")) {
						reservationPage.selectMRBLastFolio(driver, test_steps);
					}

					reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);

					intervalDates.clear();
					intervalDates = rateListInReservation.get(1);
					folioRate.clear();
					folioRate = rateListInReservation.get(0);
					app_logs.info("folioRate: " + folioRate);
					app_logs.info("intervalDates: " + intervalDates);
					category = "Room Charge";
					test_steps.add("=====Verification of interval rate in folio=====");
					for (int i = 0; i < intervalDates.size(); i++) {

						String folioDates = Utility.parseDate(intervalDates.get(i),
								ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

						String date = folio.itemDate(driver, category, i + 1);
						app_logs.info("ecpected: " + folioDates);
						app_logs.info("found: " + date);

						assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

						String description = folio.itemDetailsDescroption(driver, category, i + 1);
						app_logs.info("ecpected: " + folioName);
						app_logs.info("found: " + description);

						assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

						String amount = folio.itemAmount(driver, category, i + 1);
						amount = folio.splitStringBySign(amount, "$");
						double dbAmount = Double.parseDouble(folioRate.get(i));
						String strAmount = String.format("%.2f", dbAmount);
						app_logs.info("expected: " + strAmount);
						app_logs.info("found: " + amount);
						assertEquals(amount, "" + strAmount, "Failed: Amount is mismatching in folio line item!");
						test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

					}
					test_steps.add("Verified interval rate along with dates and description");
					reservationPage.click_DeatilsTab(driver, test_steps);

				} else {
					reservationPage.closeReservationSearch(driver, test_steps);
					test_steps.add("Failed: after updated base rate, save button is disbaled in Change Stay Details");
				}

			} else {
				test_steps.add("No rate combination found");
			}

			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);			
			
			// second add or remove adult
			test_steps.clear();
			testName = "Change stay details-Recalculate, Additonal Adlt/chaild calculation-increase, the adults count in reservation";
			test_description = "Verify recalculate folio after add or remove adult";

			int person_cap = Integer.parseInt(personCapacity.get(0));
			int adult_cap = Integer.parseInt(adultCapacity.get(0));
			int max_adult = Integer.parseInt(maxAdult1.get(0));
			int maxper = Integer.parseInt(maxPerson.get(0));

			int enterAdult = Integer.parseInt(adult);
			int enterChild = Integer.parseInt(children);

			test_steps.add("Prevouse adult: " + adult);

			if (enterAdult == max_adult) {
				adult = String.valueOf(enterAdult - 1);
			}

			if (enterAdult < max_adult) {
				adult = String.valueOf(enterAdult + 1);
			}

			enterAdult = Integer.parseInt(adult);

			int totalchild = enterAdult + enterChild;

			if (totalchild == maxper) {
				// children = String.valueOf(enterChild-1);
			}

			if (totalchild < maxper) {
				// children = String.valueOf(enterChild+1);
			}

			test_steps.add("New adult: " + max_adult);

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, "" + max_adult);
			reservationPage.enter_Children(driver, test_steps, "0");
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			// here verify find room
			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExist) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, maxAdults, "0",
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);

				}
				app_logs.info("before click on save button");
				// Wait.wait10Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}

				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);

				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("ecpected: " + folioDates);
					app_logs.info("found: " + date);

					assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("ecpected: " + folioName);
					app_logs.info("found: " + description);

					assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

					String amount = folio.itemAmount(driver, category, i + 1);
					amount = folio.splitStringBySign(amount, "$");

					double expecetdAmount = Double.parseDouble(folioRate.get(i));
					String getAmount = String.format("%.2f", expecetdAmount);
					app_logs.info("expected: " + getAmount);
					app_logs.info("found: " + amount);

					assertEquals(amount, getAmount, "Failed: Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + getAmount);

				}
				test_steps.add("Verified interval rate along with dates and description after change adult");

			} else {
				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			reservationPage.closeReservationTab(driver);

			// third increase check out date

			test_steps.clear();
			testName = "Change stay details-Recalculate, Extend checkout date and verify rate in reservation";
			test_steps.add("Change stay details-Recalculate, Extend checkout date and verify rate in reservation");
			test_description = "Verify recalculate folio after extend checkout date";

			numberOfDys = Integer.parseInt(interval);
			if (numberOfDys > 2) {
				numberOfDys = numberOfDys + (numberOfDys - 2);
			} else {
				numberOfDys = numberOfDys + (numberOfDys - 1);

			}

			int daysDifferent = ESTTimeZone.numberOfDaysBetweenDates(CheckInDate, CheckOutDate);
			app_logs.info("CheckInDate: " + CheckInDate);
			test_steps.add("Check In: " + CheckInDate);
			test_steps.add("Old Check Out: " + CheckOutDate);
			String oldCheckout = CheckOutDate;

			CheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckOutDate, "dd/MM/yyyy", "dd/MM/yyyy",
					numberOfDys, "US/Eastern");
			app_logs.info("New Check Out: " + CheckOutDate);
			test_steps.add("New Check Out: " + CheckOutDate);

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			app_logs.info("daysChnage: " + daysChange);
			test_steps.add("Number of night: " + daysChange);

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			try {
				rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			} catch (Exception e) {
				// TODO: handle exception
			}

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {

				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);

			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);

			try {

				if (isSeasonExistChange) {
					app_logs.info("season for add dates");
				} else {
					app_logs.info("No Season For no stay details change Date");
				}
				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);

			}

			navigation.cpReservation_Backward(driver);
			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				app_logs.info("isMinStayRuleChnage: " + isMinStayRuleChnage);
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);

				}

				app_logs.info("before click on save button");
				Wait.wait5Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}

				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expecetd: " + folioDates);
					app_logs.info("found: " + date);

					assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("ecpected: " + folioName);
					app_logs.info("found: " + description);

					assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

					String amount = folio.itemAmount(driver, category, i + 1);
					amount = folio.splitStringBySign(amount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected amount: " + strAmount);
					app_logs.info("found: " + amount);

					assertEquals(amount, strAmount, "Failed: Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

				}
				test_steps.add("Verified interval rate along with dates and description");
			} else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			// here end

			reservationPage.closeReservationTab(driver);
			// case decrease checkin date

			test_steps.clear();
			testName = "Change stay details-Recalculate, Reduce checkin date and verify rate in reservation";
			test_description = "Verify recalculate folio after reduce checkin date";
			test_steps.add("Old checkin date: " + CheckInDate);
			CheckInDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "dd/MM/yyyy", "dd/MM/yyyy", -2,
					"US/Eastern");
			test_steps.add("New checkin date: " + CheckInDate);
			app_logs.info("CheckInDate: " + CheckInDate);
			app_logs.info("Check out Date: " + CheckOutDate);
			test_steps.add("Check out Date: " + CheckOutDate);

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			app_logs.info("daysChnage: " + daysChange);
			test_steps.add("Number of night: " + daysChange);

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			try {
				rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);
			} catch (Exception e) {
				// TODO: handle exception
			}

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {

				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);

			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);

			try {

				if (isSeasonExistChange) {
					app_logs.info("season for add dates");

				} else {
					app_logs.info("No Season For no stay details change Date");
				}
				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);

			}

			Wait.wait5Second();
			navigation.cpReservation_Backward(driver);

			test_steps.add("Back to reservations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				app_logs.info("isMinStayRuleChnage: " + isMinStayRuleChnage);
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Successfully verified the no check in rule label not displayed");
							test_steps.add("Successfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color label for no check out rule is not found");
							app_logs.info("Successfully verified the red color label for no check out rule");
							test_steps.add("Successfully verified the red color label for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color label for no check out rule is not found");
							app_logs.info("Successfully verified the green color label for no check out rule");
							test_steps.add("Successfully verified the green color label for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Successfully verified the no check out rule label not displayed");
							test_steps.add("Successfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);

				}
				app_logs.info("before click on save button");
				Wait.wait5Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expecetd: " + folioDates);
					app_logs.info("found: " + date);

					assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected description name: " + folioName);
					app_logs.info("found: " + description);

					assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

					String amount = folio.itemAmount(driver, category, i + 1);
					amount = folio.splitStringBySign(amount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected amount: " + strAmount);
					app_logs.info("found: " + amount);

					assertEquals(amount, strAmount, "Failed: Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

				}
				test_steps.add("Verified interval rate along with dates and description");
			} else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			reservationPage.closeReservationTab(driver);

			// here end
			test_steps.clear();
			testName = "Change stay details-Recalculate, Update pro stay rate and verify rate in reservation";
			test_description = "Change stay details-Recalculate, Update pro stay rate and verify rate in reservation";

			test_steps.add(
					"=================== VERIFICATION OF PRO RATE AFTER UPDATE CUSTOME RATE ======================");
			app_logs.info(
					"=================== VERIFICATION OF PRO RATE AFTER UPDATE CUSTOME RATE ======================");

			// case update pro rate value

			CheckInDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "dd/MM/yyyy", "dd/MM/yyyy", +2,
					"US/Eastern");

			app_logs.info("CheckInDate: " + CheckInDate);
			app_logs.info("CheckInDate: " + CheckOutDate);

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			app_logs.info("daysChnage: " + daysChange);

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {

				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage.clear();
			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage.clear();
			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);

			try {

				if (isSeasonExistChange) {

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					// here get interval info and update rate value
					double newRate = Double.parseDouble(baseRate.get(0));
					newRate = newRate + 50;

					double customRatePN = Double.parseDouble(customRatePerNight.get(0));
					customRatePN = customRatePN + 100;
					double childPN = Double.parseDouble(childRatePerNight.get(0));
					childPN = childPN + 50;
					double adultPN = Double.parseDouble(adultRatePerNight.get(0));
					adultPN = adultPN + 50;
					rateGrid.updateProRateAtClassLevel(driver, ReservationChangeClass, isAdditonalAdultChild.get(0),
							true, String.valueOf(customRatePN), String.valueOf(adultPN), String.valueOf(childPN),
							test_steps);
					nightlyRate.clickSaveSason(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
					nightlyRate.closeSeason(driver, test_steps);

					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
					ArrayList<String> gettest1 = new ArrayList<>();
					for (int i = 0; i < getRoomClassWithRates.size(); i++) {
						app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

						gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
						app_logs.info("arraylistSize" + gettest1.size());
						for (int j = 0; j < gettest1.size(); j++) {
							app_logs.info(gettest1.get(j));
						}

					}

				} else {
					app_logs.info("No Season For no stay details change Date");
				}
				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);

			}
			navigation.cpReservation_Backward(driver);
			baseRate.clear();
			adultCapacity.clear();
			personCapacity.clear();
			isAdditonalAdultChild.clear();
			maxAdult1.clear();
			maxPerson.clear();
			adultRate.clear();
			childRate1.clear();
			isProStayRate.clear();
			customRatePerNight.clear();
			adultRatePerNight.clear();
			childRatePerNight.clear();

			getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);

			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
			baseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);

			max_person = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(max_person);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);

			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				app_logs.info("isMinStayRuleChnage: " + isMinStayRuleChnage);
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);

				}
				app_logs.info("before click on save button");
				Wait.wait5Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expecetd: " + folioDates);
					app_logs.info("found: " + date);

					assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected description name: " + folioName);
					app_logs.info("found: " + description);

					assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

					String amount = folio.itemAmount(driver, category, i + 1);
					amount = folio.splitStringBySign(amount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected amount: " + strAmount);
					app_logs.info("found: " + amount);

					assertEquals(amount, strAmount, "Failed: Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

				}
				test_steps.add("Verified interval rate along with dates and description");
			} else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			// end here

			test_steps.clear();
			testName = "Change stay details-Recalculate, Disabled pro stay rate and verify rate in reservation";
			test_description = "Change stay details-Recalculate, Disabled pro stay rate and verify rate in reservation";

			test_steps.add(
					"=================== VERIFICATION OF RECALCULATE AFTER DISABLED PRO STAY RATE ======================");
			app_logs.info(
					"=================== VERIFICATION OF RECALCULATE AFTER DISABLED PRO STAY RATE ======================");

			// case disabled pro rate value

			CheckInDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "dd/MM/yyyy", "dd/MM/yyyy", +1,
					"US/Eastern");

			app_logs.info("CheckInDate: " + CheckInDate);
			app_logs.info("CheckInDate: " + CheckOutDate);

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			app_logs.info("daysChnage: " + daysChange);

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {

				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage.clear();
			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage.clear();
			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);

			try {

				if (isSeasonExistChange) {

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					// here get interval info and update rate value
					double newRate = Double.parseDouble(baseRate.get(0));
					newRate = newRate + 50;

					double customRatePN = Double.parseDouble(customRatePerNight.get(0));
					customRatePN = customRatePN + 100;
					double childPN = Double.parseDouble(childRatePerNight.get(0));
					childPN = childPN + 50;
					double adultPN = Double.parseDouble(adultRatePerNight.get(0));
					adultPN = adultPN + 50;
					rateGrid.updateProRateAtClassLevel(driver, ReservationChangeClass, isAdditonalAdultChild.get(0),
							false, String.valueOf(customRatePN), String.valueOf(adultPN), String.valueOf(childPN),
							test_steps);
					nightlyRate.clickSaveSason(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
					nightlyRate.closeSeason(driver, test_steps);

					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
					ArrayList<String> gettest1 = new ArrayList<>();
					for (int i = 0; i < getRoomClassWithRates.size(); i++) {
						app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

						gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
						app_logs.info("arraylistSize" + gettest1.size());
						for (int j = 0; j < gettest1.size(); j++) {
							app_logs.info(gettest1.get(j));
						}

					}

				} else {
					app_logs.info("No Season For no stay details change Date");
				}
				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);

			}

			navigation.cpReservation_Backward(driver);
			baseRate.clear();
			adultCapacity.clear();
			personCapacity.clear();
			isAdditonalAdultChild.clear();
			maxAdult1.clear();
			maxPerson.clear();
			adultRate.clear();
			childRate1.clear();
			isProStayRate.clear();
			customRatePerNight.clear();
			adultRatePerNight.clear();
			childRatePerNight.clear();

			getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);

			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
			baseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);

			max_person = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(max_person);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {
				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);

			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			// update here
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			Wait.wait10Second();

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				app_logs.info("isMinStayRuleChnage: " + isMinStayRuleChnage);
				app_logs.info("restricrionsLengthOfStayChange: " + restricrionsLengthOfStayChange);
				app_logs.info("restricrionsBookingWindowChange: " + restricrionsBookingWindowChange);

				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);
				}
				app_logs.info("before click on save button");
				Wait.wait5Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expecetd: " + folioDates);
					app_logs.info("found: " + date);

					assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected description name: " + folioName);
					app_logs.info("found: " + description);

					assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

					String amount = folio.itemAmount(driver, category, i + 1);
					amount = folio.splitStringBySign(amount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected amount: " + strAmount);
					app_logs.info("found: " + amount);

					assertEquals(amount, strAmount, "Failed: Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

				}
				test_steps.add("Verified interval rate along with dates and description");
			} else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			// here end recalculate

			test_steps.clear();
			testName = "Change stay details-No Rate Change, rate update at season level and verify in no rate change after extend checkin an checkout";
			test_description = "Change stay details-No Rate Change, rate update at season level and verify in no rate change after extend checkin and checkout";

			// here start no rate change
			test_steps.add(
					"=================== VERIFICATION OF NO RATE CHANGE AFTER UPDATE BASE RATE  ======================");
			app_logs.info(
					"=================== VERIFICATION OF NO RATE CHANGE AFTER UPDATE BASE RATE  ======================");

			app_logs.info("folioRate: " + folioRate);
			double amount = 0.00;
			oldRates1 = new ArrayList<>();

			// oldRates = folioRate;
			for (int i = 0; i < folioRate.size(); i++) {

				amount = amount + (Double.parseDouble(folioRate.get(i)));
				oldRates1.add(folioRate.get(i));
			}
			app_logs.info("oldRates: " + oldRates1);
			String totalRoomCharges = String.format("%.2f", amount);
			app_logs.info("total RoomCharges: " + totalRoomCharges);

			numberOfDys = Integer.parseInt(interval);
			if (numberOfDys > 2) {
				numberOfDys = numberOfDys + (numberOfDys - 2);
			} else {
				numberOfDys = numberOfDys + (numberOfDys - 1);

			}

			// ArrayList<String> oldRateList =
			int intervalLength = 1;
			test_steps.add("Old check in: " + CheckInDate);
			CheckInDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "dd/MM/yyyy", "dd/MM/yyyy",
					intervalLength, "US/Eastern");
			test_steps.add("New check in: " + CheckInDate);

			test_steps.add("Old check out: " + CheckOutDate);

			CheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckOutDate, "dd/MM/yyyy", "dd/MM/yyyy",
					intervalLength, "US/Eastern");
			test_steps.add("New check out: " + CheckOutDate);

			app_logs.info("CheckInDate: " + CheckInDate);
			app_logs.info("CheckOutDate: " + CheckOutDate);

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			app_logs.info("daysChnage: " + daysChange);
			test_steps.add("Number of nights: " + daysChange);

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);
			app_logs.info("get min stay rule in : " + minruleChange);
			app_logs.info("get min stay rule size : " + minruleChange.size());
			app_logs.info("get min stay rule highest value : " + minruleChange.get(minruleChange.size() - 1));

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {

				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			// noCheckInRuleChnage.clear();
			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			// noCheckOutRuleChnage.clear();
			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);

			try {

				if (isSeasonExistChange) {

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					// here get interval info and update rate value
					double newRate = Double.parseDouble(oldbaseRate.get(0));
					double newChildRate = Double.parseDouble(oldchildRate1.get(0));
					double newAdultRate = Double.parseDouble(oldadultRate.get(0));

					double customRatePN = Double.parseDouble(oldcustomRatePerNight.get(0));
					double childPN = Double.parseDouble(oldchildRatePerNight.get(0));
					double adultPN = Double.parseDouble(oldadultRatePerNight.get(0));

					rateGrid.updateProRateAtClassLevel(driver, ReservationChangeClass, isAdditonalAdultChild.get(0),
							true, String.valueOf(customRatePN), String.valueOf(adultPN), String.valueOf(childPN),
							test_steps);

					rateGrid.enterRoomClassRates(driver, ReservationChangeClass, "" + newRate,
							oldisAdditonalAdultChild.get(0), "" + newAdultRate, "" + newChildRate, test_steps);

					nightlyRate.clickSaveSason(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					// here get interval info and update rate value

					nightlyRate.clickSaveSason(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
					nightlyRate.closeSeason(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
					nightlyRate.closeSeason(driver, test_steps);

					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
					ArrayList<String> gettest1 = new ArrayList<>();
					for (int i = 0; i < getRoomClassWithRates.size(); i++) {
						app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

						gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
						app_logs.info("arraylistSize" + gettest1.size());
						for (int j = 0; j < gettest1.size(); j++) {
							app_logs.info(gettest1.get(j));
						}

					}

				} else {
					app_logs.info("No Season For no stay details change Date");
				}
				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);

			}

			navigation.cpReservation_Backward(driver);
			baseRate.clear();
			adultCapacity.clear();
			personCapacity.clear();
			isAdditonalAdultChild.clear();
			maxAdult1.clear();
			maxPerson.clear();
			adultRate.clear();
			childRate1.clear();
			isProStayRate.clear();
			customRatePerNight.clear();
			adultRatePerNight.clear();
			childRatePerNight.clear();

			getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);

			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
			baseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);

			max_person = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(max_person);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);

			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			Wait.wait5Second();

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "No Rate Change");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				app_logs.info("isMinStayRuleChnage: " + isMinStayRuleChnage);
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color label for no check in rule is not found");
							app_logs.info("Successfully verified the red color label for no check in rule");
							test_steps.add("Successfully verified the red color label for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color label for no check out rule is not found");
							app_logs.info("Successfully verified the red color label for no check out rule");
							test_steps.add("Successfully verified the red color label for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color label for no check out rule is not found");
							app_logs.info("Successfully verified the green color label for no check out rule");
							test_steps.add("Successfully verified the green color label for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
					app_logs.info("oldRates: " + oldRates1);
					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, folioRate, totalRoomCharges,
							test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);

				}

				app_logs.info("before click on save button");
				Wait.wait5Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expecetd: " + folioDates);
					app_logs.info("found: " + date);

					// assertEquals(date, folioDates, "Failed: Interval date is
					// mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected description name: " + folioName);
					app_logs.info("found: " + description);

					// assertEquals(description, folioName, "Failed: Description
					// is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected amount: " + strAmount);
					app_logs.info("found: " + getamount);

					// assertEquals(getamount, strAmount, "Failed: Amount is
					// mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

					if (!date.equalsIgnoreCase(folioDates)) {
						test_steps.add("Failed: Folio date is mismatching!");
						break;
					}

					if (!description.equalsIgnoreCase(folioName)) {
						test_steps.add("Failed: Folio description is mismatching!");
						break;

					}

					if (!getamount.equalsIgnoreCase(strAmount)) {
						test_steps.add("Failed: Folio amount is mismatching!");
						break;

					}

				}
				test_steps.add("Verified interval rate along with dates and description");
			} else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			// end here

			app_logs.info("oldRates: " + oldRates1);
			test_steps.clear();
			testName = "Change stay details-No Rate Change, Additonal Adlt/chaild calculation-increase the adults count in reservation";
			test_description = "Additonal Adlt/chaild calculation-increase the adults count in reservation";

			test_steps.add(
					"=================== VERIFICATION OF NO RATE CHANGE AFTER UPDATED ADULT ======================");
			app_logs.info(
					"=================== VERIFICATION OF NO RATE CHANGE AFTER UPDATED ADULT ======================");

			// here add or remove adult
			reservationPage.click_DeatilsTab(driver, test_steps);
			person_cap = Integer.parseInt(personCapacity.get(0));
			adult_cap = Integer.parseInt(adultCapacity.get(0));
			max_adult = Integer.parseInt(maxAdult1.get(0));
			maxper = Integer.parseInt(maxPerson.get(0));

			enterAdult = Integer.parseInt(adult);
			enterChild = Integer.parseInt(children);

			if (enterAdult == max_adult) {
				adult = String.valueOf(enterAdult - 1);
			}

			if (enterAdult < max_adult) {
				adult = String.valueOf(enterAdult + 1);
			}

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, "" + max_adult);
			reservationPage.enter_Children(driver, test_steps, "0");
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "No Rate Change");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				app_logs.info("isMinStayRuleChnage: " + isMinStayRuleChnage);
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					app_logs.info("oldRates: " + oldRates1);
					app_logs.info("foliorate: " + folioRate);

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, "" + max_adult,
							"0", ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, oldRates1, totalRoomCharges,
							test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);

				}
				app_logs.info("before click on save button");
				Wait.wait5Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expecetd: " + folioDates);
					app_logs.info("found: " + date);

					// assertEquals(date, folioDates, "Failed: Interval date is
					// mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected description name: " + folioName);
					app_logs.info("found: " + description);

					// assertEquals(description, folioName, "Failed: Description
					// is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected amount: " + strAmount);
					app_logs.info("found: " + getamount);

					// assertEquals(getamount, strAmount, "Failed: Amount is
					// mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

					if (!date.equalsIgnoreCase(folioDates)) {
						test_steps.add("Failed: Folio date is mismatching!");
						break;
					}

					if (!description.equalsIgnoreCase(folioName)) {
						test_steps.add("Failed: Folio description is mismatching!");
						break;

					}

					if (!getamount.equalsIgnoreCase(strAmount)) {
						test_steps.add("Failed: Folio amount is mismatching!");
						break;

					}

				}
				test_steps.add("Verified interval rate along with dates and description");
			} else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			// end here
			// case update child and verify no rate change
			testName = "Change stay details-No Rate Change, Additonal Adlt/chaild calculation-increase the Children count in reservation";
			test_description = "Additonal Adlt/chaild calculation-increase the Children count in reservation";

			test_steps.add(
					"=================== VERIFICATION OF NO RATE CHANGE AFTER UPDATED CHILDREN ======================");
			app_logs.info(
					"=================== VERIFICATION OF NO RATE CHANGE AFTER UPDATED CHILDREN ======================");
			reservationPage.ClickOnDetails(driver);
			enterAdult = Integer.parseInt(adult);
			totalchild = enterAdult + enterChild;

			if (totalchild == maxper) {
				children = String.valueOf(enterChild - 1);
			}

			if (totalchild < maxper) {
				children = String.valueOf(enterChild + 1);
			}

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "No Rate Change");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				app_logs.info("isMinStayRuleChnage: " + isMinStayRuleChnage);
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color label for no check in rule is not found");
							app_logs.info("Successfully verified the red color lable for no check in rule");
							test_steps.add("Successfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color label for no check out rule is not found");
							app_logs.info("Successfully verified the green color label for no check out rule");
							test_steps.add("Successfully verified the green color label for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Successfully verified the no check out rule label not displayed");
							test_steps.add("Successfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, oldRates1, totalRoomCharges,
							test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}
					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
							minStayRuleValueChange);

				}

				app_logs.info("before click on save button");
				Wait.wait5Second();
				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expecetd: " + folioDates);
					app_logs.info("found: " + date);

					assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected description name: " + folioName);
					app_logs.info("found: " + description);

					assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected amount: " + strAmount);
					app_logs.info("found: " + getamount);

					assertEquals(getamount, strAmount, "Failed: Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

				}
				test_steps.add(
						"Verified interval rate along with dates and description after updated children in no change rate");
			}

			else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, "Failed to verify season in single room reservation", testName, "RatesV2", driver);

		} catch (Error e) {
			e.printStackTrace();
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, "Failed to verify season in single room reservation", testName, "RatesV2", driver);

		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		// end here
		void ChangeStayDetailsForAddAndRemoveDates(String RatePlanName, String CheckInDate, String CheckOutDate,
				String ReservationChangeClass, String channelName, String ReservationRoomClasses, String ReservationType,
				String adult, String children, String PromoCode, String interval) throws Exception {
			reservationPage.closeReservationTab(driver);
			testName = "Change stay details-Change only for added/removed dates, Update base rate at season level and verify in reservation after extend checkout date";
			test_description = "Change stay details-Change only for added/removed dates, Update base rate at season level and verify in reservation after extend checkout date";

			test_steps.add(
					"=================== UPDATE BASE RATE AND VERIFY CHANGE ONLY FOR ADDED/REMOVED DATES ======================");
			app_logs.info(
					"=================== UPDATE BASE RATE AND VERIFY CHANGE ONLY FOR ADDED/REMOVED DATES ======================");
			ArrayList<String> folioRate = rateListInReservation.get(0);
			ArrayList<String> intervalDates = rateListInReservation.get(1);
			ArrayList<String> oldRates1 = new ArrayList<>();
			String category = "Room Charge";
			int numberOfDys = 0;
			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
		
			//rateGrid.selectRatePlan(driver, RatePlanName, test_steps);

			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);
			
			int oldDays = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			String oldCheckin = CheckInDate;
			app_logs.info("CheckInDate: " + CheckInDate);
			test_steps.add("Checkin date: " + CheckInDate);
			int interlLength = Integer.parseInt(interval);
			ArrayList<String> getOldInterval = reservationPage.getIntervalDuration("dd/MM/yyyy", CheckInDate, 
					CheckOutDate, "MMM dd yyyy", "US/Eastern", interlLength, isProStayRate.get(0));
			
			int addedDays = interlLength+1;
			CheckInDate = CheckOutDate;
			String oldcheckout = CheckOutDate;
			CheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "dd/MM/yyyy", "dd/MM/yyyy",
					+addedDays, "US/Eastern");
			app_logs.info("CheckOutDate: " + CheckOutDate);
			test_steps.add("Old checkout date: " + oldcheckout);
			test_steps.add("New checkout date: " + CheckOutDate);

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			test_steps.add("Number of nights: " + daysChange);
			test_steps.add("How many nights increase? " + addedDays);
			
			int oldfullIntervals = 0;
			int oldhalfIntervals = 0;

			oldfullIntervals = oldDays / (Integer.parseInt(interval));
			oldhalfIntervals = oldDays % (Integer.parseInt(interval));

			System.out.println("Full Interval:" + oldhalfIntervals);
			System.out.println("Half Interval:" + oldhalfIntervals);

			int daysAdded = 0;
			if (oldhalfIntervals != 0) {
				for (int i = 1; i <= (Integer.parseInt(interval)); i++) {
					oldhalfIntervals = oldhalfIntervals + i;
					daysAdded = daysAdded + 1;
					if (oldhalfIntervals == (Integer.parseInt(interval))) {
						break;
					}
				}
			}

			CheckInDate = oldCheckin;
			app_logs.info("daysAdded: " + daysAdded);
			int newdaysChange = daysChange - daysAdded;

			System.out.println("daysAdded: " + daysAdded);
			System.out.println("newdaysChange: " + newdaysChange);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {
				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckInDate);

			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckInDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);
			// change rate value
			if (isSeasonExistChange) {
				nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				// here get interval info and update rate value
				double newRate = Double.parseDouble(oldbaseRate.get(0));
				newRate = newRate + 50;
				double newChildRate = Double.parseDouble(oldchildRate1.get(0));
				newChildRate = newChildRate + 10;
				double newAdultRate = Double.parseDouble(oldadultRate.get(0));
				newAdultRate = newAdultRate + 10;

				double customRatePN = Double.parseDouble(oldcustomRatePerNight.get(0));
				double childPN = Double.parseDouble(oldchildRatePerNight.get(0));
				double adultPN = Double.parseDouble(oldadultRatePerNight.get(0));

				rateGrid.updateProRateAtClassLevel(driver, ReservationChangeClass, isAdditonalAdultChild.get(0), true,
						String.valueOf(customRatePN), String.valueOf(adultPN), String.valueOf(childPN), test_steps);

				rateGrid.enterRoomClassRates(driver, ReservationChangeClass, "" + newRate,
						oldisAdditonalAdultChild.get(0), "" + newAdultRate, "" + newChildRate, test_steps);
				nightlyRate.clickSaveSason(driver, test_steps);

				nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate, CheckOutDate);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
						.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
				nightlyRate.closeSeason(driver, test_steps);

				app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
				ArrayList<String> gettest1 = new ArrayList<>();
				for (int i = 0; i < getRoomClassWithRates.size(); i++) {
					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

					gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
					app_logs.info("arraylistSize" + gettest1.size());
					for (int j = 0; j < gettest1.size(); j++) {
						app_logs.info(gettest1.get(j));
					}

				}

			} else {
				app_logs.info("No Season For no stay details change Date");
			}
			nightlyRate.clickSaveRatePlan(driver, test_steps);
			rateGrid.verifyLoadingGone(driver);
			rateGrid.closeOpendTabInMainMenu(driver);

			navigation.cpReservation_Backward(driver);
			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			String baserate = "";
			String adultCapicity = "";
			String personCapicity = "";
			boolean isAdditionalAdultChild = false;
			String maxAdults = "";
			String max_person = "";
			String adultsRate = "";
			String childRate = "";
			boolean isProRate = false;
			String ratePerNight = "";
			String adultPerNight = "";
			String childPerNight = "";
			String path = "";

			baseRate.clear();
			adultCapacity.clear();
			personCapacity.clear();
			isAdditonalAdultChild.clear();
			maxAdult1.clear();
			maxPerson.clear();
			adultRate.clear();
			childRate1.clear();
			isProStayRate.clear();
			customRatePerNight.clear();
			adultRatePerNight.clear();
			childRatePerNight.clear();

			getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);
			app_logs.info("getRatesWithAdultsAndChild: "+getRatesWithAdultsAndChild);

			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
			baseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);

			max_person = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(max_person);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, oldCheckin);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Change only for added/removed dates");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {
					
					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					app_logs.info("folioRateSingle: " + folioRateSingle);
					double amount = 0.00;
					oldRates1.clear();
					for (int i = 0; i < folioRateSingle.size(); i++) {
						
						amount = amount + (Double.parseDouble(folioRateSingle.get(i)));
						oldRates1.add(folioRateSingle.get(i));

					}
					app_logs.info("oldRates1: " + oldRates1);
					String totalRoomCharges = String.format("%.2f", amount);
					app_logs.info("total RoomCharges: " + totalRoomCharges);

					ArrayList<String> getNewInterval = reservationPage.getIntervalDuration("dd/MM/yyyy", oldcheckout, 
							CheckOutDate, "MMM dd yyyy", "US/Eastern", interlLength, isProStayRate.get(0));

//					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
//							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
//							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
//							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
//							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
//							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, oldRates1, totalRoomCharges,
//							newdaysChange, test_steps);
					
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetailsBaseOnNewAndOldDates(driver,
							ratesConfig.getProperty("defaultDateFormat"), oldcheckout, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, oldRates1, totalRoomCharges,
							getOldInterval,oldRates1,getNewInterval, test_steps);


					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					app_logs.info("before click on select button");
					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,

							minStayRuleValueChange);
					Wait.wait5Second();

					reservationPage.clickSaveButton(driver);
					reservationPage.verifyPopupChangeInCost1(driver, test_steps);
					reservationPage.click_Folio(driver, test_steps);

				}

				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}

				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);

				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				for (int i = 0; i < folioRate.size(); i++) {
					app_logs.info("folioRate: " + folioRate.get(i));
					oldRates1.add(folioRate.get(i));
				}
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expected: " + folioDates);
					app_logs.info("found: " + date);

					// assertEquals(date, folioDates, "Failed: Interval date is
					// mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected: " + folioName);
					app_logs.info("found: " + description);

					// assertEquals(description, folioName, "Failed: Description
					// is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");

					String expectedAmount = String.format("%.2f", Double.parseDouble(folioRate.get(i)));
					app_logs.info("expected: " + expectedAmount);
					app_logs.info("found: " + expectedAmount);
					// assertEquals(expectedAmount, expectedAmount, "Failed:
					// Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + expectedAmount);

					if (!date.equalsIgnoreCase(folioDates)) {
						test_steps.add("Failed: Folio date is mismatching!");
						break;
					}
					if (!description.equalsIgnoreCase(folioName)) {
						test_steps.add("Failed: Folio description is mismatching!");
						break;
					}
					if (!getamount.equalsIgnoreCase(expectedAmount)) {
						test_steps.add("Failed: Folio Amount is mismatching!");
						break;
					}

				}
				test_steps.add("Verified interval rate along with dates and description");
			} else {
				test_steps.add("No rate combination found");
			}
			reservationPage.closeReservationTab(driver);
			test_steps.add("Close reservation");
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			// second verify adult

			test_steps.clear();
			testName = "Change stay details-Change only for added/removed dates, update adult and verify rate in reservtion";
			test_description = "Change stay details-Change only for added/removed dates, update adult in and verify rate in reservtion";
			
			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
		
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);
			
			oldDays = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			oldCheckin = CheckInDate;
			app_logs.info("CheckInDate: " + CheckInDate);
			test_steps.add("Checkin date: " + CheckInDate);
			interlLength = Integer.parseInt(interval);			
			CheckInDate = CheckOutDate;
			oldcheckout = CheckOutDate;
			CheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "dd/MM/yyyy", "dd/MM/yyyy",
					interlLength+1, "US/Eastern");
			app_logs.info("CheckOutDate: " + CheckOutDate);
			test_steps.add("Old checkout date: " + oldcheckout);
			test_steps.add("New checkout date: " + CheckOutDate);

			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			test_steps.add("Number of nights: " + daysChange);
			test_steps.add("How many nights increase? " + addedDays);
			
			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			CheckInDate = oldCheckin;
			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {
				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckInDate);

			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckInDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);
			int person_cap = Integer.parseInt(personCapacity.get(0));
			int adult_cap = Integer.parseInt(adultCapacity.get(0));
			int max_adult = Integer.parseInt(maxAdult1.get(0));
			int maxper = Integer.parseInt(maxPerson.get(0));
			
			navigation.cpReservation_Backward(driver);
			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.click_DeatilsTab(driver, test_steps);

			int enterAdult = Integer.parseInt(adult);
			int enterChild = Integer.parseInt(children);

			if (enterAdult == max_adult) {
				adult = String.valueOf(max_adult-1);
			}

			if (enterAdult < max_adult) {
				adult = String.valueOf(max_adult);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			//reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			test_steps.add("Old check in date: "+oldCheckin);
			test_steps.add("Old check out date: "+oldcheckout);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, "0");
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Change only for added/removed dates");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			// here verify find room
			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
					
					ArrayList<String> getNewInterval = reservationPage.getIntervalDuration("dd/MM/yyyy", 
							oldcheckout, 
							CheckOutDate, "MMM dd yyyy", "US/Eastern", interlLength, isProStayRate.get(0));

					app_logs.info("folioRate: " + folioRate);
					app_logs.info("folioRate: " + folioRate);

					double amount = 0.00;
					oldRates1.clear();
					app_logs.info("folioRate: "+folioRate);
					app_logs.info("intervalDates: "+intervalDates);
					for (int i = 0; i < folioRate.size(); i++) {
						amount = amount + (Double.parseDouble(folioRate.get(i)));
						oldRates1.add(folioRate.get(i));
						getOldInterval.add(intervalDates.get(i));
					}
					
					app_logs.info("oldRates1: " + oldRates1);
					String totalRoomCharges = String.format("%.2f", amount);
					app_logs.info("total RoomCharges: " + totalRoomCharges);					
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetailsBaseOnNewAndOldDates(driver,
							ratesConfig.getProperty("defaultDateFormat"), oldcheckout, CheckOutDate, adult,
							"0",
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, oldRates1, totalRoomCharges,
							getOldInterval,oldRates1,getNewInterval, test_steps);


					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					app_logs.info("before click on select button");
					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,

							minStayRuleValueChange);
					Wait.wait5Second();

					reservationPage.clickSaveButton(driver);
					reservationPage.verifyPopupChangeInCost1(driver, test_steps);
					reservationPage.click_Folio(driver, test_steps);

				}

				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}

				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expected: " + folioDates);
					app_logs.info("found: " + date);

					// assertEquals(date, folioDates, "Failed: Interval date is
					// mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected: " + folioName);
					app_logs.info("found: " + description);

					// assertEquals(description, folioName, "Failed: Description
					// is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");

					String expectedAmount = String.format("%.2f", Double.parseDouble(folioRate.get(i)));
					app_logs.info("expected: " + expectedAmount);
					app_logs.info("found: " + expectedAmount);
					// assertEquals(expectedAmount, expectedAmount, "Failed:
					// Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + expectedAmount);

					if (!date.equalsIgnoreCase(folioDates)) {
						test_steps.add("Failed: Folio date is mismatching!");
						break;
					}
					if (!description.equalsIgnoreCase(folioName)) {
						test_steps.add("Failed: Folio description is mismatching!");
						break;
					}
					if (!getamount.equalsIgnoreCase(expectedAmount)) {
						test_steps.add("Failed: Folio Amount is mismatching!");
						break;
					}

				}
				test_steps.add("Verified interval rate along with dates and description");

			} else {
				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			// third add or remove child

			test_steps.clear();
			testName = "Change stay details-Change only for added/removed dates, update child and verify rate in reservtion";
			test_description = "Change stay details-Change only for added/removed dates, update child and verify rate in reservtion";

			reservationPage.ClickOnDetails(driver);
			reservationPage.click_DeatilsTab(driver, test_steps);
			enterAdult = Integer.parseInt(adult);
			int totalchild = enterAdult + enterChild;

			if (totalchild == maxper) {
				children = String.valueOf(enterChild - 1);
			}

			if (totalchild < maxper) {
				children = String.valueOf(enterChild + 1);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Change only for added/removed dates");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			// here verify find room
			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				String minStayColorChange = "";
				// if (restricrionsLengthOfStayChange &&
				// restricrionsBookingWindowChange && isSeasonExistChange) {
				if (isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							Utility.app_logs.info("minStayColor : " + minStayColorChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
										"Red color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the red color lable for min stay rule");
								test_steps.add("Succesfully veried the red color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeClass, minStayRuleValueChange);
							try {
								assertTrue(minStayColorChange.equalsIgnoreCase("green"),
										"green color lable for minstay rule is not found");
								app_logs.info("Succesfully veried the green color lable for min stay rule");
								test_steps.add("Succesfully veried the green color lable for min stay rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					}

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							Utility.app_logs.info("noCheckinColor");
							assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check in rule");
							test_steps.add("Succesfully verified the red color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
									"green color lable for no check in rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check in rule");
							test_steps.add("Succesfully verified the green color lable for no check in rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
									"no check in rule label is displayed");
							app_logs.info("Succesfully verified the no check in rule label not displayed");
							test_steps.add("Succesfully verified the no check in rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String noCheckoutColorChange = "";
					if (checkOutColorChange.equalsIgnoreCase("Red")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
									"red color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the red color lable for no check out rule");
							test_steps.add("Succesfully verified the red color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
									"green color lable for no check out rule is not found");
							app_logs.info("Succesfully verified the green color lable for no check out rule");
							test_steps.add("Succesfully verified the green color lable for no check out rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
									ReservationChangeClass);
							assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
									"no check out rule label is displayed");
							app_logs.info("Succesfully verified the no check out rule label not displayed");
							test_steps.add("Succesfully verified the no check out rule label not displayed");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					rateListInReservation.clear();
					app_logs.info("folioRate: " + folioRate);
					double amount = 0.00;
					oldRates1.clear();
					oldRates1 = folioRate;
					for (int i = 0; i < folioRate.size(); i++) {

						amount = amount + (Double.parseDouble(folioRate.get(i)));
					}

					String totalRoomCharges = String.format("%.2f", amount);
					app_logs.info("total RoomCharges: " + totalRoomCharges);

					app_logs.info("folio name: " + folioName);
					app_logs.info("folio rate: " + folioRate);
					app_logs.info("old charges" + totalRoomCharges);
					app_logs.info("newdaysChange: " + newdaysChange);
					rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
							ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
							ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
							personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
							adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
							adultRatePerNight.get(0), childRatePerNight.get(0), folioName, folioRate, totalRoomCharges,
							newdaysChange, test_steps);

					for (int i = 0; i < rateListInReservation.size(); i++) {
						app_logs.info(rateListInReservation.get(i));
					}

					if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
						app_logs.info("in else part of interval");
						String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
								+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
						test_steps.add("bug id: " + bugId);

					}

					app_logs.info("before click on select button");
					reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
							"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,

							minStayRuleValueChange);
					Wait.wait5Second();

					reservationPage.clickSaveButton(driver);
					reservationPage.verifyPopupChangeInCost1(driver, test_steps);
					reservationPage.click_Folio(driver, test_steps);

				}

				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}

				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);

				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expected: " + folioDates);
					app_logs.info("found: " + date);

					// assertEquals(date, folioDates, "Failed: Interval date is
					// mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected: " + folioName);
					app_logs.info("found: " + description);

					// assertEquals(description, folioName, "Failed: Description
					// is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");

					String expectedAmount = String.format("%.2f", Double.parseDouble(folioRate.get(i)));
					app_logs.info("expected: " + expectedAmount);
					app_logs.info("found: " + expectedAmount);
					// assertEquals(expectedAmount, expectedAmount, "Failed:
					// Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + expectedAmount);

					if (!date.equalsIgnoreCase(folioDates)) {
						test_steps.add("Failed: Folio date is mismatching!");
						break;
					}
					if (!description.equalsIgnoreCase(folioName)) {
						test_steps.add("Failed: Folio description is mismatching!");
						break;
					}
					if (!getamount.equalsIgnoreCase(expectedAmount)) {
						test_steps.add("Failed: Folio Amount is mismatching!");
						break;
					}

				}
				test_steps.add("Verified interval rate along with dates and description");

			} else {
				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			reservationPage.closeReservationTab(driver);

			test_steps.clear();
			testName = "Change stay details-Change only for added/removed dates, update pro stay rate at season level and verify in reservation";
			test_description = "Change stay details-Change only for added/removed dates, update pro stay rate at season level and verify in reservation";
			// case update custom stay rate
			test_steps.add(
					"=================== VERIFICATION OF PRO RATE AFTER UPDATE CUSTOME STAY RATE ======================");
			app_logs.info(
					"=================== VERIFICATION OF PRO RATE AFTER UPDATE CUSTOME STAY RATE ======================");

			oldDays = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			oldCheckin = CheckInDate;
			app_logs.info("CheckInDate: " + CheckInDate);

			interlLength = Integer.parseInt(interval);
			addedDays = interlLength + 2;

			oldcheckout = CheckOutDate;
			CheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckOutDate, "dd/MM/yyyy", "dd/MM/yyyy",
					+addedDays, "US/Eastern");
			app_logs.info("CheckOutDate: " + CheckOutDate);
			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);

			oldfullIntervals = 0;
			oldhalfIntervals = 0;

			oldfullIntervals = oldDays / (Integer.parseInt(interval));
			oldhalfIntervals = oldDays % (Integer.parseInt(interval));

			app_logs.info("Full Interval:" + oldhalfIntervals);
			app_logs.info("Half Interval:" + oldhalfIntervals);
			daysAdded = 0;
			if (oldhalfIntervals != 0) {
				for (int i = 1; i <= (Integer.parseInt(interval)); i++) {
					oldhalfIntervals = oldhalfIntervals + i;
					daysAdded = daysAdded + 1;
					if (oldhalfIntervals == (Integer.parseInt(interval))) {
						break;
					}
				}
			}
			app_logs.info("daysAdded: " + daysAdded);
			newdaysChange = daysChange - daysAdded;

			System.out.println("daysAdded: " + daysAdded);
			System.out.println("newdaysChange: " + newdaysChange);

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {

				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage.clear();
			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage.clear();
			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);

			try {

				if (isSeasonExistChange) {

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					double customRatePN = Double.parseDouble(customRatePerNight.get(0));
					customRatePN = customRatePN + 100;
					double childPN = Double.parseDouble(childRatePerNight.get(0));
					childPN = childPN + 50;
					double adultPN = Double.parseDouble(adultRatePerNight.get(0));
					adultPN = adultPN + 50;

					rateGrid.updateProRateAtClassLevel(driver, ReservationChangeClass, isAdditonalAdultChild.get(0),
							true, String.valueOf(customRatePN), String.valueOf(adultPN), String.valueOf(childPN),
							test_steps);
					nightlyRate.clickSaveSason(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
					nightlyRate.closeSeason(driver, test_steps);

					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
					ArrayList<String> gettest1 = new ArrayList<>();
					for (int i = 0; i < getRoomClassWithRates.size(); i++) {
						app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

						gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
						app_logs.info("arraylistSize" + gettest1.size());
						for (int j = 0; j < gettest1.size(); j++) {
							app_logs.info(gettest1.get(j));
						}

					}

				} else {
					app_logs.info("No Season For no stay details change Date");
				}

				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);

			}

			Wait.wait5Second();
			navigation.cpReservation_Backward(driver);

			baseRate.clear();
			adultCapacity.clear();
			personCapacity.clear();
			isAdditonalAdultChild.clear();
			maxAdult1.clear();
			maxPerson.clear();
			adultRate.clear();
			childRate1.clear();
			isProStayRate.clear();
			customRatePerNight.clear();
			adultRatePerNight.clear();
			childRatePerNight.clear();

			getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);

			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
			baseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);

			max_person = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(max_person);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);

			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {
				String minStayColorChange = "";

				if (isMinStayRuleChnage) {
					if (!isMinStayRuleBrokenPopComeOrNotChange) {
						minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
								ReservationChangeClass, minStayRuleValueChange);
						Utility.app_logs.info("minStayColor : " + minStayColorChange);
						try {
							assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
									"Red color lable for minstay rule is not found");
							app_logs.info("Succesfully veried the red color lable for min stay rule");
							test_steps.add("Succesfully veried the red color lable for min stay rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
								ReservationChangeClass, minStayRuleValueChange);
						try {
							assertTrue(minStayColorChange.equalsIgnoreCase("green"),
									"green color lable for minstay rule is not found");
							app_logs.info("Succesfully veried the green color lable for min stay rule");
							test_steps.add("Succesfully veried the green color lable for min stay rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				}

				String noCheckinColorChange = "";
				if (checkInColorChnage.equalsIgnoreCase("Red")) {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						Utility.app_logs.info("noCheckinColor");
						assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
								"red color lable for no check in rule is not found");
						app_logs.info("Succesfully verified the red color lable for no check in rule");
						test_steps.add("Succesfully verified the red color lable for no check in rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
								"green color lable for no check in rule is not found");
						app_logs.info("Succesfully verified the green color lable for no check in rule");
						test_steps.add("Succesfully verified the green color lable for no check in rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
								"no check in rule label is displayed");
						app_logs.info("Succesfully verified the no check in rule label not displayed");
						test_steps.add("Succesfully verified the no check in rule label not displayed");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}

				String noCheckoutColorChange = "";
				if (checkOutColorChange.equalsIgnoreCase("Red")) {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
								"red color lable for no check out rule is not found");
						app_logs.info("Succesfully verified the red color lable for no check out rule");
						test_steps.add("Succesfully verified the red color lable for no check out rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
								"green color lable for no check out rule is not found");
						app_logs.info("Succesfully verified the green color lable for no check out rule");
						test_steps.add("Succesfully verified the green color lable for no check out rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
								"no check out rule label is displayed");
						app_logs.info("Succesfully verified the no check out rule label not displayed");
						test_steps.add("Succesfully verified the no check out rule label not displayed");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}

				rateListInReservation.clear();
				app_logs.info("folioRate: " + folioRate);
				double amount = 0.00;
				// oldRates1.clear();
				app_logs.info("oldRates1: " + oldRates1);
				// oldRates1 = folioRate;
				for (int i = 0; i < folioRate.size(); i++) {

					amount = amount + (Double.parseDouble(folioRate.get(i)));
					oldRates1.add(folioRate.get(i));
				}

				app_logs.info("oldRates1: " + oldRates1);
				String totalRoomCharges = String.format("%.2f", amount);
				app_logs.info("total RoomCharges: " + totalRoomCharges);

				app_logs.info("folio name: " + folioName);
				app_logs.info("folio rate: " + folioRate);
				app_logs.info("old charges" + totalRoomCharges);
				app_logs.info("newdaysChange: " + newdaysChange);

				rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
						ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
						ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0), personCapacity.get(0),
						isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), adultRate.get(0),
						childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0), adultRatePerNight.get(0),
						childRatePerNight.get(0), folioName, oldRates1, totalRoomCharges, newdaysChange, test_steps);

				for (int i = 0; i < rateListInReservation.size(); i++) {
					app_logs.info(rateListInReservation.get(i));
				}

				if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
					app_logs.info("in else part of interval");
					String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
							+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
					test_steps.add("bug id: " + bugId);

				}

				app_logs.info("before click on select button");
				reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass, "Yes",
						"AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,

						minStayRuleValueChange);
				Wait.wait5Second();

				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);

				// verification of folio line item
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}

				reservationPage.click_Folio(driver, test_steps);
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);

				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expected: " + folioDates);
					app_logs.info("found: " + date);

					// assertEquals(date, folioDates, "Failed: Interval date is
					// mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected: " + folioName);
					app_logs.info("found: " + description);

					// assertEquals(description, folioName, "Failed: Description
					// is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");

					String expectedAmount = String.format("%.2f", Double.parseDouble(folioRate.get(i)));
					app_logs.info("expected: " + expectedAmount);
					app_logs.info("found: " + expectedAmount);
					// assertEquals(expectedAmount, expectedAmount, "Failed:
					// Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + expectedAmount);

					if (!date.equalsIgnoreCase(folioDates)) {
						test_steps.add("Failed: Folio date is mismatching!");
						break;
					}
					if (!description.equalsIgnoreCase(folioName)) {
						test_steps.add("Failed: Folio description is mismatching!");
						break;
					}
					if (!getamount.equalsIgnoreCase(expectedAmount)) {
						test_steps.add("Failed: Folio Amount is mismatching!");
						break;
					}

				}
				test_steps.add("Verified interval rate along with dates and description");

			} else {

				test_steps.add("No rate combination found");
			}
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			reservationPage.closeReservationTab(driver);
			// end here

			test_steps.clear();
			testName = "Change stay details-Change only for added/removed dates, disabled pro stay rate at season level and verify in reservation";
			test_description = "Change stay details-Change only for added/removed dates, disabled pro stay rate at season level and verify in reservation";

			oldDays = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			oldCheckin = CheckInDate;
			app_logs.info("CheckInDate: " + CheckInDate);

			interlLength = Integer.parseInt(interval);
			addedDays = interlLength + 2;

			oldcheckout = CheckOutDate;
			CheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckOutDate, "dd/MM/yyyy", "dd/MM/yyyy",
					+addedDays, "US/Eastern");
			app_logs.info("CheckOutDate: " + CheckOutDate);
			daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);

			oldfullIntervals = 0;
			oldhalfIntervals = 0;

			oldfullIntervals = oldDays / (Integer.parseInt(interval));
			oldhalfIntervals = oldDays % (Integer.parseInt(interval));

			app_logs.info("Full Interval:" + oldhalfIntervals);
			app_logs.info("Half Interval:" + oldhalfIntervals);
			daysAdded = 0;
			if (oldhalfIntervals != 0) {
				for (int i = 1; i <= (Integer.parseInt(interval)); i++) {
					oldhalfIntervals = oldhalfIntervals + i;
					daysAdded = daysAdded + 1;
					if (oldhalfIntervals == (Integer.parseInt(interval))) {
						break;
					}
				}
			}
			app_logs.info("daysAdded: " + daysAdded);
			newdaysChange = daysChange - daysAdded;

			System.out.println("daysAdded: " + daysAdded);
			System.out.println("newdaysChange: " + newdaysChange);

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			app_logs.info("Navigate rate grid");

			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			app_logs.info("select rate plan: " + RatePlanName);

			rateGrid.searchRatePlan(driver, RatePlanName);
			rateGrid.clickForRateGridCalender(driver, test_steps);
			Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

			rateGrid.expandRoomClass(driver, ReservationChangeClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {

				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChnage.clear();
			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage.clear();
			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
					channelName, daysChange);
			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);

			isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
					CheckInDate, CheckOutDate);

			try {

				if (isSeasonExistChange) {

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					double customRatePN = Double.parseDouble(customRatePerNight.get(0));
					customRatePN = customRatePN + 100;
					double childPN = Double.parseDouble(childRatePerNight.get(0));
					childPN = childPN + 50;
					double adultPN = Double.parseDouble(adultRatePerNight.get(0));
					adultPN = adultPN + 50;

					rateGrid.updateProRateAtClassLevel(driver, ReservationChangeClass, isAdditonalAdultChild.get(0),
							false, String.valueOf(customRatePN), String.valueOf(adultPN), String.valueOf(childPN),
							test_steps);

					nightlyRate.clickSaveSason(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
					nightlyRate.closeSeason(driver, test_steps);

					app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
					ArrayList<String> gettest1 = new ArrayList<>();
					for (int i = 0; i < getRoomClassWithRates.size(); i++) {
						app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

						gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
						app_logs.info("arraylistSize" + gettest1.size());
						for (int j = 0; j < gettest1.size(); j++) {
							app_logs.info(gettest1.get(j));
						}

					}

				} else {
					app_logs.info("No Season For no stay details change Date");
				}
				nightlyRate.clickSaveRatePlan(driver, test_steps);
				rateGrid.verifyLoadingGone(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);

			}

			Wait.wait5Second();
			navigation.cpReservation_Backward(driver);

			baseRate.clear();
			adultCapacity.clear();
			personCapacity.clear();
			isAdditonalAdultChild.clear();
			maxAdult1.clear();
			maxPerson.clear();
			adultRate.clear();
			childRate1.clear();
			isProStayRate.clear();
			customRatePerNight.clear();
			adultRatePerNight.clear();
			childRatePerNight.clear();

			getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);

			baserate = getRatesWithAdultsAndChild.get(0);
			app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
			baseRate.add(baserate);

			adultCapicity = getRatesWithAdultsAndChild.get(1);
			app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
			adultCapacity.add(adultCapicity);

			personCapicity = getRatesWithAdultsAndChild.get(2);
			app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
			personCapacity.add(personCapicity);

			app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
			isAdditionalAdultChild = false;
			if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

				isAdditionalAdultChild = true;
			}
			app_logs.info("additional: " + isAdditionalAdultChild);
			isAdditonalAdultChild.add(isAdditionalAdultChild);

			maxAdults = getRatesWithAdultsAndChild.get(4);
			app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
			maxAdult1.add(maxAdults);

			max_person = getRatesWithAdultsAndChild.get(5);
			app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
			maxPerson.add(max_person);

			adultsRate = getRatesWithAdultsAndChild.get(6);
			app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
			adultRate.add(adultsRate);

			childRate = getRatesWithAdultsAndChild.get(7);
			app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
			childRate1.add(childRate);

			app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
			isProRate = false;
			if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

				isProRate = true;
			}
			app_logs.info("is pro rate: " + isProRate);
			isProStayRate.add(isProRate);

			ratePerNight = getRatesWithAdultsAndChild.get(9);
			app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
			customRatePerNight.add(ratePerNight);

			adultPerNight = getRatesWithAdultsAndChild.get(10);
			app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
			adultRatePerNight.add(adultPerNight);

			childPerNight = getRatesWithAdultsAndChild.get(11);
			app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
			childRatePerNight.add(childPerNight);

			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}

			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, adult);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
			app_logs.info("path: " + path);
			app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {
				String minStayColorChange = "";
				if (isMinStayRuleChnage) {
					if (!isMinStayRuleBrokenPopComeOrNotChange) {
						minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
								ReservationChangeClass, minStayRuleValueChange);
						Utility.app_logs.info("minStayColor : " + minStayColorChange);
						try {
							assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
									"Red color lable for minstay rule is not found");
							app_logs.info("Succesfully veried the red color lable for min stay rule");
							test_steps.add("Succesfully veried the red color lable for min stay rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
								ReservationChangeClass, minStayRuleValueChange);
						try {
							assertTrue(minStayColorChange.equalsIgnoreCase("green"),
									"green color lable for minstay rule is not found");
							app_logs.info("Succesfully veried the green color lable for min stay rule");
							test_steps.add("Succesfully veried the green color lable for min stay rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				}

				String noCheckinColorChange = "";
				if (checkInColorChnage.equalsIgnoreCase("Red")) {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						Utility.app_logs.info("noCheckinColor");
						assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
								"red color lable for no check in rule is not found");
						app_logs.info("Succesfully verified the red color lable for no check in rule");
						test_steps.add("Succesfully verified the red color lable for no check in rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
								"green color lable for no check in rule is not found");
						app_logs.info("Succesfully verified the green color lable for no check in rule");
						test_steps.add("Succesfully verified the green color lable for no check in rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
								"no check in rule label is displayed");
						app_logs.info("Succesfully verified the no check in rule label not displayed");
						test_steps.add("Succesfully verified the no check in rule label not displayed");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}

				String noCheckoutColorChange = "";
				if (checkOutColorChange.equalsIgnoreCase("Red")) {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
								"red color lable for no check out rule is not found");
						app_logs.info("Succesfully verified the red color lable for no check out rule");
						test_steps.add("Succesfully verified the red color lable for no check out rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
								"green color lable for no check out rule is not found");
						app_logs.info("Succesfully verified the green color lable for no check out rule");
						test_steps.add("Succesfully verified the green color lable for no check out rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
								"no check out rule label is displayed");
						app_logs.info("Succesfully verified the no check out rule label not displayed");
						test_steps.add("Succesfully verified the no check out rule label not displayed");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}

				rateListInReservation.clear();
				app_logs.info("folioRate: " + folioRate);
				double amount = 0.00;
				// oldRates1.clear();
				// oldRates1 = folioRate;
				app_logs.info("oldRates1: " + oldRates1);
				for (int i = 0; i < folioRate.size(); i++) {

					amount = amount + (Double.parseDouble(folioRate.get(i)));
					oldRates1.add(folioRate.get(i));
				}
				app_logs.info("oldRates1: " + oldRates1);
				String totalRoomCharges = String.format("%.2f", amount);
				app_logs.info("total RoomCharges: " + totalRoomCharges);

				app_logs.info("folio name: " + folioName);
				app_logs.info("folio rate: " + folioRate);
				app_logs.info("old charges" + totalRoomCharges);
				app_logs.info("newdaysChange: " + newdaysChange);

				rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
						ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
						ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0), personCapacity.get(0),
						isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0), adultRate.get(0),
						childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0), adultRatePerNight.get(0),
						childRatePerNight.get(0), folioName, oldRates1, totalRoomCharges, newdaysChange, test_steps);

				for (int i = 0; i < rateListInReservation.size(); i++) {
					app_logs.info(rateListInReservation.get(i));
				}

				if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
					app_logs.info("in else part of interval");
					String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
							+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
					test_steps.add("bug id: " + bugId);

				}

				app_logs.info("before click on select button");
				reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass, "Yes",
						"AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,

						minStayRuleValueChange);
				Wait.wait5Second();

				reservationPage.clickSaveButton(driver);
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);

				// verification of folio line item
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				reservationPage.click_Folio(driver, test_steps);
				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);

				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("expected: " + folioDates);
					app_logs.info("found: " + date);

					// assertEquals(date, folioDates, "Failed: Interval date is
					// mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("expected: " + folioName);
					app_logs.info("found: " + description);

					// assertEquals(description, folioName, "Failed: Description
					// is mismatching in folio line item!");

					String getamount = folio.itemAmount(driver, category, i + 1);
					getamount = folio.splitStringBySign(getamount, "$");

					String expectedAmount = String.format("%.2f", Double.parseDouble(folioRate.get(i)));
					app_logs.info("expected: " + expectedAmount);
					app_logs.info("found: " + expectedAmount);
					// assertEquals(expectedAmount, expectedAmount, "Failed:
					// Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + expectedAmount);

					if (!date.equalsIgnoreCase(folioDates)) {
						test_steps.add("Failed: Folio date is mismatching!");
						break;
					}
					if (!description.equalsIgnoreCase(folioName)) {
						test_steps.add("Failed: Folio description is mismatching!");
						break;
					}
					if (!getamount.equalsIgnoreCase(expectedAmount)) {
						test_steps.add("Failed: Folio Amount is mismatching!");
						break;
					}

				}
				test_steps.add("Verified interval rate along with dates and description");

		}


	}

	private void verificationDepositWithPolicy(String paymentType, String policyName, String amount,
			String historyPaymentType) throws InterruptedException, ParseException, java.text.ParseException {
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, test_steps);
		reservationPage.verify_FolioPayment(driver, test_steps, amount);
		getTest_Steps = reservationPage.clickOnDetails(driver);
		test_steps.addAll(getTest_Steps);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, test_steps, policyName, "");
		reservationPage.verifyTripSummaryPaidAmount(driver, test_steps, amount);
		reservationPage.click_History(driver, test_steps);
		reservationPage.verifyDepositAtHistoryTab(driver, test_steps, amount, historyPaymentType);
	}

	private void verificationDepoistWithoutPolicy(String paymentType, String date, String policyName)
			throws InterruptedException, ParseException, java.text.ParseException {
		reservationPage.verifyNoPaymentAtFolioLineItem(driver, test_steps, date, paymentType);
		getTest_Steps = reservationPage.clickOnDetails(driver);
		test_steps.addAll(getTest_Steps);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, test_steps, policyName, "");
	}

	private void verificationCheckinWithPolicy(String paymentType, String policyName, String amount, String payment,
			String cardFormat) throws InterruptedException, ParseException, java.text.ParseException {
		reservationPage.verifyCheckInPolicy(driver, test_steps, policyName, "");
		reservationPage.clickFolio(driver, test_steps);
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, test_steps);
		reservationPage.verify_FolioPayment(driver, test_steps, payment);
		reservationPage.click_History(driver, test_steps);
		reservationPage.verifyChekInReservationOnHistoryTab(driver, test_steps);
		reservationPage.verifyHistoryWithCapturedPayment(driver, test_steps, amount, cardFormat, paymentType);
	}

	private void verificationCheckinWithoutPolicy(String policyName)
			throws ParseException, InterruptedException, java.text.ParseException {
		reservationPage.verifyCheckInPolicy(driver, test_steps, policyName, "");
		reservationPage.click_History(driver, test_steps);
		reservationPage.verifyChekInReservationOnHistoryTab(driver, test_steps);
	}

	private void completeCheckInIfPolicyExist(double balanceAmt, String amountToBePaid) throws Exception {
		if (balanceAmt > 0.00) {
			reservationPage.clickOnProceedToCheckInPaymentButton(driver, test_steps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			reservationPage.verifyAmountOnPaymentScreen(driver, amountToBePaid, test_steps);
			reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			reservationPage.clickCloseButtonOfSuccessCheckInModelPopup(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
		} else {
			reservationPage.clickOnConfirmCheckInButton(driver, test_steps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}

	public void ChangeStayDetailsRecalculateAtSeasonLevelAndVerifySaveButtonEnabled(String RatePlanName, String CheckInDate, String CheckOutDate,
			String ReservationChangeClass, String channelName, String ReservationRoomClasses, String ReservationType,
			String adult, String children, String PromoCode, String interval) throws Exception {
		test_steps.clear();
		testName = "Change stay details-Recalculate, rate update at season level";
		test_description = "Change stay details-Recalculate, rate update at season level and verify in recalculate without change"
				+ "checkin, checkout, adults and children";

		ArrayList<String> folioRate = rateListInReservation.get(0);
		ArrayList<String> intervalDates = rateListInReservation.get(1);
		app_logs.info("folioRate: " + folioRate);
		app_logs.info("intervalDates: " + intervalDates);
		String category = "Room Charge";
		int numberOfDys = 0;
		// reservationPage.closeReservationTab(driver);
		app_logs.info("close reservation tabe");

		String baserate = "";
		String adultCapicity = "";
		String personCapicity = "";
		boolean isAdditionalAdultChild = false;
		String maxAdults = "";
		String max_person = "";
		String adultsRate = "";
		String childRate = "";
		boolean isProRate = false;
		String ratePerNight = "";
		String adultPerNight = "";
		String childPerNight = "";
		String path = "";

		test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
		app_logs.info("=================== NAVIGATE TO RATE GRID ======================");

		navigation.Inventory(driver);
		test_steps.add("Navigate Inventory");
		app_logs.info("Navigate Inventory");

		navigation.RatesGrid(driver);
		test_steps.add("Navigated to RatesGrid");
		app_logs.info("Navigate rate grid");

		test_steps.add("=================== Selecting the Rate Plan ======================");
		app_logs.info("=================== Selecting the Rate Plan ======================");
		rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
		app_logs.info("select rate plan: " + RatePlanName);

		rateGrid.searchRatePlan(driver, RatePlanName);
		// need to update taking too much time
		rateGrid.clickForRateGridCalender(driver, test_steps);
		Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

		rateGrid.expandRoomClass(driver, ReservationChangeClass);
		try {
			rateGrid.expandChannel(driver, test_steps, ReservationChangeClass, channelName);

		} catch (Exception e) {
			test_steps.add("Failed: No channel is found");
		}

		daysChange = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeClass,
				channelName, daysChange);
		minruleChange = minStayRuleChnage;
		app_logs.info("get min stay rule in : " + minruleChange);
		app_logs.info("get min stay rule size : " + minruleChange.size());
		app_logs.info("get min stay rule highest value : " + minruleChange.get(minruleChange.size() - 1));

		Collections.sort(minruleChange);
		Utility.app_logs.info("minruleChnage : " + minruleChange);

		minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

		if (minStayRuleValueChange > 0) {
			isMinStayRuleChnage = true;
			isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
					minruleChange, minStayRuleValueChange, daysChange);
		}

		noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeClass,
				channelName, daysChange);

		Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

		checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
				CheckInDate, CheckOutDate);

		Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

		noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeClass,
				channelName, daysChange);

		Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

		checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
				noCheckOutRuleChnage, CheckInDate, CheckInDate);
		Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

		rateGrid.clickOnEditRatePlan(driver);
		folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
		nightlyRate.switchCalendarTab(driver, test_steps);

		boolean isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
				CheckInDate, CheckInDate);
		// change rate value
		if (isSeasonExistChange) {
			nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);

			// here get interval info and update rate value
			double newRate = Double.parseDouble(oldbaseRate.get(0));
			newRate = newRate + 50;
			double newChildRate = Double.parseDouble(oldchildRate1.get(0));
			double newAdultRate = Double.parseDouble(oldadultRate.get(0));
			rateGrid.enterRoomClassRates(driver, ReservationChangeClass, "" + newRate,
					oldisAdditonalAdultChild.get(0), "" + newAdultRate, "" + newChildRate, test_steps);

			double customRatePN = Double.parseDouble(oldcustomRatePerNight.get(0));
			double childPN = Double.parseDouble(oldchildRatePerNight.get(0));
			double adultPN = Double.parseDouble(oldadultRatePerNight.get(0));

			rateGrid.enterRoomClassRates(driver, ReservationChangeClass, "" + newRate, false, "", "", test_steps);
			nightlyRate.clickSaveSason(driver, test_steps);

			nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);

			getRoomClassWithRates.put(ReservationRoomClasses, rateGrid
					.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationChangeClass, test_steps));
			nightlyRate.closeSeason(driver, test_steps);

			app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));
			ArrayList<String> gettest1 = new ArrayList<>();
			for (int i = 0; i < getRoomClassWithRates.size(); i++) {
				app_logs.info(getRoomClassWithRates.get(ReservationChangeClass));

				gettest1 = getRoomClassWithRates.get(ReservationChangeClass);
				app_logs.info("arraylistSize" + gettest1.size());
				for (int j = 0; j < gettest1.size(); j++) {
					app_logs.info(gettest1.get(j));
				}

			}

		} else {
			app_logs.info("No Season For no stay details change Date");
		}
		rateGrid.clickOnSaveratePlan(driver);
		rateGrid.verifyLoadingGone(driver);
		rateGrid.closeOpendTabInMainMenu(driver);

		navigation.cpReservation_Backward(driver);
		test_steps.add("Back to resevations page");
		driver.navigate().refresh();
		Wait.wait5Second();
		reservationPage.Search_ResNumber_And_Click(driver, reservation);
		reservationPage.closeReservationTab(driver);
		reservationPage.Search_ResNumber_And_Click(driver, reservation);

		baseRate.clear();
		adultCapacity.clear();
		personCapacity.clear();
		isAdditonalAdultChild.clear();
		maxAdult1.clear();
		maxPerson.clear();
		adultRate.clear();
		childRate1.clear();
		isProStayRate.clear();
		customRatePerNight.clear();
		adultRatePerNight.clear();
		childRatePerNight.clear();

		ArrayList<String> getRatesWithAdultsAndChild = getRoomClassWithRates.get(ReservationRoomClasses);

		baserate = getRatesWithAdultsAndChild.get(0);
		app_logs.info("base rate: " + getRatesWithAdultsAndChild.get(0));
		baseRate.add(baserate);

		adultCapicity = getRatesWithAdultsAndChild.get(1);
		app_logs.info("Capacity adult: " + getRatesWithAdultsAndChild.get(1));
		adultCapacity.add(adultCapicity);

		personCapicity = getRatesWithAdultsAndChild.get(2);
		app_logs.info("Capacity person: " + getRatesWithAdultsAndChild.get(2));
		personCapacity.add(personCapicity);

		app_logs.info("additional: " + getRatesWithAdultsAndChild.get(3));
		isAdditionalAdultChild = false;
		if (getRatesWithAdultsAndChild.get(3).equalsIgnoreCase("yes")) {

			isAdditionalAdultChild = true;
		}
		app_logs.info("additional: " + isAdditionalAdultChild);
		isAdditonalAdultChild.add(isAdditionalAdultChild);

		maxAdults = getRatesWithAdultsAndChild.get(4);
		app_logs.info("max Adult: " + getRatesWithAdultsAndChild.get(4));
		maxAdult1.add(maxAdults);

		max_person = getRatesWithAdultsAndChild.get(5);
		app_logs.info("max person: " + getRatesWithAdultsAndChild.get(5));
		maxPerson.add(max_person);

		adultsRate = getRatesWithAdultsAndChild.get(6);
		app_logs.info("Adult rate: " + getRatesWithAdultsAndChild.get(6));
		adultRate.add(adultsRate);

		childRate = getRatesWithAdultsAndChild.get(7);
		app_logs.info("Child rate: " + getRatesWithAdultsAndChild.get(7));
		childRate1.add(childRate);

		app_logs.info("is pro rate: " + getRatesWithAdultsAndChild.get(8));
		isProRate = false;
		if (getRatesWithAdultsAndChild.get(8).equalsIgnoreCase("yes")) {

			isProRate = true;
		}
		app_logs.info("is pro rate: " + isProRate);
		isProStayRate.add(isProRate);

		ratePerNight = getRatesWithAdultsAndChild.get(9);
		app_logs.info("rate per ngith: " + getRatesWithAdultsAndChild.get(9));
		customRatePerNight.add(ratePerNight);

		adultPerNight = getRatesWithAdultsAndChild.get(10);
		app_logs.info("adult per night: " + getRatesWithAdultsAndChild.get(10));
		adultRatePerNight.add(adultPerNight);

		childPerNight = getRatesWithAdultsAndChild.get(11);
		app_logs.info("child per night: " + getRatesWithAdultsAndChild.get(11));
		childRatePerNight.add(childPerNight);

		// first verify edit rate with same date
		if (ReservationType.equalsIgnoreCase("MRB")) {
			reservationPage.selectMRBLastFolio(driver, test_steps);
		}

		String[] checkin = CheckInDate.split("\\|");
		String[] checkout_recalculate = CheckOutDate.split("\\|");
		HashMap<String, String> roomChargesFolio = null;

		// here click on details tab
		if (!ReservationType.equalsIgnoreCase("MRB")) {
			reservationPage.ClickEditStayInfo(driver, test_steps);
			reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
		} else {
			reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
		}

		Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
		reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
		reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
		reservationPage.enter_Adults(driver, test_steps, adult);
		reservationPage.enter_Children(driver, test_steps, children);
		reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
		reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
		reservationPage.clickOnFindRooms(driver, test_steps);

		path = "(//span[text()='" + ReservationChangeClass + "'])[2]";
		app_logs.info("path: " + path);
		app_logs.info("isRoomClassAvailableChange: " + isRoomClassAvailableChange);

		if (driver.findElements(By.xpath("(//span[text()='" + ReservationChangeClass + "'])[2]")).size() > 0
				&& isRoomClassAvailableChange) {

			String minStayColorChange = "";
			// if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange &&
			// isSeasonExist) {
			// if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange &&
			// isSeasonExistChange) {
			if (isSeasonExistChange) {

				if (isMinStayRuleChnage) {
					if (!isMinStayRuleBrokenPopComeOrNotChange) {
						minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
								ReservationChangeClass, minStayRuleValueChange);
						Utility.app_logs.info("minStayColor : " + minStayColorChange);
						try {
							assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
									"Red color lable for minstay rule is not found");
							app_logs.info("Succesfully veried the red color lable for min stay rule");
							test_steps.add("Succesfully veried the red color lable for min stay rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					} else {
						minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
								ReservationChangeClass, minStayRuleValueChange);
						try {
							assertTrue(minStayColorChange.equalsIgnoreCase("green"),
									"green color lable for minstay rule is not found");
							app_logs.info("Succesfully veried the green color lable for min stay rule");
							test_steps.add("Succesfully veried the green color lable for min stay rule");
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				}

				String noCheckinColorChange = "";
				if (checkInColorChnage.equalsIgnoreCase("Red")) {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						Utility.app_logs.info("noCheckinColor");
						assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
								"red color lable for no check in rule is not found");
						app_logs.info("Succesfully verified the red color lable for no check in rule");
						test_steps.add("Succesfully verified the red color lable for no check in rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
								"green color lable for no check in rule is not found");
						app_logs.info("Succesfully verified the green color lable for no check in rule");
						test_steps.add("Succesfully verified the green color lable for no check in rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					try {
						noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
								"no check in rule label is displayed");
						app_logs.info("Succesfully verified the no check in rule label not displayed");
						test_steps.add("Succesfully verified the no check in rule label not displayed");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}

				String noCheckoutColorChange = "";
				if (checkOutColorChange.equalsIgnoreCase("Red")) {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
								"red color lable for no check out rule is not found");
						app_logs.info("Succesfully verified the red color lable for no check out rule");
						test_steps.add("Succesfully verified the red color lable for no check out rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
								"green color lable for no check out rule is not found");
						app_logs.info("Succesfully verified the green color lable for no check out rule");
						test_steps.add("Succesfully verified the green color lable for no check out rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					try {
						noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
								ReservationChangeClass);
						assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
								"no check out rule label is displayed");
						app_logs.info("Succesfully verified the no check out rule label not displayed");
						test_steps.add("Succesfully verified the no check out rule label not displayed");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}

				rateListInReservation.clear();
				rateListInReservation = reservationPage.intervalRateVerificationInChangeStayDetails(driver,
						ratesConfig.getProperty("defaultDateFormat"), CheckInDate, CheckOutDate, adult, children,
						ReservationRoomClasses, interval, baseRate.get(0), adultCapacity.get(0),
						personCapacity.get(0), isAdditonalAdultChild.get(0), maxAdult1.get(0), maxPerson.get(0),
						adultRate.get(0), childRate1.get(0), isProStayRate.get(0), customRatePerNight.get(0),
						adultRatePerNight.get(0), childRatePerNight.get(0), folioName, test_steps);

				for (int i = 0; i < rateListInReservation.size(); i++) {
					app_logs.info(rateListInReservation.get(i));
					// test_steps.add("Rates: "+rateListInReservation)
				}

				if (rateListInReservation.get(rateListInReservation.size() - 1).get(0).equalsIgnoreCase("no")) {
					app_logs.info("in else part of interval");
					String bugId = "<a href='https://innroad.atlassian.net/browse/RATES-2170' target='_blank'>"
							+ "Failed: Interval rate when pro rated amount is displaying wrong from new reservation and on selecting the room class rate is updating correctly on trip summary.<br>Click here for more info: RATES-2170</a>";
					test_steps.add("bug id: " + bugId);

				}

				reservationPage.selectRoomWithRatePlanRulesValidation(driver, test_steps, ReservationChangeClass,
						"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
						minStayRuleValueChange);
			}
			app_logs.info("before click on save button");
			Wait.wait5Second();
			boolean isButtonEnabled = reservationPage.clickSaveButton(driver);
			if (isButtonEnabled) {
				reservationPage.verifyPopupChangeInCost1(driver, test_steps);
				reservationPage.click_Folio(driver, test_steps);
				if (ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}

				reservationPage.includeTaxesinLineItems(driver, getTest_Steps, false);

				intervalDates.clear();
				intervalDates = rateListInReservation.get(1);
				folioRate.clear();
				folioRate = rateListInReservation.get(0);
				app_logs.info("folioRate: " + folioRate);
				app_logs.info("intervalDates: " + intervalDates);
				category = "Room Charge";
				test_steps.add("=====Verification of interval rate in folio=====");
				for (int i = 0; i < intervalDates.size(); i++) {

					String folioDates = Utility.parseDate(intervalDates.get(i),
							ratesConfig.getProperty("defaultDateFormat"), "EE MMM dd, yyyy");

					String date = folio.itemDate(driver, category, i + 1);
					app_logs.info("ecpected: " + folioDates);
					app_logs.info("found: " + date);

					assertEquals(date, folioDates, "Failed: Interval date is mismatching in folio line item!");

					String description = folio.itemDetailsDescroption(driver, category, i + 1);
					app_logs.info("ecpected: " + folioName);
					app_logs.info("found: " + description);

					assertEquals(description, folioName, "Failed: Description is mismatching in folio line item!");

					String amount = folio.itemAmount(driver, category, i + 1);
					amount = folio.splitStringBySign(amount, "$");
					double dbAmount = Double.parseDouble(folioRate.get(i));
					String strAmount = String.format("%.2f", dbAmount);
					app_logs.info("expected: " + strAmount);
					app_logs.info("found: " + amount);
					assertEquals(amount, "" + strAmount, "Failed: Amount is mismatching in folio line item!");
					test_steps.add(folioDates + "         " + folioName + "           " + strAmount);

				}
				test_steps.add("Verified interval rate along with dates and description");
				reservationPage.click_DeatilsTab(driver, test_steps);

			} else {
				reservationPage.closeReservationSearch(driver, test_steps);
				test_steps.add("Failed: after updated base rate, save button is disbaled in Change Stay Details");
			}

		} else {
			test_steps.add("No rate combination found");
		}

		Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

	}
	private void completeCheckInIfPolicyDoesntExist(double balanceAmt) throws InterruptedException {
		if (balanceAmt > 0.00 || balanceAmt == 0.00) {
			reservationPage.clickOnConfirmCheckInButton(driver, test_steps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("Sheet1", envLoginExcel);
	}

	// @AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
}
