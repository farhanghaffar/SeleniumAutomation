package com.innroad.inncenter.properties;

public class OR_BE {

	public static final String WELCOME_PAGE_BE_TITLE = "//h2[contains(text()='Welocme')]";
	public static final String ADULTS_BE = "adults";
	public static final String CHILDREN_BE = "children";

	public static final String ADULTS_1_BE = "adults1";
	public static final String CHILDREN_1_BE = "children1";
	public static final String PROMOCODE_BE = "//input[@name='promoCode']";
	public static final String ADD_ROOM_BUTTON_BE = "//div[contains(@class,'addRoomsBtn')]";
	public static final String SEARCH_ROOMS_BUTTON_BE = "//button[@type='submit']";
	public static final String ADVANCE_OPTIONS_BUTTON_BE = "//button[contains(@class,'advancedDropdown')]";
	public static final String SELECT_WITH_RESERVATION_NUMBER_OPTION_BUTTON_BE = "//a[contains(text(),'Reservation Number')]";
	public static final String ENTER_GROUP_NUMBER_PAGE_TITLE_BE = "//h2[contains(text(),'Please Enter Your Group Number')]";
	public static final String SELECT_WITH_GROUP_NUMBER_OPTION_BUTTON_BE = "//a[contains(text(),'Group Number')]";
	public static final String GO_BUTTON_BE = "//button[contains(@class,'searchForm__submitButton')]";
	public static final String ENTER_GROUP_NUMBER_FIELD_BE = "reservationGroupNumber";

	public static final String FIRST_NAME_GUEST_INFO_BE = "firstName";
	public static final String LAST_NAME_GUEST_INFO_BE = "lastName";
	public static final String EMAIL_ADDRESS_GUEST_INFO_BE = "emailAddress";
	public static final String PHONE_NUMBER_GUEST_INFO_BE = "phoneNumber";
	public static final String HOME_ADDRESS_GUEST_INFO_BE = "homeAddress";
	public static final String HOME_APT_ADDRESS_GUEST_INFO_BE = "homeApt";
	public static final String ZIPCODE_GUEST_INFO_BE = "homeZipCode";
	public static final String CITY_GUEST_INFO_BE = "homeCity";
	public static final String STATE_GUEST_INFO_BE = "//button[contains(@data-id,'homeState')]";
	public static final String COUNTRY_GUEST_INFO_BE ="//button[contains(@data-id,'homeCountry')]";
	public static final String STATE_INPUT_FIELD_GUEST_INFO_BE = "(//div[@class='bs-searchbox'])[2]//input";

	public static final String CREDIT_CARD_NUMBER_PAYMENT_INFO_BE = "creditCardNumber";
	public static final String EXPIRY_DATE_PAYMENT_INFO_BE = "expirationDate";
	public static final String FULL_NAME_ON_CARD_PAYMENT_INFO_BE = "cardFullName";
	public static final String CVV_PAYMENT_INFO_BE = "cvv";
	public static final String AGREE_ON_TRIP_SUMMARY_POLICY_BE = "//input[@id='agreeTripSummary']/parent::span";
	public static final String BOOK_STAY_BUTTON_BE = "//span[text()='Book Stay']/parent::button[contains(@class,'searchForm__submitButton')]";

	public static final String RATEPLAN_NAME_BE = "//span[contains(@class,'ratePlanName')]";
	public static final String RATEPLAN_CHARGE_BE = "(//div[contains(@class,'rateCard')]//div[contains(@class,'RateCharge')]//span[contains(@class,'pretaxTotal')])[1]";
	public static final String BOOK_ROOM_BUTTON_BE = "//button[contains(@class,'rate__btnBook')]";
	public static final String RESERVATION_NUMBER_BE = "//h4[contains(@class,'reservationNumber')]";
	public static final String RESERVATION_SUCCESS_MESSAGE_BE = "//h2[contains(@class,'reservationSuccess__title')]";
	public static final String SUB_TOTAL_BE = "//span[contains(@class,'total')]";
	public static final String TAX_APPLIED_BE = "//p[contains(@class,'tax')]//span[contains(@class,'checkout__price')]";
	public static final String STATES_LIST_BE = "//div[contains(@class,'homeState')]//ul//li//a//span[@class='text']";
	
	public static final String startDate = "//input[@id='startDate']";
	public static final String getMonthHeading = "(//div[contains(@class,'CalendarMonth_caption_1')]//strong)[2]";
	public static final String getMonthHeadingFromSecondMonth = "(//div[contains(@class,'CalendarMonth_caption_1')]//strong)[3]";
	public static final String calendarRightArrow1 = "//i[@class='be-icons-chevron-right']";
	public static final String SKIP_THIS_STEP = "//div[text()='Skip this step']";
	public static final String POLICIES ="//h4[text()='Policies']";
	public static final String POLICIES_LIST ="//h4[text()='Policies']//../ul/li";
	//public static final String startDateToolTipRateValue = "//header[text()='Check in']//following-sibling::div/span";
	public static final String startDateToolTipRateValue = "//header[text()='Check in']/../div/span";
	
