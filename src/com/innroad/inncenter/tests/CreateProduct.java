package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Products;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreateProduct extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Products")
	public void createProduct(String productName, String productCategory, String sellOnBookingEngine,
			String productCost, String costPerFirstSelection, String costPerSecondSelection, String productDescription,
			String sellingTypeOnBookingEngine, String roomClassToSelect) throws InterruptedException, IOException {

		test_name = "Create Product";
		test_description = "Create  Product<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682418' target='_blank'>"
				+ "Click here to open TestRail: C682418</a>";
		test_category = "Products&Bundles";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		LedgerAccount ledgerAccount = new LedgerAccount();
		Products products = new Products();
		ArrayList<String> incidentals = new ArrayList<String>();
		String dateFormat = "MM/dd/yyyy";
		String startDate = Utility.GetNextDate(1, dateFormat);
		String endDate = Utility.GetNextDate(2, dateFormat);
		productName = productName + Utility.GenerateRandomNumber(999);
		;

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			Login login = new Login();
			login.login(driver, "https://www.app.qainnroad.com", "autocp", "autouser", "Auto@123");
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

		// Get All Incidentals
		try {

			test_steps.add("==========GET ALL INCIDENTALS=========");
			app_logs.info("==========GET ALL INCIDENTALS==========");
			navigation.Setup(driver);
			navigation.LedgerAccounts(driver);
			incidentals = ledgerAccount.getIncidentalsList(driver);
			test_steps.add("Incidental List:" + incidentals);
			app_logs.info("Incidental List:" + incidentals);
			productCategory = incidentals.get(Integer.parseInt(Utility.GenerateRandomNumber(incidentals.size() - 1)));
			test_steps.add("Get Product Category:" + productCategory);
			app_logs.info("Get Product Category:" + productCategory);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Pre-Requisits", testName, "Incidentals/RoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed toget Pre-Requisits", testName, "Incidentals/RoomClasses", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_steps.add("==========CREATE PRODUCT=========");
			app_logs.info("==========CREATE PRODUCT==========");
			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			navigation.productAndBundles(driver);
			app_logs.info("Navigate Product & Bundles");
			test_steps.add("Navigate Product & Bundles");
			ArrayList<String> roomClasses = new ArrayList<String>();
			for (int i = 0; i < roomClassToSelect.split("\\|").length; i++) {

				roomClasses.add(roomClassToSelect.split("\\|")[i]);
			}
			products.createProduct(driver, productName, productCost, productCategory, costPerFirstSelection,
					costPerSecondSelection, productDescription, Boolean.parseBoolean(sellOnBookingEngine),
					sellingTypeOnBookingEngine, roomClasses, dateFormat, startDate, endDate);

			app_logs.info("Product  Generated With Details:" + productName + " Category:" + productCategory + " Cost:"
					+ productCost + " FirstSelection:" + costPerFirstSelection + " SecondSelection:"
					+ costPerSecondSelection);
			test_steps.add("Product  Generated With Details:" + productName + " Category:" + productCategory + " Cost:"
					+ productCost + " FirstSelection:" + costPerFirstSelection + " SecondSelection:"
					+ costPerSecondSelection);

			ArrayList<String> productDetails = new ArrayList<String>();
			productDetails = products.createRandomProduct(driver, incidentals);
			app_logs.info("Generated Product Details:" + productDetails);
			test_steps.add("Generated Product Details:" + productDetails);
			int numberofProducts = 2;
			List<String>[] allProducts = new List[numberofProducts];
			allProducts = products.createRandomProduct(driver, incidentals, numberofProducts);
			for (int i = 0; i < numberofProducts; i++) {
				app_logs.info("All Generated Products With Details:" + allProducts[i]);
				test_steps.add("All Generated Products With Details:" + allProducts[i]);
			}
			products.deleteProductFromProductList(driver, productName);
			products.deleteProductFromProductList(driver, productDetails.get(0));

			for (int i = 0; i < numberofProducts; i++) {
				products.deleteProductFromProductList(driver, allProducts[i].get(0));
				;
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Add Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Add Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateProduct", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
