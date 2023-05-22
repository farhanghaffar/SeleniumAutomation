package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR_Products;
import com.innroad.inncenter.properties.OR_Products;

public class Elements_Products {

	WebDriver driver;

	public Elements_Products(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}

	@FindBy(xpath = OR_Products.PRODUCT_COLUMNS_TITLE_LIST)
	public List<WebElement> productColumnsTitleList;

	@FindBy(xpath = OR_Products.PRODUCTS_EDIT_ICONLIST)
	public List<WebElement> productsEditIconList;

	@FindBy(xpath = OR_Products.PRODUCTS_DELETE_ICONLIST)
	public List<WebElement> productDeleteIconList;

	@FindBy(xpath = OR_Products.ADD_PRODUCT_POP_UP_HEADING)
	public WebElement addProductPopUpHeading;

	@FindBy(xpath = OR_Products.PRODUCT_NAME)
	public WebElement productName;

	@FindBy(xpath = OR_Products.ADD_PRODUCT_CATEGORY)
	public WebElement addProductCategory;

	@FindBy(xpath = OR_Products.ADD_PRODUCT_COST_PER_ONE)
	public WebElement addProductCostPerOne;

	@FindBy(xpath = OR_Products.ADD_PRODUCT_COST_PER_TWO)
	public WebElement addProductCostPerTwo;

	@FindBy(xpath = OR_Products.PRODUCT_COST)
	public WebElement productCost;

	@FindBy(xpath = OR_Products.SELL_ON_BOOKING_CHECKBOX)
	public WebElement sellOnBookingCheckBox;

	@FindBy(xpath = OR_Products.SELECTION_ALWAYS_OR_CUSTOM)
	public WebElement selectionAlwaysOrCustom;

	@FindBy(xpath = OR_Products.ROOM_CLASS_ASSOCIATION)
	public WebElement roomClassAssociation;

	@FindBy(xpath = OR_Products.CUSTOM_START_DATE)
	public List<WebElement> customStartDate;

	@FindBy(xpath = OR_Products.CUSTOM_END_DATE)
	public List<WebElement> customEndDate;

	@FindBy(xpath = OR_Products.PLUS_CUSTOM_DATE_FIELDS)
	public WebElement plusCustomDateField;

	@FindBy(xpath = OR_Products.MINUS_CUSTOM_DATE_FIELDS)
	public List<WebElement> minusCustomDateField;

	@FindBy(xpath = OR_Products.OVERLAPING_MESSAGE)
	public WebElement overLapingMessage;

	@FindBy(xpath = OR_Products.ADD_PRODUCT_DESCRIPTION)
	public WebElement addProductDescription;

	@FindBy(xpath = OR_Products.ENTER_PRODUCT_POLICY)
	public WebElement enterProductPolicy;

	@FindBy(xpath = OR_Products.POP_UP_ADD_PRODUCT_BUTTON)
	public WebElement popUpAddProductButton;

	@FindBy(xpath = OR_Products.SELECT_CUSTOM)
	public WebElement selectCustom;

	@FindBy(xpath = OR_Products.ADD_PRODUCT_BUTTON)
	public WebElement addProductButton;

	@FindBy(xpath = OR_Products.PRODUCTS_NAMES_LIST)
	public List<WebElement> productsNameList;

	@FindBy(xpath = OR_Products.CUSTOM_DATE_RANGE)
	public List<WebElement> customDateRange;

	@FindBy(xpath = OR_Products.SELL_ON_BOOKING_ENGINE_CHECKBOX)
	public WebElement sellOnBookingEngineCheckBox;

	@FindBy(xpath = OR_Products.HEADING_MONTH)
	public WebElement headingMonth;

	@FindBy(xpath = OR_Products.RIGHT_ARROW_ON_CALENDER)
	public WebElement rightArrowOnCalender;

	@FindBy(xpath = OR_Products.DELETE_PRODUCT_TITLE)
	public WebElement deleteProductTitle;

	@FindBy(xpath = OR_Products.DELETE_PRODUCT_DESCRIPTION)
	public WebElement deleteProductDescription;

	@FindBy(xpath = OR_Products.DELETE_PRODUCT_POPUP_DELETE_BUTTON)
	public WebElement deleteProductPopupDeleteButton;

	@FindBy(xpath = OR_Products.DELETE_PRODUCT_POPUP_CANCEL_BUTTON)
	public WebElement deleteProductPopUpCancelButtonn;

	@FindBy(xpath = OR_Products.REMOVE_FROM_BUNDLE_DELETE_PRODUCT_DESCRIPTION)
	public WebElement removeProductFromBundleBeforeDeleteDescription;

	@FindBy(xpath = OR_Products.REMOVE_FROM_BUNDLE_DELETE_PRODUCT_OK_BUTTON)
	public WebElement removeProductFromBundleBeforeDeleteOkButton;

	@FindBy(xpath = OR_Products.UPDATE_PRODUCT_BUTTON)
	public WebElement updateProductButton;

}