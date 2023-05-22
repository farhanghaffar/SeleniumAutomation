package com.innroad.inncenter.pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_TaxAndFee;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Tax;

public class TaxesAndFee {

	public static Logger taxLogger = Logger.getLogger("TaxesAndFee");

	public boolean isTaxAndFeeExist(WebDriver driver, int rowIndex, String rowData) {
		String path = "//tbody//td[" + rowIndex + "][contains(text(),'" + rowData + "')]";
		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		return isExist;
	}

	public int getSizeofTaxFeeAvailable(WebDriver driver, int rowIndex, String rowData) {
		String path = "//tbody//td[" + rowIndex + "][contains(text(),'" + rowData + "')]";
		int size = driver.findElements(By.xpath(path)).size();
		return size;
	}

	public void editTaxAndFee(WebDriver driver, int rowIndex, String rowData, int index, ArrayList<String> testSteps)
			throws InterruptedException {
		String path = "//tbody//td[" + rowIndex + "][contains(text(),'" + rowData
				+ "')]/following-sibling::td//button[contains(@class,'edit')]";
		List<WebElement> element = driver.findElements(By.xpath(path));
		Wait.WaitForElement(driver, path);
		Utility.ScrollToElement(element.get(index), driver);
		element.get(index).click();
		testSteps.add("Click on Edit Icon");
		taxLogger.info("Click on Edit Icon");

	}

	public String getTaxAndFeeName(WebDriver driver) {
		Elements_Tax tax = new Elements_Tax(driver);
		String taxName = tax.taxFeeName.getAttribute("value");
		taxLogger.info("Tax Name:- " + taxName);
		return taxName;
	}

	public String getTaxAndFeeLedgerAccount(WebDriver driver) {
		Elements_Tax tax = new Elements_Tax(driver);
		String ledgerAccount = tax.taxFeeName.getAttribute("title");
		taxLogger.info("Tax Name:- " + ledgerAccount);
		return ledgerAccount;
	}

	public HashMap<String, String> getTaxAndFeeAmount(WebDriver driver) {
		Elements_Tax tax = new Elements_Tax(driver);
		HashMap<String, String> taxAmt = new HashMap<String, String>();
		String taxAmount = "";
		String valueIs = tax.taxFeeAmount.getAttribute("value");
		if (valueIs.contains("%")) {
			for (int i = 0; i < valueIs.length(); i++) {
				if (valueIs.charAt(i) > 47 && valueIs.charAt(i) < 58) {
					taxAmount = taxAmount + valueIs.charAt(i);

				}
			}
			taxAmt.put("Percentage", taxAmount);
			taxLogger.info("Tax Amount is Percentage Type:- " + taxAmount);
		} else {
			taxAmount = valueIs;
			taxAmt.put("Flat", taxAmount);
			taxLogger.info("Tax Amount is flat Type:- " + taxAmount);
		}

		return taxAmt;
	}

