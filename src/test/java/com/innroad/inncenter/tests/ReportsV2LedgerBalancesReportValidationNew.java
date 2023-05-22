package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.record.formula.functions.If;
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
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_NewRoomClass;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2LedgerBalancesReportValidationNew extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String reservationNumber = null, guestName = null, roomNumber = null, date = null;
	String guest1 = null, guest2 = null;
	String propertyName = null;
	String currency = null, clientDateFormat = null;
	String startDayOfWeek;
	String clientTimeZone;
	String dateFormat, dFormat;
	String feeValue = null;
	ArrayList<String> feeValueList = new ArrayList<>();
	ArrayList<String> taxValueList = new ArrayList<>();
	double feeCalc = 0.0;
	double taxCalc = 0.0;

	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> getRoomClasses = new ArrayList<>();

	ArrayList<String> availableTypes = new ArrayList<>();
	HashMap<String, String> beforeLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> beforeDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> beforeDetailsOfAllLedgerCategories = new HashMap<>();

	HashMap<String, String> afterLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> afterDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> afterDetailsOfAllLedgerCategories = new HashMap<>();
	
	HashMap<String, String> afterLedgerCategoryDetailsNew = new HashMap<>();
	HashMap<String, String> afterDetailsOfGivenCategoryNew = new HashMap<>();
	HashMap<String, String> afterDetailsOfAllLedgerCategoriesNew = new HashMap<>();

	ArrayList<String> guestNames = new ArrayList<>();
	ArrayList<String> reservationNumbers = new ArrayList<>();
	ArrayList<String> arrivalDates = new ArrayList<>();
	ArrayList<String> dates = new ArrayList<>();
	ArrayList<String> itemDescriptions = new ArrayList<>();
	ArrayList<String> amountList = new ArrayList<>();

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	Account accountPage = new Account();
	CPReservationPage reservationPage = new CPReservationPage();
	Reservation res = new Reservation();

	Folio folio = new Folio();
	Groups group = new Groups();
	AdvGroups advgrp = new AdvGroups();
	LedgerAccount la = new LedgerAccount();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop = new Properties();
	Tapechart tc = new Tapechart();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateLedgerBalancesReport(String Scenario, String DateRange, String StartDate, String EndDate,

			String LedgerAccount, String LedgerValue,

			String ResType, String numberOfRoomsMRB, String CheckInDate, String CheckOutDate,  
			String Rateplan, String RoomClass, String PaymentType, String CardNumber, String IsTaxExempt, String TaxExemptID,
			String marketSegment, String referral,

			String accountTypeOptions, String itemStatuOptions, String IncludeDataFromUsers, String shiftTime,
			String TaxExemptLedgerItemsOption, String marketSegmentOption, String reservationStatusOptions,
			String mrbStatus, String referralsOption) throws Exception {

		test_name = Scenario;
		test_description = "Validate LedgerBalances Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Ledger Balances Report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		HashMap<String, String> ledgerAccounts = new HashMap<>();
		HashMap<String, Double> ledgerAmounts = new HashMap<>();
		HashMap<String, String> folioItemValues = new HashMap<>();
		HashMap<String, String> itemDescription = new HashMap<>();
		
		ledgerAccounts = report.getLedgerInputsAndValues(driver, LedgerAccount, LedgerValue);

		String Incidentals = "", RoomCharges = "", Taxes = "", Transfers = "", PaymentMethod = "", Fees = "",
				UnitExpenses = "", UnitRevenues = "", TravelAgentCommission = "", DistributionMethod = "",
				GiftCertificate = "", GiftCertificateRedeemed = "";
		String accountType = "";
		String Adults = "2";
		String Children = "1";
		String PromoCode = "";
		String IsGuesProfile = "No", TravelAgent ="";
		String processingMethod = null;

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
		
		if (PaymentType.equalsIgnoreCase("MC") || PaymentType.equalsIgnoreCase("Visa") || PaymentType.equalsIgnoreCase("Amex") || PaymentType.equalsIgnoreCase("Discover")) {
			processingMethod = "Automatic";
		}else {
			processingMethod = "Manual";
		}

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
		String phoneNumber = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String alternativePhone = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String email = "innroadguest@gmail.com";
		String country  = "United States";
		String state  = "Alaska";
		String NameOnCard  = "test";
		String CardExpDate  = "12/23";
		String IsSplitRes = "No";
		
		int numberOfRooms = 1;
		if (!numberOfRoomsMRB.isEmpty()) {
			numberOfRooms = Integer.parseInt(numberOfRoomsMRB);
		}else {
			numberOfRoomsMRB = "1";
		}

		String accountName = "Account" + Utility.generateRandomStringWithoutNumbers();
		String BlockName = "Block" + Utility.generateRandomStringWithoutNumbers();

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
		HashMap<String, String> unitOwnerItems = new HashMap<>();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIn, CheckOut Date
			if (CheckInDate.equalsIgnoreCase("NA") || CheckInDate.isEmpty() || CheckInDate.equalsIgnoreCase("")) {

				//if (!isMRB.equalsIgnoreCase("Yes")) {
				if (!ResType.equalsIgnoreCase("MRB")) {
					CheckInDate = Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern");
					CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
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
			Set<String> windows = driver.getWindowHandles();
			if (windows.size() > 1) {
				driver.close();
				Wait.wait1Second();
				Utility.switchTab(driver, 0);
			}
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
/*
		try {

			//Room Class creation if not

			ArrayList<String> RoomClassNames = new ArrayList<>(); 
			Elements_NewRoomClass element = new Elements_NewRoomClass(driver); 
			nav.Setup(driver);
			nav.RoomClass(driver);

			char ch = RoomClass.charAt(0); String str = "" + ch; str = str.toUpperCase();
			driver.findElement(By.xpath("//*[text()='"+str+"']")).click();
			element.downarrow2.click(); element.select100ItemsPerPage.click();

			List<WebElement> l = driver.findElements(By.xpath("//tr/td[3]/a"));

			for(WebElement e:l) { RoomClassNames.add(e.getText()); }
			app_logs.info("Room Classes: "+RoomClassNames);
			if(RoomClassNames.contains(RoomClass)) {
			System.out.println("Room Class exists"); }else {
			Utility.clickThroughAction(driver, element.newRoomClass);
			element.newRoomClassName.sendKeys(RoomClass);
			element.newRoomClassNameAbb.sendKeys("ABR"); element.maxAdults.sendKeys("4");
			element.maxPersons.sendKeys("8"); element.rooms.click();
			element.roomQuantity.sendKeys("150"); Utility.clickThroughAction(driver,
			element.rightIcon); element.firstRoom.sendKeys("1");
			Utility.clickThroughAction(driver, element.assignNumbers);
			Utility.clickThroughAction(driver, element.Done);
			Utility.clickThroughAction(driver, element.Publish); }


			//RatePlan Creation if not exist

			RatesGrid rateGrid = new RatesGrid(); NightlyRate nightlyRate = new
			NightlyRate(); nav.Inventory(driver, test_steps); nav.ratesGrid(driver);
			boolean israteplanExist = rateGrid.isRatePlanExist(driver, Rateplan,
			test_steps); System.out.println("israteplanExist="+israteplanExist);

			if (!israteplanExist) { 
				//Creating the Rate Plan //CREATE NEW NIGHTLY RATE
				PLAN rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan"); //ENTER
				RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION Rateplan = Rateplan +
				Utility.generateRandomString(); String FolioDisplayName = Rateplan +
				Utility.generateRandomString()+Utility.generateRandomString();
				nightlyRate.enterRatePlanName(driver, Rateplan, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, Rateplan+"Description",
				test_steps); nightlyRate.clickNextButton(driver, test_steps); //SELECT
				DISTRIBUTED CHANNELS nightlyRate.selectChannels(driver, "inncenter", true,
				test_steps); nightlyRate.clickNextButton(driver, test_steps); //SELECT ROOM
				CLASSES nightlyRate.selectRoomClassesWithDeli(driver, Rateplan, true,
				test_steps); nightlyRate.clickNextButton(driver, test_steps);
				//nightlyRate.selectRestrictionsWithDeli(driver, false, "Promo code", //
				false, "2", false, "2", false, "2",false, "2", "PromoCode", test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				//nightlyRate.selectPolicyWithDeli(driver, "Check-in type", "Check-in name",
				false, test_steps); nightlyRate.clickNextButton(driver, test_steps); //CREATE
				SEASON(directly clicking on create season)
				nightlyRate.clickCreateSeason(driver, test_steps);
				nightlyRate.clickCompleteChanges(driver, test_steps);
				nightlyRate.clickSaveAsActive(driver, test_steps); Wait.wait30Second();
				app_logs.info("=================== RATE PLAN CREATED ======================");
			}





			} catch (Exception e) { 
				e.printStackTrace(); 
				if (Utility.reTry.get(testName)== Utility.count) { RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category,
			test_steps); Utility.updateReport(e, "Failed to validate Include Data From",
			"Include Data From Validation", "ReportsV2", driver); } else {
			Assert.assertTrue(false); } } catch (Error e) { if
			(Utility.reTry.get(testName) == Utility.count) { RetryFailedTestCases.count =
			Utility.reset_count; Utility.AddTest_IntoReport(testName, test_description,
			test_category, test_steps); Utility.updateReport(e,
			"Failed to validate Include Data From", "Include Data From Validation",
			"ReportsV2", driver); } else { Assert.assertTrue(false); }

			} 


*/
		try {
			
			String[] ac = LedgerAccount.split("|");
			ArrayList<String> acc = new ArrayList<>();
			for (int i = 0; i < ac.length; i++) {
				acc.add(ac[i]);
			}
			
			if (acc.contains("Tax") || LedgerAccount.equalsIgnoreCase("Tax")) {
				app_logs.info("Tax is given as Ledger account");
				nav.setup(driver); 
				report.navigateToTaxesAndFees(driver);
				boolean taxVal = report.checkIfTaxRowExists(driver);
				if (taxVal) {
//					ArrayList<String> TaxItems = report.getTaxLedgerAccounts(driver);
//					app_logs.info("Tax Items: " + TaxItems);
//					String taxValue = report.getTaxAmountFromTaxLedgerAccount(driver); // 20|USD or 20|percent
//					app_logs.info("Tax Value: " + taxValue);
//					String taxLedger = report.getTaxLedgerAccountSelectedOption(driver);
//					app_logs.info("Tax Ledger: " + taxLedger);
					
					taxValueList = report.getTaxDetailsFromSetupTaxes(driver);
					app_logs.info("Tax details: "+taxValueList);
				} else {
					report.createTaxItem(driver, "test", "test", "test", "Room Tax", "USD", "10", "Room Charge");
					taxValueList = report.getTaxDetailsFromSetupTaxes(driver);
					app_logs.info("Tax details: "+taxValueList);
				}
			}

			if (acc.contains("Fee") || LedgerAccount.equalsIgnoreCase("Fee")) {
				report.setup(driver);
				report.navigateToTaxesAndFees(driver);
				boolean feesval = report.checkIfFeesRowExists(driver);
				if (feesval) {
					//feeValue = report.getFeeAmountFromFeeLedgerAccount(driver);
					feeValueList = report.getFeeDetailsFromSetupFees(driver);
					// 20|USD|pernight/stay 
					app_logs.info("Fee Value: "+feeValueList); 
//					String feeLedger = report.getFeeLedgerAccountSelectedOption(driver);
//					System.out.println("Fee Ledger: " + feeLedger);
				}else {
					report.createFeeItem(driver, "TestFee", "TestFee", "TestFee", "Fee Adjustment", "USD", "10", "per night");
					report.setup(driver);
					report.navigateToTaxesAndFees(driver);
					feeValueList = report.getFeeDetailsFromSetupFees(driver);
					app_logs.info("Fee Value: "+feeValueList); 
				}
				report.setup(driver);
			}
			
			nav.admin(driver);
			Wait.wait1Second();
			//nav.Clientinfo(driver);
			nav.navigateToClientinfo(driver);
			
			Admin admin = new Admin();
			admin.clickClientName(driver);
			admin.clickClientOption(driver);
			
			currency = admin.getDefaultClientCurrency(driver);
			
			if (currency.equalsIgnoreCase("USD ( $ ) ")) {
				currencySymbal = "$";
			}else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
				currencySymbal = "£";
			}			
			clientDateFormat = admin.getClientDateFormat(driver);

			app_logs.info("Curency: "+currencySymbal);
			app_logs.info("Client date Format: "+clientDateFormat);
			
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
				dateFormat = "MMM dd, YYYY";
			}else if (dFormat.equalsIgnoreCase("International")) {
				dateFormat = "dd MMM, YYYY";
			}
			app_logs.info("Client Start day of the week: "+startDayOfWeek);
			app_logs.info("Client time Zone: "+clientTimeZone);
			app_logs.info("Client Date Format: "+dateFormat);
			

/*			if (TaxExemptLedgerItemsOption.equals("Tax Exempt")) {
				report.setup(driver);
				propertyName = driver.findElement(By.xpath("//p[@class='propertySelectName']")).getText();
				app_logs.info("Property Name: "+propertyName);
				nav.Properties(driver);
				prop.SearchProperty_Click(driver, propertyName, test_steps);
				prop.PropertyOptions(driver, test_steps);
				// propertyName = prop.getPropertyName(driver, test_steps);
				//propertyName = prop.getProperty(driver, test_steps);
				
				prop.LongStay(driver, propertyName, dayTaxExempt, test_steps);

			}
*/
			nav.Reservation_Backward(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Taxes & Fees details", testName, "Navigate to Taxes & fees",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Taxes & Fees details", testName, "Navigate to Taxes & fees",
						driver);
			} else {

				Assert.assertTrue(false);
			}
		}


		try {

			// If payment method is Reservation - Creating a new Reservation
			if (PaymentMethod.equalsIgnoreCase("Reservation") || Transfers.equalsIgnoreCase("Reservation")) {

				reservationPage.click_NewReservation(driver, test_steps);
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, Adults);
				reservationPage.enter_Children(driver, test_steps, Children);
				// res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
				reservationPage.clickOnFindRooms(driver, test_steps);
				// reservationPage.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
				reservationPage.selectRoom(driver, test_steps, RoomClass, "Yes", account);
				depositAmount = reservationPage.deposit(driver, test_steps, "No", "");
				// depositAmount = reservationPage.deposit(driver, test_steps,
				// IsDepositOverride, DepositOverrideAmount);

				reservationPage.clickNext(driver, test_steps);
				reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
						phoneNumber, alternativePhone, email, account, accountType, address1, address2, address3, city,
						country, state, postalCode, IsGuesProfile);
				if ((account.equalsIgnoreCase("") || account.isEmpty())) {
					reservationPage.enter_PaymentDetails(driver, test_steps, "MC", CardNumber, NameOnCard, CardExpDate);
				}
				reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
				reservationPage.clickBookNow(driver, test_steps);
				try {
					resNumberPayment = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					app_logs.info("Reservation Number for Payment: " + resNumberPayment);
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				reservationPage.closeReservationTab(driver);

			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to create Reservation", testName, "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to create Reservation", testName, "Create Reservation", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		Set<String> inputs = ledgerAccounts.keySet();
		app_logs.info("Inputs: " + inputs);

		// ArrayList<String> options = (ArrayList<String>) ledgerAccounts.values();
		// app_logs.info("Options: "+options);

		try {

			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);

			if (!DateRange.isEmpty()) {
				report.selectDateRange(driver, DateRange, test_steps);
			} else {
				report.selectStartdate(driver, StartDate, test_steps);
				report.selectEnddate(driver, EndDate, test_steps);
			}

			report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);

			report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
			report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
			if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
				report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
			}
			report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps, TaxExemptLedgerItemsOption);
			report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
			report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps, reservationStatusOptions);
			report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

			report.clickOnRunReport(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		Elements_Reports reportElements = new Elements_Reports(driver);

		try {
			if (report.checkNoReportDataAvailable(driver, test_steps)) {
				app_logs.info("Report Genaration failed, got 'No Report Data available' toast message");
				test_steps.add("Report Genaration failed, got 'No Report Data available' toast message");
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			beforeLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);
			beforeDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);

			//HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();

			driver.close();
			// Utility.switchTab(driver, 0);
			Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);

			nav.Reservation_Backward_3(driver);
			app_logs.info("Back to reservation page");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get details after Run Report", testName,
						"Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get details after Run Report", testName,
						"Getting details after run Report", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		if (accountTypeOptions.isEmpty()) {
			// accountTypeOptions = "House Account,Group,Corporate/Member
			// Accounts,Reservations,Unit Owners,Gift Certificate,Travel Agent";
			accountTypeOptions = "Gift Certificate,Travel Agent,Group,Unit Owners,House Account,Corporate/Member Accounts,Reservations";
		}

		if (accountTypeOptions.split(",").length == 1) {
			app_logs.info("Account Type: " + accountTypeOptions);
			accountType = accountTypeOptions;

			if (accountTypeOptions.equalsIgnoreCase("Reservations")) {
				// Reservations

				//if (isMRB.equalsIgnoreCase("Yes")) {
				if (ResType.equalsIgnoreCase("MRB")) {
					try {
						reservationPage.click_NewReservation(driver, test_steps);
					} catch (Exception e) {

					}

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

						guestNames.clear();
						for (int i = 0; i < numberOfRooms; i++) {
							String[] fNames = mrbGuestFirstName.split("\\|");
							String[] lNames = mrbGuestLastName.split("\\|");
							app_logs.info("Fanmes: " + fNames.length);
							app_logs.info("Lanmes: " + lNames.length);
							app_logs.info("Loop " + i);
							guestNames.add(fNames[i] + " " + lNames[i]);
						}

						app_logs.info("Guest Names: " + guestNames);
						
						arrivalDates.clear();
						for (int i = 0; i < numberOfRooms; i++) {
							String[] CheckInDates = CheckInDate.split("\\|");							
							
							if (clientDateFormat.equalsIgnoreCase("USA")) {
								arrivalDates.add(CheckInDates[i]);
							}else if (clientDateFormat.equalsIgnoreCase("International")) {
								arrivalDates.add(Utility.parseDate(CheckInDates[i], "MMM dd, yyyy", "dd MMM, yyyy"));
							}						
						}
						
						dates.clear();
						for (int i = 0; i < numberOfRooms; i++) {
							if (clientDateFormat.equalsIgnoreCase("USA")) {
								dates.add(Utility.getCurrentDate("MMM dd, yyyy"));
							}else if (clientDateFormat.equalsIgnoreCase("International")) {
								dates.add(Utility.getCurrentDate("dd MMM, yyyy"));
							}							
						}

						report.mrbReservation(driver, CheckInDate, CheckOutDate, mrbAdults, mrbChildren, mrbRateplan,
								PromoCode, IsSplitRes, mrbRoomClass, account, mrbSalutation, mrbGuestFirstName,
								mrbGuestLastName, mrbPhoneNumber, alternativePhone, mrbEmail, accountType, address1,
								address2, address3, city, country, state, postalCode, getRoomClasses, PaymentType,
								CardNumber, NameOnCard, CardExpDate, accountName, resNumberPayment, IsTaxExempt,
								TaxExemptID, TravelAgent, marketSegment, referral, reservationNumber, numberOfRooms,
								reservationNumbers, arrivalDates, status, roomCost, test_steps);
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

				else if (ResType.equalsIgnoreCase("Single") || ResType.equalsIgnoreCase("Copy")) {
					try {
						reservationPage.click_NewReservation(driver, test_steps);
					} catch (Exception e) {

					}
					test_steps.add("==== Creating Single Reservation ====");
					try {
						guestName = guestFirstName + " " + guestLastName;
						if (clientDateFormat.equalsIgnoreCase("USA")) {
							date = Utility.getCurrentDate("MMM dd, yyyy");
						}else if (clientDateFormat.equalsIgnoreCase("International")) {
							date = Utility.getCurrentDate("dd MMM, yyyy");
						}

						guestNames.clear();
						guestNames.add(guestName);
						arrivalDates.clear();
						if (clientDateFormat.equalsIgnoreCase("USA")) {
							arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
						}else {
							arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "dd MMM, yyyy"));
						}
						

						report.singleReservation(driver, CheckInDate, CheckOutDate, Adults, Children, Rateplan,
								PromoCode, RoomClass, depositAmount, salutation, guestFirstName, guestLastName,
								phoneNumber, alternativePhone, email, account, accountType, address1, address2,
								address3, city, country, state, postalCode, IsGuesProfile, resNumberPayment,
								PaymentType, CardNumber, NameOnCard, CardExpDate, accountName, IsTaxExempt, TaxExemptID,
								marketSegment, referral, reservationNumber, reservationNumbers, status, reservationStatusOptions, roomNumber,
								test_steps);
						reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
						reservationPage.click_Folio(driver, test_steps);
						
						if (IsTaxExempt.equalsIgnoreCase("Yes") && TaxExemptLedgerItemsOption.equalsIgnoreCase("Tax Exempt")) {
							reservationPage.closeReservationTab(driver);
							reservationPage.click_NewReservation(driver, test_steps);
							ArrayList<String> testList = new ArrayList<>();
							report.singleReservation(driver, CheckInDate, CheckOutDate, Adults, Children, Rateplan,
									PromoCode, RoomClass, depositAmount, salutation, guestFirstName, guestLastName,
									phoneNumber, alternativePhone, email, account, accountType, address1, address2,
									address3, city, country, state, postalCode, IsGuesProfile, resNumberPayment,
									PaymentType, CardNumber, NameOnCard, CardExpDate, accountName, "No", TaxExemptID,
									marketSegment, referral, reservationNumber, testList, status, reservationStatusOptions, roomNumber,
									test_steps);
							String reservationNumber2 = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
							reservationPage.closeReservationTab(driver);
							reservationPage.Search_ResNumber_And_Click(driver, reservationNumber);
							reservationPage.click_Folio(driver, test_steps);	
						}

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
				}else if (ResType.equalsIgnoreCase("Tapechart")) {
//					tc.TapChartLink(driver);
//					tc.tapeChartSearch(driver, CheckInDate, CheckOutDate, Adults, Children, Rateplan);
					nav.navigateToTapeChartFromReservations(driver);
					tc.waitForReservationToLoad(driver, test_steps);
					String checkIn = Utility.parseDate(CheckInDate, "dd/mm/yyyy", "mm/dd/yyyy");
					String checkOut = Utility.parseDate(CheckOutDate, "dd/mm/yyyy", "mm/dd/yyyy");
					tc.searchInTapechart(driver, test_steps, checkIn, checkOut, Adults, Children, Rateplan, PromoCode);
					tc.clickAvailableSlotWithOutRulePopupHandling(driver, test_steps, "DBR");

					tc.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
					report.enterDataForTapeChartReservation(driver, salutation, guestFirstName, guestLastName,
							phoneNumber, alternativePhone, email, account, accountType, address1, address2,
							address3, city, country, state, postalCode, IsGuesProfile, resNumberPayment,
							PaymentType, CardNumber, NameOnCard, CardExpDate, accountName, IsTaxExempt, TaxExemptID,
							marketSegment, referral, reservationNumber, reservationNumbers, status, reservationStatusOptions, roomNumber,
							test_steps);
					reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
					reservationPage.click_Folio(driver, test_steps);

					roomChargeAmount = report.getFolioAmountExcludingCurrency(driver,
							driver.findElement(
									By.xpath("(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
									.getText());
					app_logs.info("Room Charge: " + roomChargeAmount);
				}

				//if (isMRB.equalsIgnoreCase("Yes")) {
				if (ResType.equalsIgnoreCase("MRB")) {

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
						
						report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);
					
						// folio.ClickSaveFolioButton(driver);
						
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
							
							report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);

							// folio.ClickSaveFolioButton(driver);
							
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

							int numberOfNights = Integer.parseInt(Utility.differenceBetweenDates(CheckInDate, CheckOutDate));
							app_logs.info("number of nights: "+numberOfNights);
							//double feeCalc = report.feeCalculation(driver, feeValue, roomChargeAmount, numberOfNights);
							feeCalc = report.feeCalculationsAll(driver, feeValueList, roomChargeAmount, numberOfNights);
							app_logs.info("Fee calculation: "+feeCalc);
							double feeExp = feeCalc + Double.parseDouble(AmountFees);
							app_logs.info("Fee Exp: "+feeExp);
							if (FolioFees == feeExp) {
								app_logs.info("Fees mathed");
								app_logs.info("Folio Fees: "+FolioFees);
								app_logs.info("Fee Value Fees: "+feeExp);
								
							}else {
								app_logs.info("Fees not mathed");
								app_logs.info("Folio Fees: "+FolioFees);
								app_logs.info("Fee Value Fees: "+feeExp);
							}
							
							ledgerAmounts.put("Room Charge", FolioRoomCharges);
							ledgerAmounts.put("Incidental", FolioIncidentals);
							ledgerAmounts.put("Taxes", FolioTaxes);
							//ledgerAmounts.put("Fees", FolioFees);
							ledgerAmounts.put("Fees", feeExp*numberOfRooms);

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

				} else {

					try {
						test_steps.add("========== Adding Line Items ==========");
						app_logs.info("========== Adding Line Items ==========");

						folio.folioTab(driver);
						test_steps.add("Clicked Folio Tab");
						app_logs.info("Clicked Folio Tab");

						report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);

						// folio.ClickSaveFolioButton(driver);

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

						int numberOfNights = Integer.parseInt(Utility.differenceBetweenDates(CheckInDate, CheckOutDate));
						app_logs.info("number of nights: "+numberOfNights);
						//double feeCalc = report.feeCalculation(driver, feeValue, roomChargeAmount, numberOfNights);
						feeCalc = report.feeCalculationsAll(driver, feeValueList, roomChargeAmount, numberOfNights);
						app_logs.info("Fee calculation: "+feeCalc);
						double feeExp = feeCalc; //+ Double.parseDouble(AmountFees);
						app_logs.info("Fee Exp: "+feeExp);
						if (FolioFees == feeExp) {
							app_logs.info("Fees mathed");
							app_logs.info("Folio Fees: "+FolioFees);
							app_logs.info("Fee Value Fees: "+feeExp);
							
						}else {
							app_logs.info("Fees not mathed");
							app_logs.info("Folio Fees: "+FolioFees);
							app_logs.info("Fee Value Fees: "+feeExp);
						}
						
						taxCalc = report.taxCalculationsAll(driver, taxValueList, roomChargeAmount);
						app_logs.info("Tax calculation: "+taxCalc);
						double taxExp = taxCalc + Double.parseDouble(AmountTaxes);
						app_logs.info("Tax Exp: "+taxExp);
						if (FolioTaxes == taxExp) {
							app_logs.info("Tax mathed");
							app_logs.info("Folio Tax: "+FolioTaxes);
							app_logs.info("Tax Value: "+taxExp);
							
						}else {
							app_logs.info("Tax not mathed");
							app_logs.info("Folio Tax: "+FolioTaxes);
							app_logs.info("Tax Value: "+taxExp);
						}
						
						ledgerAmounts.put("Room Charge", FolioRoomCharges);
						ledgerAmounts.put("Incidental", FolioIncidentals);
						ledgerAmounts.put("Taxes", FolioTaxes);
						//ledgerAmounts.put("Fees", FolioFees);
						ledgerAmounts.put("Fees", feeExp*numberOfRooms);

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

				// Item status
				try {

					if (itemStatuOptions.split(",").length == 1) {
						if (itemStatuOptions.equals("Pending")) {
							// folio.clickAddLineItemButton(driver);
							// folio.AddFolioLineItem(driver, "Laundry", "50");
						} else if (itemStatuOptions.equals("Posted")) {
							// folio.clickAddLineItemButton(driver);
							// folio.AddFolioLineItem(driver, "Spa", "70");
							// driver.findElement(By.xpath("//span[text()='Spa']/../../td[contains(@class,'changestatus')]")).click();
							// reservationPage.checkinReservation(driver, test_steps);
//							reservationPage.CheckInButton(driver);
//							reservationPage.generatGuestReportToggle(driver, test_steps, "No");
//							reservationPage.CheckInConfrimButton(driver);
//					        Wait.wait2Second();

						}
						// driver.findElement(By.xpath("(//button[text()='Save'])[9]")).click();
					} else {
						System.out.println("Enter only one option for Item status");
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

				// Payment
				try {

//					TripSummaryRoomCharges = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps)); 
//					//TripRoomCharge = report.getFolioAmountExcludingCurrency(driver, TripSummaryRoomCharges);
//					TripSummaryTaxes = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTaxesWithCurrency(driver, test_steps));
//					//TripTax = report.getFolioAmountExcludingCurrency(driver, TripSummaryTaxes);
//					TripSummaryIncidentals = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryInceidentalsWithCurrency(driver, test_steps));
//					TripSummaryTripTotal = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTripTotalChargesWithCurrency(driver, test_steps));
//					TripSummaryPaid = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryPaidWithCurrency(driver, test_steps));
//					TripSummaryBalance = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryBalanceWithCurrency(driver, test_steps));

					//if (isPayment.equalsIgnoreCase("Yes")) {
					if (ledgerAccounts.containsKey("Payment Method")) {
						driver.navigate().refresh();
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
						reservationPage.payButtonClickInTakePayment(driver, test_steps, "" + FolioTripTotal + "", true,
								true);
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
					reservationPage.changeReservationStatus(driver, reservationStatusOptions, mrbStatus, reservationNumber, itemStatuOptions, test_steps);

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
					reservationPage.click_Folio(driver, test_steps);
					
					try {
						folio.CheckDisplayVoidItems(driver, test_steps);
					}catch(Exception e) {
						folio.CheckboxDisplayVoidItem(driver, true);
					}
					folio.includeTaxinLIneItemCheckbox(driver, false);
					
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
						//folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));
						if (folioItemValues.containsKey(items.get(i).getText())) {
							DecimalFormat df = new DecimalFormat("###.##");
							double temp = Double.parseDouble(df.format(Double.parseDouble(folioItemValues.get(items.get(i).getText())))) + 
									Double.parseDouble(itemsAmmount.get(i).getText().substring(2));
							
							
							app_logs.info("Ledger amount: "+temp);
							folioItemValues.put(items.get(i).getText(), df.format(temp));
						}else {
							folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));
						}

					}
					
					app_logs.info("Items Description: "+itemDescription);
					app_logs.info("Items Values: "+folioItemValues);
					
				}catch (Exception e) {
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
				
				if (LedgerAccount.equalsIgnoreCase("Travel Agent Commission") || LedgerAccount.equalsIgnoreCase("Transfers") || 
						inputs.contains(Transfers) ) {
					nav.Accounts(driver);
					//accountPage.OpenSearchedAccount(driver, accountName, test_steps);
					accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
					accountPage.OpenSearchedAccount(driver, accountName, test_steps);
					driver.navigate().refresh();
					accountPage.ClickFolio(driver);
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
				}
				

				app_logs.info("Reservation completed at: " + java.time.LocalTime.now());
				test_steps.add("Reservation completed at: " + java.time.LocalTime.now());
				Wait.wait60Second();
				Wait.wait60Second();

				app_logs.info("Running Report at " + java.time.LocalTime.now());
				test_steps.add("Running Report at " + java.time.LocalTime.now());

				// Select inputs
				try {

					try {

						nav.ReportsV2(driver);
						report.navigateToLedgerBalancesReport(driver);
						if (!DateRange.isEmpty()) {
							report.selectDateRange(driver, DateRange, test_steps);
						} else {
							report.selectStartdate(driver, StartDate, test_steps);
							report.selectEnddate(driver, EndDate, test_steps);
						}

						// availableTypes = report.getAllAvailableTypes(driver, test_steps);
						report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);
						// Utility.switchTab(driver, (new
						// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

						report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
//						if (!accountTypeOptions.equalsIgnoreCase("Reservations")) {
//							String accounts = accountTypeOptions + ",Reservations";
//							report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accounts);
//							// report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
//							// "Reservations");
//						} else {
//							report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
//						}
						report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
						if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
							report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
						}
						report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps,
								TaxExemptLedgerItemsOption);
						report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
						report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps,
								reservationStatusOptions);
						report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

						report.clickOnRunReport(driver);

					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
									driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
									driver);
						} else {

							Assert.assertTrue(false);
						}
					}
					
					
					if (report.checkNoReportDataAvailable(driver, test_steps)) {
						app_logs.info("Failed, Report Genaration failed, got 'No Report Data available' toast message");
						test_steps.add("AssertionError Failed, Report Genaration failed, got 'No Report Data available' toast message");
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					}else {
						try {

							test_steps.add("====  Ledger Balances Report Validation =====");
							test_steps.add("====  <b>Summary View Validation</b> =====");

							afterLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);
							afterDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);

							app_logs.info("Before: " + beforeLedgerCategoryDetails);
							test_steps.add("Before: " + beforeLedgerCategoryDetails);
							app_logs.info("After: " + afterLedgerCategoryDetails);
							test_steps.add("After: " + afterLedgerCategoryDetails);

							app_logs.info("Before Individual: " + beforeDetailsOfAllLedgerCategories);
							test_steps.add("Before Individual: " + beforeDetailsOfAllLedgerCategories);
							app_logs.info("After Individual: " + afterDetailsOfAllLedgerCategories);
							test_steps.add("After Individual: " + afterDetailsOfAllLedgerCategories);

							report.validateLedgerReportSummaryView(driver, beforeLedgerCategoryDetails,
									beforeDetailsOfAllLedgerCategories, afterLedgerCategoryDetails,
									afterDetailsOfAllLedgerCategories, ledgerAccounts, ledgerAmounts, folioItemValues,
									numberOfRooms, test_steps);

							beforeLedgerCategoryDetails.clear();
							beforeLedgerCategoryDetails = afterLedgerCategoryDetails;
							// beforeLedgerCategoryDetails.putAll(afterLedgerCategoryDetails);

							beforeDetailsOfAllLedgerCategories.clear();
							beforeDetailsOfAllLedgerCategories = afterDetailsOfAllLedgerCategories;

							app_logs.info(
									"Detailed View: " + report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));

						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to valdate Summary view", testName,
										"LedgerReportsSummaryView", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to valdate Summary view", testName,
										"LedgerReportsSummaryView", driver);
							} else {

								Assert.assertTrue(false);
							}
						}
						
						test_steps.add("====  <b>Detailed View Validation</b> =====");
						// Detailed View
						try {

							HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();

							report.validateLedgerReportDetailedView(driver, ledgerAccounts, folioItemValues, numberOfRooms,
									reservationNumbers, guestNames, accountName, arrivalDates, itemDescription, roomChargeAmount, currencySymbal,
									IsTaxExempt, dateFormat, processingMethod, test_steps);

							if (!PaymentMethod.isEmpty()) {
								TransactionDetails.clear();
								TransactionDetails = report.getTransactionDetailsList(driver, PaymentMethod, test_steps);

								if (TransactionDetails.get("Reservation #").contains(reservationNumber)) {
									app_logs.info("Reservation number " + reservationNumber + " is available under "
											+ PaymentMethod + " in Detailed View");
									test_steps.add("Reservation number " + reservationNumber + " is available under "
											+ PaymentMethod + " in Detailed View");
								} else {
									app_logs.info("Failed, Reservation number " + reservationNumber + " is not available under "
											+ PaymentMethod + " in Detailed View");
									test_steps.add("AssertionError - Failed, Reservation number " + reservationNumber
											+ " is not available under " + PaymentMethod + " in Detailed View");
								}

								if (TransactionDetails.get("Guest/Account Name").contains(guestName)) {
									app_logs.info("Guest Name " + guestName + " is available under " + PaymentMethod
											+ " in Detailed View");
									test_steps.add("Guest Name " + guestName + " is available under " + PaymentMethod
											+ " in Detailed View");
								} else {
									app_logs.info("Failed, Guest Name " + guestName + " is not available under " + PaymentMethod
											+ " in Detailed View");
									test_steps.add("AssertionError - Failed, Guest Name " + guestName
											+ " is not available under " + PaymentMethod + " in Detailed View");
								}

								if (TransactionDetails.get("Arrival Date")
										.contains(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"))) {
									app_logs.info("Arrival Date " + Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
											+ " is available under " + PaymentMethod + " in Detailed View");
									test_steps
											.add("Arrival Date " + Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
													+ " is available under " + PaymentMethod + " in Detailed View");
								} else {
									app_logs.info("Failed, Arrival Date "
											+ Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
											+ " is not available under " + PaymentMethod + " in Detailed View");
									test_steps.add("AssertionError - Failed, Arrival Date "
											+ Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
											+ " is not available under " + PaymentMethod + " in Detailed View");
								}

								if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
									app_logs.info("Date " + Utility.getCurrentDate("MMM dd, yyyy") + " is available under "
											+ PaymentMethod + " in Detailed View");
									test_steps.add("Date " + Utility.getCurrentDate("MMM dd, yyyy") + " is available under "
											+ PaymentMethod + " in Detailed View");
								} else {
									app_logs.info("Failed, Date " + Utility.getCurrentDate("MMM dd, yyyy")
											+ " is not available under " + PaymentMethod + " in Detailed View");
									test_steps.add("AssertionError - Failed, Date " + Utility.getCurrentDate("MMM dd, yyyy")
											+ " is not available under " + PaymentMethod + " in Detailed View");
								}

								if (TransactionDetails.get("Amount").contains("$" + FolioTripTotal)) {
									app_logs.info("Amount " + FolioTripTotal + " is available under " + PaymentMethod
											+ " in Detailed View");
									test_steps.add("Amount " + FolioTripTotal + " is available under " + PaymentMethod
											+ " in Detailed View");
								} else {
									app_logs.info("Failed, Amount " + FolioTripTotal + " is not available under "
											+ PaymentMethod + " in Detailed View");
									test_steps.add("AssertionError - Failed, Amount " + FolioTripTotal
											+ " is not available under " + PaymentMethod + " in Detailed View");
								}
							}

//							RetryFailedTestCases.count = Utility.reset_count;
//							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

							// driver.close();
							// Utility.switchTab(driver, 0);

						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e,
										"Failed to validate Reservation details in Detailed view after after Run report",
										testName, "RunReport-DetailedView", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e,
										"Failed to validate Reservation details in Detailed view after after Run report",
										testName, "RunReport-DetailedView", driver);
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
						Utility.updateReport(e,
								"Failed to validate Reservation details in Summary view after after Run report",
								testName, "RunReport-SummaryView", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e,
								"Failed to validate Reservation details in Summary view after Run report", testName,
								"RunReport-SummaryView", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
				if (ResType.equalsIgnoreCase("Copy")) {
					driver.close();
					Utility.switchTab(driver, 0);
					
					nav.Reservation_Backward_3(driver);
					
					reservationPage.Search_ResNumber_And_Click(driver, reservationNumber);
					driver.navigate().refresh();
					reservationPage.clickCopyButton(driver, guestFirstName);
					String copiedReservationTrimmedName = "copy [" + guestFirstName.substring(0, 3) + "...";
					reservationPage.enterGuestNameWhileCopy(driver, test_steps, copiedReservationTrimmedName);
					

					reservationPage.clickBookNow(driver, test_steps);
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					app_logs.info("Reservation Number: " + reservationNumber);
					test_steps.add("<b>Reservation Number: " + reservationNumber);
					status = reservationPage.get_ReservationStatus(driver, test_steps);

					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					//roomNumber = reservationPage.get_RoomNumber(driver, test_steps, "Yes");
					app_logs.info("Copy Reservation created Successfully");
					test_steps.add("Copy Reservation created Successfully");
					
					
					guestName = copiedReservationTrimmedName + " " + guestLastName;
					reservationPage.closeReservationTab(driver);
					reservationPage.closeReservationTab(driver);
					driver.navigate().refresh();
					Wait.wait10Second();
					reservationPage.Search_ResNumber_And_Click(driver, reservationNumber);
					folio.folioTab(driver);

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

					int numberOfNights = Integer.parseInt(Utility.differenceBetweenDates(CheckInDate, CheckOutDate));
					app_logs.info("number of nights: "+numberOfNights);
					//double feeCalc = report.feeCalculation(driver, feeValue, roomChargeAmount, numberOfNights);
					feeCalc = report.feeCalculationsAll(driver, feeValueList, roomChargeAmount, numberOfNights);
					app_logs.info("Fee calculation: "+feeCalc);
					double feeExp = feeCalc + Double.parseDouble(AmountFees);
					app_logs.info("Fee Exp: "+feeExp);
					if (FolioFees == feeExp) {
						app_logs.info("Fees mathed");
						app_logs.info("Folio Fees: "+FolioFees);
						app_logs.info("Fee Value Fees: "+feeExp);
						
					}else {
						app_logs.info("Fees not mathed");
						app_logs.info("Folio Fees: "+FolioFees);
						app_logs.info("Fee Value Fees: "+feeExp);
					}
					
					taxCalc = report.taxCalculationsAll(driver, taxValueList, roomChargeAmount);
					app_logs.info("Tax calculation: "+taxCalc);
					double taxExp = taxCalc + Double.parseDouble(AmountTaxes);
					app_logs.info("Tax Exp: "+taxExp);
					if (FolioTaxes == taxExp) {
						app_logs.info("Tax mathed");
						app_logs.info("Folio Tax: "+FolioTaxes);
						app_logs.info("Tax Value: "+taxExp);
						
					}else {
						app_logs.info("Tax not mathed");
						app_logs.info("Folio Tax: "+FolioTaxes);
						app_logs.info("Tax Value: "+taxExp);
					}
					
					ledgerAmounts.put("Room Charge", FolioRoomCharges);
					ledgerAmounts.put("Incidental", FolioIncidentals);
					ledgerAmounts.put("Taxes", FolioTaxes);
					//ledgerAmounts.put("Fees", FolioFees);
					ledgerAmounts.put("Fees", feeExp*numberOfRooms);

					app_logs.info("Ledger Amounts: " + ledgerAmounts);

					app_logs.info("FolioRoomCharges " + FolioRoomCharges);
					app_logs.info("FolioIncidentals " + FolioIncidentals);
					app_logs.info("FolioTaxes " + FolioTaxes);
					app_logs.info("FolioFees " + FolioFees);
					app_logs.info("FolioTripTotal " + FolioTripTotal);



			// to change the Reservation status
			try {
				reservationPage.changeReservationStatus(driver, reservationStatusOptions, mrbStatus, reservationNumber, itemStatuOptions, test_steps);

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
				reservationPage.click_Folio(driver, test_steps);
				
				try {
					folio.CheckDisplayVoidItems(driver, test_steps);
				}catch(Exception e) {
					folio.CheckboxDisplayVoidItem(driver, true);
				}
				folio.includeTaxinLIneItemCheckbox(driver, false);
				String strItems = "//table[contains(@class,'table-foliogrid')]//tr/td[7]/span";
				String strDesc = "//table[contains(@class,'table-foliogrid')]//tr/td[8]/a";
				String strAmount = "//table[contains(@class,'table-foliogrid')]//tr/td[11]/span";

				List<WebElement> items = driver.findElements(By.xpath(strItems));
				List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
				List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

				itemDescription.clear();
				folioItemValues.clear();
				for (int i = 0; i < items.size(); i++) {
					// List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
					// itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(),
					// cells.get(7).findElement(By.tagName("a")).getText());
					itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
					//folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));
					if (folioItemValues.containsKey(items.get(i).getText())) {
						DecimalFormat df = new DecimalFormat("###.##");
						double temp = Double.parseDouble(df.format(Double.parseDouble(folioItemValues.get(items.get(i).getText())))) + 
								Double.parseDouble(itemsAmmount.get(i).getText().substring(2));
						
						
						app_logs.info("Ledger amount: "+temp);
						folioItemValues.put(items.get(i).getText(), df.format(temp));
					}else {
						folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));
					}

				}
				
				app_logs.info("Items Description: "+itemDescription);
				app_logs.info("Items Values: "+folioItemValues);
				
			}catch (Exception e) {
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
						

			app_logs.info("Copy Reservation completed at: " + java.time.LocalTime.now());
			test_steps.add("Copy Reservation completed at: " + java.time.LocalTime.now());
			Wait.wait60Second();
			Wait.wait60Second();
			Wait.wait60Second();

			app_logs.info("Running Report at " + java.time.LocalTime.now());
			test_steps.add("Running Report at " + java.time.LocalTime.now());

			// Select inputs
			try {

				try {

					nav.ReportsV2(driver);
					report.navigateToLedgerBalancesReport(driver);
					if (!DateRange.isEmpty()) {
						report.selectDateRange(driver, DateRange, test_steps);
					} else {
						report.selectStartdate(driver, StartDate, test_steps);
						report.selectEnddate(driver, EndDate, test_steps);
					}

					// availableTypes = report.getAllAvailableTypes(driver, test_steps);
					report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);
					// Utility.switchTab(driver, (new
					// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

					report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
					report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
					if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
						report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
					}
					report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps,
							TaxExemptLedgerItemsOption);
					report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
					report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps,
							reservationStatusOptions);
					report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

					report.clickOnRunReport(driver);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
								driver);
					} else {

						Assert.assertTrue(false);
					}
				}
				
				
				if (report.checkNoReportDataAvailable(driver, test_steps)) {
					app_logs.info("Failed, Report Genaration failed, got 'No Report Data available' toast message");
					test_steps.add("AssertionError Failed, Report Genaration failed, got 'No Report Data available' toast message");
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				}else {
					try {

						test_steps.add("====  Ledger Balances Report Validation =====");
						test_steps.add("====  <b>Summary View Validation</b> =====");

						afterLedgerCategoryDetailsNew = report.getLedgerCategoryDetails(driver, test_steps);
						afterDetailsOfAllLedgerCategoriesNew = report.getDetailsOfAllLedgerCategories(driver, test_steps);

						app_logs.info("Before Copy: " + afterLedgerCategoryDetails);
						test_steps.add("Before COpy: " + afterLedgerCategoryDetails);
						app_logs.info("After Copy: " + afterLedgerCategoryDetailsNew);
						test_steps.add("After Copy: " + afterLedgerCategoryDetailsNew);

						app_logs.info("Before Copy Individual: " + afterDetailsOfAllLedgerCategories);
						test_steps.add("Before Copy Individual: " + afterDetailsOfAllLedgerCategories);
						app_logs.info("After Copy Individual: " + afterDetailsOfAllLedgerCategoriesNew);
						test_steps.add("After Copy Individual: " + afterDetailsOfAllLedgerCategoriesNew);

						report.validateLedgerReportSummaryView(driver, afterLedgerCategoryDetails,
								afterDetailsOfAllLedgerCategories, afterLedgerCategoryDetailsNew,
								afterDetailsOfAllLedgerCategoriesNew, ledgerAccounts, ledgerAmounts, folioItemValues,
								numberOfRooms, test_steps);

						afterLedgerCategoryDetails.clear();
						afterLedgerCategoryDetails = afterLedgerCategoryDetailsNew;
						// beforeLedgerCategoryDetails.putAll(afterLedgerCategoryDetails);

						afterDetailsOfAllLedgerCategories.clear();
						afterDetailsOfAllLedgerCategories = afterDetailsOfAllLedgerCategoriesNew;

						app_logs.info(
								"Detailed View: " + report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));

					
					test_steps.add("====  <b>Detailed View Validation</b> =====");
					// Detailed View

						HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();

						report.validateLedgerReportDetailedView(driver, ledgerAccounts, folioItemValues, numberOfRooms,
								reservationNumbers, guestNames, accountName, arrivalDates, itemDescription, roomChargeAmount, currencySymbal,
								IsTaxExempt, dateFormat, processingMethod, test_steps);


						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

						// driver.close();
						// Utility.switchTab(driver, 0);

					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Detailed view after after Run report",
									testName, "RunReport-DetailedView", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Detailed view after after Run report",
									testName, "RunReport-DetailedView", driver);
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
					Utility.updateReport(e,
							"Failed to validate Reservation details in Summary view after after Run report",
							testName, "RunReport-SummaryView", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e,
							"Failed to validate Reservation details in Summary view after Run report", testName,
							"RunReport-SummaryView", driver);
				} else {
					Assert.assertTrue(false);
				}
			}	
				
				}else {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				}

			} else {
				app_logs.info("Account Type: " + accountTypeOptions);
				accountType = accountTypeOptions;
				// New account creation
				if (accountTypeOptions.equals("House Account")) {

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

				else if (accountTypeOptions.equals("Gift Certificate")) {

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

				} else if (accountTypeOptions.equals("Travel Agent")) {
					ArrayList<String> TravelAgentItems = new ArrayList<>();
					int TravelAgetItemValue;

					nav.Accounts(driver);

					if (!TravelAgentCommission.isEmpty()) {

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

					}

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

						// accountPage.NewReservationButton(driver, test);

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

				else if (accountTypeOptions.equals("Unit Owners")) {

					// If Distribution method is given in Select inputs
					if (!DistributionMethod.isEmpty()) {

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
						unitOwnerItems = accountPage.getAssociatedUnitOwnerItemsList(driver, acName, category,
								test_steps);

					}

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

						// accountPage.NewReservationButton(driver, test);

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

				else if (accountTypeOptions.equals("Corporate/Member Accounts")) {
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

						// accountPage.NewReservationButton(driver, test);

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

				else if (accountTypeOptions.equals("Group")) {

//					try {
						test_steps.add("========== Creating Group account ==========");
						app_logs.info("========== Creating Group account ==========");

						nav.groups(driver);
						// group.click_NewAccount(driver, test, test_steps);
						group.type_GroupName(driver, test, accountName, test_steps);
						accountNumber = group.getAccountNo(driver);
						group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
						group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber, address1,
								city, state, country, postalCode, test_steps);
						group.Billinginfo(driver);
						group.Save(driver, test_steps);
						// group.click_GroupNewReservation(driver, test_steps);
						
						group.navigateFolio(driver, test, test_steps);
						ArrayList<Double> allAmounts = new ArrayList<>();
						report.addGroupFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, allAmounts, test_steps);
						group.navigateFolio(driver, test, test_steps);
						group.displayVoidItemButton(driver);
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
							FolioTripTotal = Double.parseDouble(group.getEndingBalance(driver));
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

//						app_logs.info("FolioRoomCharges " + amountAllRoomCharges);
//						app_logs.info("FolioIncidentals " + amountAllIncidentals);
//						app_logs.info("FolioTaxes " + amountAllTaxes);
//						app_logs.info("FolioFees " + amountAllFees);
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

						String strItems = "//table[contains(@id,'dgLineItems')]//tr/td[6]/span";
						String strDesc = "//table[contains(@id,'dgLineItems')]//tr/td/table//a";
						String strAmount = "//table[contains(@id,'dgLineItems')]//tr/td[10]/span";

						List<WebElement> items = driver.findElements(By.xpath(strItems));
						List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
						List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

						for (int i = 0; i < items.size(); i++) {
							// List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
							// itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(),
							// cells.get(7).findElement(By.tagName("a")).getText());
							itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
							folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText());
						}
						app_logs.info("Item Descreption: " + itemDescription);
						app_logs.info("Foio Item values: " + folioItemValues);

//						ledgerAmounts.put("Room Charge", FolioRoomCharges);
//						ledgerAmounts.put("Incidental", FolioIncidentals);
//						ledgerAmounts.put("Taxes", FolioTaxes);
//						ledgerAmounts.put("Fees", FolioFees);
						ledgerAmounts.put("Room Charge", allAmounts.get(1));
						ledgerAmounts.put("Incidental", allAmounts.get(0));
						ledgerAmounts.put("Taxes", FolioTaxes);
						ledgerAmounts.put("Fees", FolioFees);

						app_logs.info("Ledger Amounts: " + ledgerAmounts);

						

//					} catch (Exception e) {
//						if (Utility.reTry.get(testName) == Utility.count) {
//							RetryFailedTestCases.count = Utility.reset_count;
//							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
//							Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
//						} else {
//							Assert.assertTrue(false);
//						}
//					} catch (Error e) {
//						if (Utility.reTry.get(testName) == Utility.count) {
//							RetryFailedTestCases.count = Utility.reset_count;
//							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
//							Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
//						} else {
//							Assert.assertTrue(false);
//						}
//					}

				}

				guestName = accountName + " (" + accountNumber + ")";
				guestNames.clear();
				guestNames.add(guestName);

				Double amountAllIncidentals = 0.0, amountAllRoomCharges = 0.0, amountAllTaxes = 0.0,
						amountAllFees = 0.0;

//				try {
					test_steps.add("========== Adding Line Items ==========");
					app_logs.info("========== Adding Line Items ==========");

					// folio.folioTab(driver);
					
					if (!accountTypeOptions.equals("Group")) {
						accountPage.ClickFolio(driver);
						ArrayList<Double> allAmounts = new ArrayList<>();
						test_steps.add("Clicked Folio Tab");
						app_logs.info("Clicked Folio Tab");
						
						report.addAccountFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions,allAmounts, test_steps);
						
						if (ledgerAccounts.containsKey("Distribution Method")) {
							String amountDistribute = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
							accountPage.distributeAmountUnitOwners(driver, amountDistribute);
							ledgerAmounts.put("Distribution Method", Double.parseDouble(amountDistribute));
						}
						
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

//						ledgerAmounts.put("Room Charge", FolioRoomCharges);
//						ledgerAmounts.put("Incidental", FolioIncidentals);
//						ledgerAmounts.put("Taxes", FolioTaxes);
//						ledgerAmounts.put("Fees", FolioFees);
//						ledgerAmounts.put("Room Charge", amountAllRoomCharges);
//						ledgerAmounts.put("Incidental", amountAllIncidentals);
//						ledgerAmounts.put("Taxes", FolioTaxes);
//						ledgerAmounts.put("Fees", FolioFees);
						ledgerAmounts.put("Room Charge", allAmounts.get(1));
						ledgerAmounts.put("Incidental", allAmounts.get(0));
						ledgerAmounts.put("Taxes", FolioTaxes);
						ledgerAmounts.put("Fees", FolioFees);

						app_logs.info("Ledger Amounts: " + ledgerAmounts);
					}


					

//				} catch (Exception e) {
//					if (Utility.reTry.get(test_name) == Utility.count) {
//						RetryFailedTestCases.count = Utility.reset_count;
//						Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
//						Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems", driver);
//					} else {
//
//						Assert.assertTrue(false);
//					}
//				} catch (Error e) {
//					test_steps.add(e.toString());
//				}

				// Item status
				try {

					if (itemStatuOptions.split(",").length == 1) {
						if (itemStatuOptions.equals("Pending")) {
							// folio.clickAddLineItemButton(driver);
							// folio.AddFolioLineItem(driver, "Laundry", "50");
						} else if (itemStatuOptions.equals("Posted")) {
							// folio.clickAddLineItemButton(driver);
							// folio.AddFolioLineItem(driver, "Spa", "70");
							// driver.findElement(By.xpath("//span[text()='Spa']/../../td[contains(@class,'changestatus')]")).click();
							// reservationPage.checkinReservation(driver, test_steps);
							reservationPage.CheckInButton(driver);
							reservationPage.generatGuestReportToggle(driver, test_steps, "No");
							reservationPage.CheckInConfrimButton(driver);

						}
						// driver.findElement(By.xpath("(//button[text()='Save'])[9]")).click();
					} else {
						System.out.println("Enter only one option for Item status");
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

				// Payment
				try {

					//if (isPayment.equalsIgnoreCase("Yes")) {
					if (ledgerAccounts.containsKey("Payment Method")) {
						driver.navigate().refresh();
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
						reservationPage.payButtonClickInTakePayment(driver, test_steps, "" + FolioTripTotal + "", true,
								true);
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
				
				if (LedgerAccount.equalsIgnoreCase("Travel Agent Commission") || LedgerAccount.equalsIgnoreCase("Transfers") || 
						inputs.contains(Transfers) ) {
					nav.Accounts(driver);
					//accountPage.OpenSearchedAccount(driver, accountName, test_steps);
					accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
					accountPage.OpenSearchedAccount(driver, accountName, test_steps);
					driver.navigate().refresh();
					accountPage.ClickFolio(driver);
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
				}
				

				app_logs.info("Account/Group creation completed at: " + java.time.LocalTime.now());
				test_steps.add("Account/Group creation completed at: " + java.time.LocalTime.now());
				Wait.wait60Second();
				Wait.wait60Second();

				app_logs.info("Running Report at " + java.time.LocalTime.now());
				test_steps.add("Running Report at " + java.time.LocalTime.now());

				// Select inputs
				try {

					try {

						nav.ReportsV2(driver);
						report.navigateToLedgerBalancesReport(driver);
						if (!DateRange.isEmpty()) {
							report.selectDateRange(driver, DateRange, test_steps);
						} else {
							report.selectStartdate(driver, StartDate, test_steps);
							report.selectEnddate(driver, EndDate, test_steps);
						}

						// availableTypes = report.getAllAvailableTypes(driver, test_steps);
						report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);
						// Utility.switchTab(driver, (new
						// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

						report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
//						if (!accountTypeOptions.equalsIgnoreCase("Reservations")) {
//							String accounts = accountTypeOptions + ",Reservations";
//							report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accounts);
//							// report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
//							// "Reservations");
//						} else {
//							report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
//						}
						report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
						if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
							report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
						}
						report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps,
								TaxExemptLedgerItemsOption);
						report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
						report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps,
								reservationStatusOptions);
						report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

						report.clickOnRunReport(driver);

					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
									driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
									driver);
						} else {

							Assert.assertTrue(false);
						}
					}

					try {

						test_steps.add("====  Ledger Balances Report Validation =====");
						test_steps.add("====  <b>Summary View Validation</b> =====");

						afterLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);

						// afterDetailsOfGivenCategory = report.getDetailsOfGivenCategory(driver, "Room
						// Charge", test_steps);
						// report.getDetailsOfGivenCategory(driver, "Tax", test_steps);
						afterDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);

						app_logs.info("Before: " + beforeLedgerCategoryDetails);
						test_steps.add("Before: " + beforeLedgerCategoryDetails);
						app_logs.info("After: " + afterLedgerCategoryDetails);
						test_steps.add("After: " + afterLedgerCategoryDetails);

						app_logs.info("Before Individual: " + beforeDetailsOfAllLedgerCategories);
						test_steps.add("Before Individual: " + beforeDetailsOfAllLedgerCategories);
						app_logs.info("After Individual: " + afterDetailsOfAllLedgerCategories);
						test_steps.add("After Individual: " + afterDetailsOfAllLedgerCategories);

						report.validateLedgerReportSummaryView(driver, beforeLedgerCategoryDetails,
								beforeDetailsOfAllLedgerCategories, afterLedgerCategoryDetails,
								afterDetailsOfAllLedgerCategories, ledgerAccounts, ledgerAmounts, folioItemValues,
								numberOfRooms, test_steps);

						// Payment Method
						if (!PaymentMethod.isEmpty()) {
							if (beforeDetailsOfAllLedgerCategories.containsKey(PaymentMethod)) {
								double pay = Double
										.parseDouble(beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1))
										- Double.parseDouble(
												beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
								app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

								if (pay == FolioTripTotal) {
									app_logs.info("Payment Method " + PaymentMethod
											+ " amount validated successfully in Summary View");
									test_steps.add("Payment Method " + PaymentMethod
											+ " amount validated successfully in Summary View");
								} else {
									app_logs.info("Failed - Payment Method " + PaymentMethod
											+ " amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Payment Method " + PaymentMethod
											+ " amount in Summary View validation failed. Expected: " + FolioTripTotal
											+ " But found: " + pay);
								}

							} else {
								try {
									double pay = Double.parseDouble(
											beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
									app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

									if (pay == FolioTripTotal) {
										app_logs.info("Payment Method " + PaymentMethod
												+ " amount validated successfully in Summary View");
										test_steps.add("Payment Method " + PaymentMethod
												+ " amount validated successfully in Summary View");
									} else {
										app_logs.info("Failed - Payment Method " + PaymentMethod
												+ " amount in Summary View validation failed");
										test_steps.add("AssertionError : Failed - Payment Method " + PaymentMethod
												+ " amount in Summary View validation failed");
									}
								} catch (Exception e) {
									app_logs.info("Failed to get Payments Method details");
									test_steps.add("Failed to get Payments Method details");
								}

							}
						}

						beforeLedgerCategoryDetails.clear();
						beforeLedgerCategoryDetails = afterLedgerCategoryDetails;
						// beforeLedgerCategoryDetails.putAll(afterLedgerCategoryDetails);

						beforeDetailsOfAllLedgerCategories.clear();
						beforeDetailsOfAllLedgerCategories = afterDetailsOfAllLedgerCategories;
						// beforeDetailsOfAllLedgerCategories.putAll(afterDetailsOfAllLedgerCategories);

						// app_logs.info("Detailed View: "+report.getDetailedViewDetails(driver,
						// test_steps));
						// app_logs.info("Detailed View: "+report.getDetailedViewDetailsLatest(driver,
						// test_steps));
						app_logs.info(
								"Detailed View: " + report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));

					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to valdate Summary view", testName,
									"LedgerReportsSummaryView", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to valdate Summary view", testName,
									"LedgerReportsSummaryView", driver);
						} else {

							Assert.assertTrue(false);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e,
								"Failed to validate Reservation details in Summary view after after Run report",
								testName, "RunReport-SummaryView", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e,
								"Failed to validate Reservation details in Summary view after Run report", testName,
								"RunReport-SummaryView", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				test_steps.add("====  <b>Detailed View Validation</b> =====");
				// Detailed View
				try {

					HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();

					report.validateLedgerReportDetailedViewAccounts(driver, ledgerAccounts, folioItemValues, guestNames,
							itemDescription, currencySymbal, IsTaxExempt, test_steps);

//					driver.close();
//					Utility.switchTab(driver, 0);

					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e,
								"Failed to validate Reservation details in Detailed view after after Run report",
								testName, "RunReport-DetailedView", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e,
								"Failed to validate Reservation details in Detailed view after after Run report",
								testName, "RunReport-DetailedView", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			}

		} else {
			String[] accountsOptions = accountTypeOptions.split(",");

			// New account creation
			boolean isRes = false;

			for (int i = 0; i < accountsOptions.length; i++) {
				if (accountsOptions[i].equalsIgnoreCase("Reservations")) {
					isRes = true;
					break;
				}
			}

			if (isRes) {

				if (accountsOptions.length == 2) {

					if (accountsOptions[0].equalsIgnoreCase("Reservations")) {
						accountType = accountsOptions[1];
					} else {
						accountType = accountsOptions[0];
					}

					app_logs.info("Account Type: " + accountTypeOptions);

					// New account creation
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

							accountPage.closeAccountTab(driver);
							nav.cpReservationBackward(driver);
							reservationPage.click_NewReservation(driver, test_steps);

						} catch (Exception e) {

							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}

						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount",
										driver);
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

							accountPage.closeAccountTab(driver);
							nav.cpReservationBackward(driver);
							reservationPage.click_NewReservation(driver, test_steps);

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

						//if (!TravelAgentCommission.isEmpty()) {
						//if (!ledgerAccounts.containsKey("Travel Agent Commission")) {
						if (ledgerAccounts.containsKey("Travel Agent Commission")) {
							accountPage.ClickTravelAgentItem(driver);

							if (accountPage.chekcTravelAgentItemAvailability(driver, test_steps)) {
								TravelAgentItems = accountPage.getAssociatedTravelAgentItems(driver, test_steps);
								TravelAgetItemValue = accountPage.getTravelAgentCommissionValue(driver, test_steps);

								app_logs.info("Travel agent items: " + TravelAgentItems);
								app_logs.info("Travel agent Value: " + TravelAgetItemValue);

							} else {
								accountPage.CreateNewTravelAgentItem(driver, "ItemName", "DisplayName", "Description",
										"10", "Category", "Room Charge");
							}
							nav.accounts(driver);
						}

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

							accountPage.NewReservationButton(driver, test);

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
/*						reservationPage.click_NewReservation(driver, test_steps);
						reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
						reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
						reservationPage.enter_Adults(driver, test_steps, Adults);
						reservationPage.enter_Children(driver, test_steps, Children);
						reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
						reservationPage.clickOnFindRooms(driver, test_steps);
						roomNumber = reservationPage.getFirstRoomNumberFromRoomsDropDown(driver, RoomClass);
						app_logs.info("Room Number available: "+roomNumber);
						reservationPage.closeReservationTab(driver);*/
						
						// If Distribution method is given in Select inputs
						//if (!DistributionMethod.isEmpty()) {
						if (ledgerAccounts.containsKey("Unit Revenues") || ledgerAccounts.containsKey("Unit Expenses")) {
							nav.setup(driver);
							nav.LedgerAccounts(driver);

							la.NewAccountbutton(driver);
							String acName = "Unit" + Utility.fourDigitgenerateRandomString();
							String category = acName;

							la.LedgerAccountDetails(driver, acName, "Test", "", "Unit Revenues", "Active");
							la.SaveLedgerAccount(driver);

							nav.Accounts(driver);
							nav.UnitownerAccount(driver);

//							if (UnitRevenues.isEmpty()) {
//								category = Utility.generateRandomString();
//							} else {
//								category = UnitRevenues;
//							}
							unitOwnerItems = accountPage.getAssociatedUnitOwnerItemsList(driver, acName,
									category, test_steps);
							app_logs.info("Unit Owner Items: "+unitOwnerItems);
						}

//						try {
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
							//accountPage.associateRooms(driver, test_steps, RoomClass, roomNumber);
							report.AccountSave(driver, test, accountName, test_steps);

							accountPage.NewReservationButton(driver, test);

//						} catch (Exception e) {
//
//							if (Utility.reTry.get(testName) == Utility.count) {
//								RetryFailedTestCases.count = Utility.reset_count;
//								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
//								Utility.updateReport(e, "Failed to create Unit Owner account", testName,
//										"UnitOwnerAccount", driver);
//							}
//
//						} catch (Error e) {
//							if (Utility.reTry.get(testName) == Utility.count) {
//								RetryFailedTestCases.count = Utility.reset_count;
//								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
//								Utility.updateReport(e, "Failed to create Unit Owner account", testName,
//										"UnitOwnerAccount", driver);
//							}
//						}

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

							accountPage.NewReservationButton(driver, test);

						} catch (Exception e) {

							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create corporate account", testName,
										"CorporateAccount", driver);
							} else {
								Assert.assertTrue(false);
							}

						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create corporate account", testName,
										"CorporateAccount", driver);
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
							group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber,
									address1, city, state, country, postalCode, test_steps);
							group.Billinginfo(driver);
							group.Save(driver, test_steps);
							//group.click_GroupNewReservation(driver, test_steps);


						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						}

					}

					else if (accountType.equals("Reservations")) {

						try {
							reservationPage.click_NewReservation(driver, test_steps);

						} catch (Exception e) {

							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to click on new Reservation", testName,
										"NewReservation", driver);
							} else {
								Assert.assertTrue(false);
							}

						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to click on new Reservation", testName,
										"NewReservation", driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}

					// Reservations
					
					if (ResType.equalsIgnoreCase("GroupPickRoomingList")) {
						String Amount = "";
						//driver.navigate().back();
						
//						try {

							group.navigateRoomBlock(driver, test);
							
							BlockName = BlockName + Utility.GenerateRandomString15Digit();
							
							getTest_Steps.clear();
							//getTest_Steps = group.createNewBlock(driver, BlockName, numberOfRoomsMRB, RoomClass);
							getTest_Steps = group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRoomsMRB, RoomClass);
							test_steps.addAll(getTest_Steps);

							group.navigateRoomBlock(driver, test);

							getTest_Steps = group.roomingListClick(driver);
							test_steps.addAll(getTest_Steps);

							getTest_Steps.clear();
							//advgrp.enter_pickupdetails(driver, getTest_Steps);
							advgrp.enter_RoomPickupdetails(driver, getTest_Steps);
							test_steps.addAll(getTest_Steps);

							reservationNumber = group.pickUp_getResNo(driver);
							app_logs.info("Group pick Reservation number: "+reservationNumber);
							
							group.pickUpCloseClick(driver);
							
							nav.reservation(driver);
							reservationPage.Search_ResNumber_And_Click(driver, reservationNumber);
							reservationPage.click_Folio(driver, test_steps);

