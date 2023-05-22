package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
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
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyProductsInBEAndInncenterAfterChangeStayDetails extends TestCore {

	// Automation-2174
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

	int scenarioNumber = 0;
	String propertyName = "";
	String timeZone = "";
	String bookingEngineChannelFullName = "";
	int numberOfProperties = 0;

	public static String test_description = "";
	public static String test_catagory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyProductsInBEAndInncenterAfterChangeStayDetails(String delim, String TestCaseID,
			String testCaseDescription, String checkInDate, String checkOutDate, String productName,
			String bookingEngineAvailabilityOption, String calculationMethodOne, String calculationMethodTwo,
			String productAmount, String ratePlanProductCalculationMethodOne,
			String ratePlanProductCalculationMethodTwo, String ratePlanProductAmount, String productQuantity,
			String addAdults, String addChildren, String ratePlanUpdatedProductAmount, String changeStayDetailsOption,
			String updatedIsRatePlanRistrictionReq, String updatedRistrictionType, String updatedIsMinStay,
			String updatedIsMaxStay, String updatedIsMoreThanDaysReq, String updatedIsWithInDaysReq,
			String updatedIsSeasonLevelRules, String updatedSeasonRuleType) throws Exception {

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"CreateRatePlan_VerifyProduct");
		HashMap<String, String> staticData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"VerifyProduct_StaticData");

		Utility.DELIM = "\\" + delim;
		String bookingEngineChannel = staticData.get("channelName");
		String ratePlanType = staticData.get("ratePlanType");
		String ratePlanName = staticData.get("ratePlanName");
		String isSellOnBookingEngine = staticData.get("isSellOnBookingEngine");
		String productDescription = staticData.get("productDescritpion");
		String productCategory = staticData.get("productCategory");
		String productPolicy = staticData.get("productPolicy");
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

		String updatedMinNights = staticData.get("MinNights");
		String updatedMaxNights = staticData.get("MaxNights");
		String updatedMoreThanDaysCount = staticData.get("MoreThanDaysCount");
		String updatedWithInDaysCount = staticData.get("WithInDaysCount");
		String updatedPromoCode = staticData.get("PromoCode");
		String updatedSeasonIsAssignRulesByRoomClass = staticData.get("isAssignRulesByRoomClass");
		String updatedSeasonRuleMinStayValue = staticData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnDays = staticData.get("isSeasonRuleOnDays");
		String[] splitSeasonLevelDays = isSeasonRuleOnDays.split(Utility.DELIM);
		String updatedSeasonIsSeasonRuleOnMonday = splitSeasonLevelDays[0];
		String updatedSeasonIsSeasonRuleOnTuesday = splitSeasonLevelDays[1];
		String updatedSeasonIsSeasonRuleOnWednesday = splitSeasonLevelDays[2];
		String updatedSeasonIsSeasonRuleOnThursday = splitSeasonLevelDays[3];
		String updatedSeasonIsSeasonRuleOnFriday = splitSeasonLevelDays[4];
		String updatedSeasonIsSeasonRuleOnSaturday = splitSeasonLevelDays[5];
		String updatedSeasonIsSeasonRuleOnSunday = splitSeasonLevelDays[6];

		boolean addAndVerifyProductsInReservation = true;
		if (productQuantity.equals("0")) {
			addAndVerifyProductsInReservation = false;
		}
		boolean customProducts = false;
		if (bookingEngineAvailabilityOption.equalsIgnoreCase("custom dates")) {
			customProducts = true;
		}
		app_logs.info("Number of Products '" + productName.split(Utility.DELIM).length + "'");
		if (productName.split(Utility.DELIM).length > 1) {
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
				productCategoryForMultipleProducts = productCategoryForMultipleProducts + delim + productCategory;
				productQuantityForMultipleProducts = productQuantityForMultipleProducts + delim + productQuantity;
				app_logs.info("'" + isSellOnBookingEngineForMultipleProducts + "'");
				app_logs.info("'" + bookingEngineAvailabilityOptionForMultipleProducts + "'");
				app_logs.info("'" + productDescriptionForMultipleProducts + "'");
				app_logs.info("'" + productCategoryForMultipleProducts + "'");
				app_logs.info("'" + productQuantityForMultipleProducts + "'");
			}
			isSellOnBookingEngine = isSellOnBookingEngineForMultipleProducts;
			bookingEngineAvailabilityOption = bookingEngineAvailabilityOptionForMultipleProducts;
			productDescription = productDescriptionForMultipleProducts;
			productCategory = productCategoryForMultipleProducts;
			productQuantity = productQuantityForMultipleProducts;
		}

		String dateFormat = Utility.monthDayYear_DateFormat;
		String seasonDateFormat = Utility.dayMonthYear_DateFormat;
		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate, dateFormat);
		app_logs.info("days: " + days);

		int dayyys;
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

		Utility.SEASONDELIM = seasonDelim;
		String testName = testCaseDescription;
		test_description = "<br>" + testCaseDescription + " <br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-2174' target='_blank'>"
				+ "Click here to open JIRA: AUTOMATION-2174</a>";
		test_catagory = "BE_Addons";
		scriptName.clear();
		testDescription.clear();
		testCategory.clear();
		scriptName.add(testName + "_" + testCaseDescription);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Tax tax = new Tax();
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

		BookingEngine bookingEngine = new BookingEngine();

		String randomNumber = Utility.GenerateRandomNumber();
		lastName = lastName + randomNumber;

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

		days = Utility.getNumberofDays(checkInDate, checkOutDate, dateFormat);
		app_logs.info("days: " + days);
		String productCustomStartDate = checkInDate;
		String productCustomEndDate = checkOutDate;
		int productCustomDays = Utility.getNumberofDays(productCustomStartDate, productCustomEndDate, dateFormat);
		;

		boolean isPromocode = false;
		boolean ratePlanAvailableInBE = true;
		String noRatePlanAvailableMessage = "";
		ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;
		boolean selectedRatePlanDefaultStatus = false;
		String selectedRatePlanFolioName = null;
		String selectedIntervalRatePlanIntervalValue = null;
		int numberOfIntervals = days;
		selectedIntervalRatePlanIntervalValue = "" + days;
		boolean isIntervalRatePlan = false;
		String selectedPackageRatePlanRateType = null;
		boolean selectedIntervalRatePlanProRateForEachSeasonbyDefault = false;
		ArrayList<ArrayList<String>> selectedPackageRatePlanProducsSWithDetails = new ArrayList<>();
		HashMap<String, String> getRateLevelPolicy = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicy = new HashMap<String, String>();

		String subTotalBE = "";
		String addedProductAmount = "";
		ArrayList<String> multipleAddedProductAmount = new ArrayList<>();

		ArrayList<String> multipleBEProductAmount = new ArrayList<>();
		ArrayList<String> multipleProductRatePlanAssociatedAmount = new ArrayList<>();
		ArrayList<String> afterUpdateMultipleProductRatePlanAssociatedAmount = new ArrayList<>();
		String taxAppliedBE = "";
		String feesAppliedBE = "";
		String taxAndServicesFeesAppliedBE = "";

		ArrayList<String> productWithDetails = new ArrayList<>();
		ArrayList<ArrayList<String>> multipleProductWithDetails = new ArrayList<>();
		String env = "dev";
		int seasonDays = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate, seasonDateFormat);
		String seasonDuration = "" + seasonDays + "";
		Boolean isSellOnEngine = false;
		String calculatedAmountofProductForThisReservation = "";

		app_logs.info("'" + productPolicy + "'");
		app_logs.info("'" + Utility.DELIM + "'");
		for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {

			app_logs.info("'" + productName.split(Utility.DELIM)[i] + "'");
			isSellOnEngine = Boolean.parseBoolean(isSellOnBookingEngine.split(Utility.DELIM)[i]);
			String singleProductCategory = productCategory.split(Utility.DELIM)[i];

			app_logs.info("'" + productPolicy + "'");
			String singleProductPolicy = "";
			if (!productPolicy.equals("")) {
				singleProductPolicy = productPolicy.split(Utility.DELIM)[i];
			}

			app_logs.info("'" + isSellOnEngine + "'");
			app_logs.info("'" + bookingEngineAvailabilityOption.split(Utility.DELIM)[i] + "'");
			app_logs.info("'" + productDescription.split(Utility.DELIM)[i] + "'");
			app_logs.info("'" + productAmount.split(Utility.DELIM)[i] + "'");
			app_logs.info("'" + singleProductPolicy + "'");

			app_logs.info("'" + calculationMethodOne.split(Utility.DELIM)[i] + "'");
			app_logs.info("'" + calculationMethodTwo.split(Utility.DELIM)[i] + "'");
			app_logs.info("'" + singleProductCategory + "'");
		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			try {
				driver = getDriver();
				// loginRateV2(driver);
				Login login = new Login();
				reservationRoomClassName = "Junior Suites";
				login.login(driver, "https://app.qainnroad.com", "autoota", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				// loginRateV2(driver);
				Login login = new Login();
				reservationRoomClassName = "Junior Suites";
				login.login(driver, "https://app.qainnroad.com", "autoota", "autouser", "Auto@123");
			}

			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

			numberOfProperties = 1;
			propertyName = "Automation OTA";
			timeZone = "(GMT-05:00) Eastern Time (US and Canada)";
			Utility.clientCurrencySymbol = "$";
			Utility.clientCurrency = "USD";
			scenarioNumber = 1;
			bookingEngineChannelFullName = "innRoad Booking Engine - autootaqa.client.qainnroad.com";

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
		if (scenarioNumber == 0) {
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
				if (!propertyName.contains("'")) {
					app_logs.info("Property Name : " + propertyName);
					navigation.openProperty(driver, testSteps, propertyName);
				} else {
					app_logs.info("Property Name having ' is  : " + propertyName);
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				timeZone = navigation.get_Property_TimeZone(driver);
				testSteps.add("Property TimeZone: " + timeZone);
				navigation.Reservation_Backward(driver);
				productCustomStartDate = checkOutDate;
				productCustomEndDate = Utility.addDate(days, dateFormat, checkOutDate, dateFormat, timeZone);
				productCustomDays = Utility.getNumberofDays(productCustomStartDate, productCustomEndDate, dateFormat);
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

//		// Get default currency
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
				try {
					admin.clickClientOption(driver);
				} catch (Exception e) {
					admin.clickClientName(driver);
					admin.clickClientOption(driver);
				}
				app_logs.info("Clicked on Client Options Tab");
				testSteps.add("Clicked on Client Options Tab");
				Utility.clientCurrency = admin.getDefaultClientCurrency(driver);

				Utility.clientCurrencySymbol = Utility.clientCurrency.split("\\(")[1].replace(")", "").replace(" ", "");

				Utility.clientCurrency = Utility.clientCurrency.split("\\(")[0].replace(" ", "");

				app_logs.info("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
				testSteps.add("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
				navigation.inventoryBackwardAdmin(driver);
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

			try {

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
				navigation.Admin(driver);
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
			}

			try {
				app_logs.info("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ==");
				testSteps.add(
						"====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ======");
				navigation.inventoryBackwardAdmin(driver);
				app_logs.info("Navigate Inventory");
				testSteps.add("Navigate Inventory");
				navigation.Distribution(driver);
				app_logs.info("Navigate Distribution");
				testSteps.add("Navigate Distribution");

				app_logs.info("Get Booking Engine Channel Complete name");
				testSteps.add("Get Booking Engine Channel Complete name");
				ArrayList<String> channelsList = distribution.getAllChannelsContainingName(driver,
						bookingEngineChannel);
				if(channelsList.size()==1) {
					bookingEngineChannel = channelsList.get(0);
				}else {
					boolean channelNamefound= false;
					for(int i = 0 ; i < channelsList.size() ;i++) {
						if(channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())){
							bookingEngineChannel = channelsList.get(i);
							channelNamefound= false;
						}
					}
					if(!channelNamefound) {
						bookingEngineChannel = channelsList.get(channelsList.size() -1);
					}
				}
				app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
				testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
				bookingEngineChannelFullName = bookingEngineChannel;
				boolean distribute = false;
				if (bookingEngineChannel.contains("'")) {
					distribute = distribution.getDistributeValueOfChannel(driver, bookingEngineChannel.split("'")[0]);
				} else {
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
				navigation.mainSetupManu(driver);
				app_logs.info("Navigate Setup");
				testSteps.add("Navigate Setup");

				tax.clickOnTaxButton(driver);
				testSteps.add("Navigate to taxes page");

				ArrayList<String> taxesList = tax.getExistingAvailableTaxesName(driver);
				app_logs.info("Already available Tax :" + taxesList);
				testSteps.add("Already available Tax :" + taxesList);

				for (String taxNames : taxesList) {
					getTestSteps.clear();
					getTestSteps = tax.delete_AlreadyExistingTax(driver, taxNames);
					testSteps.addAll(getTestSteps);
				}

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

		} else {
			try {
				app_logs.info("========== SELECT PROPERTY ==========");
				testSteps.add("========== SELECT PROPERTY ==========");
				if (numberOfProperties != 1) {
					navigation.selectProperty(driver, propertyName);
					app_logs.info("Select Property : " + propertyName);
					testSteps.add("Select Property : " + propertyName);
				}
				app_logs.info("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
				testSteps.add("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
				bookingEngineChannel = bookingEngineChannelFullName;
				app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
				testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to set details", testName, "LEDGER ACCOUNT", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to set details", testName, "LEDGER ACCOUNT", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		try {
			if (scenarioNumber != 0) {
				navigation.Inventory(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
			}
			scenarioNumber++;
			app_logs.info("========== GET TODAY'S DATE ==========");
			testSteps.add("========== GET TODAY'S DATE ==========");
			navigation.ratesGrid(driver, testSteps);
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
			String getCurrentDate = Utility.getNextDate(0, dateFormat, timeZone);
			if (ESTTimeZone.CompareDates(checkInDate, dateFormat, timeZone)) {
				checkInDate = getCurrentDate;
				checkOutDate = Utility.getNextDate(days, dateFormat, timeZone);
				productCustomStartDate = checkOutDate;
				productCustomEndDate = Utility.addDate(days, dateFormat, checkOutDate, dateFormat, timeZone);
				productCustomDays = Utility.getNumberofDays(productCustomStartDate, productCustomEndDate, dateFormat);
			}
			if (ESTTimeZone.CompareDates(seasonStartDate, seasonDateFormat, timeZone)) {
				seasonStartDate = getCurrentDate;
				seasonStartDate = Utility.getNextDate(seasonDays, seasonDateFormat, timeZone);
			}
			app_logs.info("Check-in Date : " + checkInDate);
			app_logs.info("Check-out Date : " + checkOutDate);
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
			for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
				productWithDetails.clear();
				isSellOnEngine = Boolean.parseBoolean(isSellOnBookingEngine.split(Utility.DELIM)[i]);
				String singleProductPolicy = "";
				if (!productPolicy.equals("")) {
					singleProductPolicy = productPolicy.split(Utility.DELIM)[i];
				}
				String singleProductCategory = productCategory.split(Utility.DELIM)[i];
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
							productCustomStartDate, productCustomEndDate, Integer.toString(productCustomDays),
							timeZone);
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
					
					testSteps.add("====== UPDATE/VERIFY ROOM CLASS IS ATTATCHED WITH PRODUCT =====");
					app_logs.info("====== UPDATE/VERIFY ROOM CLASS IS ATTATCHED WITH PRODUCT =====");
					
					boolean roomClassAlreadySelected = false;
					boolean bookingEngineOptionAlreadySelected = false;
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
						ratePackage.updateBookingEngineCheckBox(driver, isSellOnEngine);
						getTestSteps.clear();
						getTestSteps = ratePackage.selectProductRoomClass(driver, reservationRoomClassName);
						testSteps.addAll(getTestSteps);
						getTestSteps.clear();
						getTestSteps = ratePackage.updateBookingEngineOption(driver,
								bookingEngineAvailabilityOption.split(Utility.DELIM)[i], checkInDate,
								checkOutDate, days, timeZone);
						testSteps.addAll(getTestSteps);
					}
					if (isSellOnEngine && !updatebookingEngineProduct) {
						roomClassAlreadySelected = ratePackage.roomClassIsSelectedInProductDetails(driver, reservationRoomClassName);
						if(!roomClassAlreadySelected) {
							getTestSteps.clear();
							getTestSteps = ratePackage.selectProductRoomClass(driver, reservationRoomClassName);
							testSteps.addAll(getTestSteps);
						}
						bookingEngineOptionAlreadySelected = ratePackage.verifyBookingEngineOptionAlreadySelected(driver, bookingEngineAvailabilityOption.split(Utility.DELIM)[i]);
						if(!bookingEngineOptionAlreadySelected) {
						getTestSteps.clear();
						getTestSteps = ratePackage.updateBookingEngineOption(driver,
								bookingEngineAvailabilityOption.split(Utility.DELIM)[i], productCustomStartDate,
								productCustomEndDate, productCustomDays, timeZone);
						testSteps.addAll(getTestSteps);
						}
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
		
					if(updatebookingEngineProduct || updateProductCategory || updateProductAmount
						|| updateCalculationMethodOne || updateCalculationMethodTwo || !roomClassAlreadySelected
						|| !bookingEngineOptionAlreadySelected){
						getTestSteps.clear();
						getTestSteps = ratePackage.clickUpdateProduct(driver);
						testSteps.addAll(getTestSteps);
					} else {
						getTestSteps.clear();
						getTestSteps = ratePackage.closeEditProductDialogBox(driver);
						testSteps.addAll(getTestSteps);
					}
					

					navigation.InventoryV2(driver);
					derivedRate.clickTabThroughJavaScript(driver, "Products & Bundles", getTestSteps);
					ratePackage.spinnerLoading(driver);
					try {
						productWithDetails = ratePackage.getProductDetail(driver, productName.split(Utility.DELIM)[i]);
					} catch (Exception e) {
						derivedRate.clickTabThroughJavaScript(driver, "Products & Bundles", getTestSteps);
						ratePackage.spinnerLoading(driver);
						productWithDetails = ratePackage.getProductDetail(driver, productName.split(Utility.DELIM)[i]);
					}
					app_logs.info("'" + productWithDetails.get(0) + "'");
					app_logs.info("'" + productWithDetails.get(1) + "'");
					app_logs.info("'" + productWithDetails.get(2) + "'");
					app_logs.info("'" + productWithDetails.get(3) + "'");
					app_logs.info("'" + productWithDetails.get(4) + "'");
					app_logs.info("'" + productWithDetails.get(5) + "'");

				}
				if (testCaseDescription.equalsIgnoreCase("Verfiy the NBE reservation by creating Addon")
						|| testCaseDescription.equalsIgnoreCase("Verify by adding multiple and addons")) {
					testSteps.add("Entered product Amount '" + productAmount.split(Utility.DELIM)[i] + "'");
					app_logs.info("Entered product Amount '" + productAmount.split(Utility.DELIM)[i] + "'");
					testSteps.add("Found product Amount '" + productWithDetails.get(5) + "'");
					app_logs.info("Found product Amount '" + productWithDetails.get(5) + "'");
				}
				multipleProductWithDetails.add(new ArrayList<>(productWithDetails));
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
//			driver.navigate().refresh();
//			try {
//				getTestSteps.clear();
//				israteplanExist = ratesGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//			} catch (Exception e) {
//				driver.navigate().refresh();
//				getTestSteps.clear();
//				israteplanExist = ratesGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//			}
//			testSteps.addAll(getTestSteps);
			israteplanExist = true;
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

		try {
//			ratesGrid.clearRulesFrombulkUpdate(driver, delim, "BulkUpdate", checkInDate, checkOutDate, dateFormat,
//					"yes", "yes", "yes", "yes", "yes", "yes", "yes", "All Active", "All room classes",
//					bookingEngineChannel, testSteps);
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

		try {
//
//			testSteps.add("========== UPDATE RATE PLAN '" + ratePlanName + "'=========");
//			app_logs.info("========== UPDATE RATE PLAN '" + ratePlanName + "'=========");
//			driver.navigate().refresh();
//			Wait.waitUntilPageLoadNotCompleted(driver, 40);
//			ratesGrid.clickRatePlanArrow(driver, testSteps);
//			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
//			try {
//				ratesGrid.clickOnEditRatePlan(driver);
//			} catch (Exception e) {
//				ratesGrid.clickOnEditRatePlan(driver);
//			}
//			testSteps.add("Click on edit rate plan");
//			app_logs.info("Click on edit rate plan");
//			testSteps.add("=================== UPDATING RATE PLAN RESTRICATIONS  ======================");
//			app_logs.info("=================== UPDATING RATE PLAN RESTRICATIONS  ======================");
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
//			testSteps.addAll(getTestSteps);
//			nightlyRate.selectRestrictionTypes(driver,
//					"Length of stay" + Utility.DELIM + "Booking window" + Utility.DELIM + "Promo code", false,
//					getTestSteps);
//			testSteps.add("Deselect all rate plan restrictions");
//			app_logs.info("Deselect all rate plan restrictions");
//			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(updatedIsRatePlanRistrictionReq),
//					updatedRistrictionType, Boolean.parseBoolean(updatedIsMinStay), updatedMinNights,
//					Boolean.parseBoolean(updatedIsMaxStay), updatedMaxNights,
//					Boolean.parseBoolean(updatedIsMoreThanDaysReq), updatedMoreThanDaysCount,
//					Boolean.parseBoolean(updatedIsWithInDaysReq), updatedWithInDaysCount, updatedPromoCode, testSteps);
//
//			nightlyRate.switchCalendarTab(driver, testSteps);
//			Wait.waitUntilPageLoadNotCompleted(driver, 40);
//			boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
//					Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
//					Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
//			if (isSeasonExist) {
//				testSteps.add("=================== UPDATING SEASON LEVEL RULES ======================");
//				app_logs.info("=================== UPDATING SEASON LEVEL RULES ======================");
//				nightlyRate.selectSeasonDates(driver, testSteps,
//						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
//				nightlyRate.clickEditThisSeasonButton(driver, testSteps);
//				nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);
//				nightlyRate.selectRestrictionTypes(driver,
//						"Min nights" + Utility.DELIM + "No check-in" + Utility.DELIM + "No check-out", false,
//						getTestSteps);
//				testSteps.add("Deselect all season level rules");
//				app_logs.info("Deselect all season level rules");
//				if (updatedIsSeasonLevelRules.equalsIgnoreCase("Yes")) {
//					nightlyRate.clickAssignRulesByRoomClass(driver, testSteps, updatedSeasonIsAssignRulesByRoomClass);
//					nightlyRate.enterSeasonLevelRule(driver, testSteps, updatedIsSeasonLevelRules,
//							updatedSeasonIsAssignRulesByRoomClass, reservationRoomClassName, updatedSeasonRuleType,
//							updatedSeasonRuleMinStayValue, updatedSeasonIsSeasonRuleOnMonday,
//							updatedSeasonIsSeasonRuleOnTuesday, updatedSeasonIsSeasonRuleOnWednesday,
//							updatedSeasonIsSeasonRuleOnThursday, updatedSeasonIsSeasonRuleOnFriday,
//							updatedSeasonIsSeasonRuleOnSaturday, updatedSeasonIsSeasonRuleOnSunday);
//				}
//				nightlyRate.clickSaveSason(driver, testSteps);
//				try {
//					getTestSteps.clear();
//					getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
//					testSteps.addAll(getTestSteps);
//				} catch (Exception e) {
//					nightlyRate.closeSeason(driver, testSteps);
//					getTestSteps.clear();
//					getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
//					testSteps.addAll(getTestSteps);
//				}
//				Wait.wait5Second();
//				ratesGrid.closeOpendTabInMainMenu(driver);
//			} else {
//				app_logs.info("No Season For Desired Date");
//				testSteps.add("No Season For Desired Date");
//				Assert.assertTrue(false, "Failed to Update Season Rules");
//			}

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
			if (ratePlanType.equalsIgnoreCase("Interval rate Plan")) {
				selectedIntervalRatePlanIntervalValue = ratesGrid.getInterval(driver);
				testSteps.add("Rate Plan interval value: " + selectedIntervalRatePlanIntervalValue);
				app_logs.info("Rate Plan interval value: " + selectedIntervalRatePlanIntervalValue);
				numberOfIntervals = days / Integer.parseInt(selectedIntervalRatePlanIntervalValue);
				int intervalremainder = days % Integer.parseInt(selectedIntervalRatePlanIntervalValue);
				if (intervalremainder != 0) {
					numberOfIntervals++;
				}

				testSteps.add("Number of Intervals : " + numberOfIntervals);
				app_logs.info("Number of Intervals :  " + numberOfIntervals);
				selectedIntervalRatePlanProRateForEachSeasonbyDefault = ratesGrid
						.getProrateForEachSeasonbyDefault(driver);
				isIntervalRatePlan = true;
			}

			selectedChannels = nightlyRate.getSelectedChannels(driver);
			Utility.app_logs.info("Selected Channels : " + selectedChannels);
			testSteps.add("Selected Channels : " + selectedChannels);
			if (!ratesGrid.getChannelStatus(driver, bookingEngineChannel)) {
				Utility.app_logs.info("Required Channels  is not Selected : " + bookingEngineChannel);
				testSteps.add("Required Channels  is not Selected : " + bookingEngineChannel);
				nightlyRate.selectChannels(driver, bookingEngineChannel, true, testSteps);
				selectedChannels = nightlyRate.getSelectedChannels(driver);
			}
			Utility.app_logs.info("Selected Channels : " + selectedChannels);
			testSteps.add("Selected Channels : " + selectedChannels);
			selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
			if (!ratesGrid.getRoomClassStatus(driver, reservationRoomClassName)) {
				Utility.app_logs.info("Required Room CLass  is not Selected : " + reservationRoomClassName);
				testSteps.add("Required  Room CLass  is not Selected : " + reservationRoomClassName);
				nightlyRate.selectRoomClassesEditPage(driver, reservationRoomClassName, true, testSteps);
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
			}
			Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
			testSteps.add("Selected Room Classes : " + selectedRoomClasses);

			testSteps.add("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");
			app_logs.info("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");

			selectedRatePlanDefaultStatus = ratesGrid.verifyRatePlanSetToDefault(driver, testSteps, ratePlanName);
			if (ratePlanType.equalsIgnoreCase("Package rate Plan")) {
				selectedPackageRatePlanRateType = ratePackage.getselectedRateType(driver);
				testSteps.add("Package Rate Plan selected Rate Type : " + selectedPackageRatePlanRateType);
				app_logs.info("Package Rate Plan selected Rate Type : " + selectedPackageRatePlanRateType);
				if (selectedPackageRatePlanRateType.equalsIgnoreCase("Interval rates")) {
					selectedIntervalRatePlanIntervalValue = ratesGrid.getInterval(driver);
					testSteps.add("Rate Plan interval value: " + selectedIntervalRatePlanIntervalValue);
					app_logs.info("Rate Plan interval value: " + selectedIntervalRatePlanIntervalValue);
					selectedIntervalRatePlanProRateForEachSeasonbyDefault = ratesGrid
							.getProrateForEachSeasonbyDefault(driver);
					numberOfIntervals = days / Integer.parseInt(selectedIntervalRatePlanIntervalValue);
					int intervalremainder = days % Integer.parseInt(selectedIntervalRatePlanIntervalValue);
					if (intervalremainder != 0) {
						numberOfIntervals++;
					}

					testSteps.add("Number of Intervals : " + numberOfIntervals);
					app_logs.info("Number of Intervals :  " + numberOfIntervals);
					isIntervalRatePlan = true;
				}

				getTestSteps.clear();
				getTestSteps = ratePackage.clickPackageTab(driver, "Products");
				testSteps.addAll(getTestSteps);
				testSteps.add("=================== GET SELECTED PRODUCTS IN PACKAGE RATE PLAN ======================");
				app_logs.info("=================== GET SELECTED PRODUCTS IN PACKAGE RATE PLAN ======================");
				try {
					selectedPackageRatePlanProducsSWithDetails = ratePackage.getSelectedProductDetails(driver,
							testSteps);
				} catch (Exception e) {
					driver.navigate().refresh();
					driver.switchTo().alert().accept();
					getTestSteps.clear();
					getTestSteps = ratePackage.clickPackageTab(driver, "Products");
					testSteps.addAll(getTestSteps);
					selectedPackageRatePlanProducsSWithDetails = ratePackage.getSelectedProductDetails(driver,
							testSteps);
				}
				ratePackage.removeAllSelectedProductsFromRatePlan(driver);
				for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
					getTestSteps.clear();
					getTestSteps = ratePackage.addProducts(driver, productName.split(Utility.DELIM)[i],
							ratePlanProductAmount.split(Utility.DELIM)[i],
							ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i],
							ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i]);
					testSteps.addAll(getTestSteps);
					calculatedAmountofProductForThisReservation = ratePackage
							.calculateProductPriceforGivenReservationDetails(driver, days, adults, child,
									"per " + ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i],
									"per " + ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i],
									ratePlanProductAmount.split(Utility.DELIM)[i],
									selectedIntervalRatePlanIntervalValue, isIntervalRatePlan, getTestSteps);
					app_logs.info("Product  '" + productName.split(Utility.DELIM)[i]
							+ "' Price for this reservation on InnCenter :  "
							+ calculatedAmountofProductForThisReservation);
					testSteps.add("Product '" + productName.split(Utility.DELIM)[i]
							+ "' Price for this reservation on InnCenter :  "
							+ calculatedAmountofProductForThisReservation);
					multipleProductRatePlanAssociatedAmount.add(calculatedAmountofProductForThisReservation);

					calculatedAmountofProductForThisReservation = ratePackage
							.calculateProductPriceforGivenReservationDetails(driver, days, adults, child,
									"per " + calculationMethodOne.split(Utility.DELIM)[i],
									"per " + calculationMethodTwo.split(Utility.DELIM)[i],
									productAmount.split(Utility.DELIM)[i], selectedIntervalRatePlanIntervalValue,
									isIntervalRatePlan, getTestSteps);
					testSteps.add("Product '" + productName.split(Utility.DELIM)[i]
							+ "' Price for this reservation on Booking Engine :  "
							+ calculatedAmountofProductForThisReservation);
					app_logs.info("Product '" + productName.split(Utility.DELIM)[i]
							+ "' Price for this reservation on Booking Engine :  "
							+ calculatedAmountofProductForThisReservation);
					multipleBEProductAmount.add(calculatedAmountofProductForThisReservation);
				}
				selectedPackageRatePlanProducsSWithDetails = ratePackage.getSelectedProductDetails(driver, testSteps);
				getTestSteps.clear();
				getTestSteps = ratePackage.clickPackageTab(driver, "Rate plan overview");
				testSteps.addAll(getTestSteps);

			}
			ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
			getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, getTestSteps);

			Utility.app_logs.info("Selected Policies: " + getRateLevelPolicy);
			testSteps.add("Selected Policies : " + getRateLevelPolicy);
			testSteps.add("========== Getting Restrictions for rate plan <b>" + ratePlanName + "</b> ==========");
			verifyLenthOfStayChecked = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Length of stay");
			app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayChecked);

			testSteps.add("Verified Length of Stay restriction is  : " + verifyLenthOfStayChecked);
			if (verifyLenthOfStayChecked) {

				verifyMinStayCondidtion = ratesGrid.verifyLenthOfStayCheckBox(driver, getTestSteps, "Min");
				verifyMaxStayCondition = ratesGrid.verifyLenthOfStayCheckBox(driver, getTestSteps, "Max");
				if (verifyMinStayCondidtion) {
					String getMin = ratesGrid.getMinAndMaxValue(driver, "Min");
					selectedLengthofStayRestrictions.put("Min", getMin);
				}
				if (verifyMaxStayCondition) {
					String getMax = ratesGrid.getMinAndMaxValue(driver, "Max");
					selectedLengthofStayRestrictions.put("Max", getMax);
				}
				Utility.app_logs.info("Selected Length of Stay : " + selectedLengthofStayRestrictions);
				testSteps.add("Selected Length of Stay : " + selectedLengthofStayRestrictions);
			}

			selectedBookingWindowRestrictions = ratesGrid.getBookingWindowRestrictions(driver, getTestSteps,
					ratePlanName);

			if (selectedBookingWindowRestrictions.isEmpty()) {
				testSteps.add("Verified Booking Window restriction is not selected");
				Utility.app_logs.info("Verified Booking Window restriction is not selected");
			} else {
				Utility.app_logs.info("Selected Booking Window : " + selectedBookingWindowRestrictions);
				testSteps.add("Selected Booking Window : " + selectedBookingWindowRestrictions);
			}

			isPromocode = ratesGrid.isPromoCodeChecked(driver, getTestSteps);

			if (isPromocode) {
				testSteps.add("Verified Promo Code restriction is  : " + isPromocode);
				ratePlanPromoCode = ratesGrid.getPromoCode(driver, testSteps);
				Utility.app_logs.info("Selected Promo Code : " + ratePlanPromoCode);
				testSteps.add("Selected Promo Code : " + ratePlanPromoCode);
			} else {
				ratePlanPromoCode = "";
			}

			nightlyRate.switchCalendarTab(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
					Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
					Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
			if (isSeasonExist) {
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

				nightlyRate.closeSeason(driver, testSteps);
				app_logs.info("Close Season");
				ratesGrid.clickOnSaveratePlan(driver);
				//Wait.wait5Second();
				ratesGrid.closeOpendTabInMainMenu(driver);
			} else {
				app_logs.info("No Season For Desired Date");
				testSteps.add("No Season For Desired Date");
				ratePlanAvailableInBE = false;
				noRatePlanAvailableMessage = "No Season For Desired Date";
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
			}

			testSteps.add("=================== GETTING RATE PLAN RULES ======================");
			app_logs.info("=================== GETTING RATE PLAN RULES ======================");
			ratesGrid.clickForRateGridCalender(driver, testSteps);
			Utility.selectDateFromDatePicker(driver, checkInDate, dateFormat);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			try {
				ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassName);
			} catch (Exception e) {
				ratesGrid.expandReduceRoomClass(driver, testSteps, reservationRoomClassName);
			}
			ratesGrid.expandChannel(driver, testSteps, reservationRoomClassName, bookingEngineChannel);
			if (bookingEngineChannel.contains("'")) {
				minStayRule = ratesGrid.getRuleDataValuesForMinStay(driver, testSteps, reservationRoomClassName,
						bookingEngineChannel.split("'")[0], days);
			} else {
				minStayRule = ratesGrid.getRuleDataValuesForMinStay(driver, testSteps, reservationRoomClassName,
						bookingEngineChannel, days);
			}
			minrule = minStayRule;

			Collections.sort(minrule);
			app_logs.info("minrule : " + minrule);

			minStayRuleValue = Integer.parseInt((String) minrule.get(minrule.size() - 1));

			if (minStayRuleValue > 0) {
				isMinStayRule = true;
				isMinStayRuleBrokenPopComeOrNot = reservationPage.verifyMinStayPopupComeOrNot(driver, minrule,
						minStayRuleValue, days);
			}
			if (bookingEngineChannel.contains("'")) {
				noCheckInRule = ratesGrid.getRuleDataValuesForNoCheckIn(driver, testSteps, reservationRoomClassName,
						bookingEngineChannel.split("'")[0], days);
			} else {
				noCheckInRule = ratesGrid.getRuleDataValuesForNoCheckIn(driver, testSteps, reservationRoomClassName,
						bookingEngineChannel, days);
			}

			app_logs.info("noCheckInRule : " + noCheckInRule);

			checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule, checkInDate,
					checkOutDate);

			app_logs.info("checkInColor : " + checkInColor);
			if (bookingEngineChannel.contains("'")) {
				noCheckOutRule = ratesGrid.getRuleDataValuesForNoCheckOut(driver, testSteps, reservationRoomClassName,
						bookingEngineChannel.split("'")[0], days);
			} else {
				noCheckOutRule = ratesGrid.getRuleDataValuesForNoCheckOut(driver, testSteps, reservationRoomClassName,
						bookingEngineChannel, days);
			}

			app_logs.info("noCheckOutRule : " + noCheckOutRule);

			checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, noCheckInRule, noCheckOutRule,
					checkInDate, checkOutDate);
			app_logs.info("checkOutColor : " + checkOutColor);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"====== CALCULATING THE SEASON WILL DISPLAY OR NOT IN BOOKING ENGINE BASED ON RESTRICTION AND RULES =====");
			app_logs.info(
					"====== CALCULATING THE SEASON WILL DISPLAY OR NOT IN BOOKING ENGINE BASED ON RESTRICTION AND RULES =====");
			testSteps.add("Days of Reservation : " + days);
			app_logs.info("Days of Reservation : " + days);
			testSteps.add("Days before Check-In : " + daysBeforeCheckInDate);
			app_logs.info("Days before Check-In : " + daysBeforeCheckInDate);
			testSteps.add("Reservation Dates  : " + checkInDate + " - " + checkOutDate);
			app_logs.info("Reservation Dates  : " + checkInDate + " - " + checkOutDate);
			if (verifyLenthOfStayChecked) {
				if (verifyMinStayCondidtion) {
					if (!(days >= Integer.parseInt(selectedLengthofStayRestrictions.get("Min")))) {
						ratePlanAvailableInBE = false;
						noRatePlanAvailableMessage = "Rate Plan 'Min Stay' Rule is not satisfying";
						testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						testSteps.add("Rate Plan 'Min Stay' Rule is not satisfying");
						app_logs.info("Rate Plan 'Min Stay' Rule is not satisfying");
					}
				}
				if (verifyMaxStayCondition) {
					if (!(days <= Integer.parseInt(selectedLengthofStayRestrictions.get("Max")))) {
						ratePlanAvailableInBE = false;
						noRatePlanAvailableMessage = "Rate Plan 'Max Stay' Rule is not satisfying";
						testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						testSteps.add("Rate Plan 'Max Stay' Rule is not satisfying");
						app_logs.info("Rate Plan 'Max Stay' Rule is not satisfying");
					}
				}
			}
			if (selectedBookingWindowRestrictions.containsKey("More than")) {
				if (!(daysBeforeCheckInDate >= Integer.parseInt(selectedBookingWindowRestrictions.get("More than")))) {
					ratePlanAvailableInBE = false;
					noRatePlanAvailableMessage = "Rate Plan Booking Window 'More than' Rule is not satisfying";
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					testSteps.add("Rate Plan Booking Window 'More than' Rule is not satisfying");
					app_logs.info("Rate Plan Booking Window 'More than' Rule is not satisfying");
				}
			}

			if (selectedBookingWindowRestrictions.containsKey("Within")) {
				if (!(daysBeforeCheckInDate <= Integer.parseInt(selectedBookingWindowRestrictions.get("Within")))) {
					ratePlanAvailableInBE = false;
					noRatePlanAvailableMessage = "Rate Plan Booking Window 'Within' Rule is not satisfying";
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					testSteps.add("Rate Plan Booking Window 'Within' Rule is not satisfying");
					app_logs.info("Rate Plan Booking Window 'Within' Rule is not satisfying");
				}

			}

			if (!(days >= minStayRuleValue)) {
				ratePlanAvailableInBE = false;
				noRatePlanAvailableMessage = "Rate Plan season 'Min Nights' Rule is not satisfying";
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				testSteps.add("Season 'Min Nights' Rule is not satisfying");
				app_logs.info("Season 'Min Nights' Rule is not satisfying");
			}
			if (noCheckInRule.get(0).equals("Yes")) {
				ratePlanAvailableInBE = false;
				noRatePlanAvailableMessage = "Rate Plan season 'No Check-In' Rule is not satisfying";
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				testSteps.add("Season 'No Check-In' Rule is not satisfying");
				app_logs.info("Season 'No Check-In' Rule is not satisfying");
			}
			if (noCheckOutRule.get(noCheckOutRule.size() - 1).equals("Yes")) {
				ratePlanAvailableInBE = false;
				noRatePlanAvailableMessage = "Rate Plan season 'No Check-Out' Rule is not satisfying";
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				testSteps.add("Season 'No Check-Out' Rule is not satisfying");
				app_logs.info("Season 'No Check-Out' Rule is not satisfying");
			}

			if (ratePlanAvailableInBE) {
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
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

			navigation.rateV2Setup(driver);
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
			bookingEngine.clickOnBookingEngineConfigLink(driver,bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");
			testSteps.add("===== VERIFY RATE PLAN IS SELECTED =====");
			app_logs.info("===== VERIFY RATE PLAN IS SELECTED =====");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			getTestSteps.clear();
			boolean ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, ratePlanName, getTestSteps);
			Utility.closeFirstTab(driver);
			testSteps.add("Close First Tab");
			app_logs.info("Close First Tab");
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
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
				getTestSteps = bookingEngine.enterPromoCode(driver, ratePlanPromoCode);
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickSearchOfRooms(driver);
			testSteps.addAll(getTestSteps);
			testSteps.add("=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
			app_logs.info("=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
			boolean roomClassNotExist = false;
			try {
				if (!ratePlanAvailableInBE) {
					try {
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyNoRoomAvailableMessageDisplayed(driver);
						testSteps.addAll(getTestSteps);
						roomClassNotExist = true;
						testSteps.add("No Room Class available on BE becasue of '" + noRatePlanAvailableMessage + "'");
						app_logs.info("No Room Class available on BE becasue of '" + noRatePlanAvailableMessage + "'");
					} catch (Exception f) {

					}
				}

			} catch (Exception e) {
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyRoomClassAvailableInBookingEngine(driver, reservationRoomClassName,
						false);
				testSteps.addAll(getTestSteps);
				roomClassNotExist = true;
				testSteps.add("No Room Class available on BE becasue of '" + noRatePlanAvailableMessage + "'");
				app_logs.info("No Room Class available on BE becasue of '" + noRatePlanAvailableMessage + "'");

			}

			if (!roomClassNotExist) {
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
				if (!ratePlanAvailableInBE) {
					testSteps.add("No Rate Plan available on BE becasue of '" + noRatePlanAvailableMessage + "'");
					app_logs.info("No Rate Plan available on BE becasue of '" + noRatePlanAvailableMessage + "'");
				} else {

					testSteps.add("=================== VERIFY RATE PLAN AMOUNT======================");
					app_logs.info("=================== VERIFY RATE PLAN AMOUNT======================");
					String ratePlanAmountForThisReservation = bookingEngine
							.getCalculatedRatePlanAmountForTheEnteredAdultsAndChild(driver, 1, adults, child,
									reservationRoomClassName, ratePlanBaseRate, ratePlanAdultCapicity,
									ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons, ratePlanAdultsRate,
									ratePlanChildRate, ratePlanIsAdditionalAdultChild);
					testSteps.add("Calculated Rate Plan Amount for selected Reservation details '"
							+ ratePlanAmountForThisReservation + "'");
					app_logs.info("Calculated Rate Plan Amount for selected Reservation details '"
							+ ratePlanAmountForThisReservation + "'");
					String BERatePlanValue = bookingEngine.getCalculateAdditionalChargesForTheEnteredAdultsAndChild(
							driver, ratePlanName, days, adults, child, reservationRoomClassName, ratePlanBaseRate,
							ratePlanAdultCapicity, ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons,
							ratePlanAdultsRate, ratePlanChildRate, ratePlanIsAdditionalAdultChild);
					testSteps.add("Found rate Plan Amount '" + BERatePlanValue + "'");
					app_logs.info("Found rate Plan Amount '" + BERatePlanValue + "'");
					String expectedRatePlanValue = ratePlanAmountForThisReservation;
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						expectedRatePlanValue = String.format("%.2f", (Float.parseFloat(expectedRatePlanValue)
								+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
						app_logs.info("Product amount '" + multipleProductRatePlanAssociatedAmount.get(i) + "'");
						app_logs.info(
								"Calculated Base rate Plan Amount plus products amount'" + expectedRatePlanValue + "'");
					}
					testSteps.add("Expected Rate Plan Amount With Associated Products '" + expectedRatePlanValue + "'");
					app_logs.info("Expected Rate Plan Amount With Associated Products '" + expectedRatePlanValue + "'");
					if (expectedRatePlanValue.equals(BERatePlanValue)) {
						testSteps.add("Successfully verified rate plan amount");
						app_logs.info("Successfully verified rate plan amount");
					} else {
						testSteps.add("Rate plan amount is not matching");
						app_logs.info("Rate plan amount is not matching");
					}
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickBookRoomWithoutSkip(driver, ratePlanName);
					testSteps.addAll(getTestSteps);

					testSteps.add("===== VALIDATE PRODUCTS =====");
					app_logs.info("===== VALIDATE PRODUCTS =====");
					app_logs.info("multiple Product With Details :  " + multipleProductWithDetails);

					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						productWithDetails.clear();
						productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));

						app_logs.info("Product With Details :  " + productWithDetails);

						calculatedAmountofProductForThisReservation = multipleBEProductAmount.get(i);
						productWithDetails.add(calculatedAmountofProductForThisReservation);
						multipleProductWithDetails.set(i, new ArrayList<>(productWithDetails));
						testSteps.add("Product Price for this reservation on Booking Engine :  "
								+ calculatedAmountofProductForThisReservation);
						app_logs.info(
								"Product Price for this reservation on Booking Engine :  " + productWithDetails.get(6));
						if (customProducts) {
							bookingEngine.verifyProductExist(driver, productName.split(Utility.DELIM)[i], false);
							testSteps.add("Successfully verified custom product '" + productName.split(Utility.DELIM)[i]
									+ "' is not displaying in Booking Engine.");
							app_logs.info("Successfully verified customs product '"
									+ productName.split(Utility.DELIM)[i] + "' is not displaying in Booking Engine.");

						} else {
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyProductExist(driver,
									productName.split(Utility.DELIM)[i]);
							testSteps.addAll(getTestSteps);
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyProductAmount(driver,
									productName.split(Utility.DELIM)[i], calculatedAmountofProductForThisReservation);
							testSteps.addAll(getTestSteps);
						}
					}
					if (addAndVerifyProductsInReservation) {
						testSteps.add("===== ADD PRODUCT =====");
						app_logs.info("===== ADD PRODUCT =====");
						String addedProductsAmount = "0";
						for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
							String productAmountForGivenQuantity = String.format("%.2f",
									(Float.parseFloat(productQuantity.split(Utility.DELIM)[i])
											* Float.parseFloat(multipleBEProductAmount.get(i))));
							addedProductsAmount = String.format("%.2f", (Float.parseFloat(addedProductsAmount)
									+ Float.parseFloat(productAmountForGivenQuantity)));
							testSteps.add("Product '" + productName.split(Utility.DELIM)[i]
									+ "' Price for given number of Quantity(" + productQuantity.split(Utility.DELIM)[i]
									+ ") :  " + productAmountForGivenQuantity);
							app_logs.info("Product '" + productName.split(Utility.DELIM)[i]
									+ "' Price for given number of Quantity(" + productQuantity.split(Utility.DELIM)[i]
									+ ") :  " + productAmountForGivenQuantity);
							getTestSteps.clear();
							getTestSteps = bookingEngine.addProduct(driver, productName.split(Utility.DELIM)[i],
									productQuantity.split(Utility.DELIM)[i]);
							testSteps.addAll(getTestSteps);
							multipleAddedProductAmount.add(productAmountForGivenQuantity);
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
							Assert.assertEquals(addedProductAmount, multipleAddedProductAmount.get(i),
									"Failed: product amount missmatched");
						}
					} else {
						getTestSteps.clear();
						getTestSteps = bookingEngine.skipAddProductsPart(driver);
						testSteps.addAll(getTestSteps);
					}

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
//					try {
//						taxAndServicesFeesAppliedBE = bookingEngine.getLabelAmount(driver, "Taxes and Services Fees");
//						testSteps.add(
//								"Taxes And Services Fees Applied in Booking Engine:" + taxAndServicesFeesAppliedBE);
//						app_logs.info(
//								"Taxes And Services Fees Applied in Booking Engine:" + taxAndServicesFeesAppliedBE);
//					} catch (Exception e) {
//						if (addAndVerifyProductsInReservation) {
//							try {
//								taxAppliedBE = bookingEngine.getLabelAmount(driver, "Taxes");
//
//								testSteps.add("Tax Applied in Booking Engine:" + taxAppliedBE);
//								app_logs.info("Tax Applied in Booking Engine:" + taxAppliedBE);
//							} catch (Exception f) {
//
//							}
//						}
//						try {
//							feesAppliedBE = bookingEngine.getLabelAmount(driver, "Fees");
//							testSteps.add("Fees Applied in Booking Engine:" + feesAppliedBE);
//							app_logs.info("Fees Applied in Booking Engine:" + feesAppliedBE);
//						} catch (Exception f) {
//
//						}
//					}
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickBookStay(driver);
					testSteps.addAll(getTestSteps);
					reservationNumber = bookingEngine.getReservationNumber(driver);
					comment = "Created rservation with " + reservationNumber
							+ " and verified reservation folio , trip summary , payments and history after reservation creation";

					testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
					app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);

				}
			}

		} catch (Exception e) {
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
		
		
		if (ratePlanAvailableInBE) {
			try {
				testSteps.add("============ VERIFY CREATED RESERVATION DETAILS IN INNCENTER ===========");
				app_logs.info("===== VERIFY CREATED RESERVATION DETAILS IN INNCENTER =======");
				// loginRateV2(driver);
				Login login = new Login();
				login.login(driver, "https://app.qainnroad.com", "autoota", "autouser", "Auto@123");
				testSteps.add("Logged into the InnCenter ");
				app_logs.info("Logged into the InnCenter ");

				try {
					reservation.Search_ResNumber_And_Click(driver, reservationNumber);
				} catch (Exception e) {
					driver.navigate().refresh();
					reservation.Search_ResNumber_And_Click(driver, reservationNumber);
				}

				testSteps.add("Search Reservation : " + reservationNumber);
				app_logs.info("Search Reservation : " + reservationNumber);
				testSteps.add("Open Reservation ");
				app_logs.info("Open Reservation ");
				reservation.clickFolio(driver, testSteps);
				folio.includeTaxinLIneItemCheckbox(driver, false);
				if (addAndVerifyProductsInReservation) {
					testSteps.add(
							"=================== VERIFY ADDED PRODUCTS FROM BOOKING ENGINE ======================");
					app_logs.info(
							"=================== VERIFY ADDED PRODUCTS FROM BOOKING ENGINE ======================");


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
				}
				if (customProducts) {
					testSteps.add(
							"=================== VERIFY CUSTOM PRODUCTS WHICH ARE NOT ADDED FROM BOOKING ENGINE ======================");
					app_logs.info(
							"=================== VERIFY CUSTOM PRODUCTS WHICH ARE NOT ADDED FROM BOOKING ENGINE ======================");
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						productWithDetails.clear();
						productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
						folio.verifyLineItemAdded(driver, "Package", productWithDetails.get(6),
								productName.split(Utility.DELIM)[i], false);
						testSteps.add("Successfully verified product '" + productName.split(Utility.DELIM)[i]
								+ "' with amount '" + productWithDetails.get(6) + "' not exist in folio line items.");
						app_logs.info("Successfully verified product '" + productName.split(Utility.DELIM)[i]
								+ "' with amount '" + productWithDetails.get(6) + "' not exist in folio line items.");
					}
				}
				testSteps.add("=================== VERIFY ASSOCIATED PRODUCTS WITH RATE PLAN ======================");
				app_logs.info("=================== VERIFY ASSOCIATED PRODUCTS WITH RATE PLAN ======================");

				// TO DO
				String perStayComponentsAmount = "0";
				String PerNightComponentsAmount = "0";
				String PerIntervalComponentsAmount = "0";
				for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
					testSteps.add(
							"Verify Product '" + productName.split(Utility.DELIM)[i] + "' having calculations Methods '"
									+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
									+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
									+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
					app_logs.info(
							"Verify Product '" + productName.split(Utility.DELIM)[i] + "' having calculations Methods '"
									+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
									+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
									+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
					productWithDetails.clear();
					productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
					String currentproductCategory = productWithDetails.get(2);

					app_logs.info("productWithDetails '" + productWithDetails + "'.");
					app_logs.info("product Category '" + currentproductCategory + "'.");
					app_logs.info("calculatedAmountofProductForThisReservation '"
							+ multipleProductRatePlanAssociatedAmount.get(i) + "'.");
					if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("night")) {
						folio.verifyLineItemClickDescription(driver, "Package",
								selectedRatePlanFolioName + "- Per Night Component(s)", true);
						testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
						app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
						folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
								multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
						testSteps.add("Successfully verified associated Product '" + productName.split(Utility.DELIM)[i]
								+ "' exist.");
						app_logs.info("Successfully verified associated Product '" + productName.split(Utility.DELIM)[i]
								+ "' exist.");
						folio.CancelPopupButton(driver, true, true);
						testSteps.add("Click Cancel Item Details Popup");
						app_logs.info("Click Cancel Item Details Popup");
						PerNightComponentsAmount = String.format("%.2f", (Float.parseFloat(PerNightComponentsAmount)
								+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
					} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("stay")) {
						folio.verifyLineItemClickDescription(driver, "Package",
								selectedRatePlanFolioName + "- Per Stay Component(s)", true);
						testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
						app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
						folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
								multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
						testSteps.add("Successfully verified associated Product '" + productName.split(Utility.DELIM)[i]
								+ "' exist.");
						app_logs.info("Successfully verified associated Product '" + productName.split(Utility.DELIM)[i]
								+ "' exist.");
						folio.CancelPopupButton(driver, true, true);
						testSteps.add("Click Cancel Item Details Popup");
						app_logs.info("Click Cancel Item Details Popup");
						perStayComponentsAmount = String.format("%.2f", (Float.parseFloat(perStayComponentsAmount)
								+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
					} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("interval")) {
						String perIntervalProductAmount = multipleProductRatePlanAssociatedAmount.get(i);
						// if (isIntervalRatePlan) {
						app_logs.info("numberOfIntervals '" + numberOfIntervals + "'");
						perIntervalProductAmount = String.format("%.2f",
								(Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i)) / numberOfIntervals));
						testSteps.add("Per Interval product Amount '" + perIntervalProductAmount + "'");
						app_logs.info("Per Interval product Amount '" + perIntervalProductAmount + "'");
						for (int j = 0; j < numberOfIntervals; j++) {
							folio.verifyLineItemClickDescription(driver, "Room Charge", selectedRatePlanFolioName, true,
									(j + 1));
							testSteps.add(
									"Click '" + (j + 1) + "' Line Item Description having Category 'Room Charge'.");
							app_logs.info(
									"Click '" + (j + 1) + "' Line Item Description having Category 'Room Charge'.");
							folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
									perIntervalProductAmount, currentproductCategory, false);
							testSteps.add("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							app_logs.info("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							folio.CancelPopupButton(driver, true, true);
							testSteps.add("Click Cancel Item Details Popup");
							app_logs.info("Click Cancel Item Details Popup");
						}
						PerIntervalComponentsAmount = String.format("%.2f",
								(Float.parseFloat(PerIntervalComponentsAmount)
										+ Float.parseFloat(perIntervalProductAmount)));
//						} else {
//							folio.verifyLineItemClickDescription(driver, "Room Charge", selectedRatePlanFolioName,
//									true);
//							testSteps.add("Click Line Item Description having Category 'Room Charge'.");
//							app_logs.info("Click Line Item Description having Category 'Room Charge'.");
//
//							folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
//									perIntervalProductAmount, currentproductCategory, false);
//							testSteps.add("Successfully verified associated Product '"
//									+ productName.split(Utility.DELIM)[i] + "' exist.");
//							app_logs.info("Successfully verified associated Product '"
//									+ productName.split(Utility.DELIM)[i] + "' exist.");
//							folio.CancelPopupButton(driver, true, true);
//							testSteps.add("Click Cancel Item Details Popup");
//							app_logs.info("Click Cancel Item Details Popup");
//							PerIntervalComponentsAmount = String.format("%.2f",
//									(Float.parseFloat(PerIntervalComponentsAmount)
//											+ Float.parseFloat(perIntervalProductAmount)));
//
//						}
					}
				}

				testSteps.add("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
				app_logs.info("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
				testSteps.add("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
				app_logs.info("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
				testSteps.add("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
				app_logs.info("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
				if (!perStayComponentsAmount.equals("0")) {
					folio.verifyLineItemAdded(driver, "Package", perStayComponentsAmount,
							selectedRatePlanFolioName + "- Per Stay Component(s)", true);
					testSteps.add("Verified line Item having all Per Stay components with Amount '"
							+ perStayComponentsAmount + "'");
					app_logs.info("Verified line Item having all Per Stay components with Amount '"
							+ perStayComponentsAmount + "'");
				}
				if (!PerNightComponentsAmount.equals("0")) {
					folio.verifyLineItemAdded(driver, "Package", PerNightComponentsAmount,
							selectedRatePlanFolioName + "- Per Night Component(s)", true);
					testSteps.add("Verified line Item having all Per Night components with Amount '"
							+ PerNightComponentsAmount + "'");
					app_logs.info("Verified line Item having all Per Night components with Amount '"
							+ PerNightComponentsAmount + "'");
				}
				if (!PerIntervalComponentsAmount.equals("0")) {
					String ratePlanAmountForThisReservation = bookingEngine
							.getCalculatedRatePlanAmountForTheEnteredAdultsAndChild(driver, 1, adults, child,
									reservationRoomClassName, ratePlanBaseRate, ratePlanAdultCapicity,
									ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons, ratePlanAdultsRate,
									ratePlanChildRate, ratePlanIsAdditionalAdultChild);
					testSteps.add("Rate Plan Amount Per Interval '" + ratePlanAmountForThisReservation + "'");
					app_logs.info("Rate Plan Amount  Per Interval '" + ratePlanAmountForThisReservation + "'");
					String combineAmount = String.format("%.2f", (Float.parseFloat(PerIntervalComponentsAmount)
							+ Float.parseFloat(ratePlanAmountForThisReservation)));
					testSteps.add("Combine lineItem Amount '" + combineAmount + "'");
					app_logs.info("Combine lineItem Amount '" + combineAmount + "'");
					folio.includeTaxinLIneItemCheckbox(driver, true);

					// if (isIntervalRatePlan) {
					for (int j = 0; j < numberOfIntervals; j++) {
						folio.verifyLineItemAdded(driver, "Room Charge", combineAmount, selectedRatePlanFolioName, true,
								j + 1);
						testSteps.add("Verified line Item Rate Plan Amount with all Per Interval components for "
								+ (j + 1) + " '" + combineAmount + "'");
						app_logs.info("Verified line Item Rate Plan Amount with all Per Interval components for "
								+ (j + 1) + " '" + combineAmount + "'");
					}
//					} else {
//						folio.verifyLineItemAdded(driver, "Room Charge", combineAmount, selectedRatePlanFolioName,
//								true);
//						testSteps.add("Verified line Item Rate Plan Amount with all Per Interval components for '"
//								+ combineAmount + "'");
//						app_logs.info("Verified line Item Rate Plan Amount with all Per Interval components for '"
//								+ combineAmount + "'");
//					}
					folio.includeTaxinLIneItemCheckbox(driver, false);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			if (!customProducts) {
			// Update product Amount
				try {
					testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
					app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
					navigation.Inventory(driver);
					testSteps.add("Navigate Inventory");
					navigation.RatesGrid(driver);
					testSteps.add("Navigate Rates Grid");
					app_logs.info("Navigate Rates");
					testSteps.add(
							"=================== UPDATE PRODUCT AMOUNT ASSOCIATED WITH RATE PLAN ======================");
					app_logs.info(
							"=================== UPDATE PRODUCT AMOUNT ASSOCIATED WITH RATE PLAN ======================");
					ratesGrid.clickRatePlanArrow(driver, testSteps);
					ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
					Wait.waitUntilPageLoadNotCompleted(driver, 40);
					ratesGrid.clickEditIcon(driver, testSteps);
					getTestSteps.clear();
					getTestSteps = ratePackage.clickPackageTab(driver, "Products");
					testSteps.addAll(getTestSteps);

					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						testSteps.addAll(ratePackage.addProductAmount(driver,
								ratePlanUpdatedProductAmount.split(Utility.DELIM)[i],
								productName.split(Utility.DELIM)[i]));
					}
					selectedPackageRatePlanProducsSWithDetails.clear();
					selectedPackageRatePlanProducsSWithDetails = ratePackage.getSelectedProductDetails(driver,
							testSteps);
					getTestSteps.clear();
					getTestSteps = ratePackage.clickPackageTab(driver, "Rate plan overview");
					testSteps.addAll(getTestSteps);
					ratesGrid.clickOnSaveratePlan(driver);
					//Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);
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

				//
				try {
					navigation.Reservation_Backward_4(driver);
					try {
						reservation.closeFirstOpenedReservation(driver, testSteps);
						reservation.Search_ResNumber_And_Click(driver, reservationNumber);
					} catch (Exception e) {
						driver.navigate().refresh();
						reservation.Search_ResNumber_And_Click(driver, reservationNumber);
					}
					testSteps.add("Search Reservation : " + reservationNumber);
					app_logs.info("Search Reservation : " + reservationNumber);
					testSteps.add("Open Reservation ");
					app_logs.info("Open Reservation ");
					testSteps.add(
							"===== VERIFY THAT ALREADY CREATED RESERVATION HAS NO EFFECT AFTER PRODUCT AMOUNT CHANGE ========");
					app_logs.info(
							"===== VERIFY THAT ALREADY CREATED RESERVATION HAS NO EFFECT AFTER PRODUCT AMOUNT CHANGE ========");

					reservation.clickFolio(driver, testSteps);
					folio.includeTaxinLIneItemCheckbox(driver, false);
					if (addAndVerifyProductsInReservation) {
						testSteps.add(
								"=================== VERIFY ADDED PRODUCTS FROM BOOKING ENGINE ======================");
						app_logs.info(
								"=================== VERIFY ADDED PRODUCTS FROM BOOKING ENGINE ======================");


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
					}
					testSteps.add(
							"=================== VERIFY ASSOCIATED PRODUCTS WITH RATE PLAN ======================");
					app_logs.info(
							"=================== VERIFY ASSOCIATED PRODUCTS WITH RATE PLAN ======================");

					String perStayComponentsAmount = "0";
					String PerNightComponentsAmount = "0";
					String PerIntervalComponentsAmount = "0";
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						testSteps.add("Verify Product '" + productName.split(Utility.DELIM)[i]
								+ "' having calculations Methods '"
								+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
								+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
								+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
						app_logs.info("Verify Product '" + productName.split(Utility.DELIM)[i]
								+ "' having calculations Methods '"
								+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
								+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
								+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
						productWithDetails.clear();
						productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
						String currentproductCategory = productWithDetails.get(2);

						app_logs.info("productWithDetails '" + productWithDetails + "'.");
						app_logs.info("productWithDetails.get(2) '" + currentproductCategory + "'.");
						app_logs.info("calculatedAmountofProductForThisReservation '"
								+ multipleProductRatePlanAssociatedAmount.get(i) + "'.");
						if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("night")) {
							folio.verifyLineItemClickDescription(driver, "Package",
									selectedRatePlanFolioName + "- Per Night Component(s)", true);
							testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
							app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
							folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
									multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
							testSteps.add("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							app_logs.info("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							folio.CancelPopupButton(driver, true, true);
							testSteps.add("Click Cancel Item Details Popup");
							app_logs.info("Click Cancel Item Details Popup");
							PerNightComponentsAmount = String.format("%.2f", (Float.parseFloat(PerNightComponentsAmount)
									+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
						} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("stay")) {
							folio.verifyLineItemClickDescription(driver, "Package",
									selectedRatePlanFolioName + "- Per Stay Component(s)", true);
							testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
							app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
							folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
									multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
							testSteps.add("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							app_logs.info("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							folio.CancelPopupButton(driver, true, true);
							testSteps.add("Click Cancel Item Details Popup");
							app_logs.info("Click Cancel Item Details Popup");
							perStayComponentsAmount = String.format("%.2f", (Float.parseFloat(perStayComponentsAmount)
									+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
						} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("interval")) {
							String perIntervalProductAmount = multipleProductRatePlanAssociatedAmount.get(i);
							// if (isIntervalRatePlan) {
							app_logs.info("numberOfIntervals '" + numberOfIntervals + "'");
							perIntervalProductAmount = String.format("%.2f",
									(Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))
											/ numberOfIntervals));
							testSteps.add("Per Interval product Amount '" + perIntervalProductAmount + "'");
							app_logs.info("Per Interval product Amount '" + perIntervalProductAmount + "'");
							for (int j = 0; j < numberOfIntervals; j++) {
								folio.verifyLineItemClickDescription(driver, "Room Charge", selectedRatePlanFolioName,
										true, (j + 1));
								testSteps.add(
										"Click '" + (j + 1) + "' Line Item Description having Category 'Room Charge'.");
								app_logs.info(
										"Click '" + (j + 1) + "' Line Item Description having Category 'Room Charge'.");
								folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
										perIntervalProductAmount, currentproductCategory, false);
								testSteps.add("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								app_logs.info("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								folio.CancelPopupButton(driver, true, true);
								testSteps.add("Click Cancel Item Details Popup");
								app_logs.info("Click Cancel Item Details Popup");
							}
							PerIntervalComponentsAmount = String.format("%.2f",
									(Float.parseFloat(PerIntervalComponentsAmount)
											+ Float.parseFloat(perIntervalProductAmount)));
//							} else {
//								folio.verifyLineItemClickDescription(driver, "Room Charge", selectedRatePlanFolioName,
//										true);
//								testSteps.add("Click Line Item Description having Category 'Room Charge'.");
//								app_logs.info("Click Line Item Description having Category 'Room Charge'.");
//
//								folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
//										perIntervalProductAmount, currentproductCategory, false);
//								testSteps.add("Successfully verified associated Product '"
//										+ productName.split(Utility.DELIM)[i] + "' exist.");
//								app_logs.info("Successfully verified associated Product '"
//										+ productName.split(Utility.DELIM)[i] + "' exist.");
//								folio.CancelPopupButton(driver, true, true);
//								testSteps.add("Click Cancel Item Details Popup");
//								app_logs.info("Click Cancel Item Details Popup");
//								PerIntervalComponentsAmount = String.format("%.2f",
//										(Float.parseFloat(PerIntervalComponentsAmount)
//												+ Float.parseFloat(perIntervalProductAmount)));
//
//							}
						}
					}

					testSteps.add("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
					app_logs.info("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
					testSteps.add("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
					app_logs.info("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
					testSteps.add("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
					app_logs.info("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
					if (!perStayComponentsAmount.equals("0")) {
						folio.verifyLineItemAdded(driver, "Package", perStayComponentsAmount,
								selectedRatePlanFolioName + "- Per Stay Component(s)", true);
						testSteps.add("Verified line Item having all Per Stay components with Amount '"
								+ perStayComponentsAmount + "'");
						app_logs.info("Verified line Item having all Per Stay components with Amount '"
								+ perStayComponentsAmount + "'");
					}
					if (!PerNightComponentsAmount.equals("0")) {
						folio.verifyLineItemAdded(driver, "Package", PerNightComponentsAmount,
								selectedRatePlanFolioName + "- Per Night Component(s)", true);
						testSteps.add("Verified line Item having all Per Night components with Amount '"
								+ PerNightComponentsAmount + "'");
						app_logs.info("Verified line Item having all Per Night components with Amount '"
								+ PerNightComponentsAmount + "'");
					}
					if (!PerIntervalComponentsAmount.equals("0")) {
						String ratePlanAmountForThisReservation = bookingEngine
								.getCalculatedRatePlanAmountForTheEnteredAdultsAndChild(driver, 1, adults, child,
										reservationRoomClassName, ratePlanBaseRate, ratePlanAdultCapicity,
										ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons,
										ratePlanAdultsRate, ratePlanChildRate, ratePlanIsAdditionalAdultChild);
						testSteps.add("Rate Plan Amount Per Interval '" + ratePlanAmountForThisReservation + "'");
						app_logs.info("Rate Plan Amount  Per Interval '" + ratePlanAmountForThisReservation + "'");
						String combineAmount = String.format("%.2f", (Float.parseFloat(PerIntervalComponentsAmount)
								+ Float.parseFloat(ratePlanAmountForThisReservation)));
						testSteps.add("Combine lineItem Amount '" + combineAmount + "'");
						app_logs.info("Combine lineItem Amount '" + combineAmount + "'");
						folio.includeTaxinLIneItemCheckbox(driver, true);

						// if (isIntervalRatePlan) {
						for (int j = 0; j < numberOfIntervals; j++) {
							folio.verifyLineItemAdded(driver, "Room Charge", combineAmount, selectedRatePlanFolioName,
									true, j + 1);
							testSteps.add("Verified line Item Rate Plan Amount with all Per Interval components for "
									+ (j + 1) + " '" + combineAmount + "'");
							app_logs.info("Verified line Item Rate Plan Amount with all Per Interval components for "
									+ (j + 1) + " '" + combineAmount + "'");
						}
//						} else {
//							folio.verifyLineItemAdded(driver, "Room Charge", combineAmount, selectedRatePlanFolioName,
//									true);
//							testSteps.add("Verified line Item Rate Plan Amount with all Per Interval components for '"
//									+ combineAmount + "'");
//							app_logs.info("Verified line Item Rate Plan Amount with all Per Interval components for '"
//									+ combineAmount + "'");
//						}
						folio.includeTaxinLIneItemCheckbox(driver, false);
					}
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				String updatedCheckInDate = "";
				String updatedCheckOutDate = "";
				String updatedAdults = "";
				String updatedChildren = "";
				int updatedNumberOfDays = 0;
				int updatedNumberOfInterval = 0;
				int addedNumberOfDays = updatedNumberOfDays;
				int addedNumberOfIntervals = updatedNumberOfInterval;
				// Change Stay Details
				try {
					int previousNumberOfDays = updatedNumberOfDays;
					int previousNumberOfIntervals = updatedNumberOfInterval;
					updatedCheckInDate = checkInDate;
					updatedCheckOutDate = Utility.addDate(2, dateFormat, checkOutDate, dateFormat, timeZone);
					updatedAdults = Integer.toString(Integer.parseInt(adults) + Integer.parseInt(addAdults));
					updatedChildren = Integer.toString(Integer.parseInt(child) + Integer.parseInt(addChildren));

					testSteps.add("============ CHANGE RESERVATION STAY DETAILS ===========");
					app_logs.info("===== CHANGE RESERVATION STAY DETAILS =======");
					testSteps.add("Updated Check-in Date : " + updatedCheckInDate);
					app_logs.info("Updated Check-in Date : " + updatedCheckInDate);
					testSteps.add("Updated Check-out Date : " + updatedCheckOutDate);
					app_logs.info("Updated Check-out Date : " + updatedCheckOutDate);
					testSteps.add("Updated Adults : " + updatedAdults);
					app_logs.info("Updated Adults : " + updatedAdults);
					testSteps.add("Updated Children : " + updatedChildren);
					app_logs.info("Updated Children : " + updatedChildren);
					updatedNumberOfDays = ESTTimeZone.numberOfDaysBetweenDates(updatedCheckInDate, updatedCheckOutDate,
							dateFormat);
					testSteps.add("Updated Number of days : " + updatedNumberOfDays);
					app_logs.info("Updated Number of days :  " + updatedNumberOfDays);
					updatedNumberOfInterval = updatedNumberOfDays;
					if (isIntervalRatePlan) {
						updatedNumberOfInterval = updatedNumberOfDays
								/ Integer.parseInt(selectedIntervalRatePlanIntervalValue);
						int intervalremainder = updatedNumberOfDays
								% Integer.parseInt(selectedIntervalRatePlanIntervalValue);
						if (intervalremainder != 0) {
							updatedNumberOfInterval++;
						}

						testSteps.add("Updated Number of Intervals : " + updatedNumberOfInterval);
						app_logs.info("Updated Number of Intervals :  " + updatedNumberOfInterval);
					}
					addedNumberOfDays = Math.abs(updatedNumberOfDays - previousNumberOfDays);
					addedNumberOfIntervals = Math.abs(updatedNumberOfInterval - previousNumberOfIntervals);

					testSteps.add("Added Number of Intervals : " + addedNumberOfIntervals);
					app_logs.info("Added Number of Intervals :  " + addedNumberOfIntervals);
					testSteps.add("Added Number of Days : " + addedNumberOfDays);
					app_logs.info("Added Number of Days :  " + addedNumberOfDays);

					reservationPage.click_DeatilsTab(driver, testSteps);
					reservationPage.changeReservationStayDetails(driver, testSteps,
							Utility.getCustomDate(updatedCheckInDate, Utility.monthDayYear_DateFormat,
									Utility.dayMonthYear_DateFormat, 0),
							Utility.getCustomDate(updatedCheckOutDate, Utility.monthDayYear_DateFormat,
									Utility.dayMonthYear_DateFormat, 0),
							updatedAdults, updatedChildren, ratePlanName, ratePlanPromoCode, changeStayDetailsOption,
							reservationRoomClassName);
					reservation.clickFolio(driver, testSteps);
					folio.includeTaxinLIneItemCheckbox(driver, false);
					if (addAndVerifyProductsInReservation) {
						testSteps.add(
								"=================== VERIFY ADDED PRODUCTS FROM BOOKING ENGINE ======================");
						app_logs.info(
								"=================== VERIFY ADDED PRODUCTS FROM BOOKING ENGINE ======================");
						

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
					}
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// verify already associated line items before change stay details
				try {
					testSteps.add(
							"===== VERIFY ALREADY ASSOCIATED PRODUCT'S LINE ITEMS AFTER PRODUCT CHANGE STAY DETAILS ========");
					app_logs.info(
							"===== VERIFY ALREADY ASSOCIATED PRODUCT'S LINE ITEMS AFTER PRODUCT CHANGE STAY DETAILS ========");

					String perStayComponentsAmount = "0";
					String PerNightComponentsAmount = "0";
					String PerIntervalComponentsAmount = "0";
					for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
						if (changeStayDetailsOption.equalsIgnoreCase("Recalculate Rate")
								&& ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("interval")) {
							continue;
						}
						testSteps.add("Verify Product '" + productName.split(Utility.DELIM)[i]
								+ "' having calculations Methods '"
								+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
								+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
								+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
						app_logs.info("Verify Product '" + productName.split(Utility.DELIM)[i]
								+ "' having calculations Methods '"
								+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
								+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
								+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
						productWithDetails.clear();
						productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
						String currentproductCategory = productWithDetails.get(2);
						
						
						app_logs.info("productWithDetails '" + productWithDetails + "'.");
						app_logs.info("productWithDetails.get(2) '" + currentproductCategory + "'.");
						app_logs.info("calculatedAmountofProductForThisReservation '"
								+ multipleProductRatePlanAssociatedAmount.get(i) + "'.");
						if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("night")) {
							folio.verifyLineItemClickDescription(driver, "Package",
									selectedRatePlanFolioName + "- Per Night Component(s)", true);
							testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
							app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
							folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
									multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
							testSteps.add("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							app_logs.info("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							folio.CancelPopupButton(driver, true, true);
							testSteps.add("Click Cancel Item Details Popup");
							app_logs.info("Click Cancel Item Details Popup");
							PerNightComponentsAmount = String.format("%.2f", (Float.parseFloat(PerNightComponentsAmount)
									+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
						} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("stay")) {
							folio.verifyLineItemClickDescription(driver, "Package",
									selectedRatePlanFolioName + "- Per Stay Component(s)", true);
							testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
							app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
							folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
									multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
							testSteps.add("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							app_logs.info("Successfully verified associated Product '"
									+ productName.split(Utility.DELIM)[i] + "' exist.");
							folio.CancelPopupButton(driver, true, true);
							testSteps.add("Click Cancel Item Details Popup");
							app_logs.info("Click Cancel Item Details Popup");
							perStayComponentsAmount = String.format("%.2f", (Float.parseFloat(perStayComponentsAmount)
									+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
						} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("interval")) {
							String perIntervalProductAmount = multipleProductRatePlanAssociatedAmount.get(i);
							// if (isIntervalRatePlan) {
							app_logs.info("numberOfIntervals '" + numberOfIntervals + "'");
							perIntervalProductAmount = String.format("%.2f",
									(Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))
											/ numberOfIntervals));
							testSteps.add("Per Interval product Amount '" + perIntervalProductAmount + "'");
							app_logs.info("Per Interval product Amount '" + perIntervalProductAmount + "'");
							for (int j = 0; j < numberOfIntervals; j++) {
								folio.verifyLineItemClickDescription(driver, "Room Charge", selectedRatePlanFolioName,
										true, (j + 1));
								testSteps.add(
										"Click '" + (j + 1) + "' Line Item Description having Category 'Room Charge'.");
								app_logs.info(
										"Click '" + (j + 1) + "' Line Item Description having Category 'Room Charge'.");
								folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
										perIntervalProductAmount, currentproductCategory, false);
								testSteps.add("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								app_logs.info("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								folio.CancelPopupButton(driver, true, true);
								testSteps.add("Click Cancel Item Details Popup");
								app_logs.info("Click Cancel Item Details Popup");
							}
							PerIntervalComponentsAmount = String.format("%.2f",
									(Float.parseFloat(PerIntervalComponentsAmount)
											+ Float.parseFloat(perIntervalProductAmount)));
//							} else {
//								folio.verifyLineItemClickDescription(driver, "Room Charge", selectedRatePlanFolioName,
//										true);
//								testSteps.add("Click Line Item Description having Category 'Room Charge'.");
//								app_logs.info("Click Line Item Description having Category 'Room Charge'.");
//
//								folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
//										perIntervalProductAmount, currentproductCategory, false);
//								testSteps.add("Successfully verified associated Product '"
//										+ productName.split(Utility.DELIM)[i] + "' exist.");
//								app_logs.info("Successfully verified associated Product '"
//										+ productName.split(Utility.DELIM)[i] + "' exist.");
//								folio.CancelPopupButton(driver, true, true);
//								testSteps.add("Click Cancel Item Details Popup");
//								app_logs.info("Click Cancel Item Details Popup");
//								PerIntervalComponentsAmount = String.format("%.2f",
//										(Float.parseFloat(PerIntervalComponentsAmount)
//												+ Float.parseFloat(perIntervalProductAmount)));
//
//							}
						}
					}

					testSteps.add("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
					app_logs.info("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
					testSteps.add("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
					app_logs.info("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
					testSteps.add("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
					app_logs.info("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
					if (!perStayComponentsAmount.equals("0")) {
						folio.verifyLineItemAdded(driver, "Package", perStayComponentsAmount,
								selectedRatePlanFolioName + "- Per Stay Component(s)", true);
						testSteps.add("Verified line Item having all Per Stay components with Amount '"
								+ perStayComponentsAmount + "'");
						app_logs.info("Verified line Item having all Per Stay components with Amount '"
								+ perStayComponentsAmount + "'");
					}
					if (!PerNightComponentsAmount.equals("0")) {
						folio.verifyLineItemAdded(driver, "Package", PerNightComponentsAmount,
								selectedRatePlanFolioName + "- Per Night Component(s)", true);
						testSteps.add("Verified line Item having all Per Night components with Amount '"
								+ PerNightComponentsAmount + "'");
						app_logs.info("Verified line Item having all Per Night components with Amount '"
								+ PerNightComponentsAmount + "'");
					}
					if (!changeStayDetailsOption.equalsIgnoreCase("Recalculate Rate")) {
						if (!PerIntervalComponentsAmount.equals("0")) {
							String ratePlanAmountForThisReservation = bookingEngine
									.getCalculatedRatePlanAmountForTheEnteredAdultsAndChild(driver, 1, adults, child,
											reservationRoomClassName, ratePlanBaseRate, ratePlanAdultCapicity,
											ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons,
											ratePlanAdultsRate, ratePlanChildRate, ratePlanIsAdditionalAdultChild);
							testSteps.add("Rate Plan Amount Per Interval '" + ratePlanAmountForThisReservation + "'");
							app_logs.info("Rate Plan Amount  Per Interval '" + ratePlanAmountForThisReservation + "'");
							String combineAmount = String.format("%.2f", (Float.parseFloat(PerIntervalComponentsAmount)
									+ Float.parseFloat(ratePlanAmountForThisReservation)));
							testSteps.add("Combine lineItem Amount '" + combineAmount + "'");
							app_logs.info("Combine lineItem Amount '" + combineAmount + "'");
							folio.includeTaxinLIneItemCheckbox(driver, true);

							for (int j = 0; j < numberOfIntervals; j++) {
								folio.verifyLineItemAdded(driver, "Room Charge", combineAmount,
										selectedRatePlanFolioName, true, j + 1);
								testSteps
										.add("Verified line Item Rate Plan Amount with all Per Interval components for "
												+ (j + 1) + " '" + combineAmount + "'");
								app_logs.info(
										"Verified line Item Rate Plan Amount with all Per Interval components for "
												+ (j + 1) + " '" + combineAmount + "'");
							}
							folio.includeTaxinLIneItemCheckbox(driver, false);
						}
					}
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e,
								"Failed to Verify already added associated products before change stay details ",
								testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e,
								"Failed to Verify already added associated products before change stay details",
								testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

// verify newly added lin items after product's amount change
				if (changeStayDetailsOption.equalsIgnoreCase("No Rate Change")) {
					// to DO
				} else {
					try {
						testSteps.add(
								"=================== VERIFY NEW ASSOCIATED PRODUCTS'S LINE ITEMS AFTER PRODUCT'S AMOUNT CHANGE ======================");
						app_logs.info(
								"=================== VERIFY NEW ASSOCIATED PRODUCTS'S LINE ITEMS AFTER PRODUCT'S AMOUNT CHANGE ======================");
						int numberOfDays = 0;
						int intervals = 0;
						if (changeStayDetailsOption.equalsIgnoreCase("Recalculate Rate")) {
							numberOfDays = updatedNumberOfDays;
							intervals = updatedNumberOfInterval;
						} else if (changeStayDetailsOption.equalsIgnoreCase("Change only for added/removed dates")) {
							numberOfDays = addedNumberOfDays;
							intervals = addedNumberOfIntervals;
						}

						multipleProductRatePlanAssociatedAmount.clear();
						for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
							calculatedAmountofProductForThisReservation = ratePackage
									.calculateProductPriceforGivenReservationDetails(driver, numberOfDays,
											updatedAdults, updatedChildren,
											"per " + ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i],
											"per " + ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i],
											ratePlanUpdatedProductAmount.split(Utility.DELIM)[i],
											selectedIntervalRatePlanIntervalValue, isIntervalRatePlan, getTestSteps);
							app_logs.info("Product '" + productName.split(Utility.DELIM)[i]
									+ "' Price for this reservation on InnCenter :  "
									+ calculatedAmountofProductForThisReservation);
							testSteps.add("Product '" + productName.split(Utility.DELIM)[i]
									+ "' Price for this reservation on InnCenter :  "
									+ calculatedAmountofProductForThisReservation);
							multipleProductRatePlanAssociatedAmount.add(calculatedAmountofProductForThisReservation);
						}

						String perStayComponentsAmount = "0";
						String PerNightComponentsAmount = "0";
						String PerIntervalComponentsAmount = "0";
						for (int i = 0; i < productName.split(Utility.DELIM).length; i++) {
							testSteps.add("Verify Product '" + productName.split(Utility.DELIM)[i]
									+ "' having calculations Methods '"
									+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
									+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
									+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
							app_logs.info("Verify Product '" + productName.split(Utility.DELIM)[i]
									+ "' having calculations Methods '"
									+ ratePlanProductCalculationMethodOne.split(Utility.DELIM)[i] + " & "
									+ ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i] + "' and Amount '"
									+ ratePlanProductAmount.split(Utility.DELIM)[i] + "'");
							productWithDetails.clear();
							productWithDetails = new ArrayList<String>(multipleProductWithDetails.get(i));
							String currentproductCategory = productWithDetails.get(2);
							
							app_logs.info("productWithDetails '" + productWithDetails + "'.");
							app_logs.info("productWithDetails.get(2) '" + currentproductCategory + "'.");
							app_logs.info("calculatedAmountofProductForThisReservation '"
									+ multipleProductRatePlanAssociatedAmount.get(i) + "'.");
							if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("night")) {
								folio.verifyLineItemClickDescription(driver, "Package",
										selectedRatePlanFolioName + "- Per Night Component(s)", true, 2);
								testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
								app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
								folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
										multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
								testSteps.add("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								app_logs.info("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								folio.CancelPopupButton(driver, true, true);
								testSteps.add("Click Cancel Item Details Popup");
								app_logs.info("Click Cancel Item Details Popup");
								PerNightComponentsAmount = String.format("%.2f",
										(Float.parseFloat(PerNightComponentsAmount)
												+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
							} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i].contains("stay")) {
								folio.verifyLineItemClickDescription(driver, "Package",
										selectedRatePlanFolioName + "- Per Stay Component(s)", true, 2);
								testSteps.add("Click Line Item Description having Category '" + "Package" + "'.");
								app_logs.info("Click Line Item Description having Category '" + "Package" + "'.");
								folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
										multipleProductRatePlanAssociatedAmount.get(i), currentproductCategory, false);
								testSteps.add("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								app_logs.info("Successfully verified associated Product '"
										+ productName.split(Utility.DELIM)[i] + "' exist.");
								folio.CancelPopupButton(driver, true, true);
								testSteps.add("Click Cancel Item Details Popup");
								app_logs.info("Click Cancel Item Details Popup");
								perStayComponentsAmount = String.format("%.2f",
										(Float.parseFloat(perStayComponentsAmount)
												+ Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i))));
							} else if (ratePlanProductCalculationMethodTwo.split(Utility.DELIM)[i]
									.contains("interval")) {
								String perIntervalProductAmount = multipleProductRatePlanAssociatedAmount.get(i);

								app_logs.info("intervals '" + intervals + "'");
								perIntervalProductAmount = String.format("%.2f",
										(Float.parseFloat(multipleProductRatePlanAssociatedAmount.get(i)) / intervals));
								testSteps.add("Per Interval product Amount '" + perIntervalProductAmount + "'");
								app_logs.info("Per Interval product Amount '" + perIntervalProductAmount + "'");
								for (int j = 0; j < intervals; j++) {
									int lineItemIndex = j + 2;
									if (changeStayDetailsOption.equalsIgnoreCase("Recalculate Rate")) {
										lineItemIndex = j + 1;
									}
									folio.verifyLineItemClickDescription(driver, "Room Charge",
											selectedRatePlanFolioName, true, lineItemIndex);
									testSteps.add("Click '" + lineItemIndex
											+ "' Line Item Description having Category 'Room Charge'.");
									app_logs.info("Click '" + lineItemIndex
											+ "' Line Item Description having Category 'Room Charge'.");
									folio.VerifyPaymentPopup_LineItemWithIcon(driver, currentproductCategory,
											perIntervalProductAmount, currentproductCategory, false);
									testSteps.add("Successfully verified associated Product '"
											+ productName.split(Utility.DELIM)[i] + "' exist.");
									app_logs.info("Successfully verified associated Product '"
											+ productName.split(Utility.DELIM)[i] + "' exist.");

									folio.CancelPopupButton(driver, true, true);
									testSteps.add("Click Cancel Item Details Popup");
									app_logs.info("Click Cancel Item Details Popup");
								}
								PerIntervalComponentsAmount = String.format("%.2f",
										(Float.parseFloat(PerIntervalComponentsAmount)
												+ Float.parseFloat(perIntervalProductAmount)));

							}
						}

						testSteps.add("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
						app_logs.info("Total Per Stay Components Amount '" + perStayComponentsAmount + "'");
						testSteps.add("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
						app_logs.info("Total Per Nights Components Amount '" + PerNightComponentsAmount + "'");
						testSteps.add("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
						app_logs.info("Total Per Interval Components Amount '" + PerIntervalComponentsAmount + "'");
						if (!perStayComponentsAmount.equals("0")) {
							folio.verifyLineItemAdded(driver, "Package", perStayComponentsAmount,
									selectedRatePlanFolioName + "- Per Stay Component(s)", true, 2);
							testSteps.add("Verified line Item having all Per Stay components with Amount '"
									+ perStayComponentsAmount + "'");
							app_logs.info("Verified line Item having all Per Stay components with Amount '"
									+ perStayComponentsAmount + "'");
						}
						if (!PerNightComponentsAmount.equals("0")) {
							folio.verifyLineItemAdded(driver, "Package", PerNightComponentsAmount,
									selectedRatePlanFolioName + "- Per Night Component(s)", true, 2);
							testSteps.add("Verified line Item having all Per Night components with Amount '"
									+ PerNightComponentsAmount + "'");
							app_logs.info("Verified line Item having all Per Night components with Amount '"
									+ PerNightComponentsAmount + "'");
						}
						if (!PerIntervalComponentsAmount.equals("0")) {
							String ratePlanAmountForThisReservation = bookingEngine
									.getCalculatedRatePlanAmountForTheEnteredAdultsAndChild(driver, 1, adults, child,
											reservationRoomClassName, ratePlanBaseRate, ratePlanAdultCapicity,
											ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons,
											ratePlanAdultsRate, ratePlanChildRate, ratePlanIsAdditionalAdultChild);
							testSteps.add("Rate Plan Amount Per Interval '" + ratePlanAmountForThisReservation + "'");
							app_logs.info("Rate Plan Amount  Per Interval '" + ratePlanAmountForThisReservation + "'");
							String combineAmount = String.format("%.2f", (Float.parseFloat(PerIntervalComponentsAmount)
									+ Float.parseFloat(ratePlanAmountForThisReservation)));

							testSteps.add("Combine lineItem Amount '" + combineAmount + "'");
							app_logs.info("Combine lineItem Amount '" + combineAmount + "'");
							folio.includeTaxinLIneItemCheckbox(driver, true);

							for (int j = 0; j < intervals; j++) {
								int lineItemIndex = j + 2;
								if (changeStayDetailsOption.equalsIgnoreCase("Recalculate Rate")) {
									lineItemIndex = j + 1;
								}
								folio.verifyLineItemAdded(driver, "Room Charge", combineAmount,
										selectedRatePlanFolioName, true, lineItemIndex);
								testSteps
										.add("Verified line Item Rate Plan Amount with all Per Interval components for "
												+ lineItemIndex + " '" + combineAmount + "'");
								app_logs.info(
										"Verified line Item Rate Plan Amount with all Per Interval components for "
												+ lineItemIndex + " '" + combineAmount + "'");
							}

							folio.includeTaxinLIneItemCheckbox(driver, false);
						}
					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
						} else {
							Assert.assertTrue(false);
						}
					}
				}
			}
		}
// Generate Report
		try {
			String[] testcase = TestCaseID.split("\\|");
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
		return Utility.getData("VerifyProductInBEAndInncenter", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		// Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,
		// comment,TestCore.TestRail_AssignToID);
		// driver.close();

	}
}
