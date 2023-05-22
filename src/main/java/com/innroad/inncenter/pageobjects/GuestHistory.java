package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.IGuestHistory;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_GuestHistory;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.innroad.inncenter.webelements.Elements_Reservation_SearchPage;
import com.relevantcodes.extentreports.ExtentTest;

public class GuestHistory implements IGuestHistory {

	public static String getAccountNo;
	public static Logger guestHistoryLogger = Logger.getLogger("GuestHistory");

	public void guestHistory(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.wait3Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", guest_his.Guest_History);
		// guest_his.Guest_History.click();
		Wait.wait3Second();
		guestHistoryLogger.info(" System successfully Navigated to Guest History ");
		Wait.wait3Second();
	}

	public void GuestHistory_CombineButtonClick(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement_120(guest_his.GuestHis_CombineCheckBox, driver);
			Utility.ScrollToElement(guest_his.GuestHis_CombineCheckBox, driver);
			guest_his.GuestHis_CombineCheckBox.click();
		} catch (Exception e) {
			Wait.explicit_wait_visibilityof_webelement_120(guest_his.GuestHis_CombineCheckBox1, driver);
			Utility.ScrollToElement(guest_his.GuestHis_CombineCheckBox1, driver);
			guest_his.GuestHis_CombineCheckBox1.click();
		}
	}
	public boolean EnterFirstLastNameSeperately(WebDriver driver,String salutation, String FirstName, String LastName,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		boolean flag = true;
		Wait.wait2Second();
		try {
			
			Wait.waitForElementToBeVisibile(By.xpath(OR.SelectSalutation), driver);
			test_steps.add("Sucess - Guest Account create Page" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2400'>" + "Click here to open JIRA: NG-2400</a>");
			new Select(guest_his.selectSalutation).selectByValue(salutation);
			test_steps.add("Selected salutation: "+ salutation);

		try {
			Wait.WaitForElement(driver, OR.enterAccountFName);
			Wait.waitForElementToBeVisibile(By.xpath(OR.enterAccountFName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.enterAccountFName), driver);
			guest_his.enterAccountFName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterAccountFName.sendKeys(Keys.DELETE);
			guest_his.enterAccountFName.sendKeys(FirstName);
		} catch (Exception e) {

			Wait.WaitForElement(driver, OR.enterFirstName);
			Wait.waitForElementToBeVisibile(By.xpath(OR.enterFirstName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.enterFirstName), driver);
			guest_his.EnterFirstName.sendKeys(Keys.CONTROL + "a");
			guest_his.EnterFirstName.sendKeys(Keys.DELETE);
			guest_his.EnterFirstName.sendKeys(FirstName);
		}
		guestHistoryLogger.info("First Name Enterted: " + FirstName);
		test_steps.add("First Name Enterted: " + FirstName);
		try {
			guest_his.enterGAccountLastName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterGAccountLastName.sendKeys(Keys.DELETE);
			guest_his.enterGAccountLastName.sendKeys(LastName);
		} catch (Exception e) {
			try {
				guest_his.AccountDetailsLastName.sendKeys(Keys.CONTROL + "a");
				guest_his.AccountDetailsLastName.sendKeys(Keys.DELETE);
				guest_his.AccountDetailsLastName.sendKeys(LastName);
			} catch (Exception e1) {
				flag = false;
				try {
					assertEquals(flag, true,"FAIL - Verify guest account page" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
							+ "Click here to open JIRA: NG-900</a>");
				} catch(Exception e2) {
					test_steps.add("FAIL - Verify guest account page" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
							+ "Click here to open JIRA: NG-900</a>"+e2.toString());  
				}catch(Error e2) {
					test_steps.add("FAIL - Verify guest account page" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
							+ "Click here to open JIRA: NG-900</a>"+e2.toString());  
				}
         
			} catch(Error e1) {
				flag = false;
				try {
				assertEquals(flag, true,"FAIL - Open Guest History Account" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
						+ "Click here to open JIRA: NG-900</a>");
				} catch(Error e2) {
					test_steps.add("FAIL - Verify guest account page" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
							+ "Click here to open JIRA: NG-900</a>"+e2.toString());  
				}
				
			}
		}
		guestHistoryLogger.info("Last Name Enterted: " + LastName);
		test_steps.add("Last Name Enterted: " + LastName);
		} catch (Exception e2) {
			flag = false;
			try {
				Assert.assertEquals(flag, true,"FAIL - Open Guest History Account" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
						+ "Click here to open JIRA: NG-900</a>");
			} catch(Error e3) {
				  test_steps.add("FAIL - Open Guest History Account" + "<br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
							+ "Click here to open JIRA: NG-900</a>"+e2.toString());  
			}
		} catch(Error e2) {
			flag = false;
			try {
				Assert.assertEquals(flag, true,"FAIL - Open Guest History Account" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
						+ "Click here to open JIRA: NG-900</a>");
			} catch(Error e3) {
        test_steps.add("FAIL - Open Guest History Account" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-900'>"
						+ "Click here to open JIRA: NG-900</a>"+e2.toString());
			}
		}
		return flag;

	}

	public boolean enterFirstName(WebDriver driver,String salutation, String FirstName, String LastName, ArrayList<String> test_steps)
			throws InterruptedException {
		boolean flag = EnterFirstLastNameSeperately(driver,salutation, FirstName, LastName, test_steps);
		return flag;

	}

	public void GuestHistory_NewAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		Utility.ScrollToElement(guest_his.newAccount, driver);
		guest_his.newAccount.click();
		try {
			// Wait.explicit_wait_visibilityof_webelement_150(driver.findElement(By.xpath(OR.GuestHistoryAccountPage)));
			Wait.explicit_wait_visibilityof_webelement_600(guest_his.selectAccountSalutation, driver);
		} catch (Exception e) {
			Utility.app_logs.info("Catch body");
			driver.navigate().refresh();
			// Wait.explicit_wait_visibilityof_webelement_150(driver.findElement(By.xpath(OR.GuestHistoryAccountPage)));
			guest_his.newAccount.click();
			Wait.explicit_wait_visibilityof_webelement_600(guest_his.selectAccountSalutation, driver);
		}
		assertTrue(guest_his.selectAccountSalutation.isDisplayed(), "Guest history page didn't display");

	}

	public void guestHistory_NewAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", guest_his.newAccount);
		// guest_his.newAccount.click();
		Wait.explicit_wait_absenceofelement(OR.Account_ModuleLoading, driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR.guestHistoryAccountPage), driver);
		} catch (Exception e) {
			Wait.waitForElementToBeVisibile(By.xpath(OR.GuestHistoryAccountPage), driver);

		}

	}


	public void guestHistory_AccountDetails(WebDriver driver, String salutation, String FirstName) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement_150(guest_his.selectAccountSalutation, driver);
			new Select(guest_his.selectAccountSalutation).selectByVisibleText(salutation);
		} catch (Exception e) {
			Wait.explicit_wait_visibilityof_webelement_150(guest_his.selectAccountSalutation_1, driver);
			new Select(guest_his.selectAccountSalutation_1).selectByVisibleText(salutation);
		}
		guest_his.accountFirstName.sendKeys(FirstName);

	}

	public void accountAttributes(WebDriver driver, String MarketSegment, String Referral,
			ArrayList<String> test_steps) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		// String getAccountSince= guest_his.accountSince.getText();
		// guestHistoryLogger.info(" Account Since " +getAccountSince);
		new Select(guest_his.Select_Market_Segment).selectByVisibleText(MarketSegment);
		guestHistoryLogger.info("Market Segment Selected: " + MarketSegment);
		test_steps.add("Market Segment Selected: " + MarketSegment);
		new Select(guest_his.Select_Referrals).selectByVisibleText(Referral);
		guestHistoryLogger.info("Referral Selected: " + Referral);
		test_steps.add("Referral Selected: " + Referral);

	}

	public ArrayList<String> Mailinginfo(WebDriver driver, String AccountFirstName, String AccountLastName,
			String Phonenumber, String alternativeNumber, String Address1, String Address2, String Address3,
			String Email, String city, String State, String Postalcode) throws InterruptedException {

		ArrayList<String> test_steps = new ArrayList<>();
		Elements_Accounts CreateAccount = new Elements_Accounts(driver);

		CreateAccount.Account_Enter_First_Name.sendKeys(AccountFirstName);
		guestHistoryLogger.info("Successfully Entered First Name: " + AccountFirstName);
		test_steps.add("Successfully Entered First Name: " + AccountFirstName);

		CreateAccount.Account_Enter_Last_Name.sendKeys(AccountLastName);
		guestHistoryLogger.info("Successfully Entered Last Name: " + AccountLastName);
		test_steps.add("Successfully Entered Last Name: " + AccountLastName);

		CreateAccount.Account_Phone_number.sendKeys(Phonenumber);
		guestHistoryLogger.info("Successfully Entered Phone Number: " + Phonenumber);
		test_steps.add("Successfully Entered Phone Number: " + Phonenumber);

		CreateAccount.Account_Enter_AltPhoneNumber.sendKeys(alternativeNumber);
		guestHistoryLogger.info("Successfully Entered Alternative Number: " + alternativeNumber);
		test_steps.add("Successfully Entered Alternative Number: " + alternativeNumber);

		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Utility.ElementFinderUntillNotShow(By.xpath(OR.Account_Enter_Line1), driver);
		js1.executeScript("arguments[0].scrollIntoView();", CreateAccount.Account_Enter_Line1);

		CreateAccount.Account_Enter_Line1.sendKeys(Address1);
		CreateAccount.Account_Enter_Line2.sendKeys(Address2);
		CreateAccount.Account_Enter_Line3.sendKeys(Address3);
		guestHistoryLogger.info("Successfully Scroll to Address");
		test_steps.add("Successfully Scroll to Address");

		CreateAccount.Account_Enter_Email.sendKeys(Email);
		guestHistoryLogger.info("Successfully Entered Email: " + Email);
		test_steps.add("Successfully Entered Email: " + Email);

		CreateAccount.Account_Enter_City.sendKeys(city);
		guestHistoryLogger.info("Successfully Entered City: " + city);
		test_steps.add("Successfully Entered City: " + city);

		new Select(CreateAccount.Select_Account_State).selectByVisibleText(State);
		guestHistoryLogger.info("Successfully Seleted State: " + State);
		test_steps.add("Successfully Seleted State: " + State);

		CreateAccount.Account_Enter_PostalCode.sendKeys(Postalcode);
		guestHistoryLogger.info("Successfully Entered Postalcode: " + Postalcode);
		test_steps.add("Successfully Entered Postalcode: " + Postalcode);

		return test_steps;
	}


	public void mailinginfo(WebDriver driver, String AccountFirstName, String AccountLastName, String Phonenumber,
			String alternativeNumber, String Address1, String Address2, String Address3, String Email, String city,
			String State, String Postalcode, ArrayList<String> test_steps) throws InterruptedException {
		Mailinginfo(driver, AccountFirstName, AccountLastName, Phonenumber, alternativeNumber, Address1, Address2,
				Address3, Email, city, State, Postalcode);
		test_steps.add("Added details for Mailing info sections");
	}

	public void Billinginfo(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Wait.wait5Second();
		if (CreateAccount.Account_Check_Mailing_info.isSelected()) {
			guestHistoryLogger.info("Same as Mailing Info Check box already checked");
			test_steps.add("Same as Mailing Info Check box already checked");

		} else {
			jse.executeScript("arguments[0].click();", CreateAccount.Account_Check_Mailing_info);

			guestHistoryLogger.info("Same as Mailing Info Check box checked");
			test_steps.add("Same as Mailing Info Check box checked");
		}

	}

	public ArrayList<String> Save(WebDriver driver) throws InterruptedException, IOException {

		ArrayList<String> test_steps = new ArrayList<>();
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Wait.explicit_wait_visibilityof_webelement(CreateAccount.Account_save, driver);
		jse.executeScript("arguments[0].click();", CreateAccount.Account_save);
		guestHistoryLogger.info("Clicked on Save button");
		test_steps.add("Clicked on Save button");
		// CreateAccount.Account_save.click();
		// Thread.sleep(5000);
		try {
			Wait.explicit_wait_visibilityof_webelement(CreateAccount.Toaster_Message, driver);

			if (CreateAccount.Toaster_Title.isDisplayed()) {
				String getTotasterTitle_ReservationSucess = CreateAccount.Toaster_Title.getText();
				// String getToastermessage_ReservationSucess =
				// CreateAccount.Toaster_Message.getText();
				Assert.assertEquals(getTotasterTitle_ReservationSucess, "Account Saved");
				// Thread.sleep(3000);
				String getAccountName = CreateAccount.AccountName.getText();
				guestHistoryLogger.info(" Account Name of created guest is  " + getAccountName);
				test_steps.add(" Account Name of created guest is  " + getAccountName);
				// Thread.sleep(3000);

				String getAccountNo = CreateAccount.AccountNo.getText();
				guestHistoryLogger.info(" Account Number of created guest is  " + getAccountNo);
				test_steps.add(" Account Number of created guest is  " + getAccountNo);
				FileOutputStream fos = new FileOutputStream(".\\guestHistoryAccountNo.txt");
				byte b[] = getAccountNo.getBytes();
				fos.write(b);
				fos.close();
				guestHistoryLogger.info(" Account Number of created guest is saved in guestHistoryAccountNo.txt ");
				test_steps.add(" Account Number of created guest is saved in guestHistoryAccountNo.txt ");
				// Wait.wait5Second();

				// if(getToastermessage_ReservationSucess.equals("The account
				// Test
				// Account has been successfully created."));
			}
		} catch (Exception e) {
			System.err.println("Toaster Message not Appear");
			test_steps.add("Toaster Message not Appear");
		}
		assertTrue(!CreateAccount.Account_Save_Button.isEnabled(), "Save button didn't disable");
		assertTrue(!CreateAccount.Account_Save_Clsoe.isEnabled(), "Save button didn't disable");
		assertTrue(!CreateAccount.AccountsPage_Reset_Btn.isEnabled(), "Save button didn't disable");
		assertTrue(CreateAccount.NewReservation_Button.isEnabled(), "Save button didn't disable");

		return test_steps;
	}

	public String GetAccountNumber(WebDriver driver) throws InterruptedException {
		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Utility.ScrollToElement(CreateAccount.AccountNo_1, driver);
		String getAccountNo = CreateAccount.AccountNo_1.getAttribute("value");
		guestHistoryLogger.info(" Account Number of created guest is  " + getAccountNo);
		return getAccountNo;
	}

	public void ChangeAccountNumber(WebDriver driver, String AccountNumber) throws InterruptedException {

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Wait.explicit_wait_visibilityof_webelement_150(CreateAccount.AccountNo_1, driver);
		CreateAccount.AccountNo_1.clear();
		CreateAccount.AccountNo_1.sendKeys(AccountNumber);
		Wait.wait3Second();
		String selectedOption = CreateAccount.AccountNo_1.getAttribute("value");

		System.out.println(selectedOption);
		Assert.assertEquals(AccountNumber, selectedOption);

	}
	public void newReservation(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR.newReservationButton), driver);
			guest_his.newReservationButton.click();
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", guest_his.newReservationButton);
		}
		// guest_his.newReservationButton.click();
		//Wait.explicit_wait_xpath(OR.New_Reservation_Page_Load, driver);
		Wait.wait15Second();
	}

	/*
	 * public void roomAssignment(WebDriver driver,String PropertyName, String
	 * Nights, String Adults, String Children, String RatepromoCode,String
	 * CheckorUncheckAssign, String RoomClassName, String RoomNumber) throws
	 * InterruptedException {
	 * 
	 * Elements_Reservation ReservationPage = new Elements_Reservation(driver);
	 * ReservationPage.Click_RoomPicker.click();
	 * Wait.explicit_wait_xpath(OR.Room_Assignment_PopUp); Wait.wait3Second(); try {
	 * new Select(ReservationPage.Select_property_RoomAssign).selectByVisibleText(
	 * PropertyName); } catch(Exception e) { new
	 * Select(ReservationPage.Select_property_RoomAssign2).selectByVisibleText(
	 * PropertyName); } Wait.wait15Second();
	 * ReservationPage.Click_Arrive_Datepicker.click();
	 * ReservationPage.Click_Today.click();
	 * ReservationPage.Enter_Nigts.sendKeys(Nights);
	 * ReservationPage.Enter_Adults.sendKeys(Adults);
	 * ReservationPage.Enter_Children.sendKeys(Children);
	 * ReservationPage.Enter_Rate_Promocode.sendKeys(RatepromoCode);
	 * 
	 * if(ReservationPage.Check_Assign_Room.isSelected()) { //
	 * ReservationPage.Check_Assign_Room.click();
	 * ReservationPage.Click_Search.click(); } else {
	 * if(CheckorUncheckAssign.equals("Yes")) {
	 * ReservationPage.Check_Assign_Room.click();
	 * ReservationPage.Click_Search.click(); } else {
	 * ReservationPage.Click_Search.click(); } } try {
	 * 
	 * new Select(ReservationPage.Select_Room_Class).selectByVisibleText(
	 * RoomClassName); String selectedOption = new
	 * Select(ReservationPage.Validating_UnAssgined_DDL).getFirstSelectedOption(
	 * ).getText(); guestHistoryLogger.info("selectedOption  " +selectedOption);
	 * if(selectedOption.equals("--Select--")) { //new
	 * Select(ReservationPage.Select_Room_Number).selectByVisibleText(RoomNumber );
	 * new Select(ReservationPage.Select_Room_Number).selectByIndex(1);
	 * Wait.wait5Second(); } else {
	 * guestHistoryLogger.info("Reservation is unassigned"); } } catch(Exception e)
	 * { Wait.explicit_wait_xpath(OR.Validation_Text_NoRooms);
	 * guestHistoryLogger.info("Room Class are not Available for these dates");
	 * 
	 * } ReservationPage.Click_Select.click(); try {
	 * Wait.explicit_wait_xpath(OR.Verify_RulesBroken_Popup); } catch(Exception e) {
	 * guestHistoryLogger.info("Verification failed"); } Wait.wait5Second();
	 * if(ReservationPage.Verify_RulesBroken_Popup.isDisplayed()) {
	 * ReservationPage.Click_Continue_RuleBroken_Popup.click(); } else {
	 * guestHistoryLogger.info("Satisfied Rules"); }
	 * 
	 * if(ReservationPage.Verify_Toaster_Container.isDisplayed()) { String
	 * getTotasterTitle=ReservationPage.Toaster_Title.getText(); String
	 * getToastermessage=ReservationPage.Toaster_Message.getText();
	 * guestHistoryLogger.info(getTotasterTitle + " " +getToastermessage);
	 * Assert.assertEquals(getTotasterTitle, "Room assignment has changed.");
	 * Assert.assertEquals(getToastermessage, "Room assignment has changed."); }
	 * Wait.wait2Second(); String getPropertyName =
	 * ReservationPage.Get_Property_Name.getText(); String
	 * getRoomclassName_status=ReservationPage.Get_RoomClass_Status.getText();
	 * guestHistoryLogger.info(getRoomclassName_status);
	 * Assert.assertEquals(getPropertyName,PropertyName ); String
	 * getRoomclassName[]= getRoomclassName_status.split(" ");
	 * //Assert.assertEquals(getRoomclassName[0],RoomClassName );
	 * if(CheckorUncheckAssign.equals("Yes")) {
	 * 
	 * } else { Assert.assertEquals(getRoomclassName[3],"Unassigned" ); }
	 * 
	 * 
	 * }
	 */

	public void guestHistoryAccountNo(WebDriver driver) throws InterruptedException, IOException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		getAccountNo = guest_his.guestHistoryAccountNo.getText();
		guestHistoryLogger.info("Guest History Account Number is :" + getAccountNo);
		FileOutputStream fos = new FileOutputStream(".\\guestHistoryAccountNo.txt");
		byte b[] = getAccountNo.getBytes();
		fos.write(b);
		fos.close();
		Wait.wait5Second();

	}

	public void closeGuestHistoryReservation(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his_res = new Elements_GuestHistory(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", guest_his_res.closeGuestHistoryRes);
		// guest_his_res.closeGuestHistoryRes.click();
		Wait.wait10Second();

	}

	public void closeAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);

		try {
			try {
				Wait.explicit_wait_visibilityof_webelement(guest_his.AccountSaved, driver);
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].click();", guest_his.closeReservation);
			} catch (Exception e) {
				Wait.explicit_wait_visibilityof_webelement(guest_his.closeReservation, driver);
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].click();", guest_his.closeReservation);
			}
			// guest_his.closeReservation.click();
			Wait.wait5Second();
		} catch (Exception e) {
			UnsavedData(driver);

		}
		assertTrue(guest_his.newAccount.isDisplayed(), "Guest history page didn't display");

	}


	public ArrayList<String> searchAccount(WebDriver driver, String AccountFname, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.enterAccountFName, driver);
		guest_his.enterAccountFName.clear();
		guest_his.enterAccountFName.sendKeys(AccountFname);
		Wait.wait10Second();
		String AccountNumber = "";
		try {
			// guestHistoryLogger.info("GuestHistoryAccountNumber
			// :"+getGuestHistoryAccountNumber);
			FileReader fr = new FileReader(".\\guestHistoryAccountNo.txt");
			BufferedReader br = new BufferedReader(fr);

			while ((getAccountNo = br.readLine()) != null) {
				guestHistoryLogger.info(" Guest History Account Number :" + getAccountNo);

				Wait.wait10Second();
				guest_his.enterGuestHistoryAccountNo.clear();
				guest_his.enterGuestHistoryAccountNo.sendKeys(getAccountNo);
				AccountNumber = AccountNumber + getAccountNo;

			}
			br.close();
		} catch (IOException e) {
			guestHistoryLogger.info("File not found");
		}
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", guest_his.clickSearchButton);

		// guest_his.clickSearchButton.click();
		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		int count = driver.findElements(By.xpath(OR.Number_Of_Accounts)).size();

		if (count == 0) {
			test_steps.add(
					"There are No Accounts available with " + AccountFname + " and Account Number " + AccountNumber);
			Utility.app_logs.info(
					"There are No Accounts available with " + AccountFname + " and Account Number " + AccountNumber);
		} else {
			test_steps.add("Number of Accounts available with " + AccountFname + " and Account Number " + AccountNumber
					+ " in this page are " + count);
			Utility.app_logs.info("Number of Accounts available with " + AccountFname + " and Account Number "
					+ AccountNumber + " in this page are " + count);
		}
		return test_steps;
	}

	public ArrayList<String> searchAccount(WebDriver driver, String AccountFname, String AccountNumber,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		driver.navigate().refresh();

		Wait.waitUntilPresenceOfElementLocated(OR.enterAccountFName, driver);
		guest_his.enterAccountFName.clear();
		guest_his.enterAccountFName.sendKeys(AccountFname);

		guest_his.enterGuestHistoryAccountNo.clear();
		guest_his.enterGuestHistoryAccountNo.sendKeys(AccountNumber);

		guest_his.clickSearchButton.click();
		Utility.app_logs.info("Click Search");

		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
		System.out.println(
				"AccNume expec:" + AccountNumber + "Result:" + guest_his.SearcehdGuestHistoryAccountNumber.getText());
		// Assert.assertEquals(AccountNumber,
		// guest_his.SearcehdGuestHistoryAccountNumber.getText()),
		// "Failed: Searcehd Account Missmatched");

		return test_steps;
	}

	public void deleteGuestHistoryAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", guest_his.selectGuestHistoryAccount);
		jse.executeScript("arguments[0].click();", guest_his.deleteButton);
		// Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(OR.Toaster_Message)));
		// Utility.app_logs.info(driver.findElement(By.xpath(OR.Toaster_Message)).getText());
	}

	public void SelectSearchedGuestProfile(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		guest_his.ClickSearchedGuestProfile.click();
		Wait.wait2Second();

	}

	public void UnsavedData(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		guest_his.UnsavedData_YesBtn.click();
		Wait.wait2Second();

	}

	public ArrayList<String> type_AccName(WebDriver driver, ExtentTest test1, String AccountName,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		WebDriverWait wait = new WebDriverWait(driver, 90);
		// Wait.wait2Second();
		Wait.WaitForElement(driver, OR.Account_CorpAccountName);
		wait.until(ExpectedConditions.visibilityOf(CreateAccount.Account_CorpAccountName));
		CreateAccount.Account_CorpAccountName.sendKeys(AccountName);
		test_steps.add("Enter Account Name : " + AccountName);
		return test_steps;
	}

	public void OpenSearchedGuestHistroyAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		try {
			guest_his.ClickSearchedGuestProfile.click();
			assertTrue(guest_his.selectAccountSalutation_1.isDisplayed(), "Guest History Account page didn't display");
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", guest_his.ClickSearchedGuestProfile);
		}
	}

	public void OpenReservtionTab_GuestHistoryAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Elements_Reservation_SearchPage reservationSearch = new Elements_Reservation_SearchPage(driver);

		guest_his.ReservationTab_GuestAccount.click();
		
		  assertTrue(reservationSearch.advanced.isDisplayed(),
		  "Guest History Account-> Reservation Search page didn't display");
		 
	}

	public ArrayList<String> Billinginfo(WebDriver driver, String PaymentMethod, String Account, String ExpDate,
			String BillingNotes, ArrayList<String> test_steps) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Wait.wait5Second();
		if (CreateAccount.Account_Check_Mailing_info.isSelected()) {
			guestHistoryLogger.info("Check box already checked");
		} else {
			jse.executeScript("arguments[0].click();", CreateAccount.Account_Check_Mailing_info);

		}
		new Select(guest_his.GuestHistoryAccount_BillType).selectByVisibleText(PaymentMethod);
		test_steps.add("Payement Method : " + PaymentMethod);
		guest_his.GuestHistoryAccount_BillingAccountNum.sendKeys(Account);
		test_steps.add("Account number : " + Account);

		guest_his.GuestHistoryAccount_ExpDate.sendKeys(ExpDate);
		test_steps.add("Exp Date : " + ExpDate);

		guest_his.GuestHistoryAccount_BillingNotes.sendKeys(BillingNotes);
		test_steps.add("Billing Notes : " + BillingNotes);

		return test_steps;
	}

	public void ChangeGuestHistoryAccountStatus(WebDriver driver, String Status) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		new Select(guest_his.GuestHistoryAccountStatus).selectByVisibleText(Status);
		String StatusValue = new Select(guest_his.GuestHistoryAccountStatus).getFirstSelectedOption().getText();
		assertTrue(StatusValue.equals(Status), "Status didn't change");
	}

	public void OpenSearchedGuestHistroyAccount_ReservationTab(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Elements_Reservation_SearchPage reservationSearch = new Elements_Reservation_SearchPage(driver);
		guest_his.ClickSearchedGuestProfile.click();
		assertTrue(reservationSearch.advanced.isDisplayed(),
				"Guest History Account-> Reservation Search page didn't display");
	}

	public ArrayList<String> VerifyInActiveGuestAccount(WebDriver driver, String AccountFname, String AccountNumber,
			String Status, ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		driver.navigate().refresh();

		Wait.waitUntilPresenceOfElementLocated(OR.enterAccountFName, driver);
		guest_his.enterAccountFName.clear();
		guest_his.enterAccountFName.sendKeys(AccountFname);

		guest_his.enterGuestHistoryAccountNo.clear();
		guest_his.enterGuestHistoryAccountNo.sendKeys(AccountNumber);
		new Select(guest_his.SearchGuestHistoryAccountStatus).selectByVisibleText(Status);

		guest_his.clickSearchButton.click();
		Utility.app_logs.info("Click Search");

		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
		System.out.println(
				"AccNume expec:" + AccountNumber + "Result:" + guest_his.SearcehdGuestHistoryAccountNumber.getText());
		test_steps.add("Account Searched : " + AccountNumber);

		WebElement AccountStatus = driver.findElement(
				By.xpath("(//table[@class='table table-striped table-bordered table-hover']//td)[12]//span"));
		assertEquals(AccountStatus.getText(), Status, " Guest History Account Status didn't change");
		test_steps.add("Account Status : " + Status + ": Verified");

		return test_steps;
	}

	public void clickOnNewHistoryAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.NewGuestHistoryButton);
		Wait.waitForElementToBeClickable(By.xpath(OR.NewGuestHistoryButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.NewGuestHistoryButton), driver);
		guest_his.NewGuestHistoryButton.click();
		Wait.explicit_wait_absenceofelement(OR.Account_ModuleLoading, driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR.guestHistoryAccountPage), driver);
		} catch (Exception e) {
			Wait.waitForElementToBeVisibile(By.xpath(OR.GuestHistoryAccountPage), driver);

		}

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <searchAccount> ' Description: <This method will return
	 * expected deposit amount> ' Input parameters: <WebDriver driver, String
	 * accountFirstName, String accountLastName> ' Return value: <ArrayList<String>>
	 * ' Created By: <Farhan Ghaffar> ' Created On: <06/16/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> searchAccount(WebDriver driver, String accountFirstName, String accountLastName)
			throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.WaitForElement(driver, OR.guestAccountFirstName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountFirstName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountFirstName), driver);
		guest_his.guestAccountFirstName.clear();
		guest_his.guestAccountFirstName.sendKeys(accountFirstName);
		testSteps.add("Entered Guest First Name : " + accountFirstName);
		guestHistoryLogger.info("Entered Guest First Name : " + accountFirstName);

		Wait.WaitForElement(driver, OR.guestAccountLastName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountLastName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountLastName), driver);
		guest_his.guestAccountLastName.clear();
		guest_his.guestAccountLastName.sendKeys(accountLastName);
		testSteps.add("Entered Guest Last Name : " + accountLastName);
		guestHistoryLogger.info("Entered Guest Last Name : " + accountLastName);

		try {
		Wait.WaitForElement(driver, OR.clickSearchButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton), driver);
		guest_his.clickSearchButton.click();
		guestHistoryLogger.info("Click Search");
		testSteps.add("Click Search");
		}catch(Exception e) {
			Wait.WaitForElement(driver, OR.clickSearchButton1);
			Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton1), driver);
			guest_his.clickSearchButton1.click();
			guestHistoryLogger.info("Click Search");
			testSteps.add("Click Search");
		}
		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
		System.out.println("AccNume expec:" + accountFirstName + "Result:"
				+ guest_his.SearcehdGuestHistoryAccountNumber.getText());

		return testSteps;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <updateCardNumber> ' Description: <This method will update
	 * credit card number in guest profile> ' Input parameters: <WebDriver driver,
	 * String newCCNumber> ' Return value: <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <06/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> updateCardNumber(WebDriver driver, String newCCNumber) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.WaitForElement(driver, OR.clickDecyptCCNumberImg);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickDecyptCCNumberImg), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickDecyptCCNumberImg), driver);
		Utility.ScrollToElement_NoWait(guest_his.clickDecyptCCNumberImg, driver);
		guest_his.clickDecyptCCNumberImg.click();
		guestHistoryLogger.info("Click On Decrypt Icon");
		testSteps.add("Click On Decrypt Icon");

		Wait.WaitForElement(driver, OR.billingAccountNumberInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.billingAccountNumberInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.billingAccountNumberInput), driver);
		guest_his.billingAccountNumberInput.clear();
		guest_his.billingAccountNumberInput.sendKeys(newCCNumber);
		testSteps.add("Entered CreditCard Number : " + newCCNumber);
		guestHistoryLogger.info("Entered CreditCard Number : " + newCCNumber);

		return testSteps;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <clickSave> ' Description: <This method will click on save
	 * button in guest profile> ' Input parameters: <WebDriver driver> ' Return
	 * value: <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On:
	 * <06/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> clickSave(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.guestInfoSaveButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestInfoSaveButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestInfoSaveButton), driver);
		guest_his.guestInfoSaveButton.click();
		guestHistoryLogger.info("Click Save");
		testSteps.add("Click Save");
		return testSteps;
	}
	
	

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <getAccountNumber> ' Description: <This method will return
	 * account number off searched pofile in guest history> ' Input parameters:
	 * <WebDriver driver> ' Return value: <String> ' Created By: <Farhan Ghaffar> '
	 * Created On: <06/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public String getAccountNumber(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
		String accountNumber = guest_his.SearcehdGuestHistoryAccountNumber.getText();
		return accountNumber;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <openSearchedGuestAccount> ' Description: <This method will
	 * click searched account to open details in guest history> ' Input parameters:
	 * <WebDriver driver> ' Return value: <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <06/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public ArrayList<String> openSearchedGuestAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.ClickSearchedGuestProfile);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ClickSearchedGuestProfile), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ClickSearchedGuestProfile), driver);
		guest_his.ClickSearchedGuestProfile.click();
		guestHistoryLogger.info("Click on searched guest profile");
		testSteps.add("Click on searched guest profile");

		return testSteps;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <openReservtionTabInGuestHistoryAccount> ' Description: <This
	 * method will click reservation tab in guest profile> ' Input parameters:
	 * <WebDriver driver> ' Return value: <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <06/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> openReservtionTabInGuestHistoryAccount(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.reservationTabGuestAccount);
		Wait.waitForElementToBeVisibile(By.xpath(OR.reservationTabGuestAccount), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.reservationTabGuestAccount), driver);
		guest_his.reservationTabGuestAccount.click();
		guestHistoryLogger.info("Click on reservation tab");
		testSteps.add("Click on reservation tab");

		return testSteps;

	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <verifyCardNumber> ' Description: <This method will veirfy
	 * updated credit card number in guest profile> ' Input parameters: <WebDriver
	 * driver, String newCCNumber> ' Return value: <ArrayList<String>> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <06/17/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */

	public ArrayList<String> verifyCardNumber(WebDriver driver, String newCCNumber) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.WaitForElement(driver, OR.clickDecyptCCNumberImg);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickDecyptCCNumberImg), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickDecyptCCNumberImg), driver);
		Utility.ScrollToElement_NoWait(guest_his.clickDecyptCCNumberImg, driver);
		guest_his.clickDecyptCCNumberImg.click();
		guestHistoryLogger.info("Click On Decrypt Icon");
		testSteps.add("Click On Decrypt Icon");

		Wait.WaitForElement(driver, OR.billingAccountNumberInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.billingAccountNumberInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.billingAccountNumberInput), driver);
		String expectedcardNumber = "XXXX" + newCCNumber.substring(newCCNumber.length() - 4);
		String guetsCardNumber = guest_his.billingAccountNumberInput.getAttribute("value");

		guestHistoryLogger.info("Expected CreditCard Number : " + expectedcardNumber);
		guestHistoryLogger.info("Found : " + guetsCardNumber);

		if (guetsCardNumber.contains("X")) {
			assertEquals(guetsCardNumber, expectedcardNumber, "Failed : Card Number mismatches");
			testSteps.add("Expected CreditCard Number : " + expectedcardNumber);
			testSteps.add("Found : " + guetsCardNumber);

		} else {
			assertEquals(guetsCardNumber, newCCNumber, "Failed : Card Number mismatches");
			testSteps.add("Expected CreditCard Number : " + newCCNumber);
			testSteps.add("Found : " + guetsCardNumber);
		}

		testSteps.add("verified card number");
		guestHistoryLogger.info("verified card number");

		return testSteps;
	}

	// Added By Adhnan 6/22/2020
	public void clickNewReservation(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.newReservationButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.newReservationButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.newReservationButton), driver);
		guest_his.newReservationButton.click();
	}

	public void enterFirstLastName(WebDriver driver, String FirstName, String LastName, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.wait2Second();
		Wait.WaitForElement(driver, OR.GUEST_ACCOUNT_FIRST_NAME);
		Wait.waitForElementToBeVisibile(By.xpath(OR.GUEST_ACCOUNT_FIRST_NAME), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.GUEST_ACCOUNT_FIRST_NAME), driver);
		guest_his.GUEST_ACCOUNT_FIRST_NAME.sendKeys(FirstName);
		guestHistoryLogger.info("First Name Enterted: " + FirstName);
		test_steps.add("First Name Enterted: " + FirstName);
		guest_his.GUEST_ACCOUNT_LAST_NAME.sendKeys(LastName);
		guestHistoryLogger.info("Last Name Enterted: " + LastName);
		test_steps.add("Last Name Enterted: " + LastName);

	}

	
	public void searchWithLastName(WebDriver driver, String accountLastName, ArrayList<String> testSteps) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.guestAccountLastName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountLastName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountLastName), driver);
		guest_his.guestAccountLastName.clear();
		guest_his.guestAccountLastName.sendKeys(accountLastName);
		testSteps.add("Entered Guest Last Name : " + accountLastName);
		guestHistoryLogger.info("Entered Guest Last Name : " + accountLastName);

		try {
		Wait.WaitForElement(driver, OR.clickSearchButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton), driver);
		guest_his.clickSearchButton.click();
		guestHistoryLogger.info("Click Search");
		testSteps.add("Click Search");
		}catch(Exception e) {
			Wait.WaitForElement(driver, OR.clickSearchButton1);
			Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton1), driver);
			guest_his.clickSearchButton1.click();
			guestHistoryLogger.info("Click Search");
			testSteps.add("Click Search");
		}

		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);

	}
	public void clickClear(WebDriver driver) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		try {
			try {
				guest_his.ClickClearOnGuestHistory.click();
			} catch (Exception e) {
				driver.findElement(By.xpath("(//button[text()='Clear'])[1]")).click();
			}
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", guest_his.ClickClearOnGuestHistory);
		}
	}

	public void clickAddNotes(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.ClickAddNoteButton);
		Utility.ScrollToElement(guest_his.clickAddNoteButton, driver);
		guest_his.clickAddNoteButton.click();
		test_steps.add("Add note Button clicked");
	}

	public void enterNoteDetails(WebDriver driver, String subject, String details, String noteType,
			ArrayList<String> test_steps) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.waitUntilPresenceOfElementLocated(driver, OR.NoteSubject);
		guest_his.noteSubject.sendKeys(subject);
		test_steps.add("Enter Note Subject");
		guest_his.notedetails.sendKeys(details);
		test_steps.add("Enter Note Details");
		new Select(guest_his.noteType).selectByVisibleText(noteType);
		test_steps.add("Select NoteType");


	}
	public void clickNoteSaveButton(WebDriver driver, ArrayList<String>test_steps)  {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.waitUntilPresenceOfElementLocated(driver,OR.ClickSaveNote);
		guest_his.clickSaveNote.click();
		
		test_steps.add("Note added in Guest History");
	}

	public void verifyMailingInformation(WebDriver driver, String Address1, String Address2, String Address3,
			String City, String Country, String State, String PostalCode, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_CPReservation cpelements = new Elements_CPReservation(driver);
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		// ArrayList<String> test_steps = new ArrayList<String>();
		Wait.waitUntilPresenceOfElementLocated(OR.GetAddress1, driver);

		Wait.waitForElementToBeVisibile(By.xpath(OR.GetAddress1), driver);
		System.out.println(Address1);
		assertEquals(Address1.trim(), guest_his.getAddress1.getAttribute("value"));

		guestHistoryLogger.info("Mailing Info Address1: " + Address1 + " :Verified");
		test_steps.add("Mailing Info Address1: " + Address1 + " :Verified");
		assertEquals(Address2, guest_his.getAddress2.getAttribute("value"));
		guestHistoryLogger.info("Mailing Info Address2: " + Address2 + " :Verified");
		test_steps.add("Mailing Info Address2: " + Address2 + " :Verified");
		assertEquals(Address3, guest_his.getAddress3.getAttribute("value"));
		guestHistoryLogger.info("Mailing Info Address3: " + Address3 + " :Verified");
		test_steps.add("Mailing Info Address3: " + Address3 + " :Verified");
		assertEquals(City, guest_his.getCity.getAttribute("value"));
		guestHistoryLogger.info("Mailing Info City: " + City + " :Verified");
		test_steps.add("Mailing Info City: " + City + " :Verified");
		String getCountry = new Select(guest_his.getCountry).getFirstSelectedOption().getText();
		assertEquals(Country, getCountry);
		guestHistoryLogger.info("Mailing Info Country: " + Country + " :Verified");
		test_steps.add("Mailing Info Country: " + Country + " :Verified");
		String getState = new Select(guest_his.getState).getFirstSelectedOption().getText();
		assertEquals(State, getState);
		guestHistoryLogger.info("Mailing Info State: " + State + " :Verified");
		test_steps.add("Mailing Info State: " + State + " :Verified");
		assertEquals(PostalCode, guest_his.getPostalCode.getAttribute("value"));
		guestHistoryLogger.info("Mailing Info PostalCode: " + PostalCode + " :Verified");
		test_steps.add("Mailing Info PostalCode: " + PostalCode + " :Verified");

	}

	public void verifyGuestHistoryContactInfo(WebDriver driver, String guestfirstName, String guestLastName,
			String contactFirstName, String contactLastName, String email, String phone, String alternatePhone,
			ArrayList<String> test_steps) {
		// Elements_CPReservation res = new Elements_CPReservation(driver);
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getAccountFirstName, driver);
		String foundGuestFirstName = guest_his.getAccountFirstName.getAttribute("value").toString();
		Assert.assertEquals(foundGuestFirstName, guestfirstName, "failed to verify");
		guestHistoryLogger.info("Verify Account first Name: " + foundGuestFirstName);
		test_steps.add("Verify Account first Name: " + foundGuestFirstName);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getAccountLastname, driver);
		String foundGuestLastName = guest_his.getAccountLastname.getAttribute("value").toString();
		Assert.assertEquals(foundGuestLastName, guestLastName, "failed to verify");
		guestHistoryLogger.info("Verify Account last Name: " + foundGuestLastName);
		test_steps.add("Verify Account last Name: " + foundGuestLastName);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getGuestFirstName, driver);
		String foundContactFirstName = guest_his.getGuestFirstName.getAttribute("value").toString();
		Assert.assertEquals(foundContactFirstName, contactFirstName, "failed to verify");
		guestHistoryLogger.info("Verify guest first name: " + foundContactFirstName);
		test_steps.add("Verify contact guest name: " + foundContactFirstName);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getGuestLastName, driver);
		String foundContactLastName = guest_his.getGuestLastName.getAttribute("value").toString();
		Assert.assertEquals(foundContactLastName, contactLastName, "failed to verify");
		guestHistoryLogger.info("Verify guest last name:  " + foundContactLastName);
		test_steps.add("Verify guest last name:  " + foundContactLastName);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getEmail, driver);
		String foundEmail = guest_his.getEmail.getAttribute("value").toString();
		Assert.assertEquals(foundEmail, email, "failed to verify");
		guestHistoryLogger.info("Verify guest email: " + foundEmail);
		test_steps.add("Verify guest email: " + foundEmail);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getPhone, driver);
		String foundPhone = guest_his.getPhone.getAttribute("value").toString();
		Assert.assertEquals(Utility.removeExceptDigit(foundPhone), phone, "failed to verify");
		guestHistoryLogger.info("Verify guest phone: " + foundPhone);
		test_steps.add("Verify guest phone: " + foundPhone);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getAlternatePhone, driver);
		String foundAlternamePhone = guest_his.getAlternatePhone.getAttribute("value").toString();
		Assert.assertEquals(Utility.removeExceptDigit(foundAlternamePhone), alternatePhone, "failed to verify");
		guestHistoryLogger.info("Verify guest alternate phone: " + foundAlternamePhone);
		test_steps.add("Verify guest alternate phone: " + foundAlternamePhone);

	}

	public void verifyPaymentDetailInGuestHistoryAccount(WebDriver driver, String paymentMethod, String cardNo,
			String nameOnCard, String expiry, ArrayList<String> test_steps) {
		// 'ArrayList<String> test_steps = new ArrayList<String>();
		// Elements_CPReservation res = new Elements_CPReservation(driver);
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getPaymentMethod, driver);
		String foundPayMethod = new Select(guest_his.getPaymentMethod).getFirstSelectedOption().getText();
		// String foundPayMethod =
		// guest_his.getPaymentMethod.getAttribute("title").toString();
		assertEquals(foundPayMethod, paymentMethod, "Failed To Verify Payment Method");
		guestHistoryLogger.info("Successfully Verified Payment Method : " + foundPayMethod);
		test_steps.add("Successfully Verified Payment Method : " + foundPayMethod);

		try {
			guest_his.clickDescriptor.click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", guest_his.clickDescriptor);
		}

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getCardNumber, driver);

		String foundCardNo = guest_his.getCardNumber.getAttribute("value").toString();
		assertEquals(foundCardNo.substring(foundCardNo.length() - 4).trim(),
				cardNo.substring(cardNo.length() - 4).trim(), "Failed To Verify CardNo");
		guestHistoryLogger.info("Successfully Verified CardNo : " + foundCardNo);
		test_steps.add("Successfully Verified CardNo : " + foundCardNo);

		Wait.explicit_wait_visibilityof_webelement_120(guest_his.getExpDate, driver);
		String foundExpiry = guest_his.getExpDate.getAttribute("value").toString();
		assertEquals(foundExpiry, expiry, "Failed To Verify expiry");
		guestHistoryLogger.info("Successfully Verified expiry : " + foundExpiry);
		test_steps.add("Successfully Verified expiry : " + foundExpiry);
	}

	public boolean useMailingAddressCheckBox(WebDriver driver) {
		// Elements_CPReservation res = new Elements_CPReservation(driver);
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		boolean isChecked = false;
		if (guest_his.useMailingAddressCheck.isSelected()) {
			isChecked = true;
		}
		return isChecked;

	}

	public void verifyMarketingInfo(WebDriver driver, String Market, String Referral, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		String getMarket = new Select(guest_his.getMarketing).getFirstSelectedOption().getText();
		assertEquals(Market, getMarket, "Failed to verify");
		guestHistoryLogger.info("Marketing Info Market: " + Market + " :Verified");
		test_steps.add("Marketing Info Market: " + Market + " :Verified");

		String getReferral = new Select(guest_his.getReferral).getFirstSelectedOption().getText();
		guestHistoryLogger.info("Valueee Referral:" + getReferral);

		assertEquals(Referral, getReferral, "Failed to verify");
		guestHistoryLogger.info("Marketing Info Referral: " + Referral + " :Verified");
		test_steps.add("Marketing Info Referral: " + Referral + " :Verified");

	}

	public boolean isGuestHistoryAccountExist(WebDriver driver, String accountFirstName, ArrayList<String> test_steps) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		boolean isAccountPresent = false;
		Wait.WaitForElement(driver, OR.guestAccountFirstName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountFirstName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountFirstName), driver);
		guest_his.guestAccountFirstName.clear();
		guest_his.guestAccountFirstName.sendKeys(accountFirstName);
		test_steps.add("Entered Guest First Name : " + accountFirstName);
		guestHistoryLogger.info("Entered Guest First Name : " + accountFirstName);
		Wait.WaitForElement(driver, OR.clickSearchButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton), driver);
		guest_his.clickSearchButton.click();
		guestHistoryLogger.info("Click Search");
		test_steps.add("Click Search");
		isAccountPresent = guestHistorySearchResult(driver, test_steps);
		return isAccountPresent;

	}

	public boolean guestHistorySearchResult(WebDriver driver, ArrayList<String> test_steps) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		boolean searchResult = false;
		try {
			if (guest_his.numberOfAccounts.size() > 0) {
				searchResult = true;
			}
		} catch (Exception e) {
			test_steps.add("No Records exist against the search");
		}
		return searchResult;

	}

	public void useMailingInforMation(WebDriver driver, String isChecked) {

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (CreateAccount.Account_Check_Mailing_info.isSelected() && isChecked.equalsIgnoreCase("Yes")) {
			guestHistoryLogger.info("Check box already checked");
		} else if (CreateAccount.Account_Check_Mailing_info.isSelected() && !isChecked.equalsIgnoreCase("Yes")) {
			jse.executeScript("arguments[0].click();", CreateAccount.Account_Check_Mailing_info);

		} else if (!CreateAccount.Account_Check_Mailing_info.isSelected() && isChecked.equalsIgnoreCase("Yes")) {
			jse.executeScript("arguments[0].click();", CreateAccount.Account_Check_Mailing_info);
		} else if (!CreateAccount.Account_Check_Mailing_info.isSelected() && !isChecked.equalsIgnoreCase("Yes")) {

		}
	}

	public void billingFirstAndLastName(WebDriver driver, String salutation, String firstName, String lastName,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.wait2Second();
		Wait.WaitForElement(driver, OR.SelectBullingSalutation);
		new Select(guest_his.selectBullingSalutation).selectByValue(salutation);
		try {
			Wait.WaitForElement(driver, OR.EnterBillingFirstName);
			Wait.waitForElementToBeVisibile(By.xpath(OR.EnterBillingFirstName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.EnterBillingFirstName), driver);
			guest_his.enterBillingFirstName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterBillingFirstName.sendKeys(Keys.DELETE);
			guest_his.enterBillingFirstName.sendKeys(firstName);
		} catch (Exception e) {

			Wait.WaitForElement(driver, OR.EnterBillingFirstName);
			Wait.waitForElementToBeVisibile(By.xpath(OR.EnterBillingFirstName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.EnterBillingFirstName), driver);
			guest_his.enterBillingFirstName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterBillingFirstName.sendKeys(Keys.DELETE);
			guest_his.enterBillingFirstName.sendKeys(firstName);
		}
		guestHistoryLogger.info("First Name Enterted: " + firstName);
		test_steps.add("First Name Enterted: " + firstName);
		try {
			guest_his.enterBillingLastName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterBillingLastName.sendKeys(Keys.DELETE);
			guest_his.enterBillingLastName.sendKeys(lastName);
		} catch (Exception e) {
			guest_his.enterBillingLastName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterBillingLastName.sendKeys(Keys.DELETE);
			guest_his.enterBillingLastName.sendKeys(lastName);
		}
		guestHistoryLogger.info("Last Name Enterted: " + lastName);
		test_steps.add("Last Name Enterted: " + lastName);

	}

	public void verifyBillingFirstAndLastName(WebDriver driver,String billingFirstName, String billingLastName,ArrayList<String>test_steps) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		//Elements_CPReservation res = new Elements_CPReservation(driver);
		String firstName = guest_his.getBillingFirstName.getAttribute("value").toString();
		Assert.assertEquals(firstName, billingFirstName, "Failed to verify");
		test_steps.add("Verify billing first name: " + billingFirstName);

		String lastName = guest_his.getBillingLastName.getAttribute("value").toString();
		Assert.assertEquals(lastName, billingLastName, "Failed to verify");
		test_steps.add("Verify billing last name: " + billingLastName);
	}
	public void searchAccountWithCombine(WebDriver driver, String accountFirstName, String accountLastName, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		
		String guestAccountFirstName = "(//input[contains(@data-bind,'value: AccountFirstName')])[1]";
		Wait.WaitForElement(driver, guestAccountFirstName);
		try {
		Wait.waitForElementToBeVisibile(By.xpath(guestAccountFirstName), driver,5);
		}catch (Exception e) {
			try {
			guestAccountFirstName = "(//input[contains(@data-bind,'value: AccountFirstName')])[2]";
			Wait.waitForElementToBeVisibile(By.xpath(guestAccountFirstName), driver,5);
			}catch (Exception e1) {
				try {
				guestAccountFirstName = "(//input[contains(@data-bind,'value: AccountFirstName')])[3]";
				Wait.waitForElementToBeVisibile(By.xpath(guestAccountFirstName), driver,5);
				}catch (Exception e2) {
					guestAccountFirstName = "(//input[contains(@data-bind,'value: AccountFirstName')])[4]";
					Wait.waitForElementToBeVisibile(By.xpath(guestAccountFirstName), driver,5);
				}
			}
		}
		Wait.waitForElementToBeClickable(By.xpath(guestAccountFirstName), driver);
		driver.findElement(By.xpath(guestAccountFirstName)).clear();
		driver.findElement(By.xpath(guestAccountFirstName)).sendKeys(accountFirstName);
		testSteps.add("Entered Guest First Name : " + accountFirstName);
		guestHistoryLogger.info("Entered Guest First Name : " + accountFirstName);

		
		String guestAccountLastName = "(//input[contains(@data-bind,'value: AccountLastName')])[1]";
		Wait.WaitForElement(driver, guestAccountLastName);
		try {
		Wait.waitForElementToBeVisibile(By.xpath(guestAccountLastName), driver,5);
		}catch (Exception e) {
			guestAccountLastName = "(//input[contains(@data-bind,'value: AccountLastName')])[2]";
			Wait.waitForElementToBeVisibile(By.xpath(guestAccountLastName), driver,5);
		}
		Wait.waitForElementToBeClickable(By.xpath(guestAccountLastName), driver);
		driver.findElement(By.xpath(guestAccountLastName)).clear();
		driver.findElement(By.xpath(guestAccountLastName)).sendKeys(accountLastName);
		testSteps.add("Entered Guest Last Name : " + accountLastName);
		guestHistoryLogger.info("Entered Guest Last Name : " + accountLastName);

		try {
			if(guest_his.clickCombine.isSelected()) {
				test_steps.add("Combine checkbox already selected");
			}else {
				guest_his.clickCombine.click();
				test_steps.add("Click on Combine checkbox");
			}
		} catch(Exception e) {
			if(guest_his.clickCombine1.isSelected()) {
				test_steps.add("Combine checkbox already selected");
			}else {
				guest_his.clickCombine1.click();
				test_steps.add("Click on Combine checkbox");
			}
	}
		
		try {
		Wait.WaitForElement(driver, OR.clickSearchButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton), driver);
		guest_his.clickSearchButton.click();

		guestHistoryLogger.info("Click Search");
		testSteps.add("Click Search");
		}catch(Exception e) {
			Wait.WaitForElement(driver, OR.clickSearchButton1);
			Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton1), driver);
			guest_his.clickSearchButton1.click();
			guestHistoryLogger.info("Click Search");
			testSteps.add("Click Search");
		}


		
		  Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		  Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
			/*
			 * System.out.println("AccNume expec:" + accountFirstName + "Result:" +
			 * guest_his.SearcehdGuestHistoryAccountNumber.getText());
			 */
		 
	}
