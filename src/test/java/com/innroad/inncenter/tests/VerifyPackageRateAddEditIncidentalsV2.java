/*
 * Created By Riddhi
 * 907827|907994|908119|907852
 */
package com.innroad.inncenter.tests;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elasticsearch.cluster.ClusterState.Custom;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.ReservationV2Search;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_ReservationV2;

public class VerifyPackageRateAddEditIncidentalsV2	extends TestCore
{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	Folio oldFolioObj = new Folio();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account accObj = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 resV2 = new ReservationV2();
	ReservationV2Search resSearch = new ReservationV2Search();
	Properties properties = new Properties();
	RoomClass roomClass = new RoomClass();
	Reports report = new Reports();
	Rate rate1 = new Rate();
	Policies policies = new Policies();
	MerchantServices msObj = new MerchantServices();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	CPReservationPage resV1 = new CPReservationPage();
	DerivedRate derivedRate = new DerivedRate();
	RatePackage ratePackage = new RatePackage();
	FolioNew folioObj = new FolioNew();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	ArrayList<String> sessionEndDate = new ArrayList<>();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbbs = new ArrayList<>();
	ArrayList<String> roomClassfinalRoomNos= new ArrayList<String>();
	ArrayList<String> productNames = new ArrayList<String>();
	ArrayList<String> calculationMethodsOne = new ArrayList<String>();
	ArrayList<String> calculationMethodsTwo = new ArrayList<String>();
	String checkInDate="", checkOutDate = "", guestFirstName="", guestLastName="", seasonStartDate="",seasonEndDate="",policyDesc ="";
	String  propertyName = null, productName = null,rateFolioDisplayName ="",
			calculatedAmountofProductForThisReservation1 = "", calculatedAmountofProductForThisReservation2 = "", 
			roomClassNameWithoutNum = null, roomClassAvvWithoutNum = null, rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservationNo = null, balance = null,policyname=null,policyNameWithoutNum=null,
			tripTaxes = null, tripTotal = null, depositAmount = null, date=null,expPolicyFee="", roomChargeAmount="", gcNumber =null;
	String source = "innCenter";
	HashMap<String, String> producstPriceMap = new HashMap<>();
	HashMap<String, String> producstCalculationMethodMap = new HashMap<>();
	HashMap<String, String> producstImage = new HashMap<>();
	HashMap<String, String> producstCategories = new HashMap<>();
	String productWithDetails = null; //image + Utility.DELIM + productName + Utility.DELIM + category + Utility.DELIM
										// + calculationMethod + Utility.DELIM + productPrice
	String productFirstCalculationMethod = null;
	String productSecondCalculationMethod = null;
	
	//@BeforeTest(alwaysRun = true)
	public void checkRunMode() 
	{
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if(!Utility.isExecutable(test_name, excelRiddhi)) throw new
			SkipException("Skiping the test - " + test_name);
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyPackageRateRV2", excelRiddhi);
	}
	
