package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GSVerifyRoomConditionAndBulkUpdate extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	List<String> roomStatusListOne = new ArrayList<String>();
	List<String> roomStatusListTwo = new ArrayList<String>();
	ArrayList<String> roomNumbers = new ArrayList<>();
	String testName = null;
	Navigation nav = new Navigation();
	RoomClass roomClass = new RoomClass();
	RoomStatus roomStatus = new RoomStatus();
	TaskManagement taskmang = new TaskManagement();
	public static String roomClassNames;
	public static String roomClassAbbrivations;
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	// String comments=null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSVerifyRoomConditionAndBulkUpdate(String URL, String ClientId, String UserName, String Password,
			String RoomClass, String RoomClassAbbrivation, String BedsCount, String MaxAdults, String MaxPersopns,
			String RoomQuantity) throws ParseException {
		String testCaseID="848190|848553|850175|850176";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String totalTestCase = "14";
		caseId.clear();
		statusCode.clear();
		comments.clear();
		Utility.initializeTestCase("848190|848553|850175|850176", Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = "GSVerifyRoomConditionAndBulkUpdate";
		test_description = "GS-Verificaiton of room condition change and bulk updation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848190' target='_blank'>"
				+ "Click here to open TestRail: 848190</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848553' target='_blank'>"
				+ "Click here to open TestRail: 848553</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850175' target='_blank'>"
				+ "Click here to open TestRail: 850175</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850176' target='_blank'>"
				+ "Click here to open TestRail: 850176</a><br>";

		test_catagory = "GS";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}

			driver = getDriver();
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enable toggle for Inspection
		try {
			test_steps.add("<b>======Enabling Toggle for Inspection======</b>");
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Enable toggle for Inspection", "Task Management", "RoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Enable toggle for Inspection", "Task Management", "RoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create RoomClass More Than One
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			nav.navigateToSetupfromTaskManagement(driver);
			nav.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			roomClassNames = RoomClass + Utility.getTimeStamp();
			roomClassAbbrivations = RoomClassAbbrivation + Utility.getTimeStamp();
			newRoomClass.createRoomClassV2(driver, test_steps, roomClassNames, roomClassAbbrivations, MaxAdults,
					MaxPersopns, RoomQuantity);
			Utility.RoomClassName = roomClassNames;
			Utility.RoomClassabv = roomClassAbbrivations;

			String quantity = RoomQuantity;
//				====== roomclass creations ====
			String roomNumber = Utility.RoomNo;

			roomNumbers.add(roomNumber);
			for (int i = 1; i < Integer.parseInt(quantity); i++) {
				roomNumber = String.valueOf(Integer.parseInt(roomNumber) + 1);
				roomNumbers.add(roomNumber);
			}
			app_logs.info("Room NO Are: " + roomNumbers);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Room Class", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Room Class", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Room Condition Bulk Updation
		try {
			test_steps.add("<b>======Start Verificaiton Of Room Condition Change And Bulk Updation======</b>");
			nav.navigateGuestservicesAfterrateGrid(driver);
			test_steps.add("Navigate To Guest Services");
			roomStatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomStatus.searchByRoom(driver, Utility.RoomClassName, test_steps);
			roomStatusListTwo.add("Clean");
			roomStatusListTwo.add("Inspection");
			roomStatusListTwo.add("Dirty");
			app_logs.info("First List Is: " + roomStatusListTwo);
			roomStatusListOne = roomStatus.getStatusofSingleRoomClass(driver, test_steps, Utility.RoomClassName,
					Utility.RoomNo);
			app_logs.info("Second List Is: " + roomStatusListOne);
			Utility.equalLists(roomStatusListOne, roomStatusListTwo);
			test_steps.add("Verified Room Status: <b>" + roomStatusListOne + "</b>");
			app_logs.info("Verified Room Status: " + roomStatusListOne);
			roomStatus.clickONRoomStatusForToday(driver);
			roomStatus.changeStatusMultipleRoom(driver, test_steps, "Dirty", Integer.parseInt(RoomQuantity),
					roomNumbers);
				roomStatus.changeStatusMultipleRoom(driver, test_steps, "Clean", Integer.parseInt(RoomQuantity),
					roomNumbers);
			roomStatus.changeStatusMultipleRoom(driver, test_steps, "Inspection", Integer.parseInt(RoomQuantity),
					roomNumbers);
				roomStatus.roomTileVerifyColor(driver, "Inspection", test_steps);
				roomStatus.clickRoomTileRadioButton(driver, test_steps, Integer.parseInt(RoomQuantity));
				roomStatus.verifyUpdateStatusButtonEnable(driver);
			roomStatus.ClickUpdateStatusButton(driver);
			test_steps.add("Click on Update Status Button");
			app_logs.info("Click on Update Status Button");
			roomStatus.ClickUpdateStatus_CleanButton(driver);
			test_steps.add("Bulk Updated Status to Dirty");
			app_logs.info("Bulk Updated Status to Dirty");
			String messageText = "//div[@class='toast-message'][contains(text(),'Room(s) have been updated.')]";
			Wait.WaitForElement(driver, messageText);
			roomStatus.verifyTosterMessageBulkUpdate(driver, test_steps, RoomQuantity);
	
			roomStatus.roomTileVerifyColor(driver, "Dirty", test_steps);
			roomStatus.updateStatusAndCategoryStatsVerification(driver);
			test_steps.add("Successfully Verified Room Condition Bulk Updation");
			app_logs.info("Successfully Verified Room Condition Bulk Updation");
					/*	
			test_steps.add("GS-Verificaiton of room condition change and bulk updation"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848190' target='_blank'>"
					+ "Click here to open TestRail: 848190</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.statusCode,
					"GS-Verificaiton of room condition change and bulk updation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);

			Utility.testCasePass(Utility.testId, 1,Utility.comments,"GS-Verificaiton of room condition change and bulk updation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
		
			test_steps.add("User should be able to bulk update the room status"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850175' target='_blank'>"
					+ "Click here to open TestRail: 850175</a><br>");
			Utility.testCasePass(Utility.statusCode, 2, Utility.statusCode,
					"GS-Verificaiton of room condition change and bulk updation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);

			test_steps.add("User should be able to do rooms bulk update irrespective of the current room status"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850176' target='_blank'>"
					+ "Click here to open TestRail: 850176</a><br>");
			Utility.testCasePass(Utility.statusCode, 3, Utility.statusCode,
					"User should be able to do rooms bulk update irrespective of the current room status");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);

	*/
			for(int i=0;i<Utility.statusCode.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Rooms bulk update verification");
			}
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Room Condition Bulk Updation", testName, "Verification",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Room Condition Bulk Updation", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Room Class
		try {
			test_steps.add("<b>======Delete Room Class======</b>");
			nav.GS_Setup(driver);
			test_steps.add("Navigated to Setup");
			nav.RoomClass(driver);
			newRoomClass.searchRoomClassV2(driver, Utility.RoomClassName);
			test_steps.add("Click on Search Button");
			app_logs.info("Click on Search Button");
			newRoomClass.deleteRoomClassV2(driver, Utility.RoomClassName);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + Utility.RoomClassName + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + Utility.RoomClassName);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Delete Room Class ", testName, "Delete Room Class", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Delete Room Class ", testName, "Delete Room Class", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSVerifyRoomConditionBulkUpdate", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();	
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);

	}

}
