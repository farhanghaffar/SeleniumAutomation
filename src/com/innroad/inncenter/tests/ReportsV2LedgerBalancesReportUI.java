package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2LedgerBalancesReportUI extends TestCore {
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String reservationNumber = null, roomNumber = null;
	String date = null;
	
	HashMap<String, String> beforeLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> beforeDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> beforeDetailsOfAllLedgerCategories = new HashMap<>();
	
	HashMap<String, String> afterLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> afterDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> afterDetailsOfAllLedgerCategories = new HashMap<>();
	
	

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	Account CreateTA = new Account();
	// ArrayList<String> new ArrayList<>();
	CPReservationPage reservationPage = new CPReservationPage();
	Folio folio = new Folio();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void CP_CreateReservation(String url, String ClientCode, String Username, String Password,

			String Incidentals, String RoomCharges, String Taxes, String Transfers, String PaymentMethod, String Fees,
			String UnitExpenses, String UnitRevenues, String TravelAgentCommission, String DistributionMethod,
			String GiftCertificate, String GiftCertificateRedeemed,

			String CheckInDate, String CheckOutDate,

			String accountName, String accounttype, String marketSegment, String referral, String guestFirstName,
			String guestLastName, String phoneNumber, String alternativePhone, String email, String account,
			String accountType, String address1, String address2, String address3, String city, String country,
			String state, String postalCode,

			String Adults, String Children, String Rateplan, String PromoCode, String RoomClass, String IsAssign,
			String IsDepositOverride, String DepositOverrideAmount, String Salutation, String GuestFirstName,
			String GuestLastName, String PhoneNumber, String AltenativePhone, String Email, String Account,
			String AccountType, String Address1, String Address2, String Address3, String City, String Country,
			String State, String PostalCode, String IsGuesProfile, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String IsChangeInPayAmount, String ChangedAmountValue,
			String MarketSegment, String Referral, String IsAddNotes, String NoteType, String Subject,
			String Description, String IsTask, String TaskCategory, String TaskType, String TaskDetails,
			String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus,

			String accountTypeOptions, String itemStatuOptions, String IncludeDataFromUsers,
			String IncludeDataFromProperties, String IncludeDataFromShiftTimeStartHours,
			String IncludeDataFromShiftTimeStartMinutes, String IncludeDataFromShiftTimeStartAmPm,
			String IncludeDataFromShiftTimeEndHours, String IncludeDataFromShiftTimeEndMinutes,
			String IncludeDataFromShiftTimeEndAmPm, String TaxExemptLedgerItemsOption, String marketSegmentOption,
			String reservationStatusOptions, String referralsOption) throws InterruptedException, IOException {

		test_name = "CP Reservation Creation";
		test_description = "CP Reservation Creation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/667714' target='_blank'>"
				+ "Click here to open TestRail: C667714</a>";
		test_category = "CPReservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Double depositAmount = 0.0;
		Double paidDeposit = 0.0;
		String reservation = null;
		String status = null;

		String TripSummaryRoomCharges = null, TripSummaryTaxes = null, TripSummaryIncidentals = null,
				TripSummaryTripTotal = null, TripSummaryPaid = null, TripSummaryBalance = null;
		String FilioRoomCharges = null, FilioTaxes = null, FilioIncidentals = null, FilioTripTotal = null,
				FilioPaid = null, FilioBalance = null;

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIn, CheckOut and TaskDueOn Date
			if (CheckInDate.equalsIgnoreCase("NA")) {
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

		// login
		Login login = new Login();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			try {
				// login_CP(driver);
				//loginReportsV2(driver);
				//login.login(driver, envURL, "innroad", "manogna", "Innroad@123");
				login.login(driver, envURL, "bmi", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				//loginReportsV2(driver);
				//login.login(driver, "https://app.devinnroad.com", "innroad", "manogna", "Innroad@123");
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


		
		try {
			
			nav.ReportsV2(driver);
			//driver.navigate().to("https://app.devinnroad.com/reports/ledger-balances");
			report.navigateToLedgerBalancesReport(driver);
			Wait.wait3Second();
			//report.selectDateRange(driver, "Month To Date", test_steps);
			//Wait.wait3Second();
			
			//report.validateReportSortFunctionality(driver, test_steps);
			
			
			
			
			Wait.wait30Second();
			
			report.selectAllInputsUsingSeeAll(driver, test_steps);
			
			
			
			report.clickOnRunReport(driver);
			
			app_logs.info("Transacion details list: "+report.getTransactionDetailsList(driver, "Visa", test_steps));
			
			String s = driver.findElement(By.xpath("(//span[@class='StackView_value_3U7p9'])[3]")).getText();
			app_logs.info("SSSSSSSSSSS: "+s);
			
			app_logs.info("Transaction details: "+report.getTransactionDetails(driver, "Room Charge", "16036834", test_steps));
			
			beforeLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);
			
			//beforeDetailsOfGivenCategory = report.getDetailsOfGivenCategory(driver, "Room Charge", test_steps);
			//report.getDetailsOfGivenCategory(driver, "Tax", test_steps);
			beforeDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);
			
			//app_logs.info("Detailed View: "+report.getDetailedViewDetails(driver, test_steps));
			app_logs.info("Detailed View: "+report.getDetailedViewDetailsLatest(driver, test_steps));
			app_logs.info("Detailed View: "+report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));
			
			app_logs.info("Sort details: "+report.getDetailsByGivenSortOption(driver, "Reservation #", test_steps));
			
			
			
			
			
			//RetryFailedTestCases.count = Utility.reset_count;
			//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try {
			driver.close();
			Utility.switchTab(driver, 0);
			app_logs.info("Switched to main tab");
			// Navigation nav = new Navigation();
			nav.Reservation_Backward_3(driver);
			nav.Accounts(driver);
			test_steps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		String accountNumber = null;
		if (accounttype.equals("House Account")) {
			// New account
			try {
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, accounttype);
				CreateTA.AccountDetails(driver, accounttype, accountName);
				accountNumber = Utility.GenerateRandomString15Digit();
				CreateTA.ChangeAccountNumber(driver, accountNumber);

				report.AccountSave(driver, test, accountName, test_steps);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Reservation
			try {

				test_steps.add("========== CREATING RESERVATION ==========");
				app_logs.info("========== CREATING RESERVATION ==========");

				driver.findElement(By.xpath("(//span[text()='Reservations'])[3]")).click();
				// nav.Reservation_Backward_1(driver);

				reservationPage.click_NewReservation(driver, test_steps);
				reservationPage.checkInDate(driver, 0);
				reservationPage.checkOutDate(driver, 1);
				reservationPage.enterAdult(driver, Adults);
				reservationPage.enterChildren(driver, Children);
				reservationPage.selectRateplan(driver, Rateplan, PromoCode, 1);
				reservationPage.clickOnFindRooms(driver, test_steps);
				reservationPage.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				reservationPage.clickNext(driver, test_steps);
				reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				reservationPage.enterGuestName(driver, test_steps, Salutation, guestFirstName, guestLastName);
				reservationPage.enterEmail(driver, test_steps, email);
				reservationPage.select_HouseAccoutAsPayment(driver, test_steps, accountName);
				// reservationPage.enter_TravelAgentMarketSegmentDetails(driver, test_steps,
				// TravelAgent, marketSegment, referral, accountType);

				if ((accountName.equalsIgnoreCase("") || accountName.isEmpty())) {

					reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
							CardExpDate);

				}

				reservationPage.clickBookNow(driver, test_steps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				reservationPage.get_ReservationStatus(driver, test_steps);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
				roomNumber = reservationPage.get_RoomNumber(driver, test_steps, IsAssign);

				reservationPage.closeReservationTab(driver);
				test_steps.add("Close Reservation");
				app_logs.info("Close Reservation");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (accounttype.equals("Gift Certificate")) {
			// New account
			try {
				String AccountNumber = null;
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, accounttype);
				CreateTA.AccountDetails(driver, accounttype, accountName);
				AccountNumber = Utility.GenerateRandomString15Digit();
				CreateTA.ChangeAccountNumber(driver, AccountNumber);

				report.AccountSave(driver, test, accountName, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName, "CorporateAccount",
							driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {

				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName, "CorporateAccount",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Clicking on New Reservation
			try {
				// CreateTA.NewReservationButton(driver, test);

				driver.findElement(By.xpath("(//span[text()='Reservations'])[3]")).click();
				// nav.Reservation_Backward_1(driver);

				reservationPage.click_NewReservation(driver, test_steps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			Account = accountName;
			String bal = null;
			// Reservation
			try {
				test_steps.add("========== Creating reservation ==========");
				app_logs.info("========== Creating reservation ==========");

				reservationPage.checkInDate(driver, 0);
				reservationPage.checkOutDate(driver, 1);
				reservationPage.enterAdult(driver, Adults);
				reservationPage.enterChildren(driver, Children);
				reservationPage.clickOnFindRooms(driver, test_steps);
				report.selectRoom(driver, test_steps, RoomClass, "Yes", "");
				reservationPage.clickNext(driver, test_steps);
				reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				reservationPage.enterGuestName(driver, test_steps, Salutation, guestFirstName, guestLastName);
				reservationPage.selectReferral(driver, referral);
				reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
				reservationPage.clickBookNow(driver, test_steps);
				
				
				
				
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				reservationPage.get_ReservationStatus(driver, test_steps);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
				roomNumber = reservationPage.get_RoomNumber(driver, test_steps, IsAssign);
				// reservationPage.closeReservationTab(driver);
				// test_steps.add("Close Reservation");
				// app_logs.info("Close Reservation");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else {
			try {
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, accounttype);
				CreateTA.AccountDetails(driver, accounttype, accountName);
				accountNumber = Utility.GenerateRandomString15Digit();
				CreateTA.ChangeAccountNumber(driver, accountNumber);

				CreateTA.AccountAttributes(driver, test, marketSegment, referral, test_steps);
				CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
						address1, address2, address3, email, city, state, postalCode, test_steps);
				CreateTA.Billinginfo(driver, test, test_steps);
				report.AccountSave(driver, test, accountName, test_steps);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Clicking on New Reservation
			try {
				CreateTA.NewReservationButton(driver, test);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click new reservation", testName, "NavigateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click new reservation", testName, "NavigateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			////// create corporate account ending

			// Reservation
			try {
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, Adults);
				reservationPage.enter_Children(driver, test_steps, Children);
				reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
				reservationPage.clickOnFindRooms(driver, test_steps);
				report.selectRoom(driver, test_steps, RoomClass, IsAssign, Account);
				depositAmount = report.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

				String s = "//p[contains(text(),'update the reservation to the new policies')]/../following-sibling::div[@class='modal-footer']/div/div/button[text()='Yes']";
				// WebElement e = driver.findElement(By.xpath(s));
				boolean value = Utility.isElementDisplayed(driver, By.xpath(s));
				if (value)
					driver.findElement(By.xpath(s)).click();

				reservationPage.clickNext(driver, test_steps);
				try {
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//button[text()='No'])[11]")));
				} catch (Exception e) {
					Wait.wait1Second();
				}
				reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
						phoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City,
						Country, State, PostalCode, IsGuesProfile);
				if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
					reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
							CardExpDate);
				}
				reservationPage.enter_MarketSegmentDetails(driver, test_steps, account, MarketSegment, Referral);
				reservationPage.verify_NotesSections(driver, test_steps);
				boolean falg = report.verify_TaskSections(driver, test_steps);
				reservationPage.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
				if (falg) {
					reservationPage.enter_Task(driver, test_steps, IsTask, TaskCategory, TaskType, TaskDetails,
							TaskRemarks, TaskDueon, TaskAssignee, TaskStatus);
				}
				reservationPage.clickBookNow(driver, test_steps);
				//reservationPage.pay_DepositAmount(driver, test_steps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				status = reservationPage.get_ReservationStatus(driver, test_steps);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
				roomNumber = reservationPage.get_RoomNumber(driver, test_steps, IsAssign);
				// reservationPage.get_RoomNumber(driver, test_steps, IsAssign);
				TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
				TripSummaryTaxes = reservationPage.get_TripSummaryTaxesWithCurrency(driver, test_steps);
				TripSummaryIncidentals = reservationPage.get_TripSummaryInceidentalsWithCurrency(driver, test_steps);
				TripSummaryTripTotal = reservationPage.get_TripSummaryTripTotalChargesWithCurrency(driver, test_steps);
				TripSummaryPaid = reservationPage.get_TripSummaryPaidWithCurrency(driver, test_steps);
				TripSummaryBalance = reservationPage.get_TripSummaryBalanceWithCurrency(driver, test_steps);
				reservationPage.verify_StayInfo(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children,
						RoomClass, TripSummaryRoomCharges);
				report.validate_GuestInfo(driver, test_steps, Salutation, GuestFirstName, GuestLastName, phoneNumber,
						AltenativePhone, Email, Country, Account, Address1, Address2, Address3, State, City,
						PostalCode);
				reservationPage.get_AssociatedPoliciesToReservation(driver, test_steps);

				reservationPage.click_Folio(driver, test_steps);
				FilioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
				FilioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
				FilioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
				FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
				FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
				FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
				//reservationPage.verify_BannerDetails(driver, test_steps, Salutation, GuestFirstName, GuestLastName, phoneNumber, Email, FilioTripTotal, FilioBalance, reservation, status, CheckInDate, CheckOutDate, Country);
				String payment = FilioPaid;
				FilioPaid = FilioPaid.trim();
				char ch = FilioPaid.charAt(0);
				FilioPaid = FilioPaid.replace("$", "");
				FilioPaid = FilioPaid.trim();
				paidDeposit = Double.parseDouble(FilioPaid);

				if (depositAmount > 0) {
					if (Double.compare(paidDeposit, depositAmount) == 0) {
						test_steps.add("Deposit paid amount is validated : " + ch + " " + paidDeposit);
						app_logs.info("Deposit paid amount is validated : " + ch + " " + paidDeposit);
					}
				}

				try {
					// test_steps.add("========== CLOSE ACCOUNT ==========");
					// app_logs.info("========== CLOSE ACCOUNT ==========");
					// navigation.Accounts(driver);
					// CreateTA.close_Account(driver);

					folio.folioTab(driver);
					test_steps.add("Clicked Folio Tab");
					app_logs.info("Clicked Folio Tab");

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
					

					

				} catch (Exception e) {
					if (Utility.reTry.get(test_name) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to close account", testName, "CloseAccount", driver);
					} else {

						Assert.assertTrue(false);
					}
				} catch (Error e) {
					test_steps.add(e.toString());
				}

				// reservationPage.click_History(driver, test_steps);
				// reservationPage.verify_ReservationInHistoryTab(driver, test_steps,
				// reservation);
				// if (depositAmount > 0) {
				// reservationPage.verifyDepositPaymentInHistoryTab(driver, test_steps,
				// depositAmount);
				// }
				// reservationPage.velidate_TripSummaryAndFolio(driver, test_steps,
				// FilioRoomCharges, FilioTaxes,
				// FilioIncidentals, FilioTripTotal, payment, FilioBalance,
				// TripSummaryRoomCharges,
				// TripSummaryTaxes, TripSummaryIncidentals, TripSummaryTripTotal,
				// TripSummaryPaid,
				// TripSummaryBalance);
				// reservationPage.verify_GuestReportLabelsValidations(driver, test_steps);
				//RetryFailedTestCases.count = Utility.reset_count;
				//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		
		
		
		
		
		
		
		
		
		
		try {
			
			nav.ReportsV2(driver);
			//driver.navigate().to("https://app.devinnroad.com/reports/ledger-balances");
			report.navigateToLedgerBalancesReport(driver);
			Wait.wait3Second();
			//report.selectDateRange(driver, "Month To Date", test_steps);
			//Wait.wait3Second();
			report.clickOnRunReport(driver);
			
			String s = driver.findElement(By.xpath("(//span[@class='StackView_value_3U7p9'])[3]")).getText();
			app_logs.info("SSSSSSSSSSS: "+s);
			
			
			
			afterLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);
			
			//afterDetailsOfGivenCategory = report.getDetailsOfGivenCategory(driver, "Room Charge", test_steps);
			//report.getDetailsOfGivenCategory(driver, "Tax", test_steps);
			afterDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);
			
			
			if (!Incidentals.isEmpty()) {
				
				if (beforeLedgerCategoryDetails.containsKey(Incidentals)) {
					int inc = Integer.parseInt(afterLedgerCategoryDetails.get(Incidentals))-Integer.parseInt(beforeLedgerCategoryDetails.get(Incidentals));
					app_logs.info("Incidentals: "+inc);
				}else {
					int inc = Integer.parseInt(afterLedgerCategoryDetails.get(Incidentals));
					app_logs.info("Incidentals: "+inc);
				}
				
				String[] strInc = Incidentals.split(",");
				
				if(strInc.length == 1) {
					if(beforeDetailsOfAllLedgerCategories.containsKey(Incidentals)) {
						int inc = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(Incidentals))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(Incidentals));
						app_logs.info("Incidentals - "+Incidentals+": "+inc);
					}else {
						int inc = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(Incidentals));
						app_logs.info("Incidentals - "+Incidentals+": "+inc);
					}
				}else {
					for (int i = 0; i < strInc.length; i++) {
						if(beforeDetailsOfAllLedgerCategories.containsKey(strInc[i])) {
							int inc = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strInc[i]))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strInc[i]));
							app_logs.info("Incidentals - "+strInc[i]+": "+inc);
						}else {
							int inc = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strInc[i]));
							app_logs.info("Incidentals - "+strInc[i]+": "+inc);
						}
					}
				}
				
			}
			
			if (!RoomCharges.isEmpty()) {
				
				if (beforeLedgerCategoryDetails.containsKey(RoomCharges)) {
					int rm = Integer.parseInt(afterLedgerCategoryDetails.get(RoomCharges))-Integer.parseInt(beforeLedgerCategoryDetails.get(RoomCharges));
					app_logs.info("Room Charges: "+rm);
				}else {
					int rm = Integer.parseInt(afterLedgerCategoryDetails.get(RoomCharges));
					app_logs.info("Room Charges: "+rm);
				}
				
				String[] strRm = RoomCharges.split(",");
				
				if(strRm.length == 1) {
					if(beforeDetailsOfAllLedgerCategories.containsKey(RoomCharges)) {
						int rm = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(RoomCharges))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(RoomCharges));
						app_logs.info("Room Charges - "+RoomCharges+": "+rm);
					}else {
						int rm = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(RoomCharges));
						app_logs.info("Room Charges - "+RoomCharges+": "+rm);
					}
				}else {
					for (int i = 0; i < strRm.length; i++) {
						if(beforeDetailsOfAllLedgerCategories.containsKey(strRm[i])) {
							int rm = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strRm[i]))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strRm[i]));
							app_logs.info("Room CHarges - "+strRm[i]+": "+rm);
						}else {
							int rm = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strRm[i]));
							app_logs.info("Room Charges - "+strRm[i]+": "+rm);
						}
					}
				}
				
			}
			
			if (!Taxes.isEmpty()) {
				
				if (beforeLedgerCategoryDetails.containsKey(Taxes)) {
					int tax = Integer.parseInt(afterLedgerCategoryDetails.get(Taxes))-Integer.parseInt(beforeLedgerCategoryDetails.get(Taxes));
					app_logs.info("Taxes : "+tax);
				}else {
					int tax = Integer.parseInt(afterLedgerCategoryDetails.get(Taxes));  //need to add more code
					app_logs.info("Taxes : "+tax);
				}
				
				String[] strTax = Taxes.split(",");
				
				if(strTax.length == 1) {
					if(beforeDetailsOfAllLedgerCategories.containsKey(RoomCharges)) {
						int tax = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(RoomCharges))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(RoomCharges));
						app_logs.info("Taxes - "+RoomCharges+": "+tax);
					}else {
						int tax = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(RoomCharges));
						app_logs.info("Taxes - "+RoomCharges+": "+tax);
					}
				}else {
					for (int i = 0; i < strTax.length; i++) {
						if(beforeDetailsOfAllLedgerCategories.containsKey(strTax[i])) {
							int tax = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strTax[i]))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strTax[i]));
							app_logs.info("Taxes - "+strTax[i]+": "+tax);
						}else {
							int tax = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strTax[i]));
							app_logs.info("Taxes - "+strTax[i]+": "+tax);
						}
					}
				}
				
			}
			
			if (!Fees.isEmpty()) {
				
				if (beforeLedgerCategoryDetails.containsKey(Fees)) {
					int fee = Integer.parseInt(afterLedgerCategoryDetails.get(Fees))-Integer.parseInt(beforeLedgerCategoryDetails.get(Fees));
					app_logs.info("Taxes : "+fee);
				}else {
					int fee = Integer.parseInt(afterLedgerCategoryDetails.get(Fees));  //need to add more code
					app_logs.info("Taxes : "+fee);
				}
				
				String[] strFee = Fees.split(",");
				
				if(strFee.length == 1) {
					if(beforeDetailsOfAllLedgerCategories.containsKey(Fees)) {
						int fee = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(Fees))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(Fees));
						app_logs.info("Fees - "+Fees+": "+fee);
					}else {
						int fee = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(Fees));
						app_logs.info("Fees - "+Fees+": "+fee);
					}
				}else {
					for (int i = 0; i < strFee.length; i++) {
						if(beforeDetailsOfAllLedgerCategories.containsKey(strFee[i])) {
							int fee = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strFee[i]))-Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strFee[i]));
							app_logs.info("Fees - "+strFee[i]+": "+fee);
						}else {
							int fee = Integer.parseInt(beforeDetailsOfAllLedgerCategories.get(strFee[i]));
							app_logs.info("Fees - "+strFee[i]+": "+fee);
						}
					}
				}
				
			}
			
			
			
			
			
			
			
			//app_logs.info("Detailed View: "+report.getDetailedViewDetails(driver, test_steps));
			//app_logs.info("Detailed View: "+report.getDetailedViewDetailsLatest(driver, test_steps));
			app_logs.info("Detailed View: "+report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2LedgerBalancesReportUI", excel);
		// CP_CreateReservation
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}

}
