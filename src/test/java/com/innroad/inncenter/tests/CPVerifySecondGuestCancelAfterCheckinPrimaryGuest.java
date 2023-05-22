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
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifySecondGuestCancelAfterCheckinPrimaryGuest extends TestCore {

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
	RatesGrid rateGrid = new RatesGrid();
	ArrayList<String> rateplan = new ArrayList<>();
	String seasonStartDate = null, seasonEndDate = null, confirmationNo = null, roomClassNameNew = null;

	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbb = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPVerifySecondGuestCancelAfterCheckinPrimaryGuest(String roomClassName, String maxAdults,
			String maxPersopns, String roomQuantity, String ratePlanName, String rate, String checkInDate, String checkOutDate,
			String adults, String children, String salutation, String guestFirstName, String guestLastName,
			String paymentType, String cardNumber, String nameOnCard, String marketSegment, String referral)
			throws ParseException {
		String testCaseID="848476";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifySecondGuestCancelAfterCheckinPrimaryGuest";
		test_description = "CPVerifySecondGuestCancelAfterCheckinPrimaryGuest<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/848476' target='_blank'>"
				+ "Click here to open TestRail: 848476</a><br>";

		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848476", Utility.testId, Utility.statusCode, Utility.comments, "");
		String testCase = null;

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		NightlyRate nightlyRate = new NightlyRate();
		List<String> rooms = new ArrayList<String>();
		List<String> roomNos = new ArrayList<String>();
		// Login
		try {

			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate
							.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			rateplan = Utility.splitInputData(ratePlanName);
			app_logs.info(rateplan);

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDates.get(0), checkOutDates.get(0));
			app_logs.info(datesRangeList);
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
			app_logs.info("======Start Creating New Room Class======");
			navigation.clickSetup(driver);
			navigation.RoomClass(driver);
			testSteps.add("Navigated to Room Class");
			ArrayList<String> roomClassAre = Utility.splitInputData(roomClassName);
			for (int i = 0; i < roomClassAre.size(); i++) {
				String nameofRoomClass = roomClassAre.get(i) + Utility.threeDigitgenerateRandomString();
				String abbofRoomClass = "RC" + i + 1;
				roomClassNames.add(nameofRoomClass);
				roomClassAbb.add(abbofRoomClass);
				newRoomClass.createRoomClassV2(driver, testSteps, nameofRoomClass, abbofRoomClass, maxAdults,
						maxPersopns, roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, nameofRoomClass);
			}
			testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
			app_logs.info("Room Class Created: " + roomClassNames);
			app_logs.info("Room Class Abb: " + roomClassAbb);
			roomClassNameNew = roomClassNames.get(0) + "|" + roomClassNames.get(1);
			app_logs.info("Room Class : " + roomClassNameNew);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);
		} // Create Season
		try {
			testSteps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplan.get(0), datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", "", "", "", true);		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}

		try {
			testSteps.add("<b>======Start Creating Reservation ======</b>");
			app_logs.info("<b>======Start Creating Reservation ======</b>");
			ArrayList<String> roomNumber = new ArrayList<String>();
			confirmationNo = reservation.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, adults,
					children, ratePlanName, roomClassName, salutation, guestFirstName, guestLastName, "No", roomNumber,
					paymentType, cardNumber, nameOnCard, referral, false, testSteps);
			roomNos = reservation.getStayInfoRoomNo(driver, testSteps);
			app_logs.info(roomNos);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}

		try {
			testSteps.add("<b>======Checkin Primary Guest In Reservation ======</b>");
			app_logs.info("<b>======Checkin Primary Guest InReservation ======</b>");
			reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(0));
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			reservation.clickOnConfirmCheckInButton(driver, testSteps);
			reservation.verifyStayInfoCheckOutButton(driver, testSteps, roomNos.get(0));
			reservation.verifyStayInfoCheckINButtonForMRB(driver, testSteps, roomClassNames.get(1));
		} catch (Exception e) {
			Utility.catchException(driver, e, "Checkin primary Guest In Reservation", "Reservation", "Reservation",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Checkin primary Guest In Reservation", "Reservation", "Reservation",
					testName, test_description, test_catagory, testSteps);
		}

		try {
			testSteps.add("<b>======Cancelling Second Reservation ======</b>");
			app_logs.info("<b>======Cancelling Second  Reservation ======</b>");
			reservation.clickStayInfoThreeDots(driver, testSteps, "Cancel", roomNos.get(1));
			reservation.enterCancellationReasons(driver, testSteps, "Cancel Second Guest");
			reservation.clickConfirmCancelButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.verifyStayInforRollBackButton(driver, testSteps, roomClassNames.get(1));
			
			/*testSteps.add(
					"Verify that user should able to cancel the secondary guest reservation as long as the primary guest is not assigned to it"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848476' target='_blank'>"
							+ "Click here to open TestRail: 848476</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments,
					"Verify that user should able to cancel the secondary guest reservation as long as the primary guest is not assigned to it");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Cancel Second Guest Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Cancel Second Guest  Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("CPVerifySecondGuestCancelAfterC", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
