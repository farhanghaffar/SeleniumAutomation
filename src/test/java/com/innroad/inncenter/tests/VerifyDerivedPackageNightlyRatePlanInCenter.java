package com.innroad.inncenter.tests;

import static org.junit.Assert.assertNotEquals;
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
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.CancellationPolicy;
import com.innroad.inncenter.model.PackageProduct;
import com.innroad.inncenter.model.RatesGridChannelRatesRules;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import bsh.ParseException;

public class VerifyDerivedPackageNightlyRatePlanInCenter extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	//test_name = this.getClass().getSimpleName().trim();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		 test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, envLoginExcel))
			throw new SkipException("Skipping the test - " + test_name);
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
			reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
		} else {
			reservationPage.clickOnConfirmCheckInButton(driver, test_steps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}

	private void completeCheckInIfPolicyDoesntExist(double balanceAmt) throws InterruptedException {
		if (balanceAmt > 0.00 || balanceAmt == 0.00) {
			reservationPage.clickOnConfirmCheckInButton(driver, test_steps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}

	private void getData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
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
	DerivedRate derivedRate = new DerivedRate();
	RatePackage ratePackage = new RatePackage();
	ArrayList<String> reservationRoomClassesList = new ArrayList<String>();
	String currencyName = null;
	String currencySign = "$";
	boolean israteplanExist = false;
	String channelName = "innCenter";

	int days = 0;
	boolean isRoomClassAvailableChange = true;
	boolean isRoomClassAvailable = true;
	List<Date> dates = new ArrayList<Date>();
	List dates1 = new ArrayList();
	ArrayList<String> allDatesBW = null;

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
	ArrayList<String> seasonDepositPolicy = new ArrayList<String>();
	ArrayList<String> seasonCheckInPolicy = new ArrayList<String>();
	ArrayList<String> getRatesWithAdultsAndChild = new ArrayList<>();
	CancellationPolicy foundBestPolicyAmount = null;
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

	ArrayList<String> RoomAbbri = new ArrayList<String>();
	ArrayList<String> Rooms = new ArrayList<String>();

	boolean isSeasonExist = true;

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

	HashMap<String, String> getRateLevelPolicy = new HashMap<String, String>();
	HashMap<String, String> getSessionLevelPolicy = new HashMap<String, String>();
	HashMap<String, ArrayList<String>> getRoomClassWithRates = new HashMap<>();

	String Salutation = "Mr.";
	String GuestFirstName = "Test Res";
	String GuestLastName = Utility.generateRandomString();
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
	String CardExpDate;
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
	String noOfNightsGroupBlock = "1";

	String reservation = null;
	String status = null;
	boolean isReservationCreated = false;
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

	String reervationNoShowPolicy = "No Policy";
	String reervationDepositPolicy = "No Policy";
	String reervationCheckInPolicy = "No Policy";
	ArrayList<PackageProduct> products = null;
	HashMap<String, String> getRates = new HashMap<String, String>();
	HashMap<String, String> getExAdult = new HashMap<String, String>();
	HashMap<String, String> getExChild = new HashMap<String, String>();


//	boolean isRoomClassAvailableChange = true;

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
	boolean restricrionsBookingWindowChange = false;
	boolean restricrionsLengthOfStayChange = false;
	boolean isBookinWindowChange = false;
	String PromoCodeChange = "";
//	HashMap<String, String> getRateLevelPolicyChange = new HashMap<String, String>();

	boolean isVerifyPoliciesChange = true;
	HashMap<String, String> capacityAdultChange = new HashMap<String, String>(),
			capacityChildChange = new HashMap<String, String>(), maxAdultChange = new HashMap<String, String>(),
			maxChildChange = new HashMap<String, String>();
	HashMap<String, String> getRatesChange = new HashMap<String, String>();
	HashMap<String, String> getExAdultChange = new HashMap<String, String>();
	HashMap<String, String> getExChildChange = new HashMap<String, String>();
	HashMap<String, String> getRatesPerNightChannelsChange = new HashMap<String, String>();
	List<Date> datesChnage = new ArrayList<Date>();

	
	HashMap<String, String> capacityAdult = new HashMap<String, String>(),
			capacityChild = new HashMap<String, String>(), maxAdult = new HashMap<String, String>(),
			maxChild = new HashMap<String, String>();
	
	
	boolean isDerivedRateplanExist = false;
	
	ArrayList<String> capacityAdult1 = new ArrayList<String>();
	ArrayList<String> capacityChild1 = new ArrayList<String>();
	ArrayList<String> ratePlanAdults = new ArrayList<String>();
	ArrayList<String> ratePlanChilds = new ArrayList<String>();
	ArrayList<HashMap<String, String>> ratesList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> exAdultList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> exChildList = new ArrayList<HashMap<String, String>>();
	@Test(dataProvider = "getData")
	public void verifyDerivedPackageNightlyRatePlanInCenter(String delim, String packageRatePlanName,
			String derivedRatePlanName, String productsName,
			String ReservationType, String CheckInDate, String CheckOutDate, String adult, String children,
			String ReservationRoomClasses, String ActionOnReservation,

			String ReservationChangeRoomClass, String ChangeResStartDate, String ChangeResEndDate, String ChangeAdult,
			String ChangeChildren,

			String isAccountPolicyApplicable, String isAccountPolicyCreate, String FeesGuestMustPay,
			String PercentOfFee, String FeeChargesType, String RulesUpdateType, String RulesUpdateStartDate,
			String RulesUpdateEndDate, String Type_RulesUpdate, String RuleMinStayValue_RulesUpdate,
			String Days_RulesUpdate, String RatesUpdateType, String checkInDate_RatesUpdate,
			String checkOutDate_RatesUpdate, String Days_RatesUpdate, String updateRatesType,
			String isUpdateRateByRoomClass, String nightlyRate_RatesUpdate, String additionalAdults_RatesUpdate,
			String additionalChild_RatesUpdate
			, String DrivedRulesUpdateType,
			String DrivedRulesUpdateStartDate, String DrivedRulesUpdateEndDate, String DrivedisDays_RulesUpdate,
			String DrivedType_RulesUpdate, String DrivedRuleMinStayValue_RulesUpdate, String DrivedRatesUpdateType,
			String DrivedcheckInDate_RatesUpdate, String DrivedcheckOutDate_RatesUpdate,
			String DrivedisDays_RatesUpdate, String DrivedupdateRatesType, String DrivedisUpdateRateByRoomClass,
			String DrivednightlyRate_RatesUpdate, String DrivedadditionalAdults_RatesUpdate,
			String DrivedadditionalChild_RatesUpdate) throws Exception {

		/**/
		Utility.DELIM = delim;

		ArrayList<String> Days_RulesUpdateList = Utility.convertTokenToArrayList(Days_RulesUpdate, delim);

		String isMon_RulesUpdate = Days_RulesUpdateList.get(0);
		String isTue_RulesUpdate = Days_RulesUpdateList.get(1);
		String isWed_RulesUpdate = Days_RulesUpdateList.get(2);
		String isThu_RulesUpdate = Days_RulesUpdateList.get(3);
		String isFri_RulesUpdate = Days_RulesUpdateList.get(4);
		String isSat_RulesUpdate = Days_RulesUpdateList.get(5);
		String isSun_RulesUpdate = Days_RulesUpdateList.get(6);

		ArrayList<String> Days_RatesUpdateList = Utility.convertTokenToArrayList(Days_RatesUpdate, delim);

		String isMonday_RatesUpdate = Days_RatesUpdateList.get(0);
		String isTuesday_RatesUpdate = Days_RatesUpdateList.get(1);
		String isWednesday_RatesUpdate = Days_RatesUpdateList.get(2);
		String isThursday_RatesUpdate = Days_RatesUpdateList.get(3);
		String isFriday_RatesUpdate = Days_RatesUpdateList.get(4);
		String isSaturday_RatesUpdate = Days_RatesUpdateList.get(5);
		String isSunday_RatesUpdate = Days_RatesUpdateList.get(6);
		
		ArrayList<String> DrivedisDays_RulesUpdateList = Utility.convertTokenToArrayList(DrivedisDays_RulesUpdate,
				delim);

		String DrivedisMon_RulesUpdate = DrivedisDays_RulesUpdateList.get(0);
		String DrivedisTue_RulesUpdate = DrivedisDays_RulesUpdateList.get(1);
		String DrivedisWed_RulesUpdate = DrivedisDays_RulesUpdateList.get(2);
		String DrivedisThu_RulesUpdate = DrivedisDays_RulesUpdateList.get(3);
		String DrivedisFri_RulesUpdate = DrivedisDays_RulesUpdateList.get(4);
		String DrivedisSat_RulesUpdate = DrivedisDays_RulesUpdateList.get(5);
		String DrivedisSun_RulesUpdate = DrivedisDays_RulesUpdateList.get(6);

		ArrayList<String> DrivedisDays_RatesUpdateList = Utility.convertTokenToArrayList(DrivedisDays_RatesUpdate,
				delim);

		String DrivedisMonday_RatesUpdate = DrivedisDays_RatesUpdateList.get(0);
		String DrivedisTuesday_RatesUpdate = DrivedisDays_RatesUpdateList.get(1);
		String DrivedisWednesday_RatesUpdate = DrivedisDays_RatesUpdateList.get(2);
		String DrivedisThursday_RatesUpdate = DrivedisDays_RatesUpdateList.get(3);
		String DrivedisFriday_RatesUpdate = DrivedisDays_RatesUpdateList.get(4);
		String DrivedisSaturday_RatesUpdate = DrivedisDays_RatesUpdateList.get(5);
		String DrivedisSunday_RatesUpdate = DrivedisDays_RatesUpdateList.get(6);

		HashMap<String, String> packageData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx", "CreatePackageV2");

		String productsCategory = packageData.get("productCategory");
		String productsCost = packageData.get("productsCost");
		String calculationMethodOne = packageData.get("calculationMethodOne");
		String calculationMethodTwo = packageData.get("calculationMethodTwo");
		String isSellOnBookingEngine = packageData.get("isSellOnBookingEngine");
		String bookingEngineAvailabilityOption = packageData.get("bookingEngineAvailabilityOption");
		String productsRoomClass = packageData.get("productsRoomClass");
		String productsDescription = packageData.get("productDescription");
		String productsPolicy = packageData.get("productPolicy");

		HashMap<String, String> packageRateData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx", "CreatePackageRateV2");

		String folioDisplayName = packageRateData.get("folioDisplayName");
		String description = packageRateData.get("description");
		String channels = packageRateData.get("channels");
		String roomClasses = packageRateData.get("roomClasses");
		String isRatePlanRistrictionReq = packageRateData.get("isRatePlanRistrictionReq");
		String ristrictionType = packageRateData.get("ristrictionType");
		String isMinStay = packageRateData.get("isMinStay");
		String minNights = packageRateData.get("minNights");
		String isMaxStay = packageRateData.get("isMaxStay");
		String maxNights = packageRateData.get("maxNights");
		String isMoreThanDaysReq = packageRateData.get("isMoreThanDaysReq");
		String moreThanDaysCount = packageRateData.get("moreThanDaysCount");
		String isWithInDaysReq = packageRateData.get("isWithInDaysReq");
		String withInDaysCount = packageRateData.get("withInDaysCount");
		String promoCode = packageRateData.get("promoCode");
		String isPolicesReq = packageRateData.get("isPolicesReq");
		String policiesType = packageRateData.get("policiesType");
		String policiesName = packageRateData.get("policiesName");
		String productName = packageRateData.get("productName");
		String productAmount = packageRateData.get("productAmount");
		String productFirstCalculationMethod = packageRateData.get("productFirstCalculationMethod");
		String productSecondCalculationMethod = packageRateData.get("productSecondCalculationMethod");
		String rateType = packageRateData.get("rateType");
		String intervalRatePlanIntervalValue = packageRateData.get("intervalRatePlanIntervalValue");
		String isDefaultProRateChecked = packageRateData.get("isDefaultProRateChecked");
		String isProRateStayInSeason = packageRateData.get("isProRateStayInSeason");
//			String seasonDelim = packageRateData.get("seasonDelim"); 
		String seasonName = packageRateData.get("seasonName");
		String seasonStartDate = packageRateData.get("seasonStartDate");
		String seasonEndDate = packageRateData.get("seasonEndDate");
//			String seasonDuration = packageRateData.get("seasonDuration"); 
		String isMonDay = packageRateData.get("isMonDay");
		String isTueDay = packageRateData.get("isTueDay");
		String isWednesDay = packageRateData.get("isWednesDay");
		String isThursDay = packageRateData.get("isThursDay");
		String isFriday = packageRateData.get("isFriday");
		String isSaturDay = packageRateData.get("isSaturDay");
		String isSunDay = packageRateData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = packageRateData.get("isAdditionalChargesForChildrenAdults");
		String ratePerNight = packageRateData.get("ratePerNight");
		String maxAdults = packageRateData.get("maxAdults");
		String maxPersons = packageRateData.get("maxPersons");
		String additionalAdultsPerNight = packageRateData.get("additionalAdultsPerNight");
		String additionalChildPerNight = packageRateData.get("additionalChildPerNight");
		String isAddRoomClassInSeason = packageRateData.get("isAddRoomClassInSeason");
		String extraRoomClassesInSeason = packageRateData.get("extraRoomClassesInSeason");
		String extraRoomClassRatePerNight = packageRateData.get("extraRoomClassRatePerNight");
		String extraRoomClassMaxAdults = packageRateData.get("extraRoomClassMaxAdults");
		String extraRoomClassMaxPersons = packageRateData.get("extraRoomClassMaxPersons");
		String extraRoomClassAdditionalAdultsPerNight = packageRateData.get("extraRoomClassAdditionalAdultsPerNight");
		String extraRoomClassAdditionalChildPerNight = packageRateData.get("extraRoomClassAdditionalChildPerNight");
		String isProRateInRoomClass = packageRateData.get("isProRateInRoomClass");
		String isCustomPerNight = packageRateData.get("IsCustomPerNight");
		String customRoomClasses = packageRateData.get("CustomRoomClasses");
		String customRatePerNight = packageRateData.get("CustomRatePerNight");
		String isCustomRatePerNightAdultandChild = packageRateData.get("isCustomRatePerNightAdultandChild");
		String customRateChildPerNight = packageRateData.get("CustomRateChildPerNight");
		String customRateAdultdPerNight = packageRateData.get("CustomRateAdultdPerNight");
		String roomClassesWithProRateUnchecked = packageRateData.get("roomClassesWithProRateUnchecked");
		String isSeasonLevelRules = packageRateData.get("isSeasonLevelRules");
		String isAssignRulesByRoomClassCreateSheet = packageRateData.get("isAssignRulesByRoomClass");
		String seasonRuleSpecificRoomClasses = packageRateData.get("seasonRuleSpecificRoomClasses");
		String seasonRuleType = packageRateData.get("seasonRuleType");
		String seasonRuleMinStayValue = packageRateData.get("seasonRuleMinStayValue");
		String isSeasonRuleOnMonday = packageRateData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday = packageRateData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday = packageRateData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday = packageRateData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday = packageRateData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday = packageRateData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday = packageRateData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies = packageRateData.get("isSeasonPolicies");
		String isAssignPoliciesByRoomClass = packageRateData.get("isAssignPoliciesByRoomClass");
		String seasonPolicySpecificRoomClasses = packageRateData.get("seasonPolicySpecificRoomClasses");
		String seasonPolicyType = packageRateData.get("seasonPolicyType");
		String seasonPolicyValues = packageRateData.get("seasonPolicyValues");

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
		
		if (!ReservationType.equalsIgnoreCase("MRB")) {
			days = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		}

		int daysChnage = 0;
		if (ActionOnReservation.equalsIgnoreCase("Recalculate") || ActionOnReservation.equalsIgnoreCase("No Change")
				|| ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
			daysChnage = Utility.getNumberofDays(ChangeResStartDate, ChangeResEndDate);
		}
		String[] RoomClass = roomClasses.split("\\|");
		app_logs.info(packageRateData.get("IsCustomPerNight"));
		test_name = "Package Rate Plan Creation And Verify in Rate Grid";
		test_description = "Package Rate Plan Creation And Verify in Rate Grid";
		test_catagory = "VerifyPackageRatePlanInInnCenter";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");

		try {
			test_name = "Application Login";
			test_description = "Application Login";
			test_steps.clear();
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}
			driver = getDriver();
			loginRateV2(driver);
//			Login login = new Login();
//			try {
//				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
//			} catch (Exception e) {
//				driver.switchTo().alert().accept();
//				Actions actions = new Actions(driver);
//				actions.sendKeys(Keys.ENTER);
//				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
//			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
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
		boolean isComeFromRoomClass = false;
		if (ReservationType.equalsIgnoreCase("MRB") || ReservationType.equalsIgnoreCase("tapechart")
				|| ActionOnReservation.equalsIgnoreCase("Cancellation")) {
			test_steps.add("=================== GETTING THE ROOM CLASS ABBRIVATION ======================");
			app_logs.info("=================== GETTING THE ROOM CLASS ABBRIVATION ======================");
			try {
				navigation.setup(driver);
				navigation.RoomClass(driver);
				RoomAbbri = rc.getAbbrivation(driver, delim, ReservationRoomClasses, test_steps);
				app_logs.info(RoomAbbri);
				isComeFromRoomClass = true;
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get room class abbrivations", test_name, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get room class abbrivations", test_name, "Room class", driver);
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
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create new policy for account", test_name, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create new policy for account", test_name, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String randomString = Utility.generateRandomString();
//		packageRatePlanName = packageRatePlanName + randomString;
//		folioDisplayName = folioDisplayName + randomString;
		String timeZone = "America/New_York";
		String dateFormat = "MM/dd/yyyy";
		String calendarTodayDay = "today";

		String rateTypeSummary = null;
		String parentSeasonDateFormat = "dd/M/yyyy";
		String productStartDate = Utility.convertTokenToArrayList(seasonStartDate, Utility.SEASONDELIM).get(0);
		String productEndDate = Utility.convertTokenToArrayList(seasonEndDate, Utility.SEASONDELIM)
				.get(Utility.convertTokenToArrayList(seasonEndDate, Utility.SEASONDELIM).size() - 1);

		// Verify Start Date

		try {
			test_name = "Create Package/Product if it is not exist";
			test_description = "Create Package/Product if it is not exist";
			test_steps.clear();

			navigation.Inventory(driver, test_steps);
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");

			getTest_Steps.clear();
			getTest_Steps = rateGrid.clickCalendar(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			String todayDate = rateGrid.getCalendarDate(driver, calendarTodayDay, parentSeasonDateFormat,
					getTest_Steps);
			test_steps.addAll(getTest_Steps);

			app_logs.info("Today date : " + todayDate);
			app_logs.info("Excel Sheet  Season Start Date: " + seasonStartDate);
			app_logs.info("Excel Sheet  Season End Date: " + seasonEndDate);

			test_steps.add("Excel Sheet  Season Start Date: " + seasonStartDate);
			test_steps.add("Excel Sheet  Season End Date: " + seasonEndDate);

			app_logs.info("Initially Product Start Date: " + productStartDate);
			app_logs.info("Initially Product End Date: " + productEndDate);
			test_steps.add("Initially Product Start Date: " + productStartDate);
			test_steps.add("Initially Product End Date: " + productEndDate);

//			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", parentSeasonDateFormat, getTest_Steps);
//			if (Utility.compareDates(selectedDate, todayDate, parentSeasonDateFormat) > 0) {
//				todayDate = selectedDate;
//			}
//
//			if (Utility.compareDates(seasonStartDate.split(Utility.DELIM)[0], todayDate, parentSeasonDateFormat) >= 0) {
//
//			} else {
//				seasonStartDate = todayDate;
//				seasonEndDate = Utility.addDate(Integer.parseInt(seasonDuration), parentSeasonDateFormat,
//						seasonStartDate, parentSeasonDateFormat, timeZone);
//				productStartDate = seasonStartDate;
//				productEndDate = seasonEndDate;
//			}
//			int days = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate);
//			seasonDuration = "" + days + "";
//			app_logs.info("Season Duration : " + seasonDuration);
//			app_logs.info("Selected Season Start Date: " + seasonStartDate);
//			app_logs.info("Selected Season End Date: " + seasonEndDate);
//			test_steps.add("Selected Season Start Date: " + seasonStartDate);
//			test_steps.add("Selected Season End Date: " + seasonEndDate);
//			app_logs.info("Selected Product Start Date: " + productStartDate);
//			app_logs.info("Selected Product End Date: " + productEndDate);
//			test_steps.add("Selected Product Start Date: " + productStartDate);
//			test_steps.add("Selected Product End Date: " + productEndDate);
//
//			getTest_Steps.clear();
//			getTest_Steps = ratesGrid.closeCalendar(driver);
//			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", test_name,
						"NavigateToRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", test_name,
						"NavigateToRateGrid", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
			app_logs.info("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");

			derivedRate.clickTab(driver, "Products & Bundles", test_steps);
			test_steps.add("Navigated to products and bundles");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to  navigate to products and bundles", test_name, "RatesV2", driver);
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

		try {
//			if (Boolean.parseBoolean(isCreateProducts)) {
			String productsStartDate = null;
			String productsEndDate = null;
			for (int i = 0; i < Utility.convertTokenToArrayList(productsName, Utility.DELIM).size(); i++) {
				if (i == 0) {
					productsStartDate = productStartDate;
					productsEndDate = productEndDate;
				} else {
					productsStartDate += Utility.DELIM + productStartDate;
					productsEndDate += Utility.DELIM + productEndDate;
				}
				app_logs.info(productsStartDate);
				app_logs.info(productsEndDate);
			}
			test_steps.add("=================== CREATE PRODUCTS ======================");
			app_logs.info("=================== CREATE PRODUCTS ======================");

			getTest_Steps.clear();
			getTest_Steps = ratePackage.createProducts(driver, productsName, isSellOnBookingEngine,
					bookingEngineAvailabilityOption, productsRoomClass, productsDescription, productsCost,
					productsPolicy, productsCategory, calculationMethodOne, calculationMethodTwo, productsStartDate,
					productsEndDate);
			test_steps.addAll(getTest_Steps);

			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create product ", test_name, "CreateProducts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create product ", test_name, "CreateProducts", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_name = "Create And Verify Package RatePlan if it is not exist";
			test_description = "Create And Verify Package RatePlan if it is not exist";
			test_steps.clear();

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			test_steps.add("=================== VERIFY RATE PLAN EXIST OR NOT ======================");
			app_logs.info("=================== VERIFY RATE PLAN EXIST OR NOT ======================");
			israteplanExist = rateGrid.isRatePlanExist(driver, packageRatePlanName, test_steps);
			app_logs.info("israteplanExist : " + israteplanExist);
			test_steps.add("is rateplan Exist : " + israteplanExist);
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
			try {

				test_steps.add("=================== CREATE NEW PACKAGE RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW PACKAGE RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Package rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Package rate plan", test_steps);

				test_steps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				nightlyRate.enterRatePlanName(driver, packageRatePlanName, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, description, test_steps);

				nightlyRate.clickNextButton(driver, test_steps);
				getTest_Steps.clear();
				getTest_Steps = ratePackage.selectParentRatePlan(driver, rateType);
				test_steps.addAll(getTest_Steps);
				if (rateType.equalsIgnoreCase("Interval rates")) {

					getTest_Steps.clear();
					getTest_Steps = rateGrid.enterInterval(driver, intervalRatePlanIntervalValue);
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = rateGrid.byDefaultProrateCheckbox(driver,
							Boolean.parseBoolean(isDefaultProRateChecked));
					test_steps.addAll(getTest_Steps);
					rateTypeSummary = rateType + "; Interval Length - " + intervalRatePlanIntervalValue + " nights;";
				} else {
					rateTypeSummary = rateType;
				}
				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", packageRatePlanName, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to enter package rate plan name", test_name, "CreatePackageRate",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to enter package rate plan name", test_name, "CreatePackageRate",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				getTest_Steps.clear();
				getTest_Steps = ratePackage.addProducts(driver, productName, productAmount,
						productFirstCalculationMethod, productSecondCalculationMethod);
				test_steps.addAll(getTest_Steps);
				nightlyRate.clickNextButton(driver, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to select products in package rate plan", test_name,
							"SelectProducts", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to select products in package rate plan", test_name,
							"SelectProducts", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, channels, true, test_steps);
				String summaryChannels = channels;
				// nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);

//			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, test_steps);

				test_steps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");

				nightlyRate.selectRoomClasses(driver, roomClasses, true, test_steps);
				String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, test_steps);

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
						Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode, test_steps);

				String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, test_steps);

				nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq),
						test_steps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
						Boolean.parseBoolean(isPolicesReq), test_steps);

				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to select policies during package rate plan creation", test_name,
							"SelectPolicies", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to select policies during package rate plan creation", test_name,
							"SelectPolicies", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				nightlyRate.clickCreateSeason(driver, test_steps);
				app_logs.info(isCustomPerNight);
//			nightlyRate.createUpdateSeasonForPackageIntervalRatePlan(driver, test_steps, seasonStartDate, seasonEndDate,
//					seasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
//					roomClasses, isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
//					additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason,
//					extraRoomClassRatePerNight, extraRoomClassMaxAdults, extraRoomClassMaxPersons,
//					extraRoomClassAdditionalAdultsPerNight, extraRoomClassAdditionalChildPerNight,
//					isAssignRulesByRoomClass, isSeasonLevelRules, seasonRuleSpecificRoomClasses, seasonRuleType,
//					seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday,
//					isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday,
//					isSeasonPolicies, seasonPolicyType, seasonPolicyValues, isDefaultProRateChecked,
//					isProRateStayInSeason, isProRateInRoomClass, roomClassesWithProRateUnchecked, isCustomPerNight,
//					customRoomClasses, customRatePerNight, isAssignPoliciesByRoomClass, customRateAdultdPerNight,
//					customRateChildPerNight, isCustomRatePerNightAdultandChild, seasonPolicySpecificRoomClasses, false,
//					intervalRatePlanIntervalValue);
				nightlyRate.createSeason(driver, test_steps, seasonStartDate, seasonEndDate, seasonName, isMonDay,
						isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
						additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason,
						extraRoomClassesInSeason, extraRoomClassRatePerNight, extraRoomClassMaxAdults,
						extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
						extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClassCreateSheet, isSeasonLevelRules,
						seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, seasonPolicyType, seasonPolicyValues,
						isSeasonPolicies);
				nightlyRate.clickCompleteChanges(driver, test_steps);

				try {
					nightlyRate.clickSaveAsActive(driver, test_steps);
				} catch (Exception f) {
					nightlyRate.clickCompleteChanges(driver, test_steps);
					nightlyRate.clickSaveAsActive(driver, test_steps);
				}
				Utility.rateplanName = packageRatePlanName;

				test_steps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to create package rate plan", test_name, "CreatePackagePlan",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to create package rate plan", test_name, "CreatePackagePlan",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		}
		test_steps.add("=================== VERIFY Derived RATE PLAN EXIST OR NOT ======================");
		app_logs.info("=================== VERIFY Derived RATE PLAN EXIST OR NOT ======================");
		try {
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
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
				derivedRate.selectRatePlan(driver, packageRatePlanName, true, test_steps);

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
		
		try {
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			test_steps.add("=================== "
					+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			driver.navigate().refresh();
			Wait.wait3Second();
			driver.navigate().refresh();
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, derivedRatePlanName);
			test_steps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

			test_steps.add("=================== "
					+ "Verify the newly created Derived Rate Plan in the Rates Grid's Rate Plan drop down".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify the newly created Derived Rate Plan in the Rates Grid's Rate Plan drop down".toUpperCase()
					+ " ======================");

			getTest_Steps.clear();
			getTest_Steps = rateGrid.verifyDerivedRateDisplay(driver, derivedRatePlanName);
			test_steps.addAll(getTest_Steps);

			test_steps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e,
						"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						test_name, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e,
						"Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down",
						test_name, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		ArrayList<String> parentRatePlanOffSetList = new ArrayList<String>();
		ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;

		String selectedSeasonType = "";
		HashMap<Integer, String> customDatesStart = new HashMap<>();
		HashMap<Integer, String> customDatesEnd = new HashMap<>();
		String startSeasonDate = "";
		String endSeasonDate = "";
		ArrayList<String> allDatesBW = null;
		//boolean isAssignRulesByRoomClass = false;
		ArrayList<String> selectedRulesSpecificRoomClasses = null, selectedRulesTypes = null;
		HashMap<String, String> rulesTypeValues = null;
		boolean isAssignRulesByRoomClass = false;
		if(true) {

			try {
				ArrayList<String> allSeasonsDates = null;
				test_steps.add(
						"=================== GETTING DERIVED RATE PLAN CHANNELS AND ROOM CLASESS ======================");
				app_logs.info(
						"=================== GETTING DERIVED RATE PLAN CHANNELS AND ROOM CLASESS ======================");

				// if (isDerivedRateplanExist) {
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, test_steps);

				parentRatePlanOffSetList = derivedRate.getParentPlanOffset(driver);

				selectedChannels = nightlyRate.getSelectedChannels(driver);
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
				Utility.app_logs.info("Selected Channels : " + selectedChannels);
				test_steps.add("Selected Channels : " + selectedChannels);
				Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
				test_steps.add("Selected Room Classes : " + selectedRoomClasses);

				nightlyRate.switchCalendarTab(driver, test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				selectedSeasonType = derivedRate.getSelectedSeasonType(driver);
				Utility.app_logs.info(selectedSeasonType);
				boolean isAlreadyGetRulesDone = false;
				if (selectedSeasonType.equalsIgnoreCase("Always available")) {
					navigation.ratesGrid(driver);

					rateGrid.clickRatePlanArrow(driver, test_steps);
					rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
					Wait.waitUntilPageLoadNotCompleted(driver, 40);
					derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);
					rateGrid.clickEditIcon(driver, test_steps);
					nightlyRate.switchCalendarTab(driver, test_steps);

					allSeasonsDates = nightlyRate.getAllSeasonsDates(driver);
					Utility.app_logs.info("All Season Dates : " + allSeasonsDates);
					test_steps.add("All Season Dates : " + allSeasonsDates);

					nightlyRate.clickSaveRatePlanButton(driver, test_steps);

					startSeasonDate = allSeasonsDates.get(0);
					endSeasonDate = allSeasonsDates.get(allSeasonsDates.size() - 1);

					allDatesBW = Utility.getAllDatesBetweenCheckInOutDates(startSeasonDate, endSeasonDate);

					// ArrayList<String> checkIndates = Utility.convertTokenToArrayList(CheckInDate,
					// delim);

					if (Boolean.parseBoolean(parentRatePlanOffSetList.get(3))) {

						nightlyRate.selectSeasonDates(driver, test_steps, allDatesBW);
						nightlyRate.clickEditThisSeasonButton(driver, test_steps);

						nightlyRate.clickRulesRestrictionOnSeason(driver, test_steps);
						isAssignRulesByRoomClass = nightlyRate.selectedAssignRulesByRoomClass(driver);
						if (isAssignRulesByRoomClass) {
							selectedRulesSpecificRoomClasses = nightlyRate.selectedRulesSpecificRoomClasses(driver);
						}
						selectedRulesTypes = nightlyRate.selectedRulesType(driver);
						rulesTypeValues = nightlyRate.selectedRulesValues(driver, selectedRulesTypes);

						nightlyRate.closeSeason(driver, test_steps);

						rateGrid.closeOpendTabInMainMenu(driver);
						navigation.ratesGrid(driver);
						isAlreadyGetRulesDone = true;
					}

				} else {

					customDatesStart = derivedRate.getCustomDates(driver, "Custom date range", "Start Date");
					test_steps.add("Custom start date ranges :  " + customDatesStart);
					app_logs.info("Custom start date ranges :  " + customDatesStart);
					app_logs.info(customDatesStart.size());
					customDatesEnd = derivedRate.getCustomDates(driver, "Custom date range", "End Date");
					test_steps.add("Custom end date ranges :  " + customDatesEnd);
					app_logs.info("Custom end date ranges :  " + customDatesEnd);
					app_logs.info(customDatesEnd.size());

					startSeasonDate = Utility.mapToToken(delim, customDatesStart);
					endSeasonDate = Utility.mapToToken(delim, customDatesEnd);
					allDatesBW = new ArrayList<String>();
					for (int i = 0; i < customDatesStart.size(); i++) {
						allDatesBW.addAll(Utility.getAllDatesBetweenCheckInOutDates(
								Utility.parseDate(customDatesStart.get(i), "MM/dd/yyyy", "dd/MM/yyyy"),
								Utility.parseDate(customDatesEnd.get(i), "MM/dd/yyyy", "dd/MM/yyyy")));
					}

					app_logs.info(allDatesBW);
				}
				navigation.ratesGrid(driver);

				if (isAlreadyGetRulesDone && Boolean.parseBoolean(parentRatePlanOffSetList.get(3))) {
					rateGrid.clickRatePlanArrow(driver, test_steps);
					rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
					Wait.waitUntilPageLoadNotCompleted(driver, 40);
					derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);
					rateGrid.clickEditIcon(driver, test_steps);
					nightlyRate.switchCalendarTab(driver, test_steps);

					nightlyRate.selectSeasonDates(driver, test_steps, allDatesBW);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);

					nightlyRate.clickRulesRestrictionOnSeason(driver, test_steps);
					isAssignRulesByRoomClass = nightlyRate.selectedAssignRulesByRoomClass(driver);
					if (isAssignRulesByRoomClass) {
						selectedRulesSpecificRoomClasses = nightlyRate.selectedRulesSpecificRoomClasses(driver);
					}
					selectedRulesTypes = nightlyRate.selectedRulesType(driver);
					rulesTypeValues = nightlyRate.selectedRulesValues(driver, selectedRulesTypes);

					nightlyRate.closeSeason(driver, test_steps);

					rateGrid.closeOpendTabInMainMenu(driver);
					navigation.ratesGrid(driver);
				}

//			} else {
//				// selectedSeasonType = seasonType;
//			}

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

				HashMap<String, // RoomClass
						HashMap<String, // Source
								HashMap<String, // date
										RatesGridChannelRatesRules>>> roomClassWiseSourceRatesRule = rateGrid
												.getSourceWiseRatesRules(driver, test_steps, packageRatePlanName,
														derivedRatePlanName, false, delim,
														Utility.convertArrayListToToken(selectedRoomClasses, delim),
														Utility.convertArrayListToToken(selectedChannels, delim),
														startSeasonDate, endSeasonDate);

				for (String date : allDatesBW) {
					app_logs.info(date);
					app_logs.info(roomClassWiseSourceRatesRule.get(selectedRoomClasses.get(0))
							.get(selectedChannels.get(0)).get(date));
				}

				navigation.ratesGrid(driver);

				HashMap<String, // RoomClass
						HashMap<String, // Source
								HashMap<String, // date
										RatesGridChannelRatesRules>>> roomClassWiseSourceDerivedRatesRule = rateGrid
												.getSourceWiseRatesRules(driver, test_steps, packageRatePlanName,
														derivedRatePlanName, true, delim,
														Utility.convertArrayListToToken(selectedRoomClasses, delim),
														Utility.convertArrayListToToken(selectedChannels, delim),
														startSeasonDate, endSeasonDate);

				for (String date : allDatesBW) {
					app_logs.info(date);
					app_logs.info(roomClassWiseSourceDerivedRatesRule.get(selectedRoomClasses.get(0))
							.get(selectedChannels.get(0)).get(date));
				}

				for (String roomClass : selectedRoomClasses) {
					for (String source : selectedChannels) {
						for (String date : allDatesBW) {
							RatesGridChannelRatesRules parentRateRule = roomClassWiseSourceRatesRule.get(roomClass)
									.get(source).get(date);
							RatesGridChannelRatesRules derivedRateRule = roomClassWiseSourceDerivedRatesRule
									.get(roomClass).get(source).get(date);

							Utility.parseDate(date, "dd/MM/yyyy", "EE");

							if (parentRateRule != null || derivedRateRule != null) {
								if (!parentRateRule.getRooomRate().equals("--")) {
									if (parentRatePlanOffSetList.get(1).equalsIgnoreCase("percent")) {
										parentRateRule.setRooomRate(derivedRate.calculateOffSet(
												parentRateRule.getRooomRate(), parentRatePlanOffSetList.get(0),
												parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
										parentRateRule.setExtraAdultRate(derivedRate.calculateOffSet(
												parentRateRule.getExtraAdultRate(), parentRatePlanOffSetList.get(0),
												parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
										parentRateRule.setExtraChildRate(derivedRate.calculateOffSet(
												parentRateRule.getExtraChildRate(), parentRatePlanOffSetList.get(0),
												parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
									} else {
										parentRateRule.setRooomRate(derivedRate.calculateOffSet(
												parentRateRule.getRooomRate(), parentRatePlanOffSetList.get(0),
												parentRatePlanOffSetList.get(1), parentRatePlanOffSetList.get(2)));
									}

									assertEquals(Utility.convertDecimalFormat(parentRateRule.getRooomRate()),
											Utility.convertDecimalFormat(derivedRateRule.getRooomRate()),
											"Failed To Verify Room Rate Where Date : " + date + " Source : " + source
													+ " Room Class : " + roomClass);
									assertEquals(Utility.convertDecimalFormat(parentRateRule.getExtraAdultRate()),
											Utility.convertDecimalFormat(derivedRateRule.getExtraAdultRate()),
											"Failed To Verify Extra Adult Rate Where Date : " + date + " Source : "
													+ source + " Room Class : " + roomClass);
									assertEquals(Utility.convertDecimalFormat(parentRateRule.getExtraChildRate()),
											Utility.convertDecimalFormat(derivedRateRule.getExtraChildRate()),
											"Failed To Verify Extra Child Rate Where Date : " + date + " Source : "
													+ source + " Room Class : " + roomClass);

									if (Boolean.parseBoolean(parentRatePlanOffSetList.get(3))
											&& selectedRulesTypes.size() > 0) {
										if (isAssignRulesByRoomClass) {
											if (selectedRulesSpecificRoomClasses.contains(roomClass)) {
												int index = selectedRulesSpecificRoomClasses.indexOf(roomClass);
												for (String type : selectedRulesTypes) {
													app_logs.info(rulesTypeValues.get(type));
													if (type.equalsIgnoreCase("Min nights")) {
														try {
															assertEquals(Integer.parseInt(rulesTypeValues.get(type)),
																	derivedRateRule.getMinRule(),
																	"Failed To Verify Min Rule Value Where Date : "
																			+ date + " Source : " + source
																			+ " Room Class : " + roomClass);
														} catch (Exception e) {
															test_steps.add(e.toString());
														}
													} else if (type.equalsIgnoreCase("No check-in")) {
														try {
															assertEquals(
																	nightlyRate.isRuleApplied(date,
																			rulesTypeValues.get(type)),
																	derivedRateRule.isNoCheckIn(),
																	"Failed To Verify No CheckIn Rule Where Date : "
																			+ date + " Source : " + source
																			+ " Room Class : " + roomClass);
														} catch (Exception e) {
															test_steps.add(e.toString());
														}
													} else if (type.equalsIgnoreCase("No check-out")) {
														try {
															assertEquals(
																	nightlyRate.isRuleApplied(date,
																			rulesTypeValues.get(type)),
																	derivedRateRule.isNoCheckOut(),
																	"Failed To Verify No Checkout Rule Where Date : "
																			+ date + " Source : " + source
																			+ " Room Class : " + roomClass);
														} catch (Exception e) {
															test_steps.add(e.toString());
														}
													}
												}
											}
										} else {
											for (String type : selectedRulesTypes) {
												app_logs.info(rulesTypeValues);
												if (type.equalsIgnoreCase("Min nights")) {
													try {
														assertEquals(Integer.parseInt(rulesTypeValues.get(type)),
																Integer.parseInt(derivedRateRule.getMinRule()),
																"Failed To Verify Min Rule Value Where Date : " + date
																		+ " Source : " + source + " Room Class : "
																		+ roomClass);
													} catch (Exception e) {
														test_steps.add(e.toString());
													}
												} else if (type.equalsIgnoreCase("No check-in")) {
													try {
														assertEquals(
																nightlyRate.isRuleApplied(date,
																		rulesTypeValues.get(type)),
																derivedRateRule.isNoCheckIn(),
																"Failed To Verify No CheckIn Rule Where Date : " + date
																		+ " Source : " + source + " Room Class : "
																		+ roomClass);
													} catch (Exception e) {
														test_steps.add(e.toString());
													}
												} else if (type.equalsIgnoreCase("No check-out")) {
													try {
														assertEquals(
																nightlyRate.isRuleApplied(date,
																		rulesTypeValues.get(type)),
																derivedRateRule.isNoCheckOut(),
																"Failed To Verify No Checkout Rule Where Date : " + date
																		+ " Source : " + source + " Room Class : "
																		+ roomClass);
													} catch (Exception e) {
														test_steps.add(e.toString());
													}
												}
											}
										}
//									assertEquals(parentRateRule.getMinRule(), derivedRateRule.getMinRule(),
//											"Failed To Verify Min Rule Value Where Date : " + date + " Source : "
//													+ source + " Room Class : " + roomClass);
//									assertEquals(parentRateRule.isNoCheckIn(), derivedRateRule.isNoCheckIn(),
//											"Failed To Verify No CheckIn Rule Where Date : " + date + " Source : "
//													+ source + " Room Class : " + roomClass);
//									assertEquals(parentRateRule.isNoCheckOut(), derivedRateRule.isNoCheckOut(),
//											"Failed To Verify No Checkout Rule Where Date : " + date + " Source : "
//													+ source + " Room Class : " + roomClass);
									} else {
										try {
											assertNotEquals(parentRateRule.getMinRule(), derivedRateRule.getMinRule(),
													"Failed To Verify Min Rule Value Where Date : " + date
															+ " Source : " + source + " Room Class : " + roomClass);
										} catch (Exception e) {
//										test_steps.add(e.toString());
										}
										try {
											assertNotEquals(String.valueOf(parentRateRule.isNoCheckIn()),
													String.valueOf(derivedRateRule.isNoCheckIn()),
													"Failed To Verify No CheckIn Rule Where Date : " + date
															+ " Source : " + source + " Room Class : " + roomClass);
										} catch (Exception e) {
//										test_steps.add(e.toString());
										}
										try {
											assertNotEquals(String.valueOf(parentRateRule.isNoCheckOut()),
													String.valueOf(derivedRateRule.isNoCheckOut()),
													"Failed To Verify No Checkout Rule Where Date : " + date
															+ " Source : " + source + " Room Class : " + roomClass);
										} catch (Exception e) {
//										test_steps.add(e.toString());
										}
									}
								}
							}

							app_logs.info("Successfully Verified Rate & Rules for RoomClass : " + roomClass
									+ " and Source : " + source + " where date : " + date);
							test_steps.add("Successfully Verified Rate & Rules for RoomClass : " + roomClass
									+ " and Source : " + source + " where date : " + date);
						}
					}
				}

				app_logs.info(
						"=================== SUCCESSFULLY VERIFIED RATES GRID RATES AND RULES OF DERIVED RATE PLAN : "
								+ derivedRatePlanName.toUpperCase() + " AS PER BASE RATE PLAN : "
								+ packageRatePlanName.toUpperCase() + " ===================");
				test_steps.add(
						"=================== SUCCESSFULLY VERIFIED RATES GRID RATES AND RULES OF DERIVED RATE PLAN : "
								+ derivedRatePlanName.toUpperCase() + " AS PER BASE RATE PLAN : "
								+ packageRatePlanName.toUpperCase() + " ===================");
				// assertEquals(roomClassWiseSourceDerivedRatesRule,
				// roomClassWiseSourceRatesRule);

			} catch (Exception e) {

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
		
		
		if(true) {
			try {

				test_name = "Checking Availability & Blackout B/w Check In Check Out Dates";
				test_description = "Checking Availability & Blackout B/w Check In Check Out Dates";
				test_steps.clear();

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
					rateGrid.clickExpendRooClass(driver, test_steps, ReservationRoomClasses);
					app_logs.info("==========Getting the availability count of the room class " + ReservationRoomClasses
							+ " in Availability tab==========");
					test_steps.add("==========Getting the availability count of the room class " + ReservationRoomClasses
							+ " in Availability tab==========");
					ArrayList<String> avail = rateGrid.getAvailability(driver, test_steps, ReservationRoomClasses, days,
							CheckInDate);

					app_logs.info("==========Getting the room class " + ReservationRoomClasses
							+ " as blocked out or not in Availability tab==========");
					test_steps.add("==========Getting the room class " + ReservationRoomClasses
							+ " as blocked out or not in Availability tab==========");
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

				} else {
					String[] rm = ReservationRoomClasses.split("\\|");
					String[] chkin = CheckInDate.split("\\|");
					String[] chkout = CheckOutDate.split("\\|");
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
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_steps.clear();
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
		
		try {
			rateGrid.clickRatesTab(driver, test_steps);
			reservationRoomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
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
		selectedSeasonType = null;
		parentRatePlanOffSetList.clear();
		try {

			test_steps.add("=================== GETTING DERIVED RATE PLAN SEASON TYPE ======================");
			app_logs.info("=================== GETTING DERIVED RATE PLAN SEASON TYPE ======================");

			if (isDerivedRateplanExist) {
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, test_steps);

				parentRatePlanOffSetList = derivedRate.getParentPlanOffset(driver);

				nightlyRate.switchCalendarTab(driver, test_steps);

				selectedSeasonType = derivedRate.getSelectedSeasonType(driver);
				Utility.app_logs.info(selectedSeasonType);
				test_steps.add("Selected Season Type : " + selectedSeasonType);
				navigation.ratesGrid(driver);

			} else {
				selectedSeasonType = seasonType;
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
			test_steps.add("=================== SELECTING THE RATE PLAN ======================");
			app_logs.info("=================== SELECTING THE RATE PLAN ======================");
			driver.navigate().refresh();
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, derivedRatePlanName);
			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			getTest_Steps.clear();
			getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			// start rules

			HashMap<String, ArrayList<String>> parentInitialMinStayRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> parentInitialNoCheckInRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> parentInitialNoCheckOutRule = new HashMap<String, ArrayList<String>>();

			HashMap<String, ArrayList<String>> derivedInitialMinStayRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> derivedInitialNoCheckInRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> derivedInitialNoCheckOutRule = new HashMap<String, ArrayList<String>>();

			if ((RulesUpdateType.equalsIgnoreCase("Overide") || RulesUpdateType.equalsIgnoreCase("BulkUpdate"))) {

				test_name = "Verify Bulk Update / Overide Parent Level Rules";
				test_description = "Verify Bulk Update / Overide Parent Level Rules";

				app_logs.info("==========GETTING INITIAL BASE RULES VALUES==========");
				test_steps.add("==========GETTING INITIAL BASE RULES VALUES==========");

				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, RulesUpdateStartDate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				rateGrid.expandParentRateGrid(driver, "plus");

				derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);
				for (String roomClass : reservationRoomClassesList) {
					rateGrid.expandRoomClass(driver, test_steps, roomClass);
					rateGrid.expandChannel(driver, test_steps, roomClass, channelName);

					ArrayList<String> parentInitialMinStayRuleList = null;
					ArrayList<String> parentInitialNoCheckInRuleList = null;
					ArrayList<String> parentInitialNoCheckOutRuleList = null;

					if (RulesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> RulesUpdateStartDateList = Utility
								.convertTokenToArrayList(RulesUpdateStartDate, delim);
						int days = Utility.getNumberofDays(RulesUpdateStartDateList.get(0),
								RulesUpdateStartDateList.get(RulesUpdateStartDateList.size() - 1));
						if (days == 0) {
							days = 1;
						}
						parentInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName, days);
						parentInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName, days);
						parentInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName, days);

						parentInitialMinStayRule.put(roomClass, parentInitialMinStayRuleList);
						parentInitialNoCheckInRule.put(roomClass, parentInitialNoCheckInRuleList);
						parentInitialNoCheckOutRule.put(roomClass, parentInitialNoCheckOutRuleList);
					} else if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {
						parentInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));
						parentInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));
						parentInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));

						parentInitialMinStayRule.put(roomClass, parentInitialMinStayRuleList);
						parentInitialNoCheckInRule.put(roomClass, parentInitialNoCheckInRuleList);
						parentInitialNoCheckOutRule.put(roomClass, parentInitialNoCheckOutRuleList);
					}

				}
				app_logs.info(parentInitialMinStayRule);
				app_logs.info(parentInitialNoCheckInRule);
				app_logs.info(parentInitialNoCheckOutRule);

				app_logs.info("==========GETTING INITIAL DERIVED RULES VALUES==========");
				test_steps.add("==========GETTING INITIAL DERIVED RULES VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, RulesUpdateStartDate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);
				for (String roomClass : reservationRoomClassesList) {
					rateGrid.expandRoomClass(driver, test_steps, roomClass);
					rateGrid.expandChannel(driver, test_steps, roomClass, channelName);

					ArrayList<String> derivedInitialMinStayRuleList = null;
					ArrayList<String> derivedInitialNoCheckInRuleList = null;
					ArrayList<String> derivedInitialNoCheckOutRuleList = null;

					if (RulesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> RulesUpdateStartDateList = Utility
								.convertTokenToArrayList(RulesUpdateStartDate, delim);
						int days = Utility.getNumberofDays(RulesUpdateStartDateList.get(0),
								RulesUpdateStartDateList.get(RulesUpdateStartDateList.size() - 1));
						if (days == 0) {
							days = 1;
						}
						derivedInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName, days);
						derivedInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName, days);
						derivedInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName, days);

						derivedInitialMinStayRule.put(roomClass, derivedInitialMinStayRuleList);
						derivedInitialNoCheckInRule.put(roomClass, derivedInitialNoCheckInRuleList);
						derivedInitialNoCheckOutRule.put(roomClass, derivedInitialNoCheckOutRuleList);
					} else if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {
						derivedInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));
						derivedInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));
						derivedInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));

						derivedInitialMinStayRule.put(roomClass, derivedInitialMinStayRuleList);
						derivedInitialNoCheckInRule.put(roomClass, derivedInitialNoCheckInRuleList);
						derivedInitialNoCheckOutRule.put(roomClass, derivedInitialNoCheckOutRuleList);
					}
				}
				app_logs.info(derivedInitialMinStayRule);
				app_logs.info(derivedInitialNoCheckInRule);
				app_logs.info(derivedInitialNoCheckOutRule);
			}

			rateGrid.expandParentRateGrid(driver, "plus");
			derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

			rateGrid.bulkUpdateOverideRules(driver, delim, RulesUpdateType, RulesUpdateStartDate, RulesUpdateEndDate,
					isSun_RulesUpdate, isMon_RulesUpdate, isTue_RulesUpdate, isWed_RulesUpdate, isThu_RulesUpdate,
					isFri_RulesUpdate, isSat_RulesUpdate, packageRatePlanName, ReservationRoomClasses, channelName,
					Type_RulesUpdate, RuleMinStayValue_RulesUpdate, test_steps);

			HashMap<String, ArrayList<String>> derivedAfterMinStayRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> derivedAfterNoCheckInRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> derivedAfterNoCheckOutRule = new HashMap<String, ArrayList<String>>();

			if ((RulesUpdateType.equalsIgnoreCase("Overide") || RulesUpdateType.equalsIgnoreCase("BulkUpdate"))) {

				app_logs.info("==========GETTING AFTER UPDATE DERIVED RULES VALUES==========");
				test_steps.add("==========GETTING AFTER UPDATE DERIVED RULES VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, RulesUpdateStartDate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);

				for (String roomClass : reservationRoomClassesList) {

					rateGrid.expandRoomClass(driver, test_steps, roomClass);
					rateGrid.expandChannel(driver, test_steps, roomClass, channelName);

					ArrayList<String> derivedAfterMinStayRuleList = null;
					ArrayList<String> derivedAfterNoCheckInRuleList = null;
					ArrayList<String> derivedAfterNoCheckOutRuleList = null;

					if (RulesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> RulesUpdateStartDateList = Utility
								.convertTokenToArrayList(RulesUpdateStartDate, delim);
						int days = Utility.getNumberofDays(RulesUpdateStartDateList.get(0),
								RulesUpdateStartDateList.get(RulesUpdateStartDateList.size() - 1));
						if (days == 0) {
							days = 1;
						}
						derivedAfterMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName, days);
						derivedAfterNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName, days);
						derivedAfterNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName, days);
					} else if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {
						derivedAfterMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));
						derivedAfterNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));
						derivedAfterNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate));
					}

					derivedAfterMinStayRule.put(roomClass, derivedAfterMinStayRuleList);
					derivedAfterNoCheckInRule.put(roomClass, derivedAfterNoCheckInRuleList);
					derivedAfterNoCheckOutRule.put(roomClass, derivedAfterNoCheckOutRuleList);
				}
				app_logs.info(derivedAfterMinStayRule);
				app_logs.info(derivedAfterNoCheckInRule);
				app_logs.info(derivedAfterNoCheckOutRule);
				int rulesUpdateDiffDate = Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate) + 1;

				app_logs.info("========== SELECTED SEASON TYPE IS " + selectedSeasonType.toUpperCase() + "==========");
				test_steps.add("========== SELECTED SEASON TYPE IS " + selectedSeasonType.toUpperCase() + "==========");

				for (String roomClass : reservationRoomClassesList) {
					app_logs.info(
							"==========VERIFING DERIVED RATE PLAN RULES VALUES SHOULD NOT CHANGE WHERE ROOM CLASS IS : "
									+ roomClass + "==========");
					test_steps.add(
							"==========VERIFING DERIVED RATE PLAN RULES VALUES SHOULD NOT CHANGE WHERE ROOM CLASS IS : "
									+ roomClass + "==========");
					List<Date> rulesUpdateDates = Utility.getDateRangeBetweenfromAndToDate(
							Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
									RulesUpdateStartDate),
							Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
									RulesUpdateEndDate));
					for (int i = 0; i < rulesUpdateDiffDate; i++) {

						try {
							String acctual = derivedAfterMinStayRule.get(roomClass).get(i);
							String expected = derivedInitialMinStayRule.get(roomClass).get(i);
							assertEquals(acctual, expected, "Failed To Verify After Derived Min Stay Rule");
							app_logs.info("Successfully Verified Min Stay Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
							test_steps.add("Successfully Verified Min Stay Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							String acctual = derivedAfterNoCheckInRule.get(roomClass).get(i);
							String expected = derivedInitialNoCheckInRule.get(roomClass).get(i);
							assertEquals(acctual, expected, "Failed To Verify After Derived No Check In Rule");
							app_logs.info("Successfully Verified No Check In Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
							test_steps.add("Successfully Verified No Check In Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							String acctual = derivedAfterNoCheckOutRule.get(roomClass).get(i);
							String expected = derivedInitialNoCheckOutRule.get(roomClass).get(i);
							assertEquals(acctual, expected, "Failed To Verify After Derived No Check Out Rule");
							app_logs.info("Successfully Verified No Check Out Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
							test_steps.add("Successfully Verified No Check Out Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}

					}

				}
			}
			// end rules

			HashMap<String, HashMap<String, String>> getParentInitialRates = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getParentInitialExAdult = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getParentInitialExChild = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedInitialRates = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedInitialExAdult = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedInitialExChild = new HashMap<String, HashMap<String, String>>();
			if ((RatesUpdateType.equalsIgnoreCase("Overide") || RatesUpdateType.equalsIgnoreCase("BulkUpdate"))) {
				app_logs.info("==========GETTING INITIAL BASE RATE VALUES==========");
				test_steps.add("==========GETTING INITIAL BASE RATE VALUES==========");

				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_steps.clear();
				test_name = "Verify Bulk Update / Overide Parent Level Rates";
				test_description = "Verify Bulk Update / Overide Parent Level Rates";

				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, checkInDate_RatesUpdate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				rateGrid.expandParentRateGrid(driver, "plus");
				derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

				test_steps.add("===Get Data From Rate Plan===");
				for (String roomClass : reservationRoomClassesList) {

					rateGrid.expandRoomClass(driver, test_steps, roomClass);

					HashMap<String, String> getParentInitialRatesTemp = new HashMap<String, String>();
					HashMap<String, String> getParentInitialExAdultTemp = new HashMap<String, String>();
					HashMap<String, String> getParentInitialExChildTemp = new HashMap<String, String>();
					// HashMap<String, String> getParentInitialRatesPerNightChannelsTemp = new
					// HashMap<String, String>();
					HashMap<String, // RoomClass
							HashMap<String, // Source
									HashMap<String, // date
											RatesGridChannelRatesRules>>> getParentInitialRatesPerNightChannelsTemp = null;

					if (RatesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> RatesUpdateStartDateList = Utility
								.convertTokenToArrayList(checkInDate_RatesUpdate, delim);

						getParentInitialRatesPerNightChannelsTemp = rateGrid.getSourceWiseRatesRules(driver, test_steps,
								packageRatePlanName, derivedRatePlanName, false, delim, roomClass, channelName,
								RatesUpdateStartDateList.get(0),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));

//						getParentInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
//								RatesUpdateStartDateList.get(0), RatesUpdateStartDateList.get(RatesUpdateStartDateList.size()-1), roomClass, channelName);
//						app_logs.info(getParentInitialRatesPerNightChannelsTemp);
//						getData(getParentInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								RatesUpdateStartDateList.get(0));
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
						allDatesBW = Utility.getAllDatesBetweenTwoDates(RatesUpdateStartDateList.get(0),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));
					} else if (RatesUpdateType.equalsIgnoreCase("BulkUpdate")) {
						getParentInitialRatesPerNightChannelsTemp = rateGrid.getSourceWiseRatesRules(driver, test_steps,
								packageRatePlanName, derivedRatePlanName, false, delim, roomClass, channelName,
								checkInDate_RatesUpdate, checkOutDate_RatesUpdate);
//						getParentInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
//								checkInDate_RatesUpdate, checkOutDate_RatesUpdate, roomClass, channelName);
//						app_logs.info(getParentInitialRatesPerNightChannelsTemp);
//						getData(getParentInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								checkInDate_RatesUpdate);
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								checkOutDate_RatesUpdate);
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
						allDatesBW = Utility.getAllDatesBetweenTwoDates(checkInDate_RatesUpdate,
								checkOutDate_RatesUpdate);
					}
					for (String date : allDatesBW) {
						app_logs.info(date);
						app_logs.info("Found Room Rates : "
								+ getParentInitialRatesPerNightChannelsTemp.get(roomClass).get(channelName).get(date));
						test_steps.add("Found Room Rates : "
								+ getParentInitialRatesPerNightChannelsTemp.get(roomClass).get(channelName).get(date));
						RatesGridChannelRatesRules obj = getParentInitialRatesPerNightChannelsTemp.get(roomClass)
								.get(channelName).get(date);

						getParentInitialRatesTemp.put(date, obj.getRooomRate());
						getParentInitialExAdultTemp.put(date, obj.getExtraAdultRate());
						getParentInitialExChildTemp.put(date, obj.getExtraChildRate());
					}

					getParentInitialRates.put(roomClass, getParentInitialRatesTemp);
					getParentInitialExAdult.put(roomClass, getParentInitialExAdultTemp);
					getParentInitialExChild.put(roomClass, getParentInitialExChildTemp);
				}
				app_logs.info(getParentInitialRates);
				app_logs.info(getParentInitialExAdult);
				app_logs.info(getParentInitialExChild);

				app_logs.info("==========GETTING INITIAL DERIVED RATE VALUES==========");
				test_steps.add("==========GETTING INITIAL DERIVED RATE VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, checkInDate_RatesUpdate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);

				test_steps.add("===Get Data From Rate Plan===");
				for (String roomClass : reservationRoomClassesList) {

					rateGrid.expandRoomClass(driver, test_steps, roomClass);

					HashMap<String, String> getDerivedInitialRatesTemp = new HashMap<String, String>();
					HashMap<String, String> getDerivedInitialExAdultTemp = new HashMap<String, String>();
					HashMap<String, String> getDerivedInitialExChildTemp = new HashMap<String, String>();
					HashMap<String, // RoomClass
							HashMap<String, // Source
									HashMap<String, // date
											RatesGridChannelRatesRules>>> getDerivedInitialRatesPerNightChannelsTemp = null;

					if (RatesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> RatesUpdateStartDateList = Utility
								.convertTokenToArrayList(checkInDate_RatesUpdate, delim);

						getDerivedInitialRatesPerNightChannelsTemp = rateGrid.getSourceWiseRatesRules(driver,
								test_steps, packageRatePlanName, derivedRatePlanName, true, delim, roomClass,
								channelName, RatesUpdateStartDateList.get(0),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));

//					getDerivedInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
//							RatesUpdateStartDateList.get(0), RatesUpdateStartDateList.get(RatesUpdateStartDateList.size()-1), roomClass, channelName);
//					app_logs.info(getDerivedInitialRatesPerNightChannelsTemp);
//					getData(getDerivedInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								RatesUpdateStartDateList.get(0));
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
						allDatesBW = Utility.getAllDatesBetweenTwoDates(RatesUpdateStartDateList.get(0),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));
					} else if (RatesUpdateType.equalsIgnoreCase("BulkUpdate")) {

						getDerivedInitialRatesPerNightChannelsTemp = rateGrid.getSourceWiseRatesRules(driver,
								test_steps, packageRatePlanName, derivedRatePlanName, true, delim, roomClass,
								channelName, checkInDate_RatesUpdate, checkOutDate_RatesUpdate);

//					getDerivedInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
//							checkInDate_RatesUpdate, checkOutDate_RatesUpdate, roomClass, channelName);
//					app_logs.info(getDerivedInitialRatesPerNightChannelsTemp);
//					getData(getDerivedInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								checkInDate_RatesUpdate);
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								checkOutDate_RatesUpdate);
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
						allDatesBW = Utility.getAllDatesBetweenTwoDates(checkInDate_RatesUpdate,
								checkOutDate_RatesUpdate);
					}
					for (String date : allDatesBW) {
						app_logs.info(date);
						app_logs.info("Found Room Rates : "
								+ getDerivedInitialRatesPerNightChannelsTemp.get(roomClass).get(channelName).get(date));
						test_steps.add("Found Room Rates : "
								+ getDerivedInitialRatesPerNightChannelsTemp.get(roomClass).get(channelName).get(date));
						RatesGridChannelRatesRules obj = getDerivedInitialRatesPerNightChannelsTemp.get(roomClass)
								.get(channelName).get(date);

						getDerivedInitialRatesTemp.put(date, obj.getRooomRate());
						getDerivedInitialExAdultTemp.put(date, obj.getExtraAdultRate());
						getDerivedInitialExChildTemp.put(date, obj.getExtraChildRate());
					}

					getDerivedInitialRates.put(roomClass, getDerivedInitialRatesTemp);
					getDerivedInitialExAdult.put(roomClass, getDerivedInitialExAdultTemp);
					getDerivedInitialExChild.put(roomClass, getDerivedInitialExChildTemp);

				}

				app_logs.info(getDerivedInitialRates);
				app_logs.info(getDerivedInitialExAdult);
				app_logs.info(getDerivedInitialExChild);

			}

			rateGrid.expandParentRateGrid(driver, "plus");
			derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

			rateGrid.bulkUpdateOverideRates(driver, delim, RatesUpdateType, checkInDate_RatesUpdate,
					checkOutDate_RatesUpdate, isSunday_RatesUpdate, isMonday_RatesUpdate, isTuesday_RatesUpdate,
					isWednesday_RatesUpdate, isThursday_RatesUpdate, isFriday_RatesUpdate, isSaturday_RatesUpdate,
					packageRatePlanName, ReservationRoomClasses, channelName, updateRatesType, isUpdateRateByRoomClass,
					nightlyRate_RatesUpdate, additionalAdults_RatesUpdate, additionalChild_RatesUpdate, test_steps);

			HashMap<String, HashMap<String, String>> getDerivedAfterRates = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedAfterExAdult = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedAfterExChild = new HashMap<String, HashMap<String, String>>();
			if ((RatesUpdateType.equalsIgnoreCase("Overide") || RatesUpdateType.equalsIgnoreCase("BulkUpdate"))) {

				app_logs.info("==========GETTING AFTER UPDATE DERIVED RATE VALUES==========");
				test_steps.add("==========GETTING AFTER UPDATE DERIVED RATE VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, checkInDate_RatesUpdate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);

				for (String roomClass : reservationRoomClassesList) {
					HashMap<String, String> getDerivedAfterRatesTemp = new HashMap<String, String>();
					HashMap<String, String> getDerivedAfterExAdultTemp = new HashMap<String, String>();
					HashMap<String, String> getDerivedAfterExChildTemp = new HashMap<String, String>();
					// HashMap<String, String> getDerivedAfterRatesPerNightChannelsTemp = new
					// HashMap<String, String>();

					HashMap<String, // RoomClass
							HashMap<String, // Source
									HashMap<String, // date
											RatesGridChannelRatesRules>>> getDerivedAfterRatesPerNightChannelsTemp = null;

					rateGrid.expandRoomClass(driver, test_steps, roomClass);
					test_steps.add("===Get Data From Rate Plan===");
					if (RatesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> RatesUpdateStartDateList = Utility
								.convertTokenToArrayList(checkInDate_RatesUpdate, delim);

						getDerivedAfterRatesPerNightChannelsTemp = rateGrid.getSourceWiseRatesRules(driver, test_steps,
								packageRatePlanName, derivedRatePlanName, true, delim, roomClass, channelName,
								RatesUpdateStartDateList.get(0),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));

//						getDerivedAfterRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
//								RatesUpdateStartDateList.get(0), RatesUpdateStartDateList.get(RatesUpdateStartDateList.size()-1), roomClass, channelName);
//						app_logs.info(getDerivedAfterRatesPerNightChannelsTemp);
//						getData(getDerivedAfterRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								RatesUpdateStartDateList.get(0));
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
						allDatesBW = Utility.getAllDatesBetweenTwoDates(RatesUpdateStartDateList.get(0),
								RatesUpdateStartDateList.get(RatesUpdateStartDateList.size() - 1));
					} else if (RatesUpdateType.equalsIgnoreCase("BulkUpdate")) {

						getDerivedAfterRatesPerNightChannelsTemp = rateGrid.getSourceWiseRatesRules(driver, test_steps,
								packageRatePlanName, derivedRatePlanName, true, delim, roomClass, channelName,
								checkInDate_RatesUpdate, checkOutDate_RatesUpdate);

//						getDerivedAfterRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
//								checkInDate_RatesUpdate, checkOutDate_RatesUpdate, roomClass, channelName);
//						app_logs.info(getDerivedAfterRatesPerNightChannelsTemp);
//						getData(getDerivedAfterRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								checkInDate_RatesUpdate);
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								checkOutDate_RatesUpdate);
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
						allDatesBW = Utility.getAllDatesBetweenTwoDates(checkInDate_RatesUpdate,
								checkOutDate_RatesUpdate);
					}

					for (String date : allDatesBW) {
						app_logs.info(date);
						app_logs.info("Found Room Rates : "
								+ getDerivedAfterRatesPerNightChannelsTemp.get(roomClass).get(channelName).get(date));
						test_steps.add("Found Room Rates : "
								+ getDerivedAfterRatesPerNightChannelsTemp.get(roomClass).get(channelName).get(date));
						RatesGridChannelRatesRules obj = getDerivedAfterRatesPerNightChannelsTemp.get(roomClass)
								.get(channelName).get(date);

						getDerivedAfterRatesTemp.put(date, obj.getRooomRate());
						getDerivedAfterExAdultTemp.put(date, obj.getExtraAdultRate());
						getDerivedAfterExChildTemp.put(date, obj.getExtraChildRate());
					}

					getDerivedAfterRates.put(roomClass, getDerivedAfterRatesTemp);
					getDerivedAfterExAdult.put(roomClass, getDerivedAfterExAdultTemp);
					getDerivedAfterExChild.put(roomClass, getDerivedAfterExChildTemp);
				}

				app_logs.info(getDerivedAfterRates);
				app_logs.info(getDerivedAfterExAdult);
				app_logs.info(getDerivedAfterExChild);

				app_logs.info("========== SELECTED SEASON TYPE IS " + selectedSeasonType.toUpperCase() + "==========");
				test_steps.add("========== SELECTED SEASON TYPE IS " + selectedSeasonType.toUpperCase() + "==========");

				app_logs.info("========== SELECTED RATE PLAN OFF SET TYPE IS "
						+ parentRatePlanOffSetList.get(1).toUpperCase() + "==========");
				test_steps.add("========== SELECTED RATE PLAN OFF SET TYPE IS "
						+ parentRatePlanOffSetList.get(1).toUpperCase() + "==========");

				for (String roomClass : reservationRoomClassesList) {
					if (!parentRatePlanOffSetList.get(1).equalsIgnoreCase("percent")) {
						app_logs.info(
								"==========VERIFING DERIVED RATE PLAN RATES VALUES SHOULD NOT CHANGE OTHER THAN BASE RATE WHERE ROOM CLASS IS "
										+ roomClass + "==========");
						test_steps.add(
								"==========VERIFING DERIVED RATE PLAN RATES VALUES SHOULD NOT CHANGE OTHER THAN BASE RATE WHERE ROOM CLASS IS "
										+ roomClass + "==========");

						for (int i = 0; i < dates.size(); i++) {

							String keyDate = Utility.convertDateFormattoString(
									ratesConfig.getProperty("defaultDateFormat"), dates.get(i));

							try {
								String acctual = getDerivedAfterRates.get(roomClass).get(keyDate);
								// String expected = getDerivedInitialRates.get(roomClass).get(keyDate);

								String expected = derivedRate.calculateOffSet(
										getParentInitialRates.get(roomClass).get(keyDate),
										parentRatePlanOffSetList.get(0), parentRatePlanOffSetList.get(1),
										parentRatePlanOffSetList.get(2));

								assertEquals(Float.parseFloat(acctual), Float.parseFloat(expected),
										"Failed To Verify After Derived Base Rates");
								app_logs.info("Successfully Verified Base Rates : " + acctual + "  Changes for Date : "
										+ dates.get(i));
								test_steps.add("Successfully Verified Base Rates : " + acctual + "  Changes for Date : "
										+ dates.get(i));
							} catch (Error e) {
								test_steps.add(e.toString());
							}

							try {
								String acctual = getDerivedAfterExAdult.get(roomClass).get(keyDate);
								String expected = getDerivedInitialExAdult.get(roomClass).get(keyDate);
								assertEquals(acctual, expected, "Failed To Verify After Derived Extra Adult Rates");
								app_logs.info("Successfully Verified Extra Adult Rates : " + acctual
										+ " Not Changes for Date : " + dates.get(i));
								test_steps.add("Successfully Verified Extra Adult Rates : " + acctual
										+ " Not Changes for Date : " + dates.get(i));
							} catch (Error e) {
								test_steps.add(e.toString());
							}

							try {
								String acctual = getDerivedAfterExChild.get(roomClass).get(keyDate);
								String expected = getDerivedInitialExChild.get(roomClass).get(keyDate);
								assertEquals(acctual, expected, "Failed To Verify After Derived Extra Child Rates");
								app_logs.info("Successfully Verified Extra Child Rates : " + acctual
										+ " Not Changes for Date : " + dates.get(i));
								test_steps.add("Successfully Verified Extra Child Rates : " + acctual
										+ " Not Changes for Date : " + dates.get(i));
							} catch (Error e) {
								test_steps.add(e.toString());
							}
						}

					} else {

						app_logs.info(
								"==========VERIFING DERIVED RATE PLAN RATES VALUES SHOULD CHANGED WHERE ROOM CLASS IS "
										+ roomClass + "==========");
						test_steps.add(
								"==========VERIFING DERIVED RATE PLAN RATES VALUES SHOULD CHANGED WHERE ROOM CLASS IS "
										+ roomClass + "==========");

						for (int i = 0; i < dates.size(); i++) {

							String keyDate = Utility.convertDateFormattoString(
									ratesConfig.getProperty("defaultDateFormat"), dates.get(i));
							try {
								String acctual = getDerivedAfterRates.get(roomClass).get(keyDate);
//								String expected = getDerivedInitialRates.get(roomClass).get(keyDate);
								String expected = derivedRate.calculateOffSet(
										getParentInitialRates.get(roomClass).get(keyDate),
										parentRatePlanOffSetList.get(0), parentRatePlanOffSetList.get(1),
										parentRatePlanOffSetList.get(2));
								assertEquals(Utility.formatDecimal(acctual), Utility.formatDecimal(expected),
										"Failed To Verify After Derived Base Rates");
								app_logs.info("Successfully Verified Base Rates : " + acctual + "  Changes for Date : "
										+ dates.get(i));
								test_steps.add("Successfully Verified Base Rates : " + acctual + "  Changes for Date : "
										+ dates.get(i));
							} catch (Error e) {
								test_steps.add(e.toString());
							}

							try {
								String acctual = getDerivedAfterExAdult.get(roomClass).get(keyDate);
//								String expected = getDerivedInitialExAdult.get(roomClass).get(keyDate);
								String expected = derivedRate.calculateOffSet(
										getParentInitialExAdult.get(roomClass).get(keyDate),
										parentRatePlanOffSetList.get(0), parentRatePlanOffSetList.get(1),
										parentRatePlanOffSetList.get(2));
								assertEquals(Utility.formatDecimal(acctual), Utility.formatDecimal(expected),
										"Failed To Verify After Derived Extra Adult Rates");
								app_logs.info("Successfully Verified Extra Adult Rates : " + acctual
										+ "  Changes for Date : " + dates.get(i));
								test_steps.add("Successfully Verified Extra Adult Rates : " + acctual
										+ "  Changes for Date : " + dates.get(i));
							} catch (Error e) {
								test_steps.add(e.toString());
							}

							try {
								String acctual = getDerivedAfterExChild.get(roomClass).get(keyDate);
//								String expected = getDerivedInitialExChild.get(roomClass).get(keyDate);
								String expected = derivedRate.calculateOffSet(
										getParentInitialExChild.get(roomClass).get(keyDate),
										parentRatePlanOffSetList.get(0), parentRatePlanOffSetList.get(1),
										parentRatePlanOffSetList.get(2));
								assertEquals(Utility.formatDecimal(acctual), Utility.formatDecimal(expected),
										"Failed To Verify After Derived Extra Child Rates");
								app_logs.info("Successfully Verified Extra Child Rates : " + acctual
										+ "  Changes for Date : " + dates.get(i));
								test_steps.add("Successfully Verified Extra Child Rates : " + acctual
										+ "  Changes for Date : " + dates.get(i));
							} catch (Error e) {
								test_steps.add(e.toString());
							}
						}
					}
				}
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

			test_steps.add("=================== SELECTING THE RATE PLAN ======================");
			app_logs.info("=================== SELECTING THE RATE PLAN ======================");
			driver.navigate().refresh();
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, derivedRatePlanName);
			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			getTest_Steps.clear();
			getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			// start rules

			HashMap<String, ArrayList<String>> parentInitialMinStayRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> parentInitialNoCheckInRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> parentInitialNoCheckOutRule = new HashMap<String, ArrayList<String>>();

			HashMap<String, ArrayList<String>> derivedInitialMinStayRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> derivedInitialNoCheckInRule = new HashMap<String, ArrayList<String>>();
			HashMap<String, ArrayList<String>> derivedInitialNoCheckOutRule = new HashMap<String, ArrayList<String>>();

			if ((DrivedRulesUpdateType.equalsIgnoreCase("Overide")
					|| DrivedRulesUpdateType.equalsIgnoreCase("BulkUpdate"))) {

				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_steps.clear();
				test_name = "Verify Bulk Update / Overide Derived Level Rules";
				test_description = "Verify Bulk Update / Overide Derived Level Rules";

				app_logs.info("==========GETTING INITIAL BASE RULES VALUES==========");
				test_steps.add("==========GETTING INITIAL BASE RULES VALUES==========");

				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, DrivedRulesUpdateStartDate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				rateGrid.expandParentRateGrid(driver, "plus");

				derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

				if (DrivedRulesUpdateType.equalsIgnoreCase("Overide")) {
					ArrayList<String> DrivedRulesUpdateStartDateList = Utility
							.convertTokenToArrayList(DrivedRulesUpdateStartDate, delim);
					for (String roomClass : reservationRoomClassesList) {
						ArrayList<String> parentInitialMinStayRuleList = null;
						ArrayList<String> parentInitialNoCheckInRuleList = null;
						ArrayList<String> parentInitialNoCheckOutRuleList = null;
						rateGrid.expandRoomClass(driver, test_steps, roomClass);
						rateGrid.expandChannel(driver, test_steps, roomClass, channelName);
						int days = Utility.getNumberofDays(DrivedRulesUpdateStartDateList.get(0),
								DrivedRulesUpdateStartDateList.get(DrivedRulesUpdateStartDateList.size() - 1));
						if (days == 0) {
							days = 1;
						}
						parentInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName, days);
						parentInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName, days);
						parentInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName, days);
						parentInitialMinStayRule.put(roomClass, parentInitialMinStayRuleList);
						parentInitialNoCheckInRule.put(roomClass, parentInitialNoCheckInRuleList);
						parentInitialNoCheckOutRule.put(roomClass, parentInitialNoCheckOutRuleList);
					}
				} else if (DrivedRulesUpdateType.equalsIgnoreCase("BulkUpdate")) {

					for (String roomClass : reservationRoomClassesList) {
						ArrayList<String> parentInitialMinStayRuleList = null;
						ArrayList<String> parentInitialNoCheckInRuleList = null;
						ArrayList<String> parentInitialNoCheckOutRuleList = null;
						rateGrid.expandRoomClass(driver, test_steps, roomClass);
						rateGrid.expandChannel(driver, test_steps, roomClass, channelName);
						parentInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
						parentInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
						parentInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								roomClass, channelName,
								Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
						parentInitialMinStayRule.put(roomClass, parentInitialMinStayRuleList);
						parentInitialNoCheckInRule.put(roomClass, parentInitialNoCheckInRuleList);
						parentInitialNoCheckOutRule.put(roomClass, parentInitialNoCheckOutRuleList);
					}
				}

				app_logs.info(parentInitialMinStayRule);
				app_logs.info(parentInitialNoCheckInRule);
				app_logs.info(parentInitialNoCheckOutRule);

				app_logs.info("==========GETTING INITIAL DERIVED RULES VALUES==========");
				test_steps.add("==========GETTING INITIAL DERIVED RULES VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, DrivedRulesUpdateStartDate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);

				if (DrivedRulesUpdateType.equalsIgnoreCase("Overide")) {
					ArrayList<String> DrivedRulesUpdateStartDateList = Utility
							.convertTokenToArrayList(DrivedRulesUpdateStartDate, delim);
					for (String roomClass : reservationRoomClassesList) {
						rateGrid.expandRoomClass(driver, test_steps, roomClass);
						rateGrid.expandChannel(driver, test_steps, roomClass, channelName);

						ArrayList<String> derivedInitialMinStayRuleList = null;
						ArrayList<String> derivedInitialNoCheckInRuleList = null;
						ArrayList<String> derivedInitialNoCheckOutRuleList = null;
						int days = Utility.getNumberofDays(DrivedRulesUpdateStartDateList.get(0),
								DrivedRulesUpdateStartDateList.get(DrivedRulesUpdateStartDateList.size() - 1));
						if (days == 0) {
							days = 1;
						}

						derivedInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								reservationRoomClassesList.get(0), channelName, days);
						derivedInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								reservationRoomClassesList.get(0), channelName, days);
						derivedInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								reservationRoomClassesList.get(0), channelName, days);

						derivedInitialMinStayRule.put(roomClass, derivedInitialMinStayRuleList);
						derivedInitialNoCheckInRule.put(roomClass, derivedInitialNoCheckInRuleList);
						derivedInitialNoCheckOutRule.put(roomClass, derivedInitialNoCheckOutRuleList);
					}

				} else if (DrivedRulesUpdateType.equalsIgnoreCase("BulkUpdate")) {

					for (String roomClass : reservationRoomClassesList) {
						rateGrid.expandRoomClass(driver, test_steps, roomClass);
						rateGrid.expandChannel(driver, test_steps, roomClass, channelName);

						ArrayList<String> derivedInitialMinStayRuleList = null;
						ArrayList<String> derivedInitialNoCheckInRuleList = null;
						ArrayList<String> derivedInitialNoCheckOutRuleList = null;

						derivedInitialMinStayRuleList = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
								reservationRoomClassesList.get(0), channelName,
								Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
						derivedInitialNoCheckInRuleList = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
								reservationRoomClassesList.get(0), channelName,
								Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
						derivedInitialNoCheckOutRuleList = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
								reservationRoomClassesList.get(0), channelName,
								Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));

						derivedInitialMinStayRule.put(roomClass, derivedInitialMinStayRuleList);
						derivedInitialNoCheckInRule.put(roomClass, derivedInitialNoCheckInRuleList);
						derivedInitialNoCheckOutRule.put(roomClass, derivedInitialNoCheckOutRuleList);
					}

				}
				app_logs.info(derivedInitialMinStayRule);
				app_logs.info(derivedInitialNoCheckInRule);
				app_logs.info(derivedInitialNoCheckOutRule);
			}

			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			rateGrid.expandParentRateGrid(driver, "minus");
			getTest_Steps.clear();
			getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			rateGrid.bulkUpdateOverideRules(driver, delim, DrivedRulesUpdateType, DrivedRulesUpdateStartDate,
					DrivedRulesUpdateEndDate, DrivedisSun_RulesUpdate, DrivedisMon_RulesUpdate, DrivedisTue_RulesUpdate,
					DrivedisWed_RulesUpdate, DrivedisThu_RulesUpdate, DrivedisFri_RulesUpdate, DrivedisSat_RulesUpdate,
					derivedRatePlanName, ReservationRoomClasses, channelName, DrivedType_RulesUpdate,
					DrivedRuleMinStayValue_RulesUpdate, test_steps);

			ArrayList<String> parentAfterMinStayRule = null;
			ArrayList<String> parentAfterNoCheckInRule = null;
			ArrayList<String> parentAfterNoCheckOutRule = null;

			if ((DrivedRulesUpdateType.equalsIgnoreCase("Overide")
					|| DrivedRulesUpdateType.equalsIgnoreCase("BulkUpdate"))) {

				app_logs.info("==========GETTING AFTER UPDATE BASE RULES VALUES==========");
				test_steps.add("==========GETTING AFTER UPDATE BASE RULES VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, DrivedRulesUpdateStartDate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);
				rateGrid.expandParentRateGrid(driver, "plus");

				rateGrid.expandRoomClass(driver, test_steps, reservationRoomClassesList.get(0));
				rateGrid.expandChannel(driver, test_steps, reservationRoomClassesList.get(0), channelName);

				// int days = Utility.getNumberofDays(DrivedRulesUpdateStartDate, );

				if (DrivedRulesUpdateType.equalsIgnoreCase("Overide")) {

					ArrayList<String> DrivedRulesUpdateStartDateList = Utility
							.convertTokenToArrayList(DrivedRulesUpdateStartDate, delim);

					int days = Utility.getNumberofDays(DrivedRulesUpdateStartDateList.get(0),
							DrivedRulesUpdateStartDateList.get(DrivedRulesUpdateStartDateList.size() - 1));
					if (days == 0) {
						days = 1;
					}

					parentAfterMinStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
							reservationRoomClassesList.get(0), channelName, days);
					parentAfterNoCheckInRule = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
							reservationRoomClassesList.get(0), channelName, days);
					parentAfterNoCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
							reservationRoomClassesList.get(0), channelName, days);

				} else if (DrivedRulesUpdateType.equalsIgnoreCase("BulkUpdate")) {
					parentAfterMinStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps,
							reservationRoomClassesList.get(0), channelName,
							Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
					parentAfterNoCheckInRule = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps,
							reservationRoomClassesList.get(0), channelName,
							Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
					parentAfterNoCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
							reservationRoomClassesList.get(0), channelName,
							Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate));
				}

				app_logs.info(parentAfterMinStayRule);
				app_logs.info(parentAfterNoCheckInRule);
				app_logs.info(parentAfterNoCheckOutRule);
				int rulesUpdateDiffDate = Utility.getNumberofDays(DrivedRulesUpdateStartDate, DrivedRulesUpdateEndDate)
						+ 1;

				List<Date> rulesUpdateDates = Utility.getDateRangeBetweenfromAndToDate(
						Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedRulesUpdateStartDate),
						Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedRulesUpdateEndDate));
				for (int i = 0; i < rulesUpdateDiffDate; i++) {
					for (String roomClass : reservationRoomClassesList) {
						app_logs.info(
								"==========VERIFING DERIVED RATE PLAN RULES VALUES SHOULD NOT CHANGE WHERE ROOM CLASS IS : "
										+ roomClass + "==========");
						test_steps.add(
								"==========VERIFING DERIVED RATE PLAN RULES VALUES SHOULD NOT CHANGE WHERE ROOM CLASS IS : "
										+ roomClass + "==========");
						try {
							String acctual = parentAfterMinStayRule.get(i);
							String expected = parentInitialMinStayRule.get(roomClass).get(i);
							assertEquals(acctual, expected, "Failed To Verify After Derived Min Stay Rule");
							app_logs.info("Successfully Verified Min Stay Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
							test_steps.add("Successfully Verified Min Stay Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							String acctual = parentAfterNoCheckInRule.get(i);
							String expected = parentInitialNoCheckInRule.get(roomClass).get(i);
							assertEquals(acctual, expected, "Failed To Verify After Derived No Check In Rule");
							app_logs.info("Successfully Verified No Check In Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
							test_steps.add("Successfully Verified No Check In Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							String acctual = parentAfterNoCheckOutRule.get(i);
							String expected = parentInitialNoCheckOutRule.get(roomClass).get(i);
							assertEquals(acctual, expected, "Failed To Verify After Derived No Check Out Rule");
							app_logs.info("Successfully Verified No Check Out Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
							test_steps.add("Successfully Verified No Check Out Rule : " + acctual
									+ " Not Changes for Date : " + rulesUpdateDates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}
					}
				}

			}
			// end rules

			HashMap<String, HashMap<String, String>> getParentInitialRates = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getParentInitialExAdult = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getParentInitialExChild = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedInitialRates = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedInitialExAdult = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getDerivedInitialExChild = new HashMap<String, HashMap<String, String>>();
			if ((DrivedRatesUpdateType.equalsIgnoreCase("Overide")
					|| DrivedRatesUpdateType.equalsIgnoreCase("BulkUpdate"))) {
				app_logs.info("==========GETTING INITIAL BASE RATE VALUES==========");
				test_steps.add("==========GETTING INITIAL BASE RATE VALUES==========");

				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				test_steps.clear();
				test_name = "Verify Bulk Update / Overide Derived Level Rates";
				test_description = "Verify Bulk Update / Overide Derived Level Rates";

				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, DrivedcheckInDate_RatesUpdate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				rateGrid.expandParentRateGrid(driver, "plus");
				derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

				for (String roomClass : reservationRoomClassesList) {

					HashMap<String, String> getParentInitialRatesTemp = new HashMap<String, String>();
					HashMap<String, String> getParentInitialExAdultTemp = new HashMap<String, String>();
					HashMap<String, String> getParentInitialExChildTemp = new HashMap<String, String>();
					HashMap<String, String> getParentInitialRatesPerNightChannelsTemp = new HashMap<String, String>();

					rateGrid.expandRoomClass(driver, test_steps, roomClass);
					test_steps.add("===Get Data From Rate Plan===");

					if (DrivedRatesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> DrivedcheckInDate_RatesUpdateList = Utility
								.convertTokenToArrayList(DrivedcheckInDate_RatesUpdate, delim);
						getParentInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
								DrivedcheckInDate_RatesUpdateList.get(0),
								DrivedcheckInDate_RatesUpdateList.get(DrivedcheckInDate_RatesUpdateList.size() - 1),
								roomClass, channelName);
						app_logs.info(getParentInitialRatesPerNightChannelsTemp);
						getData(getParentInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdateList.get(0));
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdateList.get(DrivedcheckInDate_RatesUpdateList.size() - 1));
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
					} else if (DrivedRatesUpdateType.equalsIgnoreCase("BulkUpdate")) {

						getParentInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
								DrivedcheckInDate_RatesUpdate, DrivedcheckOutDate_RatesUpdate, roomClass, channelName);
						app_logs.info(getParentInitialRatesPerNightChannelsTemp);
						getData(getParentInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdate);
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckOutDate_RatesUpdate);
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
					}
					for (int i = 0; i < dates.size(); i++) {
						String rateIs = StringUtils.substringBetween(
								getParentInitialRatesPerNightChannelsTemp.get(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
								"RRate:", "ExCh:");
						app_logs.info(rateIs);
						String ch = StringUtils
								.substringBetween(
										getParentInitialRatesPerNightChannelsTemp.get(Utility.convertDateFormattoString(
												ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
										"ExCh:", "ExAd:");
						app_logs.info(ch);
						String ad = StringUtils.substringAfter(getParentInitialRatesPerNightChannelsTemp.get(Utility
								.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
								"ExAd:");
						app_logs.info(ad);
						getParentInitialRatesTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), rateIs);
						getParentInitialExAdultTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ad);
						getParentInitialExChildTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ch);
					}
					getParentInitialRates.put(roomClass, getParentInitialRatesTemp);
					getParentInitialExAdult.put(roomClass, getParentInitialExAdultTemp);
					getParentInitialExChild.put(roomClass, getParentInitialExChildTemp);
				}
				app_logs.info(getParentInitialRates);
				app_logs.info(getParentInitialExAdult);
				app_logs.info(getParentInitialExChild);

				app_logs.info("==========GETTING INITIAL DERIVED RATE VALUES==========");
				test_steps.add("==========GETTING INITIAL DERIVED RATE VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, DrivedcheckInDate_RatesUpdate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);

				for (String roomClass : reservationRoomClassesList) {

					HashMap<String, String> getDerivedInitialRatesTemp = new HashMap<String, String>();
					HashMap<String, String> getDerivedInitialExAdultTemp = new HashMap<String, String>();
					HashMap<String, String> getDerivedInitialExChildTemp = new HashMap<String, String>();
					HashMap<String, String> getDerivedInitialRatesPerNightChannelsTemp = new HashMap<String, String>();

					rateGrid.expandRoomClass(driver, test_steps, roomClass);
					test_steps.add("===Get Data From Rate Plan===");
					if (DrivedRatesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> DrivedcheckInDate_RatesUpdateList = Utility
								.convertTokenToArrayList(DrivedcheckInDate_RatesUpdate, delim);
						getDerivedInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
								DrivedcheckInDate_RatesUpdateList.get(0),
								DrivedcheckInDate_RatesUpdateList.get(DrivedcheckInDate_RatesUpdateList.size() - 1),
								roomClass, channelName);
						app_logs.info(getDerivedInitialRatesPerNightChannelsTemp);
						getData(getDerivedInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdateList.get(0));
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdateList.get(DrivedcheckInDate_RatesUpdateList.size() - 1));
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);

					} else if (DrivedRatesUpdateType.equalsIgnoreCase("BulkUpdate")) {
						getDerivedInitialRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
								DrivedcheckInDate_RatesUpdate, DrivedcheckOutDate_RatesUpdate, roomClass, channelName);
						app_logs.info(getDerivedInitialRatesPerNightChannelsTemp);
						getData(getDerivedInitialRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdate);
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckOutDate_RatesUpdate);
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
					}
					for (int i = 0; i < dates.size(); i++) {
						String rateIs = StringUtils.substringBetween(
								getDerivedInitialRatesPerNightChannelsTemp.get(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
								"RRate:", "ExCh:");
						app_logs.info(rateIs);
						String ch = StringUtils
								.substringBetween(
										getDerivedInitialRatesPerNightChannelsTemp
												.get(Utility.convertDateFormattoString(
														ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
										"ExCh:", "ExAd:");
						app_logs.info(ch);
						String ad = StringUtils
								.substringAfter(
										getDerivedInitialRatesPerNightChannelsTemp
												.get(Utility.convertDateFormattoString(
														ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
										"ExAd:");
						app_logs.info(ad);
						getDerivedInitialRatesTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), rateIs);
						getDerivedInitialExAdultTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ad);
						getDerivedInitialExChildTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ch);
					}
					getDerivedInitialRates.put(roomClass, getDerivedInitialRatesTemp);
					getDerivedInitialExAdult.put(roomClass, getDerivedInitialExAdultTemp);
					getDerivedInitialExChild.put(roomClass, getDerivedInitialExChildTemp);
				}
				app_logs.info(getDerivedInitialRates);
				app_logs.info(getDerivedInitialExAdult);
				app_logs.info(getDerivedInitialExChild);

			}

			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			rateGrid.expandParentRateGrid(driver, "minus");
			getTest_Steps.clear();
			getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			rateGrid.bulkUpdateOverideRates(driver, delim, DrivedRatesUpdateType, DrivedcheckInDate_RatesUpdate,
					DrivedcheckOutDate_RatesUpdate, DrivedisSunday_RatesUpdate, DrivedisMonday_RatesUpdate,
					DrivedisTuesday_RatesUpdate, DrivedisWednesday_RatesUpdate, DrivedisThursday_RatesUpdate,
					DrivedisFriday_RatesUpdate, DrivedisSaturday_RatesUpdate, derivedRatePlanName,
					ReservationRoomClasses, channelName, DrivedupdateRatesType, DrivedisUpdateRateByRoomClass,
					DrivednightlyRate_RatesUpdate, DrivedadditionalAdults_RatesUpdate,
					DrivedadditionalChild_RatesUpdate, test_steps);

			HashMap<String, HashMap<String, String>> getParentAfterRates = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getParentAfterExAdult = new HashMap<String, HashMap<String, String>>();
			HashMap<String, HashMap<String, String>> getParentAfterExChild = new HashMap<String, HashMap<String, String>>();
			if ((DrivedRatesUpdateType.equalsIgnoreCase("Overide")
					|| DrivedRatesUpdateType.equalsIgnoreCase("BulkUpdate"))) {

				app_logs.info("==========GETTING AFTER UPDATE BASE RATE VALUES==========");
				test_steps.add("==========GETTING AFTER UPDATE BASE RATE VALUES==========");
				driver.navigate().refresh();
				rateGrid.clickForRateGridCalender(driver, test_steps);
				rateGrid.selectDateFromDatePicker(driver, DrivedcheckInDate_RatesUpdate,
						ratesConfig.getProperty("defaultDateFormat"), test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);

				for (String roomClass : reservationRoomClassesList) {

					HashMap<String, String> getParentAfterRatesTemp = new HashMap<String, String>();
					HashMap<String, String> getParentAfterExAdultTemp = new HashMap<String, String>();
					HashMap<String, String> getParentAfterExChildTemp = new HashMap<String, String>();
					HashMap<String, String> getParentAfterRatesPerNightChannelsTemp = new HashMap<String, String>();

					rateGrid.expandRoomClass(driver, test_steps, roomClass);
					test_steps.add("===Get Data From Rate Plan===");

					if (DrivedRatesUpdateType.equalsIgnoreCase("Overide")) {
						ArrayList<String> DrivedcheckInDate_RatesUpdateList = Utility
								.convertTokenToArrayList(DrivedcheckInDate_RatesUpdate, delim);
						getParentAfterRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
								DrivedcheckInDate_RatesUpdateList.get(0),
								DrivedcheckInDate_RatesUpdateList.get(DrivedcheckInDate_RatesUpdateList.size() - 1),
								roomClass, channelName);
						app_logs.info(getParentAfterRatesPerNightChannelsTemp);
						getData(getParentAfterRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdateList.get(0));
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdateList.get(DrivedcheckInDate_RatesUpdateList.size() - 1));
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);

					} else if (DrivedRatesUpdateType.equalsIgnoreCase("BulkUpdate")) {
						getParentAfterRatesPerNightChannelsTemp = rateGrid.getRoomRatesExAdExChOfChannel(driver,
								DrivedcheckInDate_RatesUpdate, DrivedcheckOutDate_RatesUpdate, roomClass, channelName);
						app_logs.info(getParentAfterRatesPerNightChannelsTemp);
						getData(getParentAfterRatesPerNightChannelsTemp);

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckInDate_RatesUpdate);
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								DrivedcheckOutDate_RatesUpdate);
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);
					}
					for (int i = 0; i < dates.size(); i++) {
						String rateIs = StringUtils.substringBetween(
								getParentAfterRatesPerNightChannelsTemp.get(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
								"RRate:", "ExCh:");
						app_logs.info(rateIs);
						String ch = StringUtils
								.substringBetween(
										getParentAfterRatesPerNightChannelsTemp.get(Utility.convertDateFormattoString(
												ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
										"ExCh:", "ExAd:");
						app_logs.info(ch);
						String ad = StringUtils.substringAfter(getParentAfterRatesPerNightChannelsTemp.get(Utility
								.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),
								"ExAd:");
						app_logs.info(ad);
						getParentAfterRatesTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), rateIs);
						getParentAfterExAdultTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ad);
						getParentAfterExChildTemp.put(Utility.convertDateFormattoString(
								ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ch);
					}
					getParentAfterRates.put(roomClass, getParentAfterRatesTemp);
					getParentAfterExAdult.put(roomClass, getParentAfterExAdultTemp);
					getParentAfterExChild.put(roomClass, getParentAfterExChildTemp);
				}
				app_logs.info(getParentAfterRates);
				app_logs.info(getParentAfterExAdult);
				app_logs.info(getParentAfterExChild);

				for (String roomClass : reservationRoomClassesList) {
					app_logs.info("==========VERIFING BASE RATE PLAN RATES VALUES SHOULD NOT CHANGE WHERE ROOM CLASS : "
							+ roomClass + "==========");
					test_steps
							.add("==========VERIFING BASE RATE PLAN RATES VALUES SHOULD NOT CHANGE WHERE ROOM CLASS : "
									+ roomClass + "==========");
					for (int i = 0; i < dates.size(); i++) {

						String keyDate = Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),
								dates.get(i));
						try {
							String acctual = getParentAfterRates.get(roomClass).get(keyDate);
							String expected = getParentInitialRates.get(roomClass).get(keyDate);
							assertEquals(acctual, expected, "Failed To Verify After Parent Base Rates");
							app_logs.info("Successfully Verified Base Rates : " + acctual + " Not Changes for Date : "
									+ dates.get(i));
							test_steps.add("Successfully Verified Base Rates : " + acctual + " Not Changes for Date : "
									+ dates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							String acctual = getParentAfterExAdult.get(roomClass).get(keyDate);
							String expected = getParentInitialExAdult.get(roomClass).get(keyDate);
							assertEquals(acctual, expected, "Failed To Verify After Parent Extra Adult Rates");
							app_logs.info("Successfully Verified Extra Adult Rates : " + acctual
									+ " Not Changes for Date : " + dates.get(i));
							test_steps.add("Successfully Verified Extra Adult Rates : " + acctual
									+ " Not Changes for Date : " + dates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							String acctual = getParentAfterExChild.get(roomClass).get(keyDate);
							String expected = getParentInitialExChild.get(roomClass).get(keyDate);
							assertEquals(acctual, expected, "Failed To Verify After Parent Extra Child Rates");
							app_logs.info("Successfully Verified Extra Child Rates : " + acctual
									+ " Not Changes for Date : " + dates.get(i));
							test_steps.add("Successfully Verified Extra Child Rates : " + acctual
									+ " Not Changes for Date : " + dates.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						}
					}
				}
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
		
		HashMap<String, String> names = null;
		HashMap<String, String> policyClauses = null;
		HashMap<String, String> guestsWillIncurAFee = null;
		HashMap<String, String> chargesType = new HashMap<String, String>();
		HashMap<String, String> cancelWithInType = null;
		HashMap<String, String> noOfDays = null;
		int MRBDays = 0;
		try {

			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_name = "Verify Derived Rate Plan In " + ReservationType + " Reservations Where Check In Date : " + CheckInDate + " & CheckOut Date : " + CheckOutDate;
			test_description = "Verify Derived Rate Plan In " + ReservationType + " Reservations Where Check In Date : " + CheckInDate + " & CheckOut Date : " + CheckOutDate;
			test_steps.clear();

			test_steps.add("=================== GETTING RATE PLAN RESTRICTION AND RULES ======================");
			app_logs.info("=================== GETTING RATE PLAN RESTRICTION AND RULES ======================");

			if (ReservationType.equalsIgnoreCase("MRB")) {
				String[] MRBCheckIn = CheckInDate.split("\\|");
				String[] MRBCheckOut = CheckOutDate.split("\\|");
				String[] roomClass = ReservationRoomClasses.split("\\|");

				for (int k = 0; k < roomClass.length; k++) {

					rateGrid.clickForRateGridCalender(driver, test_steps);
					rateGrid.selectDateFromDatePicker(driver, MRBCheckIn[k],
							ratesConfig.getProperty("defaultDateFormat"), test_steps);
					Wait.waitUntilPageLoadNotCompleted(driver, 50);
					rateGrid.clickRatePlanArrow(driver, test_steps);
					rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
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
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
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

				app_logs.info("promoCode : " + promoCode);

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

				String[] chkIn = CheckInDate.split("\\|");
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
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

				rateGrid.clickOnEditRatePlan(driver);
				nightlyRate.switchProductsTab(driver, test_steps);

				products = ratePackage.getAllProducts(driver);
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

				String[] rm = ReservationRoomClasses.split("\\|");
				driver.navigate().refresh();

				HashMap<String, // RoomClass
						HashMap<String, // Source
								HashMap<String, // date
										RatesGridChannelRatesRules>>> roomClassWiseSourceDerivedRatesRule = rateGrid
												.getSourceWiseRatesRules(driver, test_steps, packageRatePlanName,
														derivedRatePlanName, true, delim, ReservationRoomClasses,
														channelName, CheckInDate, CheckOutDate);

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
						app_logs.info(roomClassWiseSourceDerivedRatesRule.get(selectedRoomClasses.get(0))
								.get(selectedChannels.get(0)).get(date));
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

			} else {
				rateGrid.clickForRateGridCalender(driver, test_steps);
				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");

				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				rateGrid.expandParentRateGrid(driver, "minus");
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus",
						getTest_Steps);
				test_steps.addAll(getTest_Steps);
				rateGrid.expandRoomClass(driver, test_steps, reservationRoomClassesList.get(0));
				rateGrid.expandChannel(driver, test_steps, reservationRoomClassesList.get(0), channelName);

				minStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationRoomClasses,
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

				noCheckInRule = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationRoomClasses,
						channelName, days);

				app_logs.info("noCheckInRule : " + noCheckInRule);

				checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule,
						CheckInDate, CheckOutDate);

				app_logs.info("checkInColor : " + checkInColor);

				noCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationRoomClasses,
						channelName, days);

				app_logs.info("noCheckOutRule : " + noCheckOutRule);

				checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, noCheckInRule, noCheckOutRule,
						CheckInDate, CheckOutDate);
				app_logs.info("checkOutColor : " + checkOutColor);

				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
				derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, test_steps);
				folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);

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

				restricrionsLengthOfStay = reservationPage.verifylenthOfStayRestrictions(driver, test_steps,
						verifyLenthOfStayChecked, verifyMinStayCondidtion, verifyMaxStayCondition, addStayofLength,
						days);

				app_logs.info(addStayofLength);
				app_logs.info(restricrionsLengthOfStay);

				restricrionsBookingWindow = reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
						isBookinWindow, CheckInDate, CheckOutDate, bookingWindowRestrictions);

				app_logs.info("isBookinWindow : " + isBookinWindow);
				app_logs.info("restricrionsBookingWindow : " + restricrionsBookingWindow);

				isPromocode = rateGrid.isPromoCodeChecked(driver, test_steps);

				if (isPromocode) {
					derivedPromoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					derivedPromoCode = "";
				}
				app_logs.info("promoCode : " + derivedPromoCode);

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				}

				nightlyRate.switchCalendarTab(driver, test_steps);

