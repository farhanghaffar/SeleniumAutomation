package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.IRatePackage;
import com.innroad.inncenter.pages.DerivedRatePage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_PackageRatePlan;
import com.innroad.inncenter.properties.OR_Products;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_DerivedRate;
import com.innroad.inncenter.webelements.Elements_Inventory;
import com.innroad.inncenter.webelements.Elements_PackageRate;
import com.innroad.inncenter.webelements.Elements_Products;

public class RatePackage implements IRatePackage {

	public static Logger packageRateLogger = Logger.getLogger("RatePackage");

	public void inventory_Rate(WebDriver driver) throws InterruptedException {

		Elements_Inventory rate = new Elements_Inventory(driver);
		Wait.wait3Second();
		rate.click_Inventory.click();
		rate.inventory_rate.click();
		Wait.wait10Second();
		packageRateLogger.info(" System successfully Navigated to Inventory Rates ");
		// Wait.wait10Second();
	}

	public void package_details(WebDriver driver, String packageName) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Elements_Inventory packagerate = new Elements_Inventory(driver);
		Wait.explicit_wait_xpath(OR.newRate, driver);
		packagerate.newRate.click();
		Wait.explicit_wait_xpath(OR.rateName, driver);
		packagerate.rateName.sendKeys(packageName);
		String packageplan = new Select(packagerate.ratePlan).getFirstSelectedOption().getText();

		packageRateLogger.info("Selected Rate Plan : " + packageplan);

