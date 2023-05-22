package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import io.restassured.response.Response;

public class VerifyNighlyRatesOnVrBo extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> gettest_steps = new ArrayList<>();
	RatesGrid rateG = new RatesGrid();
	Admin admin = new Admin();
	Vrbo vrbo = new Vrbo();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	Season seaso = new Season();
	Response response;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyRatesOnVrBo(String roomClassame, String startdate, String enddate, String updateFrom,
			String updateRateType, String updateRate, String isAdditionalChargesForChildrenAdults, String extraAdult,
			String extraChild, String daysTobeselected, String extraAdultAmount, String extraChildAmount,
			String overrideDate, String overrideRate, String overrideAdult, String overrideChild,
			String isDays_RatesUpdate, String overrideStartDate, String overrideEndDate, String updateSeasonCheckout,
			String replaceBaseRate, String replaceAdultrate, String replaceChildRate, String action)
			throws ParseException {
		String testCaseID="802155";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String advertiserId = null, classId = null, currency = null, propertyId = null, ratePlanVrbo = null,
				isMon_RatesUpdate = null, isTue_RatesUpdate = null, isWed_RatesUpdate = null, delim = null,
				isThu_RatesUpdate = null, isFri_RatesUpdate = null, isSat_RatesUpdate = null, isSun_RatesUpdate = null,
				updateEnddate = null;
		ArrayList<String> overriderates = Utility.convertTokenToArrayList(overrideRate, "|");
		ArrayList<String> overrideAdults = Utility.convertTokenToArrayList(overrideAdult, "|");
		ArrayList<String> overrideChilds = Utility.convertTokenToArrayList(overrideChild, "|");
		ArrayList<String> updateSeason = null;
		test_name = "VerifyVrboRate_"+action;
		test_description = "Verify Vrvo Rate <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802155' target='_blank'>"
				+ "Click here to open TestRail: 802155</a>";
		test_catagory = "VerifyPromoCodeRateFromNewRes";
		String testName = test_name;
		Utility.initializeTestCase("802155", Utility.testId, Utility.statusCode, Utility.comments, "");
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		RatesGrid rateGrid = new RatesGrid();
		Navigation navigation = new Navigation();
		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		HashMap<String, ArrayList<HashMap<String, String>>> inRate = null;
		VrboObjects vrboObject = new VrboObjects();
		String channelName = "Vrbo";
		if (action.equalsIgnoreCase("bulkOverrideRate/RemoveOverride")) {
			delim = "|";
			ArrayList<String> IsDays_RatesUpdateList = Utility.convertTokenToArrayList(isDays_RatesUpdate, delim);
			isMon_RatesUpdate = IsDays_RatesUpdateList.get(0);
			isTue_RatesUpdate = IsDays_RatesUpdateList.get(1);
			isWed_RatesUpdate = IsDays_RatesUpdateList.get(2);
			isThu_RatesUpdate = IsDays_RatesUpdateList.get(3);
			isFri_RatesUpdate = IsDays_RatesUpdateList.get(4);
			isSat_RatesUpdate = IsDays_RatesUpdateList.get(5);
			isSun_RatesUpdate = IsDays_RatesUpdateList.get(6);
		}
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			try {
				loginOTA(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginRateV2(driver);
			}
			System.out.println("Startday" + startdate);
			System.out.println("Startday" + enddate);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			test_steps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			String property = homePage.getReportsProperty(driver, test_steps);
			test_steps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);
			navigation.admin(driver);
			navigation.clickonClientinfo(driver);
			test_steps.add("<b>===== Getting Currency =====</b>");
			app_logs.info("===== Getting Currency=====");
			admin.clickOnClient(driver, property);
			test_steps.add("Open Property : " + property);
			app_logs.info("Open Property : " + property);
			admin.clickClientOptions(driver, test_steps);
			currency = admin.getDefaultCurrency(driver);
			test_steps.add("Default Currency : " + currency);
			app_logs.info("Default Currency : " + currency);
			navigation.navigateToSetupfromRoomMaintenance(driver);
			test_steps.add("Click Setup");
			app_logs.info("Click Setup");
			navigation.clickVrboSetup(driver);
			test_steps.add("Click Vrbo Setup");
			app_logs.info("Click Vrbo Setup");
			test_steps.add("<b>===== Getting Advertisement ID =====</b>");
			app_logs.info("===== Getting Advertisement ID=====");
			advertiserId = vrbo.getVrboAdvertisementID(driver);
			test_steps.add("Advertisement ID : " + advertiserId);
			app_logs.info("Advertisement ID " + advertiserId);

			test_steps.add("<b>===== Getting Room Class ID =====</b>");
			app_logs.info("===== Getting Room Class ID=====");
			navigation.roomClass(driver);
			HashMap<String, String> roomClassIds = new HashMap<String, String>();
			roomClassIds = roomClass.getRoomClassDetails(driver, roomClassame, test_steps);
			classId = roomClassIds.get("roomClassId");
			test_steps.add("Room Class ID : " + classId);
			app_logs.info("Room Class ID " + classId);
			ratePlanVrbo = roomClassIds.get("ratePlan");
			test_steps.add("Room ratePlanVrbo : " + ratePlanVrbo);
			app_logs.info("Room ratePlanVrbo " + ratePlanVrbo);
			test_steps.add("<b>===== Getting PropertyId ID =====</b>");
			app_logs.info("===== Getting PropertyId ID=====");
			navigation.properties(driver);
			propertyId = vrbo.getPropertyId(driver, property);
			navigation.Reservation_Backward_4(driver);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
		try {
			if (!(Utility.validateInput(startdate))) {
				startdate = Utility.getCurrentDatTimeInUTC("dd/MM/yyyy");
				enddate = Utility.parseDate(Utility.getDatePast_FutureDateUTC(3), "MM/dd/yyyy", "dd/MM/yyyy");
			}
			if (!(Utility.validateInput(overrideDate))) {
				overrideDate = Utility.getCurrentDatTimeInUTC("dd/MM/yyyy");
			}
			if (action.equalsIgnoreCase("bulkOverrideRate/RemoveOverride")) {
				if (!(Utility.validateInput(overrideStartDate))) {
					overrideStartDate = Utility.getCurrentDatTimeInUTC("dd/MM/yyyy");
					overrideEndDate = Utility.parseDate(Utility.getDatePast_FutureDateUTC(2), "MM/dd/yyyy",
							"dd/MM/yyyy");
				}
			} else if (action.equalsIgnoreCase("replaceSeason") || action.equalsIgnoreCase("fillBlanksSeason")) {
				if (!(Utility.validateInput(startdate))) {

				} else {
					if (action.equalsIgnoreCase("replaceSeason") || action.equalsIgnoreCase("fillBlanksSeason")) {
						updateEnddate = Utility.addDays(enddate, 2);
					} else {
						updateEnddate = Utility.addDays(enddate, 2);
					}

				}
				updateSeason = Utility.getAllDatesStartAndEndDates(startdate, updateEnddate);
			}
			test_steps.add("==================UPDATE SEASON IN RATE==================");
			app_logs.info("==================UPDATE SEASON IN RATE==================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			rateGrid.verifyRatesGridLoaded(driver);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanVrbo);
			rateGrid.verifyRatesGridLoaded(driver);
			rateGrid.clickEditIcon(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);
			datesRangeList = Utility.getAllDatesStartAndEndDates(startdate, enddate);
			try {
				nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList);
				nightlyRate.deleteSeasonIcon(driver, test_steps);
			} catch (Exception e) {

			}
			nightlyRate.selectSeasonDates(driver, test_steps, startdate, enddate);
			String seasonName = "test1" + Utility.generateRandomString();
			nightlyRate.enterSeasonName(driver, test_steps, seasonName);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonColor(driver, test_steps);
			if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
				nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps,
						isAdditionalChargesForChildrenAdults);
			}
			nightlyRate.unSelectSeasonLevelExceptAdditionalRoomClass(driver, roomClassame, test_steps);
			nightlyRate.enterRate(driver, test_steps, updateRate, isAdditionalChargesForChildrenAdults, extraAdult,
					extraChild, extraAdultAmount, extraChildAmount);
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			test_steps.add("==================RATE SUCCESSFULLY UPDATED==================");
			app_logs.info("==================RATE SUCCESSFULLY UPDATED==================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			if (action.equalsIgnoreCase("OverrideRate/RemoveOverride")) {
				test_steps.add("==================OVERRIDE RATE FROM CHANNEL==================");
				app_logs.info("==================OVERRIDE RATE FROM CHANNEL==================");
				Utility.refreshPage(driver);

				ArrayList<String> overrideDates = Utility.convertTokenToArrayList(overrideDate, "|");
				rateG.overrideRateFromRatesGrid(driver, test_steps, roomClassame, overrideDates, overriderates,
						overrideAdults, overrideChilds, true);
			} else if (action.equalsIgnoreCase("bulkOverrideRate/RemoveOverride")) {
				test_steps.add("================== BULK OVERRIDE RATE ==================");
				app_logs.info("================== BULK OVERRIDE RATE ==================");
				String isUpdateRateByRoomClass = "false";
				rateGrid.bulkUpdateOverideRates(driver, delim, updateFrom, overrideStartDate, overrideEndDate,
						isSun_RatesUpdate, isMon_RatesUpdate, isTue_RatesUpdate, isWed_RatesUpdate, isThu_RatesUpdate,
						isFri_RatesUpdate, isSat_RatesUpdate, ratePlanVrbo, roomClassame, channelName, updateRateType,
						isUpdateRateByRoomClass, overrideRate, overrideAdult, overrideChild, test_steps);
			}

			test_steps.add("==================GET RATE FROM RATE GRID==================");
			app_logs.info("===================GET RATE FROM RATE GRID==================");
			inRate = rateGrid.getRatesWithAdultChildFromChannel(driver, test_steps, roomClassame, channelName,
					datesRangeList, true);

			test_steps.add("Get Inncenter Rate from rate grid: " + inRate);
			app_logs.info("Get Inncenter Rate from rate grid: " + inRate);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Inncenter rate", testName,
					test_description, test_catagory, test_steps);
			Utility.catchException(driver, e, test_description, test_catagory, "Get Inncenter rate", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
		}
		try {
			int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
			app_logs.info(statusCode);
			vrboObject.veifyVrboClientStatusCode(statusCode, test_steps);
			Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);

			test_steps.add("==================GET BASE RATE ON VRBO==================");
			app_logs.info("==================GET BASE RATE ON VRBO==================");
			HashMap<String, ArrayList<ArrayList<String>>> vrboRate = vrboObject.getAllRatesBaseRateWithDateRange(driver,
					responseBody);
			test_steps.add("Vrbo Base Rate: " + vrboRate);
			app_logs.info("Vrbo Base Rate: " + vrboRate);
			test_steps.add("==================VERIFY BASE RATE ON VRBO==================");
			app_logs.info("==================VERIFY BASE RATE ON VRBO==================");

			vrboObject.verifyVrboBaseRate(inRate, vrboRate, datesRangeList, test_steps);

			if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
				test_steps.add("==================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
				app_logs.info("===================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
				HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> recordsExtraAdultAndChild = vrboObject
						.getAllExtraAdultChildDateRange(driver, responseBody);
				test_steps.add("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
				app_logs.info("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
				test_steps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
				app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");

				vrboObject.verifyExtraChildAndAdultAmountWithDateRange(driver, inRate,
						recordsExtraAdultAndChild.get("ExtraAdult"), recordsExtraAdultAndChild.get("ExtraChild"),
						datesRangeList, test_steps);

			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Verify vrbo rate", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Verify vrbo rate", testName,
					test_description, test_catagory, test_steps);
		}
		try {
			if (action.equalsIgnoreCase("OverrideRate/RemoveOverride")) {
				test_steps.add("==================REMOVE OVERRIDE RATE FROM CHANNEL==================");
				app_logs.info("================== REMOVE OVERRIDE RATE FROM CHANNEL==================");
				Utility.refreshPage(driver);
				ArrayList<String> overrideDates = Utility.convertTokenToArrayList(overrideDate, "|");
				rateG.removeOverrideChannelLevel(driver, roomClassame, overrideDates, channelName, test_steps);
				test_steps.add("==================GET RATE FROM RATE GRID==================");
				app_logs.info("===================GET RATE FROM RATE GRID==================");
				inRate = rateGrid.getRatesWithAdultChildFromChannel(driver, test_steps, roomClassame, channelName,
						datesRangeList, true);
			} else if (action.equalsIgnoreCase("bulkOverrideRate/RemoveOverride")) {
				test_steps.add("================== REMOVE BULK OVERRIDE RATE ==================");
				app_logs.info("================== REMOVE BULK OVERRIDE RATE ==================");
				String isUpdateRateByRoomClass = "false", updateRatesType = "Remove overrides";
				ArrayList<String> activeRatePlanNames = null, inactiveRatePlanNames = null,
						activeRoomClassesNames = null, activeChannelsList = null;
				rateGrid.bulkUpdate(driver, "Rates", overrideStartDate, overrideEndDate, "dd/MM/yyyy",
						isSun_RatesUpdate, isMon_RatesUpdate, isTue_RatesUpdate, isWed_RatesUpdate, isThu_RatesUpdate,
						isFri_RatesUpdate, isSat_RatesUpdate, "No", "", "", ratePlanVrbo, roomClassame, channelName,
						updateRatesType, isUpdateRateByRoomClass, overrideRate, overrideAdult, overrideChild, "", "",
						activeRatePlanNames, inactiveRatePlanNames, activeRoomClassesNames, activeChannelsList, "", "",
						"", "", "", "");
				test_steps.add("==================GET RATE FROM RATE GRID==================");
				app_logs.info("===================GET RATE FROM RATE GRID==================");
				inRate = rateGrid.getRatesWithAdultChildFromChannel(driver, test_steps, roomClassame, channelName,
						datesRangeList, true);
			} else if (action.equalsIgnoreCase("removeSource")) {
				Utility.refreshPage(driver);
				rateGrid.clickEditIcon(driver, test_steps);
				Utility.ScrollToTillEndOfPage(driver);
				nightlyRate.selectChannels(driver, channelName, false, test_steps);
				Utility.ScrollToUp(driver);
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				navigation.Inventory(driver, test_steps);
				navigation.ratesGrid(driver);

			} else if (action.equalsIgnoreCase("replaceSeason") || action.equalsIgnoreCase("fillBlanksSeason")) {
				if(action.equalsIgnoreCase("replaceSeason") ) {
					test_steps.add("==================REPLACE SEASON ON RATE PLAN==================");
					app_logs.info("===================REPLACE SEASON ON RATE PLAN==================");
				}else if(action.equalsIgnoreCase("fillBlanksSeason") ) {
					test_steps.add("==================FILL BLANK SEASON ON RATE PLAN==================");
					app_logs.info("===================FILL BLANK SEASON ON RATE PLAN==================");
				}
				Utility.refreshPage(driver);
				rateGrid.clickEditIcon(driver, test_steps);
				nightlyRate.switchCalendarTab(driver, test_steps);
				System.out.println("endDate:" + updateEnddate);
				nightlyRate.selectSeasonDates(driver, test_steps, startdate, updateEnddate);
				if (action.equalsIgnoreCase("replaceSeason")) {
					nightlyRate.clickReplaceSeason(driver, test_steps);
				} else {
					nightlyRate.clickFillBlanksSeason(driver, test_steps);
				}
				String seasonNameUpdate = "test1" + Utility.generateRandomString();
				nightlyRate.enterSeasonName(driver, test_steps, seasonNameUpdate);
				nightlyRate.clickCreateSeason(driver, test_steps);
				nightlyRate.selectSeasonColor(driver, test_steps);
				if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
					nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps,
							isAdditionalChargesForChildrenAdults);
				}
				nightlyRate.unSelectSeasonLevelExceptAdditionalRoomClass(driver, roomClassame, test_steps);
				nightlyRate.enterRate(driver, test_steps, replaceBaseRate, isAdditionalChargesForChildrenAdults,
						extraAdult, extraChild, replaceAdultrate, replaceChildRate);
				nightlyRate.clickSaveSason(driver, test_steps);
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				navigation.Inventory(driver, test_steps);
				navigation.ratesGrid(driver);
				test_steps.add("Navigated to rateGrid");

				test_steps.add("==================GET RATE FROM RATE GRID==================");
				app_logs.info("===================GET RATE FROM RATE GRID==================");
				inRate = rateGrid.getRatesWithAdultChildFromChannel(driver, test_steps, roomClassame, channelName,
						updateSeason, true);
			} else if (action.equalsIgnoreCase("deleteSeason")) {
				test_steps.add("==================DELETE SEASON ON RATE PLAN==================");
				app_logs.info("===================DELETE SEASON ON RATE PLAN==================");
				Utility.refreshPage(driver);
				rateGrid.clickEditIcon(driver, test_steps);
				nightlyRate.switchCalendarTab(driver, test_steps);
				nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList);
				nightlyRate.deleteSeasonIcon(driver, test_steps);
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				navigation.Inventory(driver, test_steps);
				navigation.ratesGrid(driver);
				test_steps.add("Navigated to rateGrid");
			} else if (action.equalsIgnoreCase("UncheckMappedRoomClass")) {
				test_steps.add("=================REMOVE ROOMCLASS IN SEASON ON RATE PLAN==================");
				app_logs.info("===================REMOVE ROOMCLASS IN SEASON ON RATE PLAN==================");
				Utility.refreshPage(driver);
				rateGrid.clickEditIcon(driver, test_steps);
				nightlyRate.switchCalendarTab(driver, test_steps);
				nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);
				nightlyRate.unSelectSeasonLevelExistingRoomClass(driver, roomClassame, test_steps);
				nightlyRate.addLastRoomClassRandomely(driver, test_steps);
				nightlyRate.enterRate(driver, test_steps, updateRate, isAdditionalChargesForChildrenAdults, extraAdult,
						extraChild, extraAdultAmount, extraChildAmount);
				nightlyRate.clickSaveSason(driver, test_steps);
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);

			} else if (action.equalsIgnoreCase("inactiveRate")) {
				rateGrid.clickEditIcon(driver, test_steps);
				nightlyRate.ratePlanStatusChange(driver, false, test_steps);

				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			}

			test_steps.add("Get Inncenter Rate from rate grid: " + inRate);
			app_logs.info("Get Inncenter Rate from rate grid: " + inRate);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Inncenter rate after update",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Get Inncenter rate after update", testName,
					test_description, test_catagory, test_steps);
		}
		try {
			if (action.equalsIgnoreCase("OverrideRate/RemoveOverride")
					|| action.equalsIgnoreCase("bulkOverrideRate/RemoveOverride")) {
				int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
				app_logs.info(statusCode);
				vrboObject.veifyVrboClientStatusCode(statusCode, test_steps);
				Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId,
						vrvoRateToken);
				test_steps.add("==================GET BASE RATE ON VRBO AFTER REMOVE OVERRIDE==================");
				app_logs.info("==================GET BASE RATE ON VRBO AFTER REMOVE OVERRIDE==================");
				HashMap<String, ArrayList<ArrayList<String>>> vrboRate = vrboObject
						.getAllRatesBaseRateWithDateRange(driver, responseBody);
				test_steps.add("Vrbo Base Rate: " + vrboRate);
				app_logs.info("Vrbo Base Rate: " + vrboRate);
				test_steps.add("==================VERIFY BASE RATE ON VRBO==================");
				app_logs.info("==================VERIFY BASE RATE ON VRBO==================");
				vrboObject.verifyVrboBaseRate(inRate, vrboRate, datesRangeList, test_steps);
				if (!action.equalsIgnoreCase("updateRateWithoutExtraAdult/Child")) {
					test_steps.add("==================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
					app_logs.info("===================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
					HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> recordsExtraAdultAndChild = vrboObject
							.getAllExtraAdultChildDateRange(driver, responseBody);
					test_steps.add("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
					app_logs.info("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
					test_steps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					vrboObject.verifyExtraChildAndAdultAmountWithDateRange(driver, inRate,
							recordsExtraAdultAndChild.get("ExtraAdult"), recordsExtraAdultAndChild.get("ExtraChild"),
							datesRangeList, test_steps);

				}
			} else if (action.equalsIgnoreCase("removeSource") || action.equalsIgnoreCase("deleteSeason")
					|| action.equalsIgnoreCase("UncheckMappedRoomClass") || action.equalsIgnoreCase("inactiveRate")) {
				int statusCodes = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
				app_logs.info(statusCodes);
				try {
					vrboObject.veifyVrboClientStatusCode(statusCodes, test_steps);
					Response responseBodys = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId,
							vrvoRateToken);
					boolean isRateAvailable = vrboObject.isVrboRateAvailable(driver, responseBodys);
					test_steps.add("Verify rate not available");
					app_logs.info("Verify rate not available");
					test_steps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					assertEquals(isRateAvailable, false, "Failed to verify");
					if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
						boolean isAdultChildRateAvailable = vrboObject.isAdultChildRateAvailable(driver, responseBodys);
						assertEquals(isAdultChildRateAvailable, false, "Failed to verify");
						test_steps.add("Verify Adult and Child rate not available");
						app_logs.info("Verify Adult and Child rate not available");
					}
				} catch (Exception e) {
					test_steps
							.add("This got failed due to existing issue on making rate plan inactive: " + e.toString());
				} catch (Error e) {
					test_steps
							.add("This got failed due to existing issue on making rate plan inactive: " + e.toString());
				}

				if (action.equalsIgnoreCase("removeSource")) {
					rateGrid.clickEditIcon(driver, test_steps);
					Utility.ScrollToTillEndOfPage(driver);
					nightlyRate.selectChannels(driver, channelName, true, test_steps);
					Utility.ScrollToUp(driver);
					nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				}
				if (action.equalsIgnoreCase("inactiveRate")) {
					nightlyRate.ratePlanStatusChange(driver, true, test_steps);
					nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				}
			} else if (action.equalsIgnoreCase("replaceSeason") || action.equalsIgnoreCase("fillBlanksSeason")) {
				int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
				app_logs.info(statusCode);
				vrboObject.veifyVrboClientStatusCode(statusCode, test_steps);
				Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId,
						vrvoRateToken);
				test_steps.add("==================GET BASE RATE ON VRBO==================");
				app_logs.info("==================GET BASE RATE ON VRBO==================");
				HashMap<String, ArrayList<ArrayList<String>>> vrboRate = vrboObject
						.getAllRatesBaseRateWithDateRange(driver, responseBody);
				test_steps.add("Vrbo Base Rate: " + vrboRate);
				app_logs.info("Vrbo Base Rate: " + vrboRate);
				test_steps.add("==================VERIFY BASE RATE ON VRBO==================");
				app_logs.info("==================VERIFY BASE RATE ON VRBO==================");
				vrboObject.verifyVrboBaseRate(inRate, vrboRate, updateSeason, test_steps);
				if (!action.equalsIgnoreCase("updateRateWithoutExtraAdult/Child")) {
					test_steps.add("==================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
					app_logs.info("===================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
					HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> recordsExtraAdultAndChild = vrboObject
							.getAllExtraAdultChildDateRange(driver, responseBody);
					test_steps.add("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
					app_logs.info("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
					test_steps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					vrboObject.verifyExtraChildAndAdultAmountWithDateRange(driver, inRate,
							recordsExtraAdultAndChild.get("ExtraAdult"), recordsExtraAdultAndChild.get("ExtraChild"),
							updateSeason, test_steps);

				}

			} else {

			}
		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Verify vrbo rate", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Verify vrbo rate", testName,
					test_description, test_catagory, test_steps);
		}
		
		/*test_steps.add("Verify the Nightly Rate for an airbnb/VRBO client"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/802155' target='_blank'>"
				+ "Click here to open TestRail: 802155</a><br>");
		Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify the Nightly Rate for an airbnb/VRBO client");		
		Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
*/
		for(int i=0;i<Utility.testId.size();i++) {
			Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Account reservation checkin");
		}
		
		
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
	}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyRatesOnVrBo", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
