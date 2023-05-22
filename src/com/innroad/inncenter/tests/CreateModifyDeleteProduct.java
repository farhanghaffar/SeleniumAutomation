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

import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Products;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreateModifyDeleteProduct extends TestCore {
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
	public void createModifyDeleteProduct(String productName, String productCategory, String sellOnBookingEngine,
			String productCost, String costPerFirstSelection, String costPerSecondSelection, String productDescription,
			String ratePlanType, String editedProductName, String editedProductCategory, String editedProductCost,
			String editedCostPerFirstSelection, String editedCostPerSecondSelection, String editedproductDescription)
			throws InterruptedException, IOException, ParseException {

		test_name = "CreateModifyDeleteProduct";
		test_description = "Create Modify Delete Product<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682418' target='_blank'>"
				+ "Click here to open TestRail: C682418</a>";
		test_category = "Products&Bundles";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		LedgerAccount ledgerAccount = new LedgerAccount();
		RatesGrid ratesGrid = new RatesGrid();
		Products products = new Products();
		NightlyRate nightlyRate = new NightlyRate();
		RatePackage packageRate = new RatePackage();
		ArrayList<String> incidentals = new ArrayList<String>();
		productName = productName + Utility.GenerateRandomNumber(999);
		editedProductName = editedProductName + productName;
		editedproductDescription = editedproductDescription + productDescription;

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

		// Get All RoomClasses
		try {
			test_steps.add("==========GET ALL INCIDENTALS=========");
			app_logs.info("==========GET ALL INCIDENTALS==========");

			navigation.Setup(driver);
			test_steps.add("Navigate Setup");
			navigation.LedgerAccounts(driver);
			incidentals = ledgerAccount.getIncidentalsList(driver);
			test_steps.add("Incidental List:" + incidentals);
			app_logs.info("Incidental List:" + incidentals);
			incidentals.get(Integer.parseInt(Utility.GenerateRandomNumber(incidentals.size() - 1)));
			test_steps.add("Product Category:" + productCategory);
			app_logs.info("Product Category:" + productCategory);

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

			test_steps.add("==========VERIFICATION=========");
			app_logs.info("==========VERIFICATION==========");
			navigation.Inventory(driver);
			test_steps.add("Navigate Inventory");
			navigation.productAndBundles(driver);
			app_logs.info("Navigate Product & Bundles");
			test_steps.add("Navigate Product & Bundles");
			getTest_steps.clear();
			getTest_steps = products.verifyProductsColumnsTitle(driver);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.verifyEditAndDeleteIcons(driver);
			test_steps.addAll(getTest_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Pre-Requisits", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to click Next button", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_steps.add("==========ADD A PRODUCT=========");
			app_logs.info("==========ADD A PRODUCT==========");
			getTest_steps.clear();
			getTest_steps = products.clickAddProductButtonOutside(driver);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.enterProductName(driver, productName);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.selectCategory(driver, productCategory);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.enterProductCost(driver, productCost);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.selectProductCostPerTwo(driver, costPerSecondSelection);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.selectProductCostPerOne(driver, costPerFirstSelection);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.enterProductDescription(driver, productDescription);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.clickPopUpAddProductButton(driver);
			test_steps.addAll(getTest_steps);

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

		try {

			test_steps.add("==========VERIFY PRODUCT IN PRODUCT LIST=========");
			app_logs.info("==========VERIFY PRODUCT IN PRODUCT LIST==========");
			driver.navigate().refresh();
			getTest_steps.clear();
			getTest_steps = products.verifyProductInProductList(driver, productName,
					Boolean.parseBoolean(sellOnBookingEngine), productCategory, costPerFirstSelection,
					costPerSecondSelection, productCost);
			test_steps.addAll(getTest_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Verify Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Verify Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_steps.add("==========VERIFICATION=========");
			app_logs.info("==========VERIFICATION==========");
			navigation.inventory_Backward_1(driver);
			test_steps.add("Navigate Inventory");
			navigation.ratesGrid(driver);
			app_logs.info("Navigate Rates Grid");
			test_steps.add("Navigate Rates Grid");
			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlanType);
			nightlyRate.enterRatePlanName(driver, productName, getTest_steps);
			nightlyRate.enterRateFolioDisplayName(driver, productName, getTest_steps);
			nightlyRate.enterRatePlanDescription(driver, productDescription, getTest_steps);
			nightlyRate.clickNextButton(driver, getTest_steps);
			nightlyRate.clickNextButton(driver, getTest_steps);

			packageRate.verifyProductInPackageRatePlan(driver, productName);
			packageRate.addProductInPackageRatePlan(driver, productName);
			packageRate.verifyAddedProductDetailsInPackageRatePlan(driver, productName, productCost,
					costPerFirstSelection, costPerSecondSelection);
			nightlyRate.clickNextButton(driver, getTest_steps);
			packageRate.verifyToolTipAndAddedProductDetails(driver, 1);
			packageRate.closeUnSavedPackagePlan(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Pre-Requisits", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to click Next button", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_steps.add("==========EDIT PRODUCT IN PRODUCT LIST=========");
			app_logs.info("==========EDIT PRODUCT IN PRODUCT LIST==========");
			navigation.inventory_Backward_1(driver);
			navigation.productAndBundles(driver);
			app_logs.info("Navigate Product & Bundles");
			test_steps.add("Navigate Product & Bundles");
			getTest_steps.clear();
			getTest_steps = products.editButtonClickFromProductList(driver, productName);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.enterProductName(driver, editedProductName);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.selectCategory(driver, editedProductCategory);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.enterProductCost(driver, editedProductCost);
			test_steps.addAll(getTest_steps);
			editedproductDescription = productDescription + Utility.generateRandomString();
			getTest_steps.clear();
			getTest_steps = products.selectProductCostPerTwo(driver, editedCostPerSecondSelection);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.selectProductCostPerOne(driver, editedCostPerFirstSelection);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.enterProductDescription(driver, editedproductDescription);
			test_steps.addAll(getTest_steps);
			getTest_steps.clear();
			getTest_steps = products.updateProductButtonClick(driver);
			test_steps.addAll(getTest_steps);
			driver.navigate().refresh();
			getTest_steps.clear();
			getTest_steps = products.verifyProductInProductList(driver, editedProductName,
					Boolean.parseBoolean(sellOnBookingEngine), editedProductCategory, editedCostPerFirstSelection,
					editedCostPerSecondSelection, editedProductCost);
			test_steps.addAll(getTest_steps);

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

		try {

			test_steps.add("==========VERIFICATION OF EDITED PRODUCT=========");
			app_logs.info("==========VERIFICATION OF EDITED PRODUCT==========");
			navigation.inventory_Backward_1(driver);
			test_steps.add("Navigate Inventory");
			navigation.ratesGrid(driver);
			app_logs.info("Navigate Rates Grid");
			test_steps.add("Navigate Rates Grid");
			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlanType);
			nightlyRate.enterRatePlanName(driver, editedProductName, getTest_steps);
			nightlyRate.enterRateFolioDisplayName(driver, editedProductName, getTest_steps);
			nightlyRate.enterRatePlanDescription(driver, productDescription, getTest_steps);
			nightlyRate.clickNextButton(driver, getTest_steps);
			nightlyRate.clickNextButton(driver, getTest_steps);
			packageRate.verifyProductInPackageRatePlan(driver, editedProductName);
			packageRate.addProductInPackageRatePlan(driver, editedProductName);
			packageRate.verifyAddedProductDetailsInPackageRatePlan(driver, editedProductName, editedProductCost,
					editedCostPerFirstSelection, editedCostPerSecondSelection);
			nightlyRate.clickNextButton(driver, getTest_steps);
			packageRate.verifyToolTipAndAddedProductDetails(driver, 1);
			packageRate.closeUnSavedPackagePlan(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Verify Edited Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Verify Edited Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_steps.add("==========DELETE PRODUCT IN PRODUCT LIST=========");
			app_logs.info("==========DELETE PRODUCT IN PRODUCT LIST==========");
			navigation.inventory_Backward_1(driver);
			navigation.productAndBundles(driver);
			app_logs.info("Navigate Product & Bundles");
			test_steps.add("Navigate Product & Bundles");
			getTest_steps.clear();
			getTest_steps = products.deleteProductFromProductList(driver, editedProductName);
			test_steps.addAll(getTest_steps);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Delete Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Delete Product", testName, "Products", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateModifyDeleteProduct", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
