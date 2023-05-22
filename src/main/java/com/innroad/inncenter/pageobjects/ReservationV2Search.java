package com.innroad.inncenter.pageobjects;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_ReservationV2;
import com.innroad.inncenter.webelements.Elements_Reservation_SearchPage;

public class ReservationV2Search {
	

	ArrayList<String> test_steps = new ArrayList<>();
	public static Logger resSearchLogger = Logger.getLogger("ReservationSearch");
	public String basicSearch_WithResNumber(WebDriver driver, String resNumber) throws InterruptedException {
		String str = null;
		Wait.wait5Second();
		Elements_Reservation_SearchPage resservationSearch = new Elements_Reservation_SearchPage(driver);
		Wait.WaitForElement(driver, OR.Basic_Res_Number);
		Wait.explicit_wait_visibilityof_webelement(resservationSearch.Basic_Res_Number, driver);
		resservationSearch.Basic_Res_Number.click();
		test_steps.add("Click on Reservation Number Text Box");
		resSearchLogger.info("Click on Reservation Number Text Box");
		Utility.ScrollToElement(resservationSearch.Basic_Res_Number, driver);
		resservationSearch.Basic_Res_Number.clear();
		Wait.WaitForElement(driver, OR.Basic_Res_Number);
		resservationSearch.Basic_Res_Number.sendKeys(resNumber);
		resservationSearch.Click_BasicSearch.click();
		Wait.explicit_wait_xpath(OR.verify_Search_Loading_Gird, driver);
		Wait.WaitForElement(driver, OR.Get_Res_Number);
		Wait.waitUntilPresenceOfElementLocated(OR.Get_Res_Number, driver);
		String GetResNumber = resservationSearch.Get_Res_Number.getText();
		System.out.println(GetResNumber);
		if (GetResNumber == "") {
			Wait.explicit_wait_visibilityof_webelement(resservationSearch.Get_Res_Number, driver);
			GetResNumber = resservationSearch.Get_Res_Number.getText();
			System.out.println(GetResNumber);
		}
		Assert.assertEquals(resNumber, GetResNumber);
		// Explicit wait object creation
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.NewRervations)));
		String resLocator = "//span[contains(text(),'" + resNumber.trim() + "')]/../../td[4]/div/a";
		Wait.WaitForElement(driver, resLocator);
		str = driver.findElement(By.xpath(resLocator)).getText();
		Utility.scrollAndClick(driver, By.xpath(resLocator));
		resSearchLogger.info("Click on Reservation");
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation);
		return str;
	}


	
	public String basicSearchWithResNumber(WebDriver driver, String resNumber) throws InterruptedException {
		String str = null;
		Wait.wait5Second();
		Elements_Reservation_SearchPage resservationSearch = new Elements_Reservation_SearchPage(driver);
		Wait.WaitForElement(driver, OR.Basic_Res_Number);
		Wait.explicit_wait_visibilityof_webelement(resservationSearch.Basic_Res_Number, driver);
		resservationSearch.Basic_Res_Number.click();
		test_steps.add("Click on Reservation Number Text Box");
		resSearchLogger.info("Click on Reservation Number Text Box");
		Utility.ScrollToElement(resservationSearch.Basic_Res_Number, driver);
		resservationSearch.Basic_Res_Number.clear();
		Wait.WaitForElement(driver, OR.Basic_Res_Number);
		resservationSearch.Basic_Res_Number.sendKeys(resNumber);
		resservationSearch.Click_BasicSearch.click();
		Wait.explicit_wait_xpath(OR.verify_Search_Loading_Gird, driver);
		Wait.WaitForElement(driver, OR.Get_Res_Number);
		Wait.waitUntilPresenceOfElementLocated(OR.Get_Res_Number, driver);
		String GetResNumber = resservationSearch.Get_Res_Number.getText();
		System.out.println(GetResNumber);
		if (GetResNumber == "") {
			Wait.explicit_wait_visibilityof_webelement(resservationSearch.Get_Res_Number, driver);
			GetResNumber = resservationSearch.Get_Res_Number.getText();
			System.out.println(GetResNumber);
		}
		Assert.assertEquals(resNumber, GetResNumber);
		// Explicit wait object creation
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.NewRervations)));
		String resLocator = "//span[contains(text(),'" + resNumber.trim() + "')]/../../td[4]/div/a";
		Wait.WaitForElement(driver, resLocator);
		str = driver.findElement(By.xpath(resLocator)).getText();
		return str;
	}
	public void advanceLinkClick(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.ADVANCE_SEARCH_LINK, driver);
			Wait.explicit_wait_elementToBeClickable(elements.ADVANCE_SEARCH_LINK, driver);
			elements.ADVANCE_SEARCH_LINK.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.ADVANCE_SEARCH_LINK);
		}
		test_steps.add("Advance Link Clicked");
		resSearchLogger.info("Advance Link Clicked");

	}

	public void advanceSearchClick(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.ADVANCE_SEARCH_SEARCH_BUTTON, driver);
			Wait.explicit_wait_elementToBeClickable(elements.ADVANCE_SEARCH_SEARCH_BUTTON, driver);
			elements.ADVANCE_SEARCH_SEARCH_BUTTON.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.ADVANCE_SEARCH_SEARCH_BUTTON);
		}
		test_steps.add("Advance Search Button Clicked");
		resSearchLogger.info("Advance Search Button Clicked");

	}

	public void enterAccountNoInAdvanceSearch(WebDriver driver, String accountNo, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		ele.ADVANCE_SEARCH_ACCOUNT_NUMBER.clear();
		ele.ADVANCE_SEARCH_ACCOUNT_NUMBER.sendKeys(accountNo);
		resSearchLogger.info("Entered the account number for advance search");
		test_steps.add("Entered the account number for advance search");
	}

	public void advanceSearchedVerify(WebDriver driver, String guestName, boolean isOpen) throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		String foundText = ele.FIRST_SEARCHED_RES_CONFIRMATION_NO.getText();
		Utility.customAssert(foundText, guestName, true, "Successfully Verified Searched Reservation Matched", "Failed To Verify Searched Reservation", true, test_steps);
		
		Wait.waitForSweetLoading(driver);
		Wait.wait2Second();
		if(isOpen) {
			Utility.clickThroughJavaScript(driver, ele.FIRST_SEARCHED_RES);
			resSearchLogger.info("Clicked on Searched Reservaton");
		}
	}
}