//				HashMap<Integer, String> map = new HashMap<>();
//				map = derivedRate.getCustomDates(driver, "Custom date range", "Start Date");
//				test_steps.add("Custom start date ranges :  " + map);
//				app_logs.info("Custom start date ranges :  " + map);
//				map = derivedRate.getCustomDates(driver, "Custom date range", "End Date");
//				test_steps.add("Custom end date ranges :  " + map);
//				app_logs.info("Custom end date ranges :  " + map);

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
					navigation.RatesGrid(driver);
					test_steps.add("Navigated to RatesGrid");
					rateGrid.clickRatePlanArrow(driver, test_steps);
					rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
					derivedRate.expandReduceDerivedratePlans(driver, false, test_steps);

					rateGrid.clickOnEditRatePlan(driver);
					nightlyRate.switchProductsTab(driver, test_steps);

					products = ratePackage.getAllProducts(driver);
					nightlyRate.switchCalendarTab(driver, test_steps);

					if (!ReservationType.equalsIgnoreCase("MRB")) {
						nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
						nightlyRate.clickEditThisSeasonButton(driver, test_steps);

						capacityAdult.put(RoomClass[0], nightlyRate.getAdultCapacity(driver, RoomClass[0]));
						capacityChild.put(RoomClass[0], nightlyRate.getChildCapacity(driver, RoomClass[0]));
						maxAdult.put(RoomClass[0], nightlyRate.getMaxAdult(driver, RoomClass[0]));
						maxChild.put(RoomClass[0], nightlyRate.getMaxPersons(driver, RoomClass[0]));
						test_steps.add("Room Class : " + RoomClass[0] + " Adult Capacity: <b>"
								+ capacityAdult.get(RoomClass[0]) + "</b>");
						test_steps.add("Room Class : " + RoomClass[0] + " Person's Capacity: <b>"
								+ capacityChild.get(RoomClass[0]) + "</b>");
						test_steps.add("Rate Plan Max. Adults: <b>" + maxAdult.get(RoomClass[0]) + "</b>");
						test_steps.add("Rate Plan Max. Childs: <b>" + maxChild.get(RoomClass[0]) + "</b>");

					}

					nightlyRate.closeSeason(driver, test_steps);
					rateGrid.closeOpendTabInMainMenu(driver);
