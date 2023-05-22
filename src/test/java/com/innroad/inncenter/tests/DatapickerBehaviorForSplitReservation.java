package com.innroad.inncenter.tests;



import java.io.IOException;
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
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class DatapickerBehaviorForSplitReservation  extends TestCore {


	// Automation-1468
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> testName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	public static String testdescription = "";
	public static String testcatagory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "TapeChart")
	public void datapickerBehaviorForSplitReservation(String rateName, String baseAmount, String addtionalAdult,
			String additionalChild, String displayName, String associateSeason, String rateRPolicy,
			String rateDescription, String roomClassAbb, String roomClassName, String bedsCount, String maxAdults,
			String maxPersons, String roomQuantity, String startRoomNumber, String ratePlan, String rateType,
			String rateAttributes, String interval, String source, String adults, String child, String marketSegment,
			String referral, String firstName, String lastName, String salutation, String roomCharge)
			throws InterruptedException, IOException {

		String scriptName = "ExtendReduceSplitRoomReservationInTapeChart";
		testdescription = "Extend Or Reduce Split room Reservation in Tape Chart<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682408' target='_blank'>"
				+ "Click here to open TestRail: C682408</a>";

		testcatagory = "TapeChart";
		testName.add(scriptName);
		testDescription.add(testdescription);
		testCategory.add(testcatagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + scriptName + " TEST.");
		app_logs.info("##################################################################################");

		Rate rate = new Rate();
		Navigation navigation = new Navigation();
		Tapechart tapeChart = new Tapechart();
		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservation = new CPReservationPage();
		RoomClass roomClass = new RoomClass();
		String randomNumber = Utility.GenerateRandomNumber();
		Folio folio = new Folio();
		String reservationNumber = null;
		lastName = lastName + randomNumber;
		String tripTotal = null;
		String balance = null;
		String taxes = null;
		String roomA = null;
		String roomB = null;
		String movedRoomA = null;
		String movedRoomB = null;
		String amountwithTax = null;
		String amount = null;
		String twoNightsAmountwithTax = null;
		String twoNightsAmount = null;
		String threeNightsAmountwithTax = null;
		String threeNightsAmount = null;
		String tempraryRoomClassname = roomClassName;

		try {
			if (!Utility.insertTestName.containsKey(scriptName)) {
				Utility.insertTestName.put(scriptName, scriptName);
				Utility.reTry.put(scriptName, 0);

			} else {
				Utility.reTry.replace(scriptName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			testSteps.add("Entered appication URL : " + TestCore.envURL);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testdescription, testcatagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testdescription, testcatagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		

	
		// Create split reservation
		try {

			app_logs.info("==========CREATE SPLIT ROOM RESERVATION==========");
			testSteps.add("==========CREATE SPLIT ROOM RESERVATION==========");

			

			getTestSteps.clear();
			getTestSteps = reservation.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.clickAddARoom(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========ENTER STAY INFO AND SEARCH ROOMS==========");
			testSteps.add("==========ENTER STAY INFO AND SEARCH ROOMS==========");
			String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
			String checkoutDate = Utility.GetNextDate(1, "MM/dd/yyyy");
			reservation.enterDate(driver, "start", checkInDate, "1");
			testSteps.add("Select First Room CheckIn Date : " + checkInDate);
			app_logs.info("Select First Room CheckIn Date : " + checkInDate);
			reservation.enterDate(driver, "end", checkoutDate, "1");
			testSteps.add("Select First Room CheckOut Date : " + checkoutDate);
			app_logs.info("Select First Room CheckOut Date : " + checkoutDate);

			//checkInDate =checkInDate;
			checkoutDate = Utility.GetNextDate(2, "MM/dd/yyyy");
			reservation.enterDate(driver, "start", checkInDate, "2");
			testSteps.add("Select Second Room CheckIn Date : " + checkInDate);
			app_logs.info("Select Second Room CheckIn Date : " + checkInDate);
			reservation.enterDate(driver, "end", checkoutDate, "2");
			testSteps.add("Select Second Room CheckOut Date : " + checkoutDate);
			app_logs.info("Select Second Room CheckOut Date : " + checkoutDate);

			getTestSteps.clear();
			getTestSteps = reservation.splitRoomCheckbox(driver, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.enter_Children(driver, getTestSteps, child);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.select_Rateplan(driver, getTestSteps, ratePlan, "");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			/*

			app_logs.info("room class: " + roomClassName);
			app_logs.info("==========SELECT SPLIT ROOM : A==========");
			testSteps.add("==========SELECT SPLIT ROOM : A==========");

			getTestSteps.clear();
			getTestSteps = reservation.selectSplitRoom(driver, roomClassName, 2, 1);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========SELECT SPLIT ROOM : B==========");
			testSteps.add("==========SELECT SPLIT ROOM : B==========");
			getTestSteps.clear();
			getTestSteps = reservation.selectSplitRoom(driver, roomClassName, 3, 2);
			testSteps.addAll(getTestSteps);

			reservation.moveToAddARoom(driver);
			getTestSteps = reservation.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			Wait.wait5Second();

			app_logs.info("==========ENTER GUEST INFO==========");
			testSteps.add("==========ENTER GUEST INFO==========");
			reservation.enterMailingAddressOnUncheckedCreateGuestProfile(driver, getTestSteps, salutation, firstName,
					lastName, "no");

			app_logs.info("==========ENTER MARKETING INFO==========");
			testSteps.add("==========ENTER MARKETING INFO==========");
			getTestSteps.clear();
			getTestSteps = reservation.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========SAVE RESERVATION==========");
			testSteps.add("==========SAVE RESERVATION==========");
			reservation.clickBookNow(driver, testSteps);

			String confirmationNumber = reservation.getReservationConfirmationNumber(driver, testSteps);
			testSteps.add("Confirmation Number : <b>" + confirmationNumber + "</b>");
			reservationNumber = confirmationNumber.trim();
			String ReservationStatus = reservation.getReservationStatus(driver);
			Assert.assertEquals(ReservationStatus, "Reserved", "Failed: Reservation Status missmatched");
			reservation.clickCloseReservationSavePopup(driver, testSteps);

			*/
		} catch (Exception e) {

			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create split reservation", scriptName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create split reservation", scriptName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {
			String splitDatapickerMsg="//div[contains(text(),'Please ensure that the splits for the reservation are contiguous and do not overlap')]";
			String expectedMsg="Please ensure that the splits for the reservation are contiguous and do not overlap";
			
			Wait.WaitForElement(driver, splitDatapickerMsg);
			splitDatapickerMsg=driver.findElement(By.xpath(splitDatapickerMsg)).getText();
			Assert.assertEquals(splitDatapickerMsg,expectedMsg, "FAILED: Check in date of splited reservation second room  is not"
					+ " same as checkout date of first room");

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);

			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("SplitRoomReservationDataPicker", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}


}