//						} catch (Exception e) {
//							if (Utility.reTry.get(testName) == Utility.count) {
//								RetryFailedTestCases.count = Utility.reset_count;
//								Utility.AddTest_IntoReport(Scenario, "Creating Group Block and Group Pick Rooming List", "Group Pick Rooming List", test_steps);
//								Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
//							} else {
//								//Assert.assertTrue(false);
//							}
//
//						} catch (Error e) {
//							if (Utility.reTry.get(testName) == Utility.count) {
//								RetryFailedTestCases.count = Utility.reset_count;
//								Utility.AddTest_IntoReport(Scenario, "Creating Group Block and Group Pick Rooming List", "Group Pick Rooming List", test_steps);
//								Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
//							} else {
//								Assert.assertTrue(false);
//							}
//						}
						
						
	
					}else if (ResType.equalsIgnoreCase("GroupPickBlueIcon")) {
						String Amount;
						//driver.navigate().back();
						
//						try {

							group.navigateRoomBlock(driver, test);
							
							BlockName = BlockName + Utility.GenerateRandomString15Digit();
							
							getTest_Steps.clear();
							getTest_Steps = group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRoomsMRB, RoomClass);
							test_steps.addAll(getTest_Steps);
//
							group.navigateRoomBlock(driver, test);
							getTest_Steps.clear();

							advgrp.clickBlueBookIcon(driver);
							
							reservationPage.clickNext(driver, test_steps);
							reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation, guestFirstName, guestLastName, config.getProperty("flagOff"));	
							reservationPage.clickBookNow(driver, test_steps);
							reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
							reservationPage.clickCloseReservationSavePopup(driver, test_steps);		

							reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
							reservationPage.click_Folio(driver, test_steps);
				   
