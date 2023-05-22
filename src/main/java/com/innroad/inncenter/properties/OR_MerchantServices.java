package com.innroad.inncenter.properties;

public class OR_MerchantServices 
{
	public static final String MS_SUB_TITLE_LINK_FRM_SETUP= "(//a[contains(@id,'ucNavSecondary_rptrNavMain_lbtnNavMainItem') and text() = 'Merchant Services'])";
	public static final String MS_NEW_MERCHANT_ACCOUNT= "//input[@id='MainContent_btnAddGateWay' or @value='New Merchant Account']";
	public static final String MS_STATUS = "//tr[@id='trStatus']//td[2]//select";
	//public static final String MS_ACTIVE_GATEWAYCHECKBOX = "(//table[@class='dgText']//tbody//tr//td[7])[2]";
	public static final String MS_ACTIVE_GATEWAYCHECKBOX = "//input[@id='MainContent_dgMerchantService_chkDelete_0']";
	
	//public static final String MS_DELETE_BTN = "//input[@id='MainContent_btnDelete' or @value='Delete']";
	public static final String MS_DELETE_BTN = "MainContent_btnDelete";
	public static final String MS_NEW_MERCHANT_ACCOUNT_NAME = "//table[@class='ContentTable']//tr[4]//td//input[@id='MainContent_txtAccountName']";
	public static final String MS_NEW_MERCHANT_ACC_DESC = "//table[@class='ContentTable']//tr[5]//td//input[@id='MainContent_txtDescription']";
	public static final String MS_NEW_MERCHANT_ACC_NO = "//input[@id='MainContent_txtAccountNumber']";
	public static final String MS_NEW_MERCHANT_ACC_STATUS = "//select[@id='MainContent_drpStatus']";
	
	
	public static final String MS_CREATE_NEW_BUTTON = "MainContent_btnAddGateWay";
	
	public static final String MS_ACCOUNT_NAME = "MainContent_txtAccountName";
	public static final String MS_ACCOUNT_NUMBER = "MainContent_txtAccountNumber";
	public static final String MS_ACCOUNT_DESCRIPTION = "MainContent_txtDescription";
	public static final String MS_ACCOUNT_STATUS = "MainContent_drpStatus";
	public static final String MS_ACCOUNT_TRANSACTION_TYPE_CARD_PRESENT = "MainContent_chkTransactionType_0";
	public static final String MS_ACCOUNT_TRANSACTION_TYPE_CARD_NOT_PRESENT = "MainContent_chkTransactionType_1";
	public static final String MS_ACCOUNT_TRANSACTION_TYPE_E_COMMERCE = "MainContent_chkTransactionType_2";
	public static final String MS_ACCOUNT_TRANSACTION_TYPE_REQUIRE_CVV = "MainContent_chkRequireCVV";
	public static final String MS_ACCOUNT_GATEWAY = "MainContent_drpgateways";
	public static final String MS_ACCOUNT_GATEWAY_MODE = "MainContent_drpMode";

	//Merchant Partners
	public static final String MS_ACCOUNT_ACCOUNT_ID = "MainContent_txtTransactionKey";
	public static final String MS_ACCOUNT_SUB_ACCOUNT_ID  = "MainContent_txtGatewayLoginID";
	public static final String MS_ACCOUNT_MERCHANT_PIN = "MainContent_txtGatewayPassword";
	
	//Moneris
	public static final String MS_ACCOUNT_STORE_ID  = "MainContent_txtStoreId";
	public static final String MS_ACCOUNT_URL  = "MainContent_txtUrl";
	public static final String MS_ACCOUNT_TOKEN_ID = "MainContent_txtTokenId";
	
	//Payment Express
	public static final String MS_ACCOUNT_USERNAME  = "MainContent_txtGatewayLoginID";
	public static final String MS_ACCOUNT_PASSWORD  = "MainContent_txtGatewayPassword";
	public static final String MS_ACCOUNT_REST_API_KEY  = "MainContent_txtTransactionKey";
	public static final String MS_ACCOUNT_HIT_USERID  = "MainContent_txtPxHITLoginID";
	public static final String MS_ACCOUNT_HIT_KEY  = "MainContent_txtPxHITPassword";
	
	//PayMover
	public static final String MS_ACCOUNT_TRANSACTION_KEY_CODE = "MainContent_txtTransactionKey";
	
	
	public static final String MS_ACCOUNT_ASSOCIATE_PROPERTIES_BUTTON = "MainContent_btnAssociateProperty";
	
	
	public static final String MS_ACCOUNT_SAVE_BUTTON = "MainContent_btnSave";
	public static final String MS_ACCOUNT_DONE_BUTTON = "MainContent_btnDone";
	public static final String MS_ACCOUNT_CANCEL_BUTTON = "MainContent_btnCancel";
	
	
	
	
}
