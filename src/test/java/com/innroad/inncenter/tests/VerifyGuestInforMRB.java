package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGuestInforMRB extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	Create_Reservation cpReservation= new Create_Reservation();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyGuestInforMRB(String ratePlanName,String roomClassName,String updateRoomClass,String checkInDate, String checkOutDate, 
			String adults, String children,String salutation,
			String guestFirstName, String guestLastName, String paymentType,
			String cardNumber, String nameOnCard,String referral) throws ParseException {
		String testCaseID="848594|848408|848291";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String testName = null;
		test_name = "Verify the GUEST Info can be change for Primary and Additional Guests";
		test_description = "Verify the GUEST Info can be change for Primary and Additional Guests<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848594' target='_blank'>"
				+ "Click here to open TestRail: 848594</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848408' target='_blank'>"
				+ "Click here to open TestRail: 848408</a><br>";
		
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		Utility.initializeTestCase("848594|848408|848291",Utility.testId, Utility.statusCode, Utility.comments,"");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		 List<String> roomNos= new ArrayList<String>();
		
		try {
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
				if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
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
		
		// Create Reservation
		try {				
			testSteps.add("========= CREATE MRB RESERVATION ==========");
			app_logs.info("========= CREATE MRB RESERVATION ==========");
			ArrayList<String> roomNumber= new ArrayList<String>();
			reservation.createBasicMRBReservation(driver, false, checkInDate, checkOutDate, 
					adults, children, ratePlanName, roomClassName, salutation, guestFirstName, guestLastName, 
					"No", roomNumber, paymentType, cardNumber, nameOnCard, referral, 
					false, testSteps);
			roomNos= reservation.getStayInfoRoomNo(driver, testSteps);
			app_logs.info(roomNos);		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		
		try {
			
			testSteps.add("========= VERIFY RESERVATION STATUS ==========");
			app_logs.info("========= VERIFY RESERVATION STATUS ==========");
			reservation.verify_CancelButtonInReservationStatusDropdown(driver, testSteps);
			cpReservation.verifyReservationStatusFromDropdown(driver, testSteps);
			reservation.clickOnReservationStatusDropDownIcon(driver);
			Wait.wait5Second();
			testSteps.add("========= EDIT GUEST INFO AND VERIFY ==========");
			app_logs.info("========= EDIT GUEST INFO AND VERIFY==========");
			reservation.clickOnEditButtonGuestInfo(driver);
			reservation.verifyFindGuestProfileDisplayed(driver, testSteps);
			/*testSteps.add(
					"Verify the GUEST Info can be change for Primary and Additional Guests"
			+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848594' target='_blank'>"
			+ "Click here to open TestRail: 848594</a><br>");			
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the GUEST Info can be change for Primary and Additional Guests"); 
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);		*/
			reservation.clicCloseGuestProfile(driver, testSteps);
			testSteps.add("========= EDIT PRIMARY GUEST INFO AND VERIFY ==========");
			app_logs.info("========= EDIT PRIMARY GUEST INFO AND VERIFY ==========");
			reservation.clickStayInfoThreeDots(driver, testSteps, "Change Stay Details", roomNos.get(0));
			reservation.verifySpinerLoading(driver);
			reservation.clickOnFindRooms(driver, testSteps);			
			Wait.wait10Second();
			ArrayList<String> rooms = reservation.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, updateRoomClass);
			reservation.selectRoomToReserve(driver, testSteps, updateRoomClass, rooms.get(0));
			reservation.verifySpinerLoading(driver);			
			reservation.clickSaveButton(driver);
			boolean isExist=Utility.isElementPresent(driver, By.xpath(OR_Reservation.RollBacktPopupYesButton));
			if(isExist) {
			reservation.clickYesButtonRollBackMsg(driver, testSteps);
			}
			ArrayList<String> updatedRoomS= new ArrayList<String>();
			updatedRoomS= reservation.getStayInfoRoomNo(driver, testSteps);
			app_logs.info(updatedRoomS);
			reservation.verifyStayInfoRoomNo(driver, testSteps, updatedRoomS);
			testSteps.add("========= EDIT SECOND GUEST INFO AND VERIFY ==========");
			app_logs.info("========= EDIT SECOND GUEST INFO AND VERIFY ==========");
			reservation.clickStayInfoThreeDots(driver, testSteps, "Change Stay Details", roomNos.get(1));
			reservation.verifySpinerLoading(driver);
			reservation.clickOnFindRooms(driver, testSteps);			
			Wait.waitforPageLoad(50, driver);
			ArrayList<String> rooms1 = reservation.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClassName.split("\\|")[0]);
			reservation.selectRoomToReserve(driver, testSteps, roomClassName.split("\\|")[0], rooms1.get(0));
			reservation.verifySpinerLoading(driver);			
			reservation.clickSaveButton(driver);
			boolean isExist1=Utility.isElementPresent(driver, By.xpath(OR_Reservation.RollBacktPopupYesButton));
			if(isExist1) {
			reservation.clickYesButtonRollBackMsg(driver, testSteps);
			}
			Wait.wait5Second();
			ArrayList<String> updatedRoomS1= new ArrayList<String>();
			updatedRoomS1= reservation.getStayInfoRoomNo(driver, testSteps);
			app_logs.info(updatedRoomS1);
			reservation.verifyStayInfoRoomNo(driver, testSteps, updatedRoomS1);
			
			/*testSteps.add(
					"Verify the GUEST Info can be changed for Primary and Additional Guests"
			+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848408' target='_blank'>"
			+ "Click here to open TestRail: 848408</a><br>");			
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify the GUEST Info can be changed for Primary and Additional Guests"); 
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "GUEST Info can be changed for Primary and Additional Guests");
			}
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify guest Information", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify guest Information", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyGuestInforMRB", gsexcel);
	}
	

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}


}
