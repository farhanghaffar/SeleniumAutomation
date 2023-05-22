package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGenerateGuestStatementswitcherOnInCheckInCheckout extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> roomAbbri = new ArrayList<String>();
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> newRooms = new ArrayList<String>();
	Navigation nav = new Navigation();
	RoomStatus roomstatus = new RoomStatus();
	CPReservationPage res = new CPReservationPage();
	Properties properties = new Properties();
	ReservationSearch revSearch = new ReservationSearch();
	Distribution distrubution = new Distribution();
	RoomClass rc = new RoomClass();

	String testName = null;
	Double depositAmount = 0.0;
	String reservation = null, status = null, yearDate = null, propertyName = null, policyName = null, timeZone = null;
	boolean isGuestRegistrationCheckBox = false;
	boolean isGuestRegistrationCheckBoxCheckout = false;
	Date currentDate = null, previousDate = null;
	int checkoutBalance = 0;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyGenerateGuestStatementSwitcherOn(String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity,String ratePlanName, String rate,String CheckInDate, String CheckOutDate,
			String Adults, String Children,String Salutation, String GuestFirstName, String GuestLastName,
			String PaymentType, String CardNumber, String NameOnCard,String Referral)
			throws ParseException {
		String testCaseID="848496|848495|848498|848497|848151|848145";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "VerifyGenerateGuestStatementswitcherOnInCheckInCheckout";
		test_description = "Verify Generate 'Generate Guest Registration Switcher' While CHECK-IN And Verify Roll Back For DEPARTED Status <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848496' target='_blank'>"
				+ "Click here to open TestRail: 848496</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848495' target='_blank'>"
				+ "Click here to open TestRail: 848495</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848496' target='_blank'>"
				+ "Click here to open TestRail: 848496</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848498' target='_blank'>"
				+ "Click here to open TestRail: 848498</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848497' target='_blank'>"
				+ "Click here to open TestRail: 848497</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848151' target='_blank'>"
				+ "Click here to open TestRail: 848151</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848145' target='_blank'>"
				+ "Click here to open TestRail: 848145</a><br>";
		test_catagory = "CP";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848496|848495|848498|848497|848151|848145", Utility.testId, Utility.statusCode, Utility.comments, "");
		String testCase=null;
		NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		ArrayList<String> roomClassNames = new ArrayList<>();
		ArrayList<String> roomClassAbb = new ArrayList<>();
		NightlyRate nightlyRate= new NightlyRate();
		ArrayList<String> rateplan = new ArrayList<>();
		String roomClassNameNew=null,seasonStartDate=null,seasonEndDate=null;
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			//login_CP(driver);
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
		// Get checkIN and Checkout Date
		try {
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			app_logs.info(CheckInDate);
			
			
			app_logs.info(CheckOutDate);
			seasonStartDate=checkInDates.get(0);
			seasonEndDate=sessionEndDate.get(0);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			rateplan=Utility.splitInputData(ratePlanName);
			app_logs.info(rateplan);
			datesRangeList = Utility.getAllDatesStartAndEndDates(CheckInDate, CheckOutDate);
			app_logs.info(datesRangeList);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Get PropertyName
		try {
			propertyName = properties.getProperty(driver, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
		
		// Create Room Class
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			nav.clickSetup(driver);
			nav.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			ArrayList<String>roomClassAre = Utility.splitInputData(roomClassName);
			for(int i=0;i<roomClassAre.size();i++) {
				String nameofRoomClass=roomClassAre.get(i)+Utility.threeDigitgenerateRandomString();
				String abbofRoomClass="GS"+i+1;
				roomClassNames.add(nameofRoomClass);
				roomClassAbb.add(abbofRoomClass);
			newRoomClass.createRoomClassV2(driver, test_steps, nameofRoomClass, abbofRoomClass, maxAdults, maxPersopns,
					roomQuantity);
			newRoomClass.closeRoomClassTabV2(driver, nameofRoomClass);
			}
			test_steps.add("Room Class Created: <b>" + roomClassNames + "</>");
			app_logs.info("Room Class Created: " + roomClassNames);
			app_logs.info("Room Class Abb: " + roomClassAbb);
			roomClassNameNew=roomClassNames.get(0)+"|"+roomClassNames.get(1);
			app_logs.info("Room Class : " + roomClassNameNew);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, test_steps);
		}
		
		// Create Season
		try {
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, rateplan.get(0),datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", 
					"", "", "", true);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, test_steps);
		}
		// Create New Reservation
		try {
			ArrayList<String> roomsAre = new ArrayList<String>();
			test_steps.add("<b>****Start Creating New Reservation****</b>");
			reservation=res.createBasicMRBReservation(driver, true, CheckInDate, CheckOutDate, Adults, Children,
					ratePlanName, roomClassNameNew, Salutation, GuestFirstName, GuestLastName, 
					"No", roomsAre, PaymentType, CardNumber, NameOnCard, Referral, 
					false, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Set Property Option --Default to Generate Guest Registration on Check Out
		// --Off
		try {
			test_steps.add(
					"<b>****Set Default to Generate Guest Registration Check In - OFF from Property Settings****</b>");
			nav.Setup(driver);
			test_steps.add("Navigated to Setup");
			nav.Properties(driver);
			nav.open_Property(driver, test_steps, propertyName);
			properties.verifyPropertiesOpened(driver, test_steps);
		
			/*test_steps.add("Verify Property Options"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848145' target='_blank'>"
							+ "Click here to open TestRail: 848145</a><br>");			
			  Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify Property Options "); 
			  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
			  */
		    test_steps.add("Search Property: <b>" + propertyName + "</b>");
			Wait.explicit_wait_absenceofelement(OR_Reservation.Spinner, driver);
			nav.click_PropertyOptions(driver, test_steps);
			nav.click_PropertyOptions(driver, test_steps);
			isGuestRegistrationCheckBox = properties.clickGenerateGuestRegistrationCheckBoxOnCheckIn(driver, test_steps,
					"No");
			app_logs.info(isGuestRegistrationCheckBox);
			properties.clickSaveButton(driver);
			properties.PublishButton(driver);
			test_steps.add("Click Publish Button");
			Wait.wait10Second();
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Disabled Generate Guest Registration  Check In - OFF", testName,
						"Properties", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Disabled Generate Guest Registration  Check In - OFF", testName,
						"Properties", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Logged Out Login Again
		try {
			Utility.logoutToInnCenter(driver, test_steps);
	//		login_CP(driver);
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Logout Login", testName, "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Logout Login", testName, "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Verify Generate Guest Registration Check Box On
		try {
			test_steps.add("<b>****Search Reservation****</b>");
			revSearch.basicSearch_WithResNumber(driver, reservation);
			test_steps.add("Search Reservation By Reservation Number <b>" + reservation + "<b>");
			app_logs.info("Search Reservation By Reservation Number " + reservation);
			test_steps.add("<b>****Start CheckIn Reservation****</b>");
			res.clickCheckInAllButton(driver, test_steps);
			test_steps.add("<b>****Verify Generate Guest Registration Toggle- OFF On Check-In ****</b>");
			res.verifyGenerateGuestReportToggle(driver, test_steps, isGuestRegistrationCheckBox);
			/*test_steps.add(
					"Verify the 'Generate Guest Statement' switcher is Not ON in CHECK-IN Modal if 'Default to Generate Statement' is Un-checked in the Property Settings (Multi Reservation)"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848496' target='_blank'>"
							+ "Click here to open TestRail: 848496</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments,
					"Verify the 'Generate Guest Statement' switcher is Not ON in CHECK-IN Modal if 'Default to Generate Statement' is Un-checked in the Property Settings (Multi Reservation)");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
			res.clickCloseButtonOfCheckInAllWindow(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Generate Guest Registration on Check In - OFF", testName,
						"Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Generate Guest Registration on Check In - OFF", testName,
						"Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Set Property Option --Default to Generate Guest Registration Check In --On
		try {
			test_steps.add(
					"<b>****Set Default to Generate Guest Registration Check In- ON from Property Settings****</b>");
			nav.Setup(driver);
			test_steps.add("Navigated to Setup");
			nav.Properties(driver);
			properties.SearchProperty_Click(driver, propertyName, test_steps);
			test_steps.add("Search Property: <b>" + propertyName + "</b>");
			Wait.explicit_wait_absenceofelement(OR_Reservation.Spinner, driver);
			nav.click_PropertyOptions(driver, test_steps);
			isGuestRegistrationCheckBox = properties.clickGenerateGuestRegistrationCheckBoxOnCheckIn(driver, test_steps,
					"Yes");			
			isGuestRegistrationCheckBoxCheckout=properties.clickGenerateGuestRegistrationCheckBoxOnCheckOut(driver, datesRangeList, "Yes");
			properties.clickSaveButton(driver);
			properties.PublishButton(driver);
			test_steps.add("Click Publish Button");
			Wait.wait10Second();
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e,
						"Failed to Enabled Toggle Default to Generate Guest Registration Check In ON from Property Settings",
						testName, "Properties", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e,
						"Failed to Enabled Toggle Default to Generate Guest Registration Check In ON  from Property Settings",
						testName, "Properties", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Logged Out Login Again
		try {
			Utility.logoutToInnCenter(driver, test_steps);
			//login_CP(driver);
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Logout Login", testName, "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Logout Login", testName, "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verified CheckOut Generate Guest Statement on Check In --On
		try {
			test_steps.add("<b>****Search Reservation****</b>");
			revSearch.basicSearch_WithResNumber(driver, reservation);
			test_steps.add("Search Reservation By Reservation Number <b>" + reservation + "<b>");
			app_logs.info("Search Reservation By Reservation Number " + reservation);
			test_steps.add("<b>****Start CheckIn Reservation****</b>");
			res.clickCheckInAllButton(driver, test_steps);
			test_steps.add("<b>****Verify Generate Guest Registration Toggle- ON in Check-In ****</b>");
			res.verifyGenerateGuestReportToggle(driver, test_steps, isGuestRegistrationCheckBox);
			/*test_steps.add(
					"Verify the 'Generate Guest Registration' switcher is ON in CHECK-IN Modal If 'Default to Generate Guest Registration' is Checked in the Property Settings"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/682720' target='_blank'>"
							+ "Click here to open TestRail: C682720</a><br>");
			test_steps.add(
					"Verify the 'Verify the 'Generate Guest Statement' switcher is ON in CHECK-IN Modal if 'Default to Generate Statement' is Checked in the Property Settings (Multi Reservation)"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848495' target='_blank'>"
							+ "Click here to open TestRail: 848495</a><br>");			
			  Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify the 'Verify the 'Generate Guest Statement' switcher is ON in CHECK-IN Modal "
			  		+ "if 'Default to Generate Statement' is Checked in the Property Settings (Multi Reservation)" ); 
			  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
			 
			  res.generatGuestReportToggle(driver, test_steps, "No");
			  res.completeCheckInProcess(driver, test_steps);
			  res.verifySpinerLoading(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Generate Guest Statement ON", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Generate Guest Statement ON", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {
			
			res.clickCheckOutAllButton(driver, test_steps);
			res.verifySpinerLoading(driver);
			Wait.wait10Second();
			res.clickYesButtonRollBackMsg(driver, test_steps);
			res.verifySpinerLoading(driver);
			res.verifyGenerateGuestReportToggle(driver, test_steps, isGuestRegistrationCheckBoxCheckout);
			/*test_steps.add(
					"Verify the 'Generate Guest Statement' switcher is ON in CHECK-OUT Modal if 'Default to Generate Statement' is Checked in the Property Settings (Multi Reservation)"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848498' target='_blank'>"
							+ "Click here to open TestRail: 848498</a><br>");			
			  Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify the 'Generate Guest Statement' switcher is ON in CHECK-OUT Modal if 'Default to Generate Statement' is Checked in the Property Settings (Multi Reservation)"
			  		); 
			  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
			  res.clickCloseRollBackMsg(driver, test_steps);
			  res.closeReservationTab(driver);
			  
			    nav.Setup(driver);
				test_steps.add("Navigated to Setup");
				nav.Properties(driver);
				properties.SearchProperty_Click(driver, propertyName, test_steps);
				test_steps.add("Search Property: <b>" + propertyName + "</b>");
				Wait.explicit_wait_absenceofelement(OR_Reservation.Spinner, driver);
				nav.click_PropertyOptions(driver, test_steps);
				isGuestRegistrationCheckBoxCheckout = properties.clickGenerateGuestRegistrationCheckBoxOnCheckOut(driver, test_steps,
						"No");
				properties.clickSaveButton(driver);
				properties.PublishButton(driver);
				test_steps.add("Click Publish Button");
				Wait.wait10Second();
				Utility.logoutToInnCenter(driver, test_steps);
				//login_CP(driver);
				login_GS(driver);
				test_steps.add("Logged into the application");
				app_logs.info("Logged into the application");

				test_steps.add("<b>****Search Reservation****</b>");
				revSearch.basicSearch_WithResNumber(driver, reservation);
				test_steps.add("Search Reservation By Reservation Number <b>" + reservation + "<b>");
				app_logs.info("Search Reservation By Reservation Number " + reservation);				
				res.clickCheckOutAllButton(driver, test_steps);
				res.verifySpinerLoading(driver);
				res.clickYesButtonRollBackMsg(driver, test_steps);
				res.verifySpinerLoading(driver);
				res.verifyGenerateGuestReportToggle(driver, test_steps, isGuestRegistrationCheckBoxCheckout);
				res.clickCloseRollBackMsg(driver, test_steps);
				res.verifySpinerLoading(driver);
				/*test_steps.add(
						"Verify the 'Generate Guest Statement' switcher is Not ON in CHECK-OUT Modal if 'Default to Generate Statement' is UN-Checked in the Property Settings (Multi Reservation)"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848497' target='_blank'>"
								+ "Click here to open TestRail: 848497</a><br>");			
				  Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify the 'Generate Guest Statement' switcher is Not ON in CHECK-OUT Modal if 'Default to Generate Statement' is UN-Checked in the Property Settings (Multi Reservation)"); 
				  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);*/
				
				
				
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, "Checkout", "Checkout", "Checkout", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, "Checkout","Checkout", "Checkout", testName, test_description,
					test_catagory, test_steps);
		}
		
		try {			
			test_description = "Verifying Lock Functionality<br>";

			testCase="Verifying Lock Functionality";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			String message=null;
			res.roomLock(driver, test_steps);
			 message=res.toasterMsg(driver, test_steps);
			Utility.verifyText(message, "Room assignment lock updated successfully", "Loack functionality", test_steps, app_logs);
			res.roomUnLock(driver, test_steps);
			res.verifyUnlockMessage(driver, test_steps, "Please note that this guest has requested not to have their room changed.");
			res.clickYesButtonRollBackMsg(driver, test_steps);
			res.verifySpinerLoading(driver);
			 message=res.toasterMsg(driver, test_steps);
			Utility.verifyText(message, "Room assignment lock updated successfully", "Loack functionality", test_steps, app_logs);
			/*test_steps.add("Verifying Lock Functionality"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848151' target='_blank'>"
							+ "Click here to open TestRail: 848151</a><br>");			
    		  Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verifying Lock Functionality"); 
			  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
		*/	
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Generate Guest Statement toggle");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, "Checkout", "Checkout", "Checkout", testCase, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, "Checkout","Checkout", "Checkout", testCase, test_description,
					test_catagory, test_steps);
		}
	}
		
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyGGRegistrationCheckInOn", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
