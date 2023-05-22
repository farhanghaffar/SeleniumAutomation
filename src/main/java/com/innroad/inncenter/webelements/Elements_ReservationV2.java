package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;

import com.innroad.inncenter.properties.OR_ReservationV2;

public class Elements_ReservationV2 {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("logger");

	public Elements_ReservationV2(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(xpath = OR_ReservationV2.SB_GUEST_NAME)
	public WebElement SB_GUEST_NAME;
	
	@FindBy(xpath = OR_ReservationV2.SB_RESERVATION_STATUS)
	public WebElement SB_RESERVATION_STATUS;
	
	@FindBy(xpath = OR_ReservationV2.SB_CONFIRMATION_NO)
	public WebElement SB_CONFIRMATION_NO;
	
	@FindBy(xpath = OR_ReservationV2.SB_STAY_DURATION)
	public WebElement SB_STAY_DURATION;
	
	@FindBy(xpath = OR_ReservationV2.SB_PHONE_NO)
	public WebElement SB_PHONE_NO;
	
	@FindBy(xpath = OR_ReservationV2.SB_EMAIL)
	public WebElement SB_EMAIL;
	
	@FindBy(xpath = OR_ReservationV2.SB_TRIP_SUMMARAY)
	public WebElement SB_TRIP_SUMMARAY;
	
	@FindBy(xpath = OR_ReservationV2.SB_BALANCE)
	public WebElement SB_BALANCE;
	
	@FindBy(xpath = OR_ReservationV2.SI_PROPERTY_NAME)
	public WebElement SI_PROPERTY_NAME;
	
	@FindBy(xpath = OR_ReservationV2.SI_RATE_PLAN_NAME)
	public WebElement SI_RATE_PLAN_NAME;
	
	@FindBy(xpath = OR_ReservationV2.SI_CHECK_IN_DATE)
	public WebElement SI_CHECK_IN_DATE;
	
	@FindBy(xpath = OR_ReservationV2.SI_CHECK_OUT_DATE)
	public WebElement SI_CHECK_OUT_DATE;
	
	@FindBy(xpath = OR_ReservationV2.SI_NUMBER_OF_NIGHTS)
	public WebElement SI_NUMBER_OF_NIGHTS;
	
	@FindBy(xpath = OR_ReservationV2.SI_NUMBER_OF_ADULTS)
	public WebElement SI_NUMBER_OF_ADULTS;
	
	@FindBy(xpath = OR_ReservationV2.SI_NUMBER_OF_CHILDS)
	public WebElement SI_NUMBER_OF_CHILDS;
	
	@FindBy(xpath = OR_ReservationV2.SI_ROOMCLASS_NAME)
	public WebElement SI_ROOMCLASS_NAME;
	
	@FindBy(xpath = OR_ReservationV2.SI_ROOM_NUMBER)
	public WebElement SI_ROOM_NUMBER;
	
	@FindBy(xpath = OR_ReservationV2.SI_ROOM_TOTAL)
	public WebElement SI_ROOM_TOTAL;
	
	@FindBy(xpath = OR_ReservationV2.GI_GUEST_NAME)
	public WebElement GI_GUEST_NAME;
	
	@FindBy(xpath = OR_ReservationV2.GI_CONTACT_NAME)
	public WebElement GI_CONTACT_NAME;
	
	@FindBy(xpath = OR_ReservationV2.GI_EMAIL)
	public WebElement GI_EMAIL;
	
	@FindBy(xpath = OR_ReservationV2.GI_PHONE_NO)
	public WebElement GI_PHONE_NO;
	
	@FindBy(xpath = OR_ReservationV2.GI_ALTERNATE_PHONE)
	public WebElement GI_ALTERNATE_PHONE;
	
	@FindBy(xpath = OR_ReservationV2.GI_ADDRESS)
	public WebElement GI_ADDRESS;
	
	@FindBy(xpath = OR_ReservationV2.GI_CITY)
	public WebElement GI_CITY;
	
	@FindBy(xpath = OR_ReservationV2.GI_STATE)
	public WebElement GI_STATE;
	
	@FindBy(xpath = OR_ReservationV2.GI_POSTAL_CODE)
	public WebElement GI_POSTAL_CODE;
	
	@FindBy(xpath = OR_ReservationV2.GI_COUNTRY)
	public WebElement GI_COUNTRY;
	
//	@FindBy(xpath = OR_ReservationV2.GI_FAX)
//	public WebElement GI_FAX;
	
	@FindBy(xpath = OR_ReservationV2.GI_ACCOUNT)
	public WebElement GI_ACCOUNT;
	
	@FindBy(xpath = OR_ReservationV2.TS_ROOM_CHARGE)
	public WebElement TS_ROOM_CHARGE;
	
	@FindBy(xpath = OR_ReservationV2.TS_INCIDENTALS)
	public WebElement TS_INCIDENTALS;
	
	@FindBy(xpath = OR_ReservationV2.TS_FEES)
	public WebElement TS_FEES;
	
	@FindBy(xpath = OR_ReservationV2.TS_TAXES)
	public WebElement TS_TAXES;
	
	@FindBy(xpath = OR_ReservationV2.TS_TRIP_TOTAL)
	public WebElement TS_TRIP_TOTAL;
	
	@FindBy(xpath = OR_ReservationV2.TS_PAID)
	public WebElement TS_PAID;
	
	@FindBy(xpath = OR_ReservationV2.TS_BALANCE)
	public WebElement TS_BALANCE;
		
	@FindBy(xpath = OR_ReservationV2.PI_BILLING_TYPE_NAME)
	public WebElement PI_BILLING_TYPE_NAME;
	
	@FindBy(xpath = OR_ReservationV2.PI_LAST_FOUR_DIGITS)
	public WebElement PI_LAST_FOUR_DIGITS;
	
	@FindBy(xpath = OR_ReservationV2.PI_NAME_ON_CARD)
	public WebElement PI_NAME_ON_CARD;
	
	@FindBy(xpath = OR_ReservationV2.PI_EXPIRY_DATE)
	public WebElement PI_EXPIRY_DATE;
	
	@FindBy(xpath = OR_ReservationV2.MI_MARKET_SEGMENT)
	public WebElement MI_MARKET_SEGMENT;
		
	@FindBy(xpath = OR_ReservationV2.MI_MARKET_REFERRAL)
	public WebElement MI_MARKET_REFERRAL;
	
	@FindBy(xpath = OR_ReservationV2.MI_EXTERNAL_CONFIRMATION_NUMBER)
	public WebElement MI_EXTERNAL_CONFIRMATION_NUMBER;
	
	@FindBy(xpath = OR_ReservationV2.MI_MARKET_SOURCE)
	public WebElement MI_MARKET_SOURCE;
	
	@FindBy(xpath = OR_ReservationV2.MI_MARKET_SUB_SOURCE)
	public WebElement MI_MARKET_SUB_SOURCE;
	
	@FindBy(xpath = OR_ReservationV2.DEPOSIT_POLICY_TOGGLE)
	public WebElement DEPOSIT_POLICY_TOGGLE;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_POLICY_TOGGLE)
	public WebElement CHECK_IN_POLICY_TOGGLE;
	
