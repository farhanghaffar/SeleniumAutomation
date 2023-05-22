package com.innroad.inncenter.pageobjects;

import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.Assert;
import org.testng.SkipException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.jetty9.util.thread.TryExecutor;
import org.testng.Assert;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.ORRateGrid;
import com.innroad.inncenter.properties.OR_BE;
import com.innroad.inncenter.properties.OR_NightlyRatePlan;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.webelements.WebElementsOverview;
import com.innroad.inncenter.pages.DerivedRatePage;
import com.innroad.inncenter.pages.RateGridPage;
import com.innroad.inncenter.properties.OR_RateGrid;
import com.innroad.inncenter.properties.OR_RatesGrid;
import com.innroad.inncenter.webelements.Elements_Inventory;
import com.innroad.inncenter.webelements.Elements_RatesGrid;
import com.innroad.inncenter.interfaces.IRateDerived;
import com.innroad.inncenter.interfaces.IRatesGrid;
import com.innroad.inncenter.model.RatesGridChannelRatesRules;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.webelements.Elements_DerivedRate;
import com.innroad.inncenter.webelements.Elements_DocumentTemplates;
import com.innroad.inncenter.webelements.Elements_NewRoomClass;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.innroad.inncenter.webelements.Elements_VerifyAvailabilityInBookingEngine;

public class RatesGrid {

	public static Logger logger = Logger.getLogger("NightlyRate");
	public static Logger rateGridLogger = Logger.getLogger("NightlyRate");
	public static HashMap<String, String> minStayDates = new HashMap<String, String>();
	public static HashMap<String, String> noCheckInDates = new HashMap<String, String>();
	public static HashMap<String, String> noCheckOutDates = new HashMap<String, String>();
	public static HashMap<String, String> rateValuesMap = new HashMap<String, String>();

	public void clickForRateGridCalender(WebDriver driver, ArrayList<String> test_steps) {

		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		waitForSweetLoading(driver);

		Wait.explicit_wait_visibilityof_webelement_120(element.RATE_GRID_CALENDAR_ICON, driver);
		Wait.explicit_wait_elementToBeClickable(element.RATE_GRID_CALENDAR_ICON, driver);
		element.RATE_GRID_CALENDAR_ICON.click();

		try {
			Wait.explicit_wait_10sec(driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div")), driver);
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, element.RATE_GRID_CALENDAR_ICON);
		}

		test_steps.add("Rate Grid Calender Icon Clicked");
		Utility.app_logs.info("Rate Grid Calender Icon Clicked");

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRatePlanNames> ' Description: < Get all the Rate Plan
	 * Name> ' Input parameters: < WebDriver driver,String test_steps> ' Return
	 * value: <ArrayList<String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/01/2020>
	 */

	public ArrayList<String> getRatePlanNames(WebDriver driver) throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
//		Wait.WaitForElement(driver, ORRateGrid.ratePlanNamesList);

		Utility.ScrollToElement(elements.ratePlanNamesList.get(0), driver);
		for (WebElement ele : elements.ratePlanNamesList) {
			getNames.add(ele.getAttribute("aria-label"));
		}
		rateGridLogger.info("Get List of All Rate Plans: " + getNames);
		return getNames;
	}

	public ArrayList<String>[] getRatePlanNamesAndColor(WebDriver driver) throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		ArrayList<String> getColors = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, ORRateGrid.ratePlanNamesList);
		Utility.ScrollToElement(elements.ratePlanNamesList.get(0), driver);
		for (int i = 0; i < elements.ratePlanNamesList.size(); i++) {
			getNames.add(elements.ratePlanNamesList.get(i).getAttribute("aria-label"));
			String color = elements.ratePlansColor.get(i).getCssValue("color");
			getColors.add(Color.fromString(color).asHex());
		}

		rateGridLogger.info("Get List of All Rate Plans and Color: " + getNames + "--" + getColors);

		ArrayList<String>[] arrayOfList = new ArrayList[2];
		arrayOfList[0] = getNames;
		arrayOfList[1] = getColors;
		return arrayOfList;
	}

	public String selectDateFromDatePicker(WebDriver driver, String desiredDate, String desiredDateFormat,
			ArrayList<String> test_steps) {
		logger.info("==========SELECT DATE FROM DATE PICKER==========");
		test_steps.add("==========SELECT DATE FROM DATE PICKER==========");
		String selectedMonthYearPath = "//div[@class='DayPicker-Caption']/div";
		String nextMonthBtnPath = "//button[contains(@class,'FloatRight')]";
		String previousMonthBtnPath = "//button[contains(@class,'FloatLeft')]";

		String selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
		logger.info("Found Mounth Year : " + selectedMonthYearTxt);
		String foundYear = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
		logger.info("Parsed Year : " + foundYear);
		String foundMonth = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
		logger.info("Parsed Month : " + foundMonth);

		logger.info("Desired Date : " + desiredDate);
		String desiredDay = Utility.parseDate(desiredDate, desiredDateFormat, "dd");
		logger.info("Parsed Desired Day : " + desiredDay);
		String desiredMonth = Utility.parseDate(desiredDate, desiredDateFormat, "MM");
		logger.info("Parsed Desired Month : " + desiredMonth);
		String desiredYear = Utility.parseDate(desiredDate, desiredDateFormat, "yyyy");
		logger.info("Parsed Desired Year : " + desiredYear);

		logger.info("===========CHECKING YEAR CONDITION==========");
		if (!foundYear.equals(desiredYear)) {
			int foundYearIntParssed = Integer.parseInt(foundYear);
			int desiredYearIntParssed = Integer.parseInt(desiredYear);

			if (foundYearIntParssed < desiredYearIntParssed) {
				logger.info("Found Year : " + foundYearIntParssed + " is Less than " + "Desired Year : "
						+ desiredYearIntParssed);
				while (foundYearIntParssed != desiredYearIntParssed) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(nextMonthBtnPath)));
					logger.info("NEXT ARROW CLICKED FOR YEAR ");
					selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
					foundYear = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
					foundMonth = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
					foundYearIntParssed = Integer.parseInt(foundYear);
					logger.info("NEW FOUND YEAR : " + foundYearIntParssed);
				}
			} else if (foundYearIntParssed > desiredYearIntParssed) {
				logger.info("Found Year : " + foundYearIntParssed + " is Greater than " + "Desired Year : "
						+ desiredYearIntParssed);
				while (foundYearIntParssed != desiredYearIntParssed) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(previousMonthBtnPath)));
					logger.info("PREVIOUS ARROW CLICKED FOR YEAR ");
					selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
					foundYear = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
					foundMonth = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
					foundYearIntParssed = Integer.parseInt(foundYear);
					logger.info("NEW FOUND YEAR : " + foundYearIntParssed);
				}
			}
		}

		logger.info("===========CHECKING MONTH CONDITION==========");

		if (!foundMonth.equals(desiredMonth)) {
			int foundMonthIntParssed = Integer.parseInt(foundMonth);
			int desiredMonthIntParssed = Integer.parseInt(desiredMonth);

			if (foundMonthIntParssed < desiredMonthIntParssed) {
				logger.info("Found Month : " + foundMonthIntParssed + " is Less than " + "Desired Month : "
						+ desiredMonthIntParssed);
				while (foundMonthIntParssed != desiredMonthIntParssed) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(nextMonthBtnPath)));
					logger.info("NEXT ARROW CLICKED FOR Month ");
					selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
					foundYear = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
					foundMonth = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
					foundMonthIntParssed = Integer.parseInt(foundMonth);
					logger.info("NEW FOUND MONTH : " + foundMonthIntParssed);
				}
			} else if (foundMonthIntParssed > desiredMonthIntParssed) {
				logger.info("Found Month : " + foundMonthIntParssed + " is Greater than " + "Desired Month : "
						+ desiredMonthIntParssed);
				while (foundMonthIntParssed != desiredMonthIntParssed) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(previousMonthBtnPath)));
					logger.info("PREVIOUS ARROW CLICKED FOR Month ");
					selectedMonthYearTxt = driver.findElement(By.xpath(selectedMonthYearPath)).getText();
					foundYear = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "yyyy");
					foundMonth = Utility.parseDate(selectedMonthYearTxt, "MMMMM yyyy", "MM");
					foundMonthIntParssed = Integer.parseInt(foundMonth);
					logger.info("NEW FOUND MONTH : " + foundMonthIntParssed);
				}
			}
		}

		logger.info("===========SELECTING DESIRED DAY==========");
		// div[@aria-label='Fri May 08 2020']

		driver.findElement(By.xpath(
				"//div[@aria-label='" + Utility.parseDate(desiredDate, desiredDateFormat, "EE MMM dd yyyy") + "']"))
				.click();

		test_steps.add("Selected Date : " + desiredDate);
		logger.info("==========DATE SELECTED FROM DATE PICKER==========");
		test_steps.add("==========DATE SELECTED FROM DATE PICKER==========");

		return Utility.getCustomDate(desiredDate, desiredDateFormat, desiredDateFormat, 19);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRatePlanColor> ' Description: < Get all the Rate Plan
	 * Names Color> ' Input parameters: < WebDriver driver,String test_steps> '
	 * Return value: <ArrayList<String>> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/01/2020>
	 *
	 */

	public ArrayList<String> getRatePlanColor(WebDriver driver) throws InterruptedException {
		ArrayList<String> getColor = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, ORRateGrid.ratePlansColor);
		Utility.ScrollToElement(elements.ratePlansColor.get(0), driver);
		for (int i = 0; i < elements.ratePlansColor.size(); i++) {

			getColor.add(elements.ratePlanNamesList.get(i).getAttribute("aria-label") + "--"
					+ elements.ratePlansColor.get(i).getCssValue("color"));

		}
		rateGridLogger.info("Get the Color of All Rate Plans: " + getColor);
		return getColor;
	}

	public void verifySelectedDateOnDateRangeCalendar(WebDriver driver, String startDate, String endDate, String format,
			ArrayList<String> test_steps) {
		Elements_RatesGrid element = new Elements_RatesGrid(driver);

		String expectedDate = Utility.parseDate(startDate, format, "MMM d") + " - "
				+ Utility.parseDate(endDate, format, "MMM d");

		String foundDate = element.RATE_GRID_CALENDAR_ICON.getText();
		StringTokenizer str = new StringTokenizer(foundDate, "-");
		String actualDate = Utility.parseDate(str.nextToken().trim(), "MMM d", "MMM d") + " - "
				+ Utility.parseDate(str.nextToken().trim(), "MMM d", "MMM d");

		assertEquals(actualDate, expectedDate, "Failed To Verify Selected Date on Date Range Calendar");
		logger.info("Successfully Verified Selected Date : " + actualDate);
		test_steps.add("Successfully Verified Selected Date : " + actualDate);
	}

	public void verifySelectedDateOnHeader(WebDriver driver, String startDate, String endDate, String format,
			ArrayList<String> test_steps) {
		Elements_RatesGrid element = new Elements_RatesGrid(driver);

		String startDateYear = Utility.parseDate(startDate, format, "yyyy");
		logger.info("Start Date Year : " + startDateYear);

		String endDateYear = Utility.parseDate(endDate, format, "yyyy");
		logger.info("End Date Year : " + endDateYear);

		String startDateMonth = Utility.parseDate(startDate, format, "MM");
		logger.info("Start Date Month : " + startDateMonth);

		String endtDateMonth = Utility.parseDate(endDate, format, "MM");
		logger.info("End Date Month : " + endtDateMonth);

		if (startDateYear == endDateYear) {

			if (startDateMonth == endtDateMonth) {
				String foundDate = element.DATE_FROM_GRID_HEADER.get(0).getText();
				assertEquals(Utility.parseDate(foundDate, "MMM, yyyy", "MMM, yyyy"),
						Utility.parseDate(endDate, format, "MMM, yyyy"), "Failed To Verify Grid Header Date");
				logger.info("Successfully Verified Selected Date From Grid Header : " + foundDate);
				test_steps.add("Successfully Verified Selected Date From Grid Header : " + foundDate);
			} else if (Integer.parseInt(startDateMonth) < Integer.parseInt(endtDateMonth)) {

				String foundStartMonth = element.DATE_FROM_GRID_HEADER.get(0).getText();
				assertEquals(Utility.parseDate(foundStartMonth, "MMM, yyyy", "MMM, yyyy"),
						Utility.parseDate(startDate, format, "MMM, yyyy"), "Failed To Verify Grid Header Date");
				logger.info("Successfully Verified Selected Start Date From Grid Header : " + foundStartMonth);
				test_steps.add("Successfully Verified Selected Start Date From Grid Header : " + foundStartMonth);

				String foundEndMonth = element.DATE_FROM_GRID_HEADER.get(1).getText();
				assertEquals(Utility.parseDate(foundEndMonth, "MMM, yyyy", "MMM, yyyy"),
						Utility.parseDate(endDate, format, "MMM, yyyy"), "Failed To Verify Grid Header Date");
				logger.info("Successfully Verified Selected End Date From Grid Header : " + foundEndMonth);
				test_steps.add("Successfully Verified Selected End Date From Grid Header : " + foundEndMonth);
			}

		} else if (Integer.parseInt(startDateYear) < Integer.parseInt(endDateYear)) {
			String foundStartMonth = element.DATE_FROM_GRID_HEADER.get(0).getText();
			assertEquals(Utility.parseDate(foundStartMonth, "MMM, yyyy", "MMM, yyyy"),
					Utility.parseDate(startDate, format, "MMM, yyyy"), "Failed To Verify Grid Header Date");
			logger.info("Successfully Verified Selected Start Date From Grid Header : " + foundStartMonth);
			test_steps.add("Successfully Verified Selected Start Date From Grid Header : " + foundStartMonth);

			String foundEndMonth = element.DATE_FROM_GRID_HEADER.get(1).getText();
			assertEquals(Utility.parseDate(foundEndMonth, "MMM, yyyy", "MMM, yyyy"),
					Utility.parseDate(endDate, format, "MMM, yyyy"), "Failed To Verify Grid Header Date");
			logger.info("Successfully Verified Selected End Date From Grid Header : " + foundEndMonth);
			test_steps.add("Successfully Verified Selected End Date From Grid Header : " + foundEndMonth);
		}

	}

	public void verifyNoOfColumnsRateGrid(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		int foundFirstSize = element.TOTAL_OCCUPANCY_TABLE_DATE_COLUMNS.size();
		int foundSecondSize = element.ROOMCLASSES_TABLE_DATE_COLUMNS.size();

		assertEquals(foundFirstSize, 20, "Failed To Verify Occupacy Table Date Column Size");
		test_steps.add("Successfully Verified Occupacy Table Date Column Size : " + foundFirstSize);
		logger.info("Successfully Verified Occupacy Table Date Column Size : " + foundFirstSize);

		for (int i = 0; i < foundFirstSize; i++) {
			assertTrue(element.TOTAL_OCCUPANCY_TABLE_DATE_COLUMNS.get(i).isDisplayed(),
					"Failed To Verify Occupacy Table Date Column Displayed");
			test_steps.add("Successfully Verified Occupacy Table Date Column Displayed : " + (i + 1));
			logger.info("Successfully Verified Occupacy Table Date Column Displayed : " + (i + 1));
		}

		assertEquals(foundSecondSize, 20, "Failed To Verify RoomClass Table Date Column Size");
		test_steps.add("Successfully Verified RoomClass Table Date Column Size : " + foundFirstSize);
		logger.info("Successfully Verified RoomClass Table Date Column Size : " + foundFirstSize);

		for (int i = 0; i < foundSecondSize; i++) {
			assertTrue(element.ROOMCLASSES_TABLE_DATE_COLUMNS.get(i).isDisplayed(),
					"Failed To Verify RoomClass Table Date Column Displayed");
			test_steps.add("Successfully Verified RoomClass Table Date Column Displayed : " + (i + 1));
			logger.info("Successfully Verified RoomClass Table Date Column Displayed : " + (i + 1));
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickOnAvailabilityTab> ' Description: <This method will
	 * click on availability in rate grid > ' Input parameters: <WebDriver driver> '
	 * Return value : <ArrayList<String> > ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickOnAvailabilityTab(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement_ID(driver, OR.RateGridAvailableButton);
		Wait.waitForElementToBeVisibile(By.id(OR.RateGridAvailableButton), driver);
		Wait.waitForElementToBeClickable(By.id(OR.RateGridAvailableButton), driver);
		elements.RateGridAvailableButton.click();
		testSteps.add("Clicked on availability button");
		rateGridLogger.info("Clicked on availability button");
		return testSteps;
	}

	public void waitForSweetLoading(WebDriver driver) {
		try {
			Wait.waitForElementToBeGone(driver,
					driver.findElement(By.xpath("//div[@class='sweet-loading text-center d-block']")), 5);
		} catch (Exception e) {
			Utility.app_logs.info("No Sweet Loading");
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickOnBulkUpdate> ' Description: <This method will click on
	 * bulkupdate button in rate grid > ' Input parameters: <WebDriver driver> '
	 * Return value : <ArrayList<String> > ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickOnBulkUpdate(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.RateGridBulkUpdateButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.RateGridBulkUpdateButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.RateGridBulkUpdateButton), driver);
		elements.RateGridBulkUpdateButton.click();
		testSteps.add("Clicked on bulk update button");
		rateGridLogger.info("Clicked on bulk update button");
		return testSteps;
	}

	public ArrayList<String> clickRateGridAddRatePlan(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.ADD_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.ADD_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.ADD_RATE_PLAN), driver);
		Utility.ScrollToElement(element.ADD_RATE_PLAN, driver);
		element.ADD_RATE_PLAN.click();
		testSteps.add("RateGrid Add Rate Plan clicked");
		logger.info("RateGrid Add Rate Plan clicked");
		try {

			Wait.waitForElementToBeVisibile(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
		} catch (Exception e) {
			Utility.ScrollToElement(element.ADD_RATE_PLAN, driver);
			element.ADD_RATE_PLAN.click();
			testSteps.add("RateGrid Add Rate Plan again clicked");
			logger.info("RateGrid Add Rate Plan again clicked");
			Wait.waitForElementToBeVisibile(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
		}
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectBulkUpdateOption> ' Description: <This method will
	 * select bulk update options i.e rules, availability in bulk update> ' Input
	 * parameters: <WebDriver driver, String option> ' Return value :
	 * <ArrayList<String> > ' Created By: <Farhan Ghaffar> ' Created On:
	 * <07/13/2020>
	 */

	public ArrayList<String> selectBulkUpdateOption(WebDriver driver, String option) {

		ArrayList<String> testSteps = new ArrayList<>();
		String rateGridBulkUpdateAvailable = "(//li//a[contains(text(),'" + option + "')])[1]";
		Wait.WaitForElement(driver, rateGridBulkUpdateAvailable);
		Wait.waitForElementToBeVisibile(By.xpath(rateGridBulkUpdateAvailable), driver);
		Wait.waitForElementToBeClickable(By.xpath(rateGridBulkUpdateAvailable), driver);
		WebElement rateGirdOption = driver.findElement(By.xpath(rateGridBulkUpdateAvailable));
		rateGirdOption.click();
		testSteps.add("Select " + option + " from bulk update");
		rateGridLogger.info("Select " + option + " from bulk update");
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <startDate> ' Description: <This method will enter start date
	 * in bulk update> ' Input parameters: <WebDriver driver, String date> ' Return
	 * value : <ArrayList<String> > ' Created By: <Farhan Ghaffar> ' Created On:
	 * <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> startDate(WebDriver driver, String date) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.StartDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.StartDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.StartDateInput), driver);
		elements.StartDateInput.click();
		elements.StartDateInput.clear();
		elements.StartDateInput.sendKeys(date);
		testSteps.add("Start date: " + date);
		rateGridLogger.info("Start date: " + date);
		return testSteps;
	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectStartDate> ' Description: <This method will select
	 * start date calendar in bulk update> ' Input parameters: <WebDriver driver,
	 * String startDate> ' Return value : <ArrayList<String> > ' Created By: <Farhan
	 * Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> selectStartDate(WebDriver driver, String startDate)
			throws ParseException, InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.StartDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.StartDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.StartDateInput), driver);
		elements.StartDateInput.click();
		testSteps.add("Click start date");
		rateGridLogger.info("Click start date");

		Utility.selectDateFromDatePicker(driver, startDate, "MM/dd/yyyy");
//		startDate = ESTTimeZone.reformatDate(startDate, "MM/dd/yyyy", "dd/MM/yyyy");
//
//		String monthYear = Utility.get_MonthYear(startDate);
//		rateGridLogger.info("monthYear :" + monthYear);
//		String day = Utility.getDay(startDate);
//		String headerText = null;
//		for (int i = 1; i <= 12; i++) {
//			headerText = elements.StartDateHeader.getText();
//			rateGridLogger.info("headerText : " + headerText);
//
//			if (headerText.equalsIgnoreCase(monthYear)) {
//				String selectedDayPath = "(//label[text()='Start']//following-sibling::div//input//following::div[text()='"
//						+ day + "'])[1]";
//				WebElement dayElement = driver.findElement(By.xpath(selectedDayPath));
//				Wait.WaitForElement(driver, selectedDayPath);
//				dayElement.click();
//				rateGridLogger.info("Selecting start date : " + startDate);
//				testSteps.add("Selecting start date : " + startDate);
//				break;
//			} else {
//				Wait.WaitForElement(driver, OR.NextDateIcon);
//				elements.NextDateIcon.click();
//				rateGridLogger.info("Next Click ");
//				Wait.wait2Second();
//			}
//		}

		return testSteps;
	}

	public ArrayList<String> clickRateGridAddRatePlanOption(WebDriver driver, String option) {

		ArrayList<String> testSteps = new ArrayList<>();
		String optionPath = "(//li//a[contains(text(),'" + option + "')])[1]";
		Wait.WaitForElement(driver, optionPath);
		Wait.waitForElementToBeVisibile(By.xpath(optionPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(optionPath), driver);
		WebElement rateGirdOption = driver.findElement(By.xpath(optionPath));
		rateGirdOption.click();
		testSteps.add("RateGrid Add Rate Plan option clicked : " + option);
		logger.info("RateGrid Add Rate Plan option clicked : " + option);
		return testSteps;
	}

	public static Logger ratesGridLogger = Logger.getLogger("RatesGrid");

	// Added By Adhnan 7/1/2020
	public ArrayList<String> verifyHeadingDates(WebDriver driver, String currentDate, String format, String timeZone,
			ArrayList<String> getTestSteps) throws ParseException, InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		String expectedDayNumber = Utility.addDate(0, format, currentDate, "d", timeZone);
		String expectedWeekDay = Utility.addDate(0, format, currentDate, "E", timeZone);
		String foundDayNumber = null;
		String foundWeekDay = null;
		for (int i = 0; i < 20; i++) {
			ratesGridLogger.info("Date Column: " + i);
			ratesGridLogger.info("Expexted Day Number : " + expectedDayNumber);
			ratesGridLogger.info("Expexted Week Day : " + expectedWeekDay);
			Utility.ScrollToElement(ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(i), driver);
			foundDayNumber = ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(i).getText();
			foundWeekDay = ratesGrid.HEADER_DATES_ROW_WEEKDAY.get(i).getText();
			ratesGridLogger.info("Found Day Number : " + foundDayNumber);
			ratesGridLogger.info("Found Week Day : " + foundWeekDay);
			expectedDayNumber = Utility.addDate(i, format, currentDate, "d", timeZone);
			expectedWeekDay = Utility.addDate(i, format, currentDate, "E", timeZone);
			assertEquals(foundDayNumber, expectedDayNumber, "Failed: Day number missmatched");
			assertEquals(foundWeekDay, expectedWeekDay, "Failed: Week Day missmatched");
		}

		return getTestSteps;
	}

	public ArrayList<String> clickOnAvailability(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();

		Elements_Inventory element = new Elements_Inventory(driver);

		Wait.WaitForElement(driver, OR.CLICK_ON_AVAILABILITY);
		Wait.waitForElementToBeClickable(By.xpath(OR.CLICK_ON_AVAILABILITY), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.CLICK_ON_AVAILABILITY), driver);
		Utility.ScrollToElement(element.clickOnAvailability, driver);
		element.clickOnAvailability.click();
		testSteps.add("Click on Availability");
		ratesGridLogger.info("Click on Availability ");
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectEndDate> ' Description: <This method will select end
	 * date calendar in bulk update> ' Input parameters: <WebDriver driver, String
	 * endDate> ' Return value : <ArrayList<String> > ' Created By: <Farhan Ghaffar>
	 * ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> selectEndDate(WebDriver driver, String endDate)
			throws ParseException, InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.EndDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.EndDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.EndDateInput), driver);
		elements.EndDateInput.click();
		testSteps.add("Click end date");
		rateGridLogger.info("Click end date");

		endDate = ESTTimeZone.reformatDate(endDate, "MM/dd/yyyy", "dd/MM/yyyy");
		String monthYear = Utility.get_MonthYear(endDate);
		rateGridLogger.info("monthYear :" + monthYear);
		String day = Utility.getDay(endDate);
		String headerText = null;
		for (int i = 1; i <= 12; i++) {
			headerText = elements.StartDateHeader.getText();
			rateGridLogger.info("headerText : " + headerText);

			if (headerText.equalsIgnoreCase(monthYear)) {
				Wait.wait2Second();
//				Wait.WaitForElement(driver, OR.NextDateIcon);
//				elements.NextDateIcon.click();
//				rateGridLogger.info("Next Click ");

				String selectedDayPath = "(//label[text()='End']//following-sibling::div//input//following::div[text()='"
						+ day + "'])[1]";
				WebElement dayElement = driver.findElement(By.xpath(selectedDayPath));
				Wait.WaitForElement(driver, selectedDayPath);
				dayElement.click();
				rateGridLogger.info("Selecting end date : " + endDate);
				testSteps.add("Selecting end date : " + endDate);
				break;
			} else {
				Wait.WaitForElement(driver, OR.NextDateIcon);
				elements.NextDateIcon.click();
				rateGridLogger.info("Next Click ");
				Wait.wait2Second();
			}
		}

		return testSteps;
	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyStartDatePopup> ' Description: <This method will verify
	 * start date calendar in bulk update> ' Input parameters: <WebDriver driver,
	 * String startDateDate> ' Return value : <ArrayList<String> > ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyStartDateCalendar(WebDriver driver, String startDate)
			throws InterruptedException, ParseException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.StartDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.StartDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.StartDateInput), driver);
		elements.StartDateInput.click();
		testSteps.add("Click start date");
		rateGridLogger.info("Click start date");

		rateGridLogger.info("==========VERIFYING MONTH YEAR HEADING IN CALENDAR==========");
		testSteps.add("==========VERIFYING MONTH YEAR HEADING IN CALENDAR==========");
		startDate = ESTTimeZone.reformatDate(startDate, "MM/dd/yyyy", "dd/MM/yyyy");

		String monthYear = Utility.get_MonthYear(startDate);
		String header = "(//label[text()='Start']//following-sibling::div//input//following::div[text()='" + monthYear
				+ "'])[1]";
		WebElement headerElement = driver.findElement(By.xpath(header));
		rateGridLogger.info("Expected  : " + monthYear);
		testSteps.add("Expected Day : " + monthYear);

		String getHeader = headerElement.getText();
		rateGridLogger.info("Found : " + getHeader);
		testSteps.add("Found : " + getHeader);

		Assert.assertEquals(getHeader, monthYear, "Failed : Header MonthYear doesn't match");
		testSteps.add("Verified month year heading in start date calendar");
		rateGridLogger.info("Verified month year heading in start date calendar");

		rateGridLogger.info("==========VERIFYING DEFAULT SELECTED DAY==========");
		testSteps.add("==========VERIFYING DEFAULT SELECTED DAY==========");

		String day = Utility.getDay(startDate);
		rateGridLogger.info("Expected Day : " + day);
		testSteps.add("Expected Day : " + day);

		String selectedDay = elements.StartDateSelectedDay.getText();
		rateGridLogger.info("Found : " + selectedDay);
		testSteps.add("Found : " + selectedDay);

		Assert.assertEquals(day, selectedDay, "Failed to match selected day");
		testSteps.add("Verified selected day");
		rateGridLogger.info("Verified selected day");

		Assert.assertTrue(elements.TodayDateButton.isDisplayed(), "Failed today button doesn't exist");
		testSteps.add("Verified that today button is displaying at the footer of start date calendar");
		rateGridLogger.info("Verified that today button is displaying at the footer of start date calendar");

		elements.StartDateInput.sendKeys(Keys.TAB);
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getStartDate> ' Description: <This method will return start
	 * date in bulk update> ' Input parameters: <WebDriver driver> ' Return value :
	 * <String> ' Created By: <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getStartDate(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.StartDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.StartDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.StartDateInput), driver);
		String date = elements.StartDateInput.getAttribute("value");
		rateGridLogger.info("Start date: " + date);
		return date;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <endDate> ' Description: <This method will enter end date in
	 * bulk update> ' Input parameters: <WebDriver driver, String date> ' Return
	 * value : <ArrayList<String> > ' Created By: <Farhan Ghaffar> ' Created On:
	 * <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> endDate(WebDriver driver, String date) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.EndDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.EndDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.EndDateInput), driver);
		elements.EndDateInput.click();
		elements.EndDateInput.clear();
		elements.EndDateInput.sendKeys(date);
		elements.EndDateInput.sendKeys(Keys.TAB);
		testSteps.add("End date: " + date);
		rateGridLogger.info("End date: " + date);
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyEndDateCalendar> ' Description: <This method will
	 * verify end date calendar in bulk update> ' Input parameters: <WebDriver
	 * driver, String endDate> ' Return value : <ArrayList<String> > ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <endDate> ' Description: <This method will enter end date in
	 * bulk update> ' Input parameters: <WebDriver driver, String date> ' Return
	 * value : <ArrayList<String> > ' Created By: <Farhan Ghaffar> ' Created On:
	 * <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyEndDateCalendar> ' Description: <This method will
	 * verify end date calendar in bulk update> ' Input parameters: <WebDriver
	 * driver, String endDate> ' Return value : <ArrayList<String> > ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyEndDateCalendar(WebDriver driver, String endDate)
			throws InterruptedException, ParseException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.EndDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.EndDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.EndDateInput), driver);
		elements.EndDateInput.click();
		rateGridLogger.info("==========VERIFYING MONTH YEAR HEADING IN CALENDAR==========");
		testSteps.add("==========VERIFYING MONTH YEAR HEADING IN CALENDAR==========");

		endDate = ESTTimeZone.reformatDate(endDate, "MM/dd/yyyy", "dd/MM/yyyy");
		String monthYear = Utility.get_MonthYear(endDate);
		rateGridLogger.info("Expected  : " + monthYear);
		testSteps.add("Expected Day : " + monthYear);

		String header = "(//label[text()='End']//following-sibling::div//input//following::div[text()='" + monthYear
				+ "'])[1]";
		WebElement headerElement = driver.findElement(By.xpath(header));
		String getHeader = headerElement.getText();
		rateGridLogger.info("Found : " + getHeader);
		testSteps.add("Found : " + getHeader);

		Assert.assertEquals(getHeader, monthYear, "Failed : Header MonthYear doesn't match");
		testSteps.add("Verified month year heading in end date calendar");
		rateGridLogger.info("Verified month year heading in end date calendar");

		rateGridLogger.info("==========VERIFYING DEFAULT SELECTED DAY==========");
		testSteps.add("==========VERIFYING DEFAULT SELECTED DAY==========");

		String day = Utility.getDay(endDate);
		rateGridLogger.info("Expected Day : " + day);
		testSteps.add("Expected Day : " + day);

		String selectedDay = elements.EndDateSelectedDay.getText();
		rateGridLogger.info("Found : " + selectedDay);
		testSteps.add("Found : " + selectedDay);

		Assert.assertEquals(day, selectedDay, "Failed to match selected day");
		testSteps.add("Verified selected day");
		rateGridLogger.info("Verified selected day");

		Assert.assertTrue(elements.TodayDateButton.isDisplayed(), "Failed today button doesn't exist");
		testSteps.add("Verified that today button is displaying at the footer of calendar");
		rateGridLogger.info("Verified that today button is displaying at the footer of calendar");

		elements.EndDateInput.sendKeys(Keys.TAB);
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getEndDate> ' Description: <This method will return end date
	 * in bulk update> ' Input parameters: <WebDriver driver> ' Return value :
	 * <String> ' Created By: <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getEndDate(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.EndDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.EndDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.EndDateInput), driver);
		String date = elements.EndDateInput.getAttribute("value");
		rateGridLogger.info("End date: " + date);
		return date;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyAvailabilityHeading> ' Description: <This method will
	 * verofy availability popup heading> ' Input parameters: <WebDriver driver> '
	 * Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyAvailabilityHeading(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.AvailabilityHeading);
		Wait.waitForElementToBeVisibile(By.xpath(OR.AvailabilityHeading), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.AvailabilityHeading), driver);
		elements.AvailabilityHeading.click();
		assertTrue(elements.AvailabilityHeading.isDisplayed(),
				" Bulk update availability popup heading is not displaying");

		testSteps.add("Verified availability popup heading is displaying");
		rateGridLogger.info("Verified availability popup heading is displaying");
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <BulkUpdatePopupText> ' Description: <This method will return
	 * text present below heading in bulk update> ' Input parameters: <WebDriver
	 * driver> ' Return value : <String> ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getBulkUpdatePoppupText(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.BulkUpdatePopupText);
		Wait.waitForElementToBeVisibile(By.xpath(OR.BulkUpdatePopupText), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.BulkUpdatePopupText), driver);
		String text = elements.BulkUpdatePopupText.getText();
		rateGridLogger.info("text : " + text);
		return text;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <checkDays> ' Description: <This method will check whether day
	 * checkbox in bulk update> ' Input parameters: <WebDriver driver, String day,
	 * String isChecked> ' Return value : <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> checkDays(WebDriver driver, String day, String isChecked) throws InterruptedException {

		String daysCheckBox = "//span[text()='" + day + "']/following-sibling::span";
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, daysCheckBox);
		Wait.waitForElementToBeVisibile(By.xpath(daysCheckBox), driver);
		Wait.waitForElementToBeClickable(By.xpath(daysCheckBox), driver);
		WebElement daysElement = driver.findElement(By.xpath(daysCheckBox));
		Utility.ScrollToElement_NoWait(daysElement, driver);

		if (isChecked.equalsIgnoreCase("yes")) {
			testSteps.add(day + " checkbox checked");
			rateGridLogger.info(day + " checkbox checked");
		} else if (isChecked.equalsIgnoreCase("no")) {
			daysElement.click();
			testSteps.add(day + " checkbox unchecked");
			rateGridLogger.info(day + " checkbox unchecked");
		}
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyDaysCheckbox> ' Description: <This method will check
	 * whether day checkbox is displaying or not in bulk update> ' Input parameters:
	 * <WebDriver driver, String day> ' Return value : <ArrayList<String>> ' Created
	 * By: <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyDaysCheckbox(WebDriver driver, String day) throws InterruptedException {

		String daysCheckBox = "//span[text()='" + day + "']//following-sibling::span";
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, daysCheckBox);
		Wait.waitForElementToBeVisibile(By.xpath(daysCheckBox), driver);
		Wait.waitForElementToBeClickable(By.xpath(daysCheckBox), driver);
		WebElement daysElement = driver.findElement(By.xpath(daysCheckBox));

		Assert.assertTrue(daysElement.isDisplayed(), "Failed : " + day + " checkbox didn't display");
		testSteps.add("Verified " + day + " checkbox is displaying");
		rateGridLogger.info("Verified " + day + " checkbox is displaying");
		return testSteps;
	}

	public String getBulkUpdateButtonText(WebDriver driver) {

		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.BULK_UPDATE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR.BULK_UPDATE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.BULK_UPDATE_BUTTON), driver);
		String getbulkUpdateText = element.bulkUpdateButton.getText();

		return getbulkUpdateText;
	}

	public String getDayTabText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.DAY_TAB_TEXT);
		Wait.waitForElementToBeVisibile(By.xpath(OR.DAY_TAB_TEXT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.DAY_TAB_TEXT), driver);
		String dayTabText = element.dayTabText.getText();

		return dayTabText;
	}

	public String getTotalOccupancyTabText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.TOTAL_OCCUPANCY_TAB_TEXT);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TOTAL_OCCUPANCY_TAB_TEXT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TOTAL_OCCUPANCY_TAB_TEXT), driver);
		String totalOccupancyTabText = element.totalOccupancyTabText.getText();

		return totalOccupancyTabText;
	}

	public String getPaceVsLastYearTabText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.PACE_VS_LAST_YEAR_TAB_TEXT);
		Wait.waitForElementToBeVisibile(By.xpath(OR.PACE_VS_LAST_YEAR_TAB_TEXT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.PACE_VS_LAST_YEAR_TAB_TEXT), driver);
		String paceVsLastYearTabText = element.paceVsLastYearTabText.getText();

		return paceVsLastYearTabText;
	}

	public String getAddRatePlanButtonText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.ADD_RATE_PLAN_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ADD_RATE_PLAN_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ADD_RATE_PLAN_BUTTON), driver);
		String addRatePlanButton = element.addRatePlanButton.getText();

		return addRatePlanButton;
	}

	public ArrayList<String> clickOnAddRatePlan(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.ADD_RATE_PLAN_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ADD_RATE_PLAN_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ADD_RATE_PLAN_BUTTON), driver);
		element.addRatePlanButton.click();
		testSteps.add("Click add rate plan button");

		return testSteps;
	}

	public String getNightlyRatePlanText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.NIGHTLY_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
		String nightlyRatePlan = element.nightlyRatePlan.getText();

		return nightlyRatePlan;
	}

	public ArrayList<String> clickNightlyRatePlan(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.NIGHTLY_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.NIGHTLY_RATE_PLAN), driver);
		element.nightlyRatePlan.click();
		testSteps.add("Click on nightly rate plan");

		return testSteps;
	}

	public String getDrivedRatePlanText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.DERIVED_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.DERIVED_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.DERIVED_RATE_PLAN), driver);
		String drivedRatePlan = element.drivedRatePlan.getText();

		return drivedRatePlan;
	}

	public ArrayList<String> clickDrivedRatePlan(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.DERIVED_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.DERIVED_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.DERIVED_RATE_PLAN), driver);
		element.drivedRatePlan.click();
		testSteps.add("Click on drived rate plan");

		return testSteps;
	}

	public String getPackageRatePlanText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.PACKAGE_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.PACKAGE_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.PACKAGE_RATE_PLAN), driver);
		String packageRatePlan = element.packageRatePlan.getText();

		return packageRatePlan;
	}

	public ArrayList<String> clickPackageRatePlan(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.PACKAGE_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.PACKAGE_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.PACKAGE_RATE_PLAN), driver);
		element.packageRatePlan.click();
		testSteps.add("Click on pakage rate plan");

		return testSteps;
	}

	public String getIntervalRatePlanText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.INTERVAL_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.INTERVAL_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.INTERVAL_RATE_PLAN), driver);
		String intervalRatePlan = element.intervalRatePlan.getText();

		return intervalRatePlan;
	}

	public ArrayList<String> clickIntervalRatePlan(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.INTERVAL_RATE_PLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR.INTERVAL_RATE_PLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.INTERVAL_RATE_PLAN), driver);
		element.intervalRatePlan.click();
		testSteps.add("Click interval rate plan");

		return testSteps;
	}

	public String getRatesBulkUpdateText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.SELECT_RATES_BULK_UPDATE);
		Wait.waitForElementToBeVisibile(By.xpath(OR.SELECT_RATES_BULK_UPDATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.SELECT_RATES_BULK_UPDATE), driver);
		String ratesBulkUpdate = element.selectRatesBulkUpdate.getText();

		return ratesBulkUpdate;
	}

	public ArrayList<String> clickRatesBulkUpdate(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.SELECT_RATES_BULK_UPDATE);
		Wait.waitForElementToBeVisibile(By.xpath(OR.SELECT_RATES_BULK_UPDATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.SELECT_RATES_BULK_UPDATE), driver);
		element.selectRatesBulkUpdate.click();
		testSteps.add("Click Rates in bulk update");

		return testSteps;
	}

	public String getRulesBulkUpdateText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.SELECT_RULES_BULK_UPDATE);
		Wait.waitForElementToBeVisibile(By.xpath(OR.SELECT_RULES_BULK_UPDATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.SELECT_RULES_BULK_UPDATE), driver);
		String rulesBulkUpdate = element.selectRulesBulkUpdate.getText();

		return rulesBulkUpdate;
	}

	public ArrayList<String> clickRulesBulkUpdate(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.SELECT_RULES_BULK_UPDATE);
		Wait.waitForElementToBeVisibile(By.xpath(OR.SELECT_RULES_BULK_UPDATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.SELECT_RULES_BULK_UPDATE), driver);
		element.selectRulesBulkUpdate.click();
		testSteps.add("Click rules in bulk update");

		return testSteps;
	}

	public String getAvailabilityBulkUpdateText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.SELECT_AVAILABILITY_BULK_UPDATE);
		Wait.waitForElementToBeVisibile(By.xpath(OR.SELECT_AVAILABILITY_BULK_UPDATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.SELECT_AVAILABILITY_BULK_UPDATE), driver);
		String availabilityText = element.selectAvailability.getText();

		return availabilityText;
	}

	public ArrayList<String> selectAvailabilityFromBulkUpdate(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.SELECT_AVAILABILITY_BULK_UPDATE);
		Wait.waitForElementToBeVisibile(By.xpath(OR.SELECT_AVAILABILITY_BULK_UPDATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.SELECT_AVAILABILITY_BULK_UPDATE), driver);
		element.selectAvailability.click();
		testSteps.add("Availability selected");

		return testSteps;
	}

	public ArrayList<String> closeOpendTabInMainMenu(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.MAIN_MENU_CLOSE_BUTTON);
		// Wait.waitForElementToBeVisibile(By.xpath(OR.MAIN_MENU_CLOSE_BUTTON), driver);
		// Wait.waitForElementToBeClickable(By.xpath(OR.MAIN_MENU_CLOSE_BUTTON),
		// driver);
		// element.mainMenueCloseButton.click();
		Utility.clickThroughJavaScript(driver, element.mainMenueCloseButton);
		testSteps.add("Close opened tab");

		try {
			String path = "//div[contains(@class,'ConfirmationModalDialog')]//button/span[text()='Yes']";

			if (driver.findElements(By.xpath(path)).size() > 0) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
			}
		} catch (Exception e) {
			Utility.app_logs.info("You have not unsaved data found");
		}
		driver.navigate().refresh();
		return testSteps;
	}

	public String getNewRatePlanTabTitleText(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.NEW_RATE_PLAN_TAB_TITLE);
		Wait.waitForElementToBeVisibile(By.xpath(OR.NEW_RATE_PLAN_TAB_TITLE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.NEW_RATE_PLAN_TAB_TITLE), driver);
		String nightlyRatePlanTabTitle = element.newRatePlanTabTitle.getText();

		return nightlyRatePlanTabTitle;
	}

	public ArrayList<String> closeRateBulkUpdatePopup(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.CLOSE_RATE_BULK_UPDATE_POPUP);
		Wait.waitForElementToBeVisibile(By.xpath(OR.CLOSE_RATE_BULK_UPDATE_POPUP), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.CLOSE_RATE_BULK_UPDATE_POPUP), driver);
		element.closeRateBulkUpdatePopup.click();
		testSteps.add("Close rate bulk update popup");

		return testSteps;
	}

	public String getBulkUpdateHeaderTitle(WebDriver driver) {
		Elements_Inventory element = new Elements_Inventory(driver);
		Wait.WaitForElement(driver, OR.BULK_UPDATE_HEADER);
		Wait.waitForElementToBeVisibile(By.xpath(OR.BULK_UPDATE_HEADER), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.BULK_UPDATE_HEADER), driver);
		String heading = element.bulkUpdateHeader.getText();

		return heading;
	}

	// Added By Adhnan 7/1/2020
	public ArrayList<String> clickCalendarArrow(WebDriver driver, String arrow) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String xpath = "//span[@class='" + arrow + "-arrow']//parent::button";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		WebElement arrowButton = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(arrowButton, driver);
		arrowButton.click();
		testSteps.add("Click " + arrow + " arrow of Calendar");
		ratesGridLogger.info("Click " + arrow + " arrow of Calendar");

		return testSteps;
	}

	// Added By Adhnan 7/1/2020
	public ArrayList<String> calculateMonthRange(WebDriver driver, String currentDate, String format, String timeZone)
			throws ParseException, InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		String monthValue = Utility.addDate(0, format, currentDate, "MMMM, yyyy", timeZone);
		String nextmonthValue = null;
		ArrayList<String> monthRange = new ArrayList<String>();
		if (monthValue.contains("June") || monthValue.contains("July")) {
			monthRange.add(monthValue);
		} else {
			monthRange.add(Utility.parseDate(monthValue, "MMMM, yyyy", "MMM, yyyy"));
		}
		ratesGridLogger.info("Current month : " + monthValue);
		for (int i = 0; i < 20; i++) {
			nextmonthValue = Utility.addDate(i, format, currentDate, "MMMM, yyyy", timeZone);
			if (!monthValue.equals(nextmonthValue)) {
				monthValue = nextmonthValue;
				if (monthValue.contains("June") || monthValue.contains("July")) {

					ratesGridLogger.info("Setting  month : " + monthValue);
					monthRange.add(monthValue);
				} else {
					ratesGridLogger

							.info("Setting  month : " + Utility.parseDate(monthValue, "MMMM, yyyy", "MMM, yyyy"));

					monthRange.add(Utility.parseDate(monthValue, "MMMM, yyyy", "MMM, yyyy"));
				}
				monthRange.add(monthValue);
				ratesGridLogger.info("Next month : " + monthValue);
			}
		}
		return monthRange;
	}

	// Added By Adhnan 7/1/2020
	public void verifyMonthRange(WebDriver driver, ArrayList<String> monthRange)
			throws ParseException, InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.DATE_RANGE_MONTH_YEAR);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.DATE_RANGE_MONTH_YEAR), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.DATE_RANGE_MONTH_YEAR), driver);
		String expectedMonth = monthRange.get(0);
		String foundMonth = null;
		for (int i = 0; i < ratesGrid.DATE_RANGE_MONTH_YEAR.size(); i++) {
			ratesGridLogger.info("Month : " + i);
			expectedMonth = monthRange.get(i);
			ratesGridLogger.info("Expexted month : " + expectedMonth);
			Utility.ScrollToElement(ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(i), driver);
			foundMonth = ratesGrid.DATE_RANGE_MONTH_YEAR.get(i).getText();
			ratesGridLogger.info("Found month : " + foundMonth);
			assertEquals(foundMonth, expectedMonth, "Failed:month missmatched");
		}
	}

	// Added By Adhnan 7/1/2020
	public ArrayList<String> clickCalendar(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.DATE_RANGE_CALENDAR);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.DATE_RANGE_CALENDAR), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.DATE_RANGE_CALENDAR), driver);
		Utility.ScrollToElement(ratesGrid.DATE_RANGE_CALENDAR, driver);
		ratesGrid.DATE_RANGE_CALENDAR.click();
		testSteps.add("Click Date Range Calendar");
		ratesGridLogger.info("Click date range Calendar");
		try {
			Wait.WaitForElement(driver, OR_RatesGrid.CALENDER_TODAY_BUTTON);
			Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.CALENDER_TODAY_BUTTON), driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.CALENDER_TODAY_BUTTON), driver);
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, ratesGrid.DATE_RANGE_CALENDAR);
			testSteps.add("Click Date Range Calendar");
			ratesGridLogger.info("Click date range Calendar");
			Wait.WaitForElement(driver, OR_RatesGrid.CALENDER_TODAY_BUTTON);
			Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.CALENDER_TODAY_BUTTON), driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.CALENDER_TODAY_BUTTON), driver);
		}
		return testSteps;
	}

	// Added By Adhnan 7/1/2020
	public ArrayList<String> verifyTodayButtonExistInCalender(WebDriver driver, boolean click)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.CALENDER_TODAY_BUTTON);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.CALENDER_TODAY_BUTTON), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.CALENDER_TODAY_BUTTON), driver);
		assertTrue(ratesGrid.CALENDER_TODAY_BUTTON.isDisplayed(), "Failed: Today, Button is not displayed");
		testSteps.add("Verified 'Today Button' is visible with in calendar");
		ratesGridLogger.info("Verified 'Today Button' is visible with in calendar");
		if (click) {
			Utility.ScrollToElement(ratesGrid.CALENDER_TODAY_BUTTON, driver);
			ratesGrid.CALENDER_TODAY_BUTTON.click();
			testSteps.add("Click 'Today Button' of Calendar");
			ratesGridLogger.info("Click 'Today Button' of Calendar");
		}
		return testSteps;
	}

	// Added By Adhnan 7/1/2020
	public ArrayList<String> selectCalendarNextDate(WebDriver driver, String nextDays) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String xpath = "(//div[contains(@class,'selectedDays')]//following-sibling::div)[" + nextDays + "]";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		WebElement nextDate = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(nextDate, driver);
		nextDate.click();
		testSteps.add("Select '" + nextDays + "' next Day of selected Date of Calendar");
		ratesGridLogger.info("Select '" + nextDays + "' next Day of selected Date of Calendar");

		return testSteps;
	}

	// Added By Adhnan 7/1/2020
	public boolean getElementVisibility(WebDriver driver, WebElement element) throws InterruptedException {
		boolean visible = element.isDisplayed();
		ratesGridLogger.info("Element visibility : " + visible);
		return visible;
	}

	// Added By Adhnan 7/1/2020
	public boolean verifyTotalOccupancyLabelExist(WebDriver driver) {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.TOTAL_OCCUPANCY_LABEL);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.TOTAL_OCCUPANCY_LABEL), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.TOTAL_OCCUPANCY_LABEL), driver);
		assertTrue(ratesGrid.TOTAL_OCCUPANCY_LABEL.isDisplayed(), "Failed: Total Occupancy Label not found");
		return ratesGrid.TOTAL_OCCUPANCY_LABEL.isDisplayed();
	}

	// Added By Adhnan 7/1/2020
	public boolean verifyPaceVsLastYearLabelExist(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.PACE_VS_LAST_YEAR_LABEL);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.PACE_VS_LAST_YEAR_LABEL), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.PACE_VS_LAST_YEAR_LABEL), driver);
		assertTrue(ratesGrid.PACE_VS_LAST_YEAR_LABEL.isDisplayed(), "Failed: Pace Vs Last Year Label not found");

		return ratesGrid.PACE_VS_LAST_YEAR_LABEL.isDisplayed();
	}

	// Added By Adhnan 7/2/2020
	public void clickHeadingDate(WebDriver driver, int index, String date) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		Utility.ScrollToElement(ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(index), driver);
		ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(index).click();
		String xpath = "//h1[@class='undefined hide-mobile' and text()='" + date + " Insights']";
		ratesGridLogger.info(xpath);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-400)");
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		assertTrue(driver.findElement(By.xpath(xpath)).isDisplayed(), "Failed: Month Date Insight side bar not appear");
	}

	// Added By Adhnan 7/1/2020
	public ArrayList<String> closeMonthDateIndightSideBar(WebDriver driver, String date, ArrayList<String> getTestSteps)
			throws InterruptedException {

		String xpath = "//h1[@class='undefined hide-mobile' and text()='" + date + " Insights']/i";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		WebElement closeButton = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(closeButton, driver);
		closeButton.click();
		getTestSteps.add("Click Close button of Insight Side Bar");
		ratesGridLogger.info("Click Close button of Insight Side Bar");
		getTestSteps.add(date + " Insight Side Bar successfully closed.");
		ratesGridLogger.info(date + " Insight Side Bar successfully closed.");
		return getTestSteps;
	}

	public void verifyRoomClasses(WebDriver driver, ArrayList<String> getRoomClasses) throws InterruptedException {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ROOM_CLASSES_NAMES);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		String expectedRoomClass = null;
		String foundRoomClass = null;
		String previousRoomClass = null;
		boolean inorder = true;
		for (int i = 0; i < getRoomClasses.size(); i++) {
			ratesGridLogger.info("Room Class " + (i + 1));
			expectedRoomClass = getRoomClasses.get(i);
			ratesGridLogger.info("Expected Room Class : " + expectedRoomClass);
			Utility.ScrollToElement(ratesGrid.ROOM_CLASSES_NAMES.get(i), driver);
			foundRoomClass = ratesGrid.ROOM_CLASSES_NAMES.get(i).getText();
			ratesGridLogger.info("Found Room Class : " + foundRoomClass);
			assertEquals(foundRoomClass, expectedRoomClass, "Failed: Room Class missmatched");
			if (previousRoomClass == null) {
				previousRoomClass = foundRoomClass;
			} else {
				if (!(previousRoomClass.charAt(0) <= foundRoomClass.charAt(0))) {
					inorder = false;
				}
			}
			previousRoomClass = foundRoomClass;

		}

	}

	public void expandRoomClass(WebDriver driver, int index) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, "(" + OR_RatesGrid.ROOM_CLASS_EXPAND_BUTTON + ")[" + (index + 1) + "]");
		Wait.waitForElementToBeClickable(
				By.xpath("(" + OR_RatesGrid.ROOM_CLASS_EXPAND_BUTTON + ")[" + (index + 1) + "]"), driver);
		Wait.waitForElementToBeVisibile(
				By.xpath("(" + OR_RatesGrid.ROOM_CLASS_EXPAND_BUTTON + ")[" + (index + 1) + "]"), driver);
		Utility.ScrollToElement(ratesGrid.ROOM_CLASS_EXPAND_BUTTON.get(index), driver);
		try {
			ratesGrid.ROOM_CLASS_EXPAND_BUTTON.get(index).click();
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-400)");
			ratesGrid.ROOM_CLASS_EXPAND_BUTTON.get(index).click();
		}
	}

	public String getRoomClassDataValue(WebDriver driver, int index, String label) throws InterruptedException {
		String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='" + label
				+ "']//parent::div//following-sibling::div/div[1])";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		return labelValues.get(index).getText();
	}

	public ArrayList<String> clickAndVerifySettingButton(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.SETTING_BUTTON_RATE_GRID);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.SETTING_BUTTON_RATE_GRID), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.SETTING_BUTTON_RATE_GRID), driver);
		assertEquals(element.settingButtonRateGrid.isEnabled(), true);
		testSteps.add("Setting button is clickable");
		ratesGridLogger.info("Setting button is clickable");
		element.settingButtonRateGrid.click();
		return testSteps;
	}

	public ArrayList<String> clickAndVerifyAddRatePlanButton(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ADD_RATE_PLAN_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ADD_RATE_PLAN_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ADD_RATE_PLAN_BUTTON), driver);
		assertEquals(element.addRatePlanButton.isEnabled(), true);
		testSteps.add("Add rate plan button is clickable");
		ratesGridLogger.info("Add rate plan button is clickable");
		element.addRatePlanButton.click();
		return testSteps;
	}

	public ArrayList<String> clickAndVerifyBulkUpdateButton(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.BULK_UPDATE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.BULK_UPDATE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.BULK_UPDATE_BUTTON), driver);
		assertEquals(element.bulkUpdateButton.isEnabled(), true);
		testSteps.add("Bulk update button is clickable");
		ratesGridLogger.info("Bulk update button is clickable");
		element.bulkUpdateButton.click();
		return testSteps;
	}

	public ArrayList<String> verifyHeadingAvailabilitySettingMenu(WebDriver driver, String expectedHeading) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADING_AVAILABILITY_SETTING);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADING_AVAILABILITY_SETTING), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADING_AVAILABILITY_SETTING), driver);
		String getHeadingText = element.headingAvailabilitySetting.getText();
		testSteps.add("Expected : " + expectedHeading);
		testSteps.add("Found : " + getHeadingText);
		assertEquals(getHeadingText, expectedHeading);
		testSteps.add("Verified heading is : " + getHeadingText);
		return testSteps;
	}

	public ArrayList<String> verifyHeadingRatesSettingMenu(WebDriver driver, String expectedHeading) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADING_RATES_SETTING);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADING_RATES_SETTING), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADING_RATES_SETTING), driver);
		String getHeadingText = element.headingRatesSetting.getText();
		testSteps.add("Expected : " + expectedHeading);
		testSteps.add("Found : " + getHeadingText);
		assertEquals(getHeadingText, expectedHeading);
		testSteps.add("Verified heading is : " + getHeadingText);
		return testSteps;
	}

	public ArrayList<String> verifyAvailabilityToggleTextSettingMenu(WebDriver driver, String expectedText) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.AVAILABILITY_TOGGLE_TEXT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.AVAILABILITY_TOGGLE_TEXT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.AVAILABILITY_TOGGLE_TEXT), driver);
		String getText = element.availabiltyToggleText.getText();
		testSteps.add("Expected : " + expectedText);
		testSteps.add("Found : " + getText);
		assertEquals(getText, expectedText);
		testSteps.add("Verified text is : " + getText);
		return testSteps;
	}

	public ArrayList<String> verifyRatesToggleTextSettingMenu(WebDriver driver, String expectedText) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.RATES_TOGGLE_TEXT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.RATES_TOGGLE_TEXT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.RATES_TOGGLE_TEXT), driver);
		String getText = element.ratesToggleText.getText();
		testSteps.add("Expected : " + expectedText);
		testSteps.add("Found : " + getText);
		assertEquals(getText, expectedText);
		testSteps.add("Verified text is : " + getText);
		return testSteps;
	}

	public ArrayList<String> verifyToggleButtonAvailablity(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.SETTING_AVAILABILITY_TOGGLE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.SETTING_AVAILABILITY_TOGGLE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.SETTING_AVAILABILITY_TOGGLE_BUTTON), driver);
		if (element.settingAvailabilityToggleButton.isEnabled()) {
			testSteps.add("Toggle Button is verified");
			ratesGridLogger.info("Toggle Button is verified");

		} else {
			element.settingAvailabilityToggleButton.click();
			testSteps.add("Toggle Button is verified");
			ratesGridLogger.info("Toggle Button is verified");
		}
		return testSteps;
	}

	public ArrayList<String> verifyToggleButtonRates(WebDriver driver) {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.SETTING_RATES_TOGGLE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.SETTING_RATES_TOGGLE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.SETTING_RATES_TOGGLE_BUTTON), driver);
		if (element.settingRatesToggleButton.isEnabled()) {
			testSteps.add("Toggle Button is verified");
			ratesGridLogger.info("Toggle Button is verified");

		} else {
			element.settingRatesToggleButton.click();
			testSteps.add("Toggle Button is verified");
			ratesGridLogger.info("Toggle Button is verified");
		}
		return testSteps;
	}

	// Added By Adhnan 7/3/2020
	public ArrayList<String> verifyDatesBackGroungColor(WebDriver driver, String weekendColor, String weekDayColor,
			ArrayList<String> getTestSteps) throws ParseException, InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADER_DATES_BACKGROUND_COLOR);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADER_DATES_BACKGROUND_COLOR), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADER_DATES_BACKGROUND_COLOR), driver);
		String color = null;
		String foundWeekDay = null;
		for (int i = 0; i < 20; i++) {
			ratesGridLogger.info("Date Column: " + i);
			Utility.ScrollToElement(ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(i), driver);
			foundWeekDay = ratesGrid.HEADER_DATES_ROW_WEEKDAY.get(i).getText();
			ratesGridLogger.info("Found Week Day : " + foundWeekDay);
			if (foundWeekDay.equals("Fri") || foundWeekDay.equals("Sat")) {
				ratesGridLogger.info("Weekend Found");
				Utility.ScrollToElement(ratesGrid.HEADER_DATES_BACKGROUND_COLOR.get(i), driver);
				color = ratesGrid.HEADER_DATES_ROW_WEEKDAY.get(i).getCssValue("background-color");
				ratesGridLogger.info("Cell Color : " + color);
				color = ratesGrid.HEADER_DATES_BACKGROUND_COLOR.get(i).getCssValue("background-color");
				ratesGridLogger.info("Cell Color : " + color);
				assertEquals(color, weekendColor, "Failed: Week Day Color missmatched");
			} else {
				color = ratesGrid.HEADER_DATES_ROW_WEEKDAY.get(i).getCssValue("background-color");
				ratesGridLogger.info("Cell Color : " + color);
				color = ratesGrid.HEADER_DATES_BACKGROUND_COLOR.get(i).getCssValue("background-color");
				ratesGridLogger.info("Cell Color : " + color);
				assertEquals(color, weekDayColor, "Failed: Week Day Color missmatched");
			}
		}

		return getTestSteps;
	}

	// Added By Adhnan 7/7/2020
	public void changeRoomStatus(WebDriver driver, int index, String source, String roomClassName, String status)
			throws InterruptedException {
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='" + source
				+ "']//parent::div/following-sibling::div";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		String roomStatus = labelValues.get(index).getAttribute("class");
		Utility.app_logs.info("Initial room Status : " + roomStatus);
		if (roomStatus.contains("NoBlacked")) {
			roomStatus = "*";
		} else {
			roomStatus = "B";
		}
		if (roomStatus.equals(status)) {
			Utility.app_logs.info("Room is already in reguired State");
		} else {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-400)");
			labelValues.get(index).click();
			try {
				Wait.waitForElementToBeGone(driver, driver.findElement(By.xpath("//div[@class='css-kwlyb4']")), 120);
			} catch (Exception e) {

			}
		}
		Utility.app_logs.info("Initial room Status : " + roomStatus);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ######################## ' Method Name:
	 * <verifySelectedDateOnDateRangeCalendar> ' Description: <This method will
	 * Verify Selected Date on Date Rage Calendar > ' Input parameters: < WebDriver
	 * driver,String startDate, String endDate,String format, ArrayList<String>
	 * test_steps > ' Return value: <void> ' Created By: <Muhammad Haider> ' Created
	 * On: <MM/dd/yyyy> <07/01/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	// Added By Adhnan 7/7/2020
	public ArrayList<String> verifyCalendarArrowDisplayed(WebDriver driver, String arrow) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String xpath = "//span[@class='" + arrow + "-arrow']//parent::button";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		WebElement arrowButton = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(arrowButton, driver);
		assertTrue(arrowButton.isDisplayed(), "Failed: " + arrow + " arrow of Calendar not displaying");
		assertTrue(arrowButton.isEnabled(), "Failed: " + arrow + " arrow of Calendar not Enabled");
		testSteps.add("Verified " + arrow + " arrow of Calendar");
		ratesGridLogger.info("Verified " + arrow + " arrow of Calendar");

		return testSteps;
	}

	// Added By Adhnan 7/8/2020
	public int getHeadingDateIndex(WebDriver driver, String date, String format)
			throws ParseException, InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.HEADER_DATES_ROW_DAY_NUMBER), driver);
		String expectedDayNumber = Utility.parseDate(date, format, "d");
		String expectedWeekDay = Utility.parseDate(date, format, "E");
		String foundDayNumber = null;
		String foundWeekDay = null;
		int foundIndex = -1;
		for (int i = 0; i < 20; i++) {
			ratesGridLogger.info("Date Column: " + i);
			ratesGridLogger.info("Expexted Day Number : " + expectedDayNumber);
			ratesGridLogger.info("Expexted Week Day : " + expectedWeekDay);
			Utility.ScrollToElement(ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(i), driver);
			foundDayNumber = ratesGrid.HEADER_DATES_ROW_DAY_NUMBER.get(i).getText();
			foundWeekDay = ratesGrid.HEADER_DATES_ROW_WEEKDAY.get(i).getText();
			ratesGridLogger.info("Found Day Number : " + foundDayNumber);
			ratesGridLogger.info("Found Week Day : " + foundWeekDay);
			if (foundDayNumber.equals(expectedDayNumber) && foundWeekDay.equals(expectedWeekDay)) {
				foundIndex = i;
				break;
			}
		}
		ratesGridLogger.info("Expected Date Index: " + foundIndex);
		return foundIndex;
	}

	// Added By Adhnan 7/8/2020
	public int getRoomClassIndex(WebDriver driver, String roomClass) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ROOM_CLASSES_NAMES);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		java.util.List<WebElement> roomClasses = driver.findElements(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES));
		int foundIndex = -1;
		String foundRoomClass = null;
		for (int i = 0; i < roomClasses.size(); i++) {
			ratesGridLogger.info("Room Class " + (i + 1));
			Utility.ScrollToElement(ratesGrid.ROOM_CLASSES_NAMES.get(i), driver);
			foundRoomClass = ratesGrid.ROOM_CLASSES_NAMES.get(i).getText();
			ratesGridLogger.info("Expected Room Class : " + roomClass);
			ratesGridLogger.info("Found Room Class : " + foundRoomClass);
			if (foundRoomClass.equals(roomClass)) {
				foundIndex = i;
				break;
			}

		}

		ratesGridLogger.info("Expected Room Class Index: " + foundIndex);
		return foundIndex;

	}

	// Added By Adhnan 7/8/2020
	public String getCalendarDate(WebDriver driver, String day, String requiredFormat, ArrayList<String> testSteps)
			throws InterruptedException {

		String xpath = "(//div[contains(@class,'" + day + "')])[last()]";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		WebElement requiredDay = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(requiredDay, driver);
		String requiredDate = requiredDay.getAttribute("aria-label");
		ratesGridLogger.info("Calender '" + day + "' date is '" + requiredDate + "'");
		requiredDate = Utility.parseDate(requiredDate, "E MMM dd yyyy", requiredFormat);
		testSteps.add("Calender '" + day + "' date is '" + requiredDate + "'");
		ratesGridLogger.info("Calender '" + day + "' date is '" + requiredDate + "'");
		return requiredDate;
	}

	// Added By Adhnan 7/8/2020
	public ArrayList<String> closeCalendar(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.DATE_RANGE_CALENDAR);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.DATE_RANGE_CALENDAR), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.DATE_RANGE_CALENDAR), driver);
		Wait.wait10Second();
		Utility.ScrollToElement(ratesGrid.DATE_RANGE_CALENDAR, driver);
		ratesGrid.DATE_RANGE_CALENDAR.click();
		testSteps.add("Close Date Range Calendar");
		ratesGridLogger.info("Close date range Calendar");
		try {
			Wait.waitForElementToBeGone(driver, ratesGrid.DATE_RANGE_CALENDAR, 5);
		} catch (Exception e) {

		}
		return testSteps;
	}

	// added by Adhnan 7/8/2020
	public String getOccupancyORPaceDataValue(WebDriver driver, int index, String label) throws InterruptedException {
		String xpath = "//div[@class='d-flex ir-border-grid top-header-row undefined']//following-sibling::div/div[contains(@class,'"
				+ label + "')]/div";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		return labelValues.get(index).getText();
	}

	// added by Adhnan 7/9/2020
	public ArrayList<String> verifyChannels(WebDriver driver, String roomClassName, ArrayList<String> channels,
			ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='"
				+ roomClassName + "']//ancestor::div[@class='DatesTable']/div[last()]//div[@class='roomClassName']";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> roomClassChannels = driver.findElements(By.xpath(xpath));
		String expectedChannel = null;
		String foundChannel = null;
		boolean inorder = true;
		assertEquals(channels.size(), roomClassChannels.size(), "Failed: Channels size missmatched");
		for (int i = 0; i < channels.size(); i++) {
			ratesGridLogger.info("Channel " + (i + 1));
			expectedChannel = channels.get(i);
			ratesGridLogger.info("Expected Room Class : " + expectedChannel);
			Utility.ScrollToElement(ratesGrid.ROOM_CLASSES_NAMES.get(i), driver);
			foundChannel = roomClassChannels.get(i).getText();
			ratesGridLogger.info("Found Room Class : " + foundChannel);
			assertEquals(foundChannel, expectedChannel, "Failed: Room Class missmatched");

		}
		ratesGridLogger.info("Successfully verified All Active Channels Present in Room Class '" + roomClassName + "'");
		testSteps.add("Successfully verified All Active Channels Present in Room Class '" + roomClassName + "'");

		return testSteps;
	}

	// added by Adhnan 7/9/2020
	public void activeOrBlackoutChannel(WebDriver driver, String date, String dateFormat, String roomClassName,
			String channel, String status) throws ParseException, InterruptedException {
		int dateIndex = getHeadingDateIndex(driver, date, dateFormat);
		changeRoomStatus(driver, dateIndex, channel, roomClassName, status);

	}

	// Added By Adhnan 7/7/2020
	public String getRoomStatus(WebDriver driver, int index, String source, String roomClassName)
			throws InterruptedException, ParseException {
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='" + source
				+ "']//parent::div/following-sibling::div";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		String roomStatus = labelValues.get(index).getAttribute("class");
		if (roomStatus.contains("NoBlacked")) {
			roomStatus = "*";
		} else {
			roomStatus = "B";
		}
		if (roomStatus.equals("B")) {
			xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='"
					+ roomClassName + "']//parent::div/following-sibling::div)[" + (index + 1)
					+ "]/div[@class='Blackout']/span";
			Wait.WaitForElement(driver, xpath);
			Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			WebElement blackout = driver.findElement(By.xpath(xpath));
			Utility.ScrollToElement(blackout, driver);
			assertEquals(blackout.getText(), "B", "Failed: B is not visible under Percentage value of the room");
		}
		return roomStatus;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickSettingButton> ' Description: <Clicks The Settings
	 * Button > ' ' Input parameters: <WebDriver> ' Return: <ArrayList<String>>
	 * Created By: <Aqsa Manzoor> ' Created On: <30 June 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickSettingButton(WebDriver driver) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.explicit_wait_visibilityof_webelement(ratesGrid.RATESGRID_SETTING_BUTTON, driver);
		Utility.ScrollToElement(ratesGrid.RATESGRID_SETTING_BUTTON, driver);
		ratesGrid.RATESGRID_SETTING_BUTTON.click();
		test_steps.add("Rates Grid Setting Button Clicked");
		ratesGridLogger.info("Rates Grid Setting Button Clicked");
		Wait.explicit_wait_visibilityof_webelement(ratesGrid.RATESGRID_SETTING_CONTAINER, driver);
		assertEquals(ratesGrid.RATESGRID_SETTING_CONTAINER.isDisplayed(), true,
				"Failed:Setting Container didn't Display");

		test_steps.add("Rates Grid Setting Container Displayed");
		ratesGridLogger.info("Rates Grid Setting Container Displayed");
		return test_steps;

	}

	public ArrayList<String> clickSettingButtonAgain(WebDriver driver) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.explicit_wait_visibilityof_webelement(ratesGrid.RATESGRID_SETTING_BUTTON, driver);
		Utility.ScrollToElement(ratesGrid.RATESGRID_SETTING_BUTTON, driver);
		ratesGrid.RATESGRID_SETTING_BUTTON.click();
		test_steps.add("Rates Grid Setting Button Clicked");
		ratesGridLogger.info("Rates Grid Setting Button Clicked");
		return test_steps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickSettingButton> ' Description: <Clicks The Settings
	 * Button > ' ' Input parameters: <WebDriver> ' Return: <ArrayList<String>>
	 * Created By: <Aqsa Manzoor> ' Created On: <30 June 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> changeStateShowAdditionalAdultAdditionalChilToggalSettingContainer(WebDriver driver,
			boolean isToggleEnable) throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		String toggleEnableColor = "rgba(62, 168, 244, 1)";
		String toggleDisableColor = "rgba(204, 204, 204, 1)";
		Wait.explicit_wait_visibilityof_webelement(ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE, driver);
		Utility.ScrollToElement(ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE, driver);
		if (isToggleEnable) {

			if (ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE.getCssValue("background-color")
					.equals(toggleDisableColor)) {

				ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE.click();
				Wait.wait5Second();
				assertEquals(ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE.getCssValue("background-color"),
						toggleEnableColor, "Failed:Toggle didn't Enable");
			}
			test_steps.add("Rates Grid Setting: Show Additional Adul/Child Toggle Enable");
			ratesGridLogger.info("Rates Grid Setting: Show Additional Adul/Child Toggle Enable");
		} else {

			if (ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE.getCssValue("background-color").equals(toggleEnableColor)) {

				ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE.click();
				Wait.wait5Second();
				assertEquals(ratesGrid.SHOW_ADDITIONALADULT_CHILD_TOGGLE.getCssValue("background-color"),
						toggleDisableColor, "Failed:Toggle didn't Disable");
			}
			test_steps.add("Rates Grid Setting: Show Additional Adul/Child Toggle Disable");
			ratesGridLogger.info("Rates Grid Setting: Show Additional Adul/Child Toggle Disable");
		}
		return test_steps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickBulkUpdate> ' Description: <Clicks Bulk Update Button &
	 * Open Specific Type > ' ' Input parameters: <WebDriver,String> ' Return:
	 * <ArrayList<String>> Created By: <Aqsa Manzoor> ' Created On: <30 June 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickRateGridBulkUpdateRates(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.rateGridBulkUpdateRates);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.rateGridBulkUpdateRates), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.rateGridBulkUpdateRates), driver);
		ratesGrid.rateGridBulkUpdateRates.click();
		testSteps.add("RateGrid Bulk Update Rates option clicked");
		ratesGridLogger.info("RateGrid Bulk Update Rates option clicked");
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectBulkUpdateRatesOption> ' Description: <Select the
	 * Option Given in Update Rate Options > ' ' Input parameters:
	 * <WebDriver,String> ' Return: <ArrayList<String>> Created By: <Aqsa Manzoor> '
	 * Created On: <30 June 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> selectBulkUpdateRatesOption(WebDriver driver, String updateRateType)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

		Map<Integer, String> updateRateOptionsDictionary = new HashMap<Integer, String>();
		updateRateOptionsDictionary.put(0, "EnterNewRate");
		updateRateOptionsDictionary.put(1, "UpdateEachRatebyValue");
		updateRateOptionsDictionary.put(2, "RemoveOverrides");

		int rateTypeIndex = -1;

		for (Entry<Integer, String> entry : updateRateOptionsDictionary.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(updateRateType)) {
				logger.info(entry.getKey());
				rateTypeIndex = entry.getKey();
			}
		}

		Utility.ScrollToElement(ratesGrid.UPDATERATES_OPTIONS.get(rateTypeIndex), driver);
		ratesGrid.UPDATERATES_OPTIONS.get(rateTypeIndex).click();

		return test_steps;

	}

	/*
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectBulkUpdateRatesOption> ' Description: <Select the
	 * Option Given in Update Rate Options > ' ' Input parameters:
	 * <WebDriver,String> ' Return: <ArrayList<String>> Created By: <Aqsa Manzoor> '
	 * Created On: <30 June 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> updateRoomsByRoomClassToggle(WebDriver driver, boolean isToggleOn)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE), driver);
		String toggleState = ratesGrid.UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE.getAttribute("aria-checked");
		if (isToggleOn && !Boolean.parseBoolean(toggleState)) {

			ratesGrid.UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE.click();
		} else if (!isToggleOn && Boolean.parseBoolean(toggleState)) {

			ratesGrid.UPDATERATE_UPDATERATEBYROOMCLASS_TOGGLE.click();
		}
		test_steps.add("Update By Room Class Toggle State:" + toggleState);
		ratesGridLogger.info("Update By Room Class Toggle State:" + toggleState);
		return test_steps;

	}

	/*
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectBulkUpdateRatesOption> ' Description: <Select the
	 * Option Given in Update Rate Options > ' ' Input parameters:
	 * <WebDriver,String> ' Return: <ArrayList<String>> Created By: <Aqsa Manzoor> '
	 * Created On: <30 June 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> updateNightlyAdultChildRate(WebDriver driver, boolean isUpdateRoomClassByRateToggleOn,
			String numberofRoomClasses, String nightlyRate, String additionalAdult, String additionalChild)
			throws InterruptedException {

		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		for (int i = 0; i < Integer.parseInt(numberofRoomClasses); i++) {
			Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE);
			Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE), driver);
			ratesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE.get(i).sendKeys(nightlyRate);
			ratesGrid.UPDATE_RATE_NEWRATE_ADDITIONALADULT.get(i).sendKeys(additionalAdult);
			ratesGrid.UPDATE_RATE_NEWRATE_ADDITIONALCHILD.get(i).sendKeys(additionalChild);

		}

		test_steps.add("Update By Room Class Toggle State:");
		ratesGridLogger.info("Update By Room Class Toggle State:");
		return test_steps;

	}

	/*
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectBulkUpdateRatesOption> ' Description: <Select the
	 * Option Given in Update Rate Options > ' ' Input parameters:
	 * <WebDriver,String> ' Return: <ArrayList<String>> Created By: <Aqsa Manzoor> '
	 * Created On: <30 June 2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> selectRateIncreaseDecreaseOption(WebDriver driver, String changeType)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_FIRST);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_FIRST), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_FIRST), driver);
		ratesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_FIRST.click();

		String path = "(//span[text()='each rate by']/preceding-sibling::div//div[@class='Select-menu-outer'])[1]//div[contains(text(),'"
				+ changeType + "')]";
		driver.findElement(By.xpath(path)).click();
		test_steps.add(changeType + "  option select");
		ratesGridLogger.info(changeType + " option select");

		test_steps.add("Update By Room Class Toggle State:");
		ratesGridLogger.info("Update By Room Class Toggle State:");
		return test_steps;

	}

	public ArrayList<String> selectRateCurrencyOption(WebDriver driver, String currencyType)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_SECOND);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_SECOND), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_SECOND),
				driver);
		ratesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_SECOND.click();

		String path = "(//span[text()='each rate by']/following-sibling::div//div[@class='Select-menu-outer'])[1]//div[contains(text(),'"
				+ currencyType + "')]";
		driver.findElement(By.xpath(path)).click();
		test_steps.add(currencyType + "  option select");
		ratesGridLogger.info(currencyType + " option select");
		ratesGrid.UPDATE_RATE_UPDATEBYVALUE_SELECTDROPDOWN_SECOND.click();

		test_steps.add("Update By Room Class Toggle State:");
		ratesGridLogger.info("Update By Room Class Toggle State:");
		return test_steps;

	}

	public ArrayList<String> enterRateValueForUpdateRate(WebDriver driver, String updateByValue)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_VALUEFIELD);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_VALUEFIELD), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_UPDATEBYVALUE_VALUEFIELD), driver);

		test_steps.add("Enter rate value : " + updateByValue);
		ratesGridLogger.info("Enter rate value : " + updateByValue);

		ratesGrid.UPDATE_RATE_UPDATEBYVALUE_VALUEFIELD.sendKeys(updateByValue);

		test_steps.add("Update By Room Class Toggle State:");
		ratesGridLogger.info("Update By Room Class Toggle State:");
		return test_steps;

	}

	public ArrayList<String> clickRateGridBulkUpdate(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.rateGridBulkUpdate);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.rateGridBulkUpdate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.rateGridBulkUpdate), driver);
		ratesGrid.rateGridBulkUpdate.click();
		testSteps.add("RateGrid Bulk Update clicked");
		ratesGridLogger.info("RateGrid Bulk Update clicked");
		return testSteps;
	}

	public ArrayList<String> clickRateGridBulkUpdateAvailable(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.rateGridBulkUpdateAvailable);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.rateGridBulkUpdateAvailable), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.rateGridBulkUpdateAvailable), driver);
		ratesGrid.rateGridBulkUpdateAvailable.click();
		testSteps.add("RateGrid Bulk Update Available option clicked");
		ratesGridLogger.info("RateGrid Bulk Update Available option clicked");
		return testSteps;
	}

	public ArrayList<String> bulkUpdatePoppupHeading(WebDriver driver, String type) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		String bulkUpdatePopupHeading = "//div[text()='Bulk update - " + type + "']";
		// Wait.WaitForElement(driver, bulkUpdatePopupHeading);
		Wait.waitForElementToBeVisibile(By.xpath(bulkUpdatePopupHeading), driver);
		Wait.waitForElementToBeClickable(By.xpath(bulkUpdatePopupHeading), driver);
		WebElement bulkUpdatePopupHeadingElement = driver.findElement(By.xpath(bulkUpdatePopupHeading));
		assertTrue(bulkUpdatePopupHeadingElement.isDisplayed(),
				"Bulk Update " + type + " popup Heading didn't display");
		testSteps.add("Verified Bulk update " + type + "  popup heading");
		ratesGridLogger.info("Verified Bulk update " + type + "  popup heading");
		return testSteps;

	}

	public String bulkUpdatePoppupText(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupText);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupText), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupText), driver);
		String text = ratesGrid.bulkUpdatePopupText.getText();
		ratesGridLogger.info("Verified Bulk update popup text : " + text);
		return text;

	}

	public ArrayList<String> bulkUpdatePoppupDayCheck(WebDriver driver, String day, String isChecked)
			throws InterruptedException {

		String daysCheckBox = "//span[text()='" + day + "']/following-sibling::span";
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, daysCheckBox);
		Wait.waitForElementToBeVisibile(By.xpath(daysCheckBox), driver);
		Wait.waitForElementToBeClickable(By.xpath(daysCheckBox), driver);
		WebElement daysElement = driver.findElement(By.xpath(daysCheckBox));
		Utility.ScrollToElement_NoWait(daysElement, driver);

		if (isChecked.equalsIgnoreCase("yes")) {
			testSteps.add(day + " checkbox checked");
			ratesGridLogger.info(day + " checkbox checked");
		} else if (isChecked.equalsIgnoreCase("no")) {
			daysElement.click();
			testSteps.add(day + " checkbox unchecked");
			ratesGridLogger.info(day + " checkbox unchecked");
		}
		return testSteps;

	}

	public ArrayList<String> bulkUpdatePoppupUpdateAvailability(WebDriver driver, String channel,
			String updateAvailability) throws InterruptedException {

		String updateAvailabilityPath = "//div[contains(text(),'" + channel
				+ "')]//following-sibling::div//span[contains(text(),'" + updateAvailability + "')]";
		ratesGridLogger.info("updateAvailabilityPath :  " + updateAvailabilityPath);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, updateAvailabilityPath);
		Wait.waitForElementToBeVisibile(By.xpath(updateAvailabilityPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(updateAvailabilityPath), driver);
		WebElement updateAvailabilityElement = driver.findElement(By.xpath(updateAvailabilityPath));
		Utility.ScrollToElement_NoWait(updateAvailabilityElement, driver);
		updateAvailabilityElement.click();
		testSteps.add(channel + " selected update availability is " + updateAvailability);
		ratesGridLogger.info(channel + " selected update availability is " + updateAvailability);

		return testSteps;
	}

	public ArrayList<String> getBulkUpdatePoppupRoomClass(WebDriver driver) throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupRoomClass);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.bulkUpdatePopupRoomClass, driver);
		ratesGrid.bulkUpdatePopupRoomClass.click();
		String roomClassesPath = "(//div[contains(text(),'Select room class(es)')]//parent::div//following::div[@class='Select-menu-outer'])[1]//div[@class='Select-option']";
		List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));
		for (int i = 0; i < roomClassesElement.size(); i++) {
			String roomClassName = roomClassesElement.get(i).getText().trim();
			testSteps.add(roomClassName);
			ratesGridLogger.info("GetRoomClass : " + roomClassName);
		}

		return testSteps;
	}

	public ArrayList<String> bulkUpdatePoppupRoomClass(WebDriver driver, String roomClass) throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupRoomClass);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.bulkUpdatePopupRoomClass, driver);
		ratesGrid.bulkUpdatePopupRoomClass.click();

		String roomClassesPath = "(//div[contains(text(),'Select room class(es)')]//parent::div//following::div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
		List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));
		logger.info("Room Class Before for" + roomClass);
		logger.info("Room Class Size " + roomClassesElement.size());

		for (int i = 0; i < roomClassesElement.size(); i++) {

			String roomClassName = roomClassesElement.get(i).getText().trim();
			testSteps.add("GetRoomClass : " + roomClassName);
			ratesGridLogger.info("GetRoomClass : " + roomClassName);
			logger.info("ROom CLass get:" + roomClass);

			if (roomClassName.contains(roomClass)) {

				roomClassesElement.get(i).click();
				testSteps.add("Entered RoomClass : " + roomClass);
				ratesGridLogger.info("Entered RoomClass : " + roomClass);

				ratesGrid.bulkUpdatePopupCheckoutInput.sendKeys(Keys.TAB);
				testSteps.add("Clicked Tab Key");
				ratesGridLogger.info("Clicked Tab Key");

				break;
			}

		}

		return testSteps;
	}

	public ArrayList<String> clickBulkUpdatePopupUpdateButton(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupUpdateButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupUpdateButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupUpdateButton), driver);
		ratesGrid.bulkUpdatePopupUpdateButton.click();
		testSteps.add("Update button clicked");
		ratesGridLogger.info("Update button clicked");
		return testSteps;
	}

	public ArrayList<String> bulkUpdatePopupUpdateButtonEnable(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupUpdateButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupUpdateButton), driver);
		Boolean isActive = ratesGrid.bulkUpdatePopupUpdateButton.isEnabled();
		if (isActive) {
			testSteps.add("Update button is enabled");
			ratesGridLogger.info("Update button is enabled");

		} else {
			testSteps.add("Update button is disabled");
			ratesGridLogger.info("Update button is disabled");

		}
		return testSteps;
	}

	public String bulkUpdatePoppupTotalDays(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupDays);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupDays), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupDays), driver);
		String text = ratesGrid.bulkUpdatePopupDays.getText();
		ratesGridLogger.info("bulkUpdatePopupDays text : " + text);
		return text;

	}

	public ArrayList<String> bulkUpdatePoppupTotalOccupancy(WebDriver driver, String isEnable,
			String totalOccupancyType, String totalOccupancyValue) throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupTotalOccupancyToggle);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupTotalOccupancyToggle), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupTotalOccupancyToggle), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.bulkUpdatePopupTotalOccupancyToggle, driver);
		if (isEnable.equalsIgnoreCase("Yes")) {
			ratesGrid.bulkUpdatePopupTotalOccupancyToggle.click();
			testSteps.add("Enabled total occupancy toggle button");
			ratesGridLogger.info("Enabled total occupancy toggle button");

			ratesGrid.bulkUpdatePopupTotalOccupancyDropDown.click();
			testSteps.add("Dropdown clicked");
			ratesGridLogger.info("Dropdown clicked");
			if (totalOccupancyType.equalsIgnoreCase("Greater")) {
				String greater = "greater than";
				ratesGridLogger.info(greater);
				String path = "(//span[text()='For days that total occupancy is']//parent::label//parent::div//div[@class='Select-menu-outer'])[1]//div[contains(text(),'"
						+ greater + "')]";
				driver.findElement(By.xpath(path)).click();
				testSteps.add("Greater than option select");
				ratesGridLogger.info("Greater than option select");

			} else if (totalOccupancyType.equalsIgnoreCase("Less")) {
				String less = "less than";
				ratesGridLogger.info(less);
				String path = "(//span[text()='For days that total occupancy is']//parent::label//parent::div//div[@class='Select-menu-outer'])[1]//div[contains(text(),'"
						+ less + "')]";
				driver.findElement(By.xpath(path)).click();
				testSteps.add("Less than option select");
				ratesGridLogger.info("Less than option select");
			}

			ratesGrid.bulkUpdatePopupTotalOccupanyValue.sendKeys(totalOccupancyValue);
			testSteps.add("Entered total occupancy value : " + totalOccupancyValue);
			ratesGridLogger.info("Entered total occupancy value : " + totalOccupancyValue);
		}
		return testSteps;
	}

	public ArrayList<String> clickBulkUpdateCancelButton(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdateCancel);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdateCancel), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdateCancel), driver);
		ratesGrid.bulkUpdateCancel.click();
		testSteps.add("cancel button clicked");
		ratesGridLogger.info("cancel button clicked");
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickRatePlanArrow> ' Description: < Move to Rate Plan Drop
	 * Down Box and Click on Arrow > ' Input parameters: < WebDriver driver,String
	 * test_steps> ' Return value: <void> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/01/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	//
	// /*
	// *
	// ##########################################################################################################################################################################
	// *
	// * ' Method Name: <clickRatePlanArrow>
	// * ' Description: < Move to Rate Plan Drop Down Box and Click on Arrow >
	// * ' Input parameters: < WebDriver driver,String test_steps>
	// * ' Return value: <void> ' Created By: <Gangotri Sikheria> '
	// * ' Created On: <MM/dd/yyyy> <07/01/2020>
	// * Updated BY: Adhnan ghaffar
	// * Updated Date : 07/22/2020
	// *
	// ##########################################################################################################################################################################

	public void clickRatePlanArrow(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanArrow);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RateGrid.ratePlanArrow), driver);
		try {
			Utility.ScrollToElement(elements.ratePlanArrow, driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			// jse.executeScript("window.scrollBy(0,-300)");
			Wait.waitForElementToBeClickable(By.xpath(OR_RateGrid.ratePlanArrow), 60, driver);
			Utility.ScrollToViewElementINMiddle(driver, elements.ratePlanArrow);
			elements.ratePlanArrow.click();
			Wait.wait5Second();
			test_steps.add("Click Rate Plan Arrow");
			logger.info("Click Rate Plan Arrow");
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			// jse.executeScript("window.scrollBy(0,-400)");
			Utility.clickThroughJavaScript(driver, elements.ratePlanArrow);
			Wait.wait5Second();
			test_steps.add("Click Rate Plan Arrow");
			logger.info("Click Rate Plan Arrow");

		}

	}

	public void clickRatePlanArrowOpenIcon(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Utility.ScrollToElement(elements.ratesTab, driver);
		Utility.clickThroughJavaScript(driver, elements.ratePlanOpenArrowIcon);
		test_steps.add("Click Rate Plan Arrow Open Icon");
		logger.info("Click Rate Plan Arrow Open Icon");

	}

	public ArrayList<String> getRatePlanNamesCategoryWise(WebDriver driver, String colorName)
			throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanNamesList);
		Utility.ScrollToElement(elements.ratePlanNamesList.get(0), driver);
		for (int i = 0; i < elements.ratePlanNamesList.size(); i++) {
			Utility.ScrollToElement(elements.ratePlansColor.get(i), driver);
			String color = elements.ratePlansColor.get(i).getCssValue("color");
			String colorName1 = Color.fromString(color).asHex();
			if (colorName1.toLowerCase().trim().equals(colorName.toLowerCase().trim())) {
				getNames.add(elements.ratePlanNamesList.get(i).getAttribute("aria-label"));
			}
		}
		logger.info("Get List of All Rate Plans: " + getNames);
		return getNames;
	}

	public String getRatePlanColorCategoryWise(WebDriver driver, ArrayList<String> ratePlans, String colorName)
			throws InterruptedException {

		String getColor = null;
		for (String str : ratePlans) {
			String path = "//div[@role='option'and contains(@aria-label,'" + str + "')]/div";
			WebElement element = driver.findElement(By.xpath(path));
			Utility.ScrollToElement(element, driver);
			String attributeValue = element.getAttribute("style");
			String[] array = attributeValue.split(":");
			String arrayTwo[] = array[1].split(";");
			getColor = arrayTwo[0];
			getColor = Color.fromString(getColor).asHex();

		}
		logger.info("Get the Color of All Rate Plans: " + getColor);
		return getColor;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickCollapseIconOfRatePlan> ' Description: < Click Collapse
	 * icon of rate plan> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * test_steps, boolean isboolean> ' Return value: <NA>' Created By: <Gangotri
	 * Sikheria> ' ' Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String selectAndReturnSpecificRatePlan(WebDriver driver, ArrayList<String> test_steps, String ratePlanName)
			throws InterruptedException {
		String path = "//div[@class='Select-menu-outer']/div[@class='Select-menu']/div[@aria-label='" + ratePlanName
				+ "']";

		System.out.println(path);
		try {
			Wait.WaitForElement(driver, path);
			WebElement element = driver.findElement(By.xpath(path));
		} catch (Exception e) {
			Elements_RatesGrid elements = new Elements_RatesGrid(driver);
			clickRatePlanArrow(driver, test_steps);
			Utility.clickThroughJavaScript(driver, elements.ratePlanArrow);
			test_steps.add("Click Rate Plan Arrow");
			logger.info("Click Rate Plan Arrow");
			Wait.WaitForElement(driver, path);
		}
		WebElement element = driver.findElement(By.xpath(path));

		Wait.explicit_wait_visibilityof_webelement(element, driver);
		Utility.ScrollToElement(element, driver);
		assertTrue(element.isDisplayed(), "rate plan name dosen't exist");
		element.click();
		test_steps.add("Select Rate Plan from Drop Down Box: <b>" + ratePlanName + "</b>");
		logger.info("Select Rate Plan from Drop Down Box: " + ratePlanName);
		return ratePlanName;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <sizeOfAllRatePlan> ' Description: < Give size the list of
	 * Rate Plan> ' Input parameters: < WebDriver driver> ' Return value: <String>'
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public int sizeOfAllRatePlan(WebDriver driver) throws InterruptedException {
		int size = 0;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanNamesList);
		Utility.ScrollToElement(elements.ratePlanNamesList.get(0), driver);
		size = elements.ratePlanNamesList.size();
		logger.info("Rate Plan Size is: " + size);
		return size;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickExpandIconOfRatePlan> ' Description: < Click Expand icon
	 * of rate plan> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * test_steps, boolean isboolean> ' Return value: <NA>' Created By: <Gangotri
	 * Sikheria> ' ' Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void clickExpandIconOfRatePlan(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanNamesExpanCollapseIcon);
		Utility.ScrollToElement(elements.ratesTab, driver);
		boolean isExist = Utility.isElementDisplayed(driver, By.xpath(OR_RateGrid.bestAvailableRate));
		if (isExist) {
			elements.ratePlanNamesExpanCollapseIcon.click();
			test_steps.add("Click Expand Icon Of Rate Plan");
			logger.info("Click Expand Icon Of Rate Plan");
		} else if (!isExist) {

			test_steps.add("Click Expand Icon Of Rate Plan");
			logger.info("Click Expand Icon Of Rate Plan");
		}

		assertTrue(elements.ratePlanMinusIcon.isDisplayed(), "Failed verified Minus Icon");
		test_steps.add("Collapse Icon Of Rate Plan is Displayed");
		logger.info("Collapse Icon Of Rate Plan is Displayed");

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickCollapseIconOfRatePlan> ' Description: < Click Collapse
	 * icon of rate plan> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * test_steps, boolean isboolean> ' Return value: <NA>' Created By: <Gangotri
	 * Sikheria> ' ' Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void hoverOnBestAvailableRateLabel(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.bestAvailableRate);
		Utility.ScrollToElement(elements.bestAvailableRate, driver);
		Utility.hoverOnElement(driver, elements.bestAvailableRate);
		logger.info("Hover on Best Available Rate Label");
	}

	public void clickCollapseIconOfRatePlan(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanNamesExpanCollapseIcon);
		Utility.ScrollToElement(elements.ratesTab, driver);

		boolean isExist = Utility.isElementDisplayed(driver, By.xpath(OR_RateGrid.bestAvailableRate));
		if (isExist) {

			test_steps.add("Click Collapse Icon Of Rate Plan");
			logger.info("Click Collapse Icon Of Rate Plan");
		} else if (!isExist) {
			elements.ratePlanNamesExpanCollapseIcon.click();
			test_steps.add("Click Collapse Icon Of Rate Plan");
			logger.info("Click Collapse Icon Of Rate Plan");
		}

		assertTrue(elements.ratePlanPlusIcon.isDisplayed(), "Failed verified Plus Icon");
		test_steps.add("Expand Icon Of Rate Plan is Displayed");
		logger.info("Expand Icon Of Rate Plan is Displayed");
	}

	public void verifyAndClickExpandIconOfRatePlan(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		boolean isExist = Utility.isElementDisplayed(driver, By.xpath(OR_RateGrid.bestAvailableRate));
		if (isExist) {
			elements.ratePlanNamesExpanCollapseIcon.click();
			logger.info("Click Expand Icon Of Rate Plan");
			assertTrue(elements.ratePlanMinusIcon.isDisplayed(), "Failed verified Minus Icon");
			logger.info("Collapse Icon Of Rate Plan is Displayed");
		} else if (!isExist) {
			logger.info("Rate Plan Already Expanded");
		}

	}

	public String getBestAvailableRoomClass(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		String bestRoomClass = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.bestAvailableRoomClass);
		Utility.ScrollToElement(elements.ratesTab, driver);
		bestRoomClass = elements.bestAvailableRoomClass.getText();
		bestRoomClass = bestRoomClass.replaceAll("[\\n\\t ]", "");
		test_steps.add("Best Available Room Class No Is:  <b>" + bestRoomClass + "</b>");
		logger.info("Best Available Room Class No Is:  " + bestRoomClass);
		return bestRoomClass;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <searchRateAndSelectRate> ' Description: < Search Rate and
	 * Select Rate Plan from Drop Down Box> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> test_steps, String ratePlanName> ' Return value:
	 * <NA>' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void searchRateAndSelectRate(WebDriver driver, ArrayList<String> test_steps, String ratePlanName)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanSelectInput);
		Utility.ScrollToElement(elements.ratePlanSelectInput, driver);
		Actions action = new Actions(driver);
		action.moveToElement(elements.ratePlanInputBox).clickAndHold();
		logger.info("Click and Hold Rate Plan Input Box");
		elements.ratePlanInputBox.sendKeys(ratePlanName);
		test_steps.add("Enter Rate Plan <b>" + ratePlanName + "</b>");
		logger.info("Enter Rate Plan " + ratePlanName);
		String path = "//div[@role='option'and contains(@aria-label,'" + ratePlanName + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		assertTrue(element.isDisplayed(), "rate plan name dosen't exist");
		Utility.clickThroughAction(driver, element);
		test_steps.add("Select Rate Plan from Drop Down Box: <b>" + ratePlanName + "</b>");
		logger.info("Select Rate Plan from Drop Down Box: " + ratePlanName);

	}

	public ArrayList<String> getRegularRates(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		ArrayList<String> bestRoomClass = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.bestAvailableRoomClass);
		Utility.ScrollToElement(elements.ratesTab, driver);
		for (WebElement ele : elements.regularRates) {
			bestRoomClass.add(ele.getText());
		}
		test_steps.add(
				"Best Available Rates :  <b>" + (bestRoomClass.toString().replace("[", "").replace("]", "")) + "</b>");
		logger.info("Best Rates Are :  " + bestRoomClass);
		return bestRoomClass;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getrateGridRoomClass> ' Description: < Get all the Room Class
	 * of Rate Grid> ' Input parameters: < WebDriver driver> ' Return value:
	 * <ArrayList<String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> getrateGridRoomClass(WebDriver driver) throws InterruptedException {
		ArrayList<String> roomClasses = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanSelectValue);
		Utility.ScrollToElement(elements.ratesTab, driver);
		for (WebElement ele : elements.rateGridAllRoomClass) {
			roomClasses.add(ele.getAttribute("title"));
		}
		logger.info("Get List of All RoomClass: " + roomClasses);
		return roomClasses;
	}

	public int getSizeOfRateGridRoomClass(WebDriver driver) throws InterruptedException {
		int roomClassesSize = 0;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanSelectValue);
		Utility.ScrollToElement(elements.ratesTab, driver);
		roomClassesSize = elements.rateGridAllRoomClass.size();
		logger.info("RoomClass Size id: " + roomClassesSize);
		return roomClassesSize;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <expandRoomClass and collapseRoomClass> ' Description: <
	 * Clicking on Expand and Collapse icon of Specific Room Class> ' Input
	 * parameters: < WebDriver driver,ArrayList<String> testSteps, String
	 * roomClassName> ' Return value: <NA> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void expandRoomClass(WebDriver driver, ArrayList<String> testSteps, String roomClassName)
			throws InterruptedException {
		String path = "//div[@class='DatesTable']//div[(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase() + "')]/parent::div/span";
		String plusPath = "//div[@class='DatesTable']//div[(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase()
				+ "')]/parent::div/span[contains(@class,'ir-plus')]";
		logger.info(path);logger.info(plusPath);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element1 = driver.findElement(By.xpath(path));

		Utility.ScrollToViewElementINMiddle(driver, element1);
		int size = driver.findElements(By.xpath(plusPath)).size();
		String minusPath = "//div[@class='DatesTable']//div[(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase()
		+ "')]/parent::div/span[contains(@class,'ir-minus')]";
logger.info(minusPath);
		logger.info(size);
		if (size > 0) {
			Wait.WaitForElement(driver, path);

			WebElement element = driver.findElement(By.xpath(path));

			Utility.ScrollToViewElementINMiddle(driver, element);
			Utility.clickThroughJavaScript(driver, element);
			
			while(driver.findElements(By.xpath(minusPath)).size()==0) {
				Utility.clickThroughJavaScript(driver, element);
			}
		}
		
		Wait.wait2Second();
		try {
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(minusPath)));
		boolean collapseExist = driver.findElement(By.xpath(minusPath)).isDisplayed();
		if (collapseExist) {
			testSteps.add(" Expand Room Class <b>" + roomClassName + "</b>");
			logger.info("Expand Room Class " + roomClassName);
			WebElement minus = driver.findElement(By.xpath(minusPath));
			Utility.ScrollToElement(minus, driver);
			assertTrue(minus.isDisplayed(), "Failed to verify collapse icon");
			testSteps.add("Verified Room Class <b>" + roomClassName + "</b> Expanded");
			logger.info("Verified Room Class " + roomClassName + " Expanded");
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void collapseRoomClass(WebDriver driver, ArrayList<String> testSteps, String roomClassName)
			throws InterruptedException {
		String path = "//div[@class='DatesTable']//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClassName.toUpperCase() + "')]/parent::div/span";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		// element.click();
		Utility.clickThroughJavaScript(driver, element);
		String plusPath = "//div[@class='DatesTable']//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClassName.toUpperCase()
				+ "')]/parent::div/span[contains(@class,'ir-plus')]";
		boolean expandExist = driver.findElement(By.xpath(plusPath)).isDisplayed();
		if (expandExist) {
			testSteps.add("Collapse Room Class <b>" + roomClassName + "</b>");
			logger.info("Collapse Room Class " + roomClassName);
			WebElement plus = driver.findElement(By.xpath(plusPath));
			Utility.ScrollToElement(plus, driver);
			assertTrue(plus.isDisplayed(), "Failed to verify collapse icon");
			testSteps.add("Verified  Room Class <b>" + roomClassName + "</b> Collapse");
			logger.info("Verified Room Class " + roomClassName + " Collapse");
		}

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getChannelofSpecificRoomClass> ' Description: < get Channels
	 * of Specific Room Class> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps, String roomClassName> ' Return value:
	 * <Channel Name> ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/09/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> getChannelofSpecificRoomClass(WebDriver driver, String roomClassName)
			throws InterruptedException {
		ArrayList<String> channelName = new ArrayList<String>();
		String path = "//div[@class='DatesTable']//div[contains(translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClassName.toUpperCase() + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'complexView')]"
				+ "//div[@class='roomClassName']";
		Wait.WaitForElement(driver, path);
		List<WebElement> elements = driver.findElements(By.xpath(path));
		Utility.ScrollToElement(elements.get(0), driver);
		for (WebElement ele : elements) {
			channelName.add(ele.getAttribute("title"));
		}
		logger.info("Room Class " + roomClassName + "Channels Are: " + channelName);
		return channelName;
	}

	public ArrayList<String> getChannelofAllRoomClass(WebDriver driver, ArrayList<String> testSteps,
			String roomClassName) throws InterruptedException {
		ArrayList<String> channelName = new ArrayList<String>();
		String path = "//div[@class='DatesTable']//div[contains(translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClassName.toUpperCase() + "')]"
				+ "/parent::div/parent::div/following-sibling::div//div[@class='roomClassName']";

		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		if (isExist) {
			Wait.WaitForElement(driver, path);
			List<WebElement> elements = driver.findElements(By.xpath(path));
			Utility.ScrollToElement(elements.get(0), driver);
			for (WebElement ele : elements) {
				channelName.add(ele.getAttribute("title"));
			}
			logger.info("Room Class " + roomClassName + " Channels Are: " + channelName);

		} else if (!isExist) {
			testSteps.add("No Channel Associated");
		}
		return channelName;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyChannelForSpecificClass> ' Description: < Verifying
	 * channel which is associated with rate plan> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps, String roomClassName, String
	 * ratePlanName> ' Return value: <NA> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyChannelForSpecificClass(WebDriver driver, ArrayList<String> testSteps, String roomClassName,
			String channelName, String ratePlanName) throws InterruptedException {
		String path = "//div[@class='DatesTable']//div[contains(translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClassName.toUpperCase()
				+ "')]/ancestor::div/..//following-sibling::div//div[translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + channelName.toUpperCase() + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		assertTrue(element.isDisplayed(), "Failed to verify channel for specific room class");
		testSteps.add("Channel <b>" + channelName + "</b> Associated with Rate Plan <b>" + ratePlanName + "</b>");
		logger.info("Channel " + channelName + "Associated with Rate Plan" + ratePlanName);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <expandChannel and collapseChannel> ' Description: < click
	 * expand and collapse icon of channel and verify iconn> ' Input parameters: <
	 * WebDriver driver,ArrayList<String> testSteps, String roomClassName, String
	 * channelName> ' Return value: <NA> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/09/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void expandChannel(WebDriver driver, ArrayList<String> testSteps, String roomClassName, String channelName)
			throws InterruptedException {
		String path = "//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase() + "']/../../..//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + channelName.toUpperCase()
				+ "']/following-sibling::span[contains(@class,'ir-plus')]";
		if (channelName.contains("'")) {
			path = "//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase() + "']/../../..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + channelName.split("'")[0].toUpperCase()
					+ "')]/following-sibling::span[contains(@class,'ir-plus')]";
			logger.info("Channel Name having ' is " + channelName);
		}

		if (driver.findElements(By.xpath(path)).size() > 0) {
			path = "//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase() + "']/../../..//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + channelName.toUpperCase()
					+ "']/following-sibling::span";
			if (channelName.contains("'")) {
				path = "//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase() + "']/../../..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
						+ channelName.split("'")[0].toUpperCase() + "')]/following-sibling::span";
				logger.info("Channel Name having ' is " + channelName);
			}
			WebElement element = driver.findElement(By.xpath(path));
			// Utility.ScrollToElement(element, driver);
			Wait.waitForElementToBeClickable(By.xpath(path), driver, 5);

			Wait.wait5Second();
			Utility.clickThroughJavaScript(driver, element);
			// driver.findElement(By.xpath(path)).click();

		}

		String minusPath = "//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase() + "']/../../..//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + channelName.toUpperCase()
				+ "']/following-sibling::span[contains(@class,'ir-minus')]";
		if (channelName.contains("'")) {
			minusPath = "//div[translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + roomClassName.toUpperCase() + "']/../../..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
					+ channelName.split("'")[0].toUpperCase() + "')]/following-sibling::span[contains(@class,'ir-minus')]";
			logger.info("Channel Name having ' is " + channelName);
		}
try {
		boolean collapseExist = driver.findElement(By.xpath(minusPath)).isDisplayed();
		if (collapseExist) {
			testSteps.add("Expand Channel <b>" + channelName + "</b>");
			logger.info("Expand Channel " + channelName);
			WebElement minus = driver.findElement(By.xpath(minusPath));
			Utility.ScrollToElement(minus, driver);
			assertTrue(minus.isDisplayed(), "Failed to verify collapse icon");
			testSteps.add("Verified Channel <b>" + channelName + "</b> Expanded");
			logger.info("Verified Channel " + channelName + " Expanded");
		}
}catch (Exception e) {
	// TODO: handle exception
}
	}

	public void collapseChannel(WebDriver driver, ArrayList<String> testSteps, String roomClassName, String channelName)
			throws InterruptedException {
		String path = "//div[@class='DatesTable']//div[contains(translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClassName.toUpperCase() + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + channelName.toUpperCase() + "']/parent::div/span";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver, 5);
		Utility.clickThroughJavaScript(driver, element);
		String plusPath = "//div[@class='DatesTable']//div[contains(translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClassName.toUpperCase() + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + channelName.toUpperCase()
				+ "']/parent::div/span[contains(@class,'ir-plus')]";
		boolean expandExist = driver.findElement(By.xpath(plusPath)).isDisplayed();
		if (expandExist) {
			testSteps.add("Collapse Channel <b>" + channelName + "</b>");
			logger.info("Click Expand icon of Room Class " + channelName);
			WebElement plus = driver.findElement(By.xpath(plusPath));
			Utility.ScrollToElement(plus, driver);
			assertTrue(plus.isDisplayed(), "Failed to verify expand icon");
			testSteps.add("Verified Channel <b>" + channelName + "</b> Collapse");
			logger.info("Verified Channel " + channelName + " Collapse");

		}

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRuleHeader> ' Description: < Get all Rule Header> ' Input
	 * parameters: < WebDriver driver,ArrayList<String> testSteps,String roomClass,
	 * String channelName> ' Return value: <Rule Headers > ' Created By: <Gangotri
	 * Sikheria> ' ' Created On: <MM/dd/yyyy> <07/09/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> getRuleHeader(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName) throws InterruptedException {
		ArrayList<String> ruleHeader = new ArrayList<String>();
		String path = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader')]";
		Wait.WaitForElement(driver, path);
		List<WebElement> elements = driver.findElements(By.xpath(path));
		Utility.ScrollToElement(elements.get(0), driver);
		for (WebElement ele : elements) {
			ruleHeader.add(ele.getText());
		}
		logger.info("Rule Header " + ruleHeader);
		return ruleHeader;
	}

	public void hoverRuleRate(WebDriver driver, String roomClass, String rate, ArrayList<String> testSteps)
			throws InterruptedException {
		String roomClassPath = "//div[contains(text(),'" + roomClass + "')]";
		Wait.WaitForElement(driver, roomClassPath);
		WebElement elementRoomClass = driver.findElement(By.xpath(roomClassPath));
		Utility.ScrollToViewElementINMiddle(driver, elementRoomClass);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";

		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.hoverOnElement(driver, element);
		testSteps.add("Hover Rule Rate of Room Class<b>" + roomClass + "</b>");
		logger.info("Hover Rule Rate of Room Class" + roomClass);
	}

	public void hoverRuleRate(WebDriver driver, String roomClass, String rate) throws InterruptedException {
		String roomClassPath = "//div[contains(text(),'" + roomClass + "')]";
		Wait.WaitForElement(driver, roomClassPath);
		WebElement elementRoomClass = driver.findElement(By.xpath(roomClassPath));
		Utility.ScrollToViewElementINMiddle(driver, elementRoomClass);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/parent::div/following-sibling::div/div[contains(@class,'has-changes')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.hoverOnElement(driver, element);
		logger.info("Hover Rule Rate of Room Class" + roomClass);
	}

	public void hoverRuleIndicationOfClass(WebDriver driver, String roomClass, ArrayList<String> testSteps)
			throws InterruptedException {

		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[@class='RuleIndication']/span";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		testSteps.add("Hover Rule Indication of Room Class : <b>" + roomClass + "</b>");
		logger.info("Hover Rule Indication of Room Class" + roomClass);
	}

	public void hoverRuleIndicationOfClassOfBlankRate(WebDriver driver, String roomClass, ArrayList<String> testSteps)
			throws InterruptedException {

		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate') and contains(text(),'--')]/following-sibling::div[@class='RuleIndication']/span";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		testSteps.add("Hover Rule Indication of Room Class : <b>" + roomClass + "</b>");
		logger.info("Hover Rule Indication of Room Class" + roomClass);
	}

	public void hoverRuleIndicationOfChannelOfBlankRate(WebDriver driver, String roomClass, String channel,
			ArrayList<String> testSteps) throws InterruptedException {

		String path = "//div[contains(@title,'" + roomClass
				+ "')]/ancestor::div/..//following-sibling::div//div[@title='" + channel + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate') and contains(text(),'--')]/following-sibling::div[@class='RuleIndication']/span";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		testSteps.add("Hover Rule Indication of Room Class : <b>" + roomClass + "</b>Channel : <b>" + channel + "</b>");
		logger.info("Hover Rule Indication Room Class" + roomClass + "Channel" + channel);
	}

	public void hoverRuleIndicationOfChannel(WebDriver driver, String roomClass, String channel,
			ArrayList<String> testSteps) throws InterruptedException {

		String path = "//div[contains(text(),'" + roomClass
				+ "')]/ancestor::div/..//following-sibling::div//div[@title='" + channel
				+ "']/parent::div/following-sibling::div/div[@class='RuleIndication']/span";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		testSteps.add("Hover Rule Indication of Room Class : <b>" + roomClass + "</b>Channel : <b>" + channel + "</b>");
		logger.info("Hover Rule Indication Room Class" + roomClass + "Channel" + channel);
	}

	public void hoverOverRideRateAtChannelLevel(WebDriver driver, String roomClass, String channelName, String rate,
			ArrayList<String> testSteps) throws InterruptedException {
		String roomClassPath = "//div[contains(text(),'" + roomClass + "')]";
		Wait.WaitForElement(driver, roomClassPath);
		WebElement elementRoomClass = driver.findElement(By.xpath(roomClassPath));
		Utility.ScrollToViewElementINMiddle(driver, elementRoomClass);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";

		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.hoverOnElement(driver, element);
		testSteps.add("Hover OverRide Rate of Room Class<b>" + roomClass + "</b> Channel : <b>" + channelName + "</b>");
		logger.info("Hover OverRide Rate of Room Class" + roomClass + "--" + channelName);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRuleDate> amnd <getRuleDate> ' Description: < get and
	 * Verify Rate Grid Popup date > ' Input parameters: <WebDriver
	 * driver,ArrayList<String> testSteps, String date> ' Return value: <String ,NA>
	 * ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getRuleDate(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String ruleDate = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupDate);
		ruleDate = elements.rateGridPopupDate.getText();
		testSteps.add("Date: <b>" + elements.rateGridPopupDate.getText() + "</b>");
		logger.info(" Date: " + elements.rateGridPopupDate.getText());
		return ruleDate;
	}

	public String getRuleDate(WebDriver driver) throws InterruptedException {
		String ruleDate = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupDate);
		ruleDate = elements.rateGridPopupDate.getText();
		logger.info(" Date: " + elements.rateGridPopupDate.getText());
		return ruleDate;
	}

	public String getRateDate(WebDriver driver) throws InterruptedException {
		String ruleDate = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridDate);
		ruleDate = elements.rateGridDate.getText();
		logger.info(" Date: " + elements.rateGridDate.getText());
		return ruleDate;
	}

	public void verifyRuleDate(WebDriver driver, ArrayList<String> testSteps, String date) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupDate);
		assertEquals(elements.rateGridPopupDate.getText(), date, "Failed to verify rate grid popup date");
		testSteps.add("Verified  Date: <b>" + elements.rateGridPopupDate.getText() + "</b>");
		logger.info("Verified Date: " + elements.rateGridPopupDate.getText());
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRuleRate> ' Description: < Verify Rate Grid Popup Rate>
	 * ' Input parameters: <WebDriver driver,ArrayList<String> testSteps, String
	 * rate> ' Return value: <NA> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getRuleRate(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String ruleRate = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.popoverRate);
		ruleRate = elements.popoverRate.getText();
		testSteps.add("  Rate: <b>" + elements.popoverRate.getText() + "</b>");
		logger.info(" Rate: " + elements.popoverRate.getText());
		return ruleRate;
	}

	public void verifyRuleRate(WebDriver driver, ArrayList<String> testSteps, String rate) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.popoverRate);
		String ruleRate = elements.popoverRate.getText();
		logger.info(ruleRate);
		rate = "$" + rate;
		logger.info(rate);
		assertEquals(ruleRate, rate, "Failed to verify rate grid popup rate");
		testSteps.add("Verified Rate: <b>" + ruleRate + "</b>");
		logger.info("Verified Rate: " + ruleRate);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRuleLabel> ' Description: < Verify Popup Rule Label> '
	 * Input parameters: <WebDriver driver,ArrayList<String> testSteps, String
	 * labelName> ' Return value: <NA> ' Created By: <Gangotri Sikheria> ' ' Created
	 * On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String getRuleLabel(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String ruleLabel = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.popupRuleHeader);
		ruleLabel = elements.popupRuleHeader.getText();
		testSteps.add("Rule  Label: <b>" + elements.popupRuleHeader.getText() + "</b>");
		logger.info("Rule Label: " + elements.popupRuleHeader.getText());
		return ruleLabel;
	}

	public void verifyRuleLabel(WebDriver driver, ArrayList<String> testSteps, String labelName)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.popupRuleHeader);
		assertEquals(elements.popupRuleHeader.getText(), labelName, "Failed to verify rate grid popup label");
		testSteps.add("Verified Rule Label: <b>" + elements.popupRuleHeader.getText() + "</b>");
		logger.info("Verified  Rule Label: " + elements.popupRuleHeader.getText());
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRuleAppliedLabel> ' Description: < Verify Popup Rule
	 * Applied Label> ' Input parameters: <WebDriver driver,ArrayList<String>
	 * testSteps, String ruleAppliedLabel> ' Return value: <NA> ' Created By:
	 * <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String getRuleAppliedLabel(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String ruleAppliedLabel = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupRuleIndectionLabel);
		ruleAppliedLabel = elements.rateGridPopupRuleIndectionLabel.getText();
		testSteps.add("Rule  Applied Label: <b>" + elements.rateGridPopupRuleIndectionLabel.getText() + "</b>");
		logger.info("Rule Applied Label: " + elements.rateGridPopupRuleIndectionLabel.getText());
		return ruleAppliedLabel;
	}

	public void verifyRuleAppliedLabel(WebDriver driver, ArrayList<String> testSteps, String ruleAppliedLabel)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupRuleIndectionLabel);
		assertTrue(elements.ruleIndecationIcon.isDisplayed(), "Failed to Rule Applied Legend");
		assertEquals(elements.rateGridPopupRuleIndectionLabel.getText(), ruleAppliedLabel,
				"Failed to verify rate grid popup rule applied label");
		testSteps.add("Verified Rule  Applied Label: <b>" + elements.rateGridPopupRuleIndectionLabel.getText()

				+ "</b><b>-- Rule Applied Iocn is Displayed</b>");
		logger.info("Verified Rule  Applied Label: " + elements.rateGridPopupRuleIndectionLabel.getText()
				+ "Rule Applied Iocn is Displayed");

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyNightsMinimum> ' Description: < Verify Popup Rule
	 * Minimum Night> ' Input parameters: <WebDriver driver,ArrayList<String>
	 * testSteps, String minimumNights> ' Return value: <NA> ' Created By: <Gangotri
	 * Sikheria> ' ' Created On: <MM/dd/yyyy> <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyNightsMinimum(WebDriver driver, ArrayList<String> testSteps, String minimumNights)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupMinimumNights);
		Utility.ScrollToElement(elements.rateGridPopupMinimumNights, driver);
		minimumNights = "" + minimumNights + " night min";
		assertEquals(elements.rateGridPopupMinimumNights.getText(), minimumNights,
				"Failed to verify rate grid popup rule minimum nights");
		testSteps.add("Verified Minimum Nights: <b>" + elements.rateGridPopupRuleIndectionLabel.getText() + "</b>");
		logger.info("Verified   Minimum Nights: " + elements.rateGridPopupRuleIndectionLabel.getText());
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyMinStay> ' Description: < Verifying Min Stay of Day on
	 * which date for room class> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps, String weekDay, String dayNum,String
	 * roomClass, String channelName,String ruleAppliedOn,String nights> ' Return
	 * value: <NA> ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/02/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void verifyMinStay(WebDriver driver, ArrayList<String> testSteps, String weekDay, String dayNum,
			String roomClass, String channelName, String ruleAppliedOn, String nights) throws InterruptedException {
		String path = "//div[div[(text()='" + weekDay + "')]and div[(text()='" + dayNum + "')]]"
				+ "//ancestor::div//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName
				+ "']" + "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//input[@value='" + nights
				+ "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		assertTrue(element.isDisplayed(), "Failed to verify min stay nights");
		testSteps.add("Verified Min Stay Nights: <b>" + nights + "</b> For Room Class <b>" + roomClass
				+ "</b> for Channel <b>" + channelName + "</b>");
		logger.info(
				"Verified Min Stay Nights:" + nights + " For Room Class " + roomClass + " for Channel" + channelName);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyNoCheckIn> ' Description: < Verifying No Check In for
	 * the day and which date set for room class> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps, String weekDay, String dayNum,String
	 * roomClass, String channelName,String ruleAppliedOn> ' Return value: <NA> '
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void verifyNoCheckIn(WebDriver driver, ArrayList<String> testSteps, String weekDay, String dayNum,
			String roomClass, String channelName, String ruleAppliedOn) throws InterruptedException {
		String path = "//div[div[(text()='" + weekDay + "')]and div[(text()='" + dayNum + "')]]"
				+ "//ancestor::div//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName
				+ "']" + "/ancestor::div//div[text()='" + ruleAppliedOn
				+ "']/parent::div//div[@class='rt-onHover  enabled']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		assertTrue(element.isDisplayed(), "Failed to verify No Check In ");
		testSteps.add("Verified No Check In on Day <b>" + weekDay + " </b>and Date <b>" + dayNum
				+ "</b> For Room Class <b>" + roomClass + "</b>");
		logger.info("Verified No Check In on Day" + weekDay + "and Date" + dayNum + "For Room Class" + roomClass);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyNoCheckOut> ' Description: < Verifying No Checkout for
	 * the day and which date set for room class> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps, String weekDay, String dayNum,String
	 * roomClass, String channelName,String ruleAppliedOn> ' Return value: <NA> '
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyNoCheckOut(WebDriver driver, ArrayList<String> testSteps, String weekDay, String dayNum,
			String roomClass, String channelName, String ruleAppliedOn) throws InterruptedException {
		String path = "//div[div[(text()='" + weekDay + "')]and div[(text()='" + dayNum + "')]]"
				+ "//ancestor::div//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName
				+ "']" + "/ancestor::div//div[text()='" + ruleAppliedOn
				+ "']/parent::div//div[@class='rt-onHover  enabled']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		assertTrue(element.isDisplayed(), "Failed to verify No Check In ");
		testSteps.add("Verified No Check Out on Day <b>" + weekDay + " </b>and Date <b>" + dayNum
				+ "</b> For Room Class <b>" + roomClass + "</b>");
		logger.info("Verified No Check Out on Day" + weekDay + "and Date" + dayNum + "For Room Class" + roomClass);
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyNoCheckInAndOutColor> ' Description: < Verifying No
	 * Checkout for the day and which date set for room class> ' Input parameters: <
	 * WebDriver driver,ArrayList<String> testSteps, String weekDay, String
	 * dayNum,String roomClass, String channelName,String ruleAppliedOn,String
	 * colorName> ' Return value: <color> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String verifyNoCheckInAndOutColor(WebDriver driver, ArrayList<String> testSteps, String weekDay,
			String dayNum, String roomClass, String channelName, String ruleAppliedOn, String colorName)
			throws InterruptedException {
		String color = null;
		String path = "//div[div[(text()='" + weekDay + "')]and div[(text()='" + dayNum + "')]]"
				+ "//ancestor::div//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName
				+ "']" + "/ancestor::div//div[text()='" + ruleAppliedOn
				+ "']/parent::div//div[@class='rt-onHover  enabled']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		Utility.hoverOnElement(driver, element);
		color = element.getCssValue("color");
		assertEquals(color, colorName, "Failed to verify Color");
		testSteps.add("Verified Color <b>" + color + " </b>");
		logger.info("Verified Color " + color);
		return path;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <updateRoomRate> ' Description: < Update Rate Amount and
	 * verified message rate saved successfully> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps,String roomClass, String oldRate, String
	 * newRate> ' Return value: <New rates> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String updateRoomRate(WebDriver driver, ArrayList<String> testSteps, String roomClass, String oldRate,
			String newRate) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + oldRate
				+ "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		elements.rateGridRoomRate.clear();
		elements.rateGridRoomRate.sendKeys(newRate);
		testSteps.add("Enter New Rate: <b>" + newRate + "</b>");
		logger.info("Enter New Rate: " + newRate);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
		assertTrue(elements.rateSavedMessage.isDisplayed(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");
		String pathNewRate = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + newRate + "']";
		Wait.WaitForElement(driver, pathNewRate);
		WebElement elementOne = driver.findElement(By.xpath(pathNewRate));
		Utility.ScrollToElement(elementOne, driver);
		assertTrue(elementOne.isDisplayed(), "Failed to Verify New Rate");
		testSteps.add("Update Rate is : <b> " + newRate + " </b> Old Rate is: " + oldRate + "</b>");
		logger.info("Update Rate is :" + newRate + " Old Rate is: " + oldRate);
		return newRate;
	}

	public ArrayList<String> updateRate(WebDriver driver, ArrayList<String> testSteps, String roomClass)
			throws InterruptedException {
		ArrayList<String> ratesAre = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClass + "')]"
				+ "//parent::div//following-sibling::div/div[1])[contains(@class,'RegularRate')and not(contains(text(),'--'))]";
		List<WebElement> labelValues = null;
		String oldRate = null, newRate = null;
		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		if (isExist) {
			testSteps.add("========Update Rate for Room Class<b> " + roomClass + "</b>========");

			Wait.WaitForElement(driver, path);
			labelValues = driver.findElements(By.xpath(path));

			if (labelValues.size() > 0) {
				Utility.ScrollToViewElementINMiddle(driver, labelValues.get(0));
				Utility.clickThroughJavaScript(driver, labelValues.get(0));
				oldRate = getRoomRate(driver);
				ratesAre.add(oldRate);
				if (oldRate.contains(".")) {
					Double doubleValue = Double.parseDouble(oldRate) + 50;
					newRate = String.valueOf(doubleValue);
					ratesAre.add(newRate);
				} else {
					int newRates = Integer.parseInt(oldRate) + 50;
					newRate = String.valueOf(newRates);
					ratesAre.add(newRate);
				}

				elements.rateGridRoomRate.clear();
				elements.rateGridRoomRate.sendKeys(newRate);
				testSteps.add("Enter New Rate: <b>" + newRate + "</b>");
				logger.info("Enter New Rate: " + newRate);
				elements.rateGridSuccess.click();
				testSteps.add("Click on Success Icon");
				logger.info("Click on Success Icon");
				Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
				assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
				testSteps.add("Verified Message : <b> Rate saved successfully </b>");
				logger.info("Verified Message : Rate saved successfully");
				String pathNewRate = "//div[contains(text(),'" + roomClass
						+ "')]/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + newRate
						+ "']";
				Wait.WaitForElement(driver, pathNewRate);
				logger.info("Wait");
				WebElement elementOne = driver.findElement(By.xpath(pathNewRate));
				Utility.ScrollToElement(elementOne, driver);
				assertTrue(elementOne.isDisplayed(), "Failed to Verify New Rate");
				testSteps.add("Verified Override Rate at Class Level : <b> " + roomClass + " -- " + newRate
						+ " </b> And Old Rate is: " + oldRate + "</b>");
				logger.info("Verified Override Rate  at Class Level :" + roomClass + " -- " + newRate
						+ " And Old Rate is: " + oldRate);
			}
		}
		return ratesAre;
	}

	public ArrayList<String> updateRate(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate)
			throws InterruptedException {
		ArrayList<String> ratesAre = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClass + "')]"
				+ "//parent::div//following-sibling::div/div[1])[contains(@class,'RegularRate')and not(contains(text(),'--'))]";
		List<WebElement> labelValues = null;
		String oldRate = null, newRate = null;
		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		if (isExist) {
			testSteps.add("========Update Rate for Room Class<b> " + roomClass + "</b>========");

			Wait.WaitForElement(driver, path);
			labelValues = driver.findElements(By.xpath(path));

			if (labelValues.size() > 0) {
				Utility.ScrollToViewElementINMiddle(driver, labelValues.get(0));
				Utility.clickThroughJavaScript(driver, labelValues.get(0));
				oldRate = getRoomRate(driver);
				ratesAre.add(oldRate);
				if (oldRate.contains(".")) {
					Double doubleValue = Double.parseDouble(oldRate) + Integer.parseInt(rate);
					newRate = String.valueOf(doubleValue);
					ratesAre.add(newRate);
				} else {
					int newRates = Integer.parseInt(oldRate) + Integer.parseInt(rate);
					newRate = String.valueOf(newRates);
					ratesAre.add(newRate);
				}

				elements.rateGridRoomRate.clear();
				elements.rateGridRoomRate.sendKeys(newRate);
				testSteps.add("Enter New Rate: <b>" + newRate + "</b>");
				logger.info("Enter New Rate: " + newRate);
				elements.rateGridSuccess.click();
				testSteps.add("Click on Success Icon");
				logger.info("Click on Success Icon");
				Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
				assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
				testSteps.add("Verified Message : <b> Rate saved successfully </b>");
				logger.info("Verified Message : Rate saved successfully");
				String pathNewRate = "//div[contains(text(),'" + roomClass
						+ "')]/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + newRate
						+ "']";
				Wait.WaitForElement(driver, pathNewRate);
				WebElement elementOne = driver.findElement(By.xpath(pathNewRate));
				Utility.ScrollToElement(elementOne, driver);
				assertTrue(elementOne.isDisplayed(), "Failed to Verify New Rate");
				testSteps.add("Verified Override Rate at Class Level : <b> " + roomClass + " -- " + newRate
						+ " </b> And Old Rate is: " + oldRate + "</b>");
				logger.info("Verified Override Rate  at Class Level :" + roomClass + " -- " + newRate
						+ " And Old Rate is: " + oldRate);

			}

		}
		return ratesAre;
	}

	public void verifyOverRideRateAtChannellevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String rate) {
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";

		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertEquals(element.getText().trim(), rate.trim(), "Failed to verify rate at channel level");
		testSteps.add("Verified Override at Channel Level: <b>" + roomClass + "</b> -- <b>" + channelName
				+ " </b> -- <b>" + rate + "</b>");
		logger.info("Verified Override at Channel Level: " + roomClass + "--" + channelName + "--" + rate);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <updateExtraAdults> ' Description: < Update Extra Adults> '
	 * Input parameters: < WebDriver driver,ArrayList<String> testSteps,String
	 * roomClass, String rate, String adults> ' Return value: <NA> ' Created By:
	 * <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void updateExtraAdults(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,
			String adults) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.click();
		elements.rateGridExtraAd.clear();
		elements.rateGridExtraAd.sendKeys(adults);
		testSteps.add("Enter Extra Adults is: <b>" + adults + "</b>");
		logger.info("Enter Extra Adults is: " + adults);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.waitTillElementDisplayed(driver, OR_RateGrid.rateOverrideLoading);
		assertTrue(elements.rateSavedMessage.isDisplayed(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <updateExtraChild> ' Description: < Update Extra Child > '
	 * Input parameters: < WebDriver driver,ArrayList<String> testSteps,String
	 * roomClass, String rate, String child> ' Return value: <NA> ' Created By:
	 * <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void updateExtraChild(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,
			String child) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		element.click();
		elements.rateGridExtraCh.clear();
		elements.rateGridExtraCh.sendKeys(child);
		testSteps.add("Enter Extra Child is: <b>" + child + "</b>");
		logger.info("Enter Extra Child is: " + child);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.waitTillElementDisplayed(driver, OR_RateGrid.rateOverrideLoading);
		assertTrue(elements.rateSavedMessage.isDisplayed(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");

	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyOverrideRateColor> ' Description: < Verified OverRide
	 * Rate Color> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * testSteps,String roomClass,String rate, String colorName> ' Return value:
	 * <NA> ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void getRateColor(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,
			String colorName) throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		testSteps.add("Verified Ragular Rate Color <b>" + colorName + " </b>");
		logger.info("Verified Ragular Rate Color " + colorName);
	}

	public void getRateColor(WebDriver driver, ArrayList<String> testSteps, String roomClass)
			throws InterruptedException {
		String path = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClass + "')]"
				+ "//parent::div//following-sibling::div/div[1])[contains(@class,'RegularRate')and not(contains(text(),'--'))]";
		List<WebElement> labelValues = null;
		String colorName = null, rate = null;
		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		if (isExist) {
			testSteps.add("========Rate Available for Room Class<b> " + roomClass + "</b> Get Rate Color========");
			Wait.WaitForElement(driver, path);
			labelValues = driver.findElements(By.xpath(path));

			for (int i = 0; i < labelValues.size(); i++) {
				Utility.ScrollToViewElementINMiddle(driver, labelValues.get(i));
				rate = labelValues.get(i).getText();
				String color = labelValues.get(i).getCssValue("color");
				colorName = Color.fromString(color).asHex();
				testSteps.add("Rate:- <b>" + rate + " </b>Color is <b>" + colorName + " </b>");
				logger.info("Rate:- " + rate + " Color is " + colorName);
			}
		}
	}

	public void verifyRateColor(WebDriver driver, ArrayList<String> testSteps, String roomClass, String colorNames)
			throws InterruptedException {
		String path = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClass + "')]"
				+ "//parent::div//following-sibling::div/div[1])[contains(@class,'RegularRate')and not(contains(text(),'--'))]";
		List<WebElement> labelValues = null;
		String colorName = null, rate = null;
		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		if (isExist) {
			testSteps.add("========Get Room Rate Color for Room Class<b> " + roomClass + "</b>========");

			Wait.WaitForElement(driver, path);
			labelValues = driver.findElements(By.xpath(path));
			for (int i = 0; i < labelValues.size(); i++) {
				Utility.ScrollToViewElementINMiddle(driver, labelValues.get(i));
				rate = labelValues.get(i).getText();
				String color = labelValues.get(i).getCssValue("color");
				colorName = Color.fromString(color).asHex();
				assertEquals(colorName.toLowerCase().trim(), colorNames.toLowerCase().trim(), "Failed to verify Color");
				testSteps.add("Verified Rate " + rate + "Color <b>" + colorName + " </b>");
				logger.info("Verified  Rate Color " + colorName);

			}
		}
	}

	public void verifyRagularRateColor(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,
			String colorName) throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		colorName1 = colorName1.substring(0, colorName1.length() - 3);
		java.awt.Color color1 = java.awt.Color.decode(colorName1);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		String colorNameIs = util.getColorNameFromRgb(r, g, b);
		testSteps.add("Verified Ragular Rate Color <b>" + colorNameIs + " </b>");
		logger.info("Verified Ragular Rate Color " + colorNameIs);
	}

	public String getOverrideRateColor(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate)
			throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		testSteps.add("Get OverRide Rate Color <b>" + colorName1 + " </b>");
		logger.info("Get OverRide Rate Color " + colorName1);
		return colorName1;
	}

	public void verifyOverrideRateColor(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,
			String colorName) throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		colorName1 = colorName1.substring(0, colorName1.length() - 3);
		java.awt.Color color1 = java.awt.Color.decode(colorName1);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		String colorNameIs = util.getColorNameFromRgb(r, g, b);
		testSteps.add(
				"Verified OverRide Rate Color At Class Level <b>" + roomClass + "</b> -- <b>" + colorNameIs + " </b>");
		logger.info("Verified OverRide Rate Color At Class Level" + roomClass + "--" + colorNameIs);
	}

	public void verifyRegularRateColorAtChannelLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String rate, String colorName) throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		colorName1 = colorName1.substring(0, colorName1.length() - 3);
		java.awt.Color color1 = java.awt.Color.decode(colorName1);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		String colorNameIs = util.getColorNameFromRgb(r, g, b);
		testSteps.add("Verified Regular Rate Color at Channel Level<b>" + roomClass + "</b> -- <b>" + channelName
				+ "</b> -- <b>" + colorNameIs + " </b>");
		logger.info(
				"Verified Regular Rate Color at Channel Level" + roomClass + "--" + channelName + "--" + colorNameIs);
	}

	public void verifyOverrideRateColorAtChannelLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String rate, String colorName) throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		colorName1 = colorName1.substring(0, colorName1.length() - 3);
		java.awt.Color color1 = java.awt.Color.decode(colorName1);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		String colorNameIs = util.getColorNameFromRgb(r, g, b);
		testSteps.add("Verified OverRide Rate Color at Channel Level<b>" + roomClass + "</b> -- <b>" + channelName
				+ "</b> -- <b>" + colorNameIs + " </b>");
		logger.info(
				"Verified OverRide Rate Color at Channel Level" + roomClass + "--" + channelName + "--" + colorNameIs);
	}

	public void verifyRuleIndicationAndColor(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String colorName) throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[@class='RuleIndication']/span";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed to verify Rule Indication");
		testSteps.add("Verified Rule Indication Icon for Room Class <b>" + roomClass + " </b>");
		logger.info("Verified Rule Indication Icon for Room Class " + roomClass);
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		testSteps.add("Verified Rule Indication Icon Color <b>" + colorName + " </b>");
		logger.info("Verified Rule Indication Icon Color " + colorName);
	}

	public void verifyCheckInAndCheckoutColor(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn, String colorName) throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName + "']"
				+ "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//div[@class='rt-onHover  enabled']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		testSteps.add("Verified " + ruleAppliedOn + " Icon Color <b>" + colorName + " </b>");
		logger.info("Verified " + ruleAppliedOn + " Icon Color" + colorName);
	}

	public String getCheckInAndCheckoutColor(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn) throws InterruptedException {

		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName + "']"
				+ "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//div[@class='rt-onHover  enabled']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		testSteps.add(ruleAppliedOn + "  Color <b>" + colorName1 + " </b>");
		logger.info(ruleAppliedOn + "  Color" + colorName1);
		return colorName1;
	}

	public String getRegularRateColor(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate)
			throws InterruptedException {
		String path1 = "//div[contains(text(),'" + roomClass + "')]";
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath(path1)));
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		testSteps.add("Get Regular Rate Color <b>" + colorName1 + " </b>");
		logger.info("Get Regular Rate Color " + colorName1);
		return colorName1;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRateGridPopupRateChangeLabel> ' Description: < Verified
	 * Rate Change Label> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * testSteps, String rateChangeLabel> ' Return value: <NA> ' Created By:
	 * <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getRateChangeLabel(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String label = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupRateChangeHeader);
		label = elements.rateGridPopupRuleIndectionLabel.getText();
		testSteps.add("Rate Change Label: <b>" + elements.rateGridPopupRuleIndectionLabel.getText() + "</b>");
		logger.info("Rate Change  Label: " + elements.rateGridPopupRuleIndectionLabel.getText());
		return label;
	}

	public void verifyRateChangeLabel(WebDriver driver, ArrayList<String> testSteps, String rateChangeLabel)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridPopupRateChangeHeader);
		assertEquals(elements.rateGridPopupRuleIndectionLabel.getText(), rateChangeLabel,
				"Failed to verify Rate Change Label");
		testSteps.add("Verified Label: <b>" + elements.rateGridPopupRuleIndectionLabel.getText() + "</b>");
		logger.info("Verified Label: " + elements.rateGridPopupRuleIndectionLabel.getText());
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRulesLabels> ' Description: < Verified any Label Such
	 * as Updated by:, Updated on:, Previous price:,No check out, No check
	 * in,Previous price:,night min> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps, String label> ' Return value: <NA> '
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> getRulesLabels(WebDriver driver) throws InterruptedException {
		ArrayList<String> labels = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		for (WebElement ele : elements.ruleLabels) {
			labels.add(ele.getText());
		}
		logger.info("Rule Label Are:" + labels);
		return labels;
	}

	public String verifyRulesLabels(WebDriver driver, String label) throws InterruptedException {

		String path = "//span[@class='rules-labels'and contains(text(),'" + label + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed to Verify label");
		logger.info("Verified Label :" + label);
		return label;
	}

	public boolean checkAndGetLabelsfromList(WebDriver driver, ArrayList<String> list, String label)
			throws InterruptedException {
		boolean isExist = false;
		for (String element : list) {
			if (element == label) {
				isExist = true;
				break;
			}
		}

		// Print the result
		logger.info("Is " + label + " present in the List: " + isExist);
		return isExist;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyChangeValue> ' Description: < Verified Changed Value
	 * like Updated by:, Updated on:,Previous price:> ' Input parameters: <
	 * WebDriver driver,ArrayList<String> testSteps, String label> ' Return value:
	 * <NA> ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyChangeValue(WebDriver driver, String changeValue) throws InterruptedException {
		String path = "//span[@class='change-value'and contains(text(),'" + changeValue + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed to Verify Change Value");
		logger.info("Verified Change Value :" + changeValue);
	}

	public void verifyPreviousPrice(WebDriver driver, String changeValue) throws InterruptedException {
		String path = "//span[@class='change-value'and contains(normalize-space(.), '" + changeValue + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed to Verify Change Value");
		logger.info("Verified Change Value :" + element.getText());
	}

	public void verifyPreviousPrice(WebDriver driver, String ruleLabel, String price, ArrayList<String> testSteps)
			throws InterruptedException {
		String path = "//span[@class='rules-labels'and contains(text(),'" + ruleLabel + "')]"
				+ "/following-sibling::span[@class='change-value'and contains(normalize-space(.), '" + price + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		String priceIs = element.getText().replaceAll("[\\n\\t ]", "");
		logger.info(priceIs);
		String actualPrice = "$" + price;
		logger.info(actualPrice);
		assertEquals(priceIs, actualPrice, "Failed to verify Previous Price");
		testSteps.add("Verified Previoud Price : <b>" + priceIs + "</b>");
		logger.info("Verified Previoud Price :" + price);

	}

	public void verifyPreviousPfice(WebDriver driver, String changeValue) throws InterruptedException {
		String path = "//span[@class='change-value'and contains(normalize-space(.), '" + changeValue + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed to Verify Change Value");
		logger.info("Verified Change Value :" + changeValue);
	}

	public String getOverRideValues(WebDriver driver, String valueFor) throws InterruptedException {
		String value = null;
		String path = "//span[@class='rules-labels'and contains(text(),'" + valueFor + "')]/following-sibling::span";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		value = element.getText();
		logger.info(" Value Are :" + value);
		return value;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyImagesOnRulePopup> ' Description: < Verified all
	 * images> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * testSteps,String roomClass, String rate, String ruleName,String image> '
	 * Return value: <NA> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> getRuleImages(WebDriver driver) throws InterruptedException {
		ArrayList<String> images = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rulePropertyImage);
		for (WebElement element : elements.rulePropertyImage) {
			images.add(element.getAttribute("src"));
		}
		logger.info("Rule Images Are: " + images);
		return images;
	}

	public void verifyImagesOnRulePopup(WebDriver driver, ArrayList<String> testSteps, String ruleName, String image)
			throws InterruptedException {

		String imagePath = "//img[contains(@src,'" + image + "')]";
		Wait.WaitForElement(driver, imagePath);
		WebElement elementImage = driver.findElement(By.xpath(imagePath));
		assertTrue(elementImage.isDisplayed(), "Failed to Verify Images");
		if (ruleName.equals("Updated by")) {
			testSteps.add("Verified Updated by Image ");
			logger.info("Verified Updated by Image ");
		} else if (ruleName.equals("Updated on")) {
			testSteps.add("Verified Updated on Image ");
			logger.info("Verified Updated on Image ");
		} else if (ruleName.equals("night")) {
			testSteps.add("Verified Night Min Image ");
			logger.info("Verified Night Min Image ");
		} else if (ruleName.equals("NoCheckOut")) {
			testSteps.add("Verified No Check Out Image ");
			logger.info("Verified No Check Out Image ");
		} else if (ruleName.equals("NoCheckIn ")) {
			testSteps.add("Verified No Check In Image ");
			logger.info("Verified No Check In Image ");
		} else if (ruleName.equals("Previous price")) {
			String url = elementImage.getAttribute("src");
			testSteps.add("Verified " + ruleName + " Image:- " + "<img src='" + url + "' width='15' height='15'>");
			logger.info("Verified Previous price Image");
		}

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <removeOverRide> ' Description: <remoaved over ride> ' Input
	 * parameters: < WebDriver driver,ArrayList<String> testSteps,String roomClass,
	 * String rate> ' Return value: <NA> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void removeOverRide(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,
			String oldRate) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		elements.rateGridremoveOverRide.click();
		testSteps.add("Removed OverRide");
		logger.info("Removed OverRide");
		Wait.WaitForElement(driver, OR_RateGrid.rateOverrideMessage);
		assertTrue(elements.rateOverrideMessage.isEnabled(), "Failed to Verify Delete Message");

		testSteps.add("Verified Message : <b> Deleted rate override successfully </b>");
		logger.info("Verified Message : Deleted rate override successfully");
		String regulerRate = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + oldRate
				+ "']";
		WebElement elementRegular = driver.findElement(By.xpath(regulerRate));
		Utility.ScrollToElement(elementRegular, driver);
		assertTrue(elementRegular.isDisplayed(), "Failed to Verify Regular Rate");
		testSteps.add("Displayed old Rate : <b>" + oldRate + "</b>");
		logger.info("Displayed old Rate : " + oldRate);

	}

	public void removeOverRide(WebDriver driver, ArrayList<String> testSteps, String roomClass, String channelname,
			String rate, String oldRate) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelname + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		elements.rateGridremoveOverRide.click();
		testSteps.add("Remove OverRide");
		logger.info("Remove OverRide");
		Wait.WaitForElement(driver, OR_RateGrid.rateOverrideMessage);
		assertTrue(elements.rateOverrideMessage.isEnabled(), "Failed to Verify Delete Message");
		testSteps.add("Verified Message : <b> Deleted rate override successfully </b>");
		logger.info("Verified Message : Deleted rate override successfully");
		String regulerRate = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelname + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + oldRate + "']";
		WebElement elementRegular = driver.findElement(By.xpath(regulerRate));
		Utility.ScrollToElement(elementRegular, driver);
		assertTrue(elementRegular.isDisplayed(), "Failed to Verify Regular Rate");
		testSteps.add("Displayed old Rate : <b>" + oldRate + "</b>");
		logger.info("Displayed old Rate : " + oldRate);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <updateMinStay> ' Description: <Update Min Stay> ' Input
	 * parameters: < WebDriver driver,ArrayList<String> testSteps, String weekDay,
	 * String dayNum,String roomClass, String channelName,String
	 * ruleAppliedOn,String nights, String newNight> ' Return value: <NA> ' Created
	 * By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void updateRuleForMinStay(WebDriver driver, ArrayList<String> testSteps, String weekDay, String dayNum,
			String roomClass, String channelName, String ruleAppliedOn, String newNight)
			throws InterruptedException, ParseException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[div[(text()='" + weekDay + "')]and div[(text()='" + dayNum + "')]]"
				+ "//ancestor::div//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName
				+ "']" + "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//input[@value='']";

		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		testSteps.add("Click on Min Stay Box");
		element.clear();
		testSteps.add("Clear Min Stay ");
		element.sendKeys(newNight);
		testSteps.add("Input Min Stay : <b>" + newNight + "</b>");

		element.sendKeys(Keys.TAB);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isDisplayed(), "Failed to Verify Rule Saved Message");
		testSteps.add("Update Rule for <b>" + ruleAppliedOn + "</b> Message : <b> Rule saved successfully </b>");
		logger.info("Update Rule for " + ruleAppliedOn + "Message :  Rule saved successfully");
		String pathOne = "//div[div[(text()='" + weekDay + "')]and div[(text()='" + dayNum + "')]]"
				+ "//ancestor::div//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName
				+ "']" + "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//input[@value='" + newNight
				+ "']";
		WebElement elementOne = driver.findElement(By.xpath(pathOne));
		Utility.ScrollToElement(elementOne, driver);
		assertEquals(elementOne.getAttribute("value"), newNight, "Failed to Verify Updated Min Stay");
	}

	public void updateRuleForMinStay(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn, String newNight, int index)
			throws InterruptedException, ParseException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "(//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName + "']"
				+ "/ancestor::div//div[text()='Min Stay']/parent::div//input)[" + index + "]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		testSteps.add("Click on Min Stay Box");
		logger.info("Click on Min Stay Box");
		// element.clear();

		// testSteps.add("Clear Min Stay ");
		// element.sendKeys(newNight);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"), newNight);
		logger.info("Control and Delete");
		testSteps.add("Input Min Stay : <b>" + newNight + "</b>");
		element.sendKeys(Keys.TAB);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isDisplayed(), "Failed to Verify Rule Saved Message");
		testSteps.add("Update Rule for <b>" + ruleAppliedOn + "</b> Message : <b> Rule saved successfully </b>");
		logger.info("Update Rule for " + ruleAppliedOn + "Message :  Rule saved successfully");
		String pathOne = "//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName + "']"
				+ "/ancestor::div//div[text()='Min Stay']/parent::div//input[@value='" + newNight + "']";
		WebElement elementOne = driver.findElement(By.xpath(pathOne));
		Utility.ScrollToElement(elementOne, driver);
		assertEquals(elementOne.getAttribute("value"), newNight, "Failed to Verify Updated Min Stay");
	}

	public boolean getUpdateBoolValue(WebDriver driver, String roomClass, String channelName, String ruleAppliedOn) {
		String xpath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/following-sibling::div//input[not(@value='')]";
		boolean isExist = Utility.isElementPresent(driver, By.xpath(xpath));
		return isExist;
	}

	public ArrayList<String> updateRuleForMinStay(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn) throws InterruptedException, ParseException {

		ArrayList<String> minStayValue = new ArrayList<String>();
		String oldMinStayValue = null, newStayValue = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		String xpath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/following-sibling::div//input[not(@value='')]";

		List<WebElement> element = driver.findElements(By.xpath(xpath));
		minStayValue.add(oldMinStayValue);
		oldMinStayValue = element.get(0).getAttribute("value");
		Utility.ScrollToViewElementINMiddle(driver, element.get(0));
		element.get(0).click();
		testSteps.add("Click on Min Stay Box");

		int newStayValues = Integer.parseInt(oldMinStayValue) + 2;
		newStayValue = String.valueOf(newStayValues);
		minStayValue.add(newStayValue);

		element.get(0).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.get(0).sendKeys(Keys.BACK_SPACE);
		testSteps.add("Clear Min Stay ");
		element.get(0).sendKeys(newStayValue);
		testSteps.add("Input Min Stay : <b>" + newStayValue + "</b>");
		element.get(0).sendKeys(Keys.TAB);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isEnabled(), "Failed to Verify Rule Saved Message");
		testSteps.add("Update Rule for <b>" + ruleAppliedOn + "</b> Message : <b> Rule saved successfully </b>");
		logger.info("Update Rule for " + ruleAppliedOn + "Message :  Rule saved successfully");
		String pathOne = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/following-sibling::div//input[@value='" + newStayValue + "']";
		WebElement elementOne = driver.findElement(By.xpath(pathOne));

		Utility.ScrollToElement(elementOne, driver);
		assertEquals(elementOne.getAttribute("value"), newStayValue, "Failed to Verify Updated Min Stay");
		testSteps.add("Verified Updated Min Stay is :<b>" + newStayValue + " </b> Old Min Stay is: <b>"
				+ oldMinStayValue + "</b>");
		logger.info("Verified Updated Min Stay is :" + newStayValue + " Old Min Stay is: " + oldMinStayValue);

		return minStayValue;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <updateMinStay> ' Description: <Update Min Stay> ' Input
	 * parameters: < WebDriver driver,ArrayList<String> testSteps, String weekDay,
	 * String dayNum,String roomClass, String channelName,String
	 * ruleAppliedOn,String nights, String newNight> ' Return value: <NA> ' Created
	 * By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void updateRuleForNoCheckInAndOut(WebDriver driver, ArrayList<String> testSteps, String weekDay,
			String dayNum, String roomClass, String channelName, String ruleAppliedOn) throws InterruptedException {
		Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[div[(text()='" + weekDay + "')]and div[(text()='" + dayNum + "')]]"
				+ "//ancestor::div//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName
				+ "']" + "/ancestor::div//div[text()='" + ruleAppliedOn
				+ "']/parent::div//div[@class='rt-onHover  has-noValue enabled']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		element.click();
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isDisplayed(), "Failed to Verify Rule Saved Message");
		testSteps.add("Update Rule for <b>" + ruleAppliedOn + "</b> Message : <b> Rule saved successfully </b>");
		logger.info("Update Rule for " + ruleAppliedOn + "Message :  Rule saved successfully");

	}

	public void updateRuleForNoCheckInAndOut(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn) throws InterruptedException {
		Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/parent::div//div[@class='rt-onHover  has-noValue enabled']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		element.click();
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isDisplayed(), "Failed to Verify Rule Saved Message");
		testSteps.add("Update Rule for <b>" + ruleAppliedOn + " </b> Message : <b> Rule saved successfully </b>");
		logger.info("Update Rule for " + ruleAppliedOn + " Message :  Rule saved successfully");

	}

	public void updateRuleForNoCheckInAndOut(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn, int index) throws InterruptedException {
		Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "(//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']"
				+ "/parent::div//div[contains(@class,'rt-onHover') and contains(@class,'enabled')])[" + index + "]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		element.click();
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isDisplayed(), "Failed to Verify Rule Saved Message");
		testSteps.add("Update Rule for <b>" + ruleAppliedOn + " </b> Message : <b> Rule saved successfully </b>");
		logger.info("Update Rule for " + ruleAppliedOn + " Message :  Rule saved successfully");

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyEditIcon> ' Description: <Verify Edit Icon> ' Input
	 * parameters: < WebDriver driver,ArrayList<String> testSteps> ' Return value:
	 * <NA> ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyEditIcon(WebDriver driver, ArrayList<String> testSteps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		assertTrue(elements.rateEditIcon.isDisplayed(), "Failed to Edit icon");
		testSteps.add("Verified Edit Icon Displayed");
		logger.info("Verified Edit Icon Displayed");

	}

	public void clickEditIcon(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateEditIcon);
		Utility.ScrollToViewElementINMiddle(driver, elements.rateEditIcon);
		elements.rateEditIcon.click();
		testSteps.add("Click Edit Icon ");
		logger.info("Click Edit Icon");

	}

	public void verifyRatePlaninEditMode(WebDriver driver, ArrayList<String> testSteps, String ratePlan)
			throws InterruptedException {
		String path = "//a[@data-id='EDIT_RATEPLAN_TABID'and text()='" + ratePlan + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		assertTrue(element.isDisplayed(), "Failed to Edited rate Plan");
		testSteps.add("Edited Rate Plan is: <b>" + ratePlan + "</b>");
		logger.info("Edited Rate Plan is: " + ratePlan);

	}

	public void closeRatePlan(WebDriver driver, ArrayList<String> testSteps, String ratePlan)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePLanCloseIcon);
		Utility.ScrollToElement(elements.ratePLanCloseIcon, driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RateGrid.ratePLanCloseIcon), driver, 5);
		Utility.clickThroughJavaScript(driver, elements.ratePLanCloseIcon);
		testSteps.add("Close Rate Plan : <b>" + ratePlan + "</b>");
		logger.info("Close Rate Plan :" + ratePlan);
		Wait.WaitForElement(driver, OR_RateGrid.ratesTab);
		Wait.waitUntilPageLoadNotCompleted(driver, 30);
		testSteps.add("Navigated to RateGrid");
		logger.info("Navigated to RateGrid");

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyDeleteIcon> ' Description: <Verify Delete Icon> ' Input
	 * parameters: < WebDriver driver,ArrayList<String> testSteps> ' Return value:
	 * <NA> ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyDeleteIcon(WebDriver driver, ArrayList<String> testSteps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		assertTrue(elements.rateDeleteIcon.isDisplayed(), "Failed to Delete icon");
		testSteps.add("Verified Delete Icon Displayed");
		logger.info("Verified Delete Icon Displayed");

	}

	public void clickDeleteIcon(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateDeleteIcon);
		Utility.ScrollToViewElementINMiddle(driver, elements.rateDeleteIcon);

		elements.rateDeleteIcon.click();
		testSteps.add("Click Delete icon");
		logger.info("Click Delete icon");

	}

	public void verifyDeletedMsg(WebDriver driver, ArrayList<String> testSteps, String msg)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanDeleteMsg);
		Utility.ScrollToElement(elements.ratePlanDeleteMsg, driver);
		String message = elements.ratePlanDeleteMsg.getText().replaceAll("[\\n\\t ]", "");
		logger.info(message);
		msg = msg.replaceAll("[\\n\\t ]", "");
		assertEquals(msg, message, "Failed to verify deleted rate plan message");
		testSteps.add("Verified Message : <b>" + message + "</b>");
		logger.info("Verified Message : " + message);
	}

	public void clickDeleteButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateDeleteButton);
		Utility.ScrollToElement(elements.rateDeleteButton, driver);
		Utility.clickThroughJavaScript(driver, elements.rateDeleteButton);
		testSteps.add("Click Delete Button");
		logger.info("Click Delete Button");
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanDeleteMessage);
		Wait.waitForElementToBeClickable(By.xpath(OR_RateGrid.ratePlanDeleteMessage), driver);
		assertTrue(elements.ratePlanDeleteMessage.isEnabled(), "Failed to Verify Rate Plan Delete Message");
		testSteps.add("Verified  Message : <b> Rate Plan deleted successfully </b>");
		logger.info("Verified  Message : Rate Plan deleted successfully");
		Wait.WaitForElement(driver, OR_RateGrid.ratesTab);
		Wait.waitUntilPageLoadNotCompleted(driver, 30);
		logger.info("Navigated to RateGrid");

	}

	public void verifyDeletedRatePlan(WebDriver driver, ArrayList<String> testSteps, String ratePlan,
			ArrayList<String> list) {
		boolean isExist = !list.contains(ratePlan);
		if (isExist) {
			testSteps.add("Deleted Rate Plan Doesn't Exist: <b>" + ratePlan + "</b>");
			logger.info("Deleted Rate Plan Doesn't Exist: " + ratePlan);
		}

	}

	public boolean verifyRatePlanExist(WebDriver driver, ArrayList<String> testSteps, String ratePlan,
			ArrayList<String> list) {
		boolean isExist = list.contains(ratePlan);
		if (isExist) {
			testSteps.add(" Rate Plan  Exist in the list: <b>" + ratePlan + "</b> No Need to Create RatePlan");
			logger.info("Rate Plan Exist in the list: " + ratePlan + "No Need to Create RatePlan");
		} else if (!isExist) {
			testSteps.add(" Rate Plan Doesn't Exist in the list Create new RatePlan: <b>" + ratePlan + "</b>");
			logger.info(" Rate Plan Doesn't Exist in the list Create new RatePlan: " + ratePlan);
		}
		return isExist;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRatePlanType> ' Description: <Verify Rate Plan Type> '
	 * Input parameters: < WebDriver driver,ArrayList<String> testSteps,String
	 * ratePlanType> ' Return value: <NA> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifyRatePlanType(WebDriver driver, ArrayList<String> testSteps, String ratePlanType)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String ratePlantype = elements.ratePlanDesc.getText();
		Utility.ScrollToElement(elements.ratePlanDesc, driver);
		assertEquals(ratePlantype.toLowerCase().trim(), ratePlanType.toLowerCase().trim(),
				"Failed to verify Rate Plan Type");
		testSteps.add("Verified Rate Plan Type <b>" + ratePlanType + "</b>");
		logger.info("Verified Rate Plan Type " + ratePlanType);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRoomClassColor> ' Description: <Verified Room Class
	 * Color> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * testSteps,String roomClassName, String colorName> ' Return value: <NA> '
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String getRoomClassColor(WebDriver driver, String roomClassName) throws InterruptedException {
		String color = null;
		String path = "//div[contains(text(),'" + roomClassName + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		return colorName1;

	}

	public void verifyRoomClassColor(WebDriver driver, String roomClassName, String colorName)
			throws InterruptedException {
		String color = null;
		String path = "//div[contains(text(),'" + roomClassName + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(),
				"Failed to verify Color of specific room class");
		// testSteps.add("Verified Color <b>"+color+" </b>");
		logger.info("Verified Room Class Color " + color);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRateGridRoomClass> ' Description: <Get All Active Room
	 * Class of Rate Plan> ' Input parameters: < WebDriver driver> ' Return value:
	 * <ArrayList<String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> getRateGridRoomClass(WebDriver driver) {
		ArrayList<String> roomClasses = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRoomClass);
		for (WebElement element : elements.rateGridRoomClass) {
			roomClasses.add(element.getText());

		}
		logger.info("Room Class Are:  " + roomClasses);
		return roomClasses;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifiedRateGridAllActiveRoomClass> ' Description: <verified
	 * active Room Class> ' Input parameters: < WebDriver driver> ' Return value:
	 * <ArrayList<String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifiedRateGridAllActiveRoomClass(WebDriver driver, ArrayList<String> testSteps,
			ArrayList<String> roomClassesListOne, ArrayList<String> roomClassesListTwo) {

		Wait.WaitForElement(driver, OR_RateGrid.rateGridRoomClass);
		Collections.sort(roomClassesListOne);
		Collections.sort(roomClassesListTwo);
		boolean isEqual = roomClassesListOne.equals(roomClassesListTwo);
		logger.info(isEqual);
		assertEquals(isEqual, true, "Failed to active room class");
		testSteps.add("Verified Active Room Class");
		logger.info("Verified Active Room Class");

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifiedConditionsPromoCode> ' Description: <verified
	 * Condition promo Code> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps,String promoCode> ' Return value:
	 * <ArrayList<String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifiedConditionsPromoCode(WebDriver driver, ArrayList<String> testSteps, String promoCode)
			throws InterruptedException {
		String promocodeText = "Guest must enter promo code " + promoCode + "";
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Utility.ScrollToElement(elements.ratesTab, driver);
		String path = "//div[@class='rate-plan-description']/following-sibling::div";
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		String conditionText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;",
				element);
		logger.info(conditionText);
		assertTrue(conditionText.contains(promocodeText), "Failed to verify Rate Promo Code");
		testSteps.add("Verified Conditions PromoCode <b>" + promocodeText + "</b>");
		logger.info("Verified Conditions PromoCode " + promocodeText);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifiedConditionsStayNight> ' Description: <verified
	 * Condition Stay Night> ' Input parameters: < WebDriver
	 * driver,ArrayList<String> testSteps,String night> ' Return value:
	 * <ArrayList<String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/08/2020>
	 *
	 * 
	 * 
	 * 
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifiedConditionsStayNight(WebDriver driver, ArrayList<String> testSteps, String minNight,
			String maxNight, boolean minExist, boolean maxExist)
			throws InterruptedException, NumberFormatException, ParseException {
		testSteps.add("<b>============Verify RatePlan Condition Leangth Of Stay============</b>");
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String minNightText = "Guest must stay " + minNight + " nights";
		String minMaxNightText = "Guest must stay between " + minNight + " to " + maxNight + " nights";
		String minText = "Guest must stay at least " + minNight + " nights";
		String maxText = "Guest can only stay a maximum of " + maxNight + " nights";
		Utility.ScrollToViewElementINMiddle(driver, elements.conditionsDescription);
		String conditionText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;",
				elements.conditionsDescription);
		logger.info(conditionText);
		if (Utility.validateString(minNight) && Utility.validateString(maxNight)) {
			/* if(minNightText.contains(minNight)) */ if (minNight.equals(maxNight)) {
				assertTrue(conditionText.contains(minNightText), "Failed to verify Rate night text");
				testSteps.add("Verified Conditions <b>" + maxNight + "</b>");
				logger.info("Verified Conditions " + maxNight);
			}
			/* else if(minMaxNightText.contains(maxNight)) */ else if (Integer.parseInt(minNight) < Integer
					.parseInt(maxNight)) {
				assertTrue(conditionText.contains(minMaxNightText), "Failed to verify Rate night text");
				testSteps.add("Verified Conditions <b>" + minMaxNightText + "</b>");
				logger.info("Verified Conditions " + minMaxNightText);
			}
		} else if (minExist) {
			assertTrue(conditionText.contains(minText), "Failed to verify Rate night text");
			testSteps.add("Verified Conditions <b>" + minText + "</b>");
			logger.info("Verified Conditions  " + minText);
		} else if (maxExist) {
			assertTrue(conditionText.contains(maxText), "Failed to verify Rate night text");
			testSteps.add("Verified Conditions <b>" + maxText + "</b>");
			logger.info("Verified Conditions " + maxText);
		}
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifiedConditionsBookAdvanceDay> ' Description: <verified
	 * Condition Book Now> ' Input parameters: < WebDriver driver,ArrayList<String>
	 * testSteps,String minDay,String maxDay> ' Return value: <ArrayList<String>> '
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/08/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public void verifiedConditionsBookAdvanceDay(WebDriver driver, ArrayList<String> testSteps, String minDay,
			String maxDay, boolean moreThanExist, boolean withinExist)
			throws InterruptedException, NumberFormatException, ParseException {
		testSteps.add("<b>============Verify RatePlan Condition Booking Window============</b>");

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String bookingWindowText = "Guest must purchase " + minDay + " days in advance";
		String moreThanText = "Guest must purchase at least " + minDay + " days in advance";
		String advanceMaxText = "Guest must purchase " + minDay + " to " + maxDay + " days in advance";
		String withInText = "Guest must purchase not more than " + maxDay + " days in advance";
		Utility.ScrollToViewElementINMiddle(driver, elements.conditionsDescription);
		String conditionText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;",
				elements.conditionsDescription);
		String conditionTextSpace = conditionText.replaceAll("[\\n\\t ]", "");
		logger.info(conditionTextSpace);
		String moreThanText1 = moreThanText.replaceAll("[\\n\\t ]", "");
		String advanceMaxText1 = advanceMaxText.replaceAll("[\\n\\t ]", "");
		String bookingWindowText1 = bookingWindowText.replaceAll("[\\n\\t ]", "");
		String withInText1 = withInText.replaceAll("[\\n\\t ]", "");
		logger.info(moreThanText);
		logger.info(advanceMaxText);
		logger.info(bookingWindowText);
		logger.info(withInText);
		if (Utility.validateString(minDay) && Utility.validateString(maxDay)) {
			if (minDay.equals(maxDay)) {
				assertTrue(conditionTextSpace.contains(bookingWindowText1), "Failed to verify Rate advance text");
				testSteps.add("Verified Conditions <b>" + bookingWindowText + "</b>");
				logger.info("Verified Conditions" + bookingWindowText);
			} else if (Integer.valueOf(maxDay) > Integer.valueOf(minDay)) {
				assertTrue(conditionTextSpace.contains(advanceMaxText1), "Failed to verify Rate advance text");
				testSteps.add("Verified Conditions <b>" + advanceMaxText + "</b>");
				logger.info("Verified Conditions" + advanceMaxText);
			}
		} else if (moreThanExist) {
			assertTrue(conditionTextSpace.contains(moreThanText1), "Failed to verify Rate advance text");
			testSteps.add("Verified Conditions <b>" + moreThanText + "</b>");
			logger.info("Verified Conditions" + moreThanText);
		} else if (withinExist) {
			assertTrue(conditionTextSpace.contains(withInText1), "Failed to verify Rate advance text");
			testSteps.add("Verified Conditions <b>" + withInText + "</b>");
			logger.info("Verified Conditions" + withInText);

		}

	}

	public String getRatePlanDescription(WebDriver driver, ArrayList<String> testSteps) {
		String description = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanDesc);
		description = elements.ratePlanDesc.getText();
		testSteps.add("Rate Plan Description is <b>" + description + "</b>");
		logger.info("Rate Plan Description is" + description);
		return description;
	}

	public void verifyRatePlanDescription(WebDriver driver, String ratePlanType, ArrayList<String> testSteps,
			String night) {
		testSteps.add("<b>============Verify Rate Plan Description============</b>");
		String description = getRatePlanDescription(driver, testSteps);
		if (ratePlanType.equalsIgnoreCase("Interval Rate Plan")) {
			String intervalRatePlanDesc = "Guest must stay in " + night
					+ " night intervals. Rates displayed below are for the full interval";
			assertTrue(description.contains(intervalRatePlanDesc), "Failed to verify Rate Plan Description");
			testSteps.add("Verified Description <b>" + intervalRatePlanDesc + "</b>");
			logger.info("Verified Description" + intervalRatePlanDesc);
		}
	}

	public String getRateConditionsDescription(WebDriver driver, ArrayList<String> testSteps) {
		String conditionDesc = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		boolean isExist = elements.ratePlanConditionDesc.isDisplayed();
		if (isExist) {
			testSteps.add("<b>============Get Rate Plan Conditions============</b>");
			conditionDesc = elements.ratePlanConditionDesc.getText();
			testSteps.add("Rate Plan Conditions Description is <b>" + conditionDesc + "</b>");
			logger.info("Rate Plan Conditions Description is" + conditionDesc);
		}

		return conditionDesc;
	}

	public String getDefaultRatePlan(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String defaultRatePlan = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.defaultRatePlan);
		defaultRatePlan = elements.defaultRatePlan.getText();
		testSteps.add("Default Rate Plan is : <b>" + defaultRatePlan + "</b>");
		logger.info("Default Rate Plan is :" + defaultRatePlan);
		return defaultRatePlan;
	}

	public String getRoomClassDataValues(WebDriver driver, int index, String roomClass) throws InterruptedException {
		String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and @title='" + roomClass
				+ "']//parent::div//following-sibling::div/div[1])";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		return labelValues.get(index).getText();
	}

	public ArrayList<String> getRoomClassValues(WebDriver driver, String roomClass) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClass + "')]//parent::div//following-sibling::div/div[1])";
		Wait.waitUntilPresenceOfElementLocated(xpath, driver);
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));

		for (WebElement ele : labelValues) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			data.add(ele.getText());
		}

		return data;
	}

	public ArrayList<String> getRoomClassAvailibilityDataValues(WebDriver driver, String roomClassName)
			throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName
				+ "')]//parent::div/parent::div//following-sibling::div[contains(@class,'rate-grid-cell')]//div";
		logger.info(xpath);
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));

		for (WebElement ele : labelValues) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			logger.info(ele.getText());
			data.add(ele.getText());
		}
		logger.info(data);
		return data;
	}

	public ArrayList<String> getChannelDataValues(WebDriver driver, String roomClassName, String channelName)
			throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]"
				+ "//div[@title='" + channelName + "']//parent::div//following-sibling::div/div[1])";
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		for (WebElement ele : labelValues) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			data.add(ele.getText());
		}
		logger.info(data);
		return data;
	}

	public void getRuleAppliedForRoomClass(WebDriver driver, String roomClassName, ArrayList<String> testSteps)
			throws InterruptedException {
		String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]" + "//parent::div//following-sibling::div/div[1])/following-sibling::div";
		List<WebElement> labelValues = null;
		String date = null, dayNum = null, weekDay = null;
		ArrayList<String> ruleLabels = new ArrayList<String>();
		ArrayList<String> ruleImages = new ArrayList<String>();
		boolean isExist = Utility.isElementPresent(driver, By.xpath(xpath));
		if (isExist) {
			testSteps.add("============Rule Applied For <b>" + roomClassName + "</b>============");

			Wait.WaitForElement(driver, xpath);
			labelValues = driver.findElements(By.xpath(xpath));
			for (int i = 0; i < labelValues.size(); i++) {
				Utility.ScrollToViewElementINMiddle(driver, labelValues.get(i));
				Utility.hoverOnElement(driver, labelValues.get(i));
				date = getRuleDate(driver, testSteps);
				String[] array = date.split(",");
				dayNum = array[0];
				logger.info(dayNum);
				String[] array1 = array[1].split(" ");
				weekDay = array1[2];
				logger.info(weekDay);
				getRuleRate(driver, testSteps);
				ruleLabels = getRulesLabels(driver);
				ruleImages = getRuleImages(driver);
				for (int j = 0; j < ruleImages.size(); j++) {
					testSteps.add("<img src='" + ruleImages.get(j) + "' width='15' height='15'>" + "<b>-- "
							+ ruleLabels.get(j) + " </b>");
				}
				testSteps.add("Rule Applied ON Week Day:- <b>" + dayNum + " </b>Date:- <b>" + weekDay + "</b>");
				testSteps.add("-:--:--:--:--:--:--:--:--:--:--:--:--:--:--:--:--:--:--:--:--:--:-");

			}

		}

	}

	public String getRoomRate(WebDriver driver) {
		String roomRate = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		roomRate = elements.rateGridRoomRate.getAttribute("value");
		return roomRate;
	}

	public String getExtraAdult(WebDriver driver) {
		String adult = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		adult = elements.rateGridExtraAd.getAttribute("value");
		return adult;
	}

	public String getExtraChild(WebDriver driver) {
		String child = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		child = elements.rateGridExtraCh.getAttribute("value");
		return child;
	}

	public void getOverRideAndNonOverrideValueForRoomClass(WebDriver driver, String roomClassName,
			ArrayList<String> testSteps) throws InterruptedException {
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]" + "//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))]";
		List<WebElement> labelValues = null;
		String roomRate = null, extraChild = null, extraAdult = null;
		boolean isExist = Utility.isElementPresent(driver, By.xpath(xpath));
		if (isExist) {
			testSteps.add("========Get Room Rate, Extra Adult and Extra Child For <b>" + roomClassName + "</b>======");

			Wait.WaitForElement(driver, xpath);
			labelValues = driver.findElements(By.xpath(xpath));
			for (int i = 0; i < labelValues.size(); i++) {
				Utility.ScrollToViewElementINMiddle(driver, labelValues.get(i));
				Utility.clickThroughJavaScript(driver, labelValues.get(i));
				roomRate = getRoomRate(driver);
				extraAdult = getExtraAdult(driver);
				if (extraChild.isEmpty()) {
					extraChild = "0";
				}
				extraChild = getExtraChild(driver);
				if (extraAdult.isEmpty()) {
					extraAdult = "0";
				}
				if (extraChild.isEmpty() && extraAdult.isEmpty()) {
					testSteps.add("Room Rate:- <b>" + roomRate + " </b> Extra Adults:- <b>" + extraAdult
							+ "NA </b> Extra Child:- <b>" + extraChild + "NA </b>");

				} else if (extraChild.isEmpty()) {
					testSteps.add("Room Rate:- <b>" + roomRate + " </b> Extra Adults:- <b>" + extraAdult
							+ " </b> Extra Child:- <b>" + extraChild + "NA </b>");

				} else if (extraAdult.isEmpty()) {
					testSteps.add("Room Rate:- <b>" + roomRate + " </b> Extra Adults:- <b>" + extraAdult
							+ "NA </b> Extra Child:- <b>" + extraChild + " </b>");

				} else {
					testSteps.add("Room Rate:- <b>" + roomRate + " </b> Extra Adults:- <b>" + extraAdult
							+ " </b> Extra Child:- <b>" + extraChild + " </b>");

				}

			}

		}

	}

	public ArrayList<String> getOverRideValueForRoomClass(WebDriver driver, String roomClassName,
			ArrayList<String> extraAdults, ArrayList<String> extraChilds, ArrayList<String> dayNum)
			throws InterruptedException {
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]" + "//parent::div//following-sibling::div/div[contains(@class,'Override')]";
		ArrayList<String> roomRates = new ArrayList<String>();
		List<WebElement> overRideList = null;
		String roomRate = null, extraChild = null, extraAdult = null;
		Wait.WaitForElement(driver, xpath);
		overRideList = driver.findElements(By.xpath(xpath));
		for (int i = 0; i < overRideList.size(); i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.clickThroughJavaScript(driver, overRideList.get(i));
			roomRate = getRoomRate(driver);
			extraAdult = getExtraAdult(driver);
			extraChild = getExtraChild(driver);
			roomRates.add(roomRate);
			extraAdults.add(extraAdult);
			extraChilds.add(extraChild);
		}
		for (int i = 0; i < overRideList.size(); i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.hoverOnElement(driver, overRideList.get(i));
			String date = getRuleDate(driver);
			String[] array = date.split(",");
			String[] array1 = array[1].split(" ");
			dayNum.add(array1[2]);

		}
		logger.info(roomRates);
		logger.info(extraAdults);
		logger.info(extraChilds);
		logger.info(dayNum);
		return roomRates;

	}

	public ArrayList<String> getOverRideValueForChannel(WebDriver driver, String roomClassName, String channelName,
			ArrayList<String> extraAdults, ArrayList<String> extraChilds, ArrayList<String> dayNum)
			throws InterruptedException {
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]"
				+ "//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]" + "//div[@title='"
				+ channelName + "']//parent::div//following-sibling::div/div[contains(@class,'Override')]";
		ArrayList<String> roomRates = new ArrayList<String>();
		List<WebElement> overRideList = null;
		String roomRate = null, extraChild = null, extraAdult = null;
		Wait.WaitForElement(driver, xpath);
		overRideList = driver.findElements(By.xpath(xpath));
		for (int i = 0; i < overRideList.size(); i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.clickThroughJavaScript(driver, overRideList.get(i));
			roomRate = getRoomRate(driver);
			extraAdult = getExtraAdult(driver);
			extraChild = getExtraChild(driver);
			roomRates.add(roomRate);
			extraAdults.add(extraAdult);
			extraChilds.add(extraChild);
		}
		for (int i = 0; i < overRideList.size(); i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.hoverOnElement(driver, overRideList.get(i));
			String date = getRuleDate(driver);
			String[] array = date.split(",");
			String[] array1 = array[1].split(" ");
			dayNum.add(array1[2]);

		}
		logger.info(roomRates);
		logger.info(extraAdults);
		logger.info(extraChilds);
		logger.info(dayNum);
		return roomRates;

	}

	public ArrayList<String> getRateExAdExChForRoomClass(WebDriver driver, String roomClassName,
			ArrayList<String> extraAdults, ArrayList<String> extraChilds, ArrayList<String> dayNum)
			throws InterruptedException {
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]" + "//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))]";

		ArrayList<String> roomRates = new ArrayList<String>();
		List<WebElement> overRideList = null;
		String roomRate = null, extraChild = null, extraAdult = null;
		Wait.WaitForElement(driver, xpath);
		overRideList = driver.findElements(By.xpath(xpath));
		for (int i = 0; i < overRideList.size(); i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.clickThroughJavaScript(driver, overRideList.get(i));
			roomRate = getRoomRate(driver);
			extraAdult = getExtraAdult(driver);
			extraChild = getExtraChild(driver);
			roomRates.add(roomRate);
			extraAdults.add(extraAdult);
			extraChilds.add(extraChild);
		}
		for (int i = 0; i < overRideList.size(); i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.hoverOnElement(driver, overRideList.get(i));
			String date = getRuleDate(driver);
			String[] array = date.split(",");
			String[] array1 = array[1].split(" ");
			dayNum.add(array1[2]);

		}
		logger.info(roomRates);
		logger.info(extraAdults);
		logger.info(extraChilds);
		logger.info(dayNum);
		return roomRates;

	}

	public ArrayList<String> getRateExAdExChForChannel(WebDriver driver, String roomClassName, String channelName,
			ArrayList<String> extraAdults, ArrayList<String> extraChilds, ArrayList<String> dayNum)
			throws InterruptedException {
		String xpath = "//div[@class='roomClassName'and contains(@title,'" + roomClassName + "')]"
				+ "//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]" + "//div[@title='"
				+ channelName + "']//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))]";
		ArrayList<String> roomRates = new ArrayList<String>();
		List<WebElement> overRideList = null;
		String roomRate = null, extraChild = null, extraAdult = null;
		Wait.WaitForElement(driver, xpath);
		overRideList = driver.findElements(By.xpath(xpath));
		for (int i = 0; i < overRideList.size(); i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.clickThroughJavaScript(driver, overRideList.get(i));
			roomRate = getRoomRate(driver);
			extraAdult = getExtraAdult(driver);
			extraChild = getExtraChild(driver);
			roomRates.add(roomRate);
			extraAdults.add(extraAdult);
			extraChilds.add(extraChild);
			String date = getRateDate(driver);
			String[] array = date.split(",");
			String[] array1 = array[1].split(" ");
			dayNum.add(array1[2]);
		}
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Utility.ScrollToElement(elements.rateGridDanger, driver);
		Utility.clickThroughJavaScript(driver, elements.rateGridDanger);
		logger.info(roomRates);
		logger.info(extraAdults);
		logger.info(extraChilds);
		logger.info(dayNum);
		return roomRates;

	}

	public ArrayList<String> getRuleDataValuesForMinStay(WebDriver driver, String roomClassName, String channelName,
			String ruleName) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
				+ "//../..//following-sibling::div//div[@title='" + channelName + "']"
				+ "//../..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='" + ruleName
				+ "']/following-sibling::div//input";
		Wait.WaitForElement(driver, xpath);
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		for (WebElement ele : labelValues) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			data.add(ele.getAttribute("value"));
		}
		logger.info(data);
		return data;
	}

	public ArrayList<String> getRuleDataValueForCheckInCheckOut(WebDriver driver, String roomClassName,
			String channelName, String ruleName) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
				+ "//../..//following-sibling::div//div[@title='" + channelName + "']"
				+ "//../..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='" + ruleName
				+ "']/following-sibling::div/div/div";
		Wait.WaitForElement(driver, xpath);
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		for (WebElement ele : labelValues) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			data.add(ele.getText());
		}
		logger.info(data);
		return data;
	}

	public void verifyAvailibilityDisabled(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_RateGrid.availibilityLabel));
		if (!isExist) {
			testSteps.add(" Availibility Disabled");
			logger.info(" Availibility Disabled");
		}
	}

	public void verifyAvailibilityEnabled(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		boolean isExist = Utility.isElementPresent(driver, By.xpath(OR_RateGrid.availibilityLabel));
		if (isExist) {
			testSteps.add(" Availibility Enabled");
			logger.info(" Availibility Enabled");
		}
	}

	public boolean verifyAdultsDisabled(WebDriver driver, String roomClass) throws InterruptedException {
		List<WebElement> labelValues = null;
		boolean adultsIsExist = false;
		String path = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClass + "')]"
				+ "//parent::div//following-sibling::div/div[1])[contains(@class,'RegularRate')and not(contains(text(),'--'))]";
		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		if (isExist) {
			Wait.WaitForElement(driver, path);
			labelValues = driver.findElements(By.xpath(path));
			Utility.ScrollToViewElementINMiddle(driver, labelValues.get(0));
			Utility.clickThroughJavaScript(driver, labelValues.get(0));
			adultsIsExist = Utility.isElementPresent(driver, By.xpath(OR_RateGrid.rateGridExtraAd));
		}
		return adultsIsExist;
	}

	public boolean verifyChildsDisabled(WebDriver driver) throws InterruptedException {
		boolean childIsExist = false;
		childIsExist = Utility.isElementPresent(driver, By.xpath(OR_RateGrid.rateGridExtraCh));
		return childIsExist;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getAvailabilityOfRoomClass> ' Description: <Get Availability
	 * Specific Room Class as per Start Date and End Date> ' Input parameters: <
	 * WebDriver driver,String startDate,String endDate, String roomClass> ' Return
	 * value: <HashMap<String, String>> ' Created By: <Gangotri Sikheria> ' '
	 * Created On: <MM/dd/yyyy> <07/20/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public HashMap<String, String> getAvailabilityOfRoomClass(WebDriver driver, String startDate, String endDate,
			String roomClass) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> availibility = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> availibilityDate = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		availibility = getRoomClassAvailibilityDataValues(driver, roomClass);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					availibilityDate.put(dayList.get(j), availibility.get(i));
				}
			}

		}
		logger.info("Rate Grid Availibility as per  Date  " + availibilityDate);
		return availibilityDate;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRatesOfRoomClass> ' Description: <Get Rate of Specific
	 * Room Class as per Start Date and End Date> ' Input parameters: < WebDriver
	 * driver,String startDate,String endDate, String roomClass> ' Return value:
	 * <HashMap<String, String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/20/2020> ' Condition: if there is no Rate against any date
	 * then displayed NA
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public HashMap<String, String> getRatesOfRoomClass(WebDriver driver, String startDate, String endDate,
			String roomClass) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> roomClassRates = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> rates = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		Wait.waitUntilPresenceOfElementLocated(OR_RatesGrid.rateGridDay, driver);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		roomClassRates = getRoomClassValues(driver, roomClass);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					if (roomClassRates.get(i).equals("--")) {
						rates.put(dateList.get(j), "NA");
					} else {
						rates.put(dateList.get(j), roomClassRates.get(i));
					}

				}

			}

		}
		logger.info("Rates as per  Date  " + rates);
		return rates;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRatesOfChannel> ' Description: <Get Rate of Specific Room
	 * Class as per Start Date and End Date> ' Input parameters: < WebDriver
	 * driver,String startDate,String endDate, String roomClass,String channelName>
	 * ' Return value: <HashMap<String, String>> ' Created By: <Gangotri Sikheria> '
	 * ' Created On: <MM/dd/yyyy> <07/20/2020> ' Condition: if there is no Rate
	 * against any date then displayed NA
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public HashMap<String, String> getRatesOfChannel(WebDriver driver, String startDate, String endDate,
			String roomClass, String channelName) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> channelRates = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> rates = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		String[] multipleDates = startDate.split("\\|");

		if (multipleDates.length == 1) {

			Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
			logger.info("Start Date: " + fromDate);
			Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
			logger.info("End Date: " + toDate);
			dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
			logger.info("Dates Are: " + dates);
		} else {
			for (int i = 0; i < multipleDates.length; i++) {
				Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", multipleDates[i]);
				dates.add(fromDate);
			}

		}
		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		channelRates = getChannelDataValues(driver, roomClass, channelName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {

					if (channelRates.get(i).equals("--")) {
						rates.put(dateList.get(j), "NA");
					} else {
						rates.put(dateList.get(j), channelRates.get(i));
					}
				}

			}

		}
		logger.info("Rates as per  Date  " + rates);
		return rates;

	}

	public HashMap<String, String> getOverrideRatesOfChannel(WebDriver driver, String startDate, String endDate,
			String roomClass, String channelName) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> channelRates = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> rates = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		String[] multipleDates = startDate.split("\\|");

		if (multipleDates.length == 1) {

			Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
			logger.info("Start Date: " + fromDate);
			Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
			logger.info("End Date: " + toDate);
			dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
			logger.info("Dates Are: " + dates);
		} else {
			for (int i = 0; i < multipleDates.length; i++) {
				Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", multipleDates[i]);
				dates.add(fromDate);
			}

		}
		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		channelRates = getChannelDataValues(driver, roomClass, channelName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {

					if (channelRates.get(i).equals("--")) {
						rates.put(dateList.get(j), "NA");
					} else {
						rates.put(dateList.get(j), channelRates.get(i));
					}
				}

			}

		}
		logger.info("Rates as per  Date  " + rates);
		return rates;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getMinStayRulesOfChannel> ' Description: <Get Min Stay of
	 * Specific Channel as per Start Date and End Date> ' Input parameters: <
	 * WebDriver driver,String startDate,String endDate, String roomClass,String
	 * channelName,String headerName> ' Return value: <HashMap<String, String>> '
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/20/2020> '
	 * Condition: if there is no Min Stay against any date then displayed NA
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public HashMap<String, String> getMinStayRulesOfChannel(WebDriver driver, String startDate, String endDate,
			String roomClass, String channelName, String headerName) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> rulesValue = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> rules = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		rulesValue = getRuleDataValuesForMinStay(driver, roomClass, channelName, headerName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {

					if (rulesValue.get(i).isEmpty()) {
						rules.put(dateList.get(j), "NA");
					} else {
						rules.put(dateList.get(j), rulesValue.get(i));
					}
				}

			}

		}
		logger.info("Rule as per  Date  " + rules);
		return rules;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getCheckInCheckOutRulesOfChannel> ' Description: <Get Checkin
	 * and Checkout Rule of Specific Channel as per Start Date and End Date> ' Input
	 * parameters: < WebDriver driver,String startDate,String endDate, String
	 * roomClass,String channelName,String headerName> ' Return value:
	 * <HashMap<String, String>> ' Created By: <Gangotri Sikheria> ' ' Created On:
	 * <MM/dd/yyyy> <07/20/2020> ' Condition: if there is no Checkin and Checkout
	 * Rule against any date then displayed NA and Whatever Rule against any date
	 * then displayed YES instead of "?"
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public HashMap<String, String> getCheckInCheckOutRulesOfChannel(WebDriver driver, String startDate, String endDate,
			String roomClass, String channelName, String headerName) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> rulesValue = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> rules = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		rulesValue = getRuleDataValueForCheckInCheckOut(driver, roomClass, channelName, headerName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {

					if (rulesValue.get(i).isEmpty()) {
						rules.put(dateList.get(j), "NA");
					} else {
						rules.put(dateList.get(j), "YES");
					}
				}

			}

		}
		logger.info("Rule as per  Date  " + rules);
		return rules;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRoomRatesExAdExChOfRoomClass> ' Description: <Get Room
	 * Rate, Extra Adults and Extra Child of Specific Room Class as per Start Date
	 * and End Date> ' Input parameters: < WebDriver driver,String startDate,String
	 * endDate, String roomClass> ' Return value: <HashMap<String, String>> '
	 * Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/20/2020> '
	 * Condition: if there is no Room Rate, Extra Adult and Extra Child against any
	 * date then displayed NA.
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public HashMap<String, String> getRoomRatesExAdExChOfRoomClass(WebDriver driver, String startDate, String endDate,
			String roomClass) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> roomRates = new ArrayList<String>();
		ArrayList<String> extraAdults = new ArrayList<String>();
		ArrayList<String> extraChilds = new ArrayList<String>();
		ArrayList<String> dayListOfOverRide = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> roomRatesExtraAdultsExtraChild = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		roomRates = getRateExAdExChForRoomClass(driver, roomClass, extraAdults, extraChilds, dayListOfOverRide);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {

				for (int k = 0; k < dayListOfOverRide.size(); k++) {
					if (datesList.get(i).equals(dayList.get(j))) {
						if (dayList.get(j).equals(dayListOfOverRide.get(k))) {
							if (extraAdults.get(k).isEmpty() && extraChilds.get(k).isEmpty()) {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j),
										" RRate: " + roomRates.get(k) + " ExCh: NA ExAd: NA");

							} else if (extraAdults.get(k).isEmpty()) {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j),
										" RRate: " + roomRates.get(k) + " ExCh: NA ExAd: " + extraChilds.get(k));
							} else if (extraChilds.get(k).isEmpty()) {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j),
										" RRate: " + roomRates.get(k) + " ExCh: " + extraAdults.get(k) + " ExAd: NA");
							} else {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j), " RRate: " + roomRates.get(k)

										+ " ExCh: " + extraAdults.get(k) + " ExAd: " + extraChilds.get(k));

							}
						}

					}

				}

			}

		}
		logger.info("Rule as per  Date  " + roomRatesExtraAdultsExtraChild);
		return roomRatesExtraAdultsExtraChild;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRoomRatesExAdExChOfChannel> ' Description: <Get Room Rate,
	 * Extra Adults and Extra Child of Specific Room Class as per Start Date and End
	 * Date> ' Input parameters: < WebDriver driver,String startDate,String endDate,
	 * String roomClass,String channelName> ' Return value: <HashMap<String,
	 * String>> ' Created By: <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy>
	 * <07/20/2020> ' Condition: if there is no Room Rate, Extra Adult and Extra
	 * Child against any date then displayed NA.
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public HashMap<String, String> getRoomRatesExAdExChOfChannel(WebDriver driver, String startDate, String endDate,
			String roomClass, String channelName) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> roomRates = new ArrayList<String>();
		ArrayList<String> extraAdults = new ArrayList<String>();
		ArrayList<String> extraChilds = new ArrayList<String>();
		ArrayList<String> dayListOfOverRide = new ArrayList<String>();
		ArrayList<String> days = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> roomRatesExtraAdultsExtraChild = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		roomRates = getRateExAdExChForChannel(driver, roomClass, channelName, extraAdults, extraChilds,
				dayListOfOverRide);

		String strPattern = "^0+(?!$)";
		for (String str : dayListOfOverRide) {
			days.add(str.replaceAll(strPattern, ""));
		}
		logger.info("Day's Are: " + days);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				for (int k = 0; k < dayListOfOverRide.size(); k++) {
					if (datesList.get(i).equals(dayList.get(j))) {
						/* if (dayList.get(j).equals(dayListOfOverRide.get(k))) */
						if (dayList.get(j).equals(days.get(k))) {

							if (extraAdults.get(k).isEmpty() && extraChilds.get(k).isEmpty()) {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j),
										" RRate: " + roomRates.get(k) + " ExCh: NA ExAd: NA");

							} else if (extraAdults.get(k).isEmpty()) {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j),
										" RRate: " + roomRates.get(k) + " ExCh: NA ExAd: " + extraChilds.get(k));
							} else if (extraChilds.get(k).isEmpty()) {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j),
										" RRate: " + roomRates.get(k) + " ExCh: " + extraAdults.get(k) + " ExAd: NA");
							} else {
								roomRatesExtraAdultsExtraChild.put(dateList.get(j), " RRate: " + roomRates.get(k)

										+ " ExCh: " + extraAdults.get(k) + " ExAd: " + extraChilds.get(k));

							}
						}

					}

				}

			}

		}
		logger.info("Rates as per  Date  " + roomRatesExtraAdultsExtraChild);
		return roomRatesExtraAdultsExtraChild;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectUpdateAvailability> ' Description: <This method will
	 * select channel in availability poup in bulk update> ' Input parameters:
	 * <WebDriver driver, String channel, String updateAvailability)> ' Return value
	 * : <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On:
	 * <07/13/2020>
	 * 
	 */

	public ArrayList<String> selectUpdateAvailability(WebDriver driver, String channel, String updateAvailability)
			throws InterruptedException {

		String updateAvailabilityPath = "//div[contains(text(),'" + channel
				+ "')]//following-sibling::div//span[contains(text(),'" + updateAvailability + "')]";
		rateGridLogger.info("updateAvailabilityPath :  " + updateAvailabilityPath);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, updateAvailabilityPath);
		Wait.waitForElementToBeVisibile(By.xpath(updateAvailabilityPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(updateAvailabilityPath), driver);
		WebElement updateAvailabilityElement = driver.findElement(By.xpath(updateAvailabilityPath));
		Utility.ScrollToElement_NoWait(updateAvailabilityElement, driver);
		updateAvailabilityElement.click();
		testSteps.add(channel + " with update availability option : " + updateAvailability + " clicked");
		rateGridLogger.info(channel + " with update availability option : " + updateAvailability + " clicked");

		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyUpdateAvailability> ' Description: <This method will
	 * check whether channels are displaying in availability poup or not in bulk
	 * update> ' Input parameters: <WebDriver driver, String channel, String
	 * updateAvailability)> ' Return value : <ArrayList<String>> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyUpdateAvailability(WebDriver driver, String channel, String updateAvailability)
			throws InterruptedException {

		String updateAvailabilityPath = "//div[contains(text(),'" + channel
				+ "')]//following-sibling::div//span[contains(text(),'" + updateAvailability + "')]";
		rateGridLogger.info("updateAvailabilityPath :  " + updateAvailabilityPath);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, updateAvailabilityPath);
		Wait.waitForElementToBeVisibile(By.xpath(updateAvailabilityPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(updateAvailabilityPath), driver);
		WebElement updateAvailabilityElement = driver.findElement(By.xpath(updateAvailabilityPath));
		Utility.ScrollToElement_NoWait(updateAvailabilityElement, driver);
		Assert.assertTrue(updateAvailabilityElement.isDisplayed(), "Failed to verify update availability option");
		testSteps.add("Verified that " + channel + " with update availability option : " + updateAvailability
				+ " is displaying");
		rateGridLogger.info("Verified that " + channel + " with update availability option : " + updateAvailability
				+ " is displaying");
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectRoomClass> ' Description: <This method will select
	 * corresponding item from roomclass dropdowns in bulk update popup> ' Input
	 * parameters: <WebDriver driver, String roomClass> ' Return value :
	 * <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> selectRoomClass(WebDriver driver, String roomClass, String delim)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> roomClassesArray = Utility.convertTokenToArrayList(roomClass, delim);
		for (String roomClasName : roomClassesArray) {
			roomClasName = roomClasName.trim();
			rateGridLogger.info(roomClasName);
			Wait.WaitForElement(driver, OR.BulkUpdatePopupRoomClass);
			Wait.waitForElementToBeVisibile(By.xpath(OR.BulkUpdatePopupRoomClass), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.BulkUpdatePopupRoomClass), driver);
			Utility.ScrollToElement_NoWait(elements.BulkUpdatePopupRoomClass, driver);
			elements.BulkUpdatePopupRoomClass.click();

			String roomClassesPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
			List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));

			for (int i = 0; i < roomClassesElement.size(); i++) {

				String getRoomClassName = roomClassesElement.get(i).getText().trim();
				rateGridLogger.info("GetRoomClass : " + getRoomClassName);

				if (getRoomClassName.contains(roomClasName)) {

					roomClassesElement.get(i).click();
					testSteps.add("Entered RoomClass : " + roomClasName);
					rateGridLogger.info("Entered RoomClass : " + roomClasName);
					break;
				}

			}

		}

		return testSteps;
	}

	public ArrayList<String> getInActiveRatePlanNames(WebDriver driver, String ratePlanType)
			throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		for (int i = 0; i < elements.ratePlanNames.size(); i++) {

			Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);

			if (ratePlanType.equals("inactive")) {

				if (elements.ratePlanNames.get(i).getText().contains("[Inactive]")) {
					elements.ratePlanNames.get(i).click();
					if (elements.getRatePlanDescriptionList.size() > 0) {
						if (!elements.getRatePlanDescription.getText().contains("Interval")) {
							getNames.add(elements.selectedRatePlanName.getText());

						}
					}
					jse.executeScript("window.scrollBy(0,-400)");
					elements.ratePlanArrow.click();
				}

			}
		}
		logger.info("Inacivt size:" + getNames.size());
		return getNames;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickUpdateButton> ' Description: <This method will retunr
	 * sleected item text from dropdown in bulk update popup> ' Input parameters:
	 * <WebDriver driver, String dropDownName, String roomClass> ' Return value :
	 * <String> ' Created By: <Farhan Ghaffar> ' Created On: <07/13/2020> ' Method
	 * Name: <removeAllRoomClass> ' Description: <This method will remove
	 * corresponding item from dropdowns in bulk update popup> ' Input parameters:
	 * <WebDriver driver, String dropDownName, String roomClass> ' Return value :
	 * <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String getAllRoomClassText(WebDriver driver, String dropDownName, String roomClass)
			throws InterruptedException {

		String roomClassesPath = "//label[text()='" + dropDownName
				+ "']//following-sibling::div//span[contains(text(),'" + roomClass + "')]";
		WebElement roomClassesElement = driver.findElement(By.xpath(roomClassesPath));
		String getAllRoomClass = roomClassesElement.getText().trim();
		rateGridLogger.info("getAllRoomClass : " + getAllRoomClass);
		return getAllRoomClass;
	}

	public ArrayList<String> removeSelectedOptionFromField(WebDriver driver, String dropDownName, String selectedOption)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();

		String roomClassesRemovePath = "//label[text()='" + dropDownName
				+ "']//following-sibling::div//span[contains(text(),'" + selectedOption
				+ "')]//preceding::span[@class='Select-value-icon'][1]";
		driver.findElement(By.xpath(roomClassesRemovePath)).click();
		ratesGridLogger.info("click on (" + selectedOption + ") remove icon");
		testSteps.add("click on (" + selectedOption + ") remove icon");

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickUpdateButton> ' Description: <This method will click
	 * update button in bulk update popup> ' Input parameters: <WebDriver driver> '
	 * Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020> ' Method Name: <enterOccupancyValue> ' Description: <This
	 * method will enter total occupancy value in bulk update popup> ' Input
	 * parameters: <WebDriver driver, String totalOccupancyValue> ' Return value :
	 * <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On: <07/03/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickUpdateButton(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.UpdateButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.UpdateButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.UpdateButton), driver);
		elements.UpdateButton.click();
		testSteps.add("Update button clicked");
		rateGridLogger.info("Update button clicked");
		return testSteps;
	}

	public ArrayList<String> verifyOccupancyValueInputField(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.totalOccupanyValue);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.totalOccupanyValue), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.totalOccupanyValue), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.totalOccupancyType, driver);
		for (int i = 0; i < specialChars.length(); i++) {
			ratesGrid.totalOccupanyValue.sendKeys(Character.toString(specialChars.charAt(i)));
		}

		assertEquals(ratesGrid.totalOccupanyValue.getAttribute("value").isEmpty(), true,
				"Failed: Occupancy Input Field Taking Special Characters");
		testSteps.add("Verified: Occupancy Input Field Not Taking Special Characters");
		ratesGridLogger.info("Verified: Occupancy Input Field Not Taking Special Characters");

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyUpdateButtonEnabled> ' Description: <This method will
	 * verify whether update button is disabled or enabled in bulk update popup> '
	 * Input parameters: <WebDriver driver> ' Return value : <ArrayList<String>> '
	 * Created By: <Farhan Ghaffar> ' Created On: <07/13/2020>
	 */
	public ArrayList<String> verifyUpdateButtonEnabled(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.UpdateButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR.UpdateButton), driver);
		Boolean isActive = elements.UpdateButton.isEnabled();
		if (isActive) {
			testSteps.add("Update button is enabled");
			rateGridLogger.info("Update button is enabled");

		} else {
			testSteps.add("Update button is disabled");
			rateGridLogger.info("Update button is disabled");

		}
		return testSteps;
	}

	public ArrayList<String> selectItemsFromDropDowns(WebDriver driver, String dropDownName, String selectionElement)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		rateGridLogger.info(selectionElement + Utility.DELIM);
		driver.findElement(By.xpath("//div[contains(text(),'Bulk update')]")).click();
		if (selectionElement.contains(Utility.DELIMS)) {

			String[] splitedStringArray = selectionElement.split(Utility.DELIM);
			for (String getString : splitedStringArray) {
				String bulkUpdateDropDownPath = "//label[text()='" + dropDownName
						+ "']//following-sibling::div//div[1]";
				Wait.WaitForElement(driver, bulkUpdateDropDownPath);
				Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
				Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
				WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
				Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
				bulkUpdateDropdown.click();
				testSteps.add(dropDownName + " drop down clicked");
				ratesGridLogger.info(dropDownName + " drop down clicked");

				getString = getString.trim();
				rateGridLogger.info(getString);
				if (getString.contains("All Active")) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Active Rate Plans')]";
					try {
						WebElement element = driver.findElement(By.xpath(selectionElementsPath));
						element.click();
					} catch (Exception e) {
						Utility.clickThroughJavaScript(driver, bulkUpdateDropdown);
						WebElement element = driver.findElement(By.xpath(selectionElementsPath));
						element.click();
					}

					testSteps.add("Entered : " + selectionElement);
					ratesGridLogger.info("Entered : " + selectionElement);

				} else if (selectionElement.contains("All Inactive")) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Inactive Rate Plans')]";
					WebElement element = driver.findElement(By.xpath(selectionElementsPath));

					element.click();
					testSteps.add("Entered : " + getString);
					ratesGridLogger.info("Entered : " + getString);

				} else if ((getString.contains("All room")) || (getString.contains("Select"))) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All room classes')]";
					WebElement element = driver.findElement(By.xpath(selectionElementsPath));

					element.click();
					testSteps.add("Entered : " + getString);
					ratesGridLogger.info("Entered : " + getString);

				} else if (getString.contains("All sources")) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All sources')]";
					WebElement element = driver.findElement(By.xpath(selectionElementsPath));

					element.click();
					testSteps.add("Entered : " + getString);
					ratesGridLogger.info("Entered : " + getString);

				} else {

					String planInputPath = "//label[text()='" + dropDownName + "']//following-sibling::div//input";
					Wait.WaitForElement(driver, planInputPath);
					Wait.waitForElementToBeVisibile(By.xpath(planInputPath), driver);
					Wait.waitForElementToBeClickable(By.xpath(planInputPath), driver);
					WebElement input = driver.findElement(By.xpath(planInputPath));
					input.sendKeys(selectionElement);
					rateGridLogger.info("Entered rate plan name in input : " + selectionElement);
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
					List<WebElement> selectionElementsList = driver.findElements(By.xpath(selectionElementsPath));

					for (int i = 0; i < selectionElementsList.size(); i++) {
						String selectionElementName = selectionElementsList.get(i).getText().trim();
						ratesGridLogger.info("Get list items : " + selectionElementName);

						if (selectionElementName.contains(getString)) {

							selectionElementsList.get(i).click();
							testSteps.add("Entered : " + getString);
							ratesGridLogger.info("Entered : " + getString);
							break;
						}

					}
				}
			}

		} else {

			String bulkUpdateDropDownPath = "//label[text()='" + dropDownName + "']//following-sibling::div//div[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			testSteps.add(dropDownName + " drop down clicked");
			ratesGridLogger.info(dropDownName + " drop down clicked");

			if (selectionElement.contains("All Active")) {
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Active Rate Plans')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else if (selectionElement.contains("All Inactive")) {
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Inactive Rate Plans')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else if ((selectionElement.contains("All room")) || (selectionElement.contains("Select"))) {

				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(text(),'All room')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else if (selectionElement.contains("All sources")) {
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(text(),'All sources')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else {
				String planInputPath = "//label[text()='" + dropDownName + "']//following-sibling::div//input";
				Wait.WaitForElement(driver, planInputPath);
				Wait.waitForElementToBeVisibile(By.xpath(planInputPath), driver);
				Wait.waitForElementToBeClickable(By.xpath(planInputPath), driver);
				WebElement input = driver.findElement(By.xpath(planInputPath));
				input.sendKeys(selectionElement);
				rateGridLogger.info("Entered rate plan name in input : " + selectionElement);
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
				List<WebElement> selectionElementsList = driver.findElements(By.xpath(selectionElementsPath));
				for (int i = 0; i < selectionElementsList.size(); i++) {
					String selectionElementName = selectionElementsList.get(i).getText().trim();
					// ratesGridLogger.info("Get list items : " + selectionElementName);

					if (selectionElementName.contains(selectionElement)) {

						selectionElementsList.get(i).click();
						testSteps.add("Entered : " + selectionElement);
						ratesGridLogger.info("Entered : " + selectionElement);
						break;
					}

				}
			}

		}

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickCancelButton> ' Description: <This method will click
	 * cancel button in bulk update popup> ' Input parameters: <WebDriver driver> '
	 * Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickOnCancelButton(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.BulkUpdateCancel);
		Wait.waitForElementToBeVisibile(By.xpath(OR.BulkUpdateCancel), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.BulkUpdateCancel), driver);
		elements.BulkUpdateCancel.click();
		testSteps.add("Clicked on cancel button");
		rateGridLogger.info("Clicked on cancel button");
		return testSteps;
	}

	public ArrayList<String> verifyDropDownDisableOnAllListSelection(WebDriver driver, String dropDownName,
			String dropDownSelector) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		if (dropDownSelector.contains("All")) {

			// String noResultsFound = "//span[text()='no results found']";
			String bulkUpdateDropDownPath = "//label[text()='" + dropDownName + "']//following-sibling::div//div[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			testSteps.add(dropDownName + " drop down clicked");
			ratesGridLogger.info(dropDownName + " drop down clicked");
			String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
			try {

				List<WebElement> selectionElementsList = driver.findElements(By.xpath(selectionElementsPath));
				if (selectionElementsList.get(0).isDisplayed()) {
					System.out.print(" if");
					testSteps.add("Failed: The Dropdown List :" + dropDownName + "  show");
					ratesGridLogger.info("Failed:  The Dropdown List :" + dropDownName + " show");
				} else {
					testSteps.add("Verified: The Dropdown List :" + dropDownName + " didn't show");
					ratesGridLogger.info("Verified:  The Dropdown List :" + dropDownName + " didn't show");
				}
			} catch (Exception e) {
				testSteps.add("Verified: The Dropdown List :" + dropDownName + " didn't show");
				ratesGridLogger.info("Verified:  The Dropdown List :" + dropDownName + " didn't show");

			}

		}
		return testSteps;

	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <compareLists> ' Description: <This method will compare list
	 * in bulk update popup> ' Input parameters: <ArrayList<String> firstList,
	 * ArrayList<String> secondList> ' Return value : <Boolean>> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/13/2020>
	 */

	public Boolean compareLists(ArrayList<String> firstList, ArrayList<String> secondList) {
		Boolean isRoomClassPresent = false;
		int i;
		for (i = 0; i < firstList.size(); i++) {
			String activeRoomClass = firstList.get(i);
			rateGridLogger.info("activeRoomClass : " + activeRoomClass);
			String activeRoomClassInBulkUpdate = secondList.get(i);
			rateGridLogger.info("activeRoomClassInBulkUpdate : " + activeRoomClassInBulkUpdate);
			if (activeRoomClass.contains(activeRoomClassInBulkUpdate)) {
				isRoomClassPresent = true;
			} else {
				isRoomClassPresent = false;
			}
		}

		return isRoomClassPresent;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyRulesHeading> ' Description: <This method will verify
	 * rules popup heading in bulk update popup> ' Input parameters: <WebDriver
	 * driver> ' Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> '
	 * Created On: <07/13/2020>
	 */
	public ArrayList<String> verifyRulesHeading(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.RulesHeading);
		Wait.waitForElementToBeVisibile(By.xpath(OR.RulesHeading), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.RulesHeading), driver);
		assertTrue(elements.RulesHeading.isDisplayed(), "Bulk Update rules popup Heading didn't display");
		testSteps.add("Verified Bulk update rules popup heading is displaying");
		rateGridLogger.info("Verified Bulk update rules popup heading is displaying");

		return testSteps;

	}

	public ArrayList<String> verifyColorCodeofSources(WebDriver driver, String dropDownSelector) {
		ArrayList<String> testSteps = new ArrayList<>();
		String expectedColorCode = "#ed6a5a";
		String colorPath = "";
		if (dropDownSelector.contains("All")) {
			colorPath = "(//span[contains(text(),'All')]/ancestor::div[@class='Select-value'])[1]";

		} else {

			colorPath = "//span[contains(text(),'" + dropDownSelector + "')]/ancestor::div[@class='Select-value']";
		}
		Wait.WaitForElement(driver, colorPath);
		Wait.waitForElementToBeVisibile(By.xpath(colorPath), driver);
		WebElement colorPathElement = driver.findElement(By.xpath(colorPath));
		String foundColor = colorPathElement.getCssValue("background-color");
		String foundColorCode = Color.fromString(foundColor).asHex();

		assertEquals(foundColorCode, expectedColorCode, "Failed: Sources Element Color didn't match");

		testSteps.add("Sources Element Color matched");
		ratesGridLogger.info("Sources Element Color matched");
		return testSteps;

	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickMinimumStay> ' Description: <This method will click
	 * minimum stay toggle button in bulk update popup> ' Input parameters:
	 * <WebDriver driver> ' Return value : <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> clickMinimumStay(WebDriver driver, String isClick) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.MinimumStay);
		Wait.waitForElementToBeVisibile(By.xpath(OR.MinimumStay), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.MinimumStay), driver);
		if (isClick.equalsIgnoreCase("Yes")) {
			Boolean isCheck = verifyMinimumStayValue(driver);
			if (isCheck) {
				testSteps.add("Mininum Stay toggle button already enabled");
				rateGridLogger.info("Mininum Stay toggle button already enabled");

			} else {
				elements.MinimumStay.click();
				testSteps.add("Mininum Stay toggle button enabled");
				rateGridLogger.info("Mininum Stay toggle button enabled");
			}

		} else if (isClick.equalsIgnoreCase("No")) {
			Boolean isCheck = verifyMinimumStayValue(driver);
			if (!isCheck) {
				testSteps.add("Mininum Stay toggle button already disabled");
				rateGridLogger.info("Mininum Stay toggle button already disabled");

			} else {
				elements.MinimumStay.click();
				testSteps.add("Mininum Stay toggle button disabled");
				rateGridLogger.info("Mininum Stay toggle button disabled");
			}
		}

		return testSteps;
	}

	public ArrayList<String> verifyNoRoomsSelectedInUpdateRates(WebDriver driver, String roomClassName) {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		if (!roomClassName.isEmpty()) {
			String removeRoomClassButton = "//span[contains(text(),'" + roomClassName
					+ "')]/preceding-sibling::span[@class='Select-value-icon']";
			WebElement removeRoomClassButtoneElement = driver.findElement(By.xpath(removeRoomClassButton));
			removeRoomClassButtoneElement.click();

		}
		assertEquals(ratesGrid.NO_ROOM_CLASS_SELECTED.isDisplayed(), true, "Failed: No rooms selected didn't show");
		testSteps.add("Verified:" + ratesGrid.NO_ROOM_CLASS_SELECTED.getText());
		ratesGridLogger.info("Verified:" + ratesGrid.NO_ROOM_CLASS_SELECTED.getText());
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * <<<<<<< HEAD ' Method Name: <enterMinimumStayValue> ' Description: <This
	 * method will enter minimum stay value in bulk update popup> ' Input
	 * parameters: <WebDriver driver, String mininumStayValue> ' Return value :
	 * <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On: <07/13/2020>
	 */
	public ArrayList<String> enterMinimumStayValue(WebDriver driver, String mininumStayValue) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.MinimumStayValue);
		Wait.waitForElementToBeVisibile(By.xpath(OR.MinimumStayValue), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.MinimumStayValue), driver);
		elements.MinimumStayValue.clear();
		elements.MinimumStayValue.sendKeys(mininumStayValue);
		testSteps.add("Enter mininum stay value : " + mininumStayValue);
		rateGridLogger.info("Enter mininum stay value : " + mininumStayValue);
		return testSteps;
	}

	public ArrayList<String> updateNightlyRate(WebDriver driver, int index, String nightlyRate)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE), driver);
		test_steps.add("Enter Nighlty Rate : " + nightlyRate);
		ratesGridLogger.info("Enter Nighlty Rate : " + nightlyRate);

		ratesGrid.UPDATE_RATE_NEWRATE_NIGHTLYRATE.get(index).sendKeys(nightlyRate);

		test_steps.add("Updated Nighlty Rate");
		ratesGridLogger.info("Updated Nighlty Rate");
		return test_steps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickCheckin> ' Description: <This method will click CheckIn
	 * Toggle button in bulk update popup> ' Input parameters: <WebDriver driver> '
	 * Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickCheckin(WebDriver driver, String isClick) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.CheckinToggle);
		Wait.waitForElementToBeVisibile(By.xpath(OR.CheckinToggle), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.CheckinToggle), driver);
		if (isClick.equalsIgnoreCase("Yes")) {
			Boolean isCheck = verifyNoCheckInCheckbox(driver);
			if (isCheck) {
				testSteps.add("Checkin toggle button already enabled");
				rateGridLogger.info("Checkin toggle button already enabled");

			} else {
				elements.CheckinToggle.click();
				testSteps.add("Checkin toggle button enabled");
				rateGridLogger.info("Checkin toggle button enabled");
			}

		} else if (isClick.equalsIgnoreCase("No")) {
			Boolean isCheck = verifyNoCheckInCheckbox(driver);
			if (!isCheck) {
				testSteps.add("Checkin toggle button already disabled");
				rateGridLogger.info("Checkin toggle button already disabled");

			} else {
				elements.CheckinToggle.click();
				testSteps.add("Checkin toggle button disabled");
				rateGridLogger.info("Checkin toggle button disabled");
			}
		}
		return testSteps;
	}

	public ArrayList<String> updateAdditionalAdultRate(WebDriver driver, int index, String additionalAdult)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALADULT);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALADULT), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALADULT), driver);

		test_steps.add("Enter Additional Adult Rate : " + additionalAdult);
		ratesGridLogger.info("Enter Additional Adult Rate : " + additionalAdult);

		ratesGrid.UPDATE_RATE_NEWRATE_ADDITIONALADULT.get(index).sendKeys(additionalAdult);

		test_steps.add("Updated Additional Adult Rate");
		ratesGridLogger.info("Updated Additional Adult Rate");
		return test_steps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickNoCheckInCheckbox> ' Description: <This method will
	 * click NoCheckIN checkbox in bulk update popup> ' Input parameters: <WebDriver
	 * driver> ' Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> '
	 * Created On: <07/13/2020>
	 */
	public ArrayList<String> clickNoCheckInCheckbox(WebDriver driver, String isClick) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.NoCheckInCheckbox);
		Wait.waitForElementToBeVisibile(By.xpath(OR.NoCheckInCheckbox), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.NoCheckInCheckbox), driver);
		if (isClick.equalsIgnoreCase("Yes")) {
			elements.NoCheckInCheckbox.click();
			testSteps.add("NO Checkin chekcbox checked");
			rateGridLogger.info("NO Checkin chekcbox checked");
		}
		return testSteps;
	}

	public ArrayList<String> updateAdditionalChildRate(WebDriver driver, int index, String additionalChild)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALCHILD);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALCHILD), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_NEWRATE_ADDITIONALCHILD), driver);

		test_steps.add("Enter Additional Child Rate : " + additionalChild);
		ratesGridLogger.info("Enter Additional Child Rate : " + additionalChild);

		ratesGrid.UPDATE_RATE_NEWRATE_ADDITIONALCHILD.get(index).sendKeys(additionalChild);

		test_steps.add("Updated Additional Child Rate");
		ratesGridLogger.info("Updated Additional Child Rate");
		return test_steps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickCheckOut> ' Description: <This method will click
	 * CheckOut Toggle button in bulk update popup> ' Input parameters: <WebDriver
	 * driver> ' Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> '
	 * Created On: <07/13/2020>
	 * 
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickCheckOut(WebDriver driver, String isClick) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.CheckOutToggle);
		Wait.waitForElementToBeVisibile(By.xpath(OR.CheckOutToggle), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.CheckOutToggle), driver);
		if (isClick.equalsIgnoreCase("Yes")) {
			Boolean isCheck = verifyNoCheckOutCheckbox(driver);
			if (isCheck) {
				testSteps.add("Checkout toggle button already enabled");
				rateGridLogger.info("Checkout toggle button already enabled");

			} else {
				elements.CheckOutToggle.click();
				testSteps.add("Checkout toggle button enabled");
				rateGridLogger.info("Checkout toggle button enabled");
			}
		} else if (isClick.equalsIgnoreCase("No")) {
			Boolean isCheck = verifyNoCheckOutCheckbox(driver);
			if (!isCheck) {
				testSteps.add("Checkout toggle button already disabled");
				rateGridLogger.info("Checkout toggle button already disabled");

			} else {
				elements.CheckOutToggle.click();
				testSteps.add("Checkout toggle button disabled");
				rateGridLogger.info("Checkout toggle button disabled");
			}
		}
		return testSteps;
	}
	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickNoCheckOutCheckbox> ' Description: <This method will
	 * select NoCheckOut Checkbox in bulk update popup> ' Input parameters:
	 * <WebDriver driver> ' Return value : <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <07/13/2020>
	 */

	public ArrayList<String> selectBulkUpdateRatesOption(WebDriver driver, int rateTypeIndex)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Utility.ScrollToElement(ratesGrid.UPDATERATES_OPTIONS.get(rateTypeIndex), driver);

		rateGridLogger.info("Successfully clicked on selected option.");
		test_steps.add("Successfully clicked on selected option.");
		ratesGrid.UPDATERATES_OPTIONS.get(rateTypeIndex).click();

		return test_steps;

	}

	/*
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickYesUpdateButton> ' Description: <Thie method will Click
	 * Yes Button After Update Rate Button is Clicked > ' ' Input parameters:
	 * <WebDriver> ' Return: <ArrayList<String>> Created By: <Aqsa Manzoor> '
	 * Created On: <07 July 2020>
	 * 
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> clickNoCheckOutCheckbox(WebDriver driver, String isClick) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.NoCheckOutCheckbox);
		Wait.waitForElementToBeVisibile(By.xpath(OR.NoCheckOutCheckbox), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.NoCheckOutCheckbox), driver);
		if (isClick.equalsIgnoreCase("Yes")) {

			elements.NoCheckOutCheckbox.click();
			testSteps.add("NO Checkout chekcbox checked");
			rateGridLogger.info("NO Checkout chekcbox checked");
		}
		return testSteps;
	}

	public ArrayList<String> clickYesUpdateButton(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_CONFIRMATION_YES_UPDATE_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_CONFIRMATION_YES_UPDATE_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_CONFIRMATION_YES_UPDATE_BUTTON), driver);
		ratesGrid.UPDATE_RATE_CONFIRMATION_YES_UPDATE_BUTTON.click();
		testSteps.add("Yes,Update button clicked");
		ratesGridLogger.info("Yes,Update button clicked");
		return testSteps;
	}

	public ArrayList<String> previousStartEndDateValidation(WebDriver driver, String previousDate)
			throws InterruptedException {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		String activeColorCode = "rgba(0, 0, 0, 1)";
		String inActiveColorCode = "rgba(204, 204, 204, 1)";
		Wait.WaitForElement(driver, OR_RatesGrid.UPDATE_RATE_DATE_PICKER);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.UPDATE_RATE_DATE_PICKER), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.UPDATE_RATE_DATE_PICKER), driver);
		previousDate = previousDate.split("/")[1];
		ratesGrid.UPDATE_RATE_DATE_PICKER.get(0).click();
		String previousValueStartDatePath = "//label[text()='Start']/following-sibling::div//span//*[contains(text(),'"
				+ previousDate + "')]";
		WebElement previousValueStartDatePathElement = driver.findElement(By.xpath(previousValueStartDatePath));
		assertEquals(previousValueStartDatePathElement.getCssValue("color").equals(inActiveColorCode), true,
				"Failed: previous Date is Active");
		assertEquals(previousValueStartDatePathElement.getCssValue("color").equals(activeColorCode), false,
				"Failed: previous Date is Active");
		ratesGridLogger.info("Previous Date in CheckIn Calendar is InActive");
		testSteps.add("Previous Date in CheckIn Calendar is InActive");
		Wait.wait5Second();
		ratesGrid.UPDATE_RATE_DATE_PICKER.get(1).click();
		String previousValueEndDatePath = "//label[text()='End']/following-sibling::div//span//*[contains(text(),'"
				+ previousDate + "')]";
		WebElement previousValueEndDatePathElement = driver.findElement(By.xpath(previousValueEndDatePath));

		assertEquals(previousValueEndDatePathElement.getCssValue("color").equals(inActiveColorCode), true,
				"Failed: previous Date is Active");
		assertEquals(previousValueEndDatePathElement.getCssValue("color").equals(activeColorCode), false,
				"Failed: previous Date is Active");
		ratesGridLogger.info("Previous Date in Checkout Calendar is InActive");
		testSteps.add("Previous Date in Checkout Calendar is InActive");

		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getBulkUpdateDropDownsList> ' Description: <This method will
	 * return list of dropdowns based of respected parameters in bulk update popup>
	 * ' Input parameters: <WebDriver driver> ' Return value : <String> ' Created
	 * By: <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> getBulkUpdateDropDownsList(WebDriver driver, String dropDownName)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String bulkUpdateDropDownPath = "//label[text()='" + dropDownName + "']//following-sibling::div";

		Wait.WaitForElement(driver, bulkUpdateDropDownPath);
		Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
		WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
		Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
		bulkUpdateDropdown.click();
		rateGridLogger.info(dropDownName + " drop down clicked");

		String roomClassesPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
		List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));

		for (int i = 0; i < roomClassesElement.size(); i++) {
			String roomClassName = roomClassesElement.get(i).getText().trim();
			testSteps.add(roomClassName);
			rateGridLogger.info("Get " + dropDownName + " Dropdown list items : " + roomClassName);

		}
		elements.EndDateInput.sendKeys(Keys.TAB);
		rateGridLogger.info("Clicked Tab Key");
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <sendValuesToBulkUpdateDropDown> ' Description: <This method
	 * will return list of dropdowns based of respected input in bulk update popup>
	 * ' Input parameters: <WebDriver driver> ' Return value : <String> ' Created
	 * By: <Farhan Ghaffar> ' Created On: <07/09/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> sendValuesToBulkUpdateDropDown(WebDriver driver, String dropDownName,
			String dropDownOption) throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String bulkUpdateDropDownPath = "(//label[text()='" + dropDownName + "']//following-sibling::div//input)[2]";

		Wait.WaitForElement(driver, bulkUpdateDropDownPath);
		Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
		WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
		Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
		bulkUpdateDropdown.sendKeys(dropDownOption);
		rateGridLogger.info("Entered : " + dropDownOption);
		testSteps.add("Entered : " + dropDownOption);

		String roomClassesPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
		List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));

		for (int i = 0; i < roomClassesElement.size(); i++) {
			String roomClassName = roomClassesElement.get(i).getText().trim();
			testSteps.add(roomClassName);
			rateGridLogger.info("Get " + dropDownName + " Dropdown list items : " + roomClassName);

		}

		elements.EndDateInput.sendKeys(Keys.TAB);
		rateGridLogger.info("Clicked Tab Key");
		return testSteps;
	}

	public String getTotalDaysText(WebDriver driver, String daysText) {

		String daysTextPath = "//p[contains(text(),'" + daysText + "')]";
		WebElement daysTextElement = driver.findElement(By.xpath(daysTextPath));
		Wait.WaitForElement(driver, daysTextPath);
		Wait.waitForElementToBeVisibile(By.xpath(daysTextPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(daysTextPath), driver);
		String text = daysTextElement.getText();
		ratesGridLogger.info("bulkUpdatePopup Days text : " + text);
		return text;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyCloseIcon> ' Description: <This method will return
	 * close icon text in bulk update popup> ' Input parameters: <WebDriver driver>
	 * ' Return value : <String> ' Created By: <Farhan Ghaffar> ' Created On:
	 * <07/08/2020>
	 * 
	 */

	public String getCloseIconText(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.ClosePopup);
		Wait.waitForElementToBeVisibile(By.xpath(OR.ClosePopup), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.ClosePopup), driver);
		Utility.ScrollToElement_NoWait(elements.ClosePopup, driver);
		String closeText = elements.ClosePopup.getText();
		return closeText;
	}

	public ArrayList<String> verifyPercentageSignOccupancyValueField(WebDriver driver) {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		assertEquals(ratesGrid.OCCUPANCY_PERCENTAGE_SIGN.isDisplayed(), true,
				"Failed: Occupancy Input Field Tab didn't have % sign");

		assertEquals(ratesGrid.OCCUPANCY_PERCENTAGE_SIGN.getText(), "%",

				"Failed: Occupancy Input Field Tab didn't have % sign");
		testSteps.add("Occupancy Input Field Tab have % sign");
		ratesGridLogger.info("Occupancy Input Field Tab have % sign");
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <totalOccupancyTextDisplay> ' Description: <This method will
	 * return total occupancy text in bulk update popup> ' Input parameters:
	 * <WebDriver driver> ' Return value : <String> ' Created By: <Farhan Ghaffar> '
	 * Created On: <07/08/2020>
	 */

	public String totalOccupancyTextDisplay(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.TotalOccupancyText);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TotalOccupancyText), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TotalOccupancyText), driver);
		Utility.ScrollToElement_NoWait(elements.TotalOccupancyText, driver);
		String totalOccupancyText = elements.TotalOccupancyText.getText();
		return totalOccupancyText;

	}

	public ArrayList<String> verifyColorCodeofRatePlans(WebDriver driver, String dropDownSelector) {
		ArrayList<String> testSteps = new ArrayList<>();
		String expectedColorCode = "#3ea8f4";
		String colorPath = "";
		if (dropDownSelector.contains("All")) {
			colorPath = "(//span[contains(text(),'All')]/ancestor::div[@class='Select-value'])[1]";

		} else {

			colorPath = "//span[contains(text(),'" + dropDownSelector + "')]/ancestor::div[@class='Select-value']";
		}
		Wait.WaitForElement(driver, colorPath);
		Wait.waitForElementToBeVisibile(By.xpath(colorPath), driver);
		WebElement colorPathElement = driver.findElement(By.xpath(colorPath));
		System.out.print("COlorL:" + colorPathElement.getCssValue("background-color"));
		String foundColor = colorPathElement.getCssValue("background-color");
		String foundColorCode = Color.fromString(foundColor).asHex();

		assertEquals(foundColorCode, expectedColorCode, "Failed: Rate Plan Element Color didn't match");

		testSteps.add("Rate Plan Element Color matched");
		ratesGridLogger.info("Rate Plan Element Color matched");
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickTotalOccupancy> ' Description: <This method will click
	 * total occupancy in bulk update popup> ' Input parameters: <WebDriver driver>
	 * ' Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/08/2020>
	 */
	public ArrayList<String> clickTotalOccupancy(WebDriver driver, String isEnable) throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.TotalOccupancy);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TotalOccupancy), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TotalOccupancy), driver);
		Utility.ScrollToElement_NoWait(elements.TotalOccupancy, driver);
		if (isEnable.equalsIgnoreCase("Yes")) {
			Wait.WaitForElement(driver, OR.TotalOccupanyValue);
			Boolean isOccupancyEnabled = elements.TotalOccupanyValue.isEnabled();

			if (isOccupancyEnabled) {
				testSteps.add("Total occupancy already clicked");
				rateGridLogger.info("Total occupancy already clicked");
			} else {
				elements.TotalOccupancy.click();
				testSteps.add("Enabled total occupancy");
				rateGridLogger.info("Enabled total occupancy");

			}
		} else if (isEnable.equalsIgnoreCase("No")) {
			Wait.WaitForElement(driver, OR.TotalOccupanyValue);
			Boolean isOccupancyEnabled = elements.TotalOccupanyValue.isEnabled();

			if (isOccupancyEnabled) {
				elements.TotalOccupancy.click();
				testSteps.add("Total occupancy disabled");
				rateGridLogger.info("Total occupancy disabled");
			} else {
				testSteps.add("Total occupancy already disabled");
				rateGridLogger.info("Total occupancy already disabled");

			}
		}
		return testSteps;
	}

	public ArrayList<String> verifyColorCodeofRoomClasses(WebDriver driver, String dropDownSelector) {
		ArrayList<String> testSteps = new ArrayList<>();
		String expectedColorCode = "#9bc1bc";
		String colorPath = "";
		if (dropDownSelector.contains("All")) {
			colorPath = "(//span[contains(text(),'All')]/ancestor::div[@class='Select-value'])[1]";

		} else {

			colorPath = "//span[contains(text(),'" + dropDownSelector + "')]/ancestor::div[@class='Select-value']";
		}
		Wait.WaitForElement(driver, colorPath);
		Wait.waitForElementToBeVisibile(By.xpath(colorPath), driver);
		WebElement colorPathElement = driver.findElement(By.xpath(colorPath));

		String foundColor = colorPathElement.getCssValue("background-color");
		String foundColorCode = Color.fromString(foundColor).asHex();

		assertEquals(foundColorCode, expectedColorCode, "Failed:Room Classes Element Color didn't match");

		testSteps.add("Room Classes Element Color matched");
		ratesGridLogger.info("Room Classes Element Color matched");
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyTotalOccupancyTypeValues> ' Description: <This method
	 * will return total occupancy type value in bulk update popup> ' Input
	 * parameters: <WebDriver driver> ' Return value : <ArrayList<String>> ' Created
	 * By: <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> verifyTotalOccupancyTypeValues(WebDriver driver, String totalOccupancyType)
			throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.TotalOccupancyType);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TotalOccupancyType), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TotalOccupancyType), driver);
		Utility.ScrollToElement_NoWait(elements.TotalOccupancyType, driver);
		elements.TotalOccupancyType.click();
		rateGridLogger.info("Dropdown clicked");

		String path = "(//span[text()='For days that total occupancy is']//parent::label//parent::div//div[@class='Select-menu-outer'])[1]//div[contains(text(),'"
				+ totalOccupancyType + "')]";
		WebElement type = driver.findElement(By.xpath(path));
		String getType = type.getText();
		rateGridLogger.info(getType);
		Assert.assertTrue(type.isDisplayed(), "Failed to verify occupancy type value : " + totalOccupancyType);
		rateGridLogger.info("Verified " + totalOccupancyType + " value");
		testSteps.add("Verified " + totalOccupancyType + " value");
		elements.TotalOccupancyType.click();
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <selectTotalOccupancyType> ' Description: <This method will
	 * select total occupancy type in bulk update popup> ' Input parameters:
	 * <WebDriver driver> ' Return value : <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> selectTotalOccupancyType(WebDriver driver, String totalOccupancyType)
			throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.TotalOccupancyType);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TotalOccupancyType), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TotalOccupancyType), driver);
		Utility.ScrollToElement_NoWait(elements.TotalOccupancyType, driver);
		elements.TotalOccupancyType.click();
		rateGridLogger.info("Dropdown clicked");
		if (totalOccupancyType.equalsIgnoreCase("Greater")) {
			String greater = "greater than";
			rateGridLogger.info(greater);
			String path = "(//span[text()='For days that total occupancy is']//parent::label//parent::div//div[@class='Select-menu-outer'])[1]//div[contains(text(),'"
					+ greater + "')]";
			driver.findElement(By.xpath(path)).click();
			testSteps.add("Selected occupancy type greater than");
			rateGridLogger.info("Selected occupancy type greater than");

		} else if (totalOccupancyType.equalsIgnoreCase("Less")) {
			String less = "less than";
			rateGridLogger.info(less);
			String path = "(//span[text()='For days that total occupancy is']//parent::label//parent::div//div[@class='Select-menu-outer'])[1]//div[contains(text(),'"
					+ less + "')]";
			driver.findElement(By.xpath(path)).click();
			testSteps.add("Selected occupancy type less than");
			rateGridLogger.info("Selected occupancy type less than");
		}

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <enterOccupancyValue> ' Description: <This method will enter
	 * total occupancy value in bulk update popup> ' Input parameters: <WebDriver
	 * driver, String totalOccupancyValue> ' Return value : <ArrayList<String>> '
	 * Created By: <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> enterOccupancyValue(WebDriver driver, String totalOccupancyValue)
			throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.TotalOccupanyValue);
		Wait.waitForElementToBeVisibile(By.xpath(OR.TotalOccupanyValue), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.TotalOccupanyValue), driver);
		Utility.ScrollToElement_NoWait(elements.TotalOccupancyType, driver);
		elements.TotalOccupanyValue.sendKeys(totalOccupancyValue);
		testSteps.add("Entered total occupancy value : " + totalOccupancyValue);
		rateGridLogger.info("Entered total occupancy value : " + totalOccupancyValue);
		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyTotalOccupancyType> ' Description: <This method will
	 * return total occupancy type enable/disable status in bulk update popup> '
	 * Input parameters: <WebDriver driver> ' Return value : <String> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public Boolean verifyTotalOccupancyType(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.TotalOccupancyTypeVisibility);
		Boolean isEnabled = false;
		String classes = elements.TotalOccupancyTypeVisibility.getAttribute("class");
		if (classes.contains("is-disabled")) {
			isEnabled = false;
		} else {
			isEnabled = true;
		}
		rateGridLogger.info("totalOccupancyType classes :" + classes);
		return isEnabled;
	}

	/*
	 * ' Method Name: <verifyDropDownsPlaceHolder> ' Description: <This method will
	 * return specific dropdown with given place holder text in bulk update popup> '
	 * Input parameters: <WebDriver driver> ' Return value : <ArrayList<String>> '
	 * Created By: <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * 
	 */
	public ArrayList<String> verifyBulkUpdateDropDowns(WebDriver driver, String dropDownName)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		String bulkUpdateDropDownPath = "//label[text()='" + dropDownName + "']//following-sibling::div";
		Wait.WaitForElement(driver, bulkUpdateDropDownPath);
		Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
		WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
		Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
		Assert.assertTrue(bulkUpdateDropdown.isDisplayed(), "Failed : " + dropDownName + " dropdown didn't display");
		testSteps.add("Verify " + dropDownName + " dropdown displayed");
		ratesGridLogger.info("Verify " + dropDownName + " dropdown displayed");

		return testSteps;
	}

	public int getRoomClassListSize(WebDriver driver, String dropDownName, String dropDownOption)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		String bulkUpdateDropDownPath = "(//label[text()='" + dropDownName
				+ "']//following-sibling::div//input[@role='combobox'])[1]";

		Wait.WaitForElement(driver, bulkUpdateDropDownPath);
		Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
		WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
		Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
		bulkUpdateDropdown.sendKeys(dropDownOption);
		ratesGridLogger.info("Entered " + dropDownName + " name : " + dropDownOption);
		testSteps.add("Entered " + dropDownName + " name : " + dropDownOption);

		String roomClassesPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
		List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));
		logger.info("roomClassesElement: " + roomClassesElement.size());

		return roomClassesElement.size();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyTotalOccupanyIcon> ' Description: <This method will
	 * return total occupancy icon text in bulk update popup> ' Input parameters:
	 * <WebDriver driver> ' Return value : <String> ' Created By: <Farhan Ghaffar> '
	 * Created On: <07/08/2020> ' Method Name: <removeAllRoomClass> ' Description:
	 * <This method will remove corresponding item from dropdowns in bulk update
	 * popup> ' Input parameters: <WebDriver driver, String dropDownName, String
	 * roomClass> ' Return value : <ArrayList<String>> ' Created By: <Farhan
	 * Ghaffar> ' Created On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public String verifyTotalOccupanyIcon(WebDriver driver) throws InterruptedException {
		Actions actions = new Actions(driver);
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.OccupancyIcon);
		actions.moveToElement(elements.OccupancyIcon).perform();
		String getClause = elements.OcupancyClause.getText();
		rateGridLogger.info(getClause);
		return getClause;
	}

	public ArrayList<String> removeAllRoomClass(WebDriver driver, String dropDownName, String roomClass)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();

		String roomClassesRemovePath = "//label[text()='" + dropDownName
				+ "']//following-sibling::div//span[contains(text(),'" + roomClass
				+ "')]//preceding::span[@class='Select-value-icon'][1]";
		driver.findElement(By.xpath(roomClassesRemovePath)).click();
		ratesGridLogger.info("click on (" + roomClass + ") remove icon");
		testSteps.add("click on (" + roomClass + ") remove icon");

		return testSteps;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyBulkUpdateDropDowns> ' Description: <This method will
	 * verify specific dropdown with given text is displaying or not in bulk update
	 * popup> ' Input parameters: <WebDriver driver> ' Return value :
	 * <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * <<<<<<< HEAD ' Method Name: <verifyDropDownsPlaceHolder> ' Description: <This
	 * method will return specific dropdown with given place holder text in bulk
	 * update popup> ' Input parameters: <WebDriver driver> ' Return value :
	 * <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created On: <07/08/2020>
	 */
	public String getDropDownsPlaceHolder(WebDriver driver, String dropDownName) throws InterruptedException {

		String bulkUpdateDropDownPath = "//label[text()='" + dropDownName
				+ "']//following-sibling::div//div[@class='Select-placeholder']";
		Wait.WaitForElement(driver, bulkUpdateDropDownPath);
		Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
		WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
		Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
		String placeHolderText = bulkUpdateDropdown.getText();
		rateGridLogger.info("placeHolderText " + placeHolderText);

		return placeHolderText;
	}

	public ArrayList<String> daysInRangeOfDateOKButton(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.WaitForElement(driver, OR_RatesGrid.NO_DATS_MATCH_OK_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.NO_DATS_MATCH_OK_BUTTON), driver);
		elements.NO_DATS_MATCH_OK_BUTTON.click();
		ratesGridLogger.info("OK Button Clicked");
		testSteps.add("OK Button Clicked");
		return testSteps;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyUpdateExistingRule> ' Description: <This method will
	 * return update existing rule text in bulk update popup> ' Input parameters:
	 * <WebDriver driver> ' Return value : <String> ' Created By: <Farhan Ghaffar> '
	 * Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String verifyUpdateExistingRule(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.UpdatExistingRules);
		String updatExistingRules = elements.UpdatExistingRules.getText();
		rateGridLogger.info("updatExistingRules :" + updatExistingRules);

		return updatExistingRules;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyUpdateExistingRule> ' Description: <This method will
	 * return update availability text in bulk update availability popup > ' Input
	 * parameters: <WebDriver driver> ' Return value : <String> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String verifyUpdateAvailability(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.UpdateAvailability);
		String updateAvailability = elements.UpdateAvailability.getText();
		rateGridLogger.info("UpdateAvailability :" + updateAvailability);

		return updateAvailability;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyMinimumStayValue> ' Description: <This method will
	 * return minimum stay input enable/disable status in bulk update popup> ' Input
	 * parameters: <WebDriver driver> ' Return value : <String> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public Boolean verifyMinimumStayValue(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.MinimumStayValue);
		Boolean isEnabled = elements.MinimumStayValue.isEnabled();
		rateGridLogger.info("minimumStayValue :" + isEnabled);
		return isEnabled;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyNoCheckInCheckbox> ' Description: <This method will
	 * return noCheckInCheckbox enable/disable status in bulk update popup> ' Input
	 * parameters: <WebDriver driver> ' Return value : <String> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/08/2020>
	 */

	public Boolean verifyNoCheckInCheckbox(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.NoCheckInInput);
		Boolean isEnabled = elements.NoCheckInInput.isEnabled();
		rateGridLogger.info("noCheckInCheckbox :" + isEnabled);
		return isEnabled;
	}

	public String verificationTotalOccupanyIcon(WebDriver driver) throws InterruptedException {
		Actions actions = new Actions(driver);
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.OCCUPANCY_ICON);
		actions.moveToElement(elements.OccupancyIcon).perform();
		String getClause = elements.OcupancyClause.getText();
		ratesGridLogger.info(getClause);
		return getClause;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyNoCheckOutCheckbox> ' Description: <This method will
	 * return noCheckOutCheckbox enable/disable status in bulk update popup> ' Input
	 * parameters: <WebDriver driver> ' Return value : <String> ' Created By:
	 * <Farhan Ghaffar> ' Created On: <07/08/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public Boolean verifyNoCheckOutCheckbox(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR.NoCheckOutInput);
		Boolean isEnabled = elements.NoCheckOutInput.isEnabled();
		rateGridLogger.info("noCheckOutCheckbox :" + isEnabled);
		return isEnabled;
	}

	public String verifyDaysInRangeOfDate(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.NO_DAYS_MATCH);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.NO_DAYS_MATCH), driver);
		String getClause = elements.NO_DAYS_MATCH.getText();
		ratesGridLogger.info(getClause);
		return getClause;
	}

	public boolean getDayState(WebDriver driver, String day) throws InterruptedException {

		String daysCheckBox = "//span[text()='" + day + "']/following-sibling::span";
		Wait.WaitForElement(driver, daysCheckBox);
		Wait.waitForElementToBeVisibile(By.xpath(daysCheckBox), driver);
		Wait.waitForElementToBeClickable(By.xpath(daysCheckBox), driver);
		WebElement daysElement = driver.findElement(By.xpath(daysCheckBox));
		Utility.ScrollToElement_NoWait(daysElement, driver);
		boolean isSelected = daysElement.isEnabled();
		System.out.print(" IS Selected:" + daysElement.isSelected());

		System.out.print(" IS Enabled:" + daysElement.isEnabled());
		return isSelected;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * <<<<<<< HEAD ' Method Name: <getRoomClassListSize> ' Description: <This
	 * method will return list size of specific dropdowns in rate grid > ' Input
	 * parameters: <WebDriver driver, String dropDownName, String dropDownOption> '
	 * Return value : <int > ' Created By: <Farhan Ghaffar> ' Created On:
	 * <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public int getDropDownOptionsSize(WebDriver driver, String dropDownName, String dropDownOption)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		String bulkUpdateDropDownPath = "(//label[text()='" + dropDownName
				+ "']//following-sibling::div//input[@role='combobox'])[1]";

		Wait.WaitForElement(driver, bulkUpdateDropDownPath);
		Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
		WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
		Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
		bulkUpdateDropdown.sendKeys(dropDownOption);
		rateGridLogger.info("Entered " + dropDownName + " name : " + dropDownOption);
		testSteps.add("Entered " + dropDownName + " name : " + dropDownOption);

		String roomClassesPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
		List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));
		logger.info("roomClassesElement: " + roomClassesElement.size());

		return roomClassesElement.size();
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickCancelButton> ' Description: <This method will click
	 * cancel button in bulk update popup> ' Input parameters: <WebDriver driver> '
	 * Return value : <ArrayList<String>> ' Created By: <Farhan Ghaffar> ' Created
	 * On: <07/13/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> clickOnYesUpdateButton(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.Button_YesUpdate);
		Wait.waitForElementToBeVisibile(By.xpath(OR.Button_YesUpdate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.Button_YesUpdate), driver);
		elements.Button_YesUpdate.click();
		testSteps.add("Clicked on yes update button");
		rateGridLogger.info("Clicked on yes update button");
		return testSteps;
	}

	public ArrayList<String> getActiveAndInactiveRatePlanNames(WebDriver driver, String ratePlanType)
			throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		clickRateDropDown(driver, new ArrayList<String>());
		Wait.WaitForElement(driver, ORRateGrid.ratePlanNamesList);
		for (int i = 0; i < elements.ratePlanNames.size(); i++) {
			Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);
			if (ratePlanType.equals("active")) {
				if (!elements.ratePlanNames.get(i).getText().equals("Manual Override")
						&& !elements.ratePlanNames.get(i).getText().contains("[Inactive]")) {
					rateGridLogger.info("Get List of Active Rate Plans: " + elements.ratePlanNames.get(i).getText());

					getNames.add(elements.ratePlanNames.get(i).getText());
				}

			}

			else if (ratePlanType.equals("inactive")) {
				if (elements.ratePlanNames.get(i).getText().contains("[Inactive]")) {
					rateGridLogger.info("Get List of In Active Rate Plans: " + elements.ratePlanNames.get(i).getText());

					getNames.add(elements.ratePlanNames.get(i).getText());
				}
			} else if (ratePlanType.equalsIgnoreCase("all")) {
				getNames.add(elements.ratePlanNames.get(i).getText());
			}
		}
		return getNames;
	}

	/*
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 * 
	 * ' Method Name: verifyRulesIcon ' Description: This method will verify rules
	 * symbol in rate grid ' Input parameters: WebDriver driver, String
	 * roomClassName ' Return value: ArrayList<String> ' Created By: Farhan Ghaffar
	 * ' Created On: 17-07-2020
	 * 
	 * #########################################################################
	 * ####
	 * #########################################################################
	 * #### ################
	 */
	public ArrayList<String> verifyRulesIcon(WebDriver driver, String roomClassName) throws InterruptedException {
		String rulesPath = "(//div[text()='" + roomClassName
				+ "']//parent::div//following::div[@class='RuleIndication'])[1]";
		WebElement rulesElement = driver.findElement(By.xpath(rulesPath));
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, rulesPath);
		Wait.waitForElementToBeVisibile(By.xpath(rulesPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(rulesPath), driver);
		Utility.ScrollToElement_NoWait(rulesElement, driver);
		// Assert.assertTrue(rulesElement.isDisplayed(), "Faield : rules icon
		// didn't display");
		rateGridLogger.info("verified rules icon display for room class : " + roomClassName);
		testSteps.add("verified rules icon display for room class : " + roomClassName);

		return testSteps;
	}

	public void clickUpdate(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		elements.UpdateButton.click();

	}

	public ArrayList<String> clickOnCalendarIcon(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, ORRateGrid.calendarIcon);
		Wait.waitForElementToBeVisibile(By.xpath(ORRateGrid.calendarIcon), driver);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.calendarIcon), driver);
		elements.calendarIcon.click();
		testSteps.add("Clicked on calendar icon");
		rateGridLogger.info("Clicked on calendar icon");
		return testSteps;
	}

	public String getMonthFromCalendarHeading(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, ORRateGrid.calendarMonthHeading);
		Wait.waitForElementToBeVisibile(By.xpath(ORRateGrid.calendarMonthHeading), driver);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.calendarMonthHeading), driver);
		return elements.calendarMonthHeading.getText();

	}

	public ArrayList<String> clickOnCalendarRightArrow(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, ORRateGrid.calendarRightArrow);
		Wait.waitForElementToBeVisibile(By.xpath(ORRateGrid.calendarRightArrow), driver);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.calendarRightArrow), driver);
		elements.calendarRightArrow.click();
		testSteps.add("Clicked on calendar right arrow");
		rateGridLogger.info("Clicked on calendar right arrow");
		return testSteps;
	}

	public Boolean verifyTotalOccupanyValue(WebDriver driver) throws InterruptedException {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.totalOccupanyValue);
		Boolean isEnabled = ratesGrid.totalOccupanyValue.isEnabled();
		ratesGridLogger.info("totalOccupanyValue :" + isEnabled);

		return isEnabled;
	}

	public ArrayList<String> getActiveRatePlanNames(WebDriver driver, String ratePlanType) throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		int size = elements.ratePlanNames.size();
		logger.info(size);
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				Wait.wait2Second();
				elements.ratePlanArrow.click();

			}
			Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);

			String rate = elements.ratePlanNames.get(i).getText();
			if (ratePlanType.equals("active")) {
				elements.ratePlanNames.get(i).click();

				if (elements.getRatePlanDescriptionList.size() > 0) {
					if (!elements.getRatePlanDescription.getText().contains("Interval")
							&& !rate.equals("Manual Override") && !rate.contains("[Inactive]")) {
						logger.info("Active added if:" + rate);

						getNames.add(rate);
						logger.info(rate);

					}
				}

				else if (elements.getRatePlanDescriptionList.size() == 0 && !rate.equals("Manual Override")) {
					logger.info("Active added Else:" + rate);
					getNames.add(rate);
					logger.info(rate);

				}
			}

		}

		logger.info(getNames.size());
		return getNames;
	}

	public HashMap<String, String> getAvailabilityOfRoomClass(WebDriver driver, String dateFormat, String startDate,
			String endDate, String roomClass) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> availibility = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> availibilityDate = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		availibility = getRoomClassAvailibilityDataValues(driver, roomClass);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					availibilityDate.put(dayList.get(j), availibility.get(i));
				}
			}

		}
		logger.info("Rate Grid Availibility as per  Date  " + availibilityDate);
		return availibilityDate;

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRatePlanNames> ' Description: < Get all the Rate Plan Name
	 * based on reguired status> ' Input parameters: < WebDriver driver,String
	 * status,String test_steps> ' Return value: <ArrayList<String>> ' Created By:
	 * <Adhnan Ghaffar> ' ' Created On: <MM/dd/yyyy> <07/27/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> getRatePlanNames(WebDriver driver, String status) throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanNamesList);
		Utility.ScrollToElement(elements.ratePlanNamesList.get(0), driver);
		logger.info("Required Status: " + status);
		for (WebElement ele : elements.ratePlanNamesList) {
			// Utility.ScrollToElement(ele, driver);
			// logger.info("current ratePlan : " +
			// ele.getAttribute("aria-label"));
			if (status.equalsIgnoreCase("All")) {
				getNames.add(ele.getAttribute("aria-label"));
			} else if (status.equalsIgnoreCase("active")) {
				if (!ele.getAttribute("aria-label").contains("Inactive")) {
					getNames.add(ele.getAttribute("aria-label"));
					logger.info("Active Rate Plan Found");
				}
			} else if (ele.getAttribute("aria-label").contains("Inactive")) {
				getNames.add(ele.getAttribute("aria-label"));
				logger.info("Inactive Rate Plan Found");
			}

		}
		logger.info("Get List of All Rate Plans: " + getNames);
		return getNames;

	}

	public ArrayList<String> verifyRoomClasses(WebDriver driver, ArrayList<String> getRoomClasses,
			ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ROOM_CLASSES_NAMES);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		String expectedRoomClass = null;
		String foundRoomClass = null;
		String previousRoomClass = null;
		boolean inorder = true;
		for (int i = 0; i < getRoomClasses.size(); i++) {
			ratesGridLogger.info("Room Class " + (i + 1));
			expectedRoomClass = getRoomClasses.get(i);
			ratesGridLogger.info("Expected Room Class : " + expectedRoomClass);
			Utility.ScrollToElement(ratesGrid.ROOM_CLASSES_NAMES.get(i), driver);
			foundRoomClass = ratesGrid.ROOM_CLASSES_NAMES.get(i).getText();
			ratesGridLogger.info("Found Room Class : " + foundRoomClass);
			assertEquals(foundRoomClass, expectedRoomClass, "Failed: Room Class missmatched");
			if (previousRoomClass == null) {
				previousRoomClass = foundRoomClass;
			} else {
				if (!(previousRoomClass.charAt(0) <= foundRoomClass.charAt(0))) {
					inorder = false;
				}
			}
			previousRoomClass = foundRoomClass;

		}
		ratesGridLogger.info("Successfully verified All Active Room classes are shown one below another");
		testSteps.add("Successfully verified All Active Room classes are shown one below another");
		if (inorder) {
			ratesGridLogger.info("Successfully verified All Active Room classes are in ascending order.");
			testSteps.add("Successfully verified All Active Room classes are in ascending order.");
		} else {
			ratesGridLogger.info("Failed: Room classes are not in ascending order");
			testSteps.add("Failed: Room classes are not in ascending order");
		}
		return testSteps;
	}

	// Added By Adhnan 7/7/2020
	public String getRoomStatus(WebDriver driver, String date, String dateFormat, String source, String roomClassName)
			throws InterruptedException, ParseException {
		int index = getHeadingDateIndex(driver, date, dateFormat);
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='" + source
				+ "']//parent::div/following-sibling::div";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		String roomStatus = labelValues.get(index).getAttribute("class");
		if (roomStatus.contains("NoBlacked")) {
			roomStatus = "*";
		} else {
			roomStatus = "B";
		}
		if (roomStatus.equals("B")) {
			xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='"
					+ roomClassName + "']//parent::div/following-sibling::div)[" + (index + 1)
					+ "]/div[@class='Blackout']/span";
			Wait.WaitForElement(driver, xpath);
			Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			WebElement blackout = driver.findElement(By.xpath(xpath));
			Utility.ScrollToElement(blackout, driver);
			assertEquals(blackout.getText(), "B", "Failed: B is not visible under Percentage value of the room");
		}
		return roomStatus;
	}

	public ArrayList<String> clickAddRatePlan(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ADDRATEPLAN);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ADDRATEPLAN), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ADDRATEPLAN), driver);
		Utility.ScrollToElement(ratesGrid.ADDRATEPLAN, driver);
		ratesGrid.ADDRATEPLAN.click();
		testSteps.add("Click add rate plan button");
		ratesGridLogger.info("Click add rate plan button");

		return testSteps;
	}

	public ArrayList<String> clickNextButton(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.NextButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.NextButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.NextButton), driver);
		Utility.ScrollToElement(ratesGrid.NextButton, driver);
		ratesGrid.NextButton.click();
		testSteps.add("Click next button");
		ratesGridLogger.info("Click next button");

		return testSteps;
	}

	public ArrayList<String> clickCreateSeason(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.CreateSeason);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.CreateSeason), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.CreateSeason), driver);
		Utility.ScrollToElement(ratesGrid.CreateSeason, driver);
		ratesGrid.CreateSeason.click();
		testSteps.add("Click create season button");
		ratesGridLogger.info("Click create season button");

		return testSteps;
	}

	public ArrayList<String> selectSeasonDate(WebDriver driver, String date) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String month = Utility.get_Month(date);
		ratesGridLogger.info(month.toUpperCase());
		String day = Utility.getDay(date);
		ratesGridLogger.info(day);
		String year = Utility.get_Year(date);
		ratesGridLogger.info(year);

		String seasonDatePath = "(//div[text()='" + month.toUpperCase() + " " + year
				+ "']//following::div[@class='DayPicker-Body'][1]//div[@class='DayPicker-Week']//following-sibling::div//div[contains(text(),'"
				+ day + "')])[1]";
		WebElement seasonDateElement = driver.findElement(By.xpath(seasonDatePath));
		Wait.WaitForElement(driver, seasonDatePath);
		Wait.waitForElementToBeVisibile(By.xpath(seasonDatePath), driver);
		Wait.waitForElementToBeClickable(By.xpath(seasonDatePath), driver);
		Utility.ScrollToElement(seasonDateElement, driver);
		seasonDateElement.click();
		testSteps.add("Select Start Date : " + date);
		ratesGridLogger.info("Select Start Date : " + date);

		return testSteps;
	}

	public ArrayList<String> clickCompleteChanges(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.CompleteChanges);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.CompleteChanges), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.CompleteChanges), driver);
		Utility.ScrollToElement(ratesGrid.CompleteChanges, driver);
		ratesGrid.CompleteChanges.click();
		testSteps.add("Click complete changes button");
		ratesGridLogger.info("Click complete changes button");

		return testSteps;
	}

	public ArrayList<String> clickSaveAsActive(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.SaveAsActive);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.SaveAsActive), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.SaveAsActive), driver);
		Utility.ScrollToElement(ratesGrid.SaveAsActive, driver);
		ratesGrid.SaveAsActive.click();
		testSteps.add("Click save as active button");
		ratesGridLogger.info("Click save as active button");

		return testSteps;
	}

	public ArrayList<String> enterRateName(WebDriver driver, String rateName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.RateNameInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.RateNameInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.RateNameInput), driver);
		Utility.ScrollToElement(ratesGrid.RateNameInput, driver);
		ratesGrid.RateNameInput.clear();
		ratesGrid.RateNameInput.sendKeys(rateName);

		testSteps.add("Entered rate name : " + rateName);
		ratesGridLogger.info("Entered rate name : " + rateName);

		return testSteps;
	}

	public ArrayList<String> selectRatePlanType(WebDriver driver, String ratePLanType) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		// Nightly rate plan
		String ratePlanTypePath = "//a[text()='" + ratePLanType + "']";
		WebElement ratePlanElement = driver.findElement(By.xpath(ratePlanTypePath));

		Wait.WaitForElement(driver, ratePlanTypePath);
		Wait.waitForElementToBeVisibile(By.xpath(ratePlanTypePath), driver);
		Wait.waitForElementToBeClickable(By.xpath(ratePlanTypePath), driver);
		ratePlanElement.click();
		testSteps.add("Selected rate plan type : " + ratePLanType);
		ratesGridLogger.info("Selected rate plan type : " + ratePLanType);

		return testSteps;
	}

	public boolean verifyProrateAtSeasonLevel(WebDriver driver, boolean isProRateStayInRatePlan,
			boolean isProrateCheckboxCheccked, ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.CheckboxProrate);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrate), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrate), driver);
		boolean isProRateChecked = false;
		if (isProrateCheckboxCheccked) {
			if (!isProRateStayInRatePlan) {
				ratesGrid.CheckboxProrate.click();
				// testSteps.add("Checked prorate stay in season");
				isProRateChecked = true;
			}

		}
		if (!isProrateCheckboxCheccked) {
			if (!isProRateStayInRatePlan) {
				// ratesGrid.CheckboxProrate.click();
				// testSteps.add("Pro rate stay in season is checked? "+);
			}
		}

		if (!isProrateCheckboxCheccked) {
			if (isProRateStayInRatePlan) {
				ratesGrid.CheckboxProrate.click();
				// testSteps.add("Checked prorate stay in season");
				isProRateChecked = true;
			}

		}

		if (isProrateCheckboxCheccked) {
			if (isProRateStayInRatePlan) {
				// ratesGrid.CheckboxProrate.click();
				// testSteps.add("Checked prorate stay in season");
				isProRateChecked = true;
			}

		}
		return isProRateChecked;
	}

	public ArrayList<String> clickRatePlanCheckBoxes(WebDriver driver, String roomClass) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		// Nightly rate plan
		String roomClassPath = "//span[text()='" + roomClass + "']//preceding-sibling::span";
		WebElement roomClassElement = driver.findElement(By.xpath(roomClassPath));

		Wait.WaitForElement(driver, roomClassPath);
		Wait.waitForElementToBeVisibile(By.xpath(roomClassPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(roomClassPath), driver);
		roomClassElement.click();
		testSteps.add(roomClass + " checkbox checked");
		ratesGridLogger.info(roomClass + " checkbox checked");

		return testSteps;
	}

	public ArrayList<String> verifyRulesValue(WebDriver driver, String ratePlanType, String channels, String startDate,
			String endDate, String dateFormat, String timeZone, String overrideMinStayValue,
			String overrideNoCheckInValue, String overrideNoCheckOutValue, HashMap<String, Boolean> dayMap)
			throws InterruptedException, ParseException {
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.wait2Second();
		StringTokenizer token = new StringTokenizer(channels, Utility.DELIM);
		while (token.hasMoreTokens()) {
			String channelname = token.nextToken();
			expandRoomClass(driver, channelname);
			logger.info("Expand room channel '" + channelname);
			int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
			System.out.println("days : " + days);
			for (int i = 0; i <= days; i++) {
				String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", i, timeZone);
				System.out.println("day : " + day);
				if (dayMap.get(day)) {

					String getRule = getRule(driver, "Min Stay", i + 1);
					// testSteps.add("Expected "+ratePlanType+" Rate Plan Rule Value : '" +
					// overrideMinStayValue + "'");
					logger.info("Expected " + ratePlanType + " Rate Plan Rule Value : '" + overrideMinStayValue + "'");
					// testSteps.add("Found "+ratePlanType+" Rate Plan Rule Value : '" + getRule +
					// "'");

					logger.info("Found " + ratePlanType + " Rate Plan Rule Value : '" + getRule + "'");
					testSteps.add(
							"Successfully Verified  " + ratePlanType + " Rate Plan Rule Value : '" + getRule + "'");
					logger.info("Successfully Verified  " + ratePlanType + " Rate Plan Rule Value : '" + getRule + "'");
					if (!getRule.equals(overrideMinStayValue)) {
						Wait.wait5Second();
					}
					Assert.assertTrue(getRule.equals(overrideMinStayValue),
							"Failed " + ratePlanType + " Rate MinStay Value Missmatched");
					String getNoCheckIn = getNoCheckInCheckOut(driver, "No Check In", i + 1);
					// testSteps.add("Expected "+ratePlanType+" Rate Plan No Check In Rule Value :
					// '" + overrideNoCheckInValue + "'");
					logger.info("Expected " + ratePlanType + " Rate Plan No Check In Rule Value : '"
							+ overrideNoCheckInValue + "'");
					// testSteps.add("Found "+ratePlanType+" Rate Plan No Check In Rule Value : '" +
					// getNoCheckIn + "'");
					logger.info("Found " + ratePlanType + " Rate Plan No Check In Rule Value : '" + getNoCheckIn + "'");
					testSteps.add("Successfully Verified  " + ratePlanType + " Rate Plan No Check In Rule Value : '"
							+ getNoCheckIn + "'");
					logger.info("Successfully Verified  " + ratePlanType + " Rate Plan No Check In Rule Value : '"
							+ getNoCheckIn + "'");
					Assert.assertTrue(getNoCheckIn.contains(overrideNoCheckInValue),
							"Failed " + ratePlanType + " Rate No CheckIn Value Missmatched");
					String getNoCheckOut = getNoCheckInCheckOut(driver, "No Check Out", i + 1);
					// testSteps.add("Expected "+ratePlanType+" Rate Plan No Check Out Rule Value :
					// '" + overrideNoCheckOutValue + "'");
					logger.info("Expected " + ratePlanType + " Rate Plan No Check Out Rule Value : '"
							+ overrideNoCheckOutValue + "'");

					// testSteps.add("Found "+ratePlanType+" Rate Plan No Check Out Rule Value : '"
					// + getNoCheckOut + "'");

					logger.info(
							"Found " + ratePlanType + " Rate Plan No Check Out Rule Value : '" + getNoCheckOut + "'");
					testSteps.add("Successfully Verified " + ratePlanType + " Rate Plan No Check Out Rule Value : '"
							+ getNoCheckOut + "'");
					logger.info("Successfully Verified " + ratePlanType + " Rate Plan No Check Out Rule Value : '"
							+ getNoCheckOut + "'");
					Assert.assertTrue(getNoCheckOut.contains(overrideNoCheckOutValue),
							"Failed " + ratePlanType + " Rate No CheckOut Value Missmatched");
				}
			}
			expandRoomClass(driver, channelname);
			logger.info("Reduce room channel '" + channelname);
		}

		return testSteps;
	}

	/*
	 * // Added By adhnan 8/04/2020 public ArrayList<String>
	 * getActiveRatePlans(WebDriver driver, String ratePlanType) throws
	 * InterruptedException { ArrayList<String> getNames = new ArrayList<String>();
	 * Elements_RatesGrid elements = new Elements_RatesGrid(driver);
	 * Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList); int size =
	 * elements.ratePlanNames.size(); logger.info(size); for (int i = 0; i < size;
	 * i++) { if (i > 0) { Wait.wait2Second(); elements.ratePlanArrow.click(); }
	 * Utility.ScrollToElement(elements.ratePlanNames.get(i), driver); String rate =
	 * elements.ratePlanNames.get(i).getText();
	 * elements.ratePlanNames.get(i).click(); if
	 * (elements.getRatePlanDescriptionList.size() > 0) { if
	 * (elements.getRatePlanDescription.getText().contains(ratePlanType)) {
	 * logger.info(ratePlanType + " added :" + rate); getNames.add(rate);
	 * logger.info(rate); } } else { logger.info("Rate Plan not required" + rate);
	 * logger.info(rate);
	 * 
	 * } }
	 * 
	 * logger.info(getNames.size()); return getNames; }
	 */
	public void verifyRoomClassAfterDelete(WebDriver driver, String roomClass, ArrayList<String> testSteps)
			throws InterruptedException {

		String path = "//div[@class='DatesTable']//div[@class='roomClassName' and contains(text(),'" + roomClass
				+ "')]";
		boolean isExist = Utility.isElementPresent(driver, (By.xpath(path)));
		assertEquals(isExist, false, "Failed to verify room Class");
		testSteps.add("Verified Room Class : <b> Not Displayed </b>");
		logger.info("Verified Room Class : Not Displayed");

	}

	public ArrayList<String> enterNights(WebDriver driver, String nights, int index) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		// 1-3
		String roomClassPath = "(//div[text()='Nights']//preceding-sibling::div[1]//following::input)[" + index + "]";
		WebElement roomClassElement = driver.findElement(By.xpath(roomClassPath));

		Wait.WaitForElement(driver, roomClassPath);
		Wait.waitForElementToBeVisibile(By.xpath(roomClassPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(roomClassPath), driver);
		roomClassElement.click();
		testSteps.add("Entered Nights : " + nights);
		ratesGridLogger.info("Entered Nights : " + nights);

		return testSteps;
	}

	public ArrayList<String> enterSeasonName(WebDriver driver, String season) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.SeasonNameInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.SeasonNameInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.SeasonNameInput), driver);
		Utility.ScrollToElement(ratesGrid.SeasonNameInput, driver);
		ratesGrid.SeasonNameInput.clear();
		ratesGrid.SeasonNameInput.sendKeys(season);

		testSteps.add("Entered season name : " + season);
		ratesGridLogger.info("Entered season name : " + season);

		return testSteps;
	}

	public ArrayList<String> clickCreateSeasonButton(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.CreateSeasonButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.CreateSeasonButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.CreateSeasonButton), driver);
		Utility.ScrollToElement(ratesGrid.CreateSeasonButton, driver);
		ratesGrid.CreateSeasonButton.click();
		testSteps.add("Click complete season button");
		ratesGridLogger.info("Click complete season button");

		return testSteps;
	}

	public ArrayList<String> clickBlackOutButton(WebDriver driver) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.BlackOutButton);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.BlackOutButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.BlackOutButton), driver);
		Utility.ScrollToElement(ratesGrid.BlackOutButton, driver);
		ratesGrid.BlackOutButton.click();
		testSteps.add("Click black out button");
		ratesGridLogger.info("Click black out button");

		return testSteps;
	}

	// Added By Adhnan 7/21/2020
	public ArrayList<String> getRoomClasses(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> roomClassesNames = new ArrayList<String>();
		Wait.WaitForElement(driver, OR_RatesGrid.ROOM_CLASSES_NAMES);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		String expectedRoomClass = null;
		String foundRoomClass = null;
		String previousRoomClass = null;
		boolean inorder = true;
		for (int i = 0; i < ratesGrid.ROOM_CLASSES_NAMES.size(); i++) {
			ratesGridLogger.info("Room Class " + (i + 1));
			Utility.ScrollToElement(ratesGrid.ROOM_CLASSES_NAMES.get(i), driver);
			foundRoomClass = ratesGrid.ROOM_CLASSES_NAMES.get(i).getText();
			ratesGridLogger.info("Found Room Class : " + foundRoomClass);
			roomClassesNames.add(foundRoomClass);

		}
		return roomClassesNames;
	}

	// Added By Adhnan Ghaffar 7/21/2020
	public String getRoomClassAvailableValueInRatesGridTab(WebDriver driver, int index, String label)
			throws InterruptedException {
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='" + label
				+ "']//ancestor::div[@class='row d-flex']/following-sibling::div/div";
		Wait.WaitForElement(driver, xpath);
		Wait.waitForElementToBeClickable(By.xpath(xpath), driver);
		Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
		java.util.List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		return labelValues.get(index).getText();
	}

	// Added By adhnan 8/04/2020
	/*
	 * public ArrayList<String> getActiveRatePlans(WebDriver driver, String
	 * ratePlanType) throws InterruptedException { ArrayList<String> getNames = new
	 * ArrayList<String>(); Elements_RatesGrid elements = new
	 * Elements_RatesGrid(driver); Wait.WaitForElement(driver,
	 * OR_RatesGrid.ratePlanNamesList); int size = elements.ratePlanNames.size();
	 * logger.info(size); for (int i = 0; i < size; i++) { if (i > 0) {
	 * Wait.wait2Second(); elements.ratePlanArrow.click(); }
	 * Utility.ScrollToElement(elements.ratePlanNames.get(i), driver); String rate =
	 * elements.ratePlanNames.get(i).getText();
	 * elements.ratePlanNames.get(i).click(); if
	 * (elements.getRatePlanDescriptionList.size() > 0) { if
	 * (elements.getRatePlanDescription.getText().contains(ratePlanType)) {
	 * logger.info(ratePlanType + " added :" + rate); getNames.add(rate);
	 * logger.info(rate); } } else { logger.info("Rate Plan not required" + rate);
	 * logger.info(rate);
	 * 
	 * } }
	 * 
	 * logger.info(getNames.size()); return getNames; }
	 */
	// Added By adhnan 8/04/2020
	public ArrayList<String> getActiveRatePlans(WebDriver driver, String ratePlanType, String ratePlanType2,
			int startIndex) throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		int size = elements.ratePlanNames.size();
		logger.info(size);
		String rate = null;
		for (int i = startIndex; i < size; i++) {
			// if(i > 50){
			// break;
			// }
			if (i > startIndex /*
								 * 
								 * && !rate.contains("[Inactive]") && rate!=null
								 */) {

				// Wait.wait2Second();
				elements.ratePlanArrow.click();
				logger.info("click rate Plan Arrow");
			}
			// Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);
			rate = elements.ratePlanNames.get(i).getText();
			logger.info(rate);
			if (!rate.contains("[Inactive]")) {
				elements.ratePlanNames.get(i).click();
				String selectedRatePlanxpath = "//span[@class='rate-plan-picker-label']";
				Wait.WaitForElement(driver, selectedRatePlanxpath);
				WebElement selectedRatePlan = driver.findElement(By.xpath(selectedRatePlanxpath));
				Wait.explicit_wait_visibilityof_webelement(selectedRatePlan, driver);
				if (selectedRatePlan.getText().equals(rate)) {
					if (elements.getRatePlanDescriptionList.size() > 0) {
						if (elements.getRatePlanDescription.getText().contains(ratePlanType)) {
							logger.info(i + " : " + ratePlanType + " added :" + rate);
							getNames.add(rate);
							logger.info(rate);
						} else if (elements.getRatePlanDescription.getText().contains(ratePlanType2)) {
							logger.info(i + " : " + ratePlanType2 + " added :" + rate);
							getNames.add(rate);
							logger.info(rate);
						}
					} else {
						logger.info("Rate Plan not required" + rate);
						logger.info(rate);

					}
				}
			} else {

				logger.info("InActive Rate Plan found");
				clickRatePlanArrowOpenIcon(driver, new ArrayList<String>());
				break;
			}
		}

		logger.info(getNames.size());
		return getNames;
	}

	public String getHeading(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.GetTitile);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.GetTitile), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.GetTitile), driver);
		return ratesGrid.GetTitile.getText();

	}

	public String getDefultValueOfInterval(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.EnterInterval);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.EnterInterval), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.EnterInterval), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.EnterInterval, driver);
		return ratesGrid.EnterInterval.getAttribute("value");
	}

	public ArrayList<String> enterInterval(WebDriver driver, String interval) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.EnterInterval);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.EnterInterval), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.EnterInterval), driver);
		String getValue = ratesGrid.EnterInterval.getAttribute("value");
		ratesGrid.EnterInterval.click();
		ratesGrid.EnterInterval.clear();
		for (int i = 0; i < getValue.length(); i++) {
			ratesGrid.EnterInterval.sendKeys(Keys.BACK_SPACE);
		}
		ratesGrid.EnterInterval.sendKeys(interval);
		testSteps.add("Enter interval: " + interval);
		return testSteps;
	}

	public String getTextAfterCheckedProRateCheckbox(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.TextAfterCheckedProRateCheckbox);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.TextAfterCheckedProRateCheckbox), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.TextAfterCheckedProRateCheckbox), driver);
		return ratesGrid.TextAfterCheckedProRateCheckbox.getText();

	}

	public ArrayList<String> byDefaultProrateCheckbox(WebDriver driver, boolean isChecked) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.CheckboxProrateForEachSeasonbyDefault);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		if (isChecked) {
			if (!ratesGrid.ProRateCheckbox.getAttribute("class").contains("ant-checkbox-checked")) {
				ratesGrid.CheckboxProrateForEachSeasonbyDefault.click();
				testSteps.add("Checked pro-rate each season  checkbox checked");
			} else {
				testSteps.add("Checked pro-rate each season by default checked");

			}

		} else {
			if (ratesGrid.ProRateCheckbox.getAttribute("class").contains("ant-checkbox-checked")) {
				ratesGrid.CheckboxProrateForEachSeasonbyDefault.click();
				testSteps.add("UnChecked Pro-rate each season checkbox");
			} else {
				testSteps.add("Bydefault unchecked pro-rate each season");

			}

		}
		return testSteps;
	}

	public ArrayList<String> clickOnAdditionalChargForAdultsAndChildern(WebDriver driver, boolean isToggleButtonOn)
			throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.ChargeForAdditionalAdultsAndChildern);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.ChargeForAdditionalAdultsAndChildern), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.ChargeForAdditionalAdultsAndChildern), driver);
		if (isToggleButtonOn) {
			if (!ratesGrid.ChargeForAdditionalAdultsAndChildern.getAttribute("class").contains("ant-switch-checked")) {
				ratesGrid.ChargeForAdditionalAdultsAndChildern.click();
				testSteps.add("Charge for additional adult/child toggle button is on");
			} else {
				testSteps.add("Charge for additional adult/child toggle button is already on");
			}

		} else {
			if (ratesGrid.ChargeForAdditionalAdultsAndChildern.getAttribute("class").contains("ant-switch-checked")) {
				ratesGrid.ChargeForAdditionalAdultsAndChildern.click();
				testSteps.add("Charge for additional adult/child toggle button is off");
			} else {
				testSteps.add("Charge for additional adult/child toggle button is already off");
			}
		}
		return testSteps;
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

	public ArrayList<String> enterRoomClassRateWithAdditionalAdultsAndChild(WebDriver driver, String roomClassName,
			String rate, String adultRate, String childRate) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		logger.info(enterRate);
		// Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);

		clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
		element.get(0).sendKeys(rate);
		testSteps.add("Enter room class '" + roomClassName + "' rate per night: " + rate);

		element.get(3).click();
		clearInputField(driver, element.get(3), element.get(3).getAttribute("value"));
		element.get(3).sendKeys(adultRate);
		testSteps.add("Enter adult for '" + roomClassName + "' rate per night: " + rate);

		element.get(4).click();
		clearInputField(driver, element.get(4), element.get(4).getAttribute("value"));

		element.get(4).sendKeys(childRate);
		testSteps.add("Enter child for '" + roomClassName + "' rate per night: " + rate);
		element.get(0).click();

		return testSteps;
	}

	public ArrayList<String> enterRoomClassRate(WebDriver driver, String roomClassName, String rate)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();

		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);
		clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
		element.get(0).sendKeys(rate);
		testSteps.add("Enter room class '" + roomClassName + "' rate per night: " + rate);
		rateGridLogger.info("Enter room class '" + roomClassName + "' rate per night: " + rate);

		return testSteps;
	}

	public ArrayList<String> getPerNightProrate(WebDriver driver, String roomClassName, boolean isAdditionCharge)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);
		testSteps.add("Per night room class '" + roomClassName + "' rate: " + element.get(0).getAttribute("value"));

		if (isAdditionCharge) {
			testSteps.add("Addtional adult per night: " + element.get(1).getAttribute("value"));
			testSteps.add("Addtional child per night: " + element.get(2).getAttribute("value"));

		}
		return testSteps;

	}

	public boolean verifyDefaultProrateCheckbox(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.VerifyDefaultCheckbox);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.VerifyDefaultCheckbox), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.VerifyDefaultCheckbox), driver);
		ratesGrid.CheckboxProrateForEachSeasonbyDefault.click();
		logger.info("contains calss: " + ratesGrid.VerifyDefaultCheckbox.getAttribute("class"));
		boolean isCheckBoxChecked = false;
		if (ratesGrid.VerifyDefaultCheckbox.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {
			isCheckBoxChecked = true;
		}
		return isCheckBoxChecked;
	}

	public ArrayList<String> getIntervalNight(WebDriver driver, String roomClassName, boolean isAdditionCharge)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		String enterRate = "(//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//parent::div//parent::li//..//li)[1]//ul//li";
		Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);
		logger.info(element.get(0).getText());
		testSteps.add(element.get(0).getText());

		if (isAdditionCharge) {
			testSteps.add(element.get(4).getText());
			testSteps.add(element.get(5).getText());
		}
		return testSteps;
	}

	public boolean verifyToggleBtnAdditionalChargForAdultsAndChildernisOn(WebDriver driver)
			throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.ChargeForAdditionalAdultsAndChildern);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.ChargeForAdditionalAdultsAndChildern), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.ChargeForAdditionalAdultsAndChildern), driver);
		boolean isTpggleButtonOn = false;
		if (ratesGrid.ChargeForAdditionalAdultsAndChildern.getAttribute("class").contains("ant-switch-checked")) {
			isTpggleButtonOn = true;
		}

		return isTpggleButtonOn;
	}

	public ArrayList<String> getRatePerNight(WebDriver driver, String roomClassName, boolean isAdditionCharge)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		String getRate = "//span[text()='" + roomClassName
				+ "']//parent::label//..//following-sibling::div[contains(@class,'AddRoomClassList')]//input[@class='ant-input-number-input']";
		Wait.WaitForElement(driver, getRate);
		Wait.waitForElementToBeVisibile(By.xpath(getRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(getRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(getRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);
		testSteps.add(element.get(0).getAttribute("value"));
		logger.info(element.get(0).getAttribute("value"));
		if (isAdditionCharge) {
			testSteps.add(element.get(1).getAttribute("value"));
			logger.info("adult: " + element.get(1).getAttribute("value"));
			testSteps.add(element.get(2).getAttribute("value"));
			logger.info("child: " + element.get(2).getAttribute("value"));
		}
		return testSteps;
	}

	public ArrayList<String> VerifyAdditonalAdultandChildFiled(WebDriver driver, String roomClassName, boolean isOn)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		logger.info(enterRate);
		// Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);

		assertEquals(element.get(3).isDisplayed(), isOn, "Failed: addition adult input field is not displaying");
		assertEquals(element.get(4).isDisplayed(), isOn, "Failed: addition child input field is not displaying");
		testSteps.add("Verifird additional charges input fields for adult and child are displaying for room class: "
				+ roomClassName);

		return testSteps;
	}

	public boolean proRateCheckboxInSeason(WebDriver driver, String roomClass, boolean isClick, boolean isVerify,
			boolean isChecked) throws InterruptedException {
		String getproRateCheckbox = "//span[text()='" + roomClass
				+ "']//parent::label//..//following-sibling::div[contains(@class,'AddRoomClassList')]//label//span//span//parent::span";
		logger.info(getproRateCheckbox);
		// Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(getproRateCheckbox), driver);
		Wait.waitForElementToBeClickable(By.xpath(getproRateCheckbox), driver);
		WebElement element = driver.findElement(By.xpath(getproRateCheckbox));
		Utility.ScrollToElement_NoWait(element, driver);
		boolean isCheckBoxChecked = false;
		if (isVerify) {
			if (element.getAttribute("class").contains("ant-checkbox-checked")) {
				isCheckBoxChecked = true;
			}
		} else if (isClick) {
			if (isChecked) {
				if (!element.getAttribute("class").contains("ant-checkbox-checked")) {
					element.click();
					logger.info("checked");
				}

			} else {
				if (element.getAttribute("class").contains("ant-checkbox-checked")) {
					element.click();
					logger.info("unchecked");
				}
			}
		}

		return isCheckBoxChecked;
	}

	public String calculateAmountPerNight(String interval, String rate) {
		double totalAmountForPerNight = Double.parseDouble(rate) / Double.parseDouble(interval);
		String converDoubleToString = String.format("%.2f", totalAmountForPerNight);
		logger.info("totalAmountForPerNight:: " + totalAmountForPerNight);
		if (converDoubleToString.contains(".00")) {
			converDoubleToString = converDoubleToString.replace(".00", "");
		}

		return converDoubleToString;

	}

	public ArrayList<String> clickAddMoreRoomClass(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.AddMoreRoomClassesButton);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.AddMoreRoomClassesButton), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.AddMoreRoomClassesButton), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.AddMoreRoomClassesButton, driver);
		ratesGrid.AddMoreRoomClassesButton.click();
		testSteps.add("Click on add more room classes");

		return testSteps;
	}

	public ArrayList<String> selectRoomClasses(WebDriver driver, String roomClass) throws InterruptedException {
		String roomClassPath = "//span[text()='" + roomClass + "']";
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, roomClassPath);
		// Wait.waitForElementToBeVisibile(By.xpath(roomClassPath), driver);
		// Wait.waitForElementToBeClickable(By.xpath(roomClassPath), driver);
		WebElement element = driver.findElement(By.xpath(roomClassPath));
		Utility.ScrollToElement_NoWait(element, driver);
		try {
			element.click();
		} catch (Exception e) {

			driver.findElements(By.xpath("//span[text()='" + roomClass + "']")).get(1).click();

		}
		element.click();
		testSteps.add("Selected room class: " + roomClass);

		return testSteps;

	}

	public ArrayList<String> verifyRatePerInterval(WebDriver driver, String roomClassName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		logger.info(enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);

		assertEquals(element.get(0).isDisplayed(), true, "Failed: rate per interval input field is not displaying");
		testSteps.add("Verified rate per interval inptu field is displaying for room class: " + roomClassName);
		return testSteps;
	}

	public ArrayList<String> getCurrency(WebDriver driver, String roomClassName, boolean isAddtionalCharges)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String currencypath = "(//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//span[contains(@class,'inrd-input-currency')]//span)";
		// Wait.WaitForElement(driver, enterRate);
		// Wait.waitForElementToBeVisibile(By.xpath(currencypath), driver);
		// Wait.waitForElementToBeClickable(By.xpath(currencypath), driver);
		logger.info("currency path: " + currencypath);
		List<WebElement> element = driver.findElements(By.xpath(currencypath));
		Utility.ScrollToElement_NoWait(element.get(0), driver);
		testSteps.add(element.get(2).getText());
		logger.info("for 2: " + element.get(2).getText());
		if (isAddtionalCharges) {
			testSteps.add(element.get(5).getText());
			logger.info("for 5: " + element.get(5).getText());

			testSteps.add(element.get(8).getText());
			logger.info("for 8: " + element.get(5).getText());

		}

		return testSteps;
	}

	public void clickOnCLoseRoomClassesExpandList(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElementUsingClassName(driver, RateGridPage.CloseExpandRoomClassesList);
		Wait.waitForElementToBeVisibile(By.className(RateGridPage.CloseExpandRoomClassesList), driver);
		Wait.waitForElementToBeClickable(By.className(RateGridPage.CloseExpandRoomClassesList), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.CloseExpandRoomClassesList, driver);
		ratesGrid.CloseExpandRoomClassesList.click();

	}

	public ArrayList<String> getRoomClassRateWithAdditionalAdultsAndChild(WebDriver driver, String roomClassName,
			boolean isAdditionCharges) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		logger.info(enterRate);
		// Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);
		testSteps.add(element.get(0).getAttribute("value"));
		if (isAdditionCharges) {
			testSteps.add(element.get(3).getAttribute("value"));
			testSteps.add(element.get(4).getAttribute("value"));

		}

		return testSteps;
	}

	public void clearInputField(WebDriver driver, WebElement element, String characterLenght) {

		element.clear();
		for (int i = 0; i < characterLenght.length(); i++) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	public void expandRoomClassWithoutMinus(WebDriver driver, ArrayList<String> testSteps, String roomClassName)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[@class='DatesTable']//div[contains(text(),'" + roomClassName + "')]/parent::div/span";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		try {
			element.click();
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-400)");
			element.click();
		}

	}

	public void expandChannelWithoutMinus(WebDriver driver, ArrayList<String> testSteps, String roomClassName,
			String channelName) throws InterruptedException {
		String path = "//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']/parent::div/span";
		// Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver, 5);
		Utility.clickThroughJavaScript(driver, element);
		// element.click();
		String minusPath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName
				+ "']/parent::div/span[contains(@class,'ir-minus')]";

	}

	public void clickMinusChannel(WebDriver driver, String roomClassName, String channelName)
			throws InterruptedException {
		String minusPath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName
				+ "']/parent::div/span[contains(@class,'ir-minus')]";
		boolean collapseExist = driver.findElement(By.xpath(minusPath)).isDisplayed();
		if (collapseExist) {

			WebElement minus = driver.findElement(By.xpath(minusPath));
			Utility.ScrollToElement(minus, driver);
			assertTrue(minus.isDisplayed(), "Failed to verify collapse icon");

			try {
				minus.click();
			} catch (Exception e) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,-400)");
				minus.click();
			}

		}
	}

	public void clickMinusroomClass(WebDriver driver, String roomClassName) throws InterruptedException {
		String minusPath = "//div[@class='DatesTable']//div[contains(text(),'" + roomClassName
				+ "')]/parent::div/span[contains(@class,'ir-minus')]";
		boolean collapseExist = driver.findElement(By.xpath(minusPath)).isDisplayed();
		if (collapseExist) {

			WebElement minus = driver.findElement(By.xpath(minusPath));
			Utility.ScrollToElement(minus, driver);
			assertTrue(minus.isDisplayed(), "Failed to verify collapse icon");

			try {
				minus.click();
			} catch (Exception e) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,-400)");
				minus.click();
			}

		}

	}

	public ArrayList<String> bulkUpdateRateGridSymbolVerification(WebDriver driver, String clientSymbol)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<String>();
		String symbol = "//span[text()='" + clientSymbol + "']";
		Wait.WaitForElement(driver, symbol);
		List<WebElement> symbolList = driver.findElements(By.xpath(symbol));
		for (int i = 0; i < symbolList.size(); i++) {

			String symbolFound = symbolList.get(i).getText();
			assertEquals(symbolFound, clientSymbol, "Failed: Symbol didn't match");
			logger.info("Symbol Verified:" + symbolFound);

		}
		testSteps.add("Symbol Verified:" + clientSymbol);
		return testSteps;

	}

	// Verification of As per Update/aqsa

	public ArrayList<String> verifyRateUpdate(WebDriver driver, int numberofDays, String nightlyRate,
			String updateRatesType, String rateCurrencyType, String rateChangeValue,
			ArrayList<String> beforeUpdateRateValues, ArrayList<String> afterUpdateRateValues) {
		ArrayList<String> testSteps = new ArrayList<String>();

		int rateExpected = 0;

		for (int i = 0; i <= numberofDays; i++) {

			logger.info("Nighlty Rate Plan Expected In RatesGrid:" + nightlyRate);
			testSteps.add("Nighlty Rate Plan Expected In RatesGrid:" + nightlyRate);
			String rateFound = afterUpdateRateValues.get(i);
			logger.info("Nighlty Rate Plan Found In RatesGrid:" + rateFound);
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

					logger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
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
					logger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
					testSteps.add("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);

					assertEquals(rateFound.equals(Integer.toString(rateExpected)), true,
							"Failed: Nightly Rate didn't update");

				}
			}

			else if (updateRatesType.equalsIgnoreCase("Decrease")) {

				if (rateCurrencyType.equals(Utility.clientCurrency)) {

					// Parse and the subtract the
					// rateChangeValue

					rateExpected = Integer.parseInt(beforeUpdateRateValues.get(i)) - Integer.parseInt(rateChangeValue);

					logger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
					testSteps.add(
							"Nighlty Rate Plan Expected In Reservation Find Rooms:" + beforeUpdateRateValues.get(i));
					// Need to remove dollar sign here
					logger.info("Nighlty Rate Plan Expected In Reservation Find Rooms:" + rateExpected);
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
			}

			else {
				assertEquals(afterUpdateRateValues.get(i).equals(beforeUpdateRateValues.get(i)), true,
						"Failed: Nightly Rate didn't update");
			}
			logger.info("Verified");
			testSteps.add("Verified");

		}
		return testSteps;

	}

	/*
	 * 
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
			ArrayList<String> testSteps, String description, String channels, String roomClasses,
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
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday)
			throws InterruptedException, ParseException {

		NightlyRate nightlyRate = new NightlyRate();
		clickRateGridAddRatePlan(driver);
		clickRateGridAddRatePlanOption(driver, planType);
		ratePlanName = ratePlanName + Utility.generateRandomString();
		folioDisplayName = folioDisplayName + Utility.generateRandomString();
		nightlyRate.enterRatePlanName(driver, ratePlanName, testSteps);
		nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
		nightlyRate.enterRatePlanDescription(driver, description, testSteps);
		testSteps.addAll(nightlyRate.clickNextButton(driver));
		nightlyRate.selectChannels(driver, channels, true, testSteps);
		testSteps.addAll(nightlyRate.clickNextButton(driver));
		nightlyRate.selectRoomClasses(driver, roomClasses, true, testSteps);
		testSteps.addAll(nightlyRate.clickNextButton(driver));
		nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
				Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
				Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
				withInDaysCount, promoCode, testSteps);
		testSteps.addAll(nightlyRate.clickNextButton(driver));
		testSteps.addAll(nightlyRate.clickNextButton(driver));
		nightlyRate.clickCreateSeason(driver, testSteps);
		nightlyRate.selectSeasonDates(driver, testSteps, seasonStartDate, seasonEndDate);
		nightlyRate.enterSeasonName(driver, testSteps, seasonName);
		nightlyRate.selectSeasonDays(driver, testSteps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
				isSaturDay, isSunDay);
		nightlyRate.clickCreateSeason(driver, testSteps);
		nightlyRate.selectSeasonColor(driver, testSteps);
		nightlyRate.selectAdditionalChargesForChildrenAdults(driver, testSteps, isAdditionalChargesForChildrenAdults);
		nightlyRate.enterRate(driver, testSteps, ratePerNight, isAdditionalChargesForChildrenAdults, maxAdults,
				maxPersons, additionalAdultsPerNight, additionalChildPerNight);
		nightlyRate.addExtraRoomClassInSeason(driver, testSteps, isAddRoomClassInSeason, extraRoomClassesInSeason,
				isAdditionalChargesForChildrenAdults, ratePerNight, extraRoomClassRatePerNight, extraRoomClassMaxAdults,
				extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
				extraRoomClassAdditionalChildPerNight);

		nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);
		nightlyRate.clickAssignRulesByRoomClass(driver, testSteps, isAssignRulesByRoomClass);
		nightlyRate.enterSeasonLevelRules(driver, testSteps, isAssignRulesByRoomClass, seasonRuleSpecificRoomClasses,
				seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday,
				isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday,
				isSeasonRuleOnSunday);
		nightlyRate.clickSaveSason(driver, testSteps);
		nightlyRate.clickCompleteChanges(driver, testSteps);
		nightlyRate.clickSaveAsActive(driver, testSteps);
		Utility.rateplanName = ratePlanName;

	}

	public void removeRuleForMinStay(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn, int index) throws InterruptedException, ParseException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "(//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/parent::div//input[not(@value='')])[" + index + "]";

		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		testSteps.add("Click on Min Stay Box");
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.sendKeys(Keys.BACK_SPACE);
		testSteps.add("Clear Min Stay ");
		element.sendKeys(Keys.TAB);
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isEnabled(), "Failed to Verify Rule Saved Message");
		testSteps.add("Removed Rule for <b>" + ruleAppliedOn + "</b> Message : <b> Rule saved successfully </b>");
		logger.info("Removed Rule for " + ruleAppliedOn + "Message :  Rule saved successfully");
		String pathOne = "(//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName + "']"
				+ "/ancestor::div//div[text()='Min Stay']/parent::div//input[@value=''])[" + index + "]";
		WebElement elementOne = driver.findElement(By.xpath(pathOne));
		Utility.ScrollToElement(elementOne, driver);
		assertEquals(elementOne.getAttribute("value"), "", "Failed to Verify Removed Min Stay");
	}

	public void removeRuleForNoCheckInAndOut(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn, int index) throws InterruptedException {
		Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "(//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/parent::div//div[@class='rt-onHover  enabled'])[" + index + "]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.hoverOnElement(driver, element);
		element.click();
		Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
		assertTrue(elements.rateGridRuleSavedMessage.isEnabled(), "Failed to Verify Rule Saved Message");
		testSteps.add("Removed Rule for <b>" + ruleAppliedOn + " </b> Message : <b> Rule saved successfully </b>");
		logger.info("Removed Rule for " + ruleAppliedOn + " Message :  Rule saved successfully");

	}

	public void verifyRulesRemovedForClass(WebDriver driver, ArrayList<String> testSteps, String roomClass, int index) {
		/*
		 * String path = "(//div[contains(text(),'" + roomClass +
		 * "')]/parent::div/following-sibling::div/div[@class='RuleIndication']/span)["
		 * + index + "]";
		 */

		String path = "(//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[not(@class='RuleIndication')])[" + index + "]";
		Wait.WaitForElement(driver, path);
		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		assertEquals(isExist, true, "Failed to verify removed rule for room class");
		testSteps.add("Verify Rules Removed for  Class  <b>" + roomClass + "</b>");
		logger.info("Verify Rules Removed for  Class  " + roomClass);

	}

	public void verifyRulesRemovedForChannel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, int index) {
		/*
		 * String path = "(//div[contains(text(),'" + roomClass +
		 * "')]/ancestor::div/..//following-sibling::div//div[@title='" + channelName +
		 * "']/parent::div/following-sibling::div/div[@class='RuleIndication']/span)[" +
		 * index + "]";
		 */

		String path = "(//div[contains(text(),'" + roomClass
				+ "')]/ancestor::div/..//following-sibling::div//div[@title='" + channelName
				+ "']/parent::div/following-sibling::div/div[not(@class='RuleIndication')])[" + index + "]";

		boolean isExist = Utility.isElementPresent(driver, By.xpath(path));
		assertEquals(isExist, true, "Failed to verify removed rule for channel");
		testSteps.add("Verify Rules Removed for  Channel  <b>" + roomClass + " -- " + channelName + "</b>");
		logger.info("Verify Rules Removed for  Channel  <" + roomClass + " -- " + channelName);
	}

	public void updateRuleForMinStayForBlankRate(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOn, String newNight) throws InterruptedException, ParseException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div//following-sibling::div/div[contains(@class,'RegularRate')]";
		String pathOne = "//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//input";
		Wait.WaitForElement(driver, path);
		List<WebElement> elementsOne = driver.findElements(By.xpath(path));
		List<WebElement> elementsTwo = driver.findElements(By.xpath(pathOne));
		for (int i = 0; i < elementsOne.size(); i++) {

			if (elementsOne.get(i).getText().equals("--") && elementsTwo.get(i).getAttribute("value").equals("")) {
				Utility.ScrollToViewElementINMiddle(driver, elementsOne.get(i));
				elementsTwo.get(i).click();
				testSteps.add("Click on Min Stay Box");
				elementsTwo.get(i).sendKeys(newNight);
				testSteps.add("Input Min Stay : <b>" + newNight + "</b>");
				elementsTwo.get(i).sendKeys(Keys.TAB);
				Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
				assertTrue(elements.rateGridRuleSavedMessage.isEnabled(), "Failed to Verify Rule Saved Message");
				testSteps
						.add("Update Rule for <b>" + ruleAppliedOn + "</b> Message : <b> Rule saved successfully </b>");

				logger.info("Update Rule for " + ruleAppliedOn + "Message :  Rule saved successfully");
				String pathTwo = "//div[contains(@title,'" + roomClass + "')]"
						+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
						+ "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//input[@value='" + newNight
						+ "']";
				WebElement elementOne = driver.findElement(By.xpath(pathTwo));
				Utility.ScrollToElement(elementOne, driver);
				assertEquals(elementOne.getAttribute("value"), newNight, "Failed to Verify Updated Min Stay");
				break;
			}

		}

	}

	public void updateRuleForNoCheckInAndOutForBlankRate(WebDriver driver, ArrayList<String> testSteps,
			String roomClass, String channelName, String ruleAppliedOn) throws InterruptedException {
		Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div//following-sibling::div/div[contains(@class,'RegularRate')]";
		String pathOne = "//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div//div[text()='" + ruleAppliedOn + "']/parent::div//div[contains(@class,'enabled')]";
		Wait.WaitForElement(driver, path);
		List<WebElement> elementsOne = driver.findElements(By.xpath(path));
		List<WebElement> elementsTwo = driver.findElements(By.xpath(pathOne));
		for (int i = 0; i < elementsOne.size(); i++) {
			if (elementsOne.get(i).getText().equals("--") && !elementsTwo.get(i).getText().equals("?")) {
				Utility.ScrollToViewElementINMiddle(driver, elementsTwo.get(i));
				Utility.hoverOnElement(driver, elementsTwo.get(i));
				elementsTwo.get(i).click();
				Wait.WaitForElement(driver, OR_RateGrid.rateGridRuleSavedMessage);
				assertTrue(elements.rateGridRuleSavedMessage.isEnabled(), "Failed to Verify Rule Saved Message");
				testSteps.add(
						"Update Rule for <b>" + ruleAppliedOn + " </b> Message : <b> Rule saved successfully </b>");
				logger.info("Update Rule for " + ruleAppliedOn + " Message :  Rule saved successfully");
				break;
			}
		}

	}

	public void hoverBestAvailableRateForBlankRate(WebDriver driver, ArrayList<String> testSteps, int index)
			throws InterruptedException {
		Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.blankBestAvailableRates);
		Utility.ScrollToViewElementINMiddle(driver, elements.blankBestAvailableRates.get(index));
		Utility.hoverOnElement(driver, elements.blankBestAvailableRates.get(index));
		testSteps.add("Hover Best Available Rates which has No Rate");
		logger.info("Hover Best Available Rates which has No Rate");
	}

	public void hoverBestAvailableRatesOfOverRide(WebDriver driver, ArrayList<String> testSteps, int index)
			throws InterruptedException {
		Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.OverRideBestAvailableRates);
		Utility.ScrollToViewElementINMiddle(driver, elements.OverRideBestAvailableRates.get(index));
		Utility.hoverOnElement(driver, elements.OverRideBestAvailableRates.get(index));
		testSteps.add("Hover Best Available Rates Of OverRide Rate");
		logger.info("Hover Best Available Rates Of OverRide Rate");
	}

	public void verifyOverrideRateColorAtBestVailableRates(WebDriver driver, ArrayList<String> testSteps, String rate,
			String colorName) throws InterruptedException {
		Wait.wait10Second();
		String path = "//div[contains(@class,'has-changes RegularRate')and contains(text(),'" + rate + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		colorName1 = colorName1.substring(0, colorName1.length() - 3);
		java.awt.Color color1 = java.awt.Color.decode(colorName1);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		String colorNameIs = util.getColorNameFromRgb(r, g, b);
		testSteps.add("Verified OverRide Rate Color At Best Available Rates Level --<b>" + colorNameIs + " </b>");
		logger.info("Verified OverRide Rate Color At Best Available Rates Level --" + colorNameIs);
	}

	public void verifyRegularRateColorAtBestVailableRates(WebDriver driver, ArrayList<String> testSteps, String rate,
			String colorName) throws InterruptedException {
		Wait.wait10Second();
		String path = "//div[contains(@class,'RegularRate')and contains(text(),'" + rate + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		String color = element.getCssValue("color");
		String colorName1 = Color.fromString(color).asHex();
		logger.info(colorName1);
		assertEquals(colorName1.toLowerCase().trim(), colorName.toLowerCase().trim(), "Failed to verify Color");
		colorName1 = colorName1.substring(0, colorName1.length() - 3);
		java.awt.Color color1 = java.awt.Color.decode(colorName1);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		String colorNameIs = util.getColorNameFromRgb(r, g, b);
		testSteps.add("Verified OverRide Rate Color At Best Available Rates Level --<b>" + colorNameIs + " </b>");
		logger.info("Verified OverRide Rate Color At Best Available Rates Level --" + colorNameIs);
	}

	public ArrayList<String> updateRateForChannel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String rate, int index, int increaseDecreaseBy, String action)
			throws InterruptedException {
		ArrayList<String> ratesAre = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		List<WebElement> webElements = null;
		String oldRate = null, newRate = null;
		testSteps.add("========Update Rate for Channel<b> " + roomClass + " -- " + channelName + "</b>========");
		Wait.WaitForElement(driver, path);
		webElements = driver.findElements(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, webElements.get(index));
		Utility.clickThroughJavaScript(driver, webElements.get(index));
		oldRate = getRoomRate(driver);
		ratesAre.add(oldRate);
		if (action.equals("Increase")) {
			if (oldRate.contains(".")) {
				Double doubleValue = Double.parseDouble(oldRate) + increaseDecreaseBy;
				newRate = String.valueOf(doubleValue);
				ratesAre.add(newRate);
			} else {
				int newRates = Integer.parseInt(oldRate) + increaseDecreaseBy;
				newRate = String.valueOf(newRates);
				ratesAre.add(newRate);
			}
		} else if (action.equals("Decrease")) {
			if (oldRate.contains(".")) {
				Double doubleValue = Double.parseDouble(oldRate) - increaseDecreaseBy;
				newRate = String.valueOf(doubleValue);
				ratesAre.add(newRate);
			} else {
				int newRates = Integer.parseInt(oldRate) - increaseDecreaseBy;
				newRate = String.valueOf(newRates);
				ratesAre.add(newRate);
			}
		}
		elements.rateGridRoomRate.clear();
		elements.rateGridRoomRate.sendKeys(newRate);
		testSteps.add("Enter New Rate: <b>" + newRate + "</b>");
		logger.info("Enter New Rate: " + newRate);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
		assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");
		String pathNewRate = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + newRate + "']";
		Wait.WaitForElement(driver, pathNewRate);
		WebElement elementOne = driver.findElement(By.xpath(pathNewRate));
		Utility.ScrollToElement(elementOne, driver);
		assertTrue(elementOne.isDisplayed(), "Failed to Verify New Rate");
		testSteps.add("Verified Override Rate at Channel Level : <b> " + roomClass + " -- " + channelName + " -- "
				+ newRate + " </b> And Old Rate is: " + oldRate + "</b>");
		logger.info("Verified Override Rate  at Channel Level :" + roomClass + " -- " + channelName + " -- " + newRate
				+ " And Old Rate is: " + oldRate);
		return ratesAre;
	}

	public void verifyCancelToUpdateRateAtClassLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String rate, int index) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		List<WebElement> webElements = driver.findElements(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, webElements.get(index));
		Utility.clickThroughJavaScript(driver, webElements.get(index));
		testSteps.add("Click on Room Class Rate: <b> " + roomClass + " -- " + rate + " </b>");
		logger.info("Click on Room Class Rate" + roomClass + " -- " + rate);
		elements.rateCrossIcon.click();
		testSteps.add("Click on Cross Icon");
		logger.info("Click on Cross Icon");
		Utility.ScrollToElement(webElements.get(index), driver);
		assertTrue(webElements.get(index).isDisplayed(), "Failed to Verify Rate");
		testSteps.add(
				"Verified Rate not OverRide For Class : <b> " + roomClass + " </b> Still Rate is: " + rate + "</b>");
		logger.info("Verified Rate not OverRide For Class :" + roomClass + " Still Rate is: " + rate);
	}

	public void verifyOverideRateAtClasslevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String rate) {
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'has-changes')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertEquals(element.getText().trim(), rate.trim(), "Failed to verify rate at class level");
		testSteps.add("Verified Override at Class Level: <b>" + roomClass + " </b> -- <b>" + rate + "</b>");
		logger.info("Verified Override at Class Level: " + roomClass + "--" + rate);

	}

	public void verifyRegularRateAtChannelLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String rate) {
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertEquals(element.getText().trim(), rate.trim(), "Failed to verify rate at channel level");
		testSteps.add("Verified Regular Rate  at Channel Level: <b>" + roomClass + "</b> -- <b>" + channelName
				+ " </b> -- <b>" + rate + "</b>");
		logger.info("Verified Regular Rate at Channel Level: " + roomClass + "--" + channelName + "--" + rate);

	}

	public void verifyRedTrainingAtClassLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass) {
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div//span[@class='RateDifferenceIndication']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed to Verify Red Traingle");
		testSteps.add("Red Triangle Displayed For Class : <b> " + roomClass + "</b>");
		logger.info("Red Triangle Displayed For Class :" + roomClass);
	}

	public void verifySourceHaveDifferentPrice(WebDriver driver, ArrayList<String> testSteps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.differentSource);
		assertTrue(elements.differentSource.isDisplayed(), "Failed to Verify source have different source");
		testSteps.add("Verified Source have Different Prices");
		logger.info("Verified Source have Different Prices");
	}

	public void verifyOverrideRateAtBestVailableRates(WebDriver driver, ArrayList<String> testSteps, String rate)
			throws InterruptedException {
		String path = "//div[contains(text(),'Best Available Rates')]/parent::div/following-sibling::div/div[contains(@class,'has-changes')and contains(text(),'"
				+ rate + "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertEquals(element.getText().trim(), rate.trim(), "Failed to verify Best Available Override Rate");
		testSteps.add("Verified Best Avaiable Rate --<b>" + rate + " </b>");
		logger.info("Verified  Best Available Rates  --" + rate);
	}

	public ArrayList<String> updateExtraAdultOfOverRideForChannel(WebDriver driver, ArrayList<String> testSteps,
			String roomClass, String channelName, String rate, int increaseDecreaseBy, String action)
			throws InterruptedException {
		String oldAdult = null, newAdult = null;
		ArrayList<String> adultsAre = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.clickThroughJavaScript(driver, element);
		oldAdult = getExtraAdult(driver);
		adultsAre.add(oldAdult);
		if (action.equals("Increase")) {
			if (oldAdult.contains(".")) {
				Double doubleValue = Double.parseDouble(oldAdult) + increaseDecreaseBy;
				newAdult = String.valueOf(doubleValue);
				adultsAre.add(newAdult);
			} else {
				int newRates = Integer.parseInt(oldAdult) + increaseDecreaseBy;
				newAdult = String.valueOf(newRates);
				adultsAre.add(newAdult);
			}
		} else if (action.equals("Decrease")) {
			if (oldAdult.contains(".")) {
				Double doubleValue = Double.parseDouble(oldAdult) - increaseDecreaseBy;
				newAdult = String.valueOf(doubleValue);
				adultsAre.add(newAdult);
			} else {
				int newRates = Integer.parseInt(oldAdult) - increaseDecreaseBy;
				newAdult = String.valueOf(newRates);
				adultsAre.add(newAdult);
			}
		}
		elements.rateGridExtraAd.clear();
		elements.rateGridExtraAd.sendKeys(newAdult);
		testSteps.add("Enter New Adult: <b>" + newAdult + "</b>");
		logger.info("Enter New Adult: " + newAdult);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
		assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");
		return adultsAre;
	}

	public void verifyImageRemovedAtTooltip(WebDriver driver, ArrayList<String> testSteps, String label)
			throws InterruptedException {
		String path = "//span[contains(text(),'" + label + "')]/preceding-sibling::img[contains(@src,'')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertEquals(element.getAttribute("src"), "", "Failed to verify Image Removed");
		testSteps.add("Verified  Image Hide for  --<b>" + label + " </b>");
		logger.info("Verified  Image Hide for   --" + label);
	}

	public void verifyRegularRateAtClassLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String rate) throws InterruptedException {
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertEquals(element.getText().trim(), rate.trim(), "Failed to verify rate at channel level");
		testSteps.add("Verified Regular Rate  at Class Level: <b>" + roomClass + "</b> -- <b>" + rate + "</b>");
		logger.info("Verified Regular Rate at Class Level: " + roomClass + "--" + rate);

	}

	public void verifyDefaultCurrenyAtToolTip(WebDriver driver, ArrayList<String> testSteps, String currencySymbol)
			throws InterruptedException {
		String path = "//h3//span[@class='popover-rate-date']"
				+ "/following-sibling::span[normalize-space(contains(@text,'" + currencySymbol + "'))]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed to verify Default Curency");
		testSteps.add("Verified Default Curency: <b>" + currencySymbol + "</b>");
		logger.info("Verified Default Curency: " + currencySymbol);

	}

	public String getColorOfSquareBox(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String colorName = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.squarBox);
		Utility.ScrollToViewElementINMiddle(driver, elements.squarBox);
		Utility.hoverOnElement(driver, elements.squarBox);
		colorName = elements.squarBox.getCssValue("background-color");
		colorName = Color.fromString(colorName).asHex();
		logger.info(colorName);
		java.awt.Color color1 = java.awt.Color.decode(colorName);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		colorName = util.getColorNameFromRgb(r, g, b);
		testSteps.add("Verified Square  Box Color After Hover--<b>" + colorName + "</b>");
		logger.info("Verified Square  Box Color After Hover--<b>" + colorName);
		return colorName;
	}

	public String getColorOfAvailabilityBox(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		String colorName = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.availabilityBoxColor);
		Utility.ScrollToViewElementINMiddle(driver, elements.availabilityBoxColor);
		Utility.hoverOnElement(driver, elements.availabilityBoxColor);
		colorName = elements.availabilityBoxColor.getCssValue("color");
		colorName = Color.fromString(colorName).asHex();
		logger.info(colorName);
		java.awt.Color color1 = java.awt.Color.decode(colorName);
		int r = color1.getRed();
		int g = color1.getGreen();
		int b = color1.getBlue();
		Utility util = new Utility();
		colorName = util.getColorNameFromRgb(r, g, b);
		testSteps.add("Verified Availability  Box Color After Hover--<b>" + colorName + "</b>");
		logger.info("Verified Availability  Box Color After Hover--<b>" + colorName);
		return colorName;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <updateExtraAdultsAndExtraChildBoth> ' Description: <Update
	 * Extra Adult and Extra Child at both at same time > ' Input parameters:
	 * <WebDriver driver, ArrayList<String> testSteps, String roomClass, String
	 * rate, String adults, String child> ' Return value: <NA> ' Created By:
	 * <Gangotri Sikheria> ' ' Created On: <MM/dd/yyyy> <07/31/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public void updateExtraAdultsAndExtraChildAtClassLevel(WebDriver driver, ArrayList<String> testSteps,
			String roomClass, String rate, String adults, String child) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		elements.rateGridExtraAd.clear();
		elements.rateGridExtraAd.sendKeys(adults);
		testSteps.add("Enter Extra Adults is: <b>" + adults + "</b>");
		logger.info("Enter Extra Adults is: " + adults);
		elements.rateGridExtraCh.clear();
		elements.rateGridExtraCh.sendKeys(child);
		testSteps.add("Enter Extra Child is: <b>" + child + "</b>");
		logger.info("Enter Extra Child is: " + child);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.waitTillElementDisplayed(driver, OR_RateGrid.rateSavedMessage);
		assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");

	}

	public void verifyRoomRatePoupDate(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,
			String date) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass
				+ "')]/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		String pathOne = "//h3";
		Wait.WaitForElement(driver, pathOne);
		WebElement elementOne = driver.findElement(By.xpath(pathOne));
		assertEquals(elementOne.getText(), date, "Failed to verify Date");
		testSteps.add("Verified Popup Date is  : <b> " + elementOne.getText() + "</b>");
		logger.info("Verified  Popup Date is  :  " + elementOne.getText());
		elements.rateCrossIcon.click();
		logger.info("Click on Cross Icon");

	}

	public void verifyExAAndExChAtChannelLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String rate, String adult, String child) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'Override')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.clickThroughJavaScript(driver, element);
		String exAdult = getExtraAdult(driver);
		String exChild = getExtraChild(driver);
		assertEquals(exAdult, adult, "Failed to Extra Adult ");
		assertEquals(exChild, child, "Failed to Extra Child ");
		testSteps.add("Room Class Is: <b>" + roomClass + " </b>Channel Is: <b> " + channelName + " </b>Adult Is-- <b>"
				+ exAdult + "</b> AND Child Is --<b>" + exChild + "</b>");
		logger.info("Verified Extra Adult and Extra Child at Channel Level   :  Adult Is-- " + exAdult
				+ "AND Child Is-- " + exChild);
		elements.rateCrossIcon.click();
		logger.info("Click on Cross Icon");
	}

	public void verifyExAAndExChAtChannelLevelAfterRemovedOverRide(WebDriver driver, ArrayList<String> testSteps,
			String roomClass, String channelName, String rate, String adult, String child) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.clickThroughJavaScript(driver, element);
		String exAdult = getExtraAdult(driver);
		String exChild = getExtraChild(driver);
		assertEquals(exAdult, adult, "Failed to Extra Adult ");
		assertEquals(exChild, child, "Failed to Extra Child ");

		if (exAdult.isEmpty() && exChild.isEmpty()) {
			String exCh = "Extra Child Removed";
			String exAd = "Extra Adult Removed";
			testSteps.add("Room Class Is: <b>" + roomClass + " </b>Channel Is: <b> " + channelName
					+ " </b>Adult Is-- <b>" + exAd + "</b> AND Child Is --<b>" + exCh + "</b>");

		} else {
			testSteps.add("Room Class Is: <b>" + roomClass + " </b>Channel Is: <b> " + channelName
					+ " </b>Adult Is-- <b>" + exAdult + "</b> AND Child Is --<b>" + exChild + "</b>");

		}
		logger.info("Verified Extra Adult and Extra Child at Channel Level   :  Adult Is" + exAdult + "AND Child Is"
				+ exChild);
		elements.rateCrossIcon.click();
		logger.info("Click on Cross Icon");
	}

	public void updateExtraAdultsAndExtraChildAtChannelLevel(WebDriver driver, ArrayList<String> testSteps,
			String roomClass, String channelName, String rate, String adults, String child)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		elements.rateGridExtraAd.clear();
		elements.rateGridExtraAd.sendKeys(adults);
		testSteps.add("Enter Extra Adults is: <b>" + adults + "</b>");
		logger.info("Enter Extra Adults is: " + adults);
		elements.rateGridExtraCh.clear();
		elements.rateGridExtraCh.sendKeys(child);
		testSteps.add("Enter Extra Child is: <b>" + child + "</b>");
		logger.info("Enter Extra Child is: " + child);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
		assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");

	}

	public void verifyExAAndExChAtClassLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String rate, String adult, String child) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/parent::div/following-sibling::div/div[contains(@class,'has-changes')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.clickThroughJavaScript(driver, element);
		String exAdult = getExtraAdult(driver);
		String exChild = getExtraChild(driver);
		assertEquals(exAdult, adult, "Failed to Extra Adult ");
		assertEquals(exChild, child, "Failed to Extra Child ");
		testSteps.add("Room Class Is: <b>" + roomClass + " </b>Adult Is <b>" + exAdult + "</b> AND Child Is <b>"
				+ exChild + "</b>");
		logger.info("Verified Extra Adult and Extra Child at Channel Level   :  Adult Is" + exAdult + "AND Child Is"
				+ exChild);
		elements.rateCrossIcon.click();
		logger.info("Click on Cross Icon");
	}

	public void verifyExAAndExChAtClassLevelAfterRemovedOverRide(WebDriver driver, ArrayList<String> testSteps,
			String roomClass, String rate, String adult, String child) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		Utility.clickThroughJavaScript(driver, element);
		String exAdult = getExtraAdult(driver);
		String exChild = getExtraChild(driver);
		assertEquals(exAdult, adult, "Failed to Extra Adult ");
		assertEquals(exChild, child, "Failed to Extra Child ");

		if (exAdult.isEmpty() && exChild.isEmpty()) {
			String exAd = "Removed Extra Adult";
			String exCh = "Removed Extra Child";
			testSteps.add("Room Class Is: <b>" + roomClass + " </b>Adult Is <b>" + exAd + "</b> AND Child Is <b>" + exCh
					+ "</b>");

		} else {
			testSteps.add("Room Class Is: <b>" + roomClass + " </b>Adult Is <b>" + exAdult + "</b> AND Child Is <b>"
					+ exChild + "</b>");

		}
		logger.info("Verified Extra Adult and Extra Child at Class Level   :  Adult Is" + exAdult + "AND Child Is"
				+ exChild);
		elements.rateCrossIcon.click();
		logger.info("Click on Cross Icon");
	}

	public void updateExtraAdultsAndExtraChildAtChannelLevelForOverrideRate(WebDriver driver,
			ArrayList<String> testSteps, String roomClass, String channelName, String rate, String adults, String child)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'has-changes')and text()='" + rate + "']";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		elements.rateGridExtraAd.clear();
		elements.rateGridExtraAd.sendKeys(adults);
		testSteps.add("Enter Extra Adults is: <b>" + adults + "</b>");
		logger.info("Enter Extra Adults is: " + adults);
		elements.rateGridExtraCh.clear();
		elements.rateGridExtraCh.sendKeys(child);
		testSteps.add("Enter Extra Child is: <b>" + child + "</b>");
		logger.info("Enter Extra Child is: " + child);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
		assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");

	}

	// extracting rate only from the HashMap(rate parameter) which having rate as
	// value and date as Key
	public ArrayList<String> getRate(HashMap<String, String> rate, WebDriver driver, String checkin, String checkout,
			ArrayList<String> test_steps) {
		ArrayList<String> rates = new ArrayList<String>();
		ArrayList<String> dates = new ArrayList<String>();
		String checkinDate = Utility.parseDate(checkin, "dd/MM/yyyy", "MM/dd/yyyy");
		String checkoutDate = Utility.parseDate(checkout, "dd/MM/yyyy", "MM/dd/yyyy");

		Date date1 = new Date(checkinDate);

		Date date2 = new Date(checkoutDate);
		List<Date> diff = Utility.getDateRangeBetweenfromAndToDate(date1, date2);
		DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < diff.size() - 1; i++) {
			String findDate = outputFormatter.format(diff.get(i));
			dates.add(findDate);
			String getRate = rate.get(findDate);
			rates.add(getRate);
			test_steps.add("Rate is: " + getRate + " on: " + findDate);
			logger.info("Rate is: " + getRate + " on: " + findDate);

		}

		return rates;

	}

	public void clickRateDropDown(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		try {
			Wait.WaitForElement(driver, RateGridPage.OpenRateDropdown);
//			Wait.waitForElementToBeInVisibile(By.xpath(RateGridPage.OpenRateDropdown), driver);
//			Wait.waitForElementToBeClickable(By.xpath(RateGridPage.OpenRateDropdown), driver);
			Utility.ScrollToElement(driver.findElement(By.id("rates-and-availability-tabs-tab-AVAILABILITY_VIEW")),
					driver);
			elements.OpenRateDropdown.click();
		} catch (Exception e) {
			driver.navigate().refresh();
			Wait.WaitForElement(driver, RateGridPage.OpenRateDropdown);
//			Wait.waitForElementToBeInVisibile(By.xpath(RateGridPage.OpenRateDropdown), driver);
//			Wait.waitForElementToBeClickable(By.xpath(RateGridPage.OpenRateDropdown), driver);
			elements.OpenRateDropdown.click();

		}

		test_steps.add("Open rate grid dropdown");

	}

	public boolean selectRatePlan(WebDriver driver, String ratePlanName, ArrayList<String> test_steps)
			throws InterruptedException {
		boolean flag;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		clickRateDropDown(driver, test_steps);
		Wait.waitUntilPresenceOfElementLocated(RateGridPage.SearchRatPlan, driver);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SearchRatPlan), driver);

		elements.searchRatPlan.sendKeys(ratePlanName);
		test_steps.add("Search rate Plan: " + ratePlanName);

		Wait.wait1Second();
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SearchRatPlan), driver);

		elements.selectRatePlan.click();
		test_steps.add("Select rate plan: " + ratePlanName);
		flag = true;

		try {
			if (driver.findElements(By.xpath("//*[@id='ratePlans']//span[contains(@class,'ir-plus')]")).size() > 0) {
				Utility.clickThroughJavaScript(driver,
						driver.findElement(By.xpath("//*[@id='ratePlans']//span[contains(@class,'ir-plus')]")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return flag;
	}

	public boolean searchForRatePlanExistingOrNot(WebDriver driver, ArrayList<String> test_steps, String ratePlanName)
			throws Exception {
		boolean ratePlanExists = false;
		ArrayList<String> allRatePlans = new ArrayList<>();
		try {
			clickRatePlanArrow(driver, test_steps);
			allRatePlans = getRatePlanNames(driver);
			for (String string : allRatePlans) {
				if (string.equalsIgnoreCase(ratePlanName)) {
					ratePlanExists = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.info(e);
		}
		return ratePlanExists;
	}

	public void verifySelectedRatePlan(WebDriver driver, String ratePlan, ArrayList<String> test_steps)
			throws Exception {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitUntilPresenceOfElementLocated(RateGridPage.SelectedRatePlan, driver);
		String getSelectedRatePlan = elements.selectedRatePlan.getText();
		Assert.assertEquals(getSelectedRatePlan.trim(), ratePlan.trim());
		test_steps.add("Verify created rate plan is selected in dropdown: " + ratePlan);
	}

	public void verifyRatePlanSingleRoomClass(WebDriver driver, String roomClass, ArrayList<String> testSteps)
			throws InterruptedException {

		String path = "//div[@class='DatesTable']//div[@class='roomClassName' and contains(text(),'" + roomClass
				+ "')]";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		assertEquals(element.getText().toLowerCase().trim(), roomClass.toLowerCase().trim(),
				"Failed to verify room Class");
		testSteps.add("Verified Room Class : <b> " + element.getText() + " Displayed</b>");
		logger.info("Verified Room Class : " + element.getText() + "Displayed");

	}

	// Added By adhnan 8/04/2020
	public List<String>[] getActiveRatePlans(WebDriver driver, ArrayList<String> ratePlanTypes, int startIndex)
			throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		int size = elements.ratePlanNames.size();
		logger.info(size);
		String rate = null;
		List<String>[] ratePlans = new List[ratePlanTypes.size()];
		for (int i = 0; i < ratePlanTypes.size(); i++) {
			ratePlans[i] = new ArrayList<String>();
		}

		for (int i = startIndex; i < size; i++) {
//					if(i > 50){
//						break;
//					}
			if (i > startIndex /* && !rate.contains("[Inactive]") && rate!=null */) {
				// Wait.wait2Second();
				elements.ratePlanArrow.click();
				logger.info("click rate Plan Arrow");
			}
			// Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);
			rate = elements.ratePlanNames.get(i).getText();
			logger.info(rate);
			if (!rate.contains("[Inactive]")) {
				elements.ratePlanNames.get(i).click();
				String selectedRatePlanxpath = "//span[@class='rate-plan-picker-label']";
				Wait.WaitForElement(driver, selectedRatePlanxpath);
				WebElement selectedRatePlan = driver.findElement(By.xpath(selectedRatePlanxpath));
				Wait.explicit_wait_visibilityof_webelement(selectedRatePlan, driver);
				if (selectedRatePlan.getText().equals(rate)) {
					if (elements.getRatePlanDescriptionList.size() > 0) {
						for (int rateType = 0; rateType < ratePlanTypes.size(); rateType++) {

							if (elements.getRatePlanDescription.getText().contains(ratePlanTypes.get(rateType))) {
								logger.info(i + " : " + ratePlanTypes.get(rateType) + " added :" + rate);
								ratePlans[rateType].add(rate);
								logger.info(rate);
								break;
							}
						}
					} else {
						logger.info("Rate Plan not required" + rate);
						logger.info(rate);

					}
				}
			} else {

				logger.info("InActive Rate Plan found");
				clickRatePlanArrowOpenIcon(driver, new ArrayList<String>());
				break;
			}
		}

		logger.info(getNames.size());
		return ratePlans;

	}

	// Added By adhnan 8/04/2020
	public ArrayList<String> getActiveRatePlans(WebDriver driver, String ratePlanType, int requiredNumberOfRatePlans)
			throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		int size = elements.ratePlanNames.size();
		logger.info(size);
		String rate = null;
		for (int i = 0; i < size; i++) {

			logger.info(getNames.size());
			if (getNames.size() == requiredNumberOfRatePlans) {
				break;
			}
			if (i > 0 /*
						 * && !rate.contains("[Inactive]") && rate!=null
						 */) {
				// Wait.wait2Second();
				elements.ratePlanArrow.click();
				logger.info("click rate Plan Arrow");
			}
			// Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);
			rate = elements.ratePlanNames.get(i).getText();
			logger.info(rate);
			if (!rate.contains("[Inactive]")) {
				elements.ratePlanNames.get(i).click();
				String selectedRatePlanxpath = "//span[@class='rate-plan-picker-label']";
				Wait.WaitForElement(driver, selectedRatePlanxpath);
				WebElement selectedRatePlan = driver.findElement(By.xpath(selectedRatePlanxpath));
				Wait.explicit_wait_visibilityof_webelement(selectedRatePlan, driver);
				if (selectedRatePlan.getText().equals(rate)) {
					if (elements.getRatePlanDescriptionList.size() > 0) {
						logger.info(i + "Rate Plan Description : " + elements.getRatePlanDescription.getText());
						if (elements.getRatePlanDescription.getText().contains(ratePlanType)) {
							logger.info(i + " : " + ratePlanType + " added :" + rate);
							getNames.add(rate);
							logger.info(rate);
						}
					} else {
						logger.info("Rate Plan not required" + rate);
						logger.info(rate);

					}
				}
			} else {

				logger.info("InActive Rate Plan found");
				clickRatePlanArrowOpenIcon(driver, new ArrayList<String>());
				break;
			}
		}

		logger.info(getNames.size());
		return getNames;
	}

	/*
	 * public ArrayList<String> getActiveRatePlans(WebDriver driver, String
	 * ratePlanType, String ratePlanType2, int startIndex) throws
	 * InterruptedException { ArrayList<String> getNames = new ArrayList<String>();
	 * Elements_RatesGrid elements = new Elements_RatesGrid(driver);
	 * Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList); int size =
	 * elements.ratePlanNames.size(); logger.info(size); String rate = null; for
	 * (int i = startIndex; i < size; i++) { // if(i > 50){ // break; // } if (i >
	 * startIndex
	 * 
	 * && !rate.contains("[Inactive]") && rate!=null ) {
	 * 
	 * // Wait.wait2Second(); elements.ratePlanArrow.click();
	 * logger.info("click rate Plan Arrow"); } //
	 * Utility.ScrollToElement(elements.ratePlanNames.get(i), driver); rate =
	 * elements.ratePlanNames.get(i).getText(); logger.info(rate); if
	 * (!rate.contains("[Inactive]")) { elements.ratePlanNames.get(i).click();
	 * String selectedRatePlanxpath = "//span[@class='rate-plan-picker-label']";
	 * Wait.WaitForElement(driver, selectedRatePlanxpath); WebElement
	 * selectedRatePlan = driver.findElement(By.xpath(selectedRatePlanxpath));
	 * Wait.explicit_wait_visibilityof_webelement(selectedRatePlan, driver); if
	 * (selectedRatePlan.getText().equals(rate)) { if
	 * (elements.getRatePlanDescriptionList.size() > 0) { if
	 * (elements.getRatePlanDescription.getText().contains(ratePlanType)) {
	 * logger.info(i + " : " + ratePlanType + " added :" + rate);
	 * getNames.add(rate); logger.info(rate); } else if
	 * (elements.getRatePlanDescription.getText().contains(ratePlanType2)) {
	 * logger.info(i + " : " + ratePlanType2 + " added :" + rate);
	 * getNames.add(rate); logger.info(rate); } } else {
	 * logger.info("Rate Plan not required" + rate); logger.info(rate);
	 * 
	 * } } } else {
	 * 
	 * logger.info("InActive Rate Plan found"); clickRatePlanArrowOpenIcon(driver,
	 * new ArrayList<String>()); break; } }
	 * 
	 * logger.info(getNames.size()); return getNames; }
	 */
	public ArrayList<String> verifyDerivedRateDisplay(WebDriver driver, String rateName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String path = "//span[text()='" + rateName + "']";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);

		WebElement derivedRate = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(derivedRate, driver);
		assertTrue(derivedRate.isDisplayed(),
				"Failed derived rate with name ( " + rateName + ") is not displaying in rate grid");

		testSteps.add("Verified that derived rate (" + rateName + ") is displaying inrate grid");
		logger.info("Verified that derived rate (" + rateName + ") is displaying inrate grid");
		return testSteps;
	}

	public ArrayList<String> clickDerivedRateExpandIcon(WebDriver driver, String rateName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String path = "//span[text()='" + rateName + "']//preceding-sibling::span";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement derivedRate = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(derivedRate, driver);
		derivedRate.click();
		testSteps.add("Clicked derived rate (" + rateName + ") expand icon in rate grid");
		logger.info("Clicked derived rate (" + rateName + ") expand icon in rate grid");
		return testSteps;
	}

	/*
	 * public String getDerivedRateCondition(WebDriver driver) throws
	 * InterruptedException { Elements_RatesGrid elements = new
	 * Elements_RatesGrid(driver); Wait.WaitForElement(driver,
	 * OR_RatesGrid.DerivedRateCondition);
	 * Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.DerivedRateCondition),
	 * driver); String getText = elements.DerivedRateCondition.getText();
	 * 
	 * logger.info(getText); return getText; }
	 * 
	 * public ArrayList<String> verifyDerivedRatePlanAmount(WebDriver driver, String
	 * ratePlan, String amount, String currencySymbol, String parentRatePlan) throws
	 * InterruptedException { ArrayList<String> testSteps = new ArrayList<String>();
	 * String path = "//span[text()='" + ratePlan +
	 * "']//parent::h3//following-sibling::div//div[contains(text(),'Derived Rate Plan:')]"
	 * ; logger.info(path); WebElement derivedRate =
	 * driver.findElement(By.xpath(path));
	 * 
	 * =======
	 * 
	 * logger.info(getNames.size()); return ratePlans;
	 * 
	 * }
	 */
	/*
	 * public ArrayList<String> verifyDerivedRateDisplay(WebDriver driver, String
	 * rateName) throws InterruptedException { ArrayList<String> testSteps = new
	 * ArrayList<String>(); String path = "//span[text()='" + rateName + "']";
	 * Wait.WaitForElement(driver, path);
	 * Wait.waitForElementToBeVisibile(By.xpath(path), driver);
	 * Wait.waitForElementToBeClickable(By.xpath(path), driver);
	 * 
	 * WebElement derivedRate = driver.findElement(By.xpath(path));
	 * Utility.ScrollToElement(derivedRate, driver);
	 * assertTrue(derivedRate.isDisplayed(), "Failed derived rate with name ( " +
	 * rateName + ") is not displaying in rate grid");
	 * 
	 * testSteps.add("Verified that derived rate (" + rateName +
	 * ") is displaying inrate grid"); logger.info("Verified that derived rate (" +
	 * rateName + ") is displaying inrate grid"); return testSteps; }
	 * 
	 * public ArrayList<String> clickDerivedRateExpandIcon(WebDriver driver, String
	 * rateName) throws InterruptedException { ArrayList<String> testSteps = new
	 * ArrayList<String>(); String path = "//span[text()='" + rateName +
	 * "']//preceding-sibling::span"; Wait.WaitForElement(driver, path);
	 * Wait.waitForElementToBeVisibile(By.xpath(path), driver);
	 * Wait.waitForElementToBeClickable(By.xpath(path), driver); WebElement
	 * derivedRate = driver.findElement(By.xpath(path));
	 * Utility.ScrollToElement(derivedRate, driver); derivedRate.click();
	 * testSteps.add("Clicked derived rate (" + rateName +
	 * ") expand icon in rate grid"); logger.info("Clicked derived rate (" +
	 * rateName + ") expand icon in rate grid"); return testSteps; }
	 */
	public String getDerivedRateCondition(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.DerivedRateCondition);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.DerivedRateCondition), driver);
		String getText = elements.DerivedRateCondition.getText();

		logger.info(getText);
		return getText;
	}

	public ArrayList<String> verifyDerivedRatePlanAmount(WebDriver driver, String ratePlan, String amount,
			String currencySymbol, String parentRatePlan) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String path = "//span[text()='" + ratePlan
				+ "']//parent::h3//following-sibling::div//div[contains(text(),'Derived Rate Plan:')]";
		logger.info(path);
		WebElement derivedRate = driver.findElement(By.xpath(path));

		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Utility.ScrollToElement(derivedRate, driver);
		assertTrue(derivedRate.isDisplayed(), "Failed to verify derived rate amount in rate grid");

		testSteps.add("Verified derived rate amount in rate grid");
		logger.info("Verified derived rate amount in rate grid");
		return testSteps;
	}

	public ArrayList<String> verifyDerivedRatePlanRoomClasses(WebDriver driver, ArrayList<String> roomClass)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.DerivedRateCondition);

		assertTrue(elements.DerivedRatePlanClasses.size() > 0, "Failed : no room classes attahced with derived rate");
		testSteps.add("Verified room classes size " + elements.DerivedRatePlanClasses.size() + " in derived rate");
		logger.info("Verified room classes size " + elements.DerivedRatePlanClasses.size() + " in derived rate");
		boolean isPresent = false;
		for (int i = 0; i < elements.DerivedRatePlanClasses.size(); i++) {
			String getText = elements.DerivedRatePlanClasses.get(i).getText();
			logger.info("Room classes : " + getText);
			if (roomClass.contains(getText)) {
				isPresent = true;
			} else {
				isPresent = false;
				logger.info(getText);
			}

		}
		// assertEquals(isPresent, true, "Faield : room classes not matched");
		testSteps.add("Verified room class attahced with derived rate");
		logger.info("Verified room class attahced with derived rate");
		return testSteps;
	}

	/*
	 * public void verifyRoomClassAfterDelete(WebDriver driver, String roomClass,
	 * ArrayList<String> testSteps) throws InterruptedException {
	 * 
	 * String path =
	 * "//div[@class='DatesTable']//div[@class='roomClassName' and contains(text(),'"
	 * + roomClass + "')]"; boolean isExist = Utility.isElementPresent(driver,
	 * (By.xpath(path))); assertEquals(isExist, false,
	 * "Failed to verify room Class");
	 * testSteps.add("Verified Room Class : <b> Not Displayed </b>");
	 * logger.info("Verified Room Class : Not Displayed");
	 * 
	 * 
	 * 
	 * testSteps.add("Verified derived rate amount in rate grid");
	 * logger.info("Verified derived rate amount in rate grid"); return testSteps; }
	 * 
	 * public ArrayList<String> verifyDerivedRatePlanRoomClasses(WebDriver driver,
	 * ArrayList<String> roomClass) throws InterruptedException { ArrayList<String>
	 * testSteps = new ArrayList<String>(); Elements_RatesGrid elements = new
	 * Elements_RatesGrid(driver); Wait.WaitForElement(driver,
	 * OR_RatesGrid.DerivedRateCondition);
	 * 
	 * assertTrue(elements.DerivedRatePlanClasses.size() > 0,
	 * "Failed : no room classes attahced with derived rate");
	 * testSteps.add("Verified room classes size " +
	 * elements.DerivedRatePlanClasses.size() + " in derived rate");
	 * logger.info("Verified room classes size " +
	 * elements.DerivedRatePlanClasses.size() + " in derived rate"); boolean
	 * isPresent = false; for (int i = 0; i <
	 * elements.DerivedRatePlanClasses.size(); i++) { String getText =
	 * elements.DerivedRatePlanClasses.get(i).getText();
	 * logger.info("Room classes : " + getText); if (roomClass.contains(getText)) {
	 * isPresent = true; } else { isPresent = false; logger.info(getText); }
	 * 
	 * } // assertEquals(isPresent, true, "Faield : room classes not matched");
	 * testSteps.add("Verified room class attahced with derived rate");
	 * logger.info("Verified room class attahced with derived rate"); return
	 * testSteps; }
	 * 
	 * public void verifyRoomClassAfterDelete(WebDriver driver, String roomClass,
	 * ArrayList<String> testSteps) throws InterruptedException {
	 * 
	 * String path =
	 * "//div[@class='DatesTable']//div[@class='roomClassName' and contains(text(),'"
	 * + roomClass + "')]"; boolean isExist = Utility.isElementPresent(driver,
	 * (By.xpath(path))); assertEquals(isExist, false,
	 * "Failed to verify room Class");
	 * testSteps.add("Verified Room Class : <b> Not Displayed </b>");
	 * logger.info("Verified Room Class : Not Displayed");
	 * 
	 * >>>>>>> develop }
	 */
	public ArrayList<String> showroomavailability(WebDriver driver, boolean isToggleEnable)
			throws InterruptedException {
		ArrayList<String> test_steps = new ArrayList<String>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		String toggleEnableColor = "rgba(62, 168, 244, 1)";
		String toggleDisableColor = "rgba(204, 204, 204, 1)";
		Wait.explicit_wait_visibilityof_webelement(ratesGrid.settingAvailabilityToggleButton, driver);
		Utility.ScrollToElement(ratesGrid.settingAvailabilityToggleButton, driver);
		if (isToggleEnable) {

			if (ratesGrid.settingAvailabilityToggleButton.getCssValue("background-color").equals(toggleDisableColor)) {

				ratesGrid.settingAvailabilityToggleButton.click();
				Wait.wait5Second();
				assertEquals(ratesGrid.settingAvailabilityToggleButton.getCssValue("background-color"),
						toggleEnableColor, "Failed:Toggle didn't Enable");
			}
			test_steps.add("Rates Grid Setting: Show room availability Enable");
			ratesGridLogger.info("Rates Grid Setting: Show room availability Toggle Enable");
		} else {

			if (ratesGrid.settingAvailabilityToggleButton.getCssValue("background-color").equals(toggleEnableColor)) {

				ratesGrid.settingAvailabilityToggleButton.click();
				Wait.wait5Second();
				assertEquals(ratesGrid.settingAvailabilityToggleButton.getCssValue("background-color"),
						toggleDisableColor, "Failed:Toggle didn't Disable");
			}
			test_steps.add("Rates Grid Setting: Show room availability Toggle Disable");
			ratesGridLogger.info("Rates Grid Setting: Show room availability Toggle Disable");
		}
		return test_steps;

	}

//	public void verifyExAAndExChAtClassLevel(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,String adult, String child) throws InterruptedException {
//		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
//		String path="//div[contains(text(),'"+roomClass+"')]"
//				+ "/parent::div/following-sibling::div/div[contains(@class,'has-changes')and text()='"+rate+"']";		
//			Wait.WaitForElement(driver, path);
//			WebElement	element = driver.findElement(By.xpath(path));				
//			Utility.ScrollToViewElementINMiddle(driver, element);
//			Utility.clickThroughJavaScript(driver, element);
//			String exAdult=getExtraAdult(driver);
//			String exChild=getExtraChild(driver);
//			assertEquals(exAdult,adult,"Failed to Extra Adult ");
//			assertEquals(exChild,child,"Failed to Extra Child ");
//			testSteps.add("Room Class Is: <b>"+roomClass+" </b>Adult Is <b>"+ exAdult +"</b> AND Child Is <b>"+ exChild + "</b>");
//			logger.info("Verified Extra Adult and Extra Child at Channel Level   :  Adult Is"+ exAdult +"AND Child Is"+ exChild );
//			elements.rateCrossIcon.click();
//			logger.info("Click on Cross Icon");
//	}
//	
//	public void verifyExAAndExChAtClassLevelAfterRemovedOverRide(WebDriver driver, ArrayList<String> testSteps, String roomClass, String rate,String adult, String child) throws InterruptedException {
//		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
//		String path="//div[contains(text(),'"+roomClass+"')]"
//				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')and text()='"+rate+"']";		
//			Wait.WaitForElement(driver, path);
//			WebElement	element = driver.findElement(By.xpath(path));				
//			Utility.ScrollToViewElementINMiddle(driver, element);
//			Utility.clickThroughJavaScript(driver, element);
//			String exAdult=getExtraAdult(driver);
//			String exChild=getExtraChild(driver);
//			assertEquals(exAdult,adult,"Failed to Extra Adult ");
//			assertEquals(exChild,child,"Failed to Extra Child ");
//			
//			if(exAdult.isEmpty()&& exChild.isEmpty())
//			{
//				 String exAd="Removed Extra Adult";
//				 String exCh="Removed Extra Child";
//				testSteps.add("Room Class Is: <b>"+roomClass+" </b>Adult Is <b>"+ exAd +"</b> AND Child Is <b>"+ exCh + "</b>");
//
//			}
//			else
//			{
//				testSteps.add("Room Class Is: <b>"+roomClass+" </b>Adult Is <b>"+ exAdult +"</b> AND Child Is <b>"+ exChild + "</b>");
//
//			}
//			logger.info("Verified Extra Adult and Extra Child at Class Level   :  Adult Is"+ exAdult +"AND Child Is"+ exChild );
//			elements.rateCrossIcon.click();
//			logger.info("Click on Cross Icon");
//	}

//	public void updateExtraAdultsAndExtraChildAtChannelLevelForOverrideRate(WebDriver driver, ArrayList<String> testSteps, String roomClass, String channelName,String rate,
//			String adults, String child) throws InterruptedException {
//		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
//		String path="//div[contains(text(),'"+roomClass+"')]"
//				+ "/ancestor::div/..//following-sibling::div//div[@title='"+channelName+"']"
//				+ "/parent::div/following-sibling::div/div[contains(@class,'has-changes')and text()='"+rate+"']";		
//		Wait.WaitForElement(driver, path);
//		WebElement element = driver.findElement(By.xpath(path));
//		Utility.ScrollToViewElementINMiddle(driver, element);
//		element.click();
//		elements.rateGridExtraAd.clear();
//		elements.rateGridExtraAd.sendKeys(adults);
//		testSteps.add("Enter Extra Adults is: <b>" + adults + "</b>");
//		logger.info("Enter Extra Adults is: " + adults);
//		elements.rateGridExtraCh.clear();
//		elements.rateGridExtraCh.sendKeys(child);
//		testSteps.add("Enter Extra Child is: <b>" + child + "</b>");
//		logger.info("Enter Extra Child is: " + child);
//		elements.rateGridSuccess.click();
//		testSteps.add("Click on Success Icon");
//		logger.info("Click on Success Icon");
//		Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
//		assertTrue(elements.rateSavedMessage.isEnabled(), "Failed to Verify Success Message");
//		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
//		logger.info("Verified Message : Rate saved successfully");
//
//	}

	public ArrayList<String> clickStartDateCalenderIconBulkUpdateAvailability(WebDriver driver) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.StartDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.StartDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.StartDateInput), driver);
		elements.StartDateInput.click();
		testSteps.add("Click start date");
		rateGridLogger.info("Click start date");
		return testSteps;
	}

	public ArrayList<String> clickEndDateCalenderIconBulkUpdateAvailability(WebDriver driver) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.EndDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.EndDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.EndDateInput), driver);
		elements.EndDateInput.click();
		testSteps.add("Click end date");
		rateGridLogger.info("Click end date");
		return testSteps;
	}

	/*
	 * public void verifySelectedRatePlan(WebDriver driver, String ratePlan,
	 * ArrayList<String>test_steps) throws Exception {
	 * 
	 * Elements_RatesGrid elements = new Elements_RatesGrid(driver);
	 * Wait.waitUntilPresenceOfElementLocated(RateGridPage.SelectedRatePlan,
	 * driver); String getSelectedRatePlan = elements.selectedRatePlan.getText();
	 * Assert.assertEquals(getSelectedRatePlan.trim(), ratePlan.trim());
	 * test_steps.add("Verify created rate plan is selected in dropdown: "+
	 * ratePlan); }
	 */
	/*
	 * //extracting rate only from the HashMap(rate parameter) which having rate as
	 * value and date as Key public ArrayList<String> getRate(HashMap<String,
	 * String> rate, WebDriver driver, String checkin, String checkout,
	 * ArrayList<String>test_steps){ ArrayList<String> rates = new
	 * ArrayList<String>(); ArrayList<String> dates = new ArrayList<String>();
	 * String checkinDate = Utility.parseDate(checkin, "dd/MM/yyyy", "MM/dd/yyyy");
	 * String checkoutDate = Utility.parseDate(checkout, "dd/MM/yyyy",
	 * "MM/dd/yyyy"); Date date1 = new Date(checkinDate);
	 * 
	 * Date date2 = new Date(checkoutDate); List<Date> diff =
	 * Utility.getDateRangeBetweenfromAndToDate(date1, date2); DateFormat
	 * outputFormatter = new SimpleDateFormat("dd/MM/yyyy"); for(int i = 0; i <
	 * diff.size()-1; i++) { String findDate = outputFormatter.format(diff.get(i));
	 * dates.add(findDate); String getRate = rate.get(findDate); rates.add(getRate);
	 * test_steps.add("Rate is: "+ getRate+ " on: "+ findDate);
	 * logger.info("Rate is: "+ getRate+ " on: "+ findDate);
	 * 
	 * } return rates; }
	 */
	/*
	 * public void clickRateDropDown(WebDriver driver, ArrayList<String>test_steps)
	 * { Elements_RatesGrid elements = new Elements_RatesGrid(driver);
	 * Wait.waitUntilPresenceOfElementLocated(RateGridPage.OpenRateDropdown,
	 * driver); elements.OpenRateDropdown.click();
	 * test_steps.add("Open rate grid dropdown");
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } public void selectRatePlan(WebDriver driver, String ratePlanName,
	 * ArrayList<String>test_steps) { Elements_RatesGrid elements = new
	 * Elements_RatesGrid(driver); clickRateDropDown(driver, test_steps);
	 * Wait.waitUntilPresenceOfElementLocated(RateGridPage.SearchRatPlan, driver);
	 * elements.searchRatPlan.sendKeys(ratePlanName);
	 * test_steps.add("Search rate Plan: "+ratePlanName);
	 * elements.selectRatePlan.click();
	 * test_steps.add("Select rate plan: "+ratePlanName);
	 * 
	 * }
	 */

	public ArrayList<String> searchRatePlan(WebDriver driver, String ratePlanName) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		Wait.WaitForElement(driver, RateGridPage.SearchInput);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SearchInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SearchInput), driver);

		elements.SearchInput.sendKeys(ratePlanName);
		testSteps.add("Searched rate plan with name : " + ratePlanName);
		rateGridLogger.info("Searched rate plan with name : " + ratePlanName);

		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		int size = elements.ratePlanNames.size();
		logger.info(size);
		for (int i = 0; i < size; i++) {
			/*
			 * if (i > 0) { Wait.wait2Second(); elements.ratePlanArrow.click();
			 * 
			 * }
			 */

			Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);

			String getRatePlanName = elements.ratePlanNames.get(i).getText();
			if (getRatePlanName.equalsIgnoreCase(ratePlanName)) {
				elements.ratePlanNames.get(i).click();
				break;
			}

		}

		return testSteps;
	}

	public String selectedRatePlan(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.SelectedRatePlan);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SelectedRatePlan), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SelectedRatePlan), driver);

		return elements.SelectedRatePlan.getText();
	}

	public void clickOnEditRatePlan(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.ClickOnEditRatePlan);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.ClickOnEditRatePlan), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.ClickOnEditRatePlan), driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("window.scrollBy(0,-150)");
		Utility.ScrollToElement(driver.findElement(By.id("rates-and-availability-tabs-tab-AVAILABILITY_VIEW")), driver);
		// Utility.ScrollToElement(elements.ClickOnEditRatePlan, driver);
		try {
			elements.ClickOnEditRatePlan.click();

		} catch (Exception e) {

			jse.executeScript("window.scrollBy(0,-100)");
			elements.ratePlanArrow.click();
			logger.info("Scroll up");
			elements.ClickOnEditRatePlan.click();

		}
	}

	public ArrayList<String> ratePlanOverView(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.ratePlanOverView);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.ratePlanOverView), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.ratePlanOverView), driver);
		assertEquals(elements.ratePlanOverView.getText(), "Overview",
				"Failed: Overview page is not displayingafter click on edit button");
		testSteps.add("Verified overview heading");
		return testSteps;

	}

	public ArrayList<String> clickOnRestrcitionAndPoliciesTab(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.RestricationsAndPoliciesTab);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.RestricationsAndPoliciesTab), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.RestricationsAndPoliciesTab), driver);
		Utility.ScrollToElement_NoWait(elements.RestricationsAndPoliciesTab, driver);
		elements.RestricationsAndPoliciesTab.click();
		testSteps.add("Click on policies and restrication tab");
		return testSteps;

	}

	public ArrayList<String> clickOnSeasonTab(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.SeasonTab);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SeasonTab), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SeasonTab), driver);
		Utility.ScrollToElement_NoWait(elements.SeasonTab, driver);
		elements.SeasonTab.click();
		testSteps.add("Click on season tab");
		return testSteps;

	}

	public ArrayList<String> clickOnSaveratePlan(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.SaveRatePlan);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SaveRatePlan), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SaveRatePlan), driver);
		Wait.wait2Second();
		Utility.ScrollToElement_NoWait(elements.SaveRatePlan, driver);
		try {
			if (elements.SaveRatePlan.isEnabled() && driver.findElements(By.xpath("//img[@alt='dirty']")).size() > 0) {
				elements.SaveRatePlan.click();
				testSteps.add("Click on save rate plan buttton");
				logger.info("Clicking on Save rate Plan button");
				Wait.WaitForElement(driver, "//div[@class='ant-notification-notice-message']");
			}
		} catch (Exception e) {

		}

		return testSteps;

	}

	public void clickonbyDefaultProrateCheckbox(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.CheckboxProrateForEachSeasonbyDefault);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		ratesGrid.CheckboxProrateForEachSeasonbyDefault.click();

	}

	public String getPerNightRate(WebDriver driver, String roomClassName) throws InterruptedException {

		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClassName
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		Wait.WaitForElement(driver, enterRate);
		Wait.waitForElementToBeVisibile(By.xpath(enterRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(enterRate), driver);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);
		return element.get(0).getAttribute("value");

	}

	public void clickOnCapcity(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ratesGrid.ClickOnCapcity.click();

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRatePlanColorInRatePlanDropDownList> ' Description: <
	 * return color of rate plan in the Rates grid Rate Plan drop down> ' Input
	 * parameters: < WebDriver, String> ' Return value:<String> ' Created By:
	 * <Adhnan Ghaffar> ' Created On: <08/21/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public String getRatePlanColorInRatePlanDropDownList(WebDriver driver, String ratePlanName)
			throws InterruptedException {
		String path = "//div[@class='Select-menu-outer']/div[@class='Select-menu']/div[@aria-label='" + ratePlanName
				+ "']/div";
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		String color = element.getAttribute("style");
		logger.info("Rate Rate Plan Style In Drop Down Box: " + color);
		color = color.split(":")[1].trim();
		logger.info("Rate Rate Plan Color In Drop Down Box: " + color);
		color = color.replace(";", "");
		logger.info("Rate Rate Plan Color In Drop Down Box: " + color);
		return color;
	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <getRatePlanColorInverifyDerivedRateLabelRatePlanDropDownList>
	 * ' Description: < Validations on Derived Rates Label > ' Input parameters: <
	 * WebDriver, String, ArrayList,String>> ' Return value:ArrayList<String> '
	 * Created By: <Adhnan Ghaffar> ' Created On: <08/21/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyDerivedRateLabel(WebDriver driver, String labelColor, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.DERIVED_RATE_PLAN_LABEL);
		Wait.explicit_wait_visibilityof_webelement(elements.DERIVED_RATE_PLAN_LABEL, driver);
		Utility.ScrollToElement(elements.DERIVED_RATE_PLAN_LABEL, driver);
		assertTrue(elements.DERIVED_RATE_PLAN_LABEL.isDisplayed(), "Failed derived rate Plan label not exist");
		String text = elements.DERIVED_RATE_PLAN_LABEL.getText().replaceAll("/n", "");
		logger.info("Found Derived Rates Label tex : " + text);
		assertTrue(text.contains("Derived Rates"), "Failed derived rate Plan label text missmatched");
		testSteps.add("Successfully Verified Derived Rates Label is Displayed");
		logger.info("Successfully Verified Derived Rates Label is Displayed");
		String color = elements.DERIVED_RATE_PLAN_ARROW.getCssValue("color");
		testSteps.add("Found Derived Rates Label Color  : " + color);
		logger.info("Found Derived Rates Label Color : " + color);
		testSteps.add("Expected Derived Rates Label Color : " + labelColor);
		logger.info("Expected Derived Rates Label Color : " + labelColor);
		assertTrue(color.contains(labelColor), "Failed Derived Rate Label color missmatched");

		testSteps.add("Successfully verified Derived Rates Label color is  Purple(" + color + ")");
		logger.info("Successfully verified Derived Rates Label color is Purple (" + color + ")");

		return testSteps;
	}

	public boolean verifySeasonExist(WebDriver driver, String date) throws InterruptedException {

		String path = "//div[@aria-label='" + date + "']//div";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		logger.info("back color: " + element.getAttribute("style"));

		boolean isSeasonExist = false;
		if (element.getAttribute("style").contains("background-color")) {
			isSeasonExist = true;
		}

		return isSeasonExist;
	}

	public boolean verifyLenthOfStayCheckBox(WebDriver driver, String nameOfComponent) throws InterruptedException {
		String path = "//span[text()='" + nameOfComponent + "']//parent::label";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));

		boolean isLengthOfStayChecked = false;
		if (element.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {
			isLengthOfStayChecked = true;
		}
		return isLengthOfStayChecked;

	}

	public void expandRoomClass(WebDriver driver, String roomClass) throws InterruptedException {

		String expandRoomClass = "//div[text()='" + roomClass
				+ "']//following-sibling::span[contains(@class,'ir-plus')]";

		if (driver.findElements(By.xpath(expandRoomClass)).size() > 0) {
			Wait.WaitForElement(driver, expandRoomClass);
			Wait.waitForElementToBeVisibile(By.xpath(expandRoomClass), driver);
			Wait.waitForElementToBeClickable(By.xpath(expandRoomClass), driver);
			JavascriptExecutor javaScript = (JavascriptExecutor) driver;
			javaScript.executeScript("window.scrollBy(0,-300)");
			try {
				driver.findElement(By.xpath(expandRoomClass)).click();
			} catch (Exception e) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(expandRoomClass)));
			}

		}
	}

	public String getRoomClassesAvailability(WebDriver driver, String channel, int index) throws InterruptedException {

		String getAvailability = "(//div[text()='" + channel + "']//..//following-sibling::div)[" + index + "]";
		Wait.WaitForElement(driver, getAvailability);
		Wait.waitForElementToBeVisibile(By.xpath(getAvailability), driver);
		Wait.waitForElementToBeClickable(By.xpath(getAvailability), driver);
		WebElement element = driver.findElement(By.xpath(getAvailability));
		// Utility.ScrollToElement(element, driver);
		return element.getAttribute("class");

	}

	public String getRate(WebDriver driver, String channel, int index) throws InterruptedException {

		String getRate = "(//div[text()='" + channel + "']//..//following-sibling::div)[" + index
				+ "]//div[@class=' RegularRate']";
		Wait.WaitForElement(driver, getRate);
		Wait.waitForElementToBeVisibile(By.xpath(getRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(getRate), driver);
		WebElement element = driver.findElement(By.xpath(getRate));
		// Utility.ScrollToElement(element, driver);
		return element.getText();

	}

	public String getRateRegularAndOverriden(WebDriver driver, String channel, int index) throws InterruptedException {

		String getRate = "(//div[text()='" + channel + "']//..//following-sibling::div)[" + index + "]//div[1]";
		Wait.WaitForElement(driver, getRate);
		Wait.waitForElementToBeVisibile(By.xpath(getRate), driver);
		Wait.waitForElementToBeClickable(By.xpath(getRate), driver);
		WebElement element = driver.findElement(By.xpath(getRate));
		// Utility.ScrollToElement(element, driver);
		return element.getText();

	}

	public String getRule(WebDriver driver, String ruleName, int index) throws InterruptedException {

		String getRule = "(//div[text()='" + ruleName + "']//following-sibling::div)[" + index
				+ "]//input[@name='rulevalue']";
		Wait.WaitForElement(driver, getRule);
		Wait.waitForElementToBeVisibile(By.xpath(getRule), driver);
		Wait.waitForElementToBeClickable(By.xpath(getRule), driver);
		WebElement element = driver.findElement(By.xpath(getRule));
		return element.getAttribute("value");

	}

	public ArrayList<String> expandDerivedRate(WebDriver driver, String ruleName) throws InterruptedException {

		String getRule = "//span[text()='" + ruleName + "']//preceding-sibling::span";
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, getRule);
		Wait.waitForElementToBeVisibile(By.xpath(getRule), driver);
		Wait.waitForElementToBeClickable(By.xpath(getRule), driver);
		WebElement element = driver.findElement(By.xpath(getRule));
		element.click();
		testSteps.add(ruleName + " expand");
		rateGridLogger.info(ruleName + " expand");

		return testSteps;

	}

	public String getNoCheckInCheckOut(WebDriver driver, String ruleName, int index) throws InterruptedException {

		String getCheckinCheckout = "(//div[text()='" + ruleName + "']//following-sibling::div)[" + index
				+ "]//div//child::div[contains(@class, 'rt-onHover')]";
		Wait.WaitForElement(driver, getCheckinCheckout);
		Wait.waitForElementToBeVisibile(By.xpath(getCheckinCheckout), driver);
		Wait.waitForElementToBeClickable(By.xpath(getCheckinCheckout), driver);
		WebElement element = driver.findElement(By.xpath(getCheckinCheckout));
		return element.getAttribute("class");

	}

	public List<String>[] getRateMinStayRule(WebDriver driver, String checkIn, String checkout, String ratePlan,
			String roomClass, String timeZone, ArrayList<String> testSteps)
			throws InterruptedException, ParseException {

		int days = ESTTimeZone.numberOfDaysBetweenDates(checkIn, checkout);
		ArrayList<String> getRateValue = new ArrayList<>();
		ArrayList<String> getRuleValue = new ArrayList<>();
		ArrayList<String> getNoCheckInValue = new ArrayList<>();
		ArrayList<String> getNoCheckOutValue = new ArrayList<>();

		int totalCount = days / 20;
		rateGridLogger.info("totalCount: " + totalCount);
		int remingdays = days % 20;
		rateGridLogger.info("remingdays: " + remingdays);

		if (remingdays > 0) {
			totalCount = totalCount + 1;
		}
		rateGridLogger.info("totalCount: " + totalCount);

		int count = 0;
		clickOnCalendarIcon(driver);
		String getMonth = getMonthFromCalendarHeading(driver);
		boolean isMonthEqual = false;
		String getStartDate = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "EEE MMM dd yyyy");
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "MMMM yyyy");

		rateGridLogger.info("expectedMonth: " + expectedMonth);
		String path = "//div[@aria-label='" + getStartDate + "']";

		int k;
		while (isMonthEqual == false) {

			if (expectedMonth.equals(getMonth)) {
				closeCalendar(driver);
				rateGridLogger.info("Close calendar");

				Wait.wait10Second();
				for (k = 0; k < totalCount; k++) {

					rateGridLogger.info("In if:" + k);

					if (k == 0) {
						// driver.findElement(By.xpath(path)).click();
						clickRatePlanArrow(driver, testSteps);
						searchRatePlan(driver, ratePlan);
						String getRatPlanName = selectedRatePlan(driver);

						expandRoomClass(driver, roomClass);
						rateGridLogger.info("Expand room classes");
						expandRoomClass(driver, "innCenter");
						rateGridLogger.info("Expand room channel");

					}

					// here to put another loop for days verification
					// start date
					int loopCount = 0;
					if (k <= totalCount - 2) {
						loopCount = 20;
					} else {
						loopCount = remingdays;
						isMonthEqual = true;

					}

					for (int j = 1; j <= loopCount; j++) {

						String getRate = getRate(driver, "innCenter", j);
						logger.info("get Rate: " + getRate);
						getRateValue.add(getRate);
						String getRule = getRule(driver, "Min Stay", j);
						getRuleValue.add(getRule);
						logger.info("get Value: " + getRule);
						String getNoCheckIn = getNoCheckInCheckOut(driver, "No Check In", j);
						getNoCheckInValue.add(getNoCheckIn);
						logger.info("get no check in value: " + getNoCheckIn);
						String getNoCheckOut = getNoCheckInCheckOut(driver, "No Check Out", j);
						getNoCheckOutValue.add(getNoCheckOut);
						logger.info("get no check in value: " + getNoCheckOut);

						count = count + 1;

					}

					Elements_RatesGrid elements_RatesGrid = new Elements_RatesGrid(driver);
					if (!isMonthEqual) {
						elements_RatesGrid.ClickOnRightArrowOfDate.click();

					}
				}
				// it will be end when all date read
				logger.info("before break");

				break;

			} else {
				rateGridLogger.info("in else");
				clickOnCalendarRightArrow(driver);
				getMonth = getMonthFromCalendarHeading(driver);
				rateGridLogger.info("getMonth: " + getMonth);
			}

		}

		// }
		List<String>[] arrayOfList = new List[4];
		arrayOfList[0] = getRateValue;
		arrayOfList[1] = getRuleValue;
		arrayOfList[2] = getNoCheckInValue;
		arrayOfList[3] = getNoCheckOutValue;
		return arrayOfList;

	}

	public HashMap<String, List[]> getRateMinStayRule(WebDriver driver, String checkIn, String checkout,
			String ratePlan, String roomClass, String timeZone, String channels, ArrayList<String> testSteps)
			throws InterruptedException, ParseException {

		int days = ESTTimeZone.numberOfDaysBetweenDates(checkIn, checkout);
		ArrayList<String> getRateValue = new ArrayList<>();
		ArrayList<String> getRuleValue = new ArrayList<>();
		ArrayList<Boolean> getNoCheckInValue = new ArrayList<>();
		ArrayList<Boolean> getNoCheckOutValue = new ArrayList<>();

		HashMap<String, List[]> roomMap = new HashMap<>();
		String[] splittedClasses = roomClass.split(Utility.DELIM);
		String[] splittedChannels = channels.split(Utility.DELIM);

		int totalCount = days / 20;
		rateGridLogger.info("totalCount: " + totalCount);
		int remingdays = days % 20;
		rateGridLogger.info("remingdays: " + remingdays);

		if (remingdays > 0) {
			totalCount = totalCount + 1;
		}
		rateGridLogger.info("totalCount: " + totalCount);

		int count = 0;
		clickOnCalendarIcon(driver);
		String getMonth = getMonthFromCalendarHeading(driver);
		boolean isMonthEqual = false;
		String getStartDate = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "EEE MMM dd yyyy");
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "MMMM yyyy");

		rateGridLogger.info("expectedMonth: " + expectedMonth);
		String path = "//div[@aria-label='" + getStartDate + "']";
		Wait.wait10Second();
		closeCalendar(driver);
		rateGridLogger.info("Close calendar ");
		String expectedClassesForEnabled = "enabled";

		int k;
		while (isMonthEqual == false) {

			if (expectedMonth.equals(getMonth)) {

				for (k = 0; k < totalCount; k++) {
					for (String classes : splittedClasses) {
						for (String channel : splittedChannels) {

							rateGridLogger.info("In if:" + k);

							if (k == 0) {
								// driver.findElement(By.xpath(path)).click();
								clickRatePlanArrow(driver, testSteps);
								searchRatePlan(driver, ratePlan);
								String getRatPlanName = selectedRatePlan(driver);

								expandRoomClass(driver, classes);
								rateGridLogger.info("Expand room class (" + classes + ")");
								expandRoomClass(driver, channel);
								rateGridLogger.info("Expand room channel (" + channel + ")");

							}

							// here to put another loop for days verification
							// start date
							int loopCount = 0;
							if (k <= totalCount - 2) {
								loopCount = 20;
							} else {
								loopCount = remingdays;
								isMonthEqual = true;

							}

							for (int j = 1; j <= loopCount; j++) {

								String getRate = getRate(driver, channel, j);
								logger.info("get Rate for " + channel + " in " + classes + " :  " + getRate);
								getRateValue.add(getRate);
								String getRule = getRule(driver, "Min Stay", j);
								getRuleValue.add(getRule);
								logger.info("get Value for " + channel + " in " + classes + " : " + getRule);
								String getNoCheckIn = getNoCheckInCheckOut(driver, "No Check In", j);
								if (getNoCheckIn.contains(expectedClassesForEnabled)) {
									getNoCheckInValue.add(true);
									logger.info("get no check in value for " + channel + " in " + classes + " : "
											+ getNoCheckIn + " : " + true);

								} else {
									getNoCheckInValue.add(false);
									logger.info("get no check in value for " + channel + " in " + classes + " : "
											+ getNoCheckIn + " : " + false);

								}

								String getNoCheckOut = getNoCheckInCheckOut(driver, "No Check Out", j);
								if (getNoCheckOut.contains(expectedClassesForEnabled)) {
									getNoCheckOutValue.add(true);
									logger.info("get no check out value for " + channel + " in " + classes + " : "
											+ getNoCheckOut + " : " + true);
								} else {
									getNoCheckOutValue.add(false);
									logger.info("get no check out value for " + channel + " in " + classes + " : "
											+ getNoCheckOut + " : " + false);
								}
								count = count + 1;

							}

							expandRoomClass(driver, classes);
							rateGridLogger.info("Closed room class (" + classes + ")");

							List[] arrayOfLists = new List[4];
							arrayOfLists[0] = getRateValue;
							arrayOfLists[1] = getRuleValue;
							arrayOfLists[2] = getNoCheckInValue;
							arrayOfLists[3] = getNoCheckOutValue;
							roomMap.put(classes, arrayOfLists);

							Elements_RatesGrid elements_RatesGrid = new Elements_RatesGrid(driver);
							if (!isMonthEqual) {
								elements_RatesGrid.ClickOnRightArrowOfDate.click();
							}

						}
					}
				}
				// it will be end when all date read
				logger.info("before break");
				break;

			} else {
				rateGridLogger.info("in else");
				clickOnCalendarRightArrow(driver);
				getMonth = getMonthFromCalendarHeading(driver);
				rateGridLogger.info("getMonth: " + getMonth);
			}

		}

		// }
		/*
		 * List[] arrayOfList = new List[4]; arrayOfList[0] = getRateValue;
		 * arrayOfList[1] = getRuleValue; arrayOfList[2] = getNoCheckInValue;
		 * arrayOfList[3] = getNoCheckOutValue;
		 */
		return roomMap;

	}

	public List<String>[] getRateMinStayRule(WebDriver driver, String checkIn, String checkout, String ratePlan,

			String roomClass, String timeZone) throws InterruptedException, ParseException {

		int days = ESTTimeZone.numberOfDaysBetweenDates(checkIn, checkout);
		ArrayList<String> getRateValue = new ArrayList<>();
		ArrayList<String> getRuleValue = new ArrayList<>();

		int totalCount = days / 20;
		rateGridLogger.info("totalCount: " + totalCount);
		int remingdays = days % 20;
		rateGridLogger.info("remingdays: " + remingdays);

		if (remingdays > 0) {
			totalCount = totalCount + 1;
		}
		rateGridLogger.info("totalCount: " + totalCount);

		int count = 0;
		clickOnCalendarIcon(driver);
		String getMonth = getMonthFromCalendarHeading(driver);
		boolean isMonthEqual = false;
		String getStartDate = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "EEE MMM dd yyyy");
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "MMMM yyyy");

		rateGridLogger.info("expectedMonth: " + expectedMonth);
		String path = "//div[@aria-label='" + getStartDate + "']";
		int k;
		while (isMonthEqual == false) {

			if (expectedMonth.equals(getMonth)) {
				for (k = 0; k < totalCount; k++) {

					rateGridLogger.info("In if:" + k);

					if (k == 0) {
						driver.findElement(By.xpath(path)).click();
						clickRatePlanArrow(driver, getRuleValue);
						searchRatePlan(driver, ratePlan);
						String getRatPlanName = selectedRatePlan(driver);

						expandRoomClass(driver, roomClass);
						rateGridLogger.info("Expand room classes");
						expandRoomClass(driver, "innCenter");
						rateGridLogger.info("Expand room channel");

					}

					// here to put another loop for days verification
					// start date
					int loopCount = 0;
					if (k <= totalCount - 2) {
						loopCount = 20;
					} else {
						loopCount = remingdays;
						isMonthEqual = true;

					}

					for (int j = 1; j <= loopCount; j++) {

						String getRate = getRate(driver, "innCenter", j);
						logger.info("get Rate: " + getRate);
						getRateValue.add(getRate);
						String getRule = getRule(driver, "Min Stay", j);
						getRuleValue.add(getRule);
						logger.info("get Value: " + getRule);

						count = count + 1;

					}
					Elements_RatesGrid elements_RatesGrid = new Elements_RatesGrid(driver);
					if (!isMonthEqual) {
						elements_RatesGrid.ClickOnRightArrowOfDate.click();

					}
				}
				// it will be end when all date read
				logger.info("before break");

				break;

			} else {
				rateGridLogger.info("in else");
				clickOnCalendarRightArrow(driver);
				getMonth = getMonthFromCalendarHeading(driver);
				rateGridLogger.info("getMonth: " + getMonth);
			}

		}

		// }
		List<String>[] arrayOfList = new List[2];
		arrayOfList[0] = getRateValue;
		arrayOfList[1] = getRuleValue;
		return arrayOfList;

	}

	public void clickOnRatePlanArrow(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePlanArrow);
		Utility.ScrollToElement(elements.ratePlanArrow, driver);
		try {
			elements.ratePlanArrow.click();
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-400)");
			elements.ratePlanArrow.click();
			logger.info("Click Rate Plan Arrow");
		}

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <verifyNumberOfDerivedRatePlanInLabel> ' Description: < Verify
	 * Number of rate Plans in Derived Rates Label > ' Input parameters: <WebDriver,
	 * String, ArrayList,String>> ' Return value:ArrayList<String> ' Created By:
	 * <Adhnan Ghaffar> ' Created On: <08/21/2020>
	 *
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */
	public ArrayList<String> verifyNumberOfDerivedRatePlanInLabel(WebDriver driver, String numberOfRatePlans,
			ArrayList<String> testSteps) throws InterruptedException {
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.DERIVED_RATE_PLAN_LABEL);
		Wait.explicit_wait_visibilityof_webelement(elements.DERIVED_RATE_PLAN_LABEL, driver);
		Utility.ScrollToElement(elements.DERIVED_RATE_PLAN_LABEL, driver);
		assertTrue(elements.DERIVED_RATE_PLAN_LABEL.isDisplayed(), "Failed derived rate Plan label not exist");
		String text = elements.DERIVED_RATE_PLAN_LABEL.getText().replaceAll("/n", "");
		logger.info("Found Derived Rates Label text : " + text);
		logger.info("Expected Derived Rates Label text : (" + numberOfRatePlans + ")");

		testSteps.add("Expected Derived Rates are " + numberOfRatePlans);
		testSteps.add("Found Derived Rates Label text : " + text);
		assertTrue(text.contains("(" + numberOfRatePlans + ")"), "Failed number of derived rate Plans missmatched");
		testSteps.add(
				"Successfully verified Total number of Derived Rates count is Displayed next to the Derived Rate text in brackets");
		logger.info(
				"Successfully verified Total number of Derived Rates count is Displayed next to the Derived Rate text in brackets");

		return testSteps;
	}

	// Added By adhnan 8/04/2020
	public ArrayList<String> getActiveRatePlans(WebDriver driver, String ratePlanType) throws InterruptedException {
		ArrayList<String> getNames = new ArrayList<String>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		int size = elements.ratePlanNames.size();
		logger.info(size);
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				Wait.wait2Second();
				elements.ratePlanArrow.click();
			}
			Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);
			String rate = elements.ratePlanNames.get(i).getText();
			elements.ratePlanNames.get(i).click();
			if (elements.getRatePlanDescriptionList.size() > 0) {
				if (elements.getRatePlanDescription.getText().contains(ratePlanType)) {
					logger.info(ratePlanType + " added :" + rate);
					getNames.add(rate);
					logger.info(rate);
				}
			} else {
				logger.info("Rate Plan not required" + rate);
				logger.info(rate);

			}
		}

		logger.info(getNames.size());
		return getNames;
	}

	public void updateBookingWindowForNightlyRate(WebDriver driver, ArrayList<String> test_steps, String ratePlanName,
			String ratePlanType, String rateChannel, String roomClassName, String moreThanDaysCount,
			String withInDaysCount, String seasonName, String seasonStartDate, String seasonEndDate,
			String ratePerNight) throws Exception {
		Navigation navigation = new Navigation();
		navigation.Inventory(driver, test_steps);
		navigation.RatesGrid(driver);
		NightlyRate nightlyRate = new NightlyRate();
		if (searchForRatePlanExistingOrNot(driver, test_steps, ratePlanName)) {
			test_steps.add("Rate plan is already existing with <b>" + ratePlanName + "</b> name");
			selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanName);
			clickEditIcon(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
			nightlyRate.selectBookingWindow(driver, test_steps, moreThanDaysCount, withInDaysCount, true);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
		} else {
			clickRateGridAddRatePlan(driver);
			clickRateGridAddRatePlanOption(driver, ratePlanType);
			nightlyRate.enterRatePlanName(driver, ratePlanName, test_steps);

//				nightlyRate.enterRateFolioDisplayName(driver, ratePlanName, test_steps);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.selectChannels(driver, rateChannel, true, test_steps);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.selectRoomClasses(driver, roomClassName, true, test_steps);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.selectBookingWindow(driver, test_steps, moreThanDaysCount, withInDaysCount, false);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, seasonStartDate, seasonEndDate);
			nightlyRate.enterSeasonName(driver, test_steps, seasonName);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonColor(driver, test_steps);
			nightlyRate.enterRate(driver, test_steps, roomClassName, ratePerNight);
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickCompleteChanges(driver, test_steps);
			nightlyRate.clickSaveAsActive(driver, test_steps);
		}
	}

	public void searchAndEditRatePlan(WebDriver driver, ArrayList<String> test_steps, String ratePlanName)
			throws Exception {
		Navigation navigation = new Navigation();
		navigation.Inventory(driver, test_steps);
		navigation.RatesGrid(driver);
		verifyRatesGridLoaded(driver);
		if (searchForRatePlanExistingOrNot(driver, test_steps, ratePlanName)) {
			test_steps.add("Rate plan is already existing with <b>" + ratePlanName + "</b> name");
			selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanName);
			verifyRatesGridLoaded(driver);
			clickEditIcon(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
		} else {
			throw new SkipException("Rate plan is not available to validate");
		}
	}

	public HashMap<String, String> getBookingWindowRestrictions(WebDriver driver, ArrayList<String> test_steps,
			String ratePlanName) throws Exception {
		HashMap<String, String> bookingWindowRestrictions = new HashMap<>();
		NightlyRate nightlyRate = new NightlyRate();
		String bookingWindowCheckBox = "//span[text()='Booking window']/..//span[contains(@class,'ant-checkbox')]//input";
		boolean bookingWindowCheckBoxSelected = driver.findElement(By.xpath(bookingWindowCheckBox)).isSelected();
		if (bookingWindowCheckBoxSelected) {
			String moreThanValue = driver
					.findElement(By.xpath(
							"//span[contains(text(),'More than')]/../../..//input[@class='ant-input-number-input']"))
					.getAttribute("value");
			String withInValue = driver.findElement(By.

					xpath("//span[contains(text(),'Within')]/../../..//input[@class='ant-input-number-input']"))
					.getAttribute("value");

			bookingWindowRestrictions.put("More than", moreThanValue);
			bookingWindowRestrictions.put("Within", withInValue);
			if (Utility.validateString(moreThanValue)) {
				test_steps.add("Captured <b>More than --- days in advance of check-in date</b> value as <b>"
						+ moreThanValue + "</b>");
			} else {
				test_steps.add("<b>More than --- days in advance of check-in date</b> check box is disabled");
			}
			if (Utility.validateString(withInValue)) {
				test_steps.add("Captured <b>Within --- days of check-in date</b> value as <b>" + withInValue + "</b>");
			} else {
				test_steps.add("<b>Within --- days of check-in date</b> check box is disabled");
			}
		}
		// nightlyRate.clickSaveRatePlanButton(driver, test_steps);
		return bookingWindowRestrictions;
	}

	// Added By Adhnan 08/29/2020
	// Added By Adhnan 08/29/2020
	public ArrayList<String> bulkUpdate(WebDriver driver, String bulkUpdateType, String startDate, String endDate,
			String dateFormat, String sunday, String monday, String tuesday, String wednesday, String thursday,
			String friday, String saturday, String isTotalOccupancyOn, String totalOccupancyType,
			String totalOccupancyValue, String ratePlansName, String roomClassName, String channel,
			String updateRatesType, String updateRateByRoomClass, String nightlyRate, String additionalAdults,
			String additionalChild, String rateChangeValue, String rateCurrencyType,
			ArrayList<String> activeRatePlanNames, ArrayList<String> inactiveRatePlanNames,
			ArrayList<String> activeRoomClassesNames, ArrayList<String> activeChannelsList, String isMinimumStayOn,
			String minimumStayValue, String isCheckInOn, String isNoCheckInChecked, String isCheckOutOn,
			String isNoCheckOutChecked) throws InterruptedException {
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> testSteps = new ArrayList<>();

		DerivedRate derivedRate = new DerivedRate();
		getTestSteps.clear();
		getTestSteps = clickOnBulkUpdate(driver);
		testSteps.addAll(getTestSteps);
		getTestSteps.clear();
		getTestSteps = selectBulkUpdateOption(driver, bulkUpdateType);
		testSteps.addAll(getTestSteps);
		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupHeading(driver, bulkUpdateType);
		testSteps.addAll(getTestSteps);
		testSteps.add("==========SEELCT START DATE==========");
		logger.info("==========SEELCT START DATE==========");
		getTestSteps.clear();
		getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 0, startDate, dateFormat);
		testSteps.addAll(getTestSteps);

//			getTestSteps.clear();
//			getTestSteps = startDate(driver, Utility.parseDate(startDate, dateFormat, "MM/dd/yyyy"));
//			testSteps.addAll(getTestSteps);

		testSteps.add("==========SEELCT END DATE==========");
		logger.info("==========SEELCT END DATE==========");

		getTestSteps.clear();
		getTestSteps = derivedRate.selectCustomDateFromCalender(driver, 1, endDate, dateFormat);
		testSteps.addAll(getTestSteps);

//			getTestSteps.clear();
//			getTestSteps = endDate(driver, Utility.parseDate(endDate, dateFormat, "MM/dd/yyyy"));
//			testSteps.addAll(getTestSteps);

		logger.info("==========CHECKING/UNCHECKING DAYS==========");
		testSteps.add("==========CHECKING/UNCHECKING DAYS==========");

		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupDayCheck(driver, "Sun", sunday);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupDayCheck(driver, "Mon", monday);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupDayCheck(driver, "Tue", tuesday);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupDayCheck(driver, "Wed", wednesday);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupDayCheck(driver, "Thu", thursday);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupDayCheck(driver, "Fri", friday);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = bulkUpdatePoppupDayCheck(driver, "Sat", saturday);
		testSteps.addAll(getTestSteps);
		getTestSteps.clear();
		getTestSteps = clickTotalOccupancy(driver, isTotalOccupancyOn);
		testSteps.addAll(getTestSteps);

		if (isTotalOccupancyOn.equalsIgnoreCase("Yes")) {

			getTestSteps.clear();
			getTestSteps = selectTotalOccupancyType(driver, totalOccupancyType);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = enterOccupancyValue(driver, totalOccupancyValue);
			testSteps.addAll(getTestSteps);
		}
		logger.info("==========SELECTING RATE PLAN==========");
		testSteps.add("==========SELECTING RATE PLAN==========");

		String[] ratePlanArray = ratePlansName.split(Utility.DELIM);
		for (String str : ratePlanArray) {
			if (str.equalsIgnoreCase("All Active Rate Plans")) {
				str = str + " (" + (activeRatePlanNames.size() - 1) + ")";
			}
			getTestSteps.clear();
			getTestSteps = selectItemsFromDropDowns(driver, "Rate Plan", str);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = verifyDropDownDisableOnAllListSelection(driver, "Rate Plan", str);
			testSteps.addAll(getTestSteps);
		}
		logger.info("==========SELECTING ROOM CLASS==========");
		testSteps.add("==========SELECTING ROOM CLASS==========");
		String[] roomClassArray = roomClassName.split(Utility.DELIM);
		for (String str : roomClassArray) {
			if (str.equalsIgnoreCase("All room classes")) {
				str = str + " (" + activeRoomClassesNames.size() + ")";
				System.out.print(" String is:" + str);
			}
			getTestSteps.clear();
			getTestSteps = selectRoomClass(driver, str, Utility.DELIM);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = verifyDropDownDisableOnAllListSelection(driver, "Room class", str);
			testSteps.addAll(getTestSteps);

		}

		logger.info("==========SELECTING SOURCE==========");
		testSteps.add("==========SELECTING SOURCE==========");

		String[] channelArray = channel.split(Utility.DELIM);
		for (String str : channelArray) {
			if (str.equalsIgnoreCase("All sources")) {
				str = str + " (" + activeChannelsList.size() + ")";
			}
			getTestSteps.clear();
			getTestSteps = selectItemsFromDropDowns(driver, "Source", str);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = verifyDropDownDisableOnAllListSelection(driver, "Source", str);
			testSteps.addAll(getTestSteps);
			verifyColorCodeofSources(driver, str);

		}
		if (bulkUpdateType.equals("Rates")) {

			logger.info("==========UPDATE RATES==========");
			testSteps.add("==========UPDATE RATES==========");

			// Checks Rate Update Type
			if (updateRatesType.equalsIgnoreCase("EnterNewRate")) {
				getTestSteps.clear();
				getTestSteps = selectBulkUpdateRatesOption(driver, 0);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = updateRoomsByRoomClassToggle(driver, Boolean.parseBoolean(updateRateByRoomClass));
				testSteps.addAll(getTestSteps);

				String[] nightlyRateArray = nightlyRate.split(Utility.DELIM);
				int nightArrayLength = 1;
				if (updateRateByRoomClass.equalsIgnoreCase("True")) {
					nightArrayLength = nightlyRateArray.length;
				}
				String[] additionalAdultArray = additionalAdults.split(Utility.DELIM);
				String[] additionalChildArray = additionalAdults.split(Utility.DELIM);
				// Check Length of NightlyRate List and Input Values
				for (int i = 0; i < nightArrayLength; i++) {

					getTestSteps.clear();
					getTestSteps = updateNightlyRate(driver, i, nightlyRateArray[i]);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = updateAdditionalAdultRate(driver, i, additionalAdultArray[i]);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = updateAdditionalChildRate(driver, i, additionalChildArray[i]);
					testSteps.addAll(getTestSteps);

				}

			} else if (updateRatesType.equalsIgnoreCase("Increase") || updateRatesType.equalsIgnoreCase("Decrease")) {
				getTestSteps.clear();
				getTestSteps = selectBulkUpdateRatesOption(driver, 1);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = selectRateIncreaseDecreaseOption(driver, updateRatesType);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = enterRateValueForUpdateRate(driver, rateChangeValue);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = selectRateCurrencyOption(driver, rateCurrencyType);
				testSteps.addAll(getTestSteps);

			} else if (updateRatesType.equalsIgnoreCase("Remove overrides")) {
				getTestSteps.clear();
				getTestSteps = selectBulkUpdateRatesOption(driver, 2);
				testSteps.addAll(getTestSteps);

			}
		} else if (bulkUpdateType.equals("Rules")) {

			getTestSteps.clear();
			getTestSteps = clickMinimumStay(driver, isMinimumStayOn);
			testSteps.addAll(getTestSteps);

			if (isMinimumStayOn.equalsIgnoreCase("Yes")) {

				getTestSteps.clear();
				getTestSteps = enterMinimumStayValue(driver, minimumStayValue);
				testSteps.addAll(getTestSteps);
			}
			getTestSteps.clear();
			getTestSteps = clickCheckin(driver, isCheckInOn);
			testSteps.addAll(getTestSteps);

			if (isCheckInOn.equalsIgnoreCase("Yes")) {

				getTestSteps.clear();
				getTestSteps = clickNoCheckInCheckbox(driver, isNoCheckInChecked);
				testSteps.addAll(getTestSteps);
			}
			getTestSteps.clear();
			getTestSteps = clickCheckOut(driver, isCheckOutOn);
			testSteps.addAll(getTestSteps);

			if (isCheckOutOn.equalsIgnoreCase("Yes")) {
				getTestSteps.clear();
				getTestSteps = clickNoCheckOutCheckbox(driver, isNoCheckOutChecked);
				testSteps.addAll(getTestSteps);
			}
		}

		getTestSteps.clear();
		getTestSteps = clickBulkUpdatePopupUpdateButton(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = clickYesUpdateButton(driver);
		testSteps.addAll(getTestSteps);

		return testSteps;
	}

	// Added By Adhnan 09/01/2020
	public ArrayList<String> expandReduceDerivedRateRoomClass(WebDriver driver, String ratePlan, String roomClass,
			String buttonName) throws InterruptedException {

		ArrayList<String> steps = new ArrayList<String>();
		String expandRoomClass = "(//span[text()='" + ratePlan
				+ "']//ancestor::div[@class='DerivedPlan']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
				+ roomClass + "']//following-sibling::span)[1]";
		Wait.WaitForElement(driver, expandRoomClass);
		Wait.waitForElementToBeVisibile(By.xpath(expandRoomClass), driver);
		Wait.waitForElementToBeClickable(By.xpath(expandRoomClass), driver);
		WebElement expandButton = driver.findElement(By.xpath(expandRoomClass));
		Utility.ScrollToElement(expandButton, driver);
		try {
			expandButton.click();

		} catch (Exception e) {
			JavascriptExecutor javaScript = (JavascriptExecutor) driver;
			javaScript.executeScript("window.scrollBy(0,-300)");
			driver.findElement(By.xpath(expandRoomClass)).click();
		}
		steps.add("Click " + buttonName + " Room Class '" + roomClass + "' of derived rate plan '" + ratePlan + "'");
		logger.info("Click " + buttonName + " Room Class '" + roomClass + "' of derived rate plan '" + ratePlan + "'");
		return steps;
	}

	// Added By Adhnan 09/01/2020
	public ArrayList<String> expandReduceBaseRateRoomClass(WebDriver driver, String ratePlan, String roomClass,
			String buttonName) throws InterruptedException {

		ArrayList<String> steps = new ArrayList<String>();
		String expandRoomClass = "(//span[text()='" + ratePlan
				+ "']//ancestor::div[@id='ratePlans']//following-sibling::div[contains(@class,'RateplanDetails')]//div[@class='roomClassName' and text()='"
				+ roomClass + "']//following-sibling::span)[1]";
		Wait.WaitForElement(driver, expandRoomClass);
		Wait.waitForElementToBeVisibile(By.xpath(expandRoomClass), driver);
		Wait.waitForElementToBeClickable(By.xpath(expandRoomClass), driver);
		WebElement expandButton = driver.findElement(By.xpath(expandRoomClass));
		Utility.ScrollToElement(expandButton, driver);
		try {
			expandButton.click();

		} catch (Exception e) {
			JavascriptExecutor javaScript = (JavascriptExecutor) driver;
			javaScript.executeScript("window.scrollBy(0,-300)");
			driver.findElement(By.xpath(expandRoomClass)).click();
		}
		steps.add("Click " + buttonName + " Room Class '" + roomClass + "' of derived rate plan '" + ratePlan + "'");
		logger.info("Click " + buttonName + " Room Class '" + roomClass + "' of derived rate plan '" + ratePlan + "'");
		return steps;
	}

	public void overrideRule(WebDriver driver, String ruleName, int index, String minStay) throws InterruptedException {

		String getRule = "(//div[text()='" + ruleName + "']//following-sibling::div)[" + index
				+ "]//input[@name='rulevalue']";
		// Wait.WaitForElement(driver, getRule);
		Wait.waitForElementToBeVisibile(By.xpath(getRule), driver);
		Wait.waitForElementToBeClickable(By.xpath(getRule), driver);
		WebElement element = driver.findElement(By.xpath(getRule));
		// Utility.ScrollToElement(element, driver);
		String getValue = element.getAttribute("value");
		rateGridLogger.info(getValue);
		Utility.clickThroughJavaScript(driver, element);
		rateGridLogger.info("Click input");

		for (int i = 0; i < getValue.length(); i++) {
			element.sendKeys(Keys.BACK_SPACE);
		}
		element.sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
		element.sendKeys(minStay);
		rateGridLogger.info("Send key : " + minStay);

		changeRuleFocus(driver, ruleName, index + 1);

		Wait.wait3Second();
	}

	public void changeRuleFocus(WebDriver driver, String ruleName, int index) throws InterruptedException {

		String getRule = "(//div[text()='" + ruleName + "']//following-sibling::div)[" + index
				+ "]//input[@name='rulevalue']";
		Wait.WaitForElement(driver, getRule);
		Wait.waitForElementToBeVisibile(By.xpath(getRule), driver);
		Wait.waitForElementToBeClickable(By.xpath(getRule), driver);
		WebElement element = driver.findElement(By.xpath(getRule));
		Utility.clickThroughJavaScript(driver, element);
		rateGridLogger.info("Click input");
		element.clear();
		element.sendKeys(Keys.TAB);

	}

	/*
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 * 
	 * ' Method Name: <clickOnRateTab> ' Description: <This method will click on
	 * rates tab in rate grid > ' Input parameters: <WebDriver driver> ' Return
	 * value : <ArrayList<String> > ' Created By: <Farhan Ghaffar> ' Created On:
	 * <08/27/2020>
	 * 
	 * #########################################################################
	 * #########################################################################
	 * ########################
	 */

	public ArrayList<String> clickOnRateTab(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement_ID(driver, OR.RatesTab);
		Wait.waitForElementToBeVisibile(By.id(OR.RatesTab), driver);
		Wait.waitForElementToBeClickable(By.id(OR.RatesTab), driver);
		elements.RatesTab.click();
		testSteps.add("Clicked on rates tab");
		rateGridLogger.info("Clicked on rates tab");
		return testSteps;
	}

	public boolean verifyLenthOfStayCheckBox(WebDriver driver, ArrayList test_steps, String nameOfComponent)
			throws InterruptedException {
		String path = "//span[text()='" + nameOfComponent + "']//parent::label";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));

		boolean isLengthOfStayChecked = false;
		if (element.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {
			isLengthOfStayChecked = true;
		}
		test_steps.add("Length of Stay restriction is selected? : " + isLengthOfStayChecked);
		return isLengthOfStayChecked;

	}

	public String getMinAndMaxValue(WebDriver driver, String nameOfComponent) throws InterruptedException {

		String path = "//span[text()='" + nameOfComponent + "']//parent::label//..//following-sibling::div//input";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		return element.getAttribute("value");
	}

	public boolean isBookingWindowChecked(WebDriver driver, ArrayList test_steps) {
		boolean isBookingWindow = false;
		String bookingWindowCheckBox = "//span[text()='Booking window']/..//span[contains(@class,'ant-checkbox')]//input";
		isBookingWindow = driver.findElement(By.xpath(bookingWindowCheckBox)).isSelected();
		test_steps.add("Booking window restriction is selected? : " + isBookingWindow);
		return isBookingWindow;
	}

	public boolean isPromoCodeChecked(WebDriver driver, ArrayList test_steps) {
		boolean isBookingWindow = false;
		String bookingWindowCheckBox = "//span[text()='Promo code']/..//span[contains(@class,'ant-checkbox')]//input";
		isBookingWindow = driver.findElement(By.xpath(bookingWindowCheckBox)).isSelected();
		test_steps.add("Promo Code restriction is selected? : " + isBookingWindow);
		return isBookingWindow;
	}

	public String getPromoCode(WebDriver driver, ArrayList test_steps) {
		String promoCode = "";
		String promo = "//input[@id='promoCode']";
		Wait.WaitForElement(driver, promo);
		promoCode = driver.findElement(By.xpath(promo)).getAttribute("value");
		return promoCode;
	}

	public ArrayList<String> getRuleDataValuesForMinStay(WebDriver driver, ArrayList<String> test_steps,
			String roomClassName, String channelName, int days) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String rule = "//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
				+ "')]/../../..//div[text()='Min Stay']/following-sibling::div//input";
		System.out.println("rulePath: " + rule);
		Wait.WaitForElement(driver, rule);
		String ruleValue = "";
		for (int i = 0; i < days; i++) {
			rule = "(//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
					+ "')]/../../..//div[text()='Min Stay']/following-sibling::div//input)[" + (i + 1) + "]";
			ruleValue = driver.findElement(By.xpath(rule)).getAttribute("value");
			if (ruleValue.isEmpty() || ruleValue.equals("")) {
				ruleValue = "0";
			}
			data.add(ruleValue);
		}
		logger.info("Min Stay Rule Values : " + data);
		test_steps.add("Min Stay Rule Values : " + data);
		return data;
	}

	public ArrayList<String> getRuleDataValuesForNoCheckIn(WebDriver driver, ArrayList<String> test_steps,
			String roomClassName, String channelName, int days) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String rule = "//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
				+ "')]/../../..//div[text()='No Check In']/following-sibling::div/div/div";
		Wait.WaitForElement(driver, rule);
		String ruleValue = "";
		for (int i = 0; i <= days; i++) {
			rule = "(//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
					+ "')]/../../..//div[text()='No Check In']/following-sibling::div/div/div)[" + (i + 1) + "]";
			ruleValue = driver.findElement(By.xpath(rule)).getAttribute("class");
			if (ruleValue.contains("noValue")) {
				ruleValue = "No";
			} else {
				ruleValue = "Yes";
			}
			data.add(ruleValue);
		}
		logger.info("No Check in Rule for the Room Class : " + data);
		test_steps.add("No Check in Rule for the Room Class : " + data);
		return data;
	}

	public ArrayList<String> getRuleDataValuesForNoCheckOut(WebDriver driver, ArrayList<String> test_steps,
			String roomClassName, String channelName, int days) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String rule = "//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
				+ "')]/../../..//div[text()='No Check Out']/following-sibling::div/div/div";
		Wait.WaitForElement(driver, rule);
		String ruleValue = "";
		for (int i = 0; i <= days; i++) {
			rule = "(//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
					+ "')]/../../..//div[text()='No Check Out']/following-sibling::div/div/div)[" + (i + 1) + "]";
			ruleValue = driver.findElement(By.xpath(rule)).getAttribute("class");
			if (ruleValue.contains("noValue")) {
				ruleValue = "No";
			} else {
				ruleValue = "Yes";
			}
			data.add(ruleValue);
		}
		logger.info("No Check out Rule for the Room Class : " + data);
		test_steps.add("No Check out Rule for the Room Class : " + data);
		return data;
	}

	public ArrayList<String> clickOnRestrcitionSAndPoliciesTab(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.RestricationsAndPoliciesTab);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.RestricationsAndPoliciesTab), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.RestricationsAndPoliciesTab), driver);
		Utility.ScrollToElement_NoWait(elements.RestricationsAndPoliciesTab, driver);
		elements.RestricationsAndPoliciesTab.click();
		testSteps.add("Click on policies and restrication tab");
		return testSteps;

	}

	public void overrideMinStayValue(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String newNight) throws InterruptedException {
//String path = "(//div[contains(text(),'" + roomClass + "')]/ancestor::div//div[@title='" + channelName + "']"
//				+ "/ancestor::div//div[text()='Min Stay']/parent::div//input)[1]";

		String path = "(//div[contains(text(),'" + roomClass
				+ "')]/../../../..//div[translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ channelName.toUpperCase() + "']/../../..//div[text()='Min Stay']/parent::div//input)[1]";
		System.out.println(path);
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		testSteps.add("Click on Min Stay Box");
		Utility.clearValue(driver, element);
		testSteps.add("Clear Min Stay ");
		element.sendKeys(newNight);
		element.sendKeys(Keys.TAB);
		testSteps.add("Input Min Stay : <b>" + newNight + "</b>");
	}

	public void overrideRuleForNoCheckInAndOut(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channelName, String ruleAppliedOnCheckInOrCheckOut, boolean update) throws InterruptedException {
		// Wait.wait10Second();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

//		String selectedPath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
//				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
//				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
//				+ ruleAppliedOnCheckInOrCheckOut + "']" + "/following::div[1]//div[@class='rt-onHover  enabled']";
//		
//		String blankPath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
//				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
//				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
//				+ ruleAppliedOnCheckInOrCheckOut + "']" + "/following::div[1]//div[@class='rt-onHover  has-noValue enabled']";

		String selectedPath = "(//div[contains(text(),'" + roomClass
				+ "')]/../../../..//div[translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ channelName.toUpperCase() + "']/../../..//div[contains(@class,'RuleHeader') and text()='"

				+ ruleAppliedOnCheckInOrCheckOut + "']" + "/following::div[1]//div[@class='rt-onHover  enabled'])[1]";

		String blankPath = "(//div[contains(text(),'" + roomClass
				+ "')]/../../../..//div[translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
				+ channelName.toUpperCase() + "']/../../..//div[contains(@class,'RuleHeader') and text()='"

				+ ruleAppliedOnCheckInOrCheckOut + "']"
				+ "/following::div[1]//div[@class='rt-onHover  has-noValue enabled'])[1]";

		boolean isSelected = false;

		if (driver.findElements(By.xpath(selectedPath)).size() > 0) {
			isSelected = true;
		} else if (driver.findElements(By.xpath(blankPath)).size() > 0) {
			isSelected = false;
		}

		if (update) {
			if (!isSelected) {
				WebElement element = driver.findElement(By.xpath(blankPath));
				Utility.ScrollToViewElementINMiddle(driver, element);
				Utility.hoverOnElement(driver, element);
				element.click();

			}
		} else {
			if (isSelected) {
				WebElement element = driver.findElement(By.xpath(selectedPath));
				Utility.ScrollToViewElementINMiddle(driver, element);
				Utility.hoverOnElement(driver, element);
				element.click();

			}
		}

		testSteps.add("Update Rule for <b>" + ruleAppliedOnCheckInOrCheckOut
				+ " </b> Message : <b> Rule saved successfully </b>");
		logger.info("Update Rule for " + ruleAppliedOnCheckInOrCheckOut + " Message :  Rule saved successfully");

	}

	public void overrideNightExtraAdultChildRate(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String channel, String nightRate, String extAdult, String extChild) throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass
				+ "')]/ancestor::div/..//following-sibling::div//div[@title='" + channel
				+ "']//parent::div/following::div[1]/div[1][text()!='--']";
		Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(path)), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		try {
			driver.findElement(By.xpath(path)).click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}

		Utility.clearValue(driver, elements.rateGridRoomRate);
		elements.rateGridRoomRate.sendKeys(nightRate);
		testSteps.add("Enter New Rate: <b>" + nightRate + "</b>");
		logger.info("Enter New Rate: " + nightRate);

		Utility.clearValue(driver, elements.rateGridExtraAd);
		elements.rateGridExtraAd.sendKeys(extAdult);
		testSteps.add("Enter Extra Adults is: <b>" + extAdult + "</b>");
		logger.info("Enter Extra Adults is: " + extAdult);

		Utility.clearValue(driver, elements.rateGridExtraCh);
		elements.rateGridExtraCh.sendKeys(extChild);
		testSteps.add("Enter Extra Child is: <b>" + extChild + "</b>");
		logger.info("Enter Extra Child is: " + extChild);

		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
	}

	public void clickExpendRooClass(WebDriver driver, ArrayList<String> testSteps, String roomClass)
			throws InterruptedException {
		String roomClassName = "//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClass.toUpperCase() + "')]//following-sibling::span";

		String path = "//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClass.toUpperCase()
				+ "')]//following-sibling::span[contains(@class,'ir-plus')]";
		Wait.WaitForElement(driver, roomClassName);
		if (driver.findElements(By.xpath(path)).size() > 0) {
			path = "//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClass.toUpperCase() + "')]//following-sibling::span[contains(@class,'ir-plus')]";
			WebElement element = driver.findElement(By.xpath(path));
			Utility.ScrollToElement(element, driver);
			Wait.waitForElementToBeClickable(By.xpath(path), driver, 5);
			Wait.wait5Second();
			Utility.clickThroughJavaScript(driver, element);

			testSteps.add("Expending the room class : " + roomClass);
			logger.info("Expending the room class : " + roomClass);
		}

	}

	public ArrayList getAvailability(WebDriver driver, ArrayList<String> testSteps, String roomClass, int days,
			String CheckInDate) throws Exception {
		String availability = "//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + roomClass.toUpperCase()
				+ "')]/../../..//div[text()='Available']/../following-sibling::div/div";
		ArrayList avail = new ArrayList<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String today = dtf.format(now);
		int count = Utility.getNumberofDays(today, CheckInDate);
		for (int i = 1; i <= days; i++) {
			DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate2 = new Date();
			// convert date to calendar
			Calendar c2 = Calendar.getInstance();
			c2.setTime(currentDate2);
			// manipulate dateF
			c2.add(Calendar.DATE, count + (i - 1)); // same with c.add(Calendar.DAY_OF_MONTH, 1);

			// convert calendar to date
			Date currentDatePlusOne2 = c2.getTime();

			String date = dateFormat2.format(currentDatePlusOne2);

			availability = "(//div[contains(text(),'" + roomClass
					+ "')]/../../..//div[text()='Available']/../following-sibling::div/div)[" + (i) + "]";
			logger.info(availability);
			Wait.WaitForElement(driver, availability);
			Utility.ScrollToElement(driver.findElement(By.xpath(availability)), driver);
			avail.add(driver.findElement(By.xpath(availability)).getText());
			testSteps.add("Getting availability count of  the room class : " + roomClass + " for the date : " + date
					+ " as " + driver.findElement(By.xpath(availability)).getText());
			logger.info("Getting availability count of  the room class : " + roomClass + " for the date : " + date
					+ " as " + driver.findElement(By.xpath(availability)).getText());
		}
		return avail;
	}

	public void overrideNoCheckInCheckOut(WebDriver driver, String ruleName, int index, boolean isClick)
			throws InterruptedException {

		String getCheckinCheckout = "(//div[text()='" + ruleName + "']//following-sibling::div)[" + index
				+ "]//div//child::div[contains(@class, 'rt-onHover')]";
		Wait.WaitForElement(driver, getCheckinCheckout);
		Wait.waitForElementToBeVisibile(By.xpath(getCheckinCheckout), driver);
		Wait.waitForElementToBeClickable(By.xpath(getCheckinCheckout), driver);
		WebElement element = driver.findElement(By.xpath(getCheckinCheckout));
		String expectedClassesForEnabled = "enabled";

		if (isClick) {
			String getNoCheckIn = getNoCheckInCheckOut(driver, ruleName, index);
			if (getNoCheckIn.contains("has-noValue")) {
				logger.info(getCheckinCheckout);
				Utility.ScrollToElement(element, driver);
				try {
					element.click();
				} catch (Exception f) {
					Utility.clickThroughJavaScript(driver, element);
				}
				Wait.wait3Second();
				logger.info("clicked ");
				rateGridLogger.info("checked" + ruleName + "input");
			} else {
				logger.info(getCheckinCheckout);
				logger.info("already  checked");

			}

		} else {
			String getNoCheckIn = getNoCheckInCheckOut(driver, ruleName, index);

			if (getNoCheckIn.contains("has-noValue")) {
				logger.info(getCheckinCheckout);
				logger.info("already  checked");

			} else {
				logger.info(getCheckinCheckout);
				Utility.ScrollToElement(element, driver);
				try {
					element.click();
				} catch (Exception f) {
					Utility.clickThroughJavaScript(driver, element);
				}
				Wait.wait3Second();
				logger.info("Unchecked ");
				rateGridLogger.info("Unchecked " + ruleName + "input");
			}

		}

	}

	public ArrayList<String> overrideMinStayRule(WebDriver driver, String checkIn, String checkout, String ratePlan,
			String roomClass, String timeZone, String channels, String ruleValue, Boolean noCheckIn, Boolean noCheckOut)
			throws InterruptedException, ParseException {

		int days = ESTTimeZone.numberOfDaysBetweenDates(checkIn, checkout);
		ArrayList<String> testSteps = new ArrayList<>();
		String[] splittedClasses = roomClass.split(Utility.DELIM);
		String[] splittedChannels = channels.split(Utility.DELIM);

		int totalCount = days / 20;
		rateGridLogger.info("totalCount: " + totalCount);
		int remingdays = days % 20;
		rateGridLogger.info("remingdays: " + remingdays);

		if (remingdays > 0) {
			totalCount = totalCount + 1;
		}
		rateGridLogger.info("totalCount: " + totalCount);

		int count = 0;
		clickOnCalendarIcon(driver);
		String getMonth = getMonthFromCalendarHeading(driver);
		boolean isMonthEqual = false;
		String getStartDate = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "EEE MMM dd yyyy");
		String expectedMonth = ESTTimeZone.getDateBaseOnDate(checkIn, "MM/dd/yyyy", "MMMM yyyy");

		rateGridLogger.info("expectedMonth: " + expectedMonth);
		String path = "//div[@aria-label='" + getStartDate + "']";
		Wait.wait10Second();
		closeCalendar(driver);
		rateGridLogger.info("Close calendar ");
		String expectedClassesForEnabled = "enabled";

		int k;
		while (isMonthEqual == false) {

			if (expectedMonth.equals(getMonth)) {

				for (k = 0; k < totalCount; k++) {
					for (String classes : splittedClasses) {
						for (String channel : splittedChannels) {

							rateGridLogger.info("In if:" + k);

							if (k == 0) {
								// driver.findElement(By.xpath(path)).click();
								clickRatePlanArrow(driver, testSteps);
								searchRatePlan(driver, ratePlan);
								String getRatPlanName = selectedRatePlan(driver);

								rateGridLogger.info("Before room class (" + classes + ")" + channel);
								expandRoomClass(driver, classes);
								rateGridLogger.info("Expand room class (" + classes + ")");
								expandRoomClass(driver, channel);
								rateGridLogger.info("Expand channel (" + channel + ")");

							}

							// here to put another loop for days verification
							// start date
							int loopCount = 0;
							if (k <= totalCount - 2) {
								loopCount = 20;
							} else {
								loopCount = remingdays;
								isMonthEqual = true;
							}

							for (int j = 1; j <= loopCount; j++) {

								overrideRule(driver, "Min Stay", j, ruleValue);
								logger.info("Set Value for source (" + channel + ") in roomclass (" + classes + ")");
								testSteps.add("Set rule value(" + ruleValue + ") for source (" + channel
										+ ") in roomclass (" + classes + ")");
								overrideNoCheckInCheckOut(driver, "No Check In", j, noCheckIn);
								if (noCheckIn) {
									logger.info("Checked no check in for source (" + channel + ") in roomclass ("
											+ classes + ")");
									testSteps.add("Checked no check in for  source (" + channel + ") in roomclass ("
											+ classes + ")");

								} else {
									logger.info("Unchecked no check in for source (" + channel + ") in roomclass ("
											+ classes + ")");
									testSteps.add("Unchecked no check in for source (" + channel + ") in roomclass ("
											+ classes + ")");

								}
								overrideNoCheckInCheckOut(driver, "No Check Out", j, noCheckOut);
								if (noCheckOut) {
									logger.info("Checked no check out for source (" + channel + ") in roomclass ("
											+ classes + ")");
									testSteps.add("Checked no check out for source (" + channel + ") in roomclass ("
											+ classes + ")");

								} else {
									logger.info("Unchecked no check out for source (" + channel + ") in roomclass ("
											+ classes + ")");
									testSteps.add("Unchecked no check out for source (" + channel + ") in roomclass ("
											+ classes + ")");

								}
								count = count + 1;

							}

							expandRoomClass(driver, classes);
							rateGridLogger.info("Closed room class (" + classes + ")");

							Elements_RatesGrid elements_RatesGrid = new Elements_RatesGrid(driver);
							if (!isMonthEqual) {
								elements_RatesGrid.ClickOnRightArrowOfDate.click();
							}

						}
					}
				}
				// it will be end when all date read
				logger.info("before break");
				break;
			} else {
				rateGridLogger.info("in else");
				clickOnCalendarRightArrow(driver);
				getMonth = getMonthFromCalendarHeading(driver);
				rateGridLogger.info("getMonth: " + getMonth);
			}

		}

		return testSteps;

	}

	public ArrayList<String> overrideMinStayRules(WebDriver driver, String startDate, String endDate, String dateFormat,
			String ratePlan, String classes, String timeZone, String channels, String ruleValue, Boolean noCheckIn,
			Boolean noCheckOut, HashMap<String, Boolean> dayMap) throws InterruptedException, ParseException {

		ArrayList<String> testSteps = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(channels, Utility.DELIM);
		while (token.hasMoreTokens()) {

			String channel = token.nextToken();
			expandRoomClass(driver, channel);
			logger.info("Expand room channel '" + channel);
			int days = ESTTimeZone.numberOfDaysBetweenDates(startDate, endDate);
			logger.info("days : " + days);
			for (int j = 0; j <= days; j++) {
				String day = ESTTimeZone.getNextDateBaseOnPreviouseDate(startDate, dateFormat, "EEE", j, timeZone);
				logger.info("day : " + day);
				if (dayMap.get(day)) {

					overrideRule(driver, "Min Stay", j + 1, ruleValue);
					logger.info("Set Value for source (" + channel + ") in roomclass (" + classes + ")");
					testSteps.add("Set rule value(" + ruleValue + ") for source (" + channel + ") in roomclass ("
							+ classes + ")");
					overrideNoCheckInCheckOut(driver, "No Check In", j + 1, noCheckIn);
					if (noCheckIn) {
						logger.info("Checked no check in for source (" + channel + ") in roomclass (" + classes + ")");
						testSteps.add(
								"Checked no check in for  source (" + channel + ") in roomclass (" + classes + ")");

					} else {
						logger.info(
								"Unchecked no check in for source (" + channel + ") in roomclass (" + classes + ")");
						testSteps.add(
								"Unchecked no check in for source (" + channel + ") in roomclass (" + classes + ")");

					}
					overrideNoCheckInCheckOut(driver, "No Check Out", j + 1, noCheckOut);
					if (noCheckOut) {
						logger.info("Checked no check out for source (" + channel + ") in roomclass (" + classes + ")");
						testSteps.add(
								"Checked no check out for source (" + channel + ") in roomclass (" + classes + ")");

					} else {
						logger.info(
								"Unchecked no check out for source (" + channel + ") in roomclass (" + classes + ")");
						testSteps.add(
								"Unchecked no check out for source (" + channel + ") in roomclass (" + classes + ")");

					}
				}
			}
			expandRoomClass(driver, channel);
			logger.info("Reduce room channel '" + channel);
		}

		return testSteps;

	}

	public ArrayList<String> expandParentRateGrid(WebDriver driver, String className) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		String expandParentRateGrid = "//div[@id='ratePlans']//following-sibling::div[@class='RateplanOverview']//span[@class='ir-acrd-bnt ir-"
				+ className + " pr-5']";
		List<WebElement> expand = driver.findElements(By.xpath(expandParentRateGrid));
		if (expand.size() > 0) {
			Wait.WaitForElement(driver, expandParentRateGrid);
			Wait.waitForElementToBeVisibile(By.xpath(expandParentRateGrid), driver);
			Wait.waitForElementToBeClickable(By.xpath(expandParentRateGrid), driver);
			WebElement element = driver.findElement(By.xpath(expandParentRateGrid));
			Utility.clickThroughJavaScript(driver, element);
			rateGridLogger.info("Click input");

			if (className.equals("plus")) {
				testSteps.add("Expand parent rate grid");
				rateGridLogger.info("Expand parent rate grid");

			} else {
				testSteps.add("Reduce parent rate grid");
				rateGridLogger.info("Reduce parent rate grid");

			}

		} else {
			if (className.equals("plus")) {
				testSteps.add("Parent rate grid already  expanded");
				rateGridLogger.info("Parent rate grid already  expanded");

			} else {
				testSteps.add("Parent rate grid already  reduced");
				rateGridLogger.info("Parent rate grid already  reduced");

			}

		}

		return testSteps;
	}

	public void clickRatesTab(WebDriver driver, ArrayList test_steps) {
		String rates = "//a[@id='rates-and-availability-tabs-tab-RATES_VIEW']/..";
		Wait.WaitForElement(driver, rates);
		driver.findElement(By.xpath(rates)).click();
		test_steps.add("Click on rates tab");
		logger.info("Click on rates tab");
	}

	public ArrayList getBlockOutRoomClass(WebDriver driver, ArrayList<String> testSteps, String RoomClass, int days,
			String Source, String CheckInDate) throws Exception {
		ArrayList<Boolean> al = new ArrayList<Boolean>();
		String blockout = "";

		String availability = "//div[contains(text(),'" + RoomClass
				+ "')]/../../..//div[text()='Available']/../following-sibling::div/div";
		ArrayList avail = new ArrayList<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String today = dtf.format(now);
		int count = Utility.getNumberofDays(today, CheckInDate);

		for (int i = 0; i < days; i++) {

			DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate2 = new Date();
			// convert date to calendar
			Calendar c2 = Calendar.getInstance();
			c2.setTime(currentDate2);
			// manipulate dateF
			c2.add(Calendar.DATE, count + (i)); // same with c.add(Calendar.DAY_OF_MONTH, 1);

			// convert calendar to date
			Date currentDatePlusOne2 = c2.getTime();

			String date = dateFormat2.format(currentDatePlusOne2);

			blockout = "//div[contains(text(),'" + RoomClass + "')]/../../..//div[contains(text(),'" + Source
					+ "')]/../following-sibling::div[" + (i + 1) + "]";
			String classAtt = driver.findElement(By.xpath(blockout)).getAttribute("class");
			if (classAtt.contains("NoBlackedStatus")) {
				al.add(true);
				testSteps.add(
						"Getting the room class : " + RoomClass + " for the date : " + date + " as not Blocked out");
				logger.info(
						"Getting the room class : " + RoomClass + " for the date : " + date + " as not Blocked out");
			} else {
				testSteps.add("Getting the room class : " + RoomClass + " for the date : " + date + " as Blocked out");
				logger.info("Getting the room class : " + RoomClass + " for the date : " + date + " as Blocked out");
				al.add(false);
			}
		}
		return al;
	}

	public String getFolioNameOfRatePlan(WebDriver driver, ArrayList<String> test_steps) {
		String folioName = "//label[text()='Folio Display Name']/following-sibling::input";
		Wait.WaitForElement(driver, folioName);
		folioName = driver.findElement(By.xpath(folioName)).getAttribute("value").trim();
		test_steps.add("Getting Rate plan folio name as : " + folioName);
		logger.info("Getting Rate plan folio name as : " + folioName);
		return folioName;
	}

	public boolean isRatePlanExist(WebDriver driver, String ratePlanName, ArrayList<String> test_steps)
			throws InterruptedException {
		boolean flag;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		clickRateDropDown(driver, test_steps);
		Wait.waitUntilPresenceOfElementLocated(RateGridPage.SearchRatPlan, driver);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SearchRatPlan), driver);
		elements.searchRatPlan.sendKeys(ratePlanName);
		logger.info(ratePlanName);
		Wait.wait1Second();
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SearchRatPlan), driver);
		String noratefound = "//div[text()='No results found']";
		if (driver.findElements(By.xpath(noratefound)).size() > 0) {
			flag = false;
		} else {
			elements.selectRatePlan.click();
			test_steps.add(Utility.addTeststepsBlue("Selecting the Rate Plan : " + ratePlanName));
			flag = true;

			try {
				if (driver.findElements(By.xpath("//*[@id='ratePlans']//span[contains(@class,'ir-plus')]"))
						.size() > 0) {
					Utility.clickThroughJavaScript(driver,
							driver.findElement(By.xpath("//*[@id='ratePlans']//span[contains(@class,'ir-plus')]")));

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return flag;
	}

	public void closeRatePlan(WebDriver driver, String ratePlan, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RateGrid.ratePLanCloseIcon);
		Utility.ScrollToElement(elements.ratePLanCloseIcon, driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RateGrid.ratePLanCloseIcon), driver, 5);
		Utility.clickThroughJavaScript(driver, elements.ratePLanCloseIcon);
		testSteps.add("Close Rate Plan : <b>" + ratePlan + "</b>");
		logger.info("Close Rate Plan :" + ratePlan);
	}

	public HashMap<String, String> getLengthOfStayMinAndMaxValue(WebDriver driver, String componentName, String minName,
			String maxName, HashMap<String, Boolean> isLengthOfStay) throws InterruptedException {
		HashMap<String, String> minMax = new HashMap<String, String>();
		boolean isLengthOfStayCheck = verifyLenthOfStayCheckBox(driver, componentName);
		isLengthOfStay.put("Length of stay", isLengthOfStayCheck);
		logger.info("isLengthOfStayCheck " + isLengthOfStayCheck);
		if (isLengthOfStayCheck) {
			boolean isLeangthOfStayMinChecked = verifyLenthOfStayCheckBox(driver, minName);
			boolean isLeangthOfStayMaxChecked = verifyLenthOfStayCheckBox(driver, maxName);
			isLengthOfStay.put("Min", isLeangthOfStayMinChecked);
			isLengthOfStay.put("Max", isLeangthOfStayMaxChecked);
			if (isLeangthOfStayMinChecked) {
				minMax.put(minName, getMinAndMaxValue(driver, minName));
			} else if (isLeangthOfStayMaxChecked) {
				minMax.put(maxName, getMinAndMaxValue(driver, maxName));
			}

		}
		logger.info("isLengthOfStayCheck1 " + isLengthOfStayCheck);
		return minMax;
	}

	public HashMap<String, String> getBookingWindowValue(WebDriver driver, String componentName, String moreThan,
			String withIn, ArrayList<String> test_steps, HashMap<String, Boolean> bookingWindow)
			throws InterruptedException {
		HashMap<String, String> moreThanWithIn = new HashMap<String, String>();
		boolean isBookingWindowChecked = isBookingWindowChecked(driver, test_steps);
		bookingWindow.put("Booking window", isBookingWindowChecked);
		if (isBookingWindowChecked) {
			boolean isMoreThanChecked = verifyLenthOfStayCheckBox(driver, moreThan);
			boolean isWithInChecked = verifyLenthOfStayCheckBox(driver, withIn);
			bookingWindow.put("More than", isMoreThanChecked);
			bookingWindow.put("Within", isWithInChecked);
			if (isMoreThanChecked) {
				moreThanWithIn.put(moreThan, getMinAndMaxValue(driver, moreThan));
			} else if (isWithInChecked) {
				moreThanWithIn.put(withIn, getMinAndMaxValue(driver, withIn));
			}

		}
		return moreThanWithIn;
	}

	public String getPromocodeValue(WebDriver driver, String componentName, ArrayList<String> test_steps,
			HashMap<String, Boolean> promoCode) throws InterruptedException {
		String minMax = null;
		boolean isPromoCodeChecked = isPromoCodeChecked(driver, test_steps);
		promoCode.put("PromoCode", isPromoCodeChecked);
		if (isPromoCodeChecked) {
			minMax = getPromoCode(driver, test_steps);
		}
		return minMax;
	}

	public void clickRateOfRoomClass(WebDriver driver, String roomClassName, String checkInDate, String checkOutDate)
			throws InterruptedException, ParseException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]" + "//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))]";

		List<WebElement> overRideList = null;
		Wait.WaitForElement(driver, xpath);
		overRideList = driver.findElements(By.xpath(xpath));

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", checkInDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", checkOutDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		Wait.waitUntilPresenceOfElementLocated(OR_RatesGrid.rateGridDay, driver);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}
		for (int i = 0; i < datesList.size(); i++) {
			if (datesList.get(i).equals(dayList.get(i))) {
				Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
				Utility.clickThroughJavaScript(driver, overRideList.get(i));
				logger.info("Click on rate of date: " + datesList.get(i));
				break;
			}
		}
	}

	public void clickRateOfChannel(WebDriver driver, String roomClassName, String channelName, String checkInDate,
			String checkOutDate) throws InterruptedException, ParseException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]"
				+ "//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]" + "//div[@title='"
				+ channelName + "']//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))]";

		List<WebElement> overRideList = null;
		Wait.WaitForElement(driver, xpath);
		overRideList = driver.findElements(By.xpath(xpath));

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", checkInDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", checkOutDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		Wait.waitUntilPresenceOfElementLocated(OR_RatesGrid.rateGridDay, driver);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}
		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
					Utility.clickThroughJavaScript(driver, overRideList.get(i));
					break;
				}
			}
		}
		/*
		 * for (int i = 0; i < overRideList.size(); i++) {
		 * Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
		 * Utility.clickThroughJavaScript(driver, overRideList.get(i)); break; }
		 */
	}

	public void verifyReadOnlyRateExtraAdultAndChild(WebDriver driver, ArrayList<String> testSteps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		boolean isExist = Utility.isAttribtuePresent(elements.rateGridRoomRate, "readonly");
		assertEquals(isExist, true, "Failed to verify Rate field disabled");
		testSteps.add("Verified Rate field Disabled");
		logger.info("Verified Rate field Disabled");
		boolean isExADExist = Utility.isAttribtuePresent(elements.rateGridExtraAd, "readonly");
		assertEquals(isExADExist, true, "Failed to verify Extra Adult field disabled");
		testSteps.add("Verified Extra Adult field Disabled");
		logger.info("Verified Extra Adult field Disabled");
		boolean isExChExist = Utility.isAttribtuePresent(elements.rateGridExtraCh, "readonly");
		assertEquals(isExChExist, true, "Failed to verify Extra Child field disabled");
		testSteps.add("Verified Extra Child field Disabled");
		logger.info("Verified Extra Child field Disabled");
	}

	public void clickCloseRateWindow(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Utility.ScrollToElement(elements.rateGridDanger, driver);
		Utility.clickThroughJavaScript(driver, elements.rateGridDanger);
	}

	public void verifyRateExAdExChDisabledOrEnabled(WebDriver driver, ArrayList<String> testSteps, boolean isExist) {
		assertEquals(Utility.isElementPresent(driver, By.xpath(OR_RatesGrid.rateExAdExChPopup)), isExist,
				"Failed to verify Rate field disabled");
		if (isExist == false) {
			testSteps.add("Verified Rates Extra Adult and Extra Child Disabled");
			logger.info("Verified Rates Extra Adult and Extra Child Disabled");
		} else if (isExist == true) {
			testSteps.add("Verified Rates Extra Adult and Extra Child Enabled");
			logger.info("Verified Rates Extra Adult and Extra Child Enabled");

		}

	}

	public void verifyRoomClass(WebDriver driver, ArrayList<String> roomClassesList, ArrayList<String> testSteps)
			throws InterruptedException {
		ArrayList<String> roomClass = new ArrayList<String>();
		roomClass = getrateGridRoomClass(driver);
		Collections.sort(roomClass);
		Collections.sort(roomClassesList);
		roomClassesList.equals(roomClass);
		testSteps.add("Verified Room Classes on Rate Plan");
		logger.info("Verified Room Classes on Rate Plan");
	}

	public ArrayList<String> getTotalOccupancy(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> totalOccupancy = new ArrayList<String>();
		for (WebElement ele : elements.totalOccupancyList) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			totalOccupancy.add(ele.getText());
		}

		return totalOccupancy;
	}

	public HashMap<String, String> getTotalOccupancy(WebDriver driver, String startDate, String endDate)
			throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> totalOccupancy = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> totalOccupancyList = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		Wait.waitUntilPresenceOfElementLocated(OR_RatesGrid.rateGridDay, driver);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		totalOccupancy = getTotalOccupancy(driver);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					totalOccupancyList.put(dateList.get(j), totalOccupancy.get(i).replaceAll("%", ""));
				}
			}

		}
		logger.info("Total Occupancy as per  Date  " + totalOccupancyList);
		return totalOccupancyList;

	}

	public HashMap<String, String> verifyBulkUpdateRuleForMinStay(WebDriver driver, String startDate, String endDate,
			String roomClass, String channelName, String ruleAppliedOn, String minValue)
			throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> totalOccupancyList = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> dateFromtotalOccupiency = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		HashMap<String, String> totalOccupancy = new HashMap<String, String>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		Wait.waitUntilPresenceOfElementLocated(OR_RatesGrid.rateGridDay, driver);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		totalOccupancy = getTotalOccupancy(driver, startDate, endDate);
		dateFromtotalOccupiency.clear();
		for (Map.Entry<String, String> entry : totalOccupancy.entrySet()) {
			dateFromtotalOccupiency.add(Utility
					.getDateOnlyFromCompleteDate(Utility.convertStringtoDateFormat("dd/MM/yyyy", entry.getKey())));
		}
		logger.info("Total Occupancy are: " + totalOccupancy);
		String pathOne = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/following-sibling::div//input";
		List<WebElement> elementOne = driver.findElements(By.xpath(pathOne));
		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dateFromtotalOccupiency.size(); j++) {
				if (datesList.get(i).equals(dateFromtotalOccupiency.get(j))) {
					Utility.ScrollToElement(elementOne.get(i), driver);
					assertEquals(elementOne.get(i).getAttribute("value"), minValue,
							"Failed to Verify Updated Min Stay");
					logger.info("Verified Updated Min Stay is :" + minValue);
					totalOccupancyList.put(dateList.get(j), elementOne.get(i).getAttribute("value"));
				}
			}

		}
		logger.info("Verified Updated Min Stay  as per  Date  " + totalOccupancyList);
		return totalOccupancyList;

	}

	public HashMap<String, String> verifyBulkUpdateRuleForCheckInCheckout(WebDriver driver, String startDate,
			String endDate, String roomClass, String channelName, String ruleAppliedOn)
			throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> totalOccupancyList = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		ArrayList<String> dateFromtotalOccupiency = new ArrayList<String>();
		HashMap<String, String> totalOccupancy = new HashMap<String, String>();

		Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString("dd/MM/yyyy", d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		Wait.waitUntilPresenceOfElementLocated(OR_RatesGrid.rateGridDay, driver);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		totalOccupancy = getTotalOccupancy(driver, startDate, endDate);
		dateFromtotalOccupiency.clear();
		for (Map.Entry<String, String> entry : totalOccupancy.entrySet()) {
			dateFromtotalOccupiency.add(Utility
					.getDateOnlyFromCompleteDate(Utility.convertStringtoDateFormat("dd/MM/yyyy", entry.getKey())));
		}
		String pathOne = "//div[@class='DatesTable']//div[contains(@title,'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/ancestor::div/..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
				+ ruleAppliedOn + "']" + "/parent::div//div[@class='rt-onHover  enabled']";
		List<WebElement> elementOne = driver.findElements(By.xpath(pathOne));
		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dateFromtotalOccupiency.size(); j++) {
				if (datesList.get(i).equals(dateFromtotalOccupiency.get(j))) {
					Utility.ScrollToElement(elementOne.get(i), driver);
					assertTrue(elementOne.get(i).isDisplayed(), "Failed to Verify Updated Min Stay");
					logger.info("Verified Updated " + ruleAppliedOn + " : is Displayed");
					totalOccupancyList.put(dateList.get(j), ruleAppliedOn + "---is Applied");
				}
			}

		}
		logger.info("Verified Updated " + ruleAppliedOn + "as per  Date  " + totalOccupancyList);
		return totalOccupancyList;

	}

	// Created By Gangotri- Bulk Update- Date -17 Sep 2020
	public void selectBulkUpdateRatePlan(WebDriver driver, String selectionElement, ArrayList<String> testSteps)
			throws InterruptedException {

		String valueArray[] = selectionElement.split("\\|");
		for (int j = 0; j < valueArray.length; j++) {
			String bulkUpdateDropDownPath = "(//label[text()='Rate Plan']//following-sibling::div//div[1])[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			testSteps.add(" Click drop down clicked");
			ratesGridLogger.info("Click drop down clicked");
			String planInputPath = "//label[text()='Rate Plan']//following-sibling::div//input[@role='combobox']";
			Wait.WaitForElement(driver, planInputPath);
			WebElement input = driver.findElement(By.xpath(planInputPath));
			input.sendKeys(valueArray[j]);
			rateGridLogger.info("Entered rate plan name in input : " + valueArray[j]);
			String selectionElementsPath = null;
			if (valueArray[j].contains("All Active") || valueArray[j].contains("All Inactive")) {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]/span[contains(text(),'"
						+ valueArray[j] + "')]";
			} else {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]/span[text()='"
						+ valueArray[j] + "']";
			}
			WebElement selectionElementsList = driver.findElement(By.xpath(selectionElementsPath));
			selectionElementsList.click();
			testSteps.add("Entered : " + valueArray[j]);
			ratesGridLogger.info("Entered : " + valueArray[j]);
		}
	}

	public void selectBulkUpdateRoomClass(WebDriver driver, String roomClass, ArrayList<String> testSteps)
			throws InterruptedException {
		String valueArray[] = roomClass.split("\\|");
		for (int j = 0; j < valueArray.length; j++) {
			String bulkUpdateDropDownPath = "(//label[text()='Room class']//following-sibling::div//div[1])[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			testSteps.add("Click drop down clicked");
			ratesGridLogger.info("Click drop down clicked");
			String planInputPath = "//label[text()='Room class']//following-sibling::div//input[@role='combobox']";
			Wait.WaitForElement(driver, planInputPath);
			WebElement input = driver.findElement(By.xpath(planInputPath));
			input.sendKeys(valueArray[j]);
			rateGridLogger.info("Entered Room Class name in input : " + valueArray[j]);
			String selectionElementsPath = null;
			if (valueArray[j].contains("All")) {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')and contains(text(),'"
						+ valueArray[j] + "')]";
			} else {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')and text()='"
						+ valueArray[j] + "']";
			}
			WebElement selectionElementsList = driver.findElement(By.xpath(selectionElementsPath));
			selectionElementsList.click();
			testSteps.add("Entered : " + valueArray[j]);
			ratesGridLogger.info("Entered : " + valueArray[j]);
		}
	}

	public void selectBulkUpdateSource(WebDriver driver, String source, ArrayList<String> testSteps)
			throws InterruptedException {
		String valueArray[] = source.split("\\|");
		for (int j = 0; j < valueArray.length; j++) {
			String bulkUpdateDropDownPath = "(//label[text()='Source']//following-sibling::div//div[1])[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			testSteps.add("Click drop down clicked");
			ratesGridLogger.info("Click drop down clicked");
			String planInputPath = "//label[text()='Source']//following-sibling::div//input[@role='combobox']";
			Wait.WaitForElement(driver, planInputPath);
			WebElement input = driver.findElement(By.xpath(planInputPath));
			input.sendKeys(valueArray[j]);
			rateGridLogger.info("Entered Source name in input : " + valueArray[j]);
			String selectionElementsPath = null;
			if (valueArray[j].contains("All")) {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')and contains(text(),'"
						+ valueArray[j] + "')]";
			} else {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')and text()='"
						+ valueArray[j] + "']";
			}
			WebElement selectionElementsList = driver.findElement(By.xpath(selectionElementsPath));
			selectionElementsList.click();
			testSteps.add("Entered : " + valueArray[j]);
			ratesGridLogger.info("Entered : " + valueArray[j]);
		}
	}

	public boolean searchAndVerifyRatePlanExist(WebDriver driver, String ratePlanName, boolean click,
			ArrayList<String> testSteps) throws InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		Wait.WaitForElement(driver, RateGridPage.SearchInput);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SearchInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SearchInput), driver);

		elements.SearchInput.sendKeys(ratePlanName);
		testSteps.add("Searched rate plan with name : " + ratePlanName);
		rateGridLogger.info("Searched rate plan with name : " + ratePlanName);

		Wait.WaitForElement(driver, OR_RatesGrid.ratePlanNamesList);
		int size = elements.ratePlanNames.size();
		logger.info(size);
		boolean found = false;
		for (int i = 0; i < size; i++) {
			rateGridLogger.info("rate Plan Names : " + elements.ratePlanNames.get(i).getText());
			Utility.ScrollToElement(elements.ratePlanNames.get(i), driver);

			String getRatePlanName = elements.ratePlanNames.get(i).getText();
			if (getRatePlanName.equals(ratePlanName)) {
				if (click) {
					elements.ratePlanNames.get(i).click();
				}
				found = true;
				break;
			}

		}

		return found;
	}

	public HashMap<String, String> bulkUpdateAvailabilityChange(WebDriver driver, ArrayList test_steps,
			String startDate, String endDate, String roomClass, String toggleOccupancy, String totalOccupancyType,
			String totalOccupancyValue, String source, String action, String monDay, String tuesDay, String wednesDay,
			String thursDay, String friDay, String saturDay, String sunDay)
			throws ParseException, InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> sourcetype = new ArrayList<String>();
		ArrayList<String> actiontype = new ArrayList<String>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		HashMap<String, String> availabilityChange = new HashMap<String, String>();
		if (startDate.equalsIgnoreCase("NA")) {
			logger.info("Current date is selected ");
			test_steps.add("Current date is selected ");
		} else {
			startDate = Utility.parseDate(startDate, "dd/MM/yyyy", "MM/dd/yyyy");
			endDate = Utility.parseDate(endDate, "dd/MM/yyyy", "MM/dd/yyyy");

//					JavascriptExecutor JS = (JavascriptExecutor)driver;
//					JS.executeScript("arguments[0].value='"+startDate+"';", OR.StartDateInput);
//					JS.executeScript("arguments[0].value='"+endDate+"';", OR.EndDateInput);

			startDate(driver, startDate);
			endDate(driver, endDate);
		}
		selectDaysBulkUpdate(driver, monDay, tuesDay, wednesDay, thursDay, friDay, saturDay, sunDay);
		bulkUpdatePoppupTotalOccupancy(driver, toggleOccupancy, totalOccupancyType, totalOccupancyValue);
		selectMulRoomClass(driver, roomClass);

		sourcetype = Utility.splitInputData(source);
		actiontype = Utility.splitInputData(action);
		for (int i = 0; i <= sourcetype.size() - 1; i++) {
			String xpath = "//div[contains(text(),'" + sourcetype.get(i) + "')]//parent::div/div[2]//span[text()='"
					+ actiontype.get(i) + "']";
			Wait.waitForElementToBeVisibile(By.xpath(xpath), driver);
			driver.findElement(By.xpath(xpath)).click();
			logger.info("For the " + roomClass + "room class(es) ,source " + sourcetype.get(i) + "action selected is "
					+ actiontype.get(i));
			test_steps.add("For the " + roomClass + "room class(es) ,source " + sourcetype.get(i)
					+ " action selected is " + actiontype.get(i));
			availabilityChange.put(roomClass + "_" + sourcetype.get(i), actiontype.get(i));
			// logger.info(availabilityChange);
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR.upadteButton), driver);
		driver.findElement(By.xpath(OR.upadteButton)).click();
		driver.findElement(By.xpath(OR.confirmUpdate)).click();
		return availabilityChange;
	}

	public ArrayList<String> selectDaysBulkUpdate(WebDriver driver, String monDay, String tuesDay, String wednesDay,
			String thursDay, String friDay, String saturDay, String sunDay)
			throws ParseException, InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		if (monDay.equalsIgnoreCase("no")) {
			element.monCheckbox.click();
			testSteps.add("Monday checkbox is unselected ");
		} else {
			testSteps.add("Monday checkbox is selected ");

		}

		if (tuesDay.equalsIgnoreCase("no")) {
			element.tueCheckbox.click();
			testSteps.add("Tueday checkbox is unselected ");
		} else {
			testSteps.add("Tueday checkbox is selected ");
		}

		if (wednesDay.equalsIgnoreCase("no")) {
			element.wedCheckbox.click();
			testSteps.add("Wednesday checkbox is unselected ");
		} else {
			testSteps.add("Wednesday checkbox is selected ");
		}

		if (thursDay.equalsIgnoreCase("no")) {
			element.thuCheckbox.click();
			testSteps.add("Thursday checkbox is unselected ");
		} else {
			testSteps.add("Thursday checkbox is selected ");
		}

		if (friDay.equalsIgnoreCase("no")) {
			element.friCheckbox.click();
			testSteps.add("Friday checkbox is unselected ");
		} else {
			testSteps.add("Friday checkbox is selected ");
		}

		if (saturDay.equalsIgnoreCase("no")) {
			element.satCheckbox.click();
			testSteps.add("Saturday checkbox is unselected ");
		} else {
			testSteps.add("Saturday checkbox is selected ");
		}

		if (sunDay.equalsIgnoreCase("no")) {
			element.sunCheckbox.click();
			testSteps.add("Sunday checkbox is unselected ");
		} else {
			testSteps.add("Sunday checkbox is selected ");
		}

		return testSteps;
	}

	public ArrayList<String> getCheckedDays(WebDriver driver, String monDay, String tuesDay, String wednesDay,
			String thursDay, String friDay, String saturDay, String sunDay)
			throws ParseException, InterruptedException {
		ArrayList<String> checkedDays = new ArrayList<>();
		Elements_RatesGrid element = new Elements_RatesGrid(driver);
		if (monDay.equalsIgnoreCase("yes")) {
			checkedDays.add("Mon");
		} else {

			logger.info("Mon is not selected");
		}

		if (tuesDay.equalsIgnoreCase("yes")) {
			checkedDays.add("Tue");
		} else {
			logger.info("Tue is not selected");
		}

		if (wednesDay.equalsIgnoreCase("yes")) {
			checkedDays.add("Wed");
		} else {
			logger.info("Wed is not selected");
		}

		if (thursDay.equalsIgnoreCase("yes")) {
			checkedDays.add("Thu");
		} else {
			logger.info("Thu is not selected");
		}

		if (friDay.equalsIgnoreCase("yes")) {
			checkedDays.add("Fri");
		} else {
			logger.info("Fri is not selected");
		}

		if (saturDay.equalsIgnoreCase("yes")) {
			checkedDays.add("Sat");
		} else {
			logger.info("Sat is not selected");
		}

		if (sunDay.equalsIgnoreCase("yes")) {
			checkedDays.add("Sun");
		} else {
			logger.info("Sun is not selected");
		}

		return checkedDays;
	}

	public ArrayList<String> bulkUpdateMulPoppupRoomClass(WebDriver driver, String roomClass)
			throws InterruptedException, ParseException {

		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> roomclasstype = new ArrayList<>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

		roomclasstype = Utility.splitInputData(roomClass);
		logger.info(roomclasstype.size());
		logger.info(roomclasstype);

		for (int j = 0; j <= roomclasstype.size() - 1; j++) {

			Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupRoomClass);
			Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
			Utility.ScrollToElement_NoWait(ratesGrid.bulkUpdatePopupRoomClass, driver);
			ratesGrid.bulkUpdatePopupRoomClass.click();

			// String roomClassesPath = "(//div[contains(text(),'Select room
			// class(es)')]//parent::div//following::div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
			// String roomClassesPath = "//label[text()='Room
			// class']//following-sibling::div//div[2]/input";
			String roomClassesPath = "(//div[@class='Select-placeholder']//parent::div//following::div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
			List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));
			logger.info("Room Class Before for" + roomclasstype.get(j));
			logger.info("Room Class Size " + roomClassesElement.size());

			for (int i = 0; i < roomClassesElement.size(); i++) {

				String roomClassName = roomClassesElement.get(i).getText().trim();
				testSteps.add("GetRoomClass : " + roomClassName);
				ratesGridLogger.info("GetRoomClass : " + roomClassName);
				logger.info("ROom CLass get:" + roomclasstype.get(j));

				if (roomClassName.contains(roomclasstype.get(j))) {

					roomClassesElement.get(i).click();
					testSteps.add("Entered RoomClass : " + roomclasstype.get(j));
					ratesGridLogger.info("Entered RoomClass : " + roomclasstype.get(j));

					ratesGrid.bulkUpdatePopupCheckoutInput.sendKeys(Keys.TAB);
					testSteps.add("Clicked Tab Key");
					ratesGridLogger.info("Clicked Tab Key");

					break;
				}

			}
		}

		return testSteps;
	}

	public ArrayList<String> bulkUpdateEnterRoomClass(WebDriver driver, String roomClass)
			throws InterruptedException, ParseException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> roomClasses = new ArrayList<>();

		Wait.WaitForElement(driver, OR_RatesGrid.bulkUpdatePopupRoomClass);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.bulkUpdatePopupRoomClass), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.bulkUpdatePopupRoomClass, driver);

		roomClasses = Utility.splitInputData(roomClass);
		logger.info(roomClasses.size());
		logger.info(roomClasses);

		for (int i = 0; i <= roomClasses.size() - 1; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			ratesGrid.bulkUpdatePopupRoomClass.click();
			js.executeScript("arguments[0].setAttribute('value', '" + roomClasses.get(i + 1) + "')",
					ratesGrid.bulkUpdatePopupRoomClass);
			// ratesGrid.bulkUpdatePopupRoomClass.sendKeys(roomClasses.get(i+1));
			ratesGrid.bulkUpdatePopupRoomClass.sendKeys(Keys.TAB);
			testSteps.add("room class(es) are selected");

		}

		return testSteps;
	}

	public ArrayList<String> getSourceNames(WebDriver driver, String source) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "//div[text()='innCenter']//parent::div//following-sibling::div[1]";
		Wait.waitUntilPresenceOfElementLocated(xpath, driver);
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));

		for (WebElement ele : labelValues) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			logger.info(ele.getText());
			data.add(ele.getText());
		}

		return data;
	}

	public void expandRoomClassValues(WebDriver driver, String roomClass) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClass + "')]//parent::div//span)";
		js.executeScript("window.scrollTo(0,0)");
		Utility.hoverOnElement(driver, driver.findElement(By.xpath(xpath)));
		// Utility.ScrollToElement(driver.findElement(By.xpath(xpath)), driver);
		Wait.waitUntilPresenceOfElementLocated(xpath, driver);
		WebElement clickOnExpand = driver.findElement(By.xpath(xpath));
		clickOnExpand.click();

	}

	public HashMap<String, String> getAvailableBlockoutOfRoomClasses(WebDriver driver, ArrayList test_steps,
			String startDate, String endDate, String roomClass, String source)
			throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> sourceNames = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<String, String> availabilityStatus = new HashMap<String, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		ArrayList<String> rClasses = new ArrayList<String>();
		ArrayList<String> roomClassesNames = new ArrayList<String>();

		if (startDate.equalsIgnoreCase("NA")) {
			String sDate = Utility.getCurrentDate("dd/MM/yyyy");
			startDate = Utility.dayNameWithDateIncrement(sDate, 1);
			String eDate = Utility.getCurrentDate("dd/MM/yyyy");
			endDate = Utility.dayNameWithDateIncrement(eDate, 1);
			dateList.add(Utility.dayNameWithDateIncrement(sDate, 1));
			logger.info("Start Date: " + startDate);
			logger.info("End Date: " + endDate);
			test_steps.add("Start Date: " + startDate);
			test_steps.add("End Date: " + endDate);

		} else {
			Date fromDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", startDate);
			logger.info("Start Date: " + fromDate);
			test_steps.add("Start Date: " + fromDate);
			Date toDate = Utility.convertStringtoDateFormat("dd/MM/yyyy", endDate);
			logger.info("End Date: " + toDate);
			test_steps.add("End Date: " + toDate);
			dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
			logger.info("Dates Are: " + dates);

			for (Date d : dates) {
				logger.info(d);
				dayList.add(Utility.getDateOnlyFromCompleteDate(d));
				// logger.info(dayList);
				String dlist = Utility.convertDateFormattoString("dd/MM/yyyy", d);
				dateList.add(Utility.dayNameWithDateIncrement(dlist, 1));
				// logger.info(dateList);
			}

			logger.info("Day's  from Start date and End Date: " + dayList);
			logger.info("Date List from Start date and End Date: " + dateList);

			clickOnCalendarIcon(driver);
			Utility.selectDateFromDatePicker(driver, startDate, "dd/MM/yyyy");
			Wait.waitUntilPresenceOfElementLocated(OR_RatesGrid.rateGridDay, driver);
			for (WebElement ele : elements.rateGridDay) {
				datesList.add(ele.getText());
			}

			logger.info("Rate Grid Day's Are: " + datesList);

		}
		if (roomClass.equalsIgnoreCase("All")) {
			Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);

			Wait.WaitForElement(driver, OR_RatesGrid.ROOM_CLASSES_NAMES);
			Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
			String expectedRoomClass = null;
			String foundRoomClass = null;
			String previousRoomClass = null;
			boolean inorder = true;
			for (int i = 0; i < ratesGrid.ROOM_CLASSES_NAMES.size(); i++) {
				ratesGridLogger.info("Room Class " + (i + 1));
				Utility.ScrollToElement(ratesGrid.ROOM_CLASSES_NAMES.get(i), driver);
				foundRoomClass = ratesGrid.ROOM_CLASSES_NAMES.get(i).getText();
				ratesGridLogger.info("Found Room Class : " + foundRoomClass);
				roomClassesNames.add(foundRoomClass);
			}
		} else {
			roomClassesNames = Utility.splitInputData(roomClass);
		}
		for (String mulroomclasses : roomClassesNames) {
			logger.info("Room Class: " + mulroomclasses);
			test_steps.add("Room Class: " + mulroomclasses);
			// logger.info(mulroomclasses);
			expandRoomClassValues(driver, mulroomclasses);
			// sourceNames = getSourceNames(driver, roomClass);
			ArrayList<String> sources = Utility.splitInputData(source);
			for (String mulsources : sources) {
				try {

					for (int i = 0; i <= dateList.size(); i++) {
						int n = i + 1;
						String xpathAvailableCount = "//div[contains(text(),'Available')]//parent::div//following-sibling::div["
								+ n + "]";
						String availableCount = driver.findElement(By.xpath(xpathAvailableCount)).getText();
						logger.info("For " + mulroomclasses + " room class the respective day  " + dateList.get(i)
								+ " available room count is " + availableCount);
						// test_steps.add("For "+mulroomclasses+ " room class the respective day " +
						// dateList.get(i)+ " available room count is "+availableCount );

						String xpath = "//div[contains(text(),'" + mulsources
								+ "')]//parent::div//following-sibling::div[" + n + "]";
						String availability = driver.findElement(By.xpath(xpath)).getAttribute("class");
						if (availability.contains("NoBlackedStatus")) {
							logger.info("For the " + mulsources + " source, " + mulroomclasses
									+ " room class the respective day  " + dateList.get(i) + " is available");
							availabilityStatus.put(mulroomclasses + "_" + mulsources + "_" + dateList.get(i),
									"available");
							test_steps.add(" " + availabilityStatus);
						} else {
							logger.info("For the " + mulsources + " source, " + mulroomclasses
									+ " room class the respective day  " + dateList.get(i) + " is blocked");
							availabilityStatus.put(mulroomclasses + "_" + mulsources + "_" + dateList.get(i),
									"blocked");
							// logger.info(availabilityStatus);
							test_steps.add(" " + availabilityStatus);
						}

					}

				} catch (Exception e) {
					ratesGridLogger.info("Given date(s) are validated");
				}

			}
			clickMinusroomClass(driver, mulroomclasses);
		}

		return availabilityStatus;

	}

	public ArrayList<String> selectMulRoomClass(WebDriver driver, String roomClass)
			throws InterruptedException, ParseException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> eachRoomClass = new ArrayList<>();

		eachRoomClass = Utility.splitInputData(roomClass);

		for (int j = 0; j < eachRoomClass.size(); j++) {

			Wait.WaitForElement(driver, OR.BulkUpdatePopupRoomClass);
			Wait.waitForElementToBeVisibile(By.xpath(OR.BulkUpdatePopupRoomClass), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR.BulkUpdatePopupRoomClass), driver);
			Utility.ScrollToElement_NoWait(elements.BulkUpdatePopupRoomClass, driver);
			elements.BulkUpdatePopupRoomClass.click();

			String roomClassesPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
			List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));

			for (int i = 0; i < roomClassesElement.size(); i++) {

				String roomClassName = roomClassesElement.get(i).getText().trim();
				// rateGridLogger.info("GetRoomClass : " + roomClassName);

				if (roomClassName.contains(eachRoomClass.get(j))) {

					roomClassesElement.get(i).click();
					testSteps.add("Entered RoomClass : " + eachRoomClass.get(j));
					rateGridLogger.info("Entered RoomClass : " + eachRoomClass.get(j));
					break;

				}

			}
		}

		return testSteps;
	}

	public ArrayList<String> verifyGivenRoomClasses(WebDriver driver, String RoomClasses, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, OR_RatesGrid.ROOM_CLASSES_NAMES);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.ROOM_CLASSES_NAMES), driver);
		String expectedRoomClass = null;
		String foundRoomClass = null;
		String previousRoomClass = null;
		boolean inorder = true;

		String[] eachWord = RoomClasses.split("\\|");
		for (int i = 0; i < eachWord.length; i++) {
			ratesGridLogger.info("Room Class " + (i + 1));
			expectedRoomClass = eachWord[i];
			ratesGridLogger.info("Expected Room Class : " + expectedRoomClass);
			Utility.ScrollToElement(ratesGrid.ROOM_CLASSES_NAMES.get(i), driver);
			foundRoomClass = ratesGrid.ROOM_CLASSES_NAMES.get(i).getText();
			ratesGridLogger.info("Found Room Class : " + foundRoomClass);
			assertEquals(foundRoomClass, expectedRoomClass, "Failed: Room Class missmatched");
			if (previousRoomClass == null) {
				previousRoomClass = foundRoomClass;
			} else {
				if (!(previousRoomClass.charAt(0) <= foundRoomClass.charAt(0))) {
					inorder = false;
				}
			}
			previousRoomClass = foundRoomClass;

		}
		ratesGridLogger.info("Successfully verified All Active Room classes are shown one below another");
		testSteps.add("Successfully verified All Active Room classes are shown one below another");
		if (inorder) {
			ratesGridLogger.info("Successfully verified All Active Room classes are in ascending order.");
			testSteps.add("Successfully verified All Active Room classes are in ascending order.");
		} else {
			ratesGridLogger.info("Failed: Room classes are not in ascending order");
			testSteps.add("Failed: Room classes are not in ascending order");
		}
		return testSteps;
	}

	public HashMap<String, RatesGridChannelRatesRules> getRatesAndRulesForDate(WebDriver driver,
			ArrayList<String> test_steps, String parentRatePlanName, String derivedRatePlanName, boolean isDerivedLevel,
			String roomClassName, String channelName, String StartDate, String EndDate) throws Exception {

		HashMap<String, RatesGridChannelRatesRules> dateWiseRateGridValues = new HashMap<String, RatesGridChannelRatesRules>();

		ArrayList<String> startDateList = Utility.convertTokenToArrayList(StartDate, Utility.DELIM);
		ArrayList<String> endDateList = Utility.convertTokenToArrayList(EndDate, Utility.DELIM);

		for (String startDate : startDateList) {
			int days = Utility.getNumberofDays(startDate, endDateList.get(startDateList.indexOf(startDate))) + 1;

			ArrayList<String> allDatesBW = Utility.getAllDatesBetweenTwoDates(startDate,
					endDateList.get(startDateList.indexOf(startDate)));

			if (!startDate.equals(endDateList.get(startDateList.indexOf(startDate)))) {
				allDatesBW.add(endDateList.get(startDateList.indexOf(startDate)));
			}

			int div = days / 20;
			int piviot = 1;
			if (isDerivedLevel) {
				driver.navigate().refresh();
				clickForRateGridCalender(driver, test_steps);
				selectDateFromDatePicker(driver, allDatesBW.get(0), "dd/MM/yyyy", test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				clickRatePlanArrow(driver, test_steps);
				selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				expandReduceDerivedratePlans(driver, true);
				expandParentRateGrid(driver, "minus");
				expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus");

			} else {
				clickForRateGridCalender(driver, test_steps);
				selectDateFromDatePicker(driver, allDatesBW.get(0), "dd/MM/yyyy", test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				clickRatePlanArrow(driver, test_steps);
				selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				expandReduceDerivedratePlans(driver, false);
				expandParentRateGrid(driver, "plus");
			}

			String roomClassLevelRateXpath = "//div[@class='roomClassName'and contains(@title,'" + roomClassName
					+ "')]//parent::div/parent::div//following-sibling::div//div[@class=' RegularRate']";
			roomClassLevelRateXpath = "//div[@class='roomClassName'and contains(@title,'H Room5')]//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]";
			String xpath = "//div[@class='roomClassName'and contains(@title,'" + roomClassName + "')]"
					+ "//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]" + "//div["
					+ "translate(normalize-space(@title),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ channelName.toUpperCase() + "']//parent::div//following-sibling::div/div[1]";
			//String xpath = "//div[@class='roomClassName'and contains(@title,'" + roomClassName + "')]";
			rateGridLogger.info("XPath inside RateGrid Method::"+xpath);
			//expandRoomClass(driver, roomClassName, endDateList);
			expandRoomClass(driver, roomClassName);
			expandChannel(driver, test_steps, roomClassName, channelName);

			List<WebElement> overRideList = null;

			Wait.WaitForElement(driver, roomClassLevelRateXpath);
			overRideList = driver.findElements(By.xpath(roomClassLevelRateXpath));

			String rule = "//div[contains(text(),'" + roomClassName
					+ "')]/../../..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
					+ channelName.toUpperCase() + "')]/../../..//div[text()='Min Stay']/following-sibling::div//input";
			Wait.WaitForElement(driver, rule);

			int index = 0;
			for (int i = 0; i < days; i++) {

				boolean noCheckIn = false;
				boolean noCheckOut = false;
				String roomRate = "0", extraChildRate = "0", extraAdultRate = "0";

				// Utility.ScrollToViewElementINMiddle(driver, overRideList.get(index));

				if (overRideList.size() > 0) {
					if (!overRideList.get(index).getText().equals("--")) {

//				logger.info(overRideList.get(index));
						Utility.clickThroughJavaScript(driver, overRideList.get(index));
						try {
							roomRate = getRoomRate(driver);
						} catch (Exception e) {
							Utility.clickThroughJavaScript(driver, overRideList.get(index));
							roomRate = getRoomRate(driver);
						}
						extraChildRate = getExtraChild(driver);
						extraAdultRate = getExtraAdult(driver);

						Elements_RatesGrid elements = new Elements_RatesGrid(driver);
						Utility.ScrollToElement(elements.rateGridDanger, driver);
						Utility.clickThroughJavaScript(driver, elements.rateGridDanger);

//				logger.info(roomRate);
//				logger.info(extraChildRate);
//				logger.info(extraAdultRate);

						String rule1 = "(//div[contains(text(),'" + roomClassName
								+ "')]/../../..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
								+ channelName.toUpperCase()
								+ "')]/../../..//div[text()='Min Stay']/following-sibling::div//input)[" + (index + 1)
								+ "]";

//				logger.info(rule1);
						String ruleValue = driver.findElement(By.xpath(rule1)).getAttribute("value");
						if (ruleValue.isEmpty() || ruleValue.equals("")) {
							ruleValue = "0";
						}

//				logger.info(ruleValue);

						String rule2 = "(//div[contains(text(),'" + roomClassName
								+ "')]/../../..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
								+ channelName.toUpperCase()
								+ "')]/../../..//div[text()='No Check In']/following-sibling::div/div/div)["
								+ (index + 1) + "]";

//				logger.info(rule2);
						String noCheckInValue = driver.findElement(By.xpath(rule2)).getAttribute("class");
						if (noCheckInValue.contains("noValue")) {
							noCheckIn = false;
						} else {
							noCheckIn = true;
						}

//				logger.info(noCheckIn);

						String rule3 = "(//div[contains(text(),'" + roomClassName
								+ "')]/../../..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
								+ channelName.toUpperCase()
								+ "')]/../../..//div[text()='No Check Out']/following-sibling::div/div/div)["
								+ (index + 1) + "]";

//				logger.info(rule3);
						String noCheckOutValue = driver.findElement(By.xpath(rule3)).getAttribute("class");
						if (noCheckOutValue.contains("noValue")) {
							noCheckOut = false;
						} else {
							noCheckOut = true;
						}

						boolean isOverriden = false;
						try {
							isOverriden = isRatesOverrideButtonDisplayed(driver);
						} catch (Exception e) {

							// TODO: handle exception
						}

						RatesGridChannelRatesRules ratesRules = new RatesGridChannelRatesRules(ruleValue, noCheckIn,
								noCheckOut, roomRate, extraAdultRate, extraChildRate, isOverriden);
						test_steps.add(ratesRules.toString());
						dateWiseRateGridValues.put(allDatesBW.get(i), ratesRules);

//				logger.info(dateWiseRateGridValues.get(allDatesBW.get(index)));
//				logger.info(dateWiseRateGridValues.size());
					}
				}
				index++;
				if ((i + 1) == (20 * piviot) && div > 0) {

					if (isDerivedLevel) {
						driver.navigate().refresh();
						clickForRateGridCalender(driver, test_steps);
						selectDateFromDatePicker(driver, allDatesBW.get(index), "dd/MM/yyyy", test_steps);
						Wait.waitUntilPageLoadNotCompleted(driver, 50);
						clickRatePlanArrow(driver, test_steps);
						selectAndReturnSpecificRatePlan(driver, test_steps, parentRatePlanName);
						Wait.waitUntilPageLoadNotCompleted(driver, 40);
						expandReduceDerivedratePlans(driver, true);
						expandParentRateGrid(driver, "minus");
						expandReduceDerivedRatePlan(driver, derivedRatePlanName, "plus");
						expandRoomClass(driver, roomClassName);
						try {
							expandChannel(driver, test_steps, roomClassName, channelName);
						} catch (Exception e) {
							logger.info("no Channel found");
						}
					} else {

						clickForRateGridCalender(driver, test_steps);
						selectDateFromDatePicker(driver, allDatesBW.get(index), "dd/MM/yyyy", test_steps);
						Wait.waitUntilPageLoadNotCompleted(driver, 50);
					}
					piviot++;
					div--;
					overRideList = driver.findElements(By.xpath(xpath));
					index = 0;
				}

			}

			collapseRoomClass(driver, test_steps, roomClassName);
			// collapseChannel(driver, test_steps, roomClassName, channelName);

//		for(String date : allDatesBW) {
//			logger.info(date);
//			logger.info(dateWiseRateGridValues.get(date));
//		}
		}
		return dateWiseRateGridValues;

	}

	public ArrayList<String> expandReduceDerivedRatePlan(WebDriver driver, String ratePlanName, String button)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		String xpath = "//span[text()='" + ratePlanName + "']//preceding-sibling::span[contains(@class,'ir-acrd-bnt ir-"
				+ button + "')]";
		if (driver.findElements(By.xpath(xpath)).size() > 0) {
			Wait.WaitForElement(driver, xpath);
			WebElement elementrequired = driver.findElement(By.xpath(xpath));
			Wait.explicit_wait_visibilityof_webelement(elementrequired, driver);
			Wait.explicit_wait_elementToBeClickable(elementrequired, driver);
			Utility.ScrollToElement(elementrequired, driver);
			try {
				elementrequired.click();
			} catch (Exception e) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,-300)");
				Utility.clickThroughJavaScript(driver, elementrequired);
			}
		}
		if (button.equals("plus")) {
			testSteps.add("Expand '" + ratePlanName + "'");
			logger.info("Expand '" + ratePlanName + "'");
		} else if (button.equals("minus")) {
			testSteps.add("Reduce '" + ratePlanName + "'");
			logger.info("Reduce '" + ratePlanName + "'");
		}
		return testSteps;
	}

	public HashMap<String, // RoomClass
			HashMap<String, // Source
					HashMap<String, // date
							RatesGridChannelRatesRules>>> getSourceWiseRatesRules(WebDriver driver,
									ArrayList<String> test_steps, String parentRatePlanName, String derivedRatePlanName,
									boolean isDerivedLevel, String delim, String roomClassName, String channelName,
									String startDate, String endDate) throws Exception {

		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClassName, delim);
		ArrayList<String> channelNameList = Utility.convertTokenToArrayList(channelName, delim);

		HashMap<String, // RoomClass
				HashMap<String, // Source
						HashMap<String, // date
								RatesGridChannelRatesRules>>>

		roomClassWiseSourceRatesRules = new HashMap<String, HashMap<String, HashMap<String, RatesGridChannelRatesRules>>>();

		for (String roomClass : roomClassList) {

			HashMap<String, // Source
					HashMap<String, RatesGridChannelRatesRules>>

			sourceWiseRatesRules = new HashMap<String, HashMap<String, RatesGridChannelRatesRules>>();

			for (String source : channelNameList) {
				HashMap<String, RatesGridChannelRatesRules> dateWiseRateGridValues =

						getRatesAndRulesForDate(driver, test_steps, parentRatePlanName, derivedRatePlanName,
								isDerivedLevel, roomClass, source, startDate, endDate);

				sourceWiseRatesRules.put(source, dateWiseRateGridValues);
				System.out.println(dateWiseRateGridValues);
			}

			roomClassWiseSourceRatesRules.put(roomClass, sourceWiseRatesRules);
		}

		return roomClassWiseSourceRatesRules;
	}

	public void expandReduceDerivedratePlans(WebDriver driver, boolean expand) throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<String>();
		Elements_DerivedRate elements = new Elements_DerivedRate(driver);
		Wait.WaitForElement(driver, DerivedRatePage.DERIVED_RATE_PLAN_ARROW);
		Wait.explicit_wait_visibilityof_webelement(elements.DERIVED_RATE_PLAN_ARROW, driver);
		Wait.waitForElementToBeClickable(By.xpath(DerivedRatePage.DERIVED_RATE_PLAN_ARROW), driver);
		Utility.ScrollToElement(elements.DERIVED_RATE_PLAN_ARROW, driver);
		// JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("window.scrollBy(0,-300)");
		assertTrue(elements.DERIVED_RATE_PLAN_ARROW.isDisplayed(), "Failed derived rate Plan dropdown tab not exist");
		if (expand) {
			if (!elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded")) {
				Wait.wait3Second();
				Utility.clickThroughJavaScript(driver, elements.DERIVEDRATEPLANARROW);
				Wait.wait3Second();
				// assertEquals(elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded"),
				// true,
				// "Failed to Expand Derived rate Plans");

				testSteps.add("Expand Derived rate Plans");
				logger.info("Expand Derived rate Plans");
			}
		} else if (elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded")) {
			Utility.ScrollToElement(elements.DERIVED_RATE_PLAN_ARROW, driver);
			// elements.DERIVED_RATE_PLAN_ARROW.click();
			Utility.clickThroughJavaScript(driver, elements.DERIVEDRATEPLANARROW);
			assertEquals(elements.DERIVED_RATE_PLAN_ARROW.getAttribute("class").contains("expanded"), false,
					"Failed to Reduce Derived rate Plans");
			testSteps.add("Reduce Derived rate Plans");
			logger.info("Reduce Derived rate Plans");
		}
	}

	public ArrayList<String> getRoomRateRoomClassLevel(WebDriver driver, String roomClassName) {

		ArrayList<String> list = new ArrayList<String>();
		String xpath = "//div[@class='roomClassName'and contains(@title,'" + roomClassName
				+ "')]/../..//div[@class=' RegularRate']";
		List<WebElement> eleList = driver.findElements(By.xpath(xpath));
		for (WebElement ele : eleList) {
			list.add(ele.getText());
		}

		return list;
	}

	// Added By Adhnan
	public String getOrVerifyIntervalValue(WebDriver driver, String interval, boolean verify,
			ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.EnterInterval);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.EnterInterval), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.EnterInterval), driver);
		String getValue = ratesGrid.EnterInterval.getAttribute("value");
		testSteps.add("Entered Interval Value: " + interval);
		ratesGridLogger.info("Entered Interval Value: " + interval);
		if (verify) {
			assertEquals(getValue, interval, "Failed: Interval Value missmatched");
		}
		return getValue;
	}

	public ArrayList<String> verifybyDefaultProrateCheckbox(WebDriver driver, boolean isChecked)
			throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.CheckboxProrateForEachSeasonbyDefault);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		if (isChecked) {
			assertTrue(ratesGrid.ProRateCheckbox.getAttribute("class").contains("ant-checkbox-checked"),
					"Failed CheckBox is not checked");
			ratesGridLogger.info("Successfully verified Pro-rate each season checkbox is checked.");
			testSteps.add("Successfully verified Pro-rate each season checkbox is checked.");
		} else {
			assertTrue(!ratesGrid.ProRateCheckbox.getAttribute("class").contains("ant-checkbox-checked"),
					"Failed CheckBox is checked");
			ratesGridLogger.info("Successfully verified Pro-rate each season checkbox is Unchecked.");
			testSteps.add("Successfully verified Pro-rate each season checkbox is Unchecked.");
		}
		return testSteps;
	}

	public ArrayList<String> verifybyDefaultProrateCheckboxMessageDisplayed(WebDriver driver)
			throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.CheckboxProrateForEachSeasonbyDefault);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		assertTrue(ratesGrid.ProRateCheckboxMessage.isDisplayed(), "Failed CheckBox is not checked");
		ratesGridLogger.info("Message : " + ratesGrid.ProRateCheckboxMessage.getText());
		testSteps.add("Message : " + ratesGrid.ProRateCheckboxMessage.getText());
		ratesGridLogger.info("Successfully verified Pro-rate each season checkbox Message is displayed.");
		testSteps.add("Successfully verified Pro-rate each season checkbox Message is displayed.");
		return testSteps;
	}

	/*
	 * public boolean verifyProrateAtSeasonLevel(WebDriver driver, boolean
	 * isProRateStayInRatePlan, boolean isProrateCheckboxCheccked, ArrayList<String>
	 * testSteps) throws InterruptedException { Elements_RatesGrid ratesGrid = new
	 * Elements_RatesGrid(driver); Wait.WaitForElement(driver,
	 * RateGridPage.CheckboxProrate);
	 * Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrate),
	 * driver);
	 * Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrate),
	 * driver); boolean isProRateChecked = false; if (isProrateCheckboxCheccked) {
	 * if (!isProRateStayInRatePlan) { =======
	 * 
	 * public ArrayList<String> verifybyDefaultProrateCheckbox(WebDriver driver,
	 * boolean isChecked) throws InterruptedException { Elements_RatesGrid ratesGrid
	 * = new Elements_RatesGrid(driver); ArrayList<String> testSteps = new
	 * ArrayList<>(); Wait.WaitForElement(driver,
	 * RateGridPage.CheckboxProrateForEachSeasonbyDefault);
	 * Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.
	 * CheckboxProrateForEachSeasonbyDefault), driver);
	 * Wait.waitForElementToBeClickable(By.xpath(RateGridPage.
	 * CheckboxProrateForEachSeasonbyDefault), driver); if (isChecked) {
	 * assertTrue(ratesGrid.ProRateCheckbox.getAttribute("class").contains(
	 * "ant-checkbox-checked"), "Failed CheckBox is not checked"); ratesGridLogger.
	 * info("Successfully verified Pro-rate each season checkbox is checked.");
	 * testSteps.
	 * add("Successfully verified Pro-rate each season checkbox is checked."); }
	 * else { assertTrue(!ratesGrid.ProRateCheckbox.getAttribute("class").contains(
	 * "ant-checkbox-checked"), "Failed CheckBox is checked"); ratesGridLogger.
	 * info("Successfully verified Pro-rate each season checkbox is Unchecked.");
	 * testSteps.
	 * add("Successfully verified Pro-rate each season checkbox is Unchecked."); }
	 * return testSteps; }
	 * 
	 * public ArrayList<String>
	 * verifybyDefaultProrateCheckboxMessageDisplayed(WebDriver driver) throws
	 * InterruptedException { Elements_RatesGrid ratesGrid = new
	 * Elements_RatesGrid(driver); ArrayList<String> testSteps = new ArrayList<>();
	 * Wait.WaitForElement(driver,
	 * RateGridPage.CheckboxProrateForEachSeasonbyDefault);
	 * Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.
	 * CheckboxProrateForEachSeasonbyDefault), driver);
	 * Wait.waitForElementToBeClickable(By.xpath(RateGridPage.
	 * CheckboxProrateForEachSeasonbyDefault), driver);
	 * assertTrue(ratesGrid.ProRateCheckboxMessage.isDisplayed(),
	 * "Failed CheckBox is not checked"); ratesGridLogger.info("Message : " +
	 * ratesGrid.ProRateCheckboxMessage.getText()); testSteps.add("Message : " +
	 * ratesGrid.ProRateCheckboxMessage.getText()); ratesGridLogger.
	 * info("Successfully verified Pro-rate each season checkbox Message is displayed."
	 * ); testSteps.
	 * add("Successfully verified Pro-rate each season checkbox Message is displayed."
	 * ); return testSteps; }
	 * 
	 * public boolean verifyProrateAtSeasonLevel(WebDriver driver, boolean
	 * isProRateStayInRatePlan, boolean isProrateCheckboxCheccked, ArrayList<String>
	 * testSteps) throws InterruptedException { Elements_RatesGrid ratesGrid = new
	 * Elements_RatesGrid(driver); Wait.WaitForElement(driver,
	 * RateGridPage.CheckboxProrate);
	 * Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrate),
	 * driver);
	 * Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrate),
	 * driver); boolean isProRateChecked = false; if (isProrateCheckboxCheccked) {
	 * if (!isProRateStayInRatePlan) { ratesGrid.CheckboxProrate.click(); //
	 * testSteps.add("Checked prorate stay in season"); isProRateChecked = true; }
	 * 
	 * } if (!isProrateCheckboxCheccked) { if (!isProRateStayInRatePlan) { //
	 * ratesGrid.CheckboxProrate.click(); //
	 * testSteps.add("Pro rate stay in season is checked? "+); } }
	 * 
	 * if (!isProrateCheckboxCheccked) { if (isProRateStayInRatePlan) {
	 * ratesGrid.CheckboxProrate.click(); //
	 * testSteps.add("Checked prorate stay in season"); isProRateChecked = true; }
	 * 
	 * }
	 * 
	 * if (isProrateCheckboxCheccked) { if (isProRateStayInRatePlan) { //
	 * ratesGrid.CheckboxProrate.click(); //
	 * testSteps.add("Checked prorate stay in season"); isProRateChecked = true; }
	 * 
	 * } return isProRateChecked; }
	 */

	public ArrayList<String> enterRoomClassRates(WebDriver driver, String RoomClasses, String baseRate,
			String isAdditionalAdultChild, String maxAdult, String MaxPersons, String adultRate, String childRate,
			ArrayList<String> testSteps) throws InterruptedException {

		String[] splitRoomClassName = RoomClasses.split(Utility.DELIM);
		String[] splitRatePerNight = baseRate.split(Utility.DELIM);
		String[] splitadultRate = adultRate.split(Utility.DELIM);
		String[] splitchildRate = childRate.split(Utility.DELIM);
		String[] splitMaxAdult = maxAdult.split(Utility.DELIM);
		String[] splitMaxPersons = MaxPersons.split(Utility.DELIM);

		for (int i = 0; i < splitRoomClassName.length; i++) {
			String enterRate = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + splitRoomClassName[i]
					+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";

			logger.info(enterRate);
			List<WebElement> element = driver.findElements(By.xpath(enterRate));
			Utility.ScrollToElement_NoWait(element.get(0), driver);

			testSteps.add("Enter base rate, additional adult and child for room class: " + splitRoomClassName[i]);
			element.get(0).click();
			clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
			logger.info("RatePerNight : " + splitRatePerNight[i]);
			element.get(0).sendKeys(splitRatePerNight[i]);
			testSteps.add("Enter base rate:  " + splitRatePerNight[i]);

			if (Boolean.parseBoolean(isAdditionalAdultChild)) {

				// testSteps.add("Enter base rate, additional adult and child
				// for room class: " + splitRoomClassName[i]);
				logger.info("Max Adult: " + splitMaxAdult[i]);
				element.get(1).click();
				clearInputField(driver, element.get(1), element.get(1).getAttribute("value"));
				element.get(1).sendKeys(splitMaxAdult[i]);
				testSteps.add("Max adults: " + splitMaxAdult[i]);

				logger.info("Max Person: " + splitMaxPersons[i]);
				element.get(2).click();
				clearInputField(driver, element.get(2), element.get(2).getAttribute("value"));
				element.get(2).sendKeys(splitMaxPersons[i]);
				testSteps.add("Max persons: " + splitMaxPersons[i]);

				logger.info("Adult rate: " + splitadultRate[i]);
				element.get(3).click();
				clearInputField(driver, element.get(3), element.get(3).getAttribute("value"));
				element.get(3).sendKeys(splitadultRate[i]);
				testSteps.add("Add.adult/interval: " + splitadultRate[i]);

				logger.info("child rate: " + splitchildRate[i]);
				element.get(4).click();
				clearInputField(driver, element.get(4), element.get(4).getAttribute("value"));
				element.get(4).sendKeys(splitchildRate[i]);
				testSteps.add("Add.child/interval: " + splitchildRate[i]);

			}

		}

		return testSteps;
	}

	public ArrayList<String> enterProRateValues(WebDriver driver, String isAdditionalAdultChild,
			boolean isProRateChecked, String ProRateRoomClassName, String IsCustomPerNight, String CustomRoomClassName,
			String customRatePerNight,

			String CustomRateAdultdPerNight, String CustomRateChildPerNight, String isCustomRatePerNightAdultandChild,
			ArrayList<String> testSteps) throws InterruptedException {

		logger.info("customRatePerNight in rate grid: " + customRatePerNight);

		String[] splitCustomRoomClass = CustomRoomClassName.split(Utility.DELIM);
		String[] splitCustomRatePerNight = customRatePerNight.split(Utility.DELIM);
		String[] splitCustomRateAdultdPerNight = CustomRateAdultdPerNight.split(Utility.DELIM);
		String[] splitCustomRateChildPerNight = CustomRateChildPerNight.split(Utility.DELIM);
		String[] splitIsCustomPerNight = IsCustomPerNight.split(Utility.DELIM);
		String[] splitisCustomRatePerNightAdultandChild = isCustomRatePerNightAdultandChild.split(Utility.DELIM);
		String[] proRateRoomClasses = ProRateRoomClassName.split(Utility.DELIM);

		if (isProRateChecked) {
			for (int customerPerNight = 0; customerPerNight < splitIsCustomPerNight.length; customerPerNight++) {
				// clickOnAdditionalChargForAdultsAndChildern(driver,
				// Boolean.parseBoolean(splitisAdditionalAdultChild[customerPerNight]));

				if (Boolean.parseBoolean(splitIsCustomPerNight[customerPerNight])) {
					for (int i = 0; i < splitCustomRoomClass.length; i++) {

						testSteps.add("OverRide rate per night for room class: " + splitCustomRoomClass[i]);
						String pathOfRatePerNight = "//li[contains(@class,'IntervalRatePlan')]//span[text()='"
								+ splitCustomRoomClass[i]
								+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";
						List<WebElement> ratePerNight = driver.findElements(By.xpath(pathOfRatePerNight));

						logger.info("splitCustomRatePerNight: " + splitCustomRatePerNight[i]);

						ratePerNight.get(1).click();
						Wait.wait1Second();
						clearInputField(driver, ratePerNight.get(1), ratePerNight.get(1).getAttribute("value"));
						Wait.wait1Second();
						ratePerNight.get(1).sendKeys(splitCustomRatePerNight[i]);
						testSteps.add("Custom Rate Per Night " + splitCustomRatePerNight[i]);

						logger.info("Boolean.parseBoolean(isCustomRatePerNightAdultandChild): "
								+ Boolean.parseBoolean(isAdditionalAdultChild));
						if (Boolean.parseBoolean(isAdditionalAdultChild)
								&& Boolean.parseBoolean(splitisCustomRatePerNightAdultandChild[i])) {

							ratePerNight.get(2).click();
							clearInputField(driver, ratePerNight.get(2), ratePerNight.get(2).getAttribute("value"));
							ratePerNight.get(2).sendKeys(splitCustomRateAdultdPerNight[i]);
							testSteps.add("Custom Add.adult/night " + splitCustomRateAdultdPerNight[i]);

							ratePerNight.get(3).click();
							clearInputField(driver, ratePerNight.get(3), ratePerNight.get(3).getAttribute("value"));
							ratePerNight.get(3).sendKeys(splitCustomRateChildPerNight[i]);
							testSteps.add("Custom Add.child/night " + splitCustomRateChildPerNight[i]);

						}

					}

				}
			}

		}

		return testSteps;
	}

	public ArrayList<String> unCheckProRateValuesOfRoomClasses(WebDriver driver, boolean isProRateChecked,
			String seasonProRateUncheckedRoomClassName, ArrayList<String> testSteps) throws InterruptedException {

		String[] proRateInRoomClass = seasonProRateUncheckedRoomClassName.split(Utility.DELIM);

		if (isProRateChecked) {
			testSteps.add("UNCheck  pro rate stay at room class level");
			logger.info("proRateInRoomClass: " + proRateInRoomClass.length);
			for (int i = 0; i < (proRateInRoomClass.length - 1); i++) {
				logger.info("proRateInRoomClass: " + proRateInRoomClass[i]);

				String roomClass = proRateInRoomClass[i].trim();
				String pathOfRatePerNight = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + roomClass
						+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//span";
				System.out.println(pathOfRatePerNight);
				List<WebElement> proRateAtClass = driver.findElements(By.xpath(pathOfRatePerNight));
				proRateAtClass.get(0).click();
				testSteps.add("Unchecked pro rate stay in room class: " + roomClass);
				logger.info("Unchecked pro rate stay in room class: " + roomClass);
			}
		}

		return testSteps;
	}

	public ArrayList<String> verifyRoomClassProRateCheckBoxChecked(WebDriver driver, boolean checked,
			String roomClassName, ArrayList<String> testSteps) throws InterruptedException {

		String[] proRateInRoomClass = roomClassName.split(Utility.DELIM);

		int i = 0;
		logger.info("proRateInRoomClass: " + proRateInRoomClass[i]);

		String roomClass = proRateInRoomClass[i].trim();
		String pathOfRatePerNight = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + roomClass
				+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//span";
		System.out.println(pathOfRatePerNight);
		List<WebElement> proRateAtClass = driver.findElements(By.xpath(pathOfRatePerNight));

		if (checked) {
			assertTrue(proRateAtClass.size() != 0, "Failed: Season Pro Rate CheckBox is not checked");
			testSteps.add("Successfully Verified that Pro rate CheckBox is checked ");
			logger.info("Successfully Verified that Pro rate CheckBox is checked ");
		} else {
			assertTrue(proRateAtClass.size() == 0, "Failed: Season Pro Rate CheckBox is checked");
			testSteps.add("Successfully Verified that Pro rate CheckBox is not checked ");
			logger.info("Successfully Verified that Pro rate CheckBox is not checked ");
		}

		return testSteps;
	}
	/*
	 * // Added By Adhnan 09/25/2020
	 * 
	 * public ArrayList<String> enterProRateValues(WebDriver driver, String
	 * isAdditionalAdultChild, boolean isProRateChecked, String
	 * ProRateRoomClassName, String IsCustomPerNight, String CustomRoomClassName,
	 * String customRatePerNight,
	 * 
	 * String CustomRateAdultdPerNight, String CustomRateChildPerNight, String
	 * isCustomRatePerNightAdultandChild, ArrayList<String> testSteps) throws
	 * InterruptedException {
	 * 
	 * logger.info("customRatePerNight in rate grid: " + customRatePerNight);
	 * 
	 * String[] splitCustomRoomClass = CustomRoomClassName.split(Utility.DELIM);
	 * String[] splitCustomRatePerNight = customRatePerNight.split(Utility.DELIM);
	 * String[] splitCustomRateAdultdPerNight =
	 * CustomRateAdultdPerNight.split(Utility.DELIM); String[]
	 * splitCustomRateChildPerNight = CustomRateChildPerNight.split(Utility.DELIM);
	 * String[] splitIsCustomPerNight = IsCustomPerNight.split(Utility.DELIM);
	 * String[] splitisCustomRatePerNightAdultandChild =
	 * isCustomRatePerNightAdultandChild.split(Utility.DELIM); String[]
	 * proRateRoomClasses = ProRateRoomClassName.split(Utility.DELIM);
	 * 
	 * if (isProRateChecked) { for (int customerPerNight = 0; customerPerNight <
	 * splitIsCustomPerNight.length; customerPerNight++) { //
	 * clickOnAdditionalChargForAdultsAndChildern(driver, //
	 * Boolean.parseBoolean(splitisAdditionalAdultChild[customerPerNight]));
	 * 
	 * if (Boolean.parseBoolean(splitIsCustomPerNight[customerPerNight])) { for (int
	 * i = 0; i < splitCustomRoomClass.length; i++) {
	 * 
	 * testSteps.add("OverRide rate per night for room class: " +
	 * splitCustomRoomClass[i]); String pathOfRatePerNight =
	 * "//li[contains(@class,'IntervalRatePlan')]//span[text()='" +
	 * splitCustomRoomClass[i] +
	 * "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";
	 * List<WebElement> ratePerNight =
	 * driver.findElements(By.xpath(pathOfRatePerNight));
	 * 
	 * logger.info("splitCustomRatePerNight: " + splitCustomRatePerNight[i]);
	 * 
	 * ratePerNight.get(1).click(); Wait.wait1Second(); clearInputField(driver,
	 * ratePerNight.get(1), ratePerNight.get(1).getAttribute("value"));
	 * Wait.wait1Second(); ratePerNight.get(1).sendKeys(splitCustomRatePerNight[i]);
	 * testSteps.add("Custom Rate Per Night " + splitCustomRatePerNight[i]);
	 * 
	 * logger.info("Boolean.parseBoolean(isCustomRatePerNightAdultandChild): " +
	 * Boolean.parseBoolean(isAdditionalAdultChild)); if
	 * (Boolean.parseBoolean(isAdditionalAdultChild) &&
	 * Boolean.parseBoolean(splitisCustomRatePerNightAdultandChild[i])) {
	 * 
	 * ratePerNight.get(2).click(); clearInputField(driver, ratePerNight.get(2),
	 * ratePerNight.get(2).getAttribute("value"));
	 * ratePerNight.get(2).sendKeys(splitCustomRateAdultdPerNight[i]);
	 * testSteps.add("Custom Add.adult/night " + splitCustomRateAdultdPerNight[i]);
	 * 
	 * ratePerNight.get(3).click(); clearInputField(driver, ratePerNight.get(3),
	 * ratePerNight.get(3).getAttribute("value"));
	 * ratePerNight.get(3).sendKeys(splitCustomRateChildPerNight[i]);
	 * testSteps.add("Custom Add.child/night " + splitCustomRateChildPerNight[i]);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * return testSteps; }
	 * 
	 * public ArrayList<String> unCheckProRateValuesOfRoomClasses(WebDriver driver,
	 * boolean isProRateChecked, String seasonProRateUncheckedRoomClassName,
	 * ArrayList<String> testSteps) throws InterruptedException {
	 * 
	 * String[] proRateInRoomClass =
	 * seasonProRateUncheckedRoomClassName.split(Utility.DELIM); >>>>>>> develop
	 * 
	 * if (isProRateChecked) {
	 * testSteps.add("UNCheck  pro rate stay at room class level");
	 * logger.info("proRateInRoomClass: " + proRateInRoomClass.length); for (int i =
	 * 0; i < (proRateInRoomClass.length - 1); i++) {
	 * logger.info("proRateInRoomClass: " + proRateInRoomClass[i]);
	 * 
	 * String roomClass = proRateInRoomClass[i].trim(); String pathOfRatePerNight =
	 * "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + roomClass +
	 * "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//span";
	 * System.out.println(pathOfRatePerNight); List<WebElement> proRateAtClass =
	 * driver.findElements(By.xpath(pathOfRatePerNight));
	 * proRateAtClass.get(0).click();
	 * testSteps.add("Unchecked pro rate stay in room class: " + roomClass);
	 * logger.info("Unchecked pro rate stay in room class: " + roomClass); } <<<<<<<
	 * HEAD
	 * 
	 * } return testSteps; }
	 * 
	 * public ArrayList<String> clickSaveratePlan(WebDriver driver) throws
	 * InterruptedException { Elements_RatesGrid elements = new
	 * Elements_RatesGrid(driver); ArrayList<String> testSteps = new ArrayList<>();
	 * 
	 * for (int i = 0; i < 10; i++) {
	 * 
	 * if (elements.SaveRatePlanButton.isEnabled()) {
	 * logger.info("Button is Still Enabed"); try { Wait.WaitForElement(driver,
	 * RateGridPage.SaveRatePlanButton);
	 * Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SaveRatePlanButton),
	 * driver);
	 * Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SaveRatePlanButton),
	 * driver); Utility.ScrollToElement(elements.SaveRatePlanButton, driver);
	 * elements.SaveRatePlanButton.click();
	 * testSteps.add("Click on save rate plan buttton"); Wait.wait5Second(); } catch
	 * (Exception e) {
	 * 
	 * } } else { logger.info("Button is disabed"); break; } } return testSteps;
	 * 
	 * }
	 */

	public ArrayList<String> enterRoomClassRates(WebDriver driver, String RoomClasses, String baseRate,
			String isAdditionalAdultChild, String maxAdult, String MaxPersons, String adultRate, String childRate,

			boolean isProRateChecked, String isProRateInRoomClass, String ProRateRoomClassName,

			String IsCustomPerNight, String CustomRoomClassName, String customRatePerNight,

			String CustomRateAdultdPerNight, String CustomRateChildPerNight, String isCustomRatePerNightAdultandChild,
			ArrayList<String> testSteps) throws InterruptedException {

		logger.info("customRatePerNight in rate grid: " + customRatePerNight);

		String[] splitRoomClassName = RoomClasses.split("\\|");
		String[] splitRatePerNight = baseRate.split(",");
		String[] splitadultRate = adultRate.split(",");
		String[] splitchildRate = childRate.split(",");
		String[] splitMaxAdult = maxAdult.split(",");
		String[] splitMaxPersons = MaxPersons.split(",");

		String[] splitCustomRoomClass = CustomRoomClassName.split(",");
		String[] splitCustomRatePerNight = customRatePerNight.split(",");
		String[] splitCustomRateAdultdPerNight = CustomRateAdultdPerNight.split(",");
		String[] splitCustomRateChildPerNight = CustomRateChildPerNight.split(",");
		String[] splitIsCustomPerNight = IsCustomPerNight.split(",");
		String[] splitisCustomRatePerNightAdultandChild = isCustomRatePerNightAdultandChild.split(",");
		String[] proRateInRoomClass = isProRateInRoomClass.split(",");
		String[] proRateRoomClasses = ProRateRoomClassName.split(",");

		for (int i = 0; i < splitRoomClassName.length; i++) {
			String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + splitRoomClassName[i]
					+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";

			logger.info(enterRate);
			List<WebElement> element = driver.findElements(By.xpath(enterRate));
			Utility.ScrollToElement_NoWait(element.get(0), driver);

			testSteps.add("Enter base rate, additional adult and child for room class: " + splitRoomClassName[i]);
			element.get(0).click();
			clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
			logger.info("RatePerNight : " + splitRatePerNight[i]);
			element.get(0).sendKeys(splitRatePerNight[i]);
			testSteps.add("Enter base rate:  " + splitRatePerNight[i]);

			if (Boolean.parseBoolean(isAdditionalAdultChild)) {

				// testSteps.add("Enter base rate, additional adult and child
				// for room class: " + splitRoomClassName[i]);
				logger.info("Max Adult: " + splitMaxAdult[i]);
				element.get(1).click();
				clearInputField(driver, element.get(1), element.get(1).getAttribute("value"));
				element.get(1).sendKeys(splitMaxAdult[i]);
				testSteps.add("Max adults: " + splitMaxAdult[i]);

				logger.info("Max Person: " + splitMaxPersons[i]);
				element.get(2).click();
				clearInputField(driver, element.get(2), element.get(2).getAttribute("value"));
				element.get(2).sendKeys(splitMaxPersons[i]);
				testSteps.add("Max persons: " + splitMaxPersons[i]);

				logger.info("Adult rate: " + splitadultRate[i]);
				element.get(3).click();
				clearInputField(driver, element.get(3), element.get(3).getAttribute("value"));
				element.get(3).sendKeys(splitadultRate[i]);
				testSteps.add("Add.adult/interval: " + splitadultRate[i]);

				logger.info("child rate: " + splitchildRate[i]);
				element.get(4).click();
				clearInputField(driver, element.get(4), element.get(4).getAttribute("value"));
				element.get(4).sendKeys(splitchildRate[i]);
				testSteps.add("Add.child/interval: " + splitchildRate[i]);

			}

		}

		if (isProRateChecked) {
			for (int customerPerNight = 0; customerPerNight < splitIsCustomPerNight.length; customerPerNight++) {
				// clickOnAdditionalChargForAdultsAndChildern(driver,
				// Boolean.parseBoolean(splitisAdditionalAdultChild[customerPerNight]));

				if (Boolean.parseBoolean(splitIsCustomPerNight[customerPerNight])) {
					for (int i = 0; i < splitCustomRoomClass.length; i++) {

						testSteps.add("OverRide rate per night for room class: " + splitCustomRoomClass[i]);
						String pathOfRatePerNight = "//li[@class='IntervalRatePlan line']//span[text()='"
								+ splitCustomRoomClass[i]
								+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";
						List<WebElement> ratePerNight = driver.findElements(By.xpath(pathOfRatePerNight));

						logger.info("splitCustomRatePerNight: " + splitCustomRatePerNight[i]);

						ratePerNight.get(1).click();
						Wait.wait1Second();
						clearInputField(driver, ratePerNight.get(1), ratePerNight.get(1).getAttribute("value"));
						Wait.wait1Second();
						ratePerNight.get(1).sendKeys(splitCustomRatePerNight[i]);
						testSteps.add("Custom Rate Per Night " + splitCustomRatePerNight[i]);

						logger.info("Boolean.parseBoolean(isCustomRatePerNightAdultandChild): "
								+ Boolean.parseBoolean(isAdditionalAdultChild));
						if (Boolean.parseBoolean(isAdditionalAdultChild)
								&& Boolean.parseBoolean(splitisCustomRatePerNightAdultandChild[i])) {

							ratePerNight.get(2).click();
							clearInputField(driver, ratePerNight.get(2), ratePerNight.get(2).getAttribute("value"));
							ratePerNight.get(2).sendKeys(splitCustomRateAdultdPerNight[i]);
							testSteps.add("Custom Add.adult/night " + splitCustomRateAdultdPerNight[i]);

							ratePerNight.get(3).click();
							clearInputField(driver, ratePerNight.get(3), ratePerNight.get(3).getAttribute("value"));
							ratePerNight.get(3).sendKeys(splitCustomRateChildPerNight[i]);
							testSteps.add("Custom Add.child/night " + splitCustomRateChildPerNight[i]);

						}

					}

				}
			}

			testSteps.add("Verify pro rate stay at room class level");
			logger.info("proRateInRoomClass: " + proRateInRoomClass.length);
			for (int i = 0; i < proRateInRoomClass.length; i++) {
				logger.info("proRateInRoomClass: " + proRateInRoomClass[i]);

				if (Boolean.parseBoolean(proRateInRoomClass[i])) {
					// for room class
					for (int j = 0; j < proRateRoomClasses.length; j++) {

						String roomClass = proRateRoomClasses[i].trim();
						String pathOfRatePerNight = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClass
								+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//span";
						System.out.println(pathOfRatePerNight);
						List<WebElement> proRateAtClass = driver.findElements(By.xpath(pathOfRatePerNight));
						proRateAtClass.get(0).click();
						testSteps.add("Unchecked pro rate stay in room class: " + roomClass);
						logger.info("Unchecked pro rate stay in room class: " + roomClass);
					}
				}

			}
		}

		return testSteps;
	}

	public ArrayList<String> clickSaveratePlan(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			if (elements.SaveRatePlanButton.isEnabled()) {
				logger.info("Button is Still Enabed");
				try {
					Wait.WaitForElement(driver, RateGridPage.SaveRatePlanButton);
					Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SaveRatePlanButton), driver);
					Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SaveRatePlanButton), driver);
					Utility.ScrollToElement(elements.SaveRatePlanButton, driver);
					elements.SaveRatePlanButton.click();
					testSteps.add("Click on save rate plan buttton");
					Wait.wait5Second();
				} catch (Exception e) {

				}
			} else {
				logger.info("Button is disabed");
				break;
			}
		}
		return testSteps;

	}

	public void enterRoomClassRatesBasedOnPackage(WebDriver driver, String roomClass, String amount,
			boolean isAdditionalAdultAndChild, String adultRate, String childRate, ArrayList<String> testSteps)
			throws InterruptedException {

		String enterRate = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + roomClass
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";

		logger.info(enterRate);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);

		element.get(0).click();
		clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
		logger.info("RatePerNight : " + amount);
		element.get(0).sendKeys(amount);
		testSteps.add("Update base rate:  " + amount);

		if (isAdditionalAdultAndChild) {
			element.get(3).click();
			clearInputField(driver, element.get(3), element.get(3).getAttribute("value"));
			logger.info("Adult rate : " + adultRate);
			element.get(3).sendKeys(adultRate);
			testSteps.add("Update adult rate:  " + adultRate);

			element.get(4).click();
			clearInputField(driver, element.get(4), element.get(4).getAttribute("value"));
			logger.info("Child rate : " + childRate);
			element.get(4).sendKeys(childRate);
			testSteps.add("Update child rate:  " + childRate);

		}

	}

	public ArrayList<String> updateProRateAtClassLevel(WebDriver driver, String RoomClasses,
			boolean isAdditionalAdultChild, boolean isProRateChecked, String customRatePerNight, String adultRate,
			String childRate, ArrayList<String> testSteps) throws InterruptedException {

		if (isProRateChecked) {

			testSteps.add("OverRide rate per night for room class: " + RoomClasses);
			String pathOfRatePerNight = "//li[@class='IntervalRatePlan line']//span[text()='" + RoomClasses
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";

			boolean size = driver.findElements(By.xpath(pathOfRatePerNight)).size() > 0;
			logger.info(" is main checkbox checked: " + size);

			if (!size) {
				driver.findElement(By.xpath("//span[contains(@class,'ProCheck')]")).click();
			}
			String childpath = "(//li[@class='IntervalRatePlan line']//span[text()='" + RoomClasses
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//label//span)[1]";
			WebElement element = driver.findElement(By.xpath(childpath));
			Utility.ScrollToElement_NoWait(element, driver);
			if (!element.getAttribute("class").contains("ant-checkbox-checked")) {
				element.click();
			}
			List<WebElement> ratePerNight = driver.findElements(By.xpath(pathOfRatePerNight));
			logger.info("splitCustomRatePerNight: " + RoomClasses);

			ratePerNight.get(1).click();
			Wait.wait1Second();
			clearInputField(driver, ratePerNight.get(1), ratePerNight.get(1).getAttribute("value"));
			Wait.wait1Second();
			ratePerNight.get(1).sendKeys(customRatePerNight);
			testSteps.add("Custom Rate Per Night " + customRatePerNight);

			logger.info("Boolean.parseBoolean(isCustomRatePerNightAdultandChild): " + isAdditionalAdultChild);
			if (isAdditionalAdultChild) {

				ratePerNight.get(2).click();
				clearInputField(driver, ratePerNight.get(2), ratePerNight.get(2).getAttribute("value"));
				ratePerNight.get(2).sendKeys(adultRate);
				testSteps.add("Custom Add.adult/night " + adultRate);

				ratePerNight.get(3).click();
				clearInputField(driver, ratePerNight.get(3), ratePerNight.get(3).getAttribute("value"));
				ratePerNight.get(3).sendKeys(childRate);
				testSteps.add("Custom Add.child/night " + childRate);

			}
		} else {

			String childpath = "(//li[@class='IntervalRatePlan line']//span[text()='" + RoomClasses
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//label//span)[1]";
			WebElement element = driver.findElement(By.xpath(childpath));
			Utility.ScrollToElement_NoWait(element, driver);
			if (element.getAttribute("class").contains("ant-checkbox-checked")) {
				element.click();
			}

		}

		return testSteps;
	}

	public ArrayList<String> selectDate(WebDriver driver, String selecDate, boolean isStartDate) throws ParseException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testStep = new ArrayList<>();

		Wait.WaitForElement(driver, ORRateGrid.StartAndAndDateICon);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.StartAndAndDateICon), driver);
		if (isStartDate) {
			elements.StartAndAndDateICon.get(0).click();
			testStep.add("Click start date icon");

		} else {
			elements.StartAndAndDateICon.get(1).click();
			testStep.add("Click end date icon");

		}
		Wait.WaitForElement(driver, ORRateGrid.calendarMonthHeading);
		Wait.waitForElementToBeVisibile(By.xpath(ORRateGrid.calendarMonthHeading), driver);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.calendarMonthHeading), driver);
		String getMonth = elements.calendarMonthHeading.getText();

		String expectedMonth = ESTTimeZone.getDateBaseOnDate(selecDate, "dd/MM/yyyy", "MMMM yyyy");
		String getStartDate = ESTTimeZone.getDateBaseOnDate(selecDate, "dd/MM/yyyy", "EEE MMM dd yyyy");

		logger.info("expectedMonth: " + expectedMonth);
		String path = "//div[@aria-label='" + getStartDate + "']";
		boolean isMothEqual = false;
		while (!isMothEqual) {
			if (expectedMonth.equals(getMonth)) {
				logger.info("In if");
				isMothEqual = true;
				driver.findElement(By.xpath(path)).click();
				break;
			} else {
				logger.info("in else");
				clickOnCalendarRightArrow(driver);
				getMonth = getMonthFromCalendarHeading(driver);
				logger.info("getMonth: " + getMonth);
			}

		}

		if (isStartDate) {
			elements.StartAndAndDateICon.get(0).click();
			testStep.add("Select start date: " + selecDate);

		} else {
			elements.StartAndAndDateICon.get(1).click();
			testStep.add("Select end date: " + selecDate);

		}
		/// just remove focus
		driver.findElement(By.xpath("//div[contains(text(),'Bulk update')]")).click();
		return testStep;

	}

	public void expandRoomClass1(WebDriver driver, ArrayList<String> testSteps, String roomClassName)
			throws InterruptedException {
		Wait.wait5Second();
		Wait.WaitForElement(driver, "(//div[@class='DatesTable'])[1]");
//		Wait.waitForElementToBeInVisibile(By.xpath("(//div[@class='DatesTable'])[1]"), driver);
		Wait.waitForElementToBeClickable(By.xpath("(//div[@class='DatesTable'])[1]"), driver);
		String path = "//div[@class='DatesTable']//div[contains(text(),'" + roomClassName + "')]/parent::div/span";
		String plusPath = "//div[@class='DatesTable']//div[contains(text(),'" + roomClassName
				+ "')]/parent::div/span[contains(@class,'ir-plus')]";
		logger.info("path of expand: " + plusPath);
		int size = driver.findElements(By.xpath(plusPath)).size();
		logger.info("size of expand: " + size);
		if (size > 0) {
			Wait.WaitForElement(driver, path);

			WebElement element = driver.findElement(By.xpath(path));
			Utility.ScrollToViewElementINMiddle(driver, element);
			Utility.clickThroughJavaScript(driver, element);
		}
		String minusPath = "//div[@class='DatesTable']//div[contains(text(),'" + roomClassName
				+ "')]/parent::div/span[contains(@class,'ir-minus')]";

		logger.info("path of close: " + minusPath);

		Wait.wait2Second();
		boolean collapseExist = driver.findElement(By.xpath(minusPath)).isDisplayed();
		if (collapseExist) {
			testSteps.add(" Expand Room Class <b>" + roomClassName + "</b>");
			logger.info("Expand Room Class " + roomClassName);
			WebElement minus = driver.findElement(By.xpath(minusPath));
			Utility.ScrollToElement(minus, driver);
			assertTrue(minus.isDisplayed(), "Failed to verify collapse icon");
			testSteps.add("Verified Room Class <b>" + roomClassName + "</b> Expanded");
			logger.info("Verified Room Class " + roomClassName + " Expanded");
		}

	}

	public String getInterval(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.EnterInterval);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.EnterInterval), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.EnterInterval), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.EnterInterval, driver);
		return ratesGrid.EnterInterval.getAttribute("value");
	}

	public void enterRoomClassRates(WebDriver driver, String roomClass, String amount,
			boolean isAdditionalAdultAndChild, String adultRate, String childRate, ArrayList<String> testSteps)
			throws InterruptedException {

		String enterRate = "//li[@class='IntervalRatePlan line']//span[text()='" + roomClass
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";

		logger.info(enterRate);
		List<WebElement> element = driver.findElements(By.xpath(enterRate));
		Utility.ScrollToElement_NoWait(element.get(0), driver);

		element.get(0).click();
		clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
		logger.info("RatePerNight : " + amount);
		element.get(0).sendKeys(amount);
		testSteps.add("Update base rate:  " + amount);

		if (isAdditionalAdultAndChild) {
			element.get(1).click();
			clearInputField(driver, element.get(1), element.get(1).getAttribute("value"));
			logger.info("Adult rate : " + adultRate);
			element.get(1).sendKeys(adultRate);
			testSteps.add("Update adult rate:  " + adultRate);

			element.get(2).click();
			clearInputField(driver, element.get(2), element.get(1).getAttribute("value"));
			logger.info("Child rate : " + childRate);
			element.get(2).sendKeys(childRate);
			testSteps.add("Update child rate:  " + childRate);

		}

	}

	public ArrayList<String> getRoomClassRateWithAdditionalAdultAndChild(WebDriver driver, String roomClassName,
			ArrayList<String> testSteps) throws InterruptedException {

		NightlyRate nightlyRate = new NightlyRate();
		HashMap<String, ArrayList<String>> mapRoomClassWithRates = new HashMap<>();
		ArrayList<String> rates = new ArrayList<>();
		String getRoomClass = roomClassName.trim();
		testSteps.add(
				"Get Rates along with additional room adults/child and pro stay rate for room class: " + getRoomClass);

		String pathOfRates = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + getRoomClass
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		logger.info(pathOfRates);
		String getAdultCapacity = nightlyRate.getAdultCapacity(driver, getRoomClass);
		String getChildCapacity = nightlyRate.getChildCapacity(driver, getRoomClass);

		List<WebElement> getRates = driver.findElements(By.xpath(pathOfRates));
		String getBaseRate = getRates.get(0).getAttribute("value");

		String getMaxAdult = "0";
		String getMaxPerson = "0";
		String getAdultRate = "0";
		String getChildRate = "0";

		String chargeforAdditionalAdultChild = "//span[text()='Charge for additional adult/child']//..//button";
		String chargeforAdditionalAdultsChilds = "no";
		WebElement toggleBtnForChargeforAdditionalAdultChild = driver
				.findElement(By.xpath(chargeforAdditionalAdultChild));
		if (toggleBtnForChargeforAdditionalAdultChild.getAttribute("class").contains("ant-switch-checked")) {

			getMaxAdult = nightlyRate.getMaxAdult(driver, getRoomClass);
			logger.info("getMaxAdult: " + getMaxAdult);

			getMaxPerson = nightlyRate.getMaxPersons(driver, getRoomClass);
			logger.info("getMaxPerson: " + getMaxPerson);

			getAdultRate = getRates.get(3).getAttribute("value");
			logger.info("getAdultRate: " + getAdultRate);

			getChildRate = getRates.get(4).getAttribute("value");
			logger.info("getChildRate: " + getChildRate);

			chargeforAdditionalAdultsChilds = "yes";

		}

		String ratePerNight = "0";
		String proRateAdditionalAdult = "0";
		String proRateAdditionalChild = "0";

		String pathOfProRateStayInRoomClass = "//li[@class='IntervalRatePlan line']//span[text()='" + getRoomClass
				+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]";
		boolean isProRateStayCheckbox = driver.findElements(By.xpath(pathOfProRateStayInRoomClass)).size() > 0;
		String isProRateStayChecked = "no";
		// first check pro rate stay check box is displaying or not
		if (isProRateStayCheckbox) {

			// second check pro rate check box is enable or disable
			String pathOfProRateStayRateCheckBox = "//li[@class='IntervalRatePlan line']//span[text()='" + getRoomClass
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//label[contains(@class,'inrd-checkbox')]";
			WebElement getProRateStayCheckbox = driver.findElement(By.xpath(pathOfProRateStayRateCheckBox));
			if (getProRateStayCheckbox.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {

				String pathOfProRateStayRatePerNight = "//li[@class='IntervalRatePlan line']//span[text()='"
						+ getRoomClass
						+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";

				logger.info("pathOfProRateStayRatePerNight: " + pathOfProRateStayRatePerNight);
				List<WebElement> proRateStayValue = driver.findElements(By.xpath(pathOfProRateStayRatePerNight));
				ratePerNight = proRateStayValue.get(1).getAttribute("value");

				logger.info("ratePerNight: " + ratePerNight);
				isProRateStayChecked = "yes";

				// here verify additional adult and child checked or not for
				// per night
				if (toggleBtnForChargeforAdditionalAdultChild.getAttribute("class").contains("ant-switch-checked")) {
					proRateAdditionalAdult = proRateStayValue.get(2).getAttribute("value");
					logger.info("proRateAdditionalAdult: " + proRateAdditionalAdult);
					proRateAdditionalChild = proRateStayValue.get(3).getAttribute("value");
					logger.info("proRateAdditionalChild: " + proRateAdditionalChild);

				}
			}

		}

		rates.add(getBaseRate);
		testSteps.add("Base rate: " + getBaseRate);
		rates.add(getAdultCapacity);
		testSteps.add("Adults capacity: " + getAdultCapacity);

		rates.add(getChildCapacity);
		testSteps.add("Childs capacity: " + getChildCapacity);

		rates.add(chargeforAdditionalAdultsChilds);
		testSteps.add("Is additional adults/child on? " + chargeforAdditionalAdultsChilds);

		rates.add(getMaxAdult);
		testSteps.add("Max adults: " + getMaxAdult);
		rates.add(getMaxPerson);
		testSteps.add("Max persons: " + getMaxPerson);

		rates.add(getAdultRate);
		testSteps.add("Adults rate: " + getAdultRate);

		rates.add(getChildRate);
		testSteps.add("Childs rate: " + getChildRate);

		rates.add(isProRateStayChecked);
		testSteps.add("Is pro stay rate on? " + isProRateStayChecked);

		rates.add(ratePerNight);
		testSteps.add("Rate per night? " + ratePerNight);

		rates.add(proRateAdditionalAdult);
		testSteps.add("Per night adults rate " + proRateAdditionalAdult);

		rates.add(proRateAdditionalChild);
		testSteps.add("Per night childs rate " + proRateAdditionalChild);

		mapRoomClassWithRates.put(getRoomClass, rates);
		rateGridLogger.info("RoomClass: " + getRoomClass);
		// }

		return rates;
	}

	public void bulkUpdateOverideRates(WebDriver driver, String delim, String RatesUpdateType,
			String checkInDate_RatesUpdate, String checkOutDate_RatesUpdate, String isSunday_RatesUpdate,
			String isMonday_RatesUpdate, String isTuesday_RatesUpdate, String isWednesday_RatesUpdate,
			String isThursday_RatesUpdate, String isFriday_RatesUpdate, String isSaturday_RatesUpdate,
			String RatePlanName, String RoomClasses, String channelName, String UpdateRatesType,
			String isUpdateRateByRoomClass, String nightlyRate_RatesUpdate, String additionalAdults_RatesUpdate,
			String additionalChild_RatesUpdate, ArrayList<String> test_steps) throws InterruptedException {
		ArrayList<String> getTest_Steps = new ArrayList<String>();
		DerivedRate derivedRate = new DerivedRate();
		if (RatesUpdateType.equalsIgnoreCase("BulkUpdate")) {

			test_steps.add("=================== RATE PLAN RATES BULK UPDATE ======================");
			logger.info("=================== RATE PLAN RATES BULK UPDATE ======================");

			getTest_Steps.clear();
			getTest_Steps = clickOnBulkUpdate(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = selectBulkUpdateOption(driver, "Rates");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = startDate(driver, Utility.parseDate(checkInDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = endDate(driver, Utility.parseDate(checkOutDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
			test_steps.addAll(getTest_Steps);

			logger.info("==========CHECKING/UNCHECKING DAYS==========");
			test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Sun", isSunday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Mon", isMonday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Tue", isTuesday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Wed", isWednesday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Thu", isThursday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Fri", isFriday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Sat", isSaturday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			logger.info("==========SELECTING RATE PLAN " + RatePlanName + "==========");
			test_steps.add("==========SELECTING RATE PLAN " + RatePlanName + " ==========");

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
			test_steps.addAll(getTest_Steps);

			// ArrayList<String> roomClassesList =
			// Utility.convertTokenToArrayList(RoomClasses, delim);
			// for (String roomClassName : roomClassesList) {
			logger.info("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");
			test_steps.add("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");
			getTest_Steps.clear();
			getTest_Steps = selectRoomClass(driver, RoomClasses, delim);
			test_steps.addAll(getTest_Steps);
			// }

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Source", channelName);
			test_steps.addAll(getTest_Steps);

			logger.info("==========UPDATE RATES==========");
			test_steps.add("==========UPDATE RATES==========");

			// Checks Rate Update Type
			if (UpdateRatesType.equalsIgnoreCase("EnterNewRate")) {
				getTest_Steps.clear();
				getTest_Steps = selectBulkUpdateRatesOption(driver, 0);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = updateRoomsByRoomClassToggle(driver, Boolean.parseBoolean(isUpdateRateByRoomClass));
				test_steps.addAll(getTest_Steps);

				//
				// String[] roomClassArray = roomClassName.split("\\|");
				// ArrayList<String> roomClassList =
				// Utility.convertTokenToArrayList(RoomClasses, delim);
				// for (String str : roomClassList) {
				getTest_Steps.clear();
				getTest_Steps = selectRoomClass(driver, RoomClasses, delim);
				test_steps.addAll(getTest_Steps);
				test_steps.addAll(getTest_Steps);
				// }

				// String[] nightlyRateArray = nightlyRate_RatesUpdate.split("\\|");
				ArrayList<String> nightlyRateArray = Utility.convertTokenToArrayList(nightlyRate_RatesUpdate, delim);
				int nightArrayLength = 1;
				if (isUpdateRateByRoomClass.equalsIgnoreCase("True")) {
					nightArrayLength = nightlyRateArray.size();
				}
				ArrayList<String> additionalAdultArray = Utility.convertTokenToArrayList(additionalAdults_RatesUpdate,
						delim);
				ArrayList<String> additionalChildArray = Utility.convertTokenToArrayList(additionalChild_RatesUpdate,
						delim);
				// Check Length of NightlyRate List and Input Values
				for (int i = 0; i < nightArrayLength; i++) {

					getTest_Steps.clear();
					getTest_Steps = updateNightlyRate(driver, i, nightlyRateArray.get(i));
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = updateAdditionalAdultRate(driver, i, additionalAdultArray.get(i));
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = updateAdditionalChildRate(driver, i, additionalChildArray.get(i));
					test_steps.addAll(getTest_Steps);

				}
			}

			getTest_Steps.clear();
			getTest_Steps = clickBulkUpdatePopupUpdateButton(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = clickYesUpdateButton(driver);
			test_steps.addAll(getTest_Steps);
		} else if (RatesUpdateType.equalsIgnoreCase("Overide") || RatesUpdateType.equalsIgnoreCase("Override")) {

			test_steps.add("=================== RATE PLAN RATES OVERIDE ======================");
			logger.info("=================== RATE PLAN RATES OVERIDE ======================");

			ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(RoomClasses, delim);
			ArrayList<String> desiredOverideDate = Utility.convertTokenToArrayList(checkInDate_RatesUpdate, delim);
			ArrayList<String> nightlyRate_RatesUpdateList = Utility.convertTokenToArrayList(nightlyRate_RatesUpdate,
					delim);
			ArrayList<String> additionalAdults_RatesUpdateList = Utility
					.convertTokenToArrayList(additionalAdults_RatesUpdate, delim);
			ArrayList<String> additionalChild_RatesUpdateList = Utility
					.convertTokenToArrayList(additionalChild_RatesUpdate, delim);

			for (String roomClassName : roomClassesList) {
				for (int i = 0; i < desiredOverideDate.size(); i++) {
					clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, desiredOverideDate.get(i), "dd/MM/yyyy");
					expandRoomClass(driver, test_steps, roomClassName);
					overrideNightExtraAdultChildRate(driver, test_steps, roomClassName, channelName,
							nightlyRate_RatesUpdateList.get(i), additionalAdults_RatesUpdateList.get(i),
							additionalChild_RatesUpdateList.get(i));
				}
			}
		}
	}
	/*
	 * public void bulkUpdateOverideRates(WebDriver driver, String delim, String
	 * RatesUpdateType, String checkInDate_RatesUpdate, String
	 * checkOutDate_RatesUpdate, String isSunday_RatesUpdate, String
	 * isMonday_RatesUpdate, String isTuesday_RatesUpdate, String
	 * isWednesday_RatesUpdate, String isThursday_RatesUpdate, String
	 * isFriday_RatesUpdate, String isSaturday_RatesUpdate, String RatePlanName,
	 * String RoomClasses, String channelName, String UpdateRatesType, String
	 * isUpdateRateByRoomClass, String nightlyRate_RatesUpdate, String
	 * additionalAdults_RatesUpdate, String additionalChild_RatesUpdate,
	 * ArrayList<String> test_steps) throws InterruptedException { ArrayList<String>
	 * getTest_Steps = new ArrayList<String>(); DerivedRate derivedRate = new
	 * DerivedRate(); if (RatesUpdateType.equalsIgnoreCase("BulkUpdate")) {
	 * 
	 * test_steps.
	 * add("=================== RATE PLAN RATES BULK UPDATE ======================"
	 * ); logger.
	 * info("=================== RATE PLAN RATES BULK UPDATE ======================"
	 * );
	 * 
	 * getTest_Steps.clear(); getTest_Steps = clickOnBulkUpdate(driver);
	 * test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = selectBulkUpdateOption(driver,
	 * "Rates"); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = startDate(driver,
	 * Utility.parseDate(checkInDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
	 * test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = endDate(driver,
	 * Utility.parseDate(checkOutDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
	 * test_steps.addAll(getTest_Steps);
	 * 
	 * logger.info("==========CHECKING/UNCHECKING DAYS==========");
	 * test_steps.add("==========CHECKING/UNCHECKING DAYS==========");
	 * 
	 * getTest_Steps.clear(); getTest_Steps = bulkUpdatePoppupDayCheck(driver,
	 * "Sun", isSunday_RatesUpdate); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = bulkUpdatePoppupDayCheck(driver,
	 * "Mon", isMonday_RatesUpdate); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = bulkUpdatePoppupDayCheck(driver,
	 * "Tue", isTuesday_RatesUpdate); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = bulkUpdatePoppupDayCheck(driver,
	 * "Wed", isWednesday_RatesUpdate); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = bulkUpdatePoppupDayCheck(driver,
	 * "Thu", isThursday_RatesUpdate); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = bulkUpdatePoppupDayCheck(driver,
	 * "Fri", isFriday_RatesUpdate); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = bulkUpdatePoppupDayCheck(driver,
	 * "Sat", isSaturday_RatesUpdate); test_steps.addAll(getTest_Steps);
	 * 
	 * logger.info("==========SELECTING RATE PLAN " + RatePlanName + "==========");
	 * test_steps.add("==========SELECTING RATE PLAN " + RatePlanName +
	 * " ==========");
	 * 
	 * getTest_Steps.clear(); getTest_Steps = selectItemsFromDropDowns(driver,
	 * "Rate Plan", RatePlanName); test_steps.addAll(getTest_Steps);
	 * 
	 * // ArrayList<String> roomClassesList = //
	 * Utility.convertTokenToArrayList(RoomClasses, delim); // for (String
	 * roomClassName : roomClassesList) {
	 * logger.info("==========SELECTING ROOM CLASS : " + RoomClasses +
	 * " =========="); test_steps.add("==========SELECTING ROOM CLASS : " +
	 * RoomClasses + " =========="); getTest_Steps.clear(); getTest_Steps =
	 * selectRoomClass(driver, RoomClasses, delim);
	 * test_steps.addAll(getTest_Steps); // }
	 * 
	 * getTest_Steps.clear(); getTest_Steps = selectItemsFromDropDowns(driver,
	 * "Source", channelName); test_steps.addAll(getTest_Steps);
	 * 
	 * >>>>>>> develop logger.info("==========UPDATE RATES==========");
	 * test_steps.add("==========UPDATE RATES==========");
	 * 
	 * // Checks Rate Update Type <<<<<<< HEAD if
	 * (RatesUpdateType.equalsIgnoreCase("EnterNewRate")) { ======= if
	 * (UpdateRatesType.equalsIgnoreCase("EnterNewRate")) { >>>>>>> develop
	 * getTest_Steps.clear(); getTest_Steps = selectBulkUpdateRatesOption(driver,
	 * 0); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = updateRoomsByRoomClassToggle(driver,
	 * Boolean.parseBoolean(isUpdateRateByRoomClass));
	 * test_steps.addAll(getTest_Steps);
	 * 
	 * // // String[] roomClassArray = roomClassName.split("\\|"); //
	 * ArrayList<String> roomClassList = //
	 * Utility.convertTokenToArrayList(RoomClasses, delim); // for (String str :
	 * roomClassList) { getTest_Steps.clear(); getTest_Steps =
	 * selectRoomClass(driver, RoomClasses, delim);
	 * test_steps.addAll(getTest_Steps); test_steps.addAll(getTest_Steps); // }
	 * <<<<<<< HEAD
	 * 
	 * // String[] nightlyRateArray = nightlyRate_RatesUpdate.split("\\|");
	 * ArrayList<String> nightlyRateArray =
	 * Utility.convertTokenToArrayList(nightlyRate_RatesUpdate, delim); int
	 * nightArrayLength = 1; if (isUpdateRateByRoomClass.equalsIgnoreCase("True")) {
	 * nightArrayLength = nightlyRateArray.size(); } ArrayList<String>
	 * additionalAdultArray =
	 * Utility.convertTokenToArrayList(additionalAdults_RatesUpdate, delim);
	 * ArrayList<String> additionalChildArray =
	 * Utility.convertTokenToArrayList(additionalChild_RatesUpdate, delim); // Check
	 * Length of NightlyRate List and Input Values for (int i = 0; i <
	 * nightArrayLength; i++) {
	 * 
	 * getTest_Steps.clear(); getTest_Steps = updateNightlyRate(driver, i,
	 * nightlyRateArray.get(i)); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = updateAdditionalAdultRate(driver, i,
	 * additionalAdultArray.get(i)); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = updateAdditionalChildRate(driver, i,
	 * additionalChildArray.get(i)); test_steps.addAll(getTest_Steps);
	 * 
	 * } }
	 * 
	 * getTest_Steps.clear(); getTest_Steps =
	 * clickBulkUpdatePopupUpdateButton(driver); test_steps.addAll(getTest_Steps);
	 * 
	 * getTest_Steps.clear(); getTest_Steps = clickYesUpdateButton(driver);
	 * test_steps.addAll(getTest_Steps); } else if
	 * (RatesUpdateType.equalsIgnoreCase("Overide") ||
	 * RatesUpdateType.equalsIgnoreCase("Override")) {
	 * 
	 * test_steps.
	 * add("=================== DERIVED RATE PLAN RATES OVERIDE ======================"
	 * ); logger.
	 * info("=================== DERIVED RATE PLAN RATES OVERIDE ======================"
	 * );
	 * 
	 * ArrayList<String> roomClassesList =
	 * Utility.convertTokenToArrayList(RoomClasses, delim); ArrayList<String>
	 * desiredOverideDate = Utility.convertTokenToArrayList(checkInDate_RatesUpdate,
	 * delim); ArrayList<String> nightlyRate_RatesUpdateList =
	 * Utility.convertTokenToArrayList(nightlyRate_RatesUpdate, delim);
	 * ArrayList<String> additionalAdults_RatesUpdateList = Utility
	 * .convertTokenToArrayList(additionalAdults_RatesUpdate, delim);
	 * ArrayList<String> additionalChild_RatesUpdateList = Utility
	 * .convertTokenToArrayList(additionalChild_RatesUpdate, delim);
	 * 
	 * for (String roomClassName : roomClassesList) { for (int i = 0; i <
	 * desiredOverideDate.size(); i++) { clickForRateGridCalender(driver,
	 * test_steps); Utility.selectDateFromDatePicker(driver,
	 * desiredOverideDate.get(i), "dd/MM/yyyy"); expandRoomClass(driver, test_steps,
	 * roomClassName); overrideNightExtraAdultChildRate(driver, test_steps,
	 * roomClassName, channelName, nightlyRate_RatesUpdateList.get(i),
	 * additionalAdults_RatesUpdateList.get(i),
	 * additionalChild_RatesUpdateList.get(i)); } } } }
	 */

	public void bulkUpdateOverideRates(WebDriver driver, String delim, String RatesUpdateType,
			String checkInDate_RatesUpdate, String checkOutDate_RatesUpdate, String isSunday_RatesUpdate,
			String isMonday_RatesUpdate, String isTuesday_RatesUpdate, String isWednesday_RatesUpdate,
			String isThursday_RatesUpdate, String isFriday_RatesUpdate, String isSaturday_RatesUpdate,
			String RatePlanName, String RoomClasses, String channelName, String isUpdateRateByRoomClass,
			String nightlyRate_RatesUpdate, String additionalAdults_RatesUpdate, String additionalChild_RatesUpdate,
			ArrayList<String> test_steps) throws InterruptedException {
		ArrayList<String> getTest_Steps = new ArrayList<String>();
		DerivedRate derivedRate = new DerivedRate();
		if (RatesUpdateType.equalsIgnoreCase("BulkUpdate")) {

			test_steps.add("=================== DERIVED RATE PLAN RATES BULK UPDATE ======================");
			logger.info("=================== DERIVED RATE PLAN RATES BULK UPDATE ======================");

			getTest_Steps.clear();
			getTest_Steps = clickOnBulkUpdate(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = selectBulkUpdateOption(driver, "Rates");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = startDate(driver, Utility.parseDate(checkInDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = endDate(driver, Utility.parseDate(checkOutDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
			test_steps.addAll(getTest_Steps);

			logger.info("==========CHECKING/UNCHECKING DAYS==========");
			test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Sun", isSunday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Mon", isMonday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Tue", isTuesday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Wed", isWednesday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Thu", isThursday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Fri", isFriday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = bulkUpdatePoppupDayCheck(driver, "Sat", isSaturday_RatesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
			test_steps.addAll(getTest_Steps);

			// ArrayList<String> roomClassesList =
			// Utility.convertTokenToArrayList(RoomClasses, delim);
			// for (String roomClassName : roomClassesList) {
			getTest_Steps.clear();
			getTest_Steps = selectRoomClass(driver, RoomClasses, delim);
			test_steps.addAll(getTest_Steps);
			// }

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Source", channelName);
			test_steps.addAll(getTest_Steps);

			logger.info("==========UPDATE RATES==========");
			test_steps.add("==========UPDATE RATES==========");

			// Checks Rate Update Type
			if (RatesUpdateType.equalsIgnoreCase("EnterNewRate")) {
				getTest_Steps.clear();
				getTest_Steps = selectBulkUpdateRatesOption(driver, 0);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = updateRoomsByRoomClassToggle(driver, Boolean.parseBoolean(isUpdateRateByRoomClass));
				test_steps.addAll(getTest_Steps);

				//
				// String[] roomClassArray = roomClassName.split("\\|");
				// ArrayList<String> roomClassList =
				// Utility.convertTokenToArrayList(RoomClasses, delim);
				// for (String str : roomClassList) {
				getTest_Steps.clear();
				getTest_Steps = selectRoomClass(driver, RoomClasses, delim);
				test_steps.addAll(getTest_Steps);
				test_steps.addAll(getTest_Steps);
				// }

				// String[] nightlyRateArray = nightlyRate_RatesUpdate.split("\\|");
				ArrayList<String> nightlyRateArray = Utility.convertTokenToArrayList(nightlyRate_RatesUpdate, delim);
				int nightArrayLength = 1;
				if (isUpdateRateByRoomClass.equalsIgnoreCase("True")) {
					nightArrayLength = nightlyRateArray.size();
				}
				ArrayList<String> additionalAdultArray = Utility.convertTokenToArrayList(additionalAdults_RatesUpdate,
						delim);
				ArrayList<String> additionalChildArray = Utility.convertTokenToArrayList(additionalChild_RatesUpdate,
						delim);
				// Check Length of NightlyRate List and Input Values
				for (int i = 0; i < nightArrayLength; i++) {

					getTest_Steps.clear();
					getTest_Steps = updateNightlyRate(driver, i, nightlyRateArray.get(i));
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = updateAdditionalAdultRate(driver, i, additionalAdultArray.get(i));
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = updateAdditionalChildRate(driver, i, additionalChildArray.get(i));
					test_steps.addAll(getTest_Steps);

				}
			}

			getTest_Steps.clear();
			getTest_Steps = clickBulkUpdatePopupUpdateButton(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = clickYesUpdateButton(driver);
			test_steps.addAll(getTest_Steps);
		} else if (RatesUpdateType.equalsIgnoreCase("Overide") || RatesUpdateType.equalsIgnoreCase("Override")) {

			test_steps.add("=================== DERIVED RATE PLAN RATES OVERIDE ======================");
			logger.info("=================== DERIVED RATE PLAN RATES OVERIDE ======================");

			ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(RoomClasses, delim);
			ArrayList<String> desiredOverideDate = Utility.convertTokenToArrayList(checkInDate_RatesUpdate, delim);
			ArrayList<String> nightlyRate_RatesUpdateList = Utility.convertTokenToArrayList(nightlyRate_RatesUpdate,
					delim);
			ArrayList<String> additionalAdults_RatesUpdateList = Utility
					.convertTokenToArrayList(additionalAdults_RatesUpdate, delim);
			ArrayList<String> additionalChild_RatesUpdateList = Utility
					.convertTokenToArrayList(additionalChild_RatesUpdate, delim);

			for (String roomClassName : roomClassesList) {
				for (int i = 0; i < desiredOverideDate.size(); i++) {
					clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, desiredOverideDate.get(i), "dd/MM/yyyy");
					expandRoomClass(driver, test_steps, roomClassName);
					overrideNightExtraAdultChildRate(driver, test_steps, roomClassName, channelName,
							nightlyRate_RatesUpdateList.get(i), additionalAdults_RatesUpdateList.get(i),
							additionalChild_RatesUpdateList.get(i));
				}
			}
		}
	}

	public void addSeasonForIntervalRatePlan(WebDriver driver, ArrayList<String> test_steps, String checkInDate,
			String CheckoutDate, String SeasonName, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, String roomClass,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdultsRate, String ChildRate, String IsProRateStayInRate, String isProRateAtRateLevel,
			String isProRateStayInSeason, String isProRateInRoomClass, String ProRateRoomClassName,
			String IsCustomPerNight, String CustomRoomClass, String CustomRatePerNight,
			String isAssignPolicyByRoomClass, String CustomRateAdultdPerNight, String CustomRateChildPerNight,
			String isCustomRatePerNightAdultandChild, String RoomClassInPolicy) throws Exception {

		// add season base on existing
		NightlyRate nightlyRate = new NightlyRate();
		boolean isSeasonFind = true;
		int count = 5;
		String timeZone = "America/New_York";
		String getCurrentDate = "";
		while (isSeasonFind) {
			getCurrentDate = Utility.getNextDate(count, "dd/MM/yyyy", timeZone);

			boolean isSeaonExist = nightlyRate.verifySeasonExistInbetweencheckin(driver, getCurrentDate);
			if (!isSeaonExist) {
				isSeasonFind = false;
			}
			count += 5;

		}
		checkInDate = getCurrentDate;
		CheckoutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkInDate, "dd/MM/yyyy", "dd/MM/yyyy", 1, timeZone);

		nightlyRate.selectSeasonDates(driver, test_steps, checkInDate, CheckoutDate);
		nightlyRate.enterSeasonName(driver, test_steps,
				SeasonName.split("\\" + Utility.DELIM)[0] + Utility.generateRandomNumber());

		nightlyRate.clickCreateSeason(driver, test_steps);
		nightlyRate.selectSeasonColor(driver, test_steps);

		RatesGrid ratesGrid = new RatesGrid();
		ratesGrid.clickOnAdditionalChargForAdultsAndChildern(driver,
				Boolean.parseBoolean(isAdditionalChargesForChildrenAdults));
		test_steps.add("Charge for additional adult/child is on? " + isAdditionalChargesForChildrenAdults);

		boolean isProRateStay = ratesGrid.verifyProrateAtSeasonLevel(driver, Boolean.parseBoolean(IsProRateStayInRate),
				Boolean.parseBoolean(isProRateStayInSeason), test_steps);
		logger.info("isProRateStay: " + isProRateStay);
		test_steps.add("Is pro rate stay checked at season ? " + isProRateStay);

		logger.info("customRatePerNight in rate grid: " + CustomRatePerNight);

		ratesGrid.enterRoomClassRates(driver, roomClass, RatePerNight, isAdditionalChargesForChildrenAdults, MaxAdults,
				MaxPersons, AdultsRate, ChildRate, isProRateStay, isProRateInRoomClass, ProRateRoomClassName,
				IsCustomPerNight, CustomRoomClass, CustomRatePerNight, CustomRateAdultdPerNight,
				CustomRateChildPerNight, isCustomRatePerNightAdultandChild, test_steps);
		nightlyRate.clickSaveSason(driver, test_steps);

	}

	public boolean isProstayeachseasonCheckboxchecked(WebDriver driver) throws InterruptedException {
		boolean isChecked = false;
		Elements_RatesGrid elements_RatesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, ORRateGrid.ProrateforeachSeasonCheckbox);
		Wait.waitForElementToBeInVisibile(By.xpath(ORRateGrid.ProrateforeachSeasonCheckbox), driver);
		Utility.ScrollToElement_NoWait(elements_RatesGrid.ProrateforeachSeasonCheckbox, driver);
		if (elements_RatesGrid.ProrateforeachSeasonCheckbox.getAttribute("class").contains("checked"))
			isChecked = true;
		return isChecked;
	}

	public void clickProstayeachseasonCheckboxchecked(WebDriver driver, boolean isClick) throws InterruptedException {
		Elements_RatesGrid elements_RatesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, ORRateGrid.ClickProrateforeachSeasonCheckbox);
		Wait.waitForElementToBeInVisibile(By.xpath(ORRateGrid.ClickProrateforeachSeasonCheckbox), driver);
		Utility.ScrollToElement_NoWait(elements_RatesGrid.ClickProrateforeachSeasonCheckbox, driver);

		if (isClick) {
			if (!elements_RatesGrid.ProrateforeachSeasonCheckbox.getAttribute("class").contains("checked"))
				elements_RatesGrid.ClickProrateforeachSeasonCheckbox.click();
		} else {
			if (elements_RatesGrid.ProrateforeachSeasonCheckbox.getAttribute("class").contains("checked"))
				elements_RatesGrid.ClickProrateforeachSeasonCheckbox.click();

		}
	}

	/*
	 * public String calculateAdditionalAdultCharge(String currentPerson, String
	 * RoomRate, String MaxAdult, String ExtraAdultRate) { if
	 * (Integer.parseInt(currentPerson) <= Integer.parseInt(MaxAdult)) { return
	 * RoomRate; } else { return (Float.parseFloat(RoomRate) +
	 * Float.parseFloat(ExtraAdultRate)) + ""; } }
	 */

	public void verifyLoadingGone(WebDriver driver) throws InterruptedException {
		String loading = "(//div[@class='loadingSection']//div[@class='loading'])[2]";
		int counter = 0;
		try {

			while (true) {
				rateGridLogger.info("in while loop");
				boolean isLoading = driver.findElements(By.xpath(loading)).size() == 0;
				if (isLoading) {
					rateGridLogger.info("element is not dispalying");
					break;
				} else if (counter == 40) {
					rateGridLogger.info("in 30 times");
					break;
				} else {
					counter++;
					Wait.wait1Second();
					rateGridLogger.info("after wait condition");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ArrayList<String> updateProductRateWithDifferentCombination(WebDriver driver, String amount,
			String firstCalculationMethod, String secondCalculationMethod) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.productValue);
		WebElement productValElement = driver.findElement(By.xpath(RateGridPage.productValue));
		boolean isValueDifferentFromAlreadySelectedVaues = false;
		productValElement.click();
		testSteps.add("Click on product value.");
		if (!amount.equals(productValElement.getAttribute("value"))) {
			isValueDifferentFromAlreadySelectedVaues = true;
		}
		productValElement.clear();
		productValElement.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		productValElement.sendKeys(amount);
		testSteps.add("Enter product value : " + amount);

		Wait.WaitForElement(driver, RateGridPage.firstCalculationMethodDropDownPath);
		WebElement firstCalculationMethodDropdownElement = driver
				.findElement(By.xpath(RateGridPage.firstCalculationMethodDropDownPath));
		firstCalculationMethodDropdownElement.click();

		testSteps.add("Click on product value.");

		String firstElementPath = "//li[text()='" + firstCalculationMethod + "']";
		Wait.WaitForElement(driver, firstElementPath);
		if (driver.findElement(By.xpath(firstElementPath)).getAttribute("class")
				.contains("ant-select-dropdown-menu-item-selected")) {
			testSteps.add(firstCalculationMethod + " is already selected.");
		} else {
			driver.findElement(By.xpath(firstElementPath)).click();
			testSteps.add("Successfully Clicked on : " + firstCalculationMethod);
			isValueDifferentFromAlreadySelectedVaues = true;
		}

		Wait.WaitForElement(driver, RateGridPage.secondCalculationMethodDropDownPath);
		WebElement secondCalculationMethodDropdownElement = driver
				.findElement(By.xpath(RateGridPage.secondCalculationMethodDropDownPath));
		secondCalculationMethodDropdownElement.click();

		String secondElementPath = "//li[text()='" + secondCalculationMethod + "']";
		Wait.WaitForElement(driver, secondElementPath);
		if (driver.findElement(By.xpath(secondElementPath)).getAttribute("class")
				.contains("ant-select-dropdown-menu-item-selected")) {
			testSteps.add(secondCalculationMethod + " is already selected.");
		} else {
			driver.findElement(By.xpath(secondElementPath)).click();
			testSteps.add("Successfully Clicked on : " + secondCalculationMethod);
			isValueDifferentFromAlreadySelectedVaues = true;
		}

		if (isValueDifferentFromAlreadySelectedVaues) {
			Wait.WaitForElement(driver, RateGridPage.SaveRatePlanButton);
			Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SaveRatePlanButton), driver);
			Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SaveRatePlanButton), driver);
			Utility.ScrollToElement(elements.SaveRatePlanButton, driver);
			elements.SaveRatePlanButton.click();
			testSteps.add("Click on save rate plan buttton");
			Wait.wait5Second();
		}

		return testSteps;
	}

	public void bulkUpdateOverideRules(WebDriver driver, String delim, String RulesUpdateType,
			String RulesUpdateStartDate, String RulesUpdateEndDate, String isSun_RulesUpdate, String isMon_RulesUpdate,
			String isTue_RulesUpdate, String isWed_RulesUpdate, String isThu_RulesUpdate, String isFri_RulesUpdate,
			String isSat_RulesUpdate, String RatePlanName, String RoomClasses, String channelName,
			String Type_RulesUpdate, String RuleMinStayValue_RulesUpdate, ArrayList<String> test_steps)
			throws Exception {
		ArrayList<String> getTest_Steps = new ArrayList<String>();
		DerivedRate derivedRate = new DerivedRate();
		if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {

			test_steps.add("===================  RATE PLAN RULES BLUK UPDATE ======================");
			logger.info("===================  RATE PLAN RULES BLUK UPDATE ======================");

			getTest_Steps.clear();
			getTest_Steps = clickOnBulkUpdate(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = selectBulkUpdateOption(driver, "Rules");
			test_steps.addAll(getTest_Steps);

			test_steps.add("==========SELECT START DATE==========");
			logger.info("==========SELECT START DATE==========");

			getTest_Steps.clear();
			getTest_Steps = selectStartDate(driver,
					Utility.parseDate(RulesUpdateStartDate, "dd/MM/yyyy", "MM/dd/yyyy"));
			test_steps.addAll(getTest_Steps);

			test_steps.add("==========SELECT END DATE==========");
			logger.info("==========SELECT END DATE==========");

			getTest_Steps.clear();
			getTest_Steps = selectEndDate(driver, Utility.parseDate(RulesUpdateEndDate, "dd/MM/yyyy", "MM/dd/yyyy"));
			test_steps.addAll(getTest_Steps);

			logger.info("==========CHECKING/UNCHECKING DAYS==========");
			test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Sun", isSun_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Mon", isMon_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Tue", isTue_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Wed", isWed_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Thu", isThu_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Fri", isFri_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Sat", isSat_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			logger.info("==========SELECTING RATE PLAN " + RatePlanName + "==========");
			test_steps.add("==========SELECTING RATE PLAN " + RatePlanName + " ==========");

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
			test_steps.addAll(getTest_Steps);

			logger.info("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");
			test_steps.add("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");

			// ArrayList<String> roomClassesList =
			// Utility.convertTokenToArrayList(RoomClasses, delim);
			// for (String roomClassName : roomClassesList) {
			getTest_Steps.clear();
			getTest_Steps = selectRoomClass(driver, RoomClasses, delim);
			test_steps.addAll(getTest_Steps);
			// }

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Source", channelName);
			test_steps.addAll(getTest_Steps);

			ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);

			for (String type : type_rulesUpdateList) {

				if (type.equalsIgnoreCase("min stay")) {
					getTest_Steps.clear();
					getTest_Steps = clickMinimumStay(driver, "Yes");
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = enterMinimumStayValue(driver, RuleMinStayValue_RulesUpdate);
					test_steps.addAll(getTest_Steps);

				} else if (type.equalsIgnoreCase("No Check In")) {

					getTest_Steps.clear();
					getTest_Steps = clickCheckin(driver, "Yes");
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = clickNoCheckInCheckbox(driver, "Yes");
					test_steps.addAll(getTest_Steps);

				} else if (type.equalsIgnoreCase("No Check out")) {
					getTest_Steps.clear();
					getTest_Steps = clickCheckOut(driver, "Yes");
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = clickNoCheckOutCheckbox(driver, "Yes");
					test_steps.addAll(getTest_Steps);

				}

			}
			getTest_Steps.clear();
			getTest_Steps = clickUpdateButton(driver);
			test_steps.addAll(getTest_Steps);

			logger.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
			test_steps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

			int numberOfDays = Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate) + 1;
			String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
			test_steps.add("Expected total days : " + expectedDays);
			logger.info("Expected total days : " + expectedDays);
			String totalDays = getTotalDaysText(driver, "Rules");
			test_steps.add("Found : " + totalDays);
			logger.info("Found : " + totalDays);
			Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
			test_steps.add("Verified total number of days");
			logger.info("Verified total number of days");

			getTest_Steps.clear();
			getTest_Steps = clickOnYesUpdateButton(driver);
			test_steps.addAll(getTest_Steps);
		} else if (RulesUpdateType.equalsIgnoreCase("Override") || RulesUpdateType.equalsIgnoreCase("Overide")) {

			test_steps.add("===================  RATE PLAN RULES OVERIDE ======================");
			logger.info("===================  RATE PLAN RULES OVERIDE ======================");

			ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(RoomClasses, delim);
			ArrayList<String> desiredOverideDate = Utility.convertTokenToArrayList(RulesUpdateStartDate, delim);
			ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);

			for (String roomClassName : roomClassesList) {
				for (int i = 0; i < desiredOverideDate.size(); i++) {

					clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, desiredOverideDate.get(i), "dd/MM/yyyy");

					expandRoomClass(driver, test_steps, roomClassName);
					expandChannel(driver, test_steps, roomClassName, channelName);

					overrideMinStayValue(driver, test_steps, roomClassName, channelName, "0");
					overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName, "No Check In",
							false);
					overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName, "No Check Out",
							false);

				}
			}
			for (String roomClassName : roomClassesList) {
				for (int i = 0; i < desiredOverideDate.size(); i++) {

					clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, desiredOverideDate.get(i), "dd/MM/yyyy");
					expandRoomClass(driver, test_steps, roomClassName);
					expandChannel(driver, test_steps, roomClassName, channelName);

					for (String type : type_rulesUpdateList) {

						ArrayList<String> typeList = Utility.convertTokenToArrayList(type, ",");
						for (String rule : typeList) {
							if (rule.equalsIgnoreCase("min stay")) {
								overrideMinStayValue(driver, test_steps, roomClassName, channelName,
										RuleMinStayValue_RulesUpdate);
							} else if (rule.equalsIgnoreCase("No Check In")) {
								overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										"No Check In", true);

							} else if (rule.equalsIgnoreCase("No Check out")) {
								overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										"No Check Out", true);
							}
						}
					}
				}
			}

		}
	}

	public String calculateAdditionalAdultCharge(String currentPerson, String RoomRate, String MaxAdult,
			String ExtraAdultRate) {
		if (Integer.parseInt(currentPerson) <= Integer.parseInt(MaxAdult)) {
			return RoomRate;
		} else {
			return (Float.parseFloat(RoomRate) + Float.parseFloat(ExtraAdultRate)) + "";
		}
	}

	public void addProduct(WebDriver driver, String productName) throws InterruptedException {
		String path = "	//span[text()='" + productName + "']//..//span[contains(@class,'Addbtn')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement_NoWait(element, driver);
		element.click();
	}

	public HashMap<String, String> getProductValue(WebDriver driver, String productName) throws InterruptedException {

		HashMap<String, String> listOfValue = new HashMap<String, String>();
		String pathofAmount = "//span[text()='" + productName.trim() + "']//..//input";
		Wait.WaitForElement(driver, pathofAmount);
		Wait.waitForElementToBeClickable(By.xpath(pathofAmount), driver);
		WebElement elementOfAmount = driver.findElement(By.xpath(pathofAmount));
		Utility.ScrollToElement_NoWait(elementOfAmount, driver);
		listOfValue.put("amount", elementOfAmount.getAttribute("value").trim());
		String pathofCalculationMethod = "//span[text()='" + productName.trim()
				+ "']//..//div[@class='ant-select-selection-selected-value']";
		List<WebElement> listOfElements = driver.findElements(By.xpath(pathofCalculationMethod));
		listOfValue.put("firstCalcuationMethod", listOfElements.get(0).getText().trim());
		listOfValue.put("secondCalcuationMethod", listOfElements.get(1).getText().trim());
		return listOfValue;
	}

	public ArrayList<String> clickOnProductTab(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, RateGridPage.ProductTab);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.ProductTab), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.ProductTab), driver);
		Utility.ScrollToElement_NoWait(elements.ProductTab, driver);
		elements.ProductTab.click();
		testSteps.add("Click on product tab");
		return testSteps;

	}

	public ArrayList<String> updateProductRateWithDifferentCombination(WebDriver driver, String amount,
			String firstCalculationMethod, String secondCalculationMethod, boolean updateOnlyRate)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();

		Wait.WaitForElement(driver, RateGridPage.productValue);
		WebElement productValElement = driver.findElement(By.xpath(RateGridPage.productValue));
		boolean isValueDifferentFromAlreadySelectedVaues = false;
		productValElement.click();
		testSteps.add("Click on product value.");
		if (!amount.equals(productValElement.getAttribute("value"))) {
			isValueDifferentFromAlreadySelectedVaues = true;
		}
		productValElement.clear();
		productValElement.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		productValElement.sendKeys(amount);
		testSteps.add("Enter product value : " + amount);

		if (updateOnlyRate == false) {
			Wait.WaitForElement(driver, RateGridPage.firstCalculationMethodDropDownPath);
			WebElement firstCalculationMethodDropdownElement = driver
					.findElement(By.xpath(RateGridPage.firstCalculationMethodDropDownPath));
			firstCalculationMethodDropdownElement.click();

			testSteps.add("Click on product value.");

			String firstElementPath = "//li[text()='" + firstCalculationMethod + "']";
			Wait.WaitForElement(driver, firstElementPath);
			if (driver.findElement(By.xpath(firstElementPath)).getAttribute("class")
					.contains("ant-select-dropdown-menu-item-selected")) {
				testSteps.add(firstCalculationMethod + " is already selected.");
			} else {
				driver.findElement(By.xpath(firstElementPath)).click();
				testSteps.add("Successfully Clicked on : " + firstCalculationMethod);
				isValueDifferentFromAlreadySelectedVaues = true;
			}

			Wait.WaitForElement(driver, RateGridPage.secondCalculationMethodDropDownPath);
			WebElement secondCalculationMethodDropdownElement = driver
					.findElement(By.xpath(RateGridPage.secondCalculationMethodDropDownPath));
			secondCalculationMethodDropdownElement.click();

			String secondElementPath = "//li[text()='" + secondCalculationMethod + "']";
			Wait.WaitForElement(driver, secondElementPath);
			if (driver.findElement(By.xpath(secondElementPath)).getAttribute("class")
					.contains("ant-select-dropdown-menu-item-selected")) {
				testSteps.add(secondCalculationMethod + " is already selected.");
			} else {
				driver.findElement(By.xpath(secondElementPath)).click();
				testSteps.add("Successfully Clicked on : " + secondCalculationMethod);
				isValueDifferentFromAlreadySelectedVaues = true;
			}
		}
		if (isValueDifferentFromAlreadySelectedVaues) {
			Wait.WaitForElement(driver, RateGridPage.SaveRatePlanButton);
			Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.SaveRatePlanButton), driver);
			Wait.waitForElementToBeClickable(By.xpath(RateGridPage.SaveRatePlanButton), driver);
			Utility.ScrollToElement(elements.SaveRatePlanButton, driver);
			elements.SaveRatePlanButton.click();
			testSteps.add("Click on save rate plan buttton");
			Wait.wait5Second();
		}

		return testSteps;
	}

	public ArrayList<String> getRoomClassRateWithAdditionalAdultAndChildBasedOnPackage(WebDriver driver,
			String roomClassName, ArrayList<String> testSteps) throws InterruptedException {

		NightlyRate nightlyRate = new NightlyRate();
		HashMap<String, ArrayList<String>> mapRoomClassWithRates = new HashMap<>();
		ArrayList<String> rates = new ArrayList<>();
		String getRoomClass = roomClassName.trim();
		testSteps.add(
				"Get Rates along with additional room adults/child and pro stay rate for room class: " + getRoomClass);

		String pathOfRates = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + getRoomClass
				+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";
		logger.info(pathOfRates);
		String getAdultCapacity = nightlyRate.getAdultCapacity(driver, getRoomClass);
		String getChildCapacity = nightlyRate.getChildCapacity(driver, getRoomClass);

		List<WebElement> getRates = driver.findElements(By.xpath(pathOfRates));
		String getBaseRate = getRates.get(0).getAttribute("value");

		String getMaxAdult = "0";
		String getMaxPerson = "0";
		String getAdultRate = "0";
		String getChildRate = "0";

		String chargeforAdditionalAdultChild = "//span[text()='Charge for additional adult/child']//..//button";
		String chargeforAdditionalAdultsChilds = "no";
		WebElement toggleBtnForChargeforAdditionalAdultChild = driver
				.findElement(By.xpath(chargeforAdditionalAdultChild));
		if (toggleBtnForChargeforAdditionalAdultChild.getAttribute("class").contains("ant-switch-checked")) {

			getMaxAdult = nightlyRate.getMaxAdult(driver, getRoomClass);
			logger.info("getMaxAdult: " + getMaxAdult);

			getMaxPerson = nightlyRate.getMaxPersons(driver, getRoomClass);
			logger.info("getMaxPerson: " + getMaxPerson);

			getAdultRate = getRates.get(3).getAttribute("value");
			logger.info("getAdultRate: " + getAdultRate);

			getChildRate = getRates.get(4).getAttribute("value");
			logger.info("getChildRate: " + getChildRate);

			chargeforAdditionalAdultsChilds = "yes";

		}

		String ratePerNight = "0";
		String proRateAdditionalAdult = "0";
		String proRateAdditionalChild = "0";

		String pathOfProRateStayInRoomClass = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + getRoomClass
				+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]";
		boolean isProRateStayCheckbox = driver.findElements(By.xpath(pathOfProRateStayInRoomClass)).size() > 0;
		String isProRateStayChecked = "no";
		// first check pro rate stay check box is displaying or not
		if (isProRateStayCheckbox) {

			// second check pro rate check box is enable or disable
			String pathOfProRateStayRateCheckBox = "//li[contains(@class,'IntervalRatePlan')]//span[text()='"
					+ getRoomClass
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//label[contains(@class,'inrd-checkbox')]";
			WebElement getProRateStayCheckbox = driver.findElement(By.xpath(pathOfProRateStayRateCheckBox));
			if (getProRateStayCheckbox.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {

				String pathOfProRateStayRatePerNight = "//li[contains(@class,'IntervalRatePlan')]//span[text()='"
						+ getRoomClass
						+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";

				logger.info("pathOfProRateStayRatePerNight: " + pathOfProRateStayRatePerNight);
				List<WebElement> proRateStayValue = driver.findElements(By.xpath(pathOfProRateStayRatePerNight));
				ratePerNight = proRateStayValue.get(1).getAttribute("value");

				logger.info("ratePerNight: " + ratePerNight);
				isProRateStayChecked = "yes";

				// here verify additional adult and child checked or not for
				// per night
				if (toggleBtnForChargeforAdditionalAdultChild.getAttribute("class").contains("ant-switch-checked")) {
					proRateAdditionalAdult = proRateStayValue.get(2).getAttribute("value");
					logger.info("proRateAdditionalAdult: " + proRateAdditionalAdult);
					proRateAdditionalChild = proRateStayValue.get(3).getAttribute("value");
					logger.info("proRateAdditionalChild: " + proRateAdditionalChild);

				}
			}

		}

		rates.add(getBaseRate);
		testSteps.add("Base rate: " + getBaseRate);
		rates.add(getAdultCapacity);
		testSteps.add("Adults capacity: " + getAdultCapacity);

		rates.add(getChildCapacity);
		testSteps.add("Childs capacity: " + getChildCapacity);

		rates.add(chargeforAdditionalAdultsChilds);
		testSteps.add("Is additional adults/child on? " + chargeforAdditionalAdultsChilds);

		rates.add(getMaxAdult);
		testSteps.add("Max adults: " + getMaxAdult);
		rates.add(getMaxPerson);
		testSteps.add("Max persons: " + getMaxPerson);

		rates.add(getAdultRate);
		testSteps.add("Adults rate: " + getAdultRate);

		rates.add(getChildRate);
		testSteps.add("Childs rate: " + getChildRate);

		rates.add(isProRateStayChecked);
		testSteps.add("Is pro stay rate on? " + isProRateStayChecked);

		rates.add(ratePerNight);
		testSteps.add("Rate per night? " + ratePerNight);

		rates.add(proRateAdditionalAdult);
		testSteps.add("Per night adults rate " + proRateAdditionalAdult);

		rates.add(proRateAdditionalChild);
		testSteps.add("Per night childs rate " + proRateAdditionalChild);

		mapRoomClassWithRates.put(getRoomClass, rates);
		rateGridLogger.info("RoomClass: " + getRoomClass);
		// }

		return rates;
	}

	public ArrayList<String> updateRoomClassBaserate(WebDriver driver, ArrayList<String> RoomClasses, String baseRate,
			ArrayList<String> testSteps) throws InterruptedException {

		for (int i = 0; i < RoomClasses.size(); i++) {
			String enterRate = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + RoomClasses.get(i)
					+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";

			logger.info(enterRate);
			List<WebElement> element = driver.findElements(By.xpath(enterRate));
			Utility.ScrollToElement_NoWait(element.get(0), driver);
			testSteps.add("Room Class : " + RoomClasses.get(i));
			element.get(0).click();
			clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
			logger.info("RatePerNight : " + baseRate);
			element.get(0).sendKeys(baseRate);
			testSteps.add("Enter base rate:  " + baseRate);
		}

		return testSteps;
	}

	public ArrayList<String> updateProRateWithPackageAtClassLevel(WebDriver driver, String RoomClasses,
			boolean isAdditionalAdultChild, boolean isProRateChecked, String customRatePerNight, String adultRate,
			String childRate, ArrayList<String> testSteps) throws InterruptedException {

		if (isProRateChecked) {

			testSteps.add("OverRide rate per night for room class: " + RoomClasses);
			String pathOfRatePerNight = "//li[contains(@class,'IntervalRatePlan ')]//span[text()='" + RoomClasses
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";

			boolean size = driver.findElements(By.xpath(pathOfRatePerNight)).size() > 0;
			logger.info(" is main checkbox checked: " + size);

			if (!size) {
				driver.findElement(By.xpath("//span[contains(@class,'ProCheck')]")).click();
			}
			String childpath = "(//li[contains(@class,'IntervalRatePlan ')]//span[text()='" + RoomClasses
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//label//span)[1]";
			WebElement element = driver.findElement(By.xpath(childpath));
			Utility.ScrollToElement_NoWait(element, driver);
			if (!element.getAttribute("class").contains("ant-checkbox-checked")) {
				element.click();
			}
			List<WebElement> ratePerNight = driver.findElements(By.xpath(pathOfRatePerNight));
			logger.info("splitCustomRatePerNight: " + RoomClasses);

			ratePerNight.get(1).click();
			Wait.wait1Second();
			clearInputField(driver, ratePerNight.get(1), ratePerNight.get(1).getAttribute("value"));
			Wait.wait1Second();
			ratePerNight.get(1).sendKeys(customRatePerNight);
			testSteps.add("Custom Rate Per Night " + customRatePerNight);

			logger.info("Boolean.parseBoolean(isCustomRatePerNightAdultandChild): " + isAdditionalAdultChild);
			if (isAdditionalAdultChild) {

				ratePerNight.get(2).click();
				clearInputField(driver, ratePerNight.get(2), ratePerNight.get(2).getAttribute("value"));
				ratePerNight.get(2).sendKeys(adultRate);
				testSteps.add("Custom Add.adult/night " + adultRate);

				ratePerNight.get(3).click();
				clearInputField(driver, ratePerNight.get(3), ratePerNight.get(3).getAttribute("value"));
				ratePerNight.get(3).sendKeys(childRate);
				testSteps.add("Custom Add.child/night " + childRate);

			}
		} else {

			String childpath = "(//li[contains(@class,'IntervalRatePlan ')]//span[text()='" + RoomClasses
					+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//label//span)[1]";
			WebElement element = driver.findElement(By.xpath(childpath));
			Utility.ScrollToElement_NoWait(element, driver);
			if (element.getAttribute("class").contains("ant-checkbox-checked")) {
				element.click();
			}

		}

		return testSteps;
	}

	public void updateNoCheckInRule(WebDriver driver, String roomClassName, String channelName, boolean override,
			int index) throws InterruptedException {

		String rule = "//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
				+ "')]/../../..//div[text()='No Check In']/following-sibling::div/div/div";
		Wait.WaitForElement(driver, rule);
		String ruleValue = "";
		rule = "(//div[contains(text(),'" + roomClassName + "')]/../../.." + "//div[contains(text(),'" + channelName
				+ "')]/../../..//div[text()='No Check In']//" + "following-sibling::div/div/div)[" + (index + 1) + "]";
		System.out.println(rule);
		ruleValue = driver.findElement(By.xpath(rule)).getAttribute("class");
		if (override) {
			if (ruleValue.contains("noValue")) {
				Wait.wait1Second();
				driver.findElement(By.xpath(rule)).click();
				Wait.wait5Second();
			} else {
				ruleValue = "Yes";
			}

		}

		else {
			if (!ruleValue.contains("noValue")) {
				Wait.wait1Second();
				driver.findElement(By.xpath(rule)).click();
				Wait.wait5Second();
			}
		}
	}

	public void updateNoCheckOutRule(WebDriver driver, String roomClassName, String channelName, boolean override,
			int index) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String rule = "//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text(),'" + channelName
				+ "')]/../../..//div[text()='No Check Out']/following-sibling::div/div/div";
		Wait.WaitForElement(driver, rule);
		String ruleValue = "";
		rule = "(//div[contains(text(),'" + roomClassName + "')]/../../..//div[contains(text()," + "'" + channelName
				+ "')]/../../..//div[text()='No Check Out']/following-sibling::div" + "/div/div)[" + (index + 1) + "]";
		ruleValue = driver.findElement(By.xpath(rule)).getAttribute("class");
		if (override) {
			if (ruleValue.contains("noValue")) {
				Wait.wait1Second();
				driver.findElement(By.xpath(rule)).click();
				Wait.wait5Second();
			} else {
				ruleValue = "Yes";
			}

		}

		else {
			if (!ruleValue.contains("noValue")) {
				Wait.wait1Second();
				driver.findElement(By.xpath(rule)).click();
				Wait.wait5Second();

			}
		}

	}

	public String getToaster(WebDriver driver) {
		// div[@class='Toastify']//div//div[contains(@class,'Toastify__toast--success')]
		Elements_RatesGrid elements_RatesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.ToasterMessage);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.ToasterMessage), driver);

		return elements_RatesGrid.ToasterMessage.getText();
	}

	public void updateSeasonLevelRules(WebDriver driver, String minStayValue, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		NightlyRate nightlyRate = new NightlyRate();
		nightlyRate.clickRulesRestrictionOnSeason(driver, test_steps);

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
		ratesGrid.RatePlan_Season_SeasonMinNightsRuleValue.sendKeys(minStayValue);

		String noCheckIn = "//span[text()='No check-in']/preceding-sibling::span/input/..";

		if (!driver.findElement(By.xpath(noCheckIn)).getAttribute("class").contains("checked")) {
			Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckIn);
		}

		String monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
			test_steps.add("No Check in on monday is already selected");
			logger.info("No Check in on monday is already selected");
		}

		monNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
			test_steps.add("No Check in on monday is already unChecked");
			logger.info("No Check in on monday is already unChecked");
		}

		String tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";

		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
			test_steps.add("No Check in on tuesday is already selected");
			logger.info("No Check in on tuesday is already selected");
		}

		tueNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Tue']/following-sibling::span";
		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
			test_steps.add("No Check in on tuesday is already unChecked");
			logger.info("No Check in on tuesday is already unChecked");
		}

		String wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
			test_steps.add("No Check in on wednesday is already selected");
			logger.info("No Check in on wednesday is already selected");
		}
		wedNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
			test_steps.add("No Check in on wednesday is already unChecked");
			logger.info("No Check in on wednesday is already unChecked");
		}

		String thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
			test_steps.add("No Check in on thursday is already selected");
			logger.info("No Check in on thursday is already selected");
		}
		thuNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
			test_steps.add("No Check in on thursday is already unChecked");
			logger.info("No Check in on thursday is already unChecked");
		}
		String friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
			test_steps.add("No Check in on friday is already selected");
			logger.info("No Check in on friday is already selected");
		}
		friNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Fri']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
			test_steps.add("No Check in on tuesday is already unChecked");
			logger.info("No Check in on tuesday is already unChecked");
		}
		String satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
			test_steps.add("No Check in on saturday is already selected");
			logger.info("No Check in on saturday is already selected");
		}
		satNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sat']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
			test_steps.add("No Check in on saturday is already unChecked");
			logger.info("No Check in on saturday is already unChecked");
		}
		String sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
			test_steps.add("No Check in on sunday is already selected");
			logger.info("No Check in on sunday is already selected");
		}
		sunNoCheckIn = "//span[text()='No check-in']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
		logger.info(driver.findElement(By.xpath(sunNoCheckIn)).isSelected());

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
			test_steps.add("No Check in on sunday is already unChecked");
			logger.info("No Check in on sunday is already unChecked");
		}

		String noCheckOut = "//span[text()='No check-out']/preceding-sibling::span/input/..";
		if (!driver.findElement(By.xpath(noCheckOut)).getAttribute("class").contains("checked")) {
			Utility.clickThroughJavaScript(driver, ratesGrid.RatePlan_Season_SeasonNoCheckOut);
		}

		monNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
			test_steps.add("No check-out on monday is already selected");
			logger.info("No check-out on monday is already selected");
		}
		monNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Mon']/following-sibling::span";
		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(monNoCheckIn)))) {
			test_steps.add("No check-out on monday is already unChecked");
			logger.info("No check-out on monday is already unChecked");
		}

		tueNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";

		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
			test_steps.add("No check-out on tuesday is already selected");
			logger.info("No check-out on tuesday is already selected");
		}
		tueNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Tue']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(tueNoCheckIn)))) {
			test_steps.add("No check-out on tuesday is already unChecked");
			logger.info("No check-out on tuesday is already unChecked");
		}

		wedNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
			test_steps.add("No check-out on wednesday is already selected");
			logger.info("No check-out on wednesday is already selected");
		}
		wedNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Wed']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(wedNoCheckIn)))) {
			test_steps.add("No check-out on wednesday is already unChecked");
			logger.info("No check-out on wednesday is already unChecked");
		}

		thuNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
			test_steps.add("No check-out on thursday is already selected");
			logger.info("No check-out on thursday is already selected");
		}
		thuNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Thu']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(thuNoCheckIn)))) {
			test_steps.add("No check-out on thursday is already unChecked");
			logger.info("No check-out on thursday is already unChecked");
		}
		friNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
			test_steps.add("No check-out on friday is already selected");
			logger.info("No check-out on friday is already selected");
		}

		friNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Fri']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(friNoCheckIn)))) {
			test_steps.add("No check-out on tuesday is already unChecked");
			logger.info("No check-out on tuesday is already unChecked");
		}

		satNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
			test_steps.add("No check-out on saturday is already selected");
			logger.info("No check-out on saturday is already selected");
		}
		satNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sat']/following-sibling::span";

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(satNoCheckIn)))) {
			test_steps.add("No check-out on saturday is already unChecked");
			logger.info("No check-out on saturday is already unChecked");
		}

		sunNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
		if (Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
			test_steps.add("No check-out on sunday is already selected");
			logger.info("No check-out on sunday is already selected");
		}
		sunNoCheckIn = "//span[text()='No check-out']/../following-sibling::div//span[text()='Sun']/following-sibling::span";
		logger.info(driver.findElement(By.xpath(sunNoCheckIn)).isSelected());

		if (!Utility.isSelectedWithBefore(driver, driver.findElement(By.xpath(sunNoCheckIn)))) {
			test_steps.add("No check-out on sunday is already unChecked");
			logger.info("No check-out on sunday is already unChecked");
		}
	}

	public HashMap<String, ArrayList<String>> getRateExAdExChForChannel(WebDriver driver, String roomClassName,
			String channelName, int days) throws InterruptedException {
		String xpath = "//div[@class='roomClassName'and contains(@title,'" + roomClassName + "')]"
				+ "//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]" + "//div[@title='"
				+ channelName + "']//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))]";
		ArrayList<String> roomRates = new ArrayList<String>();
		ArrayList<String> listExtrAdult = new ArrayList<String>();
		ArrayList<String> listExtraChild = new ArrayList<String>();
		ArrayList<String> listOfOverRide = new ArrayList<String>();
		HashMap<String, ArrayList<String>> listOfRates = new HashMap<String, ArrayList<String>>();
		List<WebElement> overRideList = null;
		String roomRate = null, extraChild = null, extraAdult = null;
		Wait.WaitForElement(driver, xpath);
		overRideList = driver.findElements(By.xpath(xpath));
		logger.info("overRideList: " + overRideList.size());
		for (int i = 0; i < days; i++) {
			Utility.ScrollToViewElementINMiddle(driver, overRideList.get(i));
			Utility.clickThroughJavaScript(driver, overRideList.get(i));
			String getOverirde = "no";
			roomRate = getRoomRate(driver);
			extraAdult = getExtraAdult(driver);
			extraChild = getExtraChild(driver);
			roomRates.add(roomRate);
			listExtrAdult.add(extraAdult);
			listExtraChild.add(extraChild);
			boolean isOverirde = getRoomRateOverride(driver);
			if (isOverirde) {
				getOverirde = "yes";
			}
			listOfOverRide.add(getOverirde);
		}
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Utility.ScrollToElement(elements.rateGridDanger, driver);
		Utility.clickThroughJavaScript(driver, elements.rateGridDanger);
		logger.info(roomRates);
		logger.info(listExtrAdult);
		logger.info(listExtraChild);
		logger.info(listOfOverRide);
		listOfRates.put("rates", roomRates);
		listOfRates.put("adults", listExtrAdult);
		listOfRates.put("childs", listExtraChild);
		listOfRates.put("override", listOfOverRide);
		return listOfRates;

	}

	public boolean getRoomRateOverride(WebDriver driver) {
		String roomRate = null;
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		boolean isOverRide = elements.GetRatesOverRide.size() > 0;
		return isOverRide;
	}

	/*
	 * #############################################################################
	 * #############################################################################
	 * ################
	 * 
	 * ' Method Name: <createParentRatePlan> ' Description: <Create parent rate plan
	 * of any typr Nightly, Package or Interval> ' Input parameters: < WebDriver
	 * driver, String.....> ' Return value: <ArrayList> ' Created By: <Adhnan
	 * Ghaffar> ' Created On: <11/05/2020>
	 * 
	 * #############################################################################
	 * #############################################################################
	 * ################
	 */
	public ArrayList<String> createParentRatePlan(WebDriver driver, String delim, String parentRatePlanType,
			String parentRatePlanName, String parentRateFolioName, String parentRateDescription, String parentRoomClass,
			String parentRoomClassSize, String parentBaseAmount, String addtionalAdult, String additionalChild,
			String intervalRatePlanIntervalValue, String isDefaultProrateChecked, String packageRatePlanRateType,
			String packageRatePlanProductName, String packageratePlanProductFirstCalculationMethod,
			String packageratePlanProductsecondCalculationMethod, String packageRatePlanProductAmount, String channels,
			String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights,
			String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
			String isWithInDaysReq, String withInDaysCount, String promoCode, String isPolicesReq, String policiesType,
			String policiesName, String seasonDelim, String seasonName, String seasonStartDate, String seasonEndDate,
			String isMonDay, String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay,
			String isSunDay, String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults,
			String maxPersons, String additionalAdultsPerNight, String additionalChildPerNight,
			String isAddRoomClassInSeason, String extraRoomClassesInSeason, String extraRoomClassRatePerNight,
			String extraRoomClassMaxAdults, String extraRoomClassMaxPersons,
			String extraRoomClassAdditionalAdultsPerNight, String extraRoomClassAdditionalChildPerNight,
			String isSeasonLevelRules, String isAssignRulesByRoomClass, String seasonRuleSpecificRoomClasses,
			String seasonRuleType, String seasonRuleMinStayValue, String isSeasonRuleOnMonday,
			String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday, String isSeasonRuleOnThursday,
			String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday, String isSeasonRuleOnSunday,
			String isSeasonPolicies, String seasonPolicyType, String seasonPolicyValues,
			String isAssignPoliciesByRoomClass, String seasonPolicySpecificRoomClasses, String isProRateStayInSeason,
			String isProRateInRoomClass, String isCustomPerNight, String customRoomClasses, String customRatePerNight,
			String isCustomRatePerNightAdultandChild, String customRateChildPerNight, String customRateAdultdPerNight,
			String roomClassesWithProRateUnchecked) throws Exception {

		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RatePackage ratePackage = new RatePackage();
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();

		String restrictionsSummary = null;
		boolean isProrateCheckbox = false;
		boolean isIntervalRateplan = false;
		String summaryChannels = null;
		String summaryRoomClasses = null;

		testSteps.add("=================== CREATE '" + parentRatePlanType.toUpperCase()
				+ "' RATE PLAN ======================");
		logger.info("=================== CREATE '" + parentRatePlanType.toUpperCase()
				+ "' RATE PLAN ======================");

		getTestSteps.clear();
		getTestSteps = ratesGrid.clickRateGridAddRatePlan(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = ratesGrid.clickRateGridAddRatePlanOption(driver, parentRatePlanType);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType);
		testSteps.addAll(getTestSteps);

		testSteps.add(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
		logger.info(
				"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

		nightlyRate.enterRatePlanName(driver, parentRatePlanName, testSteps);
		nightlyRate.enterRateFolioDisplayName(driver, parentRateFolioName, testSteps);
		nightlyRate.enterRatePlanDescription(driver, parentRateDescription, testSteps);
		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		if (parentRatePlanType.equalsIgnoreCase("Package rate plan")) {

			testSteps.add("=================== SELECT RATE TYPE ======================");
			logger.info("=================== SELECT RATE TYPE ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.selectParentRatePlan(driver, packageRatePlanRateType);
			testSteps.addAll(getTestSteps);
			if (packageRatePlanRateType.equalsIgnoreCase("Interval rates")) {
				isIntervalRateplan = true;
				getTestSteps.clear();
				getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
				testSteps.addAll(getTestSteps);

				isProrateCheckbox = Boolean.parseBoolean(isDefaultProrateChecked);
				getTestSteps.clear();
				getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, isProrateCheckbox);
				testSteps.addAll(getTestSteps);
			}

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== ADDING PRODUCT IN PACKAGE RATE PLAN ======================");
			logger.info("=================== ADDING PRODUCT IN PACKAGE RATE PLAN ======================");

			getTestSteps.clear();
			getTestSteps = ratePackage.addProducts(driver, packageRatePlanProductName, packageRatePlanProductAmount,
					packageratePlanProductFirstCalculationMethod, packageratePlanProductsecondCalculationMethod);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = nightlyRate.clickNextButton(driver);
			testSteps.addAll(getTestSteps);
		}

		if (parentRatePlanType.contains("Interval")) {
			isIntervalRateplan = true;
			getTestSteps.clear();
			getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== ENTER INTERVAL RATE PLAN LENGTH ======================");
			logger.info("=================== ENTER INTERVAL RATE PLAN LENGTH ======================");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.enterInterval(driver, intervalRatePlanIntervalValue);
			testSteps.addAll(getTestSteps);
			isProrateCheckbox = Boolean.parseBoolean(isDefaultProrateChecked);
			getTestSteps.clear();
			getTestSteps = ratesGrid.byDefaultProrateCheckbox(driver, isProrateCheckbox);
			testSteps.addAll(getTestSteps);
			nightlyRate.clickNextButton(driver, testSteps);
		}

		testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
		logger.info("=================== SELECT DISTRIBUTED CHANNELS ======================");
		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", parentRatePlanType, testSteps);
		nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", parentRatePlanName, testSteps);
		if (parentRatePlanType.contains("Interval")) {
			String IsProrateCheckboxChecked = intervalRatePlanIntervalValue + " nights;";
			if (isProrateCheckbox) {
				IsProrateCheckboxChecked = IsProrateCheckboxChecked + " " + "prorate cost for partial stay";
			}
			nightlyRate.verifyTitleSummaryValue(driver, "Interval Length", IsProrateCheckboxChecked, testSteps);
		}
		nightlyRate.selectChannels(driver, channels, true, testSteps);
		if (parentRatePlanType.equalsIgnoreCase("Interval rate plan")) {
			summaryChannels = channels;
		} else {
			summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
		}
		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels);
		testSteps.addAll(getTestSteps);

		testSteps.add("=================== SELECT ROOM CLASSES ======================");
		logger.info("=================== SELECT ROOM CLASSES ======================");
		ArrayList<String> roomClasses = new ArrayList<String>();
		roomClasses = nightlyRate.getAllRoomClassesNames(driver);
		if (!parentRoomClass.equals("") || parentRoomClass != null) {
			parentRoomClass = nightlyRate.verifyRoomClassesExist(driver, parentRoomClass, roomClasses);
		}
		if (parentRoomClass.equals("")) {
			logger.info("Room Classes : " + roomClasses);
			parentRoomClass = "";
			for (int i = 0; i < Integer.parseInt(parentRoomClassSize); i++) {
				if (i < roomClasses.size() && i < 6) {
					if (i != 0) {
						parentRoomClass = parentRoomClass + delim;
					}
					parentRoomClass = parentRoomClass + roomClasses.get(i);

				}
			}
		}
		logger.info("Parent Room Clas : " + parentRoomClass);
		nightlyRate.selectRoomClasses(driver, parentRoomClass, true, testSteps);
		if (parentRoomClass.equals("All")) {
			summaryRoomClasses = "All room classes selected";
		} else {
			summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
		}

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses);
		testSteps.addAll(getTestSteps);
		nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
				Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
				Boolean.parseBoolean(isMoreThanDaysReq), moreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
				withInDaysCount, promoCode, testSteps);

		restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);

		getTestSteps.clear();
		getTestSteps = nightlyRate.clickNextButton(driver);
		testSteps.addAll(getTestSteps);
		try {
			nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);
		} catch (Exception e) {

		}

		nightlyRate.selectPolicy(driver, policiesType, policiesName, Boolean.parseBoolean(isPolicesReq), testSteps);

		HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, policiesName,
				Boolean.parseBoolean(isPolicesReq), testSteps);
		nightlyRate.clickNextButton(driver, testSteps);
		nightlyRate.verifyPolicyTitleSummaryValue(driver, policiesName, allPolicyDesc,
				Boolean.parseBoolean(isPolicesReq), testSteps);

		testSteps.add("=================== CREATE SEASON ======================");
		logger.info("=================== CREATE SEASON ======================");
		nightlyRate.clickCreateSeason(driver, testSteps);
		nightlyRate.createUpdateSingleOrMultipleSeason(driver, testSteps, false, seasonStartDate, seasonEndDate,
				seasonName, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
				parentRoomClass, isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
				additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason,
				extraRoomClassRatePerNight, extraRoomClassMaxAdults, extraRoomClassMaxPersons,
				extraRoomClassAdditionalAdultsPerNight, extraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass,
				isSeasonLevelRules, seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue,
				isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday,
				isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday, isSeasonPolicies, seasonPolicyType,
				seasonPolicyValues, isAssignPoliciesByRoomClass, seasonPolicySpecificRoomClasses,
				isDefaultProrateChecked, isProRateStayInSeason, isProRateInRoomClass, roomClassesWithProRateUnchecked,
				isCustomPerNight, customRoomClasses, customRatePerNight, customRateAdultdPerNight,
				customRateChildPerNight, isCustomRatePerNightAdultandChild, false, intervalRatePlanIntervalValue,
				isIntervalRateplan);

		nightlyRate.clickCompleteChanges(driver, testSteps);

		try {
			nightlyRate.clickSaveAsActive(driver, testSteps);
		} catch (Exception f) {
			nightlyRate.clickCompleteChanges(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);
		}
		testSteps.add("PARENT " + parentRatePlanType.toUpperCase() + " CREATED");
		logger.info("PARENT " + parentRatePlanType.toUpperCase() + " CREATED");

		navigation.RatesGrid(driver);
		testSteps.add("Navigated to RatesGrid");
		testSteps.add("=== " + "Verify the newly created Parent(" + parentRatePlanType.toUpperCase()
				+ ") Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ===");
		logger.info("=== " + "Verify the newly created Parent(" + parentRatePlanType.toUpperCase()
				+ ") Rate Plan is seen in the Rates Grid's Rate Plan drop down".toUpperCase() + " ===");
		try {
//				driver.navigate().refresh();
//				Wait.wait3Second();
//				driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
		} catch (Exception e) {
			driver.navigate().refresh();
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, parentRatePlanName);
		}
		testSteps.add(
				"Successfully verified that newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down");
		logger.info(
				"Successfully verified that newly created Parent(Nightly) Rate Plan is seen in the Rates Grid's Rate Plan drop down");

		return testSteps;

	}

	public ArrayList<String> verifyRatePlanSetToDefault(WebDriver driver, String ratePlanName)
			throws InterruptedException {
		ArrayList<String> testSteps = new ArrayList<>();
		NightlyRate nightlyRate = new NightlyRate();
		Wait.WaitForElement(driver, OR_RateGrid.defaltRatePlan);
		WebElement defaultRatePlanElement = driver.findElement(By.xpath(OR_RateGrid.defaltRatePlan));

		if (defaultRatePlanElement.getAttribute("class").contains("ant-switch-checked")) {
			testSteps.add(ratePlanName + " already set to default.");
			logger.info(ratePlanName + " already set to default.");
		} else {
			Wait.WaitForElement(driver, OR_RateGrid.defaltRatePlan);
			defaultRatePlanElement.click();
			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			testSteps.add("Successfully clicked on default rate plan as " + ratePlanName + " was not set by default.");
			logger.info("Successfully clicked on default rate plan as" + ratePlanName + " was not set by default.");

		}
		return testSteps;
	}

	public boolean verifyRatePlanSetToDefault(WebDriver driver, ArrayList<String> testSteps, String ratePlanName)
			throws InterruptedException {

		NightlyRate nightlyRate = new NightlyRate();
		Wait.WaitForElement(driver, OR_RateGrid.defaltRatePlan);
		WebElement defaultRatePlanElement = driver.findElement(By.xpath(OR_RateGrid.defaltRatePlan));
		boolean defaultStatus = false;
		if (defaultRatePlanElement.getAttribute("class").contains("ant-switch-checked")) {
			testSteps.add(ratePlanName + " already set to default.");
			logger.info(ratePlanName + " already set to default.");
			defaultStatus = true;
		} else {
			Wait.WaitForElement(driver, OR_RateGrid.defaltRatePlan);
			defaultRatePlanElement.click();
			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			testSteps.add("Successfully clicked on default rate plan as " + ratePlanName + " was not set by default.");
			logger.info("Successfully clicked on default rate plan as" + ratePlanName + " was not set by default.");
		}
		return defaultStatus;
	}

	public ArrayList<String> enterRoomClassBaserate(WebDriver driver, String RoomClasses, String baseRate,
			ArrayList<String> testSteps) throws InterruptedException {

		String[] splitRoomClassName = RoomClasses.split(Utility.DELIM);
		String[] splitRatePerNight = baseRate.split(Utility.DELIM);

		for (int i = 0; i < splitRoomClassName.length; i++) {
			String enterRate = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + splitRoomClassName[i]
					+ "']//parent::label//following-sibling::span[@class='additionalInfo sm-input']//input[@class='ant-input-number-input']";

			logger.info(enterRate);
			List<WebElement> element = driver.findElements(By.xpath(enterRate));
			Utility.ScrollToElement_NoWait(element.get(0), driver);
			testSteps.add("Room Class : " + splitRoomClassName[i]);
			element.get(0).click();
			clearInputField(driver, element.get(0), element.get(0).getAttribute("value"));
			logger.info("RatePerNight : " + splitRatePerNight[i]);
			element.get(0).sendKeys(splitRatePerNight[i]);
			testSteps.add("Enter base rate:  " + splitRatePerNight[i]);
		}

		return testSteps;
	}

	public ArrayList<String> getSeasonLevelRoomClasses(WebDriver driver) {

		String selectedRoomClassesPath = "//ul[@class='selected-listItems']";
		ArrayList<String> selectedRoomClasses = new ArrayList<>();
		if (driver.findElements(By.xpath(selectedRoomClassesPath)).size() > 0) {
			String roomClassesPath = "//ul[@class='selected-listItems']/li[@class='IntervalRatePlan ']/div/label/span[2]";

			List<WebElement> lstClasses = driver.findElements(By.xpath(roomClassesPath));
			for (int index = 0; index < lstClasses.size(); index++) {
				selectedRoomClasses.add(lstClasses.get(index).getText());
			}

		}
		return selectedRoomClasses;

	}

	public ArrayList<String> uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(WebDriver driver, String roomClassName,
			String channelName, String ruleName) throws InterruptedException {
		ArrayList<String> data = new ArrayList<String>();
		String xpath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
				+ "//../..//following-sibling::div//div[@title='" + channelName + "']"
				+ "//../..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='" + ruleName
				+ "']/following-sibling::div/div/div";
		Wait.WaitForElement(driver, xpath);
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));

		for (int index = 1; index <= labelValues.size(); index++) {
			String xpathOfCheckInCheckOutElement = "(//div[@class='DatesTable']//div[contains(@title,'" + roomClassName
					+ "')]" + "//../..//following-sibling::div//div[@title='" + channelName + "']"
					+ "//../..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='" + ruleName
					+ "']/following-sibling::div/div/div)[" + index + "]";
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-200)");
			WebElement element = driver.findElement(By.xpath(xpathOfCheckInCheckOutElement));
			if (element.getAttribute("class").contains("rt-onHover  enabled")) {
				Wait.WaitForElement(driver, xpathOfCheckInCheckOutElement);
				element.click();
				String alertPath = "//div[@role='alert'][text()='Rule saved successfully']";
				Wait.waitForElementToBeVisibile(By.xpath(alertPath), driver);
			}
		}
		logger.info(data);
		return data;
	}

	public void sendDataIfMinStayNightsDataNotExist(WebDriver driver, String roomClassName, String channelName,
			String ruleName, String checkInDate, String checkOutDate, String dateFormat, String minNightsValue)
			throws ParseException, InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> minStayValues = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<Integer, String> minNightStayDataValues = new HashMap<Integer, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		int index = 0;

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, checkInDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, checkOutDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		minStayValues = getRuleDataValuesForMinStay(driver, roomClassName, channelName, ruleName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					if (minStayValues.get(i).isEmpty()) {
						index = i + 1;
						String xpath = "(//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
								+ "//../..//following-sibling::div//div[@title='" + channelName + "']"
								+ "//../..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
								+ ruleName + "']/following-sibling::div//input)[" + index + "]";
						Wait.WaitForElement(driver, xpath);
						WebElement minStayNightsElement = driver.findElement(By.xpath(xpath));
						minStayNightsElement.click();
						minStayNightsElement.sendKeys(minNightsValue);
						String path = "//span[text()='Daily Occupancy & Rates']";
						Wait.WaitForElement(driver, path);
						driver.findElement(By.xpath(path)).click();
						String alertPath = "//div[@role='alert'][text()='Rule saved successfully']";
						Wait.waitForElementToBeVisibile(By.xpath(alertPath), driver);
						minNightStayDataValues.put(i, minNightsValue);
					} else {
						minNightStayDataValues.put(i, minStayValues.get(i));
					}
				}
			}

		}

	}

	public void overrideRateOnGrid(WebDriver driver, ArrayList<String> testSteps, String roomClass, String channelName,
			String newRateValue, int index) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		String path = "//div[contains(text(),'" + roomClass + "')]"
				+ "/ancestor::div/..//following-sibling::div//div[@title='" + channelName + "']"
				+ "/parent::div/following-sibling::div/div[contains(@class,'RegularRate')]";
		List<WebElement> webElements;
		testSteps.add("========Update Rate for Channel<b> " + roomClass + " -- " + channelName + "</b>========");
		Wait.WaitForElement(driver, path);
		webElements = driver.findElements(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, webElements.get(index));
		Utility.clickThroughJavaScript(driver, webElements.get(index));
		if (webElements.size() == 0) {
			return;
		}
		elements.rateGridRoomRate.clear();
		elements.rateGridRoomRate.sendKeys(newRateValue);
		testSteps.add("Enter New Rate: <b>" + newRateValue + "</b>");
		logger.info("Enter New Rate: " + newRateValue);
		elements.rateGridSuccess.click();
		testSteps.add("Click on Success Icon");
		logger.info("Click on Success Icon");
		Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
	}

	public boolean isRatesOverrideButtonDisplayed(WebDriver driver) {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		boolean isRemoveOverride = false;
		try {
			if (elements.REMOVE_OVERRIDE_BUTTON.isDisplayed()) {
				isRemoveOverride = true;
			} else {
				isRemoveOverride = false;
			}
		} catch (Exception e) {
			isRemoveOverride = false;
		}
		return isRemoveOverride;
	}

	public void removeOverride(WebDriver driver, String roomClass, ArrayList<String> days, ArrayList<String> test_steps)
			throws Exception {

		selectDateFromRatesGridCalendar(driver, test_steps, days.get(0));
		for (int i = 1; i <= days.size(); i++) {
			String clickCell = "(//div[text()='" + roomClass
					+ "']/../..//div/following::div/div[@class=' RegularRate' or @class='has-changes Override' or @class='has-changes RegularRate'])["
					+ i + "]";
			Wait.waitForElementToBeVisibile(By.xpath(clickCell), driver);
			driver.findElement(By.xpath(clickCell)).click();
			boolean isOverride = isRateOverride(driver, roomClass);
			if (isOverride) {
				String Override = "(//div[text()='" + roomClass
						+ "']/../..//div[@class='has-changes Override' or @class='has-changes RegularRate'])/following::button/b";

				driver.findElement(By.xpath(Override)).click();
				Wait.waitForElementToBeVisibile(By.xpath("//div[text()='Deleted rate override successfully']"), driver);

			}
		}
	}

	public void removeOverrideChannelLevel(WebDriver driver, String roomClass, ArrayList<String> days, String channel,
			ArrayList<String> test_steps) throws Exception {

		selectDateFromRatesGridCalendar(driver, test_steps, days.get(0));
		expandRoomClass(driver, roomClass);
		for (int i = 1; i <= days.size(); i++) {
			String clickCell = "(//div[text()='" + roomClass + "']/following::div[text()='" + channel
					+ "']/following::div[@class=' RegularRate' or @class='has-changes Override'])[" + i + "]";
			Wait.waitForElementToBeVisibile(By.xpath(clickCell), driver);
			driver.findElement(By.xpath(clickCell)).click();
			boolean isOverride = isRateOverrideChannel(driver, roomClass);
			if (isOverride) {
				String Override = "//b[text()='Remove Override']";
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click()", driver.findElement(By.xpath(Override)));
				System.out.println("Clicked");
				// driver.findElement(By.xpath(Override)).click();
				Wait.waitForElementToBeVisibile(By.xpath("//div[text()='Deleted rate override successfully']"), driver);

			}
		}
	}

	public boolean isRateOverrideChannel(WebDriver driver, String roomClass) {
		boolean isOverride = false;
		String Override = "//b[text()='Remove Override']";
		try {
			if (driver.findElement(By.xpath(Override)).isDisplayed()) {
				isOverride = true;
			}
		} catch (Exception e) {
			isOverride = false;
		}
		return isOverride;

	}

	public boolean isRateOverride(WebDriver driver, String roomClass) {
		boolean isOverride = false;
		String Override = "(//div[text()='" + roomClass
				+ "']/../..//div[@class='has-changes Override' or @class='has-changes RegularRate'])/following::button/b";
		try {
			if (driver.findElement(By.xpath(Override)).isDisplayed()) {
				isOverride = true;
			}
		} catch (Exception e) {
			isOverride = false;
		}
		return isOverride;

	}

	public void clickOnGivenRestrictions(WebDriver driver, String nameOfRestriction, boolean isCheck)
			throws InterruptedException {
		String path = "//span[text()='" + nameOfRestriction + "']//parent::label";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));

		boolean isRestrictionChecked = false;
		if (element.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {
			isRestrictionChecked = true;
		}
		if (isCheck) {
			if (isRestrictionChecked) {
				rateGridLogger.info(nameOfRestriction + " checkbox is already checked");
			} else {
				element.click();
				rateGridLogger.info("Clicked on +" + nameOfRestriction + " checkbox");
			}
		} else {
			if (isRestrictionChecked) {
				element.click();
				rateGridLogger.info(
						"Unchecked - Clicked on +" + nameOfRestriction + " checkbox because it's already checked");
			} else {
				rateGridLogger.info(nameOfRestriction + " checkbox is already Unchecked");
			}
		}
	}

	public void clickOnGivenPolicy(WebDriver driver, String nameOfPolicy, boolean isCheck) throws InterruptedException {
		String path = "//span[text()='" + nameOfPolicy + "']//parent::label";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));

		boolean isPolicyChecked = false;
		if (element.getAttribute("class").contains("ant-checkbox-wrapper-checked")) {
			isPolicyChecked = true;
		}
		if (isCheck) {
			if (isPolicyChecked) {
				rateGridLogger.info(nameOfPolicy + " checkbox is already checked");
			} else {
				element.click();
				rateGridLogger.info("Clicked on +" + nameOfPolicy + " checkbox");
			}
		} else {
			if (isPolicyChecked) {
				element.click();
				rateGridLogger
						.info("Unchecked - Clicked on +" + nameOfPolicy + " checkbox because it's already checked");
			} else {
				rateGridLogger.info(nameOfPolicy + " checkbox is already Unchecked");
			}
		}
	}

	public boolean removePolicyAtRatePlanLevel(WebDriver driver, ArrayList<String> test_steps, String policyType)
			throws Exception {
		String policyCheckBoxXpath = "//input[@class='ant-checkbox-input' and @value='" + policyType + "']";
		boolean isSaveRatePlan = false;
		boolean policyAppied = driver.findElement(By.xpath(policyCheckBoxXpath)).isSelected();
		if (policyAppied) {
			driver.findElement(By.xpath(policyCheckBoxXpath)).click();
			isSaveRatePlan = true;
		}
		test_steps.add("Removed rate plan level <b>" + policyType + "</b> policy");
		rateGridLogger.info("Removed rate plan level <b>" + policyType + "</b> policy");
		return isSaveRatePlan;
	}

	public boolean removePolicyAtSeasonTab(WebDriver driver, ArrayList<String> test_steps, String policyType)
			throws Exception {
		boolean isSaveRatePlan = false;
		String policyTypeXpath = "//div[@class='policy-set-item']//input[@class='ant-checkbox-input' and @value='"
				+ policyType + "']";
		boolean policyTypeSelected = driver.findElement(By.xpath(policyTypeXpath)).isSelected();
		if (policyTypeSelected) {
			driver.findElement(By.xpath(policyTypeXpath)).click();
			isSaveRatePlan = true;
		}
		test_steps.add("Removed season level <b>" + policyType + "</b> policy");
		rateGridLogger.info("Removed season level <b>" + policyType + "</b> policy");
		return isSaveRatePlan;
	}

	public boolean removeRestrictionsAtRatePlanLevel(WebDriver driver, ArrayList<String> test_steps,
			String RestrictionType) throws Exception {
		String RestrictionCheckBoxXpath = "//span[contains(text(),'" + RestrictionType
				+ "')]/preceding-sibling::span/input";
		boolean isSaveRatePlan = false;
		boolean RestrictionAppied = driver.findElement(By.xpath(RestrictionCheckBoxXpath)).isSelected();
		if (RestrictionAppied) {
			driver.findElement(By.xpath(RestrictionCheckBoxXpath)).click();
			isSaveRatePlan = true;
		}
		test_steps.add("Removed rate plan level <b>" + RestrictionType + "</b> policy");
		rateGridLogger.info("Removed rate plan level <b>" + RestrictionType + "</b> policy");
		return isSaveRatePlan;
	}

	public boolean removeRestrictionsAtSeasonTab(WebDriver driver, ArrayList<String> test_steps, String RestrictionType)
			throws Exception {
		boolean isSaveRatePlan = false;
		String RestrictionTypeXpath = "//span[contains(text(),'" + RestrictionType
				+ "')]/preceding-sibling::span/input";
		boolean RestrictionTypeSelected = driver.findElement(By.xpath(RestrictionTypeXpath)).isSelected();
		if (RestrictionTypeSelected) {
			driver.findElement(By.xpath(RestrictionTypeXpath)).click();
			isSaveRatePlan = true;
		}
		test_steps.add("Removed season level <b>" + RestrictionType + "</b> policy");
		rateGridLogger.info("Removed season level <b>" + RestrictionType + "</b> policy");
		return isSaveRatePlan;
	}

	public void removePoliciesAndRestrictionsFromRatePlanAndSeason(WebDriver driver, String ratePlanName,
			String checkInDate, ArrayList<String> test_steps) throws Exception {
		Navigation navigation = new Navigation();
		NightlyRate nightlyRate = new NightlyRate();
		String[] policies = { "Cancellation", "Deposit", "Check-in", "No Show" };
		String[] restrictions = { "Length of stay", "Booking window", "Promo code" };
		String[] rulesAndRestrictions = { "Min nights", "No check-in", "No check-out" };
		boolean isSaveSeasonForPolicy = false;
		boolean isSaveSeasonForRestriction = false;
		// searchAndEditRatePlan(driver, test_steps, ratePlanName);
		nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
		for (int i = 0; i < restrictions.length; i++) {
			removeRestrictionsAtRatePlanLevel(driver, test_steps, restrictions[i]);
		}
		for (int i = 0; i < policies.length; i++) {
			removePolicyAtRatePlanLevel(driver, test_steps, policies[i]);
		}
		nightlyRate.switchCalendarTab(driver, test_steps);
		nightlyRate.selectSeasonDates(driver, test_steps, checkInDate);
		nightlyRate.clickEditThisSeasonButton(driver, test_steps);
		nightlyRate.clickRulesRestrictionOnSeason(driver, test_steps);
		for (int i = 0; i < rulesAndRestrictions.length; i++) {
			isSaveSeasonForRestriction = removeRestrictionsAtSeasonTab(driver, test_steps, rulesAndRestrictions[i]);
		}
		nightlyRate.clickSeasonPolicies(driver, test_steps);
		for (int i = 0; i < policies.length; i++) {
			isSaveSeasonForPolicy = removePolicyAtSeasonTab(driver, test_steps, policies[i]);
		}

		if (isSaveSeasonForRestriction || isSaveSeasonForPolicy) {
			nightlyRate.clickSaveSason(driver, test_steps);
		} else {
			nightlyRate.closeSeason(driver, test_steps);
		}
		clickOnSaveratePlan(driver);
		closeRatePlan(driver, ratePlanName, test_steps);
	}

	public void selectDateFromRatesGridCalendar(WebDriver driver, ArrayList<String> test_steps, String date)
			throws Exception {
		clickOnCalendarIcon(driver, test_steps);
		String getMonth = getMonthFromCalendarHeading(driver);
		String expectedMonth = Utility.parseDate(date, "dd/MM/yyyy", "MMMM yyyy");
		ratesGridLogger.info("expectedMonth: " + expectedMonth);
		if (!expectedMonth.equalsIgnoreCase(getMonth)) {
			do {
				clickOnCalendarRightArrow(driver, test_steps);
				getMonth = getMonthFromCalendarHeading(driver);
			} while (!expectedMonth.equalsIgnoreCase(getMonth));
			test_steps.add("Selecting expected month and year from calendar tab as <b>" + expectedMonth
					+ "</b> taken from input data");
		}
		String dateXpath = "//div[@aria-label='" + Utility.parseDate(date, "dd/MM/yyyy", "EE MMM dd yyyy") + "']";
		Wait.waitForElementToBeClickable(By.xpath(dateXpath), driver);
		driver.findElement(By.xpath(dateXpath)).click();
	}

	public void clickOnCalendarIcon(WebDriver driver, ArrayList<String> testSteps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.calendarIcon), driver);
		elements.calendarIcon.click();
		ratesGridLogger.info("Clicked on calendar icon");
	}

	public void clickOnCalendarRightArrow(WebDriver driver, ArrayList<String> testSteps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.calendarRightArrow), driver);
		elements.calendarRightArrow.click();
		testSteps.add("Clicked on calendar right arrow");
		ratesGridLogger.info("Clicked on calendar right arrow");
	}

	public void overrideRateFromRatesGrid(WebDriver driver, ArrayList<String> test_steps, String roomClassName,
			ArrayList<String> dates, ArrayList<String> rates, ArrayList<String> extraAdults,
			ArrayList<String> extraChild, boolean isDateSelect) throws Exception {
		boolean sequence = Utility.checkAllDatesAreInSequenceOrNot(dates);
		boolean isSave = false;
		if (rates.size() == 1) {
			for (int i = 1; i < dates.size(); i++) {
				rates.add(rates.get(0));
			}
		}
		if (sequence) {
			if (isDateSelect) {
				selectDateFromRatesGridCalendar(driver, test_steps, dates.get(0));
			}
			for (int i = 0; i < dates.size(); i++) {
				overrideRateForSingleDay(driver, test_steps, dates.get(i), roomClassName, rates, extraAdults,
						extraChild, isSave, i);
			}
		} else {

			for (int i = 0; i < dates.size(); i++) {
				if (isDateSelect) {
					selectDateFromRatesGridCalendar(driver, test_steps, dates.get(i));
				}
				overrideRateForSingleDay(driver, test_steps, dates.get(i), roomClassName, rates, extraAdults,
						extraChild, isSave, i);
			}
		}
	}

	private void overrideRateForSingleDay(WebDriver driver, ArrayList<String> test_steps, String date,
			String roomClassName, ArrayList<String> rates, ArrayList<String> extraAdults, ArrayList<String> extraChild,
			boolean isSave, int i) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		int j = i + 1;
		String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]" + "//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))])["
				+ j + "]";
		String xpath1 = "(//div[@class='roomClassName'and contains(@title,'"
				+ roomClassName + "')]" + "//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))])["
				+ j + "]";
		if(Utility.isElementPresent(driver, By.xpath(xpath))) {
			Utility.ScrollToElement(driver.findElement(By.xpath(xpath)), driver);
			driver.findElement(By.xpath(xpath)).click();
		}else if(Utility.isElementPresent(driver, By.xpath(xpath1))) {
			Utility.ScrollToElement(driver.findElement(By.xpath(xpath1)), driver);
			driver.findElement(By.xpath(xpath1)).click();
		}
		String extRate = elements.roomClassLevelRatePerNight.getAttribute("value");
		String extAdult = elements.roomClassLevelExtraAdult.getAttribute("value");
		String extChild = elements.roomClassLevelExtraChild.getAttribute("value");

		if (!extRate.equalsIgnoreCase(rates.get(i))) {
			elements.roomClassLevelRatePerNight.clear();
			elements.roomClassLevelRatePerNight.sendKeys(rates.get(i));
			test_steps.add("Updating <b>Rate Per Night</b> value for the date <b>" + date + "</b> as : <b>"
					+ rates.get(i) + "</b>");
			isSave = true;
		}
		if (!extraAdults.isEmpty()) {
			if (Utility.validateString(extraAdults.get(i)) && !extAdult.equalsIgnoreCase(extraAdults.get(i))) {
				elements.roomClassLevelExtraAdult.clear();
				elements.roomClassLevelExtraAdult.sendKeys(extraAdults.get(i));
				test_steps.add("Updating <b>Extra Adults</b> value for the date <b>" + date + "</b> as : <b>"
						+ extraAdults.get(i) + "</b>");
				isSave = true;
			}
		}
		if (!extraChild.isEmpty()) {
			if (extChild.equalsIgnoreCase(extraChild.get(i)) && Utility.validateString(extraChild.get(i))) {
				elements.roomClassLevelExtraChild.clear();
				elements.roomClassLevelExtraChild.sendKeys(extraChild.get(i));
				test_steps.add("Updating <b>Extra Child</b> value for the date <b>" + date + "</b> as : <b>"
						+ extraChild.get(i) + "</b>");
				isSave = true;
			}
		}
		if (isSave) {
			elements.roomClassLevelRateSaveButton.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.rateSuccessMsg), driver);
			Utility.clickThroughJavaScript(driver, elements.ruleSuccessMsgCloseIcon);
			Wait.waitForElementToBeInvisibile(By.xpath(OR_RatesGrid.ruleSuccessMsg), driver);
		} else {
			elements.roomClassLevelRateCloseButton.click();
		}
	}

	public void overrideRateFromSeasonTab(WebDriver driver, ArrayList<String> test_steps, String roomClass,
			String ratePlan, String rate, String checkInDate) throws Exception {
		boolean isSave = false;
		searchAndEditRatePlan(driver, test_steps, ratePlan);
		switchCalendarTab(driver, test_steps);
		selectSeasonDates(driver, test_steps, checkInDate);
		clickEditThisSeasonButton(driver, test_steps);
		String rateXpath = "//div[@class='AddRoomClassList']//span[text()='" + roomClass
				+ "']/../..//input[@name='txtRate']";
		Wait.waitForElementToBeVisibile(By.xpath(rateXpath), driver);
		String exstRate = driver.findElement(By.xpath(rateXpath)).getAttribute("value");
		if (!exstRate.equalsIgnoreCase(rate)) {
			Utility.clearValue(driver, driver.findElement(By.xpath(rateXpath)));
			driver.findElement(By.xpath(rateXpath)).sendKeys(rate);
			driver.findElement(By.xpath(rateXpath)).sendKeys(Keys.TAB);
			isSave = true;
		}
		if (isSave) {
			clickSaveSason(driver, test_steps);
			clickSaveRatePlanButton(driver, test_steps);
		}
	}

	public void clickSaveRatePlanButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.SAVE_RATE_PLAN), driver);
		try {
			elements.SAVE_RATE_PLAN.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.SAVE_RATE_PLAN);
		}
		Utility.clickThroughJavaScript(driver, elements.SAVE_RATE_PLAN);
		test_steps.add("Save Rate Plan Button Clicked");
	}

	public void clickSaveSason(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.RatePlan_Season_SeasonSave), driver);
		Utility.ScrollToElement(elements.RatePlan_Season_SeasonSave, driver);
		try {
			elements.RatePlan_Season_SeasonSave.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.RatePlan_Season_SeasonSave);
		}
		test_steps.add("Clicking on Save Season");
	}

	public void switchCalendarTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.CALENDAR_TAB), driver);
		elements.CALENDAR_TAB.click();
		test_steps.add("Calendar Tab Clicked");
	}

	public void selectSeasonDates(WebDriver driver, ArrayList<String> test_steps, String date) {
		String startDateMonth = Utility.get_Month(date);
		String startDateYear = Utility.getYear(date);
		String startDateDay = Utility.getDay(date);
		startDateMonth = startDateMonth.toUpperCase();
		String startDateMonthYear = startDateMonth + " " + startDateYear;
		startDateMonthYear = startDateMonthYear.trim();
		String startDate = "//div[text()='" + startDateMonthYear
				+ "']/../..//div[@class='DayPicker-Body']//div[text()='" + startDateDay + "']";
		Wait.waitForElementToBeClickable(By.xpath(startDate), driver);
		Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(startDate)));
		test_steps.add("Selecting date  as : " + date);
	}

	public void clickEditThisSeasonButton(WebDriver driver, ArrayList<String> testSteps) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.editThisSeason), driver);
		Utility.ScrollToElement(elements.editThisSeason, driver);
		Utility.clickThroughJavaScript(driver, elements.editThisSeason);
		testSteps.add("Click Edit This Season Button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.editSeasonTitle), driver);
	}

	public void clickOnBulkUpdate(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.RateGridBulkUpdateButton), driver);
		elements.RateGridBulkUpdateButton.click();
		test_steps.add("Clicked on bulk update button");
		ratesGridLogger.info("Clicked on bulk update button");
	}

	public void selectBulkUpdateOption(WebDriver driver, ArrayList<String> test_steps, String option) {
		String rateGridBulkUpdateAvailable = "(//li//a[contains(text(),'" + option + "')])[1]";
		Wait.waitForElementToBeClickable(By.xpath(rateGridBulkUpdateAvailable), driver);
		WebElement rateGirdOption = driver.findElement(By.xpath(rateGridBulkUpdateAvailable));
		rateGirdOption.click();
		test_steps.add("Selecting <b>" + option + "</b> from bulk update option");
		ratesGridLogger.info("Select " + option + " from bulk update");
	}

	public boolean blockoutRoomClassOrAvilable(WebDriver driver, ArrayList<String> testSteps, String RoomClass,
			int days, String Source, String Action) {

		String[] src = Source.split("\\|");
		boolean flag = false;

		String blockout = "";

		if (Action.equalsIgnoreCase("Blockout")) {
			for (int i = 0; i < days; i++) {

				for (int j = 0; j < src.length; j++) {
					blockout = "//div[contains(text(),'" + RoomClass + "')]/../../..//div[contains(text(),'" + src[j]
							+ "')]/../following-sibling::div[" + (i + 1) + "]";
					String classAtt = driver.findElement(By.xpath(blockout)).getAttribute("class");
					if (classAtt.contains("NoBlackedStatus")) {
						driver.findElement(By.xpath(blockout)).click();
						String toster = "//div[contains(text(),'Distribution to " + src[j]
								+ " was successfully enabled')]";
						Wait.WaitForElement(driver, toster);
						testSteps.add(
								"Room Class : " + RoomClass + " is successfully blockedout for the source : " + src[j]);
						logger.info(
								"Room Class : " + RoomClass + " is successfully blockedout for the source : " + src[j]);
						flag = true;
					} else {
						flag = false;
						testSteps
								.add("Room Class : " + RoomClass + " is already blockedout for the source : " + src[j]);
						logger.info("Room Class : " + RoomClass + " is already blockedout for the source : " + src[j]);
					}
				}
			}
		} else if (Action.equalsIgnoreCase("Available")) {
			for (int i = 0; i < days; i++) {

				for (int j = 0; j < src.length; j++) {
					blockout = "//div[contains(text(),'" + RoomClass + "')]/../../..//div[contains(text(),'" + src[j]
							+ "')]/../following-sibling::div[" + (i + 1) + "]";
					String classAtt = driver.findElement(By.xpath(blockout)).getAttribute("class");
					logger.info("classAtt : " + classAtt);
					if (!classAtt.contains("NoBlackedStatus")) {
						driver.findElement(By.xpath(blockout)).click();
						String toster = "//div[contains(text(),'Distribution to " + src[j]
								+ " was successfully disabled')]";
						System.out.println("toster : " + toster);
						Wait.WaitForElement(driver, toster);
						testSteps.add(
								"Room Class : " + RoomClass + " is successfully Available for the source : " + src[j]);
						logger.info(
								"Room Class : " + RoomClass + " is successfully Availkable for the source : " + src[j]);
						flag = true;
					} else {
						flag = false;
						testSteps.add("Room Class : " + RoomClass + " is already Available for the source : " + src[j]);
						logger.info("Room Class : " + RoomClass + " is already Available for the source : " + src[j]);
					}
				}
			}
		}
		return flag;
	}

	public ArrayList<String> selectRoomClass(WebDriver driver, String roomClass) throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		Wait.WaitForElement(driver, OR.BulkUpdatePopupRoomClass);
		Wait.waitForElementToBeVisibile(By.xpath(OR.BulkUpdatePopupRoomClass), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.BulkUpdatePopupRoomClass), driver);
		Utility.ScrollToElement_NoWait(elements.BulkUpdatePopupRoomClass, driver);
		elements.BulkUpdatePopupRoomClass.click();

		String roomClassesPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
		List<WebElement> roomClassesElement = driver.findElements(By.xpath(roomClassesPath));

		for (int i = 0; i < roomClassesElement.size(); i++) {

			String roomClassName = roomClassesElement.get(i).getText().trim();
			rateGridLogger.info("GetRoomClass : " + roomClassName);

			if (roomClassName.contains(roomClass)) {

				roomClassesElement.get(i).click();
				testSteps.add("Entered RoomClass : " + roomClass);
				rateGridLogger.info("Entered RoomClass : " + roomClass);
				break;
			}

		}

		return testSteps;
	}

	public void enterRatesGridBulkUpdatePopupEndDate(WebDriver driver, ArrayList<String> test_steps, String endDate)
			throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		// elements.rateGridBulkUpdateEndDate.clear();
		elements.rateGridBulkUpdateEndDate.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));

		/*
		 * if (endDate.charAt(3) == '0') {
		 * elements.rateGridBulkUpdateEndDate.sendKeys("0"+Utility.parseDate(endDate,
		 * "dd/MM/yyyy", "MM/dd/yyyy"));
		 * test_steps.add("Selecting end date as <b>"+endDate+"</b>"); } else {
		 * 
		 * }
		 */

		elements.rateGridBulkUpdateEndDate.sendKeys(Utility.parseDate(endDate, "dd/MM/yyyy", "MM/dd/yyyy"));
		test_steps.add("Selecting end date as <b>" + endDate + "</b>");
		elements.rateGridBulkUpdateEndDate.sendKeys(Keys.TAB);
		// elements.rateGridBulkUpdateEndDate.sendKeys(Keys.ENTER);

	}

	public void selectDaysFromWeek(WebDriver driver, ArrayList<String> test_steps, ArrayList<String> days)
			throws Exception {
		ArrayList<String> daysOfWeek = new ArrayList<>();
		daysOfWeek.add("Mon");
		daysOfWeek.add("Tue");
		daysOfWeek.add("Wed");
		daysOfWeek.add("Thu");
		daysOfWeek.add("Fri");
		daysOfWeek.add("Sat");
		daysOfWeek.add("Sun");
		for (String day : daysOfWeek) {
			boolean isDaySelected = driver
					.findElement(By.xpath("//span[@class='text' and text()='" + day + "']/following-sibling::input"))
					.isSelected();
			WebElement checkBox = driver.findElement(
					By.xpath("//span[@class='text' and text()='" + day + "']/..//span[contains(@class,'checkmark')]"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			if (days.contains(day)) {
				if (!isDaySelected) {
					js.executeScript("arguments[0].click()", checkBox);
				}
			} else {
				if (isDaySelected) {
					js.executeScript("arguments[0].click()", checkBox);
				}
			}
		}
		test_steps.add("Selecting <b>" + days + "</b> and unselecting remaining days");
	}

	public void enterRoomClassNameInBulkUpdatePopup(WebDriver driver, ArrayList<String> test_steps, String roomClass)
			throws InterruptedException {
		String valueArray[] = roomClass.split("\\|");
		for (int j = 0; j < valueArray.length; j++) {
			String bulkUpdateDropDownPath = "(//label[text()='Room class']//following-sibling::div//div[1])[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			String planInputPath = "//label[text()='Room class']//following-sibling::div//input[@role='combobox']";
			Wait.WaitForElement(driver, planInputPath);
			WebElement input = driver.findElement(By.xpath(planInputPath));
			input.sendKeys(valueArray[j]);
			String selectionElementsPath = null;
			if (valueArray[j].contains("All")) {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')and contains(text(),'"
						+ valueArray[j] + "')]";
			} else {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')and text()='"
						+ valueArray[j] + "']";
			}
			WebElement selectionElementsList = driver.findElement(By.xpath(selectionElementsPath));
			selectionElementsList.click();
			test_steps.add("Entering room class as : <b>" + valueArray[j] + "</b>");
		}
	}

	public void enterRatePlanNameInBulkUpdatePopup(WebDriver driver, ArrayList<String> test_steps, String ratePlan)
			throws InterruptedException {
		String valueArray[] = ratePlan.split("\\|");
		for (int j = 0; j < valueArray.length; j++) {
			String bulkUpdateDropDownPath = "(//label[text()='Rate Plan']//following-sibling::div//div[1])[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			String planInputPath = "//label[text()='Rate Plan']//following-sibling::div//input[@role='combobox']";
			Wait.WaitForElement(driver, planInputPath);
			WebElement input = driver.findElement(By.xpath(planInputPath));
			input.sendKeys(valueArray[j]);
			String selectionElementsPath = null;
			if (valueArray[j].contains("All Active") || valueArray[j].contains("All Inactive")) {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]/span[contains(text(),'"
						+ valueArray[j] + "')]";
			} else {
				selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]/span[text()='"
						+ valueArray[j] + "']";
			}
			WebElement selectionElementsList = driver.findElement(By.xpath(selectionElementsPath));
			selectionElementsList.click();
			test_steps.add("Entering rate plan as : <b>" + valueArray[j] + "</b>");
		}
	}

	public void enterNewRateUpdate(WebDriver driver, ArrayList<String> test_steps, String ratePerNight,
			String addtnlAdult, String addtnlChild) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		boolean isSelected = elements.bulkUpdateEnterNewRateRadioBtn.isSelected();
		if (!isSelected) {
			elements.bulkUpdateEnterNewRateRadioBtn.click();
			test_steps.add("Selecting Enter New Rate radio button");
			elements.UPDATE_RATE_NEWRATE_NIGHTLYRATES.sendKeys(ratePerNight);
			elements.UPDATE_RATE_NEWRATE_ADDITIONALADULTS.sendKeys(addtnlAdult);
			elements.UPDATE_RATE_NEWRATE_ADDITIONALCHILDS.sendKeys(addtnlChild);
		} else {
			test_steps.add("Enter New Rate radio button is already selected");
			Utility.clearValues(driver, elements.UPDATE_RATE_NEWRATE_NIGHTLYRATES);
			Utility.clearValues(driver, elements.UPDATE_RATE_NEWRATE_ADDITIONALADULTS);
			Utility.clearValues(driver, elements.UPDATE_RATE_NEWRATE_ADDITIONALCHILDS);
			elements.UPDATE_RATE_NEWRATE_NIGHTLYRATES.sendKeys(ratePerNight);
			elements.UPDATE_RATE_NEWRATE_ADDITIONALADULTS.sendKeys(addtnlAdult);
			elements.UPDATE_RATE_NEWRATE_ADDITIONALCHILDS.sendKeys(addtnlChild);
		}
		test_steps.add("Entering Nightly rate as : <b>" + ratePerNight + "</b>");
		test_steps.add("Entering Additional adult as : <b>" + addtnlAdult + "</b>");
		test_steps.add("Entering Additional child as : <b>" + addtnlChild + "</b>");
	}

	public void clickOnUpdateButtonInBulkUpdatePopup(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.rateGridBulkUpdateUpdateButton), driver);
		try {
			elements.rateGridBulkUpdateUpdateButton.click();

			Wait.waitForElementToBeInvisibile(By.xpath(OR.rateGridBulkUpdateUpdateButton), driver);
		} catch (Exception e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", elements.rateGridBulkUpdateUpdateButton);
		}
		test_steps.add("Clicking on update button");
		List<WebElement> yesUpdateButton = driver.findElements(By.xpath("//button[@class='btn btn btn-success']"));
		if (yesUpdateButton.size() > 0) {
			yesUpdateButton.get(0).click();
			test_steps.add("Clicking on Yes, Update button");
		}
	}

	public void overrideRateFromBulkUpdate(WebDriver driver, ArrayList<String> test_steps, String roomClass,
			String ratePlan, String ratePerNight, String startDate, String endDate, ArrayList<String> days,
			String updateRateType, String addtionalAdults, String addtionalChild, String channelName) throws Exception {
		clickOnBulkUpdate(driver, test_steps);
		selectBulkUpdateOption(driver, test_steps, "Rates");
		Wait.waitForElementToBeVisibile(By.xpath(OR.rateGridBulkUpdateStartDate), driver);
		enterRatesGridBulkUpdatePopupStartDate(driver, test_steps, startDate);
		enterRatesGridBulkUpdatePopupEndDate(driver, test_steps, endDate);
		// selectEndDate(driver, endDate);
		selectDaysFromWeek(driver, test_steps, days);
		enterRoomClassNameInBulkUpdatePopup(driver, test_steps, roomClass);
		enterRatePlanNameInBulkUpdatePopup(driver, test_steps, ratePlan);
		selectItemFromDropDowns(driver, "Source", channelName);
		if (updateRateType.equalsIgnoreCase("Enter New Rate")) {
			enterNewRateUpdate(driver, test_steps, ratePerNight, addtionalAdults, addtionalChild);
		}
		clickOnUpdateButtonInBulkUpdatePopup(driver, test_steps);
	}

	// Added By Adhnan 09/25/2020
	public ArrayList<String> validateProRateValues(WebDriver driver, boolean isProRateChecked, String intervalValue,
			String roomClassNames, String ratePerNight, String isAdditionalAdultChild, String childRate,
			String adultsRate, ArrayList<String> testSteps) throws InterruptedException {

		String[] splitroomClassNames = roomClassNames.split(Utility.DELIM);
		String[] splitratePerNight = ratePerNight.split(Utility.DELIM);
		String[] splitchildRate = childRate.split(Utility.DELIM);
		String[] splitadultsRate = adultsRate.split(Utility.DELIM);

		String expectedRate = null;
		String expectedChildRate = null;
		String expectedAdultRate = null;

		if (isProRateChecked) {
			for (int i = 0; i < splitroomClassNames.length; i++) {

				String pathOfRatePerNight = "//li[contains(@class,'IntervalRatePlan')]//span[text()='"
						+ splitroomClassNames[i]
						+ "']//parent::label//..//following-sibling::div[contains(@class,'RoomStayList')]//input";
				List<WebElement> rates = driver.findElements(By.xpath(pathOfRatePerNight));
				String nightRate = rates.get(1).getAttribute("value");
				expectedRate = String.format("%.2f",
						(Float.parseFloat(splitratePerNight[i]) / Float.parseFloat(intervalValue)));
				testSteps.add("Found Night Rate : " + nightRate);
				testSteps.add("Expected Night Rate : " + expectedRate);
				logger.info("Found Night Rate : " + nightRate);
				logger.info("Expected Night Rate : " + expectedRate);
				assertTrue(expectedRate.contains(nightRate), "Failed Night Rate missmatched");

				if (Boolean.parseBoolean(isAdditionalAdultChild)) {
					expectedAdultRate = String.format("%.2f",
							(Float.parseFloat(splitadultsRate[i]) / Float.parseFloat(intervalValue)));
					String adultRate = rates.get(2).getAttribute("value");
					testSteps.add("Found Adult Rate : " + adultRate);
					testSteps.add("Expected Adult Rate : " + expectedAdultRate);
					logger.info("Found Adult Rate : " + adultRate);
					logger.info("Expected Adult Rate : " + expectedAdultRate);

					assertTrue(expectedAdultRate.contains(adultRate), "Failed Night Rate missmatched");

					expectedChildRate = String.format("%.2f",
							(Float.parseFloat(splitchildRate[i]) / Float.parseFloat(intervalValue)));
					String foundchildRate = rates.get(3).getAttribute("value");
					testSteps.add("Found Child Rate : " + foundchildRate);
					testSteps.add("Expected Child Rate : " + expectedChildRate);
					logger.info("Found Child Rate : " + foundchildRate);
					logger.info("Expected Child Rate : " + expectedChildRate);

					assertTrue(expectedChildRate.contains(foundchildRate), "Failed Night Rate missmatched");

				}

			}

		}
		return testSteps;
	}

	public void enterRatesGridBulkUpdatePopupStartDate(WebDriver driver, ArrayList<String> test_steps,
			String startDate) {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		elements.rateGridBulkUpdateStartDate.clear();

		elements.rateGridBulkUpdateStartDate.sendKeys("0" + Utility.parseDate(startDate, "dd/MM/yyyy", "MM/dd/yyyy"));
		test_steps.add("Selecting start date as <b>" + startDate + "</b>");
	}

	public ArrayList<String> getOverriddenRates(WebDriver driver, ArrayList<String> test_steps, String roomClass,
			ArrayList<String> days) throws Exception {
		ArrayList<String> allRates = new ArrayList<>();
		boolean sequence = Utility.checkAllDatesAreInSequenceOrNot(days);
		if (sequence) {
			selectDateFromRatesGridCalendar(driver, test_steps, days.get(0));
			for (int i = 1; i <= days.size(); i++) {
				String overriddenRate = driver.findElement(By.xpath("(//div[text()='" + roomClass + "']/../.."
						+ "//div[@class='has-changes Override'])[" + i + "]")).getText();
				allRates.add(overriddenRate);
			}
		} else {
			int j;
			for (int i = 0; i < days.size(); i++) {
				j = i + 1;
				selectDateFromRatesGridCalendar(driver, test_steps, days.get(i));
				String overriddenRate = driver.findElement(By.xpath("(//div[text()='" + roomClass + "']/../.."
						+ "//div[@class='has-changes Override'])[" + j + "]")).getText();
				allRates.add(overriddenRate);
			}
		}
		return allRates;
	}

	public ArrayList<String> getOverriddenOrRegularRates(WebDriver driver, ArrayList<String> test_steps,
			String roomClass, ArrayList<String> days) throws Exception {
		ArrayList<String> allRates = new ArrayList<>();
		boolean sequence = Utility.checkAllDatesAreInSequenceOrNot(days);
		if (sequence) {
			selectDateFromRatesGridCalendar(driver, test_steps, days.get(0));
			for (int i = 1; i <= days.size(); i++) {
				String rate;
				try {
					rate = driver.findElement(By.xpath("(//div[text()='" + roomClass + "']/../.."
							+ "//div[@class='has-changes Override'])[" + i + "]")).getText();
				} catch (Exception e) {
					rate = driver.findElement(By.xpath(
							"(//div[text()='" + roomClass + "']/../.." + "//div[@class=' RegularRate'])[" + i + "]"))
							.getText();
				}
				allRates.add(rate);
			}
		} else {
			int j;
			for (int i = 0; i < days.size(); i++) {
				j = i + 1;
				selectDateFromRatesGridCalendar(driver, test_steps, days.get(i));
				String rate;
				try {
					rate = driver.findElement(By.xpath("(//div[text()='" + roomClass + "']/../.."
							+ "//div[@class='has-changes Override'])[" + j + "]")).getText();
				} catch (Exception e) {
					rate = driver.findElement(By.xpath(
							"(//div[text()='" + roomClass + "']/../.." + "//div[@class=' RegularRate'])[" + j + "]"))
							.getText();
				}
				allRates.add(rate);
			}
		}
		return allRates;
	}

	public ArrayList<String> selectItemFromDropDowns(WebDriver driver, String dropDownName, String selectionElement)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();
		rateGridLogger.info(selectionElement + Utility.DELIM);
		if (selectionElement.contains(Utility.DELIMS)) {

			String[] splitedStringArray = selectionElement.split(Utility.DELIM);
			for (String getString : splitedStringArray) {
				String bulkUpdateDropDownPath = "//label[text()='" + dropDownName
						+ "']//following-sibling::div//div[1]";
				Wait.WaitForElement(driver, bulkUpdateDropDownPath);
				Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
				Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
				WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
				Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
				bulkUpdateDropdown.click();
				testSteps.add(dropDownName + " drop down clicked");
				ratesGridLogger.info(dropDownName + " drop down clicked");

				getString = getString.trim();
				rateGridLogger.info(getString);
				if (getString.contains("All Active")) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Active Rate Plans')]";
					WebElement element = driver.findElement(By.xpath(selectionElementsPath));

					element.click();
					testSteps.add("Entered : " + getString);
					ratesGridLogger.info("Entered : " + getString);

				} else if (getString.contains("All Inactive")) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Inactive Rate Plans')]";
					WebElement element = driver.findElement(By.xpath(selectionElementsPath));

					element.click();
					testSteps.add("Entered : " + getString);
					ratesGridLogger.info("Entered : " + getString);

				} else if ((getString.contains("All room")) || (getString.contains("Select"))) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All room classes')]";
					WebElement element = driver.findElement(By.xpath(selectionElementsPath));

					element.click();
					testSteps.add("Entered : " + getString);
					ratesGridLogger.info("Entered : " + getString);

				} else if (getString.contains("All sources")) {
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All sources')]";
					WebElement element = driver.findElement(By.xpath(selectionElementsPath));

					element.click();
					testSteps.add("Entered : " + getString);
					ratesGridLogger.info("Entered : " + getString);

				} else {

					String planInputPath = "//label[text()='" + dropDownName + "']//following-sibling::div//input";
					Wait.WaitForElement(driver, planInputPath);
					Wait.waitForElementToBeVisibile(By.xpath(planInputPath), driver);
					Wait.waitForElementToBeClickable(By.xpath(planInputPath), driver);
					WebElement input = driver.findElement(By.xpath(planInputPath));
					input.sendKeys(selectionElement);
					rateGridLogger.info("Entered rate plan name in input : " + selectionElement);
					String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
					List<WebElement> selectionElementsList = driver.findElements(By.xpath(selectionElementsPath));

					for (int i = 0; i < selectionElementsList.size(); i++) {
						String selectionElementName = selectionElementsList.get(i).getText().trim();
						ratesGridLogger.info("Get list items : " + selectionElementName);

						if (selectionElementName.contains(getString)) {

							selectionElementsList.get(i).click();
							testSteps.add("Entered : " + getString);
							ratesGridLogger.info("Entered : " + getString);
							break;
						}

					}
				}
			}

		} else {

			String bulkUpdateDropDownPath = "//label[text()='" + dropDownName + "']//following-sibling::div//div[1]";
			Wait.WaitForElement(driver, bulkUpdateDropDownPath);
			Wait.waitForElementToBeVisibile(By.xpath(bulkUpdateDropDownPath), driver);
			Wait.waitForElementToBeClickable(By.xpath(bulkUpdateDropDownPath), driver);
			WebElement bulkUpdateDropdown = driver.findElement(By.xpath(bulkUpdateDropDownPath));
			Utility.ScrollToElement_NoWait(bulkUpdateDropdown, driver);
			bulkUpdateDropdown.click();
			testSteps.add(dropDownName + " drop down clicked");
			ratesGridLogger.info(dropDownName + " drop down clicked");

			if (selectionElement.contains("All Active")) {
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Active Rate Plans')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else if (selectionElement.contains("All Inactive")) {
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]//child::span[contains(text(),'All Inactive Rate Plans')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else if ((selectionElement.contains("All room")) || (selectionElement.contains("Select"))) {

				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(text(),'All room')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else if (selectionElement.contains("All sources")) {
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(text(),'All sources')]";
				WebElement element = driver.findElement(By.xpath(selectionElementsPath));

				element.click();
				testSteps.add("Entered : " + selectionElement);
				ratesGridLogger.info("Entered : " + selectionElement);

			} else {
				String planInputPath = "//label[text()='" + dropDownName + "']//following-sibling::div//input";
				Wait.WaitForElement(driver, planInputPath);
				Wait.waitForElementToBeVisibile(By.xpath(planInputPath), driver);
				Wait.waitForElementToBeClickable(By.xpath(planInputPath), driver);
				WebElement input = driver.findElement(By.xpath(planInputPath));
				input.sendKeys(selectionElement);
				rateGridLogger.info("Entered rate plan name in input : " + selectionElement);
				String selectionElementsPath = "(//div[@class='Select-menu-outer'])[1]//div[contains(@class,'Select-option')]";
				List<WebElement> selectionElementsList = driver.findElements(By.xpath(selectionElementsPath));
				for (int i = 0; i < selectionElementsList.size(); i++) {
					String selectionElementName = selectionElementsList.get(i).getText().trim();
					// ratesGridLogger.info("Get list items : " + selectionElementName);

					if (selectionElementName.contains(selectionElement)) {

						selectionElementsList.get(i).click();
						testSteps.add("Entered : " + selectionElement);
						ratesGridLogger.info("Entered : " + selectionElement);
						break;
					}

				}
			}

		}

		return testSteps;
	}

	public ArrayList<String> applyCheckInCheckOutRuleValueIfTheyAlreadyNotApplied(WebDriver driver,
			String roomClassName, String channelName, String ruleName, String checkInDate, String checkOutDate,
			String dateFormat) throws ParseException, InterruptedException {

		ArrayList<String> test_Steps = new ArrayList<>();

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> checkInCheckOutDataValues = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		HashMap<Integer, String> checkInCheckOutDataValuesMap = new HashMap<Integer, String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, checkInDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, checkOutDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		checkInCheckOutDataValues = getRuleDataValueForCheckInCheckOut(driver, roomClassName, channelName, ruleName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					int index = i + 1;
					String xpath = "(//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
							+ "//../..//following-sibling::div//div[@title='" + channelName + "']"
							+ "//../..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='"
							+ ruleName + "']/following-sibling::div/div/div)[" + index + "]";
					Wait.WaitForElement(driver, xpath);
					driver.findElement(By.xpath(xpath)).click();
					String alertPath = "//div[@role='alert'][text()='Rule saved successfully']";
					Wait.waitForElementToBeVisibile(By.xpath(alertPath), driver);
					checkInCheckOutDataValuesMap.put(i, checkInCheckOutDataValues.get(i));
				}
			}

		}

		return test_Steps;
	}

	public ArrayList<String> getMinStayValuesOfRoomClassBetweenSelectedDateRange(WebDriver driver, String dateFormat,
			String startDate, String endDate, String roomClassName, String channelName, String ruleName)
			throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> minStayValues = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		minStayValues = getRuleDataValuesForMinStay(driver, roomClassName, channelName, ruleName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					minStayDates.put(dayList.get(j), minStayValues.get(i));
					test_Steps.add(ruleName + "  data value for '" + roomClassName + " as per  Date  " + dayList.get(j)
							+ " : " + minStayValues.get(i));
				}
			}

		}
		logger.info("Rate Grid " + ruleName + "  data values for '" + roomClassName + "' channel " + channelName
				+ " as per  Date  " + minStayDates);
		test_Steps.add("Rate Grid " + ruleName + "  data values for '" + roomClassName + "' channel " + channelName
				+ " as per  Date  " + minStayDates);
		return test_Steps;

	}

	public ArrayList<String> getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(WebDriver driver,
			String dateFormat, String startDate, String endDate, String roomClassName, String channelName,
			String ruleName, boolean blnUpdatedData) throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> noCheckInCheckOutValues = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		noCheckInCheckOutValues = getRuleDataValueForCheckInCheckOut(driver, roomClassName, channelName, ruleName);
		String checkInruleValue = "";

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					if (ruleName.equalsIgnoreCase("No Check In")) {
						if (noCheckInCheckOutValues.get(i).isEmpty()) {
							checkInruleValue = "No";
							noCheckInDates.put(dayList.get(j), checkInruleValue);
						} else {
							checkInruleValue = "Yes";
							noCheckInDates.put(dayList.get(j), checkInruleValue);
						}
					} else {
						if (noCheckInCheckOutValues.get(i).isEmpty()) {
							checkInruleValue = "No";
							noCheckOutDates.put(dayList.get(j), checkInruleValue);
						} else {
							checkInruleValue = "Yes";
							noCheckOutDates.put(dayList.get(j), checkInruleValue);
						}
					}

					if (blnUpdatedData) {
						test_Steps.add(ruleName + "  data value for '" + roomClassName
								+ " after Updation as per  Date  " + dayList.get(j) + " : " + checkInruleValue);
					} else {
						test_Steps.add(ruleName + "  data value for '" + roomClassName + " as per  Date  "
								+ dayList.get(j) + " : " + checkInruleValue);
					}
				}
			}

		}
		return test_Steps;

	}

	public ArrayList<String> getRateValuesOfRoomClassBetweenSelectedDateRange(WebDriver driver, String dateFormat,
			String startDate, String endDate, String roomClassName, String channelName)
			throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> test_Steps = new ArrayList<>();
		ArrayList<String> selectedRoomClassDataValues = new ArrayList<String>();
		ArrayList<String> datesList = new ArrayList<String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);
		selectedRoomClassDataValues = getChannelDataValues(driver, roomClassName, channelName);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					rateValuesMap.put(dayList.get(j), selectedRoomClassDataValues.get(i));
					test_Steps.add("Data value for '" + roomClassName + " after Updation as per  Date  "
							+ dayList.get(j) + " : " + selectedRoomClassDataValues.get(i));
				}
			}

		}
		return test_Steps;

	}

	public ArrayList<String> updateChannelRate(WebDriver driver, ArrayList<String> testSteps, String roomClass,
			String oldRate, String newRate, String channelName, String path, int index) throws InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, path);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToViewElementINMiddle(driver, element);
		element.click();
		elements.rateGridRoomRate.clear();
		elements.rateGridRoomRate.sendKeys(newRate);
		testSteps.add("Enter New Rate: <b>" + newRate + "</b>");
		logger.info("Enter New Rate: " + newRate);
		if (newRate.equals(oldRate)) {

			elements.rateGridDanger.click();
			testSteps.add("Click on Cross Icon");
			logger.info("Click on Cross Icon");
			Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
		} else {
			elements.rateGridSuccess.click();
			testSteps.add("Click on Success Icon");
			logger.info("Click on Success Icon");
			Wait.WaitForElement(driver, OR_RateGrid.rateSavedMessage);
		}
		testSteps.add("Verified Message : <b> Rate saved successfully </b>");
		logger.info("Verified Message : Rate saved successfully");
		String xpath = "(//div[@class='roomClassName'and contains(@title,'" + roomClass + "')]"
				+ "//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]" + "//div[@title='"
				+ channelName + "']//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))])[" + index
				+ "]";
		Wait.WaitForElement(driver, xpath);
		WebElement elementOne = driver.findElement(By.xpath(xpath));
		Utility.ScrollToElement(elementOne, driver);
		assertEquals(elementOne.getText(), newRate, "Failed to Verify New Rate");
		testSteps.add(
				"Successfully Verified Update rate is : <b> " + newRate + " </b> Old Rate is: " + oldRate + "</b>");
		testSteps.add("Update Rate is : <b> " + newRate + " </b> Old Rate is: " + oldRate + "</b>");
		logger.info("Update Rate is :" + newRate + " Old Rate is: " + oldRate);
		return testSteps;
	}

	public ArrayList<String> updateRateValuesForSelectedClasses(WebDriver driver, String roomClassName,
			String channelName, String updatedRate, String startDate, String endDate, String dateFormat)
			throws ParseException, InterruptedException {

		ArrayList<String> test_Steps = new ArrayList<>();
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> datesList = new ArrayList<String>();
		ArrayList<String> dayList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();

		Date fromDate = Utility.convertStringtoDateFormat(dateFormat, startDate);
		logger.info("Start Date: " + fromDate);
		Date toDate = Utility.convertStringtoDateFormat(dateFormat, endDate);
		logger.info("End Date: " + toDate);
		dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
		logger.info("Dates Are: " + dates);

		for (Date d : dates) {
			dayList.add(Utility.getDateOnlyFromCompleteDate(d));
			dateList.add(Utility.convertDateFormattoString(dateFormat, d));
		}

		logger.info("Day's  from Start date and End Date: " + dayList);
		test_Steps.add("Day's  from Start date and End Date: " + dayList);
		logger.info("Date List from Start date and End Date: " + dateList);
		test_Steps.add("Date List from Start date and End Date: " + dateList);
		for (WebElement ele : elements.rateGridDay) {
			datesList.add(ele.getText());
		}

		logger.info("Rate Grid Day's Are: " + datesList);

		for (int i = 0; i < datesList.size(); i++) {
			for (int j = 0; j < dayList.size(); j++) {
				if (datesList.get(i).equals(dayList.get(j))) {
					int index = i + 1;
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("window.scrollBy(0,-200)");
					String xpath = "(//div[@class='roomClassName'and contains(@title,'" + roomClassName + "')]"
							+ "//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]"
							+ "//div[@title='" + channelName
							+ "']//parent::div//following-sibling::div/div[1][not(contains(text(),'--'))])[" + index
							+ "]";
					Wait.WaitForElement(driver, xpath);
					WebElement element = driver.findElement(By.xpath(xpath));
					element.click();
					String path = OR_RateGrid.rateGridRoomRate;
					test_Steps.addAll(updateChannelRate(driver, test_Steps, roomClassName,
							elements.rateGridRoomRate.getAttribute("value"), updatedRate, channelName, path, index));
					rateValuesMap.put(dayList.get(j), updatedRate);
					test_Steps.add("Data value for '" + roomClassName + " after Updation as per  Date  "
							+ dayList.get(j) + " : " + updatedRate);
				}
			}

		}

		return test_Steps;

	}

	public void clickOnOkButton(WebDriver driver) {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		ratesGrid.OkButton.click();
	}

	public void clickOnOkClosePupup(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Utility.ScrollToElement_NoWait(ratesGrid.BulkUpdateCloseButton, driver);
		ratesGrid.BulkUpdateCloseButton.click();
	}

	public String getAlertMessageWhenNoDayExistBetweenDates(WebDriver driver) {

		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, ORRateGrid.NoDaysMatchAlert);
		Wait.waitForElementToBeVisibile(By.xpath(ORRateGrid.NoDaysMatchAlert), driver);
		Wait.waitForElementToBeClickable(By.xpath(ORRateGrid.NoDaysMatchAlert), driver);
		String text = ratesGrid.NoDaysMatchAlert.getText();
		ratesGridLogger.info("no rate match alert " + text);
		return text;

	}

	public String getTotalDaysTextForRemove(WebDriver driver) {

		String daysTextPath = "(//div[contains(@class,'bulkUpdateConfirmDialog')]//p)[1]";
		WebElement daysTextElement = driver.findElement(By.xpath(daysTextPath));
		Wait.WaitForElement(driver, daysTextPath);
		Wait.waitForElementToBeVisibile(By.xpath(daysTextPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(daysTextPath), driver);
		String text = daysTextElement.getText();
		ratesGridLogger.info("bulkUpdatePopup Days text : " + text);
		return text;

	}

	public HashMap<String, String> getRuleOnBaseofDays(WebDriver driver, String CheckInDate, String CheckOutDate,
			HashMap<String, String> listOfDays, String timeZone, ArrayList<String> testSteps) throws Exception {
		HashMap<String, String> getAvailabilityBaseOnDays = new HashMap<String, String>();
		int count = ESTTimeZone.numberOfDaysBetweenDates(CheckInDate, CheckOutDate);
		count = count + 1;
		rateGridLogger.info("count: " + count);
		rateGridLogger.info("before start listOfDays: " + listOfDays.size());
		rateGridLogger.info("listOfDays: " + listOfDays);
		ArrayList<String> tepName = new ArrayList<String>();
		String isDayExist = "no";

		for (int i = 0; i < count; i++) {
			isDayExist = "no";
			String getDay = "";
			String dayCheck = "";
			for (int j = 0; j < listOfDays.size(); j++) {
				rateGridLogger.info("in loop that get day");
				getDay = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "MM/dd/yyyy", "EE", i, timeZone);
				rateGridLogger.info("dayCheck: " + getDay);
				if (tepName.size() > 0) {
					if (!tepName.contains(getDay)) {
						dayCheck = listOfDays.get(getDay);
						rateGridLogger.info("dayCheck: " + dayCheck);
						if (dayCheck != null) {
							tepName.add(getDay);
							rateGridLogger.info("day found");
							isDayExist = "yes";
							break;
						}

					}
				} else {

					dayCheck = listOfDays.get(getDay);
					rateGridLogger.info("dayCheck: " + dayCheck);
					if (dayCheck != null) {
						tepName.add(getDay);
						rateGridLogger.info("day found");
						isDayExist = "yes";

						break;
					}
				}

			}
			rateGridLogger.info("listOfDays: " + listOfDays.size());
			rateGridLogger.info(dayCheck);
			getDay = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "MM/dd/yyyy", "EE", i, timeZone);
			if (isDayExist.equalsIgnoreCase("yes")) {
				getAvailabilityBaseOnDays.put("" + i, isDayExist);

			} else {
				getAvailabilityBaseOnDays.put("" + i, isDayExist);

			}
		}

		getAvailabilityBaseOnDays.put("DayExist", isDayExist);
		ratesGridLogger.info("getAvailabilityBaseOnDays: " + getAvailabilityBaseOnDays.size());
		ratesGridLogger.info("getAvailabilityBaseOnDays: " + getAvailabilityBaseOnDays);
		ratesGridLogger.info("Days: " + listOfDays);

		return getAvailabilityBaseOnDays;
	}

	public HashMap<String, String> getAvailabilityOnBaseofDays(WebDriver driver, String roomClass, String channel,
			String CheckInDate, String CheckOutDate, HashMap<String, String> listOfDays1, String timeZone,
			String isavailability, boolean isNewAvailability, ArrayList<String> testSteps) throws Exception {
		HashMap<String, String> getAvailabilityBaseOnDays = new HashMap<String, String>();

		String availabilityPath = "//div[contains(text(),'" + roomClass
				+ "')]//..//..//..//div[@class='roomClassName' and text()='" + channel
				+ "']//..//..//div[contains(@class,'AvailabilityDateCell')]";
		ratesGridLogger.info("availabilityPath: " + availabilityPath);
		Wait.WaitForElement(driver, availabilityPath);
		Wait.waitForElementToBeInVisibile(By.xpath(availabilityPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(availabilityPath), driver);

		List<WebElement> elements = driver.findElements(By.xpath(availabilityPath));
		Utility.ScrollToElement_NoWait(elements.get(0), driver);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = LocalDateTime.now();
		int count = ESTTimeZone.numberOfDaysBetweenDates(CheckInDate, CheckOutDate);
		count = count + 1;
		rateGridLogger.info("count: " + count);
		rateGridLogger.info("before start listOfDays: " + listOfDays1.size());
		rateGridLogger.info("listOfDays: " + listOfDays1);
		ArrayList<String> tepName = new ArrayList<String>();
		String isDayExist = "no";

		for (int i = 1; i <= count; i++) {
			isDayExist = "no";
			String getDay = "";
			String dayCheck = "";
			String getDate = "";
			for (int j = i - 1; j < listOfDays1.size(); j++) {
				rateGridLogger.info("in loop that get day");
				getDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "MM/dd/yyyy", "d", i, timeZone);
				getDay = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "MM/dd/yyyy", "EE", i, timeZone);
				rateGridLogger.info("dayCheck: " + getDay);
				if (tepName.size() > 0) {
					if (!tepName.contains(getDay)) {
						dayCheck = listOfDays1.get(getDay);
						rateGridLogger.info("dayCheck: " + dayCheck);
						if (dayCheck != null) {
							tepName.add(getDay);
							rateGridLogger.info("day found");
							isDayExist = "yes";
							break;
						}

					}
				} else {

					dayCheck = listOfDays1.get(getDay);
					rateGridLogger.info("dayCheck: " + dayCheck);
					if (dayCheck != null) {
						tepName.add(getDay);
						// listOfDays1.remove(getDay);
						rateGridLogger.info("day found");
						isDayExist = "yes";

						break;
					}
				}

			}
			rateGridLogger.info("listOfDays: " + listOfDays1.size());
			rateGridLogger.info(dayCheck);
			if (isNewAvailability) {

				getDay = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "MM/dd/yyyy", "EE", i, timeZone);
				getDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(CheckInDate, "MM/dd/yyyy", "d", i, timeZone);
				if (isDayExist.equalsIgnoreCase("yes")) {

					if (dayCheck.equalsIgnoreCase("yes")) {
						String getStatus = elements.get(i).getAttribute("class");
						rateGridLogger.info("get statusclass: " + elements.get(i).getAttribute("class"));
						if (isavailability.equalsIgnoreCase("available")) {
							assertTrue(getStatus.contains("NoBlackedStatus"));
							testSteps.add("Verified room class: " + roomClass + " is available on " + dayCheck);
							getAvailabilityBaseOnDays.put(getDate, isavailability);

						}
						if (isavailability.equalsIgnoreCase("blackout")) {
							assertTrue(!getStatus.contains("NoBlackedStatus"));
							assertTrue(getStatus.contains("BlackedStatus"));
							testSteps.add("Verified room class: " + roomClass + " is blackout on " + dayCheck);
							getAvailabilityBaseOnDays.put(getDate, isavailability);

						}
						isDayExist = "yes";
					}
				} else {
					testSteps.add("Verified " + dayCheck + " is not exist between of dates: " + CheckInDate + " to "
							+ CheckOutDate);
					getAvailabilityBaseOnDays.put("" + i, "NodayExist");
				}
			} else {
				if (elements.get(i).getAttribute("class").contains("NoBlackedStatus")) {
					// assertTrue(elements.get(i).getAttribute("class").contains("NoBlackedStatus"));
					getAvailabilityBaseOnDays.put("" + i, "Available");

				}
				if (!elements.get(i).getAttribute("class").contains("NoBlackedStatus")) {
					// assertTrue(!elements.get(i).getAttribute("class").contains("NoBlackedStatus"));
					// assertTrue(elements.get(i).getAttribute("class").contains("BlackedStatus"));
					getAvailabilityBaseOnDays.put("" + i, "Blackout");

				}

			}
		}

		// getAvailabilityBaseOnDays.put("DayExist", isDayExist);
		ratesGridLogger.info("getAvailabilityBaseOnDays: " + getAvailabilityBaseOnDays.size());
		ratesGridLogger.info("getAvailabilityBaseOnDays: " + getAvailabilityBaseOnDays);
		ratesGridLogger.info("Days: " + listOfDays1);

		return getAvailabilityBaseOnDays;
	}

	// Added By Farhan 09/08/2020
	public boolean getProrateForEachSeasonbyDefault(WebDriver driver) throws InterruptedException {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		Wait.WaitForElement(driver, RateGridPage.CheckboxProrateForEachSeasonbyDefault);
		Wait.waitForElementToBeVisibile(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		Wait.waitForElementToBeClickable(By.xpath(RateGridPage.CheckboxProrateForEachSeasonbyDefault), driver);
		Utility.ScrollToElement_NoWait(ratesGrid.CheckboxProrateForEachSeasonbyDefault, driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-300)");
		assertTrue(ratesGrid.CheckboxProrateForEachSeasonbyDefault.isDisplayed(),
				"Failed To Verify Take Rule from parent rate plan checkBox is not Displayed");
		boolean isChecked = false;
		if (ratesGrid.CheckboxProrateForEachSeasonbyDefault.getAttribute("class").contains("checked")) {
			assertEquals(ratesGrid.CheckboxProrateForEachSeasonbyDefault.getAttribute("class").contains("checked"),
					true, "Failed : checkBox is not in checked state");
			isChecked = true;
		} else {
			isChecked = false;
		}
		return isChecked;
	}

	public void expandReduceRoomClass(WebDriver driver, ArrayList<String> testSteps, String roomClassName)
			throws InterruptedException {
		String minusPath = "//div[@class='DatesTable']//div[contains(text(),'" + roomClassName
				+ "')]/parent::div/span[contains(@class,'ir-acrd-bnt')]";
		testSteps.add("Expand/Reduce Room Class <b>" + roomClassName + "</b>");
		logger.info("Expand/Reduce Room Class <b>" + roomClassName + "</b>");
		WebElement minus = driver.findElement(By.xpath(minusPath));
		Utility.ScrollToElement(minus, driver);
		assertTrue(minus.isDisplayed(), "Failed to verify collapse icon");
		testSteps.add("Verified Room Class <b>" + roomClassName + "</b> Expand/Reduce");
		logger.info("Verified Room Class " + roomClassName + " Expand/Reduce");
	}

	public boolean verifyRoomClassesIsSortedOrNot(WebDriver driver, ArrayList<String> getTestSteps, String RatePlan,
			ArrayList<String> roomClassesSorted) throws Exception {
		Navigation navigation = new Navigation();
		RatesGrid rateGrid = new RatesGrid();
		RoomClass rmClass = new RoomClass();
		NewRoomClassesV2 newRoomClassV2 = new NewRoomClassesV2();
		ArrayList<String> roomClassesInRatesGrid = new ArrayList<>();
		getTestSteps.add("Before sorting" + roomClassesSorted);
		ratesGridLogger.info("Before sorting" + roomClassesSorted);
		ratesGridLogger.info("Before sorting Size " + roomClassesSorted.size());
		Collections.sort(roomClassesSorted);
		getTestSteps.add("After sorting" + roomClassesSorted);
		ratesGridLogger.info("After sorting" + roomClassesSorted);
		ratesGridLogger.info("After sorting Size " + roomClassesSorted.size());
		roomClassesInRatesGrid = rateGrid.getRateGridRoomClass(driver);
		boolean inorder = true;
		for (int i = 0; i <= roomClassesSorted.size() - 1; i++) {
			if (roomClassesSorted.get(i).equals(roomClassesInRatesGrid.get(i))) {
				getTestSteps.add(roomClassesSorted.get(i) + "is in sorting order");
				ratesGridLogger.info(roomClassesSorted.get(i) + " is in sorting order");
			} else {
				inorder = false;

			}

		}
		return inorder;

	}

	public HashMap<String, ArrayList<String>> getRateDetailsFromGrid(WebDriver driver, ArrayList<String> test_steps,
			String ratePlan, String roomClassName, String channel, String startDateStr, String endDateStr,
			String dateFormat) throws InterruptedException {
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern(dateFormat));
		LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern(dateFormat));
		int dateDifference = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

		selectRatePlan(driver, ratePlan, test_steps);
		clickForRateGridCalender(driver, test_steps);
		Utility.selectDateFromDatePicker(driver, startDateStr, dateFormat);
		expandRoomClass(driver, test_steps, roomClassName);
		expandChannel(driver, test_steps, roomClassName, channel);

		for (int i = 1; i <= dateDifference; i++) {
			String date = startDate.plusDays(i - 1).format(DateTimeFormatter.ofPattern(dateFormat));
			String rate = getRateRegularAndOverriden(driver, channel, i).replace("-", "").trim();
			String minStay = getRule(driver, "Min Stay", i);
			String noCheckInRule = getNoCheckInCheckOut(driver, "No Check In", i);
			String noCheckOut = getNoCheckInCheckOut(driver, "No Check Out", i);

			rate = rate.equalsIgnoreCase("") ? "0" : rate;
			minStay = minStay.equalsIgnoreCase("") ? "0" : minStay;
			noCheckInRule = !noCheckInRule.contains("has-noValue") ? "1" : "0";
			noCheckOut = !noCheckOut.contains("has-noValue") ? "1" : "0";

			map.put(date, new ArrayList<>());
			map.get(date).add(rate);
			map.get(date).add(minStay);
			map.get(date).add(noCheckInRule);
			map.get(date).add(noCheckOut);
		}
		clickMinusChannel(driver, roomClassName, channel);

		return map;
	}

	public boolean getChannelStatus(WebDriver driver, String channel) throws InterruptedException {

		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> selectedChannels = nightlyRate.getSelectedChannels(driver);
		boolean selected = false;
		for (int i = 0; i < selectedChannels.size(); i++) {
			if (selectedChannels.get(i).equalsIgnoreCase(channel)) {
				selected = true;
			}
		}
		return selected;
	}

	public boolean getRoomClassStatus(WebDriver driver, String roomClass) throws InterruptedException {

		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
		boolean selected = false;
		for (int i = 0; i < selectedRoomClasses.size(); i++) {
			if (selectedRoomClasses.get(i).equalsIgnoreCase(roomClass)) {
				selected = true;
			}
		}
		return selected;
	}

	public boolean chargeforAdditionalAdultsChildsIsChecked(WebDriver driver) throws InterruptedException {
		String chargeforAdditionalAdultChild = "//span[text()='Charge for additional adult/child']//..//button";
		boolean chargeforAdditionalAdultsChilds = false;
		WebElement toggleBtnForChargeforAdditionalAdultChild = driver
				.findElement(By.xpath(chargeforAdditionalAdultChild));
		if (toggleBtnForChargeforAdditionalAdultChild.getAttribute("class").contains("ant-switch-checked")) {

			chargeforAdditionalAdultsChilds = true;

		}
		return chargeforAdditionalAdultsChilds;
	}

	public void unCheckRemoveRoomClassFromSeason(WebDriver driver, String roomClass) throws InterruptedException {
		String path = "//li[contains(@class,'IntervalRatePlan')]//span[text()='" + roomClass
				+ "']//preceding-sibling::span//input";
		System.out.println(path);
		WebElement roomClassCheckBox = driver.findElement(By.xpath(path));
		roomClassCheckBox.click();
		logger.info("Unchecked room class: " + roomClass);
		Wait.wait1Second();
		int size = driver.findElements(By.xpath(path)).size();
		logger.info("size : " + size);
		assertTrue(size == 0, "Failed: room Class not removed");
	}

	// Added By Adhnan
	public void bulkUpdateAndOverideRules(WebDriver driver, String delim, String RulesUpdateType,
			String RulesUpdateStartDate, String RulesUpdateEndDate, String dateFormat, String isSun_RulesUpdate,
			String isMon_RulesUpdate, String isTue_RulesUpdate, String isWed_RulesUpdate, String isThu_RulesUpdate,
			String isFri_RulesUpdate, String isSat_RulesUpdate, String RatePlanName, String RoomClasses,
			String channelName, String Type_RulesUpdate, String RuleMinStayValue_RulesUpdate,
			ArrayList<String> test_steps) throws Exception {
		ArrayList<String> getTest_Steps = new ArrayList<String>();
		DerivedRate derivedRate = new DerivedRate();
		if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {

			test_steps.add("===================  RATE PLAN RULES BLUK UPDATE ======================");
			logger.info("===================  RATE PLAN RULES BLUK UPDATE ======================");

			getTest_Steps.clear();
			getTest_Steps = clickOnBulkUpdate(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = selectBulkUpdateOption(driver, "Rules");
			test_steps.addAll(getTest_Steps);

			test_steps.add("==========SELECT START DATE==========");
			logger.info("==========SELECT START DATE==========");

			getTest_Steps.clear();
			getTest_Steps = selectStartDate(driver, RulesUpdateStartDate, dateFormat);
			test_steps.addAll(getTest_Steps);

			test_steps.add("==========SELECT END DATE==========");
			logger.info("==========SELECT END DATE==========");

			getTest_Steps.clear();
			getTest_Steps = selectEndDate(driver, RulesUpdateEndDate, dateFormat);
			test_steps.addAll(getTest_Steps);

			logger.info("==========CHECKING/UNCHECKING DAYS==========");
			test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Sun", isSun_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Mon", isMon_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Tue", isTue_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Wed", isWed_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Thu", isThu_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Fri", isFri_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = checkDays(driver, "Sat", isSat_RulesUpdate);
			test_steps.addAll(getTest_Steps);

			logger.info("==========SELECTING RATE PLAN " + RatePlanName + "==========");
			test_steps.add("==========SELECTING RATE PLAN " + RatePlanName + " ==========");

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
			test_steps.addAll(getTest_Steps);

			logger.info("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");
			test_steps.add("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");

			// ArrayList<String> roomClassesList =
			// Utility.convertTokenToArrayList(RoomClasses, delim);
			// for (String roomClassName : roomClassesList) {
//			getTest_Steps.clear();
//			getTest_Steps = selectRoomClass(driver, RoomClasses, delim);
//			test_steps.addAll(getTest_Steps);
			// }

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Room class", RoomClasses);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = selectItemsFromDropDowns(driver, "Source", channelName);
			test_steps.addAll(getTest_Steps);

			ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, Utility.DELIM);

			logger.info("Type_RulesUpdate : " + Type_RulesUpdate);
			for (String type : type_rulesUpdateList) {
				logger.info("type : " + type);
				if (type.equalsIgnoreCase("min stay")) {
					getTest_Steps.clear();
					getTest_Steps = clickMinimumStay(driver, "Yes");
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = enterMinimumStayValue(driver, RuleMinStayValue_RulesUpdate);
					test_steps.addAll(getTest_Steps);

				} else if (type.equalsIgnoreCase("No Check In")) {

					getTest_Steps.clear();
					getTest_Steps = clickCheckin(driver, "Yes");
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = clickNoCheckInCheckbox(driver, "Yes");
					test_steps.addAll(getTest_Steps);

				} else if (type.equalsIgnoreCase("No Check out")) {
					getTest_Steps.clear();
					getTest_Steps = clickCheckOut(driver, "Yes");
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = clickNoCheckOutCheckbox(driver, "Yes");
					test_steps.addAll(getTest_Steps);

				}

			}
			getTest_Steps.clear();
			getTest_Steps = clickUpdateButton(driver);
			test_steps.addAll(getTest_Steps);

			logger.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
			test_steps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

			int numberOfDays = Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate, dateFormat) + 1;
			String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
			test_steps.add("Expected total days : " + expectedDays);
			logger.info("Expected total days : " + expectedDays);
			String totalDays = getTotalDaysText(driver, "Rules");
			test_steps.add("Found : " + totalDays);
			logger.info("Found : " + totalDays);
			Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
			test_steps.add("Verified total number of days");
			logger.info("Verified total number of days");

			getTest_Steps.clear();
			getTest_Steps = clickOnYesUpdateButton(driver);
			test_steps.addAll(getTest_Steps);
		} else if (RulesUpdateType.equalsIgnoreCase("Override") || RulesUpdateType.equalsIgnoreCase("Overide")) {

			test_steps.add("===================  RATE PLAN RULES OVERIDE ======================");
			logger.info("===================  RATE PLAN RULES OVERIDE ======================");

			ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(RoomClasses, delim);
			ArrayList<String> desiredOverideDate = Utility.convertTokenToArrayList(RulesUpdateStartDate, delim);
			ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);

			for (String roomClassName : roomClassesList) {
				for (int i = 0; i < desiredOverideDate.size(); i++) {

					clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, desiredOverideDate.get(i), "dd/MM/yyyy");

					expandRoomClass(driver, test_steps, roomClassName);
					expandChannel(driver, test_steps, roomClassName, channelName);

					overrideMinStayValue(driver, test_steps, roomClassName, channelName, "0");
					overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName, "No Check In",
							false);
					overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName, "No Check Out",
							false);

				}
			}
			for (String roomClassName : roomClassesList) {
				for (int i = 0; i < desiredOverideDate.size(); i++) {

					clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, desiredOverideDate.get(i), "dd/MM/yyyy");
					expandRoomClass(driver, test_steps, roomClassName);
					expandChannel(driver, test_steps, roomClassName, channelName);

					for (String type : type_rulesUpdateList) {

						ArrayList<String> typeList = Utility.convertTokenToArrayList(type, ",");
						for (String rule : typeList) {
							if (rule.equalsIgnoreCase("min stay")) {
								overrideMinStayValue(driver, test_steps, roomClassName, channelName,
										RuleMinStayValue_RulesUpdate);
							} else if (rule.equalsIgnoreCase("No Check In")) {
								overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										"No Check In", true);

							} else if (rule.equalsIgnoreCase("No Check out")) {
								overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										"No Check Out", true);
							}
						}
					}
				}
			}

		}
	}

	// Added By Adhnan
	public ArrayList<String> selectEndDate(WebDriver driver, String endDate, String dateFormat)
			throws ParseException, InterruptedException {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.EndDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.EndDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.EndDateInput), driver);
		elements.EndDateInput.click();
		testSteps.add("Click end date");
		rateGridLogger.info("Click end date");
		Utility.selectDateFromDatePicker(driver, endDate, dateFormat);
		testSteps.add("Select end date : " + endDate);
		rateGridLogger.info("Select end date : " + endDate);
		return testSteps;
	}

	// Added By Adhnan
	public ArrayList<String> selectStartDate(WebDriver driver, String startDate, String dateFormat)
			throws ParseException, InterruptedException {

		Elements_RatesGrid elements = new Elements_RatesGrid(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR.StartDateInput);
		Wait.waitForElementToBeVisibile(By.xpath(OR.StartDateInput), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR.StartDateInput), driver);
		elements.StartDateInput.click();
		testSteps.add("Click start date");
		rateGridLogger.info("Click start date");
		Utility.selectDateFromDatePicker(driver, startDate, dateFormat);
		testSteps.add("Select start date : " + startDate);
		rateGridLogger.info("Select start date : " + startDate);
		return testSteps;
	}

	// Added By Adhnan
	public void clearRulesFrombulkUpdate(WebDriver driver, String delim, String RulesUpdateType,
			String RulesUpdateStartDate, String RulesUpdateEndDate, String dateFormat, String isSun_RulesUpdate,
			String isMon_RulesUpdate, String isTue_RulesUpdate, String isWed_RulesUpdate, String isThu_RulesUpdate,
			String isFri_RulesUpdate, String isSat_RulesUpdate, String RatePlanName, String RoomClasses,
			String channelName, ArrayList<String> test_steps) throws Exception {
		ArrayList<String> getTest_Steps = new ArrayList<String>();
		DerivedRate derivedRate = new DerivedRate();
		test_steps.add("===================  RATE PLAN CLEAR ALL RULES BLUK UPDATE ======================");
		logger.info("===================  RATE PLAN CLEAR ALL RULES BLUK UPDATE ======================");

		getTest_Steps.clear();
		getTest_Steps = clickOnBulkUpdate(driver);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = selectBulkUpdateOption(driver, "Rules");
		test_steps.addAll(getTest_Steps);

		test_steps.add("==========SELECT START DATE==========");
		logger.info("==========SELECT START DATE==========");

		getTest_Steps.clear();
		getTest_Steps = selectStartDate(driver, RulesUpdateStartDate, dateFormat);
		test_steps.addAll(getTest_Steps);

		test_steps.add("==========SELECT END DATE==========");
		logger.info("==========SELECT END DATE==========");

		getTest_Steps.clear();
		getTest_Steps = selectEndDate(driver, RulesUpdateEndDate, dateFormat);
		test_steps.addAll(getTest_Steps);

		logger.info("==========CHECKING/UNCHECKING DAYS==========");
		test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

		getTest_Steps.clear();
		getTest_Steps = checkDays(driver, "Sun", isSun_RulesUpdate);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = checkDays(driver, "Mon", isMon_RulesUpdate);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = checkDays(driver, "Tue", isTue_RulesUpdate);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = checkDays(driver, "Wed", isWed_RulesUpdate);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = checkDays(driver, "Thu", isThu_RulesUpdate);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = checkDays(driver, "Fri", isFri_RulesUpdate);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = checkDays(driver, "Sat", isSat_RulesUpdate);
		test_steps.addAll(getTest_Steps);

		logger.info("==========SELECTING RATE PLAN " + RatePlanName + "==========");
		test_steps.add("==========SELECTING RATE PLAN " + RatePlanName + " ==========");

		getTest_Steps.clear();
		getTest_Steps = selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
		test_steps.addAll(getTest_Steps);

		logger.info("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");
		test_steps.add("==========SELECTING ROOM CLASS : " + RoomClasses + " ==========");

		getTest_Steps.clear();
		getTest_Steps = selectItemsFromDropDowns(driver, "Room class", RoomClasses);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = selectItemsFromDropDowns(driver, "Source", channelName);
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = clickMinimumStay(driver, "Yes");
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = enterMinimumStayValue(driver, "0");
		test_steps.addAll(getTest_Steps);
		getTest_Steps.clear();
		getTest_Steps = clickCheckin(driver, "Yes");
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = clickNoCheckInCheckbox(driver, "No");
		test_steps.addAll(getTest_Steps);
		getTest_Steps.clear();
		getTest_Steps = clickCheckOut(driver, "Yes");
		test_steps.addAll(getTest_Steps);

		getTest_Steps.clear();
		getTest_Steps = clickNoCheckOutCheckbox(driver, "No");
		test_steps.addAll(getTest_Steps);
		getTest_Steps.clear();
		getTest_Steps = clickUpdateButton(driver);
		test_steps.addAll(getTest_Steps);

		logger.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
		test_steps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

		int numberOfDays = Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate, dateFormat) + 1;
		String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
		test_steps.add("Expected total days : " + expectedDays);
		logger.info("Expected total days : " + expectedDays);
		String totalDays = getTotalDaysText(driver, "Rules");
		test_steps.add("Found : " + totalDays);
		logger.info("Found : " + totalDays);
		Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
		test_steps.add("Verified total number of days");
		logger.info("Verified total number of days");

		getTest_Steps.clear();
		getTest_Steps = clickOnYesUpdateButton(driver);
		test_steps.addAll(getTest_Steps);

	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getRatesWithAdultChildFromChannel(WebDriver driver,
			ArrayList<String> test_steps, String roomClass, String channel, ArrayList<String> days,
			boolean isDateSelect) throws Exception {
		Elements_RatesGrid elements = new Elements_RatesGrid(driver);

		boolean sequence = Utility.checkAllDatesAreInSequenceOrNot(days);
		HashMap<String, ArrayList<HashMap<String, String>>> dateWiseRate = new HashMap<String, ArrayList<HashMap<String, String>>>();
		if (sequence) {
			if (isDateSelect) {
				selectDateFromRatesGridCalendar(driver, test_steps, days.get(0));
			}
			expandRoomClass(driver, test_steps, roomClass);
			for (int i = 1; i <= days.size(); i++) {
				ArrayList<HashMap<String, String>> getRate = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> includeAdditional = new HashMap<String, String>();
				String clickCell = "(//div[text()='" + roomClass + "']/following::div[text()='" + channel
						+ "']/following::div[@class=' RegularRate' or @class='has-changes Override'])[" + i + "]";
				Wait.waitForElementToBeVisibile(By.xpath(clickCell), driver);

				logger.info("get for" + days.get(i - 1));
				try {
					driver.findElement(By.xpath(clickCell)).click();
				} catch (Exception e) {
					Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(clickCell)));
				}
				Wait.waitForElementToBeVisibile(By.xpath(OR_RatesGrid.GetBaseFromSourcePopup), driver);
				String baseRate = elements.getBaseFromSourcePopup.getAttribute("value").toString();
				// getRate.add(baseRate);
				includeAdditional.put("BaseRate", baseRate);

				String extraAdultAmount = elements.getAdultFromSourcePopup.getAttribute("value").toString();
				if (Utility.validateString(extraAdultAmount)) {
					// getRate.add(extraAdultAmount);
					includeAdditional.put("ExtraAdult", extraAdultAmount);
				}
				String extraChildAmount = elements.getChildFromSourcePopup.getAttribute("value").toString();
				if (Utility.validateString(extraAdultAmount)) {
					// getRate.add(extraChildAmount);
					includeAdditional.put("ExtraChild", extraChildAmount);
				}
				getRate.add(includeAdditional);
				dateWiseRate.put(days.get(i - 1), getRate);
			}
		} else {
			int j = 1;
			for (int i = 1; i <= days.size(); i++) {
				ArrayList<HashMap<String, String>> getRate = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> includeAdditional = new HashMap<String, String>();
				selectDateFromRatesGridCalendar(driver, test_steps, days.get(i - 1));
				expandRoomClass(driver, test_steps, roomClass);
				String clickCell = "(//div[text()='" + roomClass + "']/following::div[text()='" + channel
						+ "']/following::div[@class=' RegularRate' or @class='has-changes Override'])[" + j + "]";
				Wait.waitForElementToBeVisibile(By.xpath(clickCell), driver);
				driver.findElement(By.xpath(clickCell)).click();
				String baseRate = elements.getBaseFromSourcePopup.getAttribute("value").toString();
				includeAdditional.put("BaseRate", baseRate);
				String extraAdultAmount = elements.getAdultFromSourcePopup.getAttribute("value").toString();
				if (Utility.validateString(extraAdultAmount)) {
					includeAdditional.put("ExtraAdult", extraAdultAmount);
				}
				String extraChildAmount = elements.getChildFromSourcePopup.getAttribute("value").toString();
				if (Utility.validateString(extraAdultAmount)) {
					includeAdditional.put("ExtraChild", extraChildAmount);
				}
				getRate.add(includeAdditional);
				dateWiseRate.put(days.get(i - 1), getRate);
			}
		}
		return dateWiseRate;

	}
	public boolean getBlackoutStatus(WebDriver driver, int index, String source, String roomClassName)
			throws InterruptedException {
		boolean isBlacked = false;
		String xpath = "//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName' and text()='" + source
				+ "']//parent::div/following-sibling::div";
		Wait.WaitForElement(driver, xpath);
		List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
		Utility.ScrollToElement(labelValues.get(index), driver);
		String roomStatus = labelValues.get(index).getAttribute("class");
		Utility.app_logs.info("Initial room Status : " + roomStatus);
		if (roomStatus.contains("NoBlackedStatus")) {
			isBlacked = false;
		} else {
			isBlacked = true;
		}
		return isBlacked;
	}

	public void associatePolicyAtSeason(WebDriver driver, ArrayList<String> test_steps, String ratePlanName,
			String checkInDate, String policyType, String policyName) throws Exception {
		Navigation navigation = new Navigation();
		// navigation.RatesGrid(driver);
		test_steps.add("Navigated to rates grid in InnCenter");
		searchAndEditRatePlan(driver, test_steps, ratePlanName);
		switchCalendarTab(driver, test_steps);
		selectSeasonDates(driver, test_steps, checkInDate);
		clickEditThisSeasonButton(driver, test_steps);
		clickSeasonPolicies(driver, test_steps);
		selectPolicyAtSeasonTab(driver, test_steps, policyType, policyName);
		clickSaveSason(driver, test_steps);
		clickOnSaveratePlan(driver);
		closeRatePlan(driver, ratePlanName, test_steps);
	}

	public void clickSeasonPolicies(WebDriver driver, ArrayList<String> test_steps) {
		Elements_RatesGrid ratessGrid = new Elements_RatesGrid(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_RatesGrid.RatePlan_Season_SeasonPolices), driver);
		try {
			Utility.ScrollToElement(ratessGrid.RatePlan_Season_SeasonPolices, driver);
			ratessGrid.RatePlan_Season_SeasonPolices.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, ratessGrid.RatePlan_Season_SeasonPolices);
		}
		test_steps.add("Clicking on season policies");
	}

	public void selectPolicyAtSeasonTab(WebDriver driver, ArrayList<String> test_steps, String policyType,
			String policyName) throws Exception {
		Elements_RatesGrid ratesGrid = new Elements_RatesGrid(driver);
		String roomClassPoliciesCheckBox = ratesGrid.assignPoliciesByRoomClass.getAttribute("aria-checked");
		if (roomClassPoliciesCheckBox.equalsIgnoreCase("true")) {
			ratesGrid.assignPoliciesByRoomClass.click();
		}
		String policyToSelect = "//div[@class='policy-set-item']//input[@class='ant-checkbox-input' " + "and @value='"
				+ policyType + "']/../../following-sibling::div//span[text()='" + policyName + "']/..//input";
		String policyTypeXpath = "//div[@class='policy-set-item']//input[@class='ant-checkbox-input' and @value='"
				+ policyType + "']";
		boolean policyTypeSelected = driver.findElement(By.xpath(policyTypeXpath)).isSelected();
		if (policyTypeSelected) {
			driver.findElement(By.xpath(policyToSelect)).click();
		} else {
			driver.findElement(By.xpath(policyTypeXpath)).click();
			driver.findElement(By.xpath(policyToSelect)).click();
		}
		test_steps.add("Associated season level <b>" + policyType + "</b> policy as <b>" + policyName + "</b>");
	}

	public void verifyRatesGridLoaded(WebDriver driver) throws InterruptedException {
		String ratesGridLoad = "//div[contains(text(),'Failed to load rates grid. Please refresh.')]";
		Wait.wait5Second();
		boolean falg = false;
		while (!falg) {
			try {
				if (driver.findElements(By.xpath(ratesGridLoad)).size() > 0) {
					System.out.println("Failed to load rates grid. Please refresh.");
					driver.navigate().refresh();
					Wait.wait5Second();
				} else {
					System.out.println("Rates grid loaded");
					falg = true;
				}
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println("Rates grid loaded");
				falg = true;
			}
		}
	}

	
public void reduceRoomClass(WebDriver driver,String roomClass, ArrayList<String> testSteps) throws InterruptedException{
			
		String expandRoomClass = "//div[text()='"+roomClass+"']//following-sibling::span[contains(@class,'ir-minus')]";
		rateGridLogger.info("Found room class reduce icon");
	
		Wait.wait10Second();
		if(driver.findElements(By.xpath(expandRoomClass)).size()>0) {
			Wait.waitForElementToBeVisibile(By.xpath(expandRoomClass), driver);
			Wait.waitForElementToBeClickable(By.xpath(expandRoomClass), driver);
				
			JavascriptExecutor javaScript = (JavascriptExecutor) driver;
			javaScript.executeScript("window.scrollBy(0,-300)");
			try{
				driver.findElement(By.xpath(expandRoomClass)).click();
			}catch(Exception e){
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(expandRoomClass)));
			}
		}
		testSteps.add("Reduce Room Class");
		rateGridLogger.info("Reduce Room Class");

		}
public void expandRoomClass(WebDriver driver,String roomClass, ArrayList<String> testSteps) throws InterruptedException{
	
	
	String expandRoomClass = "//div[text()='"+roomClass+"']//following-sibling::span[contains(@class,'ir-plus')]";
	rateGridLogger.info("Found room classexpand icon");


	Wait.wait10Second();
	if(driver.findElements(By.xpath(expandRoomClass)).size()>0) {
		Wait.waitForElementToBeVisibile(By.xpath(expandRoomClass), driver);
		Wait.waitForElementToBeClickable(By.xpath(expandRoomClass), driver);
			
		JavascriptExecutor javaScript = (JavascriptExecutor) driver;
		javaScript.executeScript("window.scrollBy(0,-300)");
		try{
			driver.findElement(By.xpath(expandRoomClass)).click();
		}catch(Exception e){
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(expandRoomClass)));
		}
	}
	testSteps.add("Expand Room Class");
	rateGridLogger.info("Expand Room Class");
	
	}

public HashMap<String, String> getAvailabilityForGivenDays(WebDriver driver, String roomClass, String channel,
		String CheckInDate, String CheckOutDate, HashMap<String, String> listOfDays1 ,ArrayList<String> testSteps) throws Exception {
	HashMap<String, String> getAvailabilityBaseOnDays = new HashMap<String, String>();

	String availabilityPath = "//div[contains(text(),'" + roomClass
			+ "')]//..//..//..//div[@class='roomClassName' and text()='" + channel
			+ "']//..//..//div[contains(@class,'AvailabilityDateCell')]";
	ratesGridLogger.info("availabilityPath: " + availabilityPath);
	Wait.WaitForElement(driver, availabilityPath);
	Wait.waitForElementToBeVisibile(By.xpath(availabilityPath), driver);
	Wait.waitForElementToBeClickable(By.xpath(availabilityPath), driver);
	
	List<WebElement> elements = driver.findElements(By.xpath(availabilityPath));
	Utility.ScrollToElement_NoWait(elements.get(0), driver);

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	LocalDateTime now = LocalDateTime.now();
	int count = ESTTimeZone.numberOfDaysBetweenDates(CheckInDate, CheckOutDate);
	count = count+1;
	rateGridLogger.info("count: "+count);
	rateGridLogger.info("before start listOfDays: " + listOfDays1.size());
	rateGridLogger.info("listOfDays: " + listOfDays1);
	String getDate = "";
	for (int i = 1; i <= count; i++) {
		rateGridLogger.info("in loop that get day");
		String InDate=Utility.getNextDate(i-1, "dd/MM/yyyy");
		getDate=Utility.getDifferentDateFormat(InDate, "d");
		String getStatus = elements.get(i).getAttribute("class");
		rateGridLogger.info("get statusclass: "+elements.get(i).getAttribute("class"));
		
			if(getStatus.contains("NoBlackedStatus")) {
				testSteps.add("Verified room class: " + roomClass + " is available on " + InDate);
				getAvailabilityBaseOnDays.put(getDate, "Available");
			}else {

				testSteps.add("Verified room class: " + roomClass + " is BlackOut on " + InDate);
				getAvailabilityBaseOnDays.put(getDate, "Blackout");
				}
				

			}

	ratesGridLogger.info("getAvailabilityBaseOnDays: "+getAvailabilityBaseOnDays);


	return getAvailabilityBaseOnDays;
}
public ArrayList<String> getChannelRatesForTheGivenDates(WebDriver driver, String startDate,String endDate,String roomClassName, String channelName)
		throws InterruptedException, ParseException {
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> allDates = Utility.getAllDatesBetweenCheckInOutDates(startDate, endDate);
	String xpath = "(//div[contains(@class,'RoomClassHeader ')]//div[@class='roomClassName'and contains(@title,'"
			+ roomClassName + "')]//parent::div/parent::div//following-sibling::div//div[contains(@class,'d-flex')]"
			+ "//div[@title='" + channelName + "']//parent::div//following-sibling::div/div[1])";
	List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
	int i=1;
	for (WebElement ele : labelValues) {
			if(i<=allDates.size()) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			data.add(ele.getText());
			i++;
		}else {
			
		}
	}
	logger.info(data);
	return data;
}
public ArrayList<String> getRuleDataValueForCheckInCheckOut1(WebDriver driver,String startDate, String endDate, String roomClassName,
		String channelName, String ruleName) throws InterruptedException, ParseException {
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> allDates = Utility.getAllDatesBetweenCheckInOutDates(startDate, endDate);
	String xpath = "//div[@class='DatesTable']//div[contains(@title,'" + roomClassName + "')]"
			+ "//../..//following-sibling::div//div[@title='" + channelName + "']"
			+ "//../..//following-sibling::div//div[contains(@class,'RuleHeader') and text()='" + ruleName
			+ "']/following-sibling::div/div/div";
	System.out.println(xpath);
	Wait.WaitForElement(driver, xpath);
	List<WebElement> labelValues = driver.findElements(By.xpath(xpath));
	int i=1;
	for (WebElement ele : labelValues) {
		if(i<=allDates.size()) {
			Utility.ScrollToViewElementINMiddle(driver, ele);
			String checkinRule=ele.getAttribute("class");
			if(checkinRule.contains("noValue")) {
				data.add("No");
			}else {
				data.add("Yes");
			}
			i++;
		}else {
			
		}
	}
	logger.info(data);
	return data;
}

public ArrayList<String> verifyReservationDetailsPageIsShowing(WebDriver driver) {
	ArrayList<String> testSteps = new ArrayList<>();
	String path ="//div[text()='ADD-ONS & INCIDENTALS']";
	Wait.WaitForElement(driver, path);
	Assert.assertEquals(driver.findElements(By.xpath(path)).size(),1,"Failed : reservation details page is not showing.");
	
	testSteps.add("Successfully verified reservation details page is showing.");
	logger.info("Successfully verified reservation details page is showing.");
	return testSteps;
	
}

public void uncheckPromoCode(WebDriver driver, ArrayList test_steps) {
	boolean isBookingWindow = false;
	String bookingWindowCheckBox = "//span[text()='Promo code']/..//span[contains(@class,'ant-checkbox')]//input";
	isBookingWindow = driver.findElement(By.xpath(bookingWindowCheckBox)).isSelected();
	if(isBookingWindow) {
		driver.findElement(By.xpath(bookingWindowCheckBox)).click();	
		test_steps.add("Promo Code is unchecked");
		logger.info("Promo Code is unchecked");
	}else {
		test_steps.add("Promo Code is already unchecked");
		logger.info("Promo Code is already unchecked");
	}

}


}

