
package com.innroad.inncenter.pageobjects;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import java.util.stream.IntStream;

import javax.print.attribute.standard.PrinterURI;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.GetElementText;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.innroad.inncenter.pages.NewFolio;
import com.innroad.inncenter.pages.NewTapeChart;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Admin;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Admin;
import com.innroad.inncenter.webelements.Elements_All_Payments;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_GuestHistory;
import com.innroad.inncenter.webelements.Elements_MovieFolio;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.innroad.inncenter.webelements.Elements_Reservation_SearchPage;
import com.innroad.inncenter.webelements.Elements_SetUp_Properties;
import com.innroad.inncenter.webelements.Elements_TapeChart;

public class ReservationHomePage {
	
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> teststeps= new ArrayList<>();
	public String Amount = "";
	public static Logger reslogger = Logger.getLogger("CPReservationPage");
	CPReservationPage reservation= new CPReservationPage();
	public ArrayList<String> verifyNewReservationTab(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		testSteps.add("=== " + "Verify 'New Reservation' button is displaying".toUpperCase() + " ===");
		reslogger.info("=== " + "Verify 'New Reservation' button is displaying".toUpperCase() + " ===");

		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.CP_NewReservation), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.CP_NewReservation), driver);
		assertTrue(res.CP_NewReservation.isDisplayed(), "Failed : new reservation button is not displaying");
		testSteps.add("'New Reservation' button is displaying");
		reslogger.info("'New Reservation' button is displaying");

		testSteps.add("=== " + "Clicking on 'New Reservation' button".toUpperCase() + " ===");
		reslogger.info("=== " + "Clicking on 'New Reservation' button".toUpperCase() + " ===");
		res.CP_NewReservation.click();

		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_CheckinCal);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.CP_NewReservation_CheckinCal), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.CP_NewReservation_CheckinCal), driver);
		assertTrue(res.CP_NewReservation_CheckinCal.isDisplayed(),
				"Failed : Reservation creation page is not displaying after clicking new reservation button");

		testSteps.add("Verified reservation creation page is displaying after clicking on 'New Reservation' button");
		reslogger.info("Verified reservation creation page is displaying after clicking on 'New Reservation' button");

		return testSteps;
	}

	public ArrayList<String> verifyTapeChartTab(WebDriver driver) {
		Elements_On_All_Navigation navigate = new Elements_On_All_Navigation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== " + "Verifying 'Tape Chart' tab is displaying".toUpperCase() + " ===");
		reslogger.info("=== " + "Verifying 'Tape Chart' tab is displaying".toUpperCase() + " ===");

		Wait.WaitForElement(driver, OR.Tape_Chart_1);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Tape_Chart_1), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Tape_Chart_1), driver);
		assertTrue(navigate.Tape_Chart_1.isDisplayed(), "Failed : tape chart tab is not displaying");
		testSteps.add("Verified that 'Tape chart' tab is displaying");
		reslogger.info("Verified that 'Tape chart' tab is displaying");

		return testSteps;

	}

	public ArrayList<String> verifyReservationTab(WebDriver driver) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== " + "Verifying 'Reservation' tab is displaying".toUpperCase() + " ===");
		reslogger.info("=== " + "Verifying 'Reservation' tab is displaying".toUpperCase() + " ===");

		Wait.WaitForElement(driver, OR_Reservation.reservationTab);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.reservationTab), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.reservationTab), driver);
		Utility.ScrollToElement(res.reservationTab, driver);
		assertTrue(res.reservationTab.isDisplayed(), "Failed : reservation tab is not displaying");
		testSteps.add("Verified that 'Reservation' tab is displaying");
		reslogger.info("Verified that 'Reservation' tab is displaying");

		return testSteps;

	}

	public ArrayList<String> clickReservationTab(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		try {

			Wait.WaitForElement(driver, OR_Reservation.reservationTab);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.reservationTab), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.reservationTab), driver);

			res.reservationTab.click();
			testSteps.add("'Reservation' tab clicked");
			reslogger.info("'Reservation' tab clicked");

		} catch (Exception e) {
			res.reservationTab_2.click();
			testSteps.add("'Reservation' tab clicked");
			reslogger.info("'Reservation' tab clicked");

		}

		return testSteps;

	}

	public ArrayList<String> verifyGuestHistoryTab(WebDriver driver) {
		Elements_On_All_Navigation navigate = new Elements_On_All_Navigation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== " + "Verifying 'Guest History' tab is displaying".toUpperCase() + " ===");
		reslogger.info("=== " + "Verifying 'Guest History' tab is displaying".toUpperCase() + " ===");

		Wait.WaitForElement(driver, OR.Guest_HistoryIcon);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Guest_HistoryIcon), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Guest_HistoryIcon), driver);
		assertTrue(navigate.Guest_HistoryIcon.isDisplayed(), "Failed : Guest history tab is not displaying");
		testSteps.add("Verified that 'Guest history' tab is displaying");
		reslogger.info("Verified that 'Guest history' tab is displaying");

		return testSteps;

	}

	public ArrayList<String> verifyGroupsTab(WebDriver driver) {
		Elements_On_All_Navigation navigate = new Elements_On_All_Navigation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== " + "Verifying 'Groups' tab is displaying".toUpperCase() + " ===");
		reslogger.info("=== " + "Verifying 'Groups' tab is displaying".toUpperCase() + " ===");

		Wait.WaitForElement(driver, OR.Groups);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Groups), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Groups), driver);
		assertTrue(navigate.Groups.isDisplayed(), "Failed : Groups tab is not displaying");
		testSteps.add("Verified that 'Groups' tab is displaying");
		reslogger.info("Verified that 'Groups' tab is displaying");

		return testSteps;

	}

	public ArrayList<String> verifyGuestNameinput(WebDriver driver) throws InterruptedException {
		Elements_Reservation_SearchPage reservationSearch = new Elements_Reservation_SearchPage(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying 'Guest Name' Input is displaying ===");
		reslogger.info("=== Verifying 'Guest Name' Input is displaying ===");

		Wait.WaitForElement(driver, OR.BasicGuestName);
		Wait.waitForElementToBeVisibile(By.xpath(OR.BasicGuestName), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.BasicGuestName), driver);
		assertTrue(reservationSearch.BasicGuestName.isDisplayed(), "Failed : Basic Guest Name Input is not displaying");
		testSteps.add("Verified that 'Guest Name' Input is displaying");
		reslogger.info("Verified that 'Guest Name' Input is displaying");

		return testSteps;

	}

	public ArrayList<String> verifyReservationNumberinput(WebDriver driver) throws InterruptedException {
		Elements_Reservation_SearchPage reservationSearch = new Elements_Reservation_SearchPage(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying 'Reservation number' Input is displaying ===");
		reslogger.info("=== Verifying 'Reservation number' Input is displaying ===");

		Wait.WaitForElement(driver, OR.Basic_Res_Number);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Basic_Res_Number), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Basic_Res_Number), driver);
		assertTrue(reservationSearch.Basic_Res_Number.isDisplayed(),
				"Failed : Reservation number Input is not displaying");
		testSteps.add("Verified that 'Reservation number' Input is displaying");
		reslogger.info("Verified that 'Reservation number' Input is displaying");

		return testSteps;

	}

	public ArrayList<String> verifyReservationSearchIcon(WebDriver driver) throws InterruptedException {
		Elements_Reservation_SearchPage reservationSearch = new Elements_Reservation_SearchPage(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying 'Reservation Search' icon is displaying ===");
		reslogger.info("=== Verifying 'Reservation Search' icon is displaying ===");

		Wait.WaitForElement(driver, OR.Click_BasicSearch);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Click_BasicSearch), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Click_BasicSearch), driver);
		assertTrue(reservationSearch.Click_BasicSearch.isDisplayed(),
				"Failed : Reservation search icon is not displaying");
		testSteps.add("Verified that 'Reservation Search' icon is displaying");
		reslogger.info("Verified that 'Reservation Search' icon is displaying");

		return testSteps;

	}

	public ArrayList<String> verifyAdvanceButton(WebDriver driver) throws InterruptedException {
		Elements_Reservation_SearchPage reservationSearch = new Elements_Reservation_SearchPage(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying 'Advance' button is displaying ===");
		reslogger.info("=== Verifying 'Advance' button is displaying ===");

		Wait.WaitForElement(driver, OR.AdvancedSearch);
		Wait.waitForElementToBeVisibile(By.xpath(OR.AdvancedSearch), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.AdvancedSearch), driver);
		Utility.ScrollToElement(reservationSearch.AdvancedSearch, driver);
		assertTrue(reservationSearch.AdvancedSearch.isDisplayed(), "Failed : Advance button is not displaying");
		testSteps.add("Verified that 'Advance' button is displaying");
		reslogger.info("Verified that 'Advance' button is displaying");

		return testSteps;

	}

	public ArrayList<String> clickReservationCloumns(WebDriver driver, String colmunName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		String tabPath = "(//span[contains(text(),'" + colmunName + "')]//parent::div)[1]";
		Wait.WaitForElement(driver, tabPath);
		Wait.waitForElementToBeVisibile(By.xpath(tabPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
		WebElement tab = driver.findElement(By.xpath(tabPath));
		Utility.ScrollToElement(tab, driver);
		if (tab.isSelected()) {
			testSteps.add("'" + colmunName + "' column already clicked from reservations table");
			reslogger.info("'" + colmunName + "' column already clicked from reservations table");

		} else {
			tab.click();
			testSteps.add("'" + colmunName + "' column click from reservations table");
			reslogger.info("'" + colmunName + "' column click from reservations table");

		}
		return testSteps;

	}

	public ArrayList<String> verifyReservationCloumns(WebDriver driver, String colmunName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying '" + colmunName + "' column is displaying in reservations table ===");
		reslogger.info("=== Verifying '" + colmunName + "' column is displaying in reservations table ===");
		try {
			String tabPath = "(//span[contains(text(),'" + colmunName + "')]//parent::div)[1]";
			Wait.WaitForElement(driver, tabPath);
			WebElement tab = driver.findElement(By.xpath(tabPath));
			assertTrue(tab.isDisplayed(), "Failed : " + colmunName + " column is not displaying");
			testSteps.add("Verified that '" + colmunName + "' column is displaying in reservations table");
			reslogger.info("Verified that '" + colmunName + "' column is displaying in reservations table");

		} catch (Exception | AssertionError e) {
			reslogger.info("In UI catch");
			testSteps.add(e.toString());
		}

		return testSteps;

	}

	public ArrayList<String> verifyTaskCloumns(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying 'Task' column is displaying in reservations table ===");
		reslogger.info("=== Verifying 'Task' column is displaying in reservations table ===");

		try {
			String tabPath = "//th[contains(text(),'Task')]";
			Wait.WaitForElement(driver, tabPath);
			WebElement tab = driver.findElement(By.xpath(tabPath));
			assertTrue(tab.isDisplayed(), "Failed : Task column is not displaying");
			testSteps.add("Verified that 'Task' column is displaying in reservations table");
			reslogger.info("Verified that 'Task' column is displaying in reservations table");

		} catch (Exception | AssertionError e) {
			reslogger.info("In UI catch");
			testSteps.add(e.toString());
		}

		return testSteps;

	}

	public ArrayList<String> clickDashBoardOption(WebDriver driver, String tabName, boolean isClick)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying '" + tabName + "' tab is displaying in dashboard ===");
		reslogger.info("=== Verifying '" + tabName + "' tab is displaying in dashboard ===");

		String tabPath = "//span[text()='" + tabName + "']//preceding-sibling::span";
		Wait.WaitForElement(driver, tabPath);
		Wait.waitForElementToBeVisibile(By.xpath(tabPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
		WebElement tab = driver.findElement(By.xpath(tabPath));
		Utility.ScrollToElement(tab, driver);
		assertTrue(tab.isDisplayed(), "Failed : " + tabName + " tab is not displaying");
		testSteps.add("Verified that '" + tabName + "' tab is displaying in dashboard");
		reslogger.info("Verified that '" + tabName + "' tab is displaying in dashboard");

		if (isClick) {

			tab.click();
			testSteps.add("'" + tabName + "' tab click from dashboard");
			reslogger.info("'" + tabName + "' tab click from dashboard");

		}

		return testSteps;

	}

	public int getDashBoardOptionValue(WebDriver driver, String tabName) throws InterruptedException {

		String tabPath = "//span[text()='" + tabName + "']//preceding-sibling::span";
		Wait.WaitForElement(driver, tabPath);
		Wait.waitForElementToBeVisibile(By.xpath(tabPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
		WebElement tab = driver.findElement(By.xpath(tabPath));
		Utility.ScrollToElement(tab, driver);
		String getText = tab.getText().trim();
		reslogger.info(getText);
		return Integer.parseInt(getText);

	}

	public ArrayList<String> clickOnAllArivalsDropDown(WebDriver driver, int index) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		String tabPath = "(//a[@data-toggle='dropdown'])[" + index + "]";
		Wait.WaitForElement(driver, tabPath);
		Wait.waitForElementToBeVisibile(By.xpath(tabPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
		WebElement tab = driver.findElement(By.xpath(tabPath));
		Utility.ScrollToElement(tab, driver);
		tab.click();
		if (index == 2) {
			testSteps.add("Clicked on All arrivals dropdown icon");
			reslogger.info("Clicked on All arrivals dropdown icon");

		} else {
			testSteps.add("Clicked on All departures dropdown icon");
			reslogger.info("Clicked on All departures dropdown icon");

		}

		return testSteps;

	}

	public int getAllArivalsAndDepartureDropDown(WebDriver driver, int index) throws InterruptedException {

		String tabPath = "(//span[text()='Arrivals & Departure']//preceding-sibling::span)[" + index + "]";
		Wait.WaitForElement(driver, tabPath);
		Wait.waitForElementToBeVisibile(By.xpath(tabPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
		WebElement tab = driver.findElement(By.xpath(tabPath));
		Utility.ScrollToElement(tab, driver);
		String getText = tab.getText().trim();
		reslogger.info("Arrivals & Departure : " + getText);

		return Integer.parseInt(getText);

	}

	public int getPendingArrivals(WebDriver driver) throws InterruptedException {

		String tabPath = "(//span[text()='Pending Arrivals']//preceding-sibling::span)[1]";
		Wait.WaitForElement(driver, tabPath);
		Wait.waitForElementToBeVisibile(By.xpath(tabPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
		WebElement tab = driver.findElement(By.xpath(tabPath));
		Utility.ScrollToElement(tab, driver);
		String getText = tab.getText().trim();
		reslogger.info("Pending Arrivals : " + getText);

		return Integer.parseInt(getText);

	}

	public int getPendingDepartures(WebDriver driver) throws InterruptedException {

		String tabPath = "(//span[text()='Pending Departures']//preceding-sibling::span)[1]";
		Wait.WaitForElement(driver, tabPath);
		Wait.waitForElementToBeVisibile(By.xpath(tabPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(tabPath), driver);
		WebElement tab = driver.findElement(By.xpath(tabPath));
		Utility.ScrollToElement(tab, driver);
		String getText = tab.getText().trim();
		reslogger.info("Pending Departures : " + getText);

		return Integer.parseInt(getText);

	}

	public ArrayList<String> verifyRecordFoundLabel(WebDriver driver) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying 'Record(s) found' label is displaying ===");
		reslogger.info("=== Verifying 'Record(s) found' label is displaying ===");

		try {
			Wait.WaitForElement(driver, OR_Reservation.recordFoundLabel);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.recordFoundLabel), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.recordFoundLabel), driver);
			Utility.ScrollToElement(res.recordFoundLabel, driver);
			assertTrue(res.recordFoundLabel.isDisplayed(), "Failed : Record found is not displaying");
			testSteps.add("Verified that 'Record(s) found' label is displaying");
			reslogger.info("Verified that 'Record(s) found' label is displaying");

		} catch (Exception | AssertionError e) {
			reslogger.info("In UI catch");
			testSteps.add(e.toString());
		}

		return testSteps;

	}

	public int getTotalRecordFound(WebDriver driver) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.recordFound);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.recordFound), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.recordFound), driver);
		Utility.ScrollToElement(res.recordFound, driver);
		int getRecord = Integer.parseInt(res.recordFound.getText().trim());
		reslogger.info("getRecord : " + getRecord);
		return getRecord;

	}

	public ArrayList<String> verifyItemsPerPage(WebDriver driver) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		testSteps.add("=== Verifying 'Items Per Page' dropdown is displaying ===");
		reslogger.info("=== Verifying 'Items Per Page' dropdown is displaying ===");

		try {
			Wait.WaitForElement(driver, OR_Reservation.selectItemsPerPage);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.selectItemsPerPage), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.selectItemsPerPage), driver);
			Utility.ScrollToElement(res.selectItemsPerPage, driver);
			assertTrue(res.selectItemsPerPage.isDisplayed(), "Failed : Items Per Page is not displaying");
			testSteps.add("Verified that 'Items Per Page' dropdown is displaying");
			reslogger.info("Verified that 'Items Per Page' dropdown is displaying");

		} catch (Exception | AssertionError e) {
			reslogger.info("In UI catch");
			testSteps.add(e.toString());
		}

		return testSteps;

	}

	public ArrayList<String> selectItemsPerPage(WebDriver driver, String itemsToSelect) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.WaitForElement(driver, OR_Reservation.selectItemsPerPage);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.selectItemsPerPage), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.selectItemsPerPage), driver);
		Utility.ScrollToElement(res.selectItemsPerPage, driver);
		Select selectItems = new Select(res.selectItemsPerPage);
		selectItems.selectByVisibleText(itemsToSelect);
		testSteps.add("Selected " + itemsToSelect + " 'Items Per Page'");
		reslogger.info("Selected " + itemsToSelect + " 'Items Per Page'");

		return testSteps;

	}

	public ArrayList<String> verifyCloumnSorted(WebDriver driver, String colmunName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		String columnPath = "";
		if (colmunName.equalsIgnoreCase("Property")) {
			testSteps.addAll(clickReservationCloumns(driver, "Property"));
			columnPath = "(//span[contains(text(),'Property')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'PropertyName')]";
		} else if (colmunName.equalsIgnoreCase("Guest Name")) {
			testSteps.addAll(clickReservationCloumns(driver, "Guest Name"));
			columnPath = "(//span[contains(text(),'Guest Name')]//..//../..)[1]//following::tr//a[contains(@data-bind, 'GuestName')]";
		} else if (colmunName.equalsIgnoreCase("Account Name")) {
			testSteps.addAll(clickReservationCloumns(driver, "Account Name"));
			columnPath = "(//span[contains(text(),'Account Name')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'AccountName')]";
		} else if (colmunName.equalsIgnoreCase("Res#")) {
			testSteps.addAll(clickReservationCloumns(driver, "Res#"));
			columnPath = "(//span[contains(text(),'Res#')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'ConfirmationNumber')]";
		} else if (colmunName.equalsIgnoreCase("Adults")) {
			testSteps.addAll(clickReservationCloumns(driver, "Adults"));
			columnPath = "(//span[contains(text(),'Adults')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'NumberOfAdults')]";
		} else if (colmunName.equalsIgnoreCase("Child")) {
			testSteps.addAll(clickReservationCloumns(driver, "Child"));
			columnPath = "(//span[contains(text(),'Child')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'NumberOfChildren')]";
		} else if (colmunName.equalsIgnoreCase("Status")) {
			testSteps.addAll(clickReservationCloumns(driver, "Status"));
			columnPath = "(//span[contains(text(),'Status')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'StatusName')]";
		} else if (colmunName.equalsIgnoreCase("Room")) {
			testSteps.addAll(clickReservationCloumns(driver, "Room"));
			columnPath = "(//span[contains(text(),'Room')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'RoomClassName')]";
		} else if (colmunName.equalsIgnoreCase("Arrive")) {
			testSteps.addAll(clickReservationCloumns(driver, "Arrive"));
			columnPath = "(//span[contains(text(),'Arrive')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'DateStart')]";
		} else if (colmunName.equalsIgnoreCase("Depart")) {
			testSteps.addAll(clickReservationCloumns(driver, "Depart"));
			columnPath = "(//span[contains(text(),'Depart')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'DateEnd')]";
		} else if (colmunName.equalsIgnoreCase("Nights")) {
			testSteps.addAll(clickReservationCloumns(driver, "Nights"));
			columnPath = "(//span[contains(text(),'Nights')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'NoOfNights')]";
		}

		Wait.waitforPageLoad(30, driver);
		Wait.WaitForElement(driver, columnPath);
		Wait.waitForElementToBeVisibile(By.xpath(columnPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(columnPath), driver);
		List<WebElement> column = driver.findElements(By.xpath(columnPath));
		ArrayList<String> getColumnData = new ArrayList<>();
		ArrayList<String> sortedColumnData = new ArrayList<>();
		if (column.size() > 0) {

			for (int i = 0; i < column.size(); i++) {
				String getText = column.get(i).getText().trim();
				System.out.println(getText);
				getColumnData.add(getText);
			}

			sortedColumnData = getColumnData;
			Collections.sort(sortedColumnData);
			reslogger.info(sortedColumnData.toString());
			reslogger.info(getColumnData.toString());
			testSteps.add("Column '" + colmunName + "' List Items : " + getColumnData.toString());
			reslogger.info("Column '" + colmunName + "' List Items : " + getColumnData.toString());

			assertEquals(getColumnData, sortedColumnData,
					"Failed : reservation list is not sorted based on column 'guestname'");
			testSteps.add("Verified that Reservation list is sorted upon clicking '" + colmunName + "' column");
			reslogger.info("Verified that Reservation list is sorted upon clicking '" + colmunName + "' column");
		} else {
			assertTrue(false, "Failed : no records found");
		}

		return testSteps;

	}

	public int getReservationListSize(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		Wait.WaitForElement(driver, OR_Reservation.selectItemsPerPage);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.selectItemsPerPage), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.selectItemsPerPage), driver);
		int size = res.reservationList.size();
		size -= 1;
		reslogger.info("reservation count : " + size);
		return size;
	}

	public String verifyReservationRoomNumber(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		Wait.WaitForElement(driver, OR_Reservation.roomNumber);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.roomNumber), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.roomNumber), driver);
		String getRoomNumber = res.roomNumber.getText().trim();
		reslogger.info("getRoomNumber : " + getRoomNumber);

		return getRoomNumber;

	}

	public ArrayList<String> clickConfirmChekInButton(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		try {
			Wait.WaitForElement(driver, OR_Reservation.confirmCheckin);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.confirmCheckin), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.confirmCheckin), driver);
			res.confirmCheckin.click();
			testSteps.add("'Confirm checkin' button clicked");
			reslogger.info("'Confirm checkin' button clicked");

		} catch (Exception e) {
			Wait.WaitForElement(driver, OR_Reservation.proceedToCheckinPayment);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.proceedToCheckinPayment), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.proceedToCheckinPayment), driver);
			reslogger.info("Proceed to chekcin button dipsplay didn't display");
			res.proceedToCheckinPayment.click();
			testSteps.add("'Proceed to checkin payment' button clicked");
			reslogger.info("'Proceed to checkin payment' button clicked");

		}

		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.confirmDirtyRoomCheckin), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.confirmDirtyRoomCheckin), driver);
			res.confirmDirtyRoomCheckin.click();
			testSteps.add("Clicked on 'Confirm' button to allow dirty room checkin");
			reslogger.info("Clicked on 'Confirm' button to allow dirty room checkin");

		} catch (Exception e) {
			reslogger.info("Dirty room popup didn't display");
		}

		return testSteps;

	}

	public ArrayList<String> clickPayButton(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_Reservation.PayButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.PayButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.PayButton), driver);

		res.PayButton.click();
		testSteps.add("'Pay' button clicked");
		reslogger.info("'Pay' button clicked");

		return testSteps;

	}

	public ArrayList<String> clickCloseCheckoutSuccessfullPopup(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_Reservation.closeSuccesPopup);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.closeSuccesPopup), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.closeSuccesPopup), driver);

		res.closeSuccesPopup.click();
		testSteps.add("Click on Checkout successfull popup 'Close' button ");
		reslogger.info("Click on Checkout successfull popup 'Close' button ");

		return testSteps;

	}

	public ArrayList<String> clickCloseCheckinSuccessfullPopup(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_Reservation.closeCheckinSuccessPopup);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.closeCheckinSuccessPopup), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.closeCheckinSuccessPopup), driver);

		res.closeCheckinSuccessPopup.click();
		testSteps.add("Click on Checkin successfull popup 'Close' button ");
		reslogger.info("Click on Checkin successfull popup 'Close' button ");

		return testSteps;

	}

	
	public void checkoutReservation(WebDriver driver, ArrayList<String> test_steps) {
		String checkout = "(//button[text()='Check Out'])";
		Wait.WaitForElement(driver, checkout);
		driver.findElement(By.xpath(checkout)).click();
		reslogger.info("click on check out");
		test_steps.add("click on check out");
		
		String generateGuestStatement = "//span[contains(@data-bind , 'click: function() {checkOutDetail.generateGuestStatement();}')]";
		Wait.WaitForElement(driver, generateGuestStatement);
		driver.findElement(By.xpath(generateGuestStatement)).click();
		reslogger.info("click on generateGuestStatement toggle");
		test_steps.add("click on generateGuestStatement toggle");
		

		String ProceedToCheckout = "(//h4[text()='Check Out']/../..//button)[2]";
		Wait.WaitForElement(driver, ProceedToCheckout);
		driver.findElement(By.xpath(ProceedToCheckout)).click();
		reslogger.info("click on ProceedToCheckout");
		test_steps.add("click on ProceedToCheckout");
		
		String AmountID = "payFormAmountV2";
		String Amount = driver.findElement(By.id(AmountID)).getAttribute("value");
		reslogger.info("Total Balance: "+Amount);
		test_steps.add("Total Balance: "+Amount);
		
		
		String AuthTypexpath = "//label[text()='TYPE']//..//span[@class='filter-option pull-left']";
		Wait.WaitForElement(driver, AuthTypexpath);
		String AuthType =  driver.findElement(By.xpath(AuthTypexpath)).getText();
		reslogger.info("Type: "+AuthType);
		test_steps.add("Type: "+AuthType);
		
		
		
		

		String pay = "//h4[text()='Check Out Payment']/../..//button[contains(text(),'Pay')]";
		if (driver.findElements(By.xpath(pay)).size() > 0) {
			driver.findElement(By.xpath(pay)).click();
			reslogger.info("click on pay");
			test_steps.add("click on pay");
			String close = "//h4[text()='Check out Successful']/../..//button[text()='CLOSE']";
			Wait.WaitForElement(driver, close);
			driver.findElement(By.xpath(close)).click();
			reslogger.info("click on close");
			test_steps.add("click on close");
		} else {
			String confirm = "(//h4[text()='Check Out']//../..//button)[2]";
			Wait.WaitForElement(driver, confirm);
			driver.findElement(By.xpath(confirm)).click();
			reslogger.info("click on confirm check out");
			test_steps.add("click on confirm check out");
		}
		String rollBack = "//button[text()='Roll Back']";
		Wait.WaitForElement(driver, rollBack);

	}
	
	public ArrayList<String> clickOnDirtyPopUp(WebDriver driver, ArrayList<String> test_sreps){
		String path = "//div[contains(text(),'The room status is dirty. Do you')]//following-sibling::div//button[contains(text(),'Confirm')]"; 
		try {
			Wait.wait5Second();
			Wait.WaitForElement(driver,path);
			driver.findElement(By.xpath(path)).click();
			test_sreps.add("click on popup dirty button");
			reslogger.info("click on popup dirty button");

		} catch (Exception e) {
			test_sreps.add("dirty room popup not  displyed");
			reslogger.info("dirty room popup not  displyed");
		}
		return test_sreps;

	}

	public void VerifyChechInPaymentPopup(WebDriver driver , ArrayList<String> testSteps,String CheckInAmount) throws InterruptedException {
		
		try {
			Thread.sleep(1000);
			String h = "//h4[contains(text(),'Check In Payment')]";
			Wait.WaitForElement(driver, h);
			String heading = driver.findElement(By.xpath(h)).getText();
			assertEquals(heading, "Check In Payment","Failed to verify Popup Heading");
			testSteps.add("Heading Verified: <b>"+heading);
			
			Thread.sleep(1000);
			String AmountID = "payFormAmountV2";
			String Amount = driver.findElement(By.id(AmountID)).getAttribute("value");
			assertEquals(String.valueOf(Double.valueOf(Amount)), CheckInAmount,"Failed to verify Popup Amount");
			testSteps.add("Amount Verified: <b>"+Amount);
			
			Thread.sleep(1000);
			String typeXpath = "//label[contains(text(),'TYPE')]//..//span[@class='filter-option pull-left']";
			Wait.WaitForElement(driver, typeXpath);
			String Type = driver.findElement(By.xpath(typeXpath)).getText();
			assertEquals(Type, "Authorization Only","Failed to verify POpup Check In Policy Type");
			testSteps.add("Type Verified: <b>"+Type);
			
			Thread.sleep(1000);
			String ButtonXpath = "//button[contains(text(),'Authorize $ "+CheckInAmount+"')]|//a[contains(text(),'Authorize $ "+CheckInAmount+"')]";
			Wait.WaitForElement(driver, ButtonXpath);
			Wait.waitForElementToBeClickable(By.xpath(ButtonXpath), driver);
			driver.findElement(By.xpath(ButtonXpath)).click();
			testSteps.add("Click On Button: <b>Authorize $ "+CheckInAmount+"<b>");
			
			
			Thread.sleep(1000);
			String SuccessPopuph = "//h4[contains(text(),'Check-In Successful')]";
			Wait.WaitForElement(driver, SuccessPopuph);
			String Successpopupheading = driver.findElement(By.xpath(SuccessPopuph)).getText();
			assertEquals(Successpopupheading, "Check-In Successful","Failed to verify Popup Heading");
			testSteps.add(" Check In Successful Popup Heading Verified: <b>"+Successpopupheading);
			
			Thread.sleep(1000);
			String CloseButtonXpath = "//button[contains(@data-bind,'click: checkInDetail.closeModal')]";
			Wait.WaitForElement(driver, CloseButtonXpath);
			Wait.waitForElementToBeClickable(By.xpath(CloseButtonXpath), driver);
			driver.findElement(By.xpath(CloseButtonXpath)).click();
			testSteps.add("Click On Button: <b>Close<b>");
		} catch (Exception e) {
			testSteps.add("Successfully Checked In");
		}
		
		
		
		
		
		
	}
	
	
	public void VerifyChechOutPaymentPopup(WebDriver driver , ArrayList<String> testSteps,String CheckInAmount) throws InterruptedException {
		Thread.sleep(1000);
		String h = "//h4[contains(text(),'Check Out Payment')]";
		Wait.WaitForElement(driver, h);
		String heading = driver.findElement(By.xpath(h)).getText();
		assertEquals(heading, "Check Out Payment","Failed to verify Popup Heading");
		testSteps.add("Heading Verified: <b>"+heading);
		
		Thread.sleep(1000);
		String AmountID = "payFormAmountV2";
		String Amount = driver.findElement(By.id(AmountID)).getAttribute("value");
		assertEquals(String.valueOf(Double.valueOf(Amount)), CheckInAmount,"Failed to verify Popup Amount");
		testSteps.add("Amount Verified: <b>"+Amount);
		
		Thread.sleep(1000);
		String typeXpath = "//label[contains(text(),'TYPE')]//..//span[@class='filter-option pull-left']";
		Wait.WaitForElement(driver, typeXpath);
		String Type = driver.findElement(By.xpath(typeXpath)).getText();
		assertEquals(Type, "Capture","Failed to verify POpup Check In Policy Type");
		testSteps.add("Type Verified: <b>"+Type);
		
		Thread.sleep(1000);
		String ButtonXpath = "//a[contains(text(),'Pay $ "+CheckInAmount+"')]";
		Wait.WaitForElement(driver, ButtonXpath);
		Wait.waitForElementToBeClickable(By.xpath(ButtonXpath), driver);
		driver.findElement(By.xpath(ButtonXpath)).click();
		testSteps.add("Click On Button: <b>Pay $ "+CheckInAmount+"<b>");
		
		try {
		Thread.sleep(1000);
		String SuccessPopuph = "//h4[contains(text(),'Check out Successful')]";
		Wait.WaitForElement(driver, SuccessPopuph);
		String Successpopupheading = driver.findElement(By.xpath(SuccessPopuph)).getText();
		//assertEquals(Successpopupheading, "Check out Successful","Failed to verify Popup Heading");
		//testSteps.add(" Check In Successful Popup Heading Verified: <b>"+Successpopupheading);
		
		Thread.sleep(1000);
		String CloseButtonXpath = "//button[contains(@data-bind,'click: checkOutDetail.closeModal')]";
		Wait.WaitForElement(driver, CloseButtonXpath);
		Wait.waitForElementToBeClickable(By.xpath(CloseButtonXpath), driver);
		driver.findElement(By.xpath(CloseButtonXpath)).click();
		testSteps.add("Click On Button: <b>Close<b>");
		
		}catch (Exception e) {
			
		}
		
		
	}
	
	public ArrayList<String> verifyCalendar(WebDriver driver, String date) throws ParseException{
		ArrayList<String> testSteps = new ArrayList<>();
		String getDate = ESTTimeZone.reformatDate(date, "MM/dd/yyyy", "dd/MM/yyyy");
		reslogger.info("getDate : " + getDate);
		String getMonth = Utility.get_Month(getDate);
		getMonth = getMonth.toUpperCase();
		reslogger.info("getMonth : " + getMonth);
		String getDay = Utility.getDay(getDate);
		reslogger.info("getDay : " + getDay);
		String getYear = Utility.get_Year(getDate);
		reslogger.info("getYear : " + getYear);

		String dayPath = "//div[@class='hidden-sm hidden-xs']//child::span[@class='ng-date' and text()='" + getDay
				+ "']";
		String monthPath = "//div[@class='hidden-sm hidden-xs']//child::span[@class='ng-month' and text()='" + getMonth
				+ "']";
		String yearPath = "//div[@class='hidden-sm hidden-xs']//child::span[@class='ng-year' and text()='" + getYear
				+ "']";

		WebElement day = driver.findElement(By.xpath(dayPath));
		WebElement month = driver.findElement(By.xpath(monthPath));
		WebElement year = driver.findElement(By.xpath(yearPath));

		assertTrue(day.isDisplayed(), "Failed : current day is not displaying");
		assertTrue(month.isDisplayed(), "Failed : current month is not displaying");
		assertTrue(year.isDisplayed(), "Failed : current year is not displaying");

		testSteps.add("Verified that calendar has selected current date as default date");
		reslogger.info("Verified that calendar has selected current date as default date");

		return testSteps;
	}

	public ArrayList<String> selectDateFromDashboardCalendar(WebDriver driver, String dateToSelect)
			throws ParseException, InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.WaitForElement(driver, OR_Reservation.calendarIcon);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.calendarIcon), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.calendarIcon), driver);

		res.calendarIcon.click();
		testSteps.add("Click on Calendar icon");
		reslogger.info("Click on Calendar icon");

		String getDate = ESTTimeZone.reformatDate(dateToSelect, "MM/dd/yyyy", "dd/MM/yyyy");
		reslogger.info("getDate : " + getDate);
		String monthYear = Utility.get_MonthYear(getDate);
		String day = Utility.getDay(getDate);
		reslogger.info(monthYear);
		String headerText = null, date;
		for (int i = 1; i <= 12; i++) {

			headerText = res.calendarHeading.getText().trim();
			if (headerText.equalsIgnoreCase(monthYear)) {
				date = "(//div[@class='datepicker-days'])[1]//child::tbody//td[(@class='day' and text()='" + day
						+ "') or (@class='active day' and text()='" + day + "')]";
				Wait.WaitForElement(driver, date);
				driver.findElement(By.xpath(date)).click();
				testSteps.add("Selecting date : " + dateToSelect);
				reslogger.info("Selecting date : " + dateToSelect);
				break;
			} else {
				if (ESTTimeZone.CompareDates(getDate, "dd/MM/yyyy", "US/Eastren")) {
					reslogger.info("Date is greater than current date");
					Wait.WaitForElement(driver, OR_Reservation.calendarPreviousIcon);
					res.calendarPreviousIcon.click();
					Wait.wait2Second();
					reslogger.info("Clicked previous icon");

				} else {
					reslogger.info("Date is less than current date");
					Wait.WaitForElement(driver, OR_Reservation.calendarNextIcon);
					res.calendarNextIcon.click();
					Wait.wait2Second();
					reslogger.info("Clciked next icon");

				}
			}
		}

		return testSteps;
	}

	public ArrayList<String> verifyMonthsFromDashboardCalendar(WebDriver driver)
			throws ParseException, InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		testSteps.add(
				"<b>===== " + "Verifying bydefult current month is selected in calendar".toUpperCase() + " ====</b>");
		reslogger.info("===== " + "Verifying bydefult current month is selected in calendar".toUpperCase() + " ====");

		Wait.WaitForElement(driver, OR_Reservation.calendarIcon);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.calendarIcon), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.calendarIcon), driver);

		res.calendarIcon.click();
		testSteps.add("Click on Calendar icon");
		reslogger.info("Click on Calendar icon");

		String getCurrentDate = Utility.getCurrentDate("MMMM yyyy");
		reslogger.info(getCurrentDate);
		String headerText = res.calendarHeading.getText().trim();
		testSteps.add("Expected Month Year : " + getCurrentDate);
		reslogger.info("Expected Month Year : " + getCurrentDate);
		testSteps.add("Found : " + headerText);
		reslogger.info("Found : " + headerText);
		assertEquals(headerText, getCurrentDate, "Failed : month didn't match");
		testSteps.add("Verified bydefult current month is selected in calendar");
		reslogger.info("Verified bydefult current month is selected in calendar");

		testSteps.add("<b>===== "
				+ "Verifying correct next month is displaying after clicking next icon in calendar".toUpperCase()
				+ " ====</b>");
		reslogger.info("===== "
				+ "Verifying correct next month is displaying after clicking next icon in calendar".toUpperCase()
				+ " ====");

		Wait.WaitForElement(driver, OR_Reservation.calendarNextIcon);
		res.calendarNextIcon.click();
		Wait.wait2Second();
		testSteps.add("Click next icon");
		reslogger.info("Click next icon");

		String getNextDate = ESTTimeZone.getMonthFromCurrentMonth(getCurrentDate, "MMMM yyyy", 1);
		reslogger.info(getNextDate);
		headerText = res.calendarHeading.getText().trim();
		testSteps.add("Expected Month Year : " + getNextDate);
		reslogger.info("Expected Month Year : " + getNextDate);
		testSteps.add("Found : " + headerText);
		reslogger.info("Found : " + headerText);
		assertEquals(headerText, getNextDate, "Failed : month didn't match");
		testSteps.add("Verified correct month is displaying after clicking next icon in calendar");
		reslogger.info("Verified correct month is displaying after clicking next icon in calendar");

		testSteps.add("<b>===== "
				+ "Verifying correct month is displaying after clicking previous icon in calendar".toUpperCase()
				+ " ====</b>");
		reslogger.info("===== "
				+ "Verifying correct month is displaying after clicking previous icon in calendar".toUpperCase()
				+ " ====");

		Wait.WaitForElement(driver, OR_Reservation.calendarPreviousIcon);
		res.calendarPreviousIcon.click();
		Wait.wait2Second();
		testSteps.add("Click previous icon");
		reslogger.info("Click previous icon");

		Wait.WaitForElement(driver, OR_Reservation.calendarPreviousIcon);
		res.calendarPreviousIcon.click();
		Wait.wait2Second();
		testSteps.add("Click previous icon");
		reslogger.info("Click previous icon");

		String getPreviousDate = ESTTimeZone.getMonthFromCurrentMonth(getCurrentDate, "MMMM yyyy", -1);
		reslogger.info(getPreviousDate);
		headerText = res.calendarHeading.getText().trim();
		testSteps.add("Expected Month Year : " + getPreviousDate);
		reslogger.info("Expected Month Year : " + getPreviousDate);
		testSteps.add("Found : " + headerText);
		reslogger.info("Found : " + headerText);

		assertEquals(headerText, getPreviousDate, "Failed : month didn't match");
		testSteps.add("Verified correct month is displaying after clicking previous icon in calendar");
		reslogger.info("Verified correct month is displaying after clicking previous icon in calendar");

		return testSteps;
	}

	public ArrayList<String> verifyNoResultsFound(WebDriver driver, boolean isDipslay) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		if (isDipslay) {
			assertTrue(res.NoRecordFound.isDisplayed(), "Failed : No Record Found is not displaying");
			reslogger.info(
					"Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying");
			testSteps.add(
					"Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying");

		} else {

			assertTrue(!res.NoRecordFound.isDisplayed(), "Failed : No Record Found is displaying");
			reslogger.info(
					"Verified that error message 'No records meet your criteria. Please change your criteria and search again.' is not displaying");
			testSteps.add(
					"Verified that error message 'No records meet your criteria. Please change your criteria and search again.' is not displaying");

		}

		return testSteps;

	}

	public ArrayList<String> verifyNoResultsFound(WebDriver driver, String columnName, boolean isDipslay)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		if (isDipslay) {
			assertTrue(res.NoRecordFound.isDisplayed(),
					"Failed : No Record Found is not displaying for following fields'" + columnName
							+ "' combination in advance search");
			reslogger.info(
					"Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying for following fields '"
							+ columnName + "' combination in advance search");
			testSteps.add(
					"Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying for following fields '"
							+ columnName + "' combination in advance search");

		} else {

			assertTrue(!res.NoRecordFound.isDisplayed(), "Failed : No Record Found is displaying for following fields '"
					+ columnName + "' combination in advance search");
			reslogger.info(
					"Verified that error message 'No records meet your criteria. Please change your criteria and search again.' is not displaying for following fields '"
							+ columnName + "' combination in advance search");
			testSteps.add(
					"Verified that error message 'No records meet your criteria. Please change your criteria and search again.' is not displaying for following fields '"
							+ columnName + "' combination in advance search");

		}

		return testSteps;

	}
	
	public ArrayList<String> AuthorizeButtonClickInTakePayment(WebDriver driver, ArrayList<String> test_steps, String amount
			,boolean isSetMainPaymentMethod, boolean isClose) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		String setAsMainPaymentMethod = "//span[text()='Set As Main Payment Method']/../input";
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		if (isSetMainPaymentMethod) {
			if (!driver.findElement(By.xpath(setAsMainPaymentMethod)).isSelected()) {
				jse.executeScript("arguments[0].click(true);", driver.findElement(By.xpath(setAsMainPaymentMethod)));
				test_steps.add("Select set As Main Payment Method");
				reslogger.info("Select set As Main Payment Method");
			} else {
				test_steps.add("Already Selected set As Main Payment Method");
				reslogger.info("Already Selected set As Main Payment Method");
			}
		}
		
	
	
		driver.findElement(By.id("payFormAmountV2")).clear();
		driver.findElement(By.id("payFormAmountV2")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(By.id("payFormAmountV2")).sendKeys(amount);
		
		String dropdownxpath = "(//h4[text()='Take Payment']//..//..//button[contains(@class,'btn dropdown-toggle btn-default')])[1]";
		Wait.WaitForElement(driver, dropdownxpath);
		driver.findElement(By.xpath(dropdownxpath)).click();
		
		String Type = "//span[text()='Authorization Only']";
		Wait.WaitForElement(driver, Type);
		driver.findElement(By.xpath(Type)).click();
		
		
		// String button = "//h4[text()='Take Payment']/../..//button[contains(text(),'"
		// + amount + "')]";
		String button = "//h4[text()='Take Payment']/../..//a[contains(text(),'Authorize')]";
		Wait.WaitForElement(driver, button);
		System.out.print("Amount:" + amount);
		WebElement payButtonElement = driver.findElement(By.xpath(button));
		Wait.waitForElementToBeClickable(By.xpath(button), driver);
		Utility.ScrollToElement(payButtonElement, driver);
		System.out.print("Text is :" + payButtonElement.getText());
		payButtonElement.click();
		test_steps.add("Click on Pay");
		reslogger.info("Click on Pay");
		String paymentSuccessfulTab = "//h4[contains(text(),'Successful')]";
		Wait.WaitForElement(driver, paymentSuccessfulTab);
		WebElement paymentSuccessfulTabElement = driver.findElement(By.xpath(paymentSuccessfulTab));
		System.out.print("Tab is:" + paymentSuccessfulTabElement.isDisplayed());
		assertEquals(paymentSuccessfulTabElement.isDisplayed(), true);
		test_steps.add("Payment Successul Tab Displayed");
		reslogger.info("Payment Successul Tab Displayed");

		if (isClose) {
			String close = "//h4[contains(text(),'Successful')]/../..//button[text()='Close']";
			Wait.WaitForElement(driver, close);
			driver.findElement(By.xpath(close)).click();
			test_steps.add("Click on Close");
			reslogger.info("Click on Close");
		}
		return test_steps;
	}

	public ArrayList<String> CaptureButtonClickInTakePayment(WebDriver driver, ArrayList<String> test_steps, String amount
			,boolean isSetMainPaymentMethod, boolean isClose) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		String setAsMainPaymentMethod = "//span[text()='Set As Main Payment Method']/../input";
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		if (isSetMainPaymentMethod) {
			if (!driver.findElement(By.xpath(setAsMainPaymentMethod)).isSelected()) {
				jse.executeScript("arguments[0].click(true);", driver.findElement(By.xpath(setAsMainPaymentMethod)));
				test_steps.add("Select set As Main Payment Method");
				reslogger.info("Select set As Main Payment Method");
			} else {
				test_steps.add("Already Selected set As Main Payment Method");
				reslogger.info("Already Selected set As Main Payment Method");
			}
		}
		
	
	
		driver.findElement(By.id("payFormAmountV2")).clear();
		driver.findElement(By.id("payFormAmountV2")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(By.id("payFormAmountV2")).sendKeys(amount);
		
		String dropdownxpath = "(//h4[text()='Take Payment']//..//..//button[contains(@class,'btn dropdown-toggle btn-default')])[1]";
		Wait.WaitForElement(driver, dropdownxpath);
		driver.findElement(By.xpath(dropdownxpath)).click();
		
		String Type = "//span[text()='Capture']";
		Wait.WaitForElement(driver, Type);
		driver.findElement(By.xpath(Type)).click();
		
		
		// String button = "//h4[text()='Take Payment']/../..//button[contains(text(),'"
		// + amount + "')]";
		String button = "(//h4[text()='Take Payment']/../..//a[contains(text(),'Pay')])[3]";
		Wait.WaitForElement(driver, button);
		System.out.print("Amount:" + amount);
		WebElement payButtonElement = driver.findElement(By.xpath(button));
		Wait.waitForElementToBeClickable(By.xpath(button), driver);
		Utility.ScrollToElement(payButtonElement, driver);
		System.out.print("Text is :" + payButtonElement.getText());
		payButtonElement.click();
		test_steps.add("Click on Pay");
		reslogger.info("Click on Pay");
		String paymentSuccessfulTab = "//h4[contains(text(),'Successful')]";
		Wait.WaitForElement(driver, paymentSuccessfulTab);
		WebElement paymentSuccessfulTabElement = driver.findElement(By.xpath(paymentSuccessfulTab));
		System.out.print("Tab is:" + paymentSuccessfulTabElement.isDisplayed());
		assertEquals(paymentSuccessfulTabElement.isDisplayed(), true);
		test_steps.add("Payment Successul Tab Displayed");
		reslogger.info("Payment Successul Tab Displayed");

		if (isClose) {
			String close = "//h4[contains(text(),'Successful')]/../..//button[text()='Close']";
			Wait.WaitForElement(driver, close);
			driver.findElement(By.xpath(close)).click();
			test_steps.add("Click on Close");
			reslogger.info("Click on Close");
		}
		return test_steps;
	}

	public void RefundButton(WebDriver driver, ArrayList<String> test_steps,boolean isClose) throws InterruptedException {
		String button = "//h4[text()='Take Payment']/../..//a[contains(text(),'Refund')]";
		Wait.WaitForElement(driver, button);
		
		WebElement payButtonElement = driver.findElement(By.xpath(button));
		Wait.waitForElementToBeClickable(By.xpath(button), driver);
		Utility.ScrollToElement(payButtonElement, driver);
		System.out.print("Text is :" + payButtonElement.getText());
		payButtonElement.click();
		test_steps.add("Click on Pay");
		reslogger.info("Click on Pay");
		
		if (isClose) {
			String close = "//h4[contains(text(),'Successful')]/../..//button[text()='Close']";
			Wait.WaitForElement(driver, close);
			driver.findElement(By.xpath(close)).click();
			test_steps.add("Click on Close");
			reslogger.info("Click on Close");
		}
	}

	public ArrayList<String> verifyPrintIcon(WebDriver driver) throws InterruptedException, IOException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Reservation_SearchPage reservationSearch = new Elements_Reservation_SearchPage(driver);
		testSteps.add("===== " + "Verifying print icon is displaying".toUpperCase() + " ====");
		reslogger.info("===== " + "Verifying print icon is displaying".toUpperCase() + " ====");

		Wait.WaitForElement(driver, OR.PRINT_ICON);
		Wait.waitForElementToBeVisibile(By.xpath(OR.PRINT_ICON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.PRINT_ICON), driver);
		assertTrue(reservationSearch.printIcon.isDisplayed(), "Failed : Print icon isn't display");
		testSteps.add("Verified print icon is displaying");
		reslogger.info("Verified print icon is displaying");

		return testSteps;

	}

	public ArrayList<String> clickOnProcessButtonInBulkNoShowPopUp(WebDriver driver) throws Exception {
		ArrayList<String> test_steps = new ArrayList<>();
		Elements_CPReservation cpResElm = new Elements_CPReservation(driver);
		Wait.explicit_wait_visibilityof_webelement_120(cpResElm.NoShowProcess, driver);
		cpResElm.NoShowProcess.click();
		reslogger.info("Process Button is Pressed Successfully");
		test_steps.add("Process Button is Pressed Successfully");

		return test_steps;
	}

	public ArrayList<String> verifyGoTopIcon(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_CPReservation cpResElm = new Elements_CPReservation(driver);
		testSteps.add(
				"===== VERIFYING THAT ON CLICKING GO-TOP ICON (Present at bottom right corner of home page) page will scroll back to top =====");
		reslogger.info(
				"===== VERIFYING THAT ON CLICKING GO-TOP ICON (Present at bottom right corner of home page) page will scroll back to top =====");

		Wait.WaitForElement(driver, OR_Reservation.goTopIcon);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.goTopIcon), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.goTopIcon), driver);
		Utility.ScrollToElement(cpResElm.goTopIcon, driver);
		Wait.wait10Second();
		try {
			cpResElm.goTopIcon.click();

		} catch (Exception e) {
			e.printStackTrace();
			Utility.clickThroughJavaScript(driver, cpResElm.goTopIcon2);
		}
		reslogger.info("Go top icon Pressed Successfully");
		testSteps.add("Go top icon Pressed Successfully");

		Wait.WaitForElement(driver, OR_Reservation.innroadLogo);
		assertTrue(cpResElm.innroadLogo.isDisplayed(), "Failed : innroad is not displaying");
		testSteps.add("Verified that page scrolled back to top");
		reslogger.info("Verified that page scrolled back to top");

		return testSteps;
	}

	public String getRatePlan(WebDriver driver, ArrayList<String> testSteps, int index) throws InterruptedException {
		String ratePath = "(//span[@data-bind='text: rateName'])[" + index + "]";
		Wait.WaitForElement(driver, ratePath);
		Wait.waitForElementToBeVisibile(By.xpath(ratePath), driver);
		Wait.waitForElementToBeClickable(By.xpath(ratePath), driver);
		WebElement ratePlan = driver.findElement(By.xpath(ratePath));
		Utility.ScrollToElement(ratePlan, driver);
		String getText = ratePlan.getText().trim();
		reslogger.info("RatePlan for reservation creation : " + getText);
		testSteps.add("RatePlan for reservation creation : " + getText);

		return getText;

	}

	public String getRoomClass(WebDriver driver, ArrayList<String> testSteps, String ratePlan, int index)
			throws InterruptedException {
		String ratePath = "(//span[@data-bind='text: rateName' and translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ ratePlan
				+ "']//ancestor::section[@class='ir-roomClassDetails manual-override']//child::span[contains(@data-bind, 'roomClass.name')])["
				+ index + "]";
		// Wait.WaitForElement(driver, ratePath);
		// Wait.waitForElementToBeVisibile(By.xpath(ratePath), driver);
		// Wait.waitForElementToBeClickable(By.xpath(ratePath), driver);
		WebElement roomClass = driver.findElement(By.xpath(ratePath));
		Utility.ScrollToElement(roomClass, driver);
		String getText = roomClass.getText().trim();
		reslogger.info("RoomClass for reservation creation : " + getText);
		testSteps.add("RoomClass for reservation creation : " + getText);
		return getText;

	}

	public String getReportsProperty(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		String getText = "";
		try {
			Wait.WaitForElement(driver, OR_Reservation.selectedProperty);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.selectedProperty), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.selectedProperty), driver);
			Utility.ScrollToElement(res.selectedProperty, driver);
			getText = res.selectedProperty.getText().trim();

		} catch (Exception e) {
			Wait.WaitForElement(driver, OR_Reservation.selectedProperty1);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.selectedProperty1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.selectedProperty1), driver);
			Utility.ScrollToElement(res.selectedProperty1, driver);
			getText = res.selectedProperty1.getText().trim();
		}
		reslogger.info("Selected property name : " + getText);
		testSteps.add("Selected property name : " + getText);
		return getText;

	}

	public String getClientName(WebDriver driver, ArrayList<String> testSteps) {
		Elements_Admin admin = new Elements_Admin(driver);
		Wait.WaitForElement(driver, OR_Admin.CLICK_CLIENT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Admin.CLICK_CLIENT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Admin.CLICK_CLIENT), driver);
		String getText = admin.clickClient.getText().trim();
		reslogger.info("Client name : " + getText);
		testSteps.add("Client name : " + getText);
		return getText;
	}

	public ArrayList<String> verifyPropertyColumn(WebDriver driver, String propertyName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String columnPath = "(//span[contains(text(),'Property')]//..//../..)[1]//following::tr//span[contains(@data-bind, 'PropertyName')]";
		Wait.waitforPageLoad(30, driver);
		Wait.WaitForElement(driver, columnPath);
		Wait.waitForElementToBeVisibile(By.xpath(columnPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(columnPath), driver);
		List<WebElement> column = driver.findElements(By.xpath(columnPath));
		ArrayList<String> getColumnData = new ArrayList<>();
		if (column.size() > 0) {
			for (int i = 0; i < column.size(); i++) {
				String getText = column.get(i).getText().trim();
				System.out.println(getText);
				getColumnData.add(getText);
				assertEquals(getText, propertyName,
						"Failed : Reservation list is didn't match selected property name : " + propertyName);
			}

			reslogger.info(getColumnData.toString());
			testSteps.add("Property Column List Items : " + getColumnData.toString());
			reslogger.info("Property Column List Items : " + getColumnData.toString());

			testSteps.add("Verified that Reservation list property matches with selected property");
			reslogger.info("Verified that Reservation list property matches with selected property");
		} else {
			assertTrue(false, "Failed : no records found");
		}

		return testSteps;

	}

	public String getRoomClassAbbreviation(WebDriver driver, ArrayList<String> testSteps, String roomClass) {
		String path = "//a[text()='" + roomClass + "']//..//following-sibling::td[1]";
		WebElement element = driver.findElement(By.xpath(path));
		String getString = element.getText().trim();
		testSteps.add("Room class (" + roomClass + ") abbreviation : " + getString);
		reslogger.info("Room class (" + roomClass + ") abbreviation : " + getString);

		return getString;
	}
	public void folioPayButton(WebDriver driver) {
		String payButton ="//button[text()='Pay']";
		Wait.WaitForElement(driver, payButton);
		Wait.waitForElementToBeClickable(By.xpath(payButton), driver);
		driver.findElement(By.xpath(payButton)).click();
	}
	public String getNameONCard(WebDriver driver) {
		String Path ="//h4[contains (text(),'Take Payment')]//..//..//input[contains(@data-bind,'NameOnCard')]";
		Wait.WaitForElement(driver, Path);
		Wait.waitForElementToBeClickable(By.xpath(Path), driver);
		String NameOnCard = driver.findElement(By.xpath(Path)).getAttribute("value");
	
		return NameOnCard;
	}
	public String getAmountTakePayment(WebDriver driver) {
		String amount = driver.findElement(By.id("payFormAmountV2")).getAttribute("value");
		return amount;
	}
	


	public void cancelPaymentInFolio(WebDriver driver, ArrayList<String> testSteps, String paymentMethod, String notes,
			String authorizationType) throws InterruptedException {

		Elements_MovieFolio moveFolio = new Elements_MovieFolio(driver);
		Elements_CPReservation res = new Elements_CPReservation(driver);
		// String statusPath = "//span[contains(@data-bind, '$data.Category') and
		// text()='"+ paymentMethod
		// +"']//..//preceding-sibling:://td/i[contains(@class,'inncenter-icons-collection')]";
		String statusPath = "(//span[contains(@data-bind, '$data.Category') and text()='" + paymentMethod
				+ "']//..)[1]//preceding-sibling::td//i";
		Wait.WaitForElement(driver, statusPath);
		Wait.waitForElementToBeVisibile(By.xpath(statusPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(statusPath), driver);
		WebElement status = driver.findElement(By.xpath(statusPath));
		Utility.ScrollToElement(status, driver);
		String getText = status.getAttribute("class");
		reslogger.info("Payment Line item status : " + getText);
		testSteps.add("Payment Line item status : " + getText);

		if (getText.contains("transactionstatus-icon8")) {
			String checkLineItemPath = "//span[contains(@data-bind, '$data.Category') and text()='" + paymentMethod
					+ "']//..//preceding-sibling::td[contains(@class,'lineitems-select')]";
			Wait.WaitForElement(driver, checkLineItemPath);
			Wait.waitForElementToBeVisibile(By.xpath(checkLineItemPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(checkLineItemPath), driver);
			WebElement checkLineItem = driver.findElement(By.xpath(checkLineItemPath));
			Utility.ScrollToElement(checkLineItem, driver);
			reslogger.info("Checked Payment Line item '" + paymentMethod + "'");
			testSteps.add("Checked Payment Line item '" + paymentMethod + "'");

			String descriptionPath = "(//span[text()='" + paymentMethod
					+ "']//..//following-sibling::td[contains(@class,'lineitem-description')]//a)[1]";
			Wait.WaitForElement(driver, descriptionPath);
			Wait.waitForElementToBeVisibile(By.xpath(descriptionPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(descriptionPath), driver);
			WebElement descriptionLineItem = driver.findElement(By.xpath(descriptionPath));
			Utility.ScrollToElement(descriptionLineItem, driver);
			descriptionLineItem.click();
			reslogger.info("Clicked Payment Line item '" + paymentMethod + "' descritpion");
			testSteps.add("Clicked Payment Line item '" + paymentMethod + "' descritpion");

			Wait.WaitForElement(driver, OR_Reservation.paymentDetailsNotes);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.paymentDetailsNotes), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.paymentDetailsNotes), driver);
			Utility.ScrollToElement(res.paymentDetailsNotes, driver);
			res.paymentDetailsNotes.clear();
			res.paymentDetailsNotes.sendKeys(notes);
			reslogger.info("Entered notes : " + notes);
			testSteps.add("Entered notes : " + notes);

			if (paymentMethod.equalsIgnoreCase("Cash")) {
				reslogger.info("In if");

				// Wait.WaitForElement(driver, OR_Reservation.voidPayment);
				// Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.voidPayment),
				// driver);
				// Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.voidPayment),
				// driver);
				Utility.ScrollToElement(res.voidPayment, driver);
				res.processPayment.click();
				reslogger.info("Clicked void button");
				testSteps.add("Clicked void button");

			} else if (paymentMethod.equalsIgnoreCase("MC")) {
				reslogger.info("In else");

				// Wait.WaitForElement(driver, OR_Reservation.authorizationType);
				// Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.authorizationType),
				// driver);
				// Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.authorizationType),
				// driver);
				// Utility.ScrollToElement(res.authorizationType, driver);
//				Select sl = new Select(res.authorizationType);
//				sl.selectByVisibleText(authorizationType);
//				reslogger.info("Selected authorization type : " + authorizationType);
//				testSteps.add("Selected authorization type : " + authorizationType);

				Wait.WaitForElement(driver, OR_Reservation.processPayment);
				Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.processPayment), driver);
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.processPayment), driver);
				// Utility.ScrollToElement(res.processPayment, driver);
				res.processPayment.click();
				reslogger.info("Clicked process button");
				testSteps.add("Clicked process button");
			}

			Wait.WaitForElement(driver, OR_Reservation.continuePayment);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.continuePayment), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.continuePayment), driver);
			Utility.ScrollToElement(res.continuePayment, driver);
			res.continuePayment.click();
			reslogger.info("Clicked continue button");
			testSteps.add("Clicked continue button");

			Wait.WaitForElement(driver, NewFolio.SaveFolio);
			Wait.waitForElementToBeVisibile(By.xpath(NewFolio.SaveFolio), driver);
			Wait.waitForElementToBeClickable(By.xpath(NewFolio.SaveFolio), driver);
			Utility.ScrollToElement(moveFolio.SaveFolio, driver);
			moveFolio.SaveFolio.click();
			testSteps.add("Clicked save folio button");
			reslogger.info("Clicked save folio button");
