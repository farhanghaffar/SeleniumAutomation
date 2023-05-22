package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPromoCodeInNewBookingEngine extends TestCore {


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

	private ArrayList<String> getDataOfHash(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		ArrayList<String> values = new ArrayList<String>();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			app_logs.info("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
			values.add(mentry.getValue().toString());

		}
		return values;
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyPromoCodeInNewBookingEngine(String delim, String testCaseDescription,
			String checkInDate, String checkOutDate,String togglePromoCode, String PromoCodeType,String ratePlanPromoCode ,String ratePlanName,String RoomClass,String calculationMethodOne, String calculationMethodTwo,
			String updatedRistrictionType, String updatedIsMinStay, String updatedIsMaxStay,
			String updatedIsMoreThanDaysReq, String updatedIsWithInDaysReq,String cases) throws Exception {

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"CreateRatePlan_CondProduct");

		HashMap<String, String> staticData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"VerifyCondProduct_StaticData");

		String bookingEngineChannel = staticData.get("ChannelName");
		
		String ratePlanType = staticData.get("ratePlanType");
		String adults = staticData.get("Adults");
		String child = staticData.get("Child");
		String reservationRoomClassName = staticData.get("ReservationRoomClass");
		String marketSegment = staticData.get("MargetSegment");
		String referral = staticData.get("Referral");
		String phone = staticData.get("Phonenumber");
		String address = staticData.get("Address1");
		String city = staticData.get("city");
		String state = staticData.get("State");
		String postalCode = staticData.get("Postalcode");
		String firstName = staticData.get("ReservationFirstName");
		String lastName = staticData.get("ReservationLastName");
		String paymentType = staticData.get("paymentType");
		String cardNumber = staticData.get("cardNumber");
		String cardName = staticData.get("cardName");
		String cardExpDate = staticData.get("cardExpDate");
		String emailAddress = staticData.get("emailID");
		String cvv = staticData.get("cvv");
		String reservationStatus = staticData.get("reservationStatus");
		String entityName = staticData.get("entityName");
		String productName = staticData.get("productsName");
		String isSellOnBookingEngine = staticData.get("isSellOnBookingEngine");
		String bookingEngineAvailabilityOption = staticData.get("bookingEngineAvailabilityOption");
		String productDescription = staticData.get("productDescritpion");
		String productCategory = staticData.get("productCategory");
		String productPolicy = staticData.get("productPolicy");
		String productAmount = staticData.get("productAmount");
		String productQuantity = staticData.get("ProductQuantity");
		String updatedMinNights = staticData.get("MinNights");
		String updatedMaxNights = staticData.get("MaxNights");
		String updatedMoreThanDaysCount = staticData.get("MoreThanDaysCount");
		String updatedWithInDaysCount = staticData.get("WithInDaysCount");
		String updatedPromoCode = staticData.get("PromoCode");
		String updatedSeasonIsAssignRulesByRoomClass = staticData.get("isAssignRulesByRoomClass");
		String updatedSeasonRuleType = staticData.get("SeasonRuleType");
		String updatedSeasonRuleMinStayValue = staticData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnDays = staticData.get("isSeasonRuleOnDays");

		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		app_logs.info("days: " + days);

		String addAndVerifyProduct = "FALSE";
		String updateRules = "yes";
		String updatedIsRatePlanRistrictionReq = "TRUE";
		String[] splitSeasonLevelDays = isSeasonRuleOnDays.split(Utility.DELIM);
		String updatedSeasonIsSeasonRuleOnMonday = splitSeasonLevelDays[0];
		String updatedSeasonIsSeasonRuleOnTuesday = splitSeasonLevelDays[1];
		String updatedSeasonIsSeasonRuleOnWednesday = splitSeasonLevelDays[2];
		String updatedSeasonIsSeasonRuleOnThursday = splitSeasonLevelDays[3];
		String updatedSeasonIsSeasonRuleOnFriday = splitSeasonLevelDays[4];
		String updatedSeasonIsSeasonRuleOnSaturday = splitSeasonLevelDays[5];
		String updatedSeasonIsSeasonRuleOnSunday = splitSeasonLevelDays[6];
		String updatedSeasonIsSeasonLevelRules = "No";



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
		String testName = testCaseDescription;
		test_description = testCaseDescription;
		test_catagory = "NBE_PromoCodeValidations";
		scriptName.clear();
		scriptName.add(testName + "_" + testCaseDescription);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		CPReservationPage reservation = new CPReservationPage();
		Distribution distribution = new Distribution();
		NightlyRate nightlyRate = new NightlyRate();
		Admin admin = new Admin();
		Properties properties = new Properties();

		BookingEngine bookingEngine = new BookingEngine();

		String randomNumber = Utility.GenerateRandomNumber();
		lastName = lastName + randomNumber;

		String inncenterHeaderTripTotal = "";
		String dateFormat = Utility.dayMonthYear_DateFormat;
		String seasonDateFormat = Utility.dayMonthYear_DateFormat;
		
		boolean israteplanExist = false;
		
		boolean isPromocode = true;
		boolean ratePlanAvailableInBE = true;	
		String subTotalBE = "";

		ArrayList<String> productWithDetails = new ArrayList<>();

		int seasonDays = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate);
		int numberOfProperties = 0;
		String timeZone = "";
		String propertyName = "";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 1);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			String[] casesList = cases.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("4");
			}
			login_Group(driver);
			checkInDate=Utility.getCurrentDate("dd/MM/yyyy");
			checkOutDate=Utility.GetNextDate(1, "dd/MM/yyyy");
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

		// Clear all associated rules
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
							updatedSeasonIsAssignRulesByRoomClass, reservationRoomClassName, updatedSeasonRuleType,
							updatedSeasonRuleMinStayValue, updatedSeasonIsSeasonRuleOnMonday,
							updatedSeasonIsSeasonRuleOnTuesday, updatedSeasonIsSeasonRuleOnWednesday,
							updatedSeasonIsSeasonRuleOnThursday, updatedSeasonIsSeasonRuleOnFriday,
							updatedSeasonIsSeasonRuleOnSaturday, updatedSeasonIsSeasonRuleOnSunday);
					nightlyRate.clickSaveSason(driver, testSteps);
					try {
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					} catch (Exception e) {
						nightlyRate.closeSeason(driver, productWithDetails);
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

			/*ratesGrid.clickRatePlanArrow(driver, testSteps);
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

			isPromocode = ratesGrid.isPromoCodeChecked(driver, getTestSteps);

			if (isPromocode) {
				testSteps.add("Verified Promo Code restriction is  : " + isPromocode);
				ratePlanPromoCode = ratesGrid.getPromoCode(driver, testSteps);
				Utility.app_logs.info("Selected Promo Code : " + ratePlanPromoCode);
				testSteps.add("Selected Promo Code : " + ratePlanPromoCode);
			} else {
				ratePlanPromoCode = "";
			}*/


		
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
			if(togglePromoCode.equalsIgnoreCase("Off")&&PromoCodeType.isEmpty()) {
				bookingEngine.togglePromoCodeToggleButton(driver, togglePromoCode);
				bookingEngine.savePromoCodeToggle(driver);
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				bookingEngine.verifyPromoCodeFieldIsNotAvailable(driver);
			}else if(togglePromoCode.equalsIgnoreCase("On")&&(PromoCodeType.equalsIgnoreCase("Invalid"))) {

				bookingEngine.togglePromoCodeToggleButton(driver, togglePromoCode);
				bookingEngine.savePromoCodeToggle(driver);
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
				app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

				getTestSteps.clear();
				getTestSteps =bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate, false);
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
			
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterPromoCode(driver, "VDEX");
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);
				
				bookingEngine.verifyRoomsNotAvailableWithInvalidPromoCode(driver);
				
				
			}
			else if(togglePromoCode.equalsIgnoreCase("On")&&(PromoCodeType.equalsIgnoreCase("Valid"))){
			testSteps.add("=================== CREATE RESERVATION FROM BOOKING ENGINE ======================");
			app_logs.info("=================== CREATE RESERVATION FROM BOOKING ENGINE ======================");
			bookingEngine.togglePromoCodeToggleButton(driver, togglePromoCode);
			bookingEngine.savePromoCodeToggle(driver);
			bookingEngine.clickWelcomePageLink(driver);
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");
			testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
			app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

			getTestSteps.clear();
			getTestSteps =bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate, false);
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
			
				getTestSteps.clear();
				getTestSteps = bookingEngine.selectRoomClass(driver, RoomClass);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
				testSteps.addAll(getTestSteps);
				
				String PromoCode=bookingEngine.getPromoCode(driver);
				Assert.assertEquals(PromoCode, ratePlanPromoCode, "Failed to verify the Promo Code");
				app_logs.info("Sucessfully verified Promo Code in NBE reservation details page");
				testSteps.add("Sucessfully verified Promo Code in NBE reservation details page");


				testSteps.add("=================== VERIFY RATE PLAN NIGHTY STAY AMOUNT======================");
				app_logs.info("=================== VERIFY RATE PLAN NIGHTY STAY AMOUNT======================");
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickBookRoom(driver, ratePlanName);
				testSteps.addAll(getTestSteps);
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

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickBookStay(driver);
				testSteps.addAll(getTestSteps);
				reservationNumber = bookingEngine.getReservationNumber(driver);
				comment = "Created rservation with " + reservationNumber
						+ " and verified reservation folio , trip summary , payments and history after reservation creation";

				testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
				app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);

			}
				
			
		}  catch (Exception e) {
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
				if(togglePromoCode.equalsIgnoreCase("On")&&PromoCodeType.equalsIgnoreCase("Valid")) {
				
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
					String guestName = firstName + " " + lastName;

					testSteps.add("Search Reservation : " + reservationNumber);
					app_logs.info("Search Reservation : " + reservationNumber);
					testSteps.add("Open Reservation ");
					app_logs.info("Open Reservation ");

					testSteps.add("=================== BANNER FIELDS VERIFICATION ======================");
					app_logs.info("=================== BANNER FIELDS VERIFICATION ======================");
					getTestSteps.clear();
					getTestSteps = reservation.verifyGuestNameAfterReservation(driver, guestName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservation.verifyStatusAfterReservation(driver, "Reserved");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservation.verifyStayDateAfterReservation(driver,
							Utility.parseDate(checkInDate, dateFormat, "MMM d"),
							Utility.parseDate(checkOutDate, dateFormat, "MMM d"));
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservation.verifyPhoneNoBanner(driver, "1" + phone);
					testSteps.addAll(getTestSteps);

					inncenterHeaderTripTotal = reservation.getTripTotal_Header(driver);
					testSteps.add("Inncenter Trip Total : " + inncenterHeaderTripTotal);
					app_logs.info("Inncenter Trip Total : " + inncenterHeaderTripTotal);
					assertEquals(inncenterHeaderTripTotal, subTotalBE, "Failed Trip Total Mismateched");
					testSteps.add("Successfully Verified Trip Total : " + inncenterHeaderTripTotal);
					app_logs.info("Successfully Verified Trip Total : " + inncenterHeaderTripTotal);

					reservation.Verify_ReservationStatus_Status(driver, testSteps, reservationStatus);

					testSteps.add("=================== STAY INFO FIELDS VERIFICATION=================== ");
					app_logs.info("=================== STAY INFO FIELDS VERIFICATION=================== ");

					String foundArrivalDate = reservation.getArrivalDate_ResDetail(driver);

					testSteps.add("Found Arrival Date : " + foundArrivalDate);
					app_logs.info("Found Arrival Date : " + foundArrivalDate);
					testSteps.add(
							"Expected Arrival Date : " + Utility.parseDate(checkInDate, dateFormat, "MMM d, yyyy"));
					app_logs.info(
							"Expected Arrival Date : " + Utility.parseDate(checkInDate, dateFormat, "MMM d, yyyy"));

					try {
						assertEquals(foundArrivalDate, Utility.parseDate(checkInDate, dateFormat, "MMM d, yyyy"),
								"Failed Arrival Date Missmatched");
					} catch (Exception e) {
						assertEquals(foundArrivalDate, Utility.parseDate(checkInDate, dateFormat, "d MMM, yyyy"),
								"Failed Arrival Date Missmatched");
					}
					testSteps.add("Successfully Verified Arrival Date : " + foundArrivalDate);
					app_logs.info("Successfully Verified Arrival Date : " + foundArrivalDate);

					String foundDepartDate = reservation.getDepartDate_ResDetail(driver);
					testSteps.add("Found Depart Date : " + foundDepartDate);
					app_logs.info("Found Depart Date : " + foundDepartDate);
					testSteps.add(
							"Expected Depart Date : " + Utility.parseDate(checkOutDate, dateFormat, "MMM d, yyyy"));
					app_logs.info(
							"Expected Depart Date : " + Utility.parseDate(checkOutDate, dateFormat, "MMM d, yyyy"));
					try {
						assertEquals(foundDepartDate, Utility.parseDate(checkOutDate, dateFormat, "MMM d, yyyy"),
								"Failed Depart Date Missmatched");
					} catch (Exception e) {
						assertEquals(foundDepartDate, Utility.parseDate(checkOutDate, dateFormat, "d MMM, yyyy"),
								"Failed Depart Date Missmatched");
					}
					testSteps.add("Successfully Verified Depart Date : " + foundDepartDate);
					app_logs.info("Successfully Verified Depart Date : " + foundDepartDate);

					String foundAdultsCount = reservation.getNoOfAdults_ResDetail(driver);
					assertEquals(foundAdultsCount, adults, "Failed No Of Adults Missmatched");
					testSteps.add("Successfully Verified No Of Adults : " + foundAdultsCount);
					app_logs.info("Successfully Verified No Of Adults : " + foundAdultsCount);

					String foundNoOfChild = reservation.getNoOfChilds_ResDetail(driver);
					assertEquals(foundNoOfChild, child, "Failed No Of Childs Missmatched");
					testSteps.add("Successfully Verified No Of Childs : " + foundNoOfChild);
					app_logs.info("Successfully Verified No Of Childs : " + foundNoOfChild);

					String foundRoomClass = reservation.getRoomClass_ResDetail(driver);
					assertEquals(foundRoomClass, RoomClass, "Failed Room Class Missmatched");
					testSteps.add("Successfully Verified Room Class : " + foundRoomClass);
					app_logs.info("Successfully Verified Room Class : " + foundRoomClass);

					testSteps.add("=================== VERIFY RATE PLAN ======================");
					app_logs.info("=================== VERIFY RATE PLAN ======================");
					String foundRate = reservation.get_RatePlan(driver, testSteps);
					if (isPromocode) {
						assertTrue(foundRate.equalsIgnoreCase("PROMO CODE"),
								"Failed RatePlan Missmatched Expected(PROMO CODE) but found(" + foundRate + ")");
						testSteps.add("Verified Rate Plan in InnCenter:" + foundRate);
						app_logs.info("Verified Rate Plan in InnCenter:" + foundRate);
						String PromoCode =reservation.get_PromoCode(driver, testSteps);
						Assert.assertEquals(PromoCode, ratePlanPromoCode, "Failed to verify Promo Code in CP Stay Details");
						testSteps.add("Verified Promo Code in InnCenter:" + ratePlanPromoCode);
						app_logs.info("Verified Promo Code in InnCenter:" + ratePlanPromoCode);
					} else {
						assertEquals(foundRate, ratePlanName, "Failed RatePlan Missmatched");
					}

					reservation.close_FirstOpenedReservation(driver, testSteps);
					navigation.Inventory_Backward_3(driver);
					navigation.ratesGrid(driver);
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
					ratesGrid.uncheckPromoCode(driver, testSteps);
					ratesGrid.clickOnSaveratePlan(driver);

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


		

		// Generate Report
		try {
			String[] testcase = cases.split(",");
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
			//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comment, TestCore.TestRail_AssignToID);
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
		return Utility.getData("VerifyPromoCodeInNbe", BEExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
	//	driver.quit();
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comment,TestCore.TestRail_AssignToID);

	}
}
