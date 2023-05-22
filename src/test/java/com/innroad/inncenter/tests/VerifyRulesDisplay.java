package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyRulesDisplay extends TestCore{
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
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData")
	public void verifyRulesDisplay(String testCaseID,String delim,String caseType, String ratePlanName,
			String roomClassName, String roomClassAbb) throws  InterruptedException, IOException, Exception {
		
		Utility.DELIM = "\\" + delim;

		if(!Utility.validateString(testCaseID)) {
			caseId.add("848138");
			statusCode.add("4");

		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		
		//CustomFolio
		testName = "VerifyRulesDisplay -" + caseType;
		testDescription = "Verify Rules Display</br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848138' target='_blank'>"
				+ "Click here to open TestRail: C848138</a>";
		testCatagory = "RulesVerification";
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		Account CreateTA = new Account();
		ReservationHomePage homePage = new ReservationHomePage();
		Tapechart tapeChart = new Tapechart();
		Groups group = new Groups();
		Rules rules = new Rules();
		
		String randomString = Utility.GenerateRandomString();
		
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
		
		String minimumStay = "Minimum Stay";
		String noCheckIn = "No Check In";
		String noCheckOut = "No Check Out";
		String misStayRule = "2";
		String ruleName = null, ruleType = null;
		HashMap<String, String> reservationNumberMap = new HashMap<>();
		boolean isRuleShowing = false;
		String season = "All Year Season";

		String defaultDateFormat = "MM/dd/yyyy";
		String timeZone = "US/Eastren";
		String checkInDate=Utility.getCurrentDate(defaultDateFormat, timeZone);
		String checkOutDate=Utility.getNextDate(2, defaultDateFormat);
		app_logs.info("checkInDate : "  + checkInDate);
		app_logs.info("checkOutDate : " + checkOutDate);
		int nights = Utility.getNumberofDays(checkInDate, checkOutDate);
		app_logs.info("getNights :  "  +nights);

		ArrayList<String> checkInDates = new ArrayList<>();
		checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "MM/dd/yyyy"));
		HashMap<String, Boolean> getDaysToCheck = new HashMap<>();
		if(caseType.equalsIgnoreCase("VerifyMinStayRule")) {
			ruleName = minimumStay + randomString;
			ruleType = minimumStay;
			isRuleShowing = true;
		}else if(caseType.equalsIgnoreCase("VerifyNoCheckinRule")) {
			ruleName = noCheckIn + randomString;			
			ruleType = noCheckIn;
			isRuleShowing = true;
		}else if(caseType.equalsIgnoreCase("VerifyNoCheckOutRule")) {
			ruleName = noCheckOut + randomString;					
			ruleType = noCheckOut;
			isRuleShowing = true;
		}else if(caseType.equalsIgnoreCase("VerifyMinStayRuleSatisfied")) {
			ruleName = minimumStay + randomString;
			ruleType = minimumStay;
			isRuleShowing = false;			
			checkInDates.add(1, Utility.parseDate(Utility.getDatePast_FutureDate(Integer.parseInt(minimumStay) + 1), "MM/dd/yyyy", "MM/dd/yyyy"));
		}else if(caseType.equalsIgnoreCase("VerifyNoCheckInRuleSatisfied")) {
			ruleName = noCheckIn + randomString;			
			ruleType = noCheckIn;
			isRuleShowing = false;			
			String getDays = ESTTimeZone.reformatDate(checkInDates.get(0), defaultDateFormat, "EEE");
			printString("getDays : " + getDays);
			getDaysToCheck.put(getDays, true);
			getDays = ESTTimeZone.reformatDate(checkInDates.get(1), defaultDateFormat, "EEE");
			printString("getDays : " + getDays);
			getDaysToCheck.put(getDays, true);
			getDays = ESTTimeZone.reformatDate(checkInDates.get(2), defaultDateFormat, "EEE");
			printString("getDays : " + getDays);
			getDaysToCheck.put(getDays, true);
			
		}else if(caseType.equalsIgnoreCase("VerifyNoCheckOutRuleSatisfied")) {
			ruleName = noCheckOut + randomString;			
			ruleType = noCheckOut;
			isRuleShowing = false;			
			String getDays = ESTTimeZone.reformatDate(checkInDates.get(0), defaultDateFormat, "EEE");
			printString("getDays : " + getDays);
			getDaysToCheck.put(getDays, true);
			getDays = ESTTimeZone.reformatDate(checkInDates.get(1), defaultDateFormat, "EEE");
			printString("getDays : " + getDays);
			getDaysToCheck.put(getDays, true);
			getDays = ESTTimeZone.reformatDate(checkInDates.get(2), defaultDateFormat, "EEE");
			printString("getDays : " + getDays);
			getDaysToCheck.put(getDays, true);
			
		}

		printString("getDaysToCheck : " + getDaysToCheck);
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			//loginRateV2(driver);
			loginClinent3281(driver);
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
	
		
		//Case : 824566
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/824566' target='_blank'>"
					+ "<b>Display of rules</b> </a> =====");
			
			navigation.Inventory(driver, testSteps);
			testSteps.add("Deleting all rules");
			
			rules.deleteNRules(driver);
			testSteps.add("All rules deleted successfully");
			
			testSteps.add("Creating new rule with name (" + ruleName + ") and type (" + ruleType + ").");
			rules.create_Rule(driver, ruleName, ruleType, ruleName, misStayRule, roomClassName, source, ratePlanName, season, "Active", getDaysToCheck);
			testSteps.add("New rule with name (" + ruleName + ") and type (" + ruleType + ") created successfully.");

			testSteps.addAll(rules.openRule(driver, ruleName));
			try {
				rules.verifyRulesDetailsPageSections(driver, testSteps);
				
			}catch (Error e) {
				testSteps.add(e.toString());
			}
		
			navigation.cpReservationBackward(driver);
			testSteps.add("Back to reservations page.");
			
			statusCode.add(0, "1");
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
		
		String isAssign = "Yes";
	
		try {

			testSteps.add("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
			app_logs.info("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
		
			reservationPage.click_NewReservation(driver, testSteps);									

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			
			homePage.verifyRulesPopupWhileSelectingRoom(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
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
			
			if(caseType.toLowerCase().contains("quote")) {
				reservationPage.click_Quote(driver, testSteps);					
			}else {
				reservationPage.clickBookNow(driver, testSteps);
			}
			reservationNumber = "";
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
			reservationNumberMap.put("single",reservationNumber);
			
			reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
			
			if(caseType.toLowerCase().contains("quote")) {
				reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
			}
			
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			
			roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");
			statusCode.add(1, "1");
			
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

		String tempCheckin = checkInDates.get(0) + "|" + checkInDates.get(1);
		String tempCheckOut = checkInDates.get(1) + "|" + checkInDates.get(2);
		String tempRatePlan = ratePlanName + "|" + ratePlanName;
		String tempMaxAdults = maxAdults + "|" + maxAdults;
		String tempMaxChildren = maxChildren + "|" + maxChildren;
		String tempRoomClassName = roomClassName + "|" + roomClassName;
		String tempFirstName = guestFirstName + "|" + guestFirstName + randomString;
		String tempLastName = guestLastName + "|" + guestLastName + randomString;
		String tempSalutation = salutation + "|" + salutation;
		String isSplitRes = "No";
		String tempPhoneNumber = phoneNumber + "|" + phoneNumber;

		testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/824567' target='_blank'>"
				+ "<b>Display of rules without rule satisfied</b> </a> =====");
	
		//824567
	
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
					reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, "");
					testSteps.addAll(getTestSteps);
					
				}
				
				reservationPage.clickOnFindRooms(driver, testSteps);
				homePage.verifyRulesPopupWhileSelectingRoom(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");					

				Utility.refreshPage(driver);
				Wait.waitforPageLoad(30, driver);
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
					reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, "");
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

				if(caseType.toLowerCase().contains("quote")) {
					reservationPage.click_Quote(driver, testSteps);					
				}else {
					reservationPage.clickBookNow(driver, testSteps);
				}
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberMap.put("mrb",reservationNumber);
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				if(caseType.toLowerCase().contains("quote")) {
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
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new account", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new account", testName, "CreateReservation", driver);

				} else {
					Assert.assertTrue(false);
				}
			}
	
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
				getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_Adults(driver, testSteps, maxAdults);
				reservationPage.enter_Children(driver, testSteps, maxChildren);
				reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
				reservationPage.clickOnFindRooms(driver, testSteps);
				isAssign = "Yes";
				
				homePage.verifyRulesPopupWhileSelectingRoom(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
				
				reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
				reservationPage.clickNext(driver, testSteps);									
	
				try {
					reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
					
				}catch (Exception e) {
				}
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);
	
				reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
	
				reservationPage.SelectReferral(driver, referral);
				
				if(caseType.toLowerCase().contains("quote")) {
					reservationPage.click_Quote(driver, testSteps);					
				}else {
					reservationPage.clickBookNow(driver, testSteps);
				}
				reservationNumber = "";
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberMap.put("account",reservationNumber);
				
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				if(caseType.toLowerCase().contains("quote")) {
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

		try {

			testSteps.add("<b> ===== CREATING 'GROUP' RESERVATION ===== </b>");
			app_logs.info("<b> ===== CREATING 'GORUP' RESERVATION ===== </b>");
		
			group.clickOnNewReservationButtonFromGroup(driver, testSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			isAssign = "Yes";
			
			homePage.verifyRulesPopupWhileSelectingRoom(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
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
			
			if(caseType.toLowerCase().contains("quote")) {
				reservationPage.click_Quote(driver, testSteps);					
			}else {
				reservationPage.clickBookNow(driver, testSteps);
			}
			reservationNumber = "";
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
			reservationNumberMap.put("single",reservationNumber);
			
			reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
			
			if(caseType.toLowerCase().contains("quote")) {
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

			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDates.get(0), checkInDates.get(1), timeZone, testSteps);
			tapeChart.enterAdult(driver, maxAdults, testSteps);
			if(ratePlanName.equalsIgnoreCase("Best Available")) {
				tapeChart.selectRatePlan(driver, "-- Best Available -- ", testSteps);								
			}else{
				tapeChart.selectRatePlan(driver, ratePlanName, testSteps);								
							
			}
			tapeChart.clickSearchButton(driver, testSteps);
			
			homePage.verifyRuleBrokenPopupAfterClickingOnAvailableSlot(driver, roomClassAbb, ruleName, ruleName, isRuleShowing, testSteps);

			if(!isRuleShowing) {
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");					
				Utility.refreshPage(driver);
				Wait.waitforPageLoad(20, driver);

				reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDates.get(0), checkInDates.get(1), timeZone, testSteps);
				tapeChart.enterAdult(driver, maxAdults, testSteps);
				if(ratePlanName.equalsIgnoreCase("Best Available")) {
					tapeChart.selectRatePlan(driver, "-- Best Available -- ", testSteps);								
				}else{
					tapeChart.selectRatePlan(driver, ratePlanName, testSteps);								
								
				}
				tapeChart.clickSearchButton(driver, testSteps);
					
			}
			
			app_logs.info("==========SELECT ROOM==========");
			testSteps.add("==========SELECT ROOM==========");
			tapeChart.clickAvailableSlot(driver, roomClassAbb);
			testSteps.add("Click available room of Room Class '" + roomClassAbb + "'");
			app_logs.info("Click on available room of Room Class '" + roomClassAbb + "'");
			Wait.wait10Second();
			testSteps.add("New Reservation page is opened");
			app_logs.info("New Reservation Page is Opened");

			String room = reservationPage.getRoomSelectedFromTapeChart(driver, testSteps);
			Assert.assertTrue(room.contains(roomClassName), "Failed: Room is not selected");
			testSteps.add("Verified Room Class is '" + roomClassName + "'");
		
			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);
	
			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);
	
			reservationPage.SelectReferral(driver, referral);
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
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
			statusCode.add(2, "1");
			
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
		comments = " Verified advance deposit realization for " + caseType;
		statusCode.add(0, "1");
		statusCode.add(1, "1");
		statusCode.add(2, "1");
		statusCode.add(3, "1");
		statusCode.add(4, "1");
		statusCode.add(5, "1");

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
		return Utility.getData("VerifyRulesDisplay", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
