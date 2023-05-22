package com.innroad.inncenter.tests;

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

public class VerifyDerivedRateOnVrboForOverRideCases extends TestCore{
	

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
   NightlyRate nightlyRate= new NightlyRate();
   ArrayList<String> getTest_Steps = new ArrayList<>();
	Response response;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private void getData(HashMap<String, String> data)
	{
		Set set = data.entrySet();
		  Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         testSteps.add("Date : "+ mentry.getKey() + " & Value : " +mentry.getValue().toString());
		      }
	}
	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyDerivedRateOnVrboAfterUpdateBaseRate(String testCaseName, String roomClassName, String channelName,String startDate, String endDate,
			 String updateRate, String isAdditionalChargesForChildrenAdults, String extraAdult,
			 String extraChild, String extraAdultAmount, String extraChildAmount,String overrideDate, String overrideRate, String overrideAdult, String overrideChild,
			 String action, String seasonCreated, String derivedRates) {
		String testName = testCaseName;
		String property=null, classId = null,
				propertyId=null, ratePlanName=null, baseRatePlanName=null;
		HashMap<String,String> derivedRatePlanAmount= new HashMap<String,String> ();
		HashMap<String, ArrayList<HashMap<String, String>>> derivedRatePlanAmounts= new HashMap<String, ArrayList<HashMap<String, String>>>();
		HashMap<String, ArrayList<HashMap<String, String>>> inRate= new HashMap<String, ArrayList<HashMap<String, String>>>();
		 ArrayList<String> offSetData= new  ArrayList<String>();		 
		 ArrayList<String> overriderates = Utility.convertTokenToArrayList(overrideRate, "|");
			ArrayList<String> overrideAdults = Utility.convertTokenToArrayList(overrideAdult, "|");
			ArrayList<String> overrideChilds = Utility.convertTokenToArrayList(overrideChild, "|");
			
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
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
		}catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver,e, test_description, test_catagory, "Get Data",
						testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver,e, test_description, test_catagory, "Get Data", testName,
						test_description, test_catagory, testSteps);
			}
		
		try {
			testSteps.add("<b>===== Create Season on Base Rate Plan Rates =====</b>");
			app_logs.info("===== Create Season on Base Rate Plan Rates=====");
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
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			baseRatePlanName= rateGrid.getDefaultRatePlan(driver, testSteps);
			app_logs.info(baseRatePlanName);
			//rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
			if(seasonCreated.equalsIgnoreCase("Yes")) {
			rateGrid.clickEditIcon(driver, testSteps);
			rateGrid.verifyRatePlaninEditMode(driver, testSteps, baseRatePlanName);			
			nightlyRate.switchCalendarTab(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList);
			nightlyRate.deleteSeasonIcon(driver, testSteps);			
			nightlyRate.selectSeasonDates(driver, testSteps, startDate, endDate);
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
			rateGrid.closeRatePlan(driver, testSteps, baseRatePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);			
			Wait.waitUntilPageLoadNotCompleted(driver, 40);	
			}
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "Update Base Rate Plan Rates",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "Update Base Rate Plan Rates", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			
			ArrayList<String> overrideDatesArray = new ArrayList<>();
			if (!(Utility.validateInput(overrideDate))) {
				overrideDate = Utility.getCurrentDatTimeInUTC("dd/MM/yyyy");
			}else {
				overrideDatesArray = Utility.splitInputData(endDate);
				startDate=overrideDatesArray.get(0);
			}
			
			if(derivedRates.equalsIgnoreCase("Yes")) {
				testSteps.add("==================OVERRIDE RATE FROM CHANNEL OF DERIVED RATE==================");
				app_logs.info("==================OVERRIDE RATE FROM CHANNELOF DERIVED RATE ==================");
				rateGrid.expandParentRateGrid(driver, "minus");
				derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);				
				getTest_Steps.clear();
				getTest_Steps = derivedRate.expandReduceDerivedRatePlan(driver, ratePlanName, "plus", getTest_Steps);
				testSteps.addAll(getTest_Steps);
			}else {
				testSteps.add("==================OVERRIDE RATE FROM CHANNEL OF BASE RATE==================");
				app_logs.info("==================OVERRIDE RATE FROM CHANNELOF BASE RATE ==================");
			}
			if (action.equalsIgnoreCase("OverrideRate")) {				
			ArrayList<String> overrideDates = Utility.convertTokenToArrayList(overrideDate, "|");
			rateGrid.overrideRateFromRatesGrid(driver, testSteps, roomClassName, overrideDates, overriderates,
					overrideAdults, overrideChilds,false);
			}else if (action.equalsIgnoreCase("RemoveOverride")) {
				testSteps.add("==================REMOVE OVERRIDE  FROM CHANNEL ==================");
				app_logs.info("================== REMOVE OVERRIDE  FROM CHANNEL ==================");
				ArrayList<String> overrideDates = Utility.convertTokenToArrayList(overrideDate, "|");
				rateGrid.removeOverrideChannelLevel(driver, roomClassName, overrideDates, channelName, testSteps);
				rateGrid.collapseRoomClass(driver, testSteps, roomClassName);
			}
			testSteps.add("==================GET RATE FROM RATE GRID==================");
			app_logs.info("===================GET RATE FROM RATE GRID==================");
			 inRate = rateGrid
						.getRatesWithAdultChildFromChannel(driver, testSteps, roomClassName, channelName, datesRangeList, false);
				app_logs.info("Rate is: "+ inRate);
				testSteps.add("Get Inncenter Rate from rate grid: " + inRate);
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "OVERRIDE RATE FROM CHANNEL",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "OVERRIDE RATE FROM CHANNEL", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			if(!derivedRates.equalsIgnoreCase("Yes")) {
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.clickEditIconOfDerivedRatePlan(driver, ratePlanName, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
				 offSetData=derivedRate.getParentPlanOffset(driver);
			 app_logs.info(offSetData);
				testSteps.add("<b>===== CalCulate Derived Rate Plan Amount =====</b>");
				app_logs.info("=====CalCulate Derived Rate Plan Amount =====");				
				 derivedRatePlanAmount=derivedRate.calculateBaseRateExtraAdultAndChildAsperBaseRate(driver, testSteps, inRate, offSetData.get(2), offSetData.get(1), offSetData.get(0), "BaseRate");
				 app_logs.info("Derived Rate is: "+ derivedRatePlanAmount);
				getData(derivedRatePlanAmount);
				derivedRatePlanAmounts=derivedRate.getRatesOnBasisDate(driver, derivedRatePlanAmount,"BaseRate");
				app_logs.info("Final Derived Rate is: "+ derivedRatePlanAmounts);
			}
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "Get Rates of Derived Rate Plan",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "Get Rates of Derived Rate Plan", testName,
					test_description, test_catagory, testSteps);
		}
		 try {
				
				testSteps.add("<b>===== VERIFY  RATES ON VRBO =====</b>");
				app_logs.info("=====VERIFY  RATES ON VRBO =====");
				
				int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
				app_logs.info(statusCode);
				vrboObject.veifyVrboClientStatusCode(statusCode, testSteps);
				Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);
				HashMap<String, ArrayList<ArrayList<String>>> vrboRate = vrboObject
						.getAllRatesBaseRateWithDateRange(driver, responseBody);
				System.out.println(vrboRate);
				if(!derivedRates.equalsIgnoreCase("Yes")) {
				vrboObject.verifyVrboBaseRate(derivedRatePlanAmounts, vrboRate, datesRangeList, testSteps);}
				else {
					vrboObject.verifyVrboBaseRate(inRate, vrboRate, datesRangeList, testSteps);}				
				if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {		
					if(!derivedRates.equalsIgnoreCase("Yes")) {
					HashMap<String,String> derivedExtraAdult= new HashMap<String,String> ();
					HashMap<String, ArrayList<HashMap<String, String>>> derivedExtraAdults= new HashMap<String, ArrayList<HashMap<String, String>>>();
					HashMap<String,String> derivedExtraChild= new HashMap<String,String> ();
					HashMap<String, ArrayList<HashMap<String, String>>> derivedExtraChilds= new HashMap<String, ArrayList<HashMap<String, String>>>();
					HashMap<String, ArrayList<HashMap<String, String>>> derivedExtraAdultsChilds= new HashMap<String, ArrayList<HashMap<String, String>>>();
					
					testSteps.add("<b>===== CalCulate Derived Extra Adult and Child Plan Amount =====</b>");
					app_logs.info("=====CalCulate Derived  Extra Adult and Child Plan Amount =====");			
					derivedExtraAdult=derivedRate.calculateBaseRateExtraAdultAndChildAsperBaseRate(driver, testSteps, inRate, offSetData.get(2), offSetData.get(1), offSetData.get(0), "ExtraAdult");
					 app_logs.info("Derived Extra Adult is: "+ derivedExtraAdult);
					getData(derivedExtraAdult);
					derivedExtraAdults=derivedRate.getRatesOnBasisDate(driver, derivedExtraAdult,"ExtraAdult");
					app_logs.info("Final Derived Extra Adult is: "+ derivedExtraAdults);
					
					derivedExtraChild=derivedRate.calculateBaseRateExtraAdultAndChildAsperBaseRate(driver, testSteps, inRate, offSetData.get(2), offSetData.get(1), offSetData.get(0), "ExtraChild");
					 app_logs.info("Derived Extra Child is: "+ derivedExtraChild);
					getData(derivedExtraChild);
					derivedExtraChilds=derivedRate.getRatesOnBasisDate(driver, derivedExtraChild,"ExtraChild");
					app_logs.info("Final Derived Extra Child is: "+ derivedExtraChilds);
					
					derivedExtraAdultsChilds=  nightlyRate.mergedtwoHashMap(derivedExtraAdults,derivedExtraChilds);
				    
						app_logs.info("Final  is: "+ derivedExtraAdultsChilds);					
		                testSteps.add("==================GET EXTRA ADULT AND CHILD AMOUNT==================");
		                app_logs.info("===================GET EXTRA ADULT AND CHILD AMOUNT==================");
		                HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> recordsExtraAdultAndChild = vrboObject
		                        .getAllExtraAdultChildDateRange(driver, responseBody);
		                testSteps.add("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
		                app_logs.info("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
		                testSteps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
		                app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
		                vrboObject.verifyExtraChildAndAdultAmountWithDateRange(driver, derivedExtraAdultsChilds,
		                        recordsExtraAdultAndChild.get("ExtraAdult"), recordsExtraAdultAndChild.get("ExtraChild"),
		                        datesRangeList, testSteps);
					}else {
						 	testSteps.add("==================GET EXTRA ADULT AND CHILD AMOUNT==================");
			                app_logs.info("===================GET EXTRA ADULT AND CHILD AMOUNT==================");
			                HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> recordsExtraAdultAndChild = vrboObject
			                        .getAllExtraAdultChildDateRange(driver, responseBody);
			                testSteps.add("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
			                app_logs.info("Vrbo Extra Adult/Child Rate: " + recordsExtraAdultAndChild);
			                testSteps.add("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
			                app_logs.info("==================VERIFY EXTRA ADULT/CHILD RATE ON VRBO==================");
			                vrboObject.verifyExtraChildAndAdultAmountWithDateRange(driver, inRate,
			                        recordsExtraAdultAndChild.get("ExtraAdult"), recordsExtraAdultAndChild.get("ExtraChild"),
			                        datesRangeList, testSteps);
					}
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
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyVrboAfteOverRide", otaexcel);
	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
