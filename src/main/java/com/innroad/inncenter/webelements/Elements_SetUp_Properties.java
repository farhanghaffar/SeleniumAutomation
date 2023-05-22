package com.innroad.inncenter.webelements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Setup;

public class Elements_SetUp_Properties {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("devpinoyLogger");

	public Elements_SetUp_Properties(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);

	}
	
	@FindBy(xpath = OR.propertyName)
	public WebElement propertyName;

	@FindBy(xpath = OR.Property_Grid)
	public WebElement Property_Grid;

	@FindBy(xpath = OR.Property_Options)
	public WebElement Property_Options;

	@FindBy(xpath = OR.Property_CreditCard)
	public WebElement Property_CreditCard;

	@FindBy(xpath = OR.OptionGuaranteed)
	public WebElement OptionGuaranteed;

	@FindBy(xpath = OR.OptionCreditCardAlways)
	public WebElement OptionCreditCardAlways;

	@FindBy(xpath = OR_Setup.Setup_PropertiesGuestStatementOption)
	public WebElement Setup_PropertiesGuestStatementOption;

	@FindBy(xpath = OR_Setup.Setup_PropertiesGuestRegistrationOption)
	public WebElement Setup_PropertiesGuestRegistrationOption;
	
	@FindBy(xpath = OR_Setup.Setup_PropertiesGuestRegistrationOptionCheckout)
	public WebElement Setup_PropertiesGuestRegistrationOptionCheckout;

	@FindBy(xpath = OR_Setup.Setup_PropertiesGuaranteedOption)
	public WebElement Setup_PropertiesGuaranteedOption;

	@FindBy(xpath = OR_Setup.PROPERTY_DEPOSITREQUIRED_SAVEGAURANTEEDRESERVATION)
	public WebElement PROPERTY_DEPOSITREQUIRED_SAVEGAURANTEEDRESERVATION;

	@FindBy(xpath = OR_Setup.PROPERTY_DEPOSITRECORDEDAUTOMATICALLY_SETGAURANTEEDRESERVATION)
	public WebElement PROPERTY_DEPOSITRECORDEDAUTOMATICALLY_SETGAURANTEEDRESERVATION;
	
	@FindBy(xpath = OR_Setup.NonZeroBalanceWhileCheckOut)
	public WebElement NonZeroBalanceWhileCheckOut;
	
	@FindBy(xpath = OR.clickPropertySelect)
	public WebElement clickPropertySelect;
	
	@FindBy(xpath = OR.editPropertySelect)
	public WebElement editPropertySelect;
	
	@FindBy(xpath = OR.inputPropertySelect)
	public WebElement inputPropertySelect;
	
	@FindBy(xpath = OR.clickPropertyName)
	public WebElement clickPropertyName;
	
	@FindBy(xpath  = OR.EmailSetOnPropertyPage)
	public WebElement emailSetOnPropertyPage;
	
	@FindBy(xpath = OR.ReplyToManualCustomRadio)
	public WebElement replyToManualCustomRadio;
	
	@FindBy(xpath = OR.ReplyToManualCustomEmailSet)
	public WebElement replyToManualCustomEmailSet;
	
	@FindBy(xpath = OR.ReplyToscheduledCustomRadio)
	public WebElement replyToscheduledCustomRadio;
	
	@FindBy(xpath = OR.ReplyToscheduledCustomEmailSet)
	public WebElement replyToscheduledCustomEmailSet;
	
	@FindBy(xpath = OR.CCOnAllMailRadio)
	public WebElement cCOnAllMailRadio;
	
	@FindBy(xpath = OR.CConAllMailSet)
	public WebElement cConAllMailSet;

	
	@FindBy(xpath = OR_Setup.taxExemptCheckBox)
	public WebElement taxExemptCheckBox;
	
	@FindBy(xpath = OR_Setup.taxExemptTextField)
	public WebElement taxExemptTextField;
	
	@FindBy(xpath = OR_Setup.taxExemptAllNights)
	public WebElement taxExemptAllNights;
	
	@FindBy(xpath = OR_Setup.taxExemptAfterLimitIsMet)
	public WebElement taxExemptAfterLimitIsMet;

	//Detail Property 
	@FindBy(id = OR.Properties_Save)
	public WebElement Properties_Save;
	@FindBy(id = OR.Enter_PropertyName)
	public WebElement Enter_PropertyName;
	@FindBy(id = OR.Enter_LegalyName)
	public WebElement Enter_LegalyName;
	@FindBy(id = OR.Select_Town)
	public WebElement Select_Town;
	@FindBy(id = OR.Enter_Description)
	public WebElement Enter_Description;
	@FindBy(id = OR.Select_Property_State)
	public WebElement Select_Property_State;
	
	//Option Property Text
	@FindBy(id = OR.AfterlimitMeet)
	public WebElement AfterlimitMeet;
	@FindBy(id = OR.AllNight)
	public WebElement AllNight;
	@FindBy(xpath = OR.PropertyOperationsText)
	public WebElement PropertyOperationsText;
	@FindBy(xpath = OR.PropertyCheckInOutText)
	public WebElement PropertyCheckInOutText;
	@FindBy(xpath = OR.PropertyBookingEngineText)
	public WebElement PropertyBookingEngineText;
	@FindBy(xpath = OR.PropertySystemText)
	public WebElement PropertySystemText;
	@FindBy(xpath = OR.PropertyCancelledReservationText)
	public WebElement PropertyCancelledReservationText;
	@FindBy(xpath = OR.PropertyInventryManagementText)
	public WebElement PropertyInventryManagementText;		
	
	@FindBy(id = OR_Setup.selectTimeZone)
	public WebElement selectTimeZone;
	
	@FindBy(xpath = OR_Setup.requireCreditCardForGuarantee)
	public WebElement requireCreditCardForGuarantee;

	@FindBy(xpath = OR_Setup.forGuaranteedReservation)
	public WebElement forGuaranteedReservation;

	@FindBy(xpath = OR_Setup.always)
	public WebElement always;

	@FindBy(xpath = OR_Setup.depositIsRecordedAutomatically)
	public WebElement depositIsRecordedAutomatically;
	
	@FindBy(xpath = OR_Setup.requireDepositForGuarantee)
	public WebElement requireDepositForGuarantee;
	
	@FindBy(xpath = OR_Setup.PROPERTY_SET_GAURANTEED)
	public WebElement PROPERTY_SET_GAURANTEED;
	
	@FindBy(xpath = OR.PropertyLink)
	public WebElement PropertyLink;
	
	@FindBy(xpath = OR.requiredMarketSegment)
	public WebElement requiredMarketSegment;
	
	@FindBy(xpath = OR.requiredReferrals)
	public WebElement requiredReferrals;
	
	@FindBy(xpath = OR.Properties_interface)
	public WebElement Properties_interface;
}
