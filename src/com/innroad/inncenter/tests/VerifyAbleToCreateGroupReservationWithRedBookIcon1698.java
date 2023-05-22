package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyAbleToCreateGroupReservationWithRedBookIcon1698 extends TestCore {

	// Automation-1465
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	public static String test_description = "";
	public static String test_catagory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyAbleToCreateGroupReservationWithRedBookIcon1698(String ratePlanName, String baseAmount,
			String addtionalAdult, String additionalChild, String folioName, String associateSession,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String startRoomNumber,
			String ratePlan, String rateType, String rateAttributes, String interval, String source, String adults,
			String child, String marketSegment, String groupReferral, String groupFirstName, String groupLastName,
			String groupPhone, String groupAddress, String groupCity, String groupCountry, String groupState,
			String groupPostalcode, String blockName, String roomPerNight, String firstName, String lastName,
			String updatedBlockedCount, String roomBlockCount, String lineItemDescription, String roomCharge,
			String postedState, String itemRow, String spanTag, String guestFolio, String pendingState,
			String blueBookClass,
			String redBookClass, String redFirstName, String redLastName,
			String isChecked, String channels,String SeasonName, String seasonDateFormat, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, String specificDate, String dateFormat,String calendarTodayDay,
			String isAdditionalChargesForChildrenAdults, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight,String reason, String	subject) throws InterruptedException, IOException {

		String testName = "VerifyAbleToCreateGroupReservationWithRedBookIcon1698";
		test_description = "Desktop : Advanced group : Verify able to create group reservation with Red Book icon.<br>";
		test_catagory = "Groups";
		ScriptName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		CPReservationPage reservation = new CPReservationPage();
		Folio folio = new Folio();
		RoomClass roomClass = new RoomClass();
		String tempraryRoomClassName = roomClassName;
		String tempraryRateName = ratePlanName;
		String randomNumber = Utility.GenerateRandomNumber();
		roomClassName = roomClassName + randomNumber;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		ratePlanName = ratePlanName + randomNumber;
		folioName = ratePlanName;
		groupLastName = groupLastName + randomNumber;
		String accountName = groupFirstName + groupLastName;
		lastName = lastName + randomNumber;
		//String blueReservationFirstName = "Blue" + firstName;
		String accountNumber = null;
		String saluation = "Mr.";
		String reservationNumber = null;
		Groups group = new Groups();
		AdvGroups advanceGroup = new AdvGroups();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		blockName = blockName + randomNumber;
		String tax = null;
		String expectedRevenue = null;
		redLastName = redLastName + randomNumber;
		
		String beforeAvailableRoom = null;
		String beforePickupValue = null;
		SeasonName = SeasonName + randomNumber;
		String calendarTodayDate = "July/17/2020";
		String beforeReservedCount = null;
		String afterReservedCount = null;
		String beforeAvailableRooms = null;
		String afterAvailableRooms = null;
		String timeZone = "America/New_York";
		app_logs.info("Time Zone " + timeZone);
		app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.navigateRoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to room class", testName, "NavigateRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to room class", testName, "NavigateRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========CREATE A NEW ROOM CLASS==========");
			testSteps.add("==========CREATE A NEW ROOM CLASS==========");

			navigation.clickOnNewRoomClass(driver);
			getTestSteps.clear();
			getTestSteps = roomClass.createRoomClass(driver, roomClassName, roomClassAbbreviation, bedsCount, maxAdults,
					maxPersons, roomQuantity, test, getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Sccessfully Created New RoomClass " + roomClassName + " Abb : " + roomClassAbbreviation);
			app_logs.info("Sccessfully Created New RoomClass" + roomClassName + " Abb : " + roomClassAbbreviation);

			roomClass.closeRoomClassTab(driver);
			testSteps.add("Close created room class tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create New Rate and Attach RoomClass

		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			calendarTodayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// Create New Rate and Attach RoomClass
		try {

			testSteps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", testSteps);

			testSteps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			

			nightlyRate.enterRatePlanName(driver, ratePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, folioName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, rateDescription, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", ratePlanName, testSteps);

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, channels, true, testSteps);
			String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, roomClassName, true, testSteps);
			String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to associate room class in newly created rateplan", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to associate room class in newly created rateplan", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps,
					Utility.parseDate(calendarTodayDate, dateFormat, seasonDateFormat),
					Utility.addDate(1, dateFormat, calendarTodayDate, seasonDateFormat, timeZone));
			nightlyRate.enterSeasonName(driver, testSteps, SeasonName);
			nightlyRate.selectSeasonDays(driver, testSteps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
					isSaturDay, isSunDay);
			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonColor(driver, testSteps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, testSteps,
					isAdditionalChargesForChildrenAdults);
			nightlyRate.enterRate(driver, testSteps, baseAmount, isAdditionalChargesForChildrenAdults, MaxAdults,
					MaxPersons, AdditionalAdultsPerNight, AdditionalChildPerNight);
			nightlyRate.addExtraRoomClassInSeason(driver, testSteps, isAddRoomClassInSeason, ExtraRoomClassesInSeason,
					isAdditionalChargesForChildrenAdults, baseAmount, ExtraRoomClassRatePerNight,
					ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
					ExtraRoomClassAdditionalChildPerNight);
		
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickCompleteChanges(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);

			testSteps.add("RATE PLAN CREATED");
			app_logs.info("RATE PLAN CREATED");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// navigate to Inventory->Rates Grid
		try {
			
			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			}
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			calendarTodayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyTodayButtonExistInCalender(driver, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE CREATING RESERVATION ========");
			testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE CREATING RESERVATION ========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyTodayButtonExistInCalender(driver, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

			int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			int roomIndex = ratesGrid.getRoomClassIndex(driver, roomClassName);
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Expand Room Class");
			app_logs.info("Expand Room Class");
			beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
			testSteps.add("Before Reserved Count : " + beforeReservedCount);
			app_logs.info("Before Reserved Count : " + beforeReservedCount);
			beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			testSteps.add("Before Available Rooms : " + beforeAvailableRooms);
			app_logs.info("Before Available Rooms : " + beforeAvailableRooms);

			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// Reservation
		try {
			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");


			navigation.secondaryGroupsManu(driver);
			testSteps.add("Navigate Groups");
			app_logs.info(" Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", testName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", testName, "NavigateAdvGroup", driver);
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click new account and enter account name", testName,
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Mailing Address
		try {
			getTestSteps.clear();
			getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, groupPhone, groupAddress,
					groupCity, groupState, groupCountry, groupPostalcode);
			testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Reservation and check count
		try {
			app_logs.info("==========GET RESERVATION COUNT==========");
			testSteps.add("==========GET RESERVATION COUNT==========");
			getTestSteps.clear();
			group.clickOnGroupsReservationTab(driver);
			testSteps.addAll(getTestSteps);

			String initialResCount = group.getReservationCount(driver);
			testSteps.add("Initial Reservation Count : " + initialResCount);
			Utility.app_logs.info("Initial Reservation Count : " + initialResCount);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========CREATE NEW BLOCK==========");
			testSteps.add("==========CREATE NEW BLOCK==========");

			getTestSteps.clear();
			getTestSteps = group.navigateRoomBlock(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.ClickNewBlock(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.EnterBlockName(driver, blockName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.ClickOkay_CreateNewBlock(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========SEARCH ROOMS==========");
			testSteps.add("==========SEARCH ROOMS==========");

			getTestSteps.clear();
			getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("MMM dd, yyyy"),
					Utility.GetNextDate(1, "MMM dd, yyyy"));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectRatePlan(driver, ratePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectAdults(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectChilds(driver, child);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.EnterNights(driver, roomPerNight);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.ClickSearchGroup(driver);
			testSteps.addAll(getTestSteps);

			advanceGroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
			advanceGroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, roomClassName);

			getTestSteps.clear();
			getTestSteps = advanceGroup.ClickCreateBlock(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
			testSteps.add("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
			
			String RoomBlocked = group.getRoomBlockedInRoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			testSteps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, roomPerNight, "Failed Room Blocked Not Matched");

			String totalRoomNight = group.getTotalRoomNightsInRoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			testSteps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, roomPerNight, "Failed Room Blocked Not Matched");

			String expectedRevenueDetail = group.getExpectedRevenueInRoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			testSteps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

			String expectedRevenueInfo = group.getExpectedRevenueInGroupInfo(driver);
			Utility.app_logs.info("Group Info Expected Revenue  : " + expectedRevenueInfo);
			testSteps.add("Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

			expectedRevenue = expectedRevenueInfo;
			String pickUpPercentage = group.getPickUpPercentageInRoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			testSteps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			assertEquals(pickUpPercentage, "0", "Failed Pickup Percentage missmatched");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// end here
		try {
			getTestSteps.clear();
			getTestSteps = group.bookIconClick(driver, roomClassName);
			testSteps.addAll(getTestSteps);
			Utility.app_logs.info("Click <b>Blue Book Icon</b> of Room Class  : " + roomClassName);
			testSteps.add("Click <b>Blue Book Icon</b> of Room Class  : " + roomClassName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {
			app_logs.info("==========ENTER RESERVATION DETAILS CREATED FROM <b>BLUE BOOK ICON</b>==========");
			testSteps.add("==========ENTER RESERVATION DETAILS CREATED FROM <b>BLUE BOOK ICON</b>==========");

			String roomnumber = reservation.getRoomNumber(driver, testSteps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			testSteps.add("Verified Room Number is Unassigned");

			getTestSteps.clear();
			getTestSteps = reservation.verifyAssociatedAccount_ResHeader(driver, accountName);
			testSteps.addAll(getTestSteps);
			

			getTestSteps.clear();
			getTestSteps = reservation.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.enter_GuestName(driver, getTestSteps, saluation, firstName, lastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.selectReferral(driver, groupReferral);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps= reservation.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			reservationNumber = reservation.get_ReservationConfirmationNumber(driver, getTestSteps);
			String ReservationStatus = reservation.get_ReservationStatus(driver, getTestSteps);
			Assert.assertEquals(ReservationStatus, "Reserved", "Failed: Reservation Status missmatched");
			reservation.clickCloseReservationSavePopup(driver, getTestSteps);
			

			testSteps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
			getTestSteps.clear();
			getTestSteps = reservation.verifyAssociatedAccount(driver, accountName);
			testSteps.addAll(getTestSteps);
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			navigation.secondaryGroupsManu(driver);
			app_logs.info("Navigate Groups");
			testSteps.add("Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = group.navigateRoomBlock(driver);
			testSteps.addAll(getTestSteps);

			String beforeBookIconClass = null;

			app_logs.info("==========ROOMING LIST DETAILS==========");
			testSteps.add("==========ROOMING LIST DETAILS==========");
			Utility.app_logs.info("Book Room From RoomClass : " + roomClassName);
			testSteps.add("Book Room From RoomClass : " + roomClassName);

			beforePickupValue = group.getPickUpValue(driver, roomClassName);
			Utility.app_logs.info("Pickup Value : " + beforePickupValue);
			testSteps.add("Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, roomClassName);
			roomQuantity = folio.MinseValue(roomQuantity, "1");
			testSteps.add("Expected available rooms: " + roomQuantity);
			testSteps.add("Found : " + beforeAvailableRoom);
			assertEquals(beforeAvailableRoom, roomQuantity, "Failed: Available rooms is mismatching");
			testSteps.add("Verified available rooms");

			beforeBookIconClass = group.getBookIconClass(driver, roomClassName);
			testSteps.add("BookIcon class : " + beforeBookIconClass);
			assertEquals(beforeBookIconClass, redBookClass, "Failed Book Icon is not red");

			String blockedCount = group.getBlocked(driver, roomClassName);
			testSteps.add("Blocked count  : " + blockedCount);
			assertEquals(blockedCount, roomBlockCount, "Failed Room Blocked Not Matched");

			group.bookIconClick(driver, roomClassName);
			Utility.app_logs.info("Click <b>Red Book Icon</b> of Room Class  : " + roomClassName);
			testSteps.add("Click on red book icon from block : " + roomClassName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {
			app_logs.info("==========ENTER RESERVATION DETAILS CREATED FROM <b>RED BOOK ICON</b>==========");
			testSteps.add("==========ENTER RESERVATION DETAILS CREATED FROM <b>RED BOOK ICON</b>==========");

			String roomnumber = reservation.getRoomNumber(driver, testSteps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			testSteps.add("Verified room number is unassigned");

			getTestSteps.clear();
			getTestSteps = reservation.verifyAssociatedAccount_ResHeader(driver, accountName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.enter_GuestName(driver, getTestSteps, saluation, redFirstName, redLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.selectReferral(driver, groupReferral);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps= reservation.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			reservationNumber = reservation.get_ReservationConfirmationNumber(driver, getTestSteps);
			String ReservationStatus = reservation.get_ReservationStatus(driver, getTestSteps);
			Assert.assertEquals(ReservationStatus, "Reserved", "Failed: Reservation Status missmatched");
			reservation.clickCloseReservationSavePopup(driver, getTestSteps);

			testSteps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
			getTestSteps.clear();
			getTestSteps = reservation.verifyAssociatedAccount(driver, accountName);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========VERIFY RESERVATION HISTORY==========");
			testSteps.add("==========VERIFY RESERVATION HISTORY==========");
			getTestSteps.clear();
			getTestSteps = reservation.ClickOnHistory(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservation.verifyResInHistory(driver, reservationNumber);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			folio.FolioTab(driver);
			Utility.app_logs.info("Click Folio Tab");
			testSteps.add("Click Folio Tab");

			tax = folio.getTax(driver, roomCharge);

			testSteps.add("Room Charges Tax : " + tax);
			app_logs.info("Room Charges Tax : " + tax);

			getTestSteps.clear();
			getTestSteps = folio.FolioExist(driver, guestFolio, accountName, true);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========VERIFY LINE ITEM DETAIL IN GUEST FOLIO==========");
			testSteps.add("==========VERIFY LINE ITEM DETAIL IN GUEST FOLIO==========");

			getTestSteps.clear();
			getTestSteps = folio.changeFolioOption(driver, guestFolio);
			testSteps.addAll(getTestSteps);

			folio.VerifyLineItems_State(driver, roomCharge, pendingState, 1);
			testSteps.add("Verify line itme in pending state after added");

			getTestSteps.clear();
			getTestSteps = folio.LineItemDate(driver, roomCharge, 0, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = folio.LineItemCategory(driver, roomCharge, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = folio.getDescroption(driver, roomCharge, lineItemDescription, false, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = folio.LineItemQuentity(driver, roomCharge, "1", 1);
			testSteps.addAll(getTestSteps);

			String getTax = folio.getTax(driver, roomCharge);
			String totalAmountWithTax = folio.AddValue(baseAmount, getTax);
			totalAmountWithTax = "$ " + totalAmountWithTax;

			String getAmount = folio.getAmount(driver, roomCharge, 1);
			getAmount = "$ " + getAmount;
			testSteps.add("Expected amount after added tax: " + totalAmountWithTax);
			testSteps.add("Found : " + getAmount);
			assertEquals(totalAmountWithTax, totalAmountWithTax, "Failed: Amount is mismatching!");
			testSteps.add("Verified amount after included tax");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========VERIFY LINE ITEM DETAIL IN ACCOUNT FOLIO==========");
			testSteps.add("==========VERIFY LINE ITEM DETAIL IN ACCOUNT FOLIO==========");

			getTestSteps.clear();
			getTestSteps = folio.changeFolioOption(driver, accountName);
			testSteps.addAll(getTestSteps);

			int rowsSize = folio.getLineItemRows(driver);
			app_logs.info("BeforeRowsSize: " + rowsSize);
			assertEquals(rowsSize, 0, "Failed: Line item is showing after selected account folio");
			testSteps.add("Verified no lien item is displaying in folio line item after selected " + accountName);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify folio", testName, "FolioVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify folio", testName, "FolioVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Start verify item in item details pop up
		String getAmount = "";
		try {
			app_logs.info("==========VERIFY 'ITEM DETAIL' POPUP LINE ITEMS==========");
			testSteps.add("==========VERIFY 'ITEM DETAIL' POPUP LINE ITEMS==========");

			getTestSteps.clear();
			getTestSteps = folio.changeFolioOption(driver, guestFolio);
			testSteps.addAll(getTestSteps);

			getTestSteps = folio.getDescroption(driver, roomCharge, lineItemDescription, true);
			testSteps.add("Click Line Item Description : " + lineItemDescription);
			app_logs.info("Click Line Item Description : " + lineItemDescription);
			folio.ItemDetails_Category_State(driver, roomCharge, postedState, 1);
			testSteps.add("Verify line itme in pending state");
			app_logs.info("Verify line itme in pending state");

			getTestSteps.clear();
			getTestSteps = folio.DateItemDetails(driver, roomCharge, 1, itemRow);

			getTestSteps.clear();
			getTestSteps = folio.ItemDetailsCategory(driver, roomCharge, 1, itemRow);

			getTestSteps.clear();
			getTestSteps = folio.itemDetails_Descroption(driver, roomCharge, lineItemDescription, false, 1, itemRow,
					spanTag);

			testSteps.add("Successfully Verified Line item exist");
			app_logs.info("Successfully Verified Line item exist");
			getAmount = folio.getAmount_ItemDetails(driver, roomCharge, 1);
			testSteps.add("Expected amount: $ " + baseAmount + ".00");
			app_logs.info("Expected amount: $ " + baseAmount + ".00");
			testSteps.add("Found: " + getAmount);
			app_logs.info("Found: " + getAmount);
			assertEquals(getAmount, "$ " + baseAmount + ".00",
					"Failed: manual override rate value is mismatching in item row");

			testSteps.add("Line Item 'Date : " + Utility.getCurrentDate("E, dd-MMM-yyyy") + "', 'Category : "
					+ roomCharge + "', Description : " + lineItemDescription + "' and Amount : " + baseAmount + "'");
			app_logs.info("Line Item 'Date : " + Utility.getCurrentDate("E, dd-MMM-yyyy") + "', 'Category : "
					+ roomCharge + "', Description : " + lineItemDescription + "' and Amount : " + baseAmount + "'");

			app_logs.info("==========VERIFY 'ITEM DETAIL' POPUP ROOM CHARGES==========");
			testSteps.add("==========VERIFY 'ITEM DETAIL' POPUP ROOM CHARGES==========");

			String itemDetails_RoomChares = folio.Itemdetails_RoomChares(driver);
			testSteps.add("Expected room chares: $ " + baseAmount + ".00");
			app_logs.info("Expected room chares: $ " + baseAmount + ".00");
			testSteps.add("Found: " + itemDetails_RoomChares);
			app_logs.info("Found: " + itemDetails_RoomChares);
			assertEquals(itemDetails_RoomChares, "$ " + baseAmount + ".00",
					"Failed: room charges are mismatching in item details popup");
			testSteps.add("Verified room charges in item detail popup");
			app_logs.info("Verified room charges in item detail popup");

			getTestSteps.clear();
			getTestSteps = folio.CancelPopupButton(driver, true, false);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Folio", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Folio", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Navigate to Groups
		try {
			navigation.secondaryGroupsManu(driver);
			app_logs.info("Navigate Groups");
			testSteps.add("Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate Group reservation tab
		try {
			app_logs.info("==========VERIFY RESERVATION CREATED GROUP==========");
			testSteps.add("==========VERIFY RESERVATION CREATED GROUP==========");
			getTestSteps.clear();
			advanceGroup.clickOnGroupsReservationTab(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.verifyReservationCount(driver, 2);
			testSteps.addAll(getTestSteps);

			String text = group.getReservationDetails(driver, reservationNumber, 1);
			assertEquals(text, redFirstName + " " + redLastName, "Failed: Guest name Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 2);
			assertEquals(text, blockName, "Failed: Block name Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 3);
			assertEquals(text, adults, "Failed: Adults Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 4);
			assertEquals(text, child, "Failed: Children Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 5);
			assertEquals(text, "Reserved", "Failed: Res Status Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 6);
			assertEquals(text, roomClassName, "Failed: Room Class Name Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 7);
			assertEquals(text, Utility.getCurrentDate("MMM d, yyyy"), "Failed: Arrival Date Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 8);
			assertEquals(text, Utility.GetNextDate(1, "MMM d, yyyy"), "Failed: Departure Date Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 9);
			assertEquals(text, roomPerNight, "Failed: Nights Missmatched");

			testSteps.add("Successfully Verified Reservation Exist");
			app_logs.info("Successfully Verified Line item is added");

			testSteps.add("'Reservation Number : " + reservationNumber + "', 'Guest Name : " + redFirstName + " "
					+ redLastName + "', 'BlockName : " + blockName + "', 'Room Class : " + roomClassName + "'");

			testSteps.add("'Adults : " + adults + "', 'Children : " + child + "', 'Arrival Date : "
					+ Utility.getCurrentDate("MMM d, yyyy") + "', 'Departure Date : "
					+ Utility.GetNextDate(1, "MMM d, yyyy") + "'");
			app_logs.info("'Adults : " + adults + "', 'Children : " + child + "', 'Arrival Date : "
					+ Utility.getCurrentDate("MMM d, yyyy") + "', 'Departure Date : "
					+ Utility.GetNextDate(1, "MMM d, yyyy") + "'");

			group.ClickReservationName_VerifyPopup(driver, reservationNumber, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// here to verify block verification

		try {
			app_logs.info("==========VERIFY BLOCK DETAILS AFTER CREATED RESERVATION==========");
			testSteps.add("==========VERIFY BLOCK DETAILS AFTER CREATED RESERVATION==========");

			getTestSteps.clear();
			getTestSteps = group.navigateRoomBlock(driver);
			testSteps.addAll(getTestSteps);

			String todayDate = ESTTimeZone.DateFormateForLineItem(0);
			todayDate = Utility.parseDate(todayDate, "MM/dd/yyyy", "MMM d, yyyy");
			String getRelaseDate = group.getReleaseDateInRoomBlockDetatil(driver);
			testSteps.add("Expected realse date: " + todayDate);
			testSteps.add("Found: " + getRelaseDate);
			assertEquals(getRelaseDate, todayDate, "Failed: Realse date is mismatching");

			String totalRoomNight = group.getTotalRoomNightsInRoomBlockDetail(driver);
			testSteps.add("Expecetd total room nights: " + roomPerNight);
			testSteps.add("Found: " + totalRoomNight);
			assertEquals(totalRoomNight, roomPerNight, "Failed: per night is mismathching");
			testSteps.add("Verified room nights");

			expectedRevenue = folio.AddValue(baseAmount, tax);
			expectedRevenue = folio.AddValue(expectedRevenue, expectedRevenue);
			String expectedRevenueDetail = group.getExpectedRevenueInRoomBlockDetail(driver);
			testSteps.add("Expected revenue: $" + expectedRevenue);
			testSteps.add("Found: $" + expectedRevenueDetail);
			assertEquals(expectedRevenueDetail, expectedRevenue, "Faied: Expected revenue is mismatching");
			testSteps.add("Verified expected revenue");

			String getPickedupRevenue = group.getPickedupRevenueInRoomBlockDetail(driver);
			testSteps.add("Pickedup revenue: $" + expectedRevenue);
			testSteps.add("Found: $" + getPickedupRevenue);
			assertEquals(getPickedupRevenue, expectedRevenue, "Faied: Pickedup revenue is mismatching");
			testSteps.add("Verified pickedup revenue");

			String afterExpectedRevenue = group.getExpectedRevenueInGroupInfo(driver);
			Utility.app_logs.info("Expected revenue in block info: " + expectedRevenue);
			testSteps.add("Expected revenue in block info  : " + getRelaseDate);
			testSteps.add("Found: $" + afterExpectedRevenue);
			assertEquals(afterExpectedRevenue, expectedRevenue, "Failed Expected revenue is mismatching in block info");

			String pickUpPercentage = group.getPickUpPercentageInRoomBlockDetatil(driver);
			testSteps.add("Expected pickup percentage: 200");
			testSteps.add("Found: " + pickUpPercentage);
			assertEquals(pickUpPercentage, "200", "Failed Pickup Percentage missmatched");
			testSteps.add("Verified pickup percentage");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room block details", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room block details", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========ROOMING LIST DETAILS AFTER CREATED NEW RESERVATION==========");
			testSteps.add("==========ROOMING LIST DETAILS AFTER CREATED NEW RESERVATION==========");

			String afterPickupValue = group.getPickUpValue(driver, roomClassName);
			testSteps.add("Expected pickup: 2");
			testSteps.add("Found: " + afterPickupValue);
			assertEquals(afterPickupValue, "2", "Failed: Pickup value is mismatching");
			testSteps.add("Verified pickup value");

			String afterAvailableRoom = group.getAvailableRooms(driver, roomClassName);
			roomQuantity = folio.MinseValue(roomQuantity, "1");
			testSteps.add("Expected available rooms: " + roomQuantity);
			testSteps.add("Found : " + afterAvailableRoom);
			assertEquals(afterAvailableRoom, roomQuantity, "Failed: Available rooms is mismatching");
			testSteps.add("Verified available rooms");

			String bookClass = group.getBookIconClass(driver, roomClassName);
			assertEquals(bookClass, redBookClass, "Failed Room book Icon is still blue");
			testSteps.add("Verified room book icon color is displying red");

			String blockedCount = group.getBlocked(driver, roomClassName);
			testSteps.add("Expected block count: " + roomBlockCount);
			testSteps.add("Found: " + blockedCount);
			assertEquals(blockedCount, roomBlockCount, "Failed Room Blocked Not Matched");
			testSteps.add("Verified block count");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rooming list", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rooming list", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// verify
		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING RESERVATION ========");
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");
			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			}
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyTodayButtonExistInCalender(driver, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

			int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			int roomIndex;
			try {
				roomIndex = ratesGrid.getRoomClassIndex(driver, roomClassName);
			} catch (Exception f) {
				driver.navigate().refresh();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyTodayButtonExistInCalender(driver, true);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);

				dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				roomIndex = ratesGrid.getRoomClassIndex(driver, roomClassName);
			}
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Expand Room Class");
			app_logs.info("Expand Room Class");
			afterReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
			testSteps.add("After Reserved Count : " + afterReservedCount);
			app_logs.info("After Reserved Count : " + afterReservedCount);
			assertEquals(afterReservedCount, Integer.toString(Integer.parseInt(beforeReservedCount) + 2),
					"Failed : Reserved count is not increased after creating reservation");

			afterAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			testSteps.add("After Available Rooms : " + afterAvailableRooms);
			app_logs.info("After Available Rooms : " + afterAvailableRooms);
			assertEquals(afterAvailableRooms, Integer.toString(Integer.parseInt(beforeAvailableRooms) - 2),
					"Failed : Available Rooms is not decreased after creating reservation");
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}


		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("CreateResFromRedIcon1698", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
