package com.innroad.inncenter.tests;
import com.innroad.inncenter.testcore.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.ListManagement;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.pages.RateGridPage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClass;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.webelements.Elements_ReservationV2;
import com.innroad.inncenter.webelements.Elements_SetUp_Properties;

public class VerifyMarketSegmentShowsAllCustomMarketFields extends TestCore
{
	public WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	String modifyReferralField = "";
	String modifyMarketSegment = "";
	
	Navigation navigation = new Navigation();
	ReservationV2 resV2 = new ReservationV2();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	ListManagement listManagement = new ListManagement();
	ReservationV2 res = new ReservationV2();
	Policies pol = new Policies();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Elements_NewRoomClassPage roomClassEle = new Elements_NewRoomClassPage(driver);
	NewRoomClassesV2 roomClassObj = new NewRoomClassesV2();
	Elements_ReservationV2 eleResv2 = new Elements_ReservationV2(driver);
	
	@BeforeTest(alwaysRun = true) 
	public void checkRunMode() 
	{ 
		String test_name = this.getClass().getSimpleName().trim(); 
		app_logs.info("Verifying Test case "+ test_name.toUpperCase() + " is Skipped or not");
		if(!Utility.isExecutable(test_name, excelRiddhi)) throw new
			SkipException("Skiping the test - " + test_name); 
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyMarketSegmentShowsAllCust", excelRiddhi);
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
	boolean isRequiredMarketSegment = false;
	boolean isRequiredReferral = false;
	
	@Test(dataProvider = "getData")
	public void verifyMarketSegmentShowsAllCustomMarketFields(String TestCaseId, String TestCaseName,String RatePlanName,String marketSegment, 
			String Referral, String PaymentMethod, String CardNumber, String NameOnCard, String ExpiryDate) throws Exception 
	{

		Utility.initializeTestCase(TestCaseId, caseId, statusCode, comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		
		try 
		{
			if (!Utility.insertTestName.containsKey(test_name)) 
			{
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else 
			{
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(excelRiddhi, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} 
		catch (Exception e) 
		{
			if (Utility.reTry.get(test_name) == Utility.count) 
			{
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} 
			else 
			{
				Assert.assertTrue(false);
			}

		} 
		catch (Error e) 
		{
			if (Utility.reTry.get(test_name) == Utility.count) 
			{
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

		//Get Rate Plan Fields From excel sheet via Rateplandata arraylist and assign them to local variables
		//String ratePlanName = ratePlanData.get("ratePlanName");
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
		//ArrayList<String> availableReferrals = new ArrayList<String>();
		ArrayList<String> availableCustomMarketSegments = new ArrayList<String>();
		
		/*
		 * Navigate to Setup >> ListManagement
		 * get all system market fields & get all system referral fields
		 */
		try
		{
			app_logs.info("==========Navigate to Setup==========");
		  	test_steps.add("==========Navigate to Setup==========");
		  	
		  	navigation.setup(driver);
		  	app_logs.info("==========Navigate to ListManagement==========");
		  	test_steps.add("==========Navigate to ListManagement==========");
		  	
			navigation.ListManagemnet(driver);
			
			listManagement.ClickOnListManagementSubSection("Market Segment",driver,test_steps);
		  	app_logs.info("======Get All avalable Custom Market Segments=======");
			test_steps.add("======Get All avalable Custom Market Segments=======");
			availableCustomMarketSegments   = listManagement.getActiveCustomMarketSegmentNames(driver);
			
			/*if(availableCustomMarketSegments.size() == 0)	
			{
				listManagement.SelectFilter(driver,"Market Segment", test_steps);
				if(!listManagement.searchListManagementItemByName(driver,marketSegment , "Market Segment", test_steps)) 
				{
					listManagement.NewItemCreation(driver, marketSegment, marketSegment, test_steps); 
					listManagement.SaveButtonClick(driver,test_steps); 
				}
			}*/
			
		}
		catch(Exception e)
		{
			if (Utility.reTry.get(test_name) == Utility.count) 
			{
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Get System/Custom Market/Referral Fields", test_name, "Setup>>List Management", driver);
			} 
			else 
			{ 
				Assert.assertTrue(false); 
			} 
		}
		
		try 
		{
			navigation.ReservationV2_Backward(driver);
			/*
			 * HashMap<String, String> data = new HashMap<>(); Account account = new
			 * Account(); Tapechart tc = new Tapechart(); Navigation nav = new Navigation();
			 * Groups group = new Groups(); ArrayList<String> roomNumbersProvided =
			 * Utility.splitInputData(roomNumber); ArrayList<String> roomNumbers = new
			 * ArrayList<>(); ArrayList<String> roomClasses =
			 * Utility.splitInputData(roomClass); Elements_ReservationV2 res = new
			 * Elements_ReservationV2(driver); int noOfRooms =
			 * guestFirstName.split("\\|").length; boolean missingMandatoryFields = false;
			 */
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) 
			{
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} 
			else 
			{
				Assert.assertTrue(false);
			}
		} 
		catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
}
