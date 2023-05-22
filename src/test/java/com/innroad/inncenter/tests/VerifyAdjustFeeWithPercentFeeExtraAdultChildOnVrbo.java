package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

import io.restassured.response.Response;

public class VerifyAdjustFeeWithPercentFeeExtraAdultChildOnVrbo extends TestCore {

	
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
	RatesGrid rateGrid= new RatesGrid();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	TaxesAndFee taxFee = new TaxesAndFee();
	Properties properties= new Properties();
	ArrayList<String> datesRangeList = new ArrayList<String>();
   DerivedRate derivedRate = new DerivedRate();
   NightlyRate nightltRate= new NightlyRate();
	Response response;
	
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData")
	public void  verifyAdjustFee(String roomClassName,String ratePlanVrbo,String startDate, String endDate,
			String feeName, String feeType, String feeCategoryValue, String categoryFee,
			 String updateRate, String isAdditionalChargesForChildrenAdults, String extraAdult,
				String extraChild, String extraAdultAmount, String extraChildAmount) {
		
		String property=null, classId = null,
		propertyId=null, ratePlanName=null;
		ArrayList<String> feeCategoriesValue = new ArrayList<String>();
		ArrayList<String> feetype = new ArrayList<String>();
		ArrayList<String> catgoriesFee = new ArrayList<String>();
		ArrayList<String> feename = new ArrayList<String>();
		test_name = "Verify adjust fee when having percent fee and extra child/adult in rate plan";
		test_description = "Verify Vrvo Rate <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "Vrbo";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		RatesGrid rateGrid = new RatesGrid();
		Navigation navigation = new Navigation();
		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		HashMap<String, ArrayList<HashMap<String, String>>> inRate = null;
		VrboObjects vrboObject = new VrboObjects();
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
		
			driver = getDriver();
			loginOTA(driver);
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
			
			ratePlanName=roomClassIds.get("ratePlan");
			testSteps.add("Room Class ID : " + ratePlanName);
			app_logs.info("Room Class ID " + ratePlanName);
			
			testSteps.add("<b>===== Getting PropertyId ID =====</b>");
			app_logs.info("===== Getting PropertyId ID=====");
			navigation.properties(driver);
			propertyId = vrbo.getPropertyId(driver, property);
			app_logs.info("Getting PropertyId ID" + propertyId);
		}
			catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver,e, test_description, test_catagory, "Get Data",
						testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver,e, test_description, test_catagory, "Get Data", testName,
						test_description, test_catagory, testSteps);
			}
		
		try {
			navigation.clickTaxesAndFees(driver);
			testSteps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");
			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", testSteps);
			}
			testSteps.add("====================CREATE NEW FEE ======================");
			app_logs.info("====================CREATE NEW FEE ======================");
				ArrayList<String> feeNames = Utility.splitInputData(feeName);
				feeCategoriesValue = Utility.splitInputData(feeCategoryValue);
				feetype = Utility.splitInputData(feeType);
				catgoriesFee = Utility.splitInputData(categoryFee);
				for (int i = 0; i < feeNames.size(); i++) {
					feename.add(feeNames.get(i) + Utility.generateRandomNumber());
					taxFee.createFee(driver, testSteps, feename.get(i), feename.get(i), feetype.get(i), feename.get(i),
							catgoriesFee.get(i), feeCategoriesValue.get(i), false, "", "", "");
				}
			app_logs.info(feename);
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "CREATE NEW FEE",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "CREATE NEW FEE", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			testSteps.add("==================== UPDATE SEASON EXTRA ADULT AND CHILD IN RATE PLAN ======================");
			app_logs.info("==================== UPDATE SEASON EXTRA ADULT AND CHILD IN RATE PLAN ======================");
			
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
			
			navigation.InventoryV2(driver);
			testSteps.add("Navigated to Inventory");
			app_logs.info("Navigated to Inventory");
			navigation.ratesGrid(driver);
			testSteps.add("Navigated to rateGrid");
			app_logs.info("Navigated to rateGrid");
			rateGrid.verifyRatesGridLoaded(driver);
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanVrbo);
			rateGrid.verifyRatesGridLoaded(driver);
			rateGrid.clickEditIcon(driver, testSteps);
			nightlyRate.switchCalendarTab(driver, testSteps);
			datesRangeList = Utility.getAllDatesStartAndEndDates(startDate, endDate);
			try {
				nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList);
				nightlyRate.deleteSeasonIcon(driver, testSteps);
			} catch (Exception e) {

			}
			nightlyRate.selectSeasonDates(driver, testSteps,startDate, endDate);
			String seasonName = "Season" + Utility.generateRandomString();
			nightlyRate.enterSeasonName(driver, testSteps, seasonName);
			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonColor(driver, testSteps);
			if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
				nightlyRate.selectAdditionalChargesForChildrenAdults(driver, testSteps,
						isAdditionalChargesForChildrenAdults);
			}
			nightlyRate.unSelectSeasonLevelExceptAdditionalRoomClass(driver, roomClassName, testSteps);
			nightlyRate.enterRate(driver, testSteps, updateRate, isAdditionalChargesForChildrenAdults, extraAdult,
					extraChild, extraAdultAmount, extraChildAmount);
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "Update Season Rate",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "pdate Season Rate", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			HashMap<String, String> adjustFee= new HashMap<String, String>();
			adjustFee=vrboObject.calculateAdjustFeeWithPercentFeeAndExtraAdultAndChild(driver, feeCategoriesValue, extraAdultAmount, extraChildAmount);
			int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
			app_logs.info(statusCode);
			vrboObject.veifyVrboClientStatusCode(statusCode, testSteps);
			Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);
			
			testSteps.add("==================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
			app_logs.info("===================GET EXTRA ADULT AND CHILD AMOUNT ON VRBO==================");
			HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> recordsExtraAdultAndChild = vrboObject
					.getAllExtraAdultChildDateRange(driver, responseBody);
			testSteps.add("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
			app_logs.info("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
			testSteps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
			app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");

			app_logs.info(recordsExtraAdultAndChild.get("ExtraChildAdjustMent"));
			app_logs.info(recordsExtraAdultAndChild.get("ExtraAdultAdjustMent"));
			ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> value=recordsExtraAdultAndChild.get("ExtraAdultAdjustMent");
			HashMap<String, ArrayList<ArrayList<String>>> getAdjustAdult=value.get(0);
			String extraAdultValue=null;
			for(Entry<String, ArrayList<ArrayList<String>>> entry: getAdjustAdult.entrySet()) {
				extraAdultValue=entry.getKey();
			}
			app_logs.info(extraAdultValue);
			ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> value1=recordsExtraAdultAndChild.get("ExtraChildAdjustMent");
			HashMap<String, ArrayList<ArrayList<String>>> getAdjustChild=value1.get(0);
			String extraChildValue=null;
			for(Entry<String, ArrayList<ArrayList<String>>> entry: getAdjustChild.entrySet()) {
				extraChildValue=entry.getKey();
			}
			app_logs.info(extraChildValue);
			
			Utility.verifyText(Utility.convertDecimalFormat(adjustFee.get("extraAdultAdjustFee")), Utility.convertDecimalFormat(extraAdultValue), "Failed to verify Extra Adult", testSteps, app_logs);
			Utility.verifyText(Utility.convertDecimalFormat(adjustFee.get("extraChildAdjustFee")), Utility.convertDecimalFormat(extraChildValue), "Failed to verify Extra Child", testSteps, app_logs);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "Get Data",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "Get Data", testName,
					test_description, test_catagory, testSteps);
		}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyAdjustFeeWithPercentFeeEx", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
