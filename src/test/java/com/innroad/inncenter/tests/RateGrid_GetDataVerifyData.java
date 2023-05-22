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

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class RateGrid_GetDataVerifyData extends TestCore{

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	HashMap<String, String> availibility = new HashMap<String, String>();
	HashMap<String, String> roomClassRates = new HashMap<String, String>();
	HashMap<String, String> channelRates = new HashMap<String, String>();
	HashMap<String, String> minStayRates = new HashMap<String, String>();
	HashMap<String, String> checkInRates = new HashMap<String, String>();
	HashMap<String, String> checkoutRates = new HashMap<String, String>();
	HashMap<String, String> roomRateExtradAExtraCh = new HashMap<String, String>();
	HashMap<String, String> roomRateExtradAExtraChForChannel = new HashMap<String, String>();
	String testName= this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	RatesGrid ratesGrid = new RatesGrid();
	String calendarTodayDate = null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		
		
		app_logs.info(ratesConfig.getProperty("previousPrice"));
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);	
	}
	
	
	private void getData(HashMap<String, String> data)
	{
		Set set = data.entrySet();
		  Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         testSteps.add("key is: "+ mentry.getKey() + " & Value is: " +mentry.getValue().toString());
		      }
	}
		 
		
	
	@Test(dataProvider = "getData", groups = "RateGrid")
	public void rateGridVerification(String ratePlanName, String startDate, String endDate, String roomClass, String channelName )
	{
		
		test_description = testName + "<br>";
		test_catagory = "RateGrid";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		//Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
					
			
			
			driver = getDriver();
			loginWPI(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
			
		} catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, testSteps);
			
		}
		try
		{
			navigation.inventory(driver);
			testSteps.add("Navigated to Inventory");
			navigation.ratesGrid(driver,testSteps);	
		/*	getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			ratesGrid.selectDateFromDatePicker(driver, startDate, "dd/MM/yyyy", testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			ratesGrid.expandRoomClass(driver, testSteps, roomClass);			
			ratesGrid.expandChannel(driver, testSteps, roomClass, channelName);
			*/
	
			testSteps.add("Rate Plan Is: <b>" +ratePlanName +"</b>");
			roomRateExtradAExtraCh=ratesGrid.getRoomRatesExAdExChOfRoomClass(driver, startDate, endDate, roomClass);
			testSteps.add("RoomClass <b>"+roomClass+"</b> OverRide as per Date  Are: ");
			getData(roomRateExtradAExtraCh);
			
		roomRateExtradAExtraChForChannel=ratesGrid.getRoomRatesExAdExChOfChannel(driver, startDate, endDate, roomClass, channelName);		
			testSteps.add("RoomClass <b>"+roomClass+"</b> Channel: <b>"+ channelName+"</b> OverRide as per Date  Are:");
			getData(roomRateExtradAExtraChForChannel);
			
				//Get Availibility
			availibility=ratesGrid.getAvailabilityOfRoomClass(driver, startDate, endDate, roomClass);
			testSteps.add("RoomClass <b>"+roomClass+"</b> Availibility as per Date  Are: ");				
			getData(availibility);
			
			
			roomClassRates=ratesGrid.getRatesOfRoomClass(driver, startDate, endDate, roomClass);
			testSteps.add("RoomClass <b>"+roomClass+"</b> Rate as per Date Are: ");	
			getData(roomClassRates);
			
			
			channelRates=ratesGrid.getRatesOfChannel(driver, startDate, endDate, roomClass, channelName);
			testSteps.add("RoomClass <b>"+roomClass+"</b> Channel: <b>"+ channelName+"</b> Rate as per Date  Are:");
			getData(channelRates);
			
			
			minStayRates=ratesGrid.getMinStayRulesOfChannel(driver, startDate, endDate, roomClass, channelName, ratesConfig.getProperty("minStay"));
			testSteps.add("RoomClass <b>"+roomClass+"</b> Channel: <b>"+ channelName+"</b> Min Stay Rule as per Date  Are:");
			getData(minStayRates);
			
			
	
			checkInRates=ratesGrid.getCheckInCheckOutRulesOfChannel(driver, startDate, endDate, roomClass, channelName, ratesConfig.getProperty("checkinRule"));
			testSteps.add("RoomClass <b>"+roomClass+"</b> Channel: <b>"+ channelName+"</b> Check In Rule as per Date  Are:");
			getData(checkInRates);
			
			
			checkoutRates=ratesGrid.getCheckInCheckOutRulesOfChannel(driver, startDate, endDate, roomClass, channelName, ratesConfig.getProperty("checkoutRule"));
			testSteps.add("RoomClass <b>"+roomClass+"</b> Channel: <b>"+ channelName+"</b> Check Out Rule as per Date  Are:");
			getData(checkoutRates);
			
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Get Availibility", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Availibility", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			
		}
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("RateGrid_GetDataVerifyData", envLoginExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