//					nightlyRate.clickSaveAsActive(driver, test_steps);

//					rateGrid.clickForRateGridCalender(driver, test_steps);
					if (!ReservationType.equalsIgnoreCase("MRB")) {

						navigation.ratesGrid(driver);

						HashMap<String, // RoomClass
								HashMap<String, // Source
										HashMap<String, // date
												RatesGridChannelRatesRules>>> roomClassWiseSourceDerivedRatesRule = rateGrid
														.getSourceWiseRatesRules(driver, test_steps, packageRatePlanName,
																derivedRatePlanName, true, delim, RoomClass[0],
																channelName, CheckInDate, CheckOutDate);

						allDatesBW = Utility.getAllDatesBetweenTwoDates(CheckInDate, CheckOutDate);

						for (String date : allDatesBW) {
							app_logs.info(date);
							app_logs.info("Found Room Rates : "
									+ roomClassWiseSourceDerivedRatesRule.get(RoomClass[0]).get(channelName).get(date));
							test_steps.add("Found Room Rates : "
									+ roomClassWiseSourceDerivedRatesRule.get(RoomClass[0]).get(channelName).get(date));
							RatesGridChannelRatesRules obj = roomClassWiseSourceDerivedRatesRule.get(RoomClass[0])
									.get(channelName).get(date);

							getRates.put(date, obj.getRooomRate());
							getExAdult.put(date, obj.getExtraAdultRate());
							getExChild.put(date, obj.getExtraChildRate());
						}

						Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								CheckInDate);
						app_logs.info("Start Date: " + fromDate);
						Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
								CheckOutDate);
						app_logs.info("End Date: " + toDate);
						dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
						app_logs.info("Dates Are: " + dates);

					}

				} else {
					app_logs.info("No Season For Desired Date");
					nightlyRate.clickSaveRatePlan(driver, test_steps);
					Wait.wait5Second();
				}

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
			navigation.cpReservation_Backward(driver);
		} catch (Exception e) {
			Actions actions = new Actions(driver);

			actions.sendKeys(Keys.ENTER);
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
		CardExpDate = Utility.getFutureMonthAndYearForMasterCard();
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
				if (derivedPromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, derivedPromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, derivedPromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);

				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					app_logs.info(getRates);
					app_logs.info(maxAdult.get(ReservationRoomClasses));
					app_logs.info(maxChild.get(ReservationRoomClasses));
					app_logs.info(getExAdult);
					app_logs.info(getExChild);

					
					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist && isSeasonExist) {
						String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
								maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult,
								getExChild, capacityChild.get(ReservationRoomClasses),
								capacityAdult.get(ReservationRoomClasses), adult, children, test_steps,
								ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
								ratesConfig.getProperty("defaultDateFormat"));
						Utility.app_logs.info(rateIs);
						
						HashMap<String, String> dateWiseAmount = ratePackage.calculateDateWiseProductAmount(products,
								CheckInDate, CheckOutDate, adult, children);

						ratePackage.verifyProductsRates(driver, dateWiseAmount, CheckInDate, CheckOutDate,
								ReservationRoomClasses, getRates, test_steps);

						String productAmounts = ratePackage.calculateProductAmount(dateWiseAmount, CheckInDate,
								CheckOutDate);

						//rateIs = (Float.parseFloat(rateIs) + Float.parseFloat(productAmounts)) + "";

						Utility.app_logs.info(productAmounts);
						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
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
								Utility.app_logs.info("noCheckinColor");
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
						depositAmount = reservationPage.deposit(driver, test_steps, "No", "10");
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
						status = reservationPage.get_ReservationStatus(driver, test_steps);
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

						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

						test_steps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
						try {
							// assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							test_steps.add("Rate plan folio name is matched : " + derivedRatePlanName);
							app_logs.info("Rate plan folio name is matched : " + derivedRatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, test_steps);
						reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
						reervationDepositPolicy = reservationPage.getReservationDepositPolicy(driver);
						reervationCheckInPolicy = reservationPage.getReservationCheckInPolicy(driver);

						String ratePlanCheckInPolicy = getRateLevelPolicy.get("Check-in");

						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
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
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
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

				String[] roomClass = ReservationRoomClasses.split("\\|");

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

					String[] roomClass1 = ReservationRoomClasses.split("\\|");
					String[] adu = adult.split("\\|");
					String[] child = children.split("\\|");

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

					String[] ratePlan = Rateplan.split("\\|");
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
				group.Billinginfo(driver);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Save(driver, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				Account = AccountName;
			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", test_name, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", test_name, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			String reservationNumber = null;
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
				if (derivedPromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, derivedPromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, derivedPromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);

				String minStayColor = "";
				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
							|| isRoomClassAvailable) {
						app_logs.info(getRates);
						app_logs.info(maxAdult.get(ReservationRoomClasses));
						app_logs.info(maxChild.get(ReservationRoomClasses));
						app_logs.info(getExAdult);
						app_logs.info(getExChild);
						String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
								maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult,
								getExChild, capacityChild.get(ReservationRoomClasses),
								capacityAdult.get(ReservationRoomClasses), adult, children, test_steps,
								ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
								ratesConfig.getProperty("defaultDateFormat"));
						Utility.app_logs.info(rateIs);

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
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
								Utility.app_logs.info("noCheckinColor");
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
								ReservationRoomClasses, "No", Account, noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);

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

						getTest_Steps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						isReservationCreated = true;

						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);
						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

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

						reservationPage.click_DeatilsTab(driver, test_steps);
						reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
						reervationDepositPolicy = reservationPage.getReservationDepositPolicy(driver);
						reervationCheckInPolicy = reservationPage.getReservationCheckInPolicy(driver);

						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
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
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
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
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", test_name, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", test_name, "Reservation",
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
				group.Billinginfo(driver);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Save(driver, getTest_Steps);
				test_steps.addAll(getTest_Steps);

			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", test_name, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", test_name, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			// Create RoomBlock

			try {

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
				getTest_Steps = group.searchRoomsForBlock(driver, CheckInDate, CheckOutDate, RoomPerNight);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.SelectRatePlan(driver, derivedRatePlanName);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.ClickSearchGroup(driver);
				test_steps.addAll(getTest_Steps);

				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					advgroup.verifyAvgRate(driver, delim, ReservationRoomClasses, ratePerNight, test_steps);
				} else {
					advgroup.verifyAvgRate(driver, delim, ReservationRoomClasses, "0", test_steps);
				}

				advgroup.updatedAutomaticallyAssignedRooms(driver, "0");
				advgroup.BlockRoomForSelectedRoomclass(driver, RoomPerNight, ReservationRoomClasses);

				getTest_Steps.clear();
				getTest_Steps = group.clickCreateBlock(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				group.Save(driver, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				group.navigateRoomBlock(driver, test);
				Utility.app_logs.info("Navigate to Room Block Tab");
				test_steps.add("Navigate to Room Block Tab");

				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					group.verifyTotalAndNightlyRate(driver, delim, ReservationRoomClasses, ratePerNight,
							test_steps);
				} else {
					group.verifyTotalAndNightlyRate(driver, delim, ReservationRoomClasses, "0", test_steps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", test_name, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", test_name, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (ReservationType.equalsIgnoreCase("Account")) {

			String AccountNumber = null;
			// New account
			try {
				navigation.Accounts(driver);
				test_steps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");
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
				if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					CreateTA.ClickFolio(driver);
					CreateTA.navigateFolioOptions(driver);
					if (policyType.equalsIgnoreCase("No Show")) {
						CreateTA.selectNoShowPolicyForAccount(driver, test_steps, policyName);
					} else if (policyType.equalsIgnoreCase("Check In")) {
						CreateTA.selectPolicyForAccount(driver, policyTypes, policyName, policyType,
								selectedpolicyNames, isAccountPolicyCreate);
					}
				}
				getTest_Steps.clear();
				getTest_Steps = CreateTA.AccountSave(driver, test, AccountName, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				Account = AccountName;
			} catch (Exception e) {

				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount",
							driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			String reservationNumber = null;
			try {
				CreateTA.NewReservationButton(driver, test);
				test_steps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, "2");
				reservationPage.enter_Children(driver, test_steps, "0");
				if (derivedPromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, derivedPromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, derivedPromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
							maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult,
							getExChild, capacityChild.get(ReservationRoomClasses),
							capacityAdult.get(ReservationRoomClasses), adult, children, test_steps,
							ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);
					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
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
								Utility.app_logs.info("noCheckinColor");
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

//						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
//								ReservationRoomClasses, "Yes", Account, noCheckinColor, noCheckoutColor, minStayColor,
//								minStayRuleValue);

						reservationPage.select_RoomWithRatePlanRulesValidationAccount(driver, test_steps,
								ReservationRoomClasses, "Yes", Account, isAccountPolicyApplicable, noCheckinColor,
								noCheckoutColor, minStayColor, minStayRuleValue);

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

						getTest_Steps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						isReservationCreated = true;

						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);
						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

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

						reservationPage.click_DeatilsTab(driver, test_steps);
						reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
						reervationDepositPolicy = reservationPage.getReservationDepositPolicy(driver);
						reervationCheckInPolicy = reservationPage.getReservationCheckInPolicy(driver);

						String ratePlanCheckInPolicy = getRateLevelPolicy.get("Check-in");

						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
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
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
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
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", test_name, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", test_name, "Reservation",
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
				reservationPage.enter_Adults(driver, test_steps, "2");
				reservationPage.enter_Children(driver, test_steps, "0");
				if (derivedPromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, derivedPromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, derivedPromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						|| isRoomClassAvailable) {

					String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
							maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult,
							getExChild, capacityChild.get(ReservationRoomClasses),
							capacityAdult.get(ReservationRoomClasses), adult, children, test_steps,
							ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);
					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
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
								Utility.app_logs.info("noCheckinColor");
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
						double depositAmount = reservationPage.deposit(driver, test_steps, "Yes", "10");
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
						status = reservationPage.get_ReservationStatus(driver, test_steps);
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						reservationPage.get_RoomNumber(driver, test_steps, "Yes");
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);

						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

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

						reservationPage.click_DeatilsTab(driver, test_steps);

						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, derivedRatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
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
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
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

						reservationPage.clickBookQuote(driver, test_steps);

						Wait.wait10Second();
						TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);
						reservationPage.click_Folio(driver, test_steps);
						folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

						reservationPage.click_DeatilsTab(driver, test_steps);
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
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
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
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", test_name, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", test_name, "Group", driver);
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

				if (derivedPromoCode.isEmpty()) {
					tapechart.searchInTapechart(driver, test_steps, Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yyyy"),
							Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MM/dd/yyyy"), adult, children,
							derivedRatePlanName, derivedPromoCode);
				} else {
					tapechart.searchInTapechart(driver, test_steps, Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yyyy"),
							Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MM/dd/yyyy"), adult, children,
							derivedRatePlanName, derivedPromoCode);
				}

				String rateIs = "";
				try {
					rateIs = tapechart.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
							maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult,
							getExChild, capacityChild.get(ReservationRoomClasses),
							capacityAdult.get(ReservationRoomClasses), adult, children, test_steps,
							ratesConfig.getProperty("tapeChartMsg"), ReservationRoomClasses, CheckInDate, CheckOutDate,
							ratesConfig.getProperty("defaultDateFormat"), RoomAbbri.get(0));
				} catch (Exception e) {
					Utility.catchException(driver, e, "Verify Rates On TapeChart Page", "Tape Chart", "Tape Chart",
							test_name, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Rates On TapeChart Page", "Tape Chart", "Tape Chart",
							test_name, test_description, test_catagory, test_steps);
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
					reservationPage.verifyTripSummaryRoomChargesAfterCreateReservationFromTapeChart(driver, test_steps,
							rateIs);
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
					status = reservationPage.get_ReservationStatus(driver, test_steps);
					isReservationCreated = true;
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					reservationPage.get_RoomNumber(driver, test_steps, "Yes");
					String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
							test_steps);

					test_steps.add("=================== Verify Rate Plan In Reservation ======================");
					app_logs.info("=================== Verify Rate Plan In Reservation ======================");
					String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, derivedRatePlanName);

					try {
						assertTrue(ratePlan.trim().equalsIgnoreCase(derivedRatePlanName),
								"Rate plan is not matched for room class");
						test_steps.add("Rate plan is matched for room class for : " + derivedRatePlanName);
						app_logs.info("Rate plan is matched for room class for : " + derivedRatePlanName);
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}

					reservationPage.click_Folio(driver, test_steps);
					String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

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

					reservationPage.click_DeatilsTab(driver, test_steps);

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

				} else {
					tapechart.verifyNoResultsmatchedInTapechart(driver, test_steps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (isReservationCreated) {

			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_name = "Verify Different Actions on Created " + ReservationType + " Reservations";
			test_description = "Verify Different Actions on Created " + ReservationType + " Reservations";
			test_steps.clear();

			afterReservationDone(delim, ActionOnReservation, reservation, CheckInDate, CheckOutDate, GuestFirstName,
					isAccountPolicyApplicable, isAccountPolicyCreate, FeesGuestMustPay, PercentOfFee, FeeChargesType,
					ReservationRoomClasses, test_name, ReservationType, adult, children, ChangeResStartDate,
					ChangeResEndDate, packageRatePlanName, derivedRatePlanName, derivedPromoCode,
					ReservationChangeRoomClass, names, policyClauses, guestsWillIncurAFee, chargesType,
					cancelWithInType, noOfDays, ChangeAdult, ChangeChildren);
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
		return Utility.getData("VerifyDerivedPackageNightlyRate", envLoginExcel);
	}

	// @AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

	public void afterReservationDone(String delim, String ActionOnReservation, String reservation, String CheckInDate,
			String CheckOutDate, String GuestFirstName, String isAccountPolicyApplicable, String isAccountPolicyCreate,
			String FeesGuestMustPay, String PercentOfFee, String FeeChargesType, String ReservationRoomClasses,
			String test_name1, String ReservationType, String adult, String children, String ChangeResStartDate,
			String ChangeResEndDate, String packageRatePlanName, String derivedRatePlanName, String derivedPromoCode,
			String ReservationChangeRoomClass, HashMap<String, String> names, HashMap<String, String> policyClauses,
			HashMap<String, String> guestsWillIncurAFee, HashMap<String, String> chargesType,
			HashMap<String, String> cancelWithInType, HashMap<String, String> noOfDays, String ChangeAdult,
			String ChangeChildre) {

		try {
			if (!ReservationType.equalsIgnoreCase("MRB")) {
			// PackagePlanFolio - Per Stay Component(s)
			// PackagePlanFolio- Per Night Component(s)
			String expectedAmountPerStay = ratePackage.calculateProductAmount_CalculateOnStay(products,
					CheckInDate, CheckOutDate, adult, children);
			app_logs.info(expectedAmountPerStay);
			String expectedAmountPerNight = ratePackage.calculateProductAmount_CalculateOnNight(products,
					CheckInDate, CheckOutDate, adult, children);
			app_logs.info(expectedAmountPerNight);

			reservationPage.verifyIncidentalsLineItemAmount(driver,
					"Per Stay Component(s)", expectedAmountPerStay, test_steps,1);
			reservationPage.verifyIncidentalsLineItemAmount(driver,
					"Per Night Component(s)", expectedAmountPerNight, test_steps,1);
			}
			HashMap<String, String> getdepositAmount = new HashMap<String, String>();
			HashMap<String, Double> getcheckInAmount = new HashMap<String, Double>();
			ArrayList<HashMap<String, String>> getdepositPoliciesData = new ArrayList<HashMap<String, String>>();
			String depositAmount = null;
			String depositPolicyApplied = "";
			reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
			reervationDepositPolicy = reservationPage.getReservationDepositPolicy(driver);
			reervationCheckInPolicy = reservationPage.getReservationCheckInPolicy(driver);

			String rateDepositPlanPolicy = getRateLevelPolicy.get("Deposit");

			app_logs.info("seasonDepositpolicy : " + rateDepositPlanPolicy);
			app_logs.info("reervationDepositPolicy : " + reervationDepositPolicy);
			if (rateDepositPlanPolicy.equalsIgnoreCase("NA")) {
				rateDepositPlanPolicy = "No Policy";
			}

			if (rateDepositPlanPolicy.equalsIgnoreCase("NA")) {
				rateDepositPlanPolicy = "No Policy";
			}
			test_steps.add("=================== Verify the deposit policy in reservation ======================");
			app_logs.info("=================== Verify the deposit policy in reservation ======================");

			if (rateDepositPlanPolicy.equalsIgnoreCase(reervationDepositPolicy)) {
				assertTrue(rateDepositPlanPolicy.equalsIgnoreCase(reervationDepositPolicy),
						"Reservation deposit policy is not matched");
				test_steps
						.add("Verified Reservation deposit policy is reservation policies : " + rateDepositPlanPolicy);
				app_logs.info("Verified Reservation deposit policy is reservation policies : " + rateDepositPlanPolicy);
			} else if (rateDepositPlanPolicy.equalsIgnoreCase(reervationDepositPolicy)) {
				assertTrue(rateDepositPlanPolicy.equalsIgnoreCase(reervationDepositPolicy),
						"Reservation deposit policy is not matched");
				test_steps
						.add("Verified Reservation deposit policy is reservation policies : " + rateDepositPlanPolicy);
				app_logs.info("Verified Reservation deposit policy is reservation policies : " + rateDepositPlanPolicy);
			}
			reservationPage.close_FirstOpenedReservation(driver, test_steps);

			if (!reervationDepositPolicy.equalsIgnoreCase("No Policy")) {
				navigation.inventory(driver);

				navigation.policies(driver, test_steps);

//				if (CheckInDate.split("\\|").length > 1) {
//					for (String str : getRateLevelPolicy.get("Deposit")) {
//						getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps,
//								ratesConfig.getProperty("depositPolicyText"), str));
//					}
//				} else {
//					getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps,
//							ratesConfig.getProperty("depositPolicyText"), seasonDepositPolicy.get(0)));
//				}

				getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps,
						ratesConfig.getProperty("depositPolicyText"), getRateLevelPolicy.get("Deposit")));
				Utility.app_logs.info(getdepositPoliciesData);
				reservationPage.navigateToReservationPage(driver);
			}
			try {
				navigation.reservation(driver);
			} catch (Exception e) {
				// TODO: handle exception
			}
			app_logs.info(reservation);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			try {
				reservationPage.clickFolio(driver, test_steps);
			} catch (Exception e) {
				reservationPage.close_FirstOpenedReservation(driver, test_steps);
				driver.navigate().refresh();
				reservationPage.Search_ResNumber_And_Click(driver, reservation);
			}
			HashMap<String, String> roomChargesAre = new HashMap<String, String>();
			ArrayList<String> roomCharges = new ArrayList<>();

			String[] chkin = CheckInDate.split("\\|");
			String[] chkout = CheckOutDate.split("\\|");
			test_steps.add("==================Verify Deposit Policy On Reservation==================");

			roomChargesAre = reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, chkin[0],
					chkout[0], true);
			for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
				roomCharges.add(entry.getValue());
			}
			if (GuestFirstName.split("\\|").length > 1) {
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(1), Rooms.get(1));
				roomChargesAre = reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, chkin[1],
						chkout[1], true);
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
				} else if (isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(0))
						.equals(ratesConfig.getProperty("noPolicyText")))) {
					depositAmount = reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
							getAccountPoliciesData.get(0).get("Type"), getAccountPoliciesData.get(0).get("AttrValue"));
				}
			} else if (!rateDepositPlanPolicy.equalsIgnoreCase("No Policy")) {
				if (CheckInDate.split("\\|").length > 1) {
//					for (int i = 0; i < seasonDepositPolicy.size(); i++) {
					String size = String.valueOf(ReservationRoomClasses.split("\\|").length);
					getdepositAmount.put(rateDepositPlanPolicy,
							reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharges,
									getdepositPoliciesData.get(0).get("Type"),
									getdepositPoliciesData.get(0).get("AttrValue"), size));
//					}
					ArrayList<Double> dbr = new ArrayList<Double>();
					for (Map.Entry<String, String> entry : getdepositAmount.entrySet()) {
						dbr.add(Double.valueOf(entry.getValue()));
					}
					DecimalFormat df = new DecimalFormat("0.00");
					df.setMaximumFractionDigits(2);
					depositAmount = df.format(Collections.max(dbr));
					app_logs.info(depositAmount);
					depositPolicyApplied = Utility.getKeyfromHashmap(getdepositAmount, depositAmount);

				} else {
					depositAmount = reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre,
							getdepositPoliciesData.get(0).get("Type"), getdepositPoliciesData.get(0).get("AttrValue"));
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
			app_logs.info(paymentTypeIs);
			if (isAccountPolicyApplicable.equals("Yes")) {
				if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					verificationDepositWithPolicy(PaymentType, policyNames.get(0), depositAmount, paymentTypeIs);
				} else if (isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(0))
						.equals(ratesConfig.getProperty("noPolicyText")))) {
					verificationDepositWithPolicy(PaymentType, selectedpolicyNames.get(policyTypes.get(0)),
							depositAmount, paymentTypeIs);
				} else {
					verificationDepoistWithoutPolicy(PaymentType, CheckInDate, ratesConfig.getProperty("noPolicyText"));
				}
			} else {
				if (!reervationDepositPolicy.equalsIgnoreCase("No Policy")) {
					verificationDepositWithPolicy(PaymentType, reervationDepositPolicy, depositAmount, paymentTypeIs);
				} else {
					verificationDepoistWithoutPolicy(PaymentType,
							Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")),
							ratesConfig.getProperty("noPolicyText"));
				}
			}
			reservationPage.click_DeatilsTab(driver, test_steps);
			if (ActionOnReservation.equalsIgnoreCase("No Show")) {
				String seasonNoShowpolicy = getRateLevelPolicy.get("No Show");
//				String seasonNoShowpolicy = getSessionLevelPolicy.get("No Show");

				if (seasonNoShowpolicy.equalsIgnoreCase("NA")) {
					seasonNoShowpolicy = "No Policy";
				}
				String policyToValidate = "No Policy", policyAttrDisplayed, policyAttrValueDisplayed, policyDesc = null,
						paymentsFromFolio = null;

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
						test_steps.add("========== Capturing all policies attribute and attribute values ==========");
						allPoliciesDetails = policy.getAllPoliciesDetails(driver, test_steps, "No Show",
								seasonNoShowpolicy);
					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
							Utility.updateReport(e, "Failed to capture no show policy attributes and values", test_name,
									test_catagory, driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.updateReport(e, "Failed to capture no show policy attributes and values", test_name,
									test_catagory, driver);
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
							checkOutDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
					} else {
						checkInDates = Utility.splitInputData(CheckInDate);
						checkOutDates = Utility.splitInputData(CheckOutDate);
					}
					try {
						test_steps.add("========== Evaluating which policy to be applied on reservation ==========");
						if (isAccountPolicyApplicable.equalsIgnoreCase("Yes")) {
							policyToValidate = policyName;
							policyAttrDisplayed = FeesGuestMustPay;
							policyAttrValueDisplayed = PercentOfFee;
							policyDesc = descWhileCreatePolicy.get(policyName).get(0);
							// test_steps.add("Season level policy should be applied for check-in date as
							// <b>"+checkInDate+"</b>");

						} else if (!(allPoliciesDetails.get("Names").isEmpty())) {
							reservationPage.click_Folio(driver, test_steps);
							highestAmountOfPolicyDetails = reservationPage.getHighestAmountOfPolicy(driver, test_steps,
									allPoliciesDetails, checkInDates, checkOutDates);
							policyToValidate = highestAmountOfPolicyDetails.get("Name");
							Utility.app_logs.info("policyToValidate : " + policyToValidate);
							policyAttrDisplayed = highestAmountOfPolicyDetails.get("Attribute");
							policyAttrValueDisplayed = highestAmountOfPolicyDetails.get("AttributeValue");
							policyDesc = highestAmountOfPolicyDetails.get("Description");
							paymentsFromFolio = highestAmountOfPolicyDetails.get("Payments");
							test_steps.add(
									"========== Calculating no show amounts for different guests based on rate plan ==========");
							noShowAmounts = reservationPage.getNoShowPaymentsForAllGuests(driver, test_steps,
									checkInDates, checkOutDates, policyAttrDisplayed, policyAttrValueDisplayed);
							noShowAmount = reservationPage.getTotalOfNoShowAmounts(noShowAmounts);

						}
					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
							Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation",
									test_name, test_catagory, driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation",
									test_name, test_catagory, driver);
						} else {
							Assert.assertTrue(false);
						}
					}
				}

				test_steps.add("=================== Verify the no show policy in reservation ======================");
				app_logs.info("=================== Verify the no show policy in reservation ======================");
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
						test_steps.add("===================== Verifying No show amount at folio =====================");
						reservationPage.click_Folio(driver, test_steps);
						if (Utility.validateString(policyToValidate)) {
							reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, noShowAmounts);
						} else {
							noShowAmounts.clear();
							reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, noShowAmounts);
						}

					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
							Utility.updateReport(e, "Failed to make reservation as no show", test_name, test_catagory,
									driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.updateReport(e, "Failed to make reservation as no show", test_name, test_catagory,
									driver);
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

				if (ratePlanCheckInPolicy.equalsIgnoreCase("NA")) {
					ratePlanCheckInPolicy = "No Policy";
				}

				app_logs.info("rateplan check In policy : " + rateDepositPlanPolicy);
				app_logs.info("reervation check In Policy : " + reervationDepositPolicy);

				app_logs.info(reervationCheckInPolicy);
				test_steps.add("=================== Verify the check in policy in reservation ======================");
				app_logs.info("=================== Verify the check in policy in reservation ======================");
				assertTrue(ratePlanCheckInPolicy.equalsIgnoreCase(reervationCheckInPolicy),
						"Reservation check in policy is not matched");
				test_steps.add(
						"Verified Reservation check in policy is reservation policies : " + reervationCheckInPolicy);
				app_logs.info(
						"Verified Reservation check in policy is reservation policies : " + reervationCheckInPolicy);

				if (!ratePlanCheckInPolicy.equalsIgnoreCase("No Policy")) {
					navigation.inventory(driver);
					navigation.policies(driver, test_steps);

					test_steps.add("=========Get  Data from Check-In Policy if policy exist on Season level=========");
					navigation.clickPoliciesAfterRateGridTab(driver, test_steps);
					Wait.waitUntilPageLoadNotCompleted(driver, 5);
					HashMap<String, String> valueHashmap = new HashMap<String, String>();
					if (CheckInDate.split("\\|").length > 1) {
						// for (int i = 0; i < seasonCheckInPolicy.size(); i++) {
						getcheckinPoliciesData.add(policy.getpoliciesData(driver, test_steps,
								ratesConfig.getProperty("checkInPolicyText"), getRateLevelPolicy.get("Check-in")));
						valueHashmap.put(getRateLevelPolicy.get("Check-in"),
								getcheckinPoliciesData.get(0).get("AttrValue"));
						// }
						String value = null;
						value = Utility.getMaxValuefromHashmap(valueHashmap);
						checkinPolicyApplied = Utility.getKeyfromHashmap(valueHashmap, value);
						checkinPoliciesData.put("AttrValue", value);
						app_logs.info(checkinPoliciesData);
					} else {
						checkinPoliciesData = policy.getpoliciesData(driver, test_steps,
								ratesConfig.getProperty("checkInPolicyText"), getRateLevelPolicy.get("Check-in"));
						checkinPolicyApplied = getRateLevelPolicy.get("Check-in");

					}
					app_logs.info(getcheckinPoliciesData);
					reservationPage.navigateToReservationPage(driver);
					reservationPage.Search_ResNumber_And_Click(driver, reservation);

					reservationPage.clickFolio(driver, test_steps);
					balance = Double.parseDouble(reservationPage.get_Balance(driver, test_steps));
					if (CheckInDate.split("\\|").length > 1) {
						reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
						reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(1), Rooms.get(1));
						balance = balance + Double.parseDouble(reservationPage.get_Balance(driver, test_steps));
						reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
						reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(0), Rooms.get(0));
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
					} else if (isAccountPolicyApplicable.equals("No") && !ratePlanCheckInPolicy.isEmpty()) {
						paymentAmt = Double
								.parseDouble(reservationPage.get_PaymentsForAccountReservation(driver, test_steps));
						app_logs.info(paymentAmt);
						checkInAmount = reservationPage.calculationOfCheckInAmountToBePaidForRateV2(
								String.valueOf(balance), checkinPoliciesData.get("AttrValue"));
					} else if (!ratePlanCheckInPolicy.isEmpty()) {
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
					if (!ratePlanCheckInPolicy.equalsIgnoreCase("No Policy")) {
						completeCheckInIfPolicyExist(balance, checkInAmount);
						test_steps.add("==================Verify Check-In Policy On Reservation==================");
						verificationCheckinWithPolicy(PaymentType, checkinPolicyApplied, checkInAmount, paymentAmount,
								checkInCardFormat);
					} else {
						completeCheckInIfPolicyDoesntExist(balance);
						test_steps.add("==================Verify Check-In Policy On Reservation==================");
						verificationCheckinWithoutPolicy(ratesConfig.getProperty("noPolicyText"));
					}
				}
			} else if (ActionOnReservation.equalsIgnoreCase("Cancellation")) {

				String cancellationPolicy = "";
				String cancellationDelim = ",";
				if (isVerifyPolicies) {

					boolean delimFlag = false;

					cancellationPolicy = getRateLevelPolicy.get("Cancellation");

					if (isAccountPolicyApplicable.equals("Yes")) {
						// to-do
					}

					if (!cancellationPolicy.equalsIgnoreCase("NA")) {

						test_steps.add("==================VERIFY CANCELLATION POLICY ON RESERVATION==================");

						reservationPage.close_FirstOpenedReservation(driver, test_steps);
						navigation.inventory(driver);
						navigation.policies(driver, test_steps);

						PolicyTypes += "Cancellation";
						PolicyNames += cancellationPolicy;
						policy.clickEditIcon(driver, "Cancellation", cancellationPolicy, test_steps);
						int noOfClauses = policy.getNoOfClauses(driver);
						TypeOfFees += policy.getSelectedTypeOfFees(driver, cancellationDelim, noOfClauses, test_steps);
						GuestsWillIncurAFee += policy.getGuestWillIncurAFee(driver, cancellationDelim, noOfClauses,
								test_steps);
						ChargesType += policy.getSelectedChargeType(driver, cancellationDelim, TypeOfFees, test_steps);
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
							chargesType.put("Cancellation", "na");
						}
						cancelWithInType = policy.getPolicyClauses(delim, PolicyTypes, CancelWithInType);
						noOfDays = policy.getPolicyClauses(delim, PolicyTypes, NoOfDays);
						delimFlag = true;

						try {
							navigation.cpReservation_Backward(driver);
						} catch (Exception e) {
							Actions actions = new Actions(driver);

							actions.sendKeys(Keys.ENTER);
						}
						reservationPage.Search_ResNumber_And_Click(driver, reservation);

						app_logs.info("Type Of Fees : " + policyClauses);
						app_logs.info("Guests Will Incur A fee : " + guestsWillIncurAFee);
						app_logs.info("Charge Type : " + chargesType);
						app_logs.info("No Of Days : " + noOfDays);
						app_logs.info("Cancel With In Type : " + cancelWithInType);
					} else {
						delimFlag = false;
					}

				}

