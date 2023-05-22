package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Inventory;
import com.innroad.inncenter.webelements.Elements_RoomMaintenance;
import com.innroad.inncenter.webelements.Elements_Users;

public class Users {

	public static Logger users = Logger.getLogger("RoomMaintenance");

	
	public void searchUser(WebDriver driver,String user){
		String name="//input[@placeholder='Login ID']";
		Wait.WaitForElement(driver, name);
		driver.findElement(By.xpath(name)).sendKeys(user);
	}
	
	public void search(WebDriver driver){
		String search="//button[contains(text(),'Search')]";
		Wait.WaitForElement(driver, search);
		driver.findElement(By.xpath(search)).click();
	}

	public String get_UserFirstName(WebDriver driver,String user){
		String Lname="//a[contains(text(),'"+user.trim()+"')]/../following-sibling::td[2]/span";
		Wait.WaitForElement(driver, Lname);
		return driver.findElement(By.xpath(Lname)).getText();
	}
	
	public String get_UserLastName(WebDriver driver,String user){
		String Fname="//a[contains(text(),'"+user.trim()+"')]/../following-sibling::td[1]/span";
		Wait.WaitForElement(driver, Fname);
		return driver.findElement(By.xpath(Fname)).getText();
	}
	
	public ArrayList<String> getAllUsersList(WebDriver driver, ArrayList<String> test_steps){
		
		ArrayList<String> usersList = new ArrayList<>();
		
		Elements_Users user = new Elements_Users(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Wait.explicit_wait_visibilityof_webelement(user.linkAllUsers, driver);
		
		user.linkAllUsers.click();
		Wait.explicit_wait_visibilityof_webelement(user.txtUsersList, driver);
		
		String strFirstName = "//table[@class='table table-striped table-bordered table-hover']//tr//td[3]/span";
		String strLastName = "//table[@class='table table-striped table-bordered table-hover']//tr//td[2]/span";
		
		List<WebElement> firstNames = driver.findElements(By.xpath(strFirstName));
		List<WebElement> lastNames = driver.findElements(By.xpath(strLastName));
		
		users.info(firstNames.size());
		users.info(lastNames.size());
		//List<WebElement> rowsUsers = driver.findElements(By.xpath("//table[@class='table table-striped table-bordered table-hover']//tr"));
		
		for (int i = 0; i < firstNames.size(); i++) {
			users.info(firstNames.get(i).getText());
			usersList.add(firstNames.get(i).getText()+" "+lastNames.get(i).getText());
		}
		
		return usersList;
	}

	public void selectUser(WebDriver driver, ArrayList<String> test_steps, String user) {
		String locator = String.format("//a[contains(text(),'%s')]", user);
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		users.info("Clicking on user " + user);
		test_steps.add("Clicking on user " + user);
		driver.findElement(By.xpath(locator)).click();
	}

	public void showFooter(WebDriver driver, ArrayList<String> test_steps, boolean isEnabled) {
		String locator = "//span[text()='Show Footer']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		String selectedOption = "Yes";
		if(!isEnabled) {
			selectedOption = "No";
		}
		By by = By.xpath(String.format(locator + "/option[text()='%s']", selectedOption));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement optionElement = driver.findElement(by);
		optionElement.click();
		element.click();
	}

	public void showLegend(WebDriver driver, ArrayList<String> test_steps, boolean isEnabled) {
		String locator = "//span[text()='Show Legend']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		String selectedOption = "Yes";
		if(!isEnabled) {
			selectedOption = "No";
		}
		By by = By.xpath(String.format(locator + "/option[text()='%s']", selectedOption));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement optionElement = driver.findElement(by);
		optionElement.click();
		element.click();
	}

	public void showRules(WebDriver driver, ArrayList<String> test_steps, boolean isEnabled) {
		String locator = "//span[text()='Show Rules']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		String selectedOption = "Yes";
		if(!isEnabled) {
			selectedOption = "No";
		}
		By by = By.xpath(String.format(locator + "/option[text()='%s']", selectedOption));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement optionElement = driver.findElement(by);
		optionElement.click();
		element.click();
	}

	public void showRoomClassData(WebDriver driver, ArrayList<String> test_steps, boolean isEnabled) {
		String locator = "//span[text()='Show Room Class Data']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		String selectedOption = "Yes";
		if(!isEnabled) {
			selectedOption = "No";
		}
		By by = By.xpath(String.format(locator + "/option[text()='%s']", selectedOption));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement optionElement = driver.findElement(by);
		optionElement.click();
		element.click();
	}

	public void groupByRoomClass(WebDriver driver, ArrayList<String> test_steps, boolean isEnabled) {
		String locator = "//span[text()='Group By Room Class']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		String selectedOption = "Yes";
		if(!isEnabled) {
			selectedOption = "No";
		}
		By by = By.xpath(String.format(locator + "/option[text()='%s']", selectedOption));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement optionElement = driver.findElement(by);
		optionElement.click();
		element.click();
	}

	public void showRoomCondition(WebDriver driver, ArrayList<String> test_steps, boolean isEnabled) {
		String locator = "//span[text()='Show Room Condition']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		String selectedOption = "Yes";
		if(!isEnabled) {
			selectedOption = "No";
		}
		By by = By.xpath(String.format(locator + "/option[text()='%s']", selectedOption));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement optionElement = driver.findElement(by);
		optionElement.click();
		element.click();
	}

	public void setDefaultTapeChartView(WebDriver driver, ArrayList<String> test_steps, int dayCount) {
		String locator = "//span[text()='Default Tape Chart View']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		String optionValue = dayCount + " Days";
		if(dayCount == 1) {
			optionValue = "1 Day";
		}
		By by = By.xpath(String.format(locator + "/option[text()='%s']", optionValue));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement optionElement = driver.findElement(by);
		optionElement.click();
		element.click();
	}

	public int getDefaultTapeChartView(WebDriver driver, ArrayList<String> test_steps) {
		String locator = "//span[text()='Default Tape Chart View']/parent::td/following-sibling::td/select";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return Integer.parseInt(((String) js.executeScript("return arguments[0].value", element)).trim());
	}

	public void saveUser(WebDriver driver, ArrayList<String> test_steps) {
		String locator = "//button[text()='Save']";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		Wait.waitForElementToBeVisibile(By.xpath("//div[@id='toast-container']"), driver);
	}
}