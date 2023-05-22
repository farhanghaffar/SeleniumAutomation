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

public class VerifyAddOnInBookingEngine extends TestCore {

	// Automation-2149
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
	public void verifyAddOnInBookingEngine(String delim,String TestCaseID, String testCaseDescription,
			String checkInDate,
			String checkOutDate,String productName,
			String calculationMethodOne, String calculationMethodTwo, String productAmount,
			String ratePlanProductCalculationMethodOne, String ratePlanProductCalculationMethodTwo,
			String ratePlanProductAmount, String addAndVerifyProduct, String updatedRoomClassRates)
			throws Exception {

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"CreateRatePlan_VerifyAddOn");
		HashMap<String, String> staticData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"VerifyAddonNBE_StaticData");

		String bookingEngineChannel = staticData.get("channelName");
		String ratePlanType = staticData.get("ratePlanType");
		//String ratePlanType = null;
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
		String seasonStartDate = ratePlanData.get("SeasonStartDate");
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
		if (testCaseDescription.equalsIgnoreCase("Checking for Add-ons to the Folio")) {
			createBEReservation = "TRUE";
			verifyResInInncenter = "TRUE";
		}
		if (testCaseDescription.equalsIgnoreCase("taxes are not calculating for Addons")) {
			createBEReservation = "TRUE";
		}
		if (testCaseDescription.equalsIgnoreCase("Verfiy the NBE reservation by creating Addon")
				|| testCaseDescription.equalsIgnoreCase("Verify by adding multiple and addons")) {
			updateRate = "yes";
		}
		if (testCaseDescription.equalsIgnoreCase("Deactivate the addOn - should not display in UI")) {
			isSellOnBookingEngine = "FALSE";
			createBEReservation = "TRUE";
		}
		if (testCaseDescription.equalsIgnoreCase("Verify Associate addon with only one rates and verify this addons should not be visible to other ratePlan")
				|| testCaseDescription.equalsIgnoreCase("Verfiy the NBE reservation by creating Addon")
				|| testCaseDescription.equalsIgnoreCase("Verify by adding multiple and addons")) {
			createLedgerAccount = "FALSE"; 
			createTaxItem = "FALSE";
			createBEReservation = "TRUE";
			verifyResInInncenter = "TRUE";
			if (testCaseDescription.equalsIgnoreCase(
					"Verify Associate addon with only one rates and verify this addons should not be visible to other ratePlan")
					|| testCaseDescription.equalsIgnoreCase("Verify by adding multiple and addons")) {
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
			}
			app_logs.info("After loop");
			app_logs.info(isSellOnBookingEngine);
			app_logs.info(bookingEngineAvailabilityOption);
			app_logs.info(productDescription);
			app_logs.info(productCategory);
			app_logs.info(productQuantity);
		}
		

		String testName = "VerifyAddOnInBookingEngine";
		test_description = testCaseDescription + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-2149' target='_blank'>"
				+ "Click here to open JIRA: AUTOMATION-2149</a>";
		test_catagory = "BE_Addons";
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
		Properties properties = new Properties();
		Login login = new Login();

		BookingEngine bookingEngine = new BookingEngine();

		String randomNumber = Utility.GenerateRandomNumber();
		lastName = lastName + randomNumber;

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
			
			String[] casesList = TestCaseID.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("4");
			}
			driver = getDriver();
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

		try {

			testSteps.add("=================GET THE PROPERTY TIMEZONE ======================");
			app_logs.info("================= GET THE PROPERTY TIMEZONE ======================");
			navigation.Setup(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");
			navigation.Properties(driver);
			app_logs.info("Navigate Properties");
			testSteps.add("Navigate Properties");
			propertyName = properties.getPropertyName(driver, 1);
			testSteps.add("Property Name : " + propertyName);
			app_logs.info("Property Name : " + propertyName);
			numberOfProperties = properties.getNumberOfProperties(driver);
			testSteps.add("Number of Properties : " + numberOfProperties);
			app_logs.info("Number of Properties : " + numberOfProperties);
			try {
				navigation.openProperty(driver, testSteps, propertyName);
			} catch (Exception e) {
				navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
			}
			testSteps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			navigation.clickPropertyOptions(driver, testSteps);
			timeZone = navigation.get_Property_TimeZone(driver);
			testSteps.add("Property TimeZone: " + timeZone);
			navigation.Reservation_Backward(driver);
			String getCurrentDate = Utility.getNextDate(0, "dd/MM/yyyy", timeZone);
			checkInDate = getCurrentDate;
			checkOutDate = Utility.getNextDate(days, "dd/MM/yyyy", timeZone);
			app_logs.info("Check-in Date : " + checkInDate);
			app_logs.info("Check-out Date : " + checkOutDate);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get property Time Zone", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get property Time Zone", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("========== SELECT PROPERTY ==========");
			testSteps.add("========== SELECT PROPERTY ==========");
			if (numberOfProperties != 1) {
				navigation.selectProperty(driver, propertyName);
				app_logs.info("Select Property : " + propertyName);
				testSteps.add("Select Property : " + propertyName);
			} else {
				app_logs.info("Already selected Property : " + propertyName);
				testSteps.add("Already selected Property : " + propertyName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select property", testName, "QAProperty", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select property", testName, "QAProperty", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		// Get default currency
				try {
					testSteps.add("==========GET DEFAULT CURRENCY FROM CLIENT INFO========");
					app_logs.info("==========GET DEFAULT CURRENCY FROM CLIENT INFO==========");
					navigation.Admin(driver);
					app_logs.info("Navigate Admin");
					testSteps.add("Navigate Admin");
					navigation.Clientinfo(driver);
					app_logs.info("Navigate ClientInfo");
					testSteps.add("Navigate ClientInfo");
					admin.clickClientName(driver);
					app_logs.info("Clicked on Client");
					testSteps.add("Clicked on Client");
					admin.clickClientOption(driver);
					app_logs.info("Clicked on Client Options Tab");
					testSteps.add("Clicked on Client Options Tab");
					Utility.clientCurrency = admin.getDefaultClientCurrency(driver);

					Utility.clientCurrencySymbol = Utility.clientCurrency.split("\\(")[1].replace(")", "").replace(" ", "");

					Utility.clientCurrency = Utility.clientCurrency.split("\\(")[0].replace(" ", "");

					app_logs.info(
							"Default Client Currency is:" + Utility.clientCurrency + " Symbol:" + Utility.clientCurrencySymbol);
					testSteps.add(
							"Default Client Currency is:" + Utility.clientCurrency + " Symbol:" + Utility.clientCurrencySymbol);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to get Default Currency'" + entityName + "' IS SELECTED", testName,
								"Preconditions", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to get Default Currency '" + entityName + "' IS SELECTED", testName,
								"Preconditions", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

		// Checking Pre Conditions
	/*	try {
			
			app_logs.info("====== VERIFY PRE-CONDITION '" + entityName + "' IS SELECTED =====");
			testSteps.add("====== VERIFY PRE-CONDITION '" + entityName + "' IS SELECTED =====");
			try {
				navigation.Admin(driver);
			} catch (Exception e) {
				driver.navigate().refresh();
			}
			app_logs.info("Navigate Admin");
			testSteps.add("Navigate Admin");
			admin.verifyReportsV2EntitlementEnable(driver, entityName, testSteps, true);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify Precondition '" + entityName + "' IS SELECTED", testName,
						"Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify Preconditions '" + entityName + "' IS SELECTED", testName,
						"Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		}*/

		try {
			app_logs.info("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ==");
			testSteps.add("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ======");
			navigation.ReservationV2_Backward(driver);
			navigation.Inventory(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			navigation.Distribution(driver);
			app_logs.info("Navigate Distribution");
			testSteps.add("Navigate Distribution");

			app_logs.info("Get Booking Engine Channel Complete name");
			testSteps.add("Get Booking Engine Channel Complete name");
			ArrayList<String> channelsList = distribution.getAllChannelsContainingName(driver, bookingEngineChannel);
			if(channelsList.size()==1) {
				bookingEngineChannel = channelsList.get(0);
			}else {
				for(int i = 0 ; i < channelsList.size() ;i++) {
					if(channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())){
						bookingEngineChannel = channelsList.get(i);
					}
				}
			}
			app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
			testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
			boolean distribute = distribution.getChannelDistributeValue(driver, bookingEngineChannel);
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

		if (Boolean.parseBoolean(createLedgerAccount)) {
			try {
				navigation.mainSetupManu(driver);
				app_logs.info("Navigate Setup");
				testSteps.add("Navigate Setup");
				app_logs.info("======= DELETE ALREADY CREATED LEDGER ACCOUNT ==========");
				testSteps.add("======= DELETE ALREADY CREATED LEDGER ACCOUNT ==========");
				navigation.LedgerAccounts(driver);
				app_logs.info("Navigate Ledger Account");
				testSteps.add("Navigate Ledger Account");
				ledgerAccount.DeleteLedgerAccounts(driver, ledgerAccountName, false);
				app_logs.info("======= CREATE NEW LEDGER ACCOUNT ==========");
				testSteps.add("======= CREATE NEW LEDGER ACCOUNT ==========");
				ledgerAccount.NewAccountbutton(driver);
				ledgerAccountName = ledgerAccountName + randomNumber;
				ledgerAccount.enterLedgerAccountDetails(driver, ledgerAccountName, ledgerAccountDescription,
						ledgerAccountType, ledgerAccountStatus);
				ledgerAccount.SaveLedgerAccount(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create LEDGER ACCOUNT", testName, "LEDGER ACCOUNT", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create LEDGER ACCOUNT", testName, "LEDGER ACCOUNT", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (Boolean.parseBoolean(createTaxItem)) {
			try {
				derivedRate.clickTab(driver, "Taxes & Fees", testSteps);

				app_logs.info("======= DELETE ALREADY CREATED TAX ITEM ==========");
				testSteps.add("======= DELETE ALREADY CREATED TAX ITEM ==========");
				//tax.deleteTaxStartsWithSameName(driver, taxName);
				taxAndFees.deleteAllTaxesAndFee(driver, testSteps);
				app_logs.info("======= CREATE NEW TAX ITEM ==========");
				testSteps.add("======= CREATE NEW TAX ITEM ==========");
				taxName = taxName + randomNumber;
				tax.clickOnCreateTaxButton(driver);

			/*	tax.createTax(driver, test, taxName, taxName, taxDescription, taxValue, ledgerAccountName,
						Boolean.parseBoolean(excludeTaxExempt), Boolean.parseBoolean(taxPercentage),
						Boolean.parseBoolean(taxVAT));*/
				
				tax.createNewTax(driver,taxName, taxName, taxDescription, taxValue, ledgerAccountName,
						"Room Charge", Boolean.parseBoolean(excludeTaxExempt), Boolean.parseBoolean(taxPercentage),
						Boolean.parseBoolean(taxVAT),false,
						reservationRoomClassName, ratePlanName, bookingEngineChannel);

				testSteps.add("successfully create Tax '" + taxName + "'");
				app_logs.info("successfully create Tax '" + taxName + "'");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create tax Item", testName, "tax", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create tax Item", testName, "tax", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}
		try {

			if (Boolean.parseBoolean(createLedgerAccount)) {
				navigation.InventoryV2(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
			}
			app_logs.info("========== GET TODAY'S DATE ==========");
			testSteps.add("========== GET TODAY'S DATE ==========");
			navigation.ratesGrid(driver, testSteps);
			
			ratesGrid.clearRulesFrombulkUpdate(driver, delim, "BulkUpdate", checkInDate, checkOutDate, dateFormat,
					"yes", "yes", "yes","yes", "yes",
					"yes", "yes", ratePlanName, reservationRoomClassName, bookingEngineChannel,
					testSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", dateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			if (Utility.getNumberofDays(todayDate, selectedDate, dateFormat) != 0) {
				todayDate = selectedDate;
			}
			app_logs.info("Today date : " + todayDate);
			daysBeforeCheckInDate = Utility.getNumberofDays(todayDate, checkInDate, dateFormat);
			app_logs.info("Days before Checkin Date : " + daysBeforeCheckInDate);
			app_logs.info("Days before Checkin Date : " + daysBeforeCheckInDate);
			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", testName,
						"NavigateToRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", testName,
						"NavigateToRateGrid", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
			app_logs.info("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");

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
				if (testCaseDescription.equalsIgnoreCase("Verfiy the NBE reservation by creating Addon")
						|| testCaseDescription.equalsIgnoreCase("Verify by adding multiple and addons")) {
					testSteps.add("Entered product Amount '" + productAmount.split(Utility.DELIM)[i] + "'");
					app_logs.info("Entered product Amount '" + productAmount.split(Utility.DELIM)[i] + "'");
					testSteps.add("Found product Amount '" + productWithDetails.get(5) + "'");
					app_logs.info("Found product Amount '" + productWithDetails.get(5) + "'");
				}
				calculatedAmountofProductForThisReservation = ratePackage
						.calculateProductPriceforGivenReservationDetails(driver, days, adults, child,
								productWithDetails.get(3), productWithDetails.get(4), productWithDetails.get(5),
								selectedIntervalRatePlanIntervalValue,
								ratePlanType.equalsIgnoreCase("Interval rate Plan"), testSteps);
				app_logs.info("Product Price for this reservation on Booking Engine :  "
						+ calculatedAmountofProductForThisReservation);
				multipleProductWithDetails.add(new ArrayList<>(productWithDetails));
				multipleProductAmount.add(calculatedAmountofProductForThisReservation);

			}
			app_logs.info("multiple Product With Details :  " + multipleProductWithDetails);

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
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
				} else {
					Assert.assertTrue(false);
				}
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

		if (updateRules.equalsIgnoreCase("yes")) {
			try {

				testSteps.add("========== UPDATE RATE PLAN '" + ratePlanName + "'=========");
				app_logs.info("========== UPDATE RATE PLAN '" + ratePlanName + "'=========");
				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				try {
					ratesGrid.clickOnEditRatePlan(driver);
				} catch (Exception e) {
					ratesGrid.clickOnEditRatePlan(driver);
				}
				testSteps.add("Click on edit rate plan");
				app_logs.info("Click on edit rate plan");
				testSteps.add("=================== UPDATING RATE PLAN RESTRICATIONS  ======================");
				app_logs.info("=================== UPDATING RATE PLAN RESTRICATIONS  ======================");
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
				testSteps.addAll(getTestSteps);
				nightlyRate.selectRestrictionTypes(driver,
						"Length of stay" + Utility.DELIM + "Booking window" + Utility.DELIM + "Promo code", false,
						getTestSteps);
				testSteps.add("Deselect all rate plan restrictions");
				app_logs.info("Deselect all rate plan restrictions");
				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(updatedIsRatePlanRistrictionReq),
						updatedRistrictionType, Boolean.parseBoolean(updatedIsMinStay), updatedMinNights,
						Boolean.parseBoolean(updatedIsMaxStay), updatedMaxNights,
						Boolean.parseBoolean(updatedIsMoreThanDaysReq), updatedMoreThanDaysCount,
						Boolean.parseBoolean(updatedIsWithInDaysReq), updatedWithInDaysCount, updatedPromoCode,
						testSteps);
				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {

					testSteps.add("=================== UPDATING SEASON LEVEL RULES ======================");
					app_logs.info("=================== UPDATING SEASON LEVEL RULES ======================");
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);
					nightlyRate.selectRestrictionTypes(driver,
							"Min nights" + Utility.DELIM + "No check-in" + Utility.DELIM + "No check-out", false,
							getTestSteps);
					testSteps.add("Deselect all season level rules");
					app_logs.info("Deselect all season level rules");
					nightlyRate.clickAssignRulesByRoomClass(driver, testSteps, updatedSeasonIsAssignRulesByRoomClass);
					nightlyRate.enterSeasonLevelRule(driver, testSteps, updatedSeasonIsSeasonLevelRules,
							updatedSeasonIsAssignRulesByRoomClass, updatedSeasonRuleSpecificRoomClasses,
							updatedSeasonRuleType, updatedSeasonRuleMinStayValue, updatedSeasonIsSeasonRuleOnMonday,
							updatedSeasonIsSeasonRuleOnTuesday, updatedSeasonIsSeasonRuleOnWednesday,
							updatedSeasonIsSeasonRuleOnThursday, updatedSeasonIsSeasonRuleOnFriday,
							updatedSeasonIsSeasonRuleOnSaturday, updatedSeasonIsSeasonRuleOnSunday);
					nightlyRate.clickSaveSason(driver, testSteps);
					try {
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					} catch (Exception e) {
						nightlyRate.closeSeason(driver, testSteps);
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					}
					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);
				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					Assert.assertTrue(false, "Failed to Update Season Rules");
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		if (updateRate.equalsIgnoreCase("yes")) {
			try {

				testSteps.add("========== UPDATE RATE VALUE FOR ROOM CLASS '" + reservationRoomClassName + "' =====");
				app_logs.info("======= UPDATE RATE VALUE FOR ROOM CLASS '" + reservationRoomClassName + "'======");
				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				try {
					ratesGrid.clickOnEditRatePlan(driver);
				} catch (Exception e) {
					ratesGrid.clickOnEditRatePlan(driver);
				}
				testSteps.add("Click on edit rate plan");
				app_logs.info("Click on edit rate plan");
				getTestSteps.clear();
				getTestSteps = ratesGrid.ratePlanOverView(driver);
				testSteps.addAll(getTestSteps);
				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					getTestSteps.clear();
					getTestSteps = ratesGrid.enterRoomClassBaserate(driver, reservationRoomClassName,
							updatedRoomClassRates, getTestSteps);
					nightlyRate.clickSeasonsTab(driver, "Rates", testSteps);
					if (testCaseDescription.equalsIgnoreCase("Verfiy the NBE reservation by creating Addon")
							|| testCaseDescription.equalsIgnoreCase("Verify by adding multiple and addons")) {
						getRoomClassWithRates.put(reservationRoomClassName,
								ratesGrid.getRoomClassRateWithAdditionalAdultAndChild(driver, reservationRoomClassName,
										getTestSteps));
						testSteps.add("Entered Rate Amount <b>'" + updatedRoomClassRates + "'</b>");
						app_logs.info("Entered Rate Amount <b>'" + updatedRoomClassRates + "'</b>");
						ArrayList<String> roomClassRateDetails = getRoomClassWithRates.get(reservationRoomClassName);
						testSteps.add("Found Rate Amount <b>'" + roomClassRateDetails.get(0) + "'</b>");
						app_logs.info("Found Rate Amount <b>'" + roomClassRateDetails.get(0) + "'</b>");
					}
					testSteps.addAll(getTestSteps);
					nightlyRate.clickSaveSason(driver, testSteps);
					try {
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					} catch (Exception e) {
						nightlyRate.closeSeason(driver, testSteps);
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					}
					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);
				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					Assert.assertTrue(false, "Failed to Update Rate Value");
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		try {
			driver.navigate().refresh();
			testSteps.add("Navigate Rates");
			app_logs.info("Navigate Rates");
			testSteps.add("=================== GETTING RATE PLAN INFORMATION ======================");
			app_logs.info("=================== GETTING RATE PLAN INFORMATION ======================");
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			ratesGrid.clickEditIcon(driver, testSteps);
			selectedRatePlanFolioName = ratesGrid.getFolioNameOfRatePlan(driver, testSteps);
			Utility.app_logs.info("Rate Plan Folio Name : " + selectedRatePlanFolioName);
			testSteps.add("Rate Plan Folio Name : " + selectedRatePlanFolioName);
			
			nightlyRate.switchCalendarTab(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
					Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
					Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
			
				app_logs.info("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
						+ checkOutDate + ")");
				testSteps.add("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
						+ checkOutDate + ")");
				nightlyRate.selectSeasonDates(driver, testSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
				nightlyRate.clickEditThisSeasonButton(driver, testSteps);
				getRoomClassWithRates.put(reservationRoomClassName, ratesGrid
						.getRoomClassRateWithAdditionalAdultAndChild(driver, reservationRoomClassName, testSteps));
				app_logs.info(getRoomClassWithRates.get(reservationRoomClassName));

				ArrayList<String> gettest = new ArrayList<>();
				gettest = getRoomClassWithRates.get(reservationRoomClassName);
				ratePlanBaseRate = gettest.get(0);
				ratePlanAdultCapicity = gettest.get(1);
				ratePlanPersonCapicity = gettest.get(2);
				ratePlanIsAdditionalAdultChild = false;
				if (gettest.get(3).equalsIgnoreCase("yes")) {
					ratePlanIsAdditionalAdultChild = true;
				}
				ratePlanMaxAdults = gettest.get(4);
				ratePlanMaxPersons = gettest.get(5);
				ratePlanAdultsRate = gettest.get(6);
				ratePlanChildRate = gettest.get(7);
				
				for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
					multipleProductRatePlanAssociatedAmount.add("0");
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

			testSteps.add("=================== VERIFY RATE PLAN IS SELECTED IN BOOKING ENGINE ======================");
			app_logs.info("=================== VERIFY RATE PLAN IS SELECTED BOOKING ENGINE ======================");

			navigation.rateV2Setup(driver,testSteps);
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
			boolean ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, ratePlanName, getTestSteps);

			if (ratePlanIsSelectedOrNot) {
				testSteps.add(
						"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
				app_logs.info(
						"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
			} else {
				testSteps.add("Select '" + ratePlanName + "' in Booking Engine.");
				app_logs.info("Select '" + ratePlanName + "' in Booking Engine.");
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
			//getTestSteps =bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
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
				getTestSteps = bookingEngine.verifyRoomClassAvailableInBookingEngine(driver, reservationRoomClassName,
						true);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
				testSteps.addAll(getTestSteps);

				if (testCaseDescription.equalsIgnoreCase("Verfiy the NBE reservation by creating Addon")
						|| testCaseDescription.equalsIgnoreCase("Verify by adding multiple and addons")) {
					testSteps.add("=================== VERIFY RATE PLAN AMOUNT======================");
					app_logs.info("=================== VERIFY RATE PLAN AMOUNT======================");
					String BERatePlanValue = bookingEngine.getCalculateAdditionalChargesForTheEnteredAdultsAndChild(
							driver, ratePlanName, days, adults, child, reservationRoomClassName, ratePlanBaseRate,
							ratePlanAdultCapicity, ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons,
							ratePlanAdultsRate, ratePlanChildRate, ratePlanIsAdditionalAdultChild);
					String expectedRatePlanValue = ratePlanBaseRate;
					testSteps.add("Calculated Base rate Plan Amount '" + BERatePlanValue + "'");
					app_logs.info("Calculated Base rate Plan Amount '" + BERatePlanValue + "'");
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						expectedRatePlanValue = String.format("%.2f", (Float.parseFloat(expectedRatePlanValue)
								+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
						app_logs.info("Product amount '" + multipleProductRatePlanAssociatedAmount.get(i) + "'");
						app_logs.info(
								"Calculated Base rate Plan Amount plus products amount'" + expectedRatePlanValue + "'");
					}

					testSteps.add("Expected Rate Plan Amount '" + expectedRatePlanValue + "'");
					app_logs.info("Expected Rate Plan Amount '" + expectedRatePlanValue + "'");
					testSteps.add("Found Rate Plan Amount '" + BERatePlanValue + "'");
					app_logs.info("Found Rate Plan Amount '" + BERatePlanValue + "'");
					if (expectedRatePlanValue.equals(BERatePlanValue)) {
						testSteps.add("Successfully verified rate plan amount");
						app_logs.info("Successfully verified rate plan amount");
					} else {
						testSteps.add("Rate plan amount is not matching");
						app_logs.info("Rate plan amount is not matching");
					}
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickBookRoomWithoutSkip(driver, ratePlanName);
				testSteps.addAll(getTestSteps);
				if (testCaseDescription.equalsIgnoreCase("Verify cancel action for addon modal")) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickBookRoomWithoutSkip(driver, ratePlanName);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
					testSteps.addAll(getTestSteps);
				} else if (testCaseDescription.equalsIgnoreCase("Deactivate the addOn - should not display in UI")) {
					testSteps.add("===== VALIDATE DEACTIVATED PRODUCTS =====");
					app_logs.info("===== VALIDATE DEACTIVATED PRODUCTS =====");
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						bookingEngine.verifyProductExist(driver, productName.split(Utility.DELIM)[i], false);
						testSteps.add("Successfully verified deactivated product '"
								+ productName.split(Utility.DELIM)[i] + "' is not displaying in Booking Engine.");
						app_logs.info("Successfully verified deactivated product '"
								+ productName.split(Utility.DELIM)[i] + "' is not displaying in Booking Engine.");
					}
				} else {

					testSteps.add("===== VALIDATE PRODUCTS =====");
					app_logs.info("===== VALIDATE PRODUCTS =====");
					app_logs.info("multiple Product With Details :  " + multipleProductWithDetails);
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						productWithDetails.clear();
						productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
						;
						app_logs.info("Product With Details :  " + productWithDetails);

						calculatedAmountofProductForThisReservation = ratePackage
								.calculateProductPriceforGivenReservationDetails(driver, days, adults, child,
										productWithDetails.get(3), productWithDetails.get(4), productWithDetails.get(5),
										selectedIntervalRatePlanIntervalValue, intervalRatePlan, testSteps);
						productWithDetails.add(calculatedAmountofProductForThisReservation);
						multipleProductWithDetails.set(i, new ArrayList<>(productWithDetails));
						testSteps.add("Product Price for this reservation on Booking Engine :  "
								+ calculatedAmountofProductForThisReservation);
						app_logs.info(
								"Product Price for this reservation on Booking Engine :  " + productWithDetails.get(6));

						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyProductExist(driver, productName.split(Utility.DELIM)[i]);
						testSteps.addAll(getTestSteps);
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyProductAmount(driver, productName.split(Utility.DELIM)[i],
								calculatedAmountofProductForThisReservation);
						testSteps.addAll(getTestSteps);
					}
					if (Boolean.parseBoolean(addAndVerifyProduct)) {
						testSteps.add("===== ADD PRODUCT =====");
						app_logs.info("===== ADD PRODUCT =====");
						String addedProductsAmount = "0";
						for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
							String productAmountForGivenQuantity = String.format("%.2f",
									(Float.parseFloat(productQuantity.split(Utility.DELIM)[i])
											* Float.parseFloat(multipleProductAmount.get(i))));
							addedProductsAmount = String.format("%.2f", (Float.parseFloat(addedProductsAmount)
									+ Float.parseFloat(productAmountForGivenQuantity)));
							testSteps.add("Product Price for given number of Quantity("
									+ productQuantity.split(Utility.DELIM)[i] + ") :  "
									+ productAmountForGivenQuantity);
							app_logs.info("Product Price for given number of Quantity("
									+ productQuantity.split(Utility.DELIM)[i] + ") :  "
									+ productAmountForGivenQuantity);
							getTestSteps.clear();
							getTestSteps = bookingEngine.addProduct(driver, productName.split(Utility.DELIM)[i],
									productQuantity.split(Utility.DELIM)[i]);
							testSteps.addAll(getTestSteps);
						}
						testSteps.add("Total Added Product Price : " + addedProductsAmount);
						app_logs.info("Total Added Product Price : " + addedProductsAmount);
						getTestSteps.clear();
						getTestSteps = bookingEngine.clickAddProductToReservation(driver);
						testSteps.addAll(getTestSteps);

						testSteps.add("=================== VERIFY PRODUCT DETAILS ======================");
						app_logs.info("=================== VERIFY PRODUCT DETAILS ======================");
						for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
							addedProductAmount = bookingEngine.getProductAmount(driver,
									productName.split(Utility.DELIM)[i]);
							testSteps.add("Successfullt verified product '" + productName.split(Utility.DELIM)[i]
									+ "' with Amount '" + addedProductAmount
									+ "' is added in Booking Engine reservation.");
							app_logs.info("Successfullt verified product '" + productName.split(Utility.DELIM)[i]
									+ "' with Amount '" + addedProductAmount
									+ "' is added in Booking Engine reservation.");
							multipleAddedProductAmount.add(addedProductAmount);
						}
					}
					if (!Boolean.parseBoolean(addAndVerifyProduct)) {
						getTestSteps.clear();
						getTestSteps = bookingEngine.skipAddProductsPart(driver);
						testSteps.addAll(getTestSteps);
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

					if (testCaseDescription.equalsIgnoreCase("taxes are not calculating for Addons")) {
						try {
							String AfterCreationTaxAndServicesFeesAppliedBE = bookingEngine.getLabelAmount(driver,
									"Taxes and Services Fees");
							testSteps
									.add("After Reservation Creation Taxes And Services Fees Applied in Booking Engine:"
											+ AfterCreationTaxAndServicesFeesAppliedBE);
							app_logs.info(
									"After Reservation Creation Taxes And Services Fees Applied in Booking Engine:"
											+ AfterCreationTaxAndServicesFeesAppliedBE);
							Assert.assertEquals(AfterCreationTaxAndServicesFeesAppliedBE, taxAndServicesFeesAppliedBE,
									"Failed Tax and services Fees missmatched");

						} catch (Exception e) {
							/*String AfterCreationTaxAppliedBE = bookingEngine.getLabelAmount(driver, "Taxes");
							testSteps.add("After Reservation Creation Taxes Applied in Booking Engine:"
									+ AfterCreationTaxAppliedBE);
							app_logs.info("After Reservation Creation Taxes Applied in Booking Engine:"
									+ AfterCreationTaxAppliedBE);
							Assert.assertEquals(AfterCreationTaxAppliedBE, taxAppliedBE, "Failed Tax missmatched");

							String AfterCreationFeesAppliedBE = bookingEngine.getLabelAmount(driver, "Fees");
							testSteps.add("After Reservation Creation Fees Applied in Booking Engine:"
									+ AfterCreationFeesAppliedBE);
							app_logs.info("After Reservation Creation Fees Applied in Booking Engine:"
									+ AfterCreationFeesAppliedBE);
							Assert.assertEquals(AfterCreationFeesAppliedBE, feesAppliedBE, "Failed  Fees missmatched");
	*/
							testSteps.add("After Reservation Creation no Taxes Applied in Booking Engine");
							app_logs.info("After Reservation Creation no Taxes Applied in Booking Engine");
							
						}
					}
				}
			} else {
				Assert.assertTrue(false, "Failed rate Plan not available in BE");
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

		if (Boolean.parseBoolean(verifyResInInncenter) && Boolean.parseBoolean(createBEReservation)) {

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

				testSteps.add("=================== VERIFY ADDED PRODUCT ======================");
				app_logs.info("=================== VERIFY ADDED PRODUCT ======================");
				reservation.clickFolio(driver, testSteps);
				folio.includeTaxinLIneItemCheckbox(driver, false);
				if (Boolean.parseBoolean(addAndVerifyProduct)) {
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						folio.verifyLineItemAdded(driver, "Package", multipleAddedProductAmount.get(i),
								productName.split(Utility.DELIM)[i], true);
						testSteps.add("Successfully verified product '" + productName.split(Utility.DELIM)[i]
								+ "' with amount '" + multipleAddedProductAmount.get(i)
								+ "' exist in folio line items.");
						app_logs.info("Successfully verified product '" + productName.split(Utility.DELIM)[i]
								+ "' with amount '" + multipleAddedProductAmount.get(i)
								+ "' exist in folio line items.");
					}
				} else {
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						productWithDetails.clear();
						productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
						;
						folio.verifyLineItemAdded(driver, "Package", productWithDetails.get(6),
								productName.split(Utility.DELIM)[i], false);
						testSteps.add("Successfully verified product '" + productName.split(Utility.DELIM)[i]
								+ "' with amount '" + productWithDetails.get(6) + "' not exist in folio line items.");
						app_logs.info("Successfully verified product '" + productName.split(Utility.DELIM)[i]
								+ "' with amount '" + productWithDetails.get(6) + "' not exist in folio line items.");
					}
				}
				if (testCaseDescription.equalsIgnoreCase("Checking for Add-ons to the Folio")) {
					if (ratePlanProductCalculationMethodTwo.contains("night")) {
						folio.verifyLineItemClickDescription(driver, "Package", selectedRatePlanFolioName, true);
						testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
						app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
					} else {
						folio.verifyLineItemClickDescription(driver, "Room Charge", selectedRatePlanFolioName, true);
						testSteps.add("Click Line Item Description having Category 'Room Charge'.");
						app_logs.info("Click Line Item Description having Category 'Room Charge'.");
					}
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						productWithDetails.clear();
						productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
						calculatedAmountofProductForThisReservation = ratePackage
								.calculateProductPriceforGivenReservationDetails(driver, days, adults, child,
										"per " + ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i],
										"per " + ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i],
										ratePlanProductAmount.split(Utility.DELIM)[i],
										selectedIntervalRatePlanIntervalValue,
										ratePlanType.equalsIgnoreCase("Interval rate Plan"), testSteps);
						app_logs.info("productWithDetails '" + productWithDetails + "'.");
						app_logs.info("productWithDetails.get(2) '" + productWithDetails.get(2) + "'.");
						app_logs.info("calculatedAmountofProductForThisReservation '"
								+ calculatedAmountofProductForThisReservation + "'.");
						folio.VerifyPaymentPopup_LineItemWithIcon(driver, productWithDetails.get(2),
								calculatedAmountofProductForThisReservation, productWithDetails.get(2), false);
						folio.CancelPopupButton(driver, true, true);
						testSteps.add("Click Cancel Item Details Popup");
						app_logs.info("Click Cancel Item Details Popup");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			if (Boolean.parseBoolean(createLedgerAccount)) {
				try {
					navigation.mainSetupManu(driver);
					app_logs.info("Navigate Setup");
					testSteps.add("Navigate Setup");
					app_logs.info("======= DELETE ALREADY CREATED LEDGER ACCOUNT ==========");
					testSteps.add("======= DELETE ALREADY CREATED LEDGER ACCOUNT ==========");
					navigation.LedgerAccounts(driver);
					app_logs.info("Navigate Ledger Account");
					testSteps.add("Navigate Ledger Account");
					ledgerAccount.DeleteLedgerAccounts(driver, ledgerAccountName, false);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to delete LEDGER ACCOUNT", testName, "LEDGER ACCOUNT", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to delete LEDGER ACCOUNT", testName, "LEDGER ACCOUNT", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}

			if (Boolean.parseBoolean(createTaxItem)) {
				try {
					derivedRate.clickTab(driver, "Taxes", testSteps);
					app_logs.info("======= DELETE ALREADY CREATED TAX ITEM ==========");
					testSteps.add("======= DELETE ALREADY CREATED TAX ITEM ==========");
					tax.deleteTaxStartsWithSameName(driver, taxName);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create tax Item", testName, "tax", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create tax Item", testName, "tax", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}

		}

		// Generate Report
		try {
			String[] testcase = TestCaseID.split(",");
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
			// Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,
			// comment,TestCore.TestRail_AssignToID);
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
		return Utility.getData("VerifyAddOnInBookingEngine", BEExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		// driver.close();
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comment,TestCore.TestRail_AssignToID);

	}
}
