package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AirbnbInObjects;
import com.innroad.inncenter.pageobjects.AirbnbObjects;
import com.innroad.inncenter.pageobjects.AirbnbReservationUI;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAirbnbReservationCheckout extends TestCore{
	
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	private WebDriver driver = null;
	CPReservationPage reservation = new CPReservationPage();
	Navigation navigation = new Navigation();
	AirbnbInObjects airbnbIn = new AirbnbInObjects();
	Admin admin = new Admin();
	ReservationHomePage homePage = new ReservationHomePage();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	AirbnbObjects airbnb = new AirbnbObjects();
	AirbnbReservationUI airbnbReservationUI= new AirbnbReservationUI();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, airbnbexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Airbnb")
	public void verifyAirbnbReservationCheckout(String action,String roomClassName,String beginDate, String noOfNights,
			 String firstName,  String lastName,String emails, String adultCount, String childCount,
			 String phoneNum, String baseprice, String payoutamount) throws ParseException {
		String testCaseID="855249|848597";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String testName = null,currency = null,listingid = null,ratePlanName = "",startDate1 = null,startDate2 = null,
				endDate1 = null,startDate = null, endDate = null,night = "",confirmationNo = null,
				guestFirstName = firstName + Utility.generateRandomStringWithGivenLength(3), guestName = "";
		HashMap<String, String> getAirbnbDetails = null;
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> stayDates = new ArrayList<String>();
		String externalConfirmation = Utility.generateRandomStringWithGivenLength(5);
		Utility.initializeTestCase("855249|848597", Utility.testId, Utility.statusCode, Utility.comments, "");
		try {
			testName = "VerifyAirbnbReservation"+ " " + action;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("#################################################################################");

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}

			driver = getDriver();
			loginAirbnb(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			test_steps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			String property = homePage.getReportsProperty(driver, test_steps);
			test_steps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);

			navigation.admin(driver);
			navigation.clickonClientinfo(driver);
			test_steps.add("<b>===== Getting Currency =====</b>");
			app_logs.info("===== Getting Currency=====");
			admin.clickOnClient(driver, property);
			test_steps.add("Open Property : " + property);
			app_logs.info("Open Property : " + property);

			admin.clickClientOptions(driver, test_steps);
			currency = admin.getDefaultCurrency(driver);
			test_steps.add("Default Currency : " + currency);
			app_logs.info("Default Currency : " + currency);

			navigation.navigateToSetupfromRoomMaintenance(driver);
			test_steps.add("Click Setup");
			app_logs.info("Click Setup");
			navigation.clickAirbnbSetup(driver);
			test_steps.add("Click Vrbo Setup");
			app_logs.info("Click Vrbo Setup");

			test_steps.add("<b>===== Getting Listing ID =====</b>");
			app_logs.info("===== Getting Listing ID=====");

			getAirbnbDetails = airbnbIn.getAirbnbListingId(driver, roomClassName, property);
			listingid = getAirbnbDetails.get("listingId");
			test_steps.add("ListingId ID : " + getAirbnbDetails.get("listingId"));
			app_logs.info("Listing ID " + getAirbnbDetails.get("listingId"));
			ratePlanName = getAirbnbDetails.get("ratePlan");
			test_steps.add("RatePlan ID : " + getAirbnbDetails.get("ratePlan"));
			app_logs.info("RatePlan ID " + getAirbnbDetails.get("ratePlan"));

			if (!(Utility.validateInput(beginDate))) {
				startDate2=Utility.getCurrentDate("dd/MM/yyyy");
				startDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
				}
			} else {
				checkInDates = Utility.splitInputData(beginDate);
				startDate1 = checkInDates.get(0);
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.addDays(startDate1, 2);
				} else {

					endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
				}
			}
			startDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "YYYY-MM-dd");
			endDate = Utility.parseDate(endDate1, "dd/MM/yyyy", "YYYY-MM-dd");			
			stayDates = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			night = String.valueOf(stayDates.size());
			app_logs.info(startDate);
			app_logs.info(endDate);
			app_logs.info(stayDates);
			app_logs.info(night);
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			
			guestName = guestFirstName + " " + lastName;
			test_steps.add("<b>===== CREATE AIRBNB RESERVATION =====</b>");
			app_logs.info("=====CREATE AIRBNB RESERVATION=====");

			airbnb.airbnbReservationActions("create", externalConfirmation, startDate, endDate, guestFirstName,
					lastName, listingid, night, emails, adultCount, childCount, phoneNum, baseprice,
					payoutamount, vrvoproperties.getProperty("BookingRequestToken"));
				
			test_steps.add("Created Airbnb reservation successfully: ");
			navigation.reservationBackward3(driver);
			confirmationNo = reservation.guestUnlimitedSearchAndOpen(driver, test_steps, guestName, true, 12);
			test_steps.add("Verified reservation existing in inncenter");
			app_logs.info("Confirmation No : " + confirmationNo);
			test_steps.add("Confirmation No : " + confirmationNo);
			Wait.wait15Second();
			reservation.ClickEditStayInfo(driver, test_steps);
			reservation.ClickStayInfo_ChangeDetails(driver, test_steps);
			Wait.wait10Second();
			reservation.clickYesButtonRollBackMsg(driver, test_steps);			
			Wait.waitforPageLoad(50, driver);
			reservation.select_CheckInDate(driver, test_steps, startDate2);
			reservation.clickFindRooms(driver);
			Wait.waitforPageLoad(50, driver);
			ArrayList<String> rooms = reservation.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName);
			reservation.selectRoomToReserve(driver, test_steps, roomClassName, rooms.get(0));
			Wait.waitforPageLoad(50, driver);
			reservation.clickYesButtonRollBackMsg(driver, test_steps);	
			Wait.waitforPageLoad(30, driver);
			reservation.clickSaveAfterEditStayInfo(driver);
			Wait.waitforPageLoad(30, driver);
			Wait.wait25Second();
			test_steps.add("<b>======Start Checkin ======</b>");
			app_logs.info("======Start Checkin ======");
			reservation.clickCheckInButton(driver, stayDates);
			reservation.generatGuestReportToggle(driver, test_steps, "No");	
			reservation.completeCheckInProcessSingleRev(driver, rooms);
			reservation.verifySpinerLoading(driver);
			Wait.waitforPageLoad(60, driver);
			test_steps.add("<b>======Start Checkout ======</b>");
			app_logs.info("======Start Checkout ======");
			reservation.clickCheckOutButton(driver, test_steps);
			Wait.wait5Second();
			airbnbReservationUI.verifySettlementMsgForAirBNB(driver, test_steps);
			reservation.clickSettlementYesButton(driver, test_steps);
			reservation.verifySpinerLoading(driver);
			Wait.wait60Second();
			Wait.wait60Second();
			reservation.clickLogORPayAuthorizedButton(driver, test_steps);
			reservation.verifySpinerLoading(driver);
			airbnbReservationUI.clickContinueButton(driver, test_steps);
			reservation.verifySpinerLoading(driver);
			Wait.waitforPageLoad(50, driver);
			Wait.wait60Second();
			Wait.wait60Second();
			reservation.clickCheckOutButton(driver, test_steps);
			Wait.waitforPageLoad(50, driver);
			reservation.generatGuestReportToggle(driver, test_steps, "No");	
			reservation.proceedToCheckOutPayment(driver, test_steps);
			reservation.verifySpinerLoading(driver);
			Wait.wait60Second();
			reservation.clickonPaymentMetod(driver);
			Wait.wait60Second();
			reservation.selectPaymentMethod(driver, "992935193071511", test_steps);
			reservation.clickLogORPayAuthorizedButton(driver, test_steps);
			reservation.verifySpinerLoading(driver);
			Wait.wait10Second();
			reservation.clickCloseonSuccessfullCheckout(driver);
			reservation.verifySpinerLoading(driver);
			reservation.verifyRollBackButton(driver, test_steps);
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify room status for MRB");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
	}catch (Exception e) {
		Utility.catchExceptionOTA(e, test_description, test_catagory, "Airbnb Res And Checkout", testName,
				test_description, test_catagory, test_steps);
	} catch (Error e) {
		Utility.catchErrorOTA(e, test_description, test_catagory, " Airbnb Res And Checkout", testName,
				test_description, test_catagory, test_steps);
	}
	}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyAirbnbReservationCheckout", airbnbexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
		 Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
	}

}
