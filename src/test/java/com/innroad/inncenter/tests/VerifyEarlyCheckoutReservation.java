package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;

public class VerifyEarlyCheckoutReservation extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	String testName = null;
	RoomClass rc = new RoomClass();
	Navigation nav = new Navigation();
	RoomStatus roomstatus = new RoomStatus();
	CPReservationPage res = new CPReservationPage();
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	ArrayList<String> newRooms = new ArrayList<String>();
	ArrayList<String> roomAbbri = new ArrayList<String>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ReservationSearch reservationSearch = new ReservationSearch();
	ReservationHomePage resHomePage = new ReservationHomePage();
	ArrayList<String> roomNos = new ArrayList<String>();
	Navigation navigation = new Navigation();
	Folio folio = new Folio();
	Elements_CPReservation element_Reservation;
	Groups group = new Groups();
	OR or = new OR();
	Tapechart tapeChart = new Tapechart();
	List<String> guestNames = new ArrayList<String>();
	String reportContant = null;
	TaskManagement task_mang = new TaskManagement();
	Properties properties = new Properties();
	ReservationSearch revSearch = new ReservationSearch();
	Policies policies = new Policies();
	Tax tax = new Tax();
	Reports report = new Reports();
	Double depositAmount = 0.0;
	String reservation = null, panelGuestName = null, status = null, tripTotalAmounts = null, panelStatus = null,
			propertyName = null, checkOutAmount = null, taxAmout = null, yearDate = null, policyName = null,
			timeZone = null;
	Date currentDate = null, previousDate = null;
	int halfAmountPaid = 0, checkoutBalance = 0;
	String buttonText = null, windowBefore = null;
	boolean isGuestStatementCheckBox = false, generateGuestStatementStatus = false, checkInPaymentWindow = false,
			checkoutPaymentWindow = false, isPolicyGridExit = false;
	String Message = "Are you sure you want to check-out all rooms at once? The Primary Guest will be responsible for all remaining Guest Charges.";
	String checkInAmount = null;
	String accountNumber = Utility.GenerateRandomString15Digit();
	String randomString = Utility.GenerateRandomNumber();
	String groupFirstName = "Group" + randomString;
	String groupLastName = "Res" + randomString;
	String accountName = groupFirstName + groupLastName;
	String DepositPolicyName = "deposit" + Utility.GenerateRandomNumber(300, 3000);
	String GuestMustPayPercentage = "100";
	String PolicyDescription = "New Deposit policy creation";
	Policies poli = new Policies();
	Admin admin = new Admin();
	String Chargestype = "Total Charges";
	String propertyId = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyEarlyCheckoutReservation(String testcaseId,String Scenario, String CheckInDate, String CheckOutDate, String Adult,
			String Children, String Salutation, String ResType, String GuestFirstName, String GuestLastName,
			String PhoneNumber, String AlternativePhone, String Email, String AccountType, String Address1,
			String Address2, String Address3, String city, String Country, String State, String PostalCode,
			String isGuesProfile, String PaymentType, String CardNumber, String NameOnCard, String CardExpDate,
			String marketSegment, String Referral, String Rateplan, String RoomClass) throws Exception {
		String[] roomClassArr = RoomClass.split("\\|");
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Get checkIN and Checkout Date
		try {

			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					if (!ResType.equalsIgnoreCase("Split")) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					} else {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkInDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
						break;
					}
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			if (ResType.equalsIgnoreCase("MRB") || ResType.equalsIgnoreCase("Split")) {
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				app_logs.info(CheckInDate);
				app_logs.info(CheckOutDate);
			} else {
				CheckInDate = checkInDates.get(0);
				CheckOutDate = checkOutDates.get(0);
				app_logs.info(CheckInDate);
				app_logs.info(CheckOutDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		if (Scenario.contains("Verify Property Options")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/824577";
			test_catagory = Scenario;
			testName = test_name;

			try {
				test_steps.add("<b>****Verify Property Detail****</b>");
				String property = resHomePage.getReportsProperty(driver, test_steps);
				test_steps.add("<b>Property Name : </b>" + property);
				app_logs.info("Property Name : " + property);
				navigation.setup(driver);
				navigation.Properties(driver);

				propertyId = navigation.getPropertyId(driver, property);
				app_logs.info(propertyId);
				test_steps.add("<b>Property Id : </b>" + propertyId);
				navigation.open_Property(driver, test_steps, property);
				test_steps.add("Open Property : " + property);
				resHomePage.verifyPropetyDetail(driver, test_steps);
				navigation.click_PropertyOptions(driver, test_steps);
				resHomePage.verifyPropetyOptions(driver, test_steps);

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (Scenario.contains("Verify Check out functionality when reservation is booked from group")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/824977";
			test_catagory = Scenario;
			testName = test_name;

			try {
				app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
				test_steps.add("==========CREATE NEW GROUP ACCOUNT==========");

				navigation.secondaryGroupsManu(driver);
				test_steps.add("Navigate Groups");
				app_logs.info(" Navigate Groups");

				test_steps.addAll(group.enterrGroupName(driver, accountName));
				accountNumber = Utility.GenerateRandomString15Digit();

				test_steps.addAll(group.enterAccountNo(driver, accountNumber));

				test_steps.addAll(group.selectAccountAttributes(driver, marketSegment, Referral));

				test_steps.addAll(group.enterMailingAddress(driver, groupFirstName, groupLastName, PhoneNumber,
						Address1, city, State, Country, PostalCode));

				test_steps.addAll(group.Billinginfo(driver));

				test_steps.addAll(group.clickOnSave(driver));

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				test_steps.add("<b>" + "===== Create Group Reservation From new Reservation button =====".toUpperCase()
						+ "</b>");
				app_logs.info("===== Create Group Reservation From new Reservation button =====".toUpperCase());

				group.click_GroupNewReservation(driver, test_steps);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);
				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");

				res.clickNext(driver, test_steps);
				res.verifyPopupChangeInPolicy(driver, test_steps);

				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));

				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);

				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.get_ReservationStatus(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
				panelStatus = res.getPanelStatusStatusName(driver);
				panelGuestName = res.getPanelStatusGuestName(driver);
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				Wait.wait2Second();

				res.closeReservationTab(driver);

				test_steps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.Search_ResNumber_And_Click(driver, reservation);
				test_steps.add("<b>****Start CheckIn Reservation****</b>");
				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}

				Wait.wait2Second();
				test_steps.add("<b>****Start Verifying Check-Out All Button ****</b>");
				res.completeCheckOutAction(driver, test_steps, false);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				res.closeReservationTab(driver);

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (Scenario.contains(
				"Verify that in MRB if any guets is checked-out then entry should update in history log with room number")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/824988";
			test_catagory = Scenario;
			testName = test_name;

			try {
				test_steps.add("<b>****Start Creating New MRB Reservation****</b>");
				Wait.wait10Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check Out All for MRB reservation.</b>");
				resHomePage.departedReservation(driver, "CheckOutSecondary", "No", true, test_steps);
				test_steps.add("<b>Verified Check-out secondary guest</b>");
				res.clickOnHistory(driver);
				test_steps.add("Clicked on history tab");
				res.verifyCheckoutRoomNo(driver, test_steps, roomNos);

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (Scenario.contains("Verify that user should able to check-out for split room reservation")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/824991";
			test_catagory = Scenario;
			testName = test_name;
			String IsSplitRes = "Yes";
			try {

				test_steps.add("<b>****Start Creating New Split Reservation****</b>");
				Wait.wait10Second();
				res.click_NewReservation(driver, test_steps);
				res.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						IsSplitRes);
				if (IsSplitRes.equalsIgnoreCase("Yes")) {
					res.enter_Adults(driver, test_steps, Adult);
					res.enter_Children(driver, test_steps, Children);
					res.select_Rateplan(driver, test_steps, Rateplan.split("\\|")[0], "");
				}
				res.clickOnFindRooms(driver, test_steps);
				ArrayList<String> rooms = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps,
						RoomClass.split("\\|")[0]);
				res.selectRoomToReserve(driver, test_steps, roomClassArr[0], rooms.get(0));
				res.verifySpinerLoading(driver);
				app_logs.info("rooms : " + rooms);
				rooms.clear();
				rooms = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClass.split("\\|")[1]);
				app_logs.info("rooms : " + rooms);
				res.selectRoomToReserve(driver, test_steps, roomClassArr[1], rooms.get(1));
				res.verifySpinerLoading(driver);

				res.clickNext(driver, test_steps);

				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));

				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);

				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.get_ReservationStatus(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
				panelStatus = res.getPanelStatusStatusName(driver);
				panelGuestName = res.getPanelStatusGuestName(driver);
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				Wait.wait2Second();

				res.closeReservationTab(driver);

				test_steps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");

			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				res.Search_ResNumber_And_Click(driver, reservation);
				test_steps.add("<b>****Start CheckIn Reservation****</b>");
				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}

				Wait.wait2Second();
				test_steps.add("<b>****Start Verifying Check-Out All Button ****</b>");
				res.completeCheckOutAction(driver, test_steps, false);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				res.closeReservationTab(driver);

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (Scenario.contains(
				"Verify that Secondary Guest should able to check-out & do not pay anything as their charges will be taken when primary guest checks out")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/824993";
			test_catagory = Scenario;
			testName = test_name;

			try {
				test_steps.add("<b>****Start Creating New MRB Reservation****</b>");
				Wait.wait10Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check Out Secondary for MRB reservation.</b>");
				String value = resHomePage.departedReservation(driver, "CheckOutSecondary", "No", true, test_steps);
				if (value.equalsIgnoreCase("0")) {
					test_steps.add(" Check-out amount is <b>" + value + "</b>");
				} else {
					test_steps.add(" Check-out amount is <b>" + value + "</b>");
				}
				test_steps.add("<b>Verified Check-out secondary guest</b>");
				test_steps.add("<b>Verify Check-out primary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutPrimary", "", true, test_steps);
				test_steps.add("<b>Verified Check-out primary guest</b>");
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (Scenario.contains(
				"Verify the folio when primary guest pays the charges of the secondary guest & select 'Yes' while checking-out")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/824994";
			test_catagory = Scenario;
			testName = test_name;
			String foundBalance = "";
			String expectedBalance = "0.00";

			try {
				test_steps.add("<b>****Start Creating New MRB Reservation****</b>");
				Wait.wait10Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				test_steps.add("<b>Verify Check In All guest</b>");
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				test_steps.add("<b>Verify Check In All guest</b>");
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check-out primary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutPrimary", "Yes", true, test_steps);
				res.click_Folio(driver, test_steps);
				test_steps.addAll(res.selectFolioOptionByIndex(driver, "2"));
				foundBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundBalance = Utility.RemoveDollarandSpaces(driver, foundBalance);
				test_steps.add("Verify Secondary guest charges should pay by the primary guest");
				if (foundBalance.equalsIgnoreCase(expectedBalance)) {
					test_steps.add("Verified Secondary guest charges should pay by the primary guest");
				} else {
					test_steps.add("Verified Secondary guest charges shouldn't pay by the primary guest");
				}
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (Scenario.contains("checkout process when the payment type as refund  for the MRB reservation")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825122";
			test_catagory = Scenario;
			testName = test_name;
			String value = "";
			try {
				test_steps.add("<b>****Start Creating New MRB Reservation****</b>");
				Wait.wait10Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				res.enterAmountTakePayment(driver, test_steps, "Yes", "500");
				driver.findElement(By.xpath(OR_Reservation.checkOutPaymentPayButton)).click();
				driver.findElement(By.xpath(OR_Reservation.NoShowSuccess_CloseButton)).click();
				res.saveReservation(driver, test_steps);
				value = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				value = Utility.removeCurrencySignBracketsAndSpaces(value);
				Wait.wait2Second();

			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check Out All for MRB reservation.</b>");
				resHomePage.verifyRefundPaymentPopupAtCheckout(driver, test_steps, "Yes", "CheckOutAll");
				res.click_Pay(driver, test_steps);
				resHomePage.verifyTakePaymentType(driver, test_steps, "Refund");
				driver.findElement(By.xpath(OR_Reservation.NoShowPaymentPopup_LogANDPayButton)).click();
				driver.findElement(By.xpath(OR_Reservation.NoShowSuccess_CloseButton)).click();
				res.saveReservation(driver, test_steps);
				test_steps.add("<b>Verify Check-out All guest</b>");
				resHomePage.departedReservation(driver, "CheckOutAll", "Yes", false, test_steps);
				test_steps.add("<b>Verified Check-out All guest</b>");
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (Scenario.contains(
				"Verify the settlement pop-up while 2nd guest is checking out with balance and primary guest is still checkin : reservation transfers")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825188";
			test_catagory = Scenario;
			testName = test_name;
			String foundSecondaryBalance = "";
			String expectedSecondaryBalance = "0.00";
			String primaryRoomBalance = "";
			String primaryRoomAfterCheckOutBalance = "";
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Creatin a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, DepositPolicyName, test_steps);
				poli.Deposit_Policy_Attributes(driver, Chargestype, GuestMustPayPercentage, test_steps);
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.click_Folio(driver, test_steps);
				primaryRoomBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				primaryRoomBalance = Utility.removeCurrencySignBracketsAndSpaces(primaryRoomBalance);
				test_steps.addAll(res.selectFolioOptionByIndex(driver, "2"));
				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check-out secondary guest</b>");
				res.click_DeatilsTab(driver, test_steps);
				resHomePage.departedReservation(driver, "CheckOutSecondary", "No", false, test_steps);
				test_steps.add("<b>Verified Check-out secondary guest</b>");
				test_steps.add("Verify primary guest balance is increase");
				res.click_Folio(driver, test_steps);
				primaryRoomAfterCheckOutBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				primaryRoomAfterCheckOutBalance = Utility
						.removeCurrencySignBracketsAndSpaces(primaryRoomAfterCheckOutBalance);
				Wait.wait2Second();

				String TotalBalance = String
						.valueOf(Double.sum(Double.valueOf(primaryRoomBalance), Double.valueOf(foundSecondaryBalance)));
				if (primaryRoomAfterCheckOutBalance.contains(TotalBalance)) {
					test_steps.add("Verified primary guest balance is increase from <b>" + primaryRoomBalance
							+ "</b> To <b>" + primaryRoomAfterCheckOutBalance + "</b>");
				} else {
					test_steps.add("Verified primary guest balance is not increase from <b>" + primaryRoomBalance
							+ "</b> To <b>" + primaryRoomAfterCheckOutBalance + "</b>");
				}
				test_steps.addAll(res.selectFolioOptionByIndex(driver, "2"));
				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				test_steps.add(
						"Verify Secondary guest charges should pay by the primary guest and the amount of secondary guest is equal to 0.00");
				if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
					test_steps.add(
							"Verified Secondary guest charges should pay by the primary guest and the amount of secondary guest is equal to <b>"
									+ foundSecondaryBalance + "</b>");
				} else {
					test_steps.add(
							"Verified Secondary guest charges shouldn't pay by the primary guest and the amount of secondary guest is no equal to 0.00");
				}
				res.click_DeatilsTab(driver, test_steps);
				test_steps.add("<b>Verify Check-out primary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutPrimary", "", false, test_steps);
				test_steps.add("<b>Verified Check-out primary guest</b>");
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (Scenario.contains(
				"Verify the settlement pop-up while 2nd guest is checking out with balance and primary guest is still checkin : without reservation transfers")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825189";
			test_catagory = Scenario;
			testName = test_name;
			String foundSecondaryBalance = "";
			String expectedSecondaryBalance = "0.00";
			String primaryRoomBalance = "";
			String expectedPrimaryRoomBalance = "0.00";
			int index = 2;
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Creatin a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, DepositPolicyName, test_steps);
				poli.Deposit_Policy_Attributes(driver, Chargestype, GuestMustPayPercentage, test_steps);
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.click_Folio(driver, test_steps);
				Wait.wait2Second();
				expectedPrimaryRoomBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				expectedPrimaryRoomBalance = Utility.removeCurrencySignBracketsAndSpaces(expectedPrimaryRoomBalance);
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check-out secondary guest</b>");
				res.click_DeatilsTab(driver, test_steps);
				resHomePage.departedReservation(driver, "CheckOutSecondary", "Yes", false, test_steps);
				test_steps.add("<b>Verified Check-out secondary guest</b>");
				res.click_Folio(driver, test_steps);
				test_steps.add("Verify secondary guest balance is equal to 0.00");
				res.mrbChangeFolio(driver, index, test_steps);
				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				test_steps.add("Verify Secondary guest charges is equal to 0.00");
				if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
					test_steps.add("Verified Secondary guest charges is equal<b>" + foundSecondaryBalance + "</b>");
				} else {
					test_steps.add("Verified Secondary guest charges is no equal 0.00");
				}
				res.click_DeatilsTab(driver, test_steps);
				test_steps.add("<b>Verify Check-out primary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutPrimary", "", false, test_steps);
				test_steps.add("<b>Verified Check-out primary guest</b>");
				res.click_Folio(driver, test_steps);
				primaryRoomBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				primaryRoomBalance = Utility.removeCurrencySignBracketsAndSpaces(primaryRoomBalance);
				Wait.wait2Second();
				if (!primaryRoomBalance.contains(expectedPrimaryRoomBalance)) {
					test_steps.add(
							"Verified primary guest amount is paid by secondary guest and tha balnce is equal to <b>"
									+ primaryRoomBalance + "</b>");
				} else {
					test_steps.add(
							"Verified primary guest amount is not paid by secondary guest and tha balnce is equal to <b>"
									+ primaryRoomBalance + "</b>");
				}
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (Scenario.contains(
				"Verify the settlement pop-up while 2nd guest is checking out without balance and primary guest is still checking")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825190";
			test_catagory = Scenario;
			testName = test_name;
			String foundSecondaryBalance = "";
			String expectedSecondaryBalance = "0.00";
			String primaryRoomBalance = "";
			String expectedPrimaryRoomBalance = "0.00";
			int index = 2;
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Creatin a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, DepositPolicyName, test_steps);
				poli.Deposit_Policy_Attributes(driver, Chargestype, GuestMustPayPercentage, test_steps);
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				test_steps.add("<b>****Start Creating New Reservation****</b>");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.click_Folio(driver, test_steps);
				Wait.wait2Second();
				expectedPrimaryRoomBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				expectedPrimaryRoomBalance = Utility.removeCurrencySignBracketsAndSpaces(expectedPrimaryRoomBalance);
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				test_steps.add("<b>Verify Check-out secondary guest</b>");
				res.click_DeatilsTab(driver, test_steps);
				resHomePage.departedReservation(driver, "CheckOutSecondary", "Yes", false, test_steps);
				test_steps.add("<b>Verified Check-out secondary guest</b>");
				res.click_Folio(driver, test_steps);
				res.mrbChangeFolio(driver, index, test_steps);
//					test_steps.addAll(res.selectFolioOptionByIndex(driver, "2"));
				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				test_steps.add("Verify Secondary guest charges is equal to 0.00");
				if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
					test_steps.add("Verified Secondary guest charges is equal<b>" + foundSecondaryBalance + "</b>");
				} else {
					test_steps.add("Verified Secondary guest charges is no equal 0.00");
				}
				res.click_DeatilsTab(driver, test_steps);
				test_steps.add("<b>Verify Check-out primary guest</b>");
				resHomePage.verifyRefundPaymentPopupAtCheckout(driver, test_steps, "", "CheckOutPrimary");
				res.click_Pay(driver, test_steps);
				resHomePage.verifyTakePaymentType(driver, test_steps, "Refund");
				driver.findElement(By.xpath(OR_Reservation.NoShowPaymentPopup_LogANDPayButton)).click();
				driver.findElement(By.xpath(OR_Reservation.NoShowSuccess_CloseButton)).click();
				res.saveReservation(driver, test_steps);
				test_steps.add("<b>Verify Check-out Pr guest</b>");
				res.click_DeatilsTab(driver, test_steps);
				resHomePage.departedReservation(driver, "CheckOutPrimary", "", false, test_steps);
				test_steps.add("<b>Verified Check-out primary guest</b>");
				test_steps.add("Verify primary guest balance is equal to 0.00");
				res.click_Folio(driver, test_steps);
				primaryRoomBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				primaryRoomBalance = Utility.removeCurrencySignBracketsAndSpaces(primaryRoomBalance);
				Wait.wait2Second();
				if (!primaryRoomBalance.contains(expectedPrimaryRoomBalance)) {
					test_steps.add(
							"Verified primary guest amount is paid by secondary guest and tha balnce is equal to <b>"
									+ primaryRoomBalance + "</b>");
				} else {
					test_steps.add(
							"Verified primary guest amount is not paid by secondary guest and tha balnce is equal to <b>"
									+ primaryRoomBalance + "</b>");
				}
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.equalsIgnoreCase(
				"Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out")) {

			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825221";
			test_catagory = Scenario;
			testName = test_name;
			String foundSecondaryBalance = "";
			String expectedSecondaryBalance = "0.00";
			String primaryRoomBalance = "";
			String expectedPrimaryRoomBalance = "0.00";
			int index = 2;
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Delete All Policies *******************");
				test_steps.add("************** Delete All Policies *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				test_steps.add("<b>****Start Creating New Reservation****</b>");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				test_steps.add("<b>Verify Check-out primary guest</b>");
				res.click_DeatilsTab(driver, test_steps);
				resHomePage.departedReservation(driver, "CheckOutPrimary", "Yes", false, test_steps);
				test_steps.add("<b>Verified Check-out priary guest</b>");
				res.click_Folio(driver, test_steps);
				res.mrbChangeFolio(driver, index, test_steps);

				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				test_steps.add("Verify Secondary guest charges is equal to 0.00");
				if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
					test_steps.add("Verified Secondary guest charges is equal<b>" + foundSecondaryBalance + "</b>");
				} else {
					test_steps.add("Verified Secondary guest charges is no equal 0.00");
				}

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (Scenario.contains(
				"Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Whole Multi Reservation (MRB) is CHECKOUT ALL")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825222";
			test_catagory = Scenario;
			testName = test_name;
			String foundSecondaryBalance = "";
			String expectedSecondaryBalance = "0.00";
			String primaryRoomBalance = "";
			String expectedPrimaryRoomBalance = "0.00";
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Deleting all policy *******************");
				test_steps.add("************** Deleting all policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				test_steps.add("<b>****Start Creating New Reservation****</b>");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				Wait.wait2Second();
				res.verifyStatusAfterReservation(driver, test_steps, "Reserved");
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check-out secondary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutAll", "Yes", false, test_steps);

				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				res.click_Folio(driver, test_steps);
				primaryRoomBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				primaryRoomBalance = Utility.removeCurrencySignBracketsAndSpaces(primaryRoomBalance);
				Wait.wait2Second();
				if (primaryRoomBalance.contains(expectedPrimaryRoomBalance)) {
					test_steps.add("Verified primary guest balance is equal<b>" + expectedPrimaryRoomBalance + "</b>");
				} else {
					test_steps.add("Verified Secondary guest balance is no equal 0.00");
				}

				res.mrbChangeFolio(driver, 2, test_steps);

				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				test_steps.add("Verify Secondary guest charges is equal to 0.00");
				if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
					test_steps.add("Verified Secondary guest balance is equal<b>" + foundSecondaryBalance + "</b>");
				} else {
					test_steps.add("Verified Secondary guest balance is no equal 0.00");
				}
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
		} 
		else if (Scenario
				.contains("Verify when user checkout All with balance ZERO & check the folio of secondary guest")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825223";
			test_catagory = Scenario;
			testName = test_name;
			String foundSecondaryBalance;
			int index = 2;
			String expectedSecondaryBalance = "0.00";
			Elements_CPReservation resElement = new Elements_CPReservation(driver);
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				Wait.wait2Second();
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				resHomePage.click_Take_payment_from_reservation_detail_button(driver, test_steps);
				Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_Amount);
				String amount = resElement.CP_Reservation_Folio_TakePayment_Amount.getText().trim();
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate,
						"Authorization Only", "Yes", "200", "No", "");

				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate,
						"Authorization Only", "Yes", "200", "No", "");

				Wait.wait2Second();
				res.click_DeatilsTab(driver, test_steps);
				test_steps.add("<b>Verify Check-out secondary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutSecondary", "Yes", false, test_steps);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				Wait.wait1Second();
				res.click_Folio(driver, test_steps);
				res.mrbChangeFolio(driver, index, test_steps);

				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				test_steps.add("Verify Secondary guest charges is equal to 0.00");
				if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
					test_steps.add("Verified Secondary guest charges is equal<b>" + foundSecondaryBalance + "</b>");
				} else {
					test_steps.add("Verified Secondary guest charges is no equal 0.00");
				}
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains(
				"Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out with CC payment method")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825225";
			test_catagory = Scenario;
			testName = test_name;
			String foundSecondaryBalance = "";
			String expectedSecondaryBalance = "0.00";
			String primaryRoomBalance = "";
			String expectedPrimaryRoomBalance = "0.00";
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Deleting a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				Wait.wait2Second();
				res.verifyStatusAfterReservation(driver, test_steps, "Reserved");
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				test_steps.add("<b>Verify Check-out primary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutPrimary", "Yes", false, test_steps);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				resHomePage.departedReservation(driver, "CheckOutSecondary", "", false, test_steps);
				res.click_Folio(driver, test_steps);
				String Tax = res.get_Taxes(driver, test_steps);
				primaryRoomBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				primaryRoomBalance = Utility.removeCurrencySignBracketsAndSpaces(primaryRoomBalance);
				Wait.wait2Second();

				if (primaryRoomBalance.contains(expectedPrimaryRoomBalance)) {
					test_steps.add("Verified primary guest balance is equal<b>" + expectedPrimaryRoomBalance + "</b>");
					test_steps.add("Verified primary guest tax  is equal to " + Tax);

				} else {
					test_steps.add("Verified Secondary guest balance is no equal 0.00");
					test_steps.add("Verified primary guest tax  is equal to " + Tax);

				}

				res.mrbChangeFolio(driver, 2, test_steps);
				Tax = res.get_Taxes(driver, test_steps);
				foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
				foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
				test_steps.add("Verify Secondary guest charges is equal to 0.00");
				if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
					test_steps.add("Verified Secondary guest balance is equal<b>" + foundSecondaryBalance + "</b>");
					test_steps.add("Verified secondary guest tax  is equal to " + Tax);

				} else {
					test_steps.add("Verified Secondary guest balance is no equal 0.00");
					test_steps.add("Verified secondary guest tax  is equal to " + Tax);

				}
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (Scenario.contains(
				"Verify Header bar in check out pop up while checking out Secondary guest when status of Primary guest is 'Reserved'/ 'Guaranteed'/'Confirmed'")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825271";
			test_catagory = Scenario;
			testName = test_name;
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Deleting a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				Wait.wait2Second();
				res.verifyStatusAfterReservation(driver, test_steps, "Reserved");
				resHomePage.inHouseReservation(driver, "CheckInSecondary", test_steps);
				test_steps.add("<b>Verify Check-out secondary guest</b>");
//					resHomePage.verifySecondaryGuestReservationStatusFromButton(driver, test_steps);
				resHomePage.departedReservation(driver, "CheckOutSecondary", "Yes", false, test_steps);
//					resHomePage.verifySecondaryGuestReservationStatusFromButton(driver, test_steps);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains(
				"Verify Header bar in Check out pop up while checking out Secondary guest when status of Primary guest is In House")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825274";
			test_catagory = Scenario;
			testName = test_name;
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Deleting a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				Wait.wait2Second();
				resHomePage.inHouseReservation(driver, "CheckInALL", test_steps);

				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected primary reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified primary guest reservation has been In-House");
				} else {
					test_steps.add("Primary Guest Reservation status is mismatching after In-House");

				}
				test_steps.add("<b>Verify Check-out secondary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutSecondary", "Yes", false, test_steps);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains(
				"Verify Header bar in check out pop up while checking out Secondary guest when status of Primary guest is 'Departed' If Primary Reservation Check Out")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825277";
			test_catagory = Scenario;
			testName = test_name;
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Deleting a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW MRB RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				test_steps.add("<b>****Start Creating New Reservation****</b>");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				Wait.wait2Second();
				resHomePage.inHouseReservation(driver, "CheckInPrimary", test_steps);
				test_steps.add("<b>Verify Check-out primary guest</b>");
				res.verifyStatusAfterReservation(driver, test_steps, "In-House");
				resHomePage.departedReservation(driver, "CheckOutPrimary", "", false, test_steps);
				test_steps.add("<b>Verified Check-out primary guest</b>");
				res.verifyStatusAfterReservation(driver, test_steps, "DEPARTED");
				resHomePage.inHouseReservation(driver, "CheckInSecondary", test_steps);
				test_steps.add("<b>Verify Check-out secondary guest</b>");
				resHomePage.departedReservation(driver, "CheckOutSecondary", "", false, test_steps);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains("verify the impact when the checkout amount balance is Zero balance")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825283";
			test_catagory = Scenario;
			testName = test_name;
			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Creatin a deposit policy *******************");
				test_steps.add("************** Creatin a deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, DepositPolicyName, test_steps);
				poli.Deposit_Policy_Attributes(driver, Chargestype, GuestMustPayPercentage, test_steps);
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE Single RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE Single RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");

				test_steps.add("<b>****Start Creating New Reservation****</b>");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);
				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				String TripTotalValue = driver
						.findElement(By.xpath(OR_Reservation.GetTripChargesInTripSummaryBeforeCreateReservation))
						.getText();
				TripTotalValue = Utility.RemoveDollarandSpaces(driver, TripTotalValue);

				res.deposit(driver, test_steps, "Yes", TripTotalValue);
				res.clickNext(driver, test_steps);

				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));

				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				Wait.wait2Second();
				test_steps.add("<b>****Start Verifying Check-Out Button ****</b>");
				res.completeCheckOutAction(driver, test_steps, false);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				Wait.wait1Second();
				res.closeReservationTab(driver);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario
				.contains("Verify Authrization should be considered in Check out when multiple Authorization")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825448";
			test_catagory = Scenario;
			testName = test_name;

			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("************** Creatin a CheckIn policy *******************");
				test_steps.add("************** Creatin a CheckIn policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, "CheckIn Ploicy", test_steps);
				WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
				Select select = new Select(selectType);
				select.selectByVisibleText("Check In");
				poli.Enter_Checkin_Policy_Attributes(driver, "authorize", "100");
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				app_logs.info("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);
				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				res.clickNext(driver, test_steps);

				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));

				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {

				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				Wait.wait2Second();
				test_steps.add("<b>****Start Verifying Check-Out Button ****</b>");
				test_steps.add("<b>Verify Check-out for last room for single</b>");
				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				res.enterAmountTakePayment(driver, test_steps, "Yes", "500");
				resHomePage.selectTakePaymentAuthorizationOnlyType(driver, test_steps);
				driver.findElement(By.xpath(OR_Reservation.NoShowPaymentPopup_LogANDPayButton)).click();
				driver.findElement(By.xpath(OR_Reservation.NoShowSuccess_CloseButton)).click();
				res.saveReservation(driver, test_steps);

				res.clickCheckOutButton(driver, test_steps);
				res.verifyCheckOutPopup(driver, test_steps);
				res.generatGuestReportToggle(driver, test_steps, "No");
				driver.findElement(By.xpath(OR.proceedtocheckoutpayment)).click();
				test_steps.add("Click on pay button");
				Wait.WaitForElement(driver, OR_Reservation.GetPaymentMethod);
				assertEquals(driver.findElement(By.xpath(OR_Reservation.GetPaymentMethod)).isEnabled(), false,
						"Error ");

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains(
				"Verify Notes is saved for payments when payment method is Card for Single Reservation in folio tab")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825492";
			test_catagory = Scenario;
			testName = test_name;
			String note = "TestNote123";

			try {
				app_logs.info("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);
				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				res.clickNext(driver, test_steps);

				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate, "Capture", "Yes",
						"200", "No", note);
				resHomePage.howerToIconInFolioDetail(driver, test_steps, PaymentType,note);
				test_steps.add("Verify Note Icon is Present");
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains(
				"Verify balance is displaying properly while check out the reservation with pending authorization line item")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825386";
			test_catagory = Scenario;
			testName = test_name;
			Elements_CPReservation resElement = new Elements_CPReservation(driver);

			try {
				app_logs.info("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);
				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				res.clickNext(driver, test_steps);

				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {

				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_Amount);
				String amount = resElement.CP_Reservation_Folio_TakePayment_Amount.getText().trim();
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate,
						"Authorization Only", "", "", "No", "");

				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				res.click_Folio(driver, test_steps);
				resHomePage.AuthorizationIcon(driver, test_steps, PaymentType);

				Wait.wait2Second();
				test_steps.add("<b>****Start Verifying Check-Out Button ****</b>");
				test_steps.add("<b>Verify Check-out for last room for single</b>");
				test_steps.add("<b>****Start Check-Out Button ****</b>");
				res.completeCheckOutAction(driver, test_steps, false);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				Wait.wait1Second();
				res.click_Folio(driver, test_steps);
				res.verifyFolioLineItemAmoutPaid(driver, PaymentType, amount, test_steps);

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains(
				"Verify authorization picker in check out screen and observe remaining authorization should be voided.")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825388";
			test_catagory = Scenario;
			testName = test_name;
			Elements_CPReservation resElement = new Elements_CPReservation(driver);

			try {
				app_logs.info("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				test_steps.add("==========CREATE NEW SINGLE RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
				Wait.waitforPageLoad(2000, driver);
				Wait.wait5Second();
				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);
				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				res.clickNext(driver, test_steps);

				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.click_Folio(driver, test_steps);
				folio.clickAddLineItemButton(driver);

				test_steps.addAll(folio.AddLineItem(driver, "Room Charge", "200", 2, ""));
				test_steps = folio.ClickSaveFolioButton(driver);

				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_Amount);
				String amount = resElement.CP_Reservation_Folio_TakePayment_Amount.getText().trim();
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate,
						"Authorization Only", "Yes", "200", "No", "");

				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate,
						"Authorization Only", "Yes", "200", "No", "");

				Wait.wait2Second();
				test_steps.add("<b>****Start Check-Out Button ****</b>");
				res.completeCheckOutAction(driver, test_steps, false);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				Wait.wait1Second();
				res.click_Folio(driver, test_steps);
				res.verifyFolioLineItemAmoutPaid(driver, PaymentType, amount, test_steps);

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		
		else if (Scenario.contains(
				"Verify that 'Updated By' field is displayed correctly for Checkout payment line item made for reservation.")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825420";
			test_catagory = Scenario;
			testName = test_name;

			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				PolicyDescription = "CheckIn Policy";
				app_logs.info("************** Creatin a CheckIn policy *******************");
				test_steps.add("************** Creatin a CheckIn policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, "CheckInPolicy", test_steps);
				WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
				Select select = new Select(selectType);
				select.selectByVisibleText("Check In");
				poli.Enter_Checkin_Policy_Attributes(driver, "capture", "10");
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				Tapechart tc = new Tapechart();
				tc.initiateReservationFromTapeChart(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children,
						Rateplan, "PS", "");
				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate, "Capture", "Yes",
						"20", "No", "");

				Wait.wait2Second();

				folio.clikOnLineItemDescriptionAfterSomeAction(driver, PaymentType, test_steps);
				resHomePage.verifyPaymentDetailUserName(driver, test_steps,
						(GuestFirstName + GuestLastName).toLowerCase());
				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				Wait.wait2Second();
				res.click_Folio(driver, test_steps);
				folio.clikOnLineItemDescriptionAfterSomeAction(driver, PaymentType, test_steps);
				Wait.wait2Second();
				resHomePage.verifyPaymentDetailUserName(driver, test_steps,
						(GuestFirstName + GuestLastName).toLowerCase());
				res.completeCheckOutAction(driver, test_steps, false);
				expectedStatus = "DEPARTED";
				getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked out: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked out");
				} else {
					test_steps.add("Reservation status is mismatching after checked out");

				}
				Wait.wait1Second();
				res.click_Folio(driver, test_steps);
				Wait.wait1Second();
				folio.clikOnLineItemDescriptionAfterSomeAction(driver, PaymentType, test_steps);
				Wait.wait2Second();
				resHomePage.verifyPaymentDetailUserName(driver, test_steps,
						(GuestFirstName + GuestLastName).toLowerCase());

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (Scenario.contains(
				"Verify that 'Updated By' field is displayed correctly for Checkin payment line item made for reservation.")) {
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825419";
			test_catagory = Scenario;
			testName = test_name;

			try {
				// After login
				test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, test_steps);
				navigation.policies(driver);
				test_steps.add("Navigated to policies");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				PolicyDescription = "CheckIn Policy";
				app_logs.info("************** Creatin a CheckIn policy *******************");
				test_steps.add("************** Creatin a CheckIn policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, "CheckInPolicy", test_steps);
				WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
				Select select = new Select(selectType);
				select.selectByVisibleText("Check In");
				poli.Enter_Checkin_Policy_Attributes(driver, "capture", "10");
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				Tapechart tc = new Tapechart();
				tc.initiateReservationFromTapeChart(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children,
						Rateplan, "PS", "");
				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));

				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);

				res.enterEmail(driver, test_steps, Email);

				test_steps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
			}

			catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				res.click_Folio(driver, test_steps);
				res.click_Pay(driver, test_steps);
				res.takePayment(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate, "Capture", "Yes",
						"20", "No", "");

				Wait.wait2Second();

				folio.clikOnLineItemDescriptionAfterSomeAction(driver, PaymentType, test_steps);
				resHomePage.verifyPaymentDetailUserName(driver, test_steps,
						(GuestFirstName + GuestLastName).toLowerCase());
				res.clickCheckInButton(driver, test_steps);
				Wait.wait3Second();
				generateGuestStatementStatus = res.generatGuestReportToggle(driver, test_steps, "No");
				buttonText = res.completeCheckInProcess(driver, test_steps);
				String expectedStatus = "IN-HOUSE";
				String getReservationStatus = res.getReservationStatusOnDetailSection(driver);
				test_steps.add("Expected reservation status after checked in: " + expectedStatus);
				test_steps.add("Found: " + getReservationStatus);
				if (getReservationStatus.equals(expectedStatus)) {
					test_steps.add("Verified reservation has been checked in");
				} else {
					test_steps.add("Reservation status is mismatching after checked in");

				}
				Wait.wait2Second();
				res.click_Folio(driver, test_steps);
				folio.clikOnLineItemDescriptionAfterSomeAction(driver, PaymentType, test_steps);
				Wait.wait2Second();
				resHomePage.verifyPaymentDetailUserName(driver, test_steps,
						(GuestFirstName + GuestLastName).toLowerCase());
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyEarlyCheckoutReservation", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}