package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPDirtyStatusGroupReservation extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	String testName = null;
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	RoomStatus roomstatus = new RoomStatus();
	CPReservationPage reservation = new CPReservationPage();
	ReservationSearch rsvSearch = new ReservationSearch();
	Groups group= new Groups();
	String roomClassNames = null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getFinalData", groups = "CP")
	public void cPDirtyStatusGroupReservation(String checkInDate, String checkOutDate, 
			String roomClassName, String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, 
			String cardNumber,String nameOnCard,String marketSegment, String referral, String child,
			String groupName, String groupReferral, String groupFirstName,String groupLastName, 
			String groupPhone,String groupAddress, String groupCity,String groupState, String groupCountry,
			String groupPostalcode) throws ParseException {
		String testCaseID="848501|848439";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPDirtyStatusGroupReservation";
		test_description = "CPDirtyStatusGroupReservation<br>"
				+ "<a href=https://innroad.testrail.io/index.php?/cases/view/848501' target='_blank'>"
				+ "Click here to open TestRail: 848501</a><br>"
				+  "<a href=https://innroad.testrail.io/index.php?/cases/view/848439' target='_blank'>"
				+ "Click here to open TestRail: 848439</a><br>";

		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848501|848439", Utility.testId, Utility.statusCode, Utility.comments, "");	
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
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
		// Create Reservation and Verify Room Status of GS with Arrival Due
				try {
					
					testSteps.add("<b>======Create Reservation ======</b>");
					app_logs.info("<b>======Create Reservation ======</b>");					
					confirmationNo=reservation.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child, 
							ratePlanName, salutation, guestFirstName, guestLastName, "No",
							paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassNames, true,false);					
					
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				
				// Checkin Reservation and Checkout and RollBack and 
				try {
					testSteps.add("<b>======Start Checkin and Roll Back Reservation ======</b>");
					app_logs.info("<b>======Start Checkin and Roll Back Reservation ======</b>");	
					reservation.clickCheckInButton(driver, testSteps);
					reservation.generatGuestReportToggle(driver, testSteps, "No");
					reservation.completeCheckInProcess(driver, testSteps);	
					Wait.wait5Second();
					reservation.reservationStatusPanelSelectStatus(driver,"Rollback",testSteps);
					Wait.wait10Second();
					reservation.verifyRollBackMsg(driver, testSteps,config.getProperty("rollBackMessage"));
					reservation.clickYesButtonRollBackMsg(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.closeReservationTab(driver);
    				rsvSearch.basicSearchResNumber(driver, confirmationNo);
					rsvSearch.bulkActionCancellation(driver);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				
				try {
					ArrayList<String> roomNumber= new ArrayList<String> ();
					testSteps.add("<b>======Create Group Reservation ======</b>");
					app_logs.info("<b>======Create GroupReservation ======</b>");	
					ArrayList<String> accountNo= new ArrayList<String>();
					navigation.groups(driver);
					group.createGroupAccount(driver, testSteps, groupName, true, test, marketSegment, groupReferral,
							groupFirstName, groupLastName, groupPhone, groupAddress, groupCity, groupState, groupCountry, 
							groupPostalcode, accountNo);
					group.clickOnNewReservationButtonFromGroup(driver, testSteps);
					Wait.waitforPageLoad(30, driver);
					reservation.select_CheckInDate(driver, testSteps, checkInDate);
					reservation.select_CheckoutDate(driver, testSteps, checkOutDate);
					reservation.enter_Adults(driver, testSteps, maxAdults);
					reservation.enter_Children(driver, testSteps, child);
					reservation.select_Rateplan(driver, testSteps, ratePlanName, "");
					confirmationNo=reservation.createGroupReservation(driver, testSteps, roomClassNames, "", "", salutation, guestFirstName,
							guestLastName, "No", paymentType, cardNumber, nameOnCard, Utility.expiryDate,
							"No", "Single", roomNumber, confirmationNo);
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Group Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Group Reservation", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
		
				try {
					testSteps.add("<b>======Start Checkin Group Reservation ======</b>");
					app_logs.info("<b>======Start Checkin Group Reservation ======</b>");	
					reservation.clickCheckInButton(driver, testSteps);
					reservation.generatGuestReportToggle(driver, testSteps, "No");
					reservation.clickOnConfirmCheckInButton(driver, testSteps);
					String dirtyMessage="The room status is dirty. Do you wish to proceed with check in?";
					reservation.verifyRollBackMsg(driver, testSteps,dirtyMessage);
					
				/*	testSteps.add("verifying the check in modal functionality when we click on cancel/confirm in the dirty tab pop up window button for the group reservation."
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848501' target='_blank'>"
							+ "Click here to open TestRail: C848501</a><br>");
					Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the dirty room status pop-up message when user click on confirm check-in button for Group Reservation");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
				
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

				}catch (Exception e) {
					Utility.catchException(driver, e, "Checkin Reservation and Dirty Message", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Checkin Reservation and Dirty Message", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				
				String testCase=null;
				try {
					
					 testCase="Verify Check out functionality when reservation is booked from group";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}	
					testSteps.add("<b>======Start Checkout Group Reservation ======</b>");
					app_logs.info("<b>======Start Checkinout Group Reservation ======</b>");	
					reservation.dirtyPopupModelClickConfirmButton(driver, testSteps, true);
					reservation.verifySpinerLoading(driver);
					reservation.clickCheckOutButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.generatGuestReportToggle(driver, testSteps, "No");
					reservation.proceedToCheckOutPayment(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.clickLogORPayAuthorizedButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.verifyRollBackButton(driver, testSteps);
					
					/*testSteps.add("Verify Check out functionality when reservation is booked from group"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848439' target='_blank'>"
							+ "Click here to open TestRail: C848439</a><br>");
					Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify Check out functionality when reservation is booked from group");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
*/
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Dirty room for Group Reservation and verify checkout functionality");
					}
					
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Checkout Group Reservation", "Reservation", "Reservation", testCase,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Checkout Group Reservation", "Reservation", "Reservation", testCase, test_description,
							test_catagory, testSteps);
				}
		}
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSVerifyArrivalDueScenarios", gsexcel);

	}
	@DataProvider
	public Object[][] getDataOne() {

		return Utility.getData("CPDirtyStatusGroupReservation", gsexcel);

	}
		@DataProvider
		public Object[][] getFinalData()
		{
			 return Utility.combine(getData(),  getDataOne());
		} 

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
