package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.IDistribution;
import com.innroad.inncenter.properties.OR_Products;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Distribution;
import com.innroad.inncenter.webelements.Elements_Products;
import com.innroad.inncenter.webelements.Elements_TapeChart;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Products {

	public static Logger logger = Logger.getLogger("Product");

	public ArrayList<String> verifyProductsColumnsTitle(WebDriver driver) {
		Elements_Products elements = new Elements_Products(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		String[] titleList = new String[] { "Picture", "Name", "For sale on Booking engine", "Category",
				"Calculation method", "Price", "", "" };
		int size = elements.productColumnsTitleList.size();

		for (int i = 0; i < size; i++) {

			if (elements.productColumnsTitleList.get(i).getText().equals(titleList[i])) {
				logger.info("Verified Title:" + elements.productColumnsTitleList.get(i).getText() + " In Products");
				testSteps.add("Verified Title:" + elements.productColumnsTitleList.get(i).getText() + " In Products");
			}

		}

		return testSteps;

	}

	public ArrayList<String> verifyEditAndDeleteIcons(WebDriver driver) {
		Elements_Products elements = new Elements_Products(driver);
		ArrayList<String> testSteps = new ArrayList<String>();

		int size = elements.productsEditIconList.size();

		for (int i = 0; i < size; i++) {

			assertEquals(elements.productsEditIconList.get(i).isDisplayed(), true, "Failed: Edit Icon didn't Display");
			logger.info("Verified Edit Icon At Index:" + i + " Displayed");
			testSteps.add("Verified Edit Icon At Index:" + i + " Displayed");
			assertEquals(elements.productDeleteIconList.get(i).isDisplayed(), true,
					"Failed: Delete Icon didn't Display");

			logger.info("Verified Delete Icon At Index:" + i + " Displayed");
			testSteps.add("Verified Delete Icon At Index:" + i + " Displayed");

		}

		return testSteps;

	}

	public ArrayList<String> verifyProductInProductList(WebDriver driver, String productName,
			boolean sellOnBookingEngine, String category, String costPerOne, String costPerTwo, String price)
			throws InterruptedException {
		Elements_Products elements = new Elements_Products(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> productDetails = new ArrayList<String>();
		if (sellOnBookingEngine) {
			productDetails.add("/inventory/static/media/ok.0d123e15.svg");
		} else {
			productDetails.add("");

		}
		productDetails.add(category);
		productDetails.add("Per " + costPerOne + "/" + "per " + costPerTwo);
		productDetails.add("$" + price);
		logger.info("List To Verify" + productDetails);
		testSteps.add("List To Verify" + productDetails);
		Wait.WaitForElement(driver, OR_Products.PRODUCTS_NAMES_LIST);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PRODUCTS_NAMES_LIST), driver);
		int size = elements.productsNameList.size();

		for (int i = 0; i < size; i++) {

			Utility.ScrollToElement(elements.productsNameList.get(i), driver);
			// if (productName.length() > 8) {
			// productName.subSequence(0, 7)+"..."
			// }
			System.out.println(" NameL:" + elements.productsNameList.get(i).getText());
			System.out.println(" productName:" + productName);

			if (elements.productsNameList.get(i).getText().equalsIgnoreCase(productName)) {
				String details = "(//tbody//tr//td[@class='item-name'])[" + i + "]/following-sibling::td";
				List<WebElement> productDetailsFound = driver.findElements(By.xpath(details));
				int detailsRowSize = productDetailsFound.size() - 2;

				for (int j = 0; j < detailsRowSize; j++) {
					details = "(//tbody//tr//td[@class='item-name'])[" + (i + 1) + "]/following-sibling::td[" + (j + 1)
							+ "]";
					WebElement productDetailFound = driver.findElement(By.xpath(details));
					Wait.WaitForElement(driver, details);
					System.out.println("Expected:" + productDetails.get(j));

					System.out.println("Found:" + productDetailFound.getText());
					assertEquals(productDetailFound.getText(), productDetails.get(j),
							"Failed: Product Details didn't match");
					logger.info("Product Detail:" + productDetailFound.getText() + " Matched");
					testSteps.add("Product Detail:" + productDetailFound.getText() + " Matched");
				}

			}

		}
		logger.info("Complete Product Details Verified");
		testSteps.add("Complete Product Details Verified");

		return testSteps;

	}

	public ArrayList<String> deleteProductFromProductList(WebDriver driver, String productName)
			throws InterruptedException {
		Elements_Products elements = new Elements_Products(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Wait.WaitForElement(driver, OR_Products.PRODUCTS_NAMES_LIST);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PRODUCTS_NAMES_LIST), driver);

		String deleteIcon = "//td[text()='" + productName + "']/following-sibling::td//i[@class='icon-trash']";
		WebElement deleteIconElement = driver.findElement(By.xpath(deleteIcon));
		deleteIconElement.click();
		logger.info("Trash Button Clicked for:" + productName);
		testSteps.add("Trash Button Clicked for:" + productName);
		try {
			Wait.WaitForElement(driver, OR_Products.DELETE_PRODUCT_TITLE);
			assertEquals(elements.deleteProductTitle.getText(), "Delete product?",
					"Failed: Delete product popup title text didn't match");
			logger.info("Title:" + elements.deleteProductTitle.getText() + " Matched");
			testSteps.add("Title:" + elements.deleteProductTitle.getText() + " Matched");
			if (elements.deleteProductDescription.isDisplayed()) {
				assertEquals(elements.deleteProductDescription.getText(),
						"Are you sure you want to delete this product?",
						"Failed: Delete product popup title text didn't match");
				logger.info("Title:" + elements.deleteProductDescription.getText() + " Matched");
				testSteps.add("Title:" + elements.deleteProductDescription.getText() + " Matched");
				elements.deleteProductPopupDeleteButton.click();

				logger.info("Delete Button Clicked");
				testSteps.add("Delete Button Clicked");

			}

		} catch (Exception e) {

			assertEquals(elements.removeProductFromBundleBeforeDeleteDescription.getText(),
					"This product belongs to the following bundles. Please remove the product from these bundles before deleting:",
					"Failed: Delete product popup title text didn't match");
			logger.info("Title:" + elements.removeProductFromBundleBeforeDeleteDescription.getText() + " Matched");
			testSteps.add("Title:" + elements.removeProductFromBundleBeforeDeleteDescription.getText() + " Matched");
			elements.removeProductFromBundleBeforeDeleteOkButton.click();

			logger.info("Ok Button Clicked");
			testSteps.add("Ok Button Clicked");

		}

		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_BUTTON), driver);
		assertEquals(elements.addProductButton.isDisplayed(), true,
				"Failed: Add Product Button  didn't display Outside");
		logger.info(" Add Product Popup Closed");
		testSteps.add("  Add Product Popup Closed");

		return testSteps;

	}

	public ArrayList<String> editButtonClickFromProductList(WebDriver driver, String productName)
			throws InterruptedException {
		Elements_Products elements = new Elements_Products(driver);
		ArrayList<String> testSteps = new ArrayList<String>();

		String editIcon = "//td[text()='" + productName + "']/following-sibling::td//i[@class='icon-edit']";
		Wait.WaitForElement(driver, editIcon);
		Wait.waitForElementToBeVisibile(By.xpath(editIcon), driver);
		WebElement editIconElement = driver.findElement(By.xpath(editIcon));
		editIconElement.click();
		logger.info("Edit Button Clicked for:" + productName);
		testSteps.add("Edit Button Clicked for:" + productName);

		return testSteps;

	}

	public ArrayList<String> updateProductButtonClick(WebDriver driver) throws InterruptedException {
		Elements_Products elements = new Elements_Products(driver);
		ArrayList<String> testSteps = new ArrayList<String>();
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.UPDATE_PRODUCT_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.UPDATE_PRODUCT_BUTTON), driver);
		Utility.ScrollToElement(elements.updateProductButton, driver);
		elements.updateProductButton.click();
		logger.info("Update Product Button Clicked");
		testSteps.add("Update Product Button Clicked");
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_BUTTON), driver);
		assertEquals(elements.addProductButton.isDisplayed(), true,
				"Failed: Add Product Button  didn't display Outside");
		logger.info(" Add Product Popup Closed");
		testSteps.add("  Add Product Popup Closed");
		return testSteps;

	}

	public ArrayList<String> clickAddProductButtonOutside(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Products elements = new Elements_Products(driver);

		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_BUTTON), driver);
		elements.addProductButton.click();
		logger.info("Add Product Button Clicked");
		testSteps.add("Add Product Button Clicked");
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_POP_UP_HEADING), driver);
		assertEquals(elements.addProductPopUpHeading.isDisplayed(), true, "Failed: Add Product Popup didn't display");
		logger.info("Add Product Popup Displayed");
		testSteps.add("Add Product Popup Displayed");
		return testSteps;
	}

	public boolean isProductNameFieldEmpty(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.PRODUCT_NAME);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PRODUCT_NAME), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.PRODUCT_NAME), driver);
		Elements_Products element = new Elements_Products(driver);
		String text = element.productName.getText();
		boolean result = false;
		if (text.equals("")) {
			result = true;
		}
		return result;
	}

	public boolean isCheckBoxEnabled(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.SELL_ON_BOOKING_CHECKBOX);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELL_ON_BOOKING_CHECKBOX), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SELL_ON_BOOKING_CHECKBOX), driver);
		Elements_Products element = new Elements_Products(driver);
		String attribute = element.sellOnBookingCheckBox.getAttribute("class");
		boolean result = false;
		if (attribute.contains("ant-checkbox-checked")) {
			result = true;
		}
		return result;
	}

	public boolean isCategoryFieldEmpty(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_CATEGORY);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_CATEGORY), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_CATEGORY), driver);
		Elements_Products element = new Elements_Products(driver);
		String text = element.addProductCategory.getText();
		boolean result = false;
		if (text.equals("")) {
			result = true;
		}
		return result;
	}

	public boolean isProductCostFieldEmpty(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.PRODUCT_COST);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PRODUCT_COST), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.PRODUCT_COST), driver);
		Elements_Products element = new Elements_Products(driver);
		String text = element.productCost.getText();
		boolean result = false;
		if (text.equals("")) {
			result = true;
		}
		return result;
	}

	public boolean isProductCostPerOneFieldEmpty(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_COST_PER_ONE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_ONE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_ONE), driver);
		Elements_Products element = new Elements_Products(driver);
		String text = element.addProductCostPerOne.getText();
		boolean result = false;
		if (text.equals("")) {
			result = true;
		}
		return result;
	}

	public boolean isProductCostPerTwoFieldEmpty(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_COST_PER_TWO);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_TWO), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_TWO), driver);
		Elements_Products element = new Elements_Products(driver);
		String text = element.addProductCostPerTwo.getText();
		boolean result = false;
		if (text.equals("")) {
			result = true;
		}
		return result;
	}

	public boolean isDescriptionFieldEmpty(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_DESCRIPTION);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_DESCRIPTION), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_DESCRIPTION), driver);
		Elements_Products element = new Elements_Products(driver);
		String text = element.addProductDescription.getText();
		boolean result = false;
		if (text.equals("")) {
			result = true;
		}
		return result;
	}

	public boolean isEnterProductPolicyFieldEmpty(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ENTER_PRODUCT_POLICY);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ENTER_PRODUCT_POLICY), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ENTER_PRODUCT_POLICY), driver);
		Elements_Products element = new Elements_Products(driver);
		String text = element.enterProductPolicy.getText();
		boolean result = false;
		if (text.equals("")) {
			result = true;
		}
		return result;
	}

	public boolean isAddProductButtonDisabled(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ENTER_PRODUCT_POLICY);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ENTER_PRODUCT_POLICY), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ENTER_PRODUCT_POLICY), driver);
		Elements_Products element = new Elements_Products(driver);
		boolean result = false;
		if (element.popUpAddProductButton.isEnabled()) {
			result = false;
		} else {
			result = true;
		}

		return result;
	}

	public boolean isAddProductButtonEnabled(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ENTER_PRODUCT_POLICY);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ENTER_PRODUCT_POLICY), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ENTER_PRODUCT_POLICY), driver);
		Elements_Products element = new Elements_Products(driver);
		boolean result = false;
		if (element.popUpAddProductButton.isEnabled()) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	public boolean isRoomClassAssociationAppeared(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.ROOM_CLASS_ASSOCIATION);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ROOM_CLASS_ASSOCIATION), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ROOM_CLASS_ASSOCIATION), driver);
		return driver.findElements(By.xpath(OR_Products.ROOM_CLASS_ASSOCIATION)).size() > 0;
	}

	public boolean isSelectionAlwaysOrCustomDisabled(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.SELECTION_ALWAYS_OR_CUSTOM);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Elements_Products element = new Elements_Products(driver);
		String attribute = element.selectionAlwaysOrCustom.getAttribute("class");
		boolean result = false;
		if (attribute.contains("enabled")) {
			result = false;
		} else {
			result = true;
		}

		return result;
	}

	public boolean isSelectionAlwaysOrCustomEnabled(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.SELECTION_ALWAYS_OR_CUSTOM);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Elements_Products element = new Elements_Products(driver);
		String attribute = element.selectionAlwaysOrCustom.getAttribute("class");
		boolean result = false;
		if (attribute.contains("enabled")) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	public void choseCustom(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.SELECTION_ALWAYS_OR_CUSTOM);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Elements_Products element = new Elements_Products(driver);
		element.selectionAlwaysOrCustom.click();
		element.selectCustom.click();
	}

	public boolean isMessageDisplayedWithRedColor(WebDriver driver, String color, String message,
			ArrayList<String> testSteps) {
		Wait.WaitForElement(driver, OR_Products.OVERLAPING_MESSAGE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.OVERLAPING_MESSAGE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.OVERLAPING_MESSAGE), driver);
		Elements_Products element = new Elements_Products(driver);
		String elementColor = element.overLapingMessage.getCssValue("color");
		boolean result = false;
		if (elementColor.equals(color)) {
			result = true;
		} else {
			result = false;
		}
		String getMessage = element.overLapingMessage.getText();
		testSteps.add("Expected : " + message);
		testSteps.add("Found : " + getMessage);
		assertEquals(getMessage, message, "Failed to verify message");
		testSteps.add("Message verified");

		return result;
	}

	public ArrayList<String> enterProductName(WebDriver driver, String productName) {
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement(driver, OR_Products.PRODUCT_NAME);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PRODUCT_NAME), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.PRODUCT_NAME), driver);
		Elements_Products element = new Elements_Products(driver);
		element.productName.clear();
		element.productName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.productName.sendKeys(productName);
		logger.info("Product Name:" + productName);
		testSteps.add("Product Name:" + productName);
		return testSteps;

	}

	public ArrayList<String> selectCategory(WebDriver driver, String category) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();

		/*String path = "//li[@label='" + category + "']";*/
		String path = "//div[@label='"+category+"']/div";
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_CATEGORY);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_CATEGORY), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_CATEGORY), driver);
		Elements_Products element = new Elements_Products(driver);
		element.addProductCategory.click();
			String path1="//div[@class='rc-virtual-list-holder-inner']/div/div";
			List<WebElement> elements= driver.findElements(By.xpath(path1));
			for(int i=0;i<elements.size();i++) {
				Utility.ScrollToElement(elements.get(i), driver);
				String cat= elements.get(i).getText();
				if(cat.contains(category)) {
					break;
				}
				}
				
		driver.findElement(By.xpath(path)).click();
		logger.info("Category Selected:" + category);
		testSteps.add("Category Selected:" + category);
		return testSteps;
	}

	public ArrayList<String> enterProductCost(WebDriver driver, String productCost) {
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement(driver, OR_Products.PRODUCT_COST);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PRODUCT_COST), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.PRODUCT_COST), driver);
		Elements_Products element = new Elements_Products(driver);
		element.productCost.clear();
		element.productCost.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.productCost.sendKeys(productCost);
		logger.info("Product Cost:" + productCost);
		testSteps.add("Product Cost:" + productCost);
		return testSteps;

	}

	public ArrayList<String> selectProductCostPerOne(WebDriver driver, String instance) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Products element = new Elements_Products(driver);

		//String path = "//li[@label='" + instance + "']";
		String path = "//div[@label='"+instance+"']/div";
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_COST_PER_ONE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_ONE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_ONE), driver);
		Utility.ScrollToElement(element.addProductCostPerOne, driver);
		Utility.clickThroughAction(driver, element.addProductCostPerOne);
		//element.addProductCostPerOne.click();
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		//driver.findElement(By.xpath(path)).click();
		logger.info("Product Cost Per:" + instance);
		testSteps.add("Product Cost Per:" + instance);
		return testSteps;
	}

	public ArrayList<String> selectProductCostPerTwo(WebDriver driver, String instance) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Products element = new Elements_Products(driver);

		//String path = "//li[@label='" + instance + "']";
		String path = "//div[@label='"+instance+"']/div";
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_COST_PER_TWO);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_TWO), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_COST_PER_TWO), driver);
		Utility.ScrollToElement(element.addProductCostPerTwo, driver);
		//element.addProductCostPerTwo.click();
		Utility.clickThroughAction(driver, element.addProductCostPerTwo);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		//driver.findElement(By.xpath(path)).click();
		logger.info("Product Cost Per:" + instance);
		testSteps.add("Product Cost Per:" + instance);
		return testSteps;
	}

	public ArrayList<String> selectCustomDates(WebDriver driver, String instance) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();

		String path = "//li[@label='" + instance + "']";
		Wait.WaitForElement(driver, OR_Products.SELECTION_ALWAYS_OR_CUSTOM);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Elements_Products element = new Elements_Products(driver);
		element.selectionAlwaysOrCustom.click();
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		driver.findElement(By.xpath(path)).click();

		logger.info("Custom Datee Selected:" + instance);
		testSteps.add("Custom Datee Selected:" + instance);
		return testSteps;
	}

	public ArrayList<String> enterProductDescription(WebDriver driver, String description) {
		ArrayList<String> testSteps = new ArrayList<String>();

		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_DESCRIPTION);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ADD_PRODUCT_DESCRIPTION), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ADD_PRODUCT_DESCRIPTION), driver);
		Elements_Products element = new Elements_Products(driver);
		element.addProductDescription.clear();
		element.addProductDescription.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.addProductDescription.sendKeys(description);
		logger.info("Product Description:" + description);
		testSteps.add("Product Description:" + description);
		return testSteps;

	}

	public void clickOnPlusSign(WebDriver driver) {
		try {
			Wait.WaitForElement(driver, OR_Products.PLUS_CUSTOM_DATE_FIELDS);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PLUS_CUSTOM_DATE_FIELDS), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Products.PLUS_CUSTOM_DATE_FIELDS), driver);
			Elements_Products element = new Elements_Products(driver);

			element.plusCustomDateField.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean isAddProductButtonEnableWithRedMessage(WebDriver driver) {
		Elements_Products element = new Elements_Products(driver);
		boolean result = true;
		if (element.overLapingMessage.isDisplayed()) {
			if (element.popUpAddProductButton.isEnabled()) {
				result = false;
			}
		}
		return result;
	}

	public boolean isPlusSignAppeared(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.PLUS_CUSTOM_DATE_FIELDS);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PLUS_CUSTOM_DATE_FIELDS), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.PLUS_CUSTOM_DATE_FIELDS), driver);
		return driver.findElements(By.xpath(OR_Products.PLUS_CUSTOM_DATE_FIELDS)).size() > 0;
	}

	public boolean isMinusSignAppeared(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.MINUS_CUSTOM_DATE_FIELDS);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS), driver);
		return driver.findElements(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS)).size() > 0;
	}

	public ArrayList<String> clickPopUpAddProductButton(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Products elements = new Elements_Products(driver);

		Wait.WaitForElement(driver, OR_Products.POP_UP_ADD_PRODUCT_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.POP_UP_ADD_PRODUCT_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.POP_UP_ADD_PRODUCT_BUTTON), driver);
		elements.popUpAddProductButton.click();
		logger.info("Add Product Button Clicked");
		testSteps.add("Add Product Button Clicked");
		Wait.WaitForElement(driver, OR_Products.ADD_PRODUCT_BUTTON);
		assertEquals(elements.addProductButton.isDisplayed(), true,
				"Failed: Add Product Button  didn't display Outside");
		logger.info(" Add Product Popup Closed");
		testSteps.add("  Add Product Popup Closed");
		return testSteps;
	}

	public ArrayList<String> createRandomProduct(WebDriver driver, ArrayList<String> incidentals)
			throws InterruptedException {
		ArrayList<String> productDetails = new ArrayList<String>();
		String[] costPerFirstSelectionArray = { "room", "person", "adult", "child" };
		String[] costPerSecondSelectionArray = { "night", "stay", "interval" };
		String productCategory = incidentals
				.get(Integer.parseInt(Utility.GenerateRandomNumber(incidentals.size() - 1)));
		int randomNumber = Utility.getRandomNumber(0, 3);
		String costPerFirstSelection = costPerFirstSelectionArray[randomNumber];
		randomNumber = Utility.getRandomNumber(0, 2);
		String costPerSecondSelection = costPerSecondSelectionArray[randomNumber];

		String productName = Utility.generateRandomStringWithGivenLength(8);
		String productCost = Integer.toString(Utility.getRandomNumber(1, 100));
		String productDescription = Utility.generateRandomStringWithGivenLength(10) + " "
				+ Utility.generateRandomStringWithGivenLength(8);
		;
		clickAddProductButtonOutside(driver);
		enterProductName(driver, productName);
		productDetails.add(productName);
		selectCategory(driver, productCategory);
		enterProductCost(driver, productCost);
		productDetails.add(productCost);
		selectProductCostPerTwo(driver, costPerSecondSelection);
		productDetails.add(costPerSecondSelection);
		selectProductCostPerOne(driver, costPerFirstSelection);
		productDetails.add(costPerFirstSelection);
		enterProductDescription(driver, productDescription);
		clickPopUpAddProductButton(driver);
		return productDetails;

	}

	public List<String>[] createRandomProduct(WebDriver driver, ArrayList<String> incidentals, int numberofProducts)
			throws InterruptedException {
		List<String>[] products = new List[numberofProducts];

		String productCategory = "";
		String[] costPerFirstSelectionArray = { "room", "person", "adult", "child" };
		String[] costPerSecondSelectionArray = { "night", "stay", "interval" };
		int randomNumber = 0;
		String costPerFirstSelection = "";
		String costPerSecondSelection = "";
		String productCost = "";
		String productName = "";
		String productDescription = "";
		for (int i = 0; i < numberofProducts; i++) {
			ArrayList<String> productDetails = new ArrayList<String>();
			productCategory = incidentals.get(Integer.parseInt(Utility.GenerateRandomNumber(incidentals.size() - 1)));
			randomNumber = Utility.getRandomNumber(0, 3);
			costPerFirstSelection = costPerFirstSelectionArray[randomNumber];
			randomNumber = Utility.getRandomNumber(0, 2);
			costPerSecondSelection = costPerSecondSelectionArray[randomNumber];

			productName = Utility.generateRandomStringWithGivenLength(8);
			productCost = Integer.toString(Utility.getRandomNumber(1, 100));
			productDescription = Utility.generateRandomStringWithGivenLength(10) + " "
					+ Utility.generateRandomStringWithGivenLength(8);
			;

			clickAddProductButtonOutside(driver);
			enterProductName(driver, productName);
			productDetails.add(productName);
			selectCategory(driver, productCategory);
			enterProductCost(driver, productCost);
			productDetails.add(productCost);
			selectProductCostPerTwo(driver, costPerSecondSelection);
			productDetails.add(costPerSecondSelection);
			selectProductCostPerOne(driver, costPerFirstSelection);
			productDetails.add(costPerFirstSelection);
			enterProductDescription(driver, productDescription);
			clickPopUpAddProductButton(driver);
			products[i] = productDetails;

		}
		return products;

	}

	public void choseCustomOrAlways(WebDriver driver, String booking) {
		Wait.WaitForElement(driver, OR_Products.SELECTION_ALWAYS_OR_CUSTOM);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SELECTION_ALWAYS_OR_CUSTOM), driver);
		String path = "//li[@label='" + booking + "']";
		Elements_Products elements = new Elements_Products(driver);
		elements.selectionAlwaysOrCustom.click();
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		driver.findElement(By.xpath(path)).click();
	}

	public ArrayList<String> createProduct(WebDriver driver, String productName, String productCost, String productCategory,
			String costPerFirstSelection, String costPerSecondSelection, String productDescription,
			boolean isSellOnBookingEngine, String sellingTypeOnBookingEngine, ArrayList<String> roomClass,
			String dateFormat, String startDate, String endDate) throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<String>();
		testSteps=clickAddProductButtonOutside(driver);
		testSteps=enterProductName(driver, productName);
		testSteps=selectCategory(driver, productCategory);
		testSteps=enterProductCost(driver, productCost);
		testSteps=selectProductCostPerTwo(driver, costPerSecondSelection);
		testSteps=selectProductCostPerOne(driver, costPerFirstSelection);
		testSteps=enterProductDescription(driver, productDescription);
		if (isSellOnBookingEngine) {

			tickCheckboxSellOnBookingEngine(driver);
			if (sellingTypeOnBookingEngine.equalsIgnoreCase("custom dates")) {

				choseCustomOrAlways(driver, sellingTypeOnBookingEngine);
				selectCustomDate(driver, dateFormat, startDate, endDate);

			}

			for (int i = 0; i < roomClass.size(); i++) {

				selectRoomClassAssociation(driver, roomClass.get(i));

			}
		}
		testSteps=clickPopUpAddProductButton(driver);
		return testSteps;
	}

	public void tickCheckboxSellOnBookingEngine(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.SELL_ON_BOOKING_ENGINE_CHECKBOX);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELL_ON_BOOKING_ENGINE_CHECKBOX), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SELL_ON_BOOKING_ENGINE_CHECKBOX), driver);
		Elements_Products elements = new Elements_Products(driver);

		if (elements.sellOnBookingEngineCheckBox.isSelected()) {
			System.out.println("Checkbox is selected");
		} else {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", elements.sellOnBookingEngineCheckBox);
			// element.sellOnBookingEngineCheckBox.click();
			System.out.println("Checkbox is selected");
		}
	}

	public void selectRoomClassAssociation(WebDriver driver, String roomClass) throws InterruptedException {
		String path = "//li[@label='" + roomClass + "']";
		Wait.WaitForElement(driver, OR_Products.ROOM_CLASS_ASSOCIATION);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ROOM_CLASS_ASSOCIATION), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.ROOM_CLASS_ASSOCIATION), driver);
		Elements_Products elements = new Elements_Products(driver);
		elements.roomClassAssociation.click();
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		driver.findElement(By.xpath(path)).click();
	}

	public boolean isCustomDateRangeIsMultiple(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.MINUS_CUSTOM_DATE_FIELDS);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS), driver);
		return driver.findElements(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS)).size() > 1;
	}

	public boolean isDateRangCanBeAdded(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.CUSTOM_DATE_RANGE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Elements_Products elements = new Elements_Products(driver);
		boolean result = false;
		int preDateSize = elements.customDateRange.size();

		clickOnPlusSign(driver);

		int postDateSize = elements.customDateRange.size();
		postDateSize = postDateSize + 2;

		if (preDateSize == postDateSize) {
			result = true;
		}

		return result;
	}

	public void clickOnMinus(WebDriver driver) {
		try {
			Wait.WaitForElement(driver, OR_Products.MINUS_CUSTOM_DATE_FIELDS);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Products.MINUS_CUSTOM_DATE_FIELDS), driver);
			Elements_Products elements = new Elements_Products(driver);
			elements.minusCustomDateField.get(1).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public boolean isNewlyAddedCustomDateRangeCanDelete(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.CUSTOM_DATE_RANGE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Elements_Products elements = new Elements_Products(driver);
		boolean result = false;
		clickOnPlusSign(driver);
		int preDateSize = elements.customDateRange.size();

		clickOnMinus(driver);
		int postDateSize = elements.customDateRange.size();
		preDateSize = preDateSize - 2;
		if (postDateSize == preDateSize) {
			result = true;
		}
		return result;
	}

	public boolean isDateRangeCanNotOverLap(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.CUSTOM_DATE_RANGE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Elements_Products elements = new Elements_Products(driver);
		boolean result = false;
		clickOnPlusSign(driver);
		String endDate = elements.customDateRange.get(2).getText();
		String nextStartDate = elements.customDateRange.get(3).getText();
		if (endDate.trim().equals(nextStartDate.trim())) {
			if (elements.overLapingMessage.isDisplayed()) {

				result = true;
			}
		}

		return result;

	}

	public void clickOnStartDate(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.CUSTOM_DATE_RANGE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Elements_Products elements = new Elements_Products(driver);
		elements.customDateRange.get(0).click();

	}

	public void clickOnEndDate(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.CUSTOM_DATE_RANGE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.CUSTOM_DATE_RANGE), driver);
		Elements_Products elements = new Elements_Products(driver);
		elements.customDateRange.get(1).click();

	}

	public String getMonthFromCalendarHeading1(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.HEADING_MONTH);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.HEADING_MONTH), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.HEADING_MONTH), driver);
		Elements_Products elements = new Elements_Products(driver);

		String month = elements.headingMonth.getText();
		return month;
	}

	public void clickOnCalendarRightArrow1(WebDriver driver) {
		Wait.WaitForElement(driver, OR_Products.HEADING_MONTH);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.HEADING_MONTH), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.HEADING_MONTH), driver);
		Elements_Products elements = new Elements_Products(driver);
		elements.rightArrowOnCalender.click();

	}

	public void selectCustomDate(WebDriver driver, String dateFormat, String startDate, String endDate)
			throws ParseException {

		clickOnStartDate(driver);

		String getMonth = getMonthFromCalendarHeading1(driver);

		boolean isStartMothEqual = false;
		boolean isEndMothEqual = false;

		String expectedMonthStartDate = ESTTimeZone.getDateBaseOnDate(startDate, dateFormat, "MMMM yyyy");
		String getStartDate = ESTTimeZone.getDateBaseOnDate(startDate, dateFormat, "EEE MMM dd yyyy");
		String expectedMonthEndDate = ESTTimeZone.getDateBaseOnDate(endDate, dateFormat, "MMMM yyyy");
		String getEndDate = ESTTimeZone.getDateBaseOnDate(endDate, dateFormat, "EEE MMM dd yyyy");

		String pathStartDate = "//div[contains(@aria-label,'" + getStartDate + "')]";
		String pathEndDate = "//div[contains(@aria-label,'" + getEndDate + "')]";

		System.out.println("path: " + pathStartDate);
		// this is for start date
		while (!isStartMothEqual) {
			if (expectedMonthStartDate.equals(getMonth)) {
				isStartMothEqual = true;
				driver.findElement(By.xpath(pathStartDate)).click();
				break;
			} else {
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);

			}

		}
		clickOnEndDate(driver);

		while (!isEndMothEqual) {
			if (expectedMonthEndDate.equals(getMonth)) {
				isEndMothEqual = true;
				driver.findElement(By.xpath(pathEndDate)).click();
				break;
			} else {
				clickOnCalendarRightArrow1(driver);
				getMonth = getMonthFromCalendarHeading1(driver);

			}

		}

	}
	
	public void clickOnBundlesTab(WebDriver driver) {
		Elements_Products elements = new Elements_Products(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.BUNDLES_TAB), driver);
		elements.bundlesTab.click();
		logger.info("Navigated to Bundles tab");
		
	}
	public void clickOnCreateBundle(WebDriver driver) {
		Elements_Products elements = new Elements_Products(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.CREATE_BUNDLE), driver);
		elements.createBundleButton.click();
		logger.info("Clicked on Create Bundle button");
		
	}
	public void enterBundleOverviewDetails(WebDriver driver,String displayName,String roomClass,String bundleDesc) {
		Elements_Products elements = new Elements_Products(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.BUNDLE_DISPLAY_NAME), driver);
		elements.bundleDisplayName.click();
		//elements.bundleDisplayName.clear();
		elements.bundleDisplayName.sendKeys(displayName);
		logger.info("Entered Bundle display name as: "+displayName);
		//Wait.waitForElementToBeVisibile(By.xpath(OR_Products.SELL_ON_NBE_CHECKBOX), driver);
		elements.sellOnBECheckbox.click();
		
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.ASSOCIATE_ROOMCLASS), driver);
		elements.associateRoomClasses.click();
		String path = "//div[text()='" + roomClass + "']";
		
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		driver.findElement(By.xpath(path)).click();
		
		elements.bundleDisplayName.click();
		elements.bundleDescription.click();
		//elements.bundleDescription.clear();
		elements.bundleDescription.sendKeys(bundleDesc);
		logger.info("Entered Bundle description name as: "+bundleDesc);
		
	}
	public void clickOnProductsAndPricing(WebDriver driver) {
		Elements_Products elements = new Elements_Products(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Products.PRODUCTS_PRICING), driver);
		elements.prodcutsAndPricing.click();
		
	}
	public void selectProductsToBundle(WebDriver driver,String productName) {
		String path="//div[contains(@class,'BundleListBtn') and text()='" + productName + "']//button";
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		driver.findElement(By.xpath(path)).click();
		logger.info("Selected the product: " +productName+ " to the bundle");
		
	}
	public void clickOnSaveButton(WebDriver driver) {
		Elements_Products elements = new Elements_Products(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Products.SAVE_BUTTON), driver);
		elements.saveButton.click();
		Wait.waitForElementToBeInvisibile(By.xpath(OR_Products.BUNDLE_TOASTER_MSG), driver);
		logger.info("Clicked on Save button");
	}
	

}