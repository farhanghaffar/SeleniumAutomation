package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.OverView;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyNoRateCombinationForConditionalRatePlan extends TestCore{

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
	Navigation navigation = new Navigation();
	CPReservationPage res = new CPReservationPage();
	RoomMaintenance room_maintenance = new RoomMaintenance();
	RoomStatus roomstatus = new RoomStatus();
	OverView overview = new OverView();
	Tapechart tapeChart = new Tapechart();
	CPReservationPage reservationPage = new CPReservationPage();
	NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
	Rules rules = new Rules();
	Season seasonObj = new Season();
	Rate rate = new Rate();
	ReservationHomePage homePage = new ReservationHomePage();
	Account CreateTA = new Account();
	ReservationSearch reservationSearch =new ReservationSearch();	
	
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	List<LocalDate> datesList = new ArrayList<LocalDate>();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	List<String> date = new ArrayList<String>();
	List<String> day = new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	ArrayList<String> checkInDates = new ArrayList<>();


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void verifyNoRateCombinationForConditionalRatePlan(String testCaseID,String ratePlan, String militaryPlan, String roomClassName, String RoomClassAbbrivation) throws ParseException {
		test_name = "VerifyNoRateCombinationForConditionalRatePlan";
		test_description = "VerifyNoRateCombinationForConditionalRatePlan <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848367' target='_blank'>"
				+ "Click here to open TestRail: C848367</a><br>";
		String reservation = null, testName = null, rateName = null,
				rateDisplayName = null;
		String nights = null, startDate = null, endDate = null;
		
		String randomString = Utility.GenerateRandomString();
		String salutation = "Mr."; 
		String guestFirstName = "VerifyRes" + randomString; 
		String guestLastName = "Realization" + randomString;
		String paymentType = "MC"; 
		String marketSegment = "GDS"; 
		String referral = "Other";
		
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
		String postalCode = "12345"; 
		String state = "Alaska";

		if(!Utility.validateString(testCaseID)) {
			caseId.add("848367");
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
		String BedsCount = "1";
		String MaxAdults = "2";
		String MaxPersons = "5";
		String RoomQuantity = "2";
		String RateName = "ConditionalRate";
		String tempRate1 = RateName; 
		String DisplayName = RateName;
		String Amount = "100";		
		String AssociateSeason = "All Year Season";
		
		String timeZone = "US/Eastren";
		String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		String checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
		String source = "innCenter";			
		String maxAdult = "1";
		String maxPerson = "0";
		checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "MM/dd/yyyy"));
		
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
			loginClinent3281(driver);
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
		String reservationNumber = "";
		
		String cardNumber = "5454545454545454"; 
		String nameOnCard = guestFirstName; 
		String cardExpDate = "12/23";

		// Reservation
		try 
		{
//825401				
			testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/848367' target='_blank'>"
					+ "===== <b> Validation message for Payment method section during create reservation. </b> ===== </a>");
			
			reservationPage.click_NewReservation(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			

			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			reservationPage.SelectReferral(driver, referral);

			testSteps.add("Verifying Invalid data error by providing CarNumber, Past Expiry Date and so on");
			
			String CardNumber = "124";
			String CardExpDate = "2/2020";
			String NameOnCard  = guestFirstName;
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, CardNumber, NameOnCard,
					CardExpDate);
			
			testSteps.add("Verify Error message 'Please enter a valid credit card number.' is showing");
			boolean isDisplay = false;
			try {
				isDisplay = homePage.getIsInvalidCardNumberErrorMessageShowing(driver);
				Assert.assertTrue(isDisplay, "Failed : Error message 'Please enter a valid credit card number.' is not showing");
			}catch (Exception|Error e) {
				testSteps.add(e.toString());
			}
			testSteps.add("Verified Error message 'Please enter a valid credit card number.' is showing");

			testSteps.add("Verify Error message 'Invalid/expired date' is showing");
			try {
				isDisplay = homePage.getIsInvalidCardExpiryErrorMessageShowing(driver);
				Assert.assertTrue(isDisplay, "Failed : Error message 'Invalid/expired date' is not showing");
			testSteps.add("Verified Error message 'Invalid/expired date' is showing");
			}catch (Exception|Error e) {
				testSteps.add(e.toString());
			}

			testSteps.add("Verify Error message 'This field is required.' is showing");
			homePage.clearCardNumberField(driver, testSteps);
			try {
				isDisplay = homePage.getIsCardNumberRequiredErrorMessageShowing(driver);
				Assert.assertTrue(isDisplay, "Failed : Error message 'This field is required.' is not showing");
			}catch (Exception|Error e) {
				testSteps.add(e.toString());
			}
			testSteps.add("Verified Error message 'This field is required.' is showing");
			
			testSteps.add("Verify Error message 'This field is required.' is showing");
			homePage.clearNameOnCardField(driver, testSteps);
			try {
			isDisplay = homePage.getIsNameOnCardRequiredErrorMessageShowing(driver);
			Assert.assertTrue(isDisplay, "Failed : Error message 'This field is required.' is not showing");
			}catch (Exception|Error e) {
				testSteps.add(e.toString());
			}
			testSteps.add("Verified Error message 'This field is required.' is showing");
			
			testSteps.add("Verify Error message 'This field is required.' is showing");
			reservationPage.createTaxExempt(driver, "123", testSteps);
			homePage.clearTaxExemptId(driver, testSteps);
			try {
			isDisplay = homePage.getIsTaxExemptRequiredErrorMessageShowing(driver);
			Assert.assertTrue(isDisplay, "Failed : Error message 'This field is required.' is not showing");
			}catch (Exception|Error e) {
				testSteps.add(e.toString());
			}
			testSteps.add("Verified Error message 'This field is required.' is showing");
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");

			
			reservationPage.click_NewReservation(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			isAssign = "Yes";
			

			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);


			CardNumber = "5454545454545454";
			CardExpDate = "02/23";
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, CardNumber, NameOnCard,
					CardExpDate);

			reservationPage.SelectReferral(driver, referral);
			
			reservationPage.longStayCheckbox(driver, testSteps, true);
			
			reservationPage.clickBookNow(driver, testSteps);

			testSteps.add("Verify Popup with heading 'Long Stay- Tax Exempt' is showing after checking Long Stay checkbox");
			try {
			isDisplay = homePage.verifyTaxExamptHeadingIsDisplaying(driver);
			Assert.assertTrue(isDisplay, "Failed : Popup with heading 'Long Stay- Tax Exempt' is not showing");
			}catch (Exception|Error e) {
				testSteps.add(e.toString());
			}
			testSteps.add("Verified Popup with heading 'Long Stay- Tax Exempt' is showing after checking Long Stay checkbox");

			if(isDisplay) {
				homePage.clickSetExemptAnywayButton(driver, testSteps);
				testSteps.add("Verify Enter tax exempt field is enabled after clicking 'SET EXEMPT ANYWAY' button");
				try {
				isDisplay = homePage.isEnterTaxExcemptIdEnabled(driver);
				Assert.assertTrue(isDisplay, "Failed : Popup with heading 'Long Stay- Tax Exempt' is not showing");
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
				}
				testSteps.add("Verified Enter tax exempt field is enabled after clicking 'SET EXEMPT ANYWAY' button");

				testSteps.add("Verify Error message 'This field is required.' is showing");
				try {
				isDisplay = homePage.getIsTaxExemptRequiredErrorMessageShowing(driver);
				Assert.assertTrue(isDisplay, "Failed : Error message 'This field is required.' is not showing");
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
				}
				testSteps.add("Verified Error message 'This field is required.' is showing");
				
			}
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");
		
			statusCode.add(0, "1");
			statusCode.add(1, "1");
			statusCode.add(2, "1");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify payment info section while creating new reservation", testName, "VerifyPaymentInfo", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify payment info section while creating new reservation", testName, "VerifyPaymentInfo", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		paymentType = "Check"; 

		try {
			Utility.refreshPage(driver);
			Wait.waitUntilPageIsLoaded(driver);
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848336' target='_blank'>"
					+ "<b>View payment Info that is associate with check.</a> =====");
				
			reservationPage.click_NewReservation(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			

			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			reservationPage.SelectReferral(driver, referral);
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
			
			reservationPage.clickBookNow(driver, testSteps);

			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
						
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			
			homePage.verifyPaymentType(driver, getTestSteps, paymentType);
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");
			
			statusCode.add(3, "1");
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			Utility.refreshPage(driver);
			Wait.waitUntilPageIsLoaded(driver);
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848395' target='_blank'>"
					+ "<b>Verify the functionality by providing the credit card expiry date as\"m/yy\" while creating the reservation from Accounts Module.</a> =====");
	
				testSteps.add("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
			
				testSteps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
	
				navigation.Accounts(driver);
				testSteps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");
				
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
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to verify new account reservation for locked date cannot be created", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		paymentType = "MC"; 
		String cardExpDateWithLatestFormat = "3/23";

			try {
				
				getTestSteps.clear();
				getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver, checkInDates.get(1));
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_Adults(driver, testSteps, maxAdult);
				reservationPage.enter_Children(driver, testSteps, maxPerson);
				reservationPage.select_Rateplan(driver, testSteps, ratePlan,"");
				reservationPage.clickOnFindRooms(driver, testSteps);
				
				reservationPage.select_Room(driver, testSteps, roomClassName, "Yes", account);
				reservationPage.clickNext(driver, testSteps);									
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDateWithLatestFormat);
	
				reservationPage.SelectReferral(driver, referral);
				
				reservationPage.clickBookNow(driver, testSteps);
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				
				reservationPage.get_ReservationStatus(driver, testSteps);
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				
				testSteps.add("Verify date format for card expiry automatically change to 'mm/yy' from 'm/yy'");
				String getExpDate = homePage.getExpiryDate(driver);
				//Utility.verifyEquals(getExpDate, ESTTimeZone.reformatDate(cardExpDate, "m/yy", "mm/yy"), testSteps);
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");
				
				
				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : "  + reservationNumber);
				app_logs.info("Searched and opened reservation with number : "  + reservationNumber);

				reservationPage.changeReservationStatus(driver, "Confirmed", "", testSteps);
				
				reservationPage.verifyReservationStatusStatus(driver, testSteps, "Confirmed");
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");
				statusCode.add(4, "1");
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to verify card date format in account reservation", testName, "VerifyCardExpDateFormat", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to verify card date format in account reservation", testName, "VerifyCardExpDateFormat", driver);
				} else {
					Assert.assertTrue(false);
				}
			}




	//	roomClassName = "Junior Suites";
	//	roomClassAbbrivation = "JS";
		int minStay = 2;
		int maxStay = 3;

		roomClassAbbrivation = RoomClassAbbrivation;

		rateName = RateName + Utility.getTimeStamp();
		rateDisplayName = DisplayName + Utility.getTimeStamp();
		
		try {
			Utility.refreshPage(driver);
			Wait.waitUntilPageIsLoaded(driver);
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848725' target='_blank'>"
					+ "<b>Verify No rate combination found when Departure date is extended when reservation is created with conditional rate.</a> =====");
	
			// Create Rate
			testSteps.add("<b> ************Create Rate/b>****************");
			
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
			getTestSteps = rate.SelectRatePlan(driver, militaryPlan);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxAdults(driver, MaxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxPersons(driver, MaxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterBaseAmount(driver, Amount);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalAdult(driver, MaxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalChild(driver, MaxPersons);
			testSteps.addAll(getTestSteps);

			rate.clickConditionalRate(driver, testSteps);
			rate.enterMinLengthOfStay(driver, testSteps, String.valueOf(minStay));
			rate.enterMaxLengthOfStay(driver, testSteps, String.valueOf(maxStay));
			
			getTestSteps.clear();
			getTestSteps = rate.EnterRateDisplayName(driver, rateDisplayName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRatePolicy(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDescription(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSeason(driver, AssociateSeason);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateRoomClass(driver, roomClassName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSource(driver, source);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.Save_DoneRate(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("New Rate '" + rateName + "' Created & Verified ");
			app_logs.info("New Rate '" + rateName + "' Created & Verified");
			
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		checkInDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
		checkOutDate = Utility.getNextDate(minStay - 1, "MM/dd/yyyy", timeZone);
		try {
			testSteps.add("===== <b>Verify 'No Rate Combination Error' when min stay condition is not satisfied.</a> =====");
	
			testSteps.add("CREATING 'SINGLE' RESERVATION");
			app_logs.info("CREATING 'SINGLE' RESERVATION");
		
			reservationPage.click_NewReservation(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkOutDate);
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, militaryPlan,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			
			reservationPage.verifyNoRateCombinationMessage(driver, testSteps, true);
			
			testSteps.add("===== <b>Verify 'No Rate Combination Error' is not showing when min stay condition is satisfied.</a> =====");
			
			checkOutDate = Utility.getNextDate(minStay, "MM/dd/yyyy", timeZone);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkOutDate);
			testSteps.addAll(getTestSteps);
			reservationPage.clickOnFindRooms(driver, testSteps);
			
			reservationPage.verifyNoRateCombinationMessage(driver, testSteps, false);
			
			testSteps.add("===== <b>Verify 'No Rate Combination Error' is showing when max stay condition is not satisfied.</a> =====");

			checkOutDate = Utility.getNextDate(maxStay + 1, "MM/dd/yyyy", timeZone);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkOutDate);
			testSteps.addAll(getTestSteps);

			reservationPage.clickOnFindRooms(driver, testSteps);

			reservationPage.verifyNoRateCombinationMessage(driver, testSteps, true);

			testSteps.add("===== <b>Verify 'No Rate Combination Error' is not showing when max stay condition is satisfied.</a> =====");
			
			checkOutDate = Utility.getNextDate(maxStay, "MM/dd/yyyy", timeZone);
		
			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkOutDate);
			testSteps.addAll(getTestSteps);
			reservationPage.clickOnFindRooms(driver, testSteps);

			reservationPage.verifyNoRateCombinationMessage(driver, testSteps, false);
					
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");		

			statusCode.add(5, "1");
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify 'No Rate Combination Found' for conditional rateplan", testName, "VerifyConditionalPlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify 'No Rate Combination Found' for conditional rateplan", testName, "VerifyConditionalPlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		rateName = "IntervalRatePlan";
		String tempIntervalRate = rateName;
		rateName = rateName + Utility.getTimeStamp();
		rateDisplayName = rateName + Utility.getTimeStamp();
		try {
			// Create Rate
			testSteps.add("<b> ************Create Rate/b>****************");
			
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
			getTestSteps = rate.SelectRatePlan(driver, militaryPlan);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxAdults(driver, MaxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxPersons(driver, MaxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterBaseAmount(driver, Amount);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalAdult(driver, MaxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalChild(driver, MaxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDisplayName(driver, rateDisplayName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRatePolicy(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDescription(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSeason(driver, AssociateSeason);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateRoomClass(driver, roomClassName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSource(driver, source);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.Save_DoneRate(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("New Rate '" + rateName + "' Created & Verified ");
			app_logs.info("New Rate '" + rateName + "' Created & Verified");
			
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	
		try {
			// Delete created records for the script
			testSteps.add("<b>*****************Deleting created Records for Script********************</b>");
			navigation.Inventory(driver);
			testSteps.add("Navigate to Inventory");
			navigation.Rate(driver);
			testSteps.add("Navigate to Rate");
			rate.deleteRates(driver, tempRate1);
			testSteps.add("Rates Deleted starting with name: " + "<b>" + tempRate1 + "</b>");
			statusCode.add(6, "1");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("noRateCombForConditionalRate", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
