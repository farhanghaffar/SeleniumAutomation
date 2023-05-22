package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
public class ReportsV2TransactionsReportCancelTransactionType extends TestCore {


	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	ReservationV2 reservationV2Page = new ReservationV2();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	ReservationSearch reservationSearch = new ReservationSearch();
	ArrayList<String> test_steps = new ArrayList<>();

	String sourceOfRes="From Reservations page", reservationNumber = null, guestFirstName = null, guestLastName,	
	 referral = "Walk In", reportsTab, applicationTab,
	cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver,test_steps),  guestFullName;
	
	HashMap<String, String> ledgerAccounts = new HashMap<>();
	HashMap<String, Double> ledgerAmounts = new HashMap<>();
	HashMap<String, String> folioItemValues = new HashMap<>();
	HashMap<String, Double> folioBalances = new HashMap<>();
	HashMap<String, String> folioPaymentItemValues= new HashMap<>();
	
	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> allTabs = new ArrayList<>();


	HashMap<String, Double> expChanges2 = new HashMap<>();
	HashMap<String, String> paymentData = new HashMap<>();	
	HashMap<String, String> paymentTransactionTypeAndAmount = new HashMap<>();

	
	HashMap<String, ArrayList<String>> beforeCreditCardTransactionTypeTotal2=new HashMap<>();
	HashMap<String, ArrayList<String>> afterCreditCardTransactionTypeTotal2=new HashMap<>();

	HashMap<String, String> itemDescription = new HashMap<>();
	HashMap<String, String> reservationData = new HashMap<>();	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();

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
		loginAutoReportsV2(driver, test_steps);
		
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateLedgerBalancesReport(String TestCaseID, String Scenario, String dateRange, 
			String resType, String numberOfRooms, String checkInDate, String checkOutDate,			
			String ratePlan, String roomClass, String isPayment, String paymentMethod, String cardNumber, String adults, String children) throws Throwable {


		test_name = Scenario;
		test_description = "Validate Transaction  Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Transaction Report";
		String testName = test_name;
		String transactionType=null;	
		GuestFolio guestFolio = new GuestFolio();
		String PaymentTransactionType=null;
		String cancelTransactionType=null;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		comments.add("Test got failed");
		if(!Utility.validateString(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}			
		}

		checkInDate=Utility.getCurrentDate("dd/MM/yyyy");
		checkOutDate=Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
		app_logs.info(checkInDate);
		app_logs.info(checkOutDate);
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
		

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
		guestFirstName = "Auto";
		guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = guestFirstName+" "+guestLastName;
		String includePayment="Yes";
		String amount="0.00";
		try {		
			
			nav.ReportsV2(driver, test_steps);
			report.navigateToTransactionsReport(driver, test_steps);
			report.selectDateRange(driver, checkInDate, checkOutDate, dateRange, test_steps);
			report.excludeZeroBalancePaymentMethodsForTransactionReport(driver, test_steps, true);
			report.selectBreakOutDailyTotalForTransactionReport(driver, test_steps, false);				
			int count=0;
			count=report.clickOnRunReportUntill(driver);
			app_logs.info("count return: "+count);	

			allTabs = new ArrayList<>(driver.getWindowHandles());
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);
			sourceOfRes="From Reservations page";		
			
			if((count>5))
			{
				beforeCreditCardTransactionTypeTotal2=report.getCreditCardTypeDetailofTransectionReport2(driver, test_steps);			
				app_logs.info("beforeCreditCardTransactionTypeTotal2: "+beforeCreditCardTransactionTypeTotal2);
				
				Utility.switchTab(driver, applicationTab);
				nav.ReservationV2_Backward(driver);
				test_steps.add("**************<b> TAKING PAYMENT FROM GUEST FOLIO </b>**************");
				reservationV2Page.createReservation_RV2(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
						children, ratePlan, null, roomClass, "", "Mr.", guestFirstName, guestLastName, true,"",
						"", "", "", "", "", "", "", "", "", false, "GDS", 
						referral, paymentMethod, cardNumber, guestFullName, cardExpDate, 0, "", false, 
						"", "", "", "", "", "", "", "", 
						"", "", "", "", "", "", false, "");
				
				test_steps.add("**************<b> TAKING PAYMENT FROM GUEST FOLIO </b>**************");
				guestFolio.clickOnFolioTab(driver, test_steps);
				includePayment="Capture";				
				guestFolio.takePaymentFromGuestFolio(driver, test_steps, includePayment);
				amount = paymentData.get("Amount");
				transactionType = paymentData.get("Transaction Type");
				paymentTransactionTypeAndAmount.put(transactionType, amount);
				
				PaymentTransactionType="Capture";
				cancelTransactionType="Cancel";
				guestFolio.cancelPaymentPopUp(driver, test_steps,PaymentTransactionType);
				
				Wait.wait60Second();
				Wait.wait60Second();
				Wait.wait60Second();
				Wait.wait60Second();
				Wait.wait60Second();

				test_steps.add("========== Validating Reports after 5 minutes at "+java.time.LocalTime.now()+"==========");
				app_logs.info("========== Validating Reports after 5 minutes at "+java.time.LocalTime.now()+"==========");

				Utility.switchTab(driver, reportsTab);
				report.clickOnRunReportUntill(driver);
				
				afterCreditCardTransactionTypeTotal2=report.getCreditCardTypeDetailofTransectionReport2(driver, test_steps);
				app_logs.info("afterCreditCardTransactionTypeTotal2: "+afterCreditCardTransactionTypeTotal2);
				
				
				test_steps.add("**************<b> COMPARING TRANSACTION REPORT BEFORE AND AFTER TRANSACTION VALUE</b>**************");
				
				report.validateCreditCardTypeDetailofTransectionReport_RV2_2( driver,  transactionType,  beforeCreditCardTransactionTypeTotal2,
						afterCreditCardTransactionTypeTotal2, paymentTransactionTypeAndAmount, test_steps,cancelTransactionType);
			
				
			}else {
			beforeCreditCardTransactionTypeTotal2=report.getCreditCardTypeDetailofTransectionReport2(driver, test_steps);			
			app_logs.info("beforeCreditCardTransactionTypeTotal2: "+beforeCreditCardTransactionTypeTotal2);
				
				Utility.switchTab(driver, applicationTab);
				nav.ReservationV2_Backward(driver);
			
			
			if (resType.equalsIgnoreCase("Single")) 
				{	test_steps.add("**************<b> TAKING PAYMENT FROM GUEST FOLIO </b>**************");
					reservationData = reservationV2Page.createReservation_RV2(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
							children, ratePlan, null, roomClass, "", "Mr.", guestFirstName, guestLastName, true,"",
							"", "", "", "", "", "", "", "", "", false, "GDS", 
							referral, paymentMethod, cardNumber, guestFullName, cardExpDate, 0, "", false, 
							"", "", "", "", "", "", "", "", 
							"", "", "", "", "", "", false, "");
					
					reservationNumber=reservationData.get("ReservationNumber");
				} 
				test_steps.add("**************<b> TAKING PAYMENT FROM GUEST FOLIO </b>**************");
				guestFolio.clickOnFolioTab(driver, test_steps);
				includePayment="Capture";
				paymentData = guestFolio.takePaymentFromGuestFolio(driver, test_steps, includePayment);
				amount = paymentData.get("Amount");
				transactionType = paymentData.get("Transaction Type");
				paymentTransactionTypeAndAmount.put(transactionType, amount);
				
			
				if(includePayment=="Authorization")
				{
					PaymentTransactionType="Authoriz";
					cancelTransactionType="Authorization Reversal";
				}else if(includePayment=="Capture")
				{
					PaymentTransactionType="Capture";
					cancelTransactionType="Cancel";
				}
				
				
				if(cardNumber=="5454545454545451")
				{
					PaymentTransactionType="Capture";
					cancelTransactionType="Failed";
				}
				guestFolio.cancelPaymentPopUp(driver, test_steps,PaymentTransactionType);
		app_logs.info("========== Action completed at "+java.time.LocalTime.now()+"==========");		
		Wait.wait60Second();
		Wait.wait60Second();
		Wait.wait60Second();
		Wait.wait60Second();
		Wait.wait60Second();
		
		test_steps.add("========== Validating Reports after 5 minutes at "+java.time.LocalTime.now()+"==========");
		app_logs.info("========== Validating Reports after 5 minutes at "+java.time.LocalTime.now()+"==========");

		
	
			Utility.switchTab(driver, reportsTab);
			report.clickOnRunReport(driver);
		
			afterCreditCardTransactionTypeTotal2=report.getCreditCardTypeDetailofTransectionReport2(driver, test_steps);
			app_logs.info("afterCreditCardTransactionTypeTotal2: "+afterCreditCardTransactionTypeTotal2);
			//test_steps.add("afterCreditCardTransactionTypeTotal2: "+afterCreditCardTransactionTypeTotal2);
			
			test_steps.add("**************<b> COMPARING TRANSACTION REPORT BEFORE AND AFTER TRANSACTION VALUE</b>**************");
		
			report.validateCreditCardTypeDetailofTransectionReport_RV2_2( driver,  transactionType,  beforeCreditCardTransactionTypeTotal2,
					afterCreditCardTransactionTypeTotal2, paymentTransactionTypeAndAmount, test_steps,cancelTransactionType);
			}
		
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", test_name, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		
		try {
			//comments = "";
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
				comments.add("Test is passed");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CancelTransactionType2", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
;
	}






}
