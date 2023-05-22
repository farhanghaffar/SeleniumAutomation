package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyUIValidationForBulkUpdateAvailability extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> testName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	ArrayList<String> ratePlanColor = new ArrayList<>();
	ArrayList<String> activeChannelsList = new ArrayList<String>();

	public static String testdescription = "";
	public static String testcatagory = "";

	@Test(dataProvider = "getData", groups = "RatesGrid")
	public void verifyUIValidationForBulkUpdateAvailability(String startDate, String endDate, String sunday,
			String monday, String tuesday, String wednesday, String thursday, String friday, String saturday,
			String isTotalOccupancyOn, String totalOccupancyType, String totalOccupancyValue, String roomClassName,
			String channel, String ratePlan, String isMinimumStayOn, String minimumStayValue, String isCheckInOn,
			String noCheckIn, String isCheckOutOn, String noCheckOut, String availability, String blackOut,
			String available, String allRoomClasses, String delim) throws Exception {

		String scriptName = "VerifyUIValidationForBulkUpdateAvailability";
		String testdescription = "Rates V2 - Rates Grid - Bulk Update popup - Rules functionality<br>";

		testcatagory = "RateGrid";
		testName.add(scriptName);
		testDescription.add(testdescription);
		testCategory.add(testcatagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		RoomClass roomClass = new RoomClass();
		Distribution distribution = new Distribution();
		String timeZone = "America/New_York";
		String getCurrentDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
		
		app_logs.info("getCurrentDate " + getCurrentDate);
		String closeToggle = "No";
		// point manage date from client info

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

		try {

			app_logs.info("==========STORE ALL ACTIVE ROOM CLASS==========");
			testSteps.add("==========STORE ALL ACTIVE ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.roomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

			List<String>[] arrayOfList = new List[5];
			arrayOfList = roomClass.getAllActiveRoomClasses(driver);
			getRoomClasses = (ArrayList<String>) arrayOfList[0];

			ArrayList<String> getRoomsNumber = new ArrayList<>();
			getRoomsNumber = (ArrayList<String>) arrayOfList[2];

			ArrayList<String> roomClassesAbbreviation = new ArrayList<>();
			roomClassesAbbreviation = (ArrayList<String>) arrayOfList[1];

			ArrayList<String> maxAdults = new ArrayList<>();
			maxAdults = (ArrayList<String>) arrayOfList[3];

			ArrayList<String> maxPersons = new ArrayList<>();
			maxPersons = (ArrayList<String>) arrayOfList[4];

			app_logs.info(getRoomClasses.size());
			testSteps.add("Get List of All RoomClasses: <b>" + getRoomClasses + "</b>");
			testSteps.add("Get List of All MaxAdults: <b>" + maxAdults + "</b>");
			testSteps.add("Get List of All MaxPersons: <b>" + maxPersons + "</b>");
			
			app_logs.info("roomClassesAbbreviation: " + roomClassesAbbreviation.size());
			app_logs.info("getRoomsNumber: " + getRoomsNumber.size());
			for (int i = 0; i < getRoomClasses.size(); i++) {
				System.out.println(
						getRoomClasses.get(i) + "  " + roomClassesAbbreviation.get(i) + "  " + getRoomsNumber.get(i) + " " + maxAdults.get(i) + " "  +  maxPersons.get(i));
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
		}

		// Get All RoomClasses/Distribution
		try {

			testSteps.add("==========GET ALL ACTIVE CHANNEL=========");
			app_logs.info("==========GET ALL ACTIVE CHANNEL==========");

			navigation.Inventory_Backward_1(driver);
			testSteps.add("Navigated to Inventory");

			navigation.Distribution(driver);
			testSteps.add("Navigate to distribution");

			activeChannelsList = distribution.getAllActiveChannelDetails(driver);
			app_logs.info(activeChannelsList);
			app_logs.info(" size is:" + activeChannelsList.size());
			testSteps.add("Get List of All Sources: <b>" + activeChannelsList + "</b>");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get source", scriptName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get source", scriptName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Get Rate Plan Name and Color
		try {
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
			Utility.updateReport(e, "Failed to navigate to rate grid", scriptName, "RatePlanName", driver);
			} else {
				Assert.assertTrue(false);
			}
		
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to navigate to rate grid", scriptName, "RatePlanName", driver);
			} else {
				Assert.assertTrue(false);
			}
		

		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAvailabilityTab(driver);
			testSteps.addAll(getTestSteps);

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
				Utility.updateReport(e, "Failed to naviagte to rules popup", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to naviagte to rules popup", scriptName, "BulkUpdate", driver);
			}
		}
		try {

			app_logs.info("==========VERIFYING BULK UPDATE AVAILABILITY POPUP HEADING==========");
			testSteps.add("==========VERIFYING BULK UPDATE AVAILABILITY POPUP HEADING==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyAvailabilityHeading(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify bulk update popup heading", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify bulk update popup heading", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING CLOSE ICON==========");
			testSteps.add("==========VERIFYING CLOSE ICON==========");

			testSteps.add("Expected : " + "X");

			String closeText = ratesGrid.getCloseIconText(driver);
			app_logs.info("Found : " + "\\" + closeText + "\\");
			testSteps.add("Found : " + "\\" + closeText + "\\");

			Assert.assertEquals("\\" + closeText + "\\", "\\×\\", "Failed to match close text");
			testSteps.add("Verified close sign");
			app_logs.info("Verified close sign");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify bulk update close icon", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify bulk update close icon", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING BULK UPDATE AVAILABILITY POPUP TEXT==========");
			testSteps.add("==========VERIFYING BULK UPDATE AVAILABILITY POPUP TEXT==========");

			String exectedPopupText = "This one-time update will adjust availability for all days that match your criteria.\n"
					+ "This update will not be recurring.";
			testSteps.add("Expected Bulk Update Popup Text : " + exectedPopupText);
			app_logs.info("Expected Bulk Update Popup Text : " + exectedPopupText);
			String updatePopupText = ratesGrid.getBulkUpdatePoppupText(driver);
			testSteps.add("Found : " + updatePopupText);
			app_logs.info("Found : " + updatePopupText);
			Assert.assertEquals(updatePopupText, exectedPopupText, "Failed to verify text");
			testSteps.add("Verified bulk update popup text");
			app_logs.info("Verified bulk update popup text");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify bulk update popup text", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify bulk update popup text", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING START DATE==========");
			testSteps.add("==========VERIFYING START DATE==========");

			app_logs.info("Expected Date : " + getCurrentDate);
			testSteps.add("Expected Date : " + getCurrentDate);

			String getStartDate = ratesGrid.getStartDate(driver);
			app_logs.info("Found : " + getStartDate);
			testSteps.add("Found : " + getStartDate);

			Assert.assertEquals(getStartDate, getCurrentDate, "Failed to match start date");
			testSteps.add("Verified start date");
			app_logs.info("Verified start date");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify start date", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify start date", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING START DATE CALENDAR==========");
			testSteps.add("==========VERIFYING START DATE CALENDAR==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyStartDateCalendar(driver, getCurrentDate);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified start date calendar");
			app_logs.info("Verified start date calendar");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify start date popup ", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify start date popup", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING END DATE==========");
			testSteps.add("==========VERIFYING END DATE==========");

			app_logs.info("Expected Date : " + getCurrentDate);
			testSteps.add("Expected Date : " + getCurrentDate);

			String getEndDate = ratesGrid.getEndDate(driver);
			app_logs.info("Found : " + getEndDate);
			testSteps.add("Found : " + getEndDate);

			Assert.assertEquals(getEndDate, getCurrentDate, "Failed to match end date");
			testSteps.add("Verified end date");
			app_logs.info("Verified end date");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify end date", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify end date", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING END DATE CALENDAR==========");
			testSteps.add("==========VERIFYING END DATE CALENDAR==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyEndDateCalendar(driver, getCurrentDate);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified end date calendar");
			app_logs.info("Verified end date calendar");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify end date popup ", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify end date popup", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING DAYS CHEKCBOXES DISPLAYED==========");
			testSteps.add("==========VERIFYING DAYS CHEKCBOXES DISPLAYED==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDaysCheckbox(driver, "Sun");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDaysCheckbox(driver, "Mon");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDaysCheckbox(driver, "Tue");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDaysCheckbox(driver, "Wed");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDaysCheckbox(driver, "Thu");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDaysCheckbox(driver, "Fri");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyDaysCheckbox(driver, "Sat");
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified days checkboxes");
			app_logs.info("Verified days checkboxes");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify days checkbox", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify days checkbox", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY TEXT==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY TEXT==========");

			String expectedOccupanyText = "For days that total occupancy is";
			testSteps.add("Expected : " + expectedOccupanyText);
			app_logs.info("Expected : " + expectedOccupanyText);

			String totalOccupancyText = ratesGrid.totalOccupancyTextDisplay(driver);
			testSteps.add("Found : " + totalOccupancyText);
			app_logs.info("Found : " + totalOccupancyText);

			Assert.assertEquals(totalOccupancyText, expectedOccupanyText, "Failed to match total occupancy text");
			testSteps.add("Verified total occupancy text");
			app_logs.info("Verified total occupancy text");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy text", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy text", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING OCCUPANCY CLAUSE TEXT BY HOVERING OVER CLAUSE ICON==========");
			testSteps.add("==========VERIFYING OCCUPANCY CLAUSE TEXT BY HOVERING OVER CLAUSE ICON==========");

			String expectedClause = "This clause only makes updates according to the total occupancy setting as of the time of the update. It will NOT make updates in the future when total occupancy reaches what is set.";
			testSteps.add("Expected : " + expectedClause);
			app_logs.info("Expected : " + expectedClause);

			String getClause = ratesGrid.verifyTotalOccupanyIcon(driver);
			testSteps.add("Found : " + getClause);
			app_logs.info("Found : " + getClause);

			Assert.assertEquals(getClause, expectedClause, "Failed to match occupancy clause text");
			testSteps.add("Verified occupancy clause text by hovering over cluase icon");
			app_logs.info("Verified occupancy clause text by hovering over cluase icon");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify occupancy clause text", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify occupancy clause text", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY DISABLED BY DAFAULT==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY DISABLED BY DAFAULT==========");

			Boolean isOccupancyTypeEnable = ratesGrid.verifyTotalOccupancyType(driver);
			app_logs.info("isOccupancyTypeEnable : " + isOccupancyTypeEnable);

			Boolean isOccupancyValueEnable = ratesGrid.verifyTotalOccupanyValue(driver);
			app_logs.info("isOccupancyValueEnable : " + isOccupancyValueEnable);

			if (!isOccupancyValueEnable && !isOccupancyTypeEnable) {
				testSteps.add("Verified total occupancy disabled by default");
				app_logs.info("Verified total occupancy disabled by default");
			} else {
				Assert.assertTrue(false, "Failed : Found total occupancy enabled by default");

			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy is disable bydefault", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy  is disable bydefault", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY TYPE DISABLED BY DAFAULT==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY TYPE DISABLED BY DAFAULT==========");

			Boolean isOccupancyTypeEnable = ratesGrid.verifyTotalOccupancyType(driver);
			app_logs.info("isOccupancyTypeEnable : " + isOccupancyTypeEnable);

			if (!isOccupancyTypeEnable) {
				testSteps.add("Verified total occupancy type disabled by default");
				app_logs.info("Verified total occupancy type disabled by default");
			} else {
				Assert.assertTrue(false, "Failed : Found total occupancy type enabled by default");
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy type is disable bydefault", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy type is disable bydefault", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY VALUE DISABLED BY DAFAULT==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY VALUE DISABLED BY DAFAULT==========");

			Boolean isOccupancyValueEnable = ratesGrid.verifyTotalOccupanyValue(driver);
			app_logs.info("isOccupancyValueEnable : " + isOccupancyValueEnable);

			if (!isOccupancyValueEnable) {
				testSteps.add("Verified total occupancy value disabled by default");
				app_logs.info("Verified total occupancy value disabled by default");
			} else {
				Assert.assertTrue(false, "Failed : Found total occupancy value enabled by default");
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy value is disable bydefault", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy value is disable bydefault", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING TOTAL OCCUPANCY ENABLED AFTRE CLICKING OCCUPANCY BUTTON==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY ENABLED AFTRE CLICKING OCCUPANCY BUTTON==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickTotalOccupancy(driver, isTotalOccupancyOn);
			testSteps.addAll(getTestSteps);

			Boolean isOccupancyTypeEnable = ratesGrid.verifyTotalOccupancyType(driver);
			app_logs.info("isOccupancyTypeEnable : " + isOccupancyTypeEnable);

			Boolean isOccupancyValueEnable = ratesGrid.verifyTotalOccupanyValue(driver);
			app_logs.info("isOccupancyValueEnable : " + isOccupancyValueEnable);

			if (isOccupancyValueEnable && isOccupancyTypeEnable) {
				testSteps.add("Verified total occupancy enabled");
				app_logs.info("Verified total occupancy enabled");
			} else {
				Assert.assertTrue(false, "Failed : Found total occupancy disabled");
			}

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

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY TYPE ENABLED==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY TYPE ENABLED==========");

			Boolean isOccupancyTypeEnable = ratesGrid.verifyTotalOccupancyType(driver);
			app_logs.info("isOccupancyTypeEnable : " + isOccupancyTypeEnable);

			if (isOccupancyTypeEnable) {
				testSteps.add("Verified total occupancy type enabled");
				app_logs.info("Verified total occupancy type enabled");
			} else {
				Assert.assertTrue(false, "Failed : Found total occupancy type enabled");
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify types after enabled total occupancy", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify types after enabled total occupancy", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY TYPE DROPDOWN VALUES==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY TYPE DROPDOWN VALUES==========");

			String[] occupancyType = totalOccupancyType.split(",");
			for(String str : occupancyType){
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyTotalOccupancyTypeValues(driver, str);
				testSteps.addAll(getTestSteps);
			}

			app_logs.info("Verified total occupancy type values");
			testSteps.add("Verified total occupancy type values");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy type values", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy type values", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY VALUE ENABLED==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY VALUE ENABLED==========");

			Boolean isOccupancyValueEnable = ratesGrid.verifyTotalOccupanyValue(driver);
			app_logs.info("isOccupancyValueEnable : " + isOccupancyValueEnable);

			if (isOccupancyValueEnable) {
				testSteps.add("Verified total occupancy value enabled");
				app_logs.info("Verified total occupancy value enabled");
			} else {
				testSteps.add("Verified total occupancy value disabled");
				app_logs.info("Verified total occupancy value disabled");
				Assert.assertTrue(false, "Failed : Found total occupancy value disabled");
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy value is enabled", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy value is enabled", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickTotalOccupancy(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable total occupancy", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable total occupancy", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING ROOMCLASSES DROPDOWN IS DISPLAYING==========");
			testSteps.add("==========VERIFYING ROOMCLASSES DROPDOWN IS DISPLAYING==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyBulkUpdateDropDowns(driver, "Room class");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class dropdown is displaying", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class dropdown is displaying", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING ROOMCLASSES PLACEHOLDER TEXT==========");
			testSteps.add("==========VERIFYING ROOMCLASSES PLACEHOLDER TEXT==========");

			String expectedPlaceHolder = "Select room class(es)";
			app_logs.info("Expected : " + expectedPlaceHolder);
			testSteps.add("Expected : " + expectedPlaceHolder);

			String placeHolderText = ratesGrid.getDropDownsPlaceHolder(driver, "Room class");
			app_logs.info("Found : " + placeHolderText);
			testSteps.add("Found : " + placeHolderText);

			Assert.assertEquals(placeHolderText, expectedPlaceHolder,
					"Failed to match place holder text for Room class");
			app_logs.info("Verified room class place holder");
			testSteps.add("Verified room class place holder");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class placeholder", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class placeholder", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING ACTIVE ROOMCLASSESS ARE SHOWING IN BULK UPDATE POPUP==========");
			testSteps.add("==========VERIFYING ACTIVE ROOMCLASSESS ARE SHOWING IN BULK UPDATE POPUP==========");

			app_logs.info("GETTING ROOMCLASSES");
			testSteps.add("GETTING ROOMCLASSES");

			ArrayList<String> getDropDownItems = new ArrayList<>();
			getDropDownItems = ratesGrid.getBulkUpdateDropDownsList(driver, "Room class");
			testSteps.add("Get List of All Room classes: <b>" + getDropDownItems + "</b>");

			app_logs.info("getDropDownItems : " + getDropDownItems.size());

			getDropDownItems.remove(0);
			Boolean isRoomClassPresent = ratesGrid.compareLists(getRoomClasses, getDropDownItems);
			app_logs.info("isRoomClassPresent : " + isRoomClassPresent);
			Assert.assertTrue(isRoomClassPresent, "Failed to compare roomclasses");

			app_logs.info("Verified active room classes are showing in bulk update popup");
			testSteps.add("Verified active room classes are showing in bulk update popup");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify all active room classes is showing in room class drop down", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify all active room classes is showing in room class drop down", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			if (!allRoomClasses.isEmpty()) {
				app_logs.info("==========VERIFYING  ALL ROOM CLASS TEXT WITH LIST SIZE==========");
				testSteps.add("==========VERIFYING  ALL ROOM CLASS TEXT WITH LIST SIZE==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", allRoomClasses);
				testSteps.addAll(getTestSteps);

				app_logs.info("Expected : " + getRoomClasses.size());
				testSteps.add("Expected : " + getRoomClasses.size());

				String getAllRoomClass = ratesGrid.getAllRoomClassText(driver, "Room class", allRoomClasses);
				getAllRoomClass = getAllRoomClass.replace("All room classes (", "");
				app_logs.info("getAllRoomClass : " + getAllRoomClass);
				getAllRoomClass = getAllRoomClass.replace(")", "");
				app_logs.info("getAllRoomClass : " + getAllRoomClass);
				app_logs.info("Found : " + getAllRoomClass.trim());
				testSteps.add("Found : " + getAllRoomClass);

				Assert.assertEquals(getAllRoomClass.trim(), String.valueOf(getRoomClasses.size()),
						"Failed to match dropdown list size");
				app_logs.info("Verified room class list size");
				testSteps.add("Verified room class list size");

				app_logs.info("==========VERIFYING NO ROOMCLASSESS ARE SHOWING IN BULK UPDATE POPUP==========");
				testSteps.add("==========VERIFYING NO ROOMCLASSESS ARE SHOWING IN BULK UPDATE POPUP==========");

				int getRoomClassDropDownOptionSize = ratesGrid.getDropDownOptionsSize(driver, "Room class",
						allRoomClasses);

				app_logs.info("Get room class drop down size after selecetd all room class: "
						+ getRoomClassDropDownOptionSize);
				testSteps.add("Get room class drop down size after selecetd all room class: "
						+ getRoomClassDropDownOptionSize);
				assertEquals(getRoomClassDropDownOptionSize, 0,
						"Failed: Room classes drop down size is mismatching after selected all active room classes");
				testSteps.add("Verified not option is showing in room classes deopdown");

				getTestSteps.clear();
				getTestSteps = ratesGrid.removeSelectedOptionFromField(driver, "Room class", allRoomClasses);
				testSteps.addAll(getTestSteps);

			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify all room classes option", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify all room classes option", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");

			
			for (int i = 0; i < 3; i++) {
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", getRoomClasses.get(i));
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
			app_logs.info("==========VERIFYING UPDATE AVAILABILITY TEXT==========");
			testSteps.add("==========VERIFYING UPDATE AVAILABILITY TEXT==========");

			String expectedAvailablityTax = "Update availability";
			app_logs.info("Expected : " + expectedAvailablityTax);
			testSteps.add("Expected : " + expectedAvailablityTax);

			String getText = ratesGrid.verifyUpdateAvailability(driver);
			app_logs.info("Found : " + getText);
			testSteps.add("Found : " + getText);

			Assert.assertEquals(getText, expectedAvailablityTax, "Failed to match update availability text");
			app_logs.info("Verified update existing rules text");
			testSteps.add("Verified update existing rules text");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update existing rules", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update existing rules", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING BLACKOUT IS DISPLAYING FOR ALL SOURCES==========");
			testSteps.add("==========VERIFYING BLACKOUT IS DISPLAYING FOR ALL SOURCES==========");

			for (int i = 0; i < activeChannelsList.size(); i++) {
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyUpdateAvailability(driver, activeChannelsList.get(i), blackOut);
				testSteps.addAll(getTestSteps);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify blackout is displaying for all sources", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify blackout is displaying for all sources", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING AVAILABILITY IS DISPLAYING FOR ALL SOURCES==========");
			testSteps.add("==========VERIFYING AVAILABILITY IS DISPLAYING FOR ALL SOURCES==========");

			for (int i = 0; i < activeChannelsList.size(); i++) {
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyUpdateAvailability(driver, activeChannelsList.get(i), available);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify availability is displaying for all sources", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify availability is displaying for all sources", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {
			
			app_logs.info("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING SOURCE==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING SOURCE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is disabled before clicking source");
			testSteps.add("Verified update button is disabled before clicking source");


			for (int i = 0; i < activeChannelsList.size(); i++) {
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectUpdateAvailability(driver, activeChannelsList.get(i), available);
				testSteps.addAll(getTestSteps);

			}
			app_logs.info("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING SOURCE==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING SOURCE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);


			app_logs.info(
					"Verified update button is enabled after clicking source");
			testSteps.add(
					"Verified update button is enabled after clicking source");

			

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify update button is enable/disable before/after clicking inncenter source",
						scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify update button is enable/disable before/after clicking inncenter source",
						scriptName, "BulkUpdate", driver);
			}
		}

		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to log steps to report", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to log steps to report", scriptName, "BulkUpdate", driver);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("UIValidationForBulkUpdateAvail", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver() {
		// driver.quit();
	}

}
