package com.innroad.inncenter.pageobjects;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.innroad.inncenter.pages.NightlyRatePage;
import com.innroad.inncenter.pages.RateGridPage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_NightlyRatePlan;
import com.innroad.inncenter.properties.OR_RateGrid;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_DerivedRate;
import com.innroad.inncenter.webelements.Elements_NightlyRate;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.webelements.WebElementsOverview;

public class NightlyRate {

	public static Logger logger = Logger.getLogger("NightlyRate");
	public static boolean allPoliciesAreAlreadyChecked = true;
	public static boolean allPoliciesAreAlreadyUnChecked = true;

	// Updated by Naveen
	public void enterRatePlanName(WebDriver driver, String rateName, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.RATE_PLAN_NAME), driver);
		// Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME,
		// driver);
		elements.RATE_PLAN_NAME.clear();
		Utility.clearValue(driver, elements.RATE_PLAN_NAME);
		elements.RATE_PLAN_NAME.sendKeys(rateName);
		test_steps.add("Entering Rate Plan Name as : <b>" + rateName + "</b>");
		logger.info("Entered Rate Plan Name : " + rateName);
	}

	public void verifyRatePlanNameVisibility(WebDriver driver, boolean isEnabled, boolean isDisplayed,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME, driver);
		assertEquals(elements.RATE_PLAN_NAME.isDisplayed(), isDisplayed, "Failed To Verify Rate Plan Name Display");
		test_steps.add("Successfully Verified Rate Plan Name is Displayed : " + isDisplayed);
		logger.info("Successfully Verified Rate Plan Name is Displayed : " + isDisplayed);

		assertEquals(elements.RATE_PLAN_NAME.isEnabled(), isEnabled, "Failed To Verify Rate Plan Name Enable");
		test_steps.add("Successfully Verified Rate Plan Name Enable : " + isEnabled);
		logger.info("Successfully Verified Rate Plan Name Enable : " + isEnabled);
	}

	public void verifyRatePlanRequiredFeild(WebDriver driver, String ratePlanName, boolean isRateAlreadyExistError,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		String errorTxt = "Rate Plan Name cannot be empty";

		if (isRateAlreadyExistError) {
			errorTxt = "A rate plan with this name already exists. Please choose a different name.";
			Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME, driver);
			enterRatePlanName(driver, ratePlanName, test_steps);
			elements.RATE_PLAN_NAME.sendKeys(Keys.TAB);
			assertTrue(elements.RATE_PLAN_NAME_ERROR_TEXT.isDisplayed(),
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Rate Plan Name Error Text Displayed ");
			logger.info("Successfully Verified Rate Plan Name Error Text Displayed ");

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.getText(), errorTxt,
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());
			logger.info("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.findElement(By.xpath("./..")).getAttribute("class"),
					"ratePlanNameErrorText", "Failed To Verify text in Red Color");
			test_steps.add("Successfully Verified Rate Plan Name Error Text is in Red Color");
			logger.info("Successfully Verified Rate Plan Name Error Text is in Red Color");
			verifyNextButton(driver, false, true, test_steps);
			Utility.clearValue(driver, elements.RATE_PLAN_NAME);
		} else {

			Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME, driver);
			enterRatePlanName(driver, ratePlanName, test_steps);
			elements.RATE_PLAN_NAME.clear();

			Utility.clearValue(driver, elements.RATE_PLAN_NAME);
			test_steps.add("Entered Rate Plan Name Cleared");
			logger.info("Entered Rate Plan Name Cleared");

			verifyRatePlanNameErrorTxt(driver, errorTxt, true, test_steps);
			verifyNextButton(driver, false, true, test_steps);
			verifyRatePlanNameFeildValue(driver, ratePlanName, false, test_steps);
			enterRatePlanName(driver, ratePlanName, test_steps);
			verifyRatePlanNameErrorTxt(driver, errorTxt, false, test_steps);
			verifyRatePlanNameFeildValue(driver, ratePlanName, true, test_steps);
		}
	}

	public void verifyRatePlanNameErrorTxt(WebDriver driver, String errorTxt, boolean isDisplayed,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Utility.clearValue(driver, elements.RATE_PLAN_NAME);

		if (isDisplayed) {
			assertTrue(elements.RATE_PLAN_NAME.getAttribute("class").toUpperCase().contains("REQUIRED"),
					"Failed To Verify RatPlan Name Feild in Red Color");
			test_steps.add("Successfully Verified Rate Plan Name Feild is in Red Color");
			logger.info("Successfully Verified Rate Plan Name Feild is in Red Color");

			assertTrue(elements.RATE_PLAN_NAME_ERROR_TEXT.isDisplayed(),
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Rate Plan Name Error Text Displayed ");
			logger.info("Successfully Verified Rate Plan Name Error Text Displayed ");

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.getText(), errorTxt,
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());
			logger.info("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.findElement(By.xpath("./..")).getAttribute("class"),
					"ratePlanNameErrorText", "Failed To Verify text in Red Color");
			test_steps.add("Successfully Verified Rate Plan Name Error Text is in Red Color");
			logger.info("Successfully Verified Rate Plan Name Error Text is in Red Color");
		} else {
			try {
				assertTrue(!elements.RATE_PLAN_NAME_ERROR_TEXT.isDisplayed(),
						"Failed To Verify Rate Plan Name Error Text Displayed");
				test_steps.add("Successfully Verified Rate Plan Name Error Text not Displayed ");
				logger.info("Successfully Verified Rate Plan Name Error Text not Displayed");
			} catch (Exception e) {
				test_steps.add("Successfully Verified Rate Plan Name Error Text not Displayed ");
				logger.info("Successfully Verified Rate Plan Name Error Text not Displayed");
			} catch (AssertionError e) {
				test_steps.add("Successfully Verified Rate Plan Name Error Text not Displayed ");
				logger.info("Successfully Verified Rate Plan Name Error Text not Displayed");
			}

		}
	}

	public void verifyRatePlanNameFeildValue(WebDriver driver, String expectedValue, boolean expectedEqual,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		String foundValue = elements.RATE_PLAN_NAME.getAttribute("value");
		if (expectedEqual) {
			assertEquals(foundValue, expectedValue, "Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified Rate Plan Name Value Matched : " + foundValue);
			logger.info("Successfully Verified Rate Plan Name Value Matched : " + foundValue);

			Actions builder = new Actions(driver);
			builder.moveToElement(elements.RATE_PLAN_NAME).perform();

			assertEquals(elements.RATE_PLAN_NAME.getAttribute("title"), expectedValue,
					"Failed To Verify tooltip value Missmatched");
			test_steps.add("Successfully Verified Rate Plan Name tool tip Value Matched : "
					+ elements.RATE_PLAN_NAME.getAttribute("title"));
			logger.info("Successfully Verified Rate Plan Name tool tip Value Matched : "
					+ elements.RATE_PLAN_NAME.getAttribute("title"));

		} else {
			test_steps.add("Rate Plan Name Value : " + foundValue);
			logger.info("Rate Plan Name Value : " + foundValue);
			assertNotEquals(foundValue, expectedValue, "Failed To Verify Value Matched");
			test_steps.add("Successfully Verified Rate Plan Name Value Not Matched");
			logger.info("Successfully Verified Rate Plan Name Value Not Matched ");
		}

	}

	public int getRatePlanInputFeiledLength(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		return elements.RATE_PLAN_NAME.getAttribute("value").length();
	}

	// Rate Plan Folio Display Name
	// Updated by Naveen
	public void enterRateFolioDisplayName(WebDriver driver, String folioDisplayName, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME), driver);
		// Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_FOLIO_DISPLAY_NAME,
		// driver);
		elements.RATE_PLAN_FOLIO_DISPLAY_NAME.clear();
		Utility.clearValue(driver, elements.RATE_PLAN_FOLIO_DISPLAY_NAME);
		elements.RATE_PLAN_FOLIO_DISPLAY_NAME.sendKeys(folioDisplayName);
		test_steps.add("Entering Rate Plan Folio Display Name as : <b>" + folioDisplayName + "</b>");
		logger.info("Entered Rate Plan Folio Display Name : " + folioDisplayName);
	}

	public void verifyRatePlanFolioDisplayNameVisibility(WebDriver driver, boolean isEnabled, boolean isDisplayed,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_FOLIO_DISPLAY_NAME, driver);
		assertEquals(elements.RATE_PLAN_FOLIO_DISPLAY_NAME.isDisplayed(), isDisplayed,
				"Failed To Verify Rate Plan Folio Display Name Displayed");
		test_steps.add("Successfully Verified Rate Plan Folio Display  Name is Displayed : " + isDisplayed);
		logger.info("Successfully Verified Rate Plan Folio Display Name is Displayed : " + isDisplayed);

		assertEquals(elements.RATE_PLAN_FOLIO_DISPLAY_NAME.isEnabled(), isEnabled,
				"Failed To Verify Rate Plan Folio Display Name Enable");
		test_steps.add("Successfully Verified Rate Plan Folio Display Name Enable : " + isEnabled);
		logger.info("Successfully Verified Rate Plan Folio Display Name Enable : " + isEnabled);
	}

	public void verifyRatePlanFolioDisplayNameRequiredFeild(WebDriver driver, String ratePlanFolioDisplayName,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_FOLIO_DISPLAY_NAME, driver);
		enterRateFolioDisplayName(driver, ratePlanFolioDisplayName, test_steps);
		elements.RATE_PLAN_FOLIO_DISPLAY_NAME.clear();

		Utility.clearValue(driver, elements.RATE_PLAN_FOLIO_DISPLAY_NAME);
		test_steps.add("Entered Rate Plan Folio Display Name Cleared");
		logger.info("Entered Rate Plan Folio Display Name Cleared");

		verifyRatePlanFolioDisplayNameErrorTxt(driver, true, test_steps);
		enterRateFolioDisplayName(driver, ratePlanFolioDisplayName, test_steps);
		verifyRatePlanFolioDisplayNameErrorTxt(driver, false, test_steps);
	}

	public void verifyRatePlanFolioDisplayNameErrorTxt(WebDriver driver, boolean isDisplayed,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		if (isDisplayed) {
			assertTrue(elements.RATE_PLAN_FOLIO_DISPLAY_NAME.getAttribute("class").toUpperCase().contains("REQUIRED"),
					"Failed To Verify RatPlan Name Feild in Red Color");
			test_steps.add("Successfully Verified Rate Plan Folio Display Name Feild is in Red Color");
			logger.info("Successfully Verified Rate Plan Folio Display Name Feild is in Red Color");

			assertTrue(elements.RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT.isDisplayed(),
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Rate Plan Folio Display Name Error Text Displayed : "
					+ elements.RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT.getText());
			logger.info("Successfully Verified Rate Plan Folio Display Name Error Text Displayed : "
					+ elements.RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT.getText());

			assertEquals(elements.RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT.findElement(By.xpath("./.."))
					.getAttribute("class"), "ratePlanNameErrorText", "Failed To Verify text in Red Color");
			test_steps.add("Successfully Verified Rate Plan Folio Display Name Error Text is in Red Color");
			logger.info("Successfully Verified Rate Plan Folio Display Name Error Text is in Red Color");
		} else {
			try {
				assertTrue(!elements.RATE_PLAN_FOLIO_DISPLAY_NAME_ERROR_TEXT.isDisplayed(),
						"Failed To Verify Rate Plan Folio Display Name Error Text Displayed");
				test_steps.add("Successfully Verified Rate Plan Folio Display Name Error Text not Displayed ");
				logger.info("Successfully Verified Rate Plan Folio Display Name Error Text not Displayed");
			} catch (Exception e) {
				test_steps.add("Successfully Verified Rate Plan Name Error Text not Displayed ");
				logger.info("Successfully Verified Rate Plan Folio Display Name Error Text not Displayed");
			}
		}
	}

	public void verifyRatePlanFolioDisplayNameFeildValue(WebDriver driver, String expectedValue, boolean expectedEqual,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		String foundValue = elements.RATE_PLAN_FOLIO_DISPLAY_NAME.getAttribute("value");
		if (expectedEqual) {
			assertEquals(foundValue, expectedValue, "Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified Rate Plan Folio Display Name Value Matched : " + foundValue);
			logger.info("Successfully Verified Rate Plan Folio Display Name Value Matched : " + foundValue);

			Actions builder = new Actions(driver);
			builder.moveToElement(elements.RATE_PLAN_FOLIO_DISPLAY_NAME).perform();

			assertEquals(elements.RATE_PLAN_FOLIO_DISPLAY_NAME.getAttribute("title"), expectedValue,
					"Failed To Verify tooltip value Missmatched");
			test_steps.add("Successfully Verified Rate Plan Folio Display Name tool tip Value Matched : "
					+ elements.RATE_PLAN_FOLIO_DISPLAY_NAME.getAttribute("title"));
			logger.info("Successfully Verified Rate Plan Folio Display Name tool tip Value Matched : "
					+ elements.RATE_PLAN_FOLIO_DISPLAY_NAME.getAttribute("title"));
		} else {

			test_steps.add("Rate Plan Folio Display Name Value  : " + foundValue);
			logger.info("Rate Plan Folio Display Name Value : " + foundValue);
			assertNotEquals(foundValue, expectedValue, "Failed To Verify Value Matched");
			test_steps.add("Successfully Verified Rate Plan Folio Display Name Value Not Matched");
			logger.info("Successfully Verified Rate Plan Folio Display Name Value Not Matched ");
		}

	}

	public int getRatePlanFolioDisplayNameInputFeiledLength(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		return elements.RATE_PLAN_FOLIO_DISPLAY_NAME.getAttribute("value").length();
	}

	// Rate Plan Description

	public void enterRatePlanDescription(WebDriver driver, String description, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_DESCRIPTION, driver);

		elements.RATE_PLAN_DESCRIPTION.clear();
		Utility.clearValue(driver, elements.RATE_PLAN_DESCRIPTION);
		elements.RATE_PLAN_DESCRIPTION.sendKeys(description);
		test_steps.add("Entered Rate Plan Description : " + description);
		logger.info("Entered Rate Plan Description : " + description);
	}

	public void verifyRatePlanDescriptionVisibility(WebDriver driver, boolean isEnabled, boolean isDisplayed,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_DESCRIPTION, driver);
		assertEquals(elements.RATE_PLAN_DESCRIPTION.isDisplayed(), isDisplayed,
				"Failed To Verify Rate Plan Description Display");
		test_steps.add("Successfully Verified Rate Plan Description is Displayed : " + isDisplayed);
		logger.info("Successfully Verified Rate Plan Description is Displayed : " + isDisplayed);

		assertEquals(elements.RATE_PLAN_DESCRIPTION.isEnabled(), isEnabled,
				"Failed To Verify Rate Plan Description Enable");
		test_steps.add("Successfully Verified Rate Plan Description Enable : " + isEnabled);
		logger.info("Successfully Verified Rate Plan Description Enable : " + isEnabled);
	}

	public void verifyRatePlanDescriptionFeildValue(WebDriver driver, String expectedValue, boolean expectedEqual,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		String foundValue = elements.RATE_PLAN_DESCRIPTION.getAttribute("value");
		if (expectedEqual) {
			assertEquals(foundValue, expectedValue, "Failed To Verify VAlue Missmatched");
			test_steps.add("Successfully Verified Rate Plan Description Value Matched : " + foundValue);
			logger.info("Successfully Verified Rate Plan Description Value Matched : " + foundValue);
		} else {

			test_steps.add("Rate Plan Description Value : " + foundValue);
			logger.info("Rate Plan Description Value : " + foundValue);
			assertNotEquals(foundValue, expectedValue, "Failed To Verify Value Matched");
			test_steps.add("Successfully Verified Rate Plan Description Value Not Matched");
			logger.info("Successfully Verified Rate Plan Description Value Not Matched ");
		}

	}

	public int getRatePlanDescriptionInputFeiledLength(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		return elements.RATE_PLAN_DESCRIPTION.getAttribute("value").length();
	}

	public String getCharCountRatePlanDescription(WebDriver driver) {
		return driver.findElement(By.xpath("//span[@class='char-count']")).getText();
	}

	public void verifyCharCountRatePlanDescription(WebDriver driver, String expectedMaxLength,
			ArrayList<String> test_steps) {

		String found = getCharCountRatePlanDescription(driver);
		assertEquals(found, expectedMaxLength + " / 255", "Failed To Verify Description Max Length");
		test_steps.add("Successfully Verified Rate Plan Description Value Max Length : " + found);
		logger.info("Successfully Verified Rate Plan Description Value Max Length : " + found);
	}

	// Next Button

	public ArrayList<String> clickNextButton(WebDriver driver) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		try {
			Wait.waitForElementToBeClickable(By.xpath(NightlyRatePage.NEXT_BUTTON), driver);
			// Wait.explicit_wait_visibilityof_webelement_120(elements.NEXT_BUTTON,
			// driver);
			// Wait.explicit_wait_elementToBeClickable(elements.NEXT_BUTTON,
			// driver);
			elements.NEXT_BUTTON.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.NEXT_BUTTON);
		}
		testSteps.add("Next Button Clicked");
		logger.info("Next Button Clicked");
		return testSteps;

	}

	public void clickNextButton(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.NEXT_BUTTON, driver);
			Wait.explicit_wait_elementToBeClickable(elements.NEXT_BUTTON, driver);
			elements.NEXT_BUTTON.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.NEXT_BUTTON);
		}
		testSteps.add("Next Button Clicked");
		logger.info("Next Button Clicked");

	}

	public void verifyNextButton(WebDriver driver, boolean isEnabled, boolean isDisplayed, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement_120(elements.NEXT_BUTTON, driver);

		assertEquals(elements.NEXT_BUTTON.isDisplayed(), isDisplayed, "Failed To Verify Next Button is not Displayed");
		test_steps.add("Successfully Verified Next Button is Displayed : " + isDisplayed);
		logger.info("Successfully Verified Next Button is Displayed : " + isDisplayed);

		assertEquals(elements.NEXT_BUTTON.isEnabled(), isEnabled, "Failed To Verify Next Button Enable");
		test_steps.add("Successfully Verified Next Button Enable : " + isEnabled);
		logger.info("Successfully Verified Next Button Enable : " + isEnabled);

	}

	// Channels

	public void selectChannels(WebDriver driver, String channels, boolean isSelect, ArrayList<String> test_steps)
			throws InterruptedException {

		StringTokenizer token = new StringTokenizer(channels, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String channel = token.nextToken();
			if (channel.equalsIgnoreCase("All")) {
				channel = "Select All";
			}
			String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ channel.toUpperCase() + "']/preceding-sibling::span/input";

			if (isSelect) {
				// add scroll by farhan
				WebElement element;
				try {
					element = driver.findElement(By.xpath(path));
				} catch (Exception e) {
					path = "//span[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
							+ channel.toUpperCase() + "')]/preceding-sibling::span/input";
					element = driver.findElement(By.xpath(path));
				}
				Utility.ScrollToElement(element, driver);
				if (!driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(channel + " Channel Selected");
					logger.info(channel + " Channel Selected");
				} else {
					test_steps.add(channel + " Channel Already Selected");
					logger.info(channel + " Channel Already Selected");
				}
			} else {
				if (driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(channel + " Channel UnSelected");
					logger.info(channel + " Channel UnSelected");
				} else {
					test_steps.add(channel + " Channel Already UnSelected");
					logger.info(channel + " Channel Already UnSelected");
				}
			}

		}
	}

	public void verifySelectedChannels(WebDriver driver, String selectedChannels, boolean isSelected,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		StringTokenizer token = new StringTokenizer(selectedChannels, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String channel = token.nextToken();
			if (channel.equalsIgnoreCase("select all") || channel.equalsIgnoreCase("all")) {
				String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='SELECT ALL']/preceding-sibling::span/input";

				assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
						"FAiled To Verify Channel not Selected : " + channel);
				test_steps.add("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
				logger.info("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
				int size = elements.DISTRIBUTION_CHANNEL_LIST.size();
				for (int i = 0; i < size; i++) {

					verifySelectedChannels(driver, elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText(), isSelected,
							test_steps);
				}
			} else {
				String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
						+ channel.toUpperCase() + "']/preceding-sibling::span/input";

				assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
						"FAiled To Verify Channel not Selected : " + channel);
				test_steps.add("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
				logger.info("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
			}
		}
	}

	public void verifyDisplayedDistributionChannels(WebDriver driver, ArrayList<String> channelList,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		int size = elements.DISTRIBUTION_CHANNEL_LIST.size();
		assertEquals(size, channelList.size(), "Failed To Verify List Size with Active List Size");
		test_steps.add("Successfully Verified Distribution Channels List Size : " + size);
		logger.info("Successfully Verified Distribution Channels List Size : " + size);
		for (int i = 0; i < size; i++) {

			assertTrue(elements.DISTRIBUTION_CHANNEL_LIST.get(i).isDisplayed(),
					"Failed To Verify Distribution Channels : " + elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText()
							+ " is not Displayed");
			test_steps.add("Successfully Verified Distribution Channels is Displayed : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());
			logger.info("Successfully Verified Distribution Channels is Displayed : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());

			assertEquals(elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText().toUpperCase(),
					channelList.get(i).toUpperCase(), "Failed To Verify Displayed Distribution Channels");
			test_steps.add("Successfully Verified Distribution Channels : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());
			logger.info("Successfully Verified Distribution Channels : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());

			verifyChannelsSelectable(driver, elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText(), test_steps);
		}
	}

	public void verifyChannelsSelectable(WebDriver driver, String channelName, ArrayList<String> test_steps) {
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ channelName.toUpperCase() + "']/preceding-sibling::span";

		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
				"Failed To Verify " + channelName + " Selectable is not Displayed");
		test_steps.add("Successfully Verified Channel Radio/CheckBox is Displayed : " + channelName);
		logger.info("Successfully Verified Channel Radio/CheckBox is Displayed : " + channelName);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
				"Failed To Verify " + channelName + " Selectable is not Enabled");
		test_steps.add("Successfully Verified Channel Radio/CheckBox is Enabled : " + channelName);
		logger.info("Successfully Verified Channel Radio/CheckBox is Enabled : " + channelName);
	}

	// RoomClasses

	public void selectRoomClasses(WebDriver driver, String roomClasses, boolean isSelect, ArrayList<String> test_steps)
			throws InterruptedException {

		StringTokenizer token = new StringTokenizer(roomClasses, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String roomClass = token.nextToken();
			if (roomClass.equalsIgnoreCase("All")) {
				roomClass = "Select All";
			}
			String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ roomClass.toUpperCase() + "']/preceding-sibling::span/input";
			logger.info("path of room class: " + path);
			if (isSelect) {
				WebElement element = driver.findElement(By.xpath(path));
				Utility.ScrollToElement(element, driver);
				if (!driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(roomClass + " Room Class Selected");
					logger.info(roomClass + " Room Class Selected");
				} else {
					test_steps.add(roomClass + " Room Class Already Selected");
					logger.info(roomClass + " Room Class Already Selected");
				}
			} else {
				if (driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(roomClass + " Room Class UnSelected");
					logger.info(roomClass + " Room Class UnSelected");
				} else {
					test_steps.add(roomClass + " Room Class Already UnSelected");
					logger.info(roomClass + " Room Class Already UnSelected");
				}
			}

		}
	}

	public void verifySelectedRoomClasses(WebDriver driver, String selectedRoomClasses, boolean isSelected,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		StringTokenizer token = new StringTokenizer(selectedRoomClasses, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String roomClass = token.nextToken();
			if (roomClass.equalsIgnoreCase("select all") || roomClass.equalsIgnoreCase("all")) {
				String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='SELECT ALL']/preceding-sibling::span/input";
				assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
						"Failed To Verify Room Class not Selected : " + roomClass + " ");
				test_steps.add("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
				logger.info("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
				int size = elements.ROOMCLASSES_LIST.size();
				for (int i = 0; i < size; i++) {
					verifySelectedRoomClasses(driver, elements.ROOMCLASSES_LIST.get(i).getText(), isSelected,
							test_steps);
				}
			} else {
				String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
						+ roomClass.toUpperCase() + "']/preceding-sibling::span/input";
				assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
						"Failed To Verify Room Class not Selected : " + roomClass + " ");
				test_steps.add("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
				logger.info("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
			}
		}
	}

	public void verifyDisplayedRoomClasses(WebDriver driver, ArrayList<String> roomClassList,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		int size = elements.ROOMCLASSES_LIST.size();
		assertEquals(size, roomClassList.size(), "Failed To Verify List Size with Active List Size");
		test_steps.add("Successfully Verified Room Class List Size : " + size);
		logger.info("Successfully Verified Room Class List Size : " + size);
		for (int i = 0; i < size; i++) {

			assertTrue(elements.ROOMCLASSES_LIST.get(i).isDisplayed(), "Failed To Verify Room Class : "
					+ elements.ROOMCLASSES_LIST.get(i).getText() + " is not Displayed");
			test_steps.add(
					"Successfully Verified Room Class is Displayed : " + elements.ROOMCLASSES_LIST.get(i).getText());
			logger.info(
					"Successfully Verified Room Class is Displayed : " + elements.ROOMCLASSES_LIST.get(i).getText());

			assertEquals(elements.ROOMCLASSES_LIST.get(i).getText().toUpperCase(), roomClassList.get(i).toUpperCase(),
					"Failed To Verify Displayed RoomClass");
			test_steps.add("Successfully Verified Room Class : " + elements.ROOMCLASSES_LIST.get(i).getText());
			logger.info("Successfully Verified Room Class : " + elements.ROOMCLASSES_LIST.get(i).getText());

			verifyRoomClassSelectable(driver, elements.ROOMCLASSES_LIST.get(i).getText(), test_steps);
		}
	}

	public void verifyRoomClassSelectable(WebDriver driver, String RoomClass, ArrayList<String> test_steps) {
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ RoomClass.toUpperCase() + "']/preceding-sibling::span";

		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
				"Failed To Verify " + RoomClass + " Selectable is not Displayed");
		test_steps.add("Successfully Verified RoomClass CheckBox is Displayed : " + RoomClass);
		logger.info("Successfully Verified RoomClass CheckBox is Displayed : " + RoomClass);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
				"Failed To Verify " + RoomClass + " Selectable is not Enabled");
		test_steps.add("Successfully Verified RoomClass CheckBox is Enabled : " + RoomClass);
		logger.info("Successfully Verified RoomClass CheckBox is Enabled : " + RoomClass);
	}

	// Restrictions

	public void selectRestrictionTypes(WebDriver driver, String restrictionTypes, boolean isSelect,
			ArrayList<String> test_steps) {
		StringTokenizer token = new StringTokenizer(restrictionTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String restriction = token.nextToken();

			String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ restriction.toUpperCase() + "']/preceding-sibling::span/input";
			if (isSelect) {
				if (!driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(restriction + " Restriction Selected");
					logger.info(restriction + " Restriction Selected");
				} else {
					test_steps.add(restriction + " Restriction Already Selected");
					logger.info(restriction + " Restriction Already Selected");
				}
			} else {
				if (driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(restriction + " Restriction UnSelected");
					logger.info(restriction + " Restriction UnSelected");
				} else {
					test_steps.add(restriction + " Restriction Already UnSelected");
					logger.info(restriction + " Restriction Already UnSelected");
				}
			}
			verifySelectedRestriction(driver, restriction, isSelect, test_steps);
		}
	}

	public ArrayList<String> selectRestrictionTypes(WebDriver driver, String restrictionTypes, boolean isSelect) {
		ArrayList<String> testSteps = new ArrayList<>();

		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ restrictionTypes.toUpperCase() + "']/preceding-sibling::span/input";
		if (isSelect) {
			if (!driver.findElement(By.xpath(path)).isSelected()) {
				try {
					driver.findElement(By.xpath(path)).click();
				} catch (Exception e) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
				}
				testSteps.add(restrictionTypes + " Restriction Selected");
				logger.info(restrictionTypes + " Restriction Selected");
			} else {
				testSteps.add(restrictionTypes + " Restriction Already Selected");
				logger.info(restrictionTypes + " Restriction Already Selected");
			}
		} else {
			if (driver.findElement(By.xpath(path)).isSelected()) {
				try {
					driver.findElement(By.xpath(path)).click();
				} catch (Exception e) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
				}
				testSteps.add(restrictionTypes + " Restriction UnSelected");
				logger.info(restrictionTypes + " Restriction UnSelected");
			} else {
				testSteps.add(restrictionTypes + " Restriction Already UnSelected");
				logger.info(restrictionTypes + " Restriction Already UnSelected");
			}
		}
		verifySelectedRestriction(driver, restrictionTypes, isSelect, testSteps);
		return testSteps;
	}

	public void verifySelectedRestriction(WebDriver driver, String options, boolean isSelected,
			ArrayList<String> test_steps) {
		StringTokenizer token = new StringTokenizer(options, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String option = token.nextToken();

			String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ option.toUpperCase() + "']/preceding-sibling::span/input";

			assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
					"Failed To verify : " + option + " Selected");
			test_steps.add("Successfully Verified : " + option + " is Selected : " + isSelected);
			logger.info("Successfully Verified : " + option + " is Selected : " + isSelected);
		}

	}

	public void verifyRestrictionsTypesCheckBoxes(WebDriver driver, String restrictionTypes,
			ArrayList<String> test_steps) {
		StringTokenizer token = new StringTokenizer(restrictionTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String restriction = token.nextToken();

			String restrictionPath = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ restriction.toUpperCase() + "']";
			String inputPath = restrictionPath + "/preceding-sibling::span";

			assertTrue(driver.findElement(By.xpath(restrictionPath)).isDisplayed(), "Failed To Verify Restriction : "
					+ driver.findElement(By.xpath(restrictionPath)).getText() + " is not Displayed");
			test_steps.add("Successfully Verified Restriction is Displayed : "
					+ driver.findElement(By.xpath(restrictionPath)).getText());
			logger.info("Successfully Verified Restriction is Displayed : "
					+ driver.findElement(By.xpath(restrictionPath)).getText());

			assertEquals(driver.findElement(By.xpath(restrictionPath)).getText().toUpperCase(),
					restriction.toUpperCase(), "Failed To Verify Displayed Restriction Text");
			test_steps.add("Successfully Verified Restriction Text : "
					+ driver.findElement(By.xpath(restrictionPath)).getText());
			logger.info("Successfully Verified Restriction Text : "
					+ driver.findElement(By.xpath(restrictionPath)).getText());

			assertTrue(driver.findElement(By.xpath(inputPath)).isDisplayed(),
					"Failed To Verify Restriction CheckBox is not Displayed");
			test_steps.add("Successfully Verified Restriction CheckBox is Displayed");
			logger.info("Successfully Verified Restriction CheckBox is Displayed");

			assertTrue(driver.findElement(By.xpath(inputPath)).isEnabled(),
					"Failed To Verify Restriction CheckBox is not Enabled");
			test_steps.add("Successfully Verified Restriction CheckBox is Enabled");
			logger.info("Successfully Verified Restriction CheckBox is Enabled");
		}
	}

	public void lengthOfStay(WebDriver driver, String restrictionTypes, boolean isMinStay, String minNightsCount,
			boolean isMaxStay, String maxNightsCount, ArrayList<String> test_steps) {

		// ****************************
		// options Only for inputCounter method
		String minOption = "Min";
		String maxOption = "Max";
		// ****************************

		boolean isLengthOfStayReq = false;
		StringTokenizer token = new StringTokenizer(restrictionTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String restriction = token.nextToken();
			if (restriction.equalsIgnoreCase("Length of stay")) {
				isLengthOfStayReq = true;
			}
		}

		if (isLengthOfStayReq) {
			if (isMinStay) {
				selectRestrictionTypes(driver, minOption, isMinStay, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, minOption, isMinStay, test_steps);
				inputCounter(driver, minOption, minNightsCount, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, minOption, minNightsCount, isMinStay,
						test_steps);
			}

			if (isMaxStay) {
				selectRestrictionTypes(driver, maxOption, isMaxStay, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, maxOption, isMaxStay, test_steps);
				inputCounter(driver, maxOption, maxNightsCount, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, maxOption, maxNightsCount, isMaxStay,
						test_steps);
			}
		}
	}

	public ArrayList<String> lengthOfStay(WebDriver driver, String restrictionTypes, String isMinStayChecked,
			String minNightsCount, String isMaxStayChecked, String maxNightsCount) {
		ArrayList<String> testSteps = new ArrayList<>();
		String minOption = "Min";
		String maxOption = "Max";

		if (restrictionTypes.equalsIgnoreCase("Yes")) {
			testSteps.addAll(selectRestrictionTypes(driver, "Length of stay", true));
			if (isMinStayChecked.equalsIgnoreCase("Yes")) {
				boolean isMinStay = true;
				selectRestrictionTypes(driver, minOption, isMinStay, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, minOption, isMinStay, testSteps);
				inputCounter(driver, minOption, minNightsCount, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, minOption, minNightsCount, isMinStay,
						testSteps);
			}

			if (isMaxStayChecked.equalsIgnoreCase("Yes")) {
				boolean isMaxStay = true;
				selectRestrictionTypes(driver, maxOption, isMaxStay, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, maxOption, isMaxStay, testSteps);
				inputCounter(driver, maxOption, maxNightsCount, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, maxOption, maxNightsCount, isMaxStay,
						testSteps);
			}
		} else {
			testSteps.addAll(selectRestrictionTypes(driver, restrictionTypes, false));

		}
		return testSteps;
	}

	public void bookingWindow(WebDriver driver, String restrictionTypes, boolean isMoreThanDaysReq,
			String moreThanDaysCount, boolean isWithInDaysReq, String withInDaysCount, ArrayList<String> test_steps) {

		// ****************************
		// options Only for inputCounter method
		String moreThanOption = "More than";
		String withInOption = "Within";
		// ****************************

		boolean isBookingWindowReq = false;
		StringTokenizer token = new StringTokenizer(restrictionTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String restriction = token.nextToken();
			if (restriction.equalsIgnoreCase("Booking window")) {
				isBookingWindowReq = true;
			}
		}

		if (isBookingWindowReq) {
			if (isMoreThanDaysReq) {
				selectRestrictionTypes(driver, moreThanOption, isMoreThanDaysReq, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, moreThanOption, isMoreThanDaysReq, test_steps);
				inputCounter(driver, moreThanOption, moreThanDaysCount, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, moreThanOption, moreThanDaysCount,
						isMoreThanDaysReq, test_steps);
			}

			if (isWithInDaysReq) {
				selectRestrictionTypes(driver, withInOption, isWithInDaysReq, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, withInOption, isWithInDaysReq, test_steps);
				inputCounter(driver, withInOption, withInDaysCount, test_steps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, withInOption, withInDaysCount,
						isWithInDaysReq, test_steps);
			}
		}
	}

	public ArrayList<String> bookingWindow(WebDriver driver, String restrictionTypes, String isMoreThanDaysReqChekced,
			String moreThanDaysCount, String isWithInDaysReqChecked, String withInDaysCount) {
		String moreThanOption = "More than";
		String withInOption = "Within";
		ArrayList<String> testSteps = new ArrayList<>();

		if (restrictionTypes.equalsIgnoreCase("Yes")) {
			testSteps.addAll(selectRestrictionTypes(driver, "Booking window", true));
			if (isMoreThanDaysReqChekced.equalsIgnoreCase("Yes")) {
				boolean isMoreThanDaysReq = true;
				selectRestrictionTypes(driver, moreThanOption, isMoreThanDaysReq, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, moreThanOption, isMoreThanDaysReq, testSteps);
				inputCounter(driver, moreThanOption, moreThanDaysCount, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, moreThanOption, moreThanDaysCount,
						isMoreThanDaysReq, testSteps);
			}

			if (isWithInDaysReqChecked.equalsIgnoreCase("Yes")) {
				boolean isWithInDaysReq = true;
				selectRestrictionTypes(driver, withInOption, isWithInDaysReq, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeild(driver, withInOption, isWithInDaysReq, testSteps);
				inputCounter(driver, withInOption, withInDaysCount, testSteps);
				verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, withInOption, withInDaysCount,
						isWithInDaysReq, testSteps);
			}
		} else {
			testSteps.addAll(selectRestrictionTypes(driver, restrictionTypes, false));

		}
		return testSteps;
	}

	public void selectBookingWindow(WebDriver driver, ArrayList<String> test_steps, String moreThanDaysCount,
			String withInDaysCount, boolean edit) throws Exception {
		String moreThanOption = "More than";
		String withInOption = "Within";
		String bookingWindowCheckBox = "//span[text()='Booking window']/..//span[contains(@class,'ant-checkbox')]";
		Wait.waitForElementToBeVisibile(By.xpath(bookingWindowCheckBox), driver);
		WebElement bookingWindowCheckBoxWE = driver.findElement(By.xpath(bookingWindowCheckBox));
		if (edit) {
			bookingWindowCheckBoxWE.click();
			Thread.sleep(2000);
			bookingWindowCheckBoxWE.click();
		} else {
			bookingWindowCheckBoxWE.click();
		}
		if (Utility.validateString(moreThanDaysCount)) {
			selectRestrictionTypes(driver, moreThanOption, true, test_steps);
			verifyRestrictionDaysNightsCountSpinnerFeild(driver, moreThanOption, true, test_steps);
			inputCounter(driver, moreThanOption, moreThanDaysCount, test_steps);
			verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, moreThanOption, moreThanDaysCount, true,
					test_steps);
		}
		if (Utility.validateString(withInDaysCount)) {
			selectRestrictionTypes(driver, withInOption, true, test_steps);
			verifyRestrictionDaysNightsCountSpinnerFeild(driver, withInOption, true, test_steps);
			inputCounter(driver, withInOption, withInDaysCount, test_steps);
			verifyRestrictionDaysNightsCountSpinnerFeildValue(driver, withInOption, withInDaysCount, true, test_steps);
		}
	}

	/*
	 * 'Options are: 'Min', 'Max', 'More than', 'Within'
	 */
	private void inputCounter(WebDriver driver, String option, String count, ArrayList<String> test_steps) {
		String path = "//span[text()='" + option + "']/parent::label/parent::div/following-sibling::div//input";

		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);
		driver.findElement(By.xpath(path)).clear();
		Utility.clearValue(driver, driver.findElement(By.xpath(path)));
		driver.findElement(By.xpath(path)).sendKeys(count);
		test_steps.add("Entered Days/Nights Count : " + count);
		logger.info("Entered Days/Nights Count : " + count);
	}

	public void verifyRestrictionDaysNightsCountSpinnerFeildValue(WebDriver driver, String option, String expectedCount,
			boolean isExpected, ArrayList<String> test_steps) {
		String path = "//span[text()='" + option + "']/parent::label/parent::div/following-sibling::div//input";

		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);

		if (isExpected) {
			assertEquals(driver.findElement(By.xpath(path)).getAttribute("value"), expectedCount,
					"Failed To Verify" + option + " Days Night count Feild Value");
			test_steps.add(
					"Successfully Verified " + option + " Days Nights Count Spinner Feild Value : " + expectedCount);
			logger.info(
					"Successfully Verified " + option + " Days Nights Count Spinner Feild Value : " + expectedCount);
		} else {
			assertNotEquals(driver.findElement(By.xpath(path)).getAttribute("value"), expectedCount,
					"Failed To Verify" + option + " Days Night count Feild Value");
			test_steps.add("Successfully Verified " + option + " Days Nights Count Spinner Feild Value Not Matched ");
			logger.info("Successfully Verified " + option + " Days Nights Count Spinner Feild Value Not Matched ");
		}

	}

	public void verifyRestrictionDaysNightsCountSpinnerFeild(WebDriver driver, String restrictions, boolean isEnabled,
			ArrayList<String> test_steps) {
		StringTokenizer token = new StringTokenizer(restrictions, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String option = token.nextToken();

			String path = "//span[text()='" + option + "']/parent::label/parent::div/following-sibling::div//input";
			if (!driver.findElement(By.xpath(path)).isDisplayed()) {
				try {
					Wait.wait3Second();
				} catch (InterruptedException e) {

				}
			}
			assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
					"Failed to Verify " + option + " Days Night count Feild not Displayed");
			test_steps.add("Successfully Verified " + option + " Days Nights Count Spinner is Displayed");
			logger.info("Successfully Verified " + option + " Days Nights Count Spinner is Displayed");

			assertEquals(driver.findElement(By.xpath(path)).isEnabled(), isEnabled,
					"Failed to Verify " + option + " Days Night count Feild Enabled");
			test_steps.add("Successfully Verified " + option + " Days Nights Count Spinner Enabled : " + isEnabled);
			logger.info("Successfully Verified " + option + " Days Nights Count Spinner Enabled : " + isEnabled);
		}
	}

	public void promoCode(WebDriver driver, String restrictionTypes, String promoCode, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		boolean isPromoCodeReq = false;
		StringTokenizer token = new StringTokenizer(restrictionTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String restriction = token.nextToken();
			if (restriction.equalsIgnoreCase("Promo code")) {
				isPromoCodeReq = true;
			}
		}

		if (isPromoCodeReq) {
			verifyPromoCodeFeildVisibility(driver, true, isPromoCodeReq, test_steps);
			elements.PROMO_CODE.clear();
			Utility.clearValue(driver, elements.PROMO_CODE);
			elements.PROMO_CODE.sendKeys(promoCode);
			test_steps.add("Entered Promo Code : " + promoCode);
			logger.info("Entered Promo Code : " + promoCode);
			verifyPromoCodeFeildValue(driver, promoCode, isPromoCodeReq, test_steps);
		}
	}

	public ArrayList<String> promoCode(WebDriver driver, String restrictionTypes, String promoCode) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		if (restrictionTypes.equalsIgnoreCase("Yes")) {
			testSteps.addAll(selectRestrictionTypes(driver, "Promo code", true));
			elements.PROMO_CODE.clear();
			Utility.clearValue(driver, elements.PROMO_CODE);
			elements.PROMO_CODE.sendKeys(promoCode);
			testSteps.add("Entered Promo Code : " + promoCode);
			logger.info("Entered Promo Code : " + promoCode);
		} else {
			testSteps.addAll(selectRestrictionTypes(driver, restrictionTypes, false));
		}
		return testSteps;
	}

	public void verifyPromoCodeFeildVisibility(WebDriver driver, boolean isEnabled, boolean isDisplayed,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, NightlyRatePage.PROMO_CODE);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.PROMO_CODE), driver);
		assertEquals(elements.PROMO_CODE.isDisplayed(), isDisplayed, "Failed To Verify Promo Code Feild Displayed");
		test_steps.add("Successfully Verified PromoCode is Displayed : " + isDisplayed);
		logger.info("Successfully Verified PromoCode is Displayed" + isDisplayed);

		assertEquals(elements.PROMO_CODE.isEnabled(), isEnabled, "Failed To Verify Promo Code Feild Enabled");
		test_steps.add("Successfully Verified PromoCode is Enabled : " + isEnabled);
		logger.info("Successfully Verified PromoCode is Enabled : " + isEnabled);

		assertEquals(elements.PROMO_CODE.getAttribute("maxlength"), "50",
				"Failed To Verify Promo Code Feild Displayed");
		test_steps.add("Successfully Verified PromoCode maxlength : 50 ");
		logger.info("Successfully Verified PromoCode maxlength : 50");
	}

	public void verifyPromoCodeFeildValue(WebDriver driver, String expectedValue, boolean isExpected,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		String foundValue = elements.PROMO_CODE.getAttribute("value");
		if (isExpected) {
			assertEquals(foundValue, expectedValue, "Failed To Verify Value Missmatched");
			test_steps.add("Successfully Verified PromoCode Value Matched : " + foundValue);
			logger.info("Successfully Verified PromoCode Value Matched : " + foundValue);
		} else {
			assertNotEquals(foundValue, expectedValue, "Failed To Verify Value Matched");
			test_steps.add("Successfully Verified PromoCode Value Not Matched");
			logger.info("Successfully Verified PromoCode Value Not Matched ");
		}

	}

	public void verifyToolTipBookingWindow(WebDriver driver, ArrayList<String> test_steps) {
		try {
			String elePath = "//span[text()='Booking window']//span";
			WebElement hoverElement = driver.findElement(By.xpath(elePath));
			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			test_steps.add("MouseHover over a Booking Widnow SVG");
			logger.info("MouseHover over a Booking Widnow SVG");

			String expected = "Booking window does not map to external channels";
			String actual = getToolTipValue(driver);
			assertEquals(actual, expected, "Failed To Verify Booking Window Tool Tip Value");
			test_steps.add("Successfully Verified Booking Window ToolTip : " + expected);
			logger.info("Successfully Verified Booking Window ToolTip : " + expected);
		} catch (Exception e) {

		}
	}

	public void verifyToolTipPromoCode(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		try {
			String elePath = "//span[text()='Promo code']//span";
			WebElement hoverElement = driver.findElement(By.xpath(elePath));
			Wait.wait1Second();
			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			test_steps.add("MouseHover over a PromoCode SVG");
			logger.info("MouseHover over a PromoCode SVG");
			Wait.wait1Second();
			String expected = "Promo code does not map to external channels";
			String actual = getToolTipValue(driver);
			assertEquals(actual, expected, "Failed To Verify PromoCode Tool Tip Value");
			test_steps.add("Successfully Verified PromoCode ToolTip : " + expected);
			logger.info("Successfully Verified PromoCode ToolTip : " + expected);
		} catch (Exception e) {

		}
	}

	public String getToolTipValue(WebDriver driver) {
		String path = "//div[@class='ant-tooltip info-popover ant-tooltip-placement-top']//div[@class='modal-popover-content']";
		return driver.findElement(By.xpath(path)).getText();
	}

	public void selectRestrictions(WebDriver driver, boolean isRatePlanRistrictionReq, String RistrictionType,
			boolean isMinStay, String MinNights, boolean isMaxStay, String MaxNights, boolean isMoreThanDaysReq,
			String MoreThanDaysCount, boolean isWithInDaysReq, String WithInDaysCount, String PromoCode,
			ArrayList<String> test_steps) {
		if (isRatePlanRistrictionReq) {

			test_steps.add("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");
			logger.info("=================== SELECT RESTRICTIONS TO QUALIFY RATE ======================");
			logger.info(RistrictionType);
			selectRestrictionTypes(driver, RistrictionType, true, test_steps);
			lengthOfStay(driver, RistrictionType, isMinStay, MinNights, isMaxStay, MaxNights, test_steps);
			bookingWindow(driver, RistrictionType, isMoreThanDaysReq, MoreThanDaysCount, isWithInDaysReq,
					WithInDaysCount, test_steps);
			promoCode(driver, RistrictionType, PromoCode, test_steps);

		} else {
			test_steps.add("=================== NO RESTRICTIONS TO QUALIFY RATE ======================");
			logger.info("=================== NO RESTRICTIONS TO QUALIFY RATE ======================");
			selectRestrictionTypes(driver, RistrictionType, false, test_steps);
		}
	}

	// Policy

	public void selectPolicy(WebDriver driver, String policyTypes, boolean isSelect, ArrayList<String> test_steps) throws InterruptedException {
		StringTokenizer token = new StringTokenizer(policyTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String policy = token.nextToken();
			if (policy.equalsIgnoreCase("All")) {
				policy = "Select All";
			}
			test_steps.add("Sele policy");

			/*String path = "(//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ policy.toUpperCase() + "']/preceding-sibling::span/input)[2]";*/
		/*	String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ policy.toUpperCase() + "']/preceding-sibling::span/input";*/

			String path = "(//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ policy.toUpperCase() + "']/preceding-sibling::span/input)";

			logger.info("path: " + path);
			
			

			if (isSelect) {
				if (!driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						//Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
						Utility.scrollAndClick(driver, By.xpath(path));
					}
					test_steps.add(policy + " Policy Selected");
					logger.info(policy + " Policy Selected");
				} else {
					test_steps.add(policy + " Policy Already Selected");
					logger.info(policy + " Policy Already Selected");
				}
			} else {
				if (driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(policy + " Policy UnSelected");
					logger.info(policy + " Policy UnSelected");
				} else {
					test_steps.add(policy + " Policy Already UnSelected");
					logger.info(policy + " Policy Already UnSelected");
				}
			}
		}
	}

	public void selectPolicy(WebDriver driver, String PoliciesType, String PoliciesName, boolean isPolicesReq,
			ArrayList<String> test_steps) throws InterruptedException {
		if (isPolicesReq) {
			test_steps.add("=================== SELECT POLICIES ======================");
			logger.info("=================== SELECT POLICIES ======================");	
			Wait.wait5Second();
			selectPolicy(driver, PoliciesType, true, test_steps);
			Wait.wait5Second();
			selectPolicy(driver, PoliciesName, true, test_steps);
			Wait.wait5Second();

		} else {
			test_steps.add("=================== NO POLICIES ======================");
			logger.info("=================== NO POLICIES ======================");
			selectPolicy(driver, PoliciesType, false, test_steps);
		}
	}

	public void verifySelectedPolicy(WebDriver driver, String policyTypes, boolean isSelected,
			ArrayList<String> test_steps) {
		StringTokenizer token = new StringTokenizer(policyTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String policy = token.nextToken();
			if (policy.equalsIgnoreCase("All")) {
				policy = "Select All";
			}
			String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ policy.toUpperCase() + "']/preceding-sibling::span/input";

			assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
					"Failed To Verify Policy not Selected : " + policy);
			test_steps.add("Successfully Verified Policy " + policy + " is Selected : " + isSelected);
			logger.info("Successfully Verified Policy " + policy + " is Selected : " + isSelected);
		}
	}

	public String getPolicyDescription(WebDriver driver, String policyType, String policyName,
			ArrayList<String> test_steps) throws InterruptedException {
		selectPolicy(driver, policyType, true, test_steps);
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyName.toUpperCase() + "']/span[@class='stepSubText']";

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		return driver.findElement(By.xpath(path)).getText();
	}

	public String getPolicyDescription(WebDriver driver, String policyName, ArrayList<String> test_steps) {

		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyName.toUpperCase() + "']/span[@class='stepSubText']";

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		return driver.findElement(By.xpath(path)).getText();
	}

	public HashMap<String, String> getAllPolicyDescriptions(WebDriver driver, String policyNames, boolean isPolicyReq,
			ArrayList<String> test_steps) {

		HashMap<String, String> map = new HashMap<>();
		if (isPolicyReq) {
			StringTokenizer token = new StringTokenizer(policyNames, Utility.DELIM);
			while (token.hasMoreTokens()) {
				String policyName = token.nextToken();
				map.put(policyName, getPolicyDescription(driver, policyName, test_steps));
				// policyDesc.add();
			}
		} else {
			map.put("NO POLICIES", "No policies selected");
		}
		return map;
	}

	public void verifyInputPolicies(WebDriver driver, String policyName, ArrayList<String> test_steps) {
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyName.toUpperCase() + "']/preceding-sibling::span";

		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
				"Failed To Verify " + policyName + " Selectable is not Displayed");
		test_steps.add("Successfully Verified Policy Radio/CheckBox is Displayed : " + policyName);
		logger.info("Successfully Verified Policy Radio/CheckBox is Displayed : " + policyName);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
				"Failed To Verify " + policyName + " Selectable is not Enabled");
		test_steps.add("Successfully Verified Policy Radio/CheckBox is Enabled : " + policyName);
		logger.info("Successfully Verified Policy Radio/CheckBox is Enabled : " + policyName);
	}

	public void verifyAllPolicies(WebDriver driver, String policyType, ArrayList<String> policyList,
			ArrayList<String> test_steps) throws InterruptedException {
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyType.toUpperCase() + "']/../following-sibling::div//label/span[2]";
		selectPolicy(driver, policyType, true, test_steps);

		int size = driver.findElements(By.xpath(path)).size();
		assertEquals(size, policyList.size(), "Failed To Verify List Size with Active List Size");
		test_steps.add("Successfully Verified Policy List Size : " + size);
		logger.info("Successfully Verified Policy List Size : " + size);
		for (int i = 0; i < size; i++) {

			assertTrue(driver.findElements(By.xpath(path)).get(i).isDisplayed(), "Failed To Verify Policy : "
					+ driver.findElements(By.xpath(path)).get(i).getText() + " is not Displayed");
			test_steps.add("Successfully Verified Policy is Displayed : "
					+ driver.findElements(By.xpath(path)).get(i).getText());
			logger.info("Successfully Verified Policy is Displayed : "
					+ driver.findElements(By.xpath(path)).get(i).getText());

			assertTrue(driver.findElements(By.xpath(path)).get(i).getText().contains(policyList.get(i)),
					"Failed To Verify Displayed Policy expected[" + policyList.get(i) + "] but found["
							+ driver.findElements(By.xpath(path)).get(i).getText() + "]");
			test_steps.add("Successfully Verified Policy : " + policyList.get(i));
			logger.info("Successfully Verified Policy : " + policyList.get(i));

			verifyInputPolicies(driver, policyList.get(i), test_steps);
		}
		selectPolicy(driver, policyType, false, test_steps);
	}

	public String getSelectedPolicy(WebDriver driver, String policyType, ArrayList<String> test_steps) {
		String selectedPolicyName = "NA";

		String typePath = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyType.toUpperCase() + "']/preceding-sibling::span/input";

		String policyPath = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyType.toUpperCase()
				+ "']/../following-sibling::div//label/span[@class='ant-radio ant-radio-checked']";

		if (driver.findElement(By.xpath(typePath)).isSelected()) {
			// logger.info(policyType);
			for (WebElement ele : driver.findElements(By.xpath(policyPath))) {

				if (ele.isDisplayed()) {
					// logger.info("ele selected");
					selectedPolicyName = driver.findElement(By.xpath(
							"//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
									+ policyType.toUpperCase()
									+ "']/../following-sibling::div//label/span[@class='ant-radio ant-radio-checked']/following::span[1]"))
							.getText();
					logger.info(selectedPolicyName);
					String str[] = selectedPolicyName.split("\n");
					selectedPolicyName = str[0];
				}
			}
		}

		test_steps.add("Selected" + policyType + " Policy : " + selectedPolicyName);
		logger.info("Selected" + policyType + " Policy : " + selectedPolicyName);

		return selectedPolicyName;
	}

	public HashMap<String, String> getAllTypeSelectedPolicy(WebDriver driver, ArrayList<String> test_steps) {
		HashMap<String, String> typeWisePolicies = new HashMap<String, String>();

		typeWisePolicies.put("Cancellation", getSelectedPolicy(driver, "Cancellation", test_steps));
		typeWisePolicies.put("Deposit", getSelectedPolicy(driver, "Deposit", test_steps));
		typeWisePolicies.put("Check-in", getSelectedPolicy(driver, "Check-in", test_steps));
		typeWisePolicies.put("No Show", getSelectedPolicy(driver, "No Show", test_steps));

		return typeWisePolicies;
	}

	public String getRestrictionsToQualifyRate(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		String restrictions = null;
		try {
			Wait.explicit_wait_visibilityof_webelement(elements.RESTRICTIONS_TO_QUALIFY_RATE, driver);
			restrictions = elements.RESTRICTIONS_TO_QUALIFY_RATE.getText();
			if (restrictions.isEmpty() || restrictions == null) {
				restrictions = "No restrictions selected";
			}
		} catch (Exception e) {
			restrictions = "No restrictions selected";
		}

		test_steps.add("Restrictions are : " + restrictions);
		logger.info("Restrictions are : " + restrictions);

		return restrictions;
	}

	public void verfiyRestrictionsToQualifyRateMsg(WebDriver driver, String expectedMsg, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(elements.RESTRICTIONS_TO_QUALIFY_RATE, driver);

			assertTrue(elements.RESTRICTIONS_TO_QUALIFY_RATE.isDisplayed(),
					"Failed to Verify Restrictions To Qualify Rate Displayed");
			test_steps.add("Successfully Verified Restrictions To Qualify Rate Text : Displayed");
			logger.info("Successfully Verified Restrictions To Qualify Rate Text : Displayed");

			String found = elements.RESTRICTIONS_TO_QUALIFY_RATE.getText();
			assertEquals(found, expectedMsg, "Failed To Verify Restrictions To Qualify Rate Msg");
			test_steps.add("Successfully Verified Restrictions To Qualify Rate Text : " + found);
			logger.info("Successfully Verified Restrictions To Qualify Rate Text : " + found);
		} catch (Exception e) {
			assertTrue(expectedMsg.isEmpty(), "Failed To Verify Restrictions To Qualify Rate Msg");
			test_steps.add("Successfully Verified No Restrictions To Qualify Rate Text");
			logger.info("Successfully Verified No Restrictions To Qualify Rate Text ");
		}
	}

	public String generateRestrictionsToQualifyRate(String restrictionTypes, boolean isMin, String minNights,
			boolean isMax, String maxNights, boolean isMoreThan, String moreThanDays, boolean isWithInDaysReq,
			String withInDaysCount, String promoCode) {

		String restrictionMsg = "";

		boolean isLenghtOfStayReq = false;
		boolean isBookingWindowReq = false;
		boolean isPromoCodeReq = false;

		StringTokenizer token = new StringTokenizer(restrictionTypes, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String restriction = token.nextToken();
			if (restriction.equalsIgnoreCase("Length of stay")) {
				isLenghtOfStayReq = true;
			}
			if (restriction.equalsIgnoreCase("Booking window")) {
				isBookingWindowReq = true;
			}
			if (restriction.equalsIgnoreCase("Promo code")) {
				isPromoCodeReq = true;
			}
		}

		String lenghtOfStayMsg = "";
		if (isLenghtOfStayReq) {
			if (isMin && isMax) {
				if (Integer.parseInt(minNights) == Integer.parseInt(maxNights)) {
					lenghtOfStayMsg = "Guests must stay " + Integer.parseInt(maxNights) + " night(s)";
				} else {
					lenghtOfStayMsg = "Guests must stay between " + Integer.parseInt(minNights) + " and "
							+ Integer.parseInt(maxNights) + " nights when they book";
				}
			} else if (isMin) {
				lenghtOfStayMsg = "Guests must stay " + Integer.parseInt(minNights) + " nights";
			} else if (isMax) {
				lenghtOfStayMsg = "Guests cannot stay more than " + Integer.parseInt(maxNights) + " nights";
			}
		}

		String bookingWindowMsg = "";
		if (isBookingWindowReq) {

			if (isLenghtOfStayReq) {
				lenghtOfStayMsg += ". ";
			}

			if (isMoreThan && isWithInDaysReq) {
				if (Integer.parseInt(moreThanDays) == Integer.parseInt(withInDaysCount)) {
					bookingWindowMsg = "Guests must book their stay " + Integer.parseInt(moreThanDays)
							+ " day(s) before the check-in date";
				} else {
					bookingWindowMsg = "Guests must book their stay between " + Integer.parseInt(moreThanDays) + " and "
							+ Integer.parseInt(withInDaysCount) + " days before the check-in date";
				}
			} else if (isMoreThan) {
				bookingWindowMsg = "Guests must book atleast " + Integer.parseInt(moreThanDays)
						+ " days in advance of check-in date";
			} else if (isWithInDaysReq) {
				bookingWindowMsg = "Guests must book within " + Integer.parseInt(withInDaysCount)
						+ " days of check-in date";
			}
		}

		String promoCodeMsg = "";
		if (isPromoCodeReq) {
			if (isBookingWindowReq) {
				bookingWindowMsg += ". ";
			} else if (isLenghtOfStayReq) {
				lenghtOfStayMsg += ". ";
			}

			promoCodeMsg = "Guest must use promo code '" + promoCode + "' to qualify for this rate plan";
		}

		restrictionMsg = lenghtOfStayMsg + bookingWindowMsg + promoCodeMsg;
		logger.info(restrictionMsg);
		return restrictionMsg;
	}

	public void clickTitleSummaryValueForEdit(WebDriver driver, String Title, ArrayList<String> test_steps) {
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ Title.toUpperCase() + "']/../following-sibling::div/span[@class='summary-value']";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		driver.findElement(By.xpath(path)).click();
		test_steps.add("Successfully Clicked Title Summray Value of  : " + Title);
		logger.info("Successfully Clicked Title Summray Value of  : " + Title);

	}

	public String getTitleSummaryValue(WebDriver driver, String Title, ArrayList<String> test_steps) {
		String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ Title.toUpperCase() + "']/../following-sibling::div/span[@class='summary-value']";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		// Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)),
		// driver);
		String found = driver.findElement(By.xpath(path)).getText();
		test_steps.add("Found Title Summray Value of " + Title + " is : " + found);
		logger.info("Found Title Summray Value of " + Title + " is : " + found);
		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
				"Failed To Verify Title Summary Value is Not Displayed");
		test_steps.add("Successfully Verified Title Summray Value of " + Title + " is : Displayed");
		logger.info("Successfully Verified Title Summray Value of " + Title + " is : Displayed ");
		return found;

	}

	public ArrayList<String> verifyTitleSummaryValue(WebDriver driver, String Title, String expected) {
		ArrayList<String> testSteps = new ArrayList<>();
		String actual = getTitleSummaryValue(driver, Title, testSteps);
		assertEquals(actual, expected, "Failed to Verify Title Summary Value");

		testSteps.add("Successfully Verified Title Summray Value of " + Title + " is : " + actual);
		logger.info("Successfully Verified Title Summray Value of " + Title + " is : " + actual);
		return testSteps;
	}

	public void verifyTitleSummaryValue(WebDriver driver, String Title, String expected, ArrayList<String> testSteps) {
		String actual = getTitleSummaryValue(driver, Title, testSteps);
		assertEquals(actual, expected, "Failed to Verify Title Summary Value");

		testSteps.add("Successfully Verified Title Summray Value of " + Title + " is : " + actual);
		logger.info("Successfully Verified Title Summray Value of " + Title + " is : " + actual);
	}

	public String generateTitleSummaryValueForChannels(WebDriver driver) {
		String result = "";

		String selectAllPath = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='SELECT ALL']/preceding-sibling::span/input";
		String itemsPath = "//span[text()='Select All']/../following-sibling::div//input";

		if (driver.findElement(By.xpath(selectAllPath)).isSelected()) {
			result = "All channels selected";
		} else {

			List<WebElement> list = driver.findElements(By.xpath(itemsPath));
			int count = 1;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isSelected()) {
					logger.info(count);
					if (count == 4) {
						break;
					} else if (count != 1 && count < 4) {
						result += ", ";
					}

					result += driver
							.findElement(By.xpath("(" + itemsPath + "/../following-sibling::span)[" + (i + 1) + "]"))
							.getText();
					logger.info(result);
					count++;
				}
			}
			if (count > 3) {
				result += " +" + (Math.abs(list.size() - 3));
			}
		}

		logger.info("Channel Generated Title Summary Value : " + result);
		return result;
	}

	public String generateTitleSummaryValueForRoomClass(WebDriver driver) {
		String result = "";

		String selectAllPath = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='SELECT ALL']/preceding-sibling::span/input";
		String itemsPath = "//span[text()='Select All']/../../following-sibling::div//input";

		/*
		 * if(driver.findElement(By.xpath(selectAllPath)).isSelected()) { result =
		 * "All room classes selected"; }else {
		 */

		List<WebElement> list = driver.findElements(By.xpath(itemsPath));
		int count = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isSelected()) {
				if (count >= 3) {

				} else if (count == 0) {
					result = driver
							.findElement(By.xpath("(" + itemsPath + "/../following-sibling::span)[" + (i + 1) + "]"))
							.getText();

				} else {
					result += ", " + driver
							.findElement(By.xpath("(" + itemsPath + "/../following-sibling::span)[" + (i + 1) + "]"))
							.getText();

				}

				count++;
			}
		}
		if (count > 3) {
			result += " +" + (Math.abs(count - 3));
		}
		// }

		logger.info("RoomClass Generated Title Summary Value : " + result);
		return result;
	}

	public ArrayList<String> verifyPolicyTitleSummaryValue(WebDriver driver, String policyNames,
			HashMap<String, String> policyDesc, boolean isPolicyReq, ArrayList<String> testSteps)
			throws InterruptedException {

		String policySummaryTitlePath = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='POLICY']";
		if (isPolicyReq) {
			String policyPath = policySummaryTitlePath + "/../following-sibling::div/span[@class='summary-value']/span";
			List<WebElement> list = driver.findElements(By.xpath(policyPath));
			ArrayList<String> tokens = Utility.convertTokenToArrayList(policyNames, Utility.DELIM);
			ArrayList<String> acctualList = new ArrayList<String>();

			for (int i = 0; i < list.size(); i++) {
				acctualList.add(list.get(i).getText());
			}

			Collections.sort(acctualList);
			Collections.sort(tokens);

			for (int i = 0; i < acctualList.size(); i++) {
				String foundPolicy = acctualList.get(i);
				assertEquals(foundPolicy, tokens.get(i), "Failed To Verify Policy In Title Summary Value");
				testSteps.add("Successfully Verified Title Summray Policy Value: " + foundPolicy);

				logger.info("Successfully Verified Title Summray Policy Value  : " + foundPolicy);
			}

			ArrayList<String> policies = Utility.convertTokenToArrayList(policyNames, Utility.DELIM);
			for (int j = 0; j < policies.size(); j++) {
				String path = "//span[@class='summary-value']/span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
						+ policies.get(j).toUpperCase() + "']/span";

				Wait.wait1Second();

				new Actions(driver).moveToElement(driver.findElement(By.xpath(policySummaryTitlePath))).perform();

				Wait.wait1Second();
				WebElement hoverElement = driver.findElement(By.xpath(path));
				Actions builder = new Actions(driver);
				builder.moveToElement(hoverElement).perform();
				testSteps.add("MouseHover over a Policy: " + policies.get(j) + " SVG");
				logger.info("MouseHover over a Policy: " + policies.get(j) + " SVG");
				Wait.wait1Second();
				String expected = policyDesc.get(policies.get(j));

				try {
					String actual = driver.findElement(By.xpath(
							"//div[@class='ant-tooltip ant-tooltip-placement-right']//div[@class='ant-tooltip-inner']/strong"))
							.getText();
					assertEquals(actual, expected, "Failed To Verify Policy Tool Tip Value");
					testSteps.add("Successfully Verified Policy ToolTip : " + expected);
					logger.info("Successfully Verified Policy ToolTip : " + expected);
				} catch (Error e) {
					logger.info("hello");
				} catch (Exception e) {
					logger.info("hello");

				}

			}
		} else {
			testSteps.addAll(verifyTitleSummaryValue(driver, "Policy", policyDesc.get("NO POLICIES")));
		}
		return testSteps;
	}

	public void clickCreateSeason(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_NightlyRatePlan.RatePlan_CreateSeason));
		if(isExist)
		{
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_CreateSeason);
		try {
			Utility.ScrollToElement(ratessGrid.RatePlan_CreateSeason, driver);
			ratessGrid.RatePlan_CreateSeason.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, ratessGrid.RatePlan_CreateSeason);
		}
		test_steps.add("Clicking on Create Season");
		logger.info("Clicking on Create Season");
		}
	}

	public void selectSeasonDates(WebDriver driver, ArrayList<String> test_steps, String SeasonStartDate,
			String SeasonEndDate) {
		logger.info(SeasonStartDate);
		logger.info(SeasonEndDate);
		String startDateMonth = Utility.get_Month(SeasonStartDate);
		String startDateYear = Utility.getYear(SeasonStartDate);
		String startDateDay = Utility.getDay(SeasonStartDate);
		startDateMonth = startDateMonth.toUpperCase();

		String startDateMonthYear = startDateMonth + " " + startDateYear;
		startDateMonthYear = startDateMonthYear.trim();

		String startDate = "//div[text()='" + startDateMonthYear
				+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
		logger.info("startDate: " + startDate);
		Wait.WaitForElement(driver, startDate);
		Wait.waitForElementToBeVisibile(By.xpath(startDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(startDate), driver);
		driver.findElement(By.xpath(startDate)).click();
		test_steps.add("Selecting start date of the season as : " + SeasonStartDate);
		logger.info("Selecting start date of the season as : " + SeasonStartDate);

		String endDateMonth = Utility.get_Month(SeasonEndDate);
		String endDateYear = Utility.getYear(SeasonEndDate);
		String endDateDay = Utility.getDay(SeasonEndDate);
		endDateMonth = endDateMonth.toUpperCase();

		String endDateMonthYear = endDateMonth + " " + endDateYear;
		endDateMonthYear = endDateMonthYear.trim();

		String endDate = "//div[text()='" + endDateMonthYear + "']/../..//div[@class='DayPicker-Body']//div[text()='"
				+ endDateDay + "']";
		logger.info("endDate: " + endDate);

		driver.findElement(By.xpath(endDate)).click();
		test_steps.add("Selecting end date of the season as : " + SeasonEndDate);
		logger.info("Selecting end date of the season as : " + SeasonEndDate);
	}

	public void enterSeasonName(WebDriver driver, ArrayList<String> test_steps, String SeasonName) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_SeasonName);
		ratessGrid.RatePlan_SeasonName.sendKeys(SeasonName);
		test_steps.add("Enter Season Name : " + SeasonName);
		logger.info("Enter Season Name : " + SeasonName);
	}

	public void selectSeasonDays(WebDriver driver, ArrayList<String> test_steps, String isMonDay, String isTueDay,
			String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay)
			throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		String day = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
				ratessGrid.RatePlan_Season_Sunday).toString();
		logger.info(day);
		if (isSunDay.equalsIgnoreCase("Yes")) {
			if (!day.contains("none")) {
				test_steps.add("Sunday Already selected");
				logger.info("Sunday Already selected");
			} else {
				ratessGrid.RatePlan_Season_Sunday.click();
				test_steps.add("Succesfully selected Sunday");
				logger.info("Succesfully selected Sunday");
			}
		} else {
			if (day.contains("\"")) {
				ratessGrid.RatePlan_Season_Sunday.click();
				test_steps.add("Succesfully unselected Sunday");
				logger.info("Succesfully unselected Sunday");
			} else {
				test_steps.add("Sunday Already unselected");
				logger.info("Sunday Already unselected");
			}
		}
		day = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
				ratessGrid.RatePlan_Season_Monnday).toString();
		logger.info("***" + day);

		if (isMonDay.equalsIgnoreCase("Yes")) {
			if (!day.contains("none")) {
				test_steps.add("Monday Already selected");
				logger.info("Monday Already selected");
			} else {
				ratessGrid.RatePlan_Season_Monnday.click();
				test_steps.add("Succesfully selected Monday");
				logger.info("Succesfully selected Monday");
			}
		} else {
			if (day.contains("\"")) {
				ratessGrid.RatePlan_Season_Monnday.click();
				test_steps.add("Succesfully unselected Monday");
				logger.info("Succesfully unselected Monday");
			} else {
				test_steps.add("Monday Already unselected");
				logger.info("Monday Already unselected");
			}
		}
		day = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
				ratessGrid.RatePlan_Season_Tuesday).toString();

		if (isTueDay.equalsIgnoreCase("Yes")) {
			if (!day.contains("none")) {
				test_steps.add("Tuesday Already selected");
				logger.info("Tuesday Already selected");
			} else {
				ratessGrid.RatePlan_Season_Tuesday.click();
				test_steps.add("Succesfully selected Tuesday");
				logger.info("Succesfully selected Tuesday");
			}
		} else {
			if (day.contains("\"")) {
				ratessGrid.RatePlan_Season_Tuesday.click();
				test_steps.add("Succesfully unselected Tuesday");
				logger.info("Succesfully unselected Tuesday");
			} else {
				test_steps.add("Tuesday Already unselected");
				logger.info("Tuesday Already unselected");
			}
		}

		day = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
				ratessGrid.RatePlan_Season_Wednesday).toString();

		if (isWednesDay.equalsIgnoreCase("Yes")) {
			if (!day.contains("none")) {
				test_steps.add("Wednesday Already selected");
				logger.info("Wednesday Already selected");
			} else {
				ratessGrid.RatePlan_Season_Wednesday.click();
				test_steps.add("Succesfully selected Wednesday");
				logger.info("Succesfully selected Wednesday");
			}
		} else {
			if (day.contains("\"")) {
				ratessGrid.RatePlan_Season_Wednesday.click();
				test_steps.add("Succesfully unselected Wednesday");
				logger.info("Succesfully unselected Wednesday");
			} else {
				test_steps.add("Wednesday Already unselected");
				logger.info("Wednesday Already unselected");
			}
		}

		day = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
				ratessGrid.RatePlan_Season_Thursday).toString();

		if (isThursDay.equalsIgnoreCase("Yes")) {
			if (!day.contains("none")) {
				test_steps.add("Thursday Already selected");
				logger.info("Thursday Already selected");
			} else {
				ratessGrid.RatePlan_Season_Thursday.click();
				test_steps.add("Succesfully selected Thursday");
				logger.info("Succesfully selected Thursday");
			}
		} else {
			if (day.contains("\"")) {
				ratessGrid.RatePlan_Season_Thursday.click();
				test_steps.add("Succesfully unselected Thursday");
				logger.info("Succesfully unselected Thursday");
			} else {
				test_steps.add("Thursday Already unselected");
				logger.info("Thursday Already unselected");
			}
		}

		day = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
				ratessGrid.RatePlan_Season_Friday).toString();

		if (isFriday.equalsIgnoreCase("Yes")) {
			if (!day.contains("none")) {
				test_steps.add("Friday Already selected");
				logger.info("Friday Already selected");
			} else {
				ratessGrid.RatePlan_Season_Friday.click();
				test_steps.add("Succesfully selected Friday");
				logger.info("Succesfully selected Friday");
			}
		} else {
			if (day.contains("\"")) {
				ratessGrid.RatePlan_Season_Friday.click();
				test_steps.add("Succesfully unselected Friday");
				logger.info("Succesfully unselected Friday");
			} else {
				test_steps.add("Friday Already unselected");
				logger.info("Friday Already unselected");
			}
		}
		day = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before')." + "getPropertyValue('content');",
				ratessGrid.RatePlan_Season_Saturday).toString();

		if (isSaturDay.equalsIgnoreCase("Yes")) {
			if (!day.contains("none")) {
				test_steps.add("Saturday Already selected");
				logger.info("Saturday Already selected");
			} else {
				ratessGrid.RatePlan_Season_Saturday.click();
				test_steps.add("Succesfully selected Saturday");
				logger.info("Succesfully selected Saturday");
			}
		} else {
			if (day.contains("\"")) {
				ratessGrid.RatePlan_Season_Saturday.click();
				test_steps.add("Succesfully unselected Saturday");
				logger.info("Succesfully unselected Saturday");
			} else {
				test_steps.add("Saturday Already unselected");
				logger.info("Saturday Already unselected");
			}
		}
	}

	public void clickCreateSeasonSave(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_CreateSeason);
		ratessGrid.RatePlan_Season_CreateSeason.click();
		test_steps.add("Click on create season");
		logger.info("Click on create season");
	}

	public String selectSeasonColor(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_ColorDropDown);
		ratessGrid.RatePlan_Season_ColorDropDown.click();
		test_steps.add("Click on season color dropdown");
		logger.info("Click on season color dropdown");

		Random random = new Random();
		int randomNumber = random.nextInt(10 - 1) + 1;

		String color = "(//div[contains(text(),'Select season color')]/following-sibling::div/div//div/span/div)["
				+ randomNumber + "]";
		Wait.WaitForElement(driver, color);

		String rgb = driver.findElement(By.xpath(color)).getAttribute("style");
		int start = rgb.indexOf("(");
		int end = rgb.indexOf(")");

		driver.findElement(By.xpath(color)).click();
		test_steps.add("Selected color to Season");
		logger.info("Selected color to Season");
		return rgb.substring(start + 1, end);
	}

	public void selectAdditionalChargesForChildrenAdults(WebDriver driver, ArrayList<String> test_steps,
			String isAdditionalChargesForChildrenAdults) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_ChargesForAdditionalChildAdult);

		String classAttribute = null;
		classAttribute = ratessGrid.RatePlan_Season_ChargesForAdditionalChildAdult.getAttribute("aria-checked");
		if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")
				|| isAdditionalChargesForChildrenAdults.equalsIgnoreCase("true")) {
			if (classAttribute.contains("false")) {
				ratessGrid.RatePlan_Season_ChargesForAdditionalChildAdult.click();
				test_steps.add("Clicking on Charge for additional adult/child");
				logger.info("Clicking on Charge for additional adult/child");
			}
		} else {
			if (classAttribute.contains("true")) {
				ratessGrid.RatePlan_Season_ChargesForAdditionalChildAdult.click();
				test_steps.add("Unselecting the Charge for additional adult/child");
				logger.info("Unselecting the Charge for additional adult/child");
			}
		}
	}

	public void enterRate(WebDriver driver, ArrayList<String> test_steps, String RatePerNight,
			String isAdditionalChargesForChildrenAdults, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight) throws InterruptedException {
		logger.info(RatePerNight);
		logger.info(Utility.DELIM);
		ArrayList<String> rate = Utility.convertTokenToArrayList(RatePerNight, Utility.DELIM);
		logger.info(rate.size());
		logger.info(rate.get(0));
		String nihtRate = "//input[@name='txtRate']";
		String maxAdults = null, maxPersons = null, addAdultPerNiht = null, AddChildPerNiht = null;

		if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
			logger.info(MaxAdults + " " + MaxPersons + " " + AdditionalAdultsPerNight + " " + AdditionalChildPerNight);
			ArrayList<String> adults = Utility.convertTokenToArrayList(MaxAdults, Utility.DELIM);

			ArrayList<String> persons = Utility.convertTokenToArrayList(MaxPersons, Utility.DELIM);

			ArrayList<String> addAdults = Utility.convertTokenToArrayList(AdditionalAdultsPerNight, Utility.DELIM);
			ArrayList<String> addChild = Utility.convertTokenToArrayList(AdditionalChildPerNight, Utility.DELIM);

			for (int i = 0; i < rate.size(); i++) {

				logger.info("Adults " + adults.get(i));
				logger.info("persons " + persons.get(i));
				logger.info("addAdults " + addAdults.get(i));
				logger.info("addChild " + addChild.get(i));

				nihtRate = "(//input[@name='txtRate'])[" + (i + 1) + "]";
				driver.findElement(By.xpath(nihtRate)).sendKeys(Keys.CONTROL, "a");
				driver.findElement(By.xpath(nihtRate)).sendKeys(Keys.BACK_SPACE);
				driver.findElement(By.xpath(nihtRate)).sendKeys(rate.get(i));
				test_steps.add("Enter rate for room " + i + " rate " + rate.get(i));
				logger.info("Enter rate for room " + i + " rate " + rate.get(i));

				maxAdults = "((//input[@name='txtRate'])[" + (i + 1)
						+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[1]";

				String selectAll = Keys.chord(Keys.CONTROL, "a");
				driver.findElement(By.xpath(maxAdults)).sendKeys(selectAll);
				Wait.wait2Second();
				driver.findElement(By.xpath(maxAdults)).sendKeys(adults.get(i));
				test_steps.add("Enter max adults for the room  " + i + " is " + adults.get(i));
				logger.info("Enter max adults for the room  " + i + " is " + adults.get(i));

				maxPersons = "((//input[@name='txtRate'])[" + (i + 1)
						+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[2]";

				driver.findElement(By.xpath(maxPersons)).sendKeys(selectAll);
				Wait.wait2Second();
				driver.findElement(By.xpath(maxPersons)).sendKeys(persons.get(i));
				test_steps.add("Enter max persons for the room  " + i + " is " + persons.get(i));
				logger.info("Enter max persons for the room  " + i + " is " + persons.get(i));

				addAdultPerNiht = "((//input[@name='txtRate'])[" + (i + 1)
						+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[3]";

				driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(Keys.CONTROL, "a");
				driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(Keys.BACK_SPACE);
				driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(selectAll);
				Wait.wait2Second();

				driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(addAdults.get(i));
				test_steps.add("Enter Additional adults per night for the room  " + i + " is " + addAdults.get(i));
				logger.info("Enter Additional adults per night for the room  " + i + " is " + addAdults.get(i));

				AddChildPerNiht = "((//input[@name='txtRate'])[" + (i + 1)
						+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[4]";

				driver.findElement(By.xpath(AddChildPerNiht)).sendKeys(Keys.CONTROL, "a");
				driver.findElement(By.xpath(AddChildPerNiht)).sendKeys(Keys.BACK_SPACE);

				driver.findElement(By.xpath(AddChildPerNiht)).sendKeys(addChild.get(i));
				test_steps.add("Enter Additional child per night for the room  " + i + " is " + addChild.get(i));
				logger.info("Enter Additional chuld per night for the room  " + i + " is " + addChild.get(i));
			}
		} else {
			for (int i = 0; i < rate.size(); i++) {
				nihtRate = "(//input[@name='txtRate'])[" + (i + 1) + "]";
				driver.findElement(By.xpath(nihtRate)).sendKeys(Keys.CONTROL, "a");
				driver.findElement(By.xpath(nihtRate)).sendKeys(Keys.BACK_SPACE);

				driver.findElement(By.xpath(nihtRate)).sendKeys(rate.get(i));
				test_steps.add("Enter rate for room " + i + " rate " + rate.get(i));
				logger.info("Enter rate for room " + i + " rate " + rate.get(i));
			}
		}
	}

	public void addExtraRoomClassInSeason(WebDriver driver, ArrayList<String> test_steps, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String isAdditionalChargesForChildrenAdults, String RatePerNight,
			String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults, String ExtraRoomClassMaxPersons,
			String ExtraRoomClassAdditionalAdultsPerNight, String ExtraRoomClassAdditionalChildPerNight)
			throws InterruptedException {

		String roomClassName;

		if (isAddRoomClassInSeason.equalsIgnoreCase("Yes")) {
			String[] roomClass = ExtraRoomClassesInSeason.split(Utility.DELIM);
			Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
			Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AddRoomClass);
			ratessGrid.RatePlan_Season_AddRoomClass.click();
			test_steps.add("Clickin on Add Room Class");
			logger.info("Clickin on Add Room Class");
			for (int i = 0; i < roomClass.length; i++) {
				roomClassName = "//span[text()='" + roomClass[i] + "']/preceding-sibling::span/input";
				Wait.WaitForElement(driver, roomClassName);
				driver.findElement(By.xpath(roomClassName)).click();
				test_steps.add("Successfully selected room class : " + roomClass[i]);
				logger.info("Successfully selected room class : " + roomClass[i]);

				String[] rate = RatePerNight.split(Utility.DELIM);

				String nihtRate = "//input[@name='txtRate']";
				String ratePerNight = null, maxAdults = null, maxPersons = null, addAdultPerNiht = null,
						AddChildPerNiht = null;

				if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
					String[] rateNight = ExtraRoomClassRatePerNight.split(Utility.DELIM);
					String[] adults = ExtraRoomClassMaxAdults.split(Utility.DELIM);
					String[] persons = ExtraRoomClassMaxPersons.split(Utility.DELIM);

					String[] addAdults = ExtraRoomClassAdditionalAdultsPerNight.split(Utility.DELIM);
					String[] addChild = ExtraRoomClassAdditionalChildPerNight.split(Utility.DELIM);

					for (int k = (rate.length + 1); k <= (rate.length + rateNight.length); k++) {
						ratePerNight = "//span[text()='" + roomClass[i] + "']/../..//input[@name='txtRate']";
						driver.findElement(By.xpath(ratePerNight)).sendKeys(rateNight[i]);
						test_steps.add("Enter rate for room " + i + " rate " + rateNight[i]);
						logger.info("Enter rate for room " + i + " rate " + rateNight[i]);

						maxAdults = "(//span[text()='" + roomClass[i]
								+ "']/../..//input[@name='txtRate']/../../../../../../li/ul/li//input[@role='spinbutton'])[1]";

						String selectAll = Keys.chord(Keys.CONTROL, "a");
						driver.findElement(By.xpath(maxAdults)).sendKeys(selectAll);
						Wait.wait2Second();
						driver.findElement(By.xpath(maxAdults)).sendKeys(adults[i]);
						test_steps.add("Enter max adults for the room  " + i + " is " + adults[i]);
						logger.info("Enter max adults for the room  " + i + " is " + adults[i]);

						maxPersons = "(//span[text()='" + roomClass[i]
								+ "']/../..//input[@name='txtRate']/../../../../../../li/ul/li//input[@role='spinbutton'])[2]";

						driver.findElement(By.xpath(maxPersons)).sendKeys(selectAll);
						Wait.wait2Second();
						driver.findElement(By.xpath(maxPersons)).sendKeys(persons[i]);
						test_steps.add("Enter max persons for the room  " + i + " is " + persons[i]);
						logger.info("Enter max persons for the room  " + i + " is " + persons[i]);

						addAdultPerNiht = "(//span[text()='" + roomClass[i]
								+ "']/../..//input[@name='txtRate']/../../../../../../li/ul/li//input[@role='spinbutton'])[3]";

						driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(selectAll);
						Wait.wait2Second();
						driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(addAdults[i]);
						test_steps.add("Enter Additional adults per night for the room  " + i + " is " + addAdults[i]);
						logger.info("Enter Additional adults per night for the room  " + i + " is " + addAdults[i]);

						AddChildPerNiht = "(//span[text()='" + roomClass[i]
								+ "']/../..//input[@name='txtRate']/../../../../../../li/ul/li//input[@role='spinbutton'])[4]";
						driver.findElement(By.xpath(AddChildPerNiht)).sendKeys(selectAll);
						Wait.wait2Second();
						driver.findElement(By.xpath(AddChildPerNiht)).sendKeys(addChild[i]);
						test_steps.add("Enter Additional child per night for the room  " + i + " is " + addChild[i]);
						logger.info("Enter Additional chuld per night for the room  " + i + " is " + addChild[i]);
					}
				} else {
					String[] rateNight = ExtraRoomClassRatePerNight.split(Utility.DELIM);
					for (int k = (rate.length + 1); k <= (rate.length + rateNight.length); k++) {
						nihtRate = "//span[text()='" + roomClass[i] + "']/../..//input[@name='txtRate']";
						driver.findElement(By.xpath(nihtRate)).sendKeys(rateNight[i]);
						test_steps.add("Enter rate for room " + i + " rate " + rateNight[i]);
						logger.info("Enter rate for room " + i + " rate " + rateNight[i]);
					}
				}
			}
		}
	}

	public void clickRulesRestrictionOnSeason(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_RulesRestrictionsOnSeason);
		ratessGrid.RatePlan_Season_RulesRestrictionsOnSeason.click();
		test_steps.add("Clicking om Rules/Restrictions on Season Page");
		logger.info("Clicking om Rules/Restrictions on Season Page");
	}

	public void clickAssignRulesByRoomClass(WebDriver driver, ArrayList<String> test_steps,
			String isAssignRulesByRoomClass) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AssignRulesByRoomClass);

		String classAttribute = null;
		classAttribute = ratessGrid.RatePlan_Season_AssignRulesByRoomClass.getAttribute("aria-checked");
		if (isAssignRulesByRoomClass.equalsIgnoreCase("Yes")) {
			if (classAttribute.contains("false")) {
				ratessGrid.RatePlan_Season_AssignRulesByRoomClass.click();
				test_steps.add("Clicking on assign rules by room class");
				logger.info("Clicking on assign rules by room class");
			}
		} else {
			if (classAttribute.contains("true")) {
				ratessGrid.RatePlan_Season_AssignRulesByRoomClass.click();
				test_steps.add("Unselecting the assign rules by room class");
				logger.info("Unselecting the assign rules by room class");
			}
		}
	}

	public void enterSeasonLevelRules(WebDriver driver, ArrayList<String> test_steps, String isSerasonLevelRules,
			String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClass, String SeasonRuleType,
			String SeasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday,
			String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday,
			String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		if (isSerasonLevelRules.equalsIgnoreCase("Yes")) {
			if (Utility.isSelectedWithBefore(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRule)) {
				Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRule);
			}
			if (Utility.isSelectedWithBefore(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn)) {
				Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);
			}

			if (Utility.isSelectedWithBefore(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut)) {
				Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);
			}

			if (isAssignRulesByRoomClass.equalsIgnoreCase("Yes")) {
				Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses);
				ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses.click();
				test_steps.add("Clicking on Choose existing room class(s)");
				logger.info("Clicking on Choose existing room class(s)");
				ArrayList<String> roomClassList = Utility.convertTokenToArrayList(SeasonRuleSpecificRoomClass,
						Utility.DELIM);

				for (int i = 0; i < roomClassList.size(); i++) {
					String roomClassName = "//li[text()='" + roomClassList.get(i) + "']";
					try {
						Utility.ScrollToElement(driver.findElement(By.xpath(roomClassName)), driver);
						driver.findElement(By.xpath(roomClassName)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(roomClassName)));
					}
					test_steps.add("Selectin room : " + roomClassList.get(i));
					logger.info("Selectin room : " + roomClassList.get(i));
				}
				Wait.wait1Second();
				try {
					Utility.ScrollToElement(driver.findElement(By.xpath("//label[text()='Room class']")), driver);
					driver.findElement(By.xpath("//label[text()='Room class']")).click();
				} catch (Exception e) {
					Utility.clickThroughJavaScript(driver,
							driver.findElement(By.xpath("//label[text()='Room class']")));
				}
				Wait.wait1Second();
			}

			ArrayList<String> ruleType = Utility.convertTokenToArrayList(SeasonRuleType, Utility.DELIM);

			ArrayList<String> monList = Utility.convertTokenToArrayList(isSeasonRuleOnMonday, Utility.DELIM);
			ArrayList<String> tueList = Utility.convertTokenToArrayList(isSeasonRuleOnTuesday, Utility.DELIM);
			ArrayList<String> wedList = Utility.convertTokenToArrayList(isSeasonRuleOnWednesday, Utility.DELIM);
			ArrayList<String> thuList = Utility.convertTokenToArrayList(isSeasonRuleOnThursday, Utility.DELIM);
			ArrayList<String> friList = Utility.convertTokenToArrayList(isSeasonRuleOnFriday, Utility.DELIM);
			ArrayList<String> satList = Utility.convertTokenToArrayList(isSeasonRuleOnSaturday, Utility.DELIM);
			ArrayList<String> sunList = Utility.convertTokenToArrayList(isSeasonRuleOnSunday, Utility.DELIM);
			if (ruleType.size() > 0 && ruleType != null) {
				int dayIndex = 0;
				for (int i = 0; i < ruleType.size(); i++) {
					if (ruleType.get(i).equalsIgnoreCase("Min Nights")) {
						String minStay = "//span[text()='Min nights']/preceding-sibling::span/input/..";

						if (!driver.findElement(By.xpath(minStay)).getAttribute("class").contains("checked")) {
							Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRule);
						}

						Wait.wait1Second();
						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.click();
						Wait.wait1Second();
						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.clear();
						Utility.clearValue(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue);
						Wait.wait1Second();
						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.sendKeys(SeasonRuleMinStayValue);
						test_steps.add("Min Rule Value Entered : " + SeasonRuleMinStayValue);
						logger.info("Min Rule Value Entered : " + SeasonRuleMinStayValue);
					}

//					if ((ruleType.size() > 2 && ruleType.toString().contains("Min Nights"))
//							|| (ruleType.size() > 1 && !ruleType.toString().contains("Min Nights"))) {

					else if (ruleType.get(i).equalsIgnoreCase("No check-in")) {

						String noCheckIn = "//span[text()='No check-in']/preceding-sibling::span/input/..";

						if (!driver.findElement(By.xpath(noCheckIn)).getAttribute("class").contains("checked")) {
							Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);
						}

						if (monList.get(dayIndex).equalsIgnoreCase("yes")) {
							String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
								test_steps.add("No Check in on monday is already selected");
								logger.info("No Check in on monday is already selected");
							} else {
								driver.findElement(By.xpath(monNoCheckIn)).click();
								test_steps.add("No Check in on monday is selected");
								logger.info("No Check in on monday is selected");
							}
						} else if (monList.get(dayIndex).equalsIgnoreCase("no")) {
							String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
								test_steps.add("No Check in on monday is already unChecked");
								logger.info("No Check in on monday is already unChecked");
							} else {
								driver.findElement(By.xpath(monNoCheckIn)).click();
								test_steps.add("No Check in on monday is unChecked");
								logger.info("No Check in on monday is unChecked");
							}
						}

						if (tueList.get(dayIndex).equalsIgnoreCase("yes")) {
							String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";

							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
								test_steps.add("No Check in on tuesday is already selected");
								logger.info("No Check in on tuesday is already selected");
							} else {
								driver.findElement(By.xpath(tueNoCheckIn)).click();
								test_steps.add("No Check in on tuesday is selected");
								logger.info("No Check in on tuesday is selected");
							}

						} else if (tueList.get(dayIndex).equalsIgnoreCase("no")) {
							String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
								test_steps.add("No Check in on tuesday is already unChecked");
								logger.info("No Check in on tuesday is already unChecked");
							} else {
								driver.findElement(By.xpath(tueNoCheckIn)).click();
								test_steps.add("No Check in on tuesday is unChecked");
								logger.info("No Check in on tuesday is unChecked");
							}
						}

						if (wedList.get(dayIndex).equalsIgnoreCase("yes")) {
							String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
								test_steps.add("No Check in on wednesday is already selected");
								logger.info("No Check in on wednesday is already selected");
							} else {
								driver.findElement(By.xpath(wedNoCheckIn)).click();
								test_steps.add("No Check in on wednesday is selected");
								logger.info("No Check in on wednesday is selected");
							}
						} else if (wedList.get(dayIndex).equalsIgnoreCase("no")) {
							String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
								test_steps.add("No Check in on wednesday is already unChecked");
								logger.info("No Check in on wednesday is already unChecked");
							} else {
								driver.findElement(By.xpath(wedNoCheckIn)).click();
								test_steps.add("No Check in on wednesday is unChecked");
								logger.info("No Check in on wednesday is unChecked");
							}
						}

						if (thuList.get(dayIndex).equalsIgnoreCase("yes")) {
							String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
								test_steps.add("No Check in on thursday is already selected");
								logger.info("No Check in on thursday is already selected");
							} else {
								driver.findElement(By.xpath(thuNoCheckIn)).click();
								test_steps.add("No Check in on thursday is selected");
								logger.info("No Check in on thursday is selected");
							}

						} else if (thuList.get(dayIndex).equalsIgnoreCase("no")) {
							String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
								test_steps.add("No Check in on thursday is already unChecked");
								logger.info("No Check in on thursday is already unChecked");
							} else {
								driver.findElement(By.xpath(thuNoCheckIn)).click();
								test_steps.add("No Check in on thursday is unChecked");
								logger.info("No Check in on thursday is unChecked");
							}
						}

						if (friList.get(dayIndex).equalsIgnoreCase("yes")) {
							String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
								test_steps.add("No Check in on friday is already selected");
								logger.info("No Check in on friday is already selected");
							} else {
								driver.findElement(By.xpath(friNoCheckIn)).click();
								test_steps.add("No Check in on friday is selected");
								logger.info("No Check in on friday is selected");
							}

						} else if (friList.get(dayIndex).equalsIgnoreCase("no")) {
							String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
								test_steps.add("No Check in on tuesday is already unChecked");
								logger.info("No Check in on tuesday is already unChecked");
							} else {
								driver.findElement(By.xpath(friNoCheckIn)).click();
								test_steps.add("No Check in on tuesday is unChecked");
								logger.info("No Check in on tuesday is unChecked");
							}
						}

						if (satList.get(dayIndex).equalsIgnoreCase("yes")) {
							String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
								test_steps.add("No Check in on saturday is already selected");
								logger.info("No Check in on saturday is already selected");
							} else {
								driver.findElement(By.xpath(satNoCheckIn)).click();
								test_steps.add("No Check in on saturday is selected");
								logger.info("No Check in on saturday is selected");
							}

						} else if (satList.get(dayIndex).equalsIgnoreCase("no")) {
							String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
								test_steps.add("No Check in on saturday is already unChecked");
								logger.info("No Check in on saturday is already unChecked");
							} else {
								driver.findElement(By.xpath(satNoCheckIn)).click();
								test_steps.add("No Check in on saturday is unChecked");
								logger.info("No Check in on saturday is unChecked");
							}
						}

						if (sunList.get(dayIndex).equalsIgnoreCase("yes")) {
							String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
								test_steps.add("No Check in on sunday is already selected");
								logger.info("No Check in on sunday is already selected");
							} else {
								driver.findElement(By.xpath(sunNoCheckIn)).click();
								test_steps.add("No Check in on sunday is selected");
								logger.info("No Check in on sunday is selected");
							}
						} else if (sunList.get(dayIndex).equalsIgnoreCase("no")) {
							String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							logger.info(driver.findElement(By.xpath(sunNoCheckIn)).isSelected());

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
								test_steps.add("No Check in on sunday is already unChecked");
								logger.info("No Check in on sunday is already unChecked");
							} else {
								driver.findElement(By.xpath(sunNoCheckIn)).click();
								test_steps.add("No Check in on sunday is unChecked");
								logger.info("No Check in on sunday is unChecked");
							}
						}
						//dayIndex++;
					}

					else if (ruleType.get(i).equalsIgnoreCase("No check-out")) {

						String noCheckOut = "//span[text()='No check-out']/preceding-sibling::span/input/..";
						if (!driver.findElement(By.xpath(noCheckOut)).getAttribute("class").contains("checked")) {
							Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);
						}

						if (monList.get(dayIndex).equalsIgnoreCase("yes")) {
							String monNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
								test_steps.add("No check-out on monday is already selected");
								logger.info("No check-out on monday is already selected");
							} else {
								driver.findElement(By.xpath(monNoCheckIn)).click();
								test_steps.add("No check-out on monday is selected");
								logger.info("No check-out on monday is selected");
							}
						} else if (monList.get(dayIndex).equalsIgnoreCase("no")) {
							String monNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
								test_steps.add("No check-out on monday is already unChecked");
								logger.info("No check-out on monday is already unChecked");
							} else {
								driver.findElement(By.xpath(monNoCheckIn)).click();
								test_steps.add("No check-out on monday is unChecked");
								logger.info("No check-out on monday is unChecked");
							}
						}

						if (tueList.get(dayIndex).equalsIgnoreCase("yes")) {
							String tueNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";

							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
								test_steps.add("No check-out on tuesday is already selected");
								logger.info("No check-out on tuesday is already selected");
							} else {
								driver.findElement(By.xpath(tueNoCheckIn)).click();
								test_steps.add("No check-out on tuesday is selected");
								logger.info("No check-out on tuesday is selected");
							}

						} else if (tueList.get(dayIndex).equalsIgnoreCase("no")) {
							String tueNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
								test_steps.add("No check-out on tuesday is already unChecked");
								logger.info("No check-out on tuesday is already unChecked");
							} else {
								driver.findElement(By.xpath(tueNoCheckIn)).click();
								test_steps.add("No check-out on tuesday is unChecked");
								logger.info("No check-out on tuesday is unChecked");
							}
						}

						if (wedList.get(dayIndex).equalsIgnoreCase("yes")) {
							String wedNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
								test_steps.add("No check-out on wednesday is already selected");
								logger.info("No check-out on wednesday is already selected");
							} else {
								driver.findElement(By.xpath(wedNoCheckIn)).click();
								test_steps.add("No check-out on wednesday is selected");
								logger.info("No check-out on wednesday is selected");
							}
						} else if (wedList.get(dayIndex).equalsIgnoreCase("no")) {
							String wedNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
								test_steps.add("No check-out on wednesday is already unChecked");
								logger.info("No check-out on wednesday is already unChecked");
							} else {
								driver.findElement(By.xpath(wedNoCheckIn)).click();
								test_steps.add("No check-out on wednesday is unChecked");
								logger.info("No check-out on wednesday is unChecked");
							}
						}

						if (thuList.get(dayIndex).equalsIgnoreCase("yes")) {
							String thuNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
								test_steps.add("No check-out on thursday is already selected");
								logger.info("No check-out on thursday is already selected");
							} else {
								driver.findElement(By.xpath(thuNoCheckIn)).click();
								test_steps.add("No check-out on thursday is selected");
								logger.info("No check-out on thursday is selected");
							}

						} else if (thuList.get(dayIndex).equalsIgnoreCase("no")) {
							String thuNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
								test_steps.add("No check-out on thursday is already unChecked");
								logger.info("No check-out on thursday is already unChecked");
							} else {
								driver.findElement(By.xpath(thuNoCheckIn)).click();
								test_steps.add("No check-out on thursday is unChecked");
								logger.info("No check-out on thursday is unChecked");
							}
						}

						if (friList.get(dayIndex).equalsIgnoreCase("yes")) {
							String friNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
								test_steps.add("No check-out on friday is already selected");
								logger.info("No check-out on friday is already selected");
							} else {
								driver.findElement(By.xpath(friNoCheckIn)).click();
								test_steps.add("No check-out on friday is selected");
								logger.info("No check-out on friday is selected");
							}

						} else if (friList.get(dayIndex).equalsIgnoreCase("no")) {
							String friNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
								test_steps.add("No check-out on tuesday is already unChecked");
								logger.info("No check-out on tuesday is already unChecked");
							} else {
								driver.findElement(By.xpath(friNoCheckIn)).click();
								test_steps.add("No check-out on tuesday is unChecked");
								logger.info("No check-out on tuesday is unChecked");
							}
						}

						if (satList.get(dayIndex).equalsIgnoreCase("yes")) {
							String satNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
								test_steps.add("No check-out on saturday is already selected");
								logger.info("No check-out on saturday is already selected");
							} else {
								driver.findElement(By.xpath(satNoCheckIn)).click();
								test_steps.add("No check-out on saturday is selected");
								logger.info("No check-out on saturday is selected");
							}

						} else if (satList.get(dayIndex).equalsIgnoreCase("no")) {
							String satNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
								test_steps.add("No check-out on saturday is already unChecked");
								logger.info("No check-out on saturday is already unChecked");
							} else {
								driver.findElement(By.xpath(satNoCheckIn)).click();
								test_steps.add("No check-out on saturday is unChecked");
								logger.info("No check-out on saturday is unChecked");
							}
						}

						if (sunList.get(dayIndex).equalsIgnoreCase("yes")) {
							String sunNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
								test_steps.add("No check-out on sunday is already selected");
								logger.info("No check-out on sunday is already selected");
							} else {
								driver.findElement(By.xpath(sunNoCheckIn)).click();
								test_steps.add("No check-out on sunday is selected");
								logger.info("No check-out on sunday is selected");
							}
						} else if (sunList.get(dayIndex).equalsIgnoreCase("no")) {
							String sunNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							logger.info(driver.findElement(By.xpath(sunNoCheckIn)).isSelected());

							if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
								test_steps.add("No check-out on sunday is already unChecked");
								logger.info("No check-out on sunday is already unChecked");
							} else {
								driver.findElement(By.xpath(sunNoCheckIn)).click();
								test_steps.add("No check-out on sunday is unChecked");
								logger.info("No check-out on sunday is unChecked");
							}
						}
						//dayIndex++;
					}

				}
			}
		}
	}

	public void clickSeasonPolicies(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonPolices);
		try {
			Utility.ScrollToElement(ratessGrid.RatePlan_Season_SeasonPolices, driver);
			ratessGrid.RatePlan_Season_SeasonPolices.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, ratessGrid.RatePlan_Season_SeasonPolices);
		}
		test_steps.add("Clicking on season policies");
		logger.info("Clicking on season policies");
	}

	@Deprecated
	public void selectSeasonPolicies(WebDriver driver, ArrayList<String> test_steps, String isSeasonPolicies,
			String SeasonPolicyType, String SeasonCancellationPolicy, String SeasonDepositPolicy,
			String SeasonCheckInPolicy, String SeasonNoShowPolicy) {
		if (isSeasonPolicies.equalsIgnoreCase("yes")) {
			String[] policy = SeasonPolicyType.split("\\|");

			for (int i = 0; i < policy.length; i++) {

				if (policy[i].equalsIgnoreCase("Cancellation Policy")) {
					String can = "//span[text()='Cancellation']/..//input";
					driver.findElement(By.xpath(can)).click();
					test_steps.add("Clicking on Cancellation Policy Checkbox");
					logger.info("Clicking on Cancellation Policy Checkbox");

					String canPolicy = "//span[contains(text(),'" + SeasonCancellationPolicy
							+ "')]/preceding-sibling::span/input";
					driver.findElement(By.xpath(canPolicy)).click();
					test_steps.add("Selecting season level Cancellation policy as : " + SeasonCancellationPolicy);
					logger.info("Selecting season level Cancellation policy as : " + SeasonCancellationPolicy);
				}

				if (policy[i].equalsIgnoreCase("Deposit")) {

					String deposit = "//span[contains(text(),'" + SeasonDepositPolicy
							+ "')]/preceding-sibling::span/input";
					driver.findElement(By.xpath(deposit)).click();
					test_steps.add("Selecting season level Deposit policy as : " + SeasonDepositPolicy);
					logger.info("Selecting season level Deposit policy as : " + SeasonDepositPolicy);
				}

				if (policy[i].equalsIgnoreCase("Check-In")) {
					String checkIn = "//span[text()='Check-in']/..//input";
					driver.findElement(By.xpath(checkIn)).click();
					test_steps.add("Clicking on checkIn Policy Checkbox");
					logger.info("Clicking on checkIn Policy Checkbox");

					String checkin = "//span[contains(text(),'" + SeasonCheckInPolicy
							+ "')]/preceding-sibling::span/input";
					driver.findElement(By.xpath(checkin)).click();
					test_steps.add("Selecting season level checkin policy as : " + SeasonCheckInPolicy);
					logger.info("Selecting season level checkin policy as : " + SeasonCheckInPolicy);
				}

				if (policy[i].equalsIgnoreCase("No Show")) {
					String noShow = "//span[text()='No Show']/..//input";
					driver.findElement(By.xpath(noShow)).click();
					test_steps.add("Clicking on noShow Policy Checkbox");
					logger.info("Clicking on noShow Policy Checkbox");

					String noShowPolicy = "//span[contains(text(),'" + SeasonNoShowPolicy
							+ "')]/preceding-sibling::span/input";
					driver.findElement(By.xpath(noShowPolicy)).click();
					test_steps.add("Selecting season level noShow policy as : " + SeasonNoShowPolicy);
					logger.info("Selecting season level noShow policy as : " + SeasonNoShowPolicy);
				}
			}
		}
	}

	public void clickSaveSason(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonSave);
		try {
			Utility.ScrollToElement(ratessGrid.RatePlan_Season_SeasonSave, driver);
			ratessGrid.RatePlan_Season_SeasonSave.click();
			// Wait.waitForElementToBeGone(driver, ratessGrid.RatePlan_Season_SeasonSave,
			// 10);
			// Wait.WaitForElement(driver,
			// OR_NightlyRatePlan.RatePlan_Season_CompleteChanges);

		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, ratessGrid.RatePlan_Season_SeasonSave);
			// Wait.WaitForElement(driver,
			// OR_NightlyRatePlan.RatePlan_Season_CompleteChanges);
		}

		test_steps.add("Clicking on Save Season");
		logger.info("Clicking on Save Season");
	}

	public void clickCompleteChanges(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_CompleteChanges);
		Wait.waitForElementToBeClickable(By.xpath(OR_NightlyRatePlan.RatePlan_Season_CompleteChanges), driver, 60);
		Wait.waitForElementToBeVisibile(By.xpath(OR_NightlyRatePlan.RatePlan_Season_CompleteChanges), driver);
		Utility.ScrollToElement(ratessGrid.RatePlan_Season_CompleteChanges, driver);
		ratessGrid.RatePlan_Season_CompleteChanges.click();
		test_steps.add("Clicking on Complete Changes");
		logger.info("Clicking on Complete Changes");
	}

	public void clickSaveAsActive(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SaveAsActive);
		Utility.ScrollToElement(ratessGrid.RatePlan_Season_SaveAsActive, driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_NightlyRatePlan.RatePlan_Season_SaveAsActive), driver);
		ratessGrid.RatePlan_Season_SaveAsActive.click();
		test_steps.add("Clicking on Save As Active");
		logger.info("Clicking on Save As Active");
		try {
			Wait.WaitForElement(driver, OR_RateGrid.ratePlanSavedMessage);
		} catch (Exception w) {
			Utility.ScrollToElement(ratessGrid.RatePlan_Season_SaveAsActive, driver);
			ratessGrid.RatePlan_Season_SaveAsActive.click();
			test_steps.add("Clicking on Save As Active");
			logger.info("Clicking on Save As Active");
			Wait.WaitForElement(driver, OR_RateGrid.ratePlanSavedMessage);
		}
	}

	public void verifyTodayDateSelectedInSeasonCancelder(WebDriver driver, ArrayList<String> test_steps) {
		String currentDate = "//div[@class='DayPicker-Day DayPicker-Day--today']/div";
		DateFormat df = new SimpleDateFormat("dd/MM/yy");
		Date dateobj = new Date();
		String date = df.format(dateobj);
		Wait.WaitForElement(driver, currentDate);
		currentDate = driver.findElement(By.xpath(currentDate)).getText().trim();
		date = Utility.getDay(date);
		assertTrue(currentDate.equalsIgnoreCase(date), "Current date was not selected in season  calender");
	}

	public void verifyCompleteChanges(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_CompleteChanges);
		Wait.waitForElementToBeClickable(By.xpath(OR_NightlyRatePlan.RatePlan_Season_CompleteChanges), driver);
		test_steps.add("Verified Complete changes button");
		logger.info("Verified Complete changes button");
	}

	public void selectDateRangeLabel(WebDriver driver, ArrayList<String> test_steps) {
		String slectDateRange = "//h3[text()='Select date range  to create a season and set rates']";
		assertTrue(driver.findElement(By.xpath(slectDateRange)).isDisplayed(),
				"Select date range  to create a season and set rates is not displayed");
		test_steps.add("Select date range  to create a season and set rates is displayed");
		logger.info("Select date range  to create a season and set rates is displayed");
	}

	public void verifyEnterSeasonNamePlaceholder(WebDriver driver, ArrayList<String> test_steps) {
		String seasonName = "//input[@placeholder='Enter season name']";
		Wait.WaitForElement(driver, seasonName);
		test_steps.add("Verified Enter season name in season popup");
		logger.info("Verified Enter season name in season popup");
	}

	public String verifySeasonNameCombinations(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_SeasonName);

		assertTrue(!ratessGrid.RatePlan_Season_CreateSeason.isEnabled(),
				"Create season button is enabled without entering the season name");
		test_steps.add("Create season button is disabled");
		logger.info("Create season button is disabled");

		String seasonName = Utility.generateRandomStringWithGivenLength(53);
		ratessGrid.RatePlan_SeasonName.clear();
		ratessGrid.RatePlan_SeasonName.sendKeys(seasonName);

		String accualValue = ratessGrid.RatePlan_SeasonName.getAttribute("value");
		int length = accualValue.length();
		assertTrue(length == 50, "failed to verify season name length");
		test_steps.add("Verified maximum season name length as 50, Entered season name length as 53, but accepted 50");
		logger.info("Verified maximum season name length as 50, Entered season name length as 53, but accepted 50");

		assertTrue(ratessGrid.RatePlan_Season_CreateSeason.isEnabled(),
				"Create season button is enabled even after entering the season name");
		test_steps.add("Create season button is enabled after entering the season name");
		logger.info("Create season button is enabled after entering the season name");
		return seasonName;
	}

	public void verifyAllSeasonDaysSelected(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);

		if (ratessGrid.RatePlan_Season_Sunday.isSelected()) {
			test_steps.add("Sunday defaultly selected");
			logger.info("Sunday defaultly selected");
		} else {
			assertTrue(false, "Sunday defaultly not selected");
			test_steps.add("Sunday defaultly not selected");
			logger.info("Sunday defaultly not selected");
		}

		if (ratessGrid.RatePlan_Season_Monnday.isSelected()) {
			test_steps.add("Monday defaultly selected");
			logger.info("Monday defaultly selected");
		} else {
			assertTrue(false, "Monday defaultly not selected");
			test_steps.add("Monday defaultly not selected");
			logger.info("Monday defaultly not selected");
		}

		if (ratessGrid.RatePlan_Season_Tuesday.isSelected()) {
			test_steps.add("Tuesday defaultly selected");
			logger.info("Tuesday defaultly selected");
		} else {
			assertTrue(false, "Tuesday defaultly not selected");
			test_steps.add("Tuesday defaultly not selected");
			logger.info("Tuesday defaultly not selected");
		}

		if (ratessGrid.RatePlan_Season_Wednesday.isSelected()) {
			test_steps.add("Wednesday defaultly selected");
			logger.info("Wednesday Already selected");
		} else {
			assertTrue(false, "Wednesday defaultly not selected");
			test_steps.add("Wednesday defaultly not selected");
			logger.info("Wednesday defaultly not selected");
		}

		if (ratessGrid.RatePlan_Season_Thursday.isSelected()) {
			test_steps.add("Thursday defaultly selected");
			logger.info("Thursday Already selected");
		} else {
			assertTrue(false, "Thursday defaultly not selected");
			test_steps.add("Thursday defaultly not selected");
			logger.info("Thursday defaultly not selected");
		}

		if (ratessGrid.RatePlan_Season_Friday.isSelected()) {
			test_steps.add("Friday defaultly selected");
			logger.info("Friday defaultly selected");
		} else {
			assertTrue(false, "Friday defaultly not selected");
			test_steps.add("Friday defaultly not selected");
			logger.info("Friday defaultly not selected");
		}

		if (ratessGrid.RatePlan_Season_Saturday.isSelected()) {
			test_steps.add("Saturday defaultly selected");
			logger.info("Saturday defaultly selected");
		} else {
			assertTrue(false, "Saturday defaultly not selected");
			test_steps.add("Saturday defaultly not selected");
			logger.info("Saturday defaultly not selected");
		}
	}

	public void verifyBlockoutIsEnabled(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		assertTrue(ratessGrid.RatePlan_Season_Blockout.isEnabled(), "Season blobk out button is disabled");
		test_steps.add("Season block out button is enabled");
		logger.info("Season block out button is enabled");
	}

	public void verifySeasonNameInSeasonPopup(WebDriver driver, ArrayList<String> test_steps, String seasonName) {
		String season = "//li/input[contains(@value,'" + seasonName + "')]";
		assertTrue(driver.findElement(By.xpath(season)).isDisplayed(), "Season name not displayed on season popup");
		test_steps.add("Season name displayed on season popup");
		logger.info("Season name displayed on season popup");
	}

	public void verifySeasonColor(WebDriver driver, ArrayList<String> test_steps) {
		String seasonColor = "//div[@class='selectSeasonColor noSeasonColor']";
		assertTrue(driver.findElement(By.xpath(seasonColor)).isDisplayed(), "Season color is not empty");
		test_steps.add("No color is selected for season");
		logger.info("No color is selected for season");

		assertTrue(!driver.findElement(By.xpath(seasonColor)).isDisplayed(), "Season color is empty");
		test_steps.add("Season color is selected");
		logger.info("Season color is selected");

	}

	public void verifyRatesHeader(WebDriver driver, ArrayList<String> test_steps) {
		String rates = "//h2[contains(text(),'Rates')]";
		assertTrue(driver.findElement(By.xpath(rates)).isDisplayed(), "Rates header is not displaying on Season popup");
		test_steps.add("Rates header is displaying on Season popup");
		logger.info("Rates header is displaying on Season popup");
	}

	public void verifyRatesLinkInSeasonPopup(WebDriver driver, ArrayList<String> test_steps, String RoomClass) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		assertTrue(ratessGrid.RatePlan_Season_ChargesForAdditionalChildAdult.isDisplayed(),
				"Charge for additional adult/child is not displayed");
		test_steps.add("Charge for additional adult/child is displayed");
		logger.info("Charge for additional adult/child is displayed");

		assertTrue(ratessGrid.RatePlan_Season_ChargesForAdditionalChildAdult.getAttribute("aria-checked")
				.equalsIgnoreCase("false"), "Charge for additional adult/child is not off defaultly");
		test_steps.add("Charge for additional adult/child is off by default");
		logger.info("Charge for additional adult/child is off by default");

		String rate = "//li[contains(text(),'Rate per night')]";
		String star = "//sup[contains(text(),'*')]";

		assertTrue(
				(driver.findElement(By.xpath(rate)).isDisplayed() && driver.findElement(By.xpath(star)).isDisplayed()),
				"Rate per night* is not displayed");
		test_steps.add("Rate per night* is displayed");
		logger.info("Rate per night* is displayed");

		assertTrue(!ratessGrid.rateGridRuleSavedMessage.isEnabled(), "Season save is enabled");
		test_steps.add("Season save is not enabled");
		logger.info("Season save is not enabled");

		String[] roomClass = RoomClass.split("\\|");

		for (int k = 0; k < roomClass.length; k++) {
			String room = "//span[contains(text(),'" + roomClass[k] + "')]";
			test_steps.add("Room class name displayed for : " + roomClass[k]);
			logger.info("Room class name displayed for : " + roomClass[k]);

			String roomClassCheck = "//span[contains(text(),'" + roomClass[k] + "')]/preceding-sibling::span/input";
			assertTrue((driver.findElement(By.xpath(roomClassCheck)).isEnabled()),
					roomClass[k] + ": Room Class is not checked");
			test_steps.add(roomClass[k] + ": Room Class is checked");
			logger.info(roomClass[k] + ": Room Class is checked");

			String nihtRate = "(//input[@name='txtRate'])[" + k + "]";
			assertTrue((driver.findElement(By.xpath(nihtRate)).isDisplayed()),
					roomClass[k] + ": Room Class rate per night is not displayed");
			test_steps.add(roomClass[k] + ": Room Class rate per night is displayed");
			logger.info(roomClass[k] + ": Room Class rate per night is displayed");
		}

		for (int k = 0; k < roomClass.length; k++) {
			ratessGrid.RatePlan_Season_ChargesForAdditionalChildAdult.click();
			test_steps.add("Clicking on Charge for additional adult/child");
			logger.info("Clicking on Charge for additional adult/child");

			assertTrue((driver.findElement(By.xpath(rate)).isDisplayed()
					&& driver.findElement(By.xpath(star)).isDisplayed()), "Rate per night* is not displayed");
			test_steps.add("Rate per night* is displayed");
			logger.info("Rate per night* is displayed");

		}

	}

	// Channels

	public void selectChannelsEditPage(WebDriver driver, String channels, boolean isSelect,
			ArrayList<String> test_steps) {

		StringTokenizer token = new StringTokenizer(channels, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String channel = token.nextToken();
			if (channel.equalsIgnoreCase("All")) {
				channel = "Select All";
			}
			String path = "//*[text()='Channel']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ channel.toUpperCase() + "']/preceding-sibling::span/input";

			if (isSelect) {
				if (!driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(channel + " Channel Selected");
					logger.info(channel + " Channel Selected");
				} else {
					test_steps.add(channel + " Channel Already Selected");
					logger.info(channel + " Channel Already Selected");
				}
			} else {
				if (driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(channel + " Channel UnSelected");
					logger.info(channel + " Channel UnSelected");
				} else {
					test_steps.add(channel + " Channel Already UnSelected");
					logger.info(channel + " Channel Already UnSelected");
				}
			}

		}
	}

	public void verifySelectedChannelsEditPage(WebDriver driver, String selectedChannels, boolean isSelected,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		StringTokenizer token = new StringTokenizer(selectedChannels, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String channel = token.nextToken();
			if (channel.equalsIgnoreCase("select all") || channel.equalsIgnoreCase("all")) {
				String path = "//*[text()='Channel']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='SELECT ALL']/preceding-sibling::span/input";

				assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
						"FAiled To Verify Channel not Selected : " + channel);
				test_steps.add("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
				logger.info("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
				int size = elements.DISTRIBUTION_CHANNEL_LIST.size();
				for (int i = 0; i < size; i++) {

					verifySelectedChannels(driver, elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText(), isSelected,
							test_steps);
				}
			} else {
				if (!isSelected) {
					if (!channel.equalsIgnoreCase("innCenter")) {
						String path = "//*[text()='Channel']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
								+ channel.toUpperCase() + "']/preceding-sibling::span/input";

						assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
								"FAiled To Verify Channel not Selected : " + channel);
						test_steps.add("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
						logger.info("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
					}
				} else {
					String path = "//*[text()='Channel']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
							+ channel.toUpperCase() + "']/preceding-sibling::span/input";

					assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
							"FAiled To Verify Channel not Selected : " + channel);
					test_steps.add("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
					logger.info("Successfully Verified Channel " + channel + " is Selected : " + isSelected);
				}

			}
		}
	}

	public void verifyDisplayedDistributionChannelsEditPage(WebDriver driver, ArrayList<String> channelList,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		int size = elements.DISTRIBUTION_CHANNEL_LIST.size();
		assertEquals(size, channelList.size(), "Failed To Verify List Size with Active List Size");
		test_steps.add("Successfully Verified Distribution Channels List Size : " + size);
		logger.info("Successfully Verified Distribution Channels List Size : " + size);
		for (int i = 0; i < size; i++) {

			assertTrue(elements.DISTRIBUTION_CHANNEL_LIST.get(i).isDisplayed(),
					"Failed To Verify Distribution Channels : " + elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText()
							+ " is not Displayed");
			test_steps.add("Successfully Verified Distribution Channels is Displayed : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());
			logger.info("Successfully Verified Distribution Channels is Displayed : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());

			assertEquals(elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText().toUpperCase(),
					channelList.get(i).toUpperCase(), "Failed To Verify Displayed Distribution Channels");
			test_steps.add("Successfully Verified Distribution Channels : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());
			logger.info("Successfully Verified Distribution Channels : "
					+ elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText());

			verifyChannelsSelectable(driver, elements.DISTRIBUTION_CHANNEL_LIST.get(i).getText(), test_steps);
		}
	}

	public void verifyChannelsSelectableEditPage(WebDriver driver, String channelName, ArrayList<String> test_steps) {
		String path = "//*[text()='Channel']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ channelName.toUpperCase() + "']/preceding-sibling::span";

		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
				"Failed To Verify " + channelName + " Selectable is not Displayed");
		test_steps.add("Successfully Verified Channel Radio/CheckBox is Displayed : " + channelName);
		logger.info("Successfully Verified Channel Radio/CheckBox is Displayed : " + channelName);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
				"Failed To Verify " + channelName + " Selectable is not Enabled");
		test_steps.add("Successfully Verified Channel Radio/CheckBox is Enabled : " + channelName);
		logger.info("Successfully Verified Channel Radio/CheckBox is Enabled : " + channelName);
	}

	// RoomClasses

	public void selectRoomClassesEditPage(WebDriver driver, String roomClasses, boolean isSelect,
			ArrayList<String> test_steps) {

		StringTokenizer token = new StringTokenizer(roomClasses, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String roomClass = token.nextToken();
			if (roomClass.equalsIgnoreCase("All")) {
				roomClass = "Select All";
			}
			String path = "//*[text()='Room class']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ roomClass.toUpperCase() + "']/preceding-sibling::span/input";
			if (isSelect) {
				if (!driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(roomClass + " Room Class Selected");
					logger.info(roomClass + " Room Class Selected");
				} else {
					test_steps.add(roomClass + " Room Class Already Selected");
					logger.info(roomClass + " Room Class Already Selected");
				}
			} else {
				if (driver.findElement(By.xpath(path)).isSelected()) {
					try {
						driver.findElement(By.xpath(path)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
					}
					test_steps.add(roomClass + " Room Class UnSelected");
					logger.info(roomClass + " Room Class UnSelected");
				} else {
					test_steps.add(roomClass + " Room Class Already UnSelected");
					logger.info(roomClass + " Room Class Already UnSelected");
				}
			}

		}
	}

	public void verifySelectedRoomClassesEditPage(WebDriver driver, String selectedRoomClasses, boolean isSelected,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		StringTokenizer token = new StringTokenizer(selectedRoomClasses, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String roomClass = token.nextToken();
			if (roomClass.equalsIgnoreCase("select all") || roomClass.equalsIgnoreCase("all")) {
				String path = "//*[text()='Room class']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='SELECT ALL']/preceding-sibling::span/input";
				assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
						"Failed To Verify Room Class not Selected : " + roomClass + " ");
				test_steps.add("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
				logger.info("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
				int size = elements.ROOMCLASSES_LIST.size();
				for (int i = 0; i < size; i++) {
					verifySelectedRoomClassesEditPage(driver, elements.ROOMCLASSES_LIST.get(i).getText(), isSelected,
							test_steps);
				}
			} else {
				String path = "//*[text()='Room class']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
						+ roomClass.toUpperCase() + "']/preceding-sibling::span/input";
				assertEquals(driver.findElement(By.xpath(path)).isSelected(), isSelected,
						"Failed To Verify Room Class not Selected : " + roomClass + " ");
				test_steps.add("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
				logger.info("Successfully Verified Room Class " + roomClass + " is Selected : " + isSelected);
			}
		}
	}

	public void verifyDisplayedRoomClassesEditPage(WebDriver driver, ArrayList<String> roomClassList,
			ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		int size = elements.ROOMCLASSES_LIST.size();
		assertEquals(size, roomClassList.size(), "Failed To Verify List Size with Active List Size");
		test_steps.add("Successfully Verified Room Class List Size : " + size);
		logger.info("Successfully Verified Room Class List Size : " + size);
		for (int i = 0; i < size; i++) {

			assertTrue(elements.ROOMCLASSES_LIST.get(i).isDisplayed(), "Failed To Verify Room Class : "
					+ elements.ROOMCLASSES_LIST.get(i).getText() + " is not Displayed");
			test_steps.add(
					"Successfully Verified Room Class is Displayed : " + elements.ROOMCLASSES_LIST.get(i).getText());
			logger.info(
					"Successfully Verified Room Class is Displayed : " + elements.ROOMCLASSES_LIST.get(i).getText());

			assertEquals(elements.ROOMCLASSES_LIST.get(i).getText().toUpperCase(), roomClassList.get(i).toUpperCase(),
					"Failed To Verify Displayed RoomClass");
			test_steps.add("Successfully Verified Room Class : " + elements.ROOMCLASSES_LIST.get(i).getText());
			logger.info("Successfully Verified Room Class : " + elements.ROOMCLASSES_LIST.get(i).getText());

			verifyRoomClassSelectableEditPage(driver, elements.ROOMCLASSES_LIST.get(i).getText(), test_steps);
		}
	}

	public void verifyRoomClassSelectableEditPage(WebDriver driver, String RoomClass, ArrayList<String> test_steps) {
		String path = "//*[text()='Room class']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ RoomClass.toUpperCase() + "']/preceding-sibling::span";

		assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
				"Failed To Verify " + RoomClass + " Selectable is not Displayed");
		test_steps.add("Successfully Verified RoomClass CheckBox is Displayed : " + RoomClass);
		logger.info("Successfully Verified RoomClass CheckBox is Displayed : " + RoomClass);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
				"Failed To Verify " + RoomClass + " Selectable is not Enabled");
		test_steps.add("Successfully Verified RoomClass CheckBox is Enabled : " + RoomClass);
		logger.info("Successfully Verified RoomClass CheckBox is Enabled : " + RoomClass);
	}

	public void ratePlanStatusChange(WebDriver driver, boolean isActive, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		//String path = "//li[text()='Inactive']";
		String path = "//div[text()='Inactive']";
		String msg = "Inactive";
		if (isActive) {
			//path = "//li[text()='Active']";
			path = "//div[text()='Active']";
			msg = "Active";
		}

		Wait.explicit_wait_visibilityof_webelement(elements.RATE_PLAN_STATUS_SELECTION, driver);
		try {
			Utility.ScrollToElement(elements.RATE_PLAN_STATUS_SELECTION, driver);
			elements.RATE_PLAN_STATUS_SELECTION.click();
			logger.info("Click on Drop Down Box");
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.RATE_PLAN_STATUS_SELECTION);
			logger.info("Click on Drop Down Box");
		}
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path)), driver);

		try {
			Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
			driver.findElement(By.xpath(path)).click();
			logger.info("Select Status: " + msg);
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}

		test_steps.add("Successfully Verified Rate Plan Status Changed to : " + msg);
		logger.info("Successfully Verified Rate Plan Status Changed to : " + msg);
		Wait.wait5Second();
		boolean isExist = Utility.isElementPresent(driver,
				By.xpath("//div[@class='ant-modal-content']//button/span[text()='Yes']"));
		if (isExist) {
			String yesButton = "//div[@class='ant-modal-content']//button/span[text()='Yes']";
			Utility.ScrollToElement(driver.findElement(By.xpath(yesButton)), driver);
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(yesButton)));
			logger.info("Click on Yes Button");
		}
		Wait.wait10Second();
		verifySelectedRatePlanStatus(driver, isActive, test_steps);
	}

	public void verifySelectedRatePlanStatus(WebDriver driver, boolean isExpectedActive, ArrayList<String> test_steps) {
		//String path = "//label[text()='Rate plan status']/following-sibling::div//div[@class='ant-select-selection-selected-value']";
		String path = "//label[text()='Rate plan status']/following-sibling::div//span[@class='ant-select-selection-item']";
		String expected = "Inactive";
		if (isExpectedActive) {
			expected = "Active";
		}
		assertEquals(driver.findElement(By.xpath(path)).getText(), expected, "Failed to verify Selected Status");
		test_steps.add(
				"Successfully Verified Rate Plan Selected Status : " + driver.findElement(By.xpath(path)).getText());
		logger.info(
				"Successfully Verified Rate Plan Selected Status : " + driver.findElement(By.xpath(path)).getText());

		assertEquals(driver.findElement(By.xpath(path)).getAttribute("title"), expected,
				"Failed to verify Selected Status tooltip");
		test_steps.add("Successfully Verified Rate Plan Selected Status tooltip : " + expected);
		logger.info("Successfully Verified Rate Plan Selected Status tooltip : " + expected);
	}

	public String getRatePlanNameEditPage(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.RATE_PLAN_NAME_EDIT_PAGE, driver);
		String found = elements.RATE_PLAN_NAME_EDIT_PAGE.getText();
		test_steps.add("Found Rate Plan Name : " + found);
		logger.info("Found Rate Plan Name : " + found);
		return found;
	}

	public void verifyRatePlanNameEditPage(WebDriver driver, String expectedName, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.RATE_PLAN_NAME_EDIT_PAGE, driver);

		assertTrue(elements.RATE_PLAN_NAME_EDIT_PAGE.isDisplayed(), "Failed to Verify Rate Plan Name");
		test_steps.add("Successfully Verified Rate Plan Name Displayed");
		logger.info("Successfully Verified Rate Plan Name Displayed");

		assertEquals(elements.RATE_PLAN_NAME_EDIT_PAGE.getText(), expectedName, "Failed to Verify Rate Plan Name");
		test_steps.add("Successfully Verified Rate Plan Name : " + expectedName);
		logger.info("Successfully Verified Rate Plan Name : " + expectedName);
	}

	public String getRatePlanTypeEditPage(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.RATE_PLAN_TYPE_EDIT_PAGE, driver);
		String found = elements.RATE_PLAN_TYPE_EDIT_PAGE.getText();
		test_steps.add("Found Rate Plan Type : " + found);
		logger.info("Found Rate Plan Type : " + found);
		return found;
	}

	public void verifyRatePlanTypeEditPage(WebDriver driver, String expectedType, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.RATE_PLAN_TYPE_EDIT_PAGE, driver);

		assertTrue(elements.RATE_PLAN_TYPE_EDIT_PAGE.isDisplayed(), "Failed to Verify Rate Plan Type");
		test_steps.add("Successfully Verified Rate Plan Type Displayed");
		logger.info("Successfully Verified Rate Plan Type Displayed");

		assertEquals(elements.RATE_PLAN_TYPE_EDIT_PAGE.getText().toUpperCase(), expectedType.toUpperCase(),
				"Failed to Verify Rate Plan Type");

		test_steps.add("Successfully Verified Rate Plan Type : " + expectedType);
		logger.info("Successfully Verified Rate Plan Type : " + expectedType);
	}

	public String getPropertyNameEditPage(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.PROPERTY_NAME_EDIT_PAGE, driver);
		String found = elements.PROPERTY_NAME_EDIT_PAGE.getText();
		test_steps.add("Found Property Name : " + found);
		logger.info("Found Property Name : " + found);
		return found;
	}

	public void verifyPropertyNameEditPage(WebDriver driver, String expectedType, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.PROPERTY_NAME_EDIT_PAGE, driver);

		assertTrue(elements.PROPERTY_NAME_EDIT_PAGE.isDisplayed(), "Failed to VerifyProperty Name");
		test_steps.add("Successfully Verified Property Name Displayed");
		logger.info("Successfully Verified Property Name Displayed");

		assertEquals(elements.PROPERTY_NAME_EDIT_PAGE.getText(), expectedType, "Failed to Verify Property Name");
		test_steps.add("Successfully Verified Property Name : " + expectedType);
		logger.info("Successfully Verified Property Name : " + expectedType);
	}

	// Save Rate plan Button

	public void clickSaveRatePlanButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.SAVE_RATE_PLAN, driver);
			Wait.explicit_wait_elementToBeClickable(elements.SAVE_RATE_PLAN, driver);
			elements.SAVE_RATE_PLAN.click();
			Utility.clickThroughJavaScript(driver, elements.SAVE_RATE_PLAN);
		} catch (Exception e) {
			try {
				Utility.clickThroughJavaScript(driver, elements.SAVE_RATE_PLAN);
			} catch (Exception e2) {
				test_steps.add("There are no changes to update rate plan");
			}
		}
		test_steps.add("Save Rate Plan Button Clicked");
		logger.info("Save Rate Plan Button Clicked");
		try {
			String successMsgXpath = "//div[@class='ant-notification-notice-message']";
			Wait.waitForElementToBeVisibile(By.xpath(successMsgXpath), driver);
			String successMsg = driver.findElement(By.xpath(successMsgXpath)).getText();
			test_steps.add("Successfully verified message as <b>" + successMsg + "</b>");
			Wait.waitForElementToBeInVisibile(By.xpath(successMsgXpath), driver);
		} catch (Exception e) {
			logger.info("no verified message displayed");
		}
	}

	public void verifySaveRatePlanButton(WebDriver driver, boolean isEnabled, boolean isDisplayed,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement_120(elements.SAVE_RATE_PLAN, driver);

		assertEquals(elements.SAVE_RATE_PLAN.isDisplayed(), isDisplayed,
				"Failed To Verify Next Button is not Displayed");
		test_steps.add("Successfully Verified Save Rate Plan Button is Displayed : " + isDisplayed);
		logger.info("Successfully Verified Save Rate Plan Button is Displayed : " + isDisplayed);

		assertEquals(elements.SAVE_RATE_PLAN.isEnabled(), isEnabled, "Failed To Verify Next Button Enable");
		test_steps.add("Successfully Verified Next Button Enable : " + isEnabled);
		logger.info("Successfully Verified Save Rate Plan Button Enable : " + isEnabled);
	}

	public void clickCalender(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_Edit_Calender);
		Utility.ScrollToElement(ratessGrid.RatePlan_Season_Edit_Calender, driver);
		ratessGrid.RatePlan_Season_Edit_Calender.click();
		test_steps.add("Clicking on calender");
		logger.info("Clicking on calender");
	}

	public void verifyOverlapSeason(WebDriver driver, ArrayList<String> test_steps, String SeasonName,
			String OverLapingSeasonName, String OverLappinSeasonStartDate, String OverLappinSeasonEndDate,
			String isMonDay, String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay,
			String isSunDay) throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		selectSeasonDates(driver, test_steps, OverLappinSeasonStartDate, OverLappinSeasonEndDate);
		enterSeasonName(driver, test_steps, SeasonName);
		Wait.wait1Second();
		Utility.ScrollToElement(ratessGrid.RatePlan_SeasonName, driver);
		String season = "//div[contains(text(),'" + SeasonName + "')]";
		String seasonAutoList = driver.findElement(By.xpath("//div[@class='autoCompleteList']")).getText();

		assertTrue(seasonAutoList.contains(SeasonName), "Existing season name is not displayed in suggession");
		test_steps.add("Existing season name came as suggession : " + SeasonName);
		logger.info("Existing season name came as suggession : " + SeasonName);

		String seasonAleadyExist = "//span[contains(text(),'This season already exists')]";
		Utility.ScrollToElement(driver.findElement(By.xpath(seasonAleadyExist)), driver);
		assertTrue(driver.findElement(By.xpath(seasonAleadyExist)).isDisplayed(),
				"This season already exists is not displayed");
		test_steps.add("This season already exists is displayed : " + SeasonName);
		logger.info("This season already exists is displayed : " + SeasonName);

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_SeasonName);
		Wait.wait1Second();
		ratessGrid.RatePlan_SeasonName.clear();
		String selectAll = Keys.chord(Keys.CONTROL, "a");
		ratessGrid.RatePlan_SeasonName.sendKeys(selectAll);

		Wait.wait1Second();
		ratessGrid.RatePlan_SeasonName.sendKeys(OverLapingSeasonName);
		test_steps.add("Enter Season Name : " + OverLapingSeasonName);
		logger.info("Enter Season Name : " + OverLapingSeasonName);

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_FillBlanks);
		ratessGrid.RatePlan_Season_FillBlanks.click();
		test_steps.add("Clicking on Season Fill blanks");
		logger.info("Clicking on Season Fill blanks");

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_CreateSeason);
		ratessGrid.RatePlan_Season_CreateSeason.click();

	}

	public void selectAsDefaultRatePlan(WebDriver driver, ArrayList<String> test_steps, String isDefaultRatePlan) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_DefaultRatePlan);

		String classAttribute = null;
		classAttribute = ratessGrid.RatePlan_Season_DefaultRatePlan.getAttribute("aria-checked");
		if (isDefaultRatePlan.equalsIgnoreCase("Yes")) {
			if (classAttribute.contains("false")) {
				ratessGrid.RatePlan_Season_DefaultRatePlan.click();
				test_steps.add("Clicking on Default Rate Plan");
				logger.info("Clicking on Default Rate Plan");
			}
		} else {
			if (classAttribute.contains("true")) {
				ratessGrid.RatePlan_Season_DefaultRatePlan.click();
				test_steps.add("Unselecting the Default Rate Plan");
				logger.info("Unselecting the Default Rate Plan");
			}
		}
	}

	public void clickSaveRatePlan(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SaveRatePlan);
		ratessGrid.RatePlan_Season_SaveRatePlan.click();
		test_steps.add("Clicking on Save Rate Plan");
		logger.info("Clicking on Save Rate Plan");
	}

	public void blockoutSeason(WebDriver driver, ArrayList<String> test_steps, String SeasonName,
			String BlockoutSeasonName, String BlockoutSeasonStartDate, String BlockoutSeasonEnddate, String isMonDay,
			String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay)
			throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		selectSeasonDates(driver, test_steps, BlockoutSeasonStartDate, BlockoutSeasonEnddate);
		enterSeasonName(driver, test_steps, SeasonName);
		Wait.wait1Second();
		Utility.ScrollToElement(ratessGrid.RatePlan_SeasonName, driver);
		String season = "//div[contains(text(),'" + SeasonName + "')]";
		String seasonAutoList = driver.findElement(By.xpath("//div[@class='autoCompleteList']")).getText();

		assertTrue(seasonAutoList.contains(SeasonName), "Existing season name is not displayed in suggession");
		test_steps.add("Existing season name came as suggession : " + SeasonName);
		logger.info("Existing season name came as suggession : " + SeasonName);

		String seasonAleadyExist = "//span[contains(text(),'This season already exists')]";
		Utility.ScrollToElement(driver.findElement(By.xpath(seasonAleadyExist)), driver);
		assertTrue(driver.findElement(By.xpath(seasonAleadyExist)).isDisplayed(),
				"This season already exists is not displayed");
		test_steps.add("This season already exists is displayed : " + SeasonName);
		logger.info("This season already exists is displayed : " + SeasonName);

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_SeasonName);
		Wait.wait1Second();
		ratessGrid.RatePlan_SeasonName.clear();
		String selectAll = Keys.chord(Keys.CONTROL, "a");
		ratessGrid.RatePlan_SeasonName.sendKeys(selectAll);

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_SeasonName);
		Wait.wait1Second();
		ratessGrid.RatePlan_SeasonName.clear();
		selectAll = Keys.chord(Keys.CONTROL, "a");
		ratessGrid.RatePlan_SeasonName.sendKeys(selectAll);

		Wait.wait1Second();
		ratessGrid.RatePlan_SeasonName.sendKeys(BlockoutSeasonName);
		test_steps.add("Enter Season Name : " + BlockoutSeasonName);
		logger.info("Enter Season Name : " + BlockoutSeasonName);

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_Replace);
		ratessGrid.RatePlan_Season_Replace.click();
		test_steps.add("Clicking on Season Replace");
		logger.info("Clicking on Season Replace");

		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_CreateSeason);
		ratessGrid.RatePlan_Season_CreateSeason.click();
	}

	public void verifySeasonDates(WebDriver driver, ArrayList<String> test_steps, String SeasonStartDate,
			String SeasonEndDate, String OverlapSeasonStartDate, String OverlapSeasonEndDate,
			String ReplaceSeasonStartDate, String ReplaceSeasonEndDate, String rgbSeason, String rgbOverlapSeason,
			String rgbReplaceSeason, String isMonday, String isTuesday, String isWednesday, String isThursday,
			String isFriday, String isSaturday, String isSunday, String isMondayOverlapSeason,
			String isTuesdayOverlapSeason, String isWednesdayOverlapSeason, String isThursdayOverlapSeason,
			String isFridayOverlapSeason, String isSaturdayOverlapSeason, String isSundayOverlapSeason,
			String isMondayReplaceSeason, String isTuesdayReplaceSeason, String isWednesdayReplaceSeason,
			String isThursdayReplaceSeason, String isFridayReplaceSeason, String isSaturdayReplaceSeason,
			String isSundayReplaceSeason, int dayOfReplaceStart, int dayOfReplaceEnd, int dayOfOverlapStart,
			int dayOfOverlapEnd) throws Exception {

		Utility util = new Utility();

		String rgbReplace[] = rgbReplaceSeason.split(",");
		String rgbOverlap[] = rgbOverlapSeason.split(",");
		String rgbseason[] = rgbSeason.split(",");

		String replaceColor = util.getColorNameFromRgb(Integer.parseInt(rgbReplace[0].trim()),
				Integer.parseInt(rgbReplace[1].trim()), Integer.parseInt(rgbReplace[2].trim()));
		String overlapColor = util.getColorNameFromRgb(Integer.parseInt(rgbOverlap[0].trim()),
				Integer.parseInt(rgbOverlap[1].trim()), Integer.parseInt(rgbOverlap[2].trim()));
		String seasonColor = util.getColorNameFromRgb(Integer.parseInt(rgbseason[0].trim()),
				Integer.parseInt(rgbseason[1].trim()), Integer.parseInt(rgbseason[2].trim()));

		logger.info(replaceColor);
		logger.info(overlapColor);
		logger.info(seasonColor);

		int days = util.getNumberofDays(ReplaceSeasonStartDate, ReplaceSeasonEndDate);
		test_steps.add("************* Season color verification for Block out Season ***************");
		logger.info("************* Season color verification for Block out Season ***************");
		for (int i = 0; i <= days; i++) {

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate dateF
			c.add(Calendar.DATE, (dayOfReplaceStart + i)); // same with
			// c.add(Calendar.DAY_OF_MONTH,
			// 1);

			// convert calendar to date
			Date currentDatePlusOne = c.getTime();

			String date = dateFormat.format(currentDatePlusOne);
			logger.info(date);
			Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
			simpleDateformat = new SimpleDateFormat("dd/MM/yyyy"); // the day of
			// the week
			// abbreviated
			simpleDateformat = new SimpleDateFormat("EEE"); // the day of the
			// week spelled out
			// completely
			String dayofCheckOut = simpleDateformat.format(now);

			String day;

			String startDateMonth = Utility.get_Month(date);
			String startDateYear = Utility.getYear(date);
			String startDateDay = Utility.getDay(date);
			startDateMonth = startDateMonth.toUpperCase();

			String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
					+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
			driver.findElement(By.xpath(rgb)).getAttribute("style");

			rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
			if ((rgb.contains("background-color:"))) {
				int start = rgb.indexOf("(");
				int end = rgb.indexOf(")");
				rgb = rgb.substring(start + 1, end);

				if (rgb.equalsIgnoreCase(rgbReplaceSeason)) {
					test_steps.add("Color if verified for date : " + date + "and the color is : " + replaceColor);
					logger.info("Color if verified for date : " + date + "and the color is : " + replaceColor);
				} else {
					test_steps.add("Color if not matched for date : " + date);
					logger.info("Color if not matched for date : " + date);
				}
			}
		}

		days = util.getNumberofDays(OverlapSeasonStartDate, OverlapSeasonEndDate);
		test_steps.add("************* Season color verification for Overlap Season ***************");
		logger.info("************* Season color verification for Overlap Season ***************");
		for (int i = 0; i <= days; i++) {

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate dateF
			c.add(Calendar.DATE, (dayOfOverlapStart + i)); // same with
			// c.add(Calendar.DAY_OF_MONTH,
			// 1);

			// convert calendar to date
			Date currentDatePlusOne = c.getTime();

			String date = dateFormat.format(currentDatePlusOne);
			logger.info(date);
			String input_date = date;
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dt1 = format1.parse(input_date);
			DateFormat format2 = new SimpleDateFormat("EE");
			String dayofCheckOut = format2.format(dt1);
			String day;
			if (dayofCheckOut.contains("Mon") && !isMonday.equalsIgnoreCase("Yes")) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbOverlapSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}

			} else if (dayofCheckOut.contains("Tue") && !isTuesday.equalsIgnoreCase("yes")) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbOverlapSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}

			} else if (dayofCheckOut.contains("Wed") && !isWednesday.equalsIgnoreCase("Yes")) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbOverlapSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Thu") && !isThursday.equalsIgnoreCase("Yes")) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbOverlapSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Fri") && !isFriday.equalsIgnoreCase("Yes")) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbOverlapSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Sat") && !isSaturday.equalsIgnoreCase("Yes")) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbOverlapSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Sun") && !isSunday.equalsIgnoreCase("Yes")) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbOverlapSeason)) {
						test_steps.add("Color if verified for date : " + date + "and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + "and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			}
		}

		days = util.getNumberofDays(SeasonStartDate, SeasonEndDate);
		test_steps.add("************* Season color verification for Season ***************");
		logger.info("************* Season color verification for Season ***************");
		for (int i = 0; i <= days; i++) {

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate dateF
			c.add(Calendar.DATE, (2 + i)); // same with
			// c.add(Calendar.DAY_OF_MONTH, 1);

			// convert calendar to date
			Date currentDatePlusOne = c.getTime();

			String date = dateFormat.format(currentDatePlusOne);

			String input_date = date;
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dt1 = format1.parse(input_date);
			DateFormat format2 = new SimpleDateFormat("EE");
			String dayofCheckOut = format2.format(dt1);
			// logger.info(dayofCheckOut);

			String day;
			if (dayofCheckOut.contains("Mon") && isMonday.equalsIgnoreCase("Yes")
					&& (i == 1 || i == 2 || i == 6 || i == 7)) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + seasonColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + seasonColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}

			} else if (dayofCheckOut.contains("Tue") && isTuesday.equalsIgnoreCase("yes")
					&& (i == 1 || i == 2 || i == 6 || i == 7)) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + seasonColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + seasonColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}

			} else if (dayofCheckOut.contains("Wed") && isWednesday.equalsIgnoreCase("Yes")
					&& (i == 1 || i == 2 || i == 6 || i == 7)) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + seasonColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + seasonColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Thu") && isThursday.equalsIgnoreCase("Yes")
					&& (i == 1 || i == 2 || i == 6 || i == 7)) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + overlapColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + overlapColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Fri") && isFriday.equalsIgnoreCase("Yes")
					&& (i == 1 || i == 2 || i == 6 || i == 7)) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + seasonColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + seasonColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Sat") && isSaturday.equalsIgnoreCase("Yes")
					&& (i == 1 || i == 2 || i == 6 || i == 7)) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbSeason)) {
						test_steps.add("Color if verified for date : " + date + " and the color is : " + seasonColor);
						logger.info("Color if verified for date : " + date + " and the color is : " + seasonColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			} else if (dayofCheckOut.contains("Sun") && isSunday.equalsIgnoreCase("Yes")
					&& (i == 1 || i == 2 || i == 6 || i == 7)) {
				String startDateMonth = Utility.get_Month(date);
				String startDateYear = Utility.getYear(date);
				String startDateDay = Utility.getDay(date);
				startDateMonth = startDateMonth.toUpperCase();

				String rgb = "//div[text()='" + startDateMonth + " " + startDateYear
						+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
				driver.findElement(By.xpath(rgb)).getAttribute("style");

				rgb = driver.findElement(By.xpath(rgb)).getAttribute("style");
				if ((rgb.contains("background-color:"))) {
					int start = rgb.indexOf("(");
					int end = rgb.indexOf(")");
					rgb = rgb.substring(start + 1, end);

					if (rgb.equalsIgnoreCase(rgbSeason)) {
						test_steps.add("Color if verified for date : " + date + "and the color is : " + seasonColor);
						logger.info("Color if verified for date : " + date + "and the color is : " + seasonColor);
					} else {
						test_steps.add("Color if not matched for date : " + date);
						logger.info("Color if not matched for date : " + date);
					}
				}
			}
		}
	}
	// Switch Tab Edit page

	public void switchOverviewTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_OVERVIEW_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.RATE_PLAN_OVERVIEW_TAB, driver);
			elements.RATE_PLAN_OVERVIEW_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.RATE_PLAN_OVERVIEW_TAB);
		}
		test_steps.add("Rate Plan Overview Tab Clicked");
		logger.info("Rate Plan Overview Tab Clicked");

	}

	public void switchRestrictionAndPoliciesTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.RESTRICTION_AND_POLICIES_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.RESTRICTION_AND_POLICIES_TAB, driver);
			elements.RESTRICTION_AND_POLICIES_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.RESTRICTION_AND_POLICIES_TAB);
		}
		test_steps.add("Restriction And Policies Tab Clicked");
		logger.info("Restriction And Policies Tab Clicked");

	}

	public void switchCalendarTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.CALENDAR_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.CALENDAR_TAB, driver);
			elements.CALENDAR_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.CALENDAR_TAB);
		}
		test_steps.add("Calendar Tab Clicked");
		logger.info("Calendar Tab Clicked");

	}

	public void verifyRatePlanRequiredFeildEditPage(WebDriver driver, String ratePlanName,
			boolean isRateAlreadyExistError, ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		String errorTxt = "Rate Plan Name cannot be empty";

		if (isRateAlreadyExistError) {
			errorTxt = "A rate plan with this name already exists. Please choose a different name.";
			Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME, driver);
			enterRatePlanName(driver, ratePlanName, test_steps);
			elements.RATE_PLAN_NAME.sendKeys(Keys.TAB);
			assertTrue(elements.RATE_PLAN_NAME_ERROR_TEXT.isDisplayed(),
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Rate Plan Name Error Text Displayed ");
			logger.info("Successfully Verified Rate Plan Name Error Text Displayed ");

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.getText(), errorTxt,
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			test_steps.add("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());
			logger.info("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.findElement(By.xpath("./..")).getAttribute("class"),
					"ratePlanNameErrorText", "Failed To Verify text in Red Color");
			test_steps.add("Successfully Verified Rate Plan Name Error Text is in Red Color");
			logger.info("Successfully Verified Rate Plan Name Error Text is in Red Color");

			Utility.clearValue(driver, elements.RATE_PLAN_NAME);
		} else {

			Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME, driver);
			enterRatePlanName(driver, ratePlanName, test_steps);
			elements.RATE_PLAN_NAME.clear();

			Utility.clearValue(driver, elements.RATE_PLAN_NAME);
			test_steps.add("Entered Rate Plan Name Cleared");
			logger.info("Entered Rate Plan Name Cleared");

			verifyRatePlanNameErrorTxt(driver, errorTxt, true, test_steps);
			verifyRatePlanNameFeildValue(driver, ratePlanName, false, test_steps);
			enterRatePlanName(driver, ratePlanName, test_steps);
			verifyRatePlanNameErrorTxt(driver, errorTxt, false, test_steps);
			verifyRatePlanNameFeildValue(driver, ratePlanName, true, test_steps);
		}
	}

	public void verifyDefaultRatePlanVisibility(WebDriver driver, ArrayList<String> test_steps, boolean isEnabled) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.DEFAULT_RATEPLAN_TOGGLE);

		assertTrue(elements.DEFAULT_RATEPLAN_TOGGLE.isDisplayed(),
				"Failed To Verify Default RatePlan Toggle Displayed");
		test_steps.add("Successfully Verified Default RatePlan Toggle is Displayed");
		logger.info("Successfully Verified Default RatePlan Toggle is Displayed");

		assertEquals(elements.DEFAULT_RATEPLAN_TOGGLE.isEnabled(), isEnabled,
				"Failed To Verify Default RatePlan Toggle Enabled");
		test_steps.add("Successfully Verified Default RatePlan Toggle is Enabled");
		logger.info("Successfully Verified Default RatePlan Toggle is Enabled");
	}

	public void verifyIsDefaultRatePlan(WebDriver driver, ArrayList<String> test_steps, boolean isDefaultRatePlan) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.DEFAULT_RATEPLAN_TOGGLE);

		boolean foundState = Boolean.parseBoolean(elements.DEFAULT_RATEPLAN_TOGGLE.getAttribute("aria-checked"));
		assertEquals(foundState, isDefaultRatePlan, "Failed To verify Default Rate Plan toggle State");
		test_steps.add("Successfully Verified Default RatePlan Toggle State : " + isDefaultRatePlan);
		logger.info("Successfully Verified Default RatePlan Toggle State : " + isDefaultRatePlan);
	}

	public void verifyToolTipDefaultRatePlan(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		try {
			Elements_NightlyRate elements = new Elements_NightlyRate(driver);
			WebElement hoverElement = elements.DEFAULT_RATEPLAN_TOOLTIP;
			Wait.wait1Second();
			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			test_steps.add("MouseHover over a Default RatePlan SVG");
			logger.info("MouseHover over a Default RatePlan SVG");
			Wait.wait1Second();
			String expected = "The Default rate plan will be shown by default in the Tape Chart and Rates Grid.";
			String actual = getToolTipValue(driver);
			assertEquals(actual, expected, "Failed To Verify Default RatePlan Tool Tip Value");
			test_steps.add("Successfully Verified Default RatePlan ToolTip : " + expected);
			logger.info("Successfully Verified Default RatePlan ToolTip : " + expected);
		} catch (Exception e) {

		}
	}

	// Derived rate Plan MEthods
	// Added By Adhnan 7/24/2020
	public ArrayList<String> enterAlreadyExistOrClearRatePlanName(WebDriver driver, String ratePlanName,
			boolean isRateAlreadyExistError, ArrayList<String> testSteps) throws InterruptedException {

		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		String errorTxt = "Rate Plan Name cannot be empty";

		if (isRateAlreadyExistError) {
			errorTxt = "A rate plan with this name already exists. Please choose a different name.";
			Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME, driver);
			enterRatePlanName(driver, ratePlanName, testSteps);
			elements.RATE_PLAN_NAME.sendKeys(Keys.TAB);
			assertTrue(elements.RATE_PLAN_NAME_ERROR_TEXT.isDisplayed(),
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			testSteps.add("Successfully Verified Rate Plan Name Error Text Displayed ");
			logger.info("Successfully Verified Rate Plan Name Error Text Displayed ");

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.getText(), errorTxt,
					"Failed To Verify Rate Plan Name Error Text Not Displayed");
			testSteps.add("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());
			logger.info("Successfully Verified Rate Plan Name Error Text : "
					+ elements.RATE_PLAN_NAME_ERROR_TEXT.getText());

			assertEquals(elements.RATE_PLAN_NAME_ERROR_TEXT.findElement(By.xpath("./..")).getAttribute("class"),
					"ratePlanNameErrorText", "Failed To Verify text in Red Color");
			testSteps.add("Successfully Verified Rate Plan Name Error Text is in Red Color");

			logger.info("Successfully Verified Rate Plan Name Error Text is in Red Color");
			verifyNextButton(driver, false, true, testSteps);
			Utility.clearValue(driver, elements.RATE_PLAN_NAME);
		} else {

			Wait.explicit_wait_visibilityof_webelement_120(elements.RATE_PLAN_NAME, driver);
			enterRatePlanName(driver, ratePlanName, testSteps);
			elements.RATE_PLAN_NAME.clear();

			Utility.clearValue(driver, elements.RATE_PLAN_NAME);
			testSteps.add("Entered Rate Plan Name Cleared");
			logger.info("Entered Rate Plan Name Cleared");

			verifyRatePlanNameErrorTxt(driver, errorTxt, true, testSteps);
			verifyNextButton(driver, false, true, testSteps);
		}
		return testSteps;
	}

	public String getCharCountColorRatePlanDescription(WebDriver driver) {
		return driver.findElement(By.xpath("//span[@class='char-count']")).getCssValue("color");
	}

	public long getRatePlanDescriptionClientHeight(WebDriver driver, boolean before) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		if (before) {
			elements.RATE_PLAN_DESCRIPTION.clear();
		}
		return Long.parseLong(elements.RATE_PLAN_DESCRIPTION.getAttribute("clientHeight"));
	}

	public long getRatePlanDescriptionOffSetHeight(WebDriver driver, boolean before) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		if (before) {
			elements.RATE_PLAN_DESCRIPTION.clear();
		}
		return Long.parseLong(elements.RATE_PLAN_DESCRIPTION.getAttribute("offsetHeight"));
	}

	public void verifyTitleSummaryValue(WebDriver driver, String Title, String expected, boolean equals,
			ArrayList<String> test_steps) {
		String actual = getTitleSummaryValue(driver, Title, test_steps);
		if (equals) {
			assertEquals(actual, expected, "Failed to Verify Title Summary Value");
		} else {
			assertTrue(actual.contains(expected), "Failed to Verify Title Summary Value");
		}

		test_steps.add("Successfully Verified Title Summray Value of " + Title + " is : " + actual);
		logger.info("Successfully Verified Title Summray Value of " + Title + " is : " + actual);
	}

	// Added By Adhnan8/10/2020
	public void enterRateForAllRoomClasses(WebDriver driver, int index, ArrayList<String> test_steps,
			String RatePerNight) {
		String nightRate = "//input[@name='txtRate']";
		List<WebElement> roomClassesRates = driver.findElements(By.xpath(nightRate));
		for (int k = index; k < roomClassesRates.size(); k++) {
			roomClassesRates.get(k).sendKeys(RatePerNight);
			test_steps.add("Enter rate for room " + (k + 1) + " rate " + RatePerNight);
			logger.info("Enter rate for room " + (k + 1) + " rate " + RatePerNight);
		}
	}

	// Added By Adhnan8/10/2020
	public ArrayList<String> getAllRoomClassesNames(WebDriver driver) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.ROOMCLASSES_LIST.get(0), driver);
		ArrayList<String> roomClassesNames = new ArrayList<String>();
		for (int i = 0; i < elements.ROOMCLASSES_LIST.size(); i++) {
			roomClassesNames.add(elements.ROOMCLASSES_LIST.get(i).getText());
		}
		return roomClassesNames;
	}

	public ArrayList<String> clearRatePlanName(WebDriver driver) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, NightlyRatePage.RATE_PLAN_NAME);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.RATE_PLAN_NAME), driver);
		elements.RATE_PLAN_NAME.click();
		elements.RATE_PLAN_NAME.clear();
		Utility.clearValue(driver, elements.RATE_PLAN_NAME);
		testSteps.add("Clear rate plan input field");
		return testSteps;
	}

	public ArrayList<String> clearRateFolioDisplayName(WebDriver driver) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME), driver);
		elements.RATE_PLAN_FOLIO_DISPLAY_NAME.click();
		elements.RATE_PLAN_FOLIO_DISPLAY_NAME.clear();
		Utility.clearValue(driver, elements.RATE_PLAN_FOLIO_DISPLAY_NAME);
		testSteps.add("Clear rate plan input field");
		return testSteps;
	}

	public void enterRate(WebDriver driver, ArrayList<String> test_steps, String roomClassName, String ratePerNight) {
		String nihtRate = "//input[@name='txtRate']";
		Wait.waitForElementToBeVisibile(By.xpath(nihtRate), driver);
		driver.findElement(By.xpath(nihtRate)).clear();
		driver.findElement(By.xpath(nihtRate)).sendKeys(ratePerNight);
		test_steps.add("Entering rate for room class <b>" + roomClassName + "</b> as rate <b>" + ratePerNight + "</b>");
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <createNightlyRatePlan> ' Description: <create Nightly Rate
	 * Plan > ' Input parameters: < WebDriver driver,String startDate,String
	 * endDate, String roomClass,String channelName> ' Return value: <NA> ' Created
	 * By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/21/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void createNightlyRatePlan(WebDriver driver, String planType, String ratePlanName, String folioDisplayName,
			ArrayList<String> test_steps, String description, String channels, String roomClasses,
			String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights,
			String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
			String isWithInDaysReq, String withInDaysCount, String promoCode, String seasonName, String seasonStartDate,
			String seasonEndDate, String isMonDay, String isTueDay, String isWednesDay, String isThursDay,
			String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults,
			String ratePerNight, String maxAdults, String maxPersons, String additionalAdultsPerNight,
			String additionalChildPerNight, String isAddRoomClassInSeason, String extraRoomClassesInSeason,
			String extraRoomClassRatePerNight, String extraRoomClassMaxAdults, String extraRoomClassMaxPersons,
			String extraRoomClassAdditionalAdultsPerNight, String extraRoomClassAdditionalChildPerNight,
			String isSerasonLevelRules, String isAssignRulesByRoomClass, String seasonRuleSpecificRoomClasses,
			String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday,
			String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday,
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String seasonPolicyType, String seasonPolicyValues, String isSeasonPolicies) throws Exception {

		RatesGrid rateGrid = new RatesGrid();
		// NightlyRate nightlyRate = new NightlyRate();
		rateGrid.clickRateGridAddRatePlan(driver);
		rateGrid.clickRateGridAddRatePlanOption(driver, planType);
		ratePlanName = ratePlanName + Utility.generateRandomString();
		folioDisplayName = folioDisplayName + Utility.generateRandomString();
		enterRatePlanName(driver, ratePlanName, test_steps);
		enterRateFolioDisplayName(driver, folioDisplayName, test_steps);
		enterRatePlanDescription(driver, description, test_steps);
		clickNextButton(driver, test_steps);
		selectChannels(driver, channels, true, test_steps);
		clickNextButton(driver, test_steps);
		selectRoomClasses(driver, roomClasses, true, test_steps);
		clickNextButton(driver, test_steps);
		selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
				Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
				Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
				withInDaysCount, promoCode, test_steps);
		clickNextButton(driver, test_steps);
		clickNextButton(driver, test_steps);
		test_steps.add("=================== CREATE SEASON ======================");
		logger.info("=================== CREATE SEASON ======================");

		clickCreateSeason(driver, test_steps);
		createSeason(driver, test_steps, seasonStartDate, seasonEndDate, seasonName, isMonDay, isTueDay, isWednesDay,
				isThursDay, isFriday, isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults, ratePerNight,
				maxAdults, maxPersons, additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason,
				extraRoomClassesInSeason, extraRoomClassRatePerNight, extraRoomClassMaxAdults, extraRoomClassMaxPersons,
				extraRoomClassAdditionalAdultsPerNight, extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass,
				isSerasonLevelRules, seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue,
				isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday,
				isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday, seasonPolicyType,
				seasonPolicyValues, isSeasonPolicies);

		clickCompleteChanges(driver, test_steps);
		clickSaveAsActive(driver, test_steps);
		Utility.rateplanName = ratePlanName;
		logger.info(Utility.rateplanName);
		Utility.rateplanName = ratePlanName;
		logger.info("Rate Plan Is : " + Utility.rateplanName);

	}

	public void createBasicNightlyRatePlan(WebDriver driver, String planType, String ratePlanName,
			String folioDisplayName, ArrayList<String> test_steps, String description, String channels,
			String roomClasses, String isRatePlanRistrictionReq, String ristrictionType, String isMinStay,
			String minNights, String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
			String isWithInDaysReq, String withInDaysCount, String promoCode, String seasonName, String seasonStartDate,
			String seasonEndDate, String isMonDay, String isTueDay, String isWednesDay, String isThursDay,
			String isFriday, String isSaturDay, String isSunDay, String isAdditionalChargesForChildrenAdults,
			String ratePerNight, String maxAdults, String maxPersons, String additionalAdultsPerNight,
			String additionalChildPerNight, String isAddRoomClassInSeason, String extraRoomClassesInSeason,
			String extraRoomClassRatePerNight, String extraRoomClassMaxAdults, String extraRoomClassMaxPersons,
			String extraRoomClassAdditionalAdultsPerNight, String extraRoomClassAdditionalChildPerNight,
			String isSerasonLevelRules, String isAssignRulesByRoomClass, String seasonRuleSpecificRoomClasses,
			String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday,
			String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday,
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String seasonPolicyType, String seasonPolicyValues, String isSeasonPolicies) throws Exception {

		RatesGrid rateGrid = new RatesGrid();
		rateGrid.clickRateGridAddRatePlan(driver);
		rateGrid.clickRateGridAddRatePlanOption(driver, planType);
		ratePlanName = ratePlanName + Utility.generateRandomString();
		folioDisplayName = folioDisplayName + Utility.generateRandomString();
		enterRatePlanName(driver, ratePlanName, test_steps);
		enterRateFolioDisplayName(driver, folioDisplayName, test_steps);
		enterRatePlanDescription(driver, description, test_steps);
		clickNextButton(driver, test_steps);
		selectChannels(driver, channels, true, test_steps);
		clickNextButton(driver, test_steps);
		selectRoomClasses(driver, roomClasses, true, test_steps);
		clickNextButton(driver, test_steps);
		selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
				Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
				Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
				withInDaysCount, promoCode, test_steps);
		clickNextButton(driver, test_steps);
		clickNextButton(driver, test_steps);
		test_steps.add("=================== CREATE SEASON ======================");
		logger.info("=================== CREATE SEASON ======================");

		clickCreateSeason(driver, test_steps);
		selectSeasonDates(driver, test_steps, seasonStartDate, seasonEndDate);
		seasonName = seasonName + Utility.generateRandomString();
		enterSeasonName(driver, test_steps, seasonName);
		clickCreateSeason(driver, test_steps);
		selectSeasonColor(driver, test_steps);
		if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
			selectAdditionalChargesForChildrenAdults(driver, test_steps, isAdditionalChargesForChildrenAdults);
		}
		selectAdditionalChargesForChildrenAdults(driver, test_steps, isAdditionalChargesForChildrenAdults);
		enterRate(driver, test_steps, ratePerNight, isAdditionalChargesForChildrenAdults, maxAdults, maxPersons,
				additionalAdultsPerNight, additionalChildPerNight);

		clickSaveSason(driver, test_steps);
		clickCompleteChanges(driver, test_steps);
		clickSaveAsActive(driver, test_steps);
		Utility.rateplanName = ratePlanName;
		logger.info(Utility.rateplanName);
		Utility.rateplanName = ratePlanName;
		logger.info("Rate Plan Is : " + Utility.rateplanName);

	}

	public void selectSeasonDates(WebDriver driver, ArrayList<String> test_steps, String date) {
		String startDateMonth = Utility.get_Month(date);
		String startDateYear = Utility.getYear(date);
		String startDateDay = Utility.getDay(date);
		startDateMonth = startDateMonth.toUpperCase();

		String startDateMonthYear = startDateMonth + " " + startDateYear;
		startDateMonthYear = startDateMonthYear.trim();

//		String startDate = "//div[text()='" + startDateMonthYear
//				+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
		String startDate = "//div[@aria-label='" + Utility.parseDate(date, "dd/MM/yyyy", "EEE MMM dd yyyy")
				+ "']//div[text()='" + startDateDay + "']";
		logger.info("startDate: " + startDate);
		Wait.WaitForElement(driver, startDate);
		// Wait.waitForElementToBeVisibile(By.xpath(startDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(startDate), driver);
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(startDate)));
		test_steps.add("Selecting date  as : " + date);
		logger.info("Selecting date as : " + date);

	}

	public void clickEditThisSeasonButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.editThisSeason);
		Utility.ScrollToElement(elements.editThisSeason, driver);
		Utility.clickThroughJavaScript(driver, elements.editThisSeason);
		// elements.editThisSeason.click();
		testSteps.add("Click Edit This Season Button");
		Wait.WaitForElement(driver, OR_NightlyRatePlan.editSeasonTitle);
	}

	public void deleteSeasonIcon(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_NightlyRatePlan.editThisSeason));
		if (isExist) {
			Wait.WaitForElement(driver, OR_NightlyRatePlan.editThisSeason);
			Utility.ScrollToElement(elements.editThisSeason, driver);
			Utility.clickThroughJavaScript(driver, elements.clickDelete);
			// elements.editThisSeason.click();
			testSteps.add("Click Edit This Season Button");
			logger.info("Click Edit This Season Button");
			Wait.WaitForElement(driver, NightlyRatePage.DeleteButton);
			elements.deleteButton.click();
			testSteps.add("Click Delete  Button");
			logger.info("Click Delete Button");
		}
	}

	public void closeSeason(WebDriver driver, ArrayList<String> testSteps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.editSeasonCloseIcon);
		elements.editSeasonCloseIcon.click();
		logger.info("Click Close Button");
		testSteps.add("Click Close Button");

	}

	public void clickYesButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_NightlyRatePlan.editSeasonCloseYesButton));
		if (isExist) {
			Wait.WaitForElement(driver, OR_NightlyRatePlan.editSeasonCloseYesButton);
			Utility.ScrollToElement(elements.editSeasonCloseYesButton, driver);
			Utility.clickThroughJavaScript(driver, elements.editSeasonCloseYesButton);
			logger.info("Click Yes Button");
			testSteps.add("Click Yes Button");
		}
		Wait.waitUntilPageLoadNotCompleted(driver, 10);
	}

	public String getAdultCapacity(WebDriver driver, String roomClass) {
		String adult = null;
		String path = "(//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[1]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		WebElement element = driver.findElement(By.xpath(path));
		adult = element.getText();
		logger.info(adult);
		String[] array = adult.split(":");
		array = array[1].split(" ");
		adult = array[1];
		logger.info(adult);
		return adult;
	}

	public String getChildCapacity(WebDriver driver, String roomClass) {
		String child = null;
		String path = "(//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[2]";
		WebElement element = driver.findElement(By.xpath(path));
		child = element.getText();
		logger.info(child);
		String[] array = child.split(":");
		array = array[1].split(" ");
		child = array[1];
		logger.info(child);
		return child;
	}

	public void verifyMaxAdultsPlusAndMinusIcon(WebDriver driver, ArrayList<String> testSteps, String roomClass) {
		boolean minus, plus;
		int maxValue = 0, minValue = 0, adultValue = 0;
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[1]//input";
		String minusIcon = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[1]//i[contains(@aria-label,'minus-circle')]";
		String plusIcon = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[1]//i[contains(@aria-label,'plus-circle')]";
		WebElement element = driver.findElement(By.xpath(path));
		WebElement elementMinus = driver.findElement(By.xpath(minusIcon));
		WebElement elementPlus = driver.findElement(By.xpath(plusIcon));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		adultValue = Integer.parseInt(element.getAttribute("value"));
		maxValue = Integer.parseInt(element.getAttribute("max"));
		minValue = Integer.parseInt(element.getAttribute("min"));
		testSteps.add("===Verifying Max. Adults Icon===");
		if (adultValue == minValue) {

			minus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementMinus);
			plus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementPlus);
			assertEquals(minus, true, "Failed to verify minus icon");
			testSteps.add("Minus Icon is Disabled");
			logger.info("Minus Icon is Disabled");
			assertEquals(plus, false, "Failed to verify Plus icon");
			testSteps.add("Plus Icon is Enabled");
			logger.info("Plus Icon is Enabled");
		} else if (adultValue > minValue && adultValue < maxValue) {

			minus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementMinus);
			plus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementPlus);
			assertEquals(minus, false, "Failed to verify minus icon");
			testSteps.add("Minus Icon is Enabled");
			logger.info("Minus Icon is Enabled");
			assertEquals(plus, false, "Failed to verify Plus icon");
			testSteps.add("Plus Icon is Enabled");
			logger.info("Plus Icon is Enabled");
		} else if (adultValue == maxValue) {

			minus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementMinus);
			plus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementPlus);
			assertEquals(minus, false, "Failed to verify minus icon");
			testSteps.add("Minus Icon is Enabled");
			logger.info("Minus Icon is Enabled");
			assertEquals(plus, true, "Failed to verify Plus icon");
			testSteps.add("Plus Icon is Disabled");
			logger.info("Plus Icon is Disabled");

		}
	}

	public void clickMaxAdultMinusIcon(WebDriver driver, ArrayList<String> testSteps, String roomClass)
			throws InterruptedException {
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[1]//i[contains(@aria-label,'minus-circle')]";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.click();
		testSteps.add("Click on Max. Adults Minus Icon");
		logger.info("Click on Max. Adults Minus Icon");

	}

	public void clickMaxAdultPlusIcon(WebDriver driver, ArrayList<String> testSteps, String roomClass)
			throws InterruptedException {
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[1]//i[contains(@aria-label,'plus-circle')]";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.click();
		testSteps.add("Click  Max. Adults on Plus Icon");
		logger.info("Click  Max. Adults on Plus Icon");

	}

	public void clickMaxPersonsMinusIcon(WebDriver driver, ArrayList<String> testSteps, String roomClass)
			throws InterruptedException {
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[2]//i[contains(@aria-label,'minus-circle')]";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.click();
		testSteps.add("Click on Max.Persons Minus Icon");
		logger.info("Click on Max.Persons Minus Icon");

	}

	public void clickMaxPersonsPlusIcon(WebDriver driver, ArrayList<String> testSteps, String roomClass)
			throws InterruptedException {
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[2]//i[contains(@aria-label,'plus-circle')]";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.click();
		testSteps.add("Click on Max.Persons Plus Icon");
		logger.info("Click on Max.Persons Plus Icon");

	}

	public void inputMaxAdult(WebDriver driver, ArrayList<String> testSteps, String roomClass, String value)
			throws InterruptedException {
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[1]//input";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.sendKeys(Keys.BACK_SPACE);
		element.sendKeys(value);
		testSteps.add("Input Max. Adults: <b>" + value + "</b>");
		logger.info("Input Max. Adults: " + value);
	}

	public void inputMaxPersons(WebDriver driver, ArrayList<String> testSteps, String roomClass, String value)
			throws InterruptedException {
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[2]//input";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.sendKeys(Keys.BACK_SPACE);
		element.sendKeys(value);
		testSteps.add("Input Max. Persons: <b>" + value + "</b>");
		logger.info("Input Max. Persons: " + value);
	}

	public void verifyPersonssPlusAndMinusIcon(WebDriver driver, ArrayList<String> testSteps, String roomClass) {
		boolean minus, plus;
		int maxValue = 0, minValue = 0, adultValue = 0;
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[2]//input";
		String minusIcon = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[2]//i[contains(@aria-label,'minus-circle')]";
		String plusIcon = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass + "')]"
				+ "/parent::label/following-sibling::span)[3]//li//ul/li)[2]//i[contains(@aria-label,'plus-circle')]";
		WebElement element = driver.findElement(By.xpath(path));
		WebElement elementMinus = driver.findElement(By.xpath(minusIcon));
		WebElement elementPlus = driver.findElement(By.xpath(plusIcon));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		adultValue = Integer.parseInt(element.getAttribute("value"));
		maxValue = Integer.parseInt(element.getAttribute("max"));
		minValue = Integer.parseInt(element.getAttribute("min"));
		testSteps.add("===Verifying Max. Persons Icon===");
		if (adultValue == minValue) {
			minus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementMinus);
			plus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementPlus);
			assertEquals(minus, true, "Failed to verify minus icon");
			testSteps.add("Minus Icon is Disabled");
			logger.info("Minus Icon is Disabled");
			assertEquals(plus, false, "Failed to verify Plus icon");
			testSteps.add("Plus Icon is Enabled");
			logger.info("Plus Icon is Enabled");
		} else if (adultValue > minValue && adultValue < maxValue) {
			minus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementMinus);
			plus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementPlus);
			assertEquals(minus, false, "Failed to verify minus icon");
			testSteps.add("Minus Icon is Enabled");
			logger.info("Minus Icon is Enabled");
			assertEquals(plus, false, "Failed to verify Plus icon");
			testSteps.add("Plus Icon is Enabled");
			logger.info("Plus Icon is Enabled");
		} else if (adultValue == maxValue) {
			minus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementMinus);
			plus = (boolean) jse.executeScript("return arguments[0].hasAttribute(\"disabled\");", elementPlus);
			assertEquals(minus, false, "Failed to verify minus icon");
			testSteps.add("Minus Icon is Enabled");
			logger.info("Minus Icon is Enabled");
			assertEquals(plus, true, "Failed to verify Plus icon");
			testSteps.add("Plus Icon is Disabled");
			logger.info("Plus Icon is Disabled");

		}
	}

	public String getaddAdultPerNight(WebDriver driver, String roomClass) {
		String adult = null;
		String path = "(//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]/ul//li/ul//input[@name='txExAdult']";
		WebElement element = driver.findElement(By.xpath(path));
		adult = element.getAttribute("value");
		logger.info(adult);
		return adult;
	}

	public String getaddChildPerNight(WebDriver driver, String roomClass) {
		String child = null;
		String path = "(//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]/ul//li/ul//input[@name='txtExChild']";
		WebElement element = driver.findElement(By.xpath(path));
		child = element.getAttribute("value");
		logger.info(child);
		return child;
	}

	public String getMaxAdult(WebDriver driver, String roomClass) {
		String adult = "0";
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[1]//input";
		if (getAdditionalAdultChildtoggle(driver)) {
			WebElement element = driver.findElement(By.xpath(path));
			adult = element.getAttribute("value");
			logger.info(adult);
		}
		return adult;
	}

	public String getMaxPersons(WebDriver driver, String roomClass) {
		String adult = "0";
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[2]//input";
		if (getAdditionalAdultChildtoggle(driver)) {
			WebElement element = driver.findElement(By.xpath(path));

			adult = element.getAttribute("value");
			logger.info(adult);
		}
		return adult;
	}

	public String getRateFolioDisplayName(WebDriver driver) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.RATE_PLAN_FOLIO_DISPLAY_NAME), driver);
		return elements.RATE_PLAN_FOLIO_DISPLAY_NAME.getAttribute("value");
	}

	public String getRatePlanName(WebDriver driver) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, NightlyRatePage.RATE_PLAN_NAME);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.RATE_PLAN_NAME), driver);
		return elements.RATE_PLAN_NAME.getAttribute("value");
	}

	public String getRatePlanDescription(WebDriver driver) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, NightlyRatePage.RATE_PLAN_DESCRIPTION);
		Wait.waitForElementToBeVisibile(By.xpath(NightlyRatePage.RATE_PLAN_DESCRIPTION), driver);
		return elements.RATE_PLAN_DESCRIPTION.getAttribute("value");
	}

	public void enterSeasonLevelRules(WebDriver driver, ArrayList<String> test_steps, String isAssignRulesByRoomClass,
			String SeasonRuleSpecificRoomClass, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

		if (isAssignRulesByRoomClass.equalsIgnoreCase("Yes")) {
			Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses);
			ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses.click();
			test_steps.add("Clicking on Choose existing room class(s)");
			logger.info("Clicking on Choose existing room class(s)");
			String[] roomClass = SeasonRuleSpecificRoomClass.split("\\|");

			for (int i = 0; i < roomClass.length; i++) {
				String roomClassName = "//li[text()='" + roomClass[i] + "']";
				try {
					Utility.ScrollToElement(driver.findElement(By.xpath(roomClassName)), driver);
					driver.findElement(By.xpath(roomClassName)).click();
				} catch (Exception e) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(roomClassName)));
				}
				test_steps.add("Selectin room : " + roomClass[i]);
				logger.info("Selectin room : " + roomClass[i]);
			}
			Wait.wait1Second();
			try {
				Utility.ScrollToElement(driver.findElement(By.xpath("//label[text()='Room class']")), driver);
				driver.findElement(By.xpath("//label[text()='Room class']")).click();
			} catch (Exception e) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath("//label[text()='Room class']")));
			}
			Wait.wait1Second();
		}

		String[] ruleType = SeasonRuleType.split("\\|");

		if (ruleType.length > 0 && ruleType != null) {
			for (int i = 0; i < ruleType.length; i++) {
				if (ruleType[i].equalsIgnoreCase("Min Nights")) {
					Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRule);

					Wait.wait1Second();
					ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.click();
					Wait.wait1Second();
					ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.clear();
					Utility.clearValue(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue);
					Wait.wait1Second();
					ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.sendKeys(SeasonRuleMinStayValue);
				}

				if ((ruleType.length > 2 && ruleType.toString().contains("Min Nights"))
						|| (ruleType.length > 1 && !ruleType.toString().contains("Min Nights"))) {
					String mon[] = isSeasonRuleOnMonday.split("\\|");
					String tue[] = isSeasonRuleOnTuesday.split("\\|");
					String wed[] = isSeasonRuleOnWednesday.split("\\|");
					String thu[] = isSeasonRuleOnThursday.split("\\|");
					String fri[] = isSeasonRuleOnFriday.split("\\|");
					String sat[] = isSeasonRuleOnSaturday.split("\\|");
					String sun[] = isSeasonRuleOnSunday.split("\\|");

					if (ruleType[i].equalsIgnoreCase("No check-in")) {

						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);

						if (mon[0].equalsIgnoreCase("yes")) {
							String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							driver.findElement(By.xpath(monNoCheckIn)).click();
							test_steps.add("No Check in on monday is selected");
							logger.info("No Check in on monday is selected");
						}

						if (tue[0].equalsIgnoreCase("yes")) {
							String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
							driver.findElement(By.xpath(tueNoCheckIn)).click();
							test_steps.add("No Check in on tuesday is selected");
							logger.info("No Check in on tuesday is selected");
						}

						if (wed[0].equalsIgnoreCase("yes")) {
							String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
							driver.findElement(By.xpath(wedNoCheckIn)).click();
							test_steps.add("No Check in on wednesday is selected");
							logger.info("No Check in on wednesday is selected");
						}

						if (thu[0].equalsIgnoreCase("yes")) {
							String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
							driver.findElement(By.xpath(thuNoCheckIn)).click();
							test_steps.add("No Check in on thursday is selected");
							logger.info("No Check in on thursday is selected");
						}

						if (fri[0].equalsIgnoreCase("yes")) {
							String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							driver.findElement(By.xpath(friNoCheckIn)).click();
							test_steps.add("No Check in on friday is selected");
							logger.info("No Check in on friday is selected");
						}

						if (sat[0].equalsIgnoreCase("yes")) {
							String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							driver.findElement(By.xpath(satNoCheckIn)).click();
							test_steps.add("No Check in on saturday is selected");
							logger.info("No Check in on saturday is selected");
						}

						if (sun[0].equalsIgnoreCase("yes")) {
							String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							driver.findElement(By.xpath(sunNoCheckIn)).click();
							test_steps.add("No Check in on sunday is selected");
							logger.info("No Check in on sunday is selected");
						}

					}

					if (ruleType[i].equalsIgnoreCase("No check-out")) {

						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);

						if (mon[0].equalsIgnoreCase("yes")) {
							String monNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							driver.findElement(By.xpath(monNoCheckOut)).click();
							test_steps.add("No Check out on monday is selected");
							logger.info("No Check out on monday is selected");
						}

						if (tue[0].equalsIgnoreCase("yes")) {
							String tueNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
							driver.findElement(By.xpath(tueNoCheckOut)).click();
							test_steps.add("No Check out on tuesday is selected");
							logger.info("No Check out on tuesday is selected");
						}

						if (wed[0].equalsIgnoreCase("yes")) {
							String wedNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
							driver.findElement(By.xpath(wedNoCheckOut)).click();
							test_steps.add("No Check out on wednesday is selected");
							logger.info("No Check out on wednesday is selected");
						}

						if (thu[0].equalsIgnoreCase("yes")) {
							String thuNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
							driver.findElement(By.xpath(thuNoCheckOut)).click();
							test_steps.add("No Check out on thursday is selected");
							logger.info("No Check out on thursday is selected");
						}

						if (fri[0].equalsIgnoreCase("yes")) {
							String friNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							driver.findElement(By.xpath(friNoCheckOut)).click();
							test_steps.add("No Check out on friday is selected");
							logger.info("No Check out on friday is selected");
						}

						if (sat[0].equalsIgnoreCase("yes")) {
							String satNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							driver.findElement(By.xpath(satNoCheckOut)).click();
							test_steps.add("No Check out on saturday is selected");
							logger.info("No Check out on saturday is selected");
						}

						if (sun[0].equalsIgnoreCase("yes")) {
							String sunNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							driver.findElement(By.xpath(sunNoCheckOut)).click();
							test_steps.add("No Check out on sunday is selected");
							logger.info("No Check out on sunday is selected");
						}

					}
				} else {

					if (ruleType[i].equalsIgnoreCase("No check-in")) {
						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);
						if (isSeasonRuleOnMonday.equalsIgnoreCase("yes")) {
							String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							driver.findElement(By.xpath(monNoCheckIn)).click();
							test_steps.add("No Check in on monday is selected");
							logger.info("No Check in on monday is selected");
						}

						if (isSeasonRuleOnTuesday.equalsIgnoreCase("yes")) {
							String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
							driver.findElement(By.xpath(tueNoCheckIn)).click();
							test_steps.add("No Check in on tuesday is selected");
							logger.info("No Check in on tuesday is selected");
						}

						if (isSeasonRuleOnWednesday.equalsIgnoreCase("yes")) {
							String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
							driver.findElement(By.xpath(wedNoCheckIn)).click();
							test_steps.add("No Check in on wednesday is selected");
							logger.info("No Check in on wednesday is selected");
						}

						if (isSeasonRuleOnThursday.equalsIgnoreCase("yes")) {
							String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
							driver.findElement(By.xpath(thuNoCheckIn)).click();
							test_steps.add("No Check in on thursday is selected");
							logger.info("No Check in on thursday is selected");
						}

						if (isSeasonRuleOnFriday.equalsIgnoreCase("yes")) {
							String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							driver.findElement(By.xpath(friNoCheckIn)).click();
							test_steps.add("No Check in on friday is selected");
							logger.info("No Check in on friday is selected");
						}

						if (isSeasonRuleOnSaturday.equalsIgnoreCase("yes")) {
							String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							driver.findElement(By.xpath(satNoCheckIn)).click();
							test_steps.add("No Check in on saturday is selected");
							logger.info("No Check in on saturday is selected");
						}

						if (isSeasonRuleOnSunday.equalsIgnoreCase("yes")) {
							String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							driver.findElement(By.xpath(sunNoCheckIn)).click();
							test_steps.add("No Check in on sunday is selected");
							logger.info("No Check in on sunday is selected");
						}

					}

					if (ruleType[i].equalsIgnoreCase("No check-out")) {
						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);
						if (isSeasonRuleOnMonday.equalsIgnoreCase("yes")) {
							String monNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							driver.findElement(By.xpath(monNoCheckOut)).click();
							test_steps.add("No Check out on monday is selected");
							logger.info("No Check out on monday is selected");
						}

						if (isSeasonRuleOnTuesday.equalsIgnoreCase("yes")) {
							String tueNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
							driver.findElement(By.xpath(tueNoCheckOut)).click();
							test_steps.add("No Check out on tuesday is selected");
							logger.info("No Check out on tuesday is selected");
						}

						if (isSeasonRuleOnWednesday.equalsIgnoreCase("yes")) {
							String wedNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
							driver.findElement(By.xpath(wedNoCheckOut)).click();
							test_steps.add("No Check out on wednesday is selected");
							logger.info("No Check out on wednesday is selected");
						}

						if (isSeasonRuleOnThursday.equalsIgnoreCase("yes")) {
							String thuNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
							driver.findElement(By.xpath(thuNoCheckOut)).click();
							test_steps.add("No Check out on thursday is selected");
							logger.info("No Check out on thursday is selected");
						}

						if (isSeasonRuleOnFriday.equalsIgnoreCase("yes")) {
							String friNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							driver.findElement(By.xpath(friNoCheckOut)).click();
							test_steps.add("No Check out on friday is selected");
							logger.info("No Check out on friday is selected");
						}

						if (isSeasonRuleOnSaturday.equalsIgnoreCase("yes")) {
							String satNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							driver.findElement(By.xpath(satNoCheckOut)).click();
							test_steps.add("No Check out on saturday is selected");
							logger.info("No Check out on saturday is selected");
						}

						if (isSeasonRuleOnSunday.equalsIgnoreCase("yes")) {
							String sunNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							driver.findElement(By.xpath(sunNoCheckOut)).click();
							test_steps.add("No Check out on sunday is selected");
							logger.info("No Check out on sunday is selected");
						}
					}

				}

			}
		}
	}

	// Added By Adhnan 7/29/2020
	public ArrayList<String> deleteNightlyRatePlan(WebDriver driver, String ratePlanName, String button,
			ArrayList<String> testSteps) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String xpath = "//span[text()='" + ratePlanName
				+ "']//ancestor::div[@id='ratePlans']/div[@class='RateplanOverview']//span/i[contains(@class,'delete-icon')]";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		jse.executeScript("window.scrollBy(0,-400)");
		elementrequired.click();
		testSteps.add("Click '" + ratePlanName + "' delete icon");
		logger.info("Click '" + ratePlanName + "' delete icon");
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.DELETE_RATE_PLAN_POPUP, driver);
		assertTrue(elements.DELETE_RATE_PLAN_POPUP.isDisplayed(), "Failed Delete rate Plan Popup not displayed");
		xpath = "//span[text()='" + button + "']//parent::button";
		elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		Utility.ScrollToElement(elementrequired, driver);
		elementrequired.click();
		testSteps.add("Click '" + button + "' button of 'Delete rate Plan' popup");
		logger.info("Click '" + button + "' button of 'Delete rate Plan' popup");
		return testSteps;
	}

	// Added By Adhnan 7/29/2020
	public ArrayList<String> deleteDeriveRatePlan(WebDriver driver, String ratePlanName, String button,
			ArrayList<String> testSteps) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String xpath = "//span[text()='" + ratePlanName + "']//..//span[@class='fix-delete-icon']";
		WebElement elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		jse.executeScript("window.scrollBy(0,-400)");
		elementrequired.click();
		testSteps.add("Click '" + ratePlanName + "' delete icon");
		logger.info("Click '" + ratePlanName + "' delete icon");
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.DELETE_RATE_PLAN_POPUP, driver);
		assertTrue(elements.DELETE_RATE_PLAN_POPUP.isDisplayed(), "Failed Delete rate Plan Popup not displayed");
		xpath = "//span[text()='" + button + "']//parent::button";
		elementrequired = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
		Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
		Utility.ScrollToElement(elementrequired, driver);
		elementrequired.click();
		testSteps.add("Click '" + button + "' button of 'Delete rate Plan' popup");
		logger.info("Click '" + button + "' button of 'Delete rate Plan' popup");
		return testSteps;
	}

	public void enterDifferentRateForRoomClasses(WebDriver driver, ArrayList<String> test_steps, String RoomClassNames,
			String RatePerNight) {
		StringTokenizer token = new StringTokenizer(RoomClassNames, Utility.DELIM);
		String[] rates = RatePerNight.split(Utility.DELIM);
		int i = 0;
		while (token.hasMoreTokens()) {
			String RoomClassName = token.nextToken();
			String nightRate = "//span[text()='" + RoomClassName
					+ "']//ancestor::div[@class='AddRoomClassList']//input[@name='txtRate']";
			WebElement roomClassesRates = driver.findElement(By.xpath(nightRate));
			roomClassesRates.sendKeys(rates[i]);
			test_steps.add("Enter rate for room " + RoomClassName + " rate " + RatePerNight);
			logger.info("Enter rate for room " + RoomClassName + " rate " + RatePerNight);
			i++;
		}
	}

	// Added By Adhnan8/10/2020
	public ArrayList<String> verifyRoomClassExist(WebDriver driver) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.explicit_wait_visibilityof_webelement(elements.ROOMCLASSES_LIST.get(0), driver);
		ArrayList<String> roomClassesNames = new ArrayList<String>();
		for (int i = 0; i < elements.ROOMCLASSES_LIST.size(); i++) {
			roomClassesNames.add(elements.ROOMCLASSES_LIST.get(i).getText());
		}
		return roomClassesNames;
	}

	public void createSeason(WebDriver driver, ArrayList<String> test_steps, String SeasonStartDate,
			String SeasonEndDate, String SeasonName, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String isAssignRulesByRoomClass, String isSerasonLevelRules,
			String SeasonRuleSpecificRoomClasses, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String SeasonPolicyType, String SeasonPolicyValues, String isSeasonPolicies)
			throws Exception {
		System.out.println(SeasonStartDate);
		System.out.println(SeasonEndDate);
		String[] seasonName = SeasonName.split("\\|");
		String[] seasonStartDate = SeasonStartDate.split("\\|");
		String[] seasonEndDate = SeasonEndDate.split("\\|");
		String[] seasonMonday = isMonDay.split(",");
		String[] seasonTue = isTueDay.split(",");
		String[] seasonWed = isWednesDay.split(",");
		String[] seasonThur = isThursDay.split(",");
		String[] seasonFri = isFriday.split(",");
		String[] seasonSat = isSaturDay.split(",");
		String[] seasonSun = isSunDay.split(",");
		String[] isAdditionalCharges = isAdditionalChargesForChildrenAdults.split(",");
		String[] maxAdult = MaxAdults.split(",");
		String[] maxPersons = MaxPersons.split(",");
		String[] addtinalAdults = AdditionalAdultsPerNight.split(",");
		String[] addtinalChild = AdditionalChildPerNight.split(",");
		String[] addRoomClass = isAddRoomClassInSeason.split(",");
		String[] extraRoomClass = ExtraRoomClassesInSeason.split(",");
		String[] rate = RatePerNight.split(",");
		String[] extraRoomRate = ExtraRoomClassRatePerNight.split(",");
		String[] extraAdult = ExtraRoomClassMaxAdults.split(",");
		String[] extraPersons = ExtraRoomClassMaxPersons.split(",");
		String[] addtionalAusltPerNight = ExtraRoomClassAdditionalAdultsPerNight.split(",");
		String[] addtionalChildPerNight = ExtraRoomClassAdditionalChildPerNight.split(",");
		String[] isAssignRuleByRoomClass = isAssignRulesByRoomClass.split(",");
		String[] seasonLevalRules = isSerasonLevelRules.split(",");
		String[] seasonRoomClass = SeasonRuleSpecificRoomClasses.split(",");
		String[] ruleType = SeasonRuleType.split(",");
		String[] minStayValue = SeasonRuleMinStayValue.split(",");
		String[] ruleMon = isSeasonRuleOnMonday.split(",");
		String[] ruleTue = isSeasonRuleOnTuesday.split(",");
		String[] ruleWed = isSeasonRuleOnWednesday.split(",");
		String[] ruleThru = isSeasonRuleOnThursday.split(",");
		String[] ruleFri = isSeasonRuleOnFriday.split(",");
		String[] ruleSat = isSeasonRuleOnSaturday.split(",");
		String[] ruleSun = isSeasonRuleOnSunday.split(",");
		String[] isPolicies = isSeasonPolicies.split(",");
		String[] seapolicyVal = SeasonPolicyValues.split(",");
		String[] seaosnPol = SeasonPolicyType.split(",");

		for (int i = 0; i < seasonStartDate.length; i++) {
			test_steps.add("=================== CREATE SEASON(" + seasonName[i] + ") ======================");
			logger.info("=================== CREATE SEASON(" + seasonName[i] + ") ======================");
			if (seasonStartDate[i].contains(",")) {
				String[] stDate = seasonStartDate[i].split(",");
				for (int j = 0; j < stDate.length; j++) {
					selectSeasonDateOnly(driver, test_steps, stDate[j]);
					selectExistingSeason(driver, test_steps);
				}
			} else {
				try {
					selectSeasonDates(driver, test_steps, seasonStartDate[i], seasonEndDate[i]);
				} catch (Exception e) {
					clickCreateSeason(driver, test_steps);
					selectSeasonDates(driver, test_steps, seasonStartDate[i], seasonEndDate[i]);
				}
				enterSeasonName(driver, test_steps, seasonName[i]);
				selectSeasonDays(driver, test_steps, seasonMonday[i], seasonTue[i], seasonWed[i], seasonThur[i],
						seasonFri[i], seasonSat[i], seasonSun[i]);

				clickCreateSeason(driver, test_steps);
				selectSeasonColor(driver, test_steps);

				selectAdditionalChargesForChildrenAdults(driver, test_steps, isAdditionalCharges[i]);
				enterRate(driver, test_steps, rate[i], isAdditionalCharges[i], maxAdult[i], maxPersons[i],
						addtinalAdults[i], addtinalChild[i]);
				if (addRoomClass[i].equalsIgnoreCase("Yes")) {
					addExtraRoomClassInSeason(driver, test_steps, addRoomClass[i], extraRoomClass[i],
							isAdditionalCharges[i], rate[i], extraRoomRate[i], extraAdult[i], extraPersons[i],
							addtionalAusltPerNight[i], addtionalChildPerNight[i]);
				} else {
					addExtraRoomClassInSeason(driver, test_steps, addRoomClass[i], ExtraRoomClassesInSeason,
							isAdditionalChargesForChildrenAdults, ExtraRoomClassRatePerNight,
							ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons,
							ExtraRoomClassAdditionalAdultsPerNight, ExtraRoomClassAdditionalChildPerNight);
				}

				clickRulesRestrictionOnSeason(driver, test_steps);

				clickAssignRulesByRoomClass(driver, test_steps, isAssignRuleByRoomClass[i]);
				if (seasonLevalRules[i].equalsIgnoreCase("Yes")) {
					enterSeasonLevelRules(driver, test_steps, seasonLevalRules[i], isAssignRuleByRoomClass[i],
							seasonRoomClass[i], ruleType[i], minStayValue[i], ruleMon[i], ruleTue[i], ruleWed[i],
							ruleThru[i], ruleFri[i], ruleSat[i], ruleSun[i]);
				} else {
					enterSeasonLevelRules(driver, test_steps, seasonLevalRules[i], isAssignRuleByRoomClass[i],
							SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
							isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday,
							isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday);
				}

				clickSeasonPolicies(driver, test_steps);
				if (Boolean.parseBoolean(isPolicies[i]) == true) {
					selectPolicy(driver, seaosnPol[i], seapolicyVal[i], Boolean.parseBoolean(isPolicies[i]),
							test_steps);
				} else {
					selectPolicy(driver, SeasonPolicyType, SeasonPolicyValues, Boolean.parseBoolean(isPolicies[i]),
							test_steps);
				}
				clickSaveSason(driver, test_steps);
			}
		}
	}

	public void selectSeasonDateOnly(WebDriver driver, ArrayList<String> test_steps, String SeasonStartDate) {
		String startDateMonth = Utility.get_Month(SeasonStartDate);
		String startDateYear = Utility.getYear(SeasonStartDate);
		String startDateDay = Utility.getDay(SeasonStartDate);
		startDateMonth = startDateMonth.toUpperCase();
		String startDateMonthYear = startDateMonth + " " + startDateYear;
		startDateMonthYear = startDateMonthYear.trim();
		String startDate = "//div[text()='" + startDateMonthYear
				+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
		logger.info("startDate: " + startDate);
		Wait.WaitForElement(driver, startDate);
		Wait.waitForElementToBeVisibile(By.xpath(startDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(startDate), driver);
		Actions act = new Actions(driver);
		act.doubleClick(driver.findElement(By.xpath(startDate))).build().perform();
		// driver.findElement(By.xpath(startDate)).click();
		test_steps.add("Selecting start date of the season as : " + SeasonStartDate);
		logger.info("Selecting start date of the season as : " + SeasonStartDate);
	}

	public void selectExistingSeason(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_SeasonName);
		ratessGrid.RatePlan_SeasonName.click();
		String autolist = "//div[@class='autoCompleteList']";
		Wait.WaitForElement(driver, autolist);
		driver.findElement(By.xpath(autolist)).click();
		String addSeason = "//span[text()='Add to season']/..";
		if (driver.findElements(By.xpath(addSeason)).size() > 0) {
			Wait.WaitForElement(driver, addSeason);
			driver.findElement(By.xpath(addSeason)).click();
		}

	}

	public boolean verifySeasonExistInbetweencheckinAndCheckout(WebDriver driver, ArrayList test_steps,
			String checkInDate, String CheckoutDate) throws Exception {
		String date = checkInDate;
		boolean flag = true;
		int days = Utility.getNumberofDays(checkInDate, CheckoutDate);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String today = dtf.format(now);
		int count = Utility.getNumberofDays(today, checkInDate);
		for (int i = 0; i < days; i++) {
			logger.info("date : " + date);
			String startDateMonth = Utility.get_Month(date);
			String startDateYear = Utility.getYear(date);
			String startDateDay = Utility.getDay(date);
			startDateMonth = startDateMonth.toUpperCase();
			String startDateMonthYear = startDateMonth + " " + startDateYear;
			startDateMonthYear = startDateMonthYear.trim();
//			String startDate = "//div[text()='" + startDateMonthYear
//					+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
			String startDate = "//div[@aria-label='" + Utility.parseDate(date, "dd/MM/yyyy", "EEE MMM dd yyyy")
					+ "']//div[text()='" + startDateDay + "']";
			logger.info("startDate: " + startDate);
			Wait.wait10Second();
			String bgcolor = driver.findElement(By.xpath(startDate)).getAttribute("style");
			logger.info("bgcolor : " + bgcolor);
			if (!bgcolor.contains("rgb")) {
				flag = false;
//				return flag;
			}
			if (driver.findElements(
					By.xpath("//div[@aria-label='" + Utility.parseDate(date, "dd/MM/yyyy", "EEE MMM dd yyyy")
							+ "']//div[text()='" + startDateDay + "'][contains(@style,'rgb')]"))
					.size() > 0) {
				flag = true;
				return flag;
			}
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate dateF
			c.add(Calendar.DATE, count + (i + 1)); // same with c.add(Calendar.DAY_OF_MONTH, 1);
			// convert calendar to date
			Date currentDatePlusOne = c.getTime();
			date = dateFormat.format(currentDatePlusOne);
		}
		return flag;
	}

	public ArrayList<String> getPoliciesAppliedFromRatePlan(WebDriver driver) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		ArrayList<String> getPolicies = new ArrayList<>();
		Wait.WaitForElement(driver, OR_NightlyRatePlan.policiesCheckBox);
		for (int i = 0; i < elements.policiesCheckBox.size(); i++) {
			if (elements.policiesCheckBox.get(i).getAttribute("class").contains("ant-checkbox-checked")) {
				Utility.ScrollToViewElementINMiddle(driver, elements.policiesCheckBox.get(i));
				getPolicies.add(elements.policiesNames.get(i).getText());
			}
		}
		return getPolicies;
	}

	public HashMap<String, String> getNameOfPoliciesAppliedFromSeason(WebDriver driver) throws InterruptedException {
		ArrayList<String> getPolicies = new ArrayList<>();
		HashMap<String, String> getPoliciesNames = new HashMap<String, String>();
		getPolicies = getPoliciesAppliedFromRatePlan(driver);
		for (String str : getPolicies) {
			String path = "//div[@class='policy-set-item']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]/following-sibling::span[contains(text(),'"
					+ str + "')]" + "/parent::label/following-sibling::div//span[contains(@class,'ant-radio')]";
			Wait.WaitForElement(driver, path);
			List<WebElement> listOfElements = driver.findElements(By.xpath(path));
			for (int i = 0; i < listOfElements.size(); i++) {
				if (listOfElements.get(i).getAttribute("class").contains("ant-radio-checked")) {
					Utility.ScrollToViewElementINMiddle(driver, listOfElements.get(i));
					String policyNamePath = "//div[@class='policy-set-item']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]/following-sibling::span[contains(text(),'"
							+ str + "')]"
							+ "/parent::label/following-sibling::div//span[contains(@class,'ant-radio')]/following-sibling::span";
					WebElement element = driver.findElement(By.xpath(policyNamePath));
					getPoliciesNames.put(str, element.getText());
				}
			}
		}
		return getPoliciesNames;
	}

	public String getRatePlanHasPoliciesOrNot(WebDriver driver, ArrayList<String> test_steps, String policyName)
			throws InterruptedException {
		String policyNameSelected = null;
		boolean policyAppied = driver
				.findElement(By.xpath("//input[@class='ant-checkbox-input' and @value='" + policyName + "']"))
				.isSelected();
		if (policyAppied) {
			String policyNameXpath = "//input[@class='ant-checkbox-input' and @value='" + policyName
					+ "']/../../..//span[@class='ant-radio ant-radio-checked']/following-sibling::span";
			policyNameSelected = driver.findElement(By.xpath(policyNameXpath)).getText();
			policyNameSelected = policyNameSelected.split("\n")[0];
		}
		test_steps.add("Selected Rate level <b>" + policyName + " policy</b> is captured as <b>" + policyNameSelected
				+ "</b>");
		return policyNameSelected;
	}

	public String getSeasonHasPoliciesOrNot(WebDriver driver, ArrayList<String> test_steps, String policyName)
			throws InterruptedException {
		String policyNameSelected = null;
		boolean policyAppied = driver.findElement(By.xpath(
				"//div[@class='policy-set-item']//input[@class='ant-checkbox-input' and @value='" + policyName + "']"))
				.isSelected();
		if (policyAppied) {
			String policyNameXpath = "//div[@class='policy-set-item']//input[@class='ant-checkbox-input' and @value='"
					+ policyName + "']/../../..//span[@class='ant-radio ant-radio-checked']/following-sibling::span";
			policyNameSelected = driver.findElement(By.xpath(policyNameXpath)).getText();
			policyNameSelected = policyNameSelected.split("\n")[0];
		}
		test_steps.add("Selected Season level <b>" + policyName + " policy</b> is captured as <b>" + policyNameSelected
				+ "</b>");
		return policyNameSelected;
	}

	public String getSeasonLevelPolicyByRoomClass(WebDriver driver, ArrayList<String> test_steps, String policyName,
			String roomClassName) throws InterruptedException {
		Elements_NightlyRate nightlyRate = new Elements_NightlyRate(driver);
		String policyNameSelected = null;
		String roomClassPoliciesCheckBox = nightlyRate.assignPoliciesByRoomClass.getAttribute("aria-checked");
		if (roomClassPoliciesCheckBox.equalsIgnoreCase("true")) {
			boolean policyAppied = driver
					.findElement(By.xpath(
							"//div[text()='" + roomClassName + "']/../../../../../../../../following-sibling::div"
									+ "//input[@class='ant-checkbox-input' and @value='" + policyName + "']"))
					.isSelected();
			if (policyAppied) {
				String policyNameXpath = "//div[text()='" + roomClassName
						+ "']/../../../../../../../../following-sibling::div"
						+ "//input[@class='ant-checkbox-input' and @value='" + policyName + "']/../../.."
						+ "//span[@class='ant-radio ant-radio-checked']/following-sibling::span";
				policyNameSelected = driver.findElement(By.xpath(policyNameXpath)).getText();
				policyNameSelected = policyNameSelected.split("\n")[0];
				test_steps.add("Selected Season level <b>" + policyName + " policy</b> for room class <b>"
						+ roomClassName + "</b> is captured as <b>" + policyNameSelected + "</b>");
			}
		} else {
			policyNameSelected = getSeasonHasPoliciesOrNot(driver, test_steps, policyName);
		}
		return policyNameSelected;
	}
	/*
	 * //Updated by Gangotri Sikheria --11 September 2020 public String
	 * getSeasonLevelPolicyByRoomClass(WebDriver driver, String policyName, String
	 * roomClassName,ArrayList<String> test_steps) throws InterruptedException {
	 * Elements_NightlyRate nightlyRate = new Elements_NightlyRate(driver); String
	 * policyNameSelected = null; String roomClassPoliciesCheckBox =
	 * nightlyRate.assignPoliciesByRoomClass.getAttribute("aria-checked"); if
	 * (roomClassPoliciesCheckBox.equalsIgnoreCase("true")) { boolean
	 * policyApplied=Utility.isElementPresent(driver,
	 * By.xpath("//div[text()='"+roomClassName+"']")); if(policyApplied) { boolean
	 * policyAppied = driver.findElement(By.xpath("//div[text()='"+roomClassName+
	 * "']/../../../../../../../../following-sibling::div"+
	 * "//input[@class='ant-checkbox-input' and @value='"+policyName+"']")).
	 * isSelected(); if (policyAppied) { String policyNameXpath =
	 * "//div[text()='"+roomClassName+
	 * "']/../../../../../../../../following-sibling::div"+
	 * "//input[@class='ant-checkbox-input' and @value='"+policyName+"']/../../.." +
	 * "//span[@class='ant-radio ant-radio-checked']/following-sibling::span";
	 * policyNameSelected = driver.findElement(By.xpath(policyNameXpath)).getText();
	 * policyNameSelected = policyNameSelected.split("\n")[0];
	 * test_steps.add("Selected Season level <b>"
	 * +policyName+" policy</b> for room class <b>"+roomClassName+
	 * "</b> is captured as <b>"+policyNameSelected+"</b>"); } }
	 *
	 * }else { policyNameSelected = getSeasonHasPoliciesOrNot(driver, test_steps,
	 * policyName); } return policyNameSelected; }
	 */

	public ArrayList<String> getMultipleRatePlansSeasonLevelPolicies(WebDriver driver, ArrayList<String> test_steps,
			String policyType, ArrayList<String> ratePlans, ArrayList<String> roomClasses,
			ArrayList<String> checkInDates) throws Exception {
//			HashMap<ArrayList<String>, V>
		RatesGrid ratesGrid = new RatesGrid();
		ArrayList<String> ratePlanLevelPolicies = new ArrayList<>();
		ArrayList<String> seasonLevelPolicies = new ArrayList<>();
		for (int i = 0; i < ratePlans.size(); i++) {
			ratesGrid.searchAndEditRatePlan(driver, test_steps, ratePlans.get(i));
			switchRestrictionAndPoliciesTab(driver, test_steps);
			String ratePlanLevelPolicy = getRatePlanHasPoliciesOrNot(driver, test_steps, policyType);
			ratePlanLevelPolicies.add(ratePlanLevelPolicy);
			switchCalendarTab(driver, test_steps);
			selectSeasonDates(driver, test_steps, checkInDates.get(i));
			clickEditThisSeasonButton(driver, test_steps);
			clickSeasonPolicies(driver, test_steps);
			String seasonLevelPolicy = getSeasonLevelPolicyByRoomClass(driver, test_steps, policyType,
					roomClasses.get(i));
			seasonLevelPolicies.add(seasonLevelPolicy);
			closeSeason(driver, test_steps);
			ratesGrid.closeRatePlan(driver, ratePlans.get(i), test_steps);
			clickYesButton(driver, test_steps);
		}
		return seasonLevelPolicies;
	}

	// Updated by Gangotri Sikheria --11 September 2020
	public String getSeasonLevelPolicyByRoomClass(WebDriver driver, String policyName, String roomClassName,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate nightlyRate = new Elements_NightlyRate(driver);
		String policyNameSelected = null;
		String roomClassPoliciesCheckBox = nightlyRate.assignPoliciesByRoomClass.getAttribute("aria-checked");
		if (roomClassPoliciesCheckBox.equalsIgnoreCase("true")) {
			boolean policyApplied = Utility.isElementPresent(driver, By.xpath("//div[text()='" + roomClassName + "']"));
			if (policyApplied) {
				boolean policyAppied = driver
						.findElement(By.xpath(
								"//div[text()='" + roomClassName + "']/../../../../../../../../following-sibling::div"
										+ "//input[@class='ant-checkbox-input' and @value='" + policyName + "']"))
						.isSelected();
				if (policyAppied) {
					String policyNameXpath = "//div[text()='" + roomClassName
							+ "']/../../../../../../../../following-sibling::div"
							+ "//input[@class='ant-checkbox-input' and @value='" + policyName + "']/../../.."
							+ "//span[@class='ant-radio ant-radio-checked']/following-sibling::span";
					policyNameSelected = driver.findElement(By.xpath(policyNameXpath)).getText();
					policyNameSelected = policyNameSelected.split("\n")[0];
					test_steps.add("Selected Season level <b>" + policyName + " policy</b> for room class <b>"
							+ roomClassName + "</b> is captured as <b>" + policyNameSelected + "</b>");
				}
			}

		} else {
			policyNameSelected = getSeasonHasPoliciesOrNot(driver, test_steps, policyName);
		}
		return policyNameSelected;
	}

	// Added By Adhnan
	public void createSeason(WebDriver driver, ArrayList<String> test_steps, String SeasonStartDate,
			String SeasonEndDate, String SeasonName, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, Boolean isProrateCheckboxCheccked,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String isAssignRulesByRoomClass, String isSerasonLevelRules,
			String SeasonRuleSpecificRoomClasses, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String SeasonPolicyType, String SeasonPolicyValues, String isSeasonPolicies)
			throws Exception {

		String[] seasonName = SeasonName.split(Utility.DELIM);
		String[] seasonStartDate = SeasonStartDate.split(Utility.DELIM);
		String[] seasonEndDate = SeasonEndDate.split(Utility.DELIM);

		for (int i = 0; i < seasonStartDate.length; i++) {

			if (seasonStartDate[i].contains(",")) {
				String[] stDate = seasonStartDate[i].split(",");
				for (int j = 0; j < stDate.length; j++) {
					selectSeasonDateOnly(driver, test_steps, stDate[j]);
					selectExistingSeason(driver, test_steps);
				}

			} else {
				selectSeasonDates(driver, test_steps, seasonStartDate[i], seasonEndDate[i]);
				enterSeasonName(driver, test_steps, seasonName[i]);

				selectSeasonDays(driver, test_steps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay,
						isSunDay);
				clickCreateSeason(driver, test_steps);
				selectSeasonColor(driver, test_steps);
				verifyProrateCheckbox(driver, isProrateCheckboxCheccked);
				selectAdditionalChargesForChildrenAdults(driver, test_steps, isAdditionalChargesForChildrenAdults);

				enterRate(driver, test_steps, RatePerNight, isAdditionalChargesForChildrenAdults, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight);
				addExtraRoomClassInSeason(driver, test_steps, isAddRoomClassInSeason, ExtraRoomClassesInSeason,
						isAdditionalChargesForChildrenAdults, RatePerNight, ExtraRoomClassRatePerNight,
						ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight);
				clickRulesRestrictionOnSeason(driver, test_steps);
				clickAssignRulesByRoomClass(driver, test_steps, isAssignRulesByRoomClass);
				enterSeasonLevelRules(driver, test_steps, isSerasonLevelRules, isAssignRulesByRoomClass,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday);
				clickSeasonPolicies(driver, test_steps);
				selectPolicy(driver, SeasonPolicyType, SeasonPolicyValues, Boolean.parseBoolean(isSeasonPolicies),
						test_steps);
				clickSaveSason(driver, test_steps);
			}
		}
	}

	public ArrayList<String> verifyProrateCheckbox(WebDriver driver, boolean isProrateCheckboxCheccked)
			throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.CheckboxProrate);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrate), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrate), driver);
		if (isProrateCheckboxCheccked) {
			ratesGrid.CheckboxProrate.click();
			testSteps.add("Checked prorate checkbox");

		} else {
			testSteps.add("Already checked prorate checkbox");
		}
		return testSteps;
	}

	// Added By Adhnan 09/24/2020
	public void createUpdateSeasonForPackageIntervalRatePlan(WebDriver driver, ArrayList<String> test_steps,
			String SeasonStartDate, String SeasonEndDate, String SeasonName, String isMonDay, String isTueDay,
			String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String roomClass, String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults,
			String MaxPersons, String AdultsRate, String ChildRate, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String isAssignRulesByRoomClass, String isSeasonLevelRules,
			String SeasonRuleSpecificRoomClasses, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String isSeasonPolicies, String SeasonPolicyType, String SeasonPolicyValues,
			String IsProRateStayInRate,

			String isProRateStayInSeason, String isProRateInRoomClass, String roomClassesWithProRateUnchecked,
			String IsCustomPerNight, String CustomRoomClass, String CustomRatePerNight,
			String isAssignPolicyByRoomClass, String CustomRateAdultdPerNight, String CustomRateChildPerNight,
			String isCustomRatePerNightAdultandChild, String RoomClassInPolicy, boolean validateProRateDefaultvalues,
			String intervalValue) throws Exception {

		ArrayList<String> seasonStartDate = Utility.convertTokenToArrayList(SeasonStartDate, Utility.SEASONDELIM);
		ArrayList<String> seasonEndDate = Utility.convertTokenToArrayList(SeasonEndDate, Utility.SEASONDELIM);
		ArrayList<String> seasonName = Utility.convertTokenToArrayList(SeasonName, Utility.SEASONDELIM);
		ArrayList<String> seasonIsMonDay = Utility.convertTokenToArrayList(isMonDay, Utility.SEASONDELIM);
		ArrayList<String> seasonIsTueDay = Utility.convertTokenToArrayList(isTueDay, Utility.SEASONDELIM);
		ArrayList<String> seasonIsWednesDay = Utility.convertTokenToArrayList(isWednesDay, Utility.SEASONDELIM);
		ArrayList<String> seasonIsThursDay = Utility.convertTokenToArrayList(isThursDay, Utility.SEASONDELIM);
		ArrayList<String> seasonIsFriday = Utility.convertTokenToArrayList(isFriday, Utility.SEASONDELIM);
		ArrayList<String> seasonIsSaturDay = Utility.convertTokenToArrayList(isSaturDay, Utility.SEASONDELIM);
		ArrayList<String> seasonIsSunDay = Utility.convertTokenToArrayList(isSunDay, Utility.SEASONDELIM);
		ArrayList<String> seasonIsAdditionalChargesForChildrenAdults = Utility
				.convertTokenToArrayList(isAdditionalChargesForChildrenAdults, Utility.SEASONDELIM);
		ArrayList<String> seasonRatePerNight = Utility.convertTokenToArrayList(RatePerNight, Utility.SEASONDELIM);
		ArrayList<String> seasonMaxAdults = Utility.convertTokenToArrayList(MaxAdults, Utility.SEASONDELIM);
		ArrayList<String> seasonMaxPersons = Utility.convertTokenToArrayList(MaxPersons, Utility.SEASONDELIM);
		ArrayList<String> adultsRate = Utility.convertTokenToArrayList(AdultsRate, Utility.SEASONDELIM);
		ArrayList<String> childRate = Utility.convertTokenToArrayList(ChildRate, Utility.SEASONDELIM);
		ArrayList<String> seasonIsAddRoomClassInSeason = Utility.convertTokenToArrayList(isAddRoomClassInSeason,
				Utility.SEASONDELIM);
		ArrayList<String> extraRoomClassesInSeason = Utility.convertTokenToArrayList(ExtraRoomClassesInSeason,
				Utility.SEASONDELIM);
		ArrayList<String> extraRoomClassRatePerNight = Utility.convertTokenToArrayList(ExtraRoomClassRatePerNight,
				Utility.SEASONDELIM);
		ArrayList<String> extraRoomClassMaxAdults = Utility.convertTokenToArrayList(ExtraRoomClassMaxAdults,
				Utility.SEASONDELIM);
		ArrayList<String> extraRoomClassMaxPersons = Utility.convertTokenToArrayList(ExtraRoomClassMaxPersons,
				Utility.SEASONDELIM);
		ArrayList<String> extraRoomClassAdditionalAdultsPerNight = Utility
				.convertTokenToArrayList(ExtraRoomClassAdditionalAdultsPerNight, Utility.SEASONDELIM);
		ArrayList<String> extraRoomClassAdditionalChildPerNight = Utility
				.convertTokenToArrayList(ExtraRoomClassAdditionalChildPerNight, Utility.SEASONDELIM);
		ArrayList<String> seasonIsAssignRulesByRoomClass = Utility.convertTokenToArrayList(isAssignRulesByRoomClass,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonLevelRules = Utility.convertTokenToArrayList(isSeasonLevelRules,
				Utility.SEASONDELIM);
		ArrayList<String> seasonRuleSpecificRoomClasses = Utility.convertTokenToArrayList(SeasonRuleSpecificRoomClasses,
				Utility.SEASONDELIM);
		ArrayList<String> seasonRuleType = Utility.convertTokenToArrayList(SeasonRuleType, Utility.SEASONDELIM);
		ArrayList<String> seasonRuleMinStayValue = Utility.convertTokenToArrayList(SeasonRuleMinStayValue,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonRuleOnMonday = Utility.convertTokenToArrayList(isSeasonRuleOnMonday,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonRuleOnTuesday = Utility.convertTokenToArrayList(isSeasonRuleOnTuesday,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonRuleOnWednesday = Utility.convertTokenToArrayList(isSeasonRuleOnWednesday,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonRuleOnThursday = Utility.convertTokenToArrayList(isSeasonRuleOnThursday,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonRuleOnFriday = Utility.convertTokenToArrayList(isSeasonRuleOnFriday,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonRuleOnSaturday = Utility.convertTokenToArrayList(isSeasonRuleOnSaturday,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonRuleOnSunday = Utility.convertTokenToArrayList(isSeasonRuleOnSunday,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsSeasonPolicies = Utility.convertTokenToArrayList(isSeasonPolicies,
				Utility.SEASONDELIM);
		ArrayList<String> seasonPolicyType = Utility.convertTokenToArrayList(SeasonPolicyType, Utility.SEASONDELIM);
		ArrayList<String> seasonPolicyValues = Utility.convertTokenToArrayList(SeasonPolicyValues, Utility.SEASONDELIM);
		ArrayList<String> seasonIsProRateStayInSeason = Utility.convertTokenToArrayList(isProRateStayInSeason,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsProRateInRoomClass = Utility.convertTokenToArrayList(isProRateInRoomClass,
				Utility.SEASONDELIM);
		ArrayList<String> seasonProRateUncheckedRoomClassName = Utility
				.convertTokenToArrayList(roomClassesWithProRateUnchecked, Utility.SEASONDELIM);

		ArrayList<String> seasonIsCustomPerNight = Utility.convertTokenToArrayList(IsCustomPerNight,
				Utility.SEASONDELIM);
		ArrayList<String> customRoomClass = Utility.convertTokenToArrayList(CustomRoomClass, Utility.SEASONDELIM);
		ArrayList<String> customRatePerNight = Utility.convertTokenToArrayList(CustomRatePerNight, Utility.SEASONDELIM);
		ArrayList<String> seasonIsAssignPolicyByRoomClass = Utility.convertTokenToArrayList(isAssignPolicyByRoomClass,
				Utility.SEASONDELIM);
		ArrayList<String> customRateAdultdPerNight = Utility.convertTokenToArrayList(CustomRateAdultdPerNight,
				Utility.SEASONDELIM);
		ArrayList<String> customRateChildPerNight = Utility.convertTokenToArrayList(CustomRateChildPerNight,
				Utility.SEASONDELIM);
		ArrayList<String> seasonIsCustomRatePerNightAdultandChild = Utility
				.convertTokenToArrayList(isCustomRatePerNightAdultandChild, Utility.SEASONDELIM);
		ArrayList<String> seasonRoomClassInPolicy = Utility.convertTokenToArrayList(RoomClassInPolicy,
				Utility.SEASONDELIM);

		for (int i = 0; i < seasonStartDate.size(); i++) {
			// seasonName.get(i) = seasonName.get(i) + Utility.generateRandomString();
			test_steps.add("=================== CREATE SEASON(" + seasonName.get(i) + ") ======================");
			logger.info("=================== CREATE SEASON(" + seasonName.get(i) + ") ======================");
			selectSeasonDates(driver, test_steps, seasonStartDate.get(i), seasonEndDate.get(i));
			enterSeasonName(driver, test_steps, seasonName.get(i));

			selectSeasonDays(driver, test_steps, seasonIsMonDay.get(i), seasonIsTueDay.get(i), seasonIsWednesDay.get(i),
					seasonIsThursDay.get(i), seasonIsFriday.get(i), seasonIsSaturDay.get(i), seasonIsSunDay.get(i));
			try {
				clickReplaceSeason(driver, test_steps);
			} catch (Exception e) {

			}
			clickCreateSeason(driver, test_steps);
			selectSeasonColor(driver, test_steps);

			RatesGrid ratesGrid = new RatesGrid();
			logger.info("proRateStay: " + seasonIsProRateStayInSeason.get(i));
			ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
					Boolean.parseBoolean(seasonIsAdditionalChargesForChildrenAdults.get(i)));
			test_steps.add(
					"Charge for additional adult/child is on? " + seasonIsAdditionalChargesForChildrenAdults.get(i));
			ratesGrid.verifyRoomClassProRateCheckBoxChecked(driver, Boolean.parseBoolean(IsProRateStayInRate),
					roomClass, test_steps);
			boolean isProRateStay = ratesGrid.verifyProrateAtSeasonLevel(driver,
					Boolean.parseBoolean(IsProRateStayInRate), Boolean.parseBoolean(seasonIsProRateStayInSeason.get(i)),
					test_steps);
			logger.info("isProRateStay: " + isProRateStay);
			test_steps.add("Is pro rate stay checked at season ? " + isProRateStay);

			logger.info("customRatePerNight in rate grid: " + CustomRatePerNight);

			ratesGrid.enterRoomClassRates(driver, roomClass, seasonRatePerNight.get(i),
					seasonIsAdditionalChargesForChildrenAdults.get(i), seasonMaxAdults.get(i), seasonMaxPersons.get(i),
					adultsRate.get(i), childRate.get(i), test_steps);
			addExtraRoomClassInSeason(driver, test_steps, seasonIsAddRoomClassInSeason.get(i),
					extraRoomClassesInSeason.get(i));
			if (validateProRateDefaultvalues) {
				test_steps.add("==== VERIFY DEFAULT PRO_RATE VALUES =======");
				logger.info("==== VERIFY DEFAULT PRO_RATE VALUES =======");
				ratesGrid.validateProRateValues(driver, Boolean.parseBoolean(seasonIsProRateStayInSeason.get(i)),
						intervalValue, roomClass, seasonRatePerNight.get(i),
						seasonIsAdditionalChargesForChildrenAdults.get(i), childRate.get(i), adultsRate.get(i),
						test_steps);
				test_steps.add(
						"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
				logger.info(
						"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
			}
			if (seasonIsAddRoomClassInSeason.get(i).equalsIgnoreCase("Yes")) {
				ratesGrid.enterRoomClassRates(driver, extraRoomClassesInSeason.get(i),
						extraRoomClassRatePerNight.get(i), seasonIsAdditionalChargesForChildrenAdults.get(i),
						extraRoomClassMaxAdults.get(i), extraRoomClassMaxPersons.get(i),
						extraRoomClassAdditionalAdultsPerNight.get(i), extraRoomClassAdditionalChildPerNight.get(i),
						test_steps);
				if (validateProRateDefaultvalues) {
					test_steps.add("==== VERIFY DEFAULT PRO_RATE VALUES =======");
					logger.info("==== VERIFY DEFAULT PRO_RATE VALUES =======");
					ratesGrid.validateProRateValues(driver, Boolean.parseBoolean(seasonIsProRateStayInSeason.get(i)),
							intervalValue, extraRoomClassesInSeason.get(i), extraRoomClassRatePerNight.get(i),
							seasonIsAdditionalChargesForChildrenAdults.get(i),
							extraRoomClassAdditionalChildPerNight.get(i), extraRoomClassAdditionalAdultsPerNight.get(i),
							test_steps);

					test_steps.add(
							"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
					logger.info(
							"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
				}
			}

			if (Boolean.parseBoolean(seasonIsProRateStayInSeason.get(i))) {
				ratesGrid.enterProRateValues(driver, seasonIsAdditionalChargesForChildrenAdults.get(i),
						Boolean.parseBoolean(seasonIsProRateInRoomClass.get(i)), customRoomClass.get(i),
						seasonIsCustomPerNight.get(i), customRoomClass.get(i), customRatePerNight.get(i),
						customRateAdultdPerNight.get(i), customRateChildPerNight.get(i),
						seasonIsCustomRatePerNightAdultandChild.get(i), test_steps);
				if (!seasonProRateUncheckedRoomClassName.get(i).equalsIgnoreCase("no")
						|| seasonProRateUncheckedRoomClassName.get(i) != null) {
					ratesGrid.unCheckProRateValuesOfRoomClasses(driver,
							Boolean.parseBoolean(seasonIsProRateInRoomClass.get(i)),
							seasonProRateUncheckedRoomClassName.get(i), test_steps);
				}
			}
			if (seasonIsSeasonLevelRules.get(i).equalsIgnoreCase("Yes")) {
				clickRulesRestrictionOnSeason(driver, test_steps);
				clickAssignRulesByRoomClass(driver, test_steps, seasonIsAssignRulesByRoomClass.get(i));
				enterSeasonLevelRule(driver, test_steps, seasonIsSeasonLevelRules.get(i),
						seasonIsAssignRulesByRoomClass.get(i), seasonRuleSpecificRoomClasses.get(i),
						seasonRuleType.get(i), seasonRuleMinStayValue.get(i), seasonIsSeasonRuleOnMonday.get(i),
						seasonIsSeasonRuleOnTuesday.get(i), seasonIsSeasonRuleOnWednesday.get(i),
						seasonIsSeasonRuleOnThursday.get(i), seasonIsSeasonRuleOnFriday.get(i),
						seasonIsSeasonRuleOnSaturday.get(i), seasonIsSeasonRuleOnSunday.get(i));
			} else {
				test_steps.add("=================== NO RULES ======================");
				logger.info("=================== NO RULES ======================");

			}
			if (Boolean.parseBoolean(seasonIsSeasonPolicies.get(i))) {
				clickSeasonPolicies(driver, test_steps);
				clickAssignPolicyByRoomClass(driver, test_steps, seasonIsAssignPolicyByRoomClass.get(i));
				selectRoomClassInPolicy(driver, seasonIsAssignPolicyByRoomClass.get(i), seasonRoomClassInPolicy.get(i),
						test_steps);
				selectPolicy(driver, seasonPolicyType.get(i), seasonPolicyValues.get(i),
						Boolean.parseBoolean(seasonIsSeasonPolicies.get(i)), test_steps);

			} else {
				test_steps.add("=================== NO POLICIES ======================");
				logger.info("=================== NO POLICIES ======================");

			}

			clickSaveSason(driver, test_steps);

		}
	}

	public void clickAssignPolicyByRoomClass(WebDriver driver, ArrayList<String> test_steps,
			String isAssignPolicyByRoomClass) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AssignPolicyByRoomClass);

		String classAttribute = null;
		classAttribute = ratessGrid.RatePlan_Season_AssignPolicyByRoomClass.getAttribute("aria-checked");
		if (isAssignPolicyByRoomClass.equalsIgnoreCase("Yes")) {
			if (classAttribute.contains("false")) {
				ratessGrid.RatePlan_Season_AssignPolicyByRoomClass.click();
				test_steps.add("Clicking on assign policies by room class");
				logger.info("Clicking on assign policies by room class");
			}
		} else {
			if (classAttribute.contains("true")) {
				ratessGrid.RatePlan_Season_AssignPolicyByRoomClass.click();
				test_steps.add("Unselecting the assign policies by room class");
				logger.info("Unselecting the assign policies by room class");
			}
		}
	}

	// Updated By Adhnan
	public void selectRoomClassInPolicy(WebDriver driver, String isAssignPolicyByRoomClass,
			String SeasonRuleSpecificRoomClass, ArrayList<String> test_steps) throws InterruptedException {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		logger.info("isAssignPolicyByRoomClass: " + isAssignPolicyByRoomClass);
		if (isAssignPolicyByRoomClass.equalsIgnoreCase("Yes")) {
			Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses);
			ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses.click();
			test_steps.add("Clicking on Choose existing room class(s)");
			logger.info("Clicking on Choose existing room class(s)");
			String[] roomClass = SeasonRuleSpecificRoomClass.split(Utility.DELIM);
			logger.info(roomClass);
			for (int i = 0; i < roomClass.length; i++) {
				// ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses.click();
				/*test_steps.add("Clicking on Choose existing room class(s)");
				logger.info("Clicking on Choose existing room class(s)");*/
				String getRoomClass = roomClass[i].trim();
				String selectRoomClassPath = "//div[text()='" + getRoomClass + "']";
				Wait.WaitForElement(driver, selectRoomClassPath);
				WebElement element = driver.findElement(By.xpath(selectRoomClassPath));
				Utility.ScrollToElement_NoWait(element, driver);
				element.click();
				test_steps.add("Select room class in policy:" + roomClass[i]);
			}
		} else {
			test_steps.add("No room class select in policy at season level");

		}
	}

	
	public void selectPolicyAsPerRoomClass(WebDriver driver, String roomClass, HashMap<String, HashMap<String, String>> policies,ArrayList<String> test_steps) throws InterruptedException, ParseException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		clickAssignPolicyByRoomClass(driver, test_steps, "Yes");	
		String[] roomClasses= roomClass.split("\\|");
		for(int i=0;i<roomClasses.length;i++) {
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses);
		Utility.ScrollToElement(ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses, driver);
		Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses);
		test_steps.add("Clicking on Choose existing room class(s)");
		logger.info("Clicking on Choose existing room class(s)");	
		Actions builder = new Actions(driver);
		  builder.moveToElement(ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses).sendKeys(roomClasses[i]).build().perform();
		String selectRoomClassPath = "//div[text()='" + roomClasses[i] + "']";
		Wait.WaitForElement(driver, selectRoomClassPath);
		WebElement element = driver.findElement(By.xpath(selectRoomClassPath));
		Utility.ScrollToElement(element, driver);
		element.click();
		driver.findElement(By.xpath("//div[@class='season-policy']")).click();
		test_steps.add("Select room class in policy:" + roomClasses[i]);
		logger.info("Select room class in policy:" + roomClasses[i]);	

		for(Map.Entry<String, String>  entry: policies.get(roomClasses[i]).entrySet()) {
			selectPolicy(driver, entry.getKey(), entry.getValue(), true, test_steps);
		}
		String setPolicies="//div[@class='add-policy-set']//span[normalize-space(contains(text(),'set of Policies'))]";
		boolean isExist= Utility.isElementEnabled(driver, By.xpath(setPolicies));
		if(isExist) {
			Utility.ScrollToElement(driver.findElement(By.xpath(setPolicies)), driver);
			String text=driver.findElement(By.xpath(setPolicies)).getText();
			driver.findElement(By.xpath(setPolicies)).click();
			test_steps.add("Click on " +text);
			logger.info("Click on " + text);
		}
		}
	}
	// Added BY Adhnan
	public void addExtraRoomClassInSeason(WebDriver driver, ArrayList<String> test_steps, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason) {

		String roomClassName;

		if (isAddRoomClassInSeason.equalsIgnoreCase("Yes")) {
			String[] roomClass = ExtraRoomClassesInSeason.split(Utility.DELIM);
			Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
			Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AddRoomClass);
			ratessGrid.RatePlan_Season_AddRoomClass.click();
			test_steps.add("Clickin on Add Room Class");
			logger.info("Clickin on Add Room Class");
			for (int i = 0; i < roomClass.length; i++) {
				roomClassName = "(//span[text()='" + roomClass[i] + "']/preceding-sibling::span/input)[last()]";
				logger.info("Room Class xpath : " + roomClassName);
				Wait.WaitForElement(driver, roomClassName);
				driver.findElement(By.xpath(roomClassName)).click();
				test_steps.add("Successfully selected room class : " + roomClass[i]);
				logger.info("Successfully selected room class : " + roomClass[i]);
			}
		}
	}

	public void enterSeasonLevelRule(WebDriver driver, ArrayList<String> test_steps, String isSerasonLevelRules,
			String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClass, String SeasonRuleType,
			String SeasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday,
			String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday,
			String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		if (isSerasonLevelRules.equalsIgnoreCase("Yes")) {
			if (isAssignRulesByRoomClass.equalsIgnoreCase("Yes")) {
				Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses);
				ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses.click();
				test_steps.add("Clicking on Choose existing room class(s)");
				logger.info("Clicking on Choose existing room class(s)");
				String[] roomClass = SeasonRuleSpecificRoomClass.split(Utility.DELIM);
				for (int i = 0; i <= (roomClass.length - 1); i++) {
					String roomClassName = "//li[text()='" + roomClass[i] + "']";
					try {
						Utility.ScrollToElement(driver.findElement(By.xpath(roomClassName)), driver);
						driver.findElement(By.xpath(roomClassName)).click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(roomClassName)));
					}
					test_steps.add("Selectin room : " + roomClass[i]);
					logger.info("Selectin room : " + roomClass[i]);
				}
				Wait.wait1Second();
				try {
					Utility.ScrollToElement(driver.findElement(By.xpath("//label[text()='Room class']")), driver);
					driver.findElement(By.xpath("//label[text()='Room class']")).click();
				} catch (Exception e) {
					Utility.clickThroughJavaScript(driver,
							driver.findElement(By.xpath("//label[text()='Room class']")));
				}
				Wait.wait1Second();
			}
			test_steps.add("Season Rule Types : " + SeasonRuleType);
			logger.info("Season Rule Types : " + SeasonRuleType);
			String[] ruleType = SeasonRuleType.split(Utility.DELIM);
			int rulesDaysIndex = 0;
			String mon[] = isSeasonRuleOnMonday.split(Utility.DELIM);
			logger.info("monday : " + isSeasonRuleOnMonday);
			String tue[] = isSeasonRuleOnTuesday.split(Utility.DELIM);
			String wed[] = isSeasonRuleOnWednesday.split(Utility.DELIM);
			String thu[] = isSeasonRuleOnThursday.split(Utility.DELIM);
			String fri[] = isSeasonRuleOnFriday.split(Utility.DELIM);
			String sat[] = isSeasonRuleOnSaturday.split(Utility.DELIM);
			String sun[] = isSeasonRuleOnSunday.split(Utility.DELIM);
			if (ruleType.length > 0 && ruleType != null) {
				for (int i = 0; i < ruleType.length; i++) {
					if (ruleType[i].equalsIgnoreCase("Min Nights")) {
						test_steps.add("Min Nights Rule");
						logger.info("Min Nights Rule");
						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRule);
						Wait.wait1Second();
						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.click();
						Wait.wait1Second();
						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.clear();
						Utility.clearValue(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue);
						Wait.wait1Second();
						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.sendKeys(SeasonRuleMinStayValue);
						test_steps.add("Enter Min Nights Value : " + SeasonRuleMinStayValue);
						logger.info("Enter Min Nights Value : : " + SeasonRuleMinStayValue);
						Wait.wait1Second();
						ArrayList<String> steps = new ArrayList<String>();
						clickRulesRestrictionOnSeason(driver, steps);
						test_steps.add("Min Nights Value : "
								+ ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.getAttribute("value"));
						logger.info("Min Nights Value : "
								+ ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.getAttribute("value"));
						if (!ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.getAttribute("value")
								.equals(SeasonRuleMinStayValue)) {
							ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.sendKeys(Keys.chord(Keys.CONTROL, "a"),
									SeasonRuleMinStayValue);
							test_steps.add("Again Enter Min Nights Value : " + SeasonRuleMinStayValue);
							logger.info("Again Enter Min Nights Value : : " + SeasonRuleMinStayValue);
							Wait.wait1Second();
							test_steps.add("Min Nights Value : "
									+ ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.getAttribute("value"));
							logger.info("Min Nights Value : : "
									+ ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.getAttribute("value"));

						}
						assertEquals(ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.getAttribute("value"),
								SeasonRuleMinStayValue, "Min Stay ENtered Value missmatched");

					} else if (ruleType[i].equalsIgnoreCase("No check-in")) {

						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);

						if (mon[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							driver.findElement(By.xpath(monNoCheckIn)).click();
							test_steps.add("No Check in on monday is selected");
							logger.info("No Check in on monday is selected");
						}

						if (tue[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
							driver.findElement(By.xpath(tueNoCheckIn)).click();
							test_steps.add("No Check in on tuesday is selected");
							logger.info("No Check in on tuesday is selected");
						}

						if (wed[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
							driver.findElement(By.xpath(wedNoCheckIn)).click();
							test_steps.add("No Check in on wednesday is selected");
							logger.info("No Check in on wednesday is selected");
						}

						if (thu[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
							driver.findElement(By.xpath(thuNoCheckIn)).click();
							test_steps.add("No Check in on thursday is selected");
							logger.info("No Check in on thursday is selected");
						}

						if (fri[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							driver.findElement(By.xpath(friNoCheckIn)).click();
							test_steps.add("No Check in on friday is selected");
							logger.info("No Check in on friday is selected");
						}

						if (sat[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							driver.findElement(By.xpath(satNoCheckIn)).click();
							test_steps.add("No Check in on saturday is selected");
							logger.info("No Check in on saturday is selected");
						}

						if (sun[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							driver.findElement(By.xpath(sunNoCheckIn)).click();
							test_steps.add("No Check in on sunday is selected");
							logger.info("No Check in on sunday is selected");
						}
						rulesDaysIndex++;

					} else if (ruleType[i].equalsIgnoreCase("No check-out")) {

						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);

						if (mon[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String monNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
							driver.findElement(By.xpath(monNoCheckOut)).click();
							test_steps.add("No Check out on monday is selected");
							logger.info("No Check out on monday is selected");
						}

						if (tue[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String tueNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
							driver.findElement(By.xpath(tueNoCheckOut)).click();
							test_steps.add("No Check out on tuesday is selected");
							logger.info("No Check out on tuesday is selected");
						}

						if (wed[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String wedNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
							driver.findElement(By.xpath(wedNoCheckOut)).click();
							test_steps.add("No Check out on wednesday is selected");
							logger.info("No Check out on wednesday is selected");
						}

						if (thu[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String thuNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
							driver.findElement(By.xpath(thuNoCheckOut)).click();
							test_steps.add("No Check out on thursday is selected");
							logger.info("No Check out on thursday is selected");
						}

						if (fri[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String friNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
							driver.findElement(By.xpath(friNoCheckOut)).click();
							test_steps.add("No Check out on friday is selected");
							logger.info("No Check out on friday is selected");
						}

						if (sat[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String satNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
							driver.findElement(By.xpath(satNoCheckOut)).click();
							test_steps.add("No Check out on saturday is selected");
							logger.info("No Check out on saturday is selected");
						}

						if (sun[rulesDaysIndex].equalsIgnoreCase("yes")) {
							String sunNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
							driver.findElement(By.xpath(sunNoCheckOut)).click();
							test_steps.add("No Check out on sunday is selected");
							logger.info("No Check out on sunday is selected");
						}
						rulesDaysIndex++;
					}
				}
			}
		}
	}

	public void clickReplaceSeason(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_NightlyRatePlan.RatePlan_Season_Replace));
		if(isExist) {
		Wait.explicit_wait_visibilityof_webelement(ratessGrid.RatePlan_Season_Replace, driver);
		// ratessGrid.RatePlan_Season_Replace.click();
		Utility.ScrollToElement(ratessGrid.RatePlan_Season_Replace, driver);
		ratessGrid.RatePlan_Season_Replace.click();
		test_steps.add("Clicking on Season Replace");
		logger.info("Clicking on Season Replace");
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name:
	 * <getPoliciesAppliedFromRatePlan,getNameOfPoliciesAppliedFromRatePlan,
	 * getChargesType> ' Description: < Get Names of Policies> ' Input parameters: <
	 * WebDriver driver> ' Return value:ArrayList<String> ' Created By: <Gangotri
	 * Sikheria> ' ' Created On:c<MM/dd/yyyy> <08/25/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public HashMap<String, String> getNameOfPoliciesAppliedFromRatePlan(WebDriver driver) throws InterruptedException {
		ArrayList<String> getPolicies = new ArrayList<>();
		HashMap<String, String> getPoliciesNames = new HashMap<String, String>();
		getPolicies = getPoliciesAppliedFromRatePlan(driver);
		for (String str : getPolicies) {
			String path = "//div[@class='center-screen']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]/following-sibling::span[contains(text(),'"
					+ str + "')]" + "/parent::label/following-sibling::div//span[contains(@class,'ant-radio')]";
			Wait.WaitForElement(driver, path);
			List<WebElement> listOfElements = driver.findElements(By.xpath(path));
			for (int i = 0; i < listOfElements.size(); i++) {
				if (listOfElements.get(i).getAttribute("class").contains("ant-radio-checked")) {
					Utility.ScrollToViewElementINMiddle(driver, listOfElements.get(i));
					String policyNamePath = "//div[@class='center-screen']//div[contains(@class,'mx')]//label/span[contains(@class,'ant-checkbox')]/following-sibling::span[contains(text(),'"
							+ str + "')]"
							+ "/parent::label/following-sibling::div//span[contains(@class,'ant-radio')]/following-sibling::span";
					WebElement element = driver.findElement(By.xpath(policyNamePath));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					String text = (String) js.executeScript("var parent = arguments[0];"
							+ "var child = parent.firstChild;" + " var textValue = ''; while(child) { "
							+ "if (child.nodeType === Node.TEXT_NODE)" + " textValue += child.textContent;"
							+ " child = child.nextSibling; " + "} return textValue;", element);
					getPoliciesNames.put(str, text);
				}

			}

		}

		return getPoliciesNames;
	}

	/*
	 * #############################################################################
	 * #####################################################################
	 * ########################
	 *
	 * ' Method Name:
	 * <getPoliciesAppliedFromSeason,getNameOfPoliciesAppliedFromRatePlan,
	 * getChargesType> ' Description: < Get Names of Policies> ' Input parameters: <
	 * WebDriver driver> ' Return value:ArrayList<String> ' Created By: <Gangotri
	 * Sikheria> ' ' Created On:c<MM/dd/yyyy> <08/25/2020>
	 *
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> getPoliciesAppliedFromSeason(WebDriver driver) throws InterruptedException {

		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		ArrayList<String> getPolicies = new ArrayList<>();
		Wait.WaitForElement(driver, OR_NightlyRatePlan.policiesCheckBoxFromSeason);
		for (int i = 0; i < elements.policiesCheckBoxFromSeason.size(); i++) {
			if (elements.policiesCheckBoxFromSeason.get(i).getAttribute("class").contains("ant-checkbox-checked")) {
				Utility.ScrollToViewElementINMiddle(driver, elements.policiesCheckBoxFromSeason.get(i));
				getPolicies.add(elements.policiesNameFromSeason.get(i).getText());
			}
		}
		return getPolicies;
	}

	public String getSeasonHasPoliciesOrNot(WebDriver driver, String policyName) throws InterruptedException {
		String policyNameSelected = null;
		boolean policyAppied = driver.findElement(By.xpath(
				"//div[@class='policy-set-item']//input[@class='ant-checkbox-input' and @value='" + policyName + "']"))
				.isSelected();
		if (policyAppied) {
			String policyNameXpath = "//div[@class='policy-set-item']//input[@class='ant-checkbox-input' and @value='"
					+ policyName + "']/../../..//span[@class='ant-radio ant-radio-checked']/following-sibling::span";
			policyNameSelected = driver.findElement(By.xpath(policyNameXpath)).getText();
			policyNameSelected = policyNameSelected.split("\n")[0];
		}
		return policyNameSelected;
	}

	public ArrayList<String> getAdultCapacity(WebDriver driver, ArrayList<String> roomClass) {
		ArrayList<String> al = null;
		String child = null;
		String path = "(//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[2]";
		WebElement element = driver.findElement(By.xpath(path));
		child = element.getText();
		logger.info(child);
		String[] array = child.split(":");
		array = array[1].split(" ");
		child = array[1];
		logger.info(child);
		al.add(child);
		return al;
	}

	public ArrayList<String> getChildCapacity(WebDriver driver, ArrayList<String> roomClass) {
		ArrayList<String> al = null;
		String child = null;
		String path = "(//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[2]";
		WebElement element = driver.findElement(By.xpath(path));
		child = element.getText();
		logger.info(child);
		String[] array = child.split(":");
		array = array[1].split(" ");
		child = array[1];
		logger.info(child);
		al.add(child);
		return al;
	}

	public ArrayList<String> getMaxAdult(WebDriver driver, ArrayList<String> roomClass) {
		ArrayList<String> al = null;
		String adult = null;
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[1]//input";
		WebElement element = driver.findElement(By.xpath(path));
		adult = element.getAttribute("value");
		logger.info(adult);
		al.add(adult);
		return al;
	}

	public ArrayList<String> getMaxPersons(WebDriver driver, ArrayList<String> roomClass) {
		ArrayList<String> al = null;
		String adult = null;
		String path = "((//div[@class='AddRoomClassList']/label//span[contains(text(),'" + roomClass
				+ "')]/parent::label/following-sibling::span)[3]//li//ul/li)[2]//input";
		WebElement element = driver.findElement(By.xpath(path));
		adult = element.getAttribute("value");
		logger.info(adult);
		al.add(adult);
		return al;
	}

	public void createNightlyRatePlan(WebDriver driver, ArrayList<String> test_steps, String ratePlan, String channels,
			String roomClasses, String seasonStartDate, String seasonEndDate, String seasonName,
			String roomsRatePerNight, String cancellationPolicyName, String depositPolicyName, String checkInPolicyName,
			String noShowPolicyName, boolean assignPolicies) throws InterruptedException {
		createNightlyRatePlan(driver, test_steps, ratePlan, channels, roomClasses, seasonStartDate, seasonEndDate,
				seasonName, roomsRatePerNight, cancellationPolicyName, depositPolicyName, checkInPolicyName,
				noShowPolicyName, assignPolicies, "No", "0", "No", "No", "");
	}

	public void createNightlyRatePlan(WebDriver driver, ArrayList<String> test_steps, String ratePlan, String channels,
			String roomClasses, String seasonStartDate, String seasonEndDate, String seasonName,
			String roomsRatePerNight, String cancellationPolicyName, String depositPolicyName, String checkInPolicyName,
			String noShowPolicyName, boolean assignPolicies, String isMinNightRuleApplied, String minNightCount,
			String isCheckInRuleApplied, String isCheckOutRuleApplied, String promoCode) throws InterruptedException {
		verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", test_steps);

		test_steps.add(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
		logger.info(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
		enterRatePlanName(driver, ratePlan, test_steps);
		enterRateFolioDisplayName(driver, ratePlan, test_steps);

		clickNextButton(driver, test_steps);
		test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
		logger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
		selectChannels(driver, channels, true, test_steps);
		clickNextButton(driver, test_steps);

		test_steps.add("=================== SELECT ROOM CLASSES ======================");
		logger.info("=================== SELECT ROOM CLASSES ======================");
		selectRoomClasses(driver, roomClasses, true, test_steps);
		clickNextButton(driver, test_steps);
		if (!promoCode.equalsIgnoreCase("")) {
			selectRestrictionTypes(driver, "Promo code", true, test_steps);
			promoCode(driver, "Promo code", promoCode, test_steps);
		}
		clickNextButton(driver, test_steps);
		if (assignPolicies) {
			String policies = cancellationPolicyName + "|" + depositPolicyName + "|" + checkInPolicyName + "|"
					+ noShowPolicyName;
			selectPolicy(driver, "Cancellation|Deposit|Check-in|No Show", policies, true, test_steps);
		}
		clickNextButton(driver, test_steps);
		test_steps.add("=================== CREATE SEASON ======================");
		logger.info("=================== CREATE SEASON ======================");

		clickCreateSeason(driver, test_steps);
		selectSeasonDates(driver, test_steps, seasonStartDate, seasonEndDate);
		enterSeasonName(driver, test_steps, seasonName);
		selectSeasonDays(driver, test_steps, "yes", "yes", "yes", "yes", "yes", "yes", "yes");
		clickCreateSeason(driver, test_steps);
		selectSeasonColor(driver, test_steps);
		selectAdditionalChargesForChildrenAdults(driver, test_steps, "false");
		enterRate(driver, test_steps, roomsRatePerNight, "No", "", "", "", "");

		String rulesApplied = "";
		if (isMinNightRuleApplied.equalsIgnoreCase("yes")) {
			rulesApplied += "Min Nights";
		}
		if (isCheckInRuleApplied.equalsIgnoreCase("yes")) {
			if (rulesApplied.length() > 0) {
				rulesApplied += "|";
			}
			rulesApplied += "No check-in";
		}

		if (isCheckOutRuleApplied.equalsIgnoreCase("yes")) {
			if (rulesApplied.length() > 0) {
				rulesApplied += "|";
			}
			rulesApplied += "No check-out";
		}
		clickRulesRestrictionOnSeason(driver, test_steps);
		enterSeasonLevelRules(driver, test_steps, "Yes", "No", "No", rulesApplied, minNightCount, "Yes", "Yes", "Yes",
				"Yes", "Yes", "Yes", "Yes");
		clickSaveSason(driver, test_steps);
		clickCompleteChanges(driver, test_steps);
		clickSaveAsActive(driver, test_steps);

		test_steps.add("=================== RATE PLAN CREATED ======================");
		logger.info("=================== RATE PLAN CREATED ======================");
	}

	public ArrayList<String> getSelectedChannels(WebDriver driver) {
		ArrayList<String> finalChannels = new ArrayList<String>();

		String path = "//h2[text()='Channel']/..//span/input[not(@name='selectAll')]";

		List<WebElement> allChannels = driver.findElements(By.xpath(path));

		for (int i = 0; i < allChannels.size(); i++) {
			if (allChannels.get(i).isSelected()) {
				String foundText = driver
						.findElement(By.xpath("(//h2[text()='Channel']/..//span/input[not(@name='selectAll')])["
								+ (i + 1) + "]/../following-sibling::span"))
						.getText();
				finalChannels.add(foundText);
			}
		}

		return finalChannels;

	}

	public ArrayList<String> getSelectedRoomClass(WebDriver driver) {
		ArrayList<String> finalRoomClass = new ArrayList<String>();

		String path = "//h2[text()='Room class']/..//span/input[not(@name='selectAll')]";

		List<WebElement> allRoomClass = driver.findElements(By.xpath(path));

		for (int i = 0; i < allRoomClass.size(); i++) {
			if (allRoomClass.get(i).isSelected()) {
				String foundText = driver
						.findElement(By.xpath("(//h2[text()='Room class']/..//span/input[not(@name='selectAll')])["
								+ (i + 1) + "]/../following-sibling::span"))
						.getText();
				finalRoomClass.add(foundText);
			}
		}

		return finalRoomClass;

	}

	public ArrayList<String> getAllSeasonsDates(WebDriver driver) {
		ArrayList<String> foundSeasonDates = new ArrayList<String>();

		String path = "//div[contains(@class,'DayPicker-Day')]//div[contains(@style,'background-color')]/..";
		List<WebElement> allSelectedDates = driver.findElements(By.xpath(path));

		for (WebElement ele : allSelectedDates) {
			foundSeasonDates.add(Utility.parseDate(ele.getAttribute("aria-label"), "EEE MMM dd yyyy", "dd/MM/yyyy"));
		}

		return foundSeasonDates;
	}

	public void enterRoomClassSeasonRate(WebDriver driver, ArrayList<String> test_steps, String roomClasses,
			String RatePerNight, String isAdditionalChargesForChildrenAdults, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNightRate, String AdditionalChildPerNightRate) {

		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClasses, Utility.DELIM);
		ArrayList<String> roomRateList = Utility.convertTokenToArrayList(RatePerNight, Utility.DELIM);
		ArrayList<String> maxAdultsList = Utility.convertTokenToArrayList(MaxAdults, Utility.DELIM);
		ArrayList<String> maxPersonsList = Utility.convertTokenToArrayList(MaxPersons, Utility.DELIM);
		ArrayList<String> additionalAdultsRateList = Utility.convertTokenToArrayList(AdditionalAdultsPerNightRate,
				Utility.DELIM);
		ArrayList<String> additionalChildsRateList = Utility.convertTokenToArrayList(AdditionalChildPerNightRate,
				Utility.DELIM);

		String nihtRate = "//input[@name='txtRate']";
		String maxAdults = null, maxPersons = null, addAdultPerNiht = null, AddChildPerNiht = null;

		if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {

			for (int i = 0; i < roomClassList.size(); i++) {

				logger.info("RoomClass Name " + roomClassList.get(i));
				logger.info("RoomRate " + roomRateList.get(i));
				logger.info("Adults " + maxAdultsList.get(i));
				logger.info("persons " + maxPersonsList.get(i));
				logger.info("addAdults Rate " + additionalAdultsRateList.get(i));
				logger.info("addChild Rate " + additionalChildsRateList.get(i));

				nihtRate = "//span[text()='" + roomClassList.get(i) + "']/../..//input[@name='txtRate']";
				Utility.clearValue(driver, driver.findElement(By.xpath(nihtRate)));
				driver.findElement(By.xpath(nihtRate)).sendKeys(roomRateList.get(i));
				test_steps.add("Enter rate for room " + roomClassList.get(i) + " rate " + roomRateList.get(i));
				logger.info("Enter rate for room " + roomClassList.get(i) + " rate " + roomRateList.get(i));

				maxAdults = "(//span[text()='" + roomClassList.get(i) + "']/../..//input[@role='spinbutton'])[2]";
				Utility.clearValue(driver, driver.findElement(By.xpath(maxAdults)));
				driver.findElement(By.xpath(maxAdults)).sendKeys(maxAdultsList.get(i));
				test_steps
						.add("Enter max adults for the room  " + roomClassList.get(i) + " is " + maxAdultsList.get(i));
				logger.info("Enter max adults for the room  " + roomClassList.get(i) + " is " + maxAdultsList.get(i));

				maxPersons = "(//span[text()='" + roomClassList.get(i) + "']/../..//input[@role='spinbutton'])[3]";
				Utility.clearValue(driver, driver.findElement(By.xpath(maxPersons)));
				driver.findElement(By.xpath(maxPersons)).sendKeys(maxPersonsList.get(i));
				test_steps.add(
						"Enter max persons for the room  " + roomClassList.get(i) + " is " + maxPersonsList.get(i));
				logger.info("Enter max persons for the room  " + roomClassList.get(i) + " is " + maxPersonsList.get(i));

				addAdultPerNiht = "//span[text()='" + roomClassList.get(i) + "']/../..//input[@name='txExAdult']";
				Utility.clearValue(driver, driver.findElement(By.xpath(addAdultPerNiht)));
				driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(additionalAdultsRateList.get(i));
				test_steps.add("Enter Additional adults per night for the room  " + roomClassList.get(i) + " is "
						+ additionalAdultsRateList.get(i));
				logger.info("Enter Additional adults per night for the room  " + roomClassList.get(i) + " is "
						+ additionalAdultsRateList.get(i));

				AddChildPerNiht = "//span[text()='" + roomClassList.get(i) + "']/../..//input[@name='txtExChild']";
				Utility.clearValue(driver, driver.findElement(By.xpath(AddChildPerNiht)));
				driver.findElement(By.xpath(AddChildPerNiht)).sendKeys(additionalChildsRateList.get(i));
				test_steps.add("Enter Additional child per night for the room  " + roomClassList.get(i) + " is "
						+ additionalChildsRateList.get(i));
				logger.info("Enter Additional chuld per night for the room  " + roomClassList.get(i) + " is "
						+ additionalChildsRateList.get(i));
			}
		} else {
			for (int i = 0; i < roomClassList.size(); i++) {
				nihtRate = "//span[text()='" + roomClassList.get(i) + "']/../..//input[@name='txtRate']";
				Utility.clearValue(driver, driver.findElement(By.xpath(nihtRate)));
				driver.findElement(By.xpath(nihtRate)).sendKeys(roomRateList.get(i));
				test_steps.add("Enter rate for room " + roomClassList.get(i) + " rate " + roomRateList.get(i));
				logger.info("Enter rate for room " + roomClassList.get(i) + " rate " + roomRateList.get(i));
			}
		}
	}

	public void addExtraRoomClassInSeason(WebDriver driver, ArrayList<String> test_steps, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String isAdditionalChargesForChildrenAdults,
			String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults, String ExtraRoomClassMaxPersons,
			String ExtraRoomClassAdditionalAdultsPerNight, String ExtraRoomClassAdditionalChildPerNight) {

		if (isAddRoomClassInSeason.equalsIgnoreCase("Yes")) {
			Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
			Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AddRoomClass);
			ratessGrid.RatePlan_Season_AddRoomClass.click();
			test_steps.add("Clickin on Add Room Class");
			logger.info("Clickin on Add Room Class");

			ArrayList<String> roomClassList = Utility.convertTokenToArrayList(ExtraRoomClassesInSeason, Utility.DELIM);

			for (String roomClass : roomClassList) {
				String xpath = "(//span[text()='" + roomClass + "']/..//input)[last()]";
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(xpath)));
				test_steps.add("Successfully Selected Room Class : " + roomClass);
				logger.info("Successfully Selected Room Class : " + roomClass);
			}

			enterRoomClassSeasonRate(driver, test_steps, ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight,
					isAdditionalChargesForChildrenAdults, ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons,
					ExtraRoomClassAdditionalAdultsPerNight, ExtraRoomClassAdditionalChildPerNight);
		}
	}

	public void unSelectSeasonLevelExistingRoomClass(WebDriver driver, String roomClassNames,
			ArrayList<String> test_steps) {

		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClassNames, Utility.DELIM);

		for (String roomClass : roomClassList) {
			String xpath = "//div[@class='AddRoomClassList']//span[text()='" + roomClass + "']/..//input";
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(xpath)));
			test_steps.add("Successfully Un Selected Room Class : " + roomClass);
			logger.info("Successfully Un Selected Room Class : " + roomClass);
		}

	}
//	
//	public void enterSeasonLevelRulesRestrictions(WebDriver driver, ArrayList<String> test_steps, String isSeasonLevelRules,
//			String isAssignRulesByRoomClass, String SeasonRuleSpecificRoomClass, String SeasonRuleType,
//			String SeasonRuleMinStayValue, String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday,
//			String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday, String isSeasonRuleOnFriday,
//			String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday) throws InterruptedException {
//		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
//
//		ArrayList<String> seasonRuleSpecificRoomClassList = Utility.convertTokenToArrayList(SeasonRuleSpecificRoomClass, Utility.DELIM);
//		ArrayList<String> seasonRuleTypeList = Utility.convertTokenToArrayList(SeasonRuleType, Utility.DELIM);
//		
//		ArrayList<String> monList = Utility.convertTokenToArrayList(isSeasonRuleOnMonday, Utility.DELIM);
//		ArrayList<String> tueList = Utility.convertTokenToArrayList(isSeasonRuleOnTuesday, Utility.DELIM);
//		ArrayList<String> wedList = Utility.convertTokenToArrayList(isSeasonRuleOnWednesday, Utility.DELIM);
//		ArrayList<String> thuList = Utility.convertTokenToArrayList(isSeasonRuleOnThursday, Utility.DELIM);
//		ArrayList<String> friList = Utility.convertTokenToArrayList(isSeasonRuleOnFriday, Utility.DELIM);
//		ArrayList<String> satList = Utility.convertTokenToArrayList(isSeasonRuleOnSaturday, Utility.DELIM);
//		ArrayList<String> sunList = Utility.convertTokenToArrayList(isSeasonRuleOnSunday, Utility.DELIM);
//		
//		if (isSeasonLevelRules.equalsIgnoreCase("Yes")) {
//			if (isAssignRulesByRoomClass.equalsIgnoreCase("Yes")) {
//				Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses);
//				ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses.click();
//				test_steps.add("Clicking on Choose existing room class(s)");
//				logger.info("Clicking on Choose existing room class(s)");
//				
//
//				for (int i = 0; i < seasonRuleSpecificRoomClassList.size() ; i++) {
//					String roomClassName = "//li[text()='" + seasonRuleSpecificRoomClassList.get(i) + "']";
//					try {
//						Utility.ScrollToElement(driver.findElement(By.xpath(roomClassName)), driver);
//						driver.findElement(By.xpath(roomClassName)).click();
//					} catch (Exception e) {
//						Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(roomClassName)));
//					}
//					test_steps.add("Selectin room : " + seasonRuleSpecificRoomClassList.get(i));
//					logger.info("Selectin room : " + seasonRuleSpecificRoomClassList.get(i));
//				}
//				Wait.wait1Second();
//				try {
//					Utility.ScrollToElement(driver.findElement(By.xpath("//label[text()='Room class']")), driver);
//					driver.findElement(By.xpath("//label[text()='Room class']")).click();
//				} catch (Exception e) {
//					Utility.clickThroughJavaScript(driver,
//							driver.findElement(By.xpath("//label[text()='Room class']")));
//				}
//				Wait.wait1Second();
//			}
//
//			
//			if (seasonRuleTypeList.size() > 0 ) {
//				for (int i = 0; i < seasonRuleTypeList.size(); i++) {
//					if (seasonRuleTypeList.get(i).equalsIgnoreCase("Min Nights")) {
//						Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRule);
//
//						Wait.wait1Second();
//						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.click();
//						Wait.wait1Second();
//						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.clear();
//						Utility.clearValue(driver, ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue);
//						Wait.wait1Second();
//						ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.sendKeys(SeasonRuleMinStayValue);
//					}
//
//					if ((seasonRuleTypeList.size() > 2 && seasonRuleTypeList.get(i).toString().contains("Min Nights"))
//							|| (seasonRuleTypeList.size() > 1 && !seasonRuleTypeList.get(i).toString().contains("Min Nights"))) {
//						
//
//						if (seasonRuleTypeList.get(i).equalsIgnoreCase("No check-in")) {
//
//							Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);
//
//							if (monList.get(i).equalsIgnoreCase("yes")) {
//								String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
//								driver.findElement(By.xpath(monNoCheckIn)).click();
//								test_steps.add("No Check in on monday is selected");
//								logger.info("No Check in on monday is selected");
//							}
//
//							if (tueList.get(i).equalsIgnoreCase("yes")) {
//								String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
//								driver.findElement(By.xpath(tueNoCheckIn)).click();
//								test_steps.add("No Check in on tuesday is selected");
//								logger.info("No Check in on tuesday is selected");
//							}
//
//							if (wedList.get(i).equalsIgnoreCase("yes")) {
//								String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
//								driver.findElement(By.xpath(wedNoCheckIn)).click();
//								test_steps.add("No Check in on wednesday is selected");
//								logger.info("No Check in on wednesday is selected");
//							}
//
//							if (thuList.get(i).equalsIgnoreCase("yes")) {
//								String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
//								driver.findElement(By.xpath(thuNoCheckIn)).click();
//								test_steps.add("No Check in on thursday is selected");
//								logger.info("No Check in on thursday is selected");
//							}
//
//							if (friList.get(i).equalsIgnoreCase("yes")) {
//								String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
//								driver.findElement(By.xpath(friNoCheckIn)).click();
//								test_steps.add("No Check in on friday is selected");
//								logger.info("No Check in on friday is selected");
//							}
//
//							if (satList.get(i).equalsIgnoreCase("yes")) {
//								String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
//								driver.findElement(By.xpath(satNoCheckIn)).click();
//								test_steps.add("No Check in on saturday is selected");
//								logger.info("No Check in on saturday is selected");
//							}
//
//							if (sunList.get(i).equalsIgnoreCase("yes")) {
//								String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
//								driver.findElement(By.xpath(sunNoCheckIn)).click();
//								test_steps.add("No Check in on sunday is selected");
//								logger.info("No Check in on sunday is selected");
//							}
//
//						}
//
//						if (ruleType[i].equalsIgnoreCase("No check-out")) {
//
//							Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);
//
//							if (monList.get(i).equalsIgnoreCase("yes")) {
//								String monNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
//								driver.findElement(By.xpath(monNoCheckOut)).click();
//								test_steps.add("No Check out on monday is selected");
//								logger.info("No Check out on monday is selected");
//							}
//
//							if (tueList.get(i).equalsIgnoreCase("yes")) {
//								String tueNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
//								driver.findElement(By.xpath(tueNoCheckOut)).click();
//								test_steps.add("No Check out on tuesday is selected");
//								logger.info("No Check out on tuesday is selected");
//							}
//
//							if (wedList.get(i).equalsIgnoreCase("yes")) {
//								String wedNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
//								driver.findElement(By.xpath(wedNoCheckOut)).click();
//								test_steps.add("No Check out on wednesday is selected");
//								logger.info("No Check out on wednesday is selected");
//							}
//
//							if (thuList.get(i).equalsIgnoreCase("yes")) {
//								String thuNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
//								driver.findElement(By.xpath(thuNoCheckOut)).click();
//								test_steps.add("No Check out on thursday is selected");
//								logger.info("No Check out on thursday is selected");
//							}
//
//							if (friList.get(i).equalsIgnoreCase("yes")) {
//								String friNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
//								driver.findElement(By.xpath(friNoCheckOut)).click();
//								test_steps.add("No Check out on friday is selected");
//								logger.info("No Check out on friday is selected");
//							}
//
//							if (satList.get(i).equalsIgnoreCase("yes")) {
//								String satNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
//								driver.findElement(By.xpath(satNoCheckOut)).click();
//								test_steps.add("No Check out on saturday is selected");
//								logger.info("No Check out on saturday is selected");
//							}
//
//							if (sunList.get(i).equalsIgnoreCase("yes")) {
//								String sunNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
//								driver.findElement(By.xpath(sunNoCheckOut)).click();
//								test_steps.add("No Check out on sunday is selected");
//								logger.info("No Check out on sunday is selected");
//							}
//
//						}
//					} else {
//
//						if (ruleType[i].equalsIgnoreCase("No check-in")) {
//							Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);
//							if (isSeasonRuleOnMonday.equalsIgnoreCase("yes")) {
//								String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
//								driver.findElement(By.xpath(monNoCheckIn)).click();
//								test_steps.add("No Check in on monday is selected");
//								logger.info("No Check in on monday is selected");
//							}
//
//							if (isSeasonRuleOnTuesday.equalsIgnoreCase("yes")) {
//								String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
//								driver.findElement(By.xpath(tueNoCheckIn)).click();
//								test_steps.add("No Check in on tuesday is selected");
//								logger.info("No Check in on tuesday is selected");
//							}
//
//							if (isSeasonRuleOnWednesday.equalsIgnoreCase("yes")) {
//								String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
//								driver.findElement(By.xpath(wedNoCheckIn)).click();
//								test_steps.add("No Check in on wednesday is selected");
//								logger.info("No Check in on wednesday is selected");
//							}
//
//							if (isSeasonRuleOnThursday.equalsIgnoreCase("yes")) {
//								String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
//								driver.findElement(By.xpath(thuNoCheckIn)).click();
//								test_steps.add("No Check in on thursday is selected");
//								logger.info("No Check in on thursday is selected");
//							}
//
//							if (isSeasonRuleOnFriday.equalsIgnoreCase("yes")) {
//								String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
//								driver.findElement(By.xpath(friNoCheckIn)).click();
//								test_steps.add("No Check in on friday is selected");
//								logger.info("No Check in on friday is selected");
//							}
//
//							if (isSeasonRuleOnSaturday.equalsIgnoreCase("yes")) {
//								String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
//								driver.findElement(By.xpath(satNoCheckIn)).click();
//								test_steps.add("No Check in on saturday is selected");
//								logger.info("No Check in on saturday is selected");
//							}
//
//							if (isSeasonRuleOnSunday.equalsIgnoreCase("yes")) {
//								String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
//								driver.findElement(By.xpath(sunNoCheckIn)).click();
//								test_steps.add("No Check in on sunday is selected");
//								logger.info("No Check in on sunday is selected");
//							}
//
//						}
//
//						if (ruleType[i].equalsIgnoreCase("No check-out")) {
//							Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);
//							if (isSeasonRuleOnMonday.equalsIgnoreCase("yes")) {
//								String monNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
//								driver.findElement(By.xpath(monNoCheckOut)).click();
//								test_steps.add("No Check out on monday is selected");
//								logger.info("No Check out on monday is selected");
//							}
//
//							if (isSeasonRuleOnTuesday.equalsIgnoreCase("yes")) {
//								String tueNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
//								driver.findElement(By.xpath(tueNoCheckOut)).click();
//								test_steps.add("No Check out on tuesday is selected");
//								logger.info("No Check out on tuesday is selected");
//							}
//
//							if (isSeasonRuleOnWednesday.equalsIgnoreCase("yes")) {
//								String wedNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
//								driver.findElement(By.xpath(wedNoCheckOut)).click();
//								test_steps.add("No Check out on wednesday is selected");
//								logger.info("No Check out on wednesday is selected");
//							}
//
//							if (isSeasonRuleOnThursday.equalsIgnoreCase("yes")) {
//								String thuNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
//								driver.findElement(By.xpath(thuNoCheckOut)).click();
//								test_steps.add("No Check out on thursday is selected");
//								logger.info("No Check out on thursday is selected");
//							}
//
//							if (isSeasonRuleOnFriday.equalsIgnoreCase("yes")) {
//								String friNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
//								driver.findElement(By.xpath(friNoCheckOut)).click();
//								test_steps.add("No Check out on friday is selected");
//								logger.info("No Check out on friday is selected");
//							}
//
//							if (isSeasonRuleOnSaturday.equalsIgnoreCase("yes")) {
//								String satNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
//								driver.findElement(By.xpath(satNoCheckOut)).click();
//								test_steps.add("No Check out on saturday is selected");
//								logger.info("No Check out on saturday is selected");
//							}
//
//							if (isSeasonRuleOnSunday.equalsIgnoreCase("yes")) {
//								String sunNoCheckOut = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
//								driver.findElement(By.xpath(sunNoCheckOut)).click();
//								test_steps.add("No Check out on sunday is selected");
//								logger.info("No Check out on sunday is selected");
//							}
//						}
//
//					}
//
//				}
//			}
//		}
//	}

	public void verifyChannelsDisplayed(WebDriver driver, String channelNames, boolean isDisplayed,
			ArrayList<String> test_steps) {

		ArrayList<String> list = Utility.convertTokenToArrayList(channelNames, Utility.DELIM);

		for (String channelName : list) {
			String path = "//*[text()='Channel']/..//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ channelName.toUpperCase() + "']/preceding-sibling::span";

			if (isDisplayed) {

				assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
						"Failed To Verify " + channelName + " Selectable is not Displayed");
				test_steps.add("Successfully Verified Channel Radio/CheckBox is Displayed : " + channelName);
				logger.info("Successfully Verified Channel Radio/CheckBox is Displayed : " + channelName);
				assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
						"Failed To Verify " + channelName + " Selectable is not Enabled");
				test_steps.add("Successfully Verified Channel Radio/CheckBox is Enabled : " + channelName);
				logger.info("Successfully Verified Channel Radio/CheckBox is Enabled : " + channelName);

			} else {
				assertTrue(driver.findElements(By.xpath(path)).size() == 0, "Failed to verfiy Channel is Dispayed");
				test_steps.add("Successfully Verified Channel Radio/CheckBox is not Displayed : " + channelName);
				logger.info("Successfully Verified Channel Radio/CheckBox is not Displayed : " + channelName);
			}

		}
	}

	public void verifyRoomClassDisplayed(WebDriver driver, String RoomClasses, boolean isDisplayed,
			ArrayList<String> test_steps) {

		ArrayList<String> list = Utility.convertTokenToArrayList(RoomClasses, Utility.DELIM);

		for (String RoomClass : list) {

			String path = "//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ RoomClass.toUpperCase() + "']/preceding-sibling::span";

			if (isDisplayed) {

				assertTrue(driver.findElement(By.xpath(path)).isDisplayed(),
						"Failed To Verify " + RoomClass + " Selectable is not Displayed");
				test_steps.add("Successfully Verified RoomClass CheckBox is Displayed : " + RoomClass);
				logger.info("Successfully Verified RoomClass CheckBox is Displayed : " + RoomClass);
				assertTrue(driver.findElement(By.xpath(path)).isEnabled(),
						"Failed To Verify " + RoomClass + " Selectable is not Enabled");
				test_steps.add("Successfully Verified RoomClass CheckBox is Enabled : " + RoomClass);
				logger.info("Successfully Verified RoomClass CheckBox is Enabled : " + RoomClass);

			} else {
				assertTrue(driver.findElements(By.xpath(path)).size() == 0, "Failed to verfiy RoomClass is Dispayed");
				test_steps.add("Successfully Verified RoomClass Radio/CheckBox is not Displayed : " + RoomClass);
				logger.info("Successfully Verified RoomClass Radio/CheckBox is not Displayed : " + RoomClass);
			}

		}
	}

	public boolean selectedAssignRulesByRoomClass(WebDriver driver) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AssignRulesByRoomClass);

		String classAttribute = null;
		classAttribute = ratessGrid.RatePlan_Season_AssignRulesByRoomClass.getAttribute("aria-checked");
		boolean isSelected = false;
		if (classAttribute.contains("false")) {
			isSelected = false;

		} else if (classAttribute.contains("true")) {
			isSelected = true;

		}
		return isSelected;
	}

	public ArrayList<String> selectedRulesSpecificRoomClasses(WebDriver driver) {
		String path = "//div[text()='Choose existing room class(s)']/..//ul/li[@class='ant-select-selection__choice']/div";

		ArrayList<String> roomClasses = new ArrayList<String>();

		for (WebElement ele : driver.findElements(By.xpath(path))) {
			roomClasses.add(ele.getText());
		}
		logger.info(roomClasses);
		return roomClasses;
	}

	public ArrayList<String> selectedRulesType(WebDriver driver) {
		ArrayList<String> rulesTypes = new ArrayList<String>();
		String minStay = "//span[text()='Min nights']/preceding-sibling::span/input/..";
		String noCheckIn = "//span[text()='No check-in']/preceding-sibling::span/input/..";
		String noCheckOut = "//span[text()='No check-out']/preceding-sibling::span/input/..";

		if (driver.findElement(By.xpath(minStay)).getAttribute("class").contains("checked")) {
			rulesTypes.add("Min nights");
		}

		if (driver.findElement(By.xpath(noCheckIn)).getAttribute("class").contains("checked")) {
			rulesTypes.add("No check-in");
		}

		if (driver.findElement(By.xpath(noCheckOut)).getAttribute("class").contains("checked")) {
			rulesTypes.add("No check-out");
		}
		return rulesTypes;

	}

	public HashMap<String, String> selectedRulesValues(WebDriver driver, ArrayList<String> selectedRulesTypes) {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

		HashMap<String, String> rulesTypeValues = new HashMap<String, String>();

		for (String str : selectedRulesTypes) {
			if (str.equalsIgnoreCase("Min nights")) {
				rulesTypeValues.put(str, ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.getAttribute("value"));
			} else if (str.equalsIgnoreCase("No check-in")) {
				String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
				boolean isMon = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)));

				String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
				boolean isTue = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)));

				String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
				boolean isWed = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)));

				String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
				boolean isThu = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)));

				String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
				boolean isFri = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)));

				String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
				boolean isSat = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)));

				String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
				boolean isSun = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)));

				String selectedDays = isMon + Utility.DELIM + isTue + Utility.DELIM + isWed + Utility.DELIM + isThu
						+ Utility.DELIM + isFri + Utility.DELIM + isSat + Utility.DELIM + isSun;
				rulesTypeValues.put(str, selectedDays);

			} else if (str.equalsIgnoreCase("No check-out")) {
				String monNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
				boolean isMon = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)));

				String tueNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
				boolean isTue = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)));

				String wedNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";
				boolean isWed = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)));

				String thuNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";
				boolean isThu = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)));

				String friNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
				boolean isFri = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)));

				String satNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
				boolean isSat = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)));

				String sunNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
				boolean isSun = Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)));

				String selectedDays = isMon + Utility.DELIM + isTue + Utility.DELIM + isWed + Utility.DELIM + isThu
						+ Utility.DELIM + isFri + Utility.DELIM + isSat + Utility.DELIM + isSun;
				rulesTypeValues.put(str, selectedDays);
			}

		}

		logger.info(rulesTypeValues);
		return rulesTypeValues;
	}

	public boolean isRuleApplied(String date, String ruleDays) {
		ArrayList<String> list = Utility.convertTokenToArrayList(ruleDays, Utility.DELIM);
		String day = Utility.parseDate(date, "dd/MM/yyyy", "EEEE");
		boolean isDay = false;
		if (day.equalsIgnoreCase("Monday")) {
			isDay = Boolean.parseBoolean(list.get(0));
		} else if (day.equalsIgnoreCase("Tuesday")) {
			isDay = Boolean.parseBoolean(list.get(1));
		} else if (day.equalsIgnoreCase("Wednesday")) {
			isDay = Boolean.parseBoolean(list.get(2));
		} else if (day.equalsIgnoreCase("Thursday")) {
			isDay = Boolean.parseBoolean(list.get(3));
		} else if (day.equalsIgnoreCase("Friday")) {
			isDay = Boolean.parseBoolean(list.get(4));
		} else if (day.equalsIgnoreCase("Saturday")) {
			isDay = Boolean.parseBoolean(list.get(5));
		} else if (day.equalsIgnoreCase("Sunday")) {
			isDay = Boolean.parseBoolean(list.get(6));
		}

		return isDay;
	}

	// Added By Adhnan 10/09/2020
	public String verifyRoomClassesExist(WebDriver driver, String roomClasses, ArrayList<String> allRoomClasses) {
		String[] roomClassesList = roomClasses.split(Utility.DELIM);
		int foundRoomClasses = 0;
		for (int i = 0; i < roomClassesList.length; i++) {
			String tempRoomClass = roomClassesList[i];
			logger.info("Temp Room Class : " + tempRoomClass);
			for (int j = 0; j < allRoomClasses.size(); j++) {
				if (allRoomClasses.get(j).equals(tempRoomClass)) {
					foundRoomClasses++;
					break;
				}
			}
		}

		logger.info("Required Room Classes : " + roomClasses);
		logger.info("Found Room Classes : " + foundRoomClasses);
		if (foundRoomClasses == roomClassesList.length) {
			return roomClasses;
		} else {
			return "";
		}

	}

	// Added By Adhnan 09/24/2020
	public void createUpdateSingleOrMultipleSeason(WebDriver driver, ArrayList<String> test_steps, boolean update,
			String SeasonStartDate, String SeasonEndDate, String SeasonName, String isMonDay, String isTueDay,
			String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String roomClass, String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults,
			String MaxPersons, String AdultsRate, String ChildRate, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String isAssignRulesByRoomClass, String isSeasonLevelRules,
			String SeasonRuleSpecificRoomClasses, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String isSeasonPolicies, String SeasonPolicyType, String SeasonPolicyValues,
			String isAssignPolicyByRoomClass, String RoomClassInPolicy, String IsProRateStayInRate,
			String isProRateStayInSeason, String isProRateInRoomClass, String roomClassesWithProRateUnchecked,
			String IsCustomPerNight, String CustomRoomClass, String CustomRatePerNight, String CustomRateAdultdPerNight,
			String CustomRateChildPerNight, String isCustomRatePerNightAdultandChild,
			boolean validateProRateDefaultvalues, String intervalValue, boolean intervalRate) throws Exception {

		String[] seasonStartDate = SeasonStartDate.split(Utility.SEASONDELIM);
		String[] seasonEndDate = SeasonEndDate.split(Utility.SEASONDELIM);
		String[] seasonName = SeasonName.split(Utility.SEASONDELIM);
		String[] seasonIsMonDay = isMonDay.split(Utility.SEASONDELIM);
		String[] seasonIsTueDay = isTueDay.split(Utility.SEASONDELIM);
		String[] seasonIsWednesDay = isWednesDay.split(Utility.SEASONDELIM);
		String[] seasonIsThursDay = isThursDay.split(Utility.SEASONDELIM);
		String[] seasonIsFriday = isFriday.split(Utility.SEASONDELIM);
		String[] seasonIsSaturDay = isSaturDay.split(Utility.SEASONDELIM);
		String[] seasonIsSunDay = isSunDay.split(Utility.SEASONDELIM);
		String[] seasonIsAdditionalChargesForChildrenAdults = isAdditionalChargesForChildrenAdults
				.split(Utility.SEASONDELIM);
		String[] seasonRatePerNight = RatePerNight.split(Utility.SEASONDELIM);
		String[] seasonMaxAdults = MaxAdults.split(Utility.SEASONDELIM);
		String[] seasonMaxPersons = MaxPersons.split(Utility.SEASONDELIM);
		String[] adultsRate = AdultsRate.split(Utility.SEASONDELIM);
		String[] childRate = ChildRate.split(Utility.SEASONDELIM);
		String[] seasonIsAddRoomClassInSeason = isAddRoomClassInSeason.split(Utility.SEASONDELIM);
		String[] extraRoomClassesInSeason = ExtraRoomClassesInSeason.split(Utility.SEASONDELIM);
		String[] extraRoomClassRatePerNight = ExtraRoomClassRatePerNight.split(Utility.SEASONDELIM);
		String[] extraRoomClassMaxAdults = ExtraRoomClassMaxAdults.split(Utility.SEASONDELIM);
		String[] extraRoomClassMaxPersons = ExtraRoomClassMaxPersons.split(Utility.SEASONDELIM);
		String[] extraRoomClassAdditionalAdultsPerNight = ExtraRoomClassAdditionalAdultsPerNight
				.split(Utility.SEASONDELIM);
		String[] extraRoomClassAdditionalChildPerNight = ExtraRoomClassAdditionalChildPerNight
				.split(Utility.SEASONDELIM);
		String[] seasonIsAssignRulesByRoomClass = isAssignRulesByRoomClass.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonLevelRules = isSeasonLevelRules.split(Utility.SEASONDELIM);
		String[] seasonRuleSpecificRoomClasses = SeasonRuleSpecificRoomClasses.split(Utility.SEASONDELIM);
		String[] seasonRuleType = SeasonRuleType.split(Utility.SEASONDELIM);
		String[] seasonRuleMinStayValue = SeasonRuleMinStayValue.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonRuleOnMonday = isSeasonRuleOnMonday.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonRuleOnTuesday = isSeasonRuleOnTuesday.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonRuleOnWednesday = isSeasonRuleOnWednesday.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonRuleOnThursday = isSeasonRuleOnThursday.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonRuleOnFriday = isSeasonRuleOnFriday.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonRuleOnSaturday = isSeasonRuleOnSaturday.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonRuleOnSunday = isSeasonRuleOnSunday.split(Utility.SEASONDELIM);
		String[] seasonIsSeasonPolicies = isSeasonPolicies.split(Utility.SEASONDELIM);
		String[] seasonPolicyType = SeasonPolicyType.split(Utility.SEASONDELIM);
		String[] seasonPolicyValues = SeasonPolicyValues.split(Utility.SEASONDELIM);
		String[] seasonRoomClassInPolicy = RoomClassInPolicy.split(Utility.SEASONDELIM);
		String[] seasonIsAssignPolicyByRoomClass = isAssignPolicyByRoomClass.split(Utility.SEASONDELIM);

		String[] seasonIsProRateStayInSeason = null;
		String[] seasonIsProRateInRoomClass = null;
		String[] seasonProRateUncheckedRoomClassName = null;

		String[] seasonIsCustomPerNight = null;
		String[] customRoomClass = null;
		String[] customRatePerNight = null;
		String[] customRateAdultdPerNight = null;
		String[] customRateChildPerNight = null;
		String[] seasonIsCustomRatePerNightAdultandChild = null;

		if (intervalRate) {
			seasonIsProRateStayInSeason = isProRateStayInSeason.split(Utility.SEASONDELIM);
			seasonIsProRateInRoomClass = isProRateInRoomClass.split(Utility.SEASONDELIM);
			seasonProRateUncheckedRoomClassName = roomClassesWithProRateUnchecked.split(Utility.SEASONDELIM);
			seasonIsCustomPerNight = IsCustomPerNight.split(Utility.SEASONDELIM);
			customRoomClass = CustomRoomClass.split(Utility.SEASONDELIM);
			customRatePerNight = CustomRatePerNight.split(Utility.SEASONDELIM);
			customRateAdultdPerNight = CustomRateAdultdPerNight.split(Utility.SEASONDELIM);
			customRateChildPerNight = CustomRateChildPerNight.split(Utility.SEASONDELIM);
			seasonIsCustomRatePerNightAdultandChild = isCustomRatePerNightAdultandChild.split(Utility.SEASONDELIM);
		}

		for (int i = 0; i < seasonStartDate.length; i++) {
			seasonName[i] = seasonName[i] + Utility.generateRandomString();
			test_steps.add("=================== CREATE SEASON(" + seasonName[i] + ") ======================");
			logger.info("=================== CREATE SEASON(" + seasonName[i] + ") ======================");
			selectSeasonDates(driver, test_steps, seasonStartDate[i], seasonEndDate[i]);
			enterSeasonName(driver, test_steps, seasonName[i]);

			selectSeasonDays(driver, test_steps, seasonIsMonDay[i], seasonIsTueDay[i], seasonIsWednesDay[i],
					seasonIsThursDay[i], seasonIsFriday[i], seasonIsSaturDay[i], seasonIsSunDay[i]);
			if (update) {
				try {
					clickReplaceSeason(driver, test_steps);
				} catch (Exception e) {

				}
			}
			clickCreateSeason(driver, test_steps);
			selectSeasonColor(driver, test_steps);
			RatesGrid ratesGrid = new RatesGrid();
			ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
					Boolean.parseBoolean(seasonIsAdditionalChargesForChildrenAdults[i]));
			test_steps.add("Charge for additional adult/child is on? " + seasonIsAdditionalChargesForChildrenAdults[i]);

			if (intervalRate) {
				logger.info("proRateStay: " + seasonIsProRateStayInSeason[i]);
				ratesGrid.verifyRoomClassProRateCheckBoxChecked(driver, Boolean.parseBoolean(IsProRateStayInRate),
						roomClass, test_steps);
				boolean isProRateStay = ratesGrid.verifyProrateAtSeasonLevel(driver,
						Boolean.parseBoolean(IsProRateStayInRate), Boolean.parseBoolean(seasonIsProRateStayInSeason[i]),
						test_steps);
				logger.info("isProRateStay: " + isProRateStay);
				test_steps.add("Is pro rate stay checked at season ? " + isProRateStay);

				logger.info("customRatePerNight in rate grid: " + CustomRatePerNight);
			}
			ratesGrid.enterRoomClassRates(driver, roomClass, seasonRatePerNight[i],
					seasonIsAdditionalChargesForChildrenAdults[i], seasonMaxAdults[i], seasonMaxPersons[i],
					adultsRate[i], childRate[i], test_steps);
			addExtraRoomClassInSeason(driver, test_steps, seasonIsAddRoomClassInSeason[i], extraRoomClassesInSeason[i]);
			if (validateProRateDefaultvalues && intervalRate) {
				test_steps.add("==== VERIFY DEFAULT PRO_RATE VALUES =======");
				logger.info("==== VERIFY DEFAULT PRO_RATE VALUES =======");
				ratesGrid.validateProRateValues(driver, Boolean.parseBoolean(seasonIsProRateStayInSeason[i]),
						intervalValue, roomClass, seasonRatePerNight[i], seasonIsAdditionalChargesForChildrenAdults[i],
						childRate[i], adultsRate[i], test_steps);
				test_steps.add(
						"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
				logger.info(
						"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
			}

			if (seasonIsAddRoomClassInSeason[i].equalsIgnoreCase("Yes")) {
				ratesGrid.enterRoomClassRates(driver, extraRoomClassesInSeason[i], extraRoomClassRatePerNight[i],
						seasonIsAdditionalChargesForChildrenAdults[i], extraRoomClassMaxAdults[i],
						extraRoomClassMaxPersons[i], extraRoomClassAdditionalAdultsPerNight[i],
						extraRoomClassAdditionalChildPerNight[i], test_steps);
				if (validateProRateDefaultvalues && intervalRate) {
					test_steps.add("==== VERIFY DEFAULT PRO_RATE VALUES =======");
					logger.info("==== VERIFY DEFAULT PRO_RATE VALUES =======");
					ratesGrid.validateProRateValues(driver, Boolean.parseBoolean(seasonIsProRateStayInSeason[i]),
							intervalValue, extraRoomClassesInSeason[i], extraRoomClassRatePerNight[i],
							seasonIsAdditionalChargesForChildrenAdults[i], extraRoomClassAdditionalChildPerNight[i],
							extraRoomClassAdditionalAdultsPerNight[i], test_steps);

					test_steps.add(
							"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
					logger.info(
							"Successfully verified that default pro-rate value are room class entered value divided by selected interval value");
				}
			}
			if (intervalRate) {
				if (Boolean.parseBoolean(seasonIsProRateStayInSeason[i])) {
					ratesGrid.enterProRateValues(driver, seasonIsAdditionalChargesForChildrenAdults[i],
							Boolean.parseBoolean(seasonIsProRateInRoomClass[i]), customRoomClass[i],
							seasonIsCustomPerNight[i], customRoomClass[i], customRatePerNight[i],
							customRateAdultdPerNight[i], customRateChildPerNight[i],
							seasonIsCustomRatePerNightAdultandChild[i], test_steps);
					if (!seasonProRateUncheckedRoomClassName[i].equalsIgnoreCase("no")
							|| seasonProRateUncheckedRoomClassName[i] != null) {
						ratesGrid.unCheckProRateValuesOfRoomClasses(driver,
								Boolean.parseBoolean(seasonIsProRateInRoomClass[i]),
								seasonProRateUncheckedRoomClassName[i], test_steps);
					}
				}
			}
			if (seasonIsSeasonLevelRules[i].equalsIgnoreCase("Yes")) {
				clickRulesRestrictionOnSeason(driver, test_steps);
				clickAssignRulesByRoomClass(driver, test_steps, seasonIsAssignRulesByRoomClass[i]);
				enterSeasonLevelRule(driver, test_steps, seasonIsSeasonLevelRules[i], seasonIsAssignRulesByRoomClass[i],
						seasonRuleSpecificRoomClasses[i], seasonRuleType[i], seasonRuleMinStayValue[i],
						seasonIsSeasonRuleOnMonday[i], seasonIsSeasonRuleOnTuesday[i], seasonIsSeasonRuleOnWednesday[i],
						seasonIsSeasonRuleOnThursday[i], seasonIsSeasonRuleOnFriday[i], seasonIsSeasonRuleOnSaturday[i],
						seasonIsSeasonRuleOnSunday[i]);
			} else {
				test_steps.add("=================== NO RULES ======================");
				logger.info("=================== NO RULES ======================");

			}
			if (Boolean.parseBoolean(seasonIsSeasonPolicies[i])) {
				clickSeasonPolicies(driver, test_steps);
				clickAssignPolicyByRoomClass(driver, test_steps, seasonIsAssignPolicyByRoomClass[i]);
				selectRoomClassInPolicy(driver, seasonIsAssignPolicyByRoomClass[i], seasonRoomClassInPolicy[i],
						test_steps);
				selectPolicy(driver, seasonPolicyType[i], seasonPolicyValues[i],
						Boolean.parseBoolean(seasonIsSeasonPolicies[i]), test_steps);

			} else {
				test_steps.add("=================== NO POLICIES ======================");
				logger.info("=================== NO POLICIES ======================");

			}

			clickSaveSason(driver, test_steps);

		}
	}

	// Added by Adhnan 10/12/2020
	public boolean getSelectedRatePlanStatus(WebDriver driver) {
		String path = "//label[text()='Rate plan status']/following-sibling::div//div[@class='ant-select-selection-selected-value']";
		String status = driver.findElement(By.xpath(path)).getText();
		if (status.equals("Active")) {
			return true;
		} else {
			return false;
		}
	}

	// Added By Adhnan
	public void createSeasons(WebDriver driver, ArrayList<String> test_steps, String SeasonStartDate,
			String SeasonEndDate, String SeasonName, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String isAssignRulesByRoomClass, String isSerasonLevelRules,
			String SeasonRuleSpecificRoomClasses, String SeasonRuleType, String SeasonRuleMinStayValue,
			String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
			String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
			String isSeasonRuleOnSunday, String SeasonPolicyType, String SeasonPolicyValues, String isSeasonPolicies)
			throws Exception {

		String[] seasonName = SeasonName.split(Utility.DELIM);
		String[] seasonStartDate = SeasonStartDate.split(Utility.DELIM);
		String[] seasonEndDate = SeasonEndDate.split(Utility.DELIM);

		for (int i = 0; i < seasonStartDate.length; i++) {

			if (seasonStartDate[i].contains(",")) {
				String[] stDate = seasonStartDate[i].split(",");
				for (int j = 0; j < stDate.length; j++) {
					selectSeasonDateOnly(driver, test_steps, stDate[j]);
					selectExistingSeason(driver, test_steps);
				}

			} else {
				selectSeasonDates(driver, test_steps, seasonStartDate[i], seasonEndDate[i]);
				enterSeasonName(driver, test_steps, seasonName[i]);

				selectSeasonDays(driver, test_steps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay,
						isSunDay);
				clickCreateSeason(driver, test_steps);
				selectSeasonColor(driver, test_steps);
				selectAdditionalChargesForChildrenAdults(driver, test_steps, isAdditionalChargesForChildrenAdults);

				enterRate(driver, test_steps, RatePerNight, isAdditionalChargesForChildrenAdults, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight);
				addExtraRoomClassInSeason(driver, test_steps, isAddRoomClassInSeason, ExtraRoomClassesInSeason,
						isAdditionalChargesForChildrenAdults, RatePerNight, ExtraRoomClassRatePerNight,
						ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight);
				clickRulesRestrictionOnSeason(driver, test_steps);
				clickAssignRulesByRoomClass(driver, test_steps, isAssignRulesByRoomClass);
				enterSeasonLevelRules(driver, test_steps, isSerasonLevelRules, isAssignRulesByRoomClass,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday);
				clickSeasonPolicies(driver, test_steps);
				selectPolicy(driver, SeasonPolicyType, SeasonPolicyValues, Boolean.parseBoolean(isSeasonPolicies),
						test_steps);
				clickSaveSason(driver, test_steps);
			}
		}
	}

	public void createSeasonForIntervalRatePlan(WebDriver driver, ArrayList<String> test_steps, String SeasonStartDate,
			String SeasonEndDate, String SeasonName, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, String roomClass,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdultsRate, String ChildRate, String isAddRoomClassInSeason, String ExtraRoomClassesInSeason,
			String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults, String ExtraRoomClassMaxPersons,
			String ExtraRoomClassAdditionalAdultsPerNight, String ExtraRoomClassAdditionalChildPerNight,
			String isAssignRulesByRoomClass, String isSerasonLevelRules, String SeasonRuleSpecificRoomClasses,
			String SeasonRuleType, String SeasonRuleMinStayValue, String isSeasonRuleOnMonday,
			String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday,
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String SeasonPolicyType, String SeasonPolicyValues, String isSeasonPolicies, String IsProRateStayInRate,

			String isProRateStayInSeason, String isProRateInRoomClass, String ProRateRoomClassName,
			String IsCustomPerNight, String CustomRoomClass, String CustomRatePerNight,
			String isAssignPolicyByRoomClass, String CustomRateAdultdPerNight, String CustomRateChildPerNight,
			String isCustomRatePerNightAdultandChild, String RoomClassInPolicy) throws Exception {

		String[] seasonName = SeasonName.split("\\" + Utility.DELIM);
		String[] seasonStartDate = SeasonStartDate.split("\\" + Utility.DELIM);
		String[] seasonEndDate = SeasonEndDate.split("\\" + Utility.DELIM);

		String[] isMonDayList = isMonDay.split("\\" + Utility.DELIM);
		String[] isTueDayList = isTueDay.split("\\" + Utility.DELIM);
		String[] isWedDayList = isWednesDay.split("\\" + Utility.DELIM);
		String[] isThuesDayList = isThursDay.split("\\" + Utility.DELIM);
		String[] isFridayList = isFriday.split("\\" + Utility.DELIM);
		String[] isSaturDayList = isSaturDay.split("\\" + Utility.DELIM);
		String[] isSunDayList = isSunDay.split("\\" + Utility.DELIM);

		String[] proRateStay = isProRateStayInSeason.split("\\" + Utility.DELIM);
		String[] additionalChargesForChildrenAdults = isAdditionalChargesForChildrenAdults.split("\\" + Utility.DELIM);
		String[] roomClassInPolicy = RoomClassInPolicy.split("\\" + Utility.DELIM);
		String[] isRoomClassInPolicy = isAssignPolicyByRoomClass.split("\\" + Utility.DELIM);

		for (int i = 0; i < seasonStartDate.length; i++) {

			if (seasonStartDate[i].contains(",")) {
				String[] stDate = seasonStartDate[i].split(",");
				for (int j = 0; j < stDate.length; j++) {
					selectSeasonDateOnly(driver, test_steps, stDate[j]);
					selectExistingSeason(driver, test_steps);
				}

			} else {

//				if (i==0) {
//					isMonDayList = isMonDayList[i].split("\\,");
//					isTueDayList = isTueDayList[i].split("\\,");
//					isWedDayList = isWedDayList[i].split("\\,");
//					isThuesDayList = isThuesDayList[i].split("\\,");
//					isFridayList = isFridayList[i].split("\\,");
//					isSaturDayList = isSaturDayList[i].split("\\,");
//					isSunDayList = isSunDayList[i].split("\\,");
//				}

				selectSeasonDates(driver, test_steps, seasonStartDate[i], seasonEndDate[i]);
				enterSeasonName(driver, test_steps, seasonName[i]);

				logger.info("isMonDayList: " + isMonDayList[i]);
				logger.info("isTueDayList: " + isTueDayList[i]);
				logger.info("isWedDayList: " + isWedDayList[i]);
				logger.info("isThuesDayList: " + isThuesDayList[i]);
				logger.info("isFridayList: " + isFridayList[i]);
				logger.info("isSaturDayList: " + isSaturDayList[i]);
				logger.info("isSunDayList: " + isSunDayList[i]);

				selectSeasonDays(driver, test_steps, isMonDayList[i], isTueDayList[i], isWedDayList[i],
						isThuesDayList[i], isFridayList[i], isSaturDayList[i], isSunDayList[i]);

				clickCreateSeason(driver, test_steps);
				selectSeasonColor(driver, test_steps);

				RatesGrid ratesGrid = new RatesGrid();
				logger.info("proRateStay: " + proRateStay[i]);
				ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
						Boolean.parseBoolean(additionalChargesForChildrenAdults[i]));
				test_steps.add("Charge for additional adult/child is on? " + additionalChargesForChildrenAdults[i]);

				boolean isProRateStay = ratesGrid.verifyProrateAtSeasonLevel(driver,
						Boolean.parseBoolean(IsProRateStayInRate), Boolean.parseBoolean(proRateStay[i]), test_steps);
				logger.info("isProRateStay: " + isProRateStay);
				test_steps.add("Is pro rate stay checked at season ? " + isProRateStay);

				logger.info("customRatePerNight in rate grid: " + CustomRatePerNight);

				ratesGrid.enterRoomClassRates(driver, roomClass, RatePerNight, additionalChargesForChildrenAdults[i],
						MaxAdults, MaxPersons, AdultsRate, ChildRate, isProRateStay, isProRateInRoomClass,
						ProRateRoomClassName, IsCustomPerNight, CustomRoomClass, CustomRatePerNight,
						CustomRateAdultdPerNight, CustomRateChildPerNight, isCustomRatePerNightAdultandChild,
						test_steps);
				clickSaveSason(driver, test_steps);
			}
		}
	}

	public boolean verifySeasonExistInbetweencheckin(WebDriver driver, String checkInDate) throws Exception {
		String date = checkInDate;
		boolean flag = true;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String today = dtf.format(now);
		int count = Utility.getNumberofDays(today, checkInDate);
		for (int i = 0; i < 1; i++) {
			logger.info("date : " + date);
			String startDateMonth = Utility.get_Month(date);
			String startDateYear = Utility.getYear(date);
			String startDateDay = Utility.getDay(date);
			startDateMonth = startDateMonth.toUpperCase();
			String startDateMonthYear = startDateMonth + " " + startDateYear;
			startDateMonthYear = startDateMonthYear.trim();
			String startDate = "//div[@aria-label='" + Utility.parseDate(date, "dd/MM/yyyy", "EEE MMM dd yyyy")
					+ "']//div[text()='" + startDateDay + "']";
			logger.info("startDate: " + startDate);
			String bgcolor = driver.findElement(By.xpath(startDate)).getAttribute("style");
			logger.info("bgcolor : " + bgcolor);
			if (!bgcolor.contains("rgb")) {
				flag = false;
				return flag;
			}
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			// manipulate dateF
			c.add(Calendar.DATE, count + (i + 1)); // same with c.add(Calendar.DAY_OF_MONTH, 1);
			// convert calendar to date
			Date currentDatePlusOne = c.getTime();
			date = dateFormat.format(currentDatePlusOne);
		}
		return flag;
	}

	public void addExtraRoomClassInSeason1(WebDriver driver, ArrayList<String> test_steps,
			String isAddRoomClassInSeason, String ExtraRoomClassesInSeason, String isAdditionalChargesForChildrenAdults,
			String RatePerNight, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight) {

		String roomClassName;

		if (isAddRoomClassInSeason.equalsIgnoreCase("Yes")) {
			String[] roomClass = ExtraRoomClassesInSeason.split(Utility.DELIMS);
			Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
			Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AddRoomClass);
			ratessGrid.RatePlan_Season_AddRoomClass.click();
			test_steps.add("Clickin on Add Room Class");
			logger.info("Clickin on Add Room Class");
			for (int i = 0; i < roomClass.length; i++) {
				roomClassName = "(//span[text()='" + roomClass[i] + "']/preceding-sibling::span/input)[2]";
				Wait.WaitForElement(driver, roomClassName);
				driver.findElement(By.xpath(roomClassName)).click();
				test_steps.add("Successfully selected room class : " + roomClass[i]);
				logger.info("Successfully selected room class : " + roomClass[i]);

				String[] rate = RatePerNight.split("\\|");

				String nihtRate = "(//span[text()='" + roomClass[0]
						+ "'])[2]/../following-sibling::span[3]//input[@name='txtRate']";

				// String nihtRate = "//input[@name='txtRate']";
				String ratePerNight = null, maxAdults = null, maxPersons = null, addAdultPerNiht = null,
						AddChildPerNiht = null;

				if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
					String[] rateNight = ExtraRoomClassRatePerNight.split(Utility.DELIMS);
					String[] adults = ExtraRoomClassMaxAdults.split(Utility.DELIMS);
					String[] persons = ExtraRoomClassMaxPersons.split(Utility.DELIMS);

					String[] addAdults = ExtraRoomClassAdditionalAdultsPerNight.split(Utility.DELIMS);
					String[] addChild = ExtraRoomClassAdditionalChildPerNight.split(Utility.DELIMS);

					for (int k = (rate.length); k <= (rate.length + rateNight.length) - 1; k++) {
						ratePerNight = "(//input[@name='txtRate'])[" + k + "]";
						driver.findElement(By.xpath(ratePerNight)).sendKeys(rateNight[i]);
						test_steps.add("Enter rate for room " + i + " rate " + rateNight[i]);
						logger.info("Enter rate for room " + i + " rate " + rateNight[i]);

						maxAdults = "((//input[@name='txtRate'])[" + k
								+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[1]";
						driver.findElement(By.xpath(maxAdults)).sendKeys(adults[i]);
						test_steps.add("Enter max adults for the room  " + i + " is " + adults[i]);
						logger.info("Enter max adults for the room  " + i + " is " + adults[i]);

						maxPersons = "((//input[@name='txtRate'])[" + k
								+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[2]";
						driver.findElement(By.xpath(maxPersons)).sendKeys(persons[i]);
						test_steps.add("Enter max persons for the room  " + i + " is " + persons[i]);
						logger.info("Enter max persons for the room  " + i + " is " + persons[i]);

						addAdultPerNiht = "((//input[@name='txtRate'])[" + k
								+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[3]";
						driver.findElement(By.xpath(addAdultPerNiht)).sendKeys(addAdults[i]);
						test_steps.add("Enter Additional adults per night for the room  " + i + " is " + addAdults[i]);
						logger.info("Enter Additional adults per night for the room  " + i + " is " + addAdults[i]);

						AddChildPerNiht = "((//input[@name='txtRate'])[" + k
								+ "]/../../../../../../li/ul/li//input[@role='spinbutton'])[4]";
						driver.findElement(By.xpath(AddChildPerNiht)).sendKeys(addChild[i]);
						test_steps.add("Enter Additional child per night for the room  " + i + " is " + addChild[i]);
						logger.info("Enter Additional chuld per night for the room  " + i + " is " + addChild[i]);
					}
				} else {
					selectAdditionalChargesForChildrenAdults(driver, test_steps, isAdditionalChargesForChildrenAdults);
					String[] rateNight = ExtraRoomClassRatePerNight.split(Utility.DELIMS);
					nihtRate = "(//span[text()='" + roomClass[0]
							+ "'])[2]/../following-sibling::span[3]//input[@name='txtRate']";
					driver.findElement(By.xpath(nihtRate)).sendKeys(rateNight[0]);
					test_steps.add("Enter rate for room " + i + " rate " + rateNight[0]);
					logger.info("Enter rate for room " + i + " rate " + rateNight[0]);

				}
			}
		}
	}

	public void selectSeasonDates(WebDriver driver, ArrayList<String> test_steps, ArrayList<String> dates) {

		for (String date : dates) {
			String startDateMonth = Utility.get_Month(date);
			String startDateYear = Utility.getYear(date);
			String startDateDay = Utility.getDay(date);
			startDateMonth = startDateMonth.toUpperCase();

			String startDateMonthYear = startDateMonth + " " + startDateYear;
			startDateMonthYear = startDateMonthYear.trim();

			// String startDate = "//div[text()='" + startDateMonthYear
			// + "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay +
			// "']";
			String startDate = "//div[@aria-label='" + Utility.parseDate(date, "dd/MM/yyyy", "EEE MMM dd yyyy")
					+ "']//div[text()='" + startDateDay + "']";
			logger.info("startDate: " + startDate);
			if (driver.findElements(By.xpath(startDate)).size() > 0) {
				Wait.WaitForElement(driver, startDate);
				Wait.waitForElementToBeVisibile(By.xpath(startDate), driver);
				Wait.waitForElementToBeClickable(By.xpath(startDate), driver);
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(startDate)));
				test_steps.add("Selecting date  as : " + date);
				logger.info("Selecting date as : " + date);
			} else {
				logger.info("Looking for Active date, inActive/past date is not clickable : " + date);

			}
		}
	}

	public String getSelectedSeasonLevelPolicy(WebDriver driver, String policyType, ArrayList<String> test_steps) {
		String selectedPolicyName = "NA";

		String typePath = "(//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyType.toUpperCase() + "'])[2]/preceding-sibling::span/input";

		String policyPath = "(//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyType.toUpperCase()
				+ "'])[2]/../following-sibling::div//label/span[@class='ant-radio ant-radio-checked']";

		if (driver.findElement(By.xpath(typePath)).isSelected()) {
			// logger.info(policyType);
			for (WebElement ele : driver.findElements(By.xpath(policyPath))) {

				if (ele.isDisplayed()) {
					// logger.info("ele selected");
					selectedPolicyName = driver.findElement(By.xpath(
							"(//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
									+ policyType.toUpperCase()
									+ "'])[2]/../following-sibling::div//label/span[@class='ant-radio ant-radio-checked']/following::span[1]"))
							.getText();
					logger.info(selectedPolicyName);
					String str[] = selectedPolicyName.split("\n");
					selectedPolicyName = str[0];
				}
			}
		}
		test_steps.add("Selected" + policyType + " Policy : " + selectedPolicyName);
		logger.info("Selected" + policyType + " Policy : " + selectedPolicyName);

		return selectedPolicyName;
	}

	public HashMap<String, String> getSeasonSelectedPolicy(WebDriver driver, ArrayList<String> test_steps) {
		HashMap<String, String> typeWisePolicies = new HashMap<String, String>();

		typeWisePolicies.put("Cancellation", getSelectedSeasonLevelPolicy(driver, "Cancellation", test_steps));
		typeWisePolicies.put("Deposit", getSelectedSeasonLevelPolicy(driver, "Deposit", test_steps));
		typeWisePolicies.put("Check-in", getSelectedSeasonLevelPolicy(driver, "Check-in", test_steps));
		typeWisePolicies.put("No Show", getSelectedSeasonLevelPolicy(driver, "No Show", test_steps));

		return typeWisePolicies;
	}

	// Added By Adhnan Ghaffar 11/12/2020
	public boolean isPolicyAppliedByRoomClassisSelected(WebDriver driver) throws InterruptedException {
		Elements_NightlyRate nightlyRate = new Elements_NightlyRate(driver);
		String policyNameSelected = null;
		String roomClassPoliciesCheckBox = nightlyRate.assignPoliciesByRoomClass.getAttribute("aria-checked");
		if (roomClassPoliciesCheckBox.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	// Added By Adhnan Ghaffar 11/12/2020
	public HashMap<String, String> getSeasonSelectedPolicyForRoomClass(WebDriver driver, String roomClass,
			ArrayList<String> test_steps) throws InterruptedException {
		HashMap<String, String> typeWisePolicies = new HashMap<String, String>();
		Properties ratesConfig = new Properties();
		typeWisePolicies.put("Cancellation", getSeasonLevelPolicyForRoomClass(driver,
				ratesConfig.getProperty("cancelPolicyText"), roomClass, test_steps));
		typeWisePolicies.put("Deposit", getSeasonLevelPolicyForRoomClass(driver,
				ratesConfig.getProperty("depositPolicyText"), roomClass, test_steps));
		typeWisePolicies.put("Check-in", getSeasonLevelPolicyForRoomClass(driver,
				ratesConfig.getProperty("checkInPolicyText"), roomClass, test_steps));
		typeWisePolicies.put("No Show", getSeasonLevelPolicyForRoomClass(driver,
				ratesConfig.getProperty("noShowPolicyText"), roomClass, test_steps));
		return typeWisePolicies;
	}

	// Added By Adhnan Ghaffar 11/12/2020
	public boolean isRoomClassSelectedForPolicy(WebDriver driver, String roomClassName) throws InterruptedException {
		return Utility.isElementPresent(driver, By.xpath("//div[text()='" + roomClassName + "']"));
	}

	// Added By Adhnan Ghaffar 11/12/2020
	public String getSeasonLevelPolicyForRoomClass(WebDriver driver, String policyName, String roomClassName,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_NightlyRate nightlyRate = new Elements_NightlyRate(driver);
		String policyNameSelected = null;

		String policyNameXpath = "//div[text()='" + roomClassName + "']/../../../../../../../../following-sibling::div"
				+ "//input[@class='ant-checkbox-input' and @value='" + policyName + "']/../../.."
				+ "//span[@class='ant-radio ant-radio-checked']/following-sibling::span";
		policyNameSelected = driver.findElement(By.xpath(policyNameXpath)).getText();
		policyNameSelected = policyNameSelected.split("\n")[0];
		return policyNameSelected;
	}

	public void switchProductsTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.PRODUCTS_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.PRODUCTS_TAB, driver);
			elements.PRODUCTS_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.PRODUCTS_TAB);
		}
		test_steps.add("Products Tab Clicked");
		logger.info("Products Tab Clicked");
	}

	public String getDateWhereSeasonNotExist(WebDriver driver) {
		//String xpath = "(//div[@class='DayPicker-Day']/div[not(contains(@style,'rgb'))]//parent::div)[1]";
		String xpath = "(//div[@class='DayPicker-Day']/div[(contains(@style,'rgb'))]//parent::div)[1]";
		
		Wait.WaitForElement(driver, xpath);
		WebElement element = driver.findElement(By.xpath(xpath));
		String selectedDate = element.getAttribute("aria-label");
		if (!selectedDate.isEmpty()) {
			selectedDate = element.getAttribute("aria-label").split(" ")[2] + "/"
					+ element.getAttribute("aria-label").split(" ")[1].toString() + "/"
					+ element.getAttribute("aria-label").split(" ")[3];
		}

		return selectedDate;
	}

	public ArrayList<String> checkUncheckSeasonLevelPolicy(WebDriver driver, ArrayList<String> test_steps,
			String checkOrUnceck) {
		ArrayList<String> testSteps = new ArrayList<>();
		if (checkOrUnceck.equals("check")) {
			checkSelectedSeasonLevelPolicy(driver, "Cancellation", test_steps);
			checkSelectedSeasonLevelPolicy(driver, "Deposit", test_steps);
			checkSelectedSeasonLevelPolicy(driver, "Check-in", test_steps);
			checkSelectedSeasonLevelPolicy(driver, "No Show", test_steps);
		} else if (checkOrUnceck.equals("unCheck")) {
			unCheckSelectedSeasonLevelPolicy(driver, "Cancellation", test_steps);
			unCheckSelectedSeasonLevelPolicy(driver, "Deposit", test_steps);
			unCheckSelectedSeasonLevelPolicy(driver, "Check-in", test_steps);
			unCheckSelectedSeasonLevelPolicy(driver, "No Show", test_steps);
		}
		return testSteps;
	}

	public String checkSelectedSeasonLevelPolicy(WebDriver driver, String policyType, ArrayList<String> test_steps) {

		String typePath = "(//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyType.toUpperCase() + "'])[2]/preceding-sibling::span/input";

		if (driver.findElement(By.xpath(typePath)).isSelected()) {
			test_steps.add("Selected '" + policyType + "' policy already checked.");
			logger.info("Selected '" + policyType + "' policy already checked.");
		} else {
			Wait.WaitForElement(driver, typePath);
			driver.findElement(By.xpath(typePath)).click();
			test_steps.add("Successfully clicked on '" + policyType + "' policy because it was not checked.");
			logger.info("Successfully clicked on '" + policyType + "' policy because it was not checked.");
			allPoliciesAreAlreadyChecked = false;
		}

		return policyType;
	}

	public String unCheckSelectedSeasonLevelPolicy(WebDriver driver, String policyType, ArrayList<String> test_steps) {
		String typePath = "(//span[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ policyType.toUpperCase() + "'])[2]/preceding-sibling::span/input";

		if (driver.findElement(By.xpath(typePath)).isSelected()) {
			Wait.WaitForElement(driver, typePath);
			driver.findElement(By.xpath(typePath)).click();
			test_steps.add("Successfully clicked on '" + policyType + "' policy because it was already checked.");
			logger.info("Successfully clicked on '" + policyType + "' policy because it was already checked.");
			allPoliciesAreAlreadyUnChecked = false;
		} else {
			test_steps.add("Selected '" + policyType + "' policy already unChecked.");
			logger.info("Selected '" + policyType + "' policy already unChecked.");
		}

		return policyType;
	}

	public void selectRoomClassesForRules(WebDriver driver, String updatedSeasonRuleSpecificRoomClasses)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_SeasonRulesSpecificRoomClasses);
		ratesGrid.RatePlan_Season_SeasonRulesSpecificRoomClasses.click();
		test_steps.add("Clicking on Choose existing room class(s)");
		logger.info("Clicking on Choose existing room class(s)");
		String[] roomClass = updatedSeasonRuleSpecificRoomClasses.split(Utility.DELIM);
		for (int i = 0; i <= (roomClass.length - 1); i++) {
			String roomClassName = "//li[text()='" + roomClass[i] + "']";
			try {
				Utility.ScrollToElement(driver.findElement(By.xpath(roomClassName)), driver);
				driver.findElement(By.xpath(roomClassName)).click();
			} catch (Exception e) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(roomClassName)));
			}
			test_steps.add("Selectin room : " + roomClass[i]);
			logger.info("Selectin room : " + roomClass[i]);
		}
		Wait.wait1Second();
		try {
			Utility.ScrollToElement(driver.findElement(By.xpath("//label[text()='Room class']")), driver);
			driver.findElement(By.xpath("//label[text()='Room class']")).click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath("//label[text()='Room class']")));
		}
		Wait.wait1Second();
		Utility.ScrollToElement(ratesGrid.clickAddSecondSetOfRules, driver);
		ratesGrid.clickAddSecondSetOfRules.click();

	}

	public void clickSeasonsTab(WebDriver driver, String tabName, ArrayList<String> testSteps) {
		String xpath = "//ul[@class='seasonNavMenu']/li//a[text()='" + tabName + "']";
		Wait.WaitForElement(driver, xpath);
		WebElement tab = driver.findElement(By.xpath(xpath));
		try {
			Utility.ScrollToElement(tab, driver);
			tab.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, tab);
		}
		testSteps.add("Clicking on season '" + tabName + "' tab");
		logger.info("Clicking on season '" + tabName + "' tab");

	}

	public void blockoutSeason(WebDriver driver, ArrayList<String> test_steps, String BlockoutSeasonStartDate,
			String BlockoutSeasonEnddate) throws InterruptedException {

		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		selectSeasonDates(driver, test_steps, BlockoutSeasonStartDate, BlockoutSeasonEnddate);

		Utility.clickThroughJavaScript(driver, ratessGrid.RatePlan_Season_Blockout);
		test_steps.add("Click on BlackOut Button");
		logger.info("Click on BlackOut Button");
	}

	public void clickDeleteSeasonButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_NightlyRate elements = new Elements_NightlyRate(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.editThisSeason);
		Utility.ScrollToElement(driver.findElement(
				By.xpath("//button/span[text()='Edit this season']/../following-sibling::span/span")), driver);
		Utility.clickThroughJavaScript(driver, driver
				.findElement(By.xpath("//button/span[text()='Edit this season']/../following-sibling::span/span")));
		// elements.editThisSeason.click();
		Wait.WaitForElement(driver, "//button/span[text()='Delete']");
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath("//button/span[text()='Delete']")));
		testSteps.add("Season Delete Button Clicked");
		logger.info("Season Delete Button Clicked");
	}

	public void unSelectSeasonLevelExceptAdditionalRoomClass(WebDriver driver, String roomClassNames,
			ArrayList<String> test_steps) {

		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClassNames, Utility.DELIM);
		String roomClass = "//div[@class='AddRoomClassList']";
		int rooms = driver.findElements(By.xpath(roomClass)).size();
		System.out.println("Rooms Size : " + rooms);
		int j = 1;
		for (int i = 1; i <= rooms; i++) {
			String getRoomClass = driver.findElement(By
					.xpath("(//div[@class='AddRoomClassList']/label/span[@class='ant-checkbox ant-checkbox-checked'])["
							+ j + "]/span/following::span"))
					.getText();
			if (roomClassList.contains(getRoomClass)) {
				j++;
			} else {
				String xpath = "//div[@class='AddRoomClassList']//span[text()='" + getRoomClass + "']/..//input";
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(xpath)));
				test_steps.add("Successfully Un Selected Room Class : " + roomClass);
				logger.info("Successfully Un Selected Room Class : " + roomClass);

			}

		}

	}

	public HashMap<String, ArrayList<HashMap<String, String>>> mergedtwoHashMap(
			HashMap<String, ArrayList<HashMap<String, String>>> derivedExtraAdults,
			HashMap<String, ArrayList<HashMap<String, String>>> derivedExtraChilds) {
		HashMap<String, ArrayList<HashMap<String, String>>> derivedExtraAdultsChilds = new HashMap<String, ArrayList<HashMap<String, String>>>();
		for (Map.Entry<String, ArrayList<HashMap<String, String>>> entry : derivedExtraAdults.entrySet()) {
			String str = entry.getKey();
			ArrayList<HashMap<String, String>> values2 = derivedExtraChilds.get(str);
			ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>(entry.getValue());
			HashMap<String, String> extraRates = new HashMap<>();
			ArrayList<HashMap<String, String>> extraRatesList = new ArrayList<>();
			for (HashMap<String, String> map : values) {
				extraRates.putAll(map);
				extraRates.putAll(values2.get(values.indexOf(map)));
			}
			extraRatesList.add(extraRates);
			derivedExtraAdultsChilds.put(str, extraRatesList);
		}
		logger.info(derivedExtraAdultsChilds);
		return derivedExtraAdultsChilds;

	}

	public void clickFillBlanksSeason(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.explicit_wait_visibilityof_webelement(ratessGrid.RatePlan_Season_FillBlanks, driver);

		Utility.ScrollToElement(ratessGrid.RatePlan_Season_FillBlanks, driver);
		ratessGrid.RatePlan_Season_FillBlanks.click();
		test_steps.add("Clicking on Season Replace");
		logger.info("Clicking on Season Replace");
	}

	public void addLastRoomClassRandomely(WebDriver driver, ArrayList<String> test_steps) {

		String roomClassName;

		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AddRoomClass);
		ratessGrid.RatePlan_Season_AddRoomClass.click();
		test_steps.add("Clickin on Add Room Class");
		logger.info("Clickin on Add Room Class");
		roomClassName = "(//span/preceding-sibling::span/input)[last()]";
		logger.info("Room Class xpath : " + roomClassName);
		Wait.WaitForElement(driver, roomClassName);
		driver.findElement(By.xpath(roomClassName)).click();
		test_steps.add("Successfully selected room class : ");
		logger.info("Successfully selected room class : ");
	}

	public void addSeasonLevelRoomClass(WebDriver driver, ArrayList<String> test_steps, String roomClass)
			throws InterruptedException {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_NightlyRatePlan.RatePlan_Season_AddRoomClass));
		if (isExist) {
			Wait.WaitForElement(driver, OR_NightlyRatePlan.RatePlan_Season_AddRoomClass);
			ratessGrid.RatePlan_Season_AddRoomClass.click();
			test_steps.add("Clickin on Add Room Class");
			logger.info("Clickin on Add Room Class");
		}
		String roomClassName = "//li//span[text()='" + roomClass + "']/preceding-sibling::span/input";
		logger.info(roomClassName);
		try {
		Utility.ScrollToElement(driver.findElement(By.xpath(roomClassName)), driver);
		driver.findElement(By.xpath(roomClassName)).click();}
		catch(Exception e) {
			Utility.scrollAndClick(driver, By.xpath(roomClassName));
		}
		test_steps.add("Successfully selected room class : " + roomClass);
		logger.info("Successfully selected room class : " + roomClass);

	}

	

	public void createSeasonForExistingRatePlan(WebDriver driver, ArrayList<String> testSteps, String ratePlanName,
			ArrayList<String> datesRangeList, String startDate, String endDate,
			String isAdditionalChargesForChildrenAdults, String roomClassName, String rate, String extraAdult,
			String extraChild, String extraAdultAmount, String extraChildAmount, boolean closeRatePlan)
			throws InterruptedException {

		Navigation navigation = new Navigation();
		RatesGrid rateGrid = new RatesGrid();
		try {
			try {
			navigation.inventory_Backward_1(driver);
			}
			catch(Exception e) {
				navigation.Inventory_Backward_3(driver);
			}
			testSteps.add("Navigated to Inventory");
			logger.info("Navigated to Inventory");
		} catch (Exception e) {
			navigation.inventoryBackwardAdmin(driver);
			testSteps.add("Navigated to Inventory");
			logger.info("Navigated to Inventory ");
		}
		navigation.ratesGrid(driver);
		testSteps.add("Navigated to rateGrid");
		rateGrid.clickRatePlanArrow(driver, testSteps);
		rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
		rateGrid.clickEditIcon(driver, testSteps);
		rateGrid.verifyRatePlaninEditMode(driver, testSteps, ratePlanName);
		switchCalendarTab(driver, testSteps);
		selectSeasonDates(driver, testSteps, datesRangeList);
		deleteSeasonIcon(driver, testSteps);
		selectSeasonDates(driver, testSteps, startDate, endDate);
		String seasonName = "Season" + Utility.generateRandomString();
		enterSeasonName(driver, testSteps, seasonName);		
		clickReplaceSeason(driver, testSteps);
		clickCreateSeason(driver, testSteps);
		selectSeasonColor(driver, testSteps);
		if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
			selectAdditionalChargesForChildrenAdults(driver, testSteps, isAdditionalChargesForChildrenAdults);
		}
		
		unSelectSeasonLevelExceptAdditionalRoomClass(driver, roomClassName, testSteps);
		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClassName, Utility.DELIM);
		for(int i=0; i<roomClassList.size();i++) {
		addSeasonLevelRoomClass(driver, testSteps, roomClassList.get(i));
		}
		enterRate(driver, testSteps, rate, isAdditionalChargesForChildrenAdults, extraAdult, extraChild,
				extraAdultAmount, extraChildAmount);
		clickSaveSason(driver, testSteps);
		clickSaveRatePlanButton(driver, testSteps);
		if (closeRatePlan) {
			rateGrid.closeRatePlan(driver, testSteps, ratePlanName);
		}
		Wait.waitUntilPageLoadNotCompleted(driver, 40);

	}

	public boolean getOverAllProRate(WebDriver driver, String getRoomClass) {
		String pathOfProRateStayInRoomClass = "//li[@class='IntervalRatePlan line']//span[text()='" + getRoomClass
				+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]";
		boolean isProRateStayCheckbox = driver.findElements(By.xpath(pathOfProRateStayInRoomClass)).size() > 0;

		return isProRateStayCheckbox;
	}

	public boolean getProRateStayForRoomClass(WebDriver driver, String roomClass) {
		boolean isProRateOn = false;
		if (getOverAllProRate(driver, roomClass)) {
			String pathOfProRateStayRateCheckBox = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClass
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//label[contains(@class,'inrd-checkbox')]";
			WebElement getProRateStayCheckbox = driver.findElement(By.xpath(pathOfProRateStayRateCheckBox));
			if (getProRateStayCheckbox.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {
				isProRateOn = true;
			}
		}
		return isProRateOn;
	}

	public String getProRatePerNight(WebDriver driver, String roomClass) {
		String ratePerNight = "0";
		if (getProRateStayForRoomClass(driver, roomClass)) {
			String pathOfProRateStayRatePerNight = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClass
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";

			logger.info("pathOfProRateStayRatePerNight: " + pathOfProRateStayRatePerNight);
			List<WebElement> proRateStayValue = driver.findElements(By.xpath(pathOfProRateStayRatePerNight));
			ratePerNight = proRateStayValue.get(1).getAttribute("value");

			logger.info("ratePerNight: " + ratePerNight);
		}
		return ratePerNight;
	}

	public String getProRateExAdult(WebDriver driver, String roomClass) {
		String proRateAdditionalAdult = "0";
		if (getProRateStayForRoomClass(driver, roomClass)) {
			String pathOfProRateStayRatePerNight = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClass
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";

			logger.info("pathOfProRateStayRatePerNight: " + pathOfProRateStayRatePerNight);
			List<WebElement> proRateStayValue = driver.findElements(By.xpath(pathOfProRateStayRatePerNight));

			if (getAdditionalAdultChildtoggle(driver)) {
				proRateAdditionalAdult = proRateStayValue.get(2).getAttribute("value");
				logger.info("proRateAdditionalAdult: " + proRateAdditionalAdult);

			}
		}
		return proRateAdditionalAdult;
	}

	public String getProRateExChild(WebDriver driver, String roomClass) {
		String proRateAdditionalChild = "0";
		if (getProRateStayForRoomClass(driver, roomClass)) {
			String pathOfProRateStayRatePerNight = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClass
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";

			logger.info("pathOfProRateStayRatePerNight: " + pathOfProRateStayRatePerNight);
			List<WebElement> proRateStayValue = driver.findElements(By.xpath(pathOfProRateStayRatePerNight));

			if (getAdditionalAdultChildtoggle(driver)) {

				proRateAdditionalChild = proRateStayValue.get(3).getAttribute("value");
				logger.info("proRateAdditionalChild: " + proRateAdditionalChild);

			}
		}
		return proRateAdditionalChild;
	}

public boolean getAdditionalAdultChildtoggle(WebDriver driver) {
	boolean istoggle = false;
	
	String chargeforAdditionalAdultChild = "//span[text()='Charge for additional adult/child']//..//button";
	
	WebElement toggleBtnForChargeforAdditionalAdultChild = driver
			.findElement(By.xpath(chargeforAdditionalAdultChild));
	
	if (toggleBtnForChargeforAdditionalAdultChild.getAttribute("class").contains("ant-switch-checked")) {
		istoggle = true;
	}
	
	return istoggle;
	}


}