		// packageRateLogger.info( "Selected Rate Plan : " +derivedplan);
		packagerate.selectPackageRatePlan.click();
		String rateType = packagerate.getRateType3.getText();
		packageRateLogger.info(" Selected Rate Type : " + rateType);
	}

	public void package_components(WebDriver driver, String PackageCompDescription, String PackageAmount)
			throws InterruptedException {

		Elements_Inventory packagerate = new Elements_Inventory(driver);
		String packageComponents = packagerate.getPackageComponents.getText();
		packageRateLogger.info("packageComponents : " + packageComponents);

		packagerate.clickPackageAddButton.click();

		Select dropdown = new Select(packagerate.selectPackageCategory);
		java.util.List<WebElement> options = dropdown.getOptions();
		int itemSize = options.size();
		packageRateLogger.info(" No of Package rates : " + itemSize);
		Wait.wait3Second();
		/*
		 * for(int i = 0; i < itemSize ; i++) { String optionsValue =
		 * options.get(i).getText(); packageRateLogger.info("Category : "
		 * +optionsValue);
		 */

		// }

		new Select(packagerate.selectPackageCategory).selectByIndex(5);
		Wait.wait3Second();
		String getpackageComponentDescription = packagerate.packageComponentDescription.getText();
		packageRateLogger.info("packageComponentDescription : " + getpackageComponentDescription);
		Wait.wait3Second();
		String getpackageCalculationMethod = new Select(packagerate.packageCalculationMethod).getFirstSelectedOption()
				.getText();
		packageRateLogger.info(" Package Calculation Method " + getpackageCalculationMethod);
		Wait.wait3Second();
		packagerate.enterPackageAmount.sendKeys(PackageAmount);

		Wait.wait10Second();

	}

	public void package_descriptiveInformation(WebDriver driver, String rateDisplayName, String ratePolicy,
			String rateDescription) throws InterruptedException {
		Elements_Inventory packagerate = new Elements_Inventory(driver);
		/// Wait.explicit_wait_xpath(OR.rate_displayName);
		packagerate.rate_displayName.sendKeys(rateDisplayName);
		Wait.explicit_wait_xpath(OR.rate_policy, driver);
		packagerate.rate_policy.sendKeys(ratePolicy);
		Wait.explicit_wait_xpath(OR.rate_description, driver);
		packagerate.rate_description.sendKeys(rateDescription);
		Wait.wait15Second();
	}

	public void associateRate(WebDriver driver) throws InterruptedException {

		Elements_Inventory packagerate = new Elements_Inventory(driver);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		Wait.wait5Second();

		packageRateLogger.info(driver.getWindowHandles());

		packagerate.clickPackageAssociateRate.click();

		// Thread.sleep(5000);
		// new WebDriverWait(driver,
		// 120).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='MainContent_dgRatesList_chkSelected_0']")));
		// packageRateLogger.info("Waiting for 15 secs");
		// Wait.explicit_wait_xpath(OR.selectRateInPackage);

		Wait.wait5Second();
		driver.switchTo().frame("dialog-body0");

		packagerate.selectRateInPackage.click();

		Wait.wait5Second();
		// jse.executeScript("window.scrollBy(0,1000)");
		// Wait.explicit_wait_xpath(OR.rate_Save_Button);
		packagerate.clickPackageSelectButton.click();
		Wait.wait5Second();
		driver.switchTo().defaultContent();
		packagerate.rate_Save_Button.click();
		Wait.wait5Second();
		packagerate.rate_done_button.click();
		Wait.wait10Second();
		packageRateLogger.info(" Clicked on Done Button ");

	}

	public void delete_rate(WebDriver driver) throws InterruptedException

	{
		Elements_Inventory rate = new Elements_Inventory(driver);

		rate.click_goButton.click();
		Wait.wait5Second();
		rate.selectPRate.click();
		Wait.wait10Second();
		rate.deleteRate.click();
		Wait.wait10Second();
		packageRateLogger.info(" System sucessfully deleted the Rate ");
		Wait.wait10Second();
	}

	public void delete_rate(WebDriver driver, String Rate) throws InterruptedException

	{
		Elements_Inventory rate = new Elements_Inventory(driver);
		Utility.ScrollToElement(rate.click_goButton, driver);
		rate.click_goButton.click();
		String RatePath = "//a[contains(text(),'" + Rate + "')]/ ../preceding-sibling::td/span/input";
		WebElement Rate_Delete = driver.findElement(By.xpath(RatePath));
		Wait.explicit_wait_visibilityof_webelement(Rate_Delete, driver);
		Utility.ScrollToElement(Rate_Delete, driver);
		Rate_Delete.click();
		Utility.ScrollToElement(rate.deleteRate, driver);
		rate.deleteRate.click();
		packageRateLogger.info(" System sucessfully deleted the Rate ");
	}

	public ArrayList<String> clickOnPackageTab(WebDriver driver) throws InterruptedException {

		Elements_Inventory rate = new Elements_Inventory(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		rate.btnPackage.click();
		packageRateLogger.info("Navigated to Package");
		testSteps.add("Navigated to Package");

		Wait.WaitForElement(driver, OR.newRate);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRate), driver);

		return testSteps;

	}

	public ArrayList<String> packageDetails(WebDriver driver, String packageName, String RatePlan)
			throws InterruptedException {

		Elements_Inventory packagerate = new Elements_Inventory(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.newRate);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newRate), driver);
		packagerate.newRate.click();
		test_steps.add("Clicked New Rate");
		packageRateLogger.info("Clicked New Rate");

		Wait.WaitForElement(driver, OR.rateName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.rateName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.rateName), driver);

		packagerate.rateName.sendKeys(packageName);
		test_steps.add("Entered Package name : " + packageName);
		packageRateLogger.info("Entered Package name : " + packageName);

		new Select(packagerate.ratePlan).selectByVisibleText(RatePlan);
		String packageplan = new Select(packagerate.ratePlan).getFirstSelectedOption().getText();

		packageRateLogger.info("Selected Rate Plan : " + packageplan);
		test_steps.add("Selected Rate Plan : " + packageplan);
		packageRateLogger.info("Selected Rate Plan : " + packageplan);

		// packageRateLogger.info( "Selected Rate Plan : " +derivedplan);
		packagerate.selectPackageRatePlan.click();
		String rateType = packagerate.getRateType3.getText();
		packageRateLogger.info(" Selected Rate Type : " + rateType);
		test_steps.add(" Selected Rate Type : " + rateType);

		return test_steps;
	}

	public ArrayList<String> packageComponentWithAddOn(WebDriver driver, String PackageCategory, String PackageAmount)
			throws InterruptedException {

		Elements_Inventory packagerate = new Elements_Inventory(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		packagerate.selectAddOn.click();
		String packageComponents = packagerate.selectAddOnLabel.getText();
		packageRateLogger.info("packageComponents : " + packageComponents);
		testSteps.add("packageComponents : " + packageComponents);

		packagerate.clickPackageAddButton.click();
		Wait.explicit_wait_visibilityof_webelement(packagerate.selectPackageCategory, driver);

		Select dropdown = new Select(packagerate.selectPackageCategory);
		java.util.List<WebElement> options = dropdown.getOptions();
		int itemSize = options.size();
		packageRateLogger.info(" No of Package rates : " + itemSize);

		new Select(packagerate.selectPackageCategory).selectByVisibleText(PackageCategory);
		packageRateLogger.info("Selected Category :  " + PackageCategory);
		testSteps.add("elected Category:  " + PackageCategory);

		String getpackageComponentDescription = packagerate.packageComponentDescription.getText();
		packageRateLogger.info("packageComponentDescription : " + getpackageComponentDescription);

		String getpackageCalculationMethod = new Select(packagerate.packageCalculationMethod).getFirstSelectedOption()
				.getText();
		packageRateLogger.info(" Package Calculation Method " + getpackageCalculationMethod);
		testSteps.add(" Package Calculation Method " + getpackageCalculationMethod);

		packagerate.enterPackageAmount.sendKeys(PackageAmount);
		packageRateLogger.info(" Entered Package Amount :  " + PackageAmount);
		testSteps.add(" Entered Package Amount :  " + PackageAmount);

		return testSteps;
	}

	public ArrayList<String> packageDescriptiveInformation(WebDriver driver, String rateDisplayName, String ratePolicy,
			String rateDescription) throws InterruptedException {
		Elements_Inventory packagerate = new Elements_Inventory(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		packagerate.rate_displayName.sendKeys(rateDisplayName);
		test_steps.add("Entered Display Name : " + rateDisplayName);
		packageRateLogger.info("Entered Display Name : " + rateDisplayName);
		Wait.WaitForElement(driver, OR.enterPolicyName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.enterPolicyName), driver);
		packagerate.rate_policy.sendKeys(ratePolicy);
		test_steps.add("Entered Policy : " + ratePolicy);
		packageRateLogger.info("Entered Policy : " + ratePolicy);

		packagerate.rateDescription.sendKeys(rateDescription);
		test_steps.add("Entered Description : " + rateDescription);
		packageRateLogger.info("Entered Description : " + rateDescription);
		return test_steps;
	}

	public ArrayList<String> clickOnAlwaysAvailableRate(WebDriver driver) throws InterruptedException {
		Elements_Inventory packagerate = new Elements_Inventory(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.alwaysAvailableRate);
		Wait.waitForElementToBeVisibile(By.xpath(OR.alwaysAvailableRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.alwaysAvailableRate), driver);
		Utility.ScrollToElement_NoWait(packagerate.alwaysAvailableRate, driver);
		Wait.explicit_wait_elementToBeClickable(packagerate.alwaysAvailableRate, driver);
		if (packagerate.alwaysAvailableRate.isSelected()) {
			test_steps.add("Associate Rates Always Available CheckBox already Selected");
			packageRateLogger.info("Associate Rates Always Available CheckBox already Selected");
		} else {
			packagerate.alwaysAvailableRate.click();
			test_steps.add("Associate Rates Always Available CheckBox Selected");
			packageRateLogger.info("Associate Rates Always Available CheckBox Selected");

		}
		return test_steps;
	}

	public ArrayList<String> SavePackageRate(WebDriver driver) throws InterruptedException {

		Elements_Inventory packagerate = new Elements_Inventory(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		packagerate.rate_Save_Button.click();
		packageRateLogger.info(" Clicked on Save Button ");
		test_steps.add(" Clicked on Save Button ");

		Wait.WaitForElement(driver, OR.rate_done_button);
		Wait.waitForElementToBeVisibile(By.xpath(OR.rate_done_button), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.rate_done_button), driver);
		packagerate.rate_done_button.click();
		packageRateLogger.info(" Clicked on Done Button ");
		test_steps.add(" Clicked on Done Button ");

		return test_steps;
	}

	public void package_details(WebDriver driver, String packageName, ArrayList<String> test_steps)
			throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Elements_Inventory packagerate = new Elements_Inventory(driver);
		Wait.explicit_wait_xpath(OR.newRate, driver);
		packagerate.newRate.click();
		Wait.explicit_wait_xpath(OR.rateName, driver);
		packagerate.rateName.sendKeys(packageName);
		String packageplan = new Select(packagerate.ratePlan).getFirstSelectedOption().getText();

		packageRateLogger.info("Selected Rate Plan : " + packageplan);

		// packageRateLogger.info( "Selected Rate Plan : " +derivedplan);
		packagerate.selectPackageRatePlan.click();
		String rateType = packagerate.getRateType3.getText();
		packageRateLogger.info(" Selected Rate Type : " + rateType);
		test_steps.add("Selected Rate is: " + "<b>" + rateType + "</>");
	}

	public void selectRatePlan(WebDriver driver, String rateplanName) {
		Elements_Inventory packagerate = new Elements_Inventory(driver);
		Wait.explicit_wait_10sec(packagerate.ratePlan, driver);
		new Select(packagerate.ratePlan).selectByVisibleText(rateplanName);
	}

	public void package_components(WebDriver driver, String PackageAmount, String PackageCompDescription,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Inventory packagerate = new Elements_Inventory(driver);
		String packageComponents = packagerate.getPackageComponents.getText();
		packageRateLogger.info("packageComponents : " + packageComponents);

		packagerate.clickPackageAddButton.click();
		test_steps.add("click Package Add Button");

		Select dropdown = new Select(packagerate.selectPackageCategory);
		java.util.List<WebElement> options = dropdown.getOptions();
		int itemSize = options.size();
		packageRateLogger.info(" No of Package rates : " + itemSize);
		Wait.wait3Second();
		/*
		 * for(int i = 0; i < itemSize ; i++) { String optionsValue =
		 * options.get(i).getText(); packageRateLogger.info("Category : "
		 * +optionsValue);
		 */

		// }

		new Select(packagerate.selectPackageCategory).selectByIndex(5);
		Wait.wait3Second();
		String getpackageComponentDescription = packagerate.packageComponentDescription.getText();
		packageRateLogger.info("packageComponentDescription : " + getpackageComponentDescription);
		test_steps.add("Package Component Description: " + "<b>" + getpackageComponentDescription + "</b>");
		Wait.wait3Second();
		String getpackageCalculationMethod = new Select(packagerate.packageCalculationMethod).getFirstSelectedOption()
				.getText();
		packageRateLogger.info(" Package Calculation Method " + getpackageCalculationMethod);
		test_steps.add(" Package Calculation Method: " + "<b>" + getpackageCalculationMethod + "</b>");
		Wait.wait3Second();
		packagerate.enterPackageAmount.sendKeys(PackageAmount);
		test_steps.add("Package Component Amount: " + "<b>" + PackageAmount + "</b>");

		Wait.wait10Second();

	}

	public void assoCiateRate(WebDriver driver, String rateName, ArrayList<String> test_steps, String roomClassName)
			throws InterruptedException {

		Elements_Inventory packagerate = new Elements_Inventory(driver);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		Wait.wait5Second();

		packageRateLogger.info(driver.getWindowHandles());

		packagerate.clickPackageAssociateRate.click();
		test_steps.add("Click at associate rate: " + "<b>" + rateName + "</b>");

		Wait.wait5Second();
		driver.switchTo().frame("dialog-body0");
		// select[@id='MainContent_drpRoomClassList']
		// packagerate.selectRateInPackage.click();
		Wait.waitUntilPresenceOfElementLocated(OR_Reservation.SelectRoomClassInSearch, driver);
		new Select(packagerate.selectRoomClassInSearch).selectByVisibleText(roomClassName);
		packagerate.clickSearchGO.click();

		String xpath = "//a[text()='" + rateName + "']/ancestor::tr/td/span/input[@type='checkbox']";
		System.out.println(xpath);
		Wait.waitUntilPresenceOfElementLocated(xpath, driver);
		Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(xpath)), driver);
		driver.findElement(By.xpath(xpath)).click();
		test_steps.add("select rate checkbox");

		Wait.wait5Second();
		// jse.executeScript("window.scrollBy(0,1000)");
		// Wait.explicit_wait_xpath(OR.rate_Save_Button);
		packagerate.clickPackageSelectButton.click();
		test_steps.add("Click at select button");
		Wait.wait5Second();
		driver.switchTo().defaultContent();
		packagerate.rate_Save_Button.click();
		test_steps.add("click at save button");
		Wait.wait5Second();
		packagerate.rate_done_button.click();
		test_steps.add("click Done");
		Wait.wait10Second();
		packageRateLogger.info(" Clicked on Done Button ");

	}

	public void clickPackage(WebDriver driver) throws InterruptedException {
		Elements_Inventory rate = new Elements_Inventory(driver);
		Utility.ScrollToElement(rate.click_goButton, driver);
		rate.clickPackageTab.click();

	}

	// Verify OneSingle Product
	public ArrayList<String> verifyProductInPackageRatePlan(WebDriver driver, String productName) {
		ArrayList<String> testSteps = new ArrayList<String>();
		String product = "//span[text()='" + productName + "']";
		WebElement productElement = driver.findElement(By.xpath(product));
		Wait.WaitForElement(driver, product);
		Wait.waitForElementToBeVisibile(By.xpath(product), driver);
		Wait.waitForElementToBeClickable(By.xpath(product), driver);

		assertEquals(productElement.isDisplayed(), true, "Failed:  Product  didn't display in Package Rate Plan List");
		packageRateLogger.info("Product  displayed in Package Rate Plan List");
		testSteps.add("Product displayed in Package Rate Plan List");
		return testSteps;
	}
	// Verify Multiple Products

	public ArrayList<String> verifyProductInPackageRatePlan(WebDriver driver, ArrayList<String> productName) {
		ArrayList<String> testSteps = new ArrayList<String>();
		String product = "";
		WebElement productElement;
		int size = productName.size();
		for (int i = 0; i < size; i++) {
			product = "//span[text()='" + productName.get(i) + "']";
			productElement = driver.findElement(By.xpath(product));
			Wait.WaitForElement(driver, product);
			Wait.waitForElementToBeVisibile(By.xpath(product), driver);
			Wait.waitForElementToBeClickable(By.xpath(product), driver);

			assertEquals(productElement.isDisplayed(), true,
					"Failed:  Product  didn't display in Package Rate Plan List");
			packageRateLogger.info("Product  displayed in Package Rate Plan List");
			testSteps.add("Product displayed in Package Rate Plan List");
		}
		return testSteps;
	}
	// Add OneSingle Product

	public ArrayList<String> addProductInPackageRatePlan(WebDriver driver, ArrayList<String> productName) {
		ArrayList<String> testSteps = new ArrayList<String>();
		int size = productName.size();
		String addProductButton = "";
		WebElement addProductButtonElement;
		for (int i = 0; i < size; i++) {
			addProductButton = "//span[text()='" + productName.get(i) + "']/preceding-sibling::span//i";
			addProductButtonElement = driver.findElement(By.xpath(addProductButton));
			Wait.WaitForElement(driver, addProductButton);
			Wait.waitForElementToBeVisibile(By.xpath(addProductButton), driver);
			Wait.waitForElementToBeClickable(By.xpath(addProductButton), driver);
			addProductButtonElement.click();
			packageRateLogger.info("Product Added:" + productName.get(i));
			testSteps.add("Product Added:" + productName.get(i));
		}
		return testSteps;
	}

	// Add Multiple Product
	public ArrayList<String> addProductInPackageRatePlan(WebDriver driver, String productName) {
		ArrayList<String> testSteps = new ArrayList<String>();
		String addProductButton = "//span[text()='" + productName + "']/preceding-sibling::span//i";
		WebElement addProductButtonElement = driver.findElement(By.xpath(addProductButton));
		Wait.WaitForElement(driver, addProductButton);
		Wait.waitForElementToBeVisibile(By.xpath(addProductButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(addProductButton), driver);
		addProductButtonElement.click();
		packageRateLogger.info("Add Product Button Clicked");
		testSteps.add("Add Product Button Clicked");
		return testSteps;
	}

	public ArrayList<String> verifyAddedProductDetailsInPackageRatePlan(WebDriver driver, ArrayList<String> productName,
			ArrayList<String> productPrice, ArrayList<String> costPerFirstSelection,
			ArrayList<String> costPerSecondSelection) {
		ArrayList<String> testSteps = new ArrayList<String>();
		System.out.println("First:" + costPerFirstSelection);

		int size = productName.size();
		String product = "";
		String productCost = "";
		String perSelectionFisrt = "";
		String perSelectionSecond = "";
		WebElement productElement;
		WebElement productCostElement;
		WebElement perSelectionFisrtElement;
		WebElement perSelectionSecondElement;
		for (int i = 0; i < size; i++) {
			product = "//span[text()='" + productName.get(i) + "']";
			productCost = "//span[text()='" + productName.get(i) + "']/following-sibling::span//input";
			perSelectionFisrt = "(//span[text()='" + productName.get(i)
					+ "']/following-sibling::div[@class='package-product-data'])[1]//div[@title='"
					+ costPerFirstSelection.get(i) + "']";
			perSelectionSecond = "(//span[text()='" + productName.get(i)
					+ "']/following-sibling::div[@class='package-product-data'])[2]//div[@title='"
					+ costPerSecondSelection.get(i) + "']";

			productElement = driver.findElement(By.xpath(product));
			productCostElement = driver.findElement(By.xpath(productCost));
			perSelectionFisrtElement = driver.findElement(By.xpath(perSelectionFisrt));
			perSelectionSecondElement = driver.findElement(By.xpath(perSelectionSecond));

			Wait.waitForElementToBeVisibile(By.xpath(productCost), driver);
			Wait.waitForElementToBeClickable(By.xpath(productCost), driver);

			assertEquals(productElement.getText(), productName.get(i), "Failed:  Product  Name didn't verify");
			packageRateLogger.info("Product  Name:" + productName.get(i) + " Verified");
			testSteps.add("Product  Name:" + productName.get(i) + " Verified");

			assertEquals(productCostElement.getAttribute("value"), productPrice.get(i),
					"Failed:  Product  Cost didn't verify");
			packageRateLogger.info("Product  Cost:" + productPrice.get(i) + " Verified");
			testSteps.add("Product  Cost:" + productPrice.get(i) + " Verified");

			assertEquals(perSelectionFisrtElement.getText(), costPerFirstSelection.get(i),
					"Failed:  Product Cost Per First Selection didn't verify");
			packageRateLogger.info("Product Cost Per First Selection :" + costPerFirstSelection.get(i) + " Verified");
			testSteps.add("Product Cost Per First Selection :" + costPerFirstSelection.get(i) + " Verified");

			assertEquals(perSelectionSecondElement.getText(), costPerSecondSelection.get(i),
					"Failed:  Product Cost Per Second Selection  didn't verify");
			packageRateLogger.info("Product Cost Per Second Selection :" + costPerSecondSelection.get(i) + " Verified");
			testSteps.add("Product Cost Per Second Selection :" + costPerSecondSelection.get(i) + " Verified");

		}
		return testSteps;
	}

	public ArrayList<String> verifyToolTipAndAddedProductDetails(WebDriver driver, int productsAdded) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		Actions action = new Actions(driver);

		assertEquals(packageRate.toolTip.isDisplayed(), true, "Failed:  Tool Tip didn't display");
		packageRateLogger.info("Tool Tip Icon Verified");
		testSteps.add("Tool Tip Icon Verified");

		assertEquals(packageRate.addedProductDetail.getText(), (productsAdded + " products"),
				"Failed:  Added product Count didn't verify");
		packageRateLogger.info("Added Procduct Count:" + productsAdded + " Verified");
		testSteps.add("Added Procduct Count:" + productsAdded + " Verified");

		action.moveToElement(packageRate.toolTip).perform();

		packageRateLogger.info("Add Product Button Clicked");
		testSteps.add("Add Product Button Clicked");

		// System.out.println("Tooltip text:" + getToolTipValue(driver));
		return testSteps;
	}

	public String getToolTipValue(WebDriver driver) {
		String path = "//div[@class='ant-tooltip info-popover ant-tooltip-placement-top']//div[@class='modal-popover-content']";
		return driver.findElement(By.xpath(path)).getText();
	}

	public ArrayList<String> verifyAddedProductDetailsInPackageRatePlan(WebDriver driver, String productName,
			String productPrice, String costPerFirstSelection, String costPerSecondSelection) {
		ArrayList<String> testSteps = new ArrayList<String>();
		String product = "//span[text()='" + productName + "']";
		String productCost = "//span[text()='" + productName + "']/following-sibling::span//input";
		String perSelectionFisrt = "(//span[text()='" + productName
				+ "']/following-sibling::div[@class='package-product-data'])[1]//div[@title='" + costPerFirstSelection
				+ "']";
		String perSelectionSecond = "(//span[text()='" + productName
				+ "']/following-sibling::div[@class='package-product-data'])[2]//div[@title='" + costPerSecondSelection
				+ "']";

		WebElement productElement = driver.findElement(By.xpath(product));
		WebElement productCostElement = driver.findElement(By.xpath(productCost));
		WebElement perSelectionFisrtElement = driver.findElement(By.xpath(perSelectionFisrt));
		WebElement perSelectionSecondElement = driver.findElement(By.xpath(perSelectionSecond));

		Wait.waitForElementToBeVisibile(By.xpath(productCost), driver);
		Wait.waitForElementToBeClickable(By.xpath(productCost), driver);

		assertEquals(productElement.getText(), productName, "Failed:  Product  Name didn't verify");
		packageRateLogger.info("Product  Name:" + productName + " Verified");
		testSteps.add("Product  Name:" + productName + " Verified");

		assertEquals(productCostElement.getAttribute("value"), productPrice, "Failed:  Product  Cost didn't verify");
		packageRateLogger.info("Product  Cost:" + productPrice + " Verified");
		testSteps.add("Product  Cost:" + productPrice + " Verified");

		assertEquals(perSelectionFisrtElement.getText(), costPerFirstSelection,
				"Failed:  Product Cost Per First Selection didn't verify");
		packageRateLogger.info("Product Cost Per First Selection :" + costPerFirstSelection + " Verified");
		testSteps.add("Product Cost Per First Selection :" + costPerFirstSelection + " Verified");

		assertEquals(perSelectionSecondElement.getText(), costPerSecondSelection,
				"Failed:  Product Cost Per Second Selection  didn't verify");
		packageRateLogger.info("Product Cost Per Second Selection :" + costPerSecondSelection + " Verified");
		testSteps.add("Product Cost Per Second Selection :" + costPerSecondSelection + " Verified");
		return testSteps;
	}

	public ArrayList<String> closeUnSavedPackagePlan(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.CLOSE_PACKAGE_RATE_PLAN_BUTTON), driver);
		Utility.ScrollToElement(packageRate.closePackageButton, driver);
		packageRate.closePackageButton.click();
		packageRateLogger.info("Close Package RatePlan Button Clicked");
		testSteps.add("Close Package RatePlan Button Clicked");
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.CLOSE_PACKAGE_RATE_PLAN_UNSAVED_DATA_STATEMENT),
				driver);

		assertEquals(packageRate.unsavedDataStatement.getText(), "You have unsaved data",
				"Failed:  Unsaved Data Statement didn't displayd");
		packageRateLogger.info("Unsaved Data Statement Verified");
		testSteps.add("Unsaved Data Statement Verified");
		assertEquals(packageRate.areYouSureStatement.getText(),
				"Are you sure you want to close the Tab? All the unsaved data will be lost.",
				"Failed:  Are You Sure Data Statement didn't displayd");
		packageRateLogger.info("Are You Sure Data Statement Verified");
		testSteps.add("Are You Sure Data Statement Verified");

		packageRate.yesButtonClosePackage.click();
		packageRateLogger.info("Close Package RatePlan Yes Button Clicked");
		testSteps.add("Close Package RatePlan Yes Button Clicked");
		return testSteps;
	}

