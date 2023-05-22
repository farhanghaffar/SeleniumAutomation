package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.OverView;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyRateAndOutOfOrderForRoomClassInTapeChart extends TestCore{

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	RoomClass roomClass = new RoomClass();
	public static String roomClassName;
	public static String roomClassAbbrivation;
	Navigation navigation = new Navigation();
	CPReservationPage res = new CPReservationPage();
	RoomMaintenance room_maintenance = new RoomMaintenance();
	RoomStatus roomstatus = new RoomStatus();
	OverView overview = new OverView();
	Tapechart tapeChart = new Tapechart();
	CPReservationPage reservationPage = new CPReservationPage();
	NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
	Rules rules = new Rules();
	Season seasonObj = new Season();
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	List<LocalDate> datesList = new ArrayList<LocalDate>();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	List<String> date = new ArrayList<String>();
	List<String> day = new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	Rate rate = new Rate();
	String guestFirstName = null, guestLastName = null, reservation = null, testName = null, rateName = null,
			rateDisplayName = null;
	String nights = null, startDate = null, endDate = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void verifyRateAndOutOfOrderForRoomClassInTapeChart(String testCaseID, String militaryPlan, String RatePlan) {
		test_name = "VerifyRateAndOutOfOrderForRoomClassInTapeChart";
		test_description = "Making a Room Out Of Order And Verify TapeChart , RoomStatus and Rate<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848710' target='_blank'>"
				+ "Click here to open TestRail: C848710</a><br>";
		if(!Utility.validateString(testCaseID)) {
			caseId.add("848710");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		test_catagory = "Verification";
		testName = test_name;
		String randomString = Utility.GenerateRandomNumber();
		String RoomClassName = "TapeChart" + randomString;
		String RoomClassAbbrivation = "TC" + randomString;
		String MaxAdults = "2";
		String MaxPersons = "5";
		String RoomQuantity = "2";
		String RateName = "VerifyTapeChart" + randomString;
		String tempRate1 = RateName; 
		RateName = RateName + randomString;
		String DisplayName = RateName;
		String Amount = "100";		
		String RatePolicy = RateName;
		String RateDescription = RateName;
		String Subject = "Subject";
		String Detail = Subject + "Details" + randomString;
		String AssociateSeason = "All Year Season";
		
		String timeZone = "US/Eastren";
		String dateFormat = "dd/MM/yyyy";
		HashMap<String, Boolean> getDaysToCheck = new HashMap<>();
		String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		String checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
		Utility.RoomNo = "1";
		String season = "SeasonWithNoDaysAvailable";
		String source = "innCenter";			
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			driver = getDriver();
			loginClinent3281(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
	
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
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
		
		roomClassName = RoomClassName + Utility.getTimeStamp();
		roomClassAbbrivation = RoomClassAbbrivation + Utility.getTimeStamp();
		rateName = RateName + Utility.getTimeStamp();
		rateDisplayName = DisplayName + Utility.getTimeStamp();
		
		try {
			testSteps.add("<b> ************Create Room Class</b>****************");
			
			navigation.Setup(driver);
			testSteps.add("Navigate to Setup");
			navigation.RoomClass(driver);
			testSteps.add("Navigate to RoomClasses tab");
			
			newRcPage.createRoomClassV2( driver, roomClassName, roomClassAbbrivation,
					MaxAdults, MaxPersons, RoomQuantity,
					test, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Create Roomclass", "NONGS_Login", "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Create Roomclass", "NONGS_Login", "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			// Create Rate
			testSteps.add("<b> ************Create Rate/b>****************");
			
			navigation.InventoryV2(driver);
			testSteps.add("Navigate to Inventory");
			navigation.Rate(driver);
			testSteps.add("Navigate to Rate");

			rate.new_Rate(driver, rateName, MaxAdults, MaxPersons, Amount, MaxAdults, MaxPersons,
				rateDisplayName, RatePolicy, RateDescription, roomClassName);

			testSteps.add("Successfull Created Rate: <b>" + rateName + "</b>");
			app_logs.info("Successfull Created Rate: " + rateName);
			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

//		roomClassName = "Junior Suites";
//		roomClassAbbrivation = "JS";
	
		// Navigate to TapeChart
			

		 int b=0;		
		String secondRateName = "VerifyNoDaysRate";
		String tempRate2 = secondRateName;
		secondRateName = secondRateName  + randomString;

		try {
			getDaysToCheck.put("Mon", false);
			getDaysToCheck.put("Tue", true);
			getDaysToCheck.put("Wed", true);
			getDaysToCheck.put("Thu", true);
			getDaysToCheck.put("Fri", true);
			getDaysToCheck.put("Sat", true);
			getDaysToCheck.put("Sun", true);

			String getDay = Utility.getCurrentDate("EEE");
			for(b=0; b < 7; b++) {
				getDay = Utility.parseDate(Utility.getDatePast_FutureDate(b), "MM/dd/yyyy", "EEE");				
				if(getDay.equalsIgnoreCase("Fri")) {
					break;
				}
			}
			printString("getDay : " + getDay);
			

			navigation.Inventory(driver, testSteps);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
	
			navigation.Seasons(driver);
			testSteps.add("Navigate to Seasons");
			
			seasonObj.NewSeasonButtonClick(driver);
			testSteps.add("Click New Season Button");

			seasonObj.enterSeasonName(driver, testSteps, season);			
			seasonObj.selectSeasonStatus(driver, testSteps, "Active");
			
			seasonObj.SelectStartDate(driver, 0);
			testSteps.add("Selecte start date : " + Utility.getDatePast_FutureDate(0));

			seasonObj.SelectEndDate(driver, 20);
			testSteps.add("Selecte end date : " + Utility.getDatePast_FutureDate(20));

			seasonObj.selectDays(driver, getDaysToCheck, testSteps);
			
			seasonObj.SaveSeason(driver);
			testSteps.add("Click on save button");

			rules.clickCloseTab(driver, testSteps);

			navigation.Inventory(driver, testSteps);
			testSteps.add("Clicked on inventory");
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create season", "CreateSeason", "CreateSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create season", "CreateSeason", "CreateSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			// Create Rate
			testSteps.add("<b> ************Create Rate/b>****************");
			
			navigation.Inventory(driver, testSteps);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			getTestSteps.clear();
			getTestSteps = navigation.secondaryRatesMenuItem(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.ClickNewRate(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateName(driver, secondRateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.SelectRatePlan(driver, militaryPlan);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxAdults(driver, MaxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterMaxPersons(driver, MaxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterBaseAmount(driver, Amount);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalAdult(driver, MaxAdults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterAdditionalChild(driver, MaxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDisplayName(driver, secondRateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRatePolicy(driver, secondRateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.EnterRateDescription(driver, secondRateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSeason(driver, season);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateRoomClass(driver, roomClassName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = rate.AssociateSource(driver, source);
			testSteps.addAll(getTestSteps);
			
			


			getTestSteps.clear();
			getTestSteps = rate.Save_DoneRate(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("New Rate '" + secondRateName + "' Created & Verified ");
			app_logs.info("New Rate '" + secondRateName + "' Created & Verified");

			testSteps.add("Successfull Created Rate: <b>" + secondRateName + "</b>");
			app_logs.info("Successfull Created Rate: " + secondRateName);
			
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		checkInDate = Utility.getNextDate(b, "MM/dd/yyyy", timeZone);
		checkOutDate = Utility.getNextDate(b+1, "MM/dd/yyyy", timeZone);
		// Navigate to TapeChart
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848710' target='_blank'>"
					+ "<b>Verify Availability on the tape chart for a search criteria which has no rates for some days.</a> =====");

			testSteps.add("<b>*****Start Verifying RoomClass and Rate in Tape Chart*****</b>");
			
			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			try {
				testSteps.addAll(tapeChart.verifyCheckinInputTapeChart(driver));				
			}catch (Error e) {
				testSteps.add(e.toString());
			}
			
			tapeChart.selectRatePlan(driver, "-- Best Available -- ", testSteps);										
			try {
			testSteps.addAll(tapeChart.verifyRatePlanColour(driver));
			}catch (Error e) {
				testSteps.add(e.toString());
			}
		
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDate, checkOutDate, timeZone, testSteps);
			tapeChart.enterAdult(driver, MaxAdults, testSteps);
			if(RatePlan.equalsIgnoreCase("Best Available")) {
				tapeChart.selectRatePlan(driver, "-- Best Available -- ", testSteps);								
			}else{
				tapeChart.selectRatePlan(driver, militaryPlan, testSteps);								
							
			}
			tapeChart.clickSearchButton(driver, testSteps);
			Wait.wait3Second();
			try {
				testSteps.addAll(tapeChart.verifyNoResultsError(driver));

			}catch (Error e) {
				testSteps.add(e.toString());
			}
			statusCode.add(0, "1");

			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848168' target='_blank'>"
					+ "<b>Set up-Verify Creating/updating a room class and verify rooms in tape chart /room assignment picker.</a> =====");

			tapeChart.selectRatePlan(driver, RatePlan, testSteps);								
			tapeChart.clickSearchButton(driver, testSteps);
			
			try {
				tapeChart.verifyRoomClassAndAmount(driver, testSteps, roomClassAbbrivation, Amount);
			}catch (Error e) {
				testSteps.add(e.toString());
			}

			statusCode.add(1, "1");
			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify tape chart fileds", testName, "VerifyTapeChartFields", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify tape chart fileds", testName, "VerifyTapeChartFields", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			// Delete created records for the script
			testSteps.add("<b>*****************Deleting created Records for Script********************</b>");
			navigation.Inventory(driver);
			testSteps.add("Navigate to Inventory");
			navigation.Rate(driver);
			testSteps.add("Navigate to Rate");
			
			rate.deleteRates(driver, tempRate2);
			testSteps.add("Rates Deleted starting with name: " + "<b>" + tempRate2 + "</b>");
			
			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");
			
			navigation.Inventory(driver, testSteps);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
	
			navigation.Seasons(driver);
			testSteps.add("Navigate to Seasons");
			
			seasonObj.SearchButtonClick(driver);
			try {
			seasonObj.DeleteSeason(driver, season, testSteps);
			}catch (Exception e) {
				e.printStackTrace();
				}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		String startDate = Utility.getCurrentDate(dateFormat, timeZone);
		String endDate = Utility.getNextDate(1, dateFormat, timeZone);
	
		try {

			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848233' target='_blank'>"
					+ "<b>Modify a room maintenance item and verify in tape chart.</a> =====");

			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");

			navigation.navigateGuestservice(driver);
			testSteps.add("Clicked guest services");
			app_logs.info("Clicked guest services");

			navigation.RoomMaintenance(driver);
			testSteps.add("Clicked room maintenance");
			app_logs.info("Clicked room maintenance");

			int getDays = Utility.getNumberofDays(startDate, endDate, dateFormat);

			room_maintenance.createOutOfOrderRoomItem(driver, startDate, endDate, dateFormat, Subject, Utility.RoomNo, roomClassName,
					testSteps, Detail);
			
	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate to TapeChart", testName, "Navigation", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate to TapeChart", testName, "Navigation", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
		checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
		
		try {

			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");
	
			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDate, checkOutDate, timeZone, testSteps);
			tapeChart.enterAdult(driver, MaxAdults, testSteps);
			tapeChart.selectRatePlan(driver, RatePlan, testSteps);								
			tapeChart.clickSearchButton(driver, testSteps);
			tapeChart.verifyOutOfOrder(driver, testSteps, roomClassAbbrivation, Utility.RoomNo);
			testSteps.add("Successfully Verified Out of Order Room colour in Tape Chart");
			app_logs.info("Successfully Verified Out of Order Room colour in Tape Chart");

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String startDate1 =Utility.getNextDate(1, dateFormat, timeZone);
		String endDate1 = Utility.getNextDate(2, dateFormat, timeZone);
	
		try {
			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");

			navigation.navigateGuestservice(driver);
			testSteps.add("Clicked guest services");
			app_logs.info("Clicked guest services");

			navigation.RoomMaintenance(driver);
			testSteps.add("Clicked room maintenance");
			app_logs.info("Clicked room maintenance");

			room_maintenance.clickOnExistingRoomMaintenance(driver, Subject, testSteps);
			room_maintenance.updateDate(driver, startDate1, endDate1, dateFormat, testSteps);
			
	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate to TapeChart", testName, "Navigation", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Navigate to TapeChart", testName, "Navigation", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
		String checkInDate1 = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
		String checkOutDate1 = Utility.getNextDate(2, "MM/dd/yyyy", timeZone);
			
		try {

			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");
	
			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDate1, checkOutDate1, timeZone, testSteps);
			tapeChart.enterAdult(driver, MaxAdults, testSteps);
			tapeChart.selectRatePlan(driver, RatePlan, testSteps);								
			tapeChart.clickSearchButton(driver, testSteps);
			tapeChart.verifyOutOfOrder(driver, testSteps, roomClassAbbrivation, Utility.RoomNo);
			testSteps.add("Successfully Verified Out of Order Room colour in Tape Chart");
			app_logs.info("Successfully Verified Out of Order Room colour in Tape Chart");
			statusCode.add(2, "1");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848234' target='_blank'>"
					+ "<b>Delete a room maintenance item and verify in tape chart.</a> =====");

			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");

			navigation.navigateGuestservice(driver);
			testSteps.add("Clicked guest services");
			app_logs.info("Clicked guest services");

			navigation.RoomMaintenance(driver);
			testSteps.add("Clicked room maintenance");
			app_logs.info("Clicked room maintenance");

			room_maintenance.DeleteRoomOutOfOrder(driver, Subject);	

		} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to delete room maintenance item", testName, "RoomMaintenance", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to delete room maintenance item", testName, "RoomMaintenance", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
		try {

			navigation.ReservationV2_Backward(driver);
			testSteps.add("Back to reservation page");
			app_logs.info("Back to reservation page");
	
			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDate, checkOutDate, timeZone, testSteps);
			tapeChart.enterAdult(driver, MaxAdults, testSteps);
			tapeChart.selectRatePlan(driver, RatePlan, testSteps);								
			tapeChart.clickSearchButton(driver, testSteps);
			tapeChart.verifyOutOfOrder(driver, testSteps, roomClassAbbrivation, Utility.RoomNo, false, 1);
			testSteps.add("Successfully Verified Out of Order message is removed for Room in Tape Chart");
			app_logs.info("Successfully Verified Out of Order message is removed for Room in Tape Chart");
			statusCode.add(3, "1");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation cannot be created for past date", testName, "VerifyReservationCreationForLockDate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		try {
			// Delete created records for the script
			testSteps.add("<b>*****************Deleting created Records for Script********************</b>");
			navigation.Inventory(driver);
			testSteps.add("Navigate to Inventory");
			navigation.Rate(driver);
			testSteps.add("Navigate to Rate");
			rate.deleteRates(driver, tempRate1);
			testSteps.add("Rates Deleted starting with name: " + "<b>" + tempRate1 + "</b>");
			
	//		rate.deleteRates(driver, tempRate2);
	//		testSteps.add("Rates Deleted starting with name: " + "<b>" + tempRate2 + "</b>");
			navigation.Setup(driver);
			testSteps.add("Navigate to Setup");
			navigation.RoomClass(driver);
			testSteps.add("Navigate to Room Classes");
			newRcPage.deleteRoomClassV2(driver, roomClassName);
			testSteps.add("Room Class deleted starting from: "+ "<b>"+roomClassName+"</b>");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("OutOfOrderInTapeChart", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}



	
	

}
