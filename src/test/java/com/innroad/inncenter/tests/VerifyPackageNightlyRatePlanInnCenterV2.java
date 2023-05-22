package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPackageNightlyRatePlanInnCenterV2 extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
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
	ReservationV2 res = new ReservationV2();
	DerivedRate derivedRate = new DerivedRate();
	RatePackage ratePackage = new RatePackage();

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
	ArrayList<PackageProduct> products = null;
	ArrayList<String> reservationRoomClassesList = new ArrayList<String>();
	HashMap<String, String> capacityAdultChange = new HashMap<String, String>(),
			capacityChildChange = new HashMap<String, String>(), maxAdultChange = new HashMap<String, String>(),
			maxChildChange = new HashMap<String, String>();
	HashMap<String, String> capacityAdult = new HashMap<String, String>(),
			capacityChild = new HashMap<String, String>(), maxAdult = new HashMap<String, String>(),
			maxChild = new HashMap<String, String>();
	HashMap<String, String> getRatesChange = new HashMap<String, String>();
	HashMap<String, String> getExAdultChange = new HashMap<String, String>();
	HashMap<String, String> getExChildChange = new HashMap<String, String>();
	HashMap<String, String> getRatesPerNightChannelsChange = new HashMap<String, String>();
	List<Date> datesChnage = new ArrayList<Date>();
	List<Date> dates = new ArrayList<Date>();
	List dates1 = new ArrayList();
	ArrayList<String> allDatesBW = null;
	
	HashMap<String, String> getRates = new HashMap<String, String>();
	HashMap<String, String> getExAdult = new HashMap<String, String>();
	HashMap<String, String> getExChild = new HashMap<String, String>();

	private void getMapData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}

	@Test(dataProvider = "getData")
	public void verifyNightlyRatePlanReservationV2(String delim, String ReservationType, String CheckInDate,
			String CheckOutDate, String adult, String children, String ReservationRoomClasses,
			String packageRatePlanName, String productsName, String change_checkInDate, String change_checkOutDate,
			String changeOption, String changeRoomClass) throws Exception {
//change_checkInDate		

		Utility.DELIM = delim;

		test_name = "VerifyRateplanReservationV2_" + ReservationType + "_" + CheckInDate + "_" + CheckOutDate;
		test_description = "VerifyRateplanInInncenter";
		test_catagory = "VerifyRateplanInInncenter";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");
		
		int days = 0;
		String channelName = "innCenter";
		System.out.println("channelName : " + channelName);
		System.out.println(System.getProperty("user.dir"));
		HashMap<String, String> ratePlanData;

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
		String isAssignRulesByRoomClass = packageRateData.get("isAssignRulesByRoomClass");
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
			Login login = new Login();
			try {
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				login.login(driver, envURL, "autorates", "autouser", "Auto@123");
			}
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
		if (!Utility.insertTestName.containsKey(test_name)) {
			Utility.insertTestName.put(test_name, test_name);
			Utility.reTry.put(test_name, 0);
		} else {
			Utility.reTry.replace(test_name, 1);
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
		boolean israteplanExist = false;
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
						extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSeasonLevelRules,
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
		try {
			// rateGrid.clickRatesTab(driver, test_steps);
			reservationRoomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
			driver.navigate().refresh();
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
		int MRBDays = 0;
		try {
			test_steps.clear();
			test_name = "Get Rate Plan Restriction, Rules, Polices And Season Level Data";
			test_description = "Get Rate Plan Restriction, Rules, Polices And Season Level Data";

			test_steps.add("=================== Getting Rate Plan Restriction and Rules ======================");
			app_logs.info("=================== Getting Rate Plan Restriction and Rules ======================");
			String[] roomClass = ReservationRoomClasses.split("\\" + Utility.DELIM);
			if (ReservationType.equalsIgnoreCase("MRB")) {
				String[] MRBCheckIn = CheckInDate.split("\\" + Utility.DELIM);
				String[] MRBCheckOut = CheckOutDate.split("\\" + Utility.DELIM);
				// String[] roomClass = ReservationRoomClasses.split("\\" + Utility.DELIM);

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
						+ packageRatePlanName + "</b> ==========");
				bookingWindowRestrictions = rateGrid.getBookingWindowRestrictions(driver, test_steps,
						packageRatePlanName);
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
					promoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					promoCode = "";
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

				nightlyRate.switchProductsTab(driver, test_steps);

				products = ratePackage.getAllProducts(driver);

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
					rateGrid.selectRatePlan(driver, packageRatePlanName, test_steps);
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
						for (int i = 0; i < roomClass.length; i++) {
							deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("depositPolicyText"), roomClass[i], test_steps));
							checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("checkInPolicyText"), roomClass[i], test_steps));
						}
					} else {
						deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
								ratesConfig.getProperty("depositPolicyText"), roomClass[0], test_steps));
						checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
								ratesConfig.getProperty("checkInPolicyText"), roomClass[0], test_steps));
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

				nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
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
						+ packageRatePlanName + "</b> ==========");
				bookingWindowRestrictions = rateGrid.getBookingWindowRestrictions(driver, test_steps,
						packageRatePlanName);
				restricrionsLengthOfStay = reservationPage.verifylenthOfStayRestrictions(driver, test_steps,
						verifyLenthOfStayChecked, verifyMinStayCondidtion, verifyMaxStayCondition, addStayofLength,
						days);

				restricrionsBookingWindow = reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
						isBookinWindow, CheckInDate, CheckOutDate, bookingWindowRestrictions);
				isPromocode = rateGrid.isPromoCodeChecked(driver, test_steps);
				app_logs.info("after get booking window");

				if (isPromocode) {
					promoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					promoCode = "";
				}

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				}
				app_logs.info("after get rate level policies");

				nightlyRate.switchProductsTab(driver, test_steps);

				products = ratePackage.getAllProducts(driver);

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

						capacityAdult.put(roomClass[0], nightlyRate.getAdultCapacity(driver, roomClass[0]));
						capacityChild.put(roomClass[0], nightlyRate.getChildCapacity(driver, roomClass[0]));
						maxAdult.put(roomClass[0], nightlyRate.getMaxAdult(driver, roomClass[0]));
						maxChild.put(roomClass[0], nightlyRate.getMaxPersons(driver, roomClass[0]));
						test_steps.add("Room Class : " + roomClass[0] + " Adult Capacity: <b>"
								+ capacityAdult.get(roomClass[0]) + "</b>");
						test_steps.add("Room Class : " + roomClass[0] + " Person's Capacity: <b>"
								+ capacityChild.get(roomClass[0]) + "</b>");
						test_steps.add("Rate Plan Max. Adults: <b>" + maxAdult.get(roomClass[0]) + "</b>");
						test_steps.add("Rate Plan Max. Childs: <b>" + maxChild.get(roomClass[0]) + "</b>");

						getRoomClassWithRates.put(ReservationRoomClasses,
								rateGrid.getRoomClassRateWithAdditionalAdultAndChild(driver, ReservationRoomClasses,
										test_steps));
