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

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CreateEditDeleteCancelationPolicy extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	// ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void createEditDeleteCancelationPolicy(String delim, String PolicyName, String TypeOfFees,
			String GuestsWillIncurAFee, String ChargesType, String NoOfDays, String CancelWithInType,
			String isCustomText, String CustomText, String EditName, String DeleteClauses, String EditClause,
			String UpdateTypeOfFees, String UpdateGuestsWillIncurAFee, String UpdateChargesType, String UpdateNoOfDays,
			String UpdateCancelWithInType, String UpdateIsCustomText, String UpdateCustomText)
			throws InterruptedException, IOException {

		Utility.DELIM = delim;

		test_name = "CreateEditDeleteCancelationPolicy";
		test_description = "CreateEditDeleteCancelationPolicy <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "CreateEditDeleteCancelationPolicy";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Policies policies = new Policies();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			//Login login = new Login();

			try {
				//login.login(driver, envURL, "wpi", "autouser", "Auto@123");
				loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				//login.login(driver, envURL, "wpi", "autouser", "Auto@123");
				loginWPI(driver);
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

		try {
			test_steps.add("=================== NAVIGATE TO POLICIES ======================");
			app_logs.info("=================== NAVIGATE TO POLICIES ======================");
			navigation.Inventory(driver, test_steps);
			navigation.policies(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		ArrayList<String> foundPolicyDesc = null;
		try {
			test_steps.add("===================  POLICY INITIAL STATE AND VALIDATION ======================");
			app_logs.info("=================== POLICY INITIAL STATE AND VALIDATION ======================");
			
			/*
			 * All Validation things are not dependent on test data sheet
			 */
			
			PolicyName = PolicyName + Utility.generateRandomStringWithoutNumbers();
			EditName = EditName  + Utility.generateRandomStringWithoutNumbers();
			
			ArrayList<String> cancellationPolices = policies.getCancelationPolicies(driver);
			String existingPolicy = "";
			if(cancellationPolices.size()>0) {
				existingPolicy = cancellationPolices.get(0);
			}
//			policies.clickCreateNewCancellationPolicy(driver, test_steps);
			policies.clickCreateNewPolicy(driver, test_steps, "Cancellation");
			policies.verifyPopupDisplayed(driver, "New cancellation policy", test_steps);
			
			policies.verifyPolicyNameVisibility(driver, true, true, test_steps);
			policies.verfiyCustomTextToggleState(driver, true, false, test_steps);
			ArrayList<String> initalPrint = new ArrayList<String>();initalPrint.add("No cancellation fine print");
			policies.verifyPolicyPrint(driver,initalPrint, test_steps);
			policies.verifyAddPolicy(driver, false, true, test_steps);
			policies.verifyPolicyRequiredFeild(driver, PolicyName, false, test_steps);
			if(cancellationPolices.size()>0) {
				policies.verifyPolicyRequiredFeild(driver, existingPolicy, true, test_steps);
			}
			policies.closePolicyPopup(driver, test_steps);
			driver.navigate().refresh();
			Wait.wait2Second();
//			policies.clickCreateNewCancellationPolicy(driver, test_steps);
			policies.clickCreateNewPolicy(driver, test_steps, "Cancellation");
			policies.verifyPopupDisplayed(driver, "New cancellation policy", test_steps);
			
			policies.selectTypeOfFee(driver, "percent of stay", 1, test_steps);
			policies.verfiySelectedTypeOfFee(driver, "percent of stay", 1, test_steps);
			
			policies.verifyPercentageVisibility(driver, 1, true, true, test_steps);
			policies.enterPercentage(driver, "0.8", 1, test_steps);
			policies.verifyPercentageValue(driver, "1", true, 1, test_steps);
			policies.enterPercentage(driver, "1000", 1, test_steps);
			policies.verifyPercentageValue(driver, "999.99", true, 1, test_steps);
			
			policies.verifyNoOfDaysVisibility(driver, 1, true, true, test_steps);
			policies.enterNoOfDays(driver, "0", 1, test_steps);
			policies.verifyNoOfDaysValue(driver, "1", true, 1, test_steps);
			policies.enterNoOfDays(driver, "1000", 1, test_steps);
			policies.verifyNoOfDaysValue(driver, "999", true, 1, test_steps);
			
			policies.selectTypeOfFee(driver, "flat fee", 1, test_steps);
			policies.verfiySelectedTypeOfFee(driver, "flat fee", 1, test_steps);
			
			policies.verifyFlatAmountVisibility(driver, 1, true, true, test_steps);
			policies.enterFlatAmount(driver, "0.8", 1, test_steps);
			policies.verifyFlatAmountValue(driver, "1", true, 1, test_steps);
			policies.enterFlatAmount(driver, "100000", 1, test_steps);
			policies.verifyFlatAmountValue(driver, "99999.99", true, 1, test_steps);
			
			policies.selectTypeOfFee(driver, "number of nights", 1, test_steps);
			policies.verfiySelectedTypeOfFee(driver, "number of nights", 1, test_steps);
			
			policies.verifyNoOfDaysVisibility(driver, 1, true, true, test_steps);
			policies.enterNoOfNights(driver, "0", 1, test_steps);
			policies.verifyNoOfNightsValue(driver, "1", true, 1, test_steps);
			policies.enterNoOfNights(driver, "1000", 1, test_steps);
			policies.verifyNoOfNightsValue(driver, "999", true, 1, test_steps);
			
			policies.closePolicyPopup(driver, test_steps);
			test_steps.add("===================  POLICY CREATION ======================");
			app_logs.info("=================== POLICY CREATION ======================");
//			policies.clickCreateNewCancellationPolicy(driver, test_steps);
			policies.clickCreateNewPolicy(driver, test_steps, "Cancellation");
			policies.verifyPopupDisplayed(driver, "New cancellation policy", test_steps);
			policies.createCancelationPolicy(driver, test_steps, delim, PolicyName, TypeOfFees, GuestsWillIncurAFee,
					ChargesType, NoOfDays, CancelWithInType, Boolean.parseBoolean(isCustomText), CustomText);
			if (!Boolean.parseBoolean(isCustomText)) {
				foundPolicyDesc = policies.getPolicyPrint(driver, test_steps);
			} else {
				foundPolicyDesc = new ArrayList<String>();
				foundPolicyDesc.add(CustomText);
			}
			policies.clickAddPolicy(driver, test_steps);

			test_steps.add("=================== VERIFYING AFTER POLICY CREATION ======================");
			app_logs.info("=================== VERIFYING AFTER POLICY CREATION ======================");

			policies.verifyLastPolicy(driver, test_steps,"Cancellation", PolicyName, true);
			policies.verifyPolicyDescription(driver, test_steps, PolicyName, foundPolicyDesc);

			policies.clickEditIcon(driver, "Cancellation", PolicyName, test_steps);
			policies.verifyPopupDisplayed(driver, "edit cancellation policy", test_steps);

			policies.verifyCancelationPolicyEditMode(driver, test_steps, delim, PolicyName, TypeOfFees,
					GuestsWillIncurAFee, ChargesType, NoOfDays, CancelWithInType, Boolean.parseBoolean(isCustomText),
					CustomText);
			if (!Boolean.parseBoolean(isCustomText)) {
				policies.verifyPolicyPrint(driver, foundPolicyDesc, test_steps);
			}
			
			test_steps.add("=================== EDIT POLICY ======================");
			app_logs.info("=================== EDIT POLICY  ======================");
			
			policies.editClauses(driver, delim, EditName, EditClause, UpdateTypeOfFees, UpdateGuestsWillIncurAFee, UpdateChargesType, UpdateNoOfDays, UpdateCancelWithInType, Boolean.parseBoolean(UpdateIsCustomText), UpdateCustomText, test_steps);
			
			if (!Boolean.parseBoolean(UpdateIsCustomText)) {
				foundPolicyDesc = policies.getPolicyPrint(driver, test_steps);
			} else {
				foundPolicyDesc = new ArrayList<String>();
				foundPolicyDesc.add(UpdateCustomText);
			}
			
			policies.clickUpdatePolicyButton(driver, test_steps);
			
			test_steps.add("=================== VERIFYING AFTER POLICY EDIT ======================");
			app_logs.info("=================== VERIFYING AFTER POLICY EDIT ======================");

			policies.verifyLastPolicy(driver, test_steps,"Cancellation", EditName, true);
			policies.verifyPolicyDescription(driver, test_steps, EditName, foundPolicyDesc);

			policies.clickEditIcon(driver, "Cancellation", EditName, test_steps);
			policies.verifyPopupDisplayed(driver, "edit cancellation policy", test_steps);

			policies.verifyCancelationPolicyEditModeClauseSpecific(driver, test_steps, delim,EditClause, EditName,UpdateTypeOfFees, UpdateGuestsWillIncurAFee, UpdateChargesType, UpdateNoOfDays, UpdateCancelWithInType, Boolean.parseBoolean(UpdateIsCustomText), UpdateCustomText);
			if (!Boolean.parseBoolean(UpdateIsCustomText)) {
				policies.verifyPolicyPrint(driver, foundPolicyDesc, test_steps);
			}
			
			test_steps.add("=================== DELETE CLAUSES ======================");
			app_logs.info("=================== DELETE CLAUSES ======================");
			
			policies.deleteClauses(driver, delim, DeleteClauses, test_steps);
			if (!Boolean.parseBoolean(UpdateIsCustomText)) {
				foundPolicyDesc = policies.getPolicyPrint(driver, test_steps);
			} else {
				foundPolicyDesc = new ArrayList<String>();
				foundPolicyDesc.add(UpdateCustomText);
			}
			
			policies.clickUpdatePolicyButton(driver, test_steps);
			
			test_steps.add("=================== VERIFYING AFTER POLICY DELETE CLAUSES ======================");
			app_logs.info("=================== VERIFYING AFTER POLICY DELETE CLAUSES ======================");

			policies.verifyLastPolicy(driver, test_steps,"Cancellation", EditName, true);
			policies.verifyPolicyDescription(driver, test_steps, EditName, foundPolicyDesc);

			policies.clickEditIcon(driver, "Cancellation", EditName, test_steps);
			policies.verifyPopupDisplayed(driver, "edit cancellation policy", test_steps);

			policies.verifyTotalNoOfCancelationClauses(driver, (Utility.convertTokenToArrayList(TypeOfFees, delim).size() - Utility.convertTokenToArrayList(DeleteClauses, delim).size())+"", test_steps);
			if (!Boolean.parseBoolean(UpdateIsCustomText)) {
				policies.verifyPolicyPrint(driver, foundPolicyDesc, test_steps);
			}
			policies.closePolicyPopup(driver, test_steps);
			
			test_steps.add("=================== DELETE POLICY ======================");
			app_logs.info("=================== DELETE POLICY ======================");
			
			policies.clickDeleteIcon(driver, "Cancellation", EditName, test_steps);
			policies.clickDeleteButton(driver, test_steps);
			
			test_steps.add("=================== VERIFYING AFTER POLICY DELETE  ======================");
			app_logs.info("=================== VERIFYING AFTER POLICY DELETE  ======================");

			policies.verifyLastPolicy(driver, test_steps,"Cancellation", EditName, false);
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Policies", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateCancellationPolicy", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
