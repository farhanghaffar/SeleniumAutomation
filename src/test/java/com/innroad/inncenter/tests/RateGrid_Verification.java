package com.innroad.inncenter.tests;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.properties.OR_RateGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class RateGrid_Verification extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	ArrayList<String> getRatePlanNames = new ArrayList<>();
	ArrayList<String> activeRatePlanNames = new ArrayList<>();
	ArrayList<String> derivedRatePlanNames = new ArrayList<>();
	ArrayList<String> inactiveRatePlanNames = new ArrayList<>();
	ArrayList<String> ratePlanColor = new ArrayList<>();
	ArrayList<String> roomClass = new ArrayList<>();
	ArrayList<String> channelName= new ArrayList<>();
	ArrayList<String> ruleHeaders= new ArrayList<>();
	ArrayList<String> ruleLabels= new ArrayList<>();
	ArrayList<String> ruleImages= new ArrayList<>();
	ArrayList<String> ruleIndicationLabels= new ArrayList<>();
	ArrayList<String> bestRates= new ArrayList<>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	RoomClass roomClass1 = new RoomClass();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid= new RatesGrid();
	NightlyRate nightltRate= new NightlyRate();
	String testName= this.getClass().getSimpleName().trim();
	String ratePlanType=null, bestAvailable=null, expandRoomClass=null;
	int size=0;
	String dayNum="";
	String weekDay="";
	String  roomClass2=null, oldRateIs=null, newRateIs=null, seasonStartDate=null, seasonEndDate=null , testCase=null;;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
		
	
		
	}
	@Test(dataProvider = "getFinalData", groups = "RateGrid")
	public void rateGridVerification(String activeRatePlanColor,String derivedRatePlanColor,String inactiveRatePlanColor,String delim,String planType,String ratePlanName,String folioDisplayName, String description, String channels, 
			String roomClasses,String isRatePlanRistrictionReq,String ristrictionType, String isMinStay, String minNights, String isMaxStay, String maxNights, 
			String isMoreThanDaysReq, String moreThanDaysCount, String isWithInDaysReq, String withInDaysCount,String promoCode, 
			String seasonName, 
			String isMonDay, String isTueDay,String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay, 
			String isAdditionalChargesForChildrenAdults,String ratePerNight,String maxAdults, String maxPersons, String additionalAdultsPerNight, String additionalChildPerNight, 
			String isAddRoomClassInSeason, String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults, String extraRoomClassMaxPersons, 
			String extraRoomClassAdditionalAdultsPerNight, String extraRoomClassAdditionalChildPerNight,String isSerasonLevelRules, String isAssignRulesByRoomClass, String seasonRuleSpecificRoomClasses, 
			String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, 
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday, String newNight,String rate,String newAdult,String seasonPolicyType, String seasonPolicyValues,String isSeasonPolicies, String manualRatePLanName)
	{
		
		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1685' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1685</a>";
		test_catagory = "RateGrid";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		NightlyRate nightlyRate = new NightlyRate();
		
		boolean isExists=false;
		//Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
					 
			driver = getDriver();
			loginRateV2(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
			
		} catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, testSteps);
			
		}
		try
		{
			
			testCase="Get Active Room Class";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Get Active Room Classes============</b>");
			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");
			navigation.roomClass(driver);
			testSteps.add("Navigate Room Class");
			newRoomClass.selectStatus(driver, testSteps, "Active");
			getRoomClasses=newRoomClass.getAllActiveRoomClassNames(driver);
			testSteps.add((getRoomClasses.toString().replace("[","").replace("]","")));
			app_logs.info(getRoomClasses);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);

		}catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to Get Room Classes", "Room Class", "Room Class", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Get Room Classes", "Room Class", "Room Class", testName, test_description, test_catagory, testSteps);
			
		}
		
		//Navigate to Rate Grid
	try
		{
		//After Setup
			navigation.inventory_Backward_1(driver);
			//Direct
		//	navigation.inventory(driver);
			testSteps.add("Navigated to Inventory");
			navigation.ratesGrid(driver,testSteps);	
		}
		catch (Exception e) {			
				Utility.catchException(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
	try
	{
		
		seasonStartDate=Utility.getCurrentDate("dd/MM/yyyy");
		seasonEndDate=Utility.parseDate(Utility.getDatePast_FutureDate(6), "MM/dd/yyyy", "dd/MM/yyyy");
		app_logs.info(seasonStartDate);
		app_logs.info(seasonEndDate);
	 
//		Utility.rateplanName=manualRatePLanName;
		if(!Utility.validateString(Utility.rateplanName)) {
			
		testSteps.add("<b>============Create new Rate Plan============</b>");				
		Utility.DELIM = "\\"+delim;
		nightltRate.createNightlyRatePlan(driver, planType, ratePlanName, folioDisplayName, testStepsOne, description, channels, 
				roomClasses, isRatePlanRistrictionReq, ristrictionType, isMinStay, 
				minNights, isMaxStay, maxNights, isMoreThanDaysReq, moreThanDaysCount, isWithInDaysReq, withInDaysCount, 
				promoCode, seasonName,seasonStartDate,seasonEndDate ,isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, 
				isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight, 
				additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason, extraRoomClassRatePerNight, 
				extraRoomClassMaxAdults, extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight, 
				extraRoomClassAdditionalChildPerNight, isSerasonLevelRules,isAssignRulesByRoomClass, seasonRuleSpecificRoomClasses, 
				seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, 
				isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday,seasonPolicyType,  seasonPolicyValues, isSeasonPolicies);
		Wait.waitUntilPageLoadNotCompleted(driver, 60);
		testSteps.add("Rate Plan Created Successfully : <b>"+Utility.rateplanName+" </b>");		
		rateGrid.closeRatePlan(driver, Utility.rateplanName,testSteps );	
		nightlyRate.clickYesButton(driver, testSteps);
		Wait.waitUntilPageLoadNotCompleted(driver, 50);
		isExists=true;
		}else {
		testSteps.add("Rate Plan Already Exist : <b>"+Utility.rateplanName+" </b>");		
		}
			
	}catch (Exception e) {			
		Utility.catchException(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
	} catch (Error e) {
		Utility.catchError(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		
}
	// Verify Edit icon Delete Icon and Get Default RatePlan and Total RatePlan
			try
			{
				testCase="RateGrid Verify Edit,Delete,Default Rate Plan, Total Rate Plan";
				app_logs.info("RateGrid Verify Edit,Delete,Default Rate Plan, Total Rate Plan");
				if (!Utility.insertTestName.containsKey(testCase)) {
					Utility.insertTestName.put(testCase, testCase);
					Utility.reTry.put(testCase, 0);
				} else {
					Utility.reTry.replace(testCase, 1);
				}
				
			testSteps.add("<b>============Verify Edit Icon of Rate Plan ============</b>");
			Wait.WaitForElement(driver, OR_RateGrid.rateEditIcon);
			rateGrid.verifyEditIcon(driver, testSteps);
			testSteps.add("<b>============Verify Delete Icon of Rate Plan ============</b>");
			rateGrid.verifyDeleteIcon(driver, testSteps);
			testSteps.add("<b>============Get Default Rate Plan ============</b>");
			rateGrid.getDefaultRatePlan(driver, testSteps);
			testSteps.add("<b>============Total Rate Plan============</b>");
			rateGrid.clickRatePlanArrow(driver, testSteps);
			size=rateGrid.sizeOfAllRatePlan(driver);
			testSteps.add("Total Rate Plans: <b>"+size+ "</b>");	
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			}
			catch (Exception e) {			
				Utility.catchException(driver, e, "Verify Edit Icon , Delete Icon, Get Default RatePlan and Total Rateplan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Verify Edit Icon , Delete Icon, Get Default RatePlan and Total Rateplan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
				
		}
			// Get Active, Inactive, Derived RatePlan and Get Color for Same
		try
		{
			testCase="RateGrid Active Rate Plan";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Get All Active Rate Plan List============</b>");
			activeRatePlanNames=rateGrid.getRatePlanNamesCategoryWise(driver, activeRatePlanColor);
			testSteps.add("Get List of All Active Rate Plans: <b>"+ (activeRatePlanNames.toString().replace("[","").replace("]",""))+ "</b>");

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			
			testCase="RateGrid Derived Rate Plan";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Get All Derived Rate Plan List============</b>");
			derivedRatePlanNames=rateGrid.getRatePlanNamesCategoryWise(driver, derivedRatePlanColor);
			testSteps.add("Get List of All Derived Rate Plans: <b>"+ (derivedRatePlanNames.toString().replace("[","").replace("]",""))+ "</b>");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			
			
			testCase="RateGrid Inactive Rate Plan";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Get All Inactive Rate Plan List============</b>");
			inactiveRatePlanNames=rateGrid.getRatePlanNamesCategoryWise(driver, inactiveRatePlanColor);
			testSteps.add("Get List of All Inactive Rate Plans: <b>"+ (inactiveRatePlanNames.toString().replace("[","").replace("]",""))+ "</b>");
				
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			
			testCase="RateGrid Rate Plan Color";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			testSteps.add("<b>============Get All Active Rate Plan Color============</b>");
			String activeRatePlanColorName=rateGrid.getRatePlanColorCategoryWise(driver, activeRatePlanNames, activeRatePlanColor);
			testSteps.add(" Color of All Active Rate Plans: <b>"+activeRatePlanColorName+ "</b>");
			
			testSteps.add("<b>============Get All Derived Rate Plan Color============</b>");
			String deriverRatePlanColorName=rateGrid.getRatePlanColorCategoryWise(driver, derivedRatePlanNames, derivedRatePlanColor);
			testSteps.add(" Color of All Derived Rate Plans: <b>"+deriverRatePlanColorName+ "</b>");
			
			testSteps.add("<b>============Get All Inactive Rate Plan Color============</b>");
			String inactiveRatePlanColorName=rateGrid.getRatePlanColorCategoryWise(driver, inactiveRatePlanNames, inactiveRatePlanColor);
			testSteps.add(" Color of All Inactive Rate Plans: <b>"+inactiveRatePlanColorName+ "</b>");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Inactive, Active, Derived RatePlan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Inactive, Active, Derived RatePlan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		
		// Select RatePlan/Edit RatePLan and Select via Search
		try
		{
			
			testCase="RateGrid Select Rate Plan";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Select Rate Plan from Drop Down Box============</b>");
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, Utility.rateplanName);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			testCase="Edit Rate Plan";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Edit Rate Plan============</b>");
			rateGrid.clickEditIcon(driver, testSteps);
			testSteps.add("<b>============Verified Rate Plan in Edit Mode============</b>");
			rateGrid.verifyRatePlaninEditMode(driver, testSteps,  Utility.rateplanName);
			//rateGrid.closeRatePlan(driver, testSteps, Utility.rateplanName);	
			rateGrid.closeRatePlan(driver, Utility.rateplanName,testSteps );	
			nightlyRate.clickYesButton(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			
			testCase="Search and Select Rate Plan";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Search and Select Rate Plan from Drop Down Box============</b>");
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.searchRateAndSelectRate(driver, testSteps,  Utility.rateplanName);	
			Wait.waitUntilPageLoadNotCompleted(driver, 60);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Select RatePlan and Edit RatePlan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Select RatePlan and Edit RatePlan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		
		// Get RatePlan Description and Condition
		try
		{
		testCase="Rate Grid Rate Plan Description";
		if (!Utility.insertTestName.containsKey(testCase)) {
			Utility.insertTestName.put(testCase, testCase);
			Utility.reTry.put(testCase, 0);
		} else {
			Utility.reTry.replace(testCase, 1);
		}
			
			testSteps.add("<b>============Get Rate Plan Description============</b>");
			ratePlanType=rateGrid.getRatePlanDescription(driver, testSteps);
			rateGrid.getRateConditionsDescription(driver, testSteps);			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		
			
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Description and Condition of RatePlan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Description and Condition of RatePlan", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		// Collapse and Expand RatePlan and Get Best Available
		try
		{
			testCase="Rate Grid Rate Plan Collapse/Expand";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>============Collapse Rate Plan ============</b>");
			rateGrid.clickCollapseIconOfRatePlan(driver, testSteps);
			testSteps.add("<b>============Get Best Available Rates And Total Room Class ============</b>");
			rateGrid.getRegularRates(driver, testSteps);
			rateGrid.getBestAvailableRoomClass(driver, testSteps);
			testSteps.add("<b>============Expand Rate Plan ============</b>");
			rateGrid.clickExpandIconOfRatePlan(driver, testSteps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Collapse and Expand RatePlan and Get Best Available", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Collapse and Expand RatePlan and Get Best Available", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		
		// Get Room Class size and All Room Classes Names and Verify Room Active Room Class and Availability Data 
		try
		{
			testCase="Rate Grid Room Class Verification";
			app_logs.info("Rate Grid Room Class Verification");
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			testSteps.add("============Get Room Class Size of Rate Plan <b>"+Utility.rateplanName+"============</b>");
			int roomClassSize=rateGrid.getSizeOfRateGridRoomClass(driver);
			testSteps.add("Total Room Classes <b>"+ roomClassSize+ "</b>");
			testSteps.add("============Get Room Class List of Rate Plan <b>"+Utility.rateplanName+"============</b>");
			roomClass=rateGrid.getrateGridRoomClass(driver);
			app_logs.info(roomClass);
			testSteps.add("<b>"+ (roomClass.toString().replace("[","").replace("]",""))+ "</b>");
			
			testSteps.add("<b>======Verify  All Active Room Classes========</b>");
			rateGrid.verifyRoomClasses(driver, getRoomClasses, testSteps);
			
			testSteps.add("<b>======Verify Sorting of  Room Classes========</b>");
			rateGrid.verifyRoomClassesIsSortedOrNot(driver, testSteps, Utility.rateplanName,getRoomClasses);
			
			
			testSteps.add("<b>======Get Room Class Value========</b>");
			for (int i = 0; i < roomClass.size(); i++) {
				ArrayList<String> data=rateGrid.getRoomClassValues(driver, roomClass.get(i));
				String classData=roomClass.get(i)+"--: <b>"+ (data.toString().replace("[","").replace("]","")+ "</b>");				
				testSteps.add(classData);
				app_logs.info(classData);
			}
			
			testSteps.add("<b>======Get Room Class Availability Value ========</b>");
			for (int i = 0; i < roomClass.size(); i++) {
				ArrayList <String> data=rateGrid.getRoomClassAvailibilityDataValues(driver, roomClass.get(i));
				String availabilityData=roomClass.get(i)+" Availability--: <b>"+(data.toString().replace("[","").replace("]","")+ "</b>");				
				testSteps.add(availabilityData);
				app_logs.info(availabilityData);
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Get Room Class size and All Room Classes Names and Verify Room Active Room Class and Availability Data", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Room Class size and All Room Classes Names and Verify Room Active Room Class and Availability Data", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		// Get Room Class  and Channel Data
		try
		{
			testCase="Rate Grid Room Class Data";
			app_logs.info("Rate Grid Room Class Data");
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			for (int i = 0; i < roomClass.size(); i++)
			{
				testSteps.add("<b>============"+roomClass.get(i)+" =========</b>");
				rateGrid.expandRoomClass(driver, testSteps, roomClass.get(i));
				channelName=rateGrid.getChannelofAllRoomClass(driver, testSteps, roomClass.get(i));
				testSteps.add("Chennals Are--:<b> "+channelName.toString().replace("[","").replace("]","")+"</b>");
				for(int j=0;j<channelName.size();j++)
				{
					ArrayList <String> channelData=rateGrid.getChannelDataValues(driver,  roomClass.get(i), channelName.get(j));
					testSteps.add(channelName.get(j)+ " Data  <b>--: "+(channelData.toString().replace("[","").replace("]","")+"</b>"));
						rateGrid.expandChannel(driver, testSteps, roomClass.get(i), channelName.get(j));
					ruleHeaders.removeAll(ruleHeaders);
					ruleHeaders= rateGrid.getRuleHeader(driver, testSteps, roomClass.get(i), channelName.get(j));
					testSteps.add("Rule Headerds are: <b>"+(ruleHeaders.toString().replace("[","").replace("]",""))+ "</b>");
					for(int k=0;k<ruleHeaders.size();k++)
					{
						if(ruleHeaders.get(k).equalsIgnoreCase(ratesConfig.getProperty("minStay")))
						{
							ArrayList<String> value=rateGrid.getRuleDataValuesForMinStay(driver, roomClass.get(i), channelName.get(j), ruleHeaders.get(k));
							testSteps.add("<b>"+ruleHeaders.get(k) + " </b>Value is -- <b>" +(value.toString().replace("[","").replace("]",""))+"</b>");
					
						}
						else 
						{
							ArrayList<String> value=rateGrid.getRuleDataValueForCheckInCheckOut(driver, roomClass.get(i), channelName.get(j), ruleHeaders.get(k));
							testSteps.add("<b>"+ruleHeaders.get(k) + " </b>Value is -- <b>" +(value.toString().replace("[","").replace("]",""))+"</b>");
					
						}
					
					}
					
					rateGrid.collapseChannel(driver, testSteps, roomClass.get(i), channelName.get(j));
				}
				rateGrid.collapseRoomClass(driver, testSteps, roomClass.get(i));
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Get Room Class  and Channel Data", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Room Class  and Channel Data", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		
		// Get Rule Applied on Which Room Classes  and get override and Non data of Room Class
		try
		{
			testCase="Rate Plan Rule Applied";
			app_logs.info("Rate Plan Rule Applied");
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			for (int i = 0; i < roomClass.size(); i++)
			{
				rateGrid.getRuleAppliedForRoomClass(driver, roomClass.get(i), testSteps);
			
				
			}
			for (int i = 0; i < roomClass.size(); i++)
			{
				rateGrid.getOverRideAndNonOverrideValueForRoomClass(driver, roomClass.get(i), testSteps);
			}
			
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Get Rule Applied on Which Room Classes  and get override and Non data of Room Class", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Rule Applied on Which Room Classes  and get override and Non data of Room Class", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		// Get Color and Verify Room Class Color
		try
		{
			testSteps.add("<b>======Get and Verify Color of All Room Classes========</b>");
			app_logs.info("======Get and Verify Color of All Room Classes========");
			String roomClassColorName=null;
			for(String str:roomClass)
			{
				roomClassColorName= rateGrid.getRoomClassColor(driver, str);
				rateGrid.verifyRoomClassColor(driver, str, roomClassColorName);
			}
			testSteps.add("<b> Color of All the Room Class is: "+ roomClassColorName+ "</b>");	
	
			
			for(String str:roomClass)
				{
				rateGrid.getRateColor(driver, testSteps, str);
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Get Color and Verify Room Class Color", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Color and Verify Room Class Color", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		// Update Rate  and Verify Changed Information of Rate and verify color of Rate
		try
		{
			testCase="Update Rule And Verify OverRide";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			ArrayList<String> updateRate=new ArrayList<String>();
			for(String str:roomClass)
			{
				roomClass2=str;
			updateRate=rateGrid.updateRate(driver, testSteps, str);
			app_logs.info(updateRate);
			oldRateIs=updateRate.get(0);
			newRateIs=updateRate.get(1);
			if(newRateIs.contains("."))
			{
				if(Double.parseDouble(newRateIs)>0.00)
				{
					break;
				}
			}
			else
			{
				if(Integer.parseInt(newRateIs)>0)
				{
					break;
				}
			}
			
			}
			
			testSteps.add("<b>======Get OverRide Data========</b>");
			rateGrid.hoverRuleRate(driver, roomClass2, newRateIs,testSteps);
			rateGrid.getRuleDate(driver, testSteps) ; 
			rateGrid.getRuleRate(driver, testSteps);
			 ruleLabels=rateGrid.getRulesLabels(driver);
			 ruleImages=rateGrid.getRuleImages(driver);
			 
			 for(int i=0;i< ruleImages.size();i++)
			 {
				String  label=rateGrid.verifyRulesLabels(driver, ruleLabels.get(i));
				
				 if(ruleLabels.get(i).contains(ratesConfig.getProperty("updateBY")))
				 {
					
					String updateBy= rateGrid.getOverRideValues(driver, ratesConfig.getProperty("updateBY"));
					 testSteps.add("<img src='"+ruleImages.get(i)+"' width='15' height='15'>"+"<b> "+label +" "+ updateBy+" </b>");
					 rateGrid.verifyChangeValue(driver, updateBy);
					 testSteps.add("Verified "+"<b> "+label +" "+ updateBy+" </b>");
				 } else if(ruleLabels.get(i).contains(ratesConfig.getProperty("updateOn")))
				 { 	 
					 String updateOn= rateGrid.getOverRideValues(driver, ratesConfig.getProperty("updateOn"));
					 testSteps.add("<img src='"+ruleImages.get(i)+"' width='15' height='15'>"+" <b> "+label +" "+ updateOn+" </b>");
					 rateGrid.verifyChangeValue(driver, updateOn);
					 testSteps.add("Verified "+"<b> "+label +" "+ updateOn+" </b>");
				 }else if(ruleLabels.get(i).contains(ratesConfig.getProperty("previousPrice")))					
				 {
					
					 String changeValue= rateGrid.getOverRideValues(driver, ratesConfig.getProperty("previousPrice"));
				 	 testSteps.add("<img src='"+ruleImages.get(i)+"' width='15' height='15'>"+"<b> "+label +" "+ changeValue+" </b>");
				 	 rateGrid.verifyPreviousPrice(driver, changeValue);
				 	testSteps.add("Verified "+"<b> "+label +" "+ changeValue+" </b>");
				 }
				 else
				 {
					 testSteps.add("<img src='"+ruleImages.get(i)+"' width='15' height='15'>"+" <b> "+label+" </b>");
				 }
				 
			 }
			
			testSteps.add("<b>======Verify Color of Override Rate========</b>");
			String colorName=rateGrid.getOverrideRateColor(driver, testSteps, roomClass2, newRateIs);
			rateGrid.verifyOverrideRateColor(driver, testSteps, roomClass2, newRateIs,colorName);
			
			
			
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Update Rate  and Verify Changed Information of Rate and verify color of Rate", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Rate  and Verify Changed Information of Rate and verify color of Rate", "RateGrid", "RateGrid", testCase, test_description, test_catagory, testSteps);
			
	}
		
		// Update Rule and get color of Rule and get updated rule
		try
		{
			
			testSteps.add("<b>======Update Rule and Verify Rule Color========</b>");
			
			for (int i = 0; i < roomClass.size(); i++)
			{
				rateGrid.expandRoomClass(driver, testSteps, roomClass.get(i));
				channelName=rateGrid.getChannelofAllRoomClass(driver, testSteps, roomClass.get(i));
				for(int j=0;j<channelName.size();j++)
				{
					rateGrid.expandChannel(driver, testSteps, roomClass.get(i), channelName.get(j));
					boolean isExist=rateGrid.getUpdateBoolValue(driver, roomClass.get(i), channelName.get(j), ratesConfig.getProperty("minStay"));
					if(isExist)
					{
					rateGrid.updateRuleForMinStay(driver, testSteps, roomClass.get(i), channelName.get(j), ratesConfig.getProperty("minStay"));
					rateGrid.updateRuleForNoCheckInAndOut(driver, testSteps,roomClass.get(i), channelName.get(j), ratesConfig.getProperty("checkinRule"));
					rateGrid.getCheckInAndCheckoutColor(driver, testSteps, roomClass.get(i), channelName.get(j), ratesConfig.getProperty("checkinRule"));				
					rateGrid.updateRuleForNoCheckInAndOut(driver, testSteps,roomClass.get(i), channelName.get(j), ratesConfig.getProperty("checkoutRule"));
					rateGrid.getCheckInAndCheckoutColor(driver, testSteps, roomClass.get(i), channelName.get(j), ratesConfig.getProperty("checkoutRule"));
					break;
					}
				}	
				break;
				
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Update Rule and get color of Rule and get updated rule", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Rule and get color of Rule and get updated rule", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			
	}
		
		// Removed OverRide, Delete RatePlan and Verify deleted ratePlan
		try
		{
			testCase="Removed Override and Verify";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			 testSteps.add("<b>======Verify Removed OverRide========</b>");
			 rateGrid.removeOverRide(driver, testSteps, roomClass2, newRateIs, oldRateIs);
/*			 if(isExists) {
			 testSteps.add("<b>======Delete Rate Plan========</b>");
			 rateGrid.clickDeleteIcon(driver, testSteps);
			 rateGrid.verifyDeletedMsg(driver, testSteps, ratesConfig.getProperty("deleteRatePlanMsg"));
			 rateGrid.clickDeleteButton(driver, testSteps);
			 rateGrid.clickRatePlanArrow(driver, testSteps);
			 ratePlanNames=rateGrid.getRatePlanNames(driver);
			 rateGrid.verifyDeletedRatePlan(driver, testSteps, Utility.rateplanName, ratePlanNames);
			 }  */
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Removed OverRide, Delete RatePlan and Verify deleted ratePlan", "RatePlanName", "RatePlanName", testCase, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Removed OverRide, Delete RatePlan and Verify deleted ratePlan", "RatePlanName", "RatePlanName", testCase, test_description, test_catagory, testSteps);
			
		}
	
	
	}

	@DataProvider
	public Object[][] getDataOne() {

		return Utility.getData("RateGridOverrideVerification", envLoginExcel);

	}

	public Object[][] getData() {

		return Utility.getData("RateGridVerification", envLoginExcel);

	}

		@DataProvider
		public Object[][] getFinalData()
		{
			 return Utility.combine(getData(),  getDataOne());
		} 
		
		 
		
		
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
