package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.pages.NightlyRatePage;
import com.innroad.inncenter.properties.OR_NightlyRatePlan;
import com.innroad.inncenter.properties.OR_RatesGrid;

public class Elements_NightlyRate {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("Elements_NightlyRate");

	public Elements_NightlyRate(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);

	}

	@FindBy(xpath = NightlyRatePage.RATE_PLAN_NAME)
	public WebElement RATE_PLAN_NAME;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME)
	public WebElement RATE_PLAN_FOLIO_DISPLAY_NAME;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_DESCRIPTION)
	public WebElement RATE_PLAN_DESCRIPTION;
	
	@FindBy(xpath = NightlyRatePage.NEXT_BUTTON)
	public WebElement NEXT_BUTTON;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_NAME_ERROR_TEXT)
	public WebElement RATE_PLAN_NAME_ERROR_TEXT;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT)
	public WebElement RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT;
	
	@FindBy(xpath = NightlyRatePage.DISTRIBUTION_CHANNEL_LIST)
	public List<WebElement> DISTRIBUTION_CHANNEL_LIST;
	
	@FindBy(xpath = NightlyRatePage.ROOMCLASSES_LIST)
	public List<WebElement> ROOMCLASSES_LIST;
	
	@FindBy(xpath = NightlyRatePage.PROMO_CODE)
	public WebElement PROMO_CODE;
	
	@FindBy(xpath = NightlyRatePage.RESTRICTIONS_TO_QUALIFY_RATE)
	public WebElement RESTRICTIONS_TO_QUALIFY_RATE;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_STATUS_SELECTION)
	public WebElement RATE_PLAN_STATUS_SELECTION;
	
	@FindBy(xpath = NightlyRatePage.SAVE_RATE_PLAN)
	public WebElement SAVE_RATE_PLAN;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_NAME_EDIT_PAGE)
	public WebElement RATE_PLAN_NAME_EDIT_PAGE;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_TYPE_EDIT_PAGE)
	public WebElement RATE_PLAN_TYPE_EDIT_PAGE;
	
	@FindBy(xpath = NightlyRatePage.PROPERTY_NAME_EDIT_PAGE)
	public WebElement PROPERTY_NAME_EDIT_PAGE;
	
	@FindBy(xpath = NightlyRatePage.RATE_PLAN_OVERVIEW_TAB)
	public WebElement RATE_PLAN_OVERVIEW_TAB;
	
	@FindBy(xpath = NightlyRatePage.RESTRICTION_AND_POLICIES_TAB)
	public WebElement RESTRICTION_AND_POLICIES_TAB;
	
	@FindBy(xpath = NightlyRatePage.CALENDAR_TAB)
	public WebElement CALENDAR_TAB;
	
	@FindBy(xpath = OR_NightlyRatePlan.DEFAULT_RATEPLAN_TOGGLE)
    public WebElement DEFAULT_RATEPLAN_TOGGLE;
	
	@FindBy(xpath = OR_NightlyRatePlan.DEFAULT_RATEPLAN_TOOLTIP)
    public WebElement DEFAULT_RATEPLAN_TOOLTIP;
	
	@FindBy(xpath = OR_NightlyRatePlan.ROOMCLASSES)
	public List<WebElement> ROOMCLASSES;

	@FindBy(xpath = NightlyRatePage.ratePlanDropDown)
    public WebElement ratePlanDropDown;
	
	@FindBy(xpath = OR_NightlyRatePlan.editThisSeason)
    public WebElement editThisSeason;
	
	@FindBy(xpath = OR_NightlyRatePlan.editSeasonCloseIcon)
    public WebElement editSeasonCloseIcon;
	
	@FindBy(xpath = OR_NightlyRatePlan.editSeasonCloseYesButton)
    public WebElement editSeasonCloseYesButton;
	
	@FindBy(xpath = OR_NightlyRatePlan.policiesCheckBox)
    public List<WebElement> policiesCheckBox;
	
	@FindBy(xpath = OR_NightlyRatePlan.policiesNames)
    public List<WebElement> policiesNames;
	
	@FindBy(xpath = OR_NightlyRatePlan.policiesCheckBoxFromSeason)
    public List<WebElement> policiesCheckBoxFromSeason;
	
	@FindBy(xpath = OR_NightlyRatePlan.policiesNameFromSeason)
    public List<WebElement> policiesNameFromSeason;
	
	@FindBy(xpath = OR_NightlyRatePlan.assignPoliciesByRoomClass)
    public WebElement assignPoliciesByRoomClass;

	@FindBy(xpath = OR_NightlyRatePlan.assignRuleByRoomClass)
    public WebElement assignRuleByRoomClass;

	@FindBy(xpath = NightlyRatePage.PRODUCTS_TAB)
	public WebElement PRODUCTS_TAB;
	
	@FindBy(xpath =NightlyRatePage. ClickDelete)
	public WebElement clickDelete;
	
	@FindBy(xpath = NightlyRatePage.DeleteButton)
	public WebElement deleteButton;
}
