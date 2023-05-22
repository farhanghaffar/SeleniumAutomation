package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.ReservationV2Search;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;


public class VerifyCorpAccQuotePaymentRefundV2 extends TestCore
{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	public static String accountIdFromUrl = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account objAcc = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 resV2 = new ReservationV2();
	ReservationV2Search resSearch = new ReservationV2Search();
	FolioNew folioNewobj = new FolioNew();
	MerchantServices msObj = new MerchantServices();
	Properties properties = new Properties();
	
	@BeforeTest(alwaysRun = true)
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
		return Utility.getData("VerifyCorpAccQuotePayRefund", excelRiddhi);
	}

	
	private void getMapData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	
	@Test(dataProvider = "getData")
	public void verifyCorpAccQuotePaymentRefundV2(String TestCaseId, String TestCaseName, 
			String paymentGateway, String accountNumber, String accountDesc, String accountStatus, 
			String isCardPresent, String isCardNotPresent, String isEcommerce, String isRequireCVVForAll,
			String mode, String accountID, String subAccountId, String merchantPin,
			String storeId, String url, String tokenId, String username, String password,
			String restAPIKey, String hituserId, String hitKey, String transactionKeyCode, String associateProperties,
			String RatePlanName,String marketSegment, String Referral, String paymentMethod, String paymentAmount) throws Exception 
	{
		Utility.initializeTestCase(TestCaseId, caseId, statusCode, comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");

		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(excelRiddhi, "ResV2_Login"));				
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			associateProperties = properties.getProperty(driver, test_steps);
		} 
		catch (Exception e) 
		{
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		catch (Error e) 
		{
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		String channelName = "innCenter";
		System.out.println("channelName : " + channelName);
		System.out.println(System.getProperty("user.dir"));
		HashMap<String, String> ratePlanData = null;
		HashMap<String, String> roomClassData = null;

		try 
		{
			roomClassData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNewRoomClass");
			ratePlanData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNightlyRatePlanV2");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Get Room Class Fields from the excel sheet via roomclassData, and assign them to local variables
		String roomClassName = roomClassData.get("roomClassName");
		String roomClassAB = roomClassData.get("roomClassAB");
		String policy = roomClassData.get("policy");
		String sortOrder = roomClassData.get("sortOrder");
		String maxAdults = roomClassData.get("maxAdults");
		String maxPerson = roomClassData.get("maxPerson");
		String details = roomClassData.get("details");
		String roomQuant = roomClassData.get("roomQuant");
		String roomName = roomClassData.get("roomName");
		String roomSortNo = roomClassData.get("roomSortNo");
		String statinId = roomClassData.get("statinId");
		String zone = roomClassData.get("zone");
		
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
		String MaxPersons = ratePlanData.get("MaxPersons");
		
		String salutation = "Mr.";
		String guestFirstName = "QuoteAuto";
		String guestLastName = "Test_"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		String phoneNumber = "1234567899";
		String altenativePhone = "9876543211";
		String email = "innroadautomation@innroad.com";
		String account = "";
		String accountType = "Account (Corp/Member)";
		String address1 = "Lane1";
		String address2 = "Lane2";
		String address3 = "Lane3";
		String city = "New York";
		String country = "United States";
		String state = "New York";
		String postalCode = "5432";
		boolean isGuesProfile = false;
		String referral = "Walk In";
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
//		String accountName = "";
		
		String accountFirstName = "Riddhi_CorpAcc";
		String accountLastName = "GuestLastName";
		boolean isTaxExempt = false;
		String taxExemptID = "";
		String CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
		String CheckOutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 1);
		String adult = "1";
		String children = "1";
		HashMap<String, String> data = null;
		String[] paymentAmounts = paymentAmount.split("\\|");
		String[] paymentGateways = paymentGateway.split("\\|");
		String[] accountIDs = accountID.split("\\|");
		String[] merchantPins = merchantPin.split("\\|");
		String[] storeIds = storeId.split("\\|");
		String[] urls = url.split("\\|");
		String[] tokenIds = tokenId.split("\\|");
		String ReservationNumber = "";
		String folioName = "";
		//Navigate to Setup >> Click on Room Classes >> Create New Room Class if it doesn't exist
		/*try
		{
			Elements_NewRoomClassPage roomClassEle = new Elements_NewRoomClassPage(driver);
			NewRoomClassesV2 roomClassObj = new NewRoomClassesV2();
			
			Wait.WaitForElement(driver, OR.setUP);
			roomClassEle.setUP.click();
			roomClassEle.roomClasses.click();
			
			boolean isRoomClassAvailable = roomClassObj.searchRoomClassV2(driver, roomClassName);
			if(!isRoomClassAvailable)
			{
				test_steps.add("=================== CREATING NEW ROOM CLASS WITH DESCRIPTION ======================");
				app_logs.info("=================== CREATING NEW ROOM CLASS WITH DESCRIPTION ======================");
				
				roomClassEle.createnewRoomClass.click();
				test_steps.add("Clicking on New Room Class");
				app_logs.info("Clicking On New Room Class");
				
				getTest_Steps.clear();
				getTest_Steps = roomClassObj.createRoomClassWithDescriptionV2(driver, getTest_Steps, roomClassName, roomClassAB, 
						policy, sortOrder, maxAdults, maxPerson, details, roomQuant, roomName, statinId, sortOrder, zone);
				test_steps.addAll(getTest_Steps);
				test_steps.add("New Room Class Created");
				app_logs.info("New Room Class Created");
			}
			else
			{
				test_steps.add(" Room Class Exists : " + roomClassName);
				app_logs.info(" Room Class Exists : " + roomClassName);
			}
		}
		catch (Exception e) 
		{
		if (Utility.reTry.get(test_name) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			Utility.updateReport(e, "create new room class", test_name, "RoomClass", driver);
		} 
			else 
			{
				Assert.assertTrue(false);
			}
		}
		catch (Error e) 
		{
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create new room class", test_name, "RoomClass", driver);
			}
		}
		//Verify Whether Rate Plan Exists or not
		try 
		{
			// After login -> Navigate to Inventory >> RatesGrid
			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
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
		//Verify Rateplan exists or not, RatePlanName extracted from ExcelSheet
		boolean israteplanExist = false;
		test_steps.add("=================== Verify Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Rate plan Exist or Not ======================");
		try {
			Utility.app_logs.info("RatePlanName : " + RatePlanName);
			israteplanExist = rateGrid.isRatePlanExist(driver, RatePlanName, test_steps);
			Utility.app_logs.info("israteplanExist : " + israteplanExist);
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
			test_steps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan ======================");
			try {

				test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", test_steps);

				test_steps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

//				RatePlanName = RatePlanName + Utility.generateRandomString();
				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, RatePlanName, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, Description, test_steps);

				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, test_steps);

				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, test_steps);
				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);

//			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, test_steps);

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

				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, test_steps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						test_steps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), test_steps);
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
				test_steps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, test_steps);

				nightlyRate.createSeason(driver, test_steps, SeasonStartDate, SeasonEndDate, SeasonName, isMonDay,
						isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
						ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
						ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues,
						isSeasonPolicies);

				nightlyRate.clickCompleteChanges(driver, test_steps);
				nightlyRate.clickSaveAsActive(driver, test_steps);
				Wait.wait30Second();
				test_steps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");
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
		}*/
		//Create Payment Gateway - MP
		try
		{
			String payGatewayAccountName = paymentGateways[0] + "_Gateway";			
		  	navigation.setup(driver);
		  	msObj.navigatetoMerchantServices(driver, test_steps);
		  	test_steps.add("Creating Payment Gateway : " + paymentGateway);
		  	app_logs.info("Creating Payment Gateway : " + paymentGateway);
		  	msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
		  	msObj.createNewMerchantService(driver, test_steps, payGatewayAccountName, accountNumber, accountDesc, accountStatus, 
		  			isCardPresent, isCardNotPresent, isEcommerce, isRequireCVVForAll, paymentGateways[0],
		  			mode, accountIDs[0], subAccountId, merchantPins[0],
		  			storeIds[0], urls[0], tokenIds[0], username, password,
		  			restAPIKey, hituserId, hitKey, transactionKeyCode, associateProperties);
	  	}
		catch(Exception e)
		{
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String CorpAccountNumber = null;
		String CorpAccountName = "RidsCorp_Auto";
		String CorpAccounttype = "Corporate/Member Accounts";
		// Create New Corporate account/Or Open Existing Corporate Account
		try {
			
			/*
			objAcc.ClickNewAccountbutton(driver, CorpAccounttype);
			objAcc.AccountDetails(driver, CorpAccounttype, CorpAccountName);
			CorpAccountNumber = Utility.GenerateRandomString15Digit();
			objAcc.ChangeAccountNumber(driver, CorpAccountNumber);
			getTest_Steps.clear();
			getTest_Steps = objAcc.AccountAttributes(driver, test, marketSegment, referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = objAcc.Mailinginfo(driver, test, GuestCorpAccFirstName, GuestCorpAccLastName, phoneNumber,
					altenativePhone, address1, address2, address3, email, city, state, postalCode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = objAcc.Billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = objAcc.AccountSave(driver, test, CorpAccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			accountIdFromUrl = Utility.getParamValueFromURL(driver, test_steps, "AccountId");
			test_steps.add("AccountId From URL :: "+accountIdFromUrl);
			app_logs.info("AccountId From URL :: "+accountIdFromUrl); */
			navigation.ReservationV2_Backward(driver);
			if (!objAcc.checkForAccountExistsAndOpen(driver, test_steps, CorpAccountName, CorpAccounttype, true)) {
				navigation.ReservationV2_Backward(driver);
				navigation.Accounts(driver);
				objAcc.createNewAccount(driver, test_steps, CorpAccounttype, CorpAccountName, marketSegment, referral, 
						accountFirstName, accountLastName, phoneNumber, altenativePhone, 
						address1, address2, address3, email, city, state, postalCode);
				objAcc.AccountSave(driver, test, CorpAccountName, getTest_Steps);				
			}
			CorpAccountNumber = objAcc.getAccountNum(driver);
			accountIdFromUrl = Utility.getParamValueFromURL(driver, test_steps, "AccountId");
			test_steps.add("AccountId From URL :: "+accountIdFromUrl);
			app_logs.info("AccountId From URL :: "+accountIdFromUrl);
			
			Wait.wait10Second();
			driver.navigate().refresh();
		} catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create New Quote From Corporate Account
		try 
		{
			test_steps.add("========== Creating Single Quote From Corporate Account ==========");
			app_logs.info("========== Creating Single Quote From Corporate Account ==========");
			guestFirstName = "SingleQuote";
			guestLastName = "Test_"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);
			
			objAcc.CreateNewReservation(driver);		
			resV2.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);			
			resV2.clickOnFindRooms(driver, test_steps);
			ArrayList<String> roomNumbers = new ArrayList<>();
			roomNumbers = resV2.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName);
			resV2.selectRoomToReserve(driver, test_steps, roomClassName, roomNumbers.get(0));				
			resV2.clickNext(driver, test_steps);
			//resV2.clickOnCreateGuestButton(driver, test_steps);
			resV2.turnOnOffcreateGuestProfileToggle(driver, test_steps,false);
			resV2.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);
			resV2.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			resV2.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, Referral, "Yes");
			
			resV2.clickSaveQuote(driver, test_steps, false);								
			String singleQuoteConfirmationNo = resV2.get_ReservationConfirmationNumber(driver, test_steps);
			test_steps.add("========== Corporate Account Single QUote Created Successfully==========");
			app_logs.info("========== Corporate Account Single QUote Created Successfully==========");			
			resV2.clickCloseReservationSavePopup(driver, test_steps);
			ReservationStatusBar statusBar = resV2.getStatusBarDetail(driver);
			resV2.verifyStatusBarDetail(statusBar,
					salutation + " " + guestFirstName + " " + guestLastName, true, true, 
					"QUOTE",  true, true, 
					singleQuoteConfirmationNo,  true, true, 
					"("+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM d")+" - "+Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MMM d")+")",  true, true, 
					zone,  false, true, 
					email,  true, true, 
					adult,  false, true, 
					children,  false, true,  test_steps);		
			//Verify Single Quote is created successfully 
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Saved quote with Confirmation Number: "+singleQuoteConfirmationNo, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			resV2.closeAllOpenedReservations(driver);
			driver.navigate().refresh();
		} catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account Single Quote", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account SIngle Quote", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//create Corporate Account - MRB Quote
		try
		{
			guestFirstName = "MRBQuote";
			guestLastName = "Test_"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);			
			String CheckInDate4MRB = CheckInDate +"|"+CheckInDate;
			String CheckOutDate4MRB= CheckOutDate+"|"+CheckOutDate;
			adult = adult+"|"+adult;
			children = children+"|"+children;
			String RatePlanName4MRB=RatePlanName+"|"+RatePlanName;
			String roomClassName4MRB =  roomClassName +"|"+roomClassName;
			ArrayList<String> roomNumbers = new ArrayList<>();
			ArrayList<String> roomClasses = Utility.splitInputData(roomClassName4MRB);
			ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
			//Navigate to Account Pages
			navigation.Accounts(driver);
			test_steps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");
			objAcc.searchAccount(driver, test, CorpAccounttype, CorpAccountName, CorpAccountNumber, test_steps);
			objAcc.OpenExitingAccount(driver);
			
			test_steps.add("========== Creating MRB Quote From Corporate Account ==========");
			app_logs.info("========== Creating MRB Quote From Corporate Account ==========");
			objAcc.CreateNewReservation(driver);
			resV2.searchDataForFindRoomsForMRB(driver, test_steps, CheckInDate4MRB, CheckOutDate4MRB, adult, 
					children, RatePlanName4MRB, PromoCode);
			resV2.clickOnFindRooms(driver, test_steps);
			
			for (int i = 0; i < roomClassName4MRB.split("\\|").length; i++) 
			{
				int j = i+1;
				roomNumbers = resV2.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));				
				try 
				{
					resV2.selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));						
					data.put("RoomNumber"+j, roomNumbersProvided.get(i));
				} catch (Exception e) 
				{	resV2.selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));	}
			}
			resV2.clickNext(driver, test_steps);
			resV2.turnOnOffcreateGuestProfileToggle(driver, test_steps,false);
			resV2.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);			
			resV2.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			resV2.clickCopyGuestFromPrimaryRoom(driver, test_steps);
			resV2.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, Referral, "Yes");			
			resV2.clickSaveQuote(driver, test_steps, false);								
			String mrbQuoteConfirmationNo = resV2.get_ReservationConfirmationNumber(driver, test_steps);
			test_steps.add("========== Corporate Account MRB QUote Created Successfully==========");
			app_logs.info("========== Corporate Account MRB QUote Created Successfully==========");			
			resV2.clickCloseReservationSavePopup(driver, test_steps);
			ReservationStatusBar statusBar = resV2.getStatusBarDetail(driver);
			resV2.verifyStatusBarDetail(statusBar,
					salutation + " " + guestFirstName + " " + guestLastName, true, true, 
					"QUOTE",  true, true, 
					mrbQuoteConfirmationNo,  true, true, 
					"("+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM d")+" - "+Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MMM d")+")",  true, true, 
					zone,  false, true, 
					email,  true, true, 
					adult,  false, true, 
					children,  false, true,  test_steps);		
			//Verify Single Quote is created successfully 
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Saved quote with Confirmation Number: "+mrbQuoteConfirmationNo, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			resV2.closeAllOpenedReservations(driver);
			driver.navigate().refresh();
		} catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account MRB Quote", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account MRB Quote", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//create Corporate Account - SPLIT-MRB Quote
		try
		{
			guestFirstName = "Split-MRBQuote";
			guestLastName = "Test_"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);		
			String RatePlanName4SplitMRB = RatePlanName+"|"+RatePlanName;
			String roomClassName4SplitMRB =  roomClassName +"|"+roomClassName;			
			ArrayList<String> roomClasses = Utility.splitInputData(roomClassName4SplitMRB);
			ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
			ArrayList<String> roomNumbers = new ArrayList<>();
			String CheckInDate4SplitMRB = Utility.getCurrentDate("dd/MM/yyyy")+"|"+
										Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 1);
			String CheckOutDate4SplitMRB = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 1)+"|"+
										Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 3);
			navigation.Accounts(driver);
			test_steps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");
			objAcc.searchAccount(driver, test, CorpAccounttype, CorpAccountName, CorpAccountNumber, test_steps);
			objAcc.OpenExitingAccount(driver);
			test_steps.add("========== Creating SPLIT-MRB Quote From Corporate Account==========");
			app_logs.info("========== Creating SPLIT-MRB Quote From Corporate Account==========");
			objAcc.CreateNewReservation(driver);
			
			resV2.searchDataForFindRoomsForSplitMRB(driver, test_steps, CheckInDate4SplitMRB, CheckOutDate4SplitMRB, 
					adult, children, RatePlanName4SplitMRB, PromoCode);
			resV2.clickOnFindRooms(driver, test_steps);
			for (int i = 0; i < roomClassName4SplitMRB.split("\\|").length; i++) 
			{
				int j = i+1;
				roomNumbers = resV2.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
				try 
				{
					resV2.selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));						
					data.put("RoomNumber"+j, roomNumbersProvided.get(i));
				} catch (Exception e) 
				{
					resV2.selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
					//data.put("RoomNumber"+j, roomNumbers.get(i));
				}
			}
			resV2.clickNext(driver, test_steps);
			resV2.turnOnOffcreateGuestProfileToggle(driver, test_steps,false);
			resV2.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);			
			resV2.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			resV2.clickCopyGuestFromPrimaryRoom(driver, test_steps);
			resV2.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, Referral, "Yes");			
			resV2.clickSaveQuote(driver, test_steps, false);								
			String splitmrbQuoteConfirmationNo = resV2.get_ReservationConfirmationNumber(driver, test_steps);
			test_steps.add("========== Corporate Account SPLIT-MRB QUote Created Successfully==========");
			app_logs.info("========== Corporate Account SPLIT-MRB QUote Created Successfully==========");			
			resV2.clickCloseReservationSavePopup(driver, test_steps);
			ReservationStatusBar statusBar = resV2.getStatusBarDetail(driver);
			resV2.verifyStatusBarDetail(statusBar,
					salutation + " " + guestFirstName + " " + guestLastName, true, true, 
					"QUOTE",  true, true, 
					splitmrbQuoteConfirmationNo,  true, true, 
					"("+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM d")+" - "+Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MMM d")+")",  true, true, 
					zone,  false, true, 
					email,  true, true, 
					adult,  false, true, 
					children,  false, true,  test_steps);		
			//Verify Single Quote is created successfully 
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Saved quote with Confirmation Number: "+splitmrbQuoteConfirmationNo, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			resV2.closeAllOpenedReservations(driver);
			driver.navigate().refresh();

		} catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account - Single Reservation", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account - Single Reservation", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Create Corporate Account Reservation & Make payment in Guest Folio & Account Folio
		
		try
		{
			guestFirstName = "Rids_CorpReservation";
			guestLastName = "Test_"+Utility.generateRandomNumberWithGivenNumberOfDigits(2);			
			ArrayList<String> roomNumbers = new ArrayList<>();
			ArrayList<String> roomClasses = Utility.splitInputData(roomClassName);
			ArrayList<String> ratePlan = Utility.splitInputData(RatePlanName);
			adult = "2";
			children = "1";
			//Navigate to Account Pages
			navigation.Accounts(driver);
			test_steps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");
			objAcc.searchAccount(driver, test, CorpAccounttype, CorpAccountName, CorpAccountNumber, test_steps);
			objAcc.OpenExitingAccount(driver);
			
			objAcc.CreateNewReservation(driver);		
			resV2.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, adult, children, RatePlanName, PromoCode);			
			resV2.clickOnFindRooms(driver, test_steps);
			roomNumbers = resV2.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName);
			resV2.selectRoomToReserve(driver, test_steps, roomClassName, roomNumbers.get(0));				
			resV2.clickNext(driver, test_steps);
			//resV2.clickOnCreateGuestButton(driver, test_steps);
			resV2.turnOnOffcreateGuestProfileToggle(driver, test_steps,false);
			resV2.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);
			resV2.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			resV2.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, Referral, "Yes");			
			resV2.clickBookNow(driver, test_steps, false);								
			ReservationNumber = resV2.get_ReservationConfirmationNumber(driver, test_steps);
			test_steps.add("========== Corporate Account Single Reservation Created Successfully==========");
			app_logs.info("========== Corporate Account Single Reservation Created Successfully==========");			
			resV2.clickCloseReservationSavePopup(driver, test_steps);
			driver.navigate().refresh();
			Wait.wait10Second();
			//Navigate to Guest Folio >> Make Payment using Corporate Account >> Verify Payment in Folio & History Logs
			resV2.click_Folio(driver, test_steps);
			folioNewobj.clickPayButtonFromFolioTab(driver, test_steps);
			String corpAccNamenNumber = CorpAccountName+" ("+CorpAccountNumber+")";
			test_steps.add("Corporate Account Name & Number : "+corpAccNamenNumber);
			folioNewobj.makePayment(driver, test_steps, paymentAmounts[0], paymentMethod, 
					corpAccNamenNumber, "", "", "", "", "");
			String amount = Utility.convertDecimalFormat(paymentAmounts[0]);
			amount = "$" + amount + "";
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), accountType,
					corpAccNamenNumber, amount, "", "", "", "", "");
			
			test_steps.add("=============Navigate to History Log & Verify History Log For Payment using Corporate Account===========");
			app_logs.info("==========Navigate to History Log & Verify History Log For Payment using Corporate Account===========");
			resV2.switchHistoryTab(driver, test_steps);
			String payByCorpAcc="Account - "+CorpAccountName+" ("+CorpAccountNumber+")";
			//resV2.verifyHistoryLogForDifferentPayments(driver, test_steps, paymentAmounts[0], payByCorpAcc, paymentMethod);			
			resV2.enterTextToSearchHistory(driver, "Made a payment "+amount+" using "+payByCorpAcc, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			driver.navigate().refresh();
			Wait.wait10Second();
			//Navigate to Corporate Account Folio >> Make Payment using Corporate Account >> Verify Payment in Folio & History Logs
			folioName=CorpAccountName+"("+accountIdFromUrl+")";
			resV2.click_Folio(driver, test_steps);
			test_steps.add("=============Navigate to Corporate Account Folio ===========");
			app_logs.info("==========Navigate to Corporate Account Folio ===========");
			test_steps.add("Account folioName : " +folioName);
			app_logs.info("Account folioName : " +folioName);			
			folioNewobj.moveToFolio(driver, test_steps, folioName);			
			folioNewobj.clickPayButtonFromFolioTab(driver, test_steps);
			//corpAccNamenNumber = CorpAccountName+" ("+CorpAccountNumber+")";
			//test_steps.add("Corporate Account Name & Number : "+corpAccNamenNumber);
			folioNewobj.makePayment(driver, test_steps, paymentAmounts[0], paymentMethod, 
					corpAccNamenNumber, "", "", "", "", "");
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), accountType,
					corpAccNamenNumber, amount, "", "", "", "", "");			
			test_steps.add("=============Navigate to History Log & Verify History Log For Payment using Corporate Account===========");
			app_logs.info("==========Navigate to History Log & Verify History Log For Payment using Corporate Account===========");
			resV2.switchHistoryTab(driver, test_steps);
			//resV2.verifyHistoryLogForDifferentPayments(driver, test_steps, paymentAmounts[0], payByCorpAcc, paymentMethod);	
			resV2.enterTextToSearchHistory(driver, "Made a payment "+amount+" using "+payByCorpAcc, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			resV2.closeAllOpenedReservations(driver);
			driver.navigate().refresh();			
		} catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account Reservation", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account Reservation", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Delete MP Gateway And create Moneris Gateway for refund
		try
		{
			String payGatewayAccountName = paymentGateways[0] + "_Gateway";			
		  	navigation.setup(driver);
		  	msObj.navigatetoMerchantServices(driver, test_steps);
		  	test_steps.add("Creating Payment Gateway : " + paymentGateway);
		  	app_logs.info("Creating Payment Gateway : " + paymentGateway);
		  	msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
		  	msObj.createNewMerchantService(driver, test_steps, payGatewayAccountName, accountNumber, accountDesc, accountStatus, 
		  			isCardPresent, isCardNotPresent, isEcommerce, isRequireCVVForAll, paymentGateways[1],
		  			mode, accountIDs[1], subAccountId, merchantPins[1],
		  			storeIds[1], urls[1], tokenIds[1], username, password,
		  			restAPIKey, hituserId, hitKey, transactionKeyCode, associateProperties);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Gateway Moneris - Create Corporate Account Reservation & Make Refund in Guest Folio & Account Folio		
		try
		{
			navigation.ReservationV2_Backward(driver);
			test_steps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");
			resV2.basicSearch_WithReservationNo(driver, ReservationNumber, true);
			
			//Navigate to Guest Folio >> Make Refund using Corporate Account >> Verify Refund in Folio & History Logs			
			resV2.click_Folio(driver, test_steps);
			folioNewobj.clickRefundButtonFromFolioTab(driver, test_steps);
			String corpAccNamenNumber = CorpAccountName+" ("+CorpAccountNumber+")";
			test_steps.add("Corporate Account Name & Number : "+corpAccNamenNumber);
			folioNewobj.refundPaymentinGuestFolio(driver, test_steps, paymentAmounts[1], 
					"Corporate Account", corpAccNamenNumber, "", "", "");
			String amount = Utility.convertDecimalFormat(paymentAmounts[1]);
			amount = "-$" + amount + "";
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), accountType,
					corpAccNamenNumber, amount, "", "", "", "", "");
			
			test_steps.add("=============Navigate to History Log & Verify History Log For Refund using Corporate Account===========");
			app_logs.info("==========Navigate to History Log & Verify History Log For Refund using Corporate Account===========");			
			resV2.switchHistoryTab(driver, test_steps);			
			//resV2.verifyHistoryLogForDifferentPayments(driver, test_steps, paymentAmounts[1], payByCorpAcc, paymentMethod);			
			String payByCorpAcc="Account - "+CorpAccountName+" ("+CorpAccountNumber+")";
			amount = Utility.convertDecimalFormat(paymentAmounts[1]);
			amount = "$" + amount + "";			
			resV2.enterTextToSearchHistory(driver, "Refunded payment for "+amount+" using "+payByCorpAcc, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			driver.navigate().refresh();	
		}catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Refund using Corporate Account in Guest Folio", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Refund using Corporate Account in Guest Folio", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Refund in Corporate Account Folio using Account As Payment Method
		try
		{
			String corpAccNamenNumber = CorpAccountName+" ("+CorpAccountNumber+")";
			resV2.click_Folio(driver, test_steps);
			test_steps.add("=============Navigate to Corporate Account Folio ===========");
			app_logs.info("==========Navigate to Corporate Account Folio ===========");
			folioNewobj.moveToFolio(driver, test_steps, folioName);
			folioNewobj.clickRefundButtonFromFolioTab(driver, test_steps);			
			folioNewobj.refundPaymentinGuestFolio(driver, test_steps, paymentAmounts[1], 
					"Corporate Account", corpAccNamenNumber, "", "", "");
			String amount = Utility.convertDecimalFormat(paymentAmounts[1]);
			amount = "-$" + amount + "";
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), accountType,
					corpAccNamenNumber, amount, "", "", "", "", "");						
			resV2.switchHistoryTab(driver, test_steps);
			//resV2.verifyHistoryLogForDifferentPayments(driver, test_steps, paymentAmounts[1], payByCorpAcc, paymentMethod);			
			String payByCorpAcc="Account - "+CorpAccountName+" ("+CorpAccountNumber+")";
			amount = Utility.convertDecimalFormat(paymentAmounts[1]);
			amount = "$" + amount + "";			
			resV2.enterTextToSearchHistory(driver, "Refunded payment for "+amount+" using "+payByCorpAcc, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);			
		}
		catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account Reservation", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Perform Refund using Moneris Method on Corporate Account Reservation", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		//Add TestRail Result
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
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} 
		catch (Error e) 
		{
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
