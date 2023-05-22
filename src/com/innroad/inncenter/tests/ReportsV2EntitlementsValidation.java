package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.text.Utilities;

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
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2EntitlementsValidation extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> reportsAccess = new ArrayList<>();
	ArrayList<String> reportsAll = new ArrayList<>();
	
	ArrayList<String> ReportTableHeader = new ArrayList<String>();
	ArrayList<String> LedgerBalancesReport = new ArrayList<String>();
	ArrayList<String> AdvanceDepositReport = new ArrayList<String>();
	ArrayList<String> TransactionsReport = new ArrayList<String>();
	ArrayList<String> FolioBalancesReport = new ArrayList<String>();
	ArrayList<String> DailyFlashReport = new ArrayList<String>();
	ArrayList<String> RoomForecastReport = new ArrayList<String>();
	ArrayList<String> NetSalesReport = new ArrayList<String>();

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2Entitlements")
	public void verifyReportsV2Entitlements(String url, String clientId, String userName, String password,
			String reportName, String description, String requiredPermissions, String reportIsAccessible,
			String ledgerBalancesReportName,String ledgerBalancesDescription,String ledgerBalancesRequiredPermissions,
			String advanceDepositReportName,String advanceDepositDescription,String advanceDepositRequiredPermissions,
			String transactionsReportName,String transactionsReportDescription,String transactionsReportRequiredPermissions,
			String folioBalancesReportName,String folioBalancesDescription,String folioBalancesRequiredPermissions,
			String dailyFlashReportName,String dailyFlashDescription,String dailyFlashRequiredPermissions,
			String roomForecastReportName,String roomForecastDescription,String roomForecastRequiredPermissions,
			String netSalesReportName,String netSalesDescription,String netSalesRequiredPermissions, String inputReportSearch) throws InterruptedException, IOException {

		test_name = "ReportsV2EntitlementsValidation";
		test_description = "Reports V2 Entitlements UI validation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/722271' target='_blank'>"
				+ "Click here to open TestRail: C722271</a>";
		test_category = "ReportsV2EntitlementsValidation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Login login = new Login();
		Navigation nav = new Navigation();
		Admin admin = new Admin();
		ReportsV2 report = new ReportsV2();
		Properties prop = new Properties();
		

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			loginReportsV2(driver);
			//login.login(driver, envURL, clientId, userName, password);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to login", "Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		// Validating Report access table in Roles
		test_steps.add("==================== Report Access table UI Validation - Reports V2 Entitlements =============================");
				
		ReportTableHeader.add(reportName);
		ReportTableHeader.add(description);
		ReportTableHeader.add(requiredPermissions);
		ReportTableHeader.add(reportIsAccessible);
		
		LedgerBalancesReport.add(ledgerBalancesReportName);
		LedgerBalancesReport.add(ledgerBalancesDescription);
		LedgerBalancesReport.add(ledgerBalancesRequiredPermissions);
		
		AdvanceDepositReport.add(advanceDepositReportName);
		AdvanceDepositReport.add(advanceDepositDescription);
		AdvanceDepositReport.add(advanceDepositRequiredPermissions);
		
		TransactionsReport.add(transactionsReportName);
		TransactionsReport.add(transactionsReportDescription);
		TransactionsReport.add(transactionsReportRequiredPermissions);
		
		FolioBalancesReport.add(folioBalancesReportName);
		FolioBalancesReport.add(folioBalancesDescription);
		FolioBalancesReport.add(folioBalancesRequiredPermissions);
		
		DailyFlashReport.add(dailyFlashReportName);
		DailyFlashReport.add(dailyFlashDescription);
		DailyFlashReport.add(dailyFlashRequiredPermissions);
		
		RoomForecastReport.add(roomForecastReportName);
		RoomForecastReport.add(roomForecastDescription);
		RoomForecastReport.add(roomForecastRequiredPermissions);
		
		NetSalesReport.add(netSalesReportName);
		NetSalesReport.add(netSalesDescription);
		NetSalesReport.add(netSalesRequiredPermissions);
		
		ArrayList<ArrayList<String>> allReports = new ArrayList<ArrayList<String>>();
		ArrayList<String> reports = new ArrayList<>();
		
		allReports.add(ReportTableHeader);
		allReports.add(LedgerBalancesReport);
		allReports.add(AdvanceDepositReport);
		allReports.add(TransactionsReport);
		allReports.add(FolioBalancesReport);
		allReports.add(DailyFlashReport);
		allReports.add(RoomForecastReport);
		allReports.add(NetSalesReport);
		
		reports.add("Reports table header");
		reports.add("Ledger Balances Report");
		reports.add("Advance Deposit Report");
		reports.add("Transactions Report");
		reports.add("Folio Balances Report");
		reports.add("Daily Flash Report");
		reports.add("Room Forecast Report");
		reports.add("Net Sales Report");
		
		try {
			nav.Admin(driver);
			nav.Roles(driver);
			admin.navigateToAdminRole(driver, "Administrator", test_steps);
			
			Assert.assertTrue(admin.verifyReportAccessTableAvailability(driver, test_steps), "Report Access table not available");
			test_steps.add("Report Access table available");
			app_logs.info("Report Access table available");
			
			for (int i = 0; i < allReports.size(); i++) {
				
				try {
					
					Assert.assertTrue(admin.validateReportsV2EntitlementsUI(driver, allReports.get(i), test_steps), "Failed, " + reports.get(i) + " details are not showing as expected in Report Access table");
					test_steps.add(reports.get(i) + " details are available in Report Access table");
					app_logs.info(reports.get(i) + " details are available in Report Access table");
				}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(test_name) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(test_name, reports.get(i), "Reports V2 Entitlements UI", test_steps);
						Utility.updateReport(e, "Failed to Verify Report Access table UI", test_name, reports.get(i), driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					test_steps.add(e.toString());
				}
			}
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed", "Reports V2 Entitlements UI", "Reports V2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
			
		// Validating Report access table for Standard roles
		test_steps.add("======= Report Access table Standard roles validation - Reports V2 Entitlements ========");
		
		ArrayList<String> roles = new ArrayList<String>();
		
		roles.add("Administrator");
		roles.add("Front Desk Agent");
		roles.add("Hotel Manager");
		roles.add("Housekeeper");
		roles.add("Night Audit");
		roles.add("Reservations");
		roles.add("Sales Manager");
		
		try {
			nav.Admin(driver);
			test_steps.add("===   Validating Administrator role - Reports access checkbox - Reports V2 Entitlements   ===");
			app_logs.info("===   Validating Administrator role - Reports access checkbox - Reports V2 Entitlements   ===");
			admin.verifyReportsV2EntitlementsStandardRole(driver, "Administrator", test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Administrator", "Reports V2 Entitlements", test_steps);
				Utility.updateReport(e, "Failed to Verify ", test_name, "Administrator", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
/*		for (int i = 0; i < roles.size(); i++) {
			
			try {
				nav.Admin(driver);
				test_steps.add("===   Validating " + roles.get(i) + " role - Reports V2 Entitlements   ===");
				app_logs.info("===   Validating " + roles.get(i) + " role - Reports V2 Entitlements   ===");
				admin.verifyReportsV2EntitlementsStandardRole(driver, roles.get(i), test_steps);
				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, roles.get(i), "Reports V2 Entitlements", test_steps);
					Utility.updateReport(e, "Failed to Verify ", test_name, roles.get(i), driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}
			
		}*/
		
		//Validating Required permissions for Reports V2 - New Role - Ledger Balances Report
		test_steps.add("======= Validating Required permissions for Reports V2 New role - Ledger Balances Report ========");
		
		try {
		nav.Admin(driver);
		admin.verifyRequiredPermissionsReportsV2NewRole(driver, test_steps);
		
		//RetryFailedTestCases.count = Utility.reset_count;
        //Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "New Role", "Reports V2 Entitlements", test_steps);
				Utility.updateReport(e, "Failed to Verify Required permissions for Ledger Balances Report - New Role", test_name, "New Role", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Required permissions for Reports V2 - New Role - All Report
		test_steps.add("======= Validating Required permissions for Reports V2 New role - All Report ========");
		
		try {
			nav.Admin(driver);
			admin.navigateToNewRole(driver, test_steps);
			String[] repo = {"Advance Deposit Report", "Transactions Report", "Folio Balances Report", "Daily Flash Report", "Net Sales Report"};
			
			try {
				for (int i = 0; i < repo.length; i++) {
					admin.verifyRequiredPermissionsReportsV2NewRoleAllReports(driver, repo[i], test_steps);
				}
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, "New Role", "Reports V2 Entitlements", test_steps);
					Utility.updateReport(e, "Failed to Verify Required permissions for all reports - New Role", test_name, "New Role", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}
						
			//RetryFailedTestCases.count = Utility.reset_count;
	        //Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "New Role", "Reports V2 Entitlements", test_steps);
				Utility.updateReport(e, "Failed to Verify Required permissions for all reports - New Role", test_name, "New Role", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Report access table for Custom roles
		test_steps.add("======= Validating Report Access table for Custom role - Reports V2 Entitlements ========");
		
		try {
			nav.Admin(driver);
			admin.navigateToNewRole(driver, test_steps);
			admin.verifyReportsV2EntitlementsCustomRoleCreation(driver, test_steps);
		
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Custom Role", "Reports V2 Entitlements", test_steps);
				Utility.updateReport(e, "Failed to Verify Reports V2 Entitlements - Custom Role", test_name, "Custom Role", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		reportsAccess.add("Ledger Balances Report");
		reportsAccess.add("Advance Deposit Report");
		reportsAccess.add("Transactions Report");
		reportsAccess.add("Folio Balances Report");
		reportsAccess.add("Daily Flash Report");
		reportsAccess.add("Room Forecast Report");
		reportsAccess.add("Net Sales Report");
		
		reportsAll.add("Ledger Balances");
		reportsAll.add("Advance Deposit");
		reportsAll.add("Transactions");
		reportsAll.add("Account Balances");
		reportsAll.add("Daily Flash");
		reportsAll.add("Room Forecast");
		reportsAll.add("Net Sales");
		
		
		try {
			
			nav.Admin(driver);
			nav.Roles(driver);
			//admin.createNewRoleWithAllReportsAccess(driver, test_steps);
			String roleName = admin.createNewRoleAndGetRoleName(driver, test_steps);
			//String roleName = strRoleName;
			nav.Roles(driver);
			admin.navigateToAdminRole(driver, roleName, test_steps);
			admin.reportsV2EntitlementEnable(driver, roleName, reportsAccess.get(0), test_steps);
			for (int i = 1; i < reportsAccess.size(); i++) {
				admin.reportsV2EntitlementDisable(driver, roleName, reportsAccess.get(i), test_steps);
				Wait.wait1Second();
			}
			admin.saveRole(driver, test_steps);
			
			report.setup(driver);
			nav.properties(driver);
			ArrayList<String> propertiesList = new ArrayList<>();
			propertiesList = prop.getPropertiesList(driver);
			String propertyAssociated = propertiesList.get(0);
			
			nav.Admin(driver);
			nav.Users(driver);
			
			String loginID = Utility.generateRandomStringWithGivenLength(5);
			admin.CreateNewUser(driver, Utility.generateRandomStringWithGivenLength(4), Utility.generateRandomStringWithGivenLength(4), 
					loginID, "innroadautomation@innroad.com", roleName, propertyAssociated, true,
					test_steps);
			admin.SaveButton(driver);
			
			EmailUtils emailUtils = new EmailUtils(EmailUtils.EmailFolder.INBOX);

			emailUtils.GetResetPasswordLink(driver);
			admin.SetNewPassword(driver, "Auto@123", test_steps);
			
			login.login_URL(driver, clientId, loginID, "Auto@123");
			//driver.findElement(By.xpath("(//span[text()='Reports'])[2]")).click();
			nav.ReportsV2(driver);
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
				//login.login(driver, envURL, clientId, userName, password);
				loginReportsV2(driver);
				nav.Admin(driver);
				nav.Roles(driver);
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
				login.login(driver, envURL, clientId, loginID, "Auto@123");
				//driver.findElement(By.xpath("(//span[text()='Reports'])[2]")).click();
				nav.ReportsV2(driver);
				//Wait.waitUntilPresenceOfElementLocated("//a[text()='Browse All Reports']", driver);
				Wait.wait3Second();
				Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, reportsAll.get(i+1), test_steps), "Failed "+reportsAll.get(i+1)+" not found in ReportsV2 page");
				test_steps.add(reportsAll.get(i+1) + " is available in Reports page");
				app_logs.info(reportsAll.get(i+1) + " is available in Reports page");
				admin.logoutCurrentUser(driver);
			}
			
			
			//login.login(driver, envURL, clientId, userName, password);
			loginReportsV2(driver);
			nav.Admin(driver);
			nav.Roles(driver);
			admin.navigateToAdminRole(driver, roleName, test_steps);
			Wait.wait3Second();
			admin.reportsV2EntitlementEnable(driver, roleName, "Net Sales Report", test_steps);
			admin.reportsV2EntitlementEnable(driver, roleName, "Ledger Balances Report", test_steps);
			test_steps.add("Net Sales Report access enabled");
			test_steps.add("Ledger Balances Report access enabled");
			
			admin.saveRole(driver, test_steps);
			Wait.wait3Second();
			
			admin.logout(driver);
			login.login(driver, envURL, clientId, loginID, "Auto@123");
			nav.ReportsV2(driver);
			//Wait.waitUntilPresenceOfElementLocated("//a[text()='Browse All Reports']", driver);
			Wait.wait3Second();
			Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, "Net Sales", test_steps), "Failed- Net Sales Report is not found in ReportsV2 page");
			test_steps.add("Net Sales Report is available in Reports page");
			Assert.assertTrue(admin.verifyReportAvailabilityInReportsPage(driver, "Ledger Balances", test_steps), "Failed- Ledger Balances Report is not found in ReportsV2 page");
			test_steps.add("Ledger Balances Report is available in Reports page");
			admin.logoutCurrentUser(driver);
			
			
			//login.login(driver, envURL, clientId, userName, password);
			loginReportsV2(driver);
			nav.Admin(driver);
			nav.Users(driver);
			admin.changeUserStatusAndEmail(driver, loginID, "auto"+Utility.generateRandomString()+Utility.generateRandomString()+"@innroad.com", "Inactive", propertiesList);
			admin.SaveButton(driver);
			
			
			
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
		// return test data from the sheet name provided
		return Utility.getData("ReportsV2EntitlementsValidation", excel);
	}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
}
