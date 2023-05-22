package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreateUnitOwnerAcc extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, SNExcel)) {
			throw new SkipException("Skipping the test - " + testName);
		}

	}

	@Test(dataProvider = "getData", groups = "Accounts")
	public void create_Unitowner_Account(String TestCaseID,String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String PromoCode,String IsSplitRes,
			String RoomClass,String IsAssign,String IsDepositOverride,String DepositOverrideAmount,String IsAddMoreGuestInfo,String Salutation,String GuestFirstName,String GuestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,String PaymentType,String CardNumber,String NameOnCard,String CardExpDate,String IsChangeInPayAmount,String ChangedAmountValue,
			String TravelAgent,String MarketSegment,String Referral,String IsAddNotes,String NoteType,String Subject,String Description,
			String IsTask, String TaskCategory, String TaskType,String TaskDetails,String TaskRemarks,String TaskDueon,String TaskAssignee,String TaskStatus,
			String AccountName,String Accounttype,String TakePaymentType)
					throws Exception {
            boolean isExcecutable= Utility.getResultForCase(driver, TestCaseID);
            if(isExcecutable) {
		test_name = "Create_Unitowner_Account";
		test_description = "Create a Unit Owners Account ,make a reservation and verify payments<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/667710' target='_blank'>"
				+ "Click here to open TestRail: C667710</a>";
		test_catagory = "Accounts";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add(TestCaseID);
			caseId.add(TestCaseID);
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}


		CPReservationPage res = new CPReservationPage();
		Navigation nav = new Navigation();
		//RoomClass rc = new RoomClass();
		Login login=new Login();
		Properties property=new Properties();
		NewRoomClassesV2 rc = new NewRoomClassesV2();
		ReservationSearch search = new ReservationSearch();
		String timeZone=null;

		ArrayList<String> Rooms = new ArrayList<String>();
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> RoomAbbri1 = new ArrayList<String>();
		ArrayList<String> roomCost = new ArrayList<String>();
		
		String property1="Payments Property";
		String property2="Payments Property-2";
		String AccountName2=null;

		Account CreateTA = new Account();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
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
			login.login(driver, envURL, "autopay", "autouser", "Auto@123");
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
		property.changeProperty(driver, property1);

		try {
			if(IsAddNotes.equalsIgnoreCase("Yes")) {
				nav.Setup(driver);
				nav.Properties(driver);
				nav.open_Property(driver, test_steps, property1);
				nav.click_PropertyOptions(driver, test_steps);
				timeZone=nav.get_Property_TimeZone(driver);
				nav.Reservation_Backward(driver);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		try {
			test_steps.add("****************** Gettirng room class abbrivation *********************");
			app_logs.info("****************** Gettirng room class abbrivation *********************");
			nav.Setup(driver);
			nav.RoomClass(driver);
			RoomAbbri1 = rc.getAbbrivation(driver, "|", RoomClass, test_steps);
			String[] Room=RoomClass.split("\\|");
			for(int i=0;i<Room.length;i++) {
			RoomAbbri.add(i, Room[i]+":"+RoomAbbri1.get(i));
			}
			Utility.app_logs.info(RoomAbbri);
			System.out.println(RoomAbbri);
			nav.cpReservation_Backward(driver);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
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

			//CreateTA.associateRooms(driver, test_steps, roomclass[0]);


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
		
		try {
			
			//CreateTA.close_Account(driver);
			test_steps.add("========== SEARCH ACCOUNT==========");
			app_logs.info("========== SEARCH ACCOUNT==========");
			
			boolean accFound=CreateTA.checkForAccountExistsAndOpen(driver, test_steps, AccountName, Accounttype, false);
			Assert.assertEquals(accFound, false,"Account found when rooms are not associated");
			comments="Verified Unit Owner Account is not displaying when no rooms are associated for the Account: "+AccountName+". ";
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
			
		try {
			test_steps.add("****************** Creating another account *********************");
			app_logs.info("****************** Creating another account *********************");
			AccountName2="UnitAcc"+Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, Accounttype);
			CreateTA.AccountDetails(driver, Accounttype, AccountName2);
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

			CreateTA.associateRooms(driver, test_steps, roomclass[0]);


			getTest_Steps.clear();
			getTest_Steps = CreateTA.AccountSave(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			CreateTA.close_Account(driver);
			test_steps.add("========== SEARCH ACCOUNT==========");
			app_logs.info("========== SEARCH ACCOUNT==========");
			
			boolean accoundFound=CreateTA.checkForAccountExistsAndOpen(driver, test_steps, AccountName2, Accounttype, false);
			Assert.assertEquals(accoundFound, true,"Account not found when rooms are associated");
			comments="Verified Unit Owner Account is displaying when rooms are associated for the Account: "+AccountName2+". ";

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
		try {	
			property.changeProperty(driver, property2);
			test_steps.add("========== SEARCH ACCOUNT==========");
			app_logs.info("========== SEARCH ACCOUNT==========");
			
			boolean accoundFound=CreateTA.checkForAccountExistsAndOpen(driver, test_steps, AccountName2, Accounttype, false);
			Assert.assertEquals(accoundFound, false,"Account found");
			comments="Verified Unit Owner Account is not displaying by changing the property for the Account: "+AccountName2+". ";
			
			
			property.changeProperty(driver, "--All--");
			boolean accFound1=CreateTA.checkForAccountExistsAndOpen(driver, test_steps, AccountName2, Accounttype, false);
			Assert.assertEquals(accFound1, true,"Account not found");
			comments="Verified Unit Owner Account is displaying by changing the property to ALL for the Account: "+AccountName2+". ";

				String[] testcase = TestCaseID.split(Utility.DELIM);
				for (int i = 0; i < testcase.length; i++) {
					statusCode.add(i, "1");
				}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
            }
	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided
		return Utility.getData("UnitOwner_Acc", SNExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
