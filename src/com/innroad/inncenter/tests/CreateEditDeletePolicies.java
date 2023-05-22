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

public class CreateEditDeletePolicies extends TestCore {
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
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void createEditDeletePolicies(String delim, String PolicyTypes, String CheckInPolicyName,
			String NoShowPolicyName, String TypeOfFees, String GuestsWillIncurAFee, String ChargesType,
			String isCustomText, String CustomText, String EditPolicyTypes, String UpdateCheckInPolicyName,
			String UpdateNoShowPolicyName, String UpdateTypeOfFees, String UpdateGuestsWillIncurAFee,
			String UpdateChargesType, String UpdateIsCustomText, String UpdateCustomText)
			throws InterruptedException, IOException {

		Utility.DELIM = delim;

		test_name = "CreateEditDeletePolicies";
		test_description = "CreateEditDeletePolicies <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "CreateEditDeletePolicies";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Policies policies = new Policies();

		/*
		 * Converting Tokens to List
		 */
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(PolicyTypes, delim);
		ArrayList<String> typeOfFeesList = Utility.convertTokenToArrayList(TypeOfFees, delim);
		ArrayList<String> percentageList = Utility.convertTokenToArrayList(GuestsWillIncurAFee, delim);
		ArrayList<String> chargesTypeList = Utility.convertTokenToArrayList(ChargesType, delim);
		ArrayList<String> isCustomTextList = Utility.convertTokenToArrayList(isCustomText, delim);
		ArrayList<String> customTextList = Utility.convertTokenToArrayList(CustomText, delim);
		
		ArrayList<String> updatePolicyTypesList = Utility.convertTokenToArrayList(EditPolicyTypes, delim);
		ArrayList<String> updateTypeOfFeesList = Utility.convertTokenToArrayList(UpdateTypeOfFees, delim);
		ArrayList<String> updatePercentageList = Utility.convertTokenToArrayList(UpdateGuestsWillIncurAFee, delim);
		ArrayList<String> updatechargesTypeList = Utility.convertTokenToArrayList(UpdateChargesType, delim);
		ArrayList<String> updateIsCustomTextList = Utility.convertTokenToArrayList(UpdateIsCustomText, delim);
		ArrayList<String> updateCustomTextList = Utility.convertTokenToArrayList(UpdateCustomText, delim);
		
		CheckInPolicyName = CheckInPolicyName + Utility.generateRandomStringWithoutNumbers();
		NoShowPolicyName = NoShowPolicyName + Utility.generateRandomStringWithoutNumbers();
		if(!UpdateCheckInPolicyName.equalsIgnoreCase("NA")) {
			UpdateCheckInPolicyName = UpdateCheckInPolicyName + Utility.generateRandomStringWithoutNumbers();
		}
		if(!UpdateNoShowPolicyName.equalsIgnoreCase("NA")) {
			UpdateNoShowPolicyName = UpdateNoShowPolicyName + Utility.generateRandomStringWithoutNumbers();
		}
		// Login to application
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			// Login login = new Login();

			try {
				// login.login(driver, envURL, "wpi", "autouser", "Auto@123");
				loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				// login.login(driver, envURL, "wpi", "autouser", "Auto@123");
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

		try {
//			=================== CHECK IN POLICY CREATION UPDATION VERIFICATION======================
//			=================== CHECK IN POLICY CREATION UPDATION VERIFICATION======================
//			
//			HashMap<String, ArrayList<String>> foundCheckInPrintText = policies.createPolicies(driver, test_steps,delim,se PolicyTypes,
//					CheckInPolicyName, NoShowPolicyName, typeOfFeesList, percentageList, chargesTypeList, isCustomTextList, customTextList);
//			
//			policies.verifyPolicies(driver, test_steps, policyTypesList, CheckInPolicyName,NoShowPolicyName, typeOfFeesList,
//					percentageList, chargesTypeList, isCustomTextList, foundCheckInPrintText);
//			
//			HashMap<String, ArrayList<String>> foundCheckInPrintTextUpdate = policies.editPolicies(driver, test_steps,
//					updatePolicyTypesList,CheckInPolicyName, NoShowPolicyName, UpdateCheckInPolicyName, UpdateNoShowPolicyName, updateTypeOfFeesList, updatePercentageList,
//					updatechargesTypeList, updateIsCustomTextList, updateCustomTextList);
//					
//			policies.verifyPolicies(driver, test_steps, updatePolicyTypesList, UpdateCheckInPolicyName, UpdateNoShowPolicyName,
//					updateTypeOfFeesList, updatePercentageList, updatechargesTypeList, updateIsCustomTextList,
//					foundCheckInPrintTextUpdate);

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
//			=================== DELETE POLICIES ======================
//			=================== DELETE POLICIES ======================
			ArrayList<String> nameList = new ArrayList<String>();
			if(UpdateCheckInPolicyName.equalsIgnoreCase("NA")) {
				nameList.add(CheckInPolicyName);
			}else {
				nameList.add(UpdateCheckInPolicyName);
			}
			
			if(UpdateNoShowPolicyName.equalsIgnoreCase("NA")) {
				nameList.add(NoShowPolicyName);
			}else {
				nameList.add(UpdateNoShowPolicyName);
			}
			
			policies.deleteAllPolicies(driver, test_steps, nameList);

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
		return Utility.getData("CreateEditDeletePolicies", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
