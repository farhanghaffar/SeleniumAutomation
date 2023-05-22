package com.innroad.inncenter.webelements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_MerchantServices;
import com.innroad.inncenter.properties.OR_ReservationV2;

public class Elements_MerchantService {
	WebDriver driver;

	public Elements_MerchantService(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);

	}
	
	@FindBy(id = OR_MerchantServices.MS_CREATE_NEW_BUTTON)
	public WebElement MS_CREATE_NEW_BUTTON;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_NAME)
	public WebElement MS_ACCOUNT_NAME;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_NUMBER)
	public WebElement MS_ACCOUNT_NUMBER;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_DESCRIPTION)
	public WebElement MS_ACCOUNT_DESCRIPTION;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_STATUS)
	public WebElement MS_ACCOUNT_STATUS;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_TRANSACTION_TYPE_CARD_PRESENT)
	public WebElement MS_ACCOUNT_TRANSACTION_TYPE_CARD_PRESENT;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_TRANSACTION_TYPE_CARD_NOT_PRESENT)
	public WebElement MS_ACCOUNT_TRANSACTION_TYPE_CARD_NOT_PRESENT;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_TRANSACTION_TYPE_E_COMMERCE)
	public WebElement MS_ACCOUNT_TRANSACTION_TYPE_E_COMMERCE;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_TRANSACTION_TYPE_REQUIRE_CVV)
	public WebElement MS_ACCOUNT_TRANSACTION_TYPE_REQUIRE_CVV;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_GATEWAY)
	public WebElement MS_ACCOUNT_GATEWAY;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_GATEWAY_MODE)
	public WebElement MS_ACCOUNT_GATEWAY_MODE;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_TRANSACTION_KEY_CODE)
	public WebElement MS_ACCOUNT_TRANSACTION_KEY_CODE;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON)
	public WebElement MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_SAVE_BUTTON)
	public WebElement MS_ACCOUNT_SAVE_BUTTON;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_DONE_BUTTON)
	public WebElement MS_ACCOUNT_DONE_BUTTON;

	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_CANCEL_BUTTON)
	public WebElement MS_ACCOUNT_CANCEL_BUTTON;
	
	
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_ACCOUNT_ID)
	public WebElement MS_ACCOUNT_ACCOUNT_ID;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_SUB_ACCOUNT_ID)
	public WebElement MS_ACCOUNT_SUB_ACCOUNT_ID;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_MERCHANT_PIN)
	public WebElement MS_ACCOUNT_MERCHANT_PIN;
	
	//Moneris
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_STORE_ID)
	public WebElement MS_ACCOUNT_STORE_ID;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_URL)
	public WebElement MS_ACCOUNT_URL;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_TOKEN_ID)
	public WebElement MS_ACCOUNT_TOKEN_ID;
	
	//Payment Express
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_USERNAME)
	public WebElement MS_ACCOUNT_USERNAME;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_PASSWORD)
	public WebElement MS_ACCOUNT_PASSWORD;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_REST_API_KEY)
	public WebElement MS_ACCOUNT_REST_API_KEY;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_HIT_USERID)
	public WebElement MS_ACCOUNT_HIT_USERID;
	@FindBy(id = OR_MerchantServices.MS_ACCOUNT_HIT_KEY)
	public WebElement MS_ACCOUNT_HIT_KEY;

	@FindBy(id = OR_MerchantServices.MS_NEW_MERCHANT_ACCOUNT)
	public WebElement MS_NEW_MERCHANT_ACCOUNT;
	
	@FindBy(id = OR_MerchantServices.MS_DELETE_BTN)
	public WebElement MS_DELETE_BTN;
	
	@FindBy(xpath = OR_MerchantServices.MS_ACTIVE_GATEWAYCHECKBOX)
	public WebElement MS_ACTIVE_GATEWAYCHECKBOX;
	
}
