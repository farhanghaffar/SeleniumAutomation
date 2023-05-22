package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyCheckOutWithNonZeroBalance extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	Properties properties = new Properties();
	RoomClass roomClass = new RoomClass();
	Reports report = new Reports();
	NightlyRate nightlyRate = new NightlyRate();
	Rate rate = new Rate();
	NewRoomClassesV2 newRoomclass = new NewRoomClassesV2();
	CPReservationPage reservationPage = new CPReservationPage();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	String propertyName = null, roomClassNames = null, roomClassNameWithoutNum = null, rateNameWithoutNum = null,
			randomNum = null, rateName = null, yearDate = null, reservation = null, balance = null, windowName = null,
			seasonStartDate = null, seasonEndDate = null;
	int tableSize = 0;

	String testName = this.getClass().getSimpleName().trim();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);

	}

	private void handelingCatchError(Error e, String desc, String category, String module) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	private void handelingCatchException(Exception e, String desc, String category, String module) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyCheckOutWithNonZeroBalance(String testCaseID,String roomClassName, String maxAdults, String maxPersons,
			String roomQuantity, String checkInDate, String checkOutDate, String ratePlanName, String rates,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String zeroAmount, String children)
			throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_description = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848435' target='_blank'>"
				+ "Click here to open TestRail: 848435</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848523' target='_blank'>"
				+ "Click here to open TestRail: 848523</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848623' target='_blank'>"
				+ "Click here to open TestRail: 848623</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848632' target='_blank'>"
				+ "Click here to open TestRail: C848632</a><br>";
		test_catagory = "CPReservation_CheckOut";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments,"");
		roomClassNameWithoutNum = roomClassName;
		randomNum = Utility.threeDigitgenerateRandomString();
		roomClassNames = roomClassNameWithoutNum + randomNum;
		rateName = rateNameWithoutNum + randomNum;
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}

			// Get CheckIN and Checkout Date

			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
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
			datesRangeList = Utility.getAllDatesStartAndEndDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);

		} catch (Exception e) {
			handelingCatchException(e, "Failed to Get Check In Date and Checkout Date", testName, "Pre-Requisites");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Get Check In Date and Checkout Date", testName, "Pre-Requisites");
		}

		// Login
		try {
			driver = getDriver();
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			// Get Property Name
			propertyName = properties.getProperty(driver, test_steps);
		} catch (Exception e) {
			handelingCatchException(e, "Failed to login", testName, "Login");
		} catch (Error e) {
			handelingCatchError(e, "Failed to login", testName, "Login");
		}
		// Select Allow non-zero balance at the time of check-out from Properties
		try {
			navigation.setup(driver);
			navigation.properties(driver);
			navigation.open_Property(driver, test_steps, propertyName);
			navigation.click_PropertyOptions(driver, test_steps);
			properties.allowNonZeroBalanceDuringCheckout(driver, test_steps, "Yes");
			properties.PublishButton(driver);
			test_steps.add("Click Publish Button");
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Select Option form Properties", testName, "Properties");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Select Option form Properties", testName, "Properties");
		}
		// Logged Out Login Again
		try {
			Utility.logoutToInnCenter(driver, test_steps);
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Logout Login", testName, "Login");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Logout Login", testName, "Login");
		}

		try {
			test_steps.add("========== Creating Room Class ==========");
			navigation.mainSetupManu(driver);
			navigation.roomClass(driver, test_steps);
			newRoomclass.deleteAllRoomClassV2(driver, roomClassNameWithoutNum);
			roomNumberAssigned = newRoomclass.createRoomClassV2(driver, test_steps, roomClassNames, roomClassNames,
					maxAdults, maxPersons, roomQuantity);
			app_logs.info("Rooms Are: " + roomNumberAssigned);

		} catch (Exception e) {
			handelingCatchException(e, "Failed to Create Room Class", testName, "Room Class");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create Room Class", testName, "Room Class");
		}
		// Create Rates
		try {
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			app_logs.info("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rates, "", "", "", "", true);
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Create Rates", testName, "Rates");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create Rates", testName, "Rates");
		}
		// Create Reservation
		try {
			test_steps.add("<b>==========Start Creating Reservation==========</b>");

			guestFirstName = guestFirstName + Utility.threeDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.threeDigitgenerateRandomString();
			app_logs.info(guestFirstName + "--" + guestLastName);
			reservation = reservationPage.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, children,
					ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard,
					marketSegment, referral, roomClassNames, true, false);
			test_steps.add("Reservation Created: <b>" + reservation + "</>");
			app_logs.info("Reservation Created: " + reservation);

		} catch (Exception e) {
			handelingCatchException(e, "Failed to Create Reservation", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create Reservation", testName, "Reservation");
		}
		// Check In
		try {
			test_steps.add("<b>==========Start Check In==========</b>");
			windowName = reservationPage.getMainWindow(driver, test_steps);
			reservationPage.clickCheckInButton(driver, test_steps);
			reservationPage.generatGuestReportToggle(driver, test_steps, "No");
			reservationPage.completeCheckInProcessSingleRev(driver, test_steps);
			reservationPage.verifySpinerLoading(driver);
			test_steps.add("<b>==========Start Verifying In-House Status==========</b>");
			reservationPage.verifyReservationStatusStatus(driver, test_steps, "IN-HOUSE");
			test_steps.add("<b>==========Start Verifying Check-Out Button ==========</b>");
			reservationPage.verifyCheckOutButton(driver, test_steps);

		} catch (Exception e) {
			handelingCatchException(e, "Failed to Check In Reservation", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Check In Reservation", testName, "Reservation");
		}
		// Check out with Zero
		try {
			test_steps.add("<b>==========Get Balance Before Checkout==========</b>");
			balance = reservationPage.get_TripSummaryBalance_Amount(driver, test_steps);
			test_steps.add("<b>==========Get Folio Line Item Table Size Before Check Out==========</b>");
			reservationPage.click_Folio(driver, test_steps);
			tableSize = reservationPage.getFolioLineItemTRSize(driver, test_steps);
			reservationPage.click_DeatilsTab(driver, test_steps);
			test_steps.add("<b>==========Start Check-Out With Zero Amount ==========</b>");
			reservationPage.clickCheckOutButton(driver, test_steps);
			Wait.wait5Second();
			reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));
			reservationPage.proceedToCheckOutPayment(driver, test_steps);
			reservationPage.inputAmountWhileCheckINAndCheckOut(driver, test_steps, zeroAmount);
			reservationPage.clickCheckoutWithoutPaymentButton(driver, test_steps);
			test_steps.add("<b>==========Start Verifying Report Generated in New Tab ==========</b>");
			reservationPage.switchToChildWindow(driver, test_steps);
			report.verifyGuestStatementReportGenerated(driver, test_steps);
			reservationPage.switchToMainWindow(driver, test_steps, windowName);
			Wait.wait10Second();
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Check In Reservation", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Check In Reservation", testName, "Reservation");
		}

		try {
			test_steps.add("<b>==========Start Verifying Roll Back Button ==========</b>");
			reservationPage.verifyRollBackButton(driver, test_steps);
			test_steps.add("<b>==========Start Verifying DEPARTED Status==========</b>");
			reservationPage.verifyReservationStatusStatus(driver, test_steps, "DEPARTED");
			test_steps.add("<b>==========Start Verifying Balance==========</b>");
			String getBalance = reservationPage.get_TripSummaryBalance_Amount(driver, test_steps);
			app_logs.info(getBalance);
			reservationPage.verifyTripSummaryBalanceAmount(driver, test_steps, getBalance, balance);
			test_steps.add("<b>==========Start Verifying History==========</b>");
			reservationPage.click_History(driver, test_steps);
			reservationPage.verifyPaymentDescriptionAtGuestHistory(driver, test_steps, "Reservation",
					"Checked out this reservation");
			test_steps.add("<b>==========Start Verifying Folio==========</b>");
			reservationPage.click_Folio(driver, test_steps);
			reservationPage.checkedDisplayVoidItem(driver, test_steps);
			reservationPage.verifyBalanceAmountAtFolio(driver, test_steps, balance);
			reservationPage.verifyFolioLineItemTRSize(driver, test_steps, tableSize);
			/*test_steps.add("verify the impact when the checkout amount balance is non Zero balance"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848523' target='_blank'>"
					+ "Click here to open TestRail: 848523</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments,
					"verify the impact when the checkout amount balance is non Zero balance");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);
			test_steps.add("Verify Check out with non zero balance"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848435' target='_blank'>"
					+ "Click here to open TestRail: 848435</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify Check out with non zero balance");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
					Utility.comments.get(1), TestCore.TestRail_AssignToID);
			test_steps.add("verify the impact when the checkout amount balance is Zero balance"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848623' target='_blank'>"
					+ "Click here to open TestRail: 848623</a><br>");
			Utility.testCasePass(Utility.statusCode, 2, Utility.comments,
					"verify the impact when the checkout amount balance is Zero balance");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
					Utility.comments.get(2), TestCore.TestRail_AssignToID);

			test_steps.add("Allow Non Zero Balance during checkoutÃ¢â‚¬â„¢ should be checked/unchecked."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848632' target='_blank'>"
					+ "Click here to open TestRail: C848632</a><br>");
			Utility.testCasePass(Utility.statusCode, 3, Utility.comments,
					"Allow Non Zero Balance during checkoutÃ¢â‚¬â„¢ should be checked/unchecked.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3),
					Utility.comments.get(3), TestCore.TestRail_AssignToID);*/
			reservationPage.closeReservationTab(driver);
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Checkout with non zero");
			}
			
			
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Check Out Reservation with Zero Balance ", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Check Out Reservation with Zero Balance", testName, "Reservation");
		}

		try {
			test_steps.add("<b>****Delete Room Class****</b>");
			navigation.setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.roomClass(driver, test_steps);
			newRoomclass.deleteRoomClassV2(driver, roomClassName);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassNameWithoutNum + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassNameWithoutNum);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Delete Room Class ", testName, "Rooom Class");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Delete Room Class", testName, "Rooom Class");
		}
		}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyCheckOutWithNonZeroBalanc", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