//	reservationSearch.multipleSearchReservationNumber(driver, allReservation);
//	
//	getTestSteps.clear();
//	getTestSteps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber1);
//	testSteps.addAll(getTestSteps);

				// String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

				String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
				foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
				String foundTotalCharge = reservationPage.getTotalTripSummary(driver);
				foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
				// Float calRoomCharge = (Float.parseFloat(foundRoomCharge) /
				// Integer.parseInt(statydiff));

				reservationPage.click_Folio(driver, test_steps);
				reservationPage.includeTaxesinLineItems(driver, test_steps, false);

				HashMap<String, ArrayList<String>> roomClassWiseDates = reservationPage
						.getRoomClassFolioWiseRoomChargeDates(driver, test_steps, reservationRoomClassesList, RoomAbbri,
								"Yes", Rooms, "dd/MM/yyyy");
				HashMap<String, HashMap<String, String>> roomClassWiseRoomCharge = reservationPage
						.getRoomClassWiseFolioRoomChargeBasedOnDates(driver, reservationRoomClassesList,
								roomClassWiseDates, "dd/MM/yyyy", RoomAbbri, "Yes", Rooms);

				app_logs.info(roomClassWiseDates);
				app_logs.info(roomClassWiseRoomCharge);
				reservationPage.click_DeatilsTab(driver, test_steps);
				if (isVerifyPolicies) {
					if (!cancellationPolicy.equalsIgnoreCase("NA")) {
						foundBestPolicyAmount = policy.findBestCancellationPolicy(",", names.get("Cancellation"),
								policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
								chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"),
								noOfDays.get("Cancellation"), foundRoomCharge + "", foundTotalCharge,
								ReservationRoomClasses, CheckInDate, CheckOutDate, roomClassWiseDates,
								roomClassWiseRoomCharge);

						app_logs.info("Best Clause : " + foundBestPolicyAmount);
						app_logs.info("Clauses : " + policyClauses.get("Cancellation"));
						app_logs.info("Guest will incur a fee : " + guestsWillIncurAFee.get("Cancellation"));
						HashMap<String, String> cancellationClauseValues = reservationPage.getCancellationClauseValue(
								",", policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"));
						app_logs.info(cancellationClauseValues);
						app_logs.info(PolicyTypes);
						app_logs.info(PolicyNames);
						reservationPage.verifyAssociatedCancellationPolicy(driver, ",", PolicyTypes, PolicyNames,
								Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), ","),
								cancellationClauseValues, test_steps);
					}
				}

				String tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
				String paidAmount = reservationPage.get_TripSummaryPaidAmount(driver, test_steps);

				boolean isCancellationPolicyApplicable = false;

				if (isVerifyPolicies) {
					if (!cancellationPolicy.equalsIgnoreCase("NA") && foundBestPolicyAmount != null) {
						isCancellationPolicyApplicable = policy.isCancellationPolicyApplicable(
								foundBestPolicyAmount.getNoOfDays(),
								Utility.convertTokenToArrayList(CheckInDate, delim).get(0));
					}
				}
				isCancellationPolicyApplicable = true;

				reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);

				if (isCancellationPolicyApplicable) {
					reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, names.get("Cancellation"),
							"Cancellation");
					reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");

					String[] calCancelPolicy = reservationPage.calculateCancellationFee(paidAmount,
							foundBestPolicyAmount.getCalculatedAmount());
					reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", calCancelPolicy[1],
							calCancelPolicy[0]);
					reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);

					if (!calCancelPolicy[0].equalsIgnoreCase("alreadyPaid")) {
						reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", calCancelPolicy[1],
								calCancelPolicy[0], "Processed");
						reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);
					}
					reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

					tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
					assertEquals(Utility.formatDecimal(tripTotal),
							Utility.formatDecimal(foundBestPolicyAmount.getCalculatedAmount()),
							"Failed to Verify Trip Total After Reservation");
					test_steps.add("Successfully Verified Trip Total After Reservation : " + tripTotal);

					paidAmount = reservationPage.get_TripSummaryPaidAmount(driver, test_steps);
					assertEquals(paidAmount, tripTotal, "Failed to Verify Paid Amount After Reservation");
					test_steps.add("Successfully Verified Paid Amount After Reservation : " + tripTotal);
				} else {
					try {
						reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, "", "Cancellation");
					} catch (Exception e) {
						// TODO: handle exception
					}

					reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
					if (isCancellationPolicyApplicable) {
						if (Float.parseFloat(paidAmount) == Float.parseFloat("0")) {

							reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
							reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");
						} else {
							String[] calCancelPolicy = reservationPage.calculateCancellationFee(paidAmount, "0");
							reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", calCancelPolicy[1],
									calCancelPolicy[0]);
							reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
							reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");
						}
					} else {
						reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");
					}
				}

			} else if (ActionOnReservation.equalsIgnoreCase("Change Stay Details")) {
				changeStaydetails(delim, packageRatePlanName, derivedRatePlanName, CheckInDate, CheckOutDate,
						ChangeResStartDate, ChangeResEndDate, ReservationChangeRoomClass, channelName,
						ReservationRoomClasses, ReservationType, ChangeAdult, ChangeChildre);
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

	}

	public void changeStaydetails(String delim, String packageRatePlanName, String derivedRatePlanName,
			String CheckInDate, String CheckOutDate, String ChangeResStartDate, String ChangeResEndDate,
			String ReservationChangeRoomClass, String channelName, String ReservationRoomClasses,
			String ReservationType, String ChangeAdult, String ChangeChildre) throws Exception {

		//Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		test_steps.clear();
		test_name = "Modify Rates And Rules For Change stay details Verification in Reservation";
		test_description = "Modify Rates And Rules For Change stay details Verification in Reservation";
		
		navigation.Inventory(driver);
		test_steps.add("Navigate Inventory");
		app_logs.info("Navigate Inventory");

		navigation.RatesGrid(driver);
		test_steps.add("Navigated to RatesGrid");
		app_logs.info("Navigate rate grid");

		rateGrid.clickRatePlanArrow(driver, test_steps);
		rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
		derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
		derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, test_steps);
		nightlyRate.switchCalendarTab(driver, test_steps);

		ArrayList<String> checkInListChnage = Utility.convertTokenToArrayList(ChangeResStartDate, delim);

		ArrayList<String> checkOutListChange = Utility.convertTokenToArrayList(ChangeResEndDate, delim);

		ArrayList<Boolean> isSeasonChange = new ArrayList<Boolean>();
		int index = 0;
		for (String checkIn : checkInListChnage) {
			isSeasonChange.add(nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps, checkIn,
					checkOutListChange.get(index++)));
		}

		for (Boolean season : isSeasonChange) {
			if (!season) {
				isSeasonExistChange = false;
				break;
			}
		}

		nightlyRate.clickSaveRatePlan(driver, test_steps);

		int daysChnage = Utility.getNumberofDays(ChangeResStartDate, ChangeResEndDate);
		if (isSeasonExistChange) {
			navigation.RatesGrid(driver);
			index = 0;
			for (String checkIn : checkInListChnage) {
				rateGrid.bulkUpdateOverideRates(driver, delim, "BulkUpdate", checkIn, checkOutListChange.get(index),
						"yes", "yes", "yes", "yes", "yes", "yes", "yes", derivedRatePlanName, ReservationRoomClasses,
						channelName, "EnterNewRate", "true", "50", "20", "10", test_steps);

				rateGrid.bulkUpdateOverideRules(driver, delim, "BulkUpdate", checkIn, checkOutListChange.get(index),
						"yes", "yes", "yes", "yes", "yes", "yes", "yes", derivedRatePlanName, ReservationRoomClasses,
						channelName, "Min stay", "3", test_steps);
				index++;
			}

			driver.navigate().refresh();
			rateGrid.clickForRateGridCalender(driver, test_steps);
			rateGrid.selectDateFromDatePicker(driver, ChangeResStartDate, ratesConfig.getProperty("defaultDateFormat"),
					test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			rateGrid.expandParentRateGrid(driver, "minus");
			getTest_Steps.clear();
			getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			rateGrid.expandRoomClass(driver, ReservationChangeRoomClass);
			rateGrid.expandChannel(driver, test_steps, ReservationChangeRoomClass, channelName);

			minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeRoomClass,
					channelName, daysChnage);
			minruleChange = minStayRuleChnage;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minruleChnage : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {
				isMinStayRuleChnage = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChnage);
			}

			noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeRoomClass,
					channelName, daysChnage);

			Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

			checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,
					ChangeResStartDate, ChangeResEndDate);

			Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

			noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
					ReservationChangeRoomClass, channelName, daysChnage);

			Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChnage, ChangeResStartDate, ChangeResEndDate);
			Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

			rateGrid.clickOnEditRatePlan(driver);
			folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);

			rateGrid.clickOnRestrcitionSAndPoliciesTab(driver);

			verifyLenthOfStayCheckedChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Length of stay");
			app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayCheckedChange);
			if (verifyLenthOfStayCheckedChange) {

				verifyMinStayCondidtionChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Min");
				verifyMaxStayConditionChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Max");
				if (verifyMinStayCondidtionChange) {
					String getMin = rateGrid.getMinAndMaxValue(driver, "Min");
					addStayofLengthChange.put("Min", getMin);
				}
				if (verifyMaxStayConditionChange) {
					String getMax = rateGrid.getMinAndMaxValue(driver, "Max");
					addStayofLengthChange.put("Max", getMax);
				}
			}
			isBookinWindowChange = rateGrid.isBookingWindowChecked(driver, test_steps);
			HashMap<String, String> bookingWindowRestrictionsChange = new HashMap<>();
			test_steps.add("========== Getting booking window default restrictions for rate plan " + "<b>"
					+ derivedRatePlanName + "</b> ==========");
			bookingWindowRestrictionsChange = rateGrid.getBookingWindowRestrictions(driver, test_steps,
					derivedRatePlanName);

			Utility.app_logs.info(bookingWindowRestrictionsChange);

			restricrionsLengthOfStayChange = reservationPage.verifylenthOfStayRestrictions(driver, test_steps,
					verifyLenthOfStayCheckedChange, verifyMinStayCondidtionChange, verifyMaxStayConditionChange,
					addStayofLengthChange, daysChnage);

			Utility.app_logs.info(addStayofLengthChange);
			Utility.app_logs.info(restricrionsLengthOfStayChange);

			restricrionsBookingWindowChange = reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
					isBookinWindowChange, ChangeResStartDate, ChangeResEndDate, bookingWindowRestrictionsChange);

			Utility.app_logs.info("isBookinWindowChange : " + isBookinWindowChange);
			Utility.app_logs.info("restricrionsBookingWindowChange : " + restricrionsBookingWindowChange);

			isPromocodeChnage = rateGrid.isPromoCodeChecked(driver, test_steps);

			if (isPromocode) {
				PromoCodeChange = rateGrid.getPromoCode(driver, test_steps);
			} else {
				PromoCodeChange = "";
			}
			Utility.app_logs.info("PromoCodeChange : " + PromoCodeChange);

			if (isVerifyPoliciesChange) {
				getRateLevelPolicyChange = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
			}
			nightlyRate.switchCalendarTab(driver, test_steps);

			nightlyRate.selectSeasonDates(driver, test_steps, ChangeResStartDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);

			capacityAdultChange.put(ReservationChangeRoomClass,
					nightlyRate.getAdultCapacity(driver, ReservationChangeRoomClass));
			capacityChildChange.put(ReservationChangeRoomClass,
					nightlyRate.getChildCapacity(driver, ReservationChangeRoomClass));
			maxAdultChange.put(ReservationChangeRoomClass, nightlyRate.getMaxAdult(driver, ReservationChangeRoomClass));
			maxChildChange.put(ReservationChangeRoomClass,
					nightlyRate.getMaxPersons(driver, ReservationChangeRoomClass));
			test_steps.add("Room Class : " + ReservationChangeRoomClass + " Adult Capacity: <b>"
					+ capacityAdultChange.get(ReservationChangeRoomClass) + "</b>");
			test_steps.add("Room Class : " + ReservationChangeRoomClass + " Person's Capacity: <b>"
					+ capacityChildChange.get(ReservationChangeRoomClass) + "</b>");
			test_steps.add("Rate Plan Max. Adults: <b>" + maxAdultChange.get(ReservationChangeRoomClass) + "</b>");
			test_steps.add("Rate Plan Max. Childs: <b>" + maxChildChange.get(ReservationChangeRoomClass) + "</b>");

			nightlyRate.closeSeason(driver, test_steps);
			rateGrid.clickOnSaveratePlan(driver);
			Wait.wait5Second();
			rateGrid.closeOpendTabInMainMenu(driver);

			String[] rm = ReservationRoomClasses.split("\\|");
			driver.navigate().refresh();

			HashMap<String, // RoomClass
					HashMap<String, // Source
							HashMap<String, // date
									RatesGridChannelRatesRules>>> roomClassWiseSourceDerivedRatesRule = rateGrid
											.getSourceWiseRatesRules(driver, test_steps, packageRatePlanName,
													derivedRatePlanName, true, delim, ReservationChangeRoomClass,
													channelName, ChangeResStartDate, ChangeResEndDate);

			Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
					ChangeResStartDate);
			app_logs.info("Start Date: " + fromDate);
			Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
					ChangeResEndDate);
			app_logs.info("End Date: " + toDate);
			datesChnage = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
			app_logs.info("Dates Are: " + datesChnage);
			try {
				for (int i = 0; i < datesChnage.size(); i++) {

					String date = Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),
							datesChnage.get(i));

					app_logs.info(date);

					RatesGridChannelRatesRules rates = roomClassWiseSourceDerivedRatesRule
							.get(ReservationChangeRoomClass).get(channelName).get(date);

					app_logs.info(rates);
