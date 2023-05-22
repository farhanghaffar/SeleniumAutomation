package com.innroad.inncenter.tests;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.innroad.inncenter.pageobjects.*;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.*;

import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyRulesInBulkUpdate extends TestCore {

	// Automation-1539
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> testName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	public static String testdescription = "";
	public static String testcatagory = "";

	@Test(dataProvider = "getData", groups = "RatesGrid")
	public void verifyRulesInBulkUpdate(String startDate, String endDate, String isSundayCheck, String isMondayCheck,
			String isTuesdayCheck, String isWednesdayCheck, String isThursdayCheck, String isFridayCheck, String isSaturdayCheck,
			String isTotalOccupancyOn, String totalOccupancyType, String totalOccupancyValue, String roomClassName, 
			String channel, String ratePlan, String isMinimumStayOn, String minimumStayValue, String isCheckInOn, String isNoCheckInChecked, String isCheckOutOn, String isNoCheckOutChecked,
			String rules, String delim ) throws Exception {

		String scriptName = "VerifyRulesInBulkUpdate";
		String testdescription = "Rates V2 - Rates Grid - Bulk Update popup - Rules functionality<br>";
				

		testcatagory = "RateGrid";
		testName.add(scriptName);
		testDescription.add(testdescription);
		testCategory.add(testcatagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		//RoomClass roomClass= new RoomClass();
		
		String timeZone = "America/New_York";
		
		HashMap<String, Boolean> dayMap = new HashMap<>();
		if(isMondayCheck.equalsIgnoreCase("Yes")){
			dayMap.put("Mon", true);
		}else{
			dayMap.put("Mon", false);
		}
		if(isTuesdayCheck.equalsIgnoreCase("Yes")){
			dayMap.put("Tue", true);
		}else{
			dayMap.put("Tue", false);
		}
		if(isWednesdayCheck.equalsIgnoreCase("Yes")){
			dayMap.put("Wed", true);
		}else{
			dayMap.put("Wed", false);
		}
		if(isThursdayCheck.equalsIgnoreCase("Yes")){
			dayMap.put("Thu", true);
		}else{
			dayMap.put("Thu", false);
		}
		if(isFridayCheck.equalsIgnoreCase("Yes")){
			dayMap.put("Fri", true);
		}else{
			dayMap.put("Fri", false);
		}
		if(isSaturdayCheck.equalsIgnoreCase("Yes")){
			dayMap.put("Sat", true);
		}else{
			dayMap.put("Sat", false);
		}
		if(isSundayCheck.equalsIgnoreCase("Yes")){
			dayMap.put("Sun", true);
		}else{
			dayMap.put("Sun", false);
		}
		Utility.DELIM = delim;
		app_logs.info("startDate: "+startDate);
		app_logs.info("endDate: "+endDate);
		try {
			if (!Utility.insertTestName.containsKey(scriptName)) {
				Utility.insertTestName.put(scriptName, scriptName);
				Utility.reTry.put(scriptName, 0);

			} else {
				Utility.reTry.replace(scriptName, 1);
			}
			driver = getDriver();
			try {
				loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				loginWPI(driver);
			}

			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "CP_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "CP_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		/*try {

			app_logs.info("==========STORE ALL ACTIVE ROOM CLASS==========");
			testSteps.add("==========STORE ALL ACTIVE ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.roomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

			List<String>[] arrayOfList = new List[3];
			arrayOfList = roomClass.getAllActiveRoomClasses(driver);
			getRoomClasses = (ArrayList<String>) arrayOfList[0];

			ArrayList<String> getRoomsNumber = new ArrayList<>();
			getRoomsNumber = (ArrayList<String>) arrayOfList[2];

			ArrayList<String> roomClassesAbbreviation = new ArrayList<>();
			roomClassesAbbreviation = (ArrayList<String>) arrayOfList[1];

			app_logs.info(getRoomClasses.size());
			testSteps.add("Get List of All RoomClasses: <b>" + getRoomClasses + "</b>");
			app_logs.info("roomClassesAbbreviation: " + roomClassesAbbreviation.size());
			app_logs.info("getRoomsNumber: " + getRoomsNumber.size());
			for (int i = 0; i < getRoomClasses.size(); i++) {
				System.out.println(
						getRoomClasses.get(i) + "  " + roomClassesAbbreviation.get(i) + "  " + getRoomsNumber.get(i));
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to store room class in arraylist", scriptName, "StoreRoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to store room class in arraylist", scriptName, "StoreRoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
*/

		
		try {
			navigation.Inventory(driver);
			//navigation.Inventory_Backward_1(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			
			getTestSteps.clear();
			getTestSteps = navigation.ratesGrid(driver);
			testSteps.addAll(getTestSteps);


		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to overview", scriptName, "NavigateOverview", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to overview", scriptName, "NavigateOverview", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Get Rate Plan Name and Color
		/*try
			{
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratePlanNames=ratesGrid.getRatePlanNames(driver);
				Collections.sort(ratePlanNames);
				testSteps.add("Get List of All Rate Plans: <b>"+ ratePlanNames + "</b>");		
				int size=ratesGrid.sizeOfAllRatePlan(driver);
				testSteps.add("Size of Rate Plans: <b>"+size+ "</b>");
				
			}catch (Exception e) {			
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Get Rate Plan Name", scriptName, "RatePlanName", driver);
				} catch (Error e) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Get Rate Plan Name", scriptName, "RatePlanName", driver);
					
			}
*/

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAvailabilityTab(driver);
			testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on availability tab", scriptName, "RateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on availability tab", scriptName, "RateGrid", driver);
			}
		}
		
		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectBulkUpdateOption(driver, rules);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rules popup", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rules popup", scriptName, "BulkUpdate", driver);
			}
		}
		try {
			
			testSteps.add("==========SELECT START DATE==========");
			app_logs.info("==========SELECT START DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectStartDate(driver, startDate);
			testSteps.addAll(getTestSteps);
		
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select start", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select start", scriptName, "BulkUpdate", driver);
			}
		}
	
		try{
			testSteps.add("==========SELECT END DATE==========");
			app_logs.info("==========SELECT END DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectEndDate(driver, endDate);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select end date", scriptName, "AvailabilityPopup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select end date", scriptName, "AvailabilityPopup", driver);
			}
		}
	

		try {
			app_logs.info("==========CHECKING/UNCHECKING DAYS==========");
			testSteps.add("==========CHECKING/UNCHECKING DAYS==========");
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Sun", isSundayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Mon", isMondayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Tue", isTuesdayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Wed", isWednesdayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Thu", isThursdayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Fri", isFridayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Sat", isSaturdayCheck);
			testSteps.addAll(getTestSteps);
					
			
			} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "BulkUpdate", driver);
			}
		}
		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickTotalOccupancy(driver, isTotalOccupancyOn);
			testSteps.addAll(getTestSteps);

			} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			
			if(isTotalOccupancyOn.equalsIgnoreCase("Yes")){
	
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectTotalOccupancyType(driver, totalOccupancyType);
				testSteps.addAll(getTestSteps);
			}

			} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			
			if(isTotalOccupancyOn.equalsIgnoreCase("Yes")){
	
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterOccupancyValue(driver, totalOccupancyValue);
				testSteps.addAll(getTestSteps);
			}

			} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "BulkUpdate", driver);
			}
		}
		
		try{
			app_logs.info("==========SELECTING RATE PLAN==========");
			testSteps.add("==========SELECTING RATE PLAN==========");


				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", ratePlan);
				testSteps.addAll(getTestSteps);

		} catch (Exception e) {
		if (Utility.reTry.get(scriptName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select rate plan", scriptName, "BulkUpdate", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(scriptName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select rate plan", scriptName, "BulkUpdate", driver);
		}
	}	
		try{
			app_logs.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClassName);
				testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
		if (Utility.reTry.get(scriptName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select room classes", scriptName, "BulkUpdate", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(scriptName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select room classes", scriptName, "BulkUpdate", driver);
		}
	}	

		try{
			app_logs.info("==========SELECTING SOURCE==========");
			testSteps.add("==========SELECTING SOURCE==========");
				
			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", channel);
			testSteps.addAll(getTestSteps);

	} catch (Exception e) {
		if (Utility.reTry.get(scriptName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select source", scriptName, "BulkUpdate", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(scriptName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select source", scriptName, "BulkUpdate", driver);
		}
	}	
		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, isMinimumStayOn);
			testSteps.addAll(getTestSteps);
				
			if(isMinimumStayOn.equalsIgnoreCase("Yes")){
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterMinimumStayValue(driver, minimumStayValue);
				testSteps.addAll(getTestSteps);
			}
			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			}
		}
		
		try{

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, isCheckInOn);
			testSteps.addAll(getTestSteps);

			if(isCheckInOn.equalsIgnoreCase("Yes")){
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickNoCheckInCheckbox(driver, isNoCheckInChecked);
					testSteps.addAll(getTestSteps);
			}

			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin button", scriptName, "BulkUpdate", driver);
			}
		}
		
		try{
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, isCheckOutOn);
			testSteps.addAll(getTestSteps);

			if(isCheckOutOn.equalsIgnoreCase("Yes")){
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickNoCheckOutCheckbox(driver, isNoCheckOutChecked);
					testSteps.addAll(getTestSteps);
			}
			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout button", scriptName, "BulkUpdate", driver);
			}
		}

		try{
			
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickUpdateButton(driver);
			testSteps.addAll(getTestSteps);
			

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click update button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click update button", scriptName, "BulkUpdate", driver);
			}
		}
		try{
			app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
			testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

			int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
				days = days+1;
			String expectedDays = "Rules will be updated for " + days + " day(s) within this date range.";
			testSteps.add("Expected total days : " + expectedDays);
			app_logs.info("Expected total days : " + expectedDays);
			String totalDays = ratesGrid.getTotalDaysText(driver, rules);
			testSteps.add("Found : " + totalDays);
			app_logs.info("Found : " + totalDays);
			Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
			testSteps.add("Verified total number of days");
			app_logs.info("Verified total number of days");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total number of days", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total number of days", scriptName, "BulkUpdate", driver);
			}
		}	
	
		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnYesUpdateButton(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click yes update button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click yes update button", scriptName, "BulkUpdate", driver);
			}
		}

		ArrayList<String> getRateValue = new ArrayList<>();
		ArrayList<String> getRuleValue = new ArrayList<>();
		ArrayList<Boolean> getNoCheckInValue = new ArrayList<>();
		ArrayList<Boolean> getNoCheckOutValue = new ArrayList<>();

		try {

			testSteps.add("===== GETTING MIN STAY RULE AND RATE VALUE FORM RATES GRID =====");
			app_logs.info("===== GETTING MIN STAY RULE AND RATE VALUE FORM RATES GRID =====");
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnRateTab(driver);
			testSteps.addAll(getTestSteps);
			HashMap<String, List[]> classesMap= new HashMap<>();
			
			List[] arrayOfList = new List[2];
			classesMap =ratesGrid.getRateMinStayRule(driver, startDate, endDate, ratePlan,
					roomClassName, timeZone, channel, testSteps);
			for(String clas : roomClassName.split(Utility.DELIM)) {
				
				arrayOfList = classesMap.get(clas);
						
						
				getRateValue = (ArrayList<String>) arrayOfList[0];
				getRuleValue = (ArrayList<String>) arrayOfList[1];
				getNoCheckInValue = (ArrayList<Boolean>) arrayOfList[2];
				getNoCheckOutValue = (ArrayList<Boolean>) arrayOfList[3];
				
				app_logs.info("getRateValue: "+getRateValue);
				app_logs.info("getRuleValue: "+getRuleValue);
				app_logs.info("getNoCheckInValue: "+getNoCheckInValue);
				app_logs.info("getNoCheckOutValue: "+getNoCheckOutValue);
				
				String expectedClassesForEnabled = "enabled";
				int numberOfDays = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
				for(int i=0; i< numberOfDays; i++){
				String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, "MM/dd/yyyy",
							"EEE", i, timeZone);
					System.out.println("day : " + day);
	
					if(dayMap.get(day)){
							
							app_logs.info(getRuleValue.get(i));
							Assert.assertEquals(getRuleValue.get(i), minimumStayValue, "Failed : minimum stay value didn't match for " + day);
							testSteps.add("Verified minimum stay value for " + day);
							app_logs.info("Verified minimum stay value for " + day);
							app_logs.info(getNoCheckInValue.get(i));
							Assert.assertTrue(getNoCheckInValue.get(i), "Failed : no check in is not applied for " + day);
							testSteps.add("Verified no check in is applied for " + day);
							app_logs.info("Verified no check in is applied for " + day);
						
							app_logs.info(getNoCheckOutValue.get(i));
							Assert.assertTrue(getNoCheckOutValue.get(i), "Failed : no check out is not applied for " + day);
							testSteps.add("Verified no check out is applied for " + day);
							app_logs.info("Verified no check out is applied for " + day);
						
						
						}
				}
			}
							
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on calendar icon", scriptName, "RateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on calendar icon", scriptName, "RateGrid", driver);
			}
		}


	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyRulesInBulkUpdate", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver() {
		// driver.quit();
	}

}
