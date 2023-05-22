package com.innroad.inncenter.tests;

import java.text.ParseException;
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
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.Navigation;
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

public class FolioBalanceReportOnHoldAndGuaranteedReservation  extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;
	ReportsV2 report = new ReportsV2();
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

	String sourceOfRes="From Reservations page", reservationNumber = null, guestFirstName = null, guestLastName	 , reportsTab, applicationTab,
	cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver,test_steps), roomCharge , guestFullName,guestFullName2,reservation_Status;
	HashMap<String, String> folioPaymentItemValues= new HashMap<>();
	HashMap<String, String> reservationDetails = new HashMap<>();
	HashMap<String, String>beforeRecivableBalance=new HashMap<>();
	HashMap<String, String>afterRecivableBalance=new HashMap<>();
	HashMap<String, String>beforePayableBalance=new HashMap<>();
	HashMap<String, String>afterPayableBalance=new HashMap<>();
	HashMap<String, String>beforeNetTotal=new HashMap<>();
	HashMap<String, String>afterNetTotal=new HashMap<>();
	String stayInfo = null;
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> allTabs = new ArrayList<>();
	
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
	public void folioBalanceReportOnHoldAndGuaranteedReservation(String TestCaseID, String Scenario, String effectiveDate, 
			String resType, String numberOfRooms, String checkInDate, String checkOutDate,			
			String ratePlan, String roomClass, String isPayment, String paymentMethod, String cardNumber, String adults, String children,
			String phoneNumber,String email,String address1,String address2,String address3,String city,String country,String state,
			String postalCode,String marketSegment,String referral,String sortReservationsBy,
			String propertyName,String reservationType,	String includeBalances,String displayAccountName,
			String includePendingFolioItem,String includeAuthorization,String status) throws Throwable {
	
		if(Utility.getResultForCase(driver, TestCaseID))
		{
		test_name = Scenario;
		test_description = "Validate foliobalance  Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - foliobalances Report";
		String testName = test_name;		
		String roomNumber=null;			
		String randomString = Utility.generateRandomStringWithoutNumbers();
		String tripBalance=null;
		guestFirstName = "Auto";
		guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = guestFirstName+" "+guestLastName;
		guestFullName2=guestLastName+" "+guestFirstName;
		
		//GuestFolio guestFolio = new GuestFolio();

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");		
		
		Utility.initializeTestCase(TestCaseID, Utility.testId, Utility.statusCode, Utility.comments, TestCaseID);
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
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			app_logs.info("==========Navigate to Report ==========");			
			test_steps.add("==========Navigate to Report ==========");

			nav.ReportsV2(driver);
			test_steps.add("Click on Reports");		
			report.clickOnNavigateFolioBalances(driver,test_steps);
			report.clearAllButtonClick(driver,test_steps);
			report.clickOnHoldCheckBox(driver, test_steps);
			report.selectSortReportOption(driver, test_steps, sortReservationsBy);
			test_steps.add("Click on Folio Balances Report");
			report.clickrunReportButton(driver, test_steps);
			Wait.wait10Second();
			
			app_logs.info("==========Get folio balance summary view details ==========");			
			test_steps.add("==========Get folio balance summary view details ==========");

			beforeRecivableBalance =report.getRecivableBalance(driver, test_steps,reservationType);
			beforePayableBalance=report.getPayableBalances(driver, test_steps,reservationType);
			beforeNetTotal=report.netTotal(driver, test_steps);
			
			allTabs = new ArrayList<>(driver.getWindowHandles());			
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);	
			Utility.switchTab(driver, applicationTab);

			app_logs.info("==========Create Reservation  "+java.time.LocalTime.now()+"==========");	
			nav.Reservation_Backward_3(driver);
			test_steps.add("==========Create Reservation  "+java.time.LocalTime.now()+"==========");
			reservationDetails=reservationV2Page.createReservation_WithDetails(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
					children, ratePlan, null, roomClass, "", "Mr.", guestFirstName, guestLastName, phoneNumber,
					phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
					referral, paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, "", isQuote, 
					"", "", "", "", "", "", "", "", 
					"", "", "", "", "", "", false, "1235","",false,false);
			
			app_logs.info("==========Change Reservation Successfully ==========");			
			test_steps.add("==========Change Reservation Successfully ==========");

			reservationV2Page.changeReservationStatus(driver, test_steps,status);			
			reservationNumber=reservationDetails.get("ReservationNumber");			
			app_logs.info("reservationNumber: "+reservationNumber);			
			StayInfo stayInfo=reservationV2Page.getStayInfoDetail(driver);
			roomNumber=stayInfo.getSI_ROOM_NUMBER();
			app_logs.info("roomNumber: "+roomNumber);
			TripSummary tripSummary = reservationV2Page.getTripSummaryDetail(driver);
			tripBalance=tripSummary.getTS_BALANCE();
			app_logs.info("reservation_tripBalance: "+tripBalance);
			
	        app_logs.info("==========Created Reservation Successfully ==========");			
			test_steps.add("==========Created Reservation Successfully ==========");			
		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			app_logs.info("========== Verifing folio balance report for on hold reservation type==========");	
			app_logs.info("========== Action started at "+java.time.LocalTime.now()+"Wait for 2 minute==========");	
			Wait.wait60Second();
			Wait.wait60Second();			

			app_logs.info("==========Verify Folio balance report for all type of reservation==========");	
			test_steps.add("==========Verify Folio balance report for all type of  reservation==========");
			Utility.switchTab(driver, reportsTab);
			report.editButtonClick(driver, test_steps);
			report.clickrunReportButton(driver, test_steps);
			Wait.wait10Second();
			app_logs.info("VerifyDetailViewFolioBalanceReportFor");
			String accountSubtotal=report.getFolioBalanceDeatilViewAccountSubtotal(driver, test_steps);
			
			app_logs.info("==========Verify Folio balance report detail view==========");	
			test_steps.add("==========Verify Folio balance report detail view==========");
			
			report.VerifyDetailViewFolioBalanceReportFor(driver, test_steps, reservationNumber, false, false,  roomNumber, roomClass,checkInDate, checkOutDate,
			guestFullName,"", tripBalance, "",  accountSubtotal);
			
			app_logs.info("==========Verify Folio balance report header==========");	
			test_steps.add("==========Verify Folio balance report header==========");
			report.verifyFolioBalanceReportSubHeader(driver,test_steps,propertyName,effectiveDate,reservationType,includeBalances,
			checkInDate,sortReservationsBy, displayAccountName,includePendingFolioItem,includeAuthorization);
			
			
			afterRecivableBalance=report.getRecivableBalance(driver, test_steps,reservationType);
			afterPayableBalance=report.getPayableBalances(driver, test_steps,reservationType);			
			afterNetTotal=report.netTotal(driver, test_steps);
			
			app_logs.info("==========Verify Folio balance report summary view==========");	
			test_steps.add("==========Verify Folio balance report summary view==========");
			report.validateFoliobalanceReportSummaryView(driver, beforeRecivableBalance, afterRecivableBalance, beforePayableBalance, afterPayableBalance,
					reservationType, tripBalance,beforeNetTotal,afterNetTotal,false,true, test_steps);
			
			app_logs.info("==========Verify Folio balance report sort order of reservation by "+sortReservationsBy+" ==========");	
			test_steps.add("==========Verify Folio balance report sort order of reservation by "+sortReservationsBy+" ==========");	
			report.reservationTableSortedByGuestName(driver, test_steps);
			app_logs.info("Verified sort order");
		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			app_logs.info("========== Verifing folio balance report for Gaurenteed reservation type==========");
			app_logs.info("==========Verify Folio balance report for all type of reservation==========");	
			test_steps.add("==========Verify Folio balance report for all type of  reservation==========");
			
			report.editButtonClick(driver, test_steps);
			report.clickClearAllButton(driver, test_steps);
			report.clickGuaranteedCheckBox(driver, test_steps);			
			report.clickrunReportButton(driver, test_steps);
			reservationType="Guaranteed";
			Wait.wait10Second();
			beforeRecivableBalance.clear();
			beforePayableBalance.clear();
			beforeNetTotal.clear();
			
			beforeRecivableBalance =report.getRecivableBalance(driver, test_steps,reservationType);
			beforePayableBalance=report.getPayableBalances(driver, test_steps,reservationType);
			beforeNetTotal=report.netTotal(driver, test_steps);

			app_logs.info("==========Switch to reservation tab and change status of reservation to Gaurenteed ==========");
			Utility.switchTab(driver, applicationTab);
			reservationV2Page.closeAllOpenedReservations(driver);
			reservationV2Page.basicSearch_WithReservationNo(driver, reservationNumber, true);
			Wait.wait5Second();
			status="Guaranteed";
			reservationV2Page.changeReservationStatus(driver, test_steps,status);
			
			app_logs.info("========== Action started at "+java.time.LocalTime.now()+"Wait for 2 minute==========");	
			Wait.wait60Second();
			Wait.wait60Second();
			
			app_logs.info("==========Reruning report==========");	
			test_steps.add("==========Reruning report==========");
			Utility.switchTab(driver, reportsTab);
			report.editButtonClick(driver, test_steps);
			report.clickClearAllButton(driver, test_steps);
			report.clickGuaranteedCheckBox(driver, test_steps);
			report.clickrunReportButton(driver, test_steps);
			Wait.wait10Second();
			
			afterRecivableBalance.clear();
			afterPayableBalance.clear();
			afterNetTotal.clear();
			afterRecivableBalance=report.getRecivableBalance(driver, test_steps,reservationType);
			afterPayableBalance=report.getPayableBalances(driver, test_steps,reservationType);			
			afterNetTotal=report.netTotal(driver, test_steps);
			
			app_logs.info("==========Verify Folio balance report summary view==========");	
			test_steps.add("==========Verify Folio balance report summary view==========");
			report.validateFoliobalanceReportSummaryView(driver, beforeRecivableBalance, afterRecivableBalance, beforePayableBalance, afterPayableBalance,
					reservationType, tripBalance,beforeNetTotal,afterNetTotal,false,true, test_steps);
			
			app_logs.info("VerifyDetailViewFolioBalanceReportFor");
			String accountSubtotal=report.getFolioBalanceDeatilViewAccountSubtotal(driver, test_steps);
			
			app_logs.info("==========Verify Folio balance report detail view==========");	
			test_steps.add("==========Verify Folio balance report detail view==========");
			
			report.VerifyDetailViewFolioBalanceReportFor(driver, test_steps, reservationNumber, false, false,  roomNumber, roomClass,checkInDate, checkOutDate,
			guestFullName,"", tripBalance, "",  accountSubtotal);
			
			app_logs.info("==========Verify Folio balance report header==========");	
			test_steps.add("==========Verify Folio balance report header==========");
			report.verifyFolioBalanceReportSubHeader(driver,test_steps,propertyName,effectiveDate,reservationType,includeBalances,
			checkInDate,sortReservationsBy, displayAccountName,includePendingFolioItem,includeAuthorization);
			
			app_logs.info("==========Verify Folio balance report sort order of reservation by "+sortReservationsBy+" ==========");	
			test_steps.add("==========Verify Folio balance report sort order of reservation by "+sortReservationsBy+" ==========");	
			report.reservationTableSortedByGuestName(driver, test_steps);
			app_logs.info("Verified sort order");
		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
	
		try {
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify foliobalance Report");
			}					
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("FolioBalanceReportOnHold", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode, Utility.comments, TestCore.TestRail_AssignToID);

	}

}