//						} catch (Exception e) {
//							if (Utility.reTry.get(testName) == Utility.count) {
//								RetryFailedTestCases.count = Utility.reset_count;
//								Utility.AddTest_IntoReport(Scenario, "Creating Group Block and Group pick Blue Icon", "Group Pick Blue Icon", test_steps);
//								Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
//							} else {
//								Assert.assertTrue(false);
//							}
//
//						} catch (Error e) {
//							if (Utility.reTry.get(testName) == Utility.count) {
//								RetryFailedTestCases.count = Utility.reset_count;
//								Utility.AddTest_IntoReport(Scenario, "Creating Group Block and Group pick Blue Icon", "Group Pick Blue Icon", test_steps);
//								Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
//							} else {
//								Assert.assertTrue(false);
//							}
//						}
						
					}else if (ResType.equalsIgnoreCase("MRB")) {
						
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

							guestNames.clear();
							for (int i = 0; i < numberOfRooms; i++) {
								String[] fNames = mrbGuestFirstName.split("\\|");
								String[] lNames = mrbGuestLastName.split("\\|");
								app_logs.info("Fanmes: " + fNames.length);
								app_logs.info("Lanmes: " + lNames.length);
								app_logs.info("Loop " + i);
								guestNames.add(fNames[i] + " " + lNames[i]);
							}

							app_logs.info("Guest Names: " + guestNames);
							
							arrivalDates.clear();
							for (int i = 0; i < numberOfRooms; i++) {
								String[] CheckInDates = CheckInDate.split("\\|");							
								
								if (clientDateFormat.equalsIgnoreCase("USA")) {
									arrivalDates.add(CheckInDates[i]);
								}else if (clientDateFormat.equalsIgnoreCase("International")) {
									arrivalDates.add(Utility.parseDate(CheckInDates[i], "MMM dd, yyyy", "dd MMM, yyyy"));
								}						
							}
							
							dates.clear();
							for (int i = 0; i < numberOfRooms; i++) {
								if (clientDateFormat.equalsIgnoreCase("USA")) {
									dates.add(Utility.getCurrentDate("MMM dd, yyyy"));
								}else if (clientDateFormat.equalsIgnoreCase("International")) {
									dates.add(Utility.getCurrentDate("dd MMM, yyyy"));
								}							
							}

							// reservationPage.click_NewReservation(driver, test_steps);
							// guest1 = MrbGuestFirstName.split("|")[1] + MrbGuestLastName.split("|")[1];
							// guest2 = MrbGuestFirstName.split("|")[2] + MrbGuestLastName.split("|")[2];

							report.mrbReservation(driver, CheckInDate, CheckOutDate, mrbAdults, mrbChildren,
									mrbRateplan, PromoCode, IsSplitRes, mrbRoomClass, account, mrbSalutation,
									mrbGuestFirstName, mrbGuestLastName, mrbPhoneNumber, alternativePhone, mrbEmail,
									accountType, address1, address2, address3, city, country, state, postalCode,
									getRoomClasses, PaymentType, CardNumber, NameOnCard, CardExpDate, accountName,
									resNumberPayment, IsTaxExempt, TaxExemptID, TravelAgent, marketSegment, referral,
									reservationNumber, numberOfRooms, reservationNumbers, arrivalDates, status,
									roomCost, test_steps);
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
							guestName = guestFirstName + " " + guestLastName;
							//date = Utility.getCurrentDate("MMM dd,yyyy");
							if (clientDateFormat.equalsIgnoreCase("USA")) {
								date = Utility.getCurrentDate("MMM dd, yyyy");
							}else if (clientDateFormat.equalsIgnoreCase("International")) {
								date = Utility.getCurrentDate("dd MMM, yyyy");
							}
							
							guestNames.clear();
							guestNames.add(guestName);
							arrivalDates.clear();
							if (clientDateFormat.equalsIgnoreCase("USA")) {
								arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
							}else {
								arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "dd MMM, yyyy"));
							}

							report.singleReservation(driver, CheckInDate, CheckOutDate, Adults, Children, Rateplan,
									PromoCode, RoomClass, depositAmount, salutation, guestFirstName, guestLastName,
									phoneNumber, alternativePhone, email, account, accountType, address1, address2,
									address3, city, country, state, postalCode, IsGuesProfile, resNumberPayment,
									PaymentType, CardNumber, NameOnCard, CardExpDate, accountName, IsTaxExempt,
									TaxExemptID, marketSegment, referral, reservationNumber, reservationNumbers, status, reservationStatusOptions, 
									roomNumber, test_steps);
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
						
						
					}
					
					

					//if (isMRB.equalsIgnoreCase("Yes")) {
					if (ResType.equalsIgnoreCase("MRB")) {

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
							
							report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);

							// folio.ClickSaveFolioButton(driver);
							
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
							// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
							// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
							// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);

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
								
								report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);

								// folio.ClickSaveFolioButton(driver);

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

								ledgerAmounts.put("Room Charge", FolioRoomCharges);
								ledgerAmounts.put("Incidental", FolioIncidentals);
								ledgerAmounts.put("Taxes", FolioTaxes);
								ledgerAmounts.put("Fees", FolioFees);

								app_logs.info("Ledger Amounts: " + ledgerAmounts);

								FolioTripTotal = FolioTripTotal + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
								FolioPaid = FolioPaid + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_PaymentsWithCurrency(driver, test_steps));
								FolioBalance = FolioBalance + report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_BalanceWithCurrency(driver, test_steps));
								// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
								// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
								// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);

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

					} else {

//						try {
							test_steps.add("========== Adding Line Items ==========");
							app_logs.info("========== Adding Line Items ==========");

//							folio.folioTab(driver);
//							test_steps.add("Clicked Folio Tab");
//							app_logs.info("Clicked Folio Tab");
							
							folio.includeTaxinLIneItemCheckbox(driver, false);
							roomChargeAmount = report.getFolioAmountExcludingCurrency(driver,
									driver.findElement(
											By.xpath("(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
											.getText());
							app_logs.info("Room Charge: " + roomChargeAmount);
							
							report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);

							// folio.ClickSaveFolioButton(driver);

							// String strItems = "//table[contains(@class,'table-foliogrid')]//tr";

//							FolioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
//							FolioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
//							FolioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
//							FolioFees = reservationPage.get_FeesWithCurrency(driver, test_steps);
//							
//							FolioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
//							FolioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
//							FolioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);

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
//							FolioBalance = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_BalanceWithCurrency(driver, test_steps));
							int numberOfNights = Integer.parseInt(Utility.differenceBetweenDates(CheckInDate, CheckOutDate));
							app_logs.info("number of nights: "+numberOfNights);
							//double feeCalc = report.feeCalculation(driver, feeValue, roomChargeAmount, numberOfNights);
							feeCalc = report.feeCalculationsAll(driver, feeValueList, roomChargeAmount, numberOfNights);
							app_logs.info("Fee calculation: "+feeCalc);
							double feeExp = feeCalc + Double.parseDouble(AmountFees);
							app_logs.info("Fee Exp: "+feeExp);
							if (FolioFees == feeExp) {
								app_logs.info("Fees mathed");
								app_logs.info("Folio Fees: "+FolioFees);
								app_logs.info("Fee Value Fees: "+feeExp);
								
							}else {
								app_logs.info("Fees not mathed");
								app_logs.info("Folio Fees: "+FolioFees);
								app_logs.info("Fee Value Fees: "+feeExp);
							}
							
							ledgerAmounts.put("Room Charge", FolioRoomCharges);
							ledgerAmounts.put("Incidental", FolioIncidentals);
							ledgerAmounts.put("Taxes", FolioTaxes);
							//ledgerAmounts.put("Fees", FolioFees);
							ledgerAmounts.put("Fees", feeExp*numberOfRooms);

							app_logs.info("Ledger Amounts: " + ledgerAmounts);

							// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
							// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
							// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);

							app_logs.info("FolioRoomCharges " + FolioRoomCharges);
							app_logs.info("FolioIncidentals " + FolioIncidentals);
							app_logs.info("FolioTaxes " + FolioTaxes);
							app_logs.info("FolioFees " + FolioFees);
							app_logs.info("FolioTripTotal " + FolioTripTotal);

//						} catch (Exception e) {
//							if (Utility.reTry.get(test_name) == Utility.count) {
//								RetryFailedTestCases.count = Utility.reset_count;
//								Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
//								Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems",
//										driver);
//							} else {
//
//								Assert.assertTrue(false);
//							}
//						} catch (Error e) {
//							test_steps.add(e.toString());
//						}

					}

/*					// Item status
					try {

						if (itemStatuOptions.split(",").length == 1) {
							if (itemStatuOptions.equals("Pending")) {
								// folio.clickAddLineItemButton(driver);
								// folio.AddFolioLineItem(driver, "Laundry", "50");
							} else if (itemStatuOptions.equals("Posted")) {
								// folio.clickAddLineItemButton(driver);
								// folio.AddFolioLineItem(driver, "Spa", "70");
								// driver.findElement(By.xpath("//span[text()='Spa']/../../td[contains(@class,'changestatus')]")).click();
								// reservationPage.checkinReservation(driver, test_steps);
								reservationPage.CheckInButton(driver);
								reservationPage.generatGuestReportToggle(driver, test_steps, "No");
								reservationPage.CheckInConfrimButton(driver);

							}
							// driver.findElement(By.xpath("(//button[text()='Save'])[9]")).click();
						} else {
							System.out.println("Enter only one option for Item status");
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
*/
					// Payment
					try {
						//if (isPayment.equalsIgnoreCase("Yes")) {
						if (ledgerAccounts.containsKey("Payment Method") || ledgerAccounts.containsKey("Transfers") || 
								ledgerAccounts.containsKey("Account (Advanced Deposit)")) {
							driver.navigate().refresh();
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
							reservationPage.payButtonClickInTakePayment(driver, test_steps, "" + FolioTripTotal + "",
									true, true);
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
						if (LedgerAccount.equalsIgnoreCase("Travel Agent Commission") || LedgerAccount.equalsIgnoreCase("Distribution Method")) {
							reservationStatusOptions = "Departed";
						}
						reservationPage.changeReservationStatus(driver, reservationStatusOptions, mrbStatus, reservationNumber, itemStatuOptions, test_steps);

					} catch (Exception e) {
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to change Reservation status", testName,
									"ReservationStatus", driver);
						} else {

							Assert.assertTrue(false);
						}
					} catch (Error e) {
						test_steps.add(e.toString());
					}
					
					try {
						
						//reservationPage.click_Folio(driver, test_steps);
						//folio.folioTab(driver);
						folio.FolioTab(driver);
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
							
							if (folioItemValues.containsKey(items.get(i).getText())) {
								DecimalFormat df = new DecimalFormat("###.##");
								double temp = Double.parseDouble(df.format(Double.parseDouble(folioItemValues.get(items.get(i).getText())))) + 
										Double.parseDouble(itemsAmmount.get(i).getText().substring(2));
								
								
								app_logs.info("Ledger amount: "+temp);
								folioItemValues.put(items.get(i).getText(), df.format(temp));
							}else {
								folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().substring(2));
							}

						}
					}catch (Exception e) {
						if (Utility.reTry.get(test_name) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
							Utility.updateReport(e, "Failed to change Status", testName, "ItemsStatus", driver);
						}
						app_logs.info(e.toString());
					} catch (Error e) {
						test_steps.add(e.toString());
					}
					
					if (LedgerAccount.equalsIgnoreCase("Travel Agent Commission") || LedgerAccount.equalsIgnoreCase("Transfers") || 
							inputs.contains(Transfers) || LedgerAccount.equalsIgnoreCase("Distribution Method") || LedgerAccount.equalsIgnoreCase("Unit Revenues") || 
							LedgerAccount.equalsIgnoreCase("Unit Expenses")) {
						nav.Accounts(driver);
						//accountPage.OpenSearchedAccount(driver, accountName, test_steps);
						accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
						accountPage.OpenSearchedAccount(driver, accountName, test_steps);
						driver.navigate().refresh();
						accountPage.ClickFolio(driver);
						
						if (LedgerAccount.equalsIgnoreCase("Distribution Method")) {
							nav.Accounts(driver);
							//accountPage.OpenSearchedAccount(driver, accountName, test_steps);
							accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
							accountPage.OpenSearchedAccount(driver, accountName, test_steps);
							driver.navigate().refresh();
							accountPage.ClickFolio(driver);
							
							String amountDistribute = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
							accountPage.distributeAmountUnitOwners(driver, amountDistribute);
							ledgerAmounts.put("Distribution Method", Double.parseDouble(amountDistribute));
	
						}
						
						if (LedgerAccount.equalsIgnoreCase("Unit Revenues") || LedgerAccount.equalsIgnoreCase("Unit Expenses")) {
							nav.Accounts(driver);
							//accountPage.OpenSearchedAccount(driver, accountName, test_steps);
							accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
							accountPage.OpenSearchedAccount(driver, accountName, test_steps);
							driver.navigate().refresh();
							accountPage.ClickFolio(driver);
							
							// Running statements
							
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
					}
					


					app_logs.info("Reservation completed at: " + java.time.LocalTime.now());
					test_steps.add("Reservation completed at: " + java.time.LocalTime.now());
					Wait.wait60Second();
					Wait.wait60Second();

					app_logs.info("Running Report at " + java.time.LocalTime.now());
					test_steps.add("Running Report at " + java.time.LocalTime.now());

					// Select inputs
					try {

						try {

							nav.ReportsV2(driver);
							report.navigateToLedgerBalancesReport(driver);
							if (!DateRange.isEmpty()) {
								report.selectDateRange(driver, DateRange, test_steps);
							} else {
								report.selectStartdate(driver, StartDate, test_steps);
								report.selectEnddate(driver, EndDate, test_steps);
							}

							// availableTypes = report.getAllAvailableTypes(driver, test_steps);
							report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);
							// Utility.switchTab(driver, (new
							// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

							report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
//							if (!accountTypeOptions.equalsIgnoreCase("Reservations")) {
//								String accounts = accountTypeOptions + ",Reservations";
//								report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accounts);
//								// report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
//								// "Reservations");
//							} else {
//								report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
//										accountTypeOptions);
//							}
							report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
							if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
								report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
							}
							report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps,
									TaxExemptLedgerItemsOption);
							report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
							report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps,
									reservationStatusOptions);
							report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

							report.clickOnRunReport(driver);

						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
										driver);
							} else {

								Assert.assertTrue(false);
							}
						}

						try {

							test_steps.add("====  Ledger Balances Report Validation =====");
							test_steps.add("====  <b>Summary View Validation</b> =====");

							afterLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);

							// afterDetailsOfGivenCategory = report.getDetailsOfGivenCategory(driver, "Room
							// Charge", test_steps);
							// report.getDetailsOfGivenCategory(driver, "Tax", test_steps);
							afterDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver,
									test_steps);

							app_logs.info("Before: " + beforeLedgerCategoryDetails);
							test_steps.add("Before: " + beforeLedgerCategoryDetails);
							app_logs.info("After: " + afterLedgerCategoryDetails);
							test_steps.add("After: " + afterLedgerCategoryDetails);

							app_logs.info("Before Individual: " + beforeDetailsOfAllLedgerCategories);
							test_steps.add("Before Individual: " + beforeDetailsOfAllLedgerCategories);
							app_logs.info("After Individual: " + afterDetailsOfAllLedgerCategories);
							test_steps.add("After Individual: " + afterDetailsOfAllLedgerCategories);

							report.validateLedgerReportSummaryView(driver, beforeLedgerCategoryDetails,
									beforeDetailsOfAllLedgerCategories, afterLedgerCategoryDetails,
									afterDetailsOfAllLedgerCategories, ledgerAccounts, ledgerAmounts, folioItemValues,
									numberOfRooms, test_steps);

							// Payment Method
							if (!PaymentMethod.isEmpty()) {
								if (beforeDetailsOfAllLedgerCategories.containsKey(PaymentMethod)) {
									double pay = Double.parseDouble(
											beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1))
											- Double.parseDouble(
													beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
									app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

									if (pay == FolioTripTotal) {
										app_logs.info("Payment Method " + PaymentMethod
												+ " amount validated successfully in Summary View");
										test_steps.add("Payment Method " + PaymentMethod
												+ " amount validated successfully in Summary View");
									} else {
										app_logs.info("Failed - Payment Method " + PaymentMethod
												+ " amount in Summary View validation failed");
										test_steps.add("AssertionError : Failed - Payment Method " + PaymentMethod
												+ " amount in Summary View validation failed. Expected: "
												+ FolioTripTotal + " But found: " + pay);
									}

								} else {
									try {
										double pay = Double.parseDouble(
												beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
										app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

										if (pay == FolioTripTotal) {
											app_logs.info("Payment Method " + PaymentMethod
													+ " amount validated successfully in Summary View");
											test_steps.add("Payment Method " + PaymentMethod
													+ " amount validated successfully in Summary View");
										} else {
											app_logs.info("Failed - Payment Method " + PaymentMethod
													+ " amount in Summary View validation failed");
											test_steps.add("AssertionError : Failed - Payment Method " + PaymentMethod
													+ " amount in Summary View validation failed");
										}
									} catch (Exception e) {
										app_logs.info("Failed to get Payments Method details");
										test_steps.add("Failed to get Payments Method details");
									}

								}
							}

							beforeLedgerCategoryDetails.clear();
							beforeLedgerCategoryDetails = afterLedgerCategoryDetails;
							// beforeLedgerCategoryDetails.putAll(afterLedgerCategoryDetails);

							beforeDetailsOfAllLedgerCategories.clear();
							beforeDetailsOfAllLedgerCategories = afterDetailsOfAllLedgerCategories;

							app_logs.info("Detailed View: "
									+ report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));

						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to valdate Summary view", testName,
										"LedgerReportsSummaryView", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to valdate Summary view", testName,
										"LedgerReportsSummaryView", driver);
							} else {

								Assert.assertTrue(false);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Summary view after after Run report",
									testName, "RunReport-SummaryView", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Summary view after Run report", testName,
									"RunReport-SummaryView", driver);
						} else {
							Assert.assertTrue(false);
						}
					}

					test_steps.add("====  <b>Detailed View Validation</b> =====");
					// Detailed View
					try {
						accountName = accountName + " (" + accountNumber + ")";
						HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();

						report.validateLedgerReportDetailedView(driver, ledgerAccounts, folioItemValues, numberOfRooms,
								reservationNumbers, guestNames, accountName, arrivalDates, itemDescription, roomChargeAmount, currencySymbal,
								IsTaxExempt, dateFormat, processingMethod, test_steps);

						if (!PaymentMethod.isEmpty()) {
							TransactionDetails.clear();
							TransactionDetails = report.getTransactionDetailsList(driver, PaymentMethod, test_steps);

							if (TransactionDetails.get("Reservation #").contains(reservationNumber)) {
								app_logs.info("Reservation number " + reservationNumber + " is available under "
										+ PaymentMethod + " in Detailed View");
								test_steps.add("Reservation number " + reservationNumber + " is available under "
										+ PaymentMethod + " in Detailed View");
							} else {
								app_logs.info("Failed, Reservation number " + reservationNumber
										+ " is not available under " + PaymentMethod + " in Detailed View");
								test_steps.add("AssertionError - Failed, Reservation number " + reservationNumber
										+ " is not available under " + PaymentMethod + " in Detailed View");
							}

							if (TransactionDetails.get("Guest/Account Name").contains(guestName)) {
								app_logs.info("Guest Name " + guestName + " is available under " + PaymentMethod
										+ " in Detailed View");
								test_steps.add("Guest Name " + guestName + " is available under " + PaymentMethod
										+ " in Detailed View");
							} else {
								app_logs.info("Failed, Guest Name " + guestName + " is not available under "
										+ PaymentMethod + " in Detailed View");
								test_steps.add("AssertionError - Failed, Guest Name " + guestName
										+ " is not available under " + PaymentMethod + " in Detailed View");
							}

							if (TransactionDetails.get("Arrival Date")
									.contains(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"))) {
								app_logs.info(
										"Arrival Date " + Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
												+ " is available under " + PaymentMethod + " in Detailed View");
								test_steps.add(
										"Arrival Date " + Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
												+ " is available under " + PaymentMethod + " in Detailed View");
							} else {
								app_logs.info("Failed, Arrival Date "
										+ Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
										+ " is not available under " + PaymentMethod + " in Detailed View");
								test_steps.add("AssertionError - Failed, Arrival Date "
										+ Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")
										+ " is not available under " + PaymentMethod + " in Detailed View");
							}

							if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
								app_logs.info("Date " + Utility.getCurrentDate("MMM dd, yyyy") + " is available under "
										+ PaymentMethod + " in Detailed View");
								test_steps.add("Date " + Utility.getCurrentDate("MMM dd, yyyy") + " is available under "
										+ PaymentMethod + " in Detailed View");
							} else {
								app_logs.info("Failed, Date " + Utility.getCurrentDate("MMM dd, yyyy")
										+ " is not available under " + PaymentMethod + " in Detailed View");
								test_steps.add("AssertionError - Failed, Date " + Utility.getCurrentDate("MMM dd, yyyy")
										+ " is not available under " + PaymentMethod + " in Detailed View");
							}

							if (TransactionDetails.get("Amount").contains("$" + FolioTripTotal)) {
								app_logs.info("Amount " + FolioTripTotal + " is available under " + PaymentMethod
										+ " in Detailed View");
								test_steps.add("Amount " + FolioTripTotal + " is available under " + PaymentMethod
										+ " in Detailed View");
							} else {
								app_logs.info("Failed, Amount " + FolioTripTotal + " is not available under "
										+ PaymentMethod + " in Detailed View");
								test_steps.add("AssertionError - Failed, Amount " + FolioTripTotal
										+ " is not available under " + PaymentMethod + " in Detailed View");
							}
						}


//						driver.close();
//						Utility.switchTab(driver, 0);

						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

						// driver.close();
						// Utility.switchTab(driver, 0);

					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Detailed view after after Run report",
									testName, "RunReport-DetailedView", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Detailed view after after Run report",
									testName, "RunReport-DetailedView", driver);
						} else {
							Assert.assertTrue(false);
						}
					}

				} else {
					for (int n = 0; n < accountsOptions.length; n++) {

						accountType = accountsOptions[n];
						AmountIncidentals = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
						AmountRoomCharges = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
						AmountTaxes = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
						AmountFees = Utility.generateRandomNumberWithGivenNumberOfDigits(2);

						if (accountsOptions[n].equals("House Account")) {

							try {
								nav.Accounts(driver);
								test_steps.add("========== Creating House account ==========");
								app_logs.info("========== Creating House account ==========");
								accountName = accountName + Utility.generateRandomString();
								accountPage.ClickNewAccountbutton(driver, accountsOptions[n]);
								accountPage.AccountDetails(driver, accountsOptions[n], accountName);
								accountNumber = Utility.GenerateRandomString15Digit();
								accountPage.ChangeAccountNumber(driver, accountNumber);

								report.AccountSave(driver, test, accountName, test_steps);

								accountPage.closeAccountTab(driver);
								nav.cpReservationBackward(driver);
								reservationPage.click_NewReservation(driver, test_steps);

							} catch (Exception e) {

								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount",
											driver);
								} else {
									Assert.assertTrue(false);
								}

							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount",
											driver);
								} else {
									Assert.assertTrue(false);
								}
							}

						}

						else if (accountsOptions[n].equals("Gift Certificate")) {

							try {
								String AccountNumber = null;

								nav.Accounts(driver);
								test_steps.add("========== Creating Gift Certificate account ==========");
								app_logs.info("========== Creating Gift Certificate account ==========");
								accountName = accountName + Utility.generateRandomString();
								accountPage.ClickNewAccountbutton(driver, accountsOptions[n]);
								accountPage.AccountDetails(driver, accountsOptions[n], accountName);
								AccountNumber = Utility.GenerateRandomString15Digit();
								accountPage.ChangeAccountNumber(driver, AccountNumber);

								report.AccountSave(driver, test, accountName, test_steps);
								accountPage.ClickFolio(driver);
								accountPage.addLineitem1(driver, "", "Gift Certificate", "1000", test_steps);
								accountPage.Commit(driver);

								// report.AccountSave(driver, test, accountName, test_steps);
								// accountPage.Save(driver);
								accountPage.Save(driver, test, test_steps);

								accountPage.closeAccountTab(driver);
								nav.cpReservationBackward(driver);
								reservationPage.click_NewReservation(driver, test_steps);

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

						} else if (accountsOptions[n].equals("Travel Agent")) {
							ArrayList<String> TravelAgentItems = new ArrayList<>();
							int TravelAgetItemValue;

							nav.Accounts(driver);

							if (!TravelAgentCommission.isEmpty()) {

								accountPage.ClickTravelAgentItem(driver);

								if (accountPage.chekcTravelAgentItemAvailability(driver, test_steps)) {
									TravelAgentItems = accountPage.getAssociatedTravelAgentItems(driver, test_steps);
									TravelAgetItemValue = accountPage.getTravelAgentCommissionValue(driver, test_steps);

									app_logs.info("Travel agent items: " + TravelAgentItems);
									app_logs.info("Travel agent Value: " + TravelAgetItemValue);

								} else {
									accountPage.CreateNewTravelAgentItem(driver, "ItemName", "DisplayName",
											"Description", "10", "Category", "SelectTax");
								}

							}

							try {
								// nav.Accounts(driver);
								test_steps.add("========== Creating Travel Agent account ==========");
								app_logs.info("========== Creating Travel Agent account ==========");
								// accountName = accountName + Utility.generateRandomString();

								// accountPage.clickOnNewAccountButton(driver, test_steps, accountType);
								accountPage.ClickNewAccountbutton(driver, accountsOptions[n]);
								app_logs.info("Clicked on new Account");
								accountPage.AccountDetails(driver, accountsOptions[n], accountName);
								app_logs.info("Entered account details");
								accountNumber = Utility.GenerateRandomString15Digit();
								accountPage.ChangeAccountNumber(driver, accountNumber);

								accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
								accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber,
										alternativePhone, address1, address2, address3, email, city, state, postalCode,
										test_steps);
								accountPage.Billinginfo(driver, test, test_steps);
								report.AccountSave(driver, test, accountName, test_steps);
								test_steps.add("========== Travel Agent Account Created ==========");
								app_logs.info("========== Travel Agent Account Created ==========");

								accountPage.NewReservationButton(driver, test);

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

						else if (accountsOptions[n].equals("Unit Owners")) {

							// If Distribution method is given in Select inputs
							if (!DistributionMethod.isEmpty()) {

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
								unitOwnerItems = accountPage.getAssociatedUnitOwnerItemsList(driver, acName,
										category, test_steps);

							}

							try {
								test_steps.add("========== Creating Unit Owners account ==========");
								app_logs.info("========== Creating Unit Owners account ==========");
								accountName = accountName + Utility.generateRandomString();
								nav.Accounts(driver);
								accountPage.ClickNewAccountbutton(driver, accountsOptions[n]);
								accountPage.AccountDetails(driver, accountsOptions[n], accountName);
								accountNumber = Utility.GenerateRandomString15Digit();
								accountPage.ChangeAccountNumber(driver, accountNumber);

								accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
								accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber,
										alternativePhone, address1, address2, address3, email, city, state, postalCode,
										test_steps);
								accountPage.Billinginfo(driver, test, test_steps);
								// accountPage.associateRooms(driver, test_steps, RoomClass);
								report.AccountSave(driver, test, accountName, test_steps);

								accountPage.NewReservationButton(driver, test);

							} catch (Exception e) {

								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create Unit Owner account", testName,
											"UnitOwnerAccount", driver);
								} else {
									Assert.assertTrue(false);
								}

							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create Unit Owner account", testName,
											"UnitOwnerAccount", driver);
								} else {
									Assert.assertTrue(false);
								}
							}

						}

						else if (accountsOptions[n].equals("Corporate/Member Accounts")) {
							try {
								test_steps.add("========== Creating Corporate/Member Accounts account ==========");
								app_logs.info("========== Creating Corporate/Member Accounts account ==========");
								// accountName = accountName + Utility.generateRandomString();
								nav.Accounts(driver);
								accountPage.ClickNewAccountbutton(driver, accountsOptions[n]);
								accountPage.AccountDetails(driver, accountsOptions[n], accountName);
								accountNumber = Utility.GenerateRandomString15Digit();
								accountPage.ChangeAccountNumber(driver, accountNumber);

								accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
								accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber,
										alternativePhone, address1, address2, address3, email, city, state, postalCode,
										test_steps);
								accountPage.Billinginfo(driver, test, test_steps);
								report.AccountSave(driver, test, accountName, test_steps);

								accountPage.NewReservationButton(driver, test);

							} catch (Exception e) {

								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create corporate account", testName,
											"CorporateAccount", driver);
								} else {
									Assert.assertTrue(false);
								}

							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create corporate account", testName,
											"CorporateAccount", driver);
								} else {
									Assert.assertTrue(false);
								}
							}
						}

						else if (accountsOptions[n].equals("Group")) {

							try {
								test_steps.add("========== Creating Group account ==========");
								app_logs.info("========== Creating Group account ==========");

								nav.groups(driver);
								// group.click_NewAccount(driver, test, test_steps);
								group.type_GroupName(driver, test, accountName, test_steps);
								group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
								group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber,
										address1, city, state, country, postalCode, test_steps);
								group.Billinginfo(driver);
								group.Save(driver, test_steps);
								group.click_GroupNewReservation(driver, test_steps);

							} catch (Exception e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount",
											driver);
								} else {
									Assert.assertTrue(false);
								}
							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount",
											driver);
								} else {
									Assert.assertTrue(false);
								}
							}

						}

						else if (accountsOptions[n].equals("Reservations")) {

							try {
								reservationPage.click_NewReservation(driver, test_steps);

							} catch (Exception e) {

								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to click on new Reservation", testName,
											"NewReservation", driver);
								} else {
									Assert.assertTrue(false);
								}

							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to click on new Reservation", testName,
											"NewReservation", driver);
								} else {
									Assert.assertTrue(false);
								}
							}
						}

						// Reservations

						//if (isMRB.equalsIgnoreCase("Yes")) {
						if (ResType.equalsIgnoreCase("MRB")) {

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

								guestNames.clear();
								for (int i = 0; i < numberOfRooms; i++) {
									String[] fNames = mrbGuestFirstName.split("\\|");
									String[] lNames = mrbGuestLastName.split("\\|");
									app_logs.info("Fanmes: " + fNames.length);
									app_logs.info("Lanmes: " + lNames.length);
									app_logs.info("Loop " + i);
									guestNames.add(fNames[i] + " " + lNames[i]);
								}

								app_logs.info("Guest Names: " + guestNames);
								
								arrivalDates.clear();
								for (int i = 0; i < numberOfRooms; i++) {
									String[] CheckInDates = CheckInDate.split("\\|");							
									
									if (clientDateFormat.equalsIgnoreCase("USA")) {
										arrivalDates.add(CheckInDates[i]);
									}else if (clientDateFormat.equalsIgnoreCase("International")) {
										arrivalDates.add(Utility.parseDate(CheckInDates[i], "MMM dd, yyyy", "dd MMM, yyyy"));
									}						
								}
								
								dates.clear();
								for (int i = 0; i < numberOfRooms; i++) {
									if (clientDateFormat.equalsIgnoreCase("USA")) {
										dates.add(Utility.getCurrentDate("MMM dd, yyyy"));
									}else if (clientDateFormat.equalsIgnoreCase("International")) {
										dates.add(Utility.getCurrentDate("dd MMM, yyyy"));
									}							
								}

								// reservationPage.click_NewReservation(driver, test_steps);
								// guest1 = MrbGuestFirstName.split("|")[1] + MrbGuestLastName.split("|")[1];
								// guest2 = MrbGuestFirstName.split("|")[2] + MrbGuestLastName.split("|")[2];

								report.mrbReservation(driver, CheckInDate, CheckOutDate, mrbAdults, mrbChildren,
										mrbRateplan, PromoCode, IsSplitRes, mrbRoomClass, account, mrbSalutation,
										mrbGuestFirstName, mrbGuestLastName, mrbPhoneNumber, alternativePhone, mrbEmail,
										accountType, address1, address2, address3, city, country, state, postalCode,
										getRoomClasses, PaymentType, CardNumber, NameOnCard, CardExpDate, accountName,
										resNumberPayment, IsTaxExempt, TaxExemptID, TravelAgent, marketSegment,
										referral, reservationNumber, numberOfRooms, reservationNumbers, arrivalDates,
										status, roomCost, test_steps);
								reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
								reservationPage.click_Folio(driver, test_steps);

								roomChargeAmount = report.getFolioAmountExcludingCurrency(driver,
										driver.findElement(By.xpath(
												"(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
												.getText());
								app_logs.info("Room Charge: " + roomChargeAmount);

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

						else if (ResType.equalsIgnoreCase("Single")) {
						
							test_steps.add("==== Creating Single Reservation ====");
							try {
								guestName = guestFirstName + " " + guestLastName;
								//date = Utility.getCurrentDate("MMM dd,yyyy");
								if (clientDateFormat.equalsIgnoreCase("USA")) {
									date = Utility.getCurrentDate("MMM dd, yyyy");
								}else if (clientDateFormat.equalsIgnoreCase("International")) {
									date = Utility.getCurrentDate("dd MMM, yyyy");
								}

								guestNames.clear();
								guestNames.add(guestName);
								arrivalDates.clear();
								if (clientDateFormat.equalsIgnoreCase("USA")) {
									arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
								}else {
									arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "dd MMM, yyyy"));
								}

								report.singleReservation(driver, CheckInDate, CheckOutDate, Adults, Children, Rateplan,
										PromoCode, RoomClass, depositAmount, salutation, guestFirstName, guestLastName,
										phoneNumber, alternativePhone, email, account, accountType, address1, address2,
										address3, city, country, state, postalCode, IsGuesProfile, resNumberPayment,
										PaymentType, CardNumber, NameOnCard, CardExpDate, accountName, IsTaxExempt,
										TaxExemptID, marketSegment, referral, reservationNumber, reservationNumbers,
										status, reservationStatusOptions,  roomNumber, test_steps);
								reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
								reservationPage.click_Folio(driver, test_steps);

								roomChargeAmount = report.getFolioAmountExcludingCurrency(driver,
										driver.findElement(By.xpath(
												"(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
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
						}

						//if (isMRB.equalsIgnoreCase("Yes")) {
						if (ResType.equalsIgnoreCase("MRB")) {

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
										driver.findElement(By.xpath(
												"(//span[contains(text(),'Room Charge')]//following::td)[4]/span"))
												.getText());
								app_logs.info("Room Charge: " + roomChargeAmount);

								if (!Incidentals.isEmpty()) {
									if (Incidentals.split(",").length == 1) {
										folio.clickAddLineItemButton(driver);
										folio.AddFolioLineItem(driver, Incidentals, AmountIncidentals);
										app_logs.info(
												"Incidentals - " + Incidentals + " - " + AmountIncidentals + " added");
										test_steps.add(
												"Incidentals - " + Incidentals + " - " + AmountIncidentals + " added");

										if (itemStatuOptions.equalsIgnoreCase("void")) {
											//folio.VoidLineItem(driver, Incidentals);
											folio.voidLineItem(driver, Incidentals, "test");
										}
										folio.ClickSaveFolioButton(driver);

									} else {
										String[] inc = Incidentals.split(",");
										for (int i = 0; i < inc.length; i++) {
											folio.clickAddLineItemButton(driver);
											folio.AddFolioLineItem(driver, inc[i], AmountIncidentals);
											app_logs.info(
													"Incidentals - " + inc[i] + " - " + AmountIncidentals + " added");
											test_steps.add(
													"Incidentals - " + inc[i] + " - " + AmountIncidentals + " added");

											if (itemStatuOptions.equalsIgnoreCase("void")) {
												folio.VoidLineItem(driver, inc[i]);
											}
										}
										folio.ClickSaveFolioButton(driver);
									}
								}

								if (!RoomCharges.isEmpty()) {

									if (RoomCharges.split(",").length == 1) {
										if (!RoomCharges.equalsIgnoreCase("Room Charge")) {
											folio.clickAddLineItemButton(driver);
											folio.AddFolioLineItem(driver, RoomCharges, AmountRoomCharges);
											// driver.findElement(By.xpath("//span[text()='"+RoomCharges+"']/../../td[contains(@class,'changestatus')]")).click();
											app_logs.info("Room Charge - " + RoomCharges + " - " + AmountRoomCharges
													+ " added");
											test_steps.add("Room Charge - " + RoomCharges + " - " + AmountRoomCharges
													+ " added");

											if (itemStatuOptions.equalsIgnoreCase("void")) {
												//folio.VoidLineItem(driver, RoomCharges);
												folio.voidLineItem(driver, RoomCharges, "test");
											}
											folio.ClickSaveFolioButton(driver);
										}

									} else {
										String[] rc = RoomCharges.split(",");
										for (int i = 0; i < rc.length; i++) {

											if (!rc[i].equalsIgnoreCase("Room Charge")) {
												folio.clickAddLineItemButton(driver);
												folio.AddFolioLineItem(driver, rc[i], AmountRoomCharges);
												// driver.findElement(By.xpath("//span[text()='"+rc[i]+"']/../../td[contains(@class,'changestatus')]")).click();
												app_logs.info("Room Charge - " + rc[i] + " - " + AmountRoomCharges
														+ " added");
												test_steps.add("Room Charge - " + rc[i] + " - " + AmountRoomCharges
														+ " added");

												if (itemStatuOptions.equalsIgnoreCase("void")) {
													//folio.VoidLineItem(driver, rc[i]);
													folio.voidLineItem(driver, rc[i], "test");
												}
											}

										}
										folio.ClickSaveFolioButton(driver);
									}
								}

								if (!Taxes.isEmpty()) {
									if (Taxes.split(",").length == 1) {
										folio.clickAddLineItemButton(driver);
										folio.AddFolioLineItem(driver, Taxes, AmountTaxes);

										app_logs.info("Taxes - " + Taxes + " - " + AmountTaxes + " added");
										test_steps.add("Taxes - " + Taxes + " - " + AmountTaxes + " added");

										if (itemStatuOptions.equalsIgnoreCase("void")) {
											//folio.VoidLineItem(driver, Taxes);
											folio.voidLineItem(driver, Taxes, "test");
										}
										folio.ClickSaveFolioButton(driver);

									} else {
										String[] tax = Taxes.split(",");
										for (int i = 0; i < tax.length; i++) {
											folio.clickAddLineItemButton(driver);
											folio.AddFolioLineItem(driver, tax[i], AmountTaxes);

											app_logs.info("Taxes - " + tax[i] + " - " + AmountTaxes + " added");
											test_steps.add("Taxes - " + tax[i] + " - " + AmountTaxes + " added");

											if (itemStatuOptions.equalsIgnoreCase("void")) {
												//folio.VoidLineItem(driver, tax[i]);
												folio.voidLineItem(driver, tax[i], "test");
											}
										}
										folio.ClickSaveFolioButton(driver);
									}
								}

								if (!Fees.isEmpty()) {
									if (Fees.split(",").length == 1) {
										folio.clickAddLineItemButton(driver);
										folio.AddFolioLineItem(driver, Fees, AmountFees);

										app_logs.info("Fees - " + Fees + " - " + AmountFees + " added");
										test_steps.add("Fees - " + Fees + " - " + AmountFees + " added");

										if (itemStatuOptions.equalsIgnoreCase("void")) {
											//folio.VoidLineItem(driver, Fees);
											folio.voidLineItem(driver, Fees, "test");
										}
										folio.ClickSaveFolioButton(driver);
									} else {
										String[] fee = Fees.split(",");
										for (int i = 0; i < fee.length; i++) {
											folio.clickAddLineItemButton(driver);
											folio.AddFolioLineItem(driver, fee[i], AmountFees);

											app_logs.info("Fees - " + fee[i] + " - " + AmountFees + " added");
											test_steps.add("Fees - " + fee[i] + " - " + AmountFees + " added");

											if (itemStatuOptions.equalsIgnoreCase("void")) {
												//folio.VoidLineItem(driver, fee[i]);
												folio.voidLineItem(driver, fee[i], "test");
											}
										}
										folio.ClickSaveFolioButton(driver);
									}
								}

								// folio.ClickSaveFolioButton(driver);
								
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

								itemDescription.clear();
								folioItemValues.clear();
								for (int i = 0; i < items.size(); i++) {
									// List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
									// itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(),
									// cells.get(7).findElement(By.tagName("a")).getText());
									itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
									folioItemValues.put(items.get(i).getText(),
											itemsAmmount.get(i).getText().substring(2));

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
								// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
								// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
								// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);

								app_logs.info("FolioRoomCharges " + FolioRoomCharges);
								app_logs.info("FolioIncidentals " + FolioIncidentals);
								app_logs.info("FolioTaxes " + FolioTaxes);
								app_logs.info("FolioFees " + FolioFees);

							} catch (Exception e) {
								if (Utility.reTry.get(test_name) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to add Folio Line Items", testName,
											"FolioLineItems", driver);
								} else {

									Assert.assertTrue(false);
								}
							} catch (Error e) {
								test_steps.add(e.toString());
							}

							app_logs.info("Before folio loop try block");
							try {

								app_logs.info("Before folio loop");

								for (int k = 2; k <= numberOfRooms; k++) {
									app_logs.info("Entered into folio loop");
									reservationPage.mrbChangeFolio(driver, k, test_steps);
									test_steps.add("========== Adding Line Items ==========");
									app_logs.info("========== Adding Line Items ==========");

									Wait.wait5Second();

									if (!Incidentals.isEmpty()) {
										if (Incidentals.split(",").length == 1) {
											folio.clickAddLineItemButton(driver);
											folio.AddFolioLineItem(driver, Incidentals, AmountIncidentals);
											app_logs.info("Incidentals - " + Incidentals + " - " + AmountIncidentals
													+ " added");
											test_steps.add("Incidentals - " + Incidentals + " - " + AmountIncidentals
													+ " added");

											if (itemStatuOptions.equalsIgnoreCase("void")) {
												//folio.VoidLineItem(driver, Incidentals);
												folio.voidLineItem(driver, Incidentals, "test");
											}
											folio.ClickSaveFolioButton(driver);

										} else {
											String[] inc = Incidentals.split(",");
											for (int i = 0; i < inc.length; i++) {
												folio.clickAddLineItemButton(driver);
												folio.AddFolioLineItem(driver, inc[i], AmountIncidentals);
												app_logs.info("Incidentals - " + inc[i] + " - " + AmountIncidentals
														+ " added");
												test_steps.add("Incidentals - " + inc[i] + " - " + AmountIncidentals
														+ " added");

												if (itemStatuOptions.equalsIgnoreCase("void")) {
													folio.VoidLineItem(driver, inc[i]);
												}
											}
											folio.ClickSaveFolioButton(driver);
										}
									}

									if (!RoomCharges.isEmpty()) {

										if (RoomCharges.split(",").length == 1) {
											if (!RoomCharges.equalsIgnoreCase("Room Charge")) {
												folio.clickAddLineItemButton(driver);
												folio.AddFolioLineItem(driver, RoomCharges, AmountRoomCharges);
												// driver.findElement(By.xpath("//span[text()='"+RoomCharges+"']/../../td[contains(@class,'changestatus')]")).click();
												app_logs.info("Room Charge - " + RoomCharges + " - " + AmountRoomCharges
														+ " added");
												test_steps.add("Room Charge - " + RoomCharges + " - "
														+ AmountRoomCharges + " added");

												if (itemStatuOptions.equalsIgnoreCase("void")) {
													//folio.VoidLineItem(driver, RoomCharges);
													folio.voidLineItem(driver, RoomCharges, "test");
												}
												folio.ClickSaveFolioButton(driver);
											}

										} else {
											String[] rc = RoomCharges.split(",");
											for (int i = 0; i < rc.length; i++) {

												if (!rc[i].equalsIgnoreCase("Room Charge")) {
													folio.clickAddLineItemButton(driver);
													folio.AddFolioLineItem(driver, rc[i], AmountRoomCharges);
													// driver.findElement(By.xpath("//span[text()='"+rc[i]+"']/../../td[contains(@class,'changestatus')]")).click();
													app_logs.info("Room Charge - " + rc[i] + " - " + AmountRoomCharges
															+ " added");
													test_steps.add("Room Charge - " + rc[i] + " - " + AmountRoomCharges
															+ " added");

													if (itemStatuOptions.equalsIgnoreCase("void")) {
														//folio.VoidLineItem(driver, rc[i]);
														folio.voidLineItem(driver, rc[i], "test");
													}
												}

											}
											folio.ClickSaveFolioButton(driver);
										}
									}

									if (!Taxes.isEmpty()) {
										if (Taxes.split(",").length == 1) {
											folio.clickAddLineItemButton(driver);
											folio.AddFolioLineItem(driver, Taxes, AmountTaxes);

											app_logs.info("Taxes - " + Taxes + " - " + AmountTaxes + " added");
											test_steps.add("Taxes - " + Taxes + " - " + AmountTaxes + " added");

											if (itemStatuOptions.equalsIgnoreCase("void")) {
												//folio.VoidLineItem(driver, Taxes);
												folio.voidLineItem(driver, Taxes, "test");
											}
											folio.ClickSaveFolioButton(driver);

										} else {
											String[] tax = Taxes.split(",");
											for (int i = 0; i < tax.length; i++) {
												folio.clickAddLineItemButton(driver);
												folio.AddFolioLineItem(driver, tax[i], AmountTaxes);

												app_logs.info("Taxes - " + tax[i] + " - " + AmountTaxes + " added");
												test_steps.add("Taxes - " + tax[i] + " - " + AmountTaxes + " added");

												if (itemStatuOptions.equalsIgnoreCase("void")) {
													//folio.VoidLineItem(driver, tax[i]);
													folio.voidLineItem(driver, tax[i], "test");
												}
											}
											folio.ClickSaveFolioButton(driver);
										}
									}

									if (!Fees.isEmpty()) {
										if (Fees.split(",").length == 1) {
											folio.clickAddLineItemButton(driver);
											folio.AddFolioLineItem(driver, Fees, AmountFees);

											app_logs.info("Fees - " + Fees + " - " + AmountFees + " added");
											test_steps.add("Fees - " + Fees + " - " + AmountFees + " added");

											if (itemStatuOptions.equalsIgnoreCase("void")) {
												//folio.VoidLineItem(driver, Fees);
												folio.voidLineItem(driver, Fees, "test");
											}
											folio.ClickSaveFolioButton(driver);
										} else {
											String[] fee = Fees.split(",");
											for (int i = 0; i < fee.length; i++) {
												folio.clickAddLineItemButton(driver);
												folio.AddFolioLineItem(driver, fee[i], AmountFees);

												app_logs.info("Fees - " + fee[i] + " - " + AmountFees + " added");
												test_steps.add("Fees - " + fee[i] + " - " + AmountFees + " added");

												if (itemStatuOptions.equalsIgnoreCase("void")) {
													//folio.VoidLineItem(driver, fee[i]);
													folio.voidLineItem(driver, fee[i], "test");
												}
											}
											folio.ClickSaveFolioButton(driver);
										}
									}
									// folio.ClickSaveFolioButton(driver);

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

									FolioTripTotal = FolioTripTotal + report.getFolioAmountExcludingCurrency(driver,
											reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
									FolioPaid = FolioPaid + report.getFolioAmountExcludingCurrency(driver,
											reservationPage.get_PaymentsWithCurrency(driver, test_steps));
									FolioBalance = FolioBalance + report.getFolioAmountExcludingCurrency(driver,
											reservationPage.get_BalanceWithCurrency(driver, test_steps));
									// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
									// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
									// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);
									int numberOfNights = Integer.parseInt(Utility.differenceBetweenDates(CheckInDate, CheckOutDate));
									app_logs.info("number of nights: "+numberOfNights);
									//double feeCalc = report.feeCalculation(driver, feeValue, roomChargeAmount, numberOfNights);
									feeCalc = report.feeCalculationsAll(driver, feeValueList, roomChargeAmount, numberOfNights);
									app_logs.info("Fee calculation: "+feeCalc);
									double feeExp = feeCalc + Double.parseDouble(AmountFees);
									app_logs.info("Fee Exp: "+feeExp);
									if (FolioFees == feeExp) {
										app_logs.info("Fees mathed");
										app_logs.info("Folio Fees: "+FolioFees);
										app_logs.info("Fee Value Fees: "+feeExp);
										
									}else {
										app_logs.info("Fees not mathed");
										app_logs.info("Folio Fees: "+FolioFees);
										app_logs.info("Fee Value Fees: "+feeExp);
									}
									
									ledgerAmounts.put("Room Charge", FolioRoomCharges);
									ledgerAmounts.put("Incidental", FolioIncidentals);
									ledgerAmounts.put("Taxes", FolioTaxes);
									//ledgerAmounts.put("Fees", FolioFees);
									ledgerAmounts.put("Fees", feeExp*numberOfRooms);
									app_logs.info("FolioTripTotal " + FolioTripTotal);

									ledgerAmounts.put("Room Charge", FolioRoomCharges);
									ledgerAmounts.put("Incidental", FolioIncidentals);
									ledgerAmounts.put("Taxes", FolioTaxes);
									ledgerAmounts.put("Fees", FolioFees);

									app_logs.info("Ledger Amounts: " + ledgerAmounts);

								}

							} catch (Exception e) {
								if (Utility.reTry.get(test_name) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to add Folio Line Items", testName,
											"FolioLineItems", driver);
								} else {

									Assert.assertTrue(false);
								}
							} catch (Error e) {
								test_steps.add(e.toString());
							}

						} else {

							try {
								test_steps.add("========== Adding Line Items ==========");
								app_logs.info("========== Adding Line Items ==========");

								folio.folioTab(driver);
								test_steps.add("Clicked Folio Tab");
								app_logs.info("Clicked Folio Tab");

								report.addFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatuOptions, test_steps);

								// folio.ClickSaveFolioButton(driver);

								// String strItems = "//table[contains(@class,'table-foliogrid')]//tr";

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

								ledgerAmounts.put("Room Charge", FolioRoomCharges);
								ledgerAmounts.put("Incidental", FolioIncidentals);
								ledgerAmounts.put("Taxes", FolioTaxes);
								ledgerAmounts.put("Fees", FolioFees);

								app_logs.info("Ledger Amounts: " + ledgerAmounts);

								FolioTripTotal = report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
								FolioPaid = report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_PaymentsWithCurrency(driver, test_steps));
								FolioBalance = report.getFolioAmountExcludingCurrency(driver,
										reservationPage.get_BalanceWithCurrency(driver, test_steps));

								// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
								// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
								// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);

								int numberOfNights = Integer.parseInt(Utility.differenceBetweenDates(CheckInDate, CheckOutDate));
								app_logs.info("number of nights: "+numberOfNights);
								//double feeCalc = report.feeCalculation(driver, feeValue, roomChargeAmount, numberOfNights);
								feeCalc = report.feeCalculationsAll(driver, feeValueList, roomChargeAmount, numberOfNights);
								app_logs.info("Fee calculation: "+feeCalc);
								double feeExp = feeCalc + Double.parseDouble(AmountFees);
								app_logs.info("Fee Exp: "+feeExp);
								if (FolioFees == feeExp) {
									app_logs.info("Fees mathed");
									app_logs.info("Folio Fees: "+FolioFees);
									app_logs.info("Fee Value Fees: "+feeExp);
									
								}else {
									app_logs.info("Fees not mathed");
									app_logs.info("Folio Fees: "+FolioFees);
									app_logs.info("Fee Value Fees: "+feeExp);
								}
								
								ledgerAmounts.put("Room Charge", FolioRoomCharges);
								ledgerAmounts.put("Incidental", FolioIncidentals);
								ledgerAmounts.put("Taxes", FolioTaxes);
								//ledgerAmounts.put("Fees", FolioFees);
								ledgerAmounts.put("Fees", feeExp*numberOfRooms);
								app_logs.info("FolioTripTotal " + FolioTripTotal);

							} catch (Exception e) {
								if (Utility.reTry.get(test_name) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to add Folio Line Items", testName,
											"FolioLineItems", driver);
								} else {

									Assert.assertTrue(false);
								}
							} catch (Error e) {
								test_steps.add(e.toString());
							}

						}

						// Item status
