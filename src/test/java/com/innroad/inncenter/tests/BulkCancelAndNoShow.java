package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class BulkCancelAndNoShow extends TestCore {

	private WebDriver driver = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> testName = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> testCatagory = new ArrayList<>();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() throws InterruptedException, IOException {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable("BulkCheckInCheckOutReservations", HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "Reservations")
	public void bulkCancelAndNoShow(String TestCaseID, String referral, String firstName1, String lastName1,
			String account, String adult, String children, String isAssign, String isChecked, String salutation,
			String paymentType, String cardNumber, String cardName, String cardExpDate, String firstName2,
			String lastName2, String firstName3, String lastName3, String firstName4, String lastName4,
			String firstName5, String lastName5, String message, String roomClassName, String roomClassAbbreviation,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String additionalAdult,
			String additionalChild, String baseAmount, String rateName, String ratePolicy, String rateDescription,
			String reservedListSize, String quoteListSize, String associateSession, String ReservationCategory,
			String paymentCategory, String ratePlan, String source) throws Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		if (!Utility.validateString(TestCaseID)) {

			caseId.add("785565");
			statusCode.add("4");
		} else {
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}

		}

		String scriptName = "BulkCheckInCheckOutReservations";
		String description = "Verifying the Bulk Check In and Check Out of a Reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/667707' target='_blank'>"
				+ "Click here to open TestRail: C667707</a>"

				+ "<br> verify the bulk check in functionality<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825098' target='_blank'>"
				+ "Click here to open TestRail: C825098</a>";
		String testcatagory = "Reservations";
		testName.add(scriptName);
		testCatagory.add(testcatagory);
		testDescription.add(description);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + scriptName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		ReservationSearch reservationSearch = new ReservationSearch();
		RoomClass roomClass = new RoomClass();
		Rate rate = new Rate();
		NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();

		String creditCardNumber = "";
		String roomNumber1 = "";
		String roomNumber2 = "";

		String reservationNumber2 = "";
		String reservationNumber1 = "";

		String randomNumber = Utility.GenerateRandomNumber();
		lastName1 = lastName1 + randomNumber;
		lastName3 = lastName3 + randomNumber;

		String guestName1 = firstName3 + " " + lastName3;
		String guestName4 = firstName1 + " " + lastName1;
		String allReservation = "";
		String enterAmount = "0";
		String getRoomChargesInTripSummary = "";
		String getTaxandServicesInTripSummary = "";
		String getTotalTripSummary = "";
		String getBalanceFromTripSummary = "";
		String expecetdAmount = "";
		String tempraryRoomClass = roomClassName;
		Folio folio = new Folio();

		try {
			if (!Utility.insertTestName.containsKey(scriptName)) {
				Utility.insertTestName.put(scriptName, scriptName);
				Utility.reTry.put(scriptName, 0);

			} else {
				Utility.reTry.replace(scriptName, 1);
			}
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "CP_Login"));
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("==========DELETE ROOM CLASSE==========");

			navigation.Setup(driver);
			testSteps.add("Click on setup");

			navigation.RoomClass(driver);
			testSteps.add("Click on room classes");

			getTestSteps.clear();
			getTestSteps = newRoomClass.deleteRoomClassV2(driver, roomClassName);
			testSteps.addAll(getTestSteps);

		}

		catch (Exception e) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClasses", driver);
		} catch (Error e) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClasses", driver);
		}

		try {
			testSteps.add("===== CREATE NEW ROOM CLASS =====");

			roomClassName = roomClassName + randomNumber;
			roomClassAbbreviation = roomClassAbbreviation + randomNumber;

			try {
				newRoomClass.createRoomClassV2(driver, testSteps, roomClassName, roomClassAbbreviation, maxAdults,
						maxPersons, roomQuantity);
//				getTestSteps.clear();
//				getTestSteps = newRoomClass.closeRoomClassTabV2(driver, roomClassName);
//				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

				navigation.clickOnNewRoomClass(driver);

				getTestSteps.clear();
				getTestSteps = roomClass.createRoomClass(driver, roomClassName, roomClassAbbreviation, bedsCount,
						maxAdults, maxPersons, roomQuantity, test, getTestSteps);
				testSteps.addAll(getTestSteps);

				roomClass.closeRoomClassTab(driver);
				testSteps.add("Close created room class tab");
			}

			testSteps.add("Sccessfully Created New RoomClass " + roomClassName + " Abb : " + roomClassAbbreviation);
			app_logs.info("Sccessfully Created New RoomClass" + roomClassName + " Abb : " + roomClassAbbreviation);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", scriptName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", scriptName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			testSteps.add("==========DELETE ALL RATES THAT START WITH NAME OF" + rateName + " ==========");
			try {
				navigation.backToReservation(driver);
			} catch (Exception e) {
				navigation.reservationBackward3(driver);
			}
			navigation.Inventory(driver);
			testSteps.add("Navigate to Inventory");
			app_logs.info("Navigate to Inventory");

			getTestSteps.clear();
			getTestSteps = navigation.secondaryRatesMenuItem(driver);
			testSteps.addAll(getTestSteps);

			// start new method for delete rate
			getTestSteps.clear();
			getTestSteps = rate.deleteRates(driver, rateName);
			testSteps.addAll(getTestSteps);
			testSteps.add("New Rate has been Deleted successfully");
			app_logs.info("New Rate has been Deleted successfully");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delete a rate ", scriptName, "DeleteRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delete a rate ", scriptName, "DeleteRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Rate with
		try {
			testSteps.add("==========CREATE NEW RATE==========");
			rateName = rateName + randomNumber;
			String DisplayName = rateName;

			getTestSteps = navigation.secondaryRatesMenuItem(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.ClickNewRate(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateName(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.SelectRatePlan(driver, ratePlan);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxAdults(driver, maxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxPersons(driver, maxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterBaseAmount(driver, baseAmount);
			testSteps.addAll(getTestSteps);
			try {
				getTestSteps.clear();
				getTestSteps = rate.EnterRateInterval(driver, "1");
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				e.printStackTrace();
			}
			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalAdult(driver, maxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalChild(driver, additionalChild);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDisplayName(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRatePolicy(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDescription(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSeason(driver, associateSession);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateRoomClass(driver, roomClassName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSource(driver, source);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.Save_DoneRate(driver);
			testSteps.addAll(getTestSteps);

			rate.SearchRate(driver, rateName, false);
			testSteps.add("New Rate '" + rateName + "' Created & Verified ");
			app_logs.info("New Rate '" + rateName + "' Created & Verified");

			testSteps.add("New Rate With Name : " + rateName + " Created & Verified ");
			app_logs.info("New Rate With Name : " + rateName + " Created & Verified ");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create  a rate ", scriptName, "NewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create  a rate ", scriptName, "NewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("==========CREATE NEW RESERVED RESERVATION # 1==========");
			app_logs.info("==========CREATE NEW RESERVED RESERVATION # 1==========");

			navigation.navigateToReservations(driver);
			testSteps.add("Navigate to Reservations");

			getTestSteps.clear();
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, +1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterAdult(driver, adult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterChildren(driver, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			printString(roomClassName);
			getTestSteps = reservationPage.selectRoom(driver, getTestSteps, roomClassName, isAssign, account);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, firstName1, lastName1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, cardName, cardExpDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.add("Successfully click Book Now");

//			reservationPage.clickOnQuote(driver);
//			testSteps.add("Click on Save Quote");

			reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
			testSteps.add("Reservation confirmation number: " + reservationNumber1);
			app_logs.info("Reservation confirmation number: " + reservationNumber1);

			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			testSteps.add("Sucessfully close save quote popup");

			roomNumber2 = reservationPage.getRoomNumberDetail(driver);
			app_logs.info("Room number for reservation : " + roomNumber2);
			testSteps.add("Room number for reservation : " + roomNumber2);

			creditCardNumber = reservationPage.getCardNoLast4Digits_ResDetail(driver);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Successfully close opened tab");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new quote reservation", scriptName, "CreateReservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new quote reservation", scriptName, "CreateReservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// CREATE RESERVATION HAVING RESERVED STATUTS

		try {

			testSteps.add("==========CREATE NEW RESERVED RESERVATION # 2==========");
			app_logs.info("==========CREATE NEW RESERVED RESERVATIONN # 2==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, +1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterAdult(driver, adult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterChildren(driver, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoom(driver, getTestSteps, roomClassName, isAssign, account);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, firstName3, lastName3);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, cardName, cardExpDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.add("Successfully click Book Now");

			reservationNumber2 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
			testSteps.add("Reservation confirmation number: " + reservationNumber2);
			app_logs.info("Reservation confirmation number: " + reservationNumber2);

			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			testSteps.add("Sucessfully close save reservation Popup");

			roomNumber1 = reservationPage.getRoomNumberDetail(driver);
			app_logs.info("Room number for reservation 2 is : " + roomNumber2);
			testSteps.add("Room number for reservation 2 is : " + roomNumber2);

			getRoomChargesInTripSummary = reservationPage.getRoomChargesInTripSummary(driver);
			app_logs.info("after room charges");
			getTaxandServicesInTripSummary = reservationPage.getTaxandServicesInTripSummary(driver);
			app_logs.info("after tax services");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close opened tab");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", scriptName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", scriptName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Search for Above Reservations

		try {

			testSteps.add("============SEARCH FOR CREATED RESERVATIONS============");
			allReservation = reservationNumber1 + "," + reservationNumber2;

			reservationSearch.multipleSearchReservationNumber(driver, allReservation);
			testSteps.add("Successfully Search with Multiple Reservation Numbers");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Search all created reservation", scriptName, "CreateReservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Search all created reservation", scriptName, "CreateReservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String roomClass1 = roomClassAbbreviation + " : " + roomNumber1;
		String roomClass2 = roomClassAbbreviation + " : " + roomNumber2;

		try {

			testSteps.add("============BULK OPTION NO SHOW============");
			app_logs.info("============BULK OPTION NO SHOW============");

//			getTestSteps.clear();
//			getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
//			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationSearch.makeReservationBulkNoShow(driver);
			testSteps.addAll(getTestSteps);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to perform bulk checkin action", scriptName, "BulkCheckIn", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to perform bulk checkin action", scriptName, "BulkCheckIn", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		try {

			testSteps.add("============VERIFICATION OF RESERVATION STATUS AFTER BULK NO SHOW==============");
			app_logs.info("============VERIFICATION OF RESERVATION STATUS AFTER BULK NO SHOW==============");
			String status = "NO SHOW";
			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber1);
			testSteps.addAll(getTestSteps);
			
//			String statusAfterRes = reservationPage.get_ReservationStatus(driver, testSteps);
//			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
//					"Successfully Verified Status After BULK ACTION : " + status,
//					"Failed to Verified Status After BULK ACTION : " + statusAfterRes, true, testSteps);
			reservationPage.verifyStatusAfterReservation(driver, testSteps, status);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try{
			reservationPage.clickRollBackButton(driver, testSteps);
			reservationPage.clickYesButtonRollBackMsg(driver, testSteps);Wait.wait15Second();
			String status = "RESERVED";
//			String statusAfterRes = reservationPage.get_ReservationStatus(driver, testSteps);
//			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
//					"Successfully Verified Status After ROLLBACK RESERVATION 1  : " + status,
//					"Failed to Verified Status After ROLLBACK RESERVATION 1 : " + statusAfterRes, true, testSteps);
			try {
				reservationPage.verifyStatusAfterReservation(driver, testSteps, status);
			}catch (Error e) {
				reservationPage.verifyStatusAfterReservation(driver, testSteps, "CONFIRMED");
			}
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close opened tab");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			testSteps.add("============VERIFICATION OF RESERVATION 2 STATUS AFTER BULK NO SHOW==============");
			app_logs.info("============VERIFICATION OF RESERVATION 2 STATUS AFTER BULK NO SHOW==============");
			String status = "NO SHOW";
			reservationSearch.multipleSearchReservationNumber(driver, reservationNumber2);
			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber2);
			testSteps.addAll(getTestSteps);
			
//			String statusAfterRes = reservationPage.get_ReservationStatus(driver, testSteps);
//			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
//					"Successfully Verified Status After BULK ACTION : " + status,
//					"Failed to Verified Status After BULK ACTION : " + statusAfterRes, true, testSteps);
			reservationPage.verifyStatusAfterReservation(driver, testSteps, status);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try{
			reservationPage.clickRollBackButton(driver, testSteps);
			reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
			Wait.wait60Second();
			String status = "RESERVED";
//			String statusAfterRes = reservationPage.get_ReservationStatus(driver, testSteps);
//			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
//					"Successfully Verified Status After ROLLBACK RESERVATION 2  : " + status,
//					"Failed to Verified Status After ROLLBACK RESERVATION 2 : " + statusAfterRes, true, testSteps);
			try {
				reservationPage.verifyStatusAfterReservation(driver, testSteps, status);
			}catch (Error e) {
				reservationPage.verifyStatusAfterReservation(driver, testSteps, "CONFIRMED");
			}
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close opened tab");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			testSteps.add("============BULK OPTION CANCELLATION============");
			app_logs.info("============BULK OPTION CANCELLATION============");

			reservationSearch.multipleSearchReservationNumber(driver, reservationNumber1+","+reservationNumber2);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationSearch.bulkActionCancellationForSelected(driver);
			testSteps.addAll(getTestSteps);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to perform bulk checkin action", scriptName, "BulkCheckIn", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to perform bulk checkin action", scriptName, "BulkCheckIn", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		try {

			testSteps.add("============VERIFICATION OF RESERVATION 1 STATUS AFTER CANCELLATION==============");
			app_logs.info("============VERIFICATION OF RESERVATION 1 STATUS AFTER CANCELLATION==============");
			String status = "CANCELLED";
			getTestSteps.clear();
			reservationSearch.multipleSearchReservationNumber(driver, reservationNumber1);
			getTestSteps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber1);
			testSteps.addAll(getTestSteps);
			
//			String statusAfterRes = reservationPage.get_ReservationStatus(driver, testSteps);
//			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
//					"Successfully Verified Status After BULK ACTION : " + status,
//					"Failed to Verified Status After BULK ACTION : " + statusAfterRes, true, testSteps);
			reservationPage.verifyStatusAfterReservation(driver, testSteps, status);
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close opened tab");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			testSteps.add("============VERIFICATION OF RESERVATION 2 STATUS AFTER CANCELLATION==============");
			app_logs.info("============VERIFICATION OF RESERVATION 2 STATUS AFTER CANCELLATION==============");
			String status = "CANCELLED";
			getTestSteps.clear();
			reservationSearch.multipleSearchReservationNumber(driver, reservationNumber2);
			getTestSteps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber2);
			testSteps.addAll(getTestSteps);
//			
//			String statusAfterRes = reservationPage.get_ReservationStatus(driver, testSteps);
//			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
//					"Successfully Verified Status After BULK ACTION : " + status,
//					"Failed to Verified Status After BULK ACTION : " + statusAfterRes, true, testSteps);
			reservationPage.verifyStatusAfterReservation(driver, testSteps, status);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify ", scriptName,
						"TripSummary&GuestHistory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			navigation.Inventory(driver);
			testSteps.add("Navigate to Inventory");
			app_logs.info("Navigate to Inventory");
			
			// create new method 
			getTestSteps.clear();
			getTestSteps = navigation.secondaryRatesMenuItem(driver);
			testSteps.addAll(getTestSteps);

			// start new method for delete rate
			getTestSteps.clear();
			getTestSteps = rate.deleteRates(driver, rateName);
			testSteps.addAll(getTestSteps);
			testSteps.add("New Rate has been Deleted successfully");
			app_logs.info("New Rate has been Deleted successfully");

			rate.verifyDeleteRate(driver, rateName);
			testSteps.add("Verify the Deleted Rate : " + rateName);
			app_logs.info("Verify the Deleted Rate " + rateName);

		} catch (Exception e) {
			
		} catch (Error e) {
			
		}
		
		// Delete Room Class try
		try {
			testSteps.add("==========DELETE ROOM CLASSE==========");

			navigation.Setup(driver);
			testSteps.add("Click on setup");

			navigation.RoomClass(driver);
			testSteps.add("Click on room classes");
			
			getTestSteps.clear();
			getTestSteps = newRoomClass.deleteRoomClassV2(driver, roomClassName);
			testSteps.addAll(getTestSteps);
			testSteps.add("Delete room class successfully");
			app_logs.info("Delete room class successfully");
			
		} catch (Exception e) {
			
		} catch (Error e) {
			
		}
		
		try {
			comments = "pass";
			ArrayList<String> list = Utility.convertTokenToArrayList(TestCaseID, "\\|");
			statusCode = new ArrayList<String>();
			caseId = new ArrayList<String>();
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("1");

			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}
	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheet name provided
		return Utility.getData("BulkCancelAndNoShow", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

	}
}