public void verifyReservationExistInSearch(WebDriver driver, String confirmationNum, boolean expected, ArrayList<String>test_steps) {
	Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	 String confirmation = "//tbody[@data-bind='foreach: ReservationList']/following::td/span[text()='"+confirmationNum+"']";
	boolean isRecord = false;
	 try {
		 if(driver.findElement(By.xpath(confirmation)).isDisplayed()) {
			 test_steps.add("Sucess - Reservation Exist in account" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/REF-236'>" + "Click here to open JIRA: REF-236</a>") ;
			 test_steps.add("Reservation record displayed :"+ confirmationNum) ;
			 isRecord = true;
		 }
	 } catch(Exception e) {
		 test_steps.add("Reservation record not displayed: "+ confirmationNum) ;
	 }
  
	
}
public void verifyGuestHistoryAccount(WebDriver driver,String guestFirstName, String guestLastName,String guestContactFirstName,String guestLastContactName,String email,String phonenumber,String alternativeNumber,String address,
		String city,String country,String state, String postalcode, String paymentMethod, String cardNumber, String guestName, String expDate,
		String billingFirstName, String billingLastName, String marketSegment, String referral,String useMailingInfo, ArrayList<String>test_steps) throws InterruptedException {
	test_steps.add("========= VERIFYING MAILING INFORMATION IN GUEST HISTORY ==========");
	verifyMailingInformation(driver, address, address, address, city, country, state,
			postalcode, test_steps);

	verifyGuestHistoryContactInfo(driver, guestFirstName, guestLastName, guestContactFirstName,
			guestLastContactName, email, phonenumber, alternativeNumber, test_steps);

	test_steps.add("========= VERIFYING PAYMENT DETAILS IN BILLING IN GUEST HISTORY ==========");
	verifyPaymentDetailInGuestHistoryAccount(driver, paymentMethod, cardNumber, guestName,
			expDate, test_steps);

	if (!useMailingInfo.equalsIgnoreCase("Yes")) {
		test_steps.add("========= VERIFYING USE MAILING CHECKBOX IN HISTORY==========");
		boolean isChecked = useMailingAddressCheckBox(driver);
		Assert.assertEquals(isChecked, false, "Failed to verify");
		test_steps.add("Use Mailing checkbox unchecked");
		test_steps.add("========= VERIFYING BILLING FIRST AND LAST NAME==========");
		verifyBillingFirstAndLastName(driver, billingFirstName, billingLastName,
				test_steps);
		verifyBillingInfo(driver, email, phonenumber, address, address,address,
				city, state,postalcode, country, test_steps);
	} else {
		test_steps.add("========= VERIFYING USE MAILING CHECKBOX IN HISTORY==========");
		boolean isChecked = useMailingAddressCheckBox(driver);
		Assert.assertEquals(isChecked, true, "Failed to verify");
		test_steps.add("Use Mailing checkbox checked");
	}

	test_steps.add("========= VERIFYING MARKETING INFORMATION IN GUEST HISTORY ==========");
	verifyMarketingInfo(driver, marketSegment, referral, test_steps);
	clickReset(driver);
	
}

public void clickReset(WebDriver driver) {
	Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	Wait.waitForElementToBeClickable(By.xpath(OR.ClickReset), driver);
	guest_his.clickReset.click();
}
public void verifyBillingInfo(WebDriver driver, String email, String phone, String address1, String address2,String address3,
		String city, String state, String postalCode, String country, ArrayList<String>test_steps) {
	Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	Wait.WaitForElement(driver, OR.GetBillingEmail);
	String foundEmail = guest_his.getBillingEmail.getAttribute("value").toString();
	Assert.assertEquals(foundEmail, email);
	guestHistoryLogger.info("Verify billing email: "+ email);
	test_steps.add("Verify billing email: "+ email);

	
	Wait.WaitForElement(driver, OR.GetBillingPostal);
	String foundpostalCode = guest_his.getBillingPostal.getAttribute("value").toString();
	Assert.assertEquals(foundpostalCode, postalCode);
	guestHistoryLogger.info("Verify billing foundpostalCode: "+ foundpostalCode);
	test_steps.add("Verify billing foundpostalCode: "+ foundpostalCode);
	
	

	Wait.WaitForElement(driver, OR.GetBillingAddress1);
	String foundAddress1 = guest_his.getBillingAddress1.getAttribute("value").toString();
	Assert.assertEquals(foundAddress1, address1);
	guestHistoryLogger.info("Verify billing Address1: "+ address1);
	test_steps.add("Verify billing Address1: "+ address1);
	
	
	Wait.WaitForElement(driver, OR.GetBilligAddress2);
	String foundAddress2 = guest_his.getBilligAddress2.getAttribute("value").toString();
	Assert.assertEquals(foundAddress2, address2);
	guestHistoryLogger.info("Verify billing address2: "+ address1);
	test_steps.add("Verify billing address2: "+ address2);
	
	Wait.WaitForElement(driver, OR.GetBillingAddress3);
	String foundAddress3 = guest_his.getBillingAddress3.getAttribute("value").toString();
	Assert.assertEquals(foundAddress3, address3);
	guestHistoryLogger.info("Verify billing address3: "+ address3);
	test_steps.add("Verify billing address3: "+ address3);
	
	Wait.WaitForElement(driver, OR.GetBillingCity);
	String foundCity = guest_his.getBillingCity.getAttribute("value").toString();
	Assert.assertEquals(foundCity, city);
	guestHistoryLogger.info("Verify billing city: "+ city);
	test_steps.add("Verify billing city: "+ city);
	
	Wait.WaitForElement(driver, OR.GetBillingState);
	String foundState = new Select(guest_his.getBillingState).getFirstSelectedOption().getText();
	Assert.assertEquals(foundState, state);
	guestHistoryLogger.info("Verify billing state: "+ state);
	test_steps.add("Verify billing state: "+ state);
	
	Wait.WaitForElement(driver, OR.GetBillingCountry);
	String foundcountry = new Select(guest_his.getBillingCountry).getFirstSelectedOption().getText();
	Assert.assertEquals(foundcountry, country);
	guestHistoryLogger.info("Verify billing country: "+ country);
	test_steps.add("Verify billing country: "+ country);
	
	
	Wait.WaitForElement(driver, OR.GetBillingPhone);
	String foundphone = guest_his.getBillingPhone.getAttribute("value").toString();
	try {
		Assert.assertEquals(foundphone, phone, 	"FAIL - Verify billing phone" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-10598'>"
				+ "Click here to open JIRA: NG-10598</a>");
		guestHistoryLogger.info("Verify billing phone: "+ phone);
		test_steps.add("Verify billing phone: "+ phone);
	} catch (Exception e) {
         test_steps.add("FAIL - Verify billing phone" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-10598'>"
				+ "Click here to open JIRA: NG-10598</a>"+e.toString());
	}catch (Error e) {
		test_steps.add("FAIL - Verify billing phone" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-10598'>"
				+ "Click here to open JIRA: NG-10598</a>"+e.toString());
	}
	
}
public void verifyRoomAndReservation(WebDriver driver, String roomCount, String resCount, ArrayList<String>test_steps) {
	Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	Wait.WaitForElement(driver, OR.RoomCount);
	String foundRoomCount = guest_his.roomCount.getText();
	
	try {
		Assert.assertEquals(foundRoomCount, roomCount, 	"FAIL - missmatch with the room count" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-4838'>"
				+ "Click here to open JIRA: NG-4838</a>");
		test_steps.add("Verify room count: "+ roomCount);
	} catch (Exception e) {
      test_steps.add("FAIL - missmatch with the room count" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-4838'>"
				+ "Click here to open JIRA: NG-4838</a>"+e.toString());
	} catch(Error e) {
		 test_steps.add("FAIL - missmatch with the room count" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/NG-4838'>"
					+ "Click here to open JIRA: NG-4838</a>"+e.toString());
	}
	
	Wait.WaitForElement(driver, OR.ReservationCount);
	String foundResCount = guest_his.reservationCount.getText();
	try {
		Assert.assertEquals(foundResCount, resCount, "Missmatch with reservation count" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-3319'>"
				+ "Click here to open JIRA: NG-3319</a>");
		test_steps.add("Verify reservation count: "+ resCount);
	} catch (Exception e) {
		 test_steps.add("FAIL - Missmatch with reservation count" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/NG-4838'>"
					+ "Click here to open JIRA: NG-4838</a>"+e.toString());
	} catch(Error e) {
		 test_steps.add("FAIL - Missmatch with reservation count" + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/NG-4838'>"
					+ "Click here to open JIRA: NG-4838</a>"+e.toString());
	}
	
	
}
public void verifyNoteUpdatedOn(WebDriver driver, String timeZone, ArrayList<String>test_steps) {
	Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	//String parsetimezone = "(GMT-05:00) Eastern Time (US and Canada)";
	String getTimeZone = timeZone.substring(1, 10);	
	System.out.println(getTimeZone);
	SimpleDateFormat isoFormat = new SimpleDateFormat("d-MMM-yy ( h:mm a )");
	 isoFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone));
	
	System.out.println(isoFormat.format(new Date()));
	String currentDayAndTime = isoFormat.format(new Date());
	System.out.println("currentDayAndTime"+ currentDayAndTime);

	/*js = (JavascriptExecutor) driver;
	String updatedOn = (String) js.executeScript("return arguments[0].value",
			guest_his.getUpdatedOn);*/
	Wait.WaitForElement(driver, OR.GetUpdatedOn);
	
	String updatedOn =  guest_his.getUpdatedOn.getText();
	//String updatedOn =  guest_his.getUpdatedOn.getAttribute("value").toString();
	
	System.out.println("updatedOn"+ updatedOn);
	//Assert.assertEquals(updatedOn, currentDayAndTime, "Failed to verify");
	if (currentDayAndTime.equalsIgnoreCase(updatedOn)) {
		//test_steps.add("Updated on date validated with property time zone date : " + currentDayAndTime);
		guestHistoryLogger.info("Updated on date validated with property time zone date : " + currentDayAndTime);
		test_steps.add("Sucess - Updated on date validated with property time zone date" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2233'>" + "Click here to open JIRA: NG-2233</a>");
	}
}

