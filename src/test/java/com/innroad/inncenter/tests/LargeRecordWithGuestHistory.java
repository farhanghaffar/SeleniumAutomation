package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.model.PaymentInfo;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class LargeRecordWithGuestHistory extends TestCore  {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	ReportsV2 report = new ReportsV2();
	Properties prop=new Properties();
	GuestHistory guestHistory=new GuestHistory();
	Navigation nav = new Navigation();
	FolioNew folioNew=new FolioNew();
	ReservationV2 reservationV2Page = new ReservationV2();
	GuestFolio guestFolio = new GuestFolio();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	//GuestFolio guestfolio=new GuestFolio();
	ReservationSearch reservationSearch = new ReservationSearch();
	ArrayList<String> test_steps = new ArrayList<>();
	OR_ReservationV2 or_res=new OR_ReservationV2();
	String sourceOfRes="From Reservations page", reservationNumber = null, guestFirstName = null, guestLastName,guestFirstNameCopied = null, guestLastNameCopied		 , reportsTab, applicationTab,
	cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver,test_steps), roomCharge , guestFullName,guestFullName2,guestFullNameCopied,reservation_Status;
	HashMap<String, String> ledgerAccounts = new HashMap<>();
	HashMap<String, String> ledgerAmounts = new HashMap<>();
	HashMap<String, String> folioItemValues = new HashMap<>();
	HashMap<String, Double> folioBalances = new HashMap<>();
	HashMap<String, String> folioPaymentItemValues= new HashMap<>();
	HashMap<String, String> reservationDetails = new HashMap<>();
	HashMap<String, String> copiedReservationDetails = new HashMap<>();
	String stayInfo = null;	
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
	public void reportsV2ReservationCardReportForLongStayRes(String TestCaseID, String Scenario, String dateRange, 
			String resType, String numberOfRooms, String checkInDate, String checkOutDate,			
			String ratePlan, String roomClass, String isPayment, String paymentMethod, String cardNumber, String adults, String children
			,String phoneNumber,String email,String address1,String address2,String address3,String city,String country,String state,
			String postalCode,String marketSegment,String referral,String includeReservationTypes,String sortReservationsBy,
			String onlyIncludeReservationsWith,String onlyIncludeReservationsWithBooked ,String propertyName) throws Throwable {

		//if(Utility.getResultForCase(driver, TestCaseID))
		//{
		test_name = Scenario;
		test_description = "Validate ReservationCard  Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - ReservationCard Report";
		String testName = test_name;		
		String roomNumber=null;	
		String source=null;
		String createdDate=null;
		String createdBy=null;
		String cardLastFourNumber=null;
		String stayon_Date_Range=null;
		Double incidental=null;
		//tax
		String randomString = Utility.generateRandomStringWithoutNumbers();
		String taxName = "Tax_"+randomString;
		String feeName = "Fee_"+randomString;
		TaxesAndFee taxesAndFee=new TaxesAndFee();
		String property="auto_Report2";
		String taxType="percent";
		String taxValue= Utility.GenerateRandomNumber(10, 20);
		String Fees="Fee Adjustment";
		String feeType="percent";
		String feecategoryValue= Utility.GenerateRandomNumber(10, 20);
		ArrayList<String> tax_Amount=new ArrayList<>();
		String feeValue=null;
		String paid_Amount="0.00";
		String includePayment="Capture";
		HashMap<String, String> calculateTax=new HashMap();
		 Double roomChargesTotal=null;
		 String roomChargePerNight=null;
		 String accountName="";
		 String accountType="";
		ArrayList<String>updatedTaxName=new ArrayList<>();
		ArrayList<String> categoryTaxs=new ArrayList<>();		
		guestFirstName = "Auto";
		guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = guestFirstName+" "+guestLastName;
		guestFullName2=guestLastName+" "+guestFirstName;		
		guestFirstNameCopied="Copied";
		guestLastNameCopied="User"+Utility.generateRandomStringWithoutNumbers();
		guestFullNameCopied=guestLastNameCopied+" "+guestFirstNameCopied;
		address1=address1+Utility.generateRandomStringWithoutNumbers();

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		Utility.initializeTestCase(TestCaseID, Utility.testId, Utility.statusCode, Utility.comments, TestCaseID);
		checkInDate=Utility.getCurrentDate("dd/MM/yyyy");
		checkOutDate=Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
		String checkInDate2=null;
		String checkOutDate2=null;		
		checkInDate2=Utility.parseDate(Utility.getDatePast_FutureDate(-3), "MM/dd/yyyy", "dd/MM/yyyy");
		checkOutDate2=Utility.parseDate(Utility.getDatePast_FutureDate(-2), "MM/dd/yyyy", "dd/MM/yyyy");
		String noOfNight="0",afterlimit="2";
		noOfNight=Utility.differenceBetweenDates(checkInDate, checkOutDate);
		boolean allNight=false;
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
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			
			for(int i=1;i<130;i++)
			{
				reservationV2Page.basicSearch_WithReservationNo(driver, "20323808", true);
				driver.navigate().refresh();
				Wait.wait5Second();

				reservationV2Page.copyReservation(driver, test_steps, guestFirstName, guestLastName);
				app_logs.info("==========Created Reservation Successfully =========="+i);			
				test_steps.add("==========Created Reservation Successfully =========="+i);			
				reservationV2Page.closeAllOpenedReservations(driver);

	}		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		
		try {
			//comments = "";
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Reservation cardreport ");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//}
	}

	@DataProvider
	public Object[][] getData() {	
		return Utility.getData("LargeRecordWithGuestHistory", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode, Utility.comments, TestCore.TestRail_AssignToID);

	}



}