//				String rateIs = StringUtils.substringBetween(
//						getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
//								ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
//						"RRate:", "ExCh:");
//				app_logs.info(rateIs);
//				String ch = StringUtils.substringBetween(
//						getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
//								ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
//						"ExCh:", "ExAd:");
//				app_logs.info(ch);
//				String ad = StringUtils.substringAfter(
//						getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
//								ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
//						"ExAd:");
//				app_logs.info(ad);
					getRatesChange.put(date, rates.getRooomRate());
					getExAdultChange.put(date, rates.getExtraAdultRate());
					getExChildChange.put(date, rates.getExtraChildRate());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			navigation.cpReservation_Backward(driver);
			test_steps.add("Back to resevations page");
			driver.navigate().refresh();
			Wait.wait5Second();
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.closeReservationTab(driver);
			reservationPage.Search_ResNumber_And_Click(driver, reservation);

			// first verify edit rate with same date

			// Recalculate Rate
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_steps.clear();
			test_name = "Change stay details-Recalculate Rate,  verify in reservation";
			test_description = "Change stay details-Recalculate Rate,  verify in reservation";

			reservationPage.click_Folio(driver, test_steps);
			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}
			HashMap<String, String> roomChargesFolio = reservationPage.getRoomChargesFromFolioBasedOnDates(driver,
					test_steps, CheckInDate, CheckOutDate);
			Utility.app_logs.info(roomChargesFolio);
			String RoomChagres = reservationPage.get_RoomCharge(driver, test_steps);
			reservationPage.click_DeatilsTab(driver, test_steps);
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {
				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}

