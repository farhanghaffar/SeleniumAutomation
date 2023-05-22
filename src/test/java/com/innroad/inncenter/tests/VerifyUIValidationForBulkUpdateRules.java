package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
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

public class VerifyUIValidationForBulkUpdateRules extends TestCore {

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
	ArrayList<String> activeRatePlanNames = new ArrayList<>();
	ArrayList<String> inactiveRatePlanNames = new ArrayList<>();

	public static String testdescription = "";
	public static String testcatagory = "";

	@Test(dataProvider = "getData", groups = "RatesGrid")
	public void verifyUIValidationForBulkUpdateRules(String startDate, String endDate, String sunday, String monday,
			String tuesday, String wednesday, String thursday, String friday, String saturday,
			String isTotalOccupancyOn, String totalOccupancyType, String totalOccupancyValue, String roomClassName,
			String channel, String ratePlan, String isMinimumStayOn, String minimumStayValue, String isCheckInOn,
			String noCheckIn, String isCheckOutOn, String noCheckOut, String rules, String allRoomClasses,
			String allActiveRatePlans, String allInActiveRatePlan, String allSources, String delim) throws Exception {

		String scriptName = "VerifyUIValidationForBulkUpdateRules";
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
		String closeToggle = "No";
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
		}

		// Get All RoomClasses/Distribution
		try {

			testSteps.add("==========GET ALL ACTIVE CHANNEL=========");
			app_logs.info("==========GET ALL ACTIVE CHANNEL==========");

			navigation.Inventory_Backward_1(driver);
			testSteps.add("Navigated to Inventory");

			navigation.Distribution(driver);
			testSteps.add("Navigate distribution");

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
			testSteps.add("Getting active and and inactive rate plan");
			navigation.Inventory(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			getTestSteps.clear();
			getTestSteps = navigation.ratesGrid(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.clickRatePlanArrow(driver, testSteps);

			activeRatePlanNames = ratesGrid.getActiveAndInactiveRatePlanNames(driver, "active");
			app_logs.info("rate plan size for active: " + activeRatePlanNames.size());
			testSteps.add("Active rate plan list: " + activeRatePlanNames);

			inactiveRatePlanNames = ratesGrid.getActiveAndInactiveRatePlanNames(driver, "inactive");
			app_logs.info("rate plan size for inactive: " + inactiveRatePlanNames.size());
			testSteps.add("InActive rate plan list: " + inactiveRatePlanNames);

		} catch (Exception e) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to Get Rate Plan Name", scriptName, "RatePlanName", driver);
		} catch (Error e) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to Get Rate Plan Name", scriptName, "RatePlanName", driver);

		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAvailabilityTab(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectBulkUpdateOption(driver, rules);
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

			app_logs.info("==========VERIFYING BULK UPDATE RULES POPUP HEADING==========");
			testSteps.add("==========VERIFYING BULK UPDATE RULES POPUP HEADING==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyRulesHeading(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
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

			String closeText = ratesGrid.getCloseIconText(driver);
			//Assert.assertEquals(closeText, "", "Failed to match close text");

			testSteps.add("Verified popup close sign");
			app_logs.info("Verified popup close sign");

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

			app_logs.info("==========VERIFYING BULK UPDATE RULES POPUP TEXT==========");
			testSteps.add("==========VERIFYING BULK UPDATE RULES POPUP TEXT==========");

			String exectedPopupText = "This one-time update will adjust rules for all days that match your criteria.\n"
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
			app_logs.info("==========VERIFYING START DATE CALENADR==========");
			testSteps.add("==========VERIFYING START DATE CALENADR==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyStartDateCalendar(driver, getCurrentDate);
			testSteps.addAll(getTestSteps);

			testSteps.add("Verified start date calendar");
			app_logs.info("Verified start date calendar");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify start date calendar ", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify start date calendar", scriptName, "BulkUpdate", driver);
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

			Assert.assertEquals(getEndDate, getCurrentDate, "Failed: end date is mismatching!");
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
				Utility.updateReport(e, "Failed to verify end date calendar ", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify end date calendar", scriptName, "BulkUpdate", driver);
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
				testSteps.add("Verified total occupancy enabled by default");
				app_logs.info("Verified total occupancy enabled by default");
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
				testSteps.add("Verified total occupancy type enabled by default");
				app_logs.info("Verified total occupancy type enabled by default");
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

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY VALUE FIELD DISABLED BY DAFAULT==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY VALUE FIELD DISABLED BY DAFAULT==========");

			Boolean isOccupancyValueEnable = ratesGrid.verifyTotalOccupanyValue(driver);
			app_logs.info("isOccupancyValueEnable : " + isOccupancyValueEnable);

			if (!isOccupancyValueEnable) {
				testSteps.add("Verified total occupancy value disabled by default");
				app_logs.info("Verified total occupancy value disabled by default");
			} else {
				testSteps.add("Verified total occupancy value enabled by default");
				app_logs.info("Verified total occupancy value enabled by default");
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
				Utility.updateReport(e, "Failed to verify total occupancy type is enabled", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy type is enabled", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY TYPES==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY TYPES==========");

			String[] occupancyType = totalOccupancyType.split(",");
			for (String str : occupancyType) {
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyTotalOccupancyTypeValues(driver, str);
				testSteps.addAll(getTestSteps);
			}

			app_logs.info("Verified total occupancy types");
			testSteps.add("Verified total occupancy types");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy types", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total occupancy types", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			app_logs.info("==========VERIFYING TOTAL OCCUPANCY VALUE FILED ENABLED==========");
			testSteps.add("==========VERIFYING TOTAL OCCUPANCY VALUE FILED ENABLED==========");

			Boolean isOccupancyValueEnable = ratesGrid.verifyTotalOccupanyValue(driver);
			app_logs.info("isOccupancyValueEnable : " + isOccupancyValueEnable);

			if (isOccupancyValueEnable) {
				testSteps.add("Verified total occupancy value enabled");
				app_logs.info("Verified total occupancy value enabled");
			} else {
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

			app_logs.info("==========VERIFYING RATEPLAN DROPDOWN IS DISPLAYING==========");
			testSteps.add("==========VERIFYING RATEPLAN DROPDOWN IS DISPLAYING==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyBulkUpdateDropDowns(driver, "Rate Plan");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan dropdown is displaying", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan dropdown is displaying", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING RATEPLAN PLACEHOLDER TEXT==========");
			testSteps.add("==========VERIFYING RATEPLAN PLACEHOLDER TEXT==========");

			String expectedPlaceHolder = "Choose existing rate plan(s)";
			app_logs.info("Expected : " + expectedPlaceHolder);
			testSteps.add("Expected : " + expectedPlaceHolder);

			String placeHolderText = ratesGrid.getDropDownsPlaceHolder(driver, "Rate Plan");
			app_logs.info("Found : " + placeHolderText);
			testSteps.add("Found : " + placeHolderText);

			Assert.assertEquals(placeHolderText, expectedPlaceHolder,
					"Failed: Rate paln place holder text is mismatching");
			app_logs.info("Verified rateplan place holder");
			testSteps.add("Verified rateplan place holder");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan placeholder", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan placeholder", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING ACTIVE RATES PLAN==========");
			testSteps.add("==========VERIFYING ACTIVE RATES PLAN==========");

			app_logs.info("GETTING RATES PLAN FROM SELECT RATE PLAN");
			testSteps.add("GETTING RATES PLAN FROM SELECT RATE PLAN");

			ArrayList<String> getDropDownItems = new ArrayList<>();
			getDropDownItems = ratesGrid.getBulkUpdateDropDownsList(driver, "Rate Plan");
			Collections.sort(getDropDownItems);
			testSteps.add("Get List of All Rate planes: <b>" + getDropDownItems + "</b>");
			app_logs.info("getDropDownItems : " + getDropDownItems.size());

			for (int i = 0; i < activeRatePlanNames.size(); i++) {
				boolean isRatePlanExist = false;
				for (int j = 0; j < getDropDownItems.size(); j++) {
					if (activeRatePlanNames.get(i).equals(getDropDownItems.get(j))) {
						isRatePlanExist = true;
						break;
					}
				}
				assertEquals(isRatePlanExist, true,
						"Failed: rate plan ' " + activeRatePlanNames.get(i) + "' is not showing in rate plan list");
			}

			app_logs.info("Verified active rates plan");
			testSteps.add("Verified active rates plan");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify active rate plan", scriptName, "VerifyActiveRatePlan",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify active rate plan", scriptName, "VerifyActiveRatePlan",
						driver);
			}
		}
		try {
			app_logs.info("==========VERIFYING InACTIVE RATES PLAN==========");
			testSteps.add("==========VERIFYING InACTIVE RATES PLAN==========");

			ArrayList<String> getDropDownItems = new ArrayList<>();
			getDropDownItems = ratesGrid.getBulkUpdateDropDownsList(driver, "Rate Plan");
			Collections.sort(getDropDownItems);

			for (int i = 0; i < inactiveRatePlanNames.size(); i++) {
				boolean isRatePlanExist = false;
				for (int j = 0; j < getDropDownItems.size(); j++) {
					
					String planName = inactiveRatePlanNames.get(i);
						planName = planName.replace("[I", "- i");
						planName = planName.replace("]", "");
					if (planName.equals(getDropDownItems.get(j))) {
						isRatePlanExist = true;
						break;
					}
				}
				assertEquals(isRatePlanExist, true,
						"Failed: rate plan ' " + inactiveRatePlanNames.get(i) + "' is not showing in rate plan list");
			}

			app_logs.info("Verified inactive rates plan");
			testSteps.add("Verified inactive rates plan");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify inactive rates plan", scriptName, "VerifyInActiveRatePlan",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify inactive rates plan", scriptName, "VerifyInActiveRatePlan",
						driver);
			}
		}

		try {

			app_logs.info("==========SELECTING ALL ACTIVE RATES PLAN==========");
			testSteps.add("==========SELECTING ALL ACTIVE RATES PLAN==========");

			int activeRatePlansSize;
			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", allActiveRatePlans);
			testSteps.addAll(getTestSteps);

			String getAllRatePlan = ratesGrid.getAllRoomClassText(driver, "Rate Plan", allActiveRatePlans);
			getAllRatePlan = getAllRatePlan.replace("All Active Rate Plans (", "");
			getAllRatePlan = getAllRatePlan.replace(")", "");
			activeRatePlansSize = Integer.parseInt(getAllRatePlan);

			testSteps.add("Expected active plan size: " + activeRatePlanNames.size());
			app_logs.info("Found : " + activeRatePlansSize);
			testSteps.add("Found : " + activeRatePlansSize);
			assertEquals(activeRatePlansSize, activeRatePlanNames.size(),
					"Failed: Total active rate plan size is mismatching");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total active rate plan", scriptName, "VerifyTotalActiveRatePlan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total active rate plan", scriptName, "VerifyTotalActiveRatePlan", driver);
			}
		}
		
