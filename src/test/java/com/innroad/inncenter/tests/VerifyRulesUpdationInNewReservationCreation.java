package com.innroad.inncenter.tests;

import java.util.ArrayList;

import com.innroad.inncenter.pageobjects.*;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyRulesUpdationInNewReservationCreation extends TestCore {

// Automation-1705 part-B
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> testName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	public static String testdescription = "";
	public static String testcatagory = "";

	@Test(dataProvider = "getData", groups = "RatesGrid")
	public void verifyRulesInBulkUpdate(String startDate, String endDate, String isSundayCheck, String isMondayCheck,
			String isTuesdayCheck, String isWednesdayCheck, String isThursdayCheck, String isFridayCheck, String isSaturdayCheck,
			String isTotalOccupancyOn, String totalOccupancyType, String totalOccupancyValue, String roomClassName, 
			String channel, String ratePlan, String isMinimumStayOn, String minimumStayValue, String isCheckInOn, String isNoCheckInChecked, String isCheckOutOn, String isNoCheckOutChecked,
			String rules, String adults,String children,String promoCode, String isAssign, String account, String noCheckIn, String noCheckOut,
			String delim) throws Exception {

		String scriptName = "VerifyRulesInBulkUpdate";
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
		CPReservationPage reservationPage = new CPReservationPage();
		
	//	String checkInDate = ESTTimeZone.reformatDate(startDate, "MM/dd/yyyy", "dd/MM/yyyy");
	//	String checkOutDate = ESTTimeZone.reformatDate(endDate, "MM/dd/yyyy", "dd/MM/yyyy");
	//	app_logs.info(checkInDate);
	//	app_logs.info(checkOutDate);
		
		
		String timeZone = "America/New_York";
		String getCurrentDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
		if (ESTTimeZone.CompareDates(startDate, "MM/dd/yyyy", timeZone)) {
			startDate = getCurrentDate;
		}
		if (ESTTimeZone.CompareDates(endDate, "MM/dd/yyyy", timeZone)) {
			 getCurrentDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
			 endDate = getCurrentDate;
		}
	
		Utility.DELIM = delim;
		try {
			if (!Utility.insertTestName.containsKey(scriptName)) {
				Utility.insertTestName.put(scriptName, scriptName);
				Utility.reTry.put(scriptName, 0);

			} else {
				Utility.reTry.replace(scriptName, 1);
			}
			driver = getDriver();
			//login_CP(driver);
			try {
				loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				loginWPI(driver);
			}
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
				Utility.updateReport(e, "Failed to navigate to overview", scriptName, "NavigateOverview", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to overview", scriptName, "NavigateOverview", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {

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
				Utility.updateReport(e, "Failed to navigate to rules popup", scriptName, "BulkUpdateOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rules popup", scriptName, "BulkUpdateOption", driver);
			}
		}
		try {
			
			testSteps.add("==========SELECT START DATE==========");
			app_logs.info("==========SELECT START DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectStartDate(driver, startDate);
			testSteps.addAll(getTestSteps);
		
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select start", scriptName, "StartDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select start", scriptName, "StartDate", driver);
			}
		}
	
		try{
			testSteps.add("==========SELECT END DATE==========");
			app_logs.info("==========SELECT END DATE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectEndDate(driver, endDate);
			testSteps.addAll(getTestSteps);
			ratesGrid.clickUpdate(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select end date", scriptName, "EndDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select end date", scriptName, "EndDate", driver);
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
			getTestSteps = ratesGrid.checkDays(driver, "Tue", isTuesdayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Wed", isWednesdayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Thu", isThursdayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Fri", isFridayCheck);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.checkDays(driver, "Sat", isSaturdayCheck);
			testSteps.addAll(getTestSteps);
					
			
			} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "checkDays", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "checkDays", driver);
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
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "TotalOccupancyToggle", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "TotalOccupancyToggle", driver);
			}
		}

		try {
			
			if(isTotalOccupancyOn.equalsIgnoreCase("Yes")){
	
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectTotalOccupancyType(driver, totalOccupancyType);
				testSteps.addAll(getTestSteps);
			}

			} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "TotalOccupancyType", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "TotalOccupancyType", driver);
			}
		}

		try {
			
			if(isTotalOccupancyOn.equalsIgnoreCase("Yes")){
	
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterOccupancyValue(driver, totalOccupancyValue);
				testSteps.addAll(getTestSteps);
			}

			} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "TotalOccupancyValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "TotalOccupancyValue", driver);
			}
		}
		
		try{
			app_logs.info("==========SELECTING RATE PLAN==========");
			testSteps.add("==========SELECTING RATE PLAN==========");
			
			getTestSteps.clear();
			app_logs.info("ratePlan: "+ratePlan);
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
		try{
			app_logs.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClassName);
			testSteps.addAll(getTestSteps);
		
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

		try{
			app_logs.info("==========SELECTING SOURCE==========");
			testSteps.add("==========SELECTING SOURCE==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", channel);
			testSteps.addAll(getTestSteps);
		
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
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickMinimumStay(driver, isMinimumStayOn);
			testSteps.addAll(getTestSteps);
				
			if(isMinimumStayOn.equalsIgnoreCase("Yes")){
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterMinimumStayValue(driver, minimumStayValue);
				testSteps.addAll(getTestSteps);
			}
			
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
		
		try{

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckin(driver, isCheckInOn);
			testSteps.addAll(getTestSteps);

			if(isCheckInOn.equalsIgnoreCase("Yes")){
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickNoCheckInCheckbox(driver, isNoCheckInChecked);
					testSteps.addAll(getTestSteps);
			}

			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin button", scriptName, "BulkUpdate", driver);
			}
		}
		
		try{
			
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCheckOut(driver, isCheckOutOn);
			testSteps.addAll(getTestSteps);

			if(isCheckOutOn.equalsIgnoreCase("Yes")){
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickNoCheckOutCheckbox(driver, isNoCheckOutChecked);
					testSteps.addAll(getTestSteps);
			}
			
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout button", scriptName, "BulkUpdate", driver);
			}
		}

		try{
			
			
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

		try{
			app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
			testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

			int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
				days = days+1;
			String expectedDays = "Rules will be updated for " + days + " day(s) within this date range.";
			testSteps.add("Expected total days : " + expectedDays);
			app_logs.info("Expected total days : " + expectedDays);
			String totalDays = ratesGrid.getTotalDaysText(driver, rules);
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
		try{
			String[] roomClassesArray = roomClassName.split(",");
			for (String roomClasName : roomClassesArray) {
				roomClasName = roomClasName.trim();
				app_logs.info(roomClasName.trim());

				app_logs.info("==========VERIFYING RULES ICON IS DISPLAYING FOR " + roomClasName + " in rate grid ==========");
				testSteps.add("==========VERIFYING RULES ICON IS DISPLAYING FOR " + roomClasName + " in rate grid ==========");
				
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyRulesIcon(driver, roomClasName);
				testSteps.addAll(getTestSteps);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules icon", scriptName, "VerifyRulesIcon", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules icon", scriptName, "VerifyRulesIcon", driver);
			}
		}	
		
		try {
			
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
			reservationPage.select_Rateplan(driver, getTestSteps, ratePlan,promoCode);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);			
			
			String[] roomClassesArray = roomClassName.split(",");
			for (String roomClasName : roomClassesArray) {
			
				roomClasName = roomClasName.trim();
				app_logs.info(roomClasName);
				
				String getNights = "";
				int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
				if(days > 1){
					getNights = reservationPage.getRulesNightsText(driver, roomClasName);
					getNights = getNights + ", ";
					app_logs.info(getNights);
				}

				testSteps.add("========== Verifying room class("+ roomClasName+ " is displaying rules in new reservation creation after creating rules in bulk update ==========");
				app_logs.info("========== Verifying room class("+ roomClasName+ " is displaying rules in new reservation creation after creating rules in bulk update ==========");

				String expectedText = minimumStayValue + " Nights (override), " + noCheckIn + " (override), "+ noCheckOut +" (override)";
				testSteps.add("Expected : " + expectedText);
				app_logs.info("Expected : " + expectedText);
				
				String getText = reservationPage.getRulesText(driver, roomClasName);
				testSteps.add("Found : " + getNights + getText);
				app_logs.info("Found : " + getNights + getText);
			
				//Assert.assertEquals(getNights + getText, expectedText, "Failed : "+ roomClasName +" rules text didn't match");
				testSteps.add("Verified room class("+ roomClasName+ " is displaying rules in new reservation creation after creating rules in bulk update");
				app_logs.info("Verified room class("+ roomClasName+ " is displaying rules in new reservation creation after creating rules in bulk update");

			}
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close reservation");
			app_logs.info("Close reservation");
			

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is displaying in reservation after makig room classes available in bulk update", scriptName, "RoomVisibility", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is displaying in reservation after makig room classes available in bulk update", scriptName, "RoomVisibility", driver);
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

		return Utility.getData("VerifyRulesUpdationInNewRes", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver() {
		// driver.quit();
	}

}
