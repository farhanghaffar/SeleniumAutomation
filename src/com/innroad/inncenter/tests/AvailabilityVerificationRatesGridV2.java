package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor.BLACK;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class AvailabilityVerificationRatesGridV2 extends TestCore {

	// Automation-1694
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	public static String test_name = "";
	//String scriptName = "AvailabilityVerificationInncenterRatesGridV2";
	public static String test_description = "";
	public static String test_category = "";
	String date = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
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

	@Test(dataProvider = "getData", groups = "Inventory")
	public void availabilityVerification(String Scenario,String RatePlanName, String RoomClasses, String startDate, String endDate, String source, String option, String isOccupancy, 
			String occupancyType, String occupancyValue, String action, String isMon, String isTue, String isWed, String isThu, String isFri, String isSat, String isSun )
			throws InterruptedException, IOException, ParseException {
		test_name = Scenario;
		test_description = "Rates V2 - Rates Grid - Availability Validation<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1694' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1694</a>";

		test_category = "Rates Grid";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		

		Navigation navigation = new Navigation();
		RoomMaintenance roomMaintenance = new RoomMaintenance();
		Reports reports = new Reports();
		Distribution distribution = new Distribution();
		NightlyRate nightlyRate = new NightlyRate();

		Tapechart tapechart = new Tapechart();

		CPReservationPage cpReservation = new CPReservationPage();
		RatesGrid ratesGrid = new RatesGrid();
		RoomClass roomClass = new RoomClass();
		Groups group = new Groups();
		AdvGroups advanceGroup = new AdvGroups();
		ArrayList<String> getRoomClasses = new ArrayList<>();
		ArrayList<String> activeChannelsList = new ArrayList<String>();
		HashMap<String, String> preAvailibilityStatus = new HashMap<String, String>();
		HashMap<String, String> postAvailabilityStatus = new HashMap<String, String>();
		HashMap<String, String> AvailabilityChange = new HashMap<String, String>();

		// considering input from excel
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
		
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName, "ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName, "ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
					
			try {
			driver = getDriver();
			loginWPI(driver);
			testSteps.add("Entered appication URL : " + TestCore.envURL);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
					
		try {
				testSteps.add("==========Navigate to Rates Grid=========");
				app_logs.info("==========Navigate to Rates Grid==========");
				
				navigation.Inventory(driver);
				testSteps.add("Navigate Setup");
				navigation.Rates_Grid(driver);
				ratesGrid.searchRatePlan(driver, RatePlanName);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");
				ratesGrid.clickOnAvailabilityTab(driver);
				
				testSteps.add("==========Before Bulk Update=========");
				preAvailibilityStatus=ratesGrid.getAvailableBlockoutOfRoomClasses(driver, testSteps,startDate, endDate, RoomClasses, source);
				app_logs.info("=========================Before Bulk Update=======================================");
				app_logs.info("##################################################################################");
				app_logs.info(preAvailibilityStatus);
				app_logs.info("##################################################################################");
				
				} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
						Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
					} else {
								Assert.assertTrue(false);
							}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
							Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
							} else {
								Assert.assertTrue(false);
							}
					}
					
		try {
				testSteps.add("===============Bulk Update=========");
				ratesGrid.clickOnBulkUpdate(driver);
				ratesGrid.selectBulkUpdateOption(driver, option);
				AvailabilityChange=ratesGrid.bulkUpdateAvailabilityChange(driver, testSteps,startDate, endDate, RoomClasses,isOccupancy, occupancyType, occupancyValue, source, action, isMon, isTue, isWed, isThu, isFri, isSat, isSun );
				app_logs.info("===============================Bulk Update========================================");
				app_logs.info("##################################################################################");
				app_logs.info(AvailabilityChange);
				app_logs.info("##################################################################################");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
						Assert.assertTrue(false);
					}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
					} else {
						Assert.assertTrue(false);
					}
			}
		
		try {
				testSteps.add("==========After Bulk Update=========");
				postAvailabilityStatus=ratesGrid.getAvailableBlockoutOfRoomClasses(driver,testSteps, startDate, endDate, RoomClasses, source);
				app_logs.info("==============================After Bulk Update===================================");
				app_logs.info("##################################################################################");
				app_logs.info(postAvailabilityStatus);
				app_logs.info("##################################################################################");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
						Assert.assertTrue(false);
					}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
					} else {
						Assert.assertTrue(false);
					}
			}
		
		try
		{
			testSteps.add("=====================Validation check=======================");
			app_logs.info("=====================Validation check=======================" );
			ArrayList<String> checkedDays=new ArrayList<>();
			checkedDays=ratesGrid.getCheckedDays(driver,isMon, isTue, isWed, isThu, isFri, isSat, isSun);
			ArrayList<String> filteredDays=new ArrayList<>();
			Set<String> keys = preAvailibilityStatus.keySet();
	        for(String key: keys){
//	            System.out.println(key);
//	            System.out.println(checkedDays);
	            for(int j=0;j<=checkedDays.size()-1;j++)
	            	{
				         if(key.contains(checkedDays.get(j)))
				            {
				            	filteredDays.add(key);
				            	
				            	//testSteps.add("Filtered days are "+filteredDays );
				            	//app_logs.info("Filtered days are "+filteredDays );
				            	
				            }
					
				      }
	           
	        }
	        testSteps.add("Filtered days are "+filteredDays );
        	app_logs.info("Filtered days are "+filteredDays );
				 
	        for(int k=0;k<=filteredDays.size()-1;k++)
			{
				if(preAvailibilityStatus.get(filteredDays.get(k)).equalsIgnoreCase("available"))
				{
					try {
					//System.out.println(preAvailibilityStatus.get(filteredDays.get(k)));
					Assert.assertEquals(postAvailabilityStatus.get(filteredDays.get(k)), "blocked", "Failed messaage");
					app_logs.info(filteredDays.get(k)+ " is changed from available to blocked");
					testSteps.add(filteredDays.get(k)+ " is changed from available to blocked");
					}catch (AssertionError e) {
						app_logs.info(filteredDays.get(k)+ " is available previously also");
						testSteps.add(filteredDays.get(k)+ " is available previously also");
					}
					
					
				}
				else
					
				{
					//System.out.println(preAvailibilityStatus.get(filteredDays.get(k)));
					try {
					Assert.assertEquals(postAvailabilityStatus.get(filteredDays.get(k)), "available", "Failed messaage");
					app_logs.info(filteredDays.get(k)+ " is changed from blocked to available");
					testSteps.add(filteredDays.get(k)+ " is changed from blocked to available");
					}catch (AssertionError e) {
						app_logs.info(filteredDays.get(k)+ " is blocked previously also");
						testSteps.add(filteredDays.get(k)+ " is blocked previously also");
					}
					
				}
				
				
			}
	        RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_category, testSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
						Assert.assertTrue(false);
					}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
					} else {
						Assert.assertTrue(false);
					}
			}
	
		        
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("AvailabilityCheckRatesGridV2", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
