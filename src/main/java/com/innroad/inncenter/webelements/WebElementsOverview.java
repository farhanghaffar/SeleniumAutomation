package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.ORRateGrid;
import com.innroad.inncenter.properties.OR_RatesGrid;

public class WebElementsOverview {

	public static Logger app_logs = Logger.getLogger("devpinoyLogger");

	WebDriver driver;

	public WebElementsOverview(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}

	@FindBy(xpath = OR.GoButtonInventory)
	public WebElement GoButtonInventory;

	@FindBy(xpath = OR.Calendar)
	public WebElement Calendar;

	@FindBy(xpath = OR.Edit_Rate)
	public WebElement Edit_Rate;

	@FindBy(xpath = OR.Rates_Override_Info_Popup)
	public WebElement Rates_Override_Info_Popup;

	@FindBy(xpath = OR.First_Roomclass_P1_Rate)
	public WebElement First_Roomclass_P1_Rate;

	@FindBy(xpath = OR.First_DBR_Roomclass_P1_Rate)
	public WebElement First_DBR_Roomclass_P1_Rate;

	@FindBy(xpath = OR.Save_Btn_In_Rates_Override_Info_Popup)
	public WebElement Save_Btn_In_Rates_Override_Info_Popup;

	@FindBy(xpath = OR.Done_Btn_In_Rates_Override_Info_Popup)
	public WebElement Done_Btn_In_Rates_Override_Info_Popup;

	@FindBy(xpath = OR.Cancel_Btn_In_Rates_Override_Info_Popup)
	public WebElement Cancel_Btn_In_Rates_Override_Info_Popup;

	@FindBy(xpath = OR.Get_Rack_Rate)
	public WebElement Get_Rack_Rate;

	@FindBy(xpath = OR.RoomClassNameWithRates)
	public List<WebElement> RoomClassNameWithRates;

	@FindBy(xpath = OR.roomClassAbbreviation)
	public List<WebElement> roomClassAbbreviation;	
	
	@FindBy(xpath = OR.PolicySuite_EvenOutFieldToday)
	public WebElement PolicySuite_EvenOutFieldToday;

	@FindBy(xpath = OR.OverViewSaveBtn)
	public WebElement OverViewSaveBtn;

	@FindBy(xpath = OR.EditOverRatePopup)
	public WebElement EditOverRatePopup;

	@FindBy(xpath = OR.Overview_MaxPerson)
	public List<WebElement> Overview_MaxPersons;

	@FindBy(xpath = OR.Overview_GoButton)
	public WebElement Overview_GoButton;

	@FindBy(xpath = OR.SelectTodaysDate)
	public WebElement SelectTodaysDate;

	@FindBy(xpath = OR.RateOverridenInfo_GoButton)
	public WebElement RateOverridenInfo_GoButton;

	@FindBy(xpath = OR.ActivDate)
	public WebElement ActivDate;

	@FindBy(css = OR.GetTodayDate)
	public WebElement GetTodayDate;

	@FindBy(xpath = OR.DateSelection_EditRatePopup)
	public List<WebElement> DateSelection_EditRatePopup;

	@FindBy(xpath = OR.SelectDate_From)
	public WebElement SelectDate_From;

	@FindBy(xpath = OR.DateSelectionFrom_OverviewPanel)
	public List<WebElement> DateSelectionFrom_OverviewPanel;

	@FindBy(id = OR.SelectRatePlan_EditRate)
	public WebElement SelectRatePlan_EditRate;

	@FindBy(id = OR.SelectRoomClass_EditRate)
	public WebElement SelectRoomClass_EditRate;

	@FindBy(name = OR.GoButton_EditRate)
	public WebElement GoButton_EditRate;

	@FindBy(xpath = OR.OverViewTodayButton)
	public WebElement OverViewTodayButton;

	@FindBy(xpath = OR.OverView_DateStart)
	public WebElement OverView_DateStart;

	@FindBy(xpath = OR.SelectRatePlan_Overview)
	public WebElement SelectRatePlan_Overview;
	
	@FindBy(xpath = OR.RatePopup)
	public WebElement RatePopup;

	@FindBy(xpath = OR.RatePopup_RoomRate)
	public WebElement RatePopup_RoomRate;

	@FindBy(xpath = OR.RatePopup_ExtraAdult)
	public WebElement RatePopup_ExtraAdult;

	@FindBy(xpath = OR.RatePopup_ExtraChild)
	public WebElement RatePopup_ExtraChild;

	@FindBy(xpath = OR.ratePopupFrame)
	public WebElement ratePopupFrame;

	@FindBy(xpath = OR.ratePopupBaseAmount)
	public WebElement ratePopupBaseAmount;

	@FindBy(xpath = OR.ratePopupExtraAdults)
	public WebElement ratePopupExtraAdults;

	@FindBy(xpath = OR.ratePopupExtraChild)
	public WebElement ratePopupExtraChild;

	@FindBy(xpath = OR.ratePopupCancel)
	public WebElement ratePopupCancel;
	
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
	
	@FindBy(xpath = OR.EndDateInput)
	public WebElement EndDateInput;

	@FindBy(xpath = OR.TotalOccupancy)
	public WebElement TotalOccupancy;

	@FindBy(xpath = OR.TotalOccupancyType)
	public WebElement TotalOccupancyType;

	@FindBy(xpath = OR.OccupancyIcon)
	public WebElement OccupancyIcon;
	
	@FindBy(xpath = OR.OccupancyClause)
	public WebElement OcupancyClause;
	
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

	@FindBy(xpath = OR.TotalOccupancyText)
	public WebElement TotalOccupancyText;
	
	@FindBy(xpath = OR.TotalOccupancyTypeVisibility)
	public WebElement TotalOccupancyTypeVisibility;
	
	@FindBy(xpath = OR.UpdatExistingRules)
	public WebElement UpdatExistingRules;

	@FindBy(xpath = OR.UpdateAvailability)
	public WebElement UpdateAvailability;
	
	@FindBy(xpath = OR.TodayDateButton)
	public WebElement TodayDateButton;
	
	@FindBy(xpath = OR.StartDateSelectedDay)
	public WebElement StartDateSelectedDay;
	
	@FindBy(xpath = OR.EndDateSelectedDay)
	public WebElement EndDateSelectedDay;

	@FindBy(xpath = OR.Button_YesUpdate)
	public WebElement Button_YesUpdate;
	
	@FindBy(xpath = ORRateGrid.ratePlanNames)
	public List<WebElement> ratePlanNames;
	
	@FindBy(xpath = ORRateGrid.calendarIcon)
	public WebElement calendarIcon;
	
	@FindBy(xpath = ORRateGrid.calendarMonthHeading)
	public WebElement calendarMonthHeading;

	@FindBy(xpath = ORRateGrid.calendarRightArrow)
	public WebElement calendarRightArrow;
	
	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupCheckinInput)
	public WebElement bulkUpdatePopupCheckinInput;
	
	@FindBy(xpath = OR_RatesGrid.bulkUpdatePopupCheckoutInput)
	public WebElement bulkUpdatePopupCheckoutInput;
	
	@FindBy(xpath = OR.checkOut)
	public WebElement checkOut;
	
	@FindBy(xpath = OR.closeRulesPopup)
	public WebElement closeRulesPopup;
	
	@FindBy(xpath = OR.checkin)
	public WebElement checkin;
	
	@FindBy(xpath = OR.occupancyIcon)
	public WebElement occupancyIcon;
	
	
	
	
	
	
	
	

	
}
