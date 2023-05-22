package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.properties.OR_FolioNew;
import com.innroad.inncenter.properties.OR_Reports;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;

public class ReportsV2LedgerBalancesReportValidationReservationV2 extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	Account accountPage = new Account();
	CPReservationPage reservationPage = new CPReservationPage();
	ReservationV2 reservationV2Page = new ReservationV2();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	Groups group = new Groups();
	AdvGroups advgrp = new AdvGroups();
	LedgerAccount la = new LedgerAccount();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop = new Properties();
	Tapechart tc = new Tapechart();
	Admin admin = new Admin();
	NewRoomClassesV2 rc2 = new NewRoomClassesV2();
//	RoomClass rc = new RoomClass();
	RoomMaintenance rm = new RoomMaintenance();
	RatesGrid ratesGrid = new RatesGrid();	
	TaxesAndFee taxesAndFee = new TaxesAndFee();
	FolioNew folioNew = new FolioNew();

	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
	String reservationNumber = null, guestFirstName = null, guestLastName, guestSalutation, phoneNumber = "1234567890",  
	email = "innRoadTestEmail@innroad.com",	address1 = "10th Building", address2 = "Block C", address3 = "Street 10",
	city = "NewYork", country = "United States", state = "Alaska", postalCode = "12345",
	currency = null, clientTimeZone, dFormat, sourceOfRes, propertyName="Groups Property", 
	accountFirstName, accountLastName, reportsTab, applicationTab, currentDay, accountName, roomNumber,
	cardExpDate, updateStartDate, updateEndDate, newRoomClassName, roomCharge , guestFullName, promoCode, roomClassAbb="NTS",
	noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee, taskStatus, 
	changeOption, taxName, feeName, randomString, cvvCode = "123";
	double roomChargeAmount;
	
	String guestName = null, date = null;
	String guest1 = null, guest2 = null, clientDateFormat = null;
	String startDayOfWeek;
