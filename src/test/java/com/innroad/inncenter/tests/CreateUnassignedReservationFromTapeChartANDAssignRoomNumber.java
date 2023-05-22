package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CreateUnassignedReservationFromTapeChartANDAssignRoomNumber extends TestCore {

	// Automation-1449
	private WebDriver driver = null;

	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	String test_name = "Verify the updates in the parking lot(Changed to Unassigned).";
	public static String test_description = "";
	public static String test_catagory = "";

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "TapeChart")
	public void createUnassignedReservationFromTapeChart(String RateName, String BaseAmount, String AddtionalAdult,
			String AdditionalChild, String DisplayName, String AssociateSession, String RatePolicy,
			String RateDescription, String RoomClassAbb, String roomClassName, String bedsCount, String maxAdults,
			String maxPersons, String roomQuantity, String StartRoomNumber, String RatePlan, String RateType,
			String RateAttributes, String Interval, String Source, String Adults, String Child, String MarketSegment,
			String Referral, String FirstName, String LastName, String GroupPhn, String GroupAddress, String Groupcity,
			String Groupcountry, String Groupstate, String GroupPostalcode, String BlockName, String RoomPerNight,
			String Salutation) throws InterruptedException, IOException {

		test_name = "Verify the updates in the parking lot(Changed to Unassigned).";
		test_description = "Create Unassigned Reservation -> Tape Chart<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848636' target='_blank'>"
				+ "Click here to open TestRail: C848636</a><br>"
				
				+"	Change Unassigned Reservation to Assigned <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848159' target='_blank'>"
				+ "Click here to open TestRail: 848159</a><br>";

		String TestCaseID = "848636|848159";
		if(Utility.getResultForCase(driver, TestCaseID)) {
		{
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
				comments = "retest";
			}

		}

		test_catagory = "TapeChart";
		String testName = test_name;
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		Tapechart tap = new Tapechart();
		ReservationSearch resSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();
		CPReservationPage cpres = new CPReservationPage();
		String RandomNumber = Utility.GenerateRandomNumber(99);
		DisplayName = RateName;
		String ReservationNumber = null;
		String AfterUnassignedReservations = "0";
		String BeforeUnassignedReservations = "0";
		String AfterUnassignedReservationsRC = "0";
		String BeforeUnassignedReservationsRC = "0";
		LastName = LastName + RandomNumber;
		String ReservationName = FirstName + " " + LastName;
		String paymentType = "MC";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			driver = getDriver();
//			login_CP(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "CP_Login"));
			test_steps.add("Entered appication URL : " + TestCore.envURL);
			test_steps.add("Logged into the application ");
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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Observe parking lots(Unassigned Reservations) in TapeChart
		try {
			app_logs.info("==========GET UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			test_steps.add("==========GET UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			nav.navTapeChart(driver, test);
			test_steps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");
			getTest_Steps.clear();
			getTest_Steps = tap.TapeChartSearch(driver, Utility.getCurrentDate("MM/dd/yyyy"),
					Utility.GetNextDate(1, "MM/dd/yyyy"), Adults, Child, RatePlan, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			tap.ClickUnassigned(driver);
			app_logs.info("Click Unassigned button");
			test_steps.add("Click Unassigned button");
			Thread.sleep(3000);
			BeforeUnassignedReservations = tap.GetandVerifyUnassignedReservationNumber(driver, true);
			app_logs.info("Number of  Unassigned Reservation : " + BeforeUnassignedReservations);
			test_steps.add("Number of  Unassigned Reservations : " + BeforeUnassignedReservations);
			app_logs.info(
					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
			test_steps.add(
					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
			app_logs.info("==========GET UNASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");
			test_steps.add("==========GET UNASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");

			BeforeUnassignedReservationsRC = tap.GetUnassignedRoomClassCount(driver, RoomClassAbb);
			app_logs.info("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ BeforeUnassignedReservationsRC + "'");
			test_steps.add("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ BeforeUnassignedReservationsRC + "'");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Unassigned Reservation From Tape Chart
		try {
			app_logs.info("==========CREATE UNASSIGNED RESERVATION FROM TAPECHART==========");
			test_steps.add("==========CREATE UNASSIGNED RESERVATION FROM TAPECHART==========");
			tap.ClickUnassignedRoomClass(driver, RoomClassAbb, test_steps);
			String roomnumber = cpres.GETRoomNumber(driver, test_steps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			test_steps.add("Verified Room Number is Unassigned");
			cpres.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, FirstName,
					LastName, "no");
			cpres.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
			cpres.enter_PaymentDetails(driver, test_steps, "MC", "5454545454545454", "Test_User", "12/23");
			cpres.clickBookNow(driver, test_steps);
			ReservationNumber = cpres.get_ReservationConfirmationNumber(driver, test_steps);
			Object ReservationStatus = cpres.get_ReservationStatus(driver, test_steps);
			try {
			Assert.assertEquals(ReservationStatus, "Reserved", "Failed: Reservation Status missmatched");
			}catch(Error er) {
				Assert.assertEquals(ReservationStatus, "Confirmed", "Failed: Reservation Status missmatched");
			}
			cpres.clickCloseReservationSavePopup(driver, test_steps);
			roomnumber = cpres.get_RoomNumber(driver, test_steps, "No");
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			test_steps.add("Verified Room Number is Unassigned");
			String TripTotal = cpres.get_TripSummaryTripTotalChargesWithoutCurrency(driver, getTest_Steps);
			Utility.app_logs.info("Trip Total '" + TripTotal + "'");

		} catch (Exception e) {
			if (Utility.count == RetryFailedTestCases.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create unassigned reservation from Tape Chart", testName,
						"CreateUnassignedReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create unassigned reservation from Tape Chart", testName,
						"CreateUnassignedReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Observe parking lots(Unassigned Reservations) in TapeChart
		try {
			driver.navigate().refresh();
			app_logs.info("==========VERIFY UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			test_steps.add("==========VERIFY UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			nav.navTapeChart(driver, test);
			test_steps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");
			tap.ClickUnassigned(driver);
			app_logs.info("Click Unassigned button");
			test_steps.add("Click Unassigned button");
			Thread.sleep(3000);
			AfterUnassignedReservations = tap.GetandVerifyUnassignedReservationNumber(driver, true);
			app_logs.info("Number of Unassigned Reservations : " + AfterUnassignedReservations);
			test_steps.add("Number of Unassigned Reservations : " + AfterUnassignedReservations);
//			app_logs.info(
//					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
//			test_steps.add(
//					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
//			Assert.assertTrue(
//					Integer.parseInt(AfterUnassignedReservations) > Integer.parseInt(BeforeUnassignedReservations),
//					"Failed: Unassigned Count not increase");
//			app_logs.info("Verified Number of Unassigned Reservations has been incremented");
//			test_steps.add("Verified Number of Unassigned Reservations has been incremented");
			tap.verifyReservationInUnassignedList(driver, test_steps, ReservationName, true, true);
			tap.VerifyUnassignedReservationColor(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
					Utility.GetNextDate(1, "E, MMM dd"), RoomClassAbb, test_steps);
			app_logs.info(
					"Successfully verified Reservation '" + ReservationName + "' Exist in Unassigned Reservations");
			test_steps.add(
					"Successfully verified Reservation '" + ReservationName + "' Exist in Unassigned Reservations");
			tap.VerifyUnassignedReservation(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
					Utility.GetNextDate(1, "E, MMM dd"), RoomClassAbb);
			app_logs.info("==========VERIFY UNASSIGNED RESERVATION DETAILS==========");
			test_steps.add("==========VERIFY UNASSIGNED RESERVATION DETAILS==========");
			test_steps.add("Reservation Name : " + ReservationName);
			test_steps.add("Arrival Date : " + Utility.getCurrentDate("E, MMM dd"));
			test_steps.add("Departure Date : " + Utility.GetNextDate(1, "E, MMM dd"));
			test_steps.add("Room Class Abbreviation : " + RoomClassAbb);
			app_logs.info("==========VERIFY UNASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");
			test_steps.add("==========VERIFY UNASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");
Wait.wait5Second();
			AfterUnassignedReservationsRC = tap.GetUnassignedRoomClassCount(driver, RoomClassAbb);
			app_logs.info("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ AfterUnassignedReservationsRC + "'");
			test_steps.add("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ AfterUnassignedReservationsRC + "'");
			Assert.assertTrue(
					Integer.parseInt(AfterUnassignedReservationsRC) > Integer.parseInt(BeforeUnassignedReservationsRC),
					"Failed: Unassigned Count not increase");
			app_logs.info("Verified Room Class '" + RoomClassAbb + "' Unassigned Reservations has been incremented");
			test_steps.add("Verified Room Class '" + RoomClassAbb + "' Unassigned Reservations has been incremented");
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Search Reservation and Assign Room
		try {
			app_logs.info("==========SEARCH RESERVATION==========");
			test_steps.add("==========SEARCH RESERVATION==========");
			nav.navigateToReservations(driver);
			test_steps.add("Navigate Reservation ");
			app_logs.info("Navigate Reservation ");
			driver.navigate().refresh();
			Thread.sleep(1000);
			resSearch.Close_Unassigned_Tab(driver);
			Thread.sleep(500);
			resSearch.basicSearch_WithResNumber(driver, ReservationNumber);
			test_steps.add("Reservation Successfully Searched : " + ReservationNumber);
			app_logs.info("Reservation Successfully Searched : " + ReservationNumber);

			app_logs.info("==========ASSIGN THE ROOM==========");
			test_steps.add("==========ASSIGN THE ROOM==========");

			Thread.sleep(500);
			reservationPage.ClickEditStayInfo(driver, test_steps);
			Thread.sleep(500);
			String Room_No = reservationPage.AssignRoomNumber(driver);
			app_logs.info(Room_No + " Room Assigned");
			test_steps.add(Room_No + " Room Assigned");
			Thread.sleep(500);
			reservationPage.clickSaveButton(driver);
			app_logs.info("Clicked On Save Button");
			test_steps.add("Clicked On Save Button");
			
			

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Reservation and verify Room Class", testName, "TapeChart",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Reservation and verify Room Class", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Number Is Assigned
		try {
			driver.navigate().refresh();
			app_logs.info("==========VERIFY ASSIGNED RESERVATIONS NOT IN UNASSIGNED COLUMN(PARKING LOT)==========");
			test_steps.add("==========VERIFY ASSIGNED RESERVATIONS NOT IN UNASSIGNED COLUMN(PARKING LOT)==========");
			nav.navTapeChart(driver, test);
			test_steps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");
			tap.ClickUnassigned(driver);
			app_logs.info("Click Unassigned button");
			test_steps.add("Click Unassigned button");
			Thread.sleep(3000);
			AfterUnassignedReservations = tap.GetandVerifyUnassignedReservationNumber(driver, true);
			app_logs.info("Number of Unassigned Reservations : " + AfterUnassignedReservations);
			test_steps.add("Number of Unassigned Reservations : " + AfterUnassignedReservations);
			app_logs.info("Number of Unassigned Reservations : " + BeforeUnassignedReservations);
			test_steps.add("Number of Unassigned Reservations : " + BeforeUnassignedReservations);
//					app_logs.info(
//							"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
//					test_steps.add(
//							"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
//					Assert.assertTrue(
//							Integer.parseInt(AfterUnassignedReservations) == Integer.parseInt(BeforeUnassignedReservations),
//							"Failed: Unassigned Count not decrease");
//					app_logs.info("Verified Number of Unassigned Reservations has not been incremented");
//					test_steps.add("Verified Number of Unassigned Reservations has not been incremented");
			tap.verifyReservationInUnassignedList(driver, test_steps, ReservationName, false, true);
//					tap.VerifyUnassignedReservationColor(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
//							Utility.GetNextDate(1, "E, MMM dd"), RoomClassAbb,test_steps);
//					app_logs.info(
//							"Successfully verified Reservation '" + ReservationName + "' not Exist in Unassigned Reservations");
//					test_steps.add(
//							"Successfully verified Reservation '" + ReservationName + "'not Exist in Unassigned Reservations");
//					tap.VerifyUnassignedReservation(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
//							Utility.GetNextDate(1, "E, MMM dd"), RoomClassAbb);
			app_logs.info("==========VERIFY ASSIGNED RESERVATION DETAILS==========");
			test_steps.add("==========VERIFY ASSIGNED RESERVATION DETAILS==========");
			test_steps.add("Reservation Name : " + ReservationName);
			test_steps.add("Arrival Date : " + Utility.getCurrentDate("E, MMM dd"));
			test_steps.add("Departure Date : " + Utility.GetNextDate(1, "E, MMM dd"));
			test_steps.add("Room Class Abbreviation : " + RoomClassAbb);
			app_logs.info("==========VERIFY ASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");
			test_steps.add("==========VERIFY ASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");

			AfterUnassignedReservationsRC = tap.GetUnassignedRoomClassCount(driver, RoomClassAbb);
			app_logs.info("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ AfterUnassignedReservationsRC + "'");
			test_steps.add("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ AfterUnassignedReservationsRC + "'");
			Assert.assertTrue(
					Integer.parseInt(AfterUnassignedReservationsRC) == Integer.parseInt(BeforeUnassignedReservationsRC),
					"Failed: Unassigned Count not decrease");
			app_logs.info(
					"Verified Room Class '" + RoomClassAbb + "' Unassigned Reservations has not been incremented");
			test_steps
					.add("Verified Room Class '" + RoomClassAbb + "' Unassigned Reservations has not been incremented");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Reservation and verify Room Class", testName, "TapeChart",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Reservation and verify Room Class", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Search Reservation and UnAssign Room
		try {
			app_logs.info("==========SEARCH RESERVATION==========");
			test_steps.add("==========SEARCH RESERVATION==========");
			nav.navigateToReservations(driver);
			test_steps.add("Navigate Reservation ");
			app_logs.info("Navigate Reservation ");
			driver.navigate().refresh();
			Thread.sleep(1000);
			resSearch.Close_Unassigned_Tab(driver);
//					String xpathe = "//span[contains(text(),'Unass')]//..//i[@data-bind = 'click: $parent.closeTab, clickBubble: false, visible: !IsDefaultMenuOption']";
//					Wait.WaitForElement(driver, xpathe);
//					driver.findElement(By.xpath(xpathe)).click();
			Thread.sleep(500);

			resSearch.basicSearch_WithResNumber(driver, ReservationNumber);
			test_steps.add("Reservation Successfully Searched : " + ReservationNumber);
			app_logs.info("Reservation Successfully Searched : " + ReservationNumber);

			app_logs.info("==========UNASSIGN THE ROOM==========");
			test_steps.add("==========UNASSIGN THE ROOM==========");

			Thread.sleep(500);
			reservationPage.ClickEditStayInfo(driver, test_steps);
			Thread.sleep(500);
			String Room_No = reservationPage.unAssignRoomNumber(driver);
			app_logs.info("Room Is " + Room_No);
			test_steps.add("Room Is " + Room_No);
			Thread.sleep(500);
			reservationPage.clickSaveButton(driver);
			app_logs.info("Clicked On Save Button");
			test_steps.add("Clicked On Save Button");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Reservation and verify Room Class", testName, "TapeChart",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Reservation and verify Room Class", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Observe parking lots(Unassigned Reservations) in TapeChart
		try {
			app_logs.info("==========VERIFY UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			test_steps.add("==========VERIFY UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			nav.navTapeChart(driver, test);
			test_steps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");
			tap.ClickUnassigned(driver);
			app_logs.info("Click Unassigned button");
			test_steps.add("Click Unassigned button");
			Thread.sleep(3000);
			AfterUnassignedReservations = tap.GetandVerifyUnassignedReservationNumber(driver, true);
			app_logs.info("Number of Unassigned Reservations : " + AfterUnassignedReservations);
			test_steps.add("Number of Unassigned Reservations : " + AfterUnassignedReservations);
//					app_logs.info(
//							"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
//					test_steps.add(
//							"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
//					Assert.assertTrue(
//							Integer.parseInt(AfterUnassignedReservations) > Integer.parseInt(BeforeUnassignedReservations),
//							"Failed: Unassigned Count not increase");
//					app_logs.info("Verified Number of Unassigned Reservations has been incremented");
//					test_steps.add("Verified Number of Unassigned Reservations has been incremented");
			tap.verifyReservationInUnassignedList(driver, test_steps, ReservationName, true, true);
			tap.VerifyUnassignedReservationColor(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
					Utility.GetNextDate(1, "E, MMM dd"), RoomClassAbb, test_steps);
			app_logs.info(
					"Successfully verified Reservation '" + ReservationName + "' Exist in Unassigned Reservations");
			test_steps.add(
					"Successfully verified Reservation '" + ReservationName + "' Exist in Unassigned Reservations");
			tap.VerifyUnassignedReservation(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
					Utility.GetNextDate(1, "E, MMM dd"), RoomClassAbb);
			app_logs.info("==========VERIFY UNASSIGNED RESERVATION DETAILS==========");
			test_steps.add("==========VERIFY UNASSIGNED RESERVATION DETAILS==========");
			test_steps.add("Reservation Name : " + ReservationName);
			test_steps.add("Arrival Date : " + Utility.getCurrentDate("E, MMM dd"));
			test_steps.add("Departure Date : " + Utility.GetNextDate(1, "E, MMM dd"));
			test_steps.add("Room Class Abbreviation : " + RoomClassAbb);
			app_logs.info("==========VERIFY UNASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");
			test_steps.add("==========VERIFY UNASSIGNED RESERVATIONS OF ROOM CLASS '" + RoomClassAbb + "'==========");

			AfterUnassignedReservationsRC = tap.GetUnassignedRoomClassCount(driver, RoomClassAbb);
			app_logs.info("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ AfterUnassignedReservationsRC + "'");
			test_steps.add("Number of Unassigned Reservations of Room Class '" + RoomClassAbb + "' are '"
					+ AfterUnassignedReservationsRC + "'");
			Assert.assertTrue(
					Integer.parseInt(AfterUnassignedReservationsRC) > Integer.parseInt(BeforeUnassignedReservationsRC),
					"Failed: Unassigned Count not increase");
			app_logs.info("Verified Room Class '" + RoomClassAbb + "' Unassigned Reservations has been incremented");
			test_steps.add("Verified Room Class '" + RoomClassAbb + "' Unassigned Reservations has been incremented");
			
			statusCode = new ArrayList<String>();
			caseId = new ArrayList<String>();
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("1");
				comments = "pass";
			}


		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		test_steps.add("<b>Verified: the updates in the parking lot(Changed to Unassigned).<b>");
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("CreateUnassignedReservationTP", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}
}