/*						try {

							if (itemStatuOptions.split(",").length == 1) {
								if (itemStatuOptions.equals("Pending")) {
									// folio.clickAddLineItemButton(driver);
									// folio.AddFolioLineItem(driver, "Laundry", "50");
								} else if (itemStatuOptions.equals("Posted")) {
									// folio.clickAddLineItemButton(driver);
									// folio.AddFolioLineItem(driver, "Spa", "70");
									// driver.findElement(By.xpath("//span[text()='Spa']/../../td[contains(@class,'changestatus')]")).click();
									// reservationPage.checkinReservation(driver, test_steps);
									reservationPage.CheckInButton(driver);
									reservationPage.generatGuestReportToggle(driver, test_steps, "No");
									reservationPage.CheckInConfrimButton(driver);

								}
								// driver.findElement(By.xpath("(//button[text()='Save'])[9]")).click();
							} else {
								System.out.println("Enter only one option for Item status");
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
*/
						// Payment
						try {

							//if (isPayment.equalsIgnoreCase("Yes")) {
							if (ledgerAccounts.containsKey("Payment Method")) {
								driver.navigate().refresh();
								try {
									driver.findElement(By.xpath("(//button[contains(text(),'TAKE PAYMENT')])[1]"))
											.click();
								} catch (Exception e) {
									Wait.wait5Second();
									driver.findElement(By.xpath("(//button[contains(text(),'TAKE PAYMENT')])[1]"))
											.click();
								}

								app_logs.info("Clicked on Take Payment");
								// reservationPage.takePayment(driver, unitOwnerItems, PaymentType, CardNumber,
								// NameOnCard, CardExpDate, TakePaymentType, IsChangeInPayAmount,
								// ChangedAmountValue, IsSetAsMainPaymentMethod, AddPaymentNotes)
								reservationPage.payButtonClickInTakePayment(driver, test_steps,
										"" + FolioTripTotal + "", true, true);
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
							reservationPage.changeReservationStatus(driver, reservationStatusOptions, mrbStatus, reservationNumber, itemStatuOptions, test_steps);

						} catch (Exception e) {
							if (Utility.reTry.get(test_name) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to change Reservation status", testName,
										"ReservationStatus", driver);
							} else {

								Assert.assertTrue(false);
							}
						} catch (Error e) {
							test_steps.add(e.toString());
						}
						
						try {
							reservationPage.clickFolio(driver, test_steps);
							
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
							itemDescription.clear();
							folioItemValues.clear();
							for (int i = 0; i < items.size(); i++) {
								// List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
								// itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(),
								// cells.get(7).findElement(By.tagName("a")).getText());
								itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
								folioItemValues.put(items.get(i).getText(),
										itemsAmmount.get(i).getText().substring(2));

							}

							app_logs.info("Items Description: " + itemDescription);
							app_logs.info("Items Values: " + folioItemValues);
						}catch (Exception e) {
							if (Utility.reTry.get(test_name) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to change Reservation status", testName,
										"ReservationStatus", driver);
							} else {

								Assert.assertTrue(false);
							}
						} catch (Error e) {
							test_steps.add(e.toString());
						}
						
						if (LedgerAccount.equalsIgnoreCase("Travel Agent Commission") || LedgerAccount.equalsIgnoreCase("Transfers") || 
								inputs.contains(Transfers) ) {
							nav.Accounts(driver);
							//accountPage.OpenSearchedAccount(driver, accountName, test_steps);
							accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
							accountPage.OpenSearchedAccount(driver, accountName, test_steps);
							driver.navigate().refresh();
							accountPage.ClickFolio(driver);
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
						}

						app_logs.info("Reservation completed at: " + java.time.LocalTime.now());
						test_steps.add("Reservation completed at: " + java.time.LocalTime.now());
						Wait.wait60Second();
						Wait.wait60Second();

						app_logs.info("Running Report at " + java.time.LocalTime.now());
						test_steps.add("Running Report at " + java.time.LocalTime.now());

						// Select inputs
						try {

							try {

								nav.ReportsV2(driver);
								report.navigateToLedgerBalancesReport(driver);
								availableTypes = report.getAllAvailableTypes(driver, test_steps);
								report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);
								// Utility.switchTab(driver, (new
								// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

								report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
										accountTypeOptions);
