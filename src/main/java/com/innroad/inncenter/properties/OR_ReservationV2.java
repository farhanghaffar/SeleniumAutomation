package com.innroad.inncenter.properties;

public class OR_ReservationV2 {

	public static final String STATUS_BAR = "//reservation-statusbar";
	public static final String SB_GUEST_NAME = STATUS_BAR + "//span[contains(@data-bind,'guestName')][@class='ir-textTrim']";
	public static final String SB_RESERVATION_STATUS = STATUS_BAR + "//span[contains(@data-bind,'statusbarModel.reservationStatus')]";
	public static final String SB_CONFIRMATION_NO = STATUS_BAR + "//span[contains(@data-bind,'statusbarModel.confirmationNumber')]";
	public static final String SB_STAY_DURATION = STATUS_BAR + "//span[contains(@data-bind,'text: stayDuration')]";
	public static final String SB_PHONE_NO = STATUS_BAR + "//span[contains(@data-bind,'text:formattedPhoneNumber')]";
	public static final String SB_EMAIL = STATUS_BAR + "//span[contains(@data-bind,'statusbarModel.email')]";
	public static final String SB_TRIP_SUMMARAY = STATUS_BAR + "//span[contains(@data-bind,'statusbarModel.tripTotal')]";
	public static final String SB_BALANCE = STATUS_BAR + "//span[contains(@data-bind,'statusbarModel.balance')]";
	
	public static final String STAY_INFO = "//div[@id='stayinfo']";
	public static final String SI_PROPERTY_NAME = STAY_INFO + "//div[contains(@data-bind,'propertyName')]";
	public static final String SI_RATE_PLAN_NAME = STAY_INFO + "//span[contains(@data-bind,'ratePlanName')]";
	public static final String SI_CHECK_IN_DATE = STAY_INFO + "//span[contains(@data-bind,'stayInfo.checkInDate') and contains(@data-bind,'DateFormatForUI')]"; //"(" + STAY_INFO + "//span[contains(@data-bind,'stayInfo.checkInDate')])[1]";
	public static final String SI_CHECK_OUT_DATE = STAY_INFO + "//span[contains(@data-bind,'stayInfo.checkOutDate')  and contains(@data-bind,'DateFormatForUI')]"; //"(" + STAY_INFO + "//span[contains(@data-bind,'stayInfo.checkOutDate')])[1]";
	public static final String SI_NUMBER_OF_NIGHTS = STAY_INFO + "//span[contains(@data-bind,'noOfNights')]";
	public static final String SI_NUMBER_OF_ADULTS = STAY_INFO + "//span[contains(@data-bind,'stayInfo.adults')]";
	public static final String SI_NUMBER_OF_CHILDS = STAY_INFO + "//span[contains(@data-bind,'stayInfo.children')]";
	public static final String SI_ROOMCLASS_NAME = STAY_INFO + "//span[contains(@data-bind,'roomClassName')]";
	public static final String SI_ROOM_NUMBER = STAY_INFO + "//span[contains(@data-bind,'roomNumber')]";
	public static final String SI_ROOM_TOTAL = STAY_INFO + "//span[contains(@data-bind,'roomTotal')]";
	
	public static final String GUEST_INFO = "//guest-info-v2";//"//guest-info";
	public static final String GI_GUEST_NAME = GUEST_INFO + "//div[contains(@class,'guest-user-details')]//span[contains(@data-bind,'fullName')]";//"//span[contains(@data-bind,'guestProfileName')]";
	public static final String GI_CONTACT_NAME = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'fullName')]";//"//span[contains(@data-bind,'contactProfileName')]";
	public static final String GI_EMAIL = GUEST_INFO + "//span[contains(@data-bind,'email')]";
	public static final String GI_PHONE_NO = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'mailingAddress.phone.number')]";//"//span[contains(@data-bind,'formattedPhoneNumber')]";
	public static final String GI_ALTERNATE_PHONE = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'mailingAddress.alternatePhone.number')]";//"//span[contains(@data-bind,'formattedAlternativePhoneNumber')]";
	public static final String GI_ADDRESS = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//label[text()='Address']/following-sibling::span";
	public static final String GI_CITY = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'city')]";
	public static final String GI_STATE = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'stateName')]";
	public static final String GI_POSTAL_CODE = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'postalCode')]";
	public static final String GI_COUNTRY = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'countryName')]";
