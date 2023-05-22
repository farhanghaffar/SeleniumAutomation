package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
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

public class GSBulkUpdate extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	TaskList tasklist = new TaskList();
	TaskManagement taskmang = new TaskManagement();
	Navigation nav = new Navigation();
	RoomStatus roomstatus = new RoomStatus();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gsBulkUpdate() throws ParseException {
		caseId.clear();
		statusCode.clear();
		comments.clear();
		Utility.initializeTestCase("787097" + "|" + "787095" + "|" + "787110"+"|"+"787193"
		+"|"+"787108"+"|"+"787109", caseId, statusCode, comments, "");
		test_name = "GSBulkUpdate";
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
		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Login", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Login", testName, test_description,
					test_catagory, test_steps);
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
			Utility.catchException(driver, e, test_description, test_catagory, "Task Management", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Task Management", testName,
					test_description, test_catagory, test_steps);
		}

		try {
			testName = "Verify Bulk Update Inspection";
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			nav.guestservices_Click(driver, test_steps);
			test_steps.add("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);

			HashMap<String, ArrayList<String>> roomData = new HashMap<String, ArrayList<String>>();
			roomData = roomstatus.getRoomTileDataForSpecificSize(driver, 10);
			app_logs.info(roomData);
			List<String> status = new ArrayList<String>();
			for (int i = 0; i < roomData.size(); i++) {
				status.add(roomstatus.getCurrentStatusofRoomTile(driver, test_steps, roomData.get("RoomClass").get(i),
						roomData.get("RoomNo").get(i)));
			}
			app_logs.info(status);
			roomstatus.clickRoomTileRadioButton(driver, test_steps, 10);
			roomstatus.ClickUpdateStatusButton(driver);
			roomstatus.clickUpdateStatus_InspectionButton(driver, test_steps);
			String messageText = "//div[@class='toast-message'][contains(text(),'Room(s) have been updated.')]";
			Wait.WaitForElement(driver, messageText);
			Wait.waitforPageLoad(40, driver);
			for (int i = 0; i < roomData.size(); i++) {
				roomstatus.verifyCurrentStatusofRoomTile(driver, test_steps, roomData.get("RoomClass").get(i),
						roomData.get("RoomNo").get(i), "Inspection");
			}
			Utility.testCasePass(statusCode, 0, comments, "Verify Bulk Update Inspection");
			test_steps.add("Verify Bulk Update Inspection "
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787097' target='_blank'>"
					+ "Click here to open TestRail: C787097</a>");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Inspection Bulk Update", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Inspection Bulk Update", testName,
					test_description, test_catagory, test_steps);
		}
		try {
			testName = "Verify Bulk Update With Select All";
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}

			HashMap<String, ArrayList<String>> roomData = new HashMap<String, ArrayList<String>>();
			roomData = roomstatus.getRoomTileDataForSpecificSize(driver, 10);
			app_logs.info(roomData);
			test_steps.add("Verified hand pointer");
			Utility.testCasePass(statusCode, 3, comments, "Verified hand pointer");
			test_steps.add("Verified hand pointer"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787095' target='_blank'>"
					+ "Click here to open TestRail: C787095</a>");
			List<String> status = new ArrayList<String>();
			for (int i = 0; i < roomData.size(); i++) {
				status.add(roomstatus.getCurrentStatusofRoomTile(driver, test_steps, roomData.get("RoomClass").get(i),
						roomData.get("RoomNo").get(i)));
			}
			app_logs.info(status);
			roomstatus.clickSelectAll(driver, test_steps);
			Wait.waitforPageLoad(50, driver);
			roomstatus.verifyAllRoomsSelectedDeselected(driver, test_steps);
			Utility.testCasePass(statusCode, 4, comments, "Verified hand pointer");
			test_steps.add("Verified hand pointer"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787109' target='_blank'>"
					+ "Click here to open TestRail: C787109</a>");
			roomstatus.clickDeSelectAll(driver, test_steps);
			Wait.waitforPageLoad(50, driver);
			roomstatus.verifyAllRoomsSelectedDeselected(driver, test_steps);
			Utility.testCasePass(statusCode, 5, comments, "Verified hand pointer");
			test_steps.add("Verified hand pointer"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787108' target='_blank'>"
					+ "Click here to open TestRail: C787108</a>");
			Wait.waitforPageLoad(50, driver);
			
			roomstatus.clickSelectAll(driver, test_steps);
			Wait.waitforPageLoad(50, driver);
			roomstatus.verifyUpdateStatusButtonEnable(driver);
			test_steps.add("Update Status button enabled");
			roomstatus.ClickUpdateStatusButton(driver);
			roomstatus.clickUpdateStatus_InspectionButton(driver, test_steps);
			String messageText = "//div[@class='toast-message'][contains(text(),'Room(s) have been updated.')]";
			Wait.WaitForElement(driver, messageText);
			Wait.waitforPageLoad(40, driver);
			for (int i = 0; i < roomData.size(); i++) {
				roomstatus.verifyCurrentStatusofRoomTile(driver, test_steps, roomData.get("RoomClass").get(i),
						roomData.get("RoomNo").get(i), "Inspection");
			}
		
			Utility.testCasePass(statusCode, 1, comments, "Verify Bulk Update With Select All");
			test_steps.add("Verify Bulk Update With Select All "
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787095' target='_blank'>"
					+ "Click here to open TestRail: C787095</a>");
			Utility.testCasePass(statusCode, 2, comments, "Verify Bulk Update With Select All");
			test_steps.add("Verify Bulk Update With Select All "
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787110' target='_blank'>"
					+ "Click here to open TestRail: C787110</a>");

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Inspection Bulk Update", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Inspection Bulk Update", testName,
					test_description, test_catagory, test_steps);
		}
	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided

		return Utility.getData("GSBulkUpdate", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
	}

}
