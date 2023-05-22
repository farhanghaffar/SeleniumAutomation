package com.innroad.inncenter.pageobjects;

import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import static org.testng.Assert.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.GDuration;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bsh.util.Util;
import com.gargoylesoftware.htmlunit.javascript.host.intl.DateTimeFormat;
import com.innroad.inncenter.pages.RateGridPage;
import com.innroad.inncenter.webelements.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.innroad.inncenter.interfaces.ITapeChart;
import com.innroad.inncenter.pages.NewTapeChart;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.innroad.inncenter.webelements.Elements_TapeChart;
import com.relevantcodes.extentreports.ExtentTest;

public class Tapechart implements ITapeChart {

	public static Logger tapechartLogger = Logger.getLogger("TapChart");

	public static String RoomNumber;
	public static String unassignedResCountTapechart;
	public static String RoomClassName;
	public static String availableRooms;
	public static String roomsAvailableAfterCreatingRes;
	public static String unassignedResCountTapechartAfterCreatingRes;
	public static String unassignedGuestNameTapechart;
	public static String row;
	public static String roomsAvailableAfterCreatingQuoteRes;
	ArrayList<String> test_steps = new ArrayList<>();

	public void TapChartLink(WebDriver driver) {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		TapeChart.Tape_Chart.click();
		Wait.explicit_wait_xpath(OR.Select_Arrival_Date, driver);

	}

	public void enterPromoCode(WebDriver driver, String promoCode) {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitForElementByXpath(driver, OR.Enter_promoCode_Tapechart);
		//Wait.waitUntilPresenceOfElementLocated(OR.Enter_promoCode_Tapechart, driver);
		TapeChart.Enter_promoCode_Tapechart.clear();
		TapeChart.Enter_promoCode_Tapechart.sendKeys(promoCode);
	}

	public void enterCheckInDate(WebDriver driver, ArrayList<String> test_steps, String checkInDate) throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.TapeChart_CheckIn);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChart_CheckIn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChart_CheckIn), driver);
		Utility.ScrollToElement_NoWait(tapeChart.TapeChart_CheckIn, driver);
		tapeChart.TapeChart_CheckIn.clear();
		tapeChart.TapeChart_CheckIn.sendKeys(checkInDate);
		test_steps.add("Enter Check In Date : " + checkInDate);
	}

	public void enterCheckOutDate(WebDriver driver, ArrayList<String> test_steps, String checkOutDate) throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, NewTapeChart.CheckoutDate);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.CheckoutDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.CheckoutDate), driver);
		Utility.ScrollToElement_NoWait(tapeChart.CheckoutDate, driver);
		tapeChart.CheckoutDate.clear();
		tapeChart.CheckoutDate.sendKeys(checkOutDate);
		test_steps.add("Enter Check Out Date : " + checkOutDate);
	}

	public void clickOnGoToDateOnChart(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, NewTapeChart.CheckoutDate);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.CheckoutDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.CheckoutDate), driver);
		Utility.ScrollToElement_NoWait(tapeChart.CheckoutDate, driver);
		tapeChart.GoToDateOnChart.click();
		waitForReservationToLoad(driver, test_steps);
		test_steps.add("Clicking on go to date on chart link");
	}

	public void tapeChartSearch(WebDriver driver, String TapeChartAdults, String TapeChartChildrens, String PromoCode)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.Select_Arrival_Date);
		Wait.explicit_wait_elementToBeClickable(TapeChart.Select_Arrival_Date, driver);
		Utility.ScrollToElement(TapeChart.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		TapeChart.Enter_Children_Tapechart.sendKeys(TapeChartChildrens);
		TapeChart.Enter_promoCode_Tapechart.clear();
		TapeChart.Enter_promoCode_Tapechart.sendKeys(PromoCode);
		TapeChart.Click_Search_TapeChart.click();
		tapechartLogger.info("Click Search");
		try {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot, driver);
		} catch (Exception e) {
			TapeChart.Click_Search_TapeChart.click();
			tapechartLogger.info("Again Click Search");
			// Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot,driver);
		}
	}

	public void TapeChartSearch(WebDriver driver, String TapeChartAdults) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);

		/*
		 * try {
		 * Wait.explicit_wait_visibilityof_webelement(TapeChart.Rule_Broken.get(
		 * 0), driver); } catch (Exception e) {
		 * TapeChart.Click_Search_TapeChart.click();
		 * Wait.explicit_wait_visibilityof_webelement(TapeChart.Rule_Broken.get(
		 * 0), driver);
		 *
		 * }
		 */
	}

	public void searchNewQuoteReservationInTapechart(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		String quoteResGuestName = "//div[contains(text(), '" + Reservation.nameGuest + "')]";

		if (driver.findElement(By.xpath(quoteResGuestName)).getText().contains(Reservation.nameGuest)) {

			roomsAvailableAfterCreatingQuoteRes = driver
					.findElement(By.xpath("(//div[contains(text(), '" + Reservation.nameGuest
							+ "')]//following::div[contains(text(),'# Rooms Available')])[1]/ ../div[2]/div[2]"))
					.getText();
			tapechartLogger.info("Rooms Available After Creating Reservation: " + roomsAvailableAfterCreatingQuoteRes);
		}

		Wait.wait5Second();

	}

	public void availableRoomsValidations(WebDriver driver, String quoteResAvailableRooms, ExtentTest test) {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		int quoteResRoomsAvailable = Integer.parseInt(quoteResAvailableRooms) - 1;

		int availableRoomsAfterCreatingQuoteRes = Integer.parseInt(roomsAvailableAfterCreatingQuoteRes);
		test_steps.add("Available Rooms in Tapechart " + quoteResRoomsAvailable);
		if (availableRoomsAfterCreatingQuoteRes == quoteResRoomsAvailable) {

			tapechartLogger.info("Successfully Validated Rooms available");

		}
	}

	public void TapeChartSearch_GivenDate(WebDriver driver, String TapeChartAdults, String TapeChartChildrens,
										  String PromoCode) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		TapeChart.Enter_Children_Tapechart.sendKeys(TapeChartChildrens);
		TapeChart.Enter_promoCode_Tapechart.clear();
		TapeChart.Enter_promoCode_Tapechart.sendKeys(PromoCode);
		TapeChart.Click_Search_TapeChart.click();
		Wait.wait2Second();

	}

	public void TapeChartSearch_RatePlan(WebDriver driver, String TapeChartAdults, String TapeChartChildrens,
										 String PromoCode) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Select_Arrival_Date, driver);
		Utility.ScrollToElement(TapeChart.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains(TapeChartAdults),
				"Failed: Adult set");
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(TapeChartChildrens);
		assertTrue(TapeChart.Enter_Children_Tapechart.getAttribute("value").contains(TapeChartChildrens),
				"Failed: Children set");
		Wait.explicit_wait_xpath(OR.Click_Tapechart_RackRate, driver);
		TapeChart.Click_Tapechart_RackRate.click();
		Wait.explicit_wait_xpath(OR.Select_Rack_Rate, driver);
		TapeChart.Select_Rack_Rate.click();
		Wait.wait2Second();
		TapeChart.Enter_promoCode_Tapechart.sendKeys(PromoCode);
		assertTrue(TapeChart.Enter_promoCode_Tapechart.getAttribute("value").contains(PromoCode),
				"Failed: RomoCode Set");
		TapeChart.Click_Search_TapeChart.click();
		tapechartLogger.info("Click Search");
		try {
			Wait.explicit_wait_visibilityof_webelement_120(TapeChart.TapeChartAvailableSlot, driver);
		} catch (Exception e) {
			TapeChart.Click_Search_TapeChart.click();
			tapechartLogger.info("Again Click Search");
			Wait.explicit_wait_visibilityof_webelement_120(TapeChart.TapeChartAvailableSlot, driver);
		}

	}

	public void TapeChartSearch_NextDate(WebDriver driver, String TapeChartAdults, String TapeChartChildrens,
										 String PromoCode) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		driver.navigate().refresh();
		Utility.app_logs.info("Refresh Page");
		try {
			Wait.explicit_wait_visibilityof_webelement_150(TapeChart.Select_Arrival_Date, driver);
		} catch (Exception e) {
			Utility.app_logs.info("In catch");
			Navigation nav = new Navigation();
			nav.TapeChart(driver);
			Utility.app_logs.info("Click TapeChart");
			Wait.explicit_wait_visibilityof_webelement_350(TapeChart.Select_Arrival_Date, driver);
		}
		TapeChart.Select_Arrival_Date.click();
		Wait.wait2Second();
		String ActiveDate = TapeChart.GetActiveDate.getText();
		int currentDate = Integer.parseInt(ActiveDate);
		currentDate = currentDate + 1;
		String SelectDate = String.valueOf(currentDate);

		try {
			tapechartLogger.info(
					"(//div[@class='datepicker-days']//table/tbody/tr/td[@class='today day']//following-sibling::td[@class!='old day'])[1]");
			driver.findElements(By
					.xpath("(//div[@class='datepicker-days']//table/tbody/tr/td[@class='today day']//following-sibling::td[@class!='old day'])[1]"))
					.get(0).click();

		} catch (Exception e) {
			Utility.app_logs.info("Today is saturday");
			String Path = "(//div[@class='datepicker-days']//table/tbody/tr/td[@class='today day']//parent::tr//following-sibling::tr//td[@class!='old day'])[1]";
			tapechartLogger.info(Path);
			driver.findElement(By.xpath(Path)).click();
		}
		Wait.wait2Second();

		// Wait.explicit_wait_xpath(OR.Click_Tomorrow);
		// TapeChart.Click_Tomorrow.click();
		Wait.wait2Second();
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains(TapeChartAdults),
				"Failed: Adult set");
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(TapeChartChildrens);
		assertTrue(TapeChart.Enter_Children_Tapechart.getAttribute("value").contains(TapeChartChildrens),
				"Failed: Children set");
		Wait.explicit_wait_xpath(OR.Click_Tapechart_RackRate, driver);
		TapeChart.Click_Tapechart_RackRate.click();
		Wait.explicit_wait_xpath(OR.Select_Rack_Rate, driver);
		TapeChart.Select_Rack_Rate.click();
		Wait.wait2Second();
		TapeChart.Enter_promoCode_Tapechart.sendKeys(PromoCode);
		assertTrue(TapeChart.Enter_promoCode_Tapechart.getAttribute("value").contains(PromoCode),
				"Failed: RomoCode Set");
		TapeChart.Click_Search_TapeChart.click();
		tapechartLogger.info("Click Search");
		try {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot, driver);
		} catch (Exception e) {
			TapeChart.Click_Search_TapeChart.click();
			tapechartLogger.info("Again Click Search");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot, driver);
		}

	}

	public void VerifyRule(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.Rule_Broken.get(0));
		Wait.wait2Second();
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait2Second();
		TapeChart.Rule_Broken.get(0).click();
		// jse.executeScript("arguments[0].click();",
		// TapeChart.Rule_Broken.get(0));
		// TapeChart.Rule_Broken.get(5).click();
		// Wait.wait2Second();
		Wait.explicit_wait_xpath(OR.Rule_Broken_PopUp, driver);
		Wait.wait2Second();
		assertEquals(TapeChart.Rule_Broken_PopUp.getText(), "Rules Broken", "Rule broken popup is not showing");
		TapeChart.Rule_Broken_Cancel.click();
		// Wait.wait2Second();
	}

	public void VerifyRule(WebDriver driver, String RoomClassAbb, String RuleName, String RuleDescription,
						   boolean isBroken) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String rate = "RackRate";
		if (isBroken) {
			rate = "NoRate";
		}
		String Path = "(//div[text()='" + RoomClassAbb
				+ "']//ancestor::div[@class='roomRatesChart']//div[contains(@class,'tapechartdatecell Available " + rate
				+ "')])[1]";

		WebElement Room = driver.findElement(By.xpath(Path));
		Wait.explicit_wait_visibilityof_webelement_350(Room, driver);
		Utility.ScrollToElement(Room, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait2Second();

		if (isBroken) {
			Room.click();
			Wait.explicit_wait_visibilityof_webelement(TapeChart.Rule_Broken_PopUp, driver);
			assertEquals(TapeChart.Rule_Broken_PopUp.isDisplayed(), true, "Rule broken popup is not Displayed");
			assertEquals(TapeChart.Rule_Broken_PopUp.getText(), "Rules Broken", "Rule broken popup is not showing");
			String ruleName = driver
					.findElement(
							By.xpath("//*[@id='rulesBrokenConfirmation']//td[contains(@data-bind,'text: RuleName')]"))
					.getText();
			assertEquals(ruleName, RuleName, "Rule Not Verified, RuleName not matched");
			String ruleDesc = driver
					.findElement(By
							.xpath("//*[@id='rulesBrokenConfirmation']//td[contains(@data-bind,'text: RuleDescription')]"))
					.getText();
			assertEquals(ruleDesc, RuleDescription, "Rule Not Verified, Rule Desc not matched");
			TapeChart.Rule_Broken_Cancel.click();
		} else {
			try {
				Room.click();
				Wait.wait2Second();
				assertEquals(TapeChart.Rule_Broken_PopUp.isDisplayed(), false, "Rule broken popup is Displayed");

				Wait.explicit_wait_visibilityof_webelement(
						driver.findElement(By.xpath(OR.OpenedReservation_CloseButton)), driver);
				Utility.ScrollToElement(driver.findElement(By.xpath(OR.OpenedReservation_CloseButton)), driver);
				driver.findElement(By.xpath(OR.OpenedReservation_CloseButton)).click();

				driver.navigate().refresh();
			} catch (Exception e) {
				tapechartLogger.info("in catch");
				String available = driver.findElement(By.xpath(Path + "//div[@class='guest_display_name_short']"))
						.getText();
				assertEquals(available, "Available", "Failed Available not Found");
				;
			}
		}

		Wait.wait2Second();
	}

	public void clickAvailableRoomClass(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		TapeChart.Click_First_Available.click();
		Wait.wait3Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);

			TapeChart.Click_Book_icon_Tapechart.click();
		} else {
			tapechartLogger.info("Rules are not broken");
		}

	}

	public void click_Unassigned(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		// WebElement ElementClick =
		// driver.findElement(By.xpath(OR.Click_Unassigned_Tapechart));

		/*
		 * Actions action = new Actions(driver);
		 * action.moveToElement(ElementClick).click(ElementClick).build().
		 * perform();
		 * action.moveToElement(ElementClick).click(ElementClick).build().
		 * perform();
		 */

		if (TestCore.targetBrowser.equalsIgnoreCase("firefox")) {

			WebElement ElementClick = driver.findElement(By.xpath(OR.Click_Unassigned_Tapechart));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ElementClick);
			jse.executeScript("window.scrollBy(0,-400)", "");

			Actions action = new Actions(driver);
			action.moveToElement(ElementClick).click(ElementClick).build().perform();
			// action.moveToElement(ElementClick).click(ElementClick).build().perform();

		} else if (TestCore.targetBrowser.equalsIgnoreCase("chrome")) {

			WebElement ElementClick = driver.findElement(By.xpath(OR.Click_Unassigned_Tapechart));
			tapechartLogger.info("Chrome");
			Wait.WaitForElement(driver, OR.Click_Unassigned_Tapechart);
			Wait.explicit_wait_elementToBeClickable(ElementClick, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ElementClick);
			jse.executeScript("window.scrollBy(0,-400)", "");
			ElementClick.click();
		}

		// TapeChart.Click_Unassigned_Tapechart.click();
		// Wait.explicit_wait_xpath(OR.New_Reservation_Tab);
		Wait.explicit_wait_xpath(OR.New_Reservation_Page_Load, driver);
	}

	public void Verify_BlackOutRoom(WebDriver driver, String BlackoutRoom) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		TapeChart.ReservationsLink.click();
		Wait.wait2Second();
		try {
			TapeChart.TapeChart.click();
		} catch (Exception e) {
			TapeChart.Tape_Chart.click();
		}
		Wait.wait2Second();
		String blackout = TapeChart.BlackOutCell.get(0).getText();
		assertEquals(blackout, "B", "Value does not match");
		Wait.wait2Second();
		/*
		 *
		 * Wait.wait2Second(); TapeChart.DatePickerIcon.get(5).click();
		 * Wait.wait2Second(); TapeChart.SelectDate.click();
		 * TapeChart.Quote_SearchButton.click(); Wait.wait2Second(); String
		 * BookButtonPath = "//a[text()='" + BlackoutRoom + <<<<<<< HEAD
		 * "']//ancestor::tr//following-sibling::button[@title='Book']";
		 * WebElement BookButton = driver.findElement(By.xpath(BookButtonPath));
		 * ======= "']//ancestor::tr//following-sibling::button[@title='Book']";
		 * WebElement BookButton = driver.findElement(By.xpath(BookButtonPath));
		 * >>>>>>> ScriptRefactoring_Vishali Utility.ScrollToElement(BookButton,
		 * driver); BookButton.click(); Wait.wait2Second();
		 *
		 * assertEquals(TapeChart.BlackOutAlert.getText(), "Blackouts Alert!",
		 * "Blackout alert box does not display");
		 *
		 * TapeChart.Blackout_OkButton.click(); Wait.wait2Second();
		 */

	}

	public void Verify_SyndicateRoom(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		String Syndicate = TapeChart.SyndicateCell.getText();
		assertEquals(Syndicate, "S", "Vlaue does not match");

	}

	public ArrayList<String> Verify_SyndicateRoom(WebDriver driver, String RoomClassAbb, String RoomNumber,
												  ArrayList<String> TestSteps) throws InterruptedException {

		Elements_TapeChart elements_TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		String PathRoomCell = "//div[text()='" + RoomClassAbb
				+ "'  and contains(@class,'roomclassname')]//ancestor::div[@class='roomClasses']//child::div[@title='"
				+ RoomNumber
				+ "']//parent::div//child::div[@class='guest_display_name_short'and text()='S']//ancestor::div[contains(@class,'tapechartdatecell')]//child::div[@class='DatesContainer']";

		Wait.wait2Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String Path = "//div[text()='" + RoomClassAbb + "' ]//ancestor::div[@class='roomClasses']//child::div[@title='"
				+ RoomNumber
				+ "']//following-sibling::div//span//div[contains(@class,'Syndicate')]//div//div//div//div[text()='S']";
		WebElement room = driver.findElement(By.xpath(Path));
		Wait.explicit_wait_visibilityof_webelement(room, driver);
		Utility.ScrollToElement(room, driver);
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait2Second();
		assertEquals(room.getText(), "S", "Syndicate room is not showing in tape chart");
		jse.executeScript("arguments[0].click();", room);

		WebElement elementRoomCell = driver.findElement(By.xpath(PathRoomCell));
		try {
			tapechartLogger.info("in try");
			elementRoomCell.click();
			Wait.wait1Second();
		} catch (Exception e) {
			tapechartLogger.info("in catch");
			jse.executeScript("arguments[0].click();", elementRoomCell);
		}
		if (elements_TapeChart.ListRuleBrokenPopUp.size() > 0 && elements_TapeChart.RuleBrokenBook_Btn.isDisplayed()) {
			elements_TapeChart.RuleBrokenBook_Btn.click();

		}
		TestSteps.add("Click syndicate room from tapechart");
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(OR.ReservationDetailPage)), driver);

		return TestSteps;
	}

	public void Verify_RoomClass(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		tapechartLogger.info("ROOMCLS:" + RoomClass);
		String RoomClassPath = "//div[.='" + RoomClass + "']";
		WebElement RoomClassAbb = driver.findElement(By.xpath(RoomClassPath));
		Wait.explicit_wait_visibilityof_webelement(RoomClassAbb, driver);
		jse.executeScript("arguments[0].scrollIntoView(true);", RoomClassAbb);
		String RoomClass_Name = RoomClassAbb.getText();
		assertEquals(RoomClass_Name, RoomClass, "Room class does not find");

	}

	public void Verify_RoomClass1(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement RoomClassName = driver.findElement(By.xpath("//div[.='" + RoomClass + "']"));
		Wait.explicit_wait_visibilityof_webelement(RoomClassName, driver);
		jse.executeScript("arguments[0].scrollIntoView(true);", RoomClassName);
		String RoomClass_Name = RoomClassName.getText();
		tapechartLogger.info("RoomClass1" + RoomClassName + "Roomclass2" + RoomClass_Name);
		assertEquals(RoomClass_Name, RoomClass, "Room class does not find");

	}

	public void Verify_RoomMaintenance(WebDriver driver, String RoomNumber) throws InterruptedException {

		String path = "//div[@title='" + RoomNumber
				+ "']//following-sibling::div//span/div/div/div/div/div[text()='Out']";
		WebElement find_room = driver.findElement(By.xpath(path));

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Utility.ScrollToElement(find_room, driver);
		Wait.wait2Second();
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait2Second();
		assertEquals(find_room.getText(), "Out", "Out of order room does not find in tapechart");

		driver.navigate().refresh();

	}

	public void CreateReservation_RoomMaintenance(WebDriver driver, String RoomNumber) throws InterruptedException {

		String path = "//div[@title='" + RoomNumber
				+ "']//following-sibling::div//span/div/div/div/div/div[text()='Available']";
		WebElement find_room = driver.findElement(By.xpath(path));
		Wait.explicit_wait_visibilityof_webelement(find_room, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", find_room);
		Wait.explicit_wait_visibilityof_webelement(find_room, driver);
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.explicit_wait_visibilityof_webelement(find_room, driver);
		assertEquals(find_room.getText(), "Available", "Available room does not find in tapechart");

	}

	public void VeirfyUnAssignedList(WebDriver driver) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		TapeChart.Unassigned_Button.click();

		int Unassigned_ListSize = driver.findElements(By.xpath(OR.UnassignedList)).size();
		int StartDate = 1;
		int EndDate = 3;
		ArrayList<String> startDate = new ArrayList<>();

		for (int i = 0; i < Unassigned_ListSize; i++) {
			try {
				startDate.add(TapeChart.UnsignedList_Date.get(StartDate).getText());
			} catch (Exception e) {

				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.UnsignedList_Date.get(StartDate));
				Wait.wait2Second();
				TapeChart.UnsignedList_Date.get(StartDate).getText();
			}
			StartDate = StartDate + 6;
			EndDate = EndDate + 6;

		}
		boolean ascendingOrder = false;
		DateFormat df = new SimpleDateFormat("E, MMM dd yyyy");
		for (int i = 0; i < startDate.size(); i++) {
			for (int j = i; j < startDate.size(); j++) {
				Date res1 = df.parse(startDate.get(i).toString() + " 2018");
				Date res2 = df.parse(startDate.get(j).toString() + " 2018");
				if (res1.equals(res2) || res1.before(res2)) {
					ascendingOrder = true;
				}
				assertEquals(ascendingOrder, true, "Unsigned list is not Ascending Order");
			}

		}

	}

	public void EarlyCheckout(WebDriver driver, String TapeChartAdults, String TapeChartChildrens, String PromoCode)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		/*
		 * String GetTodayDate = TapeChart.GetTodayDate.getText(); if
		 * (GetTodayDate.equals("1")){ String DatePickerPath =
		 * "//table[@class='datepicker-table-condensed table-condensed']//td[.='29']"
		 * ; boolean element_size =
		 * driver.findElement(By.xpath(xpathExpression)) }
		 */
		Wait.wait2Second();

		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		TapeChart.Enter_Children_Tapechart.sendKeys(TapeChartChildrens);
		// TapeChart.Click_Tapechart_Rateplan.click();
		// Wait.wait5Second();
		// TapeChart.Select_Rack_Rate.click();
		// Wait.wait3Second();
		TapeChart.Enter_promoCode_Tapechart.sendKeys(PromoCode);
		TapeChart.Click_Search_TapeChart.click();
		Wait.wait2Second();

	}

	public void VerifyReservationLimitView(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.Res_View_Limit_Element);

		WebElement ele = driver.findElement(By.xpath(
				"//*[@id=\"bpjscontainer_53\"]/div/div[2]/div[2]/div[3]/div[2]/div[1]/div[5]/div[2]/span/span/div/div/div[2]/div"));
		Actions action = new Actions(driver);
		action.moveToElement(ele).build().perform();

	}

	public void VerifyReservationLimitView(WebDriver driver, String RoomName, String RoomNumber)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		if (RoomName.equals("Double Bed Room")) {
			RoomName = "DBR";
		}
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// WebElement ele = driver.findElement(By.xpath(
		// "//*[@id=\"bpjscontainer_53\"]/div/div[2]/div[2]/div[3]/div[2]/div[1]/div[5]/div[2]/span/span/div/div/div[2]/div"));
		WebElement ele = driver.findElement(By.xpath("//div[contains(text(),'" + RoomName
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']"));
		jse.executeScript("arguments[0].scrollIntoView(true);", ele);

		Actions action = new Actions(driver);
		action.moveToElement(ele).build().perform();

	}

	public void VerifyUnassignedFooterRow(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		TapeChart.Unassigned_Button.click();
		Wait.wait2Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.DBR_Unassigned_FooterRow);

		// boolean DBR_Unassigned_FooterRowLink =
		// TapeChart.DBR_Unassigned_FooterRow.isEnabled();
		boolean DBR_Unassigned_FooterRowLink = isClickable(TapeChart.DBR_Unassigned_FooterRow, driver);
		assertEquals(DBR_Unassigned_FooterRowLink, false, "DBR_Unassigned_FooterRowLink is clickable");
		Wait.wait2Second();

		jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.JR_Unssigned_FooterRow);
		boolean JR_Unassigned_FooterRowLink = TapeChart.JR_Unssigned_FooterRow.isDisplayed();
		assertEquals(JR_Unassigned_FooterRowLink, false, "JR_Unassigned_FooterRowLink is clickable");
		Wait.wait2Second();

		jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.PR_Unssigned_FooterRow);
		boolean PR_Unassigned_FooterRowLink = TapeChart.PR_Unssigned_FooterRow.isDisplayed();
		assertEquals(PR_Unassigned_FooterRowLink, false, "PR_Unassigned_FooterRowLink is clickable");

	}

	public static boolean isClickable(WebElement el, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 6);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void click_Unassigned_RoomClass(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String CellPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']/child::div[last()]// div[contains(text(),'Unassigned')]";
		WebElement Unassigned_Reserv_Room = driver.findElement(By.xpath(CellPath));

		jse.executeScript("arguments[0].scrollIntoView(true);", Unassigned_Reserv_Room);
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitUntilPresenceOfElementLocated(CellPath, driver);
		jse.executeScript("arguments[0].click();", Unassigned_Reserv_Room);
		Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath(OR.NewReservation_ModuleLoading)), 150);
		// TapeChart.Unassigned_Reserv_Room.click();

	}

	public void DragReservtion(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		WebElement From = driver.findElement(By.xpath(OR.ReservationToDrag));
		WebElement To = driver.findElement(By.xpath(OR.NewPlaceForReserv));
		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		Action dragAndDrop = act.clickAndHold(From).moveToElement(To).release(To).build();
		dragAndDrop.perform();
		Wait.wait15Second();
		TapeChart.ConfirmChangesButton.click();
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertTrue(TapeChart.Toaster_Message.isDisplayed(), "Toast Message is'nt Displayed & element not moved");
		Wait.wait2Second();
		MoveBackReservation(driver);

	}

	private void MoveBackReservation(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		WebElement From = driver.findElement(By.xpath(OR.ReservationToDragBack));
		WebElement To = driver.findElement(By.xpath(OR.PreviousPlaceofReservation));
		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		Action dragAndDrop = act.clickAndHold(From).moveToElement(To).release(To).build();
		dragAndDrop.perform();
		Wait.wait2Second();
		TapeChart.ConfirmChangesButton.click();
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertTrue(TapeChart.Toaster_Message.isDisplayed(), "Toast Message is'nt Displayed & element not moved");
	}

	public void ExtendReservation(WebDriver driver, String RoomNumber, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		Wait.explicit_wait_xpath(path, driver);
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		int size = driver.findElements(By.xpath(path)).size();
		int preWidth = 0;
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.get(size - 1).getSize().getWidth();
			tapechartLogger.info("Pre Width : " + divWidth);
			preWidth = divWidth;
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			// Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			// Wait.wait2Second();
			int divWidth = ele.getSize().getWidth();
			tapechartLogger.info("Pre Width : " + divWidth);
			preWidth = divWidth;
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold().moveByOffset(divWidth, 0)
					.release().build();
			resizable.perform();
		}

		Wait.explicit_wait_visibilityof_webelement_350(TapeChart.CheckIn, driver);
		assertEquals(TapeChart.CheckIn.getText(), "Check In", "Check in doesnot display");
		assertEquals(TapeChart.CheckOut.getText(), "Check Out", "Check out does not display");
		assertEquals(TapeChart.TripTotal.getText(), "Trip Total", "Trip total does not display");
		assertEquals(TapeChart.BalanceDue.getText(), "Balance Due", "Balance due does not display");
		TapeChart.ConfirmChangeReservation_Button.click();

		// verify toaster message here
		try {
			// Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message,
			// driver);
			assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
					"Reservation does not expand");
		} catch (Exception e) {
			System.err.println("Toaster not Displayed");
		}
		int size2 = driver.findElements(By.xpath(path)).size();

		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth2 = ele.get(size - 1).getSize().getWidth();
			tapechartLogger.info("Post Width : " + divWidth2);
			assertTrue(divWidth2 > preWidth, "Failed : Reservation not Extended");
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			// Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			// Wait.wait2Second();
			int divWidth2 = ele.getSize().getWidth();
			tapechartLogger.info("Post Width : " + divWidth2);
			assertTrue(divWidth2 > preWidth, "Failed : Reservation not Extended");
		}

	}

	public ArrayList<String> ConfirmExtendReservation(WebDriver driver, String RoomNumber, String RoomName,
													  ArrayList<String> test_steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		int divWidth = 0;
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomName + "']/../../div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()]";

		int size = driver.findElements(By.xpath(path)).size();
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.get(size - 1).getSize().getWidth();
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.getSize().getWidth();
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold().moveByOffset(divWidth, 0)
					.release().build();
			resizable.perform();
		}

		Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChangeReservation_Button, driver);
		assertEquals(TapeChart.ConfirmChangeReservation_Button.getText(), "Confirm Changes",
				"Confirm button does not display");
		TapeChart.ConfirmChangeReservation_Button.click();
		try {
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangeReservation_Button, 30);
		} catch (Exception e) {
			Utility.app_logs.info("Again click Confrim Changes Button");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChangeReservation_Button, driver);
			TapeChart.ConfirmChangeReservation_Button.click();
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangeReservation_Button, 30);
		}
		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not expand");
		test_steps.add("Reservation has been Updated Successfully");
		// verify size change
		Wait.wait2Second();

		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			int AfterCancel_divWidth = ele.get(size - 1).getSize().getWidth();
			assertNotEquals(AfterCancel_divWidth, divWidth, "Reservation has not been expanded");
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			int AfterCancel_divWidth = ele.getSize().getWidth();
			assertNotEquals(AfterCancel_divWidth, divWidth, "Reservation has not been expanded");
		}

		return test_steps;

	}

	public void CancelExtendedReservation(WebDriver driver, String RoomNumber, String RoomClassAbb)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		int divWidth = 0;
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "//div[contains(text(),'" + RoomClassAbb
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";

		int size = driver.findElements(By.xpath(path)).size();
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.get(size - 1).getSize().getWidth();
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.getSize().getWidth();
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold().moveByOffset(divWidth, 0)
					.release().build();
			resizable.perform();
		}

		Wait.wait5Second();

		assertEquals(TapeChart.CancelChangeReservation_Button.getText(), "Cancel", "Cancel button does not display");
		TapeChart.CancelChangeReservation_Button.click();
		Wait.wait2Second();

		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			int AfterCancel_divWidth = ele.get(size - 1).getSize().getWidth();
			assertEquals(AfterCancel_divWidth, divWidth, "Reservation has been expand after cancel");
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			int AfterCancel_divWidth = ele.getSize().getWidth();
			assertEquals(AfterCancel_divWidth, divWidth, "Reservation has been expand after cancel");
		}

	}

	public void VerifyResevation_handles(WebDriver driver, String RoomNumber) throws InterruptedException {

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "//div[@title='" + RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";

		int size = driver.findElements(By.xpath(path)).size();
		if (size > 1) {
			List<WebElement> hoverElement = driver.findElements(By.xpath(path));
			int before_hover_width = hoverElement.get(size - 1).getSize().getWidth();

			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement.get(size - 1)).perform();
			int after_hover_width = hoverElement.get(size - 1).getSize().getWidth();
			assertNotEquals(after_hover_width, before_hover_width, "handles does not appear on hover");

		} else {

			WebElement hoverElement = driver.findElement(By.xpath(path));
			int before_hover_width = hoverElement.getSize().getWidth();

			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			int after_hover_width = hoverElement.getSize().getWidth();
			assertNotEquals(after_hover_width, before_hover_width, "handles does not appear on hover");

		}

	}

	public void TapeChartSearch_1Day(WebDriver driver, String TapeChartAdults, String TapeChartChildrens,
									 String PromoCode) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();

		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		TapeChart.Enter_Children_Tapechart.sendKeys(TapeChartChildrens);

		TapeChart.TapeChart_1DayButton.click();

		TapeChart.Enter_promoCode_Tapechart.sendKeys(PromoCode);
		TapeChart.Click_Search_TapeChart.click();

		Wait.wait2Second();

	}

	public void FindAvailableSlot(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.TapeChartAvailableSlot);
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait2Second();
		TapeChart.TapeChartAvailableSlot.click();

	}

	public void ClickAvailableSlot(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Elements_Reservation ReservationPage = new Elements_Reservation(driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			jse.executeScript("arguments[0].scrollIntoView(true);", TapeChart.ClickTapeChartAvailableSlot);
			try {
				Wait.wait2Second();
				TapeChart.ClickTapeChartAvailableSlot.click();

			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				Wait.wait2Second();
				TapeChart.ClickTapeChartAvailableSlot.click();
				tapechartLogger.info("Scrol back 300");

			}
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationDetailPage, driver);
		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);

				TapeChart.Click_Book_icon_Tapechart.click();
			} else {
				tapechartLogger.info("Rules are not broken");
			}
		}
		assertTrue(ReservationPage.ReservationPage.isDisplayed(), "Reservation Page didn't load");

	}

	public void ClickAvailableSlot(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Elements_Reservation ReservationPage = new Elements_Reservation(driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String CellPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.explicit_wait_visibilityof_webelement(AvailableSlot, driver);
		try {
			// jse.executeScript("arguments[0].scrollIntoView(true);",
			// AvailableSlot);
			Utility.ScrollToElement(AvailableSlot, driver);
			try {
				Wait.wait2Second();
				AvailableSlot.click();

			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				Wait.wait2Second();
				AvailableSlot.click();
				tapechartLogger.info("Scrol back 300");

			}
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationDetailPage, driver);
		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.WaitForElement(driver, OR.Rules_Broken_popup);

				TapeChart.Click_Book_icon_Tapechart.click();
			} else {
				tapechartLogger.info("Rules are not broken");
			}
		}
		assertTrue(ReservationPage.ReservationPage.isDisplayed(), "Reservation Page didn't load");

	}

	public void ClickAvailableSlot(WebDriver driver, ArrayList<String> test_steps, String RoomClass)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.explicit_wait_visibilityof_webelement(AvailableSlot, driver);
		try {
			jse.executeScript("arguments[0].scrollIntoView(true);", AvailableSlot);
			try {
				Wait.wait2Second();
				AvailableSlot.click();

			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				Wait.wait2Second();
				AvailableSlot.click();
				test_steps.add("Clicking on available slot from tape chart");

			}
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationDetailPage, driver);
		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				// Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
				Wait.wait5Second();
				Wait.WaitForElement(driver, OR.Click_Book_icon_Tapechart);
				TapeChart.Click_Book_icon_Tapechart.click();
				test_steps.add("Clicking on book icon");
			} else {
				tapechartLogger.info("Rules are not broken");
			}
		}

	}

	public void getUnassignedTapechartResCount(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait5Second();
		// Wait.WaitForElement(driver, OR.getUnsassignedResCountTapechart);
		String unassignedResCountTP = TapeChart.getUnsassignedResCountTapechart.getText();
		String unassignedTapechart1 = unassignedResCountTP.replaceAll("[^a-zA-Z0-9]", "");
		// unassignedResCountTapechart=tapechartUnassigned1.replaceAll("[]",
		// "");
		unassignedResCountTapechart = unassignedTapechart1.replaceAll("UNASSIGNED", "");
		tapechartLogger.info("Unassigned Reservation Count from Tapechart before creating the reservation: "
				+ unassignedResCountTapechart);
		Wait.wait5Second();
	}

	public void getUnassignedResCountTapechartAfterCreatingRes(WebDriver driver, ExtentTest test)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait10Second();
		String unassignedResCountTP1 = TapeChart.getUnsassignedResCountTapechart.getText();
		String unassignedTapechart2 = unassignedResCountTP1.replaceAll("[^a-zA-Z0-9]", "");
		unassignedResCountTapechartAfterCreatingRes = unassignedTapechart2.replaceAll("UNASSIGNED", "");
		tapechartLogger.info("Unassigned Reservation from TapechartCount after creating the reservation: "
				+ unassignedResCountTapechartAfterCreatingRes);
		Wait.wait5Second();
	}

	public void getUnassignedReservationCountValidationsInTapechart(WebDriver driver, ExtentTest test) {

		int unassignedCountTapechartAfterCreatingReservation = Integer
				.parseInt(unassignedResCountTapechartAfterCreatingRes);
		int unassignedCountTapechartBefore = Integer.parseInt(unassignedResCountTapechart) + 1;

		if (unassignedCountTapechartAfterCreatingReservation == unassignedCountTapechartBefore) {

			tapechartLogger.info(" Successfully validated unassigned Reservation Count in Tapechart ");
		}
	}

	public String GetAvailableSlot_RoomNumber(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String RoomNumber = "";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]//ancestor::div[contains(@class,'roomsrow')]/child::div[contains(@class,'roomnumber')]/span";
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		jse.executeScript("arguments[0].scrollIntoView(true);", AvailableSlot);
		try {
			Wait.wait2Second();
			RoomNumber = AvailableSlot.getText();
			Utility.app_logs.info("Room NUmber " + RoomNumber);

		} catch (Exception e) {
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();

			RoomNumber = AvailableSlot.getText();
			tapechartLogger.info("Scrol back 300");
		}

		Utility.app_logs.info("Room NUmber " + RoomNumber);
		return RoomNumber;

	}

	public void ClickAvailableSlot(WebDriver driver, String RoomClass, String PropertyNumber)
			throws InterruptedException {

		Wait.wait10Second();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		WebElement Property2 = driver
				.findElement(By.xpath("(" + OR.Property_ExpandButton + ")[" + PropertyNumber + "]"));
		Utility.ScrollToElement(Property2, driver);
		String PropertyName = driver
				.findElement(By.xpath("(//div [@class='propertyclickbutton'])[" + PropertyNumber + "]")).getText();
		Property2.click();

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div [@class='propertyclickbutton' and text ()='" + PropertyName
				+ "']//parent::div//following-sibling::div//child::div[contains(@class,'roomclassname') and text()='"
				+ RoomClass
				+ "']//parent::*//following-sibling::div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::div[contains(@class,'tapechartdatecell Available')]";

		List<WebElement> AvailableSlots = driver.findElements(By.xpath(CellPath));
		tapechartLogger.info("Total Available slots in RoomClass : " + RoomClass + " are " + AvailableSlots.size());
		assertTrue(AvailableSlots.size() > 1, "Failed : No room Available");

		WebElement AvailableSlot = AvailableSlots.get(0);
		try {
			jse.executeScript("arguments[0].scrollIntoView(true);", AvailableSlot);
			try {
				Wait.wait2Second();
				AvailableSlot.click();

			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				Wait.wait2Second();
				AvailableSlot.click();
				tapechartLogger.info("Scrol back 300");

			}
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationDetailPage, driver);
			assertEquals(driver.findElement(By.xpath(OR.Get_Property_Name)).getText(), PropertyName,
					"Failed: PropertyName missmatched");
		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);

				TapeChart.Click_Book_icon_Tapechart.click();
			} else {
				tapechartLogger.info("Rules are not broken");
			}
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationDetailPage, driver);
			assertEquals(driver.findElement(By.xpath(OR.Get_Property_Name)).getText(), PropertyName,
					"Failed: PropertyName missmatched");

		}

	}

	public void ClickReservationTapChart(WebDriver driver) throws InterruptedException {

		WebElement Reservation = driver
				.findElement(By.xpath("//div[@title='325']//following-sibling::div//div[@class='DatesContainer']"));
		Reservation.click();

	}

	public void ClickReservation(WebDriver driver, String RoomNumber) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		tapechartLogger.info("ROomNum:" + RoomNumber);
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "(//div[@title='" + RoomNumber
				+ "']//following-sibling::div//div[@class='DatesContainer']//parent::div)[1]";
		WebElement Reservation = driver.findElement(By.xpath(path));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", Reservation);
		// Reservation.click();

	}

	public void ExtendReservation_Verify(WebDriver driver, String RoomNumber)
			throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "(//div[@title='" + RoomNumber
				+ "']//following-sibling::div//div[@class='DatesContainer'])[last()]";

		int size = driver.findElements(By.xpath(path)).size();
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.get(size - 1).getSize().getWidth();
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.getSize().getWidth();
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold().moveByOffset(divWidth, 0)
					.release().build();
			resizable.perform();
		}

		Wait.wait5Second();
		VerifyExtendDates(driver);
		TapeChart.ConfirmChangesButton.click();

		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not expand");

	}

	public void VerifyExtendDates(WebDriver driver) throws ParseException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.NewDate_CheckOut, driver);
		String newDate = TapeChart.NewDate_CheckOut.getText();
		tapechartLogger.info(newDate);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.PreviousDate_CheckOut, driver);
		tapechartLogger.info(TapeChart.PreviousDate_CheckOut.getText());
		String PreviousDate = TapeChart.PreviousDate_CheckOut.getText().substring(4);

		tapechartLogger.info(PreviousDate);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		tapechartLogger.info(sdf.parse(PreviousDate).before(sdf.parse(newDate)));
		assertTrue(sdf.parse(PreviousDate).before(sdf.parse(newDate)), "Failed: Extended dates");

	}

	public void VerifyReduceDates(WebDriver driver) throws ParseException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		try {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.NewDate_CheckOut, driver);
		} catch (Exception e) {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.NewDate_CheckOut, driver);
		}
		String newDate = TapeChart.NewDate_CheckOut.getText();
		tapechartLogger.info(newDate);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.PreviousDate_CheckOut, driver);
		tapechartLogger.info(TapeChart.PreviousDate_CheckOut.getText());
		String PreviousDate = TapeChart.PreviousDate_CheckOut.getText().substring(4);

		tapechartLogger.info(PreviousDate);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		tapechartLogger.info(sdf.parse(newDate).before(sdf.parse(PreviousDate)));
		assertTrue(sdf.parse(newDate).before(sdf.parse(PreviousDate)), "Failed: Extended dates");

	}

	public void FindReservationToMove(WebDriver driver, String RoomNumber) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "//div[@title='" + RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		WebElement From = driver.findElement(By.xpath(path));
		driver.findElement(By
				.xpath("//*[@id='bpjscontainer_53']/div/div[2]/div[2]/div[3]/div[2]/div[1]/div[]/div[2]/span/div[7]"));
		WebElement To = driver.findElement(By.xpath(OR.PreviousPlaceofReservation));
		;

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		Action dragAndDrop = act.clickAndHold(From).moveToElement(To).release(To).build();
		dragAndDrop.perform();
		Wait.wait5Second();

		TapeChart.ConfirmChangesButton.click();

		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not expand");

	}

	public void MoveReservation(WebDriver driver, String RoomNumber, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		tapechartLogger.info("PTah:" + path);
		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='" + NextRoom
				+ "']//following-sibling::div//div[@class='tapechartdatecell Available RackRate']";
		try {
			tapechartLogger.info(driver.findElement(By.xpath(ToPath)).isDisplayed());
		} catch (Exception e) {
			for (int i = 0; i <= 10; i++) {
				ToPath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom + "']//following-sibling::div//div[@class='DatesContainer']";
				String Reservationpath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom
						+ "']//following-sibling::div//div[@class='DatesContainer']//preceding-sibling::div//child::div[@class='guest_display_name_short']";

				tapechartLogger.info("To pTah:" + ToPath + "ReserPath:" + Reservationpath);
				String ReservationName = driver.findElement(By.xpath(Reservationpath)).getText();
				tapechartLogger.info(ReservationName);
				if (ReservationName.contains("Available")) {
					break;
				} else {
					nextRoom = Integer.parseInt(NextRoom) + 1;
					NextRoom = Integer.toString(nextRoom);
				}
			}
		}
		tapechartLogger.info("To pTah:" + ToPath);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(path)));

		jse.executeScript("window.scrollBy(0,-300)");
		tapechartLogger.info("Scrolled to the reservation");
		WebElement From = driver.findElement(By.xpath(path));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		Wait.wait5Second();

		TapeChart.ConfirmChangesButton.click();

		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not Move");

	}

	public ArrayList<String> MoveReservation_Cancel(WebDriver driver, String RoomNumber, String RoomClass,
													String RoomClass2, ArrayList<String> test_steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()-1]";
		String ToPath = "//div[contains(text(),'" + RoomClass2
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("arguments[0].scrollIntoView(true);",
		// driver.findElement(By.xpath(path)));
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		jse.executeScript("window.scrollBy(0,-200)");
		Wait.wait2Second();
		tapechartLogger.info("Scrolled to the reservation");
		WebElement From = driver.findElement(By.xpath(path));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();
		test_steps.add("Drag the reservation to Next date from RoomClass: '" + RoomClass + "' to '" + RoomClass2 + "'");
		Wait.wait2Second();
		try {
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationUpdate_Popup, driver);
			assertTrue(TapeChart.ReservationUpdate_Popup.isDisplayed(), "Reservation Update popup is not open");
		} catch (Exception e) {
			act = new Actions(driver);
			// Dragged and dropped
			act.dragAndDrop(From, To).build().perform();
			test_steps.add(
					"Drag the reservation to Next date from RoomClass: '" + RoomClass + "' to '" + RoomClass2 + "'");
			Wait.wait2Second();
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationUpdate_Popup, driver);
			assertTrue(TapeChart.ReservationUpdate_Popup.isDisplayed(), "Reservation Update popup is not open");
		} catch (Error e) {
			act = new Actions(driver);
			// Dragged and dropped
			act.dragAndDrop(From, To).build().perform();
			test_steps.add(
					"Drag the reservation to Next date from RoomClass: '" + RoomClass + "' to '" + RoomClass2 + "'");
			Wait.wait2Second();
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationUpdate_Popup, driver);
			assertTrue(TapeChart.ReservationUpdate_Popup.isDisplayed(), "Reservation Update popup is not open");
		}

		test_steps.add("Reservation Update Popup Appears");
		Wait.explicit_wait_visibilityof_webelement(TapeChart.CancelChangesButton, driver);
		Utility.ScrollToElement(TapeChart.CancelChangesButton, driver);
		TapeChart.CancelChangesButton.click();
		test_steps.add("Click Cancel Reservation Update");
		try {
			Wait.waitForElementToBeGone(driver, TapeChart.CancelChangesButton, 30);
		} catch (Exception e) {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.CancelChangesButton, driver);
			Utility.ScrollToElement(TapeChart.CancelChangesButton, driver);
			TapeChart.CancelChangesButton.click();
			test_steps.add("Click Cancel Reservation Update");
			Wait.waitForElementToBeGone(driver, TapeChart.CancelChangesButton, 30);
		}
		test_steps.add("Reservation Update Popup Disappears");
		Thread.sleep(3000);
		assertEquals(driver.findElement(By.xpath(path)).getLocation(), Location, "Failed: Location has been Changed");
		test_steps.add("Reservation is Displayed in it's Original Position");
		return test_steps;

	}

	public void ClickAnyReservation(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("(" + OR.ClickReservation + ")[2]")));
		Wait.wait2Second();
		jse.executeScript("window.scrollBy(0,-300)");
		// TapeChart.ClickReservation.click();
		driver.findElement(By.xpath("(" + OR.ClickReservation + ")[1]")).click();
		Wait.wait2Second();

	}

	public void VerifyRate_TapeChart(WebDriver driver, String RateValue) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String NewSetRate = TapeChart.FirstRoomClass_Rate_In_Tapechart.getText();
		assertTrue(NewSetRate.equals("$" + RateValue), "Failed:New Rate Value is not correct");

	}

	public void VerifyRate_TapeChart(WebDriver driver, String RateValue, String RoomClassAbb)
			throws InterruptedException {

		String RoomClassRatePath = "//div[text()='" + RoomClassAbb
				+ "' and contains(@class,'roomclassname')]//..//div//div";
		tapechartLogger.info(RoomClassRatePath);

		List<WebElement> RoomRate = driver.findElements(By.xpath(RoomClassRatePath));
		Wait.explicit_wait_visibilityof_webelement(RoomRate.get(0), driver);
		Utility.ScrollToElement(RoomRate.get(0), driver);
		String NewSetRate = RoomRate.get(0).getText();
		tapechartLogger.info("TAPCHART : " + NewSetRate);
		tapechartLogger.info("rate value : " + RateValue);
		assertEquals(NewSetRate, "$" + RateValue, "new rate value is not founded");
	}

	public void TapeChartSearch_Checkin(WebDriver driver, String Checkin) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		SimpleDateFormat resultDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		SimpleDateFormat CheckInDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date ArrivalDate = resultDateFormat.parse(Checkin);
		String CheckinDate = CheckInDateFormat.format(ArrivalDate);
		tapechartLogger.info(CheckinDate);
		Wait.waitUntilPresenceOfElementLocated(OR.TapeChart_CheckIn, driver);
		TapeChart.TapeChart_CheckIn.sendKeys(CheckinDate);
		assertTrue(TapeChart.TapeChart_CheckIn.getAttribute("value").contains(CheckinDate), "Failed: CheckIn");

		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys("1");
		assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains("1"), "Failed: Adult set");
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Click_Search_TapeChart.click();
		Wait.wait2Second();

	}

	public void ExtendReservation_Room(WebDriver driver, String Room) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		String RoomClass = Room.split("\\:")[0].replaceAll("\\s+", "");
		String RoomNumber = Room.split("\\:")[1].replaceAll("\\s+", "");
		tapechartLogger.info("RC:" + RoomClass + " RN:" + RoomNumber);
		Wait.wait2Second();
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomClass + "']/../../div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()]";

		Wait.explicit_wait_visibilityof_webelement_150(driver.findElement(By.xpath(path)), driver);
		int size = driver.findElements(By.xpath(path)).size();

		tapechartLogger.info("Sixe is :" + size);
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Utility.ScrollToElement(ele.get(size - 1), driver);
			Utility.app_logs.info("Scroll");
			jse.executeScript("window.scrollBy(0,-500)");
			Utility.app_logs.info("Scroll back");
			Wait.wait2Second();
			int divWidth = ele.get(size - 1).getSize().getWidth();
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			Utility.ScrollToElement(ele, driver);
			Utility.app_logs.info("Scroll ");
			jse.executeScript("window.scrollBy(0,-500)");
			Utility.app_logs.info("Scroll back");
			Wait.wait2Second();
			int divWidth = ele.getSize().getWidth();
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold().moveByOffset(divWidth, 0)
					.release().build();
			resizable.perform();
		}
		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		VerifyExtendDates(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.ConfirmChangesButton, driver);

		TapeChart.ConfirmChangesButton.click();

		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not expand");

	}

	public void ReduceReservation(WebDriver driver, String RoomNumber, String RoomName)
			throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		int divWidth = 0;
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomName + "']/../../div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()]";
		tapechartLogger.info(path);
		Wait.explicit_wait_visibilityof_webelement_150(driver.findElement(By.xpath(path)), driver);
		int size = driver.findElements(By.xpath(path)).size();
		tapechartLogger.info("Sixe is :" + size);
		int preWidth = 0;
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.get(size - 1).getSize().getWidth();
			tapechartLogger.info("Pre Width Reduce : " + divWidth);
			preWidth = divWidth;
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(-divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.getSize().getWidth();
			tapechartLogger.info("Pre Width Reduce : " + divWidth);
			preWidth = divWidth;
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold()
					.moveByOffset(-divWidth, 0).release().build();
			resizable.perform();

		}
		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		Wait.wait2Second();
		VerifyReduceDates(driver);

		TapeChart.ConfirmChangesButton.click();

		try {
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 10000);
			tapechartLogger.info("confirmed clicked");
		} catch (Exception e) {
			TapeChart.ConfirmChangesButton.click();
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 10000);
		}

		try {
			// verify toaster message here
			// Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message,
			// driver);
			assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
					"Reservation does not Reduce");
		} catch (Exception e) {
			System.err.println("Toast Not Displayed");
		}
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.get(size - 1).getSize().getWidth();
			tapechartLogger.info("Post Width Reduce : " + divWidth);
			assertTrue(divWidth < preWidth, "Failed : Reservation not Reduced");

		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			divWidth = ele.getSize().getWidth();
			tapechartLogger.info("Post Width Reduce : " + divWidth);
			assertTrue(divWidth < preWidth, "Failed : Reservation not Reduced");
		}

	}

	public void ReduceReservation_Room(WebDriver driver, String Room) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		String RoomClass = Room.split("\\:")[0].replaceAll("\\s+", "");
		String RoomNumber = Room.split("\\:")[1].replaceAll("\\s+", "");
		tapechartLogger.info("RC:" + RoomClass + " RN:" + RoomNumber);
		Wait.wait2Second();
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomClass + "']/../../div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()]";

		Wait.explicit_wait_visibilityof_webelement_150(driver.findElement(By.xpath(path)), driver);
		int size = driver.findElements(By.xpath(path)).size();
		tapechartLogger.info("Sixe is :" + size);
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.get(size - 1).getSize().getWidth();
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(-divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.getSize().getWidth();
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold()
					.moveByOffset(-divWidth, 0).release().build();
			resizable.perform();
		}
		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		VerifyReduceDates(driver);

		TapeChart.ConfirmChangesButton.click();

		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not Reduce");

	}

	public void Verify_RoomClass_AfterRoomMainModification(WebDriver driver, String RoomNumber, String RoomClassAbb,
														   boolean isOut) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// String RoomName = RoomClass.split(" : ")[0];
		// String RoomNumber = RoomClass.split(" : ")[1];
		// tapechartLogger.info(RoomName);
		Wait.wait2Second();

		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomClassAbb + "']/../../div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])";
		Wait.wait2Second();
		List<WebElement> ele = driver.findElements(By.xpath(path));

		Wait.wait2Second();
		String eleText = "Available";
		if (isOut) {
			for (int i = 0; i < ele.size(); i++) {
				if (i < 2) {
					jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(i));
					WebElement parent = ele.get(i).findElement(By.xpath(".."));
					tapechartLogger.info(parent.getText());
					if (parent.getText().equalsIgnoreCase("Out")) {
						eleText = parent.getText();
						break;
					}
				}
			}
			assertTrue(eleText.equals("Out"), "Failed: RoomMain_Item not modified");
		} else {
			for (int i = 0; i < ele.size(); i++) {
				if (i < 1) {
					jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(i));
					WebElement parent = ele.get(i).findElement(By.xpath(".."));
					tapechartLogger.info(parent.getText());
					if (parent.getText().equalsIgnoreCase("Out")) {
						eleText = parent.getText();
						assertTrue(eleText.equals("Out"), "Failed: RoomMain_Item not modified");
						break;
					}
				} else if (i == 1) {
					jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(i));
					WebElement parent = ele.get(i).findElement(By.xpath(".."));
					tapechartLogger.info(parent.getText());
					if (!parent.getText().equalsIgnoreCase("Out")) {
						eleText = parent.getText();
						assertTrue(!eleText.equals("Out"), "Failed: RoomMain_Item Displayed Out");
						break;
					}
				}
			}

		}
	}

	public void Verify_RoomClass_AfterRoomMainDeletion(WebDriver driver, String RoomNumber, String RoomClassAbb)
			throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		Wait.wait2Second();
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomClassAbb + "']/../../div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()]";

		List<WebElement> ele = driver.findElements(By.xpath(path));

		Wait.wait2Second();
		String eleText = "Available";
		for (int i = 0; i < ele.size(); i++) {
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(i));
			WebElement parent = ele.get(i).findElement(By.xpath(".."));
			tapechartLogger.info(parent.getText());
			if (parent.getText().equalsIgnoreCase("Out")) {
				eleText = parent.getText();
				break;
			}
		}

		assertTrue(!eleText.equals("Out"), "Failed: RoomMain_Item not deleted");

	}

	public void ClickEmptyCell(WebDriver driver, String roomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		String EmptyCellPath = "//div[contains(text(),'" + roomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//following-sibling::div[contains(@class,'noResAvail')]";
		Wait.wait2Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		List<WebElement> EmptyCells = driver.findElements(By.xpath(EmptyCellPath));
		int TotalEmptyCells = EmptyCells.size();
		tapechartLogger.info("Total Cells :" + TotalEmptyCells);
		for (int i = 1; i < TotalEmptyCells; i++) {
			String CellPath = "(" + EmptyCellPath + ")[" + i + "]";
			WebElement cell = driver.findElement(By.xpath(CellPath));
			tapechartLogger.info(CellPath);
			jse.executeScript("arguments[0].scrollIntoView(true);", cell);
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait5Second();
			// jse.executeScript("arguments[0].click();", cell);
			try {
				int width = cell.getSize().getWidth();
				int height = cell.getSize().getHeight();
				Actions act = new Actions(driver);
				act.moveToElement(cell).moveByOffset((width / 2) - 2, (height / 2) - 2);
				Wait.wait2Second();
				act.click().perform();
				Wait.wait2Second();
				Utility.app_logs.info("Clicked on Empty Cell");
				if (TapeChart.Rules_Broken_popup.isDisplayed()) {
					Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);

					TapeChart.Click_Book_icon_Tapechart.click();
				} else {
					tapechartLogger.info("Rules are not broken");
				}

				Wait.explicit_wait_xpath(OR.New_Reservation_Page_Load, driver);
				if (driver.findElement(By.xpath(OR.OpenedReservation_Name)).getText().contains("New Reser...")) {
					break;
				} else {
					driver.findElement(By.xpath(OR.OpenedReservation_CloseButton)).click();
					Wait.explicit_wait_xpath(EmptyCellPath, driver);
				}
			} catch (Exception e) {
				Utility.app_logs.info("No reservation tab open");

			}
		}

	}

	public void ExtendRes_VerifyField(WebDriver driver, String RoomNumber) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "//div[@title='" + RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";

		int size = driver.findElements(By.xpath(path)).size();
		if (size > 1) {
			List<WebElement> ele = driver.findElements(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele.get(size - 1));
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.get(size - 1).getSize().getWidth();
			int divHeight = ele.get(size - 1).getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele.get(size - 1), divWidth, halfHeight).clickAndHold()
					.moveByOffset(divWidth, 0).release().build();
			resizable.perform();
		} else {
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.getSize().getWidth();
			int divHeight = ele.getSize().getHeight();
			// Get half of Height
			int halfHeight = divHeight / 2;
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold().moveByOffset(divWidth, 0)
					.release().build();
			resizable.perform();
		}

		Wait.wait10Second();
		assertEquals(TapeChart.CheckIn.getText(), "Check In", "Check in does not display");
		assertEquals(TapeChart.CheckOut.getText(), "Check Out", "Check out does not display");
		assertEquals(TapeChart.TripTotal.getText(), "Trip Total", "Trip total does not display");
		assertEquals(TapeChart.BalanceDue.getText(), "Balance Due", "Balance due does not display");

	}

	public void MoveReservation_WithoutConfirm(WebDriver driver, String RoomNumber, String RoomClass)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		Wait.explicit_wait_visibilityof_webelement_150(driver.findElement(By.xpath(path)), driver);
		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[@title='" + NextRoom
				+ "']//following-sibling::div//div[@class='tapechartdatecell Available RackRate']";
		try {
			tapechartLogger.info(driver.findElement(By.xpath(ToPath)).isDisplayed());
		} catch (Exception e) {
			for (int i = 0; i <= 10; i++) {
				ToPath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom + "']//following-sibling::div//div[@class='DatesContainer']";
				String Reservationpath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom
						+ "']//following-sibling::div//div[@class='DatesContainer']//preceding-sibling::div//child::div[@class='guest_display_name_short']";

				tapechartLogger.info("To pTah:" + ToPath + "ReserPath:" + Reservationpath);
				WebElement Reservation = driver.findElement(By.xpath(Reservationpath));
				Wait.explicit_wait_visibilityof_webelement(Reservation, driver);
				Utility.ScrollToElement(Reservation, driver);
				String ReservationName = Reservation.getText();
				tapechartLogger.info(ReservationName);
				if (ReservationName.contains("Available")) {
					break;
				} else {
					nextRoom = Integer.parseInt(NextRoom) + 1;
					NextRoom = Integer.toString(nextRoom);
				}
			}
		}

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(path)));

		jse.executeScript("window.scrollBy(0,-300)");
		tapechartLogger.info("Scrolled to the reservation");
		WebElement From = driver.findElement(By.xpath(path));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		try {
			// verify toaster message here
			Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(OR.Toaster_Message)), driver);
			tapechartLogger.info(TapeChart.Toaster_Message.getText());
			assertEquals(TapeChart.Toaster_Message.getText(), "Reservation(s) moved Successfully",
					"Reservation does not Move");
			Utility.app_logs.info("Confirm popup doesnot display");
		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
				TapeChart.Click_Book_icon_Tapechart.click();
				tapechartLogger.info("Rules are broken");
			} else {
				tapechartLogger.info("Rules are not broken");
			}
			try {
				// verify toaster message here
				Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(OR.Toaster_Message)), driver);
				tapechartLogger.info(TapeChart.Toaster_Message.getText());
				assertEquals(TapeChart.Toaster_Message.getText(), "Reservation(s) moved Successfully",
						"Reservation does not Move");
				Utility.app_logs.info("Confirm popup doesnot display");
			} catch (Exception g) {
				Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChangesButton, driver);
				TapeChart.ConfirmChangesButton.click();
				// verify toaster message here
				Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(OR.Toaster_Message)), driver);
				Utility.app_logs.info(TapeChart.Toaster_Message.getText());
				assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
						"Reservation does not Reduce");
			}
		}
	}

	public ArrayList<String> MoveReservationSameClass_WithoutConfirm(WebDriver driver, String RoomNumber,
																	 String RoomClass, String RoomClass2, ArrayList<String> test_steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()]";
		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		Utility.app_logs.info(path);
		String NextRoom = RoomNumber;
		tapechartLogger.info("RoomClass:" + RoomClass);
		String ToPath = "//div[contains(text(),'" + RoomClass2
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		// String ToPath = "//div[contains(text(),'" + RoomClass
		// + "') and
		// contains(@class,'roomclassname')]//parent::*//following-sibling::div//following-sibling::div//div[@class='tapechartdatecell
		// Available RackRate']";
		Utility.app_logs.info(ToPath);
		List<WebElement> AvailableRooms = driver.findElements(By.xpath(ToPath));
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		Wait.wait2Second();
		if (AvailableRooms.size() == 0) {
			assertTrue(false, "Failed No Room Available for the selected criteria");
		} else {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			// jse.executeScript("arguments[0].scrollIntoView(true);",
			// driver.findElement(By.xpath(path)));
			jse.executeScript("window.scrollBy(0,-200)");

			tapechartLogger.info("Scrolled to the reservation");
			WebElement From = driver.findElement(By.xpath(path));
			Point Location = From.getLocation();

			Wait.explicit_wait_xpath(ToPath, driver);
			WebElement To = driver.findElement(By.xpath(ToPath));

			// Using Action class for drag and drop.
			Actions act = new Actions(driver);
			// Dragged and dropped
			act.dragAndDrop(From, To).build().perform();
			// System.out.print("From:" + From + "To:" + To);
			test_steps.add("Change Reservation Date");
			Utility.app_logs.info("Change Reservation Date");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.OverBookingPopup, driver);
			try {
				assertTrue(TapeChart.OverBookingPopup.isDisplayed(), "Failed: OverBooking Popup not appear");
			} catch (Exception e) {
				Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
				Wait.wait2Second();
				act = new Actions(driver);
				// Dragged and dropped
				act.dragAndDrop(From, To).build().perform();
				// System.out.print("From:" + From + "To:" + To);
				test_steps.add("Change Reservation Date");
				Utility.app_logs.info("Change Reservation Date");
				Wait.explicit_wait_visibilityof_webelement(TapeChart.OverBookingPopup, driver);
				assertTrue(TapeChart.OverBookingPopup.isDisplayed(), "Failed: OverBooking Popup not appear");
			} catch (Error e) {
				Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
				Wait.wait2Second();
				act = new Actions(driver);
				// Dragged and dropped
				act.dragAndDrop(From, To).build().perform();
				// System.out.print("From:" + From + "To:" + To);
				test_steps.add("Change Reservation Date");
				Utility.app_logs.info("Change Reservation Date");
				Wait.explicit_wait_visibilityof_webelement(TapeChart.OverBookingPopup, driver);
				assertTrue(TapeChart.OverBookingPopup.isDisplayed(), "Failed: OverBooking Popup not appear");
			}
			test_steps.add("Over Booking popup Appear");
			Utility.app_logs.info("Over Booking popup Appear");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.OverBookingPopup_Continue, driver);
			Utility.ScrollToElement(TapeChart.OverBookingPopup_Continue, driver);
			TapeChart.OverBookingPopup_Continue.click();
			test_steps.add("Click on Continue Button of Over Booking popup ");
			Utility.app_logs.info("Click Continue Over Booking popup ");
			Wait.waitUntilPresenceOfElementLocated(OR.ReservationUpdate_Popup, driver);
			assertTrue(TapeChart.ReservationUpdate_Popup.isDisplayed(), "Reservation Update popup is not open");

			test_steps.add("Reservation Update Popup Appears");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChanges_Button, driver);
			Utility.ScrollToElement(TapeChart.ConfirmChanges_Button, driver);
			TapeChart.ConfirmChanges_Button.click();
			test_steps.add("Click Confirm Changes Reservation Update");
			try {
				Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChanges_Button, 30);
			} catch (Exception e) {
				Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChanges_Button, driver);
				Utility.ScrollToElement(TapeChart.ConfirmChanges_Button, driver);
				TapeChart.ConfirmChanges_Button.click();
				test_steps.add("Click Confirm Changes Reservation Update");
				Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChanges_Button, 30);
			}
			test_steps.add("Reservation Update Popup Disappears");
			Thread.sleep(3000);
		}
		return test_steps;
	}

	public ArrayList<String> MoveReservationSameClass_WithoutConfirm_NoOverbook(WebDriver driver, String RoomNumber,
																				String RoomClass, ArrayList<String> test_steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[last()]";
		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		Utility.app_logs.info(path);
		String NextRoom = RoomNumber;
		tapechartLogger.info("RoomClass:" + RoomClass);
		String ToPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//following-sibling::div//div[@class='tapechartdatecell Available RackRate']";
		Utility.app_logs.info(ToPath);
		List<WebElement> AvailableRooms = driver.findElements(By.xpath(ToPath));
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		if (AvailableRooms.size() == 0) {
			assertTrue(false, "Failed No Room Available for the selected criteria");
		} else {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			// jse.executeScript("arguments[0].scrollIntoView(true);",
			// driver.findElement(By.xpath(path)));

			// jse.executeScript("window.scrollBy(0,-300)");

			tapechartLogger.info("Scrolled to the reservation");
			WebElement From = driver.findElement(By.xpath(path));
			Point Location = From.getLocation();

			Wait.explicit_wait_xpath(ToPath, driver);
			WebElement To = driver.findElement(By.xpath(ToPath));

			// Using Action class for drag and drop.
			Actions act = new Actions(driver);
			// Dragged and dropped
			act.dragAndDrop(From, To).build().perform();
			// System.out.print("From:" + From + "To:" + To);
			test_steps.add("Change Reservation Date");
			Utility.app_logs.info("Change Reservation Date");
			try {
				Wait.explicit_wait_visibilityof_webelement(TapeChart.OverBookingPopup, driver);
			} catch (Exception e) {
				test_steps.add("No OverBooking popup Appeared");
				Utility.app_logs.info("No OverBooking popup Appeared");
			}
			Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChangesButton, driver);
		}
		return test_steps;
	}

	public void MoveReservation1(WebDriver driver, String RoomNumber, String RoomClass, String ReservationNo)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])";
		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		String NextRoom = Integer.toString(nextRoom);
		// String ToPath = "//div[@title='" + NextRoom
		// + "']//following-sibling::div//div[@class='tapechartdatecell
		// Available RackRate']";
		String ToPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title>'"
				+ RoomNumber
				+ "']//following-sibling::div//div[@class='DatesContainer']/../div//div[text()='Available']";

		try {
			tapechartLogger.info(ToPath);
			tapechartLogger.info(driver.findElement(By.xpath(ToPath)).isDisplayed());
		} catch (Exception e) {
			for (int i = 0; i <= 10; i++) {
				ToPath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom + "']//following-sibling::div//div[@class='DatesContainer']";
				String Reservationpath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom
						+ "']//following-sibling::div//div[@class='DatesContainer']//preceding-sibling::div//child::div[@class='guest_display_name_short']";

				tapechartLogger.info("To pTah:" + ToPath + "ReserPath:" + Reservationpath);
				String ReservationName = driver.findElement(By.xpath(Reservationpath)).getText();
				tapechartLogger.info(ReservationName);
				if (ReservationName.contains("Available")) {
					break;
				} else {
					nextRoom = Integer.parseInt(NextRoom) + 1;
					NextRoom = Integer.toString(nextRoom);
				}
			}
		}

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(path)));

		int sizePath = driver.findElements(By.xpath(path)).size();
		if (sizePath > 1) {
			for (int i = 1; i <= sizePath; i++) {
				WebElement ele = driver.findElement(By.xpath(path + "[" + i + "]"));
				jse.executeScript("arguments[0].scrollIntoView(true);", ele);
				jse.executeScript("arguments[0].click();", ele);
				Wait.explicit_wait_xpath(OR.Get_Confirmation_Number, driver);
				boolean flag = driver.findElement(By.xpath(OR.Get_Confirmation_Number)).getText().equals(ReservationNo);
				if (flag) {
					path = path + "[" + i + "]";
					tapechartLogger.info("New Path : " + path);
					Elements_Reservation ReservationPage = new Elements_Reservation(driver);
					Utility.ScrollToElement(ReservationPage.closeReservation, driver);
					Wait.wait1Second();
					ReservationPage.closeReservation.click();
					Wait.wait2Second();
					if (ReservationPage.AlertForTab.isDisplayed()) {

						ReservationPage.AlertForTab_Yes_Btn.click();
					}
					Wait.wait1Second();
					break;
				}
			}
		}

		jse.executeScript("window.scrollBy(0,-300)");
		tapechartLogger.info("Scrolled to the reservation");
		WebElement From = driver.findElement(By.xpath(path));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		Wait.wait2Second();
		if (TapeChart.ConfirmChangesButton.isDisplayed()) {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChangesButton, driver);
			TapeChart.ConfirmChangesButton.click();

			try {
				Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 90);
			} catch (Exception e) {
				Utility.app_logs.info("Again Click Confirm changes");
				Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChangesButton, driver);
				TapeChart.ConfirmChangesButton.click();
				Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 90);
			}
		}
		// verify toaster message here
		try {
			// Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message,
			// driver);
			// assertEquals(TapeChart.Toaster_Message.getText(), "Reservation(s)
			// moved Successfully","Reservation does not Move");

			String msg = TapeChart.Toaster_Message.getText();
			tapechartLogger.info(msg);
		} catch (Exception e) {
			System.err.println("Toaster not Displayed");
		}

	}

	public void VerifyGrpResevation_nohandles(WebDriver driver, String RoomNumber) throws InterruptedException {

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "//div[@title='" + RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";

		int size = driver.findElements(By.xpath(path)).size();
		if (size > 1) {
			List<WebElement> hoverElement = driver.findElements(By.xpath(path));
			int before_hover_width = hoverElement.get(size - 1).getSize().getWidth();

			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement.get(size - 1)).perform();
			int after_hover_width = hoverElement.get(size - 1).getSize().getWidth();
			assertEquals(after_hover_width, before_hover_width, "handles appear on hover");

		} else {

			WebElement hoverElement = driver.findElement(By.xpath(path));
			int before_hover_width = hoverElement.getSize().getWidth();

			Actions builder = new Actions(driver);
			builder.moveToElement(hoverElement).perform();
			int after_hover_width = hoverElement.getSize().getWidth();
			assertEquals(after_hover_width, before_hover_width, "handles t appear on hover");

		}

	}

	public void VerifyOutStatusRoomClass(WebDriver driver) {

		String path = "//div[@title='501']//following-sibling::div//span/div/div/div/div/div[text()='Out']";
		WebElement find_room = driver.findElement(By.xpath(path));
		Wait.explicit_wait_visibilityof_webelement(find_room, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", find_room);
		Wait.explicit_wait_visibilityof_webelement(find_room, driver);
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.explicit_wait_visibilityof_webelement(find_room, driver);
		assertEquals(find_room.getText(), "Out", "Out room does not find in tapechart");

	}

	public void ClickUnassignedButton(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitForElementByXpath(driver, OR.Unassigned_Button);
		Utility.ScrollToElement(TapeChart.Unassigned_Button, driver);
		TapeChart.Unassigned_Button.click();
		assertTrue(TapeChart.UnassignedList.isDisplayed(), "Failed:List not show");
	}

	public void MoveUnassignedReservation(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		int temp = 1;
		Wait.wait2Second();
		String UnassignedRoom = "(//div[@class='ir-tc-parking-lot-card clearfix Confirmed']//following-sibling::div[@class='panel panel-danger'])[last()]";
		Random rand = new Random();
		int low = 357;
		int high = 450;
		int result = rand.nextInt(high - low) + low;
		int nextRoom = (result) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[@title='" + NextRoom
				+ "']//following-sibling::div//div[@class='tapechartdatecell noResAvail'][" + (temp + 1) + "]";
		Utility.app_logs.info(ToPath);
		String ReservationName = driver.findElement(By.xpath(ToPath)).getText();
		if (RoomClass.equals("Double Bed Room"))
			RoomClass = "DBR";
		for (int i = 0; i < 10; i++) {
			if (ReservationName.contains("Available")) {
				tapechartLogger.info("if");
				break;
			} else {

				nextRoom = Integer.parseInt(NextRoom) + 1;
				NextRoom = Integer.toString(nextRoom);
			}
		}

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(UnassignedRoom)));
		tapechartLogger.info("Path here:");

		jse.executeScript("window.scrollBy(0,-300)");
		WebElement From = driver.findElement(By.xpath(UnassignedRoom));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		// Utility.ElementFinderUntillNotShow(By.xpath(OR.ConfirmChangesButton),
		// driver);
		// Wait.waitUntilPresenceOfElementLocated(OR.ConfirmChangesButton);
		Wait.explicit_wait_visibilityof_webelement_120(TapeChart.ConfirmChangesButton, driver);
		TapeChart.ConfirmChangesButton.click();
		Utility.app_logs.info("Click Confirm Changes Button");
		try {
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 30);
		} catch (Exception e) {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.ConfirmChangesButton, driver);
			TapeChart.ConfirmChangesButton.click();
			Utility.app_logs.info("Again Click Confirm Changes Button");
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 30);
		}
		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not Move");

	}

	public void MoveReserFromAssignToUnassign(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		int temp = 1;
		Wait.wait2Second();
		String UnassignedRoom = "(//div[@class='panel panel-danger'])[1]";
		Random rand = new Random();
		int low = 357;
		int high = 450;
		int result = rand.nextInt(high - low) + low;
		int nextRoom = (result) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[@title='" + NextRoom
				+ "']//following-sibling::div//div[@class='tapechartdatecell noResAvail'][" + (temp + 1) + "]";

		String ReservationName = driver.findElement(By.xpath(ToPath)).getText();
		if (RoomClass.equals("Double Bed Room"))
			RoomClass = "DBR";
		for (int i = 0; i < 10; i++) {
			if (ReservationName.contains("Available")) {
				tapechartLogger.info("if");
				break;
			} else {

				nextRoom = Integer.parseInt(NextRoom) + 1;
				NextRoom = Integer.toString(nextRoom);
			}
		}

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(UnassignedRoom)));
		tapechartLogger.info("Path here:");

		jse.executeScript("window.scrollBy(0,-300)");
		WebElement From = driver.findElement(By.xpath(UnassignedRoom));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		Utility.ElementFinderUntillNotShow(By.xpath(OR.ConfirmChangesButton), driver);
		Wait.waitUntilPresenceOfElementLocated(OR.ConfirmChangesButton, driver);
		TapeChart.ConfirmChangesButton.click();

		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not Move");

	}

	public void Verify_RoomClass_NegetiveCase(WebDriver driver, String RoomClass) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String RoomName = RoomClass.split(" : ")[0];
		String RoomNumber = RoomClass.split(" : ")[1];
		if (RoomName.equals("Double Bed Room"))
			RoomName = "DBR";
		Wait.wait2Second();
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomName + "']/../../div/div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])";
		if (driver.findElements(By.xpath(path)).size() < 3) {
			path = "(//div[@class='row ratesrow']//div[text()='" + RoomName + "']/../../div/div[@title='" + RoomNumber
					+ "']//following-sibling::div//div[@class='DatesContainer'])[1]";
		} else {
			path = "(//div[@class='row ratesrow']//div[text()='" + RoomName + "']/../../div/div[@title='" + RoomNumber
					+ "']//following-sibling::div//div[@class='DatesContainer'])[2]";
		}

		WebElement ele = driver.findElement(By.xpath(path));
		jse.executeScript("arguments[0].scrollIntoView(true);", ele);

		WebElement parent = ele.findElement(By.xpath(".."));
		tapechartLogger.info(parent.getText());
		assertTrue(!parent.getText().equals("Out"), "Failed: RoomMain_Item Found in Current Date");
	}

	public String getNextDate(int Day) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		final Date date = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, Day);
		int a = Integer.parseInt(format.format(calendar.getTime()));
		if (a < 10) {
			format = new SimpleDateFormat("d");
		}
		return format.format(calendar.getTime());
	}

	public String getNextDate(int Day, String formateStr) {
		SimpleDateFormat format = new SimpleDateFormat(formateStr);
		final Date date = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, Day);
		return format.format(calendar.getTime());
	}

	public ArrayList<String> searchWithGivenEndDate(WebDriver driver, int Day) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		// driver.navigate().refresh();
		// Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date,
		// driver);
		// TapeChart.Select_Arrival_Date.click();
		// TapeChart.Click_Today.click();
		// Wait.wait2Second();
		Wait.waitUntilPresenceOfElementLocated(OR.Select_DepartDate, driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Select_DepartDate, driver);
		Utility.ScrollToElement(TapeChart.Select_DepartDate, driver);
		TapeChart.Select_DepartDate.click();
		Wait.wait2Second();
		if (Integer.parseInt(getNextDate(0)) > Integer.parseInt(getNextDate(Day))) {
			driver.findElement(By.xpath("//td[@class='new day'][text()='" + getNextDate(Day) + "']")).click();
		} else {

			driver.findElement(By.xpath("//td[@class='day'][text()='" + getNextDate(Day) + "']")).click();
		}
		tapechartLogger.info("Depart Date Changed in TapChart " + Day + " Days Added");
		test_steps.add("Depart Date Changed in TapChart " + Day + " Days Added");

		Wait.wait2Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		tapechartLogger.info("Search Button Clicked");
		test_steps.add("Search Button Clicked");
		return test_steps;
	}

	public ArrayList<String> searchWithRackRate(WebDriver driver, String TapeChartAdults, String RackRate)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);

		Wait.explicit_wait_xpath(OR.Click_Tapechart_RackRate, driver);
		TapeChart.Click_Tapechart_RackRate.click();
		Wait.explicit_wait_xpath("//div[@id='tapeChartSearch']//ul[@role='menu']/li/a/span[.='" + RackRate + "']",
				driver);
		driver.findElement(By.xpath("//div[@id='tapeChartSearch']//ul[@role='menu']/li/a/span[.='" + RackRate + "']"))
				.click();

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		tapechartLogger.info("Search Button Clicked");
		test_steps.add("Search Button Clicked");
		return test_steps;
	}

	public ArrayList<String> searchWithGivenStartDate(WebDriver driver, int Day, String TapeChartAdults)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		driver.findElement(By.xpath("//td[@class='day'][text()='" + getNextDate(Day) + "']")).click();

		tapechartLogger.info("Start Date Changed in TapChart " + Day + " Days Added");
		test_steps.add("Start Date Changed in TapChart " + Day + " Days Added");
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		tapechartLogger.info("Search Button Clicked");
		test_steps.add("Search Button Clicked");
		return test_steps;
	}

	public void VerifyEditRateValue_TapeChart(WebDriver driver, String RateValue, String RoomClassAbb)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String RoomClassRatePath = "(//div[text()='" + RoomClassAbb
				+ "' and contains(@class,'roomclassname')]//..//div//div)[2]";
		tapechartLogger.info(RoomClassRatePath);

		List<WebElement> RoomRate = driver.findElements(By.xpath(RoomClassRatePath));
		Wait.explicit_wait_visibilityof_webelement(RoomRate.get(0), driver);
		Utility.ScrollToElement(RoomRate.get(0), driver);
		String NewSetRate = RoomRate.get(0).getText();
		tapechartLogger.info("RateValuee:" + NewSetRate + "Added rate Valye:" + RateValue);
		assertTrue(NewSetRate.equals("$" + RateValue), "Edit Rate Value didn't updated");
	}

	public void VerifyEditRateValue_TapeChartForAllPersons(WebDriver driver, ArrayList<Integer> RateValue,
														   String RoomClassAbb, int PersonsMaxNumber, int AdultMaxNumber, int ChildrenMaxNumber, String RatepromoCode)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		// Adults Value Check

		String MaxAdults;
		String MaxChildren;
		String NewSetRate = "";
		int rate = 0;
		String NewRateValue;

		if (AdultMaxNumber > 0 && ChildrenMaxNumber == 0) {

			for (int i = 0; i < AdultMaxNumber; i++) {
				MaxAdults = Integer.toString(i + 1);
				MaxChildren = Integer.toString(ChildrenMaxNumber);
				TapeChartSearch_Test(driver, MaxAdults, MaxChildren, RatepromoCode);
				String RoomClassRatePath = "(//div[text()='" + RoomClassAbb
						+ "' and contains(@class,'roomclassname')]//..//div//div)[2]";
				// List<WebElement> RoomRate =
				// driver.findElements(By.xpath(RoomClassRatePath));
				WebElement RoomRate = driver.findElement(By.xpath(RoomClassRatePath));

				if (i < 4) {
					tapechartLogger.info("Index:" + i);

					Wait.explicit_wait_visibilityof_webelement(RoomRate, driver);
					Utility.ScrollToElement(RoomRate, driver);
					NewSetRate = RoomRate.getText();
					tapechartLogger.info("Excpected:" + NewSetRate + "Calculated:" + RateValue.get(i));

					assertTrue(NewSetRate.equals("$" + RateValue.get(i)), "Edit Rate Value didn't updated");
				} else {
					// int rate = 0;
					// String NewRateValue;
					NewSetRate = RoomRate.getText();
					rate = RateValue.get(3) + ((RateValue.get(4)) * (i - 3));
					NewRateValue = Integer.toString(rate);
					// tapechartLogger.info("Excpected:" + NewSetRate +
					// "Calculated:" + NewRateValue);

					assertTrue(NewSetRate.equals("$" + NewRateValue), "Edit Rate Value didn't updated");

				}
			}
		}

		// Children Value check
		else if (AdultMaxNumber == 0 && ChildrenMaxNumber > 0) {
			tapechartLogger.info(" children:" + ChildrenMaxNumber);

			for (int i = 5; i <= ChildrenMaxNumber; i++) {
				MaxAdults = Integer.toString(AdultMaxNumber);
				MaxChildren = Integer.toString(i);
				TapeChartSearch_Test(driver, MaxAdults, MaxChildren, RatepromoCode);
				String RoomClassRatePath = "(//div[text()='" + RoomClassAbb
						+ "' and contains(@class,'roomclassname')]//..//div//div)[2]";
				WebElement RoomRate = driver.findElement(By.xpath(RoomClassRatePath));
				NewSetRate = RoomRate.getText();

				if (i < 9) {
					rate = RateValue.get(i) + RateValue.get(3);
					NewRateValue = Integer.toString(rate);
					// tapechartLogger.info("Excpected:" + NewSetRate +
					// "Calculated:" + NewRateValue);

					assertTrue(NewSetRate.equals("$" + NewRateValue), "Edit Rat Value didn't updated");
				} else {

					rate = RateValue.get(3) + RateValue.get(RateValue.size() - 2) + ((RateValue.get(9)) * (i - 8));
					NewRateValue = Integer.toString(rate);
					// tapechartLogger.info("1st:" + RateValue.get(3) + "2nd" +
					// RateValue.get(RateValue.size() - 2) + "3rd:"
					// + ((RateValue.get(9)) * (i - 8)));
					// tapechartLogger.info("Excpected:" + NewSetRate +
					// "Calculated:" + NewRateValue);

					assertTrue(NewSetRate.equals("$" + NewRateValue), "Edit Rat Value didn't updated");

				}
			}

		}

	}

	public void TapeChartSearch_Test(WebDriver driver, String TapeChartAdults, String TapeChartChildrens,
									 String PromoCode) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.Select_Arrival_Date);
		jse.executeScript("arguments[0].click();", TapeChart.Click_Today);
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		TapeChart.Enter_Children_Tapechart.sendKeys(TapeChartChildrens);
		TapeChart.Enter_promoCode_Tapechart.clear();
		TapeChart.Enter_promoCode_Tapechart.sendKeys(PromoCode);
		TapeChart.Click_Search_TapeChart.click();
		Wait.wait2Second();

	}

	public ArrayList<String> searchWithGivenStartDate(WebDriver driver, int Day) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		// driver.navigate().refresh();
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		// TapeChart.Click_Today.click();
		Wait.wait2Second();
		// Wait.waitUntilPresenceOfElementLocated(OR.Select_DepartDate, driver);
		// Wait.explicit_wait_visibilityof_webelement(TapeChart.Select_DepartDate,
		// driver);
		// Utility.ScrollToElement(TapeChart.Select_DepartDate, driver);
		// TapeChart.Select_DepartDate.click();
		// Wait.wait2Second();
		driver.findElement(By.xpath("//td[@class='day'][text()='" + getNextDate(Day) + "']")).click();

		tapechartLogger.info("Arival Date Changed in TapChart " + Day + " Days Added");
		test_steps.add("Arival Date Changed in TapChart " + Day + " Days Added");
		Wait.wait2Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		tapechartLogger.info("Search Button Clicked");
		test_steps.add("Search Button Clicked");
		return test_steps;
	}

	public void verifyRoomSlotFree(WebDriver driver, String RoomClass, String RoomNumber) {
		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		WebElement ele = driver.findElement(By.xpath(path));
		WebElement parent = ele.findElement(By.xpath(".."));
		tapechartLogger.info(parent.getText());

		assertEquals(parent.getText(), "Available", "Failed: not moved");
		tapechartLogger.info("Found Available");
	}

	public String GetNumberofRoomsAvaialble(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.TapeChartDay1Button);
		String NumberofRoomsAvail = driver
				.findElement(By
						.xpath("//div[@class='fixedLegend']//div[contains(@data-bind,'getElement: tapeChartConsolidatedAvailabilityAndOccupancy')]//div[@class='ir-tc-absoluteCenter']"))
				.getText();
		return NumberofRoomsAvail;
	}

	public String GetOccupancy(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.TapeChartDay1Button);
		String Occupancy = driver
				.findElement(By
						.xpath("//div[@class='fixedLegend']//div[contains(@data-bind,'getElement: tapeChartConsolidatedAvailabilityAndOccupancy')]//div[@class='row occupercentrow']//div[@class='ir-tc-absoluteCenter']"))
				.getText();
		return Occupancy;
	}

	public ArrayList<String> VerifyRoomsAvailableOccupancy(WebDriver driver, String RoomsAvailableTapechart,
														   String OccupancyTapechart, ArrayList<String> test_steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.TapeChartDay1Button);
		String Occupancy = driver
				.findElement(By
						.xpath("//div[@class='fixedLegend']//div[contains(@data-bind,'getElement: tapeChartConsolidatedAvailabilityAndOccupancy')]//div[@class='row occupercentrow']//div[@class='ir-tc-absoluteCenter']"))
				.getText();
		String NumberofRoomsAvail = driver
				.findElement(By
						.xpath("//div[@class='fixedLegend']//div[contains(@data-bind,'getElement: tapeChartConsolidatedAvailabilityAndOccupancy')]//div[@class='ir-tc-absoluteCenter']"))
				.getText();
		tapechartLogger.info("Rooms New:" + NumberofRoomsAvail + "Before:" + RoomsAvailableTapechart);

		assertTrue(!NumberofRoomsAvail.equals(RoomsAvailableTapechart), "Room Available didn't increase by 1");
		test_steps.add("Rooms Available were : " + RoomsAvailableTapechart + " Changed to : " + NumberofRoomsAvail
				+ " increased By One in TapeChart");
		tapechartLogger.info("Occ New:" + Occupancy + "before:" + OccupancyTapechart);
		int RoomsAfter = Integer.parseInt(NumberofRoomsAvail);
		int RoomsBefore = Integer.parseInt(RoomsAvailableTapechart);

		if ((RoomsAfter - RoomsBefore) >= 100) {

			assertTrue(!Occupancy.equals(OccupancyTapechart), "Occupancy in % isn't reflecting the change");
			test_steps.add("Occupancy were : " + OccupancyTapechart + " Changed to : " + Occupancy
					+ " Increased By 1% in TapeChart");
		}
		return test_steps;

	}

	public void clickOneDay(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.click1Day);
		TapeChart.click1Day.click();

	}

	public void getUnassignedTapechartResCount(WebDriver driver, ArrayList test_steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait3Second();
		// Wait.WaitForElement(driver, OR.getUnsassignedResCountTapechart);
		String unassignedResCountTP = TapeChart.getUnsassignedResCountTapechart.getText();
		String unassignedTapechart1 = unassignedResCountTP.replaceAll("[^a-zA-Z0-9]", "");
		// unassignedResCountTapechart=tapechartUnassigned1.replaceAll("[]",
		// "");
		unassignedResCountTapechart = unassignedTapechart1.replaceAll("UNASSIGNED", "");
		tapechartLogger.info("Unassigned Reservation from TapechartCount before creating the reservation: "
				+ unassignedResCountTapechart);
		test_steps.add("Unassigned Reservation from TapechartCount before creating the reservation: "
				+ unassignedResCountTapechart);
	}

	public void getUnassignedResCountTapechartAfterCreatingRes(WebDriver driver, ArrayList test_steps)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait10Second();
		Wait.WaitForElement(driver, OR.getUnsassignedResCountTapechart);
		String unassignedResCountTP1 = TapeChart.getUnsassignedResCountTapechart.getText();
		String unassignedTapechart2 = unassignedResCountTP1.replaceAll("[^a-zA-Z0-9]", "");
		unassignedResCountTapechartAfterCreatingRes = unassignedTapechart2.replaceAll("UNASSIGNED", "");
		tapechartLogger.info("Unassigned Reservation from TapechartCount after creating the reservation: "
				+ unassignedResCountTapechartAfterCreatingRes);
		test_steps.add("Unassigned Reservation from TapechartCount after creating the reservation: "
				+ unassignedResCountTapechartAfterCreatingRes);
		Wait.wait5Second();

	}

	public void getUnassignedResGuestNameTapechart(WebDriver driver, String name) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		TapeChart.getUnsassignedResCountTapechart.click();

		Wait.WaitForElement(driver, OR.getUnsassignedResTapechart);

		List<WebElement> unassignedReservations = TapeChart.getUnsassignedResTapechart;

		Iterator<WebElement> i = unassignedReservations.iterator();

		while (i.hasNext()) {

			row = i.next().getText();
			if (row.equalsIgnoreCase(name)) {
				tapechartLogger.info("***********unassigned guest name****" + row);
				unassignedGuestNameTapechart = row;
			}
		}
	}

	public void tapechartUnassignedReservationCountValidations(WebDriver driver, ArrayList test_steps) {

		if (Integer.parseInt(unassignedResCountTapechartAfterCreatingRes) > Integer
				.parseInt(unassignedResCountTapechart)) {
			tapechartLogger.info("Validated Unassigned Reservation Count in Tapechart");
			test_steps.add("Validated Unassigned Reservation Count in Tapechart");
		} else {
			Tapechart.tapechartLogger.info("Unassigened reservation count is not increased in tape chart");
			test_steps.add("Unassigened reservation count is not increased in tape chart");
		}
	}

	public void tapechartUnassignedReservationGuestNameValidation(WebDriver driver, String name, ArrayList test_steps)
			throws InterruptedException {
		Assert.assertEquals(name, unassignedGuestNameTapechart);
		tapechartLogger.info("Validated Tapechart Unassigned Reservation ");
		test_steps.add("Validated Tapechart Unassigned Reservation");
		tapechartLogger.info("System successfully validated Room class for unassigned Reservation");
	}

	public void click_UnassigenedReservationCount(WebDriver driver, ArrayList test_steps, String res)
			throws InterruptedException {

		int count = driver.findElements(By.xpath("//div[contains(text(),'Rooms')]/../following-sibling::div")).size();
		String text = "dateheadertoday";
		String temp;
		int value = 0;
		for (int i = 1; i <= count; i++) {
			temp = driver
					.findElement(By.xpath("//div[contains(text(),'Rooms')]/../following-sibling::div/div[" + i + "]"))
					.getAttribute("class");
			if (temp.contains(text)) {
				value = i;
				break;
			}
		}
		String unassigned = "//div[contains(text(),'Unassigned')]/following-sibling::div/div[" + value + "]";
		Wait.wait3Second();
		int unassignedCount = Integer.parseInt(driver.findElement(By.xpath(unassigned)).getText());
		test_steps.add("numer of unassigned for today are : " + unassignedCount);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(unassigned)));

		jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(unassigned)));

		/*
		 * Wait.wait3Second(); Wait.WaitForElement(driver, unassigned);
		 * driver.findElement(By.xpath(unassigned)).click();
		 */
		test_steps.add("clicking on unassigned count");
		Wait.wait3Second();
		Wait.WaitForElement(driver, "//span[@data-bind='text: ConfirmationNumber']");
		int tempcount = driver.findElements(By.xpath("//span[@data-bind='text: ConfirmationNumber']")).size();
		if (unassignedCount == tempcount) {
			test_steps.add("number of unassigned for today are and unassigned search are equal " + unassignedCount);
		} else {
			test_steps.add("number of unassigned for today are and unassigned search are not equal " + unassignedCount);
		}

		String verifyRes = "//table/tbody/tr/td/span[contains(text(),'" + res.trim() + "')]";
		if (driver.findElements(By.xpath(verifyRes)).size() > 0) {
			test_steps.add("Reservation is found in unassigned search : " + res);
		} else {
			test_steps.add("Reservation is not found in unassigned search : " + res);
		}
	}

	public void validate_TapeChartUnassigned(ArrayList test_steps, int before, int after) {
		if (before == (after - 1)) {
			test_steps.add("before count is less one than after count");
		} else {
			test_steps.add("before count is not less one than after count");
		}
	}

	public boolean verify_Unassigned(WebDriver driver, ArrayList test_steps) throws Exception {
		String unassigned = "//div[contains(text(),' Unassigned')]";
		Wait.wait5Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(unassigned)));
		jse.executeScript("window.scrollBy(0,-400)", "");

		boolean flag = true;
		if (driver.findElements(By.xpath(unassigned)).size() > 0) {

		} else {
			flag = false;
		}
		return flag;
	}

	public ArrayList<String> TapeChartSearch_Checkin(WebDriver driver, String CheckinDate, String Adults,
													 String Children, String RatePlan, ArrayList<String> steps) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.TapeChart_CheckIn, driver);
		TapeChart.TapeChart_CheckIn.sendKeys(CheckinDate);
		assertTrue(TapeChart.TapeChart_CheckIn.getAttribute("value").contains(CheckinDate), "Failed: CheckIn");
		steps.add("Enter Checking Date  : " + CheckinDate);
		tapechartLogger.info("Enter Checking Date  : " + CheckinDate);
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(Adults);
		assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains(Adults), "Failed: Adult set");
		steps.add("Enter Adults  : " + Adults);
		tapechartLogger.info("Enter Adults  : " + Adults);
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(Children);
		steps.add("Enter Children : " + Children);
		tapechartLogger.info("Enter Children : " + Children);
		Wait.explicit_wait_xpath(OR.Click_Tapechart_RackRate, driver);
		TapeChart.Click_Tapechart_RackRate.click();
		String RatePlan_Path = "//div[@id='tapeChartSearch']//ul[@role='menu']//child::span[contains(text(),'"
				+ RatePlan + "')]";
		Wait.explicit_wait_xpath(RatePlan_Path, driver);
		WebElement ratePlan = driver.findElement(By.xpath(RatePlan_Path));
		ratePlan.click();
		steps.add("Selected Rate Plan : " + RatePlan);
		tapechartLogger.info("Selected Rate Plan : " + RatePlan);
		TapeChart.Click_Search_TapeChart.click();
		steps.add("Click Tape Chart search");
		tapechartLogger.info("Click Tape Chart search");
		try {
			Wait.WaitForElement(driver, OR.TapeChartAvailableSlot);
		} catch (Exception e) {
			TapeChart.Click_Search_TapeChart.click();
			tapechartLogger.info("Again Click Search");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot, driver);
		}
		return steps;
	}

	public String GetUnAssignedCount_Today(WebDriver driver, String RoomClass) throws InterruptedException {

		String CellPath = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//child::div[@class = 'row unassignedrow']//child::div[@class='ir-tc-absoluteCenter'])[2]";
		Wait.WaitForElement(driver, CellPath);
		WebElement Cell = driver.findElement(By.xpath(CellPath));
		Utility.ScrollToElement(Cell, driver);
		String count = Cell.getText();
		return count;
	}

	public String GetAvailableRoomsCount_Today(WebDriver driver, String RoomClass) throws InterruptedException {

		String CellPath = "(//div[contains(text(),'" + RoomClass
				+ "') and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//following-sibling::div[@class = 'row availpercentrow']//child::div[@class='ir-tc-absoluteCenter'])[2]";
		Wait.WaitForElement(driver, CellPath);
		WebElement Cell = driver.findElement(By.xpath(CellPath));
		Utility.ScrollToElement(Cell, driver);
		String count = Cell.getText();
		return count;
	}

	public String GetPercentOccupancy_Today(WebDriver driver, String RoomClass) throws InterruptedException {

		String CellPath = "(//div[contains(text(),'" + RoomClass
				+ "') and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//following-sibling::div[@class = 'row occupercentrow']//child::div[@class='ir-tc-absoluteCenter'])[2]";
		Wait.WaitForElement(driver, CellPath);
		WebElement Cell = driver.findElement(By.xpath(CellPath));
		Utility.ScrollToElement(Cell, driver);
		String count = Cell.getText();
		return count;
	}

	public String GetPropertyLevelAvailableRoomsCount_Today(WebDriver driver, String roomClassAbbreviation)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Utility.ScrollToElement(TapeChart.Propertylevel_RoomsAvailable_Today, driver);
		String count = TapeChart.Propertylevel_RoomsAvailable_Today.getText();
		return count;
	}

	public String GetPropertyLevelPercentOccupancy_Today(WebDriver driver, String roomClassAbbreviation)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Utility.ScrollToElement(TapeChart.Propertylevel_PercentOccupancy_Today, driver);
		String count = TapeChart.Propertylevel_PercentOccupancy_Today.getText();
		return count;
	}

	public ArrayList<String> MoveLockReservation(WebDriver driver, String RoomNumber, String RoomClass,
												 ArrayList<String> test_steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		if (RoomClass.equals("Double Bed Room")) {

			RoomClass = "DBR";
			tapechartLogger.info(RoomClass);
		}

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = " ";
		try {
			path = "//div[contains(text(),'" + RoomClass

					+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
					+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		} catch (Exception e) {
			path = "(//div[contains(text(),'" + RoomClass

					+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
					+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[2]";
		}
		String ResWithLockimgString = "//div[text()='" + RoomClass
				+ "']//ancestor::div[@class='roomRatesChart']//div[@class='row roomsrow']//div//span[text()='"
				+ RoomNumber
				+ "']//..//following-sibling::div//span[@class='ir-tce-mainResCell']//div//div//div//div//div[contains(@class,'reservation-lock-white')]";
		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='" + NextRoom
				+ "']//following-sibling::div//div[@class='tapechartdatecell Available RackRate']";
		try {
			tapechartLogger.info(driver.findElement(By.xpath(ToPath)).isDisplayed());
		} catch (Exception e) {
			for (int i = 0; i <= 10; i++) {
				ToPath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom + "']//following-sibling::div//div[@class='DatesContainer']";
				String Reservationpath = "//div[contains(text(),'" + RoomClass
						+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
						+ NextRoom
						+ "']//following-sibling::div//div[@class='DatesContainer']//preceding-sibling::div//child::div[@class='guest_display_name_short']";

				String ReservationName = driver.findElement(By.xpath(Reservationpath)).getText();
				if (ReservationName.contains("Available")) {
					break;
				} else {
					nextRoom = Integer.parseInt(NextRoom) + 1;
					NextRoom = Integer.toString(nextRoom);
				}
			}
		}
		// JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("arguments[0].scrollIntoView(true);",
		// driver.findElement(By.xpath(path)));
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);

		WebElement ResWithLockImg = driver.findElement(By.xpath(ResWithLockimgString));
		assertTrue(ResWithLockImg.isDisplayed(), "Lock Img didn't display");

		test_steps.add(" Lock Image RoomClass " + RoomClass + " RoomNumber " + RoomNumber + "is Displayed");
		WebElement From = driver.findElement(By.xpath(path));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		Wait.wait5Second();
		assertTrue(!TapeChart.ConfirmChangesButton.isDisplayed(), "Reservation has been moved ");
		test_steps.add(" Couldn't move lock reservation");
		return test_steps;

	}

	public void getUnassignedResGuestNameTapechart(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		TapeChart.getUnsassignedResCountTapechart.click();

		Wait.WaitForElement(driver, OR.getUnsassignedResTapechart);

		List<WebElement> unassignedReservations = TapeChart.getUnsassignedResTapechart;

		Iterator<WebElement> i = unassignedReservations.iterator();

		while (i.hasNext()) {

			row = i.next().getText();

			if (row.equalsIgnoreCase(Reservation.nameGuest)) {

				tapechartLogger.info("***********unassigned guest name****" + row);

				unassignedGuestNameTapechart = row;

			}

		}

	}

	public void VerifyNewRule_TapeChart(WebDriver driver, ArrayList<String> test_steps, String RoomClassAbb,
										String RuleName, boolean ruleApplicable) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Elements_Reservation ReservationPage = new Elements_Reservation(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[contains(text(),'" + RoomClassAbb
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		// String
		// CellPath="//div[contains(text(),'\"+RoomClassAbb+\"')]//following-sibling::div/div";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		test_steps.add("Clicked on the CellPath Related to " + RoomClassAbb + " RoomClass");
		Wait.explicit_wait_visibilityof_webelement(AvailableSlot, driver);
		jse.executeScript("arguments[0].scrollIntoView(true);", AvailableSlot);
		try {
			Wait.explicit_wait_visibilityof_webelement(AvailableSlot, driver);
			AvailableSlot.click();
			test_steps.add("AvailableSlot is clickable");
			tapechartLogger.info("AvailableSlot is clickable");

		} catch (Exception e) {
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.explicit_wait_visibilityof_webelement(AvailableSlot, driver);
			AvailableSlot.click();
			test_steps.add("AvailableSlot is clickable in Scroll back 300");
			tapechartLogger.info("AvailableSlot is clickable in Scroll back 300");
		}
		if (ruleApplicable == true) {
			try {
				Wait.wait2Second();
				if (TapeChart.Rules_Broken_popup.isDisplayed()) {
					Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
					tapechartLogger.info("Rules Broken PopUP is  DisPlayed in TapeChart");
					test_steps.add("Rules Broken PopUP is DisPlayed in TapeChart");
					tapechartLogger.info(TapeChart.Rules_Broken_popup.getText());
					assertEquals(TapeChart.Rules_Broken_popup.getText(), "Rules Broken",
							"Rule broken popup is not showing");
					test_steps.add("Verified Rules Broken Popup in TapeChart");

					String finalString = ReservationPage.RuleName_TapeChart.getText();
					assertEquals(finalString, RuleName);
					test_steps.add("NewRule is Available in TapeChart Search With RuleName:  " + finalString);
					tapechartLogger.info("NewRule is Available in TapeChart Search With RuleName:  " + finalString);
					TapeChart.Rule_Broken_Cancel.click();
					test_steps.add("Clicked on Cancel");
				}
			} catch (Exception e) {
				tapechartLogger.info("Steps Skipped");
			}
		} else {
			test_steps.add("Rules Broken PopUP is Not DisPlayed in TapeChart");
			tapechartLogger.info("Rules Broken PopUP is Not DisPlayed in TapeChart");
			tapechartLogger.info("No Rule is Available in TapeChart Search for " + RoomClassAbb
					+ " Class With RuleName:  " + RuleName);
			test_steps.add("No Rule is Available in TapeChart Search for " + RoomClassAbb + " Class With RuleName:  "
					+ RuleName);

			try {
				// modified code
				WebDriverWait wait = new WebDriverWait(driver, 90);
				Wait.WaitForElement(driver, OR.closeReservation);
				Wait.explicit_wait_visibilityof_webelement(TapeChart.closeReservation, driver);
				Wait.WaitForElement(driver, OR.ReservationSaveButton);
				wait.until(ExpectedConditions.elementToBeClickable(TapeChart.closeReservation));
				TapeChart.closeReservation.click();
			} catch (Exception e) {

			}
		}
	}

	public void TapeChartRatePlanSearch(WebDriver driver, ArrayList<String> test_steps, String TapeChartAdults,
										String RatePlan) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();
		test_steps.add("Selected Today Date from the Date Picker ");
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);

		test_steps.add("Enter Adults '" + TapeChartAdults + "'");
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Select_Ratepaln_DropDown, driver);
		TapeChart.Select_Ratepaln_DropDown.click();

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String RatePlanList = "//div[@id='tapeChartSearch']//ul[@role='menu']/li/a/span[.='" + RatePlan + "']";
		WebElement CurrentRatePlan = driver.findElement(By.xpath(RatePlanList));
		Wait.explicit_wait_visibilityof_webelement(CurrentRatePlan, driver);
		jse.executeScript("arguments[0].click();", CurrentRatePlan);
		test_steps.add("Selected Rateplan in TapeChart Search :" + RatePlan);
		tapechartLogger.info("Selected Rateplan in TapeChart Search :" + RatePlan);

		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		test_steps.add("Clicked on Search in TapeChart");

	}

	public void VerifyNewRate_TapeChart(WebDriver driver, ArrayList<String> test_steps, String RateValue,
										String RoomClassAbb) throws InterruptedException {

		test_steps.add("Checking for the Available RoomClass Rate");
		String CellPath = "//div[contains(text(),'" + RoomClassAbb + "')]//following-sibling::div/div";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement CurrentCellPath = driver.findElement(By.xpath(CellPath));
		Utility.ScrollToElement(CurrentCellPath, driver);
		String finalValue = Utility.convertDollarToNormalAmount(driver, CurrentCellPath.getText());
		tapechartLogger
				.info("New Rate is Available in TapeChart Search with BaseAmount of " + CurrentCellPath.getText());
		test_steps.add("New Rate is Available in TapeChart Search with BaseAmount of " + CurrentCellPath.getText());
		assertEquals(RateValue, finalValue);
	}

	public void Verify_CheckedIn_RoomsStatus(WebDriver driver, ArrayList<String> test_steps, List<String> Rooms,
											 ArrayList<String> RoomAbbri, String DirtyStatus, String CleanStatus) {
		for (int i = 0; i < Rooms.size(); i++) {
			String[] room = Rooms.get(i).split(":");
			String FinalRoom = room[1].trim();
			tapechartLogger.info(" Room No: " + FinalRoom);

			String[] abb = RoomAbbri.get(i).split(":");
			String FinalAbb = abb[1].trim();
			tapechartLogger.info(" Room Class: " + FinalAbb);

			String path = "//div[@class='roomRatesChart']/div[contains(@class,'row ratesrow')]/div[contains(@class,'roomclassname')][contains(text(),'"
					+ FinalAbb
					+ "')]/ancestor::div[contains(@class,'ratesrow')]//following-sibling::div//div[contains(@class,'roomnumber')][@title='"
					+ FinalRoom + "']/span/span/img[contains(@src,'" + CleanStatus.toLowerCase().trim()
					+ "')]|//div[@class='roomRatesChart']/div[contains(@class,'row ratesrow')]/div[contains(@class,'roomclassname')][contains(text(),'"
					+ FinalAbb
					+ "')]/ancestor::div[contains(@class,'ratesrow')]//following-sibling::div//div[contains(@class,'roomnumber')][@title='"
					+ FinalRoom + "']/span/span/img[contains(@src,'" + DirtyStatus.toLowerCase().trim() + "')]";
			Wait.WaitForElement(driver, path);
			WebElement element = driver.findElement(By.xpath(path));
			assertTrue(element.isEnabled(), "Failed:to Verify Room Status On Tape Chart");
			if ((element.getAttribute("src").toString().toLowerCase().trim()
					.contains(DirtyStatus.toLowerCase().trim()))) {

				test_steps.add("Verified Abb  <b>" + FinalAbb + "</b>" + " Room No: <b>" + FinalRoom
						+ " </b>Status: <b>" + DirtyStatus + " </b>");
				tapechartLogger.info("Room Class " + FinalAbb + " Room No: " + FinalRoom + "Status: " + DirtyStatus);

			} else if ((element.getAttribute("src").toString().toLowerCase().trim()
					.contains(CleanStatus.toLowerCase().trim()))) {
				test_steps.add("Verified Abb  <b>" + FinalAbb + "</b>" + " Room No: <b>" + FinalRoom
						+ " </b>Status: <b>" + CleanStatus + " </b>");
				tapechartLogger.info("Room Class " + FinalAbb + " Room No: " + FinalRoom + "Status: " + CleanStatus);

			}

		}
	}

	public void TapeChart_Search_MRB(WebDriver driver, String CheckinDate, String Adults, ArrayList<String> steps)
			throws InterruptedException, ParseException {

		List<String> checkInDate = Arrays.asList(CheckinDate.split("\\|"));
		List<String> adults = Arrays.asList(Adults.split("\\|"));
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		Wait.waitUntilPresenceOfElementLocated(OR.TapeChart_CheckIn, driver);
		TapeChart.TapeChart_CheckIn.clear();
		TapeChart.TapeChart_CheckIn.sendKeys(checkInDate.get(0));
		steps.add("Enter Checking Date  : " + checkInDate.get(0));
		tapechartLogger.info("Enter Checking Date  : " + checkInDate.get(0));
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(adults.get(0));
		steps.add("Enter Adults  : " + Adults);
		tapechartLogger.info("Enter Adults  : " + adults.get(0));
		TapeChart.Click_Search_TapeChart.click();
		steps.add("Click Tape Chart search");
	}

	public ArrayList<String> VerifyRate_TapeChart(WebDriver driver, String RateValue, String RoomClassAbb,
												  String RateName, ArrayList<String> steps) throws InterruptedException {

		String CellPath = "//div[text()='" + RoomClassAbb + "']//following-sibling::div/div";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement CurrentCellPath = driver.findElement(By.xpath(CellPath));
		if (LastRoomClassInTapeChart(driver, RoomClassAbb)) {
			Utility.ScrollToElement(CurrentCellPath, driver);
		} else {
			Utility.ScrollToElement(CurrentCellPath, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
		}
		String finalValue = Utility.convertDollarToNormalAmount(driver, CurrentCellPath.getText());
		tapechartLogger.info("Verified '" + RateName + "' is Available in TapeChart Search with BaseAmount of "
				+ CurrentCellPath.getText());
		test_steps.add("Verified '" + RateName + "' is Available in TapeChart Search with BaseAmount of "
				+ CurrentCellPath.getText());
		assertEquals(RateValue, finalValue);
		return steps;
	}

	public ArrayList<String> ClickOnRate(WebDriver driver, String RoomClassAbb, ArrayList<String> steps)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String CellPath = "//div[text()='" + RoomClassAbb + "']//following-sibling::div/div";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement CurrentCell = driver.findElement(By.xpath(CellPath));
		Wait.explicit_wait_visibilityof_webelement(CurrentCell, driver);
		Wait.explicit_wait_elementToBeClickable(CurrentCell, driver);
		if (LastRoomClassInTapeChart(driver, RoomClassAbb)) {
			Utility.ScrollToElement(CurrentCell, driver);
		} else {
			Utility.ScrollToElement(CurrentCell, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
		}
		CurrentCell.click();
		tapechartLogger.info("Click on the Rate for one of the days against the Room class '" + RoomClassAbb + "'");
		steps.add("Click on the Rate for one of the days against the Room class '" + RoomClassAbb + "'");
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_Heading, driver);
		assertTrue(TapeChart.RateDetailPopup_Heading.isDisplayed(), "Failed: 'Rate Detail' popup is not Displayed");
		tapechartLogger.info("Verified 'Rate Detail' popup is Displayed");
		steps.add("Verified 'Rate Detail' popup is Displayed");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_RateName(WebDriver driver, String rateName, ArrayList<String> steps)
			throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_RateName, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_RateName, driver);
		String value = TapeChart.RateDetailPopup_RateName.getAttribute("value");
		assertEquals(value, rateName, "Failed: Rate Name Missmatched");
		tapechartLogger.info("Verified Rate Name '" + value + "'");
		steps.add("Verified Rate Name '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_RatePlan(WebDriver driver, String ratePlan, ArrayList<String> steps)
			throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_RatePlan, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_RatePlan, driver);
		String value = new Select(TapeChart.RateDetailPopup_RatePlan).getFirstSelectedOption().getText();
		assertEquals(value, ratePlan, "Failed: Rate Plan Missmatched");
		tapechartLogger.info("Verified Rate Plan '" + value + "'");
		steps.add("Verified Rate Plan '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_RateType(WebDriver driver, String RateType, ArrayList<String> steps)
			throws InterruptedException {
		// TODO Auto-generated method stub
		String RateTypeXpath = "//div[@id='popUpForInnroad']//span[text()='" + RateType
				+ "']//preceding-sibling::input[@type='radio']";
		WebElement RateTypeElement = driver.findElement(By.xpath(RateTypeXpath));
		Wait.explicit_wait_visibilityof_webelement(RateTypeElement, driver);
		Utility.ScrollToElement(RateTypeElement, driver);
		assertEquals(RateTypeElement.isSelected(), true, "Failed: Rate Interval Missmatched");
		tapechartLogger.info("Verified Rate Type '" + RateType + "' is selected");
		steps.add("Verified Rate Type '" + RateType + "' is selected");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_RateAttributes(WebDriver driver, String rateAttributes,
																  ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_RateAttributes, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_RateAttributes, driver);
		String value = TapeChart.RateDetailPopup_RateAttributes.getAttribute("placeholder");
		assertEquals(value, rateAttributes, "Failed: Rate Attributes Missmatched");
		tapechartLogger.info("Verified Rate Attributes '" + value + "'");
		steps.add("Verified Rate Attributes '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_Interval(WebDriver driver, String Interval, ArrayList<String> steps)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_Interval, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_Interval, driver);
		String value = TapeChart.RateDetailPopup_Interval.getAttribute("value");
		assertEquals(value, Interval, "Failed: Rate Interval Missmatched");
		tapechartLogger.info("Verified Interval '" + value + "'");
		steps.add("Verified Interval '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_MaxAdults(WebDriver driver, String maxAdults,
															 ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_MaxAdults, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_MaxAdults, driver);
		String value = TapeChart.RateDetailPopup_MaxAdults.getAttribute("value");
		assertEquals(value, maxAdults, "Failed: Rate MaxAdults Missmatched");
		tapechartLogger.info("Verified Max Adults '" + value + "'");
		steps.add("Verified Max Adults '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_MaxPersons(WebDriver driver, String maxPersons,
															  ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_MaxPersons, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_MaxPersons, driver);
		String value = TapeChart.RateDetailPopup_MaxPersons.getAttribute("value");
		assertEquals(value, maxPersons, "Failed: Rate MaxPersons Missmatched");
		tapechartLogger.info("Verified Max Persons '" + value + "'");
		steps.add("Verified Max Persons '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_BaseAmount(WebDriver driver, String baseAmount,
															  ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_BaseAmount, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_BaseAmount, driver);
		String value = TapeChart.RateDetailPopup_BaseAmount.getAttribute("value");
		assertEquals(value, baseAmount, "Failed: Rate BaseAmount Missmatched");
		tapechartLogger.info("Verified Base Amount '" + value + "'");
		steps.add("Verified Base Amount '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_AdditionalAdult(WebDriver driver, String additionalAdult,
																   ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_AdditionalAdult, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_AdditionalAdult, driver);
		String value = TapeChart.RateDetailPopup_AdditionalAdult.getAttribute("value");
		assertEquals(value, additionalAdult, "Failed: Rate Additional Adult Missmatched");
		tapechartLogger.info("Verified Additional Adult '" + value + "'");
		steps.add("Verified Additional Adult '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_AdditionalChild(WebDriver driver, String additionalChild,
																   ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_AdditionalChild, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_AdditionalChild, driver);
		String value = TapeChart.RateDetailPopup_AdditionalChild.getAttribute("value");
		assertEquals(value, additionalChild, "Failed: Rate Additional Child Missmatched");
		tapechartLogger.info("Verified Additional Child '" + value + "'");
		steps.add("Verified Additional Child '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_DisplayName(WebDriver driver, String displayName,
															   ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_DisplayName, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_DisplayName, driver);
		String value = TapeChart.RateDetailPopup_DisplayName.getAttribute("value");
		assertEquals(value, displayName, "Failed: Rate DisplayName Missmatched");
		tapechartLogger.info("Verified Display Name '" + value + "'");
		steps.add("Verified Display Name '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_Policy(WebDriver driver, String Policy, ArrayList<String> steps)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_Policy, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_Policy, driver);
		String value = TapeChart.RateDetailPopup_Policy.getAttribute("value");
		assertEquals(value, Policy, "Failed: Rate Policy Missmatched");
		tapechartLogger.info("Verified Policy '" + value + "'");
		steps.add("Verified Policy '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_Description(WebDriver driver, String Description,
															   ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_Description, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_Description, driver);
		String value = TapeChart.RateDetailPopup_Description.getAttribute("value");
		assertEquals(value, Description, "Failed: Rate Description Missmatched");
		tapechartLogger.info("Verified Description '" + value + "'");
		steps.add("Verified Description '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_Season(WebDriver driver, String Season, ArrayList<String> steps)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_Season, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_Season, driver);
		String value = TapeChart.RateDetailPopup_Season.getText();
		assertEquals(value, Season, "Failed: Rate Season Missmatched");
		tapechartLogger.info("Verified Season '" + value + "'");
		steps.add("Verified Season '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_RoomClass(WebDriver driver, String rateRoomClass,
															 ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_RoomClass, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_RoomClass, driver);
		String value = TapeChart.RateDetailPopup_RoomClass.getText();
		assertEquals(value, rateRoomClass, "Failed: Rate Room Class Missmatched");
		tapechartLogger.info("Verified Room Class '" + value + "'");
		steps.add("Verified Room Class '" + value + "'");
		return steps;
	}

	public ArrayList<String> VerifyRateDetailPopup_Source(WebDriver driver, String Source, ArrayList<String> steps)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_Source, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_Source, driver);
		String value = TapeChart.RateDetailPopup_Source.getText();
		assertEquals(value, Source, "Failed: Rate Source Missmatched");
		tapechartLogger.info("Verified Source '" + value + "'");
		steps.add("Verified Source '" + value + "'");
		return steps;
	}

	public ArrayList<String> ClickRateDetailPopupCancelButton(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RateDetailPopup_CancelButton, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.RateDetailPopup_CancelButton, driver);
		Utility.ScrollToElement(TapeChart.RateDetailPopup_CancelButton, driver);
		TapeChart.RateDetailPopup_CancelButton.click();
		tapechartLogger.info("Click 'Rate Detail' popup 'Cancel' button");
		steps.add("Click 'Rate Detail' popup 'Cancel' button");
		Wait.waitForElementToBeGone(driver, TapeChart.RateDetailPopup_CancelButton, 60);
		return steps;
	}

	public ArrayList<String> VerifyRuleRow(WebDriver driver, String RoomClass, String ruleType, String ruleValue,
										   ArrayList<String> steps) throws InterruptedException {
		String xpath = "//div[text()='" + RoomClass
				+ "']//ancestor::div[@class='roomRatesChart']//div[contains(@class,'rulesrow')]/div[1]";
		WebElement RuleTypeElement = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(RuleTypeElement, driver);
		Utility.ScrollToElement(RuleTypeElement, driver);
		assertEquals(RuleTypeElement.getText(), ruleType, "Failed : Rule type Missmatched");
		steps.add("Verified Rule Type : " + ruleType);
		tapechartLogger.info("Verified Rule Type : " + ruleType);

		xpath = "//div[text()='" + RoomClass
				+ "']//ancestor::div[@class='roomRatesChart']//div[contains(@class,'ruleactive')]//div[contains(@class,'absoluteCenter')]";
		WebElement RuleValueElement = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(RuleValueElement, driver);
		Utility.ScrollToElement(RuleValueElement, driver);
		assertEquals(RuleValueElement.getText(), ruleValue, "Failed : Rule Value Missmatched");
		steps.add("Verified Rule Value : " + ruleValue);
		tapechartLogger.info("Verified Rule Value : " + ruleValue);

		return steps;
	}

	public ArrayList<String> ClickCell(WebDriver driver, String RoomClass, String RoomNumber, ArrayList<String> steps)
			throws InterruptedException {
		String xpath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
				+ RoomNumber + "']//following-sibling::div/span/div[contains(@class,'tapechartdatecell')][1]";
		WebElement Cell = driver.findElement(By.xpath(xpath));
		Wait.explicit_wait_visibilityof_webelement(Cell, driver);
		Wait.explicit_wait_elementToBeClickable(Cell, driver);

		if (LastRoomClassInTapeChart(driver, RoomClass)) {
			Utility.ScrollToElement(Cell, driver);
		} else {
			Utility.ScrollToElement(Cell, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
		}
		Cell.click();
		steps.add("Click on Cell of Room '" + RoomClass + ":" + RoomNumber);
		tapechartLogger.info("Click on Cell of Room '" + RoomClass + ":" + RoomNumber);
		return steps;
	}

	public ArrayList<String> VerifyRuleBroken(WebDriver driver, String RuleName, String RuleDescription,
											  ArrayList<String> steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.Rule_Broken_PopUp);
		Wait.waitForToasterToBeVisibile(By.xpath(OR.Rule_Broken_PopUp), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Rule_Broken_PopUp), driver);
		assertEquals(TapeChart.Rule_Broken_PopUp.isDisplayed(), true, "Rule broken popup is not Displayed");
		assertEquals(TapeChart.Rule_Broken_PopUp.getText(), "Rules Broken", "Rule broken popup is not showing");
		steps.add("Rule Broken Popup Appeared");
		tapechartLogger.info("Rule Broken Popup Appeared");

		String ruleNamexpath = "//*[@id='rulesBrokenConfirmation']//td[contains(@data-bind,'text: RuleName')]";
		String ruleName = driver.findElement(By.xpath(ruleNamexpath)).getText();
		assertEquals(ruleName, RuleName, "Rule Not Verified, RuleName not matched");
		steps.add("Verified Rule Name '" + RuleName + "'");
		tapechartLogger.info("Verified Rule Name '" + RuleName + "'");
		String RuleDescriptionxpath = "//*[@id='rulesBrokenConfirmation']//td[contains(@data-bind,'text: RuleDescription')]";
		String ruleDesc = driver.findElement(By.xpath(RuleDescriptionxpath)).getText();
		assertEquals(ruleDesc, RuleDescription, "Rule Not Verified, Rule Desc not matched");
		steps.add("Verified Rule Description '" + RuleDescription + "'");
		tapechartLogger.info("Verified Rule Description '" + RuleDescription + "'");

		String Messagexpath = "//span[text()='Do you want to continue?']";
		WebElement Message = driver.findElement(By.xpath(Messagexpath));
		assertEquals(Message.isDisplayed(), true,
				"Rule broken popup Message 'Do you want to continue?' is not displaying");
		steps.add("Rule broken popup Message 'Do you want to continue?' is displaying");
		tapechartLogger.info("Rule broken popup Message 'Do you want to continue?' is displaying");

		assertEquals(TapeChart.Rule_Broken_Cancel.isDisplayed(), true,
				"Rule broken popup Cancel Button is not displaying");
		steps.add("Rule Broken Popup 'Cancel Button' is displaying");
		tapechartLogger.info("Rule Broken Popup 'Cancel Button' is displaying");

		assertEquals(TapeChart.RuleBrokenBook_Btn.isDisplayed(), true,
				"Rule broken popup Book Button is not displaying");
		steps.add("Rule Broken Popup 'Book Button' is displaying");
		tapechartLogger.info("Rule Broken Popup 'Book Button' is displaying");

		return steps;
	}

	public ArrayList<String> ClickBookRuleBroken(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.RuleBrokenBook_Btn, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.RuleBrokenBook_Btn, driver);
		Utility.ScrollToElement(TapeChart.RuleBrokenBook_Btn, driver);
		TapeChart.RuleBrokenBook_Btn.click();
		steps.add("Click Rule Broken Popup 'Book Button'");
		tapechartLogger.info("Click Rule Broken Popup 'Book Button'");
		Wait.waitForElementToBeGone(driver, TapeChart.RuleBrokenBook_Btn, 60);
		steps.add("New Reservation Page is opened");
		tapechartLogger.info("New Reservation Page is opened");
		return steps;
	}

	public ArrayList<String> ClickCancelRuleBroken(WebDriver driver, ArrayList<String> steps)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Rule_Broken_Cancel, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.Rule_Broken_Cancel, driver);
		Utility.ScrollToElement(TapeChart.Rule_Broken_Cancel, driver);
		TapeChart.Rule_Broken_Cancel.click();
		steps.add("Click Rule Broken Popup 'Cancel Button'");
		tapechartLogger.info("Click Rule Broken Popup 'Cancel Button'");
		Wait.waitForElementToBeGone(driver, TapeChart.Rule_Broken_Cancel, 60);
		return steps;
	}

	// Added by Adnan
	public int ExtendReservation(WebDriver driver, String RoomNumber, String RoomClass, String FullName)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path2 = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		// Actions actions = new Actions(driver);
		// actions.moveToElement(driver.findElement(By.xpath(path2))).perform();
		String path = "(//div[contains(text(),'" + RoomClass
				+ "') and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//div[text()='"
				+ FullName + "']//following::span[@class='ir-tc-rightResHandle'])[1]";
		tapechartLogger.info(path);

		int size = driver.findElements(By.xpath(path)).size();
		int preWidth = 0;
		Wait.explicit_wait_xpath(path, driver);
		WebElement ele2 = driver.findElement(By.xpath(path2));
		WebElement ele = driver.findElement(By.xpath(path));
		Utility.app_logs.info(" Location " + ele.getLocation());
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (LastRoomClassInTapeChart(driver, RoomClass)) {
			Utility.ScrollToElement(ele2, driver);
			jse.executeScript("window.scrollBy(0,200)");

		} else {
			Utility.ScrollToElement(ele2, driver);
			jse.executeScript("window.scrollBy(0,-300)");
		}
		int divWidth = ele2.getSize().getWidth();
		tapechartLogger.info("Pre Width : " + divWidth);
		preWidth = divWidth;
		// int divHeight = ele2.getSize().getHeight();
		// Get half of Height
		// int halfHeight = divHeight / 2;
		Actions builder = new Actions(driver);

		Action resizable = builder.moveToElement(ele).clickAndHold().moveByOffset(divWidth, 0).release().build();
		resizable.perform();
		return preWidth;
	}

	// Created By Adhnan
	public void VerifyUnassignedReservationColor(WebDriver driver, String ReservationName, String ArrivalDate,
												 String DepartureDate, String RoomClassAbb, ArrayList<String> steps) throws InterruptedException {
		// Verify Reservation
		int index = GetUnassignedReservationIndex(driver, ReservationName, ArrivalDate, DepartureDate, RoomClassAbb);
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String path = "(" + OR.UnassignedList + ")[" + index + "]//parent::div[contains(@class,'parking-lot-card')]";
		tapechartLogger.info("Res Path is " + path);
		Wait.explicit_wait_xpath(path, driver);
		WebElement res = driver.findElement(By.xpath(path));
		Wait.explicit_wait_visibilityof_webelement_120(res, driver);
		Utility.ScrollToElement(res, driver);
		String Color = res.getCssValue("background-color");
		tapechartLogger.info("Reservation Color : " + Color);
		steps.add("Verified Unassigned Reservation '" + ReservationName + "' Color(" + Color + ") is Green");
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyBlackOutRoomsInTapeChat> ' Description: <Verify the
	 * rooms block in TapeChart> ' Input parameters: < String RoomClassName> '
	 * Return value: ArrayList<String> ' Created By: <Reddy Ponnolu> ' Created
	 * On: <04/05/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyBlackOutRoomsInTapeChat(WebDriver driver, String RoomClassName,
														   ArrayList<String> test_steps) throws InterruptedException {
		String BlackOutRoomLine1 = "(//div[text()='" + RoomClassName
				+ "']//ancestor::div[@class='roomRatesChart']//div[contains(@class,'col-xs-11')])[2]";
		String aquifiedCellWithBlackOut = "(//div[text()='" + RoomClassName
				+ "']//ancestor::div[@class='roomRatesChart']"
				+ "//div[contains(@class,'col-xs-11')])[2]//div[contains(@class,'tapechartdatecell BlockedOut')]";
		String BlackOutText = "(//div[text()='" + RoomClassName
				+ "']//ancestor::div[@class='roomRatesChart']//div[contains(@class,'col-xs-11')])[2]//div[contains(@class,'guest_display_name_short')]";

		int cellCountInLine = driver.findElements(By.xpath(BlackOutRoomLine1)).size();
		String cellContentInLine = "null";
		for (int i = 1; i <= cellCountInLine; i++) {
			cellContentInLine = driver.findElement(By.xpath("((//div[text()='" + RoomClassName
					+ "']//ancestor::div[@class='roomRatesChart']//div[contains(@class,'col-xs-11')])[2]//div[contains(@class,'tapechartdatecell BlockedOut')])["
					+ i + "]")).getText();
			tapechartLogger.info("cellContentInLine:  " + cellContentInLine);
			if (cellContentInLine.contains("B")) {
				String blackOutTittle = driver.findElement(By.xpath(aquifiedCellWithBlackOut)).getText();
				tapechartLogger.info("blackOutTittle :" + blackOutTittle);
				Assert.assertEquals(blackOutTittle, "B", "BlckOutText is not matching");

			}
			if (cellContentInLine.contains("BlockOutRoom")) {
				String blackOutTittle = driver.findElement(By.xpath(aquifiedCellWithBlackOut)).getText();
				tapechartLogger.info("blackOutTittle :" + blackOutTittle);
				Assert.assertEquals(blackOutTittle, "BlockOutRoom", "BlckOutText is not matching");

			}
			Wait.WaitForElement(driver, OR.Click_Reservation);
			Wait.waitForElementToBeVisibile(By.xpath(OR.Click_Reservation), driver);
		}

		return test_steps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <MoveReservations> ' Description: <> ' Input parameters: (
	 * Room Number, Room Class Name ) ' Return value: <return type> ( void /
	 * String / ArrayList<String> ...etc ' Created By: <Full name of developer>
	 * ' Created On: <MM/DD/YYYY>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String MoveReservations(WebDriver driver, String roomNumber, String roomClass) throws InterruptedException {
		Wait.wait2Second();
		roomNumber = roomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(roomNumber);
		Wait.wait2Second();
		String path = "//div[contains(text(),'" + roomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ roomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		tapechartLogger.info("PTah:" + path);
		int nextRoom = Integer.parseInt(roomNumber) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[contains(text(),'" + roomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='" + NextRoom
				+ "']//following-sibling::div/span/div[contains(@class,'tapechartdatecell')][3]";

		tapechartLogger.info("To pTah:" + ToPath);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (LastRoomClassInTapeChart(driver, roomClass)) {
			jse.executeScript("window.scrollBy(0,200)");

		} else {
			jse.executeScript("window.scrollBy(0,-300)");
		}

		tapechartLogger.info("Scrolled to the reservation");

		WebElement From = driver.findElement(By.xpath(path));

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Actions builder = new Actions(driver);
		Action resizable = builder.moveToElement(From).clickAndHold().moveToElement(To).release().build();

		return NextRoom;
	}

	@Override
	public void verifyOutOfOrder(WebDriver driver, ArrayList<String> test_steps, String roomClassName, String title)
			throws InterruptedException {
		// TODO Auto-generated method stub
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		String color = tapeChart.TapeChart_outIcon.getCssValue("background-color");
		tapechartLogger.info(color);
		// Utility.ScrollToTillEndOfPage(driver);
		String TapeChart_RoomClassPath = "//div[@class='roomRatesChart']//div[contains(@class,'roomclassname')][contains(text(),'"
				+ roomClassName + "')]";
		WebElement TapeChart_RoomClassName = driver.findElement(By.xpath(TapeChart_RoomClassPath));
		Utility.ScrollToElement(TapeChart_RoomClassName, driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart_RoomClassName, driver);
		String outElement = "//div[@class='roomRatesChart']/div[contains(@class,'row ratesrow')]/div[contains(@class,'roomclassname')][contains(text(),'"
				+ roomClassName
				+ "')]/ancestor::div[contains(@class,'ratesrow')]//following-sibling::div//div[contains(@class,'roomnumber')][@title='"
				+ title + "']//following-sibling::div//div[contains(@class,'Out')][@title='Out']";
		WebElement outEle = driver.findElement(By.xpath(outElement));
		boolean tapeChatClass = outEle.isDisplayed();
		String backgroundColor = outEle.getCssValue("background-color");
		tapechartLogger.info(backgroundColor);

		if (tapeChatClass) {

			Assert.assertTrue(outEle.isDisplayed(), "Failed : to display Out for Room Class " + roomClassName);
			test_steps.add("Out is dispalyed for Room Class " + roomClassName);
			tapechartLogger.info("Out is dispalyed for Room Class " + roomClassName);
			Assert.assertTrue(color.equals(backgroundColor),
					"Failed : to verify background color of Out of Order Room");
			test_steps.add("Out of Order Room Color Matched ");
			tapechartLogger.info("Out of Order Room Color Matched ");
		} else {
			test_steps.add("Failed : to display Out for Room Class " + roomClassName);
			tapechartLogger.info("Failed : to display Out for Room Class " + roomClassName);
			test_steps.add("Failed to  Matched Out of Order Room Color");
			tapechartLogger.info("Failed to  Matched Out of Order Room Color");
		}
	}

	public ArrayList<String> verifyReservationExist(WebDriver driver, String roomNumber, String roomClass,
													String roomClassAbbreviation, String fullName)

			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Wait.wait2Second();
		String rervationPath = "//div[text()='" + roomClassAbbreviation
				+ "' and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//div[@title='"
				+ roomNumber + "']//following-sibling::div//div//div[@class='guest_display_name_short']";
		WebElement reservationElement = driver.findElement(By.xpath(rervationPath));
		JavascriptExecutor javaScript = (JavascriptExecutor) driver;
		if (LastRoomClassInTapeChart(driver, roomClass)) {
			Utility.ScrollToElement(reservationElement, driver);
			javaScript.executeScript("window.scrollBy(0,-200)");

		} else {
			Utility.ScrollToElement(reservationElement, driver);
			javaScript.executeScript("window.scrollBy(0,-300)");
			Utility.app_logs.info("Scrolled Back");
		}
		assertEquals(reservationElement.isDisplayed(), true, "Failed: Created reservation is not displaying");
		assertEquals(reservationElement.getText(), fullName, "Failed: Reservation name is mismatching in tape chart");
		testSteps.add("Verify group name with reeservation name");

		return testSteps;
	}

	public ArrayList<String> verifyResevationhandles(WebDriver driver, String roomNumber, String roomClassAbbreviation,
													 boolean right, boolean left, ArrayList<String> steps) throws InterruptedException {

		roomNumber = roomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path = "(//div[@class='row ratesrow']//div[text()='" + roomClassAbbreviation
				+ "']/../../div/div[@title='" + roomNumber
				+ "']//following-sibling::div//div[@class='DatesContainer'])[last()]";
		String leftpath = "//div[@class='row ratesrow']//div[text()='" + roomClassAbbreviation
				+ "']/../../div/div[@title='" + roomNumber
				+ "']//following-sibling::div//div[@class='DatesContainer']//ancestor::span[@class='ir-tce-mainResCell']/span[contains(@class,'left')]";
		String rightpath = "//div[@class='row ratesrow']//div[text()='" + roomClassAbbreviation
				+ "']/../../div/div[@title='" + roomNumber
				+ "']//following-sibling::div//div[@class='DatesContainer']//ancestor::span[@class='ir-tce-mainResCell']/span[contains(@class,'right')]";
		WebElement Room = driver.findElement(By.xpath(path));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (LastRoomClassInTapeChart(driver, roomClassAbbreviation)) {
			Utility.ScrollToElement(Room, driver);
			jse.executeScript("window.scrollBy(0,-200)");

		} else {
			Utility.ScrollToElement(Room, driver);
			jse.executeScript("window.scrollBy(0,-350)");
		}
		WebElement hoverElement = driver.findElement(By.xpath(path));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		steps.add("Hovers over a Reservation (Room '" + roomClassAbbreviation + ":" + roomNumber + "' )");
		WebElement lefthandle = driver.findElement(By.xpath(leftpath));
		WebElement righthandle = driver.findElement(By.xpath(rightpath));
		if (right) {
			for (int i = 0; i < 5; i++) {
				Utility.app_logs.info(righthandle.getAttribute("style"));
				if (righthandle.getAttribute("style").contains("visible")) {
					break;
				} else {
					Wait.wait2Second();
				}
			}
			assertEquals(righthandle.getAttribute("style"), "visibility: visible;", "right handles not appear");
			steps.add("Right Handle Appeared");
		} else {
			Utility.app_logs.info(righthandle.getAttribute("style"));
			assertEquals(righthandle.getAttribute("style"), "visibility: hidden;", "right handles not appear");
			steps.add("Right Handle not Appeared");
		}
		if (left) {
			for (int i = 0; i < 5; i++) {
				Utility.app_logs.info(lefthandle.getAttribute("style"));
				if (lefthandle.getAttribute("style").contains("visible")) {
					break;
				} else {
					Wait.wait2Second();
				}
			}
			assertEquals(lefthandle.getAttribute("style"), "visibility: visible;", "left handles not appear");
			steps.add("Left Handle Appeared");
		} else {

			Utility.app_logs.info(lefthandle.getAttribute("style"));
			assertEquals(lefthandle.getAttribute("style"), "visibility: hidden;", "left handles not appear");
			steps.add("Left Handle not Appeared");
		}
		return steps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <toolTipHeaderVerification> ' Description: <tool tip
	 * header verification> ' Input parameters: <first room number, room class,
	 * guest name, folio total balance ,folio balance, reservation number, room
	 * class abbreviation> ' Return value: <ArrayList> ' Created By: <Adnan
	 * Ghaffar> ' Created On: <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> toolTipHeaderVerification(WebDriver driver, String roomNumber, String roomClass,
													   String fullName, String folioTotal, String folioBalance, String reservationNumber,
													   String roomClassAbbreviation)

			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Wait.wait2Second();
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;

		String path2 = "//div[text()='" + roomClassAbbreviation
				+ "' and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//div[@title='"
				+ roomNumber + "']//following-sibling::div//div[text()='" + fullName + "']/../../../..";
		WebElement Room = driver.findElement(By.xpath(path2));
		if (LastRoomClassInTapeChart(driver, roomClassAbbreviation)) {
			Utility.ScrollToElement(Room, driver);
			javaScriptExecutor.executeScript("window.scrollBy(0,-200)");

		} else {
			tapechartLogger.info("In if scroll");
			Utility.ScrollToElement(Room, driver);
			javaScriptExecutor.executeScript("window.scrollBy(0,-350)");
		}
		WebElement hoverElement = driver.findElement(By.xpath(path2));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		testSteps.add("Hovers over a Reservation (Room '" + roomClassAbbreviation + ":" + roomNumber + "' )");

		String namePath = "//div[@role='tooltip']//div[text()='" + fullName + "']";
		String reservationNumberPath = "//div[@role='tooltip']//div[text()='" + fullName
				+ "']/../../following-sibling::span/b";
		String folioTotalPath = "//div[@role='tooltip']//div[text()='" + fullName
				+ "']/../../../../following-sibling::div//span[contains(@id,'FolioTotal')]";
		String folioBalancePath = "//div[@role='tooltip']//div[text()='" + fullName
				+ "']/../../../../following-sibling::div//span[contains(@id,'FolioBalance')]";

		// jse.executeScript("window.scrollBy(0,-300)");
		Wait.WaitForElement(driver, namePath);
		Wait.waitForElementToBeVisibile(By.xpath(namePath), driver);
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(namePath)), driver);
		String foundName = driver.findElement(By.xpath(namePath)).getText();
		assertEquals(foundName, fullName, "Failed Name Missmatched");
		testSteps.add("Successfully Verified Full Name in ToolTip : " + foundName);
		tapechartLogger.info("Successfully Verified Full Name in ToolTip : " + foundName);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(reservationNumberPath)), driver);
		String foundReservationNumber = driver.findElement(By.xpath(reservationNumberPath)).getText();
		assertEquals(foundReservationNumber.trim(), "#" + reservationNumber, "Failed Reservation No Missmatched");
		testSteps.add("Successfully Verified Reservation No in ToolTip : " + foundReservationNumber);
		tapechartLogger.info("Successfully Verified Reservation No in ToolTip : " + foundReservationNumber);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(folioTotalPath)), driver);
		String foundTotal = driver.findElement(By.xpath(folioTotalPath)).getText();

		for (int i = 0; i < 5; i++) {
			if (foundTotal.equals("")) {
				tapechartLogger.info("try " + i + " Trip total null");
				Wait.wait2Second();
				foundTotal = driver.findElement(By.xpath(folioTotalPath)).getText();
			} else {
				break;
			}
		}
		foundTotal = Utility.RemoveDollarandSpaces(driver, foundTotal);
		testSteps.add("Successfully Verified Folio Total in ToolTip : " + foundTotal);
		tapechartLogger.info("Successfully Verified Folio Total in ToolTip : " + foundTotal);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(folioBalancePath)), driver);
		String foundBalance = driver.findElement(By.xpath(folioBalancePath)).getText();
		for (int i = 0; i < 5; i++) {
			if (foundBalance.equals("")) {
				tapechartLogger.info("try " + i + "Folio Balance null");
				Wait.wait2Second();
				foundBalance = driver.findElement(By.xpath(folioBalancePath)).getText();
			} else {
				break;
			}
		}
		foundBalance = Utility.RemoveDollarandSpaces(driver, foundBalance);
		assertEquals(foundBalance, folioBalance, "Failed Folio Balance Missmatched");
		testSteps.add("Successfully Verified Folio Balance in ToolTip : " + foundBalance);
		tapechartLogger.info("Successfully Verified Folio Balance in ToolTip : " + foundBalance);

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <toolTipVerificationLine> ' Description: <verification of
	 * tool tip> ' Input parameters: <room number, room class name, guest name,
	 * arrival date, depart date , total nights, room index> ' Return value:
	 * <ArrayList> ' Created By: <Adnan Ghaffar> ' Created On: <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> toolTipVerificationLine(WebDriver driver, String roomNumber, String roomClass,
													 String guestName, String arrivalDate, String departDate, String totalNights, int index)
			throws InterruptedException {

		ArrayList<String> test_steps = new ArrayList<>();
		String arivalDatePath = "(//div[@role='tooltip']//div[text()='" + guestName
				+ "']/../../../../../../following-sibling::div//div[contains(@class,'hover-date-month ng-arriveDate')])["
				+ index + "]";
		String departDatePath = "(//div[@role='tooltip']//div[text()='" + guestName
				+ "']/../../../../../../following-sibling::div//div[contains(@class,'hover-date-month ng-departDate')])["
				+ index + "]";
		String totalNightsPath = "(//div[@role='tooltip']//div[text()='" + guestName
				+ "']/../../../../../../following-sibling::div//div[contains(@class,'totalNights')])[" + index + "]";
		String roomClassPath = "(//div[@role='tooltip']//div[text()='" + guestName
				+ "']/../../../../../../following-sibling::div//span[contains(@class,'ir-tc-roomClass')])[" + index
				+ "]";
		String roomNoPath = "(//div[@role='tooltip']//div[text()='" + guestName
				+ "']/../../../../../../following-sibling::div//span[contains(@class,'ir-tc-roomClass')]/../following-sibling::div/span)["
				+ index + "]";

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(arivalDatePath)), driver);
		String foundArivalDate = driver.findElement(By.xpath(arivalDatePath)).getText();
		assertEquals(Utility.parseDate(foundArivalDate, "MMM dd, yyyy", "MMM dd, yyyy"), arrivalDate,
				"Failed Arival Date Missmatched");
		test_steps.add("Successfully Verified Arival Date in ToolTip : " + foundArivalDate);
		tapechartLogger.info("Successfully Verified Arival Date in ToolTip : " + foundArivalDate);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(departDatePath)), driver);
		String foundDepartDate = driver.findElement(By.xpath(departDatePath)).getText();
		assertEquals(Utility.parseDate(foundDepartDate, "MMM dd, yyyy", "MMM dd, yyyy"), departDate,
				"Failed Depart Date Missmatched");
		test_steps.add("Successfully Verified Depart Date in ToolTip : " + foundDepartDate);
		tapechartLogger.info("Successfully Verified Depart Date in ToolTip : " + foundDepartDate);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(totalNightsPath)), driver);
		String foundNights = driver.findElement(By.xpath(totalNightsPath)).getText();
		assertEquals(foundNights, totalNights, "Failed Total Nights Missmatched");
		test_steps.add("Successfully Verified Total Nights in ToolTip : " + foundNights);
		tapechartLogger.info("Successfully Verified Total Nights in ToolTip : " + foundNights);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(roomClassPath)), driver);
		String foundClass = driver.findElement(By.xpath(roomClassPath)).getText();
		foundClass = foundClass.replace(".", "");
		foundClass = foundClass.replace(".", "");
		foundClass = foundClass.replace(".", "");
		assertTrue(roomClass.contains(foundClass),
				"Failed RoomClass Name Missmatched Found[" + foundClass + "} expented[" + roomClass + "]");
		test_steps.add("Successfully Verified RoomClass Name in ToolTip : " + foundClass);
		tapechartLogger.info("Successfully Verified RoomClass Name in ToolTip : " + foundClass);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(roomNoPath)), driver);
		String foundRoomNo = driver.findElement(By.xpath(roomNoPath)).getText();
		assertEquals(foundRoomNo, roomNumber, "Failed Room Number Missmatched");
		test_steps.add("Successfully Verified Room Number in ToolTip : " + foundRoomNo);
		tapechartLogger.info("Successfully Verified Room Number in ToolTip : " + foundRoomNo);

		return test_steps;
	}

	public String getTapeChartFirstCellDate(WebDriver driver) throws InterruptedException {
		Elements_TapeChart elementsTapeChart = new Elements_TapeChart(driver);

		Utility.ScrollToElement_NoWait(elementsTapeChart.TapeChartDateChart, driver);
		String getDate = elementsTapeChart.TapeChartDateChart.getText();

		tapechartLogger.info(getDate);
		String[] splitDate = getDate.split(" ");
		tapechartLogger.info("splitDate: " + splitDate.length);
		for (int i = 0; i < splitDate.length; i++) {
			tapechartLogger.info(i + " : " + splitDate[i]);
		}
		return getDate;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <moveReservations> ' Description: <Move Reservation to
	 * index Date and Next Room in same> ' Input parameters: <first room number,
	 * room class, guest name, second room number, date index> ' Return value:
	 * <String> ' Created By: <Adnan Ghaffar> ' Created On: <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String moveReservations(WebDriver driver, String roomNumber, String roomClass, String guestName, int roomAdd,
								   int dateIndex) throws InterruptedException {

		roomNumber = roomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(roomNumber);
		Wait.wait2Second();
		String path = "//div[text()='" + roomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ roomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		tapechartLogger.info("PTah:" + path);
		int nextRoom = Integer.parseInt(roomNumber) + roomAdd;
		String secondRoom = Integer.toString(nextRoom);
		tapechartLogger.info("DateIndex: " + dateIndex);
		String ToPath = "//div[text()='" + roomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
				+ secondRoom + "']//following-sibling::div/span/div[contains(@class,'tapechartdatecell')][" + dateIndex
				+ "]";

		WebElement fromElement = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(fromElement, driver);
		Utility.app_logs.info(" Location " + fromElement.getLocation());
		JavascriptExecutor javaScript = (JavascriptExecutor) driver;
		if (LastRoomClassInTapeChart(driver, roomClass)) {
			Utility.ScrollToElement(fromElement, driver);
			javaScript.executeScript("window.scrollBy(0,-200)");

		} else {
			Utility.ScrollToElement(fromElement, driver);
			javaScript.executeScript("window.scrollBy(0,-300)");
			Utility.app_logs.info("Scrolled Back");
		}

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement toElement = driver.findElement(By.xpath(ToPath));

		Actions action = new Actions(driver);
		action.dragAndDrop(fromElement, toElement).build().perform();

		Actions builder = new Actions(driver);
		Action resizable = builder.moveToElement(fromElement).clickAndHold().moveToElement(toElement).release().build();
		// resizable.perform();

		return secondRoom;
	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <extendReservation> ' Description: <Extend reserved
	 * reservation from one room to next one> ' Input parameters: <room number,
	 * roomClassAbbreviation, guest name> ' Return value: <room number> '
	 * Created By: <Adnan Ghaffar> ' Created On: <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public int extendReservation(WebDriver driver, String roomNumber, String roomClassAbbreviation, String guestName)
			throws InterruptedException {

		roomNumber = roomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String pathFrom = "//div[contains(text(),'" + roomClassAbbreviation
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ roomNumber + "']//following-sibling::div//div[@class='DatesContainer']";

		String pathTo = "(//div[text()='" + roomClassAbbreviation
				+ "' and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//div[@title='"
				+ roomNumber + "']//following-sibling::div//div[text()='" + guestName
				+ "']//following::span[@class='ir-tc-rightResHandle'])[1]";

		int preWidth = 0;
		Wait.explicit_wait_xpath(pathTo, driver);
		WebElement elementFrom = driver.findElement(By.xpath(pathFrom));
		WebElement elementTo = driver.findElement(By.xpath(pathTo));
		Utility.app_logs.info(" Location " + elementTo.getLocation());
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		if (LastRoomClassInTapeChart(driver, roomClassAbbreviation)) {
			Utility.ScrollToElement(elementFrom, driver);
			jse.executeScript("window.scrollBy(0,-200)");

		} else {
			Utility.ScrollToElement(elementFrom, driver);
			jse.executeScript("window.scrollBy(0,-350)");
		}
		int divWidth = elementFrom.getSize().getWidth();
		tapechartLogger.info("Pre Width : " + divWidth);
		preWidth = divWidth;

		Actions builder = new Actions(driver);

		Action resizable = builder.moveToElement(elementTo).clickAndHold().moveByOffset(divWidth, 0).release().build();
		resizable.perform();
		return preWidth;
	}

	// Added by adnan
	public ArrayList<String> toolTip_Verification(WebDriver driver, String RoomNumber, String RoomClass,
												  String FullName, String ArivalDate, String DepartDate, String totalNights, String FolioTotal,
												  String FolioBalance, String resNo, String RoomClassAbb) throws InterruptedException {

		ArrayList<String> test_steps = new ArrayList<>();
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();

		String path2 = "//div[contains(text(),'" + RoomClassAbb
				+ "') and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[text()='" + FullName + "']/../../../..";
		WebElement Room = driver.findElement(By.xpath(path2));
		if (LastRoomClassInTapeChart(driver, RoomClassAbb)) {
			Utility.ScrollToElement(Room, driver);
		} else {
			Utility.ScrollToElement(Room, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
		}
		WebElement hoverElement = driver.findElement(By.xpath(path2));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		test_steps.add("Hovers over a Reservation (Room '" + RoomClassAbb + ":" + RoomNumber + "' )");
		// Actions actions = new Actions(driver);
		// actions.moveToElement(driver.findElement(By.xpath(path2))).perform();
		String path = "//*[text()='" + FullName + "']/following::span[@class='ir-tc-rightResHandle']";

		String namePath = "//div[@role='tooltip']//div[text()='" + FullName + "']";
		String resNoPath = "//div[@role='tooltip']//div[text()='" + FullName + "']/../../following-sibling::span/b";
		String folioTotalPath = "//div[@role='tooltip']//div[text()='" + FullName
				+ "']/../../../../following-sibling::div//span[contains(@id,'FolioTotal')]";
		String folioBalancePath = "//div[@role='tooltip']//div[text()='" + FullName
				+ "']/../../../../following-sibling::div//span[contains(@id,'FolioBalance')]";
		String arivalDatePath = "//div[@role='tooltip']//div[text()='" + FullName
				+ "']/../../../../../../following-sibling::div//div[contains(@class,'hover-date-month ng-arriveDate')]";
		String departDatePath = "//div[@role='tooltip']//div[text()='" + FullName
				+ "']/../../../../../../following-sibling::div//div[contains(@class,'hover-date-month ng-departDate')]";
		String totalNightsPath = "//div[@role='tooltip']//div[text()='" + FullName
				+ "']/../../../../../../following-sibling::div//div[contains(@class,'totalNights')]";
		String roomClassPath = "//div[@role='tooltip']//div[text()='" + FullName
				+ "']/../../../../../../following-sibling::div//span[contains(@class,'ir-tc-roomClass')]";
		String roomNoPath = "//div[@role='tooltip']//div[text()='" + FullName
				+ "']/../../../../../../following-sibling::div//span[contains(@class,'ir-tc-roomClass')]/../following-sibling::div/span";

		// Utility.ScrollToElement(driver.findElement(By.xpath(namePath)),
		// driver);
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(namePath)), driver);
		String foundName = driver.findElement(By.xpath(namePath)).getText();
		assertEquals(foundName, FullName, "Failed Name Missmatched");
		test_steps.add("Successfully Verified Full Name in ToolTip : " + foundName);
		tapechartLogger.info("Successfully Verified Full Name in ToolTip : " + foundName);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(resNoPath)), driver);
		String foundResNo = driver.findElement(By.xpath(resNoPath)).getText();
		assertEquals(foundResNo.trim(), "#" + resNo, "Failed Reservation No Missmatched");
		test_steps.add("Successfully Verified Reservation No in ToolTip : " + foundResNo);
		tapechartLogger.info("Successfully Verified Reservation No in ToolTip : " + foundResNo);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(folioTotalPath)), driver);
		String foundTotal = driver.findElement(By.xpath(folioTotalPath)).getText();
		tapechartLogger.info(folioTotalPath);
		tapechartLogger.info(foundTotal);
		for (int i = 0; i < 5; i++) {
			if (foundTotal.equals("")) {
				tapechartLogger.info("try " + i + " Trip total null");
				Wait.wait2Second();
				foundTotal = driver.findElement(By.xpath(folioTotalPath)).getText();
				tapechartLogger.info(foundTotal);
			} else {
				break;
			}
		}
		foundTotal = Utility.RemoveDollarandSpaces(driver, foundTotal);
		test_steps.add("Successfully Verified Folio Total in ToolTip : " + foundTotal);
		tapechartLogger.info("Successfully Verified Folio Total in ToolTip : " + foundTotal);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(folioBalancePath)), driver);
		String foundBalance = driver.findElement(By.xpath(folioBalancePath)).getText();
		for (int i = 0; i < 5; i++) {
			if (foundBalance.equals("")) {
				tapechartLogger.info("try " + i + "Folio Balance null");
				Wait.wait2Second();
				foundBalance = driver.findElement(By.xpath(folioBalancePath)).getText();
				tapechartLogger.info(foundBalance);
			} else {
				break;
			}
		}
		foundBalance = Utility.RemoveDollarandSpaces(driver, foundBalance);
		assertEquals(foundBalance, FolioBalance, "Failed Folio Balance Missmatched");
		test_steps.add("Successfully Verified Folio Balance in ToolTip : " + foundBalance);
		tapechartLogger.info("Successfully Verified Folio Balance in ToolTip : " + foundBalance);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(arivalDatePath)), driver);
		String foundArivalDate = driver.findElement(By.xpath(arivalDatePath)).getText();
		assertEquals(Utility.parseDate(foundArivalDate, "MMM dd, yyyy", "MMM dd, yyyy"), ArivalDate,
				"Failed Arival Date Missmatched");
		test_steps.add("Successfully Verified Arival Date in ToolTip : " + foundArivalDate);
		tapechartLogger.info("Successfully Verified Arival Date in ToolTip : " + foundArivalDate);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(departDatePath)), driver);
		String foundDepartDate = driver.findElement(By.xpath(departDatePath)).getText();
		assertEquals(Utility.parseDate(foundDepartDate, "MMM dd, yyyy", "MMM dd, yyyy"), DepartDate,
				"Failed Depart Date Missmatched");
		test_steps.add("Successfully Verified Depart Date in ToolTip : " + foundDepartDate);
		tapechartLogger.info("Successfully Verified Depart Date in ToolTip : " + foundDepartDate);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(totalNightsPath)), driver);
		String foundNights = driver.findElement(By.xpath(totalNightsPath)).getText();
		assertEquals(foundNights, totalNights, "Failed Total Nights Missmatched");
		test_steps.add("Successfully Verified Total Nights in ToolTip : " + foundNights);
		tapechartLogger.info("Successfully Verified Total Nights in ToolTip : " + foundNights);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(roomClassPath)), driver);
		String foundClass = driver.findElement(By.xpath(roomClassPath)).getText();
		foundClass = foundClass.replace(".", "");
		foundClass = foundClass.replace(".", "");
		foundClass = foundClass.replace(".", "");
		assertTrue(RoomClass.contains(foundClass),
				"Failed RoomClass Name Missmatched Found[" + foundClass + "} expented[" + RoomClass + "]");
		test_steps.add("Successfully Verified RoomClass Name in ToolTip : " + foundClass);
		tapechartLogger.info("Successfully Verified RoomClass Name in ToolTip : " + foundClass);

		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(roomNoPath)), driver);
		String foundRoomNo = driver.findElement(By.xpath(roomNoPath)).getText();
		assertEquals(foundRoomNo, RoomNumber, "Failed Room Number Missmatched");
		test_steps.add("Successfully Verified Room Number in ToolTip : " + foundRoomNo);
		tapechartLogger.info("Successfully Verified Room Number in ToolTip : " + foundRoomNo);

		return test_steps;
	}

	// Added By Adnan: Move Reservation to next Date and Next Room in same Class
	public String MoveReservations(WebDriver driver, String RoomNumber, String RoomClass, String FullName)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		tapechartLogger.info("PTah:" + path);
		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='" + NextRoom
				+ "']//following-sibling::div/span/div[contains(@class,'tapechartdatecell')][3]";

		tapechartLogger.info("To pTah:" + ToPath);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		tapechartLogger.info("Scrolled to the reservation");
		tapechartLogger.info(path);
		WebElement ele = driver.findElement(By.xpath(path));
		Utility.app_logs.info(" Location " + ele.getLocation());
		if (LastRoomClassInTapeChart(driver, RoomClass)) {
			Utility.ScrollToElement(ele, driver);
		} else {
			Utility.ScrollToElement(ele, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
			Utility.app_logs.info("Scrolled Back");
		}
		WebElement From = driver.findElement(By.xpath(path));
		Point Location = From.getLocation();

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Actions builder = new Actions(driver);
		Action resizable = builder.moveToElement(From).clickAndHold().moveToElement(To).release().build();
		// resizable.perform();

		return NextRoom;
	}

	public void CheckInCheckOutDate(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.Select_Arrival_Date, driver);
		TapeChart.Select_Arrival_Date.click();
		TapeChart.Click_Today.click();
		Wait.wait2Second();

	}

	public void EnterAdult(WebDriver driver, String TapeChartAdults) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Enter_Adults_Tapehchart, driver);
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);

	}


	public void SelectRatePlan(WebDriver driver, String RatePlan) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.btnRatePlanSelect, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.btnRatePlanSelect, driver);
		TapeChart.btnRatePlanSelect.click();
		Wait.wait1Second();
		String selectRatePlan = "(//span[text()='Manual Override'])[2]";
		WebElement element = driver.findElement(By.xpath(selectRatePlan));
		element.click();
	}

	public void EnterRateAmount(WebDriver driver, String Amount) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.enterAmount, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.enterAmount, driver);
		TapeChart.enterAmount.sendKeys(Amount);

	}

	public void ClickOnSearch(WebDriver driver) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Click_Search_TapeChart, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.Click_Search_TapeChart, driver);
		TapeChart.Click_Search_TapeChart.click();
	}

	public void ClickOnAvailableRoom(WebDriver driver) throws InterruptedException {

		Elements_TapeChart elements_TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, NewTapeChart.AvailableRoom);
		Wait.explicit_wait_visibilityof_webelement(elements_TapeChart.AvailableRoom, driver);
		Wait.explicit_wait_elementToBeClickable(elements_TapeChart.AvailableRoom, driver);

		Utility.ScrollToElement(elements_TapeChart.AvailableRoom, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait1Second();
		elements_TapeChart.AvailableRoom.click();
		// Wait.WaitForReservationLoading(driver);
	}

	public boolean LastRoomClassInTapeChart(WebDriver driver, String RoomClassName) {
		String xpath = "(//div[contains(@class,'roomclassname')])[last()]";
		WebElement element = driver.findElement(By.xpath(xpath));
		if (element.getText().equals(RoomClassName)) {
			Utility.app_logs.info("Room class is the Last one" + RoomClassName);
			return true;
		}
		return false;

	}

	// Created By Adnan
	public ArrayList<String> TapeChartSearch(WebDriver driver, String CheckinDate, String CheckOutDate, String Adults,
											 String Children, String RatePlan, ArrayList<String> steps) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.TapeChart_CheckIn, driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChart_CheckIn, driver);
		Utility.ScrollToElement(TapeChart.TapeChart_CheckIn, driver);
		TapeChart.TapeChart_CheckIn.clear();
		TapeChart.TapeChart_CheckIn.sendKeys(CheckinDate);
		assertTrue(TapeChart.TapeChart_CheckIn.getAttribute("value").contains(CheckinDate), "Failed: CheckIn");
		steps.add("Enter Checking Date  : " + CheckinDate);
		tapechartLogger.info("Enter Checking Date  : " + CheckinDate);

		Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChart_CheckOut, driver);
		Utility.ScrollToElement(TapeChart.TapeChart_CheckOut, driver);
		TapeChart.TapeChart_CheckOut.clear();
		TapeChart.TapeChart_CheckOut.sendKeys(CheckOutDate);
		assertTrue(TapeChart.TapeChart_CheckOut.getAttribute("value").contains(CheckOutDate), "Failed: CheckOut");
		steps.add("Enter Check Out Date  : " + CheckOutDate);
		tapechartLogger.info("Enter Check Out Date  : " + CheckOutDate);

		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(Adults);
		assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains(Adults), "Failed: Adult set");
		steps.add("Enter Adults  : " + Adults);
		tapechartLogger.info("Enter Adults  : " + Adults);
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(Children);
		steps.add("Enter Children : " + Children);
		tapechartLogger.info("Enter Children : " + Children);
		// Wait.explicit_wait_xpath(OR.Click_Tapechart_RackRate, driver);
		// TapeChart.Click_Tapechart_RackRate.click();
		// String RatePlan_Path =
		// "//div[@id='tapeChartSearch']//ul[@role='menu']//child::span[contains(text(),'"
		// + RatePlan + "')]";
		// Wait.explicit_wait_xpath(RatePlan_Path, driver);
		// WebElement ratePlan = driver.findElement(By.xpath(RatePlan_Path));
		// ratePlan.click();
		steps.add("Selected Rate Plan : " + RatePlan);
		tapechartLogger.info("Selected Rate Plan : " + RatePlan);
		TapeChart.Click_Search_TapeChart.click();
		steps.add("Click Tape Chart search");
		tapechartLogger.info("Click Tape Chart search");
		// try {
		// Wait.WaitForElement(driver, OR.TapeChartAvailableSlot);
		// } catch (Exception e) {
		// TapeChart.Click_Search_TapeChart.click();
		// tapechartLogger.info("Again Click Search");
		// Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot,
		// driver);
		// }
		return steps;
	}

	public void ClickUnassignedRoomClass(WebDriver driver, String RoomClass, ArrayList<String> steps)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String CellPath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']/child::div[last()]// div[contains(text(),'Unassigned')]";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement Unassigned_Reserv_Room = driver.findElement(By.xpath(CellPath));

		if (LastRoomClassInTapeChart(driver, RoomClass)) {
			Utility.ScrollToElement(Unassigned_Reserv_Room, driver);
		} else {
			Utility.ScrollToElement(Unassigned_Reserv_Room, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
			Utility.app_logs.info("Scrolled Back");
		}
		Unassigned_Reserv_Room.click();
		steps.add("Click Unassigned of Room Class : " + RoomClass);
		Utility.app_logs.info("Click Unassigned of Room Class : " + RoomClass);
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.explicit_wait_visibilityof_webelement_120(res.CP_NewReservation_GuestSalutation, driver);
		steps.add("New Reservation Page is opened");
		tapechartLogger.info("New Reservation Page is opened");
	}

	public String GetUnassignedRoomClassCount(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String CellPath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']/child::div[last()]// div[contains(text(),'Unassigned')]";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement Unassigned_Reserv_Room = driver.findElement(By.xpath(CellPath));
		return Unassigned_Reserv_Room.getText().split(" ")[0];
	}

	public void ClickUnassigned(WebDriver driver) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Unassigned_Button, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.Unassigned_Button, driver);
		Utility.ScrollToElement(TapeChart.Unassigned_Button, driver);
		if (!TapeChart.Unassigned_Button.getAttribute("class").contains("active")) {
			TapeChart.Unassigned_Button.click();
		}
		assertTrue(TapeChart.UnassignedColumnHeader.isDisplayed(), "Failed:List not show");
	}

	public void VerifyUnassignedReservation(WebDriver driver, String ReservationName, String ArrivalDate,
											String DepartureDate, String RoomClassAbb) {
		// Verify Reservation

		tapechartLogger.info("Expected Res Name : " + ReservationName);
		tapechartLogger.info("Expected Arival Date : " + ArrivalDate);
		tapechartLogger.info("Expected Departure Date : " + DepartureDate);
		tapechartLogger.info("Expected Room Class Abbreviation : " + RoomClassAbb);
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement_350(TapeChart.UnassignedList_ResName.get(0), driver);
		int LineItem_Size = TapeChart.UnassignedList_ResName.size();
		tapechartLogger.info("Unassigned Reservations List Size : " + LineItem_Size);
		int lineNumber = LineItem_Size - 1;
		tapechartLogger.info("LineNumber : " + lineNumber);
		boolean found = false;
		for (lineNumber = LineItem_Size - 1; lineNumber >= 0; lineNumber--) {
			tapechartLogger.info("LineNumber : " + lineNumber);
			tapechartLogger.info("Res Name : " + TapeChart.UnassignedList_ResName.get(lineNumber).getText());
			tapechartLogger.info("Arival Date : " + TapeChart.UnassignedList_ArrivalDate.get(lineNumber).getText());
			tapechartLogger
					.info("Departure Date : " + TapeChart.UnassignedList_DepartureDate.get(lineNumber).getText());
			tapechartLogger.info(
					"Room Class Abbreviation : " + TapeChart.UnassignedList_RoomClassAbb.get(lineNumber).getText());
			if (TapeChart.UnassignedList_ResName.get(lineNumber).getText().equals(ReservationName)
					&& TapeChart.UnassignedList_ArrivalDate.get(lineNumber).getText().equals(ArrivalDate)
					&& TapeChart.UnassignedList_RoomClassAbb.get(lineNumber).getText().equals(RoomClassAbb)
					&& TapeChart.UnassignedList_DepartureDate.get(lineNumber).getText().equals(DepartureDate)) {

				found = true;
				break;
			}
		}
		if (!found) {
			assertTrue(false, "Failed: Reservation '" + ReservationName + "' Not found.");
		}
	}

	public int GetUnassignedReservationIndex(WebDriver driver, String ReservationName, String ArrivalDate,
											 String DepartureDate, String RoomClassAbb) {
		// Verify Reservation

		tapechartLogger.info("Expected Res Name : " + ReservationName);
		tapechartLogger.info("Expected Arival Date : " + ArrivalDate);
		tapechartLogger.info("Expected Departure Date : " + DepartureDate);
		tapechartLogger.info("Expected Room Class Abbreviation : " + RoomClassAbb);
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement_350(TapeChart.UnassignedList_ResName.get(0), driver);
		int LineItem_Size = TapeChart.UnassignedList_ResName.size();
		tapechartLogger.info("Unassigned Reservations List Size : " + LineItem_Size);
		int lineNumber = LineItem_Size - 1;
		tapechartLogger.info("LineNumber : " + lineNumber);
		boolean found = false;
		for (lineNumber = LineItem_Size - 1; lineNumber >= 0; lineNumber--) {
			tapechartLogger.info("LineNumber : " + lineNumber);
			tapechartLogger.info("Res Name : " + TapeChart.UnassignedList_ResName.get(lineNumber).getText());
			tapechartLogger.info("Arival Date : " + TapeChart.UnassignedList_ArrivalDate.get(lineNumber).getText());
			tapechartLogger
					.info("Departure Date : " + TapeChart.UnassignedList_DepartureDate.get(lineNumber).getText());
			tapechartLogger.info(
					"Room Class Abbreviation : " + TapeChart.UnassignedList_RoomClassAbb.get(lineNumber).getText());
			if (TapeChart.UnassignedList_ResName.get(lineNumber).getText().equals(ReservationName)
					&& TapeChart.UnassignedList_ArrivalDate.get(lineNumber).getText().equals(ArrivalDate)
					&& TapeChart.UnassignedList_RoomClassAbb.get(lineNumber).getText().equals(RoomClassAbb)
					&& TapeChart.UnassignedList_DepartureDate.get(lineNumber).getText().equals(DepartureDate)) {

				found = true;
				break;
			}
		}
		if (!found) {
			assertTrue(false, "Failed: Reservation '" + ReservationName + "' Not found.");
		}
		return lineNumber + 1;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <GetandVerifyUnassignedReservationNumber> ' Description:
	 * <Get Unassigned Reservation Number OR Verify Unassigned Reservation
	 * Number From the Unassigned Button Unassigned Column Header and From the
	 * size of Unassigned Reservation List> ' Input parameters: <boolean need to
	 * verify or not> ' Return value: <String> ' Created By: <Adhnan Ghaffar> '
	 * Created On: <04/27/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String GetandVerifyUnassignedReservationNumber(WebDriver driver, boolean verify)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String number = null;
		Wait.WaitForElement(driver, OR.Unassigned_Button);
//		Wait.waitForElementToBeInVisibile(By.xpath(OR.Unassigned_Button), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Unassigned_Button), driver);
		Utility.ScrollToElement(TapeChart.Unassigned_Button, driver);
		number = TapeChart.Unassigned_Button.getText();
		number = number.split(" ")[1];
		number = number.replaceAll("[()]", "");
		if (verify) {
			if (!number.equals("0")) {
				int size = driver.findElements(By.xpath(OR.UnassignedList)).size();
				assertEquals(Integer.toString(size), number,
						"Failed: Number of Unassigned Reservations missmatched on Unassigned Button");
			}
			assertEquals(TapeChart.UnassignedColumnHeader.getText(), "Unassigned (" + number + ")",
					"Failed: Number of Unassigned Reservations missmatched on Unassigned Column Header");
		}
		return number;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyReservationName_ReservationUpdate> ' Description:
	 * <Verification reservation number in update popup> ' Input parameters: <
	 * ',' separated parameters type> ' Return value: <ArrayList> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <03/30/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyReservationNameReservationUpdate(WebDriver driver, String ResName) {
		ArrayList<String> test_steps = new ArrayList<>();
		String path = "//*[@id='ReservationUpdate']//h4[contains(@data-bind,'ReservationName')]";
		WebElement element = driver.findElement(By.xpath(path));
		Wait.explicit_wait_visibilityof_webelement_120(element, driver);
		Wait.explicit_wait_elementToBeClickable(element, driver);
		String foundName = element.getText();
		assertEquals(foundName, ResName, "Failed Reservation Name Mismatched");
		tapechartLogger.info("Successfully Verified Reservation Name in Reservation Update Popup");
		test_steps.add("Successfully Verified Reservation Name in Reservation Update Popup");
		return test_steps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyCheckInTimeReservationUpdate> ' Description:
	 * <verify check in date> ' Input parameters: <check in date> ' Return
	 * value: <checkInDate> ' Created By: <Adnan Ghaffar> ' Created On:
	 * <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyCheckInTimeReservationUpdate(WebDriver driver, String checkInDate) {
		ArrayList<String> testSteps = new ArrayList<>();
		String path = "//*[@id='ReservationUpdate']//span[text()='Check In']/following-sibling::div//div[contains(@class,'datetextNoChange')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		String foundDate = driver.findElement(By.xpath(path)).getText();
		assertEquals(Utility.parseDate(foundDate, "MMM dd, yyyy", "MMM dd, yyyy"), checkInDate,
				"Failed Check In Date Mismatched");
		tapechartLogger.info("Successfully Verified Check In Date in Reservation Update Popup");
		testSteps.add("Successfully Verified Check In Date in Reservation Update Popup");

		return testSteps;
	}

	public ArrayList<String> verifyCheckInTime_ReservationUpdate(WebDriver driver, String CheckInDate, String wasDate) {
		ArrayList<String> test_steps = new ArrayList<>();
		String path = "//*[@id='ReservationUpdate']//span[text()='Check In']/following-sibling::div/div[contains(@data-bind,'NewDateArrivedDisplay')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		String foundDate = driver.findElement(By.xpath(path)).getText();
		tapechartLogger.info("CheckIn Found Date " + foundDate);
		assertEquals(Utility.parseDate(foundDate, "MMM dd, yyyy", "MMM dd, yyyy"), CheckInDate,
				"Failed Check In Date Mismatched");
		tapechartLogger.info("Successfully Verified Check In Date in Reservation Update Popup");
		test_steps.add("Successfully Verified Check In Date in Reservation Update Popup");

		String path2 = "//*[@id='ReservationUpdate']//span[text()='Check In']/following-sibling::div//div[contains(@data-bind,'OriginalDateArrivedDisplay')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path2)), driver);
		String foundDate2 = driver.findElement(By.xpath(path2)).getText();
		tapechartLogger.info("Before Checkin Found Date " + foundDate2);
		foundDate2 = foundDate2.substring(4);
		assertEquals(Utility.parseDate(foundDate2, "MMM dd, yyyy", "MMM dd, yyyy"), wasDate,
				"Failed Check In Before Date Mismatched");
		tapechartLogger.info("Successfully Verified Check In Before Date in Reservation Update Popup");
		test_steps.add("Successfully Verified Check In Before Date in Reservation Update Popup");

		return test_steps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyCheckOutTimeReservationUpdate> ' Description:
	 * <verify check out date> ' Input parameters: <checkOutDate, pastDate> '
	 * Return value: <Array List> ' Created By: <Adnan Ghaffar> ' Created On:
	 * <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyCheckOutTimeReservationUpdate(WebDriver driver, String CheckOutDate,
																 String pastDate) {
		ArrayList<String> testSteps = new ArrayList<>();
		String updatedCheckoutPath = "//*[@id='ReservationUpdate']//span[text()='Check Out']/following-sibling::div//div[contains(@data-bind,'NewDateDepartedDisplay')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(updatedCheckoutPath)), driver);
		String foundDate = driver.findElement(By.xpath(updatedCheckoutPath)).getText();
		assertEquals(Utility.parseDate(foundDate, "MMM dd, yyyy", "MMM dd, yyyy"), CheckOutDate,
				"Failed Check Out Date Mismatched");
		tapechartLogger.info("Successfully Verified Check Out Date in Reservation Update Popup");
		testSteps.add("Successfully Verified Check Out Date in Reservation Update Popup");

		String previouseCheckoutPath = "//*[@id='ReservationUpdate']//span[text()='Check Out']/following-sibling::div//div[contains(@data-bind,'OriginalDateDepartedDisplay')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(previouseCheckoutPath)), driver);
		String newCheckoutDate = driver.findElement(By.xpath(previouseCheckoutPath)).getText();
		newCheckoutDate = newCheckoutDate.substring(4);
		assertEquals(Utility.parseDate(newCheckoutDate, "MMM dd, yyyy", "MMM dd, yyyy"), pastDate,
				"Failed Check Out Was Date Mismatched");
		tapechartLogger.info("Successfully Verified First Check Out Date in Reservation Update Popup");
		testSteps.add("Successfully Verified First Check Out Date in Reservation Update Popup");

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyRoomClassReservationUpdate> ' Description: <verify
	 * updated reservation in tape chart> ' Input parameters: <roomClass> '
	 * Return value: <Array List> ' Created By: <Adnan Ghaffar> ' Created On:
	 * <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyRoomClassReservationUpdate(WebDriver driver, String roomClass) {
		ArrayList<String> test_steps = new ArrayList<>();
		String path = "//*[@id='ReservationUpdate']//span[text()='Room Class']/following-sibling::div//div[contains(@data-bind,'RoomClassChanged')][1]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		String foundName = driver.findElement(By.xpath(path)).getText();
		foundName = foundName.replace(".", "");
		foundName = foundName.replace(".", "");
		foundName = foundName.replace(".", "");
		assertTrue(roomClass.contains(foundName),
				"Failed RoomClass Mismatched expected[" + roomClass + "] but found[" + foundName + "]");
		tapechartLogger.info("Successfully Verified RoomClass Name in Reservation Update Popup");
		test_steps.add("Successfully Verified RoomClass Name in Reservation Update Popup");

		return test_steps;
	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyTripTotalReservationUpdate> ' Description: <verify
	 * tool tip> ' Input parameters: <tripTotal, wasTripTotal> ' Return value:
	 * <Array List> ' Created By: <Adnan Ghaffar> ' Created On: <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyTripTotalReservationUpdate(WebDriver driver, String tripTotal, String wasTripTotal) {
		ArrayList<String> testSteps = new ArrayList<>();
		String path = "//*[@id='ReservationUpdate']//span[text()='Trip Total']/following-sibling::div//div[contains(@data-bind,'NewFolioTotal')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		String foundTripTotal = driver.findElement(By.xpath(path)).getText();
		foundTripTotal = Utility.convertDollarToNormalAmount(driver, foundTripTotal).trim();
		assertEquals(Float.parseFloat(foundTripTotal), Float.parseFloat(tripTotal), "Failed TripTotal Mismatched");
		tapechartLogger.info("Successfully Verified TripTotal in Reservation Update Popup");
		testSteps.add("Successfully Verified TripTotal in Reservation Update Popup");

		String pastToolTripPath = "//*[@id='ReservationUpdate']//span[text()='Trip Total']/following-sibling::div//span[contains(@data-bind,'StartingFolioTotalDisplay')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(pastToolTripPath)), driver);
		String foundWasTripTotal = driver.findElement(By.xpath(pastToolTripPath)).getText();
		foundWasTripTotal = Utility.convertDollarToNormalAmount(driver, foundWasTripTotal).trim();
		assertEquals(Float.parseFloat(foundWasTripTotal), Float.parseFloat(wasTripTotal),
				"Failed TripTotal was Mismatched");
		tapechartLogger.info("Successfully Verified Trip Total before Extend in Reservation Update Popup");
		test_steps.add("Successfully Verified Trip Total before Extend in Reservation Update Popup");
		return test_steps;
	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyBalanceDueReservationUpdate> ' Description: <verify
	 * total balance> ' Input parameters: <balanceDue, pastBalanceDue> ' Return
	 * value: <Array List> ' Created By: <Adnan Ghaffar> ' Created On:
	 * <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyBalanceDueReservationUpdate(WebDriver driver, String balanceDue,
															   String pastBalanceDue) {
		ArrayList<String> testSteps = new ArrayList<>();
		String path = "//*[@id='ReservationUpdate']//span[text()='Balance Due']/following-sibling::div//div[contains(@data-bind,'NewFolioBalance')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);
		String foundBalaTotal = driver.findElement(By.xpath(path)).getText();
		foundBalaTotal = Utility.convertDollarToNormalAmount(driver, foundBalaTotal).trim();
		assertEquals(Float.parseFloat(foundBalaTotal), Float.parseFloat(balanceDue), "Failed Balance Mismatched");
		tapechartLogger.info("Successfully Verified Balance in Reservation Update Popup");
		testSteps.add("Successfully Verified Balance in Reservation Update Popup");

		String beforeExtendBalancePath = "//*[@id='ReservationUpdate']//span[text()='Balance Due']/following-sibling::div//span[contains(@data-bind,'StartingFolioBalanceDisplay')]";
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(beforeExtendBalancePath)), driver);
		String foundBalaTotalBeforeExtend = driver.findElement(By.xpath(beforeExtendBalancePath)).getText();
		foundBalaTotalBeforeExtend = Utility.convertDollarToNormalAmount(driver, foundBalaTotalBeforeExtend).trim();
		assertEquals(Float.parseFloat(foundBalaTotalBeforeExtend), Float.parseFloat(pastBalanceDue),
				"Failed Balance WAS Mismatched");
		tapechartLogger.info("Successfully Verified Balance before Extend in Reservation Update Popup");
		testSteps.add("Successfully Verified Balance before Extend in Reservation Update Popup");
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <clickConfirm_ReservationUpdate> ' Description: <click on
	 * confirm change button in popup in tape chart> ' Input parameters: < room
	 * number, room class name> ' Return value: <ArrayList> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <03/30/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickConfirmReservationUpdate(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement_120(TapeChart.ConfirmChangeReservation_Button, driver);
		Utility.ScrollToElement(TapeChart.ConfirmChangeReservation_Button, driver);
		TapeChart.ConfirmChangeReservation_Button.click();
		tapechartLogger.info("Confirm Change Clicked in Reservation Update Popup");
		testSteps.add("Confirm Change Clicked in Reservation Update Popup");
		// verify toaster message here
		try {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.Toaster_Message, driver);
			String message = TapeChart.Toaster_Message.getText();
			assertEquals(message, "Reservation has been updated successfully", "Reservation does not expand");
			tapechartLogger.info("Reservation Update Toaster Message Appear");
			testSteps.add("Get toaster message: " + message);
		} catch (Exception e) {
			System.err.println("Toaster not Displayed");
			tapechartLogger.info("Reservation Update Toaster Message not Appear");
			testSteps.add("Reservation Update Toaster Message not Appear");
		}
		return testSteps;
	}

	public void verifyReducedReservation(WebDriver driver, String RoomNumber, String RoomClass, String FullName,
										 int preWidth) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path2 = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath(path2))).perform();

		WebElement ele2 = driver.findElement(By.xpath(path2));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		// Wait.wait2Second();
		int divWidth2 = ele2.getSize().getWidth();
		tapechartLogger.info("Post Width : " + divWidth2);
		assertTrue(divWidth2 < preWidth, "Failed : Reservation not Reduced");

	}

	public void verifyExtendedReservation(WebDriver driver, String RoomNumber, String RoomClass, String FullName,
										  int preWidth) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path2 = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		Actions actions = new Actions(driver);
		Wait.explicit_wait_visibilityof_webelement(driver.findElement(By.xpath(path2)), driver);
		actions.moveToElement(driver.findElement(By.xpath(path2))).perform();

		// int preWidth = 0;
		WebElement ele2 = driver.findElement(By.xpath(path2));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");

		// Wait.wait2Second();
		int divWidth2 = ele2.getSize().getWidth();
		tapechartLogger.info("Post Width : " + divWidth2);
		assertTrue(divWidth2 > preWidth, "Failed : Reservation not Extended");

	}

	public int reduceReservation(WebDriver driver, String RoomNumber, String RoomClass, String FullName)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String path2 = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		// Actions actions = new Actions(driver);
		// actions.moveToElement(driver.findElement(By.xpath(path2))).perform();
		String path = "(//div[text()='" + RoomClass
				+ "' and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[text()='" + FullName
				+ "']//following::span[@class='ir-tc-rightResHandle'])[1]";
		// "//div[text()='"+FullName+"']/following::span[@class='ir-tc-rightResHandle']";
		tapechartLogger.info(path);
		Wait.explicit_wait_xpath(path, driver);

		WebElement ele2 = driver.findElement(By.xpath(path2));
		WebElement ele = driver.findElement(By.xpath(path));
		Utility.app_logs.info(" Location " + ele.getLocation());
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (LastRoomClassInTapeChart(driver, RoomClass)) {
			Utility.ScrollToElement(ele, driver);
			jse.executeScript("window.scrollBy(0,-200)");

		} else {
			Utility.ScrollToElement(ele, driver);
			jse.executeScript("window.scrollBy(0,-350)");
		}
		Utility.app_logs.info(" Location " + ele.getLocation());
		int preWidth = 0;
		Wait.wait2Second();
		int divWidth = ele2.getSize().getWidth();
		tapechartLogger.info("Pre Width : " + divWidth);
		preWidth = divWidth;
		Actions builder = new Actions(driver);

		Action resizable = builder.moveToElement(ele).clickAndHold().moveByOffset(-divWidth, 0).release().build();
		resizable.perform();

		Wait.explicit_wait_visibilityof_webelement_120(TapeChart.CheckIn, driver);
		assertEquals(TapeChart.CheckIn.getText(), "Check In", "Check in doesnot display");
		assertEquals(TapeChart.CheckOut.getText(), "Check Out", "Check out does not display");
		assertEquals(TapeChart.TripTotal.getText(), "Trip Total", "Trip total does not display");
		assertEquals(TapeChart.BalanceDue.getText(), "Balance Due", "Balance due does not display");
		return preWidth;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <tapeChartSearch> ' Description: <The method will enter
	 * the search information of Tape chartand click search> ' Input parameters:
	 * <WebDriver , String , String , String ,String , String > ' Return value:
	 * <ArrayList> ' Created By: <Adhnan Ghaffar> ' Created On: <05/11/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> tapeChartSearch(WebDriver driver, String CheckinDate, String CheckOutDate, String Adults,
											 String Children, String RatePlan, ArrayList<String> steps) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitUntilPresenceOfElementLocated(OR.TapeChart_CheckIn, driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChart_CheckIn, driver);
		Utility.ScrollToElement(TapeChart.TapeChart_CheckIn, driver);
		TapeChart.TapeChart_CheckIn.clear();
		TapeChart.TapeChart_CheckIn.sendKeys(CheckinDate);
		assertTrue(TapeChart.TapeChart_CheckIn.getAttribute("value").contains(CheckinDate), "Failed: CheckIn");
		steps.add("Enter Checkin Date  : " + CheckinDate);
		tapechartLogger.info("Enter Checkin Date  : " + CheckinDate);

		Wait.explicit_wait_visibilityof_webelement(TapeChart.TAPECHART_CHECKOUT, driver);
		Utility.ScrollToElement(TapeChart.TAPECHART_CHECKOUT, driver);
		TapeChart.TAPECHART_CHECKOUT.clear();
		TapeChart.TAPECHART_CHECKOUT.sendKeys(CheckOutDate);
		assertTrue(TapeChart.TAPECHART_CHECKOUT.getAttribute("value").contains(CheckOutDate), "Failed: CheckOut");
		steps.add("Enter Check Out Date  : " + CheckOutDate);
		tapechartLogger.info("Enter Check Out Date  : " + CheckOutDate);

		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(Adults);
		assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains(Adults), "Failed: Adult set");
		steps.add("Enter Adults  : " + Adults);
		tapechartLogger.info("Enter Adults  : " + Adults);
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(Children);
		steps.add("Enter Children : " + Children);
		tapechartLogger.info("Enter Children : " + Children);
		Wait.explicit_wait_xpath(OR.Click_Tapechart_RackRate, driver);
		TapeChart.Click_Tapechart_RackRate.click();
		String RatePlan_Path = "//div[@id='tapeChartSearch']//ul[@role='menu']//child::span[contains(text(),'"
				+ RatePlan + "')]";
		Wait.explicit_wait_xpath(RatePlan_Path, driver);
		WebElement ratePlan = driver.findElement(By.xpath(RatePlan_Path));
		ratePlan.click();
		steps.add("Selected Rate Plan : " + RatePlan);
		tapechartLogger.info("Selected Rate Plan : " + RatePlan);
		Wait.wait10Second();
		TapeChart.Click_Search_TapeChart.click();
		steps.add("Click Tape Chart search");
		tapechartLogger.info("Click Tape Chart search");
		Wait.wait10Second();
		try {
			Wait.WaitForElement(driver, OR.TapeChartAvailableSlot);
		} catch (Exception e) {
			TapeChart.Click_Search_TapeChart.click();
			tapechartLogger.info("Again Click Search");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot, driver);
		}
		return steps;
	}
	
	public ArrayList<String> tapeChartSearch1(WebDriver driver, String CheckinDate, String CheckOutDate, String Adults,
			 String Children, String RatePlan, ArrayList<String> steps) throws InterruptedException, ParseException {

			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			Wait.waitUntilPresenceOfElementLocated(OR.TapeChart_CheckIn, driver);
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChart_CheckIn, driver);
			Utility.ScrollToElement(TapeChart.TapeChart_CheckIn, driver);
			TapeChart.TapeChart_CheckIn.clear();
			TapeChart.TapeChart_CheckIn.sendKeys(CheckinDate);
			assertTrue(TapeChart.TapeChart_CheckIn.getAttribute("value").contains(CheckinDate), "Failed: CheckIn");
			steps.add("Enter Checkin Date  : " + CheckinDate);
			tapechartLogger.info("Enter Checkin Date  : " + CheckinDate);
			
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TAPECHART_CHECKOUT, driver);
			Utility.ScrollToElement(TapeChart.TAPECHART_CHECKOUT, driver);
			TapeChart.TAPECHART_CHECKOUT.clear();
			TapeChart.TAPECHART_CHECKOUT.sendKeys(CheckOutDate);
			assertTrue(TapeChart.TAPECHART_CHECKOUT.getAttribute("value").contains(CheckOutDate), "Failed: CheckOut");
			steps.add("Enter Check Out Date  : " + CheckOutDate);
			tapechartLogger.info("Enter Check Out Date  : " + CheckOutDate);
			
			TapeChart.Enter_Adults_Tapehchart.clear();
			TapeChart.Enter_Adults_Tapehchart.sendKeys(Adults);
			assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains(Adults), "Failed: Adult set");
			steps.add("Enter Adults  : " + Adults);
			tapechartLogger.info("Enter Adults  : " + Adults);
			TapeChart.Enter_Children_Tapechart.clear();
			TapeChart.Enter_Children_Tapechart.sendKeys(Children);
			steps.add("Enter Children : " + Children);
			tapechartLogger.info("Enter Children : " + Children);
			Wait.explicit_wait_xpath(OR.Click_Tapechart_RatePlan, driver);
			driver.findElement(By.xpath(OR.Click_Tapechart_RatePlan)).click();
			String RatePlan_Path = "//div[@id='tapeChartSearch']//ul[@role='menu']//child::span[contains(text(),'"
			+ RatePlan + "')]";
			Wait.explicit_wait_xpath(RatePlan_Path, driver);
			WebElement ratePlan = driver.findElement(By.xpath(RatePlan_Path));
			ratePlan.click();
			steps.add("Selected Rate Plan : " + RatePlan);
			tapechartLogger.info("Selected Rate Plan : " + RatePlan);
			Wait.wait10Second();
			TapeChart.Click_Search_TapeChart.click();
			steps.add("Click Tape Chart search");
			tapechartLogger.info("Click Tape Chart search");
			Wait.wait10Second();
			try {
			Wait.WaitForElement(driver, OR.TapeChartAvailableSlot);
			} catch (Exception e) {
			TapeChart.Click_Search_TapeChart.click();
			tapechartLogger.info("Again Click Search");
			Wait.explicit_wait_visibilityof_webelement(TapeChart.TapeChartAvailableSlot, driver);
			}
			return steps;
}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <clickAvailableSlot> ' Description: <click on available
	 * slot after searching tape chart> ' Input parameters:(RoomClass name ) '
	 * Return value: void ' Created By: <Adnan Ghaffar> ' CreatedOn:
	 * <05/11/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void clickAvailableSlot(WebDriver driver, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Elements_CPReservation CPReservation = new Elements_CPReservation(driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		tapechartLogger.info(CellPath);
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		try {
			Utility.ScrollToElement(AvailableSlot, driver);
			try {
				AvailableSlot.click();
			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				AvailableSlot.click();
				tapechartLogger.info("Scrol back 300");

			}
			Wait.WaitForElement(driver, OR.ReservationDetailPage);
		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.WaitForElement(driver, OR.Rules_Broken_popup);
				Wait.waitForElementToBeClickable(By.xpath(OR.Click_Book_icon_Tapechart), driver);
				Utility.ScrollToElement(TapeChart.Click_Book_icon_Tapechart, driver);
				TapeChart.Click_Book_icon_Tapechart.click();
			} else {
				tapechartLogger.info("Rules are not broken");
			}
		}
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestSalutation);
		assertTrue(CPReservation.CP_NewReservation_GuestSalutation.isDisplayed(), "Reservation Page didn't load");

	}

	public HashMap<String, String> getTapChartRates(WebDriver driver, String roomClassAbbrivation, String checkInDate,
													String checkOutDate, String adults, String children, String ratePlan, String DateFormatType,
													int numberofDays) throws ParseException, InterruptedException {
		HashMap<String, String> ratesAgainstDate = new HashMap<String, String>();
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);

		ArrayList<String> toastMessage = new ArrayList<String>();
		String dateStartFrom = "";
		String dateEndAt = "";
		if (DateFormatType.equals("USA")) {

			dateStartFrom = checkInDate.split("/")[1];
			dateEndAt = checkOutDate.split("/")[1];

		} else if (DateFormatType.equals("International")) {

			dateStartFrom = checkInDate.split("/")[0];
			dateEndAt = checkOutDate.split("/")[0];

		}

		if (tapeChart.getToastMessage.size() > 0) {
			if (tapeChart.getToastMessage.get(1).isDisplayed()) {
				String message = tapeChart.getToastMessage.get(1).getText();
				toastMessage.add(message);
				tapechartLogger.info(message);
			}
		} else {
			String ratePath = "//div[contains(text(),'" + roomClassAbbrivation
					+ "')]//following-sibling::div//div[@class='tapechartdatecell']";
			Utility.ScrollToElement(driver.findElement(By.xpath(ratePath)), driver);
			List<WebElement> rateList = driver.findElements(By.xpath(ratePath));
			tapechartLogger.info("Size of DategetList:" + tapeChart.getDateAndDayList.size());
			for (int i = 0; i < tapeChart.getDateAndDayList.size(); i++) {

				if (tapeChart.getDateAndDayList.get(i).getText().contains(dateStartFrom)) {

					for (int j = i; j < tapeChart.getDateAndDayList.size(); j++) {
						tapechartLogger.info("Date Added:" + tapeChart.getDateAndDayList.get(j).getText());

						tapechartLogger.info("Rate Added:" + rateList.get(j).getText());
						ratesAgainstDate.put(tapeChart.getDateAndDayList.get(j).getText(),
								Utility.convertDollarToNormalAmount(driver, rateList.get(j).getText()));

						if (tapeChart.getDateAndDayList.get(j).getText().contains(dateEndAt)) {
							break;
						}

					}
				}
			}
		}

		return ratesAgainstDate;

	}

	public ArrayList<String> getDragAndDropReservationPopUpDetails(WebDriver driver) {
		ArrayList<String> detailList = new ArrayList<String>();

		Elements_TapeChart element = new Elements_TapeChart(driver);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.GET_CHECK_IN_TEXT), driver);

		String getTripTotalPre = element.getTripTotalPre.getText();
		String getTripTotalPost = element.getTripTotalPost.getText();
		getTripTotalPre = Utility.convertDollarToNormalAmount(driver, getTripTotalPre);
		detailList.add(getTripTotalPre);
		tapechartLogger.info(getTripTotalPre);

		try {
			if (element.getTripTotalPost.isDisplayed()) {
				getTripTotalPost = getTripTotalPost.split(" ")[1];
				getTripTotalPost = Utility.convertDollarToNormalAmount(driver, getTripTotalPost);
				detailList.add(getTripTotalPost);
				tapechartLogger.info(getTripTotalPost);
			}
		} catch (Exception e) {
			detailList.add("");
			tapechartLogger.info("Null");
		}

		return detailList;
	}

	public ArrayList<String> selectRateOptionsToApplyRate(WebDriver driver, int index) {
		ArrayList<String> testSteps = new ArrayList<String>();

		Elements_TapeChart element = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, NewTapeChart.CHANGE_RATE_APPLIED_DROPDOWN_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.CHANGE_RATE_APPLIED_DROPDOWN_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.CHANGE_RATE_APPLIED_DROPDOWN_BUTTON), driver);
		element.changeRateAppliedDropDownButton.click();
		tapechartLogger.info(" DropDown Button Clicked");
		testSteps.add("DropDown Button Clicked");

		element.changeRateAppliedDropDownList.get(index).click();
		Wait.WaitForElement(driver, NewTapeChart.CHANGE_RATE_APPLIED_SELECTEC_OPTION);
		tapechartLogger.info("Rate Option Selected:" + element.CHANGE_RATE_APPLIED_SELECTEC_OPTION.getText());

		testSteps.add("Rate Option Selected:" + element.CHANGE_RATE_APPLIED_SELECTEC_OPTION.getText());
		return testSteps;

	}

	public ArrayList<String> verifyRateUpdate(WebDriver driver, int numberofDays, String nightlyRate,
											  String updateRatesType, String rateCurrencyType, String rateChangeValue,
											  ArrayList<String> beforeUpdateRateValues, ArrayList<String> afterUpdateRateValues) {
		ArrayList<String> testSteps = new ArrayList<String>();

		int rateExpected = 0;

		for (int i = 0; i <= numberofDays; i++) {

			tapechartLogger.info("Nighlty Rate Plan Expected In RatesGrid:" + nightlyRate);
			testSteps.add("Nighlty Rate Plan Expected In RatesGrid:" + nightlyRate);
			String rateFound = afterUpdateRateValues.get(i);
			tapechartLogger.info("Nighlty Rate Plan Found In RatesGrid:" + rateFound);
			testSteps.add("Nighlty Rate Plan Found In RatesGrid:" + rateFound);
			if (updateRatesType.equalsIgnoreCase("EnterNewRate")) {
				assertEquals(afterUpdateRateValues.get(i).equals(nightlyRate.split("\\.")[0]), true,
						"Failed: Nightly Rate didn't update");

			}

			// Increase/Decrease Verification

			else if (updateRatesType.equalsIgnoreCase("Increase")) {

				if (rateCurrencyType.equals(Utility.clientCurrency)) {

					// Parse and the add the rateChangeValue
					rateExpected = Integer.parseInt(beforeUpdateRateValues.get(i)) + Integer.parseInt(rateChangeValue);

					tapechartLogger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
					testSteps.add(
							"Nighlty Rate Plan Expected In Reservation Find Rooms:" + beforeUpdateRateValues.get(i));
					// Need to remove dollar sign here
					assertEquals(rateFound.equals(Integer.toString(rateExpected)), true,
							"Failed: Nightly Rate didn't update");
				}
				// Increase is in Percentage
				else {

					// Parse and the add the rateChangeValue
					double percentageIncrease = (Integer.parseInt(beforeUpdateRateValues.get(i))
							* Integer.parseInt(rateChangeValue)) / 100;
					percentageIncrease = Math.ceil(percentageIncrease);
					rateExpected = Integer.parseInt(beforeUpdateRateValues.get(i)) + ((int) percentageIncrease);
					tapechartLogger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
					testSteps.add("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);

					assertEquals(rateFound.equals(Integer.toString(rateExpected)), true,
							"Failed: Nightly Rate didn't update");

				}
			} else if (updateRatesType.equalsIgnoreCase("Decrease")) {

				if (rateCurrencyType.equals(Utility.clientCurrency)) {

					// Parse and the subtract the
					// rateChangeValue

					rateExpected = Integer.parseInt(beforeUpdateRateValues.get(i)) - Integer.parseInt(rateChangeValue);

					tapechartLogger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
					testSteps.add(
							"Nighlty Rate Plan Expected In Reservation Find Rooms:" + beforeUpdateRateValues.get(i));
					// Need to remove dollar sign here
					tapechartLogger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
					testSteps.add("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);

					assertEquals(rateFound.equals(Integer.toString(rateExpected)), true,
							"Failed: Nightly Rate didn't update");
				}

				// Decrease is in Percentage
				else {

					// Parse and the subtract the rateChangeValue
					double percentageDecrease = (Integer.parseInt(beforeUpdateRateValues.get(i))
							* Integer.parseInt(rateChangeValue)) / 100;
					percentageDecrease = Math.ceil(percentageDecrease);
					rateExpected = Integer.parseInt(beforeUpdateRateValues.get(i)) - (int) percentageDecrease;
					assertEquals(rateFound.equals(Integer.toString(rateExpected)), true,
							"Failed: Nightly Rate didn't update");

				}
			} else {
				assertEquals(afterUpdateRateValues.get(i).equals(beforeUpdateRateValues.get(i)), true,
						"Failed: Nightly Rate didn't update");
			}
			tapechartLogger.info("Verified");
			testSteps.add("Verified");

		}
		return testSteps;

	}

	public ArrayList<String> verifyRateChangeInMoveExtendPopUp(WebDriver driver, String changeType,
															   ArrayList<String> moveFromRate, ArrayList<String> moveToRate, int taxApplied) {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_TapeChart element = new Elements_TapeChart(driver);

		ArrayList<String> rateFound = new ArrayList<>();
		String rateToVerify = "0.0";
		// Loop to select each rate change applied option 1-NoRateChange 2-
		// Recalculate Rate 3-Change Rate for new Date

		for (int i = 0; i < element.changeRateAppliedDropDownList.size(); i++) {
			rateToVerify = "0.0";
			selectRateOptionsToApplyRate(driver, i);
			rateFound = getDragAndDropReservationPopUpDetails(driver);
			// For 0 Index means-> No Rate Changes Applied Values will be
			// same for every scenario
			if (i == 0) {
				for (int pointerOfListFrom = 0; pointerOfListFrom < moveFromRate.size(); pointerOfListFrom++) {
					rateToVerify = (Double.toString(Double.parseDouble(rateToVerify) + (Double.parseDouble(moveFromRate.get(pointerOfListFrom))
							+ ((Double.parseDouble(moveFromRate.get(pointerOfListFrom)) * taxApplied) / 100))));
				}

			} else {
				if (changeType.equalsIgnoreCase("Move") || changeType.equalsIgnoreCase("Extend")) {
					// Reservation extend to x days. It will loop to each day
					// rate and apply tax to it and add it
					for (int j = 0; j < moveToRate.size(); j++) {
						rateToVerify = (Double.toString(Double.parseDouble(rateToVerify) + (Double.parseDouble(moveToRate.get(j))
								+ ((Double.parseDouble(moveToRate.get(j)) * taxApplied) / 100))));
					}
				}

				if (changeType.equalsIgnoreCase("Reduce")) {
					// Reservation extend to x days. It will loop to each day
					// rate and apply tax to it and add it
					for (int j = 0; j < moveToRate.size(); j++) {
						rateToVerify = (Double.toString(Double.parseDouble(rateToVerify) - (Double.parseDouble(moveToRate.get(j))
								+ ((Double.parseDouble(moveToRate.get(j)) * taxApplied) / 100))));

					}
					rateToVerify = Double.toString(Math.abs(Double.parseDouble(rateToVerify)));
				}

			}
			if (rateFound.get(0).contains(rateToVerify)) {
				tapechartLogger.info("Verified No Rate Change Value:" + rateFound.get(0));
				testSteps.add("Verified No Rate Change Value:" + rateFound.get(0));
			} else {
				tapechartLogger.info("Rate Change Value did not match:" + rateFound.get(0));
				testSteps.add("Assertion Rate Change Value did not match:" + rateFound.get(0));
			}
		}

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <Move_Reservations> ' Description: <Move one reservation
	 * from one room number to other> ' Input parameters: < room number, room
	 * class name> ' Return value: <ArrayList> ' Created By: <Farhan Ghaffar> '
	 * Created On: <03/30/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String Move_Reservations(WebDriver driver, String RoomNumber, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);

		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		tapechartLogger.info("PTah:" + path);

		int nextRoom = Integer.parseInt(RoomNumber) + 1;
		String NextRoom = Integer.toString(nextRoom);
		String ToPath = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='" + NextRoom
				+ "']//following-sibling::div/span/div[contains(@class,'tapechartdatecell')][3]";

		tapechartLogger.info("To pTah:" + ToPath);
		WebElement From = driver.findElement(By.xpath(path));
		Wait.explicit_wait_visibilityof_webelement(From, driver);
		Wait.explicit_wait_elementToBeClickable(From, driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement To = driver.findElement(By.xpath(ToPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		act.dragAndDrop(From, To).build().perform();

		Actions builder = new Actions(driver);
		Action resizable = builder.moveToElement(From).clickAndHold().moveToElement(To).release().build();
		// resizable.perform();

		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		return NextRoom;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <verifyReservationName_ReservationUpdate> ' Description:
	 * <Verification reservation number in update popup> ' Input parameters: <
	 * ',' separated parameters type> ' Return value: <ArrayList> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <03/30/2020>
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyReservationName_ReservationUpdate(WebDriver driver, String ResName) {
		ArrayList<String> test_steps = new ArrayList<>();
		String path = "//*[@id='ReservationUpdate']//h4[contains(@data-bind,'ReservationName')]";
		WebElement element = driver.findElement(By.xpath(path));
		Wait.explicit_wait_visibilityof_webelement_120(element, driver);
		Wait.explicit_wait_elementToBeClickable(element, driver);
		String foundName = element.getText();
		assertEquals(foundName, ResName, "Failed Reservation Name Mismatched");
		tapechartLogger.info("Successfully Verified Reservation Name in Reservation Update Popup");
		test_steps.add("Successfully Verified Reservation Name in Reservation Update Popup");
		return test_steps;
	}


	//		/*
//		* ##########################################################################################################################################################################
//		*
//		* ' Method Name: <tapeChartSearch>
//		* ' Description: <The method will enter the search information of Tape chartand click search>
//		* ' Input parameters: <WebDriver , String , String , String ,String , String >
//		* ' Return value: <ArrayList>
//		* ' Created By: <Adhnan Ghaffar>
//		* ' Created On: <07/14/2020>
//		* ##########################################################################################################################################################################
//		*/
	public ArrayList<String> tapeChartSearch(WebDriver driver, String CheckinDate, String CheckOutDate, String Adults,
											 String Children, String RatePlan) throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChart_CheckIn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChart_CheckIn), driver);
		Utility.ScrollToElement(TapeChart.TapeChart_CheckIn, driver);
		TapeChart.TapeChart_CheckIn.clear();
		TapeChart.TapeChart_CheckIn.sendKeys(CheckinDate);
		assertTrue(TapeChart.TapeChart_CheckIn.getAttribute("value").contains(CheckinDate), "Failed: CheckIn");
		testSteps.add("Enter Checkin Date  : " + CheckinDate);
		tapechartLogger.info("Enter Checkin Date  : " + CheckinDate);

		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.TAPECHART_CHECKOUT), driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.TAPECHART_CHECKOUT), driver);
		Utility.ScrollToElement(TapeChart.TAPECHART_CHECKOUT, driver);
		TapeChart.TAPECHART_CHECKOUT.clear();
		TapeChart.TAPECHART_CHECKOUT.sendKeys(CheckOutDate);
		assertTrue(TapeChart.TAPECHART_CHECKOUT.getAttribute("value").contains(CheckOutDate), "Failed: CheckOut");
		testSteps.add("Enter Check Out Date  : " + CheckOutDate);
		tapechartLogger.info("Enter Check Out Date  : " + CheckOutDate);

		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(Adults);
		assertTrue(TapeChart.Enter_Adults_Tapehchart.getAttribute("value").contains(Adults), "Failed: Adult set");
		testSteps.add("Enter Adults  : " + Adults);
		tapechartLogger.info("Enter Adults  : " + Adults);
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(Children);
		testSteps.add("Enter Children : " + Children);
		tapechartLogger.info("Enter Children : " + Children);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChartRatePlan), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChartRatePlan), driver);

		TapeChart.TapeChartRatePlan.click();
		String RatePlan_Path = "//div[@id='tapeChartSearch']//ul[@role='menu']//child::span[text()='"
				+ RatePlan + "']";
		Wait.waitForElementToBeVisibile(By.xpath(RatePlan_Path), driver);
		Wait.waitForElementToBeClickable(By.xpath(RatePlan_Path), driver);
		WebElement ratePlan = driver.findElement(By.xpath(RatePlan_Path));
		ratePlan.click();
		testSteps.add("Selected Rate Plan : " + RatePlan);
		tapechartLogger.info("Selected Rate Plan : " + RatePlan);
		TapeChart.Click_Search_TapeChart.click();
		testSteps.add("Click Tape Chart search");
		tapechartLogger.info("Click Tape Chart search");
		try {
			Wait.WaitForElement(driver, OR.TapeChartAvailableSlot);
		} catch (Exception e) {
			TapeChart.Click_Search_TapeChart.click();
			tapechartLogger.info("Again Click Search");
			Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChartAvailableSlot), driver);
		}
		return testSteps;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 *
	 *  Method Name: verifyTapeChartBrokenRulesPopup '
	 *  Description: This method will verify rules popup in new  reservation creation in tapechart'
	 *  Input parameters: WebDriver driver, int days, String minimumNights, String noCheckIn, String noCheckOut
	 *  Return value: ArrayList<String> '
	 *  Created By: Farhan Ghaffar '
	 *  Created On: 07-24-2020
	 *
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public ArrayList<String> verifyTapeChartBrokenRulesPopup(WebDriver driver, String isMinimumStayOn, String minimumStayValue, String isNoCheckInChecked, String isNoCheckOutChecked)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		String minimumStay = "";
		String noCheckIn = "";
		String noCheckOut = "";
		if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("Yes") && isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
			minimumStay = minimumStayValue + " Nights (override)";
			noCheckIn = "No Check In (override)";
			noCheckOut = "No Check out (override)";

		} else if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("No") && isNoCheckOutChecked.equalsIgnoreCase("No")) {
			minimumStay = minimumStayValue + " Nights (override)";
			noCheckIn = "";
			noCheckOut = "";

		} else if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("Yes") && isNoCheckOutChecked.equalsIgnoreCase("No")) {
			minimumStay = minimumStayValue + " Nights (override)";
			noCheckIn = "No Check In (override), ";
			noCheckOut = "";

		} else if (isMinimumStayOn.equalsIgnoreCase("Yes") && isNoCheckInChecked.equalsIgnoreCase("No") && isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
			minimumStay = minimumStayValue + " Nights (override)";
			noCheckIn = "";
			noCheckOut = "No Check out (override)";

		} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("Yes") && isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
			minimumStay = "";
			noCheckIn = "No Check In (override)";
			noCheckOut = "No Check out (override)";

		} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("Yes") && isNoCheckOutChecked.equalsIgnoreCase("No")) {
			minimumStay = "";
			noCheckIn = "No Check In (override)";
			noCheckOut = "";

		} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("No") && isNoCheckOutChecked.equalsIgnoreCase("Yes")) {
			minimumStay = "";
			noCheckIn = "";
			noCheckOut = "No Check out (override)";

		} else if (isMinimumStayOn.equalsIgnoreCase("No") && isNoCheckInChecked.equalsIgnoreCase("No") && isNoCheckOutChecked.equalsIgnoreCase("No")) {
			minimumStay = "";
			noCheckIn = "";
			noCheckOut = "";

		}

		testSteps.add("Verifying rules heading in rule borken popup");
		tapechartLogger.info("Verifying rules heading in rule borken popup");
		String expectedRulesHeading = "Rules Broken";
		testSteps.add("Expected  : " + expectedRulesHeading);
		tapechartLogger.info("Expected  : " + expectedRulesHeading);

		Wait.WaitForElement(driver, OR.Rules_Broken_popup);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Rules_Broken_popup), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Rules_Broken_popup), driver);
		Utility.ScrollToElement(TapeChart.Rules_Broken_popup, driver);
		String getText = TapeChart.Rules_Broken_popup.getText();
		testSteps.add("Found  : " + getText);
		tapechartLogger.info("Found  : " + getText);

		Assert.assertEquals(getText, expectedRulesHeading, "Failed to verify rules popup heading text");
		testSteps.add("Verified heading ( " + expectedRulesHeading + " ) is displaying in broken rules popup");
		tapechartLogger.info("Verified heading ( " + expectedRulesHeading + " ) is displaying in broken rules popup");

		if (!minimumStay.isEmpty()) {
			testSteps.add("Verifying minimum stay description rule in rule borken popup");
			tapechartLogger.info("Verifying minimum stay description rule in rule borken popup");
			testSteps.add("Expected  : " + minimumStay);
			tapechartLogger.info("Expected  : " + minimumStay);

			String minimumStayPath = "//td[contains(text(), 'Minimum stay')]//following-sibling::td";
			Wait.WaitForElement(driver, minimumStayPath);
			Wait.waitForElementToBeVisibile(By.xpath(minimumStayPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(minimumStayPath), driver);
			WebElement minimumStayElement = driver.findElement(By.xpath(minimumStayPath));
			Utility.ScrollToElement(minimumStayElement, driver);
			getText = minimumStayElement.getText();
			testSteps.add("Found  : " + getText);
			tapechartLogger.info("Found  : " + getText);
			Assert.assertEquals(getText, minimumStay, "Failed to verify Minimum stay");
			testSteps.add("Verified minimum stay rule description");
			tapechartLogger.info("Verified minimum stay rule description");
		}

		if (!noCheckIn.isEmpty() && !noCheckIn.equals("")) {
			testSteps.add("Verifying no check in rule description in rule broken popup");
			tapechartLogger.info("Verifying no check in rule description in rule broken popup");

			testSteps.add("Expected  : " + noCheckIn);
			tapechartLogger.info("Expected  : " + noCheckIn);

			String noCheckInPath = "//td[contains(text(), 'No Check In')]//following-sibling::td";
			Wait.WaitForElement(driver, noCheckInPath);
			Wait.waitForElementToBeVisibile(By.xpath(noCheckInPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(noCheckInPath), driver);
			WebElement noCheckInElement = driver.findElement(By.xpath(noCheckInPath));
			Utility.ScrollToElement(noCheckInElement, driver);
			getText = noCheckInElement.getText();
			testSteps.add("Found  : " + getText);
			tapechartLogger.info("Found  : " + getText);
			Assert.assertEquals(getText, noCheckIn, "Failed to verify no check in");
			testSteps.add("Verified no check in rule description");
			tapechartLogger.info("Verified no check in rule description");
		}

		if (!noCheckOut.isEmpty() && !noCheckOut.equals("")) {
			testSteps.add("Verifying no check out rule description in rule broken popup");
			tapechartLogger.info("Verifying no check out rule description in rule broken popup");

			testSteps.add("Expected  : " + noCheckIn);
			tapechartLogger.info("Expected  : " + noCheckIn);

			String noCheckOutPath = "//td[contains(text(), 'No Check out')]//following-sibling::td";
			Wait.WaitForElement(driver, noCheckOutPath);
			Wait.waitForElementToBeVisibile(By.xpath(noCheckOutPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(noCheckOutPath), driver);
			WebElement noCheckOutElement = driver.findElement(By.xpath(noCheckOutPath));
			Utility.ScrollToElement(noCheckOutElement, driver);
			getText = noCheckOutElement.getText();
			testSteps.add("Found  : " + getText);
			tapechartLogger.info("Found  : " + getText);
			Assert.assertEquals(getText, noCheckOut, "Failed to verify no check out");
			testSteps.add("Verified no check out rule description");
			tapechartLogger.info("Verified no check out rule description");
		}

		testSteps.add("Verifying rules text in rule borken popup");
		tapechartLogger.info("Verifying rules text in rule borken popup");

		String expectedRulesText = "Do you want to continue?";
		testSteps.add("Expected  : " + expectedRulesText);
		tapechartLogger.info("Expected  : " + expectedRulesText);

		Wait.WaitForElement(driver, OR.TapeChartRulePopupText);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChartRulePopupText), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChartRulePopupText), driver);
		Utility.ScrollToElement(TapeChart.TapeChartRulePopupText, driver);
		getText = TapeChart.TapeChartRulePopupText.getText();
		testSteps.add("Found  : " + getText);
		tapechartLogger.info("Found  : " + getText);

		Assert.assertEquals(getText, expectedRulesText, "Failed to verify rules popup text");
		testSteps.add("Verified text ( " + expectedRulesText + " ) is displaying in broken rules popup");
		tapechartLogger.info("Verified text ( " + expectedRulesText + " ) is displaying in broken rules popup");


		return testSteps;
	}

	public void verifyTapeChartBrokenRulesPopupUpdate(WebDriver driver, ArrayList<String> testSteps, boolean isMinimumStayRuleApplicable,
													  String minimumStayValue, boolean isNoCheckInRuleApplicable, boolean isNoCheckOutRuleApplicable)
			throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		String minimumStay = "";
		String noCheckIn = "";
		String noCheckOut = "";
		if (isMinimumStayRuleApplicable) {
			minimumStay = minimumStayValue + " night minimum";
		}

		if (isNoCheckInRuleApplicable) {
			noCheckIn = "No check-in";
		}

		if (isNoCheckOutRuleApplicable) {
			noCheckOut = "No check-out";
		}

		testSteps.add("Verifying rules heading in rule borken popup");
		tapechartLogger.info("Verifying rules heading in rule borken popup");
		String expectedRulesHeading = "Rules Broken";
		testSteps.add("Expected  : " + expectedRulesHeading);
		tapechartLogger.info("Expected  : " + expectedRulesHeading);

		Wait.WaitForElement(driver, OR.Rules_Broken_popup);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Rules_Broken_popup), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Rules_Broken_popup), driver);
		Utility.ScrollToElement(TapeChart.Rules_Broken_popup, driver);
		String getText = TapeChart.Rules_Broken_popup.getText();
		testSteps.add("Found  : " + getText);
		tapechartLogger.info("Found  : " + getText);

		Assert.assertEquals(getText, expectedRulesHeading, "Failed to verify rules popup heading text");
		testSteps.add("Verified heading ( " + expectedRulesHeading + " ) is displaying in broken rules popup");
		tapechartLogger.info("Verified heading ( " + expectedRulesHeading + " ) is displaying in broken rules popup");

		if (!minimumStay.isEmpty()) {
			testSteps.add("Verifying minimum stay description rule in rule borken popup");
			tapechartLogger.info("Verifying minimum stay description rule in rule borken popup");
			testSteps.add("Expected  : " + minimumStay);
			tapechartLogger.info("Expected  : " + minimumStay);

			String minimumStayPath = String.format("//td[contains(text(),'%s')]//following-sibling::td", minimumStay);
			Wait.WaitForElement(driver, minimumStayPath);
			Wait.waitForElementToBeVisibile(By.xpath(minimumStayPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(minimumStayPath), driver);
			WebElement minimumStayElement = driver.findElement(By.xpath(minimumStayPath));
			Utility.ScrollToElement(minimumStayElement, driver);
			getText = minimumStayElement.getText();
			testSteps.add("Found  : " + getText);
			tapechartLogger.info("Found  : " + getText);
			Assert.assertEquals(getText, minimumStay, "Failed to verify Minimum stay");
			testSteps.add("Verified minimum stay rule description");
			tapechartLogger.info("Verified minimum stay rule description");
		}

		if (!noCheckIn.isEmpty() && !noCheckIn.equals("")) {
			testSteps.add("Verifying no check in rule description in rule broken popup");
			tapechartLogger.info("Verifying no check in rule description in rule broken popup");

			testSteps.add("Expected  : " + noCheckIn);
			tapechartLogger.info("Expected  : " + noCheckIn);

			String noCheckInPath = String.format("//td[contains(text(),'%s')]//following-sibling::td", noCheckIn);
			Wait.WaitForElement(driver, noCheckInPath);
			Wait.waitForElementToBeVisibile(By.xpath(noCheckInPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(noCheckInPath), driver);
			WebElement noCheckInElement = driver.findElement(By.xpath(noCheckInPath));
			Utility.ScrollToElement(noCheckInElement, driver);
			getText = noCheckInElement.getText();
			testSteps.add("Found  : " + getText);
			tapechartLogger.info("Found  : " + getText);
			Assert.assertEquals(getText, noCheckIn, "Failed to verify no check in");
			testSteps.add("Verified no check in rule description");
			tapechartLogger.info("Verified no check in rule description");
		}

		if (!noCheckOut.isEmpty() && !noCheckOut.equals("")) {
			testSteps.add("Verifying no check out rule description in rule broken popup");
			tapechartLogger.info("Verifying no check out rule description in rule broken popup");

			testSteps.add("Expected  : " + noCheckOut);
			tapechartLogger.info("Expected  : " + noCheckOut);

			String noCheckOutPath = String.format("//td[contains(text(),'%s')]//following-sibling::td", noCheckOut);
			Wait.WaitForElement(driver, noCheckOutPath);
			Wait.waitForElementToBeVisibile(By.xpath(noCheckOutPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(noCheckOutPath), driver);
			WebElement noCheckOutElement = driver.findElement(By.xpath(noCheckOutPath));
			Utility.ScrollToElement(noCheckOutElement, driver);
			getText = noCheckOutElement.getText();
			testSteps.add("Found  : " + getText);
			tapechartLogger.info("Found  : " + getText);
			Assert.assertEquals(getText, noCheckOut, "Failed to verify no check out");
			testSteps.add("Verified no check out rule description");
			tapechartLogger.info("Verified no check out rule description");
		}

		testSteps.add("Verifying rules text in rule borken popup");
		tapechartLogger.info("Verifying rules text in rule borken popup");

		String expectedRulesText = "Do you want to continue?";
		testSteps.add("Expected  : " + expectedRulesText);
		tapechartLogger.info("Expected  : " + expectedRulesText);

		Wait.WaitForElement(driver, OR.TapeChartRulePopupText);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChartRulePopupText), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChartRulePopupText), driver);
		Utility.ScrollToElement(TapeChart.TapeChartRulePopupText, driver);
		getText = TapeChart.TapeChartRulePopupText.getText();
		testSteps.add("Found  : " + getText);
		tapechartLogger.info("Found  : " + getText);

		Assert.assertEquals(getText, expectedRulesText, "Failed to verify rules popup text");
		testSteps.add("Verified text ( " + expectedRulesText + " ) is displaying in broken rules popup");
		tapechartLogger.info("Verified text ( " + expectedRulesText + " ) is displaying in broken rules popup");
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <clickAvailableSlotWithOutRulePopupHandling>
	 * ' Description: <click on available slot after searching tape chart but will not h=handle rule popup if appear>
	 * ' Input parameters:(RoomClass name ) '
	 *  Return value:  ArrayList<String>
	 *  ' Created By: <Farhan Ghaffar>
	 *  ' CreatedOn: <07/27/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void clickAvailableSlotWithOutRulePopupHandling(WebDriver driver, ArrayList<String> testSteps, String roomClassAbbr) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[text()='" + roomClassAbbr
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		try {
			Utility.ScrollToElement(AvailableSlot, driver);
			try {
				AvailableSlot.click();
			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				AvailableSlot.click();
				tapechartLogger.info("Scrol back 300");

			}
		} catch (Exception e) {
			tapechartLogger.info(e);
		}
		testSteps.add("Click available room of Room Class '" + roomClassAbbr + "'");
		tapechartLogger.info("Click available room of Room Class '" + roomClassAbbr + "'");

	}

	public ArrayList<String> clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(WebDriver driver, ArrayList<String> test_steps,
																					 String roomClassAbbreviation, String roomNumber) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = String.format("//div[text()='%s' and contains(@class,'roomclassname')]/parent::div/following-sibling::div/div//span[text()='%s']/parent::div" +
				"//following-sibling::div//div[contains(@class,'DatesContainer')]//ancestor::div[contains(@class,'tapechartdatecell Available')]", roomClassAbbreviation, roomNumber);
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		try {
			Utility.ScrollToElement(AvailableSlot, driver);
			try {
				AvailableSlot.click();
			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				AvailableSlot.click();
				tapechartLogger.info("Scrol back 300");

			}
		} catch (Exception e) {
		}
		testSteps.add("Click available room of Room Class '" + roomClassAbbreviation + "'");
		tapechartLogger.info("Click available room of Room Class '" + roomClassAbbreviation + "'");
		return testSteps;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 *
	 *  Method Name: cancelRulesPopup '
	 *  Description: This method will click cancel rules popup button in new  reservation creation from tpaecharts'
	 *  Input parameters: WebDriver driver
	 *  Return value: ArrayList<String> '
	 *  Created By: Farhan Ghaffar '
	 *  Created On: 07-27-2020
	 *
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public ArrayList<String> cancelRulesPopup(WebDriver driver)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		Wait.WaitForElement(driver, OR.CancelRulesPopupButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.CancelRulesPopupButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.CancelRulesPopupButton), driver);
		Utility.ScrollToElement(TapeChart.CancelRulesPopupButton, driver);
		TapeChart.CancelRulesPopupButton.click();

		testSteps.add("Cancel button clicked");
		tapechartLogger.info("Cancel button clicked");

		return testSteps;
	}
	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 *
	 *  Method Name: clickUnassignedRoomClass '
	 *  Description: This method will click unassigned button in new  reservation creation from tpaechart'
	 *  Input parameters: WebDriver driver
	 *  Return value: ArrayList<String> '
	 *  Created By: Farhan Ghaffar '
	 *  Created On: 07-27-2020
	 *
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */

	public ArrayList<String> clickUnassignedRoomClass(WebDriver driver, String RoomClass)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String CellPath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']/child::div// div[contains(text(),'Unassigned')]";
		Wait.explicit_wait_xpath(CellPath, driver);
		WebElement Unassigned_Reserv_Room = driver.findElement(By.xpath(CellPath));

		if (LastRoomClassInTapeChart(driver, RoomClass)) {
			Utility.ScrollToElement(Unassigned_Reserv_Room, driver);
		} else {
			Utility.ScrollToElement(Unassigned_Reserv_Room, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-300)");
			Utility.app_logs.info("Scrolled Back");
		}
		Unassigned_Reserv_Room.click();
		testSteps.add("Click Unassigned of Room Class : " + RoomClass);
		Utility.app_logs.info("Click Unassigned of Room Class : " + RoomClass);
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.explicit_wait_visibilityof_webelement_120(res.CP_NewReservation_GuestSalutation, driver);
		testSteps.add("New Reservation Page is opened");
		tapechartLogger.info("New Reservation Page is opened");
		return testSteps;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 *
	 *  Method Name: clickBookIcon '
	 *  Description: This method will click book button in rules popup in new  reservation creation from tpaecharts'
	 *  Input parameters: WebDriver driver
	 *  Return value: ArrayList<String> '
	 *  Created By: Farhan Ghaffar '
	 *  Created On: 07-28-2020
	 *
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public ArrayList<String> clickBookIcon(WebDriver driver)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_CPReservation CPReservation = new Elements_CPReservation(driver);
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);

		Wait.WaitForElement(driver, OR.Rules_Broken_popup);
		Wait.waitForElementToBeClickable(By.xpath(OR.Click_Book_icon_Tapechart), driver);
		Utility.ScrollToElement(TapeChart.Click_Book_icon_Tapechart, driver);
		TapeChart.Click_Book_icon_Tapechart.click();

		testSteps.add("Book button clicked");
		tapechartLogger.info("Book button clicked");

		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestSalutation);
		assertTrue(CPReservation.CP_NewReservation_GuestSalutation.isDisplayed(), "Reservation Page didn't load");
		testSteps.add("New Reservation page is opened");
		tapechartLogger.info("New Reservation Page is Opened");

		return testSteps;
	}

	public ArrayList<String> clickBookIconWithNoReservationPageValidation(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_CPReservation CPReservation = new Elements_CPReservation(driver);
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.Rules_Broken_popup);
		Wait.waitForElementToBeClickable(By.xpath(OR.Click_Book_icon_Tapechart), driver);
		Utility.ScrollToElement(TapeChart.Click_Book_icon_Tapechart, driver);
		TapeChart.Click_Book_icon_Tapechart.click();
		test_steps.add("Book button clicked");
		tapechartLogger.info("Book button clicked");
		return test_steps;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 *
	 *  Method Name: tapeChartRulePopupSize '
	 *  Description: This method will return size of rules popup in new  reservation creation from tapecharts'
	 *  Input parameters: WebDriver driver
	 *  Return value: int '
	 *  Created By: Farhan Ghaffar '
	 *  Created On: 07-29-2020
	 *
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public int tapeChartRulePopupSize(WebDriver driver)
			throws InterruptedException {

		List<WebElement> ruleBrokenPopup = driver.findElements(By.xpath(OR_Reservation.CP_NewReservation_GuestSalutation));
		tapechartLogger.info("Rule broken popup size : " + ruleBrokenPopup.size());

		return ruleBrokenPopup.size();
	}


	public ArrayList<String> clickConfirm_RaservationUpdate(WebDriver driver) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement_120(TapeChart.ConfirmChangeReservation_Button, driver);
		Wait.explicit_wait_elementToBeClickable(TapeChart.ConfirmChangeReservation_Button, driver);
		TapeChart.ConfirmChangeReservation_Button.click();
		tapechartLogger.info("Confirm Change Clicked in Reservation Update Popup");
		Wait.explicit_wait_visibilityof_webelement_120(TapeChart.Toaster_Message, driver);
		tapechartLogger.info(TapeChart.Toaster_Message);
		test_steps.add("Confirm Change Clicked in Reservation Update Popup");
		return test_steps;
	}

	public boolean ReservationExist(WebDriver driver, String RoomNumber, String RoomClass) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		RoomNumber = RoomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(RoomNumber);
		Wait.wait2Second();
		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		tapechartLogger.info("PTah:" + path);
		return driver.findElement(By.xpath(path)).isDisplayed();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <clickOnOneDay> ' Description: <click on one day from days
	 * option> ' Input parameters:(Webdriver ) ' Return value: void ' Created
	 * By: <Farhan Ghaffar> ' CreatedOn: <07/19/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void clickOnOneDay(WebDriver driver) throws InterruptedException {

		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, NewTapeChart.OneDayOption);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.OneDayOption), driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.OneDayOption), driver);
		Utility.ScrollToElement_NoWait(tapeChart.OneDayOption, driver);
		tapeChart.OneDayOption.click();
	}


	public String getRoomClassAvailability(WebDriver driver, String roomClassName, String roomNumber)
			throws InterruptedException {

		String pathOfRoomNumberStatus = "//div[contains(text(),'" + roomClassName
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ roomNumber + "']//following-sibling::div//div[@class='guest_display_name_short']";
		Wait.WaitForElement(driver, pathOfRoomNumberStatus);
		Wait.waitForElementToBeVisibile(By.xpath(pathOfRoomNumberStatus), driver);
		Wait.waitForElementToBeClickable(By.xpath(pathOfRoomNumberStatus), driver);
		WebElement element = driver.findElement(By.xpath(pathOfRoomNumberStatus));
		Utility.ScrollToElement_NoWait(element, driver);
		return element.getText();
	}


	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <select_CheckInDate and select_CheckoutDate >
	 * ' Description: <Select check in Date>
	 * ' Input parameters:(WebDriver driver, ArrayList<String> test_steps, String CheckInDate )
	 * ' Return value: void '
	 * Created By: <Gangotri Sikheria> ' CreatedOn: <08/14/2020>
	 *
	 *
	 * ------------------------------------------
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void select_CheckInDate(WebDriver driver, ArrayList<String> test_steps, String CheckInDate)
			throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.Select_Arrival_Date);
		tapeChart.Select_Arrival_Date.click();
		String monthYear = Utility.get_MonthYear(CheckInDate);
		String day = Utility.getDay(CheckInDate);
		tapechartLogger.info(monthYear);
		String header = null, headerText = null, next = null, date;
		for (int i = 1; i <= 12; i++) {
			header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
			headerText = driver.findElement(By.xpath(header)).getText();
			if (headerText.equalsIgnoreCase(monthYear)) {
				date = "//td[contains(@class,'day') and text()='" + day + "']";
				Wait.WaitForElement(driver, date);
				int size = driver.findElements(By.xpath(date)).size();
				for (int k = 1; k <= size; k++) {
					date = "(//td[contains(@class,'day') and text()='" + day + "'])[" + k + "]";
					String classText = driver.findElement(By.xpath(date)).getAttribute("class");
					if (!classText.contains("old")) {
						driver.findElement(By.xpath(date)).click();
						test_steps.add("Selecting checkin date : " + CheckInDate);
						tapechartLogger.info("Selecting checkin date : " + CheckInDate);
						break;
					}
				}
				break;
			} else {
				next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div";
				Wait.WaitForElement(driver, next);
				driver.findElement(By.xpath(next)).click();
				Wait.wait2Second();
			}
		}
	}

	public void select_CheckoutDate(WebDriver driver, ArrayList<String> test_steps, String CheckoutDate)
			throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.selectDepartDate);
		tapeChart.selectDepartDate.click();
		String monthYear = Utility.get_MonthYear(CheckoutDate);
		String day = Utility.getDay(CheckoutDate);
		tapechartLogger.info(monthYear);
		String header = null, headerText = null, next = null, date;
		for (int i = 1; i <= 12; i++) {
			header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
			headerText = driver.findElement(By.xpath(header)).getText();
			if (headerText.equalsIgnoreCase(monthYear)) {
				date = "//td[contains(@class,'day') and text()='" + day + "']";
				Wait.WaitForElement(driver, date);
				int size = driver.findElements(By.xpath(date)).size();
				for (int k = 1; k <= size; k++) {
					date = "(//td[contains(@class,'day') and text()='" + day + "'])[" + k + "]";
					String classText = driver.findElement(By.xpath(date)).getAttribute("class");
					if (!classText.contains("old")) {
						driver.findElement(By.xpath(date)).click();
						test_steps.add("Selecting checkout date : " + CheckoutDate);
						tapechartLogger.info("Selecting checkout date : " + CheckoutDate);
						break;
					}
				}

				break;
			} else {
				next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div";
				Wait.WaitForElement(driver, next);
				driver.findElement(By.xpath(next)).click();
				Wait.wait2Second();
			}
		}
	}


	public void searchRoomClassInTapeChart(WebDriver driver, String checkinDate, String checkoutDate, String adults,
										   String timeZone, ArrayList<String> test_steps, String ratePlan) throws Exception {
		CPReservationPage cpRes = new CPReservationPage();
		// cpRes.checkInDate(driver, checkinDate, timeZone, test_steps);
		// cpRes.checkOutDate(driver, checkoutDate, timeZone, test_steps);
		cpRes.selectcheckInAndCheckoutInTapeCharts(driver, checkinDate, checkoutDate, timeZone, test_steps);
		enterAdult(driver, adults, test_steps);
		selectRatePlan(driver, ratePlan, test_steps);
		clickSearchButton(driver, test_steps);
	}

	public void clickSearchButton(WebDriver driver, ArrayList<String> testSteps) {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		tapechartLogger.info("Search Button Clicked");
		testSteps.add("Search Button Clicked");
	}

	public void clickSearchButtonWithJavaScript(WebDriver driver, ArrayList<String> test_steps) {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
		jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		}catch (Exception e) {
			jse.executeScript("arguments[0].click();", TapeChart.Click_Search_TapeChart);
		}
		tapechartLogger.info("Search Button Clicked");
		test_steps.add("Search Button Clicked");
	}

	public void searchRoomClassInTapeChart(WebDriver driver, ArrayList<String> test_steps, String checkInDate,
			String checkOutDate, String adults, String children, String ratePlanName) throws Exception {
		select_CheckInDate(driver, test_steps, checkInDate);
		if (Utility.validateString(checkOutDate)) {
			select_CheckoutDate(driver, test_steps, checkOutDate);
		}
		enterAdult(driver, test_steps, adults);
		enterChildren(driver, test_steps, children);
		selectRatePlan(driver, ratePlanName, test_steps);
		clickOnSearch(driver, test_steps);
	}
	
	public void verifyToastMessageInTapeChart(WebDriver driver, String differentSearchMessage, ArrayList<String> test_steps) {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		//div[text()='No results match your criteria. Please change your search and try again.']
		Wait.waitUntilPresenceOfElementLocated(OR.ToastMessageInTapeChart, driver);
		String toastmessage = TapeChart.toastMessageInTapeChart.getText();
		tapechartLogger.info("toastmessage" + toastmessage);
		Assert.assertEquals(toastmessage, differentSearchMessage, "failed to verify");
		test_steps.add("Verify the toaster message displayed: " + differentSearchMessage);

	}


	public ArrayList<String> captureAvailabilityInTapeChart(WebDriver driver, String roomClass, int stayDifference) {
		int k = 2 + stayDifference;
		ArrayList<String> avail = new ArrayList<String>();
		for (int j = 2; j < k; j++) {
			String getAvailable = "(//div[.='" + roomClass + "']//following::div/following::div/div[text()='# Rooms Available']/following::div/div/div/div)[" + j + "]";
			String availability = driver.findElement(By.xpath(getAvailable)).getText();
			avail.add(availability);
		}
		return avail;
	}

	public ArrayList<String> captureRateInTapeChart(WebDriver driver, String roomClass, int stayDifference, ArrayList<String> test_steps) {
		int k = 2 + stayDifference;
		ArrayList<String> rate = new ArrayList<String>();
		for (int j = 2; j < k; j++) {
			String getRate = "(//div[.='" + roomClass + "']/following::div[@class='col-xs-11']/div)[" + j + "]";
			String getRates = driver.findElement(By.xpath(getRate)).getText();
			String rates = getRates.replace("$", "").trim();
			rate.add(rates);
		}
		test_steps.add("Rates in Tape Chart for given date is: " + rate);
		return rate;
	}


	public void enterAdult(WebDriver driver, String TapeChartAdults, ArrayList<String> testSteps)
			throws InterruptedException {
		Wait.wait5Second();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.Enter_Adults_Tapehchart);
		Utility.ScrollToElement(TapeChart.Enter_Adults_Tapehchart, driver);
		TapeChart.Enter_Adults_Tapehchart.click();
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(TapeChartAdults);
		testSteps.add("Enter adults number count in tape chart: " + TapeChartAdults);

	}

	public void enterChildren(WebDriver driver, String child, ArrayList<String> steps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart.Enter_Children_Tapechart, driver);
		Utility.ScrollToElement(TapeChart.Enter_Children_Tapechart, driver);
		TapeChart.Enter_Children_Tapechart.click();
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(child);
		steps.add("Enter Children: " + child);

	}

	public void clickOnSearch(WebDriver driver, ArrayList<String> steps) throws InterruptedException {
		ClickOnSearch(driver);
		steps.add("Click on Search Button");
	}

	public void verifyerrorMessage(WebDriver driver, ArrayList<String> testSteps, String message) throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.Toaster_Message_Xpath);
		assertEquals(tapeChart.toaster_Message_Xpath.getText().toLowerCase().trim(), message.toLowerCase().trim(), "Failed to verified Message");
		testSteps.add("Verified Message <b>" + tapeChart.toaster_Message_Xpath.getText() + "</b>");
		tapechartLogger.info("Verified Message " + tapeChart.toaster_Message_Xpath.getText());

	}

	public ArrayList<String> getRoomClassRates(WebDriver driver, String roomClassAbb) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "//div[contains(@class,'roomclassname')and contains(text(),'" + roomClassAbb + "')]/following-sibling::div//div";
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		for (WebElement ele : labelValues) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			data.add(ele.getText());
		}

		return data;
	}


	public HashMap<String, String> getRatesOfRoomClass(WebDriver driver, String startDate, String endDate, String format, ArrayList<String> testSteps, String roomClassAbb) throws InterruptedException, ParseException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		List<Date> dates = new ArrayList<Date>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		ArrayList<String> roomClassRates = new ArrayList<String>();
		HashMap<String, String> rates = new HashMap<String, String>();
		Date fromDate = Utility.convertStringtoDateFormat(format, startDate);
		tapechartLogger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(format, endDate);
		tapechartLogger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		tapechartLogger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(format, d));
		}
		tapechartLogger.info("Day's  from Start date and End Date: " + dayList);
		tapechartLogger.info("Date List from Start date and End Date: " + dateList);

		for (WebElement ele : tapeChart.roomsDateDayColumn) {
			datesList.add(ele.getText());
		}
		tapechartLogger.info("Rate Grid Day's Are: " + datesList);
		roomClassRates = getRoomClassRates(driver, roomClassAbb);
		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).contains(dayList.get(j))) {
					if (roomClassRates.get(i).equals("X")) {
						rates.put(dateList.get(j), "NA");
					} else {
						rates.put(dateList.get(j), roomClassRates.get(i));
					}

				}

			}

		}
		tapechartLogger.info("Rates as per  Date  " + rates);
		return rates;

	}


	public void verifyRoomClassCurrencyAsperDate(WebDriver driver, ArrayList<String> testSteps, String expectedRate, String actualRate, String date) {
		assertThat(expectedRate.trim(), CoreMatchers.containsString(actualRate.trim()));
		testSteps.add("Verified Amount <b>" + date + "--" + expectedRate + "</b>");
		tapechartLogger.info("Verified Amount " + expectedRate);
	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <calculateRatesAsPerAdultsAndChildCapacity>
	 * ' Description: <This method will calculate rate as per extra adult and child >
	 * ' Input parameters: <WebDriver driver,String ratePerNight, String ratePlanAdult, String ratePlanChild,String addAdultPerNight, String addChildPerNight, String roomClassCapacity , String reservationAdult, String reservationChild>
	 * ' Return value:<String>
	 * ' Created By: <Gangotri Sikheria> ' Created On: <12/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */


	private ArrayList<Double> calculationDateWise(HashMap<String, String> ratePerNight, HashMap<String, String> addAdultPerNight, HashMap<String, String> addChildPerNight, List<Date> dates, int index, String format) throws NumberFormatException, ParseException {
		ArrayList<Double> rateAdultChild = new ArrayList<Double>();
		double ratePerNights = 0.00, adultPerNight = 0.00, childPerNight = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		if (StringUtils.isNumericSpace(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index))))) {
			double a = Double.parseDouble(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			String doubleValue = df.format(a);
			ratePerNights = Double.parseDouble(doubleValue);
			rateAdultChild.add(ratePerNights);
		} else if (!(StringUtils.isNumericSpace(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index)))))) {
			ratePerNights = Double.parseDouble(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			rateAdultChild.add(ratePerNights);
		}
		if (StringUtils.isNumericSpace(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))))) {
			double a = Double.parseDouble(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			String doubleValue = df.format(a);
			adultPerNight = Double.parseDouble(doubleValue);
			rateAdultChild.add(adultPerNight);
		} else if (!(StringUtils.isNumericSpace(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index)))))) {
			adultPerNight = Double.parseDouble(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			rateAdultChild.add(adultPerNight);
		}
		if (StringUtils.isNumericSpace(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))))) {
			double a = Double.parseDouble(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			String doubleValue = df.format(a);
			childPerNight = Double.parseDouble(doubleValue);
			rateAdultChild.add(childPerNight);
		} else if (!(StringUtils.isNumericSpace(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index)))))) {
			childPerNight = Double.parseDouble(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			rateAdultChild.add(childPerNight);
		}

		return rateAdultChild;
	}

	public String calculateRatesAsPerAdultsAndChildCapacity(WebDriver driver, HashMap<String, String> ratePerNight, String ratePlanAdult, String ratePlanChild, HashMap<String, String> addAdultPerNight,
															HashMap<String, String> addChildPerNight, String roomClassCapacity, String roomClassAdult, String reservationAdult, String reservationChild,
															ArrayList<String> testSteps, String message, String roomClass, String checkInDate, String checkOutDate, String format, String abbreviation) throws InterruptedException, ParseException {
		List<Date> dates = new ArrayList<Date>();
		HashMap<String, String> getRatesRoomClassFromTapeChart = new HashMap<String, String>();
		Date fromDate = Utility.convertStringtoDateFormat(format, checkInDate);
		tapechartLogger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(format, checkOutDate);
		tapechartLogger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		tapechartLogger.info("Dates Are: " + dates);

		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		ArrayList<Double> rateAdultChild = new ArrayList<Double>();

		String rate = null;
		Float floatRate = null;
		int intRateIs = 0;
		int reservationTotalAdultAndChild = Integer.parseInt(reservationAdult) + Integer.parseInt(reservationChild);
		if (!reservationAdult.equals("0") && !reservationChild.equals("0")) {
			if (reservationTotalAdultAndChild == Integer.parseInt(roomClassCapacity) || reservationTotalAdultAndChild < Integer.parseInt(roomClassCapacity)) {
				if (Integer.parseInt(reservationAdult) == Integer.parseInt(ratePlanAdult) || Integer.parseInt(reservationAdult) < Integer.parseInt(ratePlanAdult)) {
					getRatesRoomClassFromTapeChart = getRatesOfRoomClass(driver, checkInDate, checkOutDate, format, testSteps,
							abbreviation);
					for (int i = 0; i < dates.size(); i++) {
						rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i, format);
						int childs = reservationTotalAdultAndChild - Integer.parseInt(ratePlanChild);
						if (childs <= 0) {
							childs = 0;
						}
						double child = rateAdultChild.get(0) + (childs * rateAdultChild.get(2));
						floatRate = (float) child;
						intRateIs = (int) Math.ceil(floatRate);
						rate = String.valueOf(intRateIs);
						tapechartLogger.info(rate);
						verifyRoomClassCurrencyAsperDate(driver, testSteps, getRatesRoomClassFromTapeChart.get(Utility.convertDateFormattoString(format, dates.get(i))), rate,
								Utility.convertDateFormattoString(format, dates.get(i)));
					}


				} else if (Integer.parseInt(reservationAdult) > Integer.parseInt(ratePlanAdult) && !(Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult))) {
					getRatesRoomClassFromTapeChart = getRatesOfRoomClass(driver, checkInDate, checkOutDate, format, testSteps,
							abbreviation);
					for (int i = 0; i < dates.size(); i++) {
						rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i, format);
						int couldBeChildOrAdult = reservationTotalAdultAndChild - Integer.parseInt(ratePlanChild);
						int adult = Integer.parseInt(reservationAdult) - Integer.parseInt(ratePlanAdult);
						int child = couldBeChildOrAdult - adult;
						double totalRate = rateAdultChild.get(0) + ((adult * rateAdultChild.get(1)) + (child * rateAdultChild.get(2)));
						floatRate = (float) totalRate;
						intRateIs = (int) Math.ceil(floatRate);
						rate = String.valueOf(intRateIs);
						tapechartLogger.info(rate);
						verifyRoomClassCurrencyAsperDate(driver, testSteps, getRatesRoomClassFromTapeChart.get(Utility.convertDateFormattoString(format, dates.get(i))), rate,
								Utility.convertDateFormattoString(format, dates.get(i)));
					}
				} else if (Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult)) {
					verifyerrorMessage(driver, testSteps, message);
				}
			} else if (reservationTotalAdultAndChild > Integer.parseInt(roomClassCapacity)) {
				verifyerrorMessage(driver, testSteps, message);
			}

		} else if (reservationAdult.equals("0") && Integer.parseInt(reservationChild) > Integer.parseInt(ratePlanChild) && !(Integer.parseInt(reservationChild) > Integer.parseInt(roomClassCapacity))) {
			getRatesRoomClassFromTapeChart = getRatesOfRoomClass(driver, checkInDate, checkOutDate, format, testSteps,
					abbreviation);
			for (int i = 0; i < dates.size(); i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i, format);
				int alwaysChild = Integer.parseInt(reservationChild) - Integer.parseInt(ratePlanChild);
				double child = rateAdultChild.get(0) + (alwaysChild * rateAdultChild.get(2));
				floatRate = (float) child;
				intRateIs = (int) Math.ceil(floatRate);
				rate = String.valueOf(intRateIs);
				tapechartLogger.info(rate);
				verifyRoomClassCurrencyAsperDate(driver, testSteps, getRatesRoomClassFromTapeChart.get(Utility.convertDateFormattoString(format, dates.get(i))), rate,
						Utility.convertDateFormattoString(format, dates.get(i)));
			}
		} else if (reservationChild.equals("0") && Integer.parseInt(reservationAdult) > Integer.parseInt(ratePlanAdult) && !(Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult))) {
			getRatesRoomClassFromTapeChart = getRatesOfRoomClass(driver, checkInDate, checkOutDate, format, testSteps,
					abbreviation);
			for (int i = 0; i < dates.size(); i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i, format);
				int alwaysAdult = Integer.parseInt(reservationAdult) - Integer.parseInt(ratePlanAdult);
				double child = rateAdultChild.get(0) + (alwaysAdult * rateAdultChild.get(1));
				floatRate = (float) child;
				intRateIs = (int) Math.ceil(floatRate);
				rate = String.valueOf(intRateIs);
				tapechartLogger.info(rate);
				verifyRoomClassCurrencyAsperDate(driver, testSteps, getRatesRoomClassFromTapeChart.get(Utility.convertDateFormattoString(format, dates.get(i))), rate,
						Utility.convertDateFormattoString(format, dates.get(i)));
			}
		} else if (reservationAdult.equals("0") && Integer.parseInt(reservationChild) > Integer.parseInt(roomClassCapacity)) {
			verifyerrorMessage(driver, testSteps, message);
		} else if (reservationChild.equals("0") && Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult)) {
			verifyerrorMessage(driver, testSteps, message);
		} else if (reservationAdult.equals("0") && reservationChild.equals("0")) {
			verifyerrorMessage(driver, testSteps, message);
		} else if (Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult) && Integer.parseInt(reservationChild) > Integer.parseInt(roomClassCapacity)) {
			verifyerrorMessage(driver, testSteps, message);
		} else if (reservationAdult.equals("0") && Integer.parseInt(reservationChild) < Integer.parseInt(ratePlanChild)) {
			getRatesRoomClassFromTapeChart = getRatesOfRoomClass(driver, checkInDate, checkOutDate, format, testSteps,
					abbreviation);
			for (int i = 0; i < dates.size(); i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i, format);
				double child = rateAdultChild.get(0);
				floatRate = (float) child;
				intRateIs = (int) Math.ceil(floatRate);
				rate = String.valueOf(intRateIs);
				tapechartLogger.info(rate);
				verifyRoomClassCurrencyAsperDate(driver, testSteps, getRatesRoomClassFromTapeChart.get(Utility.convertDateFormattoString(format, dates.get(i))), rate,
						Utility.convertDateFormattoString(format, dates.get(i)));

			}


		} else if (reservationChild.equals("0") && Integer.parseInt(reservationAdult) < Integer.parseInt(ratePlanAdult)) {
			getRatesRoomClassFromTapeChart = getRatesOfRoomClass(driver, checkInDate, checkOutDate, format, testSteps,
					abbreviation);
			for (int i = 0; i < dates.size(); i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i, format);
				double child = rateAdultChild.get(0);
				floatRate = (float) child;
				intRateIs = (int) Math.ceil(floatRate);
				rate = String.valueOf(intRateIs);
				tapechartLogger.info(rate);
				verifyRoomClassCurrencyAsperDate(driver, testSteps, getRatesRoomClassFromTapeChart.get(checkInDate), rate,
						Utility.convertDateFormattoString(format, dates.get(i)));

			}

		} else if (Integer.parseInt(reservationAdult) < Integer.parseInt(ratePlanAdult) && Integer.parseInt(reservationChild) < Integer.parseInt(ratePlanChild)) {
			getRatesRoomClassFromTapeChart = getRatesOfRoomClass(driver, checkInDate, checkOutDate, format, testSteps,
					abbreviation);
			for (int i = 0; i < dates.size(); i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i, format);
				double child = rateAdultChild.get(0);
				floatRate = (float) child;
				intRateIs = (int) Math.ceil(floatRate);
				rate = String.valueOf(intRateIs);
				tapechartLogger.info(rate);
				verifyRoomClassCurrencyAsperDate(driver, testSteps, getRatesRoomClassFromTapeChart.get(checkInDate), rate,
						Utility.convertDateFormattoString(format, dates.get(i)));
			}

		}
		return rate;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <isRoomClassAvailableInTapeChart>
	 * ' Description: <verify Availability of Room Class >
	 * ' Input parameters: <WebDriver driver,String roomClassName>
	 * ' Return value:<boolean>
	 * ' Created By: <Tej> ' Created On: <20/08/2020>
	 * 'Copied: From Expedia Develop Branch
	 * 'Copied By:  Gangotri Sikheria
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */


	public boolean isRoomClassAvailableInTapeChart(WebDriver driver, String roomClassName) {
		boolean availaFlag = false;		
		String CellPath="//div[contains(@data-content,'"+roomClassName+"')  and contains(@class,'roomnumber')]"
				+ "//following-sibling::div//following-sibling::div[contains(@class,'DatesContainer')]"
				+ "//ancestor::div[contains(@class,'tapechartdatecell Available')]";
		try {
			if (driver.findElements(By.xpath(CellPath)).size() > 0) {
				availaFlag = true;
				test_steps.add("Room Class Available");
				tapechartLogger.info("Room Class Available");
			} else {
				test_steps.add("Room Class not Available");
				tapechartLogger.info("Room Class not Available");
			}
		} catch (Exception e) {
			test_steps.add("Room Class not Available");
			tapechartLogger.info("Room Class not Available");
		}
		return availaFlag;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <clickAvailableSlot> ' Description: <click on available
	 * slot after searching tape chart> ' Input parameters:(RoomClass name ) '
	 * Return value: void ' Created By: <Gangotri Sikheria> ' CreatedOn:
	 * <20/08/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String clickAvailableSlot(WebDriver driver, String RoomClass, ArrayList<String> testSteps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Elements_CPReservation CPReservation = new Elements_CPReservation(driver);
		String CellPath="//div[contains(@data-content,'"+RoomClass+"')  and contains(@class,'roomnumber')]"
				+ "//following-sibling::div//following-sibling::div[contains(@class,'DatesContainer')]"
				+ "//ancestor::div[contains(@class,'tapechartdatecell Available')]";
		String roomNo="//div[contains(@class,'tapechartdatecell Available')]/..//parent::*//parent::*"
				+ "/preceding-sibling::div[contains(@data-content,'"+RoomClass+"')  and contains(@class,'roomnumber')]/span[1]";
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		String roomNos=null;
		try {
			Utility.ScrollToViewElementINMiddle(driver, AvailableSlot);
			roomNos=driver.findElement(By.xpath(roomNo)).getText();
			AvailableSlot.click();
			testSteps.add("Click on Available Slot");
			tapechartLogger.info("Click on Available Slot");
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			Wait.WaitForElement(driver, OR.ReservationDetailPage);
		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.WaitForElement(driver, OR.Rules_Broken_popup);
				Wait.waitForElementToBeClickable(By.xpath(OR.Click_Book_icon_Tapechart), driver);
				Utility.ScrollToElement(TapeChart.Click_Book_icon_Tapechart, driver);
				TapeChart.Click_Book_icon_Tapechart.click();
			} else {
				tapechartLogger.info("Rules are not broken");
			}
		}
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestSalutation);
		assertTrue(CPReservation.CP_NewReservation_GuestSalutation.isEnabled(), "Reservation Page didn't load");
		testSteps.add("Reservation Page loaded");
		tapechartLogger.info("Reservation Page loaded");
		tapechartLogger.info(roomNos);
		return roomNos;
	}

	
	public String getkAvailableSlotRoomNo(WebDriver driver, String RoomClass, ArrayList<String> testSteps) throws InterruptedException {

		String CellPath="//div[contains(@data-content,'"+RoomClass+"')  and contains(@class,'roomnumber')]"
				+ "/..//div[contains(@class,'tapechartdatecell Available')]";
		String roomNo="//div[contains(@class,'tapechartdatecell Available')]/..//parent::*//parent::*"
				+ "/preceding-sibling::div[contains(@data-content,'"+RoomClass+"')  and contains(@class,'roomnumber')]/span[1]";
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		String roomNos=null;
		try {
			Utility.ScrollToViewElementINMiddle(driver, AvailableSlot);
			roomNos=driver.findElement(By.xpath(roomNo)).getText();
		} catch (Exception e) {			
		}
		tapechartLogger.info(roomNos);
		return roomNos;
	}
	public void selectCheckInDate(WebDriver driver, ArrayList<String> test_steps, String checkInDate)
			throws InterruptedException {
		String date = "//input[@class='form-control date-from innroad-form-input-field tapeChart arriveDate']";
		Wait.waitForElementToBeVisibile(By.xpath(date), driver);
		String checkInDateConvert = Utility.parseDate(checkInDate, "dd/MM/yyyy", "MM/dd/yyyy");
		driver.findElement(By.xpath(date)).clear();
		driver.findElement(By.xpath(date)).sendKeys(checkInDateConvert);
		test_steps.add("Entering check in date in tape chart as : <b>" + checkInDate + "</b>");
	}

	public void selectCheckOutDate(WebDriver driver, ArrayList<String> test_steps, String checkOutDate)
			throws InterruptedException {
		String date = "//input[@class='form-control date-to innroad-form-input-field tapeChart departDate']";
		Wait.waitForElementToBeVisibile(By.xpath(date), driver);
		String checkOutDateConvert = Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MM/dd/yyyy");
		driver.findElement(By.xpath(date)).clear();
		driver.findElement(By.xpath(date)).sendKeys(checkOutDateConvert);
		test_steps.add("Entering check out date in tape chart as : <b>" + checkOutDate + "</b>");
	}

	public void enterAdult(WebDriver driver, ArrayList<String> test_steps, String adults)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Enter_Adults_Tapehchart), driver);
		TapeChart.Enter_Adults_Tapehchart.clear();
		TapeChart.Enter_Adults_Tapehchart.sendKeys(adults);
		test_steps.add("Entering adults number count in tape chart as : <b>" + adults + "</b>");
	}

	public void enterChildren(WebDriver driver, ArrayList<String> testSteps, String child) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Enter_Children_Tapechart), driver);
		TapeChart.Enter_Children_Tapechart.clear();
		TapeChart.Enter_Children_Tapechart.sendKeys(child);
		testSteps.add("Entering children number count in tape chart as : <b>" + child + "</b>");
	}

	public void selectRatePlan(WebDriver driver, String RatePlan, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.btnRatePlanSelect), driver);
		TapeChart.btnRatePlanSelect.click();
		String selectRatePlan = "//div[@class='selectors rate-plan']/div/select/following-sibling::div/div/ul/li/a/span[text()='"
				+ RatePlan + "']";
		Wait.waitForElementToBeVisibile(By.xpath(selectRatePlan), driver);
		driver.findElement(By.xpath(selectRatePlan)).click();
		testSteps.add("Selecting rate plan in tape chart as : <b>" + RatePlan + "</b>");
	}

	public void selectDatesAndClickOnSearch(WebDriver driver, ArrayList<String> test_steps, String checkInDate,
											String checkOutDate, String adults, String children, String ratePlanName) throws Exception {
		selectCheckInDate(driver, test_steps, checkInDate);
		if (Utility.validateString(checkOutDate)) {
			selectCheckOutDate(driver, test_steps, checkOutDate);
		}
		enterAdult(driver, test_steps, adults);
		enterChildren(driver, test_steps, children);
		selectRatePlan(driver, ratePlanName, test_steps);
		clickOnSearch(driver, test_steps);
	}


	public void searchInTapechart(WebDriver driver, ArrayList<String> testSteps, String checkin, String checkout, String adult,
				String childern, String ratePlan, String promoCode) throws InterruptedException, ParseException {

		try {
			Wait.waitUntilPresenceOfElementLocated(OR.tapeChartGridLayOut, driver);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			
			Wait.waitUntilPresenceOfElementLocated("//div[@class='tapechart-container']//div[@class='roomClasses']", driver);
		}catch (Exception e) {
			try {
				Wait.WaitForElement(driver, "//div[@class='tapechart-container']//div[@class='roomClasses']");
			}catch (Exception ex) {
				System.err.println("Tape Chart Loading");
			}
		}
		
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.TapeChart_CheckIn);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChart_CheckIn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChart_CheckIn), driver);
		Utility.ScrollToElement_NoWait(tapeChart.TapeChart_CheckIn, driver);
		
		select_CheckInDate(driver,testSteps,checkin);
		/*tapeChart.TapeChart_CheckIn.clear();
		  tapeChart.TapeChart_CheckIn.sendKeys(checkin);
		testSteps.add("Entered check-in date as : <b>" + checkin+"</b>");*/

		/*tapeChart.CheckoutDate.clear();
		tapeChart.CheckoutDate.sendKeys(checkout);
		testSteps.add("Entered check-out date as : <b>" + checkout+"</b>");*/

		select_CheckoutDate(driver,testSteps,checkout);
		
		tapeChart.Enter_Adults_Tapehchart.clear();
		tapeChart.Enter_Adults_Tapehchart.sendKeys(adult);
		testSteps.add("Entered adults as : <b>" + adult+"</b>");

		tapeChart.Enter_Children_Tapechart.clear();
		tapeChart.Enter_Children_Tapechart.sendKeys(childern);
		testSteps.add("Entered children as : <b>" + childern+"</b>");

		Wait.wait10Second();
		tapeChart.ClickOnRatePlan.click();
		tapechartLogger.info("CLick on rate plan button");
		String pathRatePlan = String.format("//div[contains(@class,'rate-plan')]//ul//li//span[contains(text(), '%s')]", ratePlan);
		tapechartLogger.info("Path: "+pathRatePlan);
		Wait.WaitForElement(driver, pathRatePlan);
		driver.findElement(By.xpath(pathRatePlan)).click();
		Wait.wait5Second();
		testSteps.add("Selected rateplan as : <b>" + ratePlan+"</b>");
		if(!Utility.validateString(promoCode)) {
			enterPromoCode(driver, promoCode);
			testSteps.add("Entered promo code as : <b>" + promoCode+"</b>");
		}

		tapeChart.Click_Search_TapeChart.click();
		tapechartLogger.info("CLick on search button");
		testSteps.add("Clicked on search");
		
		try {
			Wait.waitUntilPresenceOfElementLocated(OR.tapeChartGridLayOut, driver);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			
			Wait.waitUntilPresenceOfElementLocated("//div[@class='tapechart-container']//div[@class='roomClasses']", driver);
		}catch (Exception e) {
			try {
				Wait.WaitForElement(driver, "//div[@class='tapechart-container']//div[@class='roomClasses']");
			}catch (Exception ex) {
				System.err.println("Tape Chart Loading");
			}
		}

	}

	public ArrayList<String> searchInTapeChartManualOverride(WebDriver driver, String checkin, String checkout, String adult,
											   String childern, String amount) throws InterruptedException, ParseException {

		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.TapeChart_CheckIn);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChart_CheckIn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChart_CheckIn), driver);
		Utility.ScrollToElement_NoWait(tapeChart.TapeChart_CheckIn, driver);

		tapeChart.TapeChart_CheckIn.clear();
		tapeChart.TapeChart_CheckIn.sendKeys(checkin);
		testSteps.add("Enter checkin: " + checkin);

		tapeChart.CheckoutDate.clear();
		tapeChart.CheckoutDate.sendKeys(checkout);
		testSteps.add("Enter checkout: " + checkout);

		tapeChart.Enter_Adults_Tapehchart.clear();
		tapeChart.Enter_Adults_Tapehchart.sendKeys(adult);
		testSteps.add("Enter adults: " + adult);

		tapeChart.Enter_Children_Tapechart.clear();
		tapeChart.Enter_Children_Tapechart.sendKeys(childern);
		testSteps.add("Enter childern: " + childern);

		Wait.wait10Second();
		tapeChart.ClickOnRatePlan.click();
		tapechartLogger.info("CLick on rate plan button");
		String pathRatePlan = "//div[contains(@class,'rate-plan')]//ul//li//span[text()='Manual Override']";
		Wait.WaitForElement(driver, pathRatePlan);
		driver.findElement(By.xpath(pathRatePlan)).click();
		String amountFieldLocator = "//input[contains(@data-bind,'value: manualOverride')]";
		Wait.WaitForElement(driver, amountFieldLocator);
		Wait.waitForElementToBeVisibile(By.xpath(amountFieldLocator), driver);
		Wait.waitForElementToBeClickable(By.xpath(amountFieldLocator), driver);
		WebElement element = driver.findElement(By.xpath(amountFieldLocator));
		element.clear();
		element.sendKeys(amount);

		tapeChart.Click_Search_TapeChart.click();
		testSteps.add("Click on search");

		return testSteps;

	}


	public int getNoResultsToastSize(WebDriver driver) throws InterruptedException {
		Wait.wait2Second();
		List<WebElement> toaster = driver.findElements(By.xpath(NewTapeChart.ToasterMessage));
		tapechartLogger.info("Found : " + toaster.size());

		return toaster.size();
	}


	public ArrayList<String> getNoResultsToastMessage(WebDriver driver, String toasterText) throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, NewTapeChart.ToasterMessage);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.ToasterMessage), driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.ToasterMessage), driver);
		Utility.ScrollToElement_NoWait(tapeChart.ToasterMessage, driver);

		testSteps.add("Expected toast text : " + toasterText);
		tapechartLogger.info("Expected toast text : " + toasterText);
		String getText = tapeChart.ToasterMessage.getText();
		testSteps.add("Found : " + getText);
		tapechartLogger.info("Found : " + getText);

		assertEquals(getText, toasterText, "Faield to verify toaster message");
		testSteps.add("verified toast (" + toasterText + ") is displaying");
		tapechartLogger.info("verified toast (" + toasterText + ") is displaying");

		return testSteps;
	}

	public ArrayList<String> verifyMinimuStayInRuleBrokenPopup(WebDriver driver, int minimumStay) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String path = "//td[text()='" + minimumStay + " Nights (override)']";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement minimumStayRule = driver.findElement(By.xpath(path));
		Utility.ScrollToElement_NoWait(minimumStayRule, driver);

		assertTrue(minimumStayRule.isDisplayed(), "Failed minimum stay rule didn't display");
		testSteps.add("Verified that minimum stay rule (" + minimumStay + " Nights (override)) is displaying in rule broken popup");
		tapechartLogger.info("Verified that minimum stay rule (" + minimumStay + " Nights (override)) is displaying in rule broken popup");

		return testSteps;
	}

	public ArrayList<String> clickBrokenRulePopupButton(WebDriver driver, String buttonText) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String path = "//h4[text()='Rules Broken']//following::button[text()='" + buttonText + "']";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement button = driver.findElement(By.xpath(path));
		Utility.ScrollToElement_NoWait(button, driver);
		button.click();
		testSteps.add("Clicked " + buttonText + " button");
		tapechartLogger.info("Clicked " + buttonText + " button");

		return testSteps;
	}

	public ArrayList<String> isMinStayRule(WebDriver driver, String roomClass) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);

		String CellPath = "(//div[text()='" + roomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')])[1]";
		Wait.wait5Second();

		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		Utility.ScrollToElement(AvailableSlot, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			jse.executeScript("arguments[0].scrollIntoView(true);", AvailableSlot);
			Wait.wait2Second();

			Actions action = new Actions(driver);
			action.moveToElement(AvailableSlot).perform();
			tapechartLogger.info("Action performed");

			Wait.WaitForElement(driver, NewTapeChart.RulesApplicable);
			Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.RulesApplicable), driver);

		} catch (Exception e) {
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			Actions action = new Actions(driver);
			action.moveToElement(AvailableSlot).perform();
			tapechartLogger.info("Action performed");


		}
		assertTrue(tapeChart.RulesApplicable.isDisplayed(), "Failed to verify that rules applicable is dispalying");
		testSteps.add("Verified that rules applicable is displaying");
		tapechartLogger.info("Verified that rules applicable is displaying");

		Wait.WaitForElement(driver, NewTapeChart.MinimumStay);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.MinimumStay), driver);
		assertTrue(tapeChart.MinimumStay.isDisplayed(), "Failed to verify that rules applicable is dispalying");
		testSteps.add("Verified that minimum stay is displaying");
		tapechartLogger.info("Verified that minimum stay is displaying");


		return testSteps;

	}

	public boolean verifyMinStayLength(WebDriver driver, boolean verifyLenthOfStayChecked,
			boolean verifyMinStayCondidtion,
			HashMap<String, String> mapCondition, int days, boolean isSeasonApplied, String timeZone,
			String checkIn, String checkout, String adult, String child, String ratePlan,
			String toasterMessage, ArrayList<String> testSteps) throws InterruptedException, ParseException {
		boolean isTrue = false;
		if (verifyLenthOfStayChecked && verifyMinStayCondidtion) {

			tapechartLogger.info("==========VERIFY MIN CONDITION IN TAPECHART ==========");
			testSteps.add("==========VERIFY MIN CONDITION IN TAPECHART ==========");

			int MinCondition = Integer.parseInt(mapCondition.get("Min"));

			if (isSeasonApplied && MinCondition <= days) {

				searchInTapechart(driver, testSteps, checkIn, checkout, adult, child, ratePlan, "");

				testSteps.add("Min nights in condition: " + (MinCondition));
				testSteps.add("Searched Min nights in tapechart: " + MinCondition);


				int size = getNoResultsToastSize(driver);
				if (size == 0) {
					isTrue = true;
				} else {

					try {
						Wait.wait1Second();

						driver.findElement(By.className(OR.Toaster_Message)).click();
						Wait.wait1Second();
					} catch (Exception e) {
					}
					isTrue = false;

				}
			} else {
				isTrue = false;

			}

		} else {
			isTrue = false;

		}
		return isTrue;
	}

	public boolean verifyMinCondition(WebDriver driver, boolean verifyLenthOfStayChecked,
			boolean verifyMinStayCondidtion,
			HashMap<String, String> mapCondition, int days, boolean isSeasonApplied, String timeZone,
			String checkIn, String checkout, String adult, String child, String ratePlan,
			String toasterMessage, ArrayList<String> testSteps) throws InterruptedException, ParseException {
		boolean isTrue = false;
		if (verifyLenthOfStayChecked && verifyMinStayCondidtion) {

			tapechartLogger.info("==========VERIFY MIN CONDITION IN TAPECHART ==========");
			testSteps.add("==========VERIFY MIN CONDITION IN TAPECHART ==========");

			int MinCondition = Integer.parseInt(mapCondition.get("Min"));

			if (isSeasonApplied && MinCondition <= days) {

				String getDateForNumberOfNight = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkIn, "MM/dd/yyyy",
						"MM/dd/yyyy", MinCondition, timeZone);
				tapechartLogger.info("getDateForNumberOfNight" + getDateForNumberOfNight);

				searchInTapechart(driver, testSteps, checkIn, getDateForNumberOfNight, adult, child, ratePlan, "");

				testSteps.add("Min nights in condition: " + (MinCondition));
				testSteps.add("Searched Min nights in tapechart: " + MinCondition);


				int size = getNoResultsToastSize(driver);
				Assert.assertTrue(size == 0, "Failed : toast message is displaying");
				if (size == 0) {
					isTrue = true;
				} else {
					isTrue = false;
				}
				testSteps.add("verified toast (" + toasterMessage + ") is not displaying");
				tapechartLogger.info("verified toast (" + toasterMessage + ") is not  displaying");

				testSteps.add("Verified Min condition in tapechart with minimum nights: " + MinCondition);

				// here to verify condition is not meet
				getDateForNumberOfNight = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkIn, "MM/dd/yyyy",
						"MM/dd/yyyy", MinCondition - 1, timeZone);
				tapechartLogger.info("getDateForNumberOfNight" + getDateForNumberOfNight);

				searchInTapechart(driver, testSteps, checkIn, getDateForNumberOfNight, adult, child, ratePlan, "");


				testSteps.add("Min nights in condition: " + (MinCondition));
				testSteps.add("Searched Min nights in tapechart: " + (MinCondition - 1));

				size = getNoResultsToastSize(driver);
				Assert.assertTrue(size > 0, "Failed to verify toast message is displaying");
				if (size > 0) {
					isTrue = true;
				} else {
					isTrue = false;
				}
				testSteps.addAll(getNoResultsToastMessage(driver, toasterMessage));

				try {
					Wait.wait1Second();

					driver.findElement(By.className(OR.Toaster_Message)).click();
					Wait.wait1Second();
				} catch (Exception e) {
				}

				testSteps.add("Verified Min condition in tapechart with minimum nights: " + MinCondition);

			} else {

				// here to verify error message


				searchInTapechart(driver, testSteps, checkIn, checkout, adult, child, ratePlan, "");

				testSteps.add("Min nights in condition: " + (MinCondition));
				testSteps.add("Searched Min nights in tapechart: " + days);


				int size = getNoResultsToastSize(driver);
				Assert.assertTrue(size > 0, "Failed to verify toast message is displaying");
				if (size > 0) {
					isTrue = true;
				} else {
					isTrue = false;
				}

				testSteps.addAll(getNoResultsToastMessage(driver, toasterMessage));
				try {
					Wait.wait1Second();

					driver.findElement(By.className(OR.Toaster_Message)).click();
					Wait.wait1Second();
				} catch (Exception e) {
				}

			}

		} else {
			testSteps.add("Min condition is not applicable");
			tapechartLogger.info("Min condition is not applicable");

			isTrue = false;
		}
		return isTrue;

	}


	public void clickAvailableSlotWithRatePalnValidation(WebDriver driver, String RoomClass, boolean isMinStayRule, boolean isMinStayRuleBrokenPopComeOrNot, int minStayRuleValue, String noCheckinColor, String noCheckoutColor) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Elements_CPReservation CPReservation = new Elements_CPReservation(driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		try {
			Utility.ScrollToElement(AvailableSlot, driver);
			try {
				AvailableSlot.click();
			} catch (Exception e) {
				jse.executeScript("window.scrollBy(0,-300)");
				AvailableSlot.click();
				tapechartLogger.info("Scrol back 300");

			}
			//Wait.WaitForElement(driver, OR.ReservationDetailPage);
			if (driver.findElements(By.xpath(OR.ReservationDetailPage)).size() > 0) {

			} else {
				if (TapeChart.Rules_Broken_popup.isDisplayed()) {
					Wait.WaitForElement(driver, OR.Rules_Broken_popup);
					Wait.waitForElementToBeClickable(By.xpath(OR.Click_Book_icon_Tapechart), driver);

					if (isMinStayRule && !isMinStayRuleBrokenPopComeOrNot) {
						String min = "//td[contains(text(),'Minimum stay')]/..//td[contains(text(),'" + minStayRuleValue + " Nights') ]";
						Wait.WaitForElement(driver, min);
						test_steps.add("Min Stay Rule is verified in tape chart rules broken popup for " + minStayRuleValue + " days");
						tapechartLogger.info("Min Stay Rule is verified in tape chart rules broken popup for " + minStayRuleValue + " days");
					}

					if (noCheckinColor.equalsIgnoreCase("Red")) {
						String noCheckIn = "//td[contains(text(),'No Check In')]/..//td[contains(text(),'No Check In')]";
						Wait.WaitForElement(driver, noCheckIn);
						test_steps.add("No Check In Rule is verified in tape chart rules broken popup");
						tapechartLogger.info("No Check In Rule is verified in tape chart rules broken popup");
					}

					if (noCheckoutColor.equalsIgnoreCase("Red")) {
						String noCheckIn = "//td[contains(text(),'No Check out')]/..//td[contains(text(),'No Check out')]";
						Wait.WaitForElement(driver, noCheckIn);
						test_steps.add("No Check out Rule is verified in tape chart rules broken popup");
						tapechartLogger.info("No Check out Rule is verified in tape chart rules broken popup");
					}


					Utility.ScrollToElement(TapeChart.Click_Book_icon_Tapechart, driver);
					TapeChart.Click_Book_icon_Tapechart.click();
				} else {
					tapechartLogger.info("Rules are not broken");
				}
			}

		} catch (Exception e) {
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.WaitForElement(driver, OR.Rules_Broken_popup);
				Wait.waitForElementToBeClickable(By.xpath(OR.Click_Book_icon_Tapechart), driver);

				if (isMinStayRule && !isMinStayRuleBrokenPopComeOrNot) {
					String min = "//td[contains(text(),'Minimum stay')]/..//td[contains(text(),'" + minStayRuleValue + " Nights') ]";
					//Wait.WaitForElement(driver, min);
					test_steps.add("Min Stay Rule is verified in tape chart rules broken popup for " + minStayRuleValue + " days");
					tapechartLogger.info("Min Stay Rule is verified in tape chart rules broken popup for " + minStayRuleValue + " days");
				}

				if (noCheckinColor.equalsIgnoreCase("Red")) {
					String noCheckIn = "//td[contains(text(),'No Check In')]/..//td[contains(text(),'No Check In')]";
					//Wait.WaitForElement(driver, noCheckIn);
					test_steps.add("No Check In Rule is verified in tape chart rules broken popup");
					tapechartLogger.info("No Check In Rule is verified in tape chart rules broken popup");
				}

				if (noCheckoutColor.equalsIgnoreCase("Red")) {
					String noCheckIn = "//td[contains(text(),'No Check out')]/..//td[contains(text(),'No Check out')]";
					//Wait.WaitForElement(driver, noCheckIn);
					test_steps.add("No Check out Rule is verified in tape chart rules broken popup");
					tapechartLogger.info("No Check out Rule is verified in tape chart rules broken popup");
				}


				Utility.ScrollToElement(TapeChart.Click_Book_icon_Tapechart, driver);
				TapeChart.Click_Book_icon_Tapechart.click();
			} else {
				tapechartLogger.info("Rules are not broken");
			}
		}
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestSalutation);
		assertTrue(CPReservation.CP_NewReservation_GuestSalutation.isDisplayed(), "Reservation Page didn't load");

	}

	public void verifyNoResultsmatchedInTapechart(WebDriver driver, ArrayList<String> test_steps) {
		String msg = "//div[contains(text(),'No results match your criteria. Please change your search and try again.')]";
		Wait.WaitForElement(driver, msg);
		test_steps.add("Error : No results match your criteria. Please change your search and try again.");
		tapechartLogger.info("Error : No results match your criteria. Please change your search and try again.");
	}

	public ArrayList<String> verifySelectedRoom_Group(WebDriver driver, String RoomClassAbv, String RoomNo, String str) {
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomClassAbv + "']/../../div/div[@title='"
				+ RoomNo + "']//following-sibling::div//div[contains(@class,'guest_display_name_short')])[last()]";
		tapechartLogger.info(path);
		ArrayList<String> test_steps = new ArrayList<>();
		Wait.WaitForElement(driver, path);
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);

		String foundTxt = driver.findElement(By.xpath(path)).getText();
		tapechartLogger.info(foundTxt);
		tapechartLogger.info(str);
		assertEquals(foundTxt, str, "Failed : Selected Room Str Not Matched");
		test_steps.add("Successfully Verified Cell Displayed Message : " + foundTxt + " For RoomNO : " + RoomNo + " And RoomClass : " + RoomClassAbv);
		tapechartLogger.info("Successfully Verified Cell Displayed Message : " + foundTxt + " For RoomNO : " + RoomNo + " And RoomClass : " + RoomClassAbv);
		return test_steps;

	}

	public ArrayList<String> verifyBackGroundColor_Group(WebDriver driver, String RoomClassAbv, String RoomNo, String groupName) {
//			String path = "(//div[@class='row ratesrow']//div[text()='" + RoomClassAbv + "']/../../div/div[@title='"
//					+ RoomNo + "']//following-sibling::div//div[@class='tapechartdatecell group'][contains(@title,'"
//					+ groupName + "')])[last()]";
		String path = "(//div[@class='row ratesrow']//div[text()='" + RoomClassAbv + "']/../../div/div[@title='"
				+ RoomNo + "']//following-sibling::div//div[contains(@class,'tapechartdatecell')])[last()]";
		tapechartLogger.info(path);
		ArrayList<String> test_steps = new ArrayList<>();
		Wait.WaitForElement(driver, path);
		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(path)), driver);

		String foundColor = driver.findElement(By.xpath(path)).getCssValue("background");
		assertTrue(foundColor.contains("rgb(211, 236, 243)"), "Failed : Color not matched expected [rgb(211, 236, 243)] but found[" + foundColor + "]");
		test_steps.add("Successfully Verified Cell Backgroud Color : Light Blue [rgb(211, 236, 243)]");
		tapechartLogger.info("Successfully Verified Cell Backgroud Color : Light Blue [rgb(211, 236, 243)]");
		return test_steps;

	}

	public ArrayList<String> reservationToMove(WebDriver driver, String ToRoomNumber, String FromRoomNumber, String RoomClass, String AccountName) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<>();
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();

		String toPath = "//div[text()='" + RoomClass + "']/../../div/div[@title='" + ToRoomNumber + "']//following-sibling::div//div[contains(@title,'" + AccountName + "')]//parent::span";
		String fromPath = "//div[text()='" + RoomClass + "']/../../div/div[@title='" + FromRoomNumber + "']//following-sibling::div//div[contains(text(),'" + AccountName + "')]//parent::div//parent::div//parent::div";


		Wait.wait2Second();
		//String path = "//div[@title='" + RoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		WebElement From = driver.findElement(By.xpath(fromPath));

		WebElement To = driver.findElement(By.xpath(toPath));

		// Using Action class for drag and drop.
		Actions act = new Actions(driver);
		// Dragged and dropped
		//Action dragAndDrop = act.clickAndHold(From).moveToElement(To).release(To).build();
		act.dragAndDrop(From, To).build().perform();
		Wait.wait5Second();

		try {
			//TapeChart.ConfirmChangesButton.click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			// verify toaster message here
			Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
			assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
					"Reservation does not expand");
		} catch (Exception e) {
			// TODO: handle exception
		}
		tapechartLogger.info("Room No : " + FromRoomNumber + " Reservation drag and drop to " + ToRoomNumber);
		test_steps.add("Room No : " + FromRoomNumber + " Reservation drag and drop to " + ToRoomNumber);
		return test_steps;
	}

	public String getFooter2ndCellValue(WebDriver driver, String roomClass, String rowName) throws InterruptedException {

		//Row Names
		//# Rooms Available
		//% Occupancy

		String footerPath = "//div[contains(text(),'" + roomClass + "')]/parent::div/parent::div/following-sibling::div"
				+ "/div[@title='" + rowName + "']/following-sibling::div/div[2]/div/div";

		Wait.explicit_wait_xpath(footerPath, driver);
		Wait.waitUntilPresenceOfElementLocated(footerPath, driver);
		Wait.explicit_wait_visibilityof_webelement_150(driver.findElement(By.xpath(footerPath)), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(footerPath)), driver);
		return driver.findElement(By.xpath(footerPath)).getText();
	}


	public void moveFromToReservation(WebDriver driver, String toRoomNumber, String fromRoomNumber, String RoomClass) throws InterruptedException, AWTException {


		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();


		String path = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ fromRoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		int fromsize = driver.findElements(By.xpath(path)).size();

		String Path2 = "//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
				+ toRoomNumber + "']//following-sibling::div//div[@class='DatesContainer']";


		int tosize = driver.findElements(By.xpath(Path2)).size();

		String fromPath = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ fromRoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[" + fromsize + "]";
		tapechartLogger.info(fromPath);

		String toPath = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
				+ toRoomNumber + "']//following-sibling::div//div[@class='DatesContainer'])[" + tosize + "]";
		tapechartLogger.info(toPath);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(fromPath)));


		jse.executeScript("window.scrollBy(0,-300)");
		tapechartLogger.info("Scrolled to the reservation");
		WebElement From = driver.findElement(By.xpath(fromPath));
		Point Location = From.getLocation();


		Wait.explicit_wait_xpath(toPath, driver);
		WebElement To = driver.findElement(By.xpath(toPath));


		/*
		 * // Using Action class for drag and drop. Actions act = new Actions(driver);
		 * // Dragged and dropped //act.dragAndDrop(From, To).build().perform(); Action
		 * dragAndDrop = act.clickAndHold(From).moveToElement(To).release(To).build();
		 * dragAndDrop.perform();
		 */

		/*
		 * Actions builder = new Actions(driver); Action pickAction = builder
		 * .moveToElement(From) .clickAndHold() .build(); pickAction.perform();
		 *
		 * Utility.ScrollToElement(To, driver);
		 * jse.executeScript("window.scrollBy(0,-300)");
		 *
		 * Action dropAction = builder .moveToElement(To, To.getSize().width,
		 * To.getSize().height) .moveToElement(To) .release() .build();
		 * dropAction.perform();
		 */
		Point coordinates1 = From.getLocation();
		Point coordinates2 = To.getLocation();
		Robot robot = new Robot();
		robot.mouseMove(coordinates1.getX(), coordinates1.getY());
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(coordinates2.getX(), coordinates2.getY());
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(2000);


		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		Wait.wait5Second();

		TapeChart.ConfirmChangesButton.click();

		// verify toaster message here
		Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
		assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
				"Reservation does not Move");

	}


	public int getActiveView(WebDriver driver, ArrayList<String> test_steps) {
		String locator = "(//li[contains(@class,'highlight')]/a/span)[1]";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		String selectedView = driver.findElement(By.xpath(locator)).getText();
		return Integer.parseInt(selectedView.substring(0, selectedView.indexOf("DAY") - 1).trim());
	}

	public void verifyDefaultView(WebDriver driver, ArrayList<String> test_steps, int defaultTapeChartView) {
		tapechartLogger.info("Verifying Default View is active");
		test_steps.add("Verifying Default View is active");
		driver.navigate().refresh();
		waitForReservationToLoad(driver, test_steps);
		int activeView = getActiveView(driver, test_steps);
		if(activeView == defaultTapeChartView) {
			tapechartLogger.info("Default View is active " + defaultTapeChartView);
			test_steps.add("Default View is active " + defaultTapeChartView);
		} else {
			tapechartLogger.info("Default View is not active expected " + defaultTapeChartView + " actual " + activeView);
			test_steps.add("Assertion Default View is not active expected " + defaultTapeChartView + " actual " + activeView);
		}
	}

	public void verifyTapeChartStartDateAndEndDate(WebDriver driver, ArrayList<String> test_steps, String currentDateStr,
												   String dateFormat, int tapeChartView) {
		tapechartLogger.info("Verifying Tape Chart Start Date and End Date");
		test_steps.add("Verifying Tape Chart Start Date and End Date");
		LocalDate currentDate = LocalDate.parse(currentDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate tapeChartStartDate = LocalDate.parse(getTapeChartStartDate(driver, test_steps, dateFormat), DateTimeFormatter.ofPattern(dateFormat));
		LocalDate tapeChartEndDate = LocalDate.parse(getTapeChartEndDate(driver, test_steps, dateFormat), DateTimeFormatter.ofPattern(dateFormat));
		LocalDate expectedTapeChartStartDate;
		LocalDate expectedTapeChartEndDate;

		if(tapeChartView == 1) {
			expectedTapeChartStartDate = currentDate;
		} else {
			expectedTapeChartStartDate = currentDate.minusDays(1);
		}

		expectedTapeChartEndDate = expectedTapeChartStartDate.plusDays(tapeChartView - 1);

		tapechartLogger.info("Expected tape chart start date " + expectedTapeChartStartDate.toString());
		test_steps.add("Expected tape chart start date " + expectedTapeChartStartDate.toString());

		tapechartLogger.info("Expected tape chart end date " + expectedTapeChartEndDate.toString());
		test_steps.add("Expected tape chart end date " + expectedTapeChartEndDate.toString());

		boolean hasCorrectStartDate = tapeChartStartDate.getDayOfMonth() == expectedTapeChartStartDate.getDayOfMonth() &&
				tapeChartStartDate.getMonth() == expectedTapeChartStartDate.getMonth();

		boolean hasCorrectEndDate = tapeChartEndDate.getDayOfMonth() == expectedTapeChartEndDate.getDayOfMonth() &&
				tapeChartEndDate.getMonth() == expectedTapeChartEndDate.getMonth();

		if(hasCorrectStartDate) {
			tapechartLogger.info("Tape Chart Start Date is correct");
			test_steps.add("Tape Chart Start Date is correct ");
		} else {
			tapechartLogger.info("Tape Chart Start Date is not correct");
			test_steps.add("Assertion Tape Chart Start Date is not correct");
			test_steps.add("Issue Date is not showing based on the client timezone <a href='https://innroad.atlassian.net/browse/NG-2020' target='_blank'>NG-2020</a>");
		}

		if(hasCorrectEndDate) {
			tapechartLogger.info("Tape Chart End Date is correct");
			test_steps.add("Tape Chart End Date is correct ");
		} else {
			tapechartLogger.info("Tape Chart End Date is not correct");
			test_steps.add("Assertion Tape Chart End Date is not correct");
			test_steps.add("Issue Date is not showing based on the client timezone <a href='https://innroad.atlassian.net/browse/NG-2020' target='_blank'>NG-2020</a>");
		}
	}


	public void clickOn1Day1Button(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.oneDayButton);
		Utility.ScrollToElement(TapeChart.oneDayButton, driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.oneDayButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.oneDayButton), driver);
		TapeChart.oneDayButton.click();
		tapechartLogger.info("Clicking on Tape Chart 1 Day Button");
		test_steps.add("Clicking on Tape Chart 1 Day Button");
	}

	public void clickOn3DaysButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.threeDaysButton);
		Utility.ScrollToElement(TapeChart.threeDaysButton, driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.threeDaysButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.threeDaysButton), driver);
		TapeChart.threeDaysButton.click();
		tapechartLogger.info("Clicking on Tape Chart 3 Days Button");
		test_steps.add("Clicking on Tape Chart 3 Days Button");
	}

	public void clickOn7DaysButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.sevenDaysButton);
		Utility.ScrollToElement(TapeChart.sevenDaysButton, driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.sevenDaysButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.sevenDaysButton), driver);
		TapeChart.sevenDaysButton.click();
		tapechartLogger.info("Clicking on Tape Chart 7 Days Button");
		test_steps.add("Clicking on Tape Chart 7 Days Button");
	}

	public void clickOn15DaysButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.fifteenDaysButton);
		Utility.ScrollToElement(TapeChart.fifteenDaysButton, driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.fifteenDaysButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.fifteenDaysButton), driver);
		TapeChart.fifteenDaysButton.click();
		tapechartLogger.info("Clicking on Tape Chart 15 Days Button");
		test_steps.add("Clicking on Tape Chart 15 Days Button");
	}

	public void clickOn30DaysButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.WaitForElement(driver, OR.thirtyDaysButton);
		Utility.ScrollToElement(TapeChart.thirtyDaysButton, driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.thirtyDaysButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.thirtyDaysButton), driver);
		TapeChart.thirtyDaysButton.click();
		tapechartLogger.info("Clicking on Tape Chart 30 Days Button");
		test_steps.add("Clicking on Tape Chart 30 Days Button");
	}

	public void clickOnReservation(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, String roomNumber, String reservationFullName) throws InterruptedException {
		String locator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", roomClassAbbreviation, roomNumber, reservationFullName);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on reservation " + reservationFullName);
		test_steps.add("Clicking on reservation " + reservationFullName);
	}

	public void verifyCurrentDateIsHighlighted(WebDriver driver, ArrayList<String> test_steps, String clientCurrentDate, String dateFormat) throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		LocalDate now = LocalDate.parse(clientCurrentDate, DateTimeFormatter.ofPattern(dateFormat));
		tapeChart.TapeChart_CheckIn.clear();
		tapeChart.TapeChart_CheckIn.click();
		String checkInTodayLocator = String.format("//td[contains(@title,'%s')]", now.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")));
		Wait.WaitForElement(driver, checkInTodayLocator);
		String checkInTodayElementClass = driver.findElement(By.xpath(checkInTodayLocator)).getAttribute("class");
		if(checkInTodayElementClass.contains("today")) {
			tapechartLogger.info("Successfully verified current date is highlighted in check in date selector");
			test_steps.add("Successfully verified current date is highlighted in check in date selector");
		} else {
			tapechartLogger.info("Current date  s not highlighted in check in date selector");
			test_steps.add("Assertion Current date  s not highlighted in check in date selector");
		}
	}

	public void verifyCheckInHasErrorHighlightDueToBadData(WebDriver driver, ArrayList<String> test_steps) {
		String locator = OR.TapeChart_CheckIn + "/parent::div/parent::div";
		String classText = driver.findElement(By.xpath(locator)).getAttribute("class");
		assertTrue(classText.contains("has-error"), "Check In should have error highlight due to invalid data");
		tapechartLogger.info("Successfully verified check in field is highlighted due to invalid data");
		test_steps.add("Successfully verified check in field is highlighted due to invalid data");
	}

	public void verifyCheckOutHasErrorHighlightDueToBadData(WebDriver driver, ArrayList<String> test_steps)  {
		String locator = NewTapeChart.CheckoutDate + "/parent::div/parent::div";
		String classText = driver.findElement(By.xpath(locator)).getAttribute("class");
		assertTrue(classText.contains("has-error"), "Check Out should have error highlight due to invalid data");
		tapechartLogger.info("Successfully verified check out field is highlighted due to invalid data");
		test_steps.add("Successfully verified check out field is highlighted due to invalid data");
	}

	public void verifyAdultFieldEnterANumberMessage(WebDriver driver, ArrayList<String> test_steps) {
		String locator = OR.Enter_Adults_Tapehchart + "/following-sibling::span";
		WebElement element = driver.findElement(By.xpath(locator));
		assertEquals(element.getText(), "Please enter a number", "Adult field should have error message Please enter a number visible");
		tapechartLogger.info("Successfully verified Adult field is showing Please enter a number error message");
		test_steps.add("Successfully verified Adult field is showing Please enter a number error message");
	}

	public void verifyAdultFieldIsRequiredMessage(WebDriver driver, ArrayList<String> test_steps) {
		String locator = OR.Enter_Adults_Tapehchart + "/following-sibling::span";
		WebElement element = driver.findElement(By.xpath(locator));
		assertEquals(element.getText(), "This field is required.", "Adult field should have error message Please enter a number visible");
		tapechartLogger.info("Successfully verified Adult field is showing Please enter a number error message");
		test_steps.add("Successfully verified Adult field is showing Please enter a number error message");
	}

	public void verifyChildFieldEnterANumberMessage(WebDriver driver, ArrayList<String> test_steps) {
		String locator = OR.Enter_Children_Tapechart + "/following-sibling::span";
		WebElement element = driver.findElement(By.xpath(locator));
		assertEquals(element.getText(), "Please enter a number", "Child field should have error message Please enter a number visible");
		tapechartLogger.info("Successfully verified Child field is showing Please enter a number error message");
		test_steps.add("Successfully verified Child field is showing Please enter a number error message");
	}

	public void verifySearchUIFunctionality(WebDriver driver, ArrayList<String> test_steps, String clientCurrentDate, String dateFormat) throws InterruptedException {
		tapechartLogger.info("Verifying Search UI functionality");
		test_steps.add("Verifying Search UI functionality");
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);

		if(!Utility.isElementDisplayed(driver, By.xpath(OR.TapeChart_CheckIn)) || !Utility.isElementDisplayed(driver, By.xpath(NewTapeChart.CheckoutDate)) ) {
			test_steps.add("Failed: Issue Date picker not is visible <br/>" +
					"<a href='https://innroad.atlassian.net/browse/NG-2718' target='_blank'>NG-2718</a> <br/>" +
					"<a href='https://innroad.atlassian.net/browse/NG-1927' target='_blank'>NG-1927</a>");
		}

		Wait.WaitForElement(driver, OR.TapeChart_CheckIn);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TapeChart_CheckIn), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TapeChart_CheckIn), driver);
		Utility.ScrollToElement_NoWait(tapeChart.TapeChart_CheckIn, driver);

		verifyCurrentDateIsHighlighted(driver, test_steps, clientCurrentDate, dateFormat);

		tapeChart.TapeChart_CheckIn.clear();
		tapeChart.TapeChart_CheckIn.click();
		tapeChart.TapeChart_CheckIn.sendKeys("Invalid Data");
		tapeChart.CheckoutDate.click();
		verifyCheckInHasErrorHighlightDueToBadData(driver, test_steps);
		tapeChart.TapeChart_CheckIn.clear();

		tapeChart.CheckoutDate.clear();
		tapeChart.CheckoutDate.click();
		tapeChart.CheckoutDate.sendKeys("Invalid Data");
		tapeChart.TapeChart_CheckIn.click();
		verifyCheckOutHasErrorHighlightDueToBadData(driver, test_steps);
		tapeChart.CheckoutDate.clear();


		tapeChart.Enter_Adults_Tapehchart.clear();
		tapeChart.Enter_Adults_Tapehchart.sendKeys("Invalid data");
		verifyAdultFieldEnterANumberMessage(driver, test_steps);
		tapeChart.Enter_Adults_Tapehchart.clear();

		tapeChart.Enter_Children_Tapechart.clear();
		tapeChart.Enter_Children_Tapechart.sendKeys("Invalid data");
		verifyChildFieldEnterANumberMessage(driver, test_steps);
		tapeChart.Enter_Children_Tapechart.clear();

		tapeChart.Click_Search_TapeChart.click();
		verifyCheckInHasErrorHighlightDueToBadData(driver, test_steps);
		verifyCheckOutHasErrorHighlightDueToBadData(driver, test_steps);
		verifyAdultFieldIsRequiredMessage(driver, test_steps);

		tapechartLogger.info("Successfully verified Search UI");
		test_steps.add("Successfully verified Search UI");
	}

	public void verifyRefreshButtonFunctionality(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		tapechartLogger.info("Verifying Refresh button functionality");
		test_steps.add("Verifying Refresh button functionality");
		waitForReservationToLoad(driver, test_steps);
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		String locator = "(//button[contains(@data-bind,'tapeChartManualRefresh')])[1]";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement_NoWait(element, driver);
		element.click();
		waitForReservationToLoad(driver, test_steps);
		element.click();
		waitForReservationToLoad(driver, test_steps);
		if(tapeChart.TapeChart_CheckIn.getText().equals("")) {
			tapechartLogger.info("Check In date field is empty after clicking refresh button");
			test_steps.add("Check In date field is empty after clicking refresh button");
		} else {
			tapechartLogger.info("Check In date field should be empty after clicking refresh button");
			test_steps.add("Assertion Check In date field should be empty after clicking refresh button");
		}

		if(tapeChart.CheckoutDate.getText().equals("")) {
			tapechartLogger.info("Check Out date field is empty after clicking refresh button");
			test_steps.add("Check Out date field is empty after clicking refresh button");
		} else {
			tapechartLogger.info("Check Out date field should be empty after clicking refresh button");
			test_steps.add("Assertion Check Out date field should be empty after clicking refresh button");
		}

		if(tapeChart.Enter_Adults_Tapehchart.getText().equals("")) {
			tapechartLogger.info("Adult field is empty after clicking refresh button");
			test_steps.add("Adult field is empty after clicking refresh button");
		} else {
			tapechartLogger.info("Adult field should be empty after clicking refresh button");
			test_steps.add("Assertion Adult field should be empty after clicking refresh button");
		}

		if(tapeChart.Enter_Children_Tapechart.getText().equals("")) {
			tapechartLogger.info("Child field is empty after clicking refresh button");
			test_steps.add("Child field is empty after clicking refresh button");
		} else {
			tapechartLogger.info("Child field should be empty after clicking refresh button");
			test_steps.add("Assertion Child field should be empty after clicking refresh button");
		}

		if(tapeChart.Enter_promoCode_Tapechart.getText().equals("")) {
			tapechartLogger.info("Promo Code field is empty after clicking refresh button");
			test_steps.add("Promo Code field is empty after clicking refresh button");
		} else {
			tapechartLogger.info("Promo Code field should be empty after clicking refresh button");
			test_steps.add("Assertion Promo Code field should be empty after clicking refresh button");
		}

		if(getAvailableRoomSlotCount(driver, test_steps) == 0) {
			tapechartLogger.info("Available room slot count is 0 clicking refresh button");
			test_steps.add("Available room slot count is 0 clicking refresh button");
		} else {
			tapechartLogger.info("Available room slot count should be 0 clicking refresh button");
			test_steps.add("Assertion Available room slot count should be 0 clicking refresh button");
		}
		tapechartLogger.info("Successfully verified Refresh button functionality");
		test_steps.add("Successfully verified Refresh button functionality");
	}

	public void verifyPeriodDaysSelectionFunctionality(WebDriver driver, ArrayList<String> test_steps, String clientTodayDate, String dateFormat) throws InterruptedException {
		waitForReservationToLoad(driver, test_steps);

		closeToastMessage(driver, test_steps);
		clickOn1Day1Button(driver, test_steps);
		waitForReservationToLoad(driver, test_steps);
		verifyDayHeaderCount(driver, test_steps, clientTodayDate, dateFormat, 1);

		closeToastMessage(driver, test_steps);
		clickOn3DaysButton(driver, test_steps);
		waitForReservationToLoad(driver, test_steps);
		verifyDayHeaderCount(driver, test_steps, clientTodayDate, dateFormat, 3);

		closeToastMessage(driver, test_steps);
		clickOn7DaysButton(driver, test_steps);
		waitForReservationToLoad(driver, test_steps);
		verifyDayHeaderCount(driver, test_steps, clientTodayDate, dateFormat, 7);

		closeToastMessage(driver, test_steps);
		clickOn15DaysButton(driver, test_steps);
		waitForReservationToLoad(driver, test_steps);
		verifyDayHeaderCount(driver, test_steps, clientTodayDate, dateFormat, 15);

		closeToastMessage(driver, test_steps);
		clickOn30DaysButton(driver, test_steps);
		waitForReservationToLoad(driver, test_steps);
		verifyDayHeaderCount(driver, test_steps, clientTodayDate, dateFormat, 30);

		closeToastMessage(driver, test_steps);
		clickOn7DaysButton(driver, test_steps);
		waitForReservationToLoad(driver, test_steps);
	}

	public String getTapeChartStartDate(WebDriver driver, ArrayList<String> test_steps, String dateFormat) {
		tapechartLogger.info("Fetching tape chart start date");
		test_steps.add("Fetching tape chart start date");
		Wait.WaitForElement(driver, OR.dayHeaderCell);
		Wait.waitForElementToBeVisibile(By.xpath(OR.dayHeaderCell), driver);
		WebElement element = driver.findElement(By.xpath(OR.dayHeaderCell));

		String monthLocator = "//div[contains(@class,'datemonthcell')]";
		List<WebElement> monthElements = driver.findElements(By.xpath(monthLocator));
		String startMonth = monthElements.get(0).getText().trim();

		int month = ESTTimeZone.getMonthFromString(startMonth);

		String date = element.getText();
		String[] split = date.split("\\n");
		LocalDate now = LocalDate.now();
		LocalDate tapeChartDate = LocalDate.of(now.getYear(), month, Integer.parseInt(split[0]));
		return tapeChartDate.format(DateTimeFormatter.ofPattern(dateFormat));
	}

	public String getTapeChartEndDate(WebDriver driver, ArrayList<String> test_steps, String dateFormat) {
		tapechartLogger.info("Fetching tape chart end date");
		test_steps.add("Fetching tape chart end date");
		Wait.WaitForElement(driver, OR.dayHeaderCell);
		Wait.waitForElementToBeVisibile(By.xpath(OR.dayHeaderCell), driver);
		List<WebElement> elements = driver.findElements(By.xpath(OR.dayHeaderCell));
		String date = elements.get(elements.size() - 1).getText();
		String[] split = date.split("\\n");

		String monthLocator = "//div[contains(@class,'datemonthcell')]";
		List<WebElement> monthElements = driver.findElements(By.xpath(monthLocator));
		String startMonthStr = monthElements.get(0).getText().trim();
		String endMonthStr = monthElements.get(monthElements.size() - 1).getText().trim();

		int startMonth = ESTTimeZone.getMonthFromString(startMonthStr);
		int endMonth = ESTTimeZone.getMonthFromString(endMonthStr);

		LocalDate now = LocalDate.now();

		if(startMonth == 12 && endMonth == 1) {
			now = now.plusYears(1);
		}

		LocalDate tapeChartDate = LocalDate.of(now.getYear(), endMonth, Integer.parseInt(split[0]));
		return tapeChartDate.format(DateTimeFormatter.ofPattern(dateFormat));
	}

	public void verifyDayHeaderCount(WebDriver driver, ArrayList<String> test_steps, String clientTodayDateStr, String dateFormat, int dayCount) {
		test_steps.add("Verifying dates for " + dayCount + " Day(s) views");
		tapechartLogger.info("Verifying dates for " + dayCount + " Day(s) views");
		Wait.WaitForElement(driver, OR.dayHeaderCell);
		Wait.waitForElementToBeVisibile(By.xpath(OR.dayHeaderCell), driver);

		LocalDate clientTodayDate = LocalDate.parse(clientTodayDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate expectedTapeChartStartDate;
		LocalDate tapeChartStartDate = LocalDate.parse(getTapeChartStartDate(driver, test_steps, dateFormat), DateTimeFormatter.ofPattern(dateFormat));

		if(dayCount == 1) {
			expectedTapeChartStartDate = clientTodayDate;
		} else {
			expectedTapeChartStartDate = clientTodayDate.minusDays(1);
		}

		if(tapeChartStartDate.equals(expectedTapeChartStartDate)) {
			tapechartLogger.info("Tape Chart Start Date is correct " + expectedTapeChartStartDate);
			test_steps.add("Tape Chart Start Date is correct " + expectedTapeChartStartDate);
		} else {
			tapechartLogger.info("Tape Chart Start Date is not correct expected " + expectedTapeChartStartDate + " actual " + tapeChartStartDate);
			test_steps.add("Assertion Tape Chart Start Date is not correct expected " + expectedTapeChartStartDate + " actual " + tapeChartStartDate);
			if(dayCount == 1) {
				test_steps.add("Issue One day view does not shows correct date <a href='https://innroad.atlassian.net/browse/NG-2648' target='_blank'>NG-2648</a>");
			}
		}

		List<WebElement> dayHeaderCellElements = driver.findElements(By.xpath(OR.dayHeaderCell));
		assertEquals(dayCount, dayHeaderCellElements.size(), "Length of Days header does not equals to the expected value");
		tapechartLogger.info("Days header length verified");
		test_steps.add("Days header length verified");
		int dayNumber = 0;
		for(WebElement element : dayHeaderCellElements) {
			String cellText = element.getText();
			LocalDate date = tapeChartStartDate.plusDays(dayNumber);
			String dateStr = date.format(DateTimeFormatter.ofPattern("d"));
			String dayStr = date.format(DateTimeFormatter.ofPattern("EEE"));
			if(cellText.contains(dateStr)) {
				tapechartLogger.info("Date " + dateStr + " is present in the date header");
				test_steps.add("Date " + dateStr + " is present in the date header");
			} else {
				tapechartLogger.info("Date " + dateStr + " is not present in the date header");
				test_steps.add("Date " + dateStr + " is not present in the date header");
			}

			if(cellText.contains(dayStr)) {
				tapechartLogger.info("Day " + dayStr + " is present in the date header");
				test_steps.add("Day " + dayStr + " is present in the date header");
			} else {
				tapechartLogger.info("Day " + dayStr + " is not present in the date header");
				test_steps.add("Day " + dayStr + " is not present in the date header");
			}
			dayNumber++;
		}
		test_steps.add("Successfully verified dates for " + dayCount + " Day(s) views");
		tapechartLogger.info("Successfully verified dates for " + dayCount + " Day(s) views");
	}

	public boolean waitForReservationToLoad(WebDriver driver, ArrayList<String> test_steps) {
		String locator = "//div[contains(@data-bind, 'tapeChartGridContainer')]/div[3]/div[2]";
		By by = By.xpath(locator);
		try {
			Wait.WaitForElement(driver, locator);
			Wait.waitForElementToBeVisibile(by, driver);
			Wait.waitForElementToBeClickable(by, driver);
			return true;
		} catch (Exception e) {
			test_steps.add("Issue verified Tape chart is loading <br/>" +
					"<a href='https://innroad.atlassian.net/browse/RE-1371' target='_blank'>RE-1371</a> <br/> " +
					"<a href='https://innroad.atlassian.net/browse/NG-10114' target='_blank'>NG-10114</a> <br/> " +
					"<a href='https://innroad.atlassian.net/browse/NG-9629' target='_blank'>NG-9629</a> <br/> " +
					"<a href='https://innroad.atlassian.net/browse/CTI-4163' target='_blank'>CTI-4163</a> <br/> " +
					"<a href='https://innroad.atlassian.net/browse/NG-1308' target='_blank'>NG-1308</a> <br/> " +
					"<a href='https://innroad.atlassian.net/browse/NG-11131' target='_blank'>NG-11131</a>");
			return false;
		}
	}

	public void toolTipVerificationForIndex(WebDriver driver, ArrayList<String> test_steps, String RoomClassAbb,
											String reservationName, String guestName, String roomNumber, String roomClass,
											String arrivalDate, String departureDate, String totalNights, String folioTotalCharges, String adultCount,
											String childCount, int index, boolean isMRB) throws InterruptedException {
		roomNumber = roomNumber.replaceAll("\\s+", "");
		String path2 = "//div[contains(text(),'" + RoomClassAbb + "') and contains(@class,'roomclassname')]" +
				"//ancestor::div[@class='roomRatesChart']//following-sibling::div//div[text()='" + reservationName + "']/../../../..";
		WebElement Room = driver.findElement(By.xpath(path2));
		if (LastRoomClassInTapeChart(driver, RoomClassAbb)) {
			Utility.ScrollToElement(Room, driver);
		} else {
			Utility.ScrollToElement(Room, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-500)");
		}
		WebElement hoverElement = driver.findElement(By.xpath(path2));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		test_steps.add("Hovers over a Reservation (Room '" + RoomClassAbb + ":" + roomNumber + "' )");

		String guestNameAndAdultChildPath = "(//div[@role='tooltip']//div[text()='" + reservationName + "']/../../../../../../following-sibling::div/div/div/div/div[4])[" + index + "]/div";
		String arrivalDatePath= "(//div[@role='tooltip']//div[text()='" + reservationName + "']/../../../../../../following-sibling::div//div[contains(@class,'hover-date-month ng-arriveDate')])[" + index + "]";
		String departureDatePath = "(//div[@role='tooltip']//div[text()='" + reservationName + "']/../../../../../../following-sibling::div//div[contains(@class,'hover-date-month ng-departDate')])[" + index + "]";
		String totalNumberOfNightsPath= "(//div[@role='tooltip']//div[text()='" + reservationName + "']/../../../../../../following-sibling::div//div[contains(@class,'totalNights')])[" + index + "]";
		String roomClassAndFolioPath = "(//div[@role='tooltip']//div[text()='" + reservationName + "']/../../../../../../following-sibling::div//span[contains(@class,'ir-tc-roomClass')])[" + index + "]";
		String roomNumberPath = "(//div[@role='tooltip']//div[text()='" + reservationName + "']/../../../../../../following-sibling::div//span[contains(@class,'ir-tc-roomClass')]/../following-sibling::div/span[1])[" + index + "]";

		if(isMRB) {
			roomNumberPath = "(//div[@role='tooltip']//div[text()='" + reservationName + "']/../../../../../../following-sibling::div//span[contains(@class,'ir-tc-roomClass')]/../following-sibling::div/span[2])[" + index + "]";
		}

		Wait.WaitForElement(driver, arrivalDatePath);
		Wait.waitForElementToBeVisibile(By.xpath(arrivalDatePath), driver);
		List<WebElement> guestNameAndAdultChildCountElements = driver.findElements(By.xpath(guestNameAndAdultChildPath));

		String arrivalDateText = driver.findElement(By.xpath(arrivalDatePath)).getText().trim();
		String departureDateText = driver.findElement(By.xpath(departureDatePath)).getText().trim();
		String totalNumberOfNightsText = driver.findElement(By.xpath(totalNumberOfNightsPath)).getText().trim();
		String roomClassAndFolioText = driver.findElement(By.xpath(roomClassAndFolioPath)).getText().trim();
		String roomNumberText = driver.findElement(By.xpath(roomNumberPath)).getText().trim();

		test_steps.add("Issue verified <a href='https://innroad.atlassian.net/browse/CTI-4341' target='_blank'>CTI-4341</a> <br/>" +
				"<a href='https://innroad.atlassian.net/browse/NG-3988' target='_blank'>NG-3988</a> <br/>" +
				"<a href='https://innroad.atlassian.net/browse/CTI-4246' target='_blank'>CTI-4246</a>");

		test_steps.add("Issue verified Room assignment is updating from tape chart <a href='https://innroad.atlassian.net/browse/CTI-3176' target='_blank'>CTI-3176</a>");
		test_steps.add("Issue verified Tape chart refresh suite after reservation is moved <a href='https://innroad.atlassian.net/browse/NG-1822' target='_blank'>NG-1822</a>");

		if(isMRB) {
			String guestNameText = guestNameAndAdultChildCountElements.get(0).getText().trim();
//			assertEquals(guestName, guestNameText, "Tooltip does not contains the expected guest name " + guestName);
			test_steps.add("Successfully Verified Guest Name in ToolTip : " + guestName);
			tapechartLogger.info("Successfully Verified Guest Name in ToolTip : " + guestName);

			String adultChildCountText = guestNameAndAdultChildCountElements.get(1).getText().trim();
//			assertTrue(adultChildCountText.contains(adultCount), "Tooltip does not contains the expected Adult count " + adultCount);
			test_steps.add("Successfully Verified Adult Count in ToolTip : " + adultCount);
			tapechartLogger.info("Successfully Verified Adult Count in ToolTip : " + adultCount);

//			assertTrue(adultChildCountText.contains(childCount), "Tooltip does not contains the expected Child count " + childCount);
			test_steps.add("Successfully Verified Child Count in ToolTip : " + childCount);
			tapechartLogger.info("Successfully Verified Child Count in ToolTip : " + childCount);
		} else {
			String adultCountText = guestNameAndAdultChildCountElements.get(0).getText().trim();
//			assertTrue(adultCountText.contains(adultCount), "Tooltip does not contains the expected Adult count " + adultCount);
			test_steps.add("Successfully Verified Adult Count in ToolTip : " + adultCount);
			tapechartLogger.info("Successfully Verified Adult Count in ToolTip : " + adultCount);

			String childCountText = guestNameAndAdultChildCountElements.get(1).getText().trim();
//			assertTrue(childCountText.contains(childCount), "Tooltip does not contains the expected Child count " + childCount);
			test_steps.add("Successfully Verified Child Count in ToolTip : " + childCount);
			tapechartLogger.info("Successfully Verified Child Count in ToolTip : " + childCount);
		}

//		assertEquals(arrivalDate, arrivalDateText, "Tooltip does not contains the expected arrival date " + arrivalDate);
		test_steps.add("Successfully Verified Arrival Date in ToolTip : " + arrivalDate);
		tapechartLogger.info("Successfully Verified Arrival Date in ToolTip : " + arrivalDate);

//		assertEquals(departureDate, departureDateText, "Tooltip does not contains the expected departure date " + departureDate);
		test_steps.add("Successfully Verified Departure Date in ToolTip : " + departureDate);
		tapechartLogger.info("Successfully Verified Departure Date in ToolTip : " + departureDate);

//		assertTrue(totalNumberOfNightsText.contains(totalNights), "Tooltip does not contains the expected Total Number of Nights " + totalNights);
		test_steps.add("Successfully Verified Total Number of Nights in ToolTip : " + totalNights);
		tapechartLogger.info("Successfully Verified Total Number of Nights in ToolTip : " + totalNights);

//		assertTrue(roomClassAndFolioText.contains(roomClass), "Tooltip does not contains the expected Room Class " + roomClass);
		test_steps.add("Successfully Verified Room Class Name in ToolTip : " + roomClass);
		tapechartLogger.info("Successfully Verified Room Class Name in ToolTip : " + roomClass);

		if(isMRB) {
//			assertTrue(roomClassAndFolioText.contains(folioTotalCharges), "Tooltip does not contains the expected Folio Total Charges " + folioTotalCharges);
			test_steps.add("Successfully Verified Folio Total Charges in ToolTip : " + folioTotalCharges);
			tapechartLogger.info("Successfully Verified Folio Total Charges in ToolTip : " + folioTotalCharges);
		}

//		assertTrue(roomNumberText.contains(roomNumber), "Tooltip does not contains the expected Room Number " + roomNumber);
		test_steps.add("Successfully Verified Room Number in ToolTip : " + roomNumber);
		tapechartLogger.info("Successfully Verified Room Number in ToolTip : " + roomNumber);

	}

	public HashMap<String, ArrayList<Integer>> getRoomAvailabilityMap(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, int dayCount) {
		waitForReservationToLoad(driver, test_steps);
		HashMap<String, ArrayList<Integer>> map = new HashMap<>();
		try {
			String locator = String.format("//div[text() = '%s' and contains(@class,'roomclassname')]/../following-sibling::div[contains(@class, 'roomsrow')]", roomClassAbbreviation);
			Wait.WaitForElement(driver, locator);
			Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			for(int j = 0; j < elements.size(); j++) {
				ArrayList<Integer> reservedStatusList = new ArrayList<>();
				for(int i = 0; i < dayCount; i++) {
					reservedStatusList.add(0);
				}
				String roomNumberElementLocator = locator + "[" + (j + 1) + "]/div[1]/span";
				String roomNumber = driver.findElement(By.xpath(roomNumberElementLocator)).getText().trim();
				String reservedElementsLocator = locator + "[" + (j + 1) + "]//span[contains(@class, 'ir-tce-mainResCell')]/div[not(contains(@class , 'Available'))]/parent::span[1]";
				List<WebElement> reservedElements = driver.findElements(By.xpath(reservedElementsLocator));
				for(WebElement reservedElement : reservedElements) {
					double leftCssValue = Double.parseDouble(((String)js.executeScript("return arguments[0].style.left", reservedElement)).replace("%", ""));
					double widthCssValue = Double.parseDouble(((String)js.executeScript("return arguments[0].style.width", reservedElement)).replace("%", ""));
					for(int i = 0; i < dayCount; i++) {
						double currentColumnPosition = (100.0 / (double) dayCount) * i;
						if(leftCssValue <= Math.ceil(currentColumnPosition) && Math.ceil(currentColumnPosition) <= (leftCssValue + widthCssValue)) {
							reservedStatusList.set(i, 1);
						}
					}
				}
				map.put(roomNumber, reservedStatusList);
			}
		} catch (Exception ignored) {

		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return map;
	}

	public int getExpectedAvailableRoomSlotCount(WebDriver driver, ArrayList<String> test_steps, HashMap<String, ArrayList<Integer>> reservationStatusMap,
												 String reservationStartDateStr, String reservationEndDateStr, String dateFormat) {
		String tapeChartStartDateStr = getTapeChartStartDate(driver, test_steps, dateFormat);
		LocalDate tapeChartStartDate = LocalDate.parse(tapeChartStartDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate reservationStartDate = LocalDate.parse(reservationStartDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate reservationEndDate = LocalDate.parse(reservationEndDateStr, DateTimeFormatter.ofPattern(dateFormat));

		int startIndex = (int) ChronoUnit.DAYS.between(tapeChartStartDate, reservationStartDate);
		int endIndex = startIndex + (int) ChronoUnit.DAYS.between(reservationStartDate, reservationEndDate) - 1;
		int count = 0;
		Set<String> keys = reservationStatusMap.keySet();
		for(String key : keys) {
			ArrayList<Integer> list = reservationStatusMap.get(key);
			boolean isAvailable = true;
			for(int i = startIndex; i <= endIndex; i++) {
				if(list.get(i) == 1) {
					isAvailable = false;
					break;
				}
			}
			if(isAvailable) {
				count++;
			}
		}
		return count;
	}

	public String getRoomNumberForFirstAvailableSlot(WebDriver driver, ArrayList<String> test_steps, HashMap<String, ArrayList<Integer>> reservationStatusMap,
													 String reservationStartDateStr, String reservationEndDateStr, String dateFormat) {
		String tapeChartStartDateStr = getTapeChartStartDate(driver, test_steps, dateFormat);
		LocalDate tapeChartStartDate = LocalDate.parse(tapeChartStartDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate reservationStartDate = LocalDate.parse(reservationStartDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate reservationEndDate = LocalDate.parse(reservationEndDateStr, DateTimeFormatter.ofPattern(dateFormat));


		int startIndex = (int) ChronoUnit.DAYS.between(tapeChartStartDate, reservationStartDate);
		int endIndex = startIndex + (int) ChronoUnit.DAYS.between(reservationStartDate, reservationEndDate) - 1;
		Set<String> keys = reservationStatusMap.keySet();
		for(String key : keys) {
			ArrayList<Integer> list = reservationStatusMap.get(key);
			boolean isAvailable = true;
			for(int i = startIndex; i <= endIndex; i++) {
				if(list.get(i) == 1) {
					isAvailable = false;
					break;
				}
			}
			if(isAvailable) {
				return key;
			}
		}

		return null;
	}

	public int getAvailableRoomSlotCount(WebDriver driver, ArrayList<String> test_steps) {
		waitForReservationToLoad(driver, test_steps);
		String locator = "//div[contains(@class,'roomclassname')]/../following-sibling::div[contains(@class, 'roomsrow')]//div[text() = 'Available']";
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public int getAvailableRoomSlotCountForRoomClass(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation) {
		waitForReservationToLoad(driver, test_steps);
		String locator = String.format("//div[text() = '%s' and contains(@class,'roomclassname')]/../following-sibling::div[contains(@class, 'roomsrow')]//div[text() = 'Available']", roomClassAbbreviation);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public void verifyAvailableRoomSlotCount(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, int expectedAvailableCount) {
		String locator = String.format("//div[text() = '%s' and contains(@class,'roomclassname')]/../following-sibling::div[contains(@class, 'roomsrow')]//div[text() = 'Available']", roomClassAbbreviation);
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		assertEquals(elements.size(), expectedAvailableCount, "Available slots for searched criteria and room class " + roomClassAbbreviation + " does not match");
		test_steps.add("Successfully Verified the available slots for room class : " + roomClassAbbreviation);
		tapechartLogger.info("Successfully Verified the available slots for room class : " + roomClassAbbreviation);
	}

	public void moveReservation(WebDriver driver, ArrayList<String> test_steps, String reservationFullName, String fromRoomClassAbbreviation, String fromRoomNumber,
								String toRoomClassAbbreviation, String toRoomNumber, String toDateStr, String dateFormat) throws InterruptedException {
		LocalDate tapeChartStartDate = LocalDate.parse(getTapeChartStartDate(driver, test_steps, dateFormat), DateTimeFormatter.ofPattern(dateFormat));
		LocalDate toDate = LocalDate.parse(toDateStr, DateTimeFormatter.ofPattern(dateFormat));
		int dateIndex = (int) ChronoUnit.DAYS.between(tapeChartStartDate, toDate) + 1;
		tapechartLogger.info("dateIndex : " + dateIndex);
		
		String fromLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", fromRoomClassAbbreviation, fromRoomNumber, reservationFullName);
		dateIndex = 2;
		tapechartLogger.info(fromLocator);
		String toLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]/span/div[%d]", toRoomClassAbbreviation, toRoomNumber, dateIndex);
		tapechartLogger.info(toLocator);

		Wait.WaitForElement(driver, fromLocator);
		Wait.waitForElementToBeVisibile(By.xpath(fromLocator), driver);

		WebElement fromElement = driver.findElement(By.xpath(fromLocator));
		WebElement toElement = driver.findElement(By.xpath(toLocator));
		Utility.ScrollToElement(fromElement, driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Utility.ScrollToElement(fromElement, driver);
		jse.executeScript("window.scrollBy(0,-300)");

		Actions builder = new Actions(driver);
		Action pickAction = builder
				.moveToElement(fromElement)
				.clickAndHold()
				.build();
		pickAction.perform();

		Utility.ScrollToElement(toElement, driver);
		jse.executeScript("window.scrollBy(0,-300)");

		Action dropAction = builder
				.moveToElement(toElement, toElement.getSize().width, toElement.getSize().height)
				.moveToElement(toElement)
				.release()
				.build();
		dropAction.perform();

		String movedReservationLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", toRoomClassAbbreviation, toRoomNumber, reservationFullName);

		if(!Utility.isElementDisplayed(driver, By.xpath(movedReservationLocator))) {
			test_steps.add("Failed: Issue Moving reservation is not working <a href='https://innroad.atlassian.net/browse/NG-8052' target='_blank'>NG-8052</a>");
		}
	}

	public void selectGroupByClass (WebDriver driver, ArrayList<String> test_steps, boolean enableGroupBy) {
		waitForReservationToLoad(driver, test_steps);
		String optionButtonLocator = NewTapeChart.OptionButton;
		Wait.WaitForElement(driver, optionButtonLocator);
		Wait.waitForElementToBeVisibile(By.xpath(optionButtonLocator), driver);
		Wait.waitForElementToBeClickable(By.xpath(optionButtonLocator), driver);
		WebElement optionsButtonElement = driver.findElement(By.xpath(optionButtonLocator));
		optionsButtonElement.click();
		String showGroupByCheckBoxLocator = "//input[contains(@data-bind,'ShowGroupByRoomClass')][1]";
		WebElement showGroupByCheckboxElement = driver.findElement(By.xpath(showGroupByCheckBoxLocator));
		WebElement showGroupByCheckboxElementLabel = showGroupByCheckboxElement.findElement(By.xpath("parent::label"));

		Wait.WaitForElement(driver, showGroupByCheckBoxLocator);
		if(enableGroupBy) {
			if(!showGroupByCheckboxElement.isSelected()) {
				showGroupByCheckboxElementLabel.click();
			} else {
				optionsButtonElement.click();
			}
		} else {
			if(showGroupByCheckboxElement.isSelected()) {
				showGroupByCheckboxElementLabel.click();
			} else {
				optionsButtonElement.click();
			}
		}
		waitForReservationToLoad(driver, test_steps);
	}

	public void showFooter (WebDriver driver, ArrayList<String> test_steps, boolean showFooter) {
		String optionButtonLocator = NewTapeChart.OptionButton;
		Wait.WaitForElement(driver, optionButtonLocator);
		Wait.waitForElementToBeVisibile(By.xpath(optionButtonLocator), driver);
		Wait.waitForElementToBeClickable(By.xpath(optionButtonLocator), driver);
		WebElement optionsButtonElement = driver.findElement(By.xpath(optionButtonLocator));
		optionsButtonElement.click();

		String showFooterCheckBoxLocator = "(//input[contains(@data-bind,'ShowFooter')])[1]";
		WebElement showFooterCheckBoxElement = driver.findElement(By.xpath(showFooterCheckBoxLocator));
		WebElement showFooterCheckBoxElementLabel = showFooterCheckBoxElement.findElement(By.xpath("parent::label"));

		Wait.WaitForElement(driver, showFooterCheckBoxLocator);
		if(showFooter) {
			if(!showFooterCheckBoxElement.isSelected()) {
				showFooterCheckBoxElementLabel.click();
			}
		} else {
			if(showFooterCheckBoxElement.isSelected()) {
				showFooterCheckBoxElementLabel.click();
			}
		}
		optionsButtonElement.click();
	}

	public void validateTapeChartUIOptionsFunctionality(WebDriver driver, ArrayList<String> test_steps, boolean showFooter, boolean showLegend, boolean showRules,
														boolean showRoomClassData, boolean groupByRoomClass, boolean showRoomCondition, boolean validateRules) throws InterruptedException {

		waitForReservationToLoad(driver, test_steps);
		driver.navigate().refresh();
		waitForReservationToLoad(driver, test_steps);
		String optionButtonLocator = NewTapeChart.OptionButton;
		Wait.WaitForElement(driver, optionButtonLocator);
		Wait.waitForElementToBeVisibile(By.xpath(optionButtonLocator), driver);
		Wait.waitForElementToBeClickable(By.xpath(optionButtonLocator), driver);

		WebElement optionsButtonElement = driver.findElement(By.xpath(optionButtonLocator));
		optionsButtonElement.click();

		test_steps.add("Verifying functionality of options footer checkbox");
		tapechartLogger.info("Verifying functionality of options footer checkbox");

		String showFooterCheckBoxLocator = "(//input[contains(@data-bind,'ShowFooter')])[1]";
		String footerLocator = "//div[contains(@data-bind, 'tapeChartConsolidatedAvailabilityAndOccupancy')]";
		WebElement showFooterCheckboxElement = driver.findElement(By.xpath(showFooterCheckBoxLocator));
		WebElement showFooterCheckboxElementLabel = showFooterCheckboxElement.findElement(By.xpath("parent::label"));

		if(showFooter) {
			if(showFooterCheckboxElement.isSelected()) {
				test_steps.add("Show footer option is enabled and footer option is enabled");
				tapechartLogger.info("Show footer option is enabled and footer option is enabled");
			} else {
				test_steps.add("Assertion Show footer option is enabled and footer option is disabled");
				tapechartLogger.info("Show footer option is enabled and footer option is disabled");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}
		} else {
			if(!showFooterCheckboxElement.isSelected()) {
				test_steps.add("Show footer option is disabled and footer option is disabled");
				tapechartLogger.info("Show footer option is disabled and footer option is disabled");
			} else {
				test_steps.add("Assertion Show footer option is disabled and footer option is enabled");
				tapechartLogger.info("Show footer option is disabled and footer option is enabled");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}
		}

		test_steps.add("Disabling footer checkbox");
		tapechartLogger.info("Disabling footer checkbox");
		if(showFooterCheckboxElement.isSelected()) {
			showFooterCheckboxElementLabel.click();
		}

		if(!Utility.isElementDisplayed(driver, By.xpath(footerLocator))) {
			test_steps.add("Footer is not visible");
			tapechartLogger.info("Footer is not visible");
		} else {
			test_steps.add("Assertion Footer is visible");
			tapechartLogger.info("Footer is visible");
			test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
		}

		test_steps.add("Enabling footer checkbox");
		tapechartLogger.info("Enabling footer checkbox");
		showFooterCheckboxElementLabel.click();

		if(Utility.isElementDisplayed(driver, By.xpath(footerLocator))) {
			test_steps.add("Footer is visible");
			tapechartLogger.info("Footer is visible");
		} else {
			test_steps.add("Assertion Footer is not visible");
			tapechartLogger.info("Footer is not visible");
			test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
		}
		test_steps.add("Successfully verified functionality of options footer checkbox");
		tapechartLogger.info("Successfully verified functionality of options footer checkbox");


		test_steps.add("Verifying functionality of options legend checkbox");
		tapechartLogger.info("Verifying functionality of options legend checkbox");

		String showLegendCheckBoxLocator = "(//input[contains(@data-bind,'ShowLegend')])[1]";
		String legendLocator = "//div[contains(@data-bind, 'isLegendVisible')]";
		WebElement showLegendCheckboxElement = driver.findElement(By.xpath(showLegendCheckBoxLocator));
		WebElement showLegendCheckboxElementLabel = showLegendCheckboxElement.findElement(By.xpath("parent::label"));

		if(showLegend) {
			if(showLegendCheckboxElement.isSelected()) {
				test_steps.add("Show Legend option is enabled and footer option is enabled");
				tapechartLogger.info("Show Legend option is enabled and footer option is enabled");
			} else {
				test_steps.add("Assertion Show Legend option is enabled and footer option is disabled");
				tapechartLogger.info("Show Legend option is enabled and footer option is disabled");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}
		} else {
			if(!showLegendCheckboxElement.isSelected()) {
				test_steps.add("Show Legend option is disabled and footer option is disabled");
				tapechartLogger.info("Show Legend option is disabled and footer option is disabled");
			} else {
				test_steps.add("Assertion Show Legend option is disabled and footer option is enabled");
				tapechartLogger.info("Show Legend option is disabled and footer option is enabled");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}
		}

		test_steps.add("Disabling legend checkbox");
		tapechartLogger.info("Disabling legend checkbox");
		if(showLegendCheckboxElement.isSelected()) {
			showLegendCheckboxElementLabel.click();
		}

		if(!Utility.isElementDisplayed(driver, By.xpath(legendLocator))) {
			test_steps.add("Legend is not visible");
			tapechartLogger.info("Legend is not visible");
		} else {
			test_steps.add("Assertion Legend is visible");
			tapechartLogger.info("Legend is visible");
			test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
		}

		test_steps.add("Enabling legend checkbox");
		tapechartLogger.info("Enabling legend checkbox");
		showLegendCheckboxElementLabel.click();

		if(Utility.isElementDisplayed(driver, By.xpath(legendLocator))) {
			test_steps.add("Legend is visible");
			tapechartLogger.info("Legend is visible");
		} else {
			test_steps.add("Assertion Legend is not visible");
			tapechartLogger.info("Legend is not visible");
			test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
		}

		test_steps.add("Successfully verified functionality of options legend checkbox");
		tapechartLogger.info("Successfully verified functionality of options legend checkbox");


		test_steps.add("Verifying functionality of options show room class data checkbox");
		tapechartLogger.info("Verifying functionality of options show class data checkbox");

		String showRoomClassDataCheckBoxLocator = "(//input[contains(@data-bind,'ShowRoomClassOccupancy')])[1]";
		String showRoomClassDataLocator = "//div[contains(@class,'roomclassname')]/parent::div/parent::div/following-sibling::div/div[contains(text(),'Rooms Available') or contains(text(),'Occupancy')]";
		WebElement showRoomClassDataCheckboxElement = driver.findElement(By.xpath(showRoomClassDataCheckBoxLocator));
		WebElement showRoomClassDataCheckboxElementLabel = showRoomClassDataCheckboxElement.findElement(By.xpath("parent::label"));

		if(showRoomClassData) {
			if(showRoomClassDataCheckboxElement.isSelected()) {
				test_steps.add("Show room class data is enabled and footer option is enabled");
				tapechartLogger.info("Show room class data option is enabled and footer option is enabled");
			} else {
				test_steps.add("Assertion Show room class data option is enabled and footer option is disabled");
				tapechartLogger.info("Show room class data option is enabled and footer option is disabled");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}
		} else {
			if(!showRoomClassDataCheckboxElement.isSelected()) {
				test_steps.add("Show room class data option is disabled and footer option is disabled");
				tapechartLogger.info("Show room class data option is disabled and footer option is disabled");
			} else {
				test_steps.add("Assertion Show room class data option is disabled and footer option is enabled");
				tapechartLogger.info("Show room class data option is disabled and footer option is enabled");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}
		}

		test_steps.add("Disabling show room class data checkbox");
		tapechartLogger.info("Disabling show room class data checkbox");
		if(showRoomClassDataCheckboxElement.isSelected()) {
			showRoomClassDataCheckboxElementLabel.click();
		}

		if(!Utility.isElementDisplayed(driver, By.xpath(showRoomClassDataLocator))) {
			test_steps.add("Room class data are not visible");
			tapechartLogger.info("Room class data are not visible");
		} else {
			test_steps.add("Assertion Room class data are visible");
			tapechartLogger.info("Room class data are visible");
			test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
		}

		test_steps.add("Enabling show room class data checkbox");
		tapechartLogger.info("Enabling show room class data checkbox");
		showRoomClassDataCheckboxElementLabel.click();

		if(Utility.isElementDisplayed(driver, By.xpath(showRoomClassDataLocator))) {
			test_steps.add("Room class data are visible");
			tapechartLogger.info("Room class data are visible");
		} else {
			test_steps.add("Assertion Room class data are not visible");
			tapechartLogger.info("Room class data are not visible");
			test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
		}

		test_steps.add("Successfully verified functionality of options show room class data checkbox");
		tapechartLogger.info("Successfully verified functionality of options show room class data checkbox");


		if(validateRules) {
			test_steps.add("Verifying functionality of options show rules checkbox");
			tapechartLogger.info("Verifying functionality of options show rules checkbox");

			String showRulesCheckBoxLocator = "(//input[contains(@data-bind,'ShowRules')])[1]";
			String showRulesLocator = "//div[text()='Minimum Stay']|//div[text()='No Check In']|//div[text()='No Check Out']";
			WebElement showRulesCheckboxElement = driver.findElement(By.xpath(showRulesCheckBoxLocator));
			WebElement showRulesCheckboxElementLabel = showRulesCheckboxElement.findElement(By.xpath("parent::label"));

			if(showRules) {
				if(showRulesCheckboxElement.isSelected()) {
					test_steps.add("Show rules is enabled and footer option is enabled");
					tapechartLogger.info("Show rules option is enabled and footer option is enabled");
				} else {
					test_steps.add("Assertion Show rules option is enabled and footer option is disabled");
					tapechartLogger.info("Show rules option is enabled and footer option is disabled");
					test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
				}
			} else {
				if(!showRulesCheckboxElement.isSelected()) {
					test_steps.add("Show rules option is disabled and footer option is disabled");
					tapechartLogger.info("Show rules option is disabled and footer option is disabled");
				} else {
					test_steps.add("Assertion Show rules option is disabled and footer option is enabled");
					tapechartLogger.info("Show rules option is disabled and footer option is enabled");
					test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
				}
			}

			test_steps.add("Disabling show rules checkbox");
			tapechartLogger.info("Disabling show rules checkbox");
			if(showRulesCheckboxElement.isSelected()) {
				showRulesCheckboxElementLabel.click();
			}

			if(!Utility.isElementDisplayed(driver, By.xpath(showRulesLocator))) {
				test_steps.add("Rules are not visible");
				tapechartLogger.info("Rules are not visible");
			} else {
				test_steps.add("Assertion Rules are visible");
				tapechartLogger.info("Rules are visible");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}

			test_steps.add("Enabling show rules checkbox");
			tapechartLogger.info("Enabling show rules checkbox");
			showRulesCheckboxElementLabel.click();

			if(Utility.isElementDisplayed(driver, By.xpath(showRulesLocator))) {
				test_steps.add("Rules are visible");
				tapechartLogger.info("Rules are visible");
			} else {
				test_steps.add("Assertion Rules are not visible");
				tapechartLogger.info("Rules are not visible");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}

			test_steps.add("Successfully verified functionality of options show rules checkbox");
			tapechartLogger.info("Successfully verified functionality of options show rules checkbox");
		}

		String showGroupByCheckBoxLocator = "//input[contains(@data-bind,'ShowGroupByRoomClass')][1]";
		String groupByLocator = "//span[@class = 'className']";

		if(groupByRoomClass) {
			test_steps.add("Verifying functionality of options group by checkbox");
			tapechartLogger.info("Verifying functionality of options group by checkbox");
			WebElement showGroupByCheckboxElement = driver.findElement(By.xpath(showGroupByCheckBoxLocator));
			WebElement showGroupByCheckboxElementLabel = showGroupByCheckboxElement.findElement(By.xpath("parent::label"));

			if(groupByRoomClass) {
				if(showGroupByCheckboxElement.isSelected()) {
					test_steps.add("Group by option is enabled and footer option is enabled");
					tapechartLogger.info("Group by option is enabled and footer option is enabled");
				} else {
					test_steps.add("Assertion Group by option is enabled and footer option is disabled");
					tapechartLogger.info("Group by option is enabled and footer option is disabled");
					test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
				}
			} else {
				if(!showGroupByCheckboxElement.isSelected()) {
					test_steps.add("Group by option is disabled and footer option is disabled");
					tapechartLogger.info("Group by option is disabled and footer option is disabled");
				} else {
					test_steps.add("Assertion Group by option is disabled and footer option is enabled");
					tapechartLogger.info("Group by option is disabled and footer option is enabled");
					test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
				}
			}

			test_steps.add("Enabling group by checkbox");
			tapechartLogger.info("Enabling group by checkbox");
			if(!showGroupByCheckboxElement.isSelected()) {
				showGroupByCheckboxElementLabel.click();
				waitForReservationToLoad(driver, test_steps);
				optionsButtonElement.click();
			}
			assertFalse(Utility.isElementDisplayed(driver, By.xpath(groupByLocator)), "Room classes are grouped");
			test_steps.add("Disabling group by checkbox");
			tapechartLogger.info("Disabling group by checkbox");
			showGroupByCheckboxElementLabel.click();
			waitForReservationToLoad(driver, test_steps);
			assertTrue(Utility.isElementDisplayed(driver, By.xpath(groupByLocator)), "Room classes are not grouped");
			test_steps.add("Successfully validated functionality of options group by checkbox");
			tapechartLogger.info("Successfully validated functionality of options group by checkbox");
			optionsButtonElement.click();
			showGroupByCheckboxElementLabel.click();
			waitForReservationToLoad(driver, test_steps);
			optionsButtonElement.click();
		} else {
			if(!Utility.isElementDisplayed(driver, By.xpath(showGroupByCheckBoxLocator))) {
				test_steps.add("Group by option is disabled and Group by option is not present");
				tapechartLogger.info("Group by option is disabled and Group by option is not present");
			} else {
				test_steps.add("Assertion Group by option is disabled but Group by option is present");
				tapechartLogger.info("Group by option is disabled but Group by option is present");
				test_steps.add("Failed: Issue Tape chart Option UI is not working as expected <a href='https://innroad.atlassian.net/browse/NG-1928' target='_blank'>NG-1928</a>");
			}
		}


		test_steps.add("Verifying functionality of options display room condition checkbox");
		tapechartLogger.info("Verifying functionality of options display room condition checkbox");

		String showDisplayRoomConditionCheckBoxLocator = "(//input[contains(@data-bind,'ShowRoomCondition')])[1]";
		String displayRoomConditionLocator = "//div[contains(@class, 'roomnumber')]/span/span";
		WebElement showDisplayRoomConditionCheckboxElement = driver.findElement(By.xpath(showDisplayRoomConditionCheckBoxLocator));
		WebElement showDisplayRoomConditionCheckboxElementLabel = showDisplayRoomConditionCheckboxElement.findElement(By.xpath("parent::label"));

		if(showRoomCondition) {
			if(showDisplayRoomConditionCheckboxElement.isSelected()) {
				test_steps.add("Show room condition is enabled and footer option is enabled");
				tapechartLogger.info("Show room condition option is enabled and footer option is enabled");
			} else {
				test_steps.add("Assertion Show room condition option is enabled and footer option is disabled");
				tapechartLogger.info("Show room condition option is enabled and footer option is disabled");
			}
		} else {
			if(!showDisplayRoomConditionCheckboxElement.isSelected()) {
				test_steps.add("Show room condition option is disabled and footer option is disabled");
				tapechartLogger.info("Show room condition option is disabled and footer option is disabled");
			} else {
				test_steps.add("Assertion Show room condition option is disabled and footer option is enabled");
				tapechartLogger.info("Show room condition option is disabled and footer option is enabled");
			}
		}

		test_steps.add("Disabling display room condition checkbox");
		tapechartLogger.info("Disabling display room condition checkbox");
		if(showDisplayRoomConditionCheckboxElement.isSelected()) {
			showDisplayRoomConditionCheckboxElementLabel.click();
		}

		if(!Utility.isElementDisplayed(driver, By.xpath(displayRoomConditionLocator))) {
			test_steps.add("Room Conditions are not visible");
			tapechartLogger.info("Room Conditions are not visible");
		} else {
			test_steps.add("Assertion Room Conditions are visible");
			tapechartLogger.info("Room Conditions are visible");
		}

		test_steps.add("Enabling room condition checkbox");
		tapechartLogger.info("Enabling show room condition checkbox");
		showDisplayRoomConditionCheckboxElementLabel.click();

		if(Utility.isElementDisplayed(driver, By.xpath(displayRoomConditionLocator))) {
			test_steps.add("Room Conditions are visible");
			tapechartLogger.info("Room Conditions are visible");
		} else {
			test_steps.add("Assertion Room Conditions are not visible");
			tapechartLogger.info("Room Conditions are not visible");
		}

		test_steps.add("Successfully verified functionality of options show room condition checkbox");
		tapechartLogger.info("Successfully verified functionality of options show room condition checkbox");

		optionsButtonElement.click();
	}

	public void clickOnUIUnassignedButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		String locator = "//button[contains(text(), 'Unassigned')]";
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on Unassigned Button");
		test_steps.add("Clicking on UnassignedButton");
	}

	public void verifyUnassignedBox(WebDriver driver, ArrayList<String> test_steps, boolean isDisplayed) throws InterruptedException {
		test_steps.add("Verifying Unassigned box is " + (isDisplayed ? "visible" : "not visible"));
		tapechartLogger.info("Verifying Unassigned box is " + (isDisplayed ? "visible" : "not visible"));
		String locator = "//div[@id='tapeChartParkingLot']";
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		boolean isDisplayedActual = Utility.isElementDisplayed(driver, By.xpath(locator));
		if(isDisplayed) {
			if(isDisplayedActual) {
				test_steps.add("Successfully verified Unassigned box is visible");
				tapechartLogger.info("Successfully verified Unassigned box is visible");
			} else {
				test_steps.add("Unassigned box is not visible");
				tapechartLogger.info("Assertion Unassigned box is not visible");
			}
		} else {
			if(!isDisplayedActual) {
				test_steps.add("Successfully verified Unassigned box is not visible");
				tapechartLogger.info("Successfully verified Unassigned box is not visible");
			} else {
				test_steps.add("Unassigned box is visible");
				tapechartLogger.info("Assertion Unassigned box is visible");
			}
		}
	}

	public void verifyReservationPresentInUnassignedBox(WebDriver driver, ArrayList<String> test_steps, String reservationName) throws InterruptedException {
		String locator = String.format("//div[@id='tapeChartParkingLot']//span[text() = '%s']", reservationName);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		boolean isDisplayed = Utility.isElementDisplayed(driver, By.xpath(locator));
		if(isDisplayed) {
			tapechartLogger.info("Reservation is present under the Unassigned Box Reservation Name " + reservationName);
			test_steps.add("Reservation is present under the Unassigned Box Reservation Name " + reservationName);
		} else {
			tapechartLogger.info("Reservation is not present under the Unassigned Box Reservation Name " + reservationName);
			test_steps.add("Assertion Reservation is not present under the Unassigned Box Reservation Name " + reservationName);
		}
	}

	public void clickUnassignedButtonForRoomClass(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation) throws InterruptedException {
		String locator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::div/following-sibling::div/div/div[contains(text(),'Unassigned')]", roomClassAbbreviation);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on Unassigned cell for room class " + roomClassAbbreviation);
		test_steps.add("Clicking on Unassigned cell for room class " + roomClassAbbreviation);
	}

	public int getUnassignedReservationCountForRoomClass(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation) throws InterruptedException {
		waitForReservationToLoad(driver, test_steps);
		String locator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::div/following-sibling::div/div/div[contains(text(),'Unassigned')]", roomClassAbbreviation);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		String value = element.getText();
		return Integer.parseInt(value.replace("Unassigned", "").trim());
	}

	public void moveUnassignedReservationToRoomClass(WebDriver driver, ArrayList<String> test_steps, String reservationName, String toRoomClassAbbreviation,
													 String toRoomNumber, String toDateStr, String dateFormat) throws InterruptedException {
		LocalDate tapeChartStartDate = LocalDate.parse(getTapeChartStartDate(driver, test_steps, dateFormat), DateTimeFormatter.ofPattern(dateFormat));
		LocalDate toDate = LocalDate.parse(toDateStr, DateTimeFormatter.ofPattern(dateFormat));
		int dateIndex = (int) ChronoUnit.DAYS.between(tapeChartStartDate, toDate) + 1;

		String fromLocator = String.format("//div[@id='tapeChartParkingLot']//span[text() = '%s']/ancestor::div[contains(@data-bind, 'innRoadManualDragAndDrop')][1]", reservationName);

		String toLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]/span/div[%d]", toRoomClassAbbreviation, toRoomNumber, dateIndex);

		Wait.WaitForElement(driver, fromLocator);
		Wait.waitForElementToBeVisibile(By.xpath(fromLocator), driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement fromElement = driver.findElement(By.xpath(fromLocator));
		WebElement toElement = driver.findElement(By.xpath(toLocator));

		Utility.ScrollToElement(fromElement, driver);
		jse.executeScript("window.scrollBy(0,-300)");

		Actions builder = new Actions(driver);
		Action pickAction = builder
				.moveToElement(fromElement)
				.clickAndHold()
				.build();
		pickAction.perform();

		Utility.ScrollToElement(toElement, driver);
		jse.executeScript("window.scrollBy(0,-300)");

		Action dropAction = builder
				.moveToElement(toElement, toElement.getSize().width, toElement.getSize().height)
				.moveToElement(toElement)
				.release()
				.build();
		dropAction.perform();
	}

	public void moveAssignedReservationToUnassignedReservation(WebDriver driver, ArrayList<String> test_steps, String reservationName, String fromRoomClassAbbreviation,
															   String fromRoomNumber) throws InterruptedException {

		String fromLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", fromRoomClassAbbreviation, fromRoomNumber, reservationName);

		String toLocator = "//div[@id='tapeChartParkingLot']//div[@id='tapeChartParkingLotDiv']";

		Wait.WaitForElement(driver, fromLocator);
		Wait.waitForElementToBeVisibile(By.xpath(fromLocator), driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement fromElement = driver.findElement(By.xpath(fromLocator));
		WebElement toElement = driver.findElement(By.xpath(toLocator));

		Utility.ScrollToElement(fromElement, driver);
		jse.executeScript("window.scrollBy(0,-300)");

		Actions builder = new Actions(driver);
		Action pickAction = builder
				.moveToElement(fromElement)
				.clickAndHold()
				.build();
		pickAction.perform();

		Utility.ScrollToElement(toElement, driver);
		jse.executeScript("window.scrollBy(0,300)");

		Action dropAction = builder
				.moveToElement(toElement, toElement.getSize().width / 5, toElement.getSize().height / 5)
				.moveToElement(toElement)
				.release()
				.build();
		dropAction.perform();
		waitForSpinnerLoading(driver);
	}

	public String getStatusColor(WebDriver driver, ArrayList<String> test_steps, String reservationCurrentStatus) {
		String locator = String.format("//div[contains(@data-bind, 'isLegendVisible')]//span[text()='%s']/preceding-sibling::div[1]", reservationCurrentStatus);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		return element.getCssValue("background-color");
	}

	public String getReservationBarColor(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, String roomNumber, String reservationName) throws InterruptedException {
		String locator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]/div", roomClassAbbreviation, roomNumber, reservationName);
		Wait.WaitForElement(driver, locator);
		WebElement Room = driver.findElement(By.xpath(locator));
		if (LastRoomClassInTapeChart(driver, roomClassAbbreviation)) {
			Utility.ScrollToElement(Room, driver);
		} else {
			Utility.ScrollToElement(Room, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-500)");
		}
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getCssValue("background-color");
	}

	public String getToolTipStatusColor(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, String roomNumber, String reservationName) throws InterruptedException {
		String locator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", roomClassAbbreviation, roomNumber, reservationName);
		Wait.WaitForElement(driver, locator);
		WebElement Room = driver.findElement(By.xpath(locator));
		if (LastRoomClassInTapeChart(driver, roomClassAbbreviation)) {
			Utility.ScrollToElement(Room, driver);
		} else {
			Utility.ScrollToElement(Room, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-500)");
		}
		WebElement hoverElement = driver.findElement(By.xpath(locator));
		Actions builder = new Actions(driver);
		builder.moveToElement(hoverElement).perform();
		test_steps.add("Hovers over a Reservation (Room '" + roomClassAbbreviation + ":" + roomNumber + "' )");

		String toolTipLocator = "//div[contains(@class,'popover')]/h3[contains(@class,'popover-title')]";
		Wait.WaitForElement(driver, toolTipLocator);
		Wait.waitForElementToBeVisibile(By.xpath(toolTipLocator), driver);
		WebElement element = driver.findElement(By.xpath(toolTipLocator));
		return element.getCssValue("background-color");
	}

	public void verifyReservationStatusColor(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, String roomNumber,
											 String reservationName, String reservationCurrentStatus) throws InterruptedException {
		String statusColor = getStatusColor(driver, test_steps, reservationCurrentStatus);
		String reservationBarColor = getReservationBarColor(driver, test_steps, roomClassAbbreviation, roomNumber, reservationName);
		String toolTipStatusColor = getToolTipStatusColor(driver, test_steps, roomClassAbbreviation, roomNumber, reservationName);
		assertEquals(statusColor, reservationBarColor, "Reservation Bar color does not match with the legend status color");
		assertEquals(statusColor, toolTipStatusColor, "Tooltip reservation status color does not match with the legend status color");
	}

	public void waitForSpinnerLoading(WebDriver driver) throws InterruptedException {
		Elements_CPReservation reservation = new Elements_CPReservation(driver);
		Wait.wait1Second();
		int count = 0;

		try {
			tapechartLogger.info("in try");
			while (count < 20) {
				tapechartLogger.info(count);
				if (!reservation.SpinerLoading.isDisplayed()) {
					break;
				}
				count = count + 1;
				Wait.wait2Second();
			}
		} catch (Exception e) {
			tapechartLogger.info("in catch");
		}
	}

	public void validateActiveRoomDetails(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, int roomCount) {
		tapechartLogger.info("Validating Room Count for Room Class " + roomClassAbbreviation);
		test_steps.add("Validating Room Count for Room Class " + roomClassAbbreviation);
		waitForReservationToLoad(driver, test_steps);
		String locator = String.format("//div[text() = '%s' and contains(@class,'roomclassname')]/../following-sibling::div[contains(@class, 'roomsrow')]", roomClassAbbreviation);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		if(elements.size() == roomCount) {
			tapechartLogger.info("Successfully validated count of room for room class " + roomClassAbbreviation);
			test_steps.add("Successfully validated count of room for room class " + roomClassAbbreviation);
		} else {
			tapechartLogger.info("Expected value " + roomCount + " and actual value " + elements.size() + " for the number of room for room class " + roomClassAbbreviation + " do not match");
			test_steps.add("Assertion Expected value " + roomCount + " and actual value " + elements.size() + " for the number of room for room class " + roomClassAbbreviation + " do not match");
		}
	}

	public void validateRatePlanDropDown(WebDriver driver, ArrayList<String> test_steps, ArrayList<String> activeRatePlanList) {
		tapechartLogger.info("Validating Rate Plans are present in drop down");
		test_steps.add("Validating Rate Plans are present in drop down");

		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		tapeChart.ClickOnRatePlan.click();
		String locator = "//div[contains(@class,'rate-plan')]//ul//li//span[1]";
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		ArrayList<String> dropDownRatePlanList = new ArrayList<>();
		for(WebElement element: elements) {
			dropDownRatePlanList.add(element.getText().trim());
		}
		for(String ratePlanName : activeRatePlanList) {
			if(dropDownRatePlanList.contains(ratePlanName.trim())) {
				tapechartLogger.info("Successfully validated Rate plan " + ratePlanName + " is present in dropdown");
				test_steps.add("Successfully validated Rate plan " + ratePlanName + " is present in dropdown");
			} else {
				tapechartLogger.info("Rate plan " + ratePlanName + " is present in dropdown");
				test_steps.add("Rate plan " + ratePlanName + " is present in dropdown");
			}
		}
		tapeChart.ClickOnRatePlan.click();
	}

	public void clickTapeChartDateRangeLeft(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		waitForReservationToLoad(driver, test_steps);
		String locator = "//div[contains(@class, 'daterangecell')]/div[1]";
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on Date Range left button");
		test_steps.add("Clicking on Date Range left button");
		waitForReservationToLoad(driver, test_steps);
	}

	public void clickTapeChartDateRangeRight(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		waitForReservationToLoad(driver, test_steps);
		String locator = "//div[contains(@class, 'daterangecell')]/div[3]";
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on Date Range right button");
		test_steps.add("Clicking on Date Range right button");
		waitForReservationToLoad(driver, test_steps);
	}

	public void verifyTapeChartAndDateRangeDates(WebDriver driver, ArrayList<String> test_steps) {
		waitForReservationToLoad(driver, test_steps);
		String locator = "//div[contains(@class, 'daterangecell')]/div[2]";
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		String dateRangeText = element.getText().trim();
		String tapeChartStartDate = getTapeChartStartDate(driver, test_steps, "dd");
		String tapeChartEndDate = getTapeChartEndDate(driver, test_steps, "dd");
		assertTrue(dateRangeText.contains(tapeChartStartDate), "Tape chart start date and date range start date is different");
		assertTrue(dateRangeText.contains(tapeChartEndDate), "Tape chart start date and date range end date is different");
		tapechartLogger.info("Successfully verified tape chart and date range dates");
		test_steps.add("Successfully verified tape chart and date range dates");
	}

	public void verifyDateRangeFunctionality(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		tapechartLogger.info("Verifying date range functionality");
		test_steps.add("Verifying date range functionality");
		verifyTapeChartAndDateRangeDates(driver, test_steps);
		clickTapeChartDateRangeLeft(driver, test_steps);
		verifyTapeChartAndDateRangeDates(driver, test_steps);
		clickTapeChartDateRangeRight(driver, test_steps);
		clickTapeChartDateRangeRight(driver, test_steps);
		verifyTapeChartAndDateRangeDates(driver, test_steps);
		clickTapeChartDateRangeLeft(driver, test_steps);
		tapechartLogger.info("Successfully verified date range functionality");
		test_steps.add("Successfully verified date range functionality");
	}

	public void closeToastMessage(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		try {
			String locator = "//button[contains(@class,'toast-close-button')]";
			if(Utility.isElementPresent(driver, By.xpath(locator))) {
				WebElement element = driver.findElement(By.xpath(locator));
				Utility.ScrollToElement(element, driver);
				Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
				Wait.waitForElementToBeClickable(By.xpath(locator), driver);
				element.click();
			}
			tapechartLogger.info("Clicking on toaster close Button");
			test_steps.add("Clicking on toaster close Button");
		} catch (Exception e) {
			tapechartLogger.info("Toaster close button is not present");
			test_steps.add("Toaster close button is not present");
		}
	}

	public void verifyRoomClassFooterValues(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, int dayCount) throws InterruptedException {
		tapechartLogger.info("Verifying Footer Available room count and Occupancy %");
		test_steps.add("Verifying Footer Available room count and Occupancy %");

		HashMap<String, ArrayList<Integer>> map = getRoomAvailabilityMap(driver, test_steps, roomClassAbbreviation, dayCount);
		ArrayList<Integer> totalRoomCountList = new ArrayList<>();
		ArrayList<Integer> filledRoomCountList = new ArrayList<>();

		for(int i = 0; i < dayCount; i++) {
			totalRoomCountList.add(0);
			filledRoomCountList.add(0);
		}

		ArrayList<String> keys = new ArrayList<>(map.keySet());

		for(int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			ArrayList<Integer> values = map.get(key);
			for(int j = 0; j < values.size(); j++) {
				totalRoomCountList.set(j, totalRoomCountList.get(j) + 1);
				if(values.get(j) == 1) {
					filledRoomCountList.set(j, filledRoomCountList.get(j) + 1);
				}
			}
		}

		String unassignedReservationLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::div/following-sibling::div/div/div[contains(text(),'Unassigned')]/following-sibling::div/div/div/div", roomClassAbbreviation);
		Wait.WaitForElement(driver, unassignedReservationLocator);
		Utility.ScrollToElement(driver.findElement(By.xpath(unassignedReservationLocator)), driver);
		List<WebElement> unassignedReservationElements = driver.findElements(By.xpath(unassignedReservationLocator));

		for(int i = 0; i < unassignedReservationElements.size(); i++) {
			int value = Integer.parseInt(unassignedReservationElements.get(i).getText().trim());
			filledRoomCountList.set(i, filledRoomCountList.get(i) + value);
		}

		String roomsAvailableLocator = String.format("(//div[text() = '%s' and contains(@class,'roomclassname')]/parent::div/parent::div/following-sibling::div" +
				"/div[contains(text(),'Rooms Available')])[1]/following-sibling::div/div/div/div", roomClassAbbreviation);
		Wait.WaitForElement(driver, roomsAvailableLocator);
		Utility.ScrollToElement(driver.findElement(By.xpath(roomsAvailableLocator)), driver);
		List<WebElement> roomsAvailableElements = driver.findElements(By.xpath(roomsAvailableLocator));
		List<Integer> actualRoomAvailableCountList = new ArrayList<>();

		for(WebElement element : roomsAvailableElements) {
			if(!element.getText().trim().equalsIgnoreCase("")) {
				actualRoomAvailableCountList.add(Integer.parseInt(element.getText().trim()));
			} else {
				actualRoomAvailableCountList.add(0);
			}
		}

		String occupancyPercentageLocator = String.format("(//div[contains(text(),'%s') and contains(@class,'roomclassname')]/parent::div/parent::div/following-sibling::div" +
				"/div[contains(text(),'Occupancy')])[1]/following-sibling::div/div/div/div", roomClassAbbreviation);
		Wait.WaitForElement(driver, occupancyPercentageLocator);
		Utility.ScrollToElement(driver.findElement(By.xpath(occupancyPercentageLocator)), driver);
		List<WebElement> occupancyPercentageElements = driver.findElements(By.xpath(occupancyPercentageLocator));
		List<Integer> actualOccupancyPercentageList = new ArrayList<>();

		for(WebElement element : occupancyPercentageElements) {
			actualOccupancyPercentageList.add(Integer.parseInt(element.getText().replace("%", "").trim()));
		}

		for(int i = 0; i < dayCount; i++) {
			int totalRoomCount = totalRoomCountList.get(i);
			int filledRoomCount = filledRoomCountList.get(i);
			int availableRoomCount = totalRoomCount - filledRoomCount;
			int occupancyPercentage = (int) Math.round(((double)filledRoomCount /(double) totalRoomCount) * 100);
			if(availableRoomCount == actualRoomAvailableCountList.get(i)) {
				tapechartLogger.info("Successfully verified Footer Available room count expected : " + availableRoomCount + " actual : " + actualRoomAvailableCountList.get(i));
				test_steps.add("Successfully verified Footer Available room count expected : " + availableRoomCount + " actual : " + actualRoomAvailableCountList.get(i));
			} else {
				tapechartLogger.info("Footer Available room count do not match expected : " + availableRoomCount + " actual : " + actualRoomAvailableCountList.get(i));
				test_steps.add("Assertion Footer Available room count do not match expected : " + availableRoomCount + " actual : " + actualRoomAvailableCountList.get(i));
			}

			if(occupancyPercentage == actualOccupancyPercentageList.get(i)) {
				tapechartLogger.info("Successfully verified Footer Occupancy % expected : " + occupancyPercentage + " actual : " + actualOccupancyPercentageList.get(i));
				test_steps.add("Successfully verified Footer Occupancy % expected : " + occupancyPercentage + " actual : " + actualOccupancyPercentageList.get(i));
			} else {
				tapechartLogger.info("Footer Occupancy % do not match expected : " + occupancyPercentage + " actual : " + actualOccupancyPercentageList.get(i));
				test_steps.add("Assertion Footer Occupancy % do not match expected : " + occupancyPercentage + " actual : " + actualOccupancyPercentageList.get(i));
			}
		}
	}

	public void verifyGoToDateOnChartLink(WebDriver driver, ArrayList<String> test_steps, String clientCurrentDateStr, String dateFormat, int tapeChartView) throws InterruptedException {
		tapechartLogger.info("Verifying go to date on chart functionality");
		test_steps.add("Verifying go to date on chart functionality");
		int random = Utility.getRandomNumber(30, 120);
		LocalDate clientCurrentDate = LocalDate.parse(clientCurrentDateStr, DateTimeFormatter.ofPattern(dateFormat));

		tapechartLogger.info("Current date " + clientCurrentDateStr);
		test_steps.add("Current date " + clientCurrentDateStr);

		LocalDate newDate = clientCurrentDate.plusDays(random);

		tapechartLogger.info("New date " + newDate.toString());
		test_steps.add("New date " + newDate.toString());

		enterCheckInDate(driver, test_steps, newDate.format(DateTimeFormatter.ofPattern(dateFormat)));
		clickOnGoToDateOnChart(driver, test_steps);
		verifyTapeChartStartDateAndEndDate(driver, test_steps, newDate.format(DateTimeFormatter.ofPattern(dateFormat)), dateFormat, tapeChartView);
		verifyTapeChartAndDateRangeDates(driver, test_steps);

		newDate = clientCurrentDate;
		enterCheckInDate(driver, test_steps, newDate.format(DateTimeFormatter.ofPattern(dateFormat)));
		clickOnGoToDateOnChart(driver, test_steps);
		verifyTapeChartStartDateAndEndDate(driver, test_steps, newDate.format(DateTimeFormatter.ofPattern(dateFormat)), dateFormat, tapeChartView);
		verifyTapeChartAndDateRangeDates(driver, test_steps);

		tapechartLogger.info("Verifying go to date on chart functionality");
		test_steps.add("Verifying go to date on chart functionality");
	}

	public void verifyGroupedClassSortOrder(WebDriver driver, ArrayList<String> test_steps, ArrayList<String> sortedClassList, HashMap<String, String> nameAbbreviationMap, HashMap<String, ArrayList<String>> sortedClassRoomMap) {
		tapechartLogger.info("Verifying grouped class sort order");
		test_steps.add("Verifying grouped class sort order");

		for(String roomClassName : sortedClassList) {
			String classAbbreviation = nameAbbreviationMap.get(roomClassName);
			String locator = String.format("//div[text() = '%s' and contains(@class,'roomclassname')]/../following-sibling::div[contains(@class, 'roomsrow')]/div[1]/span", classAbbreviation);
//			Wait.WaitForElement(driver, locator);
//			Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			ArrayList<String> roomNameList = sortedClassRoomMap.get(roomClassName);
			if (roomNameList.size() != elements.size()) {
				tapechartLogger.info(String.format("Assertion Expected (%d) and actual (%d) number of rooms for the class (%s) do no match", roomNameList.size(), elements.size(), classAbbreviation));
				test_steps.add(String.format("Assertion Expected (%d) and actual (%d) number of rooms for the class (%s) do no match", roomNameList.size(), elements.size(), classAbbreviation));
				return;
			}

			for(int i = 0; i < roomNameList.size(); i++) {
				WebElement element = elements.get(i);
				String expectedRoomName = roomNameList.get(i).trim();
				String actualRoomName = element.getText().trim();

				if(expectedRoomName.equalsIgnoreCase(actualRoomName)) {
					tapechartLogger.info(String.format("Expected %s and actual %s room name matches", expectedRoomName, actualRoomName));
					test_steps.add(String.format("Expected %s and actual %s room name matches", expectedRoomName, actualRoomName));
				} else {
					tapechartLogger.info(String.format("Room name order do not match expected %s actual %s", expectedRoomName, actualRoomName));
					test_steps.add(String.format("Assertion Room name order do not match expected %s actual %s", expectedRoomName, actualRoomName));
				}
			}
		}
	}

	public void verifyClassSortOrder(WebDriver driver, ArrayList<String> test_steps, ArrayList<String> sortedList) {
		tapechartLogger.info("Verifying ungroup class sort order");
		test_steps.add("Verifying ungroup class sort order");

		String locator = "//div[contains(@class, 'roomnumber')]/span[1]";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);

		for(int i = 0; i < sortedList.size(); i++) {
			String roomNumberAbbreviation = sortedList.get(i);
			if(roomNumberAbbreviation.contains("|")) {
				String[] roomDetails = roomNumberAbbreviation.split("\\|");
				String roomNumberName = roomDetails[1];
				String roomAbbreviation = roomDetails[0];
				String roomNumberLocator = String.format("(//div[contains(@class, 'roomnumber')])[%d]/span[1]", (i + 1));
				String roomAbbreviationLocator = String.format("(//div[contains(@class, 'roomnumber')])[%d]/span[2]", (i + 1));

				String actualRoomNumber = "";
				String actualRoomAbbreviation = "";
				if(Utility.isElementPresent(driver, By.xpath(roomNumberLocator))) {
					actualRoomNumber = driver.findElement(By.xpath(roomNumberLocator)).getText();
				}

				if(Utility.isElementPresent(driver, By.xpath(roomAbbreviationLocator))) {
					actualRoomAbbreviation = driver.findElement(By.xpath(roomAbbreviationLocator)).getText();
				}

				if (roomNumberName.equalsIgnoreCase(actualRoomNumber) && roomAbbreviation.equalsIgnoreCase(actualRoomAbbreviation)) {
					tapechartLogger.info("Successfully verified room name " + roomNumberName + " and room abbreviation order" + roomAbbreviation);
					test_steps.add("Successfully verified room name " + roomNumberName + " and room abbreviation order" + roomAbbreviation);
				} else {
					tapechartLogger.info(String.format("Room name and room abbreviation order do not match expected %s - %s actual %s - %s", roomNumberName, roomAbbreviation, actualRoomNumber, actualRoomAbbreviation));
					test_steps.add(String.format("Assertion Room name and room abbreviation order do not match expected %s - %s actual %s - %s", roomNumberName, roomAbbreviation, actualRoomNumber, actualRoomAbbreviation));
				}
			} else {
				String roomNumberName = roomNumberAbbreviation;
				String roomNumberLocator = String.format("(//div[contains(@class, 'roomnumber')]/span[1])[%d]", (i + 1));

				String actualRoomNumber = "";
				if(Utility.isElementPresent(driver, By.xpath(roomNumberLocator))) {
					actualRoomNumber = driver.findElement(By.xpath(roomNumberLocator)).getText();
				}

				if (roomNumberName.equalsIgnoreCase(actualRoomNumber)) {
					tapechartLogger.info("Successfully verified room name " + roomNumberName);
					test_steps.add("Successfully verified room name " + roomNumberName);
				} else {
					tapechartLogger.info(String.format("Room name order do not match expected %s actual %s", roomNumberName, actualRoomNumber));
					test_steps.add(String.format("Assertion Room name order do not match expected %s actual %s", roomNumberName, actualRoomNumber));
				}
			}
		}
	}

	public void verifyDirtyRoomStatus(WebDriver driver, ArrayList<String> test_steps, HashMap<String, String> nameAbbreviationMap,
								 ArrayList<String> dirtyRoomList) {
		tapechartLogger.info("Verifying Room Status");
		test_steps.add("Verifying Room Status");
		waitForReservationToLoad(driver, test_steps);


		String dirtyRoomListLocator = "//div[contains(@class,'roomclassname')]/../following-sibling::div[contains(@class, 'roomsrow')]" +
				"//span/span/img[@src = 'images/dirty_tapechart_icon.png']";
		List<WebElement> actualDirtyRoomList = driver.findElements(By.xpath(dirtyRoomListLocator));

		if(dirtyRoomList.size() != actualDirtyRoomList.size()) {
			tapechartLogger.info(String.format("Expected %d and Actual %d dirty room count do not match", dirtyRoomList.size(), actualDirtyRoomList.size()));
			test_steps.add(String.format("Assertion Expected %d and Actual %d dirty room count do not match", dirtyRoomList.size(), actualDirtyRoomList.size()));
		}

		for(String str: dirtyRoomList) {
			String [] strArry = str.split("\\|");
			String classAbbreviation = nameAbbreviationMap.get(strArry[0]).trim();
			String roomNumberName = strArry[1].trim();
			String locator = String.format("//div[text() = '%s' and contains(@class,'roomclassname')]" +
					"/../following-sibling::div[contains(@class, 'roomsrow')]//span[text()='%s']" +
					"/span/img[@src = 'images/dirty_tapechart_icon.png']", classAbbreviation, roomNumberName);
			if(Utility.isElementPresent(driver, By.xpath(locator))) {
				tapechartLogger.info(String.format("Successfully verify room %s for class %s status is dirty", roomNumberName, classAbbreviation));
				test_steps.add(String.format("Successfully verify room %s for class %s status is dirty", roomNumberName, classAbbreviation));
			} else {
				tapechartLogger.info(String.format("Room %s for class %s status is not dirty", roomNumberName, classAbbreviation));
				test_steps.add(String.format("Assertion Room %s for class %s status is not dirty", roomNumberName, classAbbreviation));
				test_steps.add("Issue Room status and room status color are not correct <a href='https://innroad.atlassian.net/browse/NG-8724' target='_blank'>NG-8724</a>");
			}
		}
	}

	public ArrayList<Integer> getRoomAvailableSumList(WebDriver driver, ArrayList<String> test_steps, int dayCount) {
		showFooter(driver, test_steps, true);
		ArrayList<Integer> result = new ArrayList<>();
		String locator = "//div[contains(@class,'roomclassname')]/parent::div/parent::div/following-sibling::div/div[contains(text(),'Rooms Available')]";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);

		for(int i = 0; i < dayCount; i++) {
			result.add(0);
		}

		int elementCount = driver.findElements(By.xpath(locator)).size();
		for(int i = 1; i <= elementCount; i++) {
			List<WebElement> elements = driver.findElements(By.xpath("(" + locator + ")[" + i + "]/following-sibling::div/div/div/div"));
			for(int j = 0; j < elements.size(); j++) {
				int value = Integer.parseInt(elements.get(j).getText().trim());
				result.set(j, (result.get(j) + value));
			}
		}
		return result;
	}

	public ArrayList<Integer> getRoomAvailableTotalList(WebDriver driver, ArrayList<String> test_steps, int dayCount) {
		showFooter(driver, test_steps, true);
		ArrayList<Integer> result = new ArrayList<>();
		String locator = "//div[@data-bind='getElement: tapeChartConsolidatedAvailabilityAndOccupancy']//div[text()='# Rooms Available']/following-sibling::div/div/div/div";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);

		for(int i = 0; i < dayCount; i++) {
			result.add(0);
		}

		List<WebElement> elements = driver.findElements(By.xpath(locator));
		for(int i = 0; i < elements.size(); i++) {
			int value = Integer.parseInt(elements.get(i).getText().trim());
			result.set(i, value);
		}
		return result;
	}

	public void verifyRoomAvailableTotalCount(WebDriver driver, ArrayList<String> test_steps, int dayCount) {
		tapechartLogger.info("Verifying total room available count");
		test_steps.add("Verifying total room available count");
		ArrayList<Integer> roomAvailableSum = getRoomAvailableSumList(driver, test_steps, dayCount);
		ArrayList<Integer> roomAvailableTotal = getRoomAvailableTotalList(driver, test_steps, dayCount);

		for(int i = 0; i < roomAvailableSum.size(); i++) {
			if(roomAvailableSum.get(i).equals(roomAvailableTotal.get(i))) {
				tapechartLogger.info("Successfully verified total room available count expected : " + roomAvailableSum.get(i) + " actual : " + roomAvailableTotal.get(i));
				test_steps.add("Successfully verified total room available count expected : " + roomAvailableSum.get(i) + " actual : " + roomAvailableTotal.get(i));
			} else {
				tapechartLogger.info("Total room available count and sum of individual count do not match expected : " + roomAvailableSum.get(i) + " actual : " + roomAvailableTotal.get(i));
				test_steps.add("Assertion Total room available count and sum of individual count do not match expected : " + roomAvailableSum.get(i) + " actual : " + roomAvailableTotal.get(i));
			}
		}
	}

	public void clickOnRoomClass(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation) throws InterruptedException {
		String locator = String.format("//div[text()='%s' and contains(@class,'roomclassname')]", roomClassAbbreviation);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
	}

	public void verifyClickOnRoomClassFunctionality(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation) throws InterruptedException {
		tapechartLogger.info("Verifying click on room class functionality");
		test_steps.add("Verifying click on room class functionality");
		try {
			clickOnRoomClass(driver, test_steps, roomClassAbbreviation);
			Wait.WaitForElement(driver, OR.roomClassAbbreviationFieldV2);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			Elements_NewRoomClassV2 ele = new Elements_NewRoomClassV2(driver);
			String abbreviationTextValue = (String) jse.executeScript("return arguments[0].value", ele.roomClassAbbreviationFieldV2);

			if(roomClassAbbreviation.trim().equalsIgnoreCase(abbreviationTextValue.trim())) {
				tapechartLogger.info("Successfully verified click on room class functionality Room class page with abbreviation " + roomClassAbbreviation + " is displayed");
				test_steps.add("Successfully verified click on room class functionality Room class page with abbreviation " + roomClassAbbreviation + " is displayed");
			} else {
				tapechartLogger.info("Click on room class is not working Room class page with abbreviation " + roomClassAbbreviation + " is not visible");
				test_steps.add("Assertion Click on room class is not working Room class page with abbreviation " + roomClassAbbreviation + " is not visible");
			}
		} catch (Exception e) {
			tapechartLogger.info("Error while verifying click on room class functionality");
			test_steps.add("Assertion Error while verifying click on room class functionality");
		}
	}

	public void verifySearchIsTriggeredByClickingOnView(WebDriver driver, ArrayList<String> test_steps, String date,
														String adultCount, String ratePlan) throws InterruptedException {
		tapechartLogger.info("Verifying clicking on view triggers search");
		test_steps.add("Verifying clicking on view triggers search");
		enterAdult(driver, test_steps, adultCount);
		enterCheckInDate(driver, test_steps, date);
		selectRatePlan(driver, ratePlan, test_steps);
		clickOn15DaysButton(driver, test_steps);
		int availableRoomCount = getAvailableRoomSlotCount(driver, test_steps);
		if(availableRoomCount > 0) {
			tapechartLogger.info("Successfully verified clicking on view triggers search");
			test_steps.add("Successfully verified clicking on view triggers search");
		} else {
			tapechartLogger.info("Clicking on view does not triggers search");
			test_steps.add("Assertion Clicking on view does not triggers search");
		}
	}

	public void clickOnUnassignedTextForClassAbbreviation(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation) throws InterruptedException {
		String locator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::div/following-sibling::div/div/div[contains(text(),'Unassigned')]", roomClassAbbreviation);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on unassigned text");
		test_steps.add("Clicking on unassigned text");
	}

	public void verifyClickingOnUnassignedText(WebDriver driver, ArrayList<String> test_steps, String checkInDateStr,
											   String checkOutDateStr, String dateFormat, String roomClassName) throws InterruptedException {
		tapechartLogger.info("Verifying clicking on unassigned text takes to new reservation page");
		test_steps.add("Verifying clicking on unassigned text takes to new reservation page");
		LocalDate checkInDate = LocalDate.parse(checkInDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, DateTimeFormatter.ofPattern(dateFormat));
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		String dateRange = checkInDate.format(dateTimeFormatter) + " - " + checkOutDate.format(dateTimeFormatter);
		String roomClass = roomClassName + " : " + "Unassigned";
		String dateRangeLocator = "//div[@data-bind='text: tripStayDateRange']";
		String roomClassLocator = "//div[contains(@data-bind,'text: roomClass.name') and contains(@data-bind,'selectedRoomNumber()')]";

		Wait.wait10Second();
		if(Utility.isElementPresent(driver, By.xpath(dateRangeLocator)) && Utility.isElementPresent(driver, By.xpath(roomClassLocator))) {
			tapechartLogger.info("Successfully verified new reservation page is opened");
			test_steps.add("Successfully verified new reservation page is opened");

			String actualDateRangeText = driver.findElement(By.xpath(dateRangeLocator)).getText().trim();
			String actualRoomClassText = driver.findElement(By.xpath(roomClassLocator)).getText().trim();

			if(actualDateRangeText.equalsIgnoreCase(dateRange)) {
				tapechartLogger.info("Expected and actual date range matches expected : " + dateRange + " actual : " + actualDateRangeText);
				test_steps.add("Expected and actual date range matches expected : " + dateRange + " actual : " + actualDateRangeText);
			} else {
				tapechartLogger.info("Expected and actual date range do not match expected : " + dateRange + " actual : " + actualDateRangeText);
				test_steps.add("Assertion Expected and actual date range do not match expected : " + dateRange + " actual : " + actualDateRangeText);
			}

			if(actualRoomClassText.equalsIgnoreCase(roomClass)) {
				tapechartLogger.info("Expected and actual room class and room number matches expected : " + dateRange + " actual : " + actualDateRangeText);
				test_steps.add("Expected and actual room class and room number matches expected : " + dateRange + " actual : " + actualDateRangeText);
			} else {
				tapechartLogger.info("Expected and actual room class and room number do not match expected : " + dateRange + " actual : " + actualDateRangeText);
				test_steps.add("Expected and actual room class and room number do not match expected : " + dateRange + " actual : " + actualDateRangeText);
			}
		}
	}

	public void clickOnRateText(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation) throws InterruptedException {
		String locator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/following-sibling::div/div", roomClassAbbreviation);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on rate text");
		test_steps.add("Clicking on rate text");
	}

	public boolean verifyClickOnRateTextFunctionality(WebDriver driver, ArrayList<String>test_steps, String ratePlan) {
		tapechartLogger.info("Verifying clicking on rate text takes to rates grid");
		test_steps.add("Verifying clicking on rate text takes to rates grid");
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		if(Utility.isElementPresent(driver, By.xpath(RateGridPage.SelectedRatePlan))) {
			tapechartLogger.info("Successfully verified rates grid screen is visible");
			test_steps.add("Successfully verified rates grid screen is visible");
			Wait.waitUntilPresenceOfElementLocated(RateGridPage.SelectedRatePlan, driver);
			String selectedRatePlan = elements.selectedRatePlan.getText();
			if(selectedRatePlan.equalsIgnoreCase(ratePlan)) {
				tapechartLogger.info("Successfully verified selected rate plan expected : " + ratePlan + " actual : " +selectedRatePlan);
				test_steps.add("Successfully verified selected rate plan expected : " + ratePlan + " actual : " +selectedRatePlan);
			} else {
				tapechartLogger.info("Selected rate plan do not match expected : " + ratePlan + " actual : " +selectedRatePlan);
				test_steps.add("Assertion Selected rate plan do not match expected : " + ratePlan + " actual : " +selectedRatePlan);
			}
			return true;
		} else {
			tapechartLogger.info("Rates grid screen is not visible");
			test_steps.add("Assertion Rates grid screen is not visible");
			return false;
		}
	}

	public int getUnassignedReservationCountForIndex(WebDriver driver, ArrayList<String>test_steps, String roomClassAbbreviation, int index) throws InterruptedException {
		String locator = String.format("(//div[text() ='%s' and contains(@class,'roomclassname')]/parent::div/following-sibling::div/div" +
				"/div[contains(text(),'Unassigned')]/following-sibling::div/div/div/div)[%d]", roomClassAbbreviation, index);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		return Integer.parseInt(element.getText().trim());
	}

	public void clickOnUnassignedReservationCountForIndex(WebDriver driver, ArrayList<String>test_steps, String roomClassAbbreviation, int index) throws InterruptedException {
		String locator = String.format("(//div[text() ='%s' and contains(@class,'roomclassname')]/parent::div/following-sibling::div/div" +
				"/div[contains(text(),'Unassigned')]/following-sibling::div/div/div/div)[%d]", roomClassAbbreviation, index);
		Wait.WaitForElement(driver, locator);
		WebElement element = driver.findElement(By.xpath(locator));
		Utility.ScrollToElement(element, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		Wait.waitForElementToBeClickable(By.xpath(locator), driver);
		element.click();
		tapechartLogger.info("Clicking on unassigned reservation coutn");
		test_steps.add("Clicking on unassigned reservation coutn");
	}

	public void verifyClickOnUnassignedReservationCountFunctionality(WebDriver driver, ArrayList<String>test_steps, String dateStr,
																	 String dateFormat, int reservationCount) throws InterruptedException {
		tapechartLogger.info("Verifying clicking on unassigned reservation takes to search reservation with unassigned selected");
		test_steps.add("Verifying clicking on unassigned reservation takes to search reservation with unassigned selected");
		String activeUnassignedLocator = "//li[contains(@class,'active')]//span[text()='Unassigned']";

		Wait.wait10Second();
		if(Utility.isElementPresent(driver, By.xpath(activeUnassignedLocator))) {
			tapechartLogger.info("Successfully verified clicking on unassigned reservation count opens search reservation with unassigned selected");
			test_steps.add("Successfully verified clicking on unassigned reservation count opens search reservation with unassigned selected");
			LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormat));

			String day = date.format(DateTimeFormatter.ofPattern("d"));
			String month = date.format(DateTimeFormatter.ofPattern("MMM")).toUpperCase();
			String year = date.format(DateTimeFormatter.ofPattern("yyyy"));

			String actualDay = driver.findElement(By.xpath("(//div[contains(@class, 'ng-reservationDate')]//span[contains(@class, 'ng-date')])[2]")).getText().trim();
			String actualMonth = driver.findElement(By.xpath("(//div[contains(@class, 'ng-reservationDate')]//span[contains(@class, 'ng-month')])[2]")).getText().trim();
			String actualYear = driver.findElement(By.xpath("(//div[contains(@class, 'ng-reservationDate')]//span[contains(@class, 'ng-year')])[2]")).getText().trim();

			if(day.equalsIgnoreCase(actualDay)) {
				tapechartLogger.info("Expected and actual selected day matches " + day);
				test_steps.add("Expected and actual selected day matches " + day);
			} else {
				tapechartLogger.info("Expected and actual selected day do not match expected : " + day + " actual : " + actualDay);
				test_steps.add("Expected and actual selected day do not match expected : " + day + " actual : " + actualDay);
			}

			if(month.equalsIgnoreCase(actualMonth)) {
				tapechartLogger.info("Expected and actual selected month matches " + month);
				test_steps.add("Expected and actual selected month matches " + month);
			} else {
				tapechartLogger.info("Expected and actual selected month do not match expected : " + month + " actual : " + actualMonth);
				test_steps.add("Expected and actual selected month do not match expected : " + month + " actual : " + actualMonth);
			}

			if(year.equalsIgnoreCase(actualYear)) {
				tapechartLogger.info("Expected and actual selected year matches " + year);
				test_steps.add("Expected and actual selected year matches " + year);
			} else {
				tapechartLogger.info("Expected and actual selected year do not match expected : " + year + " actual : " + actualYear);
				test_steps.add("Expected and actual selected year do not match expected : " + year + " actual : " + actualYear);
			}

			List<WebElement> elements = driver.findElements(By.xpath("//tbody[contains(@data-bind, 'foreach: ReservationList')]/tr"));

			if(elements.size() == reservationCount) {
				tapechartLogger.info("Expected and actual unassigned reservation count matches " + reservationCount);
				test_steps.add("Expected and actual unassigned reservation count matches " + reservationCount);
			} else {
				tapechartLogger.info("Expected and actual unassigned reservation count do not match expected : " + reservationCount + " actual : " + elements.size());
				test_steps.add("Expected and actual unassigned reservation count do not match expected : " + reservationCount + " actual : " + elements.size());
			}
		} else {
			tapechartLogger.info("Clicking on unassigned reservation count did not open search reservation with unassigned selected");
			test_steps.add("Assertion Clicking on unassigned reservation count did not open search reservation with unassigned selected");
		}
	}


	public void updateCheckOutDate(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, String roomNumber, String reservationName,
								   int reservationDuration, int updateDayCount) throws InterruptedException {

		String reservationLocator = null;
		if((roomNumber == null || roomNumber.equals("")) && !(roomClassAbbreviation == null || roomClassAbbreviation.equals(""))) {
			reservationLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div/parent::div/div[2]//div[text()='%s']" +
					"/ancestor::span[contains(@class,'ir-tce-mainResCell')]", roomClassAbbreviation, reservationName);
		} else if((roomNumber == null || roomNumber.equals(""))) {
			reservationLocator = String.format("//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", reservationName);
		} else {
			reservationLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='%s']/parent::div/div[2]" +
					"//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", roomClassAbbreviation, roomNumber, reservationName);
		}

		Wait.WaitForElement(driver, reservationLocator);
		WebElement reservationElement = driver.findElement(By.xpath(reservationLocator));
		Utility.ScrollToElement(reservationElement, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		int cellWidth = (reservationElement.getSize().getWidth() / reservationDuration) * updateDayCount;

		Actions builder = new Actions(driver);
//		Action pickAction = builder
//				.moveToElement(reservationElement, reservationElement.getSize().width / 2, 0)
//				.pause(1000)
//				.clickAndHold()
//				.moveByOffset(cellWidth, 0)
//				.moveByOffset(updateDayCount, 0)
//				.pause(1000)
//				.release()
//				.build();
//		pickAction.perform();
	}

	public void updateCheckInDate(WebDriver driver, ArrayList<String> test_steps, String roomClassAbbreviation, String roomNumber, String reservationName,
								   int reservationDuration, int updateDayCount) throws InterruptedException {
		String reservationLocator = null;
		if((roomNumber == null || roomNumber.equals("")) && !roomClassAbbreviation.equals("")) {
			reservationLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div/parent::div/div[2]//div[text()='%s']" +
					"/ancestor::span[contains(@class,'ir-tce-mainResCell')]", roomClassAbbreviation, reservationName);
		} else if((roomNumber == null || roomNumber.equals(""))) {
			reservationLocator = String.format("//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", reservationName);
		} else {
			reservationLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='%s']/parent::div/div[2]" +
					"//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", roomClassAbbreviation, roomNumber, reservationName);
		}

		Wait.WaitForElement(driver, reservationLocator);
		WebElement reservationElement = driver.findElement(By.xpath(reservationLocator));
		Utility.ScrollToElement(reservationElement, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		int cellWidth = (reservationElement.getSize().getWidth() / reservationDuration) * updateDayCount;

		Actions builder = new Actions(driver);
//		Action pickAction = builder
//				.moveToElement(reservationElement, (reservationElement.getSize().width / 2) * -1, 0)
//				.pause(1000)
//				.clickAndHold()
//				.moveByOffset(cellWidth, 0)
//				.moveByOffset(updateDayCount, 0)
//				.pause(1000)
//				.release()
//				.build();
//		pickAction.perform();
	}

	public void verifyRateChangePopUpValueForIndex(WebDriver driver, ArrayList<String> test_steps, int rateOptionIndex, double newTotal, double oldTotal, double taxApplied) {
		selectRateOptionsToApplyRate(driver, rateOptionIndex);
		ArrayList<String> rateFound = getDragAndDropReservationPopUpDetails(driver);

		newTotal = newTotal + ((newTotal / 100) * taxApplied);
		oldTotal = oldTotal + ((oldTotal / 100) * taxApplied);

		if(rateFound.size() > 1) {
			if (Math.abs(Double.parseDouble(rateFound.get(1).trim()) - oldTotal) < 1) {
				tapechartLogger.info("Verified Old Rate Value:" + rateFound.get(1));
				test_steps.add("Verified Old Rate Value:" + rateFound.get(1));
			} else {
				tapechartLogger.info("Old Rate Change Value did not match expected " + oldTotal + " actual " + rateFound.get(1));
				test_steps.add("Assertion Old Rate Change Value did not match expected " + oldTotal + " actual " + rateFound.get(1));
			}
		}

		if (Math.abs(Double.parseDouble(rateFound.get(0).trim()) - newTotal) < 1) {
			tapechartLogger.info("Verified New Rate Value:" + rateFound.get(0));
			test_steps.add("Verified New Rate Value:" + rateFound.get(0));
		} else {
			tapechartLogger.info("New Rate Change Value did not match expected " + newTotal + " actual " + rateFound.get(0));
			test_steps.add("Assertion New Rate Change Value did not match expected " + newTotal + " actual " + rateFound.get(0));
		}
	}

	public void ClickRuleBrokenBookIfPopupPresent(WebDriver driver, ArrayList<String> steps) throws InterruptedException {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait10Second();
		if (!Utility.isElementDisplayed(driver, By.xpath(OR.Rules_Broken_popup))) {
			steps.add("Rule Broken Popup is not visible");
			tapechartLogger.info("Rule Broken Popup is not visible");
		} else {
			Wait.explicit_wait_visibilityof_webelement(TapeChart.RuleBrokenBook_Btn, driver);
			Wait.explicit_wait_elementToBeClickable(TapeChart.RuleBrokenBook_Btn, driver);
			Utility.ScrollToElement(TapeChart.RuleBrokenBook_Btn, driver);
			TapeChart.RuleBrokenBook_Btn.click();
			steps.add("Click Rule Broken Popup 'Book Button'");
			tapechartLogger.info("Click Rule Broken Popup 'Book Button'");
			Wait.waitForElementToBeGone(driver, TapeChart.RuleBrokenBook_Btn, 60);
			steps.add("New Reservation Page is opened");
			tapechartLogger.info("New Reservation Page is opened");
		}
	}
	

	public String getSelectedRatePlan(WebDriver driver) {
		String path = "//div[contains(@class,'rate-plan')]//button/span";
		Wait.WaitForElement(driver, path);
		return driver.findElement(By.xpath(path)).getText();
	}
	
	public boolean isRatePlanPresent(WebDriver driver, String ratePlan) throws InterruptedException, ParseException {

		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);

		tapeChart.ClickOnRatePlan.click();
		tapechartLogger.info("CLick on rate plan button");
		String pathRatePlan = "//div[contains(@class,'rate-plan')]//ul//li//span[text()='" + ratePlan + "']";
		boolean ispresent = false;
		try {
			driver.findElement(By.xpath(pathRatePlan)).click();
			ispresent = true;
		}catch (Exception e) {
			ispresent = false;
		}
		return ispresent;
	}

	public void clickOnEmptyCell(WebDriver driver, ArrayList<String> testSteps,
			 String roomClassAbbreviation) throws InterruptedException {
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[text()='"+ roomClassAbbreviation +"' and contains(@class,'roomclassname')]/parent::div/following-sibling::div//div[@class='tapechartdatecell noResAvail']";
		Wait.WaitForElement(driver, CellPath);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellPath));
		Wait.waitForElementToBeClickable(By.xpath(CellPath), driver);
		try {
		Utility.ScrollToElement(AvailableSlot, driver);
		try {
		AvailableSlot.click();
		} catch (Exception e) {
		jse.executeScript("window.scrollBy(0,-300)");
		AvailableSlot.click();
		tapechartLogger.info("Scrol back 300");
		
		}
		} catch (Exception e) {
		}
		testSteps.add("Click available room of Room Class '" + roomClassAbbreviation + "'");
		tapechartLogger.info("Click available room of Room Class '" + roomClassAbbreviation + "'");
	}


	public void verifyErrorMessage(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		/*
		Wait.WaitForElement(driver, NewTapeChart.errorMessage);
		Wait.waitForElementToBeVisibile(By.xpath(NewTapeChart.errorMessage), driver);
		Wait.waitForElementToBeClickable(By.xpath(NewTapeChart.errorMessage), driver);
		Utility.ScrollToElement(tapeChart.errorMessage, driver);
		*/
		assertTrue(tapeChart.errorMessage.isDisplayed(), "Failed : Error message 'Reservation cannot be created as the selected arrival date is locked. Please select other arrival date' is not displaying");
		testSteps.add("Verified Error message 'Reservation cannot be created as the selected arrival date is locked. Please select other arrival date' is displaying");
		tapechartLogger.info("Verified Error message 'Reservation cannot be created as the selected arrival date is locked. Please select other arrival date' is displaying");
	}	 


	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <updateRoomClass> ' Description: <Drop roomClass to an available
	 * slot of antoher class after searching tape chart> ' Input parameters:(String roomClassToDrag, String roomClassToDrop) '
	 * Return value: void ' Created By: <Muhammad Bakar> ' CreatedOn:
	 * <01/04/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void updateRoomClass(WebDriver driver, String roomClassToDrag, String roomClassToDrop, ArrayList<String> testSteps) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		
	 //String cellToDrag = "//div[contains(text(),'TC1515')  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='507']//following-sibling::div//div[@class='DatesContainer']";
	 //String CellToDrop = "(//div[text()='TC2515' and contains(@class,'roomclassname')]//ancestor::div[@class='roomRatesChart']//div[@title='3952']//following-sibling::div//div[text()='Available'])[1]";

	//String cellToDrag = "//div[text()='" + roomClassToDrag
	//			+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
	//			+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
	//			+ "div[contains(@class,'tapechartdatecell Reserved')]";
		//Wait.WaitForElement(driver, cellToDrag);
		//WebElement bookedSlot = driver.findElement(By.xpath(cellToDrag));
		//Utility.ScrollToElement(bookedSlot, driver);

	//	String CellToDrop = "//div[text()='" + roomClassToDrop
	//			+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
	//			+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
	//			+ "div[contains(@class,'tapechartdatecell Available')]";
	/*	
		Wait.WaitForElement(driver, CellToDrop);
		WebElement AvailableSlot = driver.findElement(By.xpath(CellToDrop));
		Utility.ScrollToElement(AvailableSlot, driver);
		try {
			tapechartLogger.info("In try");
			Wait.wait10Second();
			String xto=Integer.toString(AvailableSlot.getLocation().x);
			String yto=Integer.toString(AvailableSlot.getLocation().y);
			jse.executeScript("function createEvent(typeOfEvent) {\n"
			+ "var event =document.createEvent(\"CustomEvent\");\n"
			+ "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n"
			+ "data: {},\n" + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
			+ "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
			+ "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
			+ "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
			+ "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
			+ "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
			+ "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
			+ "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
			+ "var dropEvent = createEvent('drop');\n"
			+ "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
			+ "var dragEndEvent = createEvent('dragend');\n"
			+ "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
			+ "var source = arguments[0];\n" + "var destination = arguments[1];\n"
			+ "simulateHTML5DragAndDrop(source,destination);", AvailableSlot, bookedSlot);
		} catch (Exception e) {

	        	tapechartLogger.info("In catch");
	        			e.printStackTrace();
	        			//Using Action class for drag and drop.		
	       	         Actions act=new Actions(driver);					

	       	         //Dragged and dropped.		
	       	         act.dragAndDrop(bookedSlot, AvailableSlot).build().perform();	

		}
	*/	
		String selectRateOption = "(//button[@data-toggle='dropdown'])[last()]";
		Wait.WaitForElement(driver, selectRateOption);
		Wait.waitForElementToBeVisibile(By.xpath(selectRateOption), driver);
		Wait.waitForElementToBeClickable(By.xpath(selectRateOption), driver);
		driver.findElement(By.xpath(selectRateOption)).click();
		
		tapechartLogger.info("Selecting rate options");
		
		String noRateChangePath = "//span[text()='No rate change']//..//..";		
		Wait.WaitForElement(driver, noRateChangePath);
		driver.findElement(By.xpath(noRateChangePath)).click();
		testSteps.add("Select 'No Rate Change' option");
		tapechartLogger.info("Select 'No Rate Change' option");
		
		String confirmChanges = "//button[text()='Confirm Changes']";
		Wait.WaitForElement(driver, confirmChanges);
		Wait.waitForElementToBeVisibile(By.xpath(confirmChanges), driver);
		Wait.waitForElementToBeClickable(By.xpath(confirmChanges), driver);
		driver.findElement(By.xpath(confirmChanges)).click();
		testSteps.add("Click confirm changes");
		tapechartLogger.info("Click confirm changes");
			
	}


	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 *
	 * ' Method Name: <moveReservations> ' Description: <Move Reservation to
	 * index Date and Next Room in same> ' Input parameters: <first room number,
	 * room class, guest name, second room number, date index> ' Return value:
	 * <String> ' Created By: <Adnan Ghaffar> ' Created On: <05/09/2020>
	 *
	 * ' Modified By | Description of Modification:
	 * ------------------------------------------ <MM/DD/YYYY>:<Full name of
	 * developer>: 1.Step modified 2.Step modified
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String moveReservations(WebDriver driver, String roomNumber, String roomClass, String guestName,
								   int dateIndex, String secondRoomClass, String secondRoomNumber) throws InterruptedException {

		roomNumber = roomNumber.replaceAll("\\s+", "");
		tapechartLogger.info(roomNumber);
		Wait.wait2Second();
		String path = "//div[text()='" + roomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div//div[@title='"
				+ roomNumber + "']//following-sibling::div//div[@class='DatesContainer']";
		tapechartLogger.info("FromPath:" + path);
		tapechartLogger.info("DateIndex: " + dateIndex);
		String ToPath = "//div[text()='" + secondRoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::div/div[@title='"
				+ secondRoomNumber + "']//following-sibling::div/span/div[contains(@class,'tapechartdatecell')][" + dateIndex
				+ "]";
		tapechartLogger.info("ToPath:" + ToPath);
		
		WebElement fromElement = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(fromElement, driver);
		Utility.app_logs.info(" Location " + fromElement.getLocation());
		JavascriptExecutor javaScript = (JavascriptExecutor) driver;
		if (LastRoomClassInTapeChart(driver, roomClass)) {
			Utility.ScrollToElement(fromElement, driver);
			javaScript.executeScript("window.scrollBy(0,-200)");

		} else {
			Utility.ScrollToElement(fromElement, driver);
			javaScript.executeScript("window.scrollBy(0,-300)");
			Utility.app_logs.info("Scrolled Back");
		}

		Wait.explicit_wait_xpath(ToPath, driver);
		WebElement toElement = driver.findElement(By.xpath(ToPath));
		try {
			tapechartLogger.info("In try");


			Actions action = new Actions(driver);
			action.dragAndDrop(fromElement, toElement).build().perform();

			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(fromElement).clickAndHold().moveToElement(toElement).release().build();
			resizable.perform();
		} catch (Exception e) {

	        	tapechartLogger.info("In catch");
	        			e.printStackTrace();
	        			Wait.wait10Second();
	        			javaScript.executeScript("function createEvent(typeOfEvent) {\n"
	        			+ "var event =document.createEvent(\"CustomEvent\");\n"
	        			+ "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n"
	        			+ "data: {},\n" + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
	        			+ "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
	        			+ "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
	        			+ "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
	        			+ "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
	        			+ "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
	        			+ "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
	        			+ "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
	        			+ "var dropEvent = createEvent('drop');\n"
	        			+ "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
	        			+ "var dragEndEvent = createEvent('dragend');\n"
	        			+ "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
	        			+ "var source = arguments[0];\n" + "var destination = arguments[1];\n"
	        			+ "simulateHTML5DragAndDrop(source,destination);", fromElement, toElement);

		}

		return secondRoomNumber;
	}

	public void moveReservation(WebDriver driver, ArrayList<String> test_steps, String reservationFullName, String fromRoomClassAbbreviation, String fromRoomNumber,
			String toRoomClassAbbreviation, String toRoomNumber, int dateIndex) throws InterruptedException {
		
		String fromLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
		"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", fromRoomClassAbbreviation, fromRoomNumber, reservationFullName);
		tapechartLogger.info(fromLocator);
		String toLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
		"/parent::div/div[2]/span/div[%d]", toRoomClassAbbreviation, toRoomNumber, dateIndex);
		tapechartLogger.info(toLocator);
		
		Wait.WaitForElement(driver, fromLocator);
		Wait.waitForElementToBeVisibile(By.xpath(fromLocator), driver);
		
		WebElement fromElement = driver.findElement(By.xpath(fromLocator));
		WebElement toElement = driver.findElement(By.xpath(toLocator));
		Utility.ScrollToElement(fromElement, driver);
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Utility.ScrollToElement(fromElement, driver);
		jse.executeScript("window.scrollBy(0,-300)");
		
		Actions builder = new Actions(driver);
		Action pickAction = builder
		.moveToElement(fromElement)
		.clickAndHold()
		.build();
		pickAction.perform();
		
		Utility.ScrollToElement(toElement, driver);
		jse.executeScript("window.scrollBy(0,-300)");
		
		Action dropAction = builder
		.moveToElement(toElement, toElement.getSize().width, toElement.getSize().height)
		.moveToElement(toElement)
		.release()
		.build();
		dropAction.perform();
		
		String movedReservationLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
		"/parent::div/div[2]//div[text()='%s']/ancestor::span[contains(@class,'ir-tce-mainResCell')]", toRoomClassAbbreviation, toRoomNumber, reservationFullName);
		
		if(!Utility.isElementDisplayed(driver, By.xpath(movedReservationLocator))) {
		test_steps.add("Failed: Issue Moving reservation is not working <a href='https://innroad.atlassian.net/browse/NG-8052' target='_blank'>NG-8052</a>");
		}
		}


	public int minStayRuleDuration(HashMap<String, ArrayList<String>> map, LocalDate arrivalDate, LocalDate departureDate, String inputDateFormat) {
		int duration = 0;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
		int dayDiff =  (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
		for(int i = 0; i < dayDiff; i++) {
			int value = Integer.parseInt(map.get(arrivalDate.plusDays(i).format(dateTimeFormatter)).get(1));
			duration = Math.max(value, duration);
		}
		return duration;
	}

	public boolean minStayRuleApplied(HashMap<String, ArrayList<String>> map, LocalDate arrivalDate, LocalDate departureDate, String inputDateFormat) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
		int dayDiff =  (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
		int minStayRuleDuration = minStayRuleDuration(map, arrivalDate, departureDate, inputDateFormat);
		return dayDiff < minStayRuleDuration;
	}

	public boolean noCheckInApplied(HashMap<String, ArrayList<String>> map, LocalDate arrivalDate, LocalDate departureDate, String inputDateFormat) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
		int dayDiff =  (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
		boolean isNoCheckInRuleApplied = false;
		for(int i = 0; i < dayDiff; i++) {
			if(Integer.parseInt(map.get(arrivalDate.plusDays(i).format(dateTimeFormatter)).get(2)) > 0) {
				isNoCheckInRuleApplied = true;
				break;
			}
		}
		return isNoCheckInRuleApplied;
	}

	public boolean noCheckOutApplied(HashMap<String, ArrayList<String>> map, LocalDate arrivalDate, LocalDate departureDate, String inputDateFormat) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
		int dayDiff =  (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
		boolean isNoCheckOutRuleApplied = false;
		for(int i = 0; i < dayDiff; i++) {
			if(Integer.parseInt(map.get(arrivalDate.plusDays(i).format(dateTimeFormatter)).get(3)) > 0) {
				isNoCheckOutRuleApplied = true;
				break;
			}
		}
		return isNoCheckOutRuleApplied;

	}
	
	public void initiateReservationFromTapeChart(WebDriver driver, ArrayList<String> test_steps, String checkInDate, String checkOutDate, 
			String adults, String children, String ratePlan, String roomClass, String promocode) throws Exception {
		
		Navigation nav = new Navigation();
		
		nav.navigateToTapeChartFromReservations(driver);
		test_steps.add("Navigated to TapeChart screen");
//		waitForReservationToLoad(driver, test_steps);
		String checkIn = Utility.parseDate(checkInDate, "dd/mm/yyyy", "mm/dd/yyyy");
		String checkOut = checkOutDate;   //Utility.parseDate(checkOutDate, "dd/mm/yyyy", "mm/dd/yyyy");
		searchInTapechart(driver, test_steps, checkIn, checkOut, adults, children, ratePlan, promocode);
		clickAvailableSlotWithOutRulePopupHandling(driver, test_steps, roomClass);
		ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
	}
	
	public void enterDataForTapeChartReservation(WebDriver driver, 
			String salutation, String guestFirstName, String guestLastName, String phoneNumber, String alternativePhone,
			String email, String account, String accountType, String address1, String address2, String address3,
			String city, String country, String state, String postalCode, String isGuesProfile,
			String paymentMethod, String cardNumber, String nameOnCard, String cardExpDate,
			String marketSegment, String referral,  ArrayList<String> test_steps)
			throws Exception {

		CPReservationPage reservationPage = new CPReservationPage();
		reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName, phoneNumber,
				alternativePhone, email, account, accountType, address1, address2, address3, city, country, state,
				postalCode, isGuesProfile);
		if (Utility.validateString(paymentMethod)) {
			reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpDate);
		}
		reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);

	}
	
	
	public void verifyReservationOnTapeChart(WebDriver driver,String roomClass,String roomNo, String firstName, ArrayList<String>testSteps) {
		String path="//div[contains(@data-content,'"+roomClass+"')  and  contains(@class,'Reserved') and contains(@data-original-title,'"+firstName+"') and contains(@data-content, '"+roomNo+"')]";
		Wait.WaitForElement(driver, path);
		assertTrue(driver.findElement(By.xpath(path)).isEnabled(),"Failed to verify reservation on tapechart");
		testSteps.add("Verified Reservation On TapeChart"+ firstName);
		tapechartLogger.info("Verified Reservation On TapeChart" + firstName);
		
	}
	
	public void moveReservationfromOneRoomtoAnotherRoomforSameRoomClass(WebDriver driver, String roomClass,String roomOne, String roomTwo, ArrayList<String>testSteps) throws InterruptedException, AWTException {
		String roomOnePath="//div[contains(@data-content,'"+roomClass+"')  and contains(@class,'roomnumber')]"
				+ "/span[contains(text(),'"+roomOne+"')]/parent::div/..//following-sibling::div/span"
						+ "//following-sibling::span/div[contains(@class,'Reserved')]";
		/*String roomTwoPath="//div[contains(@data-content,'"+roomClass+"')  and contains(@class,'roomnumber')]"
				+ "/span[contains(text(),'"+roomTwo+"')]/parent::div/..//following-sibling::div/span"
						+ "//following-sibling::span/div[contains(@class,'tapechartdatecell Available')]";*/
	
		String roomTwoPath="//div[contains(@data-content,'"+roomClass+"')  and contains(@class,'roomnumber')]"
				+ "/span[contains(text(),'"+roomTwo+"')]/parent::div/..//following-sibling::div/span"
						+ "//div[(@class='tapechartdatecell noResAvail')]";
	    try {
		Wait.WaitForElement(driver, roomOnePath);
		Utility.ScrollToViewElementINMiddle(driver,  driver.findElement(By.xpath(roomOnePath)));
		Utility.releaseElementThroughAction(driver,  driver.findElement(By.xpath(roomOnePath)),driver.findElement(By.xpath(roomTwoPath)));
	    }
	    catch(Exception e) {
	    	JavascriptExecutor javaScript = (JavascriptExecutor) driver;
	    	javaScript.executeScript("function createEvent(typeOfEvent) {\n"
        			+ "var event =document.createEvent(\"CustomEvent\");\n"
        			+ "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n"
        			+ "data: {},\n" + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
        			+ "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
        			+ "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
        			+ "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
        			+ "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
        			+ "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
        			+ "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
        			+ "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
        			+ "var dropEvent = createEvent('drop');\n"
        			+ "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
        			+ "var dragEndEvent = createEvent('dragend');\n"
        			+ "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
        			+ "var source = arguments[0];\n" + "var destination = arguments[1];\n"
        			+ "simulateHTML5DragAndDrop(source,destination);", driver.findElement(By.xpath(roomOnePath)), driver.findElement(By.xpath(roomTwoPath)));
	    }
	    try {
			Elements_CPReservation res = new Elements_CPReservation(driver);
			Wait.WaitForElementUsingClassName(driver, OR.Toaster_Message);
			Wait.waitForElementToBeVisibile(By.className(OR.Toaster_Message), driver);
			Wait.waitForElementToBeClickable(By.className(OR.Toaster_Message), driver);
			Utility.app_logs.info(res.Toaster_Message.getText());
			testSteps.add("Toaster message: " + res.Toaster_Message.getText());

		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

//	public String GetandVerifyUnassignedReservationNumber(WebDriver driver, boolean verify)
//			throws InterruptedException {
//		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
//		String number = null;
//		Wait.WaitForElement(driver, OR.Unassigned_Button);
////		Wait.waitForElementToBeInVisibile(By.xpath(OR.Unassigned_Button), driver);
//		Wait.waitForElementToBeClickable(By.xpath(OR.Unassigned_Button), driver);
//		Utility.ScrollToElement(TapeChart.Unassigned_Button, driver);
//		number = TapeChart.Unassigned_Button.getText();
//		number = number.split(" ")[1];
//		number = number.replaceAll("[()]", "");
//		if (verify) {
//			if (!number.equals("0")) {
//				int size = driver.findElements(By.xpath(OR.UnassignedList)).size();
//				assertEquals(Integer.toString(size), number,
//						"Failed: Number of Unassigned Reservations missmatched on Unassigned Button");
//			}
//			assertEquals(TapeChart.UnassignedColumnHeader.getText(), "Unassigned (" + number + ")",
//					"Failed: Number of Unassigned Reservations missmatched on Unassigned Column Header");
//		}
//		return number;
//	}


	public ArrayList<String>  verifySlotAfterEarlyCheckout(WebDriver driver, String roomClassAbbreviation, String roomNo) {
		ArrayList<String> testSteps=new ArrayList<>();
		String locator = "//div[text()='"+roomClassAbbreviation+"'  and contains(@class,'roomclassname')]//..//..//div[@class='col-xs-1 roomnumber']//span[text()='"+roomNo+"']//parent::*//following-sibling::div//div[3]";
		Wait.WaitForElement(driver, locator);
		Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
		WebElement elements = driver.findElement(By.xpath(locator));
		
			if(elements.getAttribute("class").contains("noResAvail")) {
				testSteps.add("Successfuly Verify Slot After Early Checkout " +elements.getAttribute("class"));
				tapechartLogger.info("Successfuly Verify Slot After Early Checkout " +elements.getAttribute("class"));
			
		}
		return testSteps;
	}
	
	
	public boolean checkExtendDayAvailability(WebDriver driver, String RoomNumber, String RoomClass, String exDays,
			String exNextday, String adults, String timeZone, String ratePlan, ArrayList<String> test_steps)
			throws Exception {
		boolean flagAvail = false;
		String xpath = "(//div[contains(text(),'" + RoomClass
				+ "')  and contains(@class,'roomclassname')]//parent::*//following::div[@title='" + RoomNumber
				+ "']/following::div[contains(@class,'tapechartdatecell Available')])[1]/ancestor::div/div[@class='col-xs-1 roomnumber']";
		searchRoomClassInTapeChart(driver, exDays, exNextday, adults, timeZone, test_steps, ratePlan);
		test_steps.add("Searched room class availability for the extend day between: " + exDays + " " + exNextday);
		String getRoom = driver.findElement(By.xpath(xpath)).getAttribute("title");
		if (RoomNumber.equals(getRoom)) {
			flagAvail = true;
			test_steps.add("Room Class Available over extended days required");
		} else {
			test_steps.add("Room Class Not Available over extended days required, So unable to extend");
		}
		return flagAvail;
	}
	public boolean extendReservation(WebDriver driver, String roomNumber, String roomClass, String guestName,
			int stayDifference, String oldCheckoutDay, String exCheckoutDay, ArrayList<String> test_steps) throws Exception {
		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		boolean availaCheck = false;
		Wait.wait2Second();
		roomNumber = roomNumber.replaceAll("\\s+", "");
		Wait.wait2Second();
		String daysToExtends = Utility.differenceBetweenDates(oldCheckoutDay, exCheckoutDay);
		int daysToExtend = Integer.parseInt(daysToExtends);
			String path = "(//div[contains(text(),'" + roomClass
					+ "')  and contains(@class,'roomclassname')]/following::div[@title='" + roomNumber
					+ "']/following::div[contains(text(),'"+guestName+"')]/following::div[@class='DatesContainer'])[1]";

			Wait.WaitForElement(driver, path);
			Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path)));
			WebElement ele = driver.findElement(By.xpath(path));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			Wait.wait2Second();
			jse.executeScript("window.scrollBy(0,-300)");
			Wait.wait2Second();
			int divWidth = ele.getSize().getWidth();
			int divHeight = ele.getSize().getHeight();			
			// Get half of Height
			int halfHeight = divHeight / 2;
			int perDayWidth = divWidth / stayDifference;
			int requiredWidth = perDayWidth * (daysToExtend);
			tapechartLogger.info("Pre Width : " + divWidth);
			
			Actions builder = new Actions(driver);
			Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold()
					.moveByOffset(requiredWidth, 0).release().build();
			resizable.perform();
			Wait.explicit_wait_visibilityof_webelement_350(TapeChart.CheckIn, driver);
			assertEquals(TapeChart.CheckIn.getText(), "Check In", "Check in doesnot display");
			assertEquals(TapeChart.CheckOut.getText(), "Check Out", "Check out does not display");
			assertEquals(TapeChart.TripTotal.getText(), "Trip Total", "Trip total does not display");
			assertEquals(TapeChart.BalanceDue.getText(), "Balance Due", "Balance due does not display");
			Utility.app_logs.info("Verified all extend window");
			TapeChart.ConfirmChangeReservation_Button.click();
			Utility.app_logs.info("Click on Confirm Change button");
			// verify toaster message here
			try {
				assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
						"Reservation does not expand");
				test_steps.add("Reservation has been updated successfully till" + exCheckoutDay);
			} catch (Exception e) {
				System.err.println("Toaster not Displayed");
			}
		/*} else {

		}*/
		return availaCheck;
	}
	
	public void reduceReservation(WebDriver driver, String roomClass,String roomName, String guestName,
			int stayDifference, String oldCheckoutDay, String rdCheckoutDay,ArrayList<String> test_steps) throws InterruptedException, ParseException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		int divWidth = 0;
		Wait.wait2Second();
		String daysToReduces = Utility.differenceBetweenDates(oldCheckoutDay, rdCheckoutDay);
		int daysToReduce = Integer.parseInt(daysToReduces);
		String path = "(//div[contains(text(),'" + roomClass
				+ "')  and contains(@class,'roomclassname')]/following::div[@title='" + roomName
				+ "']/following::div[contains(text(),'"+guestName+"')]/following::div[@class='DatesContainer'])[1][last()]";
		tapechartLogger.info(path);
		Wait.WaitForElement(driver, path);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path)));
		WebElement ele = driver.findElement(By.xpath(path));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait2Second();
		divWidth = ele.getSize().getWidth();
		tapechartLogger.info("Pre Width Reduce : " + divWidth);
		// preWidth = divWidth;
		int divHeight = ele.getSize().getHeight();
		int perDayWidth = divWidth / stayDifference;
		int requiredWidth = perDayWidth * (daysToReduce);
		// Get half of Height
		int halfHeight = divHeight / 2;
		Actions builder = new Actions(driver);
		Action resizable = builder.moveToElement(ele, divWidth, halfHeight).clickAndHold()
				.moveByOffset(-requiredWidth, 0).release().build();
		resizable.perform();
		Wait.wait2Second();
		if (TapeChart.Rules_Broken_popup.isDisplayed()) {
			Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
			TapeChart.Click_Book_icon_Tapechart.click();
			tapechartLogger.info("Rules are broken");
		} else {
			tapechartLogger.info("Rules are not broken");
		}
		Wait.wait2Second();
		VerifyReduceDates(driver);

		TapeChart.ConfirmChangesButton.click();
		test_steps.add("Click Confirm Changes");
		tapechartLogger.info("Click Confirm Changes");
		try {
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 10000);
			tapechartLogger.info("confirmed clicked");
		} catch (Exception e) {
			TapeChart.ConfirmChangesButton.click();
			Wait.waitForElementToBeGone(driver, TapeChart.ConfirmChangesButton, 10000);
		}

		try {
			assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
					"Reservation does not Reduce");
			test_steps.add("Reservation has been updated successfully till" +rdCheckoutDay);
		} catch (Exception e) {
			tapechartLogger.info("Toast Not Displayed");
		}
	}

	public ArrayList<String> verify_SyndicateRoom(WebDriver driver, String RoomClassAbb, String RoomNumber,
			ArrayList<String> TestSteps) throws InterruptedException {

		Elements_TapeChart elements_TapeChart = new Elements_TapeChart(driver);
		Wait.wait2Second();
		String PathRoomCell = /*"//div[text()='" + RoomClassAbb
				+ "'  and contains(@class,'roomclassname')]//ancestor::div[@class='roomClasses']//child::div[@title='"
				+ RoomNumber
				+ "']//parent::div//child::div[@class='guest_display_name_short'and text()='S']//ancestor::div[contains(@class,'tapechartdatecell')]//child::div[@class='DatesContainer']"
				+ "|" + "//div//span[@class='roomNumber'][text()='" + RoomNumber
				+ "']/parent::div//span[@class='className'][text()='" + RoomClassAbb
				+ "']/parent::div/following-sibling::div//div[@title='Syndicated']" + "|"+*/
				 "//*[contains(@class,'roomnumber')]/span[text()='" + RoomNumber + "']/../../..//div[text()='"
				+ RoomClassAbb + "']/parent::div/following-sibling::div//div[@title='Syndicated']";
		Wait.wait2Second();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String Path = PathRoomCell + "//div[text()='S']";
		WebElement room = driver.findElement(By.xpath(Path));
		Wait.explicit_wait_visibilityof_webelement(room, driver);
		Utility.ScrollToElement(room, driver);
		jse.executeScript("window.scrollBy(0,-300)");
		Wait.wait2Second();
		assertEquals(room.getText(), "S", "Syndicate room is not showing in tape chart");
		jse.executeScript("arguments[0].click();", room);

		WebElement elementRoomCell = driver.findElement(By.xpath(PathRoomCell));
		try {
			System.out.println("in try");
			elementRoomCell.click();
			Wait.wait1Second();
		} catch (Exception e) {
			System.out.println("in catch");
			jse.executeScript("arguments[0].click();", elementRoomCell);
		}
		if (elements_TapeChart.ListRuleBrokenPopUp.size() > 0 && elements_TapeChart.RuleBrokenBook_Btn.isDisplayed()) {
			elements_TapeChart.RuleBrokenBook_Btn.click();

		}
		TestSteps.add("Click syndicate room from tapechart");
//		Wait.explicit_wait_visibilityof_webelement_120(driver.findElement(By.xpath(OR.ReservationDetailPage)), driver);

		return TestSteps;
	}
	
	public void verifyRoomClassAndAmount(WebDriver driver, ArrayList<String> test_steps, String roomClassName, String amount)
			throws InterruptedException {
		// TODO Auto-generated method stub
		String TapeChart_RoomClassPath = "//div[@class='roomRatesChart']//div[contains(@class,'roomclassname')][contains(text(),'"
				+ roomClassName + "')]";
		test_steps.add("Verify RoomClass Abbreviation '"+ roomClassName +"' is showing in tapechart");
		Assert.assertTrue(Utility.isElementDisplayed_1(driver, By.xpath(TapeChart_RoomClassPath)), "Failed : roomclass "+ roomClassName+" is not showing in tapechart");
		test_steps.add("Verified RoomClass Abbreviation '"+ roomClassName +"' is showing in tapechart");
		
		test_steps.add("Verify Rate '"+ amount +"' for RoomClass with Abbreviation '"+ roomClassName +"' is showing in tapechart");
		String TapeChart_RoomClass_RatePath = "//div[@class='roomRatesChart']//div[contains(@class,'roomclassname')][contains(text(),'"
				+ roomClassName + "')]/..//div[2]//div[@class='tapechartdatecell']";
		Wait.waitForElementByXpath(driver, TapeChart_RoomClass_RatePath);
		WebElement ratePathEle = driver.findElement(By.xpath(TapeChart_RoomClass_RatePath));
		Utility.ScrollToElement(ratePathEle, driver);
		Utility.verifyEquals(ratePathEle.getText().trim(), "$"+amount, test_steps);
		
	}

	public void verifyOutOfOrder(WebDriver driver, ArrayList<String> test_steps, String roomClassName, String title, boolean  isDisplay, int index)
			throws InterruptedException {
		Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
		String color = tapeChart.TapeChart_outIcon.getCssValue("background-color");
		tapechartLogger.info(color);
		String TapeChart_RoomClassPath = "//div[@class='roomRatesChart']//div[contains(@class,'roomclassname')][contains(text(),'"
				+ roomClassName + "')]";
		WebElement TapeChart_RoomClassName = driver.findElement(By.xpath(TapeChart_RoomClassPath));
		Utility.ScrollToElement(TapeChart_RoomClassName, driver);
		Wait.explicit_wait_visibilityof_webelement(TapeChart_RoomClassName, driver);
		if(isDisplay) {
			test_steps.add("Verifying that room maintenance item is displaying in particular room in tape chart");
			
			String outElement = "//div[@class='roomRatesChart']/div[contains(@class,'row ratesrow')]/div[contains(@class,'roomclassname')][contains(text(),'"
					+ roomClassName
					+ "')]/ancestor::div[contains(@class,'ratesrow')]//following-sibling::div//div[contains(@class,'roomnumber')][@title='"
					+ title + "']//following-sibling::div//div[contains(@class,'Out')][@title='Out']";
			
			assertTrue(Utility.isElementDisplayed(driver, By.xpath(outElement)), "Failed : RoomClass out of order is not showing in tpaechart");
			test_steps.add("Verified that room maintenance item is displaying in particular room in tape chart");
			tapechartLogger.info("Verified that room maintenance item is displaying in particular room in tape chart");
			
		}
		else {
			test_steps.add("Verifying that room maintenance item is not displaying in particular room in tape chart");
			
			String outElement = "//div[@class='roomRatesChart']/div[contains(@class,'row ratesrow')]/div[contains(@class,'roomclassname')][contains(text(),'"
					+ roomClassName
					+ "')]/ancestor::div[contains(@class,'ratesrow')]//following-sibling::div//div[contains(@class,'roomnumber')][@title='"
					+ title + "']//following-sibling::div//div[contains(@class,'Out')][@title='Out']";
			
			assertFalse(Utility.isElementDisplayed(driver, By.xpath(outElement)), "Failed : RoomClass out of order is showing in tpaechart");
			test_steps.add("Verified that room maintenance item is not displaying in particular room in tape chart");
			tapechartLogger.info("Verified that room maintenance item is not displaying in particular room in tape chart");
			
		}
	}
		public ArrayList<String> verifyCheckinInputTapeChart(WebDriver driver) throws InterruptedException{
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			ArrayList<String> testSteps = new ArrayList<>();
			String colour = "rgba(85, 85, 85, 1)";
			testSteps.add("Verify That CheckIn Date placeholder text is in Grey Colour");
			Wait.waitForElementByXpath(driver, OR.Select_Arrival_Date);
			String getColour = TapeChart.checkinTapeChart.getCssValue("color").trim(); 
			Utility.verifyEquals(getColour.trim(), colour, testSteps);
			
			
			Wait.waitForElementByXpath(driver, NewTapeChart.ClickCalanderDepartDate);
			testSteps.add("Verify that checkOut date placeholder text is in Grey Colour");
			getColour = TapeChart.checkOutTapeChart.getCssValue("color").trim(); 
			Utility.verifyEquals(getColour.trim(), colour, testSteps);
			
			Wait.waitForElementByXpath(driver, OR.Enter_Children_Tapechart);
			testSteps.add("Verify that children placeholder text is in Grey Colour");
			getColour = TapeChart.Enter_Children_Tapechart.getCssValue("color").trim(); 
			Utility.verifyEquals(getColour.trim(), colour, testSteps);
			

			Wait.waitForElementByXpath(driver, OR.Enter_Adults_Tapehchart);
			testSteps.add("Verify that adutls placeholder text is in Grey Colour");
			getColour = TapeChart.Enter_Adults_Tapehchart.getCssValue("color").trim(); 
			Utility.verifyEquals(getColour.trim(), colour, testSteps);
			
			Wait.waitForElementByXpath(driver, OR.Enter_promoCode_Tapechart);
			testSteps.add("Verify that promo code placeholder text is in Grey Colour");
			getColour = TapeChart.Enter_promoCode_Tapechart.getCssValue("color").trim(); 
			Utility.verifyEquals(getColour.trim(), colour, testSteps);
			
			return testSteps;
	}

		public ArrayList<String> verifyRatePlanColour(WebDriver driver){
			
			ArrayList<String> testSteps = new ArrayList<>();
			String colour = "rgba(85, 85, 85, 1)";
			testSteps.add("Verify that rateplan placeholder text is in Grey Colour");
			Wait.waitForElementByXpath(driver, OR.Select_Arrival_Date);
			String rateplanPath = "//div[contains(@class,'rate-plan')]//button[@data-toggle='dropdown']//span";
			Wait.waitForElementByXpath(driver, rateplanPath);
			String getColour = driver.findElement(By.xpath(rateplanPath)).getCssValue("color").trim(); 
			Utility.verifyEquals(getColour.trim(), colour, testSteps);
			
			return testSteps;
	
		}
	
		public ArrayList<String> verifyNoResultsError(WebDriver driver){
			
			ArrayList<String> testSteps = new ArrayList<>();
			testSteps.add("Verify that toaster with text 'No results match your criteria. Please change your search and try again.' is showing");
			assertTrue(Utility.isElementDisplayed(driver, By.xpath(NewTapeChart.noResultsFoundToaster)), "Failed :  Toaster with text 'No results match your criteria. Please change your search and try again.' is not showing");
			testSteps.add("Verified that toaster with text 'No results match your criteria. Please change your search and try again.' is showing");
		return testSteps;
		}
		public ArrayList<String>  verifyPrimaryReservation(WebDriver driver, String roomClassAbbreviation, String roomNo) {
			ArrayList<String> testSteps=new ArrayList<>();
			String locator = "//div[text()='"+roomClassAbbreviation+"'  and contains(@class,'roomclassname')]//..//..//div[@class='col-xs-1 roomnumber']//span[text()='"+roomNo+"']//parent::*//following-sibling::div//div[3]";
			Wait.WaitForElement(driver, locator);
			Wait.waitForElementToBeVisibile(By.xpath(locator), driver);
			WebElement elements = driver.findElement(By.xpath(locator));
			
				if(elements.getAttribute("class").contains("noResAvail")) {
					testSteps.add("Successfuly Verify Slot After Early Checkout " +elements.getAttribute("class"));
					tapechartLogger.info("Successfuly Verify Slot After Early Checkout " +elements.getAttribute("class"));
				
			}
			return testSteps;
		}
		
		public ArrayList<String> verifyReservtion(WebDriver driver, String roomClassAbbreviation, String roomClassName, String roomNo, String roomsAmount, String reservationNumber, String guestName, String ratePlan, String source,
				String maxAdults, String maxPersons, String checkInDate, String checkOutDate,
				String tripTotal, String balance) throws InterruptedException, ParseException{
			ArrayList<String> testSteps=new ArrayList<>();
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			String[] roomClassAbbArr = roomClassAbbreviation.split("\\|");
			String[] roomClassNameArr = roomClassName.split("\\|");
			String[] roomNoArr = roomNo.split("\\|");
			String[] roomsAmountArr = roomsAmount.split("\\|");
			String[] guestNameArr = guestName.split("\\|");
			String[] maxAdultsArr = maxAdults.split("\\|");
			String[] maxPersonsArr = maxPersons.split("\\|");
			String[] checkInDateArr = checkInDate.split("\\|");
			String[] checkOutDateArr = checkOutDate.split("\\|");
			String[] ratePlanArr = ratePlan.split("\\|");
			tapechartLogger.info("guestName : " + guestName);
			tapechartLogger.info("guestNameArr : " + guestNameArr.length);
			for(int i=0; i< guestNameArr.length; i++) {
				testSteps.add("<b> ===== Verify guest '"+ guestNameArr[i] +"' in tapechart. =====</b>");			

				String tempGuestName = guestNameArr[i].trim();
				if(tempGuestName.length() > 15) {
					tempGuestName = tempGuestName.substring(0, 10).trim();
				}
				
				String fromLocator = String.format("//div[text() ='%s' and contains(@class,'roomclassname')]/parent::*//following-sibling::div//div[@title='%s']" +
				"/parent::div/div[2]//div[contains(text(),'%s')]/ancestor::span[contains(@class,'ir-tce-mainResCell')]", roomClassAbbArr[i], roomNoArr[i], tempGuestName);
				tapechartLogger.info(fromLocator);
								
				Wait.WaitForElement(driver, fromLocator);
				Wait.waitForElementToBeVisibile(By.xpath(fromLocator), driver);
				
				WebElement fromElement = driver.findElement(By.xpath(fromLocator));
				Utility.ScrollToElement(fromElement, driver);
				
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				Utility.ScrollToElement(fromElement, driver);
				jse.executeScript("window.scrollBy(0,-300)");
				Wait.wait10Second();
				Utility.mouseHover(driver, fromElement);
				
				testSteps.add("Verify that numbers of rows are equal to room numbers");			
				String rowsPath = "//div[starts-with(@id, 'popover')]//div[@class='popover-content']//div[@class='container']";
				List<WebElement> rowsEle = driver.findElements(By.xpath(rowsPath));
				//Utility.verifyEquals(String.valueOf(rowsEle.size()), String.valueOf(guestNameArr.length), testSteps);
				Wait.wait10Second();
				testSteps.add("Verify Guest Name '"+ guestNameArr[i] +"' at index "+ i +" in header section");			
				String geustNamePath = "//div[starts-with(@id, 'popover')]//h3//div[contains(@class, 'ir-guestname')]";
				try {
					String getTripText = Utility.getElementText(driver.findElement(By.xpath(geustNamePath)));
					Utility.verifyEquals(getTripText, guestNameArr[i].trim(), testSteps);
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
				}
				testSteps.add("Verified Guest Name '"+ guestNameArr[i] +"' at index "+ i +" in header section");			
				
				testSteps.add("Verify reservationNumber '"+ reservationNumber +"' in header section");			
				String reservationNumberPath = "//span/b[contains(text(), '#"+ reservationNumber +"')]";
				try {
				assertTrue(Utility.isElementDisplayed_1(driver, By.xpath(reservationNumberPath)), "Failed : reservationNumber '"+ reservationNumber +"' in header section");
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
					
				}		
				testSteps.add("Verified reservationNumber '"+ reservationNumber +"' in header section");			

				testSteps.add("Verify rate plan '"+ ratePlanArr[i] +"' in header section is not showing");			
				String ratePlanPath = "//div[starts-with(@id, 'popover')]//h3//div//span[text()='"+ ratePlanArr[i] +"']";
				try {
				assertFalse(Utility.isElementDisplayed_1(driver, By.xpath(ratePlanPath)), "Failed : Rate Plan '"+ ratePlanArr[i] +"' in header section is showing");
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
				}
				testSteps.add("Verified rate plan '"+ ratePlanArr[i] +"' in header section");			

				
				testSteps.add("Verify source '"+ source +"' in header section is not showing");			
				String sourcePath = "//div[starts-with(@id, 'popover')]//h3//div//span[contains(text(),'"+ source +"')]";

				try {
					assertTrue(Utility.isElementDisplayed_1(driver, By.xpath(sourcePath)), "Failed : source '"+ source +"' in header section is not showing");
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
				}
				testSteps.add("Verified source '"+ source +"' in header section");			
								
				testSteps.add("Verify TripTotal '"+ tripTotal +"' in header section");			
				String tripTotalPath = "//div[starts-with(@id, 'popover')]//h3//div//span[contains(text(),'Trip Total')]//following-sibling::span";
				try {
					String getTripText = Utility.getElementText(driver.findElement(By.xpath(tripTotalPath)));
					getTripText = Utility.removeDollarBracketsAndSpaces(getTripText);
					tripTotal = Utility.removeDollarBracketsAndSpaces(tripTotal);
					Utility.verifyEquals(getTripText, tripTotal, testSteps);
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
				}
				testSteps.add("Verified TripTotal '"+ tripTotal +"' in header section");			

				testSteps.add("Verify balance '"+ balance +"' in header section");			
				String balancePath = "//div[starts-with(@id, 'popover')]//h3//div//span[contains(text(),'Balance Due')]//following-sibling::span";
				try {
					String getBalanceText = Utility.getElementText(driver.findElement(By.xpath(balancePath)));
					getBalanceText = Utility.removeDollarBracketsAndSpaces(getBalanceText);
					balance = Utility.removeDollarBracketsAndSpaces(balance);
					Utility.verifyEquals(getBalanceText, balance, testSteps);
				}catch (Exception|Error e) {
					testSteps.add(e.toString());
				}
				testSteps.add("Verified balance '"+ balance +"' in header section");			
				
				for(int b=0; b < guestNameArr.length; b++) {
					testSteps.add("Verify popup details at index " + i);			

					testSteps.add("Verify arrival date '"+ checkInDateArr[b] +"' in content section is not showing");			
					tempGuestName = guestNameArr[b].substring(0, 12);
					
					String arriveDatePath = "//div[@class='popover-content']//div[@class='container']//div[contains(text(),'"+ tempGuestName +"')]/..//preceding-sibling::div//div[contains(@class, 'hover-date-month ng-arriveDate')]";
					tapechartLogger.info(arriveDatePath);
					try {
						WebElement arrivalEle = driver.findElement(By.xpath(arriveDatePath));
						String getTripText = Utility.getElementText(arrivalEle);
						getTripText = ESTTimeZone.reformatDate(getTripText, "MMM dd, yyyy", "dd/MM/yyy");
						Utility.verifyEquals(getTripText, checkInDateArr[b], testSteps);
					}catch (Exception|Error e) {
						testSteps.add(e.toString());
					}
					testSteps.add("Verified arrival date '"+ checkInDateArr[b] +"' in content section");			


					testSteps.add("Verify depart date '"+ checkOutDateArr[b] +"' in content section is not showing");			
					String departDatePath = "//div[@class='popover-content']//div[@class='container']//div[contains(text(),'"+ tempGuestName +"')]/..//preceding-sibling::div//div[contains(@class, 'hover-date-month ng-departDate')]";
					tapechartLogger.info(departDatePath);
					try {
						WebElement departEle = driver.findElement(By.xpath(departDatePath));
						String getTripText = Utility.getElementText(departEle);
						getTripText = ESTTimeZone.reformatDate(getTripText, "MMM dd, yyyy", "dd/MM/yyy");
						Utility.verifyEquals(getTripText, checkOutDateArr[b], testSteps);
					}catch (Exception|Error e) {
						testSteps.add(e.toString());
					}
					testSteps.add("Verified depart date '"+ checkOutDateArr[b] +"' in content section");			

					testSteps.add("Verify guestName '"+ guestNameArr[b] +"' in content section");			
					String contentGuestNamePath = "";
					if(guestNameArr[b].trim().length() > 11) {
						contentGuestNamePath = "//div[@class='popover-content']//div[@class='container']//div[contains(text(),'" + guestNameArr[b].substring(0, 11).trim() + "')]";
					}else {
					contentGuestNamePath = "//div[@class='popover-content']//div[@class='container']//div[contains(text(),'" + guestNameArr[b] +"')]";
					}
					try {
					assertTrue(Utility.isElementDisplayed_1(driver, By.xpath(contentGuestNamePath)), "Failed : guestName '"+ guestNameArr[b] +"' in content section");
					}catch (Exception|Error e) {
						testSteps.add(e.toString());
						
					}		
					testSteps.add("Verified guestName '"+ guestNameArr[b] +"' in content section");			


					testSteps.add("Verify max adults '"+ maxAdultsArr[b] +"' and max persons '"+ maxPersonsArr[b] +"' in content section");			
					String text = maxAdultsArr[b].trim() +  " Ad, "+ maxPersonsArr[b].trim() +" Ch";
					String contentAdChildPath = "//div[@class='popover-content']//div[@class='container']//div[contains(text(),'"+ tempGuestName +"')]/..//following-sibling::div";					
					try {
						String getTripText = Utility.getElementText(driver.findElement(By.xpath(contentAdChildPath)));
						Utility.verifyEquals(getTripText.trim(), text, testSteps);
					}catch (Exception|Error e) {
						testSteps.add(e.toString());
						
					}		
					testSteps.add("Verified max adults and max persons '"+ text +"' in content section");			
					
					String tempAmount = roomsAmountArr[b].trim();
					tempAmount = tempAmount.replace(".00", "");
			
					String tempRoomClass = roomClassNameArr[b].trim() + " - " + tempAmount;
					testSteps.add("Verify roomclass and adr '"+ tempRoomClass +"' in content section");			
					String contentRoomClassPath = "//div[@class='popover-content']//div[@class='container']//div[contains(text(),'"+ tempGuestName +"')]/..//following-sibling::div//span[contains(@class, 'ir-tc-roomClass')]";
					try {
					WebElement roomClassEle = driver.findElement(By.xpath(contentRoomClassPath));						
					String getTripText = Utility.getElementText(roomClassEle);
					Utility.verifyEquals(getTripText, tempRoomClass, testSteps);
					}catch (Exception|Error e) {
						testSteps.add(e.toString());
						
					}		
					testSteps.add("Verify roomclass and adr '"+ tempRoomClass +"' in content section");			
					
					testSteps.add("Verify roomNo '"+ roomNoArr[b] +"' in content section");			
					String contentRoomNumberPath = "//div[@class='popover-content']//div[@class='container']//div[contains(text(),'"+ tempGuestName +"')]/..//following-sibling::div//span[contains(@class, 'ir-tc-room')]//following-sibling::span";
					WebElement roomNoEle = driver.findElement(By.xpath(contentRoomNumberPath));
					
					try {
						String getTripText = Utility.getElementText(roomNoEle);
						Utility.verifyEquals(getTripText, roomNoArr[b], testSteps);
					}catch (Exception|Error e) {
						testSteps.add(e.toString());
						
					}		
					testSteps.add("Verified roomNo '"+ roomNoArr[b] +"' in content section");			
					
				}
					
				
			}
			
			return testSteps;
		}
		
		public void enterManualOverrideAMount(WebDriver driver, ArrayList<String> testSteps, String overrideAmount) {
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			Wait.waitForElementByXpath(driver, NewTapeChart.tapeChartSearch_OverrideAmount);
			TapeChart.tapeChartSearch_OverrideAmount.clear();
			TapeChart.tapeChartSearch_OverrideAmount.sendKeys(overrideAmount);
			testSteps.add("Enter override amount : " + overrideAmount);
			tapechartLogger.info("Enter override amount : " + overrideAmount);
		}
		
		public void verifyRoomClassUnassigned(WebDriver driver, ArrayList<String> testSteps, String roomClass, String unassigned) throws InterruptedException {
			String path = "//div[text()='"+ roomClass +"'  and contains(@class,'roomclassname')]/..//following-sibling::div//div[@class='row unassignedrow']//div";
			Wait.waitForElementByXpath(driver, path);
			WebElement ele = driver.findElement(By.xpath(path));
			Utility.ScrollToElement(ele, driver);
			String getText = Utility.getElementText(ele);
			getText = getText.split(" ")[1];
			Utility.verifyEquals(getText, unassigned, testSteps);
		}
		

		public ArrayList<String> getUnassigendeReservationsName(WebDriver driver) {
			Elements_TapeChart tapeChart = new Elements_TapeChart(driver);
			ArrayList<String> resName= new ArrayList<>();
			Wait.waitForElementByXpath(driver, NewTapeChart.unassignedReservationNameList);
			for(int i=0; i< tapeChart.unassignedReservationNameList.size(); i++) {
				resName.add(Utility.getElementText(tapeChart.unassignedReservationNameList.get(i)));
			}	
			tapechartLogger.info("resName : " + resName);
			
			return resName;
		}
		

		public int getUnassignedButtonCount(WebDriver driver) throws InterruptedException {
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			Wait.waitForElementByXpath(driver, OR.Unassigned_Button);
			Utility.ScrollToElement(TapeChart.Unassigned_Button, driver);
			String getText = Utility.getElementText(TapeChart.Unassigned_Button);
			tapechartLogger.info(getText);
			getText = getText.split(" ")[1].trim();
			getText = getText.replace("(", "").trim();
			getText = getText.replace(")", "").trim();
			tapechartLogger.info(getText);
			return Integer.parseInt(getText);
		}
		
		public void clickConfirmButton(WebDriver driver, ArrayList<String> test_steps) {
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			try {
				if (TapeChart.ConfirmChangesButton.isDisplayed()) {
					System.out.println("size not 0 for confirm");
					Utility.ElementFinderUntillNotShow(By.xpath(OR.ConfirmChangesButton), driver);
					Wait.waitUntilPresenceOfElementLocated(OR.ConfirmChangesButton, driver);
					Wait.explicit_wait_elementToBeClickable(TapeChart.ConfirmChangesButton, driver);
					TapeChart.ConfirmChangesButton.click();
					test_steps.add("Click at confirm button");
					tapechartLogger.info("Click at confirm button");
				} else {
					System.out.println("size is 0");
					test_steps.add("Confirm button not available");
					tapechartLogger.info("Confirm button not available");
				}
			} catch (Exception e) {
				test_steps.add("Confirm button not available");
				tapechartLogger.info("Confirm button not available");
			}

		}
		
		public String moveReserVationFromUnassignToAssign(WebDriver driver, String roomClassAbb, String guestName,
				ArrayList<String> test_steps) throws InterruptedException {
			String roomNo=null;
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			Wait.wait2Second();
			String UnassignedRoom = "//span[contains(text(),'"+guestName+"')]/../../ancestor::div[@class='panel panel-danger']";
			String ToPath = "//span[.='"+roomClassAbb+"']//following::div[@class='tapechartdatecell Available RackRate' and @title='Available']";

			String ReservationName = driver.findElement(By.xpath(ToPath)).getAttribute("title");
			System.out.println(ReservationName);

			if (ReservationName.contains("Available")) {
				System.out.println("if");
				test_steps.add("Room Class is available to assigned room class: " + roomClassAbb);
				tapechartLogger.info("Room Class is available to assigned room class: " + roomClassAbb);
				String path="//span[.='"+roomClassAbb+"']//following::div[@class='tapechartdatecell Available RackRate' "
						+ "and @title='Available']/../../../parent::div/div/span";
				Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
				roomNo=driver.findElement(By.xpath(path)).getText();
			} else {
				test_steps.add("Room Class is not available to assigned room class: " + roomClassAbb);
				tapechartLogger.info("Room Class is not available to assigned room class: " + roomClassAbb);

			}

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(UnassignedRoom)));
			System.out.println("Path here:");

			jse.executeScript("window.scrollBy(0,-300)");
			WebElement From = driver.findElement(By.xpath(UnassignedRoom));
			Point Location = From.getLocation();

			Wait.explicit_wait_xpath(ToPath, driver);
			WebElement To = driver.findElement(By.xpath(ToPath));

			// Using Action class for drag and drop.
			Actions act = new Actions(driver);
			// Dragged and dropped
			act.dragAndDrop(From, To).build().perform();

			Wait.wait2Second();
			if (TapeChart.Rules_Broken_popup.isDisplayed()) {
				Wait.explicit_wait_xpath(OR.Rules_Broken_popup, driver);
				TapeChart.Click_Book_icon_Tapechart.click();
				System.out.println("Rules are broken");
			} else {
				System.out.println("Rules are not broken");
			}

			clickConfirmButton(driver, test_steps);

			// verify toaster message here
			try {
				Wait.waitUntilPresenceOfElementLocated(OR.Toaster_Message, driver);
				assertEquals(TapeChart.Toaster_Message.getText(), "Reservation has been updated successfully",
						"Reservation does not Move");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				test_steps.add(e.toString());
			}
			test_steps.add("Unassigned reservation moved successfully to assigned room of Room Class:" + roomClassAbb);
			return roomNo;
		}

		public void verifyBookedReservationWithName(WebDriver driver, String guestName, boolean isOpen, ArrayList<String> test_steps) {
			String path = "(//div[@class='guest_display_name_short'][text()='"+guestName+"'])[2]|(//*[contains(text(),'"+guestName+"')])";
			Utility.customAssert(driver.findElement(By.xpath(path)).isDisplayed()+"", "true", true, "Succesfully Verified Booked Reservaion Displayed in TapChart Named : " + guestName, "Failed to Verify Booked Reservation in TapChart With Name : " + guestName, true, test_steps);
			if(isOpen) {
				driver.findElement(By.xpath(path)).click();
			}
		}
		
		
		public void verifyReservationInUnassignedList(WebDriver driver, ArrayList<String> test_steps, String reservationName, boolean isExistInList, boolean isVerify)
				throws InterruptedException {
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			Wait.WaitForElement(driver, OR.Unassigned_Button);
			Wait.waitForElementToBeClickable(By.xpath(OR.Unassigned_Button), driver);
			Utility.ScrollToElement(TapeChart.Unassigned_Button, driver);

			boolean isfound = false;
			if (isVerify) {
				int size = TapeChart.unassignedReservationNameList.size();
				for(WebElement ele : TapeChart.unassignedReservationNameList) {
					if(ele.getText().equalsIgnoreCase(reservationName)) {
						isfound = true;
						tapechartLogger.info("Reservation Name Found in the Unassigned List : " + reservationName);
						test_steps.add("Reservation Name Found in the Unassigned List : " + reservationName);
						break;
					}
				}
					
				Utility.customAssert(isfound+"", isExistInList+"", isVerify, 
						"Successfully Verified Reservation Available in UnAssigned List : " + isExistInList, 
						"Failed to Verify Reservation Available in UnAssigned List : " + isExistInList, true, test_steps);
			}
			
		}
		
		public void clickSearchBtnInTapeChart(WebDriver driver, ArrayList<String> test_steps) {
			Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
			//JavascriptExecutor jse = (JavascriptExecutor) driver;
			try {
				Wait.WaitForElement(driver, "//div[@id='tapeChartSearch']//button[@value='Search']");
				TapeChart.Click_Search_TapeChart.click();
			
			}catch (Exception e) {
				//Wait.waitForElementToBeInvisible(driver, TapeChart.Click_Search_TapeChart, 30);
				TapeChart.Click_Search_TapeChart.click();
			}
			tapechartLogger.info("Search Button Clicked");
			test_steps.add("Search Button Clicked");
		}

}