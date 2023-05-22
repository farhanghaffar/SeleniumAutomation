package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Products;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CPVerifyTaxforBundlePackageOnReservation extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testSteps1 = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyTaxforBundlePackageOnReservation(String roomClassName, String ratePlanName, String checkInDate,
			String checkOutDate, String adults, String children, String salutation, String guestFirstName,
			String guestLastName, String paymentType, String cardNumber, String nameOnCard, String marketSegment,
			String referral, String productName, String productCost, String productCategory,
			String costPerFirstSelection, String costPerSecondSelection, String productDescription, String taxname,
			String description, String taxType, String catgoriesTax, String taxCategoriesValue,
			String ledgerAccount) throws ParseException {
		String testName = null, confirmationNo = null;
		List<String> roomNumbers = new ArrayList<String>();
		Navigation navigation = new Navigation();
		NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		Products products = new Products();
		TaxesAndFee taxFee = new TaxesAndFee();
		ArrayList<String> productsName = new ArrayList<String>();
		ArrayList<String> productsCosts = new ArrayList<String>();
		ArrayList<String> productNames = new ArrayList<String>();
		ArrayList<String> productsCategory = new ArrayList<String>();
		test_name = "CPVerifyTaxforBundlePackageOnReservation";
		test_description = "CPVerifyTaxforBundlePackageOnReservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848658' target='_blank'>"
				+ "Click here to open TestRail: 848658</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Utility.initializeTestCase("848658", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		try {
			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======Create Products======</b>");
			app_logs.info("<b>======Create Products======</b>");
			navigation.inventory(driver);
			testSteps.add("Navigated Inventory");
			navigation.productAndBundles(driver);
			testSteps.add("Navigated Product andf Bundles");
			ArrayList<String> test = new ArrayList<String>();					
			ArrayList<String> productsDesc = new ArrayList<String>();
			productsName = Utility.splitInputData(productName);
			productsCosts = Utility.splitInputData(productCost);
			productsCategory = Utility.splitInputData(productCategory);
			productsDesc = Utility.splitInputData(productDescription);
			for (int i = 0; i < productsName.size(); i++) {
				String name=productsName.get(i)+Utility.generateRandomStringWithoutNumbers();
				productNames.add(name);
				testSteps1=products.createProduct(driver, name, productsCosts.get(i), productsCategory.get(i),
						costPerFirstSelection, costPerSecondSelection, productsDesc.get(i), false, "", test, "", "",
						"");
				testSteps.addAll(testSteps1);
				testSteps.add("Create Product " + name);
				app_logs.info("Create Product " + name);
			}
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Products", "Products & Bundles", "Products & Bundles", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Products", "Products & Bundles", "Products & Bundles", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======Create Tax======</b>");
			app_logs.info("<b>======Create Tax======</b>");
			navigation.navigateToSetupfromRateGrid(driver);
			navigation.clickTaxesAndFees(driver);
			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", testSteps);
			}
			taxname=taxname+Utility.generateRandomStringWithoutNumbers();
			taxFee.createTaxes(driver, taxname, taxname, description, taxType, catgoriesTax, taxCategoriesValue, "",
					ledgerAccount, false, "", "", "", testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "C", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Abberaviation Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		}
		// Create Reservation
		try {
			testSteps.add("<b>======Create Reservation======</b>");
			app_logs.info("<b>======Create Reservation======</b>");
			ArrayList<String> roomNumber = new ArrayList<String>();
			confirmationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, adults, children,
					ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard,
					marketSegment, referral, roomClassName, true, false);
			roomNumbers = reservation.getStayInfoRoomNo(driver, testSteps);
			app_logs.info(roomNumbers);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======AddOns Products IN Reservation======</b>");
			app_logs.info("<b>======AddOns Products IN Reservation======</b>");			
			for(int i=0;i<productNames.size();i++) {
				reservation.clickAddOnsButton(driver);
				testSteps1=reservation.addingAddOns(driver, productNames.get(i), "", "", false, "");
				testSteps.addAll(testSteps1);
			}
			reservation.verifySpinerLoading(driver);
			for(int i=0;i<productNames.size();i++) {
			if(productsCategory.get(i).equalsIgnoreCase("Room Charge")) {
			String expectedTax = reservation.calculationOfCheckInAmountToBePaidForRateV2(productsCosts.get(i),
					taxCategoriesValue);
			String expectedAmount=String.valueOf(Double.parseDouble(productsCosts.get(i))+Double.parseDouble(expectedTax));
			app_logs.info(expectedTax);
			app_logs.info(expectedAmount);
			reservation.verifyIncidentalsLineItemTaxAndAmount(driver, productNames.get(i), expectedAmount, expectedTax,
					testSteps);}
			else{
				reservation.verifyIncidentalsLineItemTaxAndAmount(driver, productNames.get(i), productsCosts.get(i), "",
						testSteps);
				}
			}
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments,
					"Verify taxes associated to room charge are not applying to package ledger");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);
			reservation.closeReservationTab(driver);
			testSteps.add("====================Delete Tax======================");
			navigation.setup(driver);
			navigation.clickTaxesAndFees(driver);
			testSteps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");
			taxFee.deleteTaxAndFee(driver, taxname, testSteps);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Move Payment Line Item", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Move Payment Line Item", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CPVerifyTaxforBundlePackageOnRe", envLoginExcel);
	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
