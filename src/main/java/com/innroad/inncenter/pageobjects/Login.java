package com.innroad.inncenter.pageobjects;

import java.util.ArrayList;

import javax.mail.Message;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.ILogin;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Airbnb;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.*;

public class Login implements ILogin {

	private static Logger loginLogger = Logger.getLogger("Login");

	@Override
	public void login(WebDriver driver, String url, String Clientcode, String UserID, String Password)
			throws InterruptedException {
Utility.USED_CLIENT = Clientcode;
		WebElementsLogin wl = new WebElementsLogin(driver);
		driver.get(url); // Overriding test-data url value with
										// jenkins build parameter
		loginLogger.info("Entered application URL");
		Wait.WaitForElement_ID(driver, OR.clientCode);
		wl.clientCode.sendKeys(Clientcode);
		loginLogger.info("Entered client code " + Clientcode);
		wl.userID.sendKeys(UserID);
		Utility.userId = UserID;
		loginLogger.info("Entered used id : " + Utility.userId);
//		loginLogger.info("Entered used id");
		wl.password.sendKeys(Password);
		loginLogger.info("Entered password : " +Password);
		Wait.wait3Second();
		// Assert.assertEquals("x", "y");
		wl.Login.click();
		loginLogger.info("Clicked on login button");

		Wait.WaitForElement(driver, OR.LoginModuleLoding);
		// Wait.waitForElementToBeGone(driver, wl.LoginModuleLoding, 300);
		Wait.WaitForElement(driver, OR.New_Reservation_Button);
		;
		// Wait.waitUntilPresenceOfElementLocated("//small[.='Items Per
		// Page']");
		Wait.wait2Second();

		// if(driver.getCurrentUrl().equ)
		try {
			Wait.explicit_wait_absenceofelement("//div[@class='dvLoading']", driver);
			Wait.WaitForElement(driver, OR.New_Reservation_Button);
			Wait.explicit_wait_visibilityof_webelement_350(driver.findElement(By.xpath(OR.New_Reservation_Button)),
					driver);
			Wait.waitUntilPresenceOfElementLocated(OR.New_Reservation_Button, driver);
		} catch (Exception e) {
			driver.navigate().to(TestCore.envURL);// Overriding test-data url
													// value with jenkins build
													// parameter
			loginLogger.info("Entered application URL");

			wl.clientCode.sendKeys(Clientcode);
			loginLogger.info("Entered client code");
			Wait.wait1Second();
			wl.userID.sendKeys(UserID);
			Wait.wait1Second();
			loginLogger.info("Entered used id");
			wl.password.sendKeys(Password);
			loginLogger.info("Entered password");
			Wait.wait2Second();
			// Assert.assertEquals("x", "y");
			wl.Login.click();
			loginLogger.info("Clicked on login button");
			/*
			 * Wait.explicit_wait_absenceofelement("//div[@class='dvLoading']",
			 * driver); Wait.WaitForElement(driver, OR.New_Reservation_Button);
			 * Wait.explicit_wait_visibilityof_webelement_350(driver.findElement
			 * (By.xpath(OR.New_Reservation_Button)),driver);
			 * Wait.waitUntilPresenceOfElementLocated(OR.New_Reservation_Button,
			 * driver);
			 */
			Wait.WaitForElement(driver, OR.LoginModuleLoding);
			Wait.waitForElementToBeGone(driver, wl.LoginModuleLoding, 300);
			Wait.WaitForElement(driver, OR.New_Reservation_Button);
			;
			// Wait.waitUntilPresenceOfElementLocated("//small[.='Items Per
			// Page']");
			Wait.wait2Second();
		}
	}

	@Override
	public void BE_login(WebDriver driver, String URL) throws InterruptedException {

		WebElementsLogin wl = new WebElementsLogin(driver);
		driver.get(URL);
		Wait.wait5Second();

	}

