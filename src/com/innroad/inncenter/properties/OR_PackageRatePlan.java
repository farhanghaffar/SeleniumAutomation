package com.innroad.inncenter.properties;

public class OR_PackageRatePlan {

	public static final String TOOL_TIP_PACKAGE_RATE_PLAN = "//span[@class=' inrd-tooltip']";
	public static final String PRODUCTS_ADDED_DETAILS_WITH_TOOLTIP_PACKAGE_RATE_PLAN = "//span[@class=' inrd-tooltip']/parent::div";
	public static final String CLOSE_PACKAGE_RATE_PLAN_BUTTON = "(//a[@data-id='ADD_RATEPLAN_TABID'])[1]";
	public static final String CLOSE_PACKAGE_RATE_PLAN_YES_BUTTON = "//span[text()='Yes']/parent::button";
	public static final String CLOSE_PACKAGE_RATE_PLAN_CANCEL_BUTTON = "//span[text()='Cancel']/parent::button";
	public static final String CLOSE_PACKAGE_RATE_PLAN_UNSAVED_DATA_STATEMENT = "//div[text()='You have unsaved data']";
	public static final String CLOSE_PACKAGE_RATE_PLAN_ARE_YOU_SURE_STATEMENT = "//p[text()='Are you sure you want to close the Tab? All the unsaved data will be lost.']";

	public static final String SearchProductsInPackageRate = "//button[@class='ant-btn undefined inrd-button']//preceding-sibling::input";
	public static final String ProductsName = "//tbody[@class='ant-table-tbody']//tr//td[@class='item-name']";
	public static final String ProductPageText = "//span[text()='Which products will be available in this plan ?']";
	public static final String SEARCHED_PRODUCT_LIST_SIZE = "//div[contains(@class,'search-box')]//parent::div/div";
	
	public static final String AddProduct= "//button[text()='Add product']";
	public static final String ProductName = "//input[@name='productName']";
	public static final String ProductImage = "//div[@id='image-upload-wrapper']//input[@id='image-upload']";
	public static final String ProductCost = "//input[@class='ant-input-number-input']";
	public static final String ProductDescription = "//textarea[@name='productDescription']";
	public static final String ProductPolicy = "//textarea[@name='productPolicy']";
	public static final String AddProductButton = "//span[text()=' Add product']/..";
	public static final String checkSellOnBooking = "//input[@type='checkbox']//..";
}