package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
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
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2AdvancedDepositReportValidationReservationV2 extends TestCore {

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
	changeOption, taxName, feeName, randomString, cvvCode = "123", depositInbound, depositOutbound;
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

	
	ArrayList<String> reservationNumbers = new ArrayList<>();
	ArrayList<String> arrivalDates = new ArrayList<>();
	ArrayList<String> departureDates = new ArrayList<>();
	ArrayList<String> roomNumbers = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();
	ArrayList<String> userProcessed = new ArrayList<>();
	ArrayList<String> depositAcceptedDate = new ArrayList<>();
	ArrayList<String> description = new ArrayList<>();
	ArrayList<String> depositAmount = new ArrayList<>();
	ArrayList<String> accountNumbers = new ArrayList<>();
	ArrayList<String> accountNames = new ArrayList<>();
	ArrayList<String> descriptionOutbound = new ArrayList<>();
	ArrayList<String> depositAmountOutbound = new ArrayList<>();
	
	
	HashMap<String, ArrayList<String>> inboundDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> outboundDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> netDepositsDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> refundedDepositsDetails = new HashMap<>();
		
	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();	
	ArrayList<String> dates = new ArrayList<>();	
	ArrayList<String> amountList = new ArrayList<>();
	
	HashMap<String, HashMap<String, String>> beforeSummaryViewData = new HashMap<>();
	HashMap<String, HashMap<String, String>> afterSummaryViewData = new HashMap<>();
	HashMap<String, ArrayList<String>> beforeDetailedViewList = new HashMap<>();
	
	HashMap<String, HashMap<String, Double>> expSummaryViewData = new HashMap<>();	
	HashMap<String, HashMap<String, ArrayList<String>>> expDetailedViewData = new HashMap<>();
	
	HashMap<String, Double> inboundData = new HashMap<>();
	HashMap<String, Double> outboundData = new HashMap<>();
	HashMap<String, Double> netDepositsData = new HashMap<>();
	HashMap<String, Double> refundedDepositsData = new HashMap<>();
	
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
	public void validateRoomForeCastReport(String Scenario, String DateRange, String StartDate, String EndDate,
			String includedDepositTypes,
			String accountType, String associateAccount, String resType, String CheckInDate, String CheckOutDate,  
			String rateplan, String roomClass, String paymentMethod, String cardNumber,
			String resStatus) throws Throwable {

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
		String referral = "Walk In";
		String marketSegment = "Internet";
		guestFirstName = ""; guestFirstName = "Auto";
		guestLastName = ""; guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = ""; guestFullName = guestFirstName+" "+guestLastName;
		guestSalutation = "Mr.";
		additionalGuests = 0;
		roomNumber=null;
		isQuote = false;
		String isTaxExempt = "No";
		
		ArrayList<String> inputs = new ArrayList<>();
		if (includedDepositTypes.split("\\|").length > 1) {
			String[] include = includedDepositTypes.split("\\|");
			for (int i = 0; i < include.length; i++) {
				inputs.add(include[i]);
			}
		}else if (includedDepositTypes.equalsIgnoreCase("All")) {
			inputs.add("Deposits Held as Liability (Inbound)");
			inputs.add("Deposits Added to Portfolio (Outbound)");
			inputs.add("Net Deposits");
			inputs.add("Refunded Deposits (Cancellations)");
		}else {
			inputs.add(includedDepositTypes);
		}

		expDetailedViewData = report.setDefaultDetailedViewDataAdvancedDepositGuest();
		expSummaryViewData = report.setDefaultSummaryViewDataAdvancedDeposit();
		inboundData = report.setDefaultSummaryViewDataAdvancedDepositWithType();
		outboundData = report.setDefaultSummaryViewDataAdvancedDepositWithType();
		netDepositsData = report.setDefaultSummaryViewDataAdvancedDepositWithType();
		refundedDepositsData = report.setDefaultSummaryViewDataAdvancedDepositWithType();
		try {
			nav.ReportsV2(driver);
			report.navigateToAdvanceDepositReport(driver);
			report.selectDateRange(driver, StartDate, EndDate, DateRange, test_steps);
			//report.selectInputsAdvancedDeposit(driver, test_steps, includedDepositTypes);
			report.selectAllInputOptions(driver, test_steps);
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
			beforeSummaryViewData = report.getSummaryViewDataAdvancedDeposit(driver, test_steps);			
//			beforeDetailedViewList = report.getDetailedViewListAdvancedDeposit(driver, "Deposits Held as Liability (Inbound)", "Account Advanced Deposits", test_steps);

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
		
		if (Utility.validateString(resType)) {
			if (resType.equalsIgnoreCase("Single") || resType.equalsIgnoreCase("TapeChart") || resType.equalsIgnoreCase("Copy") || 
					resType.equalsIgnoreCase("GroupPickRoomingList") || resType.equalsIgnoreCase("GroupPickBlueIcon")) {	
							
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
					
					BlockName = BlockName + Utility.GenerateRandomString15Digit();
					group.navigateRoomBlock(driver, test);			
					test_steps.add("========== Creating Group block ==========");
					app_logs.info("========== Creating Group block ==========");
					group.createNewBlockWithMultiRoomClasses(driver, BlockName, CheckInDate, CheckOutDate, rateplan, "1", roomClass, test_steps);
					//test_steps.addAll(group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRooms, RoomClass));
					group.navigateRoomBlock(driver, test);													
					
					if (resType.equalsIgnoreCase("GroupPickRoomingList")) {
						test_steps.addAll(group.roomingListClick(driver));
						advgrp.enter_RoomPickupdetails(driver, test_steps);
						reservationNumber = group.pickUp_getResNo(driver);
						app_logs.info("Group pick Reservation number: "+reservationNumber);			
						group.pickUpCloseClick(driver);
						
						nav.ReservationV2_Backward(driver);
						reservationV2Page.basicSearch_WithReservationNo(driver, reservationNumber, true);
						
					}else if (resType.equalsIgnoreCase("GroupPickBlueIcon")) {
						advgrp.clickBlueBookIcon(driver);
						reservationV2Page.clickNext(driver, test_steps);
						reservationV2Page.enter_GuestName(driver, test_steps, "Mr.", guestFirstName, guestLastName);
						reservationV2Page.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, "Test", cardExpDate, "");
						reservationV2Page.clickBookNow(driver, test_steps);
						reservationV2Page.clickCloseReservationSavePopup(driver, test_steps);
//						roomChargeAmount = Double.parseDouble(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL().replaceAll("[$£ ]", "").trim());
					}			
				}else {
					reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes, CheckInDate, CheckOutDate, adults, 
							children, rateplan, null, roomClass, roomClassAbb, "Mr.", guestFirstName, guestLastName, phoneNumber,
							phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
							referral, paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber, isQuote, 
							noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, 
							taskAssignee, taskStatus, accountName, accountType, guestFirstName, guestLastName, taxExempt, "");
					roomCharge = reservationData.get("Room Charges");
				}

				reservationNumber = reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim();				
				reservationNumbers.clear();
				reservationNumbers.add(reservationNumber);
				
				arrivalDates.clear();
				if (dFormat.equalsIgnoreCase("USA")) {
					arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM d, yyyy"));
				}else if (clientDateFormat.equalsIgnoreCase("International")) {
					arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "d MMM, yyyy"));
				}
				app_logs.info("Arrival Date: "+arrivalDates);
				
				departureDates.clear();
				if (dFormat.equalsIgnoreCase("USA")) {
					departureDates.add(Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MMM d, yyyy"));
				}else if (clientDateFormat.equalsIgnoreCase("International")) {
					departureDates.add(Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "d MMM, yyyy"));
				}
				app_logs.info("Departure Date: "+departureDates);
				
				roomNumbers.clear();
				roomNumbers.add(reservationV2Page.getStayInfoDetail(driver).getSI_ROOM_NUMBER());
				
				//guestName = guestFirstName+" "+guestLastName;
				guestName = reservationV2Page.getStatusBarDetail(driver).getSB_GUEST_NAME().replaceAll("Mr. ", "");
				guestNames.clear();
				guestNames.add(guestName);
				app_logs.info("Guest Names: " + guestNames);
				
				userProcessed.clear();
				userProcessed.add(Utility.loginReportsV2ReservationV2.get(2));
				
				depositAcceptedDate.clear();
				if (dFormat.equalsIgnoreCase("USA")) {
					depositAcceptedDate.add(Utility.getCurrentDate("MMM d, yyyy"));
				}else if (clientDateFormat.equalsIgnoreCase("International")) {
					depositAcceptedDate.add(Utility.getCurrentDate("d MMM, yyyy"));
				}
				app_logs.info("Deposit Accepted Date: "+depositAcceptedDate);
				
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
						taskAssignee, taskStatus, accountName, accountType, guestFirstName, guestLastName, taxExempt, "");				
				roomCharge = reservationData.get("Room Charges");				
				reservationNumber = reservationV2Page.getStatusBarDetail(driver).getSB_CONFIRMATION_NO().trim();
									
				reservationNumbers.clear();					
				arrivalDates.clear();					
				departureDates.clear();
				roomNumbers.clear();
				guestNames.clear();arrivalDates.clear();dates.clear();
				userProcessed.clear();
				userProcessed.add(Utility.loginReportsV2ReservationV2.get(2));
				
				depositAcceptedDate.clear();
				if (dFormat.equalsIgnoreCase("USA")) {
					depositAcceptedDate.add(Utility.getCurrentDate("MMM d, yyyy"));
				}else if (clientDateFormat.equalsIgnoreCase("International")) {
					depositAcceptedDate.add(Utility.getCurrentDate("d MMM, yyyy"));
				}
				app_logs.info("Deposit Accepted Date: "+depositAcceptedDate);
				
				for (int i = 0; i < noOfRooms; i++) {
					reservationNumbers.add(reservationNumber);
					
					String[] CheckInDates = CheckInDate.split("\\|");
					if (dFormat.equalsIgnoreCase("USA")) {
						arrivalDates.add(Utility.parseDate(CheckInDates[i], "dd/MM/yyyy", "MMM d, yyyy"));
					}else if (clientDateFormat.equalsIgnoreCase("International")) {
						arrivalDates.add(Utility.parseDate(CheckInDates[i], "dd/MM/yyyy", "d MMM, yyyy"));
					}
					app_logs.info("Arrival Date: "+arrivalDates);
					
					String[] CheckOutDates = CheckOutDate.split("\\|");
					if (dFormat.equalsIgnoreCase("USA")) {
						departureDates.add(Utility.parseDate(CheckOutDates[i], "dd/MM/yyyy", "MMM d, yyyy"));
					}else if (clientDateFormat.equalsIgnoreCase("International")) {
						departureDates.add(Utility.parseDate(CheckOutDates[i], "dd/MM/yyyy", "d MMM, yyyy"));
					}
					app_logs.info("Departure Date: "+departureDates);
				
					roomNumbers.add(reservationV2Page.getStayInfoDetailMRB(driver).get(i).getSI_ROOM_NUMBER());
					
					String[] fNames = guestFirstName.split("\\|");
					String[] lNames = guestLastName.split("\\|");
					app_logs.info("Fanmes: " + fNames.length);
					app_logs.info("Lanmes: " + lNames.length);
					app_logs.info("Loop " + i);
					guestNames.add(fNames[i] + " " + lNames[i]);	
					app_logs.info("Guest Names: "+guestNames);
				}
				
				roomChargeAmount = (Double.parseDouble(reservationV2Page.getStayInfoDetailMRB(driver).get(0).getSI_ROOM_TOTAL().replaceAll("[$£ ]", "").trim())/numberOfNights);					
			}
			
			app_logs.info("Making Payment");
			for (int i = 0; i < noOfRooms; i++) {
				reservationV2Page.takePayment(driver, test_steps, false, null, "Test",true);
			}
			
			reservationV2Page.changeReservationStatus(driver, test_steps, resStatus, true);
			
			reservationV2Page.switchFolioTab(driver, test_steps);
			folioNew.clickFolioPaymentsTab(driver, test_steps);
			
			ArrayList<HashMap<String, String>> paymentDetails = folioNew.getAdvancedPaymentDetails(driver, test_steps, paymentMethod);
			app_logs.info("Payment Details: "+paymentDetails);
			
			ArrayList<FolioLineItem> folioLineItems = reservationV2Page.getAllFolioLineItems(driver);
			depositAmount.clear();
