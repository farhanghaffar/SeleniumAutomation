package com.innroad.inncenter.pageobjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.ILedgerAccount;
import com.innroad.inncenter.pages.NewLedgerAccount;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_LedgeAccount;

public class LedgerAccount implements ILedgerAccount {

	public static Logger accountsLogger = Logger.getLogger("LedgeAccount");

	public void NewAccountbutton(WebDriver driver) throws InterruptedException {

		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		Wait.WaitForElement(driver, OR.Create_New_LedgeAccount_ButtonClick);
		Wait.waitForElementToBeClickable(By.xpath(OR.Create_New_LedgeAccount_ButtonClick), driver);
		CreateNewLedgeAccount.Create_New_LedgeAccount_ButtonClick.click();
		Utility.app_logs.info("Click New Account");
		Wait.wait10Second();
	}

	public void LedgerAccountDetails(WebDriver driver, String AccountName, String AccountDescription,
			String DefaultAmount) throws InterruptedException {
		System.out.println(AccountName);
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		try {
			Wait.waitUntilPresenceOfElementLocated(OR.Ledger_Accounts_Name_Inputs, driver);
			Utility.ScrollToElement(CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(0), driver);
			CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(0).sendKeys(AccountName);
			CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(1).sendKeys(AccountDescription);
		} catch (Exception e) {
			CreateNewLedgeAccount.Ledger_Accounts_Name_1.sendKeys(AccountName);
		}

	}

