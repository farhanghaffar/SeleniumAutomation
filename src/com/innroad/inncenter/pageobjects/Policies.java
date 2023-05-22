
package com.innroad.inncenter.pageobjects;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.IPolicies;
import com.innroad.inncenter.model.CancellationPolicy;
import com.innroad.inncenter.pages.NewPolicies;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Inventory;
import com.innroad.inncenter.properties.OR_Setup;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.WebElements_Policies;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;


public class Policies implements IPolicies {

	public static Logger policiesLogger = Logger.getLogger("Policies");

	// selects the given policy type and clicks new policy button
	public ArrayList<String> NewPolicybutton(WebDriver driver, String PolicyType, ArrayList<String> test_steps)
			throws InterruptedException {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.New_Policy_Btn);
		Wait.waitForElementToBeVisibile(By.xpath(OR.New_Policy_Btn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.New_Policy_Btn), driver);
		CreatePolicy.New_Policy_Btn.click();
		policiesLogger.info("Click New Policy Button");
		test_steps.add("Click New policy button");
		try {
			Wait.waitForElementToBeVisibile(By.xpath(NewPolicies.Select_PolicyType), driver);
			new Select(CreatePolicy.Select_PolicyType).selectByVisibleText(PolicyType);
		} catch (Exception e) {
			Wait.waitForElementToBeClickable(By.xpath(OR.New_Policy_Btn), driver);
			Utility.ScrollToElement(CreatePolicy.New_Policy_Btn, driver);
			CreatePolicy.New_Policy_Btn.click();
			policiesLogger.info("Again Click New Policy Button");
			test_steps.add("Again Click New policy button");
			Wait.waitForElementToBeVisibile(By.xpath(NewPolicies.Select_PolicyType), driver);
			new Select(CreatePolicy.Select_PolicyType).selectByVisibleText(PolicyType);
		}
		policiesLogger.info("Select policy type : " + PolicyType);
		test_steps.add("Select policy type : " + PolicyType);

