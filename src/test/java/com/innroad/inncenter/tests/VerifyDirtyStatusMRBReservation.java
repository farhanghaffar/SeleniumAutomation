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
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyDirtyStatusMRBReservation extends TestCore{

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
	NightlyRate nightlyRate= new NightlyRate();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	private void verifyRoomStatus(String status, List<String> roomNo, String roomClass) {
		// Verify Room Status on GS
		try {
			testSteps.add("<b>======Start Searching By RoomClass and Verify Room Class Status======</b>");
			navigation.navigateGuestservice(driver);
			testSteps.add("Navigated to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);			
		    roomstatus.searchByRoom(driver, roomClass, testSteps);
			testSteps.add("Successfull Dispalyed RoomClassName: <b>" + roomClass + " On GS</b>");
			app_logs.info("Successfull Dispalyed RoomClassName: " + roomClass + " On GS");	
			for(String str: roomNo) {
			roomstatus.verifyRoomTileStatus(driver, str, roomClass, status, testSteps);
			}			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Search Room Class and Verify Status", "GS", "GS", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Search Room Class and Verify Status", "GS", "GS", testName, test_description, test_catagory,
					testSteps);
		}	
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void verifyDirtyStatusMRBReservation(String checkInDate, String checkOutDate, 
			String roomClassName, String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, 
			String cardNumber,String nameOnCard,String marketSegment, String referral, String adult,String child) throws ParseException {
		String testCaseID="848551|848552";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "GS: Verify room is displaying as dirty once we check in the room using Check in All button for MRB reservation";
		test_description = "VerifyDirtyStatusMRBReservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848551' target='_blank'>"
				+ "Click here to open TestRail: 848551</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848552' target='_blank'>"
				+ "Click here to open TestRail: 848552</a><br>";

		test_catagory = "GS Dirty Status";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848551|848552", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> roomNumber = new ArrayList<String>();
		List<String> roomNumbers = new ArrayList<String>();
		String roomClassNames = null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null;
		// Get checkIN and Checkout Date
				try {

					if (!(Utility.validateInput(checkInDate))) {
						for (int i = 0; i < guestFirstName.toString().split("\\|").length; i++) {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));	}
							sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
					} else {
						checkInDates = Utility.splitInputData(checkInDate);
						checkOutDates = Utility.splitInputData(checkOutDate);
					}
					checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
					checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
					seasonStartDate=checkInDates.get(0);
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

				// Create Season
				try {
					testSteps.add("<b>======Start Updating Rate Plan ======</b>");
					ArrayList<String> ratePlanNames=Utility.splitInputData(ratePlanName);
					nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanNames.get(0),datesRangeList,
							seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", 
							"", "", "", true);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				}
				
				// Create MRB Reservation and Verify Room Status of GS with Arrival Due
				try {						
					roomClassName=roomClassNames+ "|" + roomClassNames;
					confirmationNo=reservation.createBasicMRBReservation(driver, true, checkInDate, checkOutDate,
							adult, child, ratePlanName, roomClassName, salutation, guestFirstName, 
							guestLastName,  "No", roomNumber, paymentType, 
							cardNumber, nameOnCard, referral, false,testSteps);
					roomNumbers=reservation.getStayInfoRoomNo(driver, testSteps);	
					reservation.clickCheckInAllButton(driver, testSteps);
					reservation.generatGuestReportToggle(driver, testSteps, "No");
					reservation.completeCheckInProcess(driver, testSteps);	
					reservation.closeReservationTab(driver);
					verifyRoomStatus("Dirty",roomNumbers,roomClassNames);					
					/*Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verified Room Status as Dirty");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
					Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verified Room Class Status as Dirty");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
					*/
					for(int i=0;i<Utility.statusCode.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Rooms Status as Dirty for MRB");
					}
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}

		}
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyDirtyStatusMRBReservation", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
