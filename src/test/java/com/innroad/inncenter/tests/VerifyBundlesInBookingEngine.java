package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Products;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyBundlesInBookingEngine extends TestCore {

	
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	String date = null;

	String reservationNumber = null;
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comment;

	public static String test_description = "";
	public static String test_catagory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "NBE")
	public void verifyBundlesInBookingEngine(String delim, String testCaseDescription,
			String checkInDate,
			String checkOutDate,String roomClass,String ratePlan,String productName,
			String calculationMethodOne, String calculationMethodTwo, String productAmount,
			String ratePlanProductCalculationMethodOne, String ratePlanProductCalculationMethodTwo,
			 String addAndVerifyProduct, String updatedRoomClassRates,String packageBundleRatePlan,String cases)
			throws Exception {

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"CreateRatePlan_VerifyAddOn");
		HashMap<String, String> staticData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"VerifyAddonNBE_StaticData");

		String bookingEngineChannel = staticData.get("channelName");
		String ratePlanType = staticData.get("ratePlanType");
		String ratePlanName = staticData.get("ratePlanName");
		String adults = staticData.get("adults");
		String child = staticData.get("child");
		String reservationRoomClassName = staticData.get("reservationRoomClass");
		String phone = staticData.get("phoneNumber");
		String address = staticData.get("address1");
		String city = staticData.get("city");
		String state = staticData.get("state");
		String postalCode = staticData.get("postalCode");
		String firstName = staticData.get("reservationFirstName");
		String lastName = staticData.get("reservationLastName");
		String cardNumber = staticData.get("cardNumber");
		String cardName = staticData.get("cardName");
		String cardExpDate = staticData.get("cardExpDate");
		String emailAddress = staticData.get("emailID");
		String cvv = staticData.get("cvv");
		String entityName = staticData.get("entityName");
		String ledgerAccountName = staticData.get("ledgerAccountName");
		String ledgerAccountDescription = staticData.get("ledgerAccountDescription");
		String ledgerAccountType = staticData.get("ledgerAccountType");
		String ledgerAccountStatus = staticData.get("ledgerAccountStatus");
		String taxName = staticData.get("taxName");
		String taxDescription = staticData.get("taxDescription");
		String taxValue = staticData.get("taxValue");
		String excludeTaxExempt = staticData.get("ExcludeTaxExempt");
		String taxPercentage = staticData.get("taxPercentage");
		String taxVAT = staticData.get("taxVAT");
		String updatedIsRatePlanRistrictionReq = staticData.get("isRatePlanRistrictionReq");
		String updatedRistrictionType = staticData.get("RistrictionType");
		String updatedIsMinStay = staticData.get("isMinStay");
		String updatedMinNights = staticData.get("MinNights");
		String updatedIsMaxStay = staticData.get("isMaxStay");
		String updatedMaxNights = staticData.get("MaxNights");
		String updatedIsMoreThanDaysReq = staticData.get("isMoreThanDaysReq");
		String updatedMoreThanDaysCount = staticData.get("MoreThanDaysCount");
		String updatedIsWithInDaysReq = staticData.get("isWithInDaysReq");
		String updatedWithInDaysCount = staticData.get("WithInDaysCount");
		String updatedPromoCode = staticData.get("PromoCode");
		String updatedSeasonIsSeasonLevelRules = staticData.get("isSerasonLevelRules");
		String updatedSeasonIsAssignRulesByRoomClass = staticData.get("isAssignRulesByRoomClass");
		String updatedSeasonRuleSpecificRoomClasses = staticData.get("SeasonRuleSpecificRoomClasses");
		String updatedSeasonRuleType = staticData.get("SeasonRuleType");
		String updatedSeasonRuleMinStayValue = staticData.get("SeasonRuleMinStayValue");
		String updateRulesDaysAtSeasonLevel = staticData.get("isSeasonRuleOnMonday");

		String timeZone = "";
		String propertyName = "";
		int numberOfProperties = 0;
		String dateFormat = Utility.dayMonthYear_DateFormat;
		String seasonDateFormat = Utility.dayMonthYear_DateFormat;

		String rateFolioName = ratePlanData.get("rateFolioName");
		String rateDescription = ratePlanData.get("rateDescription");
		String rateRoomClass = ratePlanData.get("rateRoomClass");
		String rateRoomClassSize = ratePlanData.get("rateRoomClassSize");
		String ratePlanBaseAmount = ratePlanData.get("ratePlanBaseAmount");
		String addtionalAdult = ratePlanData.get("AddtionalAdult");
		String additionalChild = ratePlanData.get("AdditionalChild");
		String intervalRatePlanIntervalValue = ratePlanData.get("intervalRatePlanIntervalValue");
		String isDefaultProrateChecked = ratePlanData.get("isDefaultProrateChecked");
		String packageRatePlanRateType = ratePlanData.get("packageRatePlanRateType");
		String packageRatePlanProductName = ratePlanData.get("packageRatePlanProductName");
		String packageratePlanProductFirstCalculationMethod = ratePlanData
				.get("packageratePlanProductFirstCalculationMethod");
		String packageratePlanProductsecondCalculationMethod = ratePlanData
				.get("packageratePlanProductSecondCalculationMethod");
		String packageRatePlanProductAmount = ratePlanData.get("packageRatePlanProductAmount");
		String channels = ratePlanData.get("channels");
		String isRatePlanRistrictionReq = ratePlanData.get("isRatePlanRistrictionReq");
		String ristrictionType = ratePlanData.get("RistrictionType");
		String isMinStay = ratePlanData.get("isMinStay");
		String minNights = ratePlanData.get("MinNights");
		String isMaxStay = ratePlanData.get("isMaxStay");
		String maxNights = ratePlanData.get("MaxNights");
		String isMoreThanDaysReq = ratePlanData.get("isMoreThanDaysReq");
		String moreThanDaysCount = ratePlanData.get("MoreThanDaysCount");
		String isWithInDaysReq = ratePlanData.get("isWithInDaysReq");
		String withInDaysCount = ratePlanData.get("WithInDaysCount");
		String promoCode = ratePlanData.get("PromoCode");
		String isPolicesReq = ratePlanData.get("isPolicesReq");
		String policiesType = ratePlanData.get("PoliciesType");
		String policiesName = ratePlanData.get("PoliciesName");
		String seasonDelim = ratePlanData.get("seasonDelim");
		String seasonName = ratePlanData.get("SeasonName");
		String seasonStartDate = Utility.getCurrentDate(seasonDateFormat);
		String seasonEndDate = ratePlanData.get("SeasonEndDate");
		String isMonDay = ratePlanData.get("isMonDay");
		String isTueDay = ratePlanData.get("isTueDay");
		String isWednesDay = ratePlanData.get("isWednesDay");
		String isThursDay = ratePlanData.get("isThursDay");
		String isFriday = ratePlanData.get("isFriday");
		String isSaturDay = ratePlanData.get("isSaturDay");
		String isSunDay = ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String ratePerNight = ratePlanData.get("RatePerNight");
		String maxAdults = ratePlanData.get("MaxAdults");
		String maxPersons = ratePlanData.get("MaxPersons");
		String additionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String additionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String extraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String extraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String extraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String extraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String extraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String extraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSeasonLevelRules = ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass = ratePlanData.get("isAssignRulesByRoomClass");
		String seasonRuleSpecificRoomClasses = ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String seasonRuleType = ratePlanData.get("SeasonRuleType");
		String seasonRuleMinStayValue = ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday = ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday = ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday = ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday = ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday = ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday = ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday = ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies = ratePlanData.get("isSeasonPolicies");
		String seasonPolicyType = ratePlanData.get("SeasonPolicyType");
		String seasonPolicyValues = ratePlanData.get("SeasonPolicyValues");
		String isAssignPoliciesByRoomClass = ratePlanData.get("isAssignPoliciesByRoomClass");
		String seasonPolicySpecificRoomClasses = ratePlanData.get("seasonPolicySpecificRoomClasses");
		String isProRateStayInSeason = ratePlanData.get("isProRateStayInSeason");
		String isProRateInRoomClass = ratePlanData.get("isProRateInRoomClass");
		String isCustomPerNight = ratePlanData.get("isCustomPerNight");
		String customRoomClasses = ratePlanData.get("customRoomClasses");
		String customRatePerNight = ratePlanData.get("customRatePerNight");
		String isCustomRatePerNightAdultandChild = ratePlanData.get("isCustomRatePerNightAdultandChild");
		String customRateChildPerNight = ratePlanData.get("customRateChildPerNight");
		String customRateAdultdPerNight = ratePlanData.get("customRateAdultdPerNight");
		String roomClassesWithProRateUnchecked = ratePlanData.get("roomClassesWithProRateUnchecked");

		Utility.DELIM = "\\" + delim;
		Utility.SEASONDELIM = seasonDelim;
		// initializing static Data
		String isSellOnBookingEngine = "TRUE";
		String bookingEngineAvailabilityOption = "always";
		String productDescription = "Product Description";
		String productCategory = "Bar";
		String productQuantity = "1";
		String updateRules = "yes";
		String updateRate = "no";
		String createLedgerAccount = "TRUE"; 
		String createTaxItem = "TRUE"; 
		String createBEReservation = "FALSE";
		String verifyResInInncenter = "FALSE";
		
		if (testCaseDescription.equalsIgnoreCase("Verify bundles displaying as an addon on NBE")||
			(testCaseDescription.equalsIgnoreCase("Verify Package Bundle Rate plan displaying as Rate plan on NBE"))){
			createLedgerAccount = "FALSE"; 
			createTaxItem = "FALSE";
			createBEReservation = "TRUE";
			verifyResInInncenter = "TRUE";
				String isSellOnBookingEngineForMultipleProducts = isSellOnBookingEngine;
				String bookingEngineAvailabilityOptionForMultipleProducts = bookingEngineAvailabilityOption;
				String productDescriptionForMultipleProducts = productDescription;
				String productCategoryForMultipleProducts = productCategory;
				String productQuantityForMultipleProducts = productQuantity;
				for (int i = 1; i < productName.split(Utility.DELIM).length; i++) {
					isSellOnBookingEngineForMultipleProducts = isSellOnBookingEngineForMultipleProducts + delim
							+ isSellOnBookingEngine;
					bookingEngineAvailabilityOptionForMultipleProducts = bookingEngineAvailabilityOptionForMultipleProducts
							+ delim + bookingEngineAvailabilityOption;
					productDescriptionForMultipleProducts = productDescriptionForMultipleProducts + delim
							+ productDescription;
					productCategoryForMultipleProducts = productCategoryForMultipleProducts + delim
							+ productCategory;
					productQuantityForMultipleProducts = productQuantityForMultipleProducts + delim
							+ productQuantity;
				}
				isSellOnBookingEngine = isSellOnBookingEngineForMultipleProducts;
				bookingEngineAvailabilityOption = bookingEngineAvailabilityOptionForMultipleProducts;
				productDescription = productDescriptionForMultipleProducts;
				productCategory = productCategoryForMultipleProducts;
				productQuantity = productQuantityForMultipleProducts;
			
			app_logs.info("After loop");
			app_logs.info(isSellOnBookingEngine);
			app_logs.info(bookingEngineAvailabilityOption);
			app_logs.info(productDescription);
			app_logs.info(productCategory);
			app_logs.info(productQuantity);
		}
		

		String testName = "VerifyBundlesOnInBookingEngine";
		test_description = testCaseDescription;
		test_catagory = "BE_Bundles";
		scriptName.clear();
		scriptName.add(testName + "_" + testCaseDescription);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		LedgerAccount ledgerAccount = new LedgerAccount();
		Tax tax = new Tax();
		TaxesAndFee taxAndFees=new TaxesAndFee();
		Distribution distribution = new Distribution();
		Admin admin = new Admin();
		RatesGrid ratesGrid = new RatesGrid();
		CPReservationPage reservation = new CPReservationPage();
		Folio folio = new Folio();
		CPReservationPage reservationPage = new CPReservationPage();
		NightlyRate nightlyRate = new NightlyRate();
		DerivedRate derivedRate = new DerivedRate();
		RatePackage ratePackage = new RatePackage();
		Products productsBundles=new Products();
		Properties properties = new Properties();
		Login login = new Login();

		BookingEngine bookingEngine = new BookingEngine();

		String randomNumber = Utility.GenerateRandomNumber();
		lastName = lastName + randomNumber;
		
		String bundleName="Bundle"+randomNumber;
		ratePlanName=ratePlanName+randomNumber;

		String[] splitSeasonLevelDays = updateRulesDaysAtSeasonLevel.split(Utility.DELIM);
		String updatedSeasonIsSeasonRuleOnMonday = splitSeasonLevelDays[0];
		String updatedSeasonIsSeasonRuleOnTuesday = splitSeasonLevelDays[1];
		String updatedSeasonIsSeasonRuleOnWednesday = splitSeasonLevelDays[2];
		String updatedSeasonIsSeasonRuleOnThursday = splitSeasonLevelDays[3];
		String updatedSeasonIsSeasonRuleOnFriday = splitSeasonLevelDays[4];
		String updatedSeasonIsSeasonRuleOnSaturday = splitSeasonLevelDays[5];
		String updatedSeasonIsSeasonRuleOnSunday = splitSeasonLevelDays[6];

		String calendarTodayDay = "today";
		String todayDate = null;
		int daysBeforeCheckInDate = 0;
		boolean israteplanExist = true;

		boolean verifyLenthOfStayChecked = false;
		boolean verifyMinStayCondidtion = false;
		boolean verifyMaxStayCondition = false;
		HashMap<String, String> selectedLengthofStayRestrictions = new HashMap<>();
		String ratePlanPromoCode = null;
		HashMap<String, String> selectedBookingWindowRestrictions = new HashMap<>();
		HashMap<String, ArrayList<String>> getRoomClassWithRates = new HashMap<>();
		String ratePlanBaseRate = null;
		String ratePlanAdultCapicity = null;
		String ratePlanPersonCapicity = null;
		boolean ratePlanIsAdditionalAdultChild = false;
		String ratePlanMaxAdults = null;
		String ratePlanMaxPersons = null;
		String ratePlanAdultsRate = null;
		String ratePlanChildRate = null;
		int minStayRuleValue = 0;
		ArrayList<String> minStayRule = null;
		ArrayList<String> minrule = null;
		ArrayList<String> noCheckInRule = null;
		ArrayList<String> noCheckOutRule = null;
		String checkInColor = null;
		String checkOutColor = null;

		boolean isMinStayRule = false;
		boolean isMinStayRuleBrokenPopComeOrNot = false;
		int days = 1;
		boolean isPromocode = false;
		boolean ratePlanAvailableInBE = true;
		ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;
		boolean selectedRatePlanDefaultStatus = false;
		String selectedRatePlanFolioName = null;
		String selectedIntervalRatePlanIntervalValue = null;
		selectedIntervalRatePlanIntervalValue = "" + days;
		boolean intervalRatePlan = false;
		String selectedPackageRatePlanRateType = null;
		boolean selectedIntervalRatePlanProRateForEachSeasonbyDefault = false;
		ArrayList<ArrayList<String>> selectedPackageRatePlanProducsSWithDetails = new ArrayList<>();
		HashMap<String, String> getRateLevelPolicy = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicy = new HashMap<String, String>();

		String subTotalBE = "";
		String addedProductAmount = "";
		ArrayList<String> multipleAddedProductAmount = new ArrayList<>();

		ArrayList<String> multipleProductAmount = new ArrayList<>();
		ArrayList<String> multipleProductRatePlanAssociatedAmount = new ArrayList<>();
		String taxAppliedBE = "";
		String feesAppliedBE = "";
		String taxAndServicesFeesAppliedBE = "";

		ArrayList<String> productWithDetails = new ArrayList<>();
		ArrayList<ArrayList<String>> multipleProductWithDetails = new ArrayList<>();
		int seasonDays = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate);
		String seasonDuration = "" + seasonDays + "";
		Boolean isSellOnEngine = false;
		String productPolicy = "";
		String calculatedAmountofProductForThisReservation = "";
		
		try {

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 1);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();

			checkInDate=Utility.getCurrentDate("dd/MM/yyyy");
			checkOutDate=Utility.GetNextDate(1, "dd/MM/yyyy");
			String[] casesList = cases.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("4");
			}
			login_Group(driver);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		testSteps.add("=================GET THE PROPERTY TIMEZONE ======================");
		app_logs.info("================= GET THE PROPERTY TIMEZONE ======================");
		
	try {
		navigation.Setup(driver);
		app_logs.info("Navigate Setup");
		testSteps.add("Navigate Setup");
		navigation.Properties(driver);
		app_logs.info("Navigate Properties");
		testSteps.add("Navigate Properties");
		propertyName = properties.getPropertyName(driver, 1);
		
			app_logs.info("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ==");
			testSteps.add("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ======");
			navigation.Inventory_BackWard_Admin(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			navigation.Distribution(driver);
			app_logs.info("Navigate Distribution");
			testSteps.add("Navigate Distribution");

			app_logs.info("Get Booking Engine Channel Complete name");
			testSteps.add("Get Booking Engine Channel Complete name");
			ArrayList<String> channelsList = distribution.getAllChannelsContainingName(driver, bookingEngineChannel);
			if (channelsList.size() == 1) {
				bookingEngineChannel = channelsList.get(0);
			} else {
				for (int i = 0; i < channelsList.size(); i++) {
					if (channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())) {
						bookingEngineChannel = channelsList.get(i);
					}
				}
			}
			app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
			testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
			boolean distribute = false;
			if(bookingEngineChannel.contains("'")) {
				distribute = distribution.getDistributeValueOfChannel(driver, bookingEngineChannel.split("'")[0]);
			}else {
				distribute = distribution.getChannelDistributeValue(driver, bookingEngineChannel);
			}
			if (distribute) {
				app_logs.info("'" + bookingEngineChannel + "' Distribute is selected by default");
				testSteps.add("'" + bookingEngineChannel + "' Distribute is selected by default");
			} else {

				app_logs.info("'" + bookingEngineChannel + "' Distribute is not selected by default");
				testSteps.add("'" + bookingEngineChannel + "' Distribute is not selected by default");
			}
			Assert.assertTrue(distribute,
					"Failed '" + bookingEngineChannel + "' Distribute is not selected by default");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Precondition '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ",
						testName, "Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Preconditions '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ",
						testName, "Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
			app_logs.info("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
			navigation.Inventory(driver);
			derivedRate.clickTab(driver, "Products & Bundles", testSteps);
			testSteps.add("Navigated to products and bundles");
			ratePackage.spinnerLoading(driver);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to  navigate to products and bundles", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			for (int i = 0; i < isSellOnBookingEngine.split(Utility.DELIM).length; i++) {
				productWithDetails.clear();
				isSellOnEngine = Boolean.parseBoolean(isSellOnBookingEngine.split(Utility.DELIM)[i]);
				String singleProductPolicy = "";
				if (!productPolicy.equals("")) {
					singleProductPolicy = productPolicy.split(Utility.DELIM)[i];
				}
				String singleProductCategory = productCategory.split(Utility.DELIM)[i];
				if (Boolean.parseBoolean(createLedgerAccount)) {
					singleProductCategory = ledgerAccountName;
				}

				testSteps.add("=================== VERIFY PRODUCT EXIST ======================");
				app_logs.info("=================== VERIFY PRODUCT EXIST ======================");
				if (!ratePackage.verifyProductExist(driver, productName.split(Utility.DELIM)[i])) {

					testSteps.add(
							"Successfully verified product '" + productName.split(Utility.DELIM)[i] + "' not exist.");
					app_logs.info(
							"Successfully verified product '" + productName.split(Utility.DELIM)[i] + "' not exist.");
					testSteps.add("=================== CREATE PRODUCT ======================");
					app_logs.info("=================== CREATE PRODUCT ======================");

					getTestSteps.clear();
					getTestSteps = ratePackage.createProduct(driver, productName.split(Utility.DELIM)[i],
							isSellOnEngine, bookingEngineAvailabilityOption.split(Utility.DELIM)[i],
							reservationRoomClassName, productDescription.split(Utility.DELIM)[i],
							productAmount.split(Utility.DELIM)[i], singleProductPolicy, singleProductCategory,
							calculationMethodOne.split(Utility.DELIM)[i], calculationMethodTwo.split(Utility.DELIM)[i],
							seasonStartDate, seasonEndDate, seasonDuration, timeZone);
					testSteps.addAll(getTestSteps);

					testSteps.add("Created product successfully");
					app_logs.info("Created product successfully");
					productWithDetails = ratePackage.getProductDetail(driver, productName.split(Utility.DELIM)[i]);
					app_logs.info("'" + productWithDetails.get(0) + "'");
					app_logs.info("'" + productWithDetails.get(1) + "'");
					app_logs.info("'" + productWithDetails.get(2) + "'");
					app_logs.info("'" + productWithDetails.get(3) + "'");
					app_logs.info("'" + productWithDetails.get(4) + "'");
					app_logs.info("'" + productWithDetails.get(5) + "'");

				} else {
					boolean updatebookingEngineProduct = false;
					boolean updateProductCategory = false;
					boolean updateProductAmount = false;
					boolean updateCalculationMethodOne = false;
					boolean updateCalculationMethodTwo = false;
					testSteps.add("Successfully verified product '" + productName.split(Utility.DELIM)[i]
							+ "' already exist.");
					app_logs.info("Successfully verified product '" + productName.split(Utility.DELIM)[i]
							+ "' already exist.");
					testSteps.add("=================== VERIFY PRODUCT DETAILS ======================");
					app_logs.info("=================== VERIFY PRODUCT DETAILS ======================");
					boolean currentBookingEngineStatus = ratePackage.isBookingEngineProduct(driver,
							productName.split(Utility.DELIM)[i]);

					testSteps.add("Required Booking Engine Status '" + isSellOnEngine + "'");
					app_logs.info("Required Booking Engine Status '" + isSellOnEngine + "'");
					testSteps.add("Found Booking Engine Status '" + currentBookingEngineStatus + "'");
					app_logs.info("Found Booking Engine Status '" + currentBookingEngineStatus + "'");

					app_logs.info("Diff '" + currentBookingEngineStatus != isSellOnEngine + "'");
					if (currentBookingEngineStatus != isSellOnEngine) {
						updatebookingEngineProduct = true;
					}
					productWithDetails = ratePackage.getProductDetail(driver, productName.split(Utility.DELIM)[i]);
					app_logs.info("'" + productWithDetails.get(0) + "'");
					app_logs.info("'" + productWithDetails.get(1) + "'");
					app_logs.info("'" + productWithDetails.get(2) + "'");
					app_logs.info("'" + productWithDetails.get(3) + "'");
					app_logs.info("'" + productWithDetails.get(4) + "'");
					app_logs.info("'" + productWithDetails.get(5) + "'");
					testSteps.add("Required product Category '" + productCategory.split(Utility.DELIM)[i] + "'");
					app_logs.info("Required product Category '" + productCategory.split(Utility.DELIM)[i] + "'");
					testSteps.add("Found product Category '" + productWithDetails.get(2) + "'");
					app_logs.info("Found product Category '" + productWithDetails.get(2) + "'");
					if (!productWithDetails.get(2).equals(productCategory.split(Utility.DELIM)[i])) {
						updateProductCategory = true;
					}

					testSteps.add("Required product Calculation Method One '"
							+ calculationMethodOne.split(Utility.DELIM)[i] + "'");
					app_logs.info("Required product Calculation Method One  '"
							+ calculationMethodOne.split(Utility.DELIM)[i] + "'");
					testSteps.add("Found product Calculation Method One  '" + productWithDetails.get(3) + "'");
					app_logs.info("Found product Calculation Method One  '" + productWithDetails.get(3) + "'");
					if (!productWithDetails.get(3).contains(calculationMethodOne.split(Utility.DELIM)[i])) {
						updateCalculationMethodOne = true;
					}
					testSteps.add("Required product Calculation Method Two '"
							+ calculationMethodTwo.split(Utility.DELIM)[i] + "'");
					app_logs.info("Required product Calculation Method Two  '"
							+ calculationMethodTwo.split(Utility.DELIM)[i] + "'");
					testSteps.add("Found product Calculation Method Two  '" + productWithDetails.get(4) + "'");
					app_logs.info("Found product Calculation Method Two  '" + productWithDetails.get(4) + "'");
					if (!productWithDetails.get(4).contains(calculationMethodTwo.split(Utility.DELIM)[i])) {
						updateCalculationMethodTwo = true;
					}
					testSteps.add("Required product Amount '" + productAmount.split(Utility.DELIM)[i] + "'");
					app_logs.info("Required product Amount '" + productAmount.split(Utility.DELIM)[i] + "'");
					testSteps.add("Found product Amount '" + productWithDetails.get(5) + "'");
					app_logs.info("Found product Amount '" + productWithDetails.get(5) + "'");
					if (!productWithDetails.get(5).equals(productAmount.split(Utility.DELIM)[i])) {
						updateProductAmount = true;
					}
					if (updatebookingEngineProduct || updateProductCategory || updateProductAmount
							|| updateCalculationMethodOne || updateCalculationMethodTwo) {

						testSteps.add("=================== UPDATE PRODUCT ======================");
						app_logs.info("=================== UPDATE PRODUCT ======================");
						try {
							getTestSteps.clear();
							getTestSteps = ratePackage.clickEditProduct(driver, productName.split(Utility.DELIM)[i]);
							testSteps.addAll(getTestSteps);
						} catch (Exception e) {
							ratePackage.spinnerLoading(driver);
							getTestSteps.clear();
							getTestSteps = ratePackage.clickEditProduct(driver, productName.split(Utility.DELIM)[i]);
							testSteps.addAll(getTestSteps);
						}
						if (updatebookingEngineProduct) {
							ratePackage.updateBookingEngineCheckBox(driver, isSellOnEngine,
									bookingEngineAvailabilityOption.split(Utility.DELIM)[i], reservationRoomClassName,
									seasonStartDate, seasonEndDate, seasonDuration, timeZone);
						}
						if (updateProductCategory) {
							ratePackage.updateProductCategory(driver, productCategory.split(Utility.DELIM)[i]);
						}
						if (updateProductAmount) {
							ratePackage.updateProductAmount(driver, productAmount.split(Utility.DELIM)[i]);
						}
						if (updateCalculationMethodOne) {
							ratePackage.updateProductCalculationMethodOne(driver,
									calculationMethodOne.split(Utility.DELIM)[i]);
						}
						if (updateCalculationMethodTwo) {
							ratePackage.updateProductCalculationMethodTwo(driver,
									calculationMethodTwo.split(Utility.DELIM)[i]);
						}
						getTestSteps.clear();
						getTestSteps = ratePackage.clickUpdateProduct(driver);
						testSteps.addAll(getTestSteps);
						navigation.InventoryV2(driver);
						derivedRate.clickTab(driver, "Products & Bundles", getTestSteps);
						ratePackage.spinnerLoading(driver);
						productWithDetails = ratePackage.getProductDetail(driver, productName.split(Utility.DELIM)[i]);
						app_logs.info("'" + productWithDetails.get(0) + "'");
						app_logs.info("'" + productWithDetails.get(1) + "'");
						app_logs.info("'" + productWithDetails.get(2) + "'");
						app_logs.info("'" + productWithDetails.get(3) + "'");
						app_logs.info("'" + productWithDetails.get(4) + "'");
						app_logs.info("'" + productWithDetails.get(5) + "'");
					}
				}	

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create product ", testName, "CreateProducts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create product ", testName, "CreateProducts", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		try {

			testSteps.add("=================== ASSOCIATING ADDON TO A BUNDLE ======================");
			app_logs.info("=================== ASSOCIATING ADDON TO A BUNDLE ======================");
			productsBundles.clickOnBundlesTab(driver);
			productsBundles.clickOnCreateBundle(driver);	
			productsBundles.enterBundleOverviewDetails(driver, bundleName, roomClass, bundleName);
			productsBundles.clickOnProductsAndPricing(driver);
			for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
				productsBundles.selectProductsToBundle(driver, productName.split(Utility.DELIM)[i]);
			}
			productsBundles.clickOnSaveButton(driver);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to associate addons to a bundle", testName, "CreateBundle", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to associate addons to a bundle", testName, "CreateBundle", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	if(packageBundleRatePlan.equalsIgnoreCase("Yes")) {
		try {
			
			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, testSteps);
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== Verify Rate plan Exist or Not ======================");
			app_logs.info("=================== Verify Rate plan Exist or Not ======================");
			driver.navigate().refresh();
			try {
				getTestSteps.clear();
				israteplanExist = ratesGrid.searchForRatePlanExistingOrNot(driver,getTestSteps , ratePlanName);
			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				israteplanExist = ratesGrid.searchForRatePlanExistingOrNot(driver,getTestSteps , ratePlanName);
			}
			testSteps.addAll(getTestSteps);
			testSteps.add("Successfully verified ratePlan Exist.");
			app_logs.info("israteplanExist : " + israteplanExist);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (!israteplanExist) {
			testSteps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan ======================");

			// Create New Rate and Attach RoomClass
			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.createParentRatePlan(driver, delim, ratePlanType, ratePlanName, rateFolioName,
						rateDescription, rateRoomClass, rateRoomClassSize, ratePlanBaseAmount, addtionalAdult,
						additionalChild, intervalRatePlanIntervalValue, isDefaultProrateChecked,
						packageRatePlanRateType, packageRatePlanProductName,
						packageratePlanProductFirstCalculationMethod, packageratePlanProductsecondCalculationMethod,
						packageRatePlanProductAmount, channels, isRatePlanRistrictionReq, ristrictionType, isMinStay,
						minNights, isMaxStay, maxNights, isMoreThanDaysReq, moreThanDaysCount, isWithInDaysReq,
						withInDaysCount, promoCode, isPolicesReq, policiesType, policiesName, seasonDelim, seasonName,
						seasonStartDate, seasonEndDate, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
						isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
						additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason,
						extraRoomClassesInSeason, extraRoomClassRatePerNight, extraRoomClassMaxAdults,
						extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
						extraRoomClassAdditionalChildPerNight, isSeasonLevelRules, isAssignRulesByRoomClass,
						seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, isSeasonPolicies, seasonPolicyType,
						seasonPolicyValues, isAssignPoliciesByRoomClass, seasonPolicySpecificRoomClasses,
						isProRateStayInSeason, isProRateInRoomClass, isCustomPerNight, customRoomClasses,
						customRatePerNight, isCustomRatePerNightAdultandChild, customRateChildPerNight,
						customRateAdultdPerNight, roomClassesWithProRateUnchecked);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				e.printStackTrace();
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
				
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}
		
		//Clear all associated rules
		try {
			ratesGrid.clearRulesFrombulkUpdate(driver, delim, "BulkUpdate", checkInDate, checkOutDate, dateFormat,
					"yes", "yes", "yes", "yes", "yes", "yes", "yes", "All Active", "All room classes",
					bookingEngineChannel, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to bulk update rule", testName, "UpdateRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to bulk Update rule", testName, "UpdateRate", driver);
			}
		}

	}
		try {
			navigation.setup(driver);
			testSteps.add("Navigate Setup");
			app_logs.info("Navigate Setup");
			navigation.Properties(driver);
			testSteps.add("Navigate Properties");
			app_logs.info("Navigate Properties");
			try {
				navigation.openProperty(driver, testSteps, propertyName);
			} catch (Exception e) {
				navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
			}

			testSteps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			navigation.clickPropertyOptions(driver, testSteps);
			bookingEngine.clickOnBookingEngineConfigLink(driver);
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");
			testSteps.add("===== VERIFY RATE PLAN IS SELECTED =====");
			app_logs.info("===== VERIFY RATE PLAN IS SELECTED =====");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			getTestSteps.clear();
			boolean ratePlanIsSelectedOrNot;
			if(packageBundleRatePlan.equalsIgnoreCase("Yes")) {
				 ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, ratePlanName, getTestSteps);
					if (ratePlanIsSelectedOrNot) {
						testSteps.add(
								"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
						app_logs.info(
								"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
					}
			}else {
				 ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, ratePlan, getTestSteps);

				 if (ratePlanIsSelectedOrNot) {
					 testSteps.add(
						"Successfully Verified that '" + ratePlan + "' is already selected in Booking Engine.");
					 app_logs.info(
						"Successfully Verified that '" + ratePlan + "' is already selected in Booking Engine.");
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("============== OPEN BOOKING ENGINE ======================");
			app_logs.info("============== OPEN BOOKING ENGINE ======================");
			bookingEngine.clickWelcomePageLink(driver);
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Open BOOKING ENGINE", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Open BOOKING ENGINE", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate, false);
			testSteps.addAll(getTestSteps);
			testSteps.add("Select Check-In Date : " + checkInDate);
			app_logs.info("Select Check-In Date : " + checkInDate);

			testSteps.add("Select Check-Out Date : " + checkOutDate);
			app_logs.info("Select Check-Out Date : " + checkOutDate);
			getTestSteps.clear();
			getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.enterChildrenBE(driver, child);
			testSteps.addAll(getTestSteps);
			if (isPromocode) {
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterPromoCode(driver, updatedPromoCode);
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickSearchOfRooms(driver);
			testSteps.addAll(getTestSteps);
			if (ratePlanAvailableInBE) {
				testSteps.add(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyRoomClassAvailableInBookingEngine(driver, roomClass,
						true);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = bookingEngine.selectRoomClass(driver, roomClass);
				testSteps.addAll(getTestSteps);
			if(packageBundleRatePlan.equalsIgnoreCase("Yes")) {
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickBookRoomWithoutSkip(driver, ratePlanName);
				testSteps.addAll(getTestSteps);
				
				bookingEngine.skipAddProductsPart(driver);
			}else {
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlan, ratePlanAvailableInBE);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickBookRoomWithoutSkip(driver, ratePlan);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyProductExist(driver, bundleName);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
			
				getTestSteps = bookingEngine.addProduct(driver, bundleName,
									productQuantity.split("|")[0]);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickAddProductToReservation(driver);
				testSteps.addAll(getTestSteps);
			}

			}
			
				if (Boolean.parseBoolean(createBEReservation)) {
						testSteps.add("===== ENTER GUEST INFORMATION =====");
						app_logs.info("===== ENTER GUEST INFORMATION =====");
						getTestSteps.clear();
						getTestSteps = bookingEngine.enterGuestInfo(driver, firstName, lastName, emailAddress, phone,
								address, address, postalCode, city, state);
						testSteps.addAll(getTestSteps);
						testSteps.add("===== ENTER CARD DETAILS =====");
						app_logs.info("===== ENTER CARD DETAILS =====");
						getTestSteps.clear();
						getTestSteps = bookingEngine.enterMcCardInfo(driver, cardNumber, cardName, cardExpDate, cvv);
						testSteps.addAll(getTestSteps);

						subTotalBE = bookingEngine.getSubTotal(driver);
						testSteps.add("Sub Total in Booking Engine:" + subTotalBE);
						app_logs.info("Sub Total in Booking Engine:" + subTotalBE);
						try {
							taxAndServicesFeesAppliedBE = bookingEngine.getLabelAmount(driver,
									"Taxes and Services Fees");
							testSteps.add(
									"Taxes And Services Fees Applied in Booking Engine:" + taxAndServicesFeesAppliedBE);
							app_logs.info(
									"Taxes And Services Fees Applied in Booking Engine:" + taxAndServicesFeesAppliedBE);
						} catch (Exception e) {
							try {
							taxAppliedBE = bookingEngine.getLabelAmount(driver, "Taxes");
							testSteps.add("Tax Applied in Booking Engine:" + taxAppliedBE);
							app_logs.info("Tax Applied in Booking Engine:" + taxAppliedBE);

							feesAppliedBE = bookingEngine.getLabelAmount(driver, "Fees");
							testSteps.add("Fees Applied in Booking Engine:" + feesAppliedBE);
							app_logs.info("Fees Applied in Booking Engine:" + feesAppliedBE);
							}catch (Exception e1) {

								testSteps.add("No Tax Applied in Booking Engine");
								app_logs.info("No Tax Applied in Booking Engine");
							}
						}
						getTestSteps.clear();
						getTestSteps = bookingEngine.clickBookStay(driver);
						testSteps.addAll(getTestSteps);
						reservationNumber = bookingEngine.getReservationNumber(driver);
						comment = "Created rservation with " + reservationNumber
								+ " and verified reservation folio , trip summary , payments and history after reservation creation";

						testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
						app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);

					}


		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		

			try {
				testSteps.add("============ VERIFY CREATED RESERVATION DETAILS IN INNCENTER ===========");
				app_logs.info("===== VERIFY CREATED RESERVATION DETAILS IN INNCENTER =======");
				login_Group(driver);
				testSteps.add("Logged into the InnCenter ");
				app_logs.info("Logged into the InnCenter ");

				try {
					reservation.Search_ResNumber_And_Click(driver, reservationNumber);
				} catch (Exception e) {
					reservation.Search_ResNumber_And_Click(driver, reservationNumber);
				}

				testSteps.add("Search Reservation : " + reservationNumber);
				app_logs.info("Search Reservation : " + reservationNumber);
				testSteps.add("Open Reservation ");
				app_logs.info("Open Reservation ");

				reservation.clickFolio(driver, testSteps);
				folio.includeTaxinLIneItemCheckbox(driver, false);
				if(packageBundleRatePlan.equalsIgnoreCase("Yes")) {
					testSteps.add("=================== VERIFY FOLIO WITH AMOUNT ======================");
					app_logs.info("=================== VERIFY FOLIO WITH AMOUNT ======================");
					calculatedAmountofProductForThisReservation = ratePackage
							.calculateProductPriceforGivenReservationDetails(driver, days, adults, child,
									"Per " + packageratePlanProductFirstCalculationMethod, 
									"Per " + packageratePlanProductsecondCalculationMethod,
									packageRatePlanProductAmount,
									selectedIntervalRatePlanIntervalValue,
									false, testSteps);
					app_logs.info("Product Price for this reservation on InnCenter :  "
							+ calculatedAmountofProductForThisReservation);
					Double calculatedAmountForProduct=Double.parseDouble(calculatedAmountofProductForThisReservation);
					Double rate=Double.parseDouble(ratePerNight);
					rate=rate*days;
					rate=calculatedAmountForProduct+rate;
					String folioRate=String.valueOf(rate);
					folioRate=String.format("%.2f",Float.parseFloat(folioRate));
					folio.verifyBundleLineItemAdded(driver, rateFolioName, folioRate, testSteps);
					
					
				}else {
					testSteps.add("=================== VERIFY ADDED BUNDLE IN FOLIO WITH AMOUNT ======================");
					app_logs.info("=================== VERIFY ADDED BUNDLE IN FOLIO WITH AMOUNT ======================");
					
					String intervalFolioAmount=null;
					String perNightFolioAmount=null;
					String perStayFolioAmount=null;
					
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						getTestSteps.clear();
						calculatedAmountofProductForThisReservation = ratePackage
								.calculateProductPriceforGivenReservationDetails(driver, days, adults, child,
										"Per " + ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i],
										"Per " + ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i],
										productAmount.split(Utility.DELIM)[i],
										selectedIntervalRatePlanIntervalValue,
										false, testSteps);
						app_logs.info("Product Price for this reservation on InnCenter :  "
								+ calculatedAmountofProductForThisReservation);
						if(ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].equalsIgnoreCase("interval")) {
							
							intervalFolioAmount=calculatedAmountofProductForThisReservation;
							
						}else if(ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].equalsIgnoreCase("stay")) {
							
							perStayFolioAmount=calculatedAmountofProductForThisReservation;
							
						}else if(ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].equalsIgnoreCase("night")) {
							
							perNightFolioAmount=calculatedAmountofProductForThisReservation;
						}
						multipleProductRatePlanAssociatedAmount.add(calculatedAmountofProductForThisReservation);
					}
						for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
							if(ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].equalsIgnoreCase("interval")) {
								
								folio.verifyBundleLineItemAdded(driver, bundleName , intervalFolioAmount,testSteps);
								
							}else if(ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].equalsIgnoreCase("stay")) {
								
								folio.verifyBundleLineItemAdded(driver, bundleName+" - Per Stay Component(s)" , perStayFolioAmount,testSteps);
								
							}else if(ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].equalsIgnoreCase("night")) {
								
								folio.verifyBundleLineItemAdded(driver, bundleName+" - Per Night Component(s)" , perNightFolioAmount,testSteps);
								
							}
						}
				}
				
				
				
				
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		// Generate Report
		try {
			String[] testcase = cases.split(",");
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyBundlesInBookingEngine", BEExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comment,TestCore.TestRail_AssignToID);

	}
}
