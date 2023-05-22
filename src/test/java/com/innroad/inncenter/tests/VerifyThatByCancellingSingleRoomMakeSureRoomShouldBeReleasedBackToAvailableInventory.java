package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyThatByCancellingSingleRoomMakeSureRoomShouldBeReleasedBackToAvailableInventory extends TestCore {

	private WebDriver driver = null;
	ArrayList<String> test_catagory = new ArrayList<>();
	ArrayList<String> test_description = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> test_name = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
    ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	String testName = this.getClass().getSimpleName().trim();
	String roomChargeAmount = null, nameOnCard;
	String reservation = null, status = null, tripTotal = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Policy")
	public void verifyNoShowPolicy(String TestCaseID, String ClientCode, String Username, String Password, String resType,
			String roomClassName, String ratePlan,
			String checkInDate, String checkOutDate, String account, String accountType, String paymentMethod,
			String cardNumber) throws Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		String testDescription = "Verify that by cancelling single room, make sure room should be released back to available inventory<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682501' target='_blank'>"
				+ "Click here to open TestRail: "+TestCaseID+"</a><br/>";
		String testCatagory = "Policies";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("848487");
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}			
		}

		CPReservationPage reservationPage = new CPReservationPage();
		Login login = new Login();
		
		String adults = "2", children = "1";
		String guestFirstName = "First" + Utility.generateRandomStringWithoutNumbers();
		String guestLastName = "Last" + Utility.generateRandomStringWithoutNumbers();
		String salutation = "Mr.";
		String address1 = Utility.generateRandomString();
		String address2 = Utility.generateRandomString();
		String address3 = Utility.generateRandomString();
		String city = Utility.generateRandomString();
		String postalCode = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
		String phoneNumber = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String alternativePhone = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String referral = "Walk In";
		String email = "innRoadTestEmail@innroad.com";
		String isGuesProfile = "No";
		String country = "United States", state = "Alaska";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			
			// Get CheckIn, CheckOut Date
			if (checkInDate.equalsIgnoreCase("NA") || checkInDate.isEmpty() || checkInDate.equalsIgnoreCase("")) {

				if (!resType.equalsIgnoreCase("MRB")) {
					checkInDate = Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern");
					checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					// Get CheckIN and Checkout Date
					if (checkInDate.equalsIgnoreCase("NA") || checkInDate.isEmpty()
							|| checkInDate.equalsIgnoreCase("")) {
						checkInDates.clear();
						checkOutDates.clear();
						for (int i = 1; i <= roomClassName.split("|").length; i++) {
							app_logs.info("Loop" + i);
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern"));
							checkOutDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy"));
							app_logs.info(checkInDates);
							app_logs.info(checkOutDates);
						}
					} else {
						checkInDates = Utility.splitInputData(checkInDate);
						checkOutDates = Utility.splitInputData(checkOutDate);
					}
					app_logs.info(checkInDates);
					app_logs.info(checkOutDates);

					checkInDate = checkInDates.get(0);
					checkOutDate = checkOutDates.get(0);

					for (int i = 1; i < checkInDates.size(); i++) {
						checkInDate = checkInDate + "|" + checkInDates.get(i);
						checkOutDate = checkOutDate + "|" + checkOutDates.get(i);
					}
					app_logs.info(checkInDate);
					app_logs.info(checkOutDate);
				}
			}
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			
			String randomNum = Utility.GenerateRandomNumber();
			guestLastName = guestLastName + randomNum;
			nameOnCard = guestFirstName + " " + guestLastName;

			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to update test data before executing test scripts", testName,
						"Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to update test data before executing test scripts", testName,
						"Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			driver = getDriver();
			//login_CP(driver, test_steps);
//			login.login(driver, envURL, ClientCode, Username, Password);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "RateV2_Login"));
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to create a new room class", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to Login", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String masterCardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver, test_steps);
		String roomNumber = null;
		try {
			test_steps.add("========== Creating new Reservation ==========");
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, checkInDates.get(0));
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDates.get(0));
			reservationPage.enter_Adults(driver, test_steps, adults);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, ratePlan, "");
			reservationPage.clickOnFindRooms(driver, test_steps);
			reservationPage.selectRoom(driver, test_steps, roomClassName, Utility.RoomNo, "");
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
					phoneNumber, alternativePhone, email, account, accountType, address1, address2, address3, city,
					country, state, postalCode, isGuesProfile);
			if ((account.equalsIgnoreCase("") || account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard,
						masterCardExpDate);
			}
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", "", referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			status = reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			roomNumber = reservationPage.get_RoomNumber(driver, test_steps, "");
			app_logs.info("Room Number: "+roomNumber);
			test_steps.add("Room Number: "+roomNumber);
			roomChargeAmount = reservationPage.get_TripSummaryRoomChargesWithoutCurrency(driver, test_steps);
			tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
			
		try {
			reservationPage.cancelReservation(driver, "void");
			reservationPage.close_FirstOpenedReservation(driver, test_steps);
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, checkInDates.get(0));
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDates.get(0));
			reservationPage.enter_Adults(driver, test_steps, adults);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, ratePlan, "");
			reservationPage.clickOnFindRooms(driver, test_steps);		
			Assert.assertTrue(reservationPage.getRoomNumberAvailability(driver, test_steps, roomClassName, roomNumber), "Failed - Verifying that by cancelling single room, make sure room should be released back to available inventory");
			test_steps.add("Success - Verified that by cancelling single room, make sure room should be released back to available inventory");
			app_logs.info("Success - Verified that by cancelling single room, make sure room should be released back to available inventory");
			comments = "Verified that by cancelling single room, make sure room should be released back to available inventory";
			statusCode.add(0, "1");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyAvailabilityByCancelRoom", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
