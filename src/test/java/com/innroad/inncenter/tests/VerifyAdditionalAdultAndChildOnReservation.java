package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAdditionalAdultAndChildOnReservation extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	String testName = this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	String seasonStartDate=null,seasonEndDate=null, roomClassNameIs = null,roomClassAbbIs = null;;
	RatesGrid rateGrid= new RatesGrid();
	RoomClass roomClass = new RoomClass();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "RateGrid")
	public void verifyAdditionalAdultAndChildOnReservation(String roomClassName, String roomClassAbb,String maxAdult, String maxPerson, String roomQuantity,String delim,String planType, String ratePlanName, String folioDisplayName,
			String description, String channels, String roomClasses,
			String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights,
			String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
			String isWithInDaysReq, String withInDaysCount, String promoCode,
			 String seasonName, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason,
			String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults,
			String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight,
			String extraRoomClassAdditionalChildPerNight, String isSerasonLevelRules,String isAssignRulesByRoomClass,
			String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday)
	{
		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1811' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1811</a>";
		test_catagory = "RateGrid";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		try
		{
		    seasonStartDate=Utility.getCurrentDate("dd/MM/yyyy");
			seasonEndDate=Utility.parseDate(Utility.getDatePast_FutureDate(6), "MM/dd/yyyy", "dd/MM/yyyy");
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			
			String randomNum = Utility.GenerateRandomNumber();
			roomClassNameIs=roomClassName+"_"+randomNum;
			roomClassAbbIs=roomClassAbb+"_"+randomNum;
		

		}catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description, test_catagory, testSteps);
			
		}
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
			testSteps.add("<b>============Create new Room Class============</b>");
			navigation.setup(driver);
			navigation.roomClass(driver);
			navigation.clickOnNewRoomClassButton(driver, testSteps);
			roomClass.create_RoomClass(driver, roomClassNameIs, roomClassAbbIs, null, maxAdult, maxPerson, roomQuantity,
					test, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Rate Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Rate Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);

		}
		
		//Navigate to Rate Grid
				try
					{
						navigation.inventory(driver);
						testSteps.add("Navigated to Inventory");
						navigation.ratesGrid(driver,testSteps);	
						Wait.waitUntilPageLoadNotCompleted(driver, 50);
						rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
					}
					catch (Exception e) {			
							Utility.catchException(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
						} catch (Error e) {
							Utility.catchError(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
							
					}
		
		//Create Rate Plan
		try
		{
			testSteps.add("<b>============Create new Rate Plan============</b>");				
			Utility.DELIM = delim;
			rateGrid.createNightlyRatePlan(driver, planType, ratePlanName, folioDisplayName, testStepsOne, description, channels, 
					roomClasses, isRatePlanRistrictionReq, ristrictionType, isMinStay, 
					minNights, isMaxStay, maxNights, isMoreThanDaysReq, moreThanDaysCount, isWithInDaysReq, withInDaysCount, 
					promoCode, seasonName,seasonStartDate,seasonEndDate ,isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, 
					isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight, 
					additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason, extraRoomClassRatePerNight, 
					extraRoomClassMaxAdults, extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight, 
					extraRoomClassAdditionalChildPerNight,isSerasonLevelRules, isAssignRulesByRoomClass, seasonRuleSpecificRoomClasses, 
					seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, 
					isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			testSteps.add("Rate Plan Created Successfully : <b>"+Utility.rateplanName+" </b>");				
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			
	}
	
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAdAdultChildOnReservation", envLoginExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
