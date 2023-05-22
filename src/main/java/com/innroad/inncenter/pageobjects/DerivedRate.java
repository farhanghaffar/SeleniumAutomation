package com.innroad.inncenter.pageobjects;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Columns;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import com.innroad.inncenter.pages.DerivedRatePage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_NightlyRatePlan;
import com.innroad.inncenter.properties.OR_RateGrid;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_AdvanceGroups;
import com.innroad.inncenter.webelements.Elements_DerivedRate;
import com.innroad.inncenter.webelements.Elements_NightlyRate;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.webelements.WebElementsOverview;

import junit.framework.Assert;

public class DerivedRate {

	public static Logger logger = Logger.getLogger("DerivedRate");

	// RatePlans

	public void selectRatePlan(WebDriver driver, String ratePlans, boolean isSelect, ArrayList<String> test_steps) {

		StringTokenizer token = new StringTokenizer(ratePlans, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String ratePlan = token.nextToken();
			if (ratePlan.equalsIgnoreCase("All")) {
				ratePlan = "Select All";
			}
			String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ ratePlan.toUpperCase() + "']/preceding-sibling::span/input";
			if (isSelect) {
				if (!driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(ratePlan + " Rate Plan Selected");
					logger.info(ratePlan + " Rate Plan Selected");
				} else {
					test_steps.add(ratePlan + " Rate Plan Already Selected");
					logger.info(ratePlan + " Rate Plan Already Selected");
				}
			} else {
				if (driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(ratePlan + " Rate Plan UnSelected");
					logger.info(ratePlan + " Rate Plan UnSelected");
				} else {
					test_steps.add(ratePlan + " Rate Plan Already UnSelected");
					logger.info(ratePlan + " Rate Plan Already UnSelected");
				}
			}

		}
	}

