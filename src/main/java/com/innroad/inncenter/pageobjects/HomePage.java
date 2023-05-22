package com.innroad.inncenter.pageobjects;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class HomePage {
	
	public static Logger homeLogger = Logger.getLogger("HomePage");
	public void selectPropertiy(WebDriver driver,ArrayList<String> test_steps, String propertyName) throws InterruptedException {
		String settigIcon="//div[@class='support_panel_client_selector_open']";
		Wait.WaitForElement(driver, settigIcon);
		WebElement element= driver.findElement(By.xpath(settigIcon));
		Utility.ScrollToElement(element, driver);
		element.click();
		test_steps.add("Click Setting Icon");
		homeLogger.info("Click Setting Icon");
		String pencialIcon="(//span[@role='presentation']/b)[2]";
		Wait.WaitForElement(driver, pencialIcon);
		WebElement elementPencial= driver.findElement(By.xpath(pencialIcon));
		Utility.ScrollToElement(elementPencial, driver);
		elementPencial.click();
		test_steps.add("Click Pencial Icon");
		homeLogger.info("Click Pencial Icon");
		String searchBox="(//input[@role='combobox'])[2]";
		Wait.WaitForElement(driver, searchBox);
		WebElement elementSearchBox= driver.findElement(By.xpath(searchBox));
		Utility.ScrollToElement(elementSearchBox, driver);
		elementSearchBox.click();
		elementSearchBox.clear();
		test_steps.add("Click And Clear Search Box");
		homeLogger.info("Click And Clear Search Box");
		elementSearchBox.sendKeys(propertyName);
		test_steps.add("Entered Property Name: " +propertyName);
		homeLogger.info("Entered Property Name: " +propertyName);
		/*String propertiesList="//ul[@role='listbox']/li[@role='presentation']/div[contains(text(),'"+propertyName+"')]";*/
		String propertiesList="//ul[@role='listbox']/li[@role='presentation']/div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+propertyName.toUpperCase().trim()+"')]";
		Wait.WaitForElement(driver, propertiesList);
		WebElement elementProperList= driver.findElement(By.xpath(propertiesList));
		Utility.ScrollToElement(elementProperList, driver);
		elementProperList.click();
		test_steps.add("Click Setting Icon");
		homeLogger.info("Click Setting Icon");
		String loadingPath="//div[@class='dvLoading']";
		WebElement elementLoading= driver.findElement(By.xpath(loadingPath));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String  display=(String) jse.executeScript("return arguments[0].style.display;", elementLoading);
		homeLogger.info(display);
		int count = 0;
		try {
			homeLogger.info("in try");
			while (count < 20) {
				homeLogger.info(count);
				if (!elementLoading.isDisplayed()) {
					break;
				}
				count = count + 1;
				Wait.wait2Second();
			}
		} catch (Exception e) {
			homeLogger.info("in cathc");
		}
	}

}
