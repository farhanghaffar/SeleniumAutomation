package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAdminModification extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String date = null;
	
	String FirstName="Auto";String LastName="Guest";String Login="autouser";
	String Email="bhanu.pithani@innroad.com";String AssociateRole="Administrator";
	String AssociateProperty="BEST SERVICE HOTEL";boolean isAllProperties;

	ArrayList statusCode= new ArrayList();
	ArrayList caseId=new ArrayList();
	String comments;
	/*@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}*/

	@Test(groups = "Reservation")
	public void Verify_Admin_Modification() throws InterruptedException, IOException {

		test_name = "Verify_Admin_Modification";
		test_description = "CP Admin Modification<br>";
		test_catagory = "CPReservation";
		String testName = test_name;

	
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		String TestCaseID="848543|848726|848755";
		
		String[] testcase=TestCaseID.split("\\|");
		for(int i=0;i<testcase.length;i++) {
			caseId.add(testcase[i]);
			statusCode.add("5");
		}
		
		CPReservationPage res = new CPReservationPage();
		Navigation nav = new Navigation();
		Admin admin = new Admin();
		
		
			
		// Login	
		try {
			driver = getDriver();
			login_Group(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
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
		Login=Login+Utility.generateRandomStringWithGivenLength(2);
		nav.Admin(driver);
		nav.Users(driver);
		admin.CreateNewUser(driver, FirstName, LastName, Login, Email, AssociateRole, AssociateProperty, isAllProperties, test_steps);
		admin.SaveButton(driver);
		admin.CloseTab(driver);
		admin.SearchUser(driver, LastName, FirstName, Login, Email, "Active");
		comments="Verified display of users when property is selected in Nav. ";
		test_steps.add("Verified display of users when property is selected in Nav.");
		app_logs.info("Verified display of users when property is selected in Nav.");

		
		try {
			
			nav.Admin(driver);
			nav.Roles(driver);
			admin.selectAdminstatorRole(driver, test_steps);
			admin.disableSpecialFunctionsForRole(driver, "Policies", "Override Policy Amounts", test_steps);
			test_steps.add("Disabled Override Policy Amounts");
			app_logs.info("Disabled Override Policy Amounts");
			admin.saveRole(driver, test_steps);
			Login login = new Login();
			
			login.logout(driver);
			login_Group(driver);
			res.click_NewReservation(driver, test_steps);
			boolean toggle=res.verifyOverideDepositToggle(driver);
			Assert.assertEquals(toggle, false, "Failed to verify the Overide Deposit Toggle is displayed.");
			test_steps.add("Overide Deposit Toggle is not displayed on unchecking the option in roles");
			app_logs.info("Overide Deposit Toggle is not displayed on unchecking the option in roles");
			comments="Verified Overide Deposit Toggle is not displayed on unchecking the option in roles";
			
			nav.Admin(driver);
			nav.Roles(driver);
			admin.selectAdminstatorRole(driver, test_steps);
			admin.enableSpecialFunctionsForRole(driver, "Policies", "Override Policy Amounts", test_steps);
			admin.saveRole(driver, test_steps);
			test_steps.add("Enabled Override Policy Amounts");
			app_logs.info("Enabled Override Policy Amounts");
			
			login.logout(driver);
			login_Group(driver);
			res.click_NewReservation(driver, test_steps);
			boolean toggles=res.verifyOverideDepositToggle(driver);
			Assert.assertEquals(toggles, true, "Failed to verify the Overide Deposit Toggle is not displayed.");
			test_steps.add("Overide Deposit Toggle is  displayed on checking the option in roles");
			app_logs.info("Overide Deposit Toggle is displayed on checking the option in roles");
			comments="Verified Overide Deposit Toggle is displayed on checking the option in roles";
			
			String[] testcase1 = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase1.length; i++) {
				statusCode.add(i, "1");
			}
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("Verify_Note_Task_CP_Reservation", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
