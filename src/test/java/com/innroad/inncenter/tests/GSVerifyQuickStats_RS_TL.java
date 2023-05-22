package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.WebElements_RoomStatus;

public class GSVerifyQuickStats_RS_TL extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	TaskList tasklist = new TaskList();
	TaskManagement taskmang = new TaskManagement();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gsVerifyQuickStats_RS_TL(String ToDoStatus, String InspectionStatus,String FullCleaningTaskType, String taskCategory, String taskType,String taskDetails,
			String taskRemarks, String taskAssignee)
			throws InterruptedException, IOException, ParseException {
		String testCaseID="848176|848197";
		if(Utility.getResultForCase(driver, testCaseID)) {
		caseId.clear();
		statusCode.clear();
		comments.clear();
		Utility.initializeTestCase("848176|848197", Utility.testId, Utility.statusCode,Utility.comments,"");
		test_name = "GS-Verify Quick stats in Room status/Task List page";
		test_description = "GS-Verify Quick stats in Room status/Task List page<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848176' target='_blank'>"
				+ "Click here to open TestRail: 848176</a><br>";
		test_catagory = "GS";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");


		Navigation nav = new Navigation();
		RoomStatus roomstatus = new RoomStatus();
		TaskManagement task_mang = new TaskManagement();
		TaskList task_list = new TaskList();
		

		try {
			testName="Inspection quick stat Enable when setting is ON";
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
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
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
         String InspectionQuickStatsCount="";
		// Verify Quick Stats Room Status
		try {
			app_logs.info("======Verify Inspection Enable in Room Status=======");
			test_steps.add("======Verify Inspection Enable in Room Status=======");
			nav.guestservices_Click(driver,test_steps);
			test_steps.add("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver,test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.verify_QuickStats_Enabled_Disabled(driver, InspectionStatus,Utility.toggle,test_steps);
			test_steps.add("Verify Inspection Enable in Room Status"+
				"<a href='https://innroad.testrail.io/index.php?/cases/view/787128' target='_blank'>"
				+ "Click here to open TestRail: C787128</a><br>");
			roomstatus.verifyRoomTile_StatusEnabled_Disabled(driver, InspectionStatus,Utility.toggle, test_steps);
			test_steps.add("Verify Inspection Enable in Room Status"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787106' target='_blank'>"
					+ "Click here to open TestRail: C787106</a><br>");
			WebElements_RoomStatus wb_RoomStatus= new WebElements_RoomStatus(driver);
			InspectionQuickStatsCount=wb_RoomStatus.RoomStatus_InspectionStatSize.getText();
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Ispection  Quick Stats from Room Status", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Ispection  Quick Stats from Room Status", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}       

		// Verify Quick Stats TaskList
		try {
			app_logs.info("====== Verify Inspection Enable in Task List=======");
			test_steps.add("======Verify Inspection Enable in Task List=======");
			nav.Task_List(driver);
			Wait.wait5Second();
			test_steps.add("Click on Task List");
			app_logs.info("Click on Task List");
			getTest_Steps.clear();
			getTest_Steps = tasklist.Click_AddTask(driver);
			test_steps.addAll(getTest_Steps);			
			tasklist.CreateTaskForRoom(driver, "Room", taskCategory, taskType, taskDetails,taskRemarks,
					"1");					
			test_steps.add("Successfully Added New Task");
			app_logs.info("Successfully Added New Task");
			task_list.verifyTaskList_QuickStats(driver, InspectionStatus,Utility.toggle,test_steps);
			tasklist.SearchTask(driver, taskType);
			tasklist.selectTask(driver, taskType,test_steps);
			tasklist.ClickBulkAction(driver);
			test_steps.add("Click Bulk Action");
			tasklist.clickChangeStatus(driver, test_steps);
    		task_list.verifyStatus(driver, InspectionStatus, test_steps);
    		task_list.bulkStatusChange_CloseButton(driver,test_steps);
    		test_steps.add("Visibility of Inspection quick stat when setting is ON " + 
			        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787054' target='_blank'>"
							+ "Click here to open TestRail: C787054</a>");
     		RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Inspection Quick Stats from TaskList and Inpection status for Full Cleaning Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Inspection Quick Stats from TaskList and Inpection status for Full Cleaning Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Toggle from Task Management
		try {
			testName="Inspection quick stat Disable when setting is OFF";
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Disable Toggle for Inspection
		try {
			app_logs.info("======Disble Toggle from Task Management=======");
			test_steps.add("======Disble Toggle from Task Management=======");
			nav.GS_Setup(driver);
			test_steps.add("Navigated to Setup");
			nav.TaskManagement_TabExist(driver);
			test_steps.add("Task Management Tab Exist");
			nav.TaskManagement(driver);
			test_steps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			task_mang.setInspectionCleaningToggle(driver, false);
			task_mang.setInspectionCleaningToggleFlagStatus(driver);
			test_steps.add("Successfully Disabled Toggle for Inspection ");
			app_logs.info("Successfully Disabled Toggle for Inspection");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Disable Toggle for Inspection", testName, "DisableToggle", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Disable Toggle for Inspection", testName, "DisableToggle", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Quick Stats Room Status
		try {
			app_logs.info("======Verify Inspection Disable in Room Status=======");
			test_steps.add("======Verify Inspection Disable in Room Status=======");
			nav.guestservices_Click(driver,test_steps);
			roomstatus.verifyRoomStatusTabEnabled(driver,test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.verify_QuickStats_Enabled_Disabled(driver, InspectionStatus,Utility.toggle,test_steps);
			Wait.wait1Second();
			roomstatus.verifyRoomTile_StatusEnabled_Disabled(driver,InspectionStatus, Utility.toggle, test_steps);
	        roomstatus.verifyInspectionQuickStatsRoomTile_OnToggleDisable(driver, InspectionStatus, InspectionQuickStatsCount, getTest_Steps);	        
	        test_steps.add("Successfully Verified Inspection Room Tile Found on disable toggle " + 
	        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/17069' target='_blank'>"
					+ "Click here to open TestRail: C17069</a>");
	        app_logs.info("Successfully Verified  Inspection Room Tile Not Found on disable toggle ");
	        test_steps.add("Room status of the rooms when inspection flag is OFF " + 
	    	        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787107' target='_blank'>"
	    					+ "Click here to open TestRail: C787107</a>");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Quick Stats Room Status", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Quick Stats Room Status", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Quick Stats TaskList
		try {
			app_logs.info("====== Verify Inspection Disable in Task List=======");
			test_steps.add("======Verify Inspection Disable in Task List=======");
			nav.Task_List(driver);
			task_list.verifyTaskList_QuickStats(driver, InspectionStatus,Utility.toggle,test_steps);
			 test_steps.add("Visibility of Inspection quick stat when setting is OFF " + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787055' target='_blank'>"
								+ "Click here to open TestRail: C787055</a>");
			tasklist.SearchTask(driver, taskType);
			tasklist.selectTask(driver, taskType,test_steps);
			tasklist.ClickBulkAction(driver);
			test_steps.add("Click Bulk Action");
			tasklist.clickChangeStatus(driver, test_steps);
    		task_list.verifyStatus(driver, InspectionStatus, test_steps);
    		task_list.bulkStatusChange_CloseButton(driver,test_steps);
    		
    		/* test_steps.add("GS-Verify Quick stats in Room status/Task List page" + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848176' target='_blank'>"
								+ "Click here to open TestRail: 848176</a>");    		 
    		 Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "GS-Verify Quick stats in Room status/Task List page");
			 Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			*/
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Quick Stats TaskList", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Quick Stats TaskList", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
//Delete task
		try {
			app_logs.info("======Detele Task=======");
			test_steps.add("======Detele Task=======");
			tasklist.SearchTask(driver, taskType);
			tasklist.deleteTask(driver,  taskType, test_steps);			
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Delete Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Delete Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			nav.roomStatus(driver);
			roomstatus.verifyRoomStatusTabEnabled(driver,test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.Reports(driver);
			test_steps.add("Generate Report");
			app_logs.info("Generate Report");
			roomstatus.clickreportRoomStatus(driver);
			test_steps.add("Click Report- Room Status");
			roomstatus.switchToNextTab(driver);
			Wait.waitforPageLoad(40, driver);
			roomstatus.verify_QuickStats_Enabled_Disabled(driver, InspectionStatus,Utility.toggle,test_steps);
			 test_steps.add("Verify Inspection tab should not display on room status report " + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/787219' target='_blank'>"
								+ "Click here to open TestRail: C787219</a>");	
			 for(int i=0;i<Utility.testId.size();i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify task list delete and update");
				}
				
				Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
						Utility.comments, TestCore.TestRail_AssignToID);
				
			 RetryFailedTestCases.count = Utility.reset_count;
			 Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Report", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Report", testName, test_description, test_catagory, test_steps);
		}
	}
	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided

		return Utility.getData("GSVerifyQuickStats_RS_TL", gsexcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		driver.quit();
		
	}

}
