package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerificationOfCheckInCheckOutRuleCreateGroupReservationWithBlueBookIcon extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	public static String testDescription = "";
	public static String testCategory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private ArrayList<String> getDataOfHash(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		ArrayList<String> values = new ArrayList<String>();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getValue().toString().equalsIgnoreCase("YES")) {
				testSteps.add("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
				app_logs.info("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
				values.add(mentry.getKey().toString());

			}
		}
		return values;
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verificationOfCheckInCheckOutRuleCreateGroupReservationWithBlueBookIcon(String marketSegment,
			String groupReferral, String groupFirstName, String groupLastName, String groupPhn, String groupAddress,
			String groupCity, String groupCountry, String groupState, String groupPostalcode, String blockName,
			String roomPerNight, String firstName, String lastName, String updatedBlockedCount, String roomBlockCount,
			String rateName, String baseAmount, String addtionalAdult, String additionalChild, String displayName,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String ratePlan,
			String rateType, String rateAttributes, String source, String adults, String child, String seasonName,
			String daysOfWeekCheckIn, String daysOfWeekCheckOut) throws InterruptedException, IOException {

		String testName = "VerificationOfCheckInCheckOutRuleCreateGroupReservationWithBlueBookIcon";
		testDescription = "Desktop : Advanced group : Create group reservation with Blue Book icon.<br>";
		testCategory = "Groups";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		CPReservationPage reservationPage = new CPReservationPage();
		Login login = new Login();
		Rules rules = new Rules();
		RoomClass roomClass = new RoomClass();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		String randomNumber = Utility.GenerateRandomNumber();
		String accountName = groupFirstName + groupLastName;
		lastName = lastName + randomNumber;
		String blueResFirstName = "Blue" + firstName;
		String accountNumber = null;
		String saluation = "Mr.";
		String reservationNumber = null;
		Groups group = new Groups();
		AdvGroups advgroup = new AdvGroups();
		blockName = blockName + randomNumber;
		String timeZone = "America/New_York";
		app_logs.info("Time Zone " + timeZone);
		app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));

		roomClassName = roomClassName + randomNumber;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		rateName = rateName + randomNumber;
		displayName = rateName;

		String seasonStartDate = "";
		String seasonEndDate = "";
		String dateFormat = "MM/dd/yyyy";

		String checkInDate = Utility.getCurrentDate(dateFormat);

		String checkOutDate = Utility.GetNextDate(1, dateFormat);

		HashMap<String, String> checkInRules = new HashMap<String, String>();
		HashMap<String, String> checkoutRules = new HashMap<String, String>();
		ArrayList<Boolean> daysOfWeekCheckInList = new ArrayList<Boolean>();
		ArrayList<Boolean> daysOfWeekCheckOutList = new ArrayList<Boolean>();

		for (int i = 0; i < daysOfWeekCheckIn.split("\\|").length; i++) {

			daysOfWeekCheckInList.add(Boolean.parseBoolean(daysOfWeekCheckIn.split("\\|")[i]));
			daysOfWeekCheckOutList.add(Boolean.parseBoolean(daysOfWeekCheckOut.split("\\|")[i]));

		}

		String calendarTodayDate = Utility.getCurrentDate(dateFormat, timeZone);

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login.login(driver, "https://www.app.qainnroad.com", "autocp", "autouser", "Auto@123");
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

			app_logs.info("==========CREATE A NEW ROOM CLASS==========");
			testSteps.add("==========CREATE A NEW ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.RoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

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
		ArrayList<String> datesCheckInRuleApplied = new ArrayList<String>();
		ArrayList<String> datesCheckOutRuleApplied = new ArrayList<String>();

		try {
			testSteps.add("==========CREATE A NEW RATE==========");
			app_logs.info("==========CREATE A NEW RATE==========");
			navigation.cpReservationBackward(driver);
			navigation.Inventory_Backward(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");

			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlan);

			nightlyRate.enterRatePlanName(driver, rateName, getTestSteps);
			nightlyRate.enterRateFolioDisplayName(driver, displayName, getTestSteps);
			nightlyRate.enterRatePlanDescription(driver, rateDescription, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.selectChannels(driver, source, true, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.selectRoomClasses(driver, roomClassName, true, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickCreateSeason(driver, getTestSteps);
			seasonStartDate = Utility.getDateFromCurrentDate(0);
			seasonEndDate = Utility.getDateFromCurrentDate(20);
			nightlyRate.selectSeasonDates(driver, getTestSteps, seasonStartDate, seasonEndDate);
			nightlyRate.enterSeasonName(driver, getTestSteps, seasonName);
			nightlyRate.clickCreateSeason(driver, getTestSteps);
			nightlyRate.selectSeasonColor(driver, getTestSteps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, getTestSteps, "No");
			nightlyRate.enterRate(driver, getTestSteps, baseAmount, addtionalAdult, maxAdults, maxPersons,
					addtionalAdult, additionalChild);

			nightlyRate.clickRulesRestrictionOnSeason(driver, getTestSteps);

			getTestSteps.clear();
			getTestSteps = rules.checkInRuleCheckBox(driver, true);
			testSteps.addAll(getTestSteps);
			rules.checkInDaysOfWeek(driver, daysOfWeekCheckInList);

			getTestSteps.clear();
			getTestSteps = rules.checkOutRuleCheckBox(driver, true);
			testSteps.addAll(getTestSteps);
			rules.checkOutDaysOfWeek(driver, daysOfWeekCheckOutList);

			nightlyRate.clickSaveSason(driver, getTestSteps);
			nightlyRate.clickCompleteChanges(driver, getTestSteps);
			nightlyRate.clickSaveAsActive(driver, getTestSteps);

			testSteps.add("New Rate '" + rateName + "' Created & Verified ");
			app_logs.info("New Rate '" + rateName + "' Created & Verified");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, getTestSteps);
			ratesGrid.expandRoomClassWithoutMinus(driver, getTestSteps, roomClassName);
			ratesGrid.expandChannelWithoutMinus(driver, getTestSteps, roomClassName, source);
			checkInRules = ratesGrid.getCheckInCheckOutRulesOfChannel(driver,
					ESTTimeZone.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					ESTTimeZone.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"), roomClassName, source,
					ratesConfig.getProperty("checkinRule"));
			checkoutRules = ratesGrid.getCheckInCheckOutRulesOfChannel(driver,
					ESTTimeZone.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					ESTTimeZone.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"), roomClassName, source,
					ratesConfig.getProperty("checkoutRule"));
			datesCheckInRuleApplied = getDataOfHash(checkInRules);
			datesCheckOutRuleApplied = getDataOfHash(checkoutRules);

			System.out.println("CheckInRule:" + checkInRules);
			System.out.println("checkoutRules:" + checkoutRules);

			System.out.println("Hash1:" + datesCheckInRuleApplied);
			System.out.println("Has2:" + datesCheckOutRuleApplied);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Reservation
		try {
			driver.navigate().refresh();
			navigation.Reservation_Backward_4(driver);
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
			getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, groupPhn, groupAddress,
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
			getTestSteps = group.SelectRatePlan(driver, rateName);
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

			advgroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
			advgroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, roomClassName);

			advgroup.ClickCreateBlock(driver, getTestSteps);

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

		String beforeAvailableRoom = null;
		String beforePickupValue = null;
		try {
			group.bookIconClick(driver, roomClassName);
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
			String roomnumber = reservationPage.getRoomNumber(driver, getTestSteps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			testSteps.add("Verified New Reservation Page is Opened");
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
		// navigate to Inventory->Rates Grid-> Availability and verify roomclass
		// availability

		try {

			testSteps.add(
					"=================== CREATE Reservation & VERIFY CHECKIN/CHECKOUT RULES ======================");
			app_logs.info(
					"=================== CREATE Reservation & VERIFY CHECKIN/CHECKOUT RULES ======================");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInCheckOutRuleStatusVerfication(driver,
					ESTTimeZone.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
					ESTTimeZone.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy"), adults, child, rateName,
					roomClassName, datesCheckInRuleApplied, datesCheckOutRuleApplied);
			testSteps.addAll(getTestSteps);

			testSteps.add("CheckIn CheckOut Rule Verified ");
			app_logs.info("CheckIn CheckOut Rule Verified");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try

		{
			driver.navigate().refresh();

			testSteps.add("=================== DELETE RATEPLAN ======================");
			app_logs.info("===================  DELETE RATEPLAN  ======================");

			navigation.Inventory_Backward(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");

			ratesGrid.clickRatePlanArrow(driver, getTestSteps);
			ratesGrid.searchRatePlan(driver, rateName);
			nightlyRate.deleteNightlyRatePlan(driver, rateName, "Delete", getTestSteps);
			testSteps.add("=================== DELETE ROOMCLASS ======================");
			app_logs.info("===================  DELETE ROOMCLASS  ======================");
			driver.navigate().refresh();
			navigation.Reservation_Backward_4(driver);
			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.RoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");
			roomClass.searchButtonClick(driver);
			boolean isRoomClassShowing = roomClass.searchClass(driver, roomClassName);
			app_logs.info("Search");
			if (isRoomClassShowing) {

				roomClass.deleteRoomClass(driver, roomClassName);
				testSteps.add("Delete room class successfully");
				app_logs.info("Delete room class successfully");
			} else {
				testSteps.add("No room class exist");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("CCRuleVerifyCreateResBlueIcon", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
