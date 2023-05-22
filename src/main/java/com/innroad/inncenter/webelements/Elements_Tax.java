package com.innroad.inncenter.webelements;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.pages.NewTax;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_TaxAndFee;

public class Elements_Tax {

	WebDriver driver;

	public static Logger app_logs = Logger.getLogger("devpinoyLogger");

	public Elements_Tax(WebDriver driver2) {
		this.driver = driver2;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(xpath = OR.TaxNewItem)
	public WebElement TaxNewItem;

	@FindBy(xpath = OR.TaxItemName)
	public WebElement TaxItemName;

	@FindBy(xpath = OR.TaxDispalyName)
	public WebElement TaxDispalyName;

	@FindBy(xpath = OR.TaxDescription)
	public WebElement TaxDescription;

	@FindBy(xpath = OR.TaxValue)
	public WebElement TaxValue;

	@FindBy(xpath = OR.CalculateasVAT)
	public WebElement CalculateasVAT;

	@FindBy(xpath = OR.ExcludeTaxExempt)
	public WebElement ExcludeTaxExempt;

	@FindBy(xpath = OR.TaxPercent)
	public WebElement TaxPercent;

	@FindBy(xpath = OR.taxCategory)
	public WebElement taxCategory;

	@FindBy(xpath = OR.TaxAssociate)
	public WebElement TaxAssociate;
	
	@FindBy(xpath = OR.TaxSelectAllLedgerAccounts)
	public WebElement TaxSelectAllLedgerAccounts;

	@FindBy(xpath = OR.TaxLedgerAccountPopup)
	public WebElement TaxLedgerAccountPopup;

	@FindBy(xpath = OR.TaxPickOne)
	public WebElement TaxPickOne;

	@FindBy(xpath = OR.TaxLaedgerAccountDone)
	public WebElement TaxLaedgerAccountDone;

	@FindBy(xpath = OR.TaxDone)
	public WebElement TaxDone;

	@FindBy(xpath = OR.TaxSave)
	public WebElement TaxSave;

	@FindBy(xpath = OR.NewTaxItem_Title)
	public WebElement NewTaxItem_Title;

	@FindBy(xpath = OR.TaxStatus)
	public WebElement TaxStatus;

	@FindBy(xpath = OR.TaxesLink)
	public WebElement TaxesLink;

	@FindBy(xpath = NewTax.DeleteTax)
	public WebElement DeleteTax;

	@FindBy(xpath = NewTax.GoButton)
	public WebElement GoButton;

	@FindBy(xpath = NewTax.TaxReqMessage)
	public WebElement TaxReqMessage;
	
	@FindBy(xpath = NewTax.ReqTaxItemName)
	public WebElement ReqTaxItemName;
	
	@FindBy(xpath = NewTax.ReqTaxItemDisplayName)
	public WebElement ReqTaxItemDisplayName;
	
	@FindBy(xpath = NewTax.ReqTaxItemDesc)
	public WebElement ReqTaxItemDesc;
	
	@FindBy(xpath = NewTax.ReqTaxItemValue)
	public WebElement ReqTaxItemValue;
	
	@FindBy(xpath = NewTax.InvalidValueMessage)
	public WebElement InvalidValueMessage;
	
	@FindBy(xpath = OR.SelectedLedgerAccount)
	public WebElement SelectedLedgerAccount;
	
	@FindBy(xpath = NewTax.firstTaxItem)
	public WebElement firstTaxItem;
	
	@FindBy(id = NewTax.selectLedgerAccount)
	public WebElement selectLedgerAccount;

	@FindBy(xpath = OR.GetAlltaxes)
	public List<WebElement> getAllTaxes;
	
	
	@FindBy(xpath = OR.TaxDetailText)
	public WebElement taxDetailText;
	
	@FindBy(xpath = OR_TaxAndFee.taxFeeName)
	public WebElement taxFeeName;
	
	@FindBy(xpath = OR_TaxAndFee.taxFeeAmount)
	public WebElement taxFeeAmount;
	
	@FindBy(xpath = OR_TaxAndFee.ledgerAccount)
	public WebElement ledgerAccount;
	
	@FindBy(xpath = OR_TaxAndFee.taxVatchecked)
	public WebElement taxVatchecked;
	
	@FindBy(xpath = OR_TaxAndFee.taxVatOption)
	public WebElement taxVatOption;
	
	@FindBy(xpath = OR_TaxAndFee.saveButotn)
	public WebElement saveButotn;
	
	@FindBy(xpath = OR_TaxAndFee.displayName)
	public WebElement displayName;
	
	@FindBy(xpath = OR_TaxAndFee.description)
	public WebElement description;
	
	@FindBy(xpath = OR_TaxAndFee.createButton)
	public WebElement createButton;
	
	@FindBy(xpath = OR_TaxAndFee.createTax)
	public WebElement createTax;
	
	@FindBy(xpath = OR_TaxAndFee.createFees)
	public WebElement createFees;
	
	
	@FindBy(xpath = OR_TaxAndFee.deleteTaxIcon)
	public WebElement deleteTaxIcon;
	
	@FindBy(xpath = OR_TaxAndFee.confirmDeleteTax)
	public WebElement confirmDeleteTax;
	
	@FindBy(xpath = OR_TaxAndFee.tax_ddl)
	public WebElement tax_ddl;
	
	@FindBy(xpath = OR_TaxAndFee.restrict_Toggle)
	public WebElement restrict_Toggle;
	
	@FindBy(xpath = OR_TaxAndFee.addRoomClass)
	public WebElement addRoomClass;

	@FindBy(xpath = OR_TaxAndFee.addRatePlan)
	public WebElement addRatePlan;

	@FindBy(xpath = OR_TaxAndFee.addSource)
	public WebElement addSource;
	
	@FindBy(xpath = OR_TaxAndFee.vat_checkbox)
	public WebElement vat_checkbox;
	
	@FindBy(xpath = OR_TaxAndFee.excludeTaxExcept_checkbox)
	public WebElement excludeTaxExcept_checkbox;
	
	@FindBy(xpath = OR_TaxAndFee.addledgerAccounts)
	public WebElement addledgerAccounts;
	
	@FindBy(xpath = OR_TaxAndFee.backIcon)
	public WebElement backIcon;
	
	
	@FindBy(xpath = OR.TotalItemOnTaxesAndFeePage)
	public WebElement TotalItemOnTaxesAndFeePage;
	
	@FindBy(xpath = OR.TotalItemOnTaxesSize)
	public List<WebElement> totalItemOnTaxesSize ;
	
	
	@FindBy(xpath = OR.MakeInactive)
	public WebElement makeInactive;
	
	@FindBy(xpath = OR.MakeActive)
	public WebElement makeActive;
	
	@FindBy(xpath = OR.BackLink)
	public WebElement backLink;
	
	@FindBy(xpath = OR.ClickConfirm)
	public WebElement clickConfirm;
	
	@FindBy(xpath = OR.newTaxItem_Title)
	public WebElement newTaxItem_Title;
	
	@FindBy(xpath = OR.taxItemName)
	public WebElement taxItemName;
	
	@FindBy(xpath = OR.taxDispalyName)
	public WebElement taxDispalyName;
	
	@FindBy(xpath = OR.taxStatus)
	public WebElement taxStatus;
	
	@FindBy(xpath = OR.taxDescription)
	public WebElement taxDescription;
	
	@FindBy(xpath = OR.taxPickOne)
	public WebElement taxPickOne;
	
	@FindBy(xpath = OR.taxSelectAllLedgerAccounts)
	public WebElement taxSelectAllLedgerAccounts;
	
	@FindBy(xpath = OR.selectPercentage)
	public WebElement selectPercentage;
	
	@FindBy(xpath = OR.excludeTaxExempt)
	public WebElement excludeTaxExempt;
	
	@FindBy(xpath = OR.calculateasVAT)
	public WebElement calculateasVAT;
	
	@FindBy(xpath = OR.addLedgerAccountsLink)
	public WebElement addLedgerAccountsLink;
	
	@FindBy(xpath = OR.selectLedgerAccounts)
	public WebElement selectLedgerAccounts;
	
	@FindBy(xpath = OR.addLedgerAccountButton)
	public WebElement addLedgerAccountButton;
	
	@FindBy(xpath = OR.GetAllAvailableTaxes)
	public List<WebElement> GetAllAvailableTaxes;
	
	@FindBy(xpath = OR.deleteTaxButton)
	public WebElement deleteTaxButton;
	
	@FindBy(xpath = OR.CreateNewTaxButton)
	public WebElement CreateNewTaxButton;
	
	@FindBy(xpath = OR.addRoomClassesButton)
	public WebElement addRoomClassesButton;
	
	@FindBy(xpath = OR.addRatePlanButton)
	public WebElement addRatePlanButton;
	
	@FindBy(xpath = OR.addSourceButton)
	public WebElement addSourceButton;
	
	@FindBy(xpath = OR.selectRoomClasses)
	public WebElement selectRoomClasses;
	
	@FindBy(xpath = OR.addRoomClassButton)
	public WebElement addRoomClassButton;
	
	@FindBy(xpath = OR.addRatePlnButton)
	public WebElement addRatePlnButton;
		
	@FindBy(xpath = OR.selectRatePlans)
	public WebElement selectRatePlans;
	
	@FindBy(xpath = OR.selectSource)
	public WebElement selectSource;

	
	@FindBy(xpath = OR.saveTaxButton)
	public WebElement saveTaxButton;
	
	@FindBy(xpath = OR.percentageAmount)
	public WebElement percentageAmount;
	
	@FindBy(xpath = OR.USDAmount)
	public WebElement USDAmount;
	
	@FindBy(xpath = OR.ClickConfirm1)
	public WebElement clickConfirm1;
	
	@FindBy(xpath  = OR.selectPercentCtyFee)
	public WebElement selectPercentCtyFee;
	
	
	@FindBy(xpath = OR.taxFilterIcon)
	public WebElement taxFilterIcon;
	
	@FindBy(xpath = OR.taxFilterStatus)
	public WebElement taxFilterStatus;
	
	@FindBy(xpath = OR.feeName)
	public WebElement feeName;

	@FindBy(xpath = OR.perNightOrStay)
	public WebElement perNightOrStay;
	
}
