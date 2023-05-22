package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;


public class RateV2VerifyAdditionalAdultAndChildOnReservation extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	HashMap<String, String> getRatesRoomClass = new HashMap<String, String>();
	HashMap<String, String> getRatesChannels = new HashMap<String, String>();
	HashMap<String, String> getRatesPerNightChannels = new HashMap<String, String>();
	List<Date> dates = new ArrayList<Date>();
	ArrayList<String> ratesis = new ArrayList<String>();
	ArrayList<String> roomAbbri = new ArrayList<String>();
	
	HashMap<String, String> getRatesRoomClassFromTapeChart = new HashMap<String, String>();
	String testName = this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservation = new CPReservationPage();
	RoomClass rc = new RoomClass();
	Tapechart tapechart= new Tapechart();
	String seasonStartDate = null, seasonEndDate = null, roomClassNameIs = null, roomClassAbbIs = null,
			capacityAdult = null, capacityChild = null, checkInDates=null,checkOutDates = null,  amountDateWise=null,
			addAdultPerNights=null, addChildPerNights=null,maxAdult=null, maxChild=null, rateIs=null;
	String[] roomClassArray = null, maxAdultsIS = null, maxPersonsIs = null, ratePerNightIs = null,
			addAdultPerNight = null, addChildPerNight = null, abb=null ;
	RatesGrid rateGrid = new RatesGrid();
	RoomClass roomClass = new RoomClass();
    Double rateInDouble=0.00;
	
	ArrayList<String> capacityAdult1 = new ArrayList<String>();
	ArrayList<String> capacityChild1 = new ArrayList<String>();
	ArrayList<String> ratePlanAdults = new ArrayList<String>();
	ArrayList<String> ratePlanChilds = new ArrayList<String>();
	
	ArrayList<HashMap<String,String>> ratesList = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> exAdultList = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> exChildList = new ArrayList<HashMap<String,String>>();
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
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "RateGrid")
	public void verifyAdditionalAdultAndChildOnReservation(String checkInDate, String checkOutDate, String ratePlanName,
			String roomClasses, String adult, String children, String  channelName) {
		testName="Verify Rate On Reservation and TapeChart with Adult:  <b>"+ adult+" </b> Children: <b>"+ children +"</b>";
		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1811' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1811</a>";
		test_catagory = "RateGrid";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		try {
			
			
			if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkOutDate)))
			{
				checkInDates = Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat"));
				checkOutDates = Utility.parseDate(Utility.getDatePast_FutureDate(1),
						ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat"));
			}
			else
			{
				checkInDates=Utility.parseDate(checkInDate, ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat"));
				checkOutDates = Utility.parseDate(checkInDate,ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat"));
				
			}
			app_logs.info(checkInDates);
			app_logs.info(checkOutDates);
			
			
			} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description,
					test_catagory, testSteps);

		}
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			driver = getDriver();
			loginRateV2(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
	
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);

		}
		//Get Abbreviations
		try
		{
			roomClassArray = roomClasses.split("\\|");
			app_logs.info(roomClassArray[0]);
			app_logs.info(roomClassArray[1]);
			testSteps.add("=====Getting Abbreviations=====");
			navigation.clickSetup(driver);
			navigation.roomClass(driver);
			rc.getRoomClassAbbrivations(driver, testSteps, roomAbbri, roomClassArray[0]);
			 abb=roomAbbri.get(0).split(":");
			 app_logs.info(abb[1]);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Get Abbreviations", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Abbreviations", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);

		}
			// Navigate to Rate Grid

		try {
		
			navigation.Inventory_Backward(driver);
			testSteps.add("Navigated to Inventory");
			navigation.ratesGrid(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description,
					test_catagory, testSteps);

		}
		 
		// Get Rate Plan Data
		try {
				
			rateGrid.clickForRateGridCalender(driver, testSteps);
			rateGrid.selectDateFromDatePicker(driver, checkInDates, ratesConfig.getProperty("defaultDateFormat"), testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);	
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			
			Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"), checkInDates);
			app_logs.info("Start Date: " + fromDate);
			Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"), checkOutDates);
			app_logs.info("End Date: " + toDate);
			dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
			app_logs.info("Dates Are: " + dates);
			

			
			String[] rm =roomClasses.split("\\|");
			for(int k=0;k<rm.length;k++) {
				HashMap<String, String> getRates = new HashMap<String, String>();
				HashMap<String, String> getExAdult = new HashMap<String, String>();
				HashMap<String, String> getExChild = new HashMap<String, String>();
			rateGrid.expandRoomClass(driver, testSteps, rm[k]);
			testSteps.add("===Get Data From Rate Plan===");
			getRatesPerNightChannels=rateGrid.getRoomRatesExAdExChOfChannel(driver, checkInDates, checkOutDates, rm[k], channelName);
			app_logs.info(getRatesPerNightChannels);
			rateGrid.collapseRoomClass(driver, testSteps, rm[k]);
			getRates.clear();
			getExAdult.clear();
			getExChild.clear();
			getData(getRatesPerNightChannels);
			for(int i=0;i<dates.size();i++)
			{
				String rateIs= StringUtils.substringBetween(getRatesPerNightChannels.get(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))), "RRate:", "ExCh:");
				app_logs.info(rateIs);				
				String ch=StringUtils.substringBetween(getRatesPerNightChannels.get(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))), "ExCh:", "ExAd:");
				app_logs.info(ch);
				String ad=StringUtils.substringAfter(getRatesPerNightChannels.get(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))), "ExAd:");
				app_logs.info(ad);
				getRates.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i)),rateIs);
				getExAdult.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i)),ad);
				getExChild.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i)),ch);
			
			}
			ratesList.add(getRates);
			exAdultList.add(getExAdult);
			exChildList.add(getExChild);
			}
			app_logs.info(ratesList);
			app_logs.info(exAdultList);
			app_logs.info(exChildList);
			rateGrid.clickEditIcon(driver, testSteps);
			nightlyRate.switchCalendarTab(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, checkInDates);
			nightlyRate.clickEditThisSeasonButton(driver, testSteps);
			
			capacityAdult = nightlyRate.getAdultCapacity(driver, roomClassArray[0]);
			capacityChild = nightlyRate.getChildCapacity(driver, roomClassArray[0]);
			maxAdult=nightlyRate.getMaxAdult(driver, roomClassArray[0]);
			maxChild=nightlyRate.getMaxPersons(driver, roomClassArray[0]);	
			testSteps.add("Room Class Adult Capacity: <b>" +capacityAdult+"</b>" );
			testSteps.add("Room Class Person's Capacity: <b>" +capacityChild+"</b>" );
			testSteps.add("Rate Plan Max. Adults: <b>" +maxAdult+"</b>" );
			testSteps.add("Rate Plan Max. Childs: <b>" +maxChild+"</b>" );
			
		
			for(String str: roomClassArray){ 
				capacityAdult1.add(nightlyRate.getAdultCapacity(driver, str));
				capacityChild1.add(nightlyRate.getChildCapacity(driver, str));
				ratePlanAdults.add(nightlyRate.getMaxAdult(driver, str));
				ratePlanChilds.add(nightlyRate.getMaxPersons(driver, str));	
			}
					
			testSteps.add("Room Class Adult Capacity: <b>" +capacityAdult1+"</b>" );
			testSteps.add("Room Class Person's Capacity: <b>" +capacityChild1+"</b>" );
			testSteps.add("Rate Plan Max. Adults: <b>" +ratePlanAdults+"</b>" );
			testSteps.add("Rate Plan Max. Childs: <b>" +ratePlanChilds+"</b>" );
			nightlyRate.closeSeason(driver, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "get value from  Nightly Rate Plan", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "get value from Nightly Rate Plan", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);

		}

		// Verify Rate on Reservation Page
		try
		{
			
			testSteps.add("===Verified Amount if Reservation Adults is <b>" + adult
					+ "</b> and Reservation Child is <b>" + children + " </b>====");
			testSteps.add("RatePlan has Max. Adults as <b>" + maxAdult + "</b> and Max. Persons as <b>"
					+ maxChild + " </b>");
			navigation.Reservation_Backward_3(driver);
			reservation.click_NewReservation(driver, testSteps);
			reservation.select_CheckInDate(driver, testSteps, checkInDates);
			reservation.select_CheckoutDate(driver, testSteps, checkOutDates);
			reservation.enter_Adults(driver, testSteps, adult);
			reservation.enter_Children(driver, testSteps, children);
			reservation.select_Rateplan(driver, testSteps, ratePlanName, "");
			reservation.clickOnFindRooms(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			for(int i=0;i<roomClassArray.length;i++) {
			double str=	reservation.calculattionRatesAsPerAdultsAndChildCapacityMRB(driver, ratesList.get(i), ratePlanAdults.get(i),
					ratePlanChilds.get(i), exAdultList.get(i), exChildList.get(i), capacityChild1.get(i), capacityAdult1.get(i), adult,
					children, testSteps, ratesConfig.getProperty("noCombination"), roomClassArray[i], dates,ratesConfig.getProperty("defaultDateFormat"));
			app_logs.info(str);
			}
			reservation.closeReservationTab(driver);
			app_logs.info("Close Reservation Tab");
			
		
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Rates On Reservation Page", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Rates On Reservation Page", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);

		}
		// Verify Rate on Tape Chart Page
		try
		{
					testSteps.add("===Verified Amount if Tape Chart Adults is <b>" + adult
					+ "</b> and Reservation Child is <b>" + children + " </b>====");
			testSteps.add("RatePlan has Max. Adults as <b>" + maxAdult + "</b> and Max. Persons as <b>"
					+ maxChild + " </b>");
			navigation.navigateToTapeChartFromReservations(driver);
			tapechart.select_CheckInDate(driver, testSteps, checkInDates);
			tapechart.select_CheckoutDate(driver, testSteps, checkOutDates);
			Utility.ScrollToUp(driver);
			tapechart.enterAdult(driver, adult, testSteps);
			tapechart.enterChildren(driver, children, testSteps);
			tapechart.selectRatePlan(driver, ratePlanName, testSteps);
			tapechart.clickOnSearch(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
		/*	tapechart.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates, maxAdult,
			maxChild, getExAdult, getExChild, capacityChild, capacityAdult, adult,
			children, testSteps, ratesConfig.getProperty("tapeChartMsg"), roomClassArray[0],checkInDates, checkOutDates, ratesConfig.getProperty("defaultDateFormat"),abb[1]);
	*/		
			rateIs=	tapechart.calculateRatesAsPerAdultsAndChildCapacity(driver, ratesList.get(0), ratePlanAdults.get(0),
					ratePlanChilds.get(0), exAdultList.get(0), exChildList.get(0), capacityChild1.get(0), capacityAdult1.get(0), adult,
					children, testSteps, ratesConfig.getProperty("tapeChartMsg"), roomClassArray[0],checkInDates, checkOutDates, ratesConfig.getProperty("defaultDateFormat"),abb[1]);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Rates On TapeChart Page", "Tape Chart", "Tape Chart", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Rates On TapeChart Page", "Tape Chart", "Tape Chart", testName,
					test_description, test_catagory, testSteps);

		}
		
		try		
		{
			
			testSteps.add("===Verified Amount On Reservation Page====");
			boolean roomClassAvail = tapechart.isRoomClassAvailableInTapeChart(driver, abb[1]);
			if(roomClassAvail)
			{
				
				tapechart.clickAvailableSlot(driver, abb[1],testSteps);				
				//reservation.verifyTripSummaryRoomChargesAfterCreateReservationFromTapeChart(driver, testSteps, rateIs);
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Rates On Reservation Page", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Rates On Reservation Page", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);

		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAdAdultChildOnReservation", excel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
