package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAvailabilityInBulkUpdateReservationTapeChartAndAdvanceSearch extends TestCore {

	// Automation-1706
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
			String totalOccupancyValue, String roomClassName, String RoomClassesAbbrevaition, String channel,
			String blackout, String availability, String adults, String children, String ratePlan, String promoCode,
			String isAssign, String account, String available, String marketSegment, String groupReferral,
			String groupFirstName, String groupLastName, String groupPhn, String groupAddress, String groupCity,
			String groupCountry, String groupState, String groupPostalcode, String corpFirstName, String corpLastName,
			String corpAccountType, String email, String travelAccType, String travelFirstName, String delim) throws Exception {

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
		CPReservationPage reservationPage = new CPReservationPage();
		Tapechart tapeChart = new Tapechart();
		Groups group = new Groups();
		Account CreateTA = new Account();

		String accountNumber = null;
		String corpAccountNumber = null;
		String randomNumber = Utility.GenerateRandomNumber();
		groupLastName = groupLastName + randomNumber;
		String accountName = groupFirstName + groupLastName;
		corpLastName = corpLastName + randomNumber;
		String corpAccountFullName = corpFirstName + corpLastName;
		String travelAccountFullName = travelFirstName + corpLastName;
		//String checkInDate = ESTTimeZone.reformatDate(startDate, "MM/dd/yyyy", "dd/MM/yyyy");
		//String checkOutDate = ESTTimeZone.reformatDate(endDate, "MM/dd/yyyy", "dd/MM/yyyy");
		//app_logs.info(checkInDate);
		//app_logs.info(checkOutDate);
		
		String timeZone = "America/New_York";
		String getCurrentDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
		if (ESTTimeZone.CompareDates(startDate, "MM/dd/yyyy", timeZone)) {
			startDate = getCurrentDate;
		}
		if (ESTTimeZone.CompareDates(endDate, "MM/dd/yyyy", timeZone)) {
			 getCurrentDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
			endDate = getCurrentDate;
		}
	
		
		
		app_logs.info("startDate: "+startDate);
		app_logs.info("endDate: "+endDate);
		ArrayList<String> listOfRoomClasses = new ArrayList<>();
		ArrayList<String> listOfRoomClassesAbbrevaition = new ArrayList<>();
		HashMap<String, String> listOfRoomQuentity = new HashMap<String, String>();
		HashMap<String, String> listOfSortId = new HashMap<String, String>();
		RoomClass roomClass = new RoomClass();

		ArrayList<String> listOfDays = new ArrayList<>();
		listOfDays.add("Sun");
		listOfDays.add("Mon");
		listOfDays.add("Tue");
		listOfDays.add("Wed");
		listOfDays.add("Thu");
		listOfDays.add("Fri");
		listOfDays.add("Sat");

		HashMap<String, String> listOfDay = new HashMap<String, String>();
		listOfDay.put("Sun", isSundayCheck);
		listOfDay.put("Mon", isMondayCheck);
		listOfDay.put("Tue", isTuesdayChecked);
		listOfDay.put("Wed", isWednesdayChecked);
		listOfDay.put("Thu", isThursdayChecked);
		listOfDay.put("Fri", isFridayChecked);
		listOfDay.put("Sat", isSaturdayChecked);
		int days = 0;
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

			List<String>[] arrayOfList = new List[6];
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

			ArrayList<String> sortOrder = new ArrayList<>();
			sortOrder = (ArrayList<String>) arrayOfList[5];

			for (int i = 0; i < sortOrder.size(); i++) {
				listOfSortId.put(roomClassesAbbreviation.get(i), sortOrder.get(i));
			}

			for (int i = 0; i < getRoomsNumber.size(); i++) {
				listOfRoomQuentity.put(roomClassesAbbreviation.get(i), getRoomsNumber.get(i));
			}
			app_logs.info("listOfRoomQuentity: " + listOfRoomQuentity);

			app_logs.info("Sort id: " + listOfSortId);
			app_logs.info(getRoomClasses.size());
			testSteps.add("Get List of All RoomClasses: <b>" + getRoomClasses + "</b>");
			testSteps.add("Get List of All MaxAdults: <b>" + maxAdults + "</b>");
			testSteps.add("Get List of All MaxPersons: <b>" + maxPersons + "</b>");

			app_logs.info("roomClassesAbbreviation: " + roomClassesAbbreviation.size());
			app_logs.info("getRoomsNumber: " + getRoomsNumber.size());
			for (int i = 0; i < getRoomClasses.size(); i++) {
				System.out.println(getRoomClasses.get(i) + "  " + roomClassesAbbreviation.get(i) + "  "
						+ getRoomsNumber.get(i) + " " + maxAdults.get(i) + " " + maxPersons.get(i));
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
		
		try {

			navigation.Inventory_Backward_1(driver);
			//navigation.Inventory(driver);
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
		
		// here select date 
		try {
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnCalendarIcon(driver);
			testSteps.addAll(getTestSteps);
			
			String getMonth = ratesGrid.getMonthFromCalendarHeading(driver);
			boolean isMothEqual = false;
			String expectedMonth = ESTTimeZone.getDateBaseOnDate(startDate, "MM/dd/yyyy", "MMMM yyyy");
			String getStartDate = ESTTimeZone.getDateBaseOnDate(startDate, "MM/dd/yyyy", "EEE MMM dd yyyy");

			app_logs.info("expectedMonth: "+expectedMonth);
			String path = "//div[@aria-label='"+getStartDate+"']";
			while(!isMothEqual){
				if (expectedMonth.equals(getMonth)) {
					app_logs.info("In if");
					isMothEqual = true;
					driver.findElement(By.xpath(path)).click();
					break;
				}
				else{
					app_logs.info("in else");
					ratesGrid.clickOnCalendarRightArrow(driver);
					getMonth = ratesGrid.getMonthFromCalendarHeading(driver);
					app_logs.info("getMonth: "+getMonth);
				}
				
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on calendar icon", scriptName, "RateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on calendar icon", scriptName, "RateGrid", driver);
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
				Utility.updateReport(e, "Failed to navigate to availability popup", scriptName, "NavigateAvailability",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to availability popup", scriptName, "NavigateAvailability",
						driver);
			}
		}
		try {

			testSteps.add("==========SELECT START DATE==========");
			app_logs.info("==========SELECT START DATE==========");

			getTestSteps.clear();
			//getTestSteps = ratesGrid.startDate(driver, startDate);
		    getTestSteps = ratesGrid.selectStartDate(driver, startDate);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select start", scriptName, "EnterStartDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select start", scriptName, "EnterStartDate", driver);
			}
		}

		try {
			testSteps.add("==========SELECT END DATE==========");
			app_logs.info("==========SELECT END DATE==========");

			getTestSteps.clear();
			//getTestSteps = ratesGrid.endDate(driver, endDate);
		    getTestSteps = ratesGrid.selectEndDate(driver, endDate);
			testSteps.addAll(getTestSteps);
			ratesGrid.verifyAvailabilityHeading(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select end date", scriptName, "EnterEndDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select end date", scriptName, "EnterEndDate", driver);
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
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "CheckDays", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "CheckDays", driver);
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
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "OccupancyToggle", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "OccupancyToggle", driver);
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
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "OccupancyType", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "OccupancyType", driver);
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
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "OccupancyValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "OccupancyValue", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");

			String[] roomClassesArray = roomClassName.split(",");
			for (String getrRoomClassName : roomClassesArray) {
				getrRoomClassName = getrRoomClassName.trim();
				listOfRoomClasses.add(getrRoomClassName);
				app_logs.info(getrRoomClassName.trim());
			}
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClassName);
				testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", scriptName, "SelectRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", scriptName, "SelectRoomClass", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING UPDATE AVAILABILITY==========");
			testSteps.add("==========SELECTING UPDATE AVAILABILITY==========");
			String[] channelArr = channel.split(",");
			for (String channelString : channelArr) {

				channelString = channelString.trim();
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectUpdateAvailability(driver, channelString.trim(), blackout);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select update availability", scriptName, "UpdateAvailability",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select update availability", scriptName, "UpdateAvailability",
						driver);
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

			days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
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

		// verify availability in tape chart
		try {
			app_logs.info("==========VERIFY BALACKOUT ROOM CLASSES IN TAPECHART==========");
			testSteps.add("==========VERIFY BALACKOUT ROOM CLASSES IN TAPECHART==========");
			navigation.navigateToReservation(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");
			navigation.ClickTapeChart(driver);
			testSteps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");

		} catch (Exception e) {
			if (Utility.count == RetryFailedTestCases.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to tape chart", scriptName, "tapechart", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to tape chart", scriptName, "tapechart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			String checkoutDate = "";
			String getDate = startDate;
			listOfRoomClasses.size();
		String 	ratePlanforTapechart = "-- Best Available -- ";
			String[] abbrevaition = RoomClassesAbbrevaition.split(",");
			for (String getAbbrevaition : abbrevaition) {

				getAbbrevaition = getAbbrevaition.trim();
				listOfRoomClassesAbbrevaition.add(getAbbrevaition);
				app_logs.info("abbrevaition: " + getAbbrevaition);
			}

			// days
			for (int day = 0; day < days; day++) {
				getDate = ESTTimeZone.getDateonBasedOfDate("MM/dd/yyyy", getDate, day, "US/Eastern");
				checkoutDate = ESTTimeZone.getDateonBasedOfDate("MM/dd/yyyy", getDate, day + 1, "US/Eastern");
				tapeChart.searchInTapechart(driver, getDate, checkoutDate, "1", "", ratePlanforTapechart, "");
				// here get day for verification
				try {
					Wait.wait1Second();

					// Wait.waitForElementToBeVisibile(By.className(OR.Toaster_Message),
					// driver, 2);
					driver.findElement(By.className(OR.Toaster_Message)).click();
					Wait.wait1Second();
				} catch (Exception e) {
					// TODO: handle exception
				}
				String getDay = ESTTimeZone.getDayBasedOnDate("MM/dd/yyyy", getDate, "US/Eastern", "E");
				String pathofRoomClasses = "//div[contains(text(),'" + listOfRoomClassesAbbrevaition.get(0)
						+ "')  and contains(@class,'roomclassname')]";
				Wait.WaitForElement(driver, pathofRoomClasses);
				Wait.waitForElementToBeVisibile(By.xpath(pathofRoomClasses), driver);
				Wait.waitForElementToBeClickable(By.xpath(pathofRoomClasses), driver);

				tapeChart.clickOnOneDay(driver);
				try {
					Wait.wait1Second();
					// Wait.waitForElementToBeVisibile(By.className(OR.Toaster_Message),
					// driver, 2);
					driver.findElement(By.className(OR.Toaster_Message)).click();
					Wait.wait1Second();
				} catch (Exception e) {
					// TODO: handle exception
				}

				// room classes
				for (int i = 0; i < listOfRoomClassesAbbrevaition.size(); i++) {

					pathofRoomClasses = "//div[contains(text(),'" + listOfRoomClassesAbbrevaition.get(i)
							+ "')  and contains(@class,'roomclassname')]";

					// here put another loop for each room number
					app_logs.info("room class: " + listOfRoomClassesAbbrevaition.get(i));
					String getRoomQuentity = listOfRoomQuentity.get(listOfRoomClassesAbbrevaition.get(i));
					int convertRoomQuentityIntoInteger = Integer.parseInt(getRoomQuentity);
					app_logs.info("Quentity: " + convertRoomQuentityIntoInteger);

					// getting sort order base on room class for verification if
					// each room number
					String getRoomSortOrder = listOfSortId.get(listOfRoomClassesAbbrevaition.get(i));
					int convertRoomSortOrderIntoInteger = Integer.parseInt(getRoomSortOrder);
					app_logs.info("convertRoomSortOrderIntoInteger: " + convertRoomSortOrderIntoInteger);

					for (int j = 0; j < convertRoomQuentityIntoInteger; j++) {
						String getRoomClassAvailability = tapeChart.getRoomClassAvailability(driver,
								listOfRoomClassesAbbrevaition.get(i), "" + convertRoomSortOrderIntoInteger);
						convertRoomSortOrderIntoInteger = convertRoomSortOrderIntoInteger + 1;
						app_logs.info("Availability: " + getRoomClassAvailability);
						System.out.println(listOfDay.containsKey(getDay));
						if (listOfDay.containsKey(getDay)) {
							assertEquals(getRoomClassAvailability, "B",
									"Failed: room number is not blackout on date " + getDate);
						} else {
							assertNotEquals(getRoomClassAvailability, "B",
									"Failed: room number is blackout on date " + getDate);
						}
					}

				}
				testSteps.add("Verified room class is blackout on date " + getDate);
				app_logs.info("Verified room class is blackout on date " + getDate);

			}
		} catch (Exception e) {
			if (Utility.count == RetryFailedTestCases.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to VERIFY CREATED RULE IN TAPECHART", scriptName, "VerifyRULE", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to VERIFY CREATED RULE IN TAPECHART", scriptName, "VerifyRULE", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");

			navigation.navigateToReservations(driver);
			testSteps.add("Navigate to reservation");
			app_logs.info("Navigate to reservation");

			getTestSteps.clear();
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {
				for (int i = 0; i < listOfRoomClasses.size(); i++) {

					app_logs.info(listOfRoomClasses.get(i).trim());
					testSteps.add("========== Verifying room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new reservation creation after making room class black out in bulk update ==========");
					app_logs.info("========== Verifying room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new reservation creation after making room class black out in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, listOfRoomClasses.get(i));
					Assert.assertEquals(size, 0,
							"Failed : " + listOfRoomClasses.get(i) + " is displaying after blackout");
					testSteps.add("Verified room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new reservation creation after making room class black out in bulk update");
					app_logs.info("Verified room class(" + listOfRoomClasses.get(i)
							+ ") is not displaying in new reservation creation after making room class black out in bulk update");
				}
			} else {
				testSteps.add("No room classes are showing in new reservation creation");
				app_logs.info("No room classes are showing in new reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in reservation", scriptName,
						"RoomVisibility", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in reservation", scriptName,
						"RoomVisibility", driver);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW MRB RESERVATION CREATION AFTER MAKING ROOM CLASS BALCK OUT IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW MRB RESERVATION CREATION AFTER MAKING ROOM CLASS BALCK OUT IN BULK UPDATE ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickAddARoom(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDates(driver, startDate, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDates(driver, endDate, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDates(driver, startDate, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDates(driver, endDate, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					app_logs.info(listOfRoomClasses.get(i));
					testSteps.add("========== Verifying room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new MRB  reservation creation after making room class black out in bulk update ==========");
					app_logs.info("========== Verifying room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new MRB  reservation creation after making room class black out in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, listOfRoomClasses.get(i));
					Assert.assertEquals(size, 0,
							"Failed : " + listOfRoomClasses.get(i) + " is displaying after blackout");
					testSteps.add("Verified room class(" + listOfRoomClasses.get(i)
							+ ") is not displaying in new MRB  reservation creation after making room class black out in bulk update");
					app_logs.info("Verified room class(" + listOfRoomClasses.get(i)
							+ ") is not displaying in new MRB reservation creation after black making room class black out in bulk update");
				}
			} else {
				testSteps.add("No room classes are showing in new  MRB reservation creation");
				app_logs.info("No room classes are showing in new  MRB reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new MRB reservation creation",
						scriptName, "MRBReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new MRB reservation creation",
						scriptName, "MRBReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("==========VERIFICATION OF BLACKOUT ROOM CLASSES IN ROUP RESERVATIONS==========");
			app_logs.info("==========VERIFICATION OF BLACKOUT ROOM CLASSES IN ROUP RESERVATIONS==========");
			navigation.secondaryGroupsManu(driver);
			testSteps.add("Navigate Groups");
			app_logs.info(" Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", scriptName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", scriptName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Click New Account and Enter Account Name
		try {
			app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
			testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");
			getTestSteps.clear();
			getTestSteps = group.enterrGroupName(driver, accountName);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on new account button", scriptName, "EnterAccountName",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new account and enter account name", scriptName,
						"EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Number
		try {
			accountNumber = Utility.GenerateRandomString15Digit();
			getTestSteps.clear();
			getTestSteps = group.enterAccountNo(driver, accountNumber);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", scriptName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", scriptName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Attributes
		try {
			getTestSteps.clear();
			getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", scriptName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", scriptName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Mailing Address
		try {
			getTestSteps.clear();
			getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, groupPhn, groupAddress,
					groupCity, groupState, groupCountry, groupPostalcode);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", scriptName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", scriptName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Check Mailing Info CheckBox
		try {
			getTestSteps.clear();
			getTestSteps = group.Billinginfo(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", scriptName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", scriptName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Save Account
		try {
			getTestSteps.clear();
			getTestSteps = group.clickOnSave(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Save  Account", scriptName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Save Account", scriptName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Reservation and check count
		try {
			getTestSteps.clear();
			getTestSteps = group.clickGroupNewReservationButton(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to new reservation creation", scriptName,
						"GroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to new reservation creation", scriptName,
						"GroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW GROUP RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW GROUP RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					app_logs.info(listOfRoomClasses.get(i));
					testSteps.add("========== Verifying room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new group reservation creation after making room class black out in bulk update ==========");
					app_logs.info("========== Verifying room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new group reservation creation afte making room class black out in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, listOfRoomClasses.get(i));
					Assert.assertEquals(size, 0,
							"Failed : " + listOfRoomClasses.get(i) + " is displaying after blackout");
					testSteps.add("Verified room class(" + listOfRoomClasses.get(i)
							+ ") is not  displaying in new group reservation creation after making room class black out in bulk update");
					app_logs.info("Verified room class(" + listOfRoomClasses.get(i)
							+ ") is not displaying in new group reservation creation after making room class black out in bulk update");
				}
			} else {
				testSteps.add("No room classes are showing in new group reservation creation");
				app_logs.info("No room classes are showing in new group reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new group reservation",
						scriptName, "RoomVisibilityForGroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new group reservation",
						scriptName, "RoomVisibilityForGroupReservation", driver);
			}
		}

		try {

			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		// New account
		try {
			testSteps.add("========== Creating corprate account ==========");
			app_logs.info("========== Creating corprate account ==========");
			corpAccountFullName = corpAccountFullName + Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, corpAccountType);
			CreateTA.AccountDetails(driver, corpAccountType, corpAccountFullName);
			corpAccountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, corpAccountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, groupReferral, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Mailinginfo(driver, test, corpFirstName, corpLastName, groupPhn, groupPhn,
					groupAddress, "", "", email, groupCity, groupState, groupPostalcode, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountSave(driver, test, corpAccountFullName, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {

			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create corporate account", scriptName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create corporate account", scriptName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Clicking on New Reservation
		try {
			CreateTA.NewReservationButton(driver, test);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW CORP ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW CORP ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					String roomClasName = listOfRoomClasses.get(i).trim();
					app_logs.info(roomClasName.trim());
					testSteps.add("========== Verifying room class(" + roomClasName
							+ ") is not  displaying in new corp account reservation creation after making room class black out in bulk update ==========");
					app_logs.info("========== Verifying room class(" + roomClasName
							+ ") is not  displaying in new corp account reservation creation after making room class black out in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, roomClasName);
					Assert.assertEquals(size, 0, "Failed : " + roomClasName + " is displaying after blackout");
					testSteps.add("Verified room class(" + roomClasName
							+ ") is not  displaying in new corp account  reservation creation after making room class black out in bulk update");
					app_logs.info("Verified room class(" + roomClasName
							+ ") is not displaying in new corp account reservation creation after making room class black out in bulk update");
				}
			} else {
				testSteps.add("No room classes are showing in new resrevation page");
				app_logs.info("No room classes are showing in new resrevation page");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new corp reservation",
						scriptName, "RoomVisibilityInCorpRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new corp reservation",
						scriptName, "RoomVisibilityInCorpRes", driver);
			}
		}
		try {

			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");
			
			CreateTA.close_Account(driver);
			testSteps.add("Closed opened accounts");
			app_logs.info("Closed opened accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("========== Creating travel agent account ==========");
			app_logs.info("========== Creating travel agent account ==========");
			travelAccountFullName = travelAccountFullName + Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, travelAccType);
			CreateTA.AccountDetails(driver, travelAccType, travelAccountFullName);
			corpAccountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, corpAccountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, groupReferral, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Mailinginfo(driver, test, travelFirstName, corpLastName, groupPhn, groupPhn,
					groupAddress, "", "", email, groupCity, groupState, groupPostalcode, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountSave(driver, test, travelAccountFullName, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {

			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create travel agent account", scriptName, "TravelAgentAccount",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create travel agent account", scriptName, "TravelAgentAccount",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Clicking on New Reservation
		try {
			CreateTA.NewReservationButton(driver, test);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS BLACK OUT IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS BLACK OUT IN BULK UPDATE ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					String roomClasName = listOfRoomClasses.get(i);
					app_logs.info(roomClasName.trim());
					testSteps.add("========== Verifying room class(" + roomClasName
							+ ") is not  displaying in new travel agent account reservation creation after making room class black out in bulk update ==========");
					app_logs.info("========== Verifying room class(" + roomClasName
							+ ") is not  displaying in new travel agent account reservation creation after making room class black out in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, roomClasName);
					Assert.assertEquals(size, 0, "Failed : " + roomClasName + " is displaying after blackout");
					testSteps.add("Verified room class(" + roomClasName
							+ ") is not  displaying in new travel agent account  reservation creation after making room class black out in bulk update");
					app_logs.info("Verified room class(" + roomClasName
							+ ") is not displaying in new travel agent account reservation creation after making room class black out in bulk update");
				}
			} else {
				testSteps.add("No room classes are showing in new  reservation creation page");
				app_logs.info("No room classes are showing in new  reservation creation page");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new travel agent reservation",
						scriptName, "RoomVisibilityInTravelRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new travel agent reservation",
						scriptName, "RoomVisibilityInTravelRes", driver);
			}
		}
		try {
			testSteps.add("========== MAKING ROOM CLASSES AVAILABLE IN BULK UPDATE ==========");
			app_logs.info("========== MAKING ROOM CLASSES AVAILABLE IN BULK UPDATE ==========");

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
				Utility.updateReport(e, "Failed to navigate to availability popup", scriptName, "NavigateAvailability",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to availability popup", scriptName, "NavigateAvailability",
						driver);
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
				Utility.updateReport(e, "Failed to select start", scriptName, "EnterStartDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select start", scriptName, "EnterStartDate", driver);
			}
		}

		try {
			testSteps.add("==========SELECT END DATE==========");
			app_logs.info("==========SELECT END DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.endDate(driver, endDate);
			testSteps.addAll(getTestSteps);
			ratesGrid.verifyAvailabilityHeading(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select end date", scriptName, "EnterEndDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select end date", scriptName, "EnterEndDate", driver);
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
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "CheckDays", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "CheckDays", driver);
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
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "OccupancyToggle", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "OccupancyToggle", driver);
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
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "OccupancyType", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "OccupancyType", driver);
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
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "OccupancyValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "OccupancyValue", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClassName);
				testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", scriptName, "SelectRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select room classes", scriptName, "SelectRoomClass", driver);
			}
		}

		try {
			app_logs.info("==========SELECTING UPDATE AVAILABILITY==========");
			testSteps.add("==========SELECTING UPDATE AVAILABILITY==========");
			String[] channelArr = channel.split(",");
			for (String channelString : channelArr) {

				channelString = channelString.trim();
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectUpdateAvailability(driver, channelString.trim(), available);
				testSteps.addAll(getTestSteps);
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select update availability", scriptName, "UpdateAvailability",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select update availability", scriptName, "UpdateAvailability",
						driver);
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

			days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
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

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS AVAILBLE IN NEW RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS AVAILABLE IN NEW RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");

			navigation.Reservation_Backward_3(driver);
			testSteps.add("Navigate to reservation");
			app_logs.info("Navigate to reservation");

			getTestSteps.clear();
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					String roomClasName = listOfRoomClasses.get(i);
					app_logs.info(roomClasName.trim());
					testSteps.add("========== Verifying room class(" + roomClasName
							+ ") is displaying in new reservation creation after making room class available in bulk update ==========");
					app_logs.info("========== Verifying room class(" + roomClasName
							+ ") is displaying in new reservation creation after making room class available in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, roomClasName);

					Assert.assertTrue(size>0,
							"Failed : " + roomClasName + " is not displaying after making it available");
					testSteps.add("Verified room class(" + roomClasName
							+ ") is displaying in new reservation creation after making room class available in bulk update");
					app_logs.info("Verified room class(" + roomClasName
							+ ") is displaying in new reservation creation after making room class available in bulk update");

				}
			} else {
				testSteps.add("No room classes are showing in new reservation creation");
				app_logs.info("No room classes are showing in new reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibility", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibility", driver);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS AVAILBLE IN NEW MRB RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS AVAILABLE  IN NEW MRB RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");

			getTestSteps.clear();
			reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickAddARoom(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDates(driver, startDate, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDates(driver, endDate, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDates(driver, startDate, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDates(driver, endDate, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					String roomClasName = listOfRoomClasses.get(i);
					app_logs.info(roomClasName.trim());
					testSteps.add("========== Verifying room class(" + roomClasName
							+ ") is displaying in new MRB reservation creation after making room class available in bulk update ==========");
					app_logs.info("========== Verifying room class(" + roomClasName
							+ ") is displaying in new MRB reservation creation after making room class available in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, roomClasName);

					Assert.assertTrue(size>0,
							"Failed : " + roomClasName + " is not displaying after making it available");
					testSteps.add("Verified room class(" + roomClasName
							+ ") is displaying in new MRB reservation creation after making room class available in bulk update");
					app_logs.info("Verified room class(" + roomClasName
							+ ") is displaying in new MRB reservation creation after making room class available in bulk update");
				}
			} else {
				testSteps.add("No room classes are showing in new MRB reservation creation");
				app_logs.info("No room classes are showing in new MRB reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new MRB reservation creation",
						scriptName, "MRBReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is not displaying in new MRB reservation creation",
						scriptName, "MRBReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			navigation.secondaryGroupsManu(driver);
			app_logs.info("Navigate Groups");
			testSteps.add("Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", scriptName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", scriptName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========SEARCH CREATED GROUP ACCOUNT==========");
			testSteps.add("==========SEARCH CREATED GROUP ACCOUNT==========");
			getTestSteps.clear();
			getTestSteps = group.searchGroupAccount(driver, accountName, accountNumber, true, true);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", scriptName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", scriptName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			getTestSteps.clear();
			getTestSteps = group.clickGroupNewReservationButton(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to new reservation creation", scriptName,
						"GroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to new reservation creation", scriptName,
						"GroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS AVAILBLE IN NEW GROUP RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS AVAILABLE IN NEW GROUP RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					String roomClasName = listOfRoomClasses.get(i);
					app_logs.info(roomClasName.trim());
					testSteps.add("========== Verifying room class(" + roomClasName
							+ ") is displaying in new group reservation creation after making room class available in bulk update ==========");
					app_logs.info("========== Verifying room class(" + roomClasName
							+ ") is displaying in new group reservation creation after making room class available in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, roomClasName);

					Assert.assertTrue(size>0,
							"Failed : " + roomClasName + " is not displaying after making it available");
					testSteps.add("Verified room class(" + roomClasName
							+ ") is displaying in new group reservation creation after making room class available in bulk update");
					app_logs.info("Verified room class(" + roomClasName
							+ ") is displaying in new group reservation creation after making room class available in bulk update");

				}
			} else {
				testSteps.add("No room classes are showing in new reservation creation");
				app_logs.info("No room classes are showing in new reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in group reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibilityinGroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in group reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibilityinGroupReservation", driver);
			}
		}

		try {

			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

			CreateTA.close_Account(driver);
			testSteps.add("Closed opened accounts");
			app_logs.info("Closed opened accounts");
			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("Search and open Corporate/Member Accounts account");
			app_logs.info("Search and open Corporate/Member Accounts account");
			CreateTA.account_Search(driver, testSteps, corpAccountFullName);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search corporate Accounts", scriptName, "SearchAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search corporate Accounts", scriptName, "SearchAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			CreateTA.NewReservationButton(driver, test);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS AVAILBLE IN NEW CORPORATE ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS AVAILABLE IN NEW CORPORATE ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {

					String roomClasName = listOfRoomClasses.get(i);
					app_logs.info(roomClasName.trim());
					testSteps.add("========== Verifying room class(" + roomClasName
							+ ") is displaying in new corporate account reservation creation after making room class available in bulk update ==========");
					app_logs.info("========== Verifying room class(" + roomClasName
							+ ") is displaying in new corporate account reservation creation after making room class available in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, roomClasName);

					Assert.assertTrue(size>0,
							"Failed : " + roomClasName + " is not displaying after making it available");
					testSteps.add("Verified room class(" + roomClasName
							+ ") is displaying in new corporate account reservation creation after making room class available in bulk update");
					app_logs.info("Verified room class(" + roomClasName
							+ ") is displaying in new corporate account reservation creation after making room class available in bulk update");

				}
			} else {

				testSteps.add("No room classes are showing in new corporate account reservation creation");
				app_logs.info("No room classes are showing in new corporate account reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in corporate account  reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibilityinCorpAccountReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in corporate account reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibilityinCorpAccountReservation", driver);
			}
		}

		try {
			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

			CreateTA.close_Account(driver);
			testSteps.add("Closed opened account");
			app_logs.info("Closed opened account");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to close account", scriptName, "CloseAccount", driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to close account", scriptName, "CloseAccount", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("Search and open travel agent account");
			app_logs.info("Search and open travel agent account");

			getTestSteps.clear();
			getTestSteps = CreateTA.accountType(driver, travelAccType);
			testSteps.addAll(getTestSteps);

			CreateTA.account_Search(driver, testSteps, travelAccountFullName);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search travel agent Accounts", scriptName, "SearchAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to search travel agent Accounts", scriptName, "SearchAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			CreateTA.NewReservationButton(driver, test);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			testSteps.add(
					"========== VERIFYING ROOM CLASS AVAILBLE IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");
			app_logs.info(
					"========== VERIFYING ROOM CLASS AVAILABLE IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION AFTER MAKING ROOM CLASS AVAILABLE IN BULK UPDATE ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			int sectionSize = reservationPage.verifyRoomClassSectionVisibility(driver);
			if (sectionSize > 0) {

				for (int i = 0; i < listOfRoomClasses.size(); i++) {
					String roomClasName = listOfRoomClasses.get(i);
					app_logs.info(roomClasName.trim());
					testSteps.add("========== Verifying room class(" + roomClasName
							+ ") is displaying in new travel agent account reservation creation after making room class available in bulk update ==========");
					app_logs.info("========== Verifying room class(" + roomClasName
							+ ") is displaying in new travel agent account reservation creation after making room class available in bulk update ==========");

					int size = reservationPage.verifyRoomClassVisibility(driver, roomClasName);

					Assert.assertTrue(size>0,
							"Failed : " + roomClasName + " is not displaying after making it available");
					testSteps.add("Verified room class(" + roomClasName
							+ ") is displaying in new travel agent account reservation creation after making room class available in bulk update");
					app_logs.info("Verified room class(" + roomClasName
							+ ") is displaying in new travel agent account reservation creation after making room class available in bulk update");

				}
			} else {

				testSteps.add("No room classes are showing in new travel agent account reservation creation");
				app_logs.info("No room classes are showing in new travel agent account reservation creation");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in travel agent account  reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibilityinTravelAccountReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify room class is displaying in travel agent account  reservation after makig room classes available in bulk update",
						scriptName, "RoomVisibilityinTravelAccountReservation", driver);
			}
		}

		try {
			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

			CreateTA.close_Account(driver);
			testSteps.add("Closed opened accounts");
			app_logs.info("Closed opened accounts");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to close account", scriptName, "CloseAccount", driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to close account", scriptName, "CloseAccount", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps into report", scriptName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps into report", scriptName, "AddStepsIntoReport", driver);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAvailabilityInReservation", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver() {
		// driver.quit();
	}

}
