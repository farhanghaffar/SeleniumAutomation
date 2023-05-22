package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_Reservation;

public class ReportsV2DailyFlashReportPropertyStatistics extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();

	String reservationNumber = null, guestName = null, roomNumber = null, date = null;
	
	String newRoomClassName ;
	int roomQuantity ;
	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	HashMap<String, String> itemDescription = new HashMap<>();
	ArrayList<String> guestNames = new ArrayList<>();
	ArrayList<String> reservationNumbers = new ArrayList<>();
	ArrayList<String> arrivalDates = new ArrayList<>();
	ArrayList<String> dates = new ArrayList<>();
	ArrayList<String> itemDescriptions = new ArrayList<>();
	ArrayList<String> amountList = new ArrayList<>();
	
	HashMap<String, ArrayList<String>> beforeRevenueDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterRevenueDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforePaymentDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterPaymentDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeNetChangesDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterNetChangesDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeGuestCountDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterGuestCountDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforePropertyStatisticsDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterPropertyStatisticsDetails = new HashMap<>();
	
    ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void verifyReportsV2DailyFlash(String TestCaseID, String Scenario, String dateRange, String dateEffective,

			String accountType, String Reservation, String isMRB, String numberOfRoomsMRB, String CheckInDate, String CheckOutDate,  String Rateplan, String IsSplitRes, String RoomClass, String email,
			String country, String state, String isPayment, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String isTaxExempt, String TaxExemptID, String daysTaxExempt,
			String marketSegment, String referral, String resStatus, String propertyStatistics )throws InterruptedException, IOException, ParseException {

		test_name = Scenario;
		test_description = "ReportsV2 - DailyFlashReport - Property Statistics<br>"
				+ "<a href='' target='_blank'>"
				+ "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Daily Flash Report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}			
		}

		Navigation nav = new Navigation();
		ReportsV2 report = new ReportsV2();
		CPReservationPage reservationPage = new CPReservationPage();
		Groups group = new Groups();
		Account accountPage = new Account();
		NewRoomClassesV2 rc2 = new NewRoomClassesV2();
		RoomMaintenance rm = new RoomMaintenance();
		ReservationV2 reservationV2Page = new ReservationV2();

		String Adults = "2";
		String Children = "1";
		String PromoCode = "";
		String IsGuesProfile = "No";

		String guestFirstName = "First" + Utility.generateRandomStringWithoutNumbers();
		String guestLastName = "Last" + Utility.generateRandomStringWithoutNumbers();
		String salutation = "Mr.";
		String address1 = Utility.generateRandomString();
		String address2 = Utility.generateRandomString();
		String address3 = Utility.generateRandomString();
		String city = Utility.generateRandomString();
		String postalCode = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
		// String phoneNumber = Utility.GenerateRandomNumber(10);
		// String alternativePhone = Utility.GenerateRandomNumber(10);
		String phoneNumber = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String alternativePhone = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		int numberOfRooms = 1;
		if (!numberOfRoomsMRB.isEmpty()) {
			numberOfRooms = Integer.parseInt(numberOfRoomsMRB);
		}

		String accountName = "Account" + Utility.generateRandomStringWithoutNumbers();
		String accountNumber = null;

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIn, CheckOut Date
			if (CheckInDate.equalsIgnoreCase("NA") || CheckInDate.isEmpty() || CheckInDate.equalsIgnoreCase("")) {
				if (!isMRB.equalsIgnoreCase("Yes")) {
					CheckInDate = Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern");
					CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
//					CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(-1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
//					CheckOutDate = Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern");						
				} else {
					// Get CheckIN and Checkout Date
					if (CheckInDate.equalsIgnoreCase("NA") || CheckInDate.isEmpty()
							|| CheckInDate.equalsIgnoreCase("")) {
						checkInDates.clear();
						checkOutDates.clear();						
						for (int i = 1; i <= numberOfRooms; i++) {
							app_logs.info("Loop" + i);
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern"));
							checkOutDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy"));
							app_logs.info(checkInDates);
							app_logs.info(checkOutDates);
						}
					} else {
						checkInDates = Utility.splitInputData(CheckInDate);
						checkOutDates = Utility.splitInputData(CheckOutDate);
					}
					app_logs.info(checkInDates);
					app_logs.info(checkOutDates);

					CheckInDate = checkInDates.get(0);
					CheckOutDate = checkOutDates.get(0);
					for (int i = 1; i < checkInDates.size(); i++) {
						CheckInDate = CheckInDate + "|" + checkInDates.get(i);
						CheckOutDate = CheckOutDate + "|" + checkOutDates.get(i);
					}
					app_logs.info(CheckInDate);
					app_logs.info(CheckOutDate);
				}
			}
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
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
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			Set<String> windows = driver.getWindowHandles();
			if (windows.size() > 1) {
				driver.close();
				Wait.wait1Second();
				Utility.switchTab(driver, 0);
			}
			
			loginReportsV2ReservationV2(driver);
			//login.login(driver, envURL, clientId, userName, password);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
		} catch (Exception e) {			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);	
		} catch (Error e) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
		}
				
		//Navigate to Reports page
		try {
			//test_steps.add("========= Navigating to Daily Flash Report =========");
			nav.ReportsV2(driver);
			report.navigateToDailyFlashReport(driver);
			
			app_logs.info("Navigated to Daily Flash Report page");
			test_steps.add("Navigated to Daily Flash Report page");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Navigating to Daily Flash Report page", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
				
		try {
			test_steps.add("========= Getting Report data before Reservation =========");
			//report.clickBreakOutTaxExemptRevenueDailyFlash(driver, test_steps);
			//report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			report.clickOnRunReport(driver);
			
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 20; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			beforeRevenueDetails = report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps);
			beforePaymentDetails = report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps);
			beforeNetChangesDetails = report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps);
			beforeGuestCountDetails = report.getGuestCountStatisticsTableData(driver, test_steps);
			beforePropertyStatisticsDetails = report.getPropertyPerformanceDataWithTable(driver, "Property Statistics", test_steps);
			
			app_logs.info("Revenue Details Before: "+beforeRevenueDetails);
			app_logs.info("Payment Details Before: "+beforePaymentDetails);
			app_logs.info("Net Changes Details Before: "+beforeNetChangesDetails);
			app_logs.info("Guest Count Details Before: "+beforeGuestCountDetails);
			app_logs.info("Property Statistics Details Before: "+beforePropertyStatisticsDetails);
			
			driver.close();
			// Utility.switchTab(driver, 0);
			Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);

			//nav.Reservation_Backward_3(driver);
			nav.ReservationV2_Backward(driver);
			app_logs.info("Back to reservation page");	
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
			
		if (propertyStatistics.equalsIgnoreCase("Sold Room Nights")) {
			try {				
				if (!accountType.isEmpty() || !accountType.equalsIgnoreCase("")) {
					if (accountType.equals("House Account")) {
						try {
							nav.Accounts(driver);
							test_steps.add("========== Creating House account ==========");
							app_logs.info("========== Creating House account ==========");
							accountName = accountName + Utility.generateRandomString();
							accountPage.ClickNewAccountbutton(driver, accountType);
							accountPage.AccountDetails(driver, accountType, accountName);
							accountNumber = Utility.GenerateRandomString15Digit();
							accountPage.ChangeAccountNumber(driver, accountNumber);

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
					}else if (accountType.equals("Gift Certificate")) {
						try {
							String AccountNumber = null;

							nav.Accounts(driver);
							test_steps.add("========== Creating Gift Certificate account ==========");
							app_logs.info("========== Creating account ==========");
							accountName = accountName + Utility.generateRandomString();
							accountPage.ClickNewAccountbutton(driver, accountType);
							accountPage.AccountDetails(driver, accountType, accountName);
							AccountNumber = Utility.GenerateRandomString15Digit();
							accountPage.ChangeAccountNumber(driver, AccountNumber);

							report.AccountSave(driver, test, accountName, test_steps);
							accountPage.ClickFolio(driver);
							accountPage.addLineitem1(driver, "", "Gift Certificate", "1000", test_steps);
							accountPage.Commit(driver);
							accountPage.Save(driver, test, test_steps);
							Wait.wait5Second();
						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName,
										"Gift Certificate Account", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {

							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName,
										"Gift Certificate Account", driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					} else if (accountType.equals("Travel Agent")) {
						nav.Accounts(driver);
						try {
							// nav.Accounts(driver);
							test_steps.add("========== Creating Travel Agent account ==========");
							app_logs.info("========== Creating Travel Agent account ==========");
							accountPage.ClickNewAccountbutton(driver, accountType);
							app_logs.info("Clicked on new Account");
							accountPage.AccountDetails(driver, accountType, accountName);
							app_logs.info("Entered account details");
							accountNumber = Utility.GenerateRandomString15Digit();
							accountPage.ChangeAccountNumber(driver, accountNumber);
							accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
							accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber,
									alternativePhone, address1, address2, address3, email, city, state, postalCode,
									test_steps);
							accountPage.Billinginfo(driver, test, test_steps);
							report.AccountSave(driver, test, accountName, test_steps);
							test_steps.add("========== Account Created ==========");
							app_logs.info("========== Account Created ==========");
							if (Reservation.equalsIgnoreCase("Yes")) {
								accountPage.NewReservationButton(driver, test);
							}
						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Travel agent account", testName,
										"TravelAgentAccount", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Travel agent account", testName,
										"TravelAgentAccount", driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}else if (accountType.equals("Unit Owners")) {
						try {
							test_steps.add("========== Creating Unit Owners account ==========");
							app_logs.info("========== Creating Unit Owners account ==========");
							accountName = accountName + Utility.generateRandomString();
							nav.Accounts(driver);
							accountPage.ClickNewAccountbutton(driver, accountType);
							accountPage.AccountDetails(driver, accountType, accountName);
							accountNumber = Utility.GenerateRandomString15Digit();
							accountPage.ChangeAccountNumber(driver, accountNumber);

							accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
							accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber,
									alternativePhone, address1, address2, address3, email, city, state, postalCode,
									test_steps);
							accountPage.Billinginfo(driver, test, test_steps);
							// accountPage.associateRooms(driver, test_steps, RoomClass);
							report.AccountSave(driver, test, accountName, test_steps);

							if (Reservation.equalsIgnoreCase("Yes")) {
								accountPage.NewReservationButton(driver, test);
							}
						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Unit Owner account", testName, "UnitOwnerAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Unit Owner account", testName, "UnitOwnerAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}else if (accountType.equals("Corporate/Member Accounts")) {
						try {
							test_steps.add("========== Creating Corporate/Member Accounts account ==========");
							app_logs.info("========== Creating Corporate/Member Accounts account ==========");
							// accountName = accountName + Utility.generateRandomString();
							nav.Accounts(driver);
							accountPage.ClickNewAccountbutton(driver, accountType);
							accountPage.AccountDetails(driver, accountType, accountName);
							accountNumber = Utility.GenerateRandomString15Digit();
							accountPage.ChangeAccountNumber(driver, accountNumber);

							accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
							accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber,
									alternativePhone, address1, address2, address3, email, city, state, postalCode,
									test_steps);
							accountPage.Billinginfo(driver, test, test_steps);
							report.AccountSave(driver, test, accountName, test_steps);

							if (Reservation.equalsIgnoreCase("Yes")) {
								accountPage.NewReservationButton(driver, test);
							}
						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}else if (accountType.equals("Group")) {
						try {
							test_steps.add("========== Creating Group account ==========");
							app_logs.info("========== Creating Group account ==========");

							nav.groups(driver);
							// group.click_NewAccount(driver, test, test_steps);
							group.type_GroupName(driver, test, accountName, test_steps);
							group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
							group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber, address1,
									city, state, country, postalCode, test_steps);
							group.Billinginfo(driver);
							group.Save(driver, test_steps);
							if (Reservation.equalsIgnoreCase("Yes")) {
								group.click_GroupNewReservation(driver, test_steps);
							}
						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}
				}
				
				if (Reservation.equalsIgnoreCase("Yes")) {					
					try {
						reservationPage.click_NewReservation(driver, test_steps);
					} catch (Exception e) {

					}
					
					ArrayList<String> roomNumbers = new ArrayList<>();
					ArrayList<String> roomClasses = Utility.splitInputData(RoomClass);
					HashMap<String, String> data = new HashMap<>();
					
					if (isMRB.equalsIgnoreCase("Yes")) {
						// MRB - Reservation
						test_steps.add("===== Creting MRB Reservation =====");
						try {
							String mrbGuestFirstName = guestFirstName;
							String mrbGuestLastName = guestLastName;
							String mrbAdults = Adults;
							String mrbChildren = Children;
							String mrbRateplan = Rateplan;
							String mrbRoomClass = RoomClass;
							String mrbSalutation = salutation;
							String mrbPhoneNumber = phoneNumber;
							String mrbEmail = email;

							for (int i = 2; i <= numberOfRooms; i++) {
								mrbGuestFirstName = mrbGuestFirstName + "|" + Utility.generateRandomString();
								mrbGuestLastName = mrbGuestLastName + "|" + Utility.generateRandomString();
								mrbAdults = mrbAdults + "|" + Adults;
								mrbChildren = mrbChildren + "|" + Children;
								mrbRateplan = mrbRateplan + "|" + Rateplan;
								mrbRoomClass = mrbRoomClass + "|" + RoomClass;
								mrbSalutation = mrbSalutation + "|" + salutation;
								mrbPhoneNumber = mrbPhoneNumber + "|" + phoneNumber;
								mrbEmail = mrbEmail + "|" + email;
							}

							app_logs.info(mrbGuestFirstName);
							app_logs.info(mrbGuestLastName);
							app_logs.info(mrbAdults);
							app_logs.info(mrbChildren);
							app_logs.info(mrbRateplan);
							app_logs.info(mrbRoomClass);
							app_logs.info(mrbSalutation);
							app_logs.info(mrbPhoneNumber);
							app_logs.info(mrbEmail);

							reservationV2Page.searchDataForFindRoomsForMRB(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode);
							reservationV2Page.clickOnFindRooms(driver, test_steps);
							for (int i = 0; i < RoomClass.split("\\|").length; i++) {

								int j = i+1;
								roomNumbers = reservationV2Page.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
								reservationV2Page.selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
								data.put("Room Number"+j, roomNumbers.get(i));
							}
							
							reservationV2Page.clickNext(driver, test_steps);
							if (guestFirstName.split("\\|").length > 1) {
								reservationV2Page.add_PrimaryRoom(driver, test_steps);
								reservationV2Page.add_AdditionalRoom(driver, test_steps);
							}
							
							reservationV2Page.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName);
							reservationV2Page.uncheck_CreateGuestProfile(driver, test_steps, IsGuesProfile);
							
							reservationV2Page.enter_Phone(driver, test_steps, phoneNumber, phoneNumber);
							reservationV2Page.enter_Email(driver, test_steps, email);
							reservationV2Page.enter_Address(driver, test_steps, address1, address2, address3);
							reservationV2Page.enter_City(driver, test_steps, city);
							reservationV2Page.select_Country(driver, test_steps, country);
							reservationV2Page.select_State(driver, test_steps, state);
							reservationV2Page.enter_PostalCode(driver, test_steps, postalCode);			
							if (Utility.validateString(PaymentType)) {
								// cardNumber variable can be used as Gift Card number (If Payment method: Gift Certificate, Reservation number if Payment Method: Reservation
								reservationV2Page.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate, accountName);
							}
							reservationV2Page.enter_MarketingInfoDetails(driver, test_steps, "", "", referral, "Yes");
							reservationV2Page.enter_TaxExemptDetails(driver, test_steps, false, "");
							reservationV2Page.clickBookNow(driver, test_steps);
							data.put("Reservation Number", reservationV2Page.get_ReservationConfirmationNumber(driver, test_steps));
							data.put("Reservation Status", reservationV2Page.get_ReservationStatus(driver, test_steps));
							reservationV2Page.clickCloseReservationSavePopup(driver, test_steps);
							TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
							data.put("Room Charges", tripSummary.getTS_ROOM_CHARGE());
							data.put("Taxes", tripSummary.getTS_TAXES());
						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create new MRB reservation", testName,
										"MRBReservationCreation", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(test_name) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create new MRB reservation", testName,
										"MRBReservationCreation", driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}else {
						test_steps.add("==== Creating Single Reservation ====");
						try {							
							reservationV2Page.searchDataForFindRooms(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode);				
							reservationV2Page.clickOnFindRooms(driver, test_steps);
							roomNumbers = reservationV2Page.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClass);
							
							try {
								reservationV2Page.selectRoomToReserve(driver, test_steps, RoomClass, roomNumber);
								data.put("Room Number", roomNumber);					
							} catch (Exception e) {
								reservationV2Page.selectRoomToReserve(driver, test_steps, RoomClass, roomNumbers.get(0));				
								data.put("Room Number", roomNumbers.get(0));
							}
							
							reservationV2Page.clickNext(driver, test_steps);							
							reservationV2Page.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName);
							reservationV2Page.uncheck_CreateGuestProfile(driver, test_steps, IsGuesProfile);
							
							reservationV2Page.enter_Phone(driver, test_steps, phoneNumber, phoneNumber);
							reservationV2Page.enter_Email(driver, test_steps, email);
							reservationV2Page.enter_Address(driver, test_steps, address1, address2, address3);
							reservationV2Page.enter_City(driver, test_steps, city);
							reservationV2Page.select_Country(driver, test_steps, country);
							reservationV2Page.select_State(driver, test_steps, state);
							reservationV2Page.enter_PostalCode(driver, test_steps, postalCode);			
							if (Utility.validateString(PaymentType)) {
								// cardNumber variable can be used as Gift Card number (If Payment method: Gift Certificate, Reservation number if Payment Method: Reservation
								reservationV2Page.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate, accountName);
							}
							reservationV2Page.enter_MarketingInfoDetails(driver, test_steps, "", "", referral, "Yes");
							reservationV2Page.enter_TaxExemptDetails(driver, test_steps, false, "");
							reservationV2Page.clickBookNow(driver, test_steps);
							data.put("Reservation Number", reservationV2Page.get_ReservationConfirmationNumber(driver, test_steps));
							data.put("Reservation Status", reservationV2Page.get_ReservationStatus(driver, test_steps));
							reservationV2Page.clickCloseReservationSavePopup(driver, test_steps);
							TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
							data.put("Room Charges", tripSummary.getTS_ROOM_CHARGE());
							data.put("Taxes", tripSummary.getTS_TAXES());
						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create New reservation button", testName,
										"CreateReservation", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create New reservation button", testName,
										"CreateReservation", driver);
							} else {
								Assert.assertTrue(false);
							}
						}	
					}
					roomQuantity = numberOfRooms;	
				}
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create New reservation button", testName,
							"CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create New reservation button", testName,
							"CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}	
		}else if (propertyStatistics.equalsIgnoreCase("Total Room Nights")) {
			// creating new Room Class
			try {			
				nav.setup(driver);
				nav.roomClass(driver);
				//rc2.createRoomClassV2(driver, roomClassName, roomClassAbbrivation, maxAdults, maxPersons, roomQuantity, test, test_steps);
				
				newRoomClassName = "Test"+Utility.generateRandomNumber();
				roomQuantity = Utility.getRandomNumber(1, 5);
				app_logs.info("Rooms quantity: "+roomQuantity);

				rc2.createRoomClassV2(driver, test_steps, newRoomClassName, "TRC", "3", "5", ""+roomQuantity+"");				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			}
		}else if (propertyStatistics.equalsIgnoreCase("Out of Order Room Nights")) {
			// making room out of order
			try {
				roomQuantity = 1;
				//making rooms out of order
				//nav.Guestservices(driver);
				nav.clickOnGuestServices(driver, test_steps);
				nav.RoomMaintenance(driver);
				rm.CreateNewRoomOut(driver, ""+roomQuantity+"", "testt", RoomClass);
					
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
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
		
		app_logs.info("Property updated at: " + java.time.LocalTime.now());
		test_steps.add("Property updated at: " + java.time.LocalTime.now());
		Wait.wait60Second();
		Wait.wait60Second();
//		Wait.wait60Second();
//		Wait.wait60Second();
//		Wait.wait60Second();

		app_logs.info("Running Report at " + java.time.LocalTime.now());
		test_steps.add("Running Report at " + java.time.LocalTime.now());
			
		try {
			test_steps.add("========= Navigating to ReportsV2 =========");
			nav.ReportsV2(driver);
			report.navigateToDailyFlashReport(driver);
			//report.clickBreakOutTaxExemptRevenueDailyFlash(driver, test_steps);
			//report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			report.clickOnRunReport(driver);			
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 20; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			afterRevenueDetails = report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps);
			afterPaymentDetails = report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps);
			afterNetChangesDetails = report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps);
			afterGuestCountDetails = report.getGuestCountStatisticsTableData(driver, test_steps);
			afterPropertyStatisticsDetails = report.getPropertyPerformanceDataWithTable(driver, "Property Statistics", test_steps);
			
			app_logs.info("Revenue Details After: "+afterRevenueDetails);
			app_logs.info("Payment Details After: "+afterPaymentDetails);
			app_logs.info("Net Changes Details After: "+afterNetChangesDetails);
			app_logs.info("Guest Count Details After: "+afterGuestCountDetails);
			app_logs.info("Property Statistics Details After: "+afterPropertyStatisticsDetails);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		test_steps.add("=====  Validating Property Statistics table  =====");
		try {			
			if (!propertyStatistics.isEmpty()) {
				test_steps.add("====  Validating Property Statistics - "+propertyStatistics+"  ====");
				report.validateDailyFlashReportPropertyStatistics(driver, beforePropertyStatisticsDetails, afterPropertyStatisticsDetails, propertyStatistics, roomQuantity, test_steps);
			}

			test_steps.add("====  Validating Property Statistics - Bookable Room Nights  ====");
			report.validatePropertyStatisticsBookableRoomNights(driver, afterPropertyStatisticsDetails, test_steps);
			test_steps.add("====  Validating Property Statistics - Remaining Available Room Nights  ====");	
			report.validatePropertyStatisticsRemainingAvailableRoomNights(driver, afterPropertyStatisticsDetails, test_steps);
			test_steps.add("====  Validating Property Statistics - Occupancy%  ====");
			report.validatePropertyStatisticsOccupancy(driver, afterPropertyStatisticsDetails, test_steps);
			test_steps.add("====  Validating Property Statistics - Average Daily Rate(ADR)  ====");
			report.validatePropertyStatisticsAverageDailyRateADR(driver, afterRevenueDetails, afterPropertyStatisticsDetails, test_steps);
			test_steps.add("====  Validating Property Statistics - Rev Per AvailRoom(RevPAR)  ====");
			report.validatePropertyStatisticsRevPerAvailRoomRevPAR(driver, afterRevenueDetails, afterPropertyStatisticsDetails, test_steps);			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}

		try {			
			if (propertyStatistics.equalsIgnoreCase("Sold Room Nights") && !resStatus.isEmpty()) {				
				driver.close();
				Utility.switchTab(driver, 0);
				
				nav.reservationBackward3(driver);
				reservationPage.Search_ResNumber_And_Click(driver, reservationNumber);
				driver.navigate().refresh();
				Wait.wait5Second();
				if (resStatus.equalsIgnoreCase("Cancelled")) {
					test_steps.add("==  Cancelling the reservation  ==");
					reservationPage.cancelReservation(driver, "void");
				}else if (resStatus.equalsIgnoreCase("No Show")) {
					test_steps.add("==  No Show the reservation  ==");
					reservationPage.noShowReservation(driver, "void");
				}				
				roomQuantity = -numberOfRooms;				
				app_logs.info("Property updated at: " + java.time.LocalTime.now());
				test_steps.add("Property updated at: " + java.time.LocalTime.now());
				Wait.wait60Second();
				Wait.wait60Second();
//				Wait.wait60Second();
//				Wait.wait60Second();
//				Wait.wait60Second();

				app_logs.info("Running Report at " + java.time.LocalTime.now());
				test_steps.add("Running Report at " + java.time.LocalTime.now());
				
				HashMap<String, ArrayList<String>> afterPropertyStatisticsDetails2 = new HashMap<>();
				try {
					test_steps.add("========= Navigating to ReportsV2 =========");
					nav.ReportsV2(driver);
					report.navigateToDailyFlashReport(driver);
					//report.clickBreakOutTaxExemptRevenueDailyFlash(driver, test_steps);
					//report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
					report.clickOnRunReport(driver);
					
					try {
						String noReport = "//*[contains(text(),'No Report Data Available')]";
						for (int i = 0; i < 20; i++) {
							if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
								app_logs.info("Got No Report Data message");
								Wait.wait2Second();
								report.clickOnRunReportBottom(driver);
							}else {
								break;
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					afterRevenueDetails = report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps);
					afterPaymentDetails = report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps);
					afterNetChangesDetails = report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps);
					afterGuestCountDetails = report.getGuestCountStatisticsTableData(driver, test_steps);
					afterPropertyStatisticsDetails2 = report.getPropertyPerformanceDataWithTable(driver, "Property Statistics", test_steps);
					
					app_logs.info("Revenue Details After: "+afterRevenueDetails);
					app_logs.info("Payment Details After: "+afterPaymentDetails);
					app_logs.info("Net Changes Details After: "+afterNetChangesDetails);
					app_logs.info("Guest Count Details After: "+afterGuestCountDetails);
					app_logs.info("Property Statistics Details After: "+afterPropertyStatisticsDetails);
					
				}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
								"ReportsV2", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					test_steps.add(e.toString());
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				}
				
				test_steps.add("=====  Validating Property Statistics table after Cancel/No SHow Reservation =====");
				try {
					
					if (!propertyStatistics.isEmpty()) {
						test_steps.add("====  Validating Property Statistics - "+propertyStatistics+"  ====");
						report.validateDailyFlashReportPropertyStatistics(driver, afterPropertyStatisticsDetails, afterPropertyStatisticsDetails2, propertyStatistics, roomQuantity, test_steps);
					}

					test_steps.add("====  Validating Property Statistics - Bookable Room Nights  ====");
					report.validatePropertyStatisticsBookableRoomNights(driver, afterPropertyStatisticsDetails2, test_steps);
					test_steps.add("====  Validating Property Statistics - Remaining Available Room Nights  ====");	
					report.validatePropertyStatisticsRemainingAvailableRoomNights(driver, afterPropertyStatisticsDetails2, test_steps);
					test_steps.add("====  Validating Property Statistics - Occupancy%  ====");
					report.validatePropertyStatisticsOccupancy(driver, afterPropertyStatisticsDetails2, test_steps);
					test_steps.add("====  Validating Property Statistics - Average Daily Rate(ADR)  ====");
					report.validatePropertyStatisticsAverageDailyRateADR(driver, afterRevenueDetails, afterPropertyStatisticsDetails2, test_steps);
					test_steps.add("====  Validating Property Statistics - Rev Per AvailRoom(RevPAR)  ====");
					report.validatePropertyStatisticsRevPerAvailRoomRevPAR(driver, afterRevenueDetails, afterPropertyStatisticsDetails2, test_steps);					
				}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
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
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		try {
			//comments = "";
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("DailyFlashReportProperty", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
	
}