	private void getMapData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}
	private void handelingCatchException(Exception e, String desc, String category, String module) {
		if (Utility.reTry.get(test_name) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	private void handelingCatchError(Error e, String desc, String category, String module) {
		if (Utility.reTry.get(test_name) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(dataProvider = "getData")
	public void verifyPackageRateAddEditIncidentalsV2(String TestCaseId, String TestCaseName, String ratePlanName,
			String roomClassName, String roomClassAbb, String maxAdults, String maxPersons, String roomQuantity,
			String category, String perUnit, String addOn, String addOnPerUnit,
			String paymentMethod, String invalidCCNo, String nameOnCard, String expiryDate) throws Exception
	{
		Utility.initializeTestCase(TestCaseId, caseId, statusCode, comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";
		// String test_name = test_name;
		String productDetailsDELIM = Utility.DELIM + Utility.DELIM;
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		roomClassNames = Utility.splitInputData(roomClassName);
		roomClassAbbs = Utility.splitInputData(roomClassAbb);
		
		ArrayList<String> ratePlanNames = Utility.splitInputData(ratePlanName);
		//roomClassesNames = Utility.splitInputData(roomClassName);
		//rooms= new ArrayList<String> ();
		try {
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			if (guestFirstName.split("\\|").length > 1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			} else {
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
			}
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(excelRiddhi, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			propertyName = properties.getProperty(driver, test_steps);			
		} catch (Exception e) {
			handelingCatchException(e, "Failed to login", test_name, "Login");	}		

		 catch (Error e) {
				handelingCatchError(e, "Failed to login", test_name, "Login");	}
	
		String channelName = "innCenter";
		System.out.println("channelName : " + channelName);
		System.out.println(System.getProperty("user.dir"));
		HashMap<String, String> ratePlanData = null;
		HashMap<String, String> roomClassData = null;
		HashMap<String, String> packageData = null;
		try 
		{
			packageData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateProductV2");			
			roomClassData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNewRoomClass");
			ratePlanData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreatePackageRatePlanV2");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		//get product data from excel
		String productsName = packageData.get("productsName");
		String productsCategory = packageData.get("productCategory");
		String productsCost = packageData.get("productsCost");
		String calculationMethodOne = packageData.get("calculationMethodOne");
		String calculationMethodTwo = packageData.get("calculationMethodTwo");
		String isSellOnBookingEngine = packageData.get("isSellOnBookingEngine");
		String bookingEngineAvailabilityOption = packageData.get("bookingEngineAvailabilityOption");
		String productsRoomClass = packageData.get("productsRoomClass");
		String productsDescription = packageData.get("productDescription");
		String productsPolicy = packageData.get("productPolicy");
		
		productNames = Utility.splitInputData(productsName);
		calculationMethodsOne  = Utility.splitInputData(calculationMethodOne);
		calculationMethodsTwo  = Utility.splitInputData(calculationMethodTwo);
		ratePlanNames = Utility.splitInputData(ratePlanName);
		/*
		 * //Room Class Data String roomClass = roomClassData.get("roomClassName");
		 * String roomClassAB = roomClassData.get("roomClassAB"); String policy =
		 * roomClassData.get("policy"); String sortOrder =
		 * roomClassData.get("sortOrder"); String maxAdult =
		 * roomClassData.get("maxAdults"); String maxPerson =
		 * roomClassData.get("maxPerson"); String details =
		 * roomClassData.get("details"); String roomQuant =
		 * roomClassData.get("roomQuant"); String roomName =
		 * roomClassData.get("roomName"); String roomSortNo =
		 * roomClassData.get("roomSortNo"); String statinId =
		 * roomClassData.get("statinId"); String zone = roomClassData.get("zone");
		 */
		
		//Package Rate Plan Data
		String FolioDisplayName = ratePlanData.get("FolioDisplayName");
		String Description = ratePlanData.get("Description");
		String Channels = ratePlanData.get("Channels");
		String RoomClasses = roomClassName;
		String isRatePlanRestrictionReq = ratePlanData.get("roomClassName");
		String RestrictionType = ratePlanData.get("RestrictionType");
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
		String SeasonStartDate = Utility.getCurrentDate("dd/MM/yyyy");// ratePlanData.get("SeasonStartDate");
		String SeasonEndDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 60);// ratePlanData.get("SeasonEndDate");
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
		String MaxPerson = ratePlanData.get("MaxPerson");
		String AdditionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSeasonLevelRules = ratePlanData.get("isSeasonLevelRules");
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
		String MaxPersons = ratePlanData.get("MaxPersons");
		// Create Room Class
		/*try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			navigation.clickSetup(driver);
			navigation.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			app_logs.info("Navigated to Room Class");		
		
			for (int i = 0; i < roomClassNames.size(); i++) {
				if( !newRoomClass.searchRoomClassV2(driver, roomClassNames.get(i)) )
				{
					newRoomClass.createRoomClassV2(driver, test_steps, roomClassNames.get(i), roomClassAbbs.get(i), maxAdults,
						maxPersons, roomQuantity);
						newRoomClass.closeRoomClassTabV2(driver, roomClassNames.get(i));
					roomClassesNames.add(roomClassNames.get(i));
					roomClassfinalRoomNos.addAll(rooms);
					test_steps.add("Room Class Created:" + roomClassNames.get(i) + " Abbreviation : " + roomClassAbbs.get(i));
					app_logs.info("Room Class Created: " + roomClassNames.get(i) + " Abbreviation : " + roomClassAbbs.get(i));
				}
				else
				{
					app_logs.info("Room Class Exists: " + roomClassNames.get(i) + " Abbreviation : " + roomClassAbbs.get(i));
				}					
			}
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", test_name,
					test_description, test_catagory, test_steps);		
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", test_name, test_description,
					test_catagory, test_steps);
		}*/
		//Add Product & Bundles
		try {
			test_steps.add("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
			app_logs.info("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
			navigation.Inventory(driver, test_steps);
			derivedRate.clickTab(driver, "Products & Bundles", test_steps);
			test_steps.add("Navigated to products and bundles");
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to open Products & Bundles Tab", 
					"Failed to open Products & Bundles Tab", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to open Products & Bundles Tab", 
					"Failed to open Products & Bundles Tab", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		String productStartDate = Utility.convertTokenToArrayList(seasonStartDate, Utility.SEASONDELIM).get(0);
		String productEndDate = Utility.convertTokenToArrayList(seasonEndDate, Utility.SEASONDELIM)
				.get(Utility.convertTokenToArrayList(seasonEndDate, Utility.SEASONDELIM).size() - 1);

		//Add Products under Products & Bundles Section
		try {
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
			if(! ratePackage.verifyProductExist(driver, productsName) )
			{
				getTest_Steps = ratePackage.createProducts(driver, productsName, isSellOnBookingEngine,
						bookingEngineAvailabilityOption, productsRoomClass, productsDescription, productsCost,
						productsPolicy, productsCategory, calculationMethodOne, calculationMethodTwo, productsStartDate,
						productsEndDate);
				test_steps.addAll(getTest_Steps);
			}
			test_steps.add("=================== GET ALL PRODUCTS DEFAULT PRICE AND CALCULATION METHOD ======================");
			app_logs.info("=================== GET ALL PRODUCTS DEFAULT PRICE AND CALCULATION METHOD ======================");
			producstPriceMap = ratePackage.getAllProductsPrice(driver);
			test_steps.add("Products with default Price : " + producstPriceMap.toString());
			app_logs.info("Products with default Price : " + producstPriceMap.toString());
			producstCalculationMethodMap = ratePackage.getAllProductsCalculationMethod(driver);
			test_steps.add("Products with default calculation method : " + producstCalculationMethodMap.toString());
			app_logs.info("Products with default calculation method : " + producstCalculationMethodMap.toString());			
			producstCategories = ratePackage.getAllProductsCategories(driver);
			test_steps.add("Products with Category : " + producstCategories.toString());
			app_logs.info("Products with Category : " + producstCategories.toString());			

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create/Open Product", 
					"Failed to Create/Open Product", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create/Open Product", 
					"Failed to Create/Open Product", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Navigate to Rate Grid and verify whether rate plan exists or not, if it doesn't exist then create new Package rateplan
		try {

			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, test_steps);
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to open RateGrid", 
					"Failed to open RateGrid", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to open RateGrid", 
					"Failed to open RateGrid", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify whether rate plan exists or not
		boolean israteplanExist = false;
		test_steps.add("=================== Verify Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Rate plan Exist or Not ======================");
		try {
			Utility.app_logs.info("RatePlanName : " + ratePlanNames.get(0));
			israteplanExist = rateGrid.isRatePlanExist(driver, ratePlanNames.get(0), test_steps);
			Utility.app_logs.info("israteplanExist : " + israteplanExist);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Verify Rate Plan", 
					"Failed to Verify Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Verify Rate Plan", 
					"Failed to Verify Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		if(israteplanExist)
		{
			rateGrid.clickOnEditRatePlan(driver);
			test_steps.add("Click on edit rate plan");
			app_logs.info("Click on edit rate plan");
			rateFolioDisplayName  = nightlyRate.getRateFolioDisplayName(driver);
			test_steps.add("Folio Display Name : " + rateFolioDisplayName);
			app_logs.info("Folio Display Name : " + rateFolioDisplayName);
		}
		/*if (!israteplanExist) {
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
				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();
				nightlyRate.enterRatePlanName(driver, ratePlanName, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, Description, test_steps);

				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", ratePlanName, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);

			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
						"Failed to create Package Rate Plan", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
						"Failed to Create Package Rate Plan", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			}
			try {
				
				test_steps.clear();
				test_steps= ratePackage.addProducts(driver, productsName, productsCost, 
						calculationMethodOne, calculationMethodTwo);
				test_steps.addAll(test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				
			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
						"Failed to create Package Rate Plan", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
		 		Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
					"Failed to Create Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
			}
			try {
				
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

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRestrictionReq), RestrictionType, 
						Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights, 
						Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount, 
						Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode, test_steps);


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
				Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
						"Failed to create Package Rate Plan", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
					"Failed to Create Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
			}
			try {			
				test_steps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, test_steps);
				nightlyRate.createSeasons(driver, test_steps, seasonStartDate, seasonEndDate, SeasonName, isMonDay,
						isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						isAdditionalChargesForChildrenAdults, RatePerNight, maxAdults, maxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
						ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
						ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSeasonLevelRules,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues,
						isSeasonPolicies);
				nightlyRate.clickCompleteChanges(driver, test_steps);
				
				try {
					nightlyRate.clickSaveAsActive(driver, test_steps);
				} catch (Exception f) {
					nightlyRate.clickCompleteChanges(driver, test_steps);
					nightlyRate.clickSaveAsActive(driver, test_steps);
				}
				 Utility.rateplanName=ratePlanName;

				test_steps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");
				
			}catch (Exception e) {
				Utility.catchException(driver, e, "Failed to Create Season For Package Rate Plan", 
						"Failed to create Season For Package Rate Plan", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
		 		Utility.catchError(driver, e, "Failed to Create Season For Package Rate Plan", 
					"Failed to Create Season For Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);			
			}			
			try {
				test_steps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
				app_logs.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.searchRatePlan(driver, Utility.rateplanName);
				
				String getRatPlanName = rateGrid.selectedRatePlan(driver);				
				app_logs.info("getRatPlanName: "+getRatPlanName);
				
				test_steps.add("Successfully verified Created Rate Plan");
				app_logs.info("Successfully verified Created Rate Plan");
				
			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
						"Failed to create Package Rate Plan", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
		 		Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
					"Failed to Create Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
			}
		}
		else
		{		
			//nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName,datesRangeList,
				//	seasonStartDate, seasonEndDate, "", roomClassName, "200", "", 
				//	"", "", "", true);
			//Wait.waitUntilPageLoadNotCompleted(driver, 40);
		}*/		
		String salutation = "Ms.";
		String guestFirstName = "PackageRate_R";
		String guestLastName = Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		String phoneNumber = "9876543444";
		String altenativePhone = "9876543211";
		String email = "innroadautomation@innroad.com";
		String account = "";
		String accountType = "";
		String address1 = "Address1 - Lane1";
		String address2 = "Lane2";
		String address3 = "Lane3";
		String city = "New York";
		String country = "United States";
		String state = "New York";
		String postalCode = "5432";
		boolean isGuesProfile = false;
		String PaymentMethod = "";
		String CardNumber = "";
		String NameOnCard = "";
		String ExpiryDate = "";
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
		boolean isTaxExempt = true;
		String taxExemptID = "TAX_ITAUTO";
		
		String CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
		String CheckOutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 2);
		String adult = "1";
		String children = "1";
		HashMap<String, String> data = null;
		//Create Reservatin with package rate Plan and verify it - 907827
		try
		{			
			navigation.ReservationV2_Backward(driver);
			test_steps.add("===============Creating Single reservation With package rate plan=================");
			app_logs.info("===============Creating Single reservation With package rate plan=================");			
			data = resV2.createReservationwithACF(driver, test_steps, "From Reservations page",
			  checkInDate, checkOutDate, adult, children, ratePlanNames.get(0), "", roomClassNames.get(0),
			  roomClassAbbs.get(0), salutation, guestFirstName, guestLastName, phoneNumber,
			  altenativePhone, email, address1, address2, address3, city, country, state,
			  postalCode, isGuesProfile, marketSegment, referral, PaymentMethod,
			  CardNumber, NameOnCard, ExpiryDate, additionalGuests, roomNumber, quote,
			  noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
			  taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
			  accountFirstName, accountLastName, true, "TAX_IT",false,false,false,false);
			test_steps.add("========================Reservation with Package Rate Plan is created Successfully - Reservation Number : ========================" + data.get("ReservationNumber"));
			app_logs.info("========================Reservation with Package Rate Plan created Successfully - Reservation Number : ========================" + data.get("ReservationNumber"));			
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
					"Failed to create Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
				"Failed to Create Package Rate Plan", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Verify Trip Summary & Folio
		ReservationStatusBar statusBar = resV2.getStatusBarDetail(driver);
		TripSummary tripSummary = resV2.getTripSummaryDetail(driver);
		try
		{
			reservationNo = statusBar.getSB_CONFIRMATION_NO();
			calculatedAmountofProductForThisReservation1 = ratePackage
					.calculateProductPriceforGivenReservationDetails(driver, days, adult, children,
							"per " + calculationMethodOne.split(Utility.DELIM)[0],
							"per " + calculationMethodTwo.split(Utility.DELIM)[0],
							productsCost.split(Utility.DELIM)[0],
							"", false, test_steps);
			test_steps.add("Product1 - Addon : "+calculatedAmountofProductForThisReservation1);
			app_logs.info("Product1 - Addon : "+calculatedAmountofProductForThisReservation1);

			//calculatedAmountofProductForThisReservation1 = String.parseDouble(calculatedAmountofProductForThisReservation1);
			calculatedAmountofProductForThisReservation2 = ratePackage
					.calculateProductPriceforGivenReservationDetails(driver, days, adult, children,
							"per " + calculationMethodOne.split(Utility.DELIM)[1],
							"per " + calculationMethodTwo.split(Utility.DELIM)[1],
							productsCost.split(Utility.DELIM)[1],
							"", false, test_steps);
			String expectedTotal = calculatedAmountofProductForThisReservation1 + calculatedAmountofProductForThisReservation2;
			test_steps.add("Expected Incidentals2 total : "+calculatedAmountofProductForThisReservation2);			
			app_logs.info("Expected Incidentals2 total : "+calculatedAmountofProductForThisReservation2);
			test_steps.add("Actual Incidentals From Reservations : "+tripSummary.getTS_INCIDENTALS());			
			app_logs.info("Actual Incidentals From Reservations : "+tripSummary.getTS_INCIDENTALS());
			
			Utility.customAssert(tripSummary.getTS_INCIDENTALS(), expectedTotal, 
					true, "Successfully Verified Incidentals in Trip Summary match with expected Value", 
					"Failed To Verify - Incidentals doesn't match with Expected Value", true, test_steps);
			//resV2.closeAllOpenedReservations(driver);
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Package Rate Plan Reservation", 
					"Failed to create Package Rate Plan Reservation", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Create Package Rate Plan Reservation", 
				"Failed to Create Package Rate Plan Reservation", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Verify Package rate plan in Folio & Details Tab
		try
		{
			//FolioRateNameE6X8J8KqJe - Per Stay Component(s)
			String desc = rateFolioDisplayName + " - Per "+calculationMethodsTwo.get(0)+" Component(s)";
			app_logs.info("Package Desc1 : " + desc);
			resV2.click_Folio(driver, test_steps);
			ArrayList<FolioLineItem> folioLineItems = resV2.getAllFolioLineItems(driver);
			resV2.verifyFolioLineItem(folioLineItems.get(2), "Pending", true, false, 
					Utility.getCustomDate(checkInDate,"dd/MM/yyyy","MMM d, yyyy",0), true, false,
					"Package", true, false, desc, true, false, "1", true, false, 
					calculatedAmountofProductForThisReservation1, true, false, "0", true, false, 
					calculatedAmountofProductForThisReservation1, true, false, test_steps);
			desc = rateFolioDisplayName + " - Per "+calculationMethodsTwo.get(1)+" Component(s)";
			app_logs.info("Package Desc2 : " + desc);
			resV2.verifyFolioLineItem(folioLineItems.get(1), "Pending", true, false, 
					Utility.getCustomDate(checkInDate,"dd/MM/yyyy","MMM d, yyyy",0), true, false,
					"Package", true, false, desc, true, false, "1", true, false, 
					calculatedAmountofProductForThisReservation2, true, false, "0", true, false, 
					calculatedAmountofProductForThisReservation2, true, false, test_steps);
			resV2.closeAllOpenedReservations(driver);
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Verify Package Rate Plan Reservation", 
					"Failed to Verify Package Rate Plan Reservation", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Verify Package Rate Plan Reservation", 
				"Failed to Verify Package Rate Plan Reservation", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Create Single reservation with NIghtly rate plan and verify adding addon/incidentals in Guest Info Page - 907993		
		guestFirstName = "VerifyAddonIncidentals";
		guestLastName = "guestinfo_"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		try
		{
			test_steps.add("===============Creating Single reservation and verify Addon/Incidentals on Guest Info Page=================");
			app_logs.info("===============Creating Single reservation and verify Addon/Incidentals on Guest Info Page=================");
			navigation.ReservationV2_Backward(driver);			
			data = resV2.createReservationwithACF(driver, test_steps, "From Reservations page",
			  checkInDate, checkOutDate, adult, children, ratePlanNames.get(1), "", roomClassNames.get(1),
			  roomClassAbbs.get(1), salutation, guestFirstName, guestLastName, phoneNumber,
			  altenativePhone, email, address1, address2, address3, city, country, state,
			  postalCode, isGuesProfile, marketSegment, referral, PaymentMethod,
			  CardNumber, NameOnCard, ExpiryDate, additionalGuests, roomNumber, quote,
			  noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
			  taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
			  accountFirstName, accountLastName, true, "TAX_IT",false,false,false,false);
			test_steps.add("========================Single Reservation is created Successfully - Reservation Number : ========================" + data.get("ReservationNumber"));
			app_logs.info("========================Single Reservation is created Successfully - Reservation Number : ========================" + data.get("ReservationNumber"));			
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
					"Failed to create Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
				"Failed to Create Package Rate Plan", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		StayInfo stayInfo = new StayInfo();
		//Delete Folio Line Items & Make Invalid Payment - 908119
		try
		{
			Elements_ReservationV2 elementsV2 = new Elements_ReservationV2(driver);
			stayInfo = resV2.getStayInfoDetail(driver);
			String roomNo = stayInfo.getSI_ROOM_NUMBER();
			String folioName="Guest Folio For "+roomClassAbb + " : "+roomNo;
			String yearDate = Utility.getFutureMonthAndYearForMasterCard();
			driver.navigate().refresh();
			resV2.click_Folio(driver, test_steps);
			folioObj.addFolioLineItem(driver, test_steps, category, perUnit);
			String amount = Utility.convertDecimalFormat(perUnit);
			amount = "$" + amount + "";
			folioObj.deleteFolioLineItem(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), category, amount, "", "", "");
			//folioObj.folioSettings(driver, test_steps, folioName, "Guest Folio", "No", "Yes", "Yes", "Yes", "", "", "", "");
			folioObj.displayVoidItems(driver, test_steps);
			
			folioObj.clickPayButtonFromFolioTab(driver, test_steps);
			folioObj.enterAmountinPayRefundPopup(driver, ratePlanNames, perUnit);
			try
			{
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD), driver);
				elementsV2.TAKE_PAYMENT_PAYMENT_METHOD.click();
				String Option = "(//span[contains(text(),'" + paymentMethod + "')])[last()]";
				Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
				driver.findElement(By.xpath(Option)).click();
				app_logs.info("Selected Payment Method "+paymentMethod );
				test_steps.add("Selected Payment Method "+paymentMethod);
			}
			catch(Exception e)
			{
				Wait.WaitForElement(driver, OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD);
				Utility.ScrollToElement(elementsV2.TAKE_PAYMENT_PAYMENT_METHOD, driver);
				Wait.wait2Second();
				elementsV2.TAKE_PAYMENT_PAYMENT_METHOD.click();
	
				String Option = "(//span[contains(text(),'" + paymentMethod + "')])[last()]";
				Wait.waitForElementToBeVisibile(By.xpath(Option), driver);
				driver.findElement(By.xpath(Option)).click();
				app_logs.info("Selected Payment Method "+paymentMethod );
				test_steps.add("Selected Payment Method "+paymentMethod);
			}
			folioObj.enterCardNumberinPayRefundpopup(driver,test_steps,invalidCCNo);
			folioObj.enterCardHolderNameinPayRefundPopup(driver, test_steps, nameOnCard);
			folioObj.enterExpiryDateinPayRefundpopup(driver, test_steps, expiryDate);
			folioObj.enterCVVCodeinPayRefundpopup(driver, test_steps, "");
			Wait.wait15Second();
			try
			{
				Utility.ScrollToElement(elementsV2.takePaymentPayButton, driver);
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.takePaymentPayButton), driver);
				elementsV2.takePaymentPayButton.click();
				//Wait.wait5Second();
			}catch(Exception e)
			{
				String payXpath = "(//span[contains(@data-bind,'PaymentProcessButtonText')]|//a[contains(@data-bind,'IsPaymentFormValid')]/span[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'LOG')]|//a[contains(@data-bind,'PaymentProcessButtonText')][contains(text(),'Pay')]|//a[contains(text(),'Authorize')]|//a[contains(text(),'Refund')])[2]";
				Wait.waitForElementToBeClickable(By.xpath(payXpath), driver);
				Utility.ScrollToElement(By.xpath(payXpath).findElement(driver), driver);
				Utility.clickThroughJavaScript(driver, By.xpath(payXpath).findElement(driver));
				Wait.wait5Second();
			}
			resV2.VerifyInvalidCardMessageOnDepositPolicyPopup(driver, test_steps);
			folioObj.closePaymentDetailsWindow(driver, test_steps);
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Delete Folio Line Item & Make Payment using invalid CC", 
					"Failed to Delete Folio Line Item & Make Payment using invalid CC", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Delete Folio Line Item & Make Payment using invalid CC", 
				"Failed to Delete Folio Line Item & Make Payment using invalid CC", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Verify Void & Failed Line Items in the folio - 908119
		try
		{
			String amount = Utility.convertDecimalFormat("0");
			amount = "$" + amount + "";
			ArrayList<FolioLineItem> folioLineItemsAfterVoidingLineItem = resV2.getAllFolioLineItems(driver);
			resV2.verifyFolioLineItem(folioLineItemsAfterVoidingLineItem.get(1), "Void", true, false, 
					Utility.getCustomDate(checkInDate,"dd/MM/yyyy","MMM d, yyyy",0), true, false,
					"Spa", true, false, "Spa", true, false, "1", true, false, 
					"0", true, false, "0", true, false, "0", true, false, test_steps);
			
			if (!folioObj.verifyCheckboxisEnabledForFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), "Void", 
					"", "", amount, "", "", "", "", ""))
			{
				test_steps.add("For Voided Line Items Checkbox is disabled");
				app_logs.info("For Voided Line Items Checkbox is disabled");
				assertTrue(true);				
			}
			else
			{
				test_steps.add("For Voided Line Items Checkbox is Enabled");
				app_logs.info("For Voided Line Items Checkbox is enabled");
				assertTrue(false);
			}
			String last4Digit=Utility.getCardNumberHidden(invalidCCNo);
			String desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp.Date:" + expiryDate + "";
			resV2.verifyFolioLineItem(folioLineItemsAfterVoidingLineItem.get(1), "Failed", true, false, 
					Utility.getCustomDate(checkInDate,"dd/MM/yyyy","MMM d, yyyy",0), true, false,
					paymentMethod, true, false, desc, true, false, "0", true, false, 
					"0", true, false, "0", true, false, "0", true, false, test_steps);			
			if (!folioObj.verifyCheckboxisEnabledForFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"),
					"Failed", paymentMethod, "", amount, paymentMethod, invalidCCNo, nameOnCard, last4Digit, expiryDate))	
			{
				test_steps.add("For Failed Line Items Checkbox is disabled");
				app_logs.info("For Failed Line Items Checkbox is disabled");
				assertTrue(true);				
			}
			else
			{
				test_steps.add("For Failed Line Items Checkbox is Enabled");
				app_logs.info("For Failed Line Items Checkbox is enabled");
				assertTrue(false);
			}			
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Package Rate Plan", 
					"Failed to create Package Rate Plan", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Create Package Rate Plan", 
				"Failed to Create Package Rate Plan", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Add Incidentals to Guest Info - Details tab and verify Trip Summary
		try
		{
			driver.navigate().refresh();
			resV2.addIncidental(driver, Utility.getCurrentDate("dd/MM/yyyy"), category, perUnit, "1");
			tripSummary = resV2.getTripSummaryDetail(driver);		
			Utility.customAssert(tripSummary.getTS_INCIDENTALS(), perUnit, true, "Successfully Verified Incidental Amount in Trip Summary", 
					"Failed to Verify Incidental in Trip Summary", true, test_steps);
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Created "+category+" item "+category+" for Guest Folio", 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			driver.navigate().refresh();
			
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Add & Verify Incidental in the Trip Summary", 
					"Failed to Add & Verify Incidental in the Trip Summary", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Add & Verify Incidental in the Trip Summary", 
				"Failed to Add & Verify Incidental in the Trip Summary", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Add Addon to Guest Info - details tab and verify trip summary
		try
		{
			driver.navigate().refresh();					
			resV2.addAddon(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), addOn, Integer.parseInt(addOnPerUnit));
			tripSummary = resV2.getTripSummaryDetail(driver);		
			
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Add & Verify Addon in the Trip Summary", 
					"Failed to Add & Verify Incidental in the Trip Summary", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
	 		Utility.catchError(driver, e, "Failed to Add & Verify Addon in the Trip Summary", 
				"Failed to Add & Verify Incidental in the Trip Summary", "ReservationV2", test_name,
				test_description, test_catagory, test_steps);
		}
		//Update TestRail
		try 
		{
			ArrayList<String> list = Utility.convertTokenToArrayList(TestCaseId, "\\|");

			caseId = new ArrayList<String>();
			statusCode = new ArrayList<String>();
			comments = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				caseId.add(list.get(i));
				statusCode.add("1");
				comments.add("PASS : " + this.getClass().getSimpleName().trim());
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
					TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		 } catch (Exception e) 
		 {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	//@AfterClass(alwaysRun=true)
	public void closeDriver() {
		driver.quit();
	}

}