//			reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, ChangeResStartDate);
			reservationPage.select_CheckoutDate(driver, test_steps, ChangeResEndDate);
			reservationPage.enter_Adults(driver, test_steps, ChangeAdult);
			reservationPage.enter_Children(driver, test_steps, ChangeChildre);
			reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, PromoCodeChange);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			ArrayList<String> roomClass = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);

			if (driver.findElements(By.xpath("(//span[text()='" + roomClass.get(roomClass.size() - 1) + "'])[2]"))
					.size() > 0 && isRoomClassAvailableChange) {
				
				String minStayColorChange = "";
				if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange && isSeasonExistChange
						&& isSeasonExistChange) {
					String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRatesChange,
							maxAdultChange.get(ReservationChangeRoomClass), maxChildChange.get(ReservationChangeRoomClass),
							getExAdultChange, getExChildChange, capacityChildChange.get(ReservationChangeRoomClass),
							capacityAdultChange.get(ReservationChangeRoomClass), ChangeAdult, ChangeChildre, test_steps,
							ratesConfig.getProperty("noCombination"), ReservationChangeRoomClass, datesChnage,
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info("rateIs : " + rateIs);
					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeRoomClass, minStayRuleValueChange);
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
									ReservationChangeRoomClass, minStayRuleValueChange);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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

					reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
							ReservationChangeRoomClass, "Yes", "AccountName", noCheckinColorChange,
							noCheckoutColorChange, minStayColorChange, minStayRuleValueChange);

					reservationPage.clickSaveButton(driver);
					reservationPage.verifyPopupChangeInCost1(driver, test_steps);
					reservationPage.click_Folio(driver, test_steps);
					if (ReservationType.equalsIgnoreCase("MRB")) {
						reservationPage.selectMRBLastFolio(driver, test_steps);
					}
					HashMap<String, String> roomChargesFolio1 = reservationPage.getRoomChargesFromFolioBasedOnDates(
							driver, test_steps, ChangeResStartDate, ChangeResEndDate);
					String RoomChagres1 = reservationPage.get_RoomCharge(driver, test_steps);
					reservationPage.checkFolioLineItemsWithDates(driver, test_steps, ChangeResStartDate,
							ChangeResEndDate);
					Utility.app_logs.info("roomChargesFolio1 : " + roomChargesFolio1);
					Utility.app_logs.info("roomChargesFolio : " + roomChargesFolio);
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

			// no change option
			
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_steps.clear();
			test_name = "Change stay details-No Rate Change,  verify in reservation";
			test_description = "Change stay details-No Rate Change,  verify in reservation";
			
			reservationPage.click_Folio(driver, test_steps);

			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}
			roomChargesFolio = reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps,
					ChangeResStartDate, ChangeResEndDate);
			Utility.app_logs.info(roomChargesFolio);
			RoomChagres = reservationPage.get_RoomCharge(driver, test_steps);
			reservationPage.click_DeatilsTab(driver, test_steps);
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {

				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}
			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, ChangeResStartDate);
			reservationPage.select_CheckoutDate(driver, test_steps, ChangeResEndDate);
			reservationPage.enter_Adults(driver, test_steps, ChangeAdult);
			reservationPage.enter_Children(driver, test_steps, ChangeChildre);
			reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, PromoCodeChange);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "No Rate Change");
			reservationPage.clickOnFindRooms(driver, test_steps);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {

				/*
				 * String rateIs =
				 * reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
				 * maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses),
				 * getExAdult, getExChild, capacityChild.get(ReservationRoomClasses),
				 * capacityAdult.get(ReservationRoomClasses), adult, children, test_steps,
				 * ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
				 * ratesConfig.getProperty("defaultDateFormat")); Utility.app_logs.info(rateIs);
				 */
				String minStayColorChange = "";
				if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange && isSeasonExistChange
						&& isSeasonExistChange) {

					String noCheckinColorChange = "";
					if (checkInColorChnage.equalsIgnoreCase("Red")) {
						try {
							noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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

					reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
							ReservationChangeRoomClass, "Yes", "AccountName", noCheckinColorChange,
							noCheckoutColorChange, minStayColorChange, minStayRuleValueChange);

					reservationPage.clickSaveButton(driver);
					reservationPage.verifyPopupChangeInCost1(driver, test_steps);
					reservationPage.click_Folio(driver, test_steps);
					if (ReservationType.equalsIgnoreCase("MRB")) {
						reservationPage.selectMRBLastFolio(driver, test_steps);
					}
					HashMap<String, String> roomChargesFolio1 = reservationPage.getRoomChargesFromFolioBasedOnDates(
							driver, test_steps, ChangeResStartDate, ChangeResEndDate);
					String RoomChagres1 = reservationPage.get_RoomCharge(driver, test_steps);
					reservationPage.checkFolioLineItemsWithDates(driver, test_steps, ChangeResStartDate,
							ChangeResEndDate);
					Utility.app_logs.info(RoomChagres1);
					try {
						assertTrue(RoomChagres1.equalsIgnoreCase(RoomChagres), "Room Chares are not matched");
						test_steps.add(
								"Verified room charges are not changes even after change stay details in the reservation");
						app_logs.info(
								"Verified room charges are not changes even after change stay details in the reservation");
					} catch (Error e) {
						test_steps.add(e.toString());
					}
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

			// change dates
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			test_steps.clear();
			test_name = "Change stay details-Change only for added/removed dates,  verify in reservation";
			test_description = "Change stay details-Change only for added/removed dates,  verify in reservation";

			reservationPage.click_Folio(driver, test_steps);
			if (ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.selectMRBLastFolio(driver, test_steps);
			}
			roomChargesFolio = reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps,
					ChangeResStartDate, ChangeResEndDate);
			Utility.app_logs.info(roomChargesFolio);
			RoomChagres = reservationPage.get_RoomCharge(driver, test_steps);
			reservationPage.click_DeatilsTab(driver, test_steps);
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				reservationPage.ClickEditStayInfo(driver, test_steps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
			} else {

				reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
			}
			Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
			reservationPage.select_CheckInDate(driver, test_steps, ChangeResStartDate);
			reservationPage.select_CheckoutDate(driver, test_steps, ChangeResEndDate);
			reservationPage.enter_Adults(driver, test_steps, ChangeAdult);
			reservationPage.enter_Children(driver, test_steps, ChangeChildre);
			reservationPage.select_Rateplan(driver, test_steps, derivedRatePlanName, PromoCodeChange);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Change only for added/removed dates");
			reservationPage.clickOnFindRooms(driver, test_steps);

			if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
					&& isRoomClassAvailableChange) {
				/*
				 * String rateIs =
				 * reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver,
				 * getRatesChange, maxAdultChange.get(ReservationChangeRoomClass),
				 * maxChildChange.get(ReservationChangeRoomClass), getExAdultChange,
				 * getExChildChange, capacityChildChange.get(ReservationChangeRoomClass),
				 * capacityAdultChange.get(ReservationChangeRoomClass), adult, children,
				 * test_steps, ratesConfig.getProperty("noCombination"),
				 * ReservationChangeRoomClass, datesChnage,
				 * ratesConfig.getProperty("defaultDateFormat"));
				 * Utility.app_logs.info("rateIs : "+rateIs);
				 */
				String minStayColorChange = "";
				if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange && isSeasonExistChange
						&& isSeasonExistChange) {

					if (isMinStayRuleChnage) {
						if (!isMinStayRuleBrokenPopComeOrNotChange) {
							minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
									ReservationChangeRoomClass, minStayRuleValueChange);
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
									ReservationChangeRoomClass, minStayRuleValueChange);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
									ReservationChangeRoomClass);
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
					reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
							ReservationChangeRoomClass, "Yes", "AccountName", noCheckinColorChange,
							noCheckoutColorChange, minStayColorChange, minStayRuleValueChange);
					reservationPage.clickSaveButton(driver);
					reservationPage.verifyPopupChangeInCost1(driver, test_steps);
					reservationPage.click_Folio(driver, test_steps);
					if (ReservationType.equalsIgnoreCase("MRB")) {
						reservationPage.selectMRBLastFolio(driver, test_steps);
					}
					HashMap<String, String> roomChargesFolio1 = reservationPage.getRoomChargesFromFolioBasedOnDates(
							driver, test_steps, ChangeResStartDate, ChangeResEndDate);
					reservationPage.checkFolioLineItemsWithDates(driver, test_steps, ChangeResStartDate,
							ChangeResEndDate);
					String RoomChagres1 = reservationPage.get_RoomCharge(driver, test_steps);
					Utility.app_logs.info("roomChargesFolio1 : " + roomChargesFolio1);
					Utility.app_logs.info("roomChargesFolio : " + roomChargesFolio);
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

		}
	}

		
}
