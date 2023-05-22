package com.innroad.inncenter.tests;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

// Using Test Data --"GSVerifyArrivalDueScenarios"
public class CPVerifyCheckinSuccessfully  extends TestCore {
	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	Folio folio= new Folio();
	String abbri = null, seasonStartDate = null, seasonEndDate = null, confirmationNo = null, getAmount = null,
			roomClassNames = null,  endDate=null;
	NightlyRate nightlyRate = new NightlyRate();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	Policies policies = new Policies();
	String testName = null;
	ReservationHomePage reservationHomePage = new ReservationHomePage();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private void changeStatus(String status) throws InterruptedException
	{Wait.wait5Second();
		reservation.reservationStatusPanelSelectStatus(driver,status,testSteps);
		Wait.wait5Second();
		reservation.verifySpinerLoading(driver);
		Wait.wait5Second();
	}
	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyCheckinSuccessfully(String checkInDate, String checkOutDate,
			String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity,String ratePlanName, String rate,  
			String salutation, String guestFirstName, String guestLastName, 
			String paymentType, String cardNumber,String nameOnCard, String marketSegment, 
			String referral, String child) throws ParseException {
		String testCaseID="848405|848738|848747|848550|848709|848773|848758";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String yearDate=null;
		test_name = "cPVerifyCheckinSuccessfully";		
		test_description = "Verify CheckIn Successfully <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848405' target='_blank'>"
				+ "Click here to open TestRail: 848405</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848738' target='_blank'>"
				+ "Click here to open TestRail: 848738</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848747' target='_blank'>"
				+ "Click here to open TestRail: 848747</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848550' target='_blank'>"
				+ "Click here to open TestRail: 848550</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848709' target='_blank'>"
				+ "Click here to open TestRail: 848709</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848773' target='_blank'>"
				+ "Click here to open TestRail: 848773</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848758' target='_blank'>"
				+ "Click here to open TestRail: 848758</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848405|848738|848747|848550|848709|848773|848758", Utility.testId, Utility.statusCode,
				Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> checkOutDatesForStayInfor = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		
		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				checkOutDatesForStayInfor.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			endDate=checkOutDatesForStayInfor.get(0);
			seasonStartDate=checkInDate;
			seasonEndDate=sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			app_logs.info(endDate);

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
		
		try {			
			testSteps.add("<b>======Start Create Reservation with Invalid Expiry Date======</b>");
			app_logs.info("======Start Create Reservation with Invalid Expiry Date======");
			navigation.cpReservation_Backward(driver);
			reservation.click_NewReservation(driver, testSteps);
			reservation.select_CheckInDate(driver, testSteps, checkInDate);
			reservation.select_CheckoutDate(driver, testSteps, checkOutDate);
			reservation.enter_Adults(driver, testSteps, maxAdults);
			reservation.enter_Children(driver, testSteps, child);
			reservation.select_Rateplan(driver, testSteps, ratePlanName, "");
			reservation.clickOnFindRooms(driver, testSteps);
			ArrayList<String> rooms = reservation.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClassNames);
			reservation.selectRoomToReserve(driver, testSteps, roomClassNames, rooms.get(0));
			reservation.clickNext(driver, testSteps);
			yearDate = Utility.getPreviousYearAndMonth();
			reservation.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, testSteps, salutation, guestFirstName,
					guestLastName, "No");
			reservation.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, yearDate);			
			reservation.verifyInvalidExpiryDate(driver, testSteps);
		
		
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation and Verify Checkin Successfully", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		
		try {

			testSteps.add("<b>=====Complete Reservation ======</b>");
			app_logs.info("======Complete Reservation ======");
			yearDate= Utility.getFutureMonthAndYearForMasterCard();
			reservation.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, yearDate);
			reservation.enter_MarketSegmentDetails(driver, testSteps, "", marketSegment, referral);
			reservation.clickBookNow(driver, testSteps);
			confirmationNo=reservation.get_ReservationConfirmationNumber(driver, testSteps);
			reservation.clickCloseReservationSavePopup(driver, testSteps);			
			testSteps.add("<b>=====Before Checkin Able to Change Reservation Status======</b>");
			app_logs.info("======Before Checkin Able to Change Reservation Status ======");			
			changeStatus("Guaranteed");
			changeStatus("Confirmed");
			changeStatus("On Hold");
			changeStatus("Cancel");	
			reservation.clickCloseRollBackMsg(driver, testSteps);	
			Wait.wait5Second();
			changeStatus("No Show");	
			reservation.clickCloseRollBackMsg(driver, testSteps);	
			Wait.wait5Second();
			changeStatus("Reserved");	
			Wait.wait5Second();
			
			testSteps.add("<b>======Start Checkin ======</b>");
			app_logs.info("======Start Checkin ======");
			reservation.clickCheckInButton(driver, testSteps);
			reservation.generatGuestReportToggle(driver, testSteps, "No");	
			reservation.clickOnConfirmCheckInButton(driver, testSteps);
			testSteps.add("<b>======Verify Checkin Successfully======</b>");
			app_logs.info("======Start Checkin Successfully======");
    		String message=reservation.toasterMsg(driver, testSteps);
			String text="Check-In Successful";
			Utility.verifyText(message.toLowerCase().trim(), text.toLowerCase().trim(), "Verified Checkin Successfully", testSteps, app_logs);
			reservation.verifySpinerLoading(driver);
			reservation.verifyReservationStatusStatus(driver, testSteps, "IN-HOUSE");
			reservation.verifyConfirmationNoAfterReservation(driver, confirmationNo);		
			
	/*		testSteps.add("Verify that a confirmation message is displayed when user confirms the check in process."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848405' target='_blank'>"
					+ "Click here to open TestRail: 848405</a><br>");			
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify that a confirmation message is displayed when user confirms the check in process.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		
			testSteps.add("Verify that user should able to Check-in when country field is not selected while creating single reservation"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848738' target='_blank'>"
					+ "Click here to open TestRail: C848738</a><br>");	
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify that user should able to Check-in when country field is not selected while creating single reservation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
*/		
			} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation and Verify Checkin Successfully", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation and Verify Checkin Successfully", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		
		try {
			testSteps.add("<b>======Take Folio Payment ======</b>");
			app_logs.info("======Take Folio Payment ======");
			reservation.click_Folio(driver, testSteps);
			String note="Verify Folio Note";
			reservation.clickFolioPayButton(driver, testSteps);
			reservation.takePayment(driver, testSteps, "", "", "", "", "", "No", "No", "No", note);
			reservation.verifySpinerLoading(driver);
			folio.verifyFolioLineItemNote(driver, testSteps, checkInDate, paymentType,note);
			
		/*	testSteps.add("Verify Notes is saved for payments when payment method is Card for Single Reservation in folio tab."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848709' target='_blank'>"
					+ "Click here to open TestRail: 848709</a><br>");	
			Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify Notes is saved for payments when payment method is Card for Single Reservation in folio tab.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);
		
	*/	}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Note in Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Note in Reservation", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
	
	
		try {
			testSteps.add("<b>======Upload/Delete/Download the files from Document Tab ======</b>");
			app_logs.info("======Upload/Delete/Download the files from Document Tab ======");
			reservation.click_Documents(driver, testSteps);
			String filePath=imagesFilesPath + File.separator + "image.png";
			reservationHomePage.UploadDocument(driver, testSteps, filePath);
			reservationHomePage.downloadDocumnet(driver, testSteps,"image");
			reservationHomePage.DeleteDocument(driver, testSteps, "image");			
		
		/*	testSteps.add("Verify that user is able to navigate to documents tab and user is able to upload/delete/download the files"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848773' target='_blank'>"
					+ "Click here to open TestRail: 848773</a><br>");	
			Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify that user is able to navigate to documents tab and user is able to upload/delete/download the files");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
	*/	
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Document in Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Document in Reservation", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		
		try {
			testSteps.add("<b>======Verified Save button is enabled when stay dates are modified with Recalculate Rate option=====</b>");
			app_logs.info("======Verified Save button is enabled when stay dates are modified with Recalculate Rate option======");
			reservation.click_DeatilsTab(driver, testSteps);
			reservation.ClickEditStayInfo(driver, testSteps);
			reservation.ClickStayInfo_ChangeDetails(driver, testSteps);
			reservationHomePage.verifyStayInforComingEditMode(driver, testSteps);
			reservation.select_CheckInDate(driver, testSteps, checkInDate);
			reservation.select_CheckoutDate(driver, testSteps, endDate);
			reservation.clickFindRooms(driver);
			Wait.waitforPageLoad(50, driver);
			//reservation.clickSelectRoom(driver, roomClassNames, testSteps);
			reservation.verifySpinerLoading(driver);
			reservationHomePage.verifykSaveAfterEditStayInfo(driver, testSteps);
			reservation.clickSaveAfterEditStayInfo(driver);
			String message=reservation.toasterMsg(driver, testSteps);
			testSteps.add(message);			
			/*
			 * testSteps.
			 * add("Verify Save button is enabled when stay dates are modified with \"Recalculate Rate\" option for future stay dates reservation"
			 * +
			 * "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848758' target='_blank'>"
			 * + "Click here to open TestRail: 848758</a><br>");
			 * Utility.testCasePass(Utility.statusCode, 6, Utility.comments,
			 * "Verify Save button is enabled when stay dates are modified with \"Recalculate Rate\" option for future stay dates reservation"
			 * ); Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6),
			 * Utility.statusCode.get(6), Utility.comments.get(6),
			 * TestCore.TestRail_AssignToID);
			 * 
			 */ for (int i = 0; i < Utility.testId.size(); i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify checkin successfully");
			 	}
			}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Stay Detail in Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Stay Detail in Reservation", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		// Delete Room Class
		try {
			testSteps.add("<b>======Delete Room Class======</b>");
			app_logs.info("======Delete Room Class ======");
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
		// return test data from the sheetname provided
		return Utility.getData("GSVerifyArrivalDueScenarios", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