//
//
//			Wait.wait5Second();
//			Wait.WaitForElement(driver, NewFolio.Foliotab);
//			Wait.waitForElementToBeVisibile(By.xpath(NewFolio.Foliotab), driver);
//			Wait.waitForElementToBeClickable(By.xpath(NewFolio.Foliotab), driver);
//		
		}

	}

	public void verifyReservationExistInAdvanceSearch(WebDriver driver, ArrayList<String> testSteps,
			String reservationNumber) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		String path = "//span[contains(@data-bind,'text: ConfirmationNumber')]";
		boolean isResFound = false;
		List<WebElement> reservationElement = driver.findElements(By.xpath(path));
		ArrayList<String> reservationList = new ArrayList<>();
		Wait.WaitForElement(driver, OR_Reservation.paginationAvailable);
		testSteps.addAll(selectItemsPerPage(driver, "100"));
		for (int i = 0; i < reservationElement.size(); i++) {
			reservationList.add(reservationElement.get(i).toString().trim());
		}
		reslogger.info("reservationList :" + reservationList.toString());
		if (reservationList.contains(reservationNumber)) {
			isResFound = true;
			reslogger.info("Reservation with number '" + reservationNumber + "' found");
			testSteps.add("Reservation with number '" + reservationNumber + "' found");
		} else {

			reslogger.info(res.paginationAvailable.getAttribute("class"));
			if (res.paginationAvailable.getAttribute("class").contains("disabled")) {
				reslogger.info("Pagination not found. Total search reservation are already showing");
				testSteps.add("Pagination not found. Total search reservation are already showing");
			} else {
				Wait.WaitForElement(driver, OR_Reservation.paginationRightIcon);
				res.paginationRightIcon.click();
				reslogger.info("Click next icon");
				testSteps.add("Click next icon");

				verifyReservationExistInAdvanceSearch(driver, testSteps, reservationNumber);

			}
		}

		// assertTrue(isResFound, "Failed : Reservation not found");
	}

	public ArrayList<String> clickRestrictionsAndPolicyTab(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_Reservation.restrictionAndPolicy);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.restrictionAndPolicy), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.restrictionAndPolicy), driver);

		res.restrictionAndPolicy.click();
		testSteps.add("'Restrictions & Policies' button clicked");
		reslogger.info("'Restrictions & Policies' button clicked");

		return testSteps;

	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * ' Method Name: getGuestName ' Description: get guest name from table after
	 * search ' Input parameters: WebDriver,String,int ' Return value: String '
	 * Created By: Muhammad Bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public String getGuestNameAfterSearch(WebDriver driver, ArrayList<String> testSteps, String reservationNumber,
			boolean isClick) throws InterruptedException {

		String path = "(//span[contains(@data-bind,'text: ConfirmationNumber') and text()='" + reservationNumber
				+ "'])[1]//..//preceding-sibling::td//a[contains(@data-bind,'text: GuestName')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		String guestName = element.getAttribute("title");
		reslogger.info(guestName);
		if (isClick) {
			element.click();
			testSteps.add("Click on guest name with reservation number : " + reservationNumber);
			reslogger.info("Click on guest name with reservation number : " + reservationNumber);
		}
		return guestName;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * Method Name: getAccountNameAfterSearch Description: get account name from
	 * search table ' Input parameters: Webdriver,int ' Return value: String Created
	 * By: Muhammad Bakar Created On: 11-07-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */

	public String getAccountNameAfterSearch(WebDriver driver, String reservationNumber) throws InterruptedException {

		String path = "(//span[contains(@data-bind,'text: ConfirmationNumber') and text()='" + reservationNumber
				+ "'])[1]//..//preceding-sibling::td//span[contains(@data-bind,'text: AccountName')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		String accountName = element.getText().trim();
		return accountName;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * ' Method Name: getReservationNumberAfterSearch ' Description: get reservation
	 * number from search table ' Input parameters: Webdriver,int ' Return value:
	 * String ' Created By: Muhammad bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */

	public String getReservationNumberAfterSearch(WebDriver driver, String reservationNumber)
			throws InterruptedException {

		String path = "(//span[contains(@data-bind,'text: ConfirmationNumber') and text()='" + reservationNumber
				+ "'])[1]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		String resNumber = element.getText();
		return resNumber;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * ' Method Name: getReservationStatusAfterReservation ' Description: get
	 * reservation status from search table ' Input parameters: Webdriver,String '
	 * Return value: String ' Created By: Muhammad Bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */

	public String getReservationStatusAfterSearch(WebDriver driver, String reservationNumber) {
		String path = "(//span[contains(@data-bind,'text: ConfirmationNumber') and text()='" + reservationNumber
				+ "'])[1]//..//following-sibling::td//span[contains(@data-bind,'StatusName.replace')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		String status = element.getText();
		return status;

	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * ' Method Name: getArrivalDateAfterSearch ' Description: get arrival date from
	 * search table ' Input parameters: Webdriver,String reservationNumber ' Return
	 * value: String ' Created By: Muhammad bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public String getArrivalDateAfterSearch(WebDriver driver, String reservationNumber) throws InterruptedException {

		String path = "(//span[contains(@data-bind,'text: ConfirmationNumber') and text()='" + reservationNumber
				+ "'])[1]//..//following-sibling::td//span[contains(@data-bind,'text: DateStart')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		String arrivalDate = element.getText();
		return arrivalDate;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * ' Method Name: getDepartDateAfterSearch ' Description: get departure date
	 * from search table ' Input parameters: Webdriver,String ' Return value: String
	 * ' Created By: Muhammad bakar ' Created On: 11-18-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public String getDepartDateAfterSearch(WebDriver driver, String reservationNumber) throws InterruptedException {

		String path = "(//span[contains(@data-bind,'text: ConfirmationNumber') and text()='" + reservationNumber
				+ "'])[1]//..//following-sibling::td//span[contains(@data-bind,'text: DateEnd')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		String departDate = element.getText();
		return departDate;
	}

	public String getReportsProperty(ArrayList<String> testSteps, WebDriver driver) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		Wait.WaitForElement(driver, OR_Reservation.selectedPropertys);
		Utility.ScrollToElement(res.selectedPropertys, driver);
		String getText = res.selectedPropertys.getAttribute("title").trim();
		reslogger.info("Selected property name : " + getText);
		testSteps.add("Selected property name : " + getText);
		return getText;

	}

	

	public void verifyRulesPopupWhileSelectingRoom(WebDriver driver, ArrayList<String> testSteps, String RoomClass, String IsAssign, String Account,
			String ruleName, boolean isRuleBroken)
			throws InterruptedException {
		reslogger.info(RoomClass);
		
		Elements_CPReservation res = new Elements_CPReservation(driver);
		testSteps.add("===== Verifying rules popup =====");
		reslogger.info("Verifying rules popup");
		
		String room = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
				+ "')]/../../div[2]/span";
		reslogger.info(room);
		Wait.WaitForElement(driver, room);

		Utility.ScrollToElement(driver.findElement(By.xpath(room)), driver);
		String rooms = driver.findElement(By.xpath(room)).getText();
		
		reslogger.info(rooms);
		String[] roomsCount = rooms.split(" ");
		int count = Integer.parseInt(roomsCount[0].trim());
		if (count > 0) {
			String sel = "//div[@id='rcDtails']//span[contains(text(),'" + RoomClass
					+ "')]/../../../following-sibling::div/div/select";

			String rulessize = "(//div[@id='rcDtails']//span[contains(text(),'"
					+ RoomClass.trim() + "')]/following-sibling::span)[2]";
			reslogger.info(rulessize);
			int ruleCount = driver.findElements(By.xpath(rulessize)).size();
			if(isRuleBroken) {
				testSteps.add("===== Verify Rules colour is red. =====");
				String getClassValue = Utility.getELementText(driver.findElement(By.xpath(rulessize)), true, "class");
				assertTrue(getClassValue.contains("ir-color-red"), "Failed : Rule color is not red");
				testSteps.add("Verified Rules colour is red.");
			}else {
				if(ruleName.contains("No check-in") || ruleName.contains("No check-out")) {
					testSteps.add("===== Verify Rules name '"+ ruleName +"' is not showing. =====");
					assertTrue(driver.findElements(By.xpath(rulessize)).size() == 0, "Failed : Rule name '"+ ruleName +"' is not showing.");
					testSteps.add("Verified Rules name '"+ ruleName +"' is not showing.");
					
				}else {
					testSteps.add("===== Verify Rules colour is Green. =====");
					String getClassValue = Utility.getELementText(driver.findElement(By.xpath(rulessize)), true, "class");
					assertTrue(getClassValue.contains("ir-color-green"), "Failed : Rule color is not red");
					testSteps.add("Verified Rules colour is Green.");					
				}
			}
			testSteps.add("Selected room class : " + RoomClass);
			reslogger.info("Selected room class : " + RoomClass);
			
			if (IsAssign.equalsIgnoreCase("No")) {
				String expand = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String unAssign = "(//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass
						+ "')]/../../../following-sibling::div/div/select/following-sibling::div//ul//span[text()='Unassigned'])";
				Wait.WaitForElement(driver, unAssign);
				driver.findElement(By.xpath(unAssign)).click();
				testSteps.add("Selected room number : Unassigned");
				reslogger.info("Selected room number : Unassigned");

			} else {
				String roomnum = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/option";
				reslogger.info(roomnum);
				int count1 = driver.findElements(By.xpath(roomnum)).size();
				reslogger.info("count : " + count1);
				Random random = new Random();

				int randomNumber = random.nextInt(count1 - 1) + 1;
				reslogger.info("count : " + count1);
				reslogger.info("randomNumber : " + randomNumber);
				Wait.WaitForElement(driver, sel);

				String expand = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String roomNumber = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div//ul/li["
						+ randomNumber + "]/a/span";

				Wait.WaitForElement(driver, roomNumber);
				driver.findElement(By.xpath(roomNumber)).click();
			}

			String select = "//div[@id='rcDtails']//span[contains(text(),'"
					+ RoomClass + "')]/../../../following-sibling::div//span[contains(text(),'SELECT')]";
			Wait.WaitForElement(driver, select);
			driver.findElement(By.xpath(select)).click();

			String loading = "(//div[@class='ir-loader-in'])[2]";
			int counter = 0;
			/*
			 * while (true) { if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
			 * break; } else if (counter == 50) { break; } else { counter++;
			 * Wait.wait2Second(); } }
			 */
				reslogger.info("Waited to loading symbol");

				Wait.wait15Second();
				if(isRuleBroken) {
					testSteps.add("===== Verify that Rules popup with text 'Selecting this room will violate the following rules' is diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.rules)), "Failed : Rules popup with text 'Selecting this room will violate the following rules' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'Selecting this room will violate the following rules' is diplaying.");

					testSteps.add("===== Verify that Rules popup with text 'Are you sure you want to proceed?' is diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.areYouSureMsg)), "Failed : Rules popup with text 'Are you sure you want to proceed?' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'Are you sure you want to proceed?' is diplaying.");
					
					String getText = Utility.getElementText(res.ruleName);
					String[] ruleArr = ruleName.split(",");
					for(int b=0; b < ruleArr.length; b++) {
					testSteps.add("===== Verify that rules with name '"+ ruleArr[b] +"' is diplaying. =====");
					Assert.assertTrue(getText.contains(ruleArr[b]), "Failed : RuleName '"+ ruleArr[b] +"' is not showing");
					testSteps.add("Verified that rules with name '"+ ruleArr[b] +"' is diplaying.");
					}
					
					if (driver.findElements(By.xpath(OR_Reservation.rulesNoButton)).size() > 0) {
						Wait.wait2Second();
						res.rulesNoButton.click();
						testSteps.add(
								"Selecting this room will violate the following rules : Are you sure you want to proceed? : No");
						reslogger.info(
								"Selecting this room will violate the following rules : Are you sure you want to proceed? : No");

						loading = "(//div[@class='ir-loader-in'])[2]";
						counter = 0;
						while (true) {
							if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
								break;
							} else if (counter == 4) {
								break;
							} else {
								counter++;
								Wait.wait2Second();
							}
						}
					}

				}else {
					testSteps.add("===== Verify that Rules popup with text 'Selecting this room will violate the following rules' is not diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertFalse(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.rules)), "Failed : Rules popup with text 'Selecting this room will violate the following rules' is diplaying.");
					testSteps.add("Verified that Rules popup with text 'Selecting this room will violate the following rules' is not diplaying.");

				
				}
		
		} else {
			testSteps.add("Rooms Count <=0 for " + RoomClass + " for specified date");
			reslogger.info("Rooms Count <=0 for " + RoomClass + " for specified date");
		}
	}

	public void verifyRuleBrokenPopupAfterClickingOnAvailableSlot(WebDriver driver, String RoomClass, String ruleName, String ruleDescription,  boolean isRulePopupDisplay,ArrayList<String> testSteps) throws InterruptedException {

		Elements_TapeChart TapeChart = new Elements_TapeChart(driver);
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String CellPath = "//div[text()='" + RoomClass
				+ "'  and contains(@class,'roomclassname')]//parent::*//following-sibling::"
				+ "div//following-sibling::div[contains(@class,'DatesContainer')]//ancestor::"
				+ "div[contains(@class,'tapechartdatecell Available')]";
		System.out.println(CellPath);
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
				System.out.println("Scrol back 300");

			}
			
		} catch (Exception e) {
		}
		if(isRulePopupDisplay) {
			Wait.wait10Second();
			testSteps.add("===== Verify broken rule popup is showing. =====");
			assertTrue(TapeChart.ruleBrokenPopupTapeChart.isDisplayed(), "Failed : Rules broken popup is not showing");
			testSteps.add("Verified broken rule popup is showing");
			
			testSteps.add("===== Verify broken rule name '"+ ruleName +"' in popup. =====");
			Utility.verifyEquals(Utility.getElementText(TapeChart.ruleName), ruleName, testSteps);
			testSteps.add("Verified broken rule name '"+ ruleName +"' in popup.");

			testSteps.add("===== Verify broken rule description '"+ ruleDescription +"' in popup. =====");
			Utility.verifyEquals(Utility.getElementText(TapeChart.ruleDescription), ruleDescription, testSteps);
			testSteps.add("Verified broken rule description '"+ ruleDescription +"' in popup.");
			
			Wait.waitForElementByXpath(driver, NewTapeChart.ruleBrokenCancelIcon);
			Utility.ScrollToElement(TapeChart.ruleBrokenCancelIcon, driver);
			TapeChart.ruleBrokenCancelIcon.click();
			
		}else {
			testSteps.add("===== Verify broken rule popup is not showing. =====");
			assertFalse(TapeChart.Rules_Broken_popup.isDisplayed(), "Failed : Rules broken popup is showing");
			testSteps.add("Verified broken rule popup is not showing");
			
		}
		
	}
	
	
	public boolean isSplitResCheckboxDisplayed(WebDriver driver) throws InterruptedException {
		return Utility.isElementDisplayed_1(driver, By.xpath(OR_Reservation.CP_NewReservation_SplitReservation));
	}

	public void clickRemoveMultiRoomSearchIcon(WebDriver driver, ArrayList<String> testSteps, int b)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.removeMultiRoomSearchIcon);
		Utility.ScrollToElement(res.removeMultiRoomSearchIcon.get(b), driver);
		res.removeMultiRoomSearchIcon.get(b).click();
		testSteps.add("Clicked on red 'X' icon at index : " + b);
		reslogger.info("Clicked on red 'X' icon at index : " + b);

	}

	public int getNumberOfCheckInDatesInputs(WebDriver driver) {
		Wait.waitForElementByXpath(driver, OR_Reservation.EnterCheckinDate);
		List<WebElement> getCheckIn = driver.findElements(By.xpath(OR_Reservation.EnterCheckinDate));

		return getCheckIn.size();
	}

	public boolean isChangeRateMessageDisplayed(WebDriver driver) throws InterruptedException {
		return Utility.isElementDisplayed_1(driver, By.xpath(OR_Reservation.changeRateMessage));
	}

	public int returnRandomNumber() {
		Random rand = new Random();
		// Generate random integers in range minNumber to maxNumber
		IntStream is = rand.ints(2, 10);
		int rand_num = is.findAny().getAsInt();
		return rand_num;
	}

	public int[] returnArrayWithRandomNumbers(int arraySize) {
		int[] randomArray = new int[arraySize];
		for (int b = 0; b < randomArray.length; b++) {
			randomArray[b] = returnRandomNumber();
		}
		return randomArray;
	}

	public String getUpdatedByText(WebDriver driver) throws InterruptedException {

		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.updateBy);
		Utility.ScrollToElement(res.updateBy, driver);
		String updateBy = Utility.getELementText(res.updateBy, true, "innerHTML");
		System.out.println("updateBy : " + updateBy);
		updateBy = updateBy.split(">")[1].trim();
		System.out.println("updateBy : " + updateBy);
		updateBy = updateBy.split("M")[0].trim();
		System.out.println("updateBy : " + updateBy);
		return updateBy + "M";
	}

	public String getNotesUpdatedOnText(WebDriver driver) throws InterruptedException {

		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.getNotesUpdatedOn);
		Utility.ScrollToElement(res.getNotesUpdatedOn, driver);
		String updateBy = res.getNotesUpdatedOn.getText().trim();
		System.out.println("Notes updateBy : " + updateBy);
		return updateBy;
	}

	public void clickFirstNotesToOpenSettings(WebDriver driver) throws InterruptedException {

		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.getNotesUpdatedOn);
		Utility.ScrollToElement(res.getNotesUpdatedOn, driver);
		res.getNotesUpdatedOn.click();
	}

	public void updateNotes(WebDriver driver, ArrayList<String> test_steps, String NoteType, String Subject,
			String Description) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		clickFirstNotesToOpenSettings(driver);
		test_steps.add("Click to open first note");
		reslogger.info("Click to open first note");
		Wait.wait5Second();
		String noteTypeArrow = "//h4[text()='Edit Notes']/../..//div/div//div//label[text()='Type']/..//button";
		Wait.WaitForElement(driver, noteTypeArrow);
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(noteTypeArrow)));

		} catch (Exception e) {
			driver.findElement(By.xpath(noteTypeArrow)).click();
		}

		String noteType = "//h4[text()='Edit Notes']/../..//div/div//div//label[text()='Type']/..//button/..//div//li/a/span[text()='"
				+ NoteType.trim() + "']";
		Wait.WaitForElement(driver, noteType);
		driver.findElement(By.xpath(noteType)).click();
		test_steps.add("Select Note Type : " + NoteType);
		reslogger.info("Select Note Type : " + NoteType);

		String subject = "//h4[text()='Edit Notes']/../..//div/div//div//label[text()='Subject']/..//input";
		Wait.WaitForElement(driver, subject);
		WebElement subjectEle = driver.findElement(By.xpath(subject));
		subjectEle.clear();
		subjectEle.sendKeys(Subject);
		test_steps.add("Enter subject : " + Subject);
		reslogger.info("Enter subject : " + Subject);

		String description = "//h4[text()='Edit Notes']/../..//div/div//div//label[text()='Description']/preceding-sibling::textarea";
		Wait.WaitForElement(driver, description);
		WebElement subjectDes = driver.findElement(By.xpath(description));
		subjectDes.clear();
		subjectDes.sendKeys(Description);
		test_steps.add("Enter Description : " + Description);
		reslogger.info("Enter Description : " + Description);

		String user = "//input[starts-with(@data-bind,'value: updatedBy')]";

		JavascriptExecutor js = (JavascriptExecutor) driver;
		user = (String) js.executeScript("return arguments[0].value", driver.findElement(By.xpath(user)));

		String add = "//h4[text()='Edit Notes']/../..//button[text()='Save']";
		Wait.WaitForElement(driver, add);
		driver.findElement(By.xpath(add)).click();
		test_steps.add("Click on Add");
		reslogger.info("Click on Add");

		String noteverify = "//td[text()='" + NoteType.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Sucessfully added Note : " + Subject);
		reslogger.info("Sucessfully added Note : " + Subject);

		test_steps.add("Verified Note Type : " + NoteType);
		reslogger.info("Verified Note Type : " + NoteType);

		noteverify = "//td[text()='" + Subject.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Verified Note Subject : " + Subject);
		reslogger.info("Verified Note Subject : " + Subject);

		noteverify = "//td[text()='" + Description.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Verified Note Description : " + Description);
		reslogger.info("Verified Note Description : " + Description);

		noteverify = "//td[text()='" + user.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Verified Note updated by : " + user);
		reslogger.info("Verified Note updated by : " + user);

	}

	public String getReservationRoomNumber(WebDriver driver, int b) {
		Wait.waitForElementByXpath(driver, OR_Reservation.roomNumber);
		List<WebElement> roomNumberList = driver.findElements(By.xpath(OR_Reservation.roomNumber));
		String getRoomNumber = roomNumberList.get(b).getText().trim();
		reslogger.info("getRoomNumber : " + getRoomNumber);

		return getRoomNumber;

	}

	public String getFolioRoomNumber(WebDriver driver, int b) {
		Wait.waitForElementByXpath(driver, OR_Reservation.getFolioRoomNumber);
		List<WebElement> roomNumberList = driver.findElements(By.xpath(OR_Reservation.getFolioRoomNumber));
		String getRoomNumber = roomNumberList.get(b).getText().trim();
		reslogger.info("getRoomNumber : " + getRoomNumber);

		return getRoomNumber;

	}

	public String getFolioDates(WebDriver driver, int b) {
		Wait.waitForElementByXpath(driver, OR_Reservation.getFolioDates);
		List<WebElement> roomNumberList = driver.findElements(By.xpath(OR_Reservation.getFolioDates));
		String getRoomNumber = roomNumberList.get(b).getText().trim();
		reslogger.info("getRoomNumber : " + getRoomNumber);

		return getRoomNumber;

	}

	public void Click_InsiderTipHyperLink(WebDriver driver, ArrayList<String> test_steps) {
		Wait.WaitForElement(driver, OR.Reservation_InsideTipHyperLink);
		Wait.waitForElementToBeClickable(By.xpath(OR.Reservation_InsideTipHyperLink), driver);
		String Text = driver.findElement(By.xpath(OR.Reservation_InsideTipHyperLink)).getText();
		driver.findElement(By.xpath(OR.Reservation_InsideTipHyperLink)).click();
		test_steps.add("Click On HyperLink : <b>" + Text);
	}

	public Boolean Verify_ReservationPage_InsiderTip(WebDriver driver, ArrayList<String> test_steps) {
		Boolean ispresent = false;
		try {

			Wait.WaitForElement(driver, OR.Reservation_InsideTipClose);
			Wait.waitForElementToBeVisibile(By.xpath(OR.Reservation_InsideTipClose), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.Reservation_InsideTipClose), driver);
			test_steps.add("Notification Icon Is Visible");
			reslogger.info("Notification Icon Is Visible");
			ispresent = true;

		} catch (Exception e) {
			test_steps.add("Notification Icon Is Not Visible");
			reslogger.info("Notification Icon Is Not Visible");
		}
		try {

			Wait.WaitForElement(driver, OR.Reservation_InsideTipDescription);
			Wait.waitForElementToBeVisibile(By.xpath(OR.Reservation_InsideTipDescription), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.Reservation_InsideTipDescription), driver);
			test_steps.add("Notification Description Is Visible");
			reslogger.info("Notification Description Is Visible");
			ispresent = true;

		} catch (Exception e) {
			test_steps.add("Notification Description Is Not Visible");
			reslogger.info("Notification Description Is Not Visible");
		}

		try {

			Wait.WaitForElement(driver, OR.Reservation_InsideTipClose);
			Wait.waitForElementToBeVisibile(By.xpath(OR.Reservation_InsideTipClose), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.Reservation_InsideTipClose), driver);
			test_steps.add("Notification Close Icon Is Visible");
			reslogger.info("Notification Close Icon Is Visible");
			ispresent = true;
		} catch (Exception e) {
			test_steps.add("Notification Close Icon Is Not Visible");
			reslogger.info("Notification Close Icon Is Not Visible");
		}
		return ispresent;
	}

	public void UploadDocument(WebDriver driver, ArrayList<String> test_steps, String filepath) {

		Wait.WaitForElement(driver, OR.DocumentUploadButton);
		Wait.waitForElementToBeClickable(By.xpath(OR.DocumentUploadButton), driver);
		driver.findElement(By.xpath(OR.DocumentUploadButton)).click();
		test_steps.add("Click On Upload Button");
		reslogger.info("Click On Upload Button");

		Wait.WaitForElement(driver, OR.DocumentInputFeild);
		driver.findElement(By.xpath(OR.DocumentInputFeild)).sendKeys(filepath);
		test_steps.add("File Selected");
		reslogger.info("File Selected");

		Wait.WaitForElement(driver, OR.DocumentProcessedUploadButton);
		Wait.waitForElementToBeClickable(By.xpath(OR.DocumentProcessedUploadButton), driver);
		driver.findElement(By.xpath(OR.DocumentProcessedUploadButton)).click();
		test_steps.add("Click On Upload Button To processed");
		reslogger.info("Click On Upload Button To processed");
		SuccessToast(driver, test_steps);
	}

	public void DeleteDocument(WebDriver driver, ArrayList<String> test_steps, String filename) throws InterruptedException {
		String checkboxxpath = "//td[contains(text(),'" + filename + "')]//..//td[1]//label";
		Wait.WaitForElement(driver, checkboxxpath);
		Wait.waitForElementToBeClickable(By.xpath(checkboxxpath), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(checkboxxpath)), driver);
		driver.findElement(By.xpath(checkboxxpath)).click();
		test_steps.add("Select Document ");
		reslogger.info("Select Document ");

		Wait.WaitForElement(driver, OR.DocumentDeleteButton);
		Wait.waitForElementToBeClickable(By.xpath(OR.DocumentDeleteButton), driver);
		driver.findElement(By.xpath(OR.DocumentDeleteButton)).click();
		test_steps.add("Delete Document ");
		reslogger.info("Delete Document ");

		Wait.WaitForElement(driver, OR.DocumentProcessedDeleteButton);
		Wait.waitForElementToBeClickable(By.xpath(OR.DocumentProcessedDeleteButton), driver);
		driver.findElement(By.xpath(OR.DocumentProcessedDeleteButton)).click();
		test_steps.add("Confirm Document ");
		reslogger.info("Confirm Document ");
		SuccessToast(driver, test_steps);

	}

	
	public void downloadDocumnet(WebDriver driver, ArrayList<String> test_steps, String fileName) throws InterruptedException {
		String checkboxxpath = "//td[contains(text(),'"+fileName+"')]/..//following-sibling::td/a/i[contains(@class,'download')]";
				Wait.WaitForElement(driver, checkboxxpath);
		Utility.ScrollToElement(driver.findElement(By.xpath(checkboxxpath)), driver);
		driver.findElement(By.xpath(checkboxxpath)).click();
		test_steps.add("Download Document ");
		reslogger.info("Download Document ");
		Utility.switchTab(driver, 1);
		test_steps.add("Download Document Successfully");
		reslogger.info("Download Document Successfully");
		Utility.switchTab(driver, 0);
		
	}
	public void SuccessToast(WebDriver driver, ArrayList<String> test_steps) {
		// Success Message
		Wait.WaitForElement(driver, OR.ToastHeading);
		String Message = driver.findElement(By.xpath(OR.ToastHeading)).getText();
		test_steps.add("Toast Message Display: " + Message);
		reslogger.info("Toast Message Display: " + Message);
	}

	public void assignNewRoomNumber(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException, ParseException {

		Elements_CPReservation res = new Elements_CPReservation(driver);
		CPReservationPage reservationPage = new CPReservationPage();
		Wait.wait2Second();
		reservationPage.ClickEditStayInfo(driver, test_steps);
		res.assignRoomNumber.click();
		test_steps.add("Click Assign Room Number");

		// Click on room DropDown
		String part = "//div[contains(@class,'ir-flex-grow ir-flex-grow-3 ir-flex-mb-grow-3')]";
		By roomLocator = By.xpath(part);
		List<WebElement> elementRoom = driver.findElements(roomLocator);
		Utility.printString("elementRoom.size() : " + elementRoom.size());
		for (int b = 0; b < elementRoom.size(); b++) {
			String roomNumber = elementRoom.get(b).getText().trim();
			reslogger.info("Room Number at " + b + " :: " + roomNumber);
			elementRoom.get(b).click();
			reslogger.info("Click on room dropdown : " + b);
			test_steps.add("Click on room dropdown");

			// Select different room number
			String selectRoom = "(//div[contains(@class,'ir-flex-grow ir-flex-grow-3 ir-flex-mb-grow-3')])[" + (b + 1)
					+ "]//div//div//ul//li//a";
			System.out.println(selectRoom);
			int roomNum = driver.findElements(By.xpath(selectRoom)).size();
			Utility.printString("roomNum size : " + roomNum);
			String getRoomNumber = "";
			for (int i = 0; i < roomNum; i++) {
				List<WebElement> selectRoomList = driver.findElements(By.xpath(selectRoom));
				getRoomNumber = selectRoomList.get(i).getText().trim();
				Utility.printString("getRoomNumber at " + i + " : " + getRoomNumber);
				Utility.printString(
						"(!(getRoomNumber.equals(\"roomNumber\")) : " + !(getRoomNumber.equals(roomNumber)));
				Utility.printString(
						"(!(getRoomNumber.equals(\"Unassigned\")) : " + !(getRoomNumber.equals("Unassigned")));

				if (!(getRoomNumber.equals(roomNumber)) && (!(getRoomNumber.equals("Unassigned")))) {
					try{
						Wait.wait5Second();
					Utility.ScrollToElement(selectRoomList.get(i), driver);
					selectRoomList.get(i).click();
					}catch (Exception e) {
						Utility.clickThroughJavaScript(driver, elementRoom.get(b));
						Wait.wait5Second();
						Utility.ScrollToElement(selectRoomList.get(i), driver);
						selectRoomList.get(i).click();
					}
					Utility.printString("selectRoomList.get(i) clicked at : " + i);
					
					break;
				}

			}

		}

		// Click Save Button
		reslogger.info("Clicking Save Button");
		Wait.waitForElementByXpath(driver, OR_Reservation.CP_SaveRoomNo);
		Utility.ScrollToElement(res.CP_SaveRoomNo, driver);
		res.CP_SaveRoomNo.click();
		test_steps.add("Click on Save Room Number Button");

	}

	public String getReservationStatusFromHeader(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.HeaderStatus_AfterReservation);
		return Utility.getElementText(res.HeaderStatus_AfterReservation);
	}

	public void verifyRulesPopupWhenAvailabilityRulesDisabled(WebDriver driver, ArrayList<String> testSteps, String RoomClass, String IsAssign, String Account,
			String ruleName, boolean isRuleBroken)
			throws InterruptedException {
		reslogger.info(RoomClass);
		
		Elements_CPReservation res = new Elements_CPReservation(driver);
		testSteps.add("===== Verifying rules popup =====");
		reslogger.info("Verifying rules popup");
		
		String room = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
				+ "')]/../../div[2]/span";
		reslogger.info(room);
		Wait.WaitForElement(driver, room);

		Utility.ScrollToElement(driver.findElement(By.xpath(room)), driver);
		String rooms = driver.findElement(By.xpath(room)).getText();
		
		reslogger.info(rooms);
		String[] roomsCount = rooms.split(" ");
		int count = Integer.parseInt(roomsCount[0].trim());
		if (count > 0) {
			String sel = "//div[@id='rcDtails']//span[contains(text(),'" + RoomClass
					+ "')]/../../../following-sibling::div/div/select";

			String rulessize = "(//div[@id='rcDtails']//span[contains(text(),'"
					+ RoomClass.trim() + "')]/following-sibling::span)[2]";
			reslogger.info(rulessize);
			int ruleCount = driver.findElements(By.xpath(rulessize)).size();
			if(isRuleBroken) {
				testSteps.add("===== Verify Rules colour is red. =====");
				String getClassValue = Utility.getELementText(driver.findElement(By.xpath(rulessize)), true, "class");
				assertTrue(getClassValue.contains("ir-color-red"), "Failed : Rule color is not red");
				testSteps.add("Verified Rules colour is red.");
			}else {				
					testSteps.add("===== Verify Rules colour is Green. =====");
					String getClassValue = Utility.getELementText(driver.findElement(By.xpath(rulessize)), true, "class");
					assertTrue(getClassValue.contains("ir-color-green"), "Failed : Rule color is not red");
					testSteps.add("Verified Rules colour is Green.");					
			}
			testSteps.add("Selected room class : " + RoomClass);
			reslogger.info("Selected room class : " + RoomClass);
			
			if (IsAssign.equalsIgnoreCase("No")) {
				String expand = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String unAssign = "(//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass
						+ "')]/../../../following-sibling::div/div/select/following-sibling::div//ul//span[text()='Unassigned'])";
				Wait.WaitForElement(driver, unAssign);
				driver.findElement(By.xpath(unAssign)).click();
				testSteps.add("Selected room number : Unassigned");
				reslogger.info("Selected room number : Unassigned");

			} else {
				String roomnum = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/option";
				reslogger.info(roomnum);
				int count1 = driver.findElements(By.xpath(roomnum)).size();
				reslogger.info("count : " + count1);
				Random random = new Random();

				int randomNumber = random.nextInt(count1 - 1) + 1;
				reslogger.info("count : " + count1);
				reslogger.info("randomNumber : " + randomNumber);
				Wait.WaitForElement(driver, sel);

				String expand = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String roomNumber = "//div[@id='rcDtails']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div//ul/li["
						+ randomNumber + "]/a/span";

				Wait.WaitForElement(driver, roomNumber);
				driver.findElement(By.xpath(roomNumber)).click();
			}

			String select = "//div[@id='rcDtails']//span[contains(text(),'"
					+ RoomClass + "')]/../../../following-sibling::div//span[contains(text(),'SELECT')]";
			Wait.WaitForElement(driver, select);
			driver.findElement(By.xpath(select)).click();

			String loading = "(//div[@class='ir-loader-in'])[2]";
			int counter = 0;
			/*
			 * while (true) { if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
			 * break; } else if (counter == 50) { break; } else { counter++;
			 * Wait.wait2Second(); } }
			 */
				reslogger.info("Waited to loading symbol");

				Wait.wait5Second();
				if(isRuleBroken) {
					testSteps.add("===== Verify that Rules popup with text 'You cannot proceed because this selection violates the following rules' is diplaying. =====");
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.getCannotProceedText)), "Failed : Rules popup with text 'You cannot proceed because this selection violates the following rules' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'You cannot proceed because this selection violates the following rules' is diplaying.");

					testSteps.add("===== Verify that Rules popup with text 'Please alter your room selection to proceed' is diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.getAlterRoomText)), "Failed : Rules popup with text 'Please alter your room selection to proceed' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'Please alter your room selection to proceed' is diplaying.");
					
					testSteps.add("===== Verify that rules with name '"+ ruleName.split(",")[0] +"' is diplaying. =====");
					String getText = Utility.getElementText(res.getRuleName);
					Assert.assertTrue(getText.contains(ruleName.split(",")[0]), "Failed : Rule name '"+ ruleName.split(",")[0] +"' is showing.");
					testSteps.add("Verified that rules with name '"+ ruleName.split(",")[0] +"' is diplaying.");

					testSteps.add("===== Verify that rules with name '"+ ruleName.split(",")[1] +"' is diplaying. =====");
					Assert.assertTrue(getText.contains(ruleName.split(",")[1]), "Failed : Rule name '"+ ruleName.split(",")[1] +"' is showing.");
					testSteps.add("Verified that rules with name '"+ ruleName.split(",")[1] +"' is diplaying.");
					
					testSteps.add("===== Verify that rules with name '"+ ruleName.split(",")[2] +"' is diplaying. =====");
					Assert.assertTrue(getText.contains(ruleName.split(",")[2]), "Failed : Rule name '"+ ruleName.split(",")[2] +"' is showing.");
					testSteps.add("Verified that rules with name '"+ ruleName.split(",")[2] +"' is diplaying.");
					
					Wait.wait2Second();
					res.OkButton.click();
					testSteps.add(
							"You cannot proceed because this selection violates the following rules : Please alter your room selection to proceed : Ok");
					reslogger.info(
							"You cannot proceed because this selection violates the following rules : Please alter your room selection to proceed : Ok");

					loading = "(//div[@class='ir-loader-in'])[2]";
					counter = 0;
					while (true) {
						if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
							break;
						} else if (counter == 4) {
							break;
						} else {
							counter++;
							Wait.wait2Second();
						}
					}

				}else {
					testSteps.add("===== Verify that Rules popup with text 'Selecting this room will violate the following rules' is not diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertFalse(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.getCannotProceedText)), "Failed : Rules popup with text 'You cannot proceed because this selection violates the following rules' is diplaying.");
					testSteps.add("Verified that Rules popup with text 'You cannot proceed because this selection violates the following rules' is not diplaying.");
				
				}
		
		} else {
			testSteps.add("Rooms Count <=0 for " + RoomClass + " for specified date");
			reslogger.info("Rooms Count <=0 for " + RoomClass + " for specified date");
		}
	}

	public void VerifyNextButtonIsNotDisplaying(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		testSteps.add("Verifying that On Clicking Ok User return to Sleect RoomClas Page without sleecting any roomclass");
		Utility.verifyEquals(String.valueOf(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.CP_NewReservation_Next))), "false", testSteps);
	}
	
	public void verifyRulesWhileRoomClassUpdation(WebDriver driver, ArrayList<String> testSteps, String RoomClass, String IsAssign,
			String Account, String ruleName, boolean isRuleBroken, boolean isRulePermissionsEnable) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		
		reslogger.info(RoomClass);
		String room = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
				+ "')]/../../div[2]/span";
		reslogger.info(room);
		Wait.WaitForElement(driver, room);

		String rooms = driver.findElement(By.xpath(room)).getText();
		reslogger.info(rooms);
		String[] roomsCount = rooms.split(" ");
		int count = Integer.parseInt(roomsCount[0].trim());
		if (count > 0) {
			String sel = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
					+ "')]/../../../following-sibling::div/div/select";

			String rulessize = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
					+ RoomClass + "')]/following-sibling::span[contains(@data-bind, 'text: rules')]";
			reslogger.info(rulessize);
			int ruleCount = driver.findElements(By.xpath(rulessize)).size();
			if(isRulePermissionsEnable) {
				if(isRuleBroken) {
						testSteps.add("===== Verify Rules colour is red. =====");
						String getClassValue = Utility.getELementText(driver.findElements(By.xpath(rulessize)).get(ruleCount - 1), true, "class");
						assertTrue(getClassValue.contains("ir-color-red"), "Failed : Rule color is not red");
						testSteps.add("Verified Rules colour is red.");						
				}else {
					if(ruleName.contains("No check-in") || ruleName.contains("No check-out")) {
						testSteps.add("===== Verify Rules name '"+ ruleName +"' is not showing. =====");
						assertTrue(driver.findElements(By.xpath(rulessize)).size() == 0, "Failed : Rule name '"+ ruleName +"' is not showing.");
						testSteps.add("Verified Rules name '"+ ruleName +"' is not showing.");
						
					}else {
						testSteps.add("===== Verify Rules colour is Green. =====");
						String getClassValue = Utility.getELementText(driver.findElement(By.xpath(rulessize)), true, "class");
						assertTrue(getClassValue.contains("ir-color-green"), "Failed : Rule color is not red");
						testSteps.add("Verified Rules colour is Green.");					
					}
				}
				
			}

			WebElement ele = driver.findElement(By.xpath(sel));
			testSteps.add("Selected room class : " + RoomClass);
			reslogger.info("Selected room class : " + RoomClass);
			if (IsAssign.equalsIgnoreCase("No")) {
				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String unAssign = "((//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')])[2]/../../../following-sibling::div/div/select/following-sibling::div//ul//span[text()='Unassigned'])";
				Wait.WaitForElement(driver, unAssign);
				driver.findElement(By.xpath(unAssign)).click();
				testSteps.add("Selected room number : Unassigned");
				reslogger.info("Selected room number : Unassigned");
			} else {
				String roomnum = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/option";
				reslogger.info(roomnum);
				int count1 = driver.findElements(By.xpath(roomnum)).size();
				Random random = new Random();
				int randomNumber = random.nextInt(count1 - 1) + 1;
				reslogger.info("count : " + count1);
				reslogger.info("randomNumber : " + randomNumber);
				Wait.WaitForElement(driver, sel);

				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String roomNumber = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div//ul/li["
						+ randomNumber + "]/a/span";
				Wait.WaitForElement(driver, roomNumber);
				driver.findElement(By.xpath(roomNumber)).click();
			}

			try {

				String select = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div//span[contains(text(),'SELECT')]";
				Wait.WaitForElement(driver, select);
				driver.findElement(By.xpath(select)).click();
				reslogger.info("Click selected in try");

			} catch (Exception e) {
				String select = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div//span[contains(@data-bind,'SELECT')]";
				Wait.WaitForElement(driver, select);
				driver.findElement(By.xpath(select)).click();
				reslogger.info("Click selected in catch");

			}

			String loading = "(//div[@class='ir-loader-in'])[2]";
			int counter = 0;
			while (true) {
				if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
					break;
				} else if (counter == 50) {
					break;
				} else {
					counter++;
					Wait.wait2Second();
				}
			}
			reslogger.info("Waited to loading symbol");

			reslogger.info("Rule Count : " + ruleCount);
			if(isRulePermissionsEnable) {
				Wait.wait5Second();
				if(isRuleBroken) {
					testSteps.add("===== Verify that Rules popup with text 'Selecting this room will violate the following rules' is diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.rules)), "Failed : Rules popup with text 'Selecting this room will violate the following rules' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'Selecting this room will violate the following rules' is diplaying.");
	
					testSteps.add("===== Verify that Rules popup with text 'Are you sure you want to proceed?' is diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.areYouSureMsg)), "Failed : Rules popup with text 'Are you sure you want to proceed?' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'Are you sure you want to proceed?' is diplaying.");
					
					String getText = Utility.getElementText(res.ruleName);
					String[] ruleArr = ruleName.split(",");
					for(int b=0; b < ruleArr.length; b++) {
					testSteps.add("===== Verify that rules with name '"+ ruleArr[b] +"' is diplaying. =====");
					Assert.assertTrue(getText.contains(ruleArr[b]), "Failed : RuleName '"+ ruleArr[b] +"' is not showing");
					testSteps.add("Verified that rules with name '"+ ruleArr[b] +"' is diplaying.");
					}
					
					if (driver.findElements(By.xpath(OR_Reservation.rulesNoButton)).size() > 0) {
						Wait.wait2Second();
						res.rulesNoButton.click();
						testSteps.add(
								"Selecting this room will violate the following rules : Are you sure you want to proceed? : No");
						reslogger.info(
								"Selecting this room will violate the following rules : Are you sure you want to proceed? : No");
	
						loading = "(//div[@class='ir-loader-in'])[2]";
						counter = 0;
						while (true) {
							if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
								break;
							} else if (counter == 4) {
								break;
							} else {
								counter++;
								Wait.wait2Second();
							}
						}
					}
	
				}else {
					testSteps.add("===== Verify that Rules popup with text 'Selecting this room will violate the following rules' is not diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertFalse(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.rules)), "Failed : Rules popup with text 'Selecting this room will violate the following rules' is diplaying.");
					testSteps.add("Verified that Rules popup with text 'Selecting this room will violate the following rules' is not diplaying.");
	
				
				}
			}else {
				if(isRuleBroken) {
					Wait.wait5Second();
					testSteps.add("===== Verify that Rules popup with text 'You cannot proceed because this selection violates the following rules' is diplaying. =====");
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.getCannotProceedText)), "Failed : Rules popup with text 'You cannot proceed because this selection violates the following rules' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'You cannot proceed because this selection violates the following rules' is diplaying.");

					testSteps.add("===== Verify that Rules popup with text 'Please alter your room selection to proceed' is diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertTrue(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.getAlterRoomText)), "Failed : Rules popup with text 'Please alter your room selection to proceed' is not diplaying");
					testSteps.add("Verified that Rules popup with text 'Please alter your room selection to proceed' is diplaying.");
					
					testSteps.add("===== Verify that rules with name '"+ ruleName.split(",")[0] +"' is diplaying. =====");
					String getText = Utility.getElementText(res.getRuleName);
					Assert.assertTrue(getText.contains(ruleName.split(",")[0]), "Failed : Rule name '"+ ruleName.split(",")[0] +"' is showing.");
					testSteps.add("Verified that rules with name '"+ ruleName.split(",")[0] +"' is diplaying.");

					testSteps.add("===== Verify that rules with name '"+ ruleName.split(",")[1] +"' is diplaying. =====");
					Assert.assertTrue(getText.contains(ruleName.split(",")[1]), "Failed : Rule name '"+ ruleName.split(",")[1] +"' is showing.");
					testSteps.add("Verified that rules with name '"+ ruleName.split(",")[1] +"' is diplaying.");
					
					testSteps.add("===== Verify that rules with name '"+ ruleName.split(",")[2] +"' is diplaying. =====");
					Assert.assertTrue(getText.contains(ruleName.split(",")[2]), "Failed : Rule name '"+ ruleName.split(",")[2] +"' is showing.");
					testSteps.add("Verified that rules with name '"+ ruleName.split(",")[2] +"' is diplaying.");
					
					Wait.wait2Second();
					res.OkButton.click();
					testSteps.add(
							"You cannot proceed because this selection violates the following rules : Please alter your room selection to proceed : Ok");
					reslogger.info(
							"You cannot proceed because this selection violates the following rules : Please alter your room selection to proceed : Ok");

					loading = "(//div[@class='ir-loader-in'])[2]";
					counter = 0;
					while (true) {
						if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
							break;
						} else if (counter == 4) {
							break;
						} else {
							counter++;
							Wait.wait2Second();
						}
					}

				}else {
					Wait.wait5Second();
					testSteps.add("===== Verify that Rules popup with text 'Selecting this room will violate the following rules' is not diplaying. =====");
					reslogger.info("Rule Count : " + ruleCount);
					assertFalse(Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.getCannotProceedText)), "Failed : Rules popup with text 'You cannot proceed because this selection violates the following rules' is diplaying.");
					testSteps.add("Verified that Rules popup with text 'You cannot proceed because this selection violates the following rules' is not diplaying.");
				
				}
			}
			try {
				String policy = "//p[contains(text(),'Based on the changes made')]/../..//button[text()='Yes']";
				Wait.WaitForElement(driver, policy);
				driver.findElement(By.xpath(policy)).click();
				testSteps.add("Based on the changes made, new policies are applicable.? : yes");
				reslogger.info("Based on the changes made, new policies are applicable.? : yes");

				loading = "(//div[@class='ir-loader-in'])[2]";
				counter = 0;
				while (true) {
					if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
						break;
					} else if (counter == 3) {
						break;
					} else {
						counter++;
						Wait.wait2Second();
					}
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}

		} else {
			testSteps.add("Rooms Count <=0 for " + RoomClass + " for specified date");
			reslogger.info("Rooms Count <=0 for " + RoomClass + " for specified date");
		}
	}


	public void clickCloseStayInfo(WebDriver driver, ArrayList<String> testSteps) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR.closeStayInfo);
		res.closeStayInfo.click();
	}

	public void verifySetNowButtonIsShowingINPaymentSection(WebDriver driver, ArrayList<String> testSteps) {
		String path = "//a[text()='SET NOW']";
		testSteps.add("Verify 'SET NOW' button is showing when there is not payment method");
		boolean isTrue = Utility.isElementDisplayed(driver, By.xpath(path));
		assertTrue(isTrue, "Failed : 'SET NOW' button is not showing");
		testSteps.add("Verified 'SET NOW' button is showing");		
	}
	
	public void verifyExpiryDate(WebDriver driver, ArrayList<String> testSteps, String expDate) {
		String path = "//span[contains(@data-bind, 'creditCard.expiresOn')]";
		testSteps.add("Verify expiry date '"+ expDate +"' in payment method section");
		String getText = Utility.getElementText(driver.findElement(By.xpath(path)));
		Utility.verifyEquals(expDate, getText, testSteps);
		testSteps.add("Verified expiry date '"+ expDate +"' in payment method section");				
	}

	public void verifyExpiryDateInHistoryAfterPayment(WebDriver driver, ArrayList<String> testSteps, String expDate) {
		String path = "//span[contains(@data-bind, 'text:$data.description') and contains(text(), 'Captured payment for')]";
		testSteps.add("Verify expiry date '"+ expDate +"' is showing in guest history");
		String getText = Utility.getElementText(driver.findElement(By.xpath(path)));
		assertTrue(getText.contains(expDate), "Failed : date '"+ expDate +"'  is not showing in guest history");
		Utility.verifyEquals(expDate, getText, testSteps);
		testSteps.add("Verified expiry date '"+ expDate +"' in Guest History section");				
	}
	
	public ArrayList<String> getpaymentMethodsList(WebDriver driver) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
		res.CP_NewReservation_PaymentMethod.click();
		int size  = res.getpaymentMethodsList.size();
		for(int i=0; i < size; i++) {
			Utility.ScrollToElement(res.getpaymentMethodsList.get(i), driver);
			test_steps.add(Utility.getElementText(res.getpaymentMethodsList.get(i)));
		}
		
		return test_steps;
	}

	public void clickTakePaymentButton(WebDriver driver, ArrayList<String> testSteps) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		try {
		Wait.waitForElementByXpath(driver, OR_Reservation.takePaymentButton);
		res.takePaymentButton.click();
		testSteps.add("Click 'Take Payment' button");
		}catch (Exception e) {
			Wait.waitForElementByXpath(driver, OR_Reservation.takePaymentButton2);
			res.takePaymentButton2.click();
			testSteps.add("Click 'Take Payment' button");				
		}
	}
	
	public String getReservationNumber(WebDriver driver, int index) throws InterruptedException{
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.reservationNumberInAdvanceSearch);
		Utility.ScrollToElement(res.reservationNumberInAdvanceSearch.get(index), driver);
		String[] Val = Utility.getElementText(res.reservationNumberInAdvanceSearch.get(index)).split(" ");
		return Val[0]; 
	}
	
	public String getStatus(WebDriver driver) {
		String statuxpath = "//span[@data-bind='text:reservationViewStatuses.titleLineStatus']" ;
		Wait.WaitForElement(driver, statuxpath);
		Wait.waitForElementToBeVisibile(By.xpath(statuxpath), driver);
		String Text = driver.findElement(By.xpath(statuxpath)).getText();
		return Text;
	}
	
	public void Reserved_Reservation(WebDriver driver , ArrayList<String> testSteps) {
		String DropDownxpath = "//span[@class='dropdown dropdown-toggle ir-dropdown-span-status']";
		Wait.WaitForElement(driver, DropDownxpath);
		Wait.waitForElementToBeClickable(By.xpath(DropDownxpath), driver);
		driver.findElement(By.xpath(DropDownxpath)).click();
		testSteps.add("Click On DropDown");
		
		String Cancelxpath = "(//span[text()='Reserved'])[2]";
		Wait.WaitForElement(driver, Cancelxpath);
		driver.findElement(By.xpath(Cancelxpath)).click();
		testSteps.add("Select Reserved Option");
		
				
		
	}

	
	
	public void Cancel_ReservationWithNoVoidRoomCharge(WebDriver driver , ArrayList<String> testSteps) {
		String DropDownxpath = "//span[@class='dropdown dropdown-toggle ir-dropdown-span-status']";
		Wait.WaitForElement(driver, DropDownxpath);
		Wait.waitForElementToBeClickable(By.xpath(DropDownxpath), driver);
		driver.findElement(By.xpath(DropDownxpath)).click();
		testSteps.add("Click On DropDown");
		
		String Cancelxpath = "(//span[text()='Cancel'])[2]";
		Wait.WaitForElement(driver, Cancelxpath);
		driver.findElement(By.xpath(Cancelxpath)).click();
		testSteps.add("Select Cancel Option");
		
		// popUp
		
		String CancelPopupHeadxpath = "//h4[contains(text() , 'Cancel Reservation')]";
		Wait.WaitForElement(driver, CancelPopupHeadxpath);
		String heading = driver.findElement(By.xpath(CancelPopupHeadxpath)).getText();
		testSteps.add("Cancellation Pop Verified: "+heading);
		
		
		
		String PopUpTextArea = "//textarea[@class = 'form-control ir-frm-ctrl ir-scrollFix']";
		Wait.WaitForElement(driver, PopUpTextArea);
		testSteps.add("cancellation PopUp Appears");
		driver.findElement(By.xpath(PopUpTextArea)).sendKeys("Cancellation Reason");
		testSteps.add("Entering Text In Cancellation Text Area");
		
//		String VoidRoom = "//input[@data-bind='checked: modal.VoidCharges']";
//		Wait.WaitForElement(driver, VoidRoom);
//		Boolean ischeck = driver.findElement(By.xpath(VoidRoom)).isSelected();
//		if(ischeck = true) {
//			driver.findElement(By.xpath("(//h4[text()='Cancel Reservation']//..//..//span[@class='ir-check-box'])[1]")).click();
//		}
		
		String CancellationProcessedButtonxPath = "//button[contains(text() , 'PROCEED TO CANCELLATION PAYMENT')]";
		Wait.WaitForElement(driver, CancellationProcessedButtonxPath);
		Wait.waitForElementToBeClickable(By.xpath(CancellationProcessedButtonxPath), driver);
		driver.findElement(By.xpath(CancellationProcessedButtonxPath)).click();
		testSteps.add("Click On Processed Cancellation Button");
		
		
		//Refund popup
		
		String refundPopHeadingxpath = "//h4[contains(text(),'Cancellation Payment')]";
		Wait.WaitForElement(driver, refundPopHeadingxpath);
		String H = driver.findElement(By.xpath(refundPopHeadingxpath)).getText();
		testSteps.add("Refund popUp Heading is Verified: "+H);
		
		
		String refundPopBalancexpath = "(//span[@data-bind = 'showInnroadCurrency: { value: PaymentFolioBalance(), ShouldShowNegativeAsBracket: true }'])[3]";
		Wait.WaitForElement(driver, refundPopBalancexpath);
		String refundBalance = driver.findElement(By.xpath(refundPopBalancexpath)).getText();
		testSteps.add("Refunded Amount Is Verified: "+refundBalance);
		
		
		
		String RefundButtonxpath = "(//a[contains(text(),'Pay')])[3]";
		Wait.WaitForElement(driver, RefundButtonxpath);
		Wait.waitForElementToBeClickable(By.xpath(RefundButtonxpath), driver);
		driver.findElement(By.xpath(RefundButtonxpath)).click();
		testSteps.add("Clicking On Refund Button");
		
		
		String CancellationPopupheadingxpath = "//h4[contains(text() , 'Cancellation Successful')]";
		Wait.WaitForElement(driver, CancellationPopupheadingxpath);
		String POPHeading = driver.findElement(By.xpath(CancellationPopupheadingxpath)).getText();
		Assert.assertEquals(POPHeading, "Cancellation Successful","Failed: Pop Up Heading Mismatched");
		testSteps.add("Successful Popup is Verified: "+POPHeading);
		
		
		String SuccessfullPopCloseButtonxpath = "//button[@data-bind = 'click: cancel.closeModal']";
		Wait.WaitForElement(driver, SuccessfullPopCloseButtonxpath);
		Wait.waitForElementToBeClickable(By.xpath(SuccessfullPopCloseButtonxpath), driver);
		driver.findElement(By.xpath(SuccessfullPopCloseButtonxpath)).click();
		testSteps.add("Clicking On Cancellation Successfull pop Up Close Button");
		
		
	}

	public String departedReservation(WebDriver driver, String Status, String settleRoomCharges, Boolean voidRoomCharges,
			ArrayList<String> testSteps) throws InterruptedException {
		Elements_Reservation elementReservation = new Elements_Reservation(driver);
		String amount = "0";
		String guestName = "";
		String stayInfoguestNames = "//div[contains(text(),'Stay')]/..//span[@class='ir-multiroom-stayInfo-guestname ir-textTrim']";
		List<WebElement> stayInfoguestNameElement = driver.findElements(By.xpath(stayInfoguestNames));
		CPReservationPage res = new CPReservationPage();
		try {
			if (Status.equalsIgnoreCase("CheckOutAll")) {
				guestName = stayInfoguestNameElement.get(0).getText();
				elementReservation.CP_Reservation_CheckOutAllButton.click();
				testSteps.add("Clicked on CheckOut All Button");
			} else if (Status.equalsIgnoreCase("CheckOutPrimary")) {
				guestName = stayInfoguestNameElement.get(0).getText();
				elementReservation.CheckOutPrimaryGuest.click();
				testSteps.add("Clicked on CheckOut Primary Button");
			} else if (Status.equalsIgnoreCase("CheckOutSecondary")) {
				guestName = stayInfoguestNameElement.get(1).getText();
				elementReservation.CheckOutSecondaryGuest.click();
				testSteps.add("Clicked on CheckOut Additional Button");

			} else {
				elementReservation.CheckOut.click();
				testSteps.add("Clicked on CheckOut Button for Last Room For Single");
			}
		} catch (Exception e) {
			elementReservation.CheckOut.click();
			if (Status.equalsIgnoreCase("CheckOutPrimary")) {
				testSteps.add("Clicked on CheckOut Primary Button");
			} else {
				testSteps.add("Clicked on CheckOut Additional Button");
			}
		}

		Wait.wait2Second();
		String settleText="(//div[@class='modal-body'])[last()]";
		WebElement settleRoomChargesElement=driver.findElement(By.xpath(settleText));
		if(settleRoomChargesElement.isDisplayed()) {
			testSteps.add(settleRoomChargesElement.getText());
			if (!settleRoomCharges.equalsIgnoreCase("")) {
				try {
					Wait.wait3Second();
					String settleRoom = "(//button[text()='" + settleRoomCharges + "'])[last()]";
					driver.findElement(By.xpath(settleRoom)).click();
					testSteps.add("Clicked on 'Settle Room Charges' <b>" + settleRoomCharges + "</b> Button");
				} catch (Exception e) {

				}
			} 
		}
		else {
			testSteps.add("'Settle Room Charges' popup is not displayed");
		}
		
		if (voidRoomCharges) {
			Elements_CPReservation resElement = new Elements_CPReservation(driver);
			Wait.wait5Second();
			resElement.VoidRoomChargeLabel.click();
			testSteps.add("Check void room charges");

		}
		res.generatGuestReportToggle(driver, testSteps, "No");
		testSteps.add("Unchecked Guest Report Toggle");

		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(OR_Reservation.ProceedToCheckOutPayment)));
		testSteps.add("Clicked on Proceed To Payment Button");
		try {

			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.Log), driver, 20);
			WebElement element = driver.findElement(By.xpath(OR_Reservation.checkoutPaymentAmount));
			if (element.isDisplayed()) {
				testSteps.add("Get Amount <b>" + element.getText() + "</b>");
			} else {
				testSteps.add("Get Amount <b>" + amount + "</b>");
			}

			Utility.clickThroughJavaScript(driver, elementReservation.Log);
			testSteps.add("Clicked on Log Button");
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.CancelledClose), driver);
			elementReservation.CancelledClose.click();
			testSteps.add("Clicked on Close Button");
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath(
						"//button[contains(text(),'Pay') and contains(@data-bind,'click: PaymentProcessButtonClick')]"))
						.click();
				testSteps.add("Clicked on Pay Button");
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.CancelledClose), driver);

				elementReservation.CancelledClose.click();
				testSteps.add("Clciked on Close Button");
			} catch (Exception e1) {

			}
		}
	
		return amount;
	}
	
	public String getReservationAmount(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR.totalAmountOnStayInfoPage);		
		return Utility.getElementText(res.totalAmountOnStayInfoPage);
	}
	
	
	public String getRoomTotal(WebDriver driver, int index) {
		String path = "//span[contains(@data-bind, 'roomTotal , ShouldShowNegativeAsBracket')]";
		Wait.waitForElementByXpath(driver, path);
		List<WebElement> ele = driver.findElements(By.xpath(path));
		return Utility.getElementText(ele.get(index));
	}

	public void verifyPaymentType(WebDriver driver, ArrayList<String> testSteps, String paymentType) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		testSteps.add("==== verify payment method in reservation =====");
		Wait.waitForElementByXpath(driver, OR_Reservation.paymentType);	
		Utility.ScrollToElement(res.paymentType, driver);
		String text = Utility.getElementText(res.paymentType);
		Utility.verifyEquals(text, paymentType, testSteps);
	}
	
	public String getExpiryDate(WebDriver driver) throws InterruptedException{
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.expiryDate);	
		Utility.ScrollToElement(res.expiryDate, driver);
		return Utility.getElementText(res.expiryDate);
		
	}
	
	public boolean getIsInvalidCardNumberErrorMessageShowing(WebDriver driver) {
		String invalidPath = "//span[@data-bind='validationMessage: creditCardNumber' and contains(text(), 'Please enter a valid credit card number.')]";
		return Utility.isElementDisplayed(driver, By.xpath(invalidPath));
	}
	public boolean getIsCardNumberRequiredErrorMessageShowing(WebDriver driver) {
		String path = "//span[@data-bind='validationMessage: creditCardNumber' and contains(text(), 'This field is required.')]";
		return Utility.isElementDisplayed(driver, By.xpath(path));
		
	}	
	
	public boolean getIsInvalidCardExpiryErrorMessageShowing(WebDriver driver) {
		String path = "(//span[contains(text(), 'Invalid/expired date')])[last()]";
		return Utility.isElementDisplayed(driver, By.xpath(path));
		
	}	
	public boolean getIsNameOnCardRequiredErrorMessageShowing(WebDriver driver) {
		String path = "//span[@data-bind='validationMessage: nameOnCreditCard' and contains(text(), 'This field is required.')]";
		return Utility.isElementDisplayed(driver, By.xpath(path));
		
	}	
	public boolean getIsTaxExemptRequiredErrorMessageShowing(WebDriver driver) {
		String path = "//span[@data-bind='validationMessage: taxExemptId' and contains(text(), 'This field is required.')]";
		return Utility.isElementDisplayed(driver, By.xpath(path));
		
	}
	
	public void clearCardNumberField(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
		Utility.ScrollToElement(res.CP_NewReservation_CardNumber, driver);		
		res.CP_NewReservation_CardNumber.click();
		res.CP_NewReservation_CardNumber.clear();
		testSteps.add("Clear CardNumber");
		reslogger.info("Clear CardNumber");
	}
	public void clearNameOnCardField(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
		Utility.ScrollToElement(res.CP_NewReservation_CardNumber, driver);		
		res.CP_NewReservation_NameOnCard.click();
		res.CP_NewReservation_NameOnCard.clear();
		testSteps.add("Clear Name On Card");
		reslogger.info("Clear Name On Card");
	}
	
	public void clearExpDateField(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
		Utility.ScrollToElement(res.CP_NewReservation_CardNumber, driver);		
		res.CP_NewReservation_CardExpDate.click();
		res.CP_NewReservation_CardExpDate.clear();
		testSteps.add("Clear Card ExpDate");
		reslogger.info("Clear Card ExpDate");	
	}
	
	public boolean isEnterTaxExcemptIdEnabled(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.EnterTaxExcemptId);
		return res.enterTaxExcemptId.isEnabled();
	}
	
	public void clearTaxExemptId(WebDriver driver, ArrayList<String> test_steps) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.EnterTaxExcemptId);
		if (isEnterTaxExcemptIdEnabled(driver)) {
			res.enterTaxExcemptId.click();
			res.enterTaxExcemptId.clear();
			test_steps.add("Cleared taxExempt Id");
		}
		
	}

	public boolean verifyTaxExamptHeadingIsDisplaying(WebDriver driver) throws InterruptedException {
		return Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.longStayPopupHeading));
	}
	

	public void clickSetExemptAnywayButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.waitForElementByXpath(driver, OR_Reservation.setExemptAnywayButton);
		Utility.ScrollToElement(res.setExemptAnywayButton, driver);		
		res.setExemptAnywayButton.click();
		testSteps.add("Clicked 'SET EXEMPT ANYWAY' button");
		reslogger.info("Clicked 'SET EXEMPT ANYWAY' button");	
	}
	
	public boolean isCreditCradRequiredToasterShowing(WebDriver driver) {
		return Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.creditCardIsRequiredToaster));
	}
	

	public boolean isCreditcardIsRequiredForGuaranteedResErrorMessageShowing(WebDriver driver) {
		return Utility.isElementDisplayed(driver, By.xpath(OR_Reservation.creditcradisRequiredForGuaranteedRes));
	}
	
	
	public void changeReservationStatusInCreationPage(WebDriver driver, ArrayList<String> testSteps, String status) throws InterruptedException {
		String path = "//div[contains(@data-bind, 'ReservationUpdate')]";
		Wait.waitForElementByXpath(driver, path);
		WebElement ele = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(ele, driver);
		try {
		ele.click();
		}catch (Exception e) {
			Utility.clickThroughJavaScript(driver, ele);
		}
		String statusPath = "//ul[contains(@data-bind, 'ReservationUpdate')]//li[contains(text(), '"+ status+"')]";
		WebElement statusEle = driver.findElement(By.xpath(statusPath));
		statusEle.click();
		testSteps.add("Selected Status : " + status);
		
	}
	
	public void inHouseReservation(WebDriver driver, String status, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_Reservation elementReservation = new Elements_Reservation(driver);
		CPReservationPage res = new CPReservationPage();

		Wait.wait3Second();
		try {
			if (status.equalsIgnoreCase("CheckInALL")) {

				elementReservation.CP_Reservation_CheckInAllButton.click();
			} else if (status.equalsIgnoreCase("CheckInPrimary")) {
				elementReservation.checkInPrimaryGuest.click();
			} else if (status.equalsIgnoreCase("CheckInSecondary")) {
				elementReservation.checkInSecondaryGuest.click();
			} else if (status.equalsIgnoreCase("CheckIn")) {
				elementReservation.ReservationStatusCheckin.click();
			}
		} catch (Exception e) {
			elementReservation.ReservationStatusCheckin.click();
		}

		res.generatGuestReportToggle(driver, testSteps, "No");
		// Utility.clickThroughJavaScript(driver,
		// driver.findElement(By.xpath(OR_Reservation.ReservationStatusConfirmCheckin)));
		try {
			elementReservation.ProceedToCheckInPayment.click();
			try {
				String loading = "(//div[@class='ir-loader-in'])[1]";
				int count = 0;
				try {
					while (true) {
						if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
							break;
						} else if (count == 30) {
							break;
						} else {
							count++;
							Wait.wait1Second();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				reslogger.info("Waited to loading symbol");

				Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.roomStatusDirtyMessage), driver);
				reslogger.info("Room is Dirty");
				testSteps.add("Room is Dirty");
				List<WebElement> ele = driver.findElements(By.xpath("//button[text()='Confirm']"));
				reslogger.info("Confirm buttons: " + ele.size());
				Utility.clickThroughJavaScript(driver, ele.get(ele.size() - 1));
				// Utility.clickThroughAction(driver, ele.get(ele.size()-1));
				reslogger.info("Clicked on Confirm button");
				testSteps.add("Clicked on Confirm Button");
				try {
					String payButton = "//button[contains(text(),'Pay') and contains(@data-bind,'ready-to-process')]";
					Wait.waitForElementToBeClickable(By.xpath(payButton), driver);
					driver.findElement(By.xpath(payButton)).click();
					testSteps.add("Clicked on Pay Button");
					Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.checkOutButtonClose), driver);
					elementReservation.checkOutButtonClose.click();
					testSteps.add("Clciked on Close Button");
				} catch (Exception e) {
					// TODO: handle exception

					String autorize = "//button[contains(text(),'Authorize') and contains(@data-bind,'ready-to-process')]";
					Wait.waitForElementToBeClickable(By.xpath(autorize), driver);
					driver.findElement(By.xpath(autorize)).click();
					testSteps.add("Clicked on Authorize Button");
					Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.checkOutButtonClose), driver);
					elementReservation.checkOutButtonClose.click();
					testSteps.add("Clciked on Close Button");
				}
			} catch (Exception e) {
				try {

					String autorize = "//button[contains(text(),'Authorize') and contains(@data-bind,'ready-to-process')]";
					Wait.waitForElementToBeClickable(By.xpath(autorize), driver);
					driver.findElement(By.xpath(autorize)).click();
					testSteps.add("Clicked on Autorize Button");
					Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.checkOutButtonClose), driver);
					elementReservation.checkOutButtonClose.click();
					testSteps.add("Click on Close Button");
				} catch (Exception ef) {
					String payButton = "//button[contains(text(),'Pay') and contains(@data-bind,'ready-to-process')]";
					Wait.waitForElementToBeClickable(By.xpath(payButton), driver);
					driver.findElement(By.xpath(payButton)).click();
					testSteps.add("Clicked on Pay Button");
					Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.checkOutButtonClose), driver);
					elementReservation.checkOutButtonClose.click();
					testSteps.add("Clicked on Close Button");
				}
				reslogger.info("Room is not dirty");
				testSteps.add("Room is not dirty");
			}

		} catch (Exception e) {
			reslogger.info("There is no CheckIn policy");
			testSteps.add("There is no CheckIn policy");
			try {
				Utility.clickThroughAction(driver,
						driver.findElement(By.xpath(OR_Reservation.ReservationStatusConfirmCheckin)));
				try {
					String OkButton = "(//button[text()='OK'])[last()]";
					WebElement okElement = driver.findElement(By.xpath(OkButton));
					if (okElement.isDisplayed()) {
						okElement.click();
						String select = "//select[contains(@data-bind,'selectedRoomId')]";
						List<WebElement> selectElements = driver.findElements(By.xpath(select));
						for (int i = 0; i < selectElements.size(); i++) {
							String option = "(//select[contains(@data-bind,'selectedRoomId')])[1]//option";
							List<WebElement> optionElement = driver.findElements(By.xpath(option));
							int a = Utility.getRandomNumber(1, optionElement.size());
							Select selectOption = new Select(selectElements.get(0));
							selectOption.selectByIndex(a);

							Utility.clickThroughAction(driver,
									driver.findElement(By.xpath(OR_Reservation.ReservationStatusConfirmCheckin)));

						}
					}
				} catch (Exception eh) {

				}
				try {
					String loading = "(//div[@class='ir-loader-in'])[1]";
					int count = 0;
					try {
						while (true) {
							if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
								break;
							} else if (count == 60) {
								break;
							} else {
								count++;
								Wait.wait1Second();
							}
						}
					} catch (Exception ex) {
						// TODO: handle exception
					}

					reslogger.info("Waited to loading symbol");

					Wait.presenceOfElementLocated(driver, OR_Reservation.roomStatusDirtyMessage, 40);
					reslogger.info("Room is Dirty");
					testSteps.add("Room is Dirty");
					List<WebElement> ele = driver.findElements(By.xpath("//button[text()='Confirm']"));
					reslogger.info("Confirm buttons: " + ele.size());
					Utility.clickThroughJavaScript(driver, ele.get(ele.size() - 1));
					// Utility.clickThroughAction(driver, ele.get(ele.size()-1));
					reslogger.info("Clicked on Confirm button");
					testSteps.add("Clicked on Confirm Button");
//					 elementReservation.confirmCheckInAtDirtyMessage.click();
//					 Utility.clickThroughAction(driver,
//					 elementReservation.confirmCheckInAtDirtyMessage);
				} catch (Exception ex) {
					testSteps.add("Room is not dirty");
					reslogger.info("Room is not dirty");
				}
			} catch (Exception e1) {

			}
		}

		Wait.wait5Second();