//	public static final String GI_FAX= GUEST_INFO + "//span[contains(@data-bind,'formattedFaxNumber')]";
	public static final String GI_ACCOUNT = GUEST_INFO + "//div[contains(@data-bind,'getPrimaryGuestDetails')]//span[contains(@data-bind,'accountInfo.name')]";
	
	public static final String TRIP_SUMMARY = "(//div[contains(@data-bind,'tripSummaryModel')])[1]";
	public static final String TS_ROOM_CHARGE = TRIP_SUMMARY + "//div[contains(@data-bind,'roomChargeTotal')]";
	public static final String TS_STAY_DATE_RANGE = TRIP_SUMMARY + "//div[contains(@data-bind,'stayDateRange')]";
	public static final String TS_OCCUPANTS = TRIP_SUMMARY + "//div[contains(@data-bind,'occupants')]";
	public static final String TS_ROOM_DETAIL = TRIP_SUMMARY + "//div[contains(@data-bind,'roomClassName ')]";
	public static final String TS_INCIDENTALS = TRIP_SUMMARY + "//div[contains(@data-bind,'incidentals')]";
	public static final String TS_FEES = TRIP_SUMMARY + "//div[contains(@data-bind,'feesTotal')]";
	public static final String TS_TAXES = TRIP_SUMMARY + "//div[contains(@data-bind,'taxesTotal')]";
	public static final String TS_TRIP_TOTAL = TRIP_SUMMARY + "//div[contains(@data-bind,'tripTotal')]";
	public static final String TS_PAID = TRIP_SUMMARY + "//div[contains(@data-bind,'paymentsTotal')]";
	public static final String TS_BALANCE = TRIP_SUMMARY + "//div[contains(@data-bind,'balance')]";
	
	public static final String PAYMENT_INFO = "//div[@id='PaymentInfoModule']";
	public static final String PI_BILLING_TYPE_NAME = PAYMENT_INFO + "//span[contains(@data-bind,'billingTypeName')]";
	public static final String PI_LAST_FOUR_DIGITS = PAYMENT_INFO + "//span[contains(@data-bind,'lastFourDigits')]";
	public static final String PI_NAME_ON_CARD = PAYMENT_INFO + "//span[contains(@data-bind,'text: creditCard.name')]";
	public static final String PI_EXPIRY_DATE = PAYMENT_INFO + "//span[contains(@data-bind,'text: expirationDate')]";
	
	public static final String MARKETING_INFO = "//marketing-info";
	public static final String MI_MARKET_SEGMENT = MARKETING_INFO + "//span[contains(@data-bind,'marketSegment')]";
	public static final String MI_MARKET_REFERRAL = MARKETING_INFO + "//span[contains(@data-bind,'marketReferral')]";
	public static final String MI_EXTERNAL_CONFIRMATION_NUMBER = MARKETING_INFO + "//span[contains(@data-bind,'marketExternalConfirmationNumber')]";
	public static final String MI_MARKET_SOURCE = MARKETING_INFO + "//span[contains(@data-bind,'text:marketSource')]";
	public static final String MI_MARKET_SUB_SOURCE = MARKETING_INFO + "//span[contains(@data-bind,'text:marketSubSource')]";
	
	public static final String DEPOSIT_POLICY_TOGGLE = "(//*[@id='accordion']//a[contains(@data-bind,'policyInfoClicked')])[1]";
	public static final String CHECK_IN_POLICY_TOGGLE = "(//*[@id='accordion']//a[contains(@data-bind,'policyInfoClicked')])[2]";
	public static final String NO_SHOW_POLICY_TOGGLE = "(//*[@id='accordion']//a[contains(@data-bind,'policyInfoClicked')])[3]";
	public static final String CANCELLATION_POLICY_TOGGLE = "(//*[@id='accordion']//a[contains(@class,'policyinfo-accordion-toggle')]//span[contains(text(),'Cancellation')])";
	
	public static final String DEPOSIT_POLICY_TITLE = "(//label[text()='Deposit Policy']/following-sibling::div/button/span)[1]";
	public static final String DEPOSIT_POLICY_STATEMENT = "//textarea[contains(@data-bind,'text:depositPolicyStatement')]";
	
	public static final String CHECK_IN_POLICY_TITLE = "(//label[text()='Check-in Policy']/following-sibling::div/button/span)[1]";
	public static final String CHECK_IN_POLICY_STATEMENT = "//textarea[contains(@data-bind,'text:checkInPolicyStatement')]";
	
	public static final String NO_SHOW_POLICY_TITLE = "(//label[text()='No Show Policy']/following-sibling::div/button/span)[1]";
	public static final String NO_SHOW_POLICY_STATEMENT = "//textarea[contains(@data-bind,'text:noShowPolicyStatement')]";
	
	public static final String CANCELLATION_POLICY_TITLE = "(//label[text()='Cancellation Policy']/following-sibling::div/button/span)[1]";
	public static final String CANCELLATION_POLICY_STATEMENT = "//textarea[contains(@data-bind,'text:PolicyStatement')]";
	
	public static final String NOTES = "//tr[contains(@data-bind,'notesInfoModelForEdit.showNoteDetails')]";
	public static final String NOTES_TYPE = ".//td[1]";
	public static final String NOTES_SUBJECT = ".//td[2]";
	public static final String NOTES_DESCRIPTION = ".//td[3]";
	public static final String NOTES_UPDATED_BY = ".//td[4]";
	public static final String NOTES_UPDATED_ON = ".//td[5]";
	
	public static final String FOLIO_TABLE_RECORD = "(//table[contains(@class,'table-lineItems')])[1]/tbody/tr";
	public static final String ITEM_STATUS = ".//td[@class='lineitem-status']/span";
	public static final String ITEM_DATE = ".//td[@class='lineitem-date']/span";
	public static final String ITEM_CATEGORY = ".//td[@class='lineitem-category']/span";
	public static final String ITEM_DESCRIPTION = ".//td[contains(@class,'lineitem-description')]/a";
	public static final String ITEM_QTY = ".//td[@class='lineitem-qty']/span";
	public static final String ITEM_AMOUNT = ".//td[@class='lineitem-amount']/span";
	public static final String ITEM_TAX = ".//td[@class='lineitem-tax ']/span";
	public static final String ITEM_TOTAL = ".//td[@class='lineitem-total']/span";
	
	public static final String DETAIL_TAB = "//ul[@class='nav nav-tabs']//a[text()='Details']";
	public static final String FOLIO_TAB = "//ul[@class='nav nav-tabs']//a[text()='Folio(s)']";
	public static final String HISTORY_TAB = "//ul[@class='nav nav-tabs']//a[text()='History']";
	public static final String DOCUMENT_TAB = "//ul[@class='nav nav-tabs']//a[text()='Documents']";
	
	public static final String GUEST_NAME_SEARCH = "//input[contains(@data-bind,'textInput: BasicGuestName')]";
	public static final String GUEST_RES_NO_SEARCH = "//input[@ID='txtResNum']";
	public static final String RES_SEARCH = "//button[@ID='Button2']";
	public static final String FIRST_SEARCHED_RES = "(//a[contains(@data-bind,'text: GuestName')])[1]";
	
	public static final String RESERVATION_CHECK_IN_BUTTON = STATUS_BAR + "//button[contains(@data-bind,'statusbarModel.visibleCheckInBtn')]";
	public static final String RESERVATION_CHECKIN_GUEST_REGISTRATION_REPORT_TOGGLE = "//span[contains(@data-bind,'toggleEnableReportGeneration')]|//span[contains(@data-bind,'generateGuestStatement')]";
	public static final String RESERVATION_CHECKIN_GUEST_REGISTRATION_REPORT_TOGGLE_OFF = "//span[contains(@data-bind,'toggleEnableReportGeneration')][contains(@class,'off')]|//span[contains(@data-bind,'generateGuestStatement')][contains(@class,'off')]";
	public static final String RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON = "//span[text()='PROCEED TO CHECK IN PAYMENT']/..";
	public static final String RESERVATION_CONFIRM_CHECK_IN_PAYMENT_BUTTON  = "//span[text()='CONFIRM CHECK IN']/..";
	public static final String RESERVATION_CHECK_IN_PAYMENT_PRPCESS_BUTTON = "//span[contains(text(),'Pay') or contains(text(),'Refund') or contains(text(),'Log') ][contains(@data-bind,'PaymentProcessButtonText')]/..";//"//span[contains(text(),'Pay') or contains(text(),'Refund')][contains(@data-bind,'PaymentProcessButtonText')]/parent::button";
	public static final String RESERVATION_CHECK_IN_POPUP_CLOSE = "//button[contains(@data-bind,'onCloseCheckInPopup')]";
	public static final String RESERVATION_GUEST_INFO_EDIT = "(//a[contains(@data-bind,'editGuestInfo')][contains(@class,'edit-icon')])[1]";

	public static final String RESERVATION_GUEST_INFO_PHONE_NO = "//input[contains(@data-bind,'guestInfo.primaryGuest.phone.number')]";
	public static final String RESERVATION_GUEST_INFO_SAVE_BUTTON = "//span[text()='Save']/parent::button[contains(@data-bind,'guestInfoSave')]";
	
	public static final String CHECK_IN_PAYMENT_POLICY_NAME = "//span[contains(@data-bind,'PaymentPolicyDescription')]";
	public static final String CHECK_IN_PAYPMENT_BALANCE = "(//span[contains(@data-bind,'PaymentFolioBalance')])[1]";
	public static final String CHECK_IN_PAYMENT_AMOUNT = "//*[@id='payFormAmountV2']";
	public static final String CHECK_IN_PAYMENT_TYPE = "//*[contains(@data-bind,'PaymentAuthType')]/following-sibling::div/button/span[1]";
	
	public static final String CHECK_IN_PAYMENT_SUCCESS_POLICY_NAME = "//div[contains(@data-bind,'paymentSuccessModel')]//span[contains(@data-bind,'policyDescription')]";
	public static final String CHECK_IN_PAYPMENT_SUCCESS_BALANCE = "(//div[contains(@data-bind,'paymentSuccessModel')]//span[contains(@data-bind,'showInnroadCurrency')])[1]";
	public static final String CHECK_IN_PAYMENT_SUCCESS_AMOUNT = "(//div[contains(@data-bind,'paymentSuccessModel')]//span[contains(@data-bind,'showInnroadCurrency')])[2]";
	public static final String CHECK_IN_PAYMENT_SUCCESS_TYPE = "//div[contains(@data-bind,'paymentSuccessModel')]//span[contains(@data-bind,'text : type')]";

	public static final String CANCEL_RESERVATION_POLICY_NAME = "//div[@id='ir-cancel']//span[contains(@data-bind,'cancelPolicy')]";
	public static final String ADD_CANCELATION_REASON = "//div[@id='ir-cancel']//textarea[contains(@data-bind,'cancellationInfo.cancelReason')]";
	public static final String PROCEED_TO_CANCELLATION_BUTTON = "//div[@id='ir-cancel']//button[contains(@data-bind,'proceedToCancellationPayment')]";
	public static final String ON_CONFIRM_CANCELLATION_BUTTON = "//div[@id='ir-cancel']//button[contains(@data-bind,'onConfirmCancellationClick')]";
	public static final String CANCEL_VOID_ROOM_CHARGES = "//input[@data-bind='checked: isVoidChargesCheckboxChecked']";
	public static final String CANCEL_PAYPMENT_BALANCE = "(//span[contains(@data-bind,'PaymentFolioBalance')])[4]";
	public static final String CANCEL_PAYMENT_AMOUNT = "//*[@id='payFormAmountV2']";
	public static final String CANCEL_PAYMENT_TYPE = "//*[contains(@data-bind,'PaymentAuthType')]/following-sibling::div/button/span[1]";

	public static final String CONFIRM_NOSHOW_BUTTON = "//span[text()='CONFIRM NO SHOW']/..";
	public static final String PROCEED_TO_NO_SHOW_PAYMENT_BUTTON = "//span[contains(text(),'PROCEED TO NO SHOW PAYMENT')]|//button[contains(text(),'PROCEED TO CANCELLATION PAYMENT')]";
	public static final String NO_SHOW_VOID_ROOM_CHARGES = "//input[@data-bind='checked: noShowModal.isVoidChargesChecked']";
	public static final String NO_SHOW_PAYMENT_POPUP_CLOSE = "//div[@class='col-md-12 text-right']//button[text()='CLOSE']";
	public static final String NO_SHOW_SUCCESSFULL_POPUP_CLOSE = "//button[contains(@data-bind,'visible:flowControls().isFinalStep')][text()='Close']";

	public static final String CANCEL_PAYMENT_SUCCESS_POLICY_NAME = "//div[contains(@data-bind,'paymentSuccessOrFailureModal')]//span[contains(@data-bind,'policyDescription')]";
	public static final String CANCEL_PAYPMENT_SUCCESS_BALANCE = "(//div[contains(@data-bind,'paymentSuccessOrFailureModal')]//span[contains(@data-bind,'showInnroadCurrency')])[1]";
	public static final String CANCEL_PAYMENT_SUCCESS_AMOUNT = "(//div[contains(@data-bind,'paymentSuccessOrFailureModal')]//span[contains(@data-bind,'showInnroadCurrency')])[2]";
	public static final String CANCEL_PAYMENT_SUCCESS_TYPE = "//div[contains(@data-bind,'paymentSuccessOrFailureModal')]//span[contains(@data-bind,'text : type')]";
	public static final String CANCEL_PAYMENT_POPUP_CLOSE = "//button[contains(@data-bind,'onCloseCancellation')]";
	public static final String CANCEL_MRB_SECONDARY = "//li[text()='Cancel']";
	
	public static final String CP_NewReservationButton = "//a[.='New Reservation']";
	public static final String CP_NewReservation_CheckInDate = "//label[text()='check In']/following-sibling::input";
	public static final String CP_NewReservation_CheckOutDate = "//label[text()='check Out']/following-sibling::input";
	public static final String CP_NewReservation_Adults = "//label[text()='Adults']/preceding-sibling::input";
	public static final String CP_NewReservation_Children = "//label[text()='Children']/preceding-sibling::input";
	public static final String CP_NewReservation_Rateplan = "//label[contains(text(),'Rate Plan / Promo')]/..//preceding-sibling::div/button|//label[contains(text(),'Rate Plan / Promo')]/..//preceding-sibling::i";
	//public static final String CP_NewReservation_Rateplan = "//label[contains(text(),'Rate Plan / Promo')]/..//preceding-sibling::div/button";
	public static final String CP_NewReservation_PromoCode = "//label[contains(text(),'Enter Code')]/preceding-sibling::input";
	public static final String CP_NewReservation_SplitReservation = "//u[text()='Split Room Reservation?']";
	public static final String CP_NewReservation_FindRooms = "//*[contains((translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')),'FIND ROOM')]";

	//public static final String CP_NewReservation_AddRoom = "//button[contains((translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'ADD ROOM')]";
	public static final String CP_NewReservation_AddRoom = "//button[contains(@data-bind,'addNewRoom')]";