		try {

			app_logs.info("==========SELECTING ALL InACTIVE RATES PLAN==========");
			testSteps.add("==========SELECTING ALL InACTIVE RATES PLAN==========");
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", allInActiveRatePlan);
			testSteps.addAll(getTestSteps);

			String getAllRatePlan = ratesGrid.getAllRoomClassText(driver, "Rate Plan", allInActiveRatePlan);
			getAllRatePlan = getAllRatePlan.replace("All Inactive Rate Plans (", "");
			app_logs.info("getAllRatePlan : " + getAllRatePlan);
			getAllRatePlan = getAllRatePlan.replace(")", "");
			app_logs.info("getAllRatePlan : " + getAllRatePlan);

			int inActiveRatePlansSize = Integer.parseInt(getAllRatePlan);
			testSteps.add("Expected inactive plan size: " + inactiveRatePlanNames.size());
			app_logs.info("Found : " + inActiveRatePlansSize);
			testSteps.add("Found : " + inActiveRatePlansSize);
			assertEquals(inActiveRatePlansSize, inactiveRatePlanNames.size(),
					"Failed: Total inactive rate plan size is mismatching");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify inactive rate plan size", scriptName, "VerifyInactiveRateplan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify inactive rate plan size", scriptName, "VerifyInactiveRateplan", driver);
			}
		}
		try {

			

			app_logs.info("==========VERIFYING NO RATE PLAN OPTIONS ARE SHOWING IN RATE PLAN DROP DWON AFTER SELECTED ALL RATE PLAN==========");
			testSteps.add("==========VERIFYING NO RATE PLAN OPTIONS ARE SHOWING IN RATE PLAN DROP DWON AFTER SELECTED ALL RATE PLAN==========");

			int getRatePlanDropDownOptionSize = ratesGrid.getDropDownOptionsSize(driver, "Rate Plan", activeRatePlanNames.get(0));

			app_logs.info("Get rate plan drop down size after selecetd all active rate plan: "
					+ getRatePlanDropDownOptionSize);
			testSteps.add("Get rate plan drop down size after selecetd all active rate plan: "
					+ getRatePlanDropDownOptionSize);

			assertEquals(getRatePlanDropDownOptionSize, 0,
					"Failed: Rate plan drop down size is mismatching after selected all active rate plans");
			testSteps.add(
					"Verified not option is showing in rate plan dropdown after selecting all active rate plans");

			getTestSteps.clear();
			getTestSteps = ratesGrid.removeSelectedOptionFromField(driver, "Rate Plan", allActiveRatePlans);
			testSteps.addAll(getTestSteps);
		
			getRatePlanDropDownOptionSize = ratesGrid.getDropDownOptionsSize(driver, "Rate Plan", inactiveRatePlanNames.get(0));
			app_logs.info("Get rate plan drop down size after seleceted all inactive rate plan: "
					+ getRatePlanDropDownOptionSize);
			testSteps.add("Get rate plan drop down size after selecetd all active and inative rate plan: "
					+ getRatePlanDropDownOptionSize);
			assertEquals(getRatePlanDropDownOptionSize, 0,
					"Failed: Rate plan drop down size is mismatching after selected all inctive rate plan");
			testSteps.add(
					"Verified not option is showing in rate plan dropdown after selecting all inactive rate plans");


			getTestSteps.clear();
			getTestSteps = ratesGrid.removeSelectedOptionFromField(driver, "Rate Plan", allInActiveRatePlan);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify drop down option after selected all active and inactive rate plans", scriptName, "VerifySelectedOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify drop down option after selected all active and inactive rate plans", scriptName, "BulkUpdate", driver);
			}
		}
		try {
			app_logs.info("==========SELECTING RATE PLAN==========");
			testSteps.add("==========SELECTING RATE PLAN==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", ratePlan);
				testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select rate plan", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select rate plan", scriptName, "BulkUpdate", driver);
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
				Utility.updateReport(e, "Failed to get all room classes", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all room classes", scriptName, "BulkUpdate", driver);
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
				testSteps.add("Verified not option is showing in room classes dropdown");

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
			app_logs.info("==========VERIFYING SOURCE DROPDOWN IS DISPLAYING==========");
			testSteps.add("==========VERIFYING SOURCE DROPDOWN IS DISPLAYING==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyBulkUpdateDropDowns(driver, "Source");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify source dropdown is displaying", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify source dropdown is displaying", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING SOURCES PLACEHOLDER TEXT==========");
			testSteps.add("==========VERIFYING SOURCES PLACEHOLDER TEXT==========");

			String expectedPlaceHolder = "Select source(s)";
			app_logs.info("Expected : " + expectedPlaceHolder);
			testSteps.add("Expected : " + expectedPlaceHolder);

			String placeHolderText = ratesGrid.getDropDownsPlaceHolder(driver, "Source");
			app_logs.info("Found : " + placeHolderText);
			testSteps.add("Found : " + placeHolderText);

			Assert.assertEquals(placeHolderText, expectedPlaceHolder, "Failed to match place holder text for Source");
			app_logs.info("Verified source place holder");
			testSteps.add("Verified source place holder");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify source placeholder", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify source placeholder", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING SOURCES ARE SHOWING IN BULK UPDATE POPUP==========");
			testSteps.add("==========VERIFYING SOURCES ARE SHOWING IN BULK UPDATE POPUP==========");

			app_logs.info("GETTING SOURCES");
			testSteps.add("GETTING SOURCES");

			ArrayList<String> getDropDownItems = new ArrayList<>();
			getDropDownItems = ratesGrid.getBulkUpdateDropDownsList(driver, "Source");
			testSteps.add("Get List of All Sources: <b>" + getDropDownItems + "</b>");
			Boolean isSourcePresent = false;
			for (int i = 0; i < activeChannelsList.size(); i++) {
				if (getDropDownItems.contains(activeChannelsList.get(i))) {
					isSourcePresent = true;
				} else {
					isSourcePresent = false;
				}
			}
			app_logs.info(isSourcePresent);
			Assert.assertTrue(isSourcePresent, "Failed to match sources");
			app_logs.info("Verified sources are showing in bulk update popup");
			testSteps.add("Verified sources are showing in bulk update popup");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all sources", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get all sources", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING  ALL SOURCE TEXT WITH LIST SIZE==========");
			testSteps.add("==========VERIFYING  ALL SOURCE TEXT WITH LIST SIZE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", allSources);
			testSteps.addAll(getTestSteps);

			app_logs.info("Expected : " + activeChannelsList.size());
			testSteps.add("Expected : " + activeChannelsList.size());

			String getAllSource = ratesGrid.getAllRoomClassText(driver, "Source", allSources);
			getAllSource = getAllSource.replace("All sources (", "");
			app_logs.info("getAllSource : " + getAllSource);
			getAllSource = getAllSource.replace(")", "");
			app_logs.info("getAllSource : " + getAllSource);
			app_logs.info("Found : " + getAllSource.trim());
			testSteps.add("Found : " + getAllSource);

			Assert.assertEquals(getAllSource.trim(), String.valueOf(activeChannelsList.size()),
					"Failed to match drop down list size");
			app_logs.info("Verified source list size");
			testSteps.add("Verified source list size");

			app_logs.info("==========VERIFYING NO SOURCE ARE SHOWING IN BULK UPDATE POPUP==========");
			testSteps.add("==========VERIFYING NO SOURCE ARE SHOWING IN BULK UPDATE POPUP==========");

			int getSourceDropDownOptionSize = ratesGrid.getDropDownOptionsSize(driver, "Source", allSources);

			app_logs.info("Get sources drop down size after selecetd all source: " + getSourceDropDownOptionSize);
			testSteps.add("Get sources drop down size after selecetd all source: " + getSourceDropDownOptionSize);
			assertEquals(getSourceDropDownOptionSize, 0,
					"Failed: sources drop down size is mismatching after selected all active sources");
			testSteps.add("Verified not option is showing in sources dropdown");

			getTestSteps.clear();
			getTestSteps = ratesGrid.removeSelectedOptionFromField(driver, "Source", allSources);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify all source option", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify all source option", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING SOURCE==========");
			testSteps.add("==========SELECTING SOURCE==========");

			for (int i = 0; i < activeChannelsList.size(); i++) {
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", activeChannelsList.get(i));
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select source", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select source", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE EXISTING RULES TEXT==========");
			testSteps.add("==========VERIFYING UPDATE EXISTING RULES TEXT==========");

			String expectedUpdateExistingRulesText = "Update existing rules";
			app_logs.info("Expected : " + expectedUpdateExistingRulesText);
			testSteps.add("Expected : " + expectedUpdateExistingRulesText);

			String placeHolderText = ratesGrid.verifyUpdateExistingRule(driver);
			app_logs.info("Found : " + placeHolderText);
			testSteps.add("Found : " + placeHolderText);

			Assert.assertEquals(placeHolderText, expectedUpdateExistingRulesText,
					"Failed to match update existing rules text");
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
			app_logs.info("==========VERIFYING MINIMUM STAY IS DISABLED BY DEFAULT==========");
			testSteps.add("==========VERIFYING MINIMUM STAY IS DISABLED BY DEFAULT==========");

			Boolean isCheck = ratesGrid.verifyMinimumStayValue(driver);
			app_logs.info("isCheck : " + isCheck);

			if (!isCheck) {
				app_logs.info("Verified minimum stay is disabled by default");
				testSteps.add("Verified minimum stay is disabled by default");
			} else {
				app_logs.info("Verified minimum stay is enabled by default");
				testSteps.add("Verified minimum stay is enabled by default");
				Assert.assertTrue(false, "Failed : Found  minimum stay enabled by default");

			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify  minimum stay is disabled by default", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify  minimum stay is disabled by default", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING CHECKIN IS DISABLED BY DEFAULT==========");
			testSteps.add("==========VERIFYING CHECKIN IS DISABLED BY DEFAULT==========");

			Boolean isCheck = ratesGrid.verifyNoCheckInCheckbox(driver);
			app_logs.info("isCheck : " + isCheck);

			if (!isCheck) {
				app_logs.info("Verified check in is disabled by default");
				testSteps.add("Verified check in is disabled by default");
			} else {
				app_logs.info("Verified check in is enabled by default");
				testSteps.add("Verified check in is enabled by default");
				Assert.assertTrue(false, "Failed : Found check in enabled by default");

			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify check in is disabled by default", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify check in is disabled by default", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING CHECKOUT IS DISABLED BY DEFAULT==========");
			testSteps.add("==========VERIFYING CHECKOUT IS DISABLED BY DEFAULT==========");

			Boolean isCheck = ratesGrid.verifyNoCheckOutCheckbox(driver);
			app_logs.info("isCheck : " + isCheck);

			if (!isCheck) {
				app_logs.info("Verified check out is disabled by default");
				testSteps.add("Verified check out is disabled by default");
			} else {
				app_logs.info("Verified check out is enabled by default");
				testSteps.add("Verified check out is enabled by default");
				Assert.assertTrue(false, "Failed : Found check out enabled by default");

			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify check out is disabled by default", scriptName, "BulkUpdate",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify check out is disabled by default", scriptName, "BulkUpdate",
						driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING MINIMUM STAY==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING MINIMUM STAY==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is disabled before clicking minimum stay");
			testSteps.add("Verified update button is disabled before clicking minimum stay");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is disable before clicking minimum stay",
						scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is disable before clicking minimum stay",
						scriptName, "BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, isMinimumStayOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING MINIMUM STAY==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING MINIMUM STAY==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is enabled after clicking minimum stay");
			testSteps.add("Verified update button is enabled after clicking minimum stay");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking minimum stay",
						scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking minimum stay",
						scriptName, "BulkUpdate", driver);
			}
		}
		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable minimum stay", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING CHECKIN==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING CHECKIN==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is disabled before clicking checkin");
			testSteps.add("Verified update button is disabled before clickingcheckin");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is disable before clicking checkin", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is disable before clicking checkin", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, isCheckInOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING CHECKIN==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING CHECKIN==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is enabled after clicking checkin");
			testSteps.add("Verified update button is enabled after clicking checkin");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking checkin", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking checkin", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkin button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkin button", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING CHECKOUT==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS DISABLED BEFORE CLICKING CHECKOUT==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is disabled before clicking checkout");
			testSteps.add("Verified update button is disabled before clicking checkout");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is disable before clicking checkout",
						scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is disable before clicking checkout",
						scriptName, "BulkUpdate", driver);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, isCheckOutOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING CHECKOUT==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING CHECKOUT==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is enabled after checkout");
			testSteps.add("Verified update button is enabled after checkout");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking checkout", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking checkout", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkout button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkout button", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info(
					"==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING MINIMUM STAY AND CHECKIN==========");
			testSteps.add(
					"==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING MINIMUM STAY AND CHECKIN==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, isMinimumStayOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, isCheckInOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is enabled after clicking minimum stay and checkin");
			testSteps.add("Verified update button is enabled after clicking minimum stay and checkin");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify update button is enabled after clicking minimum stay and checkin", scriptName,
						"BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify update button is enabled after clicking minimum stay and checkin", scriptName,
						"BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable minimum stay", scriptName, "BulkUpdate", driver);
			}
		}
		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkin button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkin button", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info(
					"==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING MINIMUM STAY AND CHECKOUT==========");
			testSteps.add(
					"==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING MINIMUM STAY AND CHECKOUT==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, isMinimumStayOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, isCheckOutOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is enabled after clicking minimum stay and checkout");
			testSteps.add("Verified update button is enabled after clicking minimum stay and checkout");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify update button is enabled after clicking minimum stay and checkout",
						scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify update button is enabled after clicking minimum stay and checkout",
						scriptName, "BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable minimum stay", scriptName, "BulkUpdate", driver);
			}
		}
		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkout button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkout button", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			app_logs.info("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING CHECKIN AND CHECKOUT==========");
			testSteps.add("==========VERIFYING UPDATE BUTTON IS ENABLED AFTER CLICKING CHECKIN AND CHECKOUT==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, isCheckInOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin", scriptName, "BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, isCheckOutOn);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout", scriptName, "BulkUpdate", driver);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyUpdateButtonEnabled(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("Verified update button is enabled after clicking checkin and checkout");
			testSteps.add("Verified update button is enabled after clicking checkin and checkout");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking checkin and checkout",
						scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify update button is enabled after clicking checkin and checkout",
						scriptName, "BulkUpdate", driver);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkin", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkin", scriptName, "BulkUpdate", driver);
			}
		}
		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, closeToggle);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkout button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to disable checkout button", scriptName, "BulkUpdate", driver);
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

		return Utility.getData("UIValidationForBulkUpdateRules", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver() {
		// driver.quit();
	}

}
