package com.innroad.inncenter.webelements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Admin;

public class Elements_Roles {

	WebDriver driver;
	public static Logger app_logs = Logger.getLogger("devpinoyLogger");
	public Elements_Roles(WebDriver driver2)
	{
		this.driver=driver2;
		PageFactory.initElements(this.driver, this);
		
		 
	}
	
	@FindBy(xpath=OR.Roles_Search_Btn)
	public WebElement Roles_Search_Btn;
	
	@FindBy(xpath=OR.First_Element_In_Roles_SearchResults)
	public WebElement First_Element_In_Roles_SearchResults;
	
	@FindBy(xpath=OR.Enter_Role_Name)
	public WebElement Enter_Role_Name;
	
	@FindBy(xpath=OR.Roles_Reset_Btn)
	public WebElement Roles_Reset_Btn;
	
	@FindBy(xpath=OR.New_Role_Btn)
	public WebElement New_Role_Btn;
	
	@FindBy(xpath=OR.Select_Role_Satus)
	public WebElement Select_Role_Satus;
	
	@FindBy(xpath=OR.Toaster_Title)
	public WebElement Toaster_Title;
	
	@FindBy(xpath = OR_Admin.linkAllRoles)
	public WebElement linkAllRoles;
	
	@FindBy(xpath = OR_Admin.txtRoleList)
	public WebElement txtRoleList;
	
	@FindBy(xpath = OR_Admin.linkAdministrator)
	public WebElement linkAdministrator;
	
	@FindBy(xpath = OR_Admin.tableReportAcess)
	public WebElement tableReportAcess;
	
	@FindBy(xpath = OR_Admin.checkboxLedgerBalancesReport)
	public WebElement checkboxLedgerBalancesReport;
	
	@FindBy(xpath = OR_Admin.checkboxAdvanceDepositReport)
	public WebElement checkboxAdvanceDepositReport;
	
	@FindBy(xpath = OR_Admin.checkboxTransactionsReport)
	public WebElement checkboxTransactionsReport;
	
	@FindBy(xpath = OR_Admin.checkboxFolioBalancesReport)
	public WebElement checkboxFolioBalancesReport;
	
	@FindBy(xpath = OR_Admin.checkboxDailyFlashReport)
	public WebElement checkboxDailyFlashReport;
	
	@FindBy(xpath = OR_Admin.checkboxRoomForecastReport)
	public WebElement checkboxRoomForecastReport;
	
	@FindBy(xpath = OR_Admin.checkboxNetSalesReport)
	public WebElement checkboxNetSalesReport;
	
	@FindBy(xpath = OR_Admin.checkboxReservationsView)
	public WebElement checkboxReservationsView;
	
	@FindBy(xpath = OR_Admin.checkboxReservationsAdd)
	public WebElement checkboxReservationsAdd;
	
	@FindBy(xpath = OR_Admin.checkboxAccountView)
	public WebElement checkboxAccountView;
	
	@FindBy(xpath = OR_Admin.checkboxFolioView)
	public WebElement checkboxFolioView;
	
	@FindBy(xpath = OR_Admin.enterRoleName)
	public WebElement enterRoleName;
	
	@FindBy(xpath = OR_Admin.enterRoleDescription)
	public WebElement enterRoleDescription;
	
	@FindBy(xpath = OR_Admin.checkboxSystem)
	public WebElement checkboxSystem;
	
	@FindBy(xpath = OR_Admin.checkboxClients)
	public WebElement checkboxClients;
	
	@FindBy(xpath = OR_Admin.checkboxProperties)
	public WebElement checkboxProperties;
	
	@FindBy(xpath = OR_Admin.checkboxRoomClasses)
	public WebElement checkboxRoomClasses;
	
	@FindBy(xpath = OR_Admin.checkboxUsers)
	public WebElement checkboxUsers;
	
	@FindBy(xpath = OR_Admin.checkboxReservations)
	public WebElement checkboxReservations;
	
	@FindBy(xpath = OR_Admin.checkboxRunReportsAsOtherUsers)
	public WebElement checkboxRunReportsAsOtherUsers;
	
	@FindBy(xpath = OR_Admin.buttonSaveRole)
	public WebElement buttonSaveRole;
	
	@FindBy(xpath = OR_Admin.buttonResetRole)
	public WebElement buttonResetRole;
	
	@FindBy(xpath = OR_Admin.buttonConfirmPopup)
	public WebElement buttonConfirmPopup;
	
	@FindBy(xpath = OR_Admin.buttonCancelPopup)
	public WebElement buttonCancelPopup;
	
}
