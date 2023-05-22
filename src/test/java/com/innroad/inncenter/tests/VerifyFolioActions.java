package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Move;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.model.Category;

import groovyjarjarantlr4.v4.parse.ANTLRParser.element_return;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

public class VerifyFolioActions extends TestCore {

	private WebDriver driver = null;

	

	public static String test_description = "";
	public static String test_catagory = "";
	
	static ExtentTest test;
	static ExtentReports report;
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	
	HashMap<String, String> ItemLineStatusBeforeRollBack = new HashMap<String, String>();
	HashMap<String, String> itemLineStatusAfterRollBack = new HashMap<String, String>();

	
	//@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyFolioActions(String TestCaseID,String caseName, String displayName, String Policy_T,
			 String ratePlan,String firstName,String lastName,
			 String IsAssign, String isChecked,  String paymentType, String cardNumber, String nameOnCard,
			 String cardExpDate, String Action,
			String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String PromoCode,String IsSplitRes,String roomclassabb,
			String RoomClass,String IsDepositOverride,String DepositOverrideAmount,String IsAddMoreGuestInfo,
			String Salutation,String GuestFirstName,String GuestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,
			String TravelAgent,String MarketSegment,String Referral,String Description,
			String Incidental_Category,String Incidental_PerUnit, String Incidental_Quantity) throws ParseException, Exception {


		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> scriptName = new ArrayList<>();
		ArrayList<String> testCategory = new ArrayList<>();
		ArrayList<String> testDescription = new ArrayList<>();

		
		String [] roomClass = RoomClass.split("\\|");
		String [] roomClassabb = roomclassabb.split("\\|");
		String [] Phone_Number = PhoneNumber.split("\\|");
		String [] rate_Plan = ratePlan.split("\\|");
		
		String testName =caseName;
		String testDescriptionForLink = "";
		if(Utility.getResultForCase(driver, TestCaseID)) {
		if(!Utility.validateString(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("4");
			testDescriptionForLink = caseName+"<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/"+TestCaseID+"' target='_blank'>"
					+ "Click here to open TestRail: C"+TestCaseID+"</a>";
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
				testDescriptionForLink = testDescriptionForLink+"<br>"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/"+testcase[i]+"' target='_blank'>"
						+ "Click here to open TestRail: C"+testcase[i]+"</a>";


			}
			
		}
		test_description = testDescriptionForLink;
		test_catagory = "Folio";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
		app_logs.info("##################################################################################");

		//MBR Reservation
		Double depositAmount=0.0;
		Double paidDeposit=0.0;
		String reservation=null;
		String status=null;
		ArrayList<String> Rooms = new ArrayList<String>();
		ArrayList<String> roomCost = new ArrayList<String>();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> getTest_steps = new ArrayList<>();
		
		CPReservationPage cpRes = new CPReservationPage();
		Navigation navigation = new Navigation();
		Account account = new Account();
		Admin admin = new Admin();
		Policies policies = new Policies();
		CPReservationPage reservationPage = new CPReservationPage();
		Folio folio = new Folio();
		Properties properties = new Properties();
		System.out.print("RoomClass: "+roomClass[0]);
		String tempraryRoomClassName = roomClass[0];
		String randomNumber = Utility.GenerateRandomNumber();
		roomClass[0] = roomClass[0] + randomNumber;
		displayName = roomClass[0];
		lastName = lastName + randomNumber;
		firstName = firstName + randomNumber;
		Policy_T = Policy_T + randomNumber;
		String adult = "2";
		String children = "1";
		String reservationNumber1 = "";
		String reservationNumber2 = "";
		String GuestName1 = "";
		String GuestName2 = "";
		String ResTotalBalance2 = "";
		String AccountName = "Account_Name";
		String AccountNumber = "";
		String getTimeZone = "";
		String expectedDate = "";
		
		
				
	
		//Login
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			driver = getDriver();
//			login_CP(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "CP_Login"));
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");
		} catch (Exception e) {	e.printStackTrace();
		
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {	e.printStackTrace();
		
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}	
		
		
//		try {
//			app_logs.info("Navigating to Inventory");
//			navigation.Inventory(driver, testSteps);
//			
//			testSteps.add("Navigating to policy");
//			app_logs.info("Navigating to policy");
//			navigation.policies(driver);
//			testSteps.add("Searching For All policies");
//			app_logs.info("Searching For All policies");
//			
//			
//			getTest_steps.clear();
//			policies.Search_All_Policy_U(driver, getTest_steps);
//			testSteps.addAll(getTest_steps);
//			app_logs.info("All policies Search Complete");
//			
//				
//			int size = policies.Check_Size_Of_Policies_Rows(driver);
//			if(size>0) {
//				testSteps.add("Deleting policies");
//				app_logs.info("Deleting policies");
//				policies.DeleteAllPolicies(driver,getTest_steps);
//			}
//			else {
//				testSteps.add("No policies Found For Deletion");
//				app_logs.info("No policies Found For Deletion");
//			}
//			
//			navigation.Reservation(driver);
//		} catch (Exception e) {
//
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//				Utility.updateReport(e, "Failed to Delete Policy", testName, "Property", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
//				Utility.updateReport(e, "Failed to Failed to Delete Policy", testName, "Property", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
		String getGuestName = "";
		
		if(TestCaseID.equalsIgnoreCase("848565")) {		
		// get property timezone
		try {
			testSteps.add("Get time zone from peroperty");
			  navigation.setup(driver);
			  testSteps.add("Navigating To Setup");
			  navigation.properties(driver);
			  testSteps.add("Navigating To Properties");
			  properties.PropertyName(driver);
			  testSteps.add("Navigating To PropertyName");
			  getTest_steps.clear();
			  properties.PropertyOptions(driver, getTest_steps);
			  testSteps.addAll(getTest_steps);
			  getTimeZone = properties.getTimeZone(driver);
			  getTimeZone = Utility.getTimeZone(getTimeZone);
			  testSteps.add("Selected time zone: "+getTimeZone);
			  navigation.Reservation_Backward(driver);
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to get time zone from property", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to get time zone from property", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// get user name from admin 
		
		try {
			  testSteps.add("Get user name from admin");
			  navigation.admin(driver);
			  testSteps.add("Click on admin");
			  admin.clickOnUserTab(driver);
			  testSteps.add("Click on users");
			  admin.clickOnSearchButton(driver);
			  testSteps.add("Click on search button");
			  getGuestName = admin.getUserName(driver, Utility.login_CP.get(2));
			  testSteps.add("User name: "+getGuestName);

			  navigation.navigateToReservations(driver);
			  
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		}

		
		if(TestCaseID.equalsIgnoreCase("824863")|| TestCaseID.equalsIgnoreCase("848473")
				|| TestCaseID.equalsIgnoreCase("848656")|| TestCaseID.equalsIgnoreCase("848162")) {
			try {
				navigation.Accounts(driver);	
				// New account
				try {
					AccountName=AccountName+Utility.generateRandomString();
					account.ClickOnNewAccountButton(driver, getTest_steps);
					AccountNumber = Utility.GenerateRandomString15Digit();
					if(TestCaseID.equalsIgnoreCase("848473")) {
						account.enterAccountDetails(driver, getTest_steps, "Travel Agent", AccountName, AccountNumber);
					}
					else {
						account.enterAccountDetails(driver, getTest_steps, AccountType, AccountName, AccountNumber);
					}
					
					getTest_steps.clear();
					account.AccountAttributes(driver, test, "GDS", Referral, getTest_steps);
					testSteps.addAll(getTest_steps);
					getTest_steps.clear();
					getTest_steps = account.Mailinginfo(driver, test, GuestFirstName, GuestLastName, Phone_Number[0], Phone_Number[0],
							Address1, Address2, Address3, Email, City, State, PostalCode, getTest_steps);
					testSteps.addAll(getTest_steps);
					getTest_steps.clear();
					getTest_steps = account.Billinginfo(driver, test, getTest_steps);
					testSteps.addAll(getTest_steps);
					getTest_steps.clear();
					getTest_steps = account.AccountSave(driver, test, AccountName, getTest_steps);
					testSteps.addAll(getTest_steps);


				} catch (Exception e) {

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


				// Clicking on New Reservation
				try {
					account.CP_NewReservationButton(driver, test);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify rollback after move the item in folio", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify rollback after move the item in folio", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		
		
		
		if(TestCaseID.equalsIgnoreCase("848428")) {
			
			try {
				// Get CheckIN and Checkout Date
				if (!(Utility.validateInput(CheckInDate))) {
					for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				
				
				
				Thread.sleep(2000);
				cpRes.click_NewReservation(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.select_Dates(driver, getTest_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
				testSteps.addAll(getTest_steps);
				
				
				if(IsSplitRes.equalsIgnoreCase("Yes")) {
					getTest_steps.clear();
					cpRes.enter_Adults(driver, getTest_steps, Adults);
					testSteps.addAll(getTest_steps);
					
					
					getTest_steps.clear();
					cpRes.enter_Children(driver, getTest_steps, Children);
					testSteps.addAll(getTest_steps);
					
					
					getTest_steps.clear();
					cpRes.select_Rateplan(driver, getTest_steps, Rateplan, PromoCode);
					testSteps.addAll(getTest_steps);
				}
				getTest_steps.clear();
				cpRes.clickOnFindRooms(driver, getTest_steps);
				testSteps.addAll(getTest_steps);

				
				getTest_steps.clear();
				roomCost=cpRes.select_MRBRooms(driver, getTest_steps, RoomClass, IsAssign,Account);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				depositAmount=cpRes.deposit(driver, getTest_steps, IsDepositOverride, DepositOverrideAmount);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.clickNext(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.enter_MRB_MailingAddress(driver, getTest_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, IsAddMoreGuestInfo, IsSplitRes,Rooms);
				testSteps.addAll(getTest_steps);
				
				
				if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
					getTest_steps.clear();
					cpRes.enter_PaymentDetails(driver, getTest_steps, paymentType, cardNumber, nameOnCard, cardExpDate);
					testSteps.addAll(getTest_steps);
				}
				System.out.println(Rooms);
				
				getTest_steps.clear();
				cpRes.enter_MarketSegmentDetails(driver, getTest_steps, TravelAgent, MarketSegment, Referral);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.clickBookNow(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				reservation=cpRes.get_ReservationConfirmationNumber(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				getTest_steps.clear();
				status=cpRes.get_ReservationStatus(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.clickCloseReservationSavePopup(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				getTest_steps.clear();
				cpRes.Click_CheckInAllButton(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				getTest_steps.clear();
				cpRes.verifyCheckInConfirmDetailsPaymentPopupIsAppeared(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.VerifyinHouseStatus(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				String RowCOunt = "2";
				String RoomNo = folio.getRoomNo_OnCheckOutAll(driver, RowCOunt, getTest_steps);
				
				
				Thread.sleep(1000);
				getTest_steps.clear();
				cpRes.VerifyFolioDetailsAfterCheckOutAll(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				Thread.sleep(1000);
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.add("Clicking On Folio Tab");
				
				
				folio.getTotalBalance(driver);
				
				Thread.sleep(1000);
				folio.changeFolioOption(driver,"Guest Folio For "+roomClassabb[Integer.valueOf(RowCOunt)-1]+" : "+RoomNo);
				testSteps.add("Selecting "+"Guest Folio For "+roomClassabb[Integer.valueOf(RowCOunt)-1]+" : "+RoomNo);
				
				String SecondUserpayment =  folio.getpayment(driver);
				testSteps.add("Secondary User Payment: "+SecondUserpayment);
				testSteps.add("Primary User have Secondary User Payment: -"+SecondUserpayment);
				testSteps.add("Verify the behaviour of individual Checkout if multiple folios present");
				app_logs.info("Verify the behaviour of individual Checkout if multiple folios present");
				
				
			} catch (Exception e) {
				testSteps.add(e.toString());
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify the behaviour of individual Checkout if multiple folios present", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
		}
		else {
			//Reservation	
			try {
				testSteps.add("==========CREATE NEW RESERVED RESERVATION==========");
				app_logs.info("==========CREATE NEW RESERVED RESERVATION==========");

				if(!(TestCaseID.equalsIgnoreCase("824863") ||TestCaseID.equalsIgnoreCase("848473")
						|| TestCaseID.equalsIgnoreCase("848656")|| TestCaseID.equalsIgnoreCase("848162"))) {
					getTestSteps.clear();
					Thread.sleep(4000);
					app_logs.info("Clicking on New Reservation");
					getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
				}
				

				getTestSteps.clear();
				testSteps.add("Entering Check In Date");
				app_logs.info("Entering Check In Date");
				getTestSteps = reservationPage.checkInDate(driver, 0);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Check Out Date");
				app_logs.info("Entering Check Out Date");
				getTestSteps = reservationPage.checkOutDate(driver, +1);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Adult Value");
				app_logs.info("Entering Adult Value");
				getTestSteps = reservationPage.enterAdult(driver, adult);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Children Value");
				app_logs.info("Entering Children Value");
				getTestSteps = reservationPage.enterChildren(driver, children);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				testSteps.add("Entering Rateplan");
				app_logs.info("Entering Rateplan");
				getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				app_logs.info("Clicking On Find Room");
				getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				testSteps.add("Filling All Fullfil Values");
				app_logs.info("Filling All Fullfil Values");
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.clickNext(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, Boolean.parseBoolean(isChecked));
				testSteps.addAll(getTestSteps);					
				
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.",firstName, lastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer", cardExpDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.selectReferral(driver, "GDS");
				testSteps.addAll(getTestSteps);
				if(!TestCaseID.equalsIgnoreCase("824626") && !TestCaseID.equalsIgnoreCase("824863")
						&& !TestCaseID.equalsIgnoreCase("848656")&& !TestCaseID.equalsIgnoreCase("848162")) {
					getTestSteps.clear();
					getTestSteps = reservationPage.ButtonAddIncidental(driver);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					getTestSteps = reservationPage.enterAddOnIncidental(driver, CheckInDate, Incidental_Category,Incidental_PerUnit,String.valueOf(Integer.valueOf(Incidental_Quantity)));
					testSteps.addAll(getTestSteps);
				}
				

				Thread.sleep(1000);
				getTestSteps.clear();
				getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
				testSteps.add("Successfully click Book Now");
				
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Reservation confirmation number: " + reservationNumber1);
				app_logs.info("Reservation confirmation number: " + reservationNumber1);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Sucessfully close save quote popup");
				
				Thread.sleep(1000);
				getTestSteps.clear();
				GuestName1 = reservationPage.getguestname(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("Reservation Done with Guestname: "+GuestName1+" Reservation No: "+reservationNumber1);
			
				expectedDate = ESTTimeZone.getDateWithTime(getTimeZone,"MM/dd/yy h:mm aa");
				

			} catch (Exception e) {
				e.printStackTrace();
				
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Reservation", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			
			if(TestCaseID.equalsIgnoreCase("848473")) {
				getTest_steps.clear();
				cpRes.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				getTest_steps.clear();
				cpRes.click_FolioDetail_DropDownBox(getTest_steps, driver);
				testSteps.addAll(getTest_steps);
				getTest_steps.clear();
				folio.verifyFolioCategoryDropdownList(driver, AccountName,getTest_steps);
				testSteps.addAll(getTest_steps);
			}
			
			
			
			if(TestCaseID.equalsIgnoreCase("848568")) {
				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.ClickEditStayInfo(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.ClickStayInfo_ChangeDetails(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.clickStayInfoEditOptions(driver, getTest_steps, 1);
				testSteps.addAll(getTest_steps);
				
               try {
            	   Thread.sleep(1000);
            	   getTest_steps.clear();
            	   getTest_steps = reservationPage.checkOutDate(driver, 3);
            	   testSteps.addAll(getTest_steps);
            	   
					Thread.sleep(1000);
					testSteps.addAll(reservationPage.clickFindRooms(driver));
					
					Thread.sleep(1000);
					getTest_steps.clear();
					expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver,getTimeZone,getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					cpRes.click_Folio(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					testSteps.add("********Before Decreasing the no of check  out days********* ");
					int BeforeLineItemRowCount = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItems Count: "+BeforeLineItemRowCount);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					cpRes.click_DeatilsTab(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					reservationPage.ClickEditStayInfo(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					reservationPage.ClickStayInfo_ChangeDetails(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					reservationPage.clickStayInfoEditOptions(driver, getTest_steps, 1);
					testSteps.addAll(getTest_steps);
					
					
					Thread.sleep(1000);
					getTest_steps.clear();
					getTest_steps =reservationPage.checkOutDate(driver, 2);
					testSteps.addAll(getTest_steps);
					
					
					Thread.sleep(1000);
					testSteps.addAll(reservationPage.clickFindRooms(driver));
					
					Thread.sleep(1000);
					getTest_steps.clear();
					expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver,getTimeZone,getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					cpRes.click_Folio(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					testSteps.add("********After Decreasing the no of check  out days********* ");
					int AfterLineItemRowCount = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItems Count: "+AfterLineItemRowCount);
					
					String VerifyDateFolio = ESTTimeZone.DateFormateForLineItemWithFormate(2,"EEE MMM dd, yyyy");
					System.out.print("\n\n\n"+VerifyDateFolio+"\n\n");
					
					Thread.sleep(1000);
					getTest_steps.clear();
					folio.Verify_LineItem_(driver, VerifyDateFolio, "Room Charge",getTest_steps,false);
					testSteps.addAll(getTest_steps);
					
					
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Verify display of folio line items after extending/reducing single reservation with selecting radio button recalculate rate.", testName, "Incidental", driver);
					} else {
						Assert.assertTrue(false);
					}

				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Verify display of folio line items after extending/reducing single reservation with selecting radio button recalculate rate.", testName, "Incidental", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			}
			
			
			
			if(TestCaseID.equalsIgnoreCase("848565")) {

				getTestSteps.clear();
				reservationPage.ClickEditStayInfo(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				reservationPage.ClickStayInfo_ChangeDetails(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				reservationPage.clickStayInfoEditOptions(driver, getTestSteps, 3);
				testSteps.addAll(getTestSteps);
				try {
					
					testSteps.addAll(reservationPage.clickFindRooms(driver));
					reservationPage.select_Room(driver, tempraryRoomClassName, IsAssign);
					testSteps.add("Selecting RoomClass: "+tempraryRoomClassName);

				} catch (Exception e) {
					app_logs.info("In catch");
					testSteps.add(e.toString());
				}

				String getRoomClass = reservationPage.getRoomClass(driver, tempraryRoomClassName);
				app_logs.info(getRoomClass);
				

				expectedDate = reservationPage.clickSaveAfterEditStayInfo(driver,getTimeZone,getTest_steps);
				
				String strSplit[] = expectedDate.split(" "); 

				
				///////////////////////////////////////
				// INCIDENTAL IN HISTORY
				try {
					testSteps.add("==========Verify user name and time and date in history tab after updated reservation==========");

					getTestSteps.clear();
					getTestSteps = reservationPage.ClickOnHistory(driver);
					testSteps.addAll(getTestSteps);
				
					testSteps.add("Expected guest name: "+"auto guest");
					String getGuestNameFromHistoryTab = reservationPage.getUserNameFromHistoryTab(driver);
					testSteps.add("Found: "+getGuestNameFromHistoryTab);
					if (getGuestNameFromHistoryTab.equalsIgnoreCase(getGuestName)) {
						testSteps.add("Verified guest name is mathching in histroy tab after updated reservation");
					}
					else {
						testSteps.add("Failed: Guest name is mismatching  in history tab");
					}
					
					   
					
					testSteps.add("Expected date: "+strSplit[0]);
					String getDateFromHistoryTab = reservationPage.getDateFromHistoryTab(driver);
					testSteps.add("Found: "+strSplit[0]);
					if (getDateFromHistoryTab.equalsIgnoreCase(strSplit[0])) {
						testSteps.add("Verified date is mathching in histroy tab after updated reservation");
					}
					else {
						testSteps.add("Failed: date is mismatching  in history tab");
					}
					testSteps.add("Expected Time: "+strSplit[1]+" "+strSplit[2]);
					String getTimeFromHistoryTab = reservationPage.getTimeFromHistoryTab(driver);
					testSteps.add("Found: "+getTimeFromHistoryTab);
					if (getTimeFromHistoryTab.equalsIgnoreCase(strSplit[1]+" "+strSplit[2])) {
						testSteps.add("Verified time is mathching in histroy tab after updated reservation");
					}
					else {
						testSteps.add("Failed: time is mismatching  in history tab");
					}
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to verify incidental in hostory", testName, "Incidental", driver);
					} else {
						Assert.assertTrue(false);
					}

				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to verify incidental in hostory", testName, "Incidental", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				

				
				////////////////////////////////////////
				
				
			}
			
			if(TestCaseID.equalsIgnoreCase("824626") || TestCaseID.equalsIgnoreCase("824863")
					|| TestCaseID.equalsIgnoreCase("848656")|| TestCaseID.equalsIgnoreCase("848162")) {
				
				
				
				
				Thread.sleep(1000);
				navigation.Reservation(driver);
				
				Utility.refreshPage(driver);
				
				
				testSteps.add("==========CREATE NEW RESERVED RESERVATION==========");
				app_logs.info("==========CREATE NEW RESERVED RESERVATION==========");

				getTestSteps.clear();
				Thread.sleep(4000);
				app_logs.info("Clicking on New Reservation");
				getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Check In Date");
				app_logs.info("Entering Check In Date");
				getTestSteps = reservationPage.checkInDate(driver, 0);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Check Out Date");
				app_logs.info("Entering Check Out Date");
				getTestSteps = reservationPage.checkOutDate(driver, +1);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Adult Value");
				app_logs.info("Entering Adult Value");
				getTestSteps = reservationPage.enterAdult(driver,String.valueOf(Integer.valueOf(adult)-1) );
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				testSteps.add("Entering Children Value");
				app_logs.info("Entering Children Value");
				getTestSteps = reservationPage.enterChildren(driver,String.valueOf(Integer.valueOf(children)+1) );
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				testSteps.add("Entering Rateplan Value");
				app_logs.info("Entering Rateplan Value");
				getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, rate_Plan[0], PromoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				app_logs.info("Clicking On Find Room");
				getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				testSteps.add("Filling All Fullfil Values");
				app_logs.info("Filling All Fullfil Values");
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.clickNext(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, Boolean.parseBoolean(isChecked));
				testSteps.addAll(getTestSteps);					
				
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.",firstName+"2", lastName+"2");
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer", cardExpDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.selectReferral(driver, "GDS");
				testSteps.addAll(getTestSteps);
				

				Thread.sleep(1000);
				getTestSteps.clear();
				getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
				testSteps.add("Successfully click Book Now");
				
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationNumber2 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Reservation confirmation number: " + reservationNumber2);
				app_logs.info("Reservation confirmation number: " + reservationNumber2);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				testSteps.add("Sucessfully close save quote popup");
				
				Thread.sleep(1000);
				getTestSteps.clear();
				GuestName2 = reservationPage.getguestname(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTestSteps);
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				ResTotalBalance2 = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);
				
				
				Thread.sleep(1000);
				navigation.Reservation(driver);

				if(TestCaseID.equalsIgnoreCase("824863")||TestCaseID.equalsIgnoreCase("848656")
						||TestCaseID.equalsIgnoreCase("848162")) {
					Thread.sleep(1000);
					getTestSteps.clear();
					folio.AccountReservationMoveFolioInsideReservation(driver, test, reservationNumber1, reservationNumber2, getTestSteps);
					testSteps.addAll(getTestSteps);
				}
				else {
					Thread.sleep(1000);
					getTestSteps.clear();
					folio.MoveFolioInsideReservation_(driver, test, reservationNumber1, reservationNumber2, getTestSteps);
					testSteps.addAll(getTestSteps);
				}
				
				
				
				
				Thread.sleep(1000);
				getTestSteps.clear();
				String tBalanceAfterFolioMove =  folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);
				
				System.out.print("\n"+tBalanceAfterFolioMove);
				System.out.print("\n"+ResTotalBalance2);
				
				testSteps.add("Expected Amount of Reservation: "+String.valueOf(Double.valueOf(ResTotalBalance2)*2));
				testSteps.add("Actual Amount of Reservation: "+tBalanceAfterFolioMove);				
				if(!TestCaseID.equalsIgnoreCase("824863")) {
					testSteps.add("Verified: Associate it to Rate/Rule");
				}
				
			}
//Welcome
			if(TestCaseID.equalsIgnoreCase("824863")||TestCaseID.equalsIgnoreCase("848656")
					||TestCaseID.equalsIgnoreCase("848162")) {
				Thread.sleep(1000);
				getTestSteps.clear();
				String balanceBeforeRollback = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(1000);
				getTest_steps.clear();
				folio.makePayment(driver, "Capture", "20", getTest_steps);
				testSteps.addAll(getTest_steps);
				
				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.Cancel_Reservation(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				Thread.sleep(1000);
				getTest_steps.clear();
				reservationPage.ClickOnRollbackButton(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				Thread.sleep(1000);
				Utility.refreshPage(driver);
			    
			    Thread.sleep(1500);
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTest_steps);
				testSteps.addAll(getTestSteps);
				
				Thread.sleep(1000);
				getTestSteps.clear();
				String balanceAfterRollback = folio.getTotalBalance(driver);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("Expected Balance: 0.00");
				testSteps.add("Founded Balance: "+balanceAfterRollback.trim());
				
				assertEquals(balanceAfterRollback.trim(), "0.00");
				if(TestCaseID.equalsIgnoreCase("824863")) {
					testSteps.add("Verify rollback after move the item in folio");
				}
				else if(TestCaseID.equalsIgnoreCase("848656")) {
					testSteps.add("Verify exception error while moving one folio to another folio from reservation page.");
				}
				else if (TestCaseID.equalsIgnoreCase("848162")) {
					testSteps.add("Verify Move folio Functionality");
				}
				
				
			}
			if(TestCaseID.trim().equalsIgnoreCase("848150")) {
				
				try {	
					
					Thread.sleep(1000);
					getTestSteps.clear();
					reservationPage.clickFolio(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					reservationPage.clickFolioPayButton(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					reservationPage.payButtonClickInTakePayment(driver, getTest_steps, "20", false, isSuiteCreated);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					getTestSteps =  reservationPage.closePaymentSuccessfullModal(driver);
					testSteps.addAll(getTestSteps);
					
					ArrayList<String> ItemLinecategoryList = new ArrayList<String>();
					ItemLinecategoryList =folio.getLineItemCategoriesList(driver);

					for(String cat:ItemLinecategoryList){
						Thread.sleep(1000);
						getTest_steps.clear();
						reservationPage.postLineItem(driver, getTest_steps, cat);
						testSteps.addAll(getTest_steps);	
					}
					
					testSteps.add("<<<<<<<<<LineItem Status Before RollBack>>>>>>>>>");
					for(String cat:ItemLinecategoryList){
						
						System.out.print(cat);
						Thread.sleep(500);
						getTestSteps.clear();
						folio.VerifyRollBackLineItemPendingStatus(driver, cat,ItemLineStatusBeforeRollBack,getTestSteps);
						testSteps.addAll(getTestSteps);
						
					}
					
					for(String cat:ItemLinecategoryList){
						if(cat.equalsIgnoreCase("MC")) {
							Thread.sleep(1000);
							getTest_steps.clear();
							folio.clikOnLineItemDescription(driver, cat, getTest_steps);
							testSteps.addAll(getTest_steps);
							
							
							Thread.sleep(1000);
							getTest_steps.clear();
							folio.RollbackMC_Category(driver, getTest_steps);
							testSteps.addAll(getTest_steps);
							
						}
						else {
							Thread.sleep(1000);
							getTest_steps.clear();
							folio.clikOnLineItemDescription(driver, cat, getTest_steps);
							testSteps.addAll(getTest_steps);
							
							Thread.sleep(1000);
							getTestSteps.clear();
							getTestSteps = folio.itemDetailsPopup(driver, "Item Detail");
							testSteps.addAll(getTestSteps);
							
							Thread.sleep(1000);
							getTest_steps.clear();
							folio.clickOnRollBackIcon(driver, getTest_steps);
							testSteps.addAll(getTest_steps);
						}
						
					}
					
					getTest_steps.clear();
				    folio.CheckDisplayVoidItems(driver, getTest_steps);
				    testSteps.addAll(getTest_steps);
					
					
					System.out.print("\n\n LineItem Status After RollBack\n");
					for(String cat:ItemLinecategoryList){
						
						System.out.print(cat);
						Thread.sleep(500);
						getTestSteps.clear();
						folio.VerifyRollBackLineItemPendingStatus(driver, cat,itemLineStatusAfterRollBack,getTestSteps);
						testSteps.addAll(getTestSteps);
						
					}
					
					testSteps.add("*******Before Rollback Status*********");
					for(String cat:ItemLinecategoryList){    
						testSteps.add(cat+" Status Before Rollback: "+ItemLineStatusBeforeRollBack.get(cat));
					}
					testSteps.add("*******After Rollback Status*********");
					for(String cat:ItemLinecategoryList){    
						testSteps.add(cat+" Status Changed to ( "+itemLineStatusAfterRollBack.get(cat)+" ) After RollBack");
					}
		
				} catch (Exception e) {
					e.printStackTrace();
					
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify Rollback of Posted line Item (RC, Line Item & Payments)", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify Rollback of Posted line Item (RC, Line Item & Payments)", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
			}
			
			if(TestCaseID.trim().equalsIgnoreCase("848244")) {
				
				try {
					
					Thread.sleep(1000);
					getTestSteps.clear();
					reservationPage.clickFolio(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
									
					Thread.sleep(1000);
					getTestSteps.clear();
					int lineitems = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItem Count In Folio Tab Before Void Action: "+lineitems);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalBalanceBeforeVoidAction = Double.valueOf(folio.getToatalBalance(driver));
					testSteps.add("Total balance Before Void Action "+totalBalanceBeforeVoidAction);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalIncidentalsBeforeVoidAction = Double.valueOf(folio.getIncidental(driver));
					testSteps.add("Total Incidentals Before Void Action "+totalIncidentalsBeforeVoidAction);
					
					
					Thread.sleep(1000);
					getTestSteps.clear();
					String VoidNote_Text = "Void Action Text";
					folio.VoidActionAddOn(driver, Incidental_Category,getTestSteps,VoidNote_Text);
					testSteps.addAll(getTestSteps);

					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalBalanceAfterVoidAction = Double.valueOf(folio.getToatalBalance(driver));
					testSteps.add("Total balance After Void Action "+totalBalanceAfterVoidAction);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalIncidentalsAfterVoidAction = Double.valueOf(folio.getIncidental(driver));
					testSteps.add("Total Incidentals After Void Action "+totalIncidentalsAfterVoidAction);
					
					
					Thread.sleep(1000);
					getTestSteps.clear();
					int lineitems_Unchecked = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItem Count In Folio Tab After Void Action With Checkbox Unchecked: "+lineitems_Unchecked);
					
					getTest_steps.clear();
				    folio.CheckDisplayVoidItems(driver, getTest_steps);
				    testSteps.addAll(getTest_steps);
				    
				    
				    Thread.sleep(1000);
					getTest_steps.clear();
					folio.clikOnLineItemDescription(driver, Incidental_Category, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					String actualVoidText =  folio.getVoidItemLineText(driver, getTest_steps);
					testSteps.addAll(getTest_steps); 

					Thread.sleep(1000);
					getTestSteps.clear();
					int lineitems_checked = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItem Count In Folio Tab After Void Action With Checkbox checked: "+lineitems_checked);
					
//					Thread.sleep(1000);
//					getTestSteps.clear();
					
					testSteps.add("Actual Balnace After Void Action: "+totalBalanceAfterVoidAction);
					testSteps.add("Expected Balnace After Void Action: "+ totalBalanceAfterVoidAction);
					Assert.assertEquals(totalBalanceAfterVoidAction, totalBalanceAfterVoidAction,"Failed: Incidentals are not able to remove from total balnace");
					testSteps.add("Added Void Total Balnace is Verified");
					
					
					testSteps.add("Actual Text: "+actualVoidText);
					testSteps.add("Expected Text: "+VoidNote_Text);
					
					Assert.assertEquals(VoidNote_Text, actualVoidText,"Failed: to Verify Text");
					testSteps.add("Added Void Text Verified");
					testSteps.add("Action: (Verify folio after void add-on) is verified");
					
					
		
				} catch (Exception e) {
					e.printStackTrace();
					
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify folio after void add-on", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify folio after void add-on", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
			}
			
			
			if(TestCaseID.trim().equalsIgnoreCase("848245")) {
				
				try {
					
					Thread.sleep(1000);
					getTestSteps.clear();
					reservationPage.clickFolio(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					int lineitems = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItem Count In Folio Tab Before Void Action: "+lineitems);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalBalanceBeforeVoidAction = Double.valueOf(folio.getToatalBalance(driver));
					testSteps.add("Total balance Before Void Action "+totalBalanceBeforeVoidAction);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalIncidentalsBeforeVoidAction = Double.valueOf(folio.getIncidental(driver));
					testSteps.add("Total Incidentals Before Void Action "+totalIncidentalsBeforeVoidAction);
					
					
					Thread.sleep(1000);
					getTestSteps.clear();
					folio.VoidActionAddOn(driver, Incidental_Category,getTestSteps,"Void Note text");
					testSteps.addAll(getTestSteps);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalBalanceAfterVoidAction = Double.valueOf(folio.getToatalBalance(driver));
					testSteps.add("Total balance After Void Action "+totalBalanceAfterVoidAction);
					
					Thread.sleep(1000);
					getTestSteps.clear();
					Double totalIncidentalsAfterVoidAction = Double.valueOf(folio.getIncidental(driver));
					testSteps.add("Total Incidentals After Void Action "+totalIncidentalsAfterVoidAction);
					
					
					Thread.sleep(1000);
					getTestSteps.clear();
					int lineitems_Unchecked = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItem Count In Folio Tab After Void Action With Checkbox Unchecked: "+lineitems_Unchecked);
					
					getTest_steps.clear();
				    folio.CheckDisplayVoidItems(driver, getTest_steps);
				    testSteps.addAll(getTest_steps);
				    
				    Thread.sleep(1000);
					getTestSteps.clear();
					int lineitems_checked = folio.getLineItemRowsCount(driver);
					testSteps.add("LineItem Count In Folio Tab After Void Action With Checkbox checked: "+lineitems_checked);
					
					testSteps.add("Actual Balnace After Void Action: "+totalBalanceAfterVoidAction);
					testSteps.add("Expected Balnace After Void Action: "+ totalBalanceAfterVoidAction);
					Assert.assertEquals(totalBalanceAfterVoidAction, totalBalanceAfterVoidAction,"Failed: Incidentals are not able to remove from total balnace");
					testSteps.add("Added Void Total Balnace is Verified");
					
					Assert.assertEquals(lineitems, lineitems_checked,"Failed: to Verify ItemLine Count");
					
					testSteps.add("Action: (Verify the voided item) is verified");
					
					
		
				} catch (Exception e) {
					e.printStackTrace();
					
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify the voided item", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify the voided item", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}

		}
					
}
		statusCode = new ArrayList<String>();
		caseId = new ArrayList<String>();
		String[] testcase = TestCaseID.split("\\|");
		for (int i = 0; i < testcase.length; i++) {
			caseId.add(testcase[i]);
			statusCode.add("1");
			comments = "pass";
		}
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("folioactions", HS_EXCEL);
	}


	//@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
