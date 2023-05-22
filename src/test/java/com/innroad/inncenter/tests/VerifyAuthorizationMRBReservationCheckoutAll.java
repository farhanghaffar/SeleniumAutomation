package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAuthorizationMRBReservationCheckoutAll extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> teststepsTwo = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	Properties properties = new Properties();
	RoomClass roomClass = new RoomClass();
	Reports report = new Reports();
	Rate rate = new Rate();
	NightlyRate nightlyRate= new NightlyRate();
	Policies policies = new Policies();
	NewRoomClassesV2 newRoomclass= new NewRoomClassesV2();
	Folio folio = new Folio();
	CPReservationPage reservationPage = new CPReservationPage();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	ArrayList<String> roomAbbri = new ArrayList<String>();
	ArrayList<String> roomNumber = new ArrayList<>();	
	String  propertyName = null, roomClassNames = null, roomClassAbbs = null,
			 rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservation = null,roomClassName1=null, roomClassName2=null, roomClassAbb1=null, roomClassAbb2=null,
			 date=null,last4Digit=null,folioBalance=null, dateHistory=null, authDate=null,seasonStartDate=null,seasonEndDate=null;
	String testName = this.getClass().getSimpleName().trim();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
		
	}

	private void handelingCatchError(Error e, String desc, String category, String module) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	private void handelingCatchException(Exception e, String desc, String category, String module) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	
	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyAuthorizationMRBReservationCheckoutAll(String roomClassName,String maxAdults,
			String maxPersons, String roomQuantity, String checkInDate, String checkOutDate, String adults,String children,String ratePlan, String rates,String salutation,
			String guestFirstName, String guestLastName,String paymentType, String cardNumber, String nameOnCard,
			String marketSegment, String referral, String autorizedIcon, String notes) throws ParseException {
		String testCaseID="848670|848700|848669";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_description = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682399' target='_blank'>"
				+ "Click here to open TestRail: C682399</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848670' target='_blank'>"
				+ "Click here to open TestRail: 848670</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848700' target='_blank'>"
				+ "Click here to open TestRail: 848700</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848669' target='_blank'>"
				+ "Click here to open TestRail: 848669</a><br>";
		test_catagory = "CPReservation_CheckOut";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		randomNum = Utility.generateRandomNumber();
		String randomNum1=Utility.generateRandomNumber();
		roomClassName1 = roomClassName+randomNum;
		roomClassAbb1 = roomClassName1;
		roomClassName2 = roomClassName+randomNum1;
		roomClassAbb2 = roomClassName2;
		Utility.initializeTestCase("848670|848700|848669", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}

			// Get CheckIN and Checkout Date
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			seasonStartDate=checkInDates.get(0);
			seasonEndDate=sessionEndDate.get(0);
			datesRangeList = Utility.getAllDatesStartAndEndDates(seasonStartDate, seasonEndDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			app_logs.info(datesRangeList);			
			date=Utility.parseDate(checkInDates.get(0), "dd/MM/yyyy", "E MMM dd, yyyy");
			app_logs.info(date);
			authDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MM/dd/yyyy");
			app_logs.info(authDate);
			dateHistory=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MM/dd/yy");
			app_logs.info(dateHistory);    
	
		} catch (Exception e) {
			handelingCatchException(e, "Failed to Get Check In Date and Checkout Date", testName, "Pre-Requisites");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Get Check In Date and Checkout Date", testName, "Pre-Requisites");
		}

		// Login
		try {
			driver = getDriver();
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");			
			} catch (Exception e) {
			handelingCatchException(e, "Failed to login", testName, "Login");
		} catch (Error e) {
			handelingCatchError(e, "Failed to login", testName, "Login");
		}
		try {

			test_steps.add("<b>========== Creating 1st room class ==========</b>");
				navigation.mainSetupManu(driver);
			navigation.roomClass(driver, test_steps);
			newRoomclass.deleteAllRoomClassV2(driver, roomClassName);
			newRoomclass.createRoomClassV2(driver,test_steps, roomClassName1, roomClassAbb1, maxAdults, maxPersons, roomQuantity);			
			roomNumberAssigned.add(Utility.RoomNo);
			newRoomclass.closeRoomClassTabV2(driver, roomClassName1);
			test_steps.add("<b>========== Creating 2nd room class ==========</b>");
			newRoomclass.createRoomClassV2(driver, test_steps,roomClassName2, roomClassAbb2, maxAdults, maxPersons, roomQuantity  );			
			newRoomclass.closeRoomClassTabV2(driver, roomClassName2);		
			roomNumberAssigned.add(Utility.RoomNo);
			for (int i = 0; i < roomNumberAssigned.size(); i++) {
				roomNumberAssigned.get(i);
			}
			app_logs.info(roomNumberAssigned);
			roomAbbri.add(roomClassName1 + ":" + roomClassAbb1);
			roomAbbri.add(roomClassName2 + ":" + roomClassAbb2);
			app_logs.info(roomAbbri);
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Create Room Class", testName, "Setup");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create Room Class", testName, "Setup");
		}
		
		try
		{
			ArrayList<String> ratePlanName= Utility.splitInputData(ratePlan);
			roomClassNames=roomClassName1+"|"+roomClassName2;
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			app_logs.info("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName.get(0),datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rates, "", 
					"", "", "", true);
			
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Create Rates", testName, "Rates");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create Rates", testName, "Rates");
		}
	
		try
		{
			ArrayList<String> roomNo= new ArrayList<String>();
			reservationPage.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, adults, children, ratePlan,
					roomClassNames, salutation, guestFirstName, guestLastName, "No", roomNo, paymentType, cardNumber, nameOnCard,
					referral, false, test_steps);	
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Create Reservation", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Create Reservation", testName, "Reservation");
		}
		
		try
		{
			test_steps.add("<b>==========Start Check In==========</b>");
			reservationPage.clickCheckInAllButton(driver, test_steps);
			reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));
			reservationPage.completeCheckInProcess(driver, test_steps);
			test_steps.add("<b>==========Start Verifying Check Out All Button ==========</b>");
			reservationPage.verifyCheckOutAllButton(driver, test_steps);
			test_steps.add("<b>==========Start Verifying IN-HOUSE Status==========</b>");
			reservationPage.verifyReservationStatusStatus(driver, test_steps, config.getProperty("inHouse"));
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Check In", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Check In", testName, "Reservation");
		}
		
			
		try
		{
			test_steps.add("<b>==========Start Take Payment==========</b>");
			reservationPage.click_Folio(driver, test_steps);
			String folioname="Guest Folio For "+roomClassAbb1+": "+roomNumberAssigned.get(0)+"";
			app_logs.info("Folio Name is:"+folioname );
			folioBalance=reservationPage.get_Balance(driver, test_steps);
			reservationPage.click_Pay(driver, test_steps);
			last4Digit=cardNumber.substring(cardNumber.length() - 4);
			app_logs.info("Card NO is:"+last4Digit );
			String cardNo="XXXX "+last4Digit+"";
			app_logs.info("Card NO is:"+cardNo );
			reservationPage.paymentCheckInPopupVerify(driver, test_steps, "Take Payment", Utility.expiryDate, nameOnCard, cardNo, paymentType, "", "", ""
					, "", "", test_steps, test_steps, "", "", "");
			reservationPage.takePaymentAuthorization(driver, test_steps, "Take Payment","Authorization Only", folioBalance,folioname);
		    reservationPage.addNotesAndClickLogORPayORAuthorizedButton(driver, test_steps, notes);		   
		    reservationPage.authorizedSuccessVerification(driver, test_steps, "Authorization Successful", authDate, folioname, folioBalance, folioBalance, "Authorization Only", paymentType, last4Digit, nameOnCard, Utility.expiryDate, "Approved", notes);
		    reservationPage.clickCloseButtonOfAuthorizedSuccess(driver, test_steps); 
		    reservationPage.saveReservation(driver,test_steps);
		    Wait.explicit_wait_absenceofelement(OR_Reservation.loading, driver);
		    app_logs.info("Page Load");
		}catch (Exception e) {
			handelingCatchException(e, "Failed to take payment", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to take payment", testName, "Reservation");
		}
		
		
		try
		{
			test_steps.add("<b>======= Verifying amount details and icon at folio tab ========</b>");			
			folio.verifyPostPendingStatus(driver, test_steps, checkInDate, "Authorization");
			folio.verifyFolioLineItem(driver, test_steps, date, paymentType, null, folioBalance, nameOnCard, last4Digit, Utility.expiryDate);
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Verify Folio line item", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Verify Folio line item", testName, "Reservation");
		}
		try
		{
			test_steps.add("<b>========Start Verifying History========</b>");
			reservationPage.click_History(driver, test_steps);
			String roomNos=""+roomClassAbb1+": "+roomNumberAssigned.get(0)+"";
			app_logs.info("History Room Verification: "+roomNos);
			reservationPage.verifyHistoryTabForAuthorizedPayment(driver, test_steps, dateHistory, folioBalance,false, null, paymentType, last4Digit, Utility.expiryDate,roomNos);
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Verify History", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Verify History", testName, "Reservation");
		}
		
		
		try
		{
			test_steps.add("<b>========Start CheckOut All Info========</b>");
			reservationPage.click_DeatilsTab(driver, test_steps);
			String balance=reservationPage.get_TripSummaryBalance_Amount(driver, test_steps);
			reservationPage.clickCheckOutAllButton(driver, test_steps);
			reservationPage.clickYesButtonOfCheckOutAllConfirmationMsg(driver, test_steps, config.getProperty("checkOutAllMsg"), "CheckOutAll", "", "");
			reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));
			reservationPage.proceedToCheckOutPayment(driver, test_steps);
			double balanceOne = Double.valueOf(balance);
			int balanceAmt=(int) balanceOne;
			reservationPage.verifyCheckoutPaymentForAuthorization(driver, test_steps, "Check Out Payment", "Capture", balance);		
			
			
			/*test_steps.add("Verify balance is displaying properly while check out the reservation with pending authorization line item."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848669' target='_blank'>"
					+ "Click here to open TestRail: C848669</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify balance is displaying properly while check out the reservation with pending authorization line item.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		
			test_steps.add("Verify authorization picker in check out screen and observe remaining authorization should be voided."
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848670' target='_blank'>"
					+ "Click here to open TestRail: C848670</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify Authrization should be considered in Check out when multiple Authorization");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			
			test_steps.add("Verify  Authrization should be considered in Check out when multiple Authorization"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848700' target='_blank'>"
					+ "Click here to open TestRail: C848700</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify balance is displaying properly while check out the reservation with pending authorization line item");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Authrization should be considered in Check out when multiple Authorization");
			}
			
		}
		catch (Exception e) {
			handelingCatchException(e, "Failed to Verify Checkout All", testName, "Reservation");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Verify Checkout All", testName, "Reservation");
		}

		try
		{
			test_steps.add("<b>========Delete Room Class========</b>");
			navigation.setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.roomClass(driver, test_steps);
			newRoomclass.deleteAllRoomClassV2(driver, roomClassName);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			handelingCatchException(e, "Failed to Delete Room Class", testName, "Setup");
		} catch (Error e) {
			handelingCatchError(e, "Failed to Delete Room Class", testName, "Setup");
		}
		}
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAuthorizationMRBCheckoutA", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
