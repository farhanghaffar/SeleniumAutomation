package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2LedgerBalancesSelectInputsUI extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	//ArrayList<String> reportsAccess = new ArrayList<>();
	//ArrayList<String> reports = new ArrayList<>();
	
	ArrayList<String> AllLedgerAccountTypes = new ArrayList<>();
	
	HashSet<String> typesUnique = new HashSet<>();
	ArrayList<String> names = new ArrayList<>();
	ArrayList<String> types = new ArrayList<>();
	ArrayList<String> status = new ArrayList<>();
	ArrayList<String> typesAvailable = new ArrayList<>();
	ArrayList<String> expectedOptions = new ArrayList<>();
	ArrayList<String> allOptions = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void verifyReportsV2LedgerBalancesReportSelectInputs(String url, String clientId, String userName,
			String password)throws InterruptedException, IOException, ParseException {

		test_name = "VerifyReportsV2LedgerBalancesSelectInputsUI";
		test_description = "ReportsV2 - VerifyReportsV2LedgerBalancesSelectInputsUI<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682418' target='_blank'>"
				+ "Click here to open TestRail: C682418</a>";
		test_category = "ReportsV2 - Ledger Balances report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		Login login = new Login();
		Admin admin = new Admin();
		ReportsV2 report = new ReportsV2();
		LedgerAccount la = new LedgerAccount();
		
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
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
		
		} catch (Error e) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
		}
		
		//Getting account details from Ledger Account page
		test_steps.add("========= Getting account details from Ledger Account page =========");
		try {
			
			report.setup(driver);
			nav.LedgerAccounts(driver);
			la.PropertyTab(driver);
			
			names = la.getAllNamesFromLedgerAccount(driver, test_steps);
			types = la.getAllTypesFromLedgerAccount(driver, test_steps);
			status = la.getAllAccountsStatusFromLedgerAccount(driver, test_steps);
			typesUnique = la.getAllTypesUnique(driver, test_steps);
			
			//test_steps.add("Names- Count "+names.size()+", List: "+names);
			//test_steps.add("Types- Count "+types.size()+", List: "+names);
			//test_steps.add("Status- Count "+status.size()+", List: "+names);
			//test_steps.add("Unique type- Count "+typesUnique.size()+", List: "+names);
			
			app_logs.info("Names: "+names.size()+" Details: "+names);
			app_logs.info("Types: "+types.size()+" Details: "+types);
			app_logs.info("Status: "+status.size()+" Details: "+status);
			app_logs.info("Status: "+typesUnique.size()+" Details: "+typesUnique);
			
			app_logs.info("Got all account details from Ledger account");
			test_steps.add("Got all account details from Ledger account");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Reading Ledger Account details", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Reading Ledger Account details", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		
		// Validating all the items availability
		test_steps.add("========= Validating all the items availability =========");
		try {
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			Assert.assertTrue(report.verifySelectInputAvailability(driver, test_steps), "Failed, Select Input option is not available on Ledger Balances Report page");
			//Assert.assertTrue(report.verifyIncludeLedgerAccountAvailability(driver, test_steps), "Failed, Included Ledger Accounts option is not available on Ledger Balances Report page");

			//report.validateIncludeLedgerAccountsToolTip(driver, test_steps);			
			report.validateExcludeZeroBalanceLedgerAccountsAvailability(driver, test_steps);			
			report.validateDisplayCustomGeneralLedgerAccountAvailability(driver, test_steps);

			test_steps.add("========= Validating Select Inputs order in Multiselect model popup =========");

			report.validateSeletInputsOrder(driver, test_steps);
			
			typesAvailable = report.getAvailableTypes(driver, typesUnique, test_steps);
			app_logs.info("Available types: "+typesAvailable);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs order", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating all itrms availability", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating all itrms availability", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validate Select input 0/0 should not present
		test_steps.add("========= Validate Select input 0/0 should not present ==========");
		try {
			
			report.validate_0_0_Functionality(driver, typesAvailable, test_steps);
			app_logs.info("0/0 validated successfully");
			test_steps.add("0/0 validated successfully");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating 0/0 Select Inputs", test_description, test_category, test_steps);
				
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Select Input checkbox and clear", "ReportV2 - Ledger Balances Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Select Input options Default values
		test_steps.add("===== Validating Select Inputs default values ======");
		try {
			
			report.validateSelectInputsDefaultValues(driver, typesUnique, test_steps);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs default values", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		
		
		
		
		
		
		
		
		//Validating Select Input options availability and Count
		test_steps.add("===== Validating Select Input options availability and Count ======");
		try {
			//typesAvailable = report.getAvailableTypes(driver, typesUnique, test_steps);
			//app_logs.info("Available types: "+typesAvailable);
			report.clearAllInputOptions(driver, test_steps);
			report.validateLedgerAccountWithReport(driver, typesUnique, names, types, status, test_steps);
						
			expectedOptions.add("Acc (Corp/Mem)-Transfer");
			expectedOptions.add("Acc (Group)-Transfer");
			expectedOptions.add("Acc (Travel Agent)-Transfer");
			expectedOptions.add("Acc (Unit Owner)-Transfer");
			expectedOptions.add("Reservation");
			
			report.validateTransfersInput(driver, expectedOptions, test_steps);
			report.validateAdvanceDepositAvailability(driver, test_steps);
			
			app_logs.info("Select Inputs: All Select inputs and options count validated with Ledger Accounts");
			test_steps.add("Select Inputs: All Select inputs and options count validated with Ledger Accounts");
			report.selectAllInputOptions(driver, test_steps);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Selet Inputs and values and values count with Ledger accounts", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating selecting input options popup
		test_steps.add("========= Validating select input options popup =========");
		try {
			//for (String type : typesUnique) {
			for (String type : typesAvailable) {
			
				Wait.wait2Second();
				report.validateSelectInputsPopup(driver, type, test_steps);
				report.closePopupSelectInputs(driver, test_steps);

			}
			app_logs.info("Select Inputs: Select input options popup validated for all select inputs");
			test_steps.add("Select Inputs: Select input options popup validated for all select inputs");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs MultiSelect Popup", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
			
		//Validating selecting input options selection
		test_steps.add("========= Validating selecting input options selection =========");
		try {
			
			report.verifySelectInputOption(driver, "Incidental", "Telephone", test_steps);
			//report.verifySelectInputOption(driver, "Incidentals", "Parking Parking", test_steps);
			report.verifySelectInputOption(driver, "Payment Method", "Cash", test_steps);
			report.verifySelectInputOption(driver, "Taxes", "Service Charge", test_steps);
			report.verifySelectInputOption(driver, "Room Charge", "Room Discount", test_steps);
			//report.verifySelectInputOption(driver, "Fees", "Fee Adjustment", test_steps);
			
			app_logs.info("Select Inputs: Select input options validated successfully");
			test_steps.add("Select Inputs: Select input options validated successfully");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs options selection", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
				
		//Validating Select and De-select all input options 
		test_steps.add("========= Validating Select and Deselect all input options =========");
		try {
			
			for (String type : typesAvailable) {
			
				report.validateSelectAndDeselectAllInputOptions(driver, type, test_steps);
				
			}
			
			//report.validateSelectAndDeselectAllInputOptions(driver, typesAvailable.get(1), test_steps);
			//test_steps.add("Select Inputs: Select all and Deselect all validated successfully");
			
			app_logs.info("Select Inputs: Select all and Deselect all validated successfully for all select inputs");
			test_steps.add("Select Inputs: Select all and Deselect all validated successfully for all select inputs");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs Select All and Delect All options", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating plus and close icons on select input options popup
		test_steps.add("========= Validating plus and close icons on select input options popup =========");
		try {
			
			//for (String type : typesUnique) {
//			for (String type : typesAvailable) {
//			
//				report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, type, test_steps);
//				
//			}
			report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, "Incidental", test_steps);
			
			//report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, typesAvailable.get(1), test_steps);
			test_steps.add("Select Inputs: Plus and close icons validated sucessfully");
			
			//app_logs.info("Select Inputs: Plus and close icons validated sucessfully for all select inputs");
			//test_steps.add("Select Inputs: Plus and close icons validated sucessfully for all select inputs");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs plus and close icons", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
	
/*		// Validate Search in Available options
		test_steps.add("========= Validate Search in Available options  ==========");
		try {
			report.clickSelectInput(driver, "Incidental", test_steps);
			report.validateSearchAvailable(driver, "Incidental", "ac", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
						
			app_logs.info("Select Inputs: Search in Available validated successfully");
			test_steps.add("Select Inputs: Search in Available validated successfully");
			
			//RetryFailedTestCases.count = Utility.reset_count;
			//Utility.AddTest_IntoReport("Validating Search in Available options", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}*/
		
		// Validate Search in Selected options
		test_steps.add("========= Validate Search in Selected options  ==========");
		try {
			//report.clickSelectInput(driver, "Incidental", test_steps);
			Wait.wait2Second();
			report.selectAllInputOptions(driver, "Incidental", test_steps);
			report.validateSearchSelected(driver, "Incidental", "ac", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
						
			app_logs.info("Select Inputs: Search in Selected validated successfully");
			test_steps.add("Select Inputs: Search in Selected validated successfully");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Search in Available and Selected options", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		
/*		// Validate See All functionality
		test_steps.add("========= Validate See All functionality  ==========");
		try {

			report.validateSeeAll(driver, test_steps);
			app_logs.info("Select Inputs: See All functionality validated successfully");
			test_steps.add("Select Inputs: See All functionality validated successfully");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}*/
		
		// Validate Exclude Zero Balance Ledger Accounts Yes/No buttons
		test_steps.add("============ Validate Exclude Zero Balance Ledger Accounts Yes/No buttons  ==============");
		try {

			report.validateExcludeZeroBalanceLedgerAccountsOption(driver, test_steps);
			app_logs.info("Select Inputs: Exclude Zero Balance Ledger Accounts Yes/No buttons validated successfully");
			test_steps.add("Select Inputs: Exclude Zero Balance Ledger Accounts Yes/No buttons validated successfully");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		// Validate Display Custom General Ledger Account  Yes/No buttons
		test_steps.add("============ Validate Display Custom General Ledger Account  Yes/No buttons ==============");
		try {

			report.validateExcludeZeroBalanceLedgerAccountsOption(driver, test_steps);
			app_logs.info("Select Inputs: Display Custom General Ledger Account Yes/No buttons validated successfully");
			test_steps.add("Select Inputs: Display Custom General Ledger Account Yes/No buttons validated successfully");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Exclude Zero Balances and Display Custom General Ledger Accounts", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		
		// create and Inactive and Delete Ledger account and validate in Ledger Balances Report
		test_steps.add("==== create and Inactive and Delete Ledger account and validate in Ledger Balances Report===");
		try {
			test_steps.add("==== Creating new account and validating in Reports ======");
			//String acName = Utility.generateRandomString();
			String acName = "New Account "+Utility.generateRandomString()+" "+Utility.generateRandomString()+" "+Utility.generateRandomString();
			int beforeCount;
			int afterCount;
			
			beforeCount = report.getAllInputOptionsCount(driver, "Incidental", test_steps);
			
			driver.close();
			Utility.switchTab(driver, 0);
			
			report.setup(driver);
			
			try {
			
			nav.LedgerAccounts(driver);
			
			la.NewAccountbutton(driver);
			la.LedgerAccountDetails(driver, acName, "Test", "", "Incidental", "Active");
			la.SaveLedgerAccount(driver);
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create new Ledger account", "ReportsV2 - Ledger Balances Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}
			
			//login.logout(driver);
			admin.logout(driver);
			
			//login.login(driver, envURL, clientId, userName, password);
			loginReportsV2(driver);
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			afterCount = report.getAllInputOptionsCount(driver, "Incidental", test_steps);
			
			
			report.clickSelectInput(driver, "Incidental", test_steps);
			allOptions = report.getInputValuesFromGivenCategory(driver, "Incidental", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			
			try {
				
				if (beforeCount+1 == afterCount) {
					app_logs.info("New Ledger Account created and validated total count successfully - "+acName);
					test_steps.add("New Ledger Accoont created and validated total count successfully - "+acName);
					
					if (allOptions.contains(acName)) {
						app_logs.info("Success - We are able to see newly created account in Reports Select input - "+acName);
						test_steps.add("Success - We are able to see newly created account in Reports Select input - "+acName);
					}else {
						Assert.assertTrue(false, "Failed - We are not are able to see newly created account in Reports Select input");
					}
				}else {
					Assert.assertTrue(false, "After creating new Ledger account, count is not increasing in ReportsV2 Ledger Balances page");
				}
				
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
						Utility.updateReport(e, "Failed - Create in Ledger account and validate in Ledger Balances Report", "ReportsV2 - Ledger Balances Report",
								"ReportsV2", driver);
					} else {
						Assert.assertTrue(false);
					}
			
			} catch (Error e) {
				test_steps.add(e.toString());
			}
			
			test_steps.add("==== Inactive account and validating in Reports ======");
			
			beforeCount = afterCount;
			
			driver.close();
			Utility.switchTab(driver, 0);
			
			report.setup(driver);
			
			try {
			
				nav.LedgerAccounts(driver);
				//la.PropertyTab(driver);
				
				la.ChangeStatus(driver, acName, "InActive");
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to delete Ledger account", "ReportsV2 - Ledger Balances Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());

			}
			
			admin.logout(driver);
			
			//login.login(driver, envURL, clientId, userName, password);
			loginReportsV2(driver);
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			afterCount = report.getAllInputOptionsCount(driver, "Incidental", test_steps);
			
			report.clickSelectInput(driver, "Incidental", test_steps);
			allOptions = report.getInputValuesFromGivenCategory(driver, "Incidental", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			
			try {
			
				if (beforeCount-1 == afterCount) {
					app_logs.info("Inactive Ledger Account and Count decsreaed in Reports page validated successfully");
					test_steps.add("Inactive Ledger Accoont and Count decsreaed in Reports page validated successfully");
					
					if (!allOptions.contains(acName)) {
						app_logs.info("Success - Inactive Ledger account is not visible in Reports Select input - "+acName);
						test_steps.add("Success - Inactive Ledger account is not visible in Reports Select input - "+acName);
					}else {
						Assert.assertTrue(false, "Failed - We are able to see Inactive account in Reports Select input");
					}
				}else {
					Assert.assertTrue(false, "After Inactive Ledger account, count is not decreasing in ReportsV2 Ledger Balances page");
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed - Inactive Ledger account and validate in Ledger Balances Report", "ReportsV2 - Ledger Balances Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			}
			
			test_steps.add("==== Active account and validating in Reports ======");
			
			beforeCount = afterCount;
			
			driver.close();
			Utility.switchTab(driver, 0);

			report.setup(driver);
			
			try {
			
				nav.LedgerAccounts(driver);
				//la.PropertyTab(driver);
				
				la.SelectStatus(driver, "InActive");
				la.ChangeStatus(driver, acName, "Active");
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to delete Ledger account", "ReportsV2 - Ledger Balances Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());

			}
			
			admin.logout(driver);
			
			//login.login(driver, envURL, clientId, userName, password);
			loginReportsV2(driver);
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			afterCount = report.getAllInputOptionsCount(driver, "Incidental", test_steps);
			
			report.clickSelectInput(driver, "Incidental", test_steps);
			allOptions = report.getInputValuesFromGivenCategory(driver, "Incidental", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			
			try {
			
				if (beforeCount+1 == afterCount) {
					app_logs.info("Active Ledger Account then count increased in Reports validated successfully");
					test_steps.add("Active Ledger Account then count increased in Reports validated successfully");
					
					if (allOptions.contains(acName)) {
						app_logs.info("Success - Active Ledger account then we are able see account in Reports Select input - "+acName);
						test_steps.add("Success - Active Ledger account then we are able see accoun in Reports Select input - "+acName);
					}else {
						Assert.assertTrue(false, "Failed - We are not able to see Active account in Reports Select input after active the account");
					}
				}else {
					Assert.assertTrue(false, "After Acive Ledger account, count is not increasing in ReportsV2 Ledger Balances page");
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed - Active Ledger account and validate in Ledger Balances Report", "ReportsV2 - Ledger Balances Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			}
			
			test_steps.add("==== Delete account and validating in Reports ======");
			
			beforeCount = afterCount;
			
			driver.close();
			Utility.switchTab(driver, 0);

			report.setup(driver);
			
			try {
			
				nav.LedgerAccounts(driver);
				//la.PropertyTab(driver);
				
				la.DeleteLedgerAccount_PropertyTab(driver, acName);
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to delete Ledger account", "ReportsV2 - Ledger Balances Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());

			}
			
			admin.logout(driver);
			
			//login.login(driver, envURL, clientId, userName, password);
			loginReportsV2(driver);
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			afterCount = report.getAllInputOptionsCount(driver, "Incidental", test_steps);
			
			report.clickSelectInput(driver, "Incidental", test_steps);
			allOptions = report.getInputValuesFromGivenCategory(driver, "Incidental", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			
			try {
			
				if (beforeCount-1 == afterCount) {
					app_logs.info("Deleted Ledger Account and validated successfully");
					test_steps.add("Deleted Ledger Account and validated successfully");
					
					if (!allOptions.contains(acName)) {
						app_logs.info("Success - Delete Ledger account then it's not visible in Reports Select input - "+acName);
						test_steps.add("Success - Delete Ledger account then it's not visible in Reports Select input Select input - "+acName);
					}else {
						Assert.assertTrue(false, "Failed - We are able to see Deleted account in Reports Select input after Deleting the account");
					}
					
				}else {
					Assert.assertTrue(false, "After deleting Ledger account, count is not decreasing in ReportsV2 Ledger Balances page");
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed - delete in Ledger account and validate in Ledger Balances Report", "ReportsV2 - Ledger Balances Report",
							"ReportsV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			}
			
			
			app_logs.info("Select Inputs: Create and delete Ledger accounts and validating in Reports is Completed");
			test_steps.add("Select Inputs: Create and delete Ledger accounts and validating in Reports is Completed");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Creating, Aactive, InActive and Deleting Ledger Accounts", test_description, test_category, test_steps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed Create and delete in Ledger account and validate in Ledger Balances Report", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
//		//Validate Select input 0/0 should not present
//		test_steps.add("========= Validate Select input 0/0 should not present ==========");
//		try {
//			
//			report.validate_0_0_Functionality(driver, typesAvailable, test_steps);
//			app_logs.info("0/0 validated successfully");
//			test_steps.add("0/0 validated successfully");
//				
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
//				Utility.updateReport(e, "Failed to validate Select Input checkbox and clear", "ReportV2 - Ledger Balances Report",
//						"ReportsV2", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
			
			
/*		//Validate Select input checkbox and Clear all functionalities
		test_steps.add("========= Validate Select input checkbox and Clear all functionalities ==========");
		try {

			//for (String type : types) {
			for (String type : typesAvailable) {
				
				try {
					report.validateSelectInputCheckboxAndClearAll(driver, type, test_steps);	

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
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				}
			}
			
			//app_logs.info("Select Inputs: Checkbox and clear all functionality validated successfully");
			//test_steps.add("Select Inputs: Checkbox and clear all functionality validated successfully");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs checkbox and Clear All ", test_description, test_category, test_steps);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Select Input checkbox and clear", "ReportV2 - Ledger Balances Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}*/
		//RetryFailedTestCases.count = Utility.reset_count;
		//Utility.AddTest_IntoReport("Validating Select Inputs checkbox and Clear All ", test_description, test_category, test_steps);
	
	}
	
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2LedgerBalancesSelect", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
	
}
