//package com.innroad.inncenter.tests;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.SkipException;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import com.innroad.inncenter.pageobjects.CPReservationPage;
//import com.innroad.inncenter.pageobjects.Distribution;
//import com.innroad.inncenter.pageobjects.Navigation;
//import com.innroad.inncenter.pageobjects.OverView;
//import com.innroad.inncenter.pageobjects.Rate;
//import com.innroad.inncenter.pageobjects.RatesGrid;
//import com.innroad.inncenter.pageobjects.RoomClass;
//import com.innroad.inncenter.pageobjects.Rules;
//import com.innroad.inncenter.pageobjects.Season;
//import com.innroad.inncenter.pageobjects.Tapechart;
//import com.innroad.inncenter.testcore.TestCore;
//import com.innroad.inncenter.utils.RetryFailedTestCases;
//import com.innroad.inncenter.utils.Utility;
//import com.innroad.inncenter.webelements.Elements_RatesGrid;
//
//public class AvailabilityVerificationInncenterRatesGridV2Backup extends TestCore {
//
//	// Automation-1694
//	private WebDriver driver = null;
//
//	ArrayList<String> testSteps = new ArrayList<>();
//	ArrayList<String> getTestSteps = new ArrayList<>();
//	ArrayList<String> testName = new ArrayList<>();
//	ArrayList<String> testCategory = new ArrayList<>();
//	ArrayList<String> testDescription = new ArrayList<>();
//	String scriptName = "AvailabilityVerificationInncenterRatesGridV2";
//	public static String test_description = "";
//	public static String test_category = "";
//
//	@BeforeTest(alwaysRun = true)
//	public void checkRunMode() {
//
//		String testName = this.getClass().getSimpleName().trim();
//		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
//		if (!Utility.isExecutable(testName, excel))
//			throw new SkipException("Skipping the test - " + testName);
//	}
//
//	@Test(dataProvider = "getData", groups = "Inventory")
//	public void AvailabilityCheckRatesGridV2(String day, String totalOccupancy, String paceVsLastYear,
//			String addRatePlan, String bulkUpdate, String rates, String availability, String rules,
//			String nightlyRatePlan, String drivedRatePlan, String pakageRatePlan, String intervalRatePlan,
//			String bulkUpdateRateTitle, String bulkUpdateAvailabilityTitle, String bulkUpdateRulesTitle,
//			String weekendColor, String otherweekDayColor, String nextDays, String totalDaysDisplayed,
//			String maximumRoomClassesToExpand, String source, String dateFormat,
//			String calendarTodayDay, String blockedStatus, String greenStatus, String RatePlanName, String RoomClasses, String startDate, String endDate)
//			throws InterruptedException, IOException {
//
//		test_description = "Rates V2 - Rates Grid - Availability Validation<br>"
//				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1694' target='_blank'>"
//				+ "Click here to open Jira: AUTOMATION-1694</a>";
//
//		test_category = "Rates Grid";
//		testName.add(scriptName);
//		testDescription.add(test_description);
//		testCategory.add(test_category);
//
//		app_logs.info("##################################################################################");
//		app_logs.info("EXECUTING: " + scriptName + " TEST.");
//		app_logs.info("##################################################################################");
//
//		OverView overView = new OverView();
//		Navigation navigation = new Navigation();
//		Distribution distribution = new Distribution();
//		RatesGrid ratesGrid = new RatesGrid();
//		RoomClass roomClass = new RoomClass();
//		ArrayList<String> getRoomClasses = new ArrayList<>();
//		ArrayList<String> getRoomsNumber = new ArrayList<>();
//		ArrayList<String> roomClassesAbbreviation = new ArrayList<>();
//		ArrayList<String> activeChannelsList = new ArrayList<String>();
//		Elements_RatesGrid ratesGridElements = new Elements_RatesGrid(driver);
//		String timeZone = "America/New_York";
//		app_logs.info("Time Zone " + timeZone);
//		app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));
//
//		String propertyName = Utility.WestPointInnProperty;
//		String todayDate = "";
//		String roomTotalValue = null;
//		String roomOOOValue = null;
//		String roomReservedValue = null;
//		String roomAvailableValue = null;
//		String roomAvailablePercentage = null;
//		String roomTotalValueData = null;
//		String roomOOOValueData = null;
//		String roomReservedValueData = null;
//		String roomAvailableValueData = null;
//		String roomAvailablePercentageData = null;
//		String occupancyPercentageValue = null;
//		String paceVsLastYesrValue = null;
//		String occupancyPercentageValueData = null;
//		String paceVsLastYesrValueData = null;
//		String roomStatus = null;
//		String roomStatusData = null;
//		String calendarTodayDate = null;
//		try {
//			if (!Utility.insertTestName.containsKey(testName)) {
//				Utility.insertTestName.put(testName, testName);
//				Utility.reTry.put(testName, 0);
//			} else {
//				Utility.reTry.replace(testName, 1);
//			}
//			
//			// Get CheckIn, CheckOut and TaskDueOn Date
//			if (!(Utility.validateInput(CheckInDate))) {
//			    for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
//			        checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
//			        checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
//			        }
//			} else {
//			    checkInDates = Utility.splitInputData(CheckInDate);
//			    checkOutDates = Utility.splitInputData(CheckOutDate);
//			}
//			CheckInDate = checkInDates.get(0);
//			CheckOutDate = checkOutDates.get(0);
//			TaskDueon = CheckOutDate;
//
//			app_logs.info(CheckInDate);
//			app_logs.info(CheckOutDate);
//			date=Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yy");
//			app_logs.info(date);
//			
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
//				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName, "ChekIn and CheckOut date", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName, "ChekIn and CheckOut date", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//		//Login
//		try {
//			driver = getDriver();
//			login_CP(driver);
//			testSteps.add("Entered appication URL : " + TestCore.envURL);
//			testSteps.add("Logged into the application ");
//			app_logs.info("Logged into the application");
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//
//
//		try {
//
//			testSteps.add("==========Navigate to Rates Grid=========");
//			app_logs.info("==========Navigate to Rates GridL==========");
//
//			navigation.Inventory(driver);
//			testSteps.add("Navigate Setup");
//			navigation.Rates_Grid(driver);
//			ratesGrid.searchRatePlan(driver, RatePlanName);
//			testSteps.add("Navigate Rates Grid");
//			app_logs.info("Navigate Rates Grid");
//			try {
//				getTestSteps.clear();
//				//getTestSteps = ratesGrid.clickOnAvailability(driver);
//				testSteps.addAll(getTestSteps);
//			} catch (Exception f) {
//
//				navigation.Rates_Grid(driver);
//				getTestSteps.clear();
//				getTestSteps = ratesGrid.clickOnAvailability(driver);
//				testSteps.addAll(getTestSteps);
//			}
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to Navigate Rates Grid", scriptName, "Navigation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to Navigate Rates Grid", scriptName, "Navigation", driver);
//			} else {
//
//				Assert.assertTrue(false);
//			}
//		}
//
//
//		// verify
//		try {
//			app_logs.info(
//					"========== VERIFY ALL ACTIVE ROOM CLASSES SHOULD BE SHOWN ONE BELOW ANOTHER (IN ASCENDING ORDER) ==========");
//			testSteps.add(
//					"========== VERIFY ALL ACTIVE ROOM CLASSES SHOULD BE SHOWN ONE BELOW ANOTHER (IN ASCENDING ORDER) ==========");
//			getTestSteps.clear();
//			//getTestSteps = ratesGrid.verifyGivenRoomClasses(driver, RoomClasses, getTestSteps);
//			ratesGrid.getRatesOfRoomClass(driver, startDate, endDate, RoomClasses);
//			testSteps.addAll(getTestSteps);
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//
//				Assert.assertTrue(false);
//			}
//		}
//
//		// verify
//		try {
//			app_logs.info(
//					"== VERIFY ON CLICK ON + OF A ROOM CLASS TOTAL, OOO, RESERVED, AVAILABLE LABELS ALONG WITH CORRECT VALUES TO BE SHOWN ===");
//			testSteps.add(
//					"=== VERIFY ON CLICK ON + OF A ROOM CLASS TOTAL, OOO, RESERVED, AVAILABLE LABELS ALONG WITH CORRECT VALUES TO BE SHOWN ===");
//
//			for (int i = 0; i < getRoomClasses.size(); i++) {
//				roomTotalValueData = "Total------:";
//				roomOOOValueData = "OOO--------: ";
//				roomReservedValueData = "Reserved---: ";
//				roomAvailableValueData = "Available--: ";
//				roomAvailablePercentageData = "Percentage-: ";
//				roomStatusData = "innCenter--: ";
//				if (i == Integer.parseInt(maximumRoomClassesToExpand)) {
//					break;
//				}
//				testSteps.add("========= VERIFY Room Class : '" + getRoomClasses.get(i) + "' ===========");
//				app_logs.info("========= VERIFY Room Class : '" + getRoomClasses.get(i) + "' ===========");
//				ratesGrid.expandRoomClass(driver, i);
//				testSteps.add("Expand Room Class");
//				app_logs.info("Expand Room Class");
//				app_logs.info("Expected Room Total : " + getRoomsNumber.get(i));
//				for (int j = 0; j < Integer.parseInt(totalDaysDisplayed); j++) {
//					roomTotalValue = ratesGrid.getRoomClassDataValue(driver, j, "Total");
//					roomOOOValue = ratesGrid.getRoomClassDataValue(driver, j, "OOO");
//					roomReservedValue = ratesGrid.getRoomClassDataValue(driver, j, "Reserved");
//					roomAvailableValue = ratesGrid.getRoomClassDataValue(driver, j, "Available");
//					roomAvailablePercentage = ratesGrid.getRoomClassDataValue(driver, j, getRoomClasses.get(i));
//					roomStatus = ratesGrid.getRoomStatus(driver, j, source, getRoomClasses.get(i));
//
//					roomTotalValueData = roomTotalValueData + "--" + roomTotalValue;
//					roomOOOValueData = roomOOOValueData + "--" + roomOOOValue;
//					roomReservedValueData = roomReservedValueData + "--" + roomReservedValue;
//					roomAvailableValueData = roomAvailableValueData + "--" + roomAvailableValue;
//					roomAvailablePercentageData = roomAvailablePercentageData + "-" + roomAvailablePercentage;
//					roomStatusData = roomStatusData + "--" + roomStatus;
//					assertEquals(roomTotalValue, getRoomsNumber.get(i), "Failed: Room Total Missmatched");
//					String calculatedValue = Integer.toString(Integer.parseInt(roomOOOValue)
//							+ Integer.parseInt(roomReservedValue) + Integer.parseInt(roomAvailableValue));
//					assertEquals(calculatedValue, roomTotalValue,
//							"Failed: Sum of all OOO, Reserved and Available values is not equal to total");
//					assertEquals(roomAvailablePercentage,
//							Integer.toString(
//									(Integer.parseInt(roomReservedValue) / Integer.parseInt(roomTotalValue)) * 100)
//									+ "%",
//							"Failed: Percentafge value not matched");
//				}
//
//				testSteps.add(roomAvailablePercentageData);
//				app_logs.info(roomAvailablePercentageData);
//				testSteps.add(roomTotalValueData);
//				app_logs.info(roomTotalValueData);
//				testSteps.add(roomOOOValueData);
//				app_logs.info(roomOOOValueData);
//				testSteps.add(roomReservedValueData);
//				app_logs.info(roomReservedValueData);
//				testSteps.add(roomAvailableValueData);
//				app_logs.info(roomAvailableValueData);
//				testSteps.add(roomStatusData);
//				app_logs.info(roomStatusData);
//
//				ratesGrid.expandRoomClass(driver, i);
//				testSteps.add("Reduce Room Class");
//				app_logs.info("Reduce Room Class");
//			}
//			testSteps.add(
//					"Successfully Verified On click on + of a Room class Availability 0%, Total, OOO, Reserved, Available labels along with correct values are displayed.");
//			app_logs.info(
//					"Successfully Verified On click on + of a Room class Total, OOO, Reserved, Available labels along with correct values are displayed.");
//			testSteps.add("Successfully Verified Room class % matches with Total, OOO, Reserved and Available counts");
//			app_logs.info("Successfully Verified Room class % matches with Total, OOO, Reserved and Available counts");
//			testSteps.add("Successfully Verified Room Status(Available(Green Dots) or Blackout Room)");
//			app_logs.info("Successfully Verified Room Status(Available(Green Dots) or Blackout Room)");
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//
//				Assert.assertTrue(false);
//			}
//		}
//
//		// verify
//		try {
//			app_logs.info(
//					"========== VERIFY THE BACKGROUND COLOR FOR WEEK DAYS(LIGHT GREY) AND WEEKENDS(DARK GREY) ==========");
//			testSteps.add(
//					"========== VERIFY THE BACKGROUND COLOR FOR WEEK DAYS(LIGHT GREY) AND WEEKENDS(DARK GREY) ==========");
//			ratesGrid.verifyDatesBackGroungColor(driver, weekendColor, otherweekDayColor, getTestSteps);
//			app_logs.info("Successfully verified the background color");
//			testSteps.add("Successfully verified the background color");
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//
//				Assert.assertTrue(false);
//			}
//		}
//
//		// verify
//		try {
//			app_logs.info("=========== VERIFY ACTIVE CHANNELS IN ROOM CLASS ==========");
//			testSteps.add("=========== VERIFY ACTIVE CHANNELS IN ROOM CLASS ===========");
//
//			for (int i = 0; i < getRoomClasses.size(); i++) {
//				if (i == Integer.parseInt(maximumRoomClassesToExpand)) {
//					break;
//				}
//				testSteps.add("========= VERIFY Room Class : '" + getRoomClasses.get(i) + "' ===========");
//				app_logs.info("========= VERIFY Room Class : '" + getRoomClasses.get(i) + "' ===========");
//				ratesGrid.expandRoomClass(driver, i);
//				testSteps.add("Expand Room Class");
//				app_logs.info("Expand Room Class");
//				app_logs.info("Expected Room Total : " + getRoomsNumber.get(i));
//
//				for (int j = 0; j < activeChannelsList.size(); j++) {
//					int roomIndex = ratesGrid.getRoomClassIndex(driver, getRoomClasses.get(i));
//					getTestSteps.clear();
//					getTestSteps = ratesGrid.verifyChannels(driver, getRoomClasses.get(roomIndex), activeChannelsList,
//							getTestSteps);
//					testSteps.addAll(getTestSteps);
//					app_logs.info("=========== FOR A SPECIFIC DATE MAKE CHANNEL(" + activeChannelsList.get(j)
//							+ ") BLACKOUT THEN AVAILABLE AND VERIFY ==========");
//					testSteps.add("=========== FOR A SPECIFIC DATE MAKE CHANNEL(" + activeChannelsList.get(j)
//							+ ") BLACKOUT THEN AVAILABLE AND VERIFY ===========");
//
//					ratesGrid.activeOrBlackoutChannel(driver, calendarTodayDate, dateFormat,
//							getRoomClasses.get(roomIndex), activeChannelsList.get(j), blockedStatus);
//					app_logs.info("Blackout room for Date '" + calendarTodayDate + "' and Source '" + activeChannelsList.get(j) + "'");
//					testSteps.add("Blackout room for Date '" + calendarTodayDate + "' and Source '" + activeChannelsList.get(j) + "'");
//					assertEquals(
//							ratesGrid.getRoomStatus(driver, calendarTodayDate, dateFormat, activeChannelsList.get(j),
//									getRoomClasses.get(roomIndex)),
//							blockedStatus, "Failed : Room Status is not Blacked Out");
//					app_logs.info(
//							"Successfully verified blackout room and 'B' is displaying under room percentage value");
//					testSteps.add(
//							"Successfully verified blackout room and 'B' is displaying under room percentage value");
//					ratesGrid.activeOrBlackoutChannel(driver, calendarTodayDate, dateFormat,
//							getRoomClasses.get(roomIndex), activeChannelsList.get(j), greenStatus);
//					app_logs.info(
//							"Make room Available  for Date '" + calendarTodayDate + "' and Source '" + activeChannelsList.get(j) + "'");
//					testSteps.add(
//							"Make room Available  for Date '" + calendarTodayDate + "' and Source '" + activeChannelsList.get(j) + "'");
//					assertEquals(
//							ratesGrid.getRoomStatus(driver, calendarTodayDate, dateFormat, activeChannelsList.get(j),
//									getRoomClasses.get(roomIndex)),
//							greenStatus, "Failed : Room Status is not Available");
//					app_logs.info("Successfully verified Green Dot Appeared showing room as available");
//					testSteps.add("Successfully verified Green Dot Appeared showing room as available");
//				}
//
//				ratesGrid.expandRoomClass(driver, i);
//				testSteps.add("Reduce Room Class");
//				app_logs.info("Reduce Room Class");
//			}
//			testSteps.add(
//					"Successfully Verified On click on + of a Room class Availability 0%, Total, OOO, Reserved, Available labels along with correct values are displayed.");
//			app_logs.info(
//					"Successfully Verified On click on + of a Room class Total, OOO, Reserved, Available labels along with correct values are displayed.");
//			testSteps.add("Successfully Verified Room class % matches with Total, OOO, Reserved and Available counts");
//			app_logs.info("Successfully Verified Room class % matches with Total, OOO, Reserved and Available counts");
//			testSteps.add("Successfully Verified Room Status(Available(Green Dots) or Blackout Room)");
//			app_logs.info("Successfully Verified Room Status(Available(Green Dots) or Blackout Room)");
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//
//				Assert.assertTrue(false);
//			}
//		}
//
//		// verify
//		try {
//			app_logs.info(
//					"========== VERIFY FOR A SPECIFIC DATE MAKE A ROOM CLASS CHANNEL AVAILABLE OR BLACKOUT AND VERIFY ==========");
//			testSteps.add(
//					"========== VERIFY FOR A SPECIFIC DATE MAKE A ROOM CLASS CHANNEL AVAILABLE OR BLACKOUT AND VERIFY ==========");
//
//			int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
//			int roomIndex = ratesGrid.getRoomClassIndex(driver, getRoomClasses.get(0));
//			ratesGrid.expandRoomClass(driver, roomIndex);
//			testSteps.add("Expand Room Class");
//			app_logs.info("Expand Room Class");
//			String status = "*";
//			ratesGrid.changeRoomStatus(driver, dateIndex, source, getRoomClasses.get(roomIndex), blockedStatus);
//			app_logs.info("Make room Available (if not Available) for Date '" + calendarTodayDate + "' and Source '"
//					+ source + "'");
//			testSteps.add("Make room Available (if not Available) for Date '" + calendarTodayDate + "' and Source '"
//					+ source + "'");
//			assertEquals(ratesGrid.getRoomStatus(driver, dateIndex, source, getRoomClasses.get(roomIndex)),
//					blockedStatus, "Failed : Room Status is not Available");
//			app_logs.info("Successfully verified Green Dot Appeared showing room as available");
//			testSteps.add("Successfully verified Green Dot Appeared showing room as available");
//			status = "B";
//			ratesGrid.changeRoomStatus(driver, dateIndex, source, getRoomClasses.get(roomIndex), greenStatus);
//			app_logs.info("Blackout room");
//			testSteps.add("Blackout room");
//			assertEquals(ratesGrid.getRoomStatus(driver, dateIndex, source, getRoomClasses.get(roomIndex)), greenStatus,
//					"Failed : Room Status is not Available");
//			app_logs.info("Successfully verified blackout room and 'B' is displaying under room percentage value");
//			testSteps.add("Successfully verified blackout room and 'B' is displaying under room percentage value");
//			ratesGrid.expandRoomClass(driver, roomIndex);
//			testSteps.add("Reduce Room Class");
//			app_logs.info("Reduce Room Class");
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
//			} else {
//
//				Assert.assertTrue(false);
//			}
//		}
//
//		try {
//
//			testSteps.add("=============VERIFICATION OF AVAILABILITY TABS AND BUTTONS================");
//
//			ratesGrid.clickAndVerifySettingButton(driver);
//
//			ratesGrid.clickAndVerifyAddRatePlanButton(driver);
//
//			ratesGrid.clickAndVerifyBulkUpdateButton(driver);
//			testSteps.add("Verified Settings, Add Rate Plan, Bulk Update buttons are visible and enabled");
//			ratesGrid.clickAndVerifySettingButton(driver);
//			ratesGrid.verifyHeadingAvailabilitySettingMenu(driver, "Availability");
//			ratesGrid.verifyHeadingRatesSettingMenu(driver, "Rates");
//			ratesGrid.verifyAvailabilityToggleTextSettingMenu(driver, "Show room availability");
//			ratesGrid.verifyRatesToggleTextSettingMenu(driver, "Show additional adult and child");
//			ratesGrid.verifyToggleButtonAvailablity(driver);
//			testSteps.add("Verified On click of Settings - Show 2 menus (Availability and Rates)");
//			testSteps.add("Successfully verified Availability and Rates heading and texts and toggle buttons");
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnAvailability(driver);
//			testSteps.addAll(getTestSteps);
//
//			String getTabDetails = ratesGrid.getDayTabText(driver);
//			testSteps.add("Expexted : " + day);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, day, "Failed to verify day");
//			testSteps.add("Verified Day");
//
//			getTabDetails = ratesGrid.getTotalOccupancyTabText(driver);
//			testSteps.add("Expexted : " + totalOccupancy);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, totalOccupancy, "Failed to verify total occupancy");
//			testSteps.add("Verified total occupancy");
//
//			getTabDetails = ratesGrid.getPaceVsLastYearTabText(driver);
//			testSteps.add("Expexted : " + paceVsLastYear);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, paceVsLastYear, "Failed to verify pace vs last year");
//			testSteps.add("Verified pace vs last year");
//
//			getTabDetails = ratesGrid.getAddRatePlanButtonText(driver);
//			testSteps.add("Expexted : " + addRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, addRatePlan, "Failed to verify add rate plan");
//			testSteps.add("Verified add rate plan");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnAddRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getNightlyRatePlanText(driver);
//			testSteps.add("Expexted : " + nightlyRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, nightlyRatePlan, "failed to verify nightly rate plan");
//			testSteps.add("Verified nightly rate plan");
//
//			getTabDetails = ratesGrid.getDrivedRatePlanText(driver);
//			testSteps.add("Expexted : " + drivedRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, drivedRatePlan, "Failed to verify drived rate plan");
//			testSteps.add("Verified drived rate plan");
//
//			getTabDetails = ratesGrid.getIntervalRatePlanText(driver);
//			testSteps.add("Expexted : " + intervalRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, intervalRatePlan, "Failed to verify interval rate plan");
//			testSteps.add("Verified interval rate plan");
//
//			getTabDetails = ratesGrid.getPackageRatePlanText(driver);
//			testSteps.add("Expexted : " + pakageRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, pakageRatePlan, "Failed to verify pakage rate plan");
//			testSteps.add("Verified pakage rate plan");
//			// nightly rate plan
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickNightlyRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getNewRatePlanTabTitleText(driver);
//			testSteps.add("Expexted : " + nightlyRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, nightlyRatePlan, "Failed to verify nightly rate plan");
//			testSteps.add("Verified nightly rate plan");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.closeOpendTabInMainMenu(driver);
//			testSteps.addAll(getTestSteps);
//			// drived rate plan
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnAddRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickDrivedRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getNewRatePlanTabTitleText(driver);
//			testSteps.add("Expexted : " + drivedRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, drivedRatePlan, "Failed to verify drived rate plan");
//			testSteps.add("Verified drived rate plan");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.closeOpendTabInMainMenu(driver);
//			testSteps.addAll(getTestSteps);
//			// pakage rate plan
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnAddRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickPackageRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getNewRatePlanTabTitleText(driver);
//			testSteps.add("Expexted : " + pakageRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, pakageRatePlan, "Failed to verify pakage rate plan");
//			testSteps.add("Verified pakage rate plan");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.closeOpendTabInMainMenu(driver);
//			testSteps.addAll(getTestSteps);
//			// interval rate plan
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnAddRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickIntervalRatePlan(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getNewRatePlanTabTitleText(driver);
//			testSteps.add("Expexted : " + intervalRatePlan);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, intervalRatePlan, "Failed to verify interval rate plan");
//			testSteps.add("Verified interval rate plan");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.closeOpendTabInMainMenu(driver);
//			testSteps.addAll(getTestSteps);
//
//			// bulk update
//
//			getTabDetails = ratesGrid.getBulkUpdateButtonText(driver);
//			testSteps.add("Expexted : " + bulkUpdate);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, bulkUpdate, "Failed to verify bulk update");
//			testSteps.add("Verified bulk update button");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getRatesBulkUpdateText(driver);
//			testSteps.add("Expexted : " + rates);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, rates, "Failed to verify rates");
//			testSteps.add("Verified rates");
//
//			getTabDetails = ratesGrid.getAvailabilityBulkUpdateText(driver);
//			testSteps.add("Expexted : " + availability);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, availability, "Failed to verify availability");
//			testSteps.add("Verified availability");
//
//			getTabDetails = ratesGrid.getRulesBulkUpdateText(driver);
//			testSteps.add("Expexted : " + rules);
//			testSteps.add("Found : " + getTabDetails);
//			assertEquals(getTabDetails, rules, "Failed to verify rules");
//			testSteps.add("Verified rules");
//
//			// rates
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickRatesBulkUpdate(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getBulkUpdateHeaderTitle(driver);
//			assertTrue(getTabDetails.contains(bulkUpdateRateTitle), "Failed to verify Bulk update-Rates");
//			testSteps.add("Verified Bulk update-Rates");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.closeRateBulkUpdatePopup(driver);
//			testSteps.addAll(getTestSteps);
//			// availability
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.selectAvailabilityFromBulkUpdate(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getBulkUpdateHeaderTitle(driver);
//			assertTrue(getTabDetails.contains(bulkUpdateAvailabilityTitle),
//					"Failed to verify bulk update- availability");
//			testSteps.add("Verified Bulk update-Availability");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.closeRateBulkUpdatePopup(driver);
//			testSteps.addAll(getTestSteps);
//			// rules
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.clickRulesBulkUpdate(driver);
//			testSteps.addAll(getTestSteps);
//
//			getTabDetails = ratesGrid.getBulkUpdateHeaderTitle(driver);
//			assertTrue(getTabDetails.contains(bulkUpdateRulesTitle), "Failed to verify bulk update- Rules");
//			testSteps.add("Verified Bulk update-Rules");
//
//			getTestSteps.clear();
//			getTestSteps = ratesGrid.closeRateBulkUpdatePopup(driver);
//			testSteps.addAll(getTestSteps);
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify availibilty tabs and buttons", scriptName, "Navigation",
//						driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to verify availibilty tabs and buttons", scriptName, "Navigation",
//						driver);
//			} else {
//
//				Assert.assertTrue(false);
//			}
//		}
//		// Generate Report
//		try {
//			RetryFailedTestCases.count = Utility.reset_count;
//			Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to Generate Report", scriptName, "GenerateReport", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(scriptName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
//				Utility.updateReport(e, "Failed to Generate Report", scriptName, "GenerateReport", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		}
//	}
//
//	@DataProvider
//	public Object[][] getData() {
//		// return test data from the sheet name provided
//		return Utility.getData("AvailabilityCheckRatesGridV2", excel);
//	}
//
//	@AfterClass(alwaysRun = true)
//	public void closeDriver() {
//		// driver.quit();
//	}
//}
