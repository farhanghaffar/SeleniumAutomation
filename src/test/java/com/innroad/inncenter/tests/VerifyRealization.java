package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
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
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightAudit;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyRealization extends TestCore{
	private WebDriver driver = null;
	public static String testName = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyRealization(String TestCaseID,String caseType, String delim,String ratePlanName,
	String roomClassName, String roomClassAbbreviation,String priodLockDate) throws Exception {
		Utility.DELIM = "\\" + delim;

		if(!Utility.validateString(TestCaseID)) {
			caseId.add("686344");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}
		//CustomFolio
		testName = "VerifyRealization - " + caseType;
		testDescription = "Verify Night Audit Functionality for " +  caseType + "</br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/686344' target='_blank'>"
				+ "Click here to open TestRail: C686344</a>";
		testCatagory = "RealizationVerification";
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		NightAudit nightAudit = new NightAudit();
		Folio folio = new Folio();
		ReservationSearch reservationSearch =new ReservationSearch();	
		Account CreateTA = new Account();
		ReservationHomePage homePage = new ReservationHomePage();
		Tapechart tapeChart = new Tapechart();
		Admin admin = new Admin();
		Reports reports = new Reports();
		NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		RoomClass roomClass = new RoomClass();
		Rate rate = new Rate();
		Groups group = new Groups();
		AdvGroups advgroup = new AdvGroups();
		
		ArrayList<String> ratePlanNameList = new ArrayList<>();
		ArrayList<String> roomClassNameList = new ArrayList<>();
		ArrayList<String> roomClassAbbList = new ArrayList<>();
		for(String s : ratePlanName.split(Utility.DELIM)) {
			ratePlanNameList.add(s);			
		} 
		for(String s : roomClassName.split(Utility.DELIM)) {
			roomClassNameList.add(s);			
		} 
		for(String s : roomClassAbbreviation.split(Utility.DELIM)) {
			roomClassAbbList.add(s);			
		} 
		
		String randomString = Utility.GenerateRandomString();
		String timeZone = "US/Eastren";
		String defaultDateFormat = "dd/MM/yyyy";
		String checkInDate=Utility.getCurrentDate(defaultDateFormat, timeZone);
		String checkOutDate=Utility.getNextDate(2, defaultDateFormat);
		app_logs.info("checkInDate : "  + checkInDate);
		app_logs.info("checkOutDate : " + checkOutDate);
		int nights = Utility.getNumberofDays(checkInDate, checkOutDate);
		app_logs.info("getNights :  "  +nights);
		
		String salutation = "Mr."; 
		String guestFirstName = "VerifyRes" + randomString; 
		String guestLastName = "Realization" + randomString;
		
		String guestName = guestFirstName + " " + guestLastName;
		String phoneNumber = "8790321567";
		String alternativePhone = "8790321567";
		String email = "innroadautomation@innroad.com"; 
		String accountType = "Corporate/Member Accounts"; 
		String account = "CorporateAccount" + Utility.generateRandomString(); 
		String accountNumber = Utility.GenerateRandomString15Digit();
		String address1 = "test1"; 
		String address2 = "test2";
		String address3 = "test3"; 
		String city = "New york";
		String paymentType = "MC"; 
		if(caseType.equalsIgnoreCase("Account")) {
			paymentType = "Account (Corp/Member)";
		}
		
		
		String cardNumber = "5454545454545454"; 
		String nameOnCard = guestName; 
		String cardExpDate = "12/23";
		String marketSegment = "GDS"; 
		String referral = "Other";
		String postalCode = "12345"; 
		String isGuesProfile = "No";
		String source = "innCenter";
		String roomNumber = "";
		String maxAdults = "1";
		String maxChildren = "0"; 
		String country = "United States"; 
		String state = "Alaska";
		String tripSummaryRoomCharges = "";
		String tripSummaryTaxandServices = "";
		
		String isDepositOverride = "Yes";
		String depositOverrideAmount = "0.00";		
		Double depositAmount=0.0;
		Double paidDeposit=0.0;
		int numberOfReservation = 2;
		String pendingState = "transactionstatus-icon1";
		String postedState = "transactionstatus-icon2";
		String postedStateForPayment = "transactionstatus-icon8";
		String roomCharge = "Room Charge";
		String guestFolio = "Guest Folio";
		String customFolio = "Custom Folio";
		String capture = "Capture";
		String amountToPay = "100";
		
		String reservationNumber = "";
		String reservationStatus = "";
		
		ArrayList<String> reservationNumberList = new ArrayList<>();
		ArrayList<String> reservationStatusList = new ArrayList<>();
		ArrayList<String> roomNumberList = new ArrayList<>();
		ArrayList<String> tripSummaryRoomChargesList = new ArrayList<>();
		ArrayList<String> tripSummaryTaxandServicesList = new ArrayList<>();
		ArrayList<String> accountList = new ArrayList<>();
		ArrayList<String> accountNumberList = new ArrayList<>();
		
		String allReservation = "";
		String dateToLock = priodLockDate;		
		String currencyLabel = "GBP ( £ )";
		String standardDateFormat = "International";
		String weekDay = "Monday";
		String currentDate = Utility.getCurrentDate("dd/MM/yyyy", timeZone);

		String rateName = "GroupRate" + randomString;
		String baseAmount = "100";
		String addtionalAdult = "0"; String additionalChild = "0"; 
		String displayName = rateName; String associateSession = "All Year Season";
		String ratePolicy = "GroupRatesPolicy"; String rateDescription = rateName;
		String groupRoomClassAbb = "GRS"; String groupRoomClass = "GroupBlock" + randomString;
		String bedsCount = "1"; 
		String groupMaxAdults = "4"; String maxPersons = "4"; String roomQuantity = "1"; String startRoomNumber = "1";
		String ratePlan = "Test Rate"; String rateType = "Rooms Only"; String rateAttributes = "Based On Rate"; String interval = "1"; String adults = "1";
		String child = "0"; String groupReferral = "Walk In"; 
		String groupFirstName = "Bluebook" + randomString; String groupLastName = "Group" + randomString;
		String blockName = "BlueBook" + randomString; String roomPerNight = "1"; 
		String firstName = groupFirstName; String lastName = groupLastName;
		String updatedBlockedCount = "0"; String roomBlockCount = "1";
		String blueBookClass = "book";
		String accountName = groupFirstName + groupLastName;
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			//loginRateV2(driver);
			login_CP(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	if(caseType.equalsIgnoreCase("NightAuditUIValidation")) {

		try {
		 	
	 		testSteps.add("===== " + "Verify Night Audit UI".toUpperCase() + " =====");
		 	app_logs.info("===== " + "Verify Night Audit UI".toUpperCase() + " =====");
			
		 	getTestSteps.clear();
			getTestSteps = navigation.NightAuditIcon(driver);
			testSteps.addAll(getTestSteps);

			nightAudit.verifyAuditDateLabel(driver, testSteps);
			nightAudit.verifyCalenderIcon(driver, testSteps);
			nightAudit.verifyGoButton(driver, testSteps);
			nightAudit.verifyPeriodIsOpenButton(driver, testSteps);
			nightAudit.verifyDailyTransactionButton(driver, testSteps);
			
			nightAudit.verifyHouseKeepingButton(driver, testSteps);
			nightAudit.verifyHouseKeepingSetNowButton(driver, testSteps);
			nightAudit.verifyLongStayButton(driver, testSteps);
			nightAudit.verifyLongStaySetNowButton(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update currency symbol", testName, "UpdateCurrencyLabel",  driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to update currency symbol", testName, "UpdateCurrencyLabel",  driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
	}
		
	if(caseType.equalsIgnoreCase("VerifyCurrencyLabelForNightAuditTab")
			|| caseType.equalsIgnoreCase("VerifyDateFormatForNightAuditTab")
			|| caseType.equalsIgnoreCase("VerifyStartDayOfWeekDayForNightAuditTab")) {

		try {
		 	
			if(caseType.equalsIgnoreCase("VerifyCurrencyLabelForNightAuditTab")) {
				testSteps.add("===== " + "Update Currency label to "+ currencyLabel.toUpperCase() + " =====");
			 	app_logs.info("===== " + "Update Currency label to "+ currencyLabel.toUpperCase() + " =====");
	
		 	}else if(caseType.equalsIgnoreCase("VerifyDateFormatForNightAuditTab")) {
		 		testSteps.add("===== " + "Update Date Format to "+ standardDateFormat.toUpperCase() + " =====");
			 	app_logs.info("===== " + "Update Date Format to "+ standardDateFormat.toUpperCase() + " =====");
		 	}else if(caseType.equalsIgnoreCase("VerifyStartDayOfWeekDayForNightAuditTab")) {
		 		testSteps.add("===== " + "Update Day to "+ weekDay.toUpperCase() + " =====");
			 	app_logs.info("===== " + "Update Day to "+ weekDay.toUpperCase() + " =====");
		 		
		 	}
		 	
			
		 	navigation.admin(driver);
		 	app_logs.info("Navigate to admin");
		 	testSteps.add("Navigate to admin");
		 	
		 	navigation.Clientinfo(driver);
		 	app_logs.info("Clicked client info");
		 	testSteps.add("Clicked client info");
		 	
		 	admin.clickClientName(driver, testSteps);		 			 	
		 	admin.clickClientOptions(driver, testSteps);
		 	
		 	if(caseType.equalsIgnoreCase("VerifyCurrencyLabelForNightAuditTab")) {
			 	admin.selectCurrencyNameAndSign(driver, testSteps, currencyLabel);		 		
		 	}else if(caseType.equalsIgnoreCase("VerifyDateFormatForNightAuditTab")) {
			 	admin.selectDateFormat(driver, testSteps, standardDateFormat);		 		
		 	}else if(caseType.equalsIgnoreCase("VerifyStartDayOfWeekDayForNightAuditTab")) {
		 		admin.selectStartDayOfWeek(driver, testSteps, weekDay);
		 	}
		 	
		 	admin.clickSaveClientDetails(driver, testSteps);
		 	
		 	Utility.logoutToInnCenter(driver, testSteps);
		 	testSteps.add("Logged out");
			app_logs.info("Logged out");
			
		 	login_CP(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");


		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update currency symbol", testName, "UpdateCurrencyLabel",  driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to update currency symbol", testName, "UpdateCurrencyLabel",  driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		if(caseType.equalsIgnoreCase("VerifyCurrencyLabelForNightAuditTab")) {
	
			try {
				testSteps.add("===== CREATE RESERVATION WITH CURRENT DATE =====");
				app_logs.info("===== CREATE RESERVATION WITH CURRENT DATE =====");
				
				reservationPage.click_NewReservation(driver, testSteps);
				String checkIn = "";
				String checkOut = "";
	
				if(caseType.equalsIgnoreCase("VerifyCurrencyLabelForNightAuditTab")) {
						checkIn = Utility.getCurrentDate("MM/dd/yyyy", timeZone);
						checkOut = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
				}else if(caseType.equalsIgnoreCase("VerifyDateFormatForNightAuditTab")) {
					checkIn = Utility.getCurrentDate("dd/MM/yyyy", timeZone);
					checkOut = Utility.getNextDate(1, "dd/MM/yyyy", timeZone);
				
				}
				getTestSteps.clear();
				getTestSteps = reservationPage.checkInDate(driver, checkIn);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver,  checkOut);
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_Adults(driver, testSteps, maxAdults);
				reservationPage.enter_Children(driver, testSteps, maxChildren);
				reservationPage.select_Rateplan(driver, testSteps, ratePlanNameList.get(0),"");
				reservationPage.clickOnFindRooms(driver, testSteps);
				String isAssign = "Yes";
				
				reservationPage.select_Room(driver, testSteps, roomClassNameList.get(0), isAssign, "");
				reservationPage.clickNext(driver, testSteps);									
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);
	
				reservationPage.SelectReferral(driver, referral);
				
				reservationPage.clickBookNow(driver, testSteps);
	
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberList.add(reservationNumber);
				
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				reservationStatusList.add(reservationStatus);
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");					
					
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if(caseType.equalsIgnoreCase("VerifyStartDayOfWeekDayForNightAuditTab")) {
			
			try {
	
				getTestSteps.clear();
				getTestSteps = navigation.NightAuditIcon(driver);
				testSteps.addAll(getTestSteps);
	
				nightAudit.verifyWeekStartDay(driver, weekDay, testSteps);
				statusCode.add("1");				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify week start day in night audit", testName, "VerifyWeekStartDay", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify week start day in night audit", testName, "VerifyWeekStartDay", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
		}
		
		if(caseType.equalsIgnoreCase("VerifyDateFormatForNightAuditTab")) {
			try {

				app_logs.info("===== Verifying that date fomrat is (" + standardDateFormat +") in night audit line items =====".toUpperCase());
				testSteps.add("===== Verifying that date fomrat is (" + standardDateFormat +") in night audit line items =====".toUpperCase());
			
				getTestSteps.clear();
				getTestSteps = navigation.NightAuditIcon(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = nightAudit.EnterAuditDate(driver, currentDate);
				testSteps.addAll(getTestSteps);
	
				nightAudit.GoButtonClick(driver, testSteps);				
				nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
				nightAudit.clickPriorItems(driver, testSteps);
				
				String getFirstItemDate = nightAudit.getPriorItemsDatesBasedOnIndex(driver, testSteps, 1);
				boolean isDateInFormat = Utility.validateDateIsInRequiredFormat(getFirstItemDate,  "d MMM, yyyy");
				assertTrue(isDateInFormat, "Failed : Date is not  in formate");
				app_logs.info("Verified date is in  internation format (d MMM, yyyy)");
				testSteps.add("Verified date is in  internation format (d MMM, yyyy)");					
				
				statusCode.add(0, "1");	
			 
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to open reservation from night audit", testName, "VerifyCurrencyLabel", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to open reservation from night audit", testName, "VerifyCurrencyLabel", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
			if(caseType.equalsIgnoreCase("VerifyCurrencyLabelForNightAuditTab")) {
					
				try {
					app_logs.info("===== Verifying that currency label (" + currencyLabel +") exist in reservation =====".toUpperCase());
					testSteps.add("===== Verifying that currency label (" + currencyLabel +") exist in reservation =====".toUpperCase());

					getTestSteps.clear();
					getTestSteps = navigation.NightAuditIcon(driver);
					testSteps.addAll(getTestSteps);
		
					getTestSteps.clear();
					getTestSteps = nightAudit.EnterAuditDate(driver, currentDate);
					testSteps.addAll(getTestSteps);
		
					nightAudit.GoButtonClick(driver, testSteps);				
					nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
		
					nightAudit.clickReservation(driver, testSteps, reservationNumber);
					
					nightAudit.verifyCurrencyLabelInReservation(driver, currencyLabel, testSteps);
				
					nightAudit.clickPostedStatus(driver, testSteps);
		
					getTestSteps.clear();
					getTestSteps = nightAudit.EnterAuditDate(driver, currentDate);
					testSteps.addAll(getTestSteps);
		
					nightAudit.GoButtonClick(driver, testSteps);				
					nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
					
					nightAudit.clickPrintIcon(driver, testSteps);			
					reports.verifyNightAuditReport(driver, currencyLabel);
					statusCode.add(1, "1");				
					
				}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify currency label in new reservation", testName, "VerifyCurrencyLabel", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify currency label in new reservation", testName, "VerifyCurrencyLabel", driver);
					} else {
						Assert.assertTrue(false);
					}
				}	
			}
	}
	
	if((caseType.equalsIgnoreCase("VerifyAddLineItemChangeRoomClassBeyondLockedDate"))
			|| caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
		HashMap<String, String> reservationNumberMap = new HashMap<>();

		if(dateToLock.isEmpty() || dateToLock.equalsIgnoreCase("")) {
	 		dateToLock = ESTTimeZone.reformatDate(Utility.getDatePast_FutureDate(-60), "MM/dd/yyyy", "dd/MM/yyyy");
	 		//dateToLock = "03/01/2020";
	 		//dateToLock = "07/12/2020";
	 		app_logs.info("date to lock : " + dateToLock);
	 	}
		
	
		//Case : 727071
		String isAssign = "Yes";
		String tempRatePlan = ratePlanNameList.get(0) + "|" + ratePlanNameList.get(0);
		String tempCheckin = ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone) + "|" + ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone); 
		String tempCheckOut = ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy") + "|" + ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy");
		String tempMaxAdults = maxAdults + "|" + maxAdults;
		String tempMaxChildren = maxChildren + "|" + maxChildren;
		String tempRoomClassName = roomClassNameList.get(0) + "|" + roomClassNameList.get(0);
		String tempFirstName = guestFirstName + "|" + guestFirstName + randomString;
		String tempLastName = guestLastName + "|" + guestLastName + randomString;
		String tempSalutation = salutation + "|" + salutation;
		String isSplitRes = "No";
		String tempPhoneNumber = phoneNumber + "|" + phoneNumber;
	
		try {
			testSteps.add("<b> ===== CREATING 'MRB' RESERVATION ===== </b>");
			app_logs.info("<b> ===== CREATING 'MRB' RESERVATION ===== </b>");
		
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.selectDates(driver, testSteps, tempCheckin, tempCheckOut, tempMaxAdults, tempMaxChildren, tempRatePlan, "",isSplitRes);

				if(isSplitRes.equalsIgnoreCase("Yes")) {
					getTestSteps.clear();
					reservationPage.enter_Adults(driver, getTestSteps, maxAdults);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					reservationPage.enter_Children(driver, getTestSteps, maxChildren);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					reservationPage.select_Rateplan(driver, getTestSteps, ratePlanNameList.get(0), "");
					testSteps.addAll(getTestSteps);
					
				}
				
				reservationPage.clickOnFindRooms(driver, testSteps);
				reservationPage.select_MRBRooms(driver, testSteps, tempRoomClassName, "Yes","");
				try {
					reservationPage.clickNext(driver, testSteps);
					
				}catch(Exception e) {
					reservationPage.clickSelectRoom(driver, tempRoomClassName.split("\\|")[tempRoomClassName.split("\\|").length-1], testSteps);
					reservationPage.clickNext(driver, testSteps);
									
				}
				
				getTestSteps.clear();
				reservationPage.enter_MRB_MailingAddress(driver, testSteps, tempSalutation, tempFirstName, tempLastName, tempPhoneNumber, alternativePhone, email, "", accountType, address1, address2, address3, city, country, state, postalCode, isGuesProfile, "", isSplitRes,getTestSteps);
				
				reservationPage.SelectReferral(driver, referral);

				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.click_Quote(driver, testSteps);					
				}else {
					reservationPage.clickBookNow(driver, testSteps);
				}
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberMap.put("mrb",reservationNumber);
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
				}
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");					
					
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			

			try {

				testSteps.add("<b> ===== CREATING 'SPLIT' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'SPLIT' RESERVATION ===== </b>");
			
				reservationPage.click_NewReservation(driver, testSteps);
				isSplitRes = "Yes";
				tempCheckin = ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -2, timeZone) + "|" + ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone); 
				tempCheckOut = ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone) + "|" + ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy");
				tempFirstName = guestFirstName;
				tempLastName = guestLastName;
				tempSalutation = salutation;
				tempPhoneNumber = phoneNumber;
				tempRoomClassName = roomClassNameList.get(0) + "|" + roomClassNameList.get(0);

				reservationPage.selectDates(driver, testSteps, tempCheckin, tempCheckOut, tempMaxAdults, tempMaxChildren, tempRatePlan, "",isSplitRes);
				if(isSplitRes.equalsIgnoreCase("Yes")) {
					getTestSteps.clear();
					reservationPage.enter_Adults(driver, getTestSteps, maxAdults);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					reservationPage.enter_Children(driver, getTestSteps, maxChildren);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					reservationPage.select_Rateplan(driver, getTestSteps, ratePlanNameList.get(0), "");
					testSteps.addAll(getTestSteps);
					
				}
				
				reservationPage.clickOnFindRooms(driver, testSteps);
				for(int i=0; i < tempCheckin.split("\\|").length; i++) {
					reservationPage.selectSplitRoom(driver, tempRoomClassName.split("\\|")[i], i, 1);						
				}
				try {
					reservationPage.clickNext(driver, testSteps);
					
				}catch(Exception e) {
					reservationPage.clickSelectRoom(driver, tempRoomClassName.split("\\|")[tempRoomClassName.split("\\|").length-1], testSteps);
					reservationPage.clickNext(driver, testSteps);
									
				}
				
				getTestSteps.clear();
				reservationPage.enter_MRB_MailingAddress(driver, testSteps, tempSalutation, tempFirstName, tempLastName, tempPhoneNumber, alternativePhone, email, "", accountType, address1, address2, address3, city, country, state, postalCode, isGuesProfile, "", isSplitRes,getTestSteps);
				
				reservationPage.SelectReferral(driver, referral);

				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.click_Quote(driver, testSteps);					
				}else {
					reservationPage.clickBookNow(driver, testSteps);
				}
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberMap.put("split",reservationNumber);
				
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
				}
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");					
					
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify Split reservation cannot be created for past date", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify Split reservation cannot be created for past date", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		try {

				testSteps.add("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
			
				testSteps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
	
				navigation.Accounts(driver);
				testSteps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");
				
				accountList.add(account);
				accountNumberList.add(accountNumber);
				
				CreateTA.ClickNewAccountbutton(driver, accountType);
				CreateTA.AccountDetails(driver, accountType, account);
				CreateTA.ChangeAccountNumber(driver, accountNumber);
				getTestSteps.clear();
				getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
						address1, address2, address3, email, city, state, postalCode, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.AccountSave(driver, test, account, getTestSteps);
				testSteps.addAll(getTestSteps);
				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify new account reservation for locked date cannot be created", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify new account reservation for locked date cannot be created", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	//	}
	
		try {
				CreateTA.NewReservationButton(driver, test);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
				
			try {
				
				getTestSteps.clear();
				getTestSteps = reservationPage.checkInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone));
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver,  ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"));
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_Adults(driver, testSteps, maxAdults);
				reservationPage.enter_Children(driver, testSteps, maxChildren);
				reservationPage.select_Rateplan(driver, testSteps, ratePlanNameList.get(0),"");
				reservationPage.clickOnFindRooms(driver, testSteps);
				isAssign = "Yes";
				
				reservationPage.select_Room(driver, testSteps, roomClassNameList.get(0), isAssign, "");
				reservationPage.clickNext(driver, testSteps);									
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
	
				reservationPage.SelectReferral(driver, referral);
				
				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.click_Quote(driver, testSteps);					
				}else {
					reservationPage.clickBookNow(driver, testSteps);
				}
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberMap.put("account",reservationNumber);
				
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
				}
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				
				roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");
				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
			
				reservationPage.click_NewReservation(driver, testSteps);									
				
				getTestSteps.clear();
				getTestSteps = reservationPage.checkInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone));
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver,  ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"));
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_Adults(driver, testSteps, maxAdults);
				reservationPage.enter_Children(driver, testSteps, maxChildren);
				reservationPage.select_Rateplan(driver, testSteps, ratePlanNameList.get(0),"");
				reservationPage.clickOnFindRooms(driver, testSteps);
				isAssign = "Yes";
				
				reservationPage.select_Room(driver, testSteps, roomClassNameList.get(0), isAssign, "");
				reservationPage.clickNext(driver, testSteps);									
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
	
				reservationPage.SelectReferral(driver, referral);
				
				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.click_Quote(driver, testSteps);					
				}else {
					reservationPage.clickBookNow(driver, testSteps);
				}
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberMap.put("single",reservationNumber);
				
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
				}
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				
				roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");
				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			try {

				testSteps.add("<b> ===== CREATING 'GROUP' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'GROUP' RESERVATION ===== </b>");
			
				navigation.mainSetupManu(driver);
				app_logs.info("Navigate Setup");
				testSteps.add("Navigate Setup");

				navigation.RoomClass(driver);
				app_logs.info("Navigate Room Class");
				testSteps.add("Navigate Room Class");
				app_logs.info("==========CREATE A NEW ROOM CLASS==========");
				testSteps.add("==========CREATE A NEW ROOM CLASS==========");
					
					try {
						newRoomClass.createRoomClassV2(driver, testSteps, groupRoomClass, groupRoomClassAbb, groupMaxAdults, maxPersons, roomQuantity, startRoomNumber);
						getTestSteps.clear();
						getTestSteps = newRoomClass.closeRoomClassTabV2(driver, groupRoomClass);
						testSteps.addAll(getTestSteps);
					}
					catch(Exception e) {				
							
						navigation.clickOnNewRoomClass(driver);
						
						getTestSteps.clear();
						getTestSteps = roomClass.createRoomClass(driver, groupRoomClass, groupRoomClassAbb, 
								bedsCount, maxAdults,
								maxPersons, roomQuantity, test, getTestSteps);
						testSteps.addAll(getTestSteps);

						roomClass.closeRoomClassTab(driver);
						testSteps.add("Close created room class tab");
					}
								
					testSteps.add("Sccessfully Created New RoomClass " + groupRoomClass + " Abb : " + groupRoomClassAbb);
					app_logs.info("Sccessfully Created New RoomClass" + groupRoomClass + " Abb : " + groupRoomClassAbb);
				
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			// Create New Rate and Attach RoomClass

			try {
				testSteps.add("==========CREATE A NEW RATE==========");
				app_logs.info("==========CREATE A NEW RATE==========");
				
				
				navigation.Inventory(driver, testSteps);
				app_logs.info("Navigate Inventory");
				testSteps.add("Navigate Inventory");

				try {
					getTestSteps.clear();
					getTestSteps = navigation.secondaryRatesMenuItem(driver);
					testSteps.addAll(getTestSteps);
					
				}catch(Exception e) {
					getTestSteps.clear();
					getTestSteps = navigation.secondaryRatesMenuItem(driver, 3);
					testSteps.addAll(getTestSteps);

				}
				
				
				getTestSteps.clear();
				getTestSteps = rate.ClickNewRate(driver);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = rate.EnterRateName(driver, rateName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.SelectRatePlan(driver, ratePlan);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = rate.EnterMaxAdults(driver, maxAdults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.EnterMaxPersons(driver, maxPersons);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.EnterBaseAmount(driver, baseAmount);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.EnterAdditionalAdult(driver, addtionalAdult);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.EnterAdditionalChild(driver, additionalChild);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.EnterRateDisplayName(driver, rateName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.EnterRatePolicy(driver, rateName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.EnterRateDescription(driver, rateName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.AssociateSeason(driver, associateSession);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.AssociateRoomClass(driver, groupRoomClass);
				testSteps.addAll(getTestSteps);				
		
				getTestSteps.clear();
				getTestSteps = rate.AssociateSource(driver, source);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = rate.Save_DoneRate(driver);
				testSteps.addAll(getTestSteps);

				rate.SearchRate(driver, rateName, false);
				testSteps.add("New Rate '" + rateName + "' Created & Verified ");
				app_logs.info("New Rate '" + rateName + "' Created & Verified");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Clicking on Groups
			try {
				navigation.navigateToReservations(driver);
				testSteps.add("Navigate Reservations");
				app_logs.info("Navigate Reservations");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate back to reservation", testName, "NavigateAdvGroup", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate back to reservation", testName, "NavigateAdvGroup", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

				// Click New Account and Enter Account Name
				try {
					app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
					testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");
	
					navigation.secondaryGroupsManu(driver);
					testSteps.add("Navigate Groups");
					app_logs.info(" Navigate Groups");
	
					getTestSteps.clear();
					getTestSteps = group.enterrGroupName(driver, accountName);
					testSteps.addAll(getTestSteps);
	
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to click new account and enter account name", testName,
								"EnterAccountName", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				// Enter Account Number
				try {
					accountNumber = Utility.GenerateRandomString15Digit();
					getTestSteps.clear();
					getTestSteps = group.enterAccountNo(driver, accountNumber);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				// Enter Account Attributes
				try {
					getTestSteps.clear();
					getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				// Enter Account Mailing Address
				try {
					getTestSteps.clear();
					getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, phoneNumber, address1,
							city, state, country, postalCode);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
								"EnterAccountMailingAddress", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
								"EnterAccountMailingAddress", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				// Check Mailing Info CheckBox
				try {
					getTestSteps.clear();
					getTestSteps = group.Billinginfo(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				// Save Account
				try {
					getTestSteps.clear();
					getTestSteps = group.clickOnSave(driver);
					testSteps.addAll(getTestSteps);
	
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				// Navigate to Reservation and check count
				try {
					app_logs.info("==========GET RESERVATION COUNT==========");
					testSteps.add("==========GET RESERVATION COUNT==========");
					getTestSteps.clear();
					group.clickOnGroupsReservationTab(driver);
					testSteps.addAll(getTestSteps);
	
					String initialResCount = group.getReservationCount(driver);
					testSteps.add("Initial Reservation Count : " + initialResCount);
					Utility.app_logs.info("Initial Reservation Count : " + initialResCount);
	
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
	
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				try {
					app_logs.info("==========CREATE NEW BLOCK==========");
					testSteps.add("==========CREATE NEW BLOCK==========");
	
					getTestSteps.clear();
					getTestSteps = group.navigateRoomBlock(driver);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.ClickNewBlock(driver);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.EnterBlockName(driver, blockName);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.ClickOkay_CreateNewBlock(driver);
					testSteps.addAll(getTestSteps);
	
					app_logs.info("==========SEARCH ROOMS==========");
					testSteps.add("==========SEARCH ROOMS==========");

					getTestSteps.clear();
					getTestSteps = group.selectArrivalDepartureDates(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "dd/MM/yyyy", -1, timeZone),
							dateToLock);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.SelectRatePlan(driver, ratePlan);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.SelectAdults(driver, adults);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.SelectChilds(driver, child);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.EnterNights(driver, roomPerNight);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = group.ClickSearchGroup(driver);
					testSteps.addAll(getTestSteps);
	
					advgroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
					advgroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, groupRoomClass);
	
					advgroup.ClickCreateBlock(driver, getTestSteps);
	
					app_logs.info("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
					testSteps.add("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
					String RoomBlocked = group.getRoomBlockedInRoomBlockDetatil(driver);
					Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
					testSteps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
					assertEquals(RoomBlocked, roomPerNight, "Failed Room Blocked Not Matched");
	
					String totalRoomNight = group.getTotalRoomNightsInRoomBlockDetail(driver);
					Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
					testSteps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
					assertEquals(totalRoomNight, roomPerNight, "Failed Room Blocked Not Matched");
	
					String expectedRevenueDetail = group.getExpectedRevenueInRoomBlockDetail(driver);
					Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
					testSteps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);
	
					String expectedRevenueInfo = group.getExpectedRevenueInGroupInfo(driver);
					Utility.app_logs.info("Group Info Expected Revenue  : " + expectedRevenueInfo);
					testSteps.add("Group Info ExpectedRevenue  : " + expectedRevenueInfo);
					assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");
	
					String expectedRevenue = expectedRevenueInfo;
					String pickUpPercentage = group.getPickUpPercentageInRoomBlockDetatil(driver);
					Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
					testSteps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
					assertEquals(pickUpPercentage, "0", "Failed Pickup Percentage missmatched");
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
	
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				String beforeAvailableRoom = null;
				String beforePickupValue = null;
				try {
					String beforeBookIconClass = null;
					app_logs.info("==========ROOMING LIST DETAILS==========");
					testSteps.add("==========ROOMING LIST DETAILS==========");
					Utility.app_logs.info("Book Room From RoomClass : " + groupRoomClass);
					testSteps.add("Book Room From RoomClass : " + groupRoomClass);
	
					beforePickupValue = group.getPickUpValue(driver, groupRoomClass);
					Utility.app_logs.info("Pickup Value : " + beforePickupValue);
					testSteps.add("Pickup Value : " + beforePickupValue);
	
					beforeAvailableRoom = group.getAvailableRooms(driver, groupRoomClass);
					Utility.app_logs.info("Available Rooms : " + beforeAvailableRoom);
					testSteps.add("Available Rooms : " + beforeAvailableRoom);
	
					beforeBookIconClass = group.getBookIconClass(driver, groupRoomClass);
					Utility.app_logs.info("BookIcon Class : " + beforeBookIconClass);
					testSteps.add("BookIcon Class : " + beforeBookIconClass);
					assertEquals(beforeBookIconClass, blueBookClass, "Failed: Blue book icon is not showing");
	
					String blockedCount = group.getBlocked(driver, groupRoomClass);
					Utility.app_logs.info("Blocked Count  : " + blockedCount);
					testSteps.add("Blocked Count  : " + blockedCount);
					assertEquals(blockedCount, roomBlockCount, "Failed Room Blocked Not Matched");
	
					group.bookIconClick(driver, groupRoomClass);
					Utility.app_logs.info("Click <b>Blue Book Icon</b> of Room Class  : " + groupRoomClass);
					testSteps.add("Click <b>Blue Book Icon</b> of Room Class  : " + groupRoomClass);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
	
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	
				// Create Reservation
				try {
					app_logs.info("==========ENTER RESERVATION DETAILS CREATED FROM <b>BLUE BOOK ICON</b>==========");
					testSteps.add("==========ENTER RESERVATION DETAILS CREATED FROM <b>BLUE BOOK ICON</b>==========");
	
					String roomnumber = reservationPage.getRoomNumber(driver, getTestSteps);
					Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
					testSteps.add("Verified Room Number is Unassigned");
	
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyAssociatedAccount_ResHeader(driver, accountName);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					getTestSteps = reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean("flase"));
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = reservationPage.enter_GuestName(driver, getTestSteps, salutation, firstName, lastName);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = reservationPage.selectReferral(driver, groupReferral);
					testSteps.addAll(getTestSteps);

					if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
						reservationPage.click_Quote(driver, testSteps);					
					}else {
						reservationPage.clickBookNow(driver, testSteps);
					}
					
					reservationNumber = "";
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);
					reservationNumberMap.put("group",reservationNumber);
					
					reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
					
					if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
						reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
					}		
					reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
	
					testSteps.add("Successfully Associated Account to  Reservation");
					app_logs.info("Successfully Associated Account to Reservation");
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyAssociatedAccount(driver, accountName);
					testSteps.addAll(getTestSteps);
					
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened tab");
					app_logs.info("Closed opened tab");
					
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

		
		ArrayList<String> tapeChartClasses = new ArrayList<>();
		ArrayList<String> tapeChartClassesAbb = new ArrayList<>();
		tapeChartClasses.add("TapeChart1-" + randomString);
		tapeChartClasses.add("TapeChart2-" + randomString);
		tapeChartClassesAbb.add("TC1-" + randomString);
		tapeChartClassesAbb.add("TC2-" + randomString);
		String tapeChartRateName = "TapeChart" + randomString;
		
		String reservationFullName = guestFirstName + " " + guestLastName;
	
		if(!caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {	
			
			try {

				testSteps.add("<b> ===== CREATING 'TAPECHART' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'TAPECHART' RESERVATION ===== </b>");

				navigation.mainSetupManu(driver);
				app_logs.info("Navigate Setup");
				testSteps.add("Navigate Setup");
		
				navigation.RoomClass(driver);
				app_logs.info("Navigate Room Class");
				testSteps.add("Navigate Room Class");
		
				for(int i=0; i < tapeChartClasses.size(); i++) {
					app_logs.info("==========CREATE A NEW ROOM CLASS '"+ tapeChartClasses.get(i) +"' ==========");
					testSteps.add("==========CREATE A NEW ROOM CLASS '"+ tapeChartClasses.get(i) +"' ==========");
		
					try {
						newRoomClass.createRoomClassV2(driver, testSteps, tapeChartClasses.get(i), tapeChartClassesAbb.get(i), groupMaxAdults, maxPersons, roomQuantity, startRoomNumber);
						getTestSteps.clear();
						getTestSteps = newRoomClass.closeRoomClassTabV2(driver, tapeChartClasses.get(i));
						testSteps.addAll(getTestSteps);
					}
					catch(Exception e) {				
							
						navigation.clickOnNewRoomClass(driver);
						
						getTestSteps.clear();
						getTestSteps = roomClass.createRoomClass(driver, tapeChartClasses.get(i),  tapeChartClassesAbb.get(i), 
								bedsCount, maxAdults,
								maxPersons, roomQuantity, test, getTestSteps);
						testSteps.addAll(getTestSteps);
		
						roomClass.closeRoomClassTab(driver);
						testSteps.add("Close created room class tab");
					}
								
					testSteps.add("Sccessfully Created New RoomClass " + tapeChartClasses.get(i) + " Abb : " +  tapeChartClassesAbb.get(i));
					app_logs.info("Sccessfully Created New RoomClass" + tapeChartClasses.get(i) + " Abb : " +  tapeChartClassesAbb.get(i));
				}
		
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			// Create New Rate and Attach RoomClass
		
			try {
				testSteps.add("==========CREATE A NEW RATE==========");
				app_logs.info("==========CREATE A NEW RATE==========");
				
				
				navigation.Inventory(driver, testSteps);
				app_logs.info("Navigate Inventory");
				testSteps.add("Navigate Inventory");
		
				try {
					getTestSteps.clear();
					getTestSteps = navigation.secondaryRatesMenuItem(driver);
					testSteps.addAll(getTestSteps);
										
				}catch(Exception e) {
					getTestSteps.clear();
					getTestSteps = navigation.secondaryRatesMenuItem(driver, 3);
					testSteps.addAll(getTestSteps);
					
				}
				getTestSteps.clear();
				getTestSteps = rate.ClickNewRate(driver);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = rate.EnterRateName(driver, tapeChartRateName);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.SelectRatePlan(driver, ratePlan);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = rate.EnterMaxAdults(driver, maxAdults);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.EnterMaxPersons(driver, maxPersons);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.EnterBaseAmount(driver, baseAmount);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.EnterAdditionalAdult(driver, addtionalAdult);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.EnterAdditionalChild(driver, additionalChild);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.EnterRateDisplayName(driver, tapeChartRateName);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.EnterRatePolicy(driver, tapeChartRateName);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.EnterRateDescription(driver, tapeChartRateName);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.AssociateSeason(driver, associateSession);
				testSteps.addAll(getTestSteps);
		
				for(int i=0; i < tapeChartClasses.size(); i++) {
					getTestSteps.clear();
					getTestSteps = rate.AssociateRoomClass(driver, tapeChartClasses.get(i));
					testSteps.addAll(getTestSteps);	
				}
		
				getTestSteps.clear();
				getTestSteps = rate.AssociateSource(driver, source);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = rate.Save_DoneRate(driver);
				testSteps.addAll(getTestSteps);
		
				rate.SearchRate(driver, tapeChartRateName, false);
				testSteps.add("New Rate '" + tapeChartRateName + "' Created & Verified ");
				app_logs.info("New Rate '" + tapeChartRateName + "' Created & Verified");
		
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		
			// Clicking on Groups
			try {
				navigation.navigateToReservations(driver);
				testSteps.add("Navigate Reservations");
				app_logs.info("Navigate Reservations");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate back to reservation", testName, "NavigateAdvGroup", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate back to reservation", testName, "NavigateAdvGroup", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			try {

				navigation.TapeChart(driver);
				testSteps.add("Navigate to tape chart");
				app_logs.info("Navigate to tape chart");
				
				reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone), ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"), timeZone, testSteps);
				tapeChart.enterAdult(driver, maxAdults, testSteps);
				tapeChart.selectRatePlan(driver, ratePlan, testSteps);			
				tapeChart.clickSearchButton(driver, testSteps);
				
				app_logs.info("==========SELECT ROOM==========");
				testSteps.add("==========SELECT ROOM==========");
				tapeChart.clickAvailableSlot(driver, tapeChartClassesAbb.get(0));
				testSteps.add("Click available room of Room Class '" + tapeChartClassesAbb.get(0) + "'");
				app_logs.info("Click on available room of Room Class '" + tapeChartClassesAbb.get(0) + "'");
				Wait.wait10Second();
				testSteps.add("New Reservation page is opened");
				app_logs.info("New Reservation Page is Opened");
		
				String room = reservationPage.getRoomSelectedFromTapeChart(driver, testSteps);
				Assert.assertTrue(room.contains(tapeChartClasses.get(0)), "Failed: Room is not selected");
				testSteps.add("Verified Room Class is '" + tapeChartClasses.get(0) + "'");
			
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);
		
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);
		
				reservationPage.SelectReferral(driver, referral);
				
				reservationPage.clickBookNow(driver, testSteps);
		
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberMap.put("tapeChart",reservationNumber);
				
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				
				roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
		
				String selectedRoomClass = reservationPage.getRoomClassResDetail(driver);
				testSteps.add("Selected RoomClass : " + selectedRoomClass);
				app_logs.info("Selected RoomClass : " + selectedRoomClass);
			
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");					
		
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		
		}
		
	try {
		testSteps.add("===== " + "Locking period for specific date".toUpperCase() + " =====");
	 	app_logs.info("===== " + "Locking period for specific date".toUpperCase() + " =====");

	 	getTestSteps.clear();
		getTestSteps = navigation.NightAuditIcon(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
		testSteps.addAll(getTestSteps);

		nightAudit.GoButtonClick(driver, testSteps);				
		nightAudit.PeriodIsOpenButtonClick(driver, testSteps);

		testSteps.add("===== Verifying that date to lock period has pending line item less then 100 =====");
	 	app_logs.info("===== Verifying that date to lock period has pending line item less then 100 =====");
		int priorItemSize = nightAudit.getPriorItemSize(driver);
		if(priorItemSize > 1) {
					
			nightAudit.clickPriorItems(driver, testSteps);
			String getItems = nightAudit.getTotalPriorItems(driver, testSteps);
			app_logs.info("Prior items count for specific date: " + getItems);
			testSteps.add("Prior items count for specific date: " + getItems);
			if(Integer.parseInt(getItems) > 100) {
			    assertTrue(false, "Failed : Dates with more than 100 Items cannot be posted with automation. Please do it manually");						
		    }
			testSteps.add("===== Posting pending line item for specific date =====");
		 	app_logs.info("===== Posting pending line item for specific date =====");

			nightAudit.clickPostedStatus(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
			testSteps.addAll(getTestSteps);
		
			nightAudit.GoButtonClick(driver, testSteps);				
			nightAudit.PeriodIsOpenButtonClick(driver, testSteps);

		}
		

		try {
			nightAudit.postAllItems(driver, testSteps, "CurrentItems");
			
		}catch(Exception e) {
			app_logs.info("in catch");
		}
		try {
			
		nightAudit.postAllItems(driver, testSteps, "PriorItems");

		}catch(Exception e) {
			app_logs.info("in catch");
		}
		try {
			
		nightAudit.postAllItems(driver, testSteps, "VoidItems");

		}catch(Exception e) {
			app_logs.info("in catch");
		}	
		
		nightAudit.clickPostedStatus(driver, testSteps);

		getTestSteps.clear();
		getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
		testSteps.addAll(getTestSteps);

		nightAudit.GoButtonClick(driver, testSteps);				
		nightAudit.PeriodIsOpenButtonClick(driver, testSteps);	
	
		nightAudit.switchToLockPeriodPopup(driver, testSteps);
		nightAudit.periodLockHeadingDisplay(driver, testSteps, true);	    
	    nightAudit.clickLock(driver, testSteps);
	    	
	    navigation.Reservation_Backward(driver);				
	    app_logs.info("Back to reservations");
	    testSteps.add("Back to reservations");
	    
		Utility.refreshPage(driver);
		Wait.waitforPageLoad(30, driver);
	    
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to lock date", testName, "PeriodLock", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to lock date", testName, "PeriodLock", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
/*
	reservationNumberMap.put("mrb", "18421498");
	reservationNumberMap.put("split", "18421500");
	reservationNumberMap.put("account", "18421501");
	reservationNumberMap.put("single", "18421502");
	reservationNumberMap.put("group", "18421503");
	reservationNumberMap.put("tapeChart", "18421504");
*/
	if(caseType.equalsIgnoreCase("VerifyAddLineItemChangeRoomClassBeyondLockedDate")) {
	
	   try {
		   for (Map.Entry<String,String> entry : reservationNumberMap.entrySet())  {
				
				String tempMapKey =entry.getKey();
				app_logs.info(tempMapKey);
				if(!tempMapKey.equalsIgnoreCase("tapeChart")){
				   
					reservationSearch.basicSearchWithResNumber(driver, reservationNumberMap.get(tempMapKey), true);
					testSteps.add("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));
					app_logs.info("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));			
				
					app_logs.info("===== Verifying line items for reservations '" + tempMapKey +"' during locked period are locked =====");
					testSteps.add("===== Verifying line items for reservations '" + tempMapKey +"' during locked period are locked =====");				
					
					reservationPage.click_Folio(driver, testSteps);
					int totalLineItemSize = folio.getAllLineItemsSize(driver);
					int lockedItemSize = folio.getLockedItemsSize(driver);
					testSteps.add("Expected Locked items : " + totalLineItemSize);
					testSteps.add("Found : " + lockedItemSize);
					assertEquals(lockedItemSize, totalLineItemSize, "Failed all line items are not locked in reservation");
					app_logs.info("Verified that all line items are locked");
					testSteps.add("Verified that all line items are locked");				
	
					app_logs.info("===== Verifying line items for locked period date cannot be added =====");
					testSteps.add("===== Verifying line items for locked period date cannot be added =====");				
	
					folio.clickAddButton(driver);
					folio.addLineItem(driver, testSteps, ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"), "Bar", "100", "1");
					int toastMsgSize = folio.getToastMessageSize(driver);
					
					assertTrue(toastMsgSize > 0, "Failed : Error message didn't displayed");
					app_logs.info("Verifid error message 'Cannot add item - the selected date should be greater than the lock start date' is  displaying on adding line item after locking a specific period");
					testSteps.add("Verifid error message 'Cannot add item - the selected date should be greater than the lock start date' is  displaying on adding line item after locking a specific period");
	
					int lineItemSizeAfterAddingNewItem = folio.getAllLineItemsSize(driver);
					assertTrue(lineItemSizeAfterAddingNewItem == totalLineItemSize, "Failed : line item added");
					app_logs.info("Verifid line item cannot be added after locking a specific period");
					testSteps.add("Verifid line item cannot be added after locking a specific period");
	
					Wait.wait30Second();
					try {
						reservationPage.closeReservationTab(driver);
						testSteps.add("Close Reservtaion Tab");
						app_logs.info("Close Reservtaion Tab");
						
					}catch(Exception e) {
						reservationPage.closeReservationTab(driver);
						testSteps.add("Close Reservtaion Tab");
						app_logs.info("Close Reservtaion Tab");						
					}
				}
		   }
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to add line item beyond lock date", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to add line item beyond lock date", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		
		}
		
	}
	   try {
			if(caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
				for (Map.Entry<String,String> entry : reservationNumberMap.entrySet())  {
					
					String tempMapKey =entry.getKey();
					app_logs.info(tempMapKey);
					if(!tempMapKey.equalsIgnoreCase("tapeChart")){
						Wait.wait5Second();
						reservationSearch.basicSearchWithResNumber(driver, reservationNumberMap.get(tempMapKey), true);
						testSteps.add("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));
						app_logs.info("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));			
						testSteps.add("<b> ===== VERIFYING QUOTE '"+ tempMapKey +"' RESERVATION CAN NOT BE BOOKED BEYOND LOCKED DATE ===== </b>");
						app_logs.info("<b> ===== VERIFYING QUOTE '"+ tempMapKey +"' RESERVATION CAN NOT BE BOOKED BEYOND LOCKED DATE ===== </b>");
		
						reservationPage.bookReservationFromQuote(driver, testSteps);

						try{
							reservationPage.verifyErrorMessage(driver, testSteps);
						}catch(Exception|AssertionError e) {
								testSteps.add(e.toString());
								app_logs.info(e.toString());
							}						
						
				
					}					
				}
			}		
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be booked from locked date quote", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be booked from locked date quote", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		try {
			/*
			tapeChartClasses.add("TapeChart1-531");
			tapeChartClasses.add("TapeChart2-531");
			tapeChartClassesAbb.add("TC1-531");
			tapeChartClassesAbb.add("TC2-531");
			reservationNumberMap.put("tapeChart", "18421469");
			reservationFullName = "VerifyRes531 Realization531";
		*/
			app_logs.info("reservationNumberMap : "+ reservationNumberMap.size());

			if(caseType.equalsIgnoreCase("VerifyAddLineItemChangeRoomClassBeyondLockedDate")) {
			for (Map.Entry<String,String> entry : reservationNumberMap.entrySet())  {
					
					String tempMapKey =entry.getKey();
					app_logs.info(tempMapKey);
				if(tempMapKey.equalsIgnoreCase("mrb")) {
						testSteps.add("<b> ===== VERIFYING ROOM CLASS FOR 'MRB' RESERVATION CAN NOT BE UPDATED BEYOND LOCKED DATE ===== </b>");
						app_logs.info("<b> ===== VERIFYING ROOM CLASS FOR 'MRB' RESERVATION CAN NOT BE UPDATED BEYOND LOCKED DATE ===== </b>");						
					
						reservationSearch.basicSearchWithResNumber(driver, reservationNumberMap.get(tempMapKey), true);
						testSteps.add("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));
						app_logs.info("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));			
						
						getTestSteps.clear();
						getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
						testSteps.addAll(getTestSteps);
						
						reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);

						getTestSteps.clear();
						getTestSteps = reservationPage.clickFindRooms(driver);
						testSteps.addAll(getTestSteps);
						
						reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassNameList.get(roomClassNameList.size() -1), "Yes", "");
						
						//add select method to do
						getTestSteps.clear();
						getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
						testSteps.addAll(getTestSteps);
		
						try{
							reservationPage.verifyErrorMessage(driver, testSteps);
						}catch(Exception|AssertionError e) {
								testSteps.add(e.toString());
								app_logs.info(e.toString());
							}						
						

						try {
							
							//reservationPage.clickCloseMrbStayInfo(driver, testSteps);
						}catch(Exception e) {
							app_logs.info(" in catch");
						}
						
						getTestSteps.clear();
						getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
						testSteps.addAll(getTestSteps);

						reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);

						getTestSteps.clear();
						getTestSteps = reservationPage.clickFindRooms(driver);
						testSteps.addAll(getTestSteps);
						
						reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassNameList.get(roomClassNameList.size() -1), "Yes", "");
						
						//add select method to do
						getTestSteps.clear();
						getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
						testSteps.addAll(getTestSteps);
						
						
						try{
							reservationPage.verifyErrorMessage(driver, testSteps);
						}catch(Exception|AssertionError e) {
								testSteps.add(e.toString());
								app_logs.info(e.toString());
							}						
						
						
						try {
							
						//	reservationPage.clickCloseMrbStayInfo(driver, testSteps);
						}catch(Exception e) {
							app_logs.info(" in catch");
						}
						
						reservationPage.closeReservationTab(driver);
						testSteps.add("Close Reservation");
						app_logs.info("Close Reservation");
		
					}else if(tempMapKey.equalsIgnoreCase("account")){
						testSteps.add("<b> ===== VERIFYING ROOM CLASS FOR '"+ tempMapKey +"' RESERVATION CAN NOT BE UPDATED BEYOND LOCKED DATE ===== </b>");
						app_logs.info("<b> ===== VERIFYING ROOM CLASS FOR '"+ tempMapKey +"' RESERVATION CAN NOT BE UPDATED BEYOND LOCKED DATE ===== </b>");						

						reservationSearch.basicSearchWithResNumber(driver, reservationNumberMap.get(tempMapKey), true);
						testSteps.add("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));
						app_logs.info("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));			
					
						reservationPage.ClickEditStayInfo(driver, testSteps);			
						reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

						app_logs.info("===== " + "Verifying room class updation with 'Change only for added/removed dates' checked for reservation".toUpperCase() +" =====");
						testSteps.add("===== " + "Verifying room class updation with 'Change only for added/removed dates' checked for reservations".toUpperCase() +" =====");

						reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);

						Wait.wait10Second();		
						getTestSteps.clear();
						getTestSteps = reservationPage.clickFindRooms(driver);
						testSteps.addAll(getTestSteps);
						reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassNameList.get(roomClassNameList.size() -1), "Yes", account);

						testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));
						
						
						try{
							reservationPage.verifyErrorMessage(driver, testSteps);
						}catch(Exception|AssertionError e) {
								testSteps.add(e.toString());
								app_logs.info(e.toString());
							}						

						try {
							
					//		reservationPage.clickCloseStayInfo(driver, testSteps);
						}catch(Exception e) {
							app_logs.info(" in catch");
						
						}
						reservationPage.closeReservationTab(driver);
						testSteps.add("Close Reservtaion Tab");
						app_logs.info("Close Reservtaion Tab");
		
					}else 	if(tempMapKey.equalsIgnoreCase("tapeChart")){
						
						navigation.TapeChart(driver);
						testSteps.add("Navigate to tape chart");
						app_logs.info("Navigate to tape chart");
						
						reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone), ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"), timeZone, testSteps);
						tapeChart.enterAdult(driver, maxAdults, testSteps);
						tapeChart.selectRatePlan(driver, ratePlan, testSteps);			
						tapeChart.clickSearchButton(driver, testSteps);
						
						app_logs.info("========== DRAG AND DROP RESERVATION TO ANOTHER CLASSS==========");
						testSteps.add("<b>========== DRAG AND DROP RESERVATION TO ANOTHER CLASSS==========</b>");
						
						tapeChart.moveReservation(driver, testSteps, reservationFullName, tapeChartClassesAbb.get(0), 
							startRoomNumber, tapeChartClassesAbb.get(1),
								startRoomNumber, 2);
						Wait.wait10Second();
						tapeChart.updateRoomClass(driver, "", "", testSteps);
						

						try{
							reservationPage.verifyErrorMessage(driver, testSteps);
						}catch(Exception|AssertionError e) {
								testSteps.add(e.toString());
								app_logs.info(e.toString());
							}						
					
				
						navigation.cpReservationBackward(driver);
						testSteps.add("back to reservation page");
						app_logs.info("back to reservation page");
						
					}else {
						
						testSteps.add("<b> ===== VERIFYING ROOM CLASS FOR '"+ tempMapKey +"' RESERVATION CAN NOT BE UPDATED BEYOND LOCKED DATE ===== </b>");
						app_logs.info("<b> ===== VERIFYING ROOM CLASS FOR '"+ tempMapKey +"' RESERVATION CAN NOT BE UPDATED BEYOND LOCKED DATE ===== </b>");						

						reservationSearch.basicSearchWithResNumber(driver, reservationNumberMap.get(tempMapKey), true);
						testSteps.add("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));
						app_logs.info("Searched and opened reservation with number : "  + reservationNumberMap.get(tempMapKey));			
					
						reservationPage.ClickEditStayInfo(driver, testSteps);			
						reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);
						
						app_logs.info("===== " + "Verifying room class updation with 'Change only for added/removed dates' checked for reservation".toUpperCase() +" =====");
						testSteps.add("===== " + "Verifying room class updation with 'Change only for added/removed dates' checked for reservations".toUpperCase() +" =====");

						reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);
						
						Wait.wait10Second();		
						getTestSteps.clear();
						getTestSteps = reservationPage.clickFindRooms(driver);
						testSteps.addAll(getTestSteps);
						reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassNameList.get(roomClassNameList.size() -1), "Yes", "");						

						testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));

						try{
							reservationPage.verifyErrorMessage(driver, testSteps);
						}catch(Exception|AssertionError e) {
								testSteps.add(e.toString());
								app_logs.info(e.toString());
							}						
						

						try {
							
						//	reservationPage.clickCloseStayInfo(driver, testSteps);
						}catch(Exception e) {
							app_logs.info(" in catch");
						
						}
						

						reservationPage.closeReservationTab(driver);
						testSteps.add("Close Reservtaion Tab");
						app_logs.info("Close Reservtaion Tab");
					}
				}
			
			}	

			statusCode.add(0, "1");
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to lock date", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to lock date", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
			
		
		// Delete rate
			try {
				app_logs.info("==========DELETE RATE==========");
				testSteps.add("==========DELETE RATE==========");
			
				try {
					navigation.Inventory(driver, testSteps);
					app_logs.info("Navigate Inventory");
					testSteps.add("Navigate Inventory");
				
				}catch(Exception e) {
					navigation.inventoryBackwardAdmin(driver);
					app_logs.info("Navigate Inventory");
					testSteps.add("Navigate Inventory");					
				}
				
				navigation.secondaryRatesMenuItem(driver);
				app_logs.info("Navigate Rate");
				testSteps.add("Navigate Rate");

				Utility.refreshPage(driver);
				Wait.waitforPageLoad(30, driver);
				if(!caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
					
					rate.deleteRates(driver, tapeChartRateName);
					testSteps.add("New Rate has been Deleted successfully");
					app_logs.info("New Rate has been Deleted successfully");
	
					rate.verifyDeleteRate(driver, tapeChartRateName);
					testSteps.add("Verify the Deleted Rate : " + tapeChartRateName);
					app_logs.info("Verify the Deleted Rate " + tapeChartRateName);
				}else {

					rate.deleteRates(driver, rateName);
					testSteps.add("New Rate has been Deleted successfully");
					app_logs.info("New Rate has been Deleted successfully");

					rate.verifyDeleteRate(driver, rateName);
					testSteps.add("Verify the Deleted Rate : " + rateName);
					app_logs.info("Verify the Deleted Rate " + rateName);
					
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Delete Room Class
			try {

				app_logs.info("==========DELETE ROOM CLASS==========");
				testSteps.add("==========DELETE ROOM CLASS==========");

				navigation.mainSetupManu(driver);
				app_logs.info("Navigate Setup");
				testSteps.add("Navigate Setup");

				navigation.RoomClass(driver);
				app_logs.info("Navigate Room Class");
				testSteps.add("Navigate Room Class");
				if(!caseType.equalsIgnoreCase("VerifyBookQuoteReservationForLockedDate")) {
				
					for(int i=0; i < tapeChartClasses.size(); i++) 
					{
						getTestSteps.clear();
						getTestSteps = newRoomClass.deleteRoomClassV2(driver, tapeChartClasses.get(i));
						testSteps.addAll(getTestSteps);
					}
				}else {
					getTestSteps.clear();
					getTestSteps = newRoomClass.deleteRoomClassV2(driver, roomClassName);
					testSteps.addAll(getTestSteps);
						
				}
				
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
				}
			}
			
	}
		
	
	if(caseType.equalsIgnoreCase("VerifyReservationCreationForLockedDate")) {
		
		if(dateToLock.isEmpty() || dateToLock.equalsIgnoreCase("")) {
	 		dateToLock = ESTTimeZone.reformatDate(Utility.getDatePast_FutureDate(-60), "MM/dd/yyyy", "dd/MM/yyyy");
	 		//dateToLock = "09/12/2020";
	 		app_logs.info("date to lock : " + dateToLock);
	 	}
		
		//Case : 726480
		try {

		 	testSteps.add("===== " + "Locking period for specific date".toUpperCase() + " =====");
		 	app_logs.info("===== " + "Locking period for specific date".toUpperCase() + " =====");

		 	getTestSteps.clear();
			getTestSteps = navigation.NightAuditIcon(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
			testSteps.addAll(getTestSteps);

			nightAudit.GoButtonClick(driver, testSteps);				
			nightAudit.PeriodIsOpenButtonClick(driver, testSteps);

			testSteps.add("===== Verifying that date to lock period has pending line item less then 100 =====");
		 	app_logs.info("===== Verifying that date to lock period has pending line item less then 100 =====");
			int priorItemSize = nightAudit.getPriorItemSize(driver);
			if(priorItemSize > 0) {
						
				nightAudit.clickPriorItems(driver, testSteps);
				String getItems = nightAudit.getTotalPriorItems(driver, testSteps);
				app_logs.info("Prior items count for specific date: " + getItems);
				testSteps.add("Prior items count for specific date: " + getItems);
				if(Integer.parseInt(getItems) > 100) {
				    assertTrue(false, "Failed : Dates with more than 100 Items cannot be posted with automation. Please do it manually");						
			    }
				
				testSteps.add("===== Posting pending line item for specific date =====");
			 	app_logs.info("===== Posting pending line item for specific date =====");
	
				nightAudit.clickPostedStatus(driver, testSteps);
	
				getTestSteps.clear();
				getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
				testSteps.addAll(getTestSteps);
			
				nightAudit.GoButtonClick(driver, testSteps);				
				nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
	
				nightAudit.postAllItems(driver, testSteps, "CurrentItems");
				nightAudit.postAllItems(driver, testSteps, "PriorItems");
				nightAudit.postAllItems(driver, testSteps, "VoidItems");
				
				nightAudit.clickPostedStatus(driver, testSteps);
	
				getTestSteps.clear();
				getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
				testSteps.addAll(getTestSteps);
	
				nightAudit.GoButtonClick(driver, testSteps);				
				nightAudit.PeriodIsOpenButtonClick(driver, testSteps);	
			}
			
			nightAudit.switchToLockPeriodPopup(driver, testSteps);
			nightAudit.periodLockHeadingDisplay(driver, testSteps, true);	    
		    nightAudit.clickLock(driver, testSteps);
		    	
		    navigation.Reservation_Backward(driver);				
		    app_logs.info("Back to reservations");
		    testSteps.add("Back to reservations");
		    
			Utility.refreshPage(driver);
			Wait.waitforPageLoad(30, driver);

		    
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to lock date", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to lock date", testName, "PeriodLock", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			
			testSteps.add("===== VERIFYING RESERVATION FROM TAPECHART CANNOT BE CREATED FOR LOCKED DATE =====");
			app_logs.info("===== VERIFYING RESERVATION FROM TAPECHART CANNOT BE CREATED FOR LOCKED DATE =====");
		
			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone), ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"), timeZone, testSteps);
			tapeChart.enterAdult(driver, maxAdults, testSteps);
			tapeChart.selectRatePlan(driver, ratePlanNameList.get(0), testSteps);			
			tapeChart.clickSearchButton(driver, testSteps);
			
			tapeChart.clickOnEmptyCell(driver, testSteps, roomClassAbbList.get(0));

			try{
				tapeChart.verifyErrorMessage(driver, testSteps);
			}catch(Exception|AssertionError e) {
					testSteps.add(e.toString());
					app_logs.info(e.toString());
				}						

	
			statusCode.add(0, "1");
				
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String tempRatePlan = ratePlanNameList.get(0) + "|" + ratePlanNameList.get(0);
		String tempCheckin = ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone) + "|" + ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone); 
		String tempCheckOut = ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy") + "|" + ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy");
		String tempMaxAdults = maxAdults + "|" + maxAdults;
		String tempMaxChildren = maxChildren + "|" + maxChildren;
		String isSplitRes = "No";
		try {
			
			testSteps.add("===== VERIFYING MRB RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			app_logs.info("===== VERIFYING MRB RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			
			navigation.cpReservationBackward(driver);
			testSteps.add("back to reservations");
			app_logs.info("back to reservations");
			
			reservationPage.click_NewReservation(driver, testSteps);
			reservationPage.selectDates(driver, testSteps, tempCheckin, tempCheckOut, tempMaxAdults, tempMaxChildren, tempRatePlan, "",isSplitRes);
				
			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);


			try{
				reservationPage.verifyErrorMessage(driver, testSteps);
			}catch(Exception|AssertionError e) {
					testSteps.add(e.toString());
					app_logs.info(e.toString());
				}						
	
			statusCode.add(1, "1");
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");					
				
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		isSplitRes = "Yes";
		try {
			
			testSteps.add("===== VERIFYING SPLIT RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			app_logs.info("===== VERIFYING SPLIT RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			
			reservationPage.click_NewReservation(driver, testSteps);
			reservationPage.selectDates(driver, testSteps, tempCheckin, tempCheckOut, tempMaxAdults, tempMaxChildren, tempRatePlan, "",isSplitRes);
			
			if(isSplitRes.equalsIgnoreCase("Yes")) {
				getTestSteps.clear();
				reservationPage.enter_Adults(driver, getTestSteps, maxAdults);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				reservationPage.enter_Children(driver, getTestSteps, maxChildren);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanNameList.get(0), "");
				testSteps.addAll(getTestSteps);
				
			}
			
			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);


			try{
				reservationPage.verifyErrorMessage(driver, testSteps);
			}catch(Exception|AssertionError e) {
					testSteps.add(e.toString());
					app_logs.info(e.toString());
				}						

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");					
			

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify split reservation cannot be created for past date", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify split reservation cannot be created for past date", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("===== VERIFYING ACCOUNT RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			app_logs.info("===== VERIFYING ACCOUNT RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			
						
			testSteps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");

			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");
			
			accountList.add(account);
			accountNumberList.add(accountNumber);
			
			CreateTA.ClickNewAccountbutton(driver, accountType);
			CreateTA.AccountDetails(driver, accountType, account);
			CreateTA.ChangeAccountNumber(driver, accountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
					address1, address2, address3, email, city, state, postalCode, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountSave(driver, test, account, getTestSteps);
			testSteps.addAll(getTestSteps);
			CreateTA.NewReservationButton(driver, test);
			
			testSteps.add("===== CREATE RESERVATION =====");
			app_logs.info("===== CREATE RESERVATION =====");
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"));
			testSteps.addAll(getTestSteps);

			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.clickOnFindRooms(driver, testSteps);


			try{
				reservationPage.verifyErrorMessage(driver, testSteps);
			}catch(Exception|AssertionError e) {
					testSteps.add(e.toString());
					app_logs.info(e.toString());
			}						
	
			
			statusCode.add(2, "1");
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");					

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify new account reservation for locked date cannot be created", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify new account reservation for locked date cannot be created", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.RoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");
				app_logs.info("==========CREATE A NEW ROOM CLASS==========");
				testSteps.add("==========CREATE A NEW ROOM CLASS==========");
				
				try {
					newRoomClass.createRoomClassV2(driver, testSteps, groupRoomClass, groupRoomClassAbb, groupMaxAdults, maxPersons, roomQuantity);
					getTestSteps.clear();
					getTestSteps = newRoomClass.closeRoomClassTabV2(driver, groupRoomClass);
					testSteps.addAll(getTestSteps);
				}
				catch(Exception e) {				
						
					navigation.clickOnNewRoomClass(driver);
					
					getTestSteps.clear();
					getTestSteps = roomClass.createRoomClass(driver, groupRoomClass, groupRoomClassAbb, 
							bedsCount, maxAdults,
							maxPersons, roomQuantity, test, getTestSteps);
					testSteps.addAll(getTestSteps);

					roomClass.closeRoomClassTab(driver);
					testSteps.add("Close created room class tab");
				}
							
				testSteps.add("Sccessfully Created New RoomClass " + groupRoomClass + " Abb : " + groupRoomClassAbb);
				app_logs.info("Sccessfully Created New RoomClass" + groupRoomClass + " Abb : " + groupRoomClassAbb);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create New Rate and Attach RoomClass

		try {
			testSteps.add("==========CREATE A NEW RATE==========");
			app_logs.info("==========CREATE A NEW RATE==========");
			
			
			navigation.Inventory(driver, testSteps);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			getTestSteps.clear();
			getTestSteps = navigation.secondaryRatesMenuItem(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = rate.ClickNewRate(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = rate.EnterRateName(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.SelectRatePlan(driver, ratePlan);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = rate.EnterMaxAdults(driver, maxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxPersons(driver, maxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterBaseAmount(driver, baseAmount);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalAdult(driver, addtionalAdult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalChild(driver, additionalChild);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDisplayName(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRatePolicy(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDescription(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSeason(driver, associateSession);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateRoomClass(driver, groupRoomClass);
			testSteps.addAll(getTestSteps);				
	
			getTestSteps.clear();
			getTestSteps = rate.AssociateSource(driver, source);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.Save_DoneRate(driver);
			testSteps.addAll(getTestSteps);

			rate.SearchRate(driver, rateName, false);
			testSteps.add("New Rate '" + rateName + "' Created & Verified ");
			app_logs.info("New Rate '" + rateName + "' Created & Verified");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Clicking on Groups
		try {
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate back to reservation", testName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate back to reservation", testName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

			// Click New Account and Enter Account Name
			try {
				app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
				testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");

				navigation.secondaryGroupsManu(driver);
				testSteps.add("Navigate Groups");
				app_logs.info(" Navigate Groups");

				getTestSteps.clear();
				getTestSteps = group.enterrGroupName(driver, accountName);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to click new account and enter account name", testName,
							"EnterAccountName", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Enter Account Number
			try {
				accountNumber = Utility.GenerateRandomString15Digit();
				getTestSteps.clear();
				getTestSteps = group.enterAccountNo(driver, accountNumber);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Enter Account Attributes
			try {
				getTestSteps.clear();
				getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Enter Account Mailing Address
			try {
				getTestSteps.clear();
				getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, phoneNumber, address1,
						city, state, country, postalCode);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
							"EnterAccountMailingAddress", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
							"EnterAccountMailingAddress", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Check Mailing Info CheckBox
			try {
				getTestSteps.clear();
				getTestSteps = group.Billinginfo(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Save Account
			try {
				getTestSteps.clear();
				getTestSteps = group.clickOnSave(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Navigate to Reservation and check count
			try {
				app_logs.info("==========GET RESERVATION COUNT==========");
				testSteps.add("==========GET RESERVATION COUNT==========");
				getTestSteps.clear();
				group.clickOnGroupsReservationTab(driver);
				testSteps.addAll(getTestSteps);

				String initialResCount = group.getReservationCount(driver);
				testSteps.add("Initial Reservation Count : " + initialResCount);
				Utility.app_logs.info("Initial Reservation Count : " + initialResCount);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("==========CREATE NEW BLOCK==========");
				testSteps.add("==========CREATE NEW BLOCK==========");

				getTestSteps.clear();
				getTestSteps = group.navigateRoomBlock(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickNewBlock(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.EnterBlockName(driver, blockName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickOkay_CreateNewBlock(driver);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========SEARCH ROOMS==========");
				testSteps.add("==========SEARCH ROOMS==========");

				getTestSteps.clear();
				getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("dd/MM/yyyy"),
						Utility.GetNextDate(1, "dd/MM/yyyy"));
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectRatePlan(driver, ratePlan);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectAdults(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectChilds(driver, child);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.EnterNights(driver, roomPerNight);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickSearchGroup(driver);
				testSteps.addAll(getTestSteps);

				advgroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
				advgroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, groupRoomClass);

				advgroup.ClickCreateBlock(driver, getTestSteps);

				app_logs.info("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
				testSteps.add("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
				String RoomBlocked = group.getRoomBlockedInRoomBlockDetatil(driver);
				Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
				testSteps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
				assertEquals(RoomBlocked, roomPerNight, "Failed Room Blocked Not Matched");

				String totalRoomNight = group.getTotalRoomNightsInRoomBlockDetail(driver);
				Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
				testSteps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
				assertEquals(totalRoomNight, roomPerNight, "Failed Room Blocked Not Matched");

				String expectedRevenueDetail = group.getExpectedRevenueInRoomBlockDetail(driver);
				Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
				testSteps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

				String expectedRevenueInfo = group.getExpectedRevenueInGroupInfo(driver);
				Utility.app_logs.info("Group Info Expected Revenue  : " + expectedRevenueInfo);
				testSteps.add("Group Info ExpectedRevenue  : " + expectedRevenueInfo);
				assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

				String expectedRevenue = expectedRevenueInfo;
				String pickUpPercentage = group.getPickUpPercentageInRoomBlockDetatil(driver);
				Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
				testSteps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
				assertEquals(pickUpPercentage, "0", "Failed Pickup Percentage missmatched");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			String beforeAvailableRoom = null;
			String beforePickupValue = null;
			try {
				String beforeBookIconClass = null;
				app_logs.info("==========ROOMING LIST DETAILS==========");
				testSteps.add("==========ROOMING LIST DETAILS==========");
				Utility.app_logs.info("Book Room From RoomClass : " + groupRoomClass);
				testSteps.add("Book Room From RoomClass : " + groupRoomClass);

				beforePickupValue = group.getPickUpValue(driver, groupRoomClass);
				Utility.app_logs.info("Pickup Value : " + beforePickupValue);
				testSteps.add("Pickup Value : " + beforePickupValue);

				beforeAvailableRoom = group.getAvailableRooms(driver, groupRoomClass);
				Utility.app_logs.info("Available Rooms : " + beforeAvailableRoom);
				testSteps.add("Available Rooms : " + beforeAvailableRoom);

				beforeBookIconClass = group.getBookIconClass(driver, groupRoomClass);
				Utility.app_logs.info("BookIcon Class : " + beforeBookIconClass);
				testSteps.add("BookIcon Class : " + beforeBookIconClass);
				assertEquals(beforeBookIconClass, blueBookClass, "Failed: Blue book icon is not showing");

				String blockedCount = group.getBlocked(driver, groupRoomClass);
				Utility.app_logs.info("Blocked Count  : " + blockedCount);
				testSteps.add("Blocked Count  : " + blockedCount);
				assertEquals(blockedCount, roomBlockCount, "Failed Room Blocked Not Matched");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
		try {
			testSteps.add("===== VERIFYING GROUP BLOCK DATES CANNOT BE UPDATED FOR LOCKED DATE =====");
			app_logs.info("===== VERIFYING GROUP BLOCK DATES CANNOT BE UPDATED FOR LOCKED DATE =====");
			
				group.clickEditIcon(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = group.selectArrivalDepartureDates(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "dd/MM/yyyy", -1, timeZone),
						dateToLock);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickSearchGroup(driver);
				testSteps.addAll(getTestSteps);
				groupRoomClass = "GroupBlock612";
				Wait.wait10Second();
				advgroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
				advgroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, groupRoomClass);

				group.clickSave(driver, testSteps);
				

				try{
					group.verifyErrorMessage(driver, testSteps);
				}catch(Exception|AssertionError e) {
						testSteps.add(e.toString());
						app_logs.info(e.toString());
					}						

				testSteps.add("VERIFIED GROUP BLOCK DATES CANNOT BE UPDATED FOR LOCKED DATE".toLowerCase());
				app_logs.info("VERIFIED GROUP BLOCK DATES CANNOT BE UPDATED FOR LOCKED DATE".toLowerCase());
				statusCode.add(3, "1");
					
				group.clickCancel(driver, testSteps);
				
				navigation.Reservation_Backward(driver);
				testSteps.add("Back to reservation page");
				app_logs.info("Back to reservation page");
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to update stay dates beyond period lock", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to update stay dates beyond period lock", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
		// Delete rate
				try {
					app_logs.info("==========DELETE RATE==========");
					testSteps.add("==========DELETE RATE==========");
				
					try {
						navigation.Inventory(driver);
						app_logs.info("Navigate Inventory");
						testSteps.add("Navigate Inventory");
					
					}catch(Exception e) {
						navigation.inventoryBackwardAdmin(driver);
						app_logs.info("Navigate Inventory");
						testSteps.add("Navigate Inventory");					
					}
					
					navigation.secondaryRatesMenuItem(driver);
					app_logs.info("Navigate Rate");
					testSteps.add("Navigate Rate");

					rate.deleteRates(driver, rateName);
					testSteps.add("New Rate has been Deleted successfully");
					app_logs.info("New Rate has been Deleted successfully");

					rate.verifyDeleteRate(driver, rateName);
					testSteps.add("Verify the Deleted Rate : " + rateName);
					app_logs.info("Verify the Deleted Rate " + rateName);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Delete Room Class
				try {

					app_logs.info("==========DELETE ROOM CLASS==========");
					testSteps.add("==========DELETE ROOM CLASS==========");

					navigation.mainSetupManu(driver);
					app_logs.info("Navigate Setup");
					testSteps.add("Navigate Setup");

					navigation.RoomClass(driver);
					app_logs.info("Navigate Room Class");
					testSteps.add("Navigate Room Class");

					getTestSteps.clear();
					getTestSteps = newRoomClass.deleteRoomClassV2(driver, groupRoomClass);
					testSteps.addAll(getTestSteps);
					
					navigation.cpReservation_Backward(driver);
					testSteps.add("Back to reservation page");
					app_logs.info("Back to reservation page");
					
					
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
					}
				}

		try {
			
			testSteps.add("===== VERIFYING RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			app_logs.info("===== VERIFYING RESERVATION CANNOT BE CREATED FOR LOCKED DATE =====");
			
			reservationPage.click_NewReservation(driver, testSteps);					
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"));
			testSteps.addAll(getTestSteps);

			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.clickOnFindRooms(driver, testSteps);
		

			try{
				reservationPage.verifyErrorMessage(driver, testSteps);
			}catch(Exception|AssertionError e) {
					testSteps.add(e.toString());
					app_logs.info(e.toString());
			}						
	
			statusCode.add(4, "1");
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");					
			
				
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("===== " + "Verifying stay info dates for reservations can be updated for locked date even after chekcin , checkout, nowshow, cancel and roll back".toUpperCase() +" =====");
			testSteps.add("===== " + "Verifying stay info dates for reservations can be updated for locked date even after chekcin , checkout, nowshow, cancel and roll back".toUpperCase() +" =====");
			
			reservationPage.click_NewReservation(driver, testSteps);					
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, Utility.getCurrentDate("MM/dd/yyyy", timeZone));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  Utility.getNextDate(1, "MM/dd/yyyy", timeZone));
			testSteps.addAll(getTestSteps);

			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.select_Rateplan(driver, testSteps, ratePlanNameList.get(0),"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			
			reservationPage.select_Room(driver, testSteps, roomClassNameList.get(0), isAssign, "");
			reservationPage.clickNext(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);

			reservationPage.SelectReferral(driver, referral);
			
			reservationPage.clickBookNow(driver, testSteps);

			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
			reservationNumberList.add(reservationNumber);
			
			reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
			reservationStatusList.add(reservationStatus);
			
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			
			roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
			roomNumberList.add(roomNumber);		

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
		
			testSteps.add("===== " + "Cancelling in reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Cancelling in reservation".toUpperCase() +" =====");

			reservationPage.change_ReservationStatus(driver, testSteps, "Cancel");
			testSteps.add("==========Start Verifying Roll Back Button ==========");
			reservationPage.verifyRollBackButton(driver, testSteps);
			reservationPage.clickRollBackButton(driver, testSteps);
			reservationPage.clickYesButtonRollBackMsg(driver, testSteps);

			testSteps.add("===== " + "Changing reservation status to No Show".toUpperCase() +" =====");
			app_logs.info("===== " + "Changing reservation status to No Show".toUpperCase() +" =====");
			
			reservationPage.noShowReservation(driver, "Void");
			testSteps.add("==========Start Verifying Roll Back Button ==========");
			reservationPage.verifyRollBackButton(driver, testSteps);
			reservationPage.clickRollBackButton(driver, testSteps);
			reservationPage.clickYesButtonRollBackMsg(driver, testSteps);			
			
			testSteps.add("===== " + "Checking in reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Checking in reservation".toUpperCase() +" =====");
		
			reservationPage.Click_CheckInButton(driver, testSteps);
			reservationPage.disableGenerateGuestReportToggle(driver, testSteps);
			getTestSteps.clear();
			getTestSteps = homePage.clickConfirmChekInButton(driver);
			testSteps.addAll(getTestSteps);

			try {
				getTestSteps.clear();
				getTestSteps = homePage.clickPayButton(driver);
				testSteps.addAll(getTestSteps);

			}catch(Exception e) {
			}

			try {
				getTestSteps.clear();
				getTestSteps = homePage.clickCloseCheckinSuccessfullPopup(driver);
				testSteps.addAll(getTestSteps);
			}
			catch(Exception e) 
			{
				
			}
			testSteps.add("Reservation checked in");
			app_logs.info("Reservation checked in");

			testSteps.add("===== " + "Checking out reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Checking out reservation".toUpperCase() +" =====");
		
			reservationPage.clickCheckOutButton(driver, testSteps);
			reservationPage.generatGuestReportToggle(driver, testSteps, "No");
			reservationPage.proceedToCheckOutPayment(driver, testSteps);
			try{
				  getTestSteps.clear(); getTestSteps = homePage.clickPayButton(driver);
				  testSteps.addAll(getTestSteps);
				
			}
			catch(Exception e) {
				  
			 }
			try{
					
				getTestSteps.clear(); 
				getTestSteps =
				homePage.clickCloseCheckoutSuccessfullPopup(driver);
				testSteps.addAll(getTestSteps);
			}
			catch(Exception e) {
				  
			}
			 
			testSteps.add("Reservation checked out");
			app_logs.info("Reservation checked out");
			String loading = "(//div[@class='ir-loader-in'])";
			Wait.explicit_wait_absenceofelement(loading, driver);

			testSteps.add("==========Start Verifying Roll Back Button ==========");
			reservationPage.verifyRollBackButton(driver, testSteps);

			testSteps.add("<b>==========Start Verifying DEPARTED Status==========</b>");
			reservationPage.verifyReservationStatusStatus(driver, testSteps, "DEPARTED");
			
			reservationPage.clickRollBackButton(driver, testSteps);
			reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
	
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to checkin and check out reservation reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to checkin and check out reservation reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
			
		try {
			
			//Case : 728004			
			app_logs.info("===== " + "Verifying stay info dates with 'Recalculate Rate' checked for reservations can be updated for locked date".toUpperCase() +" =====");
			testSteps.add("===== " + "Verifying stay info dates with 'Recalculate Rate' checked for reservations can be updated for locked date".toUpperCase() +" =====");

			reservationPage.ClickEditStayInfo(driver, testSteps);
			
			reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);
			
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", -1, timeZone));
			testSteps.addAll(getTestSteps);
								
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver,  ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"));
			testSteps.addAll(getTestSteps);

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 1);
								
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
			
			reservationPage.verifyNoRateCombinationMessage(driver, testSteps, false);
			reservationPage.verifyRoomClasses(driver, testSteps, true);

			app_logs.info("===== " + "Verifying stay info dates with 'Change only for added/removed dates' checked for reservations can be updated for locked date".toUpperCase() +" =====");
			testSteps.add("===== " + "Verifying stay info dates with 'Change only for added/removed dates' checked for reservations can be updated for locked date".toUpperCase() +" =====");

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);
				
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
			
			reservationPage.verifyNoRateCombinationMessage(driver, testSteps, false);
			reservationPage.verifyRoomClasses(driver, testSteps, true);

			app_logs.info("===== " + "Verifying stay info dates with 'No Rate Change' checked for reservations can be updated for locked date".toUpperCase() +" =====");
			testSteps.add("===== " + "Verifying stay info dates with 'No Rate Change' checked for reservations can be updated for locked date".toUpperCase() +" =====");

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
						
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
		
			reservationPage.verifyNoRateCombinationMessage(driver, testSteps, false);
			reservationPage.verifyRoomClasses(driver, testSteps, true);
			statusCode.add(5, "1");
			statusCode.add(6, "1");

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update stay info for reservation with different rate combination" , testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update stay info for reservation with different rate combination" , testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	if(caseType.equalsIgnoreCase("SingleReservation") || caseType.equalsIgnoreCase("Account") || caseType.equalsIgnoreCase("CustomFolio"))
		{			
		
			try {
	
				assertTrue(numberOfReservation <= 2, "Failed : Script can only create 2 reservation");
				for(int i=0; i < numberOfReservation; i++) {
					
					if(caseType.equalsIgnoreCase("Account")) {
						testSteps.add("========== Creating account ==========");
						app_logs.info("========== Creating account ==========");
	
						navigation.Accounts(driver);
						testSteps.add("Navigate to Accounts");
						app_logs.info("Navigate to Accounts");
	
						try 
						{
							reservationPage.closeReservationTab(driver);
							testSteps.add("Closed opened account");
							app_logs.info("Closed opened account");															
						}catch(Exception e) {
							app_logs.info("No opened account found");															
												
						}					
						
						Utility.refreshPage(driver);
						Wait.waitforPageLoad(30, driver);
						
						accountList.add(account);
						accountNumberList.add(accountNumber);
						
						CreateTA.ClickNewAccountbutton(driver, accountType);
						CreateTA.AccountDetails(driver, accountType, account);
						CreateTA.ChangeAccountNumber(driver, accountNumber);
						getTestSteps.clear();
						getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
						testSteps.addAll(getTestSteps);
						getTestSteps.clear();
						getTestSteps = CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
								address1, address2, address3, email, city, state, postalCode, getTestSteps);
						testSteps.addAll(getTestSteps);
						getTestSteps.clear();
						getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
						testSteps.addAll(getTestSteps);
						getTestSteps.clear();
						getTestSteps = CreateTA.AccountSave(driver, test, account, getTestSteps);
						testSteps.addAll(getTestSteps);
						CreateTA.NewReservationButton(driver, test);
	
					}else {
						reservationPage.click_NewReservation(driver, testSteps);						
					}
					int resCount =  i + 1;
					testSteps.add("===== CREATE RESERVATION ("+ resCount +") =====");
					app_logs.info("===== CREATE RESERVATION ("+ resCount +") =====");
					
					reservationPage.enterCheckInDate(driver, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "MM/dd/yyyy"));
					reservationPage.enterCheckOutDate(driver, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "MM/dd/yyyy"));
					reservationPage.enter_Adults(driver, testSteps, maxAdults);
					reservationPage.enter_Children(driver, testSteps, maxChildren);
					reservationPage.select_Rateplan(driver, testSteps, ratePlanNameList.get(i),"");
					reservationPage.clickOnFindRooms(driver, testSteps);
					String isAssign = "Yes";
					
					reservationPage.select_Room(driver, testSteps, roomClassNameList.get(i), isAssign, "");
					depositAmount=reservationPage.deposit(driver, testSteps, isDepositOverride, depositOverrideAmount);
					reservationPage.clickNext(driver, testSteps);									
	
					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
							guestLastName);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
					testSteps.addAll(getTestSteps);
	
					reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
	
					reservationPage.SelectReferral(driver, referral);
					
					reservationPage.clickBookNow(driver, testSteps);
					
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);
					reservationNumberList.add(reservationNumber);
					
					reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
					reservationStatusList.add(reservationStatus);
					
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
					
					roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
					roomNumberList.add(roomNumber);		
	
					tripSummaryRoomCharges = reservationPage.getRoomChargesInTripSummary(driver);
					app_logs.info("TripSummaryRoomCharges : " + tripSummaryRoomCharges);
					tripSummaryRoomCharges=tripSummaryRoomCharges.trim();
					tripSummaryRoomCharges=tripSummaryRoomCharges.substring(1, tripSummaryRoomCharges.length());
					tripSummaryRoomCharges=tripSummaryRoomCharges.trim();
					testSteps.add("TripSummaryRoomCharges : " + tripSummaryRoomCharges);
					app_logs.info("TripSummaryRoomCharges : " + tripSummaryRoomCharges);
					tripSummaryRoomChargesList.add(tripSummaryRoomCharges);
					
					tripSummaryTaxandServices = reservationPage.getTaxandServicesInTripSummary(driver);
					app_logs.info("TripSummaryTaxandServices : " + tripSummaryTaxandServices);				
					tripSummaryTaxandServices=tripSummaryTaxandServices.trim();
					tripSummaryTaxandServices=tripSummaryTaxandServices.substring(1, tripSummaryTaxandServices.length());
					tripSummaryTaxandServices=tripSummaryTaxandServices.trim();
					testSteps.add("TripSummaryTaxandServices : " + tripSummaryTaxandServices);
					app_logs.info("TripSummaryTaxandServices : " + tripSummaryTaxandServices);
					tripSummaryTaxandServicesList.add(tripSummaryTaxandServices);
					
					reservationPage.click_Folio(driver, testSteps);
					
					if(caseType.equalsIgnoreCase("CustomFolio")) {
						folio.addCustomFolio(driver, customFolio, customFolio, testSteps);					
						Wait.wait10Second();
						folio.ChangeFolio(driver, guestFolio, customFolio);								
					} else if(caseType.equalsIgnoreCase("Account")) {
						folio.ChangeFolio(driver, guestFolio, account);																
					}
					Wait.wait30Second();				
					folio.makePayment(driver, capture, amountToPay, testSteps);
								
					Wait.wait30Second();				
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");					
				
					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);
				}
				app_logs.info("reservationNumberList : " + reservationNumberList.size());					
				
				for(String s : reservationNumberList) {
					if(allReservation.isEmpty() || allReservation.equalsIgnoreCase("")) {
						allReservation = s;					
					}else {
						allReservation = allReservation + "," + s;
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		
			try {
	 			
			 	testSteps.add("===== Post line items =====");
			 	app_logs.info("===== Post line items =====");
			 	
			 	getTestSteps.clear();
				getTestSteps = navigation.NightAuditIcon(driver);
				testSteps.addAll(getTestSteps);
				
				for(int i=0; i < numberOfReservation; i++) {
					getTestSteps.clear();
					getTestSteps = nightAudit.enterAuditDate(driver, checkInDate);
					testSteps.addAll(getTestSteps);
	
					nightAudit.GoButtonClick(driver, testSteps);				
					nightAudit.clickDailyTransactions(driver, testSteps);
					nightAudit.postCurrentItems(driver, testSteps, reservationNumberList.get(i));				
					nightAudit.clickPostedStatus(driver, testSteps);
	
				}
				
			    navigation.Reservation_Backward(driver);				
			    app_logs.info("Back to reservations");
			    testSteps.add("Back to reservations");
			    
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to post items", testName, "PostCurrentItems", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to post items", testName, "PostCurrentItems", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			try {
/*
				String s = "17876599,17876603";
				for(String sp: s.split(",")) {
					reservationNumberList.add(sp);	
				}
*/				
				for(int  i=0; i < reservationNumberList.size(); i++) {
					
					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);
					
					reservationSearch.basicSearchWithResNumber(driver, reservationNumberList.get(i), true);
					testSteps.add("Searched and opened reservation with number : "  + reservationNumberList.get(i));
					app_logs.info("Searched and opened reservation with number : "  + reservationNumberList.get(i));	
	
					reservationPage.click_Folio(driver, testSteps);
				
					testSteps.add("===== " + "Verifying room charge of reservation ("+ reservationNumberList.get(i) +") is in pending state".toUpperCase() + " =====");
					app_logs.info("===== " + "Verifying room charge of reservation ("+ reservationNumberList.get(i) +") is in pending state".toUpperCase() + " =====");
				
					folio.VerifyLineItems_State(driver, roomCharge, postedState, 1);
					testSteps.add("Verified room charge of reservation ("+ reservationNumberList.get(i) +") is in pending state");
					app_logs.info("Verified room charge of reservation ("+ reservationNumberList.get(i) +") is in pending state");
	
	
					testSteps.add("===== " + "Verifying room charge of second reservation is in pending state after posting line items in night audit".toUpperCase() + " =====");
					app_logs.info("===== " + "Verifying room charge of second reservation is in pending state after posting line items in night audit".toUpperCase() + " =====");
				
					folio.VerifyLineItems_State(driver, roomCharge, pendingState, 1);
					testSteps.add("Verified room charge of second reservation is in pending state after posting line items in night audit");
					app_logs.info("Verified room charge of second reservation is in pending state after posting line items in night audit");
	
					if(caseType.equalsIgnoreCase("CustomFolio")) {
						testSteps.add("===== " + "Changing to custom folio".toUpperCase() + " =====");
						app_logs.info("===== " + "Changing to custom folio".toUpperCase() + " =====");
						folio.ChangeFolio(driver, guestFolio, customFolio);								
					} else if(caseType.equalsIgnoreCase("Account")) {
						testSteps.add("===== " + "Changing to account folio".toUpperCase() + " =====");
						app_logs.info("===== " + "Changing to account folio".toUpperCase() + " =====");
						folio.ChangeFolio(driver, guestFolio, accountList.get(i));																
					}
					
					testSteps.add("===== " + "Verifying payment line item of reservation ("+ reservationNumberList.get(i) +") is in posted state".toUpperCase() + " =====");
					app_logs.info("===== " + "Verifying payment line item of reservation ("+ reservationNumberList.get(i) +") is in posted state".toUpperCase() + " =====");
		
					try {
							folio.VerifyLineItems_State(driver, paymentType, postedState, 1);					
					}catch(NoSuchElementException|AssertionError e) {
							folio.VerifyLineItems_State(driver, paymentType, postedStateForPayment, 1);					
					}
					
					folio.verifyAdvanceDepositRealized(driver, paymentType, testSteps);
					
					testSteps.add("Verified payment line item is in posted state");
					app_logs.info("Verified payment line item is in posted state");
						
					
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");					
				
				}
			
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify posted items", testName, "VerifyPostedItems", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify posted items", testName, "VerifyPostedItems", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
	
	try {
		comments = " Verified advance deposit realization for " + caseType;
		statusCode.add(0, "1");
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
}
	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyRealization", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
