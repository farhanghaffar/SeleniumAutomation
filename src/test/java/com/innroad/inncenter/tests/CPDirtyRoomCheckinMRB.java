package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPDirtyRoomCheckinMRB extends TestCore{
	
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
	CPReservationPage reservationPage = new CPReservationPage();
	ReservationSearch rsvSearch = new ReservationSearch();
	String seasonStartDate=null,seasonEndDate=null,confirmationNo=null,roomClassNameNew=null;
	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbb = new ArrayList<>();
	ArrayList<String> rateplan = new ArrayList<>();
	RatesGrid rateGrid = new RatesGrid();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPDirtyRoomCheckinMRB(String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity, String policyName, String typeOfFees, String percentage, String chargesTypes,
			String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String action) throws ParseException {
		String testCaseID="854936|848423";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPDirtyRoomCheckinMRB";
		test_description = "CPDirtyRoomCheckinMRB<br>"				
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/854936' target='_blank'>"
				+ "Click here to open TestRail: 854936</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848423' target='_blank'>"
				+ "Click here to open TestRail: 848423</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("854936|848423", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 Policies policies = new Policies();
		 NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		 List<String> roomNos= new ArrayList<String>();
		 ArrayList<String> roomNumbers= new ArrayList<String>();
		 
		// Login
			try {

				if (!(Utility.validateInput(checkInDate))) {
					for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
				}
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				seasonStartDate=checkInDates.get(0);
				seasonEndDate=sessionEndDate.get(0);
				app_logs.info(checkInDate);
				app_logs.info(checkOutDate);
				app_logs.info(seasonStartDate);
				app_logs.info(seasonEndDate);
				rateplan=Utility.splitInputData(ratePlanName);
				app_logs.info(rateplan);

				if (!Utility.insertTestName.containsKey(testName)) {
					Utility.insertTestName.put(testName, testName);
					Utility.reTry.put(testName, 0);
				} else {
					//Utility.reTry.replace(testName, 1);
					Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
				}
				datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDates.get(0), checkOutDates.get(0));
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
				app_logs.info("======Start Creating New Room Class======");
				navigation.clickSetup(driver);
				navigation.RoomClass(driver);
				testSteps.add("Navigated to Room Class");
				ArrayList<String>roomClassAre = Utility.splitInputData(roomClassName);
				for(int i=0;i<roomClassAre.size();i++) {
					String nameofRoomClass=roomClassAre.get(i)+Utility.threeDigitgenerateRandomString();
					String abbofRoomClass="RC"+i+1;
					roomClassNames.add(nameofRoomClass);
					roomClassAbb.add(abbofRoomClass);
					newRoomClass.createRoomClassV2(driver, testSteps, nameofRoomClass, abbofRoomClass, maxAdults, maxPersopns,
						roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, nameofRoomClass);
				}
				testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
				app_logs.info("Room Class Created: " + roomClassNames);
				app_logs.info("Room Class Abb: " + roomClassAbb);
				roomClassNameNew=roomClassNames.get(0)+"|"+roomClassNames.get(1);
				app_logs.info("Room Class : " + roomClassNameNew);
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
				nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplan.get(0),datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", 
						"", "", "", false);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				testSteps.add("<b>======Start Creating Reservation ======</b>");
				app_logs.info("======Start Creating Reservation ======");
				reservationPage.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, 
						adults, children, ratePlanName, roomClassNameNew, salutation, guestFirstName, guestLastName, 
						"No", roomNumbers, paymentType, cardNumber, nameOnCard, referral, 
						false, testSteps);
				}catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
			
			// Checkin Reservation and Checkout and RollBack and 
			try {
				testSteps.add("<b>======Start Checkin, Checkout and Roll Back Reservation ======</b>");
				app_logs.info("======Start Checkin, Checkout and Roll Back Reservation ======");
				reservationPage.clickCheckInAllButton(driver, testSteps);
				reservationPage.generatGuestReportToggle(driver, testSteps, "No");
				reservationPage.completeCheckInProcess(driver, testSteps);		
				reservationPage.verifyCheckOutAllButton(driver, testSteps);
				reservationPage.VerifyReservationStatus_Status(driver, "In-House");
				reservationPage.clickCheckOutAllButton(driver, testSteps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.generatGuestReportToggle(driver, testSteps, "No");
				boolean isPaymentWindow = reservationPage.getPaymentWindow(driver);
				if (isPaymentWindow) {
					reservationPage.proceedToCheckOutPayment(driver, testSteps);
					reservationPage.verifySpinerLoading(driver);
					Wait.wait5Second();
					reservationPage.clickLogORPayAuthorizedButton(driver, testSteps);
					reservationPage.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
				}
				reservationPage.verifyRollBackButton(driver, testSteps);
				reservationPage.clickRollBackButton(driver, testSteps);
				reservationPage.verifyRollBackMsg(driver, testSteps,config.getProperty("rollBackMessage"));
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
				Wait.wait5Second();
				reservationPage.cancellationReservation(driver, testSteps, true);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.closeReservationTab(driver);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
			
			// Create Reservation
			try {
				testSteps.add("<b>======Start Create Reservation Again======</b>");
				app_logs.info("======Start Create Reservation Again ======");
				confirmationNo=	reservationPage.createBasicMRBReservation(driver, false, checkInDate, checkOutDate, 
						adults, children, ratePlanName, roomClassNameNew, salutation, guestFirstName, guestLastName, 
						"No", roomNumbers, paymentType, cardNumber, nameOnCard, referral, 
						false, testSteps);
					roomNos= reservationPage.getStayInfoRoomNo(driver, testSteps);
					app_logs.info(roomNumbers);		
					app_logs.info(roomNos);		
					reservationPage.clickCheckInAllButton(driver, testSteps);
					reservationPage.generatGuestReportToggle(driver, testSteps, "No");	
					reservationPage.clickOnConfirmCheckInButton(driver, testSteps);
					Wait.wait60Second();
					reservationPage.verifyRollBackMsg(driver, testSteps,config.getProperty("dirtyMessageMRB"));
					reservationPage.dirtyPopupModelClickCancelButton(driver, testSteps);
					Wait.wait5Second();
					reservationPage.clickCloseRollBackMsg(driver, testSteps);
					reservationPage.verifySpinerLoading(driver);
					reservationPage.closeReservationTab(driver);
					
					testSteps.add("<b>======Change the Room Status from GS======</b>");
					app_logs.info("======Change the Room Status from GS ======");
					
					navigation.navigateGuestservice(driver);
					roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
					roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);
					for(int i=0;i<roomNos.size();i++) {
						roomstatus.searchByRoomHashAndChangeRoomStatus(driver, roomNos.get(i), roomClassNames.get(i), "Clean", testSteps);
					}			
					navigation.navigateToReservationFromGuestServices(driver, testSteps);
					rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);	
					reservationPage.clickCheckInAllButton(driver, testSteps);
					reservationPage.generatGuestReportToggle(driver, testSteps, "No");	
					reservationPage.clickOnConfirmCheckInButton(driver, testSteps);
					reservationPage.verifySpinerLoading(driver);
					reservationPage.VerifyReservationStatus_Status(driver, "IN-HOUSE");
					/*testSteps.add("verifying the check in modal functionality when we click on cancel/confirm in the dirty tab pop up "
							+ "window button for MRB reservation which are created from Reservation tab"
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/854936' target='_blank'>"				
							+ "Click here to open TestRail: 854936</a><br>");
					Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "verifying the check in modal functionality when we click on cancel/confirm in the dirty tab pop up window button for "
							+ "MRB reservation which are created from Reservation tab");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
				*/
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify room status for MRB");
					}
					
					
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
		}
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("CPDirtyRoomCheckinMRB", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
