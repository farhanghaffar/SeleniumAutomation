package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.CancellationPolicy;
import com.innroad.inncenter.model.PackageProduct;
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
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import bsh.ParseException;

public class VerifyDerivedPackageRateReservationV2 extends TestCore {
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
	
//	ArrayList<String> capacityAdult1 = new ArrayList<String>();
//	ArrayList<String> capacityChild1 = new ArrayList<String>();
//	ArrayList<String> ratePlanAdults = new ArrayList<String>();
//	ArrayList<String> ratePlanChilds = new ArrayList<String>();
//	ArrayList<HashMap<String, String>> ratesList = new ArrayList<HashMap<String, String>>();
//	ArrayList<HashMap<String, String>> exAdultList = new ArrayList<HashMap<String, String>>();
//	ArrayList<HashMap<String, String>> exChildList = new ArrayList<HashMap<String, String>>();
	@Test(dataProvider = "getData")
	public void verifyDerivedPackageRateReservationV2(
			String delim, String ReservationType, String CheckInDate,
			String CheckOutDate, String adult, String children, String ReservationRoomClasses,
			String packageRatePlanName,String derivedRatePlanName, String productsName, String change_checkInDate, String change_checkOutDate,
			String changeOption, String changeRoomClass) throws Exception {

		/**/
		Utility.DELIM = delim;

		

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
		ReservationV2 res = new ReservationV2();

		if (!ReservationType.equalsIgnoreCase("MRB")) {
			days = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		}

		int daysChnage = 0;
		
			daysChnage = Utility.getNumberofDays(change_checkInDate, change_checkOutDate);
		
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
		String ReservationRoomClassesAbbr="";
		NewRoomClassesV2 rc2 = new NewRoomClassesV2();
		if (ReservationType.equalsIgnoreCase("TapeChart")) {
			test_name = test_name+" from TapeChart";
			 ReservationRoomClassesAbbr = rc2.openRoomClassAndGetDataFromRoomTab(driver, test_steps, roomClasses).get("Abbreviation");
			navigation.Reservation_Backward_3(driver);
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
				) {
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
			test_name = "Getting Rate Plan Rates and Capacity : " + packageRatePlanName;
			test_description = "Getting Rate Plan Rates and Capacity : " + packageRatePlanName;
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
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);
			derivedRate.expandReduceDerivedratePlans(driver, true, test_steps);
			derivedRate.clickEditIconOfDerivedRatePlan(driver, derivedRatePlanName, test_steps);
			
			parentRatePlanOffSetList = derivedRate.getParentPlanOffset(driver);
			Utility.app_logs.info("Selected OffSet Value  : " + parentRatePlanOffSetList);
			test_steps.add("Selected OffSet Value  : " + parentRatePlanOffSetList);
			
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);

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
			test_name = "Getting Rate Plan Rates and Capacity For Change Stay Details : " + packageRatePlanName;
			test_description = "Getting Rate Plan Rates and Capacity For Change Stay Details : " + packageRatePlanName;
			test_steps.clear();
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}

		
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, packageRatePlanName);

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
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				for (String roomclass : roomClassList) {
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass), 
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel, 
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							adult,
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
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass), changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel,  changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass), adult,
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
				
				for (String roomclass : roomClassList) {
					 rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass), 
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel, 
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							adult,
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
					
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass), changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel,  changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass), adult,
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
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass), 
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel, 
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							adult,
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
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass), changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel,  changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass), adult,
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
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass), 
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel, 
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							adult,
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
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass), changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel,  changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass), adult,
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
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass), 
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel, 
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							adult,
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
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass), changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel,  changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass), adult,
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
					HashMap<String, String> rateIs = res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							ratePlanMaxAdults_SeasonLevel.get(roomclass),
							ratePlanMaxChilds_SeasonLevel.get(roomclass), 
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel, 
							roomclassCapacityChild_SeasonLevel.get(roomclass),
							roomClassCapacityAdult_SeasonLevel.get(roomclass),
							adult,
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
					changeRateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(
							baseRatePerNight_SeasonLevel,
							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass), changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
							baseRateExAdult_SeasonLevel,
							baseRateExChild_SeasonLevel,  changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass), adult,
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
		return Utility.getData("VerifyDerivedPackageRateResV2", envLoginExcel);
	}

	// @AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