	public void taxVatCheckBox(WebDriver driver, boolean checkBox, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_TaxAndFee.taxVatchecked));
		if (checkBox) {
			if (!isExist) {
				Utility.ScrollToElement(tax.taxVatOption, driver);
				tax.taxVatOption.click();
				taxLogger.info("Checked Vat Option");
				testSteps.add("Checked Vat Option");
			} else {
				taxLogger.info("Vat Option Already Checked");
				testSteps.add("Vat Option Already Checked");
			}
		} else {
			if (isExist) {
				Utility.ScrollToElement(tax.taxVatOption, driver);
				tax.taxVatOption.click();
				taxLogger.info("UnChecked Vat Option");
				testSteps.add("UnChecked Vat Option");
			} else {
				taxLogger.info("Vat Option Already UnChecked");
				testSteps.add("Vat Option Already UnChecked");
			}
		}

	}

	public void clickSaveButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		Wait.WaitForElement(driver, (OR_TaxAndFee.saveButotn));
		Utility.ScrollToElement(tax.saveButotn, driver);
		try {
			tax.saveButotn.click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", tax.saveButotn);
		}
		taxLogger.info("Click Save button");
		testSteps.add("Click Save button");
	}

	public void clickCreateButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_TaxAndFee.createButton), driver);
		test_steps.add("Click on Create Button Button");
		tax.createButton.click();
		Wait.wait5Second();
	}

	public void clickCreateTax(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Tax tax = new Elements_Tax(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_TaxAndFee.createTax), driver);
		tax.createTax.click();
		test_steps.add("Clicked on Create Tax item");
	}

	public void clickCreateFees(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_TaxAndFee.createFees), driver);
		Utility.ScrollToElement(tax.createFees, driver);
        try {		
		tax.createFees.click();
        }catch(Exception e) {
        	Utility.clickThroughJavaScript(driver, tax.createFees);
        }
		test_steps.add("Clicked on Create Fees item");
	}

	public void deleteAllTaxesAndFee(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		int count = driver.findElements(By.xpath("//button[@class='icon-delete b-none']")).size();
		if (count > 0) {
			for (int i = 0; i <= count - 1; i++) {
				tax.deleteTaxIcon.click();
				Wait.waitForElementToBeClickable(By.xpath(OR_TaxAndFee.confirmDeleteTax), driver);
				tax.confirmDeleteTax.click();
				Wait.wait5Second();
			}
			taxLogger.info("All the available Taxes are deleted");
			test_steps.add("All the available Taxes are deleted");
		} else {
			taxLogger.info("No Taxes available to delete");
			test_steps.add("No Taxes available to delete");
		
		}

	}

	public void createFee(WebDriver driver, ArrayList<String> testSteps, String name, String displayName,
			String ledgerAccountType, String description, String chargeType, String amount, boolean restrictToggle,
			String roomClass, String ratePlan, String source) throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		clickCreateButton(driver, testSteps);
		clickCreateFees(driver, testSteps);
		Wait.WaitForElement(driver, OR_TaxAndFee.taxFeeName);
		tax.taxFeeName.clear();
		tax.taxFeeName.sendKeys(name);
		taxLogger.info("New Fee Name : " + name);
		testSteps.add("New Fee Name : " + name);
		tax.displayName.clear();
		tax.displayName.sendKeys(displayName);
		taxLogger.info("New Fee Dispaly Name : " + displayName);
		testSteps.add("New Fee Dispaly Name : " + displayName);
		tax.description.clear();
		tax.description.sendKeys(description);
		taxLogger.info("New Fee description : " + description);
		testSteps.add("New Fee description : " + description);
		tax.tax_ddl.click();
		Wait.wait1Second();
		String taxType = "//div[text()='"+ledgerAccountType+"']";