/*
 * ##########################################################################################################################################################################
 * 
 * ' Method Name: <selectParentRatePlan> 
 * ' Description: <This method will select rate type in package rate> 
 * ' Input parameters: <WebDriver driver,   String parentPlan> 
 * ' Return value: <ArrayList>
 * ' Created By: <Farhan Ghaffar>
 * ' Created On:  <08/19/2020>
 * 
 * ##########################################################################################################################################################################
 */
public ArrayList<String> selectParentRatePlan(WebDriver driver, String parentPlan) throws InterruptedException {
	ArrayList<String> testSteps = new ArrayList<>();
	String path = "//span[text()='"+parentPlan+"']/preceding-sibling::span//input";
	Wait.WaitForElement(driver, path);
	WebElement selectPlan = driver.findElement(By.xpath(path));
	Utility.ScrollToElement_NoWait(selectPlan, driver);
	if(selectPlan.isSelected()){
		testSteps.add("Rate type ("+ parentPlan +") already selected");
		packageRateLogger.info("Rate type ("+ parentPlan +") already selected");
		
	}else{
		selectPlan.click();
		testSteps.add("Rate type ("+ parentPlan +") selected");
		packageRateLogger.info("Rate type ("+ parentPlan +") selected");
	}
	
	if(parentPlan.equalsIgnoreCase("Interval rates")){
		
	}
	
	return testSteps;

}

