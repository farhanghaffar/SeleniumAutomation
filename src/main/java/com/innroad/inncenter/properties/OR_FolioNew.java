package com.innroad.inncenter.properties;

public class OR_FolioNew {
	
	public static final String Stay_Info ="//div[contains(text(),'Stay')]";
	public static final String Guest_Folio ="//a[contains(text(),'Guest')]";
	public static final String AllFolios = "//div[@id='scrollable-container']//a"; //"//div[@id='scrollable-container']/ul/li";

	//public static final String AddCharge = "//li[@data-bind='click : $parent.handleClickOnCharge']//a[@href='#']";//"//button[contains(text(),'Add Charge')]";

	public static final String AddCharge ="//li[contains(@data-bind,'handleClickOnCharge')]/a[contains(text(),'Charge')]";
	public static final String CategorySelect = "//th[contains(@class,'lineitem-category')]/../../..//span[contains(text(),'--Select')]";
	public static final String EnterAmountFolio = "//td[contains(@class,'lineitem-amount')]/div/input";
	public static final String SaveChangesButton = "//span[contains(text(),'Changes')]";
	public static final String ChargesTab = "//li[contains(@class,'folio-listHover')]/a"; //"//li[contains(@class,'folio-listHover')]";
	public static final String ChargesValue = "//li[contains(@class,'folio-listHover')]/a/p";
	public static final String PaymentsTab = "//a[contains(text(),'Payments')]";
	public static final String PaymentValue = "//a[contains(text(),'Payments')]/p";
	public static final String tosterMsg ="//div[contains(text(),'Reservation Saved')]";
	public static final String AuthorizationTab ="//a[contains(text(),'Authorizations')]";
	public static final String AuthorizationValue = "//a[contains(text(),'Authorizations')]/p";
	public static final String BalanceTab = "//a[contains(text(),'Balance')]";
	public static final String BalanceValue = "//a[contains(text(),'Balance')]/p";
	public static final String BalanceGrid ="//li[contains(@class,'folio-listHover')]//following-sibling::li[3]";
	public static final String SettingsIcon ="//div[contains(@class,'tab-top-panel-actions')]//button";
	public static final String FolioName ="//label[contains(text(),'Folio Name')]//parent::div//input";
	public static final String FolioDescription ="//label[contains(text(),'Folio Description')]//parent::div//textarea";
	public static final String ToggleDisplayVoidItems ="//span[contains(text(),'Display Voided Items')]//parent::div//label";
	public static final String ToggleDisplayPendingItems ="//span[contains(text(),'Display Pending Items')]//parent::div//label";
	public static final String ToggleShowAuthReport ="//span[contains(text(),'Show Authorizations in Report')]//parent::div//label";
	public static final String PhoneAccess ="//label[contains(text(),'Phone Access')]//following::div";
	public static final String PosPosting ="//label[contains(text(),'Pos Posting')]//following::div";
	public static final String MovieRestrictions ="//label[contains(text(),'Movie Restrictions')]//following::div";
	public static final String CreditLimit ="//label[contains(text(),'Credit Limit')]//following::div//input";
	public static final String MoveToAccFolio ="//label[contains(text(),'Move to Account Folio')]//following::div";
	public static final String CancelBtn ="//span[contains(@class,'folio-settings-buttons')]//button";
	public static final String SaveBtn ="//span[contains(@class,'folio-settings-buttons')]//button[2]";
	public static final String DdlValuesPhoneAccess ="//label[contains(text(),'Phone Access')]//following::div//div//div//div//ul//li";
	public static final String DdlValuesPosPosting ="//label[contains(text(),'Pos Posting')]//following::div//div//div//div//ul//li";
	public static final String DdlValuesMovieRes ="//label[contains(text(),'Movie Restrictions')]//following::div//div//div//div//ul//li";	
	public static final String DdlValuesMoveToAccFolio="//label[contains(text(),'Move to Account Folio')]//following::div//div//div//div//ul//li";
	public static final String addFolioTypeIcon="//img[contains(@src,'Add.svg')]";
	public static final String folioPaymentDetailsWindow = "//span[contains(text(),'(Guest Folio)')]";
	public static final String folioSaveButton = "//button[contains(@data-bind,'saveChanges')]";
	public static final String payButton = "//button[text()='Pay']";
	
	public static final String bulkActionDropDown = "//span[contains(text(),' Bulk ActionmovelineitemButton')]/..";
	public static final String voidLineItem = "//a[text()='Void']";
	public static final String voidReason = "//label[text()='Void Reason']/../input";
	public static final String voidProceedButton = "//label[text()='Void Reason']/../../../../..//span[text()='Proceed']";
	public static final String folioSettingsIcon = "//button[contains(@data-bind,'showFolioSettings')]";
	public static final String displayVoidItemsCheck = "//span[text()='Display Voided Items']/../label/span[2]";
	public static final String folioSettingsSave = "//button[contains(@data-bind,'handleClickSave')]/span[text()='Save']";
	public static final String SaveChangesButton_RV2 = "//button[contains(@data-bind,'saveChanges')]//span[contains(text(),'Save')]";
	public static final String moveLineItem = "//a[text()='Move']";
	public static final String moveCurrentReservation = "//a[text()='Move']";
	public static final String moveOtherReservation = "//h4[text()='Move Folio Line Items']/../following-sibling::div//input[@value='otherReservation']/../span";
	public static final String moveOtherReservationTextBox = "//h4[text()='Move Folio Line Items']/../following-sibling::div//input[contains(@data-bind,'selectedReseravtionGuestName')]";
	public static final String moveOtherReservationTragetFolio = "//h4[text()='Move Folio Line Items']/../following-sibling::div//button[contains(@title,'Select Folio')]";
	public static final String movelineitemButton = "//div[text()='Move Items']";
	
	public static final String deleteFolioLineItemsNote = "//input[contains(@data-bind,'value: voidNote, valueUpdate')]";
	public static final String voidButton = "//button[contains(@data-bind,'HandleVoidClick')]//span";
	public static final String cancelVoidButton = "//button[contains(@data-bind,'click: handleCancelVoid')]";
	public static final String voidItemToasterMessage = "//div[@class='toast-message']";

	public static final String Charge ="(//a[contains(text(),'Charge')])[1]";
	public static final String FirstFolios = "(//div[@id='scrollable-container']//a)[1]";


}
