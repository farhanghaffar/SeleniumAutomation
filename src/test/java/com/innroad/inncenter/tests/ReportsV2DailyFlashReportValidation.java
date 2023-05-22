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

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_Reservation;

public class ReportsV2DailyFlashReportValidation extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	String startDayOfWeek;
	String clientTimeZone;
	String dateFormat=null, dFormat=null;
	String propertyName = null;
	String currentStatus = null;
	String resStatus = "";
	String reservationNumber = null, guestName = null, roomNumber = null, date = null;
	Double outBoundAmount = 0.00;
	int roomCount = 0, guestCount = 0, adultsCount = 0, childCount = 0;
	HashMap<String, String> ledgerAccounts = new HashMap<>();
	HashMap<String, Double> ledgerAmounts = new HashMap<>();
	HashMap<String, String> folioItemValues = new HashMap<>();
	HashMap<String, Double> folioBalances = new HashMap<>();
	
	Double FolioBeginningBalance, FolioPayments, FolioNewCharges, FolioTaxesAccount, FolioEndingBalance, FolioAdvanceDepositBalance;
	Double FolioTotalCharges, FolioBalance;
	
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
	HashMap<String, ArrayList<String>> beforeRoomsRevenue = new HashMap<>();
	HashMap<String, ArrayList<String>> afterRoomsRevenue = new HashMap<>();
	HashMap<String, ArrayList<String>> beforePaymentDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterPaymentDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeNetChangesDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterNetChangesDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeGuestCountDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> afterGuestCountDetails = new HashMap<>();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void verifyReportsV2DailyFlash(String Scenario, String dateRange, String dateEffective,

			String LedgerAccount, String LedgerValue, String PaymentMethod,

			String accountType, String Reservation, String isMRB, String numberOfRoomsMRB, String CheckInDate, String CheckOutDate,  String Rateplan, String IsSplitRes, String RoomClass, String email,
			String country, String state, String isPayment, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String isTaxExempt, String TaxExemptID, String daysTaxExempt,
			String marketSegment, String referral, String guestStatus )throws InterruptedException, IOException, ParseException {

		test_name = Scenario;
		test_description = "ReportsV2 - VerifyReportsV2DailyFlashUI<br>"
				+ "<a href='' target='_blank'>"
				+ "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Daily Flash report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		ReportsV2 report = new ReportsV2();
		Admin admin = new Admin();
		Properties prop = new Properties();
		CPReservationPage reservationPage = new CPReservationPage();
		Folio folio = new Folio();
		Groups group = new Groups();
		Account accountPage = new Account();
		Elements_Reservation elementReservation = new Elements_Reservation(driver);
		
		ledgerAccounts = report.getLedgerInputsAndValues(driver, LedgerAccount, LedgerValue);

		String Incidentals = "", RoomCharges = "", Taxes = "", Fees = "";
		//String accountType = "";
		String Adults = "2";
		String Children = "1";
		String PromoCode = "";
		String IsGuesProfile = "No", TravelAgent ="";

		if (LedgerAccount.split("\\|").length == 1) {
			switch (LedgerAccount) {
			case "Incidentals":
			case "Incidental":
				Incidentals = ledgerAccounts.get(LedgerAccount);
				break;

			case "Room Charges":
			case "Room Charge":
				RoomCharges = ledgerAccounts.get(LedgerAccount);
				break;

			case "Tax":
			case "Taxes":
				Taxes = ledgerAccounts.get(LedgerAccount);
				break;

			case "Fee":
			case "Fees":
				Fees = ledgerAccounts.get(LedgerAccount);
				break;

			}
		} else {
			String[] ledgers = LedgerAccount.split("\\|");

			for (int i = 0; i < ledgers.length; i++) {
				switch (ledgers[i]) {
				case "Incidentals":
				case "Incidental":
					Incidentals = ledgerAccounts.get(ledgers[i]);
					break;

				case "Room Charges":
				case "Room Charge":
					RoomCharges = ledgerAccounts.get(ledgers[i]);
					break;

				case "Tax":
				case "Taxes":
					Taxes = ledgerAccounts.get(ledgers[i]);
					break;

				case "Fee":
				case "Fees":
					Fees = ledgerAccounts.get(ledgers[i]);
					break;

				}
			}
		}

		app_logs.info("Incidentals: " + Incidentals);
		app_logs.info("Room Charges: " + RoomCharges);
		app_logs.info("Taxes: " + Taxes);
		app_logs.info("Fees: " + Fees);

		Double depositAmount = 0.0;
		Double paidDeposit = 0.0;
		String reservation = null;
		String status = null;
		String account = "";
		
		String currencySymbal = "";

		String AmountIncidentals = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		String AmountRoomCharges = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		String AmountTaxes = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		String AmountFees = Utility.generateRandomNumberWithGivenNumberOfDigits(2);

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

		// String TripSummaryRoomCharges = null, TripSummaryTaxes = null,
		// TripSummaryIncidentals = null, ripSummaryTripTotal = null, TripSummaryPaid =
		// null, TripSummaryBalance = null;
		double TripSummaryRoomCharges = 0, TripSummaryTaxes = 0, TripSummaryIncidentals = 0, TripSummaryTripTotal = 0,
				TripSummaryPaid = 0, TripSummaryBalance = 0;
		double roomChargeAmount = 0.00;

		// String FolioRoomCharges = null, FolioTaxes = null, FolioFees = null,
		// FolioIncidentals = null, FolioTripTotal = null, FolioPaid = null,
		// FolioBalance = null;
		double FolioRoomCharges = 0, FolioTaxes = 0, FolioFees = 0, FolioIncidentals = 0, FolioTripTotal = 0,
				FolioPaid = 0, FolioBalance = 0, TripRoomCharge = 0, TripTax = 0;
		
		String resNumberPayment = null;
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
					
					switch (guestStatus) {
					case "Expected Total Departure":
					case "Pending Departure (To Be Checked Out)":
						CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(-1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
						CheckOutDate = Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern");
						break;
					case "Current Staying OverNight":
						CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(-1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
						break;
					default:
						break;
					}
					
					
					
				} else {

					// Get CheckIN and Checkout Date
					if (CheckInDate.equalsIgnoreCase("NA") || CheckInDate.isEmpty()
							|| CheckInDate.equalsIgnoreCase("")) {
						checkInDates.clear();
						checkOutDates.clear();
						
						switch (guestStatus) {
						case "Expected Total Departure":
						case "Pending Departure (To Be Checked Out)":
							for (int i = 1; i <= numberOfRooms; i++) {

								app_logs.info("Loop" + i);
								checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(-1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy"));
								checkOutDates.add(Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern"));
								app_logs.info(checkInDates);
								app_logs.info(checkOutDates);
								
							}
							break;
						case "Current Staying OverNight":
							for (int i = 1; i <= numberOfRooms; i++) {

								app_logs.info("Loop" + i);
								checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(-1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy"));
								checkOutDates.add(
										Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy"));
								app_logs.info(checkInDates);
								app_logs.info(checkOutDates);

							}
							break;
						default:
							for (int i = 1; i <= numberOfRooms; i++) {

								app_logs.info("Loop" + i);
								checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern"));
								checkOutDates.add(
										Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy"));
								app_logs.info(checkInDates);
								app_logs.info(checkOutDates);

							}
							break;
						}

					} else {
						checkInDates = Utility.splitInputData(CheckInDate);
						checkOutDates = Utility.splitInputData(CheckOutDate);
					}
					// CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
					// CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);

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
			// TaskDueon = CheckOutDate;

			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			// date = Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yy");
			// app_logs.info(date);

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
			
			loginReportsV2(driver);
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
		
/*		try {
			String[] ac = LedgerAccount.split("\\|");
			app_logs.info("Ledger Accounts: "+ac);
			ArrayList<String> acc = new ArrayList<>();
			for (int i = 0; i < ac.length; i++) {
				acc.add(ac[i]);
			}
			app_logs.info("Ledger Accounts: "+acc);
			
			if (acc.contains("Taxes") || LedgerAccount.equalsIgnoreCase("Taxes")) {
				app_logs.info("Tax is given as Ledger account");
				nav.setup(driver); 
				report.navigateToTaxesAndFees(driver);
				boolean taxVal = report.checkIfTaxRowExists(driver);
				if (taxVal) {
					ArrayList<String> TaxItems = report.getTaxLedgerAccounts(driver);
					app_logs.info("Tax Items: " + TaxItems);
					String taxValue = report.getTaxAmountFromTaxLedgerAccount(driver); // 20|USD or 20|percent
					app_logs.info("Tax Value: " + taxValue);
					String taxLedger = report.getTaxLedgerAccountSelectedOption(driver);
					app_logs.info("Tax Ledger: " + taxLedger);
				}else {
					report.createTaxItem(driver, "test", "test", "test", "Room Tax", "USD", "10", "Room Charge");
				}
			}

			if (acc.contains("Fees") || LedgerAccount.equalsIgnoreCase("Fees")) {
				report.setup(driver);
				report.navigateToTaxesAndFees(driver);
				boolean feesval = report.checkIfFeesRowExists(driver);
				if (feesval) {
					String feeValue = report.getFeeAmountFromFeeLedgerAccount(driver);
					// 20|USD|pernight/stay 
					app_logs.info("Fee Value: "+feeValue); 
					String feeLedger = report.getFeeLedgerAccountSelectedOption(driver);
					System.out.println("Fee Ledger: " + feeValue);
				}else {
					report.createFeeItem(driver, "TestFee", "TestFee", "TestFee", "Fee Adjustment", "USD", "10", "per night");
					
				}
				report.setup(driver);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Client details", "Client Info",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
*/		
/*		//Getting client details
		try {
			app_logs.info("==== Getting Client details =====");
			nav.admin(driver);
			nav.Clientinfo(driver);
			
			admin.clickClientName(driver);
			admin.clickClientOption(driver);
			
			startDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
			clientTimeZone = admin.getClientTimeZone(driver, test_steps);
			dFormat = admin.getClientDateFormat(driver);
			
			app_logs.info("Client Start day of the week: "+startDayOfWeek);
			app_logs.info("Client time Zone: "+clientTimeZone);
			app_logs.info("Client Date format: "+dFormat);
			
			switch (clientTimeZone) {
			case "(GMT-05:00) Eastern Time (US and Canada)":
				clientTimeZone = "US/Eastern";
				break;
				
			case "(GMT-06:00) Central Time (US and Canada":
				clientTimeZone = "US/Central";
				break;
				
			case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
				clientTimeZone = "Europe/London";
				break;

			default:
				break;
			}
			
			if (dFormat.equalsIgnoreCase("USA")) {
				dateFormat = "MMM dd, YYYY, EEEE";
			}else if (dFormat.equalsIgnoreCase("International")) {
				dateFormat = "dd MMM, YYYY, EEEE";
			}
			app_logs.info("Client Start day of the week: "+startDayOfWeek);
			app_logs.info("Client time Zone: "+clientTimeZone);
			app_logs.info("Client Date Format: "+dateFormat);
			
			if (isTaxExempt.equals("Tax Exempt")) {
				report.setup(driver);
				propertyName = driver.findElement(By.xpath("//p[@class='propertySelectName']")).getText();
				app_logs.info("Property Name: "+propertyName);
				nav.Properties(driver);
				prop.SearchProperty_Click(driver, propertyName, test_steps);
				prop.PropertyOptions(driver, test_steps);
				// propertyName = prop.getPropertyName(driver, test_steps);
				//propertyName = prop.getProperty(driver, test_steps);
				
				prop.LongStay(driver, propertyName, daysTaxExempt, test_steps);

			}
			
			nav.Reservation_Backward(driver);
					
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Client details", "Client Info",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
*/		
		
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
		
		
		//
		try {
			test_steps.add("========= Getting Report data before Reservation =========");
			//report.clickBreakOutTaxExemptRevenueDailyFlash(driver, test_steps);
			//report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			if (dateRange.isEmpty()) {
				report.selectDateDailyFlash(driver, dateEffective, test_steps);
			}else {
				report.selectDateRange(driver, dateRange, test_steps);
			}
			report.expandGivenAdvancedOptions(driver, "Break Out Tax-Exempt Revenue", test_steps);
			report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			report.clickOnRunReport(driver);
			
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 10; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				
			}
			
			beforeRevenueDetails = report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps);
			beforePaymentDetails = report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps);
			beforeNetChangesDetails = report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps);
			beforeGuestCountDetails = report.getGuestCountStatisticsTableData(driver, test_steps);
			
			app_logs.info("Revenue Details Before: "+beforeRevenueDetails);
			app_logs.info("Payment Details Before: "+beforePaymentDetails);
			app_logs.info("Net Changes Details Before: "+beforeNetChangesDetails);
			app_logs.info("Guest Count Details Before: "+beforeGuestCountDetails);
//			report.validateDailyFlashReportRevenueTypes(driver, beforeRevenueDetails, beforeRevenueDetails, ledgerAccounts, ledgerAmounts, folioItemValues, numberOfRooms, test_steps);
			
//			report.validateDailyFlashReportPaymentsMethodTypes(driver, beforePaymentDetails, beforePaymentDetails, ledgerAccounts, ledgerAmounts, folioItemValues, numberOfRooms, test_steps);
			
			
			
//			app_logs.info("Data: "+report.getPropertyPerformanceDataWithType(driver, "Room Charges", test_steps));
//			app_logs.info("Rooms Revenue Data: "+report.getPropertyPerformanceDataWithTable(driver, "Rooms Revenue", test_steps));
//			app_logs.info("Revenue Types Data: "+report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps));
//			app_logs.info("Payments Method Types Data: "+report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps));
//			app_logs.info("Net Changes Data: "+report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps));
//			app_logs.info("Property Statistics Data: "+report.getPropertyPerformanceDataWithTable(driver, "Property Statistics", test_steps));
//			app_logs.info("Guest Count Statistics Data: "+report.getGuestCountStatisticsTableData(driver, test_steps));
			
			driver.close();
			// Utility.switchTab(driver, 0);
			Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);

			nav.Reservation_Backward_3(driver);
			app_logs.info("Back to reservation page");
			
			
			//RetryFailedTestCases.count = Utility.reset_count;
			//Utility.AddTest_IntoReport("Validating ", test_description, test_category, test_steps);
			
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

						// accountPage.closeAccountTab(driver);
						// nav.cpReservationBackward(driver);
						// reservationPage.click_NewReservation(driver, test_steps);

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

				}

				else if (accountType.equals("Gift Certificate")) {

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

						// report.AccountSave(driver, test, accountName, test_steps);
						// accountPage.Save(driver);
						accountPage.Save(driver, test, test_steps);

						Wait.wait5Second();

						// accountPage.closeAccountTab(driver);
						// nav.cpReservationBackward(driver);
						// reservationPage.click_NewReservation(driver, test_steps);

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
					ArrayList<String> TravelAgentItems = new ArrayList<>();
					int TravelAgetItemValue;

					nav.Accounts(driver);

/*					if (!TravelAgentCommission.isEmpty()) {

						accountPage.ClickTravelAgentItem(driver);

						if (accountPage.chekcTravelAgentItemAvailability(driver, test_steps)) {
							TravelAgentItems = accountPage.getAssociatedTravelAgentItems(driver, test_steps);
							TravelAgetItemValue = accountPage.getTravelAgentCommissionValue(driver, test_steps);

							app_logs.info("Travel agent items: " + TravelAgentItems);
							app_logs.info("Travel agent Value: " + TravelAgetItemValue);

						} else {
							accountPage.CreateNewTravelAgentItem(driver, "ItemName", "DisplayName", "Description", "10",
									"Category", "Room Charge");
						}

					}*/

					try {
						// nav.Accounts(driver);
						test_steps.add("========== Creating Travel Agent account ==========");
						app_logs.info("========== Creating Travel Agent account ==========");
						// accountName = accountName + Utility.generateRandomString();

						// accountPage.clickOnNewAccountButton(driver, test_steps, accountType);
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

				}

				else if (accountType.equals("Unit Owners")) {

					// If Distribution method is given in Select inputs
/*					if (!DistributionMethod.isEmpty()) {

						nav.setup(driver);
						nav.LedgerAccounts(driver);

						la.NewAccountbutton(driver);
						String acName = "Unit" + Utility.fourDigitgenerateRandomString();
						String category;

						la.LedgerAccountDetails(driver, acName, "Test", "", "Unit Expenses", "Active");
						la.SaveLedgerAccount(driver);

						nav.Accounts(driver);
						nav.UnitownerAccount(driver);

						if (UnitRevenues.isEmpty()) {
							category = Utility.generateRandomString();
						} else {
							category = UnitRevenues;
						}
						unitOwnerItems = accountPage.getAssociatedUnitOwnerItemsListAndValue(driver, acName, category,
								test_steps);

					}
*/
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

				}

				else if (accountType.equals("Corporate/Member Accounts")) {
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
				}

				else if (accountType.equals("Group")) {

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

						

						// reservationPage.click_NewReservation(driver, test_steps);
						// guest1 = MrbGuestFirstName.split("|")[1] + MrbGuestLastName.split("|")[1];
						// guest2 = MrbGuestFirstName.split("|")[2] + MrbGuestLastName.split("|")[2];

						report.mrbReservation(driver, CheckInDate, CheckOutDate, mrbAdults, mrbChildren, mrbRateplan,
								PromoCode, IsSplitRes, mrbRoomClass, account, mrbSalutation, mrbGuestFirstName,
								mrbGuestLastName, mrbPhoneNumber, alternativePhone, mrbEmail, accountType, address1,
								address2, address3, city, country, state, postalCode, getRoomClasses, PaymentType,
								CardNumber, NameOnCard, CardExpDate, accountName, resNumberPayment, isTaxExempt,
								TaxExemptID, TravelAgent, marketSegment, referral, reservationNumber, numberOfRooms,
								reservationNumbers, arrivalDates, status, roomCost, test_steps);
						reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
						reservationPage.click_Folio(driver, test_steps);

						roomChargeAmount = report.getFolioAmountExcludingCurrency(driver,
								driver.findElement(
										By.xpath("(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
										.getText());
						app_logs.info("Room Charge: " + roomChargeAmount);
						
						
						
						FolioRoomCharges = 0;
						FolioIncidentals = 0;
						FolioTaxes = 0;
						FolioFees = 0;

						FolioTripTotal = 0;
						FolioPaid = 0;
						FolioBalance = 0;

						test_steps.add("===== Adding Folio items to MRB Reservation ======");
						try {
							test_steps.add("========== Adding Line Items ==========");
							app_logs.info("========== Adding Line Items ==========");

							folio.folioTab(driver);
							test_steps.add("Clicked Folio Tab");
							app_logs.info("Clicked Folio Tab");

							roomChargeAmount = report.getFolioAmountExcludingCurrency(driver,
									driver.findElement(
											By.xpath("(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
											.getText());
							app_logs.info("Room Charge: " + roomChargeAmount);
							
							
							report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, "", AmountTaxes, "", AmountFees, "", test_steps);
							
							
							//outBoundAmount = folio.postedLastLineItemAndGetAmount(driver, test_steps);
							outBoundAmount = folio.postedFirstLineItemAndGetAmount(driver, test_steps);
							
							try {
								folio.CheckDisplayVoidItems(driver, test_steps);
							}catch(Exception e) {
								folio.CheckboxDisplayVoidItem(driver, true);
							}

							// String strItems = "//table[contains(@class,'table-foliogrid')]//tr";

							String strItems = "//table[contains(@class,'table-foliogrid')]//tr/td[7]/span";
							String strDesc = "//table[contains(@class,'table-foliogrid')]//tr/td[8]/a";
							String strAmount = "//table[contains(@class,'table-foliogrid')]//tr/td[11]/span";

							List<WebElement> items = driver.findElements(By.xpath(strItems));
							List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
							List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

							for (int i = 0; i < items.size(); i++) {
								// List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
								// itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(),
								// cells.get(7).findElement(By.tagName("a")).getText());
								itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
								folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));

							}

							FolioRoomCharges = report.getFolioAmountExcludingCurrency(driver,
									reservationPage.get_RoomChargeWithCurrency(driver, test_steps));
							FolioIncidentals = report.getFolioAmountExcludingCurrency(driver,
									reservationPage.get_InceidentalsWithCurrency(driver, test_steps));
							try {
								FolioTaxes = report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_TaxesWithCurrency(driver, test_steps));
							} catch (Exception e) {
								app_logs.info("Taxes amount not available");
							}

							try {
								FolioFees = report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_FeesWithCurrency(driver, test_steps));
							} catch (Exception e) {
								app_logs.info("Fees amount not available");
							}

							FolioTripTotal = report.getFolioAmountExcludingCurrency(driver,
									reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
							FolioPaid = report.getFolioAmountExcludingCurrency(driver,
									reservationPage.get_PaymentsWithCurrency(driver, test_steps));
							FolioBalance = report.getFolioAmountExcludingCurrency(driver,
									reservationPage.get_BalanceWithCurrency(driver, test_steps));

							app_logs.info("FolioRoomCharges " + FolioRoomCharges);
							app_logs.info("FolioIncidentals " + FolioIncidentals);
							app_logs.info("FolioTaxes " + FolioTaxes);
							app_logs.info("FolioFees " + FolioFees);

						} catch (Exception e) {
							if (Utility.reTry.get(test_name) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems",
										driver);
							} else {

								Assert.assertTrue(false);
							}
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {

							app_logs.info("Before folio loop");

							for (int k = 2; k <= numberOfRooms; k++) {
								app_logs.info("Entered into folio loop");
								reservationPage.mrbChangeFolio(driver, k, test_steps);
								test_steps.add("========== Adding Line Items ==========");
								app_logs.info("========== Adding Line Items ==========");

								Wait.wait5Second();

								
								//report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, "", test_steps);
								report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, "", AmountTaxes, "", AmountFees, "", test_steps);

								
								
								
								
								
								
								try {
									folio.CheckDisplayVoidItems(driver, test_steps);
								}catch(Exception e) {
									folio.CheckboxDisplayVoidItem(driver, true);
								}

								FolioRoomCharges = FolioRoomCharges + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_RoomChargeWithCurrency(driver, test_steps));
								FolioIncidentals = FolioIncidentals + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_InceidentalsWithCurrency(driver, test_steps));
								try {
									FolioTaxes = FolioTaxes + report.getFolioAmountExcludingCurrency(driver,
											reservationPage.get_TaxesWithCurrency(driver, test_steps));
								} catch (Exception e) {
									app_logs.info("Taxes amount not available");
								}

								try {
									FolioFees = FolioFees + report.getFolioAmountExcludingCurrency(driver,
											reservationPage.get_FeesWithCurrency(driver, test_steps));
								} catch (Exception e) {
									app_logs.info("Fees amount not available");
								}

								ledgerAmounts.put("Room Charges", FolioRoomCharges);
								ledgerAmounts.put("Incidentals", FolioIncidentals);
								ledgerAmounts.put("Taxes", FolioTaxes);
								ledgerAmounts.put("Fees", FolioFees);

								app_logs.info("Ledger Amounts: " + ledgerAmounts);

								FolioTripTotal = FolioTripTotal + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
								FolioPaid = FolioPaid + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_PaymentsWithCurrency(driver, test_steps));
								FolioBalance = FolioBalance + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_BalanceWithCurrency(driver, test_steps));

								app_logs.info("FolioRoomCharges " + FolioRoomCharges);
								app_logs.info("FolioIncidentals " + FolioIncidentals);
								app_logs.info("FolioTaxes " + FolioTaxes);
								app_logs.info("FolioFees " + FolioFees);
								app_logs.info("FolioTripTotal " + FolioTripTotal);

							}

						} catch (Exception e) {
							if (Utility.reTry.get(test_name) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems",
										driver);
							} else {

								Assert.assertTrue(false);
							}
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						
						

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

				}

				else {
					test_steps.add("==== Creating Single Reservation ====");
					try {

						report.singleReservation(driver, CheckInDate, CheckOutDate, Adults, Children, Rateplan,
								PromoCode, RoomClass, depositAmount, salutation, guestFirstName, guestLastName,
								phoneNumber, alternativePhone, email, account, accountType, address1, address2,
								address3, city, country, state, postalCode, IsGuesProfile, resNumberPayment,
								PaymentType, CardNumber, NameOnCard, CardExpDate, accountName, isTaxExempt, TaxExemptID,
								marketSegment, referral, reservationNumber, reservationNumbers, status, resStatus, roomNumber,
								test_steps);
						reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
						reservationPage.click_Folio(driver, test_steps);

						roomChargeAmount = report.getFolioAmountExcludingCurrency(driver,
								driver.findElement(
										By.xpath("(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
										.getText());
						app_logs.info("Room Charge: " + roomChargeAmount);

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
					
					
					try {
						test_steps.add("========== Adding Line Items ==========");
						app_logs.info("========== Adding Line Items ==========");

						folio.folioTab(driver);
						test_steps.add("Clicked Folio Tab");
						app_logs.info("Clicked Folio Tab");

						//report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);
						report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, "", AmountTaxes, "", AmountFees, "", test_steps);
						
						//outBoundAmount = folio.postedLastLineItemAndGetAmount(driver, test_steps);
						outBoundAmount = folio.postedFirstLineItemAndGetAmount(driver, test_steps);
						
						FolioRoomCharges = report.getFolioAmountExcludingCurrency(driver,
								reservationPage.get_RoomChargeWithCurrency(driver, test_steps));
						FolioIncidentals = report.getFolioAmountExcludingCurrency(driver,
								reservationPage.get_InceidentalsWithCurrency(driver, test_steps));
						try {
							FolioTaxes = report.getFolioAmountExcludingCurrency(driver,reservationPage.get_TaxesWithCurrency(driver, test_steps));
						} catch (Exception e) {
							app_logs.info("Taxes amount not available");
						}

						try {
							FolioFees = report.getFolioAmountExcludingCurrency(driver,
									reservationPage.get_FeesWithCurrency(driver, test_steps));
						} catch (Exception e) {
							app_logs.info("Fees amount not available");
						}

						FolioTripTotal = report.getFolioAmountExcludingCurrency(driver,
								reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
						FolioPaid = report.getFolioAmountExcludingCurrency(driver,
								reservationPage.get_PaymentsWithCurrency(driver, test_steps));
						FolioBalance = report.getFolioAmountExcludingCurrency(driver,
								reservationPage.get_BalanceWithCurrency(driver, test_steps));

						ledgerAmounts.put("Room Charges", FolioRoomCharges);
						ledgerAmounts.put("Incidentals", FolioIncidentals);
						ledgerAmounts.put("Taxes", FolioTaxes);
						ledgerAmounts.put("Fees", FolioFees);

						app_logs.info("Ledger Amounts: " + ledgerAmounts);

						app_logs.info("FolioRoomCharges " + FolioRoomCharges);
						app_logs.info("FolioIncidentals " + FolioIncidentals);
						app_logs.info("FolioTaxes " + FolioTaxes);
						app_logs.info("FolioFees " + FolioFees);
						app_logs.info("FolioTripTotal " + FolioTripTotal);

					} catch (Exception e) {
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems",
									driver);
						} else {

							Assert.assertTrue(false);
						}
					} catch (Error e) {
						test_steps.add(e.toString());
					}

					
					
				}

				
			}else {
				
				
				Double amountAllIncidentals = 0.0, amountAllRoomCharges = 0.0, amountAllTaxes = 0.0,
						amountAllFees = 0.0;

				try {
					test_steps.add("========== Adding Line Items ==========");
					app_logs.info("========== Adding Line Items ==========");

					// folio.folioTab(driver);
					accountPage.ClickFolio(driver);

					test_steps.add("Clicked Folio Tab");
					app_logs.info("Clicked Folio Tab");

					if (!Incidentals.isEmpty()) {
						if (Incidentals.split(",").length == 1) {
							accountPage.addLineItems(driver, Incidentals, AmountIncidentals, test_steps);
							accountPage.Commit(driver);
							Wait.wait3Second();
							app_logs.info("Incidentals - " + Incidentals + " - " + AmountIncidentals + " added");
							test_steps.add("Incidentals - " + Incidentals + " - " + AmountIncidentals + " added");

							amountAllIncidentals = Double.parseDouble(AmountIncidentals);

							accountPage.Save(driver);

						} else {
							String[] inc = Incidentals.split(",");
							for (int i = 0; i < inc.length; i++) {
								accountPage.addLineItems(driver, inc[i], AmountIncidentals, test_steps);
								accountPage.Commit(driver);
								Wait.wait2Second();
								// folio.clickAddLineItemButton(driver);
								// folio.AddFolioLineItem(driver, inc[i], AmountIncidentals);
								app_logs.info("Incidentals - " + inc[i] + " - " + AmountIncidentals + " added");
								test_steps.add("Incidentals - " + inc[i] + " - " + AmountIncidentals + " added");

								amountAllIncidentals = amountAllIncidentals + Double.parseDouble(AmountIncidentals);

							}
							accountPage.Save(driver);
						}
					}

					if (!RoomCharges.isEmpty()) {

						if (RoomCharges.split(",").length == 1) {
							accountPage.addLineItems(driver, RoomCharges, AmountRoomCharges, test_steps);
							accountPage.Commit(driver);
							Wait.wait2Second();
							// accountPage.Commit(driver, test);
							// folio.clickAddLineItemButton(driver);
							// folio.AddFolioLineItem(driver, RoomCharges, AmountRoomCharges);
							// driver.findElement(By.xpath("//span[text()='"+RoomCharges+"']/../../td[contains(@class,'changestatus')]")).click();
							app_logs.info("Room Charge - " + RoomCharges + " - " + AmountRoomCharges + " added");
							test_steps.add("Room Charge - " + RoomCharges + " - " + AmountRoomCharges + " added");

							amountAllRoomCharges = Double.parseDouble(AmountRoomCharges);

							accountPage.Save(driver);

						} else {
							String[] rc = RoomCharges.split(",");
							for (int i = 0; i < rc.length; i++) {

								accountPage.addLineItems(driver, rc[i], AmountRoomCharges, test_steps);
								accountPage.Commit(driver);
								Wait.wait2Second();
								// folio.clickAddLineItemButton(driver);
								// folio.AddFolioLineItem(driver, rc[i], AmountRoomCharges);
								// driver.findElement(By.xpath("//span[text()='"+rc[i]+"']/../../td[contains(@class,'changestatus')]")).click();
								app_logs.info("Room Charge - " + rc[i] + " - " + AmountRoomCharges + " added");
								test_steps.add("Room Charge - " + rc[i] + " - " + AmountRoomCharges + " added");

								amountAllRoomCharges = amountAllRoomCharges + Double.parseDouble(AmountRoomCharges);


							}
							accountPage.Save(driver);
						}
					}

					if (!Taxes.isEmpty()) {
						if (Taxes.split(",").length == 1) {

							accountPage.addLineItems(driver, Taxes, AmountTaxes, test_steps);
							accountPage.Commit(driver);
							Wait.wait2Second();
							app_logs.info("Taxes - " + Taxes + " - " + AmountTaxes + " added");
							test_steps.add("Taxes - " + Taxes + " - " + AmountTaxes + " added");

							amountAllTaxes = Double.parseDouble(AmountTaxes);

							accountPage.Save(driver);

						} else {
							String[] tax = Taxes.split(",");
							for (int i = 0; i < tax.length; i++) {
								accountPage.addLineItems(driver, tax[i], AmountTaxes, test_steps);
								accountPage.Commit(driver);
								Wait.wait2Second();
								app_logs.info("Taxes - " + tax[i] + " - " + AmountTaxes + " added");
								test_steps.add("Taxes - " + tax[i] + " - " + AmountTaxes + " added");

								amountAllTaxes = amountAllTaxes + Double.parseDouble(AmountTaxes);

							}
							accountPage.Save(driver);
						}
					}

					if (!Fees.isEmpty()) {
						if (Fees.split(",").length == 1) {
							accountPage.addLineItems(driver, Fees, AmountFees, test_steps);
							accountPage.Commit(driver);
							Wait.wait2Second();
							app_logs.info("Fees - " + Fees + " - " + AmountFees + " added");
							test_steps.add("Fees - " + Fees + " - " + AmountFees + " added");

							amountAllFees = Double.parseDouble(AmountFees);

							accountPage.Save(driver);
						} else {
							String[] fee = Fees.split(",");
							for (int i = 0; i < fee.length; i++) {
								accountPage.addLineItems(driver, fee[i], AmountFees, test_steps);
								accountPage.Commit(driver);
								Wait.wait2Second();
								app_logs.info("Fees - " + fee[i] + " - " + AmountFees + " added");
								test_steps.add("Fees - " + fee[i] + " - " + AmountFees + " added");

								amountAllFees = amountAllFees + Double.parseDouble(AmountFees);

							}
							accountPage.Save(driver);
						}
					}

					// folio.ClickSaveFolioButton(driver);
					
					//outBoundAmount = accountPage.postedFirstLineItemAndGetAmount(driver, test_steps);
					
					try {
						FolioTaxes = report.getFolioAmountExcludingCurrency(driver,
								reservationPage.get_TaxesWithCurrency(driver, test_steps));
					} catch (Exception e) {
						app_logs.info("Taxes amount not available");
					}

					try {
						FolioFees = report.getFolioAmountExcludingCurrency(driver,
								reservationPage.get_FeesWithCurrency(driver, test_steps));
					} catch (Exception e) {
						app_logs.info("Fees amount not available");
					}
					try {
						FolioTripTotal = accountPage.GetEndingBalance(driver);
					} catch (Exception e) {
						try {
							Elements_Accounts Account = new Elements_Accounts(driver);
							Utility.ScrollToElement(Account.Account_EndingBalance, driver);
							String Balance = Account.Account_EndingBalance.getText();
							System.out.print(Balance);
							FolioTripTotal = Float.parseFloat(Balance.substring(3, Balance.length() - 1));
							System.out.print(FolioTripTotal);
						} catch (Exception e1) {

						}

					}

					app_logs.info("FolioRoomCharges " + amountAllRoomCharges);
					app_logs.info("FolioIncidentals " + amountAllIncidentals);
					app_logs.info("FolioTaxes " + amountAllTaxes);
					app_logs.info("FolioFees " + amountAllFees);
					app_logs.info("FolioTripTotal " + FolioTripTotal);

					// String strItems = "//table[contains(@class,'table-foliogrid')]//tr";
					try {
						folio.CheckDisplayVoidItems(driver, test_steps);
					}catch(Exception e) {
						try {
							folio.CheckboxDisplayVoidItem(driver, true);
						}catch(Exception e1) {
							
						}
					}

					String strItems = "//table[contains(@class,'table-foliogrid')]//tr/td[7]/span";
					String strDesc = "//table[contains(@class,'table-foliogrid')]//tr/td[8]/a";
					String strAmount = "//table[contains(@class,'table-foliogrid')]//tr/td[11]/span";

					List<WebElement> items = driver.findElements(By.xpath(strItems));
					List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
					List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

					for (int i = 0; i < items.size(); i++) {
						// List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
						// itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(),
						// cells.get(7).findElement(By.tagName("a")).getText());
						itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
						folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));
					}
					app_logs.info("Item Descreption: " + itemDescription);
					app_logs.info("Foio Item values: " + folioItemValues);