	public void LedgerAccountDetails(WebDriver driver, String AccountName, String AccountDescription,
			String DefaultAmount, String AccountType, String Status) throws InterruptedException {
		System.out.println(AccountName);
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Ledger_Accounts_Name_Inputs, driver);
		Utility.ScrollToElement(CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(0), driver);
		CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(0).sendKeys(AccountName);
		CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(1).sendKeys(AccountDescription);
		new Select(CreateNewLedgeAccount.SelectLedgerAccountType).selectByVisibleText(AccountType);		
		if (!DefaultAmount.isEmpty() || !DefaultAmount.equalsIgnoreCase("")) {
			Wait.explicit_wait_visibilityof_webelement(CreateNewLedgeAccount.LedgerAccount_DefaultAmount, driver);
			CreateNewLedgeAccount.LedgerAccount_DefaultAmount.sendKeys(DefaultAmount);
		}		
		new Select(CreateNewLedgeAccount.SelectLedgerAccountStatus).selectByVisibleText(Status);

	}

	public void VerifyAccountDetails(WebDriver driver, String AccountName, String AccountDescription,
			String AccountType, String AccountDefaultAmount, String AccountStatus) throws InterruptedException {
		// System.out.println(AccountName);
		String AccountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[text()='" + AccountName + "']";
		WebElement Account = driver.findElement(By.xpath(AccountPath));
		try {
			Wait.explicit_wait_visibilityof_webelement(Account, driver);
			Utility.ScrollToElement(Account, driver);
		} catch (Exception e) {
			Utility.app_logs.info(" No Account " + AccountName + " found ");
			Assert.assertTrue(false, " No Account " + AccountName + " found ");
		}

		String CheckBoxPath = AccountPath + "//parent::td//preceding-sibling::td/input";
		WebElement CheckBox = driver.findElement(By.xpath(CheckBoxPath));
		Assert.assertEquals(CheckBox.isSelected(), false, "Failed : Check Box Is Selected");
		String DescriptionPath = AccountPath + "//parent::td//following::td[1]/span";
		WebElement Description = driver.findElement(By.xpath(DescriptionPath));
		Assert.assertEquals(Description.getText(), AccountDescription, "Failed : Account Description Missmatched");
		String TypePath = AccountPath + "//parent::td//following::td[3]/select";
		Select Type = new Select(driver.findElement(By.xpath(TypePath)));
		Assert.assertEquals(Type.getFirstSelectedOption().getText(), AccountType, "Failed : Account Type Missmatched");
		String StatusPath = AccountPath + "//parent::td//following::td[6]/select";
		Select Status = new Select(driver.findElement(By.xpath(StatusPath)));
		Assert.assertEquals(Status.getFirstSelectedOption().getText(), AccountStatus,
				"Failed : Account Status Missmatched");
		String DefaultAmountPath = AccountPath + "//parent::td//following::td[5]/span";
		WebElement DefaultAmount = driver.findElement(By.xpath(DefaultAmountPath));
		Assert.assertEquals(DefaultAmount.getText(), AccountDefaultAmount + ".00",
				"Failed : Account DefaultAmount Missmatched");
	}

	public void SaveLedgerAccount(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		Wait.WaitForElement(driver, OR.Ledger_Accounts_SaveButtonClick);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Ledger_Accounts_SaveButtonClick), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Ledger_Accounts_SaveButtonClick), driver);
		CreateNewLedgeAccount.Ledger_Accounts_SaveButtonClick.click();
		Utility.app_logs.info("Click Save Account");

	}

	public void DeleteLedgerAccount(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		Wait.wait2Second();
		int size = driver.findElements(By.xpath(OR.LedgerAccount_Chekbox)).size();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", CreateNewLedgeAccount.LedgerAccount_Chekbox.get(size - 2));

		if (CreateNewLedgeAccount.LedgerAccount_Chekbox.get(size - 1).isSelected()) {
			accountsLogger.info("Check box already checked");
		} else {

			CreateNewLedgeAccount.LedgerAccount_Chekbox.get(size - 2).click();
		}

		CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick.click();
	}

	public void DeleteLedgerAccount(WebDriver driver, String AccountName) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		String AccountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[text()='" + AccountName + "']";
		WebElement Account = driver.findElement(By.xpath(AccountPath));
		try {
			Wait.explicit_wait_visibilityof_webelement(Account, driver);
			Utility.ScrollToElement(Account, driver);
		} catch (Exception e) {
			Utility.app_logs.info(" No Account " + AccountName + " found ");
			Assert.assertTrue(false, " No Account " + AccountName + " found ");
		}

		String CheckBoxPath = AccountPath + "//parent::td//preceding-sibling::td/input";
		WebElement CheckBox = driver.findElement(By.xpath(CheckBoxPath));
		if (CheckBox.isSelected()) {
			accountsLogger.info("Check box already checked");
		} else {
			CheckBox.click();
		}
		Assert.assertEquals(CheckBox.isSelected(), true, "Failed : Check Box Is not Selected");
		Utility.ScrollToElement(CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick, driver);
		CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick.click();
	}

	public void ChangeStatus(WebDriver driver, String AccountName, String Status) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		String AccountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[text()='" + AccountName + "']";
		WebElement Account = driver.findElement(By.xpath(AccountPath));
		try {
			Wait.explicit_wait_visibilityof_webelement(Account, driver);
			Utility.ScrollToElement(Account, driver);
		} catch (Exception e) {
			Utility.app_logs.info(" No Account " + AccountName + " found ");
			Assert.assertTrue(false, " No Account " + AccountName + " found ");
		}

		String CheckBoxPath = AccountPath + "//parent::td//preceding-sibling::td/input";
		WebElement CheckBox = driver.findElement(By.xpath(CheckBoxPath));
		if (CheckBox.isSelected()) {
			accountsLogger.info("Check box already checked");
		} else {
			CheckBox.click();
		}
		Assert.assertEquals(CheckBox.isSelected(), true, "Failed : Check Box Is not Selected");
		CreateNewLedgeAccount.Ledger_Accounts_EditButtonClick.click();
		Wait.explicit_wait_visibilityof_webelement(CreateNewLedgeAccount.SelectLedgerAccountStatus, driver);
		Utility.ScrollToElement(CreateNewLedgeAccount.SelectLedgerAccountStatus, driver);
		new Select(CreateNewLedgeAccount.SelectLedgerAccountStatus).selectByVisibleText(Status);
		SaveLedgerAccount(driver);
	}

	public void SelectStatus(WebDriver driver, String Status) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);

		Wait.explicit_wait_visibilityof_webelement(CreateNewLedgeAccount.LedgerAccount_SelectStatusType, driver);
		Utility.ScrollToElement(CreateNewLedgeAccount.LedgerAccount_SelectStatusType, driver);
		new Select(CreateNewLedgeAccount.LedgerAccount_SelectStatusType).selectByVisibleText(Status);
		Wait.wait1Second();
		driver.navigate().refresh();
	}

	public void EditLedgerAccount(WebDriver driver, String Status) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		CreateNewLedgeAccount.Ledger_Accounts_EditButtonClick.click();

		Wait.waitUntilPresenceOfElementLocated(OR.Ledger_Accounts_Status, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", CreateNewLedgeAccount.Ledger_Accounts_Status
				.get(CreateNewLedgeAccount.Ledger_Accounts_Status.size() - 1));

		new Select(CreateNewLedgeAccount.Ledger_Accounts_Status
				.get(CreateNewLedgeAccount.Ledger_Accounts_Status.size() - 1)).selectByVisibleText(Status);

	}

	public void PropertyTab(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", CreateNewLedgeAccount.LedgerAccount_PropertyTab);
		driver.navigate().refresh();
	}

	public void EditLedgerAccount_PropertyTab(WebDriver driver, String Status) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement Account = driver
				.findElement(By.xpath("//input[@id='MainContent_dgPropLedgAccounts_chkClientAccFlagItem_1']"));
		jse.executeScript("arguments[0].scrollIntoView();", Account);
		Account.click();
		// Select Account
		jse.executeScript("arguments[0].click();", CreateNewLedgeAccount.Ledger_Accounts_EditButtonClick_PropertyTab);
		//

	}

	public void SelectStatus_PropertyTab(WebDriver driver, String Status) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", CreateNewLedgeAccount.Ledger_Accounts_SelectStatus);
		Wait.explicit_wait_visibilityof_webelement(CreateNewLedgeAccount.Ledger_Accounts_SelectStatus, driver);
		Utility.ScrollToElement(CreateNewLedgeAccount.Ledger_Accounts_SelectStatus, driver);
		new Select(CreateNewLedgeAccount.Ledger_Accounts_SelectStatus).selectByVisibleText(Status);
		Wait.wait1Second();
		driver.navigate().refresh();
	}

	public void SaveButtoClick_PropertyTab(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", CreateNewLedgeAccount.Ledger_Accounts_SaveButtonClick_PropertyTab);
		driver.navigate().refresh();
	}

	public void DeleteLedgerAccount_PropertyTab(WebDriver driver, String AccountName) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		String AccountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[text()='" + AccountName + "']";
		WebElement Account = driver.findElement(By.xpath(AccountPath));
		try {
			Wait.explicit_wait_visibilityof_webelement(Account, driver);
			Utility.ScrollToElement(Account, driver);
		} catch (Exception e) {
			Utility.app_logs.info(" No Account " + AccountName + " found ");
			Assert.assertTrue(false, " No Account " + AccountName + " found ");
		}

		String CheckBoxPath = AccountPath + "//parent::td//preceding-sibling::td/input";
		WebElement CheckBox = driver.findElement(By.xpath(CheckBoxPath));
		if (CheckBox.isSelected()) {
			accountsLogger.info("Check box already checked");
		} else {
			CheckBox.click();
		}
		Assert.assertEquals(CheckBox.isSelected(), true, "Failed : Check Box Is not Selected");
		Utility.ScrollToElement(CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick, driver);
		CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick.click();
	}

	public void MasterTab(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", CreateNewLedgeAccount.LedgerAccount_MasterTab);
		driver.navigate().refresh();
	}

	public boolean isAccountExist(WebDriver driver, String AccountName) throws InterruptedException {
		String AccountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[starts-with(text(),'" + AccountName
				+ "')]";
		return driver.findElements(By.xpath(AccountPath)).size() > 0;

	}

	public void clickOnDelete(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick.click();
	}

	public ArrayList<String> checkedLedgerAccounts(WebDriver driver, String AccountName) throws InterruptedException {

		ArrayList<String> listOfId = new ArrayList<>();
		String AccountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[starts-with(text(),'" + AccountName
				+ "')]//..//..//td//input";
		String getIdPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[starts-with(text(),'" + AccountName
				+ "')]//..//..//td//input[@type='checkbox']";

		System.out.println("AccountPath: " + AccountPath);
		System.out.println("getIdPath: " + getIdPath);

		List<WebElement> Accounts = driver.findElements(By.xpath(AccountPath));
		List<WebElement> getId = driver.findElements(By.xpath(getIdPath));
		System.out.println("Accounts: " + Accounts.size());
		System.out.println("getId: " + getId.size());

		Utility.ScrollToElement(Accounts.get(0), driver);
		for (int i = 0; i < Accounts.size();) {
			Accounts.get(i).click();
			listOfId.add(getId.get(i).getAttribute("id"));

			i = i + 2;
		}
		for (int i = 0; i < listOfId.size(); i++) {
			System.out.println(listOfId.get(i));
		}
		System.out.println(listOfId.size());
		return listOfId;
	}

	public void changAccStatus(WebDriver driver, ArrayList<String> InputId, String Status) throws InterruptedException {

		System.out.println(InputId.size());

		for (int i = 0; i < InputId.size(); i++) {
			String AccountPath = "(//input[@id='" + InputId.get(i) + "']//..//following-sibling::td//select)[2]";
			System.out.println(AccountPath);
			WebElement select = driver.findElement(By.xpath(AccountPath));
			Utility.ScrollToElement(select, driver);
			new Select(select).selectByVisibleText(Status);
		}
	}

	public void clickonEditButton(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		CreateNewLedgeAccount.Ledger_Accounts_EditButtonClick.click();
	}

	public ArrayList<String> getIncidentalsList(WebDriver driver) throws InterruptedException {
		Elements_LedgeAccount ledgerAccount = new Elements_LedgeAccount(driver);
		ArrayList<String> incidentals = new ArrayList<String>();
		int sizeOfLedgerAccounts = ledgerAccount.LedgerAccountTypeList.size();

		for (int i = 0; i < sizeOfLedgerAccounts; i++) {

			String selectedOption = new Select(ledgerAccount.LedgerAccountTypeList.get(i)).getFirstSelectedOption()
					.getText();
			System.out.println("Selected Option :" + selectedOption);
			if (selectedOption.equalsIgnoreCase("Incidental"))
				incidentals.add(ledgerAccount.LedgerAccountNameList.get(i).getText());

		}
		return incidentals;
	}
	//This method is to get account details of given account type
	public ArrayList<String> getLedgerAccountDetails(WebDriver driver, String accountType, String Status) throws InterruptedException {
		//PropertyTab(driver);
		String strTableSystem = "//table[@id='MainContent_dgPropertySysAccounts']";
		String strTableLedger = "//table[@id='MainContent_dgPropLedgAccounts']";
		
		ArrayList<String> accountDetails = new ArrayList<>();
		
		WebElement tableSystem = driver.findElement(By.xpath(strTableSystem));
		List<WebElement> tableSystemRows = tableSystem.findElements(By.tagName("tr"));
		accountsLogger.info(tableSystemRows.size());
		
		for (int i = 0; i < tableSystemRows.size(); i++) {
			List<WebElement> cells = tableSystemRows.get(i).findElements(By.tagName("td"));
			if (cells.get(5).getText().equals(Status) && cells.get(3).getText().equals(accountType)) {
				accountsLogger.info(cells.get(5).getText());
				accountsLogger.info(cells.get(3).getText());
				if (!accountDetails.contains(cells.get(0).getText())) {
					accountDetails.add(cells.get(0).getText());
				}	
			}			
		}

		WebElement tableLedger = driver.findElement(By.xpath(strTableLedger));
		List<WebElement> tableLedgerRows = tableLedger.findElements(By.tagName("tr"));
		accountsLogger.info("Ledger: "+tableLedgerRows.size());
		for (int i = 0; i < tableLedgerRows.size(); i++) {
			List<WebElement> cells = tableLedgerRows.get(i).findElements(By.tagName("td"));
			//accountsLogger.info("Ledger: "+cells.size());
			if (cells.get(4).getText().equals(accountType)) {
				if (!accountDetails.contains(cells.get(1).getText())) {
					accountDetails.add(cells.get(1).getText());
				}	
			}
		}
		return accountDetails;
	}
	
	//This method is to get all account types
	public ArrayList<String> getAllLedgerAccountTypes(WebDriver driver) throws InterruptedException {
		PropertyTab(driver);
		
		String strTableSystem = "//table[@id='MainContent_dgPropertySysAccounts']";
		String strTableLedger = "//table[@id='MainContent_dgPropLedgAccounts']";
		
		ArrayList<String> accountDetails = new ArrayList<>();
		
		WebElement tableSystem = driver.findElement(By.xpath(strTableSystem));
		List<WebElement> tableSystemRows = tableSystem.findElements(By.tagName("tr"));
		accountsLogger.info(tableSystemRows.size());
		
		for (int i = 1; i < tableSystemRows.size(); i++) {
			List<WebElement> cells = tableSystemRows.get(i).findElements(By.tagName("td"));
			if (!accountDetails.contains(cells.get(3).getText())) {
				accountDetails.add(cells.get(3).getText());
			}			
		}

		WebElement tableLedger = driver.findElement(By.xpath(strTableLedger));
		List<WebElement> tableLedgerRows = tableLedger.findElements(By.tagName("tr"));
		accountsLogger.info("Ledger: "+tableLedgerRows.size());
		for (int i = 1; i < tableLedgerRows.size(); i++) {
			List<WebElement> cells = tableLedgerRows.get(i).findElements(By.tagName("td"));
			if (!accountDetails.contains(cells.get(4).getText())) {
				accountDetails.add(cells.get(4).getText());
			}
		}
		return accountDetails;
	}
	
	//This method is to get all names from Ledger account
	public ArrayList<String> getAllNamesFromLedgerAccount(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		//PropertyTab(driver);
		String strTableSystem = "//table[@id='MainContent_dgPropertySysAccounts']";
		String strTableLedger = "//table[@id='MainContent_dgPropLedgAccounts']";
		
		ArrayList<String> namesLedgerAccount = new ArrayList<>();
		
		WebElement tableSystem = driver.findElement(By.xpath(strTableSystem));
		List<WebElement> tableSystemRows = tableSystem.findElements(By.tagName("tr"));
		//accountsLogger.info(tableSystemRows.size());
		
		for (int i = 1; i < tableSystemRows.size(); i++) {			
			List<WebElement> cells = tableSystemRows.get(i).findElements(By.tagName("td"));
			namesLedgerAccount.add(cells.get(0).getText());			
		}

		WebElement tableLedger = driver.findElement(By.xpath(strTableLedger));
		List<WebElement> tableLedgerRows = tableLedger.findElements(By.tagName("tr"));
		//accountsLogger.info("Ledger: "+tableLedgerRows.size());
		for (int i = 1; i < tableLedgerRows.size(); i++) {
			List<WebElement> cells = tableLedgerRows.get(i).findElements(By.tagName("td"));
			namesLedgerAccount.add(cells.get(1).getText());	
		}
		return namesLedgerAccount;
	}
	
	//This method is to get all types from Ledger account
	public ArrayList<String> getAllTypesFromLedgerAccount(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		//PropertyTab(driver);
		String strTableSystem = "//table[@id='MainContent_dgPropertySysAccounts']";
		String strTableLedger = "//table[@id='MainContent_dgPropLedgAccounts']";
		
		ArrayList<String> typesLedgerAccount = new ArrayList<>();
		
		WebElement tableSystem = driver.findElement(By.xpath(strTableSystem));
		List<WebElement> tableSystemRows = tableSystem.findElements(By.tagName("tr"));
		//accountsLogger.info(tableSystemRows.size());
		
		for (int i = 1; i < tableSystemRows.size(); i++) {			
			List<WebElement> cells = tableSystemRows.get(i).findElements(By.tagName("td"));
			typesLedgerAccount.add(cells.get(3).getText());			
		}

		WebElement tableLedger = driver.findElement(By.xpath(strTableLedger));
		List<WebElement> tableLedgerRows = tableLedger.findElements(By.tagName("tr"));
		//accountsLogger.info("Ledger: "+tableLedgerRows.size());
		for (int i = 1; i < tableLedgerRows.size(); i++) {
			List<WebElement> cells = tableLedgerRows.get(i).findElements(By.tagName("td"));
			typesLedgerAccount.add(cells.get(4).getText());	
		}
		return typesLedgerAccount;
	}
	
	//This method is to get all accounts status from Ledger account
	public ArrayList<String> getAllAccountsStatusFromLedgerAccount(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		//PropertyTab(driver);
		String strTableSystem = "//table[@id='MainContent_dgPropertySysAccounts']";
		String strTableLedger = "//table[@id='MainContent_dgPropLedgAccounts']";
		
		ArrayList<String> statusLedgerAccount = new ArrayList<>();
		
		WebElement tableSystem = driver.findElement(By.xpath(strTableSystem));
		List<WebElement> tableSystemRows = tableSystem.findElements(By.tagName("tr"));
		//accountsLogger.info(tableSystemRows.size());
		
		for (int i = 1; i < tableSystemRows.size(); i++) {			
			List<WebElement> cells = tableSystemRows.get(i).findElements(By.tagName("td"));
			statusLedgerAccount.add(cells.get(5).getText());			
		}

		WebElement tableLedger = driver.findElement(By.xpath(strTableLedger));
		List<WebElement> tableLedgerRows = tableLedger.findElements(By.tagName("tr"));
		//accountsLogger.info("Ledger: "+tableLedgerRows.size());
		for (int i = 2; i <= tableLedgerRows.size(); i++) {
			//List<WebElement> cells = tableLedgerRows.get(i).findElements(By.tagName("td"));
			statusLedgerAccount.add(driver.findElement(By.xpath("//table[@id='MainContent_dgPropLedgAccounts']//tr["+i+"]//td[7]//select//option[@selected='selected']")).getText());	
			//statusLedgerAccount.add(cells.get(6).findElement(By.xpath("//select/option[@selected='selected']")).getText());	
		}
		return statusLedgerAccount;
	}
	
	//This method is to get all types
	public HashSet<String> getAllTypesUnique(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		//PropertyTab(driver);
		String strTableSystem = "//table[@id='MainContent_dgPropertySysAccounts']";
		String strTableLedger = "//table[@id='MainContent_dgPropLedgAccounts']";
		
		HashSet<String> typesUnique = new HashSet<>();
		
		WebElement tableSystem = driver.findElement(By.xpath(strTableSystem));
		List<WebElement> tableSystemRows = tableSystem.findElements(By.tagName("tr"));
		//accountsLogger.info(tableSystemRows.size());
		
		for (int i = 1; i < tableSystemRows.size(); i++) {			
			List<WebElement> cells = tableSystemRows.get(i).findElements(By.tagName("td"));
			typesUnique.add(cells.get(3).getText());			
		}

		WebElement tableLedger = driver.findElement(By.xpath(strTableLedger));
		List<WebElement> tableLedgerRows = tableLedger.findElements(By.tagName("tr"));
		//accountsLogger.info("Ledger: "+tableLedgerRows.size());
		for (int i = 1; i < tableLedgerRows.size(); i++) {
			List<WebElement> cells = tableLedgerRows.get(i).findElements(By.tagName("td"));
			typesUnique.add(cells.get(4).getText());	
		}
		return typesUnique;
	}
	
	//Added By Adhnan Ghaffar 1/25/2021
	public void enterLedgerAccountDetails(WebDriver driver, String AccountName, String AccountDescription,
			 String AccountType, String Status) throws InterruptedException {
		System.out.println(AccountName);
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		Wait.WaitForElement(driver, OR.Ledger_Accounts_Name_Inputs);
		Wait.waitUntilPresenceOfElementLocated(OR.Ledger_Accounts_Name_Inputs, driver);
		Utility.ScrollToElement(CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(0), driver);
		CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(0).sendKeys(AccountName);
		Utility.app_logs.info("Enter Account Name : " + AccountName);
		CreateNewLedgeAccount.Ledger_Accounts_Name_Inputs.get(1).sendKeys(AccountDescription);
		Utility.app_logs.info("Enter Account Description : " + AccountDescription);
		new Select(CreateNewLedgeAccount.SelectLedgerAccountType).selectByVisibleText(AccountType);
		Utility.app_logs.info("Select Account Type : " + AccountType);
		new Select(CreateNewLedgeAccount.SelectLedgerAccountStatus).selectByVisibleText(Status);
		Utility.app_logs.info("Select Account Status : " + Status);

	}
	
	//Added By Adhnan Ghaffar 01/25/2021
	public void DeleteLedgerAccounts(WebDriver driver, String AccountName,boolean fullName) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		String accountPath;
		if(fullName) {
		 accountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[text()='" + AccountName + "']";
		}else {
			accountPath = "//table[@id='MainContent_dgclientLegdAccounts']//span[contains(text(),'" + AccountName + "')]";
		}
		boolean accountExist = false;
		try {
			WebElement Account = driver.findElement(By.xpath(accountPath));
			Wait.explicit_wait_visibilityof_webelement(Account, driver);
			Utility.ScrollToElement(Account, driver);
			accountExist = true;
		} catch (Exception e) {
			Utility.app_logs.info(" No Account " + AccountName + " found ");
		}
		if(accountExist) {
		String CheckBoxPath = accountPath + "//parent::td//preceding-sibling::td/input";
		List<WebElement> CheckBox = driver.findElements(By.xpath(CheckBoxPath));
		for(WebElement ele : CheckBox) {
		if (ele.isSelected()) {
			accountsLogger.info("Check box already checked");
		} else {
			ele.click();
		}
		Assert.assertEquals(ele.isSelected(), true, "Failed : Check Box Is not Selected");
		}
		Utility.ScrollToElement(CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick, driver);
		CreateNewLedgeAccount.Ledger_Accounts_DeleteButtonClick.click();
		}
	}


	public void selectAccountType(WebDriver driver, String Status, ArrayList<String> testSteps) throws InterruptedException {
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);

		Wait.waitForElementById(driver, NewLedgerAccount.LedgeAccount_AccountType);
		Utility.ScrollToElement(CreateNewLedgeAccount.LedgeAccount_AccountType, driver);
		new Select(CreateNewLedgeAccount.LedgeAccount_AccountType).selectByVisibleText(Status);
		testSteps.add("Selected account type : " + Status);
		accountsLogger.info("Selected account type : " + Status);
	}
	
	public ArrayList<String> getPaymentMethodList(WebDriver driver){
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_LedgeAccount CreateNewLedgeAccount = new Elements_LedgeAccount(driver);
		Wait.waitForElementByXpath(driver, NewLedgerAccount.ListOfAllPaymentMethods);
		int size  = CreateNewLedgeAccount.ListOfAllPaymentMethods.size();
		Utility.printString("CreateNewLedgeAccount.ListOfAllPaymentMethods.size() : " + size);
		for(int i=0; i < size; i++) {
			testSteps.add(Utility.getElementText(CreateNewLedgeAccount.ListOfAllPaymentMethods.get(i)));
		}
		
		return testSteps;
	}

}