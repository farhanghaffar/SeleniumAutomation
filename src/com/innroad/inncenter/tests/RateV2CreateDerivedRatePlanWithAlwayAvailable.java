package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class RateV2CreateDerivedRatePlanWithAlwayAvailable extends TestCore {
	
	//AUTOMATION-1744
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void rateV2CreateDerivedRatePlanWithAlwayAvailable(String delim, String RatePlanName,
			String FolioDisplayName, String ratePlanDescription, String Channels, String RoomClasses,
			String RistrictionType, String MinNights, String MaxNights, String MoreThanDaysCount,
			String WithInDaysCount, String PromoCode, String PoliciesType, String PoliciesName, 
			String valueOptions, String percentValue, String USDValue, String dateType,
			String parentRatePlan, String takeRulesFromRateplan) throws InterruptedException, IOException {

		Utility.DELIM = delim;

		test_name = "RateV2CreateDerivedRatePlanWithAlwayAvailable";
		testDescription = "Rates V2 - Create Derived Rate Plan (with always available) and update<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1744' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1744</a>";
		testCatagory = "RateV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		DerivedRate derivedRate = new DerivedRate();
		Policies policies = new Policies();
		//String existingRateName = "";
		ArrayList<String> ratePlanNames = new ArrayList<String>();
		RatePlanName = RatePlanName + Utility.generateRandomString();
		String currencyName = "USD";
		String percent = "percent";


		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();

			try {
				Login login = new Login();
				login.login(driver, envURL, "autorates", "autouser","Auto@123");
				//loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				//loginWPI(driver);
				Login login = new Login();
				login.login(driver, envURL, "autorates", "autouser","Auto@123");
			}
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		ArrayList<String> allCancelationPolicies = new ArrayList<String>();
		ArrayList<String> allDepositPolicies = new ArrayList<String>();
		ArrayList<String> allCheckInPolicies = new ArrayList<String>();
		ArrayList<String> allNoShowPolicies = new ArrayList<String>();
		try {
			navigation.Inventory(driver, testSteps);

				testSteps.add("=================== GET ALL POLICIES ======================");
				app_logs.info("=================== GET ALL POLICIES ======================");
				navigation.policies(driver, testSteps);
				allCancelationPolicies = policies.getCancelationPolicies(driver);
				testSteps.add("Found Cancelation Policies : " + allCancelationPolicies.size());
				app_logs.info("Found Cancelation Policies : " + allCancelationPolicies.size());
				allDepositPolicies = policies.getDepositPolicies(driver);
				testSteps.add("Found Deposit Policies : " + allDepositPolicies.size());
				app_logs.info("Found Deposit Policies : " + allDepositPolicies.size());
				allCheckInPolicies = policies.getCheckInPolicies(driver);
				testSteps.add("Found CheckIn Policies : " + allCheckInPolicies.size());
				app_logs.info("Found CheckIn Policies : " + allCheckInPolicies.size());
				allNoShowPolicies = policies.getNoShowPolicies(driver);
				testSteps.add("Found NoShow Policies : " + allNoShowPolicies.size());
				app_logs.info("Found NoShow Policies : " + allNoShowPolicies.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to get all policies", testName, "GetAllPolicies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to get all policies", testName, "GetAllPolicies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		try {
			// After login
			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rates grid", testName, "NavigateRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to navigate to rates grid", testName, "NavigateRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to select derived rate plan option", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to select derived rate plan option", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, ratePlanDescription, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== " + "SELECT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "SELECT RATE PLAN".toUpperCase() + " ======================");
			derivedRate.selectRatePlan(driver, parentRatePlan, true, testSteps);

			testSteps.add("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");
			app_logs.info("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
					+ " ======================");

			derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand Value Comparison DropDown");
			app_logs.info("Expand Value Comparison DropDown");
			derivedRate.selectDropDownOptions(driver, valueOptions.split(",")[1], testSteps);
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");
	
			derivedRate.selectDropDownOptions(driver, currencyName, testSteps);
			
			testSteps.add("===== ENTER VALUE =====");
			app_logs.info("===== ENTER VALUE =====");
			derivedRate.enterRateValue(driver, USDValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, USDValue, "Failed : Value missmatched");
			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(takeRulesFromRateplan),
					testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			testSteps.add("=================== SELECT DATES ======================");
			app_logs.info("=================== SELECT DATES ======================");
			derivedRate.selectDates(driver, dateType, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.selectChannels(driver, Channels, true, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);
			 Utility.rateplanName=RatePlanName;
			testSteps.add("=================== DERIVED RATE PLAN CREATED ======================");
			app_logs.info("=================== DERIVED RATE PLAN CREATED ======================");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			
			testSteps.add("=================== " + "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ======================");
			app_logs.info("=================== " + "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ======================");
			
			driver.navigate().refresh();
			Wait.wait3Second();
			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, RatePlanName);
			
			testSteps.add("Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info("Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down", testName, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down", testName, "DerivedRatePlanVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try {
			
			testSteps.add("=================== " + "EDIT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "EDIT RATE PLAN".toUpperCase() + " ======================");
			derivedRate.expandReduceDerivedratePlans(driver,true,testSteps);
			derivedRate.derivedratePlanExist(driver,RatePlanName,true,testSteps);
			derivedRate.clickEditIconOfDerivedRatePlan(driver,RatePlanName,testSteps);

			testSteps.add("=================== VERIFY EDIT RATE PLAN INITIAL STATE AND UPDATE ======================");
			app_logs.info("=================== VERIFY EDIT RATE PLAN INITIAL STATE AND UPDATE ======================");
			
			nightlyRate.verifyRatePlanNameEditPage(driver, RatePlanName, testSteps);
			nightlyRate.verifySaveRatePlanButton(driver, false, true, testSteps);
			nightlyRate.verifyRatePlanTypeEditPage(driver, "Derived rate plan", testSteps);
			
			testSteps.add("=================== UPDATE RATE PLAN DEFAULT STATE ===================");
			app_logs.info("=================== UPDATE RATE PLAN DEFAULT STATE ===================");
			nightlyRate.selectAsDefaultRatePlan(driver, testSteps,"Yes");
			nightlyRate.verifyIsDefaultRatePlan(driver, testSteps, true);
			
			testSteps.add("=================== UPDATE RATE PLAN STATUS ===================");
			app_logs.info("=================== UPDATE RATE PLAN STATUS ===================");
			nightlyRate.ratePlanStatusChange(driver, false, testSteps);
			nightlyRate.verifySelectedRatePlanStatus(driver, false, testSteps);
			
			String updateRatePlanName = "Updated" + RatePlanName;
			String updateFolioDisplayName = "Updated" + FolioDisplayName ;
			String updateRatePlanDescription = "Updated" + ratePlanDescription ;
			testSteps.add("=================== UPDATE RATE PLAN NAME ===================");
			app_logs.info("=================== UPDATE RATE PLAN NAME ===================");
			nightlyRate.enterRatePlanName(driver, updateRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, updateRatePlanName, true, testSteps);
			
			testSteps.add("=================== UPDATE RATE PLAN FOLIO DISPLAY NAME ===================");
			app_logs.info("=================== UPDATE RATE PLAN FOLIO DISPLAY NAME ===================");
			nightlyRate.enterRateFolioDisplayName(driver, updateFolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, updateFolioDisplayName, true, testSteps);
			
			testSteps.add("=================== UPDATE RATE PLAN DESCRIPTION ======================");
			app_logs.info("=================== UPDATE RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, updateRatePlanDescription, testSteps);

			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, updateRatePlanDescription, true, testSteps);
			
			testSteps.add("=================== UPDATE PARENT RATE PLAN OFFSET ======================");
			app_logs.info("=================== UPDATE PARENT RATE PLAN OFFSET ======================");
			String getValue = derivedRate.getDropdownValue(driver, 1, testSteps);
			
			if(getValue.equalsIgnoreCase(percent)){
				derivedRate.expandCurrencyValueDropdown(driver, 0);
				testSteps.add("Expand Currency DropDown");
				app_logs.info("Expand Currency DropDown");
				
				derivedRate.selectDropDownOptions(driver, currencyName, testSteps);
	
				derivedRate.enterRateValue(driver, USDValue, testSteps);
				String value = derivedRate.getRateValue(driver, testSteps);
				Assert.assertEquals(value,USDValue,"Failed : Value missmatched");
				
			}else{
				derivedRate.expandCurrencyValueDropdown(driver, 0);
				testSteps.add("Expand Currency DropDown");
				app_logs.info("Expand Currency DropDown");
				
				derivedRate.selectDropDownOptions(driver, percent, testSteps);
				
				derivedRate.enterRateValue(driver, percentValue, testSteps);
				String value = derivedRate.getRateValue(driver, testSteps);
				Assert.assertEquals(value,percentValue + "%","Failed : Value missmatched");
				}
			getValue = derivedRate.getDropdownValue(driver, 2, testSteps);
			
			app_logs.info(valueOptions.split(",")[0]);
			app_logs.info(valueOptions.split(",")[1]);
			if(getValue.equalsIgnoreCase(valueOptions.split(",")[0])){

				derivedRate.expandCurrencyValueDropdown(driver, 1);
				testSteps.add("Expand Value Comparison DropDown");
				app_logs.info("Expand Value Comparison DropDown");
				derivedRate.selectDropDownOptions(driver, valueOptions.split(",")[1], testSteps);
			}else{
				derivedRate.expandCurrencyValueDropdown(driver, 1);
				testSteps.add("Expand Value Comparison DropDown");
				app_logs.info("Expand Value Comparison DropDown");
				derivedRate.selectDropDownOptions(driver, valueOptions.split(",")[0], testSteps);
				
			}


			testSteps.add("=================== UPDATE 'TAKE RULE FROM PARENT RATE PLAN' ======================");
			app_logs.info("=================== UPDATE 'TAKE RULE FROM PARENT RATE PLAN' ======================");
			
			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, !Boolean.parseBoolean(takeRulesFromRateplan), testSteps);
			
			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(takeRulesFromRateplan), testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update derived rate plan", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update derived rate plan", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {		

			testSteps.add("=================== DISTRIBUTION CHANNEL UPDATE ======================");
			app_logs.info("===================  DISTRIBUTION CHANNEL UPDATE ======================");
			nightlyRate.selectChannelsEditPage(driver, Channels, true, testSteps);
			nightlyRate.verifySelectedChannelsEditPage(driver, Channels,true, testSteps);
			
			testSteps.add("=================== ROOM CLASS UPDATE ======================");
			app_logs.info("===================  ROOM CLASS UPDATE ======================");

			nightlyRate.selectRoomClassesEditPage(driver, RoomClasses, false, testSteps);
			nightlyRate.verifySelectedRoomClassesEditPage(driver, RoomClasses,false, testSteps);
			
			nightlyRate.switchRestrictionAndPoliciesTab(driver, testSteps);
			
			testSteps.add("=================== RESTRICTION UPDATE ======================");
			app_logs.info("===================  RESTRICTION UPDATE ======================");
			
			nightlyRate.selectRestrictions(driver, true, RistrictionType, true, MinNights, true, MaxNights, true
					, MoreThanDaysCount, true,WithInDaysCount, PromoCode, testSteps);
			nightlyRate.verifySelectedRestriction(driver, RistrictionType, true, testSteps);
			
			String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(RistrictionType, true, MinNights,
					true, MaxNights, true, MoreThanDaysCount, true, WithInDaysCount, PromoCode);
			nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, testSteps);
			
			testSteps.add("=================== POLICIES UPDATE ======================");
			app_logs.info("===================  POLICIES UPDATE ======================");
			
			PoliciesType = null;
			if(allCancelationPolicies.size()!=0){
				PoliciesType = "Cancellation";
			}
			if(allDepositPolicies.size()!=0){
				PoliciesType = ",Deposit";
			}
			
			if(allCheckInPolicies.size()!=0){
				PoliciesType = ",Check-in";
			}
			if(allCancelationPolicies.size()!=0){
				PoliciesType = ",No Show";
			}
			nightlyRate.selectPolicy(driver, PoliciesType,true, testSteps);
			nightlyRate.verifySelectedPolicy(driver, PoliciesType, true, testSteps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update policies in derived rate plan", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update policies in derived rate plan", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			
			testSteps.add("=================== CALENDAR UPDATE ======================");
			app_logs.info("===================  CALENDAR UPDATE ======================");
			
			nightlyRate.clickCalender(driver, testSteps);
			nightlyRate.switchCalendarTab(driver, testSteps);

			derivedRate.selectDates(driver, "Custom date range", testSteps);
			derivedRate.customDateRangeAppear(driver, true, testSteps);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update calender in derived rate plan", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to update calender in derived rate plan", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		

		
		try {
			
			testSteps.add("==" + "Verify that during modification of rate plan, in Calendar 'View parent rate plan' link is displayed".toUpperCase() + " ==");
			app_logs.info("=================== " + "Verify that during modification of rate plan, in Calendar 'View parent rate plan' link is displayed".toUpperCase() + " ======================");

			derivedRate.verifyViewParentRatePlanLinkExist(driver, testSteps);
			
			testSteps.add("==" + "Verify that on click on 'View parent rate plan' link Parent rate plan popup is displayed".toUpperCase() + " ==");
			app_logs.info("=================== " + "Verify that on click on 'View parent rate plan' link Parent rate plan popup is displayed".toUpperCase() + " ======================");
			
			derivedRate.clickviewParentRatePlanLink(driver, testSteps);
			derivedRate.closeParentRatePlanPopup(driver, testSteps);
			
			testSteps.add("==" + "Verify that 'Load more dates' link should be displayed in the calendar tab's bottom after all the month's dates are displayed".toUpperCase() + "==");
			app_logs.info("=================== " + "Verify that 'Load more dates' link should be displayed in the calendar tab's bottom after all the month's dates are displayed".toUpperCase() + " ======================");
			
			derivedRate.verifyLoadMoreDatesExist(driver, testSteps);
			
			testSteps.add("==" + "Verify that during modification, upon click of 'Load more dates' link next 2 months calendar dates are displayed".toUpperCase() + "==");
			app_logs.info("=================== " + "Verify that during modification, upon click of 'Load more dates' link next 2 months calendar dates are displayed".toUpperCase() + " ======================");
			
			derivedRate.clickLoadMoreDates(driver, testSteps);
			
			testSteps.add("==" + "Verify that during modification, upon toggling of Always available and custom date range Calendar data is displayed based on selection.".toUpperCase() + "==");
			app_logs.info("=================== " + "Verify that during modification, upon toggling of Always available and custom date range Calendar data is displayed based on selection.".toUpperCase() + " ======================");

			derivedRate.selectDates(driver, "Always available", testSteps);
			derivedRate.customDateRangeAppear(driver, false, testSteps);
			derivedRate.selectDates(driver, "Custom date range", testSteps);
			derivedRate.customDateRangeAppear(driver, true, testSteps);
			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver,"Yes", testSteps);
			derivedRate.newRateplanTabExist(driver,false,testSteps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify that upon toggling of Always available and custom date range Calendar data is displayed based on selection", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify that upon toggling of Always available and custom date range Calendar data is displayed based on selection", testName, "DerivedRatePlanUpdation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Derived rate Plan
		try {
			
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, RatePlanName);

			testSteps
					.add("=================== " + "DELETE DERIVED RATE PLAN".toUpperCase() + " ======================");
			app_logs.info(
					"=================== " + "DELETE DERIVED RATE PLAN".toUpperCase() + " ======================");
			
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			derivedRate.expandReduceDerivedratePlans(driver, true, testSteps);
			
			derivedRate.derivedratePlanExist(driver, RatePlanName, true, testSteps);
			derivedRate.deleteDerivedRatePlan(driver, RatePlanName, "Cancel", testSteps);
			derivedRate.deleteDerivedRatePlan(driver, RatePlanName, "Delete", testSteps);

			derivedRate.derivedratePlanExist(driver, RatePlanName, false, testSteps);
			testSteps.add("Successfully Delete Derived rate plan '" + RatePlanName + "'");
			app_logs.info("Successfully Delete Derived rate plan '" + RatePlanName + "'");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delete derived rate plan", testName, "DerivedRatePlanDeletion", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delete derived rate plan", testName, "DerivedRatePlanDeletion", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Generate Report
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateDerivedRatePlanV2", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
