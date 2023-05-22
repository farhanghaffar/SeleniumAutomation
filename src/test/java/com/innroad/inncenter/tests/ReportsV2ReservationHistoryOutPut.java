package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.ReservationV2Search;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2ReservationHistoryOutPut extends TestCore{

	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation nav = new Navigation();
	ReportsV2 report = new ReportsV2();
	CPReservationPage reservation = new CPReservationPage();
	ReservationV2 reservationPage=  new ReservationV2();
	String testName=null,propertyName=null;
	Properties prop= new Properties();
	ReservationV2Search rsvSearch= new ReservationV2Search();
	Users user= new Users();
	String confirmationNo=null, guestName=null;
	List<String> reservationcount= new ArrayList<String>();
	List<String> paymentcount= new ArrayList<String>();
	
	private String createReservation(String checkInDate, String checkOutDate,String adults,
			String children,String rateplan,String roomClass,String salutation,String guestFirstName,
			String guestLastName,String paymentType, String cardNumber,String nameOnCard,String marketSegment, String referral) throws Exception {
		String confirmNo=null;
		driver.close();
		Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
		report.verifyLoadingReport(driver);
		nav.Reservation_Backward_3(driver);
		app_logs.info("Back to reservation page");
		String yearDate=Utility.getFutureMonthAndYearForMasterCard();
		HashMap<String, String> reservationData= new HashMap<String, String>();
		reservationData=reservationPage.createReservation_RV2(driver, test_steps, "From Reservations page", checkInDate, checkOutDate, adults,
				children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, true,
				"", "", "", "", "", "", "", "", "", "",false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
				0, "",false, "","", "","", "","", "", "", "","", "", "","", "",false, "");
		confirmNo=reservationData.get("ReservationNumber");
		reservationPage.click_History(driver, test_steps);		
		reservationcount= reservationPage.getHitoryDescription(driver, "Reservation");
		paymentcount=reservationPage.getHitoryDescription(driver, "PAYMENT");
		reservation.closeReservationTab(driver);
		return confirmNo;
	}
	
	
	private void verifyReport(String dateOption, String checkInDate, int reservationSize, int paymentSize, int folioSize,String finalName, String confirmationNo) throws InterruptedException, ParseException {
		nav.ReportsV2(driver);
		test_steps.add("Successfully navigated to reports page.");
		app_logs.info("Successfully navigated to reports page.");
		report.navigateToReservationHistoryReport(driver, test_steps);
		test_steps.add("Navigated to Reservation Hisory reports page");					
		app_logs.info("Navigated to Reservation Hisory reports page");	
		report.selectDateRange(driver, dateOption, test_steps);
		report.clickOnRunReport(driver);
		test_steps.add("Run Report");					
		app_logs.info("Run Report");		
		report.verifyLoadingReport(driver);		
		String date=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");			
		String dateRange= ""+dateOption+" | "+date+" to "+date+"";
		HashMap<String, String> mainHeaderData=report.getMainHeaderReservationHistoryReport(driver, test_steps);
		HashMap<String, String> middleHeaderData=report.getMiddleHeaderReservationHistoryReport(driver, test_steps);	
		test_steps.add("========= Verify Main and Middle Header =========");
		 app_logs.info("========= Verify Main and Middle Header =========");
		report.verifyMainHeaderReservationHistoryData(driver, test_steps, mainHeaderData, dateRange, "Reservation", "Guest Name", "All Users");
		report.verifyMiddleHeaderReservationHistoryData(driver, test_steps, middleHeaderData, "Reservation History Report", dateRange, "Reservation", "Guest Name", date, "All Users");
		test_steps.add("========= Verify Summary View =========");
		app_logs.info("========= Verify Summary View =========");
		report.verifyReservationHistorySummary(driver, "Reservation", String.valueOf(reservationSize), test_steps);
		report.verifyReservationHistorySummary(driver,  "Payment", String.valueOf(paymentSize), test_steps);
		report.verifyReservationHistorySummary(driver,  "Folio", String.valueOf(folioSize), test_steps);
		test_steps.add("========= Verify Detailed View =========");
		app_logs.info("========= Verify Detailed View =========");
		report.verifyReservationHistoryDetailed(driver, finalName, confirmationNo, "Reservation", reservationcount.get(0), test_steps);
	}
	
	private void closeDriverAndSwtchToReservationPageAndSearchReservation(String confirmationNo) throws InterruptedException {
		driver.close();
		Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
		nav.Reservation_Backward_3(driver);
		app_logs.info("Back to reservation page");
		rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
	}
	private void verifyReportAgain(String dateOption, int reservationSize, int paymentSize,int folioSize,String finalName, String confirmationNo) throws InterruptedException, ParseException {
		reservationPage.click_History(driver, test_steps);
		reservationcount.clear();
		reservationcount= reservationPage.getHitoryDescription(driver, "Reservation");
		app_logs.info(reservationcount);
		reservation.closeReservationTab(driver);
		Wait.wait60Second();
		Wait.wait60Second();
		Wait.wait60Second();
		nav.ReportsV2(driver);
		test_steps.add("Successfully navigated to reports page.");
		app_logs.info("Successfully navigated to reports page.");
		report.navigateToReservationHistoryReport(driver, test_steps);
		test_steps.add("Navigated to Reservation Hisory reports page");					
		app_logs.info("Navigated to Reservation Hisory reports page");	
		report.selectDateRange(driver, dateOption, test_steps);
		report.clickOnRunReport(driver);
		test_steps.add("Run Report");					
		app_logs.info("Run Report");		
		report.verifyLoadingReport(driver);		
		test_steps.add("========= Verify Summary View =========");
		app_logs.info("========= Verify Summary View =========");
		report.verifyReservationHistorySummary(driver, "Reservation", String.valueOf(reservationSize), test_steps);
		report.verifyReservationHistorySummary(driver,  "Payment", String.valueOf(paymentSize), test_steps);
		report.verifyReservationHistorySummary(driver,  "Folio", String.valueOf(folioSize), test_steps);
		test_steps.add("========= Verify Detailed View =========");
		app_logs.info("========= Verify Detailed View =========");
		for(String str: reservationcount) {
			report.verifyReservationHistoryDetailed(driver, finalName, confirmationNo, "Reservation", str, test_steps);
		}
	}
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, reportV2excel))
			throw new SkipException("Skipping the test - " + testName);
	}	
	
	
	
	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void reportsV2ReservationHistoryUI(String testCaseID,String checkInDate, String checkOutDate,String adults,
			String children,String rateplan,String roomClass,String salutation,String guestFirstName,
			String guestLastName,String paymentType, String cardNumber,String nameOnCard,String marketSegment, String referral,
			String dateOption, String firstName, String lastName, String emailID, String accountName,String modifyAccount,
			String unitOwner, String modifyUnitOwner, String travelAgent, String modifyTravelAgent, String houseAccount, String giftCertificate) throws ParseException {
		
		boolean isExecutable=Utility.getResultForCase(driver, testCaseID);
		if(isExecutable) {		
		test_name = "ReportsV2 - VerifyReservationHistory";
		test_description = "ReportsV2 - VerifyReservationHistory"+testCaseID+"<br>" ;
		test_category = "ReportsV2 - Reservation History";
		testName = test_name + "--" + testCaseID;
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode,
				Utility.comments, "");		
		// Login
				try {			
						if (!(Utility.validateInput(checkInDate))) {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						} else {
						checkInDates = Utility.splitInputData(checkInDate);
						checkOutDates = Utility.splitInputData(checkOutDate);}
						checkInDate = checkInDates.get(0);
						checkOutDate = checkOutDates.get(0);
					app_logs.info(checkInDate);
					app_logs.info(checkOutDate);
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
					}					
					driver = getDriver();
					loginReportsV2(driver);
					test_steps.add("Logged into the application");
					app_logs.info("Logged into the application");	
					} catch (Exception e) {
					Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_category, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_category, test_steps);
				}				
		try {
			test_steps.add("========= Navigating to Reservation Hisory Report =========");
			app_logs.info("========= Navigating to Reservation Hisory Report =========");
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to reports page.");
			app_logs.info("Successfully navigated to reports page.");
			report.navigateToReservationHistoryReport(driver, test_steps);
			test_steps.add("Navigated to Reservation Hisory reports page");					
			app_logs.info("Navigated to Reservation Hisory reports page");	
			String message ="Select the reservation history categories that you would like to include in the report.";
			report.mouseOverIncludeReservationHistoryCategory(driver, test_steps, message);
			report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			boolean notExist =report.runReportNoDataAvailableDisplayed(driver);
			if (notExist){
				test_steps.add("========= Report Data not Available, Create New Reservation=========");
				app_logs.info("========= Report Data not Available, Create New Reservation =========");
				guestFirstName=guestFirstName+Utility.fourDigitgenerateRandomString();
				guestLastName=guestLastName+Utility.fourDigitgenerateRandomString();
				String finalName= guestFirstName+" "+guestLastName;
				confirmationNo=createReservation(checkInDate,  checkOutDate, adults,
						 children, rateplan, roomClass, salutation, guestFirstName,
						 guestLastName, paymentType,  cardNumber, nameOnCard, marketSegment,  referral);	
				int reservationSize=0, paymentSize=0,folioSize= 0;
					 reservationSize=reservationcount.size();
				app_logs.info(  "----"+reservationSize );
				if(!paymentcount.isEmpty()){
					 paymentSize=paymentcount.size();
					app_logs.info( "----" + paymentSize);
				}
				Wait.wait60Second();
				Wait.wait60Second();
				Wait.wait60Second();
				test_steps.add("========= Verify Confirmation Number Generated =========");
				app_logs.info("=========Verify Confirmation Number Generated =========");				
				verifyReport(dateOption, checkInDate, reservationSize,paymentSize,folioSize, finalName,confirmationNo);
				closeDriverAndSwtchToReservationPageAndSearchReservation(confirmationNo);
				reservationPage.changeReservationStatus(driver, test_steps, "Confirmed");
				reservationPage.clickEditGuestInfo(driver, test_steps);
				guestFirstName=firstName+Utility.fourDigitgenerateRandomString();
				guestLastName= lastName+Utility.fourDigitgenerateRandomString();
			    finalName= guestFirstName+" "+guestLastName;
				reservationPage.enter_GuestName(driver, test_steps, guestFirstName, guestLastName);
				reservationPage.enter_Email(driver, test_steps, emailID);
				Wait.wait5Second();
				reservationPage.associatAccount_RV2(driver, test_steps, accountName, "Corp", "7");
				reservationPage.clickEditGuestInfo(driver, test_steps);
				Wait.wait5Second();
				reservationPage.deleteAssociatedAccount(driver, test_steps);
				reservationPage.clickOnGuestProfilePopupSaveButton(driver, test_steps);
				Wait.wait5Second(); 
				reservationPage.clickEditGuestInfo(driver, test_steps);
				Wait.wait5Second(); 
				reservationPage.associatAccount_RV2(driver, test_steps, modifyAccount, "Corp", "9");
				reservationPage.clickEditGuestInfo(driver, test_steps);
				Wait.wait5Second();
				reservationPage.deleteAssociatedAccount(driver, test_steps);
				Wait.wait5Second();
				reservationPage.associatAccount_RV2(driver, test_steps, unitOwner, "Unit", "13");
				Wait.wait5Second();
				reservationPage.clickEditGuestInfo(driver, test_steps);
				reservationPage.deleteAssociatedAccount(driver, test_steps);
				Wait.wait5Second();
				reservationPage.clickOnGuestProfilePopupSaveButton(driver, test_steps);	
				reservationPage.clickEditGuestInfo(driver, test_steps);
				Wait.wait5Second();
				reservationPage.associatAccount_RV2(driver, test_steps, modifyUnitOwner, "Unit", "14");
				reservationPage.clickOnEditMarketingInfoIcon(driver, test_steps);
				reservationPage.selectTravelAgent_RV2(driver, test_steps, travelAgent);
				reservationPage.clickOnEditMarketingInfoIcon(driver, test_steps);
				reservationPage.deleteAssociatedTravelAgent(driver, test_steps);
				reservationPage.clickOnSaveMarketingInfo(driver, test_steps);
				reservationPage.clickOnEditMarketingInfoIcon(driver, test_steps);
				reservationPage.selectTravelAgent_RV2(driver, test_steps, modifyTravelAgent);
				Wait.wait5Second();
				reservationPage.clickEditPaymentMethod(driver, test_steps);
				reservationPage.enter_PaymentDetails(driver, test_steps, "Account (House Account)", "", "", "", houseAccount);
				reservationPage.clickOnSavePaymentInfo(driver, test_steps);
				Wait.wait5Second();
				reservationPage.clickEditPaymentMethod(driver, test_steps);
				reservationPage.clickDeletePaymentMethod(driver, test_steps);
				reservationPage.enter_PaymentDetails(driver, test_steps, "Gift Certificate", giftCertificate, "", "", "");
				reservationPage.clickOnSavePaymentInfo(driver, test_steps);
				test_steps.add("========= Verify Report =========");
				app_logs.info("=========Verify Report  =========");	
				reservationSize=reservationSize+11;
				paymentSize=paymentSize+2;
				verifyReportAgain( dateOption,  reservationSize,  paymentSize,folioSize,finalName,  confirmationNo);			
				}else {
				   String reservationCount=report.getReservationHistorySummaryCount(driver, "Summary View", "Reservation History Report", "Reservation");
				   String paymentCount=report.getReservationHistorySummaryCount(driver, "Summary View", "Reservation History Report", "Payment");
				   String folioCount=report.getReservationHistorySummaryCount(driver, "Summary View", "Reservation History Report", "Folio");
				 	guestFirstName=guestFirstName+Utility.fourDigitgenerateRandomString();
					guestLastName=guestLastName+Utility.fourDigitgenerateRandomString();
					String finalName= guestFirstName+" "+guestLastName;
					confirmationNo=createReservation(checkInDate,  checkOutDate, adults,
							 children, rateplan, roomClass, salutation, guestFirstName,
							 guestLastName, paymentType,  cardNumber, nameOnCard, marketSegment,  referral);					
							
					int reservationSize=0, paymentSize=0,folioSize= 0;			
					 reservationSize=reservationcount.size();
					 reservationSize=reservationSize+Integer.parseInt(reservationCount);
					app_logs.info(  "----"+reservationSize );
					if(!paymentcount.isEmpty()){
						 paymentSize=paymentcount.size();
						 }
					paymentSize=paymentSize+Integer.parseInt(paymentCount);
					app_logs.info( "----" + paymentSize);
					folioSize=folioSize+Integer.parseInt(folioCount);	
					app_logs.info( "----" + folioSize);
					Wait.wait60Second();
					Wait.wait60Second();
					Wait.wait60Second();
					test_steps.add("========= Verify Confirmation Number Generated =========");
					app_logs.info("=========Verify Confirmation Number Generated =========");				
					verifyReport(dateOption, checkInDate, reservationSize,paymentSize, folioSize,finalName,confirmationNo);
					closeDriverAndSwtchToReservationPageAndSearchReservation(confirmationNo);
					reservationPage.changeReservationStatus(driver, test_steps, "Confirmed");
					Wait.wait2Second();
					reservationPage.clickEditGuestInfo(driver, test_steps);
					guestFirstName=firstName+Utility.fourDigitgenerateRandomString();
					guestLastName= lastName+Utility.fourDigitgenerateRandomString();
				    finalName= guestFirstName+" "+guestLastName;
					reservationPage.enter_GuestName(driver, test_steps, guestFirstName, guestLastName);
					reservationPage.enter_Email(driver, test_steps, emailID);
					Wait.wait5Second();
						reservationPage.associatAccount_RV2(driver, test_steps, accountName, "Corp", "7");
					reservationPage.clickEditGuestInfo(driver, test_steps);
					Wait.wait5Second();
					reservationPage.deleteAssociatedAccount(driver, test_steps);
					reservationPage.clickOnGuestProfilePopupSaveButton(driver, test_steps);
					Wait.wait5Second(); 
					reservationPage.clickEditGuestInfo(driver, test_steps);
					Wait.wait5Second(); 
					reservationPage.associatAccount_RV2(driver, test_steps, modifyAccount, "Corp", "9");
					reservationPage.clickEditGuestInfo(driver, test_steps);
					Wait.wait5Second();
					reservationPage.deleteAssociatedAccount(driver, test_steps);
					Wait.wait5Second();
					reservationPage.associatAccount_RV2(driver, test_steps, unitOwner, "Unit", "13");
					Wait.wait5Second();
					reservationPage.clickEditGuestInfo(driver, test_steps);
					reservationPage.deleteAssociatedAccount(driver, test_steps);
					Wait.wait5Second();
					reservationPage.clickOnGuestProfilePopupSaveButton(driver, test_steps);	
					reservationPage.clickEditGuestInfo(driver, test_steps);
					Wait.wait5Second();
					reservationPage.associatAccount_RV2(driver, test_steps, modifyUnitOwner, "Unit", "14");
					reservationPage.clickOnEditMarketingInfoIcon(driver, test_steps);
					reservationPage.selectTravelAgent_RV2(driver, test_steps, travelAgent);
					reservationPage.clickOnEditMarketingInfoIcon(driver, test_steps);
					reservationPage.deleteAssociatedTravelAgent(driver, test_steps);
					reservationPage.clickOnSaveMarketingInfo(driver, test_steps);
					reservationPage.clickOnEditMarketingInfoIcon(driver, test_steps);
					reservationPage.selectTravelAgent_RV2(driver, test_steps, modifyTravelAgent);
					Wait.wait5Second();
					reservationPage.clickEditPaymentMethod(driver, test_steps);
					reservationPage.enter_PaymentDetails(driver, test_steps, "Account (House Account)", "", "", "", houseAccount);
					reservationPage.clickOnSavePaymentInfo(driver, test_steps);
					Wait.wait5Second();
					reservationPage.clickEditPaymentMethod(driver, test_steps);
					reservationPage.clickDeletePaymentMethod(driver, test_steps);
					reservationPage.enter_PaymentDetails(driver, test_steps, "Gift Certificate", giftCertificate, "", "", "");
					reservationPage.clickOnSavePaymentInfo(driver, test_steps);
					test_steps.add("========= Verify Report =========");
					app_logs.info("=========Verify Report  =========");
					reservationSize=reservationSize+11;
					paymentSize=paymentSize+2;
					verifyReportAgain( dateOption,  reservationSize,  paymentSize,folioSize,finalName,  confirmationNo);
					
		
			}
			driver.close();
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Net Sales Report");
			}	
			
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_category, test_steps);
		}
	}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2ReservationHistoryOutp", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {		
		driver.quit();
		
	}

}