	public static final String bookingEngineCheckInCalenderFooterFields= "//div[@class='dateRangePicker__legendBox___fjCgP DayPicker_footer']/ul/li";
	public static final String SETTINGS_LINK = "//div[text()='Settings']";
	public static final String SEARCH_DISPLAYED_RATE="//div[text()='Rate Displayed']/../../following-sibling::div//span[2]";
	
	public static final String ENTIRE_AVALABILITY = "//a[text()='View Our Entire Availability']";
	public static final String AVAILABILITY_MONTH ="//div[@class='Grid__navMonth___xJoKW']/div[1]";
	public static final String AVAILABILITY_BACKWARD = "//i[@class='glyphicon glyphicon-menu-left']//parent::button";
	public static final String AVAILABILITY_FORWARD = "//i[@class='glyphicon glyphicon-menu-right']//parent::button";
	public static final String AVAILABILITY_ROOM_CLASSES="//div[@class='GridRoomInfo__roomName___ruS4C']";
	
	public static final String CHECK_IN="//h5[text()='Check in']";
	public static final String CHECK_IN_DATE="//h5[text()='Check in']//following-sibling::p";
	public static final String CHECK_OUT="//h5[text()='Check out']";
	public static final String CHECK_OUT_DATE="//h5[text()='Check out']//following-sibling::p";
	public static final String ADULTS="//h5[text()='Adults']";
	public static final String NO_OF_ADULTS="//h5[text()='Adults']//following-sibling::div/input";
	public static final String CHILDREN="//h5[text()='Children']";
	public static final String NO_OF_CHILDREN="//h5[text()='Children']//following-sibling::div/input";
	public static final String VIEW_RATES_FROM="//button[@type='submit']/span";
	public static final String No_ROOMS_AVAILABLE_MESSAGE = "//p/../a[text()='New Search']";
	
	public static final String endDate = " //input[@id='endDate']";
	public static final String sameDayBookingToggleButton = "//h3[text()='Same-Day Bookings']/../..//div[2]//button";
	public static final String ageRangeToggleButton ="//label[text()='Display age range']/../..//button";
	public static final String adultInput = "//label[text()='Adults']/../..//input";
	public static final String childernInput = "//label[text()='Children']/../..//inputt";
	public static final String adultToolTipPath = "//span[text()='Adults']//following-sibling::span/div";
	public static final String childToolTipPath = "//span[text()='Children']//following-sibling::span/div";
	public static final String adultToolTipAgeValue = "//span[text()='Adults']//following-sibling::span/div/div/div[1]";
	public static final String childrenToolTipAgeValue = "//span[text()='Children']//following-sibling::span/div/div/div[1]";
	public static final String groupBookingsToggleButton = "//h3[text()='Group Bookings']/../../..//button";
	public static final String advancedOption = "//button[@class='ADA__dropDownBtn dropdown-toggle btn DropDown__advancedDropdown___34OjK']/i";
	public static final String groupNumberLink = "//a[text()='I have a Group Number']";
	public static final String groupNumberPopUp = "//h2[text()='Please Enter Your Group Number']";
	public static final String groupNumberInput = "//input[@id='reservationGroupNumber']";
	public static final String displayratesForCalender = "//h3[text()='Calendars']/../..//buttonn";
	public static final String saveButtonForCalender = "//h3[text()='Calendars']/../..//button[@type='submit']";
	
	public static final String CHECKOUT_FIELD = "//input[@id='endDate']";
	public static final String EMAIL_AND_PHONE = "//a[contains(@class,'header__mail')]";
	public static final String PROPERTY_HEADER = "//a[contains(@class,'header__mainHeaderLogo')]";
	public static final String LANGUAGE_DROPDOWN = "//button[@aria-label='Select Language']";
	public static final String DETAILS_POPUP = "//form[contains(@class,'RatePopup__ratePopup')]";
	public static final String PROMO_CODE="//h5[text()='Promo Code']//following-sibling::div/input";

	public static final String No_ROOMS_AVAILABLE_MESSAGE_AFTER_EDIT_DETAILS = "//p[contains(@class,'room__noAvailableText')]";
	public static final String No_ROOMS_AVAILABLE_CUSTOM_MESSAGE = "//div[contains(@class,'noRoomsAvailable')]//h4";
	public static final String Tax = "//span[text()='Taxes']//following-sibling::span";
	public static final String Fees = "//span[text()='Fees']//following-sibling::span";
	public static final String SubTotal = "//span[@class='CA__total']";
	
	public static final String availabilityGridToggleButton = "//h3[text()='Calendars']//following-sibling::div[4]/div/div[2]/button";
	public static final String haveAReservationNumberLink = "//a[text()='I know My Reservation Number']";
	public static final String backButton = "//span[text()='Back']";
	public static final String blocksInBE ="//h2[text()='Please pick your block']";
	public static final String guestProfileToggleButton = "//h3[text()='Guest Profiles']/../..//button";
	public static final String PromoCodeToggleButton = "//label[text()='Display Promocode']/../..//button";
	

	public static final String PromoCode = "//p[contains(@class,'promoCode')]";
	public static final String savePromoCodeToggle = "//button[@id='searchOptions_enableDisplayPromocode']/../../../../../..//button[@type='submit']";
	public static final String NoRoomWithInvalidPromoCode = "//div[contains(@class,'search__noPromoCodeNewSearch')]";

	
}