	public void logout(WebDriver driver) throws InterruptedException {
		String name = "//div[@class='user_des']";
		Wait.WaitForElement(driver, name);
		Wait.waitForElementToBeVisibile(By.xpath(name), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(name)), driver);
		driver.findElement(By.xpath(name)).click();
		String logout = "//a[@id='logoutlink']/..";
		//Wait.waitForElementToBeVisibile(By.xpath(logout), driver);
		//Wait.WaitForElement(driver, logout);
		Wait.waitForElementByXpath(driver, logout);
		Utility.ScrollToElement(driver.findElement(By.xpath(logout)), driver);
		driver.findElement(By.xpath(logout)).click();
		//
		// JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("arguments[0].click();",
		// driver.findElement(By.xpath(logout)));
		// driver.findElement(By.xpath(logout)).click();
	}

	public boolean login_URL(WebDriver driver, String Clientcode, String UserID, String Password)
			throws InterruptedException {
boolean flag = false;
		WebElementsLogin wl = new WebElementsLogin(driver);
		// driver.get(url); // Overriding test-data url value with
		// jenkins build parameter
		// loginLogger.info("Entered application URL : " + url);
		Wait.WaitForElement_ID(driver, OR.clientCode);
		wl.clientCode.sendKeys(Clientcode);
		loginLogger.info("Entered client code");
		wl.userID.sendKeys(UserID);
		loginLogger.info("Entered used id");
		wl.password.sendKeys(Password);
		loginLogger.info("Entered password");
		Wait.wait3Second();
		// Assert.assertEquals("x", "y");
		wl.Login.click();
		loginLogger.info("Clicked on login button");
		
try {
		Wait.wait3Second();
	if(driver.findElement(By.id("lblLoginErrorMsg")).isDisplayed()) {
		
		flag = false;
		loginLogger.info("Status : " + false);
	}else {
		flag = true;
		loginLogger.info("Status : " + true);
	}
}catch (Exception e) {
	Wait.WaitForElement(driver, OR.LoginModuleLoding);
	Wait.waitForElementToBeGone(driver, wl.LoginModuleLoding, 300);
	Wait.WaitForElement(driver, OR.New_Reservation_Button);
	Wait.explicit_wait_visibilityof_webelement_600(driver.findElement(By.xpath(OR.New_Reservation_Button)), driver);
	flag = true;
	loginLogger.info("Status : " + true);
}
return flag;
	}
	public int getSubjectLineFromOTPMails(String subject, ArrayList<String> test_steps) throws Exception {
		Thread.sleep(15000);
		int count = 0;
		EmailUtils emailUtils = new EmailUtils(EmailUtils.EmailFolder.INBOX);
		Message[] msg = emailUtils.getMessagesBySubject(subject, false, 100);
		try {
			count = msg.length;
			if (count > 0) {
				test_steps.add("Subject from latest email is captured as :: " + subject);
				System.out.println("Subject from latest email is captured as :: " + subject);
			} else {
				test_steps.add("Subject from latest email is not get captured as ");
			}
		} catch (Exception e) {

		}
		return count;
	}
	public void loginAirbnbHost(WebDriver driver,String url, String email, String password) throws InterruptedException {
		Elements_Airbnb airbnb = new Elements_Airbnb();
		driver.get(url); // Overriding test-data url value with
        loginLogger.info("Entered application URL");
		Wait.waitForElementToBeVisibile(By.xpath(OR_Airbnb.ClickContinueWithEmail), driver);
		clickCookies(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Airbnb.ClickContinueWithEmail), driver);
		//Utility.ScrollToElement(airbnb.clickContinueWithEmail, driver);
		Utility.clickThroughJavaScript(driver,driver.findElement(By.xpath(OR_Airbnb.ClickContinueWithEmail)));
		//airbnb.clickContinueWithEmail.click();
		//Wait.waitForElementToBeVisibile(By.xpath(OR_Airbnb.EnterEmail), driver);
		airbnb.enterEmail.click();
		airbnb.enterEmail.sendKeys(email);
		airbnb.enterPassword.click();
		airbnb.enterPassword.sendKeys(password);
		Wait.waitForElementToBeClickable(By.xpath(OR_Airbnb.ClickLoginButton), driver);
		
	}
	public void clickCookies(WebDriver driver) {
		Elements_Airbnb airbnb = new Elements_Airbnb();
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Airbnb.AcceptCookiesButtonOK), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Airbnb.AcceptCookiesButtonOK), driver);
			//airbnb.acceptCookiesButtonOK.click();
			Utility.clickThroughJavaScript(driver, airbnb.acceptCookiesButtonOK);
		}catch(Exception e) {
			
		}
		
	}
}

