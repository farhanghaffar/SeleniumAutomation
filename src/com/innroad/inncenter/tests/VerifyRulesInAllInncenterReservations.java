package com.innroad.inncenter.tests;

import java.util.ArrayList;

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
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyRulesInAllInncenterReservations extends TestCore {

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
	public void verifyRulesInAllInncenterReservations(String startDate, String endDate, String isSundayCheck, String isMondayCheck,
			String isTuesdayCheck, String isWednesdayCheck, String isThursdayCheck, String isFridayCheck, String isSaturdayCheck,
			String isTotalOccupancyOn, String totalOccupancyType, String totalOccupancyValue, String roomClassName, String roomClassAbb,
			String channel, String ratePlan, String isMinimumStayOn, String minimumStayValue, String isCheckInOn, String isNoCheckInChecked, String isCheckOutOn, String isNoCheckOutChecked,
			String rules, String noCheckInText, String noCheckOutText, String adults, String children, String promoCode,
			String isAssign, String account,String marketSegment, String referral,
			String groupFirstName, String groupLastName, String groupPhn, String groupAddress, String groupCity,
			String groupCountry, String groupState, String groupPostalcode, String corpFirstName, String corpLastName,
			String corpAccountType, String email, String travelAccType, String travelFirstName,
			String salutation, String guestFirstName, String guestLastName, String paymentType,
			String cardNumber, String nameOnCard, String cardExpDate, String isSplitRes, String isAddMoreGuestInfo, String isGuestProfile,
			String isNewRes, String isMRBRes, String isGroupRes, String isCorporateRes, String isTravelAgentRes, String isTapeChartRes,
			String delim
			) throws Exception {

		String scriptName = "VerifyRulesInAllInncenterReservations";
		String testdescription = "Rates V2 - Bulk Update (Rules) - All Inncenter reservations<br>";
				

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
		ReservationSearch reservationSearch = new ReservationSearch();
		
		String accountNumber = null;
		String corpAccountNumber = null;
		String randomNumber = Utility.GenerateRandomNumber();
		groupLastName = groupLastName + randomNumber;
		String accountName = groupFirstName + groupLastName;
		corpLastName = corpLastName + randomNumber;
		String corpAccountFullName = corpFirstName + corpLastName;
		String travelAccountFullName = travelFirstName + corpLastName;
		guestFirstName = guestFirstName + randomNumber;
		guestLastName = guestLastName + randomNumber;
		
		String reservationNumber = null;
		ArrayList<String> roomClassList = new ArrayList<>();
		ArrayList<String> ratePlanList = new ArrayList<>();
		ArrayList<String> roomClassAbbreviationList = new ArrayList<>();
		
		int miminumStayInInt = Integer.parseInt(minimumStayValue);
		
		String timeZone = "America/New_York"; 
		String getCurrentDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
		if (ESTTimeZone.CompareDates(startDate, "MM/dd/yyyy", timeZone)) {
			startDate = getCurrentDate;
		}
		if (ESTTimeZone.CompareDates(endDate, "MM/dd/yyyy", timeZone)) {
			 getCurrentDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
			 endDate = getCurrentDate;
		}
		String checkInDateGreaterThanEndDate = Utility.getNextDate(2, "MM/dd/yyyy", timeZone);
		String checkOutDateGreaterThanEndDate =	"";

		if(isMinimumStayOn.equalsIgnoreCase("Yes")){
			checkOutDateGreaterThanEndDate = Utility.getNextDate(miminumStayInInt + 1, "MM/dd/yyyy", timeZone);
		}else{
			checkOutDateGreaterThanEndDate = Utility.getNextDate(3, "MM/dd/yyyy", timeZone);
				
		}
		int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);

		String mrbAdults = adults + "|" + adults;
		String mrbChildren = children + "|" + children;
		String mrbPromoCode = promoCode + "|" + promoCode;
		String mrbCheckInDate = startDate + "|" + startDate;
		String mrbCheckOutDate = endDate + "|" + endDate;
		String mrbCheckInDateGreaterThanEndDate = checkInDateGreaterThanEndDate + "|" + checkInDateGreaterThanEndDate;
		String mrbCheckOutDateGreaterThanEndDate = checkOutDateGreaterThanEndDate + "|" + checkOutDateGreaterThanEndDate;
	
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "CP_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to overview", scriptName, "NavigateOverview", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rules popup", scriptName, "BulkUpdateOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select start", scriptName, "StartDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,"Failed to select end date", scriptName, "EndDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
				e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to check/uncheck days", scriptName, "checkDays", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
				e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click total occupancy", scriptName, "TotalOccupancyToggle", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
				e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select total occupancy type", scriptName, "TotalOccupancyType", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
				e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "TotalOccupancyValue", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to enter total occupancy value", scriptName, "TotalOccupancyValue", driver);
			}
		}
		
		try{
			app_logs.info("==========SELECTING RATE PLAN==========");
			testSteps.add("==========SELECTING RATE PLAN==========");
			
			String[] ratePlanArray = ratePlan.split(Utility.DELIM);
			for (String ratePlanName : ratePlanArray) {

				ratePlanName = ratePlanName.trim();
				app_logs.info(ratePlanName);
				ratePlanList.add(ratePlanName);

			}

			for (int i=0; i < ratePlanList.size(); i++) {
				String ratePlanName = ratePlanList.get(i);
				app_logs.info("ratePlan: "+ratePlanName);
			}
			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", ratePlan);
			testSteps.addAll(getTestSteps);			

		} catch (Exception e) {
			e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select rate plan", scriptName, "BulkUpdate", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select rate plan", scriptName, "BulkUpdate", driver);
		}
	}	
		try{
			app_logs.info("==========SELECTING ROOM CLASS==========");
			testSteps.add("==========SELECTING ROOM CLASS==========");
			String[] roomClassesArray = roomClassName.split(",");
			for (String roomClasName : roomClassesArray) {

				roomClasName = roomClasName.trim();
				app_logs.info(roomClasName);
				roomClassList.add(roomClasName);

			}

			for (int i=0; i < roomClassList.size(); i++) {
				String roomClasName = roomClassList.get(i);
				app_logs.info(roomClasName);
			}
			getTestSteps.clear();
			getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClassName);
			testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select room classes", scriptName, "BulkUpdate", driver);
		} else {
			Assert.assertTrue(false);
		}
		} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
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
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			Utility.updateReport(e, "Failed to select source", scriptName, "BulkUpdate", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click minimum stay", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkin button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click checkout button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click update button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click update button", scriptName, "BulkUpdate", driver);
			}
		}

		try{
			
			app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
			testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

			int numberOfDays= days+1;
			String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
			testSteps.add("Expected total days : " + expectedDays);
			app_logs.info("Expected total days : " + expectedDays);
			String totalDays = ratesGrid.getTotalDaysText(driver, rules);
			testSteps.add("Found : " + totalDays);
			app_logs.info("Found : " + totalDays);
			Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
			testSteps.add("Verified total number of days");
			app_logs.info("Verified total number of days");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify total number of days", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click yes update button", scriptName, "BulkUpdate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click yes update button", scriptName, "BulkUpdate", driver);
			}
		}
		
		String expectedText = "";
		
		try {
			navigation.Reservation_Backward_3(driver);
			testSteps.add("Navigate to reservation");
			app_logs.info("Navigate to reservation");


			if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("Yes")
					&& isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
				String minimumStay = minimumStayValue + " Nights (override), ";
				String noCheckIn = "No Check In (override), ";
				String noCheckOut = "No Check out (override)";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			} else if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("No")
					&& isNoCheckOutChecked.equalsIgnoreCase("No")) {
				String minimumStay = minimumStayValue + " Nights (override)";
				String noCheckIn = "";
				String noCheckOut = "";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			} else if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("Yes")
					&& isNoCheckOutChecked.equalsIgnoreCase("No")) {
				String minimumStay = minimumStayValue + " Nights (override), ";
				String noCheckIn = "No Check In (override), ";
				String noCheckOut = "";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			} else if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("No")
					&& isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
				String minimumStay = minimumStayValue + " Nights (override), ";
				String noCheckIn = "";
				String noCheckOut = "No Check out (override)";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("Yes")
					&& isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
				String minimumStay = "";
				String noCheckIn = "No Check In (override), ";
				String noCheckOut = "No Check out (override)";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("Yes")
					&& isNoCheckOutChecked.equalsIgnoreCase("No")) {
				String minimumStay = "";
				String noCheckIn = "No Check In (override)";
				String noCheckOut = "";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("No")
					&& isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
				String minimumStay = "";
				String noCheckIn = "";
				String noCheckOut = "No Check out (override)";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("No")
					&& isNoCheckOutChecked.equalsIgnoreCase("No")) {
				String minimumStay = "";
				String noCheckIn = "";
				String noCheckOut = "";
				expectedText = minimumStay + noCheckIn + noCheckOut;

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to reservation", scriptName, "NavigateReservation", driver);
			}
		}
		if(isNewRes.equalsIgnoreCase("Yes")){

		try{

				
				
				testSteps.add("========== VERIFYING RULES ARE BROKEN IN NEW RESERVATION CREATION ==========");
				app_logs.info("========== VERIFYING RULES ARE BROKEN IN NEW RESERVATION CREATION ==========");
	
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
	
				for (int j = 0; j < ratePlanList.size(); j++) {
					String ratePlanName = ratePlanList.get(j);
					app_logs.info("ratePlan: " + ratePlanName);
	
					getTestSteps.clear();
					reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					reservationPage.clickOnFindRooms(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
					for (int i = 0; i < roomClassList.size(); i++) {
	
						String roomClasName = roomClassList.get(i);
						app_logs.info(roomClasName);
	
						testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new reservation creation ==========");
						app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new reservation creation ==========");
						testSteps.add("Expected : " + expectedText);
						app_logs.info("Expected : " + expectedText);
	
						String getText = reservationPage.getRulesText(driver, roomClasName);
						testSteps.add("Found : " + getText);
						app_logs.info("Found : " + getText);
	
						Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
						testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new reservation creation");
						app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new reservation creation");
	
						testSteps.add("========== Verifying broken rules in broken rule popup ==========");
						app_logs.info("========== Verifying broken rules in broken rule popup ==========");
	
						getTestSteps.clear();
						getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
						testSteps.addAll(getTestSteps);
	
						getTestSteps.clear();
						getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
								isNoCheckInChecked, isNoCheckOutChecked);
						testSteps.addAll(getTestSteps);
	
						getTestSteps.clear();
						getTestSteps = reservationPage.clickRulesPopup(driver, "No");
						testSteps.addAll(getTestSteps);
	
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is displaying rules popup in reservation", scriptName, "BrokenRulesVerificationInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is displaying rules popup in reservation", scriptName, "BrokenRulesVerificationInNewRes", driver);
			}
		}
		
		try {
				
				testSteps.add("========== VERIFYING RULES ARE SATISFYING IN NEW RESERVATION CREATION ==========");
				app_logs.info("========== VERIFYING RULES ARE SATISFYING IN NEW RESERVATION CREATION ==========");
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkInDate(driver, checkInDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver, checkOutDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.enter_Adults(driver, getTestSteps, adults);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.enter_Children(driver, getTestSteps, children);
				testSteps.addAll(getTestSteps);
	
				for (int j = 0; j < ratePlanList.size(); j++) {
					String ratePlanName = ratePlanList.get(j);
					app_logs.info("ratePlan: " + ratePlanName);
	
					getTestSteps.clear();
					reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					reservationPage.clickOnFindRooms(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
	
					for (int i = 0; i < roomClassList.size(); i++) {
	
						String roomClasName = roomClassList.get(i);
						app_logs.info(roomClasName);
	
						int size = reservationPage.getRulesSize(driver, roomClasName);
						Assert.assertEquals(0, size, "Failed : " + roomClasName + " rules text display");
						testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is  not displaying rules in new reservation creation");
						app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is not displaying rules in reservation creation");
	
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new reservation creation", scriptName, "RulesSatisfyVerificationInNewCorpAccoutRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in  new reservation creation", scriptName, "RulesSatisfyVerificationInNewCorpAccoutRes",  driver);
			}
		}
	
		try {
			
				testSteps.add("========== CREATING NEW RESERVATION ==========");
				app_logs.info("========== CREATING NEW RESERVATION ==========");
	
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
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanList.get(0), promoCode);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				String roomClasName = roomClassList.get(0);
				app_logs.info(roomClasName);
	
				testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new reservation creation ==========");
				app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new reservation creation ==========");
	
				testSteps.add("Expected : " + expectedText);
				app_logs.info("Expected : " + expectedText);
	
				String getText = reservationPage.getRulesText(driver, roomClasName);
				testSteps.add("Found : " + getText);
				app_logs.info("Found : " + getText);
	
				Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
				testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new reservation creation");
				app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new reservation creation");
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
						isNoCheckInChecked, isNoCheckOutChecked);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickNext(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterEmail(driver, getTestSteps, email);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectReferral(driver, referral);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber,
						nameOnCard, cardExpDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.get_ReservationStatus(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickCloseReservation(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			}
		}		
		
		try {
			
				app_logs.info("========= EXTEND RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
				testSteps.add("========= EXTEND RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickEditReservation(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickChangeStayDetails(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckInDate(driver, checkInDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckOutDate(driver, checkOutDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
				testSteps.addAll(getTestSteps);
	
				int size = reservationPage.brokenRulesPopupSize(driver);
				Assert.assertEquals(0, size, "Failed : " + roomClassList.get(0) + " rules popup displaying");
				testSteps.add("Verified rules popup is not displaying");
				app_logs.info("Verified rules popup is not displaying");
	
				// add select method to do
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
				testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
				app_logs.info("========= REDUCE RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
				testSteps.add("=========  REDUCE RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickEditReservation(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickChangeStayDetails(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckInDate(driver, startDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckOutDate(driver, endDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
						isNoCheckInChecked, isNoCheckOutChecked);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
				testSteps.addAll(getTestSteps);
	
				// add select method to do
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
				testSteps.addAll(getTestSteps);
	
				Wait.wait2Second();
				reservationPage.closeReservationTab(driver);
				testSteps.add("Close Reservation");
				app_logs.info("Close Reservation");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
		if(isMRBRes.equalsIgnoreCase("Yes")){
			
		try {
	
				testSteps.add("========== VERIFYING RULES ARE BROKEN IN NEW MRB RESERVATION CREATION ==========");
				app_logs.info("========== VERIFYING RULES ARE BROKEN IN NEW MRB RESERVATION CREATION ==========");
	
				for (int j = 0; j < ratePlanList.size(); j++) {
					String ratePlanName = ratePlanList.get(j);
					app_logs.info("ratePlan: " + ratePlanName);
					String mrbRatePlan = ratePlanName + "|" + ratePlanName;
	
					getTestSteps.clear();
					reservationPage.click_NewReservation(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = reservationPage.enterMRBDates(driver, mrbCheckInDate, mrbCheckOutDate, mrbAdults,
							mrbChildren, mrbRatePlan, mrbPromoCode, "No");
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					reservationPage.clickOnFindRooms(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
	
					for (int i = 0; i < roomClassList.size(); i++) {
	
						String roomClasName = roomClassList.get(i);
						app_logs.info(roomClasName);
	
						String tempRoomClassName = roomClasName + "|" + roomClasName;
	
						testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new mrb reservation creation ==========");
						app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new mrb reservation creation ==========");
	
						testSteps.add("Expected : " + expectedText);
						app_logs.info("Expected : " + expectedText);
	
						String getText = reservationPage.getRulesText(driver, roomClasName);
						testSteps.add("Found : " + getText);
						app_logs.info("Found : " + getText);
	
						Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
						testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new mrb reservation creation");
						app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
								+ ") is displaying rules in new mrb reservation creation");
	
						testSteps.add("========== Verifying broken rules in broken rule popup ==========");
						app_logs.info("========== Verifying broken rules in broken rule popup ==========");
	
						getTestSteps.clear();
						getTestSteps = reservationPage.selectMRBRoomsAndVerifyRulesPopup(driver, tempRoomClassName,
								isAssign, account, isMinimumStayOn, minimumStayValue, isNoCheckInChecked,
								isNoCheckOutChecked, "Yes");
						testSteps.addAll(getTestSteps);
	
						reservationPage.closeReservationTab(driver);
						testSteps.add("Close reservation");
						app_logs.info("Close reservation");
	
						getTestSteps.clear();
						reservationPage.click_NewReservation(driver, getTestSteps);
						testSteps.addAll(getTestSteps);
	
						getTestSteps.clear();
						getTestSteps = reservationPage.enterMRBDates(driver, mrbCheckInDate, mrbCheckOutDate, mrbAdults,
								mrbChildren, mrbRatePlan, mrbPromoCode, "No");
						testSteps.addAll(getTestSteps);
	
						getTestSteps.clear();
						reservationPage.clickOnFindRooms(driver, getTestSteps);
						testSteps.addAll(getTestSteps);
	
					}
	
					reservationPage.closeReservationTab(driver);
					testSteps.add("Close reservation");
					app_logs.info("Close reservation");
	
				}
	
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new mrb reservation creation",
						scriptName, "RulesBrokenVerificationInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new mrb reservation creation",
						scriptName, "RulesBrokenVerificationInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
							
				testSteps.add("========== VERIFYING RULES ARE SATISFYING IN NEW MRB RESERVATION CREATION ==========");
				app_logs.info("========== VERIFYING RULES ARE SATISFYING IN NEW MRB RESERVATION CREATION ==========");
	
				for (int j = 0; j < ratePlanList.size(); j++) {
					String ratePlanName = ratePlanList.get(j);
					app_logs.info("ratePlan: " + ratePlanName);
					String mrbRatePlan = ratePlanName + "|" + ratePlanName;
	
					getTestSteps.clear();
					reservationPage.click_NewReservation(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					getTestSteps = reservationPage.enterMRBDates(driver, mrbCheckInDateGreaterThanEndDate,
							mrbCheckOutDateGreaterThanEndDate, mrbAdults, mrbChildren, mrbRatePlan, mrbPromoCode, "No");
					testSteps.addAll(getTestSteps);
	
					getTestSteps.clear();
					reservationPage.clickOnFindRooms(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
	
					for (int i = 0; i < roomClassList.size(); i++) {
	
						String roomClasName = roomClassList.get(i);
						app_logs.info(roomClasName);
	
						int size = reservationPage.getRulesSize(driver, roomClasName);
						Assert.assertEquals(0, size, "Failed : " + roomClasName + " rules text display");
	
						testSteps.add("Verified room class(" + roomClasName
								+ ") is not displaying any broken rules in new mrb reservation creation");
						app_logs.info("Verified room class(" + roomClasName
								+ ") is not displaying any broken rules in new mrb reservation creation");
	
					}
	
					reservationPage.closeReservationTab(driver);
					testSteps.add("Close reservation");
					app_logs.info("Close reservation");
				}
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new mrb reservation creation",
						scriptName, "RulesSatisfyVerificationInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new mrb reservation creation",
						scriptName, "RulesSatisfyVerificationInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
							
				testSteps.add("========== CREATING NEW MRB RESERVATION ==========");
				app_logs.info("========== CREATING NEW MRB RESERVATION==========");
	
				String mrbRatePlan = ratePlanList.get(0) + "|" + ratePlanList.get(0);
				String mrbGuestFirstName = guestFirstName + "|" + guestFirstName;
				String mrbGuestLastName = guestLastName + "|" + guestLastName;
				String mrbSalutation = salutation + "|" + salutation;
				String mrbPhone = groupPhn + "|" + groupPhn;
	
				getTestSteps.clear();
				reservationPage.click_NewReservation(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterMRBDates(driver, mrbCheckInDate, mrbCheckOutDate, mrbAdults,
						mrbChildren, mrbRatePlan, mrbPromoCode, "No");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				String roomClasName = roomClassList.get(0);
				app_logs.info(roomClasName);
	
				String tempRoomClassName = roomClasName + "|" + roomClasName;
	
				testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new mrb reservation creation ==========");
				app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new mrb reservation creation==========");
	
				testSteps.add("Expected : " + expectedText);
				app_logs.info("Expected : " + expectedText);
	
				String getText = reservationPage.getRulesText(driver, roomClasName);
				testSteps.add("Found : " + getText);
				app_logs.info("Found : " + getText);
	
				Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
				testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new mrb reservation creation");
				app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
						+ ") is displaying rules in new mrb reservation creation");
	
				testSteps.add("========== Verifying broken rules in broken rule popup ==========");
				app_logs.info("========== Verifying broken rules in broken rule popup ==========");
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectMRBRoomsAndVerifyRulesPopup(driver, tempRoomClassName, isAssign,
						account, isMinimumStayOn, minimumStayValue, isNoCheckInChecked, isNoCheckOutChecked, "Yes");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.clickNext(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.enter_MRB_MailingAddress(driver, getTestSteps, mrbSalutation, mrbGuestFirstName,
						mrbGuestLastName, mrbPhone, "", email, account, corpAccountType, groupAddress, "", "", groupCity,
						groupCountry, groupState, groupPostalcode, isGuestProfile, isAddMoreGuestInfo, isSplitRes,
						getRoomClasses);
				testSteps.addAll(getTestSteps);
	
				if ((account.equalsIgnoreCase("") || account.isEmpty())) {
					getTestSteps.clear();
					reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber, nameOnCard,
							cardExpDate);
					testSteps.addAll(getTestSteps);
	
				}
				System.out.println(getRoomClasses);
	
				getTestSteps.clear();
				reservationPage.enter_MarketSegmentDetails(driver, getTestSteps, "", marketSegment, referral);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.clickBookNow(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.get_ReservationStatus(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
	
				reservationPage.closeReservationTab(driver);
				testSteps.add("Close reservation");
				app_logs.info("Close reservation");
	
				getTestSteps.clear();
				reservationSearch.basicSearch_WithReservationNumber(driver, reservationNumber);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				reservationPage.verify_MR_ToolTip(driver, getTestSteps, reservationNumber);
				testSteps.addAll(getTestSteps);
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new mrb reservation",
						scriptName, "RulesBrokenVerificationInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new mrb reservation",
						scriptName, "RulesBrokenVerificationInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		try {
		
				app_logs.info("========= EXTEND RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
				testSteps.add("========= EXTEND RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
	
				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckInDate(driver, checkInDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckOutDate(driver, checkOutDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
				testSteps.addAll(getTestSteps);
	
				int size = reservationPage.brokenRulesPopupSize(driver);
				Assert.assertEquals(0, size, "Failed : " + roomClassList.get(0) + " rules popup displaying");
				testSteps.add("Verified rules popup is not displaying");
				app_logs.info("Verified rules popup is not displaying");
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckInDate(driver, checkInDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckOutDate(driver, checkOutDateGreaterThanEndDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
				testSteps.addAll(getTestSteps);
	
				size = reservationPage.brokenRulesPopupSize(driver);
				Assert.assertEquals(0, size, "Failed : " + roomClassList.get(0) + " rules popup displaying");
				testSteps.add("Verified rules popup is not displaying");
				app_logs.info("Verified rules popup is not displaying");
	
				try{
					
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
				testSteps.addAll(getTestSteps);
				}
				catch(Exception e){
					getTestSteps.clear();
					getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
					testSteps.addAll(getTestSteps);
					
				}
				reservationPage.closeReservationTab(driver);
				testSteps.add("Close Reservation");
				app_logs.info("Close Reservation");
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new mrb reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new mrb reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
		
				app_logs.info("========= REDUCE RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
				testSteps.add("========= REDUCE RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
	
				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckInDate(driver, startDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckOutDate(driver, endDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
						isNoCheckInChecked, isNoCheckOutChecked);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
				testSteps.addAll(getTestSteps);
	
				// add select method to do
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckInDate(driver, startDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.enterCheckOutDate(driver, endDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
						isNoCheckInChecked, isNoCheckOutChecked);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
				testSteps.addAll(getTestSteps);
	
				reservationPage.closeReservationTab(driver);
				testSteps.add("Close Reservation");
				app_logs.info("Close Reservation");
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new reservation on reducing date from stay info", scriptName, "BrokenRulesVerifictaionInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new reservation on reducing date from stay info", scriptName, "BrokenRulesVerifictaionInNewMRBRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
		
	if(isGroupRes.equalsIgnoreCase("Yes")){
		try {

			testSteps.add("==========VERIFICATION OF RULES IN GROUP RESERVATIONS==========");
			app_logs.info("==========VERIFICATION OF RULES IN GROUP RESERVATIONS==========");
			navigation.secondaryGroupsManu(driver);
			testSteps.add("Navigate Groups");
			app_logs.info(" Navigate Groups");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", scriptName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
						e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click on new account button", scriptName, "EnterAccountName",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
					e.printStackTrace();

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
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", scriptName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			getTestSteps = group.selectAccountAttributes(driver, marketSegment, referral);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", scriptName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", scriptName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", scriptName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Save  Account", scriptName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to new reservation creation", scriptName,
						"GroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to new reservation creation", scriptName,
						"GroupReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("========== VERIFYING RULES ARE BROKEN IN NEW GROUP RESERVATION CREATION ==========");
			app_logs.info("========== VERIFYING RULES ARE BROKEN IN NEW GROUP RESERVATION CREATION ==========");

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

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < roomClassList.size(); i++) {

					String roomClasName = roomClassList.get(i);
					app_logs.info(roomClasName);

					testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new group reservation creation ==========");
					app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new group reservation creation ==========");

					testSteps.add("Expected : " + expectedText);
					app_logs.info("Expected : " + expectedText);

					String getText = reservationPage.getRulesText(driver, roomClasName);
					testSteps.add("Found : " + getText);
					app_logs.info("Found : " + getText);

					Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
					testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new group reservation creation");
					app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new group reservation creation");

					testSteps.add("========== Verifying broken rules in broken rule popup ==========");
					app_logs.info("========== Verifying broken rules in broken rule popup ==========");

					getTestSteps.clear();
					getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
							isNoCheckInChecked, isNoCheckOutChecked);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.clickRulesPopup(driver, "No");
					testSteps.addAll(getTestSteps);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new group reservation creation",
						scriptName, "BrokenRulesVerificationInNewGroupRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new group reservation creation",
						scriptName, "BrokenRulesVerificationInNewGroupRes", driver);
			}
		}
		
		try {

			testSteps.add("========== VERIFYING RULES ARE SATISFYING IN NEW GROUP RESERVATION CREATION ==========");
			app_logs.info("========== VERIFYING RULES ARE SATISFYING IN NEW GROUP RESERVATION CREATION ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, checkOutDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < roomClassList.size(); i++) {

					String roomClasName = roomClassList.get(i);
					app_logs.info(roomClasName);

					int size = reservationPage.getRulesSize(driver, roomClasName);
					Assert.assertEquals(0, size, "Failed : " + roomClasName + " rules text display");
					testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is not displaying rules in new group reservation creation");
					app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is not displaying rules in new group reservation creation");

				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in group reservation creation", scriptName, "RulesSatisfyVerificationInNewGroupRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in group reservation creation", scriptName, "RulesSatisfyVerificationInNewGroupRes",  driver);
			}
		}
		try {

			testSteps.add("========== CREATING NEW GROUP RESERVATION ==========");
			app_logs.info("========== CREATING NEW GROUP RESERVATION ==========");

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
			reservationPage.select_Rateplan(driver, getTestSteps, ratePlanList.get(0), promoCode);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			String roomClasName = roomClassList.get(0);
			app_logs.info(roomClasName);

			testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new group reservation creation ==========");
			app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new group reservation creation ==========");

			testSteps.add("Expected : " + expectedText);
			app_logs.info("Expected : " + expectedText);

			String getText = reservationPage.getRulesText(driver, roomClasName);
			testSteps.add("Found : " + getText);
			app_logs.info("Found : " + getText);

			Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
			testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new group reservation creation");
			app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new reservation creation");

			testSteps.add("========== Verifying broken rules in broken rule popup ==========");
			app_logs.info("========== Verifying broken rules in broken rule popup ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterEmail(driver, getTestSteps, email);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber,
					nameOnCard, cardExpDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.get_ReservationStatus(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickCloseReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
				
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in group reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in group reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			}
		}		
		
		try {

			app_logs.info("========= EXTEND GROUP RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
			testSteps.add("========= EXTEND GROUP RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, checkInDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, checkOutDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			int size = reservationPage.brokenRulesPopupSize(driver);
			Assert.assertEquals(0, size, "Failed : " + roomClassList.get(0) + " rules popup displaying");
			testSteps.add("Verified rules popup is not displaying");
			app_logs.info("Verified rules popup is not displaying");

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new group reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new group reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else { 
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("========= REDUCE GROUP RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
			testSteps.add("=========  REDUCE GROUP RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
			testSteps.addAll(getTestSteps);

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			Wait.wait2Second();
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new group reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new group reservation on redcuing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	if(isCorporateRes.equalsIgnoreCase("Yes")){

		try {

			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		// New account
		try {
			testSteps.add("========== Creating corporate account ==========");
			app_logs.info("========== Creating corporate account ==========");
			corpAccountFullName = corpAccountFullName + Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, corpAccountType);
			CreateTA.AccountDetails(driver, corpAccountType, corpAccountFullName);
			corpAccountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, corpAccountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
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

			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create corporate account", scriptName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add(
					"========== VERIFYING RULES ARE BROKEN IN NEW CORPORATE ACCOUNT RESERVATION CREATION ==========");
			app_logs.info(
					"========== VERIFYING RULES ARE BROKEN IN NEW CORPORATE ACCOUNT RESERVATION CREATION ==========");

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

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < roomClassList.size(); i++) {

					String roomClasName = roomClassList.get(i);
					app_logs.info(roomClasName);

					testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new corp reservation creation ==========");
					app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new corp reservation creation ==========");

					testSteps.add("Expected : " + expectedText);
					app_logs.info("Expected : " + expectedText);

					String getText = reservationPage.getRulesText(driver, roomClasName);
					testSteps.add("Found : " + getText);
					app_logs.info("Found : " + getText);

					Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
					testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new corp reservation creation");
					app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules popup in new corp reservation creation");

					testSteps.add("========== Verifying broken rules in broken rule popup ==========");
					app_logs.info("========== Verifying broken rules in broken rule popup ==========");

					getTestSteps.clear();
					getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
							isNoCheckInChecked, isNoCheckOutChecked);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.clickRulesPopup(driver, "No");
					testSteps.addAll(getTestSteps);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new corp reservation",
						scriptName, "BrokenRulesVerificationInCorpRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new corp reservation",
						scriptName, "BrokenRulesVerificationInCorpRes", driver);
			}
		}

		try {
			
			testSteps.add(
					"========== VERIFYING RULES ARE SATISFYING IN NEW CORPORATE ACCOUNT RESERVATION CREATION ==========");
			app_logs.info(
					"========== VERIFYING RULES ARE SATISFYING IN NEW CORPORATE ACCOUNT RESERVATION CREATION ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, checkOutDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < roomClassList.size(); i++) {

					String roomClasName = roomClassList.get(i);
					app_logs.info(roomClasName);

					int size = reservationPage.getRulesSize(driver, roomClasName);
					Assert.assertEquals(0, size, "Failed : " + roomClasName + " rules text display");
					testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is not displaying rules  in new corp account reservation creation");
					app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules in new corp account reservation creation");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in corp account reservation creation", scriptName, "RulesSatisfyVerificationInNewCorpAccoutRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in  corp account reservation creation", scriptName, "RulesSatisfyVerificationInNewCorpAccoutRes",  driver);
			}
		}
		try {
			
			testSteps.add("========== CREATING NEW CORPORATE ACCOUNT RESERVATION ==========");
			app_logs.info("========== CREATING NEW CORPORATE ACCOUNT RESERVATION ==========");

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
			reservationPage.select_Rateplan(driver, getTestSteps, ratePlanList.get(0), promoCode);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			String roomClasName = roomClassList.get(0);
			app_logs.info(roomClasName);

			testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new reservation creation ==========");
			app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new reservation creation ==========");

			testSteps.add("Expected : " + expectedText);
			app_logs.info("Expected : " + expectedText);

			String getText = reservationPage.getRulesText(driver, roomClasName);
			testSteps.add("Found : " + getText);
			app_logs.info("Found : " + getText);

			Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
			testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new reservation creation");
			app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new reservation creation");

			testSteps.add("========== Verifying broken rules in broken rule popup ==========");
			app_logs.info("========== Verifying broken rules in broken rule popup ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterEmail(driver, getTestSteps, email);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber,
					nameOnCard, cardExpDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.get_ReservationStatus(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickCloseReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in corporate account reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in corporate account reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			}
		}		
		
		try {

			app_logs.info(
					"========= EXTEND CORPORATE ACCOUNT RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
			testSteps.add(
					"========= EXTEND CORPORATE ACCOUNT RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, checkInDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, checkOutDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			int size = reservationPage.brokenRulesPopupSize(driver);
			Assert.assertEquals(0, size, "Failed : " + roomClassList.get(0) + " rules popup displaying");
			testSteps.add("Verified rules popup is not displaying");
			app_logs.info("Verified rules popup is not displaying");

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new corporate  account reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new corporate  account reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info(
					"========= REDUCE CORPORATE ACCOUNT RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
			testSteps.add(
					"=========  REDUCE CORPORATE ACCOUNT RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
			testSteps.addAll(getTestSteps);

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			Wait.wait2Second();
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new corporate  account reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new corporate  account reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	if(isTravelAgentRes.equalsIgnoreCase("Yes")){
		try {

			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");
			if(isCorporateRes.equalsIgnoreCase("Yes")){
				CreateTA.close_Account(driver);
				testSteps.add("Closed opened accounts");
				app_logs.info("Closed opened accounts");
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", scriptName, "NavigateAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
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

			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create travel agent account", scriptName, "TravelAgentAccount",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", scriptName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add(
					"========== VERIFYING RULES ARE BROKEN IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION ==========");
			app_logs.info(
					"========== VERIFYING RULES ARE BROKEN IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION ==========");

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

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < roomClassList.size(); i++) {

					String roomClasName = roomClassList.get(i);
					app_logs.info(roomClasName);

					testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules in new travel agent reservation creation ==========");
					app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules in new travel agent reservation creation ==========");

					testSteps.add("Expected : " + expectedText);
					app_logs.info("Expected : " + expectedText);

					String getText = reservationPage.getRulesText(driver, roomClasName);
					testSteps.add("Found : " + getText);
					app_logs.info("Found : " + getText);

					Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
					testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules in new travel agent reservation creation");
					app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is displaying rules in new travel agent reservation creation");

					testSteps.add("========== Verifying broken rules in broken rule popup ==========");
					app_logs.info("========== Verifying broken rules in broken rule popup ==========");

					getTestSteps.clear();
					getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
							isNoCheckInChecked, isNoCheckOutChecked);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.clickRulesPopup(driver, "No");
					testSteps.addAll(getTestSteps);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new travel agent reservation",
						scriptName, "BrokenRulesVerificationInTravelRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rules popup in new travel agent reservation",
						scriptName, "BrokenRulesVerificationInTravelRes", driver);
			}
		}

		try {
			
			testSteps.add(
					"========== VERIFYING RULES ARE SATISFYING IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION ==========");
			app_logs.info(
					"========== VERIFYING RULES ARE SATISFYING IN NEW TRAVEL AGENT ACCOUNT RESERVATION CREATION ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, checkOutDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Adults(driver, getTestSteps, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, promoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < roomClassList.size(); i++) {

					String roomClasName = roomClassList.get(i);
					app_logs.info(roomClasName);
					int size = reservationPage.getRulesSize(driver, roomClasName);
					Assert.assertEquals(0, size, "Failed : " + roomClasName + " rules text display");
					testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is not displaying rules in new travel agent account reservation creation");
					app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanName
							+ ") is not displaying rules in new travel agent account reservation creation");

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in travel agent account reservation creation", scriptName, "RulesSatisfyVerificationInNewTravelAgentAccoutRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in  travel agent account reservation creation", scriptName, "RulesSatisfyVerificationInNewTravelAgentAccoutRes",  driver);
			}
		}
		try {

			testSteps.add("========== CREATING NEW TRAVEL AGENT ACCOUNT RESERVATION ==========");
			app_logs.info("========== CREATING NEW TRAVEL AGENT ACCOUNT RESERVATION ==========");

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
			reservationPage.select_Rateplan(driver, getTestSteps, ratePlanList.get(0), promoCode);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			String roomClasName = roomClassList.get(0);
			app_logs.info(roomClasName);

			testSteps.add("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules new travel agent reservation creation ==========");
			app_logs.info("========== Verifying room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules new travel agent reservation creation ==========");

			testSteps.add("Expected : " + expectedText);
			app_logs.info("Expected : " + expectedText);

			String getText = reservationPage.getRulesText(driver, roomClasName);
			testSteps.add("Found : " + getText);
			app_logs.info("Found : " + getText);

			Assert.assertEquals(getText, expectedText, "Failed : " + roomClasName + " rules text didn't match");
			testSteps.add("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new travel agent reservation creation");
			app_logs.info("Verified room class(" + roomClasName + ") with rateplan(" + ratePlanList.get(0)
					+ ") is displaying rules in new travel agent reservation creation");

			testSteps.add("========== Verifying broken rules in broken rule popup ==========");
			app_logs.info("========== Verifying broken rules in broken rule popup ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomWithoutRulePopupHandling(driver, roomClasName, "Yes", "");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterEmail(driver, getTestSteps, email);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber,
					nameOnCard, cardExpDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.get_ReservationStatus(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickCloseReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new travel agent reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new travel agent reservation", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			}
		}		
		
		try {

			app_logs.info(
					"========= EXTEND TRAVEL AGENT ACCOUNT RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
			testSteps.add(
					"========= EXTEND TRAVEL AGENT ACCOUNT RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, checkInDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, checkOutDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			int size = reservationPage.brokenRulesPopupSize(driver);
			Assert.assertEquals(0, size, "Failed : " + roomClassList.get(0) + " rules popup displaying");
			testSteps.add("Verified rules popup is not displaying");
			app_logs.info("Verified rules popup is not displaying");

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new travel agent  account reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new travel agent  account reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info(
					"========= REDUCE TRAVEL AGENT ACCOUNT  RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
			testSteps.add(
					"=========  REDUCE TRAVEL AGENT ACCOUNT RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
			testSteps.addAll(getTestSteps);

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			Wait.wait2Second();
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new travel agent  account reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new travel agent  account reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}
	if(isTapeChartRes.equalsIgnoreCase("Yes")){
		
	
		try {
			
			testSteps.add(
					"========== VERIFYING RULES ARE BROKEN IN NEW ASSIGNED RESERVATION CREATION FROM TAPECHART ==========");
			app_logs.info(
					"========== VERIFYING RULES ARE BROKEN IN NEW ASSIGNED RESERVATION CREATION FROM TAPECHART ==========");

			navigation.navigateTapeChart(driver, test);
			testSteps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");

			app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
			testSteps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				getTestSteps = tapeChart.tapeChartSearch(driver, startDate, endDate, adults, children, ratePlanName);
				testSteps.addAll(getTestSteps);

				String[] roomClasAbb = roomClassAbb.split(",");
				for (String roomAbb : roomClasAbb) {
					roomAbb = roomAbb.trim();
					app_logs.info(roomAbb);
					roomClassAbbreviationList.add(roomAbb);
				}
				for (int i = 0; i < roomClassAbbreviationList.size(); i++) {

					String roomAbb = roomClassAbbreviationList.get(i);
					app_logs.info(roomAbb);

					testSteps.add("========== Verifying broken rules in borken rule popup ==========");
					app_logs.info("========== Verifying broken rules in borken rule popup ==========");

					app_logs.info("Select available room");
					testSteps.add("Select available room");
					tapeChart.clickAvailableSlotWithOutRulePopupHandling(driver, roomAbb);

					getTestSteps.clear();
					getTestSteps = tapeChart.verifyTapeChartBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
							isNoCheckInChecked, isNoCheckOutChecked);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = tapeChart.cancelRulesPopup(driver);
					testSteps.addAll(getTestSteps);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is displaying rules popup in tape chart assigned reservation creation", scriptName, "BrokenRulesVerificationIntapeChartAssignedRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room class is displaying rules popup in tape chart assigned reservation creation", scriptName, "BrokenRulesVerificationIntapeChartAssignedRes", driver);
			}
		}
		

		try {
			
			testSteps.add(
					"========== VERIFYING RULES ARE SATISFYING IN NEW ASSIGNED RESERVATION CREATION FROM TAPECHART ==========");
			app_logs.info(
					"========== VERIFYING RULES ARE SATISFYING IN NEW ASSIGNED RESERVATION CREATION FROM TAPECHART ==========");

			app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
			testSteps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");

			for (int j = 0; j < ratePlanList.size(); j++) {
				String ratePlanName = ratePlanList.get(j);
				app_logs.info("ratePlan: " + ratePlanName);

				getTestSteps.clear();
				getTestSteps = tapeChart.tapeChartSearch(driver, checkInDateGreaterThanEndDate,
						checkOutDateGreaterThanEndDate, adults, children, ratePlanName);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < roomClassAbbreviationList.size(); i++) {

					String roomAbb = roomClassAbbreviationList.get(i);
					app_logs.info(roomAbb);

					testSteps.add("========== Verifying broken rules in broken rule popup ==========");
					app_logs.info("========== Verifying broken rules in broken rule popup ==========");

					app_logs.info("Select available room");
					testSteps.add("Select available room");
					tapeChart.clickAvailableSlotWithOutRulePopupHandling(driver, roomAbb);

					int size = tapeChart.tapeChartRulePopupSize(driver);
					 Assert.assertEquals(2, size,
					 "Failed : " + roomClassList.get(0) + " rules popup displaying");
					testSteps.add("Verified rules popup is not displaying");
					app_logs.info("Verified rules popup is not displaying");

					Wait.wait2Second();
					reservationPage.closeReservationTab(driver);
					testSteps.add("Close Reservation");
					app_logs.info("Close Reservation");

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new assigned reservation creation from tape chart", scriptName, "RulesSatisfyVerifictaionInNewtapeChartAssignedRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new assigned reservation creation from tape chart", scriptName, "RulesSatisfyVerifictaionInNewtapeChartAssignedRes", driver);
			}
		}
		

		try {
			
			testSteps.add("========== CREATING NEW ASSIGNED RESERVATION FROM TAPECHART ==========");
			app_logs.info("========== CREATING NEW ASSIGNED RESERVATION FROM TAPECHART ==========");

			app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
			testSteps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");

			String ratePlanName = ratePlanList.get(0);
			app_logs.info("ratePlan: " + ratePlanName);

			getTestSteps.clear();
			getTestSteps = tapeChart.tapeChartSearch(driver, startDate, endDate, adults, children, ratePlanName);
			testSteps.addAll(getTestSteps);

			String roomAbb = roomClassAbbreviationList.get(0);
			app_logs.info(roomAbb);

			testSteps.add("========== Verifying broken rules in broken rule popup ==========");
			app_logs.info("========== Verifying broken rules in broken rule popup ==========");

			app_logs.info("Select available room");
			testSteps.add("Select available room");
			tapeChart.clickAvailableSlotWithOutRulePopupHandling(driver, roomAbb);

			getTestSteps.clear();
			getTestSteps = tapeChart.verifyTapeChartBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = tapeChart.clickBookIcon(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			String room = reservationPage.getRoomSelectedFromTapeChart(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			 Assert.assertTrue(room.contains(roomClassList.get(0)), "Failed: Room is not selected");
			testSteps.add("Verified Room Class is '" + roomClassList.get(0) + "'");

			app_logs.info("==========ENTER GUEST INFO==========");
			testSteps.add("==========ENTER GUEST INFO==========");

			getTestSteps.clear();
			reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName, guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.uncheckCreateGuestProfile(driver, getTestSteps, isGuestProfile);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enterEmail(driver, getTestSteps, email);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========ENTER MARKETING INFO==========");
			testSteps.add("==========ENTER MARKETING INFO==========");

			getTestSteps.clear();
			reservationPage.enter_MarketSegmentDetails(driver, getTestSteps, "", marketSegment, referral);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========SAVE RESERVATION==========");
			testSteps.add("==========SAVE RESERVATION==========");

			getTestSteps.clear();
			reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========VERIFY RESERVATION CONFIRMATION POPUP DETAILS==========");
			testSteps.add("==========VERIFY RESERVATION CONFIRMATION POPUP DETAILS==========");
			getTestSteps.clear();
			getTestSteps = reservationPage.getReservationEmail(driver, email);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationNumber = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			String roomNumber = reservationPage.getRoomNumber(driver, getTestSteps, isAssign);

			testSteps.add("Selected Room Number : " + roomNumber);
			app_logs.info("Selected Room Number : " + roomNumber);

			String selectedRoomClass = reservationPage.getRoomClassResDetail(driver);
			testSteps.add("Selected RoomClass : " + selectedRoomClass);
			app_logs.info("Selected RoomClass : " + selectedRoomClass);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new assigned reservation from tape chart", scriptName, "RulesSatisfyVerifictaionInNewtapeChartAssignedRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new assigned reservation from tape chart", scriptName, "RulesSatisfyVerifictaionInNewtapeChartAssignedRes", driver);
			}
		}
		
		try {

			app_logs.info("========= EXTEND RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");
			testSteps.add("========= EXTEND RESERVATION FROM STAY INFO SECTION TO SATISFY RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, checkInDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, checkOutDateGreaterThanEndDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			int size = reservationPage.brokenRulesPopupSize(driver);
			Assert.assertEquals(0, size, "Failed : " + roomClassList.get(0) + " rules popup displaying");
			testSteps.add("Verified rules popup is not displaying");
			app_logs.info("Verified rules popup is not displaying");

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new tapechart assigned reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewTapeChartRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are satisfied in new tapechart assigned reservation on extending date from stay info", scriptName, "RulesSatisfyVerifictaionInNewTapeChartRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		try {

			app_logs.info("========= REDUCE RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");
			testSteps.add("=========  REDUCE RESERVATION FROM STAY INFO SECTION TO BROKE RULE ========");

			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, startDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, endDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoomInStayInfoSection(driver, roomClassList.get(0), "Yes");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyBrokenRulesPopup(driver, isMinimumStayOn, minimumStayValue,
					isNoCheckInChecked, isNoCheckOutChecked);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickRulesPopup(driver, "Yes");
			testSteps.addAll(getTestSteps);

			// add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			Wait.wait2Second();
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new tapechart assigned reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewtapeChartRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify that rules are broken in new tapechart assigned reservation on reducing date from stay info", scriptName, "RulesBrokenVerifictaionInNewtapeChartRes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
		try {


			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps into report", scriptName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps into report", scriptName, "AddStepsIntoReport", driver);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyRulesInAllInncenterRes", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver() {
		// driver.quit();
	}

}
