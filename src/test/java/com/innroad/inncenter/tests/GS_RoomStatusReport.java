package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.WebElements_RoomStatus;

public class GS_RoomStatusReport extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	TaskList tasklist = new TaskList();
	TaskManagement taskmang = new TaskManagement();
	Navigation nav = new Navigation();
	RoomStatus roomstatus = new RoomStatus();
	
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gsRoomStatusReport() throws ParseException {
		caseId.clear();
		statusCode.clear();
		comments.clear();
		Utility.initializeTestCase("787224"+"|"+"787181"+"|"+"787179"+"|"+"787226", caseId, statusCode,comments,"");
		test_name = "GSRoomStatusReport";
		test_description = "GSRoomStatusReport ";
		test_catagory = "GS";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		try {
			driver = getDriver();
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		}catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Login", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Login", testName, test_description, test_catagory, test_steps);
		}
		
		// Enable toggle for Inspection
		try {
			app_logs.info("======Enabling Toggle from Task Management=======");
			test_steps.add("======Enabling Toggle from Task Management=======");
			nav.Setup(driver);
			test_steps.add("Navigated to Setup");
			nav.TaskManagement_TabExist(driver);
			test_steps.add("Task Management Tab Exist");
			nav.TaskManagement(driver);
			test_steps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			taskmang.setInspectionCleaningToggle(driver, true);
			test_steps.add("Successfully Enabled Toggle for Inspection ");
			app_logs.info("Successfully Enabled Toggle for Inspection");
			taskmang.setInspectionCleaningToggleFlagStatus(driver);
			test_steps.add("Successfully Set Toggle Condition Value ");
			app_logs.info("Successfully Set Toggle Condition Value");

		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Task Management", testName, test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, test_description, test_catagory, "Task Management", testName, test_description, test_catagory, test_steps);
			}
		
		try {
			testName="Generate & Print Report for Quick Stat - Inspection";
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			nav.guestservices_Click(driver,test_steps);
			test_steps.add("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver,test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.clickStatesRoomStatus(driver, "Inspection");
			String sizeOfRoomTile=roomstatus.getQuickStatCount(driver,"Inspection");
			app_logs.info(sizeOfRoomTile);
			if(Integer.parseInt(sizeOfRoomTile)==0) {
				roomstatus.clickRoomTileRadioButton(driver, test_steps,2);
				roomstatus.ClickUpdateStatusButton(driver);
				roomstatus.clickUpdateStatus_InspectionButton(driver, test_steps);
			}
			HashMap<String, ArrayList<String>> roomData = new HashMap<String, ArrayList<String>>();
			roomData= roomstatus.getRoomTileData(driver, "Inspection");
			roomstatus.Reports(driver);
			test_steps.add("Generate Report");
			app_logs.info("Generate Report");
			roomstatus.clickreportRoomStatus(driver);
			test_steps.add("Click Report- Room Status");
			roomstatus.switchToNextTab(driver);
			Wait.waitforPageLoad(50, driver);
			roomstatus.reportVerifyQuickStatsCount(driver, getTest_Steps, "Inspection", sizeOfRoomTile);
			
			 for(int i=0;i<Integer.parseInt(sizeOfRoomTile);i++){
			roomstatus.reportVerifyData(driver, roomData.get("RoomNo").get(i), roomData.get("RoomClass").get(i), roomData.get("Zone").get(i), "Inspection", roomData.get("StatusOfRoom").get(i), "", "", "", test_steps,i);
			}
			Utility.testCasePass(statusCode, 0, comments, "Verify Generate & Print Report for Quick Stat - Inspection");
			test_steps.add("Verify Generate & Print Report for Quick Stat - Inspection " + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787224' target='_blank'>"
								+ "Click here to open TestRail: C787224</a>");		
			 roomstatus.closeCurrentWindow(driver);
			 roomstatus.switchToParentWindowTab(driver);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Room Status Report with Inspection", testName, test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, test_description, test_catagory, "Room Status Report with Inspection", testName, test_description, test_catagory, test_steps);
			}
		try {
			testName="Verify sort order by room number in reports - Dirty from quick stats";
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			roomstatus.clickStatesRoomStatus(driver, "Dirty");
			String sizeOfRoomTile=roomstatus.getQuickStatCount(driver,"Dirty");
			app_logs.info(sizeOfRoomTile);
			HashMap<String, ArrayList<String>> roomData = new HashMap<String, ArrayList<String>>();
			roomData= roomstatus.getRoomTileData(driver, "Dirty");
			roomstatus.Reports(driver);
			test_steps.add("Generate Report");
			app_logs.info("Generate Report");
			roomstatus.clickreportRoomStatus(driver);
			test_steps.add("Click Report- Room Status");
			roomstatus.switchToNextTab(driver);
			Wait.waitforPageLoad(50, driver);
			roomstatus.reportVerifyQuickStatsCount(driver, getTest_Steps, "Dirty", sizeOfRoomTile);
			
			for(int i=0;i<Integer.parseInt(sizeOfRoomTile);i++) {
			roomstatus.reportVerifyData(driver, roomData.get("RoomNo").get(i), roomData.get("RoomClass").get(i), roomData.get("Zone").get(i), "Dirty", roomData.get("StatusOfRoom").get(i), "", "", "", test_steps,i);
			}
			Utility.testCasePass(statusCode, 1, comments, "Verify sort order by room number in reports - Dirty from quick stats");
			test_steps.add("Verify sort order by room number in reports - Dirty from quick stats" + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787181' target='_blank'>"
								+ "Click here to open TestRail: C787181</a>");		
			 roomstatus.closeCurrentWindow(driver);
			 roomstatus.switchToParentWindowTab(driver);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Room Status Report with Dirty", testName, test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, test_description, test_catagory, "Room Status Report with Dirty", testName, test_description, test_catagory, test_steps);
			}
		
		
		try {
			testName="Report should display for the quick stats selected and the search criteria applied";
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			roomstatus.inputSearchAndClickSearchButton(driver, "50", getTest_Steps);
			WebElements_RoomStatus roomStatus = new WebElements_RoomStatus(driver);
			if(!(roomStatus.RoomStatus_AllQuickStatsNumber.getText().equals("0"))) {
				roomstatus.clickStatesRoomStatus(driver, "All");
				app_logs.info("Click All quick stats");
				String sizeOfAllRoomTile=roomstatus.getQuickStatCount(driver,"All");			
				String sizeOfDirtyRoomTile=roomstatus.getQuickStatCount(driver,"Dirty");			
				String sizeOfCleanRoomTile=roomstatus.getQuickStatCount(driver,"Clean");
				String sizeOfOOOCleanRoomTile=roomstatus.getQuickStatCount(driver,"Out of Order");
				String sizeOfInspectionRoomTile=roomstatus.getQuickStatCount(driver,"Inspection");
				app_logs.info(sizeOfAllRoomTile);
				app_logs.info(sizeOfDirtyRoomTile);
				app_logs.info(sizeOfCleanRoomTile);
				app_logs.info(sizeOfOOOCleanRoomTile);
				app_logs.info(sizeOfInspectionRoomTile);
				HashMap<String, ArrayList<String>> roomData = new HashMap<String, ArrayList<String>>();
				roomData= roomstatus.getRoomTileDataForAllQuickStats(driver);
				app_logs.info(roomData);
				Wait.waitforPageLoad(50, driver);
				roomstatus.Reports(driver);
				test_steps.add("Generate Report");
				app_logs.info("Generate Report");
				roomstatus.clickreportRoomStatus(driver);
				test_steps.add("Click Report- Room Status");
				roomstatus.switchToNextTab(driver);
				Wait.waitforPageLoad(50, driver);
				roomstatus.reportVerifyAllQuickStatsCount(driver, test_steps, "All", sizeOfAllRoomTile, sizeOfDirtyRoomTile, sizeOfCleanRoomTile, sizeOfOOOCleanRoomTile, sizeOfInspectionRoomTile, Integer.valueOf(sizeOfOOOCleanRoomTile));				
				for(int i=0;i<Integer.parseInt(sizeOfAllRoomTile);i++) {
				roomstatus.reportVerifyData(driver, roomData.get("RoomNo").get(i), roomData.get("RoomClass").get(i), roomData.get("Zone").get(i), "", roomData.get("StatusOfRoom").get(i), "", "", "", test_steps,i);
				}
				
				Utility.testCasePass(statusCode, 3, comments, "Report should display for the quick stats selected and the search criteria applied");
				test_steps.add("Report should display for the quick stats selected and the search criteria applied" + 
					        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787226' target='_blank'>"
									+ "Click here to open TestRail: C787226</a>");		
				 roomstatus.closeCurrentWindow(driver);
				 roomstatus.switchToParentWindowTab(driver);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			}
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Room Status Report with Dirty", testName, test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, test_description, test_catagory, "Room Status Report with Dirty", testName, test_description, test_catagory, test_steps);
			}
		try {
			testName="Verify sort order by room number in reports -> Room status(ALL Quick stats)";
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			roomstatus.inputSearchAndClickSearchButton(driver, "", getTest_Steps);
			Wait.waitforPageLoad(50, driver);
			roomstatus.clickStatesRoomStatus(driver, "All");
			app_logs.info("Click All quick stats");
			String sizeOfInspectionRoomTile=roomstatus.getQuickStatCount(driver,"Inspection");						
			String sizeOfAllRoomTile=roomstatus.getQuickStatCount(driver,"All");			
			String sizeOfDirtyRoomTile=roomstatus.getQuickStatCount(driver,"Dirty");			
			String sizeOfCleanRoomTile=roomstatus.getQuickStatCount(driver,"Clean");
			String sizeOfOOOCleanRoomTile=roomstatus.getQuickStatCount(driver,"Out of Order");
			app_logs.info(sizeOfAllRoomTile);
			app_logs.info(sizeOfDirtyRoomTile);
			app_logs.info(sizeOfCleanRoomTile);
			app_logs.info(sizeOfOOOCleanRoomTile);
			roomstatus.Reports(driver);
			test_steps.add("Generate Report");
			app_logs.info("Generate Report");
			roomstatus.clickreportRoomStatus(driver);
			test_steps.add("Click Report- Room Status");
			roomstatus.switchToNextTab(driver);
			Wait.waitforPageLoad(50, driver);
			roomstatus.reportVerifyAllQuickStatsCount(driver, test_steps, "All", sizeOfAllRoomTile, sizeOfDirtyRoomTile, sizeOfCleanRoomTile, sizeOfOOOCleanRoomTile, sizeOfInspectionRoomTile, Integer.valueOf(sizeOfOOOCleanRoomTile));
			Utility.testCasePass(statusCode, 2, comments, "Verify sort order by room number in reports -> Room status(ALL Quick stats)");
			test_steps.add("Verify sort order by room number in reports -> Room status(ALL Quick stats)" + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787179' target='_blank'>"
								+ "Click here to open TestRail: C787179</a>");		
			 roomstatus.closeCurrentWindow(driver);
			 roomstatus.switchToParentWindowTab(driver);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Room Status Report with All", testName, test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, test_description, test_catagory, "Room Status Report with All", testName, test_description, test_catagory, test_steps);
			}
		
	}
	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided

		return Utility.getData("GS_RoomStatusReport", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}

}