/*		Wait.waitForElementToBeVisibile(By.xpath(taxType), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(taxType)), driver);
		driver.findElement(By.xpath(taxType)).click();
		driver.findElement(By.xpath(OR_TaxAndFee.tax_ddl)).sendKeys(Keys.TAB);
		
	*/	Wait.waitForElementToBeVisibile(By.xpath(taxType), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(taxType)), driver);
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(taxType)));
		
		taxLogger.info("Selected Fee Value : " + ledgerAccountType);
		testSteps.add("Selected Fee Value : " + ledgerAccountType);
		if (chargeType.contains("flat") || chargeType.contains("Flat")) {
			tax.taxFeeAmount.clear();
			tax.taxFeeAmount.sendKeys(amount);
			tax.taxFeeAmount.sendKeys(Keys.TAB);
			taxLogger.info("Entered flat value as " + amount);
			testSteps.add("Entered flat value as " + amount);
		} else if (chargeType.contains("percent") || chargeType.contains("Percent")) {
		try {
			Wait.WaitForElement(driver, "//label[text()='Flat/Percent of Room Charge']/..//div[@class='ant-select-selector']");
				Wait.waitForElementToBeVisibile(By.xpath("//label[text()='Flat/Percent of Room Charge']/..//div[@class='ant-select-selector']"), driver);
				driver.findElement(By.xpath("//label[text()='Flat/Percent of Room Charge']/..//div[@class='ant-select-selector']")).click();
				driver.findElement(By.xpath(OR_TaxAndFee.selectPercentCtyFee)).click();
			} catch (Exception e) {
				Wait.waitForElementToBeVisibile(By.xpath("(//span[@class='ant-select-selection-item'])[3]"), driver);
				driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[3]")).click();
				Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@class,'ant-select-item-option-content') and text()='percentage']"), driver);
				driver.findElement(By.xpath(OR_TaxAndFee.selectPercentCtyFee)).click();
			}
			tax.taxFeeAmount.clear();
			tax.taxFeeAmount.sendKeys(amount);
			tax.taxFeeAmount.sendKeys(Keys.TAB);
			taxLogger.info("Entered percent value as " + amount);
			testSteps.add("Entered percent value as " + amount);
		}
		if (restrictToggle) {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).perform();
			Utility.ScrollToElement(tax.restrict_Toggle, driver);
			tax.restrict_Toggle.click();
			taxLogger.info("Enabled on Restrict Toggle ");
			testSteps.add("Enabled on Restrict Toggle ");
			Utility.ScrollToElement(tax.addRoomClass, driver);
			tax.addRoomClass.click();
			taxLogger.info("Click Add Room Class Icon");
			testSteps.add("Click Add Room Class Icon ");
			String clearRoomClass = "//label[text()='Select room class']/../..//div[@class='ant-select-selection-overflow-item']";
			Wait.waitForElementToBeVisibile(By.xpath(clearRoomClass), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(clearRoomClass)));
			driver.findElement(By.xpath(clearRoomClass)).sendKeys(Keys.BACK_SPACE);
			taxLogger.info("Clear existing Room Class ");
			String selectRoomClass = "//div[@class='ant-select-item ant-select-item-option undefined' and @label='"
					+ roomClass + "']";
			Wait.waitForElementToBeVisibile(By.xpath(selectRoomClass), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(selectRoomClass)), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRoomClass)));
			action.sendKeys(Keys.ESCAPE).perform();
			String addButton1 = "//label[text()='Select room class']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton1), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton1)), driver);
			driver.findElement(By.xpath(addButton1)).click();
			taxLogger.info("Select Room Class " + roomClass);
			testSteps.add("Select Room Class  " + roomClass);
			Utility.ScrollToElement(tax.addRatePlan, driver);
			tax.addRatePlan.click();
			taxLogger.info("Click Add Rate Plan Icon");
			testSteps.add("Click Add Rate Plan  Icon ");
			String clearRatePlan = "//label[text()='Select rate plan(s)']/../..//div[@class='ant-select-selection-overflow-item']";
			Wait.waitForElementToBeVisibile(By.xpath(clearRatePlan), driver);
			driver.findElement(By.xpath(clearRatePlan)).click();
			driver.findElement(By.xpath(clearRatePlan)).sendKeys(Keys.BACK_SPACE);
			String selectRatePlan = "//div[@class='ant-select-item ant-select-item-option undefined' and @label='"
					+ ratePlan + "']";
			List<WebElement> ratePlans = driver.findElements(By.xpath(selectRatePlan));
			int size = ratePlans.size();
			if (size > 1) {
				String selectRatePlan1 = "(//div[@class='ant-select-item ant-select-item-option undefined' and @label='"
						+ ratePlan + "'])[2]";
				Wait.waitForElementToBeVisibile(By.xpath(selectRatePlan1), driver);
				Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRatePlan1)));
				action.sendKeys(Keys.ESCAPE).perform();
			} else {
				Wait.waitForElementToBeVisibile(By.xpath(selectRatePlan), driver);
				Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRatePlan)));
				action.sendKeys(Keys.ESCAPE).perform();
			}
			taxLogger.info("Clear existing Rate Plan ");
			String addButton2 = "//label[text()='Select rate plan(s)']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton2), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton2)), driver);
			driver.findElement(By.xpath(addButton2)).click();
			taxLogger.info("Select Rate Plan " + ratePlan);
			testSteps.add("Select Rate Plan  " + ratePlan);
			Utility.ScrollToElement(tax.addSource, driver);
			tax.addSource.click();
			taxLogger.info("Click Add Source Icon");
			testSteps.add("Click Add Source Icon ");
			String clearSource = "//label[text()='Select source(s)']/../..//div[@class='ant-select-selection-overflow-item']";
			Wait.waitForElementToBeVisibile(By.xpath(clearSource), driver);
			driver.findElement(By.xpath(clearSource)).click();
			driver.findElement(By.xpath(clearSource)).sendKeys(Keys.BACK_SPACE);
			String selectSource = "//div[@class='ant-select-item ant-select-item-option undefined' and @label='"
					+ source + "']";
			Wait.waitForElementToBeVisibile(By.xpath(selectSource), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectSource)));
			action.sendKeys(Keys.ESCAPE).perform();
			String addButton3 = "//label[text()='Select source(s)']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton3), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton3)), driver);
			driver.findElement(By.xpath(addButton3)).click();
			taxLogger.info(" Add Source ");
			testSteps.add(" Add Source  ");
		}
		clickSaveButton(driver, testSteps);
		Wait.waitForElementToBeVisibile(By.xpath(OR_TaxAndFee.createButton), driver);
	}

	public void createTaxes(WebDriver driver, String taxName, String displayName, String description, String Type,
			String category, String categoryValue, String taxExempt, String ledgerAccount, boolean restrictToggle,
			String RoomClass, String RatePlan, String Source, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		clickCreateButton(driver, test_steps);
		clickCreateTax(driver, test_steps);
		Wait.WaitForElement(driver, OR_TaxAndFee.taxFeeName);
		tax.taxFeeName.clear();
		tax.taxFeeName.sendKeys(taxName);
		taxLogger.info("New Tax Name : " + taxName);
		test_steps.add("New Tax Name : " + taxName);
		tax.displayName.clear();
		tax.displayName.sendKeys(displayName);
		taxLogger.info("New Tax Dispaly Name : " + displayName);
		test_steps.add("New Tax Dispaly Name : " + displayName);
		tax.description.clear();
		tax.description.sendKeys(description);
		taxLogger.info("New Tax description : " + description);
		test_steps.add("New Tax description : " + description);
		Wait.WaitForElement(driver, OR_TaxAndFee.tax_ddl);
		tax.tax_ddl.click();
		String taxType = "//div[text()='" + Type + "']";
		Wait.waitForElementToBeVisibile(By.xpath(taxType), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(taxType)), driver);
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(taxType)));
		//driver.findElement(By.xpath(taxType)).click();
		taxLogger.info("Selected Tax Value : " + Type);
		test_steps.add("Selected Tax Value : " + Type);
		if (category.equalsIgnoreCase("vat")) {
			Wait.explicit_wait_visibilityof_webelement(tax.vat_checkbox, driver);
			Utility.ScrollToElement(tax.vat_checkbox, driver);
			tax.vat_checkbox.click();
			taxLogger.info("VAT is clicked");
			test_steps.add("VAT is clicked");

			tax.taxFeeAmount.clear();
			tax.taxFeeAmount.sendKeys(categoryValue);

			taxLogger.info("Entered VAT value as " + categoryValue);
			test_steps.add("Entered VAT value as " + categoryValue);

		} else if (category.equalsIgnoreCase("flat")) {
			tax.taxFeeAmount.clear();
			tax.taxFeeAmount.sendKeys(categoryValue);

			taxLogger.info("Entered flat value as " + categoryValue);
			test_steps.add("Entered flat value as " + categoryValue);
		} else if (category.equalsIgnoreCase("percent")) {
			Wait.waitForElementToBeVisibile(By.xpath("//label[text()='Tax Type']/..//div[@class='ant-select-selector']"), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath("//label[text()='Tax Type']/..//div[@class='ant-select-selector']")),
					driver);
			driver.findElement(By.xpath("//label[text()='Tax Type']/..//div[@class='ant-select-selector']")).click();

			Wait.waitForElementToBeVisibile(By.xpath(OR_TaxAndFee.selectPercentCty), driver);
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(OR_TaxAndFee.selectPercentCty)));
			tax.taxFeeAmount.clear();
			tax.taxFeeAmount.sendKeys(categoryValue);

			taxLogger.info("Entered percent value as " + categoryValue);
			test_steps.add("Entered percent value as " + categoryValue);
		}
		if (taxExempt.equalsIgnoreCase("Yes")) {
			Wait.explicit_wait_visibilityof_webelement(tax.excludeTaxExcept_checkbox, driver);
			Utility.ScrollToElement(tax.excludeTaxExcept_checkbox, driver);
			tax.excludeTaxExcept_checkbox.click();
			taxLogger.info("Exclude when tax exempt is clicked");
			test_steps.add("Exclude when tax exempt is clicked");
		}

		Utility.ScrollToElement(tax.addledgerAccounts, driver);
		tax.addledgerAccounts.click();
		String selectAccountsBox = "//label[text()='Select ledger account(s)']/../..//div[@class='ant-select ant-select-multiple ant-select-show-search']";
		Wait.waitForElementToBeVisibile(By.xpath(selectAccountsBox), driver);
		driver.findElement(By.xpath(selectAccountsBox)).click();
		String selectAccountType = "//div[text()='"+ledgerAccount+"']";
		Wait.WaitForElement(driver, selectAccountType);
		Wait.waitForElementToBeVisibile(By.xpath(selectAccountType), driver);
		driver.findElement(By.xpath(selectAccountType)).click();
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).perform();
		String addButton = "//label[text()='Select ledger account(s)']/../../../..//button//span[text()='Add']/..";
		Wait.waitForElementToBeClickable(By.xpath(addButton), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(addButton)), driver);
		driver.findElement(By.xpath(addButton)).click();

		taxLogger.info("Added ledger account as" + ledgerAccount);
		test_steps.add("Added ledger account as" + ledgerAccount);

		if (restrictToggle) {
			Utility.ScrollToElement(tax.restrict_Toggle, driver);
			tax.restrict_Toggle.click();
			Utility.ScrollToElement(tax.addRoomClass, driver);
			tax.addRoomClass.click();
			String clearRoomClass = "//label[text()='Select room class']/../..//input[@class='ant-select-search__field']";
			Wait.waitForElementToBeVisibile(By.xpath(clearRoomClass), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(clearRoomClass)));
			driver.findElement(By.xpath(clearRoomClass)).sendKeys(Keys.BACK_SPACE);
			String selectRoomClass = "//li[@class='ant-select-dropdown-menu-item undefined' and @label='" + RoomClass
					+ "']";
			Wait.waitForElementToBeVisibile(By.xpath(selectRoomClass), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(selectRoomClass)), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRoomClass)));
			action.sendKeys(Keys.ESCAPE).perform();
			String addButton1 = "//label[text()='Select room class']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton1), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton1)), driver);
			driver.findElement(By.xpath(addButton1)).click();

			taxLogger.info("Added Room Class as" + RoomClass);
			test_steps.add("Added Room Class as" + RoomClass);

			Utility.ScrollToElement(tax.addRatePlan, driver);
			tax.addRatePlan.click();
			String clearRatePlan = "//label[text()='Select rate plan(s)']/../..//input[@class='ant-select-search__field']";
			Wait.waitForElementToBeVisibile(By.xpath(clearRatePlan), driver);
			driver.findElement(By.xpath(clearRatePlan)).click();
			driver.findElement(By.xpath(clearRatePlan)).sendKeys(Keys.BACK_SPACE);
			String selectRatePlan = "//li[@class='ant-select-dropdown-menu-item undefined' and @label='" + RatePlan
					+ "']";
			Wait.waitForElementToBeVisibile(By.xpath(selectRatePlan), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRatePlan)));
			action.sendKeys(Keys.ESCAPE).perform();
			String addButton2 = "//label[text()='Select rate plan(s)']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton2), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton2)), driver);
			driver.findElement(By.xpath(addButton2)).click();
			taxLogger.info("Added Rate Plan as" + RatePlan);
			test_steps.add("Added Rate Plan as" + RatePlan);
			Utility.ScrollToElement(tax.addSource, driver);
			tax.addSource.click();
			String clearSource = "//label[text()='Select source(s)']/../..//input[@class='ant-select-search__field']";
			Wait.waitForElementToBeVisibile(By.xpath(clearSource), driver);
			driver.findElement(By.xpath(clearSource)).click();
			driver.findElement(By.xpath(clearSource)).sendKeys(Keys.BACK_SPACE);
			String selectSource = "//li[@class='ant-select-dropdown-menu-item undefined' and @label='" + Source + "']";
			Wait.waitForElementToBeVisibile(By.xpath(selectSource), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectSource)));
			action.sendKeys(Keys.ESCAPE).perform();
			String addButton3 = "//label[text()='Select source(s)']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton3), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton3)), driver);
			driver.findElement(By.xpath(addButton3)).click();
			taxLogger.info("Added Source as" + Source);
			test_steps.add("Added Source as" + Source);
		}
		clickSaveButton(driver, test_steps);
		Wait.WaitForElement(driver, OR_TaxAndFee.createButton);
	}

	public void deleteTaxAndFee(WebDriver driver, String name, ArrayList<String> test_steps)
			throws InterruptedException {
		try {
			String path = "//tbody//td[contains(text(),'" + name
					+ "')]/following-sibling::td[5]//button[contains(@class,'delete')]";
			List<WebElement> element = driver.findElements(By.xpath(path));

			Wait.WaitForElement(driver, path);
			Wait.waitForElementToBeVisibile(By.xpath(path), driver);

			for (int i = 0; i < element.size(); i++) {
				Utility.ScrollToElement(element.get(i), driver);
				element.get(i).click();
				clickConfirmEditButton(driver);
			}
			taxLogger.info("Taxes get deleted start from " + name);
			test_steps.add("Taxes get deleted start from " + name);
			Wait.wait10Second();
			Wait.waitforPageLoad(30, driver);
		} catch (Exception e) {
			String path = "//tbody//td[contains(text(),'" + name
					+ "')]/following-sibling::td[5]//button[contains(@class,'delete')]";
			List<WebElement> element = driver.findElements(By.xpath(path));

			Wait.WaitForElement(driver, path);
			Wait.waitForElementToBeVisibile(By.xpath(path), driver);

			for (int i = 0; i < element.size(); i++) {
				Utility.ScrollToElement(element.get(i), driver);
				element.get(i).click();
				clickConfirmEditButton(driver);
			}
			taxLogger.info("Taxes get deleted start from " + name);
			test_steps.add("Taxes get deleted start from " + name);
			Wait.wait10Second();
			Wait.waitforPageLoad(30, driver);
		}
	}

	public void clickBackIcon(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		Wait.WaitForElement(driver, OR_TaxAndFee.backIcon);
		Utility.ScrollToElement(tax.backIcon, driver);
		tax.backIcon.click();
		test_steps.add("Clicked on Back Icon");
		taxLogger.info("Clicked on Back Icon");
		Wait.waitForElementToBeVisibile(By.xpath(OR_TaxAndFee.createButton), driver);
	}

	public int getTotalItemOnTaxesAndFeePage(WebDriver driver) {
		Elements_Tax tax = new Elements_Tax(driver);
		int totalItem = 0;
		try {
			totalItem = tax.totalItemOnTaxesSize.size();
		} catch (Exception e) {
			taxLogger.info("catch");
		}
		return totalItem;
	}

	public boolean isTaxOrFeeItemExist(WebDriver driver) {
		Elements_Tax tax = new Elements_Tax(driver);
		boolean isExist = true;
		try {
			if ((tax.TotalItemOnTaxesAndFeePage.isDisplayed())) {
				isExist = false;
			} else {
				taxLogger.info("Else");
				isExist = true;
			}
		} catch (Exception e) {
			taxLogger.info("Exception");
		} catch (Error e) {
			taxLogger.info("Error");
		}
		return isExist;

	}

	public void makingInactiveORActiveOnAllExistingItem(WebDriver driver, String inactive, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		int itemSize = getTotalItemOnTaxesAndFeePage(driver);
		for (int i = 1; i <= itemSize; i++) {
			String clickEdit = "//tbody[@class='ant-table-tbody']/tr/td/following::div[@class='d-flex']/button[@class='icon-edit mr-25']";

			WebElement clickEle = driver.findElement(By.xpath(clickEdit));
			Wait.waitForElementToBeVisibile(By.xpath(clickEdit), driver);
			Wait.waitForElementToBeClickable(By.xpath(clickEdit), driver);
			clickEle.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR.BackLink), driver);
			if (inactive.equalsIgnoreCase("inactive")) {
				tax.makeInactive.click();

			} else {
				tax.makeActive.click();

			}
			clickSaveButton(driver, testSteps);
			clickConfirmEditButton(driver);
		}
	}

	public void clickConfirmEditButton(WebDriver driver) {
		Elements_Tax tax = new Elements_Tax(driver);

		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR.ClickConfirm1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.ClickConfirm1), driver);
			// tax.clickConfirm1.click();
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", tax.clickConfirm1);

		} catch (Exception e) {		
			Wait.waitForElementToBeVisibile(By.xpath(OR.ClickConfirm1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.ClickConfirm1), driver);
			tax.clickConfirm1.click();
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR.TaxPageSetupTitle), driver);
	}
	public void createFeeWithNightOrStay(WebDriver driver, ArrayList<String> testSteps, String name, String displayName,
			String ledgerAccountType, String description, String chargeType, String amount, boolean restrictToggle,
			String roomClass, String ratePlan, String source, String nightOrStayFee) throws InterruptedException {

		Elements_Tax tax = new Elements_Tax(driver);
		clickCreateButton(driver, testSteps);
		clickCreateFees(driver, testSteps);
		Wait.WaitForElement(driver, OR_TaxAndFee.taxFeeName);
		tax.taxFeeName.clear();
		tax.taxFeeName.sendKeys(name);
		taxLogger.info("New Fee Name : " + name);
		testSteps.add("New Fee Name : " + name);
		tax.displayName.clear();
		tax.displayName.sendKeys(displayName);
		taxLogger.info("New Fee Dispaly Name : " + displayName);
		testSteps.add("New Fee Dispaly Name : " + displayName);
		tax.description.clear();
		tax.description.sendKeys(description);
		taxLogger.info("New Fee description : " + description);
		testSteps.add("New Fee description : " + description);
		tax.tax_ddl.click();
		String taxType = "//div[contains(@class,'ant-select-item-option-content') and text()='"+ledgerAccountType+"']";
		taxLogger.info("Ledger Account : " + ledgerAccountType);
		testSteps.add("Ledger Account : " + ledgerAccountType);
		Wait.waitForElementToBeVisibile(By.xpath(taxType), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(taxType)), driver);
		driver.findElement(By.xpath(taxType)).click();
		taxLogger.info("Selected Fee Value : " + ledgerAccountType);
		testSteps.add("Selected Fee Value : " + ledgerAccountType);
		if (chargeType.contains("flat") || chargeType.contains("Flat")) {
			tax.taxFeeAmount.clear();
			tax.taxFeeAmount.sendKeys(amount);
			tax.taxFeeAmount.sendKeys(Keys.TAB);
			taxLogger.info("Entered flat value as " + amount);
			testSteps.add("Entered flat value as " + amount);
			if(Utility.validateString(nightOrStayFee)) {
				if(nightOrStayFee.equalsIgnoreCase("per stay")) {
					 Wait.waitForElementToBeVisibile(By.xpath("(//span[@class='ant-select-selection-item'])[3]"), driver);
					 driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[3]")).click();
					 driver.findElement(By.xpath(OR_TaxAndFee.SelectPerStayFee)).click();
					}
			}
		} else if (chargeType.contains("percent") || chargeType.contains("Percent")) {
				try {
			    Wait.waitForElementToBeVisibile(By.xpath("(//span[@class='ant-select-selection-item'])[2]"), driver);
			    driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[2]")).click();
			    driver.findElement(By.xpath(OR_TaxAndFee.selectPercentCtyFee)).click();
			    driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[2]")).sendKeys(Keys.TAB);
			 }catch(Exception e) {
	 }
			
				tax.taxFeeAmount.clear();
			tax.taxFeeAmount.sendKeys(amount);
			taxLogger.info("Entered percent value as " + amount);
			testSteps.add("Entered percent value as " + amount);
			if(Utility.validateString(nightOrStayFee)) {
				if(nightOrStayFee.equalsIgnoreCase("per stay")) {
					Wait.WaitForElement(driver, "(//span[@class='ant-select-selection-item'])[3]");
					Wait.waitForElementToBeVisibile(By.xpath("(//span[@class='ant-select-selection-item'])[3]"), driver);						
					driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[3]")).click();
					driver.findElement(By.xpath(OR_TaxAndFee.SelectPerStayFee)).click();
				 //   driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[3]")).sendKeys(Keys.TAB);
						}
			}
		}

		if (restrictToggle) {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).perform();
			Utility.ScrollToElement(tax.restrict_Toggle, driver);
			tax.restrict_Toggle.click();
			taxLogger.info("Enabled on Restrict Toggle ");
			testSteps.add("Enabled on Restrict Toggle ");
			Utility.ScrollToElement(tax.addRoomClass, driver);
			tax.addRoomClass.click();
			taxLogger.info("Click Add Room Class Icon");
			testSteps.add("Click Add Room Class Icon ");
			String clearRoomClass = "//label[text()='Select room class']/../..//div[@class='ant-select-selection-overflow-item']";
			Wait.waitForElementToBeVisibile(By.xpath(clearRoomClass), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(clearRoomClass)));
			driver.findElement(By.xpath(clearRoomClass)).sendKeys(Keys.BACK_SPACE);
			taxLogger.info("Clear existing Room Class ");
			String selectRoomClass = "//div[@class='ant-select-item ant-select-item-option undefined' and @label='"+roomClass+"']";
			Wait.waitForElementToBeVisibile(By.xpath(selectRoomClass), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(selectRoomClass)), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRoomClass)));
			action.sendKeys(Keys.ESCAPE).perform();

			String addButton1 = "//label[text()='Select room class']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton1), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton1)), driver);
			driver.findElement(By.xpath(addButton1)).click();

			taxLogger.info("Select Room Class " + roomClass);
			testSteps.add("Select Room Class  " + roomClass);

			Utility.ScrollToElement(tax.addRatePlan, driver);
			tax.addRatePlan.click();
			taxLogger.info("Click Add Rate Plan Icon");
			testSteps.add("Click Add Rate Plan  Icon ");

			String clearRatePlan = "//label[text()='Select rate plan(s)']/../..//div[@class='ant-select-selection-overflow-item']";
			Wait.waitForElementToBeVisibile(By.xpath(clearRatePlan), driver);
			driver.findElement(By.xpath(clearRatePlan)).click();
			driver.findElement(By.xpath(clearRatePlan)).sendKeys(Keys.BACK_SPACE);

			String selectRatePlan = "//div[@class='ant-select-item ant-select-item-option undefined' and @label='"+ratePlan+"']";
			List<WebElement> ratePlans=driver.findElements(By.xpath(selectRatePlan));
			int size=ratePlans.size();
			if(size>1) {
				String selectRatePlan1 = "(//div[@class='ant-select-item ant-select-item-option undefined' and @label='"+ratePlan+"'])[2]";
				Wait.waitForElementToBeVisibile(By.xpath(selectRatePlan1), driver);
				Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRatePlan1)));
				action.sendKeys(Keys.ESCAPE).perform();
			}else {
				Wait.waitForElementToBeVisibile(By.xpath(selectRatePlan), driver);
				Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectRatePlan)));
				action.sendKeys(Keys.ESCAPE).perform();
			}
			

			taxLogger.info("Clear existing Rate Plan ");

			String addButton2 = "//label[text()='Select rate plan(s)']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton2), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton2)), driver);
			driver.findElement(By.xpath(addButton2)).click();

			taxLogger.info("Select Rate Plan " + ratePlan);
			testSteps.add("Select Rate Plan  " + ratePlan);

			Utility.ScrollToElement(tax.addSource, driver);
			tax.addSource.click();

			taxLogger.info("Click Add Source Icon");
			testSteps.add("Click Add Source Icon ");
			String clearSource = "//label[text()='Select source(s)']/../..//div[@class='ant-select-selection-overflow-item']";
			Wait.waitForElementToBeVisibile(By.xpath(clearSource), driver);
			driver.findElement(By.xpath(clearSource)).click();
			driver.findElement(By.xpath(clearSource)).sendKeys(Keys.BACK_SPACE);

			String selectSource = "//div[@class='ant-select-item ant-select-item-option undefined' and @label='"+source+"']";
			Wait.waitForElementToBeVisibile(By.xpath(selectSource), driver);
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(selectSource)));

			action.sendKeys(Keys.ESCAPE).perform();

			String addButton3 = "//label[text()='Select source(s)']/../../../..//button//span[text()='Add']/..";
			Wait.waitForElementToBeClickable(By.xpath(addButton3), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(addButton3)), driver);
			driver.findElement(By.xpath(addButton3)).click();
			taxLogger.info(" Add Source ");
			testSteps.add(" Add Source  ");
		}

		clickSaveButton(driver, testSteps);
		Wait.waitForElementToBeVisibile(By.xpath(OR_TaxAndFee.createButton), driver);
	}
	
	
	public void inactiveTax(WebDriver driver, String taxName, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		String path = "//*[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+taxName.toUpperCase()+"']/following-sibling::td//button[@class='icon-edit mr-25']";
		try {
			Wait.WaitForElement(driver, path);
			driver.findElement(By.xpath(path)).click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}		
		testSteps.add("Click Edit Icon For : " + taxName);
		taxLogger.info("Click Edit Icon For : " + taxName);		
		tax.makeInactive.click();
		testSteps.add("Select Inactive");
		taxLogger.info("Select Inactive");					
		clickSaveButton(driver, testSteps);
		testSteps.add("Click Save");
		taxLogger.info("Click Save");	
		clickConfirmEditButton(driver);
		testSteps.add("Click Confirm");
		taxLogger.info("Click Confirm");			
	}
	
	public void activeTax(WebDriver driver, String taxName, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		boolean isExist=Utility.isElementPresent(driver, By.xpath(OR.taxFilterIcon));
		if(isExist) {
			Wait.WaitForElement(driver, OR.taxFilterIcon);
			Utility.ScrollToElement(tax.taxFilterIcon, driver);
			tax.taxFilterIcon.click();
			testSteps.add("Click Filter Icon " );
			taxLogger.info("Click Filter Icon" );
			Utility.ScrollToElement(tax.taxFilterStatus, driver);
			tax.taxFilterStatus.click();
			String inactivePath="//div[@class='ant-select-item-option-content' and text()='Inactive']";
			Wait.WaitForElement(driver, inactivePath);
			driver.findElement(By.xpath(inactivePath)).click();
			testSteps.add("Select Inactive Status " );
			taxLogger.info("Select Inactive Status " );
			Wait.waitforPageLoad(30, driver);
		}
		String path = "//*[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+taxName.toUpperCase()+"']/following-sibling::td//button[@class='icon-edit mr-25']";
		try {
			Wait.WaitForElement(driver, path);
			driver.findElement(By.xpath(path)).click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}		
		testSteps.add("Click Edit Icon For : " + taxName);
		taxLogger.info("Click Edit Icon For : " + taxName);		
		tax.makeActive.click();
		testSteps.add("Select Active");
		taxLogger.info("Select Active");					
		clickSaveButton(driver, testSteps);
		testSteps.add("Click Save");
		taxLogger.info("Click Save");	
		clickConfirmEditButton(driver);
		testSteps.add("Click Confirm");
		taxLogger.info("Click Confirm");			
	}
	
	public void deleteTaxesAndFee(WebDriver driver,String name, ArrayList<String> test_steps) throws InterruptedException {
		Elements_Tax tax = new Elements_Tax(driver);
		int count = driver.findElements(By.xpath("//td[text()='"+name+"']/..//button[@class='icon-delete b-none']")).size();
		if (count > 0) {
			for (int i = 0; i <= count - 1; i++) {
				driver.findElement(By.xpath("//td[text()='"+name+"']/..//button[@class='icon-delete b-none']")).click();
				Wait.waitForElementToBeClickable(By.xpath(OR_TaxAndFee.confirmDeleteTax), driver);
				tax.confirmDeleteTax.click();
				Wait.wait5Second();
			}
			taxLogger.info("All the available Taxes are deleted");
			test_steps.add("All the available Taxes are deleted");
		} else {
			taxLogger.info("No Taxes available to delete");
			test_steps.add("No Taxes available to delete");
		}

	}
}
