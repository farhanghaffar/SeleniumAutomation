package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GSVerifyArrivalDueAndAutoTaskForGroupReservation extends TestCore {
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
	CPReservationPage reservation = new CPReservationPage();
	ReservationSearch rsvSearch = new ReservationSearch();
	TaskManagement taskmang = new TaskManagement();
	ReservationSearch revSearch = new ReservationSearch();
	Groups group= new Groups();
	String roomClassNames = null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null, checkInTask=null,checkOutTask=null,
			checkInDueTime=null,checkOutDueTime=null,checkInDateTime=null,checkOutDateTime=null;

	
	private void verifyArrivalDue(String roomNo, String roomClassName) {
		try {
			testSteps.add("========== Verify Arrival Due On GS for Room Number "+roomNo+"==========");
			app_logs.info("========== Verify Arrival Due On GS for Room Number "+roomNo+"==========");	
			navigation.clickOnGuestServices(driver, testSteps);
			testSteps.add("Navigated to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);
			roomstatus.verifyArrivalDueofSpecificRoomNoAndRoomClass(driver, roomNo, roomClassName, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Arrival Due", "GS", "GS", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Arrival Due", "GS", "GS", testName, test_description,
					test_catagory, testSteps);
		}
	}
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSVerifyArrivalDueAndAutoTaskForGroupReservation(String checkInDate, String checkOutDate, 
			String roomClassName, String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName, String rate,
			String salutation,String accountName, String groupFirstName, String groupLastName, String groupPhone, 
			String groupAddress,String groupCity,String groupState ,String groupCountry,String groupPostalcode,String marketSegment, String referral, String child,
			String paymentType,
			String cardNumber, String nameOnCard, String categoryName, String taskName) throws ParseException {
		String testCaseID="850182|850203";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "GSVerifyArrivalDueAndAutoTaskForGroupReservation";

		test_catagory = "GS Arrival Due";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("850182|850203", Utility.testId, Utility.statusCode, Utility.comments, "");
		String testCase=null;
		
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> roomNumber=new ArrayList<String>();
		List<String> roomNumbers=new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 
		// Login
			try {

				if (!(Utility.validateInput(checkInDate))) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
					
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
				}
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
				seasonStartDate=checkInDate;
				seasonEndDate=sessionEndDate.get(0);
				app_logs.info(checkInDate);
				app_logs.info(checkOutDate);
				app_logs.info(seasonStartDate);
				app_logs.info(seasonEndDate);
				
				if (!Utility.insertTestName.containsKey(testName)) {
					Utility.insertTestName.put(testName, testName);
					Utility.reTry.put(testName, 0);
				} else {
					//Utility.reTry.replace(testName, 1);
					Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
				}
				datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDate, checkOutDate);
				app_logs.info(datesRangeList);
				checkInDateTime=datesRangeList.get(1);
				app_logs.info(checkInDateTime);
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
			// Create Room Class
			try {
				testSteps.add("<b>======Start Creating New Room Class======</b>");
				navigation.clickSetup(driver);
				navigation.RoomClass(driver);
				testSteps.add("Navigated to Room Class");
				roomClassNames = roomClassName + Utility.fourDigitgenerateRandomString();
				newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults, maxPersopns,
						roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
				testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
				app_logs.info("Room Class Created: " + roomClassNames);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
						test_catagory, testSteps);
			}

			try
			{
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
				String randomNum=Utility.fourDigitgenerateRandomString();
				categoryName=categoryName+randomNum;
				taskmang.createTaskCategory(driver, categoryName,testSteps);
				app_logs.info("Created Category: " + categoryName);
				checkInTask=taskName+"_CheckIn_"+randomNum;
				app_logs.info(checkInTask);
				checkOutTask=taskName+"_CheckOut_"+randomNum;
				app_logs.info(checkOutTask);
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
				taskmang.createTask(driver, checkOutTask, categoryName);
				checkOutDueTime=taskmang.getTaskDueTime(driver, checkOutTask);			
				app_logs.info("Create Task: " + checkOutTask);
				taskmang.enableAutoCreate(driver, checkOutTask, true);
				testSteps.add("Enable Auto Create");
				app_logs.info("Enable Auto Create");
				taskmang.setFrequency(driver, "None", checkOutTask);
				testSteps.add("Set Frequency : <b>" + "None" +"</b>");
				app_logs.info(" Set Frequency :" + "None");
				taskmang.scheduleOnCheckOut(driver, checkOutTask, true);
				testSteps.add("Schedule On CheckOut");
				app_logs.info("Schedule On CheckOut");
				testSteps.add("Task Created : <b>" + checkOutTask +"</b>");
				app_logs.info(" Task Created: " + checkOutTask);			
			}catch (Exception e) {
				Utility.catchException(driver, e, "Create Category and Task", "Task Management ", "Task Management", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Category and Task", "Task Management ", "Task Management", testName, test_description,
						test_catagory, testSteps);
			}
			
			// Create Season
			try {
				testSteps.add("<b>======Start Updating Rate Plan ======</b>");
				nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName,datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", 
						"", "", "", true);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
						test_catagory, testSteps);
			}
			
			// Create  Groups
						try {
							testSteps.add("<b>======Start Opening Group OR Creating Groups ======</b>");
							navigation.cpReservation_Backward(driver);
							testSteps.add("Navigate to Reservation");
							app_logs.info("Navigate to Reservation");
							navigation.groups(driver);
							testSteps.add("Navigate to Group");
							app_logs.info("Navigate to Group");
							reservation.verifySpinerLoading(driver);
							boolean isGroupExist=group.isGroupExist(driver);
							if(isGroupExist) {
								app_logs.info("Group Exist");
								group.openGroup(driver, testSteps, "");
								reservation.verifySpinerLoading(driver);
							}else {
								group.createGroupAccount(driver, test, accountName, marketSegment, referral, groupFirstName, groupLastName, groupPhone, groupAddress, groupCity, groupState, groupCountry, groupPostalcode, testSteps);
							}
							} catch (Exception e) {
							Utility.catchException(driver, e, "Start Opening Group OR Creating Groups ", "Rate Plan", "Rate Plan", testName,
									test_description, test_catagory, testSteps);
						} catch (Error e) {
							Utility.catchError(driver, e, "Start Opening Group OR Creating Groups", "Rate Plan", "Rate Plan", testName, test_description,
									test_catagory, testSteps);
						}
						
						
						// Create Reservation  from Groups
						try {
							test_description = "GSVerifyArrivalDueScenariosForGroup<br>"
									+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850182' target='_blank'>"
									+ "Click here to open TestRail: 850182</a><br>";

							testCase="Verify Arrival Due label on the Room Status tile ->Groups";
							if (!Utility.insertTestName.containsKey(testCase)) {
								Utility.insertTestName.put(testCase, testCase);
								Utility.reTry.put(testCase, 0);
							} else {
								Utility.reTry.replace(testCase, 1);
							}
							testSteps.add("<b>======Create Reservation  from Groups ======</b>");
							group.clickOnNewReservationButtonFromGroup(driver, testSteps);
							reservation.select_CheckInDate(driver, testSteps, checkInDate);
							reservation.select_CheckoutDate(driver, testSteps, checkOutDate);
							reservation.enter_Adults(driver, testSteps, maxAdults);
							reservation.enter_Children(driver, testSteps, child);
							reservation.select_Rateplan(driver, testSteps, ratePlanName, "");
							String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
							String reservationNo=null;
							confirmationNo=reservation.createGroupReservation(driver, testSteps, roomClassNames, "", "", 
									salutation,groupFirstName, groupLastName, "No", paymentType,
									cardNumber, nameOnCard, expiryDate, "No", 
									"Single", roomNumber, reservationNo);
							app_logs.info(roomNumber);
							app_logs.info(confirmationNo);
							roomNumbers= reservation.getStayInfoRoomNo(driver, testSteps);
							app_logs.info("Staty Info Room Nos Are: "+roomNumbers);
							revSearch.closeReservation(driver);	
							app_logs.info("Close the Reservation");
							verifyArrivalDue(roomNumbers.get(0),roomClassNames);
						} catch (Exception e) {
							Utility.catchException(driver, e, "Create Reservation  from Groups", "Rate Plan", "Rate Plan", testName,
									test_description, test_catagory, testSteps);
						} catch (Error e) {
							Utility.catchError(driver, e, "Create Reservation  from Groups", "Rate Plan", "Rate Plan", testName, test_description,
									test_catagory, testSteps);
						}
						
						try {
							navigation.navigateToReservationFromGuestServices(driver, testSteps);
							reservation.verifySpinerLoading(driver);
							app_logs.info(confirmationNo);
							revSearch.basicSearch_WithResNumber(driver, confirmationNo);	
							testSteps.add("====================Move Room No"+roomNumbers.get(0)+"to Another Room======================");	
							reservation.clickOnDetails(driver);
							reservation.ClickEditStayInfo(driver, testSteps);
							reservation.ClickStayInfo_ChangeDetails(driver, testSteps);
							reservation.clickFindRooms(driver);
							reservation.verifySpinerLoading(driver);
							ArrayList<String> rooms =reservation.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClassName);
							reservation.selectRoomToReserve(driver, testSteps, roomClassNames, rooms.get(2));
							reservation.verifySpinerLoading(driver);
							reservation.clickYesInPolicyPopUp(driver);
							reservation.clickSaveAfterEditStayInfo(driver);
							reservation.verifySpinerLoading(driver);
							revSearch.closeReservation(driver);	
							app_logs.info("Close the Reservation");
							verifyArrivalDue(rooms.get(2),roomClassNames);
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
							Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verified Arrival due status for group");
						    Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
						}catch (Exception e) {
							Utility.catchException(driver, e, "Move room One to another room and verify Status", "GS", "GS", testName,
									test_description, test_catagory, testSteps);
						} catch (Error e) {
							Utility.catchError(driver, e, "Move room One to another room and verify Status", "GS", "GS", testName, test_description,
									test_catagory, testSteps);
						}
						
						try {
							test_description = "GSVerifyAutoTaskForGroup<br>"
									+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850203' target='_blank'>"
									+ "Click here to open TestRail: 850203</a><br>";
							testCase="Verify auto task for group reservation";
							if (!Utility.insertTestName.containsKey(testCase)) {
								Utility.insertTestName.put(testCase, testCase);
								Utility.reTry.put(testCase, 0);
							} else {
								Utility.reTry.replace(testCase, 1);
							}
							navigation.navigateToReservationFromGuestServices(driver, testSteps);					
							revSearch.basicSearch_WithResNumber(driver, confirmationNo);	
							reservation.clickCheckInButton(driver, testSteps);
							reservation.generatGuestReportToggle(driver, testSteps, "No");
							reservation.completeCheckInProcessSingleRev(driver, testSteps);
							reservation.verifySpinerLoading(driver);
							revSearch.closeReservation(driver);	
							app_logs.info("Close the Reservation");
							Wait.wait60Second();
							revSearch.basicSearch_WithResNumber(driver, confirmationNo);	
							driver.navigate().refresh();
							Wait.wait60Second();
							testSteps.add("<b>==========Verify Auto Task In Reservation==========</b>");
							checkInDateTime=Utility.parseDate(checkInDateTime, "dd/MM/yyyy", "MM/dd/ yy");
							app_logs.info(checkInDateTime);
							checkOutDateTime=Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MM/dd/ yy");
							app_logs.info(checkOutDateTime);
							String finalCheckInDateTime=checkInDateTime+" "+checkInDueTime;
							app_logs.info(finalCheckInDateTime);
							String finalCheckOutDateTime=checkOutDateTime+" "+checkOutDueTime;
							app_logs.info(finalCheckOutDateTime);
							reservation.verifyReservationTask(driver, checkInTask, "Automatically generated at checkin",finalCheckInDateTime,"To Do", testSteps,"");
							reservation.verifyReservationTask(driver, checkOutTask, "Automatically generated at checkin",finalCheckOutDateTime,"To Do", testSteps,"");
							revSearch.closeReservation(driver);	
							app_logs.info("Close the Reservation");
							Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "GSVerifyAutoTaskForGroup");
						    Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);					
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
						}catch (Exception e) {
							Utility.catchException(driver, e, "Start CheckIn and Verify AutoTask", "Reservation", "Reservation", testName,
									test_description, test_catagory, testSteps);
						} catch (Error e) {
							Utility.catchError(driver, e, "Start CheckIn and Verify AutoTask", "Reservation", "Reservation", testName, test_description,
									test_catagory, testSteps);
						}
						
						try
						{
							testSteps.add("========== Delete Category ==========");		
							navigation.clickSetup(driver);
							testSteps.add("Navigated to Setup");
							navigation.taskManagement_TabExist(driver);
							testSteps.add("Task Management Tab Exist");
							navigation.taskManagement(driver);
							testSteps.add("Click on Task Management");
							app_logs.info("Click on Task Management");
							taskmang.deleteAllCategories(driver, categoryName,testSteps);
							navigation.navigateToSetupfromTaskManagement(driver);
							navigation.RoomClass(driver);
							newRoomClass.searchRoomClassV2(driver, roomClassName);
							testSteps.add("Click on Search Button");
							app_logs.info("Click on Search Button");
							newRoomClass.deleteAllRoomClassV2(driver, roomClassName);
							testSteps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
							app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);
							 RetryFailedTestCases.count = Utility.reset_count;
						     Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						}catch (Exception e) {
							Utility.catchException(driver, e, "Delete Category", "Task Management", "Task Management", testName,
									test_description, test_catagory, testSteps);
						} catch (Error e) {
							Utility.catchError(driver, e, "Delete Category", "Task Management", "Task Management", testName, test_description,
									test_catagory, testSteps);
						}
	}
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSVerifyArrivalDueNAutoTaskGRP", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	
	}
}