/*=======
	public static final String CP_NewReservation_AddRoom = "//button[contains(text(),'Add Room')]";
	
>>>>>>> develop*///div[contains(@data-bind,'with: tripSummaryModel, loadingState:{loadState: loadingData()}')]//span[@title = 'Override Deposit Due']//..//..//following-sibling::span[contains(@class,'ir-switchIcon')]
	public static final String CP_NewReservation_DepositAmount = "//label[contains(text(),'Deposit Due')]/following-sibling::div/div";
	public static final String CP_NewReservation_OverrideDeposit = "(//span[contains(text(),'Override Deposit Due')]/../following-sibling::div/span)[1]";
	public static final String CP_NewReservation_OverrideDepositAmoount = "//label[contains(text(),'Deposit Due')]/following-sibling::div//input";
	public static final String CP_NewReservation_TripTotalAmount = "//div[contains(@class,'desktop-view trip-summary-sticky')]//div[contains(@class,'panel-body')]//div[contains(@data-bind,'tripTotal')]";
	
	public static final String CP_NewReservation_Next = "//span[contains(text(),'NEXT')]/..";
	//public static final String CP_NewReservation_GuestSalutation = "(//button[contains(@class,'salutationBtn')])[1]";
	public static final String CP_NewReservation_GuestSalutation = "(//div[contains(@data-bind,'isCreateGuestProfileModal')]//select[contains(@data-bind,'saluationName')]/following-sibling::div/button)[1]";//"//button[@aria-expanded='false']//span[@class='filter-option pull-left'][text()='Select']/..";
	public static final String CP_NewReservation_GuestSalutation1 = "(//div[contains(@data-bind,'isCreateGuestProfileModal')]//select[contains(@data-bind,'saluationName')]/following-sibling::div/button)[2]";//"//div[@class='btn-group bootstrap-select form-control ir-frm-ctrl open']//span[@class='text'][text()='Select']";
	public static final String CP_NewReservation_GuestFirstName = "//label[text()='Guest ']/preceding-sibling::input";
	public static final String CP_NewReservation_GuestLastName = "//label[text()='Guest ']/../../../../following-sibling::div//input";
	public static final String CP_NewReservation_ContactSalutation = "(//button[contains(@class,'salutationBtn')])[2]";
	public static final String CP_NewReservation_ContactFirstName = "//label[text()='Contact']/preceding-sibling::input";
	public static final String CP_NewReservation_ContactLastName = "//label[text()='Contact']/../../../../following-sibling::div//input";
	public static final String CP_NewReservation_Phone = "//input[contains(@data-bind,'mailingAddress.phone.number')]";//"(//label[text()='Phone']/preceding-sibling::input)";
	public static final String CP_NewReservation_Phone2 = "(//label[text()='Phone']/preceding-sibling::input)[2]";
	public static final String CP_NewReservation_AltenativePhone = "//input[contains(@data-bind,'mailingAddress.alternatePhone.number')]";//"(//label[text()='Alternate Phone']/preceding-sibling::input)";
	public static final String CP_NewReservation_Email = "(//label[text()='E-mail']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_Account = "(//label[text()='Account']/preceding-sibling::input)";
	public static final String CP_NewReservation_Address1 = "//input[contains(@data-bind,'mailingAddress.address1')]";//"(//label[text()='Address 1']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_Address2 = "//input[contains(@data-bind,'mailingAddress.address2')]";//"(//label[text()='Address 2']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_Address3 = "(//label[text()='Address 3']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_City = "//input[contains(@data-bind,'mailingAddress.city')]";//"(//label[text()='City']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_Country = "//div[contains(@data-bind,'isCreateGuestProfileModal')]//select[@name='country']/..//button";//"(//select[@name='country'])[1]/following-sibling::div/button";
	public static final String CP_NewReservation_State = "//div[contains(@data-bind,'isCreateGuestProfileModal')]//select[@name='state']/..//button";//"(//select[@name='state'])[1]/following-sibling::div";
	public static final String CP_NewReservation_PostalCode = "//input[contains(@data-bind,'mailingAddress.postalCode')]";//"(//label[contains(text(),'Postal Code')])[1]/preceding-sibling::input";
	public static final String CP_NewReservation_CreateGuestProfileCheckbox = "//span[contains(text(),'Create Guest Profile')]/..//input";
	public static final String CP_NewReservation_CreateGuestProfile = "//span[contains(text(),'Create Guest Profile')]/..";
	public static final String CP_NewReservation_AddMoreGuestInfo = "(//button[contains(text(),'Add More')])"; //"(//button[text()='Add More Guest Info'])[2]";
	
	public static final String CP_NewReservation_PaymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//button";
	public static final String CP_NewReservation_CardNumber = "(//label[text()='Credit Card Number']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_NameOnCard = "//label[text()='Name On card']/preceding-sibling::input";
	public static final String CP_NewReservation_Swipe = "//button[text()='SWIPE']";
	public static final String CP_NewReservation_CardExpDate = "(//label[text()='Exp Date']/preceding-sibling::input)";
	public static final String CP_NewReservation_BillingNotes = "//label[text()='Billing Notes']/preceding-sibling::textarea";
	public static final String CP_NewReservation_TaxExemptionOption = "//span[contains(@data-bind,'TaxExemptionOption')]";
	public static final String CP_NewReservation_TaxExempt = "//span[text()='Tax Exempt']";
	public static final String CP_NewReservation_TaxExemptID = "//input[contains(@data-bind,'taxExemptId')]";
	public static final String CP_NewReservation_SameAsMailingAddress = "//small[contains(text(),'Address')]/../../following-sibling::div//input";
	public static final String CP_NewReservation_GiftCertNumber = "(//label[text()='Gift Certificate']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_HouseAccountName = "(//label[text()='Account Name']/preceding-sibling::input)[1]";
	public static final String CP_NewReservation_ResNumberPayment = "(//label[text()='Reservation']/preceding-sibling::input)[1]";
		
	public static final String CP_NewReservation_TravelAgent = "//label[text()='Travel Agent']/preceding-sibling::input";
	public static final String CP_NewReservation_Market = "//label[text()='Market']/preceding-sibling::div/button";
	public static final String CP_NewReservation_Referral = "(//label[text()='Referral']/preceding-sibling::div/button)";
	public static final String CP_NewReservation_Referral2 = "(//label[text()='Referral']/preceding-sibling::div/button)[2]";
	public static final String CP_NewReservation_Book = "//span[text()='Book Now' or text()='BOOK NOW']/..";
	public static final String CP_NewReservation_Quote = "//span[text()='Save Quote' or text()='SAVE QUOTE']/..";
	public static final String CP_NewReservation_QuoteBook = "//button[contains(@data-bind,'ReservationStatus.Quote')]";
	public static final String CP_NewReservation_QuoteBookConfirm = "//button[contains(@data-bind,'bookNow')]/span[text()='CONFIRM BOOKING']";
	public static final String CP_NewReservation_QuoteBookConfirm2 = "(//button[contains(@data-bind,'bookNow')])[2]/span";
	public static final String CP_NewReservation_ProceedPay = "//button[contains(@data-bind,'PaymentProcessButton')]";
	public static final String CP_NewReservation_PaymentClose = "//div[contains(@data-bind,'paymentSuccessOrFailureModal')]//button[contains(text(),'CLOSE')]";
	public static final String CP_NewReservationConfirmBookingClose = "	//div[contains(@data-bind,'confirmBookingSuccessModal')]//button[contains(text(),'CLOSE')]";
	public static final String CopyButton = "//button[contains(text(),'COPY')]";
	public static final String Copy_Reservation_Trimname = "//span[contains(@data-bind,'trimText: ValueText') and contains(text(),'copy')]";
	public static final String CopyGuest_FromPrimaryRoom = "//span[contains(text(),'from Primary Room')]";
	public static final String CopyGuest_FromPrimaryRoomCheckbox = CopyGuest_FromPrimaryRoom+"/../input";
	
	public static final String CP_NewReservation_ProceedToBookingPayment_Button = "//button[contains(text(), 'PROCEED TO BOOKING PAYMENT')]";
	public static final String CP_NewReservation_DepositPayPopUp = "//h4[text()='Deposit Payment']";
	public static final String CP_NewReservation_ConfirmationNumber = "(//h4[contains(text(),'Confirmation')]/../..//strong/following-sibling::span)[1]";
	public static final String CP_NewReservation_ReservationStatus = "//h4[contains(text(),'Confirmation')]/../..//strong[text()='Status:']/following-sibling::span";
	public static final String CP_NewReservation_ClosePopUp = "//h4[contains(text(),'Confirmation')]/../..//button[text()='Close']";
	public static final String CP_NewReservation_Folio = "//a[text()='Folio(s)']/..";
	public static final String CP_NewReservation_History = "//a[text()='History']/..";
	//public static final String CP_NewReservation_AddRoom = "//button[text()='ADD A ROOM']";
	public static final String CP_NewReservation_DetailsTab = "//a[text()='Details']/..";
	public static final String CP_NewReservation_DocumentsTab = "//a[contains(text(),'Documents')]";
	
	public static final String CP_AddNotes = "//a[contains(text(),'ADD NOTE')]";
	public static final String CP_AddTask = "//button[text()='ADD TASK']";
	
	public static final String CP_NoteFor = "//label[text()='CREATE NOTES FOR']/..//button";
	public static final String CP_NoteType = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Type']/..//button";
	public static final String CP_NoteSubject = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Subject']/..//input";
	public static final String CP_NoteDescription = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Description']/preceding-sibling::textarea";
	public static final String CP_NoteAddButton = "//h4[text()='Add Notes']/../..//button[text()='ADD' or text()='Add']";
	
	

	//Modifications
	//Stay Info
	public static final String EDIT_STAY_INFO_FOR_MRB = "//div[contains(@class,'stayinfo-room-details-view')]//div[1]//div[1]//div[1]//div[2]//button[2]//span[1]";
	public static final String EDIT_STAY_INFO_3DOT_ICON_MRB = "(//span[@class='icon custom-icon-more ir-fontSize-18 ir-color-blue'])";
	public static final String EDIT_STAY_INFO = "//button[contains(@class,'ir-stay-info-edit-btn')]";
	public static final String CHANGE_STAY_DETAILS = "//li[contains(text(),'Change Stay Details')]";
	public static final String ASSIGN_ROOM_NUMBER = "//li[contains(text(),'Assign  room Number')]";
	public static final String RECALCULATE_RATE = "//input[contains(@data-bind,'ReCalculate,')]/..";
	public static final String CHAGNE_ONLY_FOR_DATES = "//input[contains(@data-bind,'ApplyDelta')]/..";
	public static final String NO_RATE_CHANGE = "//input[contains(@data-bind,'NoChange')]/..";
	public static final String STAY_INFO_CHECKIN_DATE = "//label[text()='check In' or text()='Check In']/following-sibling::input";
	public static final String STAY_INFO_CHECKOUT_DATE = "//label[text()='check Out' or text()='Check Out']/following-sibling::input";
	public static final String STAY_INFO_ADULTS = "//label[text()='Adults']/preceding-sibling::input";
	public static final String STAY_INFO_CHILDREN = "//label[text()='Children']/preceding-sibling::input";
	public static final String STAY_INFO_ADULTS_DROPDOWN = "//label[text()='Adults']/preceding-sibling::i";
	public static final String STAY_INFO_CHILDREN_DROPDOWN = "//label[text()='Children']/preceding-sibling::i";
	public static final String STAY_INFO_ADULTS_SELECTED = "//label[text()='Adults']/preceding-sibling::div//li[@class='selected']";
	public static final String STAY_INFO_CHILDREN_SELECTED = "//label[text()='Children']/preceding-sibling::div//li[@class='selected']";
	public static final String STAY_INFO_RATEPLAN = "//label[contains(text(),'Rate Plan / Promo')]/../div/button";
	public static final String STAY_INFO_PROMO_CODE = "//label[contains(text(),'Enter Code')]/preceding-sibling::input";
	public static final String STAY_INFO_SPLIT_RESERVATION = "Split Room Reservation?";
	public static final String STAY_INFO_FIND_ROOMS = "//span[contains(text(),'find room')]"; //"//span[contains(@class,'glyphicon-search visible')]";
	public static final String STAY_INFO_ADD_ROOM = "//button[contains(text(),'Add Room')]";
	public static final String STAY_INFO_SAVE = "(//span[contains(text(),'Save')])[2]";
	public static final String STAY_INFO_CONFIRM_YES = "(//button[text()='Yes'])[2]";
	public static final String STAY_INFO_CONFIRM_NO = "(//button[text()='No'])[3]";
	public static final String STAY_INFO_THREE_DOTS = "(//span[contains(@class,'icon custom-icon-more')]/..)";
	public static final String STAY_INFO_SPLIT_INTO_SEPARATE_RESERVATION = "//li[text()='Split into Separate Reservation']";
	public static final String dialog = "//div[contains(@class,'ir-cp-confirmation-dialog in')]";
	
	public static final String INPUT_SEARCH_RESERVATION = "//input[@id='txtResNum']";
	public static final String BUTTON_SEARCH_RESERVATION = "//button[contains(@value,'Search')]";
	
	public static final String TOASTER_MESSAGE = "//div[@class='toast-message']";
	//public static final String TOASTER_MESSAGE = "";
	//Guest Info
	public static final String EDIT_GUEST_INFO = "//span[contains(@data-bind,'editGuestInfo')]";
	public static final String ADD_MORE_GUEST_INFO = "//button[contains(text(),'Add More')]";
	public static final String ADDITIONAL_GUEST_FIRST_NAME = "//button[contains(text(),'Add More')]/../../..//input[contains(@data-bind,'firstName')]";
	public static final String ADDITIONAL_GUEST_LAST_NAME = "//button[contains(text(),'Add More')]/../../..//input[contains(@data-bind,'lastName')]";
	public static final String ADDITIONAL_GUEST_EMAIL = "//button[contains(text(),'Add More')]/../../..//input[contains(@data-bind,'email')]";
	public static final String ADDITIONAL_GUEST_PHONE = "//button[contains(text(),'Add More')]/../../..//input[contains(@data-bind,'phone.number')]";
	public static final String CONTACT_INFO = "//span[contains(text(),'Contact Info')]";
	
	public static final String checkOutButton = "//button[contains(@data-bind,'initializeCheckOut')]";
	public static final String checkOut_GenerateGuestStatement = "//h4[text()='Check Out']/../..//span[contains(@data-bind,'toggleEnableReportGeneration')]|//span[contains(@data-bind,'generateGuestStatement')]";
	public static final String checkOut_GenerateGuestStatementOff = "//h4[text()='Check Out']/../..//span[contains(@data-bind,'toggleEnableReportGeneration')]|//span[contains(@data-bind,'generateGuestStatement') and contains(@class,'off')]";
	public static final String checkOutAllNoButton = dialog + "//button[text()='No' or text()='NO']";
	public static final String checkOutAllYesButton = dialog + "//button[text()='Yes' or text()='YES']";
	public static final String checkOutConfirm = "(//button[contains(@data-bind,'onConfirmClick')])[3]";
	public static final String checkOutProceedToCheckOutPayment = "(//button[contains(@data-bind,'onConfirmClick')])[3]";
	public static final String checkOutPaymentHeader = "//h4[text()='Check Out Payment']";
	public static final String checkOutPaymentMethod = "//h4[text()='Check Out Payment']/../..//label[text()='PAYMENT METHOD']/..//button";
	public static final String checkOutPaymentSetAsMainPaymentMethodCheckBox = "//span[text()='Set As Main Payment Method'][contains(@data-bind,'PaymentCheckSetAsMainMethodText')]/../input";
	public static final String checkOutPaymentSetAsMainPaymentMethodCheckBoxClick = "//span[text()='Set As Main Payment Method'][contains(@data-bind,'PaymentCheckSetAsMainMethodText')]/../span";
	public static final String checkOutPaymentLogAsExternalPaymentCheckBox = "//span[contains(text(),'Log as External Payment')]/../input";
	public static final String checkOutPaymentAddNotes = "//button[contains(@data-bind,'IsPaymentNotes')]";
	public static final String checkOutPaymentNotesInput = "//input[contains(@data-bind,'PaymentNotes')]";
	public static final String checkOutPaymentPopupAmount = "//input[@id='payFormAmountV2']";
	public static final String checkOutPaymentPayButton = "//span[contains(text(),'Pay')][contains(@data-bind,'PaymentProcessButtonText')]/parent::a";
	public static final String checkOutPaymentSuccessfulClose = "//h4[text()='Check out Successful']/../..//button[text()='CLOSE']";
	public static final String checkOutPaymentSuccessfull = "//h4[text()='Check out Successful']";
	public static final String checkOutSuccessPaymentDate = "//span[contains(@data-bind,'checkOutResult.paymentDate')]";
	public static final String checkOutSuccessBalance = "//span[contains(@data-bind,'paymentFolioBalance')]";
	public static final String checkOutSuccessPaymentAmount = "//span[contains(@data-bind,'paymentAmount')]";
	public static final String checkOutSuccessType = "//span[contains(@data-bind,'paymentType')]";
	public static final String checkOutSuccessPaymentMethod = "//span[contains(@data-bind,'paymentInformation')]";
	public static final String checkOutSuccessPaymentStatus = "//label[contains(@data-bind,'paymentStatus')]";
	
	public static final String reservationStatusDropdown = "//i[contains(@data-bind,'ReservationUpdate')]";
	public static final String rollBackButton = "//button[contains(@data-bind,'RollBack')]"; //"//button[contains(@data-bind,'enableRollBackActionBtn')]";
	public static final String rollBackButtonInDropdown = "//span[contains(@data-bind,'reservationStatusItem') and contains(text(),'Rollback')]"; //"//a[contains(@data-bind,'changeReservationStatus')]";

	public static final String GUEST_FOLIO_TAB = "//a[contains(@data-bind,'parent.handleTabSelected') and contains(text(),'Folio')]";
	public static final String GUEST_FOLIO_ADD_CHARGE = "//button[contains(@data-bind,'ClickOnCharge') and contains(text(),'Add Charge')]";
	public static final String GUEST_FOLIO_ADD_NEW_DATE = "//input[contains(@data-bind,'effectiveDate')]";
	public static final String GUEST_FOLIO_ADD_NEW_CATEGORY = "//button[contains(@data-bind,'category')]";
	public static final String GUEST_FOLIO_SearchCategoryName = "//input[@placeholder ='Search category name']";
	public static final String GUEST_FOLIO_ADD_NEW_DESCRIPTION = "//input[contains(@data-bind,'description') and contains(@placeholder,'Add description')]";
	public static final String GUEST_FOLIO_ADD_NEW_QUANTITY = "//input[contains(@data-bind,'value: quantity')]";
	public static final String GUEST_FOLIO_ADD_NEW_AMOUNT = "//td[@class='lineitem-amount']//input";
	public static final String GUEST_FOLIO_ADD_NEW_NOTE_ICON = "(//button[contains(@data-bind,'!$data.note')])[1]";
	public static final String GUEST_FOLIO_ADD_NEW_NOTE_TEXTFIELD = "(//textarea[@placeholder='Enter Notes'])[1]";
	public static final String GUEST_FOLIO_ADD_NEW_NOTE_ADD_NOTE_BUTTON = "//span[text()='Add Notes']/..";
	public static final String GUEST_FOLIO_SAVE_CHANGES = "//span[contains(@data-bind,'SavingLineItems') and contains(text(),'Save')]";
	
	public static final String GUEST_FOLIO_APPLY_ROUTING_DROPDOWN = "//button[contains(@data-bind,'ApplyRoutingEnable')]";
	public static final String GUEST_FOLIO_APPLY_ROUTING_ALLCHARGES = "//span[contains(text(),'All Applicable Charges')]/..";
	public static final String GUEST_FOLIO_APPLY_ROUTING_SELECTEDCHARGES = "//span[contains(text(),'Selected Charges Only')]/..";
	
	public static final String GUEST_FOLIO_APPLY_ROUTING_POPUP_HEADER = "//h4[contains(text(),'Apply Routing')]";
	public static final String GUEST_FOLIO_APPLY_ROUTING_POPUP_PROCEED_BUTTON= "//button[contains(@data-bind,'handleApplyRoutingProceedClick')]";
	
	
	
	public static final String Check_Reservation = "//tbody[@data-bind='foreach: ReservationList']//div[@class='checker']";
	public static final String Click_Bulk_Action = "//button[@title='Bulk Action']";
	public static final String Select_Checkin = "//span[.='Check In']";
	public static final String Verify_Bulk_Checkin_popup = "//h4[.='Bulk Check In']";
	public static final String Verify_Guest_Name = "//div[@id='tab1']//span[contains(@data-bind,' text: GuestName')]";
	public static final String Click_Process_Button = "//button[contains(@data-bind,'click: ValidateAndProcess')]";
	public static final String Verify_Bulk_Checin_Completed = "//h4[.='Bulk Check In Completed']";
	public static final String Verify_Bulk_Checkin_Success = "//span[.='1']";
	public static final String click_on_Close_icon = "//button[@data-bind='click: closeBulkPopup']";
	public static final String Get_Reservation_Status = "//span[contains(@data-bind,'text: StatusName.replace')]";
	public static final String Select_Checkout = "//span[.='Check Out']";
	public static final String Verify_Bulk_Checkout_popup = "//h4[.='Bulk Checkout']";
	public static final String Verify_Bulk_Checkout_Completed = "//h4[.='Bulk Checkout Completed']";
	public static final String Click_On_First_SearchResult = "(//td//a[@title])[1]";
	public static final String Check_First_SearchResult = "(//td//input[contains(@data-bind,'DeleteReservation')])[1]";
	public static final String Select_Delete = "//span[.='Delete']";
	public static final String Verify_Bulk_Delete_popup = "//h4[.='Bulk Delete']";
	public static final String Verify_Bulk_Delete_Completed = "//h4[.='Bulk Delete Completed']";
	public static final String Close_BulkActionPopup = "//button[contains(@data-bind,'visible: !BulkOperationInProcess()')]";

	// Bulk Cancellation
	public static final String selectAllArrivals = "//li[@class='all-arrivals active predefinedQueryOption']";
	public static final String select_AllArrivals = "//li[@class='all-arrivals predefinedQueryOption']";
	
	public static final String ClickOnCheckBoxProperty = "(//input[contains(@data-bind,'enable: EnableSelectAll')])[1]/..";
	public static final String ResBulkCanUpdatedTab = "(//span[contains(@data-bind,'text: ProcessedList().length')]/..)[1]";
	public static final String ResBulkCanNotUpdatedTab = "(//span[contains(@data-bind,'text: UnProcessedList().length')]/..)[1]";
	public static final String ClickProcessButtonBulkCheckIN = "//h4[contains(text(),'Bulk Check In')]/../..//button[text()='Process']";
	public static final String Verify_resNumber1BulkCheckInCanNotUpdate = "(//div[@id='tab2']//span[contains(@data-bind,'text: ConfirmationNumber')])[1]";
	public static final String VerifyBulkCheckInCompletePopUp = "//h4[.='Bulk Check In Completed']";
	public static final String ClickResCanNotUpdateAfterProcess = "(//div[@class='panel-group']//..//following-sibling::a)[2]";
	public static final String CloseBulkActionPopUp = "(//div[@class='ng-modal-content']//following-sibling::div//button[contains(text(),'Close')])[2]";
	public static final String ClickProcessButtonBulkCheckOut = "//h4[contains(text(),'Bulk Check')]/../..//button[text()='Process']";
	public static final String VerifyBulkCheckOutCompletePopUp = "//h4[.='Bulk Checkout Completed']";
	public static final String ClickResCanUpdateAfterProcess = "(//div[@class='panel-group']//..//following-sibling::a)[1]";
	public static final String closeBulkCheckoutPopUp = "(//div[@class='ng-modal-content']//following-sibling::div//button[contains(text(),'Close')])[1]";
	public static final String reservationStatusInDetailSection = "(//span[contains(@data-bind, 'reservationViewStatuses.titleLineStatus')])[1]";
	public static final String bulkCheckOutSuccessHeading = "//h4[.='Bulk Checkout Completed']";
	public static final String bulkCheckOutSuccessReservationCount = "(//span[@data-bind='text: ProcessedList().length'])[2]";
	public static final String bulkActionPopup = "//div[contains(@data-bind,'BulkReservationsActionContainer')]";
	public static final String bulkCancelReasonInput = "(//h4[text()='Bulk Cancel']/../..//textarea)[2]";
	public static final String bulkCancelVoidRoomCharges = "//input[contains(@data-bind,'enable: BulkVoidChargesEnable')]";
	public static final String clickProcessBulkAction = "//h4[contains(text(),'Bulk')]/../..//button[text()='Process']";
	public static final String bulkActionPostFee = "//input[contains(@data-bind,'BulkPostFeesEnable')]";
	public static final String clickProcessBulkUpdate = "//h4[contains(text(),'Bulk')]/../..//button[text()='Process']";
	
	public static final String takePaymentButton = "//button[contains(@data-bind,'takePayment')]";
	public static final String takePaymentHeader = "//h4[text()='Take Payment']";
	public static final String takePaymentPayButton = "//span[contains(@data-bind,'PaymentProcessButtonText')]|//a[contains(@data-bind,'IsPaymentFormValid')]/span[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'LOG')]|//a[contains(@data-bind,'PaymentProcessButtonText')][contains(text(),'Pay')]|//a[contains(text(),'Authorize')]|//a[contains(text(),'Refund')]";
	public static final String takePaymentAmountInput = "//h4[text()='Take Payment']/../..//label[text()='AMOUNT']/following-sibling::div/input";
	public static final String takePaymentTypeButton = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//button";
	public static final String takePaymentPaymentMethod = "//h4[text()='Take Payment']/../..//label[text()='PAYMENT METHOD']/..//button";
	
	
	public static final String clickIncidentals = "//a[contains(@data-bind,'incidentals')]";
	public static final String EnterAddOn_IncidenalDate = "//div[text()='ADD-ONS & INCIDENTALS']//..//input[contains(@data-bind,'datePickerCalendar')]";
	public static final String ButtonSelectIncidental = "//div[text()='ADD-ONS & INCIDENTALS']//..//button[@title='--Select--']";
	public static final String EnterPerUnit = "//div[text()='ADD-ONS & INCIDENTALS']//..//input[contains(@data-bind,'value: newRow.perUnit')]";
	public static final String EnterQuentity = "//div[text()='ADD-ONS & INCIDENTALS']//..//input[contains(@data-bind,'value: newRow.quantity')]";
	public static final String GetIncidentalDate = "//div[text()='ADD-ONS & INCIDENTALS']//..//td[@data-bind='text:effectiveOn']";
	public static final String GetIncidentalCategory = "//div[text()='ADD-ONS & INCIDENTALS']//..//td[@data-bind='text:category']";
	public static final String GetIncidentalDescription = "//div[text()='ADD-ONS & INCIDENTALS']//..//td[@data-bind='text:description']";
	public static final String GetIncidentalPerUnit = "//div[text()='ADD-ONS & INCIDENTALS']//..//td[contains(@data-bind,'value: perUnitPrice')]";
	public static final String GetIncidentalQuentity = "//div[text()='ADD-ONS & INCIDENTALS']//..//td[contains(@data-bind,'text: quantity')]";
	public static final String GetIncidentalTotalAmount = "//div[text()='ADD-ONS & INCIDENTALS']//..//td[contains(@data-bind,'value: chargeSummary.totalAmount')]";
	public static final String GetIncidentalTax = "//div[text()='ADD-ONS & INCIDENTALS']//..//td[contains(@data-bind,'value: chargeSummary.totalTaxAmount')]";
	public static final String IncidentalSave = "//div[text()='ADD-ONS & INCIDENTALS']//..//button[contains(@data-bind,'enable: saveEnabled')]";

	public static final String closePaymentSuccessPopup = "//h4[text()='Payment Successful']/../..//button[text()='Close' or text()='CLOSE' or text()='Continue']|//h4[text()='Authorization Successful']/../..//button[contains(text(),'Continue')]";
	
	public static final String guestFolioPayButton = "//button[contains(@data-bind,'handlePay')]";


	//public static final String guestDetailsRefundButton = "//button[contains(text(),'Refund')]";
	public static final String guestDetailsRefundButton = "(//div[@data-bind='with : moduleViewModels']//button[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'REFUND')])";

	public static final String refundPopupHeader = "//h4[@class='modal-title' and text()='Refund']";
	public static final String refundPopupAmount = "//input[contains(@data-bind,'refundAmount')]";
	public static final String refundPopupRefundButton = "//button[contains(@data-bind,'handleRefundCustom')]";

	public static final String STAY_INFO_CLOSE_ICON = "(//span[contains(@data-bind,'close')])[1]";
	
	public static final String EDIT_MARKETING_INFO_ICON = "//span[contains(@data-bind,'isEditMarketInfo')]";
	public static final String MARKETING_INFO_SAVE = "//span[text()='Save']/parent::button[contains(@data-bind,'saveMarketingInfo')]";
	public static final String RESERVATION_NUMBER = "//reservation-statusbar/div/div[1]/div[1]/div/div[1]/div[2]/span[2]";
	
	//Reservation Page Fields as per ACF Changes - guestInfoSectiononNewResPage
	public static final String NEW_RESERVATION_GUEST_INFO_SECTION = "//div[@id='GuestInfoModule']";
	public static final String FORCE_GUEST_PROFILE_LABEL = "//label[text()='Force Guest Profile']";
	public static final String FORCE_GUEST_PROFILE_CHECKBOX = "//input[@id='MainContent_chkInncenterGuestProfileCreation']";
	public static final String CREATE_GUEST_BUTTON = "//span[text()='Create Guest']"; 
	// //span[text()='Create Guest']/parent::a[contains(@data-bind,'createGuestProfile')]";
	public static final String CREATE_GUEST_PROFILE_TOGGLE = "//span[contains(@data-bind,'toggleCreateGuestProfile')]";
	
	public static final String GUEST_PROFILE_TOGGLE_ON_CLASS = "ir-switchIcon switch-guest-profile";
	public static final String GUEST_PROFILE_TOGGLE_OFF_CLASS = "ir-switchIcon switch-guest-profile off";
	//public static final String GUEST_PROFILE_SAVE = "//button[@type='button']//span[@class='btn-text-value'][text()='Save']";
	public static final String GUEST_PROFILE_SAVE = "//span[text()='Save']/parent::button[contains(@data-bind,'saveGuest')]";
	public static final String VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE = "//div[@data-bind='visible: isInvalidCreate()']";
	
	public static final String ADD_MORE_GUESTS = "//button[contains(@data-bind,'isMoreGuestsButtonEnabled')]";
	public static final String ADD_MORE_GUESTS_WARNING_POPUP = "//div[contains(text(),'The number of guest names added surpasses the number of guests booked in the room.')]/..//button[text()='Yes']";

	public static final String CP_NewReservation_GiftCertNumber2 = "(//label[text()='Gift Certificate']/preceding-sibling::input)[2]";
	
	public static final String GUEST_INFO_PHONE_COUNTRY_CODE = "//input[contains(@data-bind,'mailingAddress.phone.countryCode')]";
	public static final String GUEST_INFO_PHONE_EXTENSION = "//input[contains(@data-bind,'mailingAddress.phone.extension')]";
	public static final String GUEST_INFO_ALTERNATEPHONE_COUNTRY_CODE = "//input[contains(@data-bind,'mailingAddress.alternatePhone.countryCode')]";
	public static final String GUEST_INFO_ALTERNATEPHONE_EXTENSION = "//input[contains(@data-bind,'mailingAddress.alternatePhone.extension')]";

	//Payment Info - PI
	public static final String EDIT_PAYMENT_INFO_ICON = "//span[contains(@data-bind,'isPaymentInfo')]";
	public static final String PI_BILLING_ADDRESS1 = "//div[contains(@data-bind,'addressValidationOptions')]//input";
	public static final String PI_BILLING_ADDRESS2 = "//input[contains(@data-bind,'address2,valueUpdate')]";
	public static final String PI_BILLING_ADDRESS3 = "//input[contains(@data-bind,'address3,valueUpdate')]";
	public static final String PI_BILLING_CITY = "(//input[contains(@data-bind,'city,valueUpdate')])[1]";
	public static final String PI_BILLING_COUNTRY = "(//select[@name='country'])[2]/following-sibling::div/button";
	public static final String PI_BILLING_STATE = "(//select[@name='state'])[2]/following-sibling::div/button";
	public static final String PI_BILLING_POSTALCODE = "(//label[contains(text(),'Postal Code')])[3]/preceding-sibling::input";
	public static final String PAYMENT_INFO_SAVE = "//span[text()='Save']/parent::button[contains(@data-bind,'savePaymentInfo')]";
	
	public static final String GUEST_INFO_ACCOUNT ="//input[contains(@data-bind,'guestInfo-account')]";
	
	//public static final String SPLIT_ROOM_CHKBOX = "//input[@data-bind='checked :isSplitRooms, disable: !isCreateReservation']";
	public static final String SPLIT_ROOM_CHKBOX = "//div[contains(@data-bind,'visible : currentReservationType() == ReservationType.MultiRoom || !isCreateReservation')]//span[contains(@class,'ir-check-box')]";
	public static final String REFUND_ADD_NOTES_BUTTON = "//button[@class='btn folio-btn folio-btn-secondary add-notes-btn']";
	public static final String REFUND_NOTES = "//input[contains(@data-bind,'value: paymentNotes')]";
	//public static final String REFUND_PAYMENT_METHOD = "//h4[text()='Refund']/../..//label[text()='PAYMENT METHOD']/..//button";
	public static final String REFUND_PAYMENT_METHOD = "//div[@data-bind='visible:!IsPaymentProcessed()']//select//following-sibling::div";
	public static final String REFUND_PAYMENT_METHOD_DOWNARROW = "//h4[text()='Refund']/../..//label[text()='PAYMENT METHOD']/..//button//span[@class='bs-caret']//i";
	public static final String REFUND_PE_pathRefundSpecificTab = "(//span[@class='ir-radio-btn'])[6]";
	public static final String REFUND_PE_pathManualCCCashTab = "(//span[@class='ir-radio-btn'])[7]";
	public static final String REFUND_CLOSE_ICON = "//button[@data-bind='visible: isVisibleModalCloseButton']/img";
	public static final String REFUND_POPUP_TYPE = "//span[@class='filter-option pull-left'][normalize-space()='Refund']";
	public static final String REFUND_LOG_AS_EXTERNAL_CHK = "//label[contains(@data-bind,'isLogAsExternalPaymentDisabled')]//span[contains(@class,'ir-check-box')]";	
	public static final String REFUND_SET_AS_MAIN_METHOD_CHK = "//label[@class='ir-checkbox']//input[contains(@data-bind,'checked: isPaymentSetAsMainMethod')]/following-sibling::span[1]";
	//ResV2 Find Room Elements as per new changes - RATES-4191

	public static final String RV2_RATE_PLAN_DROPDOWN ="(//label[contains(text(),'Rate Plan / Promo')]/..//preceding-sibling::input)";
	public static final String CARD_NUMBER = "//input[contains(@data-bind,'value: CreditCardNo,inputmask:creditcardMaskDisplay')]";
	public static final String NAME_ON_CARD = "//input[contains(@data-bind,'value: NameOnCard')]";
	public static final String EXPIRY_DATE = "//input[contains(@data-bind,'enable: IsCreditCardToken() || IsInnroadPayments(), value: ExpDate, event: { blur: onBlurCreditCardExpDate }')]";
	public static final String takePaymentPaymentMethod1 =  "(//div[@data-bind='visible:!IsPaymentProcessed()']//button)[2]";
	public static final String FOLIO_REFUND_BTN="//button[contains(@data-bind,'$parent.handleClickRefund')]";
	public static final String PAYMENT_LINE_ITEM ="//table[contains(@class,'table-lineItems')]//span[text()='Authorized']/../following-sibling::td/a[contains(@data-bind,'description')]";

	public static final String RESERVATIONV2_PAYMENTMETHOD_EDIT = "//span[contains(@data-bind,'isPaymentInfoEditMode')]/i[contains(@class,'demo-icon')]";
	public static final String RESERVATIONV2_PAYMENTMETHOD_Delete = "(//label[text()='Account Name']/preceding-sibling::input)/../span//i";
	public static final String ADD_CUSTOM_FOLIO_BTN = "//button[contains(@data-bind,'getElement: btnElement, click: chkReservationAccess')]//img";
	public static final String ENTER_CUSTOM_FOLIO_NAME = "customFolioName";
	public static final String ENTER_RES_NO_IN_PAY_REFUND_POPUP = "(//input[@name='reservation-autocomplete'])[last()]";
	public static final String CVV_CODE = "//input[contains(@data-bind,'value: CvvCode,enable:EnableCVV')]";
	public static final String REFUND_NOTE_4_EXTERNAL_PAYMENT = "//span[contains(@class,'ir-popover-default transactionDeclinedMessage-col-message')][normalize-space()='Please note that the system will not process the payment through any gateway, it will just mark a record as an external payment.']";

	public static final String SAVE_CUSTOM_FOLIO_BTN = "//button[@data-bind='click: handleSaveClick,enable: isSaveEnabled']";
	public static final String ADVANCE_SEARCH_LINK = "//button[contains(@data-bind,'GoAdvancedLink')]";
	public static final String ADVANCE_SEARCH_ACCOUNT_NUMBER = "//input[contains(@data-bind,'ReservationSearchFilter.AdvAccountNumber')]";
	public static final String ADVANCE_SEARCH_SEARCH_BUTTON = "//div[contains(@data-bind,'with:ReservationAdvanceSearch')]//button[contains(@data-bind,'GoAdvanced')]";
	public static final String ADVANCE_SEARCH_BASIC_BUTTON = "//div[contains(@data-bind,'with:ReservationAdvanceSearch')]//button[contains(@data-bind,'GoBasicLink')]";	
	public static final String FIRST_SEARCHED_RES_CONFIRMATION_NO = "(//span[contains(@data-bind,'text: ConfirmationNumber')])[1]";

	public static final String OVERIDE_DEPOSIT_DUE_TOGGLE ="//div[contains(@data-bind,'with: tripSummaryModel, loadingState:{loadState: loadingData()}')]//span[@title = 'Override Deposit Due']//..//..//following-sibling::span[contains(@class,'ir-switchIcon')]";

	public static final String User_FIRST_NAME="//tbody[@data-bind='foreach:historyLogs']//td//span[contains(@data-bind,'firstName')]";
	public static final String User_LAST_NAME="//tbody[@data-bind='foreach:historyLogs']//td//span[contains(@data-bind,'lastName')]";
	public static final String FOLIO_PAY_BTN = "//button[contains(@data-bind,'handlePay')]";
	public static final String TAKE_PAYMENT_PAYMENT_METHOD="(//label[text()='PAYMENT METHOD'])/preceding-sibling::div[1]//button";	
	public static final String ENTER_HA_NO_IN_PAY_REFUND_POPUP = "(//input[@name='houseaccount-autocomplete'])[last()]";
	public static final String ENTER_GC_NO_IN_PAY_REFUND_POPUP = "(//input[@name='giftCertificate-autocomplete'])[last()]";
	public static final String TAKEPAYMENT_LOG_AS_EXTERNAL_CHK = "//label[contains(@data-bind,'EnablePaymentLogAs')]//span[@class='ir-check-box']";


	public static final String GUEST_PROFILE_SAVE_COPY = "(//span[text()='Save']/parent::button[contains(@data-bind,'saveGuest')])[2]";
	public static final String CP_NewReservation_GuestSalutation_COPY = "(//div[contains(@data-bind,'isCreateGuestProfileModal')]//select[contains(@data-bind,'saluationName')]/following-sibling::div/button)[2]";//"//button[@aria-expanded='false']//span[@class='filter-option pull-left'][text()='Select']/..";
	
	public static final String Toaster_Message_Copy = "//div[@class='toast-message']";
	public static final String Toaster_Message_Xpath_Copy = "//div[@class='toast-message']";
	public static final String Toaster_Message_Close_Copy = "//div[@id='toast-container']//button[@class='toast-close-button']";
	public static final String GUEST_PROFILE_SEARCH = "//input[contains(@data-bind,'getGuestProfileList')]";
	public static final String CHECKOUT_VOID_ROOM_CHARGES="//input[@data-bind='checked:checkOutInfo.isChargeVoided']";

	public static final String EDIT_TASK = "//tbody[contains(@data-bind,'tasks')]/tr";
	public static final String DELETE_TASK = "//td[contains(@class,'ir-delete')]";

	public static final String CP_Reservation_CheckInAllButton = "//div[contains(@class,'ir-sb-right')]";


	public static final String ROOMLOCK="//i[contains(@data-bind,'roomAssignmentLockCss')]";
	public static final String GetTotalAmount="//span[@class='ir-textTrim fw-700']";


	public static final String depositPolicyTextAtDisclaimersV2 = "//textarea[contains(@data-bind,'text:depositPolicyStatement, disable: true')]";
	public static final String DEPOSIT_PAYMENT_CLOSE_POPUP = "//h4[contains(text(),'Deposit Payment')]/preceding-sibling::button[contains(@data-bind,'PaymentCloseButtonClick')]";
	public static final String QUOTE_PROCEED_TO_PAYMENT_BTN ="//button[contains(@data-bind,'click: bookNowPostRenderEvents.proceedToPayment')]//span";
	public static final String QUOTE_BOOKING_PAYMENT_HEADER = "//h4[text()='Booking Payment']";
	public static final String DEPOSIT_DUE_AMOUNT = "(//div[@data-bind='visible: overrideDepositeButtonON()']//input[contains(@data-bind,'depositDueAmountFormatted')])[1]";
	
	public static final String FOLIO_LINEITEM_ADJUST="//button[contains(@data-bind,'$parent.handleClickOnAdjust')]//img";
	public static final String CLOSE_PAYMENT_POPUP = "//button[contains(@data-bind,'PaymentCloseButtonClick')]";
	public static final String SAVE_TASK_BTN = "//h4[text()='Add Task']/../..//button[text()='Save']";
	public static final String EDIT_SAVE_TASK_BTN= "//h4[text()='Edit Task']/../..//button[text()='Save']";
	public static final String CLICK_ADDON = "//a[contains(@data-bind,'onAddOnClick')]";
	public static final String ADDON_DATE_PICKER = "//input[contains(@data-bind,'addOnApplyDate')]";
	public static final String SEARCH_ADDON = "//div[@class='form-group ir-floating-lbl ir-frm-grp ir-icon-group search-addons-input']//input[contains(@data-bind,'searchingFlag')]";
	public static final String ADDON_QUANTITY_INCREASE = "//span[@class='addOn-details']/..//..//..//span[@data-bind='event: { mousedown: $parent.incrementAddOn }']";
	public static final String ADDON_SAVE = "//button[@data-bind = 'enable: addOnsHaveChanged() && !isAddOnsFetching(), click: saveAddonChanges']";

	public static final String RESERVATION_GUEST_INFO_EDIT2 = "(//a[contains(@data-bind,'editGuestInfo')][contains(@class,'edit-icon')])[2]";

}