//						nightlyRate.closeSeason(driver, test_steps);

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

//					rateGrid.closeOpendTabInMainMenu(driver);
					if (isVerifyPolicies) {

//						driver.navigate().refresh();
//						rateGrid.selectRatePlan(driver, packageRatePlanName, test_steps);
//						rateGrid.clickOnEditRatePlan(driver);
//						nightlyRate.switchCalendarTab(driver, test_steps);
//						nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
//						nightlyRate.clickEditThisSeasonButton(driver, test_steps);

						nightlyRate.clickSeasonPolicies(driver, test_steps);
						getSessionLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);

						app_logs.info("getSessionLevelPolicy: " + getSessionLevelPolicy);
						test_steps.add("==================Get Policy from  Season==================");
						app_logs.info("==================Get Policy from  Season==================");

						ArrayList<String> deposit = new ArrayList<String>();
						ArrayList<String> checkin = new ArrayList<String>();

						if (CheckInDate.split("\\|").length > 1) {
							for (int i = 0; i < roomClass.length; i++) {
								deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
										ratesConfig.getProperty("depositPolicyText"), roomClass[i], test_steps));
								checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
										ratesConfig.getProperty("checkInPolicyText"), roomClass[i], test_steps));
							}
						} else {
							deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("depositPolicyText"), roomClass[0], test_steps));
							checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver,
									ratesConfig.getProperty("checkInPolicyText"), roomClass[0], test_steps));
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

					}
					nightlyRate.closeSeason(driver, test_steps);
					rateGrid.closeOpendTabInMainMenu(driver);
					if (!ReservationType.equalsIgnoreCase("MRB")) {

						navigation.ratesGrid(driver);

						HashMap<String, // RoomClass
								HashMap<String, // Source
										HashMap<String, // date
												RatesGridChannelRatesRules>>> roomClassWiseSourceDerivedRatesRule = rateGrid
														.getSourceWiseRatesRules(driver, test_steps,
																packageRatePlanName, packageRatePlanName, false, delim,
																ReservationRoomClasses, channelName, CheckInDate,
																CheckOutDate);

						allDatesBW = Utility.getAllDatesBetweenTwoDates(CheckInDate, CheckOutDate);

						for (String date : allDatesBW) {
							app_logs.info(date);
							app_logs.info("Found Room Rates : "
									+ roomClassWiseSourceDerivedRatesRule.get(roomClass[0]).get(channelName).get(date));
							test_steps.add("Found Room Rates : "
									+ roomClassWiseSourceDerivedRatesRule.get(roomClass[0]).get(channelName).get(date));
							RatesGridChannelRatesRules obj = roomClassWiseSourceDerivedRatesRule.get(roomClass[0])
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
					test_steps.add("No Season For Desired Date");
				}
//				rateGrid.clickOnSaveratePlan(driver);
//				// here put wait for checking element gone
//				rateGrid.verifyLoadingGone(driver);
//				rateGrid.closeOpendTabInMainMenu(driver);

			} // end here single reservation
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify season in single room reservation", test_name, "RatesV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify season in single room reservation", test_name, "RatesV2",
						driver);
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

			String ReservationRate = packageRatePlanName;
			ArrayList<String> roomClassList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
			if (ReservationType.contains("single")) {
//				
//				for (String roomclass : roomClassList) {
//
//					res.click_NewReservation(driver, test_steps);
//					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, ReservationRate, promoCode);	
//					res.clickOnFindRooms(driver, test_steps);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							CheckInDate, CheckOutDate, adult,
//							children, roomclass, interval,
//							baseRatePerNight_SeasonLevel.get(roomclass), 
//							roomClassCapacityAdult_SeasonLevel.get(roomclass),
//							roomclassCapacityChild_SeasonLevel.get(roomclass),
//							isAdditionalAdultChild,
//							ratePlanMaxAdults_SeasonLevel.get(roomclass),
//							ratePlanMaxChilds_SeasonLevel.get(roomclass),
//							
//							baseRateExAdult_SeasonLevel.get(roomclass),
//							baseRateExChild_SeasonLevel.get(roomclass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
//							proRatePerNight_SeasonLevel.get(roomclass),
//							proRateExAdult_SeasonLevel.get(roomclass),
//							proRateExChild_SeasonLevel.get(roomclass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int i = 0; i < rateListInReservation.size(); i++) {
//						app_logs.info(rateListInReservation.get(i));
//					}
//					res.closeAllOpenedReservations(driver);
//				}

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
				
				
//				app_logs.info(changePerNightRatesList_ratesGrid);
//				app_logs.info(changeRatePlanMaxAdults_SeasonLevel);
//				app_logs.info(changeRatePlanMaxChilds_SeasonLevel);
//				app_logs.info(changeExAdultRatesList_ratesGrid);
//				app_logs.info(changeExChildRatesList_ratesGrid);
//				app_logs.info(changeRoomclassCapacityChild_SeasonLevel);
//				app_logs.info(changeRoomClassCapacityAdult_SeasonLevel);
//				app_logs.info(adult);
//				app_logs.info(children);
//				
//				app_logs.info(changeCheckInList);
//				app_logs.info(changeCheckOutList);
//				
//				for (int i = 0; i < changeCheckInList.size(); i++) {
//
//					res.clickEditStayInfo(driver);
//					res.clickChangeStayDetails(driver);
//					res.waitForSweetLoading(driver);
//					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
//					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
//					res.selectStayInfoOption(driver, changeOption);
//					res.clickFindRoomsStayInfo(driver);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
//							children, changeRoomClass, interval,
//							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
//							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
//							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
//							isAdditionalAdultChild,
//							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
//							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
//							
//							baseRateExAdult_SeasonLevel.get(changeRoomClass),
//							baseRateExChild_SeasonLevel.get(changeRoomClass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
//							proRatePerNight_SeasonLevel.get(changeRoomClass),
//							proRateExAdult_SeasonLevel.get(changeRoomClass),
//							proRateExChild_SeasonLevel.get(changeRoomClass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int j = 0; j < rateListInReservation.size(); j++) {
//						app_logs.info(rateListInReservation.get(j));
//					}
//					res.clickCloseStayInfo(driver);
//				}
//				
//				HashMap<String, ReservationRatesDetail> obj1 = new HashMap<String, ReservationRatesDetail>();
//				ReservationRatesDetail ratesDetailChangeStay = res.getFullRatesDetail_ChangeStay(driver, test_steps,
//						changeRoomClass, change_checkInDate,
//						change_checkOutDate, changeOption);
//				obj1.put(changeRoomClass, ratesDetailChangeStay);
//				app_logs.info(obj1);
//				
//				res.verifyRates(obj1, changeRoomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
//						changeRoomClass, change_checkInDate, test_steps);
//				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("mrb")) {
				ReservationRate = packageRatePlanName + "|" + packageRatePlanName;
				guestFirstName = "John Wick|Son Wick";
				guestLastName = Utility.generateRandomNumberWithGivenNumberOfDigits(4) + "|"
						+ Utility.generateRandomNumberWithGivenNumberOfDigits(4);

				String[] adu = adult.split("\\|");
				String[] child = children.split("\\|");
				ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);
				ArrayList<String> checkOutList = Utility.convertTokenToArrayList(CheckOutDate, delim);
				HashMap<String, String> rateIs = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
				
