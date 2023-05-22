package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
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
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.model.Category;

import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

public class VerifyFolioMovePaymentOptions extends TestCore {

	private WebDriver driver = null;

	static ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> totalNights = new ArrayList<>();
	ArrayList<String> roomNumber = new ArrayList<>();
	

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
		if (!Utility.isExecutable(testName, envLoginExcel))
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

		
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("824557");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}
		String [] roomClass = RoomClass.replace("|", " ").split(" ");
		String [] Phone_Number = PhoneNumber.replace("|", " ").split(" ");
		String testName =caseName;
		test_description = caseName+"<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/"+TestCaseID+"' target='_blank'>"
				+ "Click here to open TestRail: C"+TestCaseID+"</a>";
		test_catagory = "Groups";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
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
		Policies policies = new Policies();
		Account account = new Account();
		Folio folio = new Folio();
		System.out.print("RoomClass: "+roomClass[0]);
		String randomNumber = Utility.GenerateRandomNumber();
		roomClass[0] = roomClass[0] + randomNumber;
		displayName = roomClass[0];
		lastName = lastName + randomNumber;
		firstName = firstName + randomNumber;
		Policy_T = Policy_T + randomNumber;
		String AccountName = "Account_Name";
		String account_Number = "";
		
		
				
	
		//Login
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
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
		
		
		
		if(TestCaseID.equalsIgnoreCase("824964")) {
			app_logs.info("Navigating to Inventory");
			navigation.Inventory(driver, testSteps);
			
			testSteps.add("Navigating to policy");
			app_logs.info("Navigating to policy");
			navigation.policies(driver);
			testSteps.add("Searching For All policies");
			app_logs.info("Searching For All policies");
			
			
			getTest_steps.clear();
			policies.Search_All_Policy_U(driver, getTest_steps);
			testSteps.addAll(getTest_steps);
			app_logs.info("All policies Search Complete");
			
				
			int size = policies.Check_Size_Of_Policies_Rows(driver);
			if(size>0) {
				testSteps.add("Deleting policies");
				app_logs.info("Deleting policies");
				policies.DeleteAllPolicies(driver,getTest_steps);
			}
			else {
				testSteps.add("No policies Found For Deletion");
				app_logs.info("No policies Found For Deletion");
			}
			navigation.reservation(driver);
		}
		
		
		
		
		
		if(TestCaseID.equalsIgnoreCase("825266")||TestCaseID.equalsIgnoreCase("825268"))   {
			navigation.Accounts(driver);
			account.ClickOnNewAccountButton(driver, getTest_steps);
			account.AccountDetails(driver, AccountType, AccountName);
			account.AccountAttributes(driver, MarketSegment, Referral);
			Thread.sleep(1000);
			account.mailingInfo(driver, GuestFirstName, GuestLastName, Phone_Number[0], Address3, Email, City, State, PostalCode);
			Thread.sleep(1000);
			account.selectBillingInfoSameAsMailingInfoCheckBox(driver, getTest_steps);
			Thread.sleep(1000);
			account.Save(driver);
			Thread.sleep(1000);
			account_Number = account.getAccountNum(driver);
			Thread.sleep(1000);
			account.ClickFolio(driver);
			Thread.sleep(1000);
			account.clickOnFolioOptions(driver);
			Thread.sleep(1000);
			account.select_ChargeRouting(driver, "All Items");
			Thread.sleep(1000);
			account.Save(driver);
			Thread.sleep(1000);
			account.NewReservationButton(driver, test);
			
		}
		
		
		
		//MRB Reservation
		
			
			try {
				// Get CheckIN and Checkout Date
				if (!(Utility.validateInput(CheckInDate))) {
					for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						
						if(TestCaseID.equalsIgnoreCase("825266") || TestCaseID.equalsIgnoreCase("825268")) {
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
						else {
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
						
					}
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);

				if(!(TestCaseID.equalsIgnoreCase("825266") ||TestCaseID.equalsIgnoreCase("825268"))) {
					Thread.sleep(2000);
					cpRes.click_NewReservation(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
				}
				
				
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
				
				if(!TestCaseID.equalsIgnoreCase("825266")) {
					if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
						getTest_steps.clear();
						cpRes.enter_PaymentDetails(driver, getTest_steps, paymentType, cardNumber, nameOnCard, cardExpDate);
						testSteps.addAll(getTest_steps);
					}
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
				
				
				
				if(TestCaseID.equalsIgnoreCase("825268")) {
					getTest_steps.clear();
					cpRes.click_Folio(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					folio.clickAddLineItemButton(driver);
					testSteps.add("Clicking On Add New LineItem");
					
					getTest_steps.clear();
					folio.AddFolioLineItem(driver, Incidental_Category, DepositOverrideAmount);
					testSteps.addAll(getTest_steps);
					
					
					getTest_steps.clear();
					int BeforeFolioRows = folio.getLineItemRowsCount(driver);
					System.out.print("\nFolio Rows Before Applying Routing: "+BeforeFolioRows+"\n");
					testSteps.add("Folio Rows Before Applying Routing: "+BeforeFolioRows);
					
					getTest_steps.clear();
					folio.ApplyRouting(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					
					int AfterFolioRows = folio.getLineItemRowsCount(driver);
					System.out.print("\nFolio Rows After Applying Routing: "+AfterFolioRows+"\n");
					testSteps.add("Folio Rows After Applying Routing: "+AfterFolioRows);
					
					Assert.assertEquals(AfterFolioRows, 0,"Failed: No of Rows Not Matched");
					testSteps.add("Verified: Apply Routing On line Items ");
					
					navigation.Accounts(driver);
					getTest_steps.clear();
					account.search_Account(driver, test, AccountType, AccountName, account_Number, "Active", getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					account.OpenExitingAccount(driver);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					account.ClickFolio(driver);
				}
				
				
				
				if(TestCaseID.equalsIgnoreCase("825266")) {
					Thread.sleep(1000);
					getTest_steps.clear();
					cpRes.Click_CheckInAllButton(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					
					getTest_steps.clear();
					cpRes.verifyCheckInConfirmDetailsPaymentPopupIsAppeared(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					getTest_steps.clear();
					cpRes.VerifyinHouseStatus(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					getTest_steps.clear();
					cpRes.click_Folio(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					getTest_steps.clear();
					int dropdown_Size  = cpRes.FolioDropDownList(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					getTest_steps.clear();
					cpRes.mrbChangeFolio(driver, dropdown_Size, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					
					
					int BeforeFolioRows = folio.getLineItemRowsCount(driver);
					System.out.print("\nFolio Rows Before Applying Routing: "+BeforeFolioRows+"\n");
					testSteps.add("Folio Rows Before Applying Routing: "+BeforeFolioRows);
					
					getTest_steps.clear();
					folio.ApplyRouting(driver, getTest_steps);
					testSteps.addAll(getTest_steps);
					
					
					
					int AfterFolioRows = folio.getLineItemRowsCount(driver);
					System.out.print("\nFolio Rows After Applying Routing: "+AfterFolioRows+"\n");
					testSteps.add("Folio Rows After Applying Routing: "+AfterFolioRows);
					
					Assert.assertEquals(AfterFolioRows, 1,"Failed: No of Rows Not Matched");
					
					testSteps.add("Verified: Apply Routing On line Items ");
					
					navigation.Accounts(driver);
					getTest_steps.clear();
					account.search_Account(driver, test, AccountType, AccountName, account_Number, "Active", getTest_steps);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					getTest_steps.clear();
					account.OpenExitingAccount(driver);
					testSteps.addAll(getTest_steps);
					
					Thread.sleep(1000);
					account.ClickFolio(driver);
					
					
				}
				
				
				
				if(TestCaseID.equalsIgnoreCase("824964")) {
				
				getTest_steps.clear();
				cpRes.verifyCheckInAllButton(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.Click_CheckInAllButton(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				getTest_steps.clear();
				cpRes.verifyCheckInConfirmDetailsPaymentPopupIsAppeared(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				String Balance =  cpRes.getBalance_Amount(driver);
				System.out.print(Balance);
				
				
				getTest_steps.clear();
				String BalanceAfterFirstCheckout = cpRes.checkoutReservationNobutton(driver, getTest_steps,Balance);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.rollBack(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				getTest_steps.clear();
				cpRes.CheckInReservationAfterRollback(driver, getTest_steps);
				testSteps.addAll(getTest_steps);
				
				
				getTest_steps.clear();
				cpRes.checkoutReservationYesbutton(driver, getTest_steps,Balance);
				testSteps.addAll(getTest_steps);
				
				
				String FinalBalance =  cpRes.getBalance_Amount(driver);
				System.out.print(FinalBalance);
				
				Assert.assertEquals(FinalBalance, "0.00");	
				
			}	
				
			}
			catch (Exception e) {
				testSteps.add(e.toString());
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify the behaviour of individual Checkout if multiple folios present", testName, "InventoryNavigation",
							driver);
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


		comments = "Verified Folio Move Payment Popup";
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
	
		}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyFolioMovePaymentOptions", envLoginExcel);
	}


	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
