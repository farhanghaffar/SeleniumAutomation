package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyCancellationPolicy extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	ReservationHomePage reservationHomePage= new ReservationHomePage();
	String abbri = null, seasonStartDate = null, seasonEndDate = null, confirmationNo = null, getAmount = null,
			roomClassNames = null,roomChargeAmount = null;
	NightlyRate nightlyRate = new NightlyRate();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	Policies policies = new Policies();
	Folio folio = new Folio();
	String testName = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyCheckInWithPolicy(String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity, String cancellationPolicyName, String typeOfFees, String percentage, String chargesTypes,
			String noOfDays,String cancelWithInType,
			String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral) throws ParseException {
		String testCaseID="848481|848483|848629|848411";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifyCheckInWithSinglePolicy";
		test_description = "Verify CheckIn Policies <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848481' target='_blank'>"
				+ "Click here to open TestRail: 848481</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848483' target='_blank'>"
				+ "Click here to open TestRail: 848483</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848629' target='_blank'>"
				+ "Click here to open TestRail: 848629</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848411' target='_blank'>"
				+ "Click here to open TestRail: 848411</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848481|848483|848629|848411", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		RatesGrid rateGrid = new RatesGrid();
		List<String> roomNumbers = new ArrayList<String>();
		ArrayList<String> roomCharges= new ArrayList<String> ();
		HashMap<String, ArrayList<String>> policiesNames = new HashMap<String, ArrayList<String>>();
		// Login
		try {
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
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
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, checkOutDate);
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
		try {
			testSteps.add("<b>======Start Creating Policy ======</b>");
			navigation.inventoryFromRoomClass(driver, testSteps);
			navigation.policies(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 5);
			cancellationPolicyName = cancellationPolicyName + Utility.generateRandomStringWithoutNumbers();
			policiesNames = policies.createPolicies(driver, testSteps, "", "", "Cancellation", cancellationPolicyName, "", "",
					"", typeOfFees, percentage, chargesTypes, noOfDays, cancelWithInType, "No", "");
			app_logs.info(policiesNames);
			app_logs.info(cancellationPolicyName);

		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
					test_catagory, testSteps);
		}
		// Create Season
		try {
			testSteps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", false);
			Utility.refreshPage(driver);
			nightlyRate.switchCalendarTab(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, testSteps);
			nightlyRate.clickSeasonPolicies(driver, testSteps);
			nightlyRate.selectPolicy(driver, "Cancellation", cancellationPolicyName, true, testSteps);
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			rateGrid.closeRatePlan(driver, testSteps, ratePlanName);
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
			confirmationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, adults, children,
					ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard,
					marketSegment, referral, roomClassNames, true, false);
			testSteps.add("Reservation Created: <b>" + confirmationNo + "</>");
			app_logs.info("Reservation Created: " + confirmationNo);
			roomNumbers = reservation.getStayInfoRoomNo(driver, testSteps);
			app_logs.info(roomNumbers);
			app_logs.info("<b>======Get Room Charge Reservation ======</b>");
			reservation.click_Folio(driver, testSteps);
			reservation.includeTaxesinLineItems(driver, testSteps, true);
			reservation.click_Folio(driver, testSteps);
			reservation.includeTaxesinLineItems(driver, testSteps, true);
			HashMap<String, String> getRoomCharge = new HashMap<String, String>();
			getRoomCharge = reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDate,
					checkOutDate);
			for (Map.Entry<String, String> entry : getRoomCharge.entrySet()) {
				roomCharges.add(Utility.convertDecimalFormat(entry.getValue()));
			}
			app_logs.info(roomCharges);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			
			testSteps.add("<b>======Cancel Reservation ======</b>");
			app_logs.info("<b>======Cancel Reservation ======</b>");
			reservation.click_DeatilsTab(driver, testSteps);
			reservation.reservationStatusPanelSelectStatus(driver, "Cancel",testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.enterCancellationReasons(driver, testSteps, "Cancel Reservation");
			
			/*testSteps.add(
					"Desktop : Verify a model will appear when user click on cancel from reservation title line."
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848629' target='_blank'>"
							+ "Click here to open TestRail: 848629</a><br>");
			Utility.testCasePass(Utility.statusCode, 2, Utility.comments,
					"Desktop : Verify a model will appear when user click on cancel from reservation title line.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
					Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
			
			reservation.proceesOrConfirmToCancellationPayment(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			
			reservationHomePage.enterCardNumber(driver, "4545454545127879", testSteps);
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservationHomePage.verifyTransactionDeclinedMessage(driver,testSteps);
			reservation.verifySpinerLoading(driver);
			
			reservationHomePage.SelectPaymentMethod(driver, testSteps,"Cash");
		//	reservationHomePage.enterCardNumber(driver, cardNumber, testSteps);
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();			
			reservation.clickCloseButtonOfCancellationSuccessfully(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.VerifyReservationStatus_Status(driver, "CANCELLED");
		}catch (Exception e) {
			Utility.catchException(driver, e, "Cancellation Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Cancellation Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			testSteps.add("<b>======Verify Cancel fee on Reservation ======</b>");
			app_logs.info("<b>=======Verify Cancel fee on Reservation ======</b>");
			reservation.click_Folio(driver, testSteps);
			double payment= Double.parseDouble(reservation.get_Payments(driver, testSteps));
			app_logs.info(payment);
			roomChargeAmount = reservation.calculationOfCancelletionAmountToBePaidForRateV2(roomCharges, typeOfFees, percentage, "", String.valueOf(payment));
			reservation.verifyFolioLineItem(driver, checkInDate, "Cancellation Fee", "Cancellation Fee", roomChargeAmount, testSteps);			
			/*testSteps.add(
					"Verify that when we cancel the reservation then the room-specific folio should remain, so that it can be used to track the cancellation fee"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848481' target='_blank'>"
							+ "Click here to open TestRail: 848481</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments,
					"Verify that when we cancel the reservation then the room-specific folio should remain, so that it can be used to track the cancellation fee");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);
			
			testSteps.add(
					"Verify cancellation room charges fees in folio when Policy Attributes set in % of room charges & Beyond #days of reservation"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848483' target='_blank'>"
							+ "Click here to open TestRail: 848483</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments,
					"Verify cancellation room charges fees in folio when Policy Attributes set in % of room charges & Beyond #days of reservation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
					Utility.comments.get(1), TestCore.TestRail_AssignToID);
			
			testSteps.add(
					"Desktop : Verify user able to confirm cancellation."
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848411' target='_blank'>"
							+ "Click here to open TestRail: 848411</a><br>");
			Utility.testCasePass(Utility.statusCode, 3, Utility.comments,
					"Desktop : Verify user able to confirm cancellation.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3),
					Utility.comments.get(3), TestCore.TestRail_AssignToID);*/
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify cancellation policy");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		
		}catch (Exception e) {
			Utility.catchException(driver, e, "Cancellation Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Cancellation Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
	}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CPVerifyCancellationPolicy", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);

	}
}