//				for (int i = 0; i < roomClassList.size(); i++) {
////					rateIs.putAll(res.calculateRatesAsPerAdultsAndChildCapacity(perNightRatesList_ratesGrid.get(i),
////							ratePlanMaxAdults_SeasonLevel.get(roomClassList.get(i)), ratePlanMaxChilds_SeasonLevel.get(roomClassList.get(i)),
////							exAdultRatesList_ratesGrid.get(i), exChildRatesList_ratesGrid.get(i), roomclassCapacityChild_SeasonLevel.get(roomClassList.get(i)),
////							roomClassCapacityAdult_SeasonLevel.get(roomClassList.get(i)), adu[i], child[i],
////							Utility.getDateRangeBetweenfromAndToDate(
////									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
////											checkInList.get(i)),
////									Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
////											checkOutList.get(i))),
////							ratesConfig.getProperty("defaultDateFormat")));
////					roomClassWiseCalculatedRates.put(roomClassList.get(i), rateIs);
//					
//
//					String roomclass = roomClassList.get(i);
//					res.click_NewReservation(driver, test_steps);
//					res.searchDataForFindRooms(driver, test_steps, checkInList.get(i), checkOutList.get(i), adu[i], child[i], RatePlanName, PromoCode);	
//					res.clickOnFindRooms(driver, test_steps);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							checkInList.get(i), checkOutList.get(i), adu[i], child[i], roomclass, interval,
//							baseRatePerNight_SeasonLevel.get(roomclass), 
//							roomClassCapacityAdult_SeasonLevel.get(roomclass),
//							roomclassCapacityChild_SeasonLevel.get(roomclass),
//							isAdditionalAdultChild,
//							ratePlanMaxAdults_SeasonLevel.get(roomclass),
//							ratePlanMaxChilds_SeasonLevel.get(roomclass),
//							
//							baseRateExAdult_SeasonLevel.get(roomclass),
//							baseRateExChild_SeasonLevel.get(roomclass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
//							proRatePerNight_SeasonLevel.get(roomclass),
//							proRateExAdult_SeasonLevel.get(roomclass),
//							proRateExChild_SeasonLevel.get(roomclass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int j = 0; j < rateListInReservation.size(); j++) {
//						app_logs.info(rateListInReservation.get(j));
//					}
//					res.closeAllOpenedReservations(driver);
//				
//				}
//				HashMap<String, ReservationRatesDetail> obj = res.getFullRatesDetail_Creation(driver, test_steps,
//						ReservationRoomClasses, CheckInDate, CheckOutDate, adult, children, ReservationRate, "");
//				app_logs.info(obj);
//				Utility.app_logs.info(rateIs);
//
//				Utility.app_logs.info(ratesConfig.getProperty("defaultDateFormat"));
//				
//				res.verifyRates(obj, roomClassWiseCalculatedRates, ratesConfig.getProperty("defaultDateFormat"), "MMM dd yyyy", 
//						ReservationRoomClasses, CheckInDate, test_steps);
				
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
				
