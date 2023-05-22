package com.innroad.inncenter.tests;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyUpdatedNameDateTime_RollBackReservationStatus extends TestCore {

	private WebDriver driver = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> test_name = new ArrayList<>();
	ArrayList<String> test_description = new ArrayList<>();
	ArrayList<String> test_catagory = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyUpdatedNameDateTime_RollBackReservationStatus(String TestCaseID, String Amount, String Referral,
			String FirstName, String LastName, String Account, String RoomClassName, 
			String IncidentalName,String Quentity, String PendingState,
			String Notes, String VoidState, String Description, String Adult,
			String Children, String IsAssign, String isChecked, String Salutation, 
			String HistoryCategory,String ratePlan)
			throws Exception {

		if(!Utility.validateString(TestCaseID)) {
			caseId.add("785603");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}
		
		String testname = "Verify last updated by on the reservation page";
		String testdescription = "Verify last updated by on the reservation page<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824782' target='_blank'>"
				+ "Click here to open TestRail: C824782</a><br>";
		String testcatagory = "Reservations";
		test_name.add(testname);
		test_catagory.add(testcatagory);
		test_description.add(testdescription);
		String testName = testname;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage cpReservationPage = new CPReservationPage();
		String getTimeZone = "";
		Properties properties = new Properties();
		Navigation navigation = new Navigation();
		Admin admin = new Admin();
		ReservationHomePage homePage = new ReservationHomePage();


		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// get property timezone
		try {
			 test_steps.add("Get time zone from peroperty");
			  navigation.setup(driver);
			  navigation.properties(driver);
			  properties.PropertyName(driver);
			  properties.PropertyOptions(driver, test_steps);
			  getTimeZone = properties.getTimeZone(driver);
			  getTimeZone = Utility.getTimeZone(getTimeZone);
			  test_steps.add("Selected time zone: "+getTimeZone);
			  navigation.Reservation_Backward(driver);
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get time zone from property", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get time zone from property", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// get user name from admin 
		String getGuestName = "";
		try {
			  test_steps.add("Get user name from admin");
			  navigation.admin(driver);
			  test_steps.add("Click on admin");
			  admin.clickOnUserTab(driver);
			  test_steps.add("Click on users");
			  admin.clickOnSearchButton(driver);
			  test_steps.add("Click on search button");
			  getGuestName = admin.getUserName(driver, Utility.login_CP.get(2));
			  test_steps.add("User name: "+getGuestName);

			  navigation.navigateToReservations(driver);
			  
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get user name from admin", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String expectedDate = "";
		// Create Reservation
		try {
			test_steps.add("==========CREATE NEW RESERVATION==========");

			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.click_NewReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.checkInDate(driver, 0);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.checkOutDate(driver, +1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.enterAdult(driver, Adult);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.enterChildren(driver, Children);
			test_steps.addAll(getTest_Steps);

			cpReservationPage.select_Rateplan(driver, test_steps,ratePlan , null);
			
			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.clickOnFindRooms(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			
			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.selectRoom(driver, getTest_Steps, RoomClassName, IsAssign, Account);
			test_steps.addAll(getTest_Steps);
			
			
			
			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.clickNext(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			
			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
			test_steps.addAll(getTest_Steps);
			
			LastName = LastName + Utility.GenerateRandomNumber();
			
			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.enter_GuestName(driver, getTest_Steps, Salutation, FirstName, LastName);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.selectReferral(driver, Referral);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			cpReservationPage.clickBookNow(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			String reservationConfirmationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver,getTest_Steps);
			test_steps.add("Reservation confirmation number: " + reservationConfirmationNumber);

			cpReservationPage.get_ReservationStatus(driver, test_steps);
			getTest_Steps.clear();
			cpReservationPage.clickCloseReservationSavePopup(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			// here Update user name
			
			cpReservationPage.clickOnEditButtonGuestInfo(driver);
			test_steps.add("Click on edit guest info icon");
			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.enter_GuestName(driver, getTest_Steps, Salutation, FirstName+"test", LastName);
			test_steps.addAll(getTest_Steps);
			expectedDate = cpReservationPage.saveButtonAfterEditGuestInfo(driver,test_steps,getTimeZone);
			test_steps.add("Click on Save button");
			 

		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		
		// INCIDENTAL IN HISTORY
		try {
			test_steps.add("==========Verify user name and time and date in history tab after updated reservation==========");

			getTest_Steps.clear();
			getTest_Steps = cpReservationPage.ClickOnHistory(driver);
			test_steps.addAll(getTest_Steps);
		
			test_steps.add("Expected guest name: "+getGuestName);
			String getGuestNameFromHistoryTab = cpReservationPage.getUserNameFromHistoryTab(driver);
			test_steps.add("Found: "+getGuestNameFromHistoryTab);
			if (getGuestNameFromHistoryTab.equalsIgnoreCase(getGuestName)) {
				test_steps.add("Verified guest name is mathching in histroy tab after updated reservation");
			}
			else {
				test_steps.add("Failed: Guest name is mismatching  in history tab");
			}
			String strSplit[] = expectedDate.split(" "); 
			test_steps.add("Expected date: "+strSplit[0]);
			String getDateFromHistoryTab = cpReservationPage.getDateFromHistoryTab(driver);
			test_steps.add("Found: "+strSplit[0]);
			if (getDateFromHistoryTab.equalsIgnoreCase(strSplit[0])) {
				test_steps.add("Verified date is mathching in histroy tab after updated reservation");
			}
			else {
				test_steps.add("Failed: date is mismatching  in history tab");
			}
			test_steps.add("Expected Time: "+strSplit[1]+" "+strSplit[2]);
			String getTimeFromHistoryTab = cpReservationPage.getTimeFromHistoryTab(driver);
			test_steps.add("Found: "+getTimeFromHistoryTab);
			if (getTimeFromHistoryTab.equalsIgnoreCase(strSplit[1]+" "+strSplit[2])) {
				test_steps.add("Verified time is mathching in histroy tab after updated reservation");
			}
			else {
				test_steps.add("Failed: time is mismatching  in history tab");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify incidental in hostory", testName, "Incidental", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify incidental in hostory", testName, "Incidental", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

			test_name.clear();
			test_description.clear();
			test_steps.clear();
			testname = "Verify Roll Back reservation from checkin";
			testdescription = "Verify below statuses will be displayed but cannot be edited, apart from rolling back thereservation<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824784' target='_blank'>"
					+ "Click here to open TestRail: C824784</a><br>";
			test_name.add(testname);
			test_description.add(testdescription);
			test_steps.add("Opened existing eservation");
			cpReservationPage.Click_CheckInButton(driver, test_steps);
			cpReservationPage.disableGenerateGuestReportToggle(driver, test_steps);
			getTest_Steps.clear();
			getTest_Steps = homePage.clickConfirmChekInButton(driver);
			test_steps.addAll(getTest_Steps);

			try {
				getTest_Steps.clear();
				getTest_Steps = homePage.clickPayButton(driver);
				test_steps.addAll(getTest_Steps);

			} catch (Exception e) {
			}

			try {
				getTest_Steps.clear();
				getTest_Steps = homePage.clickCloseCheckinSuccessfullPopup(driver);
				test_steps.addAll(getTest_Steps);
			} catch (Exception e) {

			}
			test_steps.add("Reservation checked in");
			app_logs.info("Reservation checked in");
			
			String expectedStatus = "IN-HOUSE";
			String getReservationStatus = cpReservationPage.getReservationStatusOnDetailSection(driver);
			test_steps.add("Expected reservation status after checked in: "+expectedStatus);
			test_steps.add("Found: "+getReservationStatus);
			if (getReservationStatus.equals(expectedStatus)) {
				test_steps.add("Verified reservation has been checked in");
			}
			else {
				test_steps.add("Reservation status is mismatching after checked in");

			}
			String[] test= {"Rollback"};
			cpReservationPage.verifyReservationDropDown(driver,test);
			cpReservationPage.changeReservationStatus(driver, "Rollback");
			test_steps.add("Change reservation status: "+"Rollback");
			
			getTest_Steps.clear();
			cpReservationPage.roolBackReservationPopup(driver);
			test_steps.addAll(getTest_Steps);

			expectedStatus = "RESERVED";
			getReservationStatus = cpReservationPage.getReservationStatusOnDetailSection(driver);
			test_steps.add("Expected reservation status after checked in: "+expectedStatus);
			test_steps.add("Found: "+getReservationStatus);
			if (getReservationStatus.equals(expectedStatus)) {
				test_steps.add("Verified reservation has been rool backed from check in");
			}
			else {
				test_steps.add("Reservation status is mismatching after rool backed");
			}
			test_steps.add("=====Verify Stutus after RollBakced reservation from check in");
			String afterRoolBackedStatus[] = {"Confirmed","Guaranteed","On Hold","Cancel","No Show"};
			cpReservationPage.verifyReservationDropDown(driver,afterRoolBackedStatus);			

		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify reservation status after roll back", testName, "Incidental", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify reservation status after roll back", testName, "Incidental", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	@DataProvider
	public Object[][] getData() {

		// return test data from the sheet name provided
		return Utility.getData("VerifyUpdatedName_RollBacked", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);

	}

}
