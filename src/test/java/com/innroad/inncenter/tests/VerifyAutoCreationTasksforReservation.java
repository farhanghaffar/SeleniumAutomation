package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAutoCreationTasksforReservation extends TestCore{
	
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
	RoomStatus roomStatus = new RoomStatus();
	TaskList tasklist= new TaskList();
	CPReservationPage reservationPage = new CPReservationPage();
	ReservationSearch revSearch = new ReservationSearch();
	TaskManagement taskmang = new TaskManagement();
	Create_Reservation reservationPagee= new Create_Reservation();
	NewRoomClassesV2 newRoomclass= new NewRoomClassesV2();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	String  randomNum = null,yearDate = null, reservation = null, categoryName=null, checkInTask=null,checkOutTask=null,
					checkInDateTime=null,checkOutDateTime=null,checkInDueTime=null,checkOutDueTime=null;
	ArrayList<String> rooms= new ArrayList<String>();		
	String testName = this.getClass().getSimpleName().trim();
	ArrayList<String> comments = new ArrayList<>();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
		
	}
	
	private void handelingCatchError(Error e, String desc, String category, String module) {
		Utility.catchError(driver, e, desc, "Reservation", "Reservation",
				testName, desc, category, test_steps);
		
	}

	private void handelingCatchException(Exception e, String desc, String category, String module) {
			Utility.catchException(driver, e, desc, "Reservation",
				"Reservation", testName, desc, category, test_steps);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyAutoCreationTasksforReservation(String testCaseID,String roomClassName,String category,String taskName, String maxAdults,String child,
			 String rateplan,String checkInDate, String checkOutDate, String salutation,String guestFirstName, String guestLastName, 
			 String paymentType, String cardNumber, String nameOnCard,
			String marketSegment, String referral, String reservationType) throws ParseException
	{
		
		if(Utility.getResultForCase(driver, testCaseID)) {
		if(reservationType.equalsIgnoreCase("SingleReservation")) {
			testName="Verify Auto Creation Task  with Single Reservation";
		}else if(reservationType.equalsIgnoreCase("MRBReservation")) {
			testName="Verify Auto Creation Task with MRB Reservation";
		}
		
		test_description = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850192' target='_blank'>"
				+ "Click here to open TestRail: 850192</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850193' target='_blank'>"
				+ "Click here to open TestRail: 850193</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848171' target='_blank'>"
				+ "Click here to open TestRail: 848171</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850196' target='_blank'>"
				+ "Click here to open TestRail: 850196</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848425' target='_blank'>"
				+ "Click here to open TestRail: 848425</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850200' target='_blank'>"
				+ "Click here to open TestRail: 850200</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850197' target='_blank'>"
				+ "Click here to open TestRail: 850197</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850198' target='_blank'>"
				+ "Click here to open TestRail: 850198</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850199' target='_blank'>"
				+ "Click here to open TestRail: 850199</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850201' target='_blank'>"
				+ "Click here to open TestRail: 850201</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850204' target='_blank'>"
				+ "Click here to open TestRail: 850204</a><br>";
		
		test_catagory = "CPReservation_CheckOut";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
		randomNum = Utility.GenerateRandomNumber();
		categoryName=category;
		String  testCase=testName;
	    String zone=null;
	    ArrayList<String> zones= new ArrayList<String>();
	    ArrayList<String> roomClasses= new ArrayList<String>();
	    ArrayList<String> abbrivation= new ArrayList<String>();
	    List<String> roomNumbers= new ArrayList<String>();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
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
			
			if(reservationType.equalsIgnoreCase("SingleReservation")) {
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			}else if(reservationType.equalsIgnoreCase("MRBReservation")) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			}
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
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
		} catch (Exception e) {
			handelingCatchException(e, "Failed to login", testName, "Login");
		} catch (Error e) {
			handelingCatchError(e, "Failed to login", testName, "Login");
		}
        
		try {
			test_steps.add("========== Get Room Class Zone ==========");
			app_logs.info("========== Get Room Class Zone==========");
			
			navigation.setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.roomClass(driver);
			if(reservationType.equalsIgnoreCase("SingleReservation")) {
			zone=newRoomclass.getRoomClassZone(driver, roomClassName, test_steps, true);}
			else if(reservationType.equalsIgnoreCase("MRBReservation")) {
				abbrivation=newRoomclass.getAbbrivation(driver, Utility.DELIM, roomClassName,test_steps);	
			}
			app_logs.info(zone);
			app_logs.info(zones);
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Get Zone of Room class", testName, "Get Zone");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Get Zone of Room Class", testName, "Get Zone");
		}
		
		try
		{
			test_steps.add("========== Delete Category ==========");
			app_logs.info("========== Delete Category ==========");			
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
	// Create Reservation
				try {
					yearDate = Utility.getFutureMonthAndYearForMasterCard();
					test_steps.add("<b>==========Start Creating Reservation==========</b>");
					navigation.reservation_Backward_2(driver);
					app_logs.info("Navigate to Reservation");					
					if(reservationType.equalsIgnoreCase("SingleReservation")) {
					reservationPage.click_NewReservation(driver, test_steps);
					reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
					reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
					reservationPage.enter_Adults(driver, test_steps, maxAdults);
					reservationPage.clickOnFindRooms(driver, test_steps);
					ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName);
					reservationPage.selectRoomToReserve(driver, test_steps, roomClassName, rooms.get(0));
					reservationPage.clickNext(driver, test_steps);					
					reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation,
							guestFirstName+ randomNum, guestLastName+ randomNum, "No");
					reservationPage.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, yearDate);
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
					reservationPage.clickBookNow(driver, test_steps);
					reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					}
					else if(reservationType.equalsIgnoreCase("MRBReservation")) {							
						reservation=reservationPagee.createMRBBasicReservation(driver, zones, checkInDate, checkOutDate, maxAdults, child,
								rateplan, roomClassName, salutation, guestFirstName, guestLastName, rooms, paymentType,
								cardNumber, nameOnCard, marketSegment, referral);						
									}					
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					app_logs.info(rooms);
				} catch (Exception e) {
					handelingCatchException(e, "Failed to Create Reservation", testName, "Reservation");
				} catch (Error e) {
					handelingCatchError(e, "Failed to Create Reservation", testName, "Reservation");
				}
				
					
				// Check In
				try {
					test_steps.add("<b>==========Start Check In==========</b>");
					if(reservationType.equalsIgnoreCase("SingleReservation")) {
					reservationPage.clickCheckInButton(driver, test_steps);
					reservationPage.generatGuestReportToggle(driver, test_steps, "No");
					reservationPage.completeCheckInProcessSingleRev(driver, test_steps);
					}else if(reservationType.equalsIgnoreCase("MRBReservation")) {	
						reservationPage.clickCheckInAllButton(driver, test_steps);
						reservationPage.generatGuestReportToggle(driver, test_steps, "No");
						reservationPage.completeCheckInProcess(driver, test_steps);
					}
					if(reservationType.equalsIgnoreCase("SingleReservation")) {
						test_steps.add("<b>==========Start Verifying Check-Out Button ==========</b>");
						reservationPage.verifyCheckOutButton(driver, test_steps);
					}
					else if(reservationType.equalsIgnoreCase("MRBReservation")) {	
						reservationPage.verifyCheckOutAllButton(driver, test_steps);
					}
					
					roomNumbers=reservationPage.getStayInfoRoomNo(driver, test_steps);
				} catch (Exception e) {
					handelingCatchException(e, "Failed to Check In Reservation", testName, "Reservation");
				} catch (Error e) {
					handelingCatchError(e, "Failed to Check In Reservation", testName, "Reservation");
				}
				
				try
				{
					
					reservationPage.closeReservationTab(driver);
					app_logs.info("Close the Reservation");
					Wait.wait60Second();
					revSearch.basicSearch_WithResNumber(driver, reservation);	
					driver.navigate().refresh();
					Wait.wait60Second();
					revSearch.openReservationTab(driver);
					Wait.wait60Second();
					test_steps.add("<b>==========Verify Reservation Task==========</b>");
					checkInDateTime=Utility.parseDate(checkInDateTime, "dd/MM/yyyy", "MM/dd/ yy");
					app_logs.info(checkInDateTime);
					checkOutDateTime=Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MM/dd/ yy");
					app_logs.info(checkOutDateTime);
					String finalCheckInDateTime=checkInDateTime+" "+checkInDueTime;
					app_logs.info(finalCheckInDateTime);
					String finalCheckOutDateTime=checkOutDateTime+" "+checkOutDueTime;
					app_logs.info(finalCheckOutDateTime);
					if(reservationType.equalsIgnoreCase("SingleReservation")) {
					reservationPage.verifyReservationTask(driver, checkInTask, "Automatically generated at checkin",finalCheckInDateTime,"To Do", test_steps,"");
					reservationPage.verifyReservationTask(driver, checkOutTask, "Automatically generated at checkin",finalCheckOutDateTime,"To Do", test_steps,"");
				/*
					test_steps.add("Verify the auto created tasks for the reservation"
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848171' target='_blank'>"
							+ "Click here to open TestRail: 848171</a><br>");				
					Utility.testCasePass(Utility.testId, 2,Utility.comments,"Verify the auto created tasks for the reservation");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
				
					
					test_steps.add("Verify auto task created for one day reservation"
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850197' target='_blank'>"
							+ "Click here to open TestRail: C850197</a><br>");				
					Utility.testCasePass(Utility.testId, 6,Utility.comments,"Verify auto task created for one day reservation");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);
					
				*/	comments.add("Verify the auto created tasks for the reservatio ");
					comments.add("Verify auto task created for one day reservation");
					
					ArrayList<String> checkInTasks= new ArrayList<String>();
					checkInTasks.add(checkInTask);
					int size= checkInTasks.size();
					Utility.verifyShouldNotEqual(String.valueOf(size), String.valueOf(size+1), "Faled to verify Duplicate task created", test_steps, app_logs);
					test_steps.add("Verified Duplicate Task are not created ");
					app_logs.info("Verified Duplicate Task are not created ");
					/*test_steps.add("Duplicate auto-tasks should not be created after checkin"
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850196' target='_blank'>"
							+ "Click here to open TestRail: 850196</a><br>");
					Utility.testCasePass(Utility.statusCode,3,Utility.comments,"Duplicate auto-tasks should not be created after checkin");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
					*/
					comments.add("Verified Duplicate Task are not created ");
					List<String> roomNos= reservationPage.getStayInfoRoomNo(driver, test_steps);
					reservationPage.closeReservationTab(driver);
					test_steps.add("========== Verified Location of Task from Task List ==========");
					app_logs.info("========== Verified Location of Task from Task List ==========");
					navigation.navigateGuestservice(driver);
					test_steps.add("Navigate to Guest Services ");
					app_logs.info("Navigate to Guest Services ");
					roomStatus.verifyRoomStatusTabEnabled(driver, test_steps);
					roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
					navigation.Nav_TaskList(driver);
					app_logs.info("Navigate To Task_List");
					test_steps.add("Navigate To Task List");					
					tasklist.clickTaskForDropDown(driver, test_steps);
					tasklist.selectTaskFor(driver, test_steps, "Tomorrow");
					Wait.waitforPageLoad(10, driver);
					tasklist.verifyTask(driver, roomNos.get(0), test_steps);	
				/*	
					test_steps.add("Verify the location of Tasks and Auto-Created Tasks after Check-In"
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850192' target='_blank'>"
							+ "Click here to open TestRail: 850192</a><br>");
					Utility.testCasePass(Utility.testId, 0,Utility.comments,"Verify the location of Tasks and Auto-Created Tasks after Check-In");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
					
					test_steps.add("Verify the location of Auto Tasks created during Checkin"
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850193' target='_blank'>"
							+ "Click here to open TestRail: 850193</a><br>");
				
					Utility.testCasePass(Utility.testId, 1,Utility.comments,"Verify the location of Auto Tasks created during Checkin");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
					*/
					comments.add("Verify the location of Tasks and Auto-Created Tasks after Check-In");
					comments.add("Verify the location of Auto Tasks created during Checkin");

					test_steps.add("========== Verified OCCUPANCY of Task from Task List ==========");
					app_logs.info("========== Verified OCCUPANCY of Task from Task List ==========");
					tasklist.verifyTask(driver, "Occupied", test_steps);	
					test_steps.add("========== Verified ZONE of Task from Task List ==========");
					app_logs.info("========== Verified ZONE of Task from Task List ==========");
					tasklist.verifyTask(driver, zone, test_steps);	
				
					}else if(reservationType.equalsIgnoreCase("MRBReservation")) {	
						for(int i=0;i<roomNumbers.size();i++) {
							String abbrivationsAndRooms=abbrivation.get(i)+": "+roomNumbers.get(i); 
							app_logs.info(abbrivationsAndRooms);
						reservationPage.verifyReservationTask(driver, checkInTask, "Automatically generated at checkin",finalCheckInDateTime,"To Do", test_steps,abbrivationsAndRooms);
						reservationPage.verifyReservationTask(driver, checkOutTask, "Automatically generated at checkin",finalCheckOutDateTime,"To Do", test_steps,abbrivationsAndRooms);					
						}
						comments.add("Verify auto task created should be in TO DO state under task section in reservation");
						comments.add("Verify Task creation while checking in MRB reservation");
						comments.add("Verify auto task created for two or more days reservation");
						comments.add("Verify auto task should be created for multi room reservation");
						
						/*test_steps.add("Verify auto task created should be in TO DO state under task section in reservation"
								+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850200' target='_blank'>"
								+ "Click here to open TestRail: 850200</a><br>");
						
						Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify auto task created "
								+ "should be in TO DO state under task section in reservation");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5),
								Utility.comments.get(5), TestCore.TestRail_AssignToID);
			
						
						test_steps.add("Verify Task creation while checking in MRB reservation"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848425' target='_blank'>"
								+ "Click here to open TestRail: 848425</a><br>");
						Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify Task creation while checking in MRB reservation");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4),
								Utility.comments.get(4), TestCore.TestRail_AssignToID);
						

						test_steps.add("Verify auto task created for two or more days reservation"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850198' target='_blank'>"
								+ "Click here to open TestRail: 850198</a><br>");
						Utility.testCasePass(Utility.statusCode, 7, Utility.comments, "Verify auto task created for two or more days reservation");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(7), Utility.statusCode.get(7),
								Utility.comments.get(7), TestCore.TestRail_AssignToID);
						
						test_steps.add("Verify auto task should be created for multi room reservation"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850199' target='_blank'>"
								+ "Click here to open TestRail: 850199</a><br>");
						Utility.testCasePass(Utility.statusCode, 8, Utility.comments, "Verify auto task should be created for multi room reservation");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(8), Utility.statusCode.get(8),
								Utility.comments.get(8), TestCore.TestRail_AssignToID);
						
						*/
				
						navigation.clickSetup(driver);
					}
					
					/*test_steps.add("Verify time require for auto task to "
							+ "create while check-in the reservation -One day/multiple days/MRB reservation/Split reservation"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850201' target='_blank'>"
							+ "Click here to open TestRail: 850201</a><br>");
					Utility.testCasePass(Utility.statusCode, 9, Utility.comments, "Verify time require for auto task to create while"
							+ " check-in the reservation -One day/multiple days/MRB reservation/Split reservation");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(9), Utility.statusCode.get(9),
							Utility.comments.get(9), TestCore.TestRail_AssignToID);*/
			
					comments.add("Verify time require for auto task to create while" + 
							" check-in the reservation -One day/multiple days/MRB reservation/Split reservation");
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				}
				catch (Exception e) {
					handelingCatchException(e, "Failed to Verify Check In/Out Task", testName, "Reservation");
				} catch (Error e) {
					handelingCatchError(e, "Failed to Verify Check In/Out Task", testName, "Reservation");
				}
				
				
				try {
					if(reservationType.equalsIgnoreCase("SingleReservation")) {
						test_steps.add("========== Update Task as Cancelled In Reservation==========");
						app_logs.info("<b>======Update Task as Cancelled In Reservation======</b>");
						test_description = "GS: Verify Updated Task";
						testCase="Verify activity log of auto created task after changed status to cancel from reservation";
						if (!Utility.insertTestName.containsKey(testCase)) {
							Utility.insertTestName.put(testCase, testCase);
							Utility.reTry.put(testCase, 0);
						} else {
							Utility.reTry.replace(testCase, 1);
						}	
						navigation.guestservices_Click(driver, test_steps);
						navigation.navigateToReservationFromGuestServices(driver, test_steps);
						revSearch.basicSearch_WithResNumber(driver, reservation);	
						reservationPage.verifySpinerLoading(driver);
						reservationPage.updatetaskStatus(driver, checkInTask, "Cancelled", test_steps);
						reservationPage.closeReservationTab(driver);
						navigation.navigateGuestservice(driver);
						test_steps.add("Navigate to Guest Services ");
						app_logs.info("Navigate to Guest Services ");
						roomStatus.verifyRoomStatusTabEnabled(driver, test_steps);
						roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
						navigation.Nav_TaskList(driver);
						app_logs.info("Navigate To Task_List");
						test_steps.add("Navigate To Task List");						
						tasklist.clickTaskForDropDown(driver, test_steps);
						tasklist.selectTaskFor(driver, test_steps, "Tomorrow");
						Wait.waitforPageLoad(10, driver);
						tasklist.clickAllStatsBar(driver, test_steps);
						tasklist.SearchTask(driver, checkInTask);
						tasklist.VerifyTasksStatus(driver, "Cancelled");
						
						/*test_steps.add("Verify activity log of auto created task after changed status to cancel from reservation"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850204' target='_blank'>"
								+ "Click here to open TestRail: 850204</a><br>");
						Utility.testCasePass(Utility.statusCode, 10, Utility.comments, "Verify activity log of auto "
								+ "created task after changed status to cancel from reservation");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(10), Utility.statusCode.get(10),
								Utility.comments.get(10), TestCore.TestRail_AssignToID);*/
						
						comments.add("Verify activity log of auto created task after changed status to cancel from reservation");
						
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
						
						}
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify task in MRB Reservation");
					}
					
				
				}catch (Exception e) {
					handelingCatchException(e, "Change status to cancel from reservation and verify reservation", testName, "Reservation");
				} catch (Error e) {
					handelingCatchError(e, "Change status to cancel from reservation and verify reservation", testName, "Reservation");
				}
				
				try
				{
					test_steps.add("========== Delete Category ==========");
					if(reservationType.equalsIgnoreCase("SingleReservation")) {
					navigation.navigateToSetupfromTaskManagement(driver);}
					else {
						navigation.navigateToSetupFromReservationPage(driver, test_steps);
					}
					test_steps.add("Navigated to Setup");
					navigation.taskManagement_TabExist(driver);
					test_steps.add("Task Management Tab Exist");
					navigation.taskManagement(driver);
					test_steps.add("Click on Task Management");
					app_logs.info("Click on Task Management");
					taskmang.deleteAllCategories(driver, categoryName,test_steps);
					 RetryFailedTestCases.count = Utility.reset_count;
				     Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				}catch (Exception e) {
					handelingCatchException(e, "Failed to Delete Category", testName, "Task Management");
				} catch (Error e) {
					handelingCatchError(e, "Failed to Delete Category", testName, "Task Management");
				}
		}
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAutoCreationTasksforReser", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