public boolean verifyGuestHistoryTabSelected(WebDriver driver, ArrayList<String>test_steps) {
	Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
 boolean flag = true;
	try {
	
     Wait.waitForToasterToBeVisibile(By.xpath(OR.GuestHistorySelected), driver);
     test_steps.add("Verify GUEST HISTORY tab selected");
     try {
		Assert.assertEquals(true, flag, "FAIL - Verify GUEST HISTORY tab selected" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2436'>"
				+ "Click here to open JIRA: NG-2436</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2398'>"
				+ "Click here to open JIRA: NG-2398</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2306'>"
				+ "Click here to open JIRA: NG-2306</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2399'>"
				+ "Click here to open JIRA: NG-2399</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2307'>"
				+ "Click here to open JIRA: NG-2307</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2343'>"
				+ "Click here to open JIRA: NG-2343</a>");
	} catch (Exception e) {
		test_steps.add( "FAIL - Verify GUEST HISTORY tab selected" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2436'>"
				+ "Click here to open JIRA: NG-2436</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2398'>"
				+ "Click here to open JIRA: NG-2398</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2306'>"
				+ "Click here to open JIRA: NG-2306</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2399'>"
				+ "Click here to open JIRA: NG-2399</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2307'>"
				+ "Click here to open JIRA: NG-2307</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2343'>"
				+ "Click here to open JIRA: NG-2343</a>" + e.toString());
	}
		
	} catch (Exception e) {
		
		flag = false;
		test_steps.add( "FAIL - Verify GUEST HISTORY tab selected" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2436'>"
				+ "Click here to open JIRA: NG-2436</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2398'>"
				+ "Click here to open JIRA: NG-2398</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2306'>"
				+ "Click here to open JIRA: NG-2306</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2399'>"
				+ "Click here to open JIRA: NG-2399</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2307'>"
				+ "Click here to open JIRA: NG-2307</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2343'>"
				+ "Click here to open JIRA: NG-2343</a>" + e.toString());
		} catch (Error  e) {
		
		flag = false;
		test_steps.add( "FAIL - Verify GUEST HISTORY tab selected" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2436'>"
				+ "Click here to open JIRA: NG-2436</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2398'>"
				+ "Click here to open JIRA: NG-2398</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2306'>"
				+ "Click here to open JIRA: NG-2306</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2399'>"
				+ "Click here to open JIRA: NG-2399</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2307'>"
				+ "Click here to open JIRA: NG-2307</a>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-2343'>"
				+ "Click here to open JIRA: NG-2343</a>" + e.toString());
	}
	return flag;
	
}
	public void clickGuestHistoryNewAccount(WebDriver driver) throws InterruptedException {
		guestHistory_NewAccount(driver);
	}
	
	public String getAccountNumberFromDetailsPage(WebDriver driver) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.getAccountOnDetailsPage);
		String account = guest_his.GetAccountOnDetailsPage.getAttribute("value").toString();
		return account;
	}
	public void billinginfo(WebDriver driver, String PaymentMethod, String Account, String ExpDate, String BillingNotes,
			ArrayList<String> test_steps) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Wait.wait5Second();
		if (CreateAccount.Account_Check_Mailing_info.isSelected()) {
			guestHistoryLogger.info("Check box already checked");
		} else {
			jse.executeScript("arguments[0].click();", CreateAccount.Account_Check_Mailing_info);

		}
		new Select(guest_his.GuestHistoryAccount_BillType).selectByVisibleText(PaymentMethod);
		test_steps.add("Payement Method : " + PaymentMethod);

		if (guest_his.GuestHistoryAccount_BillingAccountNum.isEnabled()) {
			guest_his.GuestHistoryAccount_BillingAccountNum.sendKeys(Account);
			test_steps.add("Account number : " + Account);
		}

		if (guest_his.GuestHistoryAccount_ExpDate.isEnabled()) {
			guest_his.GuestHistoryAccount_ExpDate.sendKeys(ExpDate);
			test_steps.add("Exp Date : " + ExpDate);
		}

		if (guest_his.GuestHistoryAccount_BillingNotes.isEnabled()) {
			guest_his.GuestHistoryAccount_BillingNotes.sendKeys(BillingNotes);
			test_steps.add("Billing Notes : " + BillingNotes);
		}

	}
	public void verifyAccountDisplayedInSearchResult(WebDriver driver, String GuestHistoryAccount,
			ArrayList<String> test_steps) {
		boolean isAccountPrsent = false;
		String account = "//tbody[@class='table-striped' and @data-bind='foreach: AccountList']/tr/td/span[text()='"
				+ GuestHistoryAccount + "']";
		try {
			if (driver.findElements(By.xpath(account)).size() > 0) {
				isAccountPrsent = true;
				test_steps.add("Account number present in search result: " + GuestHistoryAccount);
			} else {
				isAccountPrsent = false;
			}
		} catch (Exception e) {

		}
		Assert.assertEquals(isAccountPrsent, true, "Faled to verify");
		test_steps.add("Verify the account number in search result: " + GuestHistoryAccount);

	}
	
	public void verifyAccountDisplayedInSearchResult(WebDriver driver, String FirstName, String LastName,
			ArrayList<String> test_steps) {
		boolean isAccountPrsent = false;
		String account = "//tbody[@class='table-striped' and @data-bind='foreach: AccountList']/tr/td/span[text()='"
				+ FirstName + " "+LastName+ "']";
		try {
			if (driver.findElements(By.xpath(account)).size() > 0) {
				isAccountPrsent = true;
				test_steps.add("Account name present in search result: " + FirstName+ " "+LastName);
			} else {
				isAccountPrsent = false;
			}
		} catch (Exception e) {

		}
		Assert.assertEquals(isAccountPrsent, true, "Faled to verify");
		test_steps.add("Verify the account number in search result: " + FirstName+ " "+LastName);

	}
	public void searchReservation(WebDriver driver, String confirmationNum) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		try {
			Wait.WaitForElement(driver, OR.SearchReservation1);
			guest_his.searchReservation1.clear();
			guest_his.searchReservation1.sendKeys(confirmationNum);
		} catch (Exception e) {
			Wait.WaitForElement(driver, OR.SearchReservation);
			guest_his.searchReservation.clear();
			guest_his.searchReservation.sendKeys(confirmationNum);
		}
		Wait.explicit_wait_elementToBeClickable(guest_his.clickSearch, driver);
		guest_his.clickSearch.click();
	}
	public void searchWithAccountNumber(WebDriver driver, String AccountNumber, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		driver.navigate().refresh();

		Wait.waitUntilPresenceOfElementLocated(OR.guestAccountFirstName, driver);
		guest_his.enterGuestHistoryAccountNo.clear();
		guest_his.enterGuestHistoryAccountNo.sendKeys(AccountNumber);

		try {
		guest_his.clickSearchButton.click();
		Utility.app_logs.info("Click Search");
		}catch(Exception e) {
			Wait.WaitForElement(driver, OR.clickSearchButton1);
			Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton1), driver);
			guest_his.clickSearchButton1.click();
			guestHistoryLogger.info("Click Search");
			test_steps.add("Click Search");
		}


		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
		System.out.println(
				"AccNume expec:" + AccountNumber + "Result:" + guest_his.SearcehdGuestHistoryAccountNumber.getText());
	}
	public void searchWithFirstName(WebDriver driver, String accountFirstName, ArrayList<String> testSteps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		for(int i=0;i<guest_his.guestAccountFirstNames.size();i++) {
			if(guest_his.guestAccountFirstNames.get(i).isEnabled()) {
				Wait.WaitForElement(driver, OR.guestAccountFirstName);
				Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountFirstName), driver);
				Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountFirstName), driver);
				guest_his.guestAccountFirstNames.get(i).clear();
				guest_his.guestAccountFirstNames.get(i).sendKeys(accountFirstName);
				testSteps.add("Entered Guest First Name : " + accountFirstName);
				guestHistoryLogger.info("Entered Guest First Name : " + accountFirstName);
				break;
			}	
		}		
		Wait.WaitForElement(driver, OR.clickSearchButtons);
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButtons), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButtons), driver);
		Utility.ScrollToElement(guest_his.clickSearchButtons, driver);
		guest_his.clickSearchButtons.click();
		guestHistoryLogger.info("Click Search");
		testSteps.add("Click Search");
		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
		System.out.println("AccNume expec:" + accountFirstName + "Result:"
				+ guest_his.SearcehdGuestHistoryAccountNumber.getText());
	}


	/*
	 * public void mailinginfo(WebDriver driver, String AccountFirstName, String
	 * AccountLastName, String Phonenumber, String alternativeNumber, String
	 * Address1, String Address2, String Address3, String Email, String city, String
	 * State, String Postalcode, ArrayList<String> test_steps) throws
	 * InterruptedException { Mailinginfo(driver, AccountFirstName, AccountLastName,
	 * Phonenumber, alternativeNumber, Address1, Address2, Address3, Email, city,
	 * State, Postalcode);
	 * test_steps.add("Added details for Mailing info sections"); } public void
	 * billinginfo(WebDriver driver, String PaymentMethod, String Account, String
	 * ExpDate, String BillingNotes, ArrayList<String> test_steps) throws
	 * InterruptedException { JavascriptExecutor jse = (JavascriptExecutor) driver;
	 * Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	 * 
	 * Elements_Accounts CreateAccount = new Elements_Accounts(driver);
	 * Wait.wait5Second(); if
	 * (CreateAccount.Account_Check_Mailing_info.isSelected()) {
	 * guestHistoryLogger.info("Check box already checked"); } else {
	 * jse.executeScript("arguments[0].click();",
	 * CreateAccount.Account_Check_Mailing_info);
	 * 
	 * } new Select(guest_his.GuestHistoryAccount_BillType).selectByVisibleText(
	 * PaymentMethod); test_steps.add("Payement Method : " + PaymentMethod);
	 * 
	 * if (guest_his.GuestHistoryAccount_BillingAccountNum.isEnabled()) {
	 * guest_his.GuestHistoryAccount_BillingAccountNum.sendKeys(Account);
	 * test_steps.add("Account number : " + Account); }
	 * 
	 * if (guest_his.GuestHistoryAccount_ExpDate.isEnabled()) {
	 * guest_his.GuestHistoryAccount_ExpDate.sendKeys(ExpDate);
	 * test_steps.add("Exp Date : " + ExpDate); }
	 * 
	 * if (guest_his.GuestHistoryAccount_BillingNotes.isEnabled()) {
	 * guest_his.GuestHistoryAccount_BillingNotes.sendKeys(BillingNotes);
	 * test_steps.add("Billing Notes : " + BillingNotes); }
	 * 
	 * } public void verifyAccountDisplayedInSearchResult(WebDriver driver, String
	 * GuestHistoryAccount, ArrayList<String> test_steps) { boolean isAccountPrsent
	 * = false; String account =
	 * "//tbody[@class='table-striped' and @data-bind='foreach: AccountList']/tr/td/span[text()='"
	 * + GuestHistoryAccount + "']"; try { if
	 * (driver.findElements(By.xpath(account)).size() > 0) { isAccountPrsent = true;
	 * test_steps.add("Account number present in search result: " +
	 * GuestHistoryAccount); } else { isAccountPrsent = false; } } catch (Exception
	 * e) {
	 * 
	 * } Assert.assertEquals(isAccountPrsent, true, "Faled to verify");
	 * test_steps.add("Verify the account number in search result: " +
	 * GuestHistoryAccount);
	 * 
	 * } public void searchReservation(WebDriver driver, String confirmationNum) {
	 * Elements_GuestHistory guest_his = new Elements_GuestHistory(driver); try {
	 * Wait.WaitForElement(driver, OR.SearchReservation1);
	 * guest_his.searchReservation1.clear();
	 * guest_his.searchReservation1.sendKeys(confirmationNum); } catch (Exception e)
	 * { Wait.WaitForElement(driver, OR.SearchReservation);
	 * guest_his.searchReservation.clear();
	 * guest_his.searchReservation.sendKeys(confirmationNum); }
	 * Wait.explicit_wait_elementToBeClickable(guest_his.clickSearch, driver);
	 * guest_his.clickSearch.click(); } public void
	 * searchWithAccountNumber(WebDriver driver, String AccountNumber,
	 * ArrayList<String> test_steps) throws InterruptedException {
	 * Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	 * driver.navigate().refresh();
	 * 
	 * Wait.waitUntilPresenceOfElementLocated(OR.guestAccountFirstName, driver);
	 * guest_his.enterGuestHistoryAccountNo.clear();
	 * guest_his.enterGuestHistoryAccountNo.sendKeys(AccountNumber);
	 * 
	 * guest_his.clickSearchButton.click(); Utility.app_logs.info("Click Search");
	 * 
	 * Wait.WaitForElement(driver, OR.Number_Of_Accounts);
	 * Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
	 * System.out.println( "AccNume expec:" + AccountNumber + "Result:" +
	 * guest_his.SearcehdGuestHistoryAccountNumber.getText()); } public void
	 * searchWithFirstName(WebDriver driver, String accountFirstName,
	 * ArrayList<String> testSteps) { Elements_GuestHistory guest_his = new
	 * Elements_GuestHistory(driver); Wait.WaitForElement(driver,
	 * OR.guestAccountFirstName);
	 * Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountFirstName), driver);
	 * Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountFirstName), driver);
	 * guest_his.guestAccountFirstName.clear();
	 * guest_his.guestAccountFirstName.sendKeys(accountFirstName);
	 * testSteps.add("Entered Guest First Name : " + accountFirstName);
	 * guestHistoryLogger.info("Entered Guest First Name : " + accountFirstName);
	 * Wait.WaitForElement(driver, OR.clickSearchButton);
	 * Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton), driver);
	 * Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton), driver);
	 * guest_his.clickSearchButton.click(); guestHistoryLogger.info("Click Search");
	 * testSteps.add("Click Search");
	 * 
	 * Wait.WaitForElement(driver, OR.Number_Of_Accounts);
	 * Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
	 * System.out.println("AccNume expec:" + accountFirstName + "Result:" +
	 * guest_his.SearcehdGuestHistoryAccountNumber.getText());
	 * 
	 * }
	 */
	public void clickOnPrintIcon(WebDriver driver, ArrayList<String>test_steps) throws InterruptedException, IOException {
		//ArrayList<String> testSteps = new ArrayList<String>();
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		//Elements_CPReservation element = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR.ClickPrintIcon);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ClickPrintIcon), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ClickPrintIcon), driver);
		guest_his.clickPrintIcon.click();
		test_steps.add("Click on print icon");


	}
	
	public void clickExportButton(WebDriver driver, String exportInto, ArrayList<String>test_steps) throws InterruptedException, IOException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.ClickExport);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ClickExport), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ClickExport), driver);
		guest_his.clickExport.click();
		test_steps.add("Click EXPORT Dropdown");
		
		String ClickTo = "//ul[@class='dropdown-menu']/li/div[text()='"+exportInto+"']";
		driver.findElement(By.xpath(ClickTo)).click();
		test_steps.add("Click EXPORT Dropdown Option: "+ exportInto.toUpperCase());
        Wait.wait15Second();
	}
	public void searchWithAccountNumberAndAccountName(WebDriver driver, String AccountNumber,String AccFirstName, String AccLastName,boolean isCombine)
			throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		driver.navigate().refresh();

		Wait.waitUntilPresenceOfElementLocated(OR.guestAccountFirstName, driver);
		guest_his.enterGuestHistoryAccountNo.clear();
		guest_his.enterGuestHistoryAccountNo.sendKeys(AccountNumber);
		
		Wait.WaitForElement(driver, OR.guestAccountFirstName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountFirstName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountFirstName), driver);
		guest_his.guestAccountFirstName.clear();
		guest_his.guestAccountFirstName.sendKeys(AccFirstName);
		//test_steps.add("Entered Guest First Name : " + AccFirstName);
		guestHistoryLogger.info("Entered Guest First Name : " + AccFirstName);

		Wait.WaitForElement(driver, OR.guestAccountLastName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountLastName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountLastName), driver);
		guest_his.guestAccountLastName.clear();
		guest_his.guestAccountLastName.sendKeys(AccLastName);
		//test_steps.add("Entered Guest Last Name : " + AccLastName);
		guestHistoryLogger.info("Entered Guest Last Name : " + AccLastName);
		
		if(isCombine) {
			guest_his.clickCombine.click();
		}else {
			
		
		}

		try {
		guest_his.clickSearchButton.click();
		Utility.app_logs.info("Click Search");
		}catch(Exception e) {
			Wait.WaitForElement(driver, OR.clickSearchButton1);
			Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton1), driver);
			guest_his.clickSearchButton1.click();
			guestHistoryLogger.info("Click Search");
		}

		Wait.WaitForElement(driver, OR.Number_Of_Accounts);
		Wait.WaitForElement(driver, OR.SearcehdGuestHistoryAccountNumber);
		System.out.println(
				"AccNume expec:" + AccountNumber + "Result:" + guest_his.SearcehdGuestHistoryAccountNumber.getText());
	}
	
	public void verifyFirstLastNameFieldsEmpty(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.wait2Second();
		testSteps.add("Verify salutation field is empty");
		Wait.waitForElementToBeVisibile(By.xpath(OR.SelectSalutation), driver);
		String options = new Select(guest_his.selectSalutation).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify first name field is empty");
		String getFirstName = Utility.getELementText(guest_his.enterAccountFName, true, "value");
		Utility.verifyEquals(getFirstName.trim(), "", testSteps);

		testSteps.add("Verify last name field is empty");
		String getLastName = Utility.getELementText(guest_his.AccountDetailsLastName, true, "value");
		Utility.verifyEquals(getLastName.trim(), "", testSteps);
	
	}
	
	public void verifyAccountAttributesIsNotSelected(WebDriver driver,
			ArrayList<String> testSteps) {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Select_Market_Segment), driver);
		String options = new Select(guest_his.Select_Market_Segment).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "--Select--", testSteps);
		testSteps.add("Verified market segment dropdown field is empty");
		Wait.waitForElementToBeVisibile(By.xpath(OR.Select_Referrals), driver);
		options = new Select(guest_his.Select_Referrals).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "--Select--", testSteps);
		testSteps.add("Verified referrals dropdown field is empty");

	}
	
	public void verifyAccountNumberFromDetailsPage(WebDriver driver, ArrayList<String> testSteps, String accountNo) {

		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
	
		testSteps.add("Verify account number field is reset");
		Wait.WaitForElement(driver, OR.getAccountOnDetailsPage);
		Wait.waitForElementToBeVisibile(By.xpath(OR.getAccountOnDetailsPage), driver);
		String account = guest_his.GetAccountOnDetailsPage.getAttribute("value").toString();
		Utility.verifyNotEqual(account, accountNo, testSteps);			
	}
	
	public void VerifyMailinginfoIsEmpty(WebDriver driver, String AccountFirstName, String AccountLastName,
			String Phonenumber, String alternativeNumber, String Address1, String Address2, String Address3,
			String Email, String city, String State, String Postalcode, ArrayList<String> testSteps) throws InterruptedException {

		Elements_Accounts CreateAccount = new Elements_Accounts(driver);
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);

		testSteps.add("Verify Mailing salutation dropdown field is empty");
		String options = new Select(guest_his.mailingInfoSalutation).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);

		testSteps.add("Verify First Name field is empty");
		String getFirstName = Utility.getELementText(CreateAccount.Account_Enter_First_Name, true, "value");
		Utility.verifyEquals(getFirstName.trim(), "", testSteps);

		testSteps.add("Verify Last Name field is empty");
		String getLastName = Utility.getELementText(CreateAccount.Account_Enter_Last_Name, true, "value");
		Utility.verifyEquals(getLastName.trim(), "", testSteps);
		
		testSteps.add("Verify phone number field is empty");
		String getPhoneNumber = Utility.getELementText(CreateAccount.Account_Phone_number, true, "value");
		Utility.verifyEquals(getPhoneNumber.trim(), "", testSteps);

		testSteps.add("Verify alternative phone number field is empty");
		String getAlterPhoneNumber = Utility.getELementText(CreateAccount.Account_Enter_AltPhoneNumber, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "", testSteps);

		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Utility.ElementFinderUntillNotShow(By.xpath(OR.Account_Enter_Line1), driver);
		js1.executeScript("arguments[0].scrollIntoView();", CreateAccount.Account_Enter_Line1);


		testSteps.add("Verify address line 1 field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(CreateAccount.Account_Enter_Line1, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "", testSteps);

		testSteps.add("Verify address line 2 field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(CreateAccount.Account_Enter_Line2, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "", testSteps);

		testSteps.add("Verify address line 3 field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(CreateAccount.Account_Enter_Line3, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "", testSteps);

		testSteps.add("Verify email field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(CreateAccount.Account_Enter_Email, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "", testSteps);

		testSteps.add("Verify city field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(CreateAccount.Account_Enter_City, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "", testSteps);

		testSteps.add("Verify state dropdown field is empty");
		options = "";
		options = new Select(CreateAccount.Select_Account_State).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify Mailing Country dropdown field is having default value as United States");
		options = "";
		options = new Select(guest_his.getCountry).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);

		testSteps.add("Verify postal code field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(CreateAccount.Account_Enter_PostalCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "", testSteps);

		testSteps.add("Verify phone country code field is having default value as 1");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.phoneCountryCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);

		testSteps.add("Verify alternate phone country code field is having default value as 1");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.alternatePhoneCountryCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify phone Extention is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.phoneExtention, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
	
		testSteps.add("Verify alternate phone Extention is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.alternatePhoneExtention, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
	
		testSteps.add("Verify fax country code field is having default value as 1");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.faxCountryCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
	
		testSteps.add("Verify fax country code field is having default value as 1");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.faxExtention, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
	
//BillingInformation
		
		testSteps.add("Verify billing salutation dropdown field is empty");
		options = new Select(guest_his.billingInfoSaluation).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify billing first name is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.enterBillingFirstName, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing last name is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.enterBillingLastName, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing phone country code field is having default value as 1");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingPhoneCountryCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing phone Number field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingPhoneNumber, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing phone extention field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingPhoneExtention, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing alternate phone country code field is having default value as 1");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingAlternatePhoneCountryCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing alternate phone number field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingAlternatePhoneNumber, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing alternate phone Extention field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingAlternatePhoneExtention, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing address line 1 field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingAddressLine1, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing address line 2 field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingAddressLine2, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing address line 3 field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingAddressLine3, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing Fax country code field is having default value as 1");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingFaxCountryCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing Fax number field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingFaxNumber, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing Fax Extention field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingFaxExtention, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing Email field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingEmail, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing City field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingCity, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing postal code field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.billingPostalCode, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify billing State dropdown field is empty");
		options = "";
		options = new Select(guest_his.billingStateDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify billing Country dropdown field is having default value as United States");
		options = "";
		options = new Select(guest_his.billingCountryDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify billing Property dropdown field is having default value as Reports Property");
		options = "";
		options = new Select(guest_his.billingPropertyDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify billing type dropdown field is empty");
		options = "";
		options = new Select(guest_his.billingTypeDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify Note type dropdown field is having default value as internal");
		options = "";
		options = new Select(guest_his.noteType).getFirstSelectedOption().getText();
		Utility.verifyEquals(options, "", testSteps);
		
		testSteps.add("Verify Note subject field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.noteSubject, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
		testSteps.add("Verify Note details field is empty");
		getAlterPhoneNumber = "";
		getAlterPhoneNumber = Utility.getELementText(guest_his.notedetails, true, "value");
		Utility.verifyEquals(getAlterPhoneNumber.trim(), "1", testSteps);
		
	}

	public void selectMailingInfoSaluation(WebDriver driver, String mailingInfosaluation,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.mailingInfoSalutation, driver);
		new Select(guest_his.mailingInfoSalutation).selectByVisibleText(mailingInfosaluation);
		guestHistoryLogger.info("Mailing Info Salutation Selected: " + mailingInfosaluation);
		test_steps.add("Mailing Info Salutation Selected: " + mailingInfosaluation);

	}
	public void enterPhoneCountryCode(WebDriver driver,String phoneCountryCode, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.phoneCountryCode);
			Wait.waitForElementToBeVisibile(By.xpath(OR.phoneCountryCode), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.phoneCountryCode), driver);
			guest_his.phoneCountryCode.sendKeys(Keys.CONTROL + "a");
			guest_his.phoneCountryCode.sendKeys(Keys.DELETE);
			guest_his.phoneCountryCode.sendKeys(phoneCountryCode);

		guestHistoryLogger.info("Phone Country Code Enterted: " + phoneCountryCode);
		test_steps.add("Phone Country Code Enterted: " + phoneCountryCode);
		}
	
	public void enterAlternatePhoneCountryCode(WebDriver driver,String alternatePhoneCountryCode, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.alternatePhoneCountryCode);
			Wait.waitForElementToBeVisibile(By.xpath(OR.alternatePhoneCountryCode), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.alternatePhoneCountryCode), driver);
			guest_his.alternatePhoneCountryCode.sendKeys(Keys.CONTROL + "a");
			guest_his.alternatePhoneCountryCode.sendKeys(Keys.DELETE);
			guest_his.alternatePhoneCountryCode.sendKeys(alternatePhoneCountryCode);

		guestHistoryLogger.info("Alternate Phone Country Code Enterted: " + alternatePhoneCountryCode);
		test_steps.add("Alternate phone Country Code Enterted: " + alternatePhoneCountryCode);
		}
	public void enterPhoneExtention(WebDriver driver,String phoneExtention, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.phoneExtention);
			Wait.waitForElementToBeVisibile(By.xpath(OR.phoneExtention), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.phoneExtention), driver);
			guest_his.phoneExtention.sendKeys(Keys.CONTROL + "a");
			guest_his.phoneExtention.sendKeys(Keys.DELETE);
			guest_his.phoneExtention.sendKeys(phoneExtention);

		guestHistoryLogger.info("Phone Extention Enterted: " + phoneExtention);
		test_steps.add("Phone Extention Enterted: " + phoneExtention);
		}
	public void enterAlternatePhoneExtention(WebDriver driver,String alternatePhoneExtention, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.alternatePhoneExtention);
			Wait.waitForElementToBeVisibile(By.xpath(OR.alternatePhoneExtention), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.alternatePhoneExtention), driver);
			guest_his.alternatePhoneExtention.sendKeys(Keys.CONTROL + "a");
			guest_his.alternatePhoneExtention.sendKeys(Keys.DELETE);
			guest_his.alternatePhoneExtention.sendKeys(alternatePhoneExtention);

		guestHistoryLogger.info("Alternate phone Extention Enterted: " + alternatePhoneExtention);
		test_steps.add("Alternate phone Extention Enterted: " + alternatePhoneExtention);
		}
		
	public void enterFaxCountryCode(WebDriver driver,String faxCountryCode, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.faxCountryCode);
			Wait.waitForElementToBeVisibile(By.xpath(OR.faxCountryCode), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.faxCountryCode), driver);
			guest_his.faxCountryCode.sendKeys(Keys.CONTROL + "a");
			guest_his.faxCountryCode.sendKeys(Keys.DELETE);
			guest_his.faxCountryCode.sendKeys(faxCountryCode);

		guestHistoryLogger.info("Fax country code Enterted: " + faxCountryCode);
		test_steps.add("Fax country code Enterted: " + faxCountryCode);
		}
	public void enterFaxExtention(WebDriver driver,String faxExtention, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.faxExtention);
			Wait.waitForElementToBeVisibile(By.xpath(OR.faxExtention), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.faxExtention), driver);
			guest_his.faxExtention.sendKeys(Keys.CONTROL + "a");
			guest_his.faxExtention.sendKeys(Keys.DELETE);
			guest_his.faxExtention.sendKeys(faxExtention);

		guestHistoryLogger.info("Fax country code Enterted: " + faxExtention);
		test_steps.add("Fax country code Enterted: " + faxExtention);
		}
	public void selectMailingCountry(WebDriver driver, String getCountry,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.getCountry, driver);
		new Select(guest_his.getCountry).selectByVisibleText(getCountry);
		guestHistoryLogger.info("Mailing Country Selected: " + getCountry);
		test_steps.add("Mailing Country Selected: " + getCountry);

	}
	
	
