package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyDirtyRoomCheckin extends TestCore{
	
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
	String roomClassNames = null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null;
	TaskManagement task_mang = new TaskManagement();
	
	private void checkinCheckoutAndRollBack() throws Exception {
		reservation.clickCheckInButton(driver, testSteps);
		reservation.generatGuestReportToggle(driver, testSteps, "No");
		reservation.completeCheckInProcess(driver, testSteps);		
		reservation.verifyCheckOutButton(driver, testSteps);
		reservation.clickCheckOutButton(driver, testSteps);
		Wait.wait10Second();
		reservation.generatGuestReportToggle(driver, testSteps, "No");
		boolean isPaymentWindow = reservation.getPaymentWindow(driver);
		if (isPaymentWindow) {
			reservation.proceedToCheckOutPayment(driver, testSteps);
			reservation.verifySpinerLoadings(driver);
			Wait.wait25Second();
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
		}
		reservation.verifyRollBackButton(driver, testSteps);
		reservation.clickRollBackButton(driver, testSteps);
		reservation.verifyRollBackMsg(driver, testSteps,config.getProperty("rollBackMessage"));
		reservation.clickYesButtonRollBackMsg(driver, testSteps);
		reservation.toasterMsg(driver, testSteps);
		reservation.closeReservationTab(driver);
	}

	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPVerifyDirtyRoomCheckin(String checkInDate, String checkOutDate, 
			String roomClassName, String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, 
			String cardNumber,String nameOnCard,String marketSegment, String referral, String child) throws ParseException {
		String testCaseID="848630|848500";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifyDirtyRoomCheckin";
		test_description = "GSVerifyArrivalDueScenarios<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848500' target='_blank'>"
				+ "Click here to open TestRail: 848500</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848630' target='_blank'>"
				+ "Click here to open TestRail: 848630</a><br>";

		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848630|848500", Utility.testId, Utility.statusCode, Utility.comments, "");		
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 ArrayList<String> roomNos= new ArrayList<String>();
		 ArrayList<String> roomNumbers= new ArrayList<String>();
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
				
				navigation.clickSetup(driver);
				navigation.TaskManagement_TabExist(driver);
				testSteps.add("Task Management Tab Exist");
				navigation.TaskManagement(driver);
				testSteps.add("Click on Task Management");
				app_logs.info("Click on Task Management");
				task_mang.SetRoomsToDirtyFlag(driver, true);
				testSteps.add("Successfully Enabled Toggle Set Rooms To Dirty On Check-In/Check-Out ");
				app_logs.info("Successfully Enabled Toggle Set Rooms To Dirty On Check-In/Check-Out");
				
				testSteps.add("<b>======Start Creating New Room Class======</b>");
				app_logs.info("======Start Creating New Room Class======");
				navigation.navigateToSetupfromTaskManagement(driver);
				navigation.RoomClass(driver);
				testSteps.add("Navigated to Room Class");
				roomClassNames = roomClassName + Utility.fourDigitgenerateRandomString();
				roomNumbers=newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults, maxPersopns,
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
				app_logs.info("======Start Updating Rate Plan ======");
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
				testSteps.add("<b>======Start Creating Reservation ======</b>");
				app_logs.info("======Start Creating Reservation ======");
				confirmationNo=reservation.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child, 
						ratePlanName, salutation, guestFirstName, guestLastName, "No",
						paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassNames, true,false);	
				checkinCheckoutAndRollBack();	
				rsvSearch.basicSearchResNumber(driver, confirmationNo);
				rsvSearch.bulkActionCancellation(driver);
				
				testSteps.add("<b>======Start Creating Reservation Again======</b>");
				app_logs.info("======Start Creating Reservation Again======");
				confirmationNo=reservation.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child, 
						ratePlanName, salutation, guestFirstName, guestLastName, "No",
						paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassNames, false,false);	
				for(String s: roomNumbers) {
					roomNos.add(s);
				}
				roomNos.remove(0);
				app_logs.info(roomNos);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
		
			try {
				testSteps.add("<b>======Verify Dirty Room Message for Rooms======</b>");
				app_logs.info("======Verify Dirty Room Message for Rooms======");
				reservation.clickCheckInButton(driver, testSteps);
				reservation.generatGuestReportToggle(driver, testSteps, "No");	
				reservation.clickOnConfirmCheckInButton(driver, testSteps);
				String dirtyMessage="The room status is dirty. Do you wish to proceed with check in?";
				reservation.verifyRollBackMsg(driver, testSteps,dirtyMessage);
				testSteps.add("<b>======Verify Dirty Room Highlighted======</b>");
				app_logs.info("======Verify Dirty Room Highlighted======");
				reservation.dirtyPopupModelClickCancelButton(driver, testSteps);
				String message="Dirty rooms are highlighted in the stay info section.";
				String toastermessage=reservation.toasterMsg(driver, testSteps);
				Utility.verifyText(toastermessage.toLowerCase().trim(), message.toLowerCase().trim(), message, testSteps, app_logs);				
				
				/*testSteps.add("Verify the behavior when user select \"NO\" from Dirty room pop-up message at the time of check-in."
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848630' target='_blank'>"
						+ "Click here to open TestRail: C848630</a><br>");
				Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the behavior when user select \"NO\" from Dirty room pop-up message at the time of check-in");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);				
				
				testSteps.add("verifying check in modal functionality for the reservation which are created from Reservation tab when we click on cancel button"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848500' target='_blank'>"
						+ "Click here to open TestRail: C848500</a><br>");
				Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "verifying check in modal functionality for the reservation which are created from Reservation tab when we click on cancel button");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
		*/
				reservation.selectRoomAtPaymentCheckInPopup(driver, testSteps, roomNos);
				reservation.clickOnConfirmCheckInButton(driver, testSteps);
				Wait.wait5Second();
				reservation.verifyCheckOutButton(driver, testSteps);
			   /* String message1="Stay info Successfully Saved";				
				String toastermessage1=reservation.toasterMsg(driver, testSteps);
				if(toastermessage1.contains("Stay info")) {
				Utility.verifyText(toastermessage1.toLowerCase().trim(), message1.toLowerCase().trim(), message1, testSteps, app_logs);
				
				}
				reservation.verifySpinerLoading(driver);
				String message2="Check-In Successful";
				String toastermessage2=reservation.toasterMsg(driver, testSteps);
				if(toastermessage1.contains("Check-In")) {
					Utility.verifyText(toastermessage2.toLowerCase().trim(), message2.toLowerCase().trim(), message2, testSteps, app_logs);		
				}
				if(toastermessage2.contains("Data")) {
					 toastermessage2=reservation.toasterMsg(driver, testSteps);
					 Utility.verifyText(toastermessage2.toLowerCase().trim(), message2.toLowerCase().trim(), message2, testSteps, app_logs);		
				}*/
				
				for(int i=0;i<Utility.testId.size();i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Rooms Dirty after checkin");
				}
				
					RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Dirty Room Checkin Reservation", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Dirty Room Checkin  Reservation", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
			

			// Delete Room Class
			try {
				testSteps.add("<b>======Delete Room Class======</b>");
				navigation.navigateToSetupFromReservationPage(driver, testSteps);
				testSteps.add("Navigated to Setup");
				navigation.RoomClass(driver);
				newRoomClass.searchRoomClassV2(driver, roomClassName);
				testSteps.add("Click on Search Button");
				app_logs.info("Click on Search Button");
				newRoomClass.deleteAllRoomClassV2(driver, roomClassName);
				testSteps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
				app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Delete Room Class", "GS", "GS", testName, test_description,
						test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Delete Room Class", "GS", "GS", testName, test_description, test_catagory,
						testSteps);
			}
		}
	}

	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSVerifyArrivalDueScenarios", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
