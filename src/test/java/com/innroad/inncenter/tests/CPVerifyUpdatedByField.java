package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.HomePage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyUpdatedByField extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	ReservationHomePage reservationHomePage = new ReservationHomePage();
	String abbri = null, seasonStartDate = null, seasonEndDate = null, confirmationNo = null, getAmount = null,
			roomClassNames = null,roomChargeAmount = null,checkoutAmount=null;
	NightlyRate nightlyRate = new NightlyRate();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	ReservationSearch revSearch = new ReservationSearch();
	HomePage home = new HomePage();
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
	public void cPVerifyUpdatedByField(String roomClassName, String maxAdults, String maxPersopns, String roomQuantity,
			String checkInPolicyName, String typeOfFees, String percentage, String chargesTypes, String ratePlanName,
			String rate, String checkInDate, String checkOutDate, String adults, String children, String salutation,
			String guestFirstName, String guestLastName, String paymentType, String cardNumber, String nameOnCard,
			String marketSegment, String referral) throws ParseException {
		String testCaseID="848686|848687";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifyUpdatedByField";
		test_description = "CPVerifyUpdatedByField<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848686' target='_blank'>"
				+ "Click here to open TestRail: 848686</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848687' target='_blank'>"
				+ "Click here to open TestRail: 848687</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848686|848687", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> roomNumber = new ArrayList<String>();
		RatesGrid rateGrid = new RatesGrid();
		HashMap<String, ArrayList<String>> polictiesNames = new HashMap<String, ArrayList<String>>();
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
		try {
			testSteps.add("<b>======Start Creating Policy ======</b>");
			app_logs.info("<b>======Start Creating Policy======</b>");
			navigation.inventoryFromRoomClass(driver, testSteps);
			navigation.policies(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 5);
			checkInPolicyName = checkInPolicyName + Utility.generateRandomStringWithoutNumbers();
			polictiesNames = policies.createPolicies(driver, testSteps, "", "", "Check-in", "", "", checkInPolicyName,
					"", typeOfFees, percentage, chargesTypes, "", "", "No", "");
			app_logs.info(polictiesNames);
			app_logs.info(checkInPolicyName);
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
			app_logs.info("<b>======Start Updating Rate Plan======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", false);
			Utility.refreshPage(driver);
			nightlyRate.switchCalendarTab(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, testSteps);
			nightlyRate.clickSeasonPolicies(driver, testSteps);
			nightlyRate.selectPolicy(driver, "Check-in", checkInPolicyName, true, testSteps);
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
			app_logs.info(roomNumber);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}

		// Logged Out Login Again
		try {
			testSteps.add("<b>======Logged Out and Login by Super User and Select Property ======</b>");
			app_logs.info("<b>======Logged Out and Login by Super User and Select Property ======</b>");
			Utility.logoutToInnCenter(driver, testSteps);
			loginSuperUser(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			home.selectPropertiy(driver, testSteps, Utility.login_CP.get(1));
		} catch (Exception e) {
			Utility.catchException(driver, e, "Logged and Login Again and Select Property", "Reservation",
					"Reservation", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Logged and Login Again and Select Property", "Reservation", "Reservation",
					testName, test_description, test_catagory, testSteps);
		}

		try {
			testSteps.add("<b>======Search Reservation and Checkin ======</b>");
			app_logs.info("<b>======Search Reservation and Checkin   ======</b>");
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			testSteps.add("Search Reservation By Reservation Number <b>" + reservation + "<b>");
			app_logs.info("Search Reservation By Reservation Number " + reservation);
			reservation.clickCheckInButton(driver, testSteps);
			Wait.wait5Second();
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			reservation.clickOnProceedToCheckInPaymentButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.clickCloseButtonOfCheckInSuccessfully(driver, testSteps);
			reservation.verifySpinerLoading(driver);

			Utility.logoutToInnCenter(driver, testSteps);
			login_CP(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

			testSteps.add("<b>======Verify Updated By Fiels for Payment After Checkin ======</b>");
			app_logs.info("<b>======Verify Updated By Fiels for Payment After Checkin  ======</b>");
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			testSteps.add("Search Reservation By Reservation Number <b>" + reservation + "<b>");
			app_logs.info("Search Reservation By Reservation Number " + reservation);
			reservation.click_Folio(driver, testSteps);
			reservation.includeTaxesinLineItems(driver, testSteps, true);
			HashMap<String, String> getRoomCharge = new HashMap<String, String>();
			double charge = 0.00;
			getRoomCharge = reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDate,
					checkOutDate);
			for (Map.Entry<String, String> entry : getRoomCharge.entrySet()) {
				charge = charge + Double.parseDouble(Utility.convertDecimalFormat(entry.getValue()));
			}
			roomChargeAmount = String.valueOf(charge);
			roomChargeAmount = reservation.calculationOfCheckInAmountToBePaidForRateV2(roomChargeAmount,
					percentage);
			app_logs.info(roomChargeAmount);
			String getcardNo = Utility.getCardNumberHidden(cardNumber);
			reservationHomePage.clickFolioDescription(driver, getcardNo,roomChargeAmount);
			reservation.verifySpinerLoading(driver);
			reservationHomePage.verifyPaymentDetailUserName(driver, testSteps, Utility.loginSuperuser.get(2));
/*
			testSteps.add(
					"Verify that Updated By field is displayed correctly for Checkin payment line item made for reservation"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848686' target='_blank'>"
							+ "Click here to open TestRail: 848686</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments,
					"Verify that Updated By field is displayed correctly for Checkin payment line item made for reservation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);*/

			testSteps.add("<b>======Checkout Reservation======</b>");
			app_logs.info("<b>======Checkout  Reservation======</b>");
			reservation.clickCheckOutButton(driver, testSteps);
			Wait.wait5Second();
			reservation.verifySpinerLoading(driver);
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			reservation.proceedToCheckOutPayment(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			 checkoutAmount=reservation.getAmountFromPaymentScreen(driver);
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
			reservation.verifySpinerLoading(driver);
		} catch (Exception e) {
			Utility.catchException(driver, e,
					"Search Reservation and Checkin and Verify Updated By Fiels for Payment After Checkin",
					"Reservation", "Reservation", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e,
					"Search Reservation and Checkin and Verify Updated By Fiels for Payment After Checkin",
					"Reservation", "Reservation", testName, test_description, test_catagory, testSteps);
		}

		// Logged Out Login Again
		try {
			testSteps.add("<b>======Logged Out and Login by Super User and Select Property ======</b>");
			app_logs.info("<b>======Logged Out and Login by Super User and Select Property ======</b>");
			Utility.logoutToInnCenter(driver, testSteps);
			loginSuperUser(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			home.selectPropertiy(driver, testSteps, Utility.login_CP.get(1));
			testSteps.add("<b>======Verify Updated By Fiels for Payment After Checkout ======</b>");
			app_logs.info("<b>======Verify Updated By Fiels for Payment After Checkout  ======</b>");
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			testSteps.add("Search Reservation By Reservation Number <b>" + reservation + "<b>");
			app_logs.info("Search Reservation By Reservation Number " + reservation);
			reservation.click_Folio(driver, testSteps);
			String getcardNo = Utility.getCardNumberHidden(cardNumber);
			reservationHomePage.clickFolioDescription(driver, getcardNo,Utility.convertDecimalFormat(checkoutAmount));
			reservation.verifySpinerLoading(driver);
			reservationHomePage.verifyPaymentDetailUserName(driver, testSteps, Utility.login_CP.get(2));

			/*testSteps.add(
					"Verify that Updated By field is displayed correctly for Checkout  payment line item made for reservation"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848687' target='_blank'>"
							+ "Click here to open TestRail: 848687</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments,
					"Verify that Updated By field is displayed correctly for Checkout  payment line item made for reservation");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
					Utility.comments.get(1), TestCore.TestRail_AssignToID);
			*/
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify fields updated");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e,
					"Logged and Login Again and Select Property and Verify updated by name after checkout",
					"Reservation", "Reservation", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e,
					"Logged and Login Again and Select Property and Verify updated by name after checkout",
					"Reservation", "Reservation", testName, test_description, test_catagory, testSteps);
		}
		
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CPVerifyUpdatedByField", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
