
package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyAuthorizeLineItemsFromPrimaryToSecondary extends TestCore {

	public WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 res = new ReservationV2();
	ReservationSearch searchReservation = new ReservationSearch();
	GuestFolio guestFolio = new GuestFolio();
	FolioNew folioNew = new FolioNew();
	MerchantServices msObj = new MerchantServices();
	String seasonStartDate = null, seasonEndDate = null;

	String reservationNo;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, SNExcel))
			throw new SkipException("Skiping the test - " + test_name);
	}

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();

	@Test(dataProvider = "getData")
	public void verifyAuthorizeLineItemsFromPrimaryToSecondary(String TestCaseId, String TestCaseName,
			String accountStatus, String isCardPresent, String isCardNotPresent, String isEcommerce,
			String isRequireCVVForAll, String paymentGateway, String mode, String transactionKeyCode,
			String associateProperties, String checkInDate, String checkOutDate, String adults, String children,
			String rateplan, String roomClass, String rate, String salutation, String guestFirstName,
			String guestLastName, String paymentType, String cardNumber, String nameOnCard, String marketSegment,
			String referral, String amount, String TakePaymentType, String IsChangeInPayAmount, String Category,
			String roomClassAbb) throws Exception {
		 //if(Utility.getResultForCase(driver, TestCaseId)) {

		Utility.initializeTestCase(TestCaseId, Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		String yearDate = Utility.getFutureMonthAndYearForMasterCard();
		String last4Digit = Utility.getCardNumberHidden(cardNumber);
		ArrayList<String> typeList = new ArrayList<String>();
		typeList.add("Authorization Only");
		typeList.add("Capture");

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
				int day = Utility.getStayDays(checkInDate, checkOutDate);
				sessionEndDate
						.add(Utility.parseDate(Utility.getDatePast_FutureDate(day + 2), "MM/dd/yyyy", "dd/MM/yyyy"));
			}

			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(SNExcel, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		}
		// creating payment gateway

		try {

			String payGatewayAccountName = paymentGateway + "_Gateway";
			navigation.setup(driver);
			msObj.navigatetoMerchantServices(driver, test_steps);

			test_steps.add("Creating Payment Gateway : " + paymentGateway);
			app_logs.info("Creating Payment Gateway : " + paymentGateway);
			// Verify whether any Active Gateway exists or not, if it exists then delete and
			// create new one
			msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
			msObj.createNewMerchantService(driver, test_steps, payGatewayAccountName, "", "", accountStatus,
					isCardPresent, isCardNotPresent, isEcommerce, isRequireCVVForAll, paymentGateway, mode, "", "", "",
					"", "", "", "", "", "", "", "", transactionKeyCode, associateProperties);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		}

		HashMap<String, String> data1 = null;

		try {
			navigation.ReservationV2_Backward(driver);

			guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.fourDigitgenerateRandomString();
			data1 = res.createReservation(driver, test_steps, "From Reservations page", checkInDate + "|" + checkInDate,
					checkOutDate + "|" + checkOutDate, adults + "|" + adults, children + "|" + children,
					rateplan + "|" + rateplan, "", roomClass + "|" + roomClass, "", salutation, guestFirstName,
					guestLastName, "", "", "", "", "", "", "", "", "", "", false, marketSegment, referral, "", "", "",
					"", 0, "", false, "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "");
			reservationNo = data1.get("ReservationNumber");
			ArrayList<String> rooms = new ArrayList<String>();
			rooms.add(data1.get("RoomNumber1"));
			rooms.add(data1.get("RoomNumber2"));
			res.clickTakePayment(driver, test_steps);
			res.takePayment(driver, test_steps, Utility.splitInputData(paymentType).get(0),
					Utility.splitInputData(cardNumber).get(0), nameOnCard, yearDate,
					Utility.splitInputData(TakePaymentType).get(0), IsChangeInPayAmount, amount, "", "", true, typeList,
					true);
			res.click_Folio(driver, test_steps);
			folioNew.verifyFolioLineItems(driver, test_steps, checkInDate, Utility.splitInputData(paymentType).get(0),
					amount, nameOnCard, last4Digit, yearDate);
			String folioName = "Guest Folio For " + roomClassAbb + " : " + rooms.get(1) + "";
			folioNew.moveFolioByIndex(driver, test_steps, Utility.splitInputData(Category).get(0), 1, false,
					reservationNo, folioName);
			folioNew.moveToFolio(driver, test_steps, folioName);
			folioNew.verifyFolioLineItems(driver, test_steps, checkInDate, Utility.splitInputData(paymentType).get(0),
					amount, nameOnCard, last4Digit, yearDate);
			res.click_History(driver, test_steps);
			// String amount1 = Utility.convertDecimalFormat(amount);
			//String paymentStr = "Auto User Account #" + ":" + last4Digit + " "
					//+ Utility.parseDate(yearDate, "MM/yy", "MM/yy") + " " + "to Reservation";
			String str = "Moved MC item Name:" ;
			test_steps.add(str);
			app_logs.info(str);
			res.enterTextToSearchHistory(driver, str, true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		}
		try {
			for (int i = 0; i < Utility.testId.size(); i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "VerifyAuthPayment");
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);

		}
		 }

	

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyAuthPayment", SNExcel);
	}

}
