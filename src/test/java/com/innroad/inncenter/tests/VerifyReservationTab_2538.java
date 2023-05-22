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
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_Reservation;

public class VerifyReservationTab_2538 extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_category = null;
	ArrayList<String> test_steps = new ArrayList<>();
	String testName = null;
	RoomClass rc = new RoomClass();
	Navigation nav = new Navigation();
	ReservationHomePage reservationHomePage = new ReservationHomePage();
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
	Elements_CPReservation OR_resElement = new Elements_CPReservation(driver);

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
	String checkInAmount = null;
	String accountNumber = Utility.GenerateRandomString15Digit();
	String randomString = Utility.GenerateRandomNumber();
	String PolicyDescription = "";
	Elements_CPReservation resElement = new Elements_CPReservation(driver);
	Elements_Reservation elementReservation = new Elements_Reservation(driver);
	Policies poli = new Policies();
	Admin admin = new Admin();
	String Scenario;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyReservationTab_2538(String testCaseId, String Scenario, String CheckInDate, String CheckOutDate,
			String Adult, String Children, String Salutation, String ResType, String GuestFirstName,
			String GuestLastName, String PhoneNumber, String AlternativePhone, String Email, String AccountType,
			String Address1, String Address2, String Address3, String city, String Country, String State,
			String PostalCode, String isGuesProfile, String PaymentType, String CardNumber, String NameOnCard,
			String CardExpDate, String marketSegment, String Referral, String Rateplan, String RoomClass)
			throws Throwable {
		ArrayList<String> roomClassAbb = new ArrayList<String>();
		roomClassAbb.add("KS");
		roomClassAbb.add("KS");
		test_name = Scenario;
		test_category = Scenario;
		testName = test_name;
		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}
			driver = getDriver();
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
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
						if (Scenario.contains("future stay dates")) {
							checkInDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
							checkOutDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
						} else if (Scenario.contains("early")) {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						} else {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));

						}
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
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (Scenario.contains(
				"Verify the user is navigated back to current reservation page when user clicks NO on pop up to close the reservation without saving")) {
			try {
				nav.inventory(driver);
				nav.policies(driver);
				PolicyDescription = "Deposit Policy";
				app_logs.info("************** Creatin a Deposit policy *******************");
				test_steps.add("************** Creatin a Deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, "Deposit Policy", test_steps);
				WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
				Select select = new Select(selectType);
				select.selectByVisibleText("Deposit");

				poli.Deposit_Policy_Attributes(driver, "Room Charges", "20", test_steps);
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				try {
					poli.clickSavePolicyButton_2(driver, test_steps, "Deposit Policy");
				} catch (Exception e) {
					poli.clickSavePolicyButton(driver, test_steps, "Deposit Policy");
				}

				navigation.cpReservationBackward(driver);
				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);

				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				res.InputdepositDue(driver, test_steps, "50");
				res.clickNext(driver, test_steps);
				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));
				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);
				res.enterEmail(driver, test_steps, Email);
				res.enter_Account(driver, test_steps, "cooperate account with deposit policy", "Corp");
				String Amount = res.getOverrideDepositAmount(driver, test_steps);
				test_steps.add("Amount: " + Amount);
				app_logs.info("Amount: " + Amount);
				assertEquals(Amount, "50.00");
				res.ClickOnOverrideDepositAmount(driver, test_steps);
				String DepositAmountAfterOffOverrideToggle = res.getDepositDueAmount(driver);
				test_steps.add("DepositAmountAfterDisableOverrideToggle: <b>" + DepositAmountAfterOffOverrideToggle);
				app_logs.info("DepositAmountAfterDisableOverrideToggle: <b>" + DepositAmountAfterOffOverrideToggle);

				res.close_FirstOpenedReservationWithNo(driver, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (Scenario.contains(
				"Verify the user is navigated back to main reservation page when user clicks YES on pop up to close the reservation without saving")) {
			try {
				nav.inventory(driver);
				nav.policies(driver);
				PolicyDescription = "Deposit Policy";
				app_logs.info("************** Creatin a Deposit policy *******************");
				test_steps.add("************** Creatin a Deposit policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, "Deposit Policy", test_steps);
				WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
				Select select = new Select(selectType);
				select.selectByVisibleText("Deposit");

				poli.Deposit_Policy_Attributes(driver, "Room Charges", "20", test_steps);
				poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, RoomClass.split("\\|")[0]);
				poli.Associate_RatePlan(driver, Rateplan.split("\\|")[0]);
				try {
					poli.clickSavePolicyButton_2(driver, test_steps, "Deposit Policy");
				} catch (Exception e) {
					poli.clickSavePolicyButton(driver, test_steps, "Deposit Policy");
				}

				navigation.cpReservationBackward(driver);
				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);

				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				res.InputdepositDue(driver, test_steps, "50");
				res.clickNext(driver, test_steps);
				test_steps.addAll(res.verifyGuestProfileCheckoxChecked(driver, false));
				res.enterGuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);
				res.enterEmail(driver, test_steps, Email);
				res.enter_Account(driver, test_steps, "cooperate account with deposit policy", "Corp");
				String Amount = res.getOverrideDepositAmount(driver, test_steps);
				test_steps.add("Amount: " + Amount);
				app_logs.info("Amount: " + Amount);
				assertEquals(Amount, "50.00");
				res.ClickOnOverrideDepositAmount(driver, test_steps);
				String DepositAmountAfterOffOverrideToggle = res.getDepositDueAmount(driver);
				test_steps.add("DepositAmountAfterDisableOverrideToggle: <b>" + DepositAmountAfterOffOverrideToggle);
				app_logs.info("DepositAmountAfterDisableOverrideToggle: <b>" + DepositAmountAfterOffOverrideToggle);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (Scenario.contains("Verify navigation clicking on URL in message section.(Should open in new window)")) {
			Boolean InsiderTipIsPresent = false;
			try {

				Wait.wait2Second();
				InsiderTipIsPresent = reservationHomePage.Verify_ReservationPage_InsiderTip(driver, test_steps);
				if (InsiderTipIsPresent == true) {
					int beforeclicksize = driver.getWindowHandles().size();
					app_logs.info("Number of Windows: " + beforeclicksize);
					reservationHomePage.Click_InsiderTipHyperLink(driver, test_steps);
					int afterclicksize = driver.getWindowHandles().size();
					app_logs.info("Number of Windows: " + afterclicksize);
					if (afterclicksize > beforeclicksize) {
						test_steps.add("message section open in new window");
					}

				} else {
					test_steps.add("InsiderTip is Not Present");
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (Scenario.contains("Verify Message section without clicking on X mark navigating betwen screens.")) {
			try {

				Wait.wait2Second();
				reservationHomePage.Verify_ReservationPage_InsiderTip(driver, test_steps);
				Utility.refreshPage(driver);
				Wait.wait2Second();
				nav.inventory(driver);
				test_steps.add("Navigate to Inventory");

				Utility.refreshPage(driver);
				Wait.wait2Second();
				nav.Reservation(driver);
				test_steps.add("Navigate to Reservation");

				Wait.wait2Second();
				reservationHomePage.Verify_ReservationPage_InsiderTip(driver, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (Scenario.contains(
				"Verify the loading time of the reservation module by navigating to reservation dashboard from different modules.")) {
			try {

				test_steps.add("<b>========Navigating Modules========<b>");
				app_logs.info("<b>========Navigating Modules========<b>");

				////////////////// Accounts////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Accounts(driver);
				test_steps.add("Navigating to Accounts");
				app_logs.info("Navigating to Accounts");

				Wait.wait3Second();
				nav.Reservation(driver);
				;
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// GuestServices////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Guestservices_3(driver);
				test_steps.add("Navigating to GuestServices");
				app_logs.info("Navigating to GuestServices");
				Wait.waitforPageLoad(60, driver);

				Wait.wait3Second();
				nav.Reservation_Backward_2(driver);
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Inventory////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.inventory(driver);
				test_steps.add("Navigating to inventory");
				app_logs.info("Navigating to inventory");

				Wait.wait3Second();
				nav.Reservation(driver);
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Setup////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Setup(driver);
				test_steps.add("Navigating to Setup");
				app_logs.info("Navigating to Setup");

				Wait.wait3Second();
				nav.Reservation(driver);
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Admin////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Admin(driver);
				test_steps.add("Navigating to Admin");
				app_logs.info("Navigating to Admin");

				Wait.wait3Second();
				nav.Reservation(driver);
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// NightAudit////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.NightAudit(driver);
				test_steps.add("Navigating to NightAudit");
				app_logs.info("Navigating to NightAudit");

				Wait.wait3Second();
				nav.Reservation(driver);
				;
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Reports////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Reports(driver);
				test_steps.add("Navigating to Reports");
				app_logs.info("Navigating to Reports");

				Wait.wait3Second();
				nav.Reservation(driver);
				;
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				test_steps.add("<b>========Navigating Sub Modules========<b>");
				app_logs.info("<b>========Navigating Sub Modules========<b>");

				////////////////// Accounts////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Accounts(driver);
				test_steps.add("Navigating to Accounts");
				app_logs.info("Navigating to Accounts");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Statements(driver);
				test_steps.add("Navigating to Statements");
				app_logs.info("Navigating to Statements");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.UnitownerAccount(driver);
				test_steps.add("Navigating to Unit owner Item");
				app_logs.info("Navigating to Unit owner Item");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.TravelAgent(driver);
				test_steps.add("Navigating to TravelAgent");
				app_logs.info("Navigating to TravelAgent");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.ManagementTransfers(driver);
				test_steps.add("Navigating to ManagementTransfers");
				app_logs.info("Navigating to ManagementTransfers");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.AccountDistribution(driver);
				test_steps.add("Navigating to AccountDistribution");
				app_logs.info("Navigating to AccountDistribution");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Reservation(driver);
				;
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Inventory////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.inventory(driver);
				test_steps.add("Navigating to inventory");
				app_logs.info("Navigating to inventory");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.RatesGrid(driver);
				test_steps.add("Navigating to RatesGrid");
				app_logs.info("Navigating to RatesGrid");

				// Check Side Nav
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Seasons(driver);
				test_steps.add("Navigating to Seasons");
				app_logs.info("Navigating to Seasons");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Rates(driver);
				test_steps.add("Navigating to Rates");
				app_logs.info("Navigating to Rates");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Rules(driver);
				test_steps.add("Navigating to Rules");
				app_logs.info("Navigating to Rules");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Distribution(driver);
				test_steps.add("Navigating to Distribution");
				app_logs.info("Navigating to Distribution");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.policies(driver);
				test_steps.add("Navigating to policies");
				app_logs.info("Navigating to policies");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.backToReservationfromPolicy(driver);
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Setup////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Setup(driver);
				test_steps.add("Navigating to Setup");
				app_logs.info("Navigating to Setup");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Properties(driver);
				test_steps.add("Navigating to Properties");
				app_logs.info("Navigating to Properties");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.RoomClass(driver);
				test_steps.add("Navigating to RoomClass");
				app_logs.info("Navigating to RoomClass");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Taxes(driver);
				test_steps.add("Navigating to Taxes");
				app_logs.info("Navigating to Taxes");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.LedgerAccounts(driver);
				test_steps.add("Navigating to LedgerAccounts");
				app_logs.info("Navigating to LedgerAccounts");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Merchantservices(driver);
				test_steps.add("Navigating to Merchantservices");
				app_logs.info("Navigating to Merchantservices");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.DocumentTemplate(driver);
				test_steps.add("Navigating to DocumentTemplate");
				app_logs.info("Navigating to DocumentTemplate");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.ListManagemnet(driver);
				test_steps.add("Navigating to ListManagemnet");
				app_logs.info("Navigating to ListManagemnet");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.clickAirbnbSetup(driver);
				test_steps.add("Navigating to AirbnbSetup");
				app_logs.info("Navigating to AirbnbSetup");

				// Correct it
//				Wait.wait3Second();
//				nav.VrboSetup(driver);
//				test_steps.add("Navigating to VrboSetup");
//				app_logs.info("Navigating to VrboSetup");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.TaskManagement(driver);
				test_steps.add("Navigating to TaskManagement");
				app_logs.info("Navigating to TaskManagement");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Reservation_Backward_2(driver);
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Admin////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Admin(driver);
				test_steps.add("Navigating to Admin");
				app_logs.info("Navigating to Admin");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Clientinfo(driver);
				test_steps.add("Navigating to Clientinfo");
				app_logs.info("Navigating to Clientinfo");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Users(driver);
				test_steps.add("Navigating to Users");
				app_logs.info("Navigating to Users");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Roles(driver);
				test_steps.add("Navigating to Roles");
				app_logs.info("Navigating to Roles");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.GDPR(driver);
				test_steps.add("Navigating to GDPR");
				app_logs.info("Navigating to GDPR");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Reservation_Backward_3(driver);
				;
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// NightAudit////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.NightAudit(driver);
				test_steps.add("Navigating to NightAudit");
				app_logs.info("Navigating to NightAudit");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Reservation(driver);
				;
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

				////////////////// Reports////////////////
				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Reports(driver);
				test_steps.add("Navigating to Reports");
				app_logs.info("Navigating to Reports");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.AccountBalances(driver);
				test_steps.add("Navigating to AccountBalances");
				app_logs.info("Navigating to AccountBalances");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.LedgerBalances(driver);
				test_steps.add("Navigating to LedgerBalances");
				app_logs.info("Navigating to LedgerBalances");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.MerchantTrans(driver);
				test_steps.add("Navigating to MerchantTrans");
				app_logs.info("Navigating to MerchantTrans");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.DailyFalsh(driver);
				test_steps.add("Navigating to DailyFalsh");
				app_logs.info("Navigating to DailyFalsh");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.RoomForecast(driver);
				test_steps.add("Navigating to RoomForecast");
				app_logs.info("Navigating to RoomForecast");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.NetSales(driver);
				test_steps.add("Navigating to NetSales");
				app_logs.info("Navigating to NetSales");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.AdvanceDeposite(driver);
				test_steps.add("Navigating to AdvanceDeposite");
				app_logs.info("Navigating to AdvanceDeposite");

				Utility.refreshPage(driver);
				Wait.wait3Second();
				nav.Reservation_Backward(driver);
				test_steps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (Scenario.contains(
				"Verify cancellation room charges fees in folio when Policy Attributes set in % of room charges & Beyond #days of reservation")) {
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
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
				PolicyDescription = "Cancellation Policy";
				app_logs.info("************** Creatin a Cancellation policy *******************");
				test_steps.add("************** Creatin a Cancellation policy *******************");
				poli.DeleteAllPolicies(driver, test_steps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, "CancellationPloicy", test_steps);
				WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
				Select select = new Select(selectType);
				select.selectByVisibleText("Cancellation");

				poli.Cancellation_policy_Attributes(driver, "Room Charges", "20", "Beyond", "0", test_steps);
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
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else {
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
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			app_logs.info("************** Delete All policy *******************");
			test_steps.add("************** Delete All policy *******************");
			poli.DeleteAllPolicies(driver, test_steps);
			navigation.cpReservationBackward(driver);

		}

		try {
			if (ResType.equalsIgnoreCase("Single")) {

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
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);

			} else if (ResType.equalsIgnoreCase("MRB")) {
				res.click_NewReservation(driver, test_steps);
				res.createMRBReservations(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
						RoomClass, "Yes", "", "", Salutation, GuestFirstName, GuestLastName, PhoneNumber, PhoneNumber,
						Email, AccountType, Address1, Address2, Address3, city, Country, State, PostalCode,
						isGuesProfile, Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "", roomNos);
				res.clickBookNow(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				roomNos = res.getStayInfoRoomNo(driver, test_steps);
				reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
				res.clickCloseReservationSavePopup(driver, test_steps);
			} else if (ResType.equalsIgnoreCase("Split")) {
				try {

					test_steps.add("<b>****Start Creating New Split Reservation****</b>");
					Wait.wait10Second();
					res.click_NewReservation(driver, test_steps);
					res.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, Adult, Children, Rateplan, "",
							"Yes");

					res.enter_Adults(driver, test_steps, Adult);
					res.enter_Children(driver, test_steps, Children);
					res.select_Rateplan(driver, test_steps, Rateplan.split("\\|")[0], "");

					res.clickOnFindRooms(driver, test_steps);
					List<String> rooms = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps,
							RoomClass.split("\\|")[0]);
					res.selectRoomToReserve(driver, test_steps, RoomClass.split("\\|")[0], rooms.get(0));
					res.verifySpinerLoading(driver);
					app_logs.info("rooms : " + rooms);
					rooms.clear();
					rooms = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClass.split("\\|")[1]);
					app_logs.info("rooms : " + rooms);
					res.selectRoomToReserve(driver, test_steps, RoomClass.split("\\|")[1], rooms.get(1));
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

				}

				catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Verify Header bar in Cancel pop up while canceling Secondary guest",
						testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Verify Header bar in Cancel pop up while canceling Secondary guest",
						testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (Scenario.contains(
					"Verify Save button is enabled when stay dates are modified with 'Recalculate Rate' option for future stay dates reservation")) {
				test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825634";

				res.clickEditReservation(driver);

				res.clickChangeStayDetails(driver);
				CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.clickFindRooms(driver);
				resHomePage.verifySelectedRoomNumberisAvaible(driver, test_steps, RoomClass, roomNos);
			} else if (Scenario
					.contains("Verify that user is able to navigate to history tab and history should be present")) {
				testName = testName + " " + ResType;
				test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825663";
				res.click_History(driver, test_steps);
				resHomePage.verify_history_detail_is_present_or_not(driver, test_steps);

			}

			else if (Scenario.contains(
					"Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation early Check Out")) {
				test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825632";
				String foundSecondaryBalance = "";
				String expectedSecondaryBalance = "0.00";
				int index = 2;

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
					test_steps.add("<b>Verify Check-out primary guest</b>");
					resHomePage.departedReservation(driver, "CheckOutPrimary", "Yes", false, test_steps);
					test_steps.add("<b>Verified Check-out primary guest</b>");
					res.click_Folio(driver, test_steps);
					res.mrbChangeFolio(driver, index, test_steps);
					foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
					foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
					test_steps.add("Verify Secondary guest charges is equal to 0.00");
					if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
						test_steps
								.add("Verified Secondary guest charges is equal<b> " + foundSecondaryBalance + "</b>");
					} else {
						test_steps.add("Verified Secondary guest charges is no equal 0.00");
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			} else if (Scenario
					.contains("Verify that user is able to navigate to history tab and history should be present")) {
				Wait.wait2Second();
				res.click_History(driver, test_steps);

			}

			else if (Scenario.contains(
					"Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out")) {
				test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825633";
				String foundSecondaryBalance = "";
				String expectedSecondaryBalance = "0.00";
				int index = 2;

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
					test_steps.add("<b>Verify Check-out primary guest</b>");
					resHomePage.departedReservation(driver, "CheckOutPrimary", "Yes", false, test_steps);
					test_steps.add("<b>Verified Check-out primary guest</b>");
					res.click_Folio(driver, test_steps);
					res.mrbChangeFolio(driver, index, test_steps);
					foundSecondaryBalance = driver.findElement(By.xpath(OR.getBalanceFolioLineItems)).getText();
					foundSecondaryBalance = Utility.RemoveDollarandSpaces(driver, foundSecondaryBalance);
					test_steps.add("Verify Secondary guest charges is equal to 0.00");
					if (foundSecondaryBalance.equalsIgnoreCase(expectedSecondaryBalance)) {
						test_steps
								.add("Verified Secondary guest charges is equal<b> " + foundSecondaryBalance + "</b>");
					} else {
						test_steps.add("Verified Secondary guest charges is no equal 0.00");
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			} else if (Scenario.contains(
					"Verify that user is able to navigate to documents tab and user is able to upload/delete/download the files")) {

				
				
				res.click_Documents(driver, test_steps);
				reservationHomePage.UploadDocument(driver, test_steps, "C:\\Users\\DELL\\Desktop\\PDF_FILE.PDF");
				
				//Download
//				String DownloadButtonxpath = "//td[contains(text(),'error')]//..//td//a";
//				Wait.WaitForElement(driver, DownloadButtonxpath);
//				Wait.waitForElementToBeClickable(By.xpath(DownloadButtonxpath), driver);
//				driver.findElement(By.xpath(DownloadButtonxpath)).click();
				
				reservationHomePage.DeleteDocument(driver, test_steps, "PDF_FILE");
				
				app_logs.info("Done Here");
			}

			else if (Scenario.contains(
					"Verify the user is navigated back to current reservation page when user clicks NO on pop up to close the reservation without saving")) {
				test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825666";

				res.click_NewReservation(driver, test_steps);
				app_logs.info("CheckOut Date : " + CheckOutDate);
				res.select_CheckInDate(driver, test_steps, CheckInDate);
				res.select_CheckoutDate(driver, test_steps, CheckOutDate);
				test_steps.addAll(res.enterAdult(driver, Adult));
				test_steps.addAll(res.enterChildren(driver, Children));
				test_steps.addAll(res.selectRateplan(driver, Rateplan, "", 1));
				res.clickOnFindRooms(driver, test_steps);
				res.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				OR_resElement.CP_NewReservation_OverrideDeposit.click();
				res.clickNext(driver, test_steps);

			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyReservationTab_2538", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}

}