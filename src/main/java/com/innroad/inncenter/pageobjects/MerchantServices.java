package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_MerchantServices;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Inventory;
import com.innroad.inncenter.webelements.Elements_MerchantService;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;

public class MerchantServices {
	private static Logger logger = Logger.getLogger("MerchantServices");
	
	public void selectFirstItem(WebDriver driver) {
		String tablePath = "//*[@id='MainContent_dgMerchantService']/tbody/tr";
		
		int size = driver.findElements(By.xpath(tablePath)).size();
		if(size>2) {
			assertTrue(true);
			driver.findElement(By.xpath(tablePath+"[2]/td/a")).click();
			Wait.explicit_wait_xpath("//*[@id='tdTitle']/font[.='Merchant Account']", driver);
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath("//*[@id='tdTitle']/font[.='Merchant Account']")), driver);
			boolean flag=driver.findElement(By.xpath("//*[@id='tdTitle']/font[.='Merchant Account']")).isDisplayed();
			assertTrue(flag,"Failed: Table Link not Clicked");
		}else {
			assertFalse(true);
		}
	}
	
	public void checkRequireCVV(WebDriver driver,boolean isChecked) throws InterruptedException {
		String path = "//*[@id='MainContent_chkRequireCVV']";
		WebElement inputCVV = driver.findElement(By.xpath(path));
		WebElement saveBtn = driver.findElement(By.xpath("//*[@id='MainContent_btnSave']"));
		
		Utility.ScrollToElement(inputCVV, driver);
		if(isChecked) {
			if(!inputCVV.isSelected()) {
				inputCVV.click();
				Wait.explicit_wait_elementToBeClickable(saveBtn, driver);
				saveBtn.click();
			}
		}else {
			if(inputCVV.isSelected()) {
				inputCVV.click();
				Wait.explicit_wait_elementToBeClickable(saveBtn, driver);
				saveBtn.click();
			}
		}
		assertEquals(isChecked,driver.findElement(By.xpath(path)).isSelected(),"Failed: CVV Require");
		
	}
	
	

	// TODO Auto-generated method stub
	public void changeStatusOfMerchant(WebDriver driver) throws Exception
	{
String tablePath = "//table[@id='MainContent_dgMerchantService']//tr[2]";
		
int  merchantAccount = driver.findElements(By.xpath(tablePath)).size();
		if(merchantAccount>=1) {
			assertTrue(true);
			driver.findElement(By.xpath("//table[@id='MainContent_dgMerchantService']//tr[2]/td/a")).click();
			
			Wait.explicit_wait_xpath("//font[contains(text(),'Merchant Account')]", driver);
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath("//font[contains(text(),'Merchant Account')]")), driver);
			
			Select status = new Select(driver.findElement(By.xpath("//select[contains(@id,'MainContent_drpStatus')]")));
			status.selectByVisibleText("InActive");
			
			driver.findElement(By.xpath("//input[contains(@id,'MainContent_btnDone')]")).click();
			
		
			
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath("//span[@id='MainContent_lblMessage']")), driver);
			boolean flag=driver.findElement(By.xpath("//span[@id='MainContent_lblMessage']")).isDisplayed();
			assertTrue(flag,"Failed:to inactive merchant partner");
		}
	}
	
	public void createNewMerchantService(WebDriver driver,ArrayList<String> testSteps,
			String accountName,String accountNumber,String accountDesc,String accountStatus,
			String isCardPresent, String isCardNotPresent, String isEcommerce, String isRequireCVVForAll,
			String gateway, String mode,
			String accountID, String subAccountId, String merchantPin,
			String storeId, String url, String tokenId,
			String username, String password, String restAPIKey, String hituserId, String hitKey,
			String transactionKeyCode, String associateProperties) throws InterruptedException {
		
		Elements_MerchantService ele = new Elements_MerchantService(driver);
//		Wait.WaitForElement(driver, OR_MerchantServices.MS_CREATE_NEW_BUTTON);
		Wait.waitForElementToBeVisibile(By.id(OR_MerchantServices.MS_CREATE_NEW_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.id(OR_MerchantServices.MS_CREATE_NEW_BUTTON), driver);
		ele.MS_CREATE_NEW_BUTTON.click();
		testSteps.add("Clicked New Merchant Account Button : ");
		logger.info("Clicked New Merchant Account Button : ");
		
		if(Utility.validateString(accountNumber)) {
			ele.MS_ACCOUNT_NUMBER.sendKeys(accountNumber);
			testSteps.add("Entered Account Number : " + accountNumber);
			logger.info("Entered Account Number : " + accountNumber);
		}
		if(Utility.validateString(accountDesc)) {
			ele.MS_ACCOUNT_DESCRIPTION.sendKeys(accountDesc);
			testSteps.add("Entered Description : " + accountDesc);
			logger.info("Entered Description : " + accountDesc);
		}
		new Select(ele.MS_ACCOUNT_STATUS).selectByVisibleText(accountStatus);
		testSteps.add("Selected STATUS : " + accountStatus);
		logger.info("Selected STATUS : " + accountStatus);
		if(isCardPresent.equalsIgnoreCase("yes")) {
			//Wait.wait10Second();
			ele.MS_ACCOUNT_TRANSACTION_TYPE_CARD_PRESENT.click();
			testSteps.add("Card Present Checked");
			logger.info("Card Present Checked");
		}else {
			testSteps.add("Card Present not Checked");
			logger.info("Card Present not Checked");
		}
		if(isCardNotPresent.equalsIgnoreCase("yes")) {
			Wait.wait10Second();
			ele.MS_ACCOUNT_TRANSACTION_TYPE_CARD_NOT_PRESENT.click();
			testSteps.add("Card not Present Checked");
			logger.info("Card not Present Checked");
		}else {
			testSteps.add("Card not Present not Checked");
			logger.info("Card not Present not Checked");
		}
		
		if(isEcommerce.equalsIgnoreCase("yes")) {
			Wait.wait10Second();
			ele.MS_ACCOUNT_TRANSACTION_TYPE_E_COMMERCE.click();
			testSteps.add("ECommerce Checked");
			logger.info("ECommerce Checked");
		}else {
			testSteps.add("ECommerce Checked");
			logger.info("ECommerce Checked");
		}
		
		if(isRequireCVVForAll.equalsIgnoreCase("yes")) {
			Wait.wait10Second();
			ele.MS_ACCOUNT_TRANSACTION_TYPE_REQUIRE_CVV.click();
			testSteps.add("Require CVV For ALL Checked");
			logger.info("Require CVV For ALL Checked");
		}else {
			testSteps.add("Require CVV For ALL not Checked");
			logger.info("Require CVV For ALL not Checked");
		}
		
		new Select(ele.MS_ACCOUNT_GATEWAY).selectByVisibleText(gateway);
		testSteps.add("Selected GATEWAY : " + gateway);
		logger.info("Selected GATEWAY : " + gateway);
		
		new Select(ele.MS_ACCOUNT_GATEWAY_MODE).selectByVisibleText(mode);
		testSteps.add("Selected Mode : " + mode);
		logger.info("Selected Mode : " + mode);
		
		Wait.waitForElementToBeVisibile(By.id(OR_MerchantServices.MS_ACCOUNT_NAME), driver);
		Wait.waitForElementToBeClickable(By.id(OR_MerchantServices.MS_ACCOUNT_NAME), driver);
		ele.MS_ACCOUNT_NAME.clear();
		Wait.wait5Second();
		ele.MS_ACCOUNT_NAME.sendKeys(accountName);
		Wait.wait10Second();
		testSteps.add("Entered Account Name : " + accountName);
		logger.info("Entered Account Name : " + accountName);
		
		if(gateway.equalsIgnoreCase("Merchant Partners")) {
			//String accountID, String subAccountId, String merchantPin,
			ele.MS_ACCOUNT_ACCOUNT_ID.clear();
			ele.MS_ACCOUNT_ACCOUNT_ID.sendKeys(accountID);
			testSteps.add("Entered MS_ACCOUNT_ACCOUNT_ID : " + accountID);
			logger.info("Entered MS_ACCOUNT_ACCOUNT_ID : " + accountID);
			if(Utility.validateString(subAccountId))	{
				ele.MS_ACCOUNT_SUB_ACCOUNT_ID.clear();
				ele.MS_ACCOUNT_SUB_ACCOUNT_ID.sendKeys(subAccountId);
				testSteps.add("Entered MS_ACCOUNT_SUB_ACCOUNT_ID : " + subAccountId);
				logger.info("Entered MS_ACCOUNT_SUB_ACCOUNT_ID : " + subAccountId);	}
			if(Utility.validateString(merchantPin)) {
			ele.MS_ACCOUNT_MERCHANT_PIN.clear();
			ele.MS_ACCOUNT_MERCHANT_PIN.sendKeys(merchantPin);
			testSteps.add("Entered MS_ACCOUNT_MERCHANT_PIN : " + merchantPin);
			logger.info("Entered MS_ACCOUNT_MERCHANT_PIN : " + merchantPin);}
			
		}else if(gateway.equalsIgnoreCase("Moneris")) {
			//String storeId, String url, String tokenId,
			ele.MS_ACCOUNT_STORE_ID.sendKeys(storeId);
			testSteps.add("Entered MS_ACCOUNT_STORE_ID : " + storeId);
			logger.info("Entered MS_ACCOUNT_STORE_ID : " + storeId);
			
			ele.MS_ACCOUNT_URL.sendKeys(url);
			testSteps.add("Entered MS_ACCOUNT_URL : " + url);
			logger.info("Entered MS_ACCOUNT_URL : " + url);
			
			ele.MS_ACCOUNT_TOKEN_ID.sendKeys(tokenId);
			testSteps.add("Entered MS_ACCOUNT_TOKEN_ID : " + tokenId);
			logger.info("Entered MS_ACCOUNT_TOKEN_ID : " + tokenId);
			
		}else if(gateway.equalsIgnoreCase("Payment Express")) {
			//String username, String password, String restAPIKey, String hituserId, String hitKey,
			ele.MS_ACCOUNT_USERNAME.sendKeys(username);
			testSteps.add("Entered MS_ACCOUNT_USERNAME : " + username);
			logger.info("Entered MS_ACCOUNT_USERNAME : " + username);
			
			ele.MS_ACCOUNT_PASSWORD.sendKeys(password);
			testSteps.add("Entered MS_ACCOUNT_PASSWORD : " + password);
			logger.info("Entered MS_ACCOUNT_PASSWORD : " + password);
			
			if(Utility.validateString(restAPIKey))
			{
				ele.MS_ACCOUNT_REST_API_KEY.sendKeys(restAPIKey);
				testSteps.add("Entered MS_ACCOUNT_REST_API_KEY : " + restAPIKey);
				logger.info("Entered MS_ACCOUNT_REST_API_KEY : " + restAPIKey);
			}
			ele.MS_ACCOUNT_HIT_USERID.sendKeys(hituserId);
			testSteps.add("Entered MS_ACCOUNT_HIT_USERID : " + hituserId);
			logger.info("Entered MS_ACCOUNT_HIT_USERID : " + hituserId);
			
			ele.MS_ACCOUNT_HIT_KEY.sendKeys(hitKey);
			testSteps.add("Entered MS_ACCOUNT_HIT_KEY : " + hitKey);
			logger.info("Entered MS_ACCOUNT_HIT_KEY : " + hitKey);
			
			
		}else if(gateway.equalsIgnoreCase("PayMover")) {
			ele.MS_ACCOUNT_TRANSACTION_KEY_CODE.sendKeys(transactionKeyCode);
			testSteps.add("Entered ACCOUNT_TRANSACTION_KEY_CODE : " + transactionKeyCode);
			logger.info("Entered ACCOUNT_TRANSACTION_KEY_CODE : " + transactionKeyCode);
		}
		if(Utility.validateString(associateProperties)) {
//			Wait.WaitForElement(driver, OR_MerchantServices.MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON);
			Wait.waitForElementToBeVisibile(By.id(OR_MerchantServices.MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON), driver);
			Wait.waitForElementToBeClickable(By.id(OR_MerchantServices.MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON), driver);

			Utility.ScrollToElement_NoWait(ele.MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON, driver);
			Wait.wait1Second();
			ele.MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON.click();
			testSteps.add("Associalte Properties Button Clicked");
			Wait.waitForFrame(By.id("dialog-body0"), driver);
			Wait.waitForFrame(By.id("frmWaitHost"), driver);
//			driver.switchTo().frame(driver.findElement(By.id("dialog-body0")));
//			driver.switchTo().frame(driver.findElement(By.id("frmWaitHost")));

			logger.info(" Switched to Frame ");
			String pathRoom = "//*[@id='lstRooms']//option[contains(text(),'" + associateProperties + "')]";
			WebElement seasonRoom = driver.findElement(By.xpath(pathRoom));
			Wait.explicit_wait_visibilityof_webelement(seasonRoom, driver);
			Utility.ScrollToElement(seasonRoom, driver);
			seasonRoom.click();
			Elements_Inventory rate = new Elements_Inventory(driver);
			new Select(rate.SelectAssociateRoomClass).selectByVisibleText(associateProperties);
			rate.PickerButton.click();
			Wait.explicit_wait_xpath(OR.doneButton, driver);
			rate.doneButton.click();
			testSteps.add("Select  associateProperties : " + associateProperties + " and Click Done");
			logger.info("Select  associateProperties : " + associateProperties + " and Click Done");

			driver.switchTo().defaultContent();
			
		}
			save_Done(driver);
	}
	
	public ArrayList<String> save_Done(WebDriver driver) throws InterruptedException {

		Elements_MerchantService rate = new Elements_MerchantService(driver);
		ArrayList<String> testSteps = new ArrayList<>();
//		Wait.WaitForElement(driver, OR_MerchantServices.MS_ACCOUNT_SAVE_BUTTON);
		Wait.waitForElementToBeVisibile(By.id(OR_MerchantServices.MS_ACCOUNT_SAVE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.id(OR_MerchantServices.MS_ACCOUNT_SAVE_BUTTON), driver);

		rate.MS_ACCOUNT_SAVE_BUTTON.click();
		logger.info(" Clicked on Save Button ");
		testSteps.add(" Clicked on Save Button ");
		
//		Wait.WaitForElement(driver, OR_MerchantServices.MS_ACCOUNT_DONE_BUTTON);
		Wait.waitForElementToBeVisibile(By.id(OR_MerchantServices.MS_ACCOUNT_DONE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.id(OR_MerchantServices.MS_ACCOUNT_DONE_BUTTON), driver);

		rate.MS_ACCOUNT_DONE_BUTTON.click();
		logger.info(" Clicked on Done Button ");
		testSteps.add(" Clicked on Done Button ");

		
		return testSteps;

	}
	
	//Added By Riddhi - navigate to Merchant Services Page under Setup
	public void navigatetoMerchantServices(WebDriver driver, ArrayList<String> test_steps) throws Exception
	{
		test_steps.add("==========Navigate to Merchant Services=========="); 
		logger.info("==========Navigate to Merchant Services==========");
	  	
		Wait.WaitForElement(driver, OR_MerchantServices.MS_SUB_TITLE_LINK_FRM_SETUP);
		driver.findElement(By.xpath(OR_MerchantServices.MS_SUB_TITLE_LINK_FRM_SETUP)).click();
		Wait.wait10Second();
	}
	//Added By Riddhi - Click on New Merchant Account Button
	public void clickOnNewMerchantAccount(WebDriver driver, ArrayList<String> test_steps) throws Exception
	{
		Elements_MerchantService eleMerchant = new Elements_MerchantService(driver);
	
		test_steps.add("==========Click on New Merchant Account Button=========="); 
		logger.info("==========Click on New Merchant Account Button==========");
	  	
		Wait.waitForElementToBeVisibile(By.id(OR_MerchantServices.MS_CREATE_NEW_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.id(OR_MerchantServices.MS_CREATE_NEW_BUTTON), driver);
		eleMerchant.MS_CREATE_NEW_BUTTON.click();
		
	}
	//Added By Riddhi - Delete Active Gateway from Setup >> Merchant Services, if it exists 
	public void deleteActivePaymentGatewayifExists(WebDriver driver, ArrayList<String> test_steps) throws Exception
	{
		Elements_MerchantService eleMerchant = new Elements_MerchantService(driver);
		test_steps.add("==========Checking Active Payment Gateways=========="); 
		logger.info("==========Checking Active Payment Gateways==========");
	  	
		String path = "(//table[@class='dgText']//tbody//tr//td[1])[2]";
		//if(driver.findElement(By.xpath(OR_MerchantServices.MS_ACTIVE_GATEWAYCHECKBOX)).isDisplayed())
		if(Wait.isElementDisplayed(driver, eleMerchant.MS_ACTIVE_GATEWAYCHECKBOX))
		{
			test_steps.add("==========Clicking on Delete Checkbox For Active Gateway=========="); 
			logger.info("==========Clicking on Delete Checkbox For Active Gateway=========="); 
		  	
			Wait.waitForElementToBeVisibile(By.xpath(OR_MerchantServices.MS_ACTIVE_GATEWAYCHECKBOX), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_MerchantServices.MS_ACTIVE_GATEWAYCHECKBOX), driver);
			eleMerchant.MS_ACTIVE_GATEWAYCHECKBOX.click();
			
			test_steps.add("==========Clicking on Delete Button for deleting Active Payment Gateway=========="); 
			logger.info("==========Clicking on Delete Button for deleting Active Payment Gateway==========");
		  	
			Wait.waitForElementToBeVisibile(By.id(OR_MerchantServices.MS_DELETE_BTN), driver);
			Wait.waitForElementToBeClickable(By.id(OR_MerchantServices.MS_DELETE_BTN), driver);
			eleMerchant.MS_DELETE_BTN.click();
		}
		else
		{
			test_steps.add("No Active Payment Gateway Exists");
			logger.info("No Active Payment Gateway Exists");
		}
	}
	
	//Add Device after adding PE Gateway
	public void addDevicePEGateway(WebDriver driver, ArrayList<String> test_steps) throws Exception
	{
		Navigation navigation = new Navigation();
		ReservationV2 resV2 = new ReservationV2();

		navigation.Setup(driver);
		navigation.clickOnProperties(driver);
		navigation.clickOnSpecificProperty(driver);	
		navigation.clickOnPropertiesInterfaceTab(driver);
	}
	
}
