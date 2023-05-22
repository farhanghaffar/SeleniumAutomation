package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.pages.DerivedRatePage;
import com.innroad.inncenter.pages.NightlyRatePage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_NightlyRatePlan;

public class Elements_DerivedRate {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("Elements_DerivedRate");

	public Elements_DerivedRate(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);

	}

	@FindBy(xpath = DerivedRatePage.RATE_PLAN_NAMES_LIST)
	public List<WebElement> RATE_PLAN_NAMES_LIST;
	
	@FindBy(xpath = DerivedRatePage.SELECTED_RATEPLAN_DROPDOWN_ARROWS)
	public List<WebElement> SELECTED_RATEPLAN_DROPDOWN_ARROWS;
	
	@FindBy(xpath = DerivedRatePage.RATE_VALUE)
	public WebElement RATE_VALUE;
	
	@FindBy(xpath = DerivedRatePage.DOLLAR_SIGN)
	public WebElement DOLLAR_SIGN;
	
	@FindBy(xpath = DerivedRatePage.TAKE_RULES_FROM_PARENTS_CHECKBOX)
	public WebElement TAKE_RULES_FROM_PARENTS_CHECKBOX;
	
	@FindBy(xpath = DerivedRatePage.UNSAVED_POPUP)
	public WebElement UNSAVED_POPUP;
	
	@FindBy(xpath = DerivedRatePage.OPENED_RATE_PLAN_TAB_CLOSE_BUTTON)
	public WebElement OPENED_RATE_PLAN_TAB_CLOSE_BUTTON;
	

	@FindBy(xpath = DerivedRatePage.DERIVED_RATE_PLAN_ARROW)
	public WebElement DERIVED_RATE_PLAN_ARROW;
	
	@FindBy(xpath = DerivedRatePage.DERIVEDRATEPLANARROW)
	public WebElement DERIVEDRATEPLANARROW;
	
	@FindBy(xpath = DerivedRatePage.DELETE_RATE_PLAN_POPUP)
	public WebElement DELETE_RATE_PLAN_POPUP;
	
	@FindBy(xpath = DerivedRatePage.DERIVED_RATE_PLAN_NAME_LIST)
	public List<WebElement> DERIVED_RATE_PLAN_NAME_LIST;
	
	@FindBy(xpath = DerivedRatePage.VIEW_PARENT_RATE_PLAN_LINK)
	public WebElement VIEW_PARENT_RATE_PLAN_LINK;
	
	@FindBy(xpath = DerivedRatePage.PARENT_RATE_PLAN_POPUP)
	public WebElement PARENT_RATE_PLAN_POPUP;
	
	@FindBy(xpath = DerivedRatePage.PARENT_RATE_PLAN_POPUP_CLOSE)
	public WebElement PARENT_RATE_PLAN_POPUP_CLOSE;
	
	@FindBy(xpath = DerivedRatePage.EDIT_RATE_PLAN_CALENDAR_MONTHS)
	public List<WebElement> EDIT_RATE_PLAN_CALENDAR_MONTHS;
	
	@FindBy(xpath = DerivedRatePage.LOAD_MORE_DATES)
	public WebElement LOAD_MORE_DATES;
	
	@FindBy(xpath = DerivedRatePage.CUSTOM_DATE_RANGE)
	public WebElement CUSTOM_DATE_RANGE;

	@FindBy(xpath = DerivedRatePage.TAKERULESFROMPARENTUNCHECKPOPUP)
	public WebElement TAKERULESFROMPARENTUNCHECKPOPUP;
	
	@FindBy(xpath = DerivedRatePage.SELECT_CUSTOM_DATE)
	public List<WebElement> SELECT_CUSTOM_DATE;
	
	@FindBy(xpath = DerivedRatePage.REMOVE_CUSTOM_DATE)
	public List<WebElement> REMOVE_CUSTOM_DATE;
	
	@FindBy(xpath = DerivedRatePage.ADD_CUSTOM_DATE)
	public WebElement ADD_CUSTOM_DATE;
	
	@FindBy(xpath = DerivedRatePage.CUSTOM_DATE_ERROR_MESSAGE)
	public WebElement CUSTOM_DATE_ERROR_MESSAGE;
	
	@FindBy(xpath = DerivedRatePage.DATE_RANGE_MISSMATCH_POPUP)
	public WebElement DATE_RANGE_MISSMATCH_POPUP;
	
	@FindBy(xpath = DerivedRatePage.DATE_RANGE_MISSMATCH_POPUP_MESSAGE)
	public WebElement DATE_RANGE_MISSMATCH_POPUP_MESSAGE;
	
	@FindBy(xpath = DerivedRatePage.VIEW_SEASON_CALENDAR)
	public WebElement VIEW_SEASON_CALENDAR;
	
	@FindBy(xpath = DerivedRatePage.VIEW_SEASON_CALENDAR_CLOSE)
	public WebElement VIEW_SEASON_CALENDAR_CLOSE;

	@FindBy(xpath = DerivedRatePage.CustomDateRange)
	public WebElement CustomDateRange;
	
	@FindBy(xpath = DerivedRatePage.DERIVED_RATE_PLAN_LABEL)
	public WebElement DERIVED_RATE_PLAN_LABEL;
	
	@FindBy(xpath = DerivedRatePage.getCalendarMonthHeading)
	public WebElement getCalendarMonthHeading;
	
	@FindBy(xpath = DerivedRatePage.clickCalendarNextMonth)
	public WebElement clickCalendarNextMonth;
	
}