//				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
//				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
//				HashMap<String, String> changeRateIs = new HashMap<String, String>();
//				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				app_logs.info(changePerNightRatesList_ratesGrid);
//				app_logs.info(changeRatePlanMaxAdults_SeasonLevel);
//				app_logs.info(changeRatePlanMaxChilds_SeasonLevel);
//				app_logs.info(changeExAdultRatesList_ratesGrid);
//				app_logs.info(changeExChildRatesList_ratesGrid);
//				app_logs.info(changeRoomclassCapacityChild_SeasonLevel);
//				app_logs.info(changeRoomClassCapacityAdult_SeasonLevel);
//				app_logs.info(adu);
//				app_logs.info(child);
//				
//				app_logs.info(changeCheckInList);
//				app_logs.info(changeCheckOutList);
//				
//				for (int i = 0; i < changeCheckInList.size(); i++) {
//				
//					res.clickChangeStayDetails(driver, 2);
//					res.waitForSweetLoading(driver);
//					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
//					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
//					res.selectStayInfoOption(driver, changeOption);
//					res.clickFindRoomsStayInfo(driver);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
//							children, changeRoomClass, interval,
//							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
//							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
//							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
//							isAdditionalAdultChild,
//							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
//							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
//							
//							baseRateExAdult_SeasonLevel.get(changeRoomClass),
//							baseRateExChild_SeasonLevel.get(changeRoomClass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
//							proRatePerNight_SeasonLevel.get(changeRoomClass),
//							proRateExAdult_SeasonLevel.get(changeRoomClass),
//							proRateExChild_SeasonLevel.get(changeRoomClass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int j = 0; j < rateListInReservation.size(); j++) {
//						app_logs.info(rateListInReservation.get(j));
//					}
//					res.clickCloseStayInfo(driver);
//				
//				}
//				
				
				res.modifyReservationDatesForMRB(driver, test_steps, change_checkInDate, change_checkOutDate,
						changeOption, 2,changeRoomClass);

			} else if (ReservationType.contains("Quote")) {
//				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				for (String roomclass : roomClassList) {
//
//					res.click_NewReservation(driver, test_steps);
//					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
//					res.clickOnFindRooms(driver, test_steps);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							CheckInDate, CheckOutDate, adult,
//							children, roomclass, interval,
//							baseRatePerNight_SeasonLevel.get(roomclass), 
//							roomClassCapacityAdult_SeasonLevel.get(roomclass),
//							roomclassCapacityChild_SeasonLevel.get(roomclass),
//							isAdditionalAdultChild,
//							ratePlanMaxAdults_SeasonLevel.get(roomclass),
//							ratePlanMaxChilds_SeasonLevel.get(roomclass),
//							
//							baseRateExAdult_SeasonLevel.get(roomclass),
//							baseRateExChild_SeasonLevel.get(roomclass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
//							proRatePerNight_SeasonLevel.get(roomclass),
//							proRateExAdult_SeasonLevel.get(roomclass),
//							proRateExChild_SeasonLevel.get(roomclass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int i = 0; i < rateListInReservation.size(); i++) {
//						app_logs.info(rateListInReservation.get(i));
//					}
//					res.closeAllOpenedReservations(driver);
//				}
//				
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
//				
//				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
//				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
//				HashMap<String, String> changeRateIs = new HashMap<String, String>();
//				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				
//				
//				for (int i = 0; i < changeCheckInList.size(); i++) {
//
//					res.clickEditStayInfo(driver);
//					res.clickChangeStayDetails(driver);
//					res.waitForSweetLoading(driver);
//					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
//					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
//					res.selectStayInfoOption(driver, changeOption);
//					res.clickFindRoomsStayInfo(driver);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
//							children, changeRoomClass, interval,
//							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
//							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
//							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
//							isAdditionalAdultChild,
//							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
//							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
//							
//							baseRateExAdult_SeasonLevel.get(changeRoomClass),
//							baseRateExChild_SeasonLevel.get(changeRoomClass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
//							proRatePerNight_SeasonLevel.get(changeRoomClass),
//							proRateExAdult_SeasonLevel.get(changeRoomClass),
//							proRateExChild_SeasonLevel.get(changeRoomClass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int j = 0; j < rateListInReservation.size(); j++) {
//						app_logs.info(rateListInReservation.get(j));
//					}
//					res.clickCloseStayInfo(driver);
//				}
//				
//			
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("TapeChart")) {
//				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				for (String roomclass : roomClassList) {
//
//					res.click_NewReservation(driver, test_steps);
//					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
//					res.clickOnFindRooms(driver, test_steps);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							CheckInDate, CheckOutDate, adult,
//							children, roomclass, interval,
//							baseRatePerNight_SeasonLevel.get(roomclass), 
//							roomClassCapacityAdult_SeasonLevel.get(roomclass),
//							roomclassCapacityChild_SeasonLevel.get(roomclass),
//							isAdditionalAdultChild,
//							ratePlanMaxAdults_SeasonLevel.get(roomclass),
//							ratePlanMaxChilds_SeasonLevel.get(roomclass),
//							
//							baseRateExAdult_SeasonLevel.get(roomclass),
//							baseRateExChild_SeasonLevel.get(roomclass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
//							proRatePerNight_SeasonLevel.get(roomclass),
//							proRateExAdult_SeasonLevel.get(roomclass),
//							proRateExChild_SeasonLevel.get(roomclass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int i = 0; i < rateListInReservation.size(); i++) {
//						app_logs.info(rateListInReservation.get(i));
//					}
//					res.closeAllOpenedReservations(driver);
//				}
//				
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
				
//				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
//				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
//				HashMap<String, String> changeRateIs = new HashMap<String, String>();
//				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				
//				
//				for (int i = 0; i < changeCheckInList.size(); i++) {
//
//					res.clickEditStayInfo(driver);
//					res.clickChangeStayDetails(driver);
//					res.waitForSweetLoading(driver);
//					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
//					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
//					res.selectStayInfoOption(driver, changeOption);
//					res.clickFindRoomsStayInfo(driver);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
//							children, changeRoomClass, interval,
//							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
//							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
//							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
//							isAdditionalAdultChild,
//							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
//							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
//							
//							baseRateExAdult_SeasonLevel.get(changeRoomClass),
//							baseRateExChild_SeasonLevel.get(changeRoomClass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
//							proRatePerNight_SeasonLevel.get(changeRoomClass),
//							proRateExAdult_SeasonLevel.get(changeRoomClass),
//							proRateExChild_SeasonLevel.get(changeRoomClass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int j = 0; j < rateListInReservation.size(); j++) {
//						app_logs.info(rateListInReservation.get(j));
//					}
//					res.clickCloseStayInfo(driver);
//				}
//				
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("Group")) {
//				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				for (String roomclass : roomClassList) {
//
//					res.click_NewReservation(driver, test_steps);
//					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
//					res.clickOnFindRooms(driver, test_steps);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							CheckInDate, CheckOutDate, adult,
//							children, roomclass, interval,
//							baseRatePerNight_SeasonLevel.get(roomclass), 
//							roomClassCapacityAdult_SeasonLevel.get(roomclass),
//							roomclassCapacityChild_SeasonLevel.get(roomclass),
//							isAdditionalAdultChild,
//							ratePlanMaxAdults_SeasonLevel.get(roomclass),
//							ratePlanMaxChilds_SeasonLevel.get(roomclass),
//							
//							baseRateExAdult_SeasonLevel.get(roomclass),
//							baseRateExChild_SeasonLevel.get(roomclass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
//							proRatePerNight_SeasonLevel.get(roomclass),
//							proRateExAdult_SeasonLevel.get(roomclass),
//							proRateExChild_SeasonLevel.get(roomclass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int i = 0; i < rateListInReservation.size(); i++) {
//						app_logs.info(rateListInReservation.get(i));
//					}
//					res.closeAllOpenedReservations(driver);
//				}
				
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
				
//				
//				for (int i = 0; i < changeCheckInList.size(); i++) {
//
//					res.clickEditStayInfo(driver);
//					res.clickChangeStayDetails(driver);
//					res.waitForSweetLoading(driver);
//					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
//					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
//					res.selectStayInfoOption(driver, changeOption);
//					res.clickFindRoomsStayInfo(driver);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
//							children, changeRoomClass, interval,
//							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
//							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
//							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
//							isAdditionalAdultChild,
//							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
//							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
//							
//							baseRateExAdult_SeasonLevel.get(changeRoomClass),
//							baseRateExChild_SeasonLevel.get(changeRoomClass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
//							proRatePerNight_SeasonLevel.get(changeRoomClass),
//							proRateExAdult_SeasonLevel.get(changeRoomClass),
//							proRateExChild_SeasonLevel.get(changeRoomClass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int j = 0; j < rateListInReservation.size(); j++) {
//						app_logs.info(rateListInReservation.get(j));
//					}
//					res.clickCloseStayInfo(driver);
//				}
				
				res.modifyReservationDates(driver, change_checkInDate, change_checkOutDate,
						changeOption, changeRoomClass, test_steps);
				
			} else if (ReservationType.contains("Account Reservation")) {
				
//				HashMap<String, HashMap<String, String>> roomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				for (String roomclass : roomClassList) {
//
//					res.click_NewReservation(driver, test_steps);
//					res.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);	
//					res.clickOnFindRooms(driver, test_steps);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							CheckInDate, CheckOutDate, adult,
//							children, roomclass, interval,
//							baseRatePerNight_SeasonLevel.get(roomclass), 
//							roomClassCapacityAdult_SeasonLevel.get(roomclass),
//							roomclassCapacityChild_SeasonLevel.get(roomclass),
//							isAdditionalAdultChild,
//							ratePlanMaxAdults_SeasonLevel.get(roomclass),
//							ratePlanMaxChilds_SeasonLevel.get(roomclass),
//							
//							baseRateExAdult_SeasonLevel.get(roomclass),
//							baseRateExChild_SeasonLevel.get(roomclass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(roomclass),
//							proRatePerNight_SeasonLevel.get(roomclass),
//							proRateExAdult_SeasonLevel.get(roomclass),
//							proRateExChild_SeasonLevel.get(roomclass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int i = 0; i < rateListInReservation.size(); i++) {
//						app_logs.info(rateListInReservation.get(i));
//					}
//					res.closeAllOpenedReservations(driver);
//				}
//				
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
//				
//				ArrayList<String> changeCheckInList = Utility.convertTokenToArrayList(change_checkInDate, delim);
//				ArrayList<String> changeCheckOutList = Utility.convertTokenToArrayList(change_checkOutDate, delim);
//				HashMap<String, String> changeRateIs = new HashMap<String, String>();
//				HashMap<String, HashMap<String, String>> changeRoomClassWiseCalculatedRates = new HashMap<String, HashMap<String, String>>();
//				
//				
//				for (int i = 0; i < changeCheckInList.size(); i++) {
//
//					res.clickEditStayInfo(driver);
//					res.clickChangeStayDetails(driver);
//					res.waitForSweetLoading(driver);
//					res.selectCheckInDate(driver, test_steps, changeCheckInList.get(i));
//					res.selectCheckOutDate(driver, test_steps, changeCheckOutList.get(i));
//					res.selectStayInfoOption(driver, changeOption);
//					res.clickFindRoomsStayInfo(driver);
//					
//					
//					ArrayList<ArrayList<String>> rateListInReservation =res.intervalRateVerification(driver,
//							ratesConfig.getProperty("defaultDateFormat"),
//							changeCheckInList.get(i), changeCheckOutList.get(i), adult,
//							children, changeRoomClass, interval,
//							baseRatePerNight_SeasonLevel.get(changeRoomClass), 
//							changeRoomClassCapacityAdult_SeasonLevel.get(changeRoomClass),
//							changeRoomclassCapacityChild_SeasonLevel.get(changeRoomClass),
//							isAdditionalAdultChild,
//							changeRatePlanMaxAdults_SeasonLevel.get(changeRoomClass),
//							changeRatePlanMaxChilds_SeasonLevel.get(changeRoomClass),
//							
//							baseRateExAdult_SeasonLevel.get(changeRoomClass),
//							baseRateExChild_SeasonLevel.get(changeRoomClass), 
//							
//							isRoomClassProRateToggle_SeasonLevel.get(changeRoomClass),
//							proRatePerNight_SeasonLevel.get(changeRoomClass),
//							proRateExAdult_SeasonLevel.get(changeRoomClass),
//							proRateExChild_SeasonLevel.get(changeRoomClass),
//							FolioDisplayName
//							, test_steps);
//
//					for (int j = 0; j < rateListInReservation.size(); j++) {
//						app_logs.info(rateListInReservation.get(j));
//					}
//					res.clickCloseStayInfo(driver);
//				}
//				
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
		return Utility.getData("VerifyPackageRatePlanResV2", envLoginExcel);
	}

}
