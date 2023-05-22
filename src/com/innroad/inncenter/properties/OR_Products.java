package com.innroad.inncenter.properties;

public class OR_Products {

	public static final String PRODUCT_COLUMNS_TITLE_LIST = "//thead[@class='ant-table-thead']//span[contains(@class,'title')]";
	public static final String PRODUCTS_LIST = "//tbody//tr";
	public static final String PRODUCTS_EDIT_ICONLIST = "//tbody//tr//i[@class='icon-edit']";
	public static final String PRODUCTS_NAMES_LIST = "//tbody//tr//td[@class='item-name']";
	public static final String PRODUCTS_DELETE_ICONLIST = "//tbody//tr//i[@class='icon-trash']";
	public static final String ADD_PRODUCT_POP_UP_HEADING = "//div[contains(@id,'rcDialogTitle')]";
	public static final String PRODUCT_NAME = "//input[@name='productName']";
	public static final String ADD_PRODUCT_CATEGORY = "(//div[@class='ant-select ant-select-enabled'])[1]";
	public static final String ADD_PRODUCT_COST_PER_ONE = "(//div[@class='ant-select ant-select-enabled'])[2]";
	public static final String ADD_PRODUCT_COST_PER_TWO = "(//div[@class='ant-select ant-select-enabled'])[3]//span";
	public static final String PRODUCT_COST = "//input[@class='ant-input-number-input']";
	public static final String SELL_ON_BOOKING_CHECKBOX = "//label[@class='inrd-checkbox mt-15 fs-16 fw-4 mr-10 ant-checkbox-wrapper']//span";
	public static final String SELECTION_ALWAYS_OR_CUSTOM = "//div[@class='d-flex mb-10']//div";
	public static final String ROOM_CLASS_ASSOCIATION = "(//div[@class='ant-select-selection__rendered'])[5]";
	public static final String CUSTOM_START_DATE = "//div[@class='d-flex vertical-middle mb-20 customDateRange']//div//div//input";
	public static final String CUSTOM_END_DATE = "//div[@class='d-flex vertical-middle mb-20 customDateRange']//div//div//input";
	public static final String PLUS_CUSTOM_DATE_FIELDS = "//i[@class='anticon anticon-plus-circle mx-15 outlined inrd-icon']//*[name()='svg']";
	public static final String MINUS_CUSTOM_DATE_FIELDS = "//button[@class='btn icon minus sm color-brightBlue btn-radius-100 bg-white brd-brightBlue']";
	public static final String OVERLAPING_MESSAGE = "//div[@class='color-red my-10']";
	public static final String ADD_PRODUCT_DESCRIPTION = "//textarea[@name='productDescription']";
	public static final String ENTER_PRODUCT_POLICY = "//textarea[@name='productPolicy']";
	public static final String POP_UP_ADD_PRODUCT_BUTTON = "//button[@class='ant-btn btn wide font-N mb-20 inrd-button ant-btn-success']";
	public static final String ADD_PRODUCT_BUTTON = "//button[text()='Add product']";
	public static final String UPDATE_PRODUCT_BUTTON = "//span[text()='Update product']/parent::button";

	// Delete product
	public static final String DELETE_PRODUCT_TITLE = "//div[text()='Delete product?']";
	public static final String DELETE_PRODUCT_DESCRIPTION = "//p[text()='Are you sure you want to delete this product?']";
	public static final String REMOVE_FROM_BUNDLE_DELETE_PRODUCT_DESCRIPTION = "// div[@class='bundle-product-list']//p";
	public static final String REMOVE_FROM_BUNDLE_DELETE_PRODUCT_OK_BUTTON = "//span[text()='Ok']/parent::button";

	public static final String DELETE_PRODUCT_POPUP_DELETE_BUTTON = "//span[text()='Delete']/parent::button";
	public static final String DELETE_PRODUCT_POPUP_CANCEL_BUTTON = "//span[text()='Cancel']/parent::button";
	
	
	public static final String SELECT_CUSTOM="//li[@label='custom dates']";
	public static final String SELECT_CATEGORY="//li[@label='Bar']";
	public static final String SELL_ON_BOOKING_ENGINE_CHECKBOX="//span[@class='ant-checkbox-inner']";
	public static final String HEADING_MONTH="//div[@class='DayPicker-Caption']";
	public static final String RIGHT_ARROW_ON_CALENDER="//button[@class='ant-btn calenderBtn-FloatRight inrd-button ant-btn-link']";
	
	public static final String CUSTOM_DATE_RANGE="//div[@class='d-flex vertical-middle mb-20 customDateRange']//div//div//input";



}