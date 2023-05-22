package com.innroad.inncenter.properties;

public class OR_TaxAndFee {

	public static final String taxFeeName = "//input[@name='name']";
	public static final String taxFeeAmount = "//input[@name='amount']";
	public static final String ledgerAccount = "(//div[contains(@class,'selected-value')])[1]";
	public static final String taxVatOption = "//input[@name='vat']";
	public static final String vat_checkbox="//span[text()='VAT']";
	public static final String taxVatchecked = "//span[contains(@class,'checked')]//input[@name='vat']";
	public static final String saveButotn = "//button/span[contains(text(),'Save')]";
	public static final String displayName = "//input[@name='displayName']";
	public static final String description = "//textarea[@name='description']";
	public static final String createButton="//span[contains(text(),'Create')]/..";
	public static final String createTax="//a[contains(@href,'taxes/tax')]";
	public static final String createFees="//a[contains(@href,'taxes/fee')]";
	public static final String deleteTaxIcon="//button[@class='icon-delete b-none']";
	public static final String confirmDeleteTax="//button[@class='ant-btn ant-btn-success btn inrd-button']";

	public static final String tax_ddl="//label[text()='Ledger Account']/..//div[@class='ant-select-selector']";
	   public static final String selectPercentCty="//div[contains(@class,'ant-select-item-option-content') and text()='Percentage per charge']";
    public static final String selectPercentCtyFee="//div[contains(@class,'ant-select-item-option-content') and text()='percentage']";
	public static final String SelectPerStayFee = "//div[contains(@class,'ant-select-item-option-content') and text()='per stay']";
	
	public static final String addRoomClass="//button[text()='Add room classes']";
	public static final String addRatePlan="//button[text()='Add rate plans']";
	public static final String addSource="//button[text()='Add source']";
	
	public static final String excludeTaxExcept_checkbox="//span[text()='Exclude when tax exempt']";
	//public static final String addledgerAccounts="//h2[contains(text(),'ledger accounts')]/..//div//i";

	//public static final String addledgerAccounts="(//button[@class='add-data-btn'])[1]";

	public static final String addledgerAccounts="//h2[contains(text(),'ledger accounts')]/..//div//button";

	public static final String backIcon="//div[@class='icon-return']";	
	 public static final String restrict_Toggle="//button[contains(@name,'isMoreRatePlanSourceRoomClass')]";
	 

}
