package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;

public class Elements_GuestHistory {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("devpinoyLogger");

	public Elements_GuestHistory(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(xpath = OR.reservation_Menu)
	public WebElement reservation_Menu;

	@FindBy(xpath = OR.Guest_History)
	public WebElement Guest_History;

	@FindBy(xpath = OR.newAccount)
	public WebElement newAccount;

	@FindBy(xpath = OR.selectAccountSalutation)
	public WebElement selectAccountSalutation;

	@FindBy(xpath = OR.selectAccountSalutation_1)
	public WebElement selectAccountSalutation_1;

	@FindBy(xpath = OR.accountFirstName)
	public WebElement accountFirstName;

	@FindBy(xpath = OR.accountNumber)
	public WebElement accountNumber;

	@FindBy(xpath = OR.accountSince)
	public WebElement accountSince;

	@FindBy(xpath = OR.Select_Market_Segment)
	public WebElement Select_Market_Segment;

	@FindBy(xpath = OR.Select_Referrals)
	public WebElement Select_Referrals;

	@FindBy(xpath = OR.closeReservation)
	public WebElement closeReservation;

	@FindBy(xpath = OR.guestHistoryAccountNo)
	public WebElement guestHistoryAccountNo;

	@FindBy(xpath = OR.enterAccountFName)
	public WebElement enterAccountFName;

	@FindBy(xpath = OR.enterGAccountLastName)
	public WebElement enterGAccountLastName;

	@FindBy(xpath = OR.enterGuestHistoryAccountNo)
	public WebElement enterGuestHistoryAccountNo;

	@FindBy(xpath = OR.GuestHistory_AccountNumber)
	public WebElement GuestHistory_AccountNumber;

	@FindBy(xpath = OR.clickSearchButton)
	public WebElement clickSearchButton;
	
	@FindBy(xpath = OR.clickSearchButton1)
	public WebElement clickSearchButton1;
	
	/*@FindBy(xpath = OR.clickSearchButtons)
	public List<WebElement> clickSearchButtons;*/
	@FindBy(xpath = OR.clickSearchButtons)
	public WebElement clickSearchButtons;

	@FindBy(xpath = OR.selectGuestHistoryAccount)
	public WebElement selectGuestHistoryAccount;

	@FindBy(xpath = OR.SearcehdGuestHistoryAccountNumber)
	public WebElement SearcehdGuestHistoryAccountNumber;

	@FindBy(xpath = OR.deleteButton)
	public WebElement deleteButton;

	@FindBy(xpath = OR.newReservationButton)
	public WebElement newReservationButton;

	@FindBy(xpath = OR.New_Reservation_Page_Load)
	public WebElement New_Reservation_Page_Load;

	@FindBy(xpath = OR.closeGuestHistoryRes)
	public WebElement closeGuestHistoryRes;

	@FindBy(xpath = OR.ClickSearchedGuestProfile)
	public WebElement ClickSearchedGuestProfile;

	@FindBy(xpath = OR.UnsavedData_YesBtn)
	public WebElement UnsavedData_YesBtn;

	@FindBy(xpath = OR.GuestHis_CombineCheckBox)
	public WebElement GuestHis_CombineCheckBox;

	@FindBy(xpath = OR.GuestHis_CombineCheckBox1)
	public WebElement GuestHis_CombineCheckBox1;

	@FindBy(xpath = OR.ReservationTab_GuestAccount)
	public WebElement ReservationTab_GuestAccount;

	@FindBy(xpath = OR.GuestHistoryAccountStatus)
	public WebElement GuestHistoryAccountStatus;

	@FindBy(xpath = OR.GuestHistoryAccount_BillType)
	public WebElement GuestHistoryAccount_BillType;

	@FindBy(xpath = OR.GuestHistoryAccount_BillingAccountNum)
	public WebElement GuestHistoryAccount_BillingAccountNum;

	@FindBy(xpath = OR.GuestHistoryAccount_ExpDate)
	public WebElement GuestHistoryAccount_ExpDate;

	@FindBy(xpath = OR.GuestHistoryAccount_BillingNotes)
	public WebElement GuestHistoryAccount_BillingNotes;

	@FindBy(xpath = OR.SearchGuestHistoryAccountStatus)
	public WebElement SearchGuestHistoryAccountStatus;
	
	@FindBy(xpath = OR.NewGuestHistoryButton)
	public WebElement NewGuestHistoryButton;
	
	
	@FindBy(xpath = OR.guestAccountFirstName)
	public WebElement guestAccountFirstName;

	@FindBy(xpath = OR.guestAccountLastName)
	public WebElement guestAccountLastName;
	
	@FindBy(xpath = OR.guestAccountFirstName)
	public List<WebElement> guestAccountFirstNames;

	@FindBy(xpath = OR.clickDecyptCCNumberImg)
	public WebElement clickDecyptCCNumberImg;

	@FindBy(xpath = OR.billingAccountNumberInput)
	public WebElement billingAccountNumberInput;

	@FindBy(xpath = OR.guestInfoSaveButton)
	public WebElement guestInfoSaveButton;

	@FindBy(xpath = OR.reservationTabGuestAccount)
	public WebElement reservationTabGuestAccount;
	
	@FindBy(xpath = OR.GUEST_ACCOUNT_FIRST_NAME)
	public WebElement GUEST_ACCOUNT_FIRST_NAME;
	
	@FindBy(xpath = OR.GUEST_ACCOUNT_LAST_NAME)
	public WebElement GUEST_ACCOUNT_LAST_NAME;
	
	@FindBy(xpath = OR.accountSaved)
	public WebElement AccountSaved;
	
	@FindBy(xpath = OR.getAccountOnDetailsPage)
	public WebElement GetAccountOnDetailsPage;
	
	@FindBy(xpath = OR.enterFirstName)
	public WebElement EnterFirstName;
	
	@FindBy(xpath = OR.clickClearOnGuestHistory)
	public WebElement ClickClearOnGuestHistory;
	
	@FindBy(xpath = OR.accountDetailsLastName)
	public WebElement AccountDetailsLastName;
	
	@FindBy(xpath = OR.ClickAddNoteButton)
	public WebElement clickAddNoteButton;
	
	@FindBy(xpath = OR.NoteSubject)
	public WebElement noteSubject;
	
	@FindBy(xpath = OR.Notedetails)
	public WebElement notedetails;
	
	@FindBy(xpath = OR.NoteType)
	public WebElement noteType;
	
	@FindBy(xpath = OR.ClickSaveNote)
	public WebElement clickSaveNote;
	
	@FindBy(xpath = OR.GetAddress1)
	public WebElement getAddress1;
	
	@FindBy(xpath = OR.GetAddress2)
	public WebElement getAddress2;
	
	@FindBy(xpath = OR.GetAddress3)
	public WebElement getAddress3;
	
	@FindBy(xpath = OR.GetCity)
	public WebElement getCity;
	
	@FindBy(xpath = OR.GetPostalCode)
	public WebElement getPostalCode;
	
	@FindBy(xpath = OR.GetState)
	public WebElement getState;
	
	@FindBy(xpath = OR.GetCountry)
	public WebElement getCountry;
	
	
	@FindBy(xpath = OR.GetAccountFirstName)
	public WebElement getAccountFirstName;
	
	@FindBy(xpath = OR.GetAccountLastname)
	public WebElement getAccountLastname;
	
	@FindBy(xpath = OR.GetGuestFirstName)
	public WebElement getGuestFirstName;
	
	@FindBy(xpath = OR.GetGuestLastName)
	public WebElement getGuestLastName;
	
	@FindBy(xpath = OR.GetEmail)
	public WebElement getEmail;
	
	@FindBy(xpath = OR.GetPhone)
	public WebElement getPhone;
	
	@FindBy(xpath = OR.GetAlternatePhone)
	public WebElement getAlternatePhone;
	
	@FindBy(xpath = OR.GetPaymentMethod)
	public WebElement getPaymentMethod;
	
	@FindBy(xpath = OR.GetCardNumber)
	public WebElement getCardNumber;
	
	@FindBy(xpath = OR.GetExpDate)
	public WebElement getExpDate;
	
	@FindBy(xpath = OR.ClickDescriptor)
	public WebElement clickDescriptor;
	
	@FindBy(xpath = OR.UseMailingAddressCheck)
	public WebElement useMailingAddressCheck;
	
	@FindBy(xpath = OR.GetMarketing)
	public WebElement getMarketing;
	
	@FindBy(xpath = OR.GetReferral)
	public WebElement getReferral;
	
	@FindBy(xpath = OR.Number_Of_Accounts)
	public List<WebElement> numberOfAccounts;
	
	@FindBy(xpath = OR.EnterBillingFirstName)
	public WebElement enterBillingFirstName;
	
	@FindBy(xpath = OR.EnterBillingLastName)
	public WebElement enterBillingLastName;
	
	
	@FindBy(xpath = OR.GetBillingFirstName)
	public WebElement getBillingFirstName;
	
	@FindBy(xpath = OR.GetBillingLastName)
	public WebElement getBillingLastName;
	
	@FindBy(xpath = OR.ClickCombine)
	public WebElement clickCombine;
	
	@FindBy(xpath = OR.ClickCombine1)
	public WebElement clickCombine1;
	
	
	@FindBy(xpath = OR.SearchReservation)
	public WebElement searchReservation;
	

	@FindBy(xpath = OR.SearchReservation1)
	public WebElement searchReservation1;
	
	
	@FindBy(xpath = OR.ClickSearch)
	public WebElement clickSearch;
	
	@FindBy(xpath = OR.ClickReset)
	public WebElement clickReset;
	
	//---
	
	@FindBy(xpath = OR.GetBillingEmail)
	public WebElement getBillingEmail;
	
	@FindBy(xpath = OR.GetBillingPhone)
	public WebElement getBillingPhone;
	
	@FindBy(xpath = OR.GetBillingCountry)
	public WebElement getBillingCountry;
	
	
	@FindBy(xpath = OR.GetBillingState)
	public WebElement getBillingState;
	
	
	@FindBy(xpath = OR.GetBillingCity)
	public WebElement getBillingCity;
	
	
	@FindBy(xpath = OR.GetBillingPostal)
	public WebElement getBillingPostal;
	
	
	@FindBy(xpath = OR.GetBillingAddress1)
	public WebElement getBillingAddress1;
	
	@FindBy(xpath = OR.GetBilligAddress2)
	public WebElement getBilligAddress2;
	
	
	@FindBy(xpath = OR.GetBillingAddress3)
	public WebElement getBillingAddress3;
	
	
	@FindBy(xpath = OR.RoomCount)
	public WebElement roomCount;
	
	@FindBy(xpath = OR.ReservationCount)
	public WebElement reservationCount;
	
	@FindBy(xpath = OR.GetUpdatedOn)
	public WebElement getUpdatedOn;
	
	@FindBy(xpath = OR.SelectBullingSalutation)
	public WebElement selectBullingSalutation;
	
	
	@FindBy(xpath = OR.SelectSalutation)
	public WebElement selectSalutation;
	
	@FindBy(xpath = OR.GuestHistorySelected)
	public WebElement guestHistorySelected;
	
	@FindBy(xpath = OR.ClickPrintIcon)
	public WebElement clickPrintIcon;
	
	@FindBy(xpath = OR.ClickExport)
	public WebElement clickExport;
	
	@FindBy(xpath = OR.ClickSearchedGuestProfile)
	public List<WebElement> ClickSearchedGuestProfiles;

	@FindBy(xpath = OR.mailingInfoSalutation)
	public WebElement mailingInfoSalutation;
	
	@FindBy(xpath=OR.phoneCountryCode)
	public WebElement phoneCountryCode;
	
	@FindBy(xpath=OR.alternatePhoneCountryCode)
	public WebElement alternatePhoneCountryCode;
	
	@FindBy(xpath=OR.phoneExtention)
	public WebElement phoneExtention;
	
	@FindBy(xpath=OR.alternatePhoneExtention)
	public WebElement alternatePhoneExtention;
	
	@FindBy(xpath=OR.faxCountryCode)
	public WebElement faxCountryCode;
	
	@FindBy(xpath=OR.faxExtention)
	public WebElement faxExtention;
	
	@FindBy(xpath = OR.billingInfoSaluation)
	public WebElement billingInfoSaluation;
	
	@FindBy(xpath = OR.billingPhoneCountryCode)
	public WebElement billingPhoneCountryCode;
	
	@FindBy(xpath = OR.billingPhoneNumber)
	public WebElement billingPhoneNumber;
	
	@FindBy(xpath = OR.billingPhoneExtention)
	public WebElement billingPhoneExtention;
	
	@FindBy(xpath = OR.billingAlternatePhoneCountryCode)
	public WebElement billingAlternatePhoneCountryCode;
	
	@FindBy(xpath = OR.billingAlternatePhoneNumber)
	public WebElement billingAlternatePhoneNumber;
	
	@FindBy(xpath = OR.billingAlternatePhoneExtention)
	public WebElement billingAlternatePhoneExtention;
	
	@FindBy(xpath = OR.billingAddressLine1)
	public WebElement billingAddressLine1;
	
	@FindBy(xpath = OR.billingAddressLine2)
	public WebElement billingAddressLine2;
	
	@FindBy(xpath = OR.billingAddressLine3)
	public WebElement billingAddressLine3;
	
	@FindBy(xpath = OR.billingPropertyDropdown)
	public WebElement billingPropertyDropdown;
	
	@FindBy(xpath = OR.billingTypeDropdown)
	public WebElement billingTypeDropdown;
	
	@FindBy(xpath = OR.billingFaxCountryCode)
	public WebElement billingFaxCountryCode;
	
	@FindBy(xpath = OR.billingFaxNumber)
	public WebElement billingFaxNumber;
	
	@FindBy(xpath = OR.billingFaxExtention)
	public WebElement billingFaxExtention;
	
	@FindBy(xpath = OR.billingEmail)
	public WebElement billingEmail;
	
	@FindBy(xpath = OR.billingCity)
	public WebElement billingCity;
	
	@FindBy(xpath = OR.billingPostalCode)
	public WebElement billingPostalCode;
	
	@FindBy(xpath = OR.billingStateDropdown)
	public WebElement billingStateDropdown;
	
	@FindBy(xpath = OR.billingCountryDropdown)
	public WebElement billingCountryDropdown;
	
	@FindBy(xpath = OR.AccountsPage_Reset_Btn)
	public WebElement accountsPage_Reset_Btn;

	@FindBy(xpath = OR.accountPage_Save_Btn)
	public WebElement accountPage_Save_Btn;
	
	@FindBy(xpath = OR.Account_Save_Clsoe)
	public WebElement account_Save_Clsoe;
	
	@FindBy(xpath = OR.account_Since)
	public WebElement account_Since;
	
	@FindBy(xpath = OR.market_Segment)
	public WebElement market_Segment;

	
		
	
	
}