package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyCopyOfUnitOwnerNotesToReservation extends TestCore {
	//string 
	//C825289
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna)) {
			throw new SkipException("Skipping the test - " + testName);
		}

	}

	@Test(dataProvider = "getData", groups = "Accounts")
	public void create_Unitowner_Account(String url, String ClientCode, String Username, String Password,String PropertyName,String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String PromoCode,String IsSplitRes,
			String RoomClass,String IsAssign,String IsDepositOverride,String DepositOverrideAmount,String IsAddMoreGuestInfo,String Salutation,String GuestFirstName,String GuestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,String PaymentType,String CardNumber,String NameOnCard,String CardExpDate,String IsChangeInPayAmount,String ChangedAmountValue,
			String TravelAgent,String MarketSegment,String Referral,String IsAddNotes,String NoteType,String NotesSubject,String NotesDescription,
			String IsTask, String TaskCategory, String TaskType,String TaskDetails,String TaskRemarks,String TaskDueon,String TaskAssignee,String TaskStatus,
			String AccountName,String Accounttype,String TakePaymentType)
					throws InterruptedException, IOException, Exception {
		
        String testcaseId="848628";
        test_name= this.getClass().getSimpleName().trim();
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848628");
		
		test_description = test_name + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848628' target='_blank'>"
				+ "Click here to open TestRail: C848628</a><br>";

		test_catagory = "Accounts";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");


		CPReservationPage res = new CPReservationPage();
		Navigation nav = new Navigation();
		//RoomClass rc = new RoomClass();
		
	
		String reservation=null;
		String status=null;
		
		
		
		

		Account CreateTA = new Account();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			// Get CheckIN and Checkout Date

			if ( !(Utility.validateInput(CheckInDate)) ) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			}else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}

			TaskDueon = checkOutDates.get(0);
			
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		} catch (Error e) {
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		}

		// Login
		try {
			driver = getDriver();
			login_Autoota(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	
		// navigate to accounts
		try {

			nav.Accounts(driver);
			test_steps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		String AccountNumber=null;
		String[] roomclass=RoomClass.split("\\|");

		// New account
		try {
			test_steps.add("****************** Creating account *********************");
			app_logs.info("****************** Creating account *********************");
			AccountName=AccountName+Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, Accounttype);
			CreateTA.AccountDetails(driver, Accounttype, AccountName);
			AccountNumber=Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, AccountNumber);
			getTest_Steps.clear();			
			getTest_Steps = CreateTA.AccountAttributes(driver, test, "Internet", Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.Mailinginfo(driver, test, "Acc Fname", "Account Lname", "9087653421", "9087653422",
					Address1, Address2, Address3, "Test@gmail.com", "City", State, PostalCode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.Billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			String roomClass ="Junior Suites";

			CreateTA.associateAnyRooms(driver, test_steps, "ALL");


			getTest_Steps.clear();
			getTest_Steps = CreateTA.AccountSave(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			CreateTA.Cp_AccAddNotes( driver,  NotesSubject,  NotesDescription,  NoteType);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.AccountSave(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		// Create Reservation

				try {
					app_logs.info("======CP Reservation Creation=======");
					test_steps.add("======CP Reservation Creation=======");
					nav.Click_ReservationMenuFromTaxPage(driver);
					getTest_Steps.clear();
					getTest_Steps=res.click_NewReservation(driver, getTest_Steps);
					test_steps.addAll(getTest_Steps);
					res.CheckInDate(driver, 1);
					res.CheckOutDate(driver, 1);
					getTest_Steps.clear();
					getTest_Steps=res.enter_Adults(driver, getTest_Steps, Adults);
					test_steps.addAll(getTest_Steps);
					getTest_Steps.clear();
					getTest_Steps=res.enter_Children(driver, getTest_Steps, Children);
					test_steps.addAll(getTest_Steps);
					getTest_Steps.clear();
					getTest_Steps=res.select_Rateplan(driver, getTest_Steps, Rateplan, PromoCode);
					test_steps.addAll(getTest_Steps);
					getTest_Steps.clear();
					getTest_Steps=res.clickOnFindRooms(driver, getTest_Steps);
					test_steps.addAll(getTest_Steps);
					System.out.print("ISAssigne:" + IsAssign);
					getTest_Steps.clear();
					getTest_Steps=res.selectRoom(driver, getTest_Steps, RoomClass, Utility.RoomNo, "");
					test_steps.addAll(getTest_Steps);
					//res.selectRoom(driver, RoomClassName, IsAssign);
					getTest_Steps.clear();
					getTest_Steps=res.clickNext(driver, getTest_Steps);
					test_steps.addAll(getTest_Steps);
					
					res.enterMailingAddressWithEmail(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber,
							AltenativePhone, Email, AccountName, AccountType, Address1, Address2, Address3, City, Country, State,
							PostalCode, IsGuesProfile);
					res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
					res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
					//res.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
					getTest_Steps.clear();
					getTest_Steps=res.clickBookNow(driver, getTest_Steps);
					test_steps.addAll(getTest_Steps);
					reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
					status = res.get_ReservationStatus(driver, test_steps);
					getTest_Steps.clear();
					getTest_Steps=res.clickCloseReservationSavePopup(driver, getTest_Steps);
					test_steps.addAll(getTest_Steps);
					
					res.closeFirstOpenedReservationUnSavedData(driver, test_steps);
					test_steps.addAll(getTest_Steps);

				} catch (Exception e) {

					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to Create Reservation ", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}

				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to Create Reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
		try {
			getTest_Steps.clear();
			res.closeAllOpenedReservations(driver);
			res.Search_ResNumber_And_Click(driver, reservation);
			
			getTest_Steps.clear();
			getTest_Steps=res.verifyNotesCopiedOrNot(driver,getTest_Steps, NoteType, NotesSubject, NotesDescription,true);
			test_steps.addAll(getTest_Steps);
			comments.add(0,  "Notes from unit owner account is copied to reservation");
			statusCode.add(0, "1");
	

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);
			} else {
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);
			} else {
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);

				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided
		return Utility.getData("VerifyCopyOfUnitOwnerNotesToRes", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}


}