	public void verifyDisplayedRatePlans(WebDriver driver, ArrayList<String> ratePlanList,
			ArrayList<String> test_steps) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);

		int size = elements.RATE_PLAN_NAMES_LIST.size();
		assertEquals(size, ratePlanList.size(), "Failed To Verify List Size with Active List Size");
		test_steps.add("Successfully Verified Rate Plan List Size : " + size);
		logger.info("Successfully Verified Rate Plan List Size : " + size);
		for (int i = 0; i < size; i++) {

			assertTrue(elements.RATE_PLAN_NAMES_LIST.get(i).isDisplayed(), "Failed To Verify Rate Plan : "
					+ elements.RATE_PLAN_NAMES_LIST.get(i).getText() + " is not Displayed");
			test_steps.add(
					"Successfully Verified Rate Plan is Displayed : " + elements.RATE_PLAN_NAMES_LIST.get(i).getText());
			logger.info(
					"Successfully Verified Rate Plan is Displayed : " + elements.RATE_PLAN_NAMES_LIST.get(i).getText());

			assertEquals(elements.RATE_PLAN_NAMES_LIST.get(i).getText().toUpperCase(), ratePlanList.get(i).toUpperCase(),
					"Failed To Verify Displayed Rate Plan");
			test_steps.add("Successfully Verified Rate Plan : " + elements.RATE_PLAN_NAMES_LIST.get(i).getText());
			logger.info("Successfully Verified Rate Plan : " + elements.RATE_PLAN_NAMES_LIST.get(i).getText());

			verifyRatePlanSelectable(driver, elements.RATE_PLAN_NAMES_LIST.get(i).getText(), test_steps);
		}
	}

	public void verifyRatePlanSelectable(WebDriver driver, String ratePlan, ArrayList<String> test_steps) {
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ ratePlan.toUpperCase() + "']/preceding-sibling::span";

		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
				"Failed To Verify " + ratePlan + " Selectable is not Displayed");
		test_steps.add("Successfully Verified Rate Plan CheckBox is Displayed : " + ratePlan);
		logger.info("Successfully Verified Rate Plan CheckBox is Displayed : " + ratePlan);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
				"Failed To Verify " + ratePlan + " Selectable is not Enabled");
		test_steps.add("Successfully Verified Rate Plan CheckBox is Enabled : " + ratePlan);
		logger.info("Successfully Verified Rate Plan CheckBox is Enabled : " + ratePlan);
	}
	
	public void verifyRatePlansDisplayed(WebDriver driver, ArrayList<String> ratePlanList,
			ArrayList<String> test_steps) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);

		int size = ratePlanList.size();
		for (int i = 0; i < size; i++) {
			
			verifyRatePlanSelectable(driver, ratePlanList.get(i), test_steps);
		}
	}
	
	public void enterRateValue(WebDriver driver, String value,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.RATE_VALUE);
		Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.RATE_VALUE), driver);
		Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.RATE_VALUE), driver);
		Utility.ScrollToElement(elements.RATE_VALUE, driver);
		elements.RATE_VALUE.clear();
		elements.RATE_VALUE.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
		logger.info("Enter Rate Value : " + value);
		test_steps.add("Enter Rate Value : " + value);
		//Below are just to get the focus out from the input field
		expandCurrencyValueDropdown(driver, 1);
		expandCurrencyValueDropdown(driver, 1);
		
	}
	
	public String getRateValue(WebDriver driver,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.RATE_VALUE, driver);
		Utility.ScrollToElement(elements.RATE_VALUE, driver);
		String fieldValue = elements.RATE_VALUE.getAttribute("value");
		logger.info("Rate Value : " + fieldValue);
		test_steps.add("Rate Value : " + fieldValue);
		return fieldValue;
	}
	public String getDropdownValue(WebDriver driver, int index,
			ArrayList<String> test_steps) throws InterruptedException {
		//String dropdownPath = "(//span[contains(text(),'Rates for the derived')]//parent::div//following-sibling::span[2]//div[@class='ant-select-selection-selected-value'])["+ index +"]";
		String dropdownPath = "(//span[contains(text(),'Rates for the derived')]//parent::div//following-sibling::span[2]//span[@class='ant-select-selection-item'])["+ index +"]";		
		WebElement dropdown = driver.findElement(By.xpath(dropdownPath));
		Wait.waitForElementToBeVisibile(By.xpath(dropdownPath), driver);
		Utility.ScrollToElement(dropdown, driver);
		String fieldValue = dropdown.getText().trim();
		logger.info("Rate Value : " + fieldValue);
		test_steps.add("Rate Value : " + fieldValue);
		return fieldValue;
	}
	
	public void expandCurrencyValueDropdown(WebDriver driver, int index) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.SELECTED_RATEPLAN_DROPDOWN_ARROWS.get(index), driver);
		Utility.ScrollToViewElementINMiddle(driver, (elements.SELECTED_RATEPLAN_DROPDOWN_ARROWS.get(index)));
		//elements.SELECTED_RATEPLAN_DROPDOWN_ARROWS.get(index).click();
		Utility.clickThroughAction(driver, elements.SELECTED_RATEPLAN_DROPDOWN_ARROWS.get(index));
	}
	
	public void verifyDropDownOptionsExist(WebDriver driver, int index,String options,
			ArrayList<String> test_steps) throws InterruptedException {
		String xpath = "(//ul[@role='listbox'])["+index+"]/li";
		Wait.WaitForElement(driver, xpath);
		List<WebElement> optionsValue = driver.findElements(By.xpath(xpath));
		Wait.explicit_wait_elementToBeClickable(optionsValue.get(0), driver);
		StringTokenizer token = new StringTokenizer(options, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String option = token.nextToken();
		boolean found = false;
		for(int i = 0 ; i < optionsValue.size(); i++){
			if(optionsValue.get(i).getText().equalsIgnoreCase(option)){
				logger.info("found '"+optionsValue.get(i).getText()+"' option ");
				found = true;
				break;
			}
		}
		assertTrue(found,"Failed: '" + option + "' option not found.");
		test_steps.add("Verified '"+option+"' option Exist.");
		logger.info("Verified '"+option+"' option Exist.");
		}
	}
	public void selectDropDownOptions(WebDriver driver,String option,
			ArrayList<String> test_steps) throws InterruptedException {
		/*String xpath = "(//ul[@role='listbox']/li[text()='"+option+"'])[last()]";*/
		String xpath = "//div[@class='rc-virtual-list-holder-inner']//div[text()='"+option+"']";
		WebElement optionValue = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(optionValue, driver);
		try{
			optionValue.click();
		}catch(Exception e) {
			Utility.clickThroughJavaScript(driver, optionValue);
		}
		test_steps.add("Select '"+option+"' option");
		logger.info("Select '"+option+"' option");
		String xpathOne="//div[@class='rc-virtual-list-holder-inner']//div[@title='"+option+"']";
		assertEquals(driver.findElement(By.xpath(xpathOne)).getAttribute("aria-selected"),"true","Failed: option is not selected");
		
	}
	
	
	public void selectDropDownOptions(WebDriver driver,String productName, String option, int index,
			ArrayList<String> test_steps) throws InterruptedException {
		
		String xpath = "(//span[contains(text(),'"+ productName +"')]//..//preceding::div//following::div[contains(@style,'overflow')]//li[text()='"+option+"'])[last()]";
		WebElement optionValue = driver.findElement(By.xpath(xpath));
		//Utility.ScrollToElement(optionValue, driver);
		optionValue.click();
		test_steps.add("Select '"+option+"' option");
		logger.info("Select '"+option+"' option");
		assertEquals(optionValue.getAttribute("aria-selected"),"true","Failed: option is not selected");
		
	}
	
	//Added By Adhnan 7/27/2020
	public void takeRuleFromParentRatePlanCheckBox(WebDriver driver, boolean checked,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX, driver);
		Utility.ScrollToElement(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		assertTrue(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.isDisplayed(),
				"Failed To Verify Take Rule from parent rate plan checkBox is not Displayed");
		test_steps.add("Successfully Verified 'Take Rule from parent rate plan' is Displayed");
		logger.info("Successfully Verified 'Take Rule from parent rate plan' is Displayed");
		assertTrue(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.isEnabled(),
				"Failed To Verify Take Rule from parent rate plan checkBox is not Displayed Enabled");
		test_steps.add("Successfully Verified 'Take Rule from parent rate plan' CheckBox is Enabled");
		logger.info("Successfully Verified 'Take Rule from parent rate plan' CheckBox is Enabled");
		if (checked) {
			if (!elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.getAttribute("class").contains("checked")) {
				elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.click();
				assertEquals(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.getAttribute("class").contains("checked"), true,
						"Failed : checkBox is not in checked state");
			}
		} else {
			if (elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.getAttribute("class").contains("checked")) {
					elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.click();
				try{
					Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.TAKERULESFROMPARENTUNCHECKPOPUP), driver);
					elements.TAKERULESFROMPARENTUNCHECKPOPUP.click();
					test_steps.add("'Are you sure you do not want to take rules from parent rate plan?' popup yes button clicked");
					logger.info("'Are you sure you do not want to take rules from parent rate plan?' popup yes button clicked");
					
				}catch(Exception e){
					test_steps.add("Are you sure you do not want to take rules from parent rate plan? popup didn't displayed");
					logger.info("Are you sure you do not want to take rules from parent rate plan? popup didn't displayed");
					
				}
			}
		}
	}
	
	public void verifyDollarSignDisplayed(WebDriver driver,ArrayList<String> test_steps) {

		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		assertTrue(elements.DOLLAR_SIGN.isDisplayed(),
				"Failed To Verify Dollar sign '$' is not Displayed");
		test_steps.add("Successfully Verified Dollar sign '$' is Displayed" );
		logger.info("Successfully Verified Dollar sign '$' is Displayed ");
	}

	public void clickTab(WebDriver driver, String tab,ArrayList<String> test_steps) throws InterruptedException {
		String xpath = "//a[.='"+tab+"']";
		WebElement elementTab = null;
		try{
			elementTab = driver.findElement(By.xpath(xpath));
		}catch(Exception e) {
			xpath = "//a[contains(text(),'"+tab+"')]";
			elementTab = driver.findElement(By.xpath(xpath));
		}
		try {
		Wait.WaitForElement(driver, xpath);
		Utility.ScrollToElement(elementTab, driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Utility.ScrollToElement(elementTab, driver);
		elementTab.click();
		}catch(Exception e) {
			Utility.clickThroughJavaScript(driver, elementTab);
		}
		test_steps.add("Click '"+tab+"' tab");
		logger.info("Click '"+tab+"' tab");
	
		
	}

	public void ratePlanUnSaveDataPopupAppear(WebDriver driver, String button, ArrayList<String> testSteps) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.UNSAVED_POPUP, driver);
		assertTrue(elements.UNSAVED_POPUP.isDisplayed(),
				"Failed: Unsaved Data Popup not Displayed");
		testSteps.add("Successfully Verified 'You have unsaved data' popup Displayed" );
		logger.info("Successfully Verified 'You have unsaved data' popup Displayed ");
		String buttonXpath = "//div[@class='ant-modal-content']//span[text()='"+button+"']/parent::button";
		WebElement buttonElement = driver.findElement(By.xpath(buttonXpath));
		Wait.explicit_wait_elementToBeClickable(buttonElement, driver);
		Utility.ScrollToElement(buttonElement, driver);
		buttonElement.click();
		testSteps.add("Click '"+button+"' button");
		logger.info("Click '"+button+"' button");
		try{
			Wait.waitForElementToBeGone(driver, buttonElement, 10);
		}catch(Exception e){
			
		}
		
	}

	public void newRateplanTabExist(WebDriver driver, boolean display, ArrayList<String> testSteps) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		int size = driver.findElements(By.xpath(DerivedRatePage.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON)).size();
		logger.info("Size : " + size);
		if (display) {
			assertNotEquals(size, 0, "Failed: New rate plan creation tab is still  Displayed");
			//Wait.explicit_wait_visibilityof_webelement(elements.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON, driver);
			assertTrue(elements.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON.isDisplayed(),
					"Failed: New rate plan creation tab not  Displayed");
			testSteps.add("Successfully Verified New rate plan creation tab is still Open");
			logger.info("Successfully Verified New rate plan creation tab is still Open ");
		} else {

			assertEquals(size, 0, "Failed: New rate plan creation tab is still  Displayed");
			testSteps.add("Successfully Verified New rate plan creation tab has been closed");
			logger.info("Successfully Verified New rate plan creation tab has been closed");
		}

	}
	
	// Added By Adhnan 7/27/2020
	public void selectDates(WebDriver driver, String type, ArrayList<String> test_steps) throws InterruptedException {
		String xpath = "//span[text()='" + type + "']/parent::label//span[contains(@class,'ant-radio')]";
		WebElement radioButton = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(radioButton, driver);
		radioButton.click();
		test_steps.add("Select date '" + type + "'");
		logger.info("Select date '" + type + "'");
		assertTrue(radioButton.getAttribute("class").contains("checked"), "Failed: radio button is not selected");
	}

	// Added By Adhnan 7/29/2020
	public void expandReduceDerivedratePlans(WebDriver driver, boolean expand, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.DERIVED_RATE_PLAN_ARROW);
		Wait.explicit_wait_visibilityof_webelement(elements.DERIVED_RATE_PLAN_ARROW, driver);
		Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.DERIVED_RATE_PLAN_ARROW), driver);
		Utility.ScrollToElement(elements.DERIVED_RATE_PLAN_ARROW, driver);
		// JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("window.scrollBy(0,-300)");
		assertTrue(elements.DERIVED_RATE_PLAN_ARROW.isDisplayed(), "Failed derived rate Plan dropdown tab not exist");
		if (expand){
			if(!elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded")) {
				Wait.wait3Second();
			Utility.clickThroughJavaScript(driver, elements.DERIVEDRATEPLANARROW);
			Wait.wait3Second();
			//assertEquals(elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded"), true,
				//	"Failed to Expand Derived rate Plans");

			testSteps.add("Expand Derived rate Plans");
			logger.info("Expand Derived rate Plans");
			}
		} else if (elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded")) {
			Utility.ScrollToElement(elements.DERIVED_RATE_PLAN_ARROW, driver);
			//elements.DERIVED_RATE_PLAN_ARROW.click();
			Utility.clickThroughJavaScript(driver, elements.DERIVEDRATEPLANARROW);
			assertEquals(elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded"), false,
					"Failed to Reduce Derived rate Plans");
			testSteps.add("Reduce Derived rate Plans");
			logger.info("Reduce Derived rate Plans");
		}
	}

	// Added By Adhnan 7/29/2020
	public void derivedratePlanExist(WebDriver driver, String ratePlanName, boolean exist,
			ArrayList<String> testSteps) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);

		int size = elements.DERIVED_RATE_PLAN_NAME_LIST.size();
		logger.info("Derived Rate Plan List Size : " + size);
		boolean found = false;
		for (int i = 0; i < size; i++) {
			if (elements.DERIVED_RATE_PLAN_NAME_LIST.get(i).getText().equals(ratePlanName)) {
				found = true;
				break;
			}
		}
		testSteps.add("Derived rate plan '" + ratePlanName + "' existence : " + found);
		logger.info("Derived rate plan '" + ratePlanName + "' existence : " + found);
		assertEquals(found, exist, "Failed Derive Rate Plan Exist status missmatched");
	}

	// Added By Adhnan 7/29/2020
	public void clickEditIconOfDerivedRatePlan(WebDriver driver, String ratePlanName, ArrayList<String> testSteps)
			throws InterruptedException {
		String xpath = "//span[contains(text(),'" + ratePlanName + "')]//following-sibling::a/i[contains(@class,'icon-edit')]";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Utility.ScrollToTillEndOfPage(driver);
		logger.info("Scroll to end");
		Utility.ScrollToElement(elementrequired, driver);
	
		JavascriptExecutor javaScript = (JavascriptExecutor) driver;
		javaScript.executeScript("window.scrollBy(0,-100)");
 		logger.info("in edit mehtod");
 		Wait.wait10Second();
		Utility.clickThroughJavaScript(driver, elementrequired);
		testSteps.add("Click '" + ratePlanName + "' edit icon");
		logger.info("Click '" + ratePlanName + "' edit icon");

	}

	// Added By Adhnan 7/29/2020
	public void deleteDerivedRatePlan(WebDriver driver, String ratePlanName,String button, ArrayList<String> testSteps)
			throws InterruptedException {

		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		String xpath = "//span[text()='" + ratePlanName + "']//following-sibling::span/i[contains(@class,'delete-icon')]";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		elementrequired.click();
		testSteps.add("Click '" + ratePlanName + "' delete icon");
		logger.info("Click '" + ratePlanName + "' delete icon");
		try{
			
			Wait.explicit_wait_visibilityof_webelement(elements.DELETE_RATE_PLAN_POPUP, driver);
		}catch(Exception f){
			Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
			Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
			elementrequired.click();
			testSteps.add("Click '" + ratePlanName + "' delete icon");
			logger.info("Click '" + ratePlanName + "' delete icon");
			Wait.explicit_wait_visibilityof_webelement(elements.DELETE_RATE_PLAN_POPUP, driver);
		}
		assertTrue(elements.DELETE_RATE_PLAN_POPUP.isDisplayed(), "Failed Delete rate Plan Popup not displayed");
		xpath = "//span[text()='" + button + "']//parent::button";
		elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		Utility.ScrollToElement(elementrequired, driver);
		elementrequired.click();
		testSteps.add("Click '" + button + "' button of 'Delete rate Plan' popup");
		logger.info("Click '" + button + "' button of 'Delete rate Plan' popup");

	}
	
	public void verifyViewParentRatePlanLinkExist(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		assertTrue(elements.VIEW_PARENT_RATE_PLAN_LINK.isDisplayed(),
				"Failed: View Parent rate plan link not displayed");
		logger.info("Successfully verified  'View Parent Rate Plan Link' displayed ");
		test_steps.add("Successfully verified  'View Parent Rate Plan Link' displayed ");
	}

	public void clickviewParentRatePlanLink(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.VIEW_PARENT_RATE_PLAN_LINK, driver);
		Utility.ScrollToElement(elements.VIEW_PARENT_RATE_PLAN_LINK, driver);
		elements.VIEW_PARENT_RATE_PLAN_LINK.click();
		logger.info("Click 'View Parent Rate Plan Link' ");
		test_steps.add("Click 'View Parent Rate Plan Link'");
		Wait.explicit_wait_visibilityof_webelement(elements.PARENT_RATE_PLAN_POPUP, driver);
		Wait.explicit_wait_elementToBeClickable(elements.PARENT_RATE_PLAN_POPUP_CLOSE, driver);
		assertTrue(elements.PARENT_RATE_PLAN_POPUP.isDisplayed(), "Failed: Parent rate plan Popup not displayed");
		logger.info("Successfully verified  'Parent rate plan Popup' displayed ");
		test_steps.add("Successfully verified  'Parent rate plan Popup' displayed ");
	}

	public void closeParentRatePlanPopup(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.PARENT_RATE_PLAN_POPUP_CLOSE, driver);
		Wait.explicit_wait_elementToBeClickable(elements.PARENT_RATE_PLAN_POPUP_CLOSE, driver);
		Utility.ScrollToElement(elements.PARENT_RATE_PLAN_POPUP_CLOSE, driver);
		elements.PARENT_RATE_PLAN_POPUP_CLOSE.click();
		logger.info("Click 'Parent rate plan Popup' Close button ");
		test_steps.add("Click 'Parent rate plan Popup' Close button");
	}
	
	public void verifyLoadMoreDatesExist(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		assertTrue(elements.LOAD_MORE_DATES.isDisplayed(),
				"Failed: Load More Dates link not displayed");
		logger.info("Successfully verified  'Load More Dates Link' displayed ");
		test_steps.add("Successfully verified  'Load More Dates Link' displayed ");
	}

	public void clickLoadMoreDates(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		int beforeClickMonths = elements.EDIT_RATE_PLAN_CALENDAR_MONTHS.size();
		logger.info("Before Click 'Load More Dates Link' '"+beforeClickMonths+"' months are visible");
		test_steps.add("Before Click 'Load More Dates Link' '"+beforeClickMonths+"' months are visible");
		Wait.explicit_wait_visibilityof_webelement(elements.LOAD_MORE_DATES, driver);
		Utility.ScrollToElement(elements.LOAD_MORE_DATES, driver);
		elements.LOAD_MORE_DATES.click();
		logger.info("Click 'Load More Dates Link' ");
		test_steps.add("Click 'Load More Dates Link'");
		int afterClickMonths = elements.EDIT_RATE_PLAN_CALENDAR_MONTHS.size();
		logger.info("After Click 'Load More Dates Link' '"+afterClickMonths+"' months are visible");
		test_steps.add("After Click 'Load More Dates Link' '"+afterClickMonths+"' months are visible");
		assertEquals(afterClickMonths, (beforeClickMonths + 2), "Failed: no more months are loaded.");
	}
	
	//Added By Adhnan 7/30/2020
	public void customDateRangeAppear(WebDriver driver, boolean display, ArrayList<String> testSteps) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		int size = driver.findElements(By.xpath(DerivedRatePage.CUSTOM_DATE_RANGE)).size();
		logger.info("Size : " + size);
		if (display) {
			assertNotEquals(size, 0, "Failed: Custom date range selection dates not  Displayed");
			Wait.WaitForElement(driver, DerivedRatePage.CUSTOM_DATE_RANGE);
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.CUSTOM_DATE_RANGE), driver);
			assertTrue(elements.CUSTOM_DATE_RANGE.isDisplayed(),
					"Failed: Custom date range selection dates not  Displayed");
			testSteps.add("Successfully Verified Custom date range selection dates are displaying");
			logger.info("Successfully Verified Custom date range selection dates are displaying");
		} else {

			assertEquals(size, 0, "Failed: Custom date range selection dates are displaying");
			testSteps.add("Successfully Verified Custom date range selection dates are not displaying");
			logger.info("Successfully Verified Custom date range selection dates are not displaying");
		}

	}
	
	//Added By Adhnan 7/30/2020
	public void closeOpenedRatePlanTab(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath( DerivedRatePage.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON), driver);
		Utility.ScrollToElement(elements.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON, driver);
		elements.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON.click();
		logger.info("Click Close button of opened rate plan tab ");
		test_steps.add("Click Close button of opened rate plan tab");
	}
	
	// Added By Adhnan 7/27/2020
		public ArrayList<String> verifyDatesOptionDisplayed(WebDriver driver, String type,boolean display) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<String>();
			String xpath = "//span[text()='" + type + "']/parent::label//span[contains(@class,'ant-radio')]";
			WebElement radioButton = driver.findElement(By.xpath(xpath));
			int size = driver.findElements(By.xpath(xpath)).size();
			logger.info("Size : " + size);
			if (display) {
				assertNotEquals(size, 0, "Failed: '"+type+"' date option not  Displayed");
				Wait.WaitForElement(driver, xpath);
				Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
				Wait.waitForElementToBeClickable(By.xpath( xpath), driver);
				assertTrue(radioButton.isDisplayed(),
						"Failed: '"+type+"' date option not  Displayed");
				testSteps.add("Successfully Verified '"+type+"' date option is displaying");
				logger.info("Successfully Verified '"+type+"' date option is displaying");
			} else {

				assertEquals(size, 0, "Failed: '"+type+"' date option is displaying");
				testSteps.add("Successfully Verified '"+type+"' date option is not displaying");
				logger.info("Successfully Verified '"+type+"' date option is not displaying");
			}
			return testSteps;
		}
		
		
		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <selectDailyFlashSpecificDate> 
		 * ' Description: <Select any specificdate from the current month or from future> 
		 * ' Input parameters: < 'WebDriver' separated parameters type> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Adhnan Ghaffar>
		 * ' Created On:  <08/06/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */	
		public ArrayList<String> selectCustomDateFromCalender(WebDriver driver,int index, 
				String startDate,String dateFormat)
				throws InterruptedException {
			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			ArrayList<String> testSteps = new ArrayList<>();
			Wait.WaitForElement(driver, DerivedRatePage.SELECT_CUSTOM_DATE);
			Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE), driver);
			elements.SELECT_CUSTOM_DATE.get(index).click();
			testSteps.add("Index " + index);
			logger.info("Index " + index);
			//testSteps.add("Click on Calender");
			logger.info("Click on Calender");
			String monthYear = Utility.get_MonthYear(startDate);
			String day = Utility.getDay(startDate);
			logger.info(monthYear);
			String header = null, headerText = null, next = null, date;
			logger.info("Desired Date : " + startDate);
			String desiredDay = Utility.parseDate(startDate, dateFormat, "dd");
			logger.info("Parsed Desired Day : " + startDate);
			String desiredMonth = Utility.parseDate(startDate, dateFormat, "MM");
			logger.info("Parsed Desired Month : " + desiredMonth);
			String desiredYear = Utility.parseDate(startDate, dateFormat, "yyyy");
			logger.info("Parsed Desired Year : " + desiredYear);
				header = "//div[@class='DayPicker-Caption']/div";
				headerText = driver.findElement(By.xpath(header)).getText();

				logger.info("Found Mounth Year : " + headerText);
				String foundYear = Utility.parseDate(headerText, "MMMMM yyyy", "yyyy");
				logger.info("Parsed Year : " + foundYear);
				String foundMonth = Utility.parseDate(headerText, "MMMMM yyyy", "MM");
				logger.info("Parsed Month : " + foundMonth);
				String nextMonthBtnPath = "//div[@class='DayPicker-NavBar']/button[contains(@class,'Right')]";
				String previousMonthBtnPath = "//div[@class='DayPicker-NavBar']/button[contains(@class,'Left')]";
			logger.info("===========CHECKING YEAR CONDITION==========");
			if (!foundYear.equals(desiredYear)) {
				int foundYearIntParssed = Integer.parseInt(foundYear);
				int desiredYearIntParssed = Integer.parseInt(desiredYear);

				if (foundYearIntParssed < desiredYearIntParssed) {
					logger.info("Found Year : " + foundYearIntParssed + " is Less than " + "Desired Year : "
							+ desiredYearIntParssed);
					while (foundYearIntParssed != desiredYearIntParssed) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(nextMonthBtnPath)));
						logger.info("NEXT ARROW CLICKED FOR YEAR ");
						headerText = driver.findElement(By.xpath(header)).getText();
						foundYear = Utility.parseDate(headerText, "MMMMM yyyy", "yyyy");
						foundMonth = Utility.parseDate(headerText, "MMMMM yyyy", "MM");
						foundYearIntParssed = Integer.parseInt(foundYear);
						logger.info("NEW FOUND YEAR : " + foundYearIntParssed);
					}
				} else if (foundYearIntParssed > desiredYearIntParssed) {
					logger.info("Found Year : " + foundYearIntParssed + " is Greater than " + "Desired Year : "
							+ desiredYearIntParssed);
					while (foundYearIntParssed != desiredYearIntParssed) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(previousMonthBtnPath)));
						logger.info("PREVIOUS ARROW CLICKED FOR YEAR ");
						headerText = driver.findElement(By.xpath(header)).getText();
						foundYear = Utility.parseDate(headerText, "MMMMM yyyy", "yyyy");
						foundMonth = Utility.parseDate(headerText, "MMMMM yyyy", "MM");
						foundYearIntParssed = Integer.parseInt(foundYear);
						logger.info("NEW FOUND YEAR : " + foundYearIntParssed);
					}
				}
			}

			logger.info("===========CHECKING MONTH CONDITION==========");

			if (!foundMonth.equals(desiredMonth)) {
				int foundMonthIntParssed = Integer.parseInt(foundMonth);
				int desiredMonthIntParssed = Integer.parseInt(desiredMonth);

				if (foundMonthIntParssed < desiredMonthIntParssed) {
					logger.info("Found Month : " + foundMonthIntParssed + " is Less than " + "Desired Month : "
							+ desiredMonthIntParssed);
					while (foundMonthIntParssed != desiredMonthIntParssed) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(nextMonthBtnPath)));
						logger.info("NEXT ARROW CLICKED FOR Month ");
						headerText = driver.findElement(By.xpath(header)).getText();
						foundYear = Utility.parseDate(headerText, "MMMMM yyyy", "yyyy");
						foundMonth = Utility.parseDate(headerText, "MMMMM yyyy", "MM");
						foundMonthIntParssed = Integer.parseInt(foundMonth);
						logger.info("NEW FOUND MONTH : " + foundMonthIntParssed);
					}
				} else if (foundMonthIntParssed > desiredMonthIntParssed) {
					logger.info("Found Month : " + foundMonthIntParssed + " is Greater than " + "Desired Month : "
							+ desiredMonthIntParssed);
					while (foundMonthIntParssed != desiredMonthIntParssed) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(previousMonthBtnPath)));
						logger.info("PREVIOUS ARROW CLICKED FOR Month ");
						headerText = driver.findElement(By.xpath(header)).getText();
						foundYear = Utility.parseDate(headerText, "MMMMM yyyy", "yyyy");
						foundMonth = Utility.parseDate(headerText, "MMMMM yyyy", "MM");
						foundMonthIntParssed = Integer.parseInt(foundMonth);
						logger.info("NEW FOUND MONTH : " + foundMonthIntParssed);
					}
				}
			}

			logger.info("===========SELECTING DESIRED DAY==========");

			String month = Utility.parseDate(startDate, dateFormat, "MMMM");
			String calendatCellDateFormat = "EEE MMM dd yyyy";
			driver.findElement(
					By.xpath("//div[@aria-label='" + Utility.parseDate(startDate, dateFormat, calendatCellDateFormat) + "']"))
					.click();
						testSteps.add("Select Date : " + startDate);
				logger.info("Select Date : " + startDate);
				
				elements.SELECT_CUSTOM_DATE.get(index).sendKeys(Keys.TAB);
				logger.info("Pressed tab");
				return testSteps;
		}
		

		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <clickAddCustomDate> 
		 * ' Description: <This method will click on add custom date button> 
		 * ' Input parameters: <WebDriver driver> 
		 * ' Return value: <ArrayList<String>>
		 * ' Created By: <Adnan Ghaffar>
		 * ' Created On:  <08/14/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */
	public  ArrayList<String> clickAddCustomDate(WebDriver driver) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		int beforeCustomDates = driver.findElements(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE)).size();

		logger.info("Before Click plus button '"+(beforeCustomDates/2)+"' Custome Range is displaying");
		test_steps.add("Before Click plus button '"+(beforeCustomDates/2)+"' Custome Range is displaying");
		Wait.WaitForElement(driver, DerivedRatePage.ADD_CUSTOM_DATE);
		Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.ADD_CUSTOM_DATE), driver);
		Wait.waitForElementToBeClickable(By.xpath( DerivedRatePage.ADD_CUSTOM_DATE), driver);
		Utility.ScrollToElement(elements.ADD_CUSTOM_DATE, driver);
		elements.ADD_CUSTOM_DATE.click();
		logger.info("Click plus button of Custom Date Range");
		test_steps.add("Click plus button of Custom Date Range");
		int afterCustomDates = driver.findElements(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE)).size();
		logger.info("After Click plus button '"+(afterCustomDates/2)+"' Custome Range is displaying");
		test_steps.add("After Click plus button '"+(afterCustomDates/2)+"' Custome Range is displaying");
		assertEquals(afterCustomDates - beforeCustomDates, 2, "Failed: No new Custom date range appear");
		return test_steps;
	}

	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <removeCustomDate> 
	 * ' Description: <This method will remove custom date range based on index> 
	 * ' Input parameters: <WebDriver driver, int index> 
	 * ' Return value: <ArrayList<String>>
	 * ' Created By: <Adnan Ghaffar>
	 * ' Created On:  <08/14/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */
	public ArrayList<String> removeCustomDate(WebDriver driver,int index) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.explicit_wait_elementToBeClickable(elements.SELECT_CUSTOM_DATE.get(0), driver);
		int beforeCustomDates = driver.findElements(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE)).size();
		int beforeCustomDateRemoveButtonsSize = elements.REMOVE_CUSTOM_DATE.size();

		logger.info("Before Click Minus button of Custom Date Remove Buttons '"+beforeCustomDateRemoveButtonsSize+"'");
		logger.info("Before Click Minus button of Custom Date Range '"+(beforeCustomDates/2)+"' Custome Range is displaying");
		testSteps.add("Before Click Minus button of Custom Date Range '"+(beforeCustomDates/2)+"' Custome Range is displaying");
		
		Wait.explicit_wait_visibilityof_webelement(elements.REMOVE_CUSTOM_DATE.get(index), driver);
		Utility.ScrollToElement(elements.REMOVE_CUSTOM_DATE.get(index), driver);
		elements.REMOVE_CUSTOM_DATE.get(index).click();
		logger.info("Remove Custom Date Range");
		testSteps.add("Remove Custom Date Range");

		int afterCustomDateRemoveButtonsSize = elements.REMOVE_CUSTOM_DATE.size();

		logger.info("After Click Minus button of Custom Date Remove Buttons '"+afterCustomDateRemoveButtonsSize+"'");
		for(int i = 0; i < 5 ;i++){
			if(afterCustomDateRemoveButtonsSize == beforeCustomDateRemoveButtonsSize){
				Utility.clickThroughJavaScript(driver, elements.REMOVE_CUSTOM_DATE.get(index));
				logger.info("Remove Custom Date Range");
				testSteps.add("Remove Custom Date Range");
				afterCustomDateRemoveButtonsSize = elements.REMOVE_CUSTOM_DATE.size();
				logger.info("After Click Minus button of Custom Date Remove Buttons '"+afterCustomDateRemoveButtonsSize+"'");
			}else{
				break;
			}
		}
		int afterCustomDates = driver.findElements(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE)).size();
		for(int i = 0; i < 5 ;i++){
		if(beforeCustomDates - afterCustomDates == 2){
			break;
		}else{
			Wait.wait2Second();
		}
		}
		logger.info("After Click Minus button of Custom Date Range '"+(afterCustomDates/2)+"' Custome Range is displaying");
		testSteps.add("After Click Minus button of Custom Date Range '"+(afterCustomDates/2)+"' Custome Range is displaying");
		assertEquals(beforeCustomDates - afterCustomDates, 2, "Failed: No new Custom date range appear");
		return testSteps;
	}

	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <customDateRangeErrorMessageDisplayed> 
	 * ' Description: <This method will verify that custom date range error message is displaying or not> 
	 * ' Input parameters: <WebDriver driver, boolean display> 
	 * ' Return value: <void>
	 * ' Created By: <Adnan Ghaffar>
	 * ' Created On:  <08/14/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */
	public ArrayList<String> customDateRangeErrorMessageDisplayed(WebDriver driver, boolean display) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		 ArrayList<String> testSteps = new ArrayList<>();
		int size = driver.findElements(By.xpath(DerivedRatePage.CUSTOM_DATE_ERROR_MESSAGE)).size();
		logger.info("Size : " + size);
		if (display) {
			assertNotEquals(size, 0, "Failed: Custom date range Error Message is not  Displayed");
			Wait.explicit_wait_visibilityof_webelement(elements.CUSTOM_DATE_ERROR_MESSAGE, driver);
			assertTrue(elements.CUSTOM_DATE_ERROR_MESSAGE.isDisplayed(),
					"Failed: Custom date range Error messag not  Displayed");
			testSteps.add("Successfully Verified Custom date range Error Message is displaying");
			logger.info("Successfully Verified Custom date range Error Message is displaying");
		} else {
			assertEquals(size, 0, "Failed: Custom date range Error Message is displaying");
			testSteps.add("Successfully Verified Custom date range Error Message is not displaying");
			logger.info("Successfully Verified Custom date range Error Message is not displaying");
		}
		return testSteps;
	}

	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <verifycustomDateRangeErrorMessage> 
	 * ' Description: <This method will verify that custom date range error message text is what is passed in parameters> 
	 * ' Input parameters: WebDriver driver, String errorMessage> 
	 * ' Return value: <void>
	 * ' Created By: <Adnan Ghaffar>
	 * ' Created On:  <08/14/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */
	public ArrayList<String> verifycustomDateRangeErrorMessage(WebDriver driver, String errorMessage) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.CUSTOM_DATE_ERROR_MESSAGE), driver);
		String message = elements.CUSTOM_DATE_ERROR_MESSAGE.getText();
		assertEquals(message,errorMessage,
				"Failed: Custom date range Error message missmathed");
		testSteps.add("Successfully Verified Custom date range Error Message : " + message);
		logger.info("Successfully Verified Custom date range Error Message : " + message);
		assertTrue(elements.CUSTOM_DATE_ERROR_MESSAGE.getAttribute("class").contains("red"),
				"Failed: Custom date range Error message not displaying in red");
		testSteps.add("Successfully Verified Custom date range Error Message is Displaying in Red");
		logger.info("Successfully Verified Custom date range Error Message is Displaying in Red");
		return testSteps;
	}
		
	
	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <verifyCustomDate> 
	 * ' Description: <This method will verify that custom date is equal to what is passed in parameter> 
	 * ' Input parameters: <WebDriver driver, int index, String expectedDate,String dateFormat> 
	 * ' Return value: <void>
	 * ' Created By: <Adnan Ghaffar>
	 * ' Created On:  <08/14/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */
	public void verifyCustomDate(WebDriver driver,int index, String expectedDate,String dateFormat)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.SELECT_CUSTOM_DATE);	
		String date = elements.SELECT_CUSTOM_DATE.get(index).getAttribute("value");
		logger.info("Custom date : " + date);
		logger.info("Expected date : " +expectedDate);
		assertEquals(date,expectedDate,
				"Failed: Custom date missmathed");
	}
	
	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <verifyDateRangeMissmatchPopup> 
	 * ' Description: <This method will verify that date range popup is displaying or not> 
	 * ' Input parameters: <WebDriver driver,   String popupMessage> 
	 * ' Return value: <ArrayList>
	 * ' Created By: <Adnan Ghaffar>
	 * ' Created On:  <08/14/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */
		public ArrayList<String> verifyDateRangeMissmatchPopup(WebDriver driver, String popupMessage) {
			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			ArrayList<String> testSteps = new ArrayList<>();
			Wait.explicit_wait_visibilityof_webelement(elements.DATE_RANGE_MISSMATCH_POPUP, driver);
			assertTrue(elements.DATE_RANGE_MISSMATCH_POPUP.isDisplayed(),
					"Failed: Custom date range Popup not displaying in red");
			testSteps.add("Successfully Verified Custom date range Popup appear");
			logger.info("Successfully Verified Custom date range Popup appear");
			String message = elements.DATE_RANGE_MISSMATCH_POPUP_MESSAGE.getText();
			assertEquals(message,popupMessage,
					"Failed: Custom date range Missmatched message missmathed");
			testSteps.add("Successfully Verified Custom date range Missmatched Message : " + message);
			logger.info("Successfully Verified Custom date range Missmatched Message : " + message);
			
			return testSteps;
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
				logger.info("Click '"+buttonName+"' button of Date Range Missmatched Popup");
				testSteps.add("Click '"+buttonName+"' button of Date Range Missmatched Popup");
				try{
					Wait.waitForElementToBeGone(driver, button, 30);
				}catch(Exception e){
				
				}
			} catch (Exception e) {
			}
			return testSteps;
		}

		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <clickViewSeasonCalendar> 
		 * ' Description: <This method will click on view season calendar button> 
		 * ' Input parameters: <WebDriver driver> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Adnan Ghaffar>
		 * ' Created On:  <08/14/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */
		public ArrayList<String> clickViewSeasonCalendar(WebDriver driver)
				throws InterruptedException {
			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			ArrayList<String> testSteps = new ArrayList<>();
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.VIEW_SEASON_CALENDAR), driver);
			Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.VIEW_SEASON_CALENDAR), driver);
			Utility.ScrollToElement(elements.VIEW_SEASON_CALENDAR, driver);
			elements.VIEW_SEASON_CALENDAR.click();
			logger.info("Click 'View Season Calendar' ");
			testSteps.add("Click 'View Season Calendar'");
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.VIEW_SEASON_CALENDAR_CLOSE), driver);
			Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.VIEW_SEASON_CALENDAR_CLOSE), driver);
			assertTrue(elements.VIEW_SEASON_CALENDAR_CLOSE.isDisplayed(), "Failed: View Season Calendar popup not displayed");
			logger.info("Successfully verified  'View Season Calendar popup' displayed ");
			testSteps.add("Successfully verified  'View Season Calendar popup' displayed ");
			return testSteps;
			
		}
		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <verifySeasonCalendarTabExist> 
		 * ' Description: <This method will whether a tab is existing or not in view season popup> 
		 * ' Input parameters: <WebDriver driver,  String tabName> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Adnan Ghaffar>
		 * ' Created On:  <08/14/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */
		public ArrayList<String> verifySeasonCalendarTabExist(WebDriver driver, String tabName) throws InterruptedException {
			 ArrayList<String> testSteps = new ArrayList<>();
			String xpath = "//div[text()='"+tabName+"']";
			WebElement tab = driver.findElement(By.xpath(xpath));
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			assertTrue(tab.isDisplayed(),
					"Failed: Tab '" + tabName +"' not found");
			testSteps.add("Successfully Verified Tab '" + tabName +"' displayed");
			logger.info("Successfully Verified Tab '" + tabName +"' displayed");
			
			return testSteps;
		}

		//created by adnan ghaffar 08/11/2020
		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <verifyShadedBackground> 
		 * ' Description: <This method will assert back ground colour of given date to check whether it's back ground is shaded or not> 
		 * ' Input parameters: <WebDriver driver, String startDate, String dateFormat, int tabIndex> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Adnan Ghaffar>
		 * ' Created On:  <08/11/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */
		public ArrayList<String> verifyShadedBackground(WebDriver driver, String startDate, String dateFormat, int tabIndex) {
			ArrayList<String> testSteps = new ArrayList<>();
			String calendatCellDateFormat = "EEE MMM dd yyyy";
			String cellDate = Utility.parseDate(startDate, dateFormat, calendatCellDateFormat);
			//inprogress
			String xpath ="(//div[@aria-label='"+cellDate+"']/div)["+tabIndex+"]";
			// for shaded:  style contains' background: repeating-linear-gradient'
			//for 
			Wait.WaitForElement(driver, xpath);
			String backGroundProperty  = driver.findElement(By.xpath(xpath)).getAttribute("style");
			logger.info("backGroundProperty : " + backGroundProperty);
			String expectedStyleText = "background: repeating-linear-gradient";
			String[] splitProperty = backGroundProperty.split("\\(");
			assertEquals(expectedStyleText, splitProperty[0], "Failed : Date range outside the parent date range is not shaded");
			logger.info("Verified that date outside the parent date range is showing in shaded view");
			testSteps.add("Verified that date outside the parent date range is showing in shaded view");
			
			
			return testSteps;
		}

		//created by Adnan Ghaffar 08/11/2020
		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <getCurrentDateUnderlineProperty> 
		 * ' Description: <This method will return css text-decoration property value for a specific date> 
		 * ' Input parameters: <WebDriver driver, String startDate, String dateFormat, int index> 
		 * ' Return value: <String>
		 * ' Created By: <Adnan Ghaffar>
		 * ' Created On:  <08/11/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */
		public String getCurrentDateUnderlineProperty(WebDriver driver, String startDate, String dateFormat, int index) {
			String calendatCellDateFormat = "EEE MMM dd yyyy";
			String cellDate = Utility.parseDate(startDate, dateFormat, calendatCellDateFormat);
			String xpath ="(//div[@aria-label='"+cellDate+"'])["+ index +"]";
			// for shaded:  style contains' background: repeating-linear-gradient'
			Wait.WaitForElement(driver, xpath);
			String underlinedProperty  = driver.findElement(By.xpath(xpath)).getCssValue("text-decoration");
			logger.info("underlinedProperty : " + underlinedProperty.split(" ")[0]);
						
			return underlinedProperty.split(" ")[0];
		}

	
		//added by Adnan Ghaffar 08/11//2020
		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <clickSeasonCalendarTab> 
		 * ' Description: <This method will click season calendar tab by using name of tab> 
		 * ' Input parameters: <WebDriver driver, String tabName> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Adnan Ghaffar>
		 * ' Created On:  <08/14/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */
		public ArrayList<String> clickSeasonCalendarTab(WebDriver driver, String tabName) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<>();
			
			String xpath = "//div[text()='"+tabName+"']";
			WebElement tab = driver.findElement(By.xpath(xpath));
			Wait.WaitForElement(driver, xpath);
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
			tab.click();
			testSteps.add("Clicked '" + tabName +"' Tab ");
			logger.info("Click '" + tabName +"' Tab ");
			
			return testSteps;
			
		}

		//Created by adnan ghaffar 08/11/2020
		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <verifySeasonRatesDetailPopup> 
		 * ' Description: <This method will verify dates popup content(Room classes, rates, season name, season color) upon hovering in View Season Popup> 
		 * ' Input parameters: <WebDriver driver, int index,  String startDate, String dateFormat, 
				String parentSeasonName, String backgroundColor, String columnHeadings, String currency,
				ArrayList<String> roomClassesNamesWithRates, String seasonRules> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Adnan Ghaffar>
		 * ' Created On:  <08/14/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */	
		public ArrayList<String> verifySeasonRatesDetailPopup(WebDriver driver, int index,  String startDate, String dateFormat, 
				String parentSeasonName, String backgroundColor, String columnHeadings, String currency,
				ArrayList<String> roomClassesNamesWithRates, String seasonRules) {
			ArrayList<String> testSteps = new ArrayList<>();
			Actions action = new Actions(driver);

			String calendatCellDateFormat = "EEE MMM dd yyyy";
			String cellDate = Utility.parseDate(startDate, dateFormat, calendatCellDateFormat);
			String xpath ="(//div[@aria-label='"+cellDate+"']/div)["+ index+"]";
			WebElement dateCell = driver.findElement(By.xpath(xpath));
			Wait.WaitForElement(driver, xpath);
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
			action.moveToElement(dateCell).build().perform();
			logger.info("Hover done");
			
			
			String popHeadingXpath ="//div[text()='"+ parentSeasonName +"']";
			Wait.WaitForElement(driver, popHeadingXpath);
			Wait.waitForElementToBeVisibile(By.xpath(popHeadingXpath), driver);
			WebElement popupHeading = driver.findElement(By.xpath(popHeadingXpath));
			assertTrue(popupHeading.isDisplayed(), "Failed : Popup heading is  not displaying");
			logger.info("Verified season name in popup");
			testSteps.add("Verified season name in popup");
			
			testSteps.add("==== "+ "Verify season background colour".toUpperCase() +" ===");
			logger.info("==== "+ "Verify season background colour".toUpperCase() +" ===");

			testSteps.add("Expected : " + backgroundColor);
			logger.info("Expected : " +  backgroundColor);
			String found = popupHeading.getCssValue("background-color");
			testSteps.add("Found : " + found);
			logger.info("Found : " +  found);
			assertTrue(found.contains(backgroundColor), "Failed : popup heading background colour didn't match");

			String roomClassesPath = "//div[text()='"+ parentSeasonName +"']//following-sibling::div//ul//li";
			List<WebElement> roomClass = driver.findElements(By.xpath(roomClassesPath));
			logger.info("Size of list " + roomClass.size());
			assertNotEquals(0, roomClass.size(), "Popup has no roomclasses");
			
			ArrayList<String> getRoomClasses = new ArrayList<>();
			for(int i =0; i < roomClass.size(); i++){
				if(i ==0){
					String text = roomClass.get(i).getText();
					logger.info(text);
					assertEquals(columnHeadings, text, "Failed : "+columnHeadings+" columns are not showing");
					if(columnHeadings.equalsIgnoreCase("Room Class ParentDerived")){
						columnHeadings = columnHeadings.replace("D", ", D");
						columnHeadings = columnHeadings.replace("P", ", P");
					}else if(columnHeadings.equalsIgnoreCase("Room Class Rate")){
						columnHeadings = columnHeadings.replace("s", "s,");
								
					}
					logger.info("Verified that following columns (" + columnHeadings+") are showing in popup");
					testSteps.add("Verified that following columns (" + columnHeadings+") are showing in popup");
					
					continue;
				}
				String text = roomClass.get(i).getText();
				text = text.replace("$", " $");
				logger.info(text);
				getRoomClasses.add(text);
			}
			
			logger.info("Room classes with rates : " + getRoomClasses);
			testSteps.add("Room classes with rates : " + getRoomClasses);

			for(int i = 0 ; i < roomClassesNamesWithRates.size(); i++){
				assertEquals(getRoomClasses.get(i),roomClassesNamesWithRates.get(i), "Failed : room classes in popup didn't match with parent rate plan roomclass");
				logger.info("Verified that parent rateplan room classes are showing in popup");
			}
			if(columnHeadings.contains("Derived")){	
				logger.info("Verified that both parent and derived rates value are showing with currency symbol ("+currency+") in popup");
				testSteps.add("Verified that both parent and derived rates value are showing with currency symbol ("+currency+") in popup");
			}else {
				logger.info("Verified that parent rates value are showing with currency symbol ("+currency+") in popup");
				testSteps.add("Verified that parent rates value are showing with currency symbol ("+currency+") in popup");	
			}
			if(seasonRules.equals("No Rules")){
				logger.info("Verified parent rules are not showing in popup");
				testSteps.add("Verified parent rules are not showing in popup");
			}else {

				String rulesxpath = "//div[text()='"+parentSeasonName+"']//following-sibling::div/div[@class='rules']";
				WebElement rules = driver.findElement(By.xpath(rulesxpath));
				String ratePlanRules = rules.getText();
				logger.info("Rules : " + ratePlanRules);
				testSteps.add("Rules : " + ratePlanRules);
				ratePlanRules = ratePlanRules.replaceAll("\n", ",");
				logger.info("Rules : " + ratePlanRules);
				testSteps.add("Rules : " + ratePlanRules);
				assertEquals(ratePlanRules,seasonRules,"Failed Rule missmatched");
				logger.info("Successfully Verified parent rules are showing in popup");
				testSteps.add("Seccessfully Verified parent rules are showing in popup");	
			}
			action.moveByOffset(20, 20).build().perform();
			
			return testSteps;
		}

		public ArrayList<String> clickCustomDateRange(WebDriver driver) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			Wait.WaitForElement(driver, DerivedRatePage.CustomDateRange);
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.CustomDateRange), driver);
			Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.CustomDateRange), driver);
			Utility.ScrollToElement(elements.CustomDateRange, driver);
			elements.CustomDateRange.click();
			testSteps.add("Clicked custom date range");
			logger.info("Clicked custom date range");
			
			return testSteps;
			
		}
		
		public String getDateBackgroundColour(WebDriver driver, String startDate, String dateFormat, int index) {
			String calendatCellDateFormat = "EEE MMM dd yyyy";
			String cellDate = Utility.parseDate(startDate, dateFormat, calendatCellDateFormat);
			String xpath ="(//div[@aria-label='"+cellDate+"'])["+ index +"]//div";
			Wait.WaitForElement(driver, xpath);
			String backgroundProperty  = driver.findElement(By.xpath(xpath)).getCssValue("background-color");
			return backgroundProperty;
		}

		public ArrayList<String> verifyLoadMoreDateLinkDisplay(WebDriver driver, int index, boolean isClick) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<>();
			String xpath ="(//span[contains(text(),'Load more dates')])["+ index+"]";
			Wait.WaitForElement(driver, xpath);
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
			WebElement loadMoreDate = driver.findElement(By.xpath(xpath));
			Utility.ScrollToElement(loadMoreDate, driver);
			assertTrue(loadMoreDate.isDisplayed(), "Failed : 'Load more dates' link is  not displaying");
			logger.info("Verified 'Load more dates' link is displaying");
			testSteps.add("Verified 'Load more dates' link is displaying");
			
			if(isClick){
				loadMoreDate.click();
				logger.info("Clicked 'Load more dates' link");
				testSteps.add("Clicked 'Load more dates' link");
					
			}
			return testSteps;
		}
		
		//created by adnan ghaffar 08/11/2020
		
		public ArrayList<String> verifyDateColor(WebDriver driver, String startDate, String dateFormat, int tabIndex, String color) {
			ArrayList<String> testSteps = new ArrayList<>();
			String calendatCellDateFormat = "EEE MMM dd yyyy";
			String cellDate = Utility.parseDate(startDate, dateFormat, calendatCellDateFormat);
			String xpath ="(//div[@aria-label='"+cellDate+"']/div)["+tabIndex+"]"; 
			Wait.WaitForElement(driver, xpath);
			String backGroundProperty  = driver.findElement(By.xpath(xpath)).getAttribute("style");
			logger.info("backGroundProperty : " + backGroundProperty);
			String expectedStyleText = "background: repeating-linear-gradient";
			String[] splitProperty = backGroundProperty.split("\\(");
			assertEquals(expectedStyleText, splitProperty[0], "Failed : Date range outside the parent date range isnot shaded");
			logger.info("Verified that date outside the parent date range is showing in shaded view");
			testSteps.add("Verified that date outside the parent date range is showing in shaded view");
			
			
			return testSteps;
		}
		
		public ArrayList<String> clickLoadMoreDates(WebDriver driver,int tabIndex,int numberOfMonthsAdded)
				throws InterruptedException {
			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			ArrayList<String> testSteps = new ArrayList<>();
			String xpath = "(//div[@role='tabpanel'])["+tabIndex+"]//div[@class='DayPicker-Month']";
			List<WebElement> months = driver.findElements(By.xpath(xpath));
			int beforeClickMonths = months.size();
			logger.info("Before Click 'Load More Dates Link' '"+beforeClickMonths+"' months are visible");
			testSteps.add("Before Click 'Load More Dates Link' '"+beforeClickMonths+"' months are visible");
			String elementxpath ="(//span[contains(text(),'Load more dates')])["+ tabIndex+"]";
			Wait.WaitForElement(driver, elementxpath);
			Wait.waitForElementToBeVisibile(By.xpath(elementxpath), driver);
			Wait.waitForElementToBeClickable(By.xpath(elementxpath), driver);
			WebElement loadMoreDate = driver.findElement(By.xpath(elementxpath));
			Utility.ScrollToElement(loadMoreDate, driver);
			loadMoreDate.click();
			logger.info("Click 'Load More Dates Link' ");
			testSteps.add("Click 'Load More Dates Link'");
			months = driver.findElements(By.xpath(xpath));
			int afterClickMonths = months.size();
		for (int i = 0; i < 15; i++) {
			if (afterClickMonths > beforeClickMonths) {
				break;
			}
			afterClickMonths = months.size();
			Wait.wait3Second();

			logger.info("Wait for month Load ");
		}
			logger.info("After Click 'Load More Dates Link' '"+afterClickMonths+"' months are visible");
			testSteps.add("After Click 'Load More Dates Link' '"+afterClickMonths+"' months are visible");
			assertEquals(afterClickMonths, (beforeClickMonths + numberOfMonthsAdded), "Failed: no more months are loaded.");
			
			return testSteps;
		}
		
		public void verifyMonthsDisplaying(WebDriver driver,int tabIndex,int numberOfMonths, ArrayList<String> test_steps)
				throws InterruptedException {
			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			String xpath = "(//div[@role='tabpanel'])["+tabIndex+"]//div[@class='DayPicker-Month']";
			List<WebElement> months = driver.findElements(By.xpath(xpath));
			int monthsSize = months.size();
			logger.info(" '"+monthsSize+"' months are visible");
			test_steps.add("'"+monthsSize+"' months are visible");
			assertEquals(monthsSize, numberOfMonths,"Failed: number of months missmatched");
		}
		
		public ArrayList<String> VerifyCurrentMonthExist(WebDriver driver,int tabIndex,int atPlace,String month)
				throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<>();
			String xpath = "(//div[@role='tabpanel'])["+tabIndex+"]//div[@role='heading']/div";
			List<WebElement> months = driver.findElements(By.xpath(xpath));
			logger.info("Successfully verified that current Month '"+month+"' is visible at '"+atPlace + "' place");
			testSteps.add("Successfully verified that current Month '"+month+"' is visible at '"+atPlace + "' place");
			assertEquals(months.get(atPlace -1 ).getText(), month,"Failed: current month not verified");
			return testSteps;
		}
		
		//Added By Adhnan 7/30/2020
		public ArrayList<String> closeViewSeasonCalendar(WebDriver driver)
				throws InterruptedException {
			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			ArrayList<String> testSteps = new ArrayList<>();
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.VIEW_SEASON_CALENDAR_CLOSE), driver);
			Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.VIEW_SEASON_CALENDAR_CLOSE), driver);
			Utility.ScrollToElement(elements.VIEW_SEASON_CALENDAR_CLOSE, driver);
			elements.VIEW_SEASON_CALENDAR_CLOSE.click();
			logger.info("Click Close button of View Season Calendar Popup ");
			testSteps.add("Click Close button of View Season Calendar Popup");
			return testSteps;
		}
		
		public void verifyCurrencySignDisplayed(WebDriver driver,String currencySign,ArrayList<String> test_steps) {

			Elements_DerivedRate elements = new Elements_DerivedRate(driver);
			assertTrue(elements.DOLLAR_SIGN.isDisplayed(),
					"Failed To Verify Dollar sign '"+currencySign+"' is not Displayed");
			test_steps.add("Successfully Verified Dollar sign '"+currencySign+"' is Displayed" );
			logger.info("Successfully Verified Dollar sign '"+currencySign+"' is Displayed ");
		}
		
		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <createDerivedRatePlan> 
		 * ' Description: <Create derived rate plan from scratch> 
		 * ' Input parameters: < WebDriver driver, String ratePlanType, String ratePlanName, String folioDisplayName, String ratePlanDescription,
				String parentRatePlanName, String comparisonOption, String currencyOptions, String amount, boolean isTakeRuleFromParentChecked, String dateType, String startDate, String endDate, String dateFormat,
				String Channels, String RoomClasses> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Farhan Ghaffar>
		 * ' Created On:  <08/14/2020>
		 * 
		 * ##########################################################################################################################################################################
		 */	
		public ArrayList<String> createDerivedRatePlan(WebDriver driver, String ratePlanType, String ratePlanName, String folioDisplayName, String ratePlanDescription,
				String parentRatePlanName, String comparisonOption, String currencyOptions, String amount, boolean isTakeRuleFromParentChecked, String dateType, String startDate, String endDate, String dateFormat,
				String Channels, String RoomClasses) throws InterruptedException {

			ArrayList<String> testSteps = new ArrayList<>();
			Navigation navigation = new Navigation();
			NightlyRate nightlyRate = new NightlyRate();
			RatesGrid ratesGrid = new RatesGrid();
			testSteps.addAll(ratesGrid.clickRateGridAddRatePlan(driver));
			testSteps.addAll(ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlanType));
			nightlyRate.enterRatePlanName(driver, ratePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, ratePlanDescription, testSteps);
			
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			selectRatePlan(driver, parentRatePlanName, true, testSteps);
			expandCurrencyValueDropdown(driver, 1);
			
			testSteps.add("Expand Value Comparison DropDown");
			logger.info("Expand Value Comparison DropDown");
			
			selectDropDownOptions(driver, comparisonOption, testSteps);
			expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			logger.info("Expand Currency DropDown");

			selectDropDownOptions(driver, currencyOptions, testSteps);
			
			testSteps.add("===== ENTER VALUE =====");
			logger.info("===== ENTER VALUE =====");
			enterRateValue(driver, amount, testSteps);
			
			takeRuleFromParentRatePlanCheckBox(driver, isTakeRuleFromParentChecked, testSteps);
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			if(dateType.equalsIgnoreCase("Always available")){
				selectDates(driver, dateType, testSteps);	
			}
			else{
				selectDates(driver, dateType, testSteps);
				testSteps.addAll(selectCustomDateFromCalender(driver, 0,  startDate, dateFormat));
				try{
					testSteps.addAll(clickDateRangeMissmatchedPopupButton(driver,"Yes"));

				}catch(Exception e){
					
				}
				testSteps.addAll(selectCustomDateFromCalender(driver, 1,  endDate, dateFormat));
				try{
					testSteps.addAll(clickDateRangeMissmatchedPopupButton(driver,"Yes"));

				}catch(Exception e){
					
				}				
			}
			
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			logger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			nightlyRate.selectChannels(driver, Channels, true, testSteps);
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			logger.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);

			testSteps.addAll(nightlyRate.clickNextButton(driver));

			testSteps.addAll(nightlyRate.clickNextButton(driver));

			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			nightlyRate.clickSaveAsActive(driver, testSteps);
			
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			logger.info("Navigated to RatesGrid");
			
			testSteps.add("=================== "
					+ "Verifying the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			logger.info("=================== "
					+ "Verifying the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			driver.navigate().refresh();
			Wait.wait3Second();
			driver.navigate().refresh();
			
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			
			testSteps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			logger.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

			return testSteps;
		}

		/*
		 * ##########################################################################################################################################################################
		 * 
		 * ' Method Name: <createDerivedRatePlan> 
		 * ' Description: <Create derived rate plan from scratch> 
		 * ' Input parameters: < WebDriver driver, String ratePlanType, String ratePlanName, String folioDisplayName, String ratePlanDescription,
				String parentRatePlanName, String comparisonOption, String currencyOptions, String amount, boolean isTakeRuleFromParentChecked, String dateType, String startDate, String endDate, String dateFormat,
				String Channels, String RoomClasses, String seasonDuration> 
		 * ' Return value: <ArrayList>
		 * ' Created By: <Farhan Ghaffar>
		 * ' Created On:  <08/14/2020>
		 * ' Modifed On: <08/29/2020>
		 * ' Modified By: Farhan Ghaffar
		 * ' Add  select multiple dates in custom date range
		 * ##########################################################################################################################################################################
		 */	
		public ArrayList<String> createDerivedRatePlan(WebDriver driver, String ratePlanType, String ratePlanName, String folioDisplayName, String ratePlanDescription,
				String parentRatePlanName, String comparisonOption, String currencyOptions, String amount, boolean isTakeRuleFromParentChecked, String dateType, String startDate, String endDate, String dateFormat,
				String Channels, String RoomClasses, String seasonDuration, String timeZone) throws InterruptedException, NumberFormatException, ParseException {

			ArrayList<String> testSteps = new ArrayList<>();
			Navigation navigation = new Navigation();
			NightlyRate nightlyRate = new NightlyRate();
			RatesGrid ratesGrid = new RatesGrid();
			testSteps.addAll(ratesGrid.clickRateGridAddRatePlan(driver));
			testSteps.addAll(ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlanType));
			nightlyRate.enterRatePlanName(driver, ratePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, ratePlanDescription, testSteps);
			
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			selectRatePlan(driver, parentRatePlanName, true, testSteps);
			expandCurrencyValueDropdown(driver, 1);
			
			testSteps.add("Expand Value Comparison DropDown");
			logger.info("Expand Value Comparison DropDown");
			
			selectDropDownOptions(driver, comparisonOption, testSteps);
			expandCurrencyValueDropdown(driver, 0);
			testSteps.add("Expand Currency DropDown");
			logger.info("Expand Currency DropDown");

			selectDropDownOptions(driver, currencyOptions, testSteps);
			
			testSteps.add("===== ENTER VALUE =====");
			logger.info("===== ENTER VALUE =====");
			enterRateValue(driver, amount, testSteps);
			
			takeRuleFromParentRatePlanCheckBox(driver, isTakeRuleFromParentChecked, testSteps);
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			if(dateType.equalsIgnoreCase("Always available")){
				selectDates(driver, dateType, testSteps);	
			}
			else{
				selectDates(driver, dateType, testSteps);
				testSteps.addAll(selectCustomStartAndEndDates(driver,startDate,endDate,Integer.parseInt(seasonDuration),timeZone));
			}
			
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			Wait.wait5Second();
			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			logger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
			try{
				nightlyRate.selectChannels(driver, Channels, true, testSteps);
			}catch(Exception e){
				nightlyRate.clickNextButton(driver);
			}
			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			logger.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);

			testSteps.addAll(nightlyRate.clickNextButton(driver));

			testSteps.addAll(nightlyRate.clickNextButton(driver));

			testSteps.addAll(nightlyRate.clickNextButton(driver));
			
			nightlyRate.clickSaveAsActive(driver, testSteps);
			
			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");
			logger.info("Navigated to RatesGrid");
			
			testSteps.add("==== "
					+ "Verifying the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ====");
			logger.info("=================== "
					+ "Verifying the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
							.toUpperCase()
					+ " ======================");
			driver.navigate().refresh();
			Wait.wait3Second();
			driver.navigate().refresh();
			
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			
			testSteps.add(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
			logger.info(
					"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");

			return testSteps;
		}


	// Added By Adhnan 08/18/2020
	public ArrayList<String> expandReduceDerivedRatePlan(WebDriver driver, String ratePlanName, String button,
			ArrayList<String> testSteps) throws InterruptedException {
		String xpath = "//span[text()='" + ratePlanName + "']//preceding-sibling::span[contains(@class,'ir-acrd-bnt ir-"
				+ button + "')]";
		if(driver.findElements(By.xpath(xpath)).size()>0) {
		Wait.WaitForElement(driver, xpath);
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		Utility.ScrollToElement(elementrequired, driver);
		try {
			elementrequired.click();
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
			Utility.clickThroughJavaScript(driver,elementrequired);
		}
		}
		if (button.equals("plus")) {
			testSteps.add("Expand '" + ratePlanName + "'");
			logger.info("Expand '" + ratePlanName + "'");
		} else if (button.equals("minus")) {
			testSteps.add("Reduce '" + ratePlanName + "'");
			logger.info("Reduce '" + ratePlanName + "'");
		}
		return testSteps;
	}

	
	// Added By Adhnan 08/18/2020
	public ArrayList<String> verifyDerivedRatePlanDetails(WebDriver driver, String ratePlanName,
			String derivedRatePlanDetails, ArrayList<String> testSteps) throws InterruptedException {
		String xpath = "//span[text()='" + ratePlanName + "']//parent::h3//following-sibling::div/div/div";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		String foundRateDetails = elementrequired.getText().replace("/n", " ");
		if (foundRateDetails.contains(" Conditions:")) {
			foundRateDetails = foundRateDetails.split(" Conditions:")[0];
		}
		testSteps.add("Expected DerivedRate Plan Details : '" + derivedRatePlanDetails + "'");
		logger.info("Expected DerivedRate Plan Details : '" + derivedRatePlanDetails + "'");
		testSteps.add("Found DerivedRate Plan Details : '" + foundRateDetails + "'");
		logger.info("Found DerivedRate Plan Details : '" + foundRateDetails + "'");
		assertTrue(foundRateDetails.contains(derivedRatePlanDetails), "Failed: Derived rate Plan Detials missmatched");
		testSteps.add("Successfully verified Derived Rate Plan Details");
		logger.info("Successfully verified Derived Rate Plan Details");

		return testSteps;

	}

	// Added By Adhnan 08/18/2020
	public ArrayList<String> verifyDerivedRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
			String rateValue, int index) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		testSteps.add("Verify Rate Value of Derived Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
		logger.info("Verify Rate Value of Derived Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
		testSteps.add("Index '"+index+"'");
		logger.info("Index '"+index+"'");
		String xpath = "(//span[text()='" + ratePlanName
				+ "']//ancestor::div[@class='DerivedPlan']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
				+ roomClassName + "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])[" + (index + 1)
				+ "]";
		// Wait.explicit_wait_xpath(xpath, driver);
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		String foundRateValue = elementrequired.getText();
		testSteps.add("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
		logger.info("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
		testSteps.add("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		logger.info("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		assertEquals(foundRateValue, rateValue, "Failed:  Derived rate Plan rate Value missmatcehd");

		testSteps.add("Successfully verified Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		logger.info("Successfully verified Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		return testSteps;

	}

	// Added By Adhnan 08/18/2020
	public ArrayList<String> verifyDerivedRatePlanRestrictions(WebDriver driver, String ratePlanName,
			String restrictionsDetails) throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		String xpath = "//span[text()='" + ratePlanName + "']//parent::h3//following-sibling::div/div/div/div";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		String foundRestrictionDetails = elementrequired.getText().replace("/n", " ");
		// testSteps.add("Expected Derived Rate Plan Restrictions Details : '" +
		// restrictionsDetails + "'");
		logger.info("Expected Derived Rate Plan Restrictions Details : '" + restrictionsDetails + "'");
		// testSteps.add("Found Derived Rate Plan Restrictions Details : '" +
		// foundRestrictionDetails + "'");
		logger.info("Found Derived Rate Plan Restrictions Details : '" + foundRestrictionDetails + "'");
		assertTrue(foundRestrictionDetails.contains(restrictionsDetails), "Failed: Derived rate Plan Detials");
		testSteps.add("Verified Restriction : '" + restrictionsDetails + "' Exist.");
		return testSteps;

	}

	// Added By Adhnan 8/26/2020
	public ArrayList<String> bestAvailableRatePlanDisplayed(WebDriver driver, String ratePlanName) {
		ArrayList<String> testSteps = new ArrayList<>();
		String xpath = "//span[text()='" + ratePlanName
				+ "']//ancestor::div[contains(@class,'DerivedPlanItem ')]//div[text()='Best Available Rates']";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		assertTrue(elementrequired.isDisplayed(),
				"Failed: Best Available rates for rate  '" + ratePlanName + "'  not displaying");
		testSteps.add("Verified Best Available Rates for Derived Rate '" + ratePlanName + "' is shown");
		logger.info("Verified Best Available Rates for Derived Rate '" + ratePlanName + "' is shown");
		return testSteps;
	}

	// Added By Adhnan 08/26/2020
	public ArrayList<String> verifyBestAvailableDerivedRRateValue(WebDriver driver, String ratePlanName,
			String rateValue, int index) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String xpath = "(//span[text()='" + ratePlanName
				+ "']//ancestor::div[contains(@class,'DerivedPlanItem ')]//div[contains(@class,'BestAvailableHeader')]//following-sibling::div/div[@class=' RegularRate'])["
				+ (index + 1) + "]";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		String foundRateValue = elementrequired.getText();
		testSteps.add("Expected Best Available Derived  Rate Value : '" + rateValue + "'");
		logger.info("Expected Best Available Derived  Rate Value : '" + rateValue + "'");
		testSteps.add("Found Best Available Derived  Rate Value : '" + foundRateValue + "'");
		logger.info("Found Best Available Derived  Rate Value : '" + foundRateValue + "'");
		assertEquals(foundRateValue, rateValue, "Failed:  Derived rate Plan rate Value missmatcehd");
		return testSteps;

	}

	// Added By Adhnan 08/27/2020
	public ArrayList<String> changeDerivedRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
			String rateValue, int index) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		testSteps.add("Override Rate Value of Derived Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
		logger.info("Override Rate Value of Derived Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
		String xpath = "(//span[text()='" + ratePlanName
				+ "']//ancestor::div[@class='DerivedPlan']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
				+ roomClassName + "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])[" + (index + 1)
				+ "]";
		// Wait.explicit_wait_xpath(xpath, driver);
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		String oldRateValue = elementrequired.getText();
		testSteps.add("Before Change Rate Value of  : '" + oldRateValue + "'");
		logger.info("Before Change Rate Value : '" + oldRateValue + "'");
		elementrequired.click();
		testSteps.addAll(changeDate(driver,rateValue));
		elementrequired = driver.findElement(By.xpath(xpath));
		String newRateValue = elementrequired.getText();
		testSteps.add("After Change Rate Value : '" + newRateValue + "'");
		logger.info("After Change Rate Value : '" + newRateValue + "'");

		assertEquals(newRateValue, rateValue, "Failed:  Changes rate Plan rate Value");
		assertFalse( newRateValue.equals(oldRateValue), "Failed:  Changes rate Plan rate Value missmatcehd");

		testSteps.add("Successfully override Derived Rate Plan Rate Value : '" + rateValue + "'");
		logger.info("Successfully override  Derived Rate Plan Rate Value : '" + rateValue + "'");
		return testSteps;

	}
	
	// Added By Adhnan 08/27/2020
		public ArrayList<String> changeBaseRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
				String rateValue, int index) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<>();
			testSteps.add("Override Rate Value of Base Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
			logger.info("Override Rate Value of Base Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
			String xpath = "(//span[text()='" + ratePlanName
					+ "']//ancestor::div[@id='ratePlans']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
					+ roomClassName + "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])[" + (index + 1)
					+ "]";
			// Wait.explicit_wait_xpath(xpath, driver);
			WebElement elementrequired = driver.findElement(By.xpath(xpath));
			Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
			Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
			String oldRateValue = elementrequired.getText();
			testSteps.add("Before Change Rate Value : '" + oldRateValue + "'");
			logger.info("Before Change Rate Value : '" + oldRateValue + "'");
			try{
				elementrequired.click();
			}catch(Exception e){
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,-300)");
				elementrequired.click();
			}
			testSteps.addAll(changeDate(driver,rateValue));
			elementrequired = driver.findElement(By.xpath(xpath));
			String newRateValue = elementrequired.getText();
			testSteps.add("After Change Rate Value : '" + newRateValue + "'");
			logger.info("After Change Rate Value : '" + newRateValue + "'");

			assertEquals(newRateValue, rateValue, "Failed:  Changes rate Plan rate Value");
			assertFalse( newRateValue.equals(oldRateValue), "Failed:  Changes rate Plan rate Value missmatcehd");

			testSteps.add("Successfully override Base Rate Plan Rate Value : '" + rateValue + "'");
			logger.info("Successfully override  Base Rate Plan Rate Value : '" + rateValue + "'");
			return testSteps;

		}
	
	// Added By Adhnan 08/27/2020
		public ArrayList<String> changeDate(WebDriver driver,
				String rateValue) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<>();
			Elements_RatesGrid elements = new Elements_RatesGrid(driver);
			Wait.explicit_wait_visibilityof_webelement(elements.rateGridRoomRate, driver);
			Wait.explicit_wait_elementToBeClickable(elements.rateGridSuccess, driver);
			elements.rateGridRoomRate.clear();
			elements.rateGridRoomRate.sendKeys(rateValue);
			testSteps.add("Enter New Rate: <b>" + rateValue + "</b>");
			logger.info("Enter New Rate: " + rateValue);
			elements.rateGridSuccess.click();
			testSteps.add("Click on Success Icon");
			logger.info("Click on Success Icon");
			Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
			assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
			testSteps.add("Verified Message : <b> Rate saved successfully </b>");
			logger.info("Verified Message : Rate saved successfully");
			try{
				Wait.waitForElementToBeGone(driver, elements.rateSavedMessage, 10);
			}catch(Exception e){
				
			}
			return testSteps;

		}
		
		// Added By Adhnan 08/18/2020
		public ArrayList<String> verifyBaseRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
				String rateValue, int index) throws InterruptedException {
			ArrayList<String> testSteps = new ArrayList<>();
			testSteps.add("Verify Rate Value of Base Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
			logger.info("Verify Rate Value of Base Rate Plan '"+ratePlanName+"' for Room Class '"+roomClassName+"'");
			String xpath = "(//span[text()='" + ratePlanName
					+ "']//ancestor::div[@id='ratePlans']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
					+ roomClassName + "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])[" + (index + 1)
					+ "]";
			// Wait.explicit_wait_xpath(xpath, driver);
			WebElement elementrequired = driver.findElement(By.xpath(xpath));
			Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
			Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
			String foundRateValue = elementrequired.getText();
			testSteps.add("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
			logger.info("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
			testSteps.add("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
			logger.info("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
			assertEquals(foundRateValue, rateValue, "Failed:  Derived rate Plan rate Value missmatcehd");
			testSteps.add("Successfully verified Base Rate Plan Rate Value : '" + foundRateValue + "'");
			logger.info("Successfully verified  Base Rate Plan Rate Value : '" + foundRateValue + "'");
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
   				logger.info("start: "+getDate);
				listOfStartDate.add(getDate);
				
				getDate = getEndDate[i].trim();
				if (ESTTimeZone.CompareDates(getDate, "MM/dd/yyyy", timeZone)) {
  					 getCurrentDate = Utility.getNextDate(addDays, "MM/dd/yyyy", timeZone);
  					getDate = getCurrentDate;
  				}
  			
				logger.info("end: "+getDate);
				listOfEndDate.add(getDate);
				addDays = addDays+2;
			}
   			int count = 0;
   			
   			for (int i = 0; i < listOfStartDate.size(); i++) {
				
   				logger.info("i: "+i);
   				testSteps.add("=================== SELECT START DATE ======================");
   				logger.info("=================== SELECT START DATE ======================");
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
   				logger.info("=================== SELECT END DATE ======================");
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

			logger.info("Before Click plus button '"+(beforeCustomDates/2)+"' Custome Range is displaying");
			test_steps.add("Before Click plus button '"+(beforeCustomDates/2)+"' Custome Range is displaying");
			Wait.WaitForElement(driver, DerivedRatePage.ADD_CUSTOM_DATE);
			Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.ADD_CUSTOM_DATE), driver);
			Wait.waitForElementToBeClickable(By.xpath( DerivedRatePage.ADD_CUSTOM_DATE), driver);
			//Utility.ScrollToElement(elements.ADD_CUSTOM_DATE, driver);
			elements.ADD_CUSTOM_DATE.click();
			logger.info("Click plus button of Custom Date Range");
			return test_steps;
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
			logger.info("Click on Calender");
			try{
				Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.getCalendarMonthHeading), driver);
			}catch(Exception e){
				Wait.WaitForElement(driver, DerivedRatePage.SELECT_CUSTOM_DATE);
				Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE), driver);
				elements.SELECT_CUSTOM_DATE.get(index).click();
				Wait.wait1Second();
				logger.info("Again Click on Calender");
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
						logger.info("Select Arrival Date");
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

	// Added By Adhnan 08/18/2020
	public ArrayList<String> verifyDerivedRatePlanRuleValue(WebDriver driver, String ratePlanName, String roomClassName,
			String rateValue, int index) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		testSteps.add(
				"Verify Rate Value of Derived Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		logger.info(
				"Verify Rate Value of Derived Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		testSteps.add("Index '" + index + "'");
		logger.info("Index '" + index + "'");
		String xpath = "(//span[text()='" + ratePlanName
				+ "']//ancestor::div[@class='DerivedPlan']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
				+ roomClassName + "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])["
				+ (index + 1) + "]";
		// Wait.explicit_wait_xpath(xpath, driver);
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		String foundRateValue = elementrequired.getText();
		testSteps.add("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
		logger.info("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
		testSteps.add("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		logger.info("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		assertEquals(foundRateValue, rateValue, "Failed:  Derived rate Plan rate Value missmatcehd");

		testSteps.add("Successfully verified Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		logger.info("Successfully verified Derived Rate Plan Rate Value : '" + foundRateValue + "'");
		return testSteps;

	}

	// Added By Adhnan 09/02/2020
	public ArrayList<String> verifyDerivedRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
			String rateValue, String startDate, String endDate, String dateFormat, String timeZone,
			HashMap<String, Boolean> dayMap) throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<>();
		testSteps.add(
				"Verify Rate Value of Derived Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		logger.info(
				"Verify Rate Value of Derived Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
		for (int index = 0; index <= days; index++) {
			String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", index, timeZone);
			System.out.println("day : " + day);
			if (dayMap.get(day)) {
				testSteps.add("Date Index '" + index + "'");
				logger.info("Index '" + index + "'");
				String xpath = "(//span[text()='" + ratePlanName
						+ "']//ancestor::div[@class='DerivedPlan']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
						+ roomClassName
						+ "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])["
						+ (index + 1) + "]";
				// Wait.explicit_wait_xpath(xpath, driver);
				WebElement elementrequired = driver.findElement(By.xpath(xpath));
				Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
				Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
				String foundRateValue = elementrequired.getText();
				//testSteps.add("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
				logger.info("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
				//testSteps.add("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
				logger.info("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
				assertEquals(foundRateValue, rateValue, "Failed:  Derived rate Plan rate Value missmatcehd");

				testSteps.add("Successfully verified Derived Rate Plan Rate Value : '" + foundRateValue + "'");
				logger.info("Successfully verified Derived Rate Plan Rate Value : '" + foundRateValue + "'");
			}
		}
		return testSteps;

	}

	// Added By Adhnan 08/18/2020
	public ArrayList<String> verifyBaseRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
			String rateValue, String startDate, String endDate, String dateFormat, String timeZone,
			HashMap<String, Boolean> dayMap) throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<>();
		testSteps.add(
				"Verify Rate Value of Base Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		logger.info(
				"Verify Rate Value of Base Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
		for (int index = 0; index <= days; index++) {
			String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", index, timeZone);
			System.out.println("day : " + day);
			if (dayMap.get(day)) {

				testSteps.add("Date Index '" + index + "'");
				String xpath = "(//span[text()='" + ratePlanName
						+ "']//ancestor::div[@id='ratePlans']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
						+ roomClassName
						+ "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])["
						+ (index + 1) + "]";
				Wait.explicit_wait_xpath(xpath, driver);
				WebElement elementrequired = driver.findElement(By.xpath(xpath));
				Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
				Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
				String foundRateValue = elementrequired.getText();
				//testSteps.add("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
				logger.info("Expected Derived Rate Plan Rate Value : '" + rateValue + "'");
				//testSteps.add("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
				logger.info("Found Derived Rate Plan Rate Value : '" + foundRateValue + "'");
				assertEquals(foundRateValue, rateValue, "Failed:  Derived rate Plan rate Value missmatcehd");
				testSteps.add("Successfully verified Base Rate Plan Rate Value : '" + foundRateValue + "'");
				logger.info("Successfully verified  Base Rate Plan Rate Value : '" + foundRateValue + "'");
			}
		}
		return testSteps;

	}

	// Added By Adhnan 09/02/2020
	public ArrayList<String> changeDerivedRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
			String rateValue, String startDate, String endDate, String dateFormat, String timeZone,
			HashMap<String, Boolean> dayMap) throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		testSteps.add("Override Rate Value of Derived Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName
				+ "'");
		logger.info("Override Rate Value of Derived Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName
				+ "'");
		int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
		for (int index = 0; index <= days; index++) {
			String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", index, timeZone);
			System.out.println("day : " + day);
			if (dayMap.get(day)) {
				String xpath = "(//span[text()='" + ratePlanName
						+ "']//ancestor::div[@class='DerivedPlan']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
						+ roomClassName
						+ "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])["
						+ (index + 1) + "]";
				// Wait.explicit_wait_xpath(xpath, driver);
				WebElement elementrequired = driver.findElement(By.xpath(xpath));
				Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
				Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
				String oldRateValue = elementrequired.getText();
				testSteps.add("Before Change Rate Value of  : '" + oldRateValue + "'");
				logger.info("Before Change Rate Value : '" + oldRateValue + "'");
				elementrequired.click();
				testSteps.addAll(changeDate(driver, rateValue));
				elementrequired = driver.findElement(By.xpath(xpath));
				String newRateValue = elementrequired.getText();
				testSteps.add("After Change Rate Value : '" + newRateValue + "'");
				logger.info("After Change Rate Value : '" + newRateValue + "'");

				assertEquals(newRateValue, rateValue, "Failed:  Changes rate Plan rate Value");
				assertFalse(newRateValue.equals(oldRateValue), "Failed:  Changes rate Plan rate Value missmatcehd");

				testSteps.add("Successfully override Derived Rate Plan Rate Value : '" + rateValue + "'");
				logger.info("Successfully override  Derived Rate Plan Rate Value : '" + rateValue + "'");
			}
		}
		return testSteps;
	}

	// Added By Adhnan 09/02/2020
	public ArrayList<String> changeBaseRatePlanRateValue(WebDriver driver, String ratePlanName, String roomClassName,
			String rateValue, String startDate, String endDate, String dateFormat, String timeZone,
			HashMap<String, Boolean> dayMap) throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<>();
		testSteps.add(
				"Override Rate Value of Base Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		logger.info(
				"Override Rate Value of Base Rate Plan '" + ratePlanName + "' for Room Class '" + roomClassName + "'");
		int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
		for (int index = 0; index <= days; index++) {
			String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", index, timeZone);
			System.out.println("day : " + day);
			if (dayMap.get(day)) {
				String xpath = "(//span[text()='" + ratePlanName
						+ "']//ancestor::div[@id='ratePlans']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
						+ roomClassName
						+ "']//parent::div//following-sibling::div[contains(@class,'RateDateCell')]/div[1])["
						+ (index + 1) + "]";
				// Wait.explicit_wait_xpath(xpath, driver);
				WebElement elementrequired = driver.findElement(By.xpath(xpath));
				Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
				Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
				String oldRateValue = elementrequired.getText();
				testSteps.add("Before Change Rate Value : '" + oldRateValue + "'");
				logger.info("Before Change Rate Value : '" + oldRateValue + "'");
				try {
					elementrequired.click();
				} catch (Exception e) {
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("window.scrollBy(0,-300)");
					elementrequired.click();
				}
				testSteps.addAll(changeDate(driver, rateValue));
				elementrequired = driver.findElement(By.xpath(xpath));
				String newRateValue = elementrequired.getText();
				testSteps.add("After Change Rate Value : '" + newRateValue + "'");
				logger.info("After Change Rate Value : '" + newRateValue + "'");

				assertEquals(newRateValue, rateValue, "Failed:  Changes rate Plan rate Value");
				assertFalse(newRateValue.equals(oldRateValue), "Failed:  Changes rate Plan rate Value missmatcehd");

				testSteps.add("Successfully override Base Rate Plan Rate Value : '" + rateValue + "'");
				logger.info("Successfully override  Base Rate Plan Rate Value : '" + rateValue + "'");
			}
		}
		return testSteps;

	}
	

	// Added By Adhnan 08/26/2020
	public ArrayList<String> verifyBestAvailableDerivedRRateValue(WebDriver driver, String ratePlanName,
			String rateValue,  String startDate, String endDate, String dateFormat, String timeZone,
			HashMap<String, Boolean> dayMap) throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<>();
		int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
		for (int index = 0; index <= days; index++) {
			String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", index, timeZone);
			System.out.println("day : " + day);
			if (dayMap.get(day)) {
		String xpath = "(//span[text()='" + ratePlanName
				+ "']//ancestor::div[contains(@class,'DerivedPlanItem ')]//div[contains(@class,'BestAvailableHeader')]//following-sibling::div/div[@class=' RegularRate'])["
				+ (index + 1) + "]";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		String foundRateValue = elementrequired.getText();
		testSteps.add("Expected Best Available Derived  Rate Value : '" + rateValue + "'");
		logger.info("Expected Best Available Derived  Rate Value : '" + rateValue + "'");
		testSteps.add("Found Best Available Derived  Rate Value : '" + foundRateValue + "'");
		logger.info("Found Best Available Derived  Rate Value : '" + foundRateValue + "'");
		assertEquals(foundRateValue, rateValue, "Failed:  Derived rate Plan rate Value missmatcehd");
			}
		}
		return testSteps;

	}

		
	//Added By Adhnan 09/03/2020
	// This will verify the derived rate Plan rate values for given roomClasses 
	public ArrayList<String> verifyDerivedRatePlanRoomClassRateValues(WebDriver driver, String roomClassNames,
			String startDate, String endDate, String dateFormat, String timeZone, HashMap<String, Boolean> dayMap,
			String ratePlanName, String[] rates, String amountValue,String comparator, String currencyOptions) throws ParseException, InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		ArrayList<String> getTestSteps = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(roomClassNames, Utility.DELIM);
		int rateIndex = 0;
		String derivedRateValue = null;
		int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
		while (token.hasMoreTokens()) {
			String roomClass = token.nextToken();
			testSteps.add("====== VERIFY Room Class '" + roomClass + "' DERIVED RATE VALUES =========");
			logger.info("======  VERIFY Room Class '" + roomClass + "' DERIVED RATE VALUES =========");
			if(currencyOptions.equalsIgnoreCase("percent")){
				if(comparator.equalsIgnoreCase("greater than")){
					derivedRateValue = String.format("%.2f", Float.parseFloat(rates[rateIndex])
							+ ((Float.parseFloat(rates[rateIndex]) * Float.parseFloat(amountValue)) / 100));
				}else if(comparator.equalsIgnoreCase("Lesser than")){
					derivedRateValue = String.format("%.2f", Float.parseFloat(rates[rateIndex])
							- ((Float.parseFloat(rates[rateIndex]) * Float.parseFloat(amountValue)) / 100));
				}
			}else if(comparator.equalsIgnoreCase("greater than")){
				 derivedRateValue = String.format("%.2f",
						Float.parseFloat(rates[rateIndex]) + Float.parseFloat(amountValue));
				}else if(comparator.equalsIgnoreCase("lesser than")){
					 derivedRateValue = String.format("%.2f",
							Float.parseFloat(rates[rateIndex]) - Float.parseFloat(amountValue));
				}
			for (int i = 0; i < 20; i++) {
				String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", i, timeZone);
				System.out.println("day : " + day);
				if (i <= days && dayMap.get(day)) {
					
					verifyDerivedRatePlanRateValue(driver,
							ratePlanName, roomClass, derivedRateValue, i);
				} else {
					verifyDerivedRatePlanRateValue(driver,
							ratePlanName, roomClass, "--", i);
				}
			}
			testSteps.add("Successfully verified rate value during base season is Derived Rate Value '"
					+ derivedRateValue + "'");
			logger.info("Successfully verified rate value during base season is Derived Rate Value '"
					+ derivedRateValue + "'");
			testSteps.add("Successfully verified for the remaining dates '--' is showing ");
			logger.info("Successfully verified for the remaining dates '--' is showing ");
			rateIndex++;
			testSteps.add("Successfully verified Room Class '" + roomClass + "' Derived Rate values");
			logger.info("Successfully verified Room Class '" + roomClass + "' Derived Rate values");
		}
		return testSteps;
	}
	

	public ArrayList<String> selectCustomStartAndEndDates(WebDriver driver, String startDate, String endDate) throws ParseException, InterruptedException {
//		String getStartDate[] = startDate.split(Utility.DELIM);
//		String getEndDate[] = endDate.split(Utility.DELIM);
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> listOfStartDate = Utility.convertTokenToArrayList(startDate, Utility.DELIM);
		ArrayList<String> listOfEndDate =  Utility.convertTokenToArrayList(endDate, Utility.DELIM);
//			
//			for (int i = 0; i < getStartDate.length; i++) {
//				
//				
//				String getDate = getStartDate[i].trim(); 
//				String getCurrentDate = Utility.getNextDate(0, "MM/dd/yyyy", timeZone);
//				if (ESTTimeZone.CompareDates(getDate, "MM/dd/yyyy", timeZone)) {
//					getDate = getCurrentDate;
//				}
//				logger.info("start: "+getDate);
//			listOfStartDate.add(getDate);
//			
//			getDate = getEndDate[i].trim();
//			if (ESTTimeZone.CompareDates(getDate, "MM/dd/yyyy", timeZone)) {
//					 getCurrentDate = Utility.getNextDate(addDays, "MM/dd/yyyy", timeZone);
//					getDate = getCurrentDate;
//				}
//			
//			logger.info("end: "+getDate);
//			listOfEndDate.add(getDate);
//			addDays = addDays+2;
//		}
			int count = 0;
			
			for (int i = 0; i < listOfStartDate.size(); i++) {
			
				logger.info("i: "+i);
				testSteps.add("=================== SELECT START DATE ======================");
				logger.info("=================== SELECT START DATE ======================");
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
				logger.info("=================== SELECT END DATE ======================");
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

	//Added By Farhan 09/08/2020
	public boolean verifyTakeRuleFromParentRatePlanIsChecked(WebDriver driver) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX, driver);
		Utility.ScrollToElement(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		assertTrue(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.isDisplayed(),
				"Failed To Verify Take Rule from parent rate plan checkBox is not Displayed");
		boolean isChecked  = false;
		if (elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.getAttribute("class").contains("checked")) {
			assertEquals(elements.TAKE_RULES_FROM_PARENTS_CHECKBOX.getAttribute("class").contains("checked"), true,
					"Failed : checkBox is not in checked state");
			isChecked = true;
		}else {
			isChecked = false;
		}
		return isChecked;
	}
	
	
	//Added By Farhan 09/08/2020
	public ArrayList<String> getParentPlanOffset(WebDriver driver) throws InterruptedException {
		ArrayList<String> parentPlanOffSet = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		String rate = getRateValue(driver, getTestSteps);
		logger.info("rate : " + rate);
		String getCurrencyOption = getDropdownValue(driver, 1, getTestSteps);
		logger.info("getCurrencyOption : " + getCurrencyOption);
		String getIncrementOption = getDropdownValue(driver, 2, getTestSteps);
		logger.info("getIncrementOption : " + getIncrementOption);
		boolean isChecked = verifyTakeRuleFromParentRatePlanIsChecked(driver);
		logger.info("isChecked : " + isChecked);
		parentPlanOffSet.add(rate);
		parentPlanOffSet.add(getCurrencyOption);
		parentPlanOffSet.add(getIncrementOption);
		parentPlanOffSet.add(String.valueOf(isChecked));
	
		return parentPlanOffSet;
	}
	
	//Added By Farhan 09/08/2020
	public boolean verifySeasonTypeIsChecked(WebDriver driver, String type) throws InterruptedException {
		String xpath = "(//span[text()='" + type + "']/parent::label//span[contains(@class,'ant-radio')])[1]";
		WebElement radioButton = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(radioButton, driver);
		boolean isChecked = false;
		if(radioButton.getAttribute("class").contains("checked")) {
			isChecked = true;
		}else{
			isChecked = false;
		}
		return isChecked;
	}
	

	//Added By Farhan 09/08/2020
	public HashMap<Integer, String> getCustomDates(WebDriver driver, String seasonType, String dateToReturn) throws InterruptedException {
		HashMap<Integer, String> map = new HashMap<>();
		if(seasonType.equalsIgnoreCase("Custom date range")) {
			boolean isChecked = verifySeasonTypeIsChecked(driver, seasonType);
			logger.info("isChecked : " + isChecked);
			if(isChecked) {
				Wait.WaitForElement(driver, DerivedRatePage.SELECT_CUSTOM_DATE);
				Wait.waitForElementToBeVisibile(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE), driver);
				Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE), driver);
				List<WebElement> DateElement = driver.findElements(By.xpath(DerivedRatePage.SELECT_CUSTOM_DATE));
				String getStartDate = "";
				String getEndDate = "";
				for(int i=0; i < DateElement.size(); i++) {				
					if(getStartDate.isEmpty() || getStartDate.equals("")) {
						getStartDate = DateElement.get(i).getAttribute("value");
						logger.info(+ i  + " : getStartDate : " + getStartDate);					
					}else {
						getStartDate = getStartDate + "," + DateElement.get(i).getAttribute("value");
						logger.info(+ i  + " : getStartDate : " + getStartDate);					
					}
					if(getEndDate.isEmpty()|| getEndDate.equals("")) {
						getEndDate = DateElement.get(++i).getAttribute("value");
						logger.info(+ i  + " : getEndDate : " + getEndDate);
					}else {
						getEndDate = getEndDate +  "," + DateElement.get(++i).getAttribute("value");
						logger.info(+ i  + " : getEndDate : "  + getEndDate);
					}	
				}
				if(dateToReturn.equalsIgnoreCase("Start Date")) {
					int size  = getStartDate.split(",").length;
					for(int i=0; i < size; i++ ) {
						logger.info(+ i  + " : Store StartDate : " +  getStartDate.split(",")[i]);					
						map.put(i, getStartDate.split(",")[i]);
						
					}				
				}else {
					int size  = getEndDate.split(",").length;
					for(int i=0; i < size; i++ ) {
						logger.info(+ i  + " : Store EndDate : " +  getEndDate.split(",")[i]);					
						map.put(i, getEndDate.split(",")[i]);
						
					}
				}

			}
		}else {
			boolean isChecked = verifySeasonTypeIsChecked(driver, seasonType);
			logger.info("isChecked : " + isChecked);
				
		}
		
		return map;
	}

	// Added By Adhnan
	public ArrayList<String> selectCustomStartAndEndDates(WebDriver driver, String startDate, String endDate,
			String dateFormat) throws ParseException, InterruptedException {
		String listOfStartDate[] = startDate.split(Utility.DELIM);
		String listOfEndDate[] = endDate.split(Utility.DELIM);
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> testSteps = new ArrayList<>();

		int count = 0;

		for (int i = 0; i < listOfStartDate.length; i++) {

			logger.info("i: " + i);
			testSteps.add("=================== SELECT START DATE ======================");
			logger.info("=================== SELECT START DATE ======================");

			getTestSteps.clear();
			getTestSteps = selectCustomDateFromCalender(driver, count, listOfStartDate[i], dateFormat);
			testSteps.addAll(getTestSteps);
			try {
				getTestSteps.clear();
				getTestSteps = clickDateRangeMissmatchedPopupButton(driver, "Yes");
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

			}
			count = count + 1;
			testSteps.add("=================== SELECT END DATE ======================");
			logger.info("=================== SELECT END DATE ======================");
			getTestSteps.clear();
			getTestSteps = selectCustomDateFromCalender(driver, count, listOfEndDate[i], dateFormat);
			testSteps.addAll(getTestSteps);

			count = count + 1;
			try {
				getTestSteps.clear();
				getTestSteps = clickDateRangeMissmatchedPopupButton(driver, "Yes");
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

			}

			// here added one more date
			if (i < (listOfEndDate.length - 1)) {
				Wait.wait2Second();
				getTestSteps.clear();
				getTestSteps = clickAddCustomDate1(driver);
				testSteps.addAll(getTestSteps);

			}

		}

		return testSteps;
	}
	
	public Boolean isDerivedratePlanExist(WebDriver driver, String ratePlanName, ArrayList<String> testSteps) {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);

		int size = elements.DERIVED_RATE_PLAN_NAME_LIST.size();
		logger.info("Derived Rate Plan List Size : " + size);
		boolean found = false;
		for (int i = 0; i < size; i++) {
			if (elements.DERIVED_RATE_PLAN_NAME_LIST.get(i).getText().equals(ratePlanName)) {
				found = true;
				break;
			}
		}
		testSteps.add("Derived rate plan '" + ratePlanName + "' existence : " + found);
		logger.info("Derived rate plan '" + ratePlanName + "' existence : " + found);
		return found;
	}
	
	public String calculateOffSet(String actualAmount, String value, String valueType,String calculationMode) {
		String finalAmount = "0";
		if (valueType.equalsIgnoreCase("percent")) {
			String found = Utility.getPercentage(value, actualAmount);
			
			if(calculationMode.equalsIgnoreCase("greater than")) {
				finalAmount = (Float.parseFloat(found) + Float.parseFloat(actualAmount))+"";
			} else if(calculationMode.equalsIgnoreCase("lesser than")) {
				finalAmount = Math.abs(Float.parseFloat(found) - Float.parseFloat(actualAmount))+"";
			}
			
		} else if (valueType.equalsIgnoreCase("USD")) {
			if(calculationMode.equalsIgnoreCase("greater than")) {
				finalAmount = (Float.parseFloat(value) + Float.parseFloat(actualAmount))+"";
			} else if(calculationMode.equalsIgnoreCase("lesser than")) {
				finalAmount = Math.abs(Float.parseFloat(value) - Float.parseFloat(actualAmount))+"";
			}
		}
		
		return finalAmount;
		
	}
	
