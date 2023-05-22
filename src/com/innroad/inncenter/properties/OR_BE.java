package com.innroad.inncenter.properties;

public class OR_BE {

	public static final String WELCOME_PAGE_BE_TITLE = "//h2[contains(text()='Welocme')]";
	public static final String ADULTS_BE = "adults";
	public static final String CHILDREN_BE = "children";

	public static final String ADULTS_1_BE = "adults1";
	public static final String CHILDREN_1_BE = "children1";
	public static final String PROMOCODE_BE = "promocode";
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

}
