package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR_FolioNew;

public class Elements_FolioNew {
	
	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("devpinoyLogger");

	public Elements_FolioNew(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);
		
	}
	
	@FindBy(xpath=OR_FolioNew.FolioName)
	public WebElement EleFolioName;
	
	@FindBy(xpath=OR_FolioNew.FolioDescription)
	public WebElement EleFolioDescription;
	
	@FindBy(xpath=OR_FolioNew.ToggleDisplayVoidItems)
	public WebElement EleToggleDisplayVoidItems;
	
	@FindBy(xpath=OR_FolioNew.ToggleDisplayPendingItems)
	public WebElement EleToggleDisplayPendingItems;
	
	@FindBy(xpath=OR_FolioNew.ToggleShowAuthReport)
	public WebElement EleToggleShowAuthReport;
	
	@FindBy(xpath=OR_FolioNew.PhoneAccess)
	public WebElement ElePhoneAccess;
	
	@FindBy(xpath=OR_FolioNew.PosPosting)
	public WebElement ElePosPosting;
	
	@FindBy(xpath=OR_FolioNew.MovieRestrictions)
	public WebElement EleMovieRestrictions;
	
	@FindBy(xpath=OR_FolioNew.CreditLimit)
	public WebElement EleCreditLimit;
	
	@FindBy(xpath=OR_FolioNew.MoveToAccFolio)
	public WebElement EleMoveToAccFolio;
	
	@FindBy(xpath=OR_FolioNew.tosterMsg)
	public WebElement EleTosterMsg;
	
	@FindBy(xpath=OR_FolioNew.ChargesTab)
	public WebElement ChargesTab;
	
	@FindBy(xpath=OR_FolioNew.ChargesValue)
	public WebElement ChargesValue;
	
	@FindBy(xpath=OR_FolioNew.PaymentsTab)
	public WebElement PaymentsTab;
	
	@FindBy(xpath=OR_FolioNew.PaymentValue)
	public WebElement PaymentValue;
	
	@FindBy(xpath=OR_FolioNew.AuthorizationTab)
	public WebElement AuthorizationTab;
	
	@FindBy(xpath=OR_FolioNew.AuthorizationValue)
	public WebElement AuthorizationValue;
	
	@FindBy(xpath=OR_FolioNew.BalanceTab)
	public WebElement BalanceTab;
	
	@FindBy(xpath=OR_FolioNew.BalanceValue)
	public WebElement BalanceValue;
	
	@FindBy(xpath=OR_FolioNew.AllFolios)
	public List<WebElement> AllFolios;
	
	@FindBy(xpath=OR_FolioNew.AddCharge)
	public WebElement AddCharge;
	
	@FindBy(xpath=OR_FolioNew.CategorySelect)
	public WebElement CategorySelect;
	
	@FindBy(xpath=OR_FolioNew.EnterAmountFolio)
	public WebElement EnterAmountFolio;
	
	@FindBy(xpath=OR_FolioNew.SaveChangesButton)
	public WebElement SaveChangesButton;
	
	@FindBy(xpath=OR_FolioNew.payButton)
	public WebElement payButton;
	
	@FindBy(xpath=OR_FolioNew.bulkActionDropDown)
	public WebElement bulkActionDropDown;
	
	@FindBy(xpath=OR_FolioNew.voidLineItem)
	public WebElement voidLineItem;
	
	@FindBy(xpath=OR_FolioNew.voidReason)
	public WebElement voidReason;
	
	@FindBy(xpath=OR_FolioNew.voidProceedButton)
	public WebElement voidProceedButton;
	
	@FindBy(xpath=OR_FolioNew.folioSettingsIcon)
	public WebElement folioSettingsIcon;
	
	@FindBy(xpath=OR_FolioNew.displayVoidItemsCheck)
	public WebElement displayVoidItemsCheck;
	
	@FindBy(xpath=OR_FolioNew.folioSettingsSave)
	public WebElement folioSettingsSave;
	

	@FindBy(xpath=OR_FolioNew.folioSaveButton)
	public WebElement folioSaveButton;

	@FindBy(xpath=OR_FolioNew.SaveChangesButton_RV2)
	public WebElement SaveChangesButton_RV2;
	
	@FindBy(xpath=OR_FolioNew.moveLineItem)
	public WebElement moveLineItem;
	
	@FindBy(xpath=OR_FolioNew.moveOtherReservation)
	public WebElement moveOtherReservation;
	
	@FindBy(xpath=OR_FolioNew.moveOtherReservationTextBox)
	public WebElement moveOtherReservationTextBox;
	
	@FindBy(xpath=OR_FolioNew.moveOtherReservationTragetFolio)
	public WebElement moveOtherReservationTragetFolio;
	
	@FindBy(xpath=OR_FolioNew.movelineitemButton)
	public WebElement movelineitemButton;
	

	@FindBy(xpath = OR_FolioNew.deleteFolioLineItemsNote)
	public WebElement deleteFolioLineItemsNote;
	
	@FindBy(xpath = OR_FolioNew.voidButton)
	public WebElement voidButton;
	
	@FindBy(xpath = OR_FolioNew.cancelVoidButton)
	public WebElement cancelVoidButton;

	@FindBy(xpath = OR_FolioNew.voidItemToasterMessage)
	public WebElement voidItemToasterMessage;

	@FindBy(xpath=OR_FolioNew.Charge)
	public WebElement Charge;
	
	@FindBy(xpath=OR_FolioNew.FirstFolios)
	public WebElement FirstFolios;
	


}