		return test_steps;

	}

	public void ClickNewPolicybutton(WebDriver driver) {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.New_Policy_Btn);
		CreatePolicy.New_Policy_Btn.click();
		policiesLogger.info("Click New Policy Button");
		Wait.explicit_wait_xpath(OR.Enter_Policy_Name, driver);

	}

	// enters given policy name
	public void Enter_Policy_Name(WebDriver driver, String PolicyName, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.Enter_Policy_Name);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Enter_Policy_Name), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Enter_Policy_Name), driver);
		// Utility.ScrollToElement(CreatePolicy.Enter_Policy_Name, driver);
		CreatePolicy.Enter_Policy_Name.clear();
		policiesLogger.info("Clear Policy Name");
		CreatePolicy.Enter_Policy_Name.sendKeys(PolicyName);
		policiesLogger.info("Enter Policy Name: " + PolicyName);
		test_steps.add("Enter Policy Name : <b> " + PolicyName + "</b>");

	}

	public void Clear_Policy_Name(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Utility.ScrollToElement(CreatePolicy.Enter_Policy_Name, driver);
		CreatePolicy.Enter_Policy_Name.clear();
	}

	public ArrayList<String> Verify_PolicyName_Validation(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement ele = CreatePolicy.PolicyName_Validation;
		Wait.explicit_wait_visibilityof_webelement(ele, driver);
		Utility.ScrollToElement(ele, driver);
		assertTrue(ele.isDisplayed(), "Failed: Policy Name Validation is not visible");
		// steps.add("Policy Name Validation : " + ele.getText());
		return steps;
	}

	public ArrayList<String> Verify_PolicyAttributes_Validation(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement ele = CreatePolicy.PolicyAttributes_Validation;
		Wait.explicit_wait_visibilityof_webelement(ele, driver);
		Utility.ScrollToElement(ele, driver);
		assertTrue(ele.isDisplayed(), "Failed: Policy Name Validation is not visible");
		// steps.add("Policy Attribute Validation : " + ele.getText());
		return steps;
	}

	public ArrayList<String> Verify_PolicyText_Validation(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement ele = CreatePolicy.PolicyText_Validation;
		Wait.explicit_wait_visibilityof_webelement(ele, driver);
		Utility.ScrollToElement(ele, driver);
		assertTrue(ele.isDisplayed(), "Failed: Policy Name Validation is not visible");
		// steps.add("Policy Text Validation : " + ele.getText());
		return steps;
	}

	public ArrayList<String> Verify_PolicyAssociateSource_Validation(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement ele = CreatePolicy.PolicyAssociateSource_Validation;
		Wait.explicit_wait_visibilityof_webelement(ele, driver);
		Utility.ScrollToElement(ele, driver);
		assertTrue(ele.isDisplayed(), "Failed: Policy Name Validation is not visible");
		// steps.add("Policy Associate Sources Validation : " + ele.getText());
		return steps;
	}

	public ArrayList<String> Verify_PolicyAssociateRatePlan_Validation(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement ele = CreatePolicy.PolicyAssociateRatePlan_Validation;
		Wait.explicit_wait_visibilityof_webelement(ele, driver);
		Utility.ScrollToElement(ele, driver);
		assertTrue(ele.isDisplayed(), "Failed: Policy Name Validation is not visible");
		// steps.add("Policy Associate RatePlan Validation : " + ele.getText());
		return steps;
	}

	public ArrayList<String> Verify_PolicyAssociateRoomClass_Validation(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement ele = CreatePolicy.PolicyAssociateRoomClass_Validation;
		Wait.explicit_wait_visibilityof_webelement(ele, driver);
		Utility.ScrollToElement(ele, driver);
		assertTrue(ele.isDisplayed(), "Failed: Policy Name Validation is not visible");
		// steps.add("Policy Associate RoomClass Validation : " +
		// ele.getText());
		return steps;
	}

	public ArrayList<String> Verify_PolicyAssociateSeasons_Validation(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement ele = CreatePolicy.PolicyAssociateSeasons_Validation;
		Wait.explicit_wait_visibilityof_webelement(ele, driver);
		Utility.ScrollToElement(ele, driver);
		assertTrue(ele.isDisplayed(), "Failed: Policy Name Validation is not visible");
		// steps.add("Associate Seasons Validation : " + ele.getText());
		return steps;
	}

	// checks whether selected policy type is selected or not.
	public void verify_Policy_Type(WebDriver driver, String PolicyType) {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		String selectedOption = new Select(CreatePolicy.Select_Policy_Type).getFirstSelectedOption().getText();
		Assert.assertEquals(PolicyType, selectedOption);
	}

	// checks whether selected policy type is selected or not.
	public void Enter_Policy_Type(WebDriver driver, String PolicyType) {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);

		driver.findElement(By.xpath(OR.SelectPolicyType)).click();
		new Select(CreatePolicy.SelectPolicyType).selectByVisibleText(PolicyType);
		String selectedOption = new Select(CreatePolicy.SelectPolicyType).getFirstSelectedOption().getText();
		Assert.assertEquals(PolicyType, selectedOption);
	}

	public void Enter_Deposit_policy_Attributes_RC_Percentage(WebDriver driver, String Number) {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		CreatePolicy.Enter_Deposit_Percentage_Charges.sendKeys(Number);
		policiesLogger.info("Enter Room Charges Percentage");
	}

	public void Enter_NoShow_policy_Attributes_RC_Percentage(WebDriver driver, String Number) {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		CreatePolicy.Enter_Noshow_Percentage_Charges.sendKeys(Number);
		policiesLogger.info("Enter Room Charges Percentage:" + Number);
	}

	public void Enter_Deposit_Policy_Attributes(WebDriver driver, String Chargestype, String Number)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		CreatePolicy.Enter_Deposit_Percentage_Charges.clear();
		switch (Chargestype.toUpperCase()) {
		case "PRC":
			CreatePolicy.PolicyAttributePercentage_Checkbox.click();
			CreatePolicy.Enter_Deposit_Percentage_Charges.sendKeys(Number);
			break;

		case "TRC":
			new Select(CreatePolicy.Select_Roomcharges_Type_for_Deposit).selectByVisibleText("Total Charges");
			Wait.wait2Second();
			CreatePolicy.Enter_Deposit_Percentage_Charges.sendKeys(Number);
			break;

		case "FA":
			driver.findElement(By.xpath(OR.Select_Deposit_Fixedamount_Radiobtn)).click();
			CreatePolicy.Enter_Deposit_Fixed_Amount.sendKeys(Number);
			break;

		case "FNRC":

			driver.findElement(By.xpath(OR.Select_Deposit_Firstnightrc_Radiobtn)).click();
			CreatePolicy.Enter_Deposit_First_Nights_RC.sendKeys(Number);
			break;
		default:
			System.err.println("Error! Invalid Input Supplied for deposit policy expected PRC/TRC/FA/FNRC");

		}

	}

	public ArrayList<String> Deposit_Policy_Attributes(WebDriver driver, String Chargestype, String Value,
			ArrayList<String> test_steps) throws InterruptedException {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		if (Chargestype.equals("Room Charges") || Chargestype.equals("Total Charges")) {
			WebElement RadioButton = driver.findElement(By.xpath(OR.Select_Deposit_Roomcharges_Radiobtn));
			Wait.explicit_wait_visibilityof_webelement(RadioButton, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click Room Charges Radio Button");
				test_steps.add("Click Room Charged Radio Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			new Select(CreatePolicy.Select_Roomcharges_Type_for_Deposit).selectByVisibleText(Chargestype);
			Wait.wait2Second();
			assertEquals(
					new Select(CreatePolicy.Select_Roomcharges_Type_for_Deposit).getFirstSelectedOption().getText(),
					Chargestype, "Failed: Charges Type not Successfully Selected");
			policiesLogger.info("Select Charges Type : " + Chargestype);
			test_steps.add("Select Charges Type : " + Chargestype);
			CreatePolicy.Enter_Deposit_Percentage_Charges.sendKeys(Value);
			assertEquals(CreatePolicy.Enter_Deposit_Percentage_Charges.getAttribute("value"), Value,
					"Failed: Night Not entered Successfully");
			policiesLogger.info("Enter Percentage : " + Value);
			test_steps.add("Enter Percentage : " + Value);
		} else if (Chargestype.equals("FNsRC") || Chargestype.equals("First Night Room Charges")
				|| Chargestype.equals("First Night")) {
			WebElement RadioButton = driver.findElement(By.xpath(OR.Select_Deposit_Firstnightrc_Radiobtn));
			Wait.explicit_wait_visibilityof_webelement(RadioButton, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click First Nights Room Charges Radio Button");
				test_steps.add("Click First Nights Room Charges Button");
			}
			// assertTrue(RadioButton.isSelected(), "Failed : Radio Button is
			// not Selected");
			// Updated by Naveen
			CreatePolicy.Enter_Deposit_First_Nights_RC.clear();
			CreatePolicy.Enter_Deposit_First_Nights_RC.sendKeys(Value);
			// assertEquals(CreatePolicy.Enter_Deposit_First_Nights_RC.getAttribute("value"),
			// Value,
			// "Failed: Night Not entered Successfully");
			policiesLogger.info("Enter Nights : " + Value);
			test_steps.add("Entered Number of Night room chares : " + Value);
		} else if (Chargestype.equalsIgnoreCase("Fixed Amount")) {
			Wait.waitForElementToBeClickable(By.xpath(OR.Select_Deposit_Fixedamount_Radiobtn), driver);
			if (!(driver.findElement(By.xpath(OR.Select_Deposit_Fixedamount_Radiobtn)).isSelected())) {
				driver.findElement(By.xpath(OR.Select_Deposit_Fixedamount_Radiobtn)).click();
				CreatePolicy.Enter_Deposit_Fixed_Amount.clear();
				CreatePolicy.Enter_Deposit_Fixed_Amount.sendKeys(Value);
				test_steps.add("Entering deposit policy fixed amount as <b>" + Value + "</b>");
			} else {
				CreatePolicy.Enter_Deposit_Fixed_Amount.clear();
				CreatePolicy.Enter_Deposit_Fixed_Amount.sendKeys(Value);
				test_steps.add("Entering deposit policy fixed amount as <b>" + Value + "</b>");
			}
		}
		return test_steps;

	}

	public void Enter_Checkin_Policy_Attributes(WebDriver driver, String paymenttype, String number)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);

		switch (paymenttype.toLowerCase()) {
		case "capture":
			CreatePolicy.Select_Capture_Payment_for_Checkin.click();
			CreatePolicy.Enter_Checkin_Percentage_On_balance.sendKeys(number);

			break;

		case "authorize":
			CreatePolicy.Select_Authorize_Payment_for_Checkin.click();
			CreatePolicy.Enter_Checkin_Percentage_On_balance.sendKeys(number);
			break;
		default:
			System.err.println("Error! Invalid Input Supplied for checkin policy expecting capture/authorize ");

		}

	}

	public void Enter_policy_Attributes_TC_Percentage(WebDriver driver, String Number) {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		CreatePolicy.Enter_Deposit_Percentage_Charges.sendKeys(Number);

	}

	public void Enter_Policy_Desc(WebDriver driver, String PolicyText, String PolicyDesc) {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		try {
			Utility.ScrollToElement(CreatePolicy.Enter_Policy_Text, driver);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreatePolicy.Enter_Policy_Text.clear();
		policiesLogger.info("Clear Policy Text");
		CreatePolicy.Enter_Policy_Text.sendKeys(PolicyText);
		policiesLogger.info("Enter Policy Text:" + PolicyText);
		CreatePolicy.Enter_Policy_Description.clear();
		policiesLogger.info("Clear Policy Description");
		CreatePolicy.Enter_Policy_Description.sendKeys(PolicyDesc);
		policiesLogger.info("Enter Policy Description: " + PolicyDesc);

	}

	public void Associate_Sources(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.Associate_Sources_Btn);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Associate_Sources_Btn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Sources_Btn), driver);
		Utility.ScrollToElement_NoWait(CreatePolicy.Associate_Sources_Btn, driver);
		CreatePolicy.Associate_Sources_Btn.click();
		policiesLogger.info("Click Associate Sources Button");
		Wait.WaitForElement(driver, OR.Associate_Assign_All_Btn);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Associate_Assign_All_Btn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Assign_All_Btn), driver);
		// Wait.explicit_wait_visibilityof_webelement_350(CreatePolicy.Assoociate_Assign_All_Btn,
		// driver);
		int Available_Options_Count = driver.findElements(By.xpath(OR.Available_Options_In_Popup)).size();
		policiesLogger.info(Available_Options_Count);
		Wait.wait1Second();
		CreatePolicy.Assoociate_Assign_All_Btn.click();
		Wait.wait1Second();
		policiesLogger.info("Click Associate All Button");
		int Added_Options_Count = driver.findElements(By.xpath(OR.Added_Options_In_Popup)).size();
		Assert.assertEquals(Added_Options_Count, Available_Options_Count,
				"All Available options are not added in Sources Picker popup after clicking Assign all");
		CreatePolicy.Done_In_Popup.click();
		// Wait.wait3Second();

		policiesLogger.info("Click Done Button");

	}

	public void Associate_Seasons(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Seasons_Btn), driver);
		Utility.ScrollToElement(CreatePolicy.Associate_Seasons_Btn, driver);
		CreatePolicy.Associate_Seasons_Btn.click();
		policiesLogger.info("Click Associate Seasons Button");
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Assign_All_Btn), driver);
		CreatePolicy.Assoociate_Assign_All_Btn.click();
		policiesLogger.info("Click Associate Seasons All Button");
		Wait.waitForElementToBeClickable(By.xpath(OR.Done_In_Popup), driver);
		CreatePolicy.Done_In_Popup.click();
		policiesLogger.info("Click Done Button");

	}

	public void Associate_Seasons(WebDriver driver, String Season) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.Associate_Seasons_Btn);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Seasons_Btn), driver);
		Utility.ScrollToElement_NoWait(CreatePolicy.Associate_Seasons_Btn, driver);
		CreatePolicy.Associate_Seasons_Btn.click();
		Wait.WaitForElement(driver, OR.Associate_Assign_One_Btn);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Assign_One_Btn), driver);
		new Select(CreatePolicy.Select_Available_Options_In_Popup).selectByVisibleText(Season);
		CreatePolicy.Assoociate_Assign_One_Btn.click();
		Wait.waitForElementToBeClickable(By.xpath(OR.Done_In_Popup), driver);
		CreatePolicy.Done_In_Popup.click();
	}

	public void Associate_RoomClasses(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Utility.ScrollToElement(CreatePolicy.Associate_Room_Classes_Btn, driver);
		CreatePolicy.Associate_Room_Classes_Btn.click();
		policiesLogger.info("Click Associate Room Classes Button");
		Wait.explicit_wait_visibilityof_webelement_120(CreatePolicy.Assoociate_Assign_All_Btn, driver);
		int Available_Options_Count = driver.findElements(By.xpath(OR.Available_Options_In_Popup)).size();
		System.out.println("Avaliable:" + Available_Options_Count);
		Wait.wait1Second();
		CreatePolicy.Assoociate_Assign_All_Btn.click();
		policiesLogger.info("Click Associate Room Classes All Button");
		int Added_Options_Count;
		try {
			Wait.wait5Second();
			Added_Options_Count = driver.findElements(By.xpath(OR.Added_Options_In_Popup)).size();
			System.out.println("Added:" + Added_Options_Count);

			// System.out.println("first:" + Added_Options_Count + "second:" +
			// Available_Options_Count);
			Assert.assertEquals(Added_Options_Count, Available_Options_Count,
					"All Available options are not added in Roomclasses Picker popup after clicking Assign all");
		} catch (Exception e) {
			Wait.wait5Second();
			Added_Options_Count = driver.findElements(By.xpath(OR.Added_Options_In_Popup)).size();
			System.out.println("Added:" + Added_Options_Count);
			Assert.assertEquals(Added_Options_Count, Available_Options_Count,
					"All Available options are not added in Roomclasses Picker popup after clicking Assign all");
		}
		CreatePolicy.Done_In_Popup.click();
		Wait.wait3Second();
		policiesLogger.info("Click Done Button");

	}

	public void Associate_RoomClasses(WebDriver driver, String RoomClassName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.Associate_Room_Classes_Btn);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Room_Classes_Btn), driver);
		Utility.ScrollToElement(CreatePolicy.Associate_Room_Classes_Btn, driver);
		CreatePolicy.Associate_Room_Classes_Btn.click();
		Wait.WaitForElement(driver, OR.Associate_Assign_One_Btn);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Assign_One_Btn), driver);
		new Select(CreatePolicy.Select_Available_Options_In_Popup).selectByVisibleText(RoomClassName);
		CreatePolicy.Assoociate_Assign_One_Btn.click();
		Wait.waitForElementToBeClickable(By.xpath(OR.Done_In_Popup), driver);
		CreatePolicy.Done_In_Popup.click();
	}

	public void Associate_RatePlans(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Utility.ScrollToElement(CreatePolicy.Associate_Rate_Plans_Btn, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("arguments[0].click();",
		// CreatePolicy.Associate_Rate_Plans_Btn);
		CreatePolicy.Associate_Rate_Plans_Btn.click();
		policiesLogger.info("Click Policys Button");
		Wait.explicit_wait_visibilityof_webelement_350(CreatePolicy.Assoociate_Assign_All_Btn, driver);
		int Available_Options_Count = driver.findElements(By.xpath(OR.Available_Options_In_Popup)).size();
		Wait.wait1Second();
		CreatePolicy.Assoociate_Assign_All_Btn.click();
		policiesLogger.info("Click Policys All Button");
		Wait.wait3Second();
		int Added_Options_Count = driver.findElements(By.xpath(OR.Added_Options_In_Popup)).size();
		Assert.assertEquals(Added_Options_Count, Available_Options_Count,
				"All Available options are not added in Roomclasses Picker popup after clicking Assign all");
		CreatePolicy.Done_In_Popup.click();
		Wait.wait3Second();
		policiesLogger.info("Click Done Button");

	}

	public void Associate_RatePlan(WebDriver driver, String RatePlan) throws InterruptedException {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.Associate_Rate_Plans_Btn);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Rate_Plans_Btn), driver);
		Utility.ScrollToElement(CreatePolicy.Associate_Rate_Plans_Btn, driver);
		CreatePolicy.Associate_Rate_Plans_Btn.click();
		String rate = "//option[text()='" + RatePlan + "']";
		Wait.WaitForElement(driver, rate);
		Wait.waitForElementToBeClickable(By.xpath(rate), driver);
		driver.findElement(By.xpath(rate)).click();
		String assign = "//button[@data-bind='click: AddNew']";
		Wait.waitForElementToBeClickable(By.xpath(assign), driver);
		driver.findElement(By.xpath(assign)).click();
		Wait.waitForElementToBeClickable(By.xpath(OR.Done_In_Popup), driver);
		CreatePolicy.Done_In_Popup.click();
	}

	public void Save_Policy_ClickThreeTimes(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("arguments[0].click();", CreatePolicy.Policy_Save);
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Policy_Save, driver);
		Utility.ScrollToElement(CreatePolicy.Policy_Save, driver);
		CreatePolicy.Policy_Save.click();
		policiesLogger.info("Click Save once");
		try {
			if (CreatePolicy.Toaster_Title.isDisplayed()) {
				String getTotasterTitle_ReservationSucess = CreatePolicy.Toaster_Title.getText();
				Assert.assertEquals(getTotasterTitle_ReservationSucess, "Success");

			} else {
				System.err.println("Toaster_Message is not displaying ");
			}
		} catch (Exception e) {
			CreatePolicy.Policy_Save.click();
			policiesLogger.info("Click Save once more");
			try {
				if (CreatePolicy.Toaster_Title.isDisplayed()) {
					String getTotasterTitle_ReservationSucess = CreatePolicy.Toaster_Title.getText();
					Assert.assertEquals(getTotasterTitle_ReservationSucess, "Success");

				} else {
					System.err.println("Toaster_Message is not displaying ");
				}
			} catch (Exception f) {
				CreatePolicy.Policy_Save.click();
				policiesLogger.info("Click Save once more");
				if (CreatePolicy.Toaster_Title.isDisplayed()) {
					String getTotasterTitle_ReservationSucess = CreatePolicy.Toaster_Title.getText();
					Assert.assertEquals(getTotasterTitle_ReservationSucess, "Success");

				} else {
					System.err.println("Toaster_Message is not displaying ");
				}

			}
		}
	}

	public ArrayList<String> SavePolicy_MissingField(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Policy_Save, driver);
		Utility.ScrollToElement(CreatePolicy.Policy_Save, driver);
		CreatePolicy.Policy_Save.click();
		test_steps.add("Click Save Policy");
		// jse.executeScript("arguments[0].click();", CreatePolicy.Policy_Save);
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Toaster_Message, driver);
		String Error_Message = CreatePolicy.Toaster_Message.getText();
		Assert.assertEquals(Error_Message, "Please fill in all the mandatory fields");
		test_steps.add("Toaster Message Appear : " + Error_Message);
		return test_steps;
	}

	public boolean SaveButton_IsEnabled(WebDriver driver, boolean Enabled) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Policy_Save, driver);
		Utility.ScrollToElement(CreatePolicy.Policy_Save, driver);
		boolean status = CreatePolicy.Policy_Save.isEnabled();
		if (Enabled) {
			assertTrue(status, "Failed : Save Button is not Enabled");
		} else {
			assertTrue(!status, "Failed : Save Button is Enabled");
		}
		return status;
	}

	public String Save_Policy(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR.Policy_Save);
		Wait.waitForElementToBeClickable(By.xpath(OR.Policy_Save), driver);
		CreatePolicy.Policy_Save.click();
		policiesLogger.info("Click Save Button");
		String getTotasterTitle_ReservationSucess = "";
		try {
			getTotasterTitle_ReservationSucess = CreatePolicy.Toaster_Title.getText();
			Assert.assertEquals(getTotasterTitle_ReservationSucess, "Success");
			CreatePolicy.Toaster_Title.click();
			policiesLogger.info("Close Toaster Title");
		} catch (Exception e) {
			System.err.println("Toaster_Message is not displaying ");
		}
		return getTotasterTitle_ReservationSucess;
	}

	public void clickSavePolicyButton(WebDriver driver, ArrayList<String> test_steps, String policyName)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Policy_Save), driver);
		Utility.ScrollToElement(CreatePolicy.Policy_Save, driver);
		CreatePolicy.Policy_Save.click();
		try {
			String getToasterMessage = CreatePolicy.Toaster_Message.getText();
			assertEquals(getToasterMessage, "Successfully Created Policy : " + policyName,
					"Failed - Verify policy creation success toaster message");
			test_steps.add("Successfully verified policy creation success toaster message as " + "<b>"
					+ getToasterMessage + "</b>");
			CreatePolicy.Toaster_Title.click();
		} catch (Exception e) {
			System.err.println("Toaster_Message is not displaying ");
			test_steps.add("Toaster_Message is not displaying ");
		}
	}

	public void Close_Policy_Tab(WebDriver driver) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		Utility.ScrollToElement(CreatePolicy.Policy_Close_Btn, driver);
		jse.executeScript("arguments[0].click();", CreatePolicy.Policy_Close_Btn);

		// CreatePolicy.Policy_Close_Btn.click();
		Wait.explicit_wait_xpath(OR.New_Policy_Btn, driver);
	}

	public ArrayList<String> closeOpenedPolicyTab(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Close_Policy_Tab(driver);
		test_steps.add("Closing already opened policy tab");
		return test_steps;
	}

	// Searches for the policy with the created policy name.
	public void Verify_Policy(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement_350(CreatePolicy.Policy_Name_On_Policiespage, driver);
		CreatePolicy.Policy_Name_On_Policiespage.clear();
		CreatePolicy.Policy_Name_On_Policiespage.sendKeys(PolicyName);
		CreatePolicy.Search_On_On_Policiespage.click();
		try {
			Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(OR.PolicySearch_ModuleLoading)), 60);
			Wait.waitUntilPresenceOfElementLocated(OR.First_Element_In_Search_Result, driver);
			Wait.explicit_wait_visibilityof_webelement(CreatePolicy.First_Element_In_Search_Result, driver);
		} catch (Exception e) {
			Wait.explicit_wait_visibilityof_webelement(CreatePolicy.First_Element_In_Search_Result, driver);
		}
		Assert.assertEquals(CreatePolicy.First_Element_In_Search_Result.getText(), PolicyName);

	}

	public void Open_Existing_Policy(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebElement Policy_Name_On_SearchPage = driver.findElement(By.xpath("//a[text()='" + PolicyName + "']"));
		Policy_Name_On_SearchPage.click();
	}

	public void Search_Open_Existing_Policy(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Policy_Name_On_Policiespage, driver);
		CreatePolicy.Policy_Name_On_Policiespage.clear();
		CreatePolicy.Policy_Name_On_Policiespage.sendKeys(PolicyName);
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Search_On_On_Policiespage, driver);
		Utility.ScrollToElement(CreatePolicy.Search_On_On_Policiespage, driver);
		CreatePolicy.Search_On_On_Policiespage.click();
		WebElement Policy_Name_On_SearchPage = driver.findElement(By.xpath("//a[text()='" + PolicyName + "']"));
		Policy_Name_On_SearchPage.click();
	}

	public void VerifyPolicyNotExist(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		// Wait.waitForElementToBeGone(driver, CreatePolicy.Toaster_Title, 60);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Wait.explicit_wait_visibilityof_webelement_350(CreatePolicy.Policy_Name_On_Policiespage, driver);
		CreatePolicy.Policy_Name_On_Policiespage.clear();
		CreatePolicy.Policy_Name_On_Policiespage.sendKeys(PolicyName);
		CreatePolicy.Search_On_On_Policiespage.click();
		wait.until(ExpectedConditions.textToBePresentInElement(CreatePolicy.Toaster_Title, "No Policies Exist"));
		Utility.app_logs.info(CreatePolicy.Toaster_Title.getText());

	}

	public void Delete_Policy(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Policy_Name_On_Policiespage, driver);
		CreatePolicy.Policy_Name_On_Policiespage.clear();
		CreatePolicy.Policy_Name_On_Policiespage.sendKeys(PolicyName);

		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Search_On_On_Policiespage, driver);
		Utility.ScrollToElement(CreatePolicy.Search_On_On_Policiespage, driver);
		CreatePolicy.Search_On_On_Policiespage.click();
		Wait.wait2Second();

		if (driver.findElements(By.xpath(OR.First_Element_In_Search_Result)).size() != 0) {
			Wait.explicit_wait_xpath(OR.First_Element_In_Search_Result, driver);
			Assert.assertEquals(CreatePolicy.First_Element_In_Search_Result.getText(), PolicyName);
			Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Delete_Policy_Checkbox, driver);

			WebElement Policy = driver.findElement(
					By.xpath("//a[text()='" + PolicyName + "']/../following-sibling::td//input[@type='checkbox']"));
			jse.executeScript("arguments[0].click();", Policy);
			// Policy.click();
			// CreatePolicy.Delete_Policy_Checkbox.click();
			Wait.wait1Second();
			CreatePolicy.Delete_Policy_Btn.click();
			try {
				wait.until(ExpectedConditions.textToBePresentInElement(CreatePolicy.Toaster_Title,
						"Policies Deleted Successfully"));
				CreatePolicy.Toaster_Title.click();

			} catch (Exception e) {
				policiesLogger.error("Toast Message is not displaying");
			}
		} else {
			policiesLogger.error("Given " + PolicyName + " Policy is not found");
		}
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Policy_Name_On_Policiespage, driver);

	}

	public void SearchPolicy(WebDriver driver) throws InterruptedException {
		WebElements_Policies Policy = new WebElements_Policies(driver);
		Policy.Search_On_On_Policiespage.click();
		Wait.wait2Second();
		Wait.explicit_wait_visibilityof_webelement_150(Policy.Policy_TableShow, driver);
		assertTrue(Policy.Policy_TableShow.isDisplayed(), "Failed:Table not displayed");
	}

	public void ClickSearchPolicy(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies Policy = new WebElements_Policies(driver);
		Policy.Search_On_On_Policiespage.click();
		Wait.wait2Second();
		test_steps.add("Click on Search Button");
		policiesLogger.info("Click on Search Button");
	}

	public void SelectPolicyFromTable(WebDriver driver) throws Exception {

		WebElements_Policies Policy = new WebElements_Policies(driver);
		Policy.Policy_FirstActiveClass.click();
		Wait.wait3Second();
		assertTrue(Policy.Policy_PolicyDetailsPage.isDisplayed(), "Failed: Policy Details Page not displayed");

	}

	public void UpdatePolicyInfo(WebDriver driver, String Desc) throws Exception {

		WebElements_Policies Policy = new WebElements_Policies(driver);
		Random rand = new Random();
		int n = rand.nextInt(50) + 1;
		String Number = String.valueOf(n);
		Desc = Desc + Number;
		Policy.Enter_Policy_Description.clear();
		Policy.Enter_Policy_Description.sendKeys(Desc);

	}

	public ArrayList<String> NoShow_policy_Attributes(WebDriver driver, String noShowAttribute,
			String noShowAttribute_Value, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (noShowAttribute.equals("Room Charges") || noShowAttribute.equals("Total Charges")) {
			WebElement RadioButton = driver.findElement(By.xpath(OR.Select_Noshow_Roomcharges_Radiobtn));
			Wait.explicit_wait_xpath(OR.Select_Noshow_Roomcharges_Radiobtn, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click Room Charges Radio Button");
				test_steps.add("Click Room Charged Radio Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			new Select(CreatePolicy.Select_Roomcharges_Type_for_Noshow).selectByVisibleText(noShowAttribute);
			Wait.wait2Second();
			assertEquals(new Select(CreatePolicy.Select_Roomcharges_Type_for_Noshow).getFirstSelectedOption().getText(),
					noShowAttribute, "Failed: Charges Type not Successfully Selected");
			policiesLogger.info("Select Charges Type : " + noShowAttribute);
			test_steps.add("Selected Charges Type : " + noShowAttribute);
			CreatePolicy.Enter_Noshow_Percentage_Charges.clear();
			CreatePolicy.Enter_Noshow_Percentage_Charges.sendKeys(noShowAttribute_Value);
			assertEquals(CreatePolicy.Enter_Noshow_Percentage_Charges.getAttribute("value"), noShowAttribute_Value,
					"Failed: Percentage Not entered Successfully");
			policiesLogger.info("Enter Percentage : " + noShowAttribute_Value);
			test_steps.add("Entered Percentage : " + noShowAttribute_Value);
		} else if (noShowAttribute.equals("First Night")) {
			WebElement RadioButton = driver.findElement(By.xpath(OR.Select_Noshow_Firstnightrc_Radiobtn));
			Wait.explicit_wait_xpath(OR.Select_Noshow_Firstnightrc_Radiobtn, driver);
			// Wait.explicit_wait_visibilityof_webelement(RadioButton);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click First Nights Room Charges Radio Button");
				test_steps.add("Click First Nights Room Charges Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			CreatePolicy.Enter_Noshow_First_Nights_RC.sendKeys(noShowAttribute_Value);
			assertEquals(CreatePolicy.Enter_Noshow_First_Nights_RC.getAttribute("value"), noShowAttribute_Value,
					"Failed: Night Not entered Successfully");
			policiesLogger.info("Enter Nights : " + noShowAttribute_Value);
			test_steps.add("Enter Nights : " + noShowAttribute_Value);
		} else if (noShowAttribute.equals("Fixed Amount")) {
			WebElement RadioButton = driver.findElement(By.xpath(OR.Select_Noshow_Fixedamount_Radiobtn));
			Wait.waitForElementToBeClickable(By.xpath(OR.Select_Noshow_Fixedamount_Radiobtn), driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click Fixedamount Room Charges Radio Button");
				test_steps.add("Click Fixedamount Room Charges Button");
			}
			// assertTrue(RadioButton.isSelected(), "Failed : Radio Button is
			// not Selected");
			CreatePolicy.Enter_Noshow_Fixed_Amount.clear();
			CreatePolicy.Enter_Noshow_Fixed_Amount.sendKeys(noShowAttribute_Value);
			// assertEquals(CreatePolicy.Enter_Noshow_Fixed_Amount.getAttribute("value"),
			// noShowAttribute_Value,
			// "Failed: Fixed Amount Not entered Successfully");
			policiesLogger.info("Enter Fixed Amount : " + noShowAttribute_Value);
			test_steps.add("Entering Fixed Amount as : <b>" + noShowAttribute_Value + "</b>");
		}
		return test_steps;
	}

	public ArrayList<String> Cancellation_policy_Attributes(WebDriver driver, String cancellationAttribute1,
			String cancellationAttribute1_Value, String cancellationAttribute2, String cancellationAttribute2_Value,
			ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		if (cancellationAttribute1.equals("Room Charges") || cancellationAttribute1.equals("Total Charges")) {
			WebElement RadioButton = CreatePolicy.Select_Cancellation_Roomcharges_Radiobtn;
			Wait.explicit_wait_visibilityof_webelement(RadioButton, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click Room Charges Radio Button");
				test_steps.add("Click Room Charged Radio Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			new Select(CreatePolicy.Select_Roomcharges_Type_for_Cancellation)
			.selectByVisibleText(cancellationAttribute1);
			Wait.wait2Second();
			assertEquals(new Select(CreatePolicy.Select_Roomcharges_Type_for_Cancellation).getFirstSelectedOption()
					.getText(), cancellationAttribute1, "Failed: Charges Type not Successfully Selected");
			policiesLogger.info("Select Charges Type : " + cancellationAttribute1);
			test_steps.add("Select Charges Type : " + cancellationAttribute1);
			CreatePolicy.Select_Cancellation_Roomcharges_Percentage.sendKeys(cancellationAttribute1_Value);
			assertEquals(CreatePolicy.Select_Cancellation_Roomcharges_Percentage.getAttribute("value"),
					cancellationAttribute1_Value, "Failed: PercentAge Not entered Successfully");
			policiesLogger.info("Enter Percentage : " + cancellationAttribute1_Value);
			test_steps.add("Enter Percentage : " + cancellationAttribute1_Value);
		} else if (cancellationAttribute1.equals("First Night")) {
			WebElement RadioButton = CreatePolicy.Select_Cancellation_Firstnightrc_Radiobtn;
			Wait.explicit_wait_visibilityof_webelement(RadioButton, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click First Nights Room Charges Radio Button");
				test_steps.add("Click First Nights Room Charges Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			CreatePolicy.Select_Cancellation_Firstnightrc_NumberOfNights.sendKeys(cancellationAttribute1_Value);
			assertEquals(CreatePolicy.Select_Cancellation_Firstnightrc_NumberOfNights.getAttribute("value"),
					cancellationAttribute1_Value, "Failed: Night Not entered Successfully");
			policiesLogger.info("Enter Nights : " + cancellationAttribute1_Value);
			test_steps.add("Enter Nights : " + cancellationAttribute1_Value);
		} else if (cancellationAttribute1.equals("Fixed Amount")) {
			WebElement RadioButton = CreatePolicy.Select_Cancellation_Fixedamount_Radiobtn;
			Wait.explicit_wait_visibilityof_webelement(RadioButton, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click Fixedamount Room Charges Radio Button");
				test_steps.add("Click Fixedamount Room Charges Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			CreatePolicy.Select_Cancellation_Fixedamount_Amount.sendKeys(cancellationAttribute1_Value);
			assertEquals(CreatePolicy.Select_Cancellation_Fixedamount_Amount.getAttribute("value"),
					cancellationAttribute1_Value, "Failed: Fixed Amount Not entered Successfully");
			policiesLogger.info("Enter Fixed Amount : " + cancellationAttribute1_Value);
			test_steps.add("Enter Fixed Amount : " + cancellationAttribute1_Value);
		}
		if (cancellationAttribute2.equals("Beyond")) {
			WebElement RadioButton = CreatePolicy.Select_Cancellation_Beyond_Radiobtn;
			Wait.explicit_wait_visibilityof_webelement(RadioButton, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click  Beyond Radio Button");
				test_steps.add("Click Beyond radio Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			CreatePolicy.Select_Cancellation_Beyond_NumberOfDays.sendKeys(cancellationAttribute2_Value);
			assertEquals(CreatePolicy.Select_Cancellation_Beyond_NumberOfDays.getAttribute("value"),
					cancellationAttribute2_Value, "Failed: Days Not entered Successfully");
			policiesLogger.info("Enter Days : " + cancellationAttribute2_Value);
			test_steps.add("Enter Days : " + cancellationAttribute2_Value);
		} else if (cancellationAttribute2.equals("Within")) {
			WebElement RadioButton = CreatePolicy.Select_Cancellation_Within_Radiobtn;
			Wait.explicit_wait_visibilityof_webelement(RadioButton, driver);
			if (!RadioButton.isSelected()) {
				RadioButton.click();
				policiesLogger.info("Click Within Radio Button");
				test_steps.add("Click Within Radio Button");
			}
			assertTrue(RadioButton.isSelected(), "Failed : Radio Button is not Selected");
			CreatePolicy.Select_Cancellation_Within_NumberOfDays.sendKeys(cancellationAttribute2_Value);
			assertEquals(CreatePolicy.Select_Cancellation_Within_NumberOfDays.getAttribute("value"),
					cancellationAttribute2_Value, "Failed: Days Not entered Successfully");
			policiesLogger.info("Enter Days : " + cancellationAttribute2_Value);
			test_steps.add("Enter Days : " + cancellationAttribute2_Value);
		}
		return test_steps;

	}

	public void CloseOpenPolicy(WebDriver driver) throws InterruptedException {
		WebElements_Policies Policy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(Policy.CloseOpenedTab, driver);
		Utility.ScrollToElement(Policy.CloseOpenedTab, driver);
		Policy.CloseOpenedTab.click();
		Wait.explicit_wait_xpath(OR.New_Policy_Btn, driver);
	}

	public void VerifyDeletePolicy(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement_350(CreatePolicy.Policy_Name_On_Policiespage, driver);
		CreatePolicy.Policy_Name_On_Policiespage.clear();
		CreatePolicy.Policy_Name_On_Policiespage.sendKeys(PolicyName);
		Utility.ScrollToElement(CreatePolicy.Search_On_On_Policiespage, driver);
		CreatePolicy.Search_On_On_Policiespage.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(CreatePolicy.Toaster_Title, "No Policies Exist"));
		try {
			CreatePolicy.Toaster_Title.click();
			// Wait.waitForElementToBeGone(driver, CreatePolicy.Toaster_Title,
			// 5);
		} catch (Exception e) {
			Utility.app_logs.info("Toaster is not present");
		}

	}

	public void CloseOpenPolicyTab(WebDriver driver) throws InterruptedException {
		WebElements_Policies Policy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(Policy.ClosePolicyTab, driver);
		Utility.ScrollToElement(Policy.ClosePolicyTab, driver);
		Policy.ClosePolicyTab.click();
	}

	public boolean SearchPolicies(WebDriver driver) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);

		Wait.explicit_wait_visibilityof_webelement(policy.Search_On_On_Policiespage, driver);
		policy.Search_On_On_Policiespage.click();
		Wait.waitForElementToBeGone(driver, policy.PolicySearch_ModuleLoading, 60);
		boolean isPolicyExist = false;
		try {
			String toaster_message = policy.Toaster_Title.getText();
			System.out.println("policy.Toaster_Title: " + toaster_message);
			if (toaster_message.equals("No Policies Exists")) {
				isPolicyExist = true;
			}
			System.out.println("in try of toaster");

		} catch (Exception e) {
			System.out.println("in catch");
		}
		return isPolicyExist;
	}

	public boolean AnyPolicyExist(WebDriver driver) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(policy.Search_On_On_Policiespage, driver);
		policy.Search_On_On_Policiespage.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		boolean exist;
		try {
			wait.until(ExpectedConditions.textToBePresentInElement(policy.Toaster_Title, "No Policies Exist"));
			exist = false;
		} catch (Exception e) {
			Utility.app_logs.info("Toaster not Appeaar");
			exist = true;
		}
		return exist;
	}

	public void DeleteAllPolicies(WebDriver driver) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);

		if (policy.List_PolicyCheckbox.size() > 1) {
			Utility.ScrollToElement(policy.NumberofRecordPerPage, driver);
			new Select(policy.NumberofRecordPerPage).selectByVisibleText("100");
			for (int i = 0; i < policy.List_PolicyCheckbox.size(); i++) {
				Utility.ScrollToElement(policy.List_PolicyCheckbox.get(i), driver);
				policy.List_PolicyCheckbox.get(i).click();
			}
			Utility.ScrollToElement(policy.Delete_Policy_Btn, driver);
			policy.Delete_Policy_Btn.click();
			Wait.waitUntilPresenceOfElementLocatedByClassName(OR.Toaster_Title, driver);
			Wait.explicit_wait_visibilityof_webelement(policy.Toaster_Title, driver);
			try {
				policy.Toaster_Title.click();
			} catch (Exception e) {

			}
		} else {
			policy.PolicyCheckbox.click();
			policy.Delete_Policy_Btn.click();
			Wait.waitUntilPresenceOfElementLocatedByClassName(OR.Toaster_Title, driver);
			Wait.explicit_wait_visibilityof_webelement(policy.Toaster_Title, driver);
			System.out.println(policy.Toaster_Title.getText());
			policy.Toaster_Title.click();

		}
	}

	public void DeleteAllPolicies(WebDriver driver, boolean isPolicyExist) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);

		try {
			if (!isPolicyExist) {
				if (policy.List_PolicyCheckbox.size() > 1) {
					System.out.println("after if");
					Utility.ScrollToElement(policy.NumberofRecordPerPage, driver);
					new Select(policy.NumberofRecordPerPage).selectByVisibleText("100");
					int size = policy.List_PolicyCheckbox.size();
					for (int i = 0; i < policy.List_PolicyCheckbox.size(); i++) {
						policy.List_PolicyCheckbox.get(i).click();

					}
					Utility.ScrollToElement(policy.Delete_Policy_Btn, driver);
					policy.Delete_Policy_Btn.click();
					Wait.explicit_wait_visibilityof_webelement(policy.Toaster_Title, driver);
					policy.Toaster_Title.click();
				} else {
					policy.PolicyCheckbox.click();
					policy.Delete_Policy_Btn.click();
					Wait.explicit_wait_visibilityof_webelement(policy.Toaster_Title, driver);
					System.out.println(policy.Toaster_Title.getText());
					policy.Toaster_Title.click();

				}
			}

		} catch (Exception e) {
			System.out.println("there are no policy exist");
		}

	}

	public void AssociateSingle_Seasons(WebDriver driver, String Season) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);

		Wait.explicit_wait_visibilityof_webelement(policy.Associate_Seasons_Btn, driver);
		Utility.ScrollToElement(policy.Associate_Seasons_Btn, driver);
		policy.Associate_Seasons_Btn.click();

		Wait.wait1Second();
		Wait.explicit_wait_visibilityof_webelement(policy.Selec_Attribute, driver);
		int Available_Options_Count = driver.findElements(By.xpath(OR.Available_Options_In_Popup)).size();

		new Select(policy.Selec_Attribute).selectByVisibleText(Season);
		policy.AddButton.click();
		int Added_Options_Count = driver.findElements(By.xpath(OR.Added_Options_In_Popup)).size();
		Assert.assertEquals(Added_Options_Count, 1, "Season does not add after clikc on add button");
		policy.Done_In_Popup.click();
		Wait.wait1Second();

	}

	public void AssociateSingle_RoomClass(WebDriver driver, String RoomClass) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);

		Wait.explicit_wait_visibilityof_webelement(policy.Associate_Room_Classes_Btn, driver);
		Utility.ScrollToElement(policy.Associate_Room_Classes_Btn, driver);
		policy.Associate_Room_Classes_Btn.click();

		Wait.wait1Second();
		Wait.explicit_wait_visibilityof_webelement(policy.Selec_Attribute, driver);
		int Available_Options_Count = driver.findElements(By.xpath(OR.Available_Options_In_Popup)).size();

		new Select(policy.Selec_Attribute).selectByVisibleText(RoomClass);
		policy.AddButton.click();
		int Added_Options_Count = driver.findElements(By.xpath(OR.Added_Options_In_Popup)).size();
		Assert.assertEquals(Added_Options_Count, 1, "RoomClass does not add after clikc on add button");
		policy.Done_In_Popup.click();
		Wait.wait1Second();

	}

	public void AssociateSingle_RatePlan(WebDriver driver, String RatePlan) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);

		Wait.explicit_wait_visibilityof_webelement(policy.Associate_Rate_Plans_Btn, driver);
		Utility.ScrollToElement(policy.Associate_Rate_Plans_Btn, driver);
		policy.Associate_Rate_Plans_Btn.click();

		Wait.wait1Second();
		Wait.explicit_wait_visibilityof_webelement(policy.Selec_Attribute, driver);
		int Available_Options_Count = driver.findElements(By.xpath(OR.Available_Options_In_Popup)).size();

		new Select(policy.Selec_Attribute).selectByVisibleText(RatePlan);
		policy.AddButton.click();
		int Added_Options_Count = driver.findElements(By.xpath(OR.Added_Options_In_Popup)).size();
		Assert.assertEquals(Added_Options_Count, 1, "Policy does not add after clikc on add button");
		policy.Done_In_Popup.click();
		Wait.wait1Second();

	}

	public void ClickOnPolicies(WebDriver driver) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);

		Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
		Navigate.Menuitem_Policy_Text.click();
		Wait.waitForElementToBeGone(driver, policy.LoginModuleLoding, 300);
		Wait.explicit_wait_xpath(OR.Policy_Button, driver);
	}

	public void delete_Policies(WebDriver driver, String PolicyType) throws InterruptedException {
		String type = "//label[text()='Policy Type:']/following-sibling::div/select";
		Wait.WaitForElement(driver, type);
		new Select(driver.findElement(By.xpath(type))).selectByVisibleText(PolicyType.trim());
		String search = "//button[text()='Search']";
		Wait.WaitForElement(driver, search);
		driver.findElement(By.xpath(search)).click();
		Wait.wait10Second();
		String policytext = "//div[text()='Policies List']/../..//tbody/tr/td[5]/input";

		if (driver.findElement(By.xpath("//div[text()='Policies List']")).isDisplayed()) {
			Wait.WaitForElement(driver, policytext);
			int count = driver.findElements(By.xpath(policytext)).size();

			for (int i = 1; i <= count; i++) {
				policytext = "(//div[text()='Policies List']/../..//tbody/tr/td[5]/input)[" + i + "]";
				Wait.WaitForElement(driver, policytext);
				driver.findElement(By.xpath(policytext)).click();
				Wait.wait1Second();
			}
			String del = "//button[text()='Delete']";
			Wait.WaitForElement(driver, del);
			driver.findElement(By.xpath(del)).click();
			Wait.wait5Second();
			int cnt = 1;
			while (true) {
				if (driver.findElement(By.xpath("//div[text()='Policies List']")).isDisplayed()) {
					Wait.wait5Second();
					cnt++;
				} else if (cnt == 5) {
					break;
				} else {
					break;
				}
			}
		}
	}

	public void Select_PolicyType(WebDriver driver, String policyType, ArrayList<String> test_steps)
			throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR_Inventory.Select_PolicyType);
		Utility.ScrollToElement(policy.Select_PolicyTypeDropDownBox, driver);
		Wait.explicit_wait_visibilityof_webelement(policy.Select_PolicyTypeDropDownBox, driver);
		new Select(policy.Select_PolicyTypeDropDownBox).selectByVisibleText(policyType);
		test_steps.add("Select Policy Type : <b>" + policyType + "</b>");
		policiesLogger.info("Select Policy Type : " + policyType);
	}

	public ArrayList<String> createPolicy(WebDriver driver, ArrayList<String> test_steps, String policyName,
			String policyType, String attr1, String attrValue1, String attr2, String attrValue2, String source,
			String policySeason, String roomClass, String ratePlan, String policyText, String policyDesc)
					throws Exception {
		WebElements_Policies policy = new WebElements_Policies(driver);

		try {
			NewPolicybutton(driver, policyType, test_steps);
			Enter_Policy_Name(driver, policyName, test_steps);
			String policyTypeSelected = new Select(policy.Select_PolicyType).getFirstSelectedOption().getText();
			if (!(policyTypeSelected.equalsIgnoreCase(policyType))) {
				Select_PolicyType(driver, policyType, test_steps);
			} else {
				test_steps.add("Policy type is already selected as <b>" + policyType + "</b>");
			}
			if (policyType.equalsIgnoreCase("Check In")) {
				Enter_Checkin_Policy_Attributes(driver, attr1, attrValue1);
				test_steps.add("Selecting policy attribute <b>" + attr1 + "</b>");
				test_steps.add("Providing balance during Check-in  is <b>" + attrValue1 + " </b> percentage");
			} else if (policyType.equalsIgnoreCase("Cancellation")) {
				Cancellation_policy_Attributes(driver, attr1, attrValue1, attr2, attrValue2, test_steps);
			} else if (policyType.equalsIgnoreCase("Deposit")) {
				Deposit_Policy_Attributes(driver, attr1, attrValue1, test_steps);
			} else if (policyType.equalsIgnoreCase("No Show")) {
				NoShow_policy_Attributes(driver, attr1, attrValue1, test_steps);
			}
			Enter_Policy_Desc(driver, policyText, policyDesc);
			test_steps.add("Entering policy texts as <b>" + policyDesc + "</b>");
			Utility.ScrollToElement(policy.Associate_Sources_Btn, driver);
			associateSource(driver, test_steps, source);
			if (Utility.validateString(policySeason)) {
				Associate_Seasons(driver, policySeason);
				test_steps.add("Selecting <b>" + policySeason + "</b> season");
			} else {
				Associate_Seasons(driver);
				test_steps.add("Selecting all the seasons");
			}
			Associate_RatePlan(driver, ratePlan);
			test_steps.add("Selecting rate plan as <b>" + ratePlan + "</b>");
			Associate_RoomClasses(driver, roomClass);
			test_steps.add("Selecting room class as <b>" + roomClass + "</b>");
			clickSavePolicyButton(driver, test_steps, policyName);
			test_steps.add("Saving room class by clicking on save button");
		} catch (Exception e) {

		}
		return test_steps;
	}

	public ArrayList<String> deleteAllPolicies(WebDriver driver, ArrayList<String> test_steps, String policyType,
			String policyName) throws Exception {
		try {
			WebElements_Policies policy = new WebElements_Policies(driver);
			String policyTypeSelected = new Select(policy.Select_PolicyTypeDropDownBox).getFirstSelectedOption()
					.getText();
			if (!(policyTypeSelected.equalsIgnoreCase(policyType))) {
				new Select(policy.Select_PolicyTypeDropDownBox).selectByVisibleText(policyType);
			}
			ClickSearchPolicy(driver, test_steps);
			List<WebElement> policiesCheckBoxes = driver.findElements(By.xpath(
					"//a[contains(text(), '" + policyName + "')]/../..//input[@data-bind='checked: deletePolicy']"));
			if (policiesCheckBoxes.size() > 0) {
				for (WebElement checkBox : policiesCheckBoxes) {
					checkBox.click();
				}
				policy.Delete_Policy_Btn.click();
				test_steps.add("Deleted all policies named with <b>" + policyName + "</b>");
				policiesLogger.info("Deleted all policies named with " + policyName);

			} else {
				test_steps.add("No previous policies to delete for <b>" + policyName + "</b>");
				policiesLogger.info("No previous policies to delete for " + policyName);
			}
		} catch (Exception e) {

		}
		return test_steps;
	}

	public boolean SearchPolicyWithName(WebDriver driver, String PolicyName) throws InterruptedException {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Policy_Name_On_Policiespage, driver);
		CreatePolicy.Policy_Name_On_Policiespage.clear();
		System.out.println("Policy name: " + PolicyName);
		CreatePolicy.Policy_Name_On_Policiespage.sendKeys(PolicyName);

		Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Search_On_On_Policiespage, driver);
		Utility.ScrollToElement(CreatePolicy.Search_On_On_Policiespage, driver);
		CreatePolicy.Search_On_On_Policiespage.click();
		boolean isPolicyExist = false;
		try {
			Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Toaster_Message, driver);

		} catch (Exception e) {
			isPolicyExist = true;
			new Select(CreatePolicy.selectPoliciesPerPage).selectByVisibleText("100");
			Wait.explicit_wait_visibilityof_webelement(CreatePolicy.Search_On_On_Policiespage, driver);
			Wait.explicit_wait_elementToBeClickable(CreatePolicy.Search_On_On_Policiespage, driver);

		}
		return isPolicyExist;
	}

	public ArrayList<String> DeletePolicy_1(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies policy = new WebElements_Policies(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		try {
			String policyPath = "//a[starts-with(text(),'" + PolicyName + "')]//..//following-sibling::td//input";
			List<WebElement> listOfSize = driver.findElements(By.xpath(policyPath));
			if (listOfSize.size() > 1) {
				Utility.ScrollToElement(policy.NumberofRecordPerPage, driver);
				new Select(policy.NumberofRecordPerPage).selectByVisibleText("100");

				for (int i = 0; i < listOfSize.size(); i++) {
					Utility.ScrollToElement_NoWait(listOfSize.get(i), driver);
					listOfSize.get(i).click();
				}
			} else {
				Utility.ScrollToElement_NoWait(listOfSize.get(0), driver);
				listOfSize.get(0).click();
			}
			test_steps.add("Total number of policies select for delete: " + listOfSize.size());
			policy.Delete_Policy_Btn.click();
			test_steps.add("Click on delete button");
			Wait.WaitForElementUsingClassName(driver, OR.Toaster_Title);
			Wait.explicit_wait_visibilityof_webelement(policy.Toaster_Title, driver);
			test_steps.add(policy.Toaster_Message.getText());
			policy.Toaster_Title.click();

		} catch (Exception e) {
			System.out.println("There are no policy exist");
		}
		return test_steps;
	}

	public void Enter_Checkin_Policy_Attributes_1(WebDriver driver, String paymenttype, String number, String type)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);

		if (type.toLowerCase().trim().equals("cancellation")) {
			CreatePolicy.Select_Cancellation_Roomcharges_Percentage.sendKeys(number);
			CreatePolicy.Select_Cancellation_Beyond_NumberOfDays.sendKeys("0");

		} else if (type.toLowerCase().trim().equals("no show")) {
			CreatePolicy.Select_NoShow_Roomcharges_Percentage.sendKeys(number);
		} else {
			CreatePolicy.Enter_Checkin_Percentage_On_balance.sendKeys(number);
		}

		switch (paymenttype.toLowerCase().trim()) {
		case "capture":
			if (type.toLowerCase().trim().equals("capture")) {
				CreatePolicy.Select_Capture_Payment_for_Checkin.click();
			}
			break;

		case "authorize":
			if (type.toLowerCase().trim().equals("authorize")) {
				CreatePolicy.Select_Authorize_Payment_for_Checkin.click();
			}
			break;
		default:
			System.err.println("Error! Invalid Input Supplied for checkin policy expecting capture/authorize ");
		}
	}

	// Searches for the policy with the created policy name.
	public ArrayList<String> verifySearchToaster(WebDriver driver, ArrayList<String> test_steps, String PolicyName,
			boolean isExist) throws InterruptedException {

		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement_350(CreatePolicy.Policy_Name_On_Policiespage, driver);
		CreatePolicy.Policy_Name_On_Policiespage.clear();
		CreatePolicy.Policy_Name_On_Policiespage.sendKeys(PolicyName);
		test_steps.add("Entering Policy Name : " + PolicyName);
		policiesLogger.info("Enter Policy Name : " + PolicyName);
		Utility.ScrollToElement(CreatePolicy.Search_On_On_Policiespage, driver);
		CreatePolicy.Search_On_On_Policiespage.click();
		test_steps.add("Clicked search button");
		policiesLogger.info("Clicked search button");
		if (!isExist) {
			try {
				if (CreatePolicy.Toaster_Title.isDisplayed()) {
					String getTotasterTitle_ReservationSucess = CreatePolicy.Toaster_Title.getText();
					Assert.assertEquals(getTotasterTitle_ReservationSucess, "No Policies Exist",
							"Failed to verify Toster");
					test_steps.add("Successfully Verified Toaster : " + getTotasterTitle_ReservationSucess);
					policiesLogger.info("Successfully Verified Toaster : " + getTotasterTitle_ReservationSucess);
				} else {
					System.err.println("Toaster_Message is not displaying ");
				}
			} catch (Exception e) {
				System.err.println("Toaster_Message is not displaying ");
			}
		} else {
			try {
				Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(OR.PolicySearch_ModuleLoading)), 60);
				Wait.waitUntilPresenceOfElementLocated(OR.First_Element_In_Search_Result, driver);
				Wait.explicit_wait_visibilityof_webelement(CreatePolicy.First_Element_In_Search_Result, driver);
			} catch (Exception e) {
				Wait.explicit_wait_visibilityof_webelement(CreatePolicy.First_Element_In_Search_Result, driver);
			}
			Assert.assertEquals(CreatePolicy.First_Element_In_Search_Result.getText(), PolicyName);
			test_steps.add("Successfully Verified Policy : " + PolicyName);
			policiesLogger.info("Successfully Verified Policy : " + PolicyName);
		}
		return test_steps;
	}

	public void associateSource(WebDriver driver, ArrayList<String> test_steps, String source)
			throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Utility.ScrollToElement(CreatePolicy.Associate_Sources_Btn, driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Sources_Btn), driver);
		CreatePolicy.Associate_Sources_Btn.click();
		policiesLogger.info("Click Associate Sources Button");
		Wait.waitForElementToBeClickable(By.xpath(OR.Associate_Assign_One_Btn), driver);
		new Select(CreatePolicy.Select_Available_Options_In_Popup).selectByVisibleText(source);
		// String path = "//select[contains(@data-bind,'options:
		// filteredItems')]/..//option[contains(text(),'"+source+"')]";
		// driver.findElement(By.xpath(path)).click();
		// new
		// Select(driver.findElement(By.xpath(path))).selectByVisibleText(arg0);
		CreatePolicy.Assoociate_Assign_One_Btn.click();
		policiesLogger.info("Click Associate One Button");
		Wait.waitForElementToBeClickable(By.xpath(OR.Done_In_Popup), driver);
		CreatePolicy.Done_In_Popup.click();
		policiesLogger.info("Click Done Button");
		test_steps.add("Selecting source as <b>" + source + "</b>");
	}

	public void createMultiplePolicies(WebDriver driver, ArrayList<String> test_steps, String numberOfPolicies[],
			ArrayList<String> policyNames, String policyType, ArrayList<String> policiesFor,
			ArrayList<String> policyAmounts, String source, String policySeason, String roomClassName, String ratePlan,
			ArrayList<String> policyTexts, ArrayList<String> policyDescs) throws Exception {

		// Navigation navigation = new Navigation();
		for (int j = 0; j < numberOfPolicies.length; j++) {
			int i = j + 1;
			try {
				test_steps
				.add("========== Creating policy " + i + " and Associating with created room class ==========");
				createPolicy(driver, test_steps, policyNames.get(j), policyType, policiesFor.get(j),
						policyAmounts.get(j), null, null, source, policySeason, roomClassName, ratePlan,
						policyTexts.get(j), policyDescs.get(j));
			} catch (Exception e) {
				// navigation.Inventory(driver, test_steps);
				// navigation.policies(driver, test_steps);
				// test_steps.add("========== Creating policy "+i+" and
				// Associating with created room class ==========");
				// createPolicy(driver, test_steps, policyNames.get(j),
				// policyType, policiesFor.get(j),
				// policyAmounts.get(j), null, null, source, policySeason,
				// roomClassName, ratePlan,
				// policyTexts.get(j), policyDescs.get(j));
			}
			closeOpenedPolicyTab(driver, test_steps);
		}

	}

	// enters given policy name
	public void Enter_Policy_Name(WebDriver driver, String PolicyName) throws InterruptedException {
		WebElements_Policies CreatePolicy = new WebElements_Policies(driver);
		Utility.ScrollToElement(CreatePolicy.Enter_Policy_Name, driver);
		CreatePolicy.Enter_Policy_Name.clear();
		CreatePolicy.Enter_Policy_Name.sendKeys(PolicyName);
	}

	public void Click_All(WebDriver driver) throws InterruptedException {
		String all = "(//a[contains(text(),'All')])[1]";
		Wait.WaitForElement(driver, all);
		Wait.wait2Second();
		driver.findElement(By.xpath(all)).click();
	}

	public void delete_Policies(WebDriver driver) throws InterruptedException {

		String pol = "//tbody[@data-bind='foreach: policiesList']/tr/td[5]/input";

		while (true) {
			int count = driver.findElements(By.xpath(pol)).size();
			if (count > 0) {
				String all = "//small[contains(text(),'Items Per Page')]/following-sibling::select";
				Wait.WaitForElement(driver, all);
				new Select(driver.findElement(By.xpath(all))).selectByValue("100");
				for (int i = 1; i <= count; i++) {
					pol = "//tbody[@data-bind='foreach: policiesList']/tr[" + i + "]/td[5]/input";
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					WebElement element = driver.findElement(By.xpath(pol));
					jse.executeScript("window.scrollTo(0, 50)");
					jse.executeScript("arguments[0].scrollIntoView();", element);
					Wait.wait2Second();
					driver.findElement(By.xpath(pol)).click();
					if (i == count) {
						driver.findElement(By.xpath("//button[contains(text(),'Delete')]")).click();
						Wait.wait10Second();
					}
				}
				count = driver.findElements(By.xpath(pol)).size();
				if (count == 0) {
					break;
				}
			} else {
				break;
			}
		}

	}

	public ArrayList<String> getCancelationPolicies(WebDriver driver){
		ArrayList<String> cancelationPolicie=new ArrayList<String>();
		Wait.WaitForElement(driver, OR.CANCELATION_POLICIES);
		Wait.waitForElementToBeVisibile(By.xpath(OR.CANCELATION_POLICIES), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.CANCELATION_POLICIES), driver);
		WebElements_Policies element = new WebElements_Policies(driver);
		int size=element.cancelationPolices.size();
		for(int i=0;i<size;i++) {

			String foundText=element.cancelationPolices.get(i).getText();
			cancelationPolicie.add(foundText);

		}
		return cancelationPolicie;
	}

	public ArrayList<String> getDepositPolicies(WebDriver driver){
		ArrayList<String> depositePolicie=new ArrayList<String>();
		WebElements_Policies element = new WebElements_Policies(driver);
		int size=element.depositePolices.size();
		for(int i=0;i<size;i++) {

			String foundText=element.depositePolices.get(i).getText();
			depositePolicie.add(foundText);

		}
		return depositePolicie;
	}
	public ArrayList<String> getCheckInPolicies(WebDriver driver){
		ArrayList<String> checkInPolicie=new ArrayList<String>();
		WebElements_Policies element = new WebElements_Policies(driver);
		int size=element.checkInPolicies.size();
		for(int i=0;i<size;i++) {

			String foundText=element.checkInPolicies.get(i).getText();
			checkInPolicie.add(foundText);

		}
		return checkInPolicie;
	}

	public ArrayList<String> getNoShowPolicies(WebDriver driver){
		ArrayList<String> noShowPolicie=new ArrayList<String>();
		WebElements_Policies element = new WebElements_Policies(driver);
		int size=element.noShowPolicies.size();
		for(int i=0;i<size;i++) {

			String foundText=element.noShowPolicies.get(i).getText();
			noShowPolicie.add(foundText);

		}
		return noShowPolicie;
	}

	
	
	//New Policies
	
		public void clickCreateNewPolicy(WebDriver driver, ArrayList<String> test_steps,String PolicyType) throws InterruptedException {
	       
	        String policy="//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+PolicyType.toUpperCase().trim()+"')]//../following-sibling::div//button/span";
	        try {
	            Wait.WaitForElement(driver, "//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+PolicyType.toUpperCase().trim()+"')]");
	           
		        if(!(driver.findElements(By.xpath("//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+PolicyType.toUpperCase().trim()+"')]/following-sibling::div")).size()>0)) {
		        	verifyNoPolicyAvailableText(driver, PolicyType, test_steps);
		        	Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(policy)));
		        }else {
		        	policy="//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+PolicyType.toUpperCase().trim()+"')]//div[normalize-space(text())='Create new']";
		            WebElement ele=driver.findElement(By.xpath(policy));
		            Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(policy)));
		        }
	        }catch(Exception e) {
	        	policy="//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+PolicyType.toUpperCase().trim()+"')]//div[normalize-space(text())='Create new']";
	            WebElement ele=driver.findElement(By.xpath(policy));
	            Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(policy)));
	        }
	        policiesLogger.info("Click create new "+PolicyType+" policy");
	        test_steps.add("Click create new "+PolicyType+" policy");
	    }
	/*public void clickCreateNewPolicy(WebDriver driver, ArrayList<String> test_steps,String PolicyType) throws InterruptedException {
	       
        String policy="//div[contains(normalize-space(text()),'"+PolicyType+"')]/div";
        Wait.WaitForElement(driver, policy);
        WebElement element= driver.findElement(By.xpath(policy));
        Utility.ScrollToElement(element, driver);
        Utility.clickThroughJavaScript(driver, element);
        Wait.WaitForElement(driver, "//div[@class='ant-modal-title']");
        policiesLogger.info("Click create new "+PolicyType+" policy");
        test_steps.add("Click create new "+PolicyType+" policy");
    }
*/
	public void verifyNoPolicyAvailableText(WebDriver driver,String policyType,ArrayList<String> test_steps) {
		String noPolicyText="//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+policyType.toUpperCase().trim()+"')]//../following-sibling::div//span[@id='description']";
		String found = driver.findElement(By.xpath(noPolicyText)).getText();
		assertEquals(found.trim(), "No policy available","Failed to Verify No Policy Available Text Missmatched");
		test_steps.add("Successfully Verified No Policy Available Text");
		policiesLogger.info("Successfully Verified No Policy Available Text");
	}
	
	public void clickCreateNewCancellationPolicy(WebDriver driver, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(element.CANCELLATION_POLICY_CREATE_NEW, driver);
			element.CANCELLATION_POLICY_CREATE_NEW.click();
			
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.CANCELLATION_POLICY_CREATE_NEW);
		}
		test_steps.add("Click Create New Canellation Policy Icon");
		policiesLogger.info("Click Create New Canellation Policy Icon");
	}
	
	public void clickCreateNewDepositPolicy(WebDriver driver, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(element.DEPOSIT_POLICY_CREATE_NEW, driver);
			element.DEPOSIT_POLICY_CREATE_NEW.click();
			
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.DEPOSIT_POLICY_CREATE_NEW);
		}
		test_steps.add("Click Create New Deposit Policy Icon");
		policiesLogger.info("Click Create New Deposit Policy Icon");
	}
	
	public void clickCreateNewCheckInPolicy(WebDriver driver, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(element.CHECKIN_POLICY_CREATE_NEW, driver);
			element.CHECKIN_POLICY_CREATE_NEW.click();
			
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.CHECKIN_POLICY_CREATE_NEW);
		}
		test_steps.add("Click Create New Check In Policy Icon");
		policiesLogger.info("Click Create New Check In Policy Icon");
	}
	
	public void clickCreateNewNoShowPolicy(WebDriver driver, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(element.NOSHOW_POLICY_CREATE_NEW, driver);
			element.NOSHOW_POLICY_CREATE_NEW.click();
			
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.NOSHOW_POLICY_CREATE_NEW);
		}
		test_steps.add("Click Create New No Show Policy Icon");
		policiesLogger.info("Click Create New No Show Policy Icon");
	}

	public void clickEditIcon(WebDriver driver,String policyType, String policyName,ArrayList<String> test_steps) throws InterruptedException {
		String typePath = "//*[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+policyType.toUpperCase()+"')]/../..";
		String path = typePath + "//*[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+policyName.toUpperCase()+"']/following-sibling::div/i[@class='icon-edit']";
		try {
			driver.findElement(By.xpath(path)).click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}
		
		test_steps.add("Click Edit Icon For : " + policyName);
		policiesLogger.info("Click Edit Icon For : " + policyName);
		Wait.wait5Second();
	}
	
	public void clickDeleteIcon(WebDriver driver,String policyType,String policyName,ArrayList<String> test_steps) {
		String typePath = "//*[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+policyType.toUpperCase()+"')]/../..";
		String path = typePath +  "//*[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+policyName.toUpperCase()+"']/following-sibling::div/i[@class='icon-trash']";
		try {
			driver.findElement(By.xpath(path)).click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}
		
		test_steps.add("Click Delete Icon For : " + policyName);
		policiesLogger.info("Click Delete Icon For : " + policyName);
		
	}
	
	public void clickDeleteIcon(WebDriver driver,String policyName,ArrayList<String> test_steps) {
		
		String path =  "//*[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+policyName.toUpperCase()+"']/following-sibling::div/i[@class='icon-trash']";
		try {
			driver.findElement(By.xpath(path)).click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}
		
		test_steps.add("Click Delete Icon For : " + policyName);
		policiesLogger.info("Click Delete Icon For : " + policyName);
		
	}
	
	//Delete Policy Popup
	public void clickDeleteButton(WebDriver driver,ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies elements = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.DELETE_POLICY_BUTTON, driver);
		Wait.explicit_wait_elementToBeClickable(elements.DELETE_POLICY_BUTTON, driver);
		try{
			elements.DELETE_POLICY_BUTTON.click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.DELETE_POLICY_BUTTON);
		}
		test_steps.add("Click Delete Button ");
		policiesLogger.info("Click Delete Button ");
		/*try {
			while(elements.DELETE_POLICY_BUTTON.isDisplayed()) {
				Wait.wait1Second();
			}
		}catch (Exception e) {
			System.err.println("ALREADY GONE");
		}*/
		try {
			String path = "//div[@class='ant-notification-notice-message']";
			Wait.WaitForElement(driver, path);
	//		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
			assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),"Failed to verify toaster message");
			test_steps.add("Successfully Verified Toaster  is Displayed ");
			policiesLogger.info("Successfully Verified Toaster  is Displayed ");
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}catch (Exception e) {
			System.err.println("No Toaster Displayed");
		}
	}
	
	public void verifyPopupDisplayed(WebDriver driver,String displayedPopup,ArrayList<String> test_steps) {
		String path = "//div[@class='ant-modal-title']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),"Failed To Verify Popup Not Displayed");
		test_steps.add("Successfully Verified Popup Displayed");
		policiesLogger.info("Successfully Verified Popup Displayed");
		String found = driver.findElement(By.xpath(path)).getText();
		assertEquals(found.toUpperCase(), displayedPopup.toUpperCase(),"Failed to Verify Displayed Popup");
		test_steps.add("Successfully Verified Displayed Popup Text : " + found);
		policiesLogger.info("Successfully Verified Displayed Popup Text : " + found);
	}
	
	//PolicyName Feild
	public void enterPolicyName(WebDriver driver,String policyName,ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(element.ENTER_POLICY_NAME, driver);
		element.ENTER_POLICY_NAME.clear();
		Utility.clearValue(driver, element.ENTER_POLICY_NAME);
		element.ENTER_POLICY_NAME.sendKeys(policyName);
		test_steps.add("Enter Policy Name : " + policyName);
		policiesLogger.info("Enter Policy Name : " + policyName);
	}
	
	public void verifyPolicyNameVisibility(WebDriver driver,boolean isEnabled, boolean isDisplayed,ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);

		Wait.explicit_wait_visibilityof_webelement_120(elements.ENTER_POLICY_NAME, driver);
		assertEquals(elements.ENTER_POLICY_NAME.isDisplayed(), isDisplayed, "Failed To Verify Policy Name Display");
		test_steps.add("Successfully Verified Policy Name is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified Policy Name is Displayed : " + isDisplayed);

		assertEquals(elements.ENTER_POLICY_NAME.isEnabled(), isEnabled, "Failed To Verify Policy Name Enable");
		test_steps.add("Successfully Verified Policy Name Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified Policy Name Enable : " + isEnabled);
	}
	
	public void verifyPolicyRequiredFeild(WebDriver driver, String policyName, boolean isPolicyNameAlreadyExistError, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies elements = new WebElements_Policies(driver);

		if(isPolicyNameAlreadyExistError) {
			String errorTxt = "A policy with this name already exists";
			Wait.explicit_wait_visibilityof_webelement_120(elements.ENTER_POLICY_NAME, driver);
			enterPolicyName(driver, policyName, test_steps);
			elements.ENTER_POLICY_NAME.sendKeys(Keys.TAB);
			assertTrue(elements.POLCIY_NAME_ALREADY_EXIST.isDisplayed(), "Failed To Verify Policy Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Policy Name Error Text Displayed ");
			policiesLogger.info("Successfully Verified Policy Name Error Text Displayed ");

			assertEquals(elements.POLCIY_NAME_ALREADY_EXIST.getText(),errorTxt, "Failed To Verify Policy Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Policy Name Error Text : " + elements.POLCIY_NAME_ALREADY_EXIST.getText());
			policiesLogger.info("Successfully Verified Policy Name Error Text : " + elements.POLCIY_NAME_ALREADY_EXIST.getText());

			Utility.clearValue(driver, elements.ENTER_POLICY_NAME);
		} else {

			Wait.explicit_wait_visibilityof_webelement_120(elements.ENTER_POLICY_NAME, driver);
			enterPolicyName(driver, policyName, test_steps);
			elements.ENTER_POLICY_NAME.clear();

			Utility.clearValue(driver, elements.ENTER_POLICY_NAME);
			test_steps.add("Entered Policy Name Cleared");
			policiesLogger.info("Entered Policy Name Cleared");

			verifyPolicyNameReq(driver, true, test_steps);
			verifyPolicyNameFeildValue(driver, policyName, false, test_steps);
			enterPolicyName(driver, policyName, test_steps);		
			verifyPolicyNameReq(driver, false, test_steps);
			verifyPolicyNameFeildValue(driver, policyName, true, test_steps);
		}
	}
	
	public void verifyPolicyNameReq(WebDriver driver, boolean isRequired, ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);

		if(isRequired) {
			assertTrue(elements.ENTER_POLICY_NAME.getAttribute("class").toUpperCase().contains("REQUIRED"), "Failed To Verify Policy Name Feild in Red Color");
			test_steps.add("Successfully Verified Policy Name Feild is in Red Color");
			policiesLogger.info("Successfully Verified Policy Name Feild is in Red Color");

		}else {
			try {
				assertTrue(!elements.ENTER_POLICY_NAME.getAttribute("class").toUpperCase().contains("REQUIRED"), "Failed To Verify Policy Name Error Text Displayed");
				test_steps.add("Successfully Verified Policy Name Feild is not in Red Color");
				policiesLogger.info("Successfully Verified Policy Name Feild is not in Red Color");
			} catch (Exception e) {
				test_steps.add("Successfully Verified Policy Name Feild is not in Red Color");
				policiesLogger.info("Successfully Verified Policy Name Feild is not in Red Color");
			}
		}
	}
	
	public void verifyPolicyNameFeildValue(WebDriver driver, String expectedValue, boolean expectedEqual, ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);

		String foundValue = elements.ENTER_POLICY_NAME.getAttribute("value");
		if(expectedEqual) {
			assertEquals(foundValue, expectedValue,"Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified Policy Name Value Matched : " + foundValue);
			policiesLogger.info("Successfully Verified Policy Name Value Matched : " + foundValue);

			Actions builder = new Actions(driver);
			builder.moveToElement(elements.ENTER_POLICY_NAME).perform();

			assertEquals(elements.ENTER_POLICY_NAME.getAttribute("title"), expectedValue,"Failed To Verify tooltip value Missmatched");
			test_steps.add("Successfully Verified Policy Name tool tip Value Matched : " + elements.ENTER_POLICY_NAME.getAttribute("title"));
			policiesLogger.info("Successfully Verified Policy Name tool tip Value Matched : " + elements.ENTER_POLICY_NAME.getAttribute("title"));

		}else {
			assertNotEquals(foundValue, expectedValue,"Failed To Verify Value Matched");
			test_steps.add("Successfully Verified Policy Name Value Not Matched");
			policiesLogger.info("Successfully Verified Policy Name Value Not Matched ");
		}

	}

	public int getPolicyInputFieldLength(WebDriver driver,ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);

		return elements.ENTER_POLICY_NAME.getAttribute("value").length();
	}
	
	//Select Type Of Fee
	public void selectTypeOfFee(WebDriver driver,String typeOfFee,int clauseNo,ArrayList<String> test_steps) throws InterruptedException {//div[1]
		String selectPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//span[contains(@class,'inrd-select fee-type-select')]";
		String optionPath = selectPath+"//li[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+typeOfFee.toUpperCase()+"']";
		
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
		driver.findElement(By.xpath(selectPath)).click();
		Wait.wait2Second();
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(optionPath)));
		test_steps.add("Select Fee Type : " + typeOfFee);
		policiesLogger.info("Select Fee Type : " + typeOfFee);
	}
	
	public void verfiySelectedTypeOfFee(WebDriver driver,String expectedTypeOfFee,int clauseNo,ArrayList<String> test_steps) {
		String selectPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//span[contains(@class,'inrd-select fee-type-select')]//div[@class='ant-select-selection-selected-value']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
		String foundType = driver.findElement(By.xpath(selectPath)).getText();
		assertEquals(foundType, expectedTypeOfFee,"Failed To verify Selected Type of Fee");
		test_steps.add("Successfully Verified Select Fee Type : " + foundType);
		policiesLogger.info("Successfully Verified Select Fee Type : " + foundType);
		
		assertEquals(driver.findElement(By.xpath(selectPath)).getAttribute("title"), expectedTypeOfFee,"Failed To Verify tooltip value Missmatched");
		test_steps.add("Successfully Verified Selected Fee Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
		policiesLogger.info("Successfully Verified Selected Fee Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
	}
	
	//Cancellation Policy Percentage Field
	public void enterPercentage(WebDriver driver, String percentage, int clauseNo,ArrayList<String> test_steps) {
	//	String percentPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//div[contains(text(),'Guests will incur a fee of')]/span/div/div/input";
		String percentPath = "(//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input)[1]";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(percentPath)), driver);
		Utility.clearValue(driver, driver.findElement(By.xpath(percentPath)));
		driver.findElement(By.xpath(percentPath)).sendKeys(percentage);
		driver.findElement(By.xpath(percentPath)).sendKeys(Keys.TAB);
		test_steps.add("Enter Percentage % : " + percentage);
		policiesLogger.info("Enter Percentage % : " + percentage);
	}
	
	public void verifyPercentageValue(WebDriver driver, String expectedValue,boolean expectedEqual, int clauseNo,ArrayList<String> test_steps) {
		String percentPath = "(//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input)[1]";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(percentPath)), driver);
		String foundValue = driver.findElement(By.xpath(percentPath)).getAttribute("value");
		if(expectedEqual) {
			assertEquals(foundValue, expectedValue+"%","Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified Percentage % Value Matched : " + foundValue);
			policiesLogger.info("Successfully Verified Percentage % Value Matched : " + foundValue);

		}else {
			assertNotEquals(foundValue, expectedValue+"%","Failed To Verify Value Matched");
			test_steps.add("Successfully Verified Percentage % Value Not Matched");
			policiesLogger.info("Successfully Verified Percentage % Value Not Matched ");
		}
	}
	
	public void verifyPercentageVisibility(WebDriver driver, int clauseNo, boolean isEnabled, boolean isDisplayed,ArrayList<String> test_steps) {
		String percentPath = "(//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input)[1]";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(percentPath)), driver);
		assertEquals(driver.findElement(By.xpath(percentPath)).isDisplayed(), isDisplayed, "Failed To Verify Policy Name Display");
		test_steps.add("Successfully Verified Percentage % Field is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified Percentage % Field is Displayed : " + isDisplayed);

		assertEquals(driver.findElement(By.xpath(percentPath)).isEnabled(), isEnabled, "Failed To Verify Policy Name Enable");
		test_steps.add("Successfully Verified Percentage % Field Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified Percentage % Field Enable : " + isEnabled);
	}
	
	public int getPercentageFieldLength(WebDriver driver, int clauseNo,ArrayList<String> test_steps) {
		String percentPath = "(//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input)[1]";

		return driver.findElement(By.xpath(percentPath)).getAttribute("value").length();
	}
	
	
	
	//Select Charge Type
	public void selectChargeType(WebDriver driver,String chargeType,int clauseNo,int percentOfStayCount,ArrayList<String> test_steps) throws InterruptedException {
		String chargeTypePath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//div[contains(text(),'Guests will incur a fee of')]/span[2]/div";
		String chargeTypeOptionPath = "(//li[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+chargeType.toUpperCase()+"'])";
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(chargeTypePath)));
		Wait.wait2Second();
		
		boolean flag = true;
		for(WebElement ele: driver.findElements(By.xpath(chargeTypeOptionPath))) {
			if(ele.isDisplayed()) {
				Utility.clickThroughJavaScript(driver, ele);
				test_steps.add("Select Charge Type : " + chargeType);
				policiesLogger.info("Select Charge Type : " + chargeType);
				flag=false;
				break;
			}
		}
		
		if(flag) {
			chargeTypeOptionPath = chargeTypeOptionPath + "["+percentOfStayCount+"]";
			Utility.clickThroughJavaScript(driver,driver.findElement(By.xpath(chargeTypeOptionPath)));
			test_steps.add("Select Charge Type : " + chargeType);
			policiesLogger.info("Select Charge Type : " + chargeType);
		}
	}
	
	public void verifySelectedChargeType(WebDriver driver,String expectedChargeType,int clauseNo,ArrayList<String> test_steps) {
		String selectPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//div[contains(text(),'Guests will incur a fee of')]/span[2]/div//div[@class='ant-select-selection-selected-value']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
		String foundType = driver.findElement(By.xpath(selectPath)).getText();
		assertEquals(foundType, expectedChargeType,"Failed To verify Selected Charge Type");
		test_steps.add("Successfully Verified Selected Charge Type : " + foundType);
		policiesLogger.info("Successfully Verified Selected Charge Type : " + foundType);
		
		assertEquals(driver.findElement(By.xpath(selectPath)).getAttribute("title"), expectedChargeType,"Failed To Verify tooltip value Missmatched");
		test_steps.add("Successfully Verified Selected Charge Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
		policiesLogger.info("Successfully Verified Selected Charge Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
	}
	
	//Select CheckIn Charge Type
	public void selectCheckInChargeType(WebDriver driver,String chargeType,ArrayList<String> test_steps) throws InterruptedException {
		String chargeTypePath = "//div[contains(text(),'Upon check in')]//span[contains(@class,'inrd-select')]/div";
		String chargeTypeOptionPath = "(//li[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+chargeType.toUpperCase()+"'])";
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(chargeTypePath)));
		Wait.wait2Second();
		//	boolean flag = true;
		for(WebElement ele: driver.findElements(By.xpath(chargeTypeOptionPath))) {
			if(ele.isDisplayed()) {
				Utility.clickThroughJavaScript(driver, ele);
				test_steps.add("Select Charge Type : " + chargeType);
				policiesLogger.info("Select Charge Type : " + chargeType);
				break;
			}
		}
		
	}
		
	public void verifyCheckInSelectedChargeType(WebDriver driver,String expectedChargeType,ArrayList<String> test_steps) {
		String selectPath = "//div[contains(text(),'Upon check in')]//span[contains(@class,'inrd-select')]//div[@class='ant-select-selection-selected-value']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
		String foundType = driver.findElement(By.xpath(selectPath)).getText();
		assertEquals(foundType, expectedChargeType,"Failed To verify Selected Charge Type");
		test_steps.add("Successfully Verified Selected Charge Type : " + foundType);
		policiesLogger.info("Successfully Verified Selected Charge Type : " + foundType);
			
		assertEquals(driver.findElement(By.xpath(selectPath)).getAttribute("title"), expectedChargeType,"Failed To Verify tooltip value Missmatched");
		test_steps.add("Successfully Verified Selected Charge Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
		policiesLogger.info("Successfully Verified Selected Charge Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
	}
	
	//Select No Show/Deposit Charge Type
	public void selectNoShowDepositChargeType(WebDriver driver, String chargeType, ArrayList<String> test_steps) throws InterruptedException {
		String chargeTypePath = "//div[contains(text(),'they must pay')]//span[contains(@class,'inrd-select')]/div";
		String chargeTypeOptionPath = "(//li[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ chargeType.toUpperCase() + "'])";
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(chargeTypePath)));
		Wait.wait2Second();

		// boolean flag = true;
		for (WebElement ele : driver.findElements(By.xpath(chargeTypeOptionPath))) {
			if (ele.isDisplayed()) {
				Utility.clickThroughJavaScript(driver, ele);
				test_steps.add("Select Charge Type : " + chargeType);
				policiesLogger.info("Select Charge Type : " + chargeType);
				break;
			}
		}

	}

	public void verifyNoShowSelectedChargeType(WebDriver driver, String expectedChargeType,
			ArrayList<String> test_steps) {
		String selectPath = "//div[contains(text(),'they must pay')]//span[contains(@class,'inrd-select')]//div[@class='ant-select-selection-selected-value']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
		String foundType = driver.findElement(By.xpath(selectPath)).getText();
		assertEquals(foundType, expectedChargeType, "Failed To verify Selected Charge Type");
		test_steps.add("Successfully Verified Selected Charge Type : " + foundType);
		policiesLogger.info("Successfully Verified Selected Charge Type : " + foundType);

		assertEquals(driver.findElement(By.xpath(selectPath)).getAttribute("title"), expectedChargeType,
				"Failed To Verify tooltip value Missmatched");
		test_steps.add("Successfully Verified Selected Charge Type tool tip Value Matched : "
				+ driver.findElement(By.xpath(selectPath)).getAttribute("title"));
		policiesLogger.info("Successfully Verified Selected Charge Type tool tip Value Matched : "
				+ driver.findElement(By.xpath(selectPath)).getAttribute("title"));
	}

	//Enter No Of Days
	public void enterNoOfDays(WebDriver driver,String noOfDays,int clauseNo,ArrayList<String> test_steps) {
		String noOfDaysPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfDays']";
		Utility.clearValue(driver, driver.findElement(By.xpath(noOfDaysPath)));
		driver.findElement(By.xpath(noOfDaysPath)).sendKeys(noOfDays);
		driver.findElement(By.xpath(noOfDaysPath)).sendKeys(Keys.TAB);
		test_steps.add("Enter No Of Days : " + noOfDays);
		policiesLogger.info("Enter No Of Days : " + noOfDays);
	}
	
	public void verifyNoOfDaysValue(WebDriver driver, String expectedValue,boolean expectedEqual, int clauseNo,ArrayList<String> test_steps) {
		String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfDays']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
		String foundValue = driver.findElement(By.xpath(path)).getAttribute("value");
		if(expectedEqual) {
			assertEquals(foundValue, expectedValue,"Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified No Of Days Value Matched : " + foundValue);
			policiesLogger.info("Successfully Verified No Of Days Value Matched : " + foundValue);

		}else {
			assertNotEquals(foundValue, expectedValue,"Failed To Verify Value Matched");
			test_steps.add("Successfully Verified No Of Days Value Not Matched");
			policiesLogger.info("Successfully Verified No Of Days Value Not Matched ");
		}
	}
	
	public void verifyNoOfDaysVisibility(WebDriver driver, int clauseNo, boolean isEnabled, boolean isDisplayed,ArrayList<String> test_steps) {
		String noOfDaysPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfDays']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(noOfDaysPath)), driver);
		assertEquals(driver.findElement(By.xpath(noOfDaysPath)).isDisplayed(), isDisplayed, "Failed To Verify  No Of Days Display");
		test_steps.add("Successfully Verified  No Of Days Field is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified  No Of Days Field is Displayed : " + isDisplayed);

		assertEquals(driver.findElement(By.xpath(noOfDaysPath)).isEnabled(), isEnabled, "Failed To Verify  No Of Days Enable");
		test_steps.add("Successfully Verified  No Of Days Field Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified  No Of Days Field Enable : " + isEnabled);
	}
	
	public int getNoOfDaysFieldLength(WebDriver driver, int clauseNo,ArrayList<String> test_steps) {
		String noOfDaysPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfDays']";

		return driver.findElement(By.xpath(noOfDaysPath)).getAttribute("value").length();
	}
	
	//Select Cancel Type
	public void selectCancelType(WebDriver driver, String cancelWithInType,int clauseNo,ArrayList<String> test_steps) throws InterruptedException {
		String cancelTypePath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//div[contains(text(),'Guests will incur a fee of')]/div/span[2]/div[1]/div";
		String cancelOptionPath =  "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//li[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+cancelWithInType.toUpperCase()+"']";
		driver.findElement(By.xpath(cancelTypePath)).click();
		Wait.wait2Second();
		
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(cancelOptionPath)));
			
		test_steps.add("Select Cancel Type : " + cancelWithInType);
		policiesLogger.info("Select Cancel Type : " + cancelWithInType);
	}
	
	public void verifySelectedCancelType(WebDriver driver,String expectedCancelType,int clauseNo,ArrayList<String> test_steps) {
		String selectPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//div[contains(text(),'Guests will incur a fee of')]/div/span[2]/div[1]/div//div[@class='ant-select-selection-selected-value']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
		String foundType = driver.findElement(By.xpath(selectPath)).getText();
		assertEquals(foundType, expectedCancelType,"Failed To verify Selected Cancel Type");
		test_steps.add("Successfully Verified Selected Cancel Type : " + foundType);
		policiesLogger.info("Successfully Verified Selected Cancel Type : " + foundType);
		
		assertEquals(driver.findElement(By.xpath(selectPath)).getAttribute("title"), expectedCancelType,"Failed To Verify tooltip value Missmatched");
		test_steps.add("Successfully Verified Selected Cancel Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
		policiesLogger.info("Successfully Verified Selected Cancel Type tool tip Value Matched : " + driver.findElement(By.xpath(selectPath)).getAttribute("title"));
	}
	
	//Flat Amount
	public void enterFlatAmount(WebDriver driver, String flatFee,int clauseNo,ArrayList<String> test_steps) {
		String flatPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='flatFeeAmount']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(flatPath)), driver);
		Utility.clearValue(driver, driver.findElement(By.xpath(flatPath)));
		driver.findElement(By.xpath(flatPath)).sendKeys(flatFee);
		driver.findElement(By.xpath(flatPath)).sendKeys(Keys.TAB);
		test_steps.add("Enter Flat Fee : " + flatFee);
		policiesLogger.info("Enter Flat Fee : " + flatFee);
	}
	
	public void verifyFlatAmountValue(WebDriver driver, String expectedValue,boolean expectedEqual, int clauseNo,ArrayList<String> test_steps) {
		String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='flatFeeAmount']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
		String foundValue = driver.findElement(By.xpath(path)).getAttribute("value");
		if(expectedEqual) {
			assertEquals(foundValue, expectedValue,"Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified Flat Fee Value Matched : " + foundValue);
			policiesLogger.info("Successfully Verified Flat Fee Value Matched : " + foundValue);

		}else {
			assertNotEquals(foundValue, expectedValue,"Failed To Verify Value Matched");
			test_steps.add("Successfully Verified Flat Fee Value Not Matched");
			policiesLogger.info("Successfully Verified Flat Fee Value Not Matched ");
		}
	}
	
	public void verifyFlatAmountVisibility(WebDriver driver, int clauseNo, boolean isEnabled, boolean isDisplayed,ArrayList<String> test_steps) {
		String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='flatFeeAmount']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
		assertEquals(driver.findElement(By.xpath(path)).isDisplayed(), isDisplayed, "Failed To Verify Flat Fee Display");
		test_steps.add("Successfully Verified  Flat Fee Field is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified  Flat Fee Field is Displayed : " + isDisplayed);

		assertEquals(driver.findElement(By.xpath(path)).isEnabled(), isEnabled, "Failed To Verify Flat Fee Enable");
		test_steps.add("Successfully Verified  Flat Fee Field Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified  Flat Fee Field Enable : " + isEnabled);
	}
	
	public int getFlatAmountFieldLength(WebDriver driver, int clauseNo,ArrayList<String> test_steps) {
		String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='flatFeeAmount']";

		return driver.findElement(By.xpath(path)).getAttribute("value").length();
	}
	
	//No Of Nights
	public void enterNoOfNights(WebDriver driver, String noOfNights,int clauseNo,ArrayList<String> test_steps) {
		String noOfNightsPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfNights']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(noOfNightsPath)), driver);
		driver.findElement(By.xpath(noOfNightsPath)).clear();
		driver.findElement(By.xpath(noOfNightsPath)).sendKeys(noOfNights);
		driver.findElement(By.xpath(noOfNightsPath)).sendKeys(Keys.TAB);
		test_steps.add("Enter No Of Nights : " + noOfNights);
		policiesLogger.info("Enter No Of Nights : " + noOfNights);
	}
	
	public void verifyNoOfNightsValue(WebDriver driver, String expectedValue,boolean expectedEqual, int clauseNo,ArrayList<String> test_steps) {
		String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfNights']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
		String foundValue = driver.findElement(By.xpath(path)).getAttribute("value");
		if(expectedEqual) {
			assertEquals(foundValue, expectedValue,"Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified No Of Nights Value Matched : " + foundValue);
			policiesLogger.info("Successfully Verified No Of Nights Value Matched : " + foundValue);

		}else {
			assertNotEquals(foundValue, expectedValue,"Failed To Verify Value Matched");
			test_steps.add("Successfully Verified No Of Nights Value Not Matched");
			policiesLogger.info("Successfully Verified No Of Nights Value Not Matched ");
		}
	}
	
	public void verifyNoOfNightsVisibility(WebDriver driver, int clauseNo, boolean isEnabled, boolean isDisplayed,ArrayList<String> test_steps) {
		String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfNights']";
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
		assertEquals(driver.findElement(By.xpath(path)).isDisplayed(), isDisplayed, "Failed To Verify Flat Fee Display");
		test_steps.add("Successfully Verified  No Of Nights Field is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified  No Of Nights Field is Displayed : " + isDisplayed);

		assertEquals(driver.findElement(By.xpath(path)).isEnabled(), isEnabled, "Failed To Verify Flat Fee Enable");
		test_steps.add("Successfully Verified  No Of Nights Field Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified No Of Nights Field Enable : " + isEnabled);
	}
	
	public int getNoOfNightsFieldLength(WebDriver driver, int clauseNo,ArrayList<String> test_steps) {
		String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//input[@name='numberOfNights']";

		return driver.findElement(By.xpath(path)).getAttribute("value").length();
	}
	
	public void fillPercentOfStay(WebDriver driver, String percentage, String chargeType, String noOfDays, String cancelWithInType,int clauseNo,int percentOfStayCount,ArrayList<String> test_steps) throws InterruptedException {
		enterPercentage(driver, percentage, clauseNo, test_steps);
		selectChargeType(driver, chargeType, clauseNo,percentOfStayCount, test_steps);
		enterNoOfDays(driver, noOfDays, clauseNo, test_steps);
		selectCancelType(driver, cancelWithInType, clauseNo, test_steps);	
	}
	
	public void fillFlatFee(WebDriver driver, String flatFee, String noOfDays, String cancelWithInType,int clauseNo,ArrayList<String> test_steps) throws InterruptedException {
		enterFlatAmount(driver, flatFee, clauseNo, test_steps);
		enterNoOfDays(driver, noOfDays, clauseNo, test_steps);
		selectCancelType(driver, cancelWithInType, clauseNo, test_steps);
	}
	
	public void fillNumberOfNights(WebDriver driver, String noOfNights, String noOfDays, String cancelWithInType,int clauseNo,ArrayList<String> test_steps) throws InterruptedException {
		enterNoOfNights(driver, noOfNights, clauseNo, test_steps);
		enterNoOfDays(driver, noOfDays, clauseNo, test_steps);
		selectCancelType(driver, cancelWithInType, clauseNo, test_steps);
	}
	
	public int clickAddNewClause(WebDriver driver, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(element.ADD_NEW_CLAUSE, driver);
			element.ADD_NEW_CLAUSE.click();
			
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.ADD_NEW_CLAUSE);
		}
		test_steps.add("Click Add New Clause");
		policiesLogger.info("Click Add New Clause");
		
		return Integer.parseInt(driver.findElement(By.xpath("(//span[contains(@class,'sr-no')])[last()]")).getText().replace(")", "").trim());
	}
	
	//Add Policy Button
	public void clickAddPolicy(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies element = new WebElements_Policies(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(element.ADD_POLICY_BUTTON, driver);
			element.ADD_POLICY_BUTTON.click();
			
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.ADD_POLICY_BUTTON);
		}
		test_steps.add("Click Add Policy Button");
		policiesLogger.info("Click Add Policy Button");
