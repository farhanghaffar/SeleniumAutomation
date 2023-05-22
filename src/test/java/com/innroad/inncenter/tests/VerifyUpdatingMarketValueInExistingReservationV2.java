/*
 * This test script includes - 5 test cases
 * 847637|847849|847639|847850|847638
 * verify System & Custom Market segments loads on new reservation page >> Market Dropdown
 * select all system market field >> create reservation
 * create reservation with market & referral
 * edit reservation - update market/referral fields and save reservation
 */
package com.innroad.inncenter.tests;
import com.innroad.inncenter.testcore.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.ListManagement;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pages.RateGridPage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.webelements.Elements_ReservationV2;

public class VerifyUpdatingMarketValueInExistingReservationV2 extends TestCore
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
	Elements_ReservationV2 eleResv2 = new Elements_ReservationV2(driver);
	ArrayList<String> availableCustomMarketSegments = new ArrayList<String>();
	ArrayList<String> availableSystemMarketSegments = new ArrayList<String>();
	
	
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
		return Utility.getData("verifyUpdatingMarketValueInExis", excelRiddhi);
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
	public void verifyUpdatingMarketValueInExistingReservationV2(String TestCaseId, String TestCaseName,String RatePlanName,String marketSegment, 
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
		
		
		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			driver = getDriver();
//			loginResV2(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(excelRiddhi, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
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

		//Navigate to Setup >> Click on Room Classes >> Click on New Room Class Button 
		//>> Fill mandatory fields >> create new room class by clicking on Publish
		try
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
		
		//Create Rate Plan if it does not exists
		//Navigate to Inventory >> Rate Grid >> Add Rate Plan
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
		
		//If rate plan does not exists, then create new one
		if(!israteplanExist)
		{
			test_steps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan =======================");
			try 
			{
				Elements_RatesGrid element = new Elements_RatesGrid(driver);

				test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				
				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", test_steps);
				
				nightlyRate.verifyTitleSummaryValue(driver, "Rate Plan Type","Nightly rate plan", test_steps);
				test_steps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				
				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, RatePlanName, getTest_Steps);
				nightlyRate.enterRatePlanDescription(driver, FolioDisplayName, getTest_Steps);
				nightlyRate.enterRatePlanDescription(driver, Description, getTest_Steps);
				
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, test_steps);
				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, test_steps);
				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);

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

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						test_steps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), test_steps);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} 
				else 
				{
					Assert.assertTrue(false);
				}
			}
			catch(Error e)
			{
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			//Create Season for new rate plan 
			//Navigate to season tab, once all the fields under rate plan is added
			
			try 
			{
			  test_steps.add("=================== CREATE SEASON ======================");
			  app_logs.info("=================== CREATE SEASON ======================");
			  
			  nightlyRate.clickCreateSeason(driver, test_steps);
			  nightlyRate.createSeason(driver, test_steps, SeasonStartDate, SeasonEndDate,
			  SeasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
			  isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults, RatePerNight,
			  MaxAdults, MaxPersons, AdditionalAdultsPerNight, AdditionalChildPerNight,
			  isAddRoomClassInSeason, ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight,
			  ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons,
			  ExtraRoomClassAdditionalAdultsPerNight,
			  ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass,
			  isSerasonLevelRules, SeasonRuleSpecificRoomClasses, SeasonRuleType,
			  SeasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday,
			  isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
			  isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType,
			  SeasonPolicyValues, isSeasonPolicies);
			  
			  nightlyRate.clickCompleteChanges(driver, test_steps);
			  nightlyRate.clickSaveAsActive(driver, test_steps); Wait.wait30Second();
			  test_steps.add("=================== RATE PLAN CREATED ======================");
			  app_logs.info("=================== RATE PLAN CREATED ======================"); 
			  } 
			  catch (Exception e) 
			  { 
				  e.printStackTrace(); 
				  if(Utility.reTry.get(test_name) == Utility.count) 
				  { 
					  RetryFailedTestCases.count = Utility.reset_count; 
					  Utility.AddTest_IntoReport(test_name,test_description, test_catagory, test_steps); 
					  Utility.updateReport(e,"Failed", test_name, "RatesV2", driver); 
				  } 
				  else 
				  { 
					  Assert.assertTrue(false); 
				  }
			  } 
			  catch (Error e) 
			  { 
				  e.printStackTrace(); 
				  if (Utility.reTry.get(test_name) == Utility.count) 
				  { 
					  RetryFailedTestCases.count = Utility.reset_count;
					  Utility.AddTest_IntoReport(test_name, test_description, test_catagory,test_steps); 
					  Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
				  } 
				  else 
				  { 
					  Assert.assertTrue(false); 
				  } 
			  } 
		  }
		
		 /*
		 * Method block for getting system Market & referral values
		 * Create Custom Market Field
		 * Create Custom Referral
		 */
		
		 try 
		  { 
			  	navigation.ReservationV2_Backward(driver);
				
			  	app_logs.info("==========Navigate to Setup==========");
			  	test_steps.add("==========Navigate to Setup=========="); 
			  	
			  	navigation.Setup(driver);
			  	
			  	app_logs.info("==========Navigate to ListManagement==========");
			  	test_steps.add("==========Navigate to ListManagement==========");
			  
			  	navigation.ListManagemnet(driver);
			  	
			  	app_logs.info("======Get All available System Referrals=======");
				test_steps.add("======Get All available System Referrals=======");
				//Get list of all available "Referrals" (System+Custom Referrals)
				
				ArrayList<String> availableReferrals = new ArrayList<String>();
				availableReferrals   = listManagement.getActiveMarketSegmentsNames(driver);
				
				if(availableReferrals.size()>0)
				{ 
					Referral = availableReferrals.get(0).trim();
				}
				else
				{
					listManagement.SelectFilter(driver, "Referral", test_steps);
					if(!listManagement.searchListManagementItemByName(driver,Referral, "Referral", test_steps)) 
					{
						listManagement.NewItemCreation(driver, Referral,Referral, test_steps); 
						listManagement.SaveButtonClick(driver,test_steps); 
					}
				}
			  	
			  	//Navigate to List Management -> Market Segment HyperLink
			  	listManagement.ClickOnListManagementSubSection("Market Segment",driver,test_steps);
			  	app_logs.info("======Get All avalable System Market Segments=======");
				test_steps.add("======Get All avalable System Market Segments=======");
				
				//Get list of System "Market Segment" (System Market Segments)
				availableSystemMarketSegments = listManagement.getActiveSystemMarketSegmentNames(driver);
				

				if(availableSystemMarketSegments.size() > 0)
				{
					marketSegment = availableSystemMarketSegments.get(0).trim();
				}
				else
				{
					listManagement.SelectFilter(driver,"Market Segment", test_steps);
					if(!listManagement.searchListManagementItemByName(driver,marketSegment, "Market Segment", test_steps)) 
					{
						listManagement.NewItemCreation(driver, marketSegment,marketSegment, test_steps); 
						listManagement.SaveButtonClick(driver,test_steps); 
					}
				}
				
				
				//Create Market Segment - it will be used to modify market segment in existing reservation
				modifyMarketSegment = "custom market"+marketSegment;
				app_logs.info("======Create Custom Market Field=======");
				test_steps.add("======Create Custom Market Field=======");
				
				listManagement.SelectFilter(driver,"Market Segment", test_steps);
				if(!listManagement.searchListManagementItemByName(driver,modifyMarketSegment, "Market Segment", test_steps)) 
				{
					listManagement.NewItemCreation(driver, modifyMarketSegment,modifyMarketSegment, test_steps); 
					listManagement.SaveButtonClick(driver,test_steps); 
				}
				
				
				// Get List of all available "Custom Market Segment" From Setup >> List Management
				app_logs.info("======Get All avalable Custom Market Segments=======");
				test_steps.add("======Get All avalable Custom Market Segments=======");
				
				availableCustomMarketSegments = listManagement.getActiveCustomMarketSegmentNames(driver);
			  
				app_logs.info("======Create Custom Referral Field=======");
				test_steps.add("======Create Custom Referral Field=======");
				
				//Create Custom Referral Field - It will be used to modify referral in existing reservation
				modifyReferralField = "custom referral"+Referral;
				listManagement.SelectFilter(driver,"Referral", test_steps);
				if(!listManagement.searchListManagementItemByName(driver, modifyReferralField,"Referral", test_steps))
				{ 
					listManagement.NewItemCreation(driver, modifyReferralField,modifyReferralField, test_steps); 
					listManagement.SaveButtonClick(driver, test_steps); 
				}
				
				navigation.Reservation_Backward(driver);
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
		 
		  
		//Create Reservation
		String salutation = "Ms.";
		String guestFirstName = "Riddhi";
		String guestLastName = "S_"+Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		String phoneNumber = "9876543444";
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
		//	String paymentMethod = "VISA";
		//	String cardNumber = "5454545454545454";
		//	String nameOnCard = "autovisa";
		//	String cardExpMonthAndYear = "12/23";
		//String referral = "Walk In";
		//String marketSegment = "GDS";
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
		
		String CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");// ratePlanData.get("SeasonStartDate");
		String CheckOutDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 1);
		
		String adult = "1";
		String children = "1";
		HashMap<String, String> data = null;
		ArrayList<String> marketSegListfromUI = new ArrayList<>();

	
		try 
		{
			 navigation.ReservationV2_Backward(driver);
			 //Compare All Custom Market Segment Values stored in availableCustomMarketsegment
			 //with new reservation UI - market segment dropdown values
			 //compareCustomMarketValuesFromUIwithavailablecustomMarket(CheckInDate, CheckOutDate, adult, children, 
			 //	RatePlanName, PromoCode, roomClassName, roomNumber);
			
			 marketSegListfromUI = res.getMarketValuesFromUI(driver, test_steps, "From Reservations page", 1, CheckInDate,CheckOutDate,
					  adult,children, RatePlanName, PromoCode, roomClassName, roomNumber);
			  
			test_steps.add("========================Get All Market Segments from UI - Market DropDown ======================== ");
			app_logs.info("========================Get All Market Segments from UI - Market DropDown ======================== ");
			
			//Compare SYstem Market Segment 
			for(int i=0; i < marketSegListfromUI.size(); i++)
			{
				test_steps.add("Market Segment From UI :: " + marketSegListfromUI.get(i).toString());
				app_logs.info("Market Segment From UI :: " + marketSegListfromUI.get(i).toString());
			
				for(int k=0; k<availableSystemMarketSegments.size();k++)
				{
					if( marketSegListfromUI.get(i).toString().compareTo(availableSystemMarketSegments.get(k).toString()) == 0 )
					{
						Utility.customAssert(marketSegListfromUI.get(i).toString(), availableSystemMarketSegments.get(k).toString(),
								true, true, "Matched System Market Segment", "Unmatched System Market Segment",true,test_steps);
						break;
					}
				}
			}
			
			//Compare Custom Market Segment
			for(int i=0; i < marketSegListfromUI.size(); i++)
			{
				test_steps.add("Market Segment From UI :: " + marketSegListfromUI.get(i).toString());
				app_logs.info("Market Segment From UI :: " + marketSegListfromUI.get(i).toString());
				
				
				for(int j=0; j< availableCustomMarketSegments.size(); j++)
				{
					
					if( marketSegListfromUI.get(i).toString().compareTo(availableCustomMarketSegments.get(j).toString()) == 0 )
					{
						Utility.customAssert(marketSegListfromUI.get(i).toString(), availableCustomMarketSegments.get(j).toString(),
								true, true, "Matched Custom Market Segment", "Unmatched Custom Market Segment",true,test_steps);
						break;
					}
				}
			}
			 
			test_steps.add("=============== All Custom Market segments loading in Market drop down for new Reservation ===============");
			app_logs.info("=============== All Custom Market segments loading in Market drop down for new Reservation ===============");
			
			resV2.closeAllOpenedReservations(driver);
			
			//Create new reservation with given Market & Referral Values
			  data = res.createReservationwithACF(driver, test_steps, "From Reservations page",
			  CheckInDate, CheckOutDate, adult, children, RatePlanName, "", roomClassName,
			  roomClassAB, salutation, guestFirstName, guestLastName, phoneNumber,
			  altenativePhone, email, address1, address2, address3, city, country, state,
			  postalCode, isGuesProfile, marketSegment, Referral, PaymentMethod,
			  CardNumber, NameOnCard, ExpiryDate, additionalGuests, roomNumber, quote,
			  noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
			  taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
			  accountFirstName, accountLastName, isTaxExempt, taxExemptID,false, false, false, false);
			 
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
		try
		{
			String ReservationNumber = data.get("ReservationNumber");
			resV2.closeAllOpenedReservations(driver);
			
			//Navigate to Dashboard >> Search by reservation no >> Open existing reservation
			resV2.basicSearch_WithReservationNo(driver, ReservationNumber, true);
			
			//Edit Marketing Info Icon and Modify Market
			resV2.clickOnEditMarketingInfoIcon(driver, test_steps);
			//modify market field
			resV2.modifyMarketFieldinMarketSegment(driver, modifyMarketSegment, getTest_Steps);
			
			String ActualMarketSegment = Utility.getELementText(driver.findElement(By.xpath(OR_Reservation.CP_NewReservation_Market)), false, "").trim();
			String ExpectedMarketSegment = modifyMarketSegment;
			
			test_steps.add("========================Actual Market Segment : ========================" + ActualMarketSegment);
			app_logs.info("========================Actual Market Segment : ========================" + ActualMarketSegment);
			
			test_steps.add("========================Expected Market Segment : ========================" + ExpectedMarketSegment);
			app_logs.info("========================Expected Market Segment : ========================" + ExpectedMarketSegment);
			
			test_steps.add("========================Actual Market Segment Matched with Expected one : ========================");
			app_logs.info("========================Actual Market Segment Matched with Expected one : ========================");
			
			//modify referral field
			resV2.modifyReferralFieldinMarketSegment(driver, modifyReferralField, getTest_Steps);
			resV2.clickOnSaveMarketingInfo(driver, getTest_Steps);
			
			String ActualReferral = Utility.getELementText(driver.findElement(By.xpath(OR_Reservation.CP_NewReservation_Referral)), false, "").trim();
			String ExpectedReferral = modifyReferralField; 
			test_steps.add("========================Actual Referral : ========================" + ActualReferral);
			app_logs.info("========================Actual Referral : ========================" + ActualReferral);
			
			test_steps.add("========================Expected Referral Segment : ========================" + ExpectedReferral);
			app_logs.info("========================Expected Referral Segment : ========================" + ExpectedReferral);
			
			test_steps.add("========================Actual Referral Matched with Expected one : ========================");
			app_logs.info("========================Actual Referral Matched with Expected one : ========================");
			
			Assert.assertEquals(ActualMarketSegment, modifyMarketSegment);
			Assert.assertEquals(ActualReferral, ExpectedReferral);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		//Update TestCase result in testrail
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
	
	//@Test (alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException 
	{
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}
}