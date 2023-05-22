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
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CheckoutWithNonZeroBalance  extends TestCore {



	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> roomNumber = new ArrayList<>();
	Navigation navigation = new Navigation();
	Properties properties = new Properties();
	RoomClass roomClass = new RoomClass();
	Reports report = new Reports();
	Rate rate = new Rate();
	NewRoomClassesV2 newRoomclass= new NewRoomClassesV2();
	CPReservationPage reservationPage = new CPReservationPage();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	String  propertyName = null, roomClassNames = null, roomClassAbbs = null,
			roomClassNameWithoutNum = null, roomClassAvvWithoutNum = null, rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservation = null, balance = null,windowName=null;
	int tableSize = 0;

	String testName = this.getClass().getSimpleName().trim();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	
	
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
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
	public void verifyCheckOutWithNonZeroBalance(String roomClassName, String roomClassAbbrivation, String maxAdults,
			String maxPersons, String roomQuantity, String ratesNames, String displayName, String baseAmount,
			String ratePolicy, String rateDescription, String checkInDate, String checkOutDate, String ratePlanName,String salutation,
			String guestFirstName, String guestLastName, String paymentType, String cardNumber, String nameOnCard,
			String marketSegment, String referral, String zeroAmount) throws Exception {
			
		
	String testcaseId="848632";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848632");
		//String propertyName="sowmya test property";
		test_description = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848632' target='_blank'>"
				+ "Click here to open TestRail: C848632</a><br>";
				
		test_catagory = "SetUp";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		roomClassNameWithoutNum = roomClassName;
		roomClassAvvWithoutNum = roomClassAbbrivation;
		rateNameWithoutNum = ratesNames;
		randomNum = Utility.GenerateRandomNumber();
		roomClassNames = roomClassNameWithoutNum + randomNum;
		roomClassAbbs = roomClassAvvWithoutNum + randomNum;
		rateName = rateNameWithoutNum + randomNum;

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

		
			// Get CheckIN and Checkout Date

			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
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
			login_Autoota(driver);
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
			login_Autoota(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			handelingCatchException(e, "Failed to Logout Login", testName, "Login");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Logout Login", testName, "Login");
		}

		
		
		// Create Reservation
		try {
			test_steps.add("<b>==========Start Creating Reservation==========</b>");
			
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			reservationPage.enter_Adults(driver, test_steps, maxAdults);
			reservationPage.select_Rateplan(driver, test_steps, ratesNames,"");
			reservationPage.clickOnFindRooms(driver, test_steps);
			reservationPage.select_SpecificRoom(driver, test_steps, roomClassName, "", "");
			reservationPage.clickNext(driver, test_steps);
			yearDate = Utility.getFutureMonthAndYearForMasterCard();
			guestFirstName = guestFirstName + randomNum;
			guestLastName = guestLastName + randomNum;
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation,
					guestFirstName, guestLastName, "No");
			reservationPage.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, yearDate);
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			String roomNumber1 = reservationPage.get_RoomNumber(driver, test_steps, "");
			roomNumber.add(roomNumber1);
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Create Reservation", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create Reservation", testName, "Reservation");
		}
		// Check In
		try {
			test_steps.add("<b>==========Start Check In==========</b>");
			reservationPage.Click_CheckInButton(driver, test_steps);
			reservationPage.selectRoomAtPaymentCheckInPopup(driver, test_steps, roomNumber);
			
			reservationPage.disableGenerateGuestReportToggle(driver, test_steps);
			reservationPage.clickOnProceedToCheckInPaymentButton(driver, test_steps);
			reservationPage.roomStatusDirty(driver, test_steps);
			reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
			reservationPage.checkIn_PaymentSuccess_PopupClose(driver, test_steps);	
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
			reservationPage.generatGuestReportToggle(driver, test_steps, "No");
			reservationPage.proceedToCheckOutPayment(driver, test_steps);
			reservationPage.inputAmountWhileCheckINAndCheckOut(driver, test_steps, zeroAmount);
			reservationPage.clickCheckoutWithoutPaymentButton(driver, test_steps);
			String getBalance = reservationPage.get_TripSummaryBalance_Amount(driver, test_steps);
			app_logs.info(getBalance);
			reservationPage.verifyTripSummaryBalanceAmount(driver, test_steps, getBalance, balance);
		
			statusCode.add(0, "1");
			comments.add(0, "checkedout with zero balance");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Check In Reservation", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Check In Reservation", testName, "Reservation");
		}

		
		
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("CheckoutNonZeroBal", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}


}
