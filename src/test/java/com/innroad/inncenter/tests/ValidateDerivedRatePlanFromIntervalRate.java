package com.innroad.inncenter.tests;

import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class ValidateDerivedRatePlanFromIntervalRate extends TestCore {
	
	//AUTOMATION-1801
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void validateDerivedRatePlanFromIntervalRate(String delim,String parentRatePlanType, String RatePlanName, String FolioDisplayName,
			String ratePlanDescription, String Channels, String RoomClasses, String RistrictionType, String MinNights,
			String MaxNights, String MoreThanDaysCount, String WithInDaysCount, String PromoCode, String PoliciesType,
			String PoliciesName, String specialCharactersRatePlanName, String moreThanMaximumLengthRatePlanName,
			String specialCharactersFolioDisplayName, String moreThanMaximumLengthFolioDisplayName,
			String specialCharactersRatePlanDescription, String moreThanMaximumLengthRatePlanDescription,
			String descriptionInSeperatelines, 
			String percentValue, String invalidPercentValue, String USDValue, String invalidUSDValue, String dateType)
			throws InterruptedException, IOException {

		Utility.DELIM = delim;

		test_name = "ValidateDerivedRatePlanFromIntervalRate";
		test_description = "Rates V2 - Create Derived Rate Plan having Interval as Base Rate and update<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1801' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1801</a>";
		test_catagory = "RateV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		DerivedRate derivedRate = new DerivedRate();
		Policies policies = new Policies();
		String parentIntervalRateName = "";
		ArrayList<String> ratePlanNames = new ArrayList<String>();
		RatePlanName = RatePlanName + Utility.generateRandomString();
		moreThanMaximumLengthRatePlanName = moreThanMaximumLengthRatePlanName + Utility.generateRandomString();
		String currencyOptions= "USD,percent";
		String valueOptions= "greater than,lesser than";
		String USD = "USD";
		String percent = "percent";
		List<String>[] ratePlansList = new List[3];
		
		  ArrayList<String> nightlyRatePlan = new ArrayList<String>();
		 ArrayList<String> intervalRatePlan = new ArrayList<String>();
		 ArrayList<String> packageRatePlan = new ArrayList<String>();


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
				loginRateV2(driver);
				//login.login(driver, envURL, "autorates", "autouser","Auto@123");
				//loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				Login login = new Login();
				loginRateV2(driver);
				//login.login(driver, envURL, "autorates", "autouser","Auto@123");
				//loginWPI(driver);
			}
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
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
			testSteps.add("=================== GET ALL POLICIES ======================");
			app_logs.info("=================== GET ALL POLICIES ======================");
			navigation.Inventory(driver, testSteps);
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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			// After login
			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			// navigation.Inventory(driver, testSteps);
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== GET ALREADY CREATED "+parentRatePlanType.toUpperCase()+" RATE PLAN ======================");
			app_logs.info("=================== GET ALREADY CREATED "+parentRatePlanType.toUpperCase()+" RATE PLAN ======================");
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			parentIntervalRateName = ratesGrid.getActiveRatePlans(driver, parentRatePlanType,1).get(0);
			
			
			testSteps.add("Get all("+ratePlanNames.size()+") "+parentRatePlanType.toUpperCase()+" rate plan " + ratePlanNames);
			app_logs.info("Get all("+ratePlanNames.size()+") "+parentRatePlanType.toUpperCase()+" rate plan " + ratePlanNames);

//			testSteps.add("=================== GET ALL RATE PLANS ======================");
//			app_logs.info("=================== GET ALL RATE PLANS ======================");
//			
//			ArrayList<String> ratePlanTypes = new ArrayList<String>();
//			ratePlanTypes.add("Nightly");
//			ratePlanTypes.add("Interval");
//			ratePlanTypes.add("Package");
//			ratePlansList = ratesGrid.getActiveRatePlans(driver, ratePlanTypes, 0);
//			nightlyRatePlan = (ArrayList<String>) ratePlansList[0];
//			intervalRatePlan = (ArrayList<String>) ratePlansList[1];
//			packageRatePlan = (ArrayList<String>) ratePlansList[2];
//
//			testSteps.add("Successfully Get all(" + nightlyRatePlan.size() + ") Nightly rate plan ");
//			app_logs.info("Successfully Get all(" + nightlyRatePlan.size() + ") Nightly and Interval rate plan "
//					+ nightlyRatePlan);
//			testSteps.add("Successfully Get all(" + intervalRatePlan.size() + ") Interval rate plan ");
//			app_logs.info(
//					"Successfully Get all(" + intervalRatePlan.size() + ") Interval rate plan " + intervalRatePlan);
//			testSteps.add("Successfully Get all(" + packageRatePlan.size() + ") Package rate plan ");
//			app_logs.info("Successfully Get all(" + packageRatePlan.size() + ") Package rate plan " + packageRatePlan);
 
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== VALIDATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== VALIDATE NEW DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			testSteps.add("=================== RATE PLAN INITIAL STATE ======================");
			app_logs.info("=================== RATE PLAN INITIAL STATE ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan", testSteps);
			nightlyRate.verifyNextButton(driver, false, true, testSteps);
			nightlyRate.verifyRatePlanNameVisibility(driver, true, true, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameVisibility(driver, true, true, testSteps);
			nightlyRate.verifyRatePlanDescriptionVisibility(driver, true, true, testSteps);
			nightlyRate.verifyCharCountRatePlanDescription(driver, "0", testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== RATE PLAN NAME VALIDATIONS ======================");
			app_logs.info("=================== RATE PLAN NAME VALIDATIONS ======================");
			testSteps.add("=================== VERIFY RATE PLAN NAME MANADATORY FIELD ======================");
			app_logs.info("=================== VERIFY RATE PLAN NAME MANADATORY FIELD ======================");

			nightlyRate.enterAlreadyExistOrClearRatePlanName(driver, RatePlanName, false, testSteps);
			testSteps.add("=================== ENTER EXISTING RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER EXISTING RATE PLAN NAME ======================");

			nightlyRate.enterAlreadyExistOrClearRatePlanName(driver, parentIntervalRateName, true, testSteps);

			testSteps.add("=================== ENTER EMPTY RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER EMPTY RATE PLAN NAME ======================");
			nightlyRate.enterRatePlanName(driver, "", testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, "", true, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "Rate Plan Name cannot be empty", true, testSteps);

			testSteps.add("=================== ENTER VALID RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER VALID RATE PLAN NAME ======================");
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, RatePlanName, true, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "", false, testSteps);

			testSteps.add("=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN NAME ======================");
			nightlyRate.enterRatePlanName(driver, specialCharactersRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, specialCharactersRatePlanName, true, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "", false, testSteps);
			testSteps.add(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN NAME ======================");
			app_logs.info(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN NAME ======================");

			nightlyRate.enterRatePlanName(driver, moreThanMaximumLengthRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, moreThanMaximumLengthRatePlanName, false, testSteps);
			nightlyRate.verifyRatePlanNameErrorTxt(driver, "", false, testSteps);
			int foundFeildLenght = nightlyRate.getRatePlanInputFeiledLength(driver, testSteps);
			assertNotEquals(foundFeildLenght, moreThanMaximumLengthRatePlanName.length(),
					"Failed To Verify Rate Plan Feild Length");
			testSteps.add("Successfully Verified Rate Plan Name can not be entered more than maximum length");
			app_logs.info("Successfully Verified Rate Plan Name can not be entered more than maximum length");

			testSteps.add("=================== ENTER VALID RATE PLAN NAME ======================");
			app_logs.info("=================== ENTER VALID RATE PLAN NAME ======================");
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);

			testSteps.add("=================== FOLIO DISPLAY NAME VALIDATIONS ======================");
			app_logs.info("=================== FOLIO DISPLAY NAME VALIDATIONS ======================");
			testSteps.add("=================== VERIFY FOLIO DISPLAY NAME MANADATORY FIELD ======================");
			app_logs.info("=================== VERIFY FOLIO DISPLAY NAME MANADATORY FIELD ======================");
			nightlyRate.verifyRatePlanFolioDisplayNameRequiredFeild(driver, FolioDisplayName, testSteps);

			testSteps.add("=================== ENTER EMPTY FOLIO DISPLAY NAME ======================");
			app_logs.info("=================== ENTER EMPTY FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, "", testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, "", true, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, true, testSteps);

			testSteps.add("=================== ENTER VALID FOLIO DISPLAY NAME ======================");
			app_logs.info("=================== ENTER VALID FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, FolioDisplayName, true, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, false, testSteps);

			testSteps.add("=================== ENTER SEPECIAL CHARACTERS AS FOLIO DISPLAY NAME ======================");
			app_logs.info("=================== ENTER SEPECIAL CHARACTERS AS FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, specialCharactersFolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, specialCharactersFolioDisplayName, true,
					testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, false, testSteps);
			testSteps.add(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS FOLIO DISPLAY NAME ======================");
			app_logs.info(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS FOLIO DISPLAY NAME ======================");
			nightlyRate.enterRateFolioDisplayName(driver, moreThanMaximumLengthFolioDisplayName, testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameFeildValue(driver, moreThanMaximumLengthFolioDisplayName, false,
					testSteps);
			nightlyRate.verifyRatePlanFolioDisplayNameErrorTxt(driver, false, testSteps);
			int foundFolioDisplayFeildLenght = nightlyRate.getRatePlanFolioDisplayNameInputFeiledLength(driver,
					testSteps);
			assertNotEquals(foundFolioDisplayFeildLenght, moreThanMaximumLengthFolioDisplayName.length(),
					"Failed To Verify Rate Plan Feild Length");

			testSteps.add("Successfully Verified Folio Display Name can not be entered more than maximum length");
			app_logs.info("Successfully Verified Folio Display Name can not be entered more than maximum length");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== RATE PLAN DESCRIPTION VALIDATIONS ======================");
			app_logs.info("=================== RATE PLAN DESCRIPTION VALIDATIONS ======================");

			testSteps.add("=================== ENTER VALID RATE PLAN DESCRIPTION ======================");
			app_logs.info("=================== ENTER VALID RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, ratePlanDescription, testSteps);
			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, ratePlanDescription, true, testSteps);

			testSteps.add(
					"=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER SEPECIAL CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, specialCharactersRatePlanDescription, testSteps);
			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, specialCharactersRatePlanDescription, true,
					testSteps);
			testSteps.add(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER MORE THAN MAXIMUM CHARACTERS AS RATE PLAN DESCRIPTION ======================");
			nightlyRate.enterRatePlanDescription(driver, moreThanMaximumLengthRatePlanDescription, testSteps);
			nightlyRate.verifyRatePlanDescriptionFeildValue(driver, moreThanMaximumLengthRatePlanDescription, false,
					testSteps);

			int foundDescLength = nightlyRate.getRatePlanDescriptionInputFeiledLength(driver, testSteps);
			assertNotEquals(foundDescLength, moreThanMaximumLengthRatePlanDescription.length(),
					"Failed To Verify Rate Plan Feild Length");

			testSteps.add("Successfully Verified RATE PLAN DESCRIPTION can not be entered more than maximum length");
			app_logs.info("Successfully Verified RATE PLAN DESCRIPTION can not be entered more than maximum length");

			nightlyRate.verifyCharCountRatePlanDescription(driver, "" + foundDescLength + "", testSteps);

			 testSteps
			 .add("=================== VERIFY VERTICAL SCROLL FOR RATE PLAN DESCRIPTION ======================");
			 app_logs.info(
			 "=================== VERIFY VERTICAL SCROLL FOR RATE PLAN DESCRIPTION ======================");
						
			 long beforeClientHeight =
			 nightlyRate.getRatePlanDescriptionClientHeight(driver, true);
			 testSteps.add("Initially RATE PLAN DESCRIPTION client Height : "
			 + beforeClientHeight);
			 app_logs.info("Initially RATE PLAN DESCRIPTION client Height :	"
			 + beforeClientHeight);
			 long beforeOffSetHeight =
			 nightlyRate.getRatePlanDescriptionOffSetHeight(driver, true);
			 testSteps.add("Initially RATE PLAN DESCRIPTION OffSet Height : "
			 + beforeOffSetHeight);
			 app_logs.info("Initially RATE PLAN DESCRIPTION OffSet Height : "
			 + beforeOffSetHeight);
						 nightlyRate.enterRatePlanDescription(driver,
						 descriptionInSeperatelines, testSteps);
						 nightlyRate.verifyRatePlanDescriptionFeildValue(driver,
						 descriptionInSeperatelines, true, testSteps);
			 long afterClientHeight =
			 nightlyRate.getRatePlanDescriptionClientHeight(driver, false);
			 testSteps.add("After entering RATE PLAN DESCRIPTION client Height : " + afterClientHeight);
			 app_logs.info("After entering RATE PLAN DESCRIPTION client Height : " + afterClientHeight);
			 long afterOffSetHeight =
			 nightlyRate.getRatePlanDescriptionOffSetHeight(driver, false);
			 testSteps.add("After entering RATE PLAN DESCRIPTION OffSet Height : " + afterOffSetHeight);
			 app_logs.info("After entering RATE PLAN DESCRIPTION OffSet	Height : " + afterOffSetHeight);
			
			 testSteps.add("Successfully VERIFIED VERTICAL SCROLL FOR RATE PLAN DESCRIPTION APPEAR");
			 app_logs.info("Successfully VERIFIED VERTICAL SCROLL FOR RATE PLAN DESCRIPTION APPEAR");

			testSteps.add("=="
					+ " # of characters/255 within Description is displayed in blue color at bottom right of the Description field"
							.toUpperCase()
					+ " ==");
			app_logs.info("=="
					+ " # of characters/255 within Description is displayed in blue color at bottom right of the Description field"
							.toUpperCase()
					+ " ==");
			nightlyRate.enterRatePlanDescription(driver, ratePlanDescription, testSteps);
			nightlyRate.verifyCharCountRatePlanDescription(driver, "" + ratePlanDescription.length() + "",
					testSteps);
			String descriptionCharCountColor = nightlyRate.getCharCountColorRatePlanDescription(driver);
			testSteps.add("Description Char count Color : " + descriptionCharCountColor);
			app_logs.info("Description Char count Color : " + descriptionCharCountColor);
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, ratePlanDescription, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	try {

			
//			testSteps.add(
//					"==" + "Verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property (which 						includes Rate Plans created in V2 (Nightly Package and Interval Rates)"
//							.toUpperCase() + " ==");
//			app_logs.info("=================== "
//					+ "Verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property (which 							includes Rate Plans created in V2 (Nightly Package and Interval Rates)"
//							.toUpperCase()
//					+ " ======================");
//			testSteps.add("========== VERIFY ALL ACTIVE NIGHTLY RATE PLAN =========");
//			app_logs.info("========== VERIFY ALL ACTIVE NIGHTLY RATE PLAN =========");
//			derivedRate.verifyRatePlansDisplayed(driver, nightlyRatePlan, getTestSteps);
//			testSteps.add("Successfully verified all active nightly rate Plan exist.");
//			app_logs.info("Successfully verified all active nightly rate Plan exist.");
//			testSteps.add("========== VERIFY ALL ACTIVE INTERVAL RATE PLAN =========");
//			app_logs.info("========== VERIFY ALL ACTIVE INTERVAL RATE PLAN =========");
//			derivedRate.verifyRatePlansDisplayed(driver, intervalRatePlan, getTestSteps);
//			testSteps.add("Successfully verified all active interval rate Plan exist.");
//			app_logs.info("Successfully verified all active interval rate Plan exist.");
//
//			testSteps.add("========== VERIFY ALL ACTIVE PACKAGE RATE PLAN =========");
//			app_logs.info("========== VERIFY ALL ACTIVE PACKAGE RATE PLAN =========");
//			derivedRate.verifyRatePlansDisplayed(driver, packageRatePlan, getTestSteps);
//			testSteps.add("Successfully verified all active package rate Plan exist.");
//			app_logs.info("Successfully verified all active package rate Plan exist.");
			  
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName,  test_description, test_catagory,  testSteps);
				Utility.updateReport(e,
						"Failed to verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property",
						testName, "VerifyAllRatePlans", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory,  testSteps);
				Utility.updateReport(e,
						"Failed to verify that the Rate Plan deriving rates from shows list of all Active Rate Plans for the Property",
						testName, "VerifyAllRatePlans", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		
		try {

			testSteps.add("======== SELECT "+parentRatePlanType.toUpperCase()+" RATE AS PARENT RATE PLAN ========");
			app_logs.info("======== SELECT "+parentRatePlanType.toUpperCase()+" RATE AS PARENT RATE PLAN ========");
			derivedRate.selectRatePlan(driver, parentIntervalRateName, true, testSteps);

			testSteps.add("=================== "
					+ "Verify that when deriving Rates user can choose percent/currency (USD (based on the currency setting in Admin-Client Info)) greater/lesser than the derived rate from"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that when deriving Rates user can choose percent/currency (USD (based on the currency setting in 			Admin-Client Info)) greater/lesser than the derived rate from"
							.toUpperCase()
					+ " ======================");
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");
			derivedRate.verifyDropDownOptionsExist(driver, 1, currencyOptions, testSteps);
			derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand Value Comparison DropDown");
			app_logs.info("Expand Value Comparison DropDown");
			derivedRate.verifyDropDownOptionsExist(driver, 2, valueOptions, testSteps);
			derivedRate.selectDropDownOptions(driver, valueOptions.split(",")[1], testSteps);

			testSteps.add("=================== "
					+ "Verify that upon choosing percent the rate value is changed to % and % is shown next to the Rate value"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that upon choosing percent the rate value is changed to % and % is shown next to the Rate value"
							.toUpperCase()
					+ " ======================");
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");
			derivedRate.selectDropDownOptions(driver, percent, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertTrue(value.contains("%"), "Failed : '%' symbol is not displayed  with value");

			testSteps.add("=================== "
					+ "Verify that only values from 0 to 99 can be provided in the % rate value".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that only values from 0 to 99 can be provided in the % rate value".toUpperCase()
					+ " ======================");
			testSteps.add("===== ENTER VALUE GREATER THAN 99 =====");
			app_logs.info("===== ENTER VALUE GREATER THAN 99 =====");
			derivedRate.enterRateValue(driver, invalidPercentValue, testSteps);
			value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, "99%", "Failed : Value missmatched");
			testSteps.add("===== ENTER NEGATIVE INVALID PERCENT VALUE =====");
			app_logs.info("===== ENTER NEGATIVE INVALID PERCENT VALUE =====");
			getTestSteps.clear();
			derivedRate.enterRateValue(driver, "-" + invalidPercentValue, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			value = derivedRate.getRateValue(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			Assert.assertEquals(value, "99" + "%", "Failed : Value missmatched");
			testSteps
					.add("Verified that only values greater than or equal to zero can be provided in the % rate value");

			testSteps.add("===== ENTER VALUE LESS THAN 99 =====");
			app_logs.info("===== ENTER VALUE LESS THAN 99 =====");
			derivedRate.enterRateValue(driver, percentValue, testSteps);
			value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, percentValue + "%", "Failed : Value missmatched");
			testSteps.add("Successfully verified that only values from 0 to 99 can be provided in the % rate value");
			app_logs.info("Successfully verified that only values from 0 to 99 can be provided in the % rate value");

			testSteps.add("=================== " + "Verify that "
					+ "upon choosing USD the rate value is prefixed with $ (based on the Admin-Client Info setting)"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that upon choosing USD the rate value is prefixed with $ (based on the Admin-Client Info setting)"
							.toUpperCase()
					+ " ======================");
			derivedRate.expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			app_logs.info("Expand Currency DropDown");
			derivedRate.selectDropDownOptions(driver, USD, testSteps);
			derivedRate.verifyDollarSignDisplayed(driver, testSteps);

			testSteps.add("=================== "
					+ "Verify that when deriving Rates user can enter up to 4 digits of rate value".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that when deriving Rates user can enter up to 4 digits of rate value".toUpperCase()
					+ " ======================");

			testSteps.add("===== ENTER FIVE DIGIT VALUE =====");
			app_logs.info("===== ENTER FIVE DIGIT VALUE =====");
			derivedRate.enterRateValue(driver, invalidUSDValue, testSteps);
			value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, "9999", "Failed : Value missmatched");
			testSteps.add("===== ENTER FOUR DIGIT VALUE =====");
			app_logs.info("===== ENTER FOUR DIGIT VALUE =====");
			derivedRate.enterRateValue(driver, USDValue, testSteps);
			value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, USDValue, "Failed : Value missmatched");
			testSteps.add("Successfully verified that when deriving Rates user can enter up to 4 digits of rate value");
			app_logs.info("Successfully verified that when deriving Rates user can enter up to 4 digits of rate value");

			testSteps.add("=================== "
					+ "Verify that user sees and can check 'Take rules from parent rate plan'".toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that user sees and can check 'Take rules from parent rate plan'".toUpperCase()
					+ " ======================");
			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, true, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);

			String summaryParentRatePlanOffset = "Derived rates will be " + USDValue + " $ "
					+ valueOptions.split(",")[1] + " rates";

			testSteps.add("=================== DERIVED PLAN DATES INITIAL STATE ======================");
			app_logs.info("===================  DERIVED PLAN DATESL INITIAL STATE ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan", testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);

			testSteps.add("=================== SELECT DERIVED PLAN DATES ======================");
			app_logs.info("=================== SELECT DERIVED PLAN DATES ======================");
			derivedRate.selectDates(driver, dateType, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			testSteps.add("=================== DISTRIBUTION CHANNEL INITIAL STATE ======================");
			app_logs.info("===================  DISTRIBUTION CHANNEL INITIAL STATE ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan", testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", dateType, testSteps);
			nightlyRate.verifySelectedChannels(driver, "innCenter", true, testSteps);

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, Channels, true, testSteps);
			nightlyRate.verifySelectedChannels(driver, Channels, true, testSteps);
			String summaryChannels = Channels;// nightlyRate.generateTitleSummaryValueForChannels(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			testSteps.add("=================== ROOM CLASS PAGE INITIAL STATE ======================");
			app_logs.info("===================  ROOM CLASS PAGE INITIAL STATE ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan", testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", dateType, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);
			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);
			nightlyRate.verifySelectedRoomClasses(driver, RoomClasses, true, testSteps);
			String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			testSteps.add("=================== RESTRICTION PAGE INITIAL STATE ======================");
			app_logs.info("===================  RESTRICTION PAGE INITIAL STATE ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan", testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", dateType, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

			nightlyRate.verifySelectedRestriction(driver,
					"Length of stay" + delim + "Booking window" + delim + "Promo code", false, testSteps);
			nightlyRate.verifyRestrictionsTypesCheckBoxes(driver,
					"Length of stay" + delim + "Booking window" + delim + "Promo code", testSteps);

			nightlyRate.verifyToolTipBookingWindow(driver, testSteps);
			nightlyRate.verifyToolTipPromoCode(driver, testSteps);
			testSteps.add("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");
			app_logs.info("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");
			nightlyRate.selectRestrictions(driver, true, RistrictionType, true, MinNights, true, MaxNights, true,
					MoreThanDaysCount, true, WithInDaysCount, PromoCode, testSteps);
			nightlyRate.verifySelectedRestriction(driver, RistrictionType, true, testSteps);

			String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(RistrictionType, true, MinNights, true,
					MaxNights, true, MoreThanDaysCount, true, WithInDaysCount, PromoCode);
			nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, testSteps);

			String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);

			testSteps.add("=================== POLICIES PAGE INITIAL STATE ======================");
			app_logs.info("===================  POLICIES PAGE INITIAL STATE ======================");
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan", testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", dateType, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

			nightlyRate.verifyAllPolicies(driver, "Cancellation", allCancelationPolicies, testSteps);
			nightlyRate.verifyAllPolicies(driver, "Deposit", allDepositPolicies, testSteps);
			nightlyRate.verifyAllPolicies(driver, "Check-in", allCheckInPolicies, testSteps);
			nightlyRate.verifyAllPolicies(driver, "No Show", allNoShowPolicies, testSteps);

			testSteps.add("=================== SELECT POLICIES ======================");
			app_logs.info("=================== SELECT POLICIES ======================");

			PoliciesType = null;
			PoliciesName = null;
			if (allCancelationPolicies.size() != 0) {
				PoliciesType = "Cancellation";
				PoliciesName = allCancelationPolicies.get(0);

			}
			if (allDepositPolicies.size() != 0) {
				PoliciesType = ",Deposit";
				PoliciesName = "," + allDepositPolicies.get(0);
			}

			if (allCheckInPolicies.size() != 0) {
				PoliciesType = ",Check-in";
				PoliciesName = "," + allCheckInPolicies.get(0);
			}
			if (allNoShowPolicies.size() != 0) {
				PoliciesType = ",No Show";
				PoliciesName = "," + allNoShowPolicies.get(0);
			}
			nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, true, testSteps);
			nightlyRate.verifySelectedPolicy(driver, PoliciesType, true, testSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName, true,
					testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Derived rate plan", testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Parent rate plan offset", summaryParentRatePlanOffset, false,
					testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Derived plan dates", dateType, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);
			nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

			nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc, true, testSteps);

			nightlyRate.clickSaveAsActive(driver, testSteps);
			Utility.rateplanName = RatePlanName;
			testSteps.add("=================== DERIVED RATE PLAN CREATED ======================");
			app_logs.info("=================== DERIVED RATE PLAN CREATED ======================");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			testSteps.add("=================== "
					+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			try{
				driver.navigate().refresh();
				Wait.wait3Second();
				driver.navigate().refresh();
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, RatePlanName);
			}catch(Exception e){
				driver.navigate().refresh();
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, RatePlanName);
			}
			testSteps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			app_logs.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

			testSteps.add("=================== DERIVED RATE PLAN VALIDATED ======================");
			app_logs.info("=================== DERIVED RATE PLAN VALIDATED ======================");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("=================== "
					+ "Verifying on clicking ratesgrid, policies or product & bundles during rate creation a unsaved data popup appears"
							.toUpperCase()
					+ " ===================");
			app_logs.info("=================== "
					+ "Verifying on clicking ratesgrid, policies or product & bundles during rate creation a unsaved data popup appears"
							.toUpperCase()
					+ " ===================");

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);

			derivedRate.clickTab(driver, "Rates Grid", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			derivedRate.newRateplanTabExist(driver, true, testSteps);
			derivedRate.clickTab(driver, "Distribution", testSteps);
			try {
				// driver.switchTo().alert().accept();
				driver.switchTo().alert().dismiss();
				testSteps.add("Alert!!!!!!  appear");
				app_logs.info("Alert!!!!!!  appear");

				testSteps.add("Dismiss Alert! ");
				app_logs.info("Dismiss Alert! ");
			} catch (Exception alert) {
				testSteps.add("Alert!!!!!! not appear");
				app_logs.info("Alert!!!!!! not appear");
			}
			derivedRate.newRateplanTabExist(driver, true, testSteps);
			derivedRate.clickTab(driver, "Policies", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			derivedRate.newRateplanTabExist(driver, true, testSteps);
			derivedRate.clickTab(driver, "Products & Bundles", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			derivedRate.newRateplanTabExist(driver, true, testSteps);

			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", testSteps);
			derivedRate.newRateplanTabExist(driver, true, testSteps);

			testSteps.add("=================== "
					+ "Verifying on clicking yes button in unsaved data popup rateplan creation tab is closed"
							.toUpperCase()
					+ " ===================");
			app_logs.info("=================== "
					+ "Verifying on clicking yes button in unsaved data popup rateplan creation tab is closed"
							.toUpperCase()
					+ " ===================");

			derivedRate.clickTab(driver, "Rates Grid", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);

			derivedRate.clickTab(driver, "Distribution", testSteps);
			try {
				driver.switchTo().alert().accept();
				testSteps.add("Alert!!!!!!  appear");
				app_logs.info("Alert!!!!!!  appear");

				testSteps.add("Accept Alert! ");
				app_logs.info("Accept Alert! ");
			} catch (Exception alert) {
				testSteps.add("Alert!!!!!! not appear");
				app_logs.info("Alert!!!!!! not appear");
			}
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");
			navigation.Inventory(driver, testSteps);
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);

			derivedRate.clickTab(driver, "Policies", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);

			derivedRate.clickTab(driver, "Products & Bundles", testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

			testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
			testSteps.addAll(getTestSteps);
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);

			derivedRate.closeOpenedRatePlanTab(driver, testSteps);
			derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Yes", testSteps);
			derivedRate.newRateplanTabExist(driver, false, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			// new Step
			testSteps.add("=================== "
					+ "Verify that user can edit the provided Rate plan name, date ranges, Channel, Room classes during the process of creation of the rate."
							.toUpperCase()
					+ " ======================");
			app_logs.info("=================== "
					+ "Verify that user can edit the provided Rate plan name, date ranges, Channel, Room classes during the process of creation of the rate."
							.toUpperCase()
					+ " ======================");

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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select derived rate plan option", testName,
						"DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to select derived rate plan option", testName,
						"DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			RatePlanName = RatePlanName + Utility.generateRandomString();
			nightlyRate.enterRatePlanName(driver, RatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, ratePlanDescription, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to enter rate plan name", testName, "DerivedRatePlanCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== " + "SELECT RATE PLAN".toUpperCase() + " ======================");
			app_logs.info("=================== " + "SELECT RATE PLAN".toUpperCase() + " ======================");
			derivedRate.selectRatePlan(driver, parentIntervalRateName, true, testSteps);

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

			derivedRate.selectDropDownOptions(driver, USD, testSteps);

			testSteps.add("===== ENTER VALUE =====");
			app_logs.info("===== ENTER VALUE =====");
			derivedRate.enterRateValue(driver, USDValue, testSteps);
			String value = derivedRate.getRateValue(driver, testSteps);
			Assert.assertEquals(value, USDValue, "Failed : Value missmatched");

			derivedRate.takeRuleFromParentRatePlanCheckBox(driver, true, testSteps);
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
			
			testSteps.add(
					"=================== " + "EDIT DERIED RATE PLAN FIELDS".toUpperCase() + " ======================");
			app_logs.info(
					"=================== " + "EDIT DERIED RATE PLAN FIELDS.".toUpperCase() + " ======================");
			
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Room class", testSteps);
			
			testSteps.add("=================== EDIT ROOM CLASS ======================");
			app_logs.info("===================  EDIT ROOM CLASS ======================");

			nightlyRate.selectRoomClasses(driver, RoomClasses, false, testSteps);
			nightlyRate.verifySelectedRoomClasses(driver, RoomClasses, false, testSteps);
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Channel", testSteps);
			
			testSteps.add("=================== EDIT CHANNELS ======================");
			app_logs.info("===================  EDIT CHANNELS ======================");
			nightlyRate.selectChannels(driver, Channels, true, testSteps); // we
																			// can't
																			// unselect
																			// innCenter
			nightlyRate.verifySelectedChannels(driver, Channels, true, testSteps);
			
			testSteps.add("=================== EDIT DATE ======================");
			app_logs.info("=================== EDIT DATE ======================");
			
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Derived plan dates", testSteps);
			derivedRate.selectDates(driver, "Custom date range", testSteps);
			derivedRate.customDateRangeAppear(driver, true, testSteps);

			testSteps.add("=================== EDIT RATE PLAN NAME ===================");
			app_logs.info("=================== EDIT RATE PLAN NAME ===================");
			
			nightlyRate.clickTitleSummaryValueForEdit(driver, "Rate plan name", testSteps);
			String updateRatePlanName = "Updated" + RatePlanName;
			nightlyRate.enterRatePlanName(driver, updateRatePlanName, testSteps);
			nightlyRate.verifyRatePlanNameFeildValue(driver, updateRatePlanName, true, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create derived rate plan", testName, "DerivedRatePlanCreation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Generate Report
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ValidateDerivedRatePlanInterval",envLoginExcel);
			//	+ "ValidateDerivedRatePlanFromIntervalRate", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