//								if (!accountTypeOptions.equalsIgnoreCase("Reservations")) {
//									String accounts = accountTypeOptions + ",Reservations";
//									report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
//											accountTypeOptions);
//									// report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
//									// "Reservations");
//								}
								report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
								if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
									report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
								}
								report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps,
										TaxExemptLedgerItemsOption);
								report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps,
										marketSegmentOption);
								report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps,
										reservationStatusOptions);
								report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

								report.clickOnRunReport(driver);

							} catch (Exception e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to Run Report after Reservation", testName,
											"RunReport", driver);
								} else {
									Assert.assertTrue(false);
								}
							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to Run Report after Reservation", testName,
											"RunReport", driver);
								} else {

									Assert.assertTrue(false);
								}
							}

							try {

								test_steps.add("====  Ledger Balances Report Validation =====");
								test_steps.add("====  <b>Summary View Validation</b> =====");

								afterLedgerCategoryDetails.clear();
								afterDetailsOfAllLedgerCategories.clear();

								afterLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);

								// afterDetailsOfGivenCategory = report.getDetailsOfGivenCategory(driver, "Room
								// Charge", test_steps);
								// report.getDetailsOfGivenCategory(driver, "Tax", test_steps);
								afterDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver,
										test_steps);

								app_logs.info("Before: " + beforeLedgerCategoryDetails);
								test_steps.add("Before: " + beforeLedgerCategoryDetails);
								app_logs.info("After: " + afterLedgerCategoryDetails);
								test_steps.add("After: " + afterLedgerCategoryDetails);

								app_logs.info("Before Individual: " + beforeDetailsOfAllLedgerCategories);
								test_steps.add("Before Individual: " + beforeDetailsOfAllLedgerCategories);
								app_logs.info("After Individual: " + afterDetailsOfAllLedgerCategories);
								test_steps.add("After Individual: " + afterDetailsOfAllLedgerCategories);

								report.validateLedgerReportSummaryView(driver, beforeLedgerCategoryDetails,
										beforeDetailsOfAllLedgerCategories, afterLedgerCategoryDetails,
										afterDetailsOfAllLedgerCategories, ledgerAccounts, ledgerAmounts,
										folioItemValues, numberOfRooms, test_steps);

