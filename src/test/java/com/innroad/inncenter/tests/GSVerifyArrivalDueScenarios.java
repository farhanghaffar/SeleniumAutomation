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
import com.innroad.inncenter.waits.Wait;

public class GSVerifyArrivalDueScenarios extends TestCore {

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
	String roomClassNames = null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null;
	private void checkinCheckoutAndRollBack() throws Exception {
		reservation.clickCheckInButton(driver, testSteps);
		reservation.generatGuestReportToggle(driver, testSteps, "No");
		reservation.completeCheckInProcess(driver, testSteps);		
		reservation.verifyCheckOutButton(driver, testSteps);
		reservation.clickCheckOutButton(driver, testSteps);
		reservation.generatGuestReportToggle(driver, testSteps, "No");
		boolean isPaymentWindow = reservation.getPaymentWindow(driver);
		if (isPaymentWindow) {
			reservation.proceedToCheckOutPayment(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
		}
		reservation.verifyRollBackButton(driver, testSteps);
		reservation.clickRollBackButton(driver, testSteps);
		reservation.verifyRollBackMsg(driver, testSteps,config.getProperty("rollBackMessage"));
		reservation.clickYesButtonRollBackMsg(driver, testSteps);
		reservation.toasterMsg(driver, testSteps);
		reservation.closeReservationTab(driver);
	}

	private void verifyRoomStatus(String status) {
		// Verify Room Status on GS
		try {
			testSteps.add("<b>======Start Searching By RoomClass and Verify Room Class Status======</b>");
			navigation.navigateGuestservice(driver);
			testSteps.add("Navigated to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);
			roomstatus.searchByRoom(driver, roomClassNames, testSteps);
			testSteps.add("Successfull Dispalyed RoomClassName: <b>" + roomClassNames + " On GS</b>");
			app_logs.info("Successfull Dispalyed RoomClassName: " + roomClassNames + " On GS");
			roomstatus.verifyArrivalDueStatus(driver, status, testSteps);
		/*	Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verified Room Class Status");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		*/} catch (Exception e) {
			Utility.catchException(driver, e, "Search Room Class and Verify Status", "GS", "GS", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Search Room Class and Verify Status", "GS", "GS", testName, test_description, test_catagory,
					testSteps);
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
	public void gSVerifyArrivalDueScenarios(String checkInDate, String checkOutDate, 
			String roomClassName, String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, 
			String cardNumber,String nameOnCard,String marketSegment, String referral, String child) throws ParseException {
		String testCaseID="850172|850171|850185|848443|848177|848499|848226";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "GSVerifyArrivalDueScenarios";
		test_description = "GSVerifyArrivalDueScenarios<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850172' target='_blank'>"
				+ "Click here to open TestRail: 850172</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850171' target='_blank'>"
				+ "Click here to open TestRail: 850171</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850185' target='_blank'>"
				+ "Click here to open TestRail: 850185</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848443' target='_blank'>"
				+ "Click here to open TestRail: 848443</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848177' target='_blank'>"
				+ "Click here to open TestRail: 848177</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848499' target='_blank'>"
				+ "Click here to open TestRail: 848499</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848226' target='_blank'>"
				+ "Click here to open TestRail: 848226</a><br>";

		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("850172|850171|850185|848443|848177|848499|848226", Utility.testId, Utility.statusCode, Utility.comments, "");
		String testCase=null;
		
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 List<String> rooms= new ArrayList<String>();
		// Login
		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
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
		// Create Reservation and Verify Room Status of GS with Arrival Due
				try {
					testCase="Search Room Status for Arrival Due and filter for Clean rooms";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}
					confirmationNo=reservation.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child, 
							ratePlanName, salutation, guestFirstName, guestLastName, "No",
							paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassNames, true,true);					
					verifyRoomStatus("Clean");
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
					/*Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verified Room Class Status as Clean");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		*/		} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				
				// Checkin Reservation and Checkout and RollBack and 
				try {
					navigation.navigateToReservationFromGuestServices(driver, testSteps);
					rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
					checkinCheckoutAndRollBack();	
					Utility.refreshPage(driver);
					Wait.wait10Second();
					rsvSearch.basicSearchResNumber(driver, confirmationNo);
					rsvSearch.bulkActionCancellation(driver);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				// Create Reservation
				try {
					testCase="Search Room Status for Arrival Due and filter for Dirty rooms";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}
					confirmationNo=reservation.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child, 
							ratePlanName, salutation, guestFirstName, guestLastName, "No",
							paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassNames, false,false);	
					rooms=reservation.getStayInfoRoomNo(driver, testSteps);
					reservation.closeReservationTab(driver);
					verifyRoomStatus("Dirty");
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			/*		Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verified Room Class Status as Dirty");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			*/	} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				
				// Checkin Reservation and Verify Message
				try {
					testCase="Verify that the confirmation Pop-up should come while checkin to Suite which has dirty status";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}
					navigation.navigateToReservationFromGuestServices(driver, testSteps);
					rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
					reservation.clickCheckInButton(driver, testSteps);
					reservation.generatGuestReportToggle(driver, testSteps, "No");	
					reservation.clickOnConfirmCheckInButton(driver, testSteps);
					String message="The room status is dirty. Do you wish to proceed with check in?";
					reservation.verifyRollBackMsg(driver, testSteps,message);
					/*Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verified Confirmation Message for Dirty room while checkin");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
				
					Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify the dirty room status pop-up message when user click on confirm check-in button");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);					
				
					Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify the dirty room status pop-up message when user click on confirm check-in button");				
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);
					Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verified Confirmation Message for Dirty room while checkin and checkin Successfully");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
				*/	reservation.dirtyPopupModelClickCancelButton(driver, testSteps);
					reservation.clickCloseRollBackMsg(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.closeReservationTab(driver);
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
					} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				
				try {
					 
					testSteps.add("<b>======Make the room status to Clean and try to check in the reservation ======</b>");
					testCase="Make the room status to Clean and try to check in the reservation";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}
					navigation.navigateGuestservice(driver);
					roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
					roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);
					roomstatus.searchByRoomHashAndChangeRoomStatus(driver, rooms.get(0), roomClassNames, "Clean", testSteps);
					navigation.navigateToReservationFromGuestServices(driver, testSteps);
					rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);	
					reservation.clickCheckInButton(driver, testSteps);
					reservation.generatGuestReportToggle(driver, testSteps, "No");	
					reservation.clickOnConfirmCheckInButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.VerifyReservationStatus_Status(driver, "IN-HOUSE");
					/*Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Make the room status to Clean and try to check in the reservation");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
					testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/848226' target='_blank'>"				
							+ "Click here to open TestRail: 848226</a><br>");
					Utility.testCasePass(Utility.statusCode, 6, Utility.comments, "Make the room status to Clean and try to check in the reservation");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);
				
				*/	for(int i=0;i<Utility.statusCode.size();i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Arrival Due");
				}
				
				Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
						Utility.comments, TestCore.TestRail_AssignToID);

					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Change room status to clean", "GS", "GS", testCase,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Change room status to clean", "GS", "GS", testCase, test_description,
							test_catagory, testSteps);
				}
				// Delete Room Class
				try {
					testSteps.add("<b>======Delete Room Class======</b>");
					navigation.navigateToSetupFromReservationPage(driver, testSteps);
					testSteps.add("Navigated to Setup");
					navigation.RoomClass(driver);
					newRoomClass.searchRoomClassV2(driver, roomClassName);
					testSteps.add("Click on Search Button");
					app_logs.info("Click on Search Button");
					newRoomClass.deleteAllRoomClassV2(driver, roomClassName);
					testSteps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
					app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Delete Room Class", "GS", "GS", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Delete Room Class", "GS", "GS", testName, test_description, test_catagory,
							testSteps);
				}
				
	}
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSVerifyArrivalDueScenarios", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		
	}


}
