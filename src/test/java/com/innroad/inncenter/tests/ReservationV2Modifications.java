package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.apache.poi.hssf.record.formula.functions.Today;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.CheckOutSuccessPaymentPopup;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pages.NewRoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class ReservationV2Modifications extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ReservationV2 res = new ReservationV2();
	Navigation nav = new Navigation();
	RoomClass roomclass = new RoomClass();
	NewRoomClass newRoomclass = new NewRoomClass();
	NewRoomClassesV2 roomclassV2 = new NewRoomClassesV2();
	RatesGrid ratesGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	Admin admin = new Admin();
	FolioNew folioNew = new FolioNew();
	
	String ratePlan = "Test Rate", roomClass = "TestRoom", ratePlanType = "";
	String roomAbbr;
	
	String reservationNumber = null, guestFirstName = null, guestLastName, phoneNumber = "1234567890",  
	email = "innRoadTestEmail@innroad.com",	address1 = "10th Building", address2 = "Block C", address3 = "Street 10",
	city = "NewYork", country = "United States", state = "Alaska", postalCode = "12345", referral = "Walk In",
	paymentMethod = "MC", cardNumber = "5454545454545454", currency = null, clientTimeZone, dFormat, sourceOfRes, 
	accountFirstName, accountLastName, reportsTab, applicationTab, currentDay, accountName = "AccountEQSVVD", roomNumber,
	cardExpDate, updateStartDate, updateEndDate, newRoomClassName, roomCharge , guestFullName;
	
	String checkInDate = "19/02/2021";
	String checkOutDate = "21/02/2021";
	String todayDate = Utility.getCurrentDate("dd/MM/yyyy");
	
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
		//loginReportsV2(driver);
		loginReportsV2ReservationV2(driver);
	
	}
	
	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateLedgerBalancesReport() throws Exception {
		String resNumber = "18517034";//"18520007"; // MRB = 18510510
		res.basicSearch_WithReservationNo(driver, resNumber, true);
		StayInfo stayInfo = new StayInfo();
//		stayInfo = res.getStayInfoDetail(driver);
		//res.bulkCheckIn(driver, test_steps);
		//res.bulkCheckOut(driver, test_steps);
		//res.bulkCancel(driver, test_steps, "Cancel", true);
		//res.bulkNoShow(driver, test_steps, true, true);
		
		res.clickAddIncidentals(driver, test_steps);
		res.addIncidental(driver, checkInDate, "POS", "155", "1");
		
		
		String changeOption = "Change only for added/removed dates";
		String checkIn = Utility.parseDate(stayInfo.getSI_CHECK_IN_DATE(), "MMM dd, yyyy", "dd/MM/yyyy");
		String checkOut = Utility.parseDate(stayInfo.getSI_CHECK_OUT_DATE(), "MMM dd, yyyy", "dd/MM/yyyy");
		String newDate = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", checkOut, 2);
		String newCheckOut = Utility.parseDate(newDate, "dd/MM/yyyy", "MM/dd/yyyy");
		String newCheckIn = Utility.parseDate(Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", checkIn, 2), "dd/MM/yyyy", "MM/dd/yyyy");
		app_logs.info("New date: "+newDate);
		app_logs.info("CheckIn: "+checkIn+" CheckOut: "+newCheckOut);
		
		String guestFirstName = "testttt";
		String guestLastName = "addtionall";
		String email = "";
		String phone = "9532145683";
		String roomClass = "Double Bed", roomNumber = null;
		String notesCheckOut = "Test notes";
		

		
		//res.checkInReservation(driver, test_steps);
		//res.cancelReservation(driver, test_steps, true, "Cancel");
		//res.cancel_MrbSecondary(driver, test_steps, true, "Cancel");
		
//		res.rollBackReservationStatus(driver, test_steps);
//		
//		CheckOutSuccessPaymentPopup paymentPopup = new CheckOutSuccessPaymentPopup();
//		paymentPopup = res.checkOutSingleAndMRBAll(driver, test_steps, "No", "No", notesCheckOut);
//		//res.checkOutMRBPrimary(driver, test_steps);
//		app_logs.info("Payment: "+paymentPopup.toString());
//		if (paymentPopup == null) {
//			app_logs.info("No Payment");
//		}
//		res.switchHistoryTab(driver, test_steps);
//		res.verifyHistoryForCheckOut(driver, test_steps);
//		res.switchFolioTab(driver, test_steps);
//		folioNew.clickFolioPaymentsTab(driver, test_steps);
//		folioNew.verifyCheckOutPaymentFolioLineItem(driver, test_steps, paymentMethod, paymentPopup);
//		folioNew.clickOnGivenFolioLineItemDescription(driver, test_steps, "Cash");
//		folioNew.verifyCheckOutPaymentDetailsInFolio(driver, test_steps, paymentMethod, paymentPopup, notesCheckOut);
		
				
		res.extendOrReduceReservation(driver, newCheckOut, "Recalculate Rate", test_steps);
		res.extendOrReduceMRBPrimary(driver, newCheckOut, "Change only for added/removed dates", test_steps);
		res.extendOrReduceMRBSecondary(driver, newCheckOut, "Change only for added/removed dates", test_steps);
		res.modifyReservationDates(driver, newCheckIn, newCheckOut, changeOption,roomClass, test_steps);
//		res.modifyReservationDatesMRBPrimary(driver, newCheckIn, newCheckOut, changeOption, test_steps);
//		res.modifyReservationDatesMRBSecondary(driver, newCheckIn, newCheckOut, changeOption, test_steps);
		res.modifyGuestCount(driver, "Adults", 1, test_steps);
//		res.modifyGuestCountMRBPrimary(driver, "Adults", 1, test_steps);
//		res.modifyGuestCountMRBPrimary(driver, "Children", 1, test_steps);
//		res.modifyGuestCountMRBSecondary(driver, "Adults", 1, test_steps);
//		res.modifyGuestCountMRBSecondary(driver, "Children", 1, test_steps);		
		res.addMoreGuestInfo(driver, guestFirstName, guestLastName, email, phone, test_steps);
		res.assignRoomNumberFromStayInfo(driver, roomClass, test_steps);
		res.assignRoomNumberFromStayInfoMRBPrimary(driver, roomClass, test_steps);
		res.assignRoomNumberFromStayInfoMRBSecondary(driver, roomClass, test_steps);
		res.splitIntoSeparateReservationMRBSecondary(driver, test_steps,2);

		
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport("Modify Reservation", test_description, test_category, test_steps);
		
	}

	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
}