//	String dateFormat;
	String feeValue = null;
	ArrayList<String> feeValueList = new ArrayList<>();
	ArrayList<String> taxValueList = new ArrayList<>();
	double feeCalc = 0.0;
	double taxCalc = 0.0;

	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();
	
	HashMap<String, Double> expChanges = new HashMap<>();

	ArrayList<String> reservationNumbers = new ArrayList<>();
	ArrayList<String> arrivalDates = new ArrayList<>();
	ArrayList<String> dates = new ArrayList<>();
	ArrayList<String> itemDescriptions = new ArrayList<>();
	ArrayList<String> amountList = new ArrayList<>();
	ArrayList<String> availableTypes = new ArrayList<>();
	HashMap<String, String> beforeLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> beforeDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> beforeDetailsOfAllLedgerCategories = new HashMap<>();

	HashMap<String, String> afterLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> afterDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> afterDetailsOfAllLedgerCategories = new HashMap<>();
	
	HashMap<String, String> reservationData = new HashMap<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@BeforeClass()
	public void getTimeZone() throws Exception{
		driver = getDriver();
		loginReportsV2ReservationV2(driver, test_steps);	
		nav.admin(driver);
		nav.navigateToClientinfo(driver);		
		admin.clickClientName(driver);
		admin.clickClientOption(driver);		
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		if (currency.contains("$") || currency.contains("USD")) {
			propertyCurrency = "$";
		}else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
			propertyCurrency = "£";
		}			
	
		propertyFirstDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
		clientTimeZone = admin.getClientTimeZone(driver, test_steps);
		dFormat = admin.getClientDateFormat(driver);
		
		switch (clientTimeZone) {
		case "(GMT-05:00) Eastern Time (US and Canada)":
			propertyTimeZone = "US/Eastern";
			break;			
		case "(GMT-06:00) Central Time (US and Canada)":
			propertyTimeZone = "US/Central";
			break;		
		case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
			propertyTimeZone = "Europe/London";
			break;
		default:
			break;
		}
		
		if (dFormat.equalsIgnoreCase("USA")) {
			propertyDateFormat = "MMM dd, YYYY";
		}else if (dFormat.equalsIgnoreCase("International")) {
			propertyDateFormat = "dd MMM, YYYY";
		}
		nav.ReservationV2_Backward(driver);
		cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateLedgerBalancesReport(String Scenario, String DateRange, String StartDate, String EndDate,
			String LedgerAccount, String LedgerValue,
			String accountType, String associateAccount, String resType, String CheckInDate, String CheckOutDate,  
			String rateplan, String roomClass, String paymentMethod, String cardNumber, String isTaxExempt, String taxExemptID,
			String marketSegment, String referral,
			String accountTypeOption, String itemStatus, String IncludeDataFromUsers, String shiftTime,
			String TaxExemptLedgerItemsOption,String resStatus, String mrbStatus) throws Throwable {

		test_name = Scenario;
		if (resType.equalsIgnoreCase("TapeChart")) {
			test_name = test_name+" from TapeChart";
			roomClassAbb = rc2.openRoomClassAndGetDataFromRoomTab(driver, test_steps, roomClass).get("Abbreviation");
			nav.Reservation_Backward_3(driver);
		}
		if (associateAccount.equalsIgnoreCase("Associate Account")) {
			test_name = test_name+" by associating "+accountType;
		}
		if (associateAccount.equalsIgnoreCase("Account Reservation")) {
			test_name = test_name+" for "+accountType+" Account";
		}

		randomString = Utility.generateRandomStringWithoutNumbers();
		taxName = "Test"+randomString;
		feeName = "Test"+randomString;

		test_description = "Validate Ledger Balances Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Ledger Balances Report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Utility.closeTabsExcept(driver, 1);
		loginReportsV2ReservationV2(driver);
		String BlockName = "Block" + Utility.generateRandomStringWithoutNumbers();
		int noOfRooms = 1;
		if (roomClass.split("\\|").length > 1) {
			noOfRooms = roomClass.split("\\|").length;
		}
		app_logs.info("Number of rooms: "+noOfRooms);
		
		if (Utility.validateString(accountType)) {
			if (accountType.equalsIgnoreCase("Group")) {
				accountName = "AccountGroupTest";
			}else {
				accountName = "AccountTest";
			}
			
		}else {
			accountName = "";
		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			
//			if (Scenario.contains("long stay")) {
//				nav.Setup(driver, test_steps);
//				nav.navigateToProperties(driver, test_steps);
//				nav.openProperty(driver, test_steps, propertyName);
//				nav.clickPropertyOptions(driver, test_steps);
//				prop.setOrRemoveTaxExempt(driver, test_steps, true, taxExemptInput, taxExemptOption);
//        		CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(
//        				(Integer.parseInt(taxExemptInput)+1), propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
//        		nav.clickTaxesAndFees(driver);
//        		taxesAndFee.deleteAllTaxesAndFee(driver, test_steps);
//        		taxesAndFee.createTaxes(driver, taxName, taxName, "Test Tax", "Flat amount per charge", "flat", "150", "Yes", 
//        				"Local Room Tax", false, "", "", "", test_steps);
//			}
			
			if ( !(Utility.validateDate(CheckInDate)) ) {
				if (!Scenario.contains("long stay")) {
					if (!resType.equalsIgnoreCase("MRB")) {
		            	CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
		            	if (Scenario.contains("multi")) {
		            		CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
						} else if (Scenario.contains("single")) {
							CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
						} else if (Scenario.contains("extend")) {
							CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
							updateEndDate = "";
							updateEndDate = Utility.addDays(CheckOutDate, 2);
							updateDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, updateEndDate);
						} else if (Scenario.contains("reduce")) {
							CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
							updateEndDate = "";
							updateEndDate = Utility.addDays(CheckOutDate, -1);
							updateDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, updateEndDate);
						} else if (Scenario.contains("update") && Scenario.contains("dates")) {
							CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
							updateStartDate = "";
							updateStartDate = Utility.addDays(CheckOutDate, 3);
							updateEndDate = "";
							updateEndDate = Utility.addDays(CheckOutDate, 3);
							updateDates = Utility.getAllDatesBetweenCheckInOutDates(updateStartDate, updateEndDate);
						}else {
							CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
						}

					} else {
						checkInDates.clear();
						checkOutDates.clear();
						if ( !(Utility.validateDate(CheckInDate)) ) {
							for (int i = 0; i < noOfRooms; i++) {
								checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(0, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
								if (Scenario.contains("multi")) {
									checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
								}else {
									checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
								}
								
							}
							CheckInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
							CheckOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);
						} else {
							checkInDates = Utility.splitInputData(CheckInDate);
							checkOutDates = Utility.splitInputData(CheckOutDate);
						}

					}				
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
		
		String adults = "2";
		String children = "1";
		String guestFirstName = "First" + Utility.generateRandomStringWithoutNumbers();
		String guestLastName = "Last" + Utility.generateRandomStringWithoutNumbers();
		accountFirstName = guestLastName;
		accountLastName = guestLastName;
		String salutation = "Mr.";
		String address1 = Utility.generateRandomString();
		String address2 = Utility.generateRandomString();
		String address3 = Utility.generateRandomString();
		String city = Utility.generateRandomString();
		String postalCode = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
		String phoneNumber = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String alternativePhone = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String accountNumber = null;
		guestFirstName = ""; guestFirstName = "Auto";
		guestLastName = ""; guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = ""; guestFullName = guestFirstName+" "+guestLastName;
		guestSalutation = "Mr.";
		currentDay = Utility.getCurrentDate("dd/MM/yyyy", propertyTimeZone);
		additionalGuests = 0;
		roomNumber=null;
		isQuote = false;
		String processingMethod = null;
		HashMap<String, String> ledgerAccounts = new HashMap<>();
		HashMap<String, Double> ledgerAmounts = new HashMap<>();
		HashMap<String, String> folioItemValues = new HashMap<>();
		HashMap<String, String> itemDescription = new HashMap<>();
		
		ledgerAccounts = report.getLedgerInputsAndValues(driver, LedgerAccount, LedgerValue);
		app_logs.info("Ledger Accounts: "+ledgerAccounts);

		String Incidentals = "", RoomCharges = "", Taxes = "", Fees = "";

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
		
		if (ledgerAccounts.containsKey("Tax") || ledgerAccounts.containsKey("Taxes") || ledgerAccounts.containsKey("Fee") || ledgerAccounts.containsKey("Fees")) {
			nav.Setup(driver, test_steps);
    		nav.clickTaxesAndFees(driver);
    		taxesAndFee.deleteAllTaxesAndFee(driver, test_steps);
    		if (ledgerAccounts.containsKey("Tax") || ledgerAccounts.containsKey("Taxes")) {
    			if (ledgerAccounts.containsKey("Incidental") || ledgerAccounts.containsKey("Incidentals")) {
            		taxesAndFee.createTaxes(driver, taxName, taxName, "Test Tax", Taxes, "flat", Utility.GenerateRandomNumber(10, 20), "Yes", 
            				Incidentals, false, "", "", "", test_steps);
				}else {
	        		taxesAndFee.createTaxes(driver, taxName, taxName, "Test Tax", Taxes, "flat", Utility.GenerateRandomNumber(10, 20), "Yes", 
	        				"Room Charge", false, "", "", "", test_steps);
				}
        		//String taxName = taxesAndFee.getTaxAndFeeName(driver);
    		}
    		if (ledgerAccounts.containsKey("Fee") || ledgerAccounts.containsKey("Fees")) {
    			taxesAndFee.createFee(driver, test_steps, "Test Fee", "Test Fee", Fees, "Test Fee", "flat", Utility.GenerateRandomNumber(10, 20), false, null, null, "InCenter");
    		}
    		nav.ReservationV2_Backward(driver);
		}
				
		ArrayList<String> RoomClasses = new ArrayList<>();
		String[] rc = roomClass.split("\\|");
		if (rc.length>1) {
			for (int i = 0; i < rc.length; i++) {
				RoomClasses.add(rc[i]);
			}
		}else {
			RoomClasses.add(roomClass);
		}
		Set<String> inputs = ledgerAccounts.keySet();
		app_logs.info("Inputs: " + inputs);
		
		if (!mrbStatus.isEmpty() && resStatus.isEmpty()) {
			if (mrbStatus.equalsIgnoreCase("PrimaryCheckIn")) {
				resStatus = "In-House";
			}else if (mrbStatus.equalsIgnoreCase("PrimaryCheckOut")){
				resStatus = "Departed";
			}else if (mrbStatus.equalsIgnoreCase("SecondaryCheckIn")) {
				resStatus = "In-House";
			}else if (mrbStatus.equalsIgnoreCase("SecondaryCheckOut")) {
				resStatus = "Departed";
			}else if (mrbStatus.equalsIgnoreCase("SecondaryCancel")) {
				resStatus = "Cancelled";
			}
		}

		try {
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			report.selectDateRange(driver, StartDate, EndDate, DateRange, test_steps);
			report.clearAllInputOptions(driver, test_steps);
			report.selectSelectInputsAll(driver, inputs, ledgerAccounts, test_steps);
			report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOption);
			report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatus);
			if (!shiftTime.isEmpty() || !shiftTime.equalsIgnoreCase("")) {
				report.selectIncludeDataFromOptions(driver, test_steps, IncludeDataFromUsers, shiftTime);
			}
			report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps, TaxExemptLedgerItemsOption);
			report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegment);
			report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps, resStatus);
			report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referral);

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
		
		try {
			if (report.checkNoReportDataAvailable(driver, test_steps)) {
				app_logs.info("Report Genaration failed, got 'No Report Data available' toast message");
				test_steps.add("Report Genaration failed, got 'No Report Data available' toast message");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			beforeLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);
			beforeDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);

			allTabs = new ArrayList<>(driver.getWindowHandles());
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);
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
		
		Utility.switchTab(driver, applicationTab);
		nav.ReservationV2_Backward(driver);
		
		//Action
		isQuote = false;
		if (resStatus.equalsIgnoreCase("Quote")) {
			isQuote = true;
		}
		roomNumber = "";
		if (Scenario.contains("unassigned") || Scenario.contains("Unassigned")) {
			roomNumber = "Unassigned";
		}
		additionalGuests = 0;
		if (Scenario.contains("multiple guests") || Scenario.contains("multipleGuests") || Scenario.contains("additional guest")) {
			additionalGuests = 1;
		}
		
		if (!Utility.validateString(referral)) {
			referral = "Walk In";
		}
		if (!Utility.validateString(marketSegment)) {
			marketSegment = "Internet";
		}
		
		Double totalRate = 0.0;
		reservationNumbers.clear();
		if (CheckInDate.split("\\|").length > 1) {
			numberOfNights = Utility.getNumberofDays(CheckInDate.split("\\|")[0], CheckOutDate.split("\\|")[0]);
		}else {
			numberOfNights = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		}
		app_logs.info("Number of nights: "+numberOfNights);
		
		if (Utility.validateString(associateAccount)) {
			if (associateAccount.equalsIgnoreCase("Associate Account")) {
				sourceOfRes = "Associate Account";
			} else if (associateAccount.equalsIgnoreCase("Account Reservation")) {
				sourceOfRes = "Account Reservation";
			}
		}else {
			sourceOfRes = "From Reservations page";
		}
		
		if (resType.equalsIgnoreCase("TapeChart") || resType.equalsIgnoreCase("Tape Chart")) {
			sourceOfRes = "TapeChart";
		}
		if (accountType.equalsIgnoreCase("Group")) {
			sourceOfRes = "Group";
		}
		boolean taxExempt = false;
		if (isTaxExempt.equalsIgnoreCase("Yes")) {
			taxExempt = true;
		}else {
			isTaxExempt = "No";
		}
		
		String resNumberPayment = null;
		if (paymentMethod.equalsIgnoreCase("Gift Certificate")) {
			nav.Accounts(driver);
			accountType = "Gift Certificate";
			accountName = "Acc"+Utility.generateRandomString();
			accountPage.ClickNewAccountbutton(driver, accountType);
			accountPage.AccountDetails(driver, accountType, accountName);
			accountNumber = accountPage.getAccountNum(driver);
			report.AccountSave(driver, test, accountName, test_steps);
//			cardNumber = accountPage.createAccount(driver, test_steps, null, accountType, accountName, accountFirstName, accountLastName, 
//					phoneNumber, phoneNumber, email, marketSegment, referral, address1, address2, address3, city, state, postalCode);
			accountPage.ClickFolio(driver);
			accountPage.addLineitem1(driver, "", "Gift Certificate", "1000", test_steps);
			cardNumber = accountPage.getGiftCertNumber(driver, test_steps);
			app_logs.info("Gift Card Number: "+cardNumber);
			accountPage.Commit(driver);
			accountPage.Save(driver, test, test_steps);
			nav.ReservationV2_Backward(driver);
		}else if (paymentMethod.equalsIgnoreCase("Account (House Account)")) {
			//nav.Accounts(driver);
			accountType = "House Account";
			accountName = "Acc TestHouse";
			if (!accountPage.checkForAccountExistsAndOpen(driver, test_steps, accountName, accountType, false)) {
				accountPage.ClickNewAccountbutton(driver, accountType);
				accountPage.AccountDetails(driver, accountType, accountName);			
				report.AccountSave(driver, test, accountName, test_steps);
				app_logs.info("New House account created");
			}else {
				test_steps.add("Account is already existing with <b>"+accountName+"</b> name");
			}
			nav.ReservationV2_Backward(driver);
		}else if (paymentMethod.equalsIgnoreCase("Reservation")) {
			reservationV2Page.createReservation(driver, test_steps, "From Reservations page", CheckInDate, CheckOutDate, adults, 
					children, rateplan, null, roomClass, roomClassAbb, "Mr.", guestFirstName, guestLastName, phoneNumber,
					phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
					referral, "Cash", cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, 
					noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
					taskAssignee, taskStatus, accountName, accountType, guestFirstName, guestLastName, taxExempt, taxExemptID);
			
			resNumberPayment = reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim();
			cardNumber = resNumberPayment;
			app_logs.info("Reservation Number for Payment: "+resNumberPayment);
			reservationV2Page.closeAllOpenedReservations(driver);
			driver.navigate().refresh();
		}
		
		if (ledgerAccounts.containsKey("Travel Agent Commission")) {
			ArrayList<String> TravelAgentItems = new ArrayList<>();
			int TravelAgetItemValue;
			nav.Accounts(driver);		
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
			accountName = "Acc"+Utility.generateRandomString();
			nav.ReservationV2_Backward(driver);			
		}
		
		if (ledgerAccounts.containsKey("Transfers") || ledgerAccounts.containsKey("Transfer")) {
			accountName = "Acc"+Utility.generateRandomString();
			app_logs.info("Account name: "+accountName);
		}
		
		if (resType.equalsIgnoreCase("TapeChart")) {
			nav.setup(driver);
			nav.roomClass(driver);
			roomClassAbb = rc2.getAbbreviationText(driver, test_steps, roomClass);
			app_logs.info("Room Abbreviation: "+roomClassAbb);
			nav.ReservationV2_Backward(driver);
		}
		
		if (resType.equalsIgnoreCase("GroupPickRoomingList") || resType.equalsIgnoreCase("GroupPickBlueIcon")) {
			test_steps.add("========== Creating Group account ==========");
			app_logs.info("========== Creating Group account ==========");
			nav.groups(driver);
			
			if (!group.checkForGrouptExistsAndOpen(driver, test_steps, accountName, true)) {
				group.type_GroupName(driver, test, accountName, test_steps);
				group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
				group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber,
						address1, city, state, country, postalCode, test_steps);
				group.Billinginfo(driver);
				group.Save(driver, test_steps);				
			}
			app_logs.info("Romm Classes: "+roomClass);
			app_logs.info("Romm Classes list: "+RoomClasses);
			
			BlockName = BlockName + Utility.GenerateRandomString15Digit();
			group.navigateRoomBlock(driver, test);			
			test_steps.add("========== Creating Group block ==========");
			app_logs.info("========== Creating Group block ==========");
			group.createNewBlockWithMultiRoomClasses(driver, BlockName, CheckInDate, CheckOutDate, rateplan, "1", roomClass, test_steps);
			//test_steps.addAll(group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRooms, RoomClass));
			group.navigateRoomBlock(driver, test);													
			for (int i = 0; i < RoomClasses.size(); i++) {
				totalRate = totalRate + (Double.parseDouble(group.getTotalRateValue(driver, RoomClasses.get(i))) * 
						Double.parseDouble(group.getBlocked(driver, RoomClasses.get(i))));
			}
			//totalRate = group.getTotalRateValue(driver, RoomClass);
			app_logs.info("Total rate: "+totalRate);
			
			if (resType.equalsIgnoreCase("GroupPickRoomingList")) {
				test_steps.addAll(group.roomingListClick(driver));
				advgrp.enter_RoomPickupdetails(driver, test_steps);
				reservationNumber = group.pickUp_getResNo(driver);
				app_logs.info("Group pick Reservation number: "+reservationNumber);			
				group.pickUpCloseClick(driver);
				
				ledgerAmounts.put("Room Charge", totalRate);
				folioItemValues.put("Room Charge", Double.toString(totalRate));
				itemDescription.put("Room Charge", rateplan);
			}else if (resType.equalsIgnoreCase("GroupPickBlueIcon")) {
				advgrp.clickBlueBookIcon(driver);
				reservationV2Page.clickNext(driver, test_steps);
				reservationV2Page.enter_GuestName(driver, test_steps, "Mr.", guestFirstName, guestLastName);
				reservationV2Page.clickBookNow(driver, test_steps);
				reservationV2Page.clickCloseReservationSavePopup(driver, test_steps);
				
				//roomCharge = reservationData.get("Room Charges");
				reservationNumbers.add(reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim());
				guestNames.clear();
				guestNames.add(guestFirstName+" "+guestLastName);
				app_logs.info("Guest Names: " + guestNames);
				
				arrivalDates.clear();
				if (dFormat.equalsIgnoreCase("USA")) {
					arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
				}else if (clientDateFormat.equalsIgnoreCase("International")) {
					arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "dd MMM, yyyy"));
				}
				app_logs.info("Arrival Date: "+arrivalDates);				
				dates.clear();
				if (dFormat.equalsIgnoreCase("USA")) {
					dates.add(Utility.getCurrentDate("MMM dd, yyyy"));
				}else if (clientDateFormat.equalsIgnoreCase("International")) {
					dates.add(Utility.getCurrentDate("dd MMM, yyyy"));
				}
				app_logs.info("Date: "+dates);
				roomChargeAmount = Double.parseDouble(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL().replaceAll("[$£ ]", "").trim());
			}			
		}	
		
		Utility.switchTab(driver, applicationTab);				
		if (Utility.validateString(resType)) {			
			try {
				if (resType.equalsIgnoreCase("Single") || resType.equalsIgnoreCase("TapeChart") || resType.equalsIgnoreCase("Copy")) {	
					reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, adults, 
							children, rateplan, null, roomClass, roomClassAbb, "Mr.", guestFirstName, guestLastName, phoneNumber,
							phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
							referral, paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, 
							noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
							taskAssignee, taskStatus, accountName, accountType, guestFirstName, guestLastName, taxExempt, taxExemptID);
					roomCharge = reservationData.get("Room Charges");
					reservationNumber = reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim();
					reservationNumbers.clear();
					reservationNumbers.add(reservationNumber);
					guestName = guestFirstName+" "+guestLastName;
					guestNames.clear();
					guestNames.add(guestName);
					app_logs.info("Guest Names: " + guestNames);
					
					arrivalDates.clear();
					if (dFormat.equalsIgnoreCase("USA")) {
						arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
					}else if (clientDateFormat.equalsIgnoreCase("International")) {
						arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "dd MMM, yyyy"));
					}
					app_logs.info("Arrival Date: "+arrivalDates);
					
					dates.clear();
					if (dFormat.equalsIgnoreCase("USA")) {
						dates.add(Utility.getCurrentDate("MMM dd, yyyy"));
					}else if (clientDateFormat.equalsIgnoreCase("International")) {
						dates.add(Utility.getCurrentDate("dd MMM, yyyy"));
					}
					app_logs.info("Date: "+dates);
					roomChargeAmount = (Double.parseDouble(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL().replaceAll("[$£ ]", "").trim())/numberOfNights);
				} else if (resType.equalsIgnoreCase("MRB")) {
					for (int i = 2; i <= roomClass.split("\\|").length; i++) {
						guestFirstName = guestFirstName+"|"+Utility.generateRandomString();
						guestLastName = guestLastName+"|"+Utility.generateRandomString();
						if (!(adults.split("\\|").length > 1)) {
							adults = adults + "|2";
							children = children + "|1";
						}
					}					
					reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, adults, 
							children, rateplan, null, roomClass, roomClassAbb, guestSalutation, guestFirstName, 
							guestLastName, phoneNumber, phoneNumber, email, address1, address2, address3, 
							city, country, state, postalCode, false, marketSegment, referral, paymentMethod, cardNumber, 
							guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, noteType, noteSubject, 
							noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
							taskAssignee, taskStatus, accountName, accountType, guestFirstName, guestLastName, taxExempt, taxExemptID);				
					roomCharge = reservationData.get("Room Charges");				
					reservationNumber = reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim();
					
					guestNames.clear();arrivalDates.clear();dates.clear();
					for (int i = 0; i < noOfRooms; i++) {
						String[] fNames = guestFirstName.split("\\|");
						String[] lNames = guestLastName.split("\\|");
						app_logs.info("Fanmes: " + fNames.length);
						app_logs.info("Lanmes: " + lNames.length);
						app_logs.info("Loop " + i);
						guestNames.add(fNames[i] + " " + lNames[i]);
						
						if (dFormat.equalsIgnoreCase("USA")) {
							dates.add(Utility.getCurrentDate("MMM dd, yyyy"));
						}else if (dFormat.equalsIgnoreCase("International")) {
							dates.add(Utility.getCurrentDate("dd MMM, yyyy"));
						}							
						String[] CheckInDates = CheckInDate.split("\\|");							
						
						if (dFormat.equalsIgnoreCase("USA")) {
							arrivalDates.add(Utility.parseDate(CheckInDates[i], "dd/MM/yyyy", "MMM dd, yyyy"));
						}else if (dFormat.equalsIgnoreCase("International")) {
							arrivalDates.add(Utility.parseDate(CheckInDates[i], "dd/MM/yyyy", "dd MMM, yyyy"));
						}						
						reservationNumbers.add(reservationNumber);
					}
					
					if (mrbStatus.equalsIgnoreCase("SecondaryCheckIn") || mrbStatus.equalsIgnoreCase("SecondaryCheckOut") || mrbStatus.equalsIgnoreCase("SecondaryCancel")) {
						guestNames.remove(0);					
					}
					app_logs.info("Guest Names: " + guestNames);
					app_logs.info("Arrival Date: "+arrivalDates);
					app_logs.info("Date: "+dates);
					app_logs.info("Reservation Numbers: "+reservationNumbers);
					roomChargeAmount = (Double.parseDouble(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL().replaceAll("[$£ ]", "").trim())/numberOfNights);					
				}
				if (resType.equalsIgnoreCase("Copy")) {	
					reservationV2Page.copyReservation(driver, test_steps, guestFirstName, guestLastName);
					reservationV2Page.closeAllOpenedReservationsExceptCurrent(driver);								
				}
				
				if (Scenario.contains("Quote Book") || Scenario.contains("QuoteBook") || Scenario.contains("book")) {
					reservationV2Page.bookReservationFromQuote(driver, test_steps);
				}
				//roomChargeAmount = Double.parseDouble(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL().replaceAll("[$£ ]", "").trim());

				if (!Incidentals.isEmpty()) {
					reservationV2Page.switchFolioTab(driver, test_steps);
					String incAmount = Utility.GenerateRandomNumber(50);
					if (resType.equalsIgnoreCase("MRB")) {
						if (Utility.validateString(accountType)) {
							List<WebElement> allFolios = folioNew.getAllFolioLinks(driver);
							for (int j = 0; j < allFolios.size(); j++) {
								if (!allFolios.get(j).getText().contains("Acc")) {
									allFolios.get(j).click();
									folioNew.addFolioLineItem(driver, test_steps, Incidentals, incAmount);
									if (itemStatus.equalsIgnoreCase("Void")) {
										folioNew.voidLineItem(driver, test_steps, Incidentals, "Test");
									}else if (itemStatus.equalsIgnoreCase("Posted")) {
										folioNew.postedLineItem(driver, test_steps, Incidentals);
									}
								}
							}
							allFolios.get(0).click();
						}else {
							List<WebElement> allFolios = folioNew.getAllFolioLinks(driver);
							for (int j = 0; j < allFolios.size(); j++) {
								//allFolios.get(j).click();
								Utility.clickThroughJavaScript(driver, allFolios.get(j));
								folioNew.addFolioLineItem(driver, test_steps, Incidentals, incAmount);
								if (itemStatus.equalsIgnoreCase("Void")) {
									folioNew.voidLineItem(driver, test_steps, Incidentals, "Test");
								}else if (itemStatus.equalsIgnoreCase("Posted")) {
									folioNew.postedLineItem(driver, test_steps, Incidentals);
								}
							}	
							allFolios.get(0).click();
						}
					}else {
						folioNew.addFolioLineItem(driver, test_steps, Incidentals, incAmount);
						if (itemStatus.equalsIgnoreCase("Void")) {
							folioNew.voidLineItem(driver, test_steps, Incidentals, "Test");
						}else if (itemStatus.equalsIgnoreCase("Posted")) {
							folioNew.postedLineItem(driver, test_steps, Incidentals);
						}
					}

					//reservationV2Page.addIncidental(driver, CheckInDate, Incidentals, "55", "1");
					reservationV2Page.switchDetailTab(driver, test_steps);
				}
				
				if (itemStatus.equalsIgnoreCase("Void")) {			
					reservationV2Page.switchFolioTab(driver, test_steps);
					List<WebElement> allFolios = folioNew.getAllFolioLinks(driver);
					for (int j = 0; j < allFolios.size(); j++) {
						if (!allFolios.get(j).getText().contains("Acc")) {
							Utility.clickThroughJavaScript(driver, allFolios.get(j));
							folioNew.voidLineItem(driver, test_steps, "Room Charge", "Test");
						}
					}	
					allFolios.get(0).click();
					reservationV2Page.switchDetailTab(driver, test_steps);
				}else if (itemStatus.equalsIgnoreCase("Posted")) {	
					reservationV2Page.switchFolioTab(driver, test_steps);
					List<WebElement> allFolios = folioNew.getAllFolioLinks(driver);
					for (int j = 0; j < allFolios.size(); j++) {
						if (!allFolios.get(j).getText().contains("Acc")) {
							Utility.clickThroughJavaScript(driver, allFolios.get(j));
							folioNew.postedLineItem(driver, test_steps, "Room Charge");
						}
					}
					allFolios.get(0).click();
					reservationV2Page.switchDetailTab(driver, test_steps);
				}
				
				if (ledgerAccounts.containsKey("Payment Method") || ledgerAccounts.containsKey("Gift Certificate Redeemed") || 
						ledgerAccounts.containsKey("Account (Advanced Deposit)") || ledgerAccounts.containsKey("Transfer")) {
					app_logs.info("Making Payment");
					for (int i = 0; i < noOfRooms; i++) {
						reservationV2Page.takePayment(driver, test_steps, false, null, "Test",true);
					}				
					if (paymentMethod.equalsIgnoreCase("MC") || paymentMethod.equalsIgnoreCase("Visa") || paymentMethod.equalsIgnoreCase("Amex") || paymentMethod.equalsIgnoreCase("Discover")) {
						processingMethod = "Automatic";
					}else {
						processingMethod = "Manual";
					}
				}
				
				if ( (Scenario.contains("extend")) || (Scenario.contains("reduce")) ||
						 (Scenario.contains("update") && Scenario.contains("dates")) ) {
					 if ((Scenario.contains("guest1"))) {
						 reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
								 updateDates.get(updateDates.size()-1), changeOption, 1, roomClass);
						 numberOfNights = Utility.getNumberofDays(updateDates.get(0), updateDates.get(updateDates.size()-1));
					} else if ((Scenario.contains("guest2"))) {
						reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
								 updateDates.get(updateDates.size()-1), changeOption, 2, roomClass);
						numberOfNights = Utility.getNumberofDays(updateDates.get(0), updateDates.get(updateDates.size()-1));
					} else if ((Scenario.contains("all guests"))) {
						reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
								 updateDates.get(updateDates.size()-1), changeOption, 1, roomClass);
						reservationV2Page.modifyReservationDatesForMRB(driver, test_steps, updateDates.get(0), 
								 updateDates.get(updateDates.size()-1), changeOption, 2, roomClass);
						numberOfNights = Utility.getNumberofDays(updateDates.get(0), updateDates.get(updateDates.size()-1));
					} else {
						 reservationV2Page.modifyReservationDates(driver, updateDates.get(0), updateDates.get(updateDates.size()-1), 
								 changeOption, roomClass, test_steps);	
						 numberOfNights = Utility.getNumberofDays(updateDates.get(0), updateDates.get(updateDates.size()-1));
					}
				}
				
				boolean voidRoomCharges = false;
				if (itemStatus.equalsIgnoreCase("Void")) {
					voidRoomCharges = true;
				}
				if (!mrbStatus.isEmpty()) {
					resStatus = mrbStatus;
				}
				reservationV2Page.changeReservationStatus(driver, test_steps, resStatus, voidRoomCharges);
				
