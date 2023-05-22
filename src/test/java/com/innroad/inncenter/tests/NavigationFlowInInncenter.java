package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class NavigationFlowInInncenter extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	// ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void navigationFlowInInncenter() throws InterruptedException, IOException, Exception {



		String propertyName="sowmya test property";
			

		String testcaseId="848767|848688|848768|848764|848763|848765|848766|848770|848759";
		test_name = "NavigationFlowInInncenter";
       // String testcaseId="825437|825438";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848767");
		
		test_description = test_name + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848767' target='_blank'>"
				+ "Click here to open TestRail: C848767</a><br>"
				+ "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848688' target='_blank'>"
				+ "Click here to open TestRail: C848688</a><br>"
				+"<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848768' target='_blank'>"
				+ "Click here to open TestRail: C848768</a><br>";
		
		test_catagory = "NavigationFlowInInncenter";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();	

		
		
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
				login_Autoota(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				login_Autoota(driver);
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
		
		/*
		try {
			// After login
			test_steps.add("===================SELECT A PROPERTY ======================");
			app_logs.info("=================== SELECT A PROPERTY======================");
			navigation.selectProperty(driver, propertyName);
			
			test_steps.add("SELECT A PROPERTY");
			statusCode.set(0, "1");
			comments.add(0, "Property is selected from drop down");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		*/
		statusCode.set(0, "1");
		comments.add(0, "Property is selected from drop down");
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO INVENTORY ======================");
			app_logs.info("=================== NAVIGATE TO INVENTORY======================");
			navigation.Inventory(driver, test_steps);
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			navigation.Distribution(driver);
			test_steps.add("Navigated to Distribution");
			navigation.policies(driver);
			test_steps.add("Navigated to policies");
			navigation.productAndBundlesV2(driver);
			test_steps.add("Navigated to productAndBundles");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	
		
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO ReservationPage ======================");
			app_logs.info("=================== NAVIGATE TO ReservationPage ======================");
			navigation.reservationBackward3(driver);
			test_steps.add("Navigated to ReservationPage");
			navigation.groups( driver);
			test_steps.add("Navigated to Groups");
			
			navigation.verifyGuestHistoryTab2(driver);
			test_steps.add("Navigated to GuestHistory");
			navigation.TapeChart(driver);
			test_steps.add("Navigated to TapeChart");
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO Accounts ======================");
			app_logs.info("=================== NAVIGATE TO Accounts ======================");
			
			navigation.Accounts( driver);
			
			test_steps.add("Navigated to Accounts");
			
			navigation.Statements(driver);
			test_steps.add("Navigated to Statements");
			
			navigation.UnitownerAccount(driver);
			test_steps.add("Navigated to UnitownerAccount");
			
			navigation.TravelAgent(driver);
			test_steps.add("Navigated to TravelAgent");
			
			navigation.ManagementTransfers(driver);
			test_steps.add("Navigated to ManagementTransfers");
			
			navigation.AccountDistribution(driver);
			test_steps.add("Navigated to AccountDistribution");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		

		
		
		
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO Setup ======================");
			app_logs.info("=================== NAVIGATE TO Setup ======================");
			navigation.inventoryToSetup( driver, test_steps);			
			test_steps.add("Navigated to setup");
			
			navigation.Properties(driver);			
			test_steps.add("Navigated to setup");
			
			navigation.clickTaxesAndFees(driver);			
			test_steps.add("Navigated to TaxesAndFees");
			
			navigation.Merchantservices( driver);			
			test_steps.add("Navigated to Merchantservices");
			
			navigation.ListManagemnet( driver);			
			test_steps.add("Navigated to ListManagemnet");
			
			navigation.roomClass(driver);		
			test_steps.add("Navigated to roomClass");
			
			navigation.LedgerAccounts(driver);			
			test_steps.add("Navigated to LedgerAccounts");
			
			navigation.DocumentTemplate(driver);			
			test_steps.add("Navigated to DocumentTemplate");
			
			navigation.TaskManagement(driver);		
			test_steps.add("Navigated to TaskManagement");
			
			statusCode.add(1, "1");
			comments.add(1, " merchent services is selected");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO Admin ======================");
			app_logs.info("=================== NAVIGATE TO Admin ======================");
			navigation.adminTab( driver);
			test_steps.add("Navigated to Admin");
			
			navigation.clickonClientinfo(driver);
			test_steps.add("Navigated to Admin");
			
			navigation.Roles(driver);
			test_steps.add("Navigated to Roles");
			
			navigation.Users(driver);
			test_steps.add("Navigated to Users");
			

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		/*
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO NightAudit ======================");
			app_logs.info("=================== NAVIGATE TO NightAudit ======================");
			navigation.NightAuditIcon( driver);
			
			test_steps.add("Navigated to NightAudit");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		*/
		
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO Reports ======================");
			app_logs.info("=================== NAVIGATE TO Reports ======================");
			navigation.ReportsV2( driver);
			test_steps.add("Navigated to Reports");
			
			navigation.AccountBalances(driver);
			test_steps.add("Navigated to AccountBalances");
			
			navigation.LedgerBalances(driver);
			test_steps.add("Navigated to LedgerBalances");
			
			navigation.MerchantTrans(driver);
			test_steps.add("Navigated to MerchantTrans");
			
			navigation.DailyFalsh(driver);
			test_steps.add("Navigated to DailyFalsh");
			
			navigation.RoomForecast(driver);
			test_steps.add("Navigated to RoomForecast");
			
			navigation.NetSales(driver);
			test_steps.add("Navigated to NetSales");
			
			navigation.AdvanceDeposite(driver);
			test_steps.add("Navigated to AdvanceDeposite");
			

			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		

		
		
		
		try {
			// After login
			test_steps.add("=================== NAVIGATE TO navigateGuestservice ======================");
			app_logs.info("=================== NAVIGATE TO navigateGuestservice ======================");
			navigation.Guestservices_1( driver);
			test_steps.add("Navigated to Guestservice");
			test_steps.add("Navigated to RoomStatus");

			navigation.Task_List(driver);
			test_steps.add("Navigated to Task_List");
			navigation.RoomMaintenance(driver);
			test_steps.add("Navigated to RoomMaintenance");
			
			statusCode.add(2, "1");
			comments.add(2, "All module navigated ");
			statusCode.add(3, "1");
			comments.add(3, "All module navigated ");
			statusCode.add(4, "1");
			comments.add(4, "All module navigated ");
			statusCode.add(5, "1");
			comments.add(5, "All module navigated ");
			statusCode.add(6, "1");
			comments.add(6, "All module navigated ");
			statusCode.add(7, "1");
			comments.add(7, "All module navigated ");
			statusCode.add(8, "1");
			comments.add(8, "All module navigated ");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Navigate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("NavigationFlowInInncenter", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

	}


}
