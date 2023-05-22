package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyCancelReservationOfReservedOnHoldConfirmedGurented extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	String testName = null, confirmationNo = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private void cancelReservation() throws InterruptedException {		
		reservation.reservationStatusPanelSelectStatus(driver, "Cancel",testSteps);
		Wait.wait5Second();
		reservation.enterCancellationReasons(driver, testSteps, "Cancel Reservation");
		reservation.clickConfirmCancelButton(driver, testSteps);
		reservation.verifySpinerLoading(driver);
		Wait.wait5Second();
		reservation.VerifyReservationStatus_Status(driver, "CANCELLED");
		reservation.clickRollBackButton(driver, testSteps);
		reservation.clickYesButtonRollBackMsg(driver, testSteps);
		reservation.verifySpinerLoading(driver);
		Wait.wait5Second();
		reservation.verifyReservationStatusStatus(driver, testSteps, "RESERVED");
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPVerifyCancelReservationOfReservedOnHoldConfirmedGurented(String checkInDate, String checkOutDate,
			String roomClassName, String maxAdults, String ratePlanName, String salutation, String guestFirstName, String guestLastName, String paymentType,
			String cardNumber, String nameOnCard, String marketSegment, String referral, String child)
			throws ParseException {
		test_name = "Desktop : Verify cancel option available for on hold, confirmed, reserved and Guaranteed reservation status.";
		test_description = "CPVerifyCancelReservationOfReservedOnHoldConfirmedGurented<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848532' target='_blank'>"
				+ "Click here to open TestRail: 848532</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848541' target='_blank'>"
				+ "Click here to open TestRail: 848541</a><br>";
		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848532|848541", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();

		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
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
				Utility.reTry.replace(testName, 1);
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

		try {
			testSteps.add("<b>======Start Creating Reservation ======</b>");
			app_logs.info("======Start Creating Reservation ======");
			confirmationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child,
					ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard,
					marketSegment, referral, roomClassName, false, false);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			testSteps.add("<b>======Cancel Reserved Reservation ======</b>");
			app_logs.info("======Cancel Reserved Reservation ======");
			reservation.verifyReservationStatusStatus(driver, testSteps, "RESERVED");
			cancelReservation();
			testSteps.add("<b>======Cancel Confirmed Reservation ======</b>");
			app_logs.info("======Cancel Confirmed Reservation ======");	
			reservation.reservationStatusPanelSelectStatus(driver, "Confirmed",testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.verifyReservationStatusStatus(driver, testSteps, "CONFIRMED");
			cancelReservation();
			testSteps.add("<b>======Cancel Guaranteed Reservation ======</b>");
			app_logs.info("======Cancel Guaranteed Reservation ======");	
			reservation.reservationStatusPanelSelectStatus(driver, "Guaranteed",testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.verifyReservationStatusStatus(driver, testSteps, "GUARANTEED");
			cancelReservation();			
			testSteps.add("<b>======Cancel On Hold Reservation ======</b>");
			app_logs.info("======Cancel On Hold Reservation ======");	
			reservation.reservationStatusPanelSelectStatus(driver, "On Hold",testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.verifyReservationStatusStatus(driver, testSteps, "On Hold");
			cancelReservation();
			
			testSteps.add("Desktop : Verify cancel option available for on hold, confirmed, reserved and Guaranteed reservation status."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848532' target='_blank'>"
					+ "Click here to open TestRail: C848532</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Desktop : Verify cancel option available for on hold, confirmed, reserved and Guaranteed reservation status");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Cancel Reserved Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Cancel Reserved Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		String testCase=null;
		try {
			testCase="Desktop : Roll back reservation and than check cancellation process";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			testSteps.add("<b>======Start Checkin ======</b>");
			app_logs.info("======Start Checkin ======");
			reservation.clickCheckInButton(driver, testSteps);
			reservation.generatGuestReportToggle(driver, testSteps, "No");	
			reservation.completeCheckInProcessSingleRev(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.verifyCheckOutButton(driver, testSteps);
			testSteps.add("<b>======Start CheckOut ======</b>");
			app_logs.info("======Start CheckOut ======");
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
			testSteps.add("<b>======Start Roll Back ======</b>");
			app_logs.info("======Start Roll Back ======");
			reservation.clickRollBackButton(driver, testSteps);
			reservation.clickYesButtonRollBackMsg(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.verifyReservationStatusStatus(driver, testSteps, "RESERVED");
			reservation.reservationStatusPanelSelectStatus(driver, "Cancel",testSteps);
			testSteps.add("Desktop : Verify cancel option available for on hold, confirmed, reserved and Guaranteed reservation status."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848541' target='_blank'>"
					+ "Click here to open TestRail: C8485412</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Desktop : Roll back reservation and than check cancellation process");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
					Utility.comments.get(1), TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "RollBack Reservation", "Reservation", "Reservation", testCase,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "RollBack Reservation", "Reservation", "Reservation", testCase,
					test_description, test_catagory, testSteps);
		}
	}

	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("CPVerifyCancelReservationOfRese", envLoginExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