//				 if (resStatus.equalsIgnoreCase("CheckIn") || resStatus.equalsIgnoreCase("In-House")) {				 
//					reservationV2Page.checkInReservation(driver, test_steps);
//				 }else if (resStatus.equalsIgnoreCase("CheckOut") || resStatus.equalsIgnoreCase("Departed")) {
//					reservationV2Page.checkInReservation(driver, test_steps);
//					reservationV2Page.checkOutReservation(driver, test_steps, "No", "Yes", "");
//				 }else if (resStatus.equalsIgnoreCase("Cancelled") || resStatus.equalsIgnoreCase("Cancel")) {
//					 reservationV2Page.cancelReservation(driver, test_steps, true, "Cancel");
//				 }else if (resStatus.equalsIgnoreCase("NoShow") || resStatus.equalsIgnoreCase("No Show")) {
//					 reservationV2Page.noShowReservation(driver, test_steps, true);
//				 } else if (resStatus.equalsIgnoreCase("MRBPrimaryChekIn")) {
//					 reservationV2Page.checkIn_MrbPrimary(driver, test_steps);
//				 } else if (resStatus.equalsIgnoreCase("MRBPrimaryCheckOut")) {
//					 reservationV2Page.checkIn_MrbPrimary(driver, test_steps);
//					 reservationV2Page.checkOutMRBPrimary(driver, test_steps);
//				 } else if (resStatus.equalsIgnoreCase("MRBSecondaryChekIn")) {
//					 reservationV2Page.checkIn_MrbSecondary(driver, test_steps);
//				 } else if (resStatus.equalsIgnoreCase("MRBSecondaryCheckOut")) {
//					 reservationV2Page.checkIn_MrbSecondary(driver, test_steps);
//					 reservationV2Page.checkOutMRBSecondary(driver, test_steps);
//				 } else if (resStatus.equalsIgnoreCase("MRBSecondaryCancel")) {
//					 reservationV2Page.cancel_MrbSecondary(driver, test_steps, true, "None");
//				 } else if (resStatus.equalsIgnoreCase("Confirmed")) {
//					 reservationV2Page.changeReservationStatusFromStatusDropdown(driver, test_steps, "Confirmed");
//				 } else if (resStatus.equalsIgnoreCase("Guaranteed")) {
//					 reservationV2Page.changeReservationStatusFromStatusDropdown(driver, test_steps, "Guaranteed");
//				 } else if (resStatus.equalsIgnoreCase("OnHold") || resStatus.equalsIgnoreCase("On Hold")) {
//					 reservationV2Page.changeReservationStatusFromStatusDropdown(driver, test_steps, "OnHold");
//				 }
				 
				 if (Scenario.contains("rollback") || Scenario.contains("roll back")) {
					 reservationV2Page.rollBackReservationStatus(driver, test_steps);
				 }			 
				 
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
			
			roomChargeAmount = (Double.parseDouble(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL().replaceAll("[$£ ]", "").trim())/numberOfNights);
			try {
				if (!isQuote || Scenario.contains("Quote Book") || Scenario.contains("book")) {
					if (resType.equalsIgnoreCase("Copy")) {
						TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
						ledgerAmounts.put("Room Charge", (reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_ROOM_CHARGE()))*2);
						ledgerAmounts.put("Incidental", (reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_INCIDENTALS()))*2);
						ledgerAmounts.put("Incidentals", (reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_INCIDENTALS()))*2);
						ledgerAmounts.put("Tax", (reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_TAXES()))*2);
						ledgerAmounts.put("Fee", (reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_FEES()))*2);
						ledgerAmounts.put("Payment Method", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_PAID()));
						
						reservationV2Page.switchFolioTab(driver, test_steps);
						if (itemStatus.equalsIgnoreCase("Void")) {
							folioNew.displayVoidItems(driver, test_steps);
						}
						ArrayList<FolioLineItem> folioLineItems = reservationV2Page.getAllFolioLineItems(driver);
						for (int i = 0; i < folioLineItems.size(); i++) {
							folioItemValues.put(folioLineItems.get(i).getITEM_CATEGORY(), Double.parseDouble(folioLineItems.get(i).getITEM_AMOUNT().replaceAll("[-$£ ]", "").trim())*2+"");
							itemDescription.put(folioLineItems.get(i).getITEM_CATEGORY(), folioLineItems.get(i).getITEM_DESCRIPTION());
							if (ledgerAccounts.containsKey("Tax") || ledgerAccounts.containsKey("Taxes")) {
								folioItemValues.put(Taxes, folioLineItems.get(i).getITEM_TAX().replaceAll("[$£ ]", "").trim());
								itemDescription.put(Taxes, taxName);
							}
						}						
					}else if (!resType.equalsIgnoreCase("GroupPickRoomingList")) {
						Wait.wait3Second();
						TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
						if (DateRange.equalsIgnoreCase("Today")) {
							if (mrbStatus.equalsIgnoreCase("SecondaryCheckIn") || mrbStatus.equalsIgnoreCase("SecondaryCheckOut") || mrbStatus.equalsIgnoreCase("SecondaryCancel")) {
								double charge = reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_ROOM_CHARGE())/numberOfNights - 
										reservationV2Page.getAmountExcludingCurrency(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL());
								ledgerAmounts.put("Room Charge", charge);
								noOfRooms = noOfRooms - 1;
								if (mrbStatus.equalsIgnoreCase("SecondaryCancel")) {
									roomChargeAmount = 0.0;
									ledgerAmounts.put("Room Charge", 0.0);
								}
							}else {
								ledgerAmounts.put("Room Charge", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_ROOM_CHARGE())/numberOfNights);
							}						
						}else {
							ledgerAmounts.put("Room Charge", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_ROOM_CHARGE()));
						}
						ledgerAmounts.put("Incidental", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_INCIDENTALS()));
						ledgerAmounts.put("Incidentals", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_INCIDENTALS()));
						ledgerAmounts.put("Tax", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_TAXES()));
						ledgerAmounts.put("Fee", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_FEES()));
						ledgerAmounts.put("Payment Method", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_PAID()));
						ledgerAmounts.put("Account (Advanced Deposit)", reservationV2Page.getAmountExcludingCurrency(tripSummary.getTS_PAID()));
						
						reservationV2Page.switchFolioTab(driver, test_steps);
						driver.findElement(By.xpath(OR_FolioNew.AddCharge)).sendKeys(Keys.ARROW_DOWN);
						if (itemStatus.equalsIgnoreCase("Void")) {
							folioNew.displayVoidItems(driver, test_steps);
						}
						ArrayList<FolioLineItem> folioLineItems = reservationV2Page.getAllFolioLineItems(driver);
						for (int i = 0; i < folioLineItems.size(); i++) {
							folioItemValues.put(folioLineItems.get(i).getITEM_CATEGORY(), folioLineItems.get(i).getITEM_AMOUNT().replaceAll("[-$£ ]", "").trim());
							itemDescription.put(folioLineItems.get(i).getITEM_CATEGORY(), folioLineItems.get(i).getITEM_DESCRIPTION());
							if (ledgerAccounts.containsKey("Tax") || ledgerAccounts.containsKey("Taxes")) {
								folioItemValues.put(Taxes, folioLineItems.get(i).getITEM_TAX().replaceAll("[$£ ]", "").trim());
								itemDescription.put(Taxes, taxName);
							}
							folioItemValues.put("Room Charge", roomChargeAmount+"");
						}
						folioItemValues.put("Account (Advanced Deposit)", folioItemValues.get(paymentMethod));
						itemDescription.put("Account (Advanced Deposit)", "Account (Advanced Deposit)");
					}
					
					if (Scenario.contains("rollback") || Scenario.contains("roll back")) {
						ledgerAmounts.put("Room Charge", 0.0);
						ledgerAmounts.put("Incidental", 0.0);
						ledgerAmounts.put("Incidentals", 0.0);
						ledgerAmounts.put("Tax", 0.0);
						ledgerAmounts.put("Fee", 0.0);
						ledgerAmounts.put("Payment Method", 0.0);
						roomChargeAmount = 0.0;
						folioItemValues.put("Room Charge", "0.0");
					}

					app_logs.info("Trip Summary: "+ledgerAmounts);
					app_logs.info("Folio Amounts: "+folioItemValues);
					app_logs.info("Folio Item Description: "+itemDescription);
					app_logs.info("Number of rooms: "+noOfRooms);
				}
			}catch (Exception e) {
				app_logs.info(e.toString());
			}
				
			if (ledgerAccounts.containsKey("Gift Certificate Redeemed")) {
				reservationV2Page.closeAllOpenedReservations(driver);
				accountPage.checkForAccountExistsAndOpen(driver, test_steps, accountName, accountType, true);
				accountPage.ClickFolio(driver);
				String giftRedeem = accountPage.getGiftCerificateRedeedmedAmount(driver).replaceAll("[-$£ ]", "").trim();
				ledgerAmounts.put("Gift Certificate Redeemed", Double.parseDouble(giftRedeem));
				folioItemValues.put("Gift Certificate Redeemed", giftRedeem);
				itemDescription.put("Gift Certificate Redeemed", "Res #"+reservationNumbers.get(0)+" - "+guestNames.get(0)+" - "+
										Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd")+" - "+
										Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MM/dd"));
				reservationNumbers.clear();
				reservationNumbers.add("");
				guestNames.clear();
				guestNames.add(accountName+" ("+accountNumber+")");			
			}
			
			if (ledgerAccounts.containsKey("Travel Agent Commission") || ledgerAccounts.containsKey("Transfer")) {
//				nav.Accounts(driver);
//				accountPage.searchForAnAccount(driver, test_steps, accountType, accountName);
//				accountPage.OpenSearchedAccount(driver, accountName, test_steps);
				reservationV2Page.closeAllOpenedReservations(driver);
				accountPage.checkForAccountExistsAndOpen(driver, test_steps, accountName, accountType, true);
				accountNumber = accountPage.getAccountNum(driver);
				app_logs.info("Account Number: "+accountNumber);
				driver.navigate().refresh();
				accountPage.ClickFolio(driver);

				String strItems = "//table[contains(@class,'table-foliogrid')]//tr/td[7]/span";
				String strDesc = "//table[contains(@class,'table-foliogrid')]//tr/td[8]/a";
				String strAmount = "//table[contains(@class,'table-foliogrid')]//tr/td[11]/span";

				List<WebElement> items = driver.findElements(By.xpath(strItems));
				List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
				List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

				for (int i = 0; i < items.size(); i++) {
					itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
					folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().replaceAll("[$£ ]", "").trim());
					
					if (items.get(i).getText().equalsIgnoreCase("Travel Agent Commission")) {
						ledgerAmounts.put("Travel Agent Commission", Double.parseDouble(itemsAmmount.get(i).getText().replaceAll("[$£ ]", "").trim()));
						itemDescription.put(items.get(i).getText(), rateplan+" : "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yyyy"));
					}
					else if (items.get(i).getText().equalsIgnoreCase("Reservation")) {
						ledgerAmounts.put("Transfers", Double.parseDouble(itemsAmmount.get(i).getText().replaceAll("[$£ ]", "").trim()));
						ledgerAmounts.put("Transfer", Double.parseDouble(itemsAmmount.get(i).getText().replaceAll("[$£ ]", "").trim()));
					}		
				}
				
				if (ledgerAccounts.containsKey("Transfer")) {
					accountPage.Accounts_AccountStatement(driver, test_steps);
				}
				
				//ledgerAmounts.put("Transfers", Double.parseDouble(giftRedeem));
				reservationNumbers.clear();
				reservationNumbers.add("");
				guestNames.clear();
				guestNames.add(accountName+" ("+accountNumber+")");	
				accountName = accountName+" ("+accountNumber+")";
			}
			app_logs.info("Item Descreption: " + itemDescription);
			app_logs.info("Foio Item values: " + folioItemValues);			
		}else {
			String AmountIncidentals = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
			String AmountRoomCharges = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
			String AmountTaxes = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
			String AmountFees = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
			double FolioTaxes = 0, FolioFees = 0;
			float FolioTripTotal = 0;
			if (!accountType.equalsIgnoreCase("Group")) {
				nav.Accounts(driver);
				accountName = "Acc"+Utility.generateRandomString();
				accountNumber = accountPage.createAccount(driver, test_steps, null, accountType, accountName, accountFirstName, accountLastName, 
						phoneNumber, phoneNumber, email, marketSegment, referral, address1, address2, address3, city, state, postalCode);
				try {
					test_steps.add("========== Adding Line Items ==========");
					app_logs.info("========== Adding Line Items ==========");

					accountPage.ClickFolio(driver);
					ArrayList<Double> allAmounts = new ArrayList<>();
					test_steps.add("Clicked Folio Tab");
					app_logs.info("Clicked Folio Tab");
					
					if (ledgerAccounts.containsKey("Gift Certificate")) {
						String gitCardAmount = Utility.GenerateRandomNumber(100, 500);
						accountPage.addLineitem1(driver, "", "Gift Certificate", gitCardAmount, test_steps);
						accountPage.Commit(driver);
						accountPage.Save(driver, test, test_steps);
						ledgerAmounts.put("Gift Certificate", Double.parseDouble(gitCardAmount));
					}else {
						report.addAccountFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatus, allAmounts, test_steps);
					}
					
					if (ledgerAccounts.containsKey("Distribution Method")) {
						String amountDistribute = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
						accountPage.distributeAmountUnitOwners(driver, amountDistribute);
						accountPage.Save(driver);
						ledgerAmounts.put("Distribution Method", Double.parseDouble(amountDistribute));
					}
					
					if (ledgerAccounts.containsKey("Payment Method") || ledgerAccounts.containsKey("Account (Advanced Deposit)")) {
						if (ledgerAccounts.containsKey("Account (Advanced Deposit)")) {
							accountPage.makePayment(driver, paymentMethod, "test", cardNumber, cardExpDate, null, "Capture", "Yes", Utility.GenerateRandomNumber(10, 100));	
						}else {
							accountPage.makePayment(driver, paymentMethod, "test", cardNumber, cardExpDate, null, "Capture", "No", null);
						}
						
						accountPage.Save(driver);
						if (paymentMethod.equalsIgnoreCase("MC") || paymentMethod.equalsIgnoreCase("Visa") || paymentMethod.equalsIgnoreCase("Amex") || paymentMethod.equalsIgnoreCase("Discover")) {
							processingMethod = "Automatic";
						}else {
							processingMethod = "Manual";
						}
					}
					
					try {
						accountPage.getTaxes(driver, test_steps);
					} catch (Exception e) {
						app_logs.info("Taxes amount not available");
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
					app_logs.info("FolioTripTotal " + FolioTripTotal);
					
					try {
						ledgerAmounts.put("Payments", accountPage.getPayments(driver, test_steps));
						ledgerAmounts.put("Payment Method", accountPage.getPayments(driver, test_steps));
					}catch (Exception e) {
						app_logs.info(e.toString());
					}

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
						itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
						folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText().replaceAll("[-$£ ]", "").trim());
					}
					for (int i = 0; i < items.size(); i++) {
						if (items.get(i).getText().equalsIgnoreCase(paymentMethod)) {
							folioItemValues.put("Account (Advanced Deposit)", itemsAmmount.get(i).getText().replaceAll("[-$£ ]", "").trim());
						}
					}				
					itemDescription.put("Account (Advanced Deposit)", "Account (Advanced Deposit)");
					
					app_logs.info("Item Descreption: " + itemDescription);
					app_logs.info("Foio Item values: " + folioItemValues);

					if (!LedgerAccount.equalsIgnoreCase("Gift Certificate")) {
						ledgerAmounts.put("Room Charge", allAmounts.get(1));
						ledgerAmounts.put("Incidental", allAmounts.get(0));
						ledgerAmounts.put("Taxes", FolioTaxes);
						ledgerAmounts.put("Fees", FolioFees);
						ledgerAmounts.put("Account (Advanced Deposit)", accountPage.getPayments(driver, test_steps));
					}
					
					if (itemStatus.equalsIgnoreCase("Void")) {
						ledgerAmounts.put("Room Charge", 0.0);
						ledgerAmounts.put("Incidental", 0.0);
						ledgerAmounts.put("Taxes", 0.0);
						ledgerAmounts.put("Fees", 0.0);
					}

					app_logs.info("Ledger Amounts: " + ledgerAmounts);

					if (itemStatus.equalsIgnoreCase("Locked")) {
						accountPage.Accounts_AccountStatement(driver, test_steps);
					}

				} catch (Exception e) {
					if (Utility.reTry.get(test_name) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems", driver);
					} else {	
						//Assert.assertTrue(false);
					}
				} catch (Error e) {
					test_steps.add(e.toString());
				}
					
				guestName = accountName + " (" + accountNumber + ")";
				guestNames.clear();
				guestNames.add(guestName);

			}else {
				try {
					test_steps.add("========== Creating Group account ==========");
					app_logs.info("========== Creating Group account ==========");
					accountName = "Acc"+Utility.generateRandomString();
					nav.groups(driver);
					group.type_GroupName(driver, test, accountName, test_steps);
					accountNumber = group.getAccountNo(driver);
					group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
					group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber, address1,
							city, state, country, postalCode, test_steps);
					group.Billinginfo(driver);
					group.Save(driver, test_steps);
//					group.clickGroupFolioTab(driver);
//					if (!Incidentals.isEmpty()) {
//						group.addLineItems(driver, Incidentals, Utility.GenerateRandomNumber(5, 50));
//						group.commit(driver, null);
//						group.Save(driver, test_steps);
//						group.clickGroupFolioTab(driver);
//					}
					
					guestName = accountName + " (" + accountNumber + ")";
					guestNames.clear();
					guestNames.add(guestName);
										
					group.navigateFolio(driver, test, test_steps);
					ArrayList<Double> allAmounts = new ArrayList<>();
					report.addGroupFolioLineItems(driver, Incidentals, AmountIncidentals, RoomCharges, AmountRoomCharges, Taxes, AmountTaxes, Fees, AmountFees, itemStatus, allAmounts, test_steps);
					group.navigateFolio(driver, test, test_steps);
					
					if (ledgerAccounts.containsKey("Payment Method")) {
						group.makePayment(driver, paymentMethod, "Test", cardNumber, cardExpDate, 
								cvvCode, "Capture", "Yes", "50", test_steps);
						if (paymentMethod.equalsIgnoreCase("MC") || paymentMethod.equalsIgnoreCase("Visa") || paymentMethod.equalsIgnoreCase("Amex") || paymentMethod.equalsIgnoreCase("Discover")) {
							processingMethod = "Automatic";
						}else {
							processingMethod = "Manual";
						}						
					}
					group.displayVoidItemButton(driver);
					group.unCheckIncludeTaxInLineAmount(driver);
					
					FolioTaxes = Double.parseDouble(group.getTaxesAmount(driver).replaceAll("[$£ ]", "").trim());
					FolioFees = Double.parseDouble(group.getFeesAmount(driver).replaceAll("[$£ ]", "").trim());
					FolioTripTotal = Float.parseFloat(group.getNewChargesAmount(driver));
					app_logs.info("FolioTripTotal " + FolioTripTotal);

					String strItems = "//table[contains(@id,'dgLineItems')]//tr/td[6]/span";
					String strDesc = "//table[contains(@id,'dgLineItems')]//tr/td/table//a";
					String strAmount = "//table[contains(@id,'dgLineItems')]//tr/td[12]/span";
					String strTax = "//table[contains(@id,'dgLineItems')]//tr/td[10]/span";

					List<WebElement> items = driver.findElements(By.xpath(strItems));
					List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
					List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

					for (int i = 0; i < items.size(); i++) {
						itemDescription.put(items.get(i).getText(), itemsDesc.get(i).getText());
						folioItemValues.put(items.get(i).getText(), itemsAmmount.get(i).getText());
						if (items.get(i).getText().equalsIgnoreCase("MC") || items.get(i).getText().equalsIgnoreCase("Visa")) {
							String pMethod = items.get(i).getText();
							itemsDesc.get(i).click();
							String payInfo = group.getPaymentInfo(driver).replaceAll("\n", " ") + " CVV Code: "+cvvCode;
							itemDescription.put(pMethod, payInfo);
						}
						if (ledgerAccounts.containsKey("Tax") || ledgerAccounts.containsKey("Taxes")) {
							folioItemValues.put(Taxes, group.getTaxesAmount(driver));
							itemDescription.put(Taxes, taxName);
						}
					}
					
					app_logs.info("Item Descreption: " + itemDescription);
					app_logs.info("Foio Item values: " + folioItemValues);

					ledgerAmounts.put("Room Charge", allAmounts.get(1));
					ledgerAmounts.put("Incidental", allAmounts.get(0));
					ledgerAmounts.put("Taxes", FolioTaxes);
					ledgerAmounts.put("Fees", FolioFees);
					ledgerAmounts.put("Payment Method", Double.parseDouble(group.getPaymentsAmount(driver).replaceAll("[$£ ]", "").trim()));

					app_logs.info("Ledger Amounts: " + ledgerAmounts);
					
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
			
		test_steps.add("========== Action completed at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		
		Wait.wait60Second();
		Wait.wait60Second();
		//Wait.wait60Second();
		test_steps.add("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		
		try {
			Utility.switchTab(driver, reportsTab);
			report.clickOnRunReport(driver);
			report.clickOnRunReport(driver);
			
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
							noOfRooms, test_steps);

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
				
				//test_steps.add("====  <b>Detailed View Validation</b> =====");
				// Detailed View
				try {
					HashMap<String, ArrayList<String>> TransactionDetails = new HashMap<>();
					if (!Scenario.contains("rollback") && !Scenario.contains("roll back")) {
						test_steps.add("====  <b>Detailed View Validation</b> =====");
						report.validateLedgerReportDetailedView(driver, ledgerAccounts, folioItemValues, noOfRooms,
								reservationNumbers, guestNames, accountName, arrivalDates, itemDescription, roomChargeAmount, propertyCurrency,
								isTaxExempt, propertyDateFormat, processingMethod, test_steps);
					}

//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
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
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			}
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
	
//		RetryFailedTestCases.count = Utility.reset_count;
//		Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

	}
  
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("LedgerBalances", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
//		driver.quit();
	}

}
