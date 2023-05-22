package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyAvailabilityInBulkUpdate extends TestCore {

	// Automation-1539
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> testName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	public static String testdescription = "";
	public static String testcatagory = "";

	@Test(dataProvider = "getData", groups = "RatesGrid")
	public void verifyAvailabilityInBulkUpdate(String startDate, String endDate, String isSundayCheck,
			String isMondayCheck, String isTuesdayChecked, String isWednesdayChecked, String isThursdayChecked,
			String isFridayChecked, String isSaturdayChecked, String isTotalOccupancyOn, String totalOccupancyType,
			String totalOccupancyValue, String roomClassName, String channel, String updateAvailability,
			String availability, String delim) throws Exception {

		String scriptName = "VerifyAvailabilityInBulkUpdate";
		String testdescription = "Rates V2 - Rates Grid - Bulk Update popup - Availability functionality<br>";

		testcatagory = "RateGrid";
		testName.add(scriptName);
		testDescription.add(testdescription);
		testCategory.add(testcatagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		//RoomClass roomClass = new RoomClass();
		String timeZone = "America/New_York";
		String getCurrentDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
		if (ESTTimeZone.CompareDates(startDate, "MM/dd/yyyy", timeZone)) {
			startDate  = getCurrentDate;
		}
		if (ESTTimeZone.CompareDates(endDate, "MM/dd/yyyy", timeZone)) {
			endDate  = getCurrentDate;
		}
		app_logs.info("startDate: "+startDate);
		app_logs.info("endDate: "+endDate);
		Utility.DELIM = delim;
		try {
			if (!Utility.insertTestName.containsKey(scriptName)) {
				Utility.insertTestName.put(scriptName, scriptName);
				Utility.reTry.put(scriptName, 0);

			} else {
				Utility.reTry.replace(scriptName, 1);
			}
			driver = getDriver();
			login_CP(driver);

			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "CP_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "CP_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		/*try {

			app_logs.info("==========STORE ALL ACTIVE ROOM CLASS==========");
			testSteps.add("==========STORE ALL ACTIVE ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.roomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

			List<String>[] arrayOfList = new List[3];
			arrayOfList = roomClass.getAllActiveRoomClasses(driver);
			getRoomClasses = (ArrayList<String>) arrayOfList[0];

			ArrayList<String> getRoomsNumber = new ArrayList<>();
			getRoomsNumber = (ArrayList<String>) arrayOfList[2];

			ArrayList<String> roomClassesAbbreviation = new ArrayList<>();
			roomClassesAbbreviation = (ArrayList<String>) arrayOfList[1];

			app_logs.info(getRoomClasses.size());
			testSteps.add("Get List of All RoomClasses: <b>" + getRoomClasses + "</b>");
			app_logs.info("roomClassesAbbreviation: " + roomClassesAbbreviation.size());
			app_logs.info("getRoomsNumber: " + getRoomsNumber.size());
			for (int i = 0; i < getRoomClasses.size(); i++) {
				System.out.println(
						getRoomClasses.get(i) + "  " + roomClassesAbbreviation.get(i) + "  " + getRoomsNumber.get(i));
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to store room class in arraylist", scriptName, "StoreRoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to store room class in arraylist", scriptName, "StoreRoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}*/

		try {

			//navigation.Inventory_Backward_1(driver);
			
			navigation.Inventory(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			getTestSteps.clear();
			getTestSteps = navigation.ratesGrid(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rate grid", scriptName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rate grid", scriptName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAvailabilityTab(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on availability tab", scriptName, "RateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on availability tab", scriptName, "RateGrid", driver);
			}
		}
		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectBulkUpdateOption(driver, availability);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to availability popup", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to availability popup", scriptName, "BulkUpdate", driver);
			}
		}
		try {

			testSteps.add("==========SELECT START DATE==========");
			app_logs.info("==========SELECT START DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.startDate(driver, startDate);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select start", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select start", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			testSteps.add("==========SELECT END DATE==========");
			app_logs.info("==========SELECT END DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.endDate(driver, endDate);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select end date", scriptName, "AvailabilityPopup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select end date", scriptName, "AvailabilityPopup", driver);
			}
		}

		try {
			app_logs.info("==========CHECKING/UNCHECKING DAYS==========");
			testSteps.add("==========CHECKING/UNCHECKING DAYS==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Sun", isSundayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Mon", isMondayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Tue", isTuesdayChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Wed", isWednesdayChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Thu", isThursdayChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Fri", isFridayChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Sat", isSaturdayChecked);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "BulkUpdate", driver);
			}
		}
		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickTotalOccupancy(driver, isTotalOccupancyOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			if (isTotalOccupancyOn.equalsIgnoreCase("Yes")) {

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectTotalOccupancyType(driver, totalOccupancyType);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			if (isTotalOccupancyOn.equalsIgnoreCase("Yes")) {

				getTestSteps.clear();
				getTestSteps = ratesGrid.enterOccupancyValue(driver, totalOccupancyValue);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");

			String[] roomClassesArray = roomClassName.split(",");
			for (String roomClasName : roomClassesArray) {
				roomClasName = roomClasName.trim();
				app_logs.info(roomClasName.trim());
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClassName);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING UPDATE AVAILABILITY==========");
			testSteps.add("==========SELECTING UPDATE AVAILABILITY==========");
			ArrayList<String> getUpdated = new ArrayList<>();
			String[] getUpdatedAvailability = updateAvailability.split(",");
			for (int i = 0; i < getUpdatedAvailability.length; i++) {
				String getBulkUpdate= getUpdatedAvailability[i].trim();
				getUpdated.add(getBulkUpdate);
			}
			int count = 0;
			String[] channelArr = channel.split(",");
			for (String channelString : channelArr) {
				
				channelString = channelString.trim();
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectUpdateAvailability(driver, channelString.trim(), getUpdated.get(count));
				testSteps.addAll(getTestSteps);
				count = count + 1;
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select update availability", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select update availability", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickUpdateButton(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click update button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click update button", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
			testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

			int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
			days = days + 1;
			String expectedDays = "Availability will be updated for " + days + " day(s) within this date range.";
			testSteps.add("Expected total days : " + expectedDays);
			app_logs.info("Expected total days : " + expectedDays);
			String totalDays = ratesGrid.getTotalDaysText(driver, availability);
			testSteps.add("Found : " + totalDays);
			app_logs.info("Found : " + totalDays);
			Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
			testSteps.add("Verified total number of days");
			app_logs.info("Verified total number of days");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total number of days", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total number of days", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnYesUpdateButton(driver);
			testSteps.addAll(getTestSteps);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click yes update button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click yes update button", scriptName, "BulkUpdate", driver);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAvailabilityInBulkUpdate", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver() {
		 driver.quit();
	}

}
