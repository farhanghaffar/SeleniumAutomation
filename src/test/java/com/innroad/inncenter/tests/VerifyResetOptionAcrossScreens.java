package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.OverView;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_GuestHistory;
import com.innroad.inncenter.webelements.Elements_Roles;
import com.innroad.inncenter.webelements.Elements_Users;
import com.innroad.inncenter.webelements.WebElements_Create_Seasons;

public class VerifyResetOptionAcrossScreens extends TestCore{

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	RoomClass roomClass = new RoomClass();
	public static String roomClassName;
	public static String roomClassAbbrivation;
	
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	List<LocalDate> datesList = new ArrayList<LocalDate>();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	List<String> date = new ArrayList<String>();
	List<String> day = new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	CPReservationPage res = new CPReservationPage();

	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Account accountPage=new Account();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void noPaymentMethodAssociateWithReservation(String testCaseID,String resType, String ratePlan, 
			String roomClassName, String PaymentType, String CardNumber, String NameOnCard, String CardExpDate,
			String CheckInDate,String CheckOutDate,String Adult,String Children,String Salutation,String GuestFirstName,
			String GuestLastName,String Email,String Referral) throws ParseException, InterruptedException {
		test_name = "NoPaymentMethodAssociateWithReservation";
		test_description = "NoPaymentMethodAssociateWithReservation"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824576' target='_blank'>"
				+ "Click here to open TestRail: C824576</a><br>";
		Navigation navigation = new Navigation();
		CPReservationPage reservationPage = new CPReservationPage();
		Properties prop = new Properties();
		ReservationHomePage homePage = new ReservationHomePage();
		ReservationSearch reservationSearch =new ReservationSearch();	
		Folio folio = new Folio();
		GuestHistory guestHistory = new GuestHistory();
		LedgerAccount ledgerAccount = new LedgerAccount();
		Account CreateTA = new Account();
		Groups group = new Groups();
		String testName = null;
		
		String randomString = Utility.GenerateRandomString();
		String salutation = "Mr."; 
		String guestFirstName = "VerifyRes" + randomString; 
		String guestLastName = "Realization" + randomString;
		String paymentTypeMethod = "MC"; 
		String marketSegment = "Internet"; 
		String referral = "Other";
		String guaranteedStatus = "Guaranteed";
		
		String nameOnCard = guestFirstName; 
		String cardExpDate = "12/23";
		String billingNotes = "adding primary card";
		String accountType = "";
		String billingSalutation = "Lord.";
		String country = "United States";
		String accountNo  = "";
		String phoneNumber = "1234567890";
		String alternativeNumber  = phoneNumber;
		String address  = "Address123";
		String email = "innroadautomation@innroad.com";
		String city = "New york";
		String state = "Alaska";
		String postalcode  = "12345";
		String guestName = guestFirstName + " " + guestLastName;
		String account = "CorporateAccount" + Utility.generateRandomString(); 
		String accountNumber = Utility.GenerateRandomString15Digit();
		String address1 = "test1"; 
		String address2 = "test2";
		String address3 = "test3"; 
		String postalCode = "12345"; 
		String alternativePhone = "8790321567";	
		String groupReferral = "Walk In"; 
		String groupFirstName = "Bluebook" + randomString; String groupLastName = "Group" + randomString;
		String accountName = groupFirstName + groupLastName;
		String invalidCard = "5151515151515151";
		
		ArrayList<String> roomNos = new ArrayList<String>();
		String reservation = null;
		
		if(!Utility.validateString(testCaseID)) {
			caseId.add("824576");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		test_catagory = "Verification";
		testName = test_name;
		
		
		String timeZone = "US/Eastren";
		String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		String checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);			
		String maxAdult = "1";
		String maxPerson = "0";
		String property = "Reports Property";
//		checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
//		checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy"));
//		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "MM/dd/yyyy"));
//		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "MM/dd/yyyy"));
		
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
			login_Autoota(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
	
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
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

			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					if (!resType.equalsIgnoreCase("Split")) {
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
			if (resType.equalsIgnoreCase("MRB") || resType.equalsIgnoreCase("Split")) {
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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
//
		Elements_Accounts accountEle = new Elements_Accounts(driver);
		String reservationNumber = "";
		Elements_CPReservation resElement = new Elements_CPReservation(driver);
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		try {
			
			res.click_NewReservation(driver, testSteps);
			app_logs.info("CheckOut Date : " + checkOutDates.get(0));
			res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
			res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
			testSteps.addAll(res.enterAdult(driver, Adult));
			testSteps.addAll(res.enterChildren(driver, Children));
			testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
			res.clickOnFindRooms(driver, testSteps);
			res.selectRoom(driver, testSteps, roomClassName, "Yes", "");
			res.clickNext(driver, testSteps);

			res.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName,phoneNumber,phoneNumber,Email,"","",address,address2,address3,city,country,state,postalCode,"No");

			res.enterEmail(driver, testSteps, Email);
//
			testSteps.addAll(res.selectReferral(driver, Referral));
			res.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate);

			res.clickBookNow(driver, testSteps);
			roomNos = res.getStayInfoRoomNo(driver, testSteps);
			reservationNumber = res.get_ReservationConfirmationNumber(driver, testSteps);
			res.clickCloseReservationSavePopup(driver, testSteps);		
			res.click_Folio(driver, testSteps);
			homePage.VerifyAddLineItemWithDate(driver, testSteps,"Room Service", "30", checkInDate);
			resElement.reset_Button.click();
			//homePage.click_and_verify_PostLineItem(driver, testSteps, "Room Charge");
			//resElement.reset_Button.click();
			Wait.wait2Second();

			
			resElement.check_Box.click();
			reservationPage.clickVoidButton(driver);
			reservationPage.enterNoteInFolioTab(driver, "Cancel");
			reservationPage.clickVoidButtonOnNotePopup(driver);
			resElement.reset_Button.click();
			reservationPage.click_Pay(driver, testSteps);
			homePage.takePaymentWithoutSave(driver, testSteps, PaymentType,
					CardNumber, NameOnCard,
					CardExpDate, "Capture",
					"","", "No",
					"");
			resElement.reset_Button.click();
			
			if(!resElement.clickSaveButtonUnderFolioTab.isEnabled()) {
				testSteps.add("<b>Verified Save Button is disabled</b>");
			}
			else {
				testSteps.add("<b>Verified Save Button is enable</b>");
			}
			res.CloseOpenedTab(driver);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		/*
		try {

				// Create Guest Account
				testSteps.add("=================VALIDATE GUEST HISTORY ACCOUNT======================");
				app_logs.info("=================VALIDATE GUEST HISTORY ACCOUNT======================");
				navigation.GuestHistory(driver);
				guestHistory.clickGuestHistoryNewAccount(driver);
				Wait.wait2Second();
				assertEquals(guest_his.accountsPage_Reset_Btn.isEnabled(), false,"Reset Button is enabled");
				testSteps.add("<b>Verified Reset Button is Disabled</b>");
				assertEquals(guest_his.accountPage_Save_Btn.isEnabled(), false,"Save Button is enabled");
				testSteps.add("<b>Verified Save Button is Disabled</b>");
				
				testSteps.add("Open new guest history");
				guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
						testSteps);
				guestHistory.accountAttributes(driver, marketSegment, referral, testSteps);
				accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);

				System.out.println(accountNo);
				
				
				guestHistory.selectMailingInfoSaluation(driver, salutation, testSteps);
				guestHistory.enterPhoneCountryCode(driver, "1", testSteps);
				guestHistory.enterAlternatePhoneCountryCode(driver, "1", testSteps);
				guestHistory.enterPhoneExtention(driver,"1", testSteps);
				guestHistory.enterAlternatePhoneExtention(driver,"1", testSteps);
				guestHistory.enterFaxCountryCode(driver,"1", testSteps);
				guestHistory.enterFaxExtention(driver,"1", testSteps);
				guestHistory.selectMailingCountry(driver, "United States", testSteps);
				guestHistory.mailinginfo(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
						address, address, email, city, state, postalcode, testSteps);

				guestHistory.selectBillingPropertyDropdown(driver, "Reports Property", testSteps);
				guestHistory.selectBillingCountryDropdown(driver, "United States", testSteps);
				guestHistory.selectBillingStateDropdown(driver, "Alabama", testSteps);
				guestHistory.enterBillingPostalCode(driver, postalcode, testSteps);
				guestHistory.enterBillingCity(driver, city, testSteps);
				guestHistory.enterBillingEmail(driver, email, testSteps);
				guestHistory.enterBillingFaxExtention(driver, "1", testSteps);
				guestHistory.enterBillingFaxNumber(driver, phoneNumber, testSteps);
				guestHistory.enterBillingFaxCountryCode(driver, "1", testSteps);
				guestHistory.enterBillingAddressLine3(driver, address, testSteps);
				guestHistory.enterBillingAddressLine2(driver, address1, testSteps);
				guestHistory.enterBillingAddressLine1(driver, address2, testSteps);
				guestHistory.enterBillingAlternatePhoneExtention(driver, "1234", testSteps);
				guestHistory.enterBillingAlternatePhoneNumber(driver, phoneNumber, testSteps);
				guestHistory.enterBillingAlternatePhoneCountryCode(driver, "1", testSteps);
				guestHistory.enterBillingPhoneExtention(driver, phoneNumber, testSteps);
				guestHistory.enterBillingPhoneNumber(driver, phoneNumber, testSteps);
				guestHistory.enterBillingPhoneCountryCode(driver, "123", testSteps);
				guestHistory.enterBillingLastName(driver, "1", testSteps);
				guestHistory.enterBillingFirstName(driver, "1", testSteps);
				guestHistory.selectBillingInfoSaluation(driver, salutation, testSteps);
				guestHistory.selectBillingTypeDropdown(driver, PaymentType, testSteps);
				guest_his.GuestHistoryAccount_BillingAccountNum.sendKeys(CardNumber);
				testSteps.add("Account number : " + CardNumber);
				guest_his.GuestHistoryAccount_ExpDate.sendKeys(cardExpDate);
				testSteps.add("Exp Date : " + cardExpDate);
				guest_his.GuestHistoryAccount_BillingNotes.sendKeys("Account");
				testSteps.add("Billing Notes : " + "Account");
				
				guestHistory.clickOnAddNoteButton(driver, testSteps);
				guestHistory.selectNoteType(driver, "Internal", testSteps);
				guestHistory.enterNotedetails(driver, "Void", testSteps);
				guestHistory.enterNoteSubject(driver, "1", testSteps);
				guestHistory.clickOnSaveNoteButton(driver, testSteps);
				guestHistory.clickReset(driver);
				testSteps.add("Click on reset button");
				assertEquals(guest_his.accountPage_Save_Btn.isEnabled(), false,"Save Button is enabled");
				testSteps.add("<b>Verified Save Button is Disabled</b>");
				
				
				
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		try {
			accountType="Corporate/Member Accounts";
			Wait.wait2Second();
			CreateTA.VerifyAccountNameAndType(driver, accountType, testSteps);
			String value= new Select(accountEle.Account_Status_Active).getFirstSelectedOption().getText();
			assertEquals(value, "Active");
			guestHistory.verifyFirstLastNameFieldsEmpty(driver, testSteps);
			
			guestHistory.verifyAccountAttributesIsNotSelected(driver, testSteps);
			guestHistory.verifyAccountNumberFromDetailsPage(driver, testSteps, accountNo);
			guestHistory.VerifyMailinginfoIsEmpty(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
					address, address, email, city, state, postalcode, testSteps);
			guestHistory.verifyBillingFieldAfterReset(driver, testSteps);
			res.CloseOpenedTab(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try {
			navigation.Accounts(driver);
			driver.navigate().refresh();
			Wait.waitforPageLoad(2000, driver);
//			accountPage.OpenSearchedAccount(driver, "cooperate account with deposit policy", testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		String AccountNumber = null;
		// New account
		try {
			accountType="Corporate/Member Accounts";
			accountName="Cooperate"+Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, accountType);
			Wait.wait2Second();
			assertTrue(!accountEle.Account_save.isEnabled(), "Failed: Account Page Save Button is Enabled At Start");
			testSteps.add("Verified account page save button is disabled");
			assertTrue(!accountEle.AccountsPage_Reset_Btn.isEnabled(),
					"Failed: Account Page Reset Button is Enabled At Start");
			testSteps.add("Verified account page reset button is disabled");
			CreateTA.AccountDetails(driver, accountType, accountName);
			guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
					testSteps);
			
			
			guestHistory.accountAttributes(driver, marketSegment, referral, testSteps);
			accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);

			System.out.println(accountNo);
			
			
			guestHistory.selectMailingInfoSaluation(driver, salutation, testSteps);
			guestHistory.enterPhoneCountryCode(driver, "1", testSteps);
			guestHistory.enterAlternatePhoneCountryCode(driver, "1", testSteps);
			guestHistory.enterPhoneExtention(driver,"1", testSteps);
			guestHistory.enterAlternatePhoneExtention(driver,"1", testSteps);
			guestHistory.enterFaxCountryCode(driver,"1", testSteps);
			guestHistory.enterFaxExtention(driver,"1", testSteps);
			guestHistory.selectMailingCountry(driver, "United States", testSteps);
			guestHistory.mailinginfo(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
					address, address, email, city, state, postalcode, testSteps);

			
			
//	
//			driver.findElement(By.xpath(OR.Account_Check_Mailing_info)).click();
			

			guestHistory.selectBillingPropertyDropdown(driver, "Reports Property", testSteps);
			guestHistory.selectBillingCountryDropdown(driver, "United States", testSteps);
			guestHistory.selectBillingStateDropdown(driver, "Alabama", testSteps);
			guestHistory.enterBillingPostalCode(driver, postalcode, testSteps);
			guestHistory.enterBillingCity(driver, city, testSteps);
			guestHistory.enterBillingEmail(driver, email, testSteps);
			guestHistory.enterBillingFaxExtention(driver, "1", testSteps);
			guestHistory.enterBillingFaxNumber(driver, phoneNumber, testSteps);
			guestHistory.enterBillingFaxCountryCode(driver, "1", testSteps);
			guestHistory.enterBillingAddressLine3(driver, address, testSteps);
			guestHistory.enterBillingAddressLine2(driver, address1, testSteps);
			guestHistory.enterBillingAddressLine1(driver, address2, testSteps);
			guestHistory.enterBillingAlternatePhoneExtention(driver, "1234", testSteps);
			guestHistory.enterBillingAlternatePhoneNumber(driver, phoneNumber, testSteps);
			guestHistory.enterBillingAlternatePhoneCountryCode(driver, "1", testSteps);
			guestHistory.enterBillingPhoneExtention(driver, phoneNumber, testSteps);
			guestHistory.enterBillingPhoneNumber(driver, phoneNumber, testSteps);
			guestHistory.enterBillingPhoneCountryCode(driver, "123", testSteps);
			guestHistory.enterBillingLastName(driver, "1", testSteps);
			guestHistory.enterBillingFirstName(driver, "1", testSteps);
			guestHistory.selectBillingInfoSaluation(driver, salutation, testSteps);
			guestHistory.selectBillingTypeDropdown(driver, PaymentType, testSteps);
			guest_his.GuestHistoryAccount_BillingAccountNum.sendKeys(CardNumber);
			testSteps.add("Account number : " + CardNumber);
			guest_his.GuestHistoryAccount_ExpDate.sendKeys(cardExpDate);
			testSteps.add("Exp Date : " + cardExpDate);
			guest_his.GuestHistoryAccount_BillingNotes.sendKeys("Account");
			testSteps.add("Billing Notes : " + "Account");
			
			guestHistory.clickOnAddNoteButton(driver, testSteps);
			guestHistory.enterNotedetails(driver, "1", testSteps);
			guestHistory.selectNoteType(driver, "Internal", testSteps);
			guestHistory.enterNoteSubject(driver, "1", testSteps);
			guestHistory.clickOnSaveNoteButton(driver, testSteps);
			guestHistory.clickReset(driver);
			testSteps.add("Click on reset button");
			assertEquals(guest_his.accountPage_Save_Btn.isEnabled(), false,"Save Button is enabled");
			testSteps.add("<b>Verified Save Button is Disabled</b>");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	try {
		CreateTA.VerifyAccountNameAndType(driver, accountType, testSteps);
		String value= new Select(accountEle.Account_Status_Active).getFirstSelectedOption().getText();
		assertEquals(value, "Active");
		guestHistory.verifyFirstLastNameFieldsEmpty(driver, testSteps);
		
		guestHistory.verifyAccountAttributesIsNotSelected(driver, testSteps);
		guestHistory.verifyAccountNumberFromDetailsPage(driver, testSteps, accountNo);
		guestHistory.VerifyMailinginfoIsEmpty(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
				address, address, email, city, state, postalcode, testSteps);
		guestHistory.verifyBillingFieldAfterReset(driver, testSteps);
		res.CloseOpenedTab(driver);
//
//		
//			CreateTA.VerifyAccountNameAndType(driver, accountType, testSteps);
//			guestHistory.verifyFirstLastNameFieldsEmpty(driver, testSteps);			
//			guestHistory.verifyAccountAttributesIsNotSelected(driver, testSteps);
//			guestHistory.verifyAccountNumberFromDetailsPage(driver, testSteps, accountNo);
//			guestHistory.VerifyMailinginfoIsEmpty(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
//					address, address, email, city, state, postalcode, testSteps);
//			guestHistory.verifyBillingFieldAfterReset(driver, testSteps);
//			res.CloseOpenedTab(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
	
	try {
		navigation.Accounts(driver);
		driver.navigate().refresh();
		Wait.waitforPageLoad(2000, driver);
//		accountPage.OpenSearchedAccount(driver, "cooperate account with deposit policy", testSteps);

	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
		} else {

			Assert.assertTrue(false);
		}
	}
	AccountNumber = null;
	// New account
	try {
		accountType="Unit Owners";
		accountName=accountType+Utility.generateRandomString();
		CreateTA.ClickNewAccountbutton(driver, "Unit Owners");
		Wait.wait2Second();
		assertTrue(!accountEle.Account_save.isEnabled(), "Failed: Account Page Save Button is Enabled At Start");
		testSteps.add("Verified account page save button is disabled");
		assertTrue(!accountEle.AccountsPage_Reset_Btn.isEnabled(),
				"Failed: Account Page Reset Button is Enabled At Start");
		testSteps.add("Verified account page reset button is disabled");
		CreateTA.AccountDetails(driver, accountType, accountName);
		guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
				testSteps);
		
		
		guestHistory.accountAttributes(driver, marketSegment, referral, testSteps);
		accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);

		System.out.println(accountNo);
		
		
		guestHistory.selectMailingInfoSaluation(driver, salutation, testSteps);
		guestHistory.enterPhoneCountryCode(driver, "1", testSteps);
		guestHistory.enterAlternatePhoneCountryCode(driver, "1", testSteps);
		guestHistory.enterPhoneExtention(driver,"1", testSteps);
		guestHistory.enterAlternatePhoneExtention(driver,"1", testSteps);
		guestHistory.enterFaxCountryCode(driver,"1", testSteps);
		guestHistory.enterFaxExtention(driver,"1", testSteps);
		guestHistory.selectMailingCountry(driver, "United States", testSteps);
		guestHistory.mailinginfo(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
				address, address, email, city, state, postalcode, testSteps);

		
		
//
//		driver.findElement(By.xpath(OR.Account_Check_Mailing_info)).click();
		

		guestHistory.selectBillingPropertyDropdown(driver, "Reports Property", testSteps);
		guestHistory.selectBillingCountryDropdown(driver, "United States", testSteps);
		guestHistory.selectBillingStateDropdown(driver, "Alabama", testSteps);
		guestHistory.enterBillingPostalCode(driver, postalcode, testSteps);
		guestHistory.enterBillingCity(driver, city, testSteps);
		guestHistory.enterBillingEmail(driver, email, testSteps);
		guestHistory.enterBillingFaxExtention(driver, "1", testSteps);
		guestHistory.enterBillingFaxNumber(driver, phoneNumber, testSteps);
		guestHistory.enterBillingFaxCountryCode(driver, "1", testSteps);
		guestHistory.enterBillingAddressLine3(driver, address, testSteps);
		guestHistory.enterBillingAddressLine2(driver, address1, testSteps);
		guestHistory.enterBillingAddressLine1(driver, address2, testSteps);
		guestHistory.enterBillingAlternatePhoneExtention(driver, "1234", testSteps);
		guestHistory.enterBillingAlternatePhoneNumber(driver, phoneNumber, testSteps);
		guestHistory.enterBillingAlternatePhoneCountryCode(driver, "1", testSteps);
		guestHistory.enterBillingPhoneExtention(driver, phoneNumber, testSteps);
		guestHistory.enterBillingPhoneNumber(driver, phoneNumber, testSteps);
		guestHistory.enterBillingPhoneCountryCode(driver, "123", testSteps);
		guestHistory.enterBillingLastName(driver, "1", testSteps);
		guestHistory.enterBillingFirstName(driver, "1", testSteps);
		guestHistory.selectBillingInfoSaluation(driver, salutation, testSteps);
		guestHistory.selectBillingTypeDropdown(driver, "Check", testSteps);
		guestHistory.clickOnAddNoteButton(driver, testSteps);
		guestHistory.selectNoteType(driver, "Internal", testSteps);
		guestHistory.enterNotedetails(driver, "Void", testSteps);
		guestHistory.enterNoteSubject(driver, "1", testSteps);
		guestHistory.clickOnSaveNoteButton(driver, testSteps);
		guestHistory.clickReset(driver);
		testSteps.add("Click on reset button");
		assertEquals(guest_his.accountPage_Save_Btn.isEnabled(), false,"Save Button is enabled");
		testSteps.add("<b>Verified Save Button is Disabled</b>");
	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
		} else {
			Assert.assertTrue(false);
		}

	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
try {
	CreateTA.VerifyAccountNameAndType(driver, accountType, testSteps);
	String value= new Select(accountEle.Account_Status_Active).getFirstSelectedOption().getText();
	assertEquals(value, "Active");
	guestHistory.verifyFirstLastNameFieldsEmpty(driver, testSteps);
	
	guestHistory.verifyAccountAttributesIsNotSelected(driver, testSteps);
	guestHistory.verifyAccountNumberFromDetailsPage(driver, testSteps, accountNo);
	guestHistory.VerifyMailinginfoIsEmpty(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
			address, address, email, city, state, postalcode, testSteps);
	guestHistory.verifyBillingFieldAfterReset(driver, testSteps);
	res.CloseOpenedTab(driver);


	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

try {
	navigation.Accounts(driver);
	driver.navigate().refresh();
	Wait.waitforPageLoad(2000, driver);
//	accountPage.OpenSearchedAccount(driver, "cooperate account with deposit policy", testSteps);

} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
	} else {
		Assert.assertTrue(false);
	}
} catch (Error e) {
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
	} else {

		Assert.assertTrue(false);
	}
}
AccountNumber = null;
// New account
try {
	accountType="Travel Agent";
	accountName=accountType+Utility.generateRandomString();
	CreateTA.ClickNewAccountbutton(driver, accountType);
	Wait.wait2Second();
	assertTrue(!accountEle.Account_save.isEnabled(), "Failed: Account Page Save Button is Enabled At Start");
	testSteps.add("Verified account page save button is disabled");
	assertTrue(!accountEle.AccountsPage_Reset_Btn.isEnabled(),
			"Failed: Account Page Reset Button is Enabled At Start");
	testSteps.add("Verified account page reset button is disabled");
	CreateTA.AccountDetails(driver, accountType, accountName);
	guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
			testSteps);
	
	
	guestHistory.accountAttributes(driver, marketSegment, referral, testSteps);
	accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);

	System.out.println(accountNo);
	
	
	guestHistory.selectMailingInfoSaluation(driver, salutation, testSteps);
	guestHistory.enterPhoneCountryCode(driver, "1", testSteps);
	guestHistory.enterAlternatePhoneCountryCode(driver, "1", testSteps);
	guestHistory.enterPhoneExtention(driver,"1", testSteps);
	guestHistory.enterAlternatePhoneExtention(driver,"1", testSteps);
	guestHistory.enterFaxCountryCode(driver,"1", testSteps);
	guestHistory.enterFaxExtention(driver,"1", testSteps);
	guestHistory.selectMailingCountry(driver, "United States", testSteps);
	guestHistory.mailinginfo(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
			address, address, email, city, state, postalcode, testSteps);

	
	
//
//	driver.findElement(By.xpath(OR.Account_Check_Mailing_info)).click();
	

	guestHistory.selectBillingPropertyDropdown(driver, "Reports Property", testSteps);
	guestHistory.selectBillingCountryDropdown(driver, "United States", testSteps);
	guestHistory.selectBillingStateDropdown(driver, "Alabama", testSteps);
	guestHistory.enterBillingPostalCode(driver, postalcode, testSteps);
	guestHistory.enterBillingCity(driver, city, testSteps);
	guestHistory.enterBillingEmail(driver, email, testSteps);
	guestHistory.enterBillingFaxExtention(driver, "1", testSteps);
	guestHistory.enterBillingFaxNumber(driver, phoneNumber, testSteps);
	guestHistory.enterBillingFaxCountryCode(driver, "1", testSteps);
	guestHistory.enterBillingAddressLine3(driver, address, testSteps);
	guestHistory.enterBillingAddressLine2(driver, address1, testSteps);
	guestHistory.enterBillingAddressLine1(driver, address2, testSteps);
	guestHistory.enterBillingAlternatePhoneExtention(driver, "1234", testSteps);
	guestHistory.enterBillingAlternatePhoneNumber(driver, phoneNumber, testSteps);
	guestHistory.enterBillingAlternatePhoneCountryCode(driver, "1", testSteps);
	guestHistory.enterBillingPhoneExtention(driver, phoneNumber, testSteps);
	guestHistory.enterBillingPhoneNumber(driver, phoneNumber, testSteps);
	guestHistory.enterBillingPhoneCountryCode(driver, "123", testSteps);
	guestHistory.enterBillingLastName(driver, "1", testSteps);
	guestHistory.enterBillingFirstName(driver, "1", testSteps);
	guestHistory.selectBillingInfoSaluation(driver, salutation, testSteps);
	guestHistory.selectBillingTypeDropdown(driver, "Check", testSteps);
	guestHistory.clickOnAddNoteButton(driver, testSteps);
	guestHistory.selectNoteType(driver, "Internal", testSteps);
	guestHistory.enterNotedetails(driver, "Void", testSteps);
	guestHistory.enterNoteSubject(driver, "1", testSteps);
	guestHistory.clickOnSaveNoteButton(driver, testSteps);
	guestHistory.clickReset(driver);
	testSteps.add("Click on reset button");
	assertEquals(guest_his.accountPage_Save_Btn.isEnabled(), false,"Save Button is enabled");
	testSteps.add("<b>Verified Save Button is Disabled</b>");
} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
	} else {
		Assert.assertTrue(false);
	}

} catch (Error e) {
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
	} else {
		Assert.assertTrue(false);
	}
}
try {
	CreateTA.VerifyAccountNameAndType(driver, accountType, testSteps);
	String value= new Select(accountEle.Account_Status_Active).getFirstSelectedOption().getText();
	assertEquals(value, "Active");
	guestHistory.verifyFirstLastNameFieldsEmpty(driver, testSteps);
	
	guestHistory.verifyAccountAttributesIsNotSelected(driver, testSteps);
	guestHistory.verifyAccountNumberFromDetailsPage(driver, testSteps, accountNo);
	guestHistory.VerifyMailinginfoIsEmpty(driver, guestFirstName, guestLastName, phoneNumber, alternativeNumber, address,
			address, address, email, city, state, postalcode, testSteps);
	guestHistory.verifyBillingFieldAfterReset(driver, testSteps);
	res.CloseOpenedTab(driver);


} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
	} else {
		Assert.assertTrue(false);
	}
} catch (Error e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
	} else {
		Assert.assertTrue(false);
	}
}
try {
	navigation.Accounts(driver);
	driver.navigate().refresh();
	Wait.waitforPageLoad(2000, driver);
//	accountPage.OpenSearchedAccount(driver, "cooperate account with deposit policy", testSteps);

} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
	} else {
		Assert.assertTrue(false);
	}
} catch (Error e) {
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
	} else {

		Assert.assertTrue(false);
	}
}
AccountNumber = null;
// New account
try {
	accountType="House Account";
	accountName=accountType+Utility.generateRandomString();
	CreateTA.ClickNewAccountbutton(driver, accountType);
	Wait.wait2Second();
	assertTrue(!accountEle.Account_save.isEnabled(), "Failed: Account Page Save Button is Enabled At Start");
	testSteps.add("Verified account page save button is disabled");
	assertTrue(!accountEle.AccountsPage_Reset_Btn.isEnabled(),
			"Failed: Account Page Reset Button is Enabled At Start");
	testSteps.add("Verified account page reset button is disabled");
	CreateTA.AccountDetails(driver, accountType, accountName);
	accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);
	CreateTA.AccountSatusInActive(driver);
	System.out.println(accountNo);
	guestHistory.clickOnAddNoteButton(driver, testSteps);
	guestHistory.enterNotedetails(driver, "1", testSteps);
	guestHistory.selectNoteType(driver, "Internal", testSteps);
	guestHistory.enterNotedetails(driver, "Void", testSteps);
	guestHistory.enterNoteSubject(driver, "1", testSteps);
	guestHistory.clickOnSaveNoteButton(driver, testSteps);
	guestHistory.clickReset(driver);
	testSteps.add("Click on reset button");
	assertEquals(guest_his.accountPage_Save_Btn.isEnabled(), false,"Save Button is enabled");
	testSteps.add("<b>Verified Save Button is Disabled</b>");
} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
	} else {
		Assert.assertTrue(false);
	}

} catch (Error e) {
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
	} else {
		Assert.assertTrue(false);
	}
}
try {
	CreateTA.VerifyAccountNameAndType(driver, accountType, testSteps);
	guestHistory.verifyAccountNumberFromDetailsPage(driver, testSteps, accountNo);
	String value= new Select(accountEle.Account_Status_Active).getFirstSelectedOption().getText();
	assertEquals(value, "Active");
	testSteps.add("Verify account number field is reset");
	res.CloseOpenedTab(driver);

} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
	} else {
		Assert.assertTrue(false);
	}
} catch (Error e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
	} else {
		Assert.assertTrue(false);
	}
}
AccountNumber = null;
//New account
try {
	accountType="Gift Certificate";
	accountName=accountType+Utility.generateRandomString();
	CreateTA.ClickNewAccountbutton(driver, accountType);
	Wait.wait2Second();
	assertTrue(!accountEle.Account_save.isEnabled(), "Failed: Account Page Save Button is Enabled At Start");
	testSteps.add("Verified account page save button is disabled");
	assertTrue(!accountEle.AccountsPage_Reset_Btn.isEnabled(),
			"Failed: Account Page Reset Button is Enabled At Start");
	testSteps.add("Verified account page reset button is disabled");
	CreateTA.AccountDetails(driver, accountType, accountName);
	accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);
	CreateTA.AccountSatusInActive(driver);
	System.out.println(accountNo);
	guestHistory.clickOnAddNoteButton(driver, testSteps);
	guestHistory.enterNotedetails(driver, "1", testSteps);
	guestHistory.selectNoteType(driver, "Internal", testSteps);
	guestHistory.enterNotedetails(driver, "Void", testSteps);
	guestHistory.enterNoteSubject(driver, "1", testSteps);
	guestHistory.clickOnSaveNoteButton(driver, testSteps);
	guestHistory.clickReset(driver);
	testSteps.add("Click on reset button");
	assertEquals(guest_his.accountPage_Save_Btn.isEnabled(), false,"Save Button is enabled");
	testSteps.add("<b>Verified Save Button is Disabled</b>");
} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
	} else {
		Assert.assertTrue(false);
	}

} catch (Error e) {
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
	} else {
		Assert.assertTrue(false);
	}
}
try {
	CreateTA.VerifyAccountNameAndType(driver, accountType, testSteps);
	guestHistory.verifyAccountNumberFromDetailsPage(driver, testSteps, accountNo);
	testSteps.add("Verify account number field is reset");
	String value= new Select(accountEle.Account_Status_Active).getFirstSelectedOption().getText();
	assertEquals(value, "Active");
	res.CloseOpenedTab(driver);

} catch (Exception e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
	} else {
		Assert.assertTrue(false);
	}
} catch (Error e) {
	e.printStackTrace();
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
	} else {
		Assert.assertTrue(false);
	}
}

	Rules rules = new Rules();
	Season season=new Season();
	WebElements_Create_Seasons seasonELe = new WebElements_Create_Seasons(driver);

	String seasonValue = "All Year Season";

		String minimumStay = "Minimum Stay";
		String noCheckIn = "No Check In";
		String noCheckOut = "No Check Out";
		String misStayRule = "2";
		String ruleName = null, ruleType = null;
		ruleName = minimumStay + randomString;
		ruleType = minimumStay;
		String source = "innCenter";
		String defaultDateFormat = "dd/MM/yyyy";
		
		HashMap<String, Boolean> getDaysToCheck = new HashMap<>();
		String getDays = ESTTimeZone.reformatDate(checkInDates.get(0), defaultDateFormat, "EEE");
		printString("getDays : " + getDays);
		getDaysToCheck.put(getDays, true);

	try {
		navigation.Inventory(driver, testSteps);
		driver.findElement(By.xpath(OR.click_Season)).click();
		season.NewSeasonButtonClick(driver);
		season.enterSeasonName(driver, testSteps,"Saeson123");
		season.selectSeasonStatus(driver, testSteps,"Inactive");
		season.SetSeasonPeriod(driver, 7, testSteps);
		season.selectDays(driver, getDaysToCheck, testSteps);
		seasonELe.NG_Season_Reset.click();
		season.verifySeason(driver);
	}catch (Exception e) {
		// TODO: handle exception
	}
	
	try {
		// After login
		testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
		app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
		navigation.Inventory(driver, testSteps);
		navigation.policies(driver);
		testSteps.add("Navigated to policies");
	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	String DepositPolicyName = "deposit" + Utility.GenerateRandomNumber(300, 3000);
	String GuestMustPayPercentage = "100";
	String PolicyDescription = "New Deposit policy creation";
	String Chargestype = "Total Charges";
	Policies poli = new Policies();
	try {
		app_logs.info("************** Creatin a deposit policy *******************");
		testSteps.add("************** Creatin a deposit policy *******************");
		poli.ClickNewPolicybutton(driver);
		poli.Enter_Policy_Name(driver, DepositPolicyName, testSteps);
		poli.Deposit_Policy_Attributes(driver, Chargestype, GuestMustPayPercentage, testSteps);
		poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
		poli.Associate_Sources(driver);
		poli.Associate_Seasons(driver);
		poli.Associate_RoomClasses(driver, roomClassName);
		poli.Associate_RatePlan(driver, ratePlan);
		driver.findElement(By.xpath(OR.policy_reset_Button)).click();
		poli.verifyRuleAllField(driver);
		
		WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
		Select select = new Select(selectType);
		select.selectByVisibleText("Cancellation");
		poli.Enter_Policy_Name(driver, "Cancellation Ploicy", testSteps);
		poli.Cancellation_policy_Attributes(driver, "Room Charges", "20","Beyond","0", testSteps);
		poli.Enter_Policy_Desc(driver, "Cancellation Ploicy", "Cancellation Ploicy");
		poli.Associate_Sources(driver);
		poli.Associate_Seasons(driver);
		poli.Associate_RoomClasses(driver,roomClassName);
		poli.Associate_RatePlan(driver,ratePlan);
		driver.findElement(By.xpath(OR.policy_reset_Button)).click();
		poli.verifyRuleAllField(driver);
		
		select.selectByVisibleText("Check In");
		poli.Enter_Policy_Name(driver, "CheckIn Policy", testSteps);
		poli.Enter_Checkin_Policy_Attributes(driver, "authorize", "100");
		poli.Enter_Policy_Desc(driver, "CheckIn Policy","CheckIn Policy");
		poli.Associate_Sources(driver);
		poli.Associate_Seasons(driver);
		poli.Associate_RoomClasses(driver, roomClassName);
		poli.Associate_RatePlan(driver, ratePlan);
		driver.findElement(By.xpath(OR.policy_reset_Button)).click();
		poli.verifyRuleAllField(driver);
		select.selectByVisibleText("No Show");
		poli.Enter_Policy_Name(driver, "No Show Policy", testSteps);
		poli.Enter_Policy_Desc(driver, "No Show Policy", "No Show Policy");
		poli.Associate_Sources(driver);
		poli.Associate_Seasons(driver);
		poli.Associate_RoomClasses(driver, roomClassName);
		poli.Associate_RatePlan(driver, ratePlan);
		driver.findElement(By.xpath(OR.policy_reset_Button)).click();
		poli.verifyRuleAllField(driver);

	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	try {
			navigation.Inventory(driver, testSteps);
			
			rules.clickRule(driver);
			rules.clickNewRuleButton(driver);
			testSteps.add("Creating new rule with name (" + ruleName + ") and type (" + ruleType + ").");
			rules.create_Rule_without_save(driver, ruleName, ruleType, ruleName, misStayRule, roomClassName, source, ratePlan, seasonValue, "Active", getDaysToCheck);
			rules.clickResetRuleButton(driver);
			rules.verifyRuleAllField(driver);
			
			ruleName = noCheckIn + randomString;
			ruleType = noCheckIn;	
			testSteps.add("Creating new rule with name (" + ruleName + ") and type (" + ruleType + ").");
			rules.create_Rule_without_save(driver, ruleName, ruleType, ruleName, noCheckIn, roomClassName, source, ratePlan, seasonValue, "Active", getDaysToCheck);
			rules.clickResetRuleButton(driver);
			rules.verifyRuleAllField(driver);
			

			ruleName = noCheckOut + randomString;
			ruleType = noCheckOut;
			testSteps.add("Creating new rule with name (" + ruleName + ") and type (" + ruleType + ").");
			rules.create_Rule_without_save(driver, ruleName, ruleType, ruleName, noCheckOut, roomClassName, source, ratePlan, seasonValue, "Active", getDaysToCheck);
			rules.clickResetRuleButton(driver);
			rules.verifyRuleAllField(driver);
	
	}
	catch (Exception e) {
		// TODO: handle exception
	}
		
	try {
		Admin admin=new Admin();
		Elements_Roles roles = new Elements_Roles(driver);

		Elements_Users elements=new Elements_Users(driver);
		navigation.navigateToAdminPage(driver, testSteps);
		driver.findElement(By.xpath(OR.Users)).click();
		admin.CreateNewUser(driver, guestFirstName, guestLastName, "2", email, "Administrator", property, isSuiteCreated, testSteps);
		Wait.explicit_wait_visibilityof_webelement(elements.Select_Status, driver);
		new Select(elements.Select_Status).selectByVisibleText("Inactive");
		testSteps.add("Status Changed to : " + "Inactive");
		admin.changesOrVerifyAllPreferencesValues(driver,testSteps,true);
		assertEquals(roles.buttonResetRole.isEnabled(),true);
		roles.buttonResetRole.click();
		admin.valideUserFieldIsReset(driver,testSteps);
		admin.changesOrVerifyAllPreferencesValues(driver,testSteps,false);
		
		navigation.navigateToAdminPage(driver, testSteps);
		admin.enterRoleNameAndDescriptionOrVerifyAfterReset(driver,testSteps,true);
		admin.selectAllCheckBoxOrVerifyAfterReset(driver,testSteps,true);
		roles.buttonResetRole.click();
		admin.enterRoleNameAndDescriptionOrVerifyAfterReset(driver,testSteps,false);
		admin.selectAllCheckBoxOrVerifyAfterReset(driver,testSteps,false);
		
		
	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to CheckIn", testName, "Reservation", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to CheckIN", testName, "Reservation", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	*/
	try {	
		
		statusCode.add(0, "1");
		comments = "Verify Reset Option across the screens";
		test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/824576'>" + "Click here to open TestRail: </a>";
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport("Verify Reset Option across the screens", test_description, "Verify Reset Option across the screens", testSteps);
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption", test_description, driver);
		} else {
			Assert.assertTrue(false);
		}
	}
}

	

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyResetOptionAcrossScreens", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
