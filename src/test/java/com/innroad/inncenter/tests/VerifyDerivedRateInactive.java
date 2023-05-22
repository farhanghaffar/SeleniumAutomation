package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import io.restassured.response.Response;

public class VerifyDerivedRateInactive extends TestCore {

	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	private WebDriver driver = null;
	VrboObjects vrboObject = new VrboObjects();
	ReservationSearch rsvSearch = new ReservationSearch();
	CPReservationPage reservation = new CPReservationPage();
	HashMap<String, String> data = new HashMap<String, String>();
	CreateBookingRequestXML bookingRequest = new CreateBookingRequestXML();
	HashMap<String, Response> responses = new HashMap<String, Response>();
	Navigation navigation = new Navigation();
	ReservationHomePage homePage = new ReservationHomePage();
	Admin admin = new Admin();
	Vrbo vrbo = new Vrbo();
	RatesGrid rateGrid = new RatesGrid();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	TaxesAndFee taxFee = new TaxesAndFee();
	Properties properties = new Properties();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	DerivedRate derivedRate = new DerivedRate();
	NightlyRate nightlyRate = new NightlyRate();
	Response response;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyDerivedRateInactive(String testCaseId,String testCaseName, String channelName,String roomClassName,String promoCode,String startDate, String endDate,String isAdditionalChargesForChildrenAdults, String action,
			String updateRate, String extraAdult,String extraChild, String extraAdultAmount, String extraChildAmount) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseId)) {
		String testName = testCaseName;
		String property = null, classId = null, propertyId = null, ratePlanName = null, baseRatePlanName = null;
		Utility.initializeTestCase(testCaseId, Utility.testId, Utility.statusCode, Utility.comments, "");
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			loginVrbo(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			testSteps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			property = homePage.getReportsProperty(driver, testSteps);
			testSteps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);
			navigation.setup(driver);
			testSteps.add("<b>===== Getting Room Class ID =====</b>");
			app_logs.info("===== Getting Room Class ID=====");
			navigation.roomClass(driver);
			HashMap<String, String> roomClassIds = new HashMap<String, String>();
			roomClassIds = roomClass.getRoomClassDetails(driver, roomClassName, testSteps);
			classId = roomClassIds.get("roomClassId");
			testSteps.add("Room Class ID : " + classId);
			app_logs.info("Room Class ID " + classId);
			ratePlanName = roomClassIds.get("ratePlan");
			testSteps.add("Rate Plan Name : " + ratePlanName);
			app_logs.info("Rate Plan Name  " + ratePlanName);
			testSteps.add("<b>===== Getting PropertyId ID =====</b>");
			app_logs.info("===== Getting PropertyId ID=====");
			navigation.properties(driver);
			propertyId = vrbo.getPropertyId(driver, property);
			app_logs.info("Getting PropertyId ID" + propertyId);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, testSteps);
		}

		try {			
			navigation.inventoryBackwardAdmin(driver);
			navigation.ratesGrid(driver);
			testSteps.add("Navigated to rateGrid");
			rateGrid.verifyRatesGridLoaded(driver);
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			rateGrid.verifyRatesGridLoaded(driver);
			baseRatePlanName = rateGrid.getDefaultRatePlan(driver, testSteps);
			app_logs.info(baseRatePlanName);
			if (action.equalsIgnoreCase("inactiveBaseRate")) {
				testSteps.add("==================INACTIVE BASE RATE PLAN==================");
				app_logs.info("==================INACTIVE BASE RATE PLAN==================");
			rateGrid.clickEditIcon(driver, testSteps);
			nightlyRate.ratePlanStatusChange(driver, false, testSteps);
			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			}else if(action.equalsIgnoreCase("inactiveDerivedRate")) {
				testSteps.add("==================INACTIVE DERIVED RATE PLAN==================");
				app_logs.info("==================INACTIVE DERIVED RATE PLAN==================");
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, ratePlanName, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				nightlyRate.ratePlanStatusChange(driver, false, testSteps);
				nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			}else if (action.equalsIgnoreCase("removedRoomClass")) {
				testSteps.add("==================REMOVE ROOM CLASS OF BASE RATE PLAN==================");
				app_logs.info("==================REMOVE ROOM CLASS OF BASE RATE PLAN==================");
				ArrayList<String> checkInDates = new ArrayList<>();
				ArrayList<String> checkOutDates = new ArrayList<>();
				if (!(Utility.validateInput(startDate))) {
					startDate = Utility.getCurrentDatTimeInUTC("dd/MM/yyyy");
					endDate = Utility.parseDate(Utility.getDatePast_FutureDateUTC(2), "MM/dd/yyyy", "dd/MM/yyyy");			
				} else {
					checkInDates = Utility.splitInputData(startDate);
					checkOutDates = Utility.splitInputData(endDate);
					startDate=checkInDates.get(0);
					endDate=checkOutDates.get(0);
				}
				app_logs.info("Start Date: "+startDate);
				app_logs.info("End Date: "+endDate);
				datesRangeList = Utility.getAllDatesStartAndEndDates(startDate, endDate);
				app_logs.info("Date Range: "+datesRangeList);
				rateGrid.clickEditIcon(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				nightlyRate.switchCalendarTab(driver, testSteps);
				nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList);
				nightlyRate.clickEditThisSeasonButton(driver, testSteps);
				nightlyRate.addLastRoomClassRandomely(driver, testSteps);
				nightlyRate.enterRate(driver, testSteps, updateRate, isAdditionalChargesForChildrenAdults, extraAdult,
						extraChild, extraAdultAmount, extraChildAmount);
				nightlyRate.unSelectSeasonLevelExistingRoomClass(driver, roomClassName, testSteps);
				nightlyRate.clickSaveSason(driver, testSteps);
				nightlyRate.clickSaveRatePlanButton(driver, testSteps);

			}else if (action.equalsIgnoreCase("removedSeason")) {
				testSteps.add("==================REMOVE SOURCE OF DERIVED RATE PLAN==================");
				app_logs.info("==================REMOVE SOURCE OF DERIVED RATE PLAN==================");
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, ratePlanName, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				Utility.ScrollToTillEndOfPage(driver);
				nightlyRate.selectChannels(driver, channelName, false, testSteps);
				Utility.ScrollToUp(driver);
				nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			}else if (action.equalsIgnoreCase("conditionalRate")) {
				testSteps.add("==================ADD PROMO CODE AT DERIVED RATE PLAN==================");
				app_logs.info("==================ADD PROMO CODE AT DERIVED RATE PLAN==================");
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
				derivedRate.clickEditIconOfDerivedRatePlan(driver, ratePlanName, testSteps);
				nightlyRate.switchRestrictionAndPoliciesTab(driver, testSteps);
				nightlyRate.promoCode(driver, isAdditionalChargesForChildrenAdults, promoCode);
				Utility.ScrollToUp(driver);
				nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, testSteps);
		}

		try {
			if (action.equalsIgnoreCase("inactiveBaseRate")||action.equalsIgnoreCase("inactiveDerivedRate")||action.equalsIgnoreCase("removedRoomClass")
					||action.equalsIgnoreCase("removedSeason")||action.equalsIgnoreCase("conditionalRate")) {
				int statusCodes = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
				app_logs.info(statusCodes);
				try {
					vrboObject.veifyVrboClientStatusCode(statusCodes, testSteps);
					Response responseBodys = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId,
							vrvoRateToken);
					boolean isRateAvailable = vrboObject.isVrboRateAvailable(driver, responseBodys);
					testSteps.add("Verify rate not available");
					app_logs.info("Verify rate not available");
					testSteps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
					assertEquals(isRateAvailable, false, "Failed to verify");
					if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
						boolean isAdultChildRateAvailable = vrboObject.isAdultChildRateAvailable(driver, responseBodys);
						assertEquals(isAdultChildRateAvailable, false, "Failed to verify");
						testSteps.add("Verify Adult and Child rate not available");
						app_logs.info("Verify Adult and Child rate not available");
					}
				} catch (Exception e) {
					testSteps
							.add("This got failed due to existing issue on making rate plan inactive: " + e.toString());
				} catch (Error e) {
					testSteps
							.add("This got failed due to existing issue on making rate plan inactive: " + e.toString());
				}
				if (action.equalsIgnoreCase("inactiveBaseRate")) {
					testSteps.add("==================ACTIVE BASE RATE PLAN==================");
					app_logs.info("==================ACTIVE BASE RATE PLAN==================");
					nightlyRate.ratePlanStatusChange(driver, true, testSteps);
					nightlyRate.clickSaveRatePlanButton(driver, testSteps);
					rateGrid.closeRatePlan(driver, testSteps, baseRatePlanName);
					rateGrid.verifyRatesGridLoaded(driver);
					testSteps.add("==================ACTIVE DERIVED RATE PLAN==================");
					app_logs.info("==================ACTIVE DERIVED RATE PLAN==================");
					derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
					derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
					derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
					derivedRate.clickEditIconOfDerivedRatePlan(driver, ratePlanName, testSteps);
					Wait.waitforPageLoad(50, driver);
					nightlyRate.ratePlanStatusChange(driver, true, testSteps);
					nightlyRate.clickSaveRatePlanButton(driver, testSteps);
					/*Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify the Base Rate Plan Inactive for an airbnb/VRBO client");
					testSteps.add("Verify the Base Rate Plan Inactive for an airbnb/VRBO client"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802175' target='_blank'>"
						+ "Click here to open TestRail: 802175</a><br>");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
				}else if (action.equalsIgnoreCase("inactiveDerivedRate")) {
					testSteps.add("==================ACTIVE DERIVED RATE PLAN==================");
					app_logs.info("==================ACTIVE DERIVED RATE PLAN==================");					
					nightlyRate.ratePlanStatusChange(driver, true, testSteps);
					nightlyRate.clickSaveRatePlanButton(driver, testSteps);
					/*Utility.testCasePass(Utility.statusCode,1,Utility.comments,"Verify the Derived Rate Plan Inactive for an airbnb/VRBO client");
					testSteps.add("Verify the Derived Rate Plan Inactive for an airbnb/VRBO client"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802177' target='_blank'>"
						+ "Click here to open TestRail: 802177</a><br>");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			*/
				}else if (action.equalsIgnoreCase("removedRoomClass")) {
					testSteps.add("==================SET DEFAULT ROOM CLASS OF BASE RATE PLAN==================");
					app_logs.info("==================SET DEFAULT ROOM CLASS OF BASE RATE PLAN==================");
					Utility.refreshPage(driver);
					Wait.waitUntilPageLoadNotCompleted(driver, 40);
					nightlyRate.switchCalendarTab(driver, testSteps);
					nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList);
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					ArrayList<String> roomclass=rateGrid.getSeasonLevelRoomClasses(driver);
					app_logs.info(roomclass);
					for(int i=0;i<roomclass.size();i++) {
						nightlyRate.unSelectSeasonLevelExistingRoomClass(driver, roomclass.get(i), testSteps);
					}
					nightlyRate.addSeasonLevelRoomClass(driver, testSteps, roomClassName);
					nightlyRate.enterRate(driver, testSteps, updateRate, isAdditionalChargesForChildrenAdults, extraAdult,
							extraChild, extraAdultAmount, extraChildAmount);
					nightlyRate.clickSaveSason(driver, testSteps);
					nightlyRate.clickSaveRatePlanButton(driver, testSteps);

				}else if (action.equalsIgnoreCase("removedSeason")) {
					Utility.ScrollToTillEndOfPage(driver);
					nightlyRate.selectChannels(driver, channelName, true, testSteps);
					Utility.ScrollToUp(driver);
					nightlyRate.clickSaveRatePlanButton(driver, testSteps);
				}else if (action.equalsIgnoreCase("conditionalRate")) {
					nightlyRate.selectRestrictionTypes(driver, "Promo code", false, testSteps);
					Utility.ScrollToUp(driver);
					nightlyRate.clickSaveRatePlanButton(driver, testSteps);
					/*Utility.testCasePass(Utility.statusCode,2,Utility.comments,"Verify conditional rate by making Yes that should not sync to airbnb/VRBO");
					testSteps.add("Verify conditional rate by making Yes that should not sync to airbnb/VRBO"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802183' target='_blank'>"
						+ "Click here to open TestRail: 802183</a><br>");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
				}
			}
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Conditional Rate and Inactive Base rate Plan and Imacive Drive rate plan");
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, testSteps);
		}
	}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyDerivedRateInactive", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