//BillingInformation
	
	public void selectBillingInfoSaluation(WebDriver driver, String billingInfoSaluation,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.billingInfoSaluation, driver);
		new Select(guest_his.billingInfoSaluation).selectByVisibleText(billingInfoSaluation);
		guestHistoryLogger.info("Billing Info Salutation Selected: " + billingInfoSaluation);
		test_steps.add("Billing Info Salutation Selected: " + billingInfoSaluation);

	}
	public void enterBillingFirstName(WebDriver driver,String billingFirstName, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.EnterBillingFirstName);
			Wait.waitForElementToBeVisibile(By.xpath(OR.EnterBillingFirstName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.EnterBillingFirstName), driver);
			guest_his.enterBillingFirstName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterBillingFirstName.sendKeys(Keys.DELETE);
			guest_his.enterBillingFirstName.sendKeys(billingFirstName);

		guestHistoryLogger.info("Billing first name Enterted: " + billingFirstName);
		test_steps.add("Billing first name Enterted: " + billingFirstName);
		}
	public void enterBillingLastName(WebDriver driver,String billingLastName, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.EnterBillingLastName);
			Wait.waitForElementToBeVisibile(By.xpath(OR.EnterBillingLastName), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.EnterBillingLastName), driver);
			guest_his.enterBillingLastName.sendKeys(Keys.CONTROL + "a");
			guest_his.enterBillingLastName.sendKeys(Keys.DELETE);
			guest_his.enterBillingLastName.sendKeys(billingLastName);

		guestHistoryLogger.info("Billing last name Enterted: " + billingLastName);
		test_steps.add("Billing last name Enterted: " + billingLastName);
		}
	public void enterBillingPhoneCountryCode(WebDriver driver,String billingPhoneCountryCode, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingPhoneCountryCode);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingPhoneCountryCode), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingPhoneCountryCode), driver);
			guest_his.billingPhoneCountryCode.sendKeys(Keys.CONTROL + "a");
			guest_his.billingPhoneCountryCode.sendKeys(Keys.DELETE);
			guest_his.billingPhoneCountryCode.sendKeys(billingPhoneCountryCode);

		guestHistoryLogger.info("Billing Phone Country Code Enterted: " + billingPhoneCountryCode);
		test_steps.add("Billing Phone Country Code Enterted: " + billingPhoneCountryCode);
		}
	public void enterBillingPhoneNumber(WebDriver driver,String billingPhoneNumber, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingPhoneNumber);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingPhoneNumber), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingPhoneNumber), driver);
			guest_his.billingPhoneNumber.sendKeys(Keys.CONTROL + "a");
			guest_his.billingPhoneNumber.sendKeys(Keys.DELETE);
			guest_his.billingPhoneNumber.sendKeys(billingPhoneNumber);

		guestHistoryLogger.info("Billing Phone Country Code Enterted: " + billingPhoneNumber);
		test_steps.add("Billing Phone Country Code Enterted: " + billingPhoneNumber);
		}
	public void enterBillingPhoneExtention(WebDriver driver,String billingPhoneExtention, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingPhoneExtention);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingPhoneExtention), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingPhoneExtention), driver);
			guest_his.billingPhoneExtention.sendKeys(Keys.CONTROL + "a");
			guest_his.billingPhoneExtention.sendKeys(Keys.DELETE);
			guest_his.billingPhoneExtention.sendKeys(billingPhoneExtention);

		guestHistoryLogger.info("Billing Phone Country Code Enterted: " + billingPhoneExtention);
		test_steps.add("Billing Phone Country Code Enterted: " + billingPhoneExtention);
		}
	public void enterBillingAlternatePhoneCountryCode(WebDriver driver,String billingAlternatePhoneCountryCode, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingAlternatePhoneCountryCode);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingAlternatePhoneCountryCode), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingAlternatePhoneCountryCode), driver);
			guest_his.billingAlternatePhoneCountryCode.sendKeys(Keys.CONTROL + "a");
			guest_his.billingAlternatePhoneCountryCode.sendKeys(Keys.DELETE);
			guest_his.billingAlternatePhoneCountryCode.sendKeys(billingAlternatePhoneCountryCode);

		guestHistoryLogger.info("Billing alternate Phone Country Code Enterted: " + billingAlternatePhoneCountryCode);
		test_steps.add("Billing alternate Phone Country Code Enterted: " + billingAlternatePhoneCountryCode);
		}
	public void enterBillingAlternatePhoneNumber(WebDriver driver,String billingAlternatePhoneNumber, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingAlternatePhoneNumber);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingAlternatePhoneNumber), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingAlternatePhoneNumber), driver);
			guest_his.billingAlternatePhoneNumber.sendKeys(Keys.CONTROL + "a");
			guest_his.billingAlternatePhoneNumber.sendKeys(Keys.DELETE);
			guest_his.billingAlternatePhoneNumber.sendKeys(billingAlternatePhoneNumber);

		guestHistoryLogger.info("Billing alternate Phone number Enterted: " + billingAlternatePhoneNumber);
		test_steps.add("Billing alternate Phone number Enterted: " + billingAlternatePhoneNumber);
		}
	public void enterBillingAlternatePhoneExtention(WebDriver driver,String billingAlternatePhoneExtention, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingAlternatePhoneExtention);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingAlternatePhoneExtention), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingAlternatePhoneExtention), driver);
			guest_his.billingAlternatePhoneExtention.sendKeys(Keys.CONTROL + "a");
			guest_his.billingAlternatePhoneExtention.sendKeys(Keys.DELETE);
			guest_his.billingAlternatePhoneExtention.sendKeys(billingAlternatePhoneExtention);

		guestHistoryLogger.info("Billing alternate Phone extention Enterted: " + billingAlternatePhoneExtention);
		test_steps.add("Billing alternate Phone extention Enterted: " + billingAlternatePhoneExtention);
		}
	public void enterBillingAddressLine1(WebDriver driver,String billingAddressLine1, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingAddressLine1);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingAddressLine1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingAddressLine1), driver);
			guest_his.billingAddressLine1.sendKeys(Keys.CONTROL + "a");
			guest_his.billingAddressLine1.sendKeys(Keys.DELETE);
			guest_his.billingAddressLine1.sendKeys(billingAddressLine1);

		guestHistoryLogger.info("Billing Address Line 1 Enterted: " + billingAddressLine1);
		test_steps.add("Billing Address Line 1 Enterted: " + billingAddressLine1);
		}
	public void enterBillingAddressLine2(WebDriver driver,String billingAddressLine2, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingAddressLine2);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingAddressLine2), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingAddressLine2), driver);
			guest_his.billingAddressLine2.sendKeys(Keys.CONTROL + "a");
			guest_his.billingAddressLine2.sendKeys(Keys.DELETE);
			guest_his.billingAddressLine2.sendKeys(billingAddressLine2);

		guestHistoryLogger.info("Billing Address Line 2 Enterted: " + billingAddressLine2);
		test_steps.add("Billing Address Line 2 Enterted: " + billingAddressLine2);
		}
	public void enterBillingAddressLine3(WebDriver driver,String billingAddressLine3, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingAddressLine3);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingAddressLine3), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingAddressLine3), driver);
			guest_his.billingAddressLine3.sendKeys(Keys.CONTROL + "a");
			guest_his.billingAddressLine3.sendKeys(Keys.DELETE);
			guest_his.billingAddressLine3.sendKeys(billingAddressLine3);

		guestHistoryLogger.info("Billing Address Line 3 Enterted: " + billingAddressLine3);
		test_steps.add("Billing Address Line 3 Enterted: " + billingAddressLine3);
		}
	public void enterBillingFaxCountryCode(WebDriver driver,String billingFaxCountryCode, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingFaxCountryCode);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingFaxCountryCode), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingFaxCountryCode), driver);
			guest_his.billingFaxCountryCode.sendKeys(Keys.CONTROL + "a");
			guest_his.billingFaxCountryCode.sendKeys(Keys.DELETE);
			guest_his.billingFaxCountryCode.sendKeys(billingFaxCountryCode);

		guestHistoryLogger.info("Billing fax country code Enterted: " + billingFaxCountryCode);
		test_steps.add("Billing fax country code Enterted: " + billingFaxCountryCode);
		}
	public void enterBillingFaxNumber(WebDriver driver,String billingFaxNumber, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingFaxNumber);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingFaxNumber), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingFaxNumber), driver);
			guest_his.billingFaxNumber.sendKeys(Keys.CONTROL + "a");
			guest_his.billingFaxNumber.sendKeys(Keys.DELETE);
			guest_his.billingFaxNumber.sendKeys(billingFaxNumber);

		guestHistoryLogger.info("Billing fax number Enterted: " + billingFaxNumber);
		test_steps.add("Billing fax number Enterted: " + billingFaxNumber);
		}
	public void enterBillingFaxExtention(WebDriver driver,String billingFaxExtention, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingFaxExtention);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingFaxExtention), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingFaxExtention), driver);
			guest_his.billingFaxExtention.sendKeys(Keys.CONTROL + "a");
			guest_his.billingFaxExtention.sendKeys(Keys.DELETE);
			guest_his.billingFaxExtention.sendKeys(billingFaxExtention);

		guestHistoryLogger.info("Billing fax extention Enterted: " + billingFaxExtention);
		test_steps.add("Billing fax extention Enterted: " + billingFaxExtention);
		}
	public void enterBillingEmail(WebDriver driver,String billingEmail, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingEmail);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingEmail), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingEmail), driver);
			guest_his.billingEmail.sendKeys(Keys.CONTROL + "a");
			guest_his.billingEmail.sendKeys(Keys.DELETE);
			guest_his.billingEmail.sendKeys(billingEmail);

		guestHistoryLogger.info("Billing Email Enterted: " + billingEmail);
		test_steps.add("Billing Email Enterted: " + billingEmail);
		}
	public void enterBillingCity(WebDriver driver,String billingCity, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingCity);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingCity), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingCity), driver);
			guest_his.billingCity.sendKeys(Keys.CONTROL + "a");
			guest_his.billingCity.sendKeys(Keys.DELETE);
			guest_his.billingCity.sendKeys(billingCity);

		guestHistoryLogger.info("Billing City Enterted: " + billingCity);
		test_steps.add("Billing City Enterted: " + billingCity);
		}
	public void enterBillingPostalCode(WebDriver driver,String billingPostalCode, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.billingPostalCode);
			Wait.waitForElementToBeVisibile(By.xpath(OR.billingPostalCode), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.billingPostalCode), driver);
			guest_his.billingPostalCode.sendKeys(Keys.CONTROL + "a");
			guest_his.billingPostalCode.sendKeys(Keys.DELETE);
			guest_his.billingPostalCode.sendKeys(billingPostalCode);

		guestHistoryLogger.info("Billing Postal Code Enterted: " + billingPostalCode);
		test_steps.add("Billing Postal Code Enterted: " + billingPostalCode);
		}
	public void selectBillingStateDropdown(WebDriver driver, String billingStateDropdown,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.billingStateDropdown, driver);
		new Select(guest_his.billingStateDropdown).selectByVisibleText(billingStateDropdown);
		guestHistoryLogger.info("Billing State Selected: " + billingStateDropdown);
		test_steps.add("Billing State Selected: " + billingStateDropdown);

	}
	public void selectBillingCountryDropdown(WebDriver driver, String billingCountryDropdown,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.billingCountryDropdown, driver);
		new Select(guest_his.billingCountryDropdown).selectByVisibleText(billingCountryDropdown);
		guestHistoryLogger.info("Billing State Selected: " + billingCountryDropdown);
		test_steps.add("Billing State Selected: " + billingCountryDropdown);

	}
	public void selectBillingPropertyDropdown(WebDriver driver, String billingPropertyDropdown,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.billingPropertyDropdown, driver);
		new Select(guest_his.billingPropertyDropdown).selectByVisibleText(billingPropertyDropdown);
		guestHistoryLogger.info("Billing Property Selected: " + billingPropertyDropdown);
		test_steps.add("Billing Property Selected: " + billingPropertyDropdown);

	}
	public void selectBillingTypeDropdown(WebDriver driver, String billingTypeDropdown,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.billingTypeDropdown, driver);
		new Select(guest_his.billingTypeDropdown).selectByVisibleText(billingTypeDropdown);
		guestHistoryLogger.info("Billing Type Selected: " + billingTypeDropdown);
		test_steps.add("Billing Type Selected: " + billingTypeDropdown);

	}
	//clickOnAddNoteButton
	public void clickOnAddNoteButton(WebDriver driver, ArrayList<String>test_steps) throws InterruptedException, IOException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.ClickAddNoteButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ClickAddNoteButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ClickAddNoteButton), driver);
		guest_his.clickAddNoteButton.click();
		test_steps.add("Click on add note button");

	}
	public void selectNoteType(WebDriver driver, String noteType,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.noteType, driver);
		new Select(guest_his.noteType).selectByVisibleText(noteType);
		guestHistoryLogger.info("Note Type Selected: " + noteType);
		test_steps.add("Note Type Selected: " + noteType);

	}
	public void enterNoteSubject(WebDriver driver,String noteSubject, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.NoteSubject);
			Wait.waitForElementToBeVisibile(By.xpath(OR.NoteSubject), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.NoteSubject), driver);
			guest_his.noteSubject.sendKeys(Keys.CONTROL + "a");
			guest_his.noteSubject.sendKeys(Keys.DELETE);
			guest_his.noteSubject.sendKeys(noteSubject);

		guestHistoryLogger.info("Note Subject Enterted: " + noteSubject);
		test_steps.add("Note Subject Enterted: " + noteSubject);
		}
	public void enterNotedetails(WebDriver driver,String notedetails, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			Wait.WaitForElement(driver, OR.Notedetails);
			Wait.waitForElementToBeVisibile(By.xpath(OR.Notedetails), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.Notedetails), driver);
			guest_his.notedetails.sendKeys(Keys.CONTROL + "a");
			guest_his.notedetails.sendKeys(Keys.DELETE);
			guest_his.notedetails.sendKeys(notedetails);

		guestHistoryLogger.info("Note Details Enterted: " + notedetails);
		test_steps.add("Note Details Enterted: " + notedetails);
		}
	//saveNoteButton
	public void clickOnSaveNoteButton(WebDriver driver, ArrayList<String>test_steps) throws InterruptedException, IOException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Wait.WaitForElement(driver, OR.ClickSaveNote);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ClickSaveNote), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ClickSaveNote), driver);
		guest_his.clickSaveNote.click();
		test_steps.add("Click on save note button");

	}
	
	//AccountAttributes
	
	public void selectMarketSegment(WebDriver driver, String noteType,
			ArrayList<String> test_steps) throws InterruptedException{
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Utility.ScrollToElement(guest_his.noteType, driver);
		new Select(guest_his.noteType).selectByVisibleText(noteType);
		guestHistoryLogger.info("Note Type Selected: " + noteType);
		test_steps.add("Note Type Selected: " + noteType);

	}
	
	
	
	//Verifying
	public void verifyBillingFieldAfterReset(WebDriver driver, 
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		String Value="";
		Value = new Select(guest_his.billingInfoSaluation).getFirstSelectedOption().getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing salution option is empty");
		Value = guest_his.billingPhoneCountryCode.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing country code is empty");
		Value = guest_his.billingPhoneNumber.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing phone number is empty");

		Value = guest_his.billingPhoneExtention.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing phone extention is empty");
		Value = guest_his.billingAlternatePhoneCountryCode.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing alternative phone country code is empty");
		Value = guest_his.billingAlternatePhoneNumber.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing alternative phone number is empty");
		Value = guest_his.billingAlternatePhoneExtention.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified alternative phone extention is empty");
		Value = guest_his.billingAddressLine1.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing address 1 is empty");
		Value = guest_his.billingAddressLine2.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing address 2 is empty");

		Value = guest_his.billingAddressLine3.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing address 3 is empty");
		Value = new Select(guest_his.billingPropertyDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing property is empty");
		Value = new Select(guest_his.billingTypeDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing type is empty");
		Value = guest_his.billingFaxCountryCode.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing fax country code is empty");
		Value = guest_his.billingFaxNumber.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing fax number is empty");
		Value = guest_his.billingFaxExtention.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing fax extention is empty");
		Value = guest_his.billingEmail.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing email is empty");

		Value = guest_his.billingCity.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing city is empty");
		Value = guest_his.billingPostalCode.getText();
		Utility.verifyEquals(Value, "", test_steps);
		test_steps.add("Verified billing postal code is empty");	
		Value = new Select(guest_his.billingStateDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(Value, "--Select--", test_steps);
		test_steps.add("Verified billing state is return to default");
		Value = new Select(guest_his.billingCountryDropdown).getFirstSelectedOption().getText();
		Utility.verifyEquals(Value, "United States", test_steps);
		test_steps.add("Verified billing coutry is is return to default");
	}
	
	public int searchAccountAndGetCount(WebDriver driver, String accountFirstName, String accountLastName)
			throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		int items=0;

		Wait.WaitForElement(driver, OR.guestAccountFirstName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountFirstName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountFirstName), driver);
		guest_his.guestAccountFirstName.clear();
		guest_his.guestAccountFirstName.sendKeys(accountFirstName);
		testSteps.add("Entered Guest First Name : " + accountFirstName);
		guestHistoryLogger.info("Entered Guest First Name : " + accountFirstName);

		Wait.WaitForElement(driver, OR.guestAccountLastName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.guestAccountLastName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.guestAccountLastName), driver);
		guest_his.guestAccountLastName.clear();
		guest_his.guestAccountLastName.sendKeys(accountLastName);
		testSteps.add("Entered Guest Last Name : " + accountLastName);
		guestHistoryLogger.info("Entered Guest Last Name : " + accountLastName);

		//Wait.WaitForElement(driver, OR.clickSearchButton);
		try {
		Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton), driver);
		guest_his.clickSearchButton.click();
		}catch (Exception e) {
			Wait.waitForElementToBeVisibile(By.xpath(OR.clickSearchButton1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.clickSearchButton1), driver);
			guest_his.clickSearchButton1.click();
		}
		guestHistoryLogger.info("Click Search");
		testSteps.add("Click Search");
		if(driver.findElements(By.xpath(OR.ClickSearchedGuestProfile)).size()>0) {
          items=guest_his.ClickSearchedGuestProfiles.size();	
          guestHistoryLogger.info("Total number of Accounts found "+items);
          testSteps.add("Total number of Accounts found "+items);
		}else {
	          guestHistoryLogger.info("No Accounts found "+items);
	          testSteps.add("No Accounts found "+items);
		}

		return items;
	}
	
	public boolean OpenSearchedGuestHistroyAccountAndVerifyEmail(WebDriver driver,String Email) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		 boolean isGuestProfile=false;
			int items=guest_his.ClickSearchedGuestProfiles.size();
			for(int i=0;i<=items-1;i++) {
				Utility.ScrollToElement(guest_his.ClickSearchedGuestProfiles.get(i), driver);
				guest_his.ClickSearchedGuestProfiles.get(i).click();
				assertTrue(guest_his.selectAccountSalutation_1.isDisplayed(), "Guest History Account page didn't display");
				Wait.explicit_wait_visibilityof_webelement_120(guest_his.getEmail, driver);
				String foundEmail = guest_his.getEmail.getAttribute("value").toString();
				System.out.println(foundEmail);
				if(foundEmail.equalsIgnoreCase(Email)) {
					isGuestProfile=true;
				}
				closeAccount1(driver);
				}
			return isGuestProfile;
		
	}
	public void closeAccount1(WebDriver driver) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);

		Wait.explicit_wait_visibilityof_webelement(guest_his.closeReservation, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", guest_his.closeReservation);


	}
	public void OpenSearchedAllGuestHistroyAccountAndVerifyReservationExists(WebDriver driver,String accountFirstName,String accountLastName,String Email,String reservationNumber,ArrayList<String> testSteps) throws InterruptedException {
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
			searchAccountAndGetCount(driver, accountFirstName, accountLastName);
			int items=guest_his.ClickSearchedGuestProfiles.size();
			for(int i=0;i<=items-1;i++) {
				searchAccountAndGetCount(driver, accountFirstName, accountLastName);
				Wait.WaitForElement(driver, OR.ClickSearchedGuestProfile);
				Utility.ScrollToElement(guest_his.ClickSearchedGuestProfiles.get(i), driver);
				guest_his.ClickSearchedGuestProfiles.get(i).click();
				assertTrue(guest_his.selectAccountSalutation_1.isDisplayed(), "Guest History Account page didn't display");
				OpenReservtionTab_GuestHistoryAccount(driver);
				searchReservation(driver, reservationNumber);
				verifyReservationExistInSearch(driver, reservationNumber, true, testSteps);
				closeAccount1(driver);
				
			}
		
	}
	
}
