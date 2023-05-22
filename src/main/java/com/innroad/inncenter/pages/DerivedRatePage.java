package com.innroad.inncenter.pages;

public class DerivedRatePage {
	public static final String RATE_PLAN_NAMES_LIST = "//div[@class='center-screen rate-plan-summary']//label/span[2]";
	//public static final String SELECTED_RATEPLAN_DROPDOWN_ARROWS = "//div[@class='baseRatesWrapper']//span[@class='ant-select-arrow']/i";
	public static final String SELECTED_RATEPLAN_DROPDOWN_ARROWS = "//div[@class='baseRatesWrapper']//span[@class='ant-select-arrow']/span";
	public static final String CURRENCY_OPTIONS = "(//ul[@role='listbox'])[1]/li";
	public static final String VALUE_OPTIONS = "(//ul[@role='listbox'])[2]/li";
	
	public static final String RATE_VALUE = "//input[@role='spinbutton']";   //get value to verify  percent
	public static final String DOLLAR_SIGN = "//div[contains(@class,'InputCurrency')]/following-sibling::span";
	public static final String TAKE_RULES_FROM_PARENTS_CHECKBOX = "//span[text()='Take rules from parent rate plan']//parent::label/span[contains(@class,'ant-checkbox')]";
	public static final String TAKERULESFROMPARENTUNCHECKPOPUP = "//span[contains(text(),'Yes')]//parent::button";
	public static final String UNSAVED_POPUP = "//div[text()='You have unsaved data']";
	public static final String OPENED_RATE_PLAN_TAB_CLOSE_BUTTON = "//a[contains(@data-id,'RATEPLAN_TABID') and contains(@class,'close')]";
	public static final String DERIVED_RATE_PLAN_ARROW = "//div[contains(@class,'derived-rates-listing')]";
	public static final String DERIVEDRATEPLANARROW = "//div[contains(@class,'derived-rates-listing')]//div";
	public static final String DELETE_RATE_PLAN_POPUP = "//div[text()='Delete Rate Plan?']";
	public static final String DERIVED_RATE_PLAN_NAME_LIST = "//div[@class='DerivedPlan']//h3/span[2]";
	public static final String VIEW_PARENT_RATE_PLAN_LINK = "//a[@class='ParentRatePlanLink']";
	public static final String PARENT_RATE_PLAN_POPUP = "//div[text()='Parent rate plan']";
	public static final String PARENT_RATE_PLAN_POPUP_CLOSE = "//button[@class='ant-modal-close']";
	public static final String LOAD_MORE_DATES = "//span[@class='loadMore-dates']/span";
	public static final String EDIT_RATE_PLAN_CALENDAR_MONTHS = "//div[@class='DayPicker-Month']";
	public static final String CUSTOM_DATE_RANGE = "//div[contains(@class,'customDateRange')]";
	
	public static final String SELECT_CUSTOM_DATE = "//div[@class='DayPickerInput']/input";
	public static final String ADD_CUSTOM_DATE = "(//i[contains(@class,'plus-circle')])[last()]";
	public static final String REMOVE_CUSTOM_DATE = "//button[contains(@class,'icon minus')]";
	public static final String CUSTOM_DATE_ERROR_MESSAGE = "//div[@class='row derived-ratePlans']/div[contains(@class,'color-red')]";
	public static final String DATE_RANGE_MISSMATCH_POPUP = "//div[contains(@id,'rcDialogTitle') and text()='Date Range Mismatch']";
	public static final String DATE_RANGE_MISSMATCH_POPUP_MESSAGE = "//div[@class='ant-modal-body']//p";
	public static final String VIEW_SEASON_CALENDAR = "//button[text()='View season calendar']";
	// view Season Calendar tabs:
	
	public static final String VIEW_SEASON_CALENDAR_CLOSE = "//span[contains(@class,'ant-modal-close-x')]";
	public static final String CustomDateRange = "//div[text()='Custom date range']";
	public static final String getCalendarMonthHeading = "//div[@class='DayPicker-Caption']//div";
	public static final String clickCalendarNextMonth = "//button[contains(@class,'calenderBtn-FloatRight')]";
	public static final String DERIVED_RATE_PLAN_LABEL = "//div[contains(@class,'derived-rates-listing')]/div";
}
