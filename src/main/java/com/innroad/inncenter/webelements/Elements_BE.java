package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Admin;
import com.innroad.inncenter.properties.OR_BE;

public class Elements_BE {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("BookingEngine");

	public Elements_BE(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);

	}

	@FindBy(id = OR_BE.ADULTS_BE)
	public WebElement adultsBE;

	@FindBy(id = OR_BE.CHILDREN_BE)
	public WebElement childrenBE;

	@FindBy(id = OR_BE.ADULTS_1_BE)
	public WebElement adults1BE;

	@FindBy(id = OR_BE.CHILDREN_1_BE)
	public WebElement children1BE;

	@FindBy(xpath = OR_BE.PROMOCODE_BE)
	public WebElement promocodeBE;

	@FindBy(id = OR_BE.ENTER_GROUP_NUMBER_FIELD_BE)
	public WebElement enterGroupNumBEr;

	@FindBy(id = OR_BE.FIRST_NAME_GUEST_INFO_BE)
	public WebElement firstNameBE;

	@FindBy(id = OR_BE.LAST_NAME_GUEST_INFO_BE)
	public WebElement lastNameBE;

	@FindBy(id = OR_BE.EMAIL_ADDRESS_GUEST_INFO_BE)
	public WebElement emailAddressBE;

	@FindBy(id = OR_BE.PHONE_NUMBER_GUEST_INFO_BE)
	public WebElement phoneNumberBE;

	@FindBy(id = OR_BE.HOME_ADDRESS_GUEST_INFO_BE)
	public WebElement homeAddressBE;

	@FindBy(id = OR_BE.HOME_APT_ADDRESS_GUEST_INFO_BE)
	public WebElement homeAptBE;

	@FindBy(id = OR_BE.ZIPCODE_GUEST_INFO_BE)
	public WebElement zipCodeBE;

	@FindBy(id = OR_BE.CITY_GUEST_INFO_BE)
	public WebElement cityBE;

	@FindBy(xpath = OR_BE.STATE_GUEST_INFO_BE)
	public WebElement stateBE;

	@FindBy(xpath = OR_BE.STATE_INPUT_FIELD_GUEST_INFO_BE)
	public WebElement stateInputFieldBE;

	@FindBy(id = OR_BE.CREDIT_CARD_NUMBER_PAYMENT_INFO_BE)
	public WebElement creditCardNumberBE;

	@FindBy(id = OR_BE.EXPIRY_DATE_PAYMENT_INFO_BE)
	public WebElement expirationDateBE;

	@FindBy(id = OR_BE.FULL_NAME_ON_CARD_PAYMENT_INFO_BE)
	public WebElement fullNameOnCardBE;

	@FindBy(id = OR_BE.CVV_PAYMENT_INFO_BE)
	public WebElement cvvBE;

	@FindBy(xpath = OR_BE.AGREE_ON_TRIP_SUMMARY_POLICY_BE)
	public WebElement agreeOnTermBE;

	@FindBy(xpath = OR_BE.BOOK_STAY_BUTTON_BE)
	public WebElement bookStayButtonBE;

	@FindBy(xpath = OR_BE.GO_BUTTON_BE)
	public WebElement goButtonBE;

	@FindBy(xpath = OR_BE.ADD_ROOM_BUTTON_BE)
	public WebElement addRoomButtonBE;

	@FindBy(xpath = OR_BE.SEARCH_ROOMS_BUTTON_BE)
	public WebElement searchRoomsButtonBE;

	@FindBy(xpath = OR_BE.ADVANCE_OPTIONS_BUTTON_BE)
	public WebElement advanceOptionsButtonBE;

	@FindBy(xpath = OR_BE.SELECT_WITH_RESERVATION_NUMBER_OPTION_BUTTON_BE)
	public WebElement selectReservationNumberButtonBE;

	@FindBy(xpath = OR_BE.SELECT_WITH_GROUP_NUMBER_OPTION_BUTTON_BE)
	public WebElement selectGroupNumberButtonBE;

	@FindBy(xpath = OR_BE.ENTER_GROUP_NUMBER_PAGE_TITLE_BE)
	public WebElement enterGroupNumberTitle;

	@FindBy(id = OR_BE.ENTER_GROUP_NUMBER_FIELD_BE)
	public WebElement enterGroupNumberFieldBE;

	@FindBy(xpath = OR_BE.RATEPLAN_NAME_BE)
	public WebElement ratePlanNameBE;

	@FindBy(xpath = OR_BE.RATEPLAN_CHARGE_BE)
	public WebElement ratePlanChargeBE;

	@FindBy(xpath = OR_BE.BOOK_ROOM_BUTTON_BE)
	public WebElement bookRoomButtonBE;

	@FindBy(xpath = OR_BE.RESERVATION_NUMBER_BE)
	public WebElement reservationNumberBE;

	@FindBy(xpath = OR_BE.RESERVATION_SUCCESS_MESSAGE_BE)
	public WebElement reservationSuccessMessageBE;

	@FindBy(xpath = OR_BE.SUB_TOTAL_BE)
	public WebElement subTotalBE;

	@FindBy(xpath = OR_BE.TAX_APPLIED_BE)
	public WebElement taxAppliedBE;

	@FindBy(xpath = OR_BE.STATES_LIST_BE)
	public List<WebElement> statesListBE;

	@FindBy(xpath = OR_BE.startDate)
	public WebElement startDate;

	@FindBy(xpath = OR_BE.getMonthHeading)
	public WebElement getMonthHeading;

	@FindBy(xpath = OR_BE.calendarRightArrow1)
	public List<WebElement> calendarRightArrow1;

	@FindBy(xpath = OR_BE.getMonthHeadingFromSecondMonth)
	public WebElement getMonthHeadingFromSecondMonth;
	
	@FindBy(xpath = OR_BE.SKIP_THIS_STEP)
	public WebElement SKIP_THIS_STEP;
	
	@FindBy(xpath = OR_BE.POLICIES)
	public WebElement POLICIES;
	
	@FindBy(xpath = OR_BE.POLICIES_LIST)
	public List<WebElement> POLICIES_LIST;
	
	@FindBy(xpath = OR_BE.startDateToolTipRateValue)
	public WebElement startDateToolTipRateValue;
	
	@FindBy(xpath = OR_BE.bookingEngineCheckInCalenderFooterFields)
	public List<WebElement> bookingEngineCheckInCalenderFooterFields;
	
	@FindBy(xpath = OR_BE.No_ROOMS_AVAILABLE_MESSAGE)
	public WebElement No_ROOMS_AVAILABLE_MESSAGE;
	
	@FindBy(xpath = OR_BE.endDate)
	public WebElement endDate;
	
	@FindBy(xpath = OR_BE.sameDayBookingToggleButton)
	public WebElement sameDayBookingToggleButton;
	
	@FindBy(xpath = OR_BE.ageRangeToggleButton)
	public WebElement ageRangeToggleButton;
	
	@FindBy(xpath = OR_BE.adultInput)
	public WebElement adultInput;
	
	@FindBy(xpath = OR_BE.childernInput)
	public WebElement childernInput;
	
	@FindBy(xpath = OR_BE.adultToolTipPath)
	public WebElement adultToolTipPath;
	
	@FindBy(xpath = OR_BE.childToolTipPath)
	public WebElement childToolTipPath;
	
	@FindBy(xpath = OR_BE.adultToolTipAgeValue)
	public WebElement adultToolTipAgeValue;
	
	@FindBy(xpath = OR_BE.childrenToolTipAgeValue)
	public WebElement childrenToolTipAgeValue;
	
	@FindBy(xpath = OR_BE.groupBookingsToggleButton)
	public WebElement groupBookingsToggleButton;
	
	@FindBy(xpath = OR_BE.advancedOption)
	public WebElement advancedOption;
	
	@FindBy(xpath = OR_BE.groupNumberLink)
	public WebElement groupNumberLink;
	
	@FindBy(xpath = OR_BE.groupNumberInput)
	public WebElement groupNumberInput;
	
	@FindBy(xpath = OR_BE.displayratesForCalender)
	public WebElement displayratesForCalender;
	
	@FindBy(xpath = OR_BE.saveButtonForCalender)
	public WebElement saveButtonForCalender;
	
	@FindBy(xpath = OR_BE.No_ROOMS_AVAILABLE_MESSAGE_AFTER_EDIT_DETAILS)
	public WebElement No_ROOMS_AVAILABLE_MESSAGE_AFTER_EDIT_DETAILS;
	
	@FindBy(xpath = OR_BE.No_ROOMS_AVAILABLE_CUSTOM_MESSAGE)
	public WebElement No_ROOMS_AVAILABLE_CUSTOM_MESSAGE;
	
	@FindBy(xpath = OR_BE.Tax)
	public WebElement Tax;
	
	@FindBy(xpath = OR_BE.Fees)
	public WebElement Fees;
	
	@FindBy(xpath = OR_BE.SubTotal)
	public WebElement SubTotal;	
	
	@FindBy(xpath = OR_BE.guestProfileToggleButton)
	public WebElement guestProfileToggleButton;
	
	@FindBy(xpath = OR_BE.COUNTRY_GUEST_INFO_BE)
	public WebElement COUNTRY_GUEST_INFO_BE;
	
	@FindBy(xpath = OR_BE.PromoCode)
	public WebElement PromoCode;
	
	@FindBy(xpath = OR_BE.PromoCodeToggleButton)
	public WebElement PromoCodeToggleButton;

	@FindBy(xpath = OR_BE.savePromoCodeToggle)
	public WebElement savePromoCodeToggle;

}