//								for (int i = 0; i < availableTypes.size(); i++) {
//									
//								}

								// Payment Method
								if (!PaymentMethod.isEmpty()) {
									if (beforeDetailsOfAllLedgerCategories.containsKey(PaymentMethod)) {
										double pay = Double.parseDouble(
												beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1))
												- Double.parseDouble(beforeDetailsOfAllLedgerCategories
														.get(PaymentMethod).substring(1));
										app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

										if (pay == FolioTripTotal) {
											app_logs.info("Payment Method " + PaymentMethod
													+ " amount validated successfully in Summary View");
											test_steps.add("Payment Method " + PaymentMethod
													+ " amount validated successfully in Summary View");
										} else {
											app_logs.info("Failed - Payment Method " + PaymentMethod
													+ " amount in Summary View validation failed");
											test_steps.add("AssertionError : Failed - Payment Method " + PaymentMethod
													+ " amount in Summary View validation failed. Expected: "
													+ FolioTripTotal + " But found: " + pay);
										}

									} else {
										try {
											double pay = Double.parseDouble(
													beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
											app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

											if (pay == FolioTripTotal) {
												app_logs.info("Payment Method " + PaymentMethod
														+ " amount validated successfully in Summary View");
												test_steps.add("Payment Method " + PaymentMethod
														+ " amount validated successfully in Summary View");
											} else {
												app_logs.info("Failed - Payment Method " + PaymentMethod
														+ " amount in Summary View validation failed");
												test_steps.add("AssertionError : Failed - Payment Method "
														+ PaymentMethod + " amount in Summary View validation failed");
											}
										} catch (Exception e) {
											app_logs.info("Failed to get Payments Method details");
											test_steps.add("Failed to get Payments Method details");
										}

									}
								}

								beforeLedgerCategoryDetails.clear();
								beforeLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);
								// beforeLedgerCategoryDetails.putAll(afterLedgerCategoryDetails);

								beforeDetailsOfAllLedgerCategories.clear();
								beforeDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver,
										test_steps);

							} catch (Exception e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to valdate Summary view", testName,
											"LedgerReportsSummaryView", driver);
								} else {
									Assert.assertTrue(false);
								}
							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
									Utility.updateReport(e, "Failed to valdate Summary view", testName,
											"LedgerReportsSummaryView", driver);
								} else {

									Assert.assertTrue(false);
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e,
										"Failed to validate Reservation details in Summary view after after Run report",
										testName, "RunReport-SummaryView", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e,
										"Failed to validate Reservation details in Summary view after Run report",
										testName, "RunReport-SummaryView", driver);
							} else {
								Assert.assertTrue(false);
							}
						}

						test_steps.add("====  <b>Detailed View Validation</b> =====");
						// Detailed View
						try {

							HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();

							report.validateLedgerReportDetailedView(driver, ledgerAccounts, folioItemValues,
									numberOfRooms, reservationNumbers, guestNames, accountName, arrivalDates, itemDescription,
									roomChargeAmount, currencySymbal, IsTaxExempt, dateFormat, processingMethod, test_steps);

							driver.close();
							Utility.switchTab(driver, 0);
							nav.Reservation_Backward_3(driver);

							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

						} catch (Exception e) {
							e.printStackTrace();
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e,
										"Failed to validate Reservation details in Detailed view after after Run report",
										testName, "RunReport-DetailedView", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e,
										"Failed to validate Reservation details in Detailed view after after Run report",
										testName, "RunReport-DetailedView", driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}

				}

			} else {

				for (int n = 0; n < accountsOptions.length; n++) {

					accountType = accountsOptions[n];
					app_logs.info("Account Type: " + accountType);

					if (accountType.equals("House Account")) {
						// New account
						try {
							nav.Accounts(driver);
							test_steps.add("========== Creating account ==========");
							app_logs.info("========== Creating account ==========");
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
								Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}

						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount",
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

							nav.Accounts(driver);
							test_steps.add("========== Creating account ==========");
							app_logs.info("========== Creating account ==========");
							accountName = accountName + Utility.generateRandomString();
							accountPage.ClickNewAccountbutton(driver, accountType);
							accountPage.AccountDetails(driver, accountType, accountName);
							AccountNumber = Utility.GenerateRandomString15Digit();
							accountPage.ChangeAccountNumber(driver, AccountNumber);

							report.AccountSave(driver, test, accountName, test_steps);
							accountPage.ClickFolio(driver);
							accountPage.addLineitem1(driver, "Bucksport Motor Inn", "Gift Certificate", "1000",
									test_steps);
							accountPage.Commit(driver);

							// report.AccountSave(driver, test, accountName, test_steps);
							// accountPage.Save(driver);
							accountPage.Save(driver, test, test_steps);

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

						if (!TravelAgentCommission.isEmpty()) {

							accountPage.ClickTravelAgentItem(driver);

							if (accountPage.chekcTravelAgentItemAvailability(driver, test_steps)) {
								TravelAgentItems = accountPage.getAssociatedTravelAgentItems(driver, test_steps);
								TravelAgetItemValue = accountPage.getTravelAgentCommissionValue(driver, test_steps);

								app_logs.info("Travel agent items: " + TravelAgentItems);
								app_logs.info("Travel agent Value: " + TravelAgetItemValue);

							} else {
								accountPage.CreateNewTravelAgentItem(driver, "ItemName", "DisplayName", "Description",
										"10", "Category", "SelectTax");
							}

						}

						try {
							// nav.Accounts(driver);
							test_steps.add("========== Creating account ==========");
							app_logs.info("========== Creating account ==========");
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

							// accountPage.NewReservationButton(driver, test);

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
						if (!DistributionMethod.isEmpty()) {

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
							unitOwnerItems = accountPage.getAssociatedUnitOwnerItemsList(driver, acName,
									category, test_steps);

						}

						try {
							test_steps.add("========== Creating account ==========");
							app_logs.info("========== Creating account ==========");
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

							// accountPage.NewReservationButton(driver, test);

						} catch (Exception e) {

							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Unit Owner account", testName,
										"UnitOwnerAccount", driver);
							} else {
								Assert.assertTrue(false);
							}

						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Unit Owner account", testName,
										"UnitOwnerAccount", driver);
							} else {
								Assert.assertTrue(false);
							}
						}

					}

					else if (accountType.equals("Corporate/Member Accounts")) {
						try {
							test_steps.add("========== Creating account ==========");
							app_logs.info("========== Creating account ==========");
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

							// accountPage.NewReservationButton(driver, test);

						} catch (Exception e) {

							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create corporate account", testName,
										"CorporateAccount", driver);
							} else {
								Assert.assertTrue(false);
							}

						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create corporate account", testName,
										"CorporateAccount", driver);
							} else {
								Assert.assertTrue(false);
							}
						}
					}

					else if (accountType.equals("Group")) {

						try {

							nav.groups(driver);
							// group.click_NewAccount(driver, test, test_steps);
							group.type_GroupName(driver, test, accountName, test_steps);
							group.type_AccountAttributes(driver, test, marketSegmentOption, referralsOption,
									test_steps);
							group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber,
									address1, city, state, country, postalCode, test_steps);
							group.Billinginfo(driver);
							group.Save(driver, test_steps);
							// group.click_GroupNewReservation(driver, test_steps);

						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						}

					}

					guestName = accountName + " (" + accountNumber + ")";

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

								if (itemStatuOptions.equalsIgnoreCase("void")) {
									accountPage.VoidLineItem(driver, Incidentals);
									// folio.VoidLineItem(driver, Incidentals);
								}
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

									if (itemStatuOptions.equalsIgnoreCase("void")) {
										accountPage.VoidLineItem(driver, inc[i]);
										// folio.VoidLineItem(driver, inc[i]);
									}
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

								if (itemStatuOptions.equalsIgnoreCase("void")) {
									accountPage.VoidLineItem(driver, RoomCharges);
									// folio.VoidLineItem(driver, RoomCharges);
								}
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

									if (itemStatuOptions.equalsIgnoreCase("void")) {
										accountPage.VoidLineItem(driver, rc[i]);
										// folio.VoidLineItem(driver, rc[i]);
									}

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

								if (itemStatuOptions.equalsIgnoreCase("void")) {
									//folio.VoidLineItem(driver, Taxes);
									accountPage.VoidLineItem(driver, Taxes);
								}
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

									if (itemStatuOptions.equalsIgnoreCase("void")) {
										//folio.VoidLineItem(driver, tax[i]);
										accountPage.VoidLineItem(driver, tax[i]);
									}
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

								if (itemStatuOptions.equalsIgnoreCase("void")) {
									//folio.VoidLineItem(driver, Fees);
									accountPage.VoidLineItem(driver, Fees);
								}
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

									if (itemStatuOptions.equalsIgnoreCase("void")) {
										//folio.VoidLineItem(driver, fee[i]);
										accountPage.VoidLineItem(driver, fee[i]);
									}
								}
								accountPage.Save(driver);
							}
						}

						// folio.ClickSaveFolioButton(driver);

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
						// FolioTripTotal = report.getFolioAmountExcludingCurrency(driver,
						// reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
						// FolioPaid = report.getFolioAmountExcludingCurrency(driver,
						// reservationPage.get_PaymentsWithCurrency(driver, test_steps));
						// FolioBalance = report.getFolioAmountExcludingCurrency(driver,
						// reservationPage.get_BalanceWithCurrency(driver, test_steps));

						// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
						// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
						// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);

						app_logs.info("FolioRoomCharges " + amountAllRoomCharges);
						app_logs.info("FolioIncidentals " + amountAllIncidentals);
						app_logs.info("FolioTaxes " + amountAllTaxes);
						app_logs.info("FolioFees " + amountAllFees);
						app_logs.info("FolioTripTotal " + FolioTripTotal);

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

						app_logs.info("Item Descreption: " + itemDescription);
						app_logs.info("Foio Item values: " + folioItemValues);

						ledgerAmounts.put("Room Charge", FolioRoomCharges);
						ledgerAmounts.put("Incidental", FolioIncidentals);
						ledgerAmounts.put("Taxes", FolioTaxes);
						ledgerAmounts.put("Fees", FolioFees);

						app_logs.info("Ledger Amounts: " + ledgerAmounts);

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

					// Item status
					try {

						if (itemStatuOptions.split(",").length == 1) {
							if (itemStatuOptions.equals("Pending")) {
								// folio.clickAddLineItemButton(driver);
								// folio.AddFolioLineItem(driver, "Laundry", "50");
							} else if (itemStatuOptions.equals("Posted")) {
								// folio.clickAddLineItemButton(driver);
								// folio.AddFolioLineItem(driver, "Spa", "70");
								// driver.findElement(By.xpath("//span[text()='Spa']/../../td[contains(@class,'changestatus')]")).click();
								// reservationPage.checkinReservation(driver, test_steps);
								reservationPage.CheckInButton(driver);
								reservationPage.generatGuestReportToggle(driver, test_steps, "No");
								reservationPage.CheckInConfrimButton(driver);

							}
							// driver.findElement(By.xpath("(//button[text()='Save'])[9]")).click();
						} else {
							System.out.println("Enter only one option for Item status");
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

					// Payment
					try {

//						TripSummaryRoomCharges = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps)); 
//						//TripRoomCharge = report.getFolioAmountExcludingCurrency(driver, TripSummaryRoomCharges);
//						TripSummaryTaxes = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTaxesWithCurrency(driver, test_steps));
//						//TripTax = report.getFolioAmountExcludingCurrency(driver, TripSummaryTaxes);
//						TripSummaryIncidentals = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryInceidentalsWithCurrency(driver, test_steps));
//						TripSummaryTripTotal = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTripTotalChargesWithCurrency(driver, test_steps));
//						TripSummaryPaid = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryPaidWithCurrency(driver, test_steps));
//						TripSummaryBalance = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryBalanceWithCurrency(driver, test_steps));

						//if (isPayment.equalsIgnoreCase("Yes")) {
						if (ledgerAccounts.containsKey("Payment Method")) {
							driver.navigate().refresh();
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
							reservationPage.payButtonClickInTakePayment(driver, test_steps, "" + FolioTripTotal + "",
									true, true);
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
					
					if (LedgerAccount.equalsIgnoreCase("Travel Agent Commission") || LedgerAccount.equalsIgnoreCase("Transfers") || 
							inputs.contains(Transfers) ) {
						nav.Accounts(driver);
						//accountPage.OpenSearchedAccount(driver, accountName, test_steps);
						accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
						accountPage.OpenSearchedAccount(driver, accountName, test_steps);
						driver.navigate().refresh();
						accountPage.ClickFolio(driver);
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
					}

					app_logs.info("Account creation completed at: " + java.time.LocalTime.now());
					test_steps.add("Account creation completed at: " + java.time.LocalTime.now());
					Wait.wait60Second();
					Wait.wait60Second();

					app_logs.info("Running Report at " + java.time.LocalTime.now());
					test_steps.add("Running Report at " + java.time.LocalTime.now());

					// Select inputs
					try {

						try {

							nav.ReportsV2(driver);
							report.navigateToLedgerBalancesReport(driver);
							if (!DateRange.isEmpty()) {
								report.selectDateRange(driver, DateRange, test_steps);
							} else {
								report.selectStartdate(driver, StartDate, test_steps);
								report.selectEnddate(driver, EndDate, test_steps);
							}

							// availableTypes = report.getAllAvailableTypes(driver, test_steps);
							report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);
							// Utility.switchTab(driver, (new
							// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

							report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
//							if (!accountTypeOptions.equalsIgnoreCase("Reservations")) {
//								String accounts = accountTypeOptions + ",Reservations";
//								report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accounts);
//								// report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,
//								// "Reservations");
//							} else {
//								report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps,accountTypeOptions);
//							}
							report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
							if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
								report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
							}
							report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps,
									TaxExemptLedgerItemsOption);
							report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
							report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps,
									reservationStatusOptions);
							report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

							report.clickOnRunReport(driver);

						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
										driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport",
										driver);
							} else {

								Assert.assertTrue(false);
							}
						}

						try {

							test_steps.add("====  Ledger Balances Report Validation =====");
							test_steps.add("====  <b>Summary View Validation</b> =====");

							afterLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);

							// afterDetailsOfGivenCategory = report.getDetailsOfGivenCategory(driver, "Room
							// Charge", test_steps);
							// report.getDetailsOfGivenCategory(driver, "Tax", test_steps);
							afterDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver,
									test_steps);

							app_logs.info("Before: " + beforeLedgerCategoryDetails);
							test_steps.add("Before: " + beforeLedgerCategoryDetails);
							app_logs.info("After: " + afterLedgerCategoryDetails);
							test_steps.add("After: " + afterLedgerCategoryDetails);

							app_logs.info("Before Individual: " + beforeDetailsOfAllLedgerCategories);
							test_steps.add("Before Individual: " + beforeDetailsOfAllLedgerCategories);
							app_logs.info("After Individual: " + afterDetailsOfAllLedgerCategories);
							test_steps.add("After Individual: " + afterDetailsOfAllLedgerCategories);

							report.validateLedgerReportSummaryView(driver, beforeLedgerCategoryDetails,
									beforeDetailsOfAllLedgerCategories, afterLedgerCategoryDetails,
									afterDetailsOfAllLedgerCategories, ledgerAccounts, ledgerAmounts, folioItemValues,
									numberOfRooms, test_steps);

							// Payment Method
							if (!PaymentMethod.isEmpty()) {
								if (beforeDetailsOfAllLedgerCategories.containsKey(PaymentMethod)) {
									double pay = Double.parseDouble(
											beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1))
											- Double.parseDouble(
													beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
									app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

									if (pay == FolioTripTotal) {
										app_logs.info("Payment Method " + PaymentMethod
												+ " amount validated successfully in Summary View");
										test_steps.add("Payment Method " + PaymentMethod
												+ " amount validated successfully in Summary View");
									} else {
										app_logs.info("Failed - Payment Method " + PaymentMethod
												+ " amount in Summary View validation failed");
										test_steps.add("AssertionError : Failed - Payment Method " + PaymentMethod
												+ " amount in Summary View validation failed. Expected: "
												+ FolioTripTotal + " But found: " + pay);
									}

								} else {
									try {
										double pay = Double.parseDouble(
												beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
										app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);

										if (pay == FolioTripTotal) {
											app_logs.info("Payment Method " + PaymentMethod
													+ " amount validated successfully in Summary View");
											test_steps.add("Payment Method " + PaymentMethod
													+ " amount validated successfully in Summary View");
										} else {
											app_logs.info("Failed - Payment Method " + PaymentMethod
													+ " amount in Summary View validation failed");
											test_steps.add("AssertionError : Failed - Payment Method " + PaymentMethod
													+ " amount in Summary View validation failed");
										}
									} catch (Exception e) {
										app_logs.info("Failed to get Payments Method details");
										test_steps.add("Failed to get Payments Method details");
									}

								}
							}

							beforeLedgerCategoryDetails.clear();
							beforeLedgerCategoryDetails = afterLedgerCategoryDetails;
							// beforeLedgerCategoryDetails.putAll(afterLedgerCategoryDetails);

							beforeDetailsOfAllLedgerCategories.clear();
							beforeDetailsOfAllLedgerCategories = afterDetailsOfAllLedgerCategories;
							// beforeDetailsOfAllLedgerCategories.putAll(afterDetailsOfAllLedgerCategories);

							// app_logs.info("Detailed View: "+report.getDetailedViewDetails(driver,
							// test_steps));
							// app_logs.info("Detailed View: "+report.getDetailedViewDetailsLatest(driver,
							// test_steps));
							app_logs.info("Detailed View: "
									+ report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));

						} catch (Exception e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to valdate Summary view", testName,
										"LedgerReportsSummaryView", driver);
							} else {
								Assert.assertTrue(false);
							}
						} catch (Error e) {
							if (Utility.reTry.get(testName) == Utility.count) {
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
								Utility.updateReport(e, "Failed to valdate Summary view", testName,
										"LedgerReportsSummaryView", driver);
							} else {

								Assert.assertTrue(false);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Summary view after after Run report",
									testName, "RunReport-SummaryView", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Summary view after Run report", testName,
									"RunReport-SummaryView", driver);
						} else {
							Assert.assertTrue(false);
						}
					}

					test_steps.add("====  <b>Detailed View Validation</b> =====");
					// Detailed View
					try {

						HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();

						report.validateLedgerReportDetailedViewAccounts(driver, ledgerAccounts, folioItemValues,
								guestNames, itemDescription, currencySymbal, IsTaxExempt, test_steps);

						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

						// driver.close();
						// Utility.switchTab(driver, 0);

					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Detailed view after after Run report",
									testName, "RunReport-DetailedView", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
							Utility.updateReport(e,
									"Failed to validate Reservation details in Detailed view after after Run report",
									testName, "RunReport-DetailedView", driver);
						} else {
							Assert.assertTrue(false);
						}
					}

				}
			}

		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2LedgerBalances2", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}

}
