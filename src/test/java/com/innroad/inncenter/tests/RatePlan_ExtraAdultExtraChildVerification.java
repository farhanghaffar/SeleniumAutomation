package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class RatePlan_ExtraAdultExtraChildVerification extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	ArrayList<String> channelNames = new ArrayList<>();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	List<Date> dates = new ArrayList<Date>();
	ArrayList<String> dateList = new ArrayList<String>();
	HashMap<String, String> ratesList= new HashMap<String, String>();
	String testName = this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate= new NightlyRate();
	String seasonStartDate = null, seasonEndDate = null,dateTimeZoneBasis=null,dateIs=null;
	String[] roomClass = null;
	String[] rates = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "RateGrid")
	public void rateGridOverrideVerification(String delim, String planType, String ratePlanName,
			String folioDisplayName, String description, String channels, String roomClasses,
			String isRatePlanRistrictionReq, String isMinStay, String isMaxStay, String isMoreThanDaysReq,
			String isWithInDaysReq, String seasonName, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String isAddRoomClassInSeason,
			String isSerasonLevelRules,String isAssignRulesByRoomClass, String seasonPolicyType, String seasonPolicyValues,String isSeasonPolicies,String rate, String newAdult, String newChild) {
		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1733' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1733</a>";
		test_catagory = "RateGrid";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		try {
			dateTimeZoneBasis=Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat"), ratesConfig.getProperty("timeZone"));
			app_logs.info(dateTimeZoneBasis);	
			
			seasonStartDate = Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat"));
			seasonEndDate = Utility.parseDate(Utility.getDatePast_FutureDate(6), ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat"));
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			
			Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"), seasonStartDate);
			app_logs.info("Start Date: " + fromDate);
			Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"), seasonEndDate);
			app_logs.info("End Date: " + toDate);
			dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
			app_logs.info("Dates Are: " + dates);
			for (Date d : dates) {
				dateList.add(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), d));
			}
		
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
			//loginWPI(driver);
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

		// Navigate to Rate Grid
		try {
			navigation.inventory(driver);
			testSteps.add("Navigated to Inventory");
			navigation.ratesGrid(driver, testSteps);

		} catch (Exception e) {
			Utility.catchException(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description,
					test_catagory, testSteps);

		}

		// Create Rate Plan
		try {
			testSteps.add("<b>============Create new Rate Plan============</b>");
			Utility.DELIM = "\\"+delim;
			nightlyRate.createNightlyRatePlan(driver, planType, ratePlanName, folioDisplayName, testStepsOne, description,
					channels, roomClasses, isRatePlanRistrictionReq, "", isMinStay, "", isMaxStay, "",
					isMoreThanDaysReq, "", isWithInDaysReq, "", "", seasonName, seasonStartDate, seasonEndDate,
					isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
					isAdditionalChargesForChildrenAdults, ratePerNight, "", "", "", "", isAddRoomClassInSeason, "", "",
					"", "", "", "",isSerasonLevelRules, isAssignRulesByRoomClass, "", "", "", "", "", "", "", "", "", "", seasonPolicyType, seasonPolicyValues,isSeasonPolicies);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			testSteps.add("Rate Plan Created Successfully : <b>" + Utility.rateplanName + " </b>");
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);

		}

		try {
			testSteps.add("<b>============Select Rate Plan from Drop Down Box============</b>");
			rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
			rateGrid.clickRatePlanArrow(driver, testStepsOne);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, Utility.rateplanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			roomClass = roomClasses.split("\\|");
			app_logs.info(roomClass[0]);
			app_logs.info(roomClass[1]);
			rates = ratePerNight.split("\\|");
			app_logs.info(rates[0]);
			app_logs.info(rates[1]);

			

		} catch (Exception e) {
			Utility.catchException(driver, e, "Select RatePlan", "RateGrid", "RateGrid", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Select RatePlan", "RateGrid", "RateGrid", testName, test_description,
					test_catagory, testSteps);

		}
		try
		{
			
			ratesList=rateGrid.getRatesOfRoomClass(driver, seasonStartDate, seasonEndDate,roomClass[0]);
			for(int i=0;i<ratesList.size();i++)
			{
				if(ratesList.get(dateList.get(i)).equals(rates[0]))
				{
					String weekDay=Utility.parseDate(dateList.get(i), ratesConfig.getProperty("defaultDateFormat"), ratesConfig.getProperty("weekDayFormat"));
					String monthDate=Utility.parseDate(dateList.get(i), ratesConfig.getProperty("defaultDateFormat"), ratesConfig.getProperty("monthDateFormat"));
					dateIs=weekDay+", "+monthDate;
					app_logs.info(dateIs);
					break;
				}
				
			}
			rateGrid.expandRoomClass(driver, testStepsOne, roomClass[0]);
			channelNames=rateGrid.getChannelofSpecificRoomClass(driver, roomClass[0]);
			testSteps.add("============Room Class <b>"+roomClass[0]+" </b>has <b>"+channelNames.size()+"</b> Channel============<br>");
			testSteps.addAll(channelNames);
			testSteps.add("<b>============ Verify Date on Rate Popup ============</b>");
			rateGrid.verifyRoomRatePoupDate(driver, testSteps, roomClass[0], rates[0], dateIs);			
			testSteps.add("<b>============ Update Extra Adult and Extra Child at Room Class Level============</b>");
			testSteps.add("Room Class Is: <b>" +roomClass[0] +"</b>");
			rateGrid.updateExtraAdultsAndExtraChildAtClassLevel(driver, testSteps, roomClass[0], rates[0], newAdult, newChild);		
			testSteps.add("<b>============Verify Override Extra Adult and Extra Child at Channel Level============</b>");
			rateGrid.verifyExAAndExChAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], newAdult, newChild);
			rateGrid.verifyExAAndExChAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(1), rates[0], newAdult, newChild);
			testSteps.add("<b>============Removed Override at Room Class Level============</b>");
			testSteps.add("Room Class Is: <b>" +roomClass[0] +"</b>");
			rateGrid.removeOverRide(driver, testSteps, roomClass[0], rates[0], rates[0]);
			testSteps.add("<b>============Verify Extra Adult and Extra Child at Channel Level After Removed Override at Class Level============</b>");
			rateGrid.verifyExAAndExChAtChannelLevelAfterRemovedOverRide(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], "", "");
			rateGrid.verifyExAAndExChAtChannelLevelAfterRemovedOverRide(driver, testSteps, roomClass[0], channelNames.get(1), rates[0], "", "");
			testSteps.add("<b>============ Update Extra Adult and Extra Child at Channel Level============</b>");
			testSteps.add("Channel <b>" + channelNames.get(0)+ "</b> Rate Is: <b>" +rates[0] + "</b>");
			rateGrid.updateExtraAdultsAndExtraChildAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], String.valueOf(Integer.parseInt(newAdult)+3), String.valueOf(Integer.parseInt(newChild)+3));
			testSteps.add("Channel <b>" + channelNames.get(1)+ "</b> Rate Is: <b>" +rates[0] + "</b>");
			rateGrid.updateExtraAdultsAndExtraChildAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(1), rates[0], newAdult, newChild);
			testSteps.add("<b>============Verify Override Extra Adult and Extra Child at Class Level============</b>");
			rateGrid.verifyExAAndExChAtClassLevel(driver, testSteps, roomClass[0],  rates[0], String.valueOf(Integer.parseInt(newAdult)+3), String.valueOf(Integer.parseInt(newChild)+3));
			testSteps.add("============Removed Override of Channel <b>"+channelNames.get(1)+"</b>============");
			rateGrid.removeOverRide(driver, testSteps, roomClass[0], channelNames.get(1), rates[0], rates[0]);
			testSteps.add("<b>============Verify Extra Adult and Extra Child at Class  and another Channel============</b>");
			rateGrid.verifyExAAndExChAtClassLevel(driver, testSteps,  roomClass[0], rates[0],String.valueOf(Integer.parseInt(newAdult)+3), String.valueOf(Integer.parseInt(newChild)+3));
			rateGrid.verifyExAAndExChAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], String.valueOf(Integer.parseInt(newAdult)+3), String.valueOf(Integer.parseInt(newChild)+3));
			testSteps.add("OverRide not removed of Class <b>"+roomClass[0]+ " </b>And Channel <b>" +channelNames.get(0) +"</b>");
			testSteps.add("============Removed Override of Channel <b>"+channelNames.get(0)+"</b>============");
			rateGrid.removeOverRide(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], rates[0]);
			testSteps.add("<b>============Verify Extra Adult and Extra Child at Class  and  Channel Level============</b>");
			rateGrid.verifyExAAndExChAtClassLevelAfterRemovedOverRide(driver, testSteps, roomClass[0], rates[0], "", "");
			rateGrid.verifyExAAndExChAtChannelLevelAfterRemovedOverRide(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], "", "");
			testSteps.add("OverRide removed of Class <b>"+roomClass[0]+ " </b>And Channel <b>" +channelNames.get(0) +"</b>");
			ArrayList<String> ratesList=rateGrid.updateRateForChannel(driver, testSteps, roomClass[0], channelNames.get(1), rates[0], 0, Integer.parseInt(rate), ratesConfig.getProperty("decreaseText"));
			testSteps.add("============ Update Extra Adult and Extra Child at Channel level============</b>");
			testSteps.add("Channel Is: <b>" + channelNames.get(1)+ "</b> Rate Is : <b>" +ratesList.get(1) +"</b>");
			rateGrid.updateExtraAdultsAndExtraChildAtChannelLevelForOverrideRate(driver, testSteps, roomClass[0], channelNames.get(1), ratesList.get(1), String.valueOf(Integer.parseInt(newAdult)+3), String.valueOf(Integer.parseInt(newChild)+3));
			testSteps.add("Channel Is: <b>" + channelNames.get(0)+ "</b> Rate Is : <b>" +rates[0] +"</b>");
			rateGrid.updateExtraAdultsAndExtraChildAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], newAdult, newChild);
			testSteps.add("<b>============ Verify Lowest Price of Extra Adult and Extra Child  Displayed at Class Level============</b>");
			testSteps.add("<b>"+roomClass[0]+" </b>Rate  is: <b>"+ratesList.get(1)+"</b>");
			testSteps.add("<b>"+roomClass[0]+" -- "+channelNames.get(0)+"</b> Rate is: <b>"+rates[0]+"</b>");
			testSteps.add("<b>"+roomClass[0]+" -- "+channelNames.get(1)+"</b> Rate is: <b>"+ratesList.get(1)+"</b>");
			rateGrid.verifyExAAndExChAtClassLevel(driver, testSteps,  roomClass[0], ratesList.get(1),String.valueOf(Integer.parseInt(newAdult)+3), String.valueOf(Integer.parseInt(newChild)+3));
			testSteps.add("============ Update Extra Adult and Extra Child at Already overridden Room rate of Channel level============</b>");
			testSteps.add("Channel Is: <b>" + channelNames.get(1)+ "</b> Rate Is : <b>" +ratesList.get(1) +"</b>");
			rateGrid.updateExtraAdultsAndExtraChildAtChannelLevelForOverrideRate(driver, testSteps, roomClass[0], channelNames.get(1), ratesList.get(1), String.valueOf(Integer.parseInt(newAdult)+5), String.valueOf(Integer.parseInt(newChild)+5));
			testSteps.add("<b>============ Verify Extra Adult and Extra Child  Already overridden Room rate at Class Level============</b>");
			testSteps.add("<b>"+roomClass[0]+" </b>Rate  is: <b>"+ratesList.get(1)+"</b>");
			rateGrid.verifyExAAndExChAtClassLevel(driver, testSteps,  roomClass[0], ratesList.get(1),String.valueOf(Integer.parseInt(newAdult)+5), String.valueOf(Integer.parseInt(newChild)+5));
				
		}catch (Exception e) {
			Utility.catchException(driver, e, "Override Scenario", "RateGrid", "RateGrid", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Override Scenario", "RateGrid", "RateGrid", testName, test_description,
					test_catagory, testSteps);

		}
		
		try
		{
			 testSteps.add("<b>======Delete Rate Plan========</b>");
			 rateGrid.clickDeleteIcon(driver, testSteps);
			 rateGrid.verifyDeletedMsg(driver, testSteps, ratesConfig.getProperty("deleteRatePlanMsg"));
			 rateGrid.clickDeleteButton(driver, testSteps);
			 rateGrid.clickRatePlanArrow(driver, testSteps);
			 ratePlanNames=rateGrid.getRatePlanNames(driver);
			 rateGrid.verifyDeletedRatePlan(driver, testSteps, Utility.rateplanName, ratePlanNames);
				
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

			
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Delete Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			
	}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("RatePlanExAdExChVerify", envLoginExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
