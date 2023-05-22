package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyBlackOutInNewBookingEngine extends TestCore {


	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	String date = null;
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();

	String reservationNumber = null;
	String comment;

	public static String test_description = "";
	public static String test_catagory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyBlackOutInNewBookingEngine(String delim, String testCaseDescription,
			String checkInDate, String checkOutDate,String RoomClass,String cases) throws Exception {


		HashMap<String, String> staticData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"VerifyCondProduct_StaticData");

		String bookingEngineChannel = staticData.get("ChannelName");
		
		staticData.get("ratePlanType");
		String adults = staticData.get("Adults");
		String child = staticData.get("Child");
		staticData.get("ReservationRoomClass");
		String lastName = staticData.get("ReservationLastName");
		
		

		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		app_logs.info("days: " + days);



		Utility.DELIM = "\\" + delim;
		String testName = testCaseDescription;
		test_description = testCaseDescription;
		test_catagory = "NBE_BlackOutValidations";
		scriptName.clear();
		scriptName.add(testName + "_" + testCaseDescription);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		BookingEngine bookingEngine = new BookingEngine();
		RatesGrid ratesGrid=new RatesGrid();
		Distribution distribution = new Distribution();
		Properties properties = new Properties();

		String randomNumber = Utility.GenerateRandomNumber();
		lastName = lastName + randomNumber;

		String propertyName = "";
		checkInDate=Utility.getCurrentDate("dd/MM/yyyy");
    	checkOutDate=Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
    	int stayDays = Integer.parseInt(Utility.differenceBetweenDates(checkInDate, checkOutDate));
    	String timeZone = "";
    	String dateFormat = Utility.dayMonthYear_DateFormat;
    	String getCurrentDate="";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 1);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			String[] casesList = cases.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("3");
			}
			driver = getDriver();
			login_Group(driver);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		testSteps.add("=================GET THE PROPERTY TIMEZONE ======================");
		app_logs.info("================= GET THE PROPERTY TIMEZONE ======================");
	try {
		navigation.Setup(driver);
		app_logs.info("Navigate Setup");
		testSteps.add("Navigate Setup");
		navigation.Properties(driver);
		app_logs.info("Navigate Properties");
		testSteps.add("Navigate Properties");
		propertyName = properties.getPropertyName(driver, 1);
		navigation.openProperty(driver, testSteps, propertyName);
		navigation.clickPropertyOptions(driver, testSteps);
		timeZone = navigation.get_Property_TimeZone(driver);
		testSteps.add("Property TimeZone: " + timeZone);
		navigation.Reservation_Backward(driver);
		getCurrentDate = Utility.getNextDate(0, dateFormat, timeZone);
		if (ESTTimeZone.CompareDates(checkInDate, dateFormat, timeZone)) {
			checkInDate = getCurrentDate;
			checkOutDate = Utility.getNextDate(stayDays, dateFormat, timeZone);
		} 
		app_logs.info("Check-in Date : " + checkInDate);
		app_logs.info("Check-out Date : " + checkOutDate);
		
	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
		} else {
			Assert.assertTrue(false);
		}

	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
		try {
			app_logs.info("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ==");
			testSteps.add("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ======");
			navigation.Inventory(driver);
			//navigation.Inventory_BackWard_Admin(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			navigation.Distribution(driver);
			app_logs.info("Navigate Distribution");
			testSteps.add("Navigate Distribution");

			app_logs.info("Get Booking Engine Channel Complete name");
			testSteps.add("Get Booking Engine Channel Complete name");
			ArrayList<String> channelsList = distribution.getAllChannelsContainingName(driver, bookingEngineChannel);
			if (channelsList.size() == 1) {
				bookingEngineChannel = channelsList.get(0);
			} else {
				for (int i = 0; i < channelsList.size(); i++) {
					if (channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())) {
						bookingEngineChannel = channelsList.get(i);
					}
				}
			}
			app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
			testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
			boolean distribute = false;
			if(bookingEngineChannel.contains("'")) {
				distribute = distribution.getDistributeValueOfChannel(driver, bookingEngineChannel.split("'")[0]);
			}else {
				distribute = distribution.getChannelDistributeValue(driver, bookingEngineChannel);
			}
			if (distribute) {
				app_logs.info("'" + bookingEngineChannel + "' Distribute is selected by default");
				testSteps.add("'" + bookingEngineChannel + "' Distribute is selected by default");
			} else {

				app_logs.info("'" + bookingEngineChannel + "' Distribute is not selected by default");
				testSteps.add("'" + bookingEngineChannel + "' Distribute is not selected by default");
			}
			Assert.assertTrue(distribute,
					"Failed '" + bookingEngineChannel + "' Distribute is not selected by default");
			

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Precondition '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ",
						testName, "Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Preconditions '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ",
						testName, "Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {
			testSteps.add("=================== BlackOut for the given dates ======================");
			app_logs.info("=================== BlackOut for the given dates ======================");
			
			navigation.Inventory(driver);
			navigation.Rates_Grid(driver);
	        ratesGrid.clickOnAvailability(driver);
	        ratesGrid.clickForRateGridCalender(driver,testSteps);
	        Utility.selectDateFromDatePicker(driver, checkInDate, "dd/MM/yyyy");
	        ratesGrid.clickExpendRooClass(driver, testSteps, RoomClass);
	        ratesGrid.blockoutRoomClassOrAvilable(driver, testSteps, RoomClass, stayDays, bookingEngineChannel,"Blockout");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {

			testSteps.add("=================== OPEN NEW BOOKING ENGINE ======================");
			app_logs.info("=================== OPEN NEW BOOKING ENGINE ======================");

			navigation.rateV2Setup(driver,testSteps);
			testSteps.add("Navigate Setup");
			app_logs.info("Navigate Setup");
			navigation.Properties(driver);
			testSteps.add("Navigate Properties");
			app_logs.info("Navigate Properties");
			try {
				navigation.openProperty(driver, testSteps, propertyName);
			} catch (Exception e) {
				navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
			}

			testSteps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			navigation.clickPropertyOptions(driver, testSteps);
			bookingEngine.clickOnBookingEngineConfigLink(driver);
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");
			testSteps.add("===== VERIFY RATE PLAN IS SELECTED =====");
			app_logs.info("===== VERIFY RATE PLAN IS SELECTED =====");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			getTestSteps.clear();

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== VERIFY ROOMCLASS AVAILABILITY AFTER BLACKOUT IN BOOKING ENGINE ======================");
			app_logs.info("=================== VERIFY ROOMCLASS AVAILABILITY AFTER BLACKOUT IN BOOKING ENGINE ======================");
			
			bookingEngine.clickWelcomePageLink(driver);
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");
			testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
			app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

			getTestSteps.clear();
			getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate, false);
			testSteps.addAll(getTestSteps);
			testSteps.add("Select Check-In Date : " + checkInDate);
			app_logs.info("Select Check-In Date : " + checkInDate);

			testSteps.add("Select Check-Out Date : " + checkOutDate);
			app_logs.info("Select Check-Out Date : " + checkOutDate);
			getTestSteps.clear();
			getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.enterChildrenBE(driver, child);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = bookingEngine.clickSearchOfRooms(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyRoomClassNotExists(driver, RoomClass);
			testSteps.addAll(getTestSteps);
			
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(0));
			navigation.Inventory_BackWard_Admin(driver);
			navigation.RatesGrid(driver);
			ratesGrid.clickOnAvailability(driver);
	        ratesGrid.clickForRateGridCalender(driver,testSteps);
	        Utility.selectDateFromDatePicker(driver, checkInDate, "dd/MM/yyyy");
	        ratesGrid.clickExpendRooClass(driver, testSteps, RoomClass);
	        ratesGrid.blockoutRoomClassOrAvilable(driver, testSteps, RoomClass, stayDays, bookingEngineChannel,"Available");
				
			
		}  catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
			
		// Generate Report
		try {
			String[] testcase = cases.split(",");
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
			//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comment, TestCore.TestRail_AssignToID);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyBlackOutInNbe", BEExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comment,TestCore.TestRail_AssignToID);

	}
}
