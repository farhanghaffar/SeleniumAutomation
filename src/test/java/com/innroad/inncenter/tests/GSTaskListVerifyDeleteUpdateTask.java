package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

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

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GSTaskListVerifyDeleteUpdateTask extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
//String comments=null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	String yearDate=null;
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSTaskListVerif_DeleUpdateTask(
			String CheckInDate, String CheckOutDate, String Adults, String Children, String Rateplan, 
			String RoomClass,String Salutation, String GuestFirstName, String GuestLastName, String PaymentType,
			String CardNumber, String NameOnCard,  String MarketSegment, String Referral,
			String TaskCategory, String TaskType,
			String TaskDetails, String TaskRemarks, String TaskAssignee, String TaskStatus)
			throws InterruptedException, IOException, ParseException {
		String testCaseID="848196";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String totaltestCase="12";
		caseId.clear();
		statusCode.clear();
		comments.clear();
	
		Utility.initializeTestCase("848196", Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = "GSTaskListVerify_DeleUpdateTask";
		testDescription = "GS-Task List-Verify the functionality of deleting and updating a task <br>";
		testCategory = "Verification";
		String testName = test_name;

		TestName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		TaskList tasklist = new TaskList();
		ReservationSearch reser_search = new ReservationSearch();
		CPReservationPage res = new CPReservationPage();
		String reservationNumber = null;
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		GuestLastName = GuestLastName + Utility.GenerateRandomNumber();
		String [] taskCategory= null,taskType=null,taskDetails=null,taskRemarks=null,taskAssignee=null,taskStatus=null;
		// Get checkIN and Checkout Date
				try {
					if (!(Utility.validateInput(CheckInDate))&& !(Utility.validateInput(CheckOutDate))) {
						for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
					} else {
						checkInDates = Utility.splitInputData(CheckInDate);
						checkOutDates = Utility.splitInputData(CheckOutDate);
					}
					CheckInDate = checkInDates.get(0);
					CheckOutDate = checkOutDates.get(0);
					app_logs.info(CheckInDate);
					app_logs.info(CheckOutDate);
	
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
		try {

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			//login_CP(driver);
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
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
		// Create Reservation
		try {
			app_logs.info("======CP Reservation Creation=======");
			test_steps.add("======CP Reservation Creation=======");
			res.click_NewReservation(driver, test_steps);
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan, "");
			res.clickOnFindRooms(driver, test_steps);
			res.selectRoom(driver, test_steps, RoomClass, "", "");
			res.clickNext(driver, test_steps);
			res.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, GuestFirstName, GuestLastName, config.getProperty("flagOff"));
			yearDate = Utility.getFutureMonthAndYearForMasterCard();
			res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, yearDate);
			res.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
			res.clickBookNow(driver, test_steps);
			reservationNumber = res.get_ReservationConfirmationNumber(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			res.closeReservationTab(driver);

		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation ", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Add New Task
		try {
			app_logs.info("======Create Task In GuestServices & Assign Reservation=======");
			test_steps.add("======Create Task In GuestServices & Assign Reservation=======");
			nav.navigateGuestservice(driver);
			app_logs.info("Navigate To Guest Services");
			test_steps.add("Navigate To Guest Services ");
			nav.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task List");
			taskCategory=TaskCategory.split("\\|");
			taskType=TaskType.split("\\|");
			taskDetails=TaskDetails.split("\\|");
			taskRemarks=TaskRemarks.split("\\|");
			taskAssignee=TaskAssignee.split("\\|");		
			taskStatus=TaskStatus.split("\\|");
			for(int i=0;i<taskCategory.length;i++) {
			getTest_Steps.clear();
			getTest_Steps = tasklist.Click_AddTask(driver);
			test_steps.addAll(getTest_Steps);			
			getTest_Steps = tasklist.CreateNewTask(driver, "Reservation", taskCategory[i], taskType[i], taskDetails[i],
					taskRemarks[i], taskAssignee[i], reservationNumber, getTest_Steps);			
			}
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Added New Task");
			app_logs.info("Successfully Added New Task");
			/*Utility.testCasePass(statusCode, 3,comments,"Successfully Add Task for Reservation");
			test_steps.add("Successfully Add Task for Reservation"+
				"<a href='https://innroad.testrail.io/index.php?/cases/view/787238' target='_blank'>"
				+ "Click here to open TestRail: C787238</a><br>");
			Utility.testCasePass(statusCode, 7,comments,"Successfully Creating a new task should only give user the option of To Do status");
			test_steps.add("Successfully Creating a new task should only give user the option of To Do status"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787282' target='_blank'>"
					+ "Click here to open TestRail: C787282</a><br>");
			Utility.testCasePass(statusCode, 8,comments,"Successfully Added New Task");
			test_steps.add("Successfully Add Task "+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787292' target='_blank'>"
					+ "Click here to open TestRail: C787292</a><br>");
*/
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add New Task", testName, "TaskAdd", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add New Task", testName, "TaskAdd", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Added Task
		try {
			app_logs.info("======Verify Task In Reservation & Detele Task=======");
			test_steps.add("======Verify Task In Reservation & Detele Task=======");
			nav.Reservation_Backward_2(driver);
			app_logs.info("Navigate back to Reservation");
			test_steps.add("Navigate back to Reservation");
			reser_search.basicSearch_WithResNumber(driver, reservationNumber);
			app_logs.info("Successfully Searched & Opened Reservation " + reservationNumber);
			test_steps.add("Successfully Searched &Opened Reservation " + reservationNumber);
			getTest_Steps.clear();
			for(int i=0;i<taskCategory.length;i++) {
			getTest_Steps = res.VerifyCreatedTask(driver, taskType[i], taskDetails[i], taskStatus[i], getTest_Steps);
			}
			test_steps.addAll(getTest_Steps);
		/*	Utility.testCasePass(statusCode, 2,comments,"Verify Tasks assigned to a Reservation");
			test_steps.add("Verify Tasks assigned to a Reservation"+
			"<a href='https://innroad.testrail.io/index.php?/cases/view/787235' target='_blank'>"
					+ "Click here to open TestRail: C787235</a>");
			Utility.testCasePass(statusCode, 12,comments,"Verify the Task/Note section in Reservataion list page");
			test_steps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/787120' target='_blank'>"
				+ "Click here to open TestRail: C787120</a><br>"+
				"<a href='https://innroad.testrail.io/index.php?/cases/view/787235' target='_blank'>"
				+ "Click here to open TestRail: C787235</a><br>");
	*/		getTest_Steps.clear();
			getTest_Steps = res.DeleteTask(driver, taskDetails[0], getTest_Steps);
			test_steps.addAll(getTest_Steps);
			res.closeReservationTab(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify & Delete Added  Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to  Verify & Delete Added  Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String GuestName = GuestFirstName + " " + GuestLastName;
		// Verify Deleted Task
		try {
			app_logs.info("======Verify Deleted Task In TaskList=======");
			test_steps.add("======Verify Deleted Task In TaskList ======");
			nav.navigateGuestservice(driver);
			app_logs.info("Navigate To Guest Services");
			test_steps.add("Navigate To Guest Services");
			nav.Task_List(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task_List");
			tasklist.clickAllStatsBar(driver, checkOutDates);
			Wait.wait5Second();
			tasklist.SearchTask(driver, GuestName);
			tasklist.verifyTaskForReservation(driver, taskType[0], GuestName,test_steps);
			test_steps.add("No tasks found for the selected criteria and property.");
			test_steps.add("Successfully Verified Deleted Task");
			app_logs.info("Successfully Verified Deleted  Task");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Deleted  Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to  Verify Deleted Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Delete Any Task from the TaskList
		try {
			app_logs.info("====== Deleted Any Task In TaskList=======");
			test_steps.add("====== Deleted Any Task In TaskList ======");
			getTest_Steps.clear();
			tasklist.deleteTask(driver,  taskType[1], test_steps);
			/*Utility.testCasePass(statusCode, 10,comments,"Successfully Deleted Task");
			test_steps.add("Successfully Add Task for Reservation"+
			"<a href='https://innroad.testrail.io/index.php?/cases/view/787295' target='_blank'>"
			+ "Click here to open TestRail: C787295</a><br>");*/
				} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to  Delete  any Task from the list", testName, "Deletion", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to   Delete any Task from the list", testName, "Deletion", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		// Verify Deleted Task
		try {
			app_logs.info("====== Verify Deleted Task In Reservation=======");
			test_steps.add("====== Verify Deleted  Task In Reservation ======");
			nav.Reservation_Backward_2(driver);
			reser_search.basicSearch_WithResNumber(driver, reservationNumber);
			res.verifyDeletedTaskInReservation(driver,taskType[1],test_steps);
			res.closeReservationTab(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Deleted Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to  Verify Deleted Task", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Edit Task in TaskList
		try {
			nav.navigateGuestservice(driver);
			app_logs.info("Navigate To Guest Services");
			test_steps.add("Navigate To Guest Services");
			nav.Task_List(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task_List");			
			tasklist.SearchTask(driver, GuestName);
			test_steps.add("Searched the Task With Name: " + GuestName);
			app_logs.info("Searched the Task With Name: " + GuestName);			
			tasklist.editTask(driver, taskCategory[0], taskType[0], "Done", taskDetails[0], taskCategory[2],taskType[2],test_steps);			
		/*	Utility.testCasePass(statusCode, 4,comments,"User can enter text in details text box and save after Edit task");
			test_steps.add("Successfully User can enter text in details text box and save after Edit task"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787264' target='_blank'>"
					+ "Click here to open TestRail: C787264</a><br>");
			Utility.testCasePass(statusCode, 5,comments,"Successfully User can enter text in remarks text box and save after Edit task");
			test_steps.add("Successfully User can enter text in remarks text box and save after Edit task"+
				"<a href='https://innroad.testrail.io/index.php?/cases/view/787265' target='_blank'>"
				+ "Click here to open TestRail: C787265</a><br>");
			Utility.testCasePass(statusCode, 6,comments,"Successfully User can edit task and update it to Done by editing inline");
			test_steps.add("Successfully User can edit task and update it to Done by editing inline"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787281' target='_blank'>"
					+ "Click here to open TestRail: C787281</a><br>");
			Utility.testCasePass(statusCode, 9,comments,"Successfully User should be able to Assign a Task and Update Task Status");
			test_steps.add("Successfully User should be able to Assign a Task and Update Task Status"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787293' target='_blank'>"
					+ "Click here to open TestRail: C787293</a><br>");*/
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Edit  Task", testName, "Edit", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to EditTask", testName, "Edit", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Edited Task
		try {
			app_logs.info("====== Verify Edited Task In Reservation=======");
			test_steps.add("====== Verify Edited  Task In Reservation ======");
			nav.Reservation_Backward_2(driver);
			reser_search.basicSearch_WithResNumber(driver, reservationNumber);
			getTest_Steps.clear();
			getTest_Steps = res.VerifyCreatedTask(driver, taskType[0], taskDetails[0], "Done", getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Verified Task Is Edited in Reservation " + reservationNumber);
			/*Utility.testCasePass(statusCode, 11,comments,"Successfully Verified Task Is Edited in Reservation " + reservationNumber);
			test_steps.add("Successfully Add Task for Reservation"+
				"<a href='https://innroad.testrail.io/index.php?/cases/view/787296' target='_blank'>"
				+ "Click here to open TestRail: C787296</a><br>");
			*/
			/*Utility.testCasePass(Utility.statusCode,0,Utility.comments,"GS-Task List-Verify the functionality of deleting and updating a task");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			*//*Utility.testCasePass(Utility.statusCode,1,Utility.comments,"GS-Task List-Verify the functionality of deleting and updating a task");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			*/
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify task list delete and update");
			}
			
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, testDescription, testCategory, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestDescription, test_steps);
				Utility.updateReport(e, "Failed to Verify Edited Task", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestDescription, test_steps);
				Utility.updateReport(e, "Failed to Verify  Edited Task", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("GSTaskListVerif_DeleUpdateTask", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