	@FindBy(xpath = OR_ReservationV2.NO_SHOW_POLICY_TOGGLE)
	public WebElement NO_SHOW_POLICY_TOGGLE;
	
	@FindBy(xpath = OR_ReservationV2.DEPOSIT_POLICY_TITLE)
	public WebElement DEPOSIT_POLICY_TITLE;
	
	@FindBy(xpath = OR_ReservationV2.DEPOSIT_POLICY_STATEMENT)
	public WebElement DEPOSIT_POLICY_STATEMENT;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_POLICY_TITLE)
	public WebElement CHECK_IN_POLICY_TITLE;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_POLICY_STATEMENT)
	public WebElement CHECK_IN_POLICY_STATEMENT;
	
	@FindBy(xpath = OR_ReservationV2.NO_SHOW_POLICY_TITLE)
	public WebElement NO_SHOW_POLICY_TITLE;
	
	@FindBy(xpath = OR_ReservationV2.NO_SHOW_POLICY_STATEMENT)
	public WebElement NO_SHOW_POLICY_STATEMENT;
	
	@FindBy(xpath = OR_ReservationV2.CANCELLATION_POLICY_TITLE)
	public WebElement CANCELLATION_POLICY_TITLE;
	
	@FindBy(xpath = OR_ReservationV2.CANCELLATION_POLICY_STATEMENT)
	public WebElement CANCELLATION_POLICY_STATEMENT;
	
	@FindBy(xpath = OR_ReservationV2.NOTES)
	public List<WebElement> NOTES_LIST;
	
	@FindBy(xpath = OR_ReservationV2.FOLIO_TABLE_RECORD)
	public List<WebElement> FOLIO_TABLE_RECORD;
	
	@FindBy(xpath = OR_ReservationV2.DETAIL_TAB)
	public WebElement DETAIL_TAB;
	
	@FindBy(xpath = OR_ReservationV2.FOLIO_TAB )
	public WebElement FOLIO_TAB;
	
	@FindBy(xpath = OR_ReservationV2.HISTORY_TAB)
	public WebElement HISTORY_TAB;
	
	@FindBy(xpath = OR_ReservationV2.DOCUMENT_TAB)
	public WebElement DOCUMENT_TAB;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_NAME_SEARCH)
	public WebElement GUEST_NAME_SEARCH;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_RES_NO_SEARCH)
	public WebElement  GUEST_RES_NO_SEARCH;
	
	@FindBy(xpath = OR_ReservationV2.RES_SEARCH)
	public WebElement  RES_SEARCH;
	
	@FindBy(xpath = OR_ReservationV2.FIRST_SEARCHED_RES)
	public WebElement  FIRST_SEARCHED_RES;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_CHECK_IN_BUTTON)
	public WebElement RESERVATION_CHECK_IN_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_CHECKIN_GUEST_REGISTRATION_REPORT_TOGGLE)
	public WebElement RESERVATION_CHECKIN_GUEST_REGISTRATION_REPORT_TOGGLE;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_CONFIRM_CHECK_IN_PAYMENT_BUTTON)
	public WebElement RESERVATION_CONFIRM_CHECK_IN_PAYMENT_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON)
	public WebElement RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_CHECK_IN_PAYMENT_PRPCESS_BUTTON)
	public WebElement RESERVATION_CHECK_IN_PAYMENT_PROCESS_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_CHECK_IN_POPUP_CLOSE)
	public WebElement RESERVATION_CHECK_IN_POPUP_CLOSE;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_GUEST_INFO_EDIT)
	public WebElement RESERVATION_GUEST_INFO_EDIT;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_GUEST_INFO_PHONE_NO)
	public WebElement RESERVATION_GUEST_INFO_PHONE_NO;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_GUEST_INFO_SAVE_BUTTON)
	public WebElement RESERVATION_GUEST_INFO_SAVE_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYMENT_POLICY_NAME)
	public WebElement CHECK_IN_PAYMENT_POLICY_NAME;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYPMENT_BALANCE)
	public WebElement CHECK_IN_PAYPMENT_BALANCE;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYMENT_AMOUNT)
	public WebElement CHECK_IN_PAYMENT_AMOUNT;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYMENT_TYPE)
	public WebElement CHECK_IN_PAYMENT_TYPE;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYMENT_SUCCESS_POLICY_NAME)
	public WebElement CHECK_IN_PAYMENT_SUCCESS_POLICY_NAME;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYPMENT_SUCCESS_BALANCE)
	public WebElement CHECK_IN_PAYPMENT_SUCCESS_BALANCE;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYMENT_SUCCESS_AMOUNT)
	public WebElement CHECK_IN_PAYMENT_SUCCESS_AMOUNT;
	
	@FindBy(xpath = OR_ReservationV2.CHECK_IN_PAYMENT_SUCCESS_TYPE)
	public WebElement CHECK_IN_PAYMENT_SUCCESS_TYPE;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_RESERVATION_POLICY_NAME)
	public WebElement CANCEL_RESERVATION_POLICY_NAME;
	
	@FindBy(xpath = OR_ReservationV2.ADD_CANCELATION_REASON)
	public WebElement ADD_CANCELATION_REASON;
	
	@FindBy(xpath = OR_ReservationV2.PROCEED_TO_CANCELLATION_BUTTON)
	public WebElement PROCEED_TO_CANCELLATION_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.ON_CONFIRM_CANCELLATION_BUTTON)
	public WebElement ON_CONFIRM_CANCELLATION_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_VOID_ROOM_CHARGES)
	public WebElement CANCEL_VOID_ROOM_CHARGES;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYPMENT_BALANCE)
	public WebElement CANCEL_PAYPMENT_BALANCE;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYMENT_AMOUNT)
	public WebElement CANCEL_PAYMENT_AMOUNT;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYMENT_TYPE)
	public WebElement CANCEL_PAYMENT_TYPE;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYMENT_SUCCESS_POLICY_NAME)
	public WebElement CANCEL_PAYMENT_SUCCESS_POLICY_NAME;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYPMENT_SUCCESS_BALANCE)
	public WebElement CANCEL_PAYPMENT_SUCCESS_BALANCE;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYMENT_SUCCESS_AMOUNT)
	public WebElement CANCEL_PAYMENT_SUCCESS_AMOUNT;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYMENT_SUCCESS_TYPE)
	public WebElement CANCEL_PAYMENT_SUCCESS_TYPE;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_PAYMENT_POPUP_CLOSE)
	public WebElement CANCEL_PAYMENT_POPUP_CLOSE;
	
	@FindBy(xpath = OR_ReservationV2.CANCEL_MRB_SECONDARY)
	public WebElement CANCEL_MRB_SECONDARY;	
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservationButton)
	public WebElement  CP_NewReservationButton;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_CheckInDate)
	public WebElement  CP_NewReservation_CheckInDate;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_CheckOutDate)
	public WebElement  CP_NewReservation_CheckOutDate;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Adults)
	public WebElement  CP_NewReservation_Adults;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Children)
	public WebElement  CP_NewReservation_Children;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Rateplan)
	public WebElement  CP_NewReservation_Rateplan;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_PromoCode)
	public WebElement  CP_NewReservation_PromoCode;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_SplitReservation)
	public WebElement  CP_NewReservation_SplitReservation;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_FindRooms)
	public WebElement  CP_NewReservation_FindRooms;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_AddRoom)
	public WebElement  CP_NewReservation_AddRoom;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_DepositAmount)
	public WebElement CP_NewReservation_DepositAmount;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_OverrideDeposit)
	public WebElement CP_NewReservation_OverrideDeposit;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_OverrideDepositAmoount)
	public WebElement CP_NewReservation_OverrideDepositAmoount;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_TripTotalAmount)
	public WebElement CP_NewReservation_TripTotalAmount;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Next)
	public WebElement CP_NewReservation_Next;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_GuestSalutation)
	public WebElement CP_NewReservation_GuestSalutation;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_GuestFirstName)
	public WebElement CP_NewReservation_GuestFirstName;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_GuestLastName)
	public WebElement CP_NewReservation_GuestLastName;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ContactSalutation)
	public WebElement CP_NewReservation_ContactSalutation;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ContactFirstName)
	public WebElement CP_NewReservation_ContactFirstName;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ContactLastName)
	public WebElement CP_NewReservation_ContactLastName;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Phone)
	public WebElement CP_NewReservation_Phone;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Phone2)
	public WebElement CP_NewReservation_Phone2;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Email)
	public WebElement CP_NewReservation_Email;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_AltenativePhone)
	public WebElement CP_NewReservation_AltenativePhone;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Account)
	public WebElement CP_NewReservation_Account;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Address1)
	public WebElement CP_NewReservation_Address1;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Address2)
	public WebElement CP_NewReservation_Address2;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Address3)
	public WebElement CP_NewReservation_Address3;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_City)
	public WebElement CP_NewReservation_City;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Country)
	public WebElement CP_NewReservation_Country;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_State)
	public WebElement CP_NewReservation_State;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_PostalCode)
	public WebElement CP_NewReservation_PostalCode;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_CreateGuestProfileCheckbox)
	public WebElement CP_NewReservation_CreateGuestProfileCheckbox;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_CreateGuestProfile)
	public WebElement CP_NewReservation_CreateGuestProfile;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_AddMoreGuestInfo)
	public WebElement CP_NewReservation_AddMoreGuestInfo;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_PaymentMethod)
	public WebElement CP_NewReservation_PaymentMethod;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_CardNumber)
	public WebElement CP_NewReservation_CardNumber;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_NameOnCard)
	public WebElement CP_NewReservation_NameOnCard;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_CardExpDate)
	public WebElement CP_NewReservation_CardExpDate;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Swipe)
	public WebElement CP_NewReservation_Swipe;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_BillingNotes)
	public WebElement CP_NewReservation_BillingNotes;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_TaxExemptionOption)
	public WebElement CP_NewReservation_TaxExemptionOption;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_TaxExempt)
	public WebElement CP_NewReservation_TaxExempt;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_TaxExemptID)
	public WebElement CP_NewReservation_TaxExemptID;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_SameAsMailingAddress)
	public WebElement CP_NewReservation_SameAsMailingAddress;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_GiftCertNumber)
	public WebElement CP_NewReservation_GiftCertNumber;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_HouseAccountName)
	public WebElement CP_NewReservation_HouseAccountName;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ResNumberPayment)
	public WebElement CP_NewReservation_ResNumberPayment;
		

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_TravelAgent)
	public WebElement CP_NewReservation_TravelAgent;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Market)
	public WebElement CP_NewReservation_Market;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Referral)
	public WebElement CP_NewReservation_Referral;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Referral2)
	public WebElement CP_NewReservation_Referral2;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Book)
	public WebElement CP_NewReservation_Book;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Quote)
	public WebElement CP_NewReservation_Quote;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_QuoteBook)
	public WebElement CP_NewReservation_QuoteBook;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_QuoteBookConfirm)
	public WebElement CP_NewReservation_QuoteBookConfirm;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_QuoteBookConfirm2)
	public WebElement CP_NewReservation_QuoteBookConfirm2;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ProceedPay)
	public WebElement CP_NewReservation_ProceedPay;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_PaymentClose)
	public WebElement CP_NewReservation_PaymentClose;
	
	@FindBy(xpath = OR_ReservationV2.CopyButton)
	public WebElement CopyButton;
	
	@FindBy(xpath = OR_ReservationV2.Copy_Reservation_Trimname)
	public WebElement Copy_Reservation_Trimname;
	
	@FindBy(xpath = OR_ReservationV2.CopyGuest_FromPrimaryRoom)
	public WebElement CopyGuest_FromPrimaryRoom;
	
	@FindBy(xpath = OR_ReservationV2.CopyGuest_FromPrimaryRoomCheckbox)
	public WebElement CopyGuest_FromPrimaryRoomCheckbox;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ProceedToBookingPayment_Button)
	public WebElement CP_NewReservation_ProceedToBookingPayment_Button;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_DepositPayPopUp)
	public WebElement CP_NewReservation_DepositPayPopUp;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ConfirmationNumber)
	public WebElement CP_NewReservation_ConfirmationNumber;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ReservationStatus)
	public WebElement CP_NewReservation_ReservationStatus;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_ClosePopUp)
	public WebElement CP_NewReservation_ClosePopUp;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_Folio)
	public WebElement CP_NewReservation_Folio;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_History)
	public WebElement CP_NewReservation_History;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_DetailsTab)
	public WebElement CP_NewReservation_DetailsTab;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_DocumentsTab)
	public WebElement CP_NewReservation_DocumentsTab;

	@FindBy(xpath = OR_ReservationV2.CP_AddTask)
	public WebElement CP_AddTask;

	@FindBy(xpath = OR_ReservationV2.CP_AddNotes)
	public WebElement CP_AddNotes;
	
	@FindBy(xpath = OR_ReservationV2.CP_NoteFor)
	public WebElement CP_NoteFor;
	
	@FindBy(xpath = OR_ReservationV2.CP_NoteType)
	public WebElement CP_NoteType;
	
	@FindBy(xpath = OR_ReservationV2.CP_NoteSubject)
	public WebElement CP_NoteSubject;
	
	@FindBy(xpath = OR_ReservationV2.CP_NoteDescription)
	public WebElement CP_NoteDescription;
	
	@FindBy(xpath = OR_ReservationV2.CP_NoteAddButton)
	public WebElement CP_NoteAddButton;
		
	//Modifications
	@FindBy(xpath = OR_ReservationV2.INPUT_SEARCH_RESERVATION)
	public WebElement  INPUT_SEARCH_RESERVATION;
	
	@FindBy(xpath = OR_ReservationV2.BUTTON_SEARCH_RESERVATION)
	public WebElement  BUTTON_SEARCH_RESERVATION;
	
	@FindBy(xpath = OR_ReservationV2.EDIT_STAY_INFO)
	public WebElement  EDIT_STAY_INFO;
	
	@FindBy(xpath = OR_ReservationV2.CHANGE_STAY_DETAILS)
	public WebElement  CHANGE_STAY_DETAILS;
	
	@FindBy(xpath = OR_ReservationV2.ASSIGN_ROOM_NUMBER)
	public WebElement  ASSIGN_ROOM_NUMBER;
	
	@FindBy(xpath = OR_ReservationV2.RECALCULATE_RATE)
	public WebElement  RECALCULATE_RATE;
	
	@FindBy(xpath = OR_ReservationV2.CHAGNE_ONLY_FOR_DATES)
	public WebElement  CHAGNE_ONLY_FOR_DATES;
	
	@FindBy(xpath = OR_ReservationV2.NO_RATE_CHANGE)
	public WebElement  NO_RATE_CHANGE;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CHECKIN_DATE)
	public WebElement  STAY_INFO_CHECKIN_DATE;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CHECKOUT_DATE)
	public WebElement  STAY_INFO_CHECKOUT_DATE;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_ADULTS)
	public WebElement  STAY_INFO_ADULTS;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CHILDREN)
	public WebElement  STAY_INFO_CHILDREN;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_ADULTS_DROPDOWN)
	public WebElement  STAY_INFO_ADULTS_DROPDOWN;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CHILDREN_DROPDOWN)
	public WebElement  STAY_INFO_CHILDREN_DROPDOWN;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_ADULTS_SELECTED)
	public WebElement  STAY_INFO_ADULTS_SELECTED;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CHILDREN_SELECTED)
	public WebElement  STAY_INFO_CHILDREN_SELECTED;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_RATEPLAN)
	public WebElement  STAY_INFO_RATEPLAN;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_PROMO_CODE)
	public WebElement  STAY_INFO_PROMO_CODE;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_SPLIT_RESERVATION)
	public WebElement  STAY_INFO_SPLIT_RESERVATION;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_FIND_ROOMS)
	public WebElement  STAY_INFO_FIND_ROOMS;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_ADD_ROOM)
	public WebElement  STAY_INFO_ADD_ROOM;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_SAVE)
	public WebElement  STAY_INFO_SAVE;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CONFIRM_YES)
	public WebElement  STAY_INFO_CONFIRM_YES;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CONFIRM_NO)
	public WebElement  STAY_INFO_CONFIRM_NO;
	
	@FindBy(xpath = OR_ReservationV2.TOASTER_MESSAGE)
	public WebElement  TOASTER_MESSAGE;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_SPLIT_INTO_SEPARATE_RESERVATION)
	public WebElement  STAY_INFO_SPLIT_INTO_SEPARATE_RESERVATION;
	
	//Guest Info
	@FindBy(xpath = OR_ReservationV2.EDIT_GUEST_INFO)
	public WebElement  EDIT_GUEST_INFO;
	
	@FindBy(xpath = OR_ReservationV2.ADD_MORE_GUEST_INFO)
	public WebElement  ADD_MORE_GUEST_INFO;
	
	@FindBy(xpath = OR_ReservationV2.ADDITIONAL_GUEST_FIRST_NAME)
	public WebElement  ADDITIONAL_GUEST_FIRST_NAME;
	
	@FindBy(xpath = OR_ReservationV2.ADDITIONAL_GUEST_LAST_NAME)
	public WebElement  ADDITIONAL_GUEST_LAST_NAME;
	
	@FindBy(xpath = OR_ReservationV2.ADDITIONAL_GUEST_EMAIL)
	public WebElement  ADDITIONAL_GUEST_EMAIL;
	
	@FindBy(xpath = OR_ReservationV2.ADDITIONAL_GUEST_PHONE)
	public WebElement  ADDITIONAL_GUEST_PHONE;
	
	@FindBy(xpath = OR_ReservationV2.CONTACT_INFO)
	public WebElement  CONTACT_INFO;
	
	@FindBy(xpath = OR_ReservationV2.checkOutButton)
	public WebElement  checkOutButton;
	
	@FindBy(xpath = OR_ReservationV2.checkOut_GenerateGuestStatement)
	public WebElement  checkOut_GenerateGuestStatement;
	
	@FindBy(xpath = OR_ReservationV2.checkOutAllNoButton)
	public WebElement  checkOutAllNoButton;
	
	@FindBy(xpath = OR_ReservationV2.checkOutAllYesButton)
	public WebElement  checkOutAllYesButton;
	
	@FindBy(xpath = OR_ReservationV2.checkOutConfirm)
	public WebElement  checkOutConfirm;
	
	@FindBy(xpath = OR_ReservationV2.checkOutProceedToCheckOutPayment)
	public WebElement  checkOutProceedToCheckOutPayment;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentHeader)
	public WebElement  checkOutPaymentHeader;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentMethod)
	public WebElement  checkOutPaymentMethod;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentSetAsMainPaymentMethodCheckBox)
	public WebElement  checkOutPaymentSetAsMainPaymentMethodCheckBox;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentSetAsMainPaymentMethodCheckBoxClick)
	public WebElement  checkOutPaymentSetAsMainPaymentMethodCheckBoxClick;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentLogAsExternalPaymentCheckBox)
	public WebElement  checkOutPaymentLogAsExternalPaymentCheckBox;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentAddNotes)
	public WebElement  checkOutPaymentAddNotes;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentNotesInput)
	public WebElement  checkOutPaymentNotesInput;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentPopupAmount)
	public WebElement  checkOutPaymentPopupAmount;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentPayButton)
	public WebElement  checkOutPaymentPayButton;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentSuccessfulClose)
	public WebElement  checkOutPaymentSuccessfulClose;
	
	@FindBy(xpath = OR_ReservationV2.checkOutPaymentSuccessfull)
	public WebElement  checkOutPaymentSuccessfull;
	
	@FindBy(xpath = OR_ReservationV2.checkOutSuccessPaymentDate)
	public WebElement  checkOutSuccessPaymentDate;
	
	@FindBy(xpath = OR_ReservationV2.checkOutSuccessBalance)
	public WebElement  checkOutSuccessBalance;
	
	@FindBy(xpath = OR_ReservationV2.checkOutSuccessPaymentAmount)
	public WebElement  checkOutSuccessPaymentAmount;
	
	@FindBy(xpath = OR_ReservationV2.checkOutSuccessType)
	public WebElement  checkOutSuccessType;
	
	@FindBy(xpath = OR_ReservationV2.checkOutSuccessPaymentMethod)
	public WebElement  checkOutSuccessPaymentMethod;
	
	@FindBy(xpath = OR_ReservationV2.checkOutSuccessPaymentStatus)
	public WebElement  checkOutSuccessPaymentStatus;
	
	@FindBy(xpath = OR_ReservationV2.reservationStatusDropdown)
	public WebElement  reservationStatusDropdown;
	
	@FindBy(xpath = OR_ReservationV2.rollBackButton)
	public WebElement  rollBackButton;
	
	@FindBy(xpath = OR_ReservationV2.rollBackButtonInDropdown)
	public WebElement  rollBackButtonInDropdown;

	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_TAB)
	public WebElement  GUEST_FOLIO_TAB;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_CHARGE)
	public WebElement  GUEST_FOLIO_ADD_CHARGE;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_SearchCategoryName)
	public WebElement  GUEST_FOLIO_SearchCategoryName;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_DATE)
	public WebElement  GUEST_FOLIO_ADD_NEW_DATE;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_CATEGORY)
	public WebElement  GUEST_FOLIO_ADD_NEW_CATEGORY;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_DESCRIPTION)
	public WebElement  GUEST_FOLIO_ADD_NEW_DESCRIPTION;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_SAVE_CHANGES)
	public WebElement  GUEST_FOLIO_SAVE_CHANGES;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_QUANTITY)
	public WebElement  GUEST_FOLIO_ADD_NEW_QUANTITY;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_AMOUNT)
	public WebElement  GUEST_FOLIO_ADD_NEW_AMOUNT;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_NOTE_ICON)
	public WebElement  GUEST_FOLIO_ADD_NEW_NOTE_ICON;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_NOTE_TEXTFIELD)
	public WebElement  GUEST_FOLIO_ADD_NEW_NOTE_TEXTFIELD;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_ADD_NEW_NOTE_ADD_NOTE_BUTTON)
	public WebElement  GUEST_FOLIO_ADD_NEW_NOTE_ADD_NOTE_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_DROPDOWN)
	public WebElement  GUEST_FOLIO_APPLY_ROUTING_DROPDOWN;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_ALLCHARGES)
	public WebElement  GUEST_FOLIO_APPLY_ROUTING_ALLCHARGES;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_SELECTEDCHARGES)
	public WebElement  GUEST_FOLIO_APPLY_ROUTING_SELECTEDCHARGES;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_POPUP_HEADER)
	public WebElement  GUEST_FOLIO_APPLY_ROUTING_POPUP_HEADER;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_FOLIO_APPLY_ROUTING_POPUP_PROCEED_BUTTON)
	public WebElement  GUEST_FOLIO_APPLY_ROUTING_POPUP_PROCEED_BUTTON;
		
	@FindBy(xpath = OR_ReservationV2.Click_Bulk_Action)
	public WebElement Click_Bulk_Action;

	@FindBy(xpath = OR_ReservationV2.Select_Checkin)
	public WebElement Select_Checkin;

	@FindBy(xpath = OR_ReservationV2.Verify_Bulk_Checkin_popup)
	public WebElement Verify_Bulk_Checkin_popup;

	@FindBy(xpath = OR_ReservationV2.Click_Process_Button)
	public WebElement Click_Process_Button;

	@FindBy(xpath = OR_ReservationV2.click_on_Close_icon)
	public WebElement click_on_Close_icon;

	@FindBy(xpath = OR_ReservationV2.Verify_Guest_Name)
	public WebElement Verify_Guest_Name;

	@FindBy(xpath = OR_ReservationV2.Verify_Bulk_Checkin_Success)
	public WebElement Verify_Bulk_Checkin_Success;

	@FindBy(xpath = OR_ReservationV2.Get_Reservation_Status)
	public WebElement Get_Reservation_Status;

	@FindBy(xpath = OR_ReservationV2.Select_Checkout)
	public WebElement Select_Checkout;

	@FindBy(xpath = OR_ReservationV2.Verify_Bulk_Checkout_popup)
	public WebElement Verify_Bulk_Checkout_popup;

	@FindBy(xpath = OR_ReservationV2.CP_NewReservationConfirmBookingClose)
	public WebElement  CP_NewReservationConfirmBookingClose;

	@FindBy(xpath = OR_ReservationV2.Verify_Bulk_Checkout_Completed)
	public WebElement Verify_Bulk_Checkout_Completed;

	@FindBy(xpath = OR_ReservationV2.Click_On_First_SearchResult)
	public WebElement Click_On_First_SearchResult;

	@FindBy(xpath = OR_ReservationV2.Select_Delete)
	public WebElement Select_Delete;

	@FindBy(xpath = OR_ReservationV2.Verify_Bulk_Delete_popup)
	public WebElement Verify_Bulk_Delete_popup;
	
	@FindBy(xpath = OR_ReservationV2.Close_BulkActionPopup)
	public WebElement Close_BulkActionPopup;

	@FindBy(xpath = OR_ReservationV2.Verify_Bulk_Delete_Completed)
	public WebElement Verify_Bulk_Delete_Completed;

	@FindBy(xpath = OR_ReservationV2.Check_First_SearchResult)
	public WebElement Check_First_SearchResult;
	
	@FindBy(xpath = OR_ReservationV2.ClickOnCheckBoxProperty)
	public WebElement clickOnCheckBoxProperty;

	@FindBy(xpath = OR_ReservationV2.ResBulkCanUpdatedTab)
	public WebElement ResBulkCanUpdatedTab;

	@FindBy(xpath = OR_ReservationV2.ResBulkCanNotUpdatedTab)
	public WebElement ResBulkCanNotUpdatedTab;

	@FindBy(xpath = OR_ReservationV2.ClickProcessButtonBulkCheckIN)
	public WebElement ClickProcessButtonBulkCheckIN;

	@FindBy(xpath = OR_ReservationV2.Verify_resNumber1BulkCheckInCanNotUpdate)
	public WebElement Verify_resNumber1BulkCheckInCanNotUpdate;

	@FindBy(xpath = OR_ReservationV2.ClickResCanUpdateAfterProcess)
	public WebElement clickResCanUpdateAfterProcess;

	@FindBy(xpath = OR_ReservationV2.ClickResCanNotUpdateAfterProcess)
	public WebElement clickResCanNotUpdateAfterProcess;

	@FindBy(xpath = OR_ReservationV2.VerifyBulkCheckInCompletePopUp)
	public WebElement verifyBulkCheckInCompletePopUp;

	@FindBy(xpath = OR_ReservationV2.CloseBulkActionPopUp)
	public WebElement CloseBulkActionPopUp;

	@FindBy(xpath = OR_ReservationV2.ClickProcessButtonBulkCheckOut)
	public WebElement clickProcessButtonBulkCheckOut;

	@FindBy(xpath = OR_ReservationV2.VerifyBulkCheckOutCompletePopUp)
	public WebElement verifyBulkCheckOutCompletePopUp;
	
	@FindBy(xpath = OR_ReservationV2.closeBulkCheckoutPopUp)
	public WebElement closeBulkCheckoutPopUp;
	
	@FindBy(xpath = OR_ReservationV2.reservationStatusInDetailSection)
	public WebElement reservationStatusInDetailSection;

	@FindBy(xpath = OR_ReservationV2.bulkCheckOutSuccessHeading)
	public WebElement bulkCheckOutSuccessHeading;

	@FindBy(xpath = OR_ReservationV2.bulkCheckOutSuccessReservationCount)
	public WebElement bulkCheckOutSuccessReservationCount;
	
	@FindBy(xpath = OR_ReservationV2.bulkActionPopup)
	public WebElement bulkActionPopup;
	
	@FindBy(xpath = OR_ReservationV2.bulkCancelReasonInput)
	public WebElement bulkCancelReasonInput;
	
	@FindBy(xpath = OR_ReservationV2.bulkCancelVoidRoomCharges)
	public WebElement bulkCancelVoidRoomCharges;
	
	@FindBy(xpath = OR_ReservationV2.clickProcessBulkAction)
	public WebElement clickProcessBulkAction;
	
	@FindBy(xpath = OR_ReservationV2.bulkActionPostFee)
	public WebElement bulkActionPostFee;
	
	@FindBy(xpath = OR_ReservationV2.takePaymentButton)
	public WebElement takePaymentButton;
	
	@FindBy(xpath = OR_ReservationV2.takePaymentPayButton)
	public WebElement takePaymentPayButton;
	
	@FindBy(xpath = OR_ReservationV2.takePaymentAmountInput)
	public WebElement takePaymentAmountInput;
	
	@FindBy(xpath = OR_ReservationV2.takePaymentTypeButton)
	public WebElement takePaymentTypeButton;
	
	@FindBy(xpath = OR_ReservationV2.takePaymentPaymentMethod)
	public WebElement takePaymentPaymentMethod;
	


	
	
	
	@FindBy(xpath = OR_ReservationV2.clickIncidentals)
	public WebElement clickIncidentals;
	
	@FindBy(xpath = OR_ReservationV2.EnterAddOn_IncidenalDate)
	public WebElement EnterAddOn_IncidenalDate;

	@FindBy(xpath = OR_ReservationV2.ButtonSelectIncidental)
	public WebElement ButtonSelectIncidental;

	@FindBy(xpath = OR_ReservationV2.EnterPerUnit)
	public WebElement EnterPerUnit;

	@FindBy(xpath = OR_ReservationV2.EnterQuentity)
	public WebElement EnterQuentity;

	@FindBy(xpath = OR_ReservationV2.GetIncidentalDate)
	public List<WebElement> GetIncidentalDate;

	@FindBy(xpath = OR_ReservationV2.GetIncidentalCategory)
	public List<WebElement> GetIncidentalCategory;

	@FindBy(xpath = OR_ReservationV2.GetIncidentalDescription)
	public List<WebElement> GetIncidentalDescription;

	@FindBy(xpath = OR_ReservationV2.clickProcessBulkUpdate)
	public WebElement clickProcessBulkUpdate;
	
	@FindBy(xpath = OR_ReservationV2.CONFIRM_NOSHOW_BUTTON)
	public WebElement CONFIRM_NOSHOW_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.PROCEED_TO_NO_SHOW_PAYMENT_BUTTON)
	public WebElement PROCEED_TO_NO_SHOW_PAYMENT_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.NO_SHOW_VOID_ROOM_CHARGES)
	public WebElement NO_SHOW_VOID_ROOM_CHARGES;
	
	@FindBy(xpath = OR_ReservationV2.NO_SHOW_PAYMENT_POPUP_CLOSE)
	public WebElement NO_SHOW_PAYMENT_POPUP_CLOSE;
	
	@FindBy(xpath = OR_ReservationV2.NO_SHOW_SUCCESSFULL_POPUP_CLOSE)
	public WebElement NO_SHOW_SUCCESSFULL_POPUP_CLOSE;
	
	@FindBy(xpath = OR_ReservationV2.GetIncidentalPerUnit)
	public List<WebElement> GetIncidentalPerUnit;

	@FindBy(xpath = OR_ReservationV2.GetIncidentalQuentity)
	public List<WebElement> GetIncidentalQuentity;

	@FindBy(xpath = OR_ReservationV2.GetIncidentalTax)
	public List<WebElement> GetIncidentalTax;

	@FindBy(xpath = OR_ReservationV2.GetIncidentalTotalAmount)
	public List<WebElement> GetIncidentalTotalAmount;

	@FindBy(xpath = OR_ReservationV2.IncidentalSave)
	public WebElement IncidentalSave;
	
	@FindBy(xpath = OR_ReservationV2.closePaymentSuccessPopup)
	public WebElement closePaymentSuccessPopup;

	@FindBy(xpath = OR_ReservationV2.guestFolioPayButton)
	public WebElement guestFolioPayButton;

	@FindBy(xpath = OR_ReservationV2.guestDetailsRefundButton)
	public WebElement guestDetailsRefundButton;

	@FindBy(xpath = OR_ReservationV2.refundPopupHeader)
	public WebElement refundPopupHeader;

	@FindBy(xpath = OR_ReservationV2.refundPopupAmount)
	public WebElement refundPopupAmount;

	@FindBy(xpath = OR_ReservationV2.refundPopupRefundButton)
	public WebElement refundPopupRefundButton;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_CLOSE_ICON)
	public WebElement  STAY_INFO_CLOSE_ICON;
	
	@FindBy(xpath = OR_ReservationV2.TS_STAY_DATE_RANGE)
	public WebElement TS_STAY_DATE_RANGE;
	
	@FindBy(xpath = OR_ReservationV2.TS_OCCUPANTS)
	public WebElement TS_OCCUPANTS;
	
	@FindBy(xpath = OR_ReservationV2.TS_ROOM_DETAIL)
	public WebElement TS_ROOM_DETAIL;
	
	@FindBy(xpath = OR_ReservationV2.EDIT_MARKETING_INFO_ICON)
	public WebElement EDIT_MARKETING_INFO_ICON;
	
	@FindBy(xpath = OR_ReservationV2.MARKETING_INFO_SAVE)
	public WebElement MARKETING_INFO_SAVE;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATION_NUMBER)
	public WebElement RESERVATION_NUMBER;
	
	@FindBy(xpath = OR_ReservationV2.NEW_RESERVATION_GUEST_INFO_SECTION)
	public WebElement NEW_RESERVATION_GUEST_INFO_SECTION;
	
	@FindBy(xpath = OR_ReservationV2.CREATE_GUEST_BUTTON)
	public WebElement CREATE_GUEST_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.CREATE_GUEST_PROFILE_TOGGLE)
	public WebElement CREATE_GUEST_PROFILE_TOGGLE;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_PROFILE_SAVE)
	public WebElement GUEST_PROFILE_SAVE;
	
	@FindBy(xpath = OR_ReservationV2.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE)
	public WebElement VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE;
	
	@FindBy(xpath = OR_ReservationV2.ADD_MORE_GUESTS)
	public WebElement ADD_MORE_GUESTS;
	
	@FindBy(xpath = OR_ReservationV2.ADD_MORE_GUESTS_WARNING_POPUP)
	public WebElement ADD_MORE_GUESTS_WARNING_POPUP;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_GiftCertNumber2)
	public WebElement CP_NewReservation_GiftCertNumber2;

	@FindBy(xpath = OR_ReservationV2.GUEST_INFO_PHONE_COUNTRY_CODE)
	public WebElement GUEST_INFO_PHONE_COUNTRY_CODE;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_INFO_PHONE_EXTENSION)
	public WebElement GUEST_INFO_PHONE_EXTENSION;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_INFO_ALTERNATEPHONE_COUNTRY_CODE)
	public WebElement GUEST_INFO_ALTERNATEPHONE_COUNTRY_CODE;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_INFO_ALTERNATEPHONE_EXTENSION)
	public WebElement GUEST_INFO_ALTERNATEPHONE_EXTENSION;

	@FindBy(xpath = OR_ReservationV2.EDIT_PAYMENT_INFO_ICON)
	public WebElement EDIT_PAYMENT_INFO_ICON;
	
	@FindBy(xpath = OR_ReservationV2.PAYMENT_INFO_SAVE)
	public WebElement PAYMENT_INFO_SAVE;
	
	@FindBy(xpath = OR_ReservationV2.PI_BILLING_ADDRESS1)
	public WebElement PI_BILLING_ADDRESS1;
	
	@FindBy(xpath = OR_ReservationV2.PI_BILLING_ADDRESS2)
	public WebElement PI_BILLING_ADDRESS2;
	
	@FindBy(xpath = OR_ReservationV2.PI_BILLING_ADDRESS3)
	public WebElement PI_BILLING_ADDRESS3;
	
	@FindBy(xpath = OR_ReservationV2.PI_BILLING_CITY)
	public WebElement PI_BILLING_CITY;
	
	@FindBy(xpath = OR_ReservationV2.PI_BILLING_COUNTRY)
	public WebElement PI_BILLING_COUNTRY;

	@FindBy(xpath = OR_ReservationV2.PI_BILLING_STATE)
	public WebElement PI_BILLING_STATE;
	
	@FindBy(xpath = OR_ReservationV2.PI_BILLING_POSTALCODE)
	public WebElement PI_BILLING_POSTALCODE;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_INFO_ACCOUNT)
	public WebElement GUEST_INFO_ACCOUNT;

	@FindBy(xpath = OR_ReservationV2.SPLIT_ROOM_CHKBOX)
	public WebElement SPLIT_ROOM_CHKBOX;
	
	@FindBy(xpath = OR_ReservationV2.STAY_INFO_THREE_DOTS)
	public WebElement STAY_INFO_THREE_DOTS;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_ADD_NOTES_BUTTON)
	public WebElement REFUND_ADD_NOTES_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_NOTES)
	public WebElement REFUND_NOTES;
	
	@FindBy(xpath = OR_ReservationV2.RV2_RATE_PLAN_DROPDOWN)
	public WebElement RV2_RATE_PLAN_DROPDOWN;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_PAYMENT_METHOD)
	public WebElement REFUND_PAYMENT_METHOD;
	
	@FindBy(xpath = OR_ReservationV2.PAYMENT_LINE_ITEM)
	public WebElement PAYMENT_LINE_ITEM;

	@FindBy(xpath = OR_ReservationV2.ADVANCE_SEARCH_LINK)
	public WebElement ADVANCE_SEARCH_LINK;

	@FindBy(xpath = OR_ReservationV2.ADVANCE_SEARCH_ACCOUNT_NUMBER)
	public WebElement ADVANCE_SEARCH_ACCOUNT_NUMBER;
	
	@FindBy(xpath = OR_ReservationV2.ADVANCE_SEARCH_SEARCH_BUTTON)
	public WebElement ADVANCE_SEARCH_SEARCH_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.ADVANCE_SEARCH_BASIC_BUTTON)
	public WebElement ADVANCE_SEARCH_BASIC_BUTTON;
	
	@FindBy(xpath = OR_ReservationV2.FIRST_SEARCHED_RES_CONFIRMATION_NO)
	public WebElement FIRST_SEARCHED_RES_CONFIRMATION_NO;

	@FindBy(xpath = OR_ReservationV2.RESERVATIONV2_PAYMENTMETHOD_EDIT)
	public WebElement RESERVATIONV2_PAYMENTMETHOD_EDIT;
	
	@FindBy(xpath = OR_ReservationV2.RESERVATIONV2_PAYMENTMETHOD_Delete)
	public WebElement RESERVATIONV2_PAYMENTMETHOD_Delete;

	@FindBy(xpath = OR_ReservationV2.REFUND_PE_pathRefundSpecificTab)
	public WebElement REFUND_PE_pathRefundSpecificTab;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_PE_pathManualCCCashTab)
	public WebElement REFUND_PE_pathManualCCCashTab;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_CLOSE_ICON)
	public WebElement REFUND_CLOSE_ICON;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_SET_AS_MAIN_METHOD_CHK)
	public WebElement REFUND_SET_AS_MAIN_METHOD_CHK;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_LOG_AS_EXTERNAL_CHK)
	public WebElement REFUND_LOG_AS_EXTERNAL_CHK;
	

	
	@FindBy(xpath = OR_ReservationV2.FOLIO_REFUND_BTN)
	public WebElement FOLIO_REFUND_BTN;
	
	@FindBy(xpath = OR_ReservationV2.CARD_NUMBER)
	public WebElement CARD_NUMBER;


	
	@FindBy(xpath = OR_ReservationV2.REFUND_PAYMENT_METHOD_DOWNARROW)
	public WebElement REFUND_PAYMENT_METHOD_DOWNARROW;
	
	@FindBy(xpath = OR_ReservationV2.ENTER_RES_NO_IN_PAY_REFUND_POPUP)
	public WebElement ENTER_RES_NO_IN_PAY_REFUND_POPUP;

	

	@FindBy(xpath = OR_ReservationV2.NAME_ON_CARD)
	public WebElement NAME_ON_CARD;
	
	@FindBy(xpath = OR_ReservationV2.EXPIRY_DATE)
	public WebElement EXPIRY_DATE;

	@FindBy(xpath = OR_ReservationV2.CVV_CODE)
	public WebElement CVV_CODE;
	
	@FindBy(xpath = OR_ReservationV2.REFUND_NOTE_4_EXTERNAL_PAYMENT)
	public WebElement REFUND_NOTE_4_EXTERNAL_PAYMENT;
	
	@FindBy(xpath = OR_ReservationV2.ADD_CUSTOM_FOLIO_BTN)
	public WebElement ADD_CUSTOM_FOLIO_BTN;
	
	@FindBy(id = OR_ReservationV2.ENTER_CUSTOM_FOLIO_NAME)
	public WebElement ENTER_CUSTOM_FOLIO_NAME;
	
	@FindBy(xpath = OR_ReservationV2.SAVE_CUSTOM_FOLIO_BTN)
	public WebElement SAVE_CUSTOM_FOLIO_BTN;

	@FindBy(xpath = OR_ReservationV2.FOLIO_PAY_BTN)
	public WebElement FOLIO_PAY_BTN;
	
	@FindBy(xpath = OR_ReservationV2.TAKE_PAYMENT_PAYMENT_METHOD)
	public WebElement TAKE_PAYMENT_PAYMENT_METHOD;
	
	@FindBy(xpath = OR_ReservationV2.ENTER_HA_NO_IN_PAY_REFUND_POPUP)
	public WebElement ENTER_HA_NO_IN_PAY_REFUND_POPUP;
	
	@FindBy(xpath = OR_ReservationV2.TAKEPAYMENT_LOG_AS_EXTERNAL_CHK)
	public WebElement TAKEPAYMENT_LOG_AS_EXTERNAL_CHK;
	
	@FindBy(xpath = OR_ReservationV2.takePaymentPaymentMethod1)
	public WebElement takePaymentPaymentMethod1;
	
	@FindBy(xpath = OR_ReservationV2.OVERIDE_DEPOSIT_DUE_TOGGLE)
	public WebElement OVERIDE_DEPOSIT_DUE_TOGGLE;

	@FindBy(xpath = OR_ReservationV2.GUEST_PROFILE_SAVE_COPY)
	public WebElement GUEST_PROFILE_SAVE_COPY;
	
	@FindBy(xpath = OR_ReservationV2.CP_NewReservation_GuestSalutation_COPY)
	public WebElement CP_NewReservation_GuestSalutation_COPY;
	
	@FindBy(xpath = OR_ReservationV2.Toaster_Message_Copy)
	public WebElement Toaster_Message_Copy;
	
	@FindBy(xpath = OR_ReservationV2.Toaster_Message_Close_Copy)
	public WebElement Toaster_Message_Close_Copy;
	
	@FindBy(xpath = OR_ReservationV2.Toaster_Message_Xpath_Copy)
	public WebElement Toaster_Message_Xpath_Copy;
	
	@FindBy(xpath = OR_ReservationV2.GUEST_PROFILE_SEARCH)
	public WebElement GUEST_PROFILE_SEARCH;
	
	@FindBy(xpath = OR_ReservationV2.depositPolicyTextAtDisclaimersV2)
	public WebElement depositPolicyTextAtDisclaimersV2;
	
	@FindBy(xpath = OR_ReservationV2.ENTER_GC_NO_IN_PAY_REFUND_POPUP)
	public WebElement ENTER_GC_NO_IN_PAY_REFUND_POPUP;
	
	@FindBy(xpath = OR_ReservationV2.DEPOSIT_PAYMENT_CLOSE_POPUP)
	public WebElement DEPOSIT_PAYMENT_CLOSE_POPUP;		
	
	@FindBy(xpath = OR_ReservationV2.QUOTE_PROCEED_TO_PAYMENT_BTN)
	public WebElement QUOTE_PROCEED_TO_PAYMENT_BTN;
	
	@FindBy(xpath = OR_ReservationV2.DEPOSIT_DUE_AMOUNT)
	public WebElement DEPOSIT_DUE_AMOUNT;
	
	@FindBy(xpath = OR_ReservationV2.SAVE_TASK_BTN)
	public WebElement SAVE_TASK_BTN;
	
	@FindBy(xpath = OR_ReservationV2.EDIT_SAVE_TASK_BTN)
	public WebElement EDIT_SAVE_TASK_BTN;
	
	@FindBy(xpath = OR_ReservationV2.CLICK_ADDON)
	public WebElement CLICK_ADDON;
	
	@FindBy(xpath = OR_ReservationV2.ADDON_DATE_PICKER)
	public WebElement ADDON_DATE_PICKER;
	
	@FindBy(xpath = OR_ReservationV2.SEARCH_ADDON)
	public WebElement SEARCH_ADDON;
	
	@FindBy(xpath = OR_ReservationV2.ADDON_QUANTITY_INCREASE)
	public WebElement ADDON_QUANTITY_INCREASE;
	
	@FindBy(xpath = OR_ReservationV2.ADDON_SAVE)
	public WebElement ADDON_SAVE;

	@FindBy(xpath = OR_ReservationV2.CHECKOUT_VOID_ROOM_CHARGES)
	public WebElement CHECKOUT_VOID_ROOM_CHARGES;


	@FindBy(xpath = OR_ReservationV2.RESERVATION_GUEST_INFO_EDIT2)
	public WebElement RESERVATION_GUEST_INFO_EDIT2;

	@FindBy(xpath = OR_ReservationV2.CP_Reservation_CheckInAllButton)
	public WebElement CP_Reservation_CheckInAllButton;

	@FindBy(xpath = OR_ReservationV2.ROOMLOCK)
	public WebElement ROOMLOCK;
	
	@FindBy(xpath = OR_ReservationV2.GetTotalAmount)
	public WebElement GetTotalAmount;

}

