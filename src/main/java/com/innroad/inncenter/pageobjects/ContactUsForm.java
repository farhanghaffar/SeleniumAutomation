package com.innroad.inncenter.pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ContactUsForm {

	public boolean SubmitForm(WebDriver driver, String website) throws InterruptedException {

		boolean isFormShowing = false;
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		List<WebElement> contactUs = null;
		List<WebElement> anchorTag = driver.findElements(By.xpath("//a"));
		for (int i = 0; i < anchorTag.size(); i++) {
			Object elementAttributes = executor.executeScript(
					"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
					anchorTag.get(i));
			// Utility.ScrollToElement(listOfInputFields.get(i), driver);
			if (elementAttributes.toString().toLowerCase().contains("/contactus.")||
					elementAttributes.toString().toLowerCase().contains("/contact")) {
				System.out.println(elementAttributes.toString());
				driver.get(anchorTag.get(i).getAttribute("href"));
				isFormShowing = true;
				break;
			}

		}
		
		if (!isFormShowing) {
			contactUs = driver.findElements(By.xpath(
					"//*[contains(translate(text(), \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", \"abcdefghijklmnopqrstuvwxyz\"),\"contact us\")]"));
			for (WebElement webElement : contactUs) {

			String getAtt = webElement.getAttribute("href");
			if (getAtt!=null) {
				driver.get(getAtt);
				isFormShowing = true;
			}
			}
			}

		if (!isFormShowing) {

			contactUs = driver.findElements(By.xpath(
					"//*[contains(translate(text(), \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", \"abcdefghijklmnopqrstuvwxyz\"),\"contact\")]"));
			int totalFormsbeforeClick = driver.findElements(By.xpath("//form")).size();
			if (contactUs.size() > 0) {
				for (WebElement webElement : contactUs) {
					if (webElement.isDisplayed()) {
						Utility.ScrollToElement(webElement, driver);
						webElement.click();
						isFormShowing = true;
						break;

					}
				}

			}
		}
		if (!isFormShowing) {
			contactUs = driver.findElements(By.xpath("//*[contains(text(),'Contact')]//.."));
			for (int i = 0; i < contactUs.size(); i++) {
				String getAtt = contactUs.get(i).getAttribute("href");
				driver.get(getAtt);
				isFormShowing = true;
				break;
			}
		}

		
		Wait.wait5Second();
		return isFormShowing;
	}

	public void GetAttribute(WebDriver driver, String companyName, String badReview, ExtentTest test, String scriptName)
			throws InterruptedException {

		String firstName = "Jennifer";
		String lastName = "Nicole";
		String middleName = "Obrien";
		String email = "jennifer@dandydemo.com";
		String phoneNumber = "(949) 755-7782";
		String city = "CA";
		String state = "CA";
		String zipCode = "92618";
		String message = "Hi " + companyName + "," + "\r\n" + "\r\n"
				+ "Would you like a solution to consistently remove unfair bad reviews for " + companyName
				+ " like the " + badReview
				+ " bad reviews (1, 2 and 3 star) on your Google My Business page? At Dandy, our proprietary technology has helped companies like Holiday Inn and Wyndham remove over 40,000 negative reviews for their locations and consistently monitors and helps remove any new negative reviews that appear. \r\n"
				+ "\r\n"
				+ "Do you have 15 minutes for a call today? Schedule a Call Here: https://meetings.hubspot.com/dandy-/dandy-demo-nouman  \r\n"
				+ "\r\n"
				+ "Or -  for more information please visit our website: https://getdandy.com/review-removal-contact \r\n"
				+ "\r\n" + "You can also call me directly at (949) 755-7782.\r\n" + "\r\n"
				+ "You'll see a few case studies there - one of which we removed 250 negative reviews within the first 90 days for one of our customers. Remember, negative reviews can reduce traffic to your business by up to 15%. \r\n"
				+ "\r\n" + "Thank you, \r\n" + "\r\n" + "\r\n" + "Jennifer Obrien | Director of Reviews\r\n"
				+ "Dandy, Inc. - Remove, Prevent & Capture\r\n" + "Direct: (949) 755-7782\r\n"
				+ "9891 Irvine Center Drive, Suite #200\r\n" + "Irvine, CA 92618\r\n" + "";
		String address = "9891 Irvine Center Drive, Suite #200 Irvine, CA 92618";

		Wait.wait5Second();
		List<WebElement> listOfInputFields = driver.findElements(By.xpath("//input"));
		// here first name is checking

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		for (int i = 0; i < listOfInputFields.size(); i++) {
			if (listOfInputFields.get(i).isDisplayed()) {
				Object elementAttributes = executor.executeScript(
						"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
						listOfInputFields.get(i));

				Wait.wait1Second();

				if (elementAttributes.toString().toLowerCase().contains("firstname")
						|| elementAttributes.toString().toLowerCase().contains("first name")
						|| elementAttributes.toString().toLowerCase().contains("first_name")
						) {
					System.out.println("First name found");
					Utility.ScrollToElement(listOfInputFields.get(i), driver);
					listOfInputFields.get(i).sendKeys(firstName);
					continue;

				}
				
				if (elementAttributes.toString().toLowerCase().contains("your name")||
						elementAttributes.toString().toLowerCase().contains("your_ name")) {
					System.out.println("name found");
					 Utility.ScrollToElement(listOfInputFields.get(i), driver);
					listOfInputFields.get(i).sendKeys(firstName);
					continue;

				}


				if (elementAttributes.toString().toLowerCase().contains("lastname")
						|| elementAttributes.toString().toLowerCase().contains("last name")
						|| elementAttributes.toString().toLowerCase().contains("last_name")) {
					System.out.println("last name found");
					listOfInputFields.get(i).sendKeys(lastName);
					continue;

				}

				if (elementAttributes.toString().toLowerCase().contains("middlename")
						|| elementAttributes.toString().toLowerCase().contains("middle name")
						|| elementAttributes.toString().toLowerCase().contains("middle_name")) {
					System.out.println("middle name found");
					listOfInputFields.get(i).sendKeys(middleName);
					continue;

				}

				if (elementAttributes.toString().toLowerCase().contains("email")) {
					System.out.println("email found");
					listOfInputFields.get(i).sendKeys(email);
					continue;

				}

				if (elementAttributes.toString().toLowerCase().contains("phone")
						|| elementAttributes.toString().toLowerCase().contains("mobile")
						|| elementAttributes.toString().toLowerCase().contains("contact")) {
					System.out.println("phone found");
					listOfInputFields.get(i).sendKeys(phoneNumber);
					continue;

				}

				if (elementAttributes.toString().toLowerCase().contains("city")) {
					System.out.println("city found");
					listOfInputFields.get(i).sendKeys(city);
					continue;

				}
				if (elementAttributes.toString().toLowerCase().contains("zip")) {
					System.out.println("zip found");
					listOfInputFields.get(i).sendKeys(zipCode);
					continue;

				}

				if (elementAttributes.toString().toLowerCase().contains("state")) {
					System.out.println("state found");
					listOfInputFields.get(i).sendKeys(state);
					continue;

				}

				if (elementAttributes.toString().toLowerCase().contains("checkbox")) {
					List<WebElement> checkbox = driver.findElements(By.xpath("//input[@type='checkbox']//..//label"));

					for (int j = 0; j < checkbox.size(); j++) {
						if (checkbox.size() > 0) {
							checkbox.get(i).click();
						}
					}
					System.out.println("checkbox found");
					continue;
				}

			}

		}

		// here check select
		List<WebElement> listOfSelect = driver.findElements(By.xpath("//select"));

		for (int i = 0; i < listOfSelect.size(); i++) {
			if (listOfSelect.get(i).isDisplayed()) {
				Object elementAttributes = executor.executeScript(
						"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
						listOfSelect.get(i));
				if (elementAttributes.toString().toLowerCase().contains("state")) {
					Select dropdown = new Select(listOfSelect.get(i));
					try {
						dropdown.selectByValue(state);

					} catch (Exception e) {
						dropdown.selectByValue("California");

					}
					continue;

				} else {
					Select dropdown = new Select(listOfSelect.get(i));
					dropdown.selectByIndex(0);
					Wait.wait1Second();
					WebElement option = dropdown.getFirstSelectedOption();
					String defaultItem = option.getText();
					if (defaultItem.toLowerCase().contains("select")) {
						dropdown.selectByIndex(1);
					}
				}
			}
		}

		try {
			WebElement weTextArea = driver.findElement(By.xpath("//textarea"));
			weTextArea.sendKeys(message);
			
		} catch (Exception e) {
			List<WebElement> textArea = driver.findElements(By.xpath("//textarea"));
			for (int i = 0; i < textArea.size(); i++) {
				if (textArea.get(i).isDisplayed()) {
					textArea.get(i).sendKeys(message);
					break;
				}
			}
		}
		boolean isButtonClick = false;
		List<WebElement> buttonSubmitt = driver.findElements(By.xpath("//form//input[@type='submit']"));
		for (int i = 0; i < buttonSubmitt.size(); i++) {
			if (buttonSubmitt.get(i).isDisplayed()) {
				Object elementAttributes = executor.executeScript(
						"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
						buttonSubmitt.get(i));

				if (elementAttributes.toString().toLowerCase().contains("submit")) {
					Utility.ScrollToElement(buttonSubmitt.get(i), driver);
					Wait.wait2Second();
					try {
						 buttonSubmitt.get(i).click();

					} catch (Exception e) {
						executor.executeScript("arguments[0].click();", buttonSubmitt.get(i));
					}
					System.out.println("Click on submit button");
					isButtonClick = true;
					break;
				}

			}
		}

		if (!isButtonClick) {
			try {
				WebElement btnClick = driver.findElement(By.xpath("//button[contains(text(),'Send') or contains(text(),'send')]"));
				Utility.ScrollToElement(btnClick, driver);
				btnClick.click();
				isButtonClick = true;
				
			} catch (Exception e) {
				isButtonClick = false;
			}
			
		}
		 
		buttonSubmitt = driver.findElements(By.xpath("//input[@type='submit'] |//button"));
		if (!isButtonClick) {
		for (int i = 0; i < buttonSubmitt.size(); i++) {
			if (buttonSubmitt.get(i).isDisplayed()) {
				Object elementAttributes = executor.executeScript(
						"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
						buttonSubmitt.get(i));

				if (elementAttributes.toString().toLowerCase().contains("submit")) {
					Utility.ScrollToElement(buttonSubmitt.get(i), driver);
					 buttonSubmitt.get(i).click();
					System.out.println("Click on submit button");
					isButtonClick = true;
					break;
				}

			}
		}
		}

		
		Wait.wait2Second();
	}

}