//		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.ReservationCurrentStatus), driver);
//		String s6 = elementReservation.ReservationCurrentStatus.getText();
//		System.out.println("s6=" + s6);
//		if (s6.equalsIgnoreCase("In-House")) {
//			System.out.println("Successfully changed Reservation status to In-House");
//			testSteps.add("Successfully changed Reservation status to In-House");
//		} else {
//			System.out.println("Unable to change Reservation status to In-House");
//			testSteps.add("Unable to change Reservation status to In-House");
//		}
	}

	public void verifyTakePaymentType(WebDriver driver, ArrayList<String> test_steps, String TakePaymentType) {
		test_steps.add("Verify Take Payment PopUp Payment Type is " + TakePaymentType + " or not");
		try {
			String paymentType = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//div//button";
			Wait.wait2Second();
			WebElement paymentTypeElement = driver.findElement(By.xpath(paymentType));
			reslogger.info(paymentTypeElement.getText().trim());
			Wait.wait2Second();
			assertEquals(paymentTypeElement.getAttribute("title").trim(), TakePaymentType, "Value Doesn't Match");
			test_steps.add("Verified Take Payment PopUp Payment Type is <b> " + TakePaymentType + "</b>");

		} catch (Exception e) {
			test_steps.add("Verified Take Payment PopUp Payment Type is not<b> " + TakePaymentType + "</b>");

		}
	}

	public void verifyRefundPaymentPopupAtCheckout(WebDriver driver, ArrayList<String> test_steps,
			String settleRoomCharges, String Status) throws InterruptedException {

		test_steps.add("Verify  Refund Payment Popup is Displayed or not");
		Elements_CPReservation elementCPReservation = new Elements_CPReservation(driver);
		Elements_Reservation elementReservation = new Elements_Reservation(driver);
		CPReservationPage res = new CPReservationPage();

		try {
			if (Status.equalsIgnoreCase("CheckOutAll")) {
				elementReservation.CP_Reservation_CheckOutAllButton.click();
				test_steps.add("Clicked on CheckOut All Button");
			} else if (Status.equalsIgnoreCase("CheckOutPrimary")) {
				elementReservation.CheckOutPrimaryGuest.click();
				test_steps.add("Clicked on CheckOut Primary Button");
			} else if (Status.equalsIgnoreCase("CheckOutSecondary")) {
				elementReservation.CheckOutSecondaryGuest.click();
				test_steps.add("Clicked on CheckOut Additional Button");

			} else {
				elementReservation.CheckOut.click();
				test_steps.add("Clicked on CheckOut Button for Last Room For Single");
			}
		} catch (Exception e) {
			elementReservation.CheckOut.click();
			if (Status.equalsIgnoreCase("CheckOutPrimary")) {
				test_steps.add("Clicked on CheckOut Primary Button");
			} else {
				test_steps.add("Clicked on CheckOut Additional Button");
			}
		}

		Wait.wait2Second();
		String settleText = "(//div[@class='modal-body'])[last()]";
		WebElement settleRoomChargesElement = driver.findElement(By.xpath(settleText));
		if (settleRoomChargesElement.isDisplayed()) {
			test_steps.add(settleRoomChargesElement.getText());
			if (!settleRoomCharges.equalsIgnoreCase("")) {
				try {
					Wait.wait3Second();
					String settleRoom = "(//button[text()='" + settleRoomCharges + "'])[last()]";
					driver.findElement(By.xpath(settleRoom)).click();
					test_steps.add("Clicked on 'Settle Room Charges' <b>" + settleRoomCharges + "</b> Button");
				} catch (Exception e) {

				}
			}
		} else {
			test_steps.add("'Settle Room Charges ' popup is not displayed");
		}
		res.generatGuestReportToggle(driver, test_steps, "No");
		test_steps.add("Unchecked Guest Report Toggle");

		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(OR_Reservation.ProceedToCheckOutPayment)));
		test_steps.add("Clicked on Proceed To Payment Button");
		Wait.wait3Second();
		if (elementCPReservation.checkout_ProceedMessage.isDisplayed()) {
			test_steps.add(elementCPReservation.checkout_ProceedMessage.getText());
			elementCPReservation.checkoutTakemeToFolio.click();
			test_steps.add("Verified <b>Refund Payment Popup</b> is Displayed");

		} else {
			test_steps.add("Verify <b>Refund Payment Popup</b> is not Displayed");
		}

	}

	public String getErrorText(WebDriver driver) {
		String path = "//div[text()='All the below fields are required.']";
		Wait.waitForElementByXpath(driver, path);
		return Utility.getElementText(driver.findElement(By.xpath(path)));
	}
	
	public void getGuestProfile(WebDriver driver, ArrayList<String> testSteps, String guestName) {
		String path = "//a[text()='"+ guestName +"']";
		testSteps.add("Verify Guest profile is showing in search results");		
		//Assert.assertTrue(Utility.isElementDisplayed(driver, By.xpath(path)), "Failed : Guest profile '"+ guestName +"' is not shoiwng");
		testSteps.add("Verified Guest profile is showing in search results");	
		
		testSteps.add("Verify Guest profile is not duplicated in search results");		
		Assert.assertTrue(driver.findElements(By.xpath(path)).size() == 1, "Failed : Guest profile '"+ guestName +"' is not duplicated");
		testSteps.add("Verified Guest profile is duplicated in search results");	
		
	}
	
	public void updateCardNumber(WebDriver driver, ArrayList<String> testSteps, String cardNumber) throws InterruptedException {
		String clickEditIcon = "//a[contains(@data-bind, 'decrypt') and @class='ir-decrypt']//i";
		String cardInputPath = "(//input[@placeholder='Credit Card Number'])[last()]";
		String transactionDeclined = "(//span[text()='Transaction Declined. Please try again or enter a different payment method.'])[1]";
		String clickPay = "(//a[contains(text(), 'Pay')])[last()]";
	
		testSteps.add("Verifying 'Transaction Declined. Please try again or enter a different payment method.' is showing.");
//		Assert.assertTrue(Utility.isElementDisplayed(driver, By.xpath(transactionDeclined)), "Failed : Error message is not showing");
		testSteps.add("Verified 'Transaction Declined. Please try again or enter a different payment method.' is showing.");
		
		Wait.waitForElementByXpath(driver, clickEditIcon);
		WebElement ele = driver.findElement(By.xpath(clickEditIcon));
		ele.click();
		testSteps.add("Clicked on edit icon");
		Wait.wait3Second();
		Wait.waitForElementByXpath(driver, cardInputPath);
		WebElement ele2 = driver.findElement(By.xpath(cardInputPath));		
		if(Utility.isElementEnabled(driver, By.xpath(cardInputPath))) {
			ele2.clear();
			ele2.sendKeys(cardNumber);
			testSteps.add("Enter card number :  " + cardNumber);
			testSteps.add("In  if crad num");
			ele2.sendKeys(Keys.TAB);
		}else {
			ele.click();
			ele2.clear();
			ele2.sendKeys(cardNumber);
			testSteps.add("Enter card number :  " + cardNumber);			
			ele2.sendKeys(Keys.TAB);
			testSteps.add("In else crad num");
		}
		Wait.wait3Second();
		
		WebElement ele3 =  driver.findElement(By.xpath(clickPay));
		Utility.ScrollToElement(ele3, driver);
		ele3.click();
		testSteps.add("Clicked on pay button");
		
		String closebutton = "//h4[text()='Reservation Confirmation']/../..//button[text()='Close']";
		Wait.waitForElementByXpath(driver, closebutton);
		driver.findElement(By.xpath(closebutton)).click();
		testSteps.add("Clicked on close button");
		
	}
	
	public void verifyRoomMoveTaskIsCreated(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException{
		testSteps.add("Verify Room Move Task is created for split reservation");
		String path = "//div[@class='ir-task-type']//span[text()='Room Move']";
		try {
		Assert.assertTrue(Utility.isElementDisplayed_1(driver, By.xpath(path)), "Failed : Room move task is not created");
		}catch (Exception e) {
			testSteps.add(e.toString());
		}

		testSteps.add("Verified Room Move Task is created for split reservation");
		
	}
	public ArrayList<String> clickAutoSuggestedGuest(WebDriver driver, String firstName) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Utility.ScrollToElement(res.enterFindGuestProfile, driver);
		res.enterFindGuestProfile.clear();
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.EnterFindGuestProfile), driver);
		res.enterFindGuestProfile.sendKeys(firstName);
		String path = "//ul[@class='knockout-autocomplete menu scrollable popup-container']/li/div/div/span/b[contains(text(),'"+ firstName +"')]";
		try {
			List<WebElement> resultList = driver.findElements(By.xpath(path));
			reslogger.info("resultList : " + resultList.size());
			Wait.wait2Second();
			if (resultList.get(0).isDisplayed()) {
				Wait.wait2Second();
				resultList.get(0).click();
				try {
					Wait.waitUntilPresenceOfElementLocated(OR_Reservation.GuestHistoryConfirmationPopup, driver);
					if (res.guestHistoryConfirmationPopup.isDisplayed()) {
						res.guestHistoryConfirmationPopup.click();
					}
				} catch (Exception e) {

				}
			}else {
				Wait.wait2Second();
				res.enterFindGuestProfile.clear();
				Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.EnterFindGuestProfile), driver);
				res.enterFindGuestProfile.sendKeys(firstName);
				resultList.clear();
				resultList = driver.findElements(By.xpath(path));
				Wait.wait2Second();
				reslogger.info("resultList : " + resultList.size());
				resultList.get(0).click();
				try {
					Wait.waitUntilPresenceOfElementLocated(OR_Reservation.GuestHistoryConfirmationPopup, driver);
					if (res.guestHistoryConfirmationPopup.isDisplayed()) {
						res.guestHistoryConfirmationPopup.click();
					}
				} catch (Exception e) {

				}
				
			}
			
		} catch (Exception e) {
			testSteps.add("No result found");
		}
		
		return testSteps;
	}

	public void VerifyAddLineItemWithDate(WebDriver driver, ArrayList<String> testSteps, String LineItemCategory,
			String LineItemCategoryAmount, String ItemAddedDate) throws InterruptedException {
		testSteps.add("******************* Add Line Item to folio**********************");
		reslogger.info("******************* Add Line Item to folio**********************");

		String[] item = LineItemCategory.split("\\|");
		String[] amt = LineItemCategoryAmount.split("\\|");
		for (int i = 0; i < item.length; i++) {

			Elements_CPReservation ReservationPage = new Elements_CPReservation(driver);
//			Wait.explicit_wait_visibilityof_webelement_150(ReservationPage.Add_LineItem, driver);
			Utility.ScrollToElement(ReservationPage.Add_LineItem, driver);
			ReservationPage.Add_LineItem.click();
			testSteps.add("Click Add");
			reslogger.info("Click Add");
			Wait.wait2Second();
			Utility.ScrollToElement(ReservationPage.Select_LineItem_Category, driver);
			Wait.wait2Second();
			Wait.explicit_wait_visibilityof_webelement(ReservationPage.Select_LineItem_Category, driver);
			new Select(ReservationPage.Select_LineItem_Category).selectByVisibleText(item[i]);
			testSteps.add("Adding line item to folio : " + item[i]);
			reslogger.info("Adding line item to folio : " + item[i]);
			
			if(Utility.validateString(ItemAddedDate)) {
				ReservationPage.enterlineItemDate.clear();
				ReservationPage.enterlineItemDate.sendKeys(ItemAddedDate);
				ReservationPage.enterlineItemDate.sendKeys(Keys.TAB);
				testSteps.add("Adding date : " + ItemAddedDate);
				reslogger.info("Adding date : " + ItemAddedDate);
			}

			testSteps.add("Line Item Quantity : 1");
			reslogger.info("Line Item Quantity : 1");

			ReservationPage.Enter_LineItem_Amount.sendKeys(amt[i]);
			testSteps.add("Add Line Item Amount : " + amt[i]);
			reslogger.info("Add Line Item Amount : " + amt[i]);

			Wait.wait2Second();
			ReservationPage.Commit_LineItem.click();
			testSteps.add("Click Commit");
			reslogger.info("Click Commit");
			Wait.wait2Second();
//			ReservationPage.reset_Button.click();
//			testSteps.add("Click Reset Button");
//			reslogger.info("Click Reset Button");
		}
		
		
	}
	
	
	
	public void click_and_verify_PostLineItem(WebDriver driver, ArrayList<String> testSteps, String LineItemCategory) throws InterruptedException {
		testSteps.add("******************* Verify Post Line Item to folio**********************");
		reslogger.info("******************* Verify Post Line Item to folio**********************");

		String input = "(//td[@class='lineitem-category']/span[text()='" + LineItemCategory
				+ "'])/../preceding-sibling::td//i[contains(@data-bind,'PendingToPosted')]";
		Wait.WaitForElement(driver, input);
		Wait.waitForElementByXpath(driver, input);
		driver.findElement(By.xpath(input)).click();
		String src = driver.findElement(By.xpath(input)).getAttribute("class");
		Utility.app_logs.info(src);
		if (src.contains("inncenter-icons-collection transactionstatus-icon2")) {
			testSteps.add("LineitemCategory " + LineItemCategory + " folio line item status Posted");
			reslogger.info("LineitemCategory " + LineItemCategory + " folio line item status Posted");
		}
		else {
			testSteps.add("LineitemCategory " + LineItemCategory + " folio line item status is not Posted");
			reslogger.info("LineitemCategory " + LineItemCategory + " folio line item status is not Posted");
			
		}
		
		
		
	}
	
	public String takePaymentWithoutSave(WebDriver driver, ArrayList<String> test_steps, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String TakePaymentType, String IsChangeInPayAmount,
			String ChangedAmountValue, String IsSetAsMainPaymentMethod, String AddPaymentNotes)
			throws InterruptedException {
		test_steps.add("******************* Making payment **********************");
		reslogger.info("******************* Making payment **********************");
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment);
		Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_Amount);
		String amount = res.CP_Reservation_Folio_TakePayment_Amount.getText().trim();
		reslogger.info("amount : " + amount);
		test_steps.add("Amount before Pay : " + amount);
		reslogger.info("Amount before Pay : " + amount);
		Wait.waitForElementByXpath(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_PaymentButton);;
		res.CP_Reservation_Folio_TakePayment_PaymentButton.click();

		String CCNumber = "//h4[text()='Take Payment']/../..//label[text()='CARD NUMBER']/following-sibling::div/input";
		String name = "//h4[text()='Take Payment']/../..//label[text()='NAME ON CARD']/following-sibling::div/input";
		String exp = "//h4[text()='Take Payment']/../..//label[contains(text(),'EXPIRY')]/following-sibling::div/input";
		if (PaymentType.equalsIgnoreCase("Swipe")) {
			String CCCard = "(//h4[text()='Take Payment']/../..//label[text()='CARD NUMBER']/following-sibling::div/input)[2]";
			String swipe = "//h4[text()='Take Payment']/../..//button[text()='SWIPE']";
			Wait.WaitForElement(driver, swipe);
			driver.findElement(By.xpath(swipe)).click();
			test_steps.add("Clicking on Swipe");
			reslogger.info("Clicking on Swipe");
			Wait.WaitForElement(driver, CCCard);
			driver.findElement(By.xpath(CCCard)).sendKeys(CardNumber);
			test_steps.add("Enter Card Number : " + CardNumber);
			reslogger.info("Enter Card Number : " + CardNumber);
		} else {
			String payment = "//h4[text()='Take Payment']/../..//label[text()='PAYMENT METHOD']/..//div/div//span[text()='"
					+ PaymentType.trim() + "']";
			Wait.WaitForElement(driver, payment);
			driver.findElement(By.xpath(payment)).click();
			test_steps.add("Select Payment Type as : " + PaymentType);
			reslogger.info("Select Payment Type as : " + PaymentType);
			if (PaymentType.equalsIgnoreCase("MC") || PaymentType.equalsIgnoreCase("Visa")
					|| PaymentType.equalsIgnoreCase("Amex") || PaymentType.equalsIgnoreCase("Discover")) {

				Wait.WaitForElement(driver, CCNumber);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String CC = (String) js.executeScript("return arguments[0].value",
						driver.findElement(By.xpath(CCNumber)));
				reslogger.info(CC);
				if (CC.isEmpty() || CC.equalsIgnoreCase("")) {
					Wait.WaitForElement(driver, CCNumber);
					driver.findElement(By.xpath(CCNumber)).sendKeys(CardNumber);
					test_steps.add("Enter Card Number : " + CardNumber);
					reslogger.info("Enter Card Number : " + CardNumber);
					Wait.WaitForElement(driver, name);
					driver.findElement(By.xpath(name)).sendKeys(NameOnCard);
					test_steps.add("Enter Card Name : " + NameOnCard);
					reslogger.info("Enter Card Name : " + NameOnCard);
					Wait.WaitForElement(driver, exp);
					driver.findElement(By.xpath(exp)).sendKeys(CardExpDate);
					test_steps.add("Enter Card Exp Date : " + CardExpDate);
					reslogger.info("Enter Card Exp Date : " + CardExpDate);
				}
			}
		}
		if (IsChangeInPayAmount.equalsIgnoreCase("Yes")) {
			test_steps.add("Change the pay amount value : Yes");
			reslogger.info("Change the pay amount value : Yes");
			res.CP_Reservation_Folio_TakePayment_Amount.clear();
			res.CP_Reservation_Folio_TakePayment_Amount.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			res.CP_Reservation_Folio_TakePayment_Amount.sendKeys(ChangedAmountValue);
			test_steps.add("Enter the Change Amount Value : " + ChangedAmountValue);
			reslogger.info("Enter the Change Amount Value : " + ChangedAmountValue);
			amount = ChangedAmountValue;
		} else {
			test_steps.add("paying the amount : " + amount);
			reslogger.info("paying the amount : " + amount);
		}

		Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_TypeButton);
		res.CP_Reservation_Folio_TakePayment_TypeButton.click();

		String type = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//div/div//span[text()='"
				+ TakePaymentType.trim() + "']";
		Wait.WaitForElement(driver, type);
		driver.findElement(By.xpath(type)).click();
		test_steps.add("Select Take Payment Type : " + TakePaymentType);
		reslogger.info("Select Take Payment Type : " + TakePaymentType);

		String setAsMainPaymentMethod = "//span[text()='Set As Main Payment Method']/../input";
		if (IsSetAsMainPaymentMethod.equalsIgnoreCase("Yes")) {
			if (!driver.findElement(By.xpath(setAsMainPaymentMethod)).isSelected()) {
				Wait.WaitForElement(driver, setAsMainPaymentMethod);
				driver.findElement(By.xpath("//span[text()='Set As Main Payment Method']")).click();
				test_steps.add("Select set As Main Payment Method");
				reslogger.info("Select set As Main Payment Method");
			} else {
				test_steps.add("Already Selected set As Main Payment Method");
				reslogger.info("Already Selected set As Main Payment Method");
			}
		}

		String addPaymentNotes = "//button[text()='ADD NOTES']";
		String notes = "//input[@placeholder='Add Notes']";

		if (!(AddPaymentNotes.isEmpty() || AddPaymentNotes.equalsIgnoreCase(""))) {
			Wait.WaitForElement(driver, addPaymentNotes);
			driver.findElement(By.xpath(addPaymentNotes)).click();
			test_steps.add("Clicking on Add Payment Notes");
			reslogger.info("Clicking on Add Payment Notes");
			Wait.WaitForElement(driver, notes);
			driver.findElement(By.xpath(notes)).sendKeys(AddPaymentNotes);
			test_steps.add("Adding payment notes : " + AddPaymentNotes);
			reslogger.info("Adding payment notes : " + AddPaymentNotes);
		}

		String button = "//h4[text()='Take Payment']/../..//a[contains(text(),'" + amount.trim() + "')]";
		Wait.WaitForElement(driver, button);
		driver.findElement(By.xpath(button)).click();
		test_steps.add("Click on Pay");
		reslogger.info("Click on Pay");

		String takePayType = "//h4[contains(text(),'Successful')]/../..//label[text()='TYPE']/following-sibling::span";
		Wait.WaitForElement(driver, takePayType);
		String paytype = driver.findElement(By.xpath(takePayType)).getText().trim();
		assertEquals(paytype, TakePaymentType.trim());
		test_steps.add("Take Payment Type validated after payment : " + TakePaymentType);
		reslogger.info("Take Payment Type validated after payment : " + TakePaymentType);

		String paymentType = "//h4[contains(text(),'Successful')]/../..//label[text()='PAYMENT METHOD']/following-sibling::label/span[contains(text(),'"
				+ PaymentType.trim() + "')]";

		if (PaymentType.equalsIgnoreCase("Swipe")) {
			paymentType = "//h4[contains(text(),'Successful')]/../..//label[text()='PAYMENT METHOD']/following-sibling::label/span[contains(text(),'MC')]";
		}
		Wait.WaitForElement(driver, paymentType);
		if (driver.findElement(By.xpath(paymentType)).isDisplayed()) {
			test_steps.add("Payment Type validated after payment : " + PaymentType);
			reslogger.info("Payment Type validated after payment : " + PaymentType);
		} else {
			test_steps.add("Payment Type not displayed after payment : " + PaymentType);
			reslogger.info("Payment Type not displayed after payment : " + PaymentType);
		}

		String status = "//h4[contains(text(),'Successful')]/../..//label[text()='Approved']";
		Wait.WaitForElement(driver, status);
		if (driver.findElement(By.xpath(status)).isDisplayed()) {
			test_steps.add("Transaction status is : Approved");
			reslogger.info("Transaction status is : Approved");
		} else {
			test_steps.add("Transaction status is not : Approved");
			reslogger.info("Transaction status is not : Approved");
		}

		String payNotes = "//h4[contains(text(),'Successful')]/../..//span[text()='" + AddPaymentNotes + "']";
		if (!(AddPaymentNotes.isEmpty() || AddPaymentNotes.equalsIgnoreCase(""))) {
			if (driver.findElement(By.xpath(payNotes)).isDisplayed()) {
				test_steps.add("Paymant notes displayed " + AddPaymentNotes);
				reslogger.info("Paymant notes displayed " + AddPaymentNotes);
			} else {
				test_steps.add("Paymant notes not displayed " + AddPaymentNotes);
				reslogger.info("Paymant notes not displayed " + AddPaymentNotes);
			}
		}

		String close = "//h4[contains(text(),'Successful')]/../..//button[text()='Close']";
		Wait.WaitForElement(driver, close);
		driver.findElement(By.xpath(close)).click();
		test_steps.add("Click on Close");
		reslogger.info("Click on Close");
		String loading = "(//div[@class='ir-loader-in'])";
		int count = 0;
		while (true) {
			if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
				break;
			} else if (count == 20) {
				break;
			} else {
				count++;
				Wait.wait2Second();
			}
		}
		reslogger.info("Waited to loading symbol");
		return amount;
	}
	
	public void LineItemDescritionContainSpecialCharacter(WebDriver driver,ArrayList<String> testSteps) {
		testSteps.add("Verify line item contain special character or not");
		String lineItemDescription="(//span[text()='MC'])[last()]//..//following-sibling::td//a[@data-bind='text: $data.Description, click: $parent.fnPayLineItemDetail']";
		WebElement element=driver.findElement(By.xpath(lineItemDescription));
		 Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
	        Matcher matcher = pattern.matcher(element.getText());
	        boolean isStringContainsSpecialCharacter = matcher.find();
	        if(isStringContainsSpecialCharacter) {
	        	testSteps.add(element.getText()+ " contains special character");
	        }
	        	else  {  
	        	testSteps.add(element.getText()+ " does NOT contain special character");
	    }
		
		
	}
	public void CheckPanelGuestNameContainSpecialCharter(WebDriver driver,ArrayList<String> testSteps) {
		CPReservationPage res = new CPReservationPage();
		String value=res.getPanelStatusGuestName(driver);
		 Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
	        Matcher matcher = pattern.matcher(value);
	        boolean isStringContainsSpecialCharacter = matcher.find();
	        if(isStringContainsSpecialCharacter) {
	        	testSteps.add(value+ " contains special character");
	        }
	        	else  {  
	        	testSteps.add(value+ " does NOT contain special character");
	    }
		
		
	}
	
	public void verifyGuestsStatus(WebDriver driver, String status, ArrayList<String> testSteps)
			throws InterruptedException {

		Elements_Reservation elementReservation = new Elements_Reservation(driver);
		testSteps.add("Verify <b>Primary Guest</b> Status");
		if (elementReservation.checkInPrimaryGuest.isDisplayed()) {
			testSteps.add("Verify <b>Primary Guest</b> Status is not CheckedIn");
		} else {
			testSteps.add("Verify <b>Primary Guest</b> Status is CheckedIn");
		}
		testSteps.add("Verify <b>Secondary Guest</b> Status");
		if (elementReservation.checkInSecondaryGuest.isDisplayed()) {
			testSteps.add("Verify <b>Secondary Guest</b> Status is not CheckedIn");
		} else {
			testSteps.add("Verify <b>Secondary Guest</b> Status is CheckedIn");
		}

	}

	public void verifyCheckOutGuestName(WebDriver driver, ArrayList<String> test_steps, String expectedGuestName)
			throws InterruptedException {
		try {
			Wait.wait3Second();
			test_steps.add("Verify Header Bar Guest Name <b>" + expectedGuestName + "</b>");
			String foundvalue = driver
					.findElement(By.xpath(OR_Reservation.CP_SECONDARYGUEST_CHECKIN_CHECKOUTTAB_TITLENAME)).getText();
			assertEquals(foundvalue, expectedGuestName, "Guest Name not match");

			test_steps.add("Verified Header Bar Guest Name <b>" + expectedGuestName + " </b>");
		} catch (Exception e) {
			test_steps.add("Failed to Verify Guest Name <b>" + e.toString() + " </b>");

		}
	}

	public void verifySecondaryGuestReservationStatusFromButton(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_Reservation elementReservation = new Elements_Reservation(driver);
		String secondaryRollBackButton = "//div[contains(@data-bind,'$parent.ReservationStatus')][2]//button[text()='Roll Back']";

		if (elementReservation.checkInSecondaryGuest.isDisplayed()) {
			if (elementReservation.checkInSecondaryGuest.isDisplayed()) {
				test_steps.add("Verified Reservation Status for Primary Guest Is Reserved");
			}

		}

		else if (elementReservation.CheckOutSecondaryGuest.isDisplayed()) {
			test_steps.add("Verified Reservation Status for Secondary Guest Is In Checked in");
		} else {
			WebElement secondaryRollBackButtonElement = driver.findElement(By.xpath(secondaryRollBackButton));
			if (secondaryRollBackButtonElement.isDisplayed()) {
				test_steps.add("Verified Reservation Status for Secondary Guest Is CheckedOut");

			}
		}

	}

	public void verifyPropetyDetail(WebDriver driver, ArrayList<String> testSteps) throws Exception {
		testSteps.add("Verify Propert Detail Field");
		Elements_CPReservation reservation = new Elements_CPReservation(driver);
		CPReservationPage cpPage = new CPReservationPage();
		Elements_SetUp_Properties elements = new Elements_SetUp_Properties(driver);
		testSteps.add("Verify 'property details select country' is displayed or not");
		assertNotEquals(cpPage.propertyDetailsCountry(driver, testSteps), "");
		testSteps.add("Verified 'property details select country' is displayed");
		testSteps.add("Verify 'property details property name input field' is displayed or not");
		assertEquals(elements.Enter_PropertyName.isDisplayed(), true);
		testSteps.add("Verified 'property details property name input field' is displayed");
		testSteps.add("Verify 'property details legaly name input field' is displayed or not");
		assertEquals(elements.Enter_LegalyName.isDisplayed(), true);
		testSteps.add("Verified 'property details legaly name input field' is displayed");
		testSteps.add("Verify 'property details select town' is displayed or not");
		assertEquals(elements.Select_Town.isDisplayed(), true);
		testSteps.add("Verified 'property details select town' is displayed");
		testSteps.add("Verify 'property details description input field' is displayed or not");
		assertEquals(elements.Enter_Description.isDisplayed(), true);
		testSteps.add("Verified 'property details description input field' is displayed");
		testSteps.add("Verify 'property details contact name input field' is displayed or not");
		assertEquals(reservation.propertyDetailscontactName.isDisplayed(), true);
		testSteps.add("Verified 'property details contact name input field' is displayed");
		testSteps.add("Verify 'property details email input field' is displayed or not");
		assertEquals(reservation.propertyDetailsinputEmail.isDisplayed(), true);
		testSteps.add("Verified 'property details email input field' is displayed");
		testSteps.add("Verify 'property details phone number input field' is displayed or not");
		assertEquals(reservation.propertyDetailsPhoneNumber.isDisplayed(), true);
		testSteps.add("Verified 'property details phone number input field' is displayed");
		testSteps.add("Verify 'property details address input field' is displayed or not");
		assertEquals(reservation.propertyDetailsAddress1.isDisplayed(), true);
		testSteps.add("Verified 'property details address input field' is displayed");
		testSteps.add("Verify 'property details city input field' is displayed or not");
		assertEquals(reservation.propertyDetailsCity.isDisplayed(), true);
		testSteps.add("Verified 'property details city input field' is displayed");
		testSteps.add("Verify 'property details select State' is displayed or not");
		assertEquals(elements.Select_Property_State.isDisplayed(), true);
		testSteps.add("Verified 'property details select state' is displayed");
		testSteps.add("Verify 'property details save button' is displayed or not");
		assertEquals(elements.Properties_Save.isDisplayed(), true);
		testSteps.add("Verified 'property details save button' is displayed");
		testSteps.add("Verified <b>Propert Detail Field</b>");
	}

	public void verifyPropetyOptions(WebDriver driver, ArrayList<String> testSteps) throws Exception {
		testSteps.add("Verify Propert Detail Field");
		Elements_SetUp_Properties elements = new Elements_SetUp_Properties(driver);
		testSteps.add("Verify 'property options operations text' is displayed or not");
		assertEquals(elements.PropertyOperationsText.isDisplayed(), true);
		testSteps.add("Verified 'property options operations text' is displayed");
		testSteps.add("Verify 'property options check in/out text' is displayed or not");
		assertEquals(elements.PropertyCheckInOutText.isDisplayed(), true);
		testSteps.add("Verified 'property options check in/out text' is displayed");
		testSteps.add("Verify 'property options booking engine text' is displayed or not");
		assertEquals(elements.PropertyBookingEngineText.isDisplayed(), true);
		testSteps.add("Verified 'property options booking engine text' is displayed");
		testSteps.add("Verify 'property options system text' is displayed or not");
		assertEquals(elements.PropertySystemText.isDisplayed(), true);
		testSteps.add("Verified 'property options system text' is displayed");
		testSteps.add("Verify 'property options cancelled reservation text' is displayed or not");
		assertEquals(elements.PropertyCancelledReservationText.isDisplayed(), true);
		testSteps.add("Verified 'property options cancelled reservation text' is displayed");
		testSteps.add("Verify 'property options operations text' is displayed or not");
		assertEquals(elements.PropertyOperationsText.isDisplayed(), true);
		testSteps.add("Verified 'property options operations text' is displayed");
		testSteps.add("Verify 'property options credit card text' is displayed or not");
		assertEquals(elements.Property_CreditCard.isDisplayed(), true);
		testSteps.add("Verified 'property options credit card text' is displayed");
		testSteps.add("Verify 'property options guaranteed radio button' is displayed or not");
		assertEquals(elements.OptionGuaranteed.isDisplayed(), true);
		testSteps.add("Verified 'property options guaranteed radio button' is displayed");
		testSteps.add("Verify 'property options credit card always radio button' is displayed or not");
		assertEquals(elements.OptionCreditCardAlways.isDisplayed(), true);
		testSteps.add("Verified 'property options credit card always radio button' is displayed");
		testSteps.add("Verify 'property options after limit meet radio button' is displayed or not");
		assertEquals(elements.AfterlimitMeet.isDisplayed(), true);
		testSteps.add("Verified 'property options after limit meet radio button' is displayed");
		testSteps.add("Verify 'property options all night radio button' is displayed or not");
		assertEquals(elements.AllNight.isDisplayed(), true);
		testSteps.add("Verified 'property options all night radio button' is displayed");
		testSteps.add("Verify <b>Propert Detail Field</b>");
	}

	public void selectTakePaymentAuthorizationOnlyType(WebDriver driver, ArrayList<String> testSteps) throws Exception {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		String path = "//ul[@class='dropdown-menu inner']//span[contains(text(),'Authorization Only')]";
		res.CP_Reservation_NoShowPaymentPopup_NoShowPaymentPopupType.click();
		driver.findElement(By.xpath(path)).click();

		testSteps.add("Select Type as Authorization Only");
	}

	public void howerToIconInFolioDetail(WebDriver driver, ArrayList<String> test_steps, String PaymentType, String note) {
		String verifyNoteIcon = "//span[contains(text(), '" + PaymentType
				+ "')]/../..//td[@class='text-center lineitem-note']//i[contains(@title,'"+note+"')]";
		WebElement verifyNoteIconElement = driver.findElement(By.xpath(verifyNoteIcon));
		/*Actions action = new Actions(driver);
		action.moveToElement(verifyNoteIconElement).perform();*/
		assertTrue(verifyNoteIconElement.isDisplayed(), "Failed to verify Note icon");
		test_steps.add("Verified Note: " + note);

	}

	public void AuthorizationIcon(WebDriver driver, ArrayList<String> test_steps, String PaymentType) {
		test_steps.add("Verify Authorization Icon is Present or not");
		String verifyNoteIcon = "//span[contains(text(), '" + PaymentType
				+ "')]/../..//td[@class='text-center lineitems-changestatus']//i[@class='inncenter-icons-collection transactionstatus-icon7']";
		driver.findElement(By.xpath(verifyNoteIcon));
		test_steps.add("Verify Authorization Icon is Present");
	}

	public void verifyPaymentDetailUserName(WebDriver driver, ArrayList<String> test_steps, String userName)
			throws InterruptedException {
		test_steps.add("Verify <b>" + userName
				+ "</b> should be displayed or not in the footer of the line item Details pop-up.");
		String foundDetail = "(//span[contains(@data-bind,'$root.UpdatedBy()')])[last()]";
		String cancelPaymentButton = "(//span[contains(@data-bind,'$root.UpdatedBy()')])[last()]//..//..//div//button[text()='Cancel']";

		WebElement elementfoundDetail = driver.findElement(By.xpath(foundDetail));
		String value = elementfoundDetail.getText();
		if (value.contains(userName)) {
			test_steps.add("Verified <b>" + userName
					+ "</b> should be displayed in the footer of the line item Details pop-up.");
		} else {
			test_steps.add("Verified <b>" + userName
					+ "</b> should not be displayed in the footer of the line item Details pop-up.");

		}
		driver.findElement(By.xpath(cancelPaymentButton)).click();
	}

	public ArrayList<String> verifySelectedRoomNumberisAvaible(WebDriver driver, ArrayList<String> test_steps,
			String RoomClass, ArrayList<String> roomNos) throws InterruptedException {
		for (int a = 0; a < roomNos.size(); a++) {
			String room = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
					+ "')]//..//..//div[2]//span";

			Wait.WaitForElement(driver, room);
			String rooms = driver.findElement(By.xpath(room)).getText();
			reslogger.info(rooms);
			String[] roomsCount = rooms.split(" ");
			int count = Integer.parseInt(roomsCount[0].trim());
			if (count > 0) {
				String sel = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]//..//..//..//following-sibling::div//div//select";

				String rulessize = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"

						+ RoomClass.trim() + "')]/following-sibling::span";
				reslogger.info(rulessize);

				int ruleCount = driver.findElements(By.xpath(rulessize)).size();
				WebElement ele = driver.findElement(By.xpath(sel));
				test_steps.add("Selected room class : " + RoomClass);
				reslogger.info("Selected room class : " + RoomClass);
				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div/button";
				Wait.waitForElementToBeClickable(By.xpath(expand), driver, 20);
				Wait.waitForElementToBeClickable(By.xpath(expand), driver);
				WebElement elementExpand = driver.findElement(By.xpath(expand));
				Utility.ScrollToElement_NoWait(elementExpand, driver);
				elementExpand.click();

				String roomnum = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]//..//..//..//following-sibling::div//div//select//following-sibling::div//ul//li//span[@class='text']";
				reslogger.info(roomnum);
				List<WebElement> getRoomNumber = driver.findElements(By.xpath(roomnum));
				Utility.app_logs.info(getRoomNumber.size());
				for (int i = 0; i < getRoomNumber.size(); i++) {
					if (getRoomNumber.get(i).getText().equals(roomNos.get(a))) {
						test_steps.add(roomNos.get(a) + " is avaible for");
					}
				}

			}

		}

		return test_steps;
	}

	public void click_Take_payment_from_reservation_detail_button(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR.TakePaymentFromReservationDetailButton);
		Wait.explicit_wait_visibilityof_webelement(res.TakePaymentFromReservationDetailButton, driver);
		Utility.ScrollToElement(res.TakePaymentFromReservationDetailButton, driver);
		res.TakePaymentFromReservationDetailButton.click();
		test_steps.add("Click Take Payment from detail");
		reslogger.info("Click Take Payment from detail");
	}

	public void verify_history_detail_is_present_or_not(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		test_steps.add("<b>Verify history detail is present or not</b>");
		reslogger.info("<b>Verify history detail is present or not</b>");
		String historyDetail = "//tbody[@data-bind='foreach:historyList']//td";
		List<WebElement> element = driver.findElements(By.xpath(historyDetail));
		reslogger.info(element.size());
		if (element.size() > 1) {
			test_steps.add("<b>Verified history detail is present</b>");
			reslogger.info("<b>Verified history detail is present</b>");
		} else {
			test_steps.add("<b>Verified history detail is not present</b>");
			reslogger.info("<b>Verified history detail is not present</b>");
		}
	}
	public void verifyLineItem(WebDriver driver, ArrayList<String> test_steps, String CheckInDate, String CheckOutDate) throws InterruptedException {
		reslogger.info("******************* Verify Folio Details **********************");

		Elements_CPReservation res = new Elements_CPReservation(driver);
		String LineItemAmount = "//tbody[contains(@data-bind,'ComputedFolioItems')]//td[@class='lineitem-date']/span[contains(text(),'')]/../following-sibling::td[@class='lineitem-category']/span[contains(text(),'Room Charge')]/../following-sibling::td[@class='lineitem-description']//a/../following-sibling::td[@class='lineitem-amount']";
		String Date="//span[contains(text(),'Room Charge')]//..//..//..//td[@class='lineitem-date']/span";
		Wait.WaitForElement(driver, LineItemAmount);
		List<WebElement> SetLineItemElement = driver.findElements(By.xpath(LineItemAmount));
		List<WebElement> getDate = driver.findElements(By.xpath(Date));
		// Verified No Show Fee line item
		for(int i=0;i<SetLineItemElement.size()-1;i++) {
			assertTrue(SetLineItemElement.get(i).isDisplayed(), "Failed: To Verify Line Item");
			if(SetLineItemElement.size()>1) {
				assertTrue(SetLineItemElement.get(i+1).isDisplayed(), "Failed: To Verify Line Item");
				
			test_steps.add(" Verified Date:<b>  " + getDate.get(i).getText() + "</b>");
			reslogger.info("Verified  Date: " + getDate.get(i).getText());
			test_steps.add(" Verified Amount For Date:<b>  "+getDate.get(i).getText()+" : " +SetLineItemElement.get(i).getText() + "</b>");
			reslogger.info(" Verified Amount For Date:<b>  "+getDate.get(i).getText()+" : "+ SetLineItemElement.get(i).getText());
			test_steps.add(" Verified Date:<b>  " + getDate.get(i+1).getText()  + "</b>");
			reslogger.info("Verified  Date: " + getDate.get(i+1).getText());
			test_steps.add(" Verified Amount For Date:<b>  "+getDate.get(i+1).getText()+" : " + SetLineItemElement.get(i+1).getText()  + "</b>");
			reslogger.info(" Verified Amount For Date:<b>  "+getDate.get(i+1).getText()+" : "+ SetLineItemElement.get(i+1).getText());
			}
			else {
				test_steps.add(" Verified Date:<b>  " + getDate.get(i) + "</b>");
				reslogger.info(" Verified Amount For Date:<b>  "+getDate.get(i).getText()+" : "+ SetLineItemElement.get(i));
			}
			}
		
		

	}
	public ArrayList<String> get_STAYINFORoomChargesWithoutCurrency(WebDriver driver, ArrayList test_steps, String RoomClassName) {
		String[] room=RoomClassName.split("\\|");
		ArrayList<String> balances=new ArrayList<>(); 
		for(int i=0;i<room.length;i++) {
			
			String Balance = driver.findElement(By.xpath(
					"(//div[contains(@data-bind,'ReservationStatusLookup')]/div//div[contains(@class,'ir-roomInfo')]//span[contains(text(),'"+room[i]+"')]//..//span[contains(@data-bind,'showInnroadCurrency')])[1]"))
					.getText();
			Balance = Balance.replace("$", "");
			if(i==0) {
			test_steps.add("StayInfo Room Charges for primary guest: <b>" + Balance + "</b>");
			reslogger.info("StayInfo Room Charges for primary guest: " + Balance);
			}
			else {
				test_steps.add("StayInfo Room Charges for secondary guest: <b>" + Balance + "</b>");
				reslogger.info("StayInfo Room Charges for secondary guest: " + Balance);
			}
			balances.add(Balance);
			
			
		}
		return balances;
	}


	 public void getLoadtime(WebDriver driver, ArrayList<String> test_steps, String pageName) {
		 Long loadtime = (Long)((JavascriptExecutor)driver).executeScript(
		            "return performance.timing.loadEventEnd - performance.timing.navigationStart;");    
		String time= String.valueOf(TimeUnit.MILLISECONDS.toMillis(loadtime));
		String time1= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(loadtime));
		        test_steps.add(pageName + "Load time is " + time + " </b> MilliSecond");
				reslogger.info(pageName + "Load time is " + time + " MilliSecond");
				test_steps.add(pageName + "Load time is " + time1 + " </b> Second");
				reslogger.info(pageName + "Load time is " + time1 +" Second");
	 }
	 
	 public void verifyStayInforComingEditMode(WebDriver driver, ArrayList<String> test_steps) {
		 Elements_CPReservation element = new Elements_CPReservation(driver);
		 Wait.WaitForElement(driver, OR_Reservation.FindRoomButton);
			Wait.explicit_wait_visibilityof_webelement(element.FindRoomButton, driver);
			assertTrue(element.FindRoomButton.isDisplayed(), "Failed: 'Stay Infor' not displayed");
			test_steps.add("Change Stay infor Displayed in Edit mode ");
			reslogger.info("Change Stay infor Displayed in Edit mode ");
		}
	 
	 public void verifykSaveAfterEditStayInfo(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		     	Elements_CPReservation res = new Elements_CPReservation(driver);
		     	  reservation.verifySpinerLoading(driver);
				Wait.WaitForElement(driver, OR_Reservation.SaveButton);
				Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.SaveButton), driver);
				assertTrue(res.SaveButton.isDisplayed(), "Failed: to verify Save button");
				test_steps.add("Save button enabled ");
				reslogger.info("Save button enabled");
		}
	 
	 public void removeAssociatedAccount(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		 Elements_CPReservation res = new Elements_CPReservation(driver);
		 res.CP_NewReservation_Account.sendKeys(Keys.CONTROL,"a");
		 res.CP_NewReservation_Account.sendKeys(Keys.DELETE);
		 test_steps.add("Removed Associated account from Reservation");
		 reslogger.info("Removed Associated account from Reservation");
		 
	 }
	 
	 
	 public void clickFolioDescription(WebDriver driver, String description, String amount) throws InterruptedException {
			String path="//tbody[contains(@data-bind,'ComputedFolioItems')]//td[@class='lineitem-amount']"
					+ "/span[contains(text(),'"+amount+"')]/parent::td/preceding-sibling::td[@class='lineitem-description']/a[contains(text(),'"+description+"')]";
			Wait.WaitForElement(driver, path);
			WebElement element = driver.findElement(By.xpath(path));
			Utility.ScrollToElement(element, driver);
			element.click();
			String path1 = "//span[contains(text(),'Payment Details')]";
			Wait.WaitForElement(driver, path1);
		}
	 
	 public void enterCardNumber(WebDriver driver, String cardNumber, ArrayList<String> testStep )
				throws InterruptedException, ParseException {
			Elements_CPReservation element = new Elements_CPReservation(driver);
		
			Wait.WaitForElement(driver, OR_Reservation.decryptLink1);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.decryptLink1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.decryptLink1), driver);
			Utility.ScrollToElement_NoWait(element.decryptLink1, driver);
			element.decryptLink1.click();
			testStep.add("Click on decrypt Card number button");
			reslogger.info("Click on decrypt Card number button");

			Wait.WaitForElement(driver, OR_Reservation.ccNumberInput1);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.ccNumberInput1), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.ccNumberInput1), driver);
			Utility.ScrollToElement_NoWait(element.ccNumberInput1, driver);
			element.ccNumberInput1.clear();
			element.ccNumberInput1.sendKeys(cardNumber);
			element.ccNumberInput1.sendKeys(Keys.TAB);
			testStep.add("Entered card Number : " + cardNumber);
			reslogger.info("Entered card Number : " + cardNumber);

			}
	 
	 public void verifyTransactionDeclinedMessage(WebDriver driver, ArrayList<String> testStep) {
		 Elements_CPReservation CPReservationPage = new Elements_CPReservation(driver);
		 Wait.WaitForElement(driver, OR_Reservation.transactionDeclinedErrorMessage);
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.transactionDeclinedErrorMessage), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.transactionDeclinedErrorMessage), driver);
			assertTrue(CPReservationPage.transactionDeclinedErrorMessage.isDisplayed(), "Failed : is Element Dipslayed");
			String getText = CPReservationPage.transactionDeclinedErrorMessage.getText().trim();
			assertEquals(getText, "Transaction Declined. Please try again or enter a different payment method.", "Failed : Transaction declined. Please try again");
			testStep.add("Verified that Error Message 'Transaction Declined. Please try again or enter a different payment method.' is diplaying");
			reslogger.info("Verified that Error Message 'Transaction Declined. Please try again or enter a different payment method.' is diplaying");

	 }
		public void clickSwipeButtonInCheckInPopup(WebDriver driver, ArrayList<String> testSteps) {
			Elements_Reservation elementReservation = new Elements_Reservation(driver);
			Wait.waitForElementByXpath(driver, OR_Reservation.swipeButtonInCheckInPopup);
			elementReservation.swipeButtonInCheckInPopup.click();
			testSteps.add("Click swipe button in checkin popup");
			
		}

		public void enterCardNumberInCheckInPopup(WebDriver driver, ArrayList<String> testSteps, String cardNumber) {
			Elements_Reservation elementReservation = new Elements_Reservation(driver);
			Wait.waitForElementByXpath(driver, OR_Reservation.cardNumberInCheckInPopup);
			elementReservation.cardNumberInCheckInPopup.sendKeys(cardNumber);
			elementReservation.cardNumberInCheckInPopup.sendKeys(Keys.TAB);
			testSteps.add("Enter card number  : " + cardNumber);
			
		}

		public void clickAuthorizeInCheckInPopup(WebDriver driver, ArrayList<String> testSteps) {
			try {
				
				Elements_Reservation elementReservation = new Elements_Reservation(driver);
				Wait.waitForElementByXpath(driver, OR_Reservation.authorizeInCheckInPopup2);
				elementReservation.authorizeInCheckInPopup2.click();
				testSteps.add("Click authorize button in checkin popup");
				
				
			} catch (Exception e) {
				Elements_Reservation elementReservation = new Elements_Reservation(driver);
				Wait.waitForElementByXpath(driver, OR_Reservation.authorizeInCheckInPopup);
				elementReservation.authorizeInCheckInPopup.click();
				testSteps.add("Click authorize button in checkin popup");
			}
			
			
		}
		
		public void cancelReservation(WebDriver driver, ArrayList<String> testSteps, String reason, String amount) throws InterruptedException {
			Elements_Reservation elementReservation = new Elements_Reservation(driver);
			CPReservationPage reservationPage = new CPReservationPage();
			Wait.waitForElementByXpath(driver, OR_Reservation.ReservationStatusDropDown);
			Wait.wait1Second();
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(OR_Reservation.ReservationStatusDropDown)));
			elementReservation.ReservationStatusCancel.click();
			elementReservation.CancellationReason.sendKeys(reason);
			testSteps.add("Enter cancellation reason : " + reason);
			
			Wait.waitForElementByXpath(driver, OR_Reservation.VoidRoomChargesCheckBox);
			elementReservation.VoidRoomChargesCheckBox.click();
			testSteps.add("Clicked Void room charges button");
			
			try {
				elementReservation.ProceedToCancellationPayment.click();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			reservationPage.inputAmountWhileCheckINAndCheckOut(driver, testSteps, amount);
			testSteps.add("Verifying amount entered ias zero : " + amount);		
			String getText  = reservationPage.getCancelationAmount(driver);
			Utility.verifyEquals(getText, "0.00", testSteps);
			try {
				driver.findElement(By.xpath("//button[contains(@data-bind, 'CancelWithoutPayment')]")).click();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try {
				elementReservation.Log.click();
			} catch (Exception e) {
			}
			try {
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.CancelledClose), 30, driver);
				elementReservation.CancelledClose.click();
			} catch (Exception e) {
				
			}

			Wait.wait5Second();
			String s4 = elementReservation.ReservationCurrentStatus.getText();
			if (s4.equalsIgnoreCase("Cancelled")) {
				System.out.println("Successfully changed Reservation status to Cancelled");
			} else {
				System.out.println("Unable to change Reservation status to Cancelled");
			}

		}
		
		public void inHouseReservation2(WebDriver driver) throws InterruptedException {
			Elements_Reservation elementReservation = new Elements_Reservation(driver);

			Utility.clickThroughAction(driver, driver.findElement(By.xpath(OR_Reservation.ReservationStatusDropDown)));
			try {
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.ReservationStatusCheckin), driver,60);
				elementReservation.ReservationStatusCheckin.click();
			} catch (Exception e) {
				elementReservation.CP_Reservation_CheckInAllButton.click();
			}
			reservation.generatGuestReportToggle(driver, test_steps, "No");
			// Utility.clickThroughJavaScript(driver,
			// driver.findElement(By.xpath(OR_Reservation.ReservationStatusConfirmCheckin)));
			try {
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.ProceedToCheckInPayment), driver);
				elementReservation.ProceedToCheckInPayment.click();
			} catch (Exception e) {
				reslogger.info("There is no CheckIn policy");
				try {
					Utility.clickThroughAction(driver,
							driver.findElement(By.xpath(OR_Reservation.ReservationStatusConfirmCheckin)));

				} catch (Exception e1) {
					Utility.clickThroughJavaScript(driver,
							driver.findElement(By.xpath(OR_Reservation.ReservationStatusConfirmCheckin)));
				}
			}
			Wait.wait2Second();
			try {
				Wait.presenceOfElementLocated(driver, OR_Reservation.roomStatusDirtyMessage, 20);
				reslogger.info("Room is Dirty");
				List<WebElement> ele = driver.findElements(By.xpath("//button[text()='Confirm']"));
				reslogger.info("Confirm buttons: " + ele.size());
				Utility.clickThroughJavaScript(driver, ele.get(ele.size() - 1));
				// Utility.clickThroughAction(driver, ele.get(ele.size()-1));
				reslogger.info("Clicked on Confirm button");
				// elementReservation.confirmCheckInAtDirtyMessage.click();
				// Utility.clickThroughAction(driver,
				// elementReservation.confirmCheckInAtDirtyMessage);
			} catch (Exception e) {
				reslogger.info("Room is not dirty");
			}
			try {
				String payButton = "//a[contains(text(),'Pay') and contains(@data-bind,'ready-to-process')]";
				Wait.waitForElementToBeClickable(By.xpath(payButton), driver);
				Utility.ScrollToElement(driver.findElement(By.xpath(payButton)), driver);
				driver.findElement(By.xpath(payButton)).click();
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.checkOutButtonClose), driver);
				elementReservation.checkOutButtonClose.click();
			}catch (Exception e) {
				//reservation.selectPaymentMethod(driver, Amount);
			}
			Wait.wait5Second();
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.ReservationCurrentStatus), driver);
			String s6 = elementReservation.ReservationCurrentStatus.getText();
			System.out.println("s6=" + s6);
			if (s6.equalsIgnoreCase("In-House")) {
				System.out.println("Successfully changed Reservation status to In-House");
			} else {
				System.out.println("Unable to change Reservation status to In-House");
			}
		}
		
		public void void_PostedLineItem(WebDriver driver, ArrayList teststeps, String LineItemCategory)
				throws InterruptedException {
			teststeps.add("******************* Void Line Item to folio**********************");
			reslogger.info("******************* Void Line Item to folio**********************");

			Elements_CPReservation ReservationPage = new Elements_CPReservation(driver);
			String line = "(//td[@class='lineitem-category']/span[text()='" + LineItemCategory + "'])";

			String input = "(//td[@class='lineitem-category']/span[text()='" + LineItemCategory
					+ "'])/../preceding-sibling::td//input";
			Wait.WaitForElement(driver, input);
			
			driver.findElement(By.xpath(input)).click();
	boolean postlineItemStatus=false;
			try {
				Wait.WaitForElement(driver, input);
				
				postlineItemStatus=driver.findElement(By.xpath(input)).isEnabled();
				if(postlineItemStatus==true)
				{
					teststeps.add("posted line item can  be voided");
					reslogger.info("posted line item can  be voided");
					}
				else
				{
					teststeps.add("posted line item can not be voided");
					reslogger.info("posted line item can not be voided");
					
					
				}
			} catch (Exception e) {
				teststeps.add("posted line item can not be voided");
				reslogger.info("posted line item can not be voided");	
			}
			
		}
		
		public void changeResStatus(WebDriver driver, ArrayList test_steps, String ResStatus)
				throws InterruptedException {

				try {
				Elements_CPReservation res = new Elements_CPReservation(driver);
				JavascriptExecutor jse = (JavascriptExecutor) driver;

				String expand = "//div[@class='ng-statusChnage ir-statusMenu ul']//span";
				Wait.WaitForElement(driver, expand);
				WebElement expandElement = driver.findElement(By.xpath(expand));
				Wait.explicit_wait_elementToBeClickable(expandElement, driver);
				try {
				expandElement.click();
				} catch (Exception e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", expandElement);
				}
				String status = "//div[@class='ng-statusChnage ir-statusMenu ul']//span//ul//span[text()='" + ResStatus
				+ "']";
				try {
				Wait.WaitForElement(driver, status);
				driver.findElement(By.xpath(status)).click();
				} catch (Exception e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(status)));
				}
				test_steps.add("Reservation status changes to : " + ResStatus);
				reslogger.info("Reservation status changes to : " + ResStatus);
				if (ResStatus.equals("Cancel")) {
				Utility.app_logs.info("ResStatus: " + ResStatus);
				Wait.WaitForElement(driver, OR_Reservation.CancelReservation_Reason);
				Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.CancelReservation_Reason), driver);
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.CancelReservation_Reason), driver);
				res.CancelReservation_Reason.sendKeys(ResStatus);
				test_steps.add("Cancel Reason : " + ResStatus);
				reslogger.info("Cancel Reason: " + ResStatus);
				String cancelReservation = "//button[text()='PROCEED TO CANCELLATION PAYMENT']|//button[text()='CONFIRM CANCELLATION ']";
				String mainPaymentCheckbox = "//div[contains(@data-bind,'IsPaymentSetAsMainMethodVisible')]//span[@class='ir-check-box']";
				String PaymentRefund = "(//button[@class='btn ir-btn btn-success btn-block'])[1]";
				try {
					Utility.ScrollToElement(res.ProceedToPaymentButton, driver);
					res.ProceedToPaymentButton.sendKeys(Keys.ENTER);
					Utility.clickThroughJavaScript(driver, res.ProceedToPaymentButton);
					test_steps.add("Click 'PROCEED TO CANCELLATION PAYMENT' Button");
					reslogger.info("Click 'PROCEED TO CANCELLATION PAYMENT' Button");
					String loading = "(//div[@class='ir-loader-in'])";
					Wait.waitTillElementDisplayed(driver, loading);
				}catch(Exception e){
					reslogger.info("Button with text  'PROCEED TO CANCELLATION PAYMENT' is not clicked");
					
				}
				try {

				Wait.WaitForElement(driver, cancelReservation);
				Wait.waitForElementToBeVisibile(By.xpath(cancelReservation), driver);
				Wait.waitForElementToBeClickable(By.xpath(cancelReservation), driver);
				driver.findElement(By.xpath(cancelReservation)).click();
				}catch(Exception e){				
					reslogger.info("Button with text  'Confirm CANCELLATION' not showing");
				}
				
				

				}
				String loading = "(//div[@class='ir-loader-in'])";
				int count = 0;
				while (true) {
				if (driver.findElements(By.xpath(loading)).size() > 4) {
				break;
				} else if (count == 20) {
				break;
				} else {
				count++;
				Wait.wait2Second();

				}
				reslogger.info("Waited to loading symbol");
				}
				} catch (Exception e) {
				e.printStackTrace();
				//reslogger.info(e);
				}

				}
		

		public void SelectPaymentMethod(WebDriver driver, ArrayList<String> test_steps,
				String PaymentMethod) throws ParseException {
				String path = "//div[contains(@class,'payment-method')]//button";
				WebElement PaymentMethodElement = driver.findElement(By.xpath(path));
				PaymentMethodElement.click();
				test_steps.add("Click Payment Method");
				reslogger.info("Click CPayment Method");
				String options = "//paymentmethod//div[contains(@class,'open')]/ul//li/a//span[@class='text'][contains(text(),'"
				+ PaymentMethod + "')]";
				WebElement PaymentMethodOptions = driver.findElement(By.xpath(options));
				PaymentMethodOptions.click();
				test_steps.add("Select Payment Option :<b> " + PaymentMethod + "<b>");
				reslogger.info("Click Payment Option: " + PaymentMethod);
				}

}

