package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAutoTaskWithfrequencyNLogs extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	String testName = null;
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	RoomStatus roomstatus = new RoomStatus();
	CPReservationPage reservationPage = new CPReservationPage();
	ReservationSearch rsvSearch = new ReservationSearch();
	TaskManagement taskmang = new TaskManagement();
	ReservationSearch revSearch = new ReservationSearch();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Create_Reservation reservation= new Create_Reservation();
	Groups group= new Groups();
	ArrayList<String> comments = new ArrayList<>();
	String roomClassNames = null,confirmationNo=null, checkInTask=null,checkOutTask=null,
			checkInDueTime=null,checkOutDueTime=null,checkInDateTime=null,checkOutDateTime=null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void verifyAutoTaskWithfrequencyNLogs(String checkInDate, String checkOutDate, 
			String roomClassName,String salutation, String guestFirstName, String guestLastName, 
			String paymentType,String cardNumber, String nameOnCard, String marketSegment, String referral,
			String categoryName, String taskName, String systemTaskName,String updatedCatName, String updatedTaskType,
			String updatedTaskDetails, String  updatedTaskStatus) throws ParseException {
		String testCaseID="850205|850202";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "VerifyAutoTaskWithfrequencyNLogs";	
		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("850205|850202", Utility.testId, Utility.statusCode, Utility.comments, "");
		String testCase=null;
		String randomNum=Utility.fourDigitgenerateRandomString();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> allDatesRangeList = new ArrayList<String>();
		ArrayList<String> datesRange = new ArrayList<String>();
		 ArrayList<String> systemTaskNames=new ArrayList<String>();
		 ArrayList<String> systemTaskNamesEveryDay=new ArrayList<String>();
		 HashMap<String,String> systemTaskNamesEveryTwoDay=new HashMap<String,String>();
		 ArrayList<String> systemTaskNamesOnlyEveryTwoDay=new ArrayList<String>();
		 HashMap<String,String> systemTaskDueTime=new HashMap<String,String>();
		 HashMap<String, Boolean> taskAndEnabledOrNot= new HashMap<String, Boolean>();
		 HashMap<String,String> systemTaskFequency=new HashMap<String,String>();
		 boolean isTaskExist=false, isAutoSetforTask=false;
		// Login
					try {

						if (!(Utility.validateInput(checkInDate))) {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));								
						} else {
							checkInDates = Utility.splitInputData(checkInDate);
							checkOutDates = Utility.splitInputData(checkOutDate);
						}
						checkInDate = checkInDates.get(0);
						checkOutDate = checkOutDates.get(0);
						app_logs.info(checkInDate);
						app_logs.info(checkOutDate);
						
						if (!Utility.insertTestName.containsKey(testName)) {
							Utility.insertTestName.put(testName, testName);
							Utility.reTry.put(testName, 0);
						} else {
							//Utility.reTry.replace(testName, 1);
							Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
						}
						datesRangeList= Utility.getAllDatesBetweenCheckInOutDates(checkInDate, checkOutDate);
						datesRangeList.remove(0);
						allDatesRangeList = Utility.getAllDatesStartAndEndDates(checkInDate, checkOutDate);
						app_logs.info(datesRangeList);
						for(String str: datesRangeList) {
						datesRange.add(Utility.parseDate(str, "dd/MM/yyyy", "MM/dd/ yy"));						
						}
						app_logs.info(datesRange);
						
						driver = getDriver();
						login_GS(driver);
						testSteps.add("Logged into the application");
						app_logs.info("Logged into the application");														
					} catch (Exception e) {
						Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
								test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
								test_catagory, testSteps);
					}										
					try
					{
						navigation.clickSetup(driver);
						testSteps.add("Click on Setup");
						app_logs.info("Click on Setup");			
						testSteps.add("========== Delete Category ==========");
						app_logs.info("========== Delete Category ==========");			
						navigation.taskManagement_TabExist(driver);
						testSteps.add("Task Management Tab Exist");
						navigation.taskManagement(driver);
						testSteps.add("Click on Task Management");
						app_logs.info("Click on Task Management");
						taskmang.deleteAllCategories(driver, categoryName,testSteps);
					}catch (Exception e) {
						Utility.catchException(driver, e, "Delete Category", "Task Management ", "Task Management", testName,
								test_description, test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Delete Category", "Task Management", "Task Management", testName, test_description,
								test_catagory, testSteps);
					}									
					try
					{						
						testSteps.add("========== Create Category and New Task ==========");
						systemTaskNames=Utility.splitInputData(systemTaskName);
						for(int i=0;i<systemTaskNames.size();i++) {
							 isTaskExist=taskmang.isTaskThere(driver, systemTaskNames.get(i));
							 if(isTaskExist) {
								 taskAndEnabledOrNot.put(systemTaskNames.get(i), isTaskExist);
								 app_logs.info("Task Exist " + systemTaskNames.get(i));
								 	 isAutoSetforTask=taskmang.isAutoCreateDisabledForTask(driver, systemTaskNames.get(i));
								 if(isAutoSetforTask) {
									 app_logs.info("Auto Create flag Disabled for Task " + systemTaskNames.get(i));
									 taskmang.enableAutoCreate(driver, systemTaskNames.get(i), true);
									 testSteps.add("Enabled Auto Create flag for task " + systemTaskNames.get(i));
									 app_logs.info("Enabled Auto Create flag for task " + systemTaskNames.get(i));
								 }else {
									 testSteps.add("Enabled Auto Create flag for task " + systemTaskNames.get(i));
									 app_logs.info("Enabled Auto Create flag for task " + systemTaskNames.get(i));
								 }
								 testSteps.add(systemTaskNames.get(i));
								 
								 if(systemTaskNames.get(i).equals("Room Move")) {
									 taskmang.setFrequency(driver, "Every 2nd Day", systemTaskNames.get(i));
								 testSteps.add("Set Frequency Every 2nd Day of  task " + systemTaskNames.get(i));
								 app_logs.info("Set Frequency Every 2nd Day of  task " + systemTaskNames.get(i));
								 String time=taskmang.getTaskDueTime(driver, systemTaskNames.get(i));
								 String str[]= time.split(":");
								 if(str[0].length()==1) {
									 time="0"+time;
								 }	
								 systemTaskNamesOnlyEveryTwoDay.add(systemTaskNames.get(i));
								 systemTaskNamesEveryTwoDay.put(systemTaskNames.get(i), time);
								 }else {
									 
								 taskmang.setFrequency(driver, "Every Day", systemTaskNames.get(i));
								 testSteps.add("Set Frequency Every Day of  task " + systemTaskNames.get(i));
								 app_logs.info("Set Frequency Every Day of  task " +systemTaskNames.get(i));
								 String time=taskmang.getTaskDueTime(driver, systemTaskNames.get(i));
								 String str[]= time.split(":");
								 if(str[0].length()==1) {
									 time="0"+time;
								 }								 
								 systemTaskDueTime.put(systemTaskNames.get(i),time);
								 systemTaskFequency.put(systemTaskNames.get(i), "Every Day");
								 systemTaskNamesEveryDay.add(systemTaskNames.get(i));
								 }
					
							 }
						}						
						categoryName=categoryName+randomNum;
						taskmang.createTaskCategory(driver, categoryName,testSteps);
						app_logs.info("Created Category: " + categoryName);
						checkInTask=taskName+randomNum;
						app_logs.info(checkInTask);
						taskmang.createTask(driver, checkInTask, categoryName);
						app_logs.info("Create Task: " + checkInTask);
						checkInDueTime=taskmang.getTaskDueTime(driver, checkInTask);
						taskmang.enableAutoCreate(driver, checkInTask, true);
						testSteps.add("Enable Auto Create");
						app_logs.info("Enable Auto Create");
						taskmang.setFrequency(driver, "Every Day", checkInTask);
						testSteps.add("Set Frequency : <b>" + "Every Day" +"</b>");
						app_logs.info(" Set Frequency :" + "Every Day");
						testSteps.add("Task Created : <b>" + checkInTask +"</b>");
						app_logs.info(" Task Created: " + checkInTask);
						
						boolean isTaskExist1=taskmang.isTaskThere(driver, checkInTask);
						if(isTaskExist1) {
						 taskAndEnabledOrNot.put(checkInTask, isTaskExist1);
						 systemTaskDueTime.put(checkInTask,checkInDueTime);
						 systemTaskFequency.put(checkInTask, "Every Day");
						 systemTaskNamesEveryDay.add(checkInTask);
						}
						app_logs.info(taskAndEnabledOrNot);
						app_logs.info(systemTaskDueTime);
						app_logs.info(systemTaskFequency);
						app_logs.info(systemTaskNamesEveryDay);
					}catch (Exception e) {
						Utility.catchException(driver, e, "Create Category and Task", "Task Management ", "Task Management", testName,
								test_description, test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Create Category and Task", "Task Management ", "Task Management", testName, test_description,
								test_catagory, testSteps);
					}
					
					// Create Reservation
					try {
						testSteps.add("<b>==========Start Creating Reservation==========</b>");
						app_logs.info("==========Start Creating Reservation==========");
						confirmationNo=reservationPage.createBasicReservation(driver, checkInDate, checkOutDate, "1", "0", 
								"All", salutation, guestFirstName, guestLastName, "No",
								paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassName, true,false);		
						} catch (Exception e) {
						Utility.catchException(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName,
								test_description, test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName, test_description,
								test_catagory, testSteps);
					}
						
					// Check In
					try {
						testSteps.add("<b>==========Start Check In==========</b>");
						app_logs.info("==========Start Check In==========");
						reservationPage.clickCheckInButton(driver, testSteps);
						reservationPage.generatGuestReportToggle(driver, testSteps, "No");
						reservationPage.completeCheckInProcessSingleRev(driver, testSteps);
						reservationPage.verifySpinerLoading(driver);
						revSearch.closeReservation(driver);	
						app_logs.info("Close the Reservation");
						Wait.wait60Second();
					} catch (Exception e) {
						Utility.catchException(driver, e, "Failed to CheckIn", "Reservation", "Reservation", testName,
								test_description, test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Failed to CheckIn", "Reservation", "Reservation", testName, test_description,
								test_catagory, testSteps);
					}
					
					try {	
						test_description = "verifyAutoTaskWithfrequencyNLogs";

						testCase="Verify auto task created as per set frequency in task management for Multiple days reservation";
						if (!Utility.insertTestName.containsKey(testCase)) {
							Utility.insertTestName.put(testCase, testCase);
							Utility.reTry.put(testCase, 0);
						} else {
							Utility.reTry.replace(testCase, 1);
						}
						revSearch.basicSearch_WithResNumber(driver, confirmationNo);	
						driver.navigate().refresh();
						Wait.wait60Second();
						testSteps.add("<b>==========Verify Auto Task In Reservation==========</b>");
						app_logs.info("==========Verify Auto Task In Reservation==========");
						systemTaskNames.add(checkInTask);
						app_logs.info(systemTaskNames);
						for(int i=0;i<datesRange.size();i++) {
							for(Map.Entry<String, String> set:systemTaskDueTime.entrySet()) {
								String finalDatetime=datesRange.get(i)+" "+set.getValue();
								reservationPage.verifyReservationTask(driver, set.getKey(), "Automatically generated at checkin",finalDatetime,"To Do", testSteps,"");							
							}
						}
						if(allDatesRangeList.size()>3) {
						ArrayList<String> datesList = new ArrayList<String>();
						int j=0;
						do {							
							datesList.add(allDatesRangeList.get(j));
							if(j==1) {
								for(Map.Entry<String, String> set:systemTaskNamesEveryTwoDay.entrySet()) {
									String date =Utility.parseDate(allDatesRangeList.get(j+1), "dd/MM/yyyy", "MM/dd/ yy");
									String finalDatetime=date+" "+set.getValue();
									reservationPage.verifyReservationTask(driver, set.getKey(), "Automatically generated at checkin",finalDatetime,"To Do", testSteps,"");							
								}
								for(String str: datesList) {
							    	allDatesRangeList.remove(str);
							    }
								j=0;
								datesList.clear();
								continue;
							}							
							j++;	
							if(allDatesRangeList.size()==2) {
								break;
							}
						}while(j<allDatesRangeList.size());	}
						else {
							reservationPage.reservationTaskshouldNotAppeared(driver, systemTaskNamesOnlyEveryTwoDay.get(0));
							testSteps.add("Task Doesn't Displayed for task " + systemTaskNamesOnlyEveryTwoDay.get(0));
							app_logs.info("Task Doesn't Displayed for task " + systemTaskNamesOnlyEveryTwoDay.get(0));
						}
						
						
						/*testSteps.add("Verify auto task created as per set frequency in task management for Multiple days reservation"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850202' target='_blank'>"
								+ "Click here to open TestRail: 850202</a><br>");
						Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify auto task created as per set frequency in task management for Multiple days reservation");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
								Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
				
							comments.add("Verify auto task created as per set frequency in task management for Multiple days reservation");
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
					}catch (Exception e) {
						Utility.catchException(driver, e, "Verify AutoTask", "Reservation", "Reservation", testName,
								test_description, test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, " Verify AutoTask", "Reservation", "Reservation", testName, test_description,
								test_catagory, testSteps);
					}
					
					
					try {
						test_description = "verifyAutoTaskWithChangeTaskTypefromReservation<br>";

						testCase="Verify no error message for auto/manually created task when change task type from reservation";
						if (!Utility.insertTestName.containsKey(testCase)) {
							Utility.insertTestName.put(testCase, testCase);
							Utility.reTry.put(testCase, 0);
						} else {
							Utility.reTry.replace(testCase, 1);
						}
						testSteps.add("<b>==========Update Auto Task from Reservation==========</b>");
						app_logs.info("==========Update Auto Task from Reservation==========");
						reservationPage.updateTaskFromReservation(driver, checkInTask, updatedCatName, updatedTaskType, 
								updatedTaskDetails, "", "", "", updatedTaskStatus, testSteps);						
						testSteps.add("<b>==========Verify Updated Auto Task from Reservation==========</b>");
						app_logs.info("==========verify Updated Auto Task from Reservation==========");
						getTest_Steps = reservationPage.VerifyCreatedTask(driver, updatedTaskType, updatedTaskDetails, updatedTaskStatus, getTest_Steps);
						testSteps.addAll(getTest_Steps);
						
						/*testSteps.add("Verify no error message for auto/manually created task when change task type from reservation"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850205' target='_blank'>"
								+ "Click here to open TestRail: 850205</a><br>");
						Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify no error message for auto/manually created task when change task type from reservation");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
								Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
				
						comments.add("Verify no error message for auto/manually created task when change task type from reservation");
						for(int i=0;i<Utility.testId.size();i++) {
							Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Dirty room for Group Reservation and verify checkout functionality");
						}
						
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
					}catch (Exception e) {
						Utility.catchException(driver, e, "Change task type from reservation", "task change", "task change", testName, test_description,
								test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Change task type from reservation", "task change", "task change", testName, test_description,
								test_catagory, testSteps);
					}
					try
					{
						revSearch.closeReservation(driver);	
						app_logs.info("Close the Reservation");
						testSteps.add("========== Delete Category ==========");
						app_logs.info("========== Delete Category ==========");
						navigation.clickSetup(driver);
						testSteps.add("Click on Setup");
						app_logs.info("Click on Setup");
						navigation.taskManagement_TabExist(driver);
						testSteps.add("Task Management Tab Exist");
						navigation.taskManagement(driver);
						testSteps.add("Click on Task Management");
						app_logs.info("Click on Task Management");
						taskmang.deleteAllCategories(driver, categoryName,testSteps);
						 RetryFailedTestCases.count = Utility.reset_count;
					     Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					}catch (Exception e) {
						Utility.catchException(driver, e, "Delete Catgory", "Task Management", "Task Management", testName,
								test_description, test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Delete Catgory", "Task Management", "Task Management", testName, test_description,
								test_catagory, testSteps);
					}
		}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAutoTaskWithfrequencyNLog", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
