package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyBlackOutColourValidationInTapeChart extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	public static ArrayList<String> test_name = new ArrayList<>();
	public static ArrayList<String> test_description = new ArrayList<>();
	public static ArrayList<String> test_catagory = new ArrayList<>();

	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Regression")
	public void verifyBlackoutRoom(String RoomClassAbbreviation, String RoomClassName, String RateName)
			throws InterruptedException, IOException {

		String testName = "VerifyBlackoutRoom";
		String testDescription = "BlackOut the room<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554254' target='_blank'>"
				+ "Click here to open TestRail: C554254</a>";
		String testCatagory = "BlackOut Room";

		test_name.add(testName);
		test_description.add(testDescription);
		test_catagory.add(testCatagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		// distribution = new Distribution();
		Tapechart tapechart = new Tapechart();
		RoomClass roomClass = new RoomClass();
		Rate rate = new Rate();
		// Season sc = new Season();
		// RateQuote rateQuote = new RateQuote();
		// String TripSummaryRoomCharges=null;
		// ArrayList<String> Rooms = new ArrayList<String>();
		new ArrayList<String>();
		// ArrayList<String> roomCost = new ArrayList<String>();
		String random = Utility.fourDigitgenerateRandomString();
		RoomClassName = RoomClassName + random;
		RoomClassAbbreviation = RoomClassAbbreviation + random;
		String bedsCount = "2";
		String maxAdults = "4";
		String maxPersopns = "8";
		String roomQuantity = "3";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				System.out.println(Utility.reTry.get(testName));

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_NONGS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			navigation.Inventory(driver);
			test_steps.add("Click on inventory tab");
			navigation.Rate(driver);
			RateName = "Blackoutrate" + random;
			// rate.VerifyDeleteRate(driver, "BlockOutRoom");
			rate.deleteRates(driver, "Blackoutrate");

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

		try {
			test_steps.add("******************* Creating room class **********************");
			app_logs.info("******************* Creating room class **********************");
			//navigation.Setup(driver);
			driver.findElement(By.xpath("(//*[text()='Setup'])[2]")).click();
			navigation.RoomClass(driver);
			roomClass.SearchRoomClass(driver, "BlockOutRoom", test_steps);
			roomClass.deleteRoomClass(driver, "BlockOutRoom");
			roomClass.selectNewRoomClass(driver);
			roomClass.roomClassRooms(driver, RoomClassName, RoomClassAbbreviation, bedsCount, maxAdults, maxPersopns,
					roomQuantity, test, test_steps);
			test_steps.add("Successfully created new  roomclass : " + RoomClassName);
			app_logs.info("Successfully created new roomclass : " + RoomClassName);
			//navigation.Reservation_Backward_1(driver);
			driver.findElement(By.xpath("(//span[text()='Reservations'])[3]")).click();
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create room class", "room class", "Failed to create room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create room class", "room class", "Failed to create room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Inventory
		try {

			navigation.Inventory(driver);
			test_steps.add("Click on inventory tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click Inventory", testName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click Inventory", testName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// String maxAdults="2";
		String maxPersons = "5";
		String baseAmount = "125";
		String additionalAdult = "2";
		String additionalChild = "3";
		try {
			test_steps.add("******************* Creating the Rate**********************");
			app_logs.info("******************* Creating the Rate**********************");
			//navigation.Inventory(driver);
			//test_steps.add("Click on inventory tab");
			navigation.Rate(driver);
			RateName = "Blackoutrate" + random;
			// rate.VerifyDeleteRate(driver, "BlockOutRoom");
			rate.DeleteRate(driver, "Blackoutrate");
			rate.createRate(driver, RateName, maxAdults, maxPersons, baseAmount, additionalAdult, additionalChild,
					"BlackoutrateName", "BlackoutratePolicy", "BlackoutrateDescription", RoomClassName, test_steps);
			//navigation.Reservation_Backward_1(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to rate", "rate", "Failed to rate", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create rate", "rate", "Failed to create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// BulkUpdate operations
		test_steps.add("*******************Rooms Bloked  for SingleDay**********************");
		app_logs.info("******************* Rooms Bloked  for SingleDay**********************");
		try {
			//navigation.Inventory(driver);
			driver.findElement(By.xpath("(//*[text()='Inventory'])[2]")).click();
			getTest_Steps.clear();
			rate.rateGrid(driver);
			rate.clickOnRateGridAvilability(driver, test_steps);
			rate.searchAndExpandRoomClassInGrid(driver, RoomClassName, test_steps);
			rate.selectInncenterTab(driver, test_steps);
			rate.clickOnBulkUpdateTab(driver, test_steps);
			rate.selectBulkAvilability(driver, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find blackout room ", testName, "BlackOut", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find blackout room ", testName, "BlackOut", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("*******************Rooms Bloked  for MultipleDays**********************");
			app_logs.info("******************* Rooms Bloked  for MultipleDays**********************");

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date currentDate = new Date();
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate date
			c.add(Calendar.DATE, 0); // same with c.add(Calendar.DAY_OF_MONTH, 1);
			// convert calendar to date
			Date currentDatePlusOne = c.getTime();
			String startDate = dateFormat.format(currentDatePlusOne);
			test_steps.add("Season Start Date : " + startDate);
			app_logs.info("Season Start Date : " + startDate);
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			currentDate = new Date();
			// convert date to calendar
			c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate date
			c.add(Calendar.DATE, 3); // same with c.add(Calendar.DAY_OF_MONTH, 1);
			// convert calendar to date
			currentDatePlusOne = c.getTime();
			String endDate = dateFormat.format(currentDatePlusOne);
			test_steps.add("Season End Date : " + endDate);
			app_logs.info("Season End Date : " + endDate);
			rate.bulkUpdate(driver, test_steps, startDate, endDate);
			rate.verifyBulkUpdateAvilabilityHeadTex(driver, test_steps);
			rate.selectRoomClassInBulkUpadte(driver, RoomClassName, test_steps);
			rate.clickOnBlackOutButton(driver, test_steps);
			rate.clickOnBulkUpdateButton(driver, test_steps);
			rate.clickOnYesUpdateButton(driver, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to season", "season", "Failed to season", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create season", "season", "Failed to create season", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		test_steps.add("*************************Very  Black out Room In TapeCaht   ****************************");
		app_logs.info("*************************Select  Black out Room In TapeCaht   ****************************");
		try {
			//navigation.navigateToReservations(driver);
			driver.findElement(By.xpath("//*[text()='Reservations']")).click();
			navigation.TapeChart(driver);
			tapechart.verifyBlackOutRoomsInTapeChat(driver, RoomClassName, getTest_Steps);
			// tapechart.Verify_BlackOutRoom(driver, BlackoutRoom);
			test_steps.add("Verify room class status in TapeChart");
			app_logs.info("Verify room class status in TapeChart");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find blackout room in Tapchart", testName, "BlackOut", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find blackout room in Tapchart", testName, "BlackOut", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	
		test_steps.add("************************* Remove Blackout Room****************************");
		app_logs.info("************************* Remove Blackout Room ****************************");

		try {
			//navigation.Inventory(driver);
			driver.findElement(By.xpath("(//*[text()='Inventory'])[2]")).click();
			getTest_Steps.clear();
			rate.rateGrid(driver);
			rate.clickOnRateGridAvilability(driver, test_steps);
			rate.searchAndExpandRoomClassInGrid(driver, RoomClassName, test_steps);
			rate.selectInncenterTab(driver, test_steps);
			// rate.clickOnUpdateButton(driver, test_steps);
			rate.clickOnBulkUpdateTab(driver, test_steps);
			rate.selectBulkAvilability(driver, test_steps);
			// rate.verifyBulkUpdateAvilability(driver, test_steps);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date currentDate = new Date();
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate date
			c.add(Calendar.DATE, 0); // same with c.add(Calendar.DAY_OF_MONTH, 1);
			// convert calendar to date
			Date currentDatePlusOne = c.getTime();
			String startDate = dateFormat.format(currentDatePlusOne);
			test_steps.add(" Remove BlackOut Start Date : " + startDate);
			app_logs.info("Remove BlackOut Start Date: " + startDate);
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			currentDate = new Date();
			// convert date to calendar
			c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate date
			c.add(Calendar.DATE, 3); // same with c.add(Calendar.DAY_OF_MONTH, 1);
			// convert calendar to date
			currentDatePlusOne = c.getTime();
			String endDate = dateFormat.format(currentDatePlusOne);
			test_steps.add("Season End Date : " + endDate);
			app_logs.info("Season End Date : " + endDate);
			rate.bulkUpdate(driver, test_steps, startDate, endDate);
			rate.verifyBulkUpdateAvilabilityHeadTex(driver, test_steps);
			rate.selectRoomClassInBulkUpadte(driver, RoomClassName, test_steps);
			rate.clickOnAvilableTab(driver, test_steps);
			rate.clickOnBulkUpdateButton(driver, test_steps);
			rate.clickOnYesUpdateButton(driver, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to season", "season", "Failed to season", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create season", "season", "Failed to create season", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String packageName = "Blackoutrate";

		try {
			test_steps.add("***********************Delete all Rates created during test run *************************");
			app_logs.info("*********************** Delete all Rates created during test run *************************");
			navigation.cpReservation_Backward(driver);
			navigation.Inventory(driver);
			navigation.Rate(driver);
			// rate.deleteAllRates(driver, getTest_Steps,"BlockOutRoom");
			rate.deleteRates(driver, "Blackoutrate");
			// rate.VerifyDeleteRate(driver, "BlockOutRoom");
			app_logs.info("Successfully deleted Rate : " + RateName);
			test_steps.add("Successfully deleted Rate : " + RateName);

			app_logs.info("==========DELETE ALL RATES THAT START WITH NAME OF " + packageName + "==========");
			test_steps.add("==========DELETE ALL RATES THAT START WITH NAME OF " + packageName + "==========");

			test_steps.add(
					"***********************Delete all room classes created during test run *************************");
			app_logs.info(
					"***********************Delete all room classes created during test run *************************");
			// nav.cpReservation_Backward(driver);
			//navigation.Reservation_Backward_1(driver);
			driver.findElement(By.xpath("(//*[text()='Reservations'])[2]")).click();
			navigation.Setup(driver);
			navigation.RoomClass(driver);
			roomClass.SearchRoomClass(driver, "BlockOutRoom", test_steps);
			roomClass.deleteRoomClass(driver, "BlockOutRoom");
			test_steps.add("Verify the Deleted Rates that started with name of : " + packageName);
			app_logs.info("Verify the Deleted Rates that started with name of : " + packageName);

			app_logs.info("Successfully deleted setup");
			test_steps.add("Successfully deleted setup");

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Delete Rate,Seanson,Room Class", testName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Delete Rate,Seanson,Room Class", testName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided

		return Utility.getData("verifyBlackOutColorInTapeChart", envLoginExcel);
	}

	// @AfterClass
	public void closedriver() {
		driver.quit();
	}

}
