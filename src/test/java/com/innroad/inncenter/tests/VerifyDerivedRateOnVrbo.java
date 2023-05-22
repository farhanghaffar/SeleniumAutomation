package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

public class VerifyDerivedRateOnVrbo extends TestCore{
	
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
	
	private void getData(HashMap<String, String> data)
	{
		Set set = data.entrySet();
		  Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         testSteps.add("Date : "+ mentry.getKey() + " & Value : " +mentry.getValue().toString());
		      }
	}
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyDerivedRateOnVrbo(String testCaseID,String testCaseName, String roomClassName, String channelName,String startDate, String endDate,
			String  selectComparator, String derivedRateType, String derivedRateValue) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		String testName = testCaseName;
		String property=null, classId = null,
				propertyId=null, ratePlanName=null, baseRatePlanName=null;
		HashMap<String,String> derivedRatePlanAmount= new HashMap<String,String> ();
		HashMap<String, ArrayList<HashMap<String, String>>> derivedRatePlanAmounts= new HashMap<String, ArrayList<HashMap<String, String>>>();
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
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
			
			ratePlanName=roomClassIds.get("ratePlan");
			testSteps.add("Rate Plan ID : " + ratePlanName);
			app_logs.info("Rate Plan ID " + ratePlanName);
			
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
			
			testSteps.add("<b>===== Getting Base Rate Plan Rates =====</b>");
			app_logs.info("===== Getting Base Rate Plan Rates=====");
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
			navigation.inventoryBackwardAdmin(driver);
			navigation.ratesGrid(driver);
			testSteps.add("Navigated to rateGrid");
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 60);
			baseRatePlanName= rateGrid.getDefaultRatePlan(driver, testSteps);
			app_logs.info(baseRatePlanName);
			HashMap<String, ArrayList<HashMap<String, String>>> inRate = rateGrid
					.getRatesWithAdultChildFromChannel(driver, testSteps, roomClassName, channelName, datesRangeList,false);
			app_logs.info("Rate is: "+ inRate);
			testSteps.add("<b>===== Update Derived Rate Plan Offset =====</b>");
			app_logs.info("===== Update Derived Rate Plan Offset =====");
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.clickEditIconOfDerivedRatePlan(driver, ratePlanName, testSteps);
			derivedRate.updateParentoffset(driver, testSteps, selectComparator, derivedRateType, derivedRateValue);
			nightltRate.clickSaveRatePlanButton(driver, testSteps);
			testSteps.add("<b>===== CalCulate Derived Rate Plan Amount =====</b>");
			app_logs.info("=====CalCulate Derived Rate Plan Amount =====");
			
			derivedRatePlanAmount= derivedRate.calculateBaseRateExtraAdultAndChildAsperBaseRate(driver, testSteps, inRate, selectComparator, derivedRateType, derivedRateValue,"BaseRate");
			app_logs.info("Derived Rate is: "+ derivedRatePlanAmount);
			getData(derivedRatePlanAmount);
			derivedRatePlanAmounts=derivedRate.getRatesOnBasisDate(driver, derivedRatePlanAmount,"BaseRate");
			app_logs.info("Final Derived Rate is: "+ derivedRatePlanAmounts);
		
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "Get Rates of Base Rate Plan",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "Get Rates of Base Rate Plan", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			
			testSteps.add("<b>===== Verify Vrbo Rates =====</b>");
			app_logs.info("===== Verify Vrbo Rates =====");
			
			int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
			app_logs.info(statusCode);
			vrboObject.veifyVrboClientStatusCode(statusCode, testSteps);
			Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);
			HashMap<String, ArrayList<ArrayList<String>>> vrboRate = vrboObject
					.getAllRatesBaseRateWithDateRange(driver, responseBody);
			System.out.println(vrboRate);
			vrboObject.verifyVrboBaseRate(derivedRatePlanAmounts, vrboRate, datesRangeList, testSteps);

			/*if(selectComparator.equalsIgnoreCase("greater than"))
			{
			Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify the Derived when base rate plan selected default for an airbnb client");
			testSteps.add("Verify the Derived when base rate plan selected default for an airbnb client"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802164' target='_blank'>"
				+ "Click here to open TestRail: 802164</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			Utility.testCasePass(Utility.statusCode,1,Utility.comments,"Verify the Derived Rate with all entities Always available for an airbnb/VRBO client");
			testSteps.add("Verify the Derived Rate with all entities Always available for an airbnb/VRBO client"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802165' target='_blank'>"
				+ "Click here to open TestRail: 802165</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			}*/
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "Verification on VrBO",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "Verification on VrBO", testName,
					test_description, test_catagory, testSteps);
		}
		}	
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyDerivedRateOnVrbo", otaexcel);
	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
		 Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
	}


}