/*		try {
//			Wait.waitForElementToBeGone(driver, element.ADD_POLICY_BUTTON, 5);
			while( element.ADD_POLICY_BUTTON.isDisplayed()){
				Wait.wait1Second();
			}
			}catch (Exception e) {
			System.err.println("ALREADY GONE");
		}
		*/
		try {
			String path = "//div[@class='ant-notification-notice-message']";
			Wait.WaitForElement(driver, path);
//			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
			assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),"Failed to verify toaster message");
			test_steps.add("Successfully Verified Toaster  is Displayed ");
			policiesLogger.info("Successfully Verified Toaster  is Displayed ");
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}catch (Exception e) {
			System.err.println("No Toaster Displayed");
		}
	}
	
	public void verifyAddPolicy(WebDriver driver, boolean isEnabled, boolean isDisplayed, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies elements = new WebElements_Policies(driver);
		
		Wait.explicit_wait_visibilityof_webelement_120(elements.ADD_POLICY_BUTTON, driver);

		assertEquals(elements.ADD_POLICY_BUTTON.isDisplayed(), isDisplayed, "Failed To Verify Add Policy is not Displayed");
		test_steps.add("Successfully Verified Add Policy Button is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified Add Policy Button is Displayed : " + isDisplayed);

		assertEquals(elements.ADD_POLICY_BUTTON.isEnabled(), isEnabled, "Failed To Verify Add Polciy Button Enable");
		test_steps.add("Successfully Verified Add Policy Button Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified Add Policy Button Enable : " + isEnabled);
		
	}
	
	// Update Policy Button
	public void clickUpdatePolicyButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies element = new WebElements_Policies(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(element.UPDATE_POLICY_BUTTON, driver);
			element.UPDATE_POLICY_BUTTON.click();
			
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.UPDATE_POLICY_BUTTON);
		}
		test_steps.add("Click Update Policy Button");
		policiesLogger.info("Click Update Policy Button");
		try {
			//Wait.waitForElementToBeGone(driver, element.UPDATE_POLICY_BUTTON, 5);
			while( element.UPDATE_POLICY_BUTTON.isDisplayed()){
				Wait.wait1Second();
			}
		}catch (Exception e) {
			System.err.println("ALREADY GONE");
		}
		try {
			String path = "//div[@class='ant-notification-notice-message']";
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
//			assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),"Failed to verify toaster message");
//			test_steps.add("Successfully Verified Toaster  is Displayed ");
//			policiesLogger.info("Successfully Verified Toaster  is Displayed ");
		}catch (Exception e) {
			System.err.println("No Toaster Displayed");
		}
	}
	
	public void verifyUpdatePolicyButton(WebDriver driver, boolean isEnabled, boolean isDisplayed, ArrayList<String> test_steps) throws InterruptedException {
		WebElements_Policies elements = new WebElements_Policies(driver);
		
		Wait.explicit_wait_visibilityof_webelement_120(elements.UPDATE_POLICY_BUTTON, driver);

		assertEquals(elements.UPDATE_POLICY_BUTTON.isDisplayed(), isDisplayed, "Failed To Verify Update Policy is not Displayed");
		test_steps.add("Successfully Verified Update Policy Button is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified AUpdatedd Policy Button is Displayed : " + isDisplayed);

		assertEquals(elements.UPDATE_POLICY_BUTTON.isEnabled(), isEnabled, "Failed To Verify Update Button Enable");
		test_steps.add("Successfully Verified Update Policy Button Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified Update Policy Button Enable : " + isEnabled);
		
	}
	
	//Default Text
	public ArrayList<String> getPolicyPrint(WebDriver driver,ArrayList<String> test_steps) {
		ArrayList<String> finalText = new ArrayList<String>();
		String path = "//div[text()='Custom text']/../following-sibling::ul/li";
		for(WebElement ele:driver.findElements(By.xpath(path))) {
			finalText.add(ele.getText().trim());
		}
		test_steps.add("Found Policy Print Text : " + finalText);
		policiesLogger.info("Found Policy Print Text : " + finalText);
		return finalText;
	}
	
	public void verifyPolicyPrint(WebDriver driver, ArrayList<String> expectedDesc, ArrayList<String> test_steps) {
		ArrayList<String> found = getPolicyPrint(driver, test_steps);
		assertTrue(found.equals(expectedDesc),"Failed to Verify Policy Print");
		test_steps.add("Successfully Verified Policy Print Text : " + found);
		policiesLogger.info("Successfully Verified Policy Print Text : " + found);
	}
	
	//Custom Text Toggle
	public void verfiyCustomTextToggleState(WebDriver driver, boolean isDisplayed, boolean expected, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(element.CUSTOM_TEXT_TOGGLE, driver);
		
		assertEquals(element.CUSTOM_TEXT_TOGGLE.isDisplayed(), isDisplayed, "Failed To Verify Update Policy is not Displayed");
		test_steps.add("Successfully Verified Custom Text Toggle is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified Custom Text Toggle is Displayed : " + isDisplayed);
		
		assertEquals(getCustomTextToggleState(driver), expected,"Failed to verify Custom Text Toggle State");
		test_steps.add("Successfully Verified Custom Text Toggle State : " + expected);
		policiesLogger.info("Successfully Verified Custom Text Toggle State : " + expected);
	}
	
	public void changeCustomTextToggleState(WebDriver driver, boolean isActive, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		
		boolean foundState = getCustomTextToggleState(driver);
		if(isActive) {
			if(!foundState){
				element.CUSTOM_TEXT_TOGGLE.click();
				test_steps.add("Custom Text Toggled");
				policiesLogger.info("Custom Text Toggled");
			} else {
				test_steps.add("Already Custom Text Toggled");
				policiesLogger.info("Already Custom Text Toggled");
			}
		} else {
			if(foundState){
				element.CUSTOM_TEXT_TOGGLE.click();
				test_steps.add("Custom Text Toggled");
				policiesLogger.info("Custom Text Toggled");
			} else {
				test_steps.add("Already Custom Text Toggled");
				policiesLogger.info("Already Custom Text Toggled");
			}
		}
	}
	
	public boolean getCustomTextToggleState(WebDriver driver) {
		WebElements_Policies element = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(element.CUSTOM_TEXT_TOGGLE, driver);
		boolean foundState = Boolean.parseBoolean(element.CUSTOM_TEXT_TOGGLE.getAttribute("aria-checked"));
		return foundState;
	}
	
	//Custom Text Feild
	public void enterCustomText(WebDriver driver,String newCustomText, ArrayList<String> test_steps) {
		WebElements_Policies element = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(element.CUSTOM_TEXT_AREA, driver);
		element.CUSTOM_TEXT_AREA.clear();
		Utility.clearValue(driver, element.CUSTOM_TEXT_AREA);
		element.CUSTOM_TEXT_AREA.sendKeys(newCustomText);
		test_steps.add("Enter Custom Text : " + newCustomText);
		policiesLogger.info("Enter Custom Text : " + newCustomText);
	}
	
	public void verifyCustomTextVisibility(WebDriver driver,boolean isEnabled, boolean isDisplayed,ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.CUSTOM_TEXT_AREA, driver);
		assertEquals(elements.CUSTOM_TEXT_AREA.isDisplayed(), isDisplayed, "Failed To Verify Custom Text Display");
		test_steps.add("Successfully Verified Custom Text is Displayed : " + isDisplayed);
		policiesLogger.info("Successfully Verified Custom Text is Displayed : " + isDisplayed);

		assertEquals(elements.CUSTOM_TEXT_AREA.isEnabled(), isEnabled, "Failed To Verify Custom Text Enable");
		test_steps.add("Successfully Verified Custom Text Enable : " + isEnabled);
		policiesLogger.info("Successfully Verified Custom Text Enable : " + isEnabled);
	}

	public void verifyCustomTextFeildValue(WebDriver driver, String expectedValue, boolean expectedEqual, ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);

		String foundValue = elements.CUSTOM_TEXT_AREA.getAttribute("value");
		if(expectedEqual) {
			assertEquals(foundValue, expectedValue,"Failed To Verify VAlue Missmatched");
			test_steps.add("Successfully Verified Custom Text Value Matched : " + foundValue);
			policiesLogger.info("Successfully Verified Custom Text Value Matched : " + foundValue);
		}else {
			assertNotEquals(foundValue, expectedValue,"Failed To Verify Value Matched");
			test_steps.add("Successfully Verified Custom Text Value Not Matched");
			policiesLogger.info("Successfully Verified Custom Text Value Not Matched ");
		}

	}

	public int getCustomTextInputFieldLength(WebDriver driver,ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);

		return elements.CUSTOM_TEXT_AREA.getAttribute("value").length();
	}

	public String getCharCountCustomText(WebDriver driver) {
		return driver.findElement(By.xpath("//span[@class='char-count']")).getText();
	}

	public void verifyCharCountCustomText(WebDriver driver, String expectedMaxLength, ArrayList<String> test_steps) {

		String found = getCharCountCustomText(driver);
		assertEquals(found, expectedMaxLength + " / 255", "Failed To Verify Description Max Length");
		test_steps.add("Successfully Verified Custom Text Value Max Length : " + found);
		policiesLogger.info("Successfully Verified Custom Text Value Max Length : " + found);
	}
	
	
	//Policy Uses
	public void clickUseLink(WebDriver driver, String policyName,ArrayList<String> test_steps) {
		//String typePath = "//*[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+policyType.toUpperCase()+"')]/../..";
		String path = "//*[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+policyName.toUpperCase()+"']/following-sibling::div/span[@class='fs-18 uses']";
		try {
			driver.findElement(By.xpath(path)).click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}
		
		test_steps.add("Click Use Link For : " + policyName);
		policiesLogger.info("Click Use Link For : " + policyName);
	}
	
	public String getPolicyUsesLinkValue(WebDriver driver,String policyName,ArrayList<String> test_steps) {
		//String typePath = "//*[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+policyType.toUpperCase()+"')]/../..";
		String path =  "//*[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"+policyName.toUpperCase()+"']/following-sibling::div/span[@class='fs-18 uses']";
		String found = driver.findElement(By.xpath(path)).getText().replace("uses", "").trim();
		test_steps.add("Found " + policyName + " Uses : " + found);
		policiesLogger.info("Found " + policyName + " Uses : " + found);
		
		return found;
	}
	
	public void verifyPolicyUsesLinkValue(WebDriver driver,String policyName,String expected,ArrayList<String> test_steps) {
		assertEquals(getPolicyUsesLinkValue(driver, policyName, test_steps), expected,"Failed To Verify Uses Missmatched");
		test_steps.add("Successfully Verified Uses Link Value : " + expected);
		policiesLogger.info("Successfully Verified Uses Link Value : " + expected);
	}
	
	public void verifyPioilcyUsesLinkValue(WebDriver driver,String delim,String policyNames,String expected,ArrayList<String> test_steps) {
		ArrayList<String> policyNamesList = Utility.convertTokenToArrayList(policyNames, delim);
		for(String name: policyNamesList) {
			verifyPolicyUsesLinkValue(driver, name, expected, test_steps);
		}
	}
	
	public ArrayList<String> getAllPolicyUses(WebDriver driver,String policyName,ArrayList<String> test_steps){
		WebElements_Policies element = new WebElements_Policies(driver);
		ArrayList<String> getAllPolicyUse = new ArrayList<String>();
		for(WebElement ele:element.All_POLICY_USES) {
			getAllPolicyUse.add(ele.getText());
		}
		return getAllPolicyUse;
	}
	
	public void verifyNoPolicyUses(WebDriver driver,String policyName,ArrayList<String> test_steps) {
		clickUseLink(driver, policyName, test_steps);
		verifyPopupDisplayed(driver, "Policy uses", test_steps);
		String path = "//*[text()='Policy uses']/../following-sibling::div/div";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		assertEquals(driver.findElement(By.xpath(path)).getText().trim(),"No uses for this policy","Failed To Verify No Uses For this Policy Text");
		test_steps.add("Succesfully Verified No Policy Uses Text");
		policiesLogger.info("Succesfully Verified No Policy Uses Text");
		closePolicyPopup(driver, test_steps);
	}
	
	public void verifyNoPolicyUsesAllType(WebDriver driver,String delim,String policyTypes,String cancellationPolicyName,String depositPolicyName,String checkInPolicyName,String noShowPolicyName,ArrayList<String> test_steps) {
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyTypes, delim);
		test_steps.add("=================== VERIFY POLICY USES ======================");
		policiesLogger.info("=================== VERIFY POLICY USES ======================");
		for(String type: policyTypesList) {
			if(type.equalsIgnoreCase("Cancellation")) {
				verifyNoPolicyUses(driver, cancellationPolicyName, test_steps);
			}else if(type.equalsIgnoreCase("Deposit")) {
				verifyNoPolicyUses(driver, depositPolicyName, test_steps);
			}else if(type.equalsIgnoreCase("Check-In")) {
				verifyNoPolicyUses(driver, checkInPolicyName, test_steps);
			}else if(type.equalsIgnoreCase("No Show")) {
				verifyNoPolicyUses(driver, noShowPolicyName, test_steps);
			}
		}
	}
	
	public void verifyPolicyUses(WebDriver driver,String rateName,boolean isExpected,ArrayList<String> test_steps) {
		String path = "//ul[@class='policy-uses']//li[text()='"+rateName.toUpperCase()+"']";
		
		try {
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
			assertEquals(driver.findElement(By.xpath(path)).isDisplayed(),isExpected,"Failed To Verify Rate Name Displayed ");
			test_steps.add("Successfully Verified Rate Name : "+rateName+" is Displayed : " + isExpected);
			policiesLogger.info("Successfully Verified Rate Name : "+rateName+" is Displayed : " + isExpected);
		}catch (Exception e) {
			if(isExpected) {
				assertTrue(false,"Failed To Verify Rate Name is not Displayed ");
			}else {
				assertTrue(true,"Failed To Verify Rate Name is Displayed ");
				test_steps.add("Successfully Verified Rate Name : "+rateName+" is not Displayed " );
				policiesLogger.info("Successfully Verified Rate Name : "+rateName+" is not Displayed ");
			}
		}
	}
	
	public void verifyPolicyUsesAllType(WebDriver driver, String delim, String policyNames,String rateName,boolean isExpected,ArrayList<String> test_steps) {
		ArrayList<String> policyNamesList = Utility.convertTokenToArrayList(policyNames, delim);
		for(String name: policyNamesList) {
			clickUseLink(driver, name, test_steps);
			verifyPopupDisplayed(driver, "Policy uses", test_steps);
			verifyPolicyUses(driver, rateName, isExpected, test_steps);
			closePolicyPopup(driver, test_steps);
		}
	}
	
	public void createCancelationPolicy(WebDriver driver,ArrayList<String> test_steps,String delim,String policyName,String TypeOfFees,String GuestsWillIncurAFee,
			String ChargesType,String NoOfDays,String CancelWithInType,boolean isCustomText,String CustomText) throws InterruptedException {
		
		ArrayList<String> typeOfFees = Utility.convertTokenToArrayList(TypeOfFees, delim);
		ArrayList<String> guestWillIncurAFee = Utility.convertTokenToArrayList(GuestsWillIncurAFee, delim);
		ArrayList<String> chargesType = Utility.convertTokenToArrayList(ChargesType, delim);
		ArrayList<String> noOfDays = Utility.convertTokenToArrayList(NoOfDays, delim);
		ArrayList<String> cancelWithInType = Utility.convertTokenToArrayList(CancelWithInType, delim);
		
		enterPolicyName(driver, policyName, test_steps);
		int percentOfStayCount = 0;
		for(int clause=0;clause<typeOfFees.size();clause++) {
			int clauseNo = clause + 1;
			if(clauseNo>1) {
				clickAddNewClause(driver, test_steps);
			}
			String feeType = typeOfFees.get(clause);
			selectTypeOfFee(driver, feeType, clauseNo, test_steps);
			
			if(feeType.equalsIgnoreCase("percent of stay")) {
				fillPercentOfStay(driver, guestWillIncurAFee.get(clause), chargesType.get(percentOfStayCount), noOfDays.get(clause), cancelWithInType.get(clause), clauseNo, (percentOfStayCount+1),test_steps);
				percentOfStayCount++;
			}else if(feeType.equalsIgnoreCase("flat fee")) {
				fillFlatFee(driver, guestWillIncurAFee.get(clause), noOfDays.get(clause), cancelWithInType.get(clause), clauseNo, test_steps);
			}else if(feeType.equalsIgnoreCase("number of nights")) {
				fillNumberOfNights(driver, guestWillIncurAFee.get(clause), noOfDays.get(clause), cancelWithInType.get(clause), clauseNo, test_steps);
			}
			
		}
		
		if(isCustomText) {
			changeCustomTextToggleState(driver, isCustomText, test_steps);
			enterCustomText(driver, CustomText, test_steps);
		}
		
		
	}
	
	public void verifyCancelationPolicyEditMode(WebDriver driver,ArrayList<String> test_steps,String delim,String policyName,String TypeOfFees,String GuestsWillIncurAFee,
			String ChargesType,String NoOfDays,String CancelWithInType,boolean isCustomText,String CustomText) throws InterruptedException {
		ArrayList<String> typeOfFees = Utility.convertTokenToArrayList(TypeOfFees, delim);
		ArrayList<String> guestWillIncurAFee = Utility.convertTokenToArrayList(GuestsWillIncurAFee, delim);
		ArrayList<String> chargesType = Utility.convertTokenToArrayList(ChargesType, delim);
		ArrayList<String> noOfDays = Utility.convertTokenToArrayList(NoOfDays, delim);
		ArrayList<String> cancelWithInType = Utility.convertTokenToArrayList(CancelWithInType, delim);
		
		verifyPolicyNameFeildValue(driver, policyName, true, test_steps);
		int percentOfStayCount = 0;
		for(int clause=0;clause<typeOfFees.size();clause++) {
			int clauseNo = clause + 1;
//			
			String feeType = typeOfFees.get(clause);
			verfiySelectedTypeOfFee(driver, feeType, clauseNo, test_steps);
			
			if(feeType.equalsIgnoreCase("percent of stay")) {
				
				verifyPercentageValue(driver, guestWillIncurAFee.get(clause), true, clauseNo, test_steps);
				verifySelectedChargeType(driver, chargesType.get(percentOfStayCount), clauseNo, test_steps);
				verifyNoOfDaysValue(driver, noOfDays.get(clause), true, clauseNo, test_steps);
				verifySelectedCancelType(driver, cancelWithInType.get(clause), clauseNo, test_steps);	
				
				percentOfStayCount++;
			}else if(feeType.equalsIgnoreCase("flat fee")) {
				
				verifyFlatAmountValue(driver, guestWillIncurAFee.get(clause), true, clauseNo, test_steps);
				verifyNoOfDaysValue(driver, noOfDays.get(clause), true, clauseNo, test_steps);
				verifySelectedCancelType(driver, cancelWithInType.get(clause), clauseNo, test_steps);	
			}else if(feeType.equalsIgnoreCase("number of nights")) {
				
				verifyNoOfNightsValue(driver, guestWillIncurAFee.get(clause), true, clauseNo, test_steps);
				verifyNoOfDaysValue(driver, noOfDays.get(clause), true, clauseNo, test_steps);
				verifySelectedCancelType(driver, cancelWithInType.get(clause), clauseNo, test_steps);	
			}
			
		}
		
		verfiyCustomTextToggleState(driver, true, isCustomText, test_steps);
		if(isCustomText) {
			verifyCustomTextFeildValue(driver, CustomText, true, test_steps);
		}
	}
	
	public void verifyCancelationPolicyEditModeClauseSpecific(WebDriver driver,ArrayList<String> test_steps,String delim,String clauses,String policyName,String TypeOfFees,String GuestsWillIncurAFee,
			String ChargesType,String NoOfDays,String CancelWithInType,boolean isCustomText,String CustomText) throws InterruptedException {
		ArrayList<String> clausesList = Utility.convertTokenToArrayList(clauses, delim);
		
		ArrayList<String> typeOfFees = Utility.convertTokenToArrayList(TypeOfFees, delim);
		ArrayList<String> guestWillIncurAFee = Utility.convertTokenToArrayList(GuestsWillIncurAFee, delim);
		ArrayList<String> chargesType = Utility.convertTokenToArrayList(ChargesType, delim);
		ArrayList<String> noOfDays = Utility.convertTokenToArrayList(NoOfDays, delim);
		ArrayList<String> cancelWithInType = Utility.convertTokenToArrayList(CancelWithInType, delim);
		
		verifyPolicyNameFeildValue(driver, policyName, true, test_steps);
		int percentOfStayCount = 0;
		int clause = 0;
		for(String clauseStr: clausesList) {
			 
			int clauseNo = Integer.parseInt(clauseStr);
//			
			String feeType = typeOfFees.get(clause);
			verfiySelectedTypeOfFee(driver, feeType, clauseNo, test_steps);
			
			if(feeType.equalsIgnoreCase("percent of stay")) {
				
				verifyPercentageValue(driver, guestWillIncurAFee.get(clause), true, clauseNo, test_steps);
				verifySelectedChargeType(driver, chargesType.get(percentOfStayCount), clauseNo, test_steps);
				verifyNoOfDaysValue(driver, noOfDays.get(clause), true, clauseNo, test_steps);
				verifySelectedCancelType(driver, cancelWithInType.get(clause), clauseNo, test_steps);	
				
				percentOfStayCount++;
			}else if(feeType.equalsIgnoreCase("flat fee")) {
				
				verifyFlatAmountValue(driver, guestWillIncurAFee.get(clause), true, clauseNo, test_steps);
				verifyNoOfDaysValue(driver, noOfDays.get(clause), true, clauseNo, test_steps);
				verifySelectedCancelType(driver, cancelWithInType.get(clause), clauseNo, test_steps);	
			}else if(feeType.equalsIgnoreCase("number of nights")) {
				
				verifyNoOfNightsValue(driver, guestWillIncurAFee.get(clause), true, clauseNo, test_steps);
				verifyNoOfDaysValue(driver, noOfDays.get(clause), true, clauseNo, test_steps);
				verifySelectedCancelType(driver, cancelWithInType.get(clause), clauseNo, test_steps);	
			}
			clause++;
		}
		
		verfiyCustomTextToggleState(driver, true, isCustomText, test_steps);
		if(isCustomText) {
			verifyCustomTextFeildValue(driver, CustomText, true, test_steps);
		}
	}
	
	public void verifyLastPolicy(WebDriver driver,ArrayList<String> test_steps,String policyType, String policyName,boolean isExpected){
		String path = "(//*[contains(text(),'"+policyType+"')]//..//..//div[@class='title'])[last()]";
		if(isExpected) {
			assertEquals(driver.findElement(By.xpath(path)).isDisplayed(), isExpected,"Failed to Verify Policy Displayed");
			test_steps.add("Successfully Verified Policy Named : "+policyName+" is Displayed " );
			policiesLogger.info("Successfully Verified Policy Named : "+policyName+" is Displayed " );
			assertEquals(driver.findElement(By.xpath(path)).getText(), policyName,"Failed to Verify Policy");
			test_steps.add("Successfully Verified Policy Named : "+policyName);
			policiesLogger.info("Successfully Verified Policy Named : "+policyName);
		}else {
			try {
				assertNotEquals(driver.findElement(By.xpath(path)).getText(), policyName,"Failed to Verify Policy");
				test_steps.add("Successfully Verified Policy Not Found Named : "+policyName);
				policiesLogger.info("Successfully Verified Policy Not Found Named : "+policyName);
				
				path = "//*[contains(text(),'"+policyType+"')]//..//..//div[@class='title' and text()='"+policyName+"']";
				assertNotEquals(driver.findElement(By.xpath(path)).getText(), policyName,"Failed to Verify Policy");
				test_steps.add("Successfully Verified Policy Not Found Named : "+policyName);
				policiesLogger.info("Successfully Verified Policy Not Found Named : "+policyName);
				
			}catch (Exception e) {
				assertTrue(true);
				test_steps.add("Successfully Verified Policy Named : "+policyName+" is not Displayed " );
				policiesLogger.info("Successfully Verified Policy Named : "+policyName+" is not Displayed " );
			}
		}
	}
	
	public void verifyLastPolicy(WebDriver driver,ArrayList<String> test_steps, String policyName,boolean isExpected){
		String path = "(//div[@class='title'])[last()]";
		if(isExpected) {
			assertEquals(driver.findElement(By.xpath(path)).isDisplayed(), isExpected,"Failed to Verify Policy Displayed");
			test_steps.add("Successfully Verified Policy Named : "+policyName+" is Displayed " );
			policiesLogger.info("Successfully Verified Policy Named : "+policyName+" is Displayed " );
			assertEquals(driver.findElement(By.xpath(path)).getText(), policyName,"Failed to Verify Policy");
			test_steps.add("Successfully Verified Policy Named : "+policyName);
			policiesLogger.info("Successfully Verified Policy Named : "+policyName);
		}else {
			try {
				assertNotEquals(driver.findElement(By.xpath(path)).getText(), policyName,"Failed to Verify Policy");
				test_steps.add("Successfully Verified Policy Not Found Named : "+policyName);
				policiesLogger.info("Successfully Verified Policy Not Found Named : "+policyName);
				
				path = "//div[@class='title' and text()='"+policyName+"']";
				assertNotEquals(driver.findElement(By.xpath(path)).getText(), policyName,"Failed to Verify Policy");
				test_steps.add("Successfully Verified Policy Not Found Named : "+policyName);
				policiesLogger.info("Successfully Verified Policy Not Found Named : "+policyName);
				
			}catch (Exception e) {
				assertTrue(true);
				test_steps.add("Successfully Verified Policy Named : "+policyName+" is not Displayed " );
				policiesLogger.info("Successfully Verified Policy Named : "+policyName+" is not Displayed " );
			}
		}
	}
	
	public void verifyPolicyDescription(WebDriver driver,ArrayList<String> test_steps,String policyName,ArrayList<String> policyDescList) {
		String path = "//div[@class='title' and text()='"+policyName+"']/../following-sibling::span";
		int count = 0 ;
		for(WebElement ele:driver.findElements(By.xpath(path))){
			assertEquals(ele.getText(), policyDescList.get(count),"Failed to Verify Policy Description");
			test_steps.add("Successfully Verified Policy Description : " + ele.getText() );
			policiesLogger.info("Successfully Verified Policy Description : " + ele.getText() );
			count++;
		}
	}
	
	public void deleteClauses(WebDriver driver,String delim, String deleteClauses,ArrayList<String> test_steps) throws InterruptedException {
		ArrayList<String> clausesList = Utility.convertTokenToArrayList(deleteClauses, delim);
		ArrayList<Integer> intClause = new ArrayList<Integer>();
		for(String str: clausesList) {
			if(str.equalsIgnoreCase("na")) {
				break;
			}else {
				intClause.add(Integer.parseInt(str));
			}
		}
		Collections.sort(intClause);
		Collections.reverse(intClause);
		int count = 0;
		for(int clause: intClause) {
			if(clause==1) {
				test_steps.add("Cannot Delete First Clause");
				policiesLogger.info("Cannot Delete First Clause");
			}else {
				String path = "//span[contains(@class,'sr-no') and contains(text(),'"+clause+"')]/../span[@class='remove']";
				Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(path)), driver);
				Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
				driver.findElement(By.xpath(path)).click();
				test_steps.add("Click Delete Clause : " + clause);
				policiesLogger.info("Click Delete Clause : " + clause);
				int c = clickAddNewClause(driver, new ArrayList<String>());
				selectTypeOfFee(driver, "flat fee", c, test_steps);
				count++;
			}
		}
		for(int i=1; i<=count;i++) {
			String path = "(//span[contains(@class,'sr-no')]/../span[@class='remove'])[last()]";
			Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(path)), driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
			driver.findElement(By.xpath(path)).click();
		}
	}
	
	public void editClauses(WebDriver driver,String delim,String EditName,String EditClause,String UpdateTypeOfFees,String UpdateGuestsWillIncurAFee,
			String UpdateChargesType,String UpdateNoOfDays,String UpdateCancelWithInType,boolean UpdateIsCustomText,String UpdateCustomText,ArrayList<String> test_steps) throws InterruptedException {
		ArrayList<String> clausesList = Utility.convertTokenToArrayList(EditClause, delim);
		
		ArrayList<String> typeOfFees = Utility.convertTokenToArrayList(UpdateTypeOfFees, delim);
		ArrayList<String> guestWillIncurAFee = Utility.convertTokenToArrayList(UpdateGuestsWillIncurAFee, delim);
		ArrayList<String> chargesType = Utility.convertTokenToArrayList(UpdateChargesType, delim);
		ArrayList<String> noOfDays = Utility.convertTokenToArrayList(UpdateNoOfDays, delim);
		ArrayList<String> cancelWithInType = Utility.convertTokenToArrayList(UpdateCancelWithInType, delim);
		
		enterPolicyName(driver, EditName, test_steps);
		int percentOfStayCount = 0;
		int clause = 0;
		for(String clauseStr: clausesList) {
			int clauseNo = Integer.parseInt(clauseStr);
			
//			if(!clauseStr.equals("1")) {
//				deleteClauses(driver, delim, clauseNo+"", test_steps);
//				clickAddNewClause(driver, test_steps);
//			}
			String feeType = typeOfFees.get(clause);
			selectTypeOfFee(driver, feeType, clauseNo, test_steps);
			
			if(feeType.equalsIgnoreCase("percent of stay")) {
				fillPercentOfStay(driver, guestWillIncurAFee.get(clause), chargesType.get(percentOfStayCount), noOfDays.get(clause), cancelWithInType.get(clause), clauseNo,(percentOfStayCount+1), test_steps);
				percentOfStayCount++;
			}else if(feeType.equalsIgnoreCase("flat fee")) {
				fillFlatFee(driver, guestWillIncurAFee.get(clause), noOfDays.get(clause), cancelWithInType.get(clause), clauseNo, test_steps);
			}else if(feeType.equalsIgnoreCase("number of nights")) {
				fillNumberOfNights(driver, guestWillIncurAFee.get(clause), noOfDays.get(clause), cancelWithInType.get(clause), clauseNo, test_steps);
			}
			clause++;
		}
		
		if(UpdateIsCustomText) {
			changeCustomTextToggleState(driver, UpdateIsCustomText, test_steps);
			enterCustomText(driver, UpdateCustomText, test_steps);
		}
	}
	
	public int getTotalNoOfCancelationPolicyClauses(WebDriver driver,ArrayList<String> test_steps) {
		int size = driver.findElements(By.xpath("//span[contains(@class,'sr-no')]")).size();
		test_steps.add("Found Total No Of Clauses : " + size );
		policiesLogger.info("Found Total No Of Clauses : " + size );
		return size;
	}
	
	public void verifyTotalNoOfCancelationClauses(WebDriver driver, String expectedNo,ArrayList<String> test_steps) {
		assertEquals(getTotalNoOfCancelationPolicyClauses(driver, test_steps)+"", expectedNo+"","Failed Total No of Clauses MissMatched");
		test_steps.add("Successfully Verified Total No of Cancelation Clauses : " + expectedNo );
		policiesLogger.info("Successfully Verified Total No of Cancelation Clauses : " + expectedNo );
	}
	
	public void closePolicyPopup(WebDriver driver, ArrayList<String> test_steps) {
		String path = "//button[@aria-label='Close']";
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		test_steps.add("Policy Popup Closed" );
		policiesLogger.info("Policy Popup Closed" );
	}

	public String enterNewDepsitPolicyDetails(WebDriver driver, ArrayList<String> test_steps,String DepositPolicyName,String GuestMustPayType,String GuestMustPayPercentage,String GuestMustPayChargesType,String isCustomText,String DepositCustomText,String currency) {
		WebElements_Policies element = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyHeader);	
		assertTrue(element.NewPolicies_DepositPolicyHeader.isDisplayed(), "New deposit policy header is not displayed");
		policiesLogger.info("New deposit policy header is displayed");
		test_steps.add("New deposit policy header is displayed");
		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyName);	
		element.NewPolicies_DepositPolicyName.sendKeys(DepositPolicyName);
		policiesLogger.info("Enter desit policy name : "+DepositPolicyName);
		test_steps.add("Enter desit policy name : "+DepositPolicyName);

		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyGuestPayDropDown);
		element.NewPolicies_DepositPolicyGuestPayDropDown.click();
		policiesLogger.info("Click guest must pay dropdown");
		test_steps.add("Click guest must pay dropdown");

		GuestMustPayType=GuestMustPayType.toLowerCase();
		String guestMustPayType="//li[contains(text(),'"+GuestMustPayType+"')]";
		Wait.WaitForElement(driver,guestMustPayType);
		driver.findElement(By.xpath(guestMustPayType)).click();
		policiesLogger.info("selecting guest must pay type as : "+GuestMustPayType);
		test_steps.add("selecting guest must pay type as : "+GuestMustPayType);

		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyGuestPayPercentage);	
		element.NewPolicies_DepositPolicyGuestPayPercentage.sendKeys(GuestMustPayPercentage);
		policiesLogger.info("Enter guest must pay percentage : "+GuestMustPayPercentage);
		test_steps.add("Enter guest must pay percentage : "+GuestMustPayPercentage);
		
		
		if(GuestMustPayType.equalsIgnoreCase("flat fee")) {
			 String appCurrentcy="//div[contains(text(),'When guest books reservation, they must pay')]/span/label/span";
			 appCurrentcy= driver.findElement(By.xpath(appCurrentcy)).getText();
			 appCurrentcy=appCurrentcy.trim();
			 assertTrue(appCurrentcy.equalsIgnoreCase(currency), "Default currency symbol is not found");
			 policiesLogger.info("Default currency symbol is verified");
				test_steps.add("Default currency symbol is verified");
		}

		if(GuestMustPayType.equalsIgnoreCase("percent of stay")) {
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyGuestPayTypeDropDown);
			element.NewPolicies_DepositPolicyGuestPayTypeDropDown.click();
			policiesLogger.info("Click guest must pay type dropdown");
			test_steps.add("Click guest must pay type dropdown");

			GuestMustPayChargesType=GuestMustPayChargesType.toLowerCase();
			String guestMustPaychargesType="//li[contains(text(),'"+GuestMustPayChargesType+"')]";
			Wait.WaitForElement(driver,guestMustPaychargesType);
			driver.findElement(By.xpath(guestMustPaychargesType)).click();
			policiesLogger.info("selecting guest must pay charges type as : "+GuestMustPayChargesType);
			test_steps.add("selecting guest must pay charges type as : "+GuestMustPayChargesType);
		}
		String depositFinePrint=null;
		if(isCustomText.equalsIgnoreCase("Yes")) {
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyCustomTextButton);
			element.NewPolicies_DepositPolicyCustomTextButton.click();
			policiesLogger.info("Click on guest custom text button");
			test_steps.add("Click on guest custom text button");
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyCustomText);
			element.NewPolicies_DepositPolicyCustomText.clear();

			String selectAll = Keys.chord(Keys.CONTROL, "a");
			element.NewPolicies_DepositPolicyCustomText.sendKeys(selectAll);

			element.NewPolicies_DepositPolicyCustomText.sendKeys(DepositCustomText);
			policiesLogger.info("Enter deposit policy custom text as : "+DepositCustomText);
			test_steps.add("Enter deposit policy custom text as : "+DepositCustomText);
			depositFinePrint=DepositCustomText;
		}else {
			String text="//li[contains(text(),'When guest books')]";
			depositFinePrint=driver.findElement(By.xpath(text)).getText();
		}

		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyAddPolicy);
		element.NewPolicies_DepositPolicyAddPolicy.click();
		policiesLogger.info("Click on add policy");
		test_steps.add("Click on add policy");

		return depositFinePrint;
	}


	public void verifyCreatedDepositPolicy(WebDriver driver, ArrayList<String> test_steps,String DepositPolicyName,String customText) {
		String policy="//div[contains(text(),'Deposit')]/../following-sibling::div//div[contains(text(),'"+DepositPolicyName+"')]";
		//Wait.WaitForElement(driver, policy);	
		assertTrue(driver.findElement(By.xpath(policy)).isDisplayed(), "Created policy is not displayed in policy grid: "+DepositPolicyName);
		policiesLogger.info("Created policy is displayed in policy grid : "+DepositPolicyName);
		test_steps.add("Created policy is displayed in policy grid : "+DepositPolicyName);

		String text="//span[contains(text(),'Deposit')]/../following-sibling::div//span[contains(text(),'"+customText+"')]";
		Wait.WaitForElement(driver, text);	
		assertTrue(driver.findElement(By.xpath(text)).isDisplayed(), "Created policy custeom is not displayed in policy grid: "+customText);
		policiesLogger.info("Created policy custeom text is displayed in policy grid : "+customText);
		test_steps.add("Created policy custom text is displayed in policy grid : "+customText);
	}

	public void clickEditDepositPolicy(WebDriver driver, ArrayList<String> test_steps,String DepositPolicyName) {
		String policy="//div[contains(text(),'"+DepositPolicyName+"')]/../div/i";
		Wait.WaitForElement(driver, policy);
		driver.findElement(By.xpath(policy)).click();
		policiesLogger.info("Click on edit policy button");
		test_steps.add("Click on edit policy button");
	}

	public String updateNewDepsitPolicyDetails(WebDriver driver, ArrayList<String> test_steps,String UpdatedDepositPolicyName,String UpdatedGuestMustPayType,String UpdatedGuestMustPayPercentage,String UpdatedGuestMustPayChargesType,String isCustomTextUpdated,String UpdatedDepositCustomText,String currency) {
		WebElements_Policies element = new WebElements_Policies(driver);
		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyEditHeader);	
		assertTrue(element.NewPolicies_DepositPolicyEditHeader.isDisplayed(), "edit deposit policy header is not displayed");
		policiesLogger.info("edit deposit policy header is displayed");
		test_steps.add("edit deposit policy header is displayed");
		String selectAll = Keys.chord(Keys.CONTROL, "a");
		element.NewPolicies_DepositPolicyName.sendKeys(selectAll);
		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyName);	
		element.NewPolicies_DepositPolicyName.sendKeys(UpdatedDepositPolicyName);
		policiesLogger.info("Enter desit policy name : "+UpdatedDepositPolicyName);
		test_steps.add("Enter desit policy name : "+UpdatedDepositPolicyName);

		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyGuestPayDropDown);
		element.NewPolicies_DepositPolicyGuestPayDropDown.click();
		policiesLogger.info("Click guest must pay dropdown");
		test_steps.add("Click guest must pay dropdown");

		UpdatedGuestMustPayType=UpdatedGuestMustPayType.toLowerCase();
		String guestMustPayType="//li[contains(text(),'"+UpdatedGuestMustPayType+"')]";
		Wait.WaitForElement(driver,guestMustPayType);
		driver.findElement(By.xpath(guestMustPayType)).click();
		policiesLogger.info("selecting guest must pay type as : "+UpdatedGuestMustPayType);
		test_steps.add("selecting guest must pay type as : "+UpdatedGuestMustPayType);

		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyGuestPayPercentage);	
		element.NewPolicies_DepositPolicyGuestPayPercentage.sendKeys(UpdatedGuestMustPayPercentage);
		policiesLogger.info("Enter guest must pay percentage : "+UpdatedGuestMustPayPercentage);
		test_steps.add("Enter guest must pay percentage : "+UpdatedGuestMustPayPercentage);

		if(UpdatedGuestMustPayType.equalsIgnoreCase("flat fee")) {
			 String appCurrentcy="//div[contains(text(),'When guest books reservation, they must pay')]/span/label/span";
			 appCurrentcy= driver.findElement(By.xpath(appCurrentcy)).getText();
			 appCurrentcy=appCurrentcy.trim();
			 assertTrue(appCurrentcy.equalsIgnoreCase(currency), "Default currency symbol is not found");
			 policiesLogger.info("Default currency symbol is verified");
				test_steps.add("Default currency symbol is verified");
		}
		
		if(UpdatedGuestMustPayType.equalsIgnoreCase("percent of stay")) {
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyGuestPayTypeDropDown);
			element.NewPolicies_DepositPolicyGuestPayTypeDropDown.click();
			policiesLogger.info("Click guest must pay type dropdown");
			test_steps.add("Click guest must pay type dropdown");

			UpdatedGuestMustPayChargesType=UpdatedGuestMustPayChargesType.toLowerCase();
			String guestMustPaychargesType="//li[contains(text(),'"+UpdatedGuestMustPayChargesType+"')]";
			Wait.WaitForElement(driver,guestMustPaychargesType);
			driver.findElement(By.xpath(guestMustPaychargesType)).click();
			policiesLogger.info("selecting guest must pay charges type as : "+UpdatedGuestMustPayChargesType);
			test_steps.add("selecting guest must pay charges type as : "+UpdatedGuestMustPayChargesType);
		}
		String depositFinePrint=null;
		if(isCustomTextUpdated.equalsIgnoreCase("Yes")) {
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyCustomTextButton);

			String area_checked=element.NewPolicies_DepositPolicyCustomTextButton.getAttribute("aria-checked");

			if(!area_checked.equalsIgnoreCase("true")) {
				element.NewPolicies_DepositPolicyCustomTextButton.click();
				policiesLogger.info("Click on guest custom text button");
				test_steps.add("Click on guest custom text button");
			}
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyCustomText);
			element.NewPolicies_DepositPolicyCustomText.clear();
			String selectAll1 = Keys.chord(Keys.CONTROL, "a");
			element.NewPolicies_DepositPolicyCustomText.sendKeys(selectAll1);
			element.NewPolicies_DepositPolicyCustomText.sendKeys(UpdatedDepositCustomText);
			policiesLogger.info("Enter deposit policy custom text as : "+UpdatedDepositCustomText);
			test_steps.add("Enter deposit policy custom text as : "+UpdatedDepositCustomText);
			depositFinePrint=UpdatedDepositCustomText;
		}else {
			String area_checked=element.NewPolicies_DepositPolicyCustomTextButton.getAttribute("aria-checked");

			if(area_checked.equalsIgnoreCase("true")) {
				element.NewPolicies_DepositPolicyCustomTextButton.click();
			}
			String text="//li[contains(text(),'When guest books')]";
			depositFinePrint=driver.findElement(By.xpath(text)).getText();
		}

		Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyUpdatePolicy);
		element.NewPolicies_DepositPolicyUpdatePolicy.click();
		policiesLogger.info("Click on update policy");
		test_steps.add("Click on update policy");

		return depositFinePrint;
	}
	
	public void deleteDepositPolicy(WebDriver driver, ArrayList<String> test_steps,String DepositPolicyName) throws InterruptedException {
			String policy="//div[contains(text(),'"+DepositPolicyName+"')]/../div/i[2]";
			Wait.WaitForElement(driver, policy);
			driver.findElement(By.xpath(policy)).click();
			policiesLogger.info("Click on delete policy button");
			test_steps.add("Click on delete policy button");
			
			WebElements_Policies element = new WebElements_Policies(driver);
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyDeleteHeader);	
			assertTrue(element.NewPolicies_DepositPolicyDeleteHeader.isDisplayed(), "delete deposit policy header is not displayed");
			policiesLogger.info("delete deposit policy header is displayed");
			test_steps.add("delete deposit policy header is displayed");
			Wait.WaitForElement(driver, OR_Setup.NewPolicies_DepositPolicyDeleteHeader);
			element.NewPolicies_DepositPolicyDelete.click();
			policiesLogger.info("click on delete");
			test_steps.add("click on delete");
			Wait.wait3Second();
			policy="//div[contains(text(),'Deposit')]/../following-sibling::div//div[contains(text(),'"+DepositPolicyName+"')]";
			assertFalse(driver.findElements(By.xpath(policy)).size()>0, "Created policy is not deleted in policy grid: "+DepositPolicyName);
			policiesLogger.info("Created policy is deleted in policy grid : "+DepositPolicyName);
			test_steps.add("Created policy is deleted in policy grid : "+DepositPolicyName);
	}

	public HashMap<String, ArrayList<String>> createPolicies(WebDriver driver,ArrayList<String> test_steps,
			String delim,String secondaryDelim,String policyTypes,
			String cancellationolicyName,String depositPolicyName,String checkInPolicyName,String noShowPolicyName,
			String typeOfFees,String guestsWillIncurAFee,String chargesTypes,String noOfDays,String cancelWithInType,
			String isCustomText,String customText) throws InterruptedException{
		
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyTypes, delim);
		ArrayList<String> typeOfFeesList = Utility.convertTokenToArrayList(typeOfFees, delim);
		ArrayList<String> percentageList = Utility.convertTokenToArrayList(guestsWillIncurAFee, delim);
		ArrayList<String> chargesTypeList = Utility.convertTokenToArrayList(chargesTypes, delim);
		ArrayList<String> isCustomTextList = Utility.convertTokenToArrayList(isCustomText, delim);
		ArrayList<String> customTextList = Utility.convertTokenToArrayList(customText, delim);
		
		HashMap<String, ArrayList<String>> policyDesc = new HashMap<String, ArrayList<String>>();
		int index = 0;
		//int chargesTypeIndex = 0;
		int clauseNo = 1; //this will not change as clause is only for cancellation type
		for(String type:policyTypesList) {
			if(type.equalsIgnoreCase("Cancellation")) {
				test_steps.add("=================== CANCELLATION POLICY CREATION ======================");
				policiesLogger.info("=================== CANCELLATION POLICY CREATION ======================");
				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				clickCreateNewPolicy(driver, test_steps, type);
				verifyPopupDisplayed(driver, "New cancellation policy", test_steps);
				createCancelationPolicy(driver, test_steps, secondaryDelim, cancellationolicyName, typeOfFeesList.get(index), percentageList.get(index), chargesTypeList.get(index), noOfDays, cancelWithInType, false, "");
				Wait.wait2Second();
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				Wait.wait2Second();
//				System.out.println(foundPolicyDesc);
				policyDesc.put(cancellationolicyName, foundPolicyDesc);
				//System.out.println(policyDesc.get(CheckInPolicyName));
				clickAddPolicy(driver, test_steps);
			} else if(type.equalsIgnoreCase("Deposit")) {
				
				test_steps.add("=================== DEPOSIT POLICY CREATION ======================");
				policiesLogger.info("=================== DEPOSIT POLICY CREATION ======================");
				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				clickCreateNewPolicy(driver, test_steps, type);
				verifyPopupDisplayed(driver, "New deposit policy", test_steps);
				
				
				enterPolicyName(driver, depositPolicyName, test_steps);
				selectTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				
				if(typeOfFeesList.get(index).equalsIgnoreCase("percent of stay")) {
					enterPercentage(driver, percentageList.get(index),clauseNo, test_steps);
					selectNoShowDepositChargeType(driver, chargesTypeList.get(index), test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("flat fee")) {
					enterFlatAmount(driver, percentageList.get(index), clauseNo, test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("number of nights")) {
					enterNoOfNights(driver, percentageList.get(index), clauseNo, test_steps);
				}
				
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				
				policyDesc.put(depositPolicyName, foundPolicyDesc);
				clickAddPolicy(driver, test_steps);
				
			}else if(type.equalsIgnoreCase("Check-in")) {
				test_steps.add("=================== CHECK IN POLICY CREATION ======================");
				policiesLogger.info("=================== CHECK IN POLICY CREATION ======================");
				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				clickCreateNewPolicy(driver, test_steps, type);
				verifyPopupDisplayed(driver, "New Check-in policy", test_steps);
				
				enterPolicyName(driver, checkInPolicyName, test_steps);
				selectTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				enterPercentage(driver, percentageList.get(index),clauseNo, test_steps);
				selectCheckInChargeType(driver, chargesTypeList.get(index), test_steps);
				Wait.wait2Second();
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				Wait.wait2Second();
//				System.out.println(foundPolicyDesc);
				policyDesc.put(checkInPolicyName, foundPolicyDesc);
				//System.out.println(policyDesc.get(CheckInPolicyName));
				clickAddPolicy(driver, test_steps);
			} else if(type.equalsIgnoreCase("No Show")) {
				test_steps.add("=================== NO SHOW POLICY CREATION ======================");
				policiesLogger.info("=================== NO SHOW POLICY CREATION ======================");
				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				clickCreateNewPolicy(driver, test_steps, type);
				verifyPopupDisplayed(driver, "New No show policy", test_steps);
				
				
				enterPolicyName(driver, noShowPolicyName, test_steps);
				selectTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				
				if(typeOfFeesList.get(index).equalsIgnoreCase("percent of stay")) {
					enterPercentage(driver, percentageList.get(index),clauseNo, test_steps);
					selectNoShowDepositChargeType(driver, chargesTypeList.get(index), test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("flat fee")) {
					enterFlatAmount(driver, percentageList.get(index), clauseNo, test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("number of nights")) {
					enterNoOfNights(driver, percentageList.get(index), clauseNo, test_steps);
				}
				
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				
				policyDesc.put(noShowPolicyName, foundPolicyDesc);
				clickAddPolicy(driver, test_steps);
			}
			
			
			index++;
		}
		
		return policyDesc;
	}
	
	public HashMap<String, ArrayList<String>> editPolicies(WebDriver driver,ArrayList<String> test_steps,
			String delim,String secondaryDelim,String policyTypes, 
			String oldCancellationPolicyName,String oldDepositPolicyName,String oldCheckInPolicyName,String oldNoShowPolicyName,
			String updateCancellationPolicyName,String updateDepositPolicyName,String updateCheckInPolicyName,String updateNoShowPolicyName,String deleteClauses,String editClause,
			String typeOfFees,String guestsWillIncurAFee,String chargesTypes,String noOfDays,String cancelWithInType,
			String isCustomText,String customText) throws InterruptedException{
		
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyTypes, delim);
		ArrayList<String> typeOfFeesList = Utility.convertTokenToArrayList(typeOfFees, delim);
		ArrayList<String> percentageList = Utility.convertTokenToArrayList(guestsWillIncurAFee, delim);
		ArrayList<String> chargesTypeList = Utility.convertTokenToArrayList(chargesTypes, delim);
		ArrayList<String> isCustomTextList = Utility.convertTokenToArrayList(isCustomText, delim);
		ArrayList<String> customTextList = Utility.convertTokenToArrayList(customText, delim);
		
		HashMap<String, ArrayList<String>> policyDesc = new HashMap<String, ArrayList<String>>();
		int index = 0;
	//	int chargesTypeIndex = 0;
		int clauseNo = 1; //this will not change as clause is only for cancellation type
		for(String type:policyTypesList) {
			if(type.equalsIgnoreCase("Cancellation")) {
				test_steps.add("=================== CANCELLATION POLICY UPDATION ======================");
				policiesLogger.info("=================== CANCELLATION POLICY UPDATION ======================");
				clickEditIcon(driver, type, oldCancellationPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit cancellation policy", test_steps);
				
				editClauses(driver, secondaryDelim, updateCancellationPolicyName, editClause, typeOfFeesList.get(index), percentageList.get(index), chargesTypeList.get(index), noOfDays, cancelWithInType, false, "", test_steps);
				deleteClauses(driver, secondaryDelim, deleteClauses, test_steps);
				
				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				policyDesc.put(updateCancellationPolicyName, foundPolicyDesc);
				clickUpdatePolicyButton(driver, test_steps);
				
			} else if(type.equalsIgnoreCase("Deposit")) {
				test_steps.add("=================== DEPOSIT POLICY UPDATION ======================");
				policiesLogger.info("=================== DEPOSIT POLICY UPDATION ======================");
				clickEditIcon(driver, type, oldDepositPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit deposit policy", test_steps);
				
				enterPolicyName(driver, updateDepositPolicyName, test_steps);
				selectTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				
				if(typeOfFeesList.get(index).equalsIgnoreCase("percent of stay")) {
					enterPercentage(driver, percentageList.get(index),clauseNo, test_steps);
					selectNoShowDepositChargeType(driver, chargesTypeList.get(index), test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("flat fee")) {
					enterFlatAmount(driver, percentageList.get(index), clauseNo, test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("number of nights")) {
					enterNoOfNights(driver, percentageList.get(index), clauseNo, test_steps);
				}
				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				policyDesc.put(updateDepositPolicyName, foundPolicyDesc);
				clickUpdatePolicyButton(driver, test_steps);
			}else if(type.equalsIgnoreCase("Check-in")) {
				test_steps.add("=================== CHECK IN POLICY UPDATION ======================");
				policiesLogger.info("=================== CHECK IN POLICY UPDATION ======================");
				clickEditIcon(driver, type, oldCheckInPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit check-in policy", test_steps);
				
				enterPolicyName(driver, updateCheckInPolicyName, test_steps);
				selectTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				enterPercentage(driver, percentageList.get(index),clauseNo, test_steps);
				selectCheckInChargeType(driver, chargesTypeList.get(index), test_steps);

				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				policyDesc.put(updateCheckInPolicyName, foundPolicyDesc);
				clickUpdatePolicyButton(driver, test_steps);
			} else if(type.equalsIgnoreCase("No Show")) {
				test_steps.add("=================== NO SHOW POLICY UPDATION ======================");
				policiesLogger.info("=================== NO SHOW POLICY UPDATION ======================");
				clickEditIcon(driver, type, oldNoShowPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit no show policy", test_steps);
				
				
				enterPolicyName(driver, updateNoShowPolicyName, test_steps);
				selectTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				
				if(typeOfFeesList.get(index).equalsIgnoreCase("percent of stay")) {
					enterPercentage(driver, percentageList.get(index),clauseNo, test_steps);
					selectNoShowDepositChargeType(driver, chargesTypeList.get(index), test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("flat fee")) {
					enterFlatAmount(driver, percentageList.get(index), clauseNo, test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("number of nights")) {
					enterNoOfNights(driver, percentageList.get(index), clauseNo, test_steps);
				}
				ArrayList<String> foundPolicyDesc = new ArrayList<String>();
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					changeCustomTextToggleState(driver, true, test_steps);
					enterCustomText(driver, customTextList.get(index), test_steps);
					foundPolicyDesc.add(customTextList.get(index));
				}else {
					foundPolicyDesc = getPolicyPrint(driver, test_steps);
				}
				policyDesc.put(updateNoShowPolicyName, foundPolicyDesc);
				clickUpdatePolicyButton(driver, test_steps);
			}
			
//			if(!type.equalsIgnoreCase("Cancellation")) {
//				chargesTypeIndex++;
//			}
//			
			index++;
		}
		
		return policyDesc;
	}
	
	public void verifyPolicies(WebDriver driver,ArrayList<String> test_steps,
			String delim,String secondaryDelim,String policyTypes,
			String cancellationPolicyName,String depositPolicyName,String checkInPolicyName,String noShowPolicyName,
			String typeOfFees,String guestsWillIncurAFee,String chargesTypes,String noOfDays,String cancelWithInType,
			String isCustomText,HashMap<String, ArrayList<String>> CustomText) throws InterruptedException{
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyTypes, delim);
		ArrayList<String> typeOfFeesList = Utility.convertTokenToArrayList(typeOfFees, delim);
		ArrayList<String> percentageList = Utility.convertTokenToArrayList(guestsWillIncurAFee, delim);
		ArrayList<String> chargesTypeList = Utility.convertTokenToArrayList(chargesTypes, delim);
		ArrayList<String> isCustomTextList = Utility.convertTokenToArrayList(isCustomText, delim);
		
		int index = 0;
	//	int chargesTypeIndex = 0;
		int clauseNo = 1; //this will not change as clause is only for cancellation type
		for(String type:policyTypesList) {
			if(type.equalsIgnoreCase("Cancellation")) {
				test_steps.add("=================== VERIFY CANCELLATION POLICY ======================");
				policiesLogger.info("=================== VERIFY CANCELLATION POLICY ======================");
				
				verifyLastPolicy(driver, test_steps,type, cancellationPolicyName, true);
				verifyPolicyDescription(driver, test_steps,cancellationPolicyName, CustomText.get(cancellationPolicyName));
				
				clickEditIcon(driver, type, cancellationPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit cancellation policy", test_steps);
				verifyCancelationPolicyEditMode(driver, test_steps, secondaryDelim, cancellationPolicyName, typeOfFeesList.get(index),
						percentageList.get(index), chargesTypeList.get(index), noOfDays, cancelWithInType, false,
						"");
				verfiyCustomTextToggleState(driver, true, Boolean.parseBoolean(isCustomTextList.get(index)), test_steps);
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					verifyCustomTextFeildValue(driver, CustomText.get(cancellationPolicyName).get(0), true, test_steps);
				}else {
					verifyPolicyPrint(driver, CustomText.get(cancellationPolicyName), test_steps);
				}
				closePolicyPopup(driver, test_steps);
			} else if(type.equalsIgnoreCase("Deposit")) {
				test_steps.add("=================== VERIFY DEPOSIT POLICY ======================");
				policiesLogger.info("=================== VERIFY DEPOSIT POLICY ======================");
				
				verifyLastPolicy(driver, test_steps,type, depositPolicyName, true);
				verifyPolicyDescription(driver, test_steps, depositPolicyName, CustomText.get(depositPolicyName));
				
				clickEditIcon(driver,type, depositPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit deposit policy", test_steps);
				verifyPolicyNameFeildValue(driver, depositPolicyName, true, test_steps);
				verfiySelectedTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				
				if(typeOfFeesList.get(index).equalsIgnoreCase("percent of stay")) {
					verifyPercentageValue(driver, percentageList.get(index), true, clauseNo, test_steps);
					verifyNoShowSelectedChargeType(driver, chargesTypeList.get(index), test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("flat fee")) {
					verifyFlatAmountValue(driver, percentageList.get(index), true, clauseNo, test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("number of nights")) {
					verifyNoOfNightsValue(driver, percentageList.get(index), true, clauseNo, test_steps);
				}
				
				verfiyCustomTextToggleState(driver, true, Boolean.parseBoolean(isCustomTextList.get(index)), test_steps);
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					verifyCustomTextFeildValue(driver, CustomText.get(depositPolicyName).get(0), true, test_steps);
				}else {
					verifyPolicyPrint(driver, CustomText.get(depositPolicyName), test_steps);
				}
				closePolicyPopup(driver, test_steps);
				
			}else if(type.equalsIgnoreCase("Check-in")) {
				test_steps.add("=================== VERIFY CHECK IN POLICY ======================");
				policiesLogger.info("=================== VERIFY CHECK IN POLICY ======================");
				
				verifyLastPolicy(driver, test_steps,type, checkInPolicyName, true);
				verifyPolicyDescription(driver, test_steps,checkInPolicyName, CustomText.get(checkInPolicyName));
				
				clickEditIcon(driver, type, checkInPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit check-in policy", test_steps);
				verifyPolicyNameFeildValue(driver, checkInPolicyName, true, test_steps);
				verfiySelectedTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				verifyPercentageValue(driver, percentageList.get(index), true, clauseNo, test_steps);
				verifyCheckInSelectedChargeType(driver, chargesTypeList.get(index), test_steps);
				
				verfiyCustomTextToggleState(driver, true, Boolean.parseBoolean(isCustomTextList.get(index)), test_steps);
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					verifyCustomTextFeildValue(driver, CustomText.get(checkInPolicyName).get(0), true, test_steps);
				}else {
					verifyPolicyPrint(driver, CustomText.get(checkInPolicyName), test_steps);
				}
				closePolicyPopup(driver, test_steps);
			} else if(type.equalsIgnoreCase("No Show")) {
				test_steps.add("=================== VERIFY NO SHOW POLICY ======================");
				policiesLogger.info("=================== VERIFY NO SHOW POLICY ======================");
				
				verifyLastPolicy(driver, test_steps,type, noShowPolicyName, true);
				verifyPolicyDescription(driver, test_steps, noShowPolicyName, CustomText.get(noShowPolicyName));
				
				clickEditIcon(driver,type, noShowPolicyName, test_steps);
				verifyPopupDisplayed(driver, "edit no show policy", test_steps);
				verifyPolicyNameFeildValue(driver, noShowPolicyName, true, test_steps);
				verfiySelectedTypeOfFee(driver, typeOfFeesList.get(index), clauseNo, test_steps);
				
				if(typeOfFeesList.get(index).equalsIgnoreCase("percent of stay")) {
					verifyPercentageValue(driver, percentageList.get(index), true, clauseNo, test_steps);
					verifyNoShowSelectedChargeType(driver, chargesTypeList.get(index), test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("flat fee")) {
					verifyFlatAmountValue(driver, percentageList.get(index), true, clauseNo, test_steps);
				}else if(typeOfFeesList.get(index).equalsIgnoreCase("number of nights")) {
					verifyNoOfNightsValue(driver, percentageList.get(index), true, clauseNo, test_steps);
				}
				
				verfiyCustomTextToggleState(driver, true, Boolean.parseBoolean(isCustomTextList.get(index)), test_steps);
				if(Boolean.parseBoolean(isCustomTextList.get(index))) {
					verifyCustomTextFeildValue(driver, CustomText.get(noShowPolicyName).get(0), true, test_steps);
				}else {
					verifyPolicyPrint(driver, CustomText.get(noShowPolicyName), test_steps);
				}
				closePolicyPopup(driver, test_steps);
			}
			
//			if(!type.equalsIgnoreCase("Cancellation")) {
//				chargesTypeIndex++;
//			}
			
			index++;
		}
	}
	

	public void deleteAllPolicies(WebDriver driver,ArrayList<String> test_steps,ArrayList<String> PolicyNames) throws InterruptedException {
		test_steps.add("=================== DELETE POLICIES ======================");
		policiesLogger.info("=================== DELETE POLICIES ======================");
		for(String name:PolicyNames) {
		
			clickDeleteIcon(driver,  name, test_steps);
			clickDeleteButton(driver, test_steps);
			
			test_steps.add("=================== VERIFYING AFTER POLICY DELETE  ======================");
			policiesLogger.info("=================== VERIFYING AFTER POLICY DELETE  ======================");

			verifyLastPolicy(driver, test_steps, name, false);
		
		}
	}
	
	public String getPolicyNames(String delim,String policyTypes,String CancellationPolicy,String DepositPolicy,String CheckInPolicy,String NoShowPolicy) {
		String policyNames = "";
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyTypes, delim);
		int index = 1;
		for(String type:policyTypesList) {
			
			if(type.equalsIgnoreCase("Cancellation")) {
				policyNames = policyNames + CancellationPolicy;
			} else if(type.equalsIgnoreCase("Deposit")) {
				policyNames = policyNames + DepositPolicy;
			} else if(type.equalsIgnoreCase("Check-in")) {
				policyNames = policyNames + CheckInPolicy;
			} else if(type.equalsIgnoreCase("No Show")) {
				policyNames = policyNames + NoShowPolicy;
			}
			
			if(index!=policyTypesList.size()) {
				policyNames+=delim;
			}
			index++;
		}
		return policyNames;
	}
	
	public HashMap<String, String> getPolicyNames(String delim,String policyTypes,String policyNames){
		HashMap<String, String> names = new HashMap<String, String>();
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyTypes, delim);
		ArrayList<String> policyNamesList = Utility.convertTokenToArrayList(policyNames, delim);
		int index = 0;
		for(String type:policyTypesList) {
			names.put(type, policyNamesList.get(index));
			index++;
		}
		return names;
	}
	
	public HashMap<String, String> getPolicyClauses(String delim, String policyTypes,String clauses){
		HashMap<String, String> finalClauses = new HashMap<String, String>();
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyTypes, delim);
		ArrayList<String> clausesList = Utility.convertTokenToArrayList(clauses, delim);
		int index = 0;
		for(String type:policyTypesList) {
			if(type.equalsIgnoreCase("Cancellation")) {
				finalClauses.put(type, clausesList.get(index));
			} else if(type.equalsIgnoreCase("Deposit")) {
				finalClauses.put(type, clausesList.get(index));
			} else if(type.equalsIgnoreCase("Check-in")) {
				finalClauses.put(type, clausesList.get(index));
			} else if(type.equalsIgnoreCase("No Show")) {
				finalClauses.put(type, clausesList.get(index));
			}
			index++;
		}
		return finalClauses;
	}
	
	public CancellationPolicy findBestCancellationPolicy(String delim,String cancellationPolicyName,String typeOfFees,
			String guestsWillIncurAFee,String chargesTypes,String cancelWithInType,String noOfDays,String roomChargeValue,String totalChargeValue,
			
			String roomClasses, String CheckInDate, String CheckOutDate, 
			HashMap<String, ArrayList<String>> roomClassWiseDates,
			HashMap<String, HashMap<String, String>> roomClassWiseRoomCharge) throws NumberFormatException, ParseException {
		
		
		ArrayList<String> typeOfFeesList = Utility.convertTokenToArrayList(typeOfFees, delim);
		ArrayList<String> percentageList = Utility.convertTokenToArrayList(guestsWillIncurAFee, delim);
		ArrayList<String> chargesTypeList = Utility.convertTokenToArrayList(chargesTypes, delim);
		ArrayList<String> cancelWithInTypeList = Utility.convertTokenToArrayList(cancelWithInType, delim);
		ArrayList<String> noOfDaysList = Utility.convertTokenToArrayList(noOfDays, delim);
		int index = 0;
		int chargeIndex = 0;
		ArrayList<Float> finalValues = new ArrayList<Float>();
		ArrayList<String> typeList = new ArrayList<String>();
		ArrayList<String> chargeTypeList = new ArrayList<String>();
		for(String type: typeOfFeesList) {
			if(type.equalsIgnoreCase("flat fee")) {
				if(cancelWithInTypeList.get(index).equalsIgnoreCase("within check-in date")) {
					finalValues.add(Float.parseFloat(percentageList.get(index)));
					typeList.add(type);
					chargeTypeList.add("");
				}else if(Integer.parseInt(noOfDaysList.get(index))==0) {
					finalValues.add(Float.parseFloat(percentageList.get(index)));
					typeList.add(type);
					chargeTypeList.add("");
				}
			} else if(type.equalsIgnoreCase("number of nights")) {
				if(cancelWithInTypeList.get(index).equalsIgnoreCase("within check-in date")) {
					
					
					finalValues.add(Float.parseFloat(calculateNoOfNightsAmount(delim, roomClasses, CheckInDate, CheckOutDate, roomClassWiseDates, roomClassWiseRoomCharge, percentageList.get(index))));
					
				//	finalValues.add(Float.parseFloat((Integer.parseInt(percentageList.get(index)) * Float.parseFloat(roomChargeValue))+""));
					typeList.add(type);
					chargeTypeList.add("");
				}else if(Integer.parseInt(noOfDaysList.get(index))==0) {
					finalValues.add(Float.parseFloat(calculateNoOfNightsAmount(delim, roomClasses, CheckInDate, CheckOutDate, roomClassWiseDates, roomClassWiseRoomCharge, percentageList.get(index))));
					
					//	finalValues.add(Float.parseFloat((Integer.parseInt(percentageList.get(index)) * Float.parseFloat(roomChargeValue))+""));
						typeList.add(type);
						chargeTypeList.add("");
				}
			} else if(type.equalsIgnoreCase("percent of stay")) {
				if(cancelWithInTypeList.get(index).equalsIgnoreCase("within check-in date")) {
					if(chargesTypeList.get(chargeIndex).equalsIgnoreCase("total charges")) {
						finalValues.add(Float.parseFloat(Utility.getPercentage(percentageList.get(index), totalChargeValue)));
						typeList.add(type);
					}else if(chargesTypeList.get(chargeIndex).equalsIgnoreCase("room charges")) {
						finalValues.add(Float.parseFloat((Utility.getPercentage(percentageList.get(index), roomChargeValue))));
						typeList.add(type);
					}
					chargeTypeList.add(chargesTypeList.get(chargeIndex));
					chargeIndex++;
				}else if(Integer.parseInt(noOfDaysList.get(index))==0) {
					if(chargesTypeList.get(chargeIndex).equalsIgnoreCase("total charges")) {
						finalValues.add(Float.parseFloat(Utility.getPercentage(percentageList.get(index), totalChargeValue)));
						typeList.add(type);
					}else if(chargesTypeList.get(chargeIndex).equalsIgnoreCase("room charges")) {
						finalValues.add(Float.parseFloat((Utility.getPercentage(percentageList.get(index), roomChargeValue))));
						typeList.add(type);
					}
					chargeTypeList.add(chargesTypeList.get(chargeIndex));
					chargeIndex++;
				}
			}
			
			index++;
		}
		CancellationPolicy bestClause = null;
		if(finalValues.size()>0) {
			 Float max = Collections.max(finalValues);  
			 
			bestClause = new CancellationPolicy(
					cancellationPolicyName, 
					typeList.get(finalValues.indexOf(max)), 
					percentageList.get(finalValues.indexOf(max)), 
					chargesTypeList.get(finalValues.indexOf(max)), 
					noOfDaysList.get(finalValues.indexOf(max)),
					cancelWithInTypeList.get(finalValues.indexOf(max)),
					max+""
					);
			
		}else { // other than within check-in date which is not possible
			
			bestClause = null;
		}
		
		
		return bestClause;
	}
	


	
	
		public String verifyListOfPoliciesExist(WebDriver driver, String policyNameslist, String requiredPolicyNames) {
			String policyNamesExist = null;
			StringTokenizer token = new StringTokenizer(requiredPolicyNames, Utility.DELIM);
			boolean first = true;
			while (token.hasMoreTokens()) {
				String policyName = token.nextToken();
				if (!policyNameslist.contains(policyName)) {
					return null;
				}
			}
			return requiredPolicyNames;
			
		}

	public int getNoOfClauses(WebDriver driver) {
		String path = "(//span[contains(@class,'sr-no')])";
		return driver.findElements(By.xpath(path)).size();
	}
	
	public String getSelectedTypeOfFees (WebDriver driver,String returnDelim, int noOfClauses,ArrayList<String> test_steps) {
		String foundValue = "";
		for(int i=1; i<=noOfClauses; i++) {
			if(i!=1) {
				foundValue +=returnDelim;
			}
			String selectPath = "//span[contains(@class,'sr-no') and contains(text(),'"+i+"')]/../..//span[contains(@class,'inrd-select fee-type-select')]//div[@class='ant-select-selection-selected-value']";
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
			foundValue += driver.findElement(By.xpath(selectPath)).getText();
		}
		return foundValue;
	}
	
	public String getGuestWillIncurAFee (WebDriver driver,String returnDelim, int noOfClauses,ArrayList<String> test_steps) {
		String foundValue = "";
		for(int i=1; i<=noOfClauses; i++) {
			if(i!=1) {
				foundValue +=returnDelim;
			}
			String percentPath = "(//span[contains(@class,'sr-no') and contains(text(),'"+i+"')]/../..//input)[1]";
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(percentPath)), driver);
			 foundValue += driver.findElement(By.xpath(percentPath)).getAttribute("value");
		}
		return foundValue;
	}
	
	public String getSelectedChargeType (WebDriver driver,String delim, String TypeOfFees, ArrayList<String> test_steps) {
		ArrayList<String> typeOfFees = Utility.convertTokenToArrayList(TypeOfFees, delim);
		String foundType = "";
		int percentOfStayCount = 0;
		for(int clause=0;clause<typeOfFees.size();clause++) {
			int clauseNo = clause + 1;
			if(typeOfFees.get(clause).equalsIgnoreCase("percent of stay")) {
		
				if(percentOfStayCount!=0) {
					foundType += delim;
				}
				
				String selectPath = "//span[contains(@class,'sr-no') and contains(text(),'"+clauseNo+"')]/../..//div[contains(text(),'Guests will incur a fee of')]/span[2]/div//div[@class='ant-select-selection-selected-value']";
				Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
				 foundType += driver.findElement(By.xpath(selectPath)).getText()+"";
				 percentOfStayCount++;
			}
		}
		return foundType;
	}
	
	public String getNoOfDays (WebDriver driver,String returnDelim, int noOfClauses,ArrayList<String> test_steps) {
		String foundValue = "";
		for(int i=1; i<=noOfClauses; i++) {
			if(i!=1) {
				foundValue +=returnDelim;
			}
			String path = "//span[contains(@class,'sr-no') and contains(text(),'"+i+"')]/../..//input[@name='numberOfDays']";
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
			 foundValue += driver.findElement(By.xpath(path)).getAttribute("value");
		}
		return foundValue;
	}
	
	public String getSelectedCancelType (WebDriver driver,String returnDelim, int noOfClauses,ArrayList<String> test_steps) {
		String foundType = "";
		for(int i=1; i<=noOfClauses; i++) {
			if(i!=1) {
				foundType +=returnDelim;
			}
			String selectPath = "//span[contains(@class,'sr-no') and contains(text(),'"+i+"')]/../..//div[contains(text(),'Guests will incur a fee of')]/div/span[2]/div[1]/div//div[@class='ant-select-selection-selected-value']";
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(selectPath)), driver);
			 foundType += driver.findElement(By.xpath(selectPath)).getText();
		}
		return foundType;
	}

	
	public String getPolicyDescriptionFromPolicy(WebDriver driver, ArrayList<String> test_steps, String policyName) {
		WebElements_Policies elements = new WebElements_Policies(driver);
		String policyDesc = null;	 
		policyDesc = elements.policyDescription.getText();
		test_steps.add("Captured policy description is <b>" + policyDesc+ "</b> for policy <b>"+policyName+"</b>");
		return policyDesc;
	}
	
	public String getSelectedType(WebDriver driver, String policyType) {
		WebElements_Policies elements = new WebElements_Policies(driver);
		String type = null;		
		if(!(policyType.equalsIgnoreCase("Cancellation"))) {
			type=elements.selectType.getAttribute("title");
		}
		return type;
	}
	
	public String getCancellationSelectedType(WebDriver driver, String policyType) {
		WebElements_Policies elements = new WebElements_Policies(driver);
		String type = null;		
		type=elements.selectType.getAttribute("title");
		return type;
	}
	
	public String getChargesType(WebDriver driver,  String policyType) {
		WebElements_Policies elements = new WebElements_Policies(driver);	 
		String charges = null;		
		if(!(policyType.equalsIgnoreCase("Cancellation"))){
			 charges=elements.chargesType.getAttribute("title");
		}
		return charges;
	}

	
	public String gettCancellationChargesType(WebDriver driver,  String policyType) {
		WebElements_Policies elements = new WebElements_Policies(driver);	 
		String charges = null;		
			 charges=elements.chargesType.getAttribute("title");
			return charges;
	}
	
	public String gettCancellationDays(WebDriver driver,  String policyType) {
		WebElements_Policies elements = new WebElements_Policies(driver);	 
		String charges = null;		
			 charges=elements.chargesType.getAttribute("title");
			return charges;
	}
	
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getPercentageFieldLength,getSelectedType,getChargesType> 
	 * ' Description: < Get percentage, get chargestype and get selected type> 
	 * ' Input parameters: < WebDriver driver,int clauseNo, String policyType> 
	 * ' Return value:<String>> 
	 * ' Created By: <Gangotri Sikheria> ' 
	 * ' Created On:c<MM/dd/yyyy> <08/25/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	//Added By Adhnan 09/03/2020
		// this will return the policy Names  from the given policyNames which are present

	
	public String getPercentageFieldLength(WebDriver driver, String policyType) {
		WebElements_Policies elements = new WebElements_Policies(driver);
		String valueIs = null;	 
		if(!(policyType.equalsIgnoreCase("Cancellation"))){
			 valueIs = elements.chargesValue.getAttribute("value");			 
		}
		String returnString = "";  
		for (int i = 0; i < valueIs.length(); i++) {
			if (valueIs.charAt(i)>47&&valueIs.charAt(i)<58) {
				returnString = returnString+valueIs.charAt(i);
			}
		}
		return returnString;
	}
	public String getCancellationAttrValue(WebDriver driver, String policyType) {
		WebElements_Policies elements = new WebElements_Policies(driver);
		String valueIs = null;	 
		
			 valueIs = elements.PercentagechargesValue.getAttribute("value");			 
		
		String returnString = "";  
		for (int i = 0; i < valueIs.length(); i++) {
			if (valueIs.charAt(i)>47&&valueIs.charAt(i)<58) {
				returnString = returnString+valueIs.charAt(i);
			}
		}
		return returnString;
	}



	public String getPolicyNameFromPolicy(WebDriver driver, ArrayList<String> test_steps) {
		WebElements_Policies elements = new WebElements_Policies(driver);
		String policyName = null;	 
		policyName = elements.ENTER_POLICY_NAME.getAttribute("value");
		test_steps.add("Captured policy name as <b>" + policyName+ "</b> ");
		return policyName;
	}

	public HashMap<String, ArrayList<String>> getAllPoliciesDetails(WebDriver driver, ArrayList<String> test_steps, String policyType,
			ArrayList<String> policyNames) throws Exception {
		HashMap<String, ArrayList<String>> allPoliciesDetails = new HashMap<>();
		ArrayList<String> policyNamesGet = new ArrayList<>();
		ArrayList<String> policyAttrs = new ArrayList<>();
		ArrayList<String> policyAttrValues = new ArrayList<>();
		ArrayList<String> policyDescs = new ArrayList<>();		
		for (String policyName : policyNames) {
			if (Utility.validateString(policyName)) {
				clickEditIcon(driver, policyType, policyName, test_steps);
				policyNamesGet.add(getPolicyNameFromPolicy(driver, test_steps));
				policyAttrs.add(getSelectedType(driver, policyType));
				test_steps.add("Captured policy attribute as : <b>" + getSelectedType(driver, policyType)+ "</b> for policy <b>"+policyName+"</b>");
				policyAttrValues.add(getPercentageFieldLength(driver, policyType));
				test_steps.add("Captured policy attribute value as : <b>" + getPercentageFieldLength(driver, policyType)+ "</b>"
						+ " for policy <b>"+policyName+"</b>");
				policyDescs.add(getPolicyDescriptionFromPolicy(driver, test_steps, policyName));
				closePolicyPopup(driver, test_steps);				
			}
		}
		allPoliciesDetails.put("Names", policyNamesGet);
		allPoliciesDetails.put("Attributes", policyAttrs);
		allPoliciesDetails.put("AttributeValues", policyAttrValues);
		allPoliciesDetails.put("Descriptions", policyDescs);
		return allPoliciesDetails;		
	}
	

	// Added By Adhnan 09/03/2020
	// this will return the policy Names from the given policyNames which are
	// present
	public String[] verifyPoliciesExist(WebDriver driver,String policyTypesList, String policyNamesList, String requiredPolicyNames,
			String policyTypes) {
		String foundPolicyNames = null;
		String foundPolicyTypes = null;
		String policyName = null;
		String[] policyTypesAndNames = new String[2];
		String[] listOfPolicyTypes = policyTypes.split(Utility.DELIM);
		String[] listOfPolicyNames = requiredPolicyNames.split(Utility.DELIM);
		assertEquals(listOfPolicyTypes.length, listOfPolicyNames.length,
				"Failed : policy Types and policy Names list size missmatched");
		for (int i = 0; i < listOfPolicyNames.length; i++) {
			policyName = listOfPolicyNames[i];
			policiesLogger.info("Policies Type : " + listOfPolicyTypes[i]);
			policiesLogger.info("Policies List : " + listOfPolicyNames[i]);
				
			if (policyNamesList.contains(policyName)) {
				if (foundPolicyNames == null) {
					foundPolicyNames = policyName;
				} else {
					foundPolicyNames = foundPolicyNames + Utility.DELIM + policyName;
				}
				if (foundPolicyTypes == null) {
					foundPolicyTypes = listOfPolicyTypes[i];
				} else {
					foundPolicyTypes = foundPolicyTypes + Utility.DELIM + listOfPolicyTypes[i];
				}

			}
		}
		if(foundPolicyNames==null){
			assertTrue(false, "Failed: Required policies not found");
		}else{
		policyTypesAndNames[0] = foundPolicyTypes;
		policyTypesAndNames[1] = foundPolicyNames;
		}
		return policyTypesAndNames;

	}

	public HashMap<String, ArrayList<String>> getAllPoliciesDetails(WebDriver driver, ArrayList<String> test_steps, String policyType,
			String policyName) throws Exception {
		HashMap<String, ArrayList<String>> allPoliciesDetails = new HashMap<>();
		ArrayList<String> policyNamesGet = new ArrayList<>();
		ArrayList<String> policyAttrs = new ArrayList<>();
		ArrayList<String> policyAttrValues = new ArrayList<>();
		ArrayList<String> policyDescs = new ArrayList<>();		
		if (Utility.validateString(policyName)) {
			clickEditIcon(driver, policyType, policyName, test_steps);
			policyNamesGet.add(getPolicyNameFromPolicy(driver, test_steps));
			policyAttrs.add(getSelectedType(driver, policyType));
			test_steps.add("Captured policy attribute as : <b>" + getSelectedType(driver, policyType)+ "</b> for policy <b>"+policyName+"</b>");
			policyAttrValues.add(getPercentageFieldLength(driver, policyType));
			test_steps.add("Captured policy attribute value as : <b>" + getPercentageFieldLength(driver, policyType)+ "</b>"
					+ " for policy <b>"+policyName+"</b>");
			policyDescs.add(getPolicyDescriptionFromPolicy(driver, test_steps, policyName));
			closePolicyPopup(driver, test_steps);				
		}

		allPoliciesDetails.put("Names", policyNamesGet);
		allPoliciesDetails.put("Attributes", policyAttrs);
		allPoliciesDetails.put("AttributeValues", policyAttrValues);
		allPoliciesDetails.put("Descriptions", policyDescs);
		return allPoliciesDetails;		
	}
	
	public HashMap<String, String> getpoliciesData(WebDriver driver, ArrayList<String> test_steps, String policyType, String policyName) throws InterruptedException{
		HashMap<String,String> getData= new HashMap<String,String>();
		String type=null, attrValue=null,chargesType=null;
		clickEditIcon(driver, policyType, policyName, test_steps);
		if(policyType.equalsIgnoreCase("Deposit")) {
			type=getSelectedType(driver,  policyType);
			test_steps.add("Selected Type: <b>" + type+ "</b>");
			attrValue=getPercentageFieldLength(driver,policyType);
			test_steps.add("Must Pay: <b>" + attrValue+ "</b>");
			
			
		}else if(policyType.equalsIgnoreCase("Check-in")) {
			type=getSelectedType(driver,  "");
			test_steps.add("Check-In Selected Type: <b>" + type+ "</b>");				
			attrValue=getPercentageFieldLength(driver,"");
			test_steps.add("Upon Check In: <b>" + attrValue+ "</b>");
			chargesType=getChargesType(driver,  "");
			test_steps.add("Check-In Applied On: <b>" + chargesType+ "</b>");
			
		}
		else if(policyType.equalsIgnoreCase("Cancellation")) {
			type=getCancellationSelectedType(driver,  "");
			test_steps.add("Cancellation Selected Type: <b>" + type+ "</b>");				
			attrValue=getCancellationAttrValue(driver,"");
			test_steps.add("Guests will incur a fee of: <b>" + attrValue+ "</b>");
			chargesType=gettCancellationChargesType(driver,  "");
			test_steps.add("Cancellation Applied On: <b>" + chargesType+ "</b>");
		}
		getData.put("Type", type);
		getData.put("AttrValue", attrValue);
		getData.put("Charges", chargesType);
		closePolicyPopup(driver, test_steps);
		return getData;	

	}
	
	public boolean isCancellationPolicyApplicable(String noOfDays, String checkInDate) throws NumberFormatException, ParseException {
		
		int diff = Integer.parseInt(Utility.differenceBetweenDates(Utility.getCurrentDate("dd/MM/yyyy"), checkInDate));
		if(diff<=Integer.parseInt(noOfDays)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public String calculateNoOfNightsAmount(String delim, String roomClasses, String CheckInDate, String CheckOutDate, 
			HashMap<String, ArrayList<String>> roomClassWiseDates,
			HashMap<String, HashMap<String, String>> roomClassWiseRoomCharge, 
			String cancellationNoOfNights) throws NumberFormatException, ParseException {
		
		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClasses, delim);
		ArrayList<String> checkInDateList = Utility.convertTokenToArrayList(CheckInDate, delim);
		ArrayList<String> checkOutDateList = Utility.convertTokenToArrayList(CheckOutDate, delim);
		int noOfNights = Integer.parseInt(cancellationNoOfNights);
		
		
		int index = 0;
		float finalAmount = 0;
		
		for(String roomClass : roomClassList) {
			int stayDiff = Integer.parseInt(Utility.differenceBetweenDates(checkInDateList.get(index), checkOutDateList.get(index)));
			
			if(stayDiff<=noOfNights) {
				for(int i=0; i<stayDiff; i++) {
					String folioDate = roomClassWiseDates.get(roomClass).get(i);
					finalAmount += Float.parseFloat(roomClassWiseRoomCharge.get(roomClass).get(folioDate));
				}
			}else {
				for(int i=0; i<noOfNights; i++) {
					String folioDate = roomClassWiseDates.get(roomClass).get(i);
					finalAmount += Float.parseFloat(roomClassWiseRoomCharge.get(roomClass).get(folioDate));
				}
			}
			
			index++;
		}
		
		return finalAmount+"";
	}


	
	/*public HashMap<String, String> getpoliciesData(WebDriver driver, ArrayList<String> test_steps, String policyType, String policyName) throws InterruptedException{
		HashMap<String,String> getData= new HashMap<String,String>();
		String type=null, attrValue=null,chargesType=null;
		clickEditIcon(driver, policyType, policyName, test_steps);
		if(policyType.equalsIgnoreCase("Deposit")) {
			type=getSelectedType(driver,  policyType);
			test_steps.add("Selected Type: <b>" + type+ "</b>");
			attrValue=getPercentageFieldLength(driver,policyType);
			test_steps.add("Must Pay: <b>" + attrValue+ "</b>");
			
			
		}else if(policyType.equalsIgnoreCase("Check-in")) {
			type=getSelectedType(driver,  "");
			test_steps.add("Check-In Selected Type: <b>" + type+ "</b>");				
			attrValue=getPercentageFieldLength(driver,"");
			test_steps.add("Upon Check In: <b>" + attrValue+ "</b>");
			chargesType=getChargesType(driver,  "");
			test_steps.add("Check-In Applied On: <b>" + chargesType+ "</b>");
			
		}
		getData.put("Type", type);
		getData.put("AttrValue", attrValue);
		getData.put("Charges", chargesType);
		closePolicyPopup(driver, test_steps);
		return getData;	

	}*/



}