//	public static void main(String[] args) {
//		System.out.println(calculateOffSet("160", "10", "percent", "greater than"));
//	}

	public String getSelectedSeasonType(WebDriver driver) {
		Wait.waitUntilPageLoadNotCompleted(driver, 40);
		String path = "//span[contains(@class,'ant-radio ant-radio-checked')]/following-sibling::span";
		Wait.explicit_wait_visibilityof_webelement_600( driver.findElement(By.xpath(path)), driver);
		String found = driver.findElement(By.xpath(path)).getText();
		found = found.split("\n")[0];
		return found;
	}

	/*
	 * ##########################################################################################################################################################################
	 * 
	 * ' Method Name: <createDerivedRatePlanFromParentRatePlan> 
	 * ' Description: <Create parent rate  plan then create derived rate Plan from parent rate plan> 
	 * ' Input parameters: < WebDriver driver, String ratePlanType, String ratePlanName, String folioDisplayName, String ratePlanDescription,
			String parentRatePlanName, String comparisonOption, String currencyOptions, String amount, boolean isTakeRuleFromParentChecked, String dateType, String startDate, String endDate, String dateFormat,
			String Channels, String RoomClasses> 
	 * ' Return value: <ArrayList>
	 * ' Created By: <Adhnan Ghaffar>
	 * ' Created On:  <10/13/2020>
	 * 
	 * ##########################################################################################################################################################################
	 */	
	public ArrayList<String> createDerivedRatePlanFromParentRatePlan(WebDriver driver,String delim, String parentRatePlanType, String parentRatePlanName, String parentRateFolioName,
			String parentRateDescription, String parentRoomClass, String parentRoomClassSize, String parentBaseAmount,
			String addtionalAdult, String additionalChild, String intervalRatePlanIntervalValue,
			String isDefaultProrateChecked, String packageRatePlanRateType, String packageRatePlanProductName,
			String packageratePlanProductFirstCalculationMethod, String packageratePlanProductsecondCalculationMethod,
			String packageRatePlanProductAmount, String channels, String isRatePlanRistrictionReq,
			String ristrictionType, String isMinStay, String minNights, String isMaxStay, String maxNights,
			String isMoreThanDaysReq, String moreThanDaysCount, String isWithInDaysReq, String withInDaysCount,
			String promoCode, String isPolicesReq, String policiesType, String policiesName, String seasonDelim,
			String seasonName, String seasonStartDate, String seasonEndDate, String isMonDay, String isTueDay,
			String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason,
			String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults,
			String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight,
			String extraRoomClassAdditionalChildPerNight, String isSeasonLevelRules, String isAssignRulesByRoomClass,
			String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String isSeasonPolicies, String seasonPolicyType, String seasonPolicyValues,
			String isAssignPoliciesByRoomClass, String seasonPolicySpecificRoomClasses, String isProRateStayInSeason,
			String isProRateInRoomClass, String isCustomPerNight, String customRoomClasses, String customRatePerNight,
			String isCustomRatePerNightAdultandChild, String customRateChildPerNight, String customRateAdultdPerNight,
			String roomClassesWithProRateUnchecked,String derivedRatePlanName, String derivedRatePlanFolioDisplayName, 
			String derivedRatePlanDescription,String istakenRulesFromParentRateplan, String dateRange, String customStartDate,
			String customEndDate,
			String selectComparator, String derivedRateType, String derivedRateValue, String derivedRateRoomClasses,
			String derivedRateChannels, String derivedRateIsRatePlanRistrictionReq, String derivedRateRistrictionType,
			String derivedRateIsMinStay, String derivedRateMinNights, String derivedRateIsMaxStay,
			String derivedRateMaxNights, String derivedRateIsMoreThanDaysReq, String derivedRateMoreThanDaysCount,
			String derivedRateIsWithInDaysReq, String derivedRateWithInDaysCount, String derivedRatePromoCode,
			String derivedRateIsPolicesReq, String derivedRatePoliciesType, String derivedRatePoliciesName
			) throws Exception {

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		DerivedRate derivedRate = new DerivedRate();
		RatePackage ratePackage = new RatePackage();
		Policies policies = new Policies();
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		

		String restrictionsSummary = null;
		boolean isProrateCheckbox = false;
		boolean isIntervalRateplan = false;
		String summaryChannels = null;
		String summaryRoomClasses = null;
		String derivedRateValues = "";
		String customDateRange = "Custom date range";
		String seasonDateFormat = "dd/M/yyyy";

		testSteps.add("=================== CREATE PARENT(" + parentRatePlanType.toUpperCase()
				+ ") RATE PLAN ======================");
		logger.info("=================== CREATE PARENT(" + parentRatePlanType.toUpperCase()
				+ ") RATE PLAN ======================");

		getTestSteps.clear();
		getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, parentRatePlanType);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType);
		testSteps.addAll(getTestSteps);

		testSteps.add(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
		logger.info(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

		nightlyRate.enterRatePlanName(driver, parentRatePlanName, testSteps);
		nightlyRate.enterRateFolioDisplayName(driver, parentRateFolioName, testSteps);
		nightlyRate.enterRatePlanDescription(driver, parentRateDescription, testSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		if (parentRatePlanType.equalsIgnoreCase("Package rate plan")) {

			testSteps.add("=================== SELECT RATE TYPE ======================");
			logger.info("=================== SELECT RATE TYPE ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.selectParentRatePlan(driver, packageRatePlanRateType);
			testSteps.addAll(getTestSteps);
			if (packageRatePlanRateType.equalsIgnoreCase("Interval rates")) {
				isIntervalRateplan = true;
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
				testSteps.addAll(getTestSteps);

				isProrateCheckbox = Boolean.parseBoolean(isDefaultProrateChecked);
				getTestSteps.clear();
				getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, isProrateCheckbox);
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== ADDING PRODUCT IN PACKAGE RATE PLAN ======================");
			logger.info("=================== ADDING PRODUCT IN PACKAGE RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.addProducts(driver, packageRatePlanProductName, packageRatePlanProductAmount,
					packageratePlanProductFirstCalculationMethod, packageratePlanProductsecondCalculationMethod);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		}

		if (parentRatePlanType.contains("Interval")) {
			isIntervalRateplan = true;
			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== ENTER INTERVAL RATE PLAN LENGTH ======================");
			logger.info("=================== ENTER INTERVAL RATE PLAN LENGTH ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
			testSteps.addAll(getTestSteps);
			isProrateCheckbox = Boolean.parseBoolean(isDefaultProrateChecked);
			getTestSteps.clear();
			getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, isProrateCheckbox);
			testSteps.addAll(getTestSteps);
			nightlyRate.clickNextButton(driver, testSteps);
		}

		testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
		logger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType, testSteps);
		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, testSteps);
		if (parentRatePlanType.contains("Interval")) {
			String IsProrateCheckboxChecked = intervalRatePlanIntervalValue + " nights;";
			if (isProrateCheckbox) {
				IsProrateCheckboxChecked = IsProrateCheckboxChecked + " " + "prorate cost for partial stay";
			}
			nightlyRate.verifyTitleSummaryValue(driver, "Interval Length", IsProrateCheckboxChecked, testSteps);
		}
		nightlyRate.selectChannels(driver, channels, true, testSteps);
		if (parentRatePlanType.equalsIgnoreCase("Interval rate plan")
				|| parentRatePlanType.equalsIgnoreCase("Package rate plan")) {
			summaryChannels = channels;
		} else {
			summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
		}
		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
		testSteps.addAll(getTestSteps);

		testSteps.add("=================== SELECT ROOM CLASSES ======================");
		logger.info("=================== SELECT ROOM CLASSES ======================");
		ArrayList<String> roomClasses = new ArrayList<String>();
		roomClasses = nightlyRate.getAllRoomClassesNames(driver);
		if (!parentRoomClass.equals("") || parentRoomClass != null) {
			parentRoomClass = nightlyRate.verifyRoomClassesExist(driver, parentRoomClass, roomClasses);
		}
		if (parentRoomClass.equals("")) {
			String[] parentBaseAmountList = parentBaseAmount.split(Utility.DELIM);
			logger.info("Room Classes : " + roomClasses);
			parentRoomClass = "";

			String derivedValue = null;
			for (int i = 0; i < Integer.parseInt(parentRoomClassSize); i++) {
				if (i < roomClasses.size() && i < 6) {
					if (i != 0) {
						parentRoomClass = parentRoomClass + delim;
					}
					parentRoomClass = parentRoomClass + roomClasses.get(i);

					if (derivedRateType.equals("percent")) {
						if (selectComparator.equals("greater than")) {
							derivedValue = Integer.toString(Integer.parseInt(parentBaseAmountList[i])
									+ (Integer.parseInt(parentBaseAmountList[i])
											* Integer.parseInt(derivedRateValue)));
						} else if (selectComparator.equals("lesser than")) {
							derivedValue = Integer.toString(Integer.parseInt(parentBaseAmountList[i])
									- (Integer.parseInt(parentBaseAmountList[i])
											* Integer.parseInt(derivedRateValue)));
						}
					} else {
						if (selectComparator.equals("greater than")) {
							derivedValue = Integer.toString(
									Integer.parseInt(parentBaseAmountList[i]) + Integer.parseInt(derivedRateValue));
						} else if (selectComparator.equals("lesser than")) {
							derivedValue = Integer.toString(
									Integer.parseInt(parentBaseAmountList[i]) - Integer.parseInt(derivedRateValue));
						}
					}
				}
				if (i == 0) {
					derivedRateValues = derivedValue;
				} else {

					derivedRateValues = derivedRateValues + Utility.DELIM + derivedValue;
				}
			}
		}
		logger.info("Parent Room Clas : " + parentRoomClass);
		nightlyRate.selectRoomClasses(driver, parentRoomClass, true, testSteps);
		if (parentRoomClass.equals("All")) {
			summaryRoomClasses = "All room classes selected";
		} else {
			summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
		}

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses);
		testSteps.addAll(getTestSteps);
		nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
				Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
				Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
				withInDaysCount, promoCode, testSteps);

		restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);
		nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

		nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);

		HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
				Boolean.parseBoolean(isPolicesReq), testSteps);
		nightlyRate.clickNextButton(driver, testSteps);
		nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
				Boolean.parseBoolean(isPolicesReq), testSteps);
	

		testSteps.add("=================== CREATE SEASON ======================");
		logger.info("=================== CREATE SEASON ======================");
		nightlyRate.clickCreateSeason(driver, testSteps);
		nightlyRate.createUpdateSingleOrMultipleSeason(driver, testSteps, false, seasonStartDate, seasonEndDate,
				seasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
				parentRoomClass, isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
				additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason,
				extraRoomClassRatePerNight, extraRoomClassMaxAdults, extraRoomClassMaxPersons,
				extraRoomClassAdditionalAdultsPerNight, extraRoomClassAdditionalChildPerNight,
				isAssignRulesByRoomClass, isSeasonLevelRules, seasonRuleSpecificRoomClasses, seasonRuleType,
				seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday,
				isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday,
				isSeasonPolicies, seasonPolicyType, seasonPolicyValues, isAssignPoliciesByRoomClass,
				seasonPolicySpecificRoomClasses, isDefaultProrateChecked, isProRateStayInSeason,
				isProRateInRoomClass, roomClassesWithProRateUnchecked, isCustomPerNight, customRoomClasses,
				customRatePerNight, customRateAdultdPerNight, customRateChildPerNight,
				isCustomRatePerNightAdultandChild, false, intervalRatePlanIntervalValue, isIntervalRateplan);

		nightlyRate.clickCompleteChanges(driver, testSteps);

		try {
			nightlyRate.clickSaveAsActive(driver, testSteps);
		} catch (Exception f) {
			nightlyRate.clickCompleteChanges(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);
		}
		testSteps.add("PARENT " + parentRatePlanType.toUpperCase() + " CREATED");
		logger.info("PARENT " + parentRatePlanType.toUpperCase() + " CREATED");
	
		navigation.RatesGrid(driver);
		testSteps.add("Navigated to RatesGrid");
		testSteps.add("=== " + "Verify the newly created Parent(" + parentRatePlanType.toUpperCase()
				+ ") Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ===");
		logger.info("=== " + "Verify the newly created Parent(" + parentRatePlanType.toUpperCase()
				+ ") Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ===");
		try {
//			driver.navigate().refresh();
//			Wait.wait3Second();
//			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
		} catch (Exception e) {
			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
		}
		testSteps.add(
				"Successfully verified that newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down");
		logger.info(
				"Successfully verified that newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down");

	
		testSteps.add("=================== CREATE NEW DERIVED RATE PLAN ======================");
		logger.info("=================== CREATE NEW DERIVED RATE PLAN ======================");

		getTestSteps.clear();
		getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, "Derived rate plan");
	

		nightlyRate.enterRatePlanName(driver, derivedRatePlanName, testSteps);
		nightlyRate.enterRateFolioDisplayName(driver, derivedRatePlanFolioDisplayName, testSteps);
		nightlyRate.enterRatePlanDescription(driver, derivedRatePlanDescription, testSteps);
		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);


		testSteps.add("=================== " + "SELECT PARENT " + parentRatePlanType.toUpperCase()
				+ " ======================");
		logger.info("=================== " + "SELECT PARENT " + parentRatePlanType.toUpperCase()
				+ " ======================");

		derivedRate.selectRatePlan(driver, parentRatePlanName, true, testSteps);

		testSteps.add("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
				+ " ======================");
		logger.info("=================== " + "Select Parent Rate Plan Offset In Usd".toUpperCase()
				+ " ======================");

		derivedRate.expandCurrencyValueDropdown(driver, 1);
		testSteps.add("Expand Value Comparison DropDown");
		logger.info("Expand Value Comparison DropDown");
		derivedRate.selectDropDownOptions(driver, selectComparator, testSteps);
		derivedRate.expandCurrencyValueDropdown(driver, 0);
		testSteps.add("Expand Currency DropDown");
		logger.info("Expand Currency DropDown");

		derivedRate.selectDropDownOptions(driver, derivedRateType, testSteps);

		testSteps.add("===== ENTER VALUE =====");
		logger.info("===== ENTER VALUE =====");
		derivedRate.enterRateValue(driver, derivedRateValue, testSteps);
		String value = derivedRate.getRateValue(driver, testSteps);
		if (derivedRateType.equals("percent")) {
			assertEquals(value, derivedRateValue + "%", "Failed : Value missmatched");
		} else {
			assertEquals(value, derivedRateValue, "Failed : Value missmatched");
		}

		derivedRate.takeRuleFromParentRatePlanCheckBox(driver, Boolean.parseBoolean(istakenRulesFromParentRateplan),
				testSteps);
		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);
		testSteps.add("=================== SELECT DATES ======================");
		logger.info("=================== SELECT DATES ======================");
		derivedRate.selectDates(driver, dateRange, testSteps);
		if (dateRange.equals(customDateRange)) {
			derivedRate.customDateRangeAppear(driver, true, testSteps);
			getTestSteps.clear();
			getTestSteps = derivedRate.selectCustomStartAndEndDates(driver, customStartDate, customEndDate,
					seasonDateFormat);
			testSteps.addAll(getTestSteps);
		}
		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);
		testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
		logger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
		nightlyRate.selectChannels(driver, derivedRateChannels, true, testSteps);
		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		testSteps.add("=================== SELECT ROOM CLASSES ======================");
		logger.info("=================== SELECT ROOM CLASSES ======================");
		nightlyRate.selectRoomClasses(driver, derivedRateRoomClasses, true, testSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq),
				derivedRateRistrictionType, Boolean.parseBoolean(derivedRateIsMinStay), derivedRateMinNights,
				Boolean.parseBoolean(derivedRateIsMaxStay), derivedRateMaxNights,
				Boolean.parseBoolean(derivedRateIsMoreThanDaysReq), derivedRateMoreThanDaysCount,
				Boolean.parseBoolean(derivedRateIsWithInDaysReq), derivedRateWithInDaysCount, derivedRatePromoCode,
				testSteps);

		getTestSteps.clear();
		nightlyRate.verifySelectedRestriction(driver, derivedRateRistrictionType,
				Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq), getTestSteps);
		testSteps.addAll(getTestSteps);

		if (Boolean.parseBoolean(derivedRateIsRatePlanRistrictionReq)) {
			String foundMsg = nightlyRate.generateRestrictionsToQualifyRate(derivedRateRistrictionType,
					Boolean.parseBoolean(derivedRateIsMinStay), derivedRateMinNights,
					Boolean.parseBoolean(derivedRateIsMaxStay), derivedRateMaxNights,
					Boolean.parseBoolean(derivedRateIsMoreThanDaysReq), derivedRateMoreThanDaysCount,
					Boolean.parseBoolean(derivedRateIsWithInDaysReq), derivedRateWithInDaysCount,
					derivedRatePromoCode);

			getTestSteps.clear();
			nightlyRate.verfiyRestrictionsToQualifyRateMsg(driver, foundMsg, getTestSteps);
			testSteps.addAll(getTestSteps);
		}

		getTestSteps.clear();
		restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, getTestSteps);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		nightlyRate.selectPolicy(driver, derivedRatePoliciesType, derivedRatePoliciesName,
				Boolean.parseBoolean(derivedRateIsPolicesReq), testSteps);
		getTestSteps.clear();
		nightlyRate.verifySelectedPolicy(driver, derivedRatePoliciesType,
				Boolean.parseBoolean(derivedRateIsPolicesReq), getTestSteps);
		testSteps.addAll(getTestSteps);

		allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver,
				derivedRatePoliciesName, Boolean.parseBoolean(derivedRateIsPolicesReq), testSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		nightlyRate.clickSaveAsActive(driver, testSteps);
		Utility.rateplanName = derivedRatePlanName;
		testSteps.add("=================== DERIVED RATE PLAN CREATED ======================");
		logger.info("=================== DERIVED RATE PLAN CREATED ======================");
	
		navigation.RatesGrid(driver);
		testSteps.add("Navigated to RatesGrid");
		testSteps.add("=================== "
				+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
						.toUpperCase()
				+ " ======================");
		logger.info("=================== "
				+ "Verify the newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down"
						.toUpperCase()
				+ " ======================");
		driver.navigate().refresh();
		Wait.wait3Second();
		driver.navigate().refresh();
		ratesGrid.clickRatePlanArrow(driver, testSteps);
		ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, derivedRatePlanName);
		testSteps.add(
				"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
		logger.info(
				"Successfully verified that newly created Derived Rate Plan is seen in the Rates Grid's Rate Plan drop down");
		getTestSteps.clear();
		getTestSteps = ratesGrid.verifyDerivedRateDisplay(driver, derivedRatePlanName);
		testSteps.addAll(getTestSteps);
		return testSteps;

	}
	
	public void clickTabThroughJavaScript(WebDriver driver, String tab,ArrayList<String> test_steps) throws InterruptedException {
		String xpath = "//a[.='"+tab+"']";
		WebElement elementTab = null;
		try{
			elementTab = driver.findElement(By.xpath(xpath));
		}catch(Exception e) {
			xpath = "//a[contains(text(),'"+tab+"')]";
			elementTab = driver.findElement(By.xpath(xpath));
		}
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Utility.ScrollToElement(elementTab, driver);
		Utility.clickThroughJavaScript(driver, elementTab);
		test_steps.add("Click '"+tab+"' tab");
		logger.info("Click '"+tab+"' tab");
	
		
	}

	public void updateParentoffset(WebDriver driver,ArrayList<String> testSteps, String selectComparator, String derivedRateType, String derivedRateValue) throws InterruptedException {
		Wait.WaitForElement(driver, DerivedRatePage.SELECTED_RATEPLAN_DROPDOWN_ARROWS);
		testSteps.add("===== Update Comparison =====");
		logger.info("===== Update Comparison =====");
		expandCurrencyValueDropdown(driver, 1);
		testSteps.add("Expand Value Comparison DropDown");
		logger.info("Expand Value Comparison DropDown");
		selectDropDownOptions(driver, selectComparator, testSteps);
		testSteps.add("===== Update Currency =====");
		logger.info("===== Update Currency =====");				
		expandCurrencyValueDropdown(driver, 0);
		testSteps.add("Expand Currency DropDown");
		logger.info("Expand Currency DropDown");
		selectDropDownOptions(driver, derivedRateType, testSteps);
		testSteps.add("===== Update Value =====");
		logger.info("===== Update Value =====");
		enterRateValue(driver, derivedRateValue, testSteps);
	}
	
	
	
	public HashMap<String, ArrayList<HashMap<String, String>>> getRatesOnBasisDate(WebDriver driver,HashMap<String,String> drivedRates,String updateType){
		HashMap<String, ArrayList<HashMap<String, String>>> getRates= new HashMap<String, ArrayList<HashMap<String, String>>>();
		for(Entry<String,String> entry: drivedRates.entrySet()) {
			HashMap<String, String> baseRate= new HashMap<String, String>();
			ArrayList<HashMap<String, String>> amount= new ArrayList<HashMap<String, String>>();
			baseRate.put(updateType,entry.getValue());
			//amount.clear();
			amount.add(baseRate);
			getRates.put(entry.getKey(), amount);
		}
		return getRates;
	}
	public HashMap<String,String> calculateBaseRateExtraAdultAndChildAsperBaseRate(WebDriver driver, ArrayList<String> testSteps, HashMap<String, ArrayList<HashMap<String, String>>> ratesListAsperDate ,String selectComparator, String derivedRateType, 
			String derivedRateValue, String updateType) {
		HashMap<String,String> finalDrivedRate= new HashMap<String,String>();  
		ArrayList<HashMap<String, String>> baseRate= new ArrayList<HashMap<String, String>>();
		ArrayList<String> getDate= new ArrayList<String>();
		
		double amount=0.00;
		for(Entry<String, ArrayList<HashMap<String, String>>> entry: ratesListAsperDate.entrySet()) {
			
			baseRate.addAll(entry.getValue());
			getDate.add(entry.getKey());
				
		}
			for(int i=0;i<getDate.size();i++) {
			if(derivedRateType.equalsIgnoreCase("USD")) {
			if(selectComparator.equalsIgnoreCase("greater than")) {
		         amount=Double.parseDouble(Utility.convertDecimalFormat(baseRate.get(i).get(updateType)))+Double.parseDouble(Utility.convertDecimalFormat(derivedRateValue));			
			}else if(selectComparator.equalsIgnoreCase("lesser than")){
				 amount=Double.parseDouble(Utility.convertDecimalFormat(baseRate.get(i).get(updateType)))-Double.parseDouble(Utility.convertDecimalFormat(derivedRateValue));								
			}
			}else if(derivedRateType.equalsIgnoreCase("percent")) {
				if(derivedRateValue.contains("%")) {
					derivedRateValue=derivedRateValue.replace("%", "").trim();
					logger.info(derivedRateValue);
				}
				amount=(Double.parseDouble(Utility.convertDecimalFormat(baseRate.get(i).get(updateType)))*Double.parseDouble(Utility.convertDecimalFormat(derivedRateValue)))/100;				
				if(selectComparator.equalsIgnoreCase("greater than")) {
			         amount=Double.parseDouble(Utility.convertDecimalFormat(baseRate.get(i).get(updateType)))+amount;			
				}else if(selectComparator.equalsIgnoreCase("lesser than")){
					 amount=Double.parseDouble(Utility.convertDecimalFormat(baseRate.get(i).get(updateType)))-amount;								
				}
			}	
			finalDrivedRate.put(getDate.get(i), String.valueOf(amount));
		}
		
		return finalDrivedRate;
		
	}
	
}
