package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.ListManagement;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2LedgerBalancesAdvancedInputs extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();

	ArrayList<String> usersList = new ArrayList<>();
	ArrayList<String> propertiesList = new ArrayList<>();

	ArrayList<String> usersListActual = new ArrayList<>();
	ArrayList<String> propertiesListActual = new ArrayList<>();

	String roleAssociated;
	String propertyAssociated;
	String loginID;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateReportsV2(String url, String clientId, String userName, String password, String firstName,
			String lastName, String email, String newPassword) throws Throwable {

		test_name = "ReportsV2LedgerBalancesAdvanced";
		test_description = "Ledger Balances - VerifyLedgerBalancesReportV2AdvancedOptions <br>";
		test_category = "ReportsV2 - Ledger Balances report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		Login login = new Login();
		ReportsV2 report = new ReportsV2();
		Admin admin = new Admin();
		Properties prop = new Properties();
		Users user = new Users();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			try {
				loginReportsV2(driver);
				//login.login(driver, envURL, clientId, userName, password);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				login.login(driver, envURL, clientId, userName, password);
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
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

		// Validating Advanced Options
		test_steps.add("========= Validating Advanced Options =========");
		try {
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);

			report.validateAdvancedOptionsToolTip(driver, test_steps);
			report.validateExpandAllfunctionalityofAdvancedOptions(driver, test_steps);
			report.validateCollapseAllfunctionalityofAdvancedOptions(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Advanced options Heading details",
						"Advanced Options Heading details validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// validating default values of all advanced options
		try {
			ReportsV2 reports = new ReportsV2();
			reports.validateAllAdvancedpOptionsdefaultCollapseText(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Advanced options Heading details",
						"Advanced Options Heading details validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Account Type
		test_steps.add("========= Validating Advanced Options - Account Type =========");
		try {
			Elements_Reports res = new Elements_Reports(driver);

			report.validateAccountTypeToolTip(driver, test_steps);
			report.validateAccountTypeExpandAndCollapseFunctionality(driver, test_steps);
			report.validateAccountTypeClearAllfuntionality(driver, test_steps);
			report.validateAccountTypeSelectAllFunctionality(driver, test_steps);
			report.validateAccountTypeOptions(driver, test_steps);

			// validate Account Type default checked options count and displayed text
			Utility.clickThroughAction(driver, res.AccountType);
			String defaultText = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Account Type");
			Utility.clickThroughAction(driver, res.AccountType);
			int defaultcount = report.getCountOfCheckedAdvancedSubOptions(driver, "Account Type");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Account Type", defaultcount,
					defaultText);

			// Uncheck two Options and validate the count with displayed text
			report.unCheckRequiredAdvancedOption(driver, test_steps, "Travel Agent");
			report.unCheckRequiredAdvancedOption(driver, test_steps, "Reservations");
			int count1 = report.getCountOfCheckedAdvancedSubOptions(driver, "Account Type");
			Utility.clickThroughAction(driver, res.AccountType);
			String Text1 = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Account Type");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Account Type", count1, Text1);

			// check one Option and validate the count with displayed text
			Utility.clickThroughAction(driver, res.AccountType);
			report.checkRequiredAdvancedOption(driver, test_steps, "Travel Agent");
			int count2 = report.getCountOfCheckedAdvancedSubOptions(driver, "Account Type");
			Utility.clickThroughAction(driver, res.AccountType);
			String Text2 = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Account Type");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Account Type", count2, Text2);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Account Type", "Account Type validation", "ReportsV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Item Status
		test_steps.add("========= Validating Advanced Options - Item Status =========");
		try {
			Elements_Reports res = new Elements_Reports(driver);
			report.validateItemStatusToolTip(driver, test_steps);
			report.validateItemStatusExpandAndCollapseFunctionality(driver, test_steps);
			report.validateItemStatusSelectAllFunctionality(driver, test_steps);
			report.validateItemStatusClearAllfuntionality(driver, test_steps);
			report.validateItemStatusOptions(driver, test_steps);

			// validate Items Status default checked options count and displayed text
			Utility.clickThroughAction(driver, res.ItemStatus);
			String defaultText = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Item Status");
			Utility.clickThroughAction(driver, res.ItemStatus);
			int defaultcount = report.getCountOfCheckedAdvancedSubOptions(driver, "Item Status");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Item Status", defaultcount,
					defaultText);

			// Uncheck two Options and validate the count with displayed text
			report.unCheckRequiredAdvancedOption(driver, test_steps, "Pending");
			report.unCheckRequiredAdvancedOption(driver, test_steps, "Posted");
			int count1 = report.getCountOfCheckedAdvancedSubOptions(driver, "Item Status");
			Utility.clickThroughAction(driver, res.ItemStatus);
			String Text1 = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Item Status");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Item Status", count1, Text1);

			// check one Option and validate the count with displayed text
			Utility.clickThroughAction(driver, res.ItemStatus);
			report.checkRequiredAdvancedOption(driver, test_steps, "Pending");
			report.checkRequiredAdvancedOption(driver, test_steps, "Posted");
			report.checkRequiredAdvancedOption(driver, test_steps, "Void");
			int count2 = report.getCountOfCheckedAdvancedSubOptions(driver, "Item Status");
			Utility.clickThroughAction(driver, res.ItemStatus);
			String Text2 = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Item Status");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Item Status", count2, Text2);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Item Status", "Item Status Validation", "ReportsV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Include Data From
		test_steps.add("========= Validating Advanced Options - Include Data From =========");
		try {
			Elements_Reports res = new Elements_Reports(driver);
			report.validateIncludeDataFromToolTip(driver, test_steps);
			report.validateIncludeDataFromExpandAndCollapseFunctionality(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Include Data From - Collapse text
		test_steps.add("========= Validating Advanced Options - Include Data From Collapse Text=========");
		try {
			report.validateIncludeDataFormCollapseText(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Tax Exempt Ledger Items
		test_steps.add("========= Validating Advanced Options - Tax Exempt Ledger Items =========");
		try {
			Elements_Reports res = new Elements_Reports(driver);
			report.validateTaxExemptLedgerItemsToolTip(driver, test_steps);
			report.validateTaxExemptLedgerItemsExpandAndCollapseFunctionality(driver, test_steps);
			report.validateTaxExemptLedgerItemsOptions(driver, test_steps);
			report.validateTaxExemptLedgerItemsCollapseText(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Tax Exempt Ledger Items",
						"Tax Exempt Ledger Items Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Market Segment
		test_steps.add("========= Validating Advanced Options - Market Segment =========");
		try {
			Elements_Reports res = new Elements_Reports(driver);
			report.validateMarketSegmentToolTip(driver, test_steps);
			report.validateMarketSegmentExpandAndCollapseFunctionality(driver, test_steps);
			driver.close();
			Utility.switchTab(driver, 0);
			ListManagement ls = new ListManagement();

			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.newMarketSegmentCreation(driver, "Corporate", "Corporate");
			test_steps.add("*****Validating of Market segment options after creation of new item*****");
			report.validateMarketSegmentOptions(driver, test_steps);
			driver.close();

			Utility.switchTab(driver, 0);
			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.MakeMarketSegmentitemInactive(driver, "Corporate");
			test_steps.add("*****Validating of Market segment options after making new item Inactive*****");
			report.validateMarketSegmentOptions(driver, test_steps);
			driver.close();

			Utility.switchTab(driver, 0);
			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.SelectStatus(driver, "InActive");
			ls.MakeMarketSegmentitemActive(driver, "Corporate");
			test_steps.add("*****Validating of Market segment options after making new item Active*****");
			report.setup(driver);
			nav.ListManagemnet(driver);
			report.validateMarketSegmentOptions(driver, test_steps);
			driver.close();

			Utility.switchTab(driver, 0);
			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.DeleteActiveMarketSegmentitem(driver, "Corporate");
			test_steps.add("*****Validating of Market segment options after deleting item*****");
			report.validateMarketSegmentOptions(driver, test_steps);

			report.validateMarketSegmentCollapseText(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Market Segment", "Market Segment Validation", "ReportsV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Reservation Status
		test_steps.add("========= Validating Advanced Options - Reservation Status =========");
		try {

			Elements_Reports res = new Elements_Reports(driver);

			report.validateReservationStatusToolTip(driver, test_steps);
			report.validateReservationStatusExpandAndCollapseFunctionality(driver, test_steps);
			report.validateReservationStatusClearAllfuntionality(driver, test_steps);
			report.validateReservationStatusSelectAllFunctionality(driver, test_steps);
			report.validateReservationStatusOptions(driver, test_steps);

			// Reservation Status Options validations

			// validate Reservation Status default checked options count and displayed text
			Utility.clickThroughAction(driver, res.ReservationStatus);
			String defaultText = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Reservation Status");
			Utility.clickThroughAction(driver, res.ReservationStatus);
			int defaultcount = report.getCountOfCheckedAdvancedSubOptions(driver, "Reservation Status");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Reservation Status", defaultcount,
					defaultText);

			// Uncheck two Options and validate the count with displayed text
			report.unCheckRequiredAdvancedOption(driver, test_steps, "Quote");
			report.unCheckRequiredAdvancedOption(driver, test_steps, "Departed");
			int count1 = report.getCountOfCheckedAdvancedSubOptions(driver, "Reservation Status");
			Utility.clickThroughAction(driver, res.ReservationStatus);
			String Text1 = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Reservation Status");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Reservation Status", count1, Text1);

			// check one Option and validate the count with displayed text
			Utility.clickThroughAction(driver, res.ReservationStatus);
			report.checkRequiredAdvancedOption(driver, test_steps, "Departed");
			int count2 = report.getCountOfCheckedAdvancedSubOptions(driver, "Reservation Status");
			Utility.clickThroughAction(driver, res.ReservationStatus);
			String Text2 = report.getTextOfCheckedAdvancedSubOptionsDisplayed(driver, "Reservation Status");
			report.validateCountOfSelectedTotalAdvancedOption(driver, test_steps, "Reservation Status", count2, Text2);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Referrals
		test_steps.add("========= Validating Advanced Options - Referrals =========");
		try {
			report.validateReferralsToolTip(driver, test_steps);
			report.validateReferralsExpandAndCollapseFunctionality(driver, test_steps);
			driver.close();
			Utility.switchTab(driver, 0);

			ListManagement ls = new ListManagement();

			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.newReferralCreation(driver, "Invitation", "Invitation");
			test_steps.add("*****Validating of Referral options after creation of new item*****");
			report.validateReferralsOptions(driver, test_steps);
			driver.close();

			Utility.switchTab(driver, 0);
			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.MakeReferralitemInactive(driver, "Invitation");
			test_steps.add("*****Validating of Referral options after making item Inactive*****");
			report.validateReferralsOptions(driver, test_steps);
			driver.close();

			Utility.switchTab(driver, 0);
			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.SelectStatus(driver, "InActive");
			ls.MakeReferralitemActive(driver, "Invitation");
			test_steps.add("*****Validating of Referral options after making item Active*****");
			report.setup(driver);
			nav.ListManagemnet(driver);
			report.validateReferralsOptions(driver, test_steps);
			driver.close();

			Utility.switchTab(driver, 0);
			report.setup(driver);
			nav.ListManagemnet(driver);
			ls.DeleteActiveReferralitem(driver, "Invitation");
			test_steps.add("*****Validating of Referral options after deleting item*****");
			report.validateReferralsOptions(driver, test_steps);

			report.validateReferralsCollapseText(driver, test_steps);
			driver.close();
			Utility.switchTab(driver, 0);

			// RetryFailedTestCases.count = Utility.reset_count;
			// Utility.AddTest_IntoReport(testName, test_description, test_category,
			// test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Referrals options", "Referrals options validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Create a new user and validate in Ledger Balance Reports

		test_steps.add(
				"==== Create a user who has access to both the properties and enabled the option 'run report for other users' - if client has multi property====");
		try {

			// nav.admin(driver);
			report.admin(driver);
			nav.Roles(driver);
			roleAssociated = admin.createNewRoleAndGetRoleName(driver, test_steps);
			// roleAssociated = "Custom Role 251";

			// nav.setup(driver);
			report.setup(driver);
			nav.properties(driver);
			propertiesList = prop.getPropertiesList(driver);
			propertyAssociated = propertiesList.get(0);

			// nav.admin(driver);
			report.admin(driver);
			nav.Users(driver);
			// loginID = "jDSmk";
			loginID = Utility.generateRandomStringWithGivenLength(5);
			admin.CreateNewUser(driver, firstName, lastName, loginID, email, roleAssociated, propertyAssociated, true,
					test_steps);
			admin.SaveButton(driver);

			// nav.admin(driver);
			report.admin(driver);
			nav.Users(driver);
			usersList = user.getAllUsersList(driver, test_steps);
			admin.associateAllPropertiesToExistingUser(driver, loginID, test_steps);
			// admin.CloseTab(driver);

			EmailUtils emailUtils = new EmailUtils(EmailUtils.EmailFolder.INBOX);

			emailUtils.GetResetPasswordLink(driver);
			admin.SetNewPassword(driver, newPassword, test_steps);

			// login.logout(driver);
			login.login_URL(driver, clientId, loginID, newPassword);

			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			report.expandGivenAdvancedOptions(driver, "Include Data From", test_steps);

			Collections.sort(usersList);
			Collections.sort(propertiesList);

			app_logs.info(usersList);
			app_logs.info(propertiesList);

			usersListActual = report.getUsersListFromIncludeDataForm(driver, test_steps);
			//propertiesListActual = report.getPropertiesListFromIncludeDataForm(driver, test_steps);

			app_logs.info(usersListActual);
			//app_logs.info(propertiesListActual);

			Collections.sort(usersListActual);
			//Collections.sort(propertiesListActual);

			try {
				if (usersList.equals(usersListActual)) {
					app_logs.info("Users List validated");
					test_steps.add("Users List validated");

				} else {
					app_logs.info("Users List validation Failed");
					test_steps.add("Users List validation Failed");
					Assert.assertTrue(false);

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}

/*			try {
				if (propertiesList.equals(propertiesListActual)) {
					app_logs.info("Properties List validated");
					test_steps.add("Properties List validated");
				} else {
					app_logs.info("Properties List validation Failed");
					test_steps.add("Properties List validation Failed");
					Assert.assertTrue(false);

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		//
		test_steps.add(
				"========= User who has access to both the properties and disbled the option \"run report for other users\" =========");
		try {
			usersList.clear();
			propertiesList.clear();
			usersListActual.clear();
			propertiesListActual.clear();

			driver.close();
			Utility.switchTab(driver, 0);
			report.logout(driver, test_steps);
			login.login(driver, envURL, clientId, userName, password);
			// nav.admin(driver);
			report.admin(driver);
			nav.Roles(driver);
			admin.navigateToAdminRole(driver, roleAssociated, test_steps);
			admin.disableSpecialFunctionsForRole(driver, roleAssociated, "Run reports as other users", test_steps);
			admin.saveRole(driver, test_steps);

			// nav.admin(driver);
			report.admin(driver);
			nav.Users(driver);
			usersList = user.getAllUsersList(driver, test_steps);

			// nav.setup(driver);
			report.setup(driver);
			nav.properties(driver);
			propertiesList = prop.getPropertiesList(driver);

			admin.logout(driver, test_steps);

			login.login(driver, envURL, clientId, loginID, newPassword);

			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			report.expandGivenAdvancedOptions(driver, "Include Data From", test_steps);

			Collections.sort(usersList);
			Collections.sort(propertiesList);

			app_logs.info(usersList);
			app_logs.info(propertiesList);

			usersListActual = report.getUsersListFromIncludeDataForm(driver, test_steps);
			//propertiesListActual = report.getPropertiesListFromIncludeDataForm(driver, test_steps);

			app_logs.info(usersListActual);
			//app_logs.info(propertiesListActual);

			try {
				if (!(usersListActual.size() > 1)) {
					app_logs.info("Users List validated Successfully");
					test_steps.add("Users List validated Successfully" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-359'>"
							+ "Click here to open JIRA: RPT-359</a>");
				} else {
					app_logs.info("Users List validation Failed");
					test_steps.add("Users List validation Failed" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-359'>"
							+ "Click here to open JIRA: RPT-359</a>");
					Assert.assertTrue(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}

/*			try {
				if (propertiesList.equals(propertiesListActual)) {
					app_logs.info("Properties List validated Successfully");
					test_steps.add("Properties List validated Successfully");
				} else {
					app_logs.info("Properties List validation Failed");
					test_steps.add("Properties List validation Failed");
					Assert.assertTrue(false);

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		//
		test_steps.add(
				"========= User who has access to one property and enabled the option \"run report for other users\" =========");
		try {
			usersList.clear();
			propertiesList.clear();
			usersListActual.clear();
			propertiesListActual.clear();

			driver.close();
			Utility.switchTab(driver, 0);
			report.logout(driver, test_steps);
			login.login(driver, envURL, clientId, userName, password);
			// nav.admin(driver);
			report.admin(driver);
			nav.Roles(driver);
			admin.navigateToAdminRole(driver, roleAssociated, test_steps);
			admin.enableSpecialFunctionsForRole(driver, roleAssociated, "Run reports as other users", test_steps);
			admin.saveRole(driver, test_steps);

			// nav.Admin(driver);
			report.admin(driver);
			nav.Users(driver);
			usersList = user.getAllUsersList(driver, test_steps);
			admin.associatePropertyToExistingUser(driver, loginID, propertyAssociated, test_steps);

			// nav.setup(driver);
			report.setup(driver);
			nav.properties(driver);
			propertiesList = prop.getPropertiesList(driver);

			admin.logout(driver, test_steps);

			login.login(driver, envURL, clientId, loginID, newPassword);

			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			report.expandGivenAdvancedOptions(driver, "Include Data From", test_steps);

			Collections.sort(usersList);
			Collections.sort(propertiesList);

			app_logs.info(usersList);
			app_logs.info(propertiesList);

			usersListActual = report.getUsersListFromIncludeDataForm(driver, test_steps);
			//propertiesListActual = report.getPropertiesListFromIncludeDataForm(driver, test_steps);

			app_logs.info(usersListActual);
			//app_logs.info(propertiesListActual);

			try {
				if (usersList.equals(usersListActual)) {
					app_logs.info("Users List validated Succesfully");
					test_steps.add("Users List validated Succesfully");
				} else {
					app_logs.info("Users List validation Failed");
					test_steps.add("Users List validation Failed");
					Assert.assertTrue(false);

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}

/*			try {
				if (propertiesList.size() > 1) {
					if (!(propertiesListActual.size() > 1)) {
						app_logs.info("Properties List validated");
					} else {
						app_logs.info("Properties List validation Failed");
						Assert.assertTrue(false);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		//
		test_steps.add(
				"========= user who has access to one property and disabled the option \"run report for other users\" =========");
		try {
			usersList.clear();
			propertiesList.clear();
			usersListActual.clear();
			propertiesListActual.clear();

			driver.close();
			Utility.switchTab(driver, 0);
			report.logout(driver, test_steps);
			login.login(driver, envURL, clientId, userName, password);
			// nav.admin(driver);
			report.admin(driver);
			nav.Roles(driver);
			admin.navigateToAdminRole(driver, roleAssociated, test_steps);
			admin.disableSpecialFunctionsForRole(driver, roleAssociated, "Run reports as other users", test_steps);
			admin.saveRole(driver, test_steps);

			// nav.admin(driver);
			report.admin(driver);
			nav.Users(driver);
			usersList = user.getAllUsersList(driver, test_steps);

			// nav.setup(driver);
			report.setup(driver);
			nav.properties(driver);
			propertiesList = prop.getPropertiesList(driver);

			admin.logout(driver, test_steps);

			login.login(driver, envURL, clientId, loginID, newPassword);

			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			report.expandGivenAdvancedOptions(driver, "Include Data From", test_steps);

			Collections.sort(usersList);
			Collections.sort(propertiesList);

			app_logs.info(usersList);
			app_logs.info(propertiesList);

			usersListActual = report.getUsersListFromIncludeDataForm(driver, test_steps);
			//propertiesListActual = report.getPropertiesListFromIncludeDataForm(driver, test_steps);

			app_logs.info(usersListActual);
			//app_logs.info(propertiesListActual);

			try {
				if (!(usersListActual.size() > 1)) {
					app_logs.info("Users List validated Successfully");
					test_steps.add("Users List validated Successfully" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-359'>"
							+ "Click here to open JIRA: RPT-359</a>");
				} else {
					app_logs.info("Users List validation Failed");
					test_steps.add("Users List validation Failed" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-359'>"
							+ "Click here to open JIRA: RPT-359</a>");
					Assert.assertTrue(false);

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}

/*			try {
				if (propertiesList.size() > 1) {
					if (!(propertiesListActual.size() > 1)) {
						app_logs.info("Properties List validated Successfully");
						test_steps.add("Properties List validated Successfully");
					} else {
						app_logs.info("Properties List validation Failed");
						test_steps.add("Properties List validation Failed");
						Assert.assertTrue(false);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		test_steps.add("=========   Validate Shift time ===================");
		try {
			report.clickShifTime(driver, test_steps);
			report.validateShiftTimeStart(driver, "10", "26", "PM", test_steps);
			report.validateShiftTimeEnd(driver, "07", "39", "AM", test_steps);
			// report.validateNowShiftTimeStart(driver, test_steps);
			// report.validateNowShiftTimeEnd(driver, test_steps);
			
			driver.close();
			Utility.switchTab(driver, 0);
			report.logout(driver, test_steps);
			login.login(driver, envURL, clientId, userName, password);
			report.admin(driver);
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
				Utility.updateReport(e, "Failed to validate Shift Time in Include Data Form", "Include Data Form",
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
		return Utility.getData("ReportsV2LedgerBalancesAdvanced", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}

}