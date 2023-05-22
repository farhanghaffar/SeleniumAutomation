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
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPSplitReservationCheckin extends TestCore{
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
	Create_Reservation reservationPage= new Create_Reservation();
	Folio folio= new Folio();
	String roomClassNames = null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPSplitReservationCheckin(String checkInDate, String checkOutDate, 
			String roomClassName, String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, 
			String cardNumber,String nameOnCard,String marketSegment, String referral, String adult,String child) throws ParseException {
		String testCaseID="848452|848451|848453";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "Verify the behavior when user select the Rollback all option after the completion of check-in all";
		test_description = "CPSplitReservationCheckin<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848452' target='_blank'>"
				+ "Click here to open TestRail: 848452</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848451' target='_blank'>"
				+ "Click here to open TestRail: 848451</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848453' target='_blank'>"
				+ "Click here to open TestRail: 848453</a><br>";
		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848452|848451|848453", Utility.testId, Utility.statusCode, Utility.comments, "");
		String testCase=null;
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> checkOutFinalDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 ArrayList<String> rateplan= new ArrayList<String>();
		// Login
		try {
			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				checkOutFinalDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(5), "MM/dd/yyyy", "dd/MM/yyyy"));	
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0) + "|" + checkOutDates.get(0);
			checkOutDate = checkOutDates.get(0) + "|" + checkOutFinalDates.get(0);
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
			datesRangeList = Utility.getAllDatesStartAndEndDates(seasonStartDate, seasonEndDate);
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
			app_logs.info("<b>======Start Creating New Room Class======</b>");
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
			app_logs.info("<b>======Start Updating Rate Plan ======</b>");
		    rateplan= Utility.splitInputData(ratePlanName);
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplan.get(0),datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", 
					"", "", "", true);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======Create Split Reservation ======</b>");
			app_logs.info("<b>======Create Split Reservation ======</b>");	
			navigation.cpReservation_Backward(driver);
			ArrayList<String> roomNumber = new ArrayList<String>();
			roomClassName=roomClassNames+ "|" + roomClassNames;
			ArrayList<String> sal= Utility.splitInputData(salutation);
			ArrayList<String> gFirstname= Utility.splitInputData(guestFirstName);
			ArrayList<String> gLastname= Utility.splitInputData(guestLastName);
			reservationPage.createSplitReservation(driver, testSteps, checkInDate, checkOutDate, adult, child, ratePlanName, 
					roomClassName, sal.get(0), gFirstname.get(0), gLastname.get(0), paymentType, cardNumber, nameOnCard, 
					marketSegment, referral,"No", false,false);		
			
			testSteps.add("<b>======Verify adding amount for room charges(all rooms) line items for split reservation ======</b>");
			app_logs.info("<b>======Verify adding amount for room charges(all rooms) line items for split reservation======</b>");			
			testSteps.add("<b>======Checkin Split Reservation ======</b>");
			app_logs.info("<b>======Checkin Split Reservation ======</b>");	
			reservation.clickCheckInButton(driver, testSteps);
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			reservation.completeCheckInProcess(driver, testSteps);	
			
			/*testSteps.add("Verify that user should able to check-in for split room reservation."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848452' target='_blank'>"
					+ "Click here to open TestRail: C848452</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify that user should able to check-in for split room reservation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			testCase="Verify the behavior when user select the Rollback all option after the completion of check-in all";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}			
			testSteps.add("<b>======Roll Back Reservation ======</b>");
			app_logs.info("<b>======Roll Back Reservation ======</b>");	
			reservation.reservationStatusPanelSelectStatus(driver,"Rollback",testSteps);
			reservation.verifyRollBackMsg(driver, testSteps,config.getProperty("rollBackMessage"));
			reservation.clickYesButtonRollBackMsg(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			
			/*testSteps.add("Verify the behavior when user select the Rollback all option after the completion of \"check-in all\"."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848451' target='_blank'>"
					+ "Click here to open TestRail: C848451</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify the behavior when user select the Rollback all option after the completion of \"check-in all\"");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);	*/

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			}catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation ", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		
		try {
			testCase="Verify that user should able to check-out for split room reservation";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}	
			testSteps.add("<b>======Checkin Split Reservation ======</b>");
			app_logs.info("<b>======Checkin Split Reservation ======</b>");	
			reservation.clickCheckInButton(driver, testSteps);
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			reservation.completeCheckInProcess(driver, testSteps);	
			reservation.verifySpinerLoading(driver);
			reservation.verifyCheckOutButton(driver, testSteps);
			testSteps.add("<b>======Checkout Split Reservation ======</b>");
			app_logs.info("<b>======Checkout Split Reservation ======</b>");	
			reservation.clickCheckOutButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			reservation.proceedToCheckOutPayment(driver, testSteps);
			Wait.wait5Second();
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.verifyRollBackButton(driver, testSteps);
			
			/*testSteps.add("Verify that user should able to check-out for split room reservation"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848453' target='_blank'>"
					+ "Click here to open TestRail: C848453</a><br>");
			Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify that user should able to check-out for split room reservation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);*/

			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Split Reservation Checkin checkout verifiation");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
			}catch (Exception e) {
			Utility.catchException(driver, e, "Checkin Split and Checkout Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Checkin Split and Checkout  Reservation ", "Reservation", "Reservation", testName, test_description,
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
