package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
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
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.ReservationV2Search;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;

public class VerifyCreateTaskEntitlement extends TestCore {
	public WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Users user = new Users();
	// Admin admin = new Admin();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account acc = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 res = new ReservationV2();
	ReservationSearch searchReservation = new ReservationSearch();
	ReservationV2Search Rv2Srearch = new ReservationV2Search();
	ReservationHomePage ResHome = new ReservationHomePage();
	GuestFolio guestFolio = new GuestFolio();
	FolioNew folioNew = new FolioNew();
	MerchantServices msObj = new MerchantServices();
	String seasonStartDate = null, seasonEndDate = null;
	public static String accountIdFromUrl = "";
	Admin ad = new Admin();
	TaskList Task = new TaskList();
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
	Boolean deleteTask = false;

	@Test(dataProvider = "getData")
	public void verifyCreateTaskEntitlement(String TestCaseId, String TestCaseName, String checkInDate,
			String checkOutDate, String adults, String children, String rateplan, String roomClass, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String amount, String TakePaymentType,
			String IsChangeInPayAmount, String IsTask, String TaskCategory, String TaskType, String TaskDetails,
			String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus) throws Exception {
		// if (Utility.getResultForCase(driver, TestCaseId)) {

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
		String roomNumber = "";
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> userList = new ArrayList<>();

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

		// **************Create Task entitlement*******************
		try {
			navigation.admin(driver);
			navigation.Roles(driver);
			ad.selectAdminstatorRole(driver, test_steps);
			ad.makeCreateTaskEnableOrDisable(driver, test_steps, false);
			ad.saveRole(driver, test_steps);
			app_logs.info("Disabled Task Create ");

			ad.logout(driver);
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

		Rv2Srearch.advanceLinkClick(driver, test_steps);
		Rv2Srearch.advanceSearchClick(driver, test_steps);
		int record = ResHome.getTotalRecordFound(driver);
		if (record > 1) {
			searchReservation.SelectFirstActiveReservation(driver);
			res.clickAddTaskButtonEnabledorDisabled(driver, test_steps, true);

		} else {

			HashMap<String, String> data = null;

			navigation.ReservationV2_Backward(driver);
			guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.fourDigitgenerateRandomString();
			data = res.createReservation(driver, test_steps, "From Reservations page", checkInDate, checkOutDate,
					adults, children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, "", "",
					"", "", "", "", "", "", "", "", false, marketSegment, referral, paymentType, cardNumber, nameOnCard,
					yearDate, 0, "", false, "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "");
			reservationNo = data.get("ReservationNumber");

			res.clickAddTaskButtonEnabledorDisabled(driver, test_steps, true);
		}
		try {
			navigation.admin(driver);
			navigation.Roles(driver);
			ad.selectAdminstatorRole(driver, test_steps);
			ad.makeCreateTaskEnableOrDisable(driver, test_steps, true);
			ad.saveRole(driver, test_steps);
			app_logs.info("Enabled Task Create Button");
			ad.logout(driver);
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(SNExcel, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		}

		// *******************************Update & Delete Task
		// Entitlement*******************
		try {
			navigation.admin(driver);
			navigation.Roles(driver);
			ad.selectAdminstatorRole(driver, test_steps);
			ad.makeUpdateTaskEnableOrDisable(driver, test_steps, false);
			ad.makeDeleteTaskEnableOrDisable(driver, test_steps, false);
			ad.saveRole(driver, test_steps);
			app_logs.info("Disabled Task Update  ");
			app_logs.info("Disabled Task Delete ");

			ad.logout(driver);
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

		HashMap<String, String> data1 = null;
		try {
			navigation.ReservationV2_Backward(driver);
			guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.fourDigitgenerateRandomString();
			data1 = res.createReservation(driver, test_steps, "From Reservations page", checkInDate, checkOutDate,
					adults, children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, "", "",
					"", "", "", "", "", "", "", "", false, marketSegment, referral, paymentType, cardNumber, nameOnCard,
					yearDate, 0, "", false, "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "");
			reservationNo = data1.get("ReservationNumber");
			res.AddTask(driver, test_steps, IsTask, TaskCategory, TaskType, TaskDetails, TaskRemarks, TaskDueon,
					TaskAssignee, TaskStatus);
			res.editTaskEnabledorDisabled(driver, test_steps, true);
			res.DeleteTaskEnabledorDisabled(driver, test_steps, true);
			navigation.admin(driver);
			navigation.Roles(driver);
			ad.selectAdminstatorRole(driver, test_steps);
			ad.makeUpdateTaskEnableOrDisable(driver, test_steps, true);
			ad.makeDeleteTaskEnableOrDisable(driver, test_steps, true);

			ad.saveRole(driver, test_steps);
			app_logs.info("Enabled Task Update ");
			app_logs.info("Enabled Task Delete ");
			ad.logout(driver);
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(SNExcel, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		}

		try {
			for (int i = 0; i < Utility.testId.size(); i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "TaskEntitlement");
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
		return Utility.getData("TaskEntitlement", SNExcel);
	}

}
