package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.jetty9.security.UserAuthentication;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.ListManagement;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;
import com.sun.jna.platform.win32.Netapi32Util.User;

public class ReportsV2LedgerBalancesReservation extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String date = null;

	Double depositAmount = 0.0;
	Double paidDeposit = 0.0;
	String reservation = null;
	String status = null;
	String roomNumber = null;

	String folioRoomCharges = null, folioTaxes = null, folioIncidentals = null, folioTripTotal = null,
			folioBalance = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateLedgerBalancesReport(String Incidentals, String RoomCharges, String Taxes, String Transfers,
			String PaymentMethod, String Fees, String UnitExpenses, String UnitRevenues, String TravelAgentCommission,
			String DistributionMethod, String GiftCertificate, String GiftCertificateRedeemed, String CheckInDate,
			String CheckOutDate, String Adults, String Children, String Rateplan, String PromoCode, String RoomClass,
			String IsAssign, String IsDepositOverride, String DepositOverrideAmount, String Salutation,
			String GuestFirstName, String GuestLastName, String PhoneNumber, String AltenativePhone, String Email,
			String Account, String AccountType, String Address1, String Address2, String Address3, String City,
			String Country, String State, String PostalCode, String IsGuesProfile, String PaymentType,
			String CardNumber, String NameOnCard, String CardExpDate, String IsChangeInPayAmount,
			String ChangedAmountValue, String TravelAgent, String MarketSegment, String Referral, String IsAddNotes,
			String NoteType, String Subject, String Description, String IsTask, String TaskCategory, String TaskType,
			String TaskDetails, String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus)
			throws Throwable {

		test_name = "ReportsV2LedgerBalancesReport";
		test_description = "Ledger Balances Report - ReportsV2LedgerBalancesReport <br>";
		test_category = "ReportsV2 - Ledger Balances report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		Login login = new Login();
		ReportsV2 report = new ReportsV2();
		Admin admin = new Admin();
		Properties prop = new Properties();
		Users user = new Users();
		CPReservationPage res = new CPReservationPage();
		Folio folio = new Folio();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIn, CheckOut and TaskDueOn Date
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			CheckInDate = checkInDates.get(0);
			CheckOutDate = checkOutDates.get(0);
			TaskDueon = CheckOutDate;

			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			date = Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yy");
			app_logs.info(date);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			driver = getDriver();
			try {
				loginReportsV2(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginReportsV2(driver);
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
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

		String[] inputs = { "Incidentals", "Room Charges", "Taxes", "Transfers", "Payment Method", "Fees",
				"Unit Expenses", "Unit Revenues", "Travel Agent Commission", "Distribution Method", "Gift Certificate",
				"Gift Certificate Redeemed" };
		String[] options = { Incidentals, RoomCharges, Taxes, Transfers, PaymentMethod, Fees, UnitExpenses,
				UnitRevenues, TravelAgentCommission, DistributionMethod, GiftCertificate, GiftCertificateRedeemed };

		try {

			nav.setup(driver);
			// nav.Properties(driver);
			// prop.SearchProperty_Click(driver, "River Rock Inn", test_steps);
			// prop.PropertyOptions(driver, test_steps);
			prop.LongStay(driver, "River Rock Inn", "3", test_steps);

			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);

			// report.selectSelectInputsAll(driver, inputs, options, test_steps);

			report.clickOnRunReport(driver);

			driver.close();
			Utility.switchTab(driver, 0);

			// nav.Reservation(driver);
			driver.findElement(By.xpath("//*[contains(text(),'Reservations')]")).click();

			// res.click_NewReservation(driver, test_steps);
			// res.checkInDate(driver, 0);
			// res.checkOutDate(driver, +1);
			// res.enterAdult(driver, "2");
			// res.enterChildren(driver, "1");
			// res.clickOnFindRooms(driver, test_steps);
			// res.selectRoom(driver, roomClassName, isAssign);

			res.click_NewReservation(driver, test_steps);
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			// res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			res.clickOnFindRooms(driver, test_steps);
			res.selectRoom(driver, test_steps, RoomClass, IsAssign, Account);
			depositAmount = res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

			res.clickNext(driver, test_steps);
			res.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber,
					AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
			res.verify_NotesSections(driver, test_steps);
			boolean falg = res.verify_TaskSections(driver, test_steps);
			res.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
			// if (falg) {
			// res.enter_Task(driver, test_steps, IsTask, TaskCategory, TaskType,
			// TaskDetails, TaskRemarks, TaskDueon,
			// TaskAssignee, TaskStatus);
			// }
			res.clickBookNow(driver, test_steps);
			reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
			status = res.get_ReservationStatus(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			roomNumber = res.get_RoomNumber(driver, test_steps, IsAssign);

			folio.folioTab(driver);
			test_steps.add("Clicked Folio Tab");
			app_logs.info("Clicked Folio Tab");

			folioRoomCharges = res.get_RoomChargeWithCurrency(driver, test_steps);
			folioRoomCharges = res.replaceCurrency(folioRoomCharges);

			folioIncidentals = res.get_InceidentalsWithCurrency(driver, test_steps);
			folioIncidentals = res.replaceCurrency(folioIncidentals);

			folioTaxes = res.get_TaxesWithCurrency(driver, test_steps);
			folioTaxes = res.replaceCurrency(folioTaxes);

			folioTripTotal = res.get_TotalChargesWithCurrency(driver, test_steps);
			folioTripTotal = res.replaceCurrency(folioTripTotal);

			folioBalance = res.get_BalanceWithCurrency(driver, test_steps);
			folioBalance = res.replaceCurrency(folioBalance);

			test_steps.add("========== MAKING LINE ITEM STATUS TO VOID ==========");
			app_logs.info("========== MAKING LINE ITEM STATUS TO VOID ==========");

			if (!Incidentals.isEmpty()) {
				if (Incidentals.split(",").length == 1) {
					folio.clickAddLineItemButton(driver);
					folio.AddFolioLineItem(driver, Incidentals, "50");
				} else {
					String[] inc = Incidentals.split(",");
					for (int i = 0; i < inc.length; i++) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, inc[i], "50");
					}
				}
			}

			if (!RoomCharges.isEmpty()) {
				if (RoomCharges.split(",").length == 1) {
					folio.clickAddLineItemButton(driver);
					folio.AddFolioLineItem(driver, RoomCharges, "50");
				} else {
					String[] rc = RoomCharges.split(",");
					for (int i = 0; i < rc.length; i++) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, rc[i], "50");
					}
				}
			}

			if (!Taxes.isEmpty()) {
				if (Taxes.split(",").length == 1) {
					folio.clickAddLineItemButton(driver);
					folio.AddFolioLineItem(driver, Taxes, "50");
				} else {
					String[] tax = Taxes.split(",");
					for (int i = 0; i < tax.length; i++) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, tax[i], "50");
					}
				}
			}

			if (!Fees.isEmpty()) {
				if (Fees.split(",").length == 1) {
					folio.clickAddLineItemButton(driver);
					folio.AddFolioLineItem(driver, Fees, "50");
				} else {
					String[] fee = Fees.split(",");
					for (int i = 0; i < fee.length; i++) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, fee[i], "50");
					}
				}
			}

			test_steps.add("========== UnCheck All CheckBoxes ==========");
			app_logs.info("========== UnCheck All CheckBoxes ==========");

			// folio.clickFolioDetailsCheckBoxes(driver, includeTaxesInLineItemsCheckBox,
			// false);
			// folio.clickFolioDetailsCheckBoxes(driver, displayPendingItemsCheckBox,
			// false);
			// folio.clickFolioDetailsCheckBoxes(driver, includePendingItemsInTotalCheckBox,
			// false);
			// folio.clickFolioDetailsCheckBoxes(driver, displayVoidItemsCheckBox, false);
			// folio.clickFolioDetailsCheckBoxes(driver, displayCCNumberInReportsCheckBox,
			// false);
			// folio.clickFolioDetailsCheckBoxes(driver, showAuthorizationsInReportCheckBox,
			// false);

			// folio.clickFolioDetailsCheckBoxes(driver, "Include Taxes in Line Items",
			// false);
			// folio.clickFolioDetailsCheckBoxes(driver, "Display Pending Items", false);
			// folio.clickFolioDetailsCheckBoxes(driver, "Include Pending Items in Total",
			// false);
			// folio.clickFolioDetailsCheckBoxes(driver, "Display Void Items", false);
			// folio.clickFolioDetailsCheckBoxes(driver, "Display CC# in Reports", false);
			// folio.clickFolioDetailsCheckBoxes(driver, "Show Authorizations in Report",
			// false);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Shift Time in Include Data Form", "Include Data Form",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2ReservationsLedger", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}

}