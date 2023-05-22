package com.innroad.inncenter.pageobjects;

import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.lang.model.util.Elements;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.interfaces.IAdmin;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Admin;
import com.innroad.inncenter.properties.OR_Inventory;
import com.innroad.inncenter.properties.OR_Reports;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_Admin;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_Inventory;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;
import com.innroad.inncenter.webelements.Elements_Reports;
import com.innroad.inncenter.webelements.Elements_Roles;
import com.innroad.inncenter.webelements.Elements_TapeChart;
import com.innroad.inncenter.webelements.Elements_Users;
import com.snowtide.pdf.ad;

public class Admin implements IAdmin {

	public static Logger AdminLogger = Logger.getLogger("Admin");

	private boolean VerifySelectedItem(WebDriver driver, String SelectedListPath, String SelectedItem) {

		Select SelectedList = new Select(driver.findElement(By.xpath(SelectedListPath)));
		boolean found = false;
		List<WebElement> Items = SelectedList.getOptions();
		for (WebElement Item : Items) {
			String ItemName = Item.getText();
			if (ItemName.contains(SelectedItem)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public void logout(WebDriver driver) {
		Elements_Users user = new Elements_Users(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.CurrentUser), driver);
		user.CurrentUser.click();
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR.Logout), driver, 10);
			user.Logout.click();
		} catch (Exception e) {
			Wait.waitForElementToBeClickable(By.xpath(OR.logoutLink), driver, 10);
			user.logoutLink.click();
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR.LoginPage), driver);
	}

	public void logout(WebDriver driver, ArrayList<String> test_steps) {
		logout(driver);
		test_steps.add("Logging out to the inncenter applicaction");
	}

	public ArrayList<String> CreateNewUser(WebDriver driver, String FirstName, String LastName, String Login,
			String Email, String AssociateRole, String AssociateProperty, boolean isAllProperties,
			ArrayList<String> test_steps) throws InterruptedException {

		Elements_Users user = new Elements_Users(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(user.New_User_Btn, driver);
		user.New_User_Btn.click();
		test_steps.add("Clikc on New User button");

		Wait.explicit_wait_visibilityof_webelement(user.UserLogin, driver);
		user.UserFirstName.clear();
		user.UserFirstName.sendKeys(FirstName);
		test_steps.add("Enter first name : " + FirstName);

		user.UserLastName.clear();
		user.UserLastName.sendKeys(LastName);
		test_steps.add("Enter last name : " + LastName);

		user.UserLogin.clear();
		user.UserLogin.sendKeys(Login);
		AdminLogger.info("Login ID : " + Login);
		test_steps.add("Enter login id : " + Login);

		user.UserEmail.clear();
		user.UserEmail.sendKeys(Email);
		AdminLogger.info("Email : " + Email);
		test_steps.add("Enter email id : " + Email);

		user.User_AssociateRole_Btn.click();
		test_steps.add("Click on associate role button ");

		Wait.waitUntilPresenceOfElementLocated(OR.RolePicker_popup, driver);
		new Select(user.SelectAssociateRole).selectByVisibleText(AssociateRole);
		test_steps.add("Select associate role");

		user.AddAssociateRole.click();
		Assert.assertTrue(VerifySelectedItem(driver, OR.SelectedAssociateRole, AssociateRole),
				"Failed: Associate Role Selection");
		user.User_AssociateRole_Done.click();
		AdminLogger.info("Associate Role : " + AssociateRole);
		test_steps.add("Attached associate role : " + AssociateRole);
		Wait.wait1Second();

		Wait.WaitForElement(driver, OR.User_AssociateProperties_Btn);
		Wait.explicit_wait_visibilityof_webelement(user.User_AssociateProperties_Btn, driver);
		Utility.ScrollToElement(user.User_AssociateProperties_Btn, driver);
		user.User_AssociateProperties_Btn.click();
		test_steps.add("Click on associate property button");

		Wait.wait1Second();
		Wait.waitUntilPresenceOfElementLocated(OR.PropertyPicker_popup, driver);

		AssociateProperty = AssociateProperty.trim();
		new Select(user.SelectAssociateProperty).selectByVisibleText(AssociateProperty);
		test_steps.add("Select  associate property : " + AssociateProperty);
		user.AddAssociateProperty.click();
		Wait.wait1Second();
		user.User_AssociateProperty_Done.click();
		AdminLogger.info("Associate Property : " + AssociateProperty);
		test_steps.add("Attached associate property : " + AssociateProperty);
		Wait.wait2Second();

		return test_steps;

	}

	public void CloseTab(WebDriver driver) throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Utility.ScrollToElement(user.CloseNewUserTab, driver);
		user.CloseNewUserTab.click();
	}

	public void SearchUser(WebDriver driver, String LastName, String FirstName, String LoginId, String Email)
			throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Wait.wait1Second();
		Wait.explicit_wait_visibilityof_webelement(user.Search_LastName, driver);
		user.Search_LastName.sendKeys(LastName);
		user.Search_FirstName.sendKeys(FirstName);
		user.Search_LoginId.sendKeys(LoginId);
		user.Search_Email.sendKeys(Email);
		user.User_SearchButton.click();
		Wait.explicit_wait_visibilityof_webelement(user.VerifySearch, driver);
		assertEquals(user.VerifySearch.getText(), LoginId, "Faild to search created new user");
		user.VerifySearch.click();

	}

	public void ChangeUserStatus(WebDriver driver, String Status) throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Wait.wait1Second();
		Wait.explicit_wait_visibilityof_webelement(user.Select_Status, driver);
		new Select(user.Select_Status).selectByVisibleText(Status);

	}

	public ArrayList<String> SetNewPassword(WebDriver driver, String NewPassword, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Wait.WaitForElement(driver, OR.SetNewPassword_Title);
		// System.out.println("title:" + user.SetNewPassword_Title.getText());
		assertEquals(user.SetNewPassword_Title.getText(), "Setup new password", "Reset Password page didn't show");
		test_steps.add("Set Password Page Verified Title :" + user.SetNewPassword_Title.getText());
		user.SetNewPassword_NewPasswordInputField.sendKeys(NewPassword);
		test_steps.add("Entered New Password : " + NewPassword);
		System.out.println("Pass:" + NewPassword);
		user.SetNewPassword_ConfirmPasswordInputField.sendKeys(NewPassword);
		test_steps.add("Confirmed New Password : " + NewPassword);
		user.SetNewPassword_SubmitButton.click();
		test_steps.add("Submit Button is Click");

		// Verify
		Wait.WaitForElement(driver, OR.Relogin_Message_LoginPage);
		System.out.println("title:" + user.Relogin_Message_LoginPage.getText());
		WebElement Relogin = driver.findElement(By.xpath("//div[text()='Relogin with your new password.']"));
		assertEquals(Relogin.isDisplayed(), true, "Relogin page didn't show");

		return test_steps;
	}

	public void SaveButton(WebDriver driver) {
		Elements_Users user = new Elements_Users(driver);
		Wait.explicit_wait_visibilityof_webelement(user.User_Save_Btn, driver);
		user.User_Save_Btn.click();

		Wait.explicit_wait_visibilityof_webelement_350(user.Toaster_Title, driver);
		System.out.println(user.Toaster_Title.getText());
		assertEquals(user.Toaster_Title.getText(), "Success", "Failed to verify new user after save button");

	}

	public void ResetPasswordButtonClick(WebDriver driver) throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Wait.wait1Second();
		user.User_ResetPassword_Btn.click();
		Wait.explicit_wait_visibilityof_webelement_350(user.Toaster_Title, driver);
		System.out.println(user.Toaster_Title.getText());
		assertEquals(user.Toaster_Title.getText(), "Success", "Failed to verify new user after save button");
		user.User_Save_Btn.click();
		Wait.explicit_wait_visibilityof_webelement_350(user.Toaster_Title, driver);
		assertEquals(user.Toaster_Title.getText(), "Success", "Failed to verify new user after save button");
	}

	public boolean VerifyUser(WebDriver driver, String LastName, String FirstName, String LoginId, String Email)
			throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Wait.wait1Second();
		Wait.explicit_wait_visibilityof_webelement(user.Search_LastName, driver);
		user.Search_LastName.sendKeys(Keys.chord(Keys.CONTROL, "a"), LastName);
		user.Search_FirstName.sendKeys(Keys.chord(Keys.CONTROL, "a"), FirstName);
		user.Search_LoginId.sendKeys(Keys.chord(Keys.CONTROL, "a"), LoginId);
		user.Search_Email.sendKeys(Keys.chord(Keys.CONTROL, "a"), Email);

		user.User_SearchButton.click();
		boolean isUserExist = false;
		System.out.println("Search click");
		try {
			Wait.wait5Second();

			WebElement toaster = driver.findElement(By.xpath("//div[@class='toast-title']"));
			if (toaster.isDisplayed()) {
				System.out.println("User not found click");
				assertEquals(toaster.getText(), "No Users Exist", "User Exists with ID");

				isUserExist = false;
			}

		} catch (Exception e) {
			Wait.WaitForElement(driver, OR.VerifySearch);
			assertEquals(user.VerifySearch.getText(), LoginId, "Faild to search created new user");
			Utility.ScrollToElement(user.VerifySearch, driver);
			user.VerifySearch.click();
			Wait.WaitForElement(driver, OR.UserLogin);
			System.out.println("User found click");

			assertTrue(user.UserLogin.isDisplayed(), "User Page didn't display");
			isUserExist = true;

		}

		return isUserExist;

	}

	public ArrayList<String> ChangeUserStatus(WebDriver driver, String Login, String Email, String Status,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Wait.wait1Second();
		user.UserLogin.clear();
		user.UserLogin.sendKeys(Login);
		AdminLogger.info("Login ID Changed To : " + Login);
		test_steps.add("Login ID Changed To  : " + Login);
		user.UserEmail.clear();
		user.UserEmail.sendKeys(Email);
		AdminLogger.info("Email Changed To : " + Email);
		test_steps.add("Email Changed To : " + Email);
		Wait.explicit_wait_visibilityof_webelement(user.Select_Status, driver);
		new Select(user.Select_Status).selectByVisibleText(Status);
		test_steps.add("Status Changed to : " + Status);
		String SelectedOption = new Select(user.Select_Status).getFirstSelectedOption().getText();
		assertTrue(SelectedOption.equals(Status), "Status didn't change");
		return test_steps;
	}

	public void SearchUser(WebDriver driver, String LastName, String FirstName, String LoginId, String Email,
			String Status) throws InterruptedException {
		Elements_Users user = new Elements_Users(driver);
		Wait.wait1Second();
		Wait.explicit_wait_visibilityof_webelement(user.Search_LastName, driver);
		user.Search_LastName.clear();
		user.Search_LastName.sendKeys(LastName);
		user.Search_FirstName.clear();
		user.Search_FirstName.sendKeys(FirstName);
		user.Search_LoginId.clear();
		user.Search_LoginId.sendKeys(LoginId);
		user.Search_Email.clear();
		user.Search_Email.sendKeys(Email);
		new Select(user.UserPage_SelectStatus).selectByVisibleText(Status);
		user.User_SearchButton.click();
		Wait.WaitForElement(driver, OR.VerifySearch);
		assertEquals(user.VerifySearch.getText(), LoginId, "Faild to search created new user");
		assertEquals(user.UserPage_SearchedUser_Status.getText(), Status, "Status didn't change");

	}

	public boolean selectInventorySubSystemCheckBox(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		boolean checkBoxSelected = true;
		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Accounts accounts = new Elements_Accounts(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clientDetailsInventorySubSystemCheckBox), driver);
		Utility.ScrollToElement(admin.clientDetailsInventorySubSystemCheckBox, driver);
		if (admin.clientDetailsInventorySubSystemCheckBox.isSelected()) {
			test_steps.add("Inventory Sub System check box is already selected");
		} else {
			admin.clientDetailsInventorySubSystemCheckBox.click();
			Wait.waitForElementToBeClickable(By.id(OR.SaveItem_Button), driver, 10);
			accounts.SaveItem_Button.click();
			Wait.waitForElementToBeClickable(By.id(OR.DoneItem_Button), driver, 10);
			accounts.DoneItem_Button.click();
			test_steps.add("Selecting Inventory Sub System check box");
			checkBoxSelected = false;
		}
		return checkBoxSelected;
	}

	public void selectBothInventorySubSystemAndRateCheckBox(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Accounts accounts = new Elements_Accounts(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clientDetailsBothCheckBox), driver);
		Utility.ScrollToElement(admin.clientDetailsBothCheckBox, driver);
		if (admin.clientDetailsBothCheckBox.isSelected()) {
			test_steps.add("<b>Both Inventory Sub System and Rate Override</b> check box is already selected");
		} else {
			admin.clientDetailsBothCheckBox.click();
			Wait.waitForElementToBeClickable(By.id(OR.SaveItem_Button), driver, 10);
			accounts.SaveItem_Button.click();
			Wait.waitForElementToBeClickable(By.id(OR.DoneItem_Button), driver, 10);
			accounts.DoneItem_Button.click();
			test_steps.add("Selecting <b>Both Inventory Sub System and Rate Override</b> check box");
		}
	}

	public boolean verifyEntitlementEnable(WebDriver driver, String entityType, ArrayList<String> test_steps,
			boolean disable) throws Exception {
		String selectAlphabet = "//a[contains(@data-bind,'AssignAlphabet')][text()='A']";
		String clickAdministrator = "//a[contains(@data-bind,'RoleName')][text()='Administrator']";
		String report = "//span[contains(@data-bind,'EntityName')][text()='" + entityType + "']/../../td[6]/input";
		String saveButton = "(//button[contains(text(),'Save')])[2]";
		boolean ifSelected = false;

//		try {
//			String path = "//*[@id='roleDetail']//div[@class='modules_loading']";
//			Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(path)), 180);
//		} catch (Exception e) {
//			System.out.println("No Loading");
//		}
		// Wait.waitForElementToBeClickable(By.xpath(selectAlphabet), driver);
		WebElement selectpage = driver.findElement(By.xpath(selectAlphabet));
		// Wait.waitForElementToBeClickable(By.xpath(report), driver);
		selectpage.click();
		Wait.waitForElementToBeVisibile(By.xpath(clickAdministrator), driver);
		driver.findElement(By.xpath(clickAdministrator)).click();

		try {
			String path = "//*[@id='roleDetail']//div[@class='modules_loading']";
			Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(path)), 180);
		} catch (Exception e) {
			System.out.println("No Loading");
		}
		WebElement reservationreport = driver.findElement(By.xpath(report));
		Utility.ScrollToElement(reservationreport, driver);
		if (disable) {
			if (reservationreport.isSelected()) {
				System.out.println("reservationreport not  selected");
				test_steps.add("reservationreport not  selected");
				Wait.wait2Second();
				reservationreport.click();
				test_steps.add("click on reservationreport ");
				AdminLogger.info("click on reservationreport ");
			} else {
				test_steps.add("Reservation Report Already selected");
				AdminLogger.info("Reservation Report Already selected");
				ifSelected = true;
			}
		} else {
			if (!reservationreport.isSelected()) {
				test_steps.add("reservation report not is already selected");
				Wait.wait2Second();
				reservationreport.click();
				test_steps.add("click on reservationreport ");
				AdminLogger.info("click on reservationreport ");
				ifSelected = true;
			} else {
				test_steps.add("Reservation Report Already unselected");
				AdminLogger.info("Reservation Report Already selected");
			}
		}
		Wait.waitForElementToBeVisibile(By.xpath(saveButton), driver);
		driver.findElement(By.xpath(saveButton)).click();
		try {
			String path = "//*[@id='roleDetail']//div[@class='modules_loading']";
			Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(path)), 180);
		} catch (Exception e) {
			System.out.println("No Loading");
		}
		return ifSelected;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickClientOption> ' Description: <Clicks The Client Name In
	 * Client Info Tab > ' ' Input parameters: <WebDriver> ' Return: <void> Created
	 * By: <Aqsa Manzoor> ' Created On: <14 July 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void clickClientName(WebDriver driver) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.CLICK_CLIENT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.CLICK_CLIENT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.CLICK_CLIENT), driver);
		admin.clickClient.click();
		try {
			Wait.WaitForElement(driver, OR_Admin.CLICK_CLIENT_OPTION);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.CLICK_CLIENT_OPTION), driver);
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, admin.clickClient);
			Wait.WaitForElement(driver, OR_Admin.CLICK_CLIENT_OPTION);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.CLICK_CLIENT_OPTION), driver);
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickClientOption> ' Description: <Clicks The Client Options
	 * Tab Button > ' ' Input parameters: <WebDriver> ' Return: <void> Created By:
	 * <Aqsa Manzoor> ' Created On: <14 July 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void clickClientOption(WebDriver driver) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.CLICK_CLIENT_OPTION);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.CLICK_CLIENT_OPTION), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.CLICK_CLIENT_OPTION), driver);
		admin.clickClientOption.click();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getDefaultClinetCurrency> ' Description: <Get the selected
	 * Currency Type in the Client and returns it > ' ' Input parameters:
	 * <WebDriver> ' Return: <String> Created By: <Aqsa Manzoor> ' Created On: <14
	 * July 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String getDefaultClientCurrency(WebDriver driver) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.GET_DEFAUL_CURRENCY);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.GET_DEFAUL_CURRENCY), driver);
		String currency = admin.getDefaultCurrency.getText();
		AdminLogger.info("Currency: " + currency);
		return currency;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getClientDateFormat> ' Description: <Get the selected Date
	 * Format Type in the Client and returns it > ' ' Input parameters: <WebDriver>
	 * ' Return: <String> Created By: <Aqsa Manzoor> ' Created On: <14 July 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String getClientDateFormat(WebDriver driver) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.GET_DATE_FORMAT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.GET_DATE_FORMAT), driver);
		String date = admin.getDateFormat.getText();
		return date;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getClientDateFormat> ' Description: <Get the selected Date
	 * Format Type in the Client and returns it > ' ' Input parameters: <WebDriver>
	 * ' Return: <String> Created By: <Aqsa Manzoor> ' Created On: <14 July 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> selectClientDateFormat(WebDriver driver, String dateFormatType) {
		ArrayList<String> testSteps = new ArrayList<>();

		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.SELECT_DATE_FORMAT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.SELECT_DATE_FORMAT), driver);
		new Select(admin.selectDateFormat).selectByVisibleText(dateFormatType);
		testSteps.add("The client Date Format Type changed to: " + dateFormatType);
		AdminLogger.info("The client Date Format Type changed to:" + dateFormatType);

		return testSteps;

	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <adminSaveButton> ' Description: <Click the save button in
	 * client info > ' ' Input parameters: <WebDriver> ' Return: <void> Created By:
	 * <Aqsa Manzoor> ' Created On: <14 July 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void adminSaveButton(WebDriver driver) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.ADMIN_SAVE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.ADMIN_SAVE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.ADMIN_SAVE_BUTTON), driver);
		admin.adminSaveButton.click();

	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * 'Method Name: <adminDoneButton> ' Description: <Click the done button in
	 * client info > ' ' Input parameters: <WebDriver> ' Return: <void> Created By:
	 * <Aqsa Manzoor> ' Created On: <14 July 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void adminDoneButton(WebDriver driver) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.ADMIN_DONE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.ADMIN_DONE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.ADMIN_DONE_BUTTON), driver);
		admin.adminDoneButton.click();

	}

	public void clickClientName(WebDriver driver, ArrayList<String> test_steps) {
		String clientName = "//table[@id='MainContent_dgClientList']/tbody/tr[2]/td/a";
		Wait.WaitForElement(driver, clientName);
		driver.findElement(By.xpath(clientName)).click();
		test_steps.add("Click on client name");
		AdminLogger.info("Click on client name");
	}

	public void clickClientOptions(WebDriver driver, ArrayList<String> test_steps) {
		String clientoption = "//input[@id='MainContent_btnClientOption']";
		Wait.WaitForElement(driver, clientoption);
		driver.findElement(By.xpath(clientoption)).click();
		test_steps.add("Click on client options");
		AdminLogger.info("Click on client options");
	}

	public String getDefaultTimeZone(WebDriver driver, ArrayList<String> test_steps) {
		String timeZone = "//select[@id='MainContent_drpTimeZone']";
		Wait.WaitForElement(driver, timeZone);
		timeZone = new Select(driver.findElement(By.xpath(timeZone))).getFirstSelectedOption().getText();
		int start = timeZone.indexOf("(");
		int end = timeZone.indexOf(")");
		timeZone = timeZone.substring(start + 1, end);
		timeZone = timeZone.trim();
		test_steps.add("Default Time Zone : " + timeZone);
		AdminLogger.info("Default Time Zone : " + timeZone);
		return timeZone;
	}

	public String getDefaultCurrency(WebDriver driver, ArrayList<String> test_steps) {
		String currency = "//select[@id='MainContent_drpDefaultCurrency']";
		Wait.WaitForElement(driver, currency);
		currency = new Select(driver.findElement(By.xpath(currency))).getFirstSelectedOption().getText();
		int start = currency.indexOf("(");
		int end = currency.indexOf(")");
		currency = currency.substring(start + 1, end);
		currency = currency.trim();
		test_steps.add("Default currency : " + currency);
		AdminLogger.info("Default currency : " + currency);
		return currency;
	}

	// This method is to get Start Day of the Week for client
	public String getClientStartDayOfTheWeek(WebDriver driver, ArrayList<String> test_steps) {

		String strDay = "//select[contains(@id,'StartDayOfWeek')]//child::option[@selected='selected']";
		Wait.WaitForElement(driver, strDay);
		String startDay = driver.findElement(By.xpath(strDay)).getText();

		return startDay;

	}

	// This method is to get client time zone
	public String getClientTimeZone(WebDriver driver, ArrayList<String> test_steps) {

		String strTimeZone = "//select[contains(@id,'drpTimeZone')]//child::option[@selected='selected']";
		Wait.WaitForElement(driver, strTimeZone);
		String timeZone = driver.findElement(By.xpath(strTimeZone)).getText();

		return timeZone;

	}

	// Reports V2 Entitlement
	public boolean verifyReportsV2EntitlementEnable(WebDriver driver, String entityName, ArrayList<String> test_steps,
			boolean disable) throws Exception {
		// String
		// selectAlphabet="//a[contains(@data-bind,'AssignAlphabet')][text()='A']";
		String clickAdministrator = "//a[contains(@data-bind,'RoleName')][text()='Administrator']";
		String entity = "//div[text()='Reports Access']//following::table//tr//td//span[text()='" + entityName
				+ "']//ancestor::tr//td[@class='text-center']//input";
		String saveButton = "(//button[contains(text(),'Save')])[2]";
		boolean ifSelected = false;

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles role = new Elements_Roles(driver);
		Wait.WaitForElement(driver, OR_Admin.linkRoles);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.linkRoles), driver);
		admin.linkRoles.click();
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.linkAllRoles), driver);
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, admin.linkRoles);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.linkAllRoles), driver);
		}
		role.linkAllRoles.click();

		Wait.waitForElementToBeVisibile(By.xpath(clickAdministrator), driver);
		driver.findElement(By.xpath(clickAdministrator)).click();
		try {
			Wait.WaitForElement(driver, entity);
			Wait.waitForElementToBeVisibile(By.xpath(entity), driver);
		} catch (Exception e) {
			Wait.WaitForElement(driver, entity);
			Wait.waitForElementToBeVisibile(By.xpath(entity), driver);
		}
		WebElement report = driver.findElement(By.xpath(entity));
		Wait.explicit_wait_visibilityof_webelement(report, driver);
		Utility.ScrollToElement(report, driver);
		if (disable) {
			if (!report.isSelected()) {
				System.out.println(entityName + " not  selected");
				test_steps.add(entityName + " not  selected");
				Wait.wait2Second();
				report.click();
				test_steps.add("click on " + entityName);
				AdminLogger.info("click on " + entityName);
			} else {
				test_steps.add(entityName + " Already selected");
				AdminLogger.info(entityName + " Already selected");
				ifSelected = true;
			}
		} else {
			if (!report.isSelected()) {
				test_steps.add(entityName + " is not already selected");
				Wait.wait2Second();
				report.click();
				test_steps.add("click on " + entityName);
				AdminLogger.info("click on " + entityName);
				ifSelected = true;
			} else {
				test_steps.add(entityName + " Already unselected");
				AdminLogger.info(entityName + " Already selected");
			}
		}
		Wait.waitForElementToBeVisibile(By.xpath(saveButton), driver);
		driver.findElement(By.xpath(saveButton)).click();
		return ifSelected;
	}

	public void verifyReportsV2RequiredPermissions(WebDriver driver, String entityName, ArrayList<String> test_steps)
			throws Exception {
		String entity = "//div[text()='Reports Access']//following::table//tr//td//span[text()='" + entityName
				+ "']//ancestor::tr//td[@class='text-center']//input";
		String permissionsPopup = "//h4[@id='myModalLabel'][@data-bind='text: ModalTitleText']";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles role = new Elements_Roles(driver);

		// admin.AdminIcon.click();
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.linkRoles.click();
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAllRoles, driver);
		role.linkAllRoles.click();
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAdministrator, driver);
		role.linkAdministrator.click();

		if (role.checkboxReservationsView.isSelected()) {
			role.checkboxReservationsView.click();
			test_steps.add("Reservations view is unselected");
			AdminLogger.info("Reservations view is unselected");
		}

		if (entityName.equals("Ledger Balances Report")) {

			Wait.wait1Second();
			if (role.checkboxReservationsAdd.isSelected()) {
				role.checkboxReservationsAdd.click();
				test_steps.add("Reservations Add is unselected");
				AdminLogger.info("Reservations Add is unselected");
			}

			Utility.ScrollToElement(role.checkboxAccountView, driver);
			Wait.wait1Second();
			if (role.checkboxAccountView.isSelected()) {
				role.checkboxAccountView.click();
				test_steps.add("Account View is unselected");
				AdminLogger.info("Account View is unselected");
			}

			Utility.ScrollToElement(role.checkboxFolioView, driver);
			Wait.wait1Second();
			if (role.checkboxFolioView.isSelected()) {
				role.checkboxFolioView.click();
				test_steps.add("Folio View is unselected");
				AdminLogger.info("Folio View is unselected");
			}

		}

		WebElement report = driver.findElement(By.xpath(entity));
		Utility.ScrollToElement(role.checkboxFolioView, driver);
		if (!report.isSelected()) {
			report.click();
			test_steps.add(entityName + " is selected");
			AdminLogger.info(entityName + " is selected");

		} else {
			test_steps.add(entityName + " is already selected");
			AdminLogger.info(entityName + " is already selected");
		}

		role.buttonSaveRole.click();
		test_steps.add("Clicking on Save button");
		AdminLogger.info("Clicking on Save button");

		try {
			Wait.wait5Second();
			WebElement popupPermissions = driver.findElement(By.xpath(permissionsPopup));
			Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?",
					"Faild to get required text");

			ArrayList<String> permissionsAll = new ArrayList<String>();
			List<WebElement> permissionsList = driver.findElements(By.xpath("//li[@data-bind='text: EntityName']"));

			for (WebElement permission : permissionsList) {
				permissionsAll.add(permission.getText());
				test_steps.add("Stored all permissions into a list");
				AdminLogger.info("Stored all permissions into a list");
			}

			if (entityName.equals("Ledger Balances Report")) {
				Assert.assertTrue(permissionsAll.contains("Reservations(View)"),
						"Faild to get required permission: Reservations(View)");
				Assert.assertTrue(permissionsAll.contains("Reservations(Add)"),
						"Faild to get required permission: Reservations(Add)");
				Assert.assertTrue(permissionsAll.contains("Account(View)"),
						"Faild to get required permission: Account(View)");
				Assert.assertTrue(permissionsAll.contains("Folio(View)"),
						"Faild to get required permission: Folio(View)");
			} else {
				Assert.assertTrue(permissionsAll.contains("Reservations(View)"),
						"Faild to get required permission: Reservations(View)");
			}

		} catch (Exception e) {
			Assert.assertTrue(false, "Faild to get required permissions");
		}

		Wait.wait1Second();
		role.buttonConfirmPopup.click();
		test_steps.add("Clicked on Confirm button");
		AdminLogger.info("Clicked on Confirm button");
	}

	public void verifyEntitlemenReportDesiabled(WebDriver driver, String entityType, ArrayList<String> test_steps)
			throws Exception {
		String selectAlphabet = "//a[contains(@data-bind,'AssignAlphabet')][text()='A']";
		String clickAdministrator = "//a[contains(@data-bind,'RoleName')][text()='Administrator']";
		String report = "//span[contains(@data-bind,'EntityName')][text()='" + entityType + "']/../../td[6]/input";
		String saveButton = "(//button[contains(text(),'Save')])[2]";
		WebElement selectpage = driver.findElement(By.xpath(selectAlphabet));

		selectpage.click();
		Wait.waitForElementToBeVisibile(By.xpath(clickAdministrator), driver);
		driver.findElement(By.xpath(clickAdministrator)).click();
		WebElement reservationreport = driver.findElement(By.xpath(report));
		Utility.ScrollToElement(reservationreport, driver);
		if (reservationreport.isSelected()) {
			Wait.wait2Second();
			reservationreport.click();
			System.out.println("reservation reports desable successfully ");

		} else {
			System.out.println("reservationreport Already selected");

		}
		Wait.waitForElementToBeVisibile(By.xpath(saveButton), driver);
		driver.findElement(By.xpath(saveButton)).click();

	}

	public ArrayList<String> clickOnClient(WebDriver driver, String clientName) throws InterruptedException {
		String clientNamePath = "//a[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ clientName.toUpperCase() + "']";
		Wait.WaitForElement(driver, clientNamePath);
		Utility.ScrollToElement(driver.findElement(By.xpath(clientNamePath)), driver);
		driver.findElement(By.xpath(clientNamePath)).click();
		ArrayList<String> testStep = new ArrayList<>();
		testStep.add("Click on client: " + clientName);
		return testStep;

	}

	public void navigateToAdminRole(WebDriver driver, String roleName, ArrayList<String> test_steps) throws Exception {
		String roleLoctor = "//a[contains(text(),'" + roleName + "')]";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		/*
		 * Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		 * admin.linkRoles.click(); test_steps.add("Navigated to Roles");
		 * AdminLogger.info("Navigated to Roles");
		 */
		// Wait.WaitForElement(driver, OR_Admin.linkAllRoles);
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAllRoles, driver);
		// roles.linkAllRoles.click();
		Utility.clickThroughAction(driver, roles.linkAllRoles);
		test_steps.add("Clicked on All roles");
		AdminLogger.info("Clicked on All roles");
		Wait.WaitForElement(driver, OR_Admin.txtRoleList);
		Wait.wait2Second();
		// Wait.waitUntilElementToBePresentByXapth(roleLoctor, driver, 20);
		Utility.ScrollToElement(driver.findElement(By.xpath(roleLoctor)), driver);
		driver.findElement(By.xpath(roleLoctor)).click();
		test_steps.add("Clicked on " + roleName + " role");
		AdminLogger.info("Clicked on " + roleName + " role");
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.enterRoleName), driver, 180);
	}

	public void clickOnClientOptions(WebDriver driver) throws InterruptedException {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement_ID(driver, OR_Admin.ClientOptions);
		Wait.waitForElementToBeVisibile(By.id(OR_Admin.ClientOptions), driver);
		Wait.waitForElementToBeClickable(By.id(OR_Admin.ClientOptions), driver);
		admin.clientOptions.click();
	}

	public void navigateToNewRole(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.linkRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR.New_Role_Btn, driver);
		roles.New_Role_Btn.click();
		test_steps.add("Clicked on New Role");
		AdminLogger.info("Clicked on New Role");
	}

	public boolean verifyReportAccessTableAvailability(WebDriver driver, ArrayList<String> test_steps)
			throws Exception {

		boolean flag = false;
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.enterRoleName, driver);
		if (roles.tableReportAcess.isDisplayed()) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	public boolean validateReportsV2EntitlementsUI(WebDriver driver, ArrayList<String> reportName,
			ArrayList<String> test_steps) throws Exception {

		// Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.tableReportAcess, driver);
		Utility.ScrollToElement(roles.tableReportAcess, driver);

		ArrayList<String> report = new ArrayList<String>();
		boolean flag = false;

		List<WebElement> reportElements = roles.tableReportAcess
				.findElements(By.xpath(OR_Admin.tableReportAcess + "//tbody//tr"));
		List<WebElement> reportHeader = driver
				.findElements(By.xpath("//div[text()='Reports Access']//following::table[1]//thead//tr//th"));

		if (reportName.get(0).equals("Report Name")) {
			for (int i = 0; i < reportHeader.size(); i++) {
				report.add(reportHeader.get(i).getText());
				test_steps.add("Report Access table header details identified and added to a list");
				AdminLogger.info("Report Access table header details identified and added to a list");
			}
		} else {

			for (int i = 0; i < reportElements.size(); i++) {

				List<WebElement> reportValues = reportElements.get(i).findElements(By.tagName("td"));

				if (reportValues.get(0).getText().equals(reportName.get(0))) {
					for (int j = 0; j < reportValues.size() - 1; j++) {
						report.add(reportValues.get(j).getText());
					}
					test_steps.add(reportName.get(0) + " row values added into a list");
					AdminLogger.info(reportName.get(0) + " row values added into a list");

					Assert.assertTrue(reportValues.get(reportValues.size() - 1).isEnabled(),
							reportName + " Checkbox is not enabled");
					test_steps.add(reportName.get(0) + " Checkbox is validated and it's enabled");
					AdminLogger.info(reportName.get(0) + " Checkbox is validated and it's enabled");
					break;
				}
				reportValues.clear();
			}
		}

		if (report.equals(reportName)) {
			flag = true;
		}

		return flag;
	}

	public void verifyReportsV2EntitlementsStandardRole(WebDriver driver, String roleName, ArrayList<String> test_steps)
			throws Exception {
		String roleLoctor = "//a[contains(text(),'" + roleName + "')]";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.linkRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAllRoles, driver);
		roles.linkAllRoles.click();
		test_steps.add("Clicked on All roles");
		AdminLogger.info("Clicked on All roles");
		Wait.waitUntilElementToBePresentByXapth(roleLoctor, driver, 20);
		WebElement role = driver.findElement(By.xpath(roleLoctor));
		Utility.ScrollToElement(role, driver);
		role.click();
		test_steps.add("Clicked on " + roleName + " role");
		AdminLogger.info("Clicked on " + roleName + " role");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.tableReportAcess, driver);
		Wait.wait2Second();
		Utility.ScrollToElement(roles.tableReportAcess, driver);
		Wait.wait1Second();

		ArrayList<WebElement> checkboxes = new ArrayList<WebElement>();
		ArrayList<String> reports = new ArrayList<String>();

		checkboxes.add(roles.checkboxLedgerBalancesReport);
		checkboxes.add(roles.checkboxAdvanceDepositReport);
		checkboxes.add(roles.checkboxTransactionsReport);
		checkboxes.add(roles.checkboxFolioBalancesReport);
		checkboxes.add(roles.checkboxDailyFlashReport);
		checkboxes.add(roles.checkboxRoomForecastReport);
		checkboxes.add(roles.checkboxNetSalesReport);

		reports.add("Ledger Balances Report");
		reports.add("Advance Deposit Report");
		reports.add("Transactions Report");
		reports.add("Folio Balances Report");
		reports.add("Daily Flash Report");
		reports.add("Room Forecast Report");
		reports.add("Net Sales Report");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.checkboxLedgerBalancesReport, driver);

		for (int i = 0; i < checkboxes.size(); i++) {

			// Utility.ScrollToElement(checkboxes.get(i), driver);
			Wait.wait2Second();
			checkboxes.get(i).click();
			test_steps.add("Clicked on " + reports.get(i) + " checkbox");
			AdminLogger.info("Clicked on " + reports.get(i) + " checkbox");
			Wait.wait2Second();

			if (roles.buttonSaveRole.isEnabled() && roles.buttonResetRole.isEnabled()) {
				test_steps.add("Save and Reset buttons are enabled after clicking on " + reports.get(i) + " checkbox");
				AdminLogger
						.info("Save and Reset buttons are enabled after clicking on " + reports.get(i) + " checkbox");
			} else {
				Assert.assertTrue(false, "Failed, Save and Reset buttons are not enabled after clicking on "
						+ reports.get(i) + " checkbox");
			}

			// Utility.ScrollToElement(checkboxes.get(i), driver);
			checkboxes.get(i).click();
			test_steps.add("Second time Clicked on " + reports.get(i) + " checkbox");
			AdminLogger.info("Second time Clicked on " + reports.get(i) + " checkbox");
			Wait.wait2Second();

			if (!roles.buttonSaveRole.isEnabled() && !roles.buttonResetRole.isEnabled()) {
				test_steps.add("Save and Reset buttons are disabled after clicking on " + reports.get(i)
						+ " checkbox second time");
				AdminLogger.info("Save and Reset buttons are disabled after clicking on " + reports.get(i)
						+ " checkbox second time");
			} else {
				Assert.assertTrue(false, "Failed, Save and Reset buttons are not disabled after clicking on "
						+ reports.get(i) + " checkbox second time");
			}
		}
	}

	public void createNewRoleWithAllReportsAccess(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		String permissionsPopup = "//h4[@id='myModalLabel'][@data-bind='text: ModalTitleText']";
		String roleName = Utility.GenerateRandomString();
		String roleLoctor = "//a[contains(text(),'" + roleName + "')]";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.linkRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR.New_Role_Btn, driver);
		roles.New_Role_Btn.click();
		test_steps.add("Clicked on New Role");
		AdminLogger.info("Clicked on New Role");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.enterRoleName, driver);
		roles.enterRoleName.sendKeys(roleName);
		test_steps.add("Entered Role name");
		AdminLogger.info("Entered Role name");

		roles.enterRoleDescription.sendKeys("Test Role Description");
		test_steps.add("Entered Role Description");
		AdminLogger.info("Entered Role Description");

		Utility.ScrollToElement(roles.checkboxLedgerBalancesReport, driver);
		roles.checkboxLedgerBalancesReport.click();
		test_steps.add("Clicked on Ledger Balances Report checkbox");
		AdminLogger.info("Clicked on Ledger Balances Report checkbox");
		Wait.wait2Second();

		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		Wait.wait5Second();
		WebElement popupPermissions = driver.findElement(By.xpath(permissionsPopup));
		Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?", "Faild to get Popup");
		test_steps.add("Required Permissions Popup displayed");
		AdminLogger.info("Required Permissions Popup displayed");

		roles.buttonConfirmPopup.click();
		test_steps.add("Clicked on Cofirm button on Popup");
		AdminLogger.info("Clicked on Confirm button on Popup");

		Utility.ScrollToUp(driver);

		Wait.explicit_wait_xpath(OR_Admin.buttonRoles, driver);
		// Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.buttonRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAllRoles, driver);
		roles.linkAllRoles.click();
		test_steps.add("Clicked on All roles");
		AdminLogger.info("Clicked on All roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.txtRoleList, driver);
		// WebElement role = driver.findElement(By.xpath(roleLoctor));

		if (driver.findElement(By.xpath(roleLoctor)).isDisplayed()) {
			test_steps.add(roleName + " Role is successfully created");
			AdminLogger.info(roleName + " Role is successfully created");
		} else {
			Assert.assertTrue(false, "Failed to create new Role");
		}
	}

	public String createNewRoleAndGetRoleName(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Wait.wait3Second();
		String permissionsPopup = "//h4[@id='myModalLabel'][@data-bind='text: ModalTitleText']";
		String roleName = "CRoles " + Utility.GenerateRandomString() + Utility.GenerateRandomString();
		String roleLoctor = "//a[contains(text(),'" + roleName + "')]";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);
		Elements_Users user = new Elements_Users(driver);

		// Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		// admin.linkRoles.click();
		// test_steps.add("Navigated to Roles");
		// AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR.New_Role_Btn, driver);
		roles.New_Role_Btn.click();
		// test_steps.add("Clicked on New Role");
		// AdminLogger.info("Clicked on New Role");
		// Wait.waitUntilPresenceOfElementLocated(OR_Admin.enterRoleName, driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.enterRoleName), driver, 120);
		Wait.wait1Second();
		roles.enterRoleName.sendKeys(roleName);
		roles.enterRoleDescription.sendKeys("Test Role Description");
		Utility.ScrollToElement(roles.checkboxRunReportsAsOtherUsers, driver);
		roles.checkboxRunReportsAsOtherUsers.click();
		Wait.wait2Second();

		Utility.ScrollToElement(roles.checkboxLedgerBalancesReport, driver);
		roles.checkboxLedgerBalancesReport.click();
		roles.checkboxAdvanceDepositReport.click();
		roles.checkboxTransactionsReport.click();
		roles.checkboxFolioBalancesReport.click();
		roles.checkboxDailyFlashReport.click();
		roles.checkboxRoomForecastReport.click();
		roles.checkboxNetSalesReport.click();
		Wait.wait2Second();
		roles.buttonSaveRole.click();
		// test_steps.add("Clicked on Save button");
		// AdminLogger.info("Clicked on Save button");

		Wait.wait5Second();
		WebElement popupPermissions = driver.findElement(By.xpath(permissionsPopup));
		Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?", "Faild to get Popup");
		test_steps.add("Required Permissions Popup displayed");
		AdminLogger.info("Required Permissions Popup displayed");
		Utility.ScrollToElement(roles.buttonConfirmPopup, driver);
		roles.buttonConfirmPopup.click();
		test_steps.add("Clicked on Cofirm button on Popup");
		AdminLogger.info("Clicked on Confirm button on Popup");
		Wait.wait3Second();
		Utility.ScrollToUp(driver);
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR_Admin.checkboxEntityAll), driver, 10);
			Utility.clickThroughJavaScript(driver, roles.checkboxEntityAll);
			Wait.wait2Second();
		} catch (Exception e) {
			AdminLogger.info(e.toString());
		}
		Utility.ScrollToElement(roles.buttonSaveRole, driver);
		Utility.clickThroughJavaScript(driver, roles.buttonSaveRole);

		Wait.explicit_wait_visibilityof_webelement_350(user.Toaster_Title, driver);
		AdminLogger.info(user.Toaster_Title.getText());
		assertEquals(user.Toaster_Title.getText(), "Success", "Failed to verify new user after save button");

		Utility.ScrollToUp(driver);
		Wait.explicit_wait_xpath(OR_Admin.buttonRoles, driver);
		// Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.buttonRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAllRoles, driver);
		roles.linkAllRoles.click();
		test_steps.add("Clicked on All roles");
		AdminLogger.info("Clicked on All roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.txtRoleList, driver);
		// WebElement role = driver.findElement(By.xpath(roleLoctor));

		if (driver.findElement(By.xpath(roleLoctor)).isDisplayed()) {
			test_steps.add(roleName + " Role is successfully created");
			AdminLogger.info(roleName + " Role is successfully created");
		} else {
			Assert.assertTrue(false, "Failed to create new Role");
		}

		return roleName;
	}

	public ArrayList<String> associateNewRoleToExistingUser(WebDriver driver, String loginID, String associateRole,
			ArrayList<String> test_steps) throws InterruptedException {

		String userLocator = "//a[text()='" + loginID + "']";

		Elements_Users user = new Elements_Users(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(user.linkAllUsers, driver);

		user.linkAllUsers.click();
		Wait.explicit_wait_visibilityof_webelement(user.txtUsersList, driver);
		WebElement linkLoginID = driver.findElement(By.xpath(userLocator));

		Utility.ScrollToElement(linkLoginID, driver);
		linkLoginID.click();
		Wait.explicit_wait_visibilityof_webelement(user.User_AssociateRole_Btn, driver);
		user.User_AssociateRole_Btn.click();
		test_steps.add("Click on associate role button ");

		Wait.waitUntilPresenceOfElementLocated(OR.RolePicker_popup, driver);

		user.buttonDiscardAll.click();
		Wait.wait10Second();
		new Select(user.SelectAssociateRole).selectByVisibleText(associateRole);
		test_steps.add("Select associate role");

		user.AddAssociateRole.click();
		Assert.assertTrue(VerifySelectedItem(driver, OR.SelectedAssociateRole, associateRole),
				"Failed: Associate Role Selection");
		user.User_AssociateRole_Done.click();
		AdminLogger.info("Associate Role : " + associateRole);
		test_steps.add("Attached associate role : " + associateRole);
		Wait.wait1Second();

		Wait.explicit_wait_visibilityof_webelement(user.User_Save_Btn, driver);
		user.User_Save_Btn.click();

		Wait.explicit_wait_visibilityof_webelement_350(user.Toaster_Title, driver);
		System.out.println(user.Toaster_Title.getText());
		assertEquals(user.Toaster_Title.getText(), "Success", "Failed to verify new user after save button");

		return test_steps;

	}

	// This method is to associate all properties to existing user
	public ArrayList<String> associateAllPropertiesToExistingUser(WebDriver driver, String loginID,
			ArrayList<String> test_steps) throws InterruptedException {

		String userLocator = "//a[text()='" + loginID + "']";

		Elements_Users user = new Elements_Users(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(user.linkAllUsers, driver);

		user.linkAllUsers.click();
		Wait.explicit_wait_visibilityof_webelement(user.txtUsersList, driver);
		WebElement linkLoginID = driver.findElement(By.xpath(userLocator));

		Utility.ScrollToElement(linkLoginID, driver);
		linkLoginID.click();
		Wait.WaitForElement(driver, OR.User_AssociateProperties_Btn);
		// Wait.explicit_wait_visibilityof_webelement(user.User_AssociateProperties_Btn,
		// driver);
		Utility.ScrollToElement(user.User_AssociateProperties_Btn, driver);
		Utility.clickThroughAction(driver, user.User_AssociateProperties_Btn);
		// user.User_AssociateProperties_Btn.click();
		// test_steps.add("Click on Associate Properties button ");

		Wait.wait1Second();
		Wait.waitUntilPresenceOfElementLocated(OR.PropertyPicker_popup, driver);

		// associateProperty = associateProperty.trim();
		user.buttonDiscardAll.click();
		Wait.wait2Second();
		user.buttonAssignAll.click();
		Wait.wait2Second();
		// test_steps.add("Select all associate properties");
		user.User_AssociateProperty_Done.click();
		AdminLogger.info("Associated all Properties");
		// test_steps.add("Associated all Properties : " );
		Wait.wait2Second();

		Wait.explicit_wait_visibilityof_webelement(user.User_Save_Btn, driver);
		user.User_Save_Btn.click();

		Wait.explicit_wait_visibilityof_webelement_350(user.Toaster_Title, driver);
		System.out.println(user.Toaster_Title.getText());
		assertEquals(user.Toaster_Title.getText(), "Success", "Failed to verify new user after save button");

		return test_steps;

	}

	// This method is to associate property to existing user
	public ArrayList<String> associatePropertyToExistingUser(WebDriver driver, String loginID, String associateProperty,
			ArrayList<String> test_steps) throws InterruptedException {

		String userLocator = "//a[text()='" + loginID + "']";

		Elements_Users user = new Elements_Users(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(user.linkAllUsers, driver);

		user.linkAllUsers.click();
		Wait.explicit_wait_visibilityof_webelement(user.txtUsersList, driver);
		WebElement linkLoginID = driver.findElement(By.xpath(userLocator));

		Utility.ScrollToElement(linkLoginID, driver);
		linkLoginID.click();
		Wait.explicit_wait_visibilityof_webelement(user.User_AssociateProperties_Btn, driver);
		// user.User_AssociateProperties_Btn.click();
		Wait.wait2Second();
		Utility.clickThroughJavaScript(driver, user.User_AssociateProperties_Btn);
		// test_steps.add("Click on Associate Properties button ");

//		Wait.WaitForElement(driver, OR.User_AssociateProperties_Btn);
//		Wait.explicit_wait_visibilityof_webelement(user.User_AssociateProperties_Btn, driver);
//		Utility.ScrollToElement(user.User_AssociateProperties_Btn, driver);
//		user.User_AssociateProperties_Btn.click();
		// test_steps.add("Click on associate property button");

		Wait.wait1Second();
		Wait.waitUntilPresenceOfElementLocated(OR.PropertyPicker_popup, driver);

		associateProperty = associateProperty.trim();
		user.buttonDiscardAll.click();
		Wait.wait2Second();
		new Select(user.SelectAssociateProperty).selectByVisibleText(associateProperty);
		// test_steps.add("Select associate property : " + associateProperty);
		user.AddAssociateProperty.click();
		Wait.wait1Second();
		user.User_AssociateProperty_Done.click();
		AdminLogger.info("Associate Property : " + associateProperty);
		// test_steps.add("Attached associate property : " + associateProperty);
		Wait.wait2Second();

		Wait.explicit_wait_visibilityof_webelement(user.User_Save_Btn, driver);
		user.User_Save_Btn.click();

		Wait.explicit_wait_visibilityof_webelement_350(user.Toaster_Title, driver);
		System.out.println(user.Toaster_Title.getText());
		assertEquals(user.Toaster_Title.getText(), "Success", "Failed to verify new user after save button");

		return test_steps;

	}

	public void reportsV2EntitlementEnable(WebDriver driver, String roleName, String entityName,
			ArrayList<String> test_steps) throws Exception {
		// String
		// selectAlphabet="//a[contains(@data-bind,'AssignAlphabet')][text()='A']";
		String roleLocator = "//a[contains(@data-bind,'RoleName')][text()='" + roleName + "']";
		String entity = "//div[text()='Reports Access']//following::table//tr//td//span[text()='" + entityName
				+ "']//ancestor::tr//td[@class='text-center']//input";
		String saveButton = "(//button[contains(text(),'Save')])[2]";
		boolean ifSelected = false;

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles role = new Elements_Roles(driver);
		Navigation nav = new Navigation();

		// nav.Roles(driver);

		// Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.linkRoles), driver);
		// admin.linkRoles.click();
		// Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.linkAllRoles), driver);
		// role.linkAllRoles.click();

		/*
		 * Wait.waitUntilPresenceOfElementLocated("//a[text()='All']", driver);
		 * driver.findElement(By.xpath("//a[text()='All']")).click();
		 * 
		 * Wait.waitForElementToBeVisibile(By.xpath(roleLocator), driver);
		 * driver.findElement(By.xpath(roleLocator)).click();
		 */

		WebElement report = driver.findElement(By.xpath(entity));
		Utility.ScrollToElement(report, driver);
		Wait.wait2Second();

		if (!report.isSelected()) {
			report.click();
		}

		Wait.wait2Second();
		// role.buttonSaveRole.click();
	}

	// This method is to enable all entitlements for ReportsV2
	public void reportsV2AllEntitlementsEnable(WebDriver driver, String roleName, ArrayList<String> test_steps)
			throws Exception {
		Wait.wait2Second();
		Elements_Roles role = new Elements_Roles(driver);
		String[] reports = {};

		for (int i = 0; i < reports.length; i++) {

			String entity = "//div[text()='Reports Access']//following::table//tr//td//span[text()='" + reports[i]
					+ "']//ancestor::tr//td[@class='text-center']//input";

			WebElement report = driver.findElement(By.xpath(entity));
			Utility.ScrollToElement(report, driver);
			Wait.wait2Second();

			if (!report.isSelected()) {
				report.click();
			}
			Wait.wait3Second();
		}

		Utility.ScrollToElement(role.checkboxReservationsView, driver);
		if (!role.checkboxReservationsView.isSelected()) {
			role.checkboxReservationsView.click();
		}

		if (!role.checkboxReservationsAdd.isSelected()) {
			role.checkboxReservationsAdd.click();
		}

		if (!role.checkboxAccountView.isSelected()) {
			role.checkboxAccountView.click();
		}

		if (!role.checkboxFolioView.isSelected()) {
			role.checkboxFolioView.click();
		}
	}

	public void reportsV2EntitlementDisable(WebDriver driver, String roleName, String entityName,
			ArrayList<String> test_steps) throws Exception {
		// String
		// selectAlphabet="//a[contains(@data-bind,'AssignAlphabet')][text()='A']";
		String roleLocator = "//a[contains(@data-bind,'RoleName')][text()='" + roleName + "']";
		String entity = "//div[text()='Reports Access']//following::table//tr//td//span[text()='" + entityName
				+ "']//ancestor::tr//td[@class='text-center']//input";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles role = new Elements_Roles(driver);
		Navigation nav = new Navigation();

		// nav.Roles(driver);

		// Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.linkRoles), driver);
		// admin.linkRoles.click();
		// Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.linkAllRoles), driver);
		// role.linkAllRoles.click();

		/*
		 * Wait.waitUntilPresenceOfElementLocated("//a[text()='All']", driver);
		 * driver.findElement(By.xpath("//a[text()='All']")).click();
		 * 
		 * Wait.waitForElementToBeVisibile(By.xpath(roleLocator), driver);
		 * driver.findElement(By.xpath(roleLocator)).click();
		 */

		WebElement report = driver.findElement(By.xpath(entity));
		Utility.ScrollToElement(report, driver);
		Wait.wait2Second();
		if (report.isSelected()) {
			report.click();
		}

		Wait.wait2Second();
		// role.buttonSaveRole.click();
	}

	// This method is to enable Special Functions
	public void enableSpecialFunctionsForRole(WebDriver driver, String roleName, String entityName,
			ArrayList<String> test_steps) throws Exception {

		// String strEntityName = "//span[contains(@data-bind,'EntityName')][text()='Run
		// reports as other users']/../../td[4]/input[@type='checkbox']";
		String strEntityName = "//div[text()='Special Functions']//following::table//tr//td//span[text()='" + entityName
				+ "']//ancestor::tr//td[@class='text-center']//input";
		String roleLocator = "//a[contains(@data-bind,'RoleName')][text()='" + roleName + "']";

		Wait.waitForElementToBeVisibile(By.xpath(strEntityName), driver, 180);
		Wait.wait2Second();
		WebElement entity = driver.findElement(By.xpath(strEntityName));
		Utility.ScrollToElement(entity, driver);
		Wait.wait2Second();

		if (!entity.isSelected()) {
			entity.click();
		}

		Wait.wait2Second();

	}

	// This method is to disable Special Functions
	public void disableSpecialFunctionsForRole(WebDriver driver, String roleName, String entityName,
			ArrayList<String> test_steps) throws Exception {

		// String strEntityName = "//span[contains(@data-bind,'EntityName')][text()='Run
		// reports as other users']/../../td[4]/input[@type='checkbox']";
		String strEntityName = "//div[text()='Special Functions']//following::table//tr//td//span[text()='" + entityName
				+ "']//ancestor::tr//td[@class='text-center']//input";
		String roleLocator = "//a[contains(@data-bind,'RoleName')][text()='" + roleName + "']";

		Wait.waitForElementToBeVisibile(By.xpath(strEntityName), driver, 180);
		Wait.wait2Second();
		WebElement entity = driver.findElement(By.xpath(strEntityName));
		Utility.ScrollToElement(entity, driver);
		Wait.wait2Second();

		if (entity.isSelected()) {
			entity.click();
		}
		Wait.wait2Second();
	}

	public void saveRole(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_Roles role = new Elements_Roles(driver);
		Elements_TapeChart role1 = new Elements_TapeChart(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.buttonSaveRole), driver);
		// role.buttonSaveRole.click();
		String save = "//div[@data-bind='visible: isSaving']";
		Wait.wait5Second();
		String enabled = "//button[contains(@data-bind,'enable: !isSaving()')]";
		if (driver.findElement(By.xpath(enabled)).isEnabled()) {
			role.buttonSaveRole.click();
			Wait.explicit_wait_visibilityof_webelement_150(role1.Toaster_Message, driver);
			// System.out.println(role.Toaster_Title.getText());
			// assertEquals(role.Toaster_Title.getText(), "Success",
			// "Failed to verify new user after save button");

		} else {

		}

	}

	public boolean verifyReportAvailabilityInReportsPage(WebDriver driver, String reportName,
			ArrayList<String> test_steps) throws Exception {
		boolean flag = false;
		String reportLocator = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='" + reportName + "']";

		if (driver.findElements(By.xpath(reportLocator)).size() > 0) {
			flag = true;
		}

		return flag;
	}

	public void logoutCurrentUser(WebDriver driver) {
		Elements_Users user = new Elements_Users(driver);
		Wait.waitForElementToBeClickable(By.xpath("//span[@class='ant-avatar-string']"), driver);
		// user.CurrentUser.click();
		driver.findElement(By.xpath("//span[@class='ant-avatar-string']")).click();
		try {
			// Wait.waitForElementToBeClickable(By.xpath(OR.Logout), driver, 10);
			Wait.waitForElementToBeClickable(By.xpath("//a[text()='Logout']"), driver, 10);
			// user.Logout.click();
			driver.findElement(By.xpath("//a[text()='Logout']")).click();
		} catch (Exception e) {
			Wait.waitForElementToBeClickable(By.xpath(OR.logoutLink), driver, 10);
			user.logoutLink.click();
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR.LoginPage), driver);
	}

	public void verifyRequiredPermissionsReportsV2NewRole(WebDriver driver, ArrayList<String> test_steps)
			throws Exception {
		String permissionsPopup = "//h4[@id='myModalLabel'][@data-bind='text: ModalTitleText']";
		String roleName = Utility.GenerateRandomString();
		String roleLoctor = "//a[contains(text(),'" + roleName + "')]";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.linkRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR.New_Role_Btn, driver);
		roles.New_Role_Btn.click();
		test_steps.add("Clicked on New Role");
		AdminLogger.info("Clicked on New Role");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.enterRoleName, driver);
		roles.enterRoleName.sendKeys(roleName);
		// test_steps.add("Entered Role name");
		AdminLogger.info("Entered Role name");

		roles.enterRoleDescription.sendKeys("Test Role Description");
		// test_steps.add("Entered Role Description");
		AdminLogger.info("Entered Role Description");

		Utility.ScrollToElement(roles.checkboxLedgerBalancesReport, driver);
		roles.checkboxLedgerBalancesReport.click();
		test_steps.add("Clicked on Ledger Balances Report checkbox");
		AdminLogger.info("Clicked on Ledger Balances Report checkbox");
		Wait.wait2Second();

		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		Wait.wait5Second();
		WebElement popupPermissions = driver.findElement(By.xpath(permissionsPopup));
		Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?", "Faild to get Popup" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/RPT-255'>" + "Click here to open JIRA: RPT-255</a>");
		test_steps.add("Required Permissions Popup displayed" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/RPT-255'>" + "Click here to open JIRA: RPT-255</a>");
		AdminLogger.info("Required Permissions Popup displayed");

		ArrayList<String> permissionsAll = new ArrayList<String>();
		List<WebElement> permissionsList = driver.findElements(By.xpath("//li[@data-bind='text: EntityName']"));

		// Validating all the required permissions
		for (WebElement permission : permissionsList) {
			permissionsAll.add(permission.getText());
		}
		test_steps.add("Stored all permissions into a list");
		AdminLogger.info("Stored all permissions into a list");

		Assert.assertEquals(permissionsAll.get(0), "Reservations(View)",
				"Faild to get required permission: Reservations(View)" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/RPT-254'>"
						+ "Click here to open JIRA: RPT-254</a>");
		Assert.assertEquals(permissionsAll.get(1), "Reservations(Add)",
				"Faild to get required permission: Reservations(Add)");
		Assert.assertEquals(permissionsAll.get(2), "Account(View)", "Faild to get required permission: Account(View)");
		Assert.assertEquals(permissionsAll.get(3), "Folio(View)", "Faild to get required permission: Folio(View)");

		test_steps.add(
				"Successfully validated Required permissions for Ledger Balances Report: Reservations(View), Reservations(Add), Account(View, Folio(View)"
						+ "<br>" + "<a href='https://innroad.atlassian.net/browse/RPT-254'>"
						+ "Click here to open JIRA: RPT-254</a>");
		AdminLogger.info(
				"Successfully validated Required permissions for Ledger Balances Report: Reservations(View), Reservations(Add), Account(View, Folio(View)");

		roles.buttonCancelPopup.click();
		test_steps.add("Clicked on Cancel button on Popup");
		AdminLogger.info("Clicked on Cancel button on Popup");

		// Validating the required permissions: Reservations(Add), Account(View),
		// Folio(View)
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.checkboxReservationsView, driver);
		Utility.ScrollToElement(roles.checkboxReservationsView, driver);
		Wait.wait1Second();
		roles.checkboxReservationsView.click();
		test_steps.add("Clicked on Reservations view checkbox");
		AdminLogger.info("Clicked on Reservations view checkbox");
		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		Wait.wait5Second();
		Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?", "Faild to get Popup");
		test_steps.add("Required Permissions Popup displayed");
		AdminLogger.info("Required Permissions Popup displayed");

		permissionsAll.clear();
		permissionsList.clear();

		permissionsList = driver.findElements(By.xpath("//li[@data-bind='text: EntityName']"));
		for (WebElement permission : permissionsList) {
			permissionsAll.add(permission.getText());
		}
		test_steps.add("Stored all permissions into a list");
		AdminLogger.info("Stored all permissions into a list");

		Assert.assertEquals(permissionsAll.get(0), "Reservations(Add)",
				"Faild to get required permission: Reservations(Add)");
		Assert.assertEquals(permissionsAll.get(1), "Account(View)", "Faild to get required permission: Account(View)");
		Assert.assertEquals(permissionsAll.get(2), "Folio(View)", "Faild to get required permission: Folio(View)");

		test_steps.add(
				"Successfully validated Required permissions for Ledger Balances Report: Reservations(Add), Account(View, Folio(View)");
		AdminLogger.info(
				"Successfully validated Required permissions for Ledger Balances Report: Reservations(Add), Account(View, Folio(View)");

		roles.buttonCancelPopup.click();
		test_steps.add("Clicked on Cancel button on Popup");
		AdminLogger.info("Clicked on Cancel button on Popup");

		// Validating the required permissions: Account(View), Folio(View)
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.checkboxReservationsAdd, driver);
		Utility.ScrollToElement(roles.checkboxReservationsAdd, driver);
		Wait.wait1Second();
		roles.checkboxReservationsAdd.click();
		test_steps.add("Clicked on Reservations Add checkbox");
		AdminLogger.info("Clicked on Reservations Add checkbox");
		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		Wait.wait5Second();
		Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?", "Faild to get required Popup");
		test_steps.add("Required Permissions Popup displayed");
		AdminLogger.info("Required Permissions Popup displayed");

		permissionsAll.clear();
		permissionsList.clear();

		permissionsList = driver.findElements(By.xpath("//li[@data-bind='text: EntityName']"));
		for (WebElement permission : permissionsList) {
			permissionsAll.add(permission.getText());
		}
		test_steps.add("Stored all permissions into a list");
		AdminLogger.info("Stored all permissions into a list");

		Assert.assertEquals(permissionsAll.get(0), "Account(View)", "Faild to get required permission: Account(View)");
		Assert.assertEquals(permissionsAll.get(1), "Folio(View)", "Faild to get required permission: Folio(View)");
		test_steps.add(
				"Successfully validated Required permissions for Ledger Balances Report: Account(View, Folio(View)");
		AdminLogger.info(
				"Successfully validated Required permissions for Ledger Balances Report: Account(View, Folio(View)");

		roles.buttonCancelPopup.click();
		test_steps.add("Clicked on Cancel button on Popup");
		AdminLogger.info("Clicked on Cancel button on Popup");

		// Validating the required permissions: Folio(View)
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.checkboxAccountView, driver);
		Utility.ScrollToElement(roles.checkboxAccountView, driver);
		Wait.wait1Second();
		roles.checkboxAccountView.click();
		test_steps.add("Clicked on Account View checkbox");
		AdminLogger.info("Clicked on Account View checkbox");
		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		Wait.wait5Second();
		Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?", "Faild to get required Popup");
		test_steps.add("Required Permissions Popup displayed");
		AdminLogger.info("Required Permissions Popup displayed");

		permissionsAll.clear();
		permissionsList.clear();

		permissionsList = driver.findElements(By.xpath("//li[@data-bind='text: EntityName']"));
		for (WebElement permission : permissionsList) {
			permissionsAll.add(permission.getText());
		}
		test_steps.add("Stored all permissions into a list");
		AdminLogger.info("Stored all permissions into a list");

		Assert.assertEquals(permissionsAll.get(0), "Folio(View)", "Faild to get required permission: Folio(View)");
		test_steps.add("Successfully validated Required permissions for Ledger Balances Report: Folio(View)");
		AdminLogger.info("Successfully validated Required permissions for Ledger Balances Report: Folio(View)");

		roles.buttonCancelPopup.click();
		test_steps.add("Clicked on Cancel button on Popup");
		AdminLogger.info("Clicked on Cancel button on Popup");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.checkboxFolioView, driver);
		Utility.ScrollToElement(roles.checkboxFolioView, driver);
		Wait.wait1Second();
		roles.checkboxFolioView.click();
		test_steps.add("Clicked on Folio View checkbox");
		AdminLogger.info("Clicked on Folio View checkbox");
		Wait.wait1Second();
		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		// admin.AdminIcon.click();
		Utility.ScrollToUp(driver);

		Wait.explicit_wait_xpath(OR_Admin.buttonRoles, driver);
		// Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.buttonRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAllRoles, driver);
		roles.linkAllRoles.click();
		test_steps.add("Clicked on All roles");
		AdminLogger.info("Clicked on All roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.txtRoleList, driver);
		// WebElement role = driver.findElement(By.xpath(roleLoctor));

		if (driver.findElement(By.xpath(roleLoctor)).isDisplayed()) {
			test_steps.add(roleName + " Role is successfully created");
			AdminLogger.info(roleName + " Role is successfully created");
		} else {
			Assert.assertTrue(false, "Failed to create new Role");

		}
	}

	public void verifyRequiredPermissionsReportsV2NewRoleAllReports(WebDriver driver, String reportName,
			ArrayList<String> test_steps) throws Exception {
		String permissionsPopup = "//h4[@id='myModalLabel'][@data-bind='text: ModalTitleText']";
		String roleName = Utility.GenerateRandomString();
		String roleLoctor = "//a[contains(text(),'" + roleName + "')]";
		String reportCheckboxLocator = "//div[text()='Reports Access']//following::table//tr//td//span[text()='"
				+ reportName + "']//ancestor::tr//td[@class='text-center']//input";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.enterRoleName, driver);
		roles.enterRoleName.clear();
		roles.enterRoleName.sendKeys(roleName);
		test_steps.add("Entered Role name");
		AdminLogger.info("Entered Role name");
		roles.enterRoleDescription.clear();
		roles.enterRoleDescription.sendKeys("Test Role Description");
		test_steps.add("Entered Role Description");
		AdminLogger.info("Entered Role Description");

		WebElement reportCheckbox = driver.findElement(By.xpath(reportCheckboxLocator));
		Utility.ScrollToElement(reportCheckbox, driver);
		Wait.wait2Second();
		reportCheckbox.click();
		test_steps.add("Clicked on " + reportName + " checkbox, Checkbox selected");
		AdminLogger.info("Clicked on " + reportName + " checkbox, Checkbox selected");
		Wait.wait3Second();

		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		Wait.wait5Second();
		WebElement popupPermissions = driver.findElement(By.xpath(permissionsPopup));
		Assert.assertEquals(popupPermissions.getText(), "Enable Required Permissions?", "Faild to get Popup");
		test_steps.add("Required Permissions Popup displayed");
		AdminLogger.info("Required Permissions Popup displayed");

		WebElement permission = driver.findElement(By.xpath("//li[@data-bind='text: EntityName']"));
		Assert.assertEquals(permission.getText(), "Reservations(View)",
				"Faild to get required permission: Reservations(View)");

		test_steps.add("Successfully validated Required permissions for " + reportName + ": Reservations(View)");
		AdminLogger.info("Successfully validated Required permissions for " + reportName + ": Reservations(View)");

		roles.buttonCancelPopup.click();
		test_steps.add("Clicked on Cancel button on Popup");
		AdminLogger.info("Clicked on Cancel button on Popup");

		/*
		 * Wait.explicit_wait_xpath(reportCheckboxLocator, driver);
		 * Utility.ScrollToElement(reportCheckbox, driver); reportCheckbox.click();
		 * test_steps.add("Clicked on " + reportName +
		 * " checkbox, Checkbox deselected"); AdminLogger.info("Clicked on " +
		 * reportName + " checkbox, Checkbox deselected");
		 */
	}

	public void verifyReportsV2EntitlementsCustomRoleCreation(WebDriver driver, ArrayList<String> test_steps)
			throws Exception {
		String roleName = Utility.GenerateRandomString() + Utility.GenerateRandomString();
		String roleLoctor = "//a[contains(text(),'" + roleName + "')]";

		Elements_Admin admin = new Elements_Admin(driver);
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.enterRoleName, driver);
		roles.enterRoleName.clear();
		roles.enterRoleName.sendKeys(roleName);
		test_steps.add("Entered Role name");
		AdminLogger.info("Entered Role name");
		roles.enterRoleDescription.clear();
		roles.enterRoleDescription.sendKeys("Test Role Description");
		test_steps.add("Entered Role Description");
		AdminLogger.info("Entered Role Description");

		roles.checkboxSystem.click();
		roles.checkboxClients.click();
		roles.checkboxProperties.click();
		roles.checkboxRoomClasses.click();
		roles.checkboxUsers.click();
		roles.checkboxReservations.click();
		Utility.ScrollToElement(roles.checkboxAccountView, driver);
		roles.checkboxAccountView.click();
		roles.checkboxFolioView.click();

		Wait.wait2Second();
		Utility.ScrollToElement(roles.checkboxLedgerBalancesReport, driver);
		roles.checkboxLedgerBalancesReport.click();
		roles.checkboxAdvanceDepositReport.click();
		roles.checkboxTransactionsReport.click();
		roles.checkboxFolioBalancesReport.click();
		roles.checkboxDailyFlashReport.click();
		roles.checkboxRoomForecastReport.click();
		roles.checkboxNetSalesReport.click();

		Wait.wait1Second();
		roles.buttonSaveRole.click();
		test_steps.add("Clicked on Save button");
		AdminLogger.info("Clicked on Save button");

		Wait.wait2Second();
		Utility.ScrollToUp(driver);

		Wait.explicit_wait_xpath(OR_Admin.buttonRoles, driver);
		// Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkRoles, driver);
		admin.buttonRoles.click();
		test_steps.add("Navigated to Roles");
		AdminLogger.info("Navigated to Roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.linkAllRoles, driver);
		roles.linkAllRoles.click();
		test_steps.add("Clicked on All roles");
		AdminLogger.info("Clicked on All roles");

		Wait.waitUntilPresenceOfElementLocated(OR_Admin.txtRoleList, driver);
		// WebElement role = driver.findElement(By.xpath(roleLoctor));

		if (driver.findElement(By.xpath(roleLoctor)).isDisplayed()) {
			test_steps.add(roleName + " Role is successfully created");
			AdminLogger.info(roleName + " Role is successfully created");
		} else {
			Assert.assertTrue(false, "Failed to create new Role");
		}
	}

	public void verifyNavigationToReportsV2(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_Reports reports = new Elements_Reports(driver);
		Elements_On_All_Navigation navigate = new Elements_On_All_Navigation(driver);
		Elements_Inventory inventory = new Elements_Inventory(driver);
		Navigation nav = new Navigation();

		test_steps.add("======= Navigating from Reservation Search to Reports V2 page ========");
		// nav.Reservation(driver);
		try {
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to Reports V2 page from Reservation Search page");
			AdminLogger.info("Successfully navigated to Reports V2 page from Reservation Search page");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to navigating to Reports V2 page from Reservations Search page");
		}

		test_steps.add("======= Navigating from Accounts to Reports V2 page ========");
		reports.linkAccounts.click();
		Wait.explicit_wait_visibilityof_webelement_150(navigate.Accounts_sec_Nav, driver);
		test_steps.add("Navigated to Accounts page");
		try {
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to Reports V2 page from Accounts module");
			AdminLogger.info("Successfully navigated to Reports V2 page from Accounts module");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to navigating to Reports V2 page from Accounts page");
		}

		test_steps.add("======= Navigating from Guest Services to Reports V2 page ========");
		reports.linkGuestServices.click();
		// nav.GuestServices(driver);

		try {
			Wait.explicit_wait_xpath(OR.GuestServicesPage, driver);
		} catch (Exception e) {
			Wait.explicit_wait_xpath(OR.GuestServicesLabel, driver);
		}

		test_steps.add("Navigated to Guest Services page");
		try {
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to Reports V2 page from Guest Services" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/RPT-256'>"
					+ "Click here to open JIRA: RPT-256</a>");
			AdminLogger.info("Successfully navigated to Reports V2 page from Guest Services");
		} catch (Exception e) {
			Assert.assertTrue(false,
					"Failed to navigating to Reports V2 page from Guest Services page" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/RPT-256'>"
							+ "Click here to open JIRA: RPT-256</a>");
		}

		Wait.wait3Second();
		test_steps.add("======= Navigating from Inventory to Reports V2 page ========");
		reports.linkInventory.click();
		Wait.explicit_wait_xpath(OR.Inventory_Grid, driver);
		test_steps.add("Navigated to Inventory page");
		try {
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to Reports V2 page from Inventory");
			AdminLogger.info("Successfully navigated to Reports V2 page from Inventory");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to navigating to Reports V2 page from Inventory page");
		}

		Wait.wait3Second();
		/*
		 * test_steps.
		 * add("======= Navigating from Inventory Rates Grid to Reports V2 page ========"
		 * ); reports.linkInventory.click(); Wait.explicit_wait_xpath(OR.Inventory_Grid,
		 * driver); test_steps.add("Navigated to Inventory page"); //nav.Rates2(driver);
		 * //navigate.rates2.click(); WebElement rates = driver.findElement(By.
		 * xpath("//a[@id='MainContent_rptrMenu_lbtnMenuItem_0' and text()='Rates Grid']"
		 * )); rates.click();
		 * Wait.explicit_wait_xpath("//span[text()='Average Weekly Occupancy']",
		 * driver); try { //nav.ReportsV2(driver);
		 * Wait.waitUntilPresenceOfElementLocated(OR_Reports.Reports2, driver);
		 * navigate.Reports2.click();
		 * Wait.explicit_wait_xpath(OR_Reports.linkBrowseAllReports, driver);
		 * Wait.WaitForElement(driver, OR_Reports.linkBrowseAllReports); test_steps.
		 * add("Successfully navigated to Reports V2 page from Inventory Rates");
		 * AdminLogger.
		 * info("Successfully navigated to Reports V2 page from Inventory Rates");
		 * }catch (Exception e) { Assert.assertTrue(false,
		 * "Failed to navigating to Reports V2 page from Inventory Rates page"); }
		 * 
		 * Wait.wait3Second(); test_steps.
		 * add("======= Navigating from Inventory Demand Management to Reports V2 page ========"
		 * ); reports.linkInventory.click(); Wait.explicit_wait_xpath(OR.Inventory_Grid,
		 * driver); test_steps.add("Navigated to Inventory page"); //nav.Rates2(driver);
		 * //navigate.rates2.click();
		 * Wait.waitUntilPresenceOfElementLocated(OR_Inventory.linkDemandManagement,
		 * driver); inventory.linkDemandManagement.click(); try { String linkGoals =
		 * "//h3[contains(text(),'goals')]"; Wait.explicit_wait_xpath(linkGoals,
		 * driver);
		 * 
		 * }catch(Exception e) { Wait.explicit_wait_xpath(OR_Inventory.linkGoals,
		 * driver); }
		 * 
		 * try { //nav.ReportsV2(driver);
		 * Wait.waitUntilPresenceOfElementLocated(OR_Reports.Reports2, driver);
		 * navigate.Reports2.click();
		 * Wait.explicit_wait_xpath(OR_Reports.linkBrowseAllReports, driver);
		 * Wait.WaitForElement(driver, OR_Reports.linkBrowseAllReports); test_steps.
		 * add("Successfully navigated to Reports V2 page from Inventory Demand Management"
		 * ); AdminLogger.
		 * info("Successfully navigated to Reports V2 page from Inventory Demand Management"
		 * ); }catch (Exception e) { Assert.assertTrue(false,
		 * "Failed to navigating to Reports V2 page from Inventory Demand Management page"
		 * ); }
		 */
		Wait.wait3Second();
		test_steps.add("======= Navigating from Setup page to Reports V2 page ========");
		reports.linkSetup.click();
		Wait.explicit_wait_xpath(OR.Setup_Menu_Title, driver);
		test_steps.add("Navigated to Setup page");
		try {
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to Reports V2 page from Setup page");
			AdminLogger.info("Successfully navigated to Reports V2 page from Setup page");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to navigating to Reports V2 page from Setup page");
		}

		Wait.wait3Second();
		test_steps.add("======= Navigating from Admin page to Reports V2 page ========");
		reports.linkAdmin.click();
		Wait.explicit_wait_xpath(OR.Admin_Grid, driver);
		test_steps.add("Navigated to Admin page");
		try {
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to Reports V2 page from Admin page");
			AdminLogger.info("Successfully navigated to Reports V2 page from Admin page");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to navigating to Reports V2 page from Admin page");
		}

		Wait.wait3Second();
		test_steps.add("======= Navigating from Night Audit page to Reports V2 page ========");
		reports.linkNightAudit.click();
		Wait.explicit_wait_xpath(OR.Period_status, driver);
		test_steps.add("Navigated to Night Audit page");
		try {
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to Reports V2 page from Night Audit page");
			AdminLogger.info("Successfully navigated to Reports V2 page from Night Audit page");
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to navigating to Reports V2 page from Admin page");
		}
	}

	public ArrayList<String> gettingResultsFromReportsV2Search(WebDriver driver, String searchItem,
			ArrayList<String> test_steps) throws Exception {

		Elements_Reports reports = new Elements_Reports(driver);
		Elements_On_All_Navigation navigate = new Elements_On_All_Navigation(driver);
		Navigation nav = new Navigation();

		ArrayList<String> SearchReports_Actual;
		ArrayList<String> SearchReports = new ArrayList<String>();

		// reports.SearchReports.click();
		Utility.clickThroughAction(driver, reports.SearchReports);
		reports.SearchReports.sendKeys(searchItem);
		Wait.wait2Second();
		List<WebElement> SearchElements = driver.findElements(By.xpath(OR_Reports.SearchElements));
		System.out.println(SearchElements.size());

		for (int i = 0; i < SearchElements.size(); i++) {
			SearchReports.add(SearchElements.get(i).getText());
		}

		return SearchReports;
	}

	// This method is to change user status and email
	public void changeUserStatusAndEmail(WebDriver driver, String loginID, String Email, String Status,
			ArrayList<String> test_steps) throws InterruptedException {

		String userLocator = "//a[text()='" + loginID + "']";

		Elements_Users user = new Elements_Users(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(user.linkAllUsers, driver);

		user.linkAllUsers.click();
		Wait.explicit_wait_visibilityof_webelement(user.txtUsersList, driver);
		WebElement linkLoginID = driver.findElement(By.xpath(userLocator));

		Utility.ScrollToElement(linkLoginID, driver);
		linkLoginID.click();
		Wait.waitForElementToBeVisibile(By.xpath(OR.UserFirstName), driver);
		Wait.wait1Second();
		user.UserEmail.clear();
		user.UserEmail.sendKeys(Email);
		AdminLogger.info("Email Changed To : " + Email);
		test_steps.add("Email Changed To : " + Email);
		Wait.explicit_wait_visibilityof_webelement(user.Select_Status, driver);
		new Select(user.Select_Status).selectByVisibleText(Status);
		test_steps.add("Status Changed to : " + Status);
		String SelectedOption = new Select(user.Select_Status).getFirstSelectedOption().getText();
		assertTrue(SelectedOption.equals(Status), "Status didn't change");
		Wait.wait1Second();

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getSelectedCurrency> ' Description: <Get selected currency
	 * from client info option page > ' ' Input parameters: <WebDriver> ' Return:
	 * <void> Created By: Farhan Ghaffar> ' Created On: <3 August 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String getSelectedCurrency(WebDriver driver) throws InterruptedException {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement_ID(driver, OR_Admin.SelectCurrency);
		Wait.waitForElementToBeVisibile(By.id(OR_Admin.SelectCurrency), driver);
		Wait.waitForElementToBeClickable(By.id(OR_Admin.SelectCurrency), driver);
		Select select = new Select(admin.selectCurrency);
		WebElement option = select.getFirstSelectedOption();
		String selectedOption = option.getText();
		System.out.println(selectedOption);
		AdminLogger.info("Currency: " + selectedOption);
		selectedOption = selectedOption.split("\\(")[1];
		AdminLogger.info("Currency: " + selectedOption);

		selectedOption = selectedOption.replace(")", "");

		AdminLogger.info("Currency: " + selectedOption.length());

		selectedOption = selectedOption.trim();

		return selectedOption;
	}

	public String getSelectedCurrencyNameAndSign(WebDriver driver) throws InterruptedException {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement_ID(driver, OR_Admin.SelectCurrency);
		Wait.waitForElementToBeVisibile(By.id(OR_Admin.SelectCurrency), driver);
		Wait.waitForElementToBeClickable(By.id(OR_Admin.SelectCurrency), driver);
		Select select = new Select(admin.selectCurrency);
		WebElement option = select.getFirstSelectedOption();
		String selectedOption = option.getText();
		System.out.println(selectedOption);
		AdminLogger.info("Currency: " + selectedOption);
		selectedOption = selectedOption.trim();
		return selectedOption;
	}

	public void selectCurrencyNameAndSign(WebDriver driver, ArrayList<String> testSteps, String currencyLabel)
			throws InterruptedException {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement_ID(driver, OR_Admin.SelectCurrency);
		Wait.waitForElementToBeVisibile(By.id(OR_Admin.SelectCurrency), driver);
		Wait.waitForElementToBeClickable(By.id(OR_Admin.SelectCurrency), driver);
		Select select = new Select(admin.selectCurrency);
		select.selectByVisibleText(currencyLabel);
		AdminLogger.info("Selected Currency: " + currencyLabel);
		testSteps.add("Selected Currency : " + currencyLabel);
	}

	public void selectDateFormat(WebDriver driver, ArrayList<String> testSteps, String dateFormat)
			throws InterruptedException {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.SELECT_DATE_FORMAT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.SELECT_DATE_FORMAT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.SELECT_DATE_FORMAT), driver);
		Select select = new Select(admin.selectDateFormat);
		select.selectByVisibleText(dateFormat);
		AdminLogger.info("Selected date format : " + dateFormat);
		testSteps.add("Selected date format : " + dateFormat);
	}

	public void selectStartDayOfWeek(WebDriver driver, ArrayList<String> testSteps, String weekDay)
			throws InterruptedException {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement_ID(driver, OR_Admin.selectWeekDay);
		Wait.waitForElementToBeVisibile(By.id(OR_Admin.selectWeekDay), driver);
		Wait.waitForElementToBeClickable(By.id(OR_Admin.selectWeekDay), driver);
		Select select = new Select(admin.selectWeekDay);
		select.selectByVisibleText(weekDay);
		AdminLogger.info("Selected Week day : " + weekDay);
		testSteps.add("Selected Week day : " + weekDay);
	}

	public void clickSaveClientDetails(WebDriver driver, ArrayList<String> testSteps) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.saveClientDetails);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.saveClientDetails), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.saveClientDetails), driver);
		admin.saveClientDetails.click();
		testSteps.add("Click on save");
		AdminLogger.info("Click on save");
	}

	public String getPropertyName(WebDriver driver) {
		String strProperty = "//p[@class='propertySelectName']";
		String property = driver.findElement(By.xpath(strProperty)).getText();
		return property;
	}

	public String getDefaultCurrency(WebDriver driver) {
		String currency = getDefaultClientCurrency(driver);
		AdminLogger.info(currency);
		String arrayList[] = currency.trim().split("\\(");
		currency = arrayList[0].trim();
		return currency;

	}

	public void clickAutoMaskPhone(WebDriver driver, boolean isCheck, ArrayList<String> testSteps) {
		Elements_Admin admin = new Elements_Admin(driver);
		if (isCheck) {
			if (admin.clickPhoneMask.isSelected()) {
				AdminLogger.info("Checkbox already checked");
			} else {
				admin.clickPhoneMask.click();
				adminSaveButton(driver);
				testSteps.add("Checkbox  checked");
				AdminLogger.info("Checkbox  checked");
			}
		} else {
			if (admin.clickPhoneMask.isSelected()) {
				admin.clickPhoneMask.click();
				adminSaveButton(driver);
				AdminLogger.info("Checkbox  Unchecked");
			} else {

				testSteps.add("Checkbox  already unchecked");
				AdminLogger.info("Checkbox  already unchecked");
			}

		}

	}

	public String getAdminName(WebDriver driver) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.GetAdminName), driver);

		String adminName = admin.getAdminName.getText();
		return adminName;
	}

	public void clickOnUserTab(WebDriver driver) {
		Elements_Users user = new Elements_Users(driver);
		Wait.WaitForElement_ID(driver, OR_Admin.UserTab);
		Wait.waitForElementToBeClickable(By.id(OR_Admin.UserTab), driver);
		user.UserTab.click();
	}

	public void clickOnSearchButton(WebDriver driver) {
		Elements_Users user = new Elements_Users(driver);
		Wait.WaitForElement(driver, OR_Admin.SearchButton);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.SearchButton), driver);
		user.SearchButton.click();
	}

	public String getUserName(WebDriver driver, String loginUserName) {

		String path = "//a[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ loginUserName.toUpperCase() + "']//..//following-sibling::td";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		List<WebElement> listOfElement = driver.findElements(By.xpath(path));
		String getLastName = listOfElement.get(0).getText();
		String getFiestName = listOfElement.get(1).getText();
		return getFiestName + " " + getLastName;

	}

	public void selectAdminstatorRole(WebDriver driver, ArrayList<String> testSteps) {
		String all = "//a[text()='All']";
		Wait.WaitForElement(driver, all);
		Wait.waitForElementToBeClickable(By.xpath(all), driver);
		driver.findElement(By.xpath(all)).click();

		String admin = "//a[text()='Administrator']/..";
		Wait.WaitForElement(driver, admin);
		Wait.waitForElementToBeClickable(By.xpath(admin), driver);
		driver.findElement(By.xpath(admin)).click();

	}

	public void makeDeleteTaskEnableOrDisable(WebDriver driver, ArrayList<String> testSteps, Boolean deleteTask)
			throws InterruptedException {
		String enable = "//span[text()='Delete task']/../following-sibling::td/input";
		Wait.WaitForElement(driver, enable);
		String save = "//button[text()='Save']";

		if (deleteTask.equals(true)) {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				testSteps.add("Delete task is already checked");
				AdminLogger.info("Delete task is already checked");
			} else {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Delete task is checked");
				AdminLogger.info("Delete task is checked");

				driver.findElement(By.xpath(save)).click();
				String sucess = "//div[contains(text(),'Successfully')]";
				Wait.WaitForElement(driver, sucess);
			}
		} else {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Delete task is unchecked");
				AdminLogger.info("Delete task is unchecked");
				driver.findElement(By.xpath(save)).click();
				String sucess = "//div[contains(text(),'Successfully')]";
				Wait.WaitForElement(driver, sucess);
			} else {
				testSteps.add("Delete task is already unchecked");
				AdminLogger.info("Delete task is already unchecked");

			}
		}

		Wait.wait5Second();
	}

	public void makeAvailabilityRulesEnableOrDisable(WebDriver driver, ArrayList<String> testSteps, Boolean deleteTask)
			throws InterruptedException {
		String enable = "//span[text()='Availability Rules']/../following-sibling::td/input";
		Wait.WaitForElement(driver, enable);
		String save = "//button[text()='Save']";

		if (deleteTask.equals(true)) {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				testSteps.add("Availability Rules is already checked");
				AdminLogger.info("Availability Rules is already checked");
			} else {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Availability Rules is checked");
				AdminLogger.info("Availability Rules is checked");

				driver.findElement(By.xpath(save)).click();
				try {
					String sucess = "//div[contains(text(),'Successfully')]";
					Wait.WaitForElement(driver, sucess);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Availability Rules is unchecked");
				AdminLogger.info("Availability Rules is unchecked");
				driver.findElement(By.xpath(save)).click();
				try {
					String sucess = "//div[contains(text(),'Successfully')]";
					Wait.WaitForElement(driver, sucess);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				testSteps.add("Availability Rules is already unchecked");
				AdminLogger.info("Availability Rules is already unchecked");

			}
		}

		Wait.wait5Second();
	}

	public void changesOrVerifyAllPreferencesValues(WebDriver driver, ArrayList<String> test_steps, boolean change)
			throws Exception {
		String value = "";
		String prefencesText = "//select[contains(@data-bind,'value: preferenceValue, enable: !$parent.isSaving()')]//parent::td//..//span";
		String preferencesSelect = "//select[contains(@data-bind,'value: preferenceValue, enable: !$parent.isSaving()')]";
		List<WebElement> elementText = driver.findElements(By.xpath(prefencesText));
		List<WebElement> elementSelect = driver.findElements(By.xpath(preferencesSelect));
		if (change) {
			for (int i = 0; i < elementSelect.size(); i++) {
				Select select = new Select(elementSelect.get(i));
				if (elementText.get(i).getText().equalsIgnoreCase("Default Tape Chart View")) {
					select.selectByVisibleText("3 Days");
					test_steps.add("Select 3 Days for " + elementText.get(i).getText());
				} else {
					select.selectByVisibleText("No");
					test_steps.add("Select No for " + elementText.get(i).getText());

				}
			}
		} else {
			for (int i = 0; i < elementSelect.size(); i++) {
				Select select = new Select(elementSelect.get(i));
				if (elementText.get(i).getText().equalsIgnoreCase("Default Tape Chart View")) {
					value = select.getFirstSelectedOption().getText();
					assertEquals(value, "7 Days", "Selected Value is not retun to default");
					test_steps.add("Select value for " + elementText.get(i).getText() + " is return to default");
				} else {
					value = select.getFirstSelectedOption().getText();
					assertEquals(value, "Yes", "Selected Value is not retun to default");
					test_steps.add("Select value for " + elementText.get(i).getText() + " is return to default");

				}
			}
		}
	}

	public void valideUserFieldIsReset(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		String value = "";
		String clientName = "//input[contains(@data-bind,'value: $parent.ClientName')]";
		String PasswordField = "//label[text()='Password:']//following-sibling::div//input[1]";
		Elements_Users user = new Elements_Users(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		value = user.UserFirstName.getText();
		assertEquals(value, "", "user first name field is not empty");
		test_steps.add("Verify first name field is empty");
		value = user.UserLastName.getText();
		assertEquals(value, "", "user first name field is not empty");
		test_steps.add("Verify first name field is empty");

		value = user.UserLogin.getText();
		assertEquals(value, "", "user login id is not empty");
		test_steps.add("Verify user login id is empty");

		user.UserEmail.clear();
		user.UserEmail.getText();
		assertEquals(value, "", "user email is not empty");
		test_steps.add("Verify user email is empty");
		assertEquals(driver.findElement(By.xpath(PasswordField)).isEnabled(), false, "password field is enabled");
		test_steps.add("Verified password input is disabled");
		assertEquals(driver.findElement(By.xpath(clientName)).isEnabled(), false, "client name field is enabled");
		test_steps.add("Verified client name  is disabled");

		assertEquals(user.User_ResetPassword_Btn.isEnabled(), true, "Reset password button is disabled");
		test_steps.add("Verified reset password button is enabled");
		String selectedValue = "(//select[contains(@data-bind,'options: filteredItemsRight')])[last()]";
		WebElement selectedValueOption = driver.findElement(By.xpath(selectedValue));

		user.User_AssociateRole_Btn.click();
		test_steps.add("Click on associate role button ");
		Wait.wait2Second();
		value = selectedValueOption.getText();
		assertEquals(value, "", "Selected Item found");
		test_steps.add("Verify associate role selected list is empty");
		user.User_AssociateRole_Done.click();

		Wait.WaitForElement(driver, OR.User_AssociateProperties_Btn);
		Wait.explicit_wait_visibilityof_webelement(user.User_AssociateProperties_Btn, driver);
		Utility.ScrollToElement(user.User_AssociateProperties_Btn, driver);
		user.User_AssociateProperties_Btn.click();
		Wait.wait2Second();
		value = selectedValueOption.getText();
		assertEquals(value, "", "Selected Item found");
		test_steps.add("Verify associate role selected list is empty");
		user.User_AssociateRole_Done.click();

	}

	// This method is to enable/disable Special Functions using type
	public void enableSpecialFunctionsForRoleUsingType(WebDriver driver, String type, boolean isEnable,
			ArrayList<String> test_steps) throws Exception {
		String strType = "//div[text()='Special Functions']//following::table//tr//td//span[text()='" + type
				+ "']//ancestor::tr//td[@class='text-center']//input";

		Wait.waitForElementToBeVisibile(By.xpath(strType), driver, 180);
		Wait.wait2Second();
		WebElement entity = driver.findElement(By.xpath(strType));
		Utility.ScrollToElement(entity, driver);
		Wait.wait2Second();
		if (isEnable) {
			if (!entity.isSelected()) {
				entity.click();
				AdminLogger.info("Clicked on given Special function type " + type + " checkbox to enable");
				test_steps.add("Clicked on given Special function type " + type + " checkbox to enable");
			} else {
				AdminLogger.info("Given Special function type " + type + " checkbox is already selected");
				test_steps.add("Given Special function type " + type + " checkbox is already selected");
			}
		} else {
			if (entity.isSelected()) {
				entity.click();
				AdminLogger.info("Clicked on given Special function type " + type + " checkbox to disable");
				test_steps.add("Clicked on given Special function type " + type + " checkbox to disable");
			} else {
				AdminLogger.info("Given Special function type " + type + " checkbox is already unchecked");
				test_steps.add("Given Special function type " + type + " checkbox is already unchecked");
			}
		}
		Wait.wait2Second();
	}

	public void verifyAdminRoleUI(WebDriver driver, ArrayList<String> testSteps, String defaultValue) {
		Elements_Roles role = new Elements_Roles(driver);
		Wait.WaitForElement(driver, OR.New_Role_Btn);
		assertTrue(role.New_Role_Btn.isDisplayed(), "Failed to verify New Role Button");
		testSteps.add("Verified New Role Button");
		AdminLogger.info("Verified New Role Button");
		assertTrue(role.roleDeleteButton.isDisplayed(), "Failed to verify Delete Button");
		testSteps.add("Verified New Delete Button");
		AdminLogger.info("Verified New Delete Button");
		assertTrue(role.roleName.isDisplayed(), "Failed to verify Role Name search box");
		testSteps.add("Verified New Role Search Box");
		AdminLogger.info("Verified New Role Search Box");
		assertTrue(role.roleDescription.isDisplayed(), "Failed to verify Description text box");
		testSteps.add("Verified  Description Search Box");
		AdminLogger.info("Verified Description Search Box");
		assertTrue(role.roleClear.isDisplayed(), "Failed to verify Clear Button");
		testSteps.add("Verified Clear Button");
		AdminLogger.info("Verified Clear Button");
		assertTrue(role.Roles_Search_Btn.isDisplayed(), "Failed to verify Search Button");
		testSteps.add("Verified Search Button");
		AdminLogger.info("Verified Search Button");
		String value = Utility.getDefaultvalueofDropDownBox(driver, role.roleStatusDropDownBox);
		assertEquals(value.toLowerCase().trim(), defaultValue.toLowerCase().trim(),
				"Failed to verify Default selected value");
	}

	public void cickRoleHelpIcon(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_Roles role = new Elements_Roles(driver);
		Wait.WaitForElement(driver, OR_Admin.roleHelpIcon);
		Utility.ScrollToElement(role.roleHelpIcon, driver);
		role.roleHelpIcon.click();
		testSteps.add("Click Help Icon");
		AdminLogger.info("Click Help Icon");
	}

	public void verifyHelpWindow(WebDriver driver, ArrayList<String> testSteps) {
		String title = Utility.getWindowTitle(driver);
		Utility.verifyText(title, "innRoad", "title", testSteps, AdminLogger);
		String path = "//link[@href='https://support.innroad.com/hc/en-us']";
		Wait.WaitForElement(driver, path);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(), "Failed to verify Help Window");
		testSteps.add("Verified Help Window");
		AdminLogger.info("Verified Help Window");
	}

	public void verifySpinerLoading(WebDriver driver) throws InterruptedException {
		Elements_Roles role = new Elements_Roles(driver);
		Wait.wait1Second();
		int count = 0;

		try {
			AdminLogger.info("in try");
			while (count < 20) {
				AdminLogger.info(count);
				if (!role.roleSpinerLoading.isDisplayed()) {
					break;
				}
				count = count + 1;
				Wait.wait2Second();
			}
		} catch (Exception e) {
			AdminLogger.info("in cathc");
		}
	}

	public void clickNewRole(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_Roles roles = new Elements_Roles(driver);

		Wait.waitUntilPresenceOfElementLocated(OR.New_Role_Btn, driver);
		roles.New_Role_Btn.click();
		test_steps.add("Clicked on New Role");
		AdminLogger.info("Clicked on New Role");
		Wait.WaitForElement(driver, OR_Admin.enterRoleName);
		Wait.waitUntilPresenceOfElementLocated(OR_Admin.enterRoleName, driver);
		assertTrue(roles.enterRoleName.isDisplayed(), "Failed to verify New Role Tab");
		test_steps.add("Verified New Role Tab");
		AdminLogger.info("Verified New Role Tab");
	}

	public void disabledSaveButton(WebDriver driver, ArrayList<String> test_steps) {
		Elements_Roles roles = new Elements_Roles(driver);
		Wait.WaitForElement(driver, OR_Admin.buttonSaveRole);
		assertTrue(!roles.buttonSaveRole.isEnabled(), "Failed to verify Save button disabled");
		test_steps.add("Verified Save button disabled on Role Details Page");
		AdminLogger.info("Verified Save button disabled on Role Details Page");

	}

	public void makeCreateTaskEnableOrDisable(WebDriver driver, ArrayList<String> testSteps, Boolean checkbox)
			throws InterruptedException {
		String enable = "//span[text()='Create task']/../following-sibling::td/input";
		Wait.WaitForElement(driver, enable);
		String save = "//button[text()='Save']";

		if (checkbox.equals(true)) {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				testSteps.add("Create task is already checked");
				AdminLogger.info("Create task is already checked");
			} else {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Create task is checked");
				AdminLogger.info("Create task is checked");

				driver.findElement(By.xpath(save)).click();
				String sucess = "//div[contains(text(),'Successfully')]";
				Wait.WaitForElement(driver, sucess);
			}
		} else {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Create task is unchecked");
				AdminLogger.info("Create task is unchecked");
				driver.findElement(By.xpath(save)).click();
				String sucess = "//div[contains(text(),'Successfully')]";
				Wait.WaitForElement(driver, sucess);
			} else {
				testSteps.add("Create task is already unchecked");
				AdminLogger.info("Create task is already unchecked");

			}
		}

		Wait.wait5Second();
	}

	public void makeUpdateTaskEnableOrDisable(WebDriver driver, ArrayList<String> testSteps, Boolean deleteTask)
			throws InterruptedException {
		String enable = "//span[text()='Update a task']/../following-sibling::td/input";
		Wait.WaitForElement(driver, enable);
		String save = "//button[text()='Save']";

		if (deleteTask.equals(true)) {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				testSteps.add("Update task is already checked");
				AdminLogger.info("Update task is already checked");
			} else {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Update task is checked");
				AdminLogger.info("Update task is checked");

				driver.findElement(By.xpath(save)).click();
				String sucess = "//div[contains(text(),'Successfully')]";
				Wait.WaitForElement(driver, sucess);
			}
		} else {
			if (driver.findElement(By.xpath(enable)).isSelected()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath(enable)));
				driver.findElement(By.xpath(enable)).click();
				testSteps.add("Update task is unchecked");
				AdminLogger.info("Update task is unchecked");
				driver.findElement(By.xpath(save)).click();
				String sucess = "//div[contains(text(),'Successfully')]";
				Wait.WaitForElement(driver, sucess);
			} else {
				testSteps.add("Update task is already unchecked");
				AdminLogger.info("Update task is already unchecked");

			}
		}

		Wait.wait5Second();
	}

}
