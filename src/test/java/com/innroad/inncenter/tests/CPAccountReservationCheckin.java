package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPAccountReservationCheckin extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	Create_Reservation reservationPage= new Create_Reservation();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	ReservationSearch rsvSearch = new ReservationSearch();
	Account account= new Account();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPAccountReservationCheckin(String roomClassName,
			 String maxAdults, String maxPersopns, String roomQuantity, String checkInDate, String checkOutDate, 
			 String ratePlanName, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, 
			String cardNumber,String nameOnCard,String marketSegment, String referral, String child,
			String accountName, String phonenumber,String alternativeNumber, String address1, String address2, String address3, 
			String email, String city, String state, String postalcode) throws ParseException {
		String testCaseID="848377";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String testName = null, roomClassNames = null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null;
		test_name = "CPAccountReservationCheckin";
		test_description = "CPAccountReservationCheckin<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848377' target='_blank'>"
				+ "Click here to open TestRail: 848377</a><br>";
		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848377", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 ArrayList<String> rateplan= new ArrayList<String>();
		 
		// Login
			try {

				if (!(Utility.validateInput(checkInDate))) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
					
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
				}
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
				seasonStartDate=checkInDate;
				seasonEndDate=sessionEndDate.get(0);
				app_logs.info(checkInDate);
				app_logs.info(checkOutDate);
				app_logs.info(seasonStartDate);
				app_logs.info(seasonEndDate);

				if (!Utility.insertTestName.containsKey(testName)) {
					Utility.insertTestName.put(testName, testName);
					Utility.reTry.put(testName, 0);
				} else {
					//Utility.reTry.replace(testName, 1);
					Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
				}
				datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDate, checkOutDate);
				app_logs.info(datesRangeList);
				driver = getDriver();
				login_CP(driver);
				testSteps.add("Logged into the application");
				app_logs.info("Logged into the application");
			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, testSteps);
			}
			// Create Room Class
			try {
				testSteps.add("<b>======Start Creating New Room Class======</b>");
				app_logs.info("<b>======Start Creating New Room Class======</b>");
				navigation.clickSetup(driver);
				navigation.RoomClass(driver);
				testSteps.add("Navigated to Room Class");
				roomClassNames = roomClassName + Utility.fourDigitgenerateRandomString();
				newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults, maxPersopns,
						roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
				testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
				app_logs.info("Room Class Created: " + roomClassNames);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
						test_catagory, testSteps);
			}

			// Create Season
			try {
				testSteps.add("<b>======Start Updating Rate Plan ======</b>");
				app_logs.info("<b>======Start Updating Rate Plan ======</b>");
				nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName,datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", 
						"", "", "", true);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				
				testSteps.add("<b>======Create Account ======</b>");
				app_logs.info("<b>======Create Account  ======</b>");
				guestFirstName= guestFirstName+Utility.threeDigitgenerateRandomString();
				guestLastName= guestLastName+Utility.threeDigitgenerateRandomString();
				accountName=accountName+Utility.generateRandomStringWithoutNumbers();
				navigation.navigateToAccounts(driver, testSteps);
				account.createNewAccount(driver, testSteps, "Corporate/Member Accounts", accountName, marketSegment, referral, 
						guestFirstName, guestLastName, phonenumber, alternativeNumber, address1, address2, address3, 
						email, city, state, postalcode);
				account.clickOnSaveAccountButton(driver, testSteps);
				account.closeAccountTab(driver);
				Wait.waitforPageLoad(30, driver);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Create Account", "Account", "Account", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Account", "Account", "Account", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				
				testSteps.add("<b>======Create Reservation with Account======</b>");
				app_logs.info("<b>======Create Reservation with Account======</b>");					
				confirmationNo=reservationPage.createBasicAccountReservation(driver, checkInDate, checkOutDate, maxAdults, child, 
						ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, 
						nameOnCard, marketSegment, referral, roomClassNames, true, false, testSteps, 
						accountName);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				
				testSteps.add("<b>==========Start Check In Account Reservation==========</b>");
				app_logs.info("<b>==========Start Check In Account Reservation==========</b>");
				reservation.clickCheckInButton(driver, testSteps);
				reservation.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));
				reservation.completeCheckInProcess(driver, testSteps);
				reservation.verifyCheckOutButton(driver, testSteps);				
				
				/*testSteps.add("Hit on Check-In , showing error message"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848377' target='_blank'>"
						+ "Click here to open TestRail: C848377</a><br>");
				Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Hit on Check-In , showing error message ");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
			} catch (Exception e) {
				Utility.catchException(driver, e, "Checkin Reservation ", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Checkin Reservation", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				
				reservation.reservationStatusPanelSelectStatus(driver, "Rollback", testSteps);
				reservation.clickYesButtonRollBackMsg(driver, testSteps);
				reservation.verifySpinerLoading(driver);
				reservation.closeReservationTab(driver);
				rsvSearch.basicSearchResNumber(driver, confirmationNo);
				getTest_Steps=rsvSearch.BulkCancel(driver);
				reservation.verifySpinerLoading(driver);
				testSteps.addAll(getTest_Steps);
				navigation.accounts(driver);
				account.searchForAnAccount(driver, testSteps, "Corporate/Member Accounts", accountName);
				account.deleteAllAccount(driver, accountName, testSteps);
				for(int i=0;i<Utility.testId.size();i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Account reservation checkin");
				}
				
				
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Deleted Create Account", "Account", "Account", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Deleted Account", "Account", "Account", testName, test_description,
						test_catagory, testSteps);
			}
		}
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("CPAccountReservationCheckin", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