//			depositAmount.add(folioLineItems.get(0).getITEM_AMOUNT().trim());
			
			description.clear();			
			if (paymentMethod.equalsIgnoreCase("MC") || paymentMethod.equalsIgnoreCase("Visa")) {
				String payInfo = paymentMethod + "("+cardNumber.substring(12)+")";
				description.add(payInfo);
			}else {
				description.add(folioLineItems.get(0).getITEM_DESCRIPTION());
			}
			
			List<WebElement> allFolios = folioNew.getAllFolioLinks(driver);
			double dep = 0.0;
			for (int i = 0; i < allFolios.size(); i++) {
				allFolios.get(i).click();
				Wait.wait2Second();
				folioLineItems = reservationV2Page.getAllFolioLineItems(driver);
				
				if (folioLineItems.size() > 0) {
					depositAmount.add(folioLineItems.get(0).getITEM_AMOUNT().trim());
					dep = dep + Double.parseDouble(folioLineItems.get(0).getITEM_AMOUNT().replaceAll("[-$£ ]", "").trim());
				}
			}
			allFolios.get(0).click();
			inboundData.put("Guest Advanced Deposit", dep);
			inboundData.put("Total Advanced Deposit", inboundData.get("Guest Advanced Deposit") + inboundData.get("Account Advanced Deposit"));
			
			if (resStatus.equalsIgnoreCase("CheckIn")) {
				dep = 0.0;
				for (int i = 0; i < allFolios.size(); i++) {
					allFolios.get(i).click();
					Wait.wait3Second();
					folioLineItems = reservationV2Page.getAllFolioLineItems(driver);
					if (folioLineItems.size() > 0) {
						dep = dep + Double.parseDouble(folioLineItems.get(0).getITEM_AMOUNT().replaceAll("[-$£ ]", "").trim());
					}
				}
				allFolios.get(0).click();			
				outboundData.put("Guest Advanced Deposit", -dep);
				outboundData.put("Total Advanced Deposit", outboundData.get("Guest Advanced Deposit") + outboundData.get("Account Advanced Deposit"));
			}else if (resStatus.equalsIgnoreCase("Cancelled")) {
				dep = 0.0;
				for (int i = 0; i < allFolios.size(); i++) {
					allFolios.get(i).click();
					Wait.wait3Second();
					folioLineItems = reservationV2Page.getAllFolioLineItems(driver);
					if (folioLineItems.size() > 0) {
						dep = dep + Double.parseDouble(folioLineItems.get(0).getITEM_AMOUNT().replaceAll("[-$£ ]", "").trim());
					}
				}
				allFolios.get(0).click();			
				outboundData.put("Guest Advanced Deposit", -dep);
				outboundData.put("Total Advanced Deposit", outboundData.get("Guest Advanced Deposit") + outboundData.get("Account Advanced Deposit"));
				//refundedDepositsData.put("Guest Advanced Deposit", folioNew.getFolioPayments(driver, test_steps));
				//refundedDepositsData.put("Total Advanced Deposit", refundedDepositsData.get("Guest Advanced Deposit") + refundedDepositsData.get("Account Advanced Deposit"));
			}
			
			netDepositsData.put("Guest Advanced Deposit", (inboundData.get("Guest Advanced Deposit") + outboundData.get("Guest Advanced Deposit")));
			netDepositsData.put("Total Advanced Deposit", (inboundData.get("Total Advanced Deposit") + outboundData.get("Total Advanced Deposit")));
			
			expSummaryViewData.put("Deposits Held as Liability (Inbound)", inboundData);
			expSummaryViewData.put("Deposits Added to Portfolio (Outbound)", outboundData);
			expSummaryViewData.put("Net Deposits", netDepositsData);
			expSummaryViewData.put("Refunded Deposits (Cancellations)", refundedDepositsData);
			
			app_logs.info("Expected Summary View Data: "+expSummaryViewData);
			
			inboundDetails.put("Reservation #", reservationNumbers);
			inboundDetails.put("Arrival Date", arrivalDates);
			inboundDetails.put("Departure Date", departureDates);
			inboundDetails.put("Room #", roomNumbers);
			inboundDetails.put("Guest Name", guestNames);
			inboundDetails.put("User Processed", userProcessed);
			inboundDetails.put("Deposit Accepted Date", depositAcceptedDate);
			inboundDetails.put("Description", description);
			inboundDetails.put("Deposit Amount", depositAmount);
			
			if (resStatus.equalsIgnoreCase("CheckIn")) {
				outboundDetails.put("Reservation #", reservationNumbers);
				outboundDetails.put("Arrival Date", arrivalDates);
				outboundDetails.put("Departure Date", departureDates);
				outboundDetails.put("Room #", roomNumbers);
				outboundDetails.put("Guest Name", guestNames);
				outboundDetails.put("User Processed", userProcessed);
				outboundDetails.put("Deposit Accepted Date", depositAcceptedDate);
				for (int i = 0; i < noOfRooms; i++) {
					descriptionOutbound.add("Transferred From Advanced Deposit");					
					depositAmountOutbound.add(i, propertyCurrency+"-"+depositAmount.get(i).replaceAll("[-$£ ]", "").trim());
				}
				outboundDetails.put("Description", descriptionOutbound);
				outboundDetails.put("Deposit Amount", depositAmountOutbound);
			}else if (resStatus.equalsIgnoreCase("Cancelled")) {
				refundedDepositsDetails.put("Reservation #", reservationNumbers);
				refundedDepositsDetails.put("Arrival Date", arrivalDates);
				refundedDepositsDetails.put("Departure Date", departureDates);
				refundedDepositsDetails.put("Room #", roomNumbers);
				refundedDepositsDetails.put("Guest Name", guestNames);
				refundedDepositsDetails.put("User Processed", userProcessed);
				refundedDepositsDetails.put("Deposit Accepted Date", depositAcceptedDate);
				refundedDepositsDetails.put("Description", description);
				refundedDepositsDetails.put("Deposit Amount", depositAmount);
			}
			
			expDetailedViewData.put("Deposits Held as Liability (Inbound)", inboundDetails);
			expDetailedViewData.put("Deposits Added to Portfolio (Outbound)", outboundDetails);
			expDetailedViewData.put("Net Deposits", netDepositsDetails);
			expDetailedViewData.put("Refunded Deposits (Cancellations)", refundedDepositsDetails);
			
			app_logs.info("Expected Detailed View Data: "+expDetailedViewData);
				
		}else {
			accountNumbers.clear();
			accountNames.clear();
			userProcessed.clear();
			depositAcceptedDate.clear();
			description.clear();
			depositAmount.clear();
			
			if (!accountType.equalsIgnoreCase("Group")) {
				nav.Accounts(driver);
				accountName = "Acc"+Utility.generateRandomString();
				accountNumber = accountPage.createAccount(driver, test_steps, null, accountType, accountName, accountFirstName, accountLastName, 
						phoneNumber, phoneNumber, email, marketSegment, referral, address1, address2, address3, city, state, postalCode);
				
				accountNumbers.add(accountNumber);
				accountNames.add(accountName);
				userProcessed.add(Utility.loginReportsV2ReservationV2.get(2));
				if (dFormat.equalsIgnoreCase("USA")) {
					depositAcceptedDate.add(Utility.getCurrentDate("MMM d, yyyy"));
				}else if (clientDateFormat.equalsIgnoreCase("International")) {
					depositAcceptedDate.add(Utility.getCurrentDate("d MMM, yyyy"));
				}
				app_logs.info("Deposit Accepted Date: "+depositAcceptedDate);
				depositInbound = Utility.GenerateRandomNumber(10, 100);
				depositOutbound = depositInbound;
				accountPage.ClickFolio(driver);
				accountPage.makePayment(driver, paymentMethod, "test", cardNumber, cardExpDate, null, "Capture", "Yes", depositInbound);
				accountPage.Save(driver);
				
				if (Scenario.contains("outbound") || Scenario.contains("Outbound")) {					
					accountPage.addLineItems(driver, "POS", depositOutbound, test_steps);
					accountPage.Commit(driver);
					accountPage.Save(driver);
					//accountPage.postedLineItem(driver, "POS", test_steps);
					accountPage.outboundAdvancedDepositForLineItem(driver, "POS", test_steps);
					outboundData.put("Account Advanced Deposit", -Double.parseDouble(depositOutbound.replaceAll("[-$£ ]", "").trim()));
					outboundData.put("Total Advanced Deposit", outboundData.get("Guest Advanced Deposit") + outboundData.get("Account Advanced Deposit"));
				}
				
				String strItems = "//table[contains(@class,'table-foliogrid')]//tr/td[7]/span";
				String strDesc = "//table[contains(@class,'table-foliogrid')]//tr/td[8]/a";
				String strAmount = "//table[contains(@class,'table-foliogrid')]//tr/td[11]/span";

				List<WebElement> items = driver.findElements(By.xpath(strItems));
				List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
				List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

				for (int i = 0; i < items.size(); i++) {
					if (items.get(i).getText().equalsIgnoreCase(paymentMethod)) {
						//description.add(itemsDesc.get(i).getText());
						depositAmount.add(itemsAmmount.get(i).getText().replace(" ", "").trim());							
						if (items.get(i).getText().equalsIgnoreCase("MC") || items.get(i).getText().equalsIgnoreCase("Visa")) {
							String payInfo = paymentMethod + "("+cardNumber.substring(12)+")";
							description.add(payInfo);
						}else {
							description.add(itemsDesc.get(i).getText());
						}						
					}
				}
				
				double dep = 0.0;
				for (int i = 0; i < depositAmount.size(); i++) {
					dep = dep + Double.parseDouble(depositAmount.get(i).replaceAll("[-$£ ]", "").trim());
				}

				inboundData.put("Account Advanced Deposit", dep);
				inboundData.put("Total Advanced Deposit", inboundData.get("Guest Advanced Deposit") + inboundData.get("Account Advanced Deposit"));
				
				if (Scenario.contains("outbound") || Scenario.contains("Outbound")) {
					descriptionOutbound.add("Transferred From Advanced Deposit");					
					depositAmountOutbound.add(propertyCurrency+"-"+depositAmount.get(0).replaceAll("[-$£ ]", "").trim());
				}			
			}else {
				try {
					accountNumbers.clear();
					accountNames.clear();
					userProcessed.clear();
					depositAcceptedDate.clear();
					description.clear();
					depositAmount.clear();
					
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
					
					accountNumbers.add(accountNumber);
					accountNames.add(accountName);
					userProcessed.add(Utility.loginReportsV2ReservationV2.get(2));
					if (dFormat.equalsIgnoreCase("USA")) {
						depositAcceptedDate.add(Utility.getCurrentDate("MMM d, yyyy"));
					}else if (clientDateFormat.equalsIgnoreCase("International")) {
						depositAcceptedDate.add(Utility.getCurrentDate("d MMM, yyyy"));
					}
					app_logs.info("Deposit Accepted Date: "+depositAcceptedDate);
					depositInbound = Utility.GenerateRandomNumber(10, 100);
					depositOutbound = depositInbound;
					
					group.clickGroupFolioTab(driver);
					group.makePayment(driver, paymentMethod, "Test", cardNumber, cardExpDate, 
							cvvCode, "Capture", "Yes", depositInbound, test_steps);
					
					if (Scenario.contains("outbound") || Scenario.contains("Outbound")) {	
						group.addLineItems(driver, "POS", depositOutbound);
						group.commit(driver, null);
						group.Save(driver, test_steps);
						group.clickGroupFolioTab(driver);
						
						group.outboundAdvancedDepositForLineItem(driver, "POS", test_steps);
						group.clickGroupFolioTab(driver);
						outboundData.put("Account Advanced Deposit", -Double.parseDouble(depositOutbound.replaceAll("[-$£ ]", "").trim()));
						outboundData.put("Total Advanced Deposit", outboundData.get("Guest Advanced Deposit") + outboundData.get("Account Advanced Deposit"));
					}

					String strItems = "//table[contains(@id,'dgLineItems')]//tr/td[6]/span";
					String strDesc = "//table[contains(@id,'dgLineItems')]//tr/td/table//a";
					String strAmount = "//table[contains(@id,'dgLineItems')]//tr/td[12]/span";

					List<WebElement> items = driver.findElements(By.xpath(strItems));
					List<WebElement> itemsDesc = driver.findElements(By.xpath(strDesc));
					List<WebElement> itemsAmmount = driver.findElements(By.xpath(strAmount));

					for (int i = 0; i < items.size(); i++) {						
						if (items.get(i).getText().equalsIgnoreCase(paymentMethod)) {
							depositAmount.add(propertyCurrency+itemsAmmount.get(i).getText().replace(" ", "").trim());							
							if (items.get(i).getText().equalsIgnoreCase("MC") || items.get(i).getText().equalsIgnoreCase("Visa")) {
								String payInfo = paymentMethod + "("+cardNumber.substring(12)+")";
								description.add(payInfo);
							}else {
								description.add(itemsDesc.get(i).getText());
							}
						}
					}
					
					double dep = 0.0;
					for (int i = 0; i < depositAmount.size(); i++) {
						dep = dep + Double.parseDouble(depositAmount.get(i).replaceAll("[-$£ ]", "").trim());
					}

					inboundData.put("Account Advanced Deposit", dep);
					inboundData.put("Total Advanced Deposit", inboundData.get("Guest Advanced Deposit") + inboundData.get("Account Advanced Deposit"));
					
					if (Scenario.contains("outbound") || Scenario.contains("Outbound")) {
						descriptionOutbound.add("Transferred From Advanced Deposit");					
						depositAmountOutbound.add(propertyCurrency+"-"+depositAmount.get(0).replaceAll("[-$£ ]", "").trim());
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
			
			netDepositsData.put("Account Advanced Deposit", (inboundData.get("Account Advanced Deposit") + outboundData.get("Account Advanced Deposit")));
			netDepositsData.put("Total Advanced Deposit", (inboundData.get("Total Advanced Deposit") + outboundData.get("Total Advanced Deposit")));
		
			expSummaryViewData.put("Deposits Held as Liability (Inbound)", inboundData);
			expSummaryViewData.put("Deposits Added to Portfolio (Outbound)", outboundData);
			expSummaryViewData.put("Net Deposits", netDepositsData);
			expSummaryViewData.put("Refunded Deposits (Cancellations)", refundedDepositsData);
			
			app_logs.info("Expected Summary View Data: "+expSummaryViewData);
			
			inboundDetails.put("Account #", accountNumbers);
			inboundDetails.put("Account Name", accountNames);
			inboundDetails.put("User Processed", userProcessed);
			inboundDetails.put("Deposit Accepted Date", depositAcceptedDate);
			inboundDetails.put("Description", description);
			inboundDetails.put("Deposit Amount", depositAmount);
			
			if (Scenario.contains("outbound") || Scenario.contains("Outbound")) {
				outboundDetails.put("Account #", accountNumbers);
				outboundDetails.put("Account Name", accountNames);
				outboundDetails.put("User Processed", userProcessed);
				outboundDetails.put("Deposit Accepted Date", depositAcceptedDate);
				outboundDetails.put("Description", descriptionOutbound);
				outboundDetails.put("Deposit Amount", depositAmountOutbound);
			}else if (Scenario.contains("refund") || Scenario.contains("Refund")) {
				refundedDepositsDetails.put("Account #", accountNumbers);
				refundedDepositsDetails.put("Account Name", accountNames);
				refundedDepositsDetails.put("User Processed", userProcessed);
				refundedDepositsDetails.put("Deposit Accepted Date", depositAcceptedDate);
				refundedDepositsDetails.put("Description", description);
				refundedDepositsDetails.put("Deposit Amount", depositAmount);
			}
			
			expDetailedViewData.put("Deposits Held as Liability (Inbound)", inboundDetails);
			expDetailedViewData.put("Deposits Added to Portfolio (Outbound)", outboundDetails);
			expDetailedViewData.put("Net Deposits", netDepositsDetails);
			expDetailedViewData.put("Refunded Deposits (Cancellations)", refundedDepositsDetails);
			
			app_logs.info("Expected Detailed View Data: "+expDetailedViewData);
		}
		
		test_steps.add("========== Action completed at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		
		Wait.wait60Second();
		Wait.wait60Second();
		//Wait.wait60Second();
		test_steps.add("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Validating Reports after 2 minutes at "+java.time.LocalTime.now()+"==========");
		
		Utility.switchTab(driver, reportsTab);
		report.clickOnRunReport(driver);
		report.clickOnRunReport(driver);
		
		if (report.checkNoReportDataAvailable(driver, test_steps)) {
			app_logs.info("Failed, Report Genaration failed, got 'No Report Data available' toast message");
			test_steps.add("AssertionError Failed, Report Genaration failed, got 'No Report Data available' toast message");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}else {
			afterSummaryViewData = report.getSummaryViewDataAdvancedDeposit(driver, test_steps);
			app_logs.info("Before: "+beforeSummaryViewData);
			app_logs.info("After: "+afterSummaryViewData);
			app_logs.info("Expected: "+expSummaryViewData);
			
			app_logs.info("Validate Summary View");
			test_steps.add("===== Validate Summary View =====");
			report.validateSummaryViewAdvancedDeposit(driver, inputs, beforeSummaryViewData, afterSummaryViewData, expSummaryViewData, test_steps);
			
			app_logs.info("Validating detailed view");
			test_steps.add("===== Validating Detailed view =====");
			if (Utility.validateString(resType)) {
				report.validateDetailedViewGuestAdvancedDeposit(driver, inputs, expDetailedViewData, test_steps);
			}else {
				report.validateDetailedViewAccountAdvancedDeposit(driver, inputs, expDetailedViewData, test_steps);
			}			
		}

		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
	}
  
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("AdvancedDeposit", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
//		driver.quit();
	}

}