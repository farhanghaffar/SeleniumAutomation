package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.By;
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
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;

public class CPVerifyDirtyMessageForUnassignedRoomReservation extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> gstestSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	NightlyRate nightlyRate = new NightlyRate();
	Navigation navigation = new Navigation();
	Tapechart tapechart = new Tapechart();
	ReservationSearch revSearch = new ReservationSearch();
	String testName = null, confirmationNo = null, seasonStartDate = null, seasonEndDate = null, roomClassAbb = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyDirtyMessageForUnassignedRoomReservation(String checkInDate, String checkOutDate,  String roomClassName, 
			String maxAdults,String maxPersopns, String roomQuantity,String ratePlanName, String rate,String salutation, String guestFirstName, String guestLastName,
			String paymentType, String cardNumber, String nameOnCard, String marketSegment, String referral,
			String child) throws ParseException {
		String testCaseID="848608|848503|848650";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String roomNo = null;
		test_name = "cPVerifyDirtyMessageForUnassignedRoomReservation";
		test_description = "Verify CheckIn Policies <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848608' target='_blank'>"
				+ "Click here to open TestRail: 848608</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848503' target='_blank'>"
				+ "Click here to open TestRail: 848503</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848650' target='_blank'>"
				+ "Click here to open TestRail: 848650</a><br>";

		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848608|848503|848650", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();

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
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			login_CP(driver);
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
			roomClassName = roomClassName + Utility.fourDigitgenerateRandomString();
			roomClassAbb = "abb" + Utility.fourDigitgenerateRandomString();
			newRoomClass.createRoomClassV2(driver, testSteps, roomClassName, roomClassAbb, maxAdults, maxPersopns,
					roomQuantity);
			newRoomClass.closeRoomClassTabV2(driver, roomClassName);
			testSteps.add("Room Class Created: <b>" + roomClassName + "</>");
			app_logs.info("Room Class Created: " + roomClassName);
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
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassName, rate, "", "", "", "", true);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}

		// Create Reservation
		try {
			testSteps.add("<b>======Start Creating Reservation ======</b>");
			app_logs.info("<b>======Start Creating Reservation ======</b>");
			guestFirstName = guestFirstName + Utility.threeDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.threeDigitgenerateRandomString();
			navigation.cpReservation_Backward(driver);
			testSteps = reservation.click_NewReservation(driver, testSteps);
			reservation.select_CheckInDate(driver, testSteps, checkInDate);
			reservation.select_CheckoutDate(driver, testSteps, checkOutDate);
			gstestSteps = reservation.enter_Adults(driver, testSteps, maxAdults);
			gstestSteps = reservation.enter_Children(driver, testSteps, child);
			gstestSteps = reservation.select_Rateplan(driver, testSteps, ratePlanName, "");
			gstestSteps = reservation.clickOnFindRooms(driver, testSteps);
			testSteps.addAll(gstestSteps);
			reservation.select_Room(driver, testSteps, roomClassName, "No", "");
			reservation.clickNext(driver, testSteps);
			String yearDate = Utility.getFutureMonthAndYearForMasterCard();
			Utility.expiryDate = yearDate;
			reservation.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, testSteps, salutation,
					guestFirstName, guestLastName, "No");
			reservation.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, yearDate);
			reservation.enter_MarketSegmentDetails(driver, testSteps, "", marketSegment, referral);
			reservation.clickBookNow(driver, testSteps);
			confirmationNo = reservation.get_ReservationConfirmationNumber(driver, testSteps);
			reservation.clickCloseReservationSavePopup(driver, testSteps);
			testSteps.add("Reservation Created: <b>" + confirmationNo + "</>");
			app_logs.info("Reservation Created: " + confirmationNo);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}

		try {
			testSteps.add("<b>======Verify Dirty Message for Unassigned Room for Reservation ======</b>");
			app_logs.info("<b>======Verify Dirty Message for Unassigned Room for Reservation ======</b>");
			reservation.clickCheckInButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.clickConfirmCheckInButton(driver, testSteps);
			/*Elements_CPReservation res = new Elements_CPReservation(driver);
			int count = 0;String message=null;
			try {
				app_logs.info("in try");
				while (count < 20) {
					reservation.clickOnConfirmCheckInButton(driver, testSteps);
					app_logs.info(count);
					if (res.Toaster_Message.isDisplayed()) {
						 message = reservation.toasterMsg(driver, testSteps);
						break;
					}
					count = count + 1;
					Wait.wait2Second();
				}
			} catch (Exception e) {
				app_logs.info("in cathc");
			}*/
			String  message = reservation.toasterMsg(driver, testSteps);
			Utility.verifyText(message, "Please select a room for the reservation", message, testSteps, app_logs);
			reservation.clickCloseRollBackMsg(driver, testSteps);
			reservation.closeReservationTab(driver);
		/*	testSteps.add(
					"Verify that the dirty room pop up message should not appear when we create a reservation with unassigned room"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848608' target='_blank'>"
							+ "Click here to open TestRail: 848608</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify that the dirty room pop up message should not "
					+ "appear when we create a reservation with unassigned room");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);
			testSteps.add(
					"Verify that the dirty room pop up message is not appearing when we create a reservation with unassigned room"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848503' target='_blank'>"
							+ "Click here to open TestRail: 848503</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify that the dirty room pop up message "
					+ "should not appear when we create a reservation with unassigned room");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
					Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
		} catch (Exception e) {
			Utility.catchException(driver, e, "Checkin Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Checkin Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}

		try {
			testSteps.add("<b>======Assigned Room to Reservation ======</b>");
			app_logs.info("<b>======Assigned Room to Reservation ======</b>");
			navigation.ClickTapeChart(driver);
			tapechart.searchInTapechart(driver, testSteps, checkInDate, checkOutDate, maxAdults, child, ratePlanName,
					"");
			tapechart.clickOnUIUnassignedButton(driver, testSteps);
			roomNo = tapechart.moveReserVationFromUnassignToAssign(driver, roomClassAbb, guestFirstName, testSteps);
			testSteps.add("<b>======Verify Assigned Room to Reservation ======</b>");
			app_logs.info("<b>======Verify Assigned Room to Reservation ======</b>");
			navigation.Reservation_Backward_TapeChart(driver);
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			ArrayList<String> rooms= new ArrayList<String>();
			rooms.add(roomNo);
			reservation.verifyStayInfoRoomNo(driver, testSteps, rooms);		
			/*testSteps.add(
					"Verify converting unassigned reservation into assigned room"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848650' target='_blank'>"
							+ "Click here to open TestRail: 848650</a><br>");
			Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify converting unassigned reservation into assigned room "
					+ "should not appear when we create a reservation with unassigned room");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
					Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Assign room  to Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Assign room  to  Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
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
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