/*
 * ##########################################################################################################################################################################
 * 
 * ' Method Name: <addProductInPackageRatePlan> 
 * ' Description: <This method will select product and enter product amount, sleet per adult, per interval in package rate> 
 * ' Input parameters: <WebDriver driver, String amount, String productName, String perAdult, String perInterval> 
 * ' Return value: <ArrayList>
 * ' Created By: <Farhan Ghaffar>
 * ' Created On:  <08/19/2020>
 * 
 * ##########################################################################################################################################################################
 */
	public ArrayList<String> addProductInPackageRatePlan(WebDriver driver, String amount, String productName, String perAdult, String perInterval) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		DerivedRate derivedRate = new DerivedRate();
		
		String addProductButton = "//span[text()='" + productName + "']/preceding-sibling::span//i";
		WebElement addProductButtonElement = driver.findElement(By.xpath(addProductButton));
		Wait.WaitForElement(driver, addProductButton);
		Wait.waitForElementToBeVisibile(By.xpath(addProductButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(addProductButton), driver);
		Utility.ScrollToElement_NoWait(addProductButtonElement, driver);
		addProductButtonElement.click();
		packageRateLogger.info("Add Product ("+ productName +") Button Clicked");
		testSteps.add("Add Product ("+ productName +") Button Clicked");
		
		String rateInputPath = "//span[text()='"+ productName +"']//following-sibling::span//input";
		Wait.WaitForElement(driver, rateInputPath);
		Wait.waitForElementToBeVisibile(By.xpath(rateInputPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(rateInputPath), driver);
		WebElement rateInputPathElement = driver.findElement(By.xpath(rateInputPath));
		Utility.ScrollToElement_NoWait(rateInputPathElement, driver);
		rateInputPathElement.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		rateInputPathElement.sendKeys(amount);
		packageRateLogger.info("Entered amount : "  + amount);
		testSteps.add("Entered amount : "  + amount);
		
		
		String firstDropdownOptionText = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][1]//div[@class='ant-select-selection-selected-value']";
		Wait.WaitForElement(driver, firstDropdownOptionText);
		Wait.waitForElementToBeVisibile(By.xpath(firstDropdownOptionText), driver);
		Wait.waitForElementToBeClickable(By.xpath(firstDropdownOptionText), driver);
		WebElement firstDropdownOptionTextElement = driver.findElement(By.xpath(firstDropdownOptionText));
		Utility.ScrollToElement_NoWait(firstDropdownOptionTextElement, driver);
		String getText = firstDropdownOptionTextElement.getText();
		if(perAdult.equalsIgnoreCase(getText)){
			packageRateLogger.info(perAdult + " option is already selected");
			testSteps.add(perAdult + " option is already selected");
				
		}else{
			
			String selectDropdown = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][1]//span[@class='ant-select-arrow']/i";
			WebElement selectDropdownElement = driver.findElement(By.xpath(selectDropdown));
			Wait.WaitForElement(driver, selectDropdown);
			Wait.waitForElementToBeVisibile(By.xpath(selectDropdown), driver);
			Wait.waitForElementToBeClickable(By.xpath(selectDropdown), driver);
			Utility.ScrollToElement_NoWait(selectDropdownElement, driver);
			selectDropdownElement.click();
			testSteps.add("Expand DropDown");
			packageRateLogger.info("Expand DropDown");
			derivedRate.selectDropDownOptions(driver, perAdult, testSteps);
		}

		String secondDropdownOptionText = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][2]//div[@class='ant-select-selection-selected-value']";
		Wait.WaitForElement(driver, secondDropdownOptionText);
		Wait.waitForElementToBeVisibile(By.xpath(secondDropdownOptionText), driver);
		Wait.waitForElementToBeClickable(By.xpath(secondDropdownOptionText), driver);
		WebElement secondDropdownOptionTextElement = driver.findElement(By.xpath(secondDropdownOptionText));
		Utility.ScrollToElement_NoWait(secondDropdownOptionTextElement, driver);
		getText = secondDropdownOptionTextElement.getText();
		if(perInterval.equalsIgnoreCase(getText)){
			packageRateLogger.info(perInterval + " option is already selected");
			testSteps.add(perInterval + " option is already selected");
				
		}else{
				
	
			String selectSecondDropdown = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][2]//span[@class='ant-select-arrow']/i";
			WebElement selectSecondDropdownElement = driver.findElement(By.xpath(selectSecondDropdown));
			Wait.WaitForElement(driver, selectSecondDropdown);
			Wait.waitForElementToBeVisibile(By.xpath(selectSecondDropdown), driver);
			Wait.waitForElementToBeClickable(By.xpath(selectSecondDropdown), driver);
			Utility.ScrollToElement_NoWait(selectSecondDropdownElement, driver);
			selectSecondDropdownElement.click();
			
			//derivedRate.expandCurrencyValueDropdown(driver, 1);
			testSteps.add("Expand DropDown");
			packageRateLogger.info("Expand DropDown");
			derivedRate.selectDropDownOptions(driver, perInterval, testSteps);
		}
		
		return testSteps;
	}
	
	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <addProductsInPackageRatePlan> 
	 * ' Description: <This method will select products and enter product amount, sleet per adult, per interval in package rate> 
	 * ' Input parameters: <WebDriver driver, String amount, String productName, String perAdult, String perInterval> 
	 * ' Return value: <ArrayList>
	 * ' Created By: <Adhnan Ghaffar>
	 * ' Created On:  <09/03/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */
		public ArrayList<String> addProductsInPackageRatePlan(WebDriver driver, String packageRatePlanProductAmount, String packageratePlanProduct, String packageRatePlanPerRateIncrementType1, String packageRatePlanPerRateIncrementType2) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<String>();
			ArrayList<String> getTestSteps = new ArrayList<String>();
			for (String prod : packageratePlanProduct.split(Utility.DELIM)) {
				getTestSteps.clear();
				getTestSteps = addProductInPackageRatePlan(driver, packageRatePlanProductAmount, prod,
						packageRatePlanPerRateIncrementType1, packageRatePlanPerRateIncrementType2);
				testSteps.addAll(getTestSteps);
			}
			return testSteps;
		}
		

	public ArrayList<String> verifyProductPage(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
			Elements_PackageRate packageRate = new Elements_PackageRate(driver);
			Wait.WaitForElement(driver, OR_PackageRatePlan.ProductPageText);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductPageText),
					driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductPageText), driver);
			Utility.ScrollToElement_NoWait(packageRate.ProductPageText, driver);
			assertTrue(packageRate.ProductPageText.isDisplayed(), "Failed : product page is nto displaying");
			
			packageRateLogger.info("Verify that Products tab is displayed in between rate type and channel");
			testSteps.add("Verify that Products tab is displayed in between rate type and channel");
		return testSteps;
	}
	
	public ArrayList<String> searchProduct(WebDriver driver, String productName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
			String[] productArray =productName.split(Utility.DELIM);
			for(String prod : productArray) {
				Elements_PackageRate packageRate = new Elements_PackageRate(driver);
				Wait.WaitForElement(driver, OR_PackageRatePlan.SearchProductsInPackageRate);
				Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.SearchProductsInPackageRate),
						driver);
				Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.SearchProductsInPackageRate), driver);
				Utility.ScrollToElement_NoWait(packageRate.SearchProductsInPackageRate, driver);
				packageRate.SearchProductsInPackageRate.clear();
				packageRate.SearchProductsInPackageRate.sendKeys(Keys.chord(Keys.CONTROL, "a"), prod);
				packageRateLogger.info("Entered product in search box : " + prod);
				testSteps.add("Entered product in search box : " + prod);

				
			}
		return testSteps;
	}

	public ArrayList<String> clickProduct(WebDriver driver, String productName, boolean isAdd) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
			String addProductButton = "//span[text()='" + productName + "']/preceding-sibling::span//i";
			WebElement addProductButtonElement = driver.findElement(By.xpath(addProductButton));
			Wait.WaitForElement(driver, addProductButton);
			Wait.waitForElementToBeVisibile(By.xpath(addProductButton), driver);
			Wait.waitForElementToBeClickable(By.xpath(addProductButton), driver);
			Utility.ScrollToElement_NoWait(addProductButtonElement, driver);
			addProductButtonElement.click();
			if(isAdd) {
				packageRateLogger.info("Add Product ("+ productName +") Button Clicked");
				testSteps.add("Add Product ("+ productName +") Button Clicked");
				String rateInputPath = "//span[text()='"+ productName +"']//following-sibling::span//input";
				Wait.WaitForElement(driver, rateInputPath);
				Wait.waitForElementToBeVisibile(By.xpath(rateInputPath), driver);
			}else {
				packageRateLogger.info("Remove Product ("+ productName +") Button Clicked");
				testSteps.add("Remove Product ("+ productName +") Button Clicked");	
			}
		
		return testSteps;
	}
	

	public ArrayList<String> addProductAmount(WebDriver driver, String amount, String productName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		
			String[] amountArray = amount.split(Utility.DELIM);
			for(String productAmount : amountArray) {
			packageRateLogger.info("Product : " + productName  +productAmount);

			String rateInputPath = "//span[text()='"+ productName +"']//following-sibling::span//input";
			Wait.WaitForElement(driver, rateInputPath);
			Wait.waitForElementToBeVisibile(By.xpath(rateInputPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(rateInputPath), driver);
			WebElement rateInputPathElement = driver.findElement(By.xpath(rateInputPath));
			Utility.ScrollToElement_NoWait(rateInputPathElement, driver);
					
			rateInputPathElement.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			rateInputPathElement.sendKeys(productAmount);
			packageRateLogger.info("Entered amount : "  + productAmount);
			testSteps.add("Entered amount : "  + productAmount);
			}
		return testSteps;
	}


	public String getProductAmount(WebDriver driver, String productName) throws InterruptedException {
		String rateInputPath = "//span[text()='"+ productName +"']//following-sibling::span//input";
		Wait.WaitForElement(driver, rateInputPath);
		Wait.waitForElementToBeVisibile(By.xpath(rateInputPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(rateInputPath), driver);
		WebElement rateInputPathElement = driver.findElement(By.xpath(rateInputPath));
		Utility.ScrollToElement_NoWait(rateInputPathElement, driver);
		String amount = rateInputPathElement.getAttribute("value");
		
		packageRateLogger.info("Get amount : "  + amount);
		
		return amount.trim();
	}
	
	public ArrayList<String> selectCalculationMethod(WebDriver driver, String productName, String perAdult, String perInterval) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		DerivedRate derivedRate = new DerivedRate();

			String[] perAdultArray = perAdult.split(Utility.DELIM);
			for(int i=0; i < perAdultArray.length; i++) {
		
				String firstDropdownOptionText = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][1]//div[@class='ant-select-selection-selected-value']";
				Wait.WaitForElement(driver, firstDropdownOptionText);
				Wait.waitForElementToBeVisibile(By.xpath(firstDropdownOptionText), driver);
				Wait.waitForElementToBeClickable(By.xpath(firstDropdownOptionText), driver);
				WebElement firstDropdownOptionTextElement = driver.findElement(By.xpath(firstDropdownOptionText));
				Utility.ScrollToElement_NoWait(firstDropdownOptionTextElement, driver);
				String getText = firstDropdownOptionTextElement.getText();
				if(perAdultArray[i].equalsIgnoreCase(getText)){
					packageRateLogger.info(perAdultArray[i] + " option is already selected");
					testSteps.add(perAdultArray[i] + " option is already selected");
						
				}else{
					
					String selectDropdown = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][1]//span[@class='ant-select-arrow']/i";
					WebElement selectDropdownElement = driver.findElement(By.xpath(selectDropdown));
					Wait.WaitForElement(driver, selectDropdown);
					Wait.waitForElementToBeVisibile(By.xpath(selectDropdown), driver);
					Wait.waitForElementToBeClickable(By.xpath(selectDropdown), driver);
					Utility.ScrollToElement_NoWait(selectDropdownElement, driver);
					selectDropdownElement.click();
					testSteps.add("Expand DropDown");
					packageRateLogger.info("Expand DropDown");
					derivedRate.selectDropDownOptions(driver, productName, perAdultArray[i], i+1, testSteps);
				}
			}
			
			String[] perIntervalArray = perInterval.split(Utility.DELIM);
			for(int i=0; i<  perIntervalArray.length; i++) {
		
				String secondDropdownOptionText = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][2]//div[@class='ant-select-selection-selected-value']";
				Wait.WaitForElement(driver, secondDropdownOptionText);
				Wait.waitForElementToBeVisibile(By.xpath(secondDropdownOptionText), driver);
				Wait.waitForElementToBeClickable(By.xpath(secondDropdownOptionText), driver);
				WebElement secondDropdownOptionTextElement = driver.findElement(By.xpath(secondDropdownOptionText));
				Utility.ScrollToElement_NoWait(secondDropdownOptionTextElement, driver);
				String getText = secondDropdownOptionTextElement.getText();
				if(perIntervalArray[i].equalsIgnoreCase(getText)){
					packageRateLogger.info(perIntervalArray[i] + " option is already selected");
					testSteps.add(perIntervalArray[i] + " option is already selected");
						
				}else{
						
			
					String selectSecondDropdown = "//span[text()='"+ productName +"']//following-sibling::div[@class='package-product-data'][2]//span[@class='ant-select-arrow']/i";
					WebElement selectSecondDropdownElement = driver.findElement(By.xpath(selectSecondDropdown));
					Wait.WaitForElement(driver, selectSecondDropdown);
					Wait.waitForElementToBeVisibile(By.xpath(selectSecondDropdown), driver);
					Wait.waitForElementToBeClickable(By.xpath(selectSecondDropdown), driver);
					Utility.ScrollToElement_NoWait(selectSecondDropdownElement, driver);
					selectSecondDropdownElement.click();
					
					//derivedRate.expandCurrencyValueDropdown(driver, 1);
					testSteps.add("Expand DropDown");
					packageRateLogger.info("Expand DropDown");
					derivedRate.selectDropDownOptions(driver, productName, perIntervalArray[i], i+1, testSteps);
				}
			}
		return testSteps;
	}

	public ArrayList<String> addProducts(WebDriver driver, String productName, String amount, String perAdult, String perInterval) throws InterruptedException {
		
		ArrayList<String> testSteps = new ArrayList<String>();
		packageRateLogger.info(" product : " + productName);
		String[] prodArray  = productName.split( Utility.DELIM);
		String[] amountArr = amount.split( Utility.DELIM);
		String[] adultArr = perAdult.split(Utility.DELIM);
		String[] intervalArr = perInterval.split( Utility.DELIM);
		
		for(int i=0; i <prodArray.length; i++) {
		packageRateLogger.info(" product : " + productName + " : " + prodArray[i] + " : " + amountArr[i] +adultArr[i] + intervalArr[i]);
		
		testSteps.addAll(clickProduct(driver, prodArray[i], true));
		testSteps.addAll(addProductAmount(driver, amountArr[i], prodArray[i]));
		testSteps.addAll(selectCalculationMethod(driver, prodArray[i], adultArr[i], intervalArr[i]));
		
	}
		
		return testSteps;
	}
	
	public HashMap<String, String> getAllProductsPrice(WebDriver driver) throws InterruptedException {
		HashMap<String, String> map = new HashMap<>();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductsName);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductsName),
				driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductsName), driver);
		for(int i=0; i < packageRate.ProductsName.size(); i++) {
			String productName = packageRate.ProductsName.get(i).getText();
			productName = productName.trim();
			packageRateLogger.info("productName :  "+ productName);
			
			String DefaultProductPrice = "//td[text()='"+productName+"']//following-sibling::td[4]";
			Wait.WaitForElement(driver, DefaultProductPrice);
			Wait.waitForElementToBeVisibile(By.xpath(DefaultProductPrice),
					driver);
			Wait.waitForElementToBeClickable(By.xpath(DefaultProductPrice), driver);
			WebElement priceElement = driver.findElement(By.xpath(DefaultProductPrice));
			Utility.ScrollToElement_NoWait(priceElement, driver);
			String productPrice = priceElement.getText();	
			packageRateLogger.info("productPrice :  "+ productPrice);
			productPrice = Utility.removeDollarBracketsAndSpaces(productPrice);
			packageRateLogger.info("productPrice :  "+ productPrice);

			map.put(productName, productPrice);
		}		
		return map;
	}

	
	public HashMap<String, String> getAllProductsCalculationMethod(WebDriver driver) throws InterruptedException {
		HashMap<String, String> map = new HashMap<>();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductsName);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductsName),
				driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductsName), driver);
		for(int i=0; i < packageRate.ProductsName.size(); i++) {
			String productName = packageRate.ProductsName.get(i).getText();
			productName = productName.trim();
			packageRateLogger.info("productName :  "+ productName);
			
			String DefaultCalculationMethod = "//td[text()='"+productName+"']//following-sibling::td[3]";
			Wait.WaitForElement(driver, DefaultCalculationMethod);
			Wait.waitForElementToBeVisibile(By.xpath(DefaultCalculationMethod),
					driver);
			Wait.waitForElementToBeClickable(By.xpath(DefaultCalculationMethod), driver);
			WebElement calculationElement = driver.findElement(By.xpath(DefaultCalculationMethod));
			Utility.ScrollToElement_NoWait(calculationElement, driver);
			String calculationMethod = calculationElement.getText();	
			packageRateLogger.info("productPrice :  "+ calculationMethod);
			calculationMethod = calculationMethod.trim();
			packageRateLogger.info("productPrice :  "+ calculationMethod);

			map.put(productName, calculationMethod);
		}		
		return map;
	}

	public ArrayList<String> clickPackageTab(WebDriver driver, String tabName) throws InterruptedException {
			
			ArrayList<String> testSteps = new ArrayList<String>();
			String tabPath = "//div[text()='"+ tabName +"']";
			Wait.WaitForElement(driver, tabPath);
			Wait.waitForElementToBeVisibile(By.xpath(tabPath),
					driver);
			Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
			WebElement tabElement = driver.findElement(By.xpath(tabPath));
			Utility.ScrollToElement_NoWait(tabElement, driver);
			tabElement.click();
			packageRateLogger.info("Clicked " + tabName + " tab");
			testSteps.add("Clicked " + tabName + " tab");
			return testSteps;
	}

	public ArrayList<String> verifyRatePlanTypeDisability(WebDriver driver, String parentPlan) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String path = "//span[text()='"+parentPlan+"']/preceding-sibling::span//input";
		Wait.WaitForElement(driver, path);
		WebElement selectPlan = driver.findElement(By.xpath(path));
		Utility.ScrollToElement_NoWait(selectPlan, driver);
		assertTrue(!selectPlan.isEnabled(), "Failed : rate type "+ parentPlan + " is enabled");
		testSteps.add("Verified that rate type ("+ parentPlan +") is disabled");
		packageRateLogger.info("Verified that rate type ("+ parentPlan +") is disabled");

		return testSteps;

	}
	
	public ArrayList<String> verifySearchedProductExist(WebDriver driver, String productName, boolean exist) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
	//	list<WebElement> size = driver.findElements(By.xpath(OR_PackageRatePlan.SEARCHED_PRODUCT_LIST_SIZE)).size();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		packageRateLogger.info("list size : "+driver.findElements(By.xpath(OR_PackageRatePlan.SEARCHED_PRODUCT_LIST_SIZE)).size() );
		Wait.wait2Second();

		packageRateLogger.info("list size : "+ packageRate.SEARCHED_PRODUCT_LIST_SIZE.size() );
		if(exist) {
			assertTrue(packageRate.SEARCHED_PRODUCT_LIST_SIZE.size() > 1 , "Failed: product not found");
			String addProductButton = "//span[text()='" + productName + "']/preceding-sibling::span//i";
			WebElement addProductButtonElement = driver.findElement(By.xpath(addProductButton));
			Wait.WaitForElement(driver, addProductButton);
			Wait.waitForElementToBeVisibile(By.xpath(addProductButton), driver);
			packageRateLogger.info("Verified product ("+ productName +") displayed.");
			testSteps.add("Verified product ("+ productName +") displayed.");
		}else {
			assertTrue(packageRate.SEARCHED_PRODUCT_LIST_SIZE.size() == 1 , "Failed: product found");
			packageRateLogger.info("Verified product("+ productName +") is not displaying.");
			testSteps.add("Verified product("+ productName +") is not displaying.");
			
		}
		packageRateLogger.info("list size : "+ packageRate.SEARCHED_PRODUCT_LIST_SIZE.size() );
		return testSteps;
	}
	
	public HashMap<String, String> getAllProductsImage(WebDriver driver) throws InterruptedException {
		HashMap<String, String> map = new HashMap<>();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductsName);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductsName),
				driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductsName), driver);
		for(int i=0; i < packageRate.ProductsName.size(); i++) {
			String productName = packageRate.ProductsName.get(i).getText();
			productName = productName.trim();
			packageRateLogger.info("productName :  "+ productName);
			
			String productImagePath = "//td[text()='"+productName+"']//preceding-sibling::td/img";
			Wait.WaitForElement(driver, productImagePath);
			Wait.waitForElementToBeVisibile(By.xpath(productImagePath),
					driver);
			Wait.waitForElementToBeClickable(By.xpath(productImagePath), driver);
			WebElement imageSource = driver.findElement(By.xpath(productImagePath));
			Utility.ScrollToElement_NoWait(imageSource, driver);
			String image = imageSource.getAttribute("src");	
			packageRateLogger.info("product Image :  "+ image);
			image = image.trim();
			packageRateLogger.info("product Image :  "+ image);

			map.put(productName, image);
		}		
		return map;
	}
	
	public ArrayList<String> verifyProductImage(WebDriver driver, String productName,String image) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String rateInputPath = "//span[text()='"+ productName +"']//preceding-sibling::div/img";
		//Wait.WaitForElement(driver, rateInputPath);
		Wait.waitForElementToBeVisibile(By.xpath(rateInputPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(rateInputPath), driver);
		WebElement rateInputPathElement = driver.findElement(By.xpath(rateInputPath));
		Utility.ScrollToElement_NoWait(rateInputPathElement, driver);
		String imageSrc = rateInputPathElement.getAttribute("src");
		packageRateLogger.info("Found Image    : "  + imageSrc);
		packageRateLogger.info("Expected Image : "  + image);
		assertEquals( imageSrc,image, "Failed: product's image  missmatched");
		packageRateLogger.info("Verified product ("+ productName +") image.");
		testSteps.add("Verified product ("+ productName +") image.");
		return testSteps;
	}
	
	public ArrayList<String> clearSearchProductField(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
				Wait.WaitForElement(driver, OR_PackageRatePlan.SearchProductsInPackageRate);
				Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.SearchProductsInPackageRate),
						driver);
				Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.SearchProductsInPackageRate), driver);
				Utility.ScrollToElement_NoWait(packageRate.SearchProductsInPackageRate, driver);
				packageRate.SearchProductsInPackageRate.clear();
				
				packageRateLogger.info("Clear product search field ");
				testSteps.add("Clear product search field ");

			
		return testSteps;
	}

	public HashMap<String, String> getAllProductsCategories(WebDriver driver) throws InterruptedException {
		HashMap<String, String> map = new HashMap<>();
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductsName);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductsName),
				driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductsName), driver);
		for(int i=0; i < packageRate.ProductsName.size(); i++) {
			String productName = packageRate.ProductsName.get(i).getText();
			productName = productName.trim();
			packageRateLogger.info("productName :  "+ productName);
			
			String productCategoryPath = "//td[text()='"+productName+"']//following-sibling::td[2]";
			Wait.WaitForElement(driver, productCategoryPath);
			Wait.waitForElementToBeVisibile(By.xpath(productCategoryPath),
					driver);
			Wait.waitForElementToBeClickable(By.xpath(productCategoryPath), driver);
			WebElement categoryElement = driver.findElement(By.xpath(productCategoryPath));
			Utility.ScrollToElement_NoWait(categoryElement, driver);
			String category = categoryElement.getText();	
			packageRateLogger.info("product Category :  "+ category);
			category = category.trim();
			packageRateLogger.info("product Category :  "+ category);

			map.put(productName, category);
		}		
		return map;
	}
	
	public ArrayList<String> verifyCalculationMethod(WebDriver driver, String productName,
			String firstCalculationMethod, String secondCalculationMethod) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String firstDropdownOptionText = "//span[text()='" + productName
				+ "']//following-sibling::div[@class='package-product-data'][1]//div[@class='ant-select-selection-selected-value']";
		Wait.WaitForElement(driver, firstDropdownOptionText);
		Wait.waitForElementToBeVisibile(By.xpath(firstDropdownOptionText), driver);
		Wait.waitForElementToBeClickable(By.xpath(firstDropdownOptionText), driver);
		WebElement firstDropdownOptionTextElement = driver.findElement(By.xpath(firstDropdownOptionText));
		Utility.ScrollToElement_NoWait(firstDropdownOptionTextElement, driver);
		String firstMethod = firstDropdownOptionTextElement.getText();
		assertEquals(firstMethod, firstCalculationMethod, "Failed: First Calculation Method missmatched");

		String secondDropdownOptionText = "//span[text()='" + productName
				+ "']//following-sibling::div[@class='package-product-data'][2]//div[@class='ant-select-selection-selected-value']";
		Wait.WaitForElement(driver, secondDropdownOptionText);
		Wait.waitForElementToBeVisibile(By.xpath(secondDropdownOptionText), driver);
		Wait.waitForElementToBeClickable(By.xpath(secondDropdownOptionText), driver);
		WebElement secondDropdownOptionTextElement = driver.findElement(By.xpath(secondDropdownOptionText));
		Utility.ScrollToElement_NoWait(secondDropdownOptionTextElement, driver);
		String secondMethod = secondDropdownOptionTextElement.getText();
		assertEquals(secondMethod, secondCalculationMethod, "Failed: second Calculation Method missmatched");

		return testSteps;
	}

	public ArrayList<String> selectProducts(WebDriver driver, HashMap<String, String> producstPriceMap) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> getTestSteps = new ArrayList<String>();
		for (Map.Entry<String, String> entry : producstPriceMap.entrySet()) { 
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 	
			getTestSteps.clear();
			getTestSteps = searchProduct(driver, entry.getKey());
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = clickProduct(driver, entry.getKey(), true);
			testSteps.addAll(getTestSteps);
		}
		return testSteps;
	}
	
	public ArrayList<String> verifySelectedProductPrice(WebDriver driver, HashMap<String, String> producstPriceMap) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		for (Map.Entry<String, String> entry : producstPriceMap.entrySet()) { 
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			testSteps.add("Verify default price of Product("+ entry.getKey() +")");
			packageRateLogger.info("Verify default price of Product("+ entry.getKey() +")");
			testSteps.add("Expected default price : " + entry.getValue());
			packageRateLogger.info("Expected default price : " + entry.getValue());
			String getAmount = getProductAmount(driver, entry.getKey());
			testSteps.add("Found : " + getAmount);
			packageRateLogger.info("Found : " + getAmount);
			Assert.assertEquals(entry.getValue(), getAmount, "Failed : Product amount mismacthes");	
		}
		return testSteps;
	}
	
	public ArrayList<String> verifySelectedProductsCalculationMethods(WebDriver driver, HashMap<String, String> producstCalculationMethodMap) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> getTestSteps = new ArrayList<String>();
		for (Map.Entry<String, String> calculationEntry : producstCalculationMethodMap.entrySet()) { 
			System.out.println("Key = " + calculationEntry.getKey() + ", Value = " + calculationEntry.getValue()); 
			testSteps.add("Verify default Calculation method of Product("+ calculationEntry.getKey() +")");
			packageRateLogger.info("Verify default Calculation method of Product("+ calculationEntry.getKey() +")");
			String[] calculationArray = calculationEntry.getValue().split("/");
			String firstCalcualtionMethod = calculationArray[0];
			packageRateLogger.info(" " + firstCalcualtionMethod);
			firstCalcualtionMethod = firstCalcualtionMethod.replace("Per ", "");
			firstCalcualtionMethod = firstCalcualtionMethod.trim();
			packageRateLogger.info(" " + firstCalcualtionMethod);

			String secondCalcualtionMethod = calculationArray[1];
			packageRateLogger.info(" " + secondCalcualtionMethod);
			secondCalcualtionMethod = secondCalcualtionMethod.replace("per ", "");
			secondCalcualtionMethod = secondCalcualtionMethod.trim();
			packageRateLogger.info(" " + secondCalcualtionMethod);

			getTestSteps.clear();
			getTestSteps = verifyCalculationMethod(driver, calculationEntry.getKey(), firstCalcualtionMethod, secondCalcualtionMethod);
			testSteps.addAll(getTestSteps);
		}
		return testSteps;
	}
	
	public ArrayList<String> removeProducts(WebDriver driver, HashMap<String, String> producstPriceMap) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> getTestSteps = new ArrayList<String>();
		for (Map.Entry<String, String> entry : producstPriceMap.entrySet()) { 
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			getTestSteps.clear();
			getTestSteps = clickProduct(driver, entry.getKey(), false);
			testSteps.addAll(getTestSteps);
		}
		return testSteps;
	}
	
	public ArrayList<String> verifyProductsImages(WebDriver driver, HashMap<String, String> producstImage) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> getTestSteps = new ArrayList<String>();
		for (Map.Entry<String, String> entry : producstImage.entrySet()) { 
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
							getTestSteps.clear();
			getTestSteps = verifyProductImage(driver, entry.getKey(),entry.getValue());
			testSteps.addAll(getTestSteps);
			
		}
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getProductDetails> ' Description: <THis method will
	 * return required product details separated by delimiter but if the
	 * required is null or empty it will return first product's details> ' Input
	 * parameters: < WebDriver, String> ' Return value:<String> ' Created By:
	 * <Adhnan Ghaffar> ' Created On: <09/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getProductDetails(WebDriver driver, String productName,String DELIM) throws InterruptedException {
		String productDetails = null;
		if (productName == null || productName.equals("")) {
			Elements_PackageRate packageRate = new Elements_PackageRate(driver);
			Wait.WaitForElement(driver, OR_PackageRatePlan.ProductsName);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductsName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductsName), driver);
			productName = packageRate.ProductsName.get(0).getText();
			productName = productName.trim();
		}
		packageRateLogger.info("productName :  " + productName);
		productDetails = productName;
		String DefaultProductPrice = "//td[text()='" + productName + "']//following-sibling::td[4]";
		Wait.WaitForElement(driver, DefaultProductPrice);
		Wait.waitForElementToBeVisibile(By.xpath(DefaultProductPrice), driver);
		Wait.waitForElementToBeClickable(By.xpath(DefaultProductPrice), driver);
		WebElement priceElement = driver.findElement(By.xpath(DefaultProductPrice));
		Utility.ScrollToElement_NoWait(priceElement, driver);
		String productPrice = priceElement.getText();
		packageRateLogger.info("productPrice :  " + productPrice);
		productPrice = Utility.removeDollarBracketsAndSpaces(productPrice);
		packageRateLogger.info("productPrice :  " + productPrice);

		String DefaultCalculationMethod = "//td[text()='" + productName + "']//following-sibling::td[3]";
		Wait.WaitForElement(driver, DefaultCalculationMethod);
		Wait.waitForElementToBeVisibile(By.xpath(DefaultCalculationMethod), driver);
		Wait.waitForElementToBeClickable(By.xpath(DefaultCalculationMethod), driver);
		WebElement calculationElement = driver.findElement(By.xpath(DefaultCalculationMethod));
		Utility.ScrollToElement_NoWait(calculationElement, driver);
		String calculationMethod = calculationElement.getText();
		packageRateLogger.info("productPrice :  " + calculationMethod);
		calculationMethod = calculationMethod.trim();
		packageRateLogger.info("productPrice :  " + calculationMethod);

		String productImagePath = "//td[text()='" + productName + "']//preceding-sibling::td/img";
		Wait.WaitForElement(driver, productImagePath);
		Wait.waitForElementToBeVisibile(By.xpath(productImagePath), driver);
		Wait.waitForElementToBeClickable(By.xpath(productImagePath), driver);
		WebElement imageSource = driver.findElement(By.xpath(productImagePath));
		Utility.ScrollToElement_NoWait(imageSource, driver);
		String image = imageSource.getAttribute("src");
		packageRateLogger.info("product Image :  " + image);
		image = image.trim();
		packageRateLogger.info("product Image :  " + image);

		String productCategoryPath = "//td[text()='" + productName + "']//following-sibling::td[2]";
		Wait.WaitForElement(driver, productCategoryPath);
		Wait.waitForElementToBeVisibile(By.xpath(productCategoryPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(productCategoryPath), driver);
		WebElement categoryElement = driver.findElement(By.xpath(productCategoryPath));
		Utility.ScrollToElement_NoWait(categoryElement, driver);
		String category = categoryElement.getText();
		packageRateLogger.info("product Category :  " + category);
		category = category.trim();
		packageRateLogger.info("product Category :  " + category);

		productDetails = image +DELIM+ productName + DELIM+ category + DELIM
				+ calculationMethod + DELIM+ productPrice;
		packageRateLogger.info("productDetails :  " + productDetails);
		return productDetails;
	}
	
	public void verifyProductsTabExistBetweenRestrictionsPoliciesAndCalendar(WebDriver driver)  {
		
		ArrayList<String> testSteps = new ArrayList<String>();
		String nextTabPath = "//div[text()='Products']//following-sibling::div[1]";
		Wait.WaitForElement(driver, nextTabPath);
		Wait.waitForElementToBeVisibile(By.xpath(nextTabPath),
				driver);
		Wait.waitForElementToBeClickable(By.xpath(nextTabPath), driver);
		WebElement nexttab = driver.findElement(By.xpath(nextTabPath));
		assertEquals(nexttab.getText(),"Calendar","Failed: Products Next tab is not 'Calendar'");
		String previousTabPath = "//div[text()='Products']//preceding-sibling::div[1]";
		Wait.WaitForElement(driver, previousTabPath);
		Wait.waitForElementToBeVisibile(By.xpath(previousTabPath),
				driver);
		Wait.waitForElementToBeClickable(By.xpath(previousTabPath), driver);
		WebElement previoustab = driver.findElement(By.xpath(previousTabPath));
		assertEquals(previoustab.getText(),"Restrictions & Policies","Failed: Products Previous tab is not 'Calendar'");
	}

	public  ArrayList<String> createProduct(WebDriver driver, String productName, boolean isSellOnBookingEngine, String bookingEngineAvailabilityOption, String roomClass, String productDescription, String productCost, String productPolicy, String productCategory, String calculationMethodFirst, String calculationMethodTwo,
			String startDate, String endDate, String seasonDuration, String timeZone) throws InterruptedException, NumberFormatException, ParseException {
			Elements_PackageRate packageRate = new Elements_PackageRate(driver);
			ArrayList<String> testSteps = new ArrayList<String>();

			String picFilePath = System.getProperty("user.dir") +"\\Images" + "/image.png";
			packageRateLogger.info(picFilePath);
			
			Wait.WaitForElement(driver, OR_PackageRatePlan.AddProduct);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.AddProduct), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.AddProduct), driver);
			Utility.ScrollToElement_NoWait(packageRate.AddProduct, driver);
			packageRate.AddProduct.click();
			testSteps.add("Clicked add prouct");
			packageRateLogger.info("Clicked add prouct");

			
			Wait.WaitForElement(driver, OR_PackageRatePlan.ProductName);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductName), driver);
			Utility.ScrollToElement_NoWait(packageRate.ProductName, driver);
			packageRate.ProductName.clear();
			packageRate.ProductName.sendKeys(productName);
			testSteps.add("Entered product name : " + productName);
			packageRateLogger.info("Entered product name : " + productName);


			packageRate.ProductImage.sendKeys(picFilePath);
			testSteps.add("Uploaded product image : " + picFilePath);
			packageRateLogger.info("Uploaded product image : " + picFilePath);
			
			if(isSellOnBookingEngine) {

				Wait.WaitForElement(driver, OR_PackageRatePlan.checkSellOnBooking);
				Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.checkSellOnBooking), driver);
				Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.checkSellOnBooking), driver);
				Utility.ScrollToElement_NoWait(packageRate.checkSellOnBooking, driver);
				packageRate.checkSellOnBooking.click();
				testSteps.add("Checked 'Sell on booking engine'");
				packageRateLogger.info("Checked 'Sell on booking engine'");
				
				String clickRoomClassInput = "//input[@class='ant-select-search__field']";
				Wait.WaitForElement(driver, clickRoomClassInput);
				Wait.waitForElementToBeVisibile(By.xpath(clickRoomClassInput), driver);
				Wait.waitForElementToBeClickable(By.xpath(clickRoomClassInput), driver);
				WebElement clickElement = driver.findElement(By.xpath(clickRoomClassInput));
				Utility.ScrollToElement_NoWait(clickElement, driver);
				clickElement.sendKeys(roomClass);
				packageRateLogger.info("Entered class name : " + roomClass);
				
				String selectRoomClass = "//li[text()='"+ roomClass +"']";
				Wait.WaitForElement(driver, selectRoomClass);
				Wait.waitForElementToBeVisibile(By.xpath(selectRoomClass), driver);
				Wait.waitForElementToBeClickable(By.xpath(selectRoomClass), driver);
				WebElement roomClasselement = driver.findElement(By.xpath(selectRoomClass));
				Utility.ScrollToElement_NoWait(roomClasselement, driver);
				roomClasselement.click();
				
				testSteps.add("Selected class : " + productName);
				packageRateLogger.info("Selected class : " + productName);
				
				String path = "//div[text()='"+ bookingEngineAvailabilityOption +"']";
				List<WebElement> dateOption = driver.findElements(By.xpath(path));
				packageRateLogger.info("dateOption.size() : " + dateOption.size());
				if(!(dateOption.size() > 0)) {
					testSteps.addAll(selectDropdownOptions(driver, 4, bookingEngineAvailabilityOption));		
				}

				if(bookingEngineAvailabilityOption.equalsIgnoreCase("custom dates")) {
					testSteps.addAll(selectCustomStartAndEndDates(driver,startDate,endDate,Integer.parseInt(seasonDuration),timeZone));
				}			
			
			}
			
			testSteps.addAll(selectDropdownOptions(driver, 1, productCategory));
			
			Wait.WaitForElement(driver, OR_PackageRatePlan.ProductCost);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductCost), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductCost), driver);
			Utility.ScrollToElement_NoWait(packageRate.ProductCost, driver);
			packageRate.ProductCost.clear();
			packageRate.ProductCost.sendKeys(productCost);
			testSteps.add("Entered product cost : " + productCost);
			packageRateLogger.info("Entered product cost : " + productCost);

			testSteps.addAll(selectDropdownOptions(driver, 2, calculationMethodFirst));
			testSteps.addAll(selectDropdownOptions(driver, 3, calculationMethodTwo));

			Wait.WaitForElement(driver, OR_PackageRatePlan.ProductDescription);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductDescription), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductDescription), driver);
			Utility.ScrollToElement_NoWait(packageRate.ProductDescription, driver);
			packageRate.ProductDescription.clear();
			packageRate.ProductDescription.sendKeys(productDescription);
			testSteps.add("Entered product description : " + productDescription);
			packageRateLogger.info("Entered product description : " + productDescription);

			Wait.WaitForElement(driver, OR_PackageRatePlan.ProductPolicy);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductPolicy), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductPolicy), driver);
			Utility.ScrollToElement_NoWait(packageRate.ProductPolicy, driver);
			packageRate.ProductPolicy.clear();
			packageRate.ProductPolicy.sendKeys(productPolicy);
			testSteps.add("Entered product policy : " + productPolicy);
			packageRateLogger.info("Entered product policy : " + productPolicy);

			Wait.WaitForElement(driver, OR_PackageRatePlan.AddProductButton);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.AddProductButton), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.AddProductButton), driver);
			Utility.ScrollToElement_NoWait(packageRate.AddProductButton, driver);
			packageRate.AddProductButton.click();
			testSteps.add("Clicked add prouct");
			packageRateLogger.info("Clicked add prouct");
			
			return testSteps;

	}	


	public ArrayList<String> selectCustomStartAndEndDates(WebDriver driver, String startDate, String endDate, int addDays,String timeZone) throws ParseException, InterruptedException {
		String getStartDate[] = startDate.split(Utility.DELIM);
		String getEndDate[] = endDate.split(Utility.DELIM);
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> listOfStartDate = new ArrayList<>(); 
		ArrayList<String> listOfEndDate = new ArrayList<>(); 
			
			for (int i = 0; i < getStartDate.length; i++) {
				
				
				String getDate = getStartDate[i].trim(); 
				String getCurrentDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
				if (ESTTimeZone.CompareDates(getDate, "MM/dd/yyyy", timeZone)) {
					getDate = getCurrentDate;
				}
				packageRateLogger.info("start: "+getDate);
			listOfStartDate.add(getDate);
			
			getDate = getEndDate[i].trim();
			if (ESTTimeZone.CompareDates(getDate, "MM/dd/yyyy", timeZone)) {
					 getCurrentDate = Utility.getNextDate(addDays, "MM/dd/yyyy", timeZone);
					getDate = getCurrentDate;
				}
			
			packageRateLogger.info("end: "+getDate);
			listOfEndDate.add(getDate);
			addDays = addDays+2;
		}
			int count = 0;
			
			for (int i = 0; i < listOfStartDate.size(); i++) {
			
				packageRateLogger.info("i: "+i);
				testSteps.add("=================== SELECT START DATE ======================");
				packageRateLogger.info("=================== SELECT START DATE ======================");
				getTestSteps.clear();
			//	getTestSteps = derivedRate.selectCustomDateFromCalender(driver, count, startDate, dateFormat);
				
				getTestSteps.clear();
				getTestSteps = selectDate(driver, listOfStartDate.get(i),count);
				testSteps.addAll(getTestSteps);
				try{
					getTestSteps.clear();
					getTestSteps = clickDateRangeMissmatchedPopupButton(driver, "Yes");
					testSteps.addAll(getTestSteps);
				}catch(Exception e){
					
				}
					count = count+1;
				testSteps.add("=================== SELECT END DATE ======================");
				packageRateLogger.info("=================== SELECT END DATE ======================");
				getTestSteps.clear();
				getTestSteps = selectDate(driver, listOfEndDate.get(i),count);
				testSteps.addAll(getTestSteps);
				
				     count = count+1;
				try{
				    getTestSteps.clear();
					getTestSteps =clickDateRangeMissmatchedPopupButton(driver, "Yes");
					testSteps.addAll(getTestSteps);
				}catch(Exception e){
					
				}
				
				// here added one more date
				if (i<(listOfEndDate.size()-1)) {
					Wait.wait2Second();
					getTestSteps.clear();
	   				getTestSteps = clickAddCustomDate1(driver);
	   				testSteps.addAll(getTestSteps);

			}
				
		}

		return testSteps;
	}

	public  ArrayList<String> clickAddCustomDate1(WebDriver driver) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		int beforeCustomDates = driver.findElements(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE)).size();

		packageRateLogger.info("Before Click plus button '"+(beforeCustomDates/2)+"' Custome Range is displaying");
		test_steps.add("Before Click plus button '"+(beforeCustomDates/2)+"' Custome Range is displaying");
		Wait.WaitForElement(driver, DerivedRatePage.ADD_CUSTOM_DATE);
		Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.ADD_CUSTOM_DATE), driver);
		Wait.waitForElementToBeClickable(By.xpath( DerivedRatePage.ADD_CUSTOM_DATE), driver);
		//Utility.ScrollToElement(elements.ADD_CUSTOM_DATE, driver);
		elements.ADD_CUSTOM_DATE.click();
		packageRateLogger.info("Click plus button of Custom Date Range");
		return test_steps;
	}
	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <clickDateRangeMissmatchedPopupButton> 
	 * ' Description: <This method will click on date range popup yes or cancel button based on parameter> 
	 * ' Input parameters: <WebDriver driver,   String buttonName> 
	 * ' Return value: <ArrayList>
	 * ' Created By: <Adnan Ghaffar>
	 * ' Created On:  <08/14/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */
	public ArrayList<String> clickDateRangeMissmatchedPopupButton(WebDriver driver, String buttonName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String xpath = "//div[@class='ant-modal-footer']//span[text()='"+buttonName+"']/parent::button";			
		try {
			WebElement button = driver.findElement(By.xpath(xpath));
			//Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			Wait.wait2Second();
			Utility.ScrollToElement(button, driver);
			button.click();
			packageRateLogger.info("Click '"+buttonName+"' button of Date Range Missmatched Popup");
			testSteps.add("Click '"+buttonName+"' button of Date Range Missmatched Popup");
			try{
				Wait.waitForElementToBeGone(driver, button, 30);
			}catch(Exception e){
			
			}
		} catch (Exception e) {
		}
		return testSteps;
	}

	public ArrayList<String> selectDate(WebDriver driver, String date,int index)
			throws InterruptedException, ParseException {
		ArrayList<String> test_steps = new ArrayList<>();

		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.SELECT_CUSTOM_DATE);
		Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE), driver);
		elements.SELECT_CUSTOM_DATE.get(index).click();
		Wait.wait1Second();
		packageRateLogger.info("Click on Calender");
		try{
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.getCalendarMonthHeading), driver);
		}catch(Exception e){
			Wait.WaitForElement(driver, DerivedRatePage.SELECT_CUSTOM_DATE);
			Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE), driver);
			elements.SELECT_CUSTOM_DATE.get(index).click();
			Wait.wait1Second();
			packageRateLogger.info("Again Click on Calender");
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.getCalendarMonthHeading), driver);
		}
		boolean isMonthFind = true;

		while (isMonthFind) {
			
			
			Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.getCalendarMonthHeading), driver);

			String getMonth = elements.getCalendarMonthHeading.getText().trim();
			String expectedMonth = ESTTimeZone.getDateBaseOnDate(date, "MM/dd/yyyy", "MMMM yyyy");
			System.out.println("expectedMonth: " + expectedMonth);

			String getDate = ESTTimeZone.getDateBaseOnDate(date, "MM/dd/yyyy", "EEE MMM dd yyyy");
			System.out.println("getStartDate: " + getDate);

			System.out.println("getMonth: " + getMonth);

			if (getMonth.equals(expectedMonth)) {

				String path = "//div[@aria-label='"+getDate+"']";
				driver.findElement(By.xpath(path)).click();
					test_steps.add("Select arrival date: " + date);
					packageRateLogger.info("Select Arrival Date");
					isMonthFind = false;
				break;
			}
			// click on next month
			else {
				elements.clickCalendarNextMonth.click();
				test_steps.add("Click on next month");
			}
		}

		return test_steps;

	}

	public  ArrayList<String> selectDropdownOptions(WebDriver driver, int index, String optionToSelect) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<String>();
			
			String ClickToExpandDropdown = "(//span[@class='ant-select-arrow']/i)[" + index + "]";
			Wait.WaitForElement(driver, ClickToExpandDropdown);
			Wait.waitForElementToBeVisibile(By.xpath(ClickToExpandDropdown), driver);
			Wait.waitForElementToBeClickable(By.xpath(ClickToExpandDropdown), driver);
			WebElement element = driver.findElement(By.xpath(ClickToExpandDropdown));
			Utility.ScrollToElement_NoWait(element, driver);
			element.click();
			packageRateLogger.info("Clicked dropdown icon");

			packageRateLogger.info("Selected option : " + optionToSelect);
			String optionPath = "//li[contains(text(), '"+ optionToSelect +"')]";
			Wait.WaitForElement(driver, optionPath);
			Wait.waitForElementToBeVisibile(By.xpath(optionPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(optionPath), driver);
			WebElement optionElement = driver.findElement(By.xpath(optionPath));
			Utility.ScrollToElement_NoWait(optionElement, driver);
			optionElement.click();
			testSteps.add("Selected option : " + optionToSelect);
			packageRateLogger.info("Selected option : " + optionToSelect);
			return testSteps;
	}
	
	//Added By Adhnan 09/10/2020
	// this mwthod will verify the details of the single product which is already selected 
	public ArrayList<String> verifySelectedProductDetails(WebDriver driver,String productName, String productPrice , String image, String firstCalculationMethod, String secondCalculationMethod) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>(); 
		ArrayList<String> getTestSteps = new ArrayList<String>();
			testSteps.add("Verify default price of Product("+ productName +")");
			packageRateLogger.info("Verify default price of Product("+ productName +")");
			testSteps.add("Expected default price : " + productPrice);
			packageRateLogger.info("Expected default price : " + productPrice);
			String getAmount = getProductAmount(driver, productName);
			testSteps.add("Found : " + getAmount);
			packageRateLogger.info("Found : " + getAmount);
			Assert.assertEquals(productPrice, getAmount, "Failed : Product amount mismacthes");	
			getTestSteps.clear();
			getTestSteps = verifyCalculationMethod(driver, productName, firstCalculationMethod, secondCalculationMethod);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = verifyProductImage(driver, productName,image);
			testSteps.addAll(getTestSteps);
		return testSteps;
	}

	//Added by farhan on 09/10/2020
	//This method will create package rate plan
	public ArrayList<String> createPackage(WebDriver driver, String packageRatePlanName, String folioDisplayName, String packageDescription, String rateType,
			String productName, String productAmount, String perAdult, String perInterval,String channels, String roomClasses, String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights, String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount, String isWithInDaysReq,
			String withInDaysCount, String promoCode, String policiesType, String policiesName, String isPolicesReq, String seasonStartDate, String seasonEndDate, String seasonName,
			String isMonDay, String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults, String ratePerNight,
			String maxAdults, String maxPersons, String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason, String extraRoomClassesInSeason, String extraRoomClassRatePerNight,
			String extraRoomClassMaxAdults, String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight, String extraRoomClassAdditionalChildPerNight,
			String isAssignRulesByRoomClass, String isSeasonLevelRules, String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String seasonPolicyType, String seasonPolicyValues, String isSeasonPolicies) throws Exception {
		ArrayList<String> testSteps = new ArrayList<String>();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		testSteps.add("=================== CREATING NEW PACKAGE RATE PLAN ======================");
		packageRateLogger.info("=================== CREATING NEW PACKAGE RATE PLAN ======================");

		ratesGrid.clickRateGridAddRatePlan(driver);
		ratesGrid.clickRateGridAddRatePlanOption(driver, "Package rate plan");

		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Package rate plan", testSteps);

		testSteps.add(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
		packageRateLogger.info(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

		nightlyRate.enterRatePlanName(driver, packageRatePlanName, testSteps);
		nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
		nightlyRate.enterRatePlanDescription(driver, packageDescription, testSteps);

		nightlyRate.clickNextButton(driver, testSteps);
		selectParentRatePlan(driver, rateType);
		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", packageRatePlanName, testSteps);
		nightlyRate.clickNextButton(driver, testSteps);
		
		testSteps.addAll(addProducts(driver, productName, productAmount, perAdult, perInterval));
		nightlyRate.clickNextButton(driver, testSteps);
				

		testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
		packageRateLogger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

		nightlyRate.selectChannels(driver, channels, true, testSteps);
		String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
		nightlyRate.clickNextButton(driver, testSteps);

		nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

		testSteps.add("=================== SELECT ROOM CLASSES ======================");
		packageRateLogger.info("=================== SELECT ROOM CLASSES ======================");
		nightlyRate.selectRoomClasses(driver, roomClasses, true, testSteps);
		String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
		nightlyRate.clickNextButton(driver, testSteps);

		nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

		nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType, Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights, Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode, testSteps);


		String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
		nightlyRate.clickNextButton(driver, testSteps);

		nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

		nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);

		HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
				Boolean.parseBoolean(isPolicesReq), testSteps);
		nightlyRate.clickNextButton(driver, testSteps);
		nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
				Boolean.parseBoolean(isPolicesReq), testSteps);

		

		testSteps.add("=================== CREATE SEASON ======================");
		packageRateLogger.info("=================== CREATE SEASON ======================");

		nightlyRate.clickCreateSeason(driver, testSteps);
		nightlyRate.createSeason(driver, testSteps, seasonStartDate, seasonEndDate, seasonName, isMonDay,
				isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
				isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
				additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason,
				extraRoomClassesInSeason, extraRoomClassRatePerNight, extraRoomClassMaxAdults,
				extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
				extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSeasonLevelRules,
				seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
				isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
				isSeasonRuleOnSaturday, isSeasonRuleOnSunday, seasonPolicyType, seasonPolicyValues,
				isSeasonPolicies);
		
		nightlyRate.clickCompleteChanges(driver, testSteps);
		nightlyRate.clickSaveAsActive(driver, testSteps);
		
		testSteps.add("package rate  plan created");
		packageRateLogger.info("package rate  plan created");


		testSteps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
		packageRateLogger.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

		ratesGrid.clickRatePlanArrow(driver, testSteps);
		ratesGrid.searchRatePlan(driver,packageRatePlanName);
		
		String getRatPlanName = ratesGrid.selectedRatePlan(driver);
		
		packageRateLogger.info("getRatPlanName: "+getRatPlanName);
		
		testSteps.add("Successfully verified Created Rate Plan");
		packageRateLogger.info("Successfully verified Created Rate Plan");
		return testSteps;
	}

	
	//Added by farhan on 09/10/2020
		//This method will create package rate plan
		public ArrayList<String> createPackage(WebDriver driver, String packageRatePlanName, String folioDisplayName, String packageDescription, String rateType,String intervalRatePlanIntervalValue,
				String isDefaultProRateChecked,
				String productName, String productAmount, String perAdult, String perInterval,String channels, String roomClasses, String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights, String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount, String isWithInDaysReq,
				String withInDaysCount, String promoCode, String policiesType, String policiesName, String isPolicesReq, String seasonStartDate, String seasonEndDate, String seasonName,
				String isMonDay, String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults, String ratePerNight,
				String maxAdults, String maxPersons, String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason, String extraRoomClassesInSeason, String extraRoomClassRatePerNight,
				String extraRoomClassMaxAdults, String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight, String extraRoomClassAdditionalChildPerNight,
				String isAssignRulesByRoomClass, String isSeasonLevelRules, String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
				String seasonPolicyType, String seasonPolicyValues, String isSeasonPolicies) throws Exception {
			ArrayList<String> testSteps = new ArrayList<String>();
			RatesGrid ratesGrid = new RatesGrid();
			NightlyRate nightlyRate = new NightlyRate();
			testSteps.add("=================== CREATING NEW PACKAGE RATE PLAN ======================");
			packageRateLogger.info("=================== CREATING NEW PACKAGE RATE PLAN ======================");

			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Package rate plan");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Package rate plan", testSteps);

			testSteps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			packageRateLogger.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			nightlyRate.enterRatePlanName(driver, packageRatePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, packageDescription, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);
			testSteps.addAll(selectParentRatePlan(driver, rateType));
			if (rateType.equalsIgnoreCase("Interval rates")) {

				testSteps.addAll( ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue));

				testSteps.addAll(ratesGrid.byDefaultProrateCheckbox(driver,
						Boolean.parseBoolean(isDefaultProRateChecked)));
			} 
			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", packageRatePlanName, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			
			testSteps.addAll(addProducts(driver, productName, productAmount, perAdult, perInterval));
			nightlyRate.clickNextButton(driver, testSteps);
					

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			packageRateLogger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, channels, true, testSteps);
			String summaryChannels =null;
			if(rateType.equalsIgnoreCase("Interval rates")) {
				summaryChannels = channels;
			}else {
				summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
			}
				
			
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			packageRateLogger.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, roomClasses, true, testSteps);
			String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType, Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights, Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode, testSteps);


			String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

			nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);

			HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
					Boolean.parseBoolean(isPolicesReq), testSteps);
			nightlyRate.clickNextButton(driver, testSteps);
			nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
					Boolean.parseBoolean(isPolicesReq), testSteps);

			

			testSteps.add("=================== CREATE SEASON ======================");
			packageRateLogger.info("=================== CREATE SEASON ======================");

			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.createSeason(driver, testSteps, seasonStartDate, seasonEndDate, seasonName, isMonDay,
					isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
					isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
					additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason,
					extraRoomClassesInSeason, extraRoomClassRatePerNight, extraRoomClassMaxAdults,
					extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
					extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSeasonLevelRules,
					seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
					isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
					isSeasonRuleOnSaturday, isSeasonRuleOnSunday, seasonPolicyType, seasonPolicyValues,
					isSeasonPolicies);
			
			nightlyRate.clickCompleteChanges(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);
			
			testSteps.add("package rate  plan created");
			packageRateLogger.info("package rate  plan created");


			testSteps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
			packageRateLogger.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.searchRatePlan(driver,packageRatePlanName);
			
			String getRatPlanName = ratesGrid.selectedRatePlan(driver);
			
			packageRateLogger.info("getRatPlanName: "+getRatPlanName);
			
			testSteps.add("Successfully verified Created Rate Plan");
			packageRateLogger.info("Successfully verified Created Rate Plan");
			return testSteps;
		}

		
		//Added by farhan on 09/10/2020
				//This method will create package rate plan
			public ArrayList<String> createPackage(WebDriver driver, String packageRatePlanName, String folioDisplayName, String packageDescription, String rateType,String intervalRatePlanIntervalValue,
						String isDefaultProRateChecked,
						String productName, String productAmount, String perAdult, String perInterval,String channels, String roomClasses, String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights, String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount, String isWithInDaysReq,
						String withInDaysCount, String promoCode, String policiesType, String policiesName, String isPolicesReq, String seasonStartDate, String seasonEndDate, String seasonName,
						String isMonDay, String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults, String ratePerNight,
						String maxAdults, String maxPersons, String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason, String extraRoomClassesInSeason, String extraRoomClassRatePerNight,
						String extraRoomClassMaxAdults, String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight, String extraRoomClassAdditionalChildPerNight,
						String isAssignRulesByRoomClass, String isSeasonLevelRules, String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
						String seasonPolicyType, String seasonPolicyValues, String isSeasonPolicies,String isProRateStayInSeason, String isProRateInRoomClass, String roomClassesWithProRateUnchecked,String  isCustomPerNight, String customRoomClasses,
						String customRatePerNight,String  isAssignPoliciesByRoomClass, String customRateAdultdPerNight, String customRateChildPerNight,
						String isCustomRatePerNightAdultandChild, String seasonPolicySpecificRoomClasses)throws Exception {
					ArrayList<String> testSteps = new ArrayList<String>();
					RatesGrid ratesGrid = new RatesGrid();
					NightlyRate nightlyRate = new NightlyRate();
					testSteps.add("=================== CREATING NEW PACKAGE RATE PLAN ======================");
					packageRateLogger.info("=================== CREATING NEW PACKAGE RATE PLAN ======================");

					ratesGrid.clickRateGridAddRatePlan(driver);
					ratesGrid.clickRateGridAddRatePlanOption(driver, "Package rate plan");

					nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Package rate plan", testSteps);

					testSteps.add(
							"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
					packageRateLogger.info(
							"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

					nightlyRate.enterRatePlanName(driver, packageRatePlanName, testSteps);
					nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
					nightlyRate.enterRatePlanDescription(driver, packageDescription, testSteps);

					nightlyRate.clickNextButton(driver, testSteps);
					testSteps.addAll(selectParentRatePlan(driver, rateType));
					if (rateType.equalsIgnoreCase("Interval rates")) {

						testSteps.addAll( ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue));

						testSteps.addAll(ratesGrid.byDefaultProrateCheckbox(driver,
								Boolean.parseBoolean(isDefaultProRateChecked)));
					} 
					nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", packageRatePlanName, testSteps);
					nightlyRate.clickNextButton(driver, testSteps);
					
					testSteps.addAll(addProducts(driver, productName, productAmount, perAdult, perInterval));
					nightlyRate.clickNextButton(driver, testSteps);
							

					testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
					packageRateLogger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

					nightlyRate.selectChannels(driver, channels, true, testSteps);
					String summaryChannels =null;
					if(rateType.equalsIgnoreCase("Interval rates")) {
						summaryChannels = channels;
					}else {
						summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
					}
						
					
					nightlyRate.clickNextButton(driver, testSteps);

					nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

					testSteps.add("=================== SELECT ROOM CLASSES ======================");
					packageRateLogger.info("=================== SELECT ROOM CLASSES ======================");
					nightlyRate.selectRoomClasses(driver, roomClasses, true, testSteps);
					String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
					nightlyRate.clickNextButton(driver, testSteps);

					nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

					nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType, Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights, Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq), withInDaysCount, promoCode, testSteps);


					String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
					nightlyRate.clickNextButton(driver, testSteps);

					nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

					nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);

					HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
							Boolean.parseBoolean(isPolicesReq), testSteps);
					nightlyRate.clickNextButton(driver, testSteps);
					nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
							Boolean.parseBoolean(isPolicesReq), testSteps);

					

					testSteps.add("=================== CREATE SEASON ======================");
					packageRateLogger.info("=================== CREATE SEASON ======================");

					nightlyRate.clickCreateSeason(driver, testSteps);
					nightlyRate.createUpdateSeasonForPackageIntervalRatePlan(driver, testSteps, seasonStartDate, seasonEndDate, seasonName,
							isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, roomClasses,
							isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight,
							additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason,
							extraRoomClassRatePerNight, extraRoomClassMaxAdults, extraRoomClassMaxPersons,
							extraRoomClassAdditionalAdultsPerNight, extraRoomClassAdditionalChildPerNight,
							isAssignRulesByRoomClass, isSeasonLevelRules, seasonRuleSpecificRoomClasses, seasonRuleType,
							seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday,
							isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday,
							isSeasonPolicies,seasonPolicyType, seasonPolicyValues,  isDefaultProRateChecked,
							isProRateStayInSeason, isProRateInRoomClass, roomClassesWithProRateUnchecked, isCustomPerNight, customRoomClasses,
							customRatePerNight, isAssignPoliciesByRoomClass, customRateAdultdPerNight, customRateChildPerNight,
							isCustomRatePerNightAdultandChild, seasonPolicySpecificRoomClasses,false,intervalRatePlanIntervalValue);

					
					nightlyRate.clickCompleteChanges(driver, testSteps);
					try{
						nightlyRate.clickSaveAsActive(driver, testSteps);
					}catch(Exception e) {
						Wait.wait3Second();
						nightlyRate.clickCompleteChanges(driver, testSteps);
						nightlyRate.clickSaveAsActive(driver, testSteps);
					}
					
					testSteps.add("package rate  plan created");
					packageRateLogger.info("package rate  plan created");


					testSteps.add("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");
					packageRateLogger.info("=================== SEARCH CREATED PACKAGE RATE PLAN ======================");

					ratesGrid.clickRatePlanArrow(driver, testSteps);
					ratesGrid.searchRatePlan(driver,packageRatePlanName);
					
					String getRatPlanName = ratesGrid.selectedRatePlan(driver);
					
					packageRateLogger.info("getRatPlanName: "+getRatPlanName);
					
					testSteps.add("Successfully verified Created Rate Plan");
					packageRateLogger.info("Successfully verified Created Rate Plan");
					return testSteps;
				}

	// Added by Adhnan 09/29/2020
	public ArrayList<String> createProducts(WebDriver driver, String productName,
			String isSellOnBookingEngine, String bookingEngineAvailabilityOption, String roomClass,
			String productDescription, String productCost, String productPolicy, String productCategory,
			String calculationMethodFirst, String calculationMethodTwo, String startDate, String endDate)
			throws InterruptedException, NumberFormatException, ParseException {
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> getTestSteps = new ArrayList<String>();

		packageRateLogger.info("productName : " + productName);

		packageRateLogger.info("productCategory : " + productCategory);
		packageRateLogger.info("isSellOnBookingEngine : " + isSellOnBookingEngine);

		packageRateLogger.info("bookingEngineAvailabilityOption : " + bookingEngineAvailabilityOption);
		packageRateLogger.info("roomClass : " + roomClass);
		packageRateLogger.info("productDescription : " + productDescription);
		packageRateLogger.info("productCost : " + productCost);
		packageRateLogger.info("productPolicy : " + productPolicy);
		packageRateLogger.info("calculationMethodFirst : " + calculationMethodFirst);
		packageRateLogger.info("calculationMethodTwo : " + calculationMethodTwo);
		packageRateLogger.info("startDate : " + startDate);
		packageRateLogger.info("EndDate : " + endDate);
		
		packageRateLogger.info("productNameSize : " + productName.split(Utility.DELIM).length);
		packageRateLogger.info("productCategorySize : " + productCategory.split(Utility.DELIM).length);
		packageRateLogger.info("isSellOnBookingEngineSize : " + isSellOnBookingEngine.split(Utility.DELIM).length);
		packageRateLogger.info("bookingEngineAvailabilityOptionSize : " + bookingEngineAvailabilityOption.split(Utility.DELIM).length);
		packageRateLogger.info("roomClassSize : " + roomClass.split(Utility.DELIM).length);
		packageRateLogger.info("productDescriptionSize : " + productDescription.split(Utility.DELIM).length);
		packageRateLogger.info("productCostSize : " + productCost.split(Utility.DELIM).length);
		packageRateLogger.info("productPolicySize : " + productPolicy.split(Utility.DELIM).length);
		packageRateLogger.info("calculationMethodFirstSize : " + calculationMethodFirst.split(Utility.DELIM).length);
		packageRateLogger.info("calculationMethodTwoSize : " + calculationMethodTwo.split(Utility.DELIM).length);
		packageRateLogger.info("startDateSize : " + startDate.split(Utility.DELIM).length);
		packageRateLogger.info("EndDateSize : " + endDate.split(Utility.DELIM).length);
		String[] productsName = productName.split(Utility.DELIM);
		String[] isSellOnBookingEngineList = isSellOnBookingEngine.split(Utility.DELIM);
		String[] bookingEngineAvailabilityOptions = bookingEngineAvailabilityOption.split(Utility.DELIM);
		String[] roomClasses = roomClass.split(Utility.DELIM);
		String[] productsDescription = productDescription.split(Utility.DELIM);
		String[] productsAmount = productCost.split(Utility.DELIM);
		String[] productsPolicy = productPolicy.split(Utility.DELIM);
		String[] productsCategory = productCategory.split(Utility.DELIM);
		String[] calculationMethodsFirst = calculationMethodFirst.split(Utility.DELIM);
		String[] calculationMethodsTwo = calculationMethodTwo.split(Utility.DELIM);
		String[] startDateList = startDate.split(Utility.DELIM);
		String[] endDateList = endDate.split(Utility.DELIM);
		for (int i = 0; i < productsName.length; i++) {
			if(!verifyProductExist(driver,productsName[i])) {
			getTestSteps.clear();
			getTestSteps = createProduct(driver, productsName[i],
					Boolean.parseBoolean(isSellOnBookingEngineList[i]), bookingEngineAvailabilityOptions[i],
					roomClasses[i], productsDescription[i], productsAmount[i], productsPolicy[i],
					productsCategory[i], calculationMethodsFirst[i], calculationMethodsTwo[i],
					startDateList[i], endDateList[i]);
			testSteps.addAll(getTestSteps);
			}
		}
		return testSteps;

	}

	// Added by Adhnan 09/29/2020
	public ArrayList<String> createProduct(WebDriver driver, String productName,
			boolean isSellOnBookingEngine, String bookingEngineAvailabilityOption, String roomClass,
			String productDescription, String productCost, String productPolicy, String productCategory,
			String calculationMethodFirst, String calculationMethodTwo, String startDate, String endDate)
			throws InterruptedException, NumberFormatException, ParseException {
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement(driver, OR_PackageRatePlan.AddProduct);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.AddProduct), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.AddProduct), driver);
		Utility.ScrollToElement_NoWait(packageRate.AddProduct, driver);
		try{
			packageRate.AddProduct.click();
		}catch(Exception e) {
			Utility.clickThroughJavaScript(driver, packageRate.AddProduct);
		}
		testSteps.add("Clicked add prouct");
		packageRateLogger.info("Clicked add prouct");

		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductName);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductName), driver);
		Utility.ScrollToElement_NoWait(packageRate.ProductName, driver);
		packageRate.ProductName.clear();
		packageRate.ProductName.sendKeys(productName);
		testSteps.add("Entered product name : " + productName);
		packageRateLogger.info("Entered product name : " + productName);

		if (isSellOnBookingEngine) {

			Wait.WaitForElement(driver, OR_PackageRatePlan.checkSellOnBooking);
			Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.checkSellOnBooking), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.checkSellOnBooking), driver);
			Utility.ScrollToElement_NoWait(packageRate.checkSellOnBooking, driver);
			packageRate.checkSellOnBooking.click();
			testSteps.add("Checked 'Sell on booking engine'");
			packageRateLogger.info("Checked 'Sell on booking engine'");

			String clickRoomClassInput = "//input[@class='ant-select-search__field']";
			Wait.WaitForElement(driver, clickRoomClassInput);
			Wait.waitForElementToBeVisibile(By.xpath(clickRoomClassInput), driver);
			Wait.waitForElementToBeClickable(By.xpath(clickRoomClassInput), driver);
			WebElement clickElement = driver.findElement(By.xpath(clickRoomClassInput));
			Utility.ScrollToElement_NoWait(clickElement, driver);
			clickElement.sendKeys(roomClass);
			packageRateLogger.info("Entered class name : " + roomClass);

			String selectRoomClass = "//li[text()='" + roomClass + "']";
			Wait.WaitForElement(driver, selectRoomClass);
			Wait.waitForElementToBeVisibile(By.xpath(selectRoomClass), driver);
			Wait.waitForElementToBeClickable(By.xpath(selectRoomClass), driver);
			WebElement roomClasselement = driver.findElement(By.xpath(selectRoomClass));
			Utility.ScrollToElement_NoWait(roomClasselement, driver);
			roomClasselement.click();

			testSteps.add("Selected class : " + productName);
			packageRateLogger.info("Selected class : " + productName);

			String path = "//div[text()='" + bookingEngineAvailabilityOption + "']";
			List<WebElement> dateOption = driver.findElements(By.xpath(path));
			packageRateLogger.info("dateOption.size() : " + dateOption.size());
			if (!(dateOption.size() > 0)) {
				testSteps.addAll(selectDropdownOptions(driver, 4, bookingEngineAvailabilityOption));
			}

			if (bookingEngineAvailabilityOption.equalsIgnoreCase("custom dates")) {
				testSteps.addAll(selectCustomStartAndEndDates(driver, startDate, endDate));
			}

		}

		testSteps.addAll(selectDropdownOptions(driver, 1, productCategory));

		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductCost);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductCost), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductCost), driver);
		Utility.ScrollToElement_NoWait(packageRate.ProductCost, driver);
		packageRate.ProductCost.clear();
		packageRate.ProductCost.sendKeys(productCost);
		testSteps.add("Entered product cost : " + productCost);
		packageRateLogger.info("Entered product cost : " + productCost);

		testSteps.addAll(selectDropdownOptions(driver, 2, calculationMethodFirst));
		testSteps.addAll(selectDropdownOptions(driver, 3, calculationMethodTwo));

		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductDescription);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductDescription), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductDescription), driver);
		Utility.ScrollToElement_NoWait(packageRate.ProductDescription, driver);
		packageRate.ProductDescription.clear();
		packageRate.ProductDescription.sendKeys(productDescription);
		testSteps.add("Entered product description : " + productDescription);
		packageRateLogger.info("Entered product description : " + productDescription);

		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductPolicy);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductPolicy), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductPolicy), driver);
		Utility.ScrollToElement_NoWait(packageRate.ProductPolicy, driver);
		packageRate.ProductPolicy.clear();
		packageRate.ProductPolicy.sendKeys(productPolicy);
		testSteps.add("Entered product policy : " + productPolicy);
		packageRateLogger.info("Entered product policy : " + productPolicy);

		Wait.WaitForElement(driver, OR_PackageRatePlan.AddProductButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.AddProductButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.AddProductButton), driver);
		Utility.ScrollToElement_NoWait(packageRate.AddProductButton, driver);
		packageRate.AddProductButton.click();
		testSteps.add("Clicked add prouct");
		packageRateLogger.info("Clicked add prouct");

		return testSteps;

	}

	// Added by Adhnan 09/29/2020
	public ArrayList<String> selectCustomStartAndEndDates(WebDriver driver, String startDate,
			String endDate) throws ParseException, InterruptedException {
		String getStartDate[] = startDate.split(Utility.DELIM);
		String getEndDate[] = endDate.split(Utility.DELIM);
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> listOfStartDate = new ArrayList<>();
		ArrayList<String> listOfEndDate = new ArrayList<>();

		int count = 0;

		for (int i = 0; i < listOfStartDate.size(); i++) {

			packageRateLogger.info("i: " + i);
			testSteps.add("=================== SELECT START DATE ======================");
			packageRateLogger.info("=================== SELECT START DATE ======================");
			getTestSteps.clear();
			// getTestSteps = derivedRate.selectCustomDateFromCalender(driver, count,
			// startDate, dateFormat);

			getTestSteps.clear();
			getTestSteps = selectDate(driver, getStartDate[i], count);
			testSteps.addAll(getTestSteps);
			try {
				getTestSteps.clear();
				getTestSteps = clickDateRangeMissmatchedPopupButton(driver, "Yes");
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

			}
			count = count + 1;
			testSteps.add("=================== SELECT END DATE ======================");
			packageRateLogger.info("=================== SELECT END DATE ======================");
			getTestSteps.clear();
			getTestSteps = selectDate(driver, getEndDate[i], count);
			testSteps.addAll(getTestSteps);

			count = count + 1;
			try {
				getTestSteps.clear();
				getTestSteps = clickDateRangeMissmatchedPopupButton(driver, "Yes");
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

			}

			// here added one more date
			if (i < (listOfEndDate.size() - 1)) {
				Wait.wait2Second();
				getTestSteps.clear();
				getTestSteps = clickAddCustomDate1(driver);
				testSteps.addAll(getTestSteps);

			}

		}

		return testSteps;
	}
	
	public boolean verifyProductExist(WebDriver driver,String requiredProductName) throws InterruptedException {
		
		Elements_PackageRate packageRate = new Elements_PackageRate(driver);
		Wait.WaitForElement(driver, OR_PackageRatePlan.ProductsName);
		Wait.waitForElementToBeVisibile(By.xpath(OR_PackageRatePlan.ProductsName),
				driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_PackageRatePlan.ProductsName), driver);
		boolean found = false;
		for(int i= 0; i < packageRate.ProductsName.size(); i++) {
			String productName = packageRate.ProductsName.get(i).getText();
			productName = productName.trim();
//			packageRateLogger.info("productName :  "+ productName);
//			packageRateLogger.info("requiredProductName :  "+ requiredProductName);
			if(productName.equalsIgnoreCase(requiredProductName)) {
				found = true;
				break;
			}	
		}		
		return found;
	}

}
