package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyRoleFunctionality extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	Navigation navigation = new Navigation();
	Admin admin= new Admin();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(groups = "Admin")
	public void verifyRoleFunctionality() throws ParseException {
		String testName = null;
		test_name = "Navigation to Roles";
		test_description = "Navigation to Roles <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825649' target='_blank'>"
				+ "Click here to open TestRail: 825649</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825644' target='_blank'>"
				+ "Click here to open TestRail: 825644</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825650' target='_blank'>"
				+ "Click here to open TestRail: 825650</a><br>";
		test_catagory = "Role";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("825649|825644|825650", Utility.testId, Utility.statusCode, Utility.comments, "");
		// Login
				try {
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						Utility.reTry.replace(testName, 1);
					}
					driver = getDriver();
					login_CP(driver);
					test_steps.add("Logged into the application");
					app_logs.info("Logged into the application");
				} catch (Exception e) {
					Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, test_steps);
				}
				
				try {
					
					test_steps.add("<b>======Navigate to Admin and Verify UI======</b>");
					app_logs.info("<b>======Navigate to Admin and Verify UI======</b>");
					navigation.admin(driver);
					navigation.Roles(driver);
					admin.verifyAdminRoleUI(driver, test_steps, "Active");					
					test_steps.add("Navigation to Roles"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/825649' target='_blank'>"
						+ "Click here to open TestRail: 825649</a><br>");			
					  Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Navigation to Roles"); 
					  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Navigate to Admin", "Admin", "Admin", testName, test_description,
							test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Navigate to Admin", "Admin", "Admin", testName, test_description,
							test_catagory, test_steps);
				}
				
				try {
					
					test_steps.add("<b>======Verify Help icon for Roles in secondary navigation of admin page======</b>");
					app_logs.info("<b>======Verify Help icon for Roles in secondary navigation of admin page======</b>");
					admin.cickRoleHelpIcon(driver, test_steps);
					Utility.switchTab(driver, 1);
					app_logs.info("Switch Window");
					admin.verifyHelpWindow(driver, test_steps);
					Utility.switchTab(driver, 0);
					admin.clickNewRole(driver, test_steps);
					admin.verifySpinerLoading(driver);
					test_steps.add("Verify Help icon for Roles in secondary navigation of admin page"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/825644' target='_blank'>"
							+ "Click here to open TestRail: 825644</a><br>");			
						  Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify Help icon for Roles in secondary navigation of admin page"); 
						  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
					admin.disabledSaveButton(driver, test_steps);
				    test_steps.add("Navigation to Roles details page"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/825650' target='_blank'>"
						+ "Click here to open TestRail: 825650</a><br>");			
					  Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Navigation to Roles details page"); 
					  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
					  RetryFailedTestCases.count = Utility.reset_count;
					  Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);	
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Help icon for Roles in secondary navigation of admin page", "Admin", "Admin", testName, test_description,
							test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Help icon for Roles in secondary navigation of admin page", "Admin", "Admin", testName, test_description,
							test_catagory, test_steps);
				}
			}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
