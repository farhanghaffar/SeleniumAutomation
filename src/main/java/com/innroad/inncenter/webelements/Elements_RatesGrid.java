package com.innroad.inncenter.webelements;

import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.pages.NightlyRatePage;
import com.innroad.inncenter.pages.RateGridPage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.ORRateGrid;
import com.innroad.inncenter.properties.OR_NightlyRatePlan;
import com.innroad.inncenter.properties.OR_RateGrid;
import com.innroad.inncenter.properties.OR_RatesGrid;

public class Elements_RatesGrid {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("Elements_RatesGrid");

	public Elements_RatesGrid(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);

	}

	@FindBy(id = OR.RateGridAvailableButton)
	public WebElement RateGridAvailableButton;

	@FindBy(xpath = OR.RateGridBulkUpdateButton)
	public WebElement RateGridBulkUpdateButton;

	@FindBy(xpath = OR.RateGridBulkUpdateAvailable)
	public WebElement RateGridBulkUpdateAvailable;

	@FindBy(xpath = OR.AvailabilityHeading)
	public WebElement AvailabilityHeading;

	@FindBy(xpath = OR.BulkUpdatePopupText)
	public WebElement BulkUpdatePopupText;

	@FindBy(xpath = OR.StartDateInput)
	public WebElement StartDateInput;

	@FindBy(xpath = OR.StartDateHeader)
	public WebElement StartDateHeader;

	@FindBy(xpath = OR.NextDateIcon)
	public WebElement NextDateIcon;

	@FindBy(xpath = OR.TotalOccupancy)
	public WebElement TotalOccupancy;

	@FindBy(xpath = OR.TotalOccupanyValue)
	public WebElement TotalOccupanyValue;

	@FindBy(xpath = OR.BulkUpdatePopupRoomClass)
	public WebElement BulkUpdatePopupRoomClass;

	@FindBy(xpath = OR.UpdateButton)
	public WebElement UpdateButton;

	@FindBy(xpath = OR.DaysText)
	public WebElement DaysText;

	@FindBy(xpath = OR.BulkUpdateCancel)
	public WebElement BulkUpdateCancel;

	@FindBy(xpath = OR.RulesHeading)
	public WebElement RulesHeading;

	@FindBy(xpath = OR.MinimumStay)
	public WebElement MinimumStay;

	@FindBy(xpath = OR.MinimumStayValue)
	public WebElement MinimumStayValue;

	@FindBy(xpath = OR.CheckinToggle)
	public WebElement CheckinToggle;

	@FindBy(xpath = OR.NoCheckInInput)
	public WebElement NoCheckInInput;

	@FindBy(xpath = OR.NoCheckInCheckbox)
	public WebElement NoCheckInCheckbox;

	@FindBy(xpath = OR.CheckOutToggle)
	public WebElement CheckOutToggle;

	@FindBy(xpath = OR.NoCheckOutInput)
	public WebElement NoCheckOutInput;

	@FindBy(xpath = OR.NoCheckOutCheckbox)
	public WebElement NoCheckOutCheckbox;

	@FindBy(xpath = OR.ClosePopup)
	public WebElement ClosePopup;

	@FindBy(xpath = OR.TotalOccupancyTypeVisibility)
	public WebElement TotalOccupancyTypeVisibility;

	@FindBy(xpath = OR.UpdatExistingRules)
	public WebElement UpdatExistingRules;

	@FindBy(xpath = OR.UpdateAvailability)
	public WebElement UpdateAvailability;

	@FindBy(xpath = OR.Button_YesUpdate)
	public WebElement Button_YesUpdate;

	@FindBy(xpath = ORRateGrid.calendarIcon)
	public WebElement calendarIcon;

	@FindBy(xpath = ORRateGrid.calendarMonthHeading)
	public WebElement calendarMonthHeading;

	@FindBy(xpath = ORRateGrid.calendarRightArrow)
	public WebElement calendarRightArrow;

	@FindBy(xpath = RateGridPage.RATE_GRID_CALENDAR_ICON)
	public WebElement RATE_GRID_CALENDAR_ICON;

	@FindBy(xpath = RateGridPage.DATE_FROM_GRID_HEADER)
	public List<WebElement> DATE_FROM_GRID_HEADER;

	@FindBy(xpath = RateGridPage.TOTAL_OCCUPANCY_TABLE_DATE_COLUMNS)
	public List<WebElement> TOTAL_OCCUPANCY_TABLE_DATE_COLUMNS;

	@FindBy(xpath = RateGridPage.ROOMCLASSES_TABLE_DATE_COLUMNS)
	public List<WebElement> ROOMCLASSES_TABLE_DATE_COLUMNS;

	@FindBy(xpath = RateGridPage.ADD_RATE_PLAN)
	public WebElement ADD_RATE_PLAN;

	@FindBy(xpath = OR_RatesGrid.HEADER_DATES_ROW_WEEKDAY)
	public List<WebElement> HEADER_DATES_ROW_WEEKDAY;

	@FindBy(xpath = OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER)
	public List<WebElement> HEADER_DATES_ROW_DAY_NUMBER;

	@FindBy(xpath = OR_RatesGrid.DATE_RANGE_MONTH_YEAR)
	public List<WebElement> DATE_RANGE_MONTH_YEAR;

	@FindBy(xpath = OR_RatesGrid.DATE_RANGE_CALENDAR)
	public WebElement DATE_RANGE_CALENDAR;

	@FindBy(xpath = OR_RatesGrid.CALENDER_TODAY_BUTTON)
	public WebElement CALENDER_TODAY_BUTTON;

	@FindBy(xpath = OR_RatesGrid.TOTAL_OCCUPANCY_LABEL)
	public WebElement TOTAL_OCCUPANCY_LABEL;

	@FindBy(xpath = OR_RatesGrid.PACE_VS_LAST_YEAR_LABEL)
	public WebElement PACE_VS_LAST_YEAR_LABEL;

	@FindBy(xpath = OR_RatesGrid.ROOM_CLASSES_NAMES)
	public List<WebElement> ROOM_CLASSES_NAMES;

	@FindBy(xpath = OR_RatesGrid.ROOM_CLASS_EXPAND_BUTTON)
	public List<WebElement> ROOM_CLASS_EXPAND_BUTTON;

	@FindBy(xpath = OR_RatesGrid.SETTING_BUTTON_RATE_GRID)
	public WebElement settingButtonRateGrid;

	@FindBy(xpath = OR_RatesGrid.HEADING_AVAILABILITY_SETTING)
	public WebElement headingAvailabilitySetting;

	@FindBy(xpath = OR_RatesGrid.HEADING_RATES_SETTING)
	public WebElement headingRatesSetting;

	@FindBy(xpath = OR_RatesGrid.AVAILABILITY_TOGGLE_TEXT)
	public WebElement availabiltyToggleText;

	@FindBy(xpath = OR_RatesGrid.RATES_TOGGLE_TEXT)
	public WebElement ratesToggleText;

	@FindBy(xpath = OR_RatesGrid.SETTING_AVAILABILITY_TOGGLE_BUTTON)
	public WebElement settingAvailabilityToggleButton;

	@FindBy(xpath = OR_RatesGrid.SETTING_RATES_TOGGLE_BUTTON)
	public WebElement settingRatesToggleButton;

	@FindBy(xpath = OR.BULK_UPDATE_BUTTON)
	public WebElement bulkUpdateButton;

	@FindBy(xpath = OR.ADD_RATE_PLAN_BUTTON)
	public WebElement addRatePlanButton;

	@FindBy(xpath = OR_RatesGrid.HEADER_DATES_BACKGROUND_COLOR)
	public List<WebElement> HEADER_DATES_BACKGROUND_COLOR;

	@FindBy(xpath = OR_RatesGrid.RATESGRID_SETTING_BUTTON)
	public WebElement RATESGRID_SETTING_BUTTON;

	@FindBy(xpath = OR_RatesGrid.RATESGRID_SETTING_CONTAINER)
	public WebElement RATESGRID_SETTING_CONTAINER;

	@FindBy(xpath = OR_RatesGrid.RATESGRID_SETTING_CONTAINER_SHOWADDITIONALADULT_TOGGLE)
	public WebElement SHOW_ADDITIONALADULT_CHILD_TOGGLE;

	@FindBy(xpath = OR_RatesGrid.RATESGRID_BULKUPDATE_BUTTON)
	public WebElement RATESGRID_BULKUPDATE_BUTTON;

	@FindBy(xpath = OR_RatesGrid.UPDATERATES_OPTIONS)
	public List<WebElement> UPDATERATES_OPTIONS;

	@FindBy(xpath = OR_RatesGrid.rateGridBulkUpdate)
	public WebElement rateGridBulkUpdate;

	@FindBy(xpath = OR_RatesGrid.rateGridBulkUpdateAvailable)
	public WebElement rateGridBulkUpdateAvailable;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupHeading)
	public WebElement bulkUpdatePopupHeading;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupText)
	public WebElement bulkUpdatePopupText;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupCheckinInput)
	public WebElement bulkUpdatePopupCheckinInput;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupCheckoutInput)
	public WebElement bulkUpdatePopupCheckoutInput;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupRoomClass)
	public WebElement bulkUpdatePopupRoomClass;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupUpdateButton)
	public WebElement bulkUpdatePopupUpdateButton;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupDays)
	public WebElement bulkUpdatePopupDays;

	@FindBy(xpath = OR_RatesGrid.bulkUpdateCancel)
	public WebElement bulkUpdateCancel;

	@FindBy(xpath = OR_RatesGrid.rateGridBulkUpdateRates)
	public WebElement rateGridBulkUpdateRates;

	@FindBy(xpath = OR_RatesGrid.UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE)
	public WebElement UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE)
	public List<WebElement> UPDATE_RATE_NEWRATE_NIGHTLYRATE;
	
	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATES)
	public WebElement UPDATE_RATE_NEWRATE_NIGHTLYRATES;
	
	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALADULTS)
	public WebElement UPDATE_RATE_NEWRATE_ADDITIONALADULTS;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALCHILDS)
	public WebElement UPDATE_RATE_NEWRATE_ADDITIONALCHILDS;

	@FindBy(xpath = OR_RatesGrid.ADDRATEPLAN)
	public WebElement ADDRATEPLAN;

	@FindBy(xpath = OR_RatesGrid.RateNameInput)
	public WebElement RateNameInput;

	@FindBy(xpath = OR_RatesGrid.NextButton)
	public WebElement NextButton;

	@FindBy(xpath = OR_RatesGrid.CreateSeason)
	public WebElement CreateSeason;

	@FindBy(xpath = OR_RatesGrid.CompleteChanges)
	public WebElement CompleteChanges;

	@FindBy(xpath = OR_RatesGrid.SaveAsActive)
	public WebElement SaveAsActive;

	@FindBy(xpath = OR_RatesGrid.BlackOutButton)
	public WebElement BlackOutButton;

	@FindBy(xpath = OR_RatesGrid.SeasonNameInput)
	public WebElement SeasonNameInput;

	@FindBy(xpath = OR_RatesGrid.CreateSeasonButton)
	public WebElement CreateSeasonButton;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALADULT)
	public List<WebElement> UPDATE_RATE_NEWRATE_ADDITIONALADULT;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALCHILD)
	public List<WebElement> UPDATE_RATE_NEWRATE_ADDITIONALCHILD;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_FIRST)
	public WebElement UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_FIRST;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_SECOND)
	public WebElement UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_SECOND;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_VALUEFIELD)
	public WebElement UPDATE_RATE_UPDATEBYVALUE_VALUEFIELD;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_CreateSeason)
	public WebElement RatePlan_CreateSeason;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_SeasonName)
	public WebElement RatePlan_SeasonName;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Sunday)
	public WebElement RatePlan_Season_Sunday;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Monnday)
	public WebElement RatePlan_Season_Monnday;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Tuesday)
	public WebElement RatePlan_Season_Tuesday;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Wednesday)
	public WebElement RatePlan_Season_Wednesday;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Thursday)
	public WebElement RatePlan_Season_Thursday;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Friday)
	public WebElement RatePlan_Season_Friday;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Saturday)
	public WebElement RatePlan_Season_Saturday;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_CreateSeason)
	public WebElement RatePlan_Season_CreateSeason;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_ColorDropDown)
	public WebElement RatePlan_Season_ColorDropDown;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_ChargesForAdditionalChildAdult)
	public WebElement RatePlan_Season_ChargesForAdditionalChildAdult;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_AddRoomClass)
	public WebElement RatePlan_Season_AddRoomClass;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_RulesRestrictionsOnSeason)
	public WebElement RatePlan_Season_RulesRestrictionsOnSeason;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_AssignRulesByRoomClass)
	public WebElement RatePlan_Season_AssignRulesByRoomClass;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses)
	public WebElement RatePlan_Season_SeasonRulesSpecificRoomClasses;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SeasonMinNightsRule)
	public WebElement RatePlan_Season_SeasonMinNightsRule;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SeasonMinNightsRuleValue)
	public WebElement RatePlan_Season_SeasonMinNightsRuleValue;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SeasonSave)
	public WebElement RatePlan_Season_SeasonSave;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SeasonPolices)
	public WebElement RatePlan_Season_SeasonPolices;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_CompleteChanges)
	public WebElement RatePlan_Season_CompleteChanges;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SaveAsActive)
	public WebElement RatePlan_Season_SaveAsActive;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SeasonNoCheckIn)
	public WebElement RatePlan_Season_SeasonNoCheckIn;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SeasonNoCheckOut)
	public WebElement RatePlan_Season_SeasonNoCheckOut;

	@FindBy(xpath = NightlyRatePage.RATE_PLAN_DESCRIPTION)
	public WebElement RATE_PLAN_DESCRIPTION;

	@FindBy(xpath = NightlyRatePage.NEXT_BUTTON)
	public WebElement NEXT_BUTTON;

	@FindBy(xpath = NightlyRatePage.RATE_PLAN_NAME_ERROR_TEXT)
	public WebElement RATE_PLAN_NAME_ERROR_TEXT;

	@FindBy(xpath = NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT)
	public WebElement RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT;

	@FindBy(xpath = OR_RateGrid.ratePlanArrow)
	public WebElement ratePlanArrow;

	@FindBy(xpath = OR_RateGrid.ratePlanNamesList)
	public List<WebElement> ratePlanNamesList;

	@FindBy(xpath = OR_RateGrid.ratePlansColor)
	public List<WebElement> ratePlansColor;

	@FindBy(xpath = OR_RateGrid.ratePlanOption)
	public List<WebElement> ratePlanOption;

	@FindBy(xpath = OR_RateGrid.pacevsLastYearLabel)
	public WebElement pacevsLastYearLabel;

	@FindBy(xpath = OR_RateGrid.ratePlanSelectValue)
	public WebElement ratePlanSelectValue;

	@FindBy(xpath = OR_RateGrid.ratesTab)
	public WebElement ratesTab;

	@FindBy(xpath = OR_RateGrid.ratePlanNamesExpanCollapseIcon)
	public WebElement ratePlanNamesExpanCollapseIcon;
	
	@FindBy(xpath = OR_RateGrid.ratePlanNamesExpanIcon)
	public WebElement ratePlanNamesExpanIcon;
	
	@FindBy(xpath = OR_RateGrid.ratePlanNamesCollapseIcon)
	public WebElement ratePlanNamesCollapseIcon;

	@FindBy(xpath = OR_RateGrid.ratePlanSelectInput)
	public WebElement ratePlanSelectInput;

	@FindBy(xpath = OR_RateGrid.ratePlanInputBox)
	public WebElement ratePlanInputBox;

	@FindBy(xpath = OR_RateGrid.rateGridAllRoomClass)
	public List<WebElement> rateGridAllRoomClass;


	@FindBy(xpath = OR_RateGrid.rateGridAvailabilityLabel)
	public List<WebElement> rateGridAvailabilityLabel;

	@FindBy(xpath = OR_RateGrid.rateGridPopupDate)
	public WebElement rateGridPopupDate;

	@FindBy(xpath = OR_RateGrid.rateGridPopupRuleHeader)
	public WebElement rateGridPopupRuleHeader;

	@FindBy(xpath = OR_RateGrid.rateGridPopupRuleIndectionLabel)
	public WebElement rateGridPopupRuleIndectionLabel;

	@FindBy(xpath = OR_RateGrid.rateGridPopupMinimumNights)
	public WebElement rateGridPopupMinimumNights;

	@FindBy(xpath = OR_RateGrid.rateGridRoomRate)
	public WebElement rateGridRoomRate;

	@FindBy(xpath = OR_RateGrid.rateGridExtraAd)
	public WebElement rateGridExtraAd;

	@FindBy(xpath = OR_RateGrid.rateGridExtraCh)
	public WebElement rateGridExtraCh;

	@FindBy(xpath = OR_RateGrid.rateGridDate)
	public WebElement rateGridDate;

	@FindBy(xpath = OR_RateGrid.rateGridSuccess)
	public WebElement rateGridSuccess;

	@FindBy(xpath = OR_RateGrid.rateGridDanger)
	public WebElement rateGridDanger;

	@FindBy(xpath = OR_RateGrid.rateSavedMessage)
	public WebElement rateSavedMessage;

	@FindBy(xpath = OR_RateGrid.rateOverrideMessage)
	public WebElement rateOverrideMessage;

	@FindBy(xpath = OR_RateGrid.rateGridPopupRateChangeHeader)
	public WebElement rateGridPopupRateChangeHeader;

	@FindBy(xpath = OR_RateGrid.rateGridremoveOverRide)
	public WebElement rateGridremoveOverRide;

	@FindBy(xpath = OR_RateGrid.rateGridRuleSavedMessage)
	public WebElement rateGridRuleSavedMessage;

	@FindBy(xpath = OR_RateGrid.rateEditIcon)
	public WebElement rateEditIcon;

	@FindBy(xpath = OR_RateGrid.rateDeleteIcon)
	public WebElement rateDeleteIcon;

	@FindBy(xpath = OR_RateGrid.ruleIndecationIcon)
	public WebElement ruleIndecationIcon;

	@FindBy(xpath = OR_RateGrid.rateGridRoomClass)
	public List<WebElement> rateGridRoomClass;

	@FindBy(xpath = OR_RateGrid.ratePlanDesc)
	public WebElement ratePlanDesc;

	@FindBy(xpath = OR_RateGrid.ratePlanConditionDesc)
	public WebElement ratePlanConditionDesc;

	@FindBy(xpath = OR_RateGrid.popoverRate)
	public WebElement popoverRate;

	@FindBy(xpath = OR_RateGrid.rulePropertyImage)
	public List<WebElement> rulePropertyImage;

	@FindBy(xpath = OR_RateGrid.ratePlanMinusIcon)
	public WebElement ratePlanMinusIcon;

	@FindBy(xpath = OR_RateGrid.ratePlanPlusIcon)
	public WebElement ratePlanPlusIcon;

	@FindBy(xpath = OR_RateGrid.ruleToolTip)
	public WebElement ruleToolTip;

	@FindBy(xpath = OR_RateGrid.ratePlanOpenArrowIcon)
	public WebElement ratePlanOpenArrowIcon;

	@FindBy(xpath = OR_RateGrid.popupRuleHeader)
	public WebElement popupRuleHeader;

	@FindBy(xpath = OR_RateGrid.ruleLabels)
	public List<WebElement> ruleLabels;

	@FindBy(xpath = OR_RateGrid.ruleLabels)
	public WebElement ruleLabel;

	@FindBy(xpath = OR_RateGrid.ratePLanCloseIcon)
	public WebElement ratePLanCloseIcon;

	@FindBy(xpath = OR_RateGrid.ratePLanCalendarTab)
	public WebElement ratePLanCalendarTab;

	@FindBy(xpath = OR_RateGrid.rateDeleteButton)
	public WebElement rateDeleteButton;

	@FindBy(xpath = OR_RateGrid.ratePlanDeleteMsg)
	public WebElement ratePlanDeleteMsg;

	@FindBy(xpath = OR_RateGrid.ratePlanDeleteMessage)
	public WebElement ratePlanDeleteMessage;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Blockout)
	public WebElement RatePlan_Season_Blockout;

	@FindBy(xpath = OR_RatesGrid.totalOccupancyText)
	public WebElement totalOccupancyText;

	@FindBy(xpath = OR_RatesGrid.totalOccupancy)
	public WebElement totalOccupancy;

	@FindBy(xpath = OR_RatesGrid.totalOccupancyType)
	public WebElement totalOccupancyType;

	@FindBy(xpath = OR_RatesGrid.totalOccupancyTypeVisibility)
	public WebElement totalOccupancyTypeVisibility;

	@FindBy(xpath = OR_RatesGrid.occupancyIcon)
	public WebElement occupancyIcon;

	@FindBy(xpath = OR_RatesGrid.totalOccupanyValue)
	public WebElement totalOccupanyValue;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_DATE_PICKER)
	public List<WebElement> UPDATE_RATE_DATE_PICKER;

	@FindBy(xpath = OR_RatesGrid.UPDATE_RATE_CONFIRMATION_YES_UPDATE_BUTTON)
	public WebElement UPDATE_RATE_CONFIRMATION_YES_UPDATE_BUTTON;

	@FindBy(xpath = OR_RatesGrid.OCCUPANCY_ICON)
	public WebElement OccupancyIcon;

	@FindBy(xpath = OR_RateGrid.defaultRatePlan)
	public WebElement defaultRatePlan;

	@FindBy(xpath = OR_RateGrid.bestAvailableRoomClass)
	public WebElement bestAvailableRoomClass;

	@FindBy(xpath = OR_RateGrid.bestAvailableRateLabel)
	public WebElement bestAvailableRateLabel;

	@FindBy(xpath = OR_RateGrid.regularRates)
	public List<WebElement> regularRates;

	@FindBy(xpath = OR_RatesGrid.START_DATE_INPUT)
	public WebElement startDateInput;

	@FindBy(xpath = OR_RatesGrid.END_DATE_INPUT)
	public WebElement EndDateInput;

	@FindBy(xpath = OR_RatesGrid.TOTAL_OCCUPANCY_TEXT)
	public WebElement TotalOccupancyText;

	@FindBy(xpath = OR_RatesGrid.TOTAL_OCCUPANCY_TYPE)
	public WebElement TotalOccupancyType;

	@FindBy(xpath = OR_RatesGrid.TODAY_DATE_BUTTON)
	public WebElement TodayDateButton;

	@FindBy(xpath = OR_RatesGrid.START_DATE_SELECTED_DAY)
	public WebElement StartDateSelectedDay;

	@FindBy(xpath = OR_RatesGrid.END_DATE_SELECTED_DAY)
	public WebElement EndDateSelectedDay;

	@FindBy(xpath = OR_RatesGrid.OCCUPANCY_CLAUSE)
	public WebElement OcupancyClause;

	@FindBy(xpath = OR_RatesGrid.OCCUPANCY_PERCENTAGE_SIGN)
	public WebElement OCCUPANCY_PERCENTAGE_SIGN;

	@FindBy(xpath = OR_RatesGrid.NO_ROOM_CLASS_SELECTED)
	public WebElement NO_ROOM_CLASS_SELECTED;

	@FindBy(xpath = OR_RatesGrid.NO_DAYS_MATCH)
	public WebElement NO_DAYS_MATCH;

	@FindBy(xpath = OR_RatesGrid.BULKUPDATE_RATES_CLOSEBUTTON)
	public WebElement BULKUPDATE_RATES_CLOSEBUTTON;

	@FindBy(xpath = OR_RatesGrid.NO_DATS_MATCH_OK_BUTTON)
	public WebElement NO_DATS_MATCH_OK_BUTTON;

	@FindBy(xpath = OR_RatesGrid.ratePlanNames)
	public List<WebElement> ratePlanNames;

	@FindBy(xpath = OR_RatesGrid.rateGridDay)
	public List<WebElement> rateGridDay;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Edit_Calender)
	public WebElement RatePlan_Season_Edit_Calender;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_DefaultRatePlan)
	public WebElement RatePlan_Season_DefaultRatePlan;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_FillBlanks)
	public WebElement RatePlan_Season_FillBlanks;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_SaveRatePlan)
	public WebElement RatePlan_Season_SaveRatePlan;

	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_Replace)
	public WebElement RatePlan_Season_Replace;


	@FindBy(xpath = OR_RateGrid.blankBestAvailableRates)
	public List<WebElement> blankBestAvailableRates;
	
	@FindBy(xpath = OR_RateGrid.OverRideBestAvailableRates)
	public List<WebElement> OverRideBestAvailableRates;
	
	@FindBy(xpath = OR_RateGrid.rateCrossIcon)
	public WebElement rateCrossIcon;
	
	@FindBy(xpath = OR_RateGrid.differentSource)
	public WebElement differentSource;
	
	@FindBy(xpath = OR_RateGrid.squarBox)
	public WebElement squarBox;
	
	@FindBy(xpath = OR_RateGrid.availabilityBoxColor)
	public WebElement availabilityBoxColor;
	
	@FindBy(xpath = OR_RateGrid.bestAvailableRate)
	public WebElement bestAvailableRate;


	@FindBy(xpath = OR_RatesGrid.GET_RATE_PALN_DESCRIPTION)
	public WebElement getRatePlanDescription;

	@FindBy(xpath = OR_RatesGrid.GET_RATE_PALN_DESCRIPTION)
	public List<WebElement> getRatePlanDescriptionList;

	@FindBy(xpath = OR_RatesGrid.SELECTED_RATEPLAN_NAME)
	public WebElement selectedRatePlanName;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupTotalOccupancyDropDown)
	public WebElement bulkUpdatePopupTotalOccupancyDropDown;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupTotalOccupancyToggle)
	public WebElement bulkUpdatePopupTotalOccupancyToggle;

	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupTotalOccupanyValue)
	public WebElement bulkUpdatePopupTotalOccupanyValue;
	

	@FindBy(xpath = OR_RateGrid.availibilityLabel)
	public List<WebElement> availibilityLabel;

	@FindBy(xpath = RateGridPage.GetTitile)
	public WebElement GetTitile;
	
	@FindBy(xpath = RateGridPage.EnterInterval)
	public WebElement EnterInterval;
	
	@FindBy(xpath = RateGridPage.CheckboxProrateForEachSeasonbyDefault)
	public WebElement CheckboxProrateForEachSeasonbyDefault;
	
	@FindBy(xpath = RateGridPage.TextAfterCheckedProRateCheckbox)
	public WebElement TextAfterCheckedProRateCheckbox;
	
	@FindBy(xpath = RateGridPage.ChargeForAdditionalAdultsAndChildern)
	public WebElement ChargeForAdditionalAdultsAndChildern;
	
	@FindBy(xpath = RateGridPage.CheckboxProrate)
	public WebElement CheckboxProrate;
	
	@FindBy(xpath = RateGridPage.VerifyDefaultCheckbox)
	public WebElement VerifyDefaultCheckbox;
	
	@FindBy(xpath = RateGridPage.AddMoreRoomClassesButton)
	public WebElement AddMoreRoomClassesButton;
	
	@FindBy(className = RateGridPage.CloseExpandRoomClassesList)
	public WebElement CloseExpandRoomClassesList;
	

	@FindBy(xpath = RateGridPage.OpenRateDropdown)
	public WebElement OpenRateDropdown;
	
	@FindBy(xpath = RateGridPage.SearchRatPlan)
	public WebElement searchRatPlan;
	
	@FindBy(xpath = RateGridPage.SelectRatePlan)
	public WebElement selectRatePlan;

	@FindBy(xpath = RateGridPage.SearchInput)
	public WebElement SearchInput;
	
	@FindBy(xpath = RateGridPage.SelectedRatePlan)
	public WebElement SelectedRatePlan;
	
	@FindBy(xpath = RateGridPage.ClickOnEditRatePlan)
	public WebElement ClickOnEditRatePlan;
	
	@FindBy(xpath = RateGridPage.ratePlanOverView)
	public WebElement ratePlanOverView;
	
	@FindBy(xpath = RateGridPage.ProRateCheckbox)
	public WebElement ProRateCheckbox;
	
	@FindBy(xpath = RateGridPage.RestricationsAndPoliciesTab)
	public WebElement RestricationsAndPoliciesTab;
	
	@FindBy(xpath = RateGridPage.SeasonTab)
	public WebElement SeasonTab;
	
	@FindBy(xpath = RateGridPage.SaveRatePlan)
	public WebElement SaveRatePlan;
	
	@FindBy(xpath = RateGridPage.ClickOnCapcity)
	public WebElement ClickOnCapcity;
	
	@FindBy(xpath = RateGridPage.LengthOfStay)
	public WebElement LengthOfStay;
	
	@FindBy(xpath = RateGridPage.ClickOnRightArrowOfDate)
	public WebElement ClickOnRightArrowOfDate;
		
	@FindBy(xpath = OR_RatesGrid.DerivedRateCondition)
	public WebElement DerivedRateCondition;

	@FindBy(xpath = OR_RatesGrid.DerivedRatePlanClasses)
	public List<WebElement> DerivedRatePlanClasses;

	@FindBy(xpath = RateGridPage.SelectedRatePlan)
	public WebElement selectedRatePlan;
	
	@FindBy(id = OR.RatesTab)
	public WebElement RatesTab;
	

	@FindBy(xpath = OR_RatesGrid.conditionsDescription)
	public WebElement conditionsDescription;	

	@FindBy(xpath = RateGridPage.checkboxMon)
	public WebElement monCheckbox;
	
	@FindBy(xpath = RateGridPage.checkboxTue)
	public WebElement tueCheckbox;
	
	@FindBy(xpath = RateGridPage.checkboxWed)
	public WebElement wedCheckbox;
	
	@FindBy(xpath = RateGridPage.checkboxThu)
	public WebElement thuCheckbox;
	
	@FindBy(xpath = RateGridPage.checkboxFri)
	public WebElement friCheckbox;
	
	@FindBy(xpath = RateGridPage.checkboxSat)
	public WebElement satCheckbox;
	
	@FindBy(xpath = RateGridPage.checkboxSun)
	public WebElement sunCheckbox;

	@FindBy(xpath = OR_RatesGrid.rateExAdExChPopup)
	public WebElement rateExAdExChPopup;
	
	@FindBy(xpath = RateGridPage.ProRateCheckboxMessage)
	public WebElement ProRateCheckboxMessage;
	
	@FindBy(xpath = OR_NightlyRatePlan.RatePlan_Season_AssignPolicyByRoomClass)
	public WebElement RatePlan_Season_AssignPolicyByRoomClass;
	
	@FindBy(xpath = RateGridPage.SaveRatePlanButton)
	public WebElement SaveRatePlanButton;
	
	@FindBy(xpath = OR_RatesGrid.totalOccupancyList)
	public List< WebElement >totalOccupancyList;
	
	@FindBy(xpath = ORRateGrid.StartAndAndDateICon)
	public List<WebElement> StartAndAndDateICon;
	
	@FindBy(xpath = OR_RateGrid.REMOVE_OVERRIDE_BUTTON)
	public WebElement REMOVE_OVERRIDE_BUTTON;

	@FindBy(xpath = RateGridPage.ProductTab)
	public WebElement ProductTab;
	
	@FindBy(xpath = RateGridPage.ToasterMessage)
	public WebElement ToasterMessage;
	
	@FindBy(css = ORRateGrid.GetRatesOverRide)
	public List<WebElement> GetRatesOverRide;

	@FindBy(xpath = OR_RatesGrid.roomClassLevelRatePerNight)
	public WebElement roomClassLevelRatePerNight;

	@FindBy(xpath = OR_RatesGrid.roomClassLevelExtraAdult)
	public WebElement roomClassLevelExtraAdult;

	@FindBy(xpath = OR_RatesGrid.roomClassLevelExtraChild)
	public WebElement roomClassLevelExtraChild;

	@FindBy(xpath = OR_RatesGrid.roomClassLevelRateSaveButton)
	public WebElement roomClassLevelRateSaveButton;
	
	@FindBy(xpath = OR_RatesGrid.roomClassLevelRateCloseButton)
	public WebElement roomClassLevelRateCloseButton;
	

	@FindBy(xpath = OR_RatesGrid.ruleSuccessMsgCloseIcon)
	public WebElement ruleSuccessMsgCloseIcon;
	
	@FindBy(xpath = OR_RatesGrid.bulkUpdateEnterNewRateRadioBtn)
	public WebElement bulkUpdateEnterNewRateRadioBtn;
	
	@FindBy(xpath = OR.rateGridBulkUpdateUpdateButton)
	public WebElement rateGridBulkUpdateUpdateButton;

	@FindBy(xpath = OR.rateGridBulkUpdateStartDate)
	public WebElement rateGridBulkUpdateStartDate;

	@FindBy(xpath = OR.rateGridBulkUpdateEndDate)
	public WebElement rateGridBulkUpdateEndDate;

	@FindBy(xpath = OR_RatesGrid.SAVE_RATE_PLAN)
	public WebElement SAVE_RATE_PLAN;


	@FindBy(xpath = ORRateGrid.OkButton)
	public WebElement OkButton;
	
	@FindBy(css = ORRateGrid.BulkUpdateCloseButton)
	public WebElement BulkUpdateCloseButton;
	
	@FindBy(xpath = ORRateGrid.NoDaysMatchAlert)
	public WebElement NoDaysMatchAlert;
	
	@FindBy(xpath = ORRateGrid.clickAddSecondSetOfRules)
	public WebElement clickAddSecondSetOfRules;

	@FindBy(xpath = OR_RatesGrid.CALENDAR_TAB)
	public WebElement CALENDAR_TAB;
	@FindBy(xpath = OR_RatesGrid.editThisSeason)
	public WebElement editThisSeason;
	
	@FindBy(xpath = OR_RatesGrid.GetBaseFromSourcePopup)
	public WebElement getBaseFromSourcePopup;
	
	@FindBy(xpath = OR_RatesGrid.GetAdultFromSourcePopup)
	public WebElement getAdultFromSourcePopup;
	
	@FindBy(xpath = OR_RatesGrid.GetChildFromSourcePopup)
	public WebElement getChildFromSourcePopup;
	
	@FindBy(xpath = OR_RatesGrid.assignPoliciesByRoomClass)
    public WebElement assignPoliciesByRoomClass;
	
	
	@FindBy(xpath = ORRateGrid.SeasonMonth)
	public List<WebElement> SeasonMonth;
	
	@FindBy(xpath = ORRateGrid.ProrateforeachSeasonCheckbox)
	public WebElement ProrateforeachSeasonCheckbox;
	
	@FindBy(xpath = ORRateGrid.ClickProrateforeachSeasonCheckbox)
	public WebElement ClickProrateforeachSeasonCheckbox;
	
	

}
