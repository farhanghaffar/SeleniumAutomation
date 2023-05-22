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
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class RatePlan_RoomClassVerification extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	ArrayList<String> testStepsTwo = new ArrayList<>();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	String testName = this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	RoomClass roomClass = new RoomClass();
	RatesGrid rateGrid= new RatesGrid();
	String roomClassNameIs = null,roomClassAbbIs = null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "RateGrid")
	public void rateGridRoomClassVerification(String roomClassName, String roomClassAbb,String maxAdults, String maxPersons, String roomQuantity)
	{
		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1715' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1715</a>";
		test_catagory = "RateGrid";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
	
		String randomNum = Utility.GenerateRandomNumber();
		roomClassNameIs=roomClassName+"_"+randomNum;
		roomClassAbbIs=roomClassAbb+"_"+randomNum;
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
				try
				{
					testSteps.add("<b>============Create new Room Class============</b>");
					navigation.setup(driver);
					navigation.roomClass(driver);
					navigation.clickOnNewRoomClassButton(driver, testSteps);
					roomClass.create_RoomClass(driver, roomClassNameIs, roomClassAbbIs, null, maxAdults, maxPersons, roomQuantity,
							test, testSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Create Rate Room Class", "Room Class", "Room Class", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Rate Room Class", "Room Class", "Room Class", testName, test_description,
							test_catagory, testSteps);

				}
				
				try
				{
					testSteps.add("<b>============Verify Room Class For Rate Plan After Create Room Class============</b>");
					navigation.inventory(driver);
					testSteps.add("Navigated to Inventory");
					navigation.ratesGrid(driver, testSteps);
					Wait.waitUntilPageLoadNotCompleted(driver, 50);
					rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
					rateGrid.verifyRatePlanSingleRoomClass(driver, roomClassNameIs, testSteps);
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Room Class For Rate Plan", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Room Class For Rate Plan", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);

				}
				
				try
				{
					testSteps.add("<b>============Delete Room Class============</b>");
					navigation.rateV2Setup(driver);
					navigation.roomClass(driver);
					roomClass.searchButtonClick(driver);
					testSteps.add("Click on Search Button");
					app_logs.info("Click on Search Button");
					roomClass.searchRoomClass(driver, roomClassName, testSteps);
					roomClass.deleteRoomClass(driver, roomClassName);							
					testSteps.add("All Room Class Deleted Successfully With Name: <b>" +roomClassName + " </b>");
					app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Delete Room Class", "Room Class", "Room Class", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Delete Room Class", "Room Class", "Room Class", testName, test_description,
							test_catagory, testSteps);

				}
				try
				{
					testSteps.add("<b>============Verify Room Class For Rate Plan After Delete Room Class============</b>");
					navigation.inventory(driver);
					testSteps.add("Navigated to Inventory");
					navigation.ratesGrid(driver, testSteps);
					Wait.waitUntilPageLoadNotCompleted(driver, 50);
					rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
					rateGrid.verifyRoomClassAfterDelete(driver, roomClassNameIs, testSteps);					
			
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Room Class For Rate Plan", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Room Class For Rate Plan", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);

				}
				
				try
				{
					testSteps.add("<b>============Verify Show room availability Disabled ============</b>");
					testStepsTwo=rateGrid.clickSettingButton(driver);
					testSteps.addAll(testStepsTwo);
					testStepsTwo=rateGrid.showroomavailability(driver, false);
					testSteps.addAll(testStepsTwo);
					testStepsTwo=rateGrid.clickSettingButtonAgain(driver);
					testSteps.addAll(testStepsTwo);					
					rateGrid.verifyAvailibilityDisabled(driver, testSteps);
					testSteps.add("<b>============Verify Show room availability Enabled============</b>");
					testStepsTwo=rateGrid.clickSettingButton(driver);
					testSteps.addAll(testStepsTwo);
					testStepsTwo=rateGrid.showroomavailability(driver, true);
					testSteps.addAll(testStepsTwo);
					testStepsTwo=rateGrid.clickSettingButtonAgain(driver);
					testSteps.addAll(testStepsTwo);	
					rateGrid.verifyAvailibilityEnabled(driver, testSteps);
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Show room availability", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Show room availability", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);

				}
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("RatePlanVerifyRoomClass", excel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
