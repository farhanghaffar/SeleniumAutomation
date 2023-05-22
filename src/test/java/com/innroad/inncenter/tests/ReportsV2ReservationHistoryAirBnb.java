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

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AirbnbInObjects;
import com.innroad.inncenter.pageobjects.AirbnbObjects;
import com.innroad.inncenter.pageobjects.AirbnbReservationUI;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2ReservationHistoryAirBnb extends TestCore{
	
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	private WebDriver driver = null;
	CPReservationPage reservation = new CPReservationPage();
	Navigation navigation = new Navigation();
	AirbnbInObjects airbnbIn = new AirbnbInObjects();
	Admin admin = new Admin();
	ReservationHomePage homePage = new ReservationHomePage();
	ReservationV2 reservationPage=  new ReservationV2();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	AirbnbObjects airbnb = new AirbnbObjects();
	AirbnbReservationUI airbnbReservationUI= new AirbnbReservationUI();
	ReportsV2 report = new ReportsV2();
	FolioNew folio= new FolioNew();
	List<String> foliocount= new ArrayList<String>();
	List<String> reservationcount= new ArrayList<String>();
	String testName = null,currency = null,listingid = null,ratePlanName = "",startDate1 = null,startDate2 = null,
			endDate1 = null,startDate = null, endDate = null,night = "",confirmationNo = null,
			guestFirstName = null, guestName = "";
	HashMap<String, String> getAirbnbDetails = null;
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> stayDates = new ArrayList<String>();
	String externalConfirmation = Utility.generateRandomStringWithGivenLength(5);
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, reportV2excel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	private void createAirBnbReservation(String beginDate, String noOfNights,
			 String firstName,  String lastName,String emails, String adultCount, String childCount,
			 String phoneNum, String baseprice, String payoutamount, String category,String amount, String voidReason,
			 String customFolioName) throws NumberFormatException, ParseException, Exception {
		guestFirstName = firstName + Utility.generateRandomStringWithGivenLength(3);
		driver.close();
		Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
		report.verifyLoadingReport(driver);
		navigation.Reservation_Backward_3(driver);
		app_logs.info("Back to reservation page");
		
		if (!(Utility.validateInput(beginDate))) {
			startDate2=Utility.getCurrentDate("dd/MM/yyyy");
			startDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
			if (!Utility.validateString(noOfNights)) {
				endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
			} else {
				endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
			}
		} else {
			checkInDates = Utility.splitInputData(beginDate);
			startDate1 = checkInDates.get(0);
			if (!Utility.validateString(noOfNights)) {
				endDate1 = Utility.addDays(startDate1, 2);
			} else {

				endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
			}
		}				
		startDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "YYYY-MM-dd");
		endDate = Utility.parseDate(endDate1, "dd/MM/yyyy", "YYYY-MM-dd");			
		stayDates = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
		night = String.valueOf(stayDates.size());
		app_logs.info(startDate);
		app_logs.info(endDate);
		app_logs.info(stayDates);
		app_logs.info(night);
		datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
		guestName = guestFirstName + " " + lastName;
	test_steps.add("<b>===== CREATE AIRBNB RESERVATION =====</b>");
	app_logs.info("=====CREATE AIRBNB RESERVATION=====");
	airbnb.airbnbReservationActions("create", externalConfirmation, startDate, endDate, guestFirstName,
			lastName, listingid, night, emails, adultCount, childCount, phoneNum, baseprice,
			payoutamount, vrvoproperties.getProperty("BookingRequestToken"));
		
	test_steps.add("Created Airbnb reservation successfully: ");
	confirmationNo = reservation.guestUnlimitedSearchAndOpen(driver, test_steps, guestName, true, 12);
	test_steps.add("Verified reservation existing in inncenter");
	app_logs.info("Confirmation No : " + confirmationNo);
	test_steps.add("Confirmation No : " + confirmationNo);
	Wait.wait15Second();
	reservationPage.click_Folio(driver, test_steps);
	List<String> cat= new ArrayList<String>();
	List<String> amt= new ArrayList<String>();
	cat=Utility.splitInputData(category);
	amt=Utility.splitInputData(amount);
	for(int i=0;i<cat.size();i++) {
	folio.addFolioLineItemWithDate(driver, test_steps, startDate2,cat.get(i), amt.get(i));}
	folio.postedLineItem(driver, test_steps, cat.get(0));
	folio.voidLineItemByIndex(driver, test_steps, cat.get(1), voidReason, 1);
	folio.moveFolioByIndex(driver, test_steps, cat.get(0), 1, false, confirmationNo, customFolioName);
	reservationPage.click_History(driver, test_steps);	
	reservationcount= reservationPage.getHitoryDescription(driver, "Reservation");
	foliocount=reservationPage.getHitoryDescription(driver, "FOLIO");
	reservation.closeReservationTab(driver);	
	}
	private void verifyReportAgain(String dateOption, int reservationSize, int folioSize,String finalName, String confirmationNo) throws InterruptedException, ParseException {
		navigation.ReportsV2(driver);
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
		report.verifyReservationHistorySummary(driver,  "Folio", String.valueOf(folioSize), test_steps);
		test_steps.add("========= Verify Detailed View =========");
		app_logs.info("========= Verify Detailed View =========");
		for(String str: reservationcount) {
			report.verifyReservationHistoryDetailed(driver, finalName, confirmationNo, "Reservation", str, test_steps);
		}
		for(String str: foliocount) {
			report.verifyReservationHistoryDetailed(driver, finalName, confirmationNo, "Folio", str, test_steps);
		}
	}
	
	@Test(dataProvider = "getData", groups = "Airbnb")
	public void verifyAirbnbReservationCheckout(String testCaseID,String action,String roomClassName,String beginDate, String noOfNights,
			 String firstName,  String lastName,String emails, String adultCount, String childCount,
			 String phoneNum, String baseprice, String payoutamount, String category,String amount, String voidReason,
			 String customFolioName, String dateOption) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
		
		try {
			testName = "ReportsV2ReservationHistoryAirBnb"+ " " + testCaseID;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("#################################################################################");

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
			
			test_steps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			String property = homePage.getReportsProperty(driver, test_steps);
			test_steps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);
			navigation.admin(driver);
			navigation.clickonClientinfo(driver);
			test_steps.add("<b>===== Getting Currency =====</b>");
			app_logs.info("===== Getting Currency=====");
			admin.clickOnClient(driver, property);
			test_steps.add("Open Property : " + property);
			app_logs.info("Open Property : " + property);
			admin.clickClientOptions(driver, test_steps);
			currency = admin.getDefaultCurrency(driver);
			test_steps.add("Default Currency : " + currency);
			app_logs.info("Default Currency : " + currency);
			navigation.navigateToSetupfromRoomMaintenance(driver);
			test_steps.add("Click Setup");
			app_logs.info("Click Setup");
			navigation.clickAirbnbSetup(driver);
			test_steps.add("Click Vrbo Setup");
			app_logs.info("Click Vrbo Setup");
			test_steps.add("<b>===== Getting Listing ID =====</b>");
			app_logs.info("===== Getting Listing ID=====");
			getAirbnbDetails = airbnbIn.getAirbnbListingId(driver, roomClassName, property);
			listingid = getAirbnbDetails.get("listingId");
			test_steps.add("ListingId ID : " + getAirbnbDetails.get("listingId"));
			app_logs.info("Listing ID " + getAirbnbDetails.get("listingId"));
			ratePlanName = getAirbnbDetails.get("ratePlan");
			test_steps.add("RatePlan ID : " + getAirbnbDetails.get("ratePlan"));
			app_logs.info("RatePlan ID " + getAirbnbDetails.get("ratePlan"));
			test_steps.add("========= Navigating to Reservation Hisory Report =========");
			app_logs.info("========= Navigating to Reservation Hisory Report =========");
			navigation.ReportsV2(driver);
			test_steps.add("Successfully navigated to reports page.");
			app_logs.info("Successfully navigated to reports page.");
			report.navigateToReservationHistoryReport(driver, test_steps);
			test_steps.add("Navigated to Reservation Hisory reports page");					
			app_logs.info("Navigated to Reservation Hisory reports page");	
			report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			boolean notExist =report.runReportNoDataAvailableDisplayed(driver);
			if (notExist){
				createAirBnbReservation(beginDate, noOfNights,
						  firstName,   lastName, emails,  adultCount,  childCount,
						  phoneNum,  baseprice,  payoutamount,  category, amount,  voidReason,
						  customFolioName);	
			 int reservationSize=0, folioSize=0;
			 reservationSize=reservationcount.size();
			 folioSize=foliocount.size();
			 app_logs.info(  "----"+reservationSize );
			 app_logs.info( "----" + folioSize);
			 Wait.wait60Second();
			 Wait.wait60Second();
			 Wait.wait60Second();
			 verifyReportAgain( dateOption,  reservationSize,  folioSize, guestName,  confirmationNo);
		
			}else {
				 String reservationCount=report.getReservationHistorySummaryCount(driver, "Summary View", "Reservation History Report", "Reservation");
				 String folioCount=report.getReservationHistorySummaryCount(driver, "Summary View", "Reservation History Report", "Folio");
				 createAirBnbReservation(beginDate, noOfNights,
						  firstName,   lastName, emails,  adultCount,  childCount,
						  phoneNum,  baseprice,  payoutamount,  category, amount,  voidReason,
						  customFolioName);	
				 int reservationSize=0, folioSize= 0;			
				 reservationSize=reservationcount.size();
				 reservationSize=reservationSize+Integer.parseInt(reservationCount);
				app_logs.info(  "----"+reservationSize );
				folioSize=foliocount.size();
				folioSize=folioSize+Integer.parseInt(folioCount);
				app_logs.info( "----" + folioSize);
				Wait.wait60Second();
				Wait.wait60Second();
				Wait.wait60Second();
				verifyReportAgain(dateOption,  reservationSize,   folioSize, guestName,  confirmationNo);
			}			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Reservation History Report");
			}	
			
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);			
		}catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Airbnb Res And Verify Report", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Airbnb Res And Verify Report", testName,
					test_description, test_catagory, test_steps);
		}	
		}
	}	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("ReportsV2ReservationHistoryAirB", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}	 

}
