package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAddDeleteAndMoveOfTask extends TestCore {
	

	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	Properties properties = new Properties();
	RoomClass roomClass = new RoomClass();
	Reports report = new Reports();
	Rate rate = new Rate();
	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	
	CPReservationPage reservationPage = new CPReservationPage();
	ReservationSearch revSearch = new ReservationSearch();
	TaskManagement taskmang = new TaskManagement();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	String  randomNum = null,yearDate = null, reservation = null, categoryName=null, checkInTask=null,checkOutTask=null,
					checkInDateTime=null,checkOutDateTime=null,checkInDueTime=null,checkOutDueTime=null;;
			
	String testName = this.getClass().getSimpleName().trim();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
		
	}
	
	private void handelingCatchError(Error e, String desc, String category, String module) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	private void handelingCatchException(Exception e, String desc, String category, String module) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	@Test(dataProvider = "getData", groups = "TaskManagement")
	public void verifyAutoCreationTasksforReservation(String category,String taskName, String maxAdults,
			 String checkInDate, String checkOutDate, String salutation,String guestFirstName, String guestLastName, 
			 String paymentType, String cardNumber, String nameOnCard,
			String marketSegment, String referral,String rateplan,String roomClass) throws Exception
	{
		
		
        String testcaseId="848169";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848169");
		
		test_description = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848169' target='_blank'>"
				+ "Click here to open TestRail: C848169</a><br>"
				+ "<br>";
				
		
		test_catagory = "Task Management";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		randomNum = Utility.GenerateRandomNumber();
		categoryName=category;
		String randomNum1=Utility.GenerateRandomNumber(),randomNum2=Utility.GenerateRandomNumber(),
				category_1=category+randomNum1,category_2=category+randomNum2;
		String Task_1=taskName+randomNum1;
		
		
		
	

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

		
			// Get CheckIN and Checkout Date

			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			checkInDateTime=Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
			app_logs.info(checkInDateTime);
			
			
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Get Check In Date and Checkout Date", testName, "Pre-Requisites");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Get Check In Date and Checkout Date", testName, "Pre-Requisites");
		}

		// Login
		try {
			driver = getDriver();
			login_Autoota(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
		} catch (Exception e) {
			handelingCatchException(e, "Failed to login", testName, "Login");
		} catch (Error e) {
			handelingCatchError(e, "Failed to login", testName, "Login");
		}
        
		try
		{
			test_steps.add("========== Delete Category ==========");
			navigation.setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.taskManagement_TabExist(driver);
			test_steps.add("Task Management Tab Exist");
			navigation.taskManagement(driver);
			test_steps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			taskmang.deleteAllCategories(driver, categoryName,test_steps);
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Delete Category", testName, "Task Management");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Delete Category", testName, "Task Management");
		}
		
		try
		{
			test_steps.add("========== Create Category and New Task ==========");
			categoryName=categoryName+"_"+randomNum;
			taskmang.createTaskCategory(driver, categoryName,test_steps);
			app_logs.info("Created Category: " + categoryName);
			checkInTask=taskName+"_CheckIn_"+randomNum;
			app_logs.info(checkInTask);
			checkOutTask=taskName+"_CheckOut_"+randomNum;
			app_logs.info(checkOutTask);
			taskmang.createTask(driver, checkInTask, categoryName);
			app_logs.info("Create Task: " + checkInTask);
			checkInDueTime=taskmang.getTaskDueTime(driver, checkInTask);
			taskmang.enableAutoCreate(driver, checkInTask, true);
			test_steps.add("Enable Auto Create");
			app_logs.info("Enable Auto Create");
			taskmang.setFrequency(driver, "Every Day", checkInTask);
			test_steps.add("Set Frequency : <b>" + "Every Day" +"</b>");
			app_logs.info(" Set Frequency :" + "Every Day");
			test_steps.add("Task Created : <b>" + checkInTask +"</b>");
			app_logs.info(" Task Created: " + checkInTask);
			taskmang.createTask(driver, checkOutTask, categoryName);
			checkOutDueTime=taskmang.getTaskDueTime(driver, checkOutTask);			
			app_logs.info("Create Task: " + checkOutTask);
			taskmang.enableAutoCreate(driver, checkOutTask, true);
			test_steps.add("Enable Auto Create");
			app_logs.info("Enable Auto Create");
			taskmang.setFrequency(driver, "None", checkOutTask);
			test_steps.add("Set Frequency : <b>" + "None" +"</b>");
			app_logs.info(" Set Frequency :" + "None");
			taskmang.scheduleOnCheckOut(driver, checkOutTask, true);
			test_steps.add("Schedule On CheckOut");
			app_logs.info("Schedule On CheckOut");
			test_steps.add("Task Created : <b>" + checkOutTask +"</b>");
			app_logs.info(" Task Created: " + checkOutTask);
			
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Create New Category and task", testName, "Task Management");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create New Category and task", testName, "Task Management");
		}
		
	
		
				
				try
				{
					test_steps.add("========== MoveTask  ==========");
					
					taskmang.createTaskCategory(driver, category_1, test_steps);
					test_steps.add(category_1+" category is created");
					taskmang.createTaskCategory(driver, category_2, test_steps);
					test_steps.add(category_2+" category is created");
					taskmang.createTask(driver, Task_1, category_1);
					test_steps.add(Task_1+" task is created");
					taskmang.MoveTask(driver, Task_1, category_1,category_2);
					test_steps.add(Task_1+"is moved from"+category_1+" to "+ category_2);
					
					statusCode.set(0, "1");
					comments.add(0, "Add,deleet and move of task is done");
		
				}catch (Exception e) {
					handelingCatchException(e, "Failed to Delete Category", testName, "Task Management");
				} catch (Error e) {
					handelingCatchError(e, "Failed to Delete Category", testName, "Task Management");
				}
				
				try {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed", testName, "Navigate", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed", testName, "Navigate", driver);
					} else {
						Assert.assertTrue(false);
					}
				}		
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("AddDeleteAndMoveOfTask", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}



}
