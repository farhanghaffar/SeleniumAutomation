package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyUserWithReportsV2Entilements extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> reportsAccess = new ArrayList<>();
	ArrayList<String> reports = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void verifyUserWithReportsV2Entilements(String url, String clientId, String userName, 
			String password, String newUserName, String newPassword, String strRoleName)throws InterruptedException, IOException, ParseException {

		test_name = "VerifyUserWithReportsV2Entilements";
		test_description = "ReportsV2 - User validation associated with role which has ReportsV2 entitlments<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682418' target='_blank'>"
				+ "Click here to open TestRail: C682418</a>";
		test_category = "ReportsV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Login login = new Login();
		Admin admin = new Admin();
		
		reportsAccess.add("Ledger Balances Report");
		reportsAccess.add("Advance Deposit Report");
		reportsAccess.add("Transactions Report");
		reportsAccess.add("Folio Balances Report");
		reportsAccess.add("Daily Flash Report");
		reportsAccess.add("Room Forecast Report");
		reportsAccess.add("Net Sales Report");
		
		reports.add("Ledger Balances");
		reports.add("Advance Deposit");
		reports.add("Transactions");
		reports.add("Account Balances");
		reports.add("Daily Flash");
		reports.add("Room Forecast");
		reports.add("Net Sales");
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login.login(driver, envURL, clientId, userName, password);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
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
			
			navigation.Admin(driver);
			navigation.Roles(driver);
			//admin.createNewRoleWithAllReportsAccess(driver, test_steps);
			String roleName = admin.createNewRoleAndGetRoleName(driver, test_steps);
			//String roleName = strRoleName;
			navigation.Roles(driver);
			admin.navigateToAdminRole(driver, roleName, test_steps);
			admin.reportsV2EntitlementEnable(driver, roleName, reportsAccess.get(0), test_steps);
			for (int i = 1; i < reportsAccess.size(); i++) {
				admin.reportsV2EntitlementDisable(driver, roleName, reportsAccess.get(i), test_steps);
				Wait.wait1Second();
			}
			admin.saveRole(driver, test_steps);
			
			navigation.Admin(driver);
			navigation.Users(driver);
			admin.associateNewRoleToExistingUser(driver, "Autotest", roleName, test_steps);
			admin.logout(driver, test_steps);
			
			login.login_URL(driver, clientId, newUserName, newPassword);
			//driver.findElement(By.xpath("(//span[text()='Reports'])[2]")).click();
			navigation.ReportsV2(driver);
			Wait.wait3Second();
			Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, "Ledger Balances", test_steps), "Failed");
			app_logs.info("Ledger Balances Report available in Reports page");
			admin.logoutCurrentUser(driver);
			
			/*login.login(driver, envURL, "riverrock", "manogna", "Innroad@123");
			navigation.Admin(driver);;
			admin.reportsV2EntitlementDisable(driver, roleName, "Ledger Balances Report", testSteps);
			admin.reportsV2EntitlementEnable(driver, roleName, "Advance Deposit Report", testSteps);
			
			admin.logout(driver);
			login.login(driver, envURL, "riverrock", "autotest", "Auto@123");
			driver.findElement(By.xpath("(//span[text()='Reports'])[2]")).click();
			//navigation.ReportsV2(driver);
			Wait.waitUntilPresenceOfElementLocated("//a[text()='Browse All Reports']", driver);
			Wait.wait3Second();
			Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, "Advance Deposit", testSteps), "Failed");*/
			//admin.logoutCurrentUser(driver);
			
			for (int i = 0; i < reportsAccess.size()-1; i++) {
				login.login(driver, envURL, clientId, userName, password);
				navigation.Admin(driver);
				navigation.Roles(driver);
				admin.navigateToAdminRole(driver, roleName, test_steps);
				admin.reportsV2EntitlementDisable(driver, roleName, reportsAccess.get(i), test_steps);
				test_steps.add(reportsAccess.get(i) + " access disabled");
				app_logs.info(reportsAccess.get(i) + " access disabled");
				Wait.wait3Second();
				admin.reportsV2EntitlementEnable(driver, roleName, reportsAccess.get(i+1), test_steps);
				test_steps.add(reportsAccess.get(i+1) + " access enabled");
				app_logs.info(reportsAccess.get(i+1) + " access enabled");
				admin.saveRole(driver, test_steps);
				Wait.wait3Second();
				
				admin.logout(driver);
				login.login(driver, envURL, clientId, newUserName, newPassword);
				//driver.findElement(By.xpath("(//span[text()='Reports'])[2]")).click();
				navigation.ReportsV2(driver);
				//Wait.waitUntilPresenceOfElementLocated("//a[text()='Browse All Reports']", driver);
				Wait.wait3Second();
				Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, reports.get(i+1), test_steps), "Failed"+reports.get(i+1)+" not found in ReportsV2 page");
				test_steps.add(reports.get(i+1) + " is available in Reports page");
				app_logs.info(reports.get(i+1) + " is available in Reports page");
				admin.logoutCurrentUser(driver);
			}
			
			
			login.login(driver, envURL, clientId, userName, password);
			navigation.Admin(driver);
			navigation.Roles(driver);
			admin.navigateToAdminRole(driver, roleName, test_steps);
			Wait.wait3Second();
			admin.reportsV2EntitlementEnable(driver, roleName, "Net Sales Report", test_steps);
			admin.reportsV2EntitlementEnable(driver, roleName, "Ledger Balances Report", test_steps);
			test_steps.add("Net Sales Report access enabled");
			test_steps.add("Ledger Balances Report access enabled");
			
			admin.saveRole(driver, test_steps);
			Wait.wait3Second();
			
			admin.logout(driver);
			login.login(driver, envURL, "riverrock", "autotest", "Auto@123");
			navigation.ReportsV2(driver);
			//Wait.waitUntilPresenceOfElementLocated("//a[text()='Browse All Reports']", driver);
			Wait.wait3Second();
			Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, "Net Sales", test_steps), "Failed- Net Sales Report is not found in ReportsV2 page");
			test_steps.add("Net Sales Report is available in Reports page");
			Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, "Ledger Balances", test_steps), "Failed- Ledger Balances Report is not found in ReportsV2 page");
			test_steps.add("Ledger Balances Report is available in Reports page");
			admin.logoutCurrentUser(driver);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate User associated with ReportsV2 entitlements", "ReportsV2 - User Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
			
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyUserWithReportsV2", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
		
}