//					ledgerAmounts.put("Room Charge", FolioRoomCharges);
//					ledgerAmounts.put("Incidental", FolioIncidentals);
//					ledgerAmounts.put("Taxes", FolioTaxes);
//					ledgerAmounts.put("Fees", FolioFees);
					ledgerAmounts.put("Room Charges", amountAllRoomCharges);
					ledgerAmounts.put("Incidentals", amountAllIncidentals);
					ledgerAmounts.put("Taxes", amountAllTaxes);
					ledgerAmounts.put("Fees", amountAllFees);

					app_logs.info("Ledger Amounts: " + ledgerAmounts);
					

				} catch (Exception e) {
					if (Utility.reTry.get(test_name) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems", driver);
					} else {

						Assert.assertTrue(false);
					}
				} catch (Error e) {
					test_steps.add(e.toString());
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
		

		
		
		// Payment
		try {
			
			if (isPayment.equalsIgnoreCase("Yes")) {
				//driver.navigate().refresh();
				if (Reservation.isEmpty()) {
					
					//accountPage.ClickFolio(driver);
					
					try {
						accountPage.cashPayment(driver, test, "10", test_steps);
					}catch (Exception e) {
						driver.navigate().refresh();
						Wait.wait5Second();
						accountPage.ClickFolio(driver);
						accountPage.cashPayment(driver, test, "10", test_steps);
					}
					
//					driver.findElement(By.xpath("(//button[contains(text(),'Pay')])[1]")).click();
//					driver.findElement(By.xpath("//select[contains(@class,'payment-method-dropdown')]")).click();
//					new Select(driver.findElement(By.xpath("//select[contains(@class,'payment-method-dropdown')]"))).selectByIndex(2);
//					driver.findElement(By.xpath("(//button[contains(text(),'Add')])[4]")).click();
//					driver.findElement(By.xpath("((//button[contains(text(),'Continue')]))[5]")).click();
//					driver.findElement(By.xpath("((//button[contains(text(),'Continue')]))[5]")).click();
				}else {
					reservationPage.clickOnDetails(driver);
					try {
						driver.findElement(By.xpath("(//button[contains(text(),'TAKE PAYMENT')])[1]")).click();
					} catch (Exception e) {
						Wait.wait5Second();
						driver.findElement(By.xpath("(//button[contains(text(),'TAKE PAYMENT')])[1]")).click();
					}

					app_logs.info("Clicked on Take Payment");
					// reservationPage.takePayment(driver, unitOwnerItems, PaymentType, CardNumber,
					// NameOnCard, CardExpDate, TakePaymentType, IsChangeInPayAmount,
					// ChangedAmountValue, IsSetAsMainPaymentMethod, AddPaymentNotes)
					reservationPage.payButtonClickInTakePayment(driver, test_steps, "" + FolioTripTotal/numberOfRooms + "", true,
							true);
				}

			}

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to change Status", testName, "ItemsStatus", driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// to change the Reservation status
		try {
			//reservationPage.changeReservationStatus(driver, resStatus, mrbStatus, reservationNumber, "", test_steps);
			
			switch (guestStatus) {
			case "Expected Total Departure":
			case "Pending Departure (To Be Checked Out)":
			case "Current Staying OverNight":
			case "Current In-House":
				reservationPage.inHouseReservation(driver);
				break;

			default:
				break;
			}
			
			//currentStatus = elementReservation.ReservationCurrentStatus.getText();
			
			if (!Reservation.isEmpty()) {
				currentStatus = reservationPage.getReservationStatusInDetailsSection(driver);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to change Reservation status", testName, "ReservationStatus",
						driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		try {
			if (!Reservation.isEmpty()) {
				try {
					reservationPage.click_Folio(driver, test_steps);
				}catch (Exception e) {
					driver.navigate().refresh();
					Wait.wait5Second();
					reservationPage.click_Folio(driver, test_steps);
				}
			}
			
			try {
				folio.CheckDisplayVoidItems(driver, test_steps);
			}catch(Exception e) {
				folio.CheckboxDisplayVoidItem(driver, true);
			}
			
			String strItems = "//table[contains(@class,'table-foliogrid')]//tr/td[7]/span";
			String strDesc = "//table[contains(@class,'table-foliogrid')]//tr/td[8]/a";
			String strAmount = "//table[contains(@class,'table-foliogrid')]//tr/td[11]/span";

			List<WebElement> items = driver.findElements(By.xpath(strItems));
			List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
			List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

			for (int i = 0; i < items.size(); i++) {
				// List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
				// itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(),
				// cells.get(7).findElement(By.tagName("a")).getText());
				itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
				folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));

			}
			
			app_logs.info("Items Description: "+itemDescription);
			app_logs.info("Items Values: "+folioItemValues);
			
			if (!Reservation.isEmpty()) {
				
				FolioTotalCharges = Double.parseDouble(folio.getTotalCharges(driver));
				FolioPayments = Double.parseDouble(folio.getpayment(driver));
				FolioBalance = Double.parseDouble(folio.getTotalBalance(driver).replaceAll("[^a-zA-Z0-9]", ""));
				
				folioBalances.put("Total Charges", FolioTotalCharges);
				folioBalances.put("Payments", FolioPayments);
				folioBalances.put("Balance", FolioBalance);
				
				app_logs.info("Folio Balances: "+folioBalances);
				
				if (currentStatus.equalsIgnoreCase("In-House")) {
					outBoundAmount = folioBalances.get("Total Charges");
					app_logs.info("OutBound amount: "+outBoundAmount);
				}
						
			}else {
				
				FolioBeginningBalance = accountPage.getBeginningBalance(driver, test_steps);
				FolioPayments = accountPage.getPayments(driver, test_steps);
				FolioNewCharges = accountPage.getNewCharges(driver, test_steps);
				FolioTaxesAccount = accountPage.getTaxes(driver, test_steps);
				FolioEndingBalance = accountPage.GetEndingBalance(driver, test_steps);
//				FolioAdvanceDepositBalance = accountPage.getAdvanceDepositBalance(driver, test_steps);
				
				app_logs.info("FolioBeginningBalance: "+FolioBeginningBalance);
				app_logs.info("FolioPayments: "+FolioPayments);
				app_logs.info("FolioNewCharges: "+FolioNewCharges);
				app_logs.info("FolioTaxesAccount: "+FolioTaxesAccount);
				app_logs.info("FolioEndingBalance: "+FolioEndingBalance);
//				app_logs.info("FolioAdvanceDepositBalance: "+FolioAdvanceDepositBalance);
				
				folioBalances.put("Beginning Balance", FolioBeginningBalance);
				folioBalances.put("Payments", FolioPayments);
				folioBalances.put("New Charges", FolioNewCharges);
				folioBalances.put("Taxes", FolioTaxesAccount);
				folioBalances.put("Ending Balance", FolioEndingBalance);
//				folioBalances.put("Advance Deposit Balance", FolioAdvanceDepositBalance);
			}
			
			app_logs.info("Folio Balances: "+folioBalances);
			
		}catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Folio details", testName, "Folio detailsS",
						driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		try {		
			if (!Reservation.isEmpty()) {
				roomCount = numberOfRooms;
				adultsCount = Integer.parseInt(Adults) * numberOfRooms;
				childCount = Integer.parseInt(Children) * numberOfRooms;
				guestCount = adultsCount + childCount;
				
				app_logs.info("Room Count: "+roomCount);
				app_logs.info("Guest Count: "+guestCount);
				app_logs.info("Adults: "+adultsCount);
				app_logs.info("Children: "+childCount);
			}
			
		}catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Guest details and Room count", testName, "ReservationStatus",
						driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		app_logs.info("Reservation completed at: " + java.time.LocalTime.now());
		test_steps.add("Reservation completed at: " + java.time.LocalTime.now());
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
			if (dateRange.isEmpty()) {
				report.selectDateDailyFlash(driver, dateEffective, test_steps);
			}else {
				report.selectDateRange(driver, dateRange, test_steps);
			}
			report.expandGivenAdvancedOptions(driver, "Break Out Tax-Exempt Revenue", test_steps);
			report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			report.clickOnRunReport(driver);
			
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 10; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				
			}
			
			afterRevenueDetails = report.getPropertyPerformanceDataWithTable(driver, "Revenue Types", test_steps);
			afterPaymentDetails = report.getPropertyPerformanceDataWithTable(driver, "Payments Method Types", test_steps);
			afterNetChangesDetails = report.getPropertyPerformanceDataWithTable(driver, "Net Changes", test_steps);
			afterGuestCountDetails = report.getGuestCountStatisticsTableData(driver, test_steps);
			
			app_logs.info("Revenue Details After: "+afterRevenueDetails);
			app_logs.info("Payment Details After: "+afterPaymentDetails);
			app_logs.info("Net Changes Details After: "+afterNetChangesDetails);
			app_logs.info("Guest Count Details After: "+afterGuestCountDetails);
			
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

			app_logs.info("Ledger Account: "+LedgerAccount);
			app_logs.info("Payment Method: "+PaymentMethod);
			
			if (guestStatus.isEmpty()) {
				if (!LedgerAccount.isEmpty()) {
					test_steps.add("========= Validating Revenue Type table =========");
					report.validateDailyFlashReportRevenueTypes(driver, beforeRevenueDetails, afterRevenueDetails, ledgerAccounts, ledgerAmounts, folioItemValues, numberOfRooms, test_steps);
					
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Daily Flash Report - Revenue Type table", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Daily Flash Report - Revenue Type table", "ReportV2 - Daily Flash Report",
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

			if (!PaymentMethod.isEmpty()) {
				test_steps.add("========= Validating Payment Method Type table =========");
				report.validateDailyFlashReportPaymentsMethodTypes(driver, PaymentMethod, beforePaymentDetails, afterPaymentDetails, ledgerAccounts, ledgerAmounts, folioItemValues, numberOfRooms, test_steps);

			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Daily Flash Report - Payment Method Type table", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Daily Flash Report - Payment Method Type table", "ReportV2 - Daily Flash Report",
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
			
			if (guestStatus.isEmpty()) {
				test_steps.add("========= Validating Net Changes table =========");
				
				if (Reservation.isEmpty()) {
					report.validateDailyFlashReportNetChangess(driver, beforeNetChangesDetails, afterNetChangesDetails, "Net Change In City Ledger", folioBalances, numberOfRooms, outBoundAmount, test_steps);

				}else {
					report.validateDailyFlashReportNetChangess(driver, beforeNetChangesDetails, afterNetChangesDetails, "Net Change In Guest Ledger", folioBalances, numberOfRooms, outBoundAmount, test_steps);

				}
				report.validateDailyFlashReportNetChangess(driver, beforeNetChangesDetails, afterNetChangesDetails, "Net Change In Advanced Deposits", folioBalances, numberOfRooms, outBoundAmount, test_steps);

				report.validateDailyFlashReportNetChangessTrialBalance(driver, afterRevenueDetails, afterPaymentDetails, afterNetChangesDetails, test_steps);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Daily Flash Report - Net Changes table", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Daily Flash Report - Net Changes table", "ReportV2 - Daily Flash Report",
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

			test_steps.add("========= Validating Guest Count Statistics table =========");
						
			if (!guestStatus.isEmpty()) {
				report.validateDailyFlashReportGuestCount(driver, beforeGuestCountDetails, afterGuestCountDetails, roomCount, guestCount, adultsCount, childCount, guestStatus, test_steps);

			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Daily Flash Report - Guest Count Statistics table", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Daily Flash Report - Guest Count Statistics table", "ReportV2 - Daily Flash Report",
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
		return Utility.getData("DailyFlashReport", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
	
}
