package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.pages.NightlyRatePage;
import com.innroad.inncenter.properties.OR_NightlyRatePlan;
import com.innroad.inncenter.properties.OR_PackageRatePlan;

public class Elements_PackageRate {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("Elements_PackageRate");

	public Elements_PackageRate(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);

	}

	@FindBy(xpath = OR_PackageRatePlan.TOOL_TIP_PACKAGE_RATE_PLAN)
	public WebElement toolTip;

	@FindBy(xpath = OR_PackageRatePlan.PRODUCTS_ADDED_DETAILS_WITH_TOOLTIP_PACKAGE_RATE_PLAN)
	public WebElement addedProductDetail;

	@FindBy(xpath = OR_PackageRatePlan.CLOSE_PACKAGE_RATE_PLAN_BUTTON)
	public WebElement closePackageButton;

	@FindBy(xpath = OR_PackageRatePlan.CLOSE_PACKAGE_RATE_PLAN_YES_BUTTON)
	public WebElement yesButtonClosePackage;

	@FindBy(xpath = OR_PackageRatePlan.CLOSE_PACKAGE_RATE_PLAN_CANCEL_BUTTON)
	public WebElement cancelButtonClosePackage;

	@FindBy(xpath = OR_PackageRatePlan.CLOSE_PACKAGE_RATE_PLAN_UNSAVED_DATA_STATEMENT)
	public WebElement unsavedDataStatement;

	@FindBy(xpath = OR_PackageRatePlan.CLOSE_PACKAGE_RATE_PLAN_ARE_YOU_SURE_STATEMENT)
	public WebElement areYouSureStatement;

	@FindBy(xpath = OR_PackageRatePlan.SearchProductsInPackageRate)
	public WebElement SearchProductsInPackageRate;

	@FindBy(xpath = OR_PackageRatePlan.ProductsName)
	public List<WebElement> ProductsName;

	@FindBy(xpath = OR_PackageRatePlan.ProductPageText)
	public WebElement ProductPageText;
	
	@FindBy(xpath = OR_PackageRatePlan.SEARCHED_PRODUCT_LIST_SIZE)
	public List<WebElement> SEARCHED_PRODUCT_LIST_SIZE;
	
	@FindBy(xpath = OR_PackageRatePlan.AddProduct)
	public WebElement AddProduct;
	
	@FindBy(xpath = OR_PackageRatePlan.ProductName)
	public WebElement ProductName;

	@FindBy(xpath = OR_PackageRatePlan.ProductImage)
	public WebElement ProductImage;
	
	@FindBy(xpath = OR_PackageRatePlan.ProductCost)
	public WebElement ProductCost;
	
	@FindBy(xpath = OR_PackageRatePlan.ProductDescription)
	public WebElement ProductDescription;
	
	@FindBy(xpath = OR_PackageRatePlan.ProductPolicy)
	public WebElement ProductPolicy;
	
	@FindBy(xpath = OR_PackageRatePlan.AddProductButton)
	public WebElement AddProductButton;
	
	@FindBy(xpath = OR_PackageRatePlan.checkSellOnBooking)
	public WebElement checkSellOnBooking;

	@FindBy(xpath = OR_PackageRatePlan.SELECTED_RATE_TYPE)
	public WebElement SELECTED_RATE_TYPE;
	
	@FindBy(xpath = OR_PackageRatePlan.EDIT_PRODUCT_DIALOG)
	public WebElement EDIT_PRODUCT_DIALOG;
	
	@FindBy(xpath = OR_PackageRatePlan.UPDATE_PRODUCT_BUTTON)
	public WebElement UPDATE_PRODUCT_BUTTON;
	
	@FindBy(xpath = OR_PackageRatePlan.SELECTED_PRODUCT_IN_RATEPLAN)
	public List<WebElement> SELECTED_PRODUCT_IN_RATEPLAN;
	
	@FindBy(xpath = OR_PackageRatePlan.SELECTED_ROOM_CLASSES_IN_PRODUCT_DETAILS)
	public List<WebElement> SELECTED_ROOM_CLASSES_IN_PRODUCT_DETAILS;
	
	@FindBy(xpath = OR_PackageRatePlan.CLOSE_EDIT_PRODUCT_DIALOG)
	public WebElement CLOSE_EDIT_PRODUCT_DIALOG;
	
}