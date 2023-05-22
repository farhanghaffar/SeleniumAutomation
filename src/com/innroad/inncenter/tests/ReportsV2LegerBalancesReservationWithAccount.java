package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;

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
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2LegerBalancesReservationWithAccount extends TestCore {
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
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> getRoomClasses = new ArrayList<>();

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	Account CreateTA = new Account();
	CPReservationPage reservationPage = new CPReservationPage();
	Folio folio = new Folio();
	Groups group = new Groups();
	LedgerAccount la = new LedgerAccount();
	ReservationSearch reservationSearch = new ReservationSearch();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void CP_CreateReservation(String url, String ClientCode, String Username, String Password,

			String Incidentals, String AmountIncidentals, String RoomCharges, String AmountRoomCharges, String Taxes,
			String AmountTaxes, String Transfers, String PaymentMethod, String Fees, String AmountFees,
			String UnitExpenses, String UnitRevenues, String TravelAgentCommission, String DistributionMethod,
			String GiftCertificate, String GiftCertificateRedeemed,

			String accountName, String accountType, String account, String marketSegment, String referral, String guestFirstName,
			String guestLastName, String phoneNumber, String alternativePhone, String email,
			String address1, String address2, String address3, String city, String country,
			String state, String postalCode,

			String CheckInDate, String CheckOutDate, String Adults, String Children, String Rateplan, String PromoCode, String IsSplitRes, String RoomClass, String IsAssign,
			String IsDepositOverride, String DepositOverrideAmount, String Salutation, String GuestFirstName,
			String GuestLastName, String MrbGuestFirstName,
			String MrbGuestLastName, String PhoneNumber, String AltenativePhone, String Email, 
			String Address1, String Address2, String Address3, String City, String Country,
			String State, String PostalCode, String IsGuesProfile, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String IsChangeInPayAmount, String ChangedAmountValue, String TravelAgent,
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
		guestFirstName = guestFirstName + Utility.generateRandomString();
		guestLastName = guestLastName + Utility.generateRandomStringWithGivenLength(4);
		GuestFirstName = guestFirstName;
		GuestLastName = guestLastName;
		accountName = accountName + Utility.generateRandomString();

		String TripSummaryRoomCharges = null, TripSummaryTaxes = null, TripSummaryIncidentals = null,
				TripSummaryTripTotal = null, TripSummaryPaid = null, TripSummaryBalance = null;
		String FilioRoomCharges = null, FilioTaxes = null, FilioIncidentals = null, FilioTripTotal = null,
				FilioPaid = null, FilioBalance = null;
		String resNumberPayment = null;
		String accountNumber = null;
		ArrayList<String> unitOwnerItems = new ArrayList<>();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIn, CheckOut and TaskDueOn Date
			if (CheckInDate.equalsIgnoreCase("NA")) {
				CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
				CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");

			}
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
				loginReportsV2(driver);
				// login.login(driver, envURL, "bmi", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginReportsV2(driver);
				// login.login(driver, envURL, "bmi", "autouser", "Auto@123");
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

		


		
		
		
		// Reservation
		try {
			//String tempRatePlan = ratePlanName + "|" + ratePlanName;
			//String tempRoomClassName = roomClassName + "|" + roomClassName;
			
			//nav.Reservation_Backward_3(driver);
			//test_steps.add("Naviagte to reservations");
			//app_logs.info("Naviagte to reservations");
			

			reservationPage.click_NewReservation(driver, test_steps);

			
			reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode, IsSplitRes);

			
			if(IsSplitRes.equalsIgnoreCase("Yes")) {

				reservationPage.enter_Adults(driver, test_steps, Adults);				
				reservationPage.enter_Children(driver, test_steps, Children);
				reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
				
			}
			
			reservationPage.clickOnFindRooms(driver, test_steps);

			roomCost=reservationPage.select_MRBRooms(driver, test_steps, RoomClass, IsAssign, account);
			//RoomCharges = reservationPage.select_MRBRooms(driver, test_steps, RoomClass, IsAssign, account);
		
			reservationPage.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
			
			reservationPage.clickNext(driver, test_steps);
			
			//reservationPage.enter_MRB_MailingAddress(driver, test_steps, Salutation, guestFirstName, guestLastName, phoneNumber, alternativePhone, email, account, accountType, address1, address2, address3, city, country, state, postalCode, isGuesProfile, isAddMoreGuestInfo, isSplitRes,getRoomClasses);
			reservationPage.enter_MRB_MailingAddress(driver, test_steps, Salutation, MrbGuestFirstName, MrbGuestLastName, PhoneNumber, AltenativePhone, Email, account, accountType, address1, address2, address3, city, country, state, postalCode, "No", "No", IsSplitRes, getRoomClasses);
			
			if((account.equalsIgnoreCase("")||account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
				
			}
			//System.out.println(getRoomClasses);
			
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, marketSegment, referral);
			
			reservationPage.clickBookNow(driver, test_steps);
			
			reservationNumber=reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			
			status=reservationPage.get_ReservationStatus(driver, test_steps);
			
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			
			reservationPage.close_FirstOpenedReservation(driver, test_steps);
			
			reservationSearch.basicSearch_WithReservationNumber(driver, reservationNumber);
			
			reservationPage.verify_MR_ToolTip(driver, test_steps, reservationNumber);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// If payment method is Reservation - Creating a new Reservation
		if (PaymentMethod.equalsIgnoreCase("Reservation") || Transfers.equalsIgnoreCase("Reservation")) {

			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, Adults);
			reservationPage.enter_Children(driver, test_steps, Children);
			// res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			reservationPage.clickOnFindRooms(driver, test_steps);
			reservationPage.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
			depositAmount = reservationPage.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, AltenativePhone, Email, account, accountType, Address1, Address2, Address3, City,
					Country, State, PostalCode, IsGuesProfile);
			if ((account.equalsIgnoreCase("") || account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, "MC", CardNumber, NameOnCard, CardExpDate);
			}
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
			reservationPage.verify_NotesSections(driver, test_steps);
			boolean falg = reservationPage.verify_TaskSections(driver, test_steps);
			reservationPage.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
			// if (falg) {
			// res.enter_Task(driver, test_steps, IsTask, TaskCategory, TaskType,
			// TaskDetails, TaskRemarks, TaskDueon,
			// TaskAssignee, TaskStatus);
			// }
			reservationPage.clickBookNow(driver, test_steps);
			try {
				resNumberPayment = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				app_logs.info("Reservation Number for Payment: " + resNumberPayment);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			// Navigation nav = new Navigation();
			//nav.Accounts(driver);
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

		if (accountType.equals("House Account")) {
			// New account
			try {
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, accountType);
				CreateTA.AccountDetails(driver, accountType, accountName);
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
				app_logs.info("Reservation Number: " + reservationNumber);
				test_steps.add("Reservation Number: " + reservationNumber);
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

		else if (accountType.equals("Gift Certificate")) {
			// New account
			try {
				String AccountNumber = null;
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, accountType);
				CreateTA.AccountDetails(driver, accountType, accountName);
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

			//account = accountName;
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
				app_logs.info("Reservation Number: " + reservationNumber);
				test_steps.add("Reservation Number: " + reservationNumber);
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
		}else if(accountType.equals("Group")) {
			nav.groups(driver);
			//group.click_NewAccount(driver, test, test_steps);
			group.type_GroupName(driver, test, accountName, test_steps);
			group.type_AccountAttributes(driver, test, marketSegmentOption, referralsOption, test_steps);
			group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, PhoneNumber, address1, City, State, Country, PostalCode, test_steps);
			group.Billinginfo(driver);
			group.Save(driver, test_steps);
			group.click_GroupNewReservation(driver, test_steps);
			
			
			try {
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, Adults);
			reservationPage.enter_Children(driver, test_steps, Children);
			reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			reservationPage.clickOnFindRooms(driver, test_steps);
			report.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
			depositAmount = report.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

			String s = "//p[contains(text(),'update the reservation to the new policies')]/../following-sibling::div[@class='modal-footer']/div/div/button[text()='Yes']";
			// WebElement e = driver.findElement(By.xpath(s));
			boolean value = Utility.isElementDisplayed(driver, By.xpath(s));
			if (value)
				driver.findElement(By.xpath(s)).click();

			reservationPage.clickNext(driver, test_steps);
			try {
				Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//button[text()='No'])[15]")));
			} catch (Exception e) {
				Wait.wait1Second();
			}
			reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					phoneNumber, AltenativePhone, Email, account, accountType, Address1, Address2, Address3, City,
					Country, State, PostalCode, IsGuesProfile);
			if ((account.equalsIgnoreCase("") || account.isEmpty())) {

				if (PaymentMethod.equalsIgnoreCase("Reservation")) {
					reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
				} else if (PaymentMethod.equalsIgnoreCase("MC") || PaymentMethod.equalsIgnoreCase("Visa")
						|| PaymentMethod.equalsIgnoreCase("Amex") || PaymentMethod.equalsIgnoreCase("Discover")) {

					reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
							CardExpDate);
				} else {
					reservationPage.clickonPaymentMetod(driver, test_steps);
					reservationPage.selectPaymentMethod(driver, PaymentMethod, test_steps);
				}

				// reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType,
				// CardNumber, NameOnCard, CardExpDate);
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
			// reservationPage.pay_DepositAmount(driver, test_steps);
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			app_logs.info("Reservation Number: " + reservationNumber);
			test_steps.add("<b>Reservation Number: " + reservationNumber);
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
					AltenativePhone, Email, Country, account, Address1, Address2, Address3, State, City,
					PostalCode);
			reservationPage.get_AssociatedPoliciesToReservation(driver, test_steps);

			reservationPage.click_Folio(driver, test_steps);
			FilioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
			FilioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
			FilioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
			FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
			FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
			FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
			// reservationPage.verify_BannerDetails(driver, test_steps, Salutation,
			// GuestFirstName, GuestLastName, phoneNumber, Email, FilioTripTotal,
			// FilioBalance, reservation, status, CheckInDate, CheckOutDate, Country);
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

			
			
			
			
		}else if(accountType.equals("Travel Agent")) {
			ArrayList<String> TravelAgentItems = new ArrayList<>();
			int TravelAgetItemValue;
			
			nav.Accounts(driver);
			
			if (!TravelAgentCommission.isEmpty()) {
				
				CreateTA.ClickTravelAgentItem(driver);
				
				if (CreateTA.chekcTravelAgentItemAvailability(driver, test_steps)) {
					TravelAgentItems = CreateTA.getAssociatedTravelAgentItems(driver, test_steps);
					TravelAgetItemValue = CreateTA.getTravelAgentCommissionValue(driver, test_steps);
					
				}else {
					CreateTA.CreateNewTravelAgentItem(driver, "ItemName", "DisplayName", "Description", "10", "Category", "SelectTax");
				}
			}
			
			try {
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				//accountName = accountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, accountType);
				CreateTA.AccountDetails(driver, accountType, accountName);
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

			// Reservation
			try {
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, Adults);
				reservationPage.enter_Children(driver, test_steps, Children);
				reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
				reservationPage.clickOnFindRooms(driver, test_steps);
				report.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
				depositAmount = report.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

				String s = "//p[contains(text(),'update the reservation to the new policies')]/../following-sibling::div[@class='modal-footer']/div/div/button[text()='Yes']";
				// WebElement e = driver.findElement(By.xpath(s));
				boolean value = Utility.isElementDisplayed(driver, By.xpath(s));
				if (value)
					driver.findElement(By.xpath(s)).click();

				reservationPage.clickNext(driver, test_steps);
				try {
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//button[text()='No'])[15]")));
				} catch (Exception e) {
					Wait.wait1Second();
				}
				reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
						phoneNumber, AltenativePhone, Email, account, accountType, Address1, Address2, Address3, City,
						Country, State, PostalCode, IsGuesProfile);
				if ((account.equalsIgnoreCase("") || account.isEmpty())) {

					if (PaymentMethod.equalsIgnoreCase("Reservation")) {
						reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
					} else if (PaymentMethod.equalsIgnoreCase("MC") || PaymentMethod.equalsIgnoreCase("Visa")
							|| PaymentMethod.equalsIgnoreCase("Amex") || PaymentMethod.equalsIgnoreCase("Discover")) {

						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					} else {
						reservationPage.clickonPaymentMetod(driver, test_steps);
						reservationPage.selectPaymentMethod(driver, PaymentMethod, test_steps);
					}

					// reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType,
					// CardNumber, NameOnCard, CardExpDate);
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
				// reservationPage.pay_DepositAmount(driver, test_steps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				app_logs.info("Reservation Number: " + reservationNumber);
				test_steps.add("<b>Reservation Number: " + reservationNumber);
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
						AltenativePhone, Email, Country, account, Address1, Address2, Address3, State, City,
						PostalCode);
				reservationPage.get_AssociatedPoliciesToReservation(driver, test_steps);

				reservationPage.click_Folio(driver, test_steps);
				FilioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
				FilioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
				FilioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
				FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
				FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
				FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
				// reservationPage.verify_BannerDetails(driver, test_steps, Salutation,
				// GuestFirstName, GuestLastName, phoneNumber, Email, FilioTripTotal,
				// FilioBalance, reservation, status, CheckInDate, CheckOutDate, Country);
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
		
		else if(accountType.equals("Unit Owners")) {
			
			// If Distribution method is given in Select inputs
			if (!DistributionMethod.isEmpty()) {

				nav.setup(driver);
				nav.LedgerAccounts(driver);

				la.NewAccountbutton(driver);
				String acName = "Unit"+Utility.fourDigitgenerateRandomString();
				
				la.LedgerAccountDetails(driver, acName, "Test", "", "Unit Expenses", "Active");
				la.SaveLedgerAccount(driver);
				
				
				
				
				nav.Accounts(driver);
				nav.UnitownerAccount(driver);

				unitOwnerItems = CreateTA.getAssociatedUnitOwnerItemsListAndValue(driver, acName, "",test_steps);
				
			}
				
				
				
				try {
					test_steps.add("========== Creating account ==========");
					app_logs.info("========== Creating account ==========");
					accountName = accountName + Utility.generateRandomString();
					nav.Accounts(driver);
					CreateTA.ClickNewAccountbutton(driver, accountType);
					CreateTA.AccountDetails(driver, accountType, accountName);
					accountNumber = Utility.GenerateRandomString15Digit();
					CreateTA.ChangeAccountNumber(driver, accountNumber);

					CreateTA.AccountAttributes(driver, test, marketSegment, referral, test_steps);
					CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
							address1, address2, address3, email, city, state, postalCode, test_steps);
					CreateTA.Billinginfo(driver, test, test_steps);
					CreateTA.associateRooms(driver, test_steps, RoomClass);
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

				// Reservation
				try {
					CreateTA.NewReservationButton(driver, test);

					reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
					reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
					reservationPage.enter_Adults(driver, test_steps, Adults);
					reservationPage.enter_Children(driver, test_steps, Children);
					reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
					reservationPage.clickOnFindRooms(driver, test_steps);
					report.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
					depositAmount = report.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

					String s = "//p[contains(text(),'update the reservation to the new policies')]/../following-sibling::div[@class='modal-footer']/div/div/button[text()='Yes']";
					// WebElement e = driver.findElement(By.xpath(s));
					boolean value = Utility.isElementDisplayed(driver, By.xpath(s));
					if (value)
						driver.findElement(By.xpath(s)).click();

					reservationPage.clickNext(driver, test_steps);
					try {
						Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//button[text()='No'])[15]")));
					} catch (Exception e) {
						Wait.wait1Second();
					}
					reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
							phoneNumber, AltenativePhone, Email, account, accountType, Address1, Address2, Address3, City,
							Country, State, PostalCode, IsGuesProfile);
					if ((account.equalsIgnoreCase("") || account.isEmpty())) {

						if (PaymentMethod.equalsIgnoreCase("Reservation")) {
							reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
						} else if (PaymentMethod.equalsIgnoreCase("MC") || PaymentMethod.equalsIgnoreCase("Visa")
								|| PaymentMethod.equalsIgnoreCase("Amex") || PaymentMethod.equalsIgnoreCase("Discover")) {

							reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
									CardExpDate);
						} else {
							reservationPage.clickonPaymentMetod(driver, test_steps);
							reservationPage.selectPaymentMethod(driver, PaymentMethod, test_steps);
						}

						// reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType,
						// CardNumber, NameOnCard, CardExpDate);
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
					// reservationPage.pay_DepositAmount(driver, test_steps);
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					app_logs.info("Reservation Number: " + reservationNumber);
					test_steps.add("<b>Reservation Number: " + reservationNumber);
					status = reservationPage.get_ReservationStatus(driver, test_steps);
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					roomNumber = reservationPage.get_RoomNumber(driver, test_steps, IsAssign);

					reservationPage.click_Folio(driver, test_steps);

					folio.clickAddLineItemButton(driver);
					folio.AddFolioLineItem(driver, unitOwnerItems.get(1), "50");

					folio.makePayment(driver, PaymentMethod);

					reservationPage.checkinReservation(driver, test_steps);
					reservationPage.checkoutReservation(driver, test_steps);
					
					if (!DistributionMethod.isEmpty()) {

					nav.Accounts(driver);

					CreateTA.searchForAnAccountAndOpen(driver, unitOwnerItems, accountName, accountType);

					CreateTA.ClickFolio(driver);
					// CreateTA.SelectPeriod(driver, CheckOutDate);
					CreateTA.Accounts_AccountStatement(driver, test_steps);
					CreateTA.SelectPeriod(driver, CheckOutDate);
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

			
			
		}
		else if(accountType.equals("Reservations")) {
			
			try {
				reservationPage.click_NewReservation(driver, test_steps);
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, Adults);
				reservationPage.enter_Children(driver, test_steps, Children);
				reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
				reservationPage.clickOnFindRooms(driver, test_steps);
				report.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
				depositAmount = report.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

				String s = "//p[contains(text(),'update the reservation to the new policies')]/../following-sibling::div[@class='modal-footer']/div/div/button[text()='Yes']";
				// WebElement e = driver.findElement(By.xpath(s));
				boolean value = Utility.isElementDisplayed(driver, By.xpath(s));
				if (value)
					driver.findElement(By.xpath(s)).click();

				reservationPage.clickNext(driver, test_steps);
				try {
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//button[text()='No'])[15]")));
				} catch (Exception e) {
					Wait.wait1Second();
				}
				reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
						phoneNumber, AltenativePhone, Email, account, accountType, Address1, Address2, Address3, City,
						Country, State, PostalCode, IsGuesProfile);
				if ((account.equalsIgnoreCase("") || account.isEmpty())) {

					if (PaymentMethod.equalsIgnoreCase("Reservation")) {
						reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
					} else if (PaymentMethod.equalsIgnoreCase("MC") || PaymentMethod.equalsIgnoreCase("Visa")
							|| PaymentMethod.equalsIgnoreCase("Amex") || PaymentMethod.equalsIgnoreCase("Discover")) {

						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					} else {
						reservationPage.clickonPaymentMetod(driver, test_steps);
						reservationPage.selectPaymentMethod(driver, PaymentMethod, test_steps);
					}

					// reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType,
					// CardNumber, NameOnCard, CardExpDate);
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
				// reservationPage.pay_DepositAmount(driver, test_steps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				app_logs.info("Reservation Number: " + reservationNumber);
				test_steps.add("<b>Reservation Number: " + reservationNumber);
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
						AltenativePhone, Email, Country, account, Address1, Address2, Address3, State, City,
						PostalCode);
				reservationPage.get_AssociatedPoliciesToReservation(driver, test_steps);

				reservationPage.click_Folio(driver, test_steps);
				FilioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
				FilioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
				FilioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
				FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
				FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
				FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
				// reservationPage.verify_BannerDetails(driver, test_steps, Salutation,
				// GuestFirstName, GuestLastName, phoneNumber, Email, FilioTripTotal,
				// FilioBalance, reservation, status, CheckInDate, CheckOutDate, Country);
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
			}catch (Exception e) {

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
		}
		
		
		else {
			try {
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				//accountName = accountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, accountType);
				CreateTA.AccountDetails(driver, accountType, accountName);
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
				report.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
				depositAmount = report.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

				String s = "//p[contains(text(),'update the reservation to the new policies')]/../following-sibling::div[@class='modal-footer']/div/div/button[text()='Yes']";
				// WebElement e = driver.findElement(By.xpath(s));
				boolean value = Utility.isElementDisplayed(driver, By.xpath(s));
				if (value)
					driver.findElement(By.xpath(s)).click();

				reservationPage.clickNext(driver, test_steps);
				try {
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//button[text()='No'])[15]")));
				} catch (Exception e) {
					Wait.wait1Second();
				}
				reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
						phoneNumber, AltenativePhone, Email, account, accountType, Address1, Address2, Address3, City,
						Country, State, PostalCode, IsGuesProfile);
				if ((account.equalsIgnoreCase("") || account.isEmpty())) {

					if (PaymentMethod.equalsIgnoreCase("Reservation")) {
						reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
					} else if (PaymentMethod.equalsIgnoreCase("MC") || PaymentMethod.equalsIgnoreCase("Visa")
							|| PaymentMethod.equalsIgnoreCase("Amex") || PaymentMethod.equalsIgnoreCase("Discover")) {

						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					} else {
						reservationPage.clickonPaymentMetod(driver, test_steps);
						reservationPage.selectPaymentMethod(driver, PaymentMethod, test_steps);
					}

					// reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType,
					// CardNumber, NameOnCard, CardExpDate);
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
				// reservationPage.pay_DepositAmount(driver, test_steps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				app_logs.info("Reservation Number: " + reservationNumber);
				test_steps.add("<b>Reservation Number: " + reservationNumber);
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
						AltenativePhone, Email, Country, account, Address1, Address2, Address3, State, City,
						PostalCode);
				reservationPage.get_AssociatedPoliciesToReservation(driver, test_steps);

				reservationPage.click_Folio(driver, test_steps);
				FilioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
				FilioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
				FilioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
				FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
				FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
				FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
				// reservationPage.verify_BannerDetails(driver, test_steps, Salutation,
				// GuestFirstName, GuestLastName, phoneNumber, Email, FilioTripTotal,
				// FilioBalance, reservation, status, CheckInDate, CheckOutDate, Country);
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
							folio.AddFolioLineItem(driver, Incidentals, AmountIncidentals);
						} else {
							String[] inc = Incidentals.split(",");
							for (int i = 0; i < inc.length; i++) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, inc[i], AmountIncidentals);
							}
						}
					}

					if (!RoomCharges.isEmpty()) {
						if (RoomCharges.split(",").length == 1) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, RoomCharges, AmountRoomCharges);
						} else {
							String[] rc = RoomCharges.split(",");
							for (int i = 0; i < rc.length; i++) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, rc[i], AmountRoomCharges);
							}
						}
					}

					if (!Taxes.isEmpty()) {
						if (Taxes.split(",").length == 1) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, Taxes, AmountTaxes);
						} else {
							String[] tax = Taxes.split(",");
							for (int i = 0; i < tax.length; i++) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, tax[i], AmountTaxes);
							}
						}
					}

					if (!Fees.isEmpty()) {
						if (Fees.split(",").length == 1) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, Fees, AmountFees);
						} else {
							String[] fee = Fees.split(",");
							for (int i = 0; i < fee.length; i++) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, fee[i], AmountFees);
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

				// to change the Reservation status
				try {

					if (reservationStatusOptions.split(",").length == 1) {
						// driver.findElement(By.xpath("//ul[@class='nav_des']//span[text()='Reservations']")).click();
						// driver.findElement(By.xpath("(//input[@id='txtResNum'])[1]")).sendKeys(reservationNumber);
						// driver.findElement(By.xpath("(//div[@class='input-group-btn
						// ng-searchSubmit'])[1]")).click();
						// driver.findElement(By.xpath("(//span[text()='"+reservationNumber+"'])[1]/../preceding-sibling::td[2]/div/a")).click();
						driver.findElement(By.xpath("//i[@data-toggle='dropdown']")).click();
						switch (reservationStatusOptions) {
						case "Confirmed":
							driver.findElement(By.xpath(
									"//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='Confirmed']"))
									.click();
							Wait.wait5Second();
							String s1 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
							if (s1.equals("CONFIRMED")) {
								app_logs.info("Successfully changed Reservation status to confirmed");
							} else {
								app_logs.info("Unable to change Reservation status to confirmed");
							}
							break;
						case "Guaranteed":
							driver.findElement(By.xpath(
									"//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='Guaranteed']"))
									.click();
							Wait.wait5Second();
							String s2 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
							if (s2.equals("GUARANTEED")) {
								app_logs.info("Successfully changed Reservation status to Guaranteed");
							} else {
								app_logs.info("Unable to change Reservation status to Guaranteed");
							}
							break;
						case "On Hold":
							driver.findElement(By
									.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='On Hold']"))
									.click();
							Wait.wait5Second();
							String s3 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
							if (s3.equals("ON HOLD")) {
								app_logs.info("Successfully changed Reservation status to On Hold");
							} else {
								app_logs.info("Unable to change Reservation status to On Hold");
							}
							break;
						case "Cancelled":
							driver.findElement(
									By.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='Cancel']"))
									.click();
							driver.findElement(
									By.xpath("//label[text()='ADD CANCELLATION REASON']/following-sibling::textarea"))
									.sendKeys("Cancel");
							driver.findElement(By.xpath("//button[text()='PROCEED TO CANCELLATION PAYMENT']")).click();
							driver.findElement(By.xpath("//button[contains(text(),'Log ')]")).click();
							driver.findElement(By.xpath("//div[@class='col-md-12 text-right']/button[text()='CLOSE']"))
									.click();
							Wait.wait5Second();
							String s4 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
							if (s4.equals("CANCELLED")) {
								app_logs.info("Successfully changed Reservation status to Cancelled");
							} else {
								app_logs.info("Unable to change Reservation status to Cancelled");
							}
							break;
						case "No Show":
							driver.findElement(By
									.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='No Show']"))
									.click();
							driver.findElement(By.xpath("//button[text()='CONFIRM NO SHOW']")).click();
							driver.findElement(
									By.xpath("//button[@data-bind='visible:noShow.isFinalStep' and text()='Close']"))
									.click();
							Wait.wait5Second();
							String s5 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
							if (s5.equals("NO SHOW")) {
								app_logs.info("Successfully changed Reservation status to No Show");
							} else {
								app_logs.info("Unable to change Reservation status to No Show");
							}
							break;
						}
					} else {
						System.out.println("Enter only one option for Reservation status");
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
				// RetryFailedTestCases.count = Utility.reset_count;
				// Utility.AddTest_IntoReport(testName, test_description, test_category,
				// test_steps);
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

		// Select Advanced inputs
		try {
			/*
			 * nav.ReportsV2(driver); report.navigateToLedgerBalancesReport(driver);
			 * 
			 * Utility.switchTab(driver, (new
			 * ArrayList<String>(driver.getWindowHandles()).size()) - 1);
			 * 
			 * report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
			 * accountTypeOptions); report.selectItemStatusOptionsGiventhroughExcel(driver,
			 * test_steps, itemStatuOptions);
			 * report.selectIncludeDataFromOptionsGiventhroughExcel(driver, test_steps,
			 * IncludeDataFromUsers, IncludeDataFromProperties,
			 * IncludeDataFromShiftTimeStartHours, IncludeDataFromShiftTimeStartMinutes,
			 * IncludeDataFromShiftTimeStartAmPm, IncludeDataFromShiftTimeEndHours,
			 * IncludeDataFromShiftTimeEndMinutes, IncludeDataFromShiftTimeEndAmPm);
			 * report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps,
			 * TaxExemptLedgerItemsOption);
			 * report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps,
			 * marketSegmentOption);
			 * report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps,
			 * reservationStatusOptions);
			 * report.selectReferralsOptionGiventhroughExcel(driver, test_steps,
			 * referralsOption);
			 * 
			 * // click on Run Report report.clickOnRunReport(driver);
			 * 
			 * driver.close(); Utility.switchTab(driver, (new
			 * ArrayList<String>(driver.getWindowHandles()).size()) - 1);
			 */

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

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
		return Utility.getData("ReportsV2ReservationWithAccount", excel);
		// CP_CreateReservation
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}

}